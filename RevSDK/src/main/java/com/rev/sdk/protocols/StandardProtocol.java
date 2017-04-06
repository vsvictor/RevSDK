package com.rev.sdk.protocols;

import android.content.Intent;
import android.util.Log;

import com.rev.sdk.Actions;
import com.rev.sdk.Constants;
import com.rev.sdk.RevApplication;
import com.rev.sdk.RevSDK;
import com.rev.sdk.database.RequestTable;
import com.rev.sdk.statistic.sections.RequestOne;
import com.rev.sdk.types.HTTPCode;
import com.rev.sdk.types.Tag;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.rev.sdk.RevSDK.isFree;
import static com.rev.sdk.RevSDK.isStatistic;
import static com.rev.sdk.RevSDK.isSystem;

/**
 * Created by victor on 05.04.17.
 */

public class StandardProtocol extends Protocol {
    private static final String TAG = StandardProtocol.class.getSimpleName();
    private HTTPException prevException;
    public StandardProtocol() {
        this.descroption = EnumProtocol.STANDART;
    }

    @Override
    public synchronized Response send(Interceptor.Chain chain) throws IOException {
        Request result = null;
        Request original = chain.request();
        boolean systemRequest = isSystem(original);
        boolean freeRequest = isFree(original);
        if (!systemRequest && !freeRequest) {
            result = RevSDK.processingRequest(original, true);
        } else {
            Log.i("System", original.toString());
            result = original;
        }
        Response response = null;
        try {
            response = chain.proceed(result);
            if (response == null) {
                throw new HTTPException(original, result, response, this);
            }
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() == HTTPCode.Type.SERVER_ERROR) {
                throw new HTTPException(original, result, response, this);
            }

            if (!isSystem(original) && isStatistic()) {
                try {
                    final RequestOne statRequest = RequestOne.toRequestOne(original, result, response, RevApplication.getInstance().getBest().getDescription());
                    RevApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(RevApplication.getInstance().getConfig().getAppName(), statRequest));
                    Log.i("database", statRequest.toString());
                } catch (NullPointerException ex) {
                    Log.i("database", "Database error!!!");
                }
            }
            if (!isSystem(original)) {
                this.zeroing();
            }
        } catch (HTTPException ex) {
            response = chain.proceed(original);
            this.errorIncrement();
            if (this.isOverflow()) {
                RevApplication.getInstance().removeProtocol(EnumProtocol.createInstance(this.getDescription()));
                RevApplication.getInstance().sendBroadcast(new Intent(Actions.RETEST));
                this.zeroing();
            }
            ex.printStackTrace();
        }
        Log.i(TAG, "Response:" + response.toString());
        return response;
    }

    @Override
    public long test(String url) {
        long res = -1;
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(new Tag(Constants.SYSTEM_REQUEST, true));
        Call callback = RevSDK.OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC, false, false).newCall(builder.build());
        try {
            Response response = callback.execute();
            HTTPCode code = HTTPCode.create(response.code());
            if (code.getType() != HTTPCode.Type.SUCCESSFULL) {
                return -1;
            } else {
                res = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = -1;
        }
        return res;
    }
}
