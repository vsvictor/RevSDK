package com.nuubit.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.MoreAsserts;
import android.util.Log;

//import org.junit.Test;
//import org.junit.runner.RunWith;

import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.services.Configurator;
import com.nuubit.sdk.utils.DateTimeUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.cglib.core.Constants;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
@RunWith(MockitoJUnitRunner.class)
public class ApplicationTestApplication extends ApplicationTestCase<NuubitApplication> {
    private static final String TAG = "newConfigNull";
    private NuubitApplication application;

    public ApplicationTestApplication() {
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
    public void testStartConfugurator() throws Exception {
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
        iRes.putExtra(NuubitConstants.CONFIG, "{'field': 'field'");
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
