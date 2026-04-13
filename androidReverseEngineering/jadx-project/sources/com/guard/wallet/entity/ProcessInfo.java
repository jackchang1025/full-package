package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ProcessInfo implements Serializable {
    private int parentId;
    private int processGroupId;
    private int processId;
    private int realUserId;
    private String stat;
    private String user;

    public ProcessInfo() {
    }

    public ProcessInfo(int i2, int i3, int i4, int i5, String str, String str2) {
        this.processId = i2;
        this.parentId = i3;
        this.processGroupId = i4;
        this.realUserId = i5;
        this.user = str;
        this.stat = str2;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getProcessGroupId() {
        return this.processGroupId;
    }

    public int getProcessId() {
        return this.processId;
    }

    public int getRealUserId() {
        return this.realUserId;
    }

    public String getStat() {
        return this.stat;
    }

    public String getUser() {
        return this.user;
    }

    public void setParentId(int i2) {
        this.parentId = i2;
    }

    public void setProcessGroupId(int i2) {
        this.processGroupId = i2;
    }

    public void setProcessId(int i2) {
        this.processId = i2;
    }

    public void setRealUserId(int i2) {
        this.realUserId = i2;
    }

    public void setStat(String str) {
        this.stat = str;
    }

    public void setUser(String str) {
        this.user = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ProcessInfo{processId=");
        sb.append(this.processId);
        sb.append(", parentId=");
        sb.append(this.parentId);
        sb.append(", processGroupId=");
        sb.append(this.processGroupId);
        sb.append(", realUserId=");
        sb.append(this.realUserId);
        sb.append(", user='");
        sb.append(this.user);
        sb.append("', stat='");
        return AbstractC0000a.m18n(sb, this.stat, "'}");
    }
}
