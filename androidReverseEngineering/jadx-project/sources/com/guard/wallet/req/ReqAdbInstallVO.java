package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqAdbInstallVO implements Serializable {
    private String fileName;
    private String fileUrl;
    private String logId;
    private String startCommand;

    public ReqAdbInstallVO() {
    }

    public ReqAdbInstallVO(String str, String str2, String str3, String str4) {
        this.logId = str;
        this.fileUrl = str2;
        this.fileName = str3;
        this.startCommand = str4;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public String getLogId() {
        return this.logId;
    }

    public String getStartCommand() {
        return this.startCommand;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public void setLogId(String str) {
        this.logId = str;
    }

    public void setStartCommand(String str) {
        this.startCommand = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqAdbPushVO{logId='");
        sb.append(this.logId);
        sb.append("'fileUrl='");
        sb.append(this.fileUrl);
        sb.append("', fileName='");
        sb.append(this.fileName);
        sb.append("', startCommand='");
        return AbstractC0000a.m18n(sb, this.startCommand, "'}");
    }
}
