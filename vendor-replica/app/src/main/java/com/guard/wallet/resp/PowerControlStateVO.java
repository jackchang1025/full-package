package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class PowerControlStateVO extends MessageBodyVO {
    private Boolean allowAllFullBackground;
    private Boolean allowAutoStart;
    private Boolean allowPopupInBackground;
    private Boolean allowRelateStart;
    private String deviceId;
    private String packageName;
    private int retryCount;

    public PowerControlStateVO() {
        Boolean f = Boolean.FALSE;
        this.allowAllFullBackground = f;
        this.allowPopupInBackground = f;
        this.allowAutoStart = f;
        this.allowRelateStart = f;
        this.retryCount = 0;
    }

    public PowerControlStateVO(String deviceId, String packageName, Boolean allowAllFullBackground,
                               Boolean allowPopupInBackground, Boolean allowAutoStart, Boolean allowRelateStart, int retryCount) {
        this.deviceId = deviceId; this.packageName = packageName;
        this.allowAllFullBackground = allowAllFullBackground; this.allowPopupInBackground = allowPopupInBackground;
        this.allowAutoStart = allowAutoStart; this.allowRelateStart = allowRelateStart; this.retryCount = retryCount;
    }

    public Boolean getAllowAllFullBackground() { return this.allowAllFullBackground; }
    public Boolean getAllowAutoStart() { return this.allowAutoStart; }
    public Boolean getAllowPopupInBackground() { return this.allowPopupInBackground; }
    public Boolean getAllowRelateStart() { return this.allowRelateStart; }
    public String getDeviceId() { return this.deviceId; }
    public String getPackageName() { return this.packageName; }
    public int getRetryCount() { return this.retryCount; }

    public void setAllowAllFullBackground(Boolean v) { this.allowAllFullBackground = v; }
    public void setAllowAutoStart(Boolean v) { this.allowAutoStart = v; }
    public void setAllowPopupInBackground(Boolean v) { this.allowPopupInBackground = v; }
    public void setAllowRelateStart(Boolean v) { this.allowRelateStart = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setPackageName(String v) { this.packageName = v; }
    public void setRetryCount(int v) { this.retryCount = v; }

    @NonNull
    @Override
    public String toString() {
        return "PowerControlStateVO{allowAllFullBackground=" + this.allowAllFullBackground
                + ", allowPopupInBackground=" + this.allowPopupInBackground
                + ", allowAutoStart=" + this.allowAutoStart + ", allowRelateStart=" + this.allowRelateStart
                + ", retryCount=" + this.retryCount + ", packageName=" + this.packageName
                + ", deviceId=" + this.deviceId + "}";
    }
}
