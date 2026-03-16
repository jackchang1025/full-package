package com.vendor.rat.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 引擎管理器 (模块 02)
 *
 * 管理所有自动化引擎的注册、分发和生命周期
 */
public class EngineManager {

    private static final String TAG = "EngineManager";

    private final MyAccessibilityService service;
    private final List<AutoEngine> engines = new ArrayList<>();

    public EngineManager(MyAccessibilityService service) {
        this.service = service;
    }

    /**
     * 注册引擎
     */
    public void register(AutoEngine engine) {
        if (!engines.contains(engine)) {
            engines.add(engine);
            Log.d(TAG, "Engine registered: " + engine.getClass().getSimpleName());
        }
    }

    /**
     * 取消注册引擎
     */
    public void unregister(AutoEngine engine) {
        engines.remove(engine);
    }

    /**
     * 取消所有引擎
     */
    public void unregisterAll() {
        engines.clear();
        Log.d(TAG, "All engines unregistered");
    }

    /**
     * 分发事件到匹配的引擎
     */
    public void dispatchEvent(String packageName, String className,
                              AccessibilityEvent event) {
        for (AutoEngine engine : engines) {
            try {
                if (engine.matchWindow(packageName, className)) {
                    engine.onWindowMatched(packageName, className, event);
                }
            } catch (Exception e) {
                Log.e(TAG, "Engine dispatch error: "
                    + engine.getClass().getSimpleName(), e);
            }
        }
    }

    public MyAccessibilityService getService() { return service; }
    public List<AutoEngine> getEngines() { return engines; }
}
