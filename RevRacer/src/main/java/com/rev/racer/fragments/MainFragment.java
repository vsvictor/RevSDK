package com.rev.racer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rev.racer.R;
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

public class MainFragment extends Fragment {
    //private Typeface main;
    private LinearLayout llBackground;
    private TextView tvNativeMobileApp;
    private TextView tvWebApp;

    private Animation racer;
    //private Animation items;
    private Animation exit;
    private Animation exit_item;
    private OnMainListener listener;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //main = Typeface.createFromAsset(getActivity().getAssets(), "fonts/crochet.ttf");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_start);
        racer.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //tvNativeMobileApp.startAnimation(items);
                //tvWebApp.startAnimation(items);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        //items = AnimationUtils.loadAnimation(getActivity(), R.anim.items);
        exit = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_change);
        exit_item = AnimationUtils.loadAnimation(getActivity(), R.anim.items_reverse);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llBackground = (LinearLayout) view.findViewById(R.id.llMainFragmentContainer);
        tvNativeMobileApp = (TextView) view.findViewById(R.id.tvNativeMobileApp);
        //tvNativeMobileApp.setTypeface(main);
        tvNativeMobileApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNativeMobile();
            }
        });

        tvWebApp = (TextView) view.findViewById(R.id.tvWebApp);
        //tvWebApp.setTypeface(main);
        tvWebApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWeb();
            }
        });
        llBackground.startAnimation(racer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainListener) {
            listener = (OnMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnMainListener {
        void onNativeMobile();
        void onWeb();
    }
}
