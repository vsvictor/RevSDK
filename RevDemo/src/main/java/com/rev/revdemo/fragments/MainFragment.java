package com.rev.revdemo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.rev.revdemo.R;
import com.rev.revsdk.Constants;
import com.rev.revsdk.RevSDK;
import com.rev.revsdk.types.HTTPCode;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.rev.revsdk.utils.IOUtils.runRequest;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private OnMainListener listener;

    private OkHttpClient client = RevSDK.OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC, true, true);
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
        //edQuery.setText("stackoverflow.com/questions/3961589/android-webview-and-loaddata");
        edQuery.setText("google.com.ua");
        //edQuery.setText("mail.ru");
        wvMain = (WebView) view.findViewById(R.id.wvMain);
        wvMain.setWebViewClient(RevSDK.createWebViewClient(getActivity(), wvMain, client));
        wvMain.setWebChromeClient(RevSDK.createWebChromeClient());

        rlRun = (RelativeLayout) view.findViewById(R.id.rlRun);
        rlRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edQuery.getText().toString();
                if (url != null && !url.isEmpty()) {
                    HttpUrl test = HttpUrl.parse(url);
                    String newURL = null;
                    try {
                        newURL = test.toString();

                    } catch (NullPointerException ex) {
                        newURL = "http://" + url;
                        edQuery.setText(newURL);
                    }

                    new Reader().execute(newURL);
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

    public WebView getBrowser(){
        return wvMain;
    }

    public interface OnMainListener {
        void onMain();
    }

    private class Reader extends AsyncTask<String, Void, String> {
        private String contentType;
        private String codePage;
        private String currURL;

        @Override
        protected String doInBackground(String... params) {
            final String url = params[0];
            currURL = url;
            Response response = null;
            String body = null;
            if (url != null && !url.isEmpty()) {
                try {
                    response = runRequest(client, url, "GET", null);
                    String location = "";
                    while ((HTTPCode.create(response.code()) == HTTPCode.MOVED_PERMANENTLY) ||
                            (HTTPCode.create(response.code()) == HTTPCode.FOUND)) {
                        location = response.header("location");
                        response = runRequest(client, location, response.request().method(), null);
                    }

                    HTTPCode code = HTTPCode.create(response.code());
                    if (code.getType() == HTTPCode.Type.CLIENT_ERROR) {
                        response = runRequest(client, response.request().url().toString(), response.request().method(), null);
                    }

                    body = response.body().string();
                    final String endURL = location == null?url:location;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            edQuery.setText(endURL);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return body;
        }

        @Override
        protected void onPostExecute(String body) {
            wvMain.loadDataWithBaseURL(null, body, contentType, codePage, null);
            //edQuery.setText(currURL);
        }
    }
}
