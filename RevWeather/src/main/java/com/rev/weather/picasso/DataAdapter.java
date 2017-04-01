package com.rev.weather.picasso;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rev.sdk.RevSDK;
import com.rev.weather.R;
import com.rev.weather.fragments.PicassoFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

//import com.jakewharton.picasso.OkHttp3Downloader;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    private PicassoFragment.OnPicassoListener listener;
    private Picasso picasso;

    public DataAdapter(Context context, ArrayList<AndroidVersion> android_versions, PicassoFragment.OnPicassoListener listener) {
        this.context = context;
        this.android_versions = android_versions;
        OkHttpClient client = RevSDK.OkHttpCreate(10, false, false);
        //picasso = new Picasso.Builder(context).downloader(new RevOkHttpDownloader(RevApp.getInstance().getHTTPClient())).build();
        picasso = new Picasso.Builder(context).downloader(new RevOkHttpDownloader(client)).build();
        this.listener = listener;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        String sLoad = android_versions.get(i).getAndroid_image_url();
        picasso.load(sLoad).resize(120, 60).into(viewHolder.img_android);
        //picasso.load(sLoad).into(viewHolder.img_android);
        if (listener != null) listener.onPicassoLoad(sLoad);
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_android;
        ImageView img_android;

        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView) view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }
}
