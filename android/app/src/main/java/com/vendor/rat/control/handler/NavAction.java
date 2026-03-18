package com.vendor.rat.control.handler;

/**
 * 导航动作枚举
 * Panel nav shortcut → APK 动作映射
 */
public enum NavAction {
    HOME,         // "ho" → 主页
    BACK,         // "bak" → 返回
    RECENTS,      // "rec" → 多任务
    UNKNOWN;

    public static NavAction fromShortcut(String shortcut) {
        if (shortcut == null) return UNKNOWN;
        switch (shortcut) {
            case "ho":  return HOME;
            case "bak": return BACK;
            case "rec": return RECENTS;
            default:    return UNKNOWN;
        }
    }
}
