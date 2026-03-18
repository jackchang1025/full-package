package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqDownloadFileVO implements Serializable {
    private String fileUrl;
    private String filepath;
    private boolean saveToGallery;
    public ReqDownloadFileVO() {
    }
    public ReqDownloadFileVO(String str, String str2, boolean z2) {
        this.filepath = str;
        this.fileUrl = str2;
        this.saveToGallery = z2;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public String getFilepath() {
        return this.filepath;
    }
    public boolean isSaveToGallery() {
        return this.saveToGallery;
    }
    public void setFileUrl(String str) {
        this.fileUrl = str;
    }
    public void setFilepath(String str) {
        this.filepath = str;
    }
    public void setSaveToGallery(boolean z2) {
        this.saveToGallery = z2;
    }
    @NonNull
    public String toString() {
        return "ReqDownloadFileVO{filepath='" + this.filepath + "', fileUrl='" + this.fileUrl + "', saveToGallery=" + this.saveToGallery + '}';
    }
}
