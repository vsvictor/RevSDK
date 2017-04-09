package com.nuubit.tester.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuubit.sdk.NuubitActions;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.config.OperationMode;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.tester.NuubitApp;
import com.nuubit.tester.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    //private WebView wvMain;
    private AppCompatSpinner spMethod;
    private String method;
    private AppCompatSpinner spMode;
    private OperationMode mode = OperationMode.transfer_and_report;
    private RelativeLayout rlRun;
    private TextInputEditText edBody;
    private String body;
    private TextInputEditText edHeaders;
    private String headers;
    private AppCompatCheckBox cbView;
    private AppCompatCheckBox cbClient;
    private ScrollView svText;
    private ScrollView svWeb;
    private WebView wvMain;
    private TextView tvMain;
    private TextView tvHeader;
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


        wvMain = (WebView) view.findViewById(R.id.wvMain);
        wvMain.setWebViewClient(new WebViewClient());
        wvMain.setWebChromeClient(new WebChromeClient());

        tvMain = (TextView) view.findViewById(R.id.tvMain);
        tvHeader = (TextView) view.findViewById(R.id.tvHeaders);

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
                    new Reader().execute(newURL, edBody.getText().toString(), edHeaders.getText().toString());
                }
            }
        });

        spMethod = (AppCompatSpinner) view.findViewById(R.id.spMethod);
        spMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        method = "GET";
                        break;
                    }
                    case 1: {
                        method = "POST";
                        break;
                    }
                    case 2: {
                        method = "PUT";
                        break;
                    }
                    case 3: {
                        method = "DELETE";
                        break;
                    }
                    case 4: {
                        method = "HEAD";
                        break;
                    }
                    case 5: {
                        method = "CONNECT";
                        break;
                    }
                    case 6: {
                        method = "OPTIONS";
                        break;
                    }
                    case 7: {
                        method = "TRACE";
                        break;
                    }
                    default: {
                        method = "GET";
                        break;
                    }
                }
                Toast.makeText(getActivity(), method, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMode = (AppCompatSpinner) view.findViewById(R.id.spMode);
        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        mode = OperationMode.transfer_and_report;
                        break;
                    }
                    case 1: {
                        mode = OperationMode.transfer_only;
                        break;
                    }
                    case 2: {
                        mode = OperationMode.report_only;
                        break;
                    }
                    case 3: {
                        mode = OperationMode.off;
                        break;
                    }
                    default: {
                        mode = OperationMode.transfer_and_report;
                        break;
                    }
                }
                NuubitApp.getInstance().getConfig().getParam().get(0).setOperationMode(mode);
                Toast.makeText(getActivity(), mode.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edBody = (TextInputEditText) view.findViewById(R.id.edBody);
        edBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                body = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edHeaders = (TextInputEditText) view.findViewById(R.id.edHeaders);
        edHeaders.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                headers = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edHeaders.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.standart_header_layout, null, false);
                final AppCompatCheckBox cbHost = (AppCompatCheckBox) view.findViewById(R.id.cbHost);
                final AppCompatCheckBox cbXRevHost = (AppCompatCheckBox) view.findViewById(R.id.cbXRevHost);
                final AppCompatCheckBox cbXRevProto = (AppCompatCheckBox) view.findViewById(R.id.cbXRevProto);
                final RadioGroup rgProto = (RadioGroup) view.findViewById(R.id.rgProto);
                final AppCompatRadioButton rbHTTP = (AppCompatRadioButton) view.findViewById(R.id.rbHTTP);
                final AppCompatRadioButton rbHTTPS = (AppCompatRadioButton) view.findViewById(R.id.rbHTTPS);

                String sBegURL = edQuery.getText().toString();
                HttpUrl begURL = HttpUrl.parse(sBegURL);
                if (begURL == null) {
                    String sEndURL = "http://" + sBegURL;
                    begURL = HttpUrl.parse(sEndURL);
                    edQuery.setText(begURL.toString());
                }

                rgProto.setVisibility(View.INVISIBLE);
                cbXRevProto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        rgProto.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getResources().getString(R.string.add_headers));
                builder.setView(view);
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (headers == null) headers = "";
                        StringBuilder sb = new StringBuilder();
                        sb.append(headers);
                        if (cbHost.isChecked()) {
                            headers += "Host=" + NuubitApp.getInstance().getConfig().getParam().get(0).getEdgeSdkDomain() + " ";
                        }
                        if (cbXRevHost.isChecked()) {
                            headers += "X-Rev-Host=" + HttpUrl.parse(edQuery.getText().toString()).host();
                        }
                        if (cbXRevProto.isChecked()) {
                            String sProto = "X-Rev-Proto=";
                            if (rbHTTP.isChecked()) sProto += "http";
                            else sProto += "https";
                            headers += sProto + " ";
                        }
                        edHeaders.setText(headers);
                    }
                });
                builder.create().show();
                return false;
            }
        });

        svText = (ScrollView) view.findViewById(R.id.svText);
        svWeb = (ScrollView) view.findViewById(R.id.svWeb);
        cbClient = (AppCompatCheckBox) view.findViewById(R.id.cbClient);

        cbView = (AppCompatCheckBox) view.findViewById(R.id.cbView);
        cbView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    svText.setVisibility(View.INVISIBLE);
                    svWeb.setVisibility(View.VISIBLE);
                    cbClient.setVisibility(View.VISIBLE);
                } else {
                    svText.setVisibility(View.VISIBLE);
                    svWeb.setVisibility(View.INVISIBLE);
                    cbClient.setVisibility(View.INVISIBLE);
                }
            }
        });
        cbClient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((cbClient.getVisibility() == View.VISIBLE) && (cbClient.isChecked())) {
                    wvMain.setWebViewClient(NuubitSDK.createWebViewClient(getActivity(), wvMain, client));
                    wvMain.setWebChromeClient(NuubitSDK.createWebChromeClient());
                } else {
                    wvMain.setWebViewClient(new WebViewClient());
                    wvMain.setWebChromeClient(new WebChromeClient());
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
                    + " must implement OnMainListener");
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
        private String currURL;
        private Headers resHeader;
        @Override
        protected String doInBackground(String... params) {
            final String qURL = params[0];
            final String qMethod = params[1];
            String qBody = params[2];
            currURL = qURL;
            Response response = null;
            String qody = null;
            if (qURL != null && !qURL.isEmpty()) {
                try {
                    //response = runRequest(client, url, "GET", null);
                    Request.Builder builder = new Request.Builder();
                    builder.url(qURL);
                    if (qMethod.equalsIgnoreCase("GET")) {
                        builder.method(method, RequestBody.create(MediaType.parse("application/json"), qBody));
                    }
                    final Call callback = client.newCall(builder.build());
                    response = callback.execute();

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
                    resHeader = response.headers();
                    body = response.body().string();
                    final String endURL = location == null ? qURL : location;
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
            tvMain.setText(body);
            tvHeader.setText(resHeader.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter ifConfig = new IntentFilter();
        ifConfig.addAction(NuubitActions.CONFIG_LOADED);
        getActivity().registerReceiver(configReceiver, ifConfig);
    }

    private BroadcastReceiver configReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, NuubitApp.getInstance().getConfig().getParam().get(0).getOperationMode().toString());
            switch (NuubitApp.getInstance().getConfig().getParam().get(0).getOperationMode()) {
                case transfer_and_report: {
                    spMode.setSelection(0);
                    break;
                }
                case transfer_only: {
                    spMode.setSelection(1);
                    break;
                }
                case report_only: {
                    spMode.setSelection(2);
                    break;
                }
                case off: {
                    spMode.setSelection(3);
                    break;
                }
                //default:{spMode.setSelection(0);break;}
            }
        }
    };
}
