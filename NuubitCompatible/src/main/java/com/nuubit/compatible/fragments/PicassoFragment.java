package com.nuubit.compatible.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuubit.compatible.R;
import com.nuubit.compatible.picasso.AndroidVersion;
import com.nuubit.compatible.picasso.DataAdapter;

import java.util.ArrayList;
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

public class PicassoFragment extends Fragment {

    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
            //"Nugat"
    };

    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    private String[] urls = {
            "http://nofilmschool.com/sites/default/files/styles/facebook/public/donut.jpg?itok=2KIhsE9n",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj-xPC84IPTAhUBkSwKHa3cDOsQjRwIBw&url=https%3A%2F%2Fteckhamsterblog.wordpress.com%2F2010%2F12%2F01%2Fhistory-of-google-android%2F&psig=AFQjCNEaIheyYHYdvzOoCNBvT0XB2Avnew&ust=1491153128931561",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj02fbX4IPTAhVBWiwKHYupDTEQjRwIBw&url=http%3A%2F%2Fwww.ppcgeeks.com%2Ftag%2Ffroyo%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNEpX0eYL-xZGgqu4fTR0Ovi71vcJw&ust=1491153193235471",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjvwJaB4YPTAhWHlSwKHZM6BasQjRwIBw&url=http%3A%2F%2Fwww.hxcorp.com.vn%2Fnews%2F350-medical-uses-for-ginger.html&bvm=bv.151325232,d.bGg&psig=AFQjCNEIIhYUU2iHIsnDv4_qo_7dvwQ2TQ&ust=1491153250519769",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiVo_KY4YPTAhXBkywKHYewBRIQjRwIBw&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F368591550728356017%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNH21Ln_CgP5_hyy41RmSqhgBDovUQ&ust=1491153317464086",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjCsJ-w4YPTAhVJkywKHa2tDrEQjRwIBw&url=http%3A%2F%2Fandroid.mobile-review.com%2Fnews%2F2280%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNE2LOwfUQ22UM82IuKQVBZ_f_gsqQ&ust=1491153376416909",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiPi4fD4YPTAhXIBSwKHSYdAc8QjRwIBw&url=http%3A%2F%2Fwww.talkandroid.com%2F121456-how-to-compile-android-4-1-jelly-bean-on-ubuntu%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNEmqTnA1_3pFAzNVxUp29r2jlQgdg&ust=1491153417856039",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiSsZHc4YPTAhVEkCwKHZ-EBf0QjRwIBw&url=http%3A%2F%2Fwww.zdnet.com%2Farticle%2Fgoogle-unloads-more-android-treats-kitkat-nexus-5%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNGYbbgr9c9_YkoPy-BlpwQdkWeLvQ&ust=1491153464085547",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiMi4D54YPTAhXFjSwKHXWSC1UQjRwIBw&url=http%3A%2F%2Fhuaweinews.com%2F2014%2F10%2Fandroid-5-lollipop%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNHLdBSR0IL6tuhp0YhDIZ2X6RtyGQ&ust=1491153519688157",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwi206iM4oPTAhWG2SwKHfHlDXsQjRwIBw&url=http%3A%2F%2Fblog.rifix.net%2Fblog%2Ftag%2Fgoogle%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNH0GdHgPM5Kif4Zg5c7NAtBwgumKQ&ust=1491153569256374",
            "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwi2ivWz4oPTAhUJCCwKHUCDCi4QjRwIBw&url=http%3A%2F%2Fweekend.sunstar.com.ph%2Fblog%2F2016%2F07%2F10%2Fgoogle-serves-nougat-fans-android-software%2F&bvm=bv.151325232,d.bGg&psig=AFQjCNFMn0QxmW-ujCDP1mblE_9QDJe64A&ust=1491153641677182"
    };

    private OnPicassoListener listener;

    public PicassoFragment() {
    }

    public static PicassoFragment newInstance() {
        PicassoFragment fragment = new PicassoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picasso, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle data) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(getActivity(), androidVersions, listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) throws RuntimeException {
        super.onAttach(context);
        if (context instanceof OnPicassoListener) {
            listener = (OnPicassoListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnPicassoListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private ArrayList prepareData() {

        ArrayList android_version = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    public interface OnPicassoListener {
        void onPicassoLoad(String url);
    }
}
