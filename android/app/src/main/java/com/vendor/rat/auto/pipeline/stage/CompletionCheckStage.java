package com.vendor.rat.auto.pipeline.stage;

import android.content.Context;
import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

/**
 * Stage 2: 完成状态检查
 *
 * 检查保活自动化是否已完成 (持久化)。
 * 如果已完成且版本未变化，终止管道（无需重跑）。
 * 如果版本变化，即使已完成也继续（VersionCheckStage 已重置标志）。
 */
public class CompletionCheckStage implements PipelineStage {

    private static final String TAG = "CompletionCheckStage";
    private static final String PREF_NAME = "keep_alive_state";
    private static final String KEY_COMPLETED = "keep_alive_completed";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        Context appCtx = passable.getAppContext();
        if (appCtx == null) {
            next.run();
            return;
        }

        boolean completed = isKeepAliveCompleted(appCtx);

        if (completed && !passable.isVersionChanged()) {
            Log.d(TAG, "Keep-alive already completed, version unchanged, skip pipeline");
            return;
        }

        if (passable.isVersionChanged()) {
            Log.i(TAG, "Version changed, re-running pipeline even if previously completed");
        } else {
            Log.d(TAG, "Keep-alive not completed, proceeding");
        }

        next.run();
    }

    private static boolean isKeepAliveCompleted(Context ctx) {
        return ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_COMPLETED, false);
    }
}
