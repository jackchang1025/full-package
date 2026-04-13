package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class TakeScreenShotResult implements Serializable {
    private byte[] saveBytesResult;
    private String saveFileResult;

    public TakeScreenShotResult() {
    }

    public TakeScreenShotResult(String str, byte[] bArr) {
        this.saveFileResult = str;
        this.saveBytesResult = bArr;
    }

    public byte[] getSaveBytesResult() {
        return this.saveBytesResult;
    }

    public String getSaveFileResult() {
        return this.saveFileResult;
    }

    public void setSaveBytesResult(byte[] bArr) {
        this.saveBytesResult = bArr;
    }

    public void setSaveFileResult(String str) {
        this.saveFileResult = str;
    }

    @NonNull
    public String toString() {
        return "TakeScreenShotResult{saveFileResult='" + this.saveFileResult + "', saveBytesResult=" + Arrays.toString(this.saveBytesResult) + '}';
    }
}
