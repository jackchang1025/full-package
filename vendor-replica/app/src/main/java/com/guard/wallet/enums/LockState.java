package com.guard.wallet.enums;

/**
 * 锁屏状态 (b=UNKNOWN/0, c=LOCKED/1, d=UNLOCKED/2)。
 * vendor 原始路径: r/c.java
 */
public enum LockState {
    b(0), c(1), d(2);

    public static final LockState[] e = values();
    public final int a;

    LockState(int val) { this.a = val; }

    @Override
    public final String toString() { return this.a + " " + this.name(); }
}
