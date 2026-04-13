package com.guard.wallet.entity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.req.TouchEvent;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class DeviceCipher implements Serializable {
    private Rect boundsInParent;
    private Rect boundsInScreen;
    private String cipherGradeCode;
    private List<TouchEvent> eventCipher;
    private Boolean locked;
    private List<Point> patternCipher;
    private String textCipher;
    private List<Point> touchCipher;

    public DeviceCipher() {
    }

    public DeviceCipher(String str, String str2, List<Point> list, List<Point> list2, List<TouchEvent> list3, Rect rect, Rect rect2, Boolean bool) {
        this.cipherGradeCode = str;
        this.textCipher = str2;
        this.patternCipher = list;
        this.touchCipher = list2;
        this.eventCipher = list3;
        this.boundsInScreen = rect;
        this.boundsInParent = rect2;
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
        return "DeviceCipher{cipherGradeCode='" + this.cipherGradeCode + "', textCipher='" + this.textCipher + "', patternCipher=" + this.patternCipher + ", touchCipher=" + this.touchCipher + ", eventCipher=" + this.eventCipher + ", boundsInScreen=" + this.boundsInScreen + ", boundsInParent=" + this.boundsInParent + ", locked=" + this.locked + '}';
    }
}
