package com.vendor.rat.model.resp;
import androidx.annotation.NonNull;
import java.io.Serializable;
/* loaded from: classes.dex */
public class CacheTaskVO implements Serializable {
    private Integer argMethod;
    private String arguments;
    private String containerCode;
    private boolean fileStream;
    private Integer reqMethod;
    private String reqUri;
    private String resTypeCode;
    private boolean socketStream;
    public CacheTaskVO() {
        this.socketStream = false;
        this.fileStream = false;
    }
    public CacheTaskVO(String str, Integer num, String str2, Integer num2, String str3, String str4, Boolean bool, Boolean bool2) {
        this.socketStream = false;
        this.fileStream = false;
        this.reqUri = str;
        this.reqMethod = num;
        this.containerCode = str2;
        this.argMethod = num2;
        this.arguments = str3;
        this.resTypeCode = str4;
        this.socketStream = bool.booleanValue();
        this.fileStream = bool2.booleanValue();
    }
    public Integer getArgMethod() {
        return this.argMethod;
    }
    public String getArguments() {
        return this.arguments;
    }
    public String getContainerCode() {
        return this.containerCode;
    }
    public Boolean getFileStream() {
        return Boolean.valueOf(this.fileStream);
    }
    public Integer getReqMethod() {
        return this.reqMethod;
    }
    public String getReqUri() {
        return this.reqUri;
    }
    public String getResTypeCode() {
        return this.resTypeCode;
    }
    public Boolean getSocketStream() {
        return Boolean.valueOf(this.socketStream);
    }
    public void setArgMethod(Integer num) {
        this.argMethod = num;
    }
    public void setArguments(String str) {
        this.arguments = str;
    }
    public void setContainerCode(String str) {
        this.containerCode = str;
    }
    public void setFileStream(Boolean bool) {
        this.fileStream = bool.booleanValue();
    }
    public void setReqMethod(Integer num) {
        this.reqMethod = num;
    }
    public void setReqUri(String str) {
        this.reqUri = str;
    }
    public void setResTypeCode(String str) {
        this.resTypeCode = str;
    }
    public void setSocketStream(Boolean bool) {
        this.socketStream = bool.booleanValue();
    }
    @NonNull
    public String toString() {
        return "CacheTaskVO{reqUri='" + this.reqUri + "', reqMethod=" + this.reqMethod + ", containerCode='" + this.containerCode + "', argMethod=" + this.argMethod + ", arguments='" + this.arguments + "', resTypeCode='" + this.resTypeCode + "', socketStream='" + this.socketStream + "', fileStream='" + this.fileStream + "'}";
    }
}
