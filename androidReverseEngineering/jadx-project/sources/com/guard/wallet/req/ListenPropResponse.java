package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ListenPropResponse implements Serializable {
    private String prop;
    private Integer targetIndex;
    public Long timestamp;
    private String value;

    public ListenPropResponse() {
        this.timestamp = Long.valueOf(System.nanoTime());
    }

    public String getProp() {
        return this.prop;
    }

    public Integer getTargetIndex() {
        return this.targetIndex;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public String getValue() {
        return this.value;
    }

    public void setProp(String str) {
        this.prop = str;
    }

    public void setTargetIndex(Integer num) {
        this.targetIndex = num;
    }

    public void setTimestamp(Long l2) {
        this.timestamp = l2;
    }

    public void setValue(String str) {
        this.value = str;
    }

    @NonNull
    public String toString() {
        return "ListenPropResponse{targetIndex=" + this.targetIndex + ", prop='" + this.prop + "', value='" + this.value + "', timestamp='" + this.timestamp + "'}";
    }

    public ListenPropResponse(Integer num, String str, String str2, Long l2) {
        this.targetIndex = num;
        this.prop = str;
        this.value = str2;
        this.timestamp = l2;
    }
}
