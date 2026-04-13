package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.media.RecordState;

public class DeviceRecordStateVO extends MessageBodyVO {
    private Integer allowRecord;
    private Integer audioSource;
    private String message;
    private RecordState state;

    public DeviceRecordStateVO() {}
    public DeviceRecordStateVO(Integer allowRecord, Integer audioSource, RecordState state, String message) {
        this.allowRecord = allowRecord; this.audioSource = audioSource;
        this.state = state; this.message = message;
    }

    public Integer getAllowRecord() { return this.allowRecord; }
    public Integer getAudioSource() { return this.audioSource; }
    public String getMessage() { return this.message; }
    public RecordState getState() { return this.state; }
    public void setAllowRecord(Integer v) { this.allowRecord = v; }
    public void setAudioSource(Integer v) { this.audioSource = v; }
    public void setMessage(String v) { this.message = v; }
    public void setState(RecordState v) { this.state = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceRecordStateVO{allowRecord=" + this.allowRecord + ", audioSource=" + this.audioSource
                + ", state=" + this.state + ", message=" + this.message + "}";
    }
}
