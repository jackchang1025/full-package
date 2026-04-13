package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

/* loaded from: classes.dex */
public class SyncSmsBodyVO extends MessageBodyVO {
    private String deviceId;
    private List<SmsMessageVO> messages;

    public SyncSmsBodyVO() {
    }

    public SyncSmsBodyVO(String str, List<SmsMessageVO> list) {
        this.deviceId = str;
        this.messages = list;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public List<SmsMessageVO> getMessages() {
        return this.messages;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setMessages(List<SmsMessageVO> list) {
        this.messages = list;
    }

    @NonNull
    public String toString() {
        return "SmsBodyVO{deviceId='" + this.deviceId + "', messages=" + this.messages + '}';
    }
}
