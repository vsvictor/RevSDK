package com.rev.wather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rev.wather.R;

public class SixteenDays extends Fragment {

    private OnSixteenDaysListener listener;

    public SixteenDays() {
    }

    public static SixteenDays newInstance() {
        SixteenDays fragment = new SixteenDays();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sixteen_days, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onSixteetDays(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSixteenDaysListener) {
            listener = (OnSixteenDaysListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnSixteenDaysListener {
        void onSixteetDays(Uri uri);
    }
}
