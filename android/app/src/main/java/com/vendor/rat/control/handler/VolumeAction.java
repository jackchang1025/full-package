package com.vendor.rat.control.handler;

/**
 * 音量动作枚举
 * Panel volstate → APK 动作映射
 */
public enum VolumeAction {
    UP,       // "up" → 增加音量
    DOWN,     // "down" → 减少音量
    MUTE,     // "mute" → 静音
    UNMUTE,   // "unmute" → 取消静音
    UNKNOWN;

    public static VolumeAction fromState(String state) {
        if (state == null) return UNKNOWN;
        switch (state) {
            case "up":     return UP;
            case "down":   return DOWN;
            case "mute":   return MUTE;
            case "unmute": return UNMUTE;
            // 兼容旧数字格式
            case "0":      return UP;
            case "1":      return DOWN;
            default:       return UNKNOWN;
        }
    }
}
