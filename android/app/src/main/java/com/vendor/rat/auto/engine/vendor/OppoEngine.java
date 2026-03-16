package com.vendor.rat.auto.engine.vendor;

import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

/**
 * OPPO 厂商适配引擎 (模块 03)
 * 市场份额: ~18%
 */
public class OppoEngine extends AutoEngine {

    private static final String COLOR_OS = "com.coloros.safecenter";

    @Override
    public boolean matchWindow(String packageName, String className) {
        return COLOR_OS.equals(packageName);
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // TODO: 实现 OPPO 自启动管理自动化
    }

    @Override
    public void execute() {
        // TODO: 启动 OPPO 自启动管理页面
    }
}
