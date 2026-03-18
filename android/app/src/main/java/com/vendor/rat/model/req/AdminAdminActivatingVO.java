package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
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
