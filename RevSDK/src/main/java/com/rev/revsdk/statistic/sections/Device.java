package com.rev.revsdk.statistic.sections;

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

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rev.revsdk.Constants;
import com.rev.revsdk.types.Pair;

import java.util.ArrayList;

public class Device extends Data implements Parcelable {
    private Context context;

    private float batt_cap;
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

    private float battCap() {
        return 0;
    }

    private String battStatus() {
        return Constants.UNDEFINED;
    }

    private String battTech() {
        return Constants.UNDEFINED;
    }

    private String battTemp() {
        return Constants.UNDEFINED;
    }

    private String battVolt() {
        return Constants.UNDEFINED;
    }

    private String brand() {
        return Constants.UNDEFINED;
    }

    private String cpu() {
        return Constants.UNDEFINED;
    }

    private String cpuCores() {
        return Constants.S_ZERO;
    }

    private String cpuFreq() {
        return Constants.UNDEFINED;
    }

    private String cpuNumber() {
        return Constants.S_ZERO;
    }

    private String cpuSub() {
        return Constants.S_ZERO;
    }

    private String device() {
        return "Nexus 6P";
    }

    private String hight() {
        return Constants.S_ZERO;
    }

    private String width() {
        return Constants.UNDEFINED;
    }

    private String ICCID() {
        return Constants.UNDEFINED;
    }

    private String IMEI() {
        return Constants.UNDEFINED;
    }

    private String IMSI() {
        return Constants.UNDEFINED;
    }

    private String manufacture() {
        return Constants.UNDEFINED;
    }

    private String MEIS() {
        return Constants.UNDEFINED;
    }

    private String OS() {return "Android";}

    private String phoneNumber() {
        return Constants.UNDEFINED;
    }

    private String radioSerial() {
        return Constants.UNDEFINED;
    }

    private String serialNumber() {
        return Constants.UNDEFINED;
    }

    private String UUID() {
        return Constants.UNDEFINED;
    }

    private String nameOS() {
        return "Android";
    }

    private String versionOS() {
        return "7.1.1";
    }

    private String model() {
        return "Nexus 6P";
    }

    public float getBattCap() {
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
        dest.writeFloat(batt_cap);
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
