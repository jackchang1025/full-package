package com.guard.wallet.enums;

/**
 * 权限状态 (8 值, 0~7)。
 * vendor 原始路径: r/g.java
 */
public enum PermissionState {
    b(0), c(1), d(2), e(3), f(4), g(5), h(6), i(7);

    public static final PermissionState[] j = values();
    public final int a;

    PermissionState(int val) {
        this.a = val;
    }

    @Override
    public final String toString() {
        return this.a + " " + this.name();
    }
}
