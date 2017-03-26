package com.rev.racer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by victor on 24.03.17.
 */

public class Table extends ArrayList<Row> implements Comparator<Row> {
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Row r : this) {
            builder.append(r.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public long average() {
        long sum = 0;
        for (Row r : this) {
            sum += r.getTimeInMillis();
        }
        return sum / this.size();
    }

    public long median() {
        if (this.size() == 0) return 0;
        else if (this.size() == 1) return this.get(0).getTimeInMillis();
        else {
            Collections.sort(this, this);

            if (this.size() % 2 == 0) {
                Row m1 = this.get(this.size() / 2);
                Row m2 = this.get((this.size() / 2) - 1);
                return (m1.getTimeInMillis() + m2.getTimeInMillis()) / 2;
            } else {
                return this.get((this.size() / 2)).getTimeInMillis();
            }
        }
    }

    @Override
    public int compare(Row o1, Row o2) {
        if (o1.getTimeInMillis() == o2.getTimeInMillis()) return 0;
        else if (o1.getTimeInMillis() > o2.getTimeInMillis()) return 1;
        else return -1;
    }
}
