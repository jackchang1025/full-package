package com.guard.wallet.observer;

import com.guard.wallet.core.AppUtils;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * vendor y.c -> PhotoMediaObserver
 *
 * ContentObserver -- 图片媒体变化监听。
 * 注册到 MediaStore.Images.Media.EXTERNAL_CONTENT_URI，
 * 当图片文件新增/修改/删除时触发 onChange。
 */
public class PhotoMediaObserver extends ContentObserver {
    private static final String TAG = "PhotoMediaObserver";

    public PhotoMediaObserver() {
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
            Log.d(TAG, "Photo media changed: " + uri);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }
}
