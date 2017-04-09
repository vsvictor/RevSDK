package com.rev.racer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rev.racer.Const;
import com.rev.racer.NuubitApp;
import com.rev.racer.R;

import okhttp3.HttpUrl;
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

public class TaskFragment extends Fragment {
    private static final String TAG = TaskFragment.class.getSimpleName();
    private Typeface main;
    private LinearLayout llBackground;
    private Animation racer;

    private AppCompatEditText edURL;
    private SeekBar sbSteps;
    private TextView tvSteps;
    private SeekBar sbSize;
    private TextView tvSize;
    private AppCompatSpinner spMethod;
    private AppCompatSpinner spMime;
    private RelativeLayout rlHistory;
    private RelativeLayout rlStart;
    private RelativeLayout rlParalelly;
    private HttpUrl url;
    private OnTaskListener listener;

    private String currentURL;
    public TaskFragment() {
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentURL = NuubitApp.getInstance().getSettings().getString(Const.CURRENT_URL, "https://google.com");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = Typeface.createFromAsset(getActivity().getAssets(), "fonts/crochet.ttf");
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_task);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llBackground = (LinearLayout) view.findViewById(R.id.llMainFragmentContainer);
        llBackground.startAnimation(racer);
        edURL = (AppCompatEditText) view.findViewById(R.id.edURL);
        edURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                url = HttpUrl.parse(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                url = HttpUrl.parse(s.toString());
            }
        });
        edURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    url = HttpUrl.parse(edURL.getText().toString());
                    if (url == null) {
                        url = HttpUrl.parse("http://" + edURL.getText().toString());
                        edURL.setText(url.toString());
                        edURL.setSelection(edURL.getText().toString().length());
                    }
                    Log.i(TAG, url.toString());
                    SharedPreferences.Editor ed = NuubitApp.getInstance().getSettings().edit();
                    ed.putString(Const.CURRENT_URL, url.toString());
                    ed.commit();
                }
            }
        });
        edURL.setText(currentURL);
        sbSteps = (SeekBar) view.findViewById(R.id.sbTests);
        tvSteps = (TextView) view.findViewById(R.id.tvTests);
        sbSteps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) {
                    seekBar.setProgress(1);
                    progress = 1;
                }
                tvSteps.setText(String.valueOf(progress) + " " + getActivity().getString(R.string.steps_one));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                edURL.clearFocus();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sbSize = (SeekBar) view.findViewById(R.id.sbPayload);
        tvSize = (TextView) view.findViewById(R.id.tvSize);
        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) {
                    seekBar.setProgress(1);
                    progress = 1;
                }
                tvSize.setText(String.valueOf(progress) + " " + getActivity().getString(R.string.size_one));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                edURL.clearFocus();
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        spMethod = (AppCompatSpinner) view.findViewById(R.id.spMethod);
        spMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sbSize.setEnabled(false);
                    spMime.setEnabled(false);
                    tvSize.setVisibility(View.INVISIBLE);
                } else {
                    sbSize.setEnabled(true);
                    spMime.setEnabled(true);
                    spMime.setSelection(0);
                    tvSize.setVisibility(View.VISIBLE);
                }
                edURL.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edURL.clearFocus();
            }
        });
        spMime = (AppCompatSpinner) view.findViewById(R.id.spPayload);
        spMime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edURL.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edURL.clearFocus();
            }
        });
        rlHistory = (RelativeLayout) view.findViewById(R.id.rlHistory);
        rlHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edURL.clearFocus();
            }
        });
        rlStart = (RelativeLayout) view.findViewById(R.id.rlStart);
        rlStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edURL.clearFocus();
                Log.i(TAG, url.toString());
                listener.onStartTaskInSeries(sbSteps.getProgress(), sbSize.getProgress(), url.toString(), spMethod.getSelectedItem().toString(), spMime.getSelectedItem().toString());
            }
        });
        rlParalelly = (RelativeLayout) view.findViewById(R.id.rlParallel);
        rlParalelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edURL.clearFocus();
                Log.i(TAG, url.toString());
                listener.onStartTaskParelelly(sbSteps.getProgress(), sbSize.getProgress(), url.toString(), spMethod.getSelectedItem().toString(), spMime.getSelectedItem().toString());
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragment.OnMainListener) {
            listener = (OnTaskListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTaskListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnTaskListener {
        void onStartTaskInSeries(int steps, long body, String url, String method, String type);

        void onStartTaskParelelly(int steps, long body, String url, String method, String type);
    }
}
