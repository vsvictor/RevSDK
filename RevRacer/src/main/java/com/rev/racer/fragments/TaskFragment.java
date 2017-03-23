package com.rev.racer.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.rev.racer.R;

public class TaskFragment extends Fragment {
    private Typeface main;
    private LinearLayout llBackground;
    private Animation racer;
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
