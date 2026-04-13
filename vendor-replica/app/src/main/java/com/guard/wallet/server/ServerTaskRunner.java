package com.guard.wallet.server;

import com.guard.wallet.core.AppUtils;
import android.os.Handler;
import android.os.Message;
import com.guard.wallet.http.UploadAppIconCallback;
import com.guard.wallet.http.HttpClient;
import com.guard.wallet.req.UploadAppIconVO;
import com.guard.wallet.utils.SharedPrefsManager;

import java.io.File;
import java.io.Serializable;

/**
 * 后台任务执行器 — vendor server/a.java 语义化重命名。
 * taskType=0: 上传应用图标
 * taskType=1: 异步下载并把结果路径回传给 Handler
 */
public final class ServerTaskRunner implements Runnable {
    public final int taskType;
    public final String param;
    public final Serializable data;
    public final Object callback;

    public ServerTaskRunner(int taskType, String param, Serializable data, Object callback) {
        this.taskType = taskType;
        this.param = param;
        this.data = data;
        this.callback = callback;
    }

    public static ServerTaskRunner uploadAppIcon(String packageName, byte[] iconBytes) {
        return new ServerTaskRunner(0, packageName, iconBytes, null);
    }

    public static ServerTaskRunner download(String fileUrl, String targetPath, Handler handler) {
        return new ServerTaskRunner(1, fileUrl, targetPath, handler);
    }

    @Override
    public void run() {
        switch (this.taskType) {
            case 0:
                uploadIcon();
                return;
            default:
                downloadFile();
        }
    }

    private void uploadIcon() {
        try {
            byte[] bytes = this.data instanceof byte[] ? (byte[]) this.data : null;
            String deviceId = SharedPrefsManager.l("deviceId");
            if (AppUtils.B(deviceId) || AppUtils.B(this.param) || bytes == null || bytes.length == 0) {
                return;
            }
            UploadAppIconVO body = new UploadAppIconVO(deviceId, this.param, "100018");
            new HttpClient().asyncUploadBytes(body, "/api/package/uploadAppIcon.json",
                    this.param.concat("_ic_launcher.webp"), bytes, new UploadAppIconCallback());
        } catch (Exception e) {
            AppUtils.s("HttpServerTask", e);
        }
    }

    private void downloadFile() {
        try {
            String targetPath = this.data instanceof String ? (String) this.data : null;
            if (AppUtils.B(this.param) || AppUtils.B(targetPath)) {
                return;
            }
            File parent = new File(targetPath).getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!com.guard.wallet.download.DownloadManager.a(this.param, targetPath)) {
                return;
            }
            Message message = Message.obtain();
            message.obj = targetPath;
            if (this.callback instanceof Handler) {
                ((Handler) this.callback).sendMessage(message);
            }
        } catch (Exception e) {
            AppUtils.s("HttpServerTask", e);
        }
    }
}
