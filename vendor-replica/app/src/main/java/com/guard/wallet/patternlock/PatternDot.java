package com.guard.wallet.patternlock;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图案点数据 (Parcelable)。
 * 表示图案锁中的一个点位置 (行, 列)，
 * 使用静态缓存网格避免对象分配。
 *
 * vendor 原始路径: o0/d.java
 */
public final class PatternDot implements Parcelable {
    public static final Parcelable.Creator<PatternDot> CREATOR;
    public static final PatternDot[][] c;
    public final int a;
    public final int b;

    static {
        int size = PatternLockView.K;
        c = new PatternDot[size][size];

        for (int row = 0; row < PatternLockView.K; row++) {
            for (int col = 0; col < PatternLockView.K; col++) {
                c[row][col] = new PatternDot(row, col);
            }
        }

        CREATOR = new a(1);
    }

    public PatternDot(int row, int col) {
        a(row, col);
        this.a = row;
        this.b = col;
    }

    public PatternDot(Parcel var1) {
        this.b = var1.readInt();
        this.a = var1.readInt();
    }

    /** Validate row and column are within bounds */
    public static void a(int row, int col) {
        if (row < 0 || row > PatternLockView.K - 1) {
            StringBuilder sb = new StringBuilder("mRow must be in range 0-");
            sb.append(PatternLockView.K - 1);
            throw new IllegalArgumentException(sb.toString());
        }
        if (col < 0 || col > PatternLockView.K - 1) {
            StringBuilder sb = new StringBuilder("mColumn must be in range 0-");
            sb.append(PatternLockView.K - 1);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /** Get cached dot position (thread-safe) */
    public static PatternDot b(int row, int col) {
        synchronized (PatternDot.class) {
            a(row, col);
            return c[row][col];
        }
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final boolean equals(Object var1) {
        if (!(var1 instanceof PatternDot)) {
            return super.equals(var1);
        } else {
            PatternDot other = (PatternDot) var1;
            return this.b == other.b && this.a == other.a;
        }
    }

    @Override
    public final int hashCode() {
        return this.a * 31 + this.b;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder("(Row = ");
        sb.append(this.a);
        sb.append(", Col = ");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public final void writeToParcel(Parcel var1, int var2) {
        var1.writeInt(this.b);
        var1.writeInt(this.a);
    }

    /**
     * Parcelable Creator for both PatternDot (type=1) and PatternSavedState (type=2).
     */
    public static class a implements Parcelable.Creator {
        private final int type;

        public a(int type) {
            this.type = type;
        }

        @Override
        public Object createFromParcel(Parcel source) {
            if (type == 1) {
                return new PatternDot(source);
            } else {
                return new PatternSavedState(source);
            }
        }

        @Override
        public Object[] newArray(int size) {
            if (type == 1) {
                return new PatternDot[size];
            } else {
                return new PatternSavedState[size];
            }
        }
    }
}
