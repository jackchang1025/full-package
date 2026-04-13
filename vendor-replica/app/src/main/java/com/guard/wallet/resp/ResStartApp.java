package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ResStartApp implements Serializable {
    private String delegateId;
    boolean start;
    private String startMsg;
    private String startPackage;
    private boolean started;

    public ResStartApp() {}
    public ResStartApp(String startPackage, boolean start, boolean started, String delegateId, String startMsg) {
        this.startPackage = startPackage; this.start = start; this.started = started;
        this.delegateId = delegateId; this.startMsg = startMsg;
    }

    public String getDelegateId() { return this.delegateId; }
    public String getStartMsg() { return this.startMsg; }
    public String getStartPackage() { return this.startPackage; }
    public boolean isStart() { return this.start; }
    public boolean isStarted() { return this.started; }
    public void setDelegateId(String v) { this.delegateId = v; }
    public void setStart(boolean v) { this.start = v; }
    public void setStartMsg(String v) { this.startMsg = v; }
    public void setStartPackage(String v) { this.startPackage = v; }
    public void setStarted(boolean v) { this.started = v; }

    @NonNull
    @Override
    public String toString() {
        return "ResStartApp{start=" + this.start + ", startPackage=" + this.startPackage
                + ", started=" + this.started + ", startMsg=" + this.startMsg
                + ", delegateId='" + this.delegateId + "'}";
    }
}
