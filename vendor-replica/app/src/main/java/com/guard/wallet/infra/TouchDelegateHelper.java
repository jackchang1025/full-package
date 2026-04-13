package com.guard.wallet.infra;

import android.graphics.Region;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo;

/**
 * TouchDelegateHelper — 触摸委托信息辅助工具类。
 * 提供 TouchDelegateInfo 的获取、区域查询，以及 BlendMode / WifiNetworkSpecifier 的兼容封装。
 *
 * vendor 原始路径: a0/d.java
 */
public class TouchDelegateHelper {

    /** 从 AccessibilityNodeInfo 获取 TouchDelegateInfo */
    public static TouchDelegateInfo getTouchDelegateInfo(AccessibilityNodeInfo node) {
        // TODO: implement when a0/ is ported
        return null;
    }

    /** 获取 TouchDelegateInfo 中的区域数量 */
    public static int getRegionCount(TouchDelegateInfo info) {
        // TODO: implement when a0/ is ported
        return 0;
    }

    /** 获取 TouchDelegateInfo 中指定索引的区域 */
    public static Region getRegionAt(TouchDelegateInfo info, int index) {
        // TODO: implement when a0/ is ported
        return null;
    }

    /** 解包 TouchDelegateInfo（直接返回原对象） */
    public static TouchDelegateInfo unwrapDelegateInfo(TouchDelegateInfo info) {
        return info;
    }

    /** 获取指定区域对应的目标节点 */
    public static AccessibilityNodeInfo getTargetNodeForRegion(TouchDelegateInfo info, Region region) {
        // TODO: implement when a0/ is ported
        return null;
    }

    /** 获取默认 BlendMode（API 29+），低版本返回 null */
    public static android.graphics.BlendMode getDefaultBlendMode() {
        return null;
    }

    /** 为 Paint 设置 BlendMode（API 29+），低版本为空操作 */
    public static void applyBlendMode(android.graphics.Paint paint, android.graphics.BlendMode mode) {
        if (android.os.Build.VERSION.SDK_INT >= 29 && mode != null) {
            paint.setBlendMode(mode);
        }
    }

    /** 清除/初始化（合成类中的空操作） */
    public static void clearInit() {
    }

    /** WifiNetworkSpecifier 构建器 — 构建最终 Specifier（API 29+） */
    public static android.net.wifi.WifiNetworkSpecifier buildWifiSpecifier(Object builder) {
        return null;
    }

    /** WifiNetworkSpecifier 构建器 — 获取构建器实例 */
    public static Object getWifiBuilder(Object builder) { return builder; }

    /** WifiNetworkSpecifier 构建器 — 设置密钥 */
    public static Object setWifiKey(Object builder, String key) { return builder; }

    /** WifiNetworkSpecifier 构建器 — 设置 SSID */
    public static Object setWifiSsid(Object builder, String ssid) { return builder; }

    /** WifiNetworkSpecifier 构建器 — 创建新构建器 */
    public static Object createWifiBuilder() { return null; }

    /** 创建新的 SurfaceControl.Transaction（用于截屏跳过等场景） */
    public static void beginTransaction() {
    }

    /** 获取当前 SurfaceControl.Transaction 实例 */
    public static android.view.SurfaceControl.Transaction getTransaction() {
        return null;
    }
}
