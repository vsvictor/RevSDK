package com.nuubit.sdk.utils;

import com.nuubit.sdk.config.OperationMode;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by victor on 10.04.17.
 */

public class ABTester {
    private int percent;
    private boolean[] data;
    private boolean mode;
    private OperationMode realOperatiomMode;

    public ABTester() {
        this(50);
    }

    public ABTester(int percent) {
        this.percent = percent;
        data = new boolean[100];
    }

    public void init() {
        ArrayList<Boolean> test = new ArrayList<Boolean>();
        for (int i = percent; i < 100; i++) {
            test.add(new Boolean(true));
        }
        for (int i = 0; i < percent; i++) {
            test.add(new Boolean(false));
        }
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int r = random.nextInt(test.size());
            data[i] = test.get(r);
            test.remove(r);
        }
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void generate(){
        Random random = new Random();
        mode = data[random.nextInt(100)];
    }

    public boolean isAMode() {
        return mode;
    }

    public OperationMode getRealOperatiomMode() {
        return realOperatiomMode;
    }

    public void setRealOperatiomMode(OperationMode realOperatiomMode) {
        this.realOperatiomMode = realOperatiomMode;
    }
}
