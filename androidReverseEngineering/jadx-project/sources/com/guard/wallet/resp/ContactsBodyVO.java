package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

/* loaded from: classes.dex */
public class ContactsBodyVO extends MessageBodyVO {
    private List<DeviceContactInfoVO> contacts;
    private String deviceId;

    public ContactsBodyVO() {
    }

    public ContactsBodyVO(String str, List<DeviceContactInfoVO> list) {
        this.deviceId = str;
        this.contacts = list;
    }

    public List<DeviceContactInfoVO> getContacts() {
        return this.contacts;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setContacts(List<DeviceContactInfoVO> list) {
        this.contacts = list;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    @NonNull
    public String toString() {
        return "ContactsBodyVO{deviceId='" + this.deviceId + "', contacts=" + this.contacts + '}';
    }
}
