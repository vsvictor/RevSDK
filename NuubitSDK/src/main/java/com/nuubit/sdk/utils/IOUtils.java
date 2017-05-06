package com.nuubit.sdk.utils;

import android.util.Log;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.protocols.HTTPException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

public class IOUtils {
    private static final String TAG = IOUtils.class.getSimpleName();

    public static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    public static Response runRequest(OkHttpClient client, String url, String method, RequestBody abody) throws HTTPException {
        Response response;
        RequestBody body = abody;
        try {
            HttpUrl realURL = HttpUrl.parse(url);
            if(realURL == null) throw new IOException("Bad URL");
            if (body == null && method.equalsIgnoreCase("POST"))
                body = RequestBody.create(null, new byte[0]);
            Request req = new Request.Builder()
                    .url(url)
                    .header(NuubitConstants.USER_AGENT, NuubitConstants.USER_AGENT_VALUE)
                    .method(method, body)
                    .build();

            response = client.newCall(req).execute();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        } catch (NullPointerException e){
            response = null;
            e.printStackTrace();
        }
        return response;
    }

    public static long responseHeadersSize(Response response) {
        long result = 0;
        if (response != null) {

            for (String sName : response.headers().names()) {
                String sVal = response.header(sName);
                result += sName.getBytes().length;
                result += sVal.getBytes().length;
            }

            long bodySize = 0;
/*
            try {
                bodySize = response.body().string().getBytes().length;
                result += bodySize;
                Log.i(TAG, "Loaded response, size: "+ bodySize);
            } catch (IOException e) {
                Log.i(TAG, "Exception response, size: "+ 0);
                e.printStackTrace();
            }
*/
        }
        return result;
    }

    public static long requestSize(Request request) {
        long result = 0;
        if (request != null) {
            for (String sName : request.headers().names()) {
                String sVal = request.header(sName);
                result += sName.getBytes().length;
                result += sVal.getBytes().length;
            }
            try {
                long bodySize = request.body().contentLength();
                result += bodySize;
                Log.i(TAG, "Loaded request, size: "+ bodySize);
            } catch (Exception e) {
                Log.i(TAG, "Exception request, size: "+ 0);
                e.printStackTrace();
            }
        }
        return result;
    }
}
