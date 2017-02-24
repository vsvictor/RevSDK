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

public class GeoIP {
    private Context context;

    private String countryCodeTwoLetter;
    private String regionName;
    private String cityName;

    public GeoIP(Context context){
        this.context = context;

        countryCodeTwoLetter = countryCodeTwoLetter();
        regionName = regionName();
        cityName = cityName();
    }

    private String countryCodeTwoLetter(){return Constants.UA;}
    private String regionName(){return  Constants.Dnipro;}
    private String cityName(){return  Constants.Dnipro;}

    public String getCountryCodeTwoLetter() {
        return countryCodeTwoLetter;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }
}
