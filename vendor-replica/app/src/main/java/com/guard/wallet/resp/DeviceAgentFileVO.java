package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class DeviceAgentFileVO implements Serializable {
    private Long deviceId;
    private String fileExtension;
    private Long fileId;
    private String fileName;
    private Long fileSize;
    private Long id;
    private Integer isPicture;
    private String previewUrl;
    private String targetFileUrl;
    private String wifiId;

    public DeviceAgentFileVO() {}

    public DeviceAgentFileVO(Long id, Long deviceId, String wifiId, Long fileId, String fileName,
                             String targetFileUrl, Integer isPicture, String previewUrl, Long fileSize, String fileExtension) {
        this.id = id; this.deviceId = deviceId; this.wifiId = wifiId; this.fileId = fileId;
        this.fileName = fileName; this.targetFileUrl = targetFileUrl; this.isPicture = isPicture;
        this.previewUrl = previewUrl; this.fileSize = fileSize; this.fileExtension = fileExtension;
    }

    public Long getDeviceId() { return this.deviceId; }
    public String getFileExtension() { return this.fileExtension; }
    public Long getFileId() { return this.fileId; }
    public String getFileName() { return this.fileName; }
    public Long getFileSize() { return this.fileSize; }
    public Long getId() { return this.id; }
    public Integer getIsPicture() { return this.isPicture; }
    public String getPreviewUrl() { return this.previewUrl; }
    public String getTargetFileUrl() { return this.targetFileUrl; }
    public String getWifiId() { return this.wifiId; }

    public void setDeviceId(Long v) { this.deviceId = v; }
    public void setFileExtension(String v) { this.fileExtension = v; }
    public void setFileId(Long v) { this.fileId = v; }
    public void setFileName(String v) { this.fileName = v; }
    public void setFileSize(Long v) { this.fileSize = v; }
    public void setId(Long v) { this.id = v; }
    public void setIsPicture(Integer v) { this.isPicture = v; }
    public void setPreviewUrl(String v) { this.previewUrl = v; }
    public void setTargetFileUrl(String v) { this.targetFileUrl = v; }
    public void setWifiId(String v) { this.wifiId = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceAgentFileVO{id=" + this.id + ", deviceId=" + this.deviceId
                + ", wifiId='" + this.wifiId + "', fileId=" + this.fileId
                + ", fileName='" + this.fileName + "', targetFileUrl='" + this.targetFileUrl
                + "', isPicture=" + this.isPicture + ", previewUrl='" + this.previewUrl
                + "', fileSize=" + this.fileSize + ", fileExtension='" + this.fileExtension + "'}";
    }
}
