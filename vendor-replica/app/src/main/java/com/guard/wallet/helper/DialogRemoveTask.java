package com.guard.wallet.helper;

import android.util.Log;

/**
 * 延迟弹出对话框的 Runnable 任务。
 *
 * <p>当非主线程调用对话框显示时，通过 Handler.postDelayed 提交本任务，
 * 延迟 300ms 后在主线程执行对话框创建。
 *
 * <p>根据构造参数 {@code action} 区分对话框类型：
 * <ul>
 *   <li>0 — 通知对话框，调用 {@link NotificationDialog#b}</li>
 *   <li>default — WiFi 引导对话框，调用 {@link NotificationDialog#a}</li>
 * </ul>
 *
 * <p>vendor 原名: {@code helper.m}
 */
public final class DialogRemoveTask implements Runnable {
    public final int a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;

    public DialogRemoveTask(int a, String b, String c, String d, String e, String f) {
        this.a = a; this.b = b; this.c = c; this.d = d; this.e = e; this.f = f;
    }

    @Override
    public final void run() {
        switch (a) {
            case 0:
                if (NotificationDialog.b(b, c, d, e, f)) {
                    Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
                } else {
                    Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
                }
                break;
            default:
                if (NotificationDialog.a(b, c, d, e, f)) {
                    Log.d("com.guard.wallet.helper.n", "弹出WIFI引导对话框成功");
                } else {
                    Log.e("com.guard.wallet.helper.n", "弹出WIFI引导对话框失败");
                }
                break;
        }
    }
}
