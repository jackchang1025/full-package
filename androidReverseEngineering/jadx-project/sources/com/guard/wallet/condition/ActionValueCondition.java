package com.guard.wallet.condition;

import java.io.Serializable;

/* loaded from: classes.dex */
public class ActionValueCondition implements Serializable {
    private String key;
    private String type;
    private String value;

    public ActionValueCondition() {
    }

    public ActionValueCondition(String str, String str2, String str3) {
        this.type = str;
        this.key = str2;
        this.value = str3;
    }

    public String getKey() {
        return this.key;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
