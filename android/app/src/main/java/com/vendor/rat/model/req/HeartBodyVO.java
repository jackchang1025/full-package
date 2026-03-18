package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
public class HeartBodyVO extends MessageBodyVO {
    private String containerCode;
    private Integer isOpened;
    private String packageName;
    private Integer serviceState;
    public HeartBodyVO() {
    }
    public HeartBodyVO(String str, String str2, Integer num, Integer num2) {
        this.packageName = str;
        this.containerCode = str2;
        this.isOpened = num;
        this.serviceState = num2;
    }
    public String getContainerCode() {
        return this.containerCode;
    }
    public Integer getIsOpened() {
        return this.isOpened;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public Integer getServiceState() {
        return this.serviceState;
    }
    public void setContainerCode(String str) {
        this.containerCode = str;
    }
    public void setIsOpened(Integer num) {
        this.isOpened = num;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    public void setServiceState(Integer num) {
        this.serviceState = num;
    }
    @NonNull
    public String toString() {
        return "HeartBodyVO{packageName='" + this.packageName + "', containerCode='" + this.containerCode + "', isOpened=" + this.isOpened + ", serviceState=" + this.serviceState + '}';
    }
}
