package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class CheckedResult implements Serializable {
    private boolean checked;
    private boolean clicked;

    public CheckedResult() {
        this.checked = false;
        this.clicked = false;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setChecked(boolean z2) {
        this.checked = z2;
    }

    public void setClicked(boolean z2) {
        this.clicked = z2;
    }

    @NonNull
    public String toString() {
        return "CheckedResult{checked=" + this.checked + ", clicked=" + this.clicked + '}';
    }

    public CheckedResult(boolean z2, boolean z3) {
        this.checked = z2;
        this.clicked = z3;
    }
}
