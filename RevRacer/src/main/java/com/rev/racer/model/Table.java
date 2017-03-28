package com.rev.racer.model;

import com.rev.racer.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

public class Table extends ArrayList<Row> implements Comparator<Row> {
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Row r : this) {
            builder.append(r.toString());
            builder.append("\n");
        }
        //builder.append("Average: ");
        //builder.append(this.average());
        //builder.append(" Mediane: ");
        //builder.append(this.median());
        return builder.toString();
    }

    public long average() {
        long sum = 0;
        for (Row r : this) {
            sum += r.getTimeInMillis();
        }
        if (size() == 0) return 0;
        else return sum / this.size();
    }

    public long median() {
        if (this.size() == 0) return 0;
        else if (this.size() == 1) return this.get(0).getTimeInMillis();
        else {
            ArrayList<Row> c = (ArrayList<Row>) this.clone();
            Collections.sort(c, this);

            if (this.size() % 2 == 0) {
                Row m1 = this.get(this.size() / 2);
                Row m2 = this.get((this.size() / 2) - 1);
                return (m1.getTimeInMillis() + m2.getTimeInMillis()) / 2;
            } else {
                return this.get((this.size() / 2)).getTimeInMillis();
            }
        }
    }

    public long min() {
        if (this.size() == 0) return 0;
        Row r = Collections.min(this, this);
        return r.getTimeInMillis();
    }

    public long max() {
        if (this.size() == 0) return 0;
        Row r = Collections.max(this, this);
        return r.getTimeInMillis();
    }

    public long disperse() {
        if (this.size() == 0) return 0;
        long rr = max() - average();
        double sum = 0;
        for (int i = 0; i < this.size(); i++) {
            sum += Math.pow(Math.abs(rr - this.get(i).getTimeInMillis()), 2);
        }
        double dd = sum / this.size();
        return (new Double(dd)).longValue();
    }

    public long standDeviation() {
        if (this.size() == 0) return 0;
        return (new Double(Math.sqrt(disperse()))).longValue();
    }

    @Override
    public int compare(Row o1, Row o2) {
        if (o1.getTimeInMillis() == o2.getTimeInMillis()) return 0;
        else if (o1.getTimeInMillis() > o2.getTimeInMillis()) return 1;
        else return -1;
    }

    public static String toTable(Table real, Table original, int mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("Mode: ");
        builder.append(mode == Const.MODE_CONSISTENTLY ? "Consistently" : "Paralelly");
        builder.append("\n");
        for (int i = 0; i < real.size(); i++) {
            builder.append(i);
            builder.append(": nuu:bit: ");
            builder.append(real.get(i).toTable());
            builder.append(", original : ");
            builder.append(original.get(i).toTable());
            builder.append("\n");
        }
        builder.append("----------------------------------");
        builder.append("\n");
        builder.append("NUU:Bit\n");
        builder.append("Min: ");
        builder.append(real.min());
        builder.append(" Max: ");
        builder.append(real.max());
        builder.append(" Average: ");
        builder.append(real.average());
        builder.append(" Mediane: ");
        builder.append(real.median());
        builder.append(" Stand. deviation: ");
        builder.append(real.standDeviation());
        builder.append("\n");
        builder.append("Original\n");
        builder.append("Min: ");
        builder.append(original.min());
        builder.append(" Max: ");
        builder.append(original.max());
        builder.append(" Average: ");
        builder.append(original.average());
        builder.append(" Mediane: ");
        builder.append(original.median());
        builder.append(" Stand. deviation: ");
        builder.append(original.standDeviation());
        builder.append("\n");
        return builder.toString();
    }
}
