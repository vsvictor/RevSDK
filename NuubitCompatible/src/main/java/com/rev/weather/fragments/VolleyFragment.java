package com.rev.weather.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.rev.weather.NuubitApp;
import com.rev.weather.R;
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

public class VolleyFragment extends Fragment {

    private final static String sIMAGE_URL = "https://goo.gl/XOXAXG";
    private ImageView nivImageView;

    private OnVolleyListener listener;

    public VolleyFragment() {
    }

    public static VolleyFragment newInstance() {
        VolleyFragment fragment = new VolleyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_volley, container, false);
        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        nivImageView = (ImageView) view.findViewById(R.id.ivImageView);
        final ImageRequest imageRequest =
                new ImageRequest
                        (
                                sIMAGE_URL,
                                new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        nivImageView.setImageBitmap(bitmap);
                                        if (listener != null) listener.onVolleyLoad();
                                    }
                                },
                                0,
                                0,
                                ImageView.ScaleType.CENTER_INSIDE,
                                Bitmap.Config.ARGB_8888,
                                new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        nivImageView.setImageResource(R.drawable.image_cloud_sad);
                                    }
                                }
                        );

        NuubitApp.addRequest(imageRequest, sIMAGE_URL);
    }

    @Override
    public void onAttach(Context context) throws RuntimeException {
        super.onAttach(context);
        if (context instanceof OnVolleyListener) {
            listener = (OnVolleyListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnVolleyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnVolleyListener {
        void onVolleyLoad();
    }
}
