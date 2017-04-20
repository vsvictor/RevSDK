package com.nuubit.demo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nuubit.demo.R;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.protocols.HTTPException;
import com.nuubit.sdk.types.HTTPCode;

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
        //edQuery.setText("google.com.ua");
        edQuery.setText("amazon.com");
        //edQuery.setText("mail.ru");
        //edQuery.setText("http://httpbin.org/status/500");
        wvMain = (WebView) view.findViewById(R.id.wvMain);
        wvMain.setWebViewClient(NuubitSDK.createWebViewClient(getActivity(), wvMain, client));
        //wvMain.setWebChromeClient(NuubitSDK.createWebChromeClient());
        CookieManager cookieManager = CookieManager.getInstance();
        Log.i("COOKIES", ""+cookieManager.acceptCookie());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(wvMain, false);
        }
        Log.i("COOKIES", ""+cookieManager.acceptCookie());

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
        private HTTPCode code;
        @Override
        protected String doInBackground(String... params) {
            final String url = params[0];
            currURL = url;
            Response response = null;
            String body = null;
            if (url != null && !url.isEmpty()) {
                try {
                    response = runRequest(client, url, "GET", null);
                    if(response == null) throw new NullPointerException("Response error");
                    String location = "";
                    while ((HTTPCode.create(response.code()) == HTTPCode.MOVED_PERMANENTLY) ||
                            (HTTPCode.create(response.code()) == HTTPCode.FOUND)) {
                        location = response.header("location");
                        response = runRequest(client, location, response.request().method(), null);
                    }

                    code = HTTPCode.create(response.code());

                    if (code.getType() == HTTPCode.Type.CLIENT_ERROR) {
                        //response = runRequest(client, response.request().url().toString(), response.request().method(), null);
                        Log.i(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 404 AAAAAAAAAAAAAAAAAAA");
                    }

                    body = response.body().string();
                    final String endURL = location == null ? url : location;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!endURL.trim().isEmpty()) {
                                currURL = endURL;
                                edQuery.setText(currURL);
                            }
                        }
                    });
                } catch (HTTPException ex){
                    response = null;
                    ex.printStackTrace();
                }catch (IOException e) {
                    response = null;
                    e.printStackTrace();
                } catch (NullPointerException ex) {
                    response = null;
                    ex.printStackTrace();
                }
            }
            return body;
        }

        @Override
        protected void onPostExecute(String body) {
            if(body != null) {
                wvMain.loadDataWithBaseURL(null, body, contentType, codePage, null);
                edQuery.setText(currURL);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.bad_request), Toast.LENGTH_SHORT).show();
            }
            //edQuery.setText("http://httpbin.org/status/500");
        }
    }
}
