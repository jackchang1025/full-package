package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ProcessInfo implements Serializable {
    private int parentId;
    private int processGroupId;
    private int processId;
    private int realUserId;
    private String stat;
    private String user;

    public ProcessInfo() {
    }

    public ProcessInfo(int processId, int parentId, int processGroupId, int realUserId,
                       String user, String stat) {
        this.processId = processId;
        this.parentId = parentId;
        this.processGroupId = processGroupId;
        this.realUserId = realUserId;
        this.user = user;
        this.stat = stat;
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

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setProcessGroupId(int processGroupId) {
        this.processGroupId = processGroupId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public void setRealUserId(int realUserId) {
        this.realUserId = realUserId;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProcessInfo{processId=" + this.processId
                + ", parentId=" + this.parentId
                + ", processGroupId=" + this.processGroupId
                + ", realUserId=" + this.realUserId
                + ", user='" + this.user
                + "', stat='" + this.stat + "'}";
    }
}
