package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class CacheTaskResponseVO implements Serializable {
    private String containerCode;
    private String deviceId;
    private String reqUri;
    private String response;

    public CacheTaskResponseVO() {}
    public CacheTaskResponseVO(String deviceId, String reqUri, String containerCode, String response) {
        this.deviceId = deviceId; this.reqUri = reqUri; this.containerCode = containerCode; this.response = response;
    }

    public String getContainerCode() { return this.containerCode; }
    public String getDeviceId() { return this.deviceId; }
    public String getReqUri() { return this.reqUri; }
    public String getResponse() { return this.response; }
    public void setContainerCode(String v) { this.containerCode = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setReqUri(String v) { this.reqUri = v; }
    public void setResponse(String v) { this.response = v; }

    @NonNull
    @Override
    public String toString() {
        return "CacheTaskResultVO{deviceId='" + this.deviceId + "', reqUri='" + this.reqUri
                + "', containerCode='" + this.containerCode + "', response='" + this.response + "'}";
    }
}
