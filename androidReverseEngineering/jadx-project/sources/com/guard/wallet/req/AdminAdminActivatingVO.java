package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class AdminAdminActivatingVO implements Serializable {
    private boolean adminActivating;

    public AdminAdminActivatingVO() {
    }

    public AdminAdminActivatingVO(boolean z2) {
        this.adminActivating = z2;
    }

    public boolean isAdminActivating() {
        return this.adminActivating;
    }

    public void setAdminActivating(boolean z2) {
        this.adminActivating = z2;
    }

    @NonNull
    public String toString() {
        return "AdminAdminActivatingVO{adminActivating=" + this.adminActivating + '}';
    }
}
