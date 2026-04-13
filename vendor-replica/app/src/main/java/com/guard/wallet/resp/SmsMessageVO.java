package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class SmsMessageVO extends MessageBodyVO {
    private String content;
    private String sender;
    private String senderName;
    private String smsFormat;
    private String smsTime;
    private Integer smsType;

    public SmsMessageVO() {}
    public SmsMessageVO(String sender, String senderName, String content, String smsFormat, String smsTime, Integer smsType) {
        this.sender = sender; this.senderName = senderName; this.content = content;
        this.smsFormat = smsFormat; this.smsTime = smsTime; this.smsType = smsType;
    }

    public String getContent() { return this.content; }
    public String getSender() { return this.sender; }
    public String getSenderName() { return this.senderName; }
    public String getSmsFormat() { return this.smsFormat; }
    public String getSmsTime() { return this.smsTime; }
    public Integer getSmsType() { return this.smsType; }
    public void setContent(String v) { this.content = v; }
    public void setSender(String v) { this.sender = v; }
    public void setSenderName(String v) { this.senderName = v; }
    public void setSmsFormat(String v) { this.smsFormat = v; }
    public void setSmsTime(String v) { this.smsTime = v; }
    public void setSmsType(Integer v) { this.smsType = v; }

    @NonNull
    @Override
    public String toString() {
        return "SmsMessageVO{sender='" + this.sender + "', senderName='" + this.senderName
                + "', content='" + this.content + "', smsFormat='" + this.smsFormat
                + "', smsTime='" + this.smsTime + "', smsType='" + this.smsType + "'}";
    }
}
