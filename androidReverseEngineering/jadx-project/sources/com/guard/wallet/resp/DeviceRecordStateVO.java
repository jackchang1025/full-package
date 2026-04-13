package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import p007j.EnumC0348c;

/* loaded from: classes.dex */
public class DeviceRecordStateVO extends MessageBodyVO {
    private Integer allowRecord;
    private Integer audioSource;
    private String message;
    private EnumC0348c state;

    public DeviceRecordStateVO() {
    }

    public DeviceRecordStateVO(Integer num, Integer num2, EnumC0348c enumC0348c, String str) {
        this.allowRecord = num;
        this.audioSource = num2;
        this.state = enumC0348c;
        this.message = str;
    }

    public Integer getAllowRecord() {
        return this.allowRecord;
    }

    public Integer getAudioSource() {
        return this.audioSource;
    }

    public String getMessage() {
        return this.message;
    }

    public EnumC0348c getState() {
        return this.state;
    }

    public void setAllowRecord(Integer num) {
        this.allowRecord = num;
    }

    public void setAudioSource(Integer num) {
        this.audioSource = num;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setState(EnumC0348c enumC0348c) {
        this.state = enumC0348c;
    }

    @NonNull
    public String toString() {
        return "DeviceRecordStateVO{allowRecord=" + this.allowRecord + ", audioSource=" + this.audioSource + ", state=" + this.state + ", message=" + this.message + '}';
    }
}
