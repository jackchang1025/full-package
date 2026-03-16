package com.vendor.rat.auto.engine.vendor;

import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

/**
 * vivo 厂商适配引擎 (模块 03)
 * 市场份额: ~12%
 */
public class VivoEngine extends AutoEngine {

    private static final String IQOO_SECURE = "com.vivo.permissionmanager";

    @Override
    public boolean matchWindow(String packageName, String className) {
        return IQOO_SECURE.equals(packageName);
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // TODO: 实现 vivo 自启动管理自动化
    }

    @Override
    public void execute() {
        // TODO: 启动 vivo 自启动管理页面
    }
}
