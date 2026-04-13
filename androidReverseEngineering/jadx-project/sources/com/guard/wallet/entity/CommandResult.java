package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class CommandResult implements Serializable {
    private List<String> errorMsgLines;
    private int result;
    private List<String> successMsgLines;

    public CommandResult(int i2) {
        this.result = i2;
    }

    public List<String> getErrorMsgLines() {
        return this.errorMsgLines;
    }

    public int getResult() {
        return this.result;
    }

    public List<String> getSuccessMsgLines() {
        return this.successMsgLines;
    }

    public void setErrorMsgLines(List<String> list) {
        this.errorMsgLines = list;
    }

    public void setResult(int i2) {
        this.result = i2;
    }

    public void setSuccessMsgLines(List<String> list) {
        this.successMsgLines = list;
    }

    @NonNull
    public String toString() {
        return "CommandResult{result=" + this.result + ", successMsgLines=" + this.successMsgLines + ", errorMsgLines=" + this.errorMsgLines + '}';
    }

    public CommandResult(int i2, List<String> list, List<String> list2) {
        this.result = i2;
        this.successMsgLines = list;
        this.errorMsgLines = list2;
    }
}
