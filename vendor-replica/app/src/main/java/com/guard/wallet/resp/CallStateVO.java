package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class CallStateVO implements Serializable {
    private String callState;
    private String description;
    private Integer state;

    public CallStateVO() {}
    public CallStateVO(Integer state, String callState, String description) {
        this.state = state; this.callState = callState; this.description = description;
    }

    public String getCallState() { return this.callState; }
    public String getDescription() { return this.description; }
    public Integer getState() { return this.state; }
    public void setCallState(String v) { this.callState = v; }
    public void setDescription(String v) { this.description = v; }
    public void setState(Integer v) { this.state = v; }

    @NonNull
    @Override
    public String toString() {
        return "CallStateVO{state=" + this.state + ", callState='" + this.callState
                + "', description='" + this.description + "'}";
    }
}
