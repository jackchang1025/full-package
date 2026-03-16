package com.vendor.rat.auto.engine.vendor;

import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

/**
 * 华为厂商适配引擎 (模块 03)
 *
 * 适配 3 个开关:
 *   - 自启动 (com.huawei.systemmanager)
 *   - 关联启动
 *   - 后台活动
 *
 * 市场份额: ~20%（复杂度最高）
 */
public class HuaweiEngine extends AutoEngine {

    private static final String SYSTEM_MANAGER = "com.huawei.systemmanager";

    @Override
    public boolean matchWindow(String packageName, String className) {
        return SYSTEM_MANAGER.equals(packageName);
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // TODO: 实现华为启动管理页面的 3 个开关自动化
    }

    @Override
    public void execute() {
        // TODO: 启动华为应用启动管理页面
    }
}
