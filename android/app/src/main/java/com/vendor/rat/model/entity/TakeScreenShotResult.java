package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.TakeScreenShotResult

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Arrays;

public class TakeScreenShotResult implements Serializable {
    private byte[] saveBytesResult;
    private String saveFileResult;

    public TakeScreenShotResult() {
    }

    public TakeScreenShotResult(String saveFileResult, byte[] saveBytesResult) {
        this.saveFileResult = saveFileResult;
        this.saveBytesResult = saveBytesResult;
    }

    public byte[] getSaveBytesResult() {
        return this.saveBytesResult;
    }

    public String getSaveFileResult() {
        return this.saveFileResult;
    }

    public void setSaveBytesResult(byte[] saveBytesResult) {
        this.saveBytesResult = saveBytesResult;
    }

    public void setSaveFileResult(String saveFileResult) {
        this.saveFileResult = saveFileResult;
    }

    @NonNull
    @Override
    public String toString() {
        return "TakeScreenShotResult{saveFileResult='" + saveFileResult
                + "', saveBytesResult=" + Arrays.toString(saveBytesResult) + '}';
    }
}
