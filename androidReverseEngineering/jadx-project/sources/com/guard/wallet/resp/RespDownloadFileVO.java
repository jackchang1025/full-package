package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class RespDownloadFileVO implements Serializable {
    private String filePathAndName;
    private String galleryUrl;

    public RespDownloadFileVO() {
    }

    public RespDownloadFileVO(String str, String str2) {
        this.filePathAndName = str;
        this.galleryUrl = str2;
    }

    public String getFilePathAndName() {
        return this.filePathAndName;
    }

    public String getGalleryUrl() {
        return this.galleryUrl;
    }

    public void setFilePathAndName(String str) {
        this.filePathAndName = str;
    }

    public void setGalleryUrl(String str) {
        this.galleryUrl = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("RespDownloadFileVO{filePathAndName='");
        sb.append(this.filePathAndName);
        sb.append("', galleryUrl='");
        return AbstractC0000a.m18n(sb, this.galleryUrl, "'}");
    }
}
