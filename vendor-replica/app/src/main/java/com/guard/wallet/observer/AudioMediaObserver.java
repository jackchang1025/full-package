package com.guard.wallet.observer;

import com.guard.wallet.core.AppUtils;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * vendor y.a -> AudioMediaObserver
 *
 * ContentObserver -- 音频媒体变化监听。
 * 注册到 MediaStore.Audio.Media.EXTERNAL_CONTENT_URI，
 * 当音频文件新增/修改/删除时触发 onChange。
 */
public class AudioMediaObserver extends ContentObserver {
    private static final String TAG = "AudioMediaObserver";

    public AudioMediaObserver() {
        super(new Handler(Looper.getMainLooper()));
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        try {
            super.onChange(selfChange, uri);
            Log.d(TAG, "Audio media changed: " + uri);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }
}
