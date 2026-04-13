package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class RespDeleteFileVO implements Serializable {
    private Boolean fileDeleted;
    private Boolean galleryDeleted;

    public RespDeleteFileVO() {
    }

    public RespDeleteFileVO(Boolean bool, Boolean bool2) {
        this.fileDeleted = bool;
        this.galleryDeleted = bool2;
    }

    public Boolean getFileDeleted() {
        return this.fileDeleted;
    }

    public Boolean getGalleryDeleted() {
        return this.galleryDeleted;
    }

    public void setFileDeleted(Boolean bool) {
        this.fileDeleted = bool;
    }

    public void setGalleryDeleted(Boolean bool) {
        this.galleryDeleted = bool;
    }

    @NonNull
    public String toString() {
        return "RespDeleteFileVO{fileDeleted=" + this.fileDeleted + ", galleryDeleted=" + this.galleryDeleted + '}';
    }
}
