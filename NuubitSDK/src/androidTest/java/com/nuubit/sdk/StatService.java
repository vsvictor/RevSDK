package com.nuubit.sdk;

import android.content.ContentValues;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.test.ServiceTestCase;
import android.util.Log;

import com.nuubit.sdk.database.DBHelper;
import com.nuubit.sdk.database.RequestTable;
import com.nuubit.sdk.interseptor.NuubitInterceptor;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.services.Statist;
import com.nuubit.sdk.statistic.Statistic;
import com.nuubit.sdk.statistic.sections.RequestOne;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

@RunWith(MockitoJUnitRunner.class)
public class StatService extends ApplicationTestCase<NuubitApplication> {
    private final static String TAG = StatService.class.getName();
    private NuubitApplication application;


    public StatService() {
        super(NuubitApplication.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        application = getApplication();

    }
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void startStat() {

        RequestOne result = new RequestOne();
        result.setID(-1);
        result.setConnectionID(-1);
        result.setContentEncode("utf-8");
        result.setContentType("text/html");
        result.setStartTS(System.currentTimeMillis());
        result.setSentTS(System.currentTimeMillis());
        result.setFirstByteTime(System.currentTimeMillis());
        result.setEndTS(System.currentTimeMillis());
        result.setKeepAliveStatus(1);
        result.setLocalCacheStatus(NuubitConstants.UNDEFINED);
        result.setMethod("GET");
        result.setEdgeTransport(EnumProtocol.STANDARD);

        result.setNetwork("NETWORK");

        result.setEnumProtocol(EnumProtocol.fromString("https"));
        result.setReceivedBytes(System.currentTimeMillis());

        RequestBody body = null;

        long bodySize = 0;
        if (body != null) {
            try {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                bodySize = buffer.size();
            } catch (IOException e) {
                e.printStackTrace();
                bodySize = 0;
            }
        }

        result.setSentBytes(1000L);
        result.setStatusCode(200);
        result.setSuccessStatus(0);
        result.setTransportEnumProtocol(EnumProtocol.STANDARD);
        result.setURL("https://google.com");
        result.setDestination("https://google.com");
        String cache = (NuubitConstants.UNDEFINED);
        result.setXRevCache(cache == null ? NuubitConstants.UNDEFINED : cache);
        result.setDomain("google.com");


        DBHelper dbHelper = new DBHelper(application);
        ContentValues cv = RequestTable.toContentValues("test",result);
        cv.put(RequestTable.Columns.SENT, 1);
        cv.put(RequestTable.Columns.CONFIRMED,0);
        dbHelper.insertRequest(cv);
        dbHelper.close();

        int unsent = dbHelper.getUnsent().getCount();
        Log.i("Statist","Unsent count:"+unsent);

        Statist statist = new Statist("Mock");
        Intent intent = new Intent(application, Statist.class);
        intent.putExtra(NuubitConstants.TIMEOUT, 10);
        intent.putExtra(NuubitConstants.STATISTIC, "http://httpbin.org/status/400");
        statist.onHandleIntent(intent);
        Log.i("Statist","Unsent count:"+dbHelper.getUnsent().getCount());
        assertEquals(unsent, dbHelper.getUnsent().getCount()-1);
    }
}