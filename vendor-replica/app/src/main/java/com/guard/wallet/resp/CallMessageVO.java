package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class CallMessageVO extends MessageBodyVO {
    private String callNumber;
    private String callState;
    private Integer callType;

    public CallMessageVO() {}
    public CallMessageVO(Integer callType, String callNumber, String callState) {
        this.callType = callType; this.callNumber = callNumber; this.callState = callState;
    }

    public String getCallNumber() { return this.callNumber; }
    public String getCallState() { return this.callState; }
    public Integer getCallType() { return this.callType; }
    public void setCallNumber(String v) { this.callNumber = v; }
    public void setCallState(String v) { this.callState = v; }
    public void setCallType(Integer v) { this.callType = v; }

    @NonNull
    @Override
    public String toString() {
        return "CallMessageVO{callType=" + this.callType + ", callNumber='" + this.callNumber
                + "', callState='" + this.callState + "'}";
    }
}
