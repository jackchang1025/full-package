package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
// ADAPT: j.c (obfuscated enum) -> String for state field
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
public class DeviceRecordStateVO extends MessageBodyVO {
    private Integer allowRecord;
    private Integer audioSource;
    private String message;
    private String state; // ADAPT: vendor uses obfuscated j.c type
    public DeviceRecordStateVO() {
    }
    public DeviceRecordStateVO(Integer num, Integer num2, String state, String str) {
        this.allowRecord = num;
        this.audioSource = num2;
        this.state = state;
        this.message = str;
    }
    public Integer getAllowRecord() { return this.allowRecord; }
    public Integer getAudioSource() { return this.audioSource; }
    public String getMessage() { return this.message; }
    public String getState() { return this.state; }
    public void setAllowRecord(Integer num) { this.allowRecord = num; }
    public void setAudioSource(Integer num) { this.audioSource = num; }
    public void setMessage(String str) { this.message = str; }
    public void setState(String state) { this.state = state; }
    @NonNull
    public String toString() {
        return "DeviceRecordStateVO{allowRecord=" + this.allowRecord + ", audioSource=" + this.audioSource + ", state=" + this.state + ", message=" + this.message + '}';
    }
}
