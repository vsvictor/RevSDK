package com.rev.revdemo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        RecyclerView rvMain = (RecyclerView) view.findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMain.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvCarrier = (RecyclerView) view.findViewById(R.id.rvCarrier);
        rvCarrier.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCarrier.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.getCarrier().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvDevice = (RecyclerView) view.findViewById(R.id.rvDevice);
        rvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDevice.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.getDevice().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvEvents.setAdapter(new PairRecyclerViewAdapter(getActivity(),stat.getEvents().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvLocation = (RecyclerView) view.findViewById(R.id.rvLocation);
        rvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocation.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.getLocation().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvNetwork = (RecyclerView) view.findViewById(R.id.rvNetwork);
        rvNetwork.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNetwork.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.getNetwork().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvRequests = (RecyclerView) view.findViewById(R.id.rvRequests);
        rvRequests.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvRequests.setAdapter(new PairRecyclerViewAdapter(RevApp.getInstance(),stat.getRequests().toArray(), PairRecyclerViewAdapter.listener));
        RecyclerView rvWiFi = (RecyclerView) view.findViewById(R.id.rvWiFi);
        rvWiFi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWiFi.setAdapter(new PairRecyclerViewAdapter(getActivity(), stat.getWifi().toArray(), PairRecyclerViewAdapter.listener));
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
