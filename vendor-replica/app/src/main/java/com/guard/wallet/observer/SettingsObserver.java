package com.guard.wallet.observer;

import com.guard.wallet.core.AppUtils;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * vendor y.d -> SettingsObserver
 *
 * ContentObserver -- ADB/开发者设置变化监听。
 * 监听 Settings.Global 中 development_settings_enabled / adb_enabled /
 * adb_wifi_enabled 的变化，变化后记录日志。
 */
public final class SettingsObserver extends ContentObserver {
    private static final String TAG = "SettingsObserver";

    public SettingsObserver() {
        super(new Handler(Looper.getMainLooper()));
    }

    @Override
    public final void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public final void onChange(boolean selfChange, Uri uri) {
        try {
            super.onChange(selfChange, uri);
            Log.d(TAG, "Settings changed: " + uri);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }
}
