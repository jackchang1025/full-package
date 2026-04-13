package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

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

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @NonNull
    @Override
    public String toString() {
        return "CheckedResult{checked=" + this.checked + ", clicked=" + this.clicked + '}';
    }
}
