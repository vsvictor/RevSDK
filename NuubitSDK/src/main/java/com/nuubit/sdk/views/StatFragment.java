package com.nuubit.sdk.views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.R;
import com.nuubit.sdk.statistic.Statistic;
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
        this.stat = new Statistic(NuubitApplication.getInstance());
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
        rvMain.setAdapter(new PairAdapter(getActivity(), stat.toArray(), PairAdapter.listener));
        RecyclerView rvCarrier = (RecyclerView) view.findViewById(R.id.rvCarrier);
        rvCarrier.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCarrier.setAdapter(new PairAdapter(getActivity(), stat.getCarrier().toArray(), PairAdapter.listener));
        RecyclerView rvDevice = (RecyclerView) view.findViewById(R.id.rvDevice);
        rvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDevice.setAdapter(new PairAdapter(getActivity(), stat.getDevice().toArray(), PairAdapter.listener));
        RecyclerView rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvEvents.setAdapter(new PairAdapter(getActivity(),stat.getEvents().toArray(), PairAdapter.listener));
        RecyclerView rvLocation = (RecyclerView) view.findViewById(R.id.rvLocation);
        rvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocation.setAdapter(new PairAdapter(getActivity(), stat.getLocation().toArray(), PairAdapter.listener));
        RecyclerView rvNetwork = (RecyclerView) view.findViewById(R.id.rvNetwork);
        rvNetwork.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNetwork.setAdapter(new PairAdapter(getActivity(), stat.getNetwork().toArray(), PairAdapter.listener));
        RecyclerView rvRequests = (RecyclerView) view.findViewById(R.id.rvRequests);
        rvRequests.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRequests.setAdapter(new PairAdapter(NuubitApplication.getInstance(), NuubitApplication.getInstance().getRequestCounter().toArray(), PairAdapter.listener));
        RecyclerView rvWiFi = (RecyclerView) view.findViewById(R.id.rvWiFi);
        rvWiFi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWiFi.setAdapter(new PairAdapter(getActivity(), stat.getWifi().toArray(), PairAdapter.listener));
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
