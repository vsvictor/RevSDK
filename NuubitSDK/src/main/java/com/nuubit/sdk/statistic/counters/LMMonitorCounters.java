package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.types.Pair;
import com.nuubit.sdk.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by victor on 12.04.17.
 */

public class LMMonitorCounters extends Counters {

    private long successRequest;
    private long failRequest;
    private long lastSuccessTime;
    private long lastFailTime;
    private String lastFailReason;
    private ArrayBlockingQueue<EnumProtocol> availableProtocol;
    private EnumProtocol currentProtocol;

    public LMMonitorCounters() {
        this.availableProtocol = new ArrayBlockingQueue(NuubitConstants.COUNT_PROTOCOL);
        this.currentProtocol = EnumProtocol.UNDEFINED;
    }

    @Override
    public void save(SharedPreferences share) {
        SharedPreferences.Editor editor = share.edit();
        editor.putLong(NuubitConstants.SUCCESS_COUNT, successRequest);
        editor.putLong(NuubitConstants.FAIL_COUNT, failRequest);
        editor.putLong(NuubitConstants.LAST_TIME_SUCCESS, lastSuccessTime);
        editor.putLong(NuubitConstants.LAST_TIME_FAIL, lastFailTime);
        editor.putString(NuubitConstants.LAST_FAIL_REASON, lastFailReason);
        Set<String> sArr = new HashSet<String>();
        for (EnumProtocol proto : availableProtocol) {
            sArr.add(proto.toString());
        }
        editor.putStringSet(NuubitConstants.AVAILABLE_PROTOCOLS, sArr);
        editor.putString(NuubitConstants.CURRENT_PROTOCOL, currentProtocol.toString());
        editor.commit();
    }

    @Override
    public void load(SharedPreferences share) {
        successRequest = share.getLong(NuubitConstants.SUCCESS_COUNT, 0);
        failRequest = share.getLong(NuubitConstants.FAIL_COUNT, 0);
        lastSuccessTime = share.getLong(NuubitConstants.LAST_TIME_SUCCESS, 0);
        lastFailTime = share.getLong(NuubitConstants.LAST_TIME_FAIL, 0);
        lastFailReason = share.getString(NuubitConstants.LAST_FAIL_REASON, NuubitConstants.UNDEFINED);
        Set<String> sArr = share.getStringSet(NuubitConstants.AVAILABLE_PROTOCOLS, new HashSet<String>());
        for (String s : sArr) {
            availableProtocol.add(EnumProtocol.fromString(s));
        }
        currentProtocol = EnumProtocol.fromString(share.getString(NuubitConstants.CURRENT_PROTOCOL, NuubitConstants.UNDEFINED));
    }

    public void addSuccess(long sum) {
        successRequest += sum;
    }

    public void addFail(long sum) {
        failRequest += sum;
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        StringBuilder builder = new StringBuilder();
        for (EnumProtocol s : availableProtocol) {
            builder.append(s.toString());
            builder.append(", ");
        }
        result.add(new Pair("Available protocols: ", builder.toString()));
        result.add(new Pair("Current protocol:", currentProtocol.toString()));
        result.add(new Pair("All monitoring request", String.valueOf(getAllRequest())));
        result.add(new Pair("Success monitoring request", String.valueOf(getSuccessRequest())));
        result.add(new Pair("Fail monitoring request", String.valueOf(getFailRequest())));
        //result.add(new Pair("Time of last success request", String.valueOf(getLastSuccessTime())));
        //result.add(new Pair("Time of last fail request", String.valueOf(getLastFailTime())));
        result.add(new Pair("Time of last success request", DateTimeUtil.dateToString(NuubitApplication.getInstance(), getLastSuccessTime())));
        result.add(new Pair("Time of last fail request", DateTimeUtil.dateToString(NuubitApplication.getInstance(), getLastFailTime())));
        result.add(new Pair("Reason of last fail request", getLastFailReason()));
        return result;
    }

    public ArrayBlockingQueue<EnumProtocol> getAvailableProtocol() {
        return availableProtocol;
    }

    public void setAvailableProtocol(ArrayBlockingQueue<EnumProtocol> availableProtocol) {
        this.availableProtocol = availableProtocol;
    }

    public EnumProtocol getCurrentProtocol() {
        return currentProtocol;
    }

    public void setCurrentProtocol(EnumProtocol currentProtocol) {
        this.currentProtocol = currentProtocol;
    }

    public long getSuccessRequest() {
        return successRequest;
    }

    public long getFailRequest() {
        return failRequest;
    }

    public long getAllRequest() {
        return getSuccessRequest() + getFailRequest();
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
