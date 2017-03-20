package com.rev.revbanshee;

import com.rev.sdk.RevApplication;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by victor on 12.02.17.
 */
@ReportsCrashes(mailTo = "android-crash-reports@nuubit.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.SILENT, resToastText = R.string.crash_toast_text)
public class BansheeApplication extends RevApplication {
    private static final String TAG = BansheeApplication.class.getSimpleName();

    @Override
    public void onCreate(){
        super.onCreate();
        ACRA.init(this);
    }
}
