package com.vendor.rat.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 无障碍服务 (模块 02 核心)
 *
 * 功能:
 *   - 监听所有窗口事件
 *   - 分发事件到 EngineManager
 *   - 提供 rootNode 访问
 *   - 支持暂停/恢复
 */
public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyA11yService";
    private static final AtomicReference<MyAccessibilityService> instanceRef =
        new AtomicReference<>(null);
    private static final AtomicBoolean paused = new AtomicBoolean(false);

    private EngineManager engineManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instanceRef.set(this);
        engineManager = new EngineManager(this);
        Log.i(TAG, "Accessibility service created");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (paused.get()) return;
        if (event == null) return;

        int eventType = event.getEventType();
        String packageName = event.getPackageName() != null
            ? event.getPackageName().toString() : "";
        String className = event.getClassName() != null
            ? event.getClassName().toString() : "";

        // 分发事件到引擎管理器
        if (engineManager != null) {
            engineManager.dispatchEvent(packageName, className, event);
        }
    }

    @Override
    public void onInterrupt() {
        Log.w(TAG, "Accessibility service interrupted");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instanceRef.set(null);
        if (engineManager != null) {
            engineManager.unregisterAll();
        }
        Log.i(TAG, "Accessibility service destroyed");
    }

    /**
     * 获取当前窗口根节点
     */
    public AccessibilityNodeInfo getRootNode() {
        try {
            return getRootInActiveWindow();
        } catch (Exception e) {
            Log.w(TAG, "Failed to get root node", e);
            return null;
        }
    }

    // ============ 暂停/恢复 ============

    public void pauseProxy() { paused.set(true); }
    public void resumeProxy() { paused.set(false); }
    public static void setPaused(boolean value) { paused.set(value); }

    // ============ 单例访问 ============

    public static MyAccessibilityService getInstance() {
        return instanceRef.get();
    }

    public EngineManager getEngineManager() { return engineManager; }
}
