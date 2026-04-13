package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.CommandResult;
import java.io.Serializable;

/* loaded from: classes.dex */
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

    public PushResponseVO(String str, String str2, String str3, Integer num, Integer num2, Integer num3, CommandResult commandResult, CommandResult commandResult2) {
        this.logId = str;
        this.deviceId = str2;
        this.fileUrl = str3;
        this.installMethod = num;
        this.installResult = num2;
        this.startResult = num3;
        this.pushCommandResult = commandResult;
        this.runCommandResult = commandResult2;
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

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setDownloadResult(Integer num) {
        this.downloadResult = num;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public void setInstallMethod(Integer num) {
        this.installMethod = num;
    }

    public void setInstallResult(Integer num) {
        this.installResult = num;
    }

    public void setLogId(String str) {
        this.logId = str;
    }

    public void setPushCommandResult(CommandResult commandResult) {
        this.pushCommandResult = commandResult;
    }

    public void setRunCommandResult(CommandResult commandResult) {
        this.runCommandResult = commandResult;
    }

    public void setStartResult(Integer num) {
        this.startResult = num;
    }

    @NonNull
    public String toString() {
        return "PushResponseVO{logId='" + this.logId + "', deviceId='" + this.deviceId + "', fileUrl='" + this.fileUrl + "', installMethod=" + this.installMethod + ", installResult=" + this.installResult + ", startResult=" + this.startResult + ", pushCommandResult=" + this.pushCommandResult + ", runCommandResult=" + this.runCommandResult + '}';
    }
}
