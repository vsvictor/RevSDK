package com.rev.weather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rev.weather.R;
import com.rev.weather.model.Root;
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

public class RetrofitFragment extends Fragment {
    private Root root;

    private TextView tvWeather;
    private TextView tvCurrTemp;
    private TextView tvHumidity;
    private TextView tvPressure;
    private TextView tvWind;

    private OnTodayListener listener;

    public RetrofitFragment() {
    }

    public static RetrofitFragment newInstance() {
        RetrofitFragment fragment = new RetrofitFragment();
        //Bundle data = new Bundle();
        //data.putString("data", sData);
        //fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String s = getArguments().getString("data");
        //Gson g = new Gson();
        //root = g.fromJson(s, Root.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_retrofit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle data) {
        tvWeather = (TextView) view.findViewById(R.id.tvWeather);
        tvCurrTemp = (TextView) view.findViewById(R.id.tvCurTempValue);
        tvHumidity = (TextView) view.findViewById(R.id.tvHumidityValue);
        tvPressure = (TextView) view.findViewById(R.id.tvPressureValue);
        tvWind = (TextView) view.findViewById(R.id.tvWindValue);
    }
    @Override
    public void onAttach(Context context) throws RuntimeException {
        super.onAttach(context);
        if (context instanceof OnTodayListener) {
            listener = (OnTodayListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void updateData(Root r) {
        root = r;
        String header = getActivity().getResources().getString(R.string.weather) + " in " + root.getNameCity();
        tvWeather.setText(header);
        double celsium = Double.parseDouble(root.getMain().getTemp()) - 273.15d;
        tvCurrTemp.setText(String.valueOf(celsium) + " " + (char) 0x00B0 + "C");
        tvHumidity.setText(root.getMain().getHumidity() + " %");
        Double mm = Double.parseDouble(root.getMain().getPressure()) / 1.3332239;
        tvPressure.setText(String.format("%(.2f", mm) + " mm Hg");
        String sWind = root.getWind().getDegree() + (char) 0x00B0 + ", " + root.getWind().getSpeed() + " m/s";
        tvWind.setText(sWind);
    }

    public interface OnTodayListener {
        void onTodayWeather(Uri uri);
    }
}
