package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AdbShellResult implements Serializable {
    private String output;
    private boolean success;

    public AdbShellResult() {
    }

    public AdbShellResult(boolean z2, String str) {
        this.success = z2;
        this.output = str;
    }

    public String getOutput() {
        return this.output;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setOutput(String str) {
        this.output = str;
    }

    public void setSuccess(boolean z2) {
        this.success = z2;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("AdbShellResult{success=");
        sb.append(this.success);
        sb.append(", output='");
        return AbstractC0000a.m18n(sb, this.output, "'}");
    }
}
