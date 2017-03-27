package com.rev.racer.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rev.racer.MainActivity;
import com.rev.racer.R;
import com.rev.racer.ResultActivity;

public class SeriesFragment extends Fragment {
    private static final String TAG = SeriesFragment.class.getSimpleName();
    private Typeface main;
    private LinearLayout llBackground;
    private RelativeLayout rlMainContainer;
    private Animation racer;
    private RecyclerView rvResult;
    private FloatingActionButton fbEMail;

    private OnSeriesListener listener;

    public SeriesFragment() {
    }

    public static SeriesFragment newInstance() {
        SeriesFragment fragment = new SeriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = Typeface.createFromAsset(getActivity().getAssets(), "fonts/crochet.ttf");
        View rootView = inflater.inflate(R.layout.fragment_series, container, false);
        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_result);
        //adapter = new ResultAdapter(getActivity(), ((MainActivity) getActivity()).getTable());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llBackground = (LinearLayout) view.findViewById(R.id.llMainFragmentContainer);
        rlMainContainer = (RelativeLayout) view.findViewById(R.id.rlMainContainer);
        //rlMainContainer.startAnimation(racer);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvResult.setAdapter(((ResultActivity) getActivity()).getAdapter());

        fbEMail = (FloatingActionButton) view.findViewById(R.id.fbEmail);
        fbEMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
//                builder.append(all.toString());
                builder.append("\n");
                builder.append("Average: ");
                builder.append(((ResultActivity) getActivity()).getTable().average());
                builder.append(" Mediane: ");
                builder.append(((ResultActivity) getActivity()).getTable().median());

                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("message/rfc822")
                        .addEmailTo(((MainActivity) getActivity()).getEMail())
                        .setSubject("Racer")
                        .setText(builder.toString())
                        //.setHtmlText(body) //If you are using HTML in your body text
                        .setChooserTitle("Select:")
                        .startChooser();
            }
        });
        ((ResultActivity) getActivity()).startTask();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSeriesListener) {
            listener = (OnSeriesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSeriesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnSeriesListener {
        void onSeries(Uri uri);
    }
}
