package com.guard.wallet.infra;
import com.guard.wallet.core.AppUtils;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

/**
 * 窗口信息兼容工具 — 提供 AccessibilityWindowInfo 的 API 版本兼容封装。
 * API 33+ 使用 getRoot(prefetchingStrategy=4) 预取策略，旧版回退到 getRoot()。
 *
 * vendor 原始路径: a0/g.java
 */
public class WindowInfoCompat {
    /**
     * 从 AccessibilityWindowInfo 获取根节点。
     * API 33+ 使用 getRoot(4) 预取策略，旧版回退到 getRoot()。
     */
    public static AccessibilityNodeInfo getRootNode(AccessibilityWindowInfo windowInfo) {
        if (windowInfo == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                return windowInfo.getRoot(4);
            }
            return windowInfo.getRoot();
        } catch (Exception e) {
            AppUtils.s("WindowInfoCompat", e);
            return null;
        }
    }
}
