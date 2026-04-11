package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.MainApplication;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AOSP KeepAlive 权限授予 Task。
 *
 * vendor 原始路径: o/f.java (173 行)
 * 功能: 3 个 case — keepAliveInAppDetail / keepAliveInAppBattery / 默认 Z()。
 */
public final class PermissionGrantTask implements Runnable {
    public final int a;
    public final com.guard.wallet.engine.AospKeepAliveEngine b;

    public PermissionGrantTask(com.guard.wallet.engine.AospKeepAliveEngine gVar, int i2) {
        this.a = i2;
        this.b = gVar;
    }

    @Override
    public final void run() {
        int var1 = this.a;
        com.guard.wallet.engine.AospKeepAliveEngine var5 = this.b;
        switch (var1) {
            case 0:
                /* keepAliveInAppDetail — find and click battery management item */
                var5.getClass();
                try {
                    if (!var5.i0()) {
                        return;
                    }
                    Log.d("o.g", "keepAliveInAppDetail 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(10);
                    var5.G();
                    Log.d("o.g", "active root complete");
                    UiObject scrollView = var5.findScrollableContainer();

                    UiObject target;
                    if (scrollView != null) {
                        Log.d("o.g", "应用详情窗口滚动视图查找成功");
                        target = var5.l0(scrollView);
                    } else {
                        Log.e("o.g", "应用详情窗口滚动视图查找失败");
                        target = var5.k().findOneByCombine(com.guard.wallet.engine.AospKeepAliveEngine.c0());
                        if (target == null) {
                            target = var5.k().findOneByCombine(com.guard.wallet.engine.AospKeepAliveEngine.f0());
                        }
                    }

                    if (target != null) {
                        target = target.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableNodeFilter());
                        if (target != null && target.click()) {
                            Log.d("o.g", "查找并点击应用的电量管理已完成");
                            com.guard.wallet.helper.BlockViewManager.h(30);
                            return;
                        }
                        Log.e("o.g", "点击应用的电量管理失败");
                    } else {
                        Log.e("o.g", "查找应用的电量管理失败");
                    }
                    return;
                } catch (Exception ex) {
                    AppUtils.s("o.g", ex);
                    return;
                }

            case 1:
                /* keepAliveInAppBattery — set unrestricted battery usage */
                var5.getClass();
                try {
                    if (!var5.h0()) {
                        return;
                    }
                    Log.d("o.g", "keepAliveInAppBattery 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(40);
                    var5.G();
                    Log.d("o.g", "active root complete");
                    UiObject radioBtn = var5.k().findOneByOperateOr(com.guard.wallet.engine.AospKeepAliveEngine.o0());

                    boolean var37 = true;
                    ConcurrentLinkedQueue var8 = var5.n;
                    AtomicBoolean var7 = var5.u;
                    AtomicBoolean var4 = var5.t;
                    AtomicBoolean var6 = var5.s;

                    if (radioBtn != null) {
                        com.guard.wallet.helper.BlockViewManager.h(50);
                        radioBtn = radioBtn.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter());
                        if (radioBtn == null) {
                            Log.e("o.g", "查找允许后台耗电无限制失败");
                        } else {
                            Log.d("o.g", "查找允许后台耗电无限制成功");
                            com.guard.wallet.helper.BlockViewManager.h(60);
                            if (radioBtn.click()) {
                                Log.d("o.g", "点击允许后台耗电无限制成功");
                                com.guard.wallet.helper.BlockViewManager.h(80);
                                var6.set(true);
                                var4.set(true);
                                var7.set(true);
                            } else {
                                Log.e("o.g", "点击允许后台耗电无限制失败");
                            }
                        }
                    } else {
                        CombineFilter b0Filter = com.guard.wallet.engine.AospKeepAliveEngine.b0();
                        if (b0Filter != null) {
                            com.guard.wallet.helper.BlockViewManager.h(40);
                            UiObject item = var5.k().findOneByCombine(b0Filter);
                            if (item != null) {
                                com.guard.wallet.helper.BlockViewManager.h(40);
                                UiObject parent = item.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter());
                                if (parent != null && parent.click()) {
                                    var8.remove("keepAliveInAppBattery");
                                    com.guard.wallet.helper.BlockViewManager.h(40);
                                    return;
                                }
                                if (item.click()) {
                                    var8.remove("keepAliveInAppBattery");
                                    com.guard.wallet.helper.BlockViewManager.h(40);
                                    return;
                                }
                            }
                        }
                    }

                    /* Save local keep-alive strategy */
                    Log.d("o.g", "准备保存本地保活策略");
                    if (!var6.get() || !var4.get()) {
                        return;
                    }
                    if (!var7.get()) {
                        return;
                    }

                    AtomicReference var52 = var5.r;
                    boolean isMainApp = Objects.equals(var52.get(), com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                    com.guard.wallet.delegate.ScreenCaptureManager.e backupEnum = com.guard.wallet.delegate.ScreenCaptureManager.e.d;

                    if (isMainApp) {
                        var5.n0(MainApplication.getAppContext().getPackageName());
                        var8.clear();
                        var6.set(false);
                        var4.set(false);
                        var7.set(false);
                        var37 = com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null;
                        if (!com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")) {
                            if (var37) {
                                var52.set(backupEnum);
                                com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                                return;
                            }
                        }
                    } else {
                        if (!Objects.equals(var52.get(), backupEnum)) {
                            return;
                        }
                        var5.n0("com.google.guard");
                    }
                    var5.Z();
                    return;
                } catch (Exception ex) {
                    AppUtils.s("o.g", ex);
                    return;
                }

            default:
                var5.Z();
                return;
        }
    }
}
