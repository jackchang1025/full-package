package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class MainUninstallPolicyVO implements Serializable {
    private Boolean activeAdmin;
    private Boolean uninstall;

    public MainUninstallPolicyVO() {}
    public MainUninstallPolicyVO(Boolean uninstall, Boolean activeAdmin) {
        this.uninstall = uninstall; this.activeAdmin = activeAdmin;
    }

    public Boolean getActiveAdmin() { return this.activeAdmin; }
    public Boolean getUninstall() { return this.uninstall; }
    public void setActiveAdmin(Boolean v) { this.activeAdmin = v; }
    public void setUninstall(Boolean v) { this.uninstall = v; }

    @NonNull
    @Override
    public String toString() {
        return "MainUninstallPolicyVO{uninstall=" + this.uninstall + ", activeAdmin=" + this.activeAdmin + "}";
    }
}
