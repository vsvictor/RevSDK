package com.rev.racer.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rev.racer.Const;
import com.rev.racer.MainActivity;
import com.rev.racer.R;
import com.rev.racer.model.Row;
import com.rev.racer.model.Table;
import com.rev.sdk.Constants;
import com.rev.sdk.RevSDK;
import com.rev.sdk.types.HTTPCode;
import com.rev.sdk.types.Tag;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.HttpUrl;
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

public class ResultFragment extends Fragment {
    private static final String TAG = ResultFragment.class.getSimpleName();
    private Typeface main;
    private LinearLayout llBackground;
    private RelativeLayout rlMainContainer;
    private Animation racer;

    private int steps;
    private long size;
    private HttpUrl url;
    private String method;
    private String stype;

    //private Table table;
    private RecyclerView rvResult;
    private ResultAdapter adapter;
    private TextView tvAverage;
    private TextView tvMedian;
    private FloatingActionButton fbEMail;
    private Table all;

    //private HttpUrl url;
    private OkHttpClient client = RevSDK.OkHttpCreate(Constants.DEFAULT_TIMEOUT_SEC, false, false);

    public ResultFragment() {
        this.all = new Table();
    }

    public static ResultFragment newInstance(int steps, long body, String url, String method, String type) {
        ResultFragment fragment = new ResultFragment();
        Bundle data = new Bundle();
        data.putInt(Const.STEPS, steps);
        data.putLong(Const.SIZE, body);
        data.putString(Const.URL, url);
        data.putString(Const.METHOD, method);
        data.putString(Const.TYPE, type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        steps = data.getInt(Const.STEPS);
        size = data.getLong(Const.SIZE);
        url = HttpUrl.parse(data.getString(Const.URL));
        method = data.getString(Const.METHOD);
        stype = data.getString(Const.TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = Typeface.createFromAsset(getActivity().getAssets(), "fonts/crochet.ttf");
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        racer = AnimationUtils.loadAnimation(getActivity(), R.anim.racer_result);
        //adapter = new ResultAdapter(getActivity(), ((MainActivity) getActivity()).getTable());
        adapter = new ResultAdapter(getActivity(), all);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        llBackground = (LinearLayout) view.findViewById(R.id.llMainFragmentContainer);
        rlMainContainer = (RelativeLayout) view.findViewById(R.id.rlMainContainer);
        //rlMainContainer.startAnimation(racer);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvResult.setAdapter(adapter);
        tvAverage = (TextView) view.findViewById(R.id.tvAverage);
        tvMedian = (TextView) view.findViewById(R.id.tvMedian);
        fbEMail = (FloatingActionButton) view.findViewById(R.id.fbEmail);
        fbEMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                builder.append(all.toString());
                builder.append("\n");
                builder.append("Average: ");
                builder.append(((MainActivity) getActivity()).getTable().average());
                builder.append(" Mediane: ");
                builder.append(((MainActivity) getActivity()).getTable().median());

                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("message/rfc822")
                        .addEmailTo(((MainActivity) getActivity()).getEMail())
                        .setSubject("Racer")
                        .setText(builder.toString())
                        //.setHtmlText(body) //If you are using HTML in your body text
                        .setChooserTitle("Select:")
                        .startChooser();
            }
        });
        startTask();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void startTask() {
        ((MainActivity) getActivity()).getTable().clear();
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
                ;
                ((MainActivity) getActivity()).getTable().add(row);
            } else {
                row.setSource("O");
                ((MainActivity) getActivity()).getTableOriginal().add(row);
            }
            all.add(row);
            tvAverage.setText("Average: " + String.valueOf(((MainActivity) getActivity()).getTable().average()));
            tvMedian.setText("Mediane: " + String.valueOf(((MainActivity) getActivity()).getTable().median()));
            adapter.notifyDataSetChanged();
            Log.i(TAG, ((MainActivity) getActivity()).getTable().toString());
        }
    }

    public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
        private Context context;
        private Table data;

        public ResultAdapter(Context context, Table table) {
            this.context = context;
            this.data = table;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.result_item, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int pos) {

            holder.mItem = data.get(pos);
            if ((pos) % 2 == 0)
                holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGray));
            else
                holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrayLight));
            holder.tvSource.setText(data.get(pos).getSource());
            holder.tvStart.setText(String.valueOf(data.get(pos).getStart()));
            holder.tvFinish.setText(String.valueOf(data.get(pos).getFinish()));
            holder.tvTime.setText(String.valueOf(data.get(pos).getTimeInMillis()));
            holder.tvBody.setText(String.valueOf(data.get(pos).getBody()));
            holder.tvPayload.setText(String.valueOf(data.get(pos).getPayload()));
            holder.cvMain.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((MainActivity) getActivity()).getTable().clear();
                    notifyDataSetChanged();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;
            public final CardView cvMain;
            public final TextView tvSource;
            public final TextView tvStart;
            public final TextView tvFinish;
            public final TextView tvTime;
            public final TextView tvBody;
            public final TextView tvPayload;
            public Row mItem;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                this.cvMain = (CardView) view.findViewById(R.id.cvMain);
                tvSource = (TextView) view.findViewById(R.id.tvSource);
                tvStart = (TextView) view.findViewById(R.id.tvStart);
                tvFinish = (TextView) view.findViewById(R.id.tvFinish);
                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvBody = (TextView) view.findViewById(R.id.tvBody);
                tvPayload = (TextView) view.findViewById(R.id.tvPayload);
            }
        }
    }
}
