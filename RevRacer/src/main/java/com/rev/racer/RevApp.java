package com.rev.racer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;

import com.rev.sdk.Constants;
import com.rev.sdk.RevApplication;

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

public class RevApp extends RevApplication {
    private SharedPreferences settings;
    private String email;
    private static RevApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        settings = getSharedPreferences(Const.DATA, MODE_PRIVATE);
        email = settings.getString(Const.EMAIL, Constants.UNDEFINED);
        if (email.equalsIgnoreCase(Constants.UNDEFINED)) {
            AlertDialog dialog = email("");
            dialog.show();
        }
    }

    public static RevApp getInstance() {
        return instance;
    }

    public String getEMail() {
        return email;
    }

    public AlertDialog email(String aEMail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Const.EMAIL);
        View view = LayoutInflater.from(this).inflate(R.layout.email_layout, null);
        final AppCompatEditText edInput = (AppCompatEditText) view.findViewById(R.id.edEMail);
        edInput.setText(aEMail);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //email = input.getText().toString();
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(Const.EMAIL, edInput.getText().toString());
                editor.commit();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    public SharedPreferences getSettings() {
        return settings;
    }
}
