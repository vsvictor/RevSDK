package com.rev.sdk.protocols;

import android.util.Log;

import com.rev.sdk.RevApplication;
import com.rev.sdk.database.RequestTable;
import com.rev.sdk.statistic.sections.RequestOne;

import okhttp3.Request;
import okhttp3.Response;

import static com.rev.sdk.RevSDK.isStatistic;
import static com.rev.sdk.RevSDK.isSystem;

/**
 * Created by victor on 06.04.17.
 */

public class HTTPException extends Exception {
    private Protocol protocol;

    public HTTPException(Request origin, Request transfered, Response response, Protocol protocol) {
        if (!isSystem(origin) && isStatistic()) {
            try {
                final RequestOne statRequest = RequestOne.toRequestOne(origin, transfered, response, RevApplication.getInstance().getBest().getDescription());
                RevApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(RevApplication.getInstance().getConfig().getAppName(), statRequest));
                Log.i("database", statRequest.toString());
            } catch (NullPointerException ex) {
                Log.i("database", "Database error!!!");
            }
        }
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }
}
