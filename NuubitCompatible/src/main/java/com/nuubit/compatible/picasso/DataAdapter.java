package com.nuubit.compatible.picasso;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuubit.compatible.R;
import com.nuubit.compatible.fragments.PicassoFragment;
import com.nuubit.sdk.NuubitSDK;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

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


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    private PicassoFragment.OnPicassoListener listener;
    private Picasso picasso;

    public DataAdapter(Context context, ArrayList<AndroidVersion> android_versions, PicassoFragment.OnPicassoListener listener) {
        this.context = context;
        this.android_versions = android_versions;
        OkHttpClient client = NuubitSDK.OkHttpCreate(10, false, false);
        picasso = new Picasso.Builder(context).downloader(new NuubitOkHttpDownloader(client)).build();
        this.listener = listener;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        String sLoad = android_versions.get(i).getAndroid_image_url();
        picasso.load(sLoad).resize(120, 60).into(viewHolder.img_android);

        if (listener != null) listener.onPicassoLoad(sLoad);
        viewHolder.cvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = null;
                switch (i) {
                    case 0: {
                        sText = context.getResources().getString(R.string.donut);
                        break;
                    }
                    case 1: {
                        sText = context.getResources().getString(R.string.eclair);
                        break;
                    }
                    case 2: {
                        sText = context.getResources().getString(R.string.froyo);
                        break;
                    }
                    case 3: {
                        sText = context.getResources().getString(R.string.ginger);
                        break;
                    }
                    case 4: {
                        sText = context.getResources().getString(R.string.honey);
                        break;
                    }
                    case 5: {
                        sText = context.getResources().getString(R.string.icecream);
                        break;
                    }
                    case 6: {
                        sText = context.getResources().getString(R.string.jellybean);
                        break;
                    }
                    case 7: {
                        sText = context.getResources().getString(R.string.kitkat);
                        break;
                    }
                    case 8: {
                        sText = context.getResources().getString(R.string.lollipop);
                        break;
                    }
                    case 9: {
                        sText = context.getResources().getString(R.string.marshmallow);
                        break;
                    }
                }
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(android_versions.get(i).getAndroid_version_name());
                View view = LayoutInflater.from(context).inflate(R.layout.dialog, null, false);
                TextView tv = (TextView) view.findViewById(R.id.tvText);
                tv.setText(sText);
                builder.setView(view);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CardView cvCard;
        final TextView tv_android;
        final ImageView img_android;

        public ViewHolder(View view) {
            super(view);
            cvCard = (CardView) view.findViewById(R.id.cvCard);
            tv_android = (TextView) view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }
}
