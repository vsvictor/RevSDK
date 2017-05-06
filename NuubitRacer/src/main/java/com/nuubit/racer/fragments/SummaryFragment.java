package com.nuubit.racer.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuubit.racer.ConsistentlyWebActivity;
import com.nuubit.racer.Const;
import com.nuubit.racer.ParalellyActivity;
import com.nuubit.racer.ParalellyWebActivity;
import com.nuubit.racer.R;
import com.nuubit.racer.ConsistentlyActivity;

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

public class SummaryFragment extends Fragment {

    private Animation racer;
    private LinearLayout llContainer;
    private TextView tvMin;
    private TextView tvMax;
    private TextView tvAverage;
    private TextView tvMediane;
    private TextView tvStandDeviation;
    private TextView tvMinOrigin;
    private TextView tvMaxOrigin;
    private TextView tvAverageOrigin;
    private TextView tvMedianeOrigin;
    private TextView tvStandDeviationOrigin;
    private TextView tvMethodMode;
    private int mode;

    private OnSummaryListener listener;

    public SummaryFragment() {
    }

    public static SummaryFragment newInstance(int mode) {
        SummaryFragment fragment = new SummaryFragment();
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
        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_task);
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llContainer = (LinearLayout) view.findViewById(R.id.llContainer);
        tvMin = (TextView) view.findViewById(R.id.tvMinValue);
        tvMax = (TextView) view.findViewById(R.id.tvMaxValue);
        tvAverage = (TextView) view.findViewById(R.id.tvAverageValue);
        tvMediane = (TextView) view.findViewById(R.id.tvMedianeValue);
        tvStandDeviation = (TextView) view.findViewById(R.id.tvStandDeviationValue);
        tvMinOrigin = (TextView) view.findViewById(R.id.tvMinOriginValue);
        tvMaxOrigin = (TextView) view.findViewById(R.id.tvMaxOriginValue);
        tvAverageOrigin = (TextView) view.findViewById(R.id.tvAverageOriginValue);
        tvMedianeOrigin = (TextView) view.findViewById(R.id.tvMedianeOriginValue);
        tvStandDeviationOrigin = (TextView) view.findViewById(R.id.tvStandDeviationOriginValue);
        tvMethodMode = (TextView) view.findViewById(R.id.tvRequestsValue);

        if (mode == Const.MODE_CONSISTENTLY) {
            tvMethodMode.setText(((ConsistentlyActivity) getActivity()).getMethodMode());
        } else if(mode == Const.MODE_PARALELLY) {
            tvMethodMode.setText(((ParalellyActivity) getActivity()).getMethodMode());
        } else if(mode == Const.MODE_WEB_CONSISTENTLY){
            tvMethodMode.setText(((ConsistentlyWebActivity) getActivity()).getMethodMode());
        } else if(mode == Const.MODE_WEB_PARALELLY){
            tvMethodMode.setText(((ParalellyWebActivity) getActivity()).getMethodMode());
        }

        llContainer.startAnimation(racer);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSummaryListener) {
            listener = (OnSummaryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSummaryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter ifa = new IntentFilter();
        ifa.addAction(Const.UPDATE_DATA);
        getActivity().registerReceiver(receiver, ifa);
    }

    public interface OnSummaryListener {
        void onSummary(Uri uri);
        void onStopRequests();
    }

    public void updateRequests(int count){
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mode == Const.MODE_CONSISTENTLY) {
                tvMin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTable().min()));
                tvMax.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTable().max()));
                tvAverage.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTable().average()));
                tvMediane.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTable().median()));
                tvStandDeviation.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTable().standDeviation()));
                tvMinOrigin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTableOriginal().min()));
                tvMaxOrigin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTableOriginal().max()));
                tvAverageOrigin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTableOriginal().average()));
                tvMedianeOrigin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTableOriginal().median()));
                tvStandDeviationOrigin.setText(String.valueOf(((ConsistentlyActivity) getActivity()).getTableOriginal().standDeviation()));
            } else if(mode == Const.MODE_PARALELLY){
                tvMin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTable().min()));
                tvMax.setText(String.valueOf(((ParalellyActivity) getActivity()).getTable().max()));
                tvAverage.setText(String.valueOf(((ParalellyActivity) getActivity()).getTable().average()));
                tvMediane.setText(String.valueOf(((ParalellyActivity) getActivity()).getTable().median()));
                tvStandDeviation.setText(String.valueOf(((ParalellyActivity) getActivity()).getTable().standDeviation()));
                tvMinOrigin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTableOriginal().min()));
                tvMaxOrigin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTableOriginal().max()));
                tvAverageOrigin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTableOriginal().average()));
                tvMedianeOrigin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTableOriginal().median()));
                tvStandDeviationOrigin.setText(String.valueOf(((ParalellyActivity) getActivity()).getTableOriginal().standDeviation()));
            }else if(mode == Const.MODE_WEB_CONSISTENTLY){
                tvMin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTable().min()));
                tvMax.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTable().max()));
                tvAverage.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTable().average()));
                tvMediane.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTable().median()));
                tvStandDeviation.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTable().standDeviation()));
                tvMinOrigin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTableOriginal().min()));
                tvMaxOrigin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTableOriginal().max()));
                tvAverageOrigin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTableOriginal().average()));
                tvMedianeOrigin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTableOriginal().median()));
                tvStandDeviationOrigin.setText(String.valueOf(((ConsistentlyWebActivity) getActivity()).getTableOriginal().standDeviation()));
            }else if(mode == Const.MODE_WEB_PARALELLY){
                tvMin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTable().min()));
                tvMax.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTable().max()));
                tvAverage.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTable().average()));
                tvMediane.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTable().median()));
                tvStandDeviation.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTable().standDeviation()));
                tvMinOrigin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTableOriginal().min()));
                tvMaxOrigin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTableOriginal().max()));
                tvAverageOrigin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTableOriginal().average()));
                tvMedianeOrigin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTableOriginal().median()));
                tvStandDeviationOrigin.setText(String.valueOf(((ParalellyWebActivity) getActivity()).getTableOriginal().standDeviation()));
            }
        }
    };
}
