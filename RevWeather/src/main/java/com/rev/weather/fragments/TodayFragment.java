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

public class TodayFragment extends Fragment {
    private Root root;

    private TextView tvToday;

    private OnTodayListener listener;

    public TodayFragment() {
    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
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
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle data) {
        tvToday = (TextView) view.findViewById(R.id.tvToday);
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
        tvToday.setText(root.toString());
    }

    public interface OnTodayListener {
        void onTodayWeather(Uri uri);
    }
}
