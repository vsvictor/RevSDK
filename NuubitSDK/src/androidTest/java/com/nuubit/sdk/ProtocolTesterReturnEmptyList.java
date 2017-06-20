package com.nuubit.sdk;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.nuubit.sdk.config.Config;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.utils.DateTimeUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//import org.junit.Test;
//import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
@RunWith(MockitoJUnitRunner.class)
public class ProtocolTesterReturnEmptyList extends ApplicationTestCase<NuubitApplication> {
    private static final String TAG = "newConfigNull";
    private NuubitApplication application;

    public ProtocolTesterReturnEmptyList() {
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
    public void emptyProtocolList() throws Exception {
        Field receiver = null;
        BroadcastReceiver rec = null;
        try {
            receiver = application.getClass().getDeclaredField("testReceiver");
            receiver.setAccessible(true);
            rec = (BroadcastReceiver) receiver.get(application);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Intent iRes = new Intent(NuubitActions.TEST_PROTOCOL_ACTION);
        iRes.putExtra(NuubitConstants.TEST_PROTOCOL,"");
        rec.onReceive(application,iRes);

        Field conf = application.getClass().getDeclaredField("config");
        conf.setAccessible(true);
        Config c = (Config) conf.get(application);

        System.out.println(c);
        Log.d(TAG, DateTimeUtil.dateToString(application, c.getLastUpdate()));

        assertNotNull(c);
        assertEquals(c.getParam().get(0).getOperationMode(), OperationMode.report_only);
    }

}
