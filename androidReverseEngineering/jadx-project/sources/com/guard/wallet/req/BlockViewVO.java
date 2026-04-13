package com.guard.wallet.req;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class BlockViewVO implements Serializable {
    private Drawable blockDrawable;
    private boolean destroyLock;
    private String hint;
    private boolean transparent;
    private boolean zeroBrightness;

    public BlockViewVO() {
        this.transparent = false;
        this.zeroBrightness = true;
        this.destroyLock = true;
    }

    public Drawable getBlockDrawable() {
        return this.blockDrawable;
    }

    public String getHint() {
        return this.hint;
    }

    public boolean isDestroyLock() {
        return this.destroyLock;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public boolean isZeroBrightness() {
        return this.zeroBrightness;
    }

    public void setBlockDrawable(Drawable drawable) {
        this.blockDrawable = drawable;
    }

    public void setDestroyLock(boolean z2) {
        this.destroyLock = z2;
    }

    public void setHint(String str) {
        this.hint = str;
    }

    public void setTransparent(boolean z2) {
        this.transparent = z2;
    }

    public void setZeroBrightness(boolean z2) {
        this.zeroBrightness = z2;
    }

    @NonNull
    public String toString() {
        return "BlockViewVO{transparent=" + this.transparent + ", hint='" + this.hint + "', zeroBrightness=" + this.zeroBrightness + "', destroyLock=" + this.destroyLock + "'}";
    }

    public BlockViewVO(boolean z2, String str, boolean z3, boolean z4) {
        this.transparent = z2;
        this.hint = str;
        this.zeroBrightness = z3;
        this.destroyLock = z4;
    }
}
