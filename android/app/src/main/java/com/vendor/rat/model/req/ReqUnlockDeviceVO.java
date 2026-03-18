package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
// ADAPT: com.guard.wallet.entity.Point -> com.vendor.rat.auto.entity.Point

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import java.io.Serializable;
import java.util.List;

public class ReqUnlockDeviceVO implements Serializable {
    private Rect boundsInParent;
    private Rect boundsInScreen;
    private String cipherGradeCode;
    private String deviceId;
    private Long duration;
    private List<TouchEvent> eventCipher;
    private Boolean locked;
    private List<Point> patternCipher;
    private String textCipher;
    private List<Point> touchCipher;

    public ReqUnlockDeviceVO() {
        this.locked = Boolean.FALSE;
    }

    public ReqUnlockDeviceVO(String str, List<Point> list, List<Point> list2, List<TouchEvent> list3, String str2, String str3, Rect rect, Rect rect2, Long l2, Boolean bool) {
        this.textCipher = str;
        this.patternCipher = list;
        this.touchCipher = list2;
        this.eventCipher = list3;
        this.deviceId = str2;
        this.cipherGradeCode = str3;
        this.boundsInScreen = rect;
        this.boundsInParent = rect2;
        this.duration = l2;
        this.locked = bool;
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

    public Long getDuration() {
        return this.duration;
    }

    public List<TouchEvent> getEventCipher() {
        return this.eventCipher;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public List<Point> getPatternCipher() {
        return this.patternCipher;
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

    public void setDuration(Long l2) {
        this.duration = l2;
    }

    public void setEventCipher(List<TouchEvent> list) {
        this.eventCipher = list;
    }

    public void setLocked(Boolean bool) {
        this.locked = bool;
    }

    public void setPatternCipher(List<Point> list) {
        this.patternCipher = list;
    }

    public void setTextCipher(String str) {
        this.textCipher = str;
    }

    public void setTouchCipher(List<Point> list) {
        this.touchCipher = list;
    }

    @NonNull
    public String toString() {
        return "ReqUnlockDeviceVO{textCipher='" + this.textCipher + "', patternCipher=" + this.patternCipher + ", touchCipher=" + this.touchCipher + ", eventCipher=" + this.eventCipher + ", deviceId='" + this.deviceId + "', cipherGradeCode='" + this.cipherGradeCode + "', boundsInScreen=" + this.boundsInScreen + ", boundsInParent=" + this.boundsInParent + ", duration=" + this.duration + ", locked=" + this.locked + '}';
    }
}
