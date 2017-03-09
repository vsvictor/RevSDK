package com.rev.revdemo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.rev.revdemo.R;
import com.rev.revsdk.RevSDK;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private OnMainListener listener;

    private OkHttpClient client = RevSDK.OkHttpCreate();
    private TextInputEditText edQuery;
    private WebView wvMain;
    private RelativeLayout rlRun;


    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        edQuery = (TextInputEditText) view.findViewById(R.id.edQuery);
        edQuery.setText("stackoverflow.com/questions/3961589/android-webview-and-loaddata");
        //edQuery.setText("https://google.com");
        wvMain = (WebView) view.findViewById(R.id.wvMain);
        wvMain.setWebViewClient(RevSDK.createWebViewClient());

        wvMain.getSettings().setJavaScriptEnabled(true);
        wvMain.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wvMain.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wvMain.getSettings().setPluginState(WebSettings.PluginState.ON);

        rlRun = (RelativeLayout) view.findViewById(R.id.rlRun);
        rlRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edQuery.getText().toString();
                if (url != null && !url.isEmpty()) {
                    String sRes = url.substring(0, url.length());
                    Log.i(TAG, "1:" + sRes);
                    String s1 = url.substring(0, 7);
                    String s2 = url.substring(0, 8);
                    Log.i(TAG, s1 + "---" + s2);
                    if (!s1.equalsIgnoreCase("http://") && !s2.equalsIgnoreCase("https://")) {
                        sRes = "http://" + url;
                        Log.i(TAG, "2:" + sRes);
                    }
                    Log.i(TAG, "3:" + sRes);
                    edQuery.setText(sRes);
                    //Log.i(TAG, sRes);
                    new Reader().execute(sRes);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainListener) {
            listener = (OnMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnMainListener {
        void onMain();
    }

    private class Reader extends AsyncTask<String, Void, String> {
        private String contentType;
        private String codePage;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            Response response = null;
            String body = null;
            if (url != null && !url.isEmpty()) {
                response = runRequest(url);
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return body;
        }
        @Override
        protected void onPostExecute(String body) {
            wvMain.loadDataWithBaseURL(null, body, contentType, codePage, null);
        }

        private Response runRequest(String url) {
            Response response;
            Request req = new Request.Builder()
                    .url(url)
                    .build();
            try {
                response = client.newCall(req).execute();
            } catch (IOException e) {
                response = null;
                e.printStackTrace();
            }
            return response;
        }
    }
}
