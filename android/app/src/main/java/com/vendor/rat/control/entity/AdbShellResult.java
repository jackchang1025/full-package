package com.vendor.rat.control.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * ADB Shell 执行结果
 * vendor: com.guard.wallet.entity.AdbShellResult
 */
public class AdbShellResult implements Serializable {
    private boolean success;
    private String output;

    public AdbShellResult() {
    }

    public AdbShellResult(boolean success, String output) {
        this.success = success;
        this.output = output;
    }

    public boolean isSuccess() { return this.success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getOutput() { return this.output; }
    public void setOutput(String output) { this.output = output; }

    @NonNull
    public String toString() {
        return "AdbShellResult{success=" + this.success + ", output='" + this.output + "'}";
    }
}
