package com.guard.wallet.enums;

/**
 * 开发者选项开启阶段 (12 值, -1~11)。
 * vendor 原始路径: r/f.java
 */
public enum DevelopmentStage {
    b(-1), c(0), d(1), e(2), f(3), g(4), h(5), i(7), j(8), k(9), l(10), m(11);

    public static final DevelopmentStage[] n = values();
    public final int a;

    DevelopmentStage(int val) { this.a = val; }

    @Override
    public final String toString() { return this.a + " " + this.name(); }
}
