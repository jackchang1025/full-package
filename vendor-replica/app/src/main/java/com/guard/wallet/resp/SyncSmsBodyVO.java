package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

public class SyncSmsBodyVO extends MessageBodyVO {
    private String deviceId;
    private List<SmsMessageVO> messages;

    public SyncSmsBodyVO() {}
    public SyncSmsBodyVO(String deviceId, List<SmsMessageVO> messages) {
        this.deviceId = deviceId; this.messages = messages;
    }

    public String getDeviceId() { return this.deviceId; }
    public List<SmsMessageVO> getMessages() { return this.messages; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setMessages(List<SmsMessageVO> v) { this.messages = v; }

    @NonNull
    @Override
    public String toString() {
        return "SmsBodyVO{deviceId='" + this.deviceId + "', messages=" + this.messages + "}";
    }
}
