package com.nuubit.demo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.nuubit.demo.R;
import com.nuubit.sdk.NuubitApplication;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.protocols.HTTPException;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.web.NuubitWebViewClient;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.nuubit.sdk.utils.IOUtils.runRequest;
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

    private static final String TAG = MainFragment.class.getSimpleName();
    private OnMainListener listener;

    private OkHttpClient client = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false);
    private TextInputEditText edQuery;
    private WebView wvMain;
    private NuubitWebViewClient webClient;
    private RelativeLayout rlRun;
    private RelativeLayout rlClear;
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
        //edQuery.setTexpb.getFirstByteTime()t("stackoverflow.com/questions/3961589/android-webview-and-loaddata");
        edQuery.setText("google.com.ua");
        //edQuery.setText("youtube.com");
        //edQuery.setText("https://monitor.revsw.net/1M.jpg");
        //edQuery.setText("mail.ru");
        //edQuery.setText("http://httpbin.org/status/500");
        wvMain = (WebView) view.findViewById(R.id.wvMain);
        webClient = NuubitSDK.createWebViewClient(getActivity(), wvMain, client);

        webClient.setOnURLChangeListener(new NuubitWebViewClient.OnURLChanged() {
            @Override
            public void onURLChanged(final String url) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(url != null && !url.isEmpty() && !url.equalsIgnoreCase("about:blank")) {
                            edQuery.setText(url);
                        }
                    }
                });
            }
        });

        wvMain.setWebViewClient(webClient);
        wvMain.setWebChromeClient(NuubitSDK.createWebChromeClient());

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
                        //edQuery.setText(newURL);
                        webClient.onURLChanged(newURL);
                    }

                    new Reader().execute(newURL);
                }
            }
        });
        rlClear = (RelativeLayout) view.findViewById(R.id.rlClear);
        rlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edQuery.setText("");
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

    public WebView getBrowser() {
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
                    //while ((HTTPCode.create(response.code()) == HTTPCode.MOVED_PERMANENTLY) ||
                    //        (HTTPCode.create(response.code()) == HTTPCode.FOUND))
/*
                    while(response.isRedirect())
                    {
                        location = response.header("location");
                        response = runRequest(client, location, response.request().method(), null);
                        currURL = location;
                        webClient.onURLChanged(currURL);
                    }
*/
/*
                    if((response.request().url().toString() != null)) {
                        currURL = response.request().url().toString();
                        webClient.onURLChanged(currURL);
                    }
*/
                    HTTPCode code = HTTPCode.create(response.code());

                    if (code.getType() == HTTPCode.Type.CLIENT_ERROR) {
                        //response = runRequest(client, response.request().url().toString(), response.request().method(), null);
                        Log.i(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 404 AAAAAAAAAAAAAAAAAAA");
                    }

                    body = response.body().string();
                    int i = 0;
                    final String endURL = (location == null ? url : location);

                    if(endURL != null && !endURL.isEmpty()) {
                        currURL = endURL;
                        webClient.onURLChanged(endURL);
                    }

/*
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!endURL.trim().isEmpty()) {
                                currURL = endURL;
                                edQuery.setText(currURL);
                            }
                        }
                    });
*/
                } catch (IOException e) {
                    //webClient.onURLChanged(currURL);
                    e.printStackTrace();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
            return body;
        }

        @Override
        protected void onPostExecute(String body) {
            wvMain.loadDataWithBaseURL(null, body, contentType, codePage, null);
            //edQuery.setText("http://httpbin.org/status/500");
            webClient.onURLChanged(currURL);
        }
    }
}
