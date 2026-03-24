package com.vendor.rat.auto.pipeline.stage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

/**
 * Stage 1: 版本检测
 *
 * 对比当前 APK 的 versionCode 与 SharedPreferences 中保存的值。
 * 如果版本变化（更新安装），重置 keepAliveCompleted 标志，
 * 让后续 CompletionCheckStage 允许管道继续执行。
 */
public class VersionCheckStage implements PipelineStage {

    private static final String TAG = "VersionCheckStage";
    private static final String PREF_NAME = "keep_alive_state";
    private static final String KEY_LAST_VERSION = "last_version_code";

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        Context appCtx = passable.getAppContext();
        if (appCtx == null) {
            next.run();
            return;
        }

        int currentVersion = getVersionCode(appCtx);
        int savedVersion = getSavedVersionCode(appCtx);
        passable.setCurrentVersionCode(currentVersion);
        passable.setSavedVersionCode(savedVersion);

        if (currentVersion != savedVersion && savedVersion != 0) {
            passable.setVersionChanged(true);
            resetCompletedFlag(appCtx);
            Log.i(TAG, "Version changed: " + savedVersion + " -> " + currentVersion
                + ", keepAliveCompleted reset");
        } else if (savedVersion == 0) {
            // 首次安装，无需重置
            passable.setVersionChanged(false);
            Log.d(TAG, "First install, versionCode=" + currentVersion);
        } else {
            passable.setVersionChanged(false);
            Log.d(TAG, "Version unchanged: " + currentVersion);
        }

        next.run();
    }

    private static int getVersionCode(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager()
                .getPackageInfo(ctx.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int getSavedVersionCode(Context ctx) {
        return ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_LAST_VERSION, 0);
    }

    private static void resetCompletedFlag(Context ctx) {
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean("keep_alive_completed", false)
            .apply();
    }

    /** 供 MarkCompletedStage 调用 — 保存当前 versionCode */
    public static void saveVersionCode(Context ctx, int versionCode) {
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_LAST_VERSION, versionCode)
            .apply();
    }
}
