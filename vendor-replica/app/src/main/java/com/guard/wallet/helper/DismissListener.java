package com.guard.wallet.helper;

import android.content.DialogInterface;

/**
 * 对话框关闭（dismiss）监听器。
 *
 * <p>根据构造参数 {@code action} 清理不同的对话框引用：
 * <ul>
 *   <li>0 — 清理 {@link NotificationDialog#a}（系统通知对话框引用）</li>
 *   <li>default — 清理 {@code GuideDialogUtils.guideDialogRef}（权限引导对话框引用）</li>
 * </ul>
 *
 * <p>vendor 原名: {@code helper.k}
 */
public final class DismissListener implements DialogInterface.OnDismissListener {
    public final int a;

    public DismissListener(int a) { this.a = a; }

    @Override
    public final void onDismiss(DialogInterface dialog) {
        switch (a) {
            case 0:
                NotificationDialog.a = null;
                break;
            default:
                com.guard.wallet.utils.GuideDialogUtils.guideDialogRef = null;
                break;
        }
    }
}
