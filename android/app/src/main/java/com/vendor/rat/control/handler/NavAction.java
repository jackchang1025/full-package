package com.vendor.rat.control.handler;

/**
 * 导航动作枚举
 * Panel nav shortcut → APK 动作映射
 */
public enum NavAction {
    WAKE_SCREEN,  // "ho" → 点亮屏幕 (不是 HOME)
    BACK,         // "bak" → 返回
    RECENTS,      // "rec" → 多任务
    UNKNOWN;

    public static NavAction fromShortcut(String shortcut) {
        if (shortcut == null) return UNKNOWN;
        switch (shortcut) {
            case "ho":  return WAKE_SCREEN;
            case "bak": return BACK;
            case "rec": return RECENTS;
            default:    return UNKNOWN;
        }
    }
}
