package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqMonitorLocationVO implements Serializable {
    private Float minDistanceM;
    private Long minTimeMs;
    public ReqMonitorLocationVO() {
        this.minTimeMs = 10000L;
        this.minDistanceM = Float.valueOf(100.0f);
    }
    public ReqMonitorLocationVO(Long l2, Float f2) {
        this.minTimeMs = 10000L;
        this.minTimeMs = l2;
        this.minDistanceM = f2;
    }
    public Float getMinDistanceM() {
        return this.minDistanceM;
    }
    public Long getMinTimeMs() {
        return this.minTimeMs;
    }
    public void setMinDistanceM(Float f2) {
        this.minDistanceM = f2;
    }
    public void setMinTimeMs(Long l2) {
        this.minTimeMs = l2;
    }
    @NonNull
    public String toString() {
        return "ReqMonitorLocationVO{minTimeMs=" + this.minTimeMs + ", minDistanceM=" + this.minDistanceM + '}';
    }
}
