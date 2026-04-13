package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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

    public DeviceAgentFileVO(Long l2, Long l3, String str, Long l4, String str2, String str3, Integer num, String str4, Long l5, String str5) {
        this.id = l2;
        this.deviceId = l3;
        this.wifiId = str;
        this.fileId = l4;
        this.fileName = str2;
        this.targetFileUrl = str3;
        this.isPicture = num;
        this.previewUrl = str4;
        this.fileSize = l5;
        this.fileExtension = str5;
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

    public void setDeviceId(Long l2) {
        this.deviceId = l2;
    }

    public void setFileExtension(String str) {
        this.fileExtension = str;
    }

    public void setFileId(Long l2) {
        this.fileId = l2;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setFileSize(Long l2) {
        this.fileSize = l2;
    }

    public void setId(Long l2) {
        this.id = l2;
    }

    public void setIsPicture(Integer num) {
        this.isPicture = num;
    }

    public void setPreviewUrl(String str) {
        this.previewUrl = str;
    }

    public void setTargetFileUrl(String str) {
        this.targetFileUrl = str;
    }

    public void setWifiId(String str) {
        this.wifiId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceAgentFileVO{id=");
        sb.append(this.id);
        sb.append(", deviceId=");
        sb.append(this.deviceId);
        sb.append(", wifiId='");
        sb.append(this.wifiId);
        sb.append("', fileId=");
        sb.append(this.fileId);
        sb.append(", fileName='");
        sb.append(this.fileName);
        sb.append("', targetFileUrl='");
        sb.append(this.targetFileUrl);
        sb.append("', isPicture=");
        sb.append(this.isPicture);
        sb.append(", previewUrl='");
        sb.append(this.previewUrl);
        sb.append("', fileSize=");
        sb.append(this.fileSize);
        sb.append(", fileExtension='");
        return AbstractC0000a.m18n(sb, this.fileExtension, "'}");
    }
}
