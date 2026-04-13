package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class MainUninstallPolicyVO implements Serializable {
    private Boolean activeAdmin;
    private Boolean uninstall;

    public MainUninstallPolicyVO() {
    }

    public MainUninstallPolicyVO(Boolean bool, Boolean bool2) {
        this.uninstall = bool;
        this.activeAdmin = bool2;
    }

    public Boolean getActiveAdmin() {
        return this.activeAdmin;
    }

    public Boolean getUninstall() {
        return this.uninstall;
    }

    public void setActiveAdmin(Boolean bool) {
        this.activeAdmin = bool;
    }

    public void setUninstall(Boolean bool) {
        this.uninstall = bool;
    }

    @NonNull
    public String toString() {
        return "MainUninstallPolicyVO{uninstall=" + this.uninstall + ", activeAdmin=" + this.activeAdmin + '}';
    }
}
