package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.types.Pair;
import com.nuubit.sdk.utils.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by victor on 11.04.17.
 */

public class ConfigCounters extends Counters {
    private long pullSuccess;
    private long pullFail;
    private long timeLastSuccess;
    private long timeLastFail;
    private String reasonLastFail;
    private boolean abOn;
    private OperationMode realMode;

    public ConfigCounters() {
        this.pullSuccess = 0;
        this.pullFail = 0;
        this.timeLastSuccess = 0;
        this.timeLastFail = 0;
        this.reasonLastFail = "";
        this.abOn = false;
        this.realMode = OperationMode.off;
    }

    public void addSuccessConfig() {
        this.pullSuccess++;
    }

    public void addFailConfig() {
        this.pullFail++;
    }

    public long getPullSize() {
        return getPullSuccess() + getPullFail();
    }

    public long getPullSuccess() {
        return pullSuccess;
    }

    public long getPullFail() {
        return pullFail;
    }

    public long getTimeLastSuccess() {
        return timeLastSuccess;
    }

    public void setTimeLastSuccess(long timeLastSuccess) {
        this.timeLastSuccess = timeLastSuccess;
    }

    public long getTimeLastFail() {
        return timeLastFail;
    }

    public void setTimeLastFail(long timeLastFail) {
        this.timeLastFail = timeLastFail;
    }

    public String getReasonLastFail() {
        return reasonLastFail;
    }

    public void setReasonLastFail(String reasonLastFail) {
        this.reasonLastFail = reasonLastFail;
    }

    public boolean isAbOn() {
        return abOn;
    }

    public void setAbOn(boolean abOn) {
        this.abOn = abOn;
    }

    public OperationMode getRealMode() {
        return realMode;
    }

    public void setRealMode(OperationMode realMode) {
        this.realMode = realMode;
    }

    @Override
    public void save(SharedPreferences share) {
        SharedPreferences.Editor editor = share.edit();
        editor.putLong(NuubitConstants.PULL_SUCCESS, getPullSuccess());
        editor.putLong(NuubitConstants.PULL_FAIL, getPullFail());
        editor.putLong(NuubitConstants.LAST_TIME_SUCCESS, getTimeLastSuccess());
        editor.putLong(NuubitConstants.LAST_TIME_FAIL, getTimeLastFail());
        editor.putString(NuubitConstants.LAST_FAIL_REASON, getReasonLastFail());
        editor.putString(NuubitConstants.REAL_MODE, getRealMode().toString());
        editor.commit();
    }

    @Override
    public void load(SharedPreferences share) {
        pullSuccess = share.getLong(NuubitConstants.PULL_SUCCESS, 0);
        pullFail = share.getLong(NuubitConstants.PULL_FAIL, 0);
        timeLastSuccess = share.getLong(NuubitConstants.LAST_TIME_SUCCESS, 0);
        timeLastFail = share.getLong(NuubitConstants.LAST_TIME_FAIL, 0);
        reasonLastFail = share.getString(NuubitConstants.LAST_FAIL_REASON, "");
        realMode = OperationMode.fromString(share.getString(NuubitConstants.REAL_MODE, "off"));
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("totalPull", String.valueOf(getPullSize())));
        result.add(new Pair("sucessPull", String.valueOf(getPullSuccess())));
        result.add(new Pair("failFull", String.valueOf(getPullFail())));
        result.add(new Pair("lastSuccessTime", DateTimeUtil.dateToString(NuubitApplication.getInstance().getApplicationContext(), getTimeLastSuccess())));
        result.add(new Pair("lastFailTime", DateTimeUtil.dateToString(NuubitApplication.getInstance().getApplicationContext(), getTimeLastFail())));
        result.add(new Pair("lastReasonFail", String.valueOf(getReasonLastFail())));
        result.add(new Pair("realMode", String.valueOf(getRealMode())));
        String sOn = isAbOn() ? "On, " : "Off ";
        String sMode = "";
        if (isAbOn()) {
            sMode = NuubitApplication.getInstance().getABTester().isAMode() ? "A" : "B";
        }
        result.add(new Pair("A/B testing", sOn + sMode + (isAbOn() ? " mode" : "")));
        return result;
    }
}
