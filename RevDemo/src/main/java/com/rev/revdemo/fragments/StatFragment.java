package com.rev.revdemo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rev.revdemo.R;

public class StatFragment extends Fragment {

    public StatFragment() {
    }

    public static StatFragment newInstance() {
        StatFragment fragment = new StatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
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
