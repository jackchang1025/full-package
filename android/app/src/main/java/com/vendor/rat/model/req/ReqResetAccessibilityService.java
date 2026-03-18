package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqResetAccessibilityService implements Serializable {
    private String serviceName;
    public ReqResetAccessibilityService() {
    }
    public ReqResetAccessibilityService(String str) {
        this.serviceName = str;
    }
    public String getServiceName() {
        return this.serviceName;
    }
    public void setServiceName(String str) {
        this.serviceName = str;
    }
    @NonNull
    public String toString() {
        return "ReqResetAccessibilityService{serviceName='" + this.serviceName + "'}";
    }
}
