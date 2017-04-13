package com.nuubit.sdk.statistic.counters;

import android.content.SharedPreferences;

import com.nuubit.sdk.types.Pair;

import java.util.ArrayList;

/**
 * Created by victor on 12.04.17.
 */

public interface OnCounters {
    void save(SharedPreferences shared);

    void load(SharedPreferences shared);

    ArrayList<Pair> toArray();
}
