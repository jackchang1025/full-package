package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class SmsRecognizeRespVO implements Serializable {
    private Boolean autoDelete;
    private Boolean resp;
    private String sender;

    public SmsRecognizeRespVO() {}
    public SmsRecognizeRespVO(String sender, Boolean resp, Boolean autoDelete) {
        this.sender = sender; this.resp = resp; this.autoDelete = autoDelete;
    }

    public Boolean getAutoDelete() { return this.autoDelete; }
    public Boolean getResp() { return this.resp; }
    public String getSender() { return this.sender; }
    public void setAutoDelete(Boolean v) { this.autoDelete = v; }
    public void setResp(Boolean v) { this.resp = v; }
    public void setSender(String v) { this.sender = v; }

    @NonNull
    @Override
    public String toString() {
        return "SmsRecognizeRespVO{sender='" + this.sender + "', resp=" + this.resp
                + ", autoDelete=" + this.autoDelete + "}";
    }
}
