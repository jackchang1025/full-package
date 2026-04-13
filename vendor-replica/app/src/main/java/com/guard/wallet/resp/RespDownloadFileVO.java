package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class RespDownloadFileVO implements Serializable {
    private String filePathAndName;
    private String galleryUrl;

    public RespDownloadFileVO() {}
    public RespDownloadFileVO(String filePathAndName, String galleryUrl) {
        this.filePathAndName = filePathAndName; this.galleryUrl = galleryUrl;
    }

    public String getFilePathAndName() { return this.filePathAndName; }
    public String getGalleryUrl() { return this.galleryUrl; }
    public void setFilePathAndName(String v) { this.filePathAndName = v; }
    public void setGalleryUrl(String v) { this.galleryUrl = v; }

    @NonNull
    @Override
    public String toString() {
        return "RespDownloadFileVO{filePathAndName='" + this.filePathAndName
                + "', galleryUrl='" + this.galleryUrl + "'}";
    }
}
