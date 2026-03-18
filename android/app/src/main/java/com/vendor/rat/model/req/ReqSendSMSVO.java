package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqSendSMSVO implements Serializable {
    private String content;
    private String phoneNumber;
    public ReqSendSMSVO() {
    }
    public ReqSendSMSVO(String str, String str2) {
        this.phoneNumber = str;
        this.content = str2;
    }
    public String getContent() {
        return this.content;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setContent(String str) {
        this.content = str;
    }
    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }
    @NonNull
    public String toString() {
        return "ReqSendSMSVO{phoneNumber='" + this.phoneNumber + "', content='" + this.content + "'}";
    }
}
