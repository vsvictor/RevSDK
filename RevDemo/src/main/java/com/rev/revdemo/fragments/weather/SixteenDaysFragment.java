package com.rev.revdemo.fragments.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rev.revdemo.R;

public class SixteenDaysFragment extends Fragment {

    private OnSixteenDaysListener listener;

    public SixteenDaysFragment() {
    }

    public static SixteenDaysFragment newInstance() {
        SixteenDaysFragment fragment = new SixteenDaysFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sixteen_days, container, false);
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
        void onSixteenDaysWeather(Uri uri);
    }
}
