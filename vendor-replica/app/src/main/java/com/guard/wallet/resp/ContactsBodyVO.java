package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

public class ContactsBodyVO extends MessageBodyVO {
    private List<DeviceContactInfoVO> contacts;
    private String deviceId;

    public ContactsBodyVO() {}
    public ContactsBodyVO(String deviceId, List<DeviceContactInfoVO> contacts) {
        this.deviceId = deviceId; this.contacts = contacts;
    }

    public List<DeviceContactInfoVO> getContacts() { return this.contacts; }
    public String getDeviceId() { return this.deviceId; }
    public void setContacts(List<DeviceContactInfoVO> v) { this.contacts = v; }
    public void setDeviceId(String v) { this.deviceId = v; }

    @NonNull
    @Override
    public String toString() {
        return "ContactsBodyVO{deviceId='" + this.deviceId + "', contacts=" + this.contacts + "}";
    }
}
