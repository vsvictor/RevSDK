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
/*
    private final TelephonyManager tm = null;

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
*/
    public Carrier(Context context){
        this.context = context;
/*
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperator();
        tm.listen(new RSSIPhoneStateListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        countryCode = tm.getNetworkCountryIso();
        deviceID = tm.getDeviceId();
        mcc = networkOperator.substring(0, 3);
        mnc = networkOperator.substring(3);
        netOperator = tm.getNetworkOperatorName();
        netType = networkType2String(tm.getNetworkType());
        phoneType = phoneType2String(tm.getPhoneType());
        rssi = -10000;
        rssiAverage = -10000;
        rssiBest = -10000;
        signalType = getNetworkType(context);
        simOperator = tm.getSimOperatorName();
        //List<NeighboringCellInfo> inf = tm.getNeighboringCellInfo();
        //for(NeighboringCellInfo i : inf){

        //}
        this.shortTower = "undefined";
        this.longTower = "undefined";
*/
    }
/*
    public String getCountryCode() {
        return countryCode.toUpperCase();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getMCC() {
        return mcc;
    }

    public String getMNC() {
        return mnc;
    }

    public String getNetOperator(){
        return netOperator;
    }
    public String getNetType() {
        return netType;
    }
    public String getPhoneType() {
        return phoneType;
    }
    public float getRSSI() {
        return rssi;
    }
    public float getRSSIAverage() {
        return rssiAverage;
    }
    public float getRSSIBest() {
        return rssiBest;
    }
    public String getNetworkType(){
        return signalType;
    }
    public String getSimOperator() {
        return simOperator;
    }

    public String getTowerLong(){
        GsmCellLocation cl = (GsmCellLocation) tm.getCellLocation();
        return String.valueOf(cl.getCid());

    }
    public String getTowerShort(){
        GsmCellLocation cl = (GsmCellLocation) tm.getCellLocation();
        return String.valueOf(cl.getCid());
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
    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info==null || !info.isConnected()) return context.getResources().getString(R.string.no_network); //not connected
        if(info.getType() == ConnectivityManager.TYPE_WIFI) return context.getResources().getString(R.string.wifi);
        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
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
    }
    public class RSSIPhoneStateListener extends PhoneStateListener {
        private final String TAG = RSSIPhoneStateListener.class.getSimpleName();
        private ArrayList<Float> rssiArr = new ArrayList<>();
        public RSSIPhoneStateListener() {
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
*/
}
