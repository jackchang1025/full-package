package com.vendor.rat.control.handler;

/**
 * 音量动作枚举
 * Panel volstate → APK 动作映射
 */
public enum VolumeAction {
    UP,       // "0" → 增加音量
    DOWN,     // "1" → 减少音量
    UNKNOWN;

    public static VolumeAction fromState(String state) {
        if (state == null) return UNKNOWN;
        switch (state) {
            case "0": return UP;
            case "1": return DOWN;
            default:  return UNKNOWN;
        }
    }
}
