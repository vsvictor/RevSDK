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

import com.rev.revsdk.Constants;

public class Device {
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

    private float battCap() {
        return 0;
    }

    private String battStatus() {
        return Constants.undefined;
    }

    private String battTech() {
        return Constants.undefined;
    }

    private String battTemp() {
        return Constants.undefined;
    }

    private String battVolt() {
        return Constants.undefined;
    }

    private String brand() {
        return Constants.undefined;
    }

    private String cpu() {
        return Constants.undefined;
    }

    private String cpuCores() {
        return Constants.s_zerro;
    }

    private String cpuFreq() {
        return Constants.undefined;
    }

    private String cpuNumber() {
        return Constants.s_zerro;
    }

    private String cpuSub() {
        return Constants.s_zerro;
    }

    private String device() {
        return "Nexus 6P";
    }

    private String hight() {
        return Constants.s_zerro;
    }

    private String width() {
        return Constants.undefined;
    }

    private String ICCID() {
        return Constants.undefined;
    }

    private String IMEI() {
        return Constants.undefined;
    }

    private String IMSI() {
        return Constants.undefined;
    }

    private String manufacture() {
        return Constants.undefined;
    }

    private String MEIS() {
        return Constants.undefined;
    }

    private String OS() {return "Android";}

    private String phoneNumber() {
        return Constants.undefined;
    }

    private String radioSerial() {
        return Constants.undefined;
    }

    private String serialNumber() {
        return Constants.undefined;
    }

    private String UUID() {
        return Constants.undefined;
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
}
