package com.vendor.rat.model.resp;

// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp

import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;

public class PowerControlStateVO extends MessageBodyVO {
    private Boolean allowAllFullBackground;
    private Boolean allowAutoStart;
    private Boolean allowPopupInBackground;
    private Boolean allowRelateStart;
    private String deviceId;
    private String packageName;
    private int retryCount;

    public PowerControlStateVO() {
        Boolean bool = Boolean.FALSE;
        this.allowAllFullBackground = bool;
        this.allowPopupInBackground = bool;
        this.allowAutoStart = bool;
        this.allowRelateStart = bool;
        this.retryCount = 0;
    }

    public PowerControlStateVO(String str, String str2, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4, int i2) {
        this.deviceId = str;
        this.packageName = str2;
        this.allowAllFullBackground = bool;
        this.allowPopupInBackground = bool2;
        this.allowAutoStart = bool3;
        this.allowRelateStart = bool4;
        this.retryCount = i2;
    }

    public Boolean getAllowAllFullBackground() { return this.allowAllFullBackground; }
    public Boolean getAllowAutoStart() { return this.allowAutoStart; }
    public Boolean getAllowPopupInBackground() { return this.allowPopupInBackground; }
    public Boolean getAllowRelateStart() { return this.allowRelateStart; }
    public String getDeviceId() { return this.deviceId; }
    public String getPackageName() { return this.packageName; }
    public int getRetryCount() { return this.retryCount; }

    public void setAllowAllFullBackground(Boolean bool) { this.allowAllFullBackground = bool; }
    public void setAllowAutoStart(Boolean bool) { this.allowAutoStart = bool; }
    public void setAllowPopupInBackground(Boolean bool) { this.allowPopupInBackground = bool; }
    public void setAllowRelateStart(Boolean bool) { this.allowRelateStart = bool; }
    public void setDeviceId(String str) { this.deviceId = str; }
    public void setPackageName(String str) { this.packageName = str; }
    public void setRetryCount(int i2) { this.retryCount = i2; }

    @NonNull
    public String toString() {
        return "PowerControlStateVO{allowAllFullBackground=" + this.allowAllFullBackground + ", allowPopupInBackground=" + this.allowPopupInBackground + ", allowAutoStart=" + this.allowAutoStart + ", allowRelateStart=" + this.allowRelateStart + ", retryCount=" + this.retryCount + ", packageName=" + this.packageName + ", deviceId=" + this.deviceId + '}';
    }
}
