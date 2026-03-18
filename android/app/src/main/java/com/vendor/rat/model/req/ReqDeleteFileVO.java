package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqDeleteFileVO implements Serializable {
    private String filePathAndName;
    private String galleryUrl;
    public ReqDeleteFileVO() {
    }
    public ReqDeleteFileVO(String str, String str2) {
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
        return "ReqDeleteFileVO{filePathAndName='" + this.filePathAndName + "', galleryUrl='" + this.galleryUrl + "'}";
    }
}
