package com.guard.wallet.condition;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class StringCondition implements Serializable {
    private String contains;
    private String equals;
    private String prefix;
    private String property;
    private String regex;
    private String suffix;

    public StringCondition() {
    }

    public StringCondition(String str, String str2, String str3, String str4, String str5, String str6) {
        this.property = str;
        this.equals = str2;
        this.contains = str3;
        this.prefix = str4;
        this.suffix = str5;
        this.regex = str6;
    }

    public String getContains() {
        return this.contains;
    }

    public String getEquals() {
        return this.equals;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getProperty() {
        return this.property;
    }

    public String getRegex() {
        return this.regex;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setContains(String str) {
        this.contains = str;
    }

    public void setEquals(String str) {
        this.equals = str;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public void setProperty(String str) {
        this.property = str;
    }

    public void setRegex(String str) {
        this.regex = str;
    }

    public void setSuffix(String str) {
        this.suffix = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("StringCondition{property='");
        sb.append(this.property);
        sb.append("', equals='");
        sb.append(this.equals);
        sb.append("', contains='");
        sb.append(this.contains);
        sb.append("', prefix='");
        sb.append(this.prefix);
        sb.append("', suffix='");
        sb.append(this.suffix);
        sb.append("', regex='");
        return AbstractC0000a.m18n(sb, this.regex, "'}");
    }
}
