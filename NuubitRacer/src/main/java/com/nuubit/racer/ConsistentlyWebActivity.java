package com.nuubit.racer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuubit.racer.fragments.SeriesFragment;
import com.nuubit.racer.fragments.SummaryFragment;
import com.nuubit.racer.model.Row;
import com.nuubit.racer.model.Table;
import com.nuubit.racer.web.WebViewClientCounter;
import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.interseptor.ProgressResponseBody;
import com.nuubit.sdk.views.CountersFragment;
import com.nuubit.sdk.web.NuubitWebViewClient;
import com.nuubit.sdk.web.WebResourceRequestDefault;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class ConsistentlyWebActivity extends AppCompatActivity implements
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
    private int counterOrigin;
    private String textBody = null;
    private String stype;
    private ProgressDialog pd;
    private RelativeLayout rlSendMail;

    private TextView tvCounter;
    private AlertDialog dialog;
    private WebView wvMain;
    private boolean isStop = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consistently_web);
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
                String sData = Table.toTable(ConsistentlyWebActivity.this, getTable(), getTableOriginal(), Const.MODE_CONSISTENTLY, url, method);
                ShareCompat.IntentBuilder.from(ConsistentlyWebActivity.this)
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
        //wvMain = (WebView) findViewById(R.id.wvMain);
        //wvOrigin = (WebView) findViewById(R.id.wvOrigin);
        wvMain = new WebView(this);
        wvMain.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
    public void start(){
        counter = 0;
        boolean isOrigin = false;
        if (steps > 0) {
            pd = new ProgressDialog(this);
            pd.setTitle("");
            pd.setMessage(this.getResources().getString(R.string.please_wait));
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            pd.setMax(steps);
            pd.setIndeterminate(false);
            pd.show();
        } else {
            AlertDialog.Builder alb = new AlertDialog.Builder(this);
            alb.setTitle("");
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.counter, null);
            tvCounter = (TextView) view.findViewById(R.id.tvCounter);
            RelativeLayout rlStop = (RelativeLayout) view.findViewById(R.id.rlStop);
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
        final NuubitWebViewClient webClient = NuubitSDK.createWebViewClient(this, wvMain, client);

        webClient.setOrigin(counter%2==1);
        webClient.setOnPageLoaded(new NuubitWebViewClient.OnPageLoaded() {
            @Override
            public void onLoaded(long size) {
                Log.i("BODY","Body: "+size);
            }
        });

        wvMain.setWebViewClient(webClient);
        wvMain.setWebChromeClient(NuubitSDK.createWebChromeClient());
/*
        wvMain.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.i("WEBVIEWDATA", "Started");
            }
        });
*/
/*
        wvMain.setOnLoadedPage(new NuubitWebView.OnLoaded() {
            @Override
            public void onLoaded(int code, String url, long startTime, long finishTime, long sent, long received) {
                Log.i("WEBVIEWDATA", "Code: "+code+" Start: "+startTime+" stop: "+finishTime+" sent: "+sent+" received: "+received+" Time: "+(finishTime-startTime));
                //Log.i("WEBVIEWDATA", url);
                Log.i("WEBVIEWDATA", "Counter: "+counter+" Origin: "+webClient.isOrigin());

                Row row = new Row();
                row.setCodeResult(200);
                row.setUrl(url);
                row.setBody(sent);
                row.setFinish(finishTime);
                row.setPayload(received);
                row.setSource(counter%2==0?"R":"O");
                row.setStart(startTime);
                if(counter%2==0) getTable().add(row);
                else getTableOriginal().add(row);
                counter++;
                //isOrigin = !isOrigin;
                adapter.dataUpdated();
                if (steps == -1) {
                    if (!isStop) {
                        webClient.setOrigin(counter%2==1);
                        tvCounter.setText(String.valueOf((int)(Math.floor(counter/2))));
                        wvMain.loadUrl(url);
                    }
                } else {
                    if (counter < (steps*2)) {
                        webClient.setOrigin(counter%2==1);
                        wvMain.loadUrl(url);
                    }
                }
            }
        });
*/
        wvMain.loadUrl(url);
    }

    public Table getTable() {
        return table;
    }

    public Table getTableOriginal() {
        return tableOriginal;
    }

    public String getMethodMode() {
        return method + " , Web " + getResources().getString(R.string.start);
    }

    public ResultAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onSeries(Uri uri) {

    }

    @Override
    public void onSummary(Uri uri) {

    }

    @Override
    public void onStopRequests() {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SummaryFragment first;
        private SeriesFragment second;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            first = SummaryFragment.newInstance(Const.MODE_WEB_CONSISTENTLY);
            second = SeriesFragment.newInstance(Const.MODE_WEB_CONSISTENTLY);
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
        public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_result, parent, false);
            return new ResultAdapter.ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ResultAdapter.ViewHolder holder, int pos) {
            try {
                holder.mItem = data.get(pos);
                if ((pos) % 2 == 0)
                    holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGray));
                else
                    holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrayLight));
            } catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
            try{

                holder.tvNumber.setText(String.valueOf(pos + 1));
                holder.tvTime.setText(String.valueOf(data.get(pos).getTimeInMillis()));
                holder.tvCode.setText(String.valueOf(data.get(pos).getCodeResult()));
                holder.tvBody.setText(String.valueOf(data.get(pos).getBody()/1024));
                holder.tvPayload.setText(String.valueOf(data.get(pos).getPayload() / 1024));
            } catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
            try {
                holder.tvTimeOrigin.setText(String.valueOf(original.get(pos).getTimeInMillis()));
                holder.tvCodeOrigin.setText(String.valueOf(original.get(pos).getCodeResult()));
                holder.tvBodyOrigin.setText(String.valueOf(original.get(pos).getBody()/1024));
                holder.tvPayloadOrigin.setText(String.valueOf(original.get(pos).getPayload() / 1024));
            } catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
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

}
