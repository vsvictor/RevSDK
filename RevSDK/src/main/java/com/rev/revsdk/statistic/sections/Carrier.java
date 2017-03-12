package com.rev.revsdk.statistic.sections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.rev.revsdk.Constants;
import com.rev.revsdk.R;
import com.rev.revsdk.RevApplication;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.types.Pair;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

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
public class Carrier extends Data implements Parcelable {
    private static final String TAG = Carrier.class.getSimpleName();
    private Context context;

    private static boolean isListened = false;

    private String countryCode;
    private String deviceID;
    private String mcc;
    private String mnc;
    private String netOperator;
    private String netType;
    private static String rssi;
    private static String rssiAverage;
    private static String rssiBest;
    private String signalType;
    private String simOperator;
    private String shortTower;
    private String longTower;

    public Carrier(Context context) {
        this.context = context;
        TelephonyManager tm = null;
        String vNetworkOperator = null;
        try {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            vNetworkOperator = tm.getNetworkOperator();
        } catch (NullPointerException ex) {
            tm = null;
            netOperator = "";
        }
        countryCode = countryCode(tm);
        deviceID = deviceID(tm);
        mcc = MMC(vNetworkOperator);
        mnc = MNC(vNetworkOperator);
        netOperator = netOperator(tm);
        netType = netType(tm);
        if (rssi == null || rssi.isEmpty()) rssi = RSSI();
        if (rssiAverage == null || rssiAverage.isEmpty()) rssiAverage = RSSIAverage();
        if (rssiBest == null || rssiBest.isEmpty()) rssiBest = RSSIBest();
        signalType = networkType(context);
        simOperator = simOperator(tm);
        this.shortTower = towerLong();
        this.longTower = towerShort();
    }

    protected Carrier(Parcel in) {
        String s = in.readString();
        Carrier c = RevSDK.gsonCreate().fromJson(s, Carrier.class);
        this.countryCode = c.getCountryCode();
        this.deviceID = c.getDeviceID();
        this.mcc = c.getMCC();
        this.mnc = c.getMNC();
        this.netOperator = c.getNetOperator();
        this.netType = c.getNetType();
        this.rssi = c.getRSSI();
        this.rssiAverage = c.getRSSIAverage();
        this.rssiBest = c.getRSSIBest();
        this.signalType = c.getNetworkType();
        this.simOperator = c.getSimOperator();
        this.shortTower = c.getTowerShort();
        this.longTower = c.getTowerLong();

    }


    public static final Creator<Carrier> CREATOR = new Creator<Carrier>() {
        @Override
        public Carrier createFromParcel(Parcel in) {
            return new Carrier(in);
        }

        @Override
        public Carrier[] newArray(int size) {
            return new Carrier[size];
        }
    };

    private String countryCode(TelephonyManager tm) {
        try {
            return tm.getNetworkCountryIso();
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }

    }

    public String getCountryCode() {
        return countryCode.toUpperCase();
    }

    private String deviceID(TelephonyManager tm) {
        try {
            return tm.getDeviceId();
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        } catch (SecurityException ex) {
            return Constants.UNDEFINED;
        }
    }

    public String getDeviceID() {
        return deviceID;
    }

    private String MMC(String netOperator) {
        try {
            return netOperator.substring(0, 3);
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }
    }

    public String getMCC() {
        return mcc;
    }

    private String MNC(String netOperator) {
        try {
            return netOperator.substring(3);
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }
    }

    public String getMNC() {
        return mnc;
    }

    private String netOperator(TelephonyManager tm) {
        try {
            return tm.getNetworkOperatorName();
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }
    }

    public String getNetOperator() {
        return netOperator;
    }

    private String netType(TelephonyManager tm) {
        try {
            return networkType2String(tm.getNetworkType());
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }
    }

    public String getNetType() {
        return netType;
    }

    private String RSSI() {
        return Constants.RSSI;
    }

    public String getRSSI() {
        return rssi;
    }

    private String RSSIAverage() {
        return Constants.RSSI;
    }

    public String getRSSIAverage() {
        return rssiAverage;
    }

    private String RSSIBest() {
        return Constants.RSSI;
    }

    public String getRSSIBest() {
        return rssiBest;
    }

    private String networkType(Context context) {
        return getNetworkMobileType(context);
    }

    public String getNetworkType() {
        return signalType;
    }

    private String simOperator(TelephonyManager tm) {
        try {
            return tm.getSimOperatorName();
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }

    }

    public String getSimOperator() {
        return simOperator;
    }

    private String towerLong() {
        return Constants.UNDEFINED;
    }

    public String getTowerLong() {
        return longTower;
    }

    private String towerShort() {
        return Constants.UNDEFINED;
    }

    public String getTowerShort() {
        return shortTower;
    }

    private String networkType2String(int netType) {
        String result = context.getResources().getString(R.string.unknown);
        switch (netType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT: {
                result = "1xRTT";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_CDMA: {
                result = "CDMA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EDGE: {
                result = "EDGE";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EHRPD: {
                result = "EHRPD";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_0: {
                result = "EVDO revision 0";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_A: {
                result = "EVDO revision A";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_B: {
                result = "EVDO revision B";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_GPRS: {
                result = "GPRS";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_GSM: {
                result = "GSM";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSDPA: {
                result = "HSDPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSPA: {
                result = "HSPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSUPA: {
                result = "HSUPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                result = "iDen";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_IWLAN: {
                result = "IWLAN";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {
                result = "LTE";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA: {
                result = "TD_SCDMA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_UMTS: {
                result = "UMTS";
                break;
            }
        }
        return result;
    }

    private String phoneType2String(int phoneType) {
        String result = context.getResources().getString(R.string.unknown);
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_CDMA: {
                result = "CDMA";
                break;
            }
            case TelephonyManager.PHONE_TYPE_GSM: {
                result = "GSM";
                break;
            }
            case TelephonyManager.PHONE_TYPE_SIP: {
                result = "SIP";
                break;
            }
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
        } catch (NullPointerException ex) {
            return Constants.UNDEFINED;
        }
    }

    public static void runRSSIListener() {
        try {
            Looper.prepare();
            TelephonyManager manager = (TelephonyManager) RevApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            manager.listen(new RSSIPhoneStateListener(manager.getNetworkType()), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            isListened = true;
            Looper.loop();
        } catch (NullPointerException ex) {
            isListened = false;
        }
    }

    public static boolean isIsListen() {
        return isListened;
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("countryCode", countryCode));
        result.add(new Pair("deviceID", deviceID));
        result.add(new Pair("mcc", mcc));
        result.add(new Pair("mnc", mnc));
        result.add(new Pair("netOperator", netOperator));
        result.add(new Pair("netType", netType));
        result.add(new Pair("rssi", rssi));
        result.add(new Pair("rssiAverage", rssiAverage));
        result.add(new Pair("rssiBest", rssiBest));
        result.add(new Pair("signalType", signalType));
        result.add(new Pair("simOperator", simOperator));
        result.add(new Pair("shortTower", shortTower));
        result.add(new Pair("longTower", longTower));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryCode);
        dest.writeString(deviceID);
        dest.writeString(mcc);
        dest.writeString(mnc);
        dest.writeString(netOperator);
        dest.writeString(netType);
        dest.writeString(signalType);
        dest.writeString(simOperator);
        dest.writeString(shortTower);
        dest.writeString(longTower);
    }

    public static class RSSIPhoneStateListener extends PhoneStateListener {
        private static int netWorkType;
        private static final String TAG = RSSIPhoneStateListener.class.getSimpleName();
        //private static ArrayList<Float> rssiArr = new ArrayList<>();
        //private static List<Float> rssiArr = Collections.synchronizedList(new ArrayList<Float>());
        private static ArrayBlockingQueue<Float> rssiArr = new ArrayBlockingQueue<Float>(1024, true);

        public RSSIPhoneStateListener(int netWorkType) {
            this.netWorkType = netWorkType;
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            String[] parts = signalStrength.toString().split(" ");
            if (this.netWorkType == TelephonyManager.NETWORK_TYPE_LTE) {
                rssi = String.valueOf(Integer.parseInt(parts[8]) - 140);
            } else {
                if (signalStrength.getGsmSignalStrength() != 99) {
                    rssi = String.valueOf(-113 + 2 * signalStrength.getGsmSignalStrength());
                }
            }
            rssiArr.add(Float.parseFloat(rssi));
            float rssiSum = 0;
            for (float rf : rssiArr) {
                rssiSum += rf;
            }
            if (rssiArr.size() > 0) rssiAverage = String.valueOf(rssiSum / rssiArr.size());
            else rssiAverage = rssi;
            rssiBest = String.valueOf(Math.max(Float.parseFloat(rssi), Float.parseFloat(rssiBest)));
        }
    }
}
