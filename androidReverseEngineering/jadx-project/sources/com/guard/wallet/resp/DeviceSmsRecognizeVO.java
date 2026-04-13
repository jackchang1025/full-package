package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DeviceSmsRecognizeVO extends MessageBodyVO {
    private String content;
    private String deviceId;
    private String plugId;
    private String recognizeContent;
    private String sender;

    public DeviceSmsRecognizeVO() {
    }

    public DeviceSmsRecognizeVO(String str, String str2, String str3, String str4, String str5) {
        this.deviceId = str;
        this.plugId = str2;
        this.sender = str3;
        this.content = str4;
        this.recognizeContent = str5;
    }

    public String getContent() {
        return this.content;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getPlugId() {
        return this.plugId;
    }

    public String getRecognizeContent() {
        return this.recognizeContent;
    }

    public String getSender() {
        return this.sender;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setPlugId(String str) {
        this.plugId = str;
    }

    public void setRecognizeContent(String str) {
        this.recognizeContent = str;
    }

    public void setSender(String str) {
        this.sender = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceSmsRecognizeVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("',plugId='");
        sb.append(this.plugId);
        sb.append("', sender='");
        sb.append(this.sender);
        sb.append("', content='");
        sb.append(this.content);
        sb.append("', recognizeContent='");
        return AbstractC0000a.m18n(sb, this.recognizeContent, "'}");
    }
}
