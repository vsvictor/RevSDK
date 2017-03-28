package com.rev.racer.fragments;

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
import android.widget.TextView;

import com.rev.racer.Const;
import com.rev.racer.ParalellyActivity;
import com.rev.racer.R;
import com.rev.racer.ResultActivity;

public class SummaryFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
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
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mode == Const.MODE_CONSISTENTLY) {
                tvMin.setText(String.valueOf(((ResultActivity) getActivity()).getTable().min()));
                tvMax.setText(String.valueOf(((ResultActivity) getActivity()).getTable().max()));
                tvAverage.setText(String.valueOf(((ResultActivity) getActivity()).getTable().average()));
                tvMediane.setText(String.valueOf(((ResultActivity) getActivity()).getTable().median()));
                tvStandDeviation.setText(String.valueOf(((ResultActivity) getActivity()).getTable().standDeviation()));
                tvMinOrigin.setText(String.valueOf(((ResultActivity) getActivity()).getTableOriginal().min()));
                tvMaxOrigin.setText(String.valueOf(((ResultActivity) getActivity()).getTableOriginal().max()));
                tvAverageOrigin.setText(String.valueOf(((ResultActivity) getActivity()).getTableOriginal().average()));
                tvMedianeOrigin.setText(String.valueOf(((ResultActivity) getActivity()).getTableOriginal().median()));
                tvStandDeviationOrigin.setText(String.valueOf(((ResultActivity) getActivity()).getTableOriginal().standDeviation()));
            } else {
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
            }
        }
    };
}