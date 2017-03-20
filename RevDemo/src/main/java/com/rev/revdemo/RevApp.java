package com.rev.revdemo;

import com.rev.sdk.RevApplication;

/**
 * Created by victor on 04.03.17.
 */
/*
@ReportsCrashes(mailTo = "android-crash-reports@nuubit.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.SILENT, resToastText = R.string.crash_toast_text)
*/
public class RevApp extends RevApplication {
    @Override
    public void onCreate(){
        super.onCreate();
        //ACRA.init(this);
    }

}
