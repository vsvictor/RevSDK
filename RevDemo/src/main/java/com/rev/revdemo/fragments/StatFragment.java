package com.rev.revdemo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rev.revdemo.R;
import com.rev.revdemo.RevApp;
import com.rev.revsdk.statistic.Statistic;

public class StatFragment extends Fragment {
    private Statistic stat;
    public StatFragment() {
    }

    public static StatFragment newInstance() {
        StatFragment fragment = new StatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.stat = new Statistic(RevApp.getInstance());
        int i = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        PairFragment carrier = PairFragment.newInstance(1, stat.getCarrier());
        PairFragment device = PairFragment.newInstance(1, stat.getDevice());
        getActivity().getFragmentManager().beginTransaction()
                .add(R.id.llContainer, carrier)
                .add(R.id.llContainer, device)
                .commit();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
