package com.nuubit.racer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.nuubit.sdk.views.CountersFragment;

import okhttp3.OkHttpClient;

public class ParalellyWebActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
                String sData = Table.toTable(ParalellyWebActivity.this, getTable(), getTableOriginal(), Const.MODE_PARALELLY, url, method);
                ShareCompat.IntentBuilder.from(ParalellyWebActivity.this)
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
        //tvMethodMode.setText(method + " , " + getResources().getString(R.string.parallel));

    }

    public String getMethodMode() {
        return method + " , " + getResources().getString(R.string.parallel);
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
        public void onBindViewHolder(ResultAdapter.ViewHolder holder, int pos) {

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

}