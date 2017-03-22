package com.rev.racer.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rev.racer.R;

public class MainFragment extends Fragment {
    private Typeface main;
    private TextView tvNativeMobileApp;
    private TextView tvWebApp;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = Typeface.createFromAsset(getActivity().getAssets(), "fonts/rock.ttf");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        tvNativeMobileApp = (TextView) view.findViewById(R.id.tvNativeMobileApp);
        tvWebApp = (TextView) view.findViewById(R.id.tvWebApp);
    }
}
