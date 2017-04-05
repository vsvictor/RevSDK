package com.rev.sdk.types;

/**
 * Created by victor on 05.04.17.
 */

public class PairLong extends Tag {
    public PairLong(String name, long value) {
        super(name, value);
    }

    @Override
    public Long getValue() {
        return (Long) super.getValue();
    }
}
