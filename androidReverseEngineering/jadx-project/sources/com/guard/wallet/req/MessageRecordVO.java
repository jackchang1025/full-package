package com.guard.wallet.req;

import com.guard.wallet.req.MessageBodyVO;

/* loaded from: classes.dex */
public class MessageRecordVO<V extends MessageBodyVO> {
    private String deviceId;
    private V extraBody;
    private String intentCode;

    public MessageRecordVO() {
    }

    public MessageRecordVO(String str, String str2, V v2) {
        this.intentCode = str;
        this.deviceId = str2;
        this.extraBody = v2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public V getExtraBody() {
        return this.extraBody;
    }

    public String getIntentCode() {
        return this.intentCode;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setExtraBody(V v2) {
        this.extraBody = v2;
    }

    public void setIntentCode(String str) {
        this.intentCode = str;
    }
}
