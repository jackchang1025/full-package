package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
public class LocalDebugEventVO extends MessageBodyVO {
    private String message;
    private String topic;
    private String type;
    public LocalDebugEventVO() {
    }
    public LocalDebugEventVO(String str, String str2, String str3) {
        this.topic = str;
        this.type = str2;
        this.message = str3;
    }
    public String getMessage() {
        return this.message;
    }
    public String getTopic() {
        return this.topic;
    }
    public String getType() {
        return this.type;
    }
    public void setMessage(String str) {
        this.message = str;
    }
    public void setTopic(String str) {
        this.topic = str;
    }
    public void setType(String str) {
        this.type = str;
    }
    @NonNull
    public String toString() {
        return "LocalDebugEventVO{topic='" + this.topic + "', type='" + this.type + "', message='" + this.message + "'}";
    }
}
