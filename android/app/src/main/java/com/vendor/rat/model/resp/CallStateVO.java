package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
public class CallStateVO implements Serializable {
    private String callState;
    private String description;
    private Integer state;
    public CallStateVO() {
    }
    public CallStateVO(Integer num, String str, String str2) {
        this.state = num;
        this.callState = str;
        this.description = str2;
    }
    public String getCallState() {
        return this.callState;
    }
    public String getDescription() {
        return this.description;
    }
    public Integer getState() {
        return this.state;
    }
    public void setCallState(String str) {
        this.callState = str;
    }
    public void setDescription(String str) {
        this.description = str;
    }
    public void setState(Integer num) {
        this.state = num;
    }
    @NonNull
    public String toString() {
        return "CallStateVO{state=" + this.state + ", callState='" + this.callState + "', description='" + this.description + "'}";
    }
}
