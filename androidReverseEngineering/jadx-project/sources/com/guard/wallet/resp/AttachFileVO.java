package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AttachFileVO implements Serializable {
    private String contentType;
    private String fileExtension;
    private String fileName;
    private Long id;
    private Integer isPicture;
    private String previewUrl;
    private Long spaceId;
    private String spaceName;
    private String targetFileUrl;

    public AttachFileVO() {
    }

    public AttachFileVO(Long l2, Long l3, String str, String str2, String str3, Integer num, String str4, String str5, String str6) {
        this.id = l2;
        this.spaceId = l3;
        this.spaceName = str;
        this.fileName = str2;
        this.targetFileUrl = str3;
        this.isPicture = num;
        this.previewUrl = str4;
        this.fileExtension = str5;
        this.contentType = str6;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public String getFileName() {
        return this.fileName;
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

    public Long getSpaceId() {
        return this.spaceId;
    }

    public String getSpaceName() {
        return this.spaceName;
    }

    public String getTargetFileUrl() {
        return this.targetFileUrl;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public void setFileExtension(String str) {
        this.fileExtension = str;
    }

    public void setFileName(String str) {
        this.fileName = str;
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

    public void setSpaceId(Long l2) {
        this.spaceId = l2;
    }

    public void setSpaceName(String str) {
        this.spaceName = str;
    }

    public void setTargetFileUrl(String str) {
        this.targetFileUrl = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("AttachFileVO{id=");
        sb.append(this.id);
        sb.append(", spaceId=");
        sb.append(this.spaceId);
        sb.append(", spaceName='");
        sb.append(this.spaceName);
        sb.append("', fileName='");
        sb.append(this.fileName);
        sb.append("', targetFileUrl='");
        sb.append(this.targetFileUrl);
        sb.append("', isPicture=");
        sb.append(this.isPicture);
        sb.append(", previewUrl='");
        sb.append(this.previewUrl);
        sb.append("', fileExtension='");
        sb.append(this.fileExtension);
        sb.append("', contentType='");
        return AbstractC0000a.m18n(sb, this.contentType, "'}");
    }
}
