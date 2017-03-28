package com.rev.racer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
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

import com.rev.racer.fragments.SeriesFragment;
import com.rev.racer.fragments.SummaryFragment;
import com.rev.racer.model.Row;
import com.rev.racer.model.Table;
import com.rev.sdk.Constants;
import com.rev.sdk.RevSDK;
import com.rev.sdk.types.HTTPCode;
import com.rev.sdk.types.Tag;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParalellyActivity extends AppCompatActivity implements
        SeriesFragment.OnSeriesListener,
        SummaryFragment.OnSummaryListener {

    private static final String TAG = ParalellyActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private int steps;
    private long size;
    private String url;
    private String method;
    //private String type;

    private OkHttpClient client = RevSDK.OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC, false, false);
    private Table table;
    private Table tableOriginal;

    private ResultAdapter adapter;

    private int counter;
    private String textBody = null;
    private String stype;
    private ProgressDialog pd;
    private RelativeLayout rlSendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                String sData = Table.toTable(getTable(), getTableOriginal(), Const.MODE_PARALELLY);
                ShareCompat.IntentBuilder.from(ParalellyActivity.this)
                        .setType("message/rfc822")
                        .addEmailTo(RevApp.getInstance().getEMail())
                        .setSubject("Racer")
                        .setText(sData)
                        //.setHtmlText(body) //If you are using HTML in your body text
                        .setChooserTitle("Select:")
                        .startChooser();

            }
        });

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
            RevApp.getInstance().email(RevApp.getInstance().getEMail(), this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void startTask() {
        getTable().clear();
        getTableOriginal().clear();
        for (int st = 0; st < steps * 2; st++) {
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            RequestBody body = null;
            Log.i(TAG, "Method N " + method);
            String textBody = null;
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
                Log.i(TAG, textBody);
                body = RequestBody.create(type, textBody);
            }
            builder.method(method, body);
            if (textBody == null) {
                new Getter(0, st % 2 == 0).execute(builder.build());
            } else new Getter(textBody.length(), st % 2 == 0).execute(builder.build());
        }
        pd = new ProgressDialog(this);
        pd.setTitle("");
        pd.setMessage(this.getResources().getString(R.string.please_wait));
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgress(0);
        pd.setMax(steps);
        pd.setIndeterminate(false);
        pd.show();
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

        return result.toString();
    }

    @Override
    public void onSeries(Uri uri) {

    }

    @Override
    public void onSummary(Uri uri) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return SummaryFragment.newInstance(Const.MODE_PARALELLY);
                }
                case 1: {
                    return SeriesFragment.newInstance(Const.MODE_PARALELLY);
                }
                default:
                    return SummaryFragment.newInstance(Const.MODE_PARALELLY);
            }
        }

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
            holder.tvBody.setText(String.valueOf(data.get(pos).getBody() / 1024));
            holder.tvPayload.setText(String.valueOf(data.get(pos).getPayload() / 1024));
            holder.tvTimeOrigin.setText(String.valueOf(original.get(pos).getTimeInMillis()));
            holder.tvCodeOrigin.setText(String.valueOf(original.get(pos).getCodeResult()));
            holder.tvBodyOrigin.setText(String.valueOf(original.get(pos).getBody() / 1024));
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

        public Getter(long bodySize, boolean serv) {
            this.bodySize = bodySize;
            this.serv = serv;
        }

        @Override
        protected Row doInBackground(Request... params) {
            Request req = params[0];
            HTTPCode res = HTTPCode.UNDEFINED;
            Response response = null;
            do {
                //Log.i(TAG, res.toString());
                if (res.getType() == HTTPCode.Type.REDIRECTION) {
                    Request.Builder builder = new Request.Builder();
                    String newURL = response.header("location");
                    //Log.i(TAG, newURL);
                    builder.url(newURL);
                    builder.method(req.method(), req.body());
                    builder.tag(req.tag());
                    builder.headers(req.headers());
                    if (serv) builder.tag(new Tag(Constants.SYSTEM_REQUEST, true));
                    req = builder.build();
                    //Log.i(TAG, req.toString());
                }

                Call callback = client.newCall(req);
                try {
                    response = callback.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                res = HTTPCode.create(response.code());

                //Log.i(TAG, res.toString());
                //Log.i(TAG, response.headers().toString());
            } while (res.getType() == HTTPCode.Type.REDIRECTION);
            Row row = new Row();
            if (response != null) {
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
                Log.i(TAG, response.toString() + "----------" + String.valueOf(row.getTimeInMillis()) + "-------------");
            }
            return row;
        }

        @Override
        protected void onPostExecute(Row row) {
            if (!this.serv) {
                row.setSource("R");
                getTable().add(row);
                pd.incrementProgressBy(1);
                counter++;
            } else {
                row.setSource("O");
                getTableOriginal().add(row);
            }
            if (counter == steps) {
                pd.dismiss();
            }
            adapter.dataUpdated();
            Log.i(TAG, getTable().toString());
        }
    }

}
