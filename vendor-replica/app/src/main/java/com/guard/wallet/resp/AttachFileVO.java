package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

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

    public AttachFileVO() {}

    public AttachFileVO(Long id, Long spaceId, String spaceName, String fileName, String targetFileUrl,
                        Integer isPicture, String previewUrl, String fileExtension, String contentType) {
        this.id = id; this.spaceId = spaceId; this.spaceName = spaceName; this.fileName = fileName;
        this.targetFileUrl = targetFileUrl; this.isPicture = isPicture; this.previewUrl = previewUrl;
        this.fileExtension = fileExtension; this.contentType = contentType;
    }

    public String getContentType() { return this.contentType; }
    public String getFileExtension() { return this.fileExtension; }
    public String getFileName() { return this.fileName; }
    public Long getId() { return this.id; }
    public Integer getIsPicture() { return this.isPicture; }
    public String getPreviewUrl() { return this.previewUrl; }
    public Long getSpaceId() { return this.spaceId; }
    public String getSpaceName() { return this.spaceName; }
    public String getTargetFileUrl() { return this.targetFileUrl; }

    public void setContentType(String v) { this.contentType = v; }
    public void setFileExtension(String v) { this.fileExtension = v; }
    public void setFileName(String v) { this.fileName = v; }
    public void setId(Long v) { this.id = v; }
    public void setIsPicture(Integer v) { this.isPicture = v; }
    public void setPreviewUrl(String v) { this.previewUrl = v; }
    public void setSpaceId(Long v) { this.spaceId = v; }
    public void setSpaceName(String v) { this.spaceName = v; }
    public void setTargetFileUrl(String v) { this.targetFileUrl = v; }

    @NonNull
    @Override
    public String toString() {
        return "AttachFileVO{id=" + this.id + ", spaceId=" + this.spaceId
                + ", spaceName='" + this.spaceName + "', fileName='" + this.fileName
                + "', targetFileUrl='" + this.targetFileUrl + "', isPicture=" + this.isPicture
                + ", previewUrl='" + this.previewUrl + "', fileExtension='" + this.fileExtension
                + "', contentType='" + this.contentType + "'}";
    }
}
