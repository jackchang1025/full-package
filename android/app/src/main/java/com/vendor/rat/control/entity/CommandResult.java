package com.vendor.rat.control.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/**
 * Shell 命令执行结果
 * vendor: com.guard.wallet.entity.CommandResult
 */
public class CommandResult implements Serializable {
    private int result;
    private List<String> successMsgLines;
    private List<String> errorMsgLines;

    public CommandResult(int result) {
        this.result = result;
    }

    public CommandResult(int result, List<String> successMsgLines, List<String> errorMsgLines) {
        this.result = result;
        this.successMsgLines = successMsgLines;
        this.errorMsgLines = errorMsgLines;
    }

    public int getResult() { return this.result; }
    public void setResult(int result) { this.result = result; }
    public List<String> getSuccessMsgLines() { return this.successMsgLines; }
    public void setSuccessMsgLines(List<String> list) { this.successMsgLines = list; }
    public List<String> getErrorMsgLines() { return this.errorMsgLines; }
    public void setErrorMsgLines(List<String> list) { this.errorMsgLines = list; }

    @NonNull
    public String toString() {
        return "CommandResult{result=" + this.result + ", successMsgLines=" + this.successMsgLines + ", errorMsgLines=" + this.errorMsgLines + '}';
    }
}
