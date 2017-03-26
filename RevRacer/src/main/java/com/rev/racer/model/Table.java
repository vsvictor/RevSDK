package com.rev.racer.model;

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

    @Override
    public int compare(Row o1, Row o2) {
        if (o1.getTimeInMillis() == o2.getTimeInMillis()) return 0;
        else if (o1.getTimeInMillis() > o2.getTimeInMillis()) return 1;
        else return -1;
    }
}
