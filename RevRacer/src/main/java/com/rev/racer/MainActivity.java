package com.rev.racer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rev.racer.fragments.MainFragment;
import com.rev.racer.fragments.TaskFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainListener, TaskFragment.OnTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNativeMobile() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, TaskFragment.newInstance())
                .commit();
    }

    @Override
    public void onWeb() {

    }

    @Override
    public void onStartTask() {

    }
}
