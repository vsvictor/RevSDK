package com.rev.revdemo;

import android.content.Context;

import com.rev.revsdk.RevApplication;
import com.rev.revsdk.protocols.Protocol;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by victor on 04.03.17.
 */

@ReportsCrashes(mailTo = "android-crash-reports@nuubit.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.SILENT, resToastText = R.string.crash_toast_text)
public class RevApp extends RevApplication {
    @Override
    public void onCreate(){
        super.onCreate();
        ACRA.init(this);
    }
}
