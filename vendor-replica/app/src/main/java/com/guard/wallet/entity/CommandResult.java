package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class CommandResult implements Serializable {
    private List<String> errorMsgLines;
    private int result;
    private List<String> successMsgLines;

    public CommandResult(int result) {
        this.result = result;
    }

    public CommandResult(int result, List<String> successMsgLines, List<String> errorMsgLines) {
        this.result = result;
        this.successMsgLines = successMsgLines;
        this.errorMsgLines = errorMsgLines;
    }

    public List<String> getErrorMsgLines() {
        return this.errorMsgLines;
    }

    public int getResult() {
        return this.result;
    }

    public List<String> getSuccessMsgLines() {
        return this.successMsgLines;
    }

    public void setErrorMsgLines(List<String> errorMsgLines) {
        this.errorMsgLines = errorMsgLines;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setSuccessMsgLines(List<String> successMsgLines) {
        this.successMsgLines = successMsgLines;
    }

    @NonNull
    @Override
    public String toString() {
        return "CommandResult{result=" + this.result
                + ", successMsgLines=" + this.successMsgLines
                + ", errorMsgLines=" + this.errorMsgLines + '}';
    }
}
