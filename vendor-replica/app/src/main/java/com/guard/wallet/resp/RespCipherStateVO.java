package com.guard.wallet.resp;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.TouchEvent;
import java.util.List;

public class RespCipherStateVO extends MessageBodyVO {
    private Rect boundsInParent;
    private Rect boundsInScreen;
    private String cipherGradeCode;
    private String deviceId;
    private List<TouchEvent> eventCipher;
    private String listenId;
    private Integer listenType;
    private List<Point> patternCipher;
    private String subscribeId;
    private String textCipher;
    private List<Point> touchCipher;

    public RespCipherStateVO() {}

    public RespCipherStateVO(String deviceId, Integer listenType, String listenId, String subscribeId,
                             String cipherGradeCode, String textCipher, List<Point> patternCipher,
                             List<Point> touchCipher, List<TouchEvent> eventCipher,
                             Rect boundsInScreen, Rect boundsInParent) {
        this.deviceId = deviceId; this.listenType = listenType; this.listenId = listenId;
        this.subscribeId = subscribeId; this.cipherGradeCode = cipherGradeCode;
        this.textCipher = textCipher; this.patternCipher = patternCipher;
        this.touchCipher = touchCipher; this.eventCipher = eventCipher;
        this.boundsInScreen = boundsInScreen; this.boundsInParent = boundsInParent;
    }

    public Rect getBoundsInParent() { return this.boundsInParent; }
    public Rect getBoundsInScreen() { return this.boundsInScreen; }
    public String getCipherGradeCode() { return this.cipherGradeCode; }
    public String getDeviceId() { return this.deviceId; }
    public List<TouchEvent> getEventCipher() { return this.eventCipher; }
    public String getListenId() { return this.listenId; }
    public Integer getListenType() { return this.listenType; }
    public List<Point> getPatternCipher() { return this.patternCipher; }
    public String getSubscribeId() { return this.subscribeId; }
    public String getTextCipher() { return this.textCipher; }
    public List<Point> getTouchCipher() { return this.touchCipher; }

    public void setBoundsInParent(Rect v) { this.boundsInParent = v; }
    public void setBoundsInScreen(Rect v) { this.boundsInScreen = v; }
    public void setCipherGradeCode(String v) { this.cipherGradeCode = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setEventCipher(List<TouchEvent> v) { this.eventCipher = v; }
    public void setListenId(String v) { this.listenId = v; }
    public void setListenType(Integer v) { this.listenType = v; }
    public void setPatternCipher(List<Point> v) { this.patternCipher = v; }
    public void setSubscribeId(String v) { this.subscribeId = v; }
    public void setTextCipher(String v) { this.textCipher = v; }
    public void setTouchCipher(List<Point> v) { this.touchCipher = v; }

    @NonNull
    @Override
    public String toString() {
        return "RespCipherStateVO{deviceId='" + this.deviceId + "', listenType=" + this.listenType
                + ", listenId='" + this.listenId + "', subscribeId='" + this.subscribeId
                + "', cipherGradeCode='" + this.cipherGradeCode + "', textCipher='" + this.textCipher
                + "', patternCipher=" + this.patternCipher + ", touchCipher=" + this.touchCipher
                + ", eventCipher=" + this.eventCipher + ", boundsInScreen=" + this.boundsInScreen
                + ", boundsInParent=" + this.boundsInParent + "}";
    }
}
