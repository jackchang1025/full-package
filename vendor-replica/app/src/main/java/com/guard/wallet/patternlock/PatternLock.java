package com.guard.wallet.patternlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 图案完成锁。
 * 封装 ReentrantLock，防止并发处理图案完成事件。
 *
 * vendor 原始路径: o0/i.java
 */
public final class PatternLock {
    public final PatternLockView a;
    public final ReentrantLock b = new ReentrantLock();

    public PatternLock(PatternLockView var1) {
        this.a = var1;
    }
}
