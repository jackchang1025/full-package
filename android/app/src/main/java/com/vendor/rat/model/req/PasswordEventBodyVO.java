package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
public class PasswordEventBodyVO extends MessageBodyVO {
    private String lockBatchId;
    private String password;
    public PasswordEventBodyVO() {
    }
    public PasswordEventBodyVO(String str, String str2) {
        this.password = str;
        this.lockBatchId = str2;
    }
    public String getLockBatchId() {
        return this.lockBatchId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setLockBatchId(String str) {
        this.lockBatchId = str;
    }
    public void setPassword(String str) {
        this.password = str;
    }
    @NonNull
    public String toString() {
        return "PasswordEventBodyVO{password='" + this.password + "', lockBatchId='" + this.lockBatchId + "'}";
    }
}
