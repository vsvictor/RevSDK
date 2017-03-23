package com.rev.racer.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.rev.racer.R;

import okhttp3.HttpUrl;

public class TaskFragment extends Fragment {
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

    private HttpUrl url;
    private OnTaskListener listener;

    public TaskFragment() {
    }
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if (url == null) {
                    url = HttpUrl.parse("http://" + s.toString());
                    edURL.setText(url.toString().substring(0, url.toString().length() - 1));
                    edURL.setSelection(edURL.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                    tvSize.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMime = (AppCompatSpinner) view.findViewById(R.id.spPayload);
        rlHistory = (RelativeLayout) view.findViewById(R.id.rlHistory);
        rlStart = (RelativeLayout) view.findViewById(R.id.rlStart);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskListener) {
            listener = (OnTaskListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTaskListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnTaskListener {
        void onStartTask();
    }
}
