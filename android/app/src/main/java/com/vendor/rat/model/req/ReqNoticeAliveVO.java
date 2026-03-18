package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqNoticeAliveVO implements Serializable {
    private String packageName;
    private Long timestamp = Long.valueOf(System.currentTimeMillis());
    public ReqNoticeAliveVO() {
    }
    public ReqNoticeAliveVO(String str) {
        this.packageName = str;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    public void setTimestamp(Long l2) {
        this.timestamp = l2;
    }
    @NonNull
    public String toString() {
        return "ReqNoticeAliveVO{packageName='" + this.packageName + "', timestamp=" + this.timestamp + '}';
    }
}
