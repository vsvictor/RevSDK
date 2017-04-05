package com.rev.sdk.protocols;

import android.util.Log;

import com.rev.sdk.RevApplication;
import com.rev.sdk.RevSDK;
import com.rev.sdk.database.RequestTable;
import com.rev.sdk.statistic.sections.RequestOne;

import java.io.IOException;

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

    public StandardProtocol() {
        this.descroption = EnumProtocol.STANDART;
    }

    @Override
    public Response send(Interceptor.Chain chain) throws IOException {
        Request result = null;
        Request original = chain.request();
        boolean systemRequest = isSystem(original);
        boolean freeRequest = isFree(original);
        if (!systemRequest && !freeRequest) {
            result = RevSDK.processingRequest(original);
        } else {
            Log.i("System", original.toString());
            result = original;
        }

        Response response = chain.proceed(result);

        if (!systemRequest && isStatistic()) {
            try {
                final RequestOne statRequest = RequestOne.toRequestOne(original, result, response, RevApplication.getInstance().getBest().getDescription());
                RevApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(RevApplication.getInstance().getConfig().getAppName(), statRequest));
                Log.i("database", statRequest.toString());
            } catch (NullPointerException ex) {
                Log.i("database", "Database error!!!");
            }
        }
        Log.i(TAG, "Response:" + response.toString());
        return response;
    }

    @Override
    public long test() {
        return 0;
    }
}
