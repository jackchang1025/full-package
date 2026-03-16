package com.vendor.rat.auto.engine.vendor;

import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

/**
 * 三星厂商适配引擎 (模块 03)
 * 市场份额: ~10%
 */
public class SamsungEngine extends AutoEngine {

    private static final String DEVICE_CARE = "com.samsung.android.lool";

    @Override
    public boolean matchWindow(String packageName, String className) {
        return DEVICE_CARE.equals(packageName);
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // TODO: 实现三星电池优化排除自动化
    }

    @Override
    public void execute() {
        // TODO: 启动三星 Device Care 页面
    }
}
