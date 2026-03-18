package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 文件操作处理器 — vendor b.java 文件路由
 *
 * 覆盖路由: /syncDownload, /asyncDownload,
 *   /deleteFile, /uploadAppIcon, /reloadAgentFile
 */
public class FileOperationHandler {

    private static final String TAG = "FileOperationHandler";

    /** /syncDownload — vendor m3() */
    public void syncDownload(String filepath, String fileUrl,
            boolean saveToGallery) {
        Log.d(TAG, "syncDownload: " + fileUrl);
    }

    /** /asyncDownload — vendor e() */
    public void asyncDownload(String filepath, String fileUrl,
            boolean saveToGallery) {
        Log.d(TAG, "asyncDownload: " + fileUrl);
    }

    /** /deleteFile — vendor y() */
    public void deleteFile(String filePathAndName, String galleryUrl) {
        Log.d(TAG, "deleteFile: " + filePathAndName);
    }

    /** /uploadAppIcon */
    public void uploadAppIcon() {
        Log.d(TAG, "uploadAppIcon");
    }

    /** /reloadAgentFile */
    public void reloadAgentFile() {
        Log.d(TAG, "reloadAgentFile");
    }
}
