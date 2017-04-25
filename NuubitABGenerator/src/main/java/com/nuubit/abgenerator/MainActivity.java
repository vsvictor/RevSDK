package com.nuubit.abgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nuubit.sdk.utils.ABTester;

public class MainActivity extends AppCompatActivity {

    private ABTester tester;
    private SeekBar sbPersent;
    private TextView tvText;
    private RelativeLayout rlGenerate;
    private RelativeLayout rlCount10;
    private TextView tvText10;
    private RelativeLayout rlCount100;
    private TextView tvText100;
    private RelativeLayout rlCount1000;
    private TextView tvText1000;
    private RelativeLayout rlCount10000;
    private TextView tvText10000;
    private RelativeLayout rlCount100000;
    private TextView tvText100000;
    private RelativeLayout rlCount1000000;
    private TextView tvText1000000;
    private RelativeLayout rlCount10000000;
    private TextView tvText10000000;

    private long aCount = 0;
    private long bCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_header));

        tester = new ABTester();

        tvText = (TextView) findViewById(R.id.tvText);
        sbPersent = (SeekBar) findViewById(R.id.sb_ab_percent);
        sbPersent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvText.setText("A "+String.valueOf(100-progress)+"%,  B "+String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbPersent.setProgress(50);
        rlGenerate = (RelativeLayout) findViewById(R.id.rlGenerate);
        rlGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(rlCount10, tvText10, 10);
                calculate(rlCount100, tvText100, 100);
                calculate(rlCount1000, tvText1000, 1000);
                calculate(rlCount10000, tvText10000, 10000);
                calculate(rlCount100000, tvText100000, 100000);
                calculate(rlCount1000000, tvText1000000, 1000000);
                //calculate(rlCount10000000, tvText10000000, 10000000);

            }
        });
        rlCount10 = (RelativeLayout) findViewById(R.id.rlCount10);
        tvText10 = (TextView) findViewById(R.id.tvText_10);

        rlCount100 = (RelativeLayout) findViewById(R.id.rlCount100);
        tvText100 = (TextView) findViewById(R.id.tvText_100);

        rlCount1000 = (RelativeLayout) findViewById(R.id.rlCount1000);
        tvText1000 = (TextView) findViewById(R.id.tvText_1000);

        rlCount10000 = (RelativeLayout) findViewById(R.id.rlCount10000);
        tvText10000 = (TextView) findViewById(R.id.tvText_10000);

        rlCount100000 = (RelativeLayout) findViewById(R.id.rlCount100000);
        tvText100000 = (TextView) findViewById(R.id.tvText_100000);

        rlCount1000000 = (RelativeLayout) findViewById(R.id.rlCount1000000);
        tvText1000000 = (TextView) findViewById(R.id.tvText_1000000);
        rlCount10000000 = (RelativeLayout) findViewById(R.id.rlCount10000000);
        tvText10000000 = (TextView) findViewById(R.id.tvText_10000000);
    }
    private void calculate(final RelativeLayout rl, final TextView tv, final long count){
        StringBuilder builder = new StringBuilder();
        tester.setPercent(sbPersent.getProgress());
        tester.init();
        aCount = 0;
        bCount = 0;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rl.setVisibility(View.VISIBLE);
            }
        });

        for(int i = 0; i<count; i++){
            tester.generate();
            if(tester.isAMode()) aCount++;
            else bCount++;
        }
        //int aPercent = Math.round(100f/((float) count)* ((float)aCount));
        //int bPercent = Math.round(100f/((float) count)* ((float)bCount));
        float aPercent = 100f/((float) count)* ((float)aCount);
        float bPercent = 100f/((float) count)* ((float)bCount);

        builder.append("A mode: ");
        builder.append(aCount);
        builder.append(", B mode: ");
        builder.append(bCount);
        builder.append(",\n A - ");
        builder.append( String.format("%.2f", aPercent));
        builder.append("% ");
        builder.append(", B - ");
        builder.append( String.format("%.2f", bPercent));
        builder.append("%");
        final String s = builder.toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(s);
            }
        });

    }
}
