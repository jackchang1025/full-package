package com.guard.wallet.enums;

/**
 * 检查线程状态 (b=IDLE/-1, c=RUNNING/0, d=STOPPED/2)。
 * vendor 原始路径: r/d.java
 */
public enum CheckThreadState {
    b(-1), c(0), d(2);

    public static final CheckThreadState[] e = values();
    public final int a;

    CheckThreadState(int val) { this.a = val; }

    @Override
    public final String toString() { return this.a + " " + this.name(); }
}
