package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class CacheTaskVO implements Serializable {
    private Integer argMethod;
    private String arguments;
    private String containerCode;
    private boolean fileStream;
    private Integer reqMethod;
    private String reqUri;
    private String resTypeCode;
    private boolean socketStream = false;

    public CacheTaskVO() { this.fileStream = false; }

    public CacheTaskVO(String reqUri, Integer reqMethod, String containerCode, Integer argMethod,
                       String arguments, String resTypeCode, Boolean socketStream, Boolean fileStream) {
        this.fileStream = false;
        this.reqUri = reqUri; this.reqMethod = reqMethod; this.containerCode = containerCode;
        this.argMethod = argMethod; this.arguments = arguments; this.resTypeCode = resTypeCode;
        this.socketStream = socketStream; this.fileStream = fileStream;
    }

    public Integer getArgMethod() { return this.argMethod; }
    public String getArguments() { return this.arguments; }
    public String getContainerCode() { return this.containerCode; }
    public Boolean getFileStream() { return this.fileStream; }
    public Integer getReqMethod() { return this.reqMethod; }
    public String getReqUri() { return this.reqUri; }
    public String getResTypeCode() { return this.resTypeCode; }
    public Boolean getSocketStream() { return this.socketStream; }

    public void setArgMethod(Integer v) { this.argMethod = v; }
    public void setArguments(String v) { this.arguments = v; }
    public void setContainerCode(String v) { this.containerCode = v; }
    public void setFileStream(Boolean v) { this.fileStream = v; }
    public void setReqMethod(Integer v) { this.reqMethod = v; }
    public void setReqUri(String v) { this.reqUri = v; }
    public void setResTypeCode(String v) { this.resTypeCode = v; }
    public void setSocketStream(Boolean v) { this.socketStream = v; }

    @NonNull
    @Override
    public String toString() {
        return "CacheTaskVO{reqUri='" + this.reqUri + "', reqMethod=" + this.reqMethod
                + ", containerCode='" + this.containerCode + "', argMethod=" + this.argMethod
                + ", arguments='" + this.arguments + "', resTypeCode='" + this.resTypeCode
                + "', socketStream='" + this.socketStream + "', fileStream='" + this.fileStream + "'}";
    }
}
