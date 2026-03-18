package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
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
        return "RespDownloadFileVO{filePathAndName='" + this.filePathAndName + "', galleryUrl='" + this.galleryUrl + "'}";
    }
}
