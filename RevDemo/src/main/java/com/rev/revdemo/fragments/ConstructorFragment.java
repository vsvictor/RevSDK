package com.rev.revdemo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rev.revdemo.R;

public class ConstructorFragment extends Fragment {
    private OnConstructorListener listener;

    public ConstructorFragment() {
    }

    public static ConstructorFragment newInstance() {
        ConstructorFragment fragment = new ConstructorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_constructor, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConstructorListener) {
            listener = (OnConstructorListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnConstructorListener {
        void onFragmentInteraction(Uri uri);
    }
}
