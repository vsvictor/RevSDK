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
        /*
        PairFragment main = PairFragment.newInstance(stat);
        PairFragment carrier = PairFragment.newInstance(stat.getCarrier());
        PairFragment device = PairFragment.newInstance(stat.getDevice());
        PairFragment events = PairFragment.newInstance(stat.getEvents());
        PairFragment location = PairFragment.newInstance(stat.getLocation());
        PairFragment network = PairFragment.newInstance(stat.getNetwork());
        PairFragment requests = PairFragment.newInstance(stat.getRequests());
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
        */

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
/*
    public static class ListUtils {
        public static void setDynamicHeight(RecyclerView mListView) {
            PairRecyclerViewAdapter mListAdapter = (PairRecyclerViewAdapter) mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getItemCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
*/
}
