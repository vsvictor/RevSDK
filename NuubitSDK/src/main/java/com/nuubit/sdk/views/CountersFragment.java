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

public class CountersFragment extends Fragment {
    private Statistic stat;

    public CountersFragment() {
    }

    public static CountersFragment newInstance() {
        CountersFragment fragment = new CountersFragment();
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
        View view = inflater.inflate(R.layout.fragment_counters, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        RecyclerView rvMain = (RecyclerView) view.findViewById(R.id.rvConfig);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMain.setAdapter(new PairAdapter(getActivity(), NuubitApplication.getInstance().getConfigCounters().toArray(), PairAdapter.listener));
/*
        RecyclerView rvRequests = (RecyclerView) view.findViewById(R.id.rvRequests);
        rvRequests.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRequests.setAdapter(new PairAdapter(NuubitApplication.getInstance(), NuubitApplication.getInstance().getRequestCounter().toArray(), PairAdapter.listener));
*/
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
