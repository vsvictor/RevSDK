package com.rev.sdk.types;

/**
 * Created by victor on 06.03.17.
 */

public class Pair extends Tag {
    public Pair(String name, String value) {
        super(name, value);
    }

    @Override
    public String getValue() {
        return super.getValue().toString();
    }
}
