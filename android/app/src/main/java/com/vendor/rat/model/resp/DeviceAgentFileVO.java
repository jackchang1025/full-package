package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.DeviceAgentFileVO
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

    public DeviceAgentFileVO() {
    }

    public DeviceAgentFileVO(Long id, Long deviceId, String wifiId, Long fileId, String fileName, String targetFileUrl, Integer isPicture, String previewUrl, Long fileSize, String fileExtension) {
        this.id = id;
        this.deviceId = deviceId;
        this.wifiId = wifiId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.targetFileUrl = targetFileUrl;
        this.isPicture = isPicture;
        this.previewUrl = previewUrl;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
    }

    public Long getDeviceId() {
        return this.deviceId;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public Long getFileId() {
        return this.fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getIsPicture() {
        return this.isPicture;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public String getTargetFileUrl() {
        return this.targetFileUrl;
    }

    public String getWifiId() {
        return this.wifiId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsPicture(Integer isPicture) {
        this.isPicture = isPicture;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void setTargetFileUrl(String targetFileUrl) {
        this.targetFileUrl = targetFileUrl;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }

    @NonNull
    public String toString() {
        return "DeviceAgentFileVO{id=" + this.id
                + ", deviceId=" + this.deviceId
                + ", wifiId='" + this.wifiId
                + "', fileId=" + this.fileId
                + ", fileName='" + this.fileName
                + "', targetFileUrl='" + this.targetFileUrl
                + "', isPicture=" + this.isPicture
                + ", previewUrl='" + this.previewUrl
                + "', fileSize=" + this.fileSize
                + ", fileExtension='" + this.fileExtension + "'}";
    }
}
