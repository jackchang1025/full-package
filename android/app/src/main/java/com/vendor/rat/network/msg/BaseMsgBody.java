package com.vendor.rat.network.msg;

import java.io.Serializable;

public class BaseMsgBody implements Serializable {
    private final Long timestamp = Long.valueOf(System.currentTimeMillis());

    public Long getTimestamp() {
        return this.timestamp;
    }
}
