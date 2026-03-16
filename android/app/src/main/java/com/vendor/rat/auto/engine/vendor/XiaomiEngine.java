package com.vendor.rat.auto.engine.vendor;

import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

/**
 * 小米厂商适配引擎 (模块 03)
 *
 * 适配:
 *   - 自启动管理 (com.miui.securitycenter)
 *   - 电池优化 (com.miui.powerkeeper)
 *   - 后台运行
 *
 * 市场份额: ~30%
 */
public class XiaomiEngine extends AutoEngine {

    private static final String SECURITY_CENTER = "com.miui.securitycenter";
    private static final String POWER_KEEPER = "com.miui.powerkeeper";

    @Override
    public boolean matchWindow(String packageName, String className) {
        return SECURITY_CENTER.equals(packageName)
            || POWER_KEEPER.equals(packageName);
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // TODO: 实现小米权限设置页面的自动化操作
    }

    @Override
    public void execute() {
        // TODO: 启动小米自启动管理页面
    }
}
