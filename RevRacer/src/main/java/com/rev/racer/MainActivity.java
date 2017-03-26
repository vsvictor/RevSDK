package com.rev.racer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rev.racer.fragments.MainFragment;
import com.rev.racer.fragments.ResultFragment;
import com.rev.racer.fragments.TaskFragment;
import com.rev.racer.model.Table;
import com.rev.sdk.Constants;
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

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener, TaskFragment.OnTaskListener {
    public static Fragment current;
    private Table table;
    private Table tableOriginal;
    private SharedPreferences settings;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        current = MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
        table = new Table();
        tableOriginal = new Table();
        settings = getSharedPreferences(Const.DATA, MODE_PRIVATE);
        email = settings.getString(Const.EMAIL, Constants.UNDEFINED);
        if (email.equalsIgnoreCase(Constants.UNDEFINED)) {
            AlertDialog dialog = email("");
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (current instanceof TaskFragment) {
            current = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, current)
                    .commit();
        } else if (current instanceof ResultFragment) {
            current = TaskFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, current)
                    .commit();
        } else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //startActivity(new Intent(this, SettingActivity.class));
            email(email).show();
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

    @Override
    public void onNativeMobile() {
        current = TaskFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
    }

    @Override
    public void onWeb() {

    }

    @Override
    public void onStartTask(int steps, long body, String url, String method, String type) {
        current = ResultFragment.newInstance(steps, body, url, method, type);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, current)
                .commit();
    }


}
