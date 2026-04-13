package org.bouncycastle.pqc.crypto.qtesla;

/* loaded from: classes.dex */
final class IntSlicer {
    private int base;
    private final int[] values;

    public IntSlicer(int[] iArr, int i2) {
        this.values = iArr;
        this.base = i2;
    }

    public final int at(int i2) {
        return this.values[this.base + i2];
    }

    public final IntSlicer copy() {
        return new IntSlicer(this.values, this.base);
    }

    public final IntSlicer from(int i2) {
        return new IntSlicer(this.values, this.base + i2);
    }

    public final void incBase(int i2) {
        this.base += i2;
    }

    public final int at(int i2, int i3) {
        this.values[this.base + i2] = i3;
        return i3;
    }

    public final int at(int i2, long j2) {
        int[] iArr = this.values;
        int i3 = this.base + i2;
        int i4 = (int) j2;
        iArr[i3] = i4;
        return i4;
    }
}
