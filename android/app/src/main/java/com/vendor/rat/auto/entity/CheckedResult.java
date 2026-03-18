package com.vendor.rat.auto.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * Vendor: com/guard/wallet/entity/CheckedResult.java (42行)
 */
public class CheckedResult implements Serializable {

    private boolean checked;
    private boolean clicked;

    public CheckedResult() {
        this.checked = false;
        this.clicked = false;
    }

    public CheckedResult(boolean checked, boolean clicked) {
        this.checked = checked;
        this.clicked = clicked;
    }

    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }

    public boolean isClicked() { return clicked; }
    public void setClicked(boolean clicked) { this.clicked = clicked; }

    @NonNull
    public String toString() {
        return "CheckedResult{checked=" + this.checked + ", clicked=" + this.clicked + '}';
    }
}
