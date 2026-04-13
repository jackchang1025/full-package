package com.guard.wallet.enums;

/**
 * 任务状态 (b=PENDING/0, c=ACTIVE/1, d=DONE/2)。
 * vendor 原始路径: r/e.java
 */
public enum TaskState {
    b(0), c(1), d(2);

    public static final TaskState[] e = values();
    public final int a;

    TaskState(int val) { this.a = val; }

    @Override
    public final String toString() { return this.a + " " + this.name(); }
}
