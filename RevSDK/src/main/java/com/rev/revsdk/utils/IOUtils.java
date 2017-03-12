package com.rev.revsdk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IOUtils {
    public static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    public static Response runRequest(OkHttpClient client, String url, String method, RequestBody abody) {
        Response response;
        RequestBody body = abody;
        if (body == null && method.equalsIgnoreCase("POST"))
            body = RequestBody.create(null, new byte[0]);
        Request req = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
        try {
            response = client.newCall(req).execute();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        }
        return response;
    }
}
