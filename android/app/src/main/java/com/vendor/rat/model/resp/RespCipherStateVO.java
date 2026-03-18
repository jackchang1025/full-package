package com.vendor.rat.model.resp;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import com.vendor.rat.model.req.MessageBodyVO;
import com.vendor.rat.model.req.TouchEvent;
import java.util.List;
/* loaded from: classes.dex */
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
    public RespCipherStateVO() {
    }
    public RespCipherStateVO(String str, Integer num, String str2, String str3, String str4, String str5, List<Point> list, List<Point> list2, List<TouchEvent> list3, Rect rect, Rect rect2) {
        this.deviceId = str;
        this.listenType = num;
        this.listenId = str2;
        this.subscribeId = str3;
        this.cipherGradeCode = str4;
        this.textCipher = str5;
        this.patternCipher = list;
        this.touchCipher = list2;
        this.eventCipher = list3;
        this.boundsInScreen = rect;
        this.boundsInParent = rect2;
    }
    public Rect getBoundsInParent() {
        return this.boundsInParent;
    }
    public Rect getBoundsInScreen() {
        return this.boundsInScreen;
    }
    public String getCipherGradeCode() {
        return this.cipherGradeCode;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public List<TouchEvent> getEventCipher() {
        return this.eventCipher;
    }
    public String getListenId() {
        return this.listenId;
    }
    public Integer getListenType() {
        return this.listenType;
    }
    public List<Point> getPatternCipher() {
        return this.patternCipher;
    }
    public String getSubscribeId() {
        return this.subscribeId;
    }
    public String getTextCipher() {
        return this.textCipher;
    }
    public List<Point> getTouchCipher() {
        return this.touchCipher;
    }
    public void setBoundsInParent(Rect rect) {
        this.boundsInParent = rect;
    }
    public void setBoundsInScreen(Rect rect) {
        this.boundsInScreen = rect;
    }
    public void setCipherGradeCode(String str) {
        this.cipherGradeCode = str;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setEventCipher(List<TouchEvent> list) {
        this.eventCipher = list;
    }
    public void setListenId(String str) {
        this.listenId = str;
    }
    public void setListenType(Integer num) {
        this.listenType = num;
    }
    public void setPatternCipher(List<Point> list) {
        this.patternCipher = list;
    }
    public void setSubscribeId(String str) {
        this.subscribeId = str;
    }
    public void setTextCipher(String str) {
        this.textCipher = str;
    }
    public void setTouchCipher(List<Point> list) {
        this.touchCipher = list;
    }
    @NonNull
    public String toString() {
        return "RespCipherStateVO{deviceId='" + this.deviceId + "', listenType=" + this.listenType + ", listenId='" + this.listenId + "', subscribeId='" + this.subscribeId + "', cipherGradeCode='" + this.cipherGradeCode + "', textCipher='" + this.textCipher + "', patternCipher=" + this.patternCipher + ", touchCipher=" + this.touchCipher + ", eventCipher=" + this.eventCipher + ", boundsInScreen=" + this.boundsInScreen + ", boundsInParent=" + this.boundsInParent + '}';
    }
}
