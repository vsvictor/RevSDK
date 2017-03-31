package com.rev.racer.fragments;

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

import com.rev.racer.Const;
import com.rev.racer.ParalellyActivity;
import com.rev.racer.R;
import com.rev.racer.ResultActivity;

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
        } else {
            rvResult.setAdapter(((ParalellyActivity) getActivity()).getAdapter());
        }
        rvResult.setNestedScrollingEnabled(false);
        if (mode == Const.MODE_CONSISTENTLY) {
            ((ResultActivity) getActivity()).startTask();
        } else {
            ((ParalellyActivity) getActivity()).startTask();
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
