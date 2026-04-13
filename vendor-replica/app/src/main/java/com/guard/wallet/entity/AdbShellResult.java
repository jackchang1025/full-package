package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class AdbShellResult implements Serializable {
    private String output;
    private boolean success;

    public AdbShellResult() {
    }

    public AdbShellResult(boolean success, String output) {
        this.success = success;
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @NonNull
    @Override
    public String toString() {
        return "AdbShellResult{success=" + this.success + ", output='" + this.output + "'}";
    }
}
