package com.nuubit.sdk;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.utils.DateTimeUtil;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by victor on 17.06.17.
 */

public class ConfigMissingLines extends ApplicationTestCase<NuubitApplication> {
    private static final String TAG = "newConfigNull";
    private NuubitApplication application;

    public ConfigMissingLines() {
        super(NuubitApplication.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        application = getApplication();
        //application.registration();
        Method m = application.getClass().getDeclaredMethod("registration");
        m.setAccessible(true);
        m.invoke(application);

    }
    @Test
    public void missingLines() throws Exception {
        Field receiver = null;
        BroadcastReceiver rec = null;
        try {
            receiver = application.getClass().getDeclaredField("configReceiver");
            receiver.setAccessible(true);
            rec = (BroadcastReceiver) receiver.get(application);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field confOld = application.getClass().getDeclaredField("config");
        confOld.setAccessible(true);
        Config cOld = (Config) confOld.get(application);


        Intent iRes = new Intent(NuubitActions.CONFIG_UPDATE_ACTION);
        iRes.putExtra(NuubitConstants.HTTP_RESULT, 200);
        iRes.putExtra(NuubitConstants.CONFIG, "{'field': 'field'}");
        rec.onReceive(application,iRes);

        Field conf = application.getClass().getDeclaredField("config");
        conf.setAccessible(true);
        Config c = (Config) conf.get(application);

        System.out.println(c);
        Log.d(TAG, DateTimeUtil.dateToString(application, c.getLastUpdate()));

        assertNotNull(c);
        assertEquals(c,cOld);
    }
}
