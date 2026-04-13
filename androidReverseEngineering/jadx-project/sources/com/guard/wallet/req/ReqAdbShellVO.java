package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqAdbShellVO implements Serializable {
    private String command;

    public ReqAdbShellVO() {
    }

    public ReqAdbShellVO(String str) {
        this.command = str;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String str) {
        this.command = str;
    }

    @NonNull
    public String toString() {
        return AbstractC0000a.m18n(new StringBuilder("ReqAdbShellVO{command='"), this.command, "'}");
    }
}
