package com.nuubit.sdk.statistic.sections;

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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.types.Pair;
import com.nuubit.sdk.utils.DeviceUuidFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Device extends Data implements Parcelable {
    private Context context;

    private double batt_cap;
    private String batt_status;
    private String batt_tech;
    private String batt_temp;
    private String batt_volt;
    private String brand;
    private String cpu;
    private String cpu_cores;
    private String cpu_freq;
    private String cpu_number;
    private String cpu_sub;
    private String device;
    private String hight;
    private String width;
    private String iccid;
    private String imei;
    private String imsi;
    private String manufacture;
    private String meis;
    private String os;
    private String phone_number;
    private String radio_serial;
    private String serial_number;
    private String uuid;
    private String osName;
    private String osVersion;
    private String model;

    public Device(Context context) {
        this.context = context;

        this.batt_cap = battCap();
        this.batt_status = battStatus();
        this.batt_tech = battTech();
        this.batt_temp = battTemp();
        this.batt_volt = battVolt();
        this.brand = brand();
        this.cpu = cpu();
        this.cpu_cores = cpuCores();
        this.cpu_freq = cpuFreq();
        this.cpu_number = cpuNumber();
        this.cpu_sub = cpuSub();
        this.device = device();
        this.hight = hight();
        this.width = width();
        this.iccid = ICCID();
        this.imei = IMEI();
        this.imsi = IMSI();
        this.manufacture = manufacture();
        this.meis = MEIS();
        this.os = OS();
        this.phone_number = phoneNumber();
        this.radio_serial = radioSerial();
        this.serial_number = serialNumber();
        this.uuid = UUID();
        this.osName = nameOS();
        this.osVersion = versionOS();
        this.model = model();
    }

    protected Device(Parcel in) {
        batt_cap = in.readFloat();
        batt_status = in.readString();
        batt_tech = in.readString();
        batt_temp = in.readString();
        batt_volt = in.readString();
        brand = in.readString();
        cpu = in.readString();
        cpu_cores = in.readString();
        cpu_freq = in.readString();
        cpu_number = in.readString();
        cpu_sub = in.readString();
        device = in.readString();
        hight = in.readString();
        width = in.readString();
        iccid = in.readString();
        imei = in.readString();
        imsi = in.readString();
        manufacture = in.readString();
        meis = in.readString();
        os = in.readString();
        phone_number = in.readString();
        radio_serial = in.readString();
        serial_number = in.readString();
        uuid = in.readString();
        osName = in.readString();
        osVersion = in.readString();
        osVersion = in.readString();
    }

    @Override
    public ArrayList<Pair> toArray() {
        ArrayList<Pair> result = new ArrayList<Pair>();
        result.add(new Pair("batt_cap", String.valueOf(batt_cap)));
        result.add(new Pair("batt_status", String.valueOf(batt_status)));
        result.add(new Pair("batt_tech", String.valueOf(batt_tech)));
        result.add(new Pair("batt_temp", String.valueOf(batt_temp)));
        result.add(new Pair("batt_volt", String.valueOf(batt_volt)));
        result.add(new Pair("brand", String.valueOf(brand)));
        result.add(new Pair("cpu", String.valueOf(cpu)));
        result.add(new Pair("cpu_cores", String.valueOf(cpu_cores)));
        result.add(new Pair("cpu_freq", String.valueOf(cpu_freq)));
        result.add(new Pair("cpu_number", String.valueOf(cpu_number)));
        result.add(new Pair("cpu_sub", String.valueOf(cpu_sub)));
        result.add(new Pair("device", String.valueOf(device)));
        result.add(new Pair("hight", String.valueOf(hight)));
        result.add(new Pair("width", String.valueOf(width)));
        result.add(new Pair("iccid", String.valueOf(iccid)));
        result.add(new Pair("imei", String.valueOf(imei)));
        result.add(new Pair("imsi", String.valueOf(imsi)));
        result.add(new Pair("manufacture", String.valueOf(manufacture)));
        result.add(new Pair("meis", String.valueOf(meis)));
        result.add(new Pair("os", String.valueOf(os)));
        result.add(new Pair("phone_number", String.valueOf(phone_number)));
        result.add(new Pair("radio_serial", String.valueOf(radio_serial)));
        result.add(new Pair("serial_number", String.valueOf(serial_number)));
        result.add(new Pair("uuid", String.valueOf(uuid)));
        result.add(new Pair("osName", String.valueOf(osName)));
        result.add(new Pair("osVersion", String.valueOf(osVersion)));
        result.add(new Pair("osVersion", String.valueOf(osVersion)));
        return result;
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    private double battCap() {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        return (int) (batteryPct * 100);
    }

    private String battStatus() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return NuubitConstants.UNDEFINED;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return NuubitConstants.CHARGING;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return NuubitConstants.DISCHARGING;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return NuubitConstants.NOT_CHARGING;
            case BatteryManager.BATTERY_STATUS_FULL:
                return NuubitConstants.FULL;
            default:
                return NuubitConstants.UNDEFINED;
        }
    }

    private String battTech() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, ifilter);
        return intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
    }

    private String battTemp() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, ifilter);
        return String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0));
    }

    private String battVolt() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, ifilter);
        return String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0));
    }

    private String brand() {
        return Build.BRAND;
    }

    private String cpu() {
        return Build.CPU_ABI;
    }

    private String cpuCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles((FileFilter) new CpuFilter());
            return String.valueOf(files.length);
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    private String cpuFreq() {
        String cpuMaxFreq = "";
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            cpuMaxFreq = reader.readLine();
            reader.close();
            return cpuMaxFreq;
        } catch (FileNotFoundException e) {
            return NuubitConstants.UNDEFINED;
        } catch (IOException e) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String cpuNumber() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

    private String cpuSub() {
        return NuubitConstants.S_ZERO;
    }

    private String device() {
        return Build.MODEL;
    }

    private String hight() {
        DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return String.valueOf(metrics.heightPixels);
    }

    private String width() {
        DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return String.valueOf(metrics.widthPixels);
    }

    private String ICCID() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimSerialNumber();
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String IMEI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String IMSI() {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getSubscriberId();
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String manufacture() {
        return Build.MANUFACTURER;
    }

    private String MEIS() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String OS() {
        return "Android " + Build.VERSION.RELEASE;
    }

    private String phoneNumber() {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phnNo = mTelephonyMgr.getLine1Number();
            if (phnNo == null) {
                phnNo = getNoFromWatsApp();
            }
            return phnNo;
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String getNoFromWatsApp() {
        try {
            AccountManager am = AccountManager.get(context);
            Account[] accounts = am.getAccounts();
            String phoneNumber = "";
            for (Account ac : accounts) {
                String acname = ac.name;
                String actype = ac.type;
                // Take your time to look at all available accounts
                if (actype.equals("com.whatsapp")) {
                    phoneNumber = ac.name;
                }
            }
            return phoneNumber;
        } catch (SecurityException ex) {
            return NuubitConstants.UNDEFINED;
        }
    }

    private String radioSerial() {
        return Build.getRadioVersion();
    }

    private String serialNumber() {
        return Build.SERIAL;
    }

    private String UUID() {
        DeviceUuidFactory getter = new DeviceUuidFactory(context);
        return getter.getDeviceUuid().toString();
    }

    private String nameOS() {
        return "Android";
    }

    private String versionOS() {
        return Build.VERSION.RELEASE;
    }

    private String model() {
        return Build.MODEL;
    }

    public double getBattCap() {
        return batt_cap;
    }

    public String getBattStatus() {
        return batt_status;
    }

    public String getBattTech() {
        return batt_tech;
    }

    public String getBattTemp() {
        return batt_temp;
    }

    public String getBattVolt() {
        return batt_volt;
    }

    public String getBrand() {
        return brand;
    }

    public String getCPU() {
        return cpu;
    }

    public String getCPUCores() {
        return cpu_cores;
    }

    public String getCPUFreq() {
        return cpu_freq;
    }

    public String getCPUNumber() {
        return cpu_number;
    }

    public String getCPUSub() {
        return cpu_sub;
    }

    public String getDevice() {
        return device;
    }

    public String getHight() {
        return hight;
    }

    public String getWidth() {
        return width;
    }

    public String getIccid() {
        return iccid;
    }

    public String getIMEI() {
        return imei;
    }

    public String getIMSI() {
        return imsi;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getMEIS() {
        return meis;
    }

    public String getOS() {
        return os;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getRadioSerial() {
        return radio_serial;
    }

    public String getSerialNumber() {
        return serial_number;
    }

    public String getUUID() {
        return uuid;
    }

    public String getOSName() {
        return osName;
    }

    public String getOSVersion() {
        return osVersion;
    }

    public String getModel() {
        return model;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(batt_cap);
        dest.writeString(batt_status);
        dest.writeString(batt_tech);
        dest.writeString(batt_temp);
        dest.writeString(batt_volt);
        dest.writeString(brand);
        dest.writeString(cpu);
        dest.writeString(cpu_cores);
        dest.writeString(cpu_freq);
        dest.writeString(cpu_number);
        dest.writeString(cpu_sub);
        dest.writeString(device);
        dest.writeString(hight);
        dest.writeString(width);
        dest.writeString(iccid);
        dest.writeString(imei);
        dest.writeString(imsi);
        dest.writeString(manufacture);
        dest.writeString(meis);
        dest.writeString(os);
        dest.writeString(phone_number);
        dest.writeString(radio_serial);
        dest.writeString(serial_number);
        dest.writeString(uuid);
        dest.writeString(osName);
        dest.writeString(osVersion);
        dest.writeString(model);
    }
}
