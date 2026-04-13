package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.entity.CommandResult;
import java.io.Serializable;

public class PushResponseVO implements Serializable {
    private String deviceId;
    private Integer downloadResult;
    private String fileUrl;
    private Integer installMethod;
    private Integer installResult;
    private String logId;
    private CommandResult pushCommandResult;
    private CommandResult runCommandResult;
    private Integer startResult;

    public PushResponseVO() {}

    public PushResponseVO(String logId, String deviceId, String fileUrl, Integer installMethod,
                          Integer installResult, Integer startResult, CommandResult pushCommandResult,
                          CommandResult runCommandResult) {
        this.logId = logId; this.deviceId = deviceId; this.fileUrl = fileUrl;
        this.installMethod = installMethod; this.installResult = installResult;
        this.startResult = startResult; this.pushCommandResult = pushCommandResult;
        this.runCommandResult = runCommandResult;
    }

    public String getDeviceId() { return this.deviceId; }
    public Integer getDownloadResult() { return this.downloadResult; }
    public String getFileUrl() { return this.fileUrl; }
    public Integer getInstallMethod() { return this.installMethod; }
    public Integer getInstallResult() { return this.installResult; }
    public String getLogId() { return this.logId; }
    public CommandResult getPushCommandResult() { return this.pushCommandResult; }
    public CommandResult getRunCommandResult() { return this.runCommandResult; }
    public Integer getStartResult() { return this.startResult; }

    public void setDeviceId(String v) { this.deviceId = v; }
    public void setDownloadResult(Integer v) { this.downloadResult = v; }
    public void setFileUrl(String v) { this.fileUrl = v; }
    public void setInstallMethod(Integer v) { this.installMethod = v; }
    public void setInstallResult(Integer v) { this.installResult = v; }
    public void setLogId(String v) { this.logId = v; }
    public void setPushCommandResult(CommandResult v) { this.pushCommandResult = v; }
    public void setRunCommandResult(CommandResult v) { this.runCommandResult = v; }
    public void setStartResult(Integer v) { this.startResult = v; }

    @NonNull
    @Override
    public String toString() {
        return "PushResponseVO{logId='" + this.logId + "', deviceId='" + this.deviceId
                + "', fileUrl='" + this.fileUrl + "', installMethod=" + this.installMethod
                + ", installResult=" + this.installResult + ", startResult=" + this.startResult
                + ", pushCommandResult=" + this.pushCommandResult
                + ", runCommandResult=" + this.runCommandResult + "}";
    }
}
