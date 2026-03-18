package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
public class SmsMessageVO extends MessageBodyVO {
    private String content;
    private String sender;
    private String senderName;
    private String smsFormat;
    private String smsTime;
    private Integer smsType;
    public SmsMessageVO() {
    }
    public SmsMessageVO(String str, String str2, String str3, String str4, String str5, Integer num) {
        this.sender = str;
        this.senderName = str2;
        this.content = str3;
        this.smsFormat = str4;
        this.smsTime = str5;
        this.smsType = num;
    }
    public String getContent() { return this.content; }
    public String getSender() { return this.sender; }
    public String getSenderName() { return this.senderName; }
    public String getSmsFormat() { return this.smsFormat; }
    public String getSmsTime() { return this.smsTime; }
    public Integer getSmsType() { return this.smsType; }
    public void setContent(String str) { this.content = str; }
    public void setSender(String str) { this.sender = str; }
    public void setSenderName(String str) { this.senderName = str; }
    public void setSmsFormat(String str) { this.smsFormat = str; }
    public void setSmsTime(String str) { this.smsTime = str; }
    public void setSmsType(Integer num) { this.smsType = num; }
    @NonNull
    public String toString() {
        return "SmsMessageVO{sender='" + this.sender + "', senderName='" + this.senderName + "', content='" + this.content + "', smsFormat='" + this.smsFormat + "', smsTime='" + this.smsTime + "', smsType='" + this.smsType + "'}";
    }
}
