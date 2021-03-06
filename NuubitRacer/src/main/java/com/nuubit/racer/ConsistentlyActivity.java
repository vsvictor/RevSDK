package com.nuubit.racer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuubit.racer.fragments.SeriesFragment;
import com.nuubit.racer.fragments.SummaryFragment;
import com.nuubit.racer.model.Row;
import com.nuubit.racer.model.Table;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.interseptor.ProgressResponseBody;
import com.nuubit.sdk.statistic.sections.RequestOne;
import com.nuubit.sdk.types.HTTPCode;
import com.nuubit.sdk.types.Tag;
import com.nuubit.sdk.views.CountersFragment;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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


public class ConsistentlyActivity extends AppCompatActivity implements
        SeriesFragment.OnSeriesListener,
        SummaryFragment.OnSummaryListener {

    private static final String TAG = ConsistentlyActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView tvMethodMode;

    private int steps;
    private long size;
    private String url;
    private String method;
    //private String type;

    private OkHttpClient client = NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false);
    private Table table;
    private Table tableOriginal;

    private ResultAdapter adapter;

    private int counter;
    private String textBody = null;
    private String stype;
    private ProgressDialog pd;
    private RelativeLayout rlSendMail;

    private boolean isStop;
    private TextView tvCounter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        isStop = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                getFragmentManager().beginTransaction().replace(R.id.llLeftDrawer, CountersFragment.newInstance()).commit();
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        steps = getIntent().getIntExtra(Const.STEPS, 0);
        size = getIntent().getLongExtra(Const.SIZE, 0);
        url = getIntent().getStringExtra(Const.URL);
        method = getIntent().getStringExtra(Const.METHOD);
        stype = getIntent().getStringExtra(Const.TYPE);

        table = new Table();
        tableOriginal = new Table();

        adapter = new ResultAdapter(this, getTable(), getTableOriginal());

        rlSendMail = (RelativeLayout) findViewById(R.id.bSendMail);
        rlSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sData = Table.toTable(ConsistentlyActivity.this, getTable(), getTableOriginal(), Const.MODE_CONSISTENTLY, url, method);
                ShareCompat.IntentBuilder.from(ConsistentlyActivity.this)
                        .setType("message/rfc822")
                        .addEmailTo(NuubitApp.getInstance().getEMail())
                        .setSubject("Racer: " + url)
                        .setText(sData)
                        //.setHtmlText(body) //If you are using HTML in your body text
                        .setChooserTitle("Select:")
                        .startChooser();

            }
        });
        tvMethodMode = (TextView) findViewById(R.id.tvMethodMode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            NuubitApp.getInstance().email(NuubitApp.getInstance().getEMail(), this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getMethodMode() {
        return method + ", " + getResources().getString(R.string.start);
    }

    public Table getTable() {
        return table;
    }

    public Table getTableOriginal() {
        return tableOriginal;
    }

    public ResultAdapter getAdapter() {
        return adapter;
    }

    private Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        RequestBody body = null;
        Log.i(TAG, "Method N " + method);
        textBody = null;
        if (!method.equalsIgnoreCase("GET")) {
            MediaType type = null;
            String ssMime = "application/json";
            if (stype.equalsIgnoreCase("JSON")) {
                ssMime = "application/json";
            } else if (stype.equalsIgnoreCase("XML")) {
                ssMime = "application/xml";
            }
            type = MediaType.parse(ssMime);
            Log.i(TAG, stype);
            Log.i(TAG, type.toString());
            textBody = generateBody(ssMime, size);
            Log.i(TAG, "!!!!!!!!!!!!!!!!!!" + textBody + "!!!!!!!!!!!!!!!!");
            body = RequestBody.create(type, textBody);
        }
        builder.method(method, body);
        return builder.build();
    }

    public void startTask() {
        Log.i(TAG, "******************* START ********************");
        counter = 0;
        getTable().clear();
        getTableOriginal().clear();
        Request r = buildRequest();
        if (textBody == null) {
            new Getter(0, true).execute(r);
            Log.i(TAG, "CCCCCCCCCCCCCCCC body is empty CCCCCCCCCCCCC");
        } else {
            Log.i(TAG, "+++++++++++++++" + textBody + "++++++++++++++");
            new Getter(textBody.length(), true).execute(r);
        }
        if (steps > 0) {
            pd = new ProgressDialog(this);
            pd.setTitle("");
            pd.setMessage(this.getResources().getString(R.string.please_wait));
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            pd.setMax(steps);
            pd.setIndeterminate(false);
            pd.show();
        } else{
            AlertDialog.Builder alb = new AlertDialog.Builder(this);
            alb.setTitle("");
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.counter,null);
            tvCounter = (TextView) view.findViewById(R.id.tvCounter);
            RelativeLayout rlStop= (RelativeLayout) view.findViewById(R.id.rlStop);
            rlStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    isStop = true;
                }
            });
            alb.setView(view);
            alb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    isStop = true;
                }
            });
            dialog = alb.show();
        }

    }

    private String generateBody(String type, long size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        Log.i(TAG + " generator", "Begin, " + type);
        if (type.contains("application/json")) {
            result.append("{");
            while (result.toString().length() < (size * 1024)) {
                Random r = new Random();
                char c = (char) (r.nextInt(26) + 'a');
                result.append("{\"" + c + "\":\"" + c + "\"},");
                //Log.i(TAG+" generator",result.toString());

            }
            Log.i(TAG, "Size: " + result.toString().length());
            String ss = result.toString().substring(0, result.length() - 1);
            result = new StringBuilder();
            result.append(ss);
            result.append("}");
        } else if (type.contains("application/xml")) {
            result.append("<main>");
            while (result.toString().length() < (size * 1024)) {
                Random r = new Random();
                char c = (char) (r.nextInt(26) + 'a');
                result.append("<" + c + ">" + c + "</" + c + ">");
                Log.i(TAG + " generator", result.toString());
            }
            result.append("</main>");
        }
        Log.i(TAG, result.toString());
        textBody = result.toString();
        return textBody;
    }

    @Override
    public void onSeries(Uri uri) {

    }

    @Override
    public void onSummary(Uri uri) {

    }

    @Override
    public void onStopRequests(){
        isStop = true;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SummaryFragment first;
        private SeriesFragment second;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            first = SummaryFragment.newInstance(Const.MODE_CONSISTENTLY);
            second = SeriesFragment.newInstance(Const.MODE_CONSISTENTLY);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return first;
                }
                case 1: {
                    return second;
                }
                default:
                    return first;
            }
        }

        public SummaryFragment getSummary(){return first;}
        public SeriesFragment getSeries(){return second;}

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Advensed";
            }
            return null;
        }
    }

    public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
        private Context context;
        private Table data;
        private Table original;

        public ResultAdapter(Context context, Table table, Table tableOriginal) {
            this.context = context;
            this.data = table;
            this.original = tableOriginal;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_result, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int pos) {

            holder.mItem = data.get(pos);
            if ((pos) % 2 == 0)
                holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGray));
            else
                holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrayLight));

            holder.tvNumber.setText(String.valueOf(pos + 1));
            holder.tvTime.setText(String.valueOf(data.get(pos).getTimeInMillis()));
            holder.tvCode.setText(String.valueOf(data.get(pos).getCodeResult()));
            holder.tvBody.setText(String.valueOf(data.get(pos).getBody()));
            holder.tvPayload.setText(String.valueOf(data.get(pos).getPayload() / 1024));
            holder.tvTimeOrigin.setText(String.valueOf(original.get(pos).getTimeInMillis()));
            holder.tvCodeOrigin.setText(String.valueOf(original.get(pos).getCodeResult()));
            holder.tvBodyOrigin.setText(String.valueOf(original.get(pos).getBody()));
            holder.tvPayloadOrigin.setText(String.valueOf(original.get(pos).getPayload() / 1024));

        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }


        public void dataUpdated() {
            notifyDataSetChanged();
            Intent intent = new Intent(Const.UPDATE_DATA);
            sendBroadcast(intent);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;
            public final CardView cvMain;
            private final TextView tvNumber;
            public final TextView tvTime;
            public final TextView tvCode;
            public final TextView tvBody;
            public final TextView tvPayload;
            public final TextView tvTimeOrigin;
            public final TextView tvCodeOrigin;
            public final TextView tvBodyOrigin;
            public final TextView tvPayloadOrigin;

            public Row mItem;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                this.cvMain = (CardView) view.findViewById(R.id.cvMain);
                tvNumber = (TextView) view.findViewById(R.id.tvNumber);
                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvCode = (TextView) view.findViewById(R.id.tvCode);
                tvBody = (TextView) view.findViewById(R.id.tvBody);
                tvPayload = (TextView) view.findViewById(R.id.tvPayload);
                tvTimeOrigin = (TextView) view.findViewById(R.id.tvTimeOrigin);
                tvCodeOrigin = (TextView) view.findViewById(R.id.tvCodeOrigin);
                tvBodyOrigin = (TextView) view.findViewById(R.id.tvBodyOrigin);
                tvPayloadOrigin = (TextView) view.findViewById(R.id.tvPayloadOrigin);

            }
        }
    }

    private class Getter extends AsyncTask<Request, Void, Row> {
        private long bodySize = 0;
        private boolean serv;
        private Response response;
        private Response resp;
        private ProgressResponseBody.ProgressListener listener;

        public Getter(long bodySize, boolean serv) {
            this.bodySize = bodySize;
            this.serv = serv;
        }

        protected void onPreExecute(){
            listener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {

                }

                @Override
                public void firstByteTime(long time) {

                }

                @Override
                public void lastByteTime(long time) {

                }

                @Override
                public void onRequest(RequestOne res) {

                }

                @Override
                public void onResponse(Response response, boolean serv, long lastByteTime) {

                }
            };
        }

        @Override
        protected Row doInBackground(Request... params) {
            Request req = params[0];
            HTTPCode res = HTTPCode.UNDEFINED;
            response = null;
            do {
                if (res.getType() == HTTPCode.Type.REDIRECTION) {
                    Request.Builder builder = new Request.Builder();
                    String newURL = response.header("location");
                    builder.url(newURL);
                    builder.method(req.method(), req.body());
                    builder.tag(req.tag());
                    builder.headers(req.headers());
                    req = builder.build();
                }
                if (serv) {
                    Request.Builder builder = req.newBuilder();
                    builder.tag(new Tag(NuubitConstants.FREE_REQUEST, true));
                    req = builder.build();
                }
                Call callback = client.newCall(req);
                try {
                    resp = callback.execute();
                    response = resp.newBuilder().body(new ProgressResponseBody(resp.body(),listener)).build();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                res = HTTPCode.create(response.code());
            } while (res.getType() == HTTPCode.Type.REDIRECTION);
            Row row = new Row();
            if (response != null) {
                row.setUrl(response.request().url().toString());
                row.setStart(response.sentRequestAtMillis());
                row.setFinish(response.receivedResponseAtMillis());
                try {
                    Log.i(TAG, String.valueOf(bodySize));
                    row.setBody(bodySize / 1024);
                } catch (NullPointerException e) {
                    row.setBody(0);
                }
                try {
                    row.setPayload(response.body().bytes().length);
                } catch (IOException e) {
                    row.setPayload(0);
                }
                row.setCodeResult(response.code());
                Log.i(TAG, req.toString() + "---------- -------------\n");
                Log.i(TAG, response.toString() + "----------" + String.valueOf(row.getTimeInMillis()) + "-------------");
            }

            return row;
        }

        @Override
        protected void onPostExecute(Row row) {
            long rr = row.getFinish() - row.getStart();
            if (!this.serv) {
                row.setSource("R");
                getTable().add(row);
                if(steps > 0) {
                    pd.incrementProgressBy(1);
                }
            } else {
                row.setSource("O");
                getTableOriginal().add(row);
            }
            adapter.dataUpdated();
            Log.i(TAG, row.toString());
            counter++;
            if(steps > 0) {
                if ((counter) < (steps * 2)) {
                    Request req = buildRequest();
                    if (req.method().equalsIgnoreCase("GET")) {
                        new Getter(0, counter % 2 == 0).execute(buildRequest());
                    } else {
                        Request request = buildRequest();
                        new Getter(textBody.length(), counter % 2 == 0).execute(request);
                    }
                } else {
                    pd.dismiss();
                }
            } else{
                if(!isStop) {
                    tvCounter.setText(String.valueOf(getAdapter().getItemCount()+1));
                    Request req = buildRequest();
                    if (req.method().equalsIgnoreCase("GET")) {
                        new Getter(0, counter % 2 == 0).execute(buildRequest());
                    } else {
                        Request request = buildRequest();
                        new Getter(textBody.length(), counter % 2 == 0).execute(request);
                    }
                }
            }
        }
    }
}
