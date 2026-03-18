package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
// ADAPT: removed obfuscated static of() method (depends on a1.q, r.a, wallet/utils/g, wallet/utils/h)
import androidx.annotation.NonNull;
import java.io.Serializable;
public class BackAppStateVO implements Serializable {
    private Integer installed;
    private Integer running;
    public BackAppStateVO() {
    }
    public BackAppStateVO(Integer num, Integer num2) {
        this.installed = num;
        this.running = num2;
    }
    // TODO: VENDOR_VERIFY - static of() method removed, depends on obfuscated utils
    public Integer getInstalled() { return this.installed; }
    public Integer getRunning() { return this.running; }
    public void setInstalled(Integer num) { this.installed = num; }
    public void setRunning(Integer num) { this.running = num; }
    @NonNull
    public String toString() {
        return "BackAppStateVO{installed=" + this.installed + ", running=" + this.running + '}';
    }
}
