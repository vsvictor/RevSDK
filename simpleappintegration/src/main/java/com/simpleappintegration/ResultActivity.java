package com.simpleappintegration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private RadioButton radioHeader;
    private RadioButton radioBody;
    private ProgressBar progressBar;
    private TextView textResult;

    private String dataHeader;
    private String dataBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int menuIndex = intent.getIntExtra("menu index", 0);
        String menuTitle = getResources().getStringArray(R.array.menu_list)[menuIndex];

        radioHeader = (RadioButton) findViewById(R.id.radioHeader);
        radioBody   = (RadioButton) findViewById(R.id.radioBody);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textResult  = (TextView)    findViewById(R.id.textResult);

        setTitle(menuTitle);

        radioHeader.setOnCheckedChangeListener(this);
        radioBody.setOnCheckedChangeListener(this);

        switch (menuIndex) {
            case 0: callSyncGet();      break;  // Synchronous Get
            case 1: callASyncGet();     break;  // Asynchronous Get
            case 2: callAccessHeader(); break;  // Accessing Headers
            case 3: callPostString();   break;  // Posting a String
            // TODO : Add other case
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) return;

        String result = null;

        int viewId = buttonView.getId();
        switch (viewId) {
            case R.id.radioHeader:  result = dataHeader;    break;
            case R.id.radioBody:    result = dataBody;      break;
        }

        textResult.setText(result);
    }

    private void callSyncGet() {
        new AsyncTask<Void, Void, Message>() {
            @Override
            protected void onPreExecute() {
                resetView();

                super.onPreExecute();
            }

            @Override
            protected Message doInBackground(Void... voids) {
                //OkHttpClient okHttpClient = new OkHttpClient();
                OkHttpClient okHttpClient = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false);

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://date.jsontest.com/").build();

                Message message = new Message();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        message.what = 1;
                        message.obj  = response;
                    } else {
                        message.what = 0;
                        message.obj  = "Not Success\ncode : " + response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = 0;
                    message.obj  = "Error\n" + e.getMessage();
                }

                return message;
            }

            @Override
            protected void onPostExecute(Message message) {
                super.onPostExecute(message);

                switch (message.what) {
                    case 0:
                        dataBody = (String) message.obj;
                        break;
                    case 1:
                        getResponseData((Response) message.obj);
                        break;
                }

                showView();

                message.recycle();
            }
        }.execute();
    }

    private void callASyncGet() {
        resetView();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://md5.jsontest.com/?text=https://github.com/first087/Android-OkHttp-Example").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataBody = "Error\n" + e.getMessage();

                updateView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    getResponseData(response);
                } else {
                    dataBody = "Not Success\ncode : " + response.code();
                }

                updateView();

            }

            public void updateView() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showView();
                    }
                });
            }
        });
    }

    private void callAccessHeader() {
        resetView();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
                dataBody = "Error\n" + e.getMessage();

                updateView();
            }
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    getResponseData(response);
                } else {
                    dataBody = "Not Success\ncode : " + response.code();
                }

                updateView();
            }

            public void updateView() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showView();
                    }
                });
            }
        });
    }

    private void callPostString() {
        resetView();

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, postBody))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataBody = "Error\n" + e.getMessage();

                updateView();
            }
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    getResponseData(response);
                } else {
                    dataBody = "Not Success\ncode : " + response.code();
                }

                updateView();
            }

            public void updateView() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showView();
                    }
                });
            }
        });
    }

    private void getResponseData(Response response) {
        Headers headers = response.headers();
        for (String header : headers.names()) {
            dataHeader += "name : " + header + "\n+ value : " + headers.get(header) + "\n";
        }

        try {
            dataBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            dataBody = "Error !\n\n" + e.getMessage();
        }
    }

    private void resetView() {
        dataHeader = "";
        dataBody   = "";

        radioHeader.setEnabled(false);
        radioHeader.setChecked(false);
        radioBody.setEnabled(false);
        radioBody.setChecked(false);
        progressBar.setVisibility(View.VISIBLE);
        textResult.setVisibility(View.GONE);
        textResult.setText("");
    }

    private void showView() {
        radioHeader.setEnabled(true);
        radioBody.setEnabled(true);
        radioBody.setChecked(true);
        progressBar.setVisibility(View.GONE);
        textResult.setVisibility(View.VISIBLE);
    }
}
