package com.nuubit.racer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.nuubit.racer.ConsistentlyWebActivity;
import com.nuubit.racer.Const;
import com.nuubit.racer.ParalellyActivity;
import com.nuubit.racer.ParalellyWebActivity;
import com.nuubit.racer.R;
import com.nuubit.racer.ResultActivity;

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

public class SeriesFragment extends Fragment {
    private static final String TAG = SeriesFragment.class.getSimpleName();
    private LinearLayout llContainer;
    private Animation racer;
    private RecyclerView rvResult;

    private int mode;

    private OnSeriesListener listener;

    public SeriesFragment() {
    }

    public static SeriesFragment newInstance(int mode) {
        SeriesFragment fragment = new SeriesFragment();
        Bundle data = new Bundle();
        data.putInt(Const.MODE, mode);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getArguments().getInt(Const.MODE, Const.MODE_CONSISTENTLY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_series, container, false);
        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_task);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llContainer = (LinearLayout) view.findViewById(R.id.llContainer);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mode == Const.MODE_CONSISTENTLY) {
            rvResult.setAdapter(((ResultActivity) getActivity()).getAdapter());
        } else if(mode == Const.MODE_UNLIM){
            rvResult.setAdapter(((ResultActivity) getActivity()).getAdapter());
        } else if(mode == Const.MODE_PARALELLY){
            rvResult.setAdapter(((ParalellyActivity) getActivity()).getAdapter());
        }else if(mode == Const.MODE_WEB_CONSISTENTLY){
            rvResult.setAdapter(((ConsistentlyWebActivity) getActivity()).getAdapter());
        }else if(mode == Const.MODE_WEB_PARALELLY){
            rvResult.setAdapter(((ParalellyWebActivity) getActivity()).getAdapter());
        }
        rvResult.setNestedScrollingEnabled(false);
        if (mode == Const.MODE_CONSISTENTLY) {
            ((ResultActivity) getActivity()).startTask();
        } else if(mode == Const.MODE_UNLIM){
            ((ResultActivity) getActivity()).startTask();
        } else if(mode == Const.MODE_WEB_PARALELLY){
            ((ParalellyWebActivity) getActivity()).start();
        } else if(mode == Const.MODE_WEB_CONSISTENTLY){
            ((ConsistentlyWebActivity) getActivity()).start();
        }
        llContainer.startAnimation(racer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSeriesListener) {
            listener = (OnSeriesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSeriesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnSeriesListener {
        void onSeries(Uri uri);
    }
}
