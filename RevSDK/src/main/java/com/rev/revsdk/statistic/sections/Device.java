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
    private int cpu_cores;
    private String cpu_freq;
    private float cpu_number;
    private int cpu_sub;
    private String device;
    private float hight;
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


    public Device(Context context) {
        this.context = context;

        batt_cap = battCap();
        batt_status = battStatus();
        batt_tech = battTech();
        batt_temp = battTemp();
        batt_volt = battVolt();
        brand = brand();
        cpu = cpu();
        cpu_cores = cpuCores();
        cpu_freq = cpuFreq();
        cpu_number = cpuNumber();
        cpu_sub = cpuSub();
        device = device();
        hight = hight();
        width = width();
        iccid = ICCID();
        imei = IMEI();
        imsi = IMSI();
        manufacture = manufacture();
        meis = MEIS();
        os = OS();
        phone_number = phoneNumber();
        radio_serial = radioSerial();
        serial_number = serialNumber();
        uuid = UUID();

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

    private int cpuCores() {
        return 0;
    }

    private String cpuFreq() {
        return Constants.undefined;
    }

    private float cpuNumber() {
        return 0;
    }

    private int cpuSub() {
        return 0;
    }

    private String device() {
        return Constants.undefined;
    }

    private float hight() {
        return 0;
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

    private String OS() {
        return Constants.undefined;
    }

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

    public int getCPUCores() {
        return cpu_cores;
    }

    public String getCPUFreq() {
        return cpu_freq;
    }

    public float getCPUNumber() {
        return cpu_number;
    }

    public int getCPUSub() {
        return cpu_sub;
    }

    public String getDevice() {
        return device;
    }

    public float getHight() {
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
}
