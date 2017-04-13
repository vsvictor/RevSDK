package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.types.Pair;
import com.nuubit.sdk.utils.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by victor on 13.04.17.
 */

public class StatsCounters extends Counters {
    private long statRequestUploaded;
    private long statRequestFailed;
    private long requestCount;
    private long lastSuccessTime;
    private long lastFailTime;
    private String lastFailReason;

    @Override
    public void save(SharedPreferences shared) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putLong(NuubitConstants.STAT_REQUESTS_UPLOADED, statRequestUploaded);
        editor.putLong(NuubitConstants.STAT_REQUESTS_FAILED, statRequestFailed);
        editor.putLong(NuubitConstants.STAT_REQUESTS_COUNT, requestCount);
        editor.putLong(NuubitConstants.STAT_REQUESTS_UPLOADED, statRequestUploaded);
        editor.putLong(NuubitConstants.STAT_LAST_TIME_SUCCESS, lastSuccessTime);
        editor.putLong(NuubitConstants.STAT_LAST_TIME_FAIL, lastFailTime);
        editor.putString(NuubitConstants.STAT_LAST_FAIL_REASON, lastFailReason);
        editor.commit();
    }

    @Override
    public void load(SharedPreferences shared) {
        statRequestUploaded = shared.getLong(NuubitConstants.STAT_REQUESTS_UPLOADED, 0);
        statRequestFailed = shared.getLong(NuubitConstants.STAT_REQUESTS_FAILED, 0);
        requestCount = shared.getLong(NuubitConstants.STAT_REQUESTS_COUNT, 0);
        lastSuccessTime = shared.getLong(NuubitConstants.STAT_LAST_TIME_SUCCESS, 0);
        lastFailTime = shared.getLong(NuubitConstants.STAT_LAST_TIME_FAIL, 0);
        lastFailReason = shared.getString(NuubitConstants.STAT_LAST_FAIL_REASON, "");
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("Total stats request uploaded", String.valueOf(getStatRequestUploaded())));
        result.add(new Pair("Total stats request failed", String.valueOf(getStatRequestFailed())));
        result.add(new Pair("Request count", String.valueOf(getRequestCount())));
        result.add(new Pair("Last time request uploaded", DateTimeUtil.dateToString(NuubitApplication.getInstance(), getLastSuccessTime())));
        result.add(new Pair("Last time request failed", DateTimeUtil.dateToString(NuubitApplication.getInstance(), getLastFailTime())));
        result.add(new Pair("Last reason request failed", getLastFailReason()));
        return result;
    }

    public void addRequestUploaded() {
        statRequestUploaded++;
    }

    public long getStatRequestUploaded() {
        return statRequestUploaded;
    }

    public void setStatRequestUploaded(long statRequestUploaded) {
        this.statRequestUploaded = statRequestUploaded;
    }

    public void addStatRequestFailed() {
        statRequestFailed++;
    }

    public long getStatRequestFailed() {
        return statRequestFailed;
    }

    public void setStatRequestFailed(long statRequestFailed) {
        this.statRequestFailed = statRequestFailed;
    }

    public void addRequestCount(long count) {
        requestCount += count;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public long getLastSuccessTime() {
        return lastSuccessTime;
    }

    public void setLastSuccessTime(long lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    public long getLastFailTime() {
        return lastFailTime;
    }

    public void setLastFailTime(long lastFailTime) {
        this.lastFailTime = lastFailTime;
    }

    public String getLastFailReason() {
        return lastFailReason;
    }

    public void setLastFailReason(String lastFailReason) {
        this.lastFailReason = lastFailReason;
    }
}
