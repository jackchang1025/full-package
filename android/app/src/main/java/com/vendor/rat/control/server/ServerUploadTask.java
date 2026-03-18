package com.vendor.rat.control.server;

import android.util.Log;
import java.io.Serializable;

/**
 * 服务端上传任务 (图标上传 / 图片解码)
 * vendor: com.guard.wallet.server.a
 *
 * 原始逻辑:
 *   case 0: 上传 app icon (byte[] → uploadAppIcon API)
 *   case 1: 解码图片路径 → 发送 Message 到 Handler
 */
public final class ServerUploadTask implements Runnable, Serializable {

    private static final String TAG = "ServerUploadTask";

    private final int taskType;
    private final Object target;
    private final String key;
    private final Serializable payload;

    public ServerUploadTask(Object target, String key, Serializable payload, int taskType) {
        this.taskType = taskType;
        this.target = target;
        this.key = key;
        this.payload = payload;
    }

    @Override
    public void run() {
        switch (this.taskType) {
            case 0:
                handleUploadIcon();
                break;
            default:
                handleDecodeImage();
                break;
        }
    }

    private void handleUploadIcon() {
        // vendor 逻辑: 获取 deviceId, 校验参数, 调用 uploadAppIcon API
        // ADAPT: 依赖 network 模块 HttpClient
        byte[] data = (byte[]) this.payload;
        if (this.key == null || data == null || data.length <= 0) {
            return;
        }
        Log.d(TAG, "Upload icon for: " + this.key);
        // TODO: VENDOR_VERIFY - 需要 HttpClient.uploadFile()
    }

    private void handleDecodeImage() {
        // vendor 逻辑: 下载图片 → 解码 Bitmap → 发送 Message
        try {
            Log.d(TAG, "Decode image: " + this.key);
            // TODO: VENDOR_VERIFY - 需要图片下载和 Handler 回调
        } catch (Exception e) {
            Log.e(TAG, "Decode image failed", e);
        }
    }
}
