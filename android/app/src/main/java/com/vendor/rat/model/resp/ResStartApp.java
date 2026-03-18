package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ResStartApp implements Serializable {
    private String delegateId;
    boolean start;
    private String startMsg;
    private String startPackage;
    private boolean started;
    public ResStartApp() {
    }
    public ResStartApp(String str, boolean z2, boolean z3, String str2, String str3) {
        this.startPackage = str;
        this.start = z2;
        this.started = z3;
        this.delegateId = str2;
        this.startMsg = str3;
    }
    public String getDelegateId() { return this.delegateId; }
    public String getStartMsg() { return this.startMsg; }
    public String getStartPackage() { return this.startPackage; }
    public boolean isStart() { return this.start; }
    public boolean isStarted() { return this.started; }
    public void setDelegateId(String str) { this.delegateId = str; }
    public void setStart(boolean z2) { this.start = z2; }
    public void setStartMsg(String str) { this.startMsg = str; }
    public void setStartPackage(String str) { this.startPackage = str; }
    public void setStarted(boolean z2) { this.started = z2; }
    @NonNull
    public String toString() {
        return "ResStartApp{start=" + this.start + ", startPackage=" + this.startPackage + ", started=" + this.started + ", startMsg=" + this.startMsg + ", delegateId='" + this.delegateId + "'}";
    }
}
