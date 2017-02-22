package com.rev.revsdk.statistic.sections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.rev.revsdk.Constants;
import com.rev.revsdk.R;

import java.util.ArrayList;
import java.util.List;

/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

public class Carrier {
    private Context context;

    //private TelephonyManager tm = null;

    private String countryCode;
    private String deviceID;
    private String mcc;
    private String mnc;
    private String netOperator;
    private String netType;
    private String phoneType;

    private float rssi;
    private float rssiAverage;
    private float rssiBest;
    private String signalType;
    private String simOperator;
    private String shortTower;
    private String longTower;

    public Carrier(Context context){
        this.context = context;
        TelephonyManager tm = null;
        String vNetworkOperator = null;
        try {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            vNetworkOperator = tm.getNetworkOperator();
        } catch (NullPointerException ex){
            tm = null;
            netOperator = "";
        }

        countryCode = countryCode(tm);

        deviceID = deviceID(tm);
        mcc = MMC(vNetworkOperator);
        mnc = MNC(vNetworkOperator);

        netOperator = netOperator(tm);
        netType = netType(tm);
        phoneType = phoneType(tm);
        rssi = RSSI();
        rssiAverage = RSSIAverage();
        rssiBest = RSSIBest();
        signalType = networkType(context);
        simOperator = simOperator(tm);
        this.shortTower = towerLong();
        this.longTower = towerShort();

        try {
            tm.listen(new RSSIPhoneStateListener(tm), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }catch (NullPointerException ex){}

    }
    private String countryCode(TelephonyManager tm){
        try{
            return tm.getNetworkCountryIso();
        }catch (NullPointerException ex){
            return Constants.undefined;
        }

    }
    public String getCountryCode() {
        return countryCode.toUpperCase();
    }
    private String deviceID(TelephonyManager tm){
        try {
            return tm.getDeviceId();
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getDeviceID() {
        return deviceID;
    }
    private String MMC(String netOperator){
        try {
            return netOperator.substring(0, 3);
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getMCC() {
        return mcc;
    }
    private String MNC(String netOperator){
        try {
            return netOperator.substring(3);
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getMNC() {
        return mnc;
    }
    private String netOperator(TelephonyManager tm){
        try {
            return tm.getNetworkOperatorName();
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getNetOperator(){
        return netOperator;
    }
    private String netType(TelephonyManager tm){
        try {
            return networkType2String(tm.getNetworkType());
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getNetType() {
        return netType;
    }
    private String phoneType(TelephonyManager tm){
        try {
            return phoneType2String(tm.getPhoneType());
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public String getPhoneType() {
        return phoneType;
    }
    private float RSSI(){
        return -10000;
    }
    public float getRSSI() {
        return rssi;
    }
    private float RSSIAverage(){
        return -10000;
    }
    public float getRSSIAverage() {
        return rssiAverage;
    }
    private float RSSIBest(){
        return  -10000;
    }
    public float getRSSIBest() {
        return rssiBest;
    }
    private String networkType(Context context){
        return getNetworkMobileType(context);
    }
    public String getNetworkType(){
        return signalType;
    }
    private String simOperator(TelephonyManager tm){
        try {
            return tm.getSimOperatorName();
        }catch (NullPointerException ex){
            return Constants.undefined;
        }

    }
    public String getSimOperator() {
        return simOperator;
    }
    private String towerLong(){
        return "undefined";
    }
    public String getTowerLong(){
        return longTower;
    }
    private String towerShort(){
        return "undefined";
    }
    public String getTowerShort(){
        return shortTower;
    }

    private String networkType2String(int netType){
        String result = context.getResources().getString(R.string.unknown);
        switch (netType){
            case TelephonyManager.NETWORK_TYPE_1xRTT:{result="1xRTT";break;}
            case TelephonyManager.NETWORK_TYPE_CDMA:{result="CDMA";break;}
            case TelephonyManager.NETWORK_TYPE_EDGE:{result="EDGE";break;}
            case TelephonyManager.NETWORK_TYPE_EHRPD:{result="EHRPD";break;}
            case TelephonyManager.NETWORK_TYPE_EVDO_0:{result="EVDO revision 0";break;}
            case TelephonyManager.NETWORK_TYPE_EVDO_A:{result="EVDO revision A";break;}
            case TelephonyManager.NETWORK_TYPE_EVDO_B:{result="EVDO revision B";break;}
            case TelephonyManager.NETWORK_TYPE_GPRS:{result="GPRS";break;}
            case TelephonyManager.NETWORK_TYPE_GSM:{result="GSM";break;}
            case TelephonyManager.NETWORK_TYPE_HSDPA:{result="HSDPA";break;}
            case TelephonyManager.NETWORK_TYPE_HSPA:{result="HSPA";break;}
            case TelephonyManager.NETWORK_TYPE_HSUPA:{result="HSUPA";break;}
            case TelephonyManager.NETWORK_TYPE_IDEN:{result="iDen";break;}
            case TelephonyManager.NETWORK_TYPE_IWLAN:{result="IWLAN";break;}
            case TelephonyManager.NETWORK_TYPE_LTE:{result="LTE";break;}
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:{result="TD_SCDMA";break;}
            case TelephonyManager.NETWORK_TYPE_UMTS:{result="UMTS";break;}
        }
        return result;
    }
    private String phoneType2String(int phoneType) {
        String result = context.getResources().getString(R.string.unknown);
        switch (phoneType){
            case TelephonyManager.PHONE_TYPE_CDMA:{result = "CDMA";break;}
            case TelephonyManager.PHONE_TYPE_GSM:{result = "GSM";break;}
            case TelephonyManager.PHONE_TYPE_SIP:{result = "SIP";break;}
        }
        return result;
    }
    public static String getNetworkMobileType(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null || !info.isConnected())
                return context.getResources().getString(R.string.no_network); //not connected
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return context.getResources().getString(R.string.wifi);
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int networkType = info.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        return context.getResources().getString(R.string.g2);
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        return context.getResources().getString(R.string.g3);
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        return context.getResources().getString(R.string.g4);
                    default:
                        return context.getResources().getString(R.string.unknown);
                }
            }
            return context.getResources().getString(R.string.unknown);
        }catch (NullPointerException ex){
            return Constants.undefined;
        }
    }
    public class RSSIPhoneStateListener extends PhoneStateListener {
        private TelephonyManager tm;
        private final String TAG = RSSIPhoneStateListener.class.getSimpleName();
        private ArrayList<Float> rssiArr = new ArrayList<>();
        public RSSIPhoneStateListener(TelephonyManager tm) {
            this.tm = tm;
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            String[] parts = signalStrength.toString().split(" ");
            if ( tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){
                rssi = Integer.parseInt(parts[8])-140;
            }
            else{
                if (signalStrength.getGsmSignalStrength() != 99) {
                    rssi = -113 + 2 * signalStrength.getGsmSignalStrength();
                }
            }
            rssiArr.add(rssi);
            float rssiSum = 0;
            for(float rf : rssiArr) rssiSum += rf;
            if(rssiArr.size() > 0) rssiAverage = rssiSum/rssiArr.size();
            else rssiAverage = rssi;
            rssiBest = Math.max(rssi, rssiBest);
            Log.i(TAG, String.valueOf(rssiArr.size()));
        }
    }
}
