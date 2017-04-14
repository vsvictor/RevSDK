package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.google.android.gms.wallet.PaymentMethodToken;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.protocols.EnumProtocol;
import com.nuubit.sdk.protocols.Protocol;
import com.nuubit.sdk.types.Pair;

import java.util.ArrayList;

/**
 * Created by victor on 14.04.17.
 */

public class ProtocolCounters extends Counters {

    private EnumProtocol protocol;

    private long totalRequest;
    private long totalFailed;
    private long totalKBReceived;
    private long totalKBSent;

    public ProtocolCounters(EnumProtocol protocol){
        this.protocol = protocol;
        this.totalRequest = 0;
        this.totalFailed = 0;
        this.totalKBReceived = 0;
        this.totalKBSent = 0;
    }

    public void addSuccessRequest(){
        totalRequest++;
    }
    public void  addFailRequest(){
        totalFailed++;
    }
    public void addReceive(long size){
        totalKBReceived += size;
    }
    public void  addSent(long size){
        totalKBSent += size;
    }
    @Override
    public void save(SharedPreferences shared) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putLong(NuubitConstants.TOTAL_REQUEST+protocol.name(), totalRequest);
        editor.putLong(NuubitConstants.TOTAL_FAIL+protocol.name(), totalFailed);
        editor.putLong(NuubitConstants.TOTAL_KB_RECEIVED+protocol.name(), totalKBReceived);
        editor.putLong(NuubitConstants.TOTAL_KB_SENT+protocol.name(), totalKBSent);
        editor.commit();
    }
    @Override
    public void load(SharedPreferences shared) {
        totalRequest = shared.getLong(NuubitConstants.TOTAL_REQUEST+protocol.name(), 0);
        totalFailed = shared.getLong(NuubitConstants.TOTAL_FAIL+protocol.name(), 0);
        totalKBReceived = shared.getLong(NuubitConstants.TOTAL_KB_RECEIVED+protocol.name(), 0);
        totalKBSent = shared.getLong(NuubitConstants.TOTAL_KB_SENT+protocol.name(), 0);
    }
    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("Total requests:", String.valueOf(totalRequest)));
        result.add(new Pair("Total fail requests:", String.valueOf(totalFailed)));
        result.add(new Pair("Total received, KB:", String.valueOf(totalKBReceived/1024)));
        result.add(new Pair("Total sent, KB:", String.valueOf(totalKBSent/1024)));
        return result;
    }
}
