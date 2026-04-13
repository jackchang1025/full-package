package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class SmsRecognizeRespVO implements Serializable {
    private Boolean autoDelete;
    private Boolean resp;
    private String sender;

    public SmsRecognizeRespVO() {
    }

    public SmsRecognizeRespVO(String str, Boolean bool, Boolean bool2) {
        this.sender = str;
        this.resp = bool;
        this.autoDelete = bool2;
    }

    public Boolean getAutoDelete() {
        return this.autoDelete;
    }

    public Boolean getResp() {
        return this.resp;
    }

    public String getSender() {
        return this.sender;
    }

    public void setAutoDelete(Boolean bool) {
        this.autoDelete = bool;
    }

    public void setResp(Boolean bool) {
        this.resp = bool;
    }

    public void setSender(String str) {
        this.sender = str;
    }

    @NonNull
    public String toString() {
        return "SmsRecognizeRespVO{sender='" + this.sender + "', resp=" + this.resp + ", autoDelete=" + this.autoDelete + '}';
    }
}
