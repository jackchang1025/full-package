package com.guard.wallet.req;

import java.io.Serializable;
import p014r.EnumC0889b;

/* loaded from: classes.dex */
public class SearchFieldVO implements Serializable {
    private EnumC0889b compare;
    private Integer isTimestamp;
    private String name;
    private Object value;

    public SearchFieldVO() {
    }

    public SearchFieldVO(String str, Object obj, EnumC0889b enumC0889b, Integer num) {
        this.name = str;
        this.value = obj;
        this.compare = enumC0889b;
        this.isTimestamp = num;
    }

    public EnumC0889b getCompare() {
        return this.compare;
    }

    public Integer getIsTimestamp() {
        return this.isTimestamp;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setCompare(EnumC0889b enumC0889b) {
        this.compare = enumC0889b;
    }

    public void setIsTimestamp(Integer num) {
        this.isTimestamp = num;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }
}
