package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class AppLocateValueVO implements Serializable {
    private String locateCode;
    private String locateValue;
    public AppLocateValueVO() {
    }
    public AppLocateValueVO(String str, String str2) {
        this.locateCode = str;
        this.locateValue = str2;
    }
    public String getLocateCode() {
        return this.locateCode;
    }
    public String getLocateValue() {
        return this.locateValue;
    }
    public void setLocateCode(String str) {
        this.locateCode = str;
    }
    public void setLocateValue(String str) {
        this.locateValue = str;
    }
    @NonNull
    public String toString() {
        return "AppLocateValueVO{locateCode='" + this.locateCode + "', locateValue='" + this.locateValue + "'}";
    }
}
