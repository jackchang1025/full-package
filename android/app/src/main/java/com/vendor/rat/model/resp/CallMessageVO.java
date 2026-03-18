package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
public class CallMessageVO extends MessageBodyVO {
    private String callNumber;
    private String callState;
    private Integer callType;
    public CallMessageVO() {
    }
    public CallMessageVO(Integer num, String str, String str2) {
        this.callType = num;
        this.callNumber = str;
        this.callState = str2;
    }
    public String getCallNumber() { return this.callNumber; }
    public String getCallState() { return this.callState; }
    public Integer getCallType() { return this.callType; }
    public void setCallNumber(String str) { this.callNumber = str; }
    public void setCallState(String str) { this.callState = str; }
    public void setCallType(Integer num) { this.callType = num; }
    @NonNull
    public String toString() {
        return "CallMessageVO{callType=" + this.callType + ", callNumber='" + this.callNumber + "', callState='" + this.callState + "'}";
    }
}
