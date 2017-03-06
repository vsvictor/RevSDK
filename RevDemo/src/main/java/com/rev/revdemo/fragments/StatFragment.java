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
        PairFragment main = PairFragment.newInstance(stat);
        PairFragment carrier = PairFragment.newInstance(stat.getCarrier());
        PairFragment device = PairFragment.newInstance(stat.getDevice());
        //PairFragment events = PairFragment.newInstance(stat.getEvents());
        PairFragment location = PairFragment.newInstance(stat.getLocation());
        PairFragment network = PairFragment.newInstance(stat.getNetwork());
        //PairFragment requests = PairFragment.newInstance(stat.getRequests());
        PairFragment wifi = PairFragment.newInstance(stat.getWifi());
        getActivity().getFragmentManager().beginTransaction()
                .add(R.id.llContainer, main)
                .add(R.id.llContainer, carrier)
                .add(R.id.llContainer, device)
                //.add(R.id.llContainer, events)
                .add(R.id.llContainer, location)
                .add(R.id.llContainer, network)
                //.add(R.id.llContainer, requests)
                .add(R.id.llContainer, wifi)
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
