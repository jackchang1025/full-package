package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class RespDeleteFileVO implements Serializable {
    private Boolean fileDeleted;
    private Boolean galleryDeleted;

    public RespDeleteFileVO() {}
    public RespDeleteFileVO(Boolean fileDeleted, Boolean galleryDeleted) {
        this.fileDeleted = fileDeleted; this.galleryDeleted = galleryDeleted;
    }

    public Boolean getFileDeleted() { return this.fileDeleted; }
    public Boolean getGalleryDeleted() { return this.galleryDeleted; }
    public void setFileDeleted(Boolean v) { this.fileDeleted = v; }
    public void setGalleryDeleted(Boolean v) { this.galleryDeleted = v; }

    @NonNull
    @Override
    public String toString() {
        return "RespDeleteFileVO{fileDeleted=" + this.fileDeleted + ", galleryDeleted=" + this.galleryDeleted + "}";
    }
}
