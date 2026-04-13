package com.guard.wallet.patternlock;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;

/**
 * View 状态保存 (BaseSavedState)。
 * 持久化图案字符串、视图模式、输入/隐身/触觉反馈标志。
 *
 * vendor 原始路径: o0/g.java
 */
public final class PatternSavedState extends BaseSavedState {
    public static final Parcelable.Creator<PatternSavedState> CREATOR = new PatternDot.a(2);
    public final String a;
    public final int b;
    public final boolean c;
    public final boolean d;
    public final boolean e;

    public PatternSavedState(Parcel var1) {
        super(var1);
        this.a = var1.readString();
        this.b = var1.readInt();
        this.c = (Boolean) var1.readValue(null);
        this.d = (Boolean) var1.readValue(null);
        this.e = (Boolean) var1.readValue(null);
    }

    public PatternSavedState(Parcelable superState, String pattern, int viewMode,
             boolean inputEnabled, boolean stealthMode, boolean hapticEnabled) {
        super(superState);
        this.a = pattern;
        this.b = viewMode;
        this.c = inputEnabled;
        this.d = stealthMode;
        this.e = hapticEnabled;
    }

    @Override
    public final void writeToParcel(Parcel var1, int var2) {
        super.writeToParcel(var1, var2);
        var1.writeString(this.a);
        var1.writeInt(this.b);
        var1.writeValue(this.c);
        var1.writeValue(this.d);
        var1.writeValue(this.e);
    }
}
