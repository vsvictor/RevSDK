package com.rev.revdemo.fragments;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rev.revdemo.R;
import com.rev.revsdk.utils.Pair;

import java.util.List;

public class PairRecyclerViewAdapter extends RecyclerView.Adapter<PairRecyclerViewAdapter.ViewHolder> {

    private static Context context;
    private final List<Pair> mValues;
    private final OnClickPair mListener;

    public PairRecyclerViewAdapter(Context context, List<Pair> items, OnClickPair listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (position % 2 == 0)
            holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGray));
        else
            holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrayLight));
        holder.tvName.setText(mValues.get(position).getName());
        holder.tvValue.setText(mValues.get(position).getValue());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClick(new Pair(holder.tvName.getText().toString(), holder.tvValue.getText().toString()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final CardView cvMain;
        public final TextView tvName;
        public final TextView tvValue;
        public Pair mItem;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.cvMain = (CardView) view.findViewById(R.id.cvMain);
            tvName = (TextView) view.findViewById(R.id.id);
            tvValue = (TextView) view.findViewById(R.id.content);
        }
/*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
*/
    }

    public static OnClickPair listener = new OnClickPair() {
        @Override
        public void onClick(Pair pair) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_param, null, false);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(pair.getName());
            TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
            tvValue.setText(pair.getValue());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Configuration parameter:").setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
}
