package com.rev.revbanshee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.rev.revsdk.RevSDK;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client = RevSDK.OkHttpCreate();

    private TextInputEditText edQuery;
    private WebView wvMain;
    private RelativeLayout rlRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edQuery = (TextInputEditText) findViewById(R.id.edQuery);
        edQuery.setText("http://stackoverflow.com/questions/3961589/android-webview-and-loaddata");
        //edQuery.setText("https://www.google.com");
        //edQuery.setText("https://mail.ru/");
        wvMain = (WebView) findViewById(R.id.wvMain);
        wvMain.getSettings().setJavaScriptEnabled(true);
        wvMain.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wvMain.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wvMain.getSettings().setPluginState(WebSettings.PluginState.ON);


        wvMain.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String newURL) {
                super.onPageFinished(view, newURL);
                edQuery.setText(newURL);
            }
        });
        wvMain.setWebChromeClient(new WebChromeClient());

        rlRun = (RelativeLayout) findViewById(R.id.rlRun);
        rlRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edQuery.getText().toString();
                if (url != null && !url.isEmpty()) {
                    new Reader().execute(url);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Request req = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    response = client.newCall(req).execute();
                } catch (IOException e) {
                    response = null;
                    e.printStackTrace();
                }
                try {
                    Headers hh = response.headers();
                    contentType = "text/html";
                    codePage = Charset.forName("windows-1251").name().toUpperCase();
                    for (int i = 0; i < hh.size(); i++) {
                        String headerName = hh.name(i).toLowerCase();
                        if (headerName.equals("content-type")) {
                            String sAll = hh.value(i).toString();
                            String[] parse = sAll.split(";");
                            contentType = sAll;
                            if (parse.length > 1) {
                                String[] ss = parse[1].split("=");
                                codePage = ss[1].toUpperCase();
                            }
                            int ii = 0;
                            break;
                        }
                    }
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }
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
            //wvMain.loadData(body, contentType, codePage);
            wvMain.loadDataWithBaseURL(null, body, contentType, codePage, null);
        }
    }
}
