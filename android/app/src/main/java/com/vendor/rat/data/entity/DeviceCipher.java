package com.vendor.rat.data.entity;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import java.io.Serializable;
import java.util.List;

/**
 * ADAPT: vendor com.guard.wallet.entity.DeviceCipher
 * Lock screen cipher data model.
 * ADAPT: vendor references TouchEvent for eventCipher — using Object placeholder
 */
public class DeviceCipher implements Serializable {
    private Rect boundsInParent;
    private Rect boundsInScreen;
    private String cipherGradeCode;
    private List<Object> eventCipher; // TODO: VENDOR_VERIFY — vendor uses List<TouchEvent>
    private Boolean locked;
    private List<Point> patternCipher;
    private String textCipher;
    private List<Point> touchCipher;

    public DeviceCipher() {
    }

    public DeviceCipher(String cipherGradeCode, String textCipher,
                        List<Point> patternCipher, List<Point> touchCipher,
                        List<Object> eventCipher, Rect boundsInScreen,
                        Rect boundsInParent, Boolean locked) {
        this.cipherGradeCode = cipherGradeCode;
        this.textCipher = textCipher;
        this.patternCipher = patternCipher;
        this.touchCipher = touchCipher;
        this.eventCipher = eventCipher;
        this.boundsInScreen = boundsInScreen;
        this.boundsInParent = boundsInParent;
        this.locked = locked;
    }

    public Rect getBoundsInParent() { return this.boundsInParent; }
    public Rect getBoundsInScreen() { return this.boundsInScreen; }
    public String getCipherGradeCode() { return this.cipherGradeCode; }
    public List<Object> getEventCipher() { return this.eventCipher; }
    public Boolean getLocked() { return this.locked; }
    public List<Point> getPatternCipher() { return this.patternCipher; }
    public String getTextCipher() { return this.textCipher; }
    public List<Point> getTouchCipher() { return this.touchCipher; }

    public void setBoundsInParent(Rect boundsInParent) { this.boundsInParent = boundsInParent; }
    public void setBoundsInScreen(Rect boundsInScreen) { this.boundsInScreen = boundsInScreen; }
    public void setCipherGradeCode(String cipherGradeCode) { this.cipherGradeCode = cipherGradeCode; }
    public void setEventCipher(List<Object> eventCipher) { this.eventCipher = eventCipher; }
    public void setLocked(Boolean locked) { this.locked = locked; }
    public void setPatternCipher(List<Point> patternCipher) { this.patternCipher = patternCipher; }
    public void setTextCipher(String textCipher) { this.textCipher = textCipher; }
    public void setTouchCipher(List<Point> touchCipher) { this.touchCipher = touchCipher; }

    @NonNull
    public String toString() {
        return "DeviceCipher{cipherGradeCode='" + this.cipherGradeCode
                + "', textCipher='" + this.textCipher
                + "', patternCipher=" + this.patternCipher
                + ", touchCipher=" + this.touchCipher
                + ", eventCipher=" + this.eventCipher
                + ", boundsInScreen=" + this.boundsInScreen
                + ", boundsInParent=" + this.boundsInParent
                + ", locked=" + this.locked + '}';
    }
}
