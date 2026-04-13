package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceSmsRecognizeVO extends MessageBodyVO {
    private String content;
    private String deviceId;
    private String plugId;
    private String recognizeContent;
    private String sender;

    public DeviceSmsRecognizeVO() {}
    public DeviceSmsRecognizeVO(String deviceId, String plugId, String sender, String content, String recognizeContent) {
        this.deviceId = deviceId; this.plugId = plugId; this.sender = sender;
        this.content = content; this.recognizeContent = recognizeContent;
    }

    public String getContent() { return this.content; }
    public String getDeviceId() { return this.deviceId; }
    public String getPlugId() { return this.plugId; }
    public String getRecognizeContent() { return this.recognizeContent; }
    public String getSender() { return this.sender; }
    public void setContent(String v) { this.content = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setPlugId(String v) { this.plugId = v; }
    public void setRecognizeContent(String v) { this.recognizeContent = v; }
    public void setSender(String v) { this.sender = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceSmsRecognizeVO{deviceId='" + this.deviceId + "',plugId='" + this.plugId
                + "', sender='" + this.sender + "', content='" + this.content
                + "', recognizeContent='" + this.recognizeContent + "'}";
    }
}
