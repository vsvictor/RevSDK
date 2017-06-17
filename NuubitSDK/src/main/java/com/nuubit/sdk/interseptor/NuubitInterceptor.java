package com.nuubit.sdk.interseptor;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.statistic.sections.RequestOne;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Response;

/**
 * Created by victor on 19.04.17.
 */

public class NuubitInterceptor implements Interceptor {
    private static final String TAG = NuubitInterceptor.class.getSimpleName();

  @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = null;

        long begTime = System.currentTimeMillis();
        long endTime;
        try {
            begTime = System.currentTimeMillis();
            response = NuubitApplication.getInstance().getBest().send(chain);
            if(response == null) throw new IOException("Response error");
            endTime = System.currentTimeMillis();
        } catch (IOException ex) {
            //response = null;
            endTime = System.currentTimeMillis();
            final RequestOne statRequest = RequestOne.toRequestOne(chain.request(), chain.request(), response, NuubitApplication.getInstance().getBest().getDescription(), begTime, endTime, 0);
            if(statRequest.getFirstByteTime() == 0) statRequest.setFirstByteTime(statRequest.getEndTS());
            NuubitApplication.getInstance().getDatabase().insertRequest(RequestTable.toContentValues(NuubitApplication.getInstance().getConfig().getAppName(), statRequest));
            response = new Response.Builder().code(400).request(chain.request()).protocol(Protocol.HTTP_2).build();
        }
        return response;
    }
}
