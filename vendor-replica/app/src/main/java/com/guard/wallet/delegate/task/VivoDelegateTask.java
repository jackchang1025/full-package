/**
 * Vivo 引擎调度任务 — VivoEngine 的 8 个 case 分发。
 *
 * vendor 原始类: o/h0.java (354 行)
 * 8 switch cases (0-7):
 *   case 0: timeout -> Z()
 *   case 1: keepAliveInPowerRank
 *   case 2: keepAliveInExcessivePowerManager
 *   case 3: keepAliveInExcessivePowerDescription
 *   case 4: keepAliveInAppDetail
 *   case 5: keepAliveInAppPermissionManage -> t0()
 *   case 6: keepAliveInAppPermissionDetail
 *   default(7): keepAliveInPermissionAllowDialog
 */
package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.os.Build;
import android.util.Log;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class VivoDelegateTask implements Runnable {

    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    public final int a;
    public final com.guard.wallet.engine.VivoEngine b;

    public VivoDelegateTask(com.guard.wallet.engine.VivoEngine engine, int caseId) {
        this.a = caseId;
        this.b = engine;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.engine.VivoEngine engine = this.b;

        switch (caseId) {
            case 0:
                engine.Z();
                return;

            case 1:
                /* keepAliveInPowerRank — find and click background power manager entry */
                engine.getClass();
                try {
                    if (!engine.p0()) return;
                    Log.d("o.i0", "keepAliveInPowerRank 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(10);
                    engine.G();
                    Log.d("o.i0", "active root complete");

                    UiObject found = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.E0());
                    if (found == null) {
                        UiObject scrollView = engine.findScrollableContainer();
                        if (scrollView != null) {
                            scrollView.scrollBackwardEnd();
                            com.guard.wallet.utils.SystemHelper.T0(5);
                        }
                    }
                    if (found == null) {
                        found = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.E0());
                    }

                    if (found != null) {
                        Log.d("o.i0", "后台耗电管理栏目查找成功");
                        int sdkInt = Build.VERSION.SDK_INT;
                        AtomicReference stateRef = engine.s;
                        String successMsg;

                        if (sdkInt >= 35) {
                            UiObject parentClickable = found.findParentUtilCombine(
                                    EngineHelper.cL());
                            if (parentClickable != null && parentClickable.click()) {
                                successMsg = "后台耗电父节点点击成功";
                            } else if (found.click()) {
                                successMsg = "后台耗电管理栏目点击成功";
                            } else {
                                Log.e("o.i0", "后台耗电管理栏目点击失败");
                                return;
                            }
                        } else if (found.click()) {
                            successMsg = "后台耗电管理栏目点击成功";
                        } else {
                            Log.e("o.i0", "后台耗电管理栏目点击失败");
                            return;
                        }

                        Log.d("o.i0", successMsg);
                        com.guard.wallet.helper.BlockViewManager.h(15);
                        stateRef.set("prepareInExcessivePowerManager");
                    } else {
                        Log.e("o.i0", "后台耗电管理栏目查找失败");
                    }
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
                return;

            case 2:
                /* keepAliveInExcessivePowerManager — find app in power manager */
                engine.getClass();
                try {
                    if (!engine.n0()) return;
                    Log.d("o.i0", "keepAliveInExcessivePowerManager 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(20);
                    engine.G();
                    Log.d("o.i0", "active root complete");

                    AtomicReference keepAliveRef = engine.r;
                    String appLabel;
                    if (Objects.equals(keepAliveRef.get(), KEEP_ALIVE_UNKNOWN)) {
                        keepAliveRef.set(KEEP_ALIVE_MAIN);
                        appLabel = com.guard.wallet.utils.SystemHelper.x0();
                    } else if (Objects.equals(keepAliveRef.get(), KEEP_ALIVE_MAIN)
                            && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                        keepAliveRef.set(KEEP_ALIVE_BACKUP);
                        appLabel = com.guard.wallet.utils.SystemHelper.e();
                    } else {
                        keepAliveRef.set(KEEP_ALIVE_UNKNOWN);
                        engine.z0();
                        return;
                    }

                    UiObject scrollView = engine.findScrollableContainer();
                    AtomicInteger counter = new AtomicInteger(0);
                    while (scrollView == null && counter.incrementAndGet() <= 5) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                        scrollView = engine.findScrollableContainer();
                    }

                    UiObject found;
                    if (scrollView != null) {
                        if (scrollView.canScrollBackward()) {
                            scrollView.scrollBackwardEnd();
                            com.guard.wallet.utils.SystemHelper.T0(5);
                        }
                        Log.d("o.i0", "应用耗电管理窗口滚动视图查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(25);
                        found = FilterHelper.scrollForwardUtil(scrollView,
                                EngineHelper.cH(appLabel), 0, 0);
                        if (found == null) {
                            found = FilterHelper.scrollBackwardUtilFilter(scrollView,
                                    EngineHelper.cH(appLabel));
                        }
                    } else {
                        Log.e("o.i0", "应用耗电管理窗口滚动视图查找失败");
                        found = null;
                    }
                    if (found == null) {
                        found = engine.k().findOneByCombine(EngineHelper.cH(appLabel));
                    }
                    if (found == null) {
                        Log.e("o.i0", appLabel.concat(" App栏目查找失败"));
                        appLabel.concat(" App栏目查找失败");
                        return;
                    }

                    Log.d("o.i0", appLabel.concat(" App栏目查找成功"));
                    com.guard.wallet.helper.BlockViewManager.h(30);
                    appLabel.concat(" App栏目查找成功");
                    UiObject parentClickable = found.findParentUtilCombine(EngineHelper.cL());
                    AtomicReference stateRef = engine.s;
                    String clickMsg;
                    if (parentClickable != null && parentClickable.click()) {
                        clickMsg = appLabel.concat(" App栏目点击成功");
                    } else if (found.click()) {
                        clickMsg = appLabel.concat(" App栏目点击成功");
                    } else {
                        Log.e("o.i0", appLabel.concat(" App栏目点击失败"));
                        appLabel.concat(" App栏目点击失败");
                        return;
                    }
                    Log.d("o.i0", clickMsg);
                    com.guard.wallet.helper.BlockViewManager.h(35);
                    appLabel.concat(" App栏目点击成功");
                    stateRef.set("prepareInExcessivePowerDescription");
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
                return;

            case 3:
                /* keepAliveInExcessivePowerDescription — allow high background power */
                engine.getClass();
                try {
                    if (!engine.m0()) return;
                    Log.d("o.i0", "keepAliveInExcessivePowerDescription 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(40);
                    engine.G();
                    Log.d("o.i0", "active root complete");
                    if (engine.k() == null) return;

                    UiObject found = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.C0());
                    String msg;
                    if (found != null) {
                        Log.d("o.i0", "允许后台高耗电查找成功");
                        if (found.click()) {
                            Log.d("o.i0", "允许后台高耗电点击成功");
                            com.guard.wallet.helper.BlockViewManager.h(40);
                            AtomicBoolean flag = Objects.equals(engine.r.get(), KEEP_ALIVE_MAIN)
                                    ? engine.x : engine.y;
                            flag.set(true);
                            engine.s.set("prepareInExcessivePowerManager");
                            com.guard.wallet.utils.SystemHelper.F0(1);
                            return;
                        }
                        msg = "允许后台高耗电点击失败";
                    } else {
                        msg = "允许后台高耗电查找失败";
                    }
                    Log.d("o.i0", msg);
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
                return;

            case 4:
                /* keepAliveInAppDetail — find and click permissions entry */
                engine.getClass();
                try {
                    if (!engine.j0()) return;
                    Log.d("o.i0", "keepAliveInAppDetail 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(50);
                    engine.G();
                    Log.d("o.i0", "active root complete");

                    UiObject found = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.H0());
                    String errMsg;
                    if (found != null) {
                        Log.d("o.i0", "权限栏目查找完成");
                        UiObject parent = found.findParentUtilCombine(EngineHelper.cL());
                        if (parent != null && parent.click()) {
                            Log.d("o.i0", "查找并点击权限栏目完成");
                            com.guard.wallet.helper.BlockViewManager.h(55);
                            com.guard.wallet.utils.SystemHelper.T0(10);
                            engine.s.set("prepareInAppPermissionManage");
                            engine.t0();
                            return;
                        }
                        errMsg = "查找并点击权限栏目失败";
                    } else {
                        errMsg = "权限栏目查找失败";
                    }
                    Log.e("o.i0", errMsg);
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
                return;

            case 5:
                /* keepAliveInAppPermissionManage -> delegate to t0() */
                engine.t0();
                return;

            case 6:
                /* keepAliveInAppPermissionDetail — auto-start + popup toggles */
                engine.getClass();
                try {
                    if (!engine.k0()) return;
                    Log.d("o.i0", "keepAliveInAppPermissionDetail 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(60);
                    engine.G();
                    Log.d("o.i0", "active root complete");

                    UiObject scrollView = engine.findScrollableContainer();
                    AtomicInteger counter = new AtomicInteger(0);
                    while (scrollView == null && counter.incrementAndGet() <= 5) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                        scrollView = engine.findScrollableContainer();
                    }

                    if (scrollView != null && scrollView.canScrollBackward()) {
                        Log.d("o.i0", "App所有权限窗口滚动至顶部成功");
                        scrollView.scrollForwardEnd();
                        engine.k().refresh();
                    }

                    // Auto-start toggle
                    UiObject autoStartNode = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.i0());
                    AtomicReference keepAliveRef = engine.r;
                    if (autoStartNode != null) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                        Log.d("o.i0", "自启动栏目查找成功");
                        CheckedResult result = com.guard.wallet.engine.KeepAliveEngine.toggleSwitchByGesture(autoStartNode.parent());
                        if (result.isClicked() || result.isChecked()) {
                            Log.d("o.i0", "自启动栏目点击成功");
                            com.guard.wallet.helper.BlockViewManager.h(65);
                            AtomicBoolean flag = Objects.equals(keepAliveRef.get(), KEEP_ALIVE_MAIN)
                                    ? engine.t : engine.u;
                            flag.set(true);
                        } else {
                            Log.d("o.i0", "未勾选App自启动");
                        }
                    }

                    // Popup in background toggle
                    UiObject popupNode = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.w0());
                    if (popupNode != null) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                        Log.d("o.i0", "后台弹出界面栏目查找成功");
                        CheckedResult result2 = com.guard.wallet.engine.KeepAliveEngine.toggleSwitchByGesture(popupNode.parent());
                        if (result2.isClicked() || result2.isChecked()) {
                            Log.d("o.i0", "后台弹出界面栏目点击成功");
                            com.guard.wallet.helper.BlockViewManager.h(70);
                            AtomicBoolean flag = Objects.equals(keepAliveRef.get(), KEEP_ALIVE_MAIN)
                                    ? engine.z : engine.A;
                            flag.set(true);
                        } else {
                            Log.e("o.i0", "未勾选后台弹出界面");
                        }
                    }

                    engine.z0();
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
                return;

            default:
                /* keepAliveInPermissionAllowDialog — click allow button */
                engine.getClass();
                try {
                    if (!engine.o0()) return;
                    Log.d("o.i0", "keepAliveInPermissionAllowDialog 窗口匹配");
                    engine.G();
                    Log.d("o.i0", "active root complete");

                    UiObject allowBtn = engine.k().findOneByCombine(com.guard.wallet.engine.VivoEngine.b0());
                    if (allowBtn != null && allowBtn.click()) {
                        Log.d("o.i0", "查找并点击允许按钮完成");
                        com.guard.wallet.helper.BlockViewManager.h(80);
                        AtomicBoolean flag = Objects.equals(engine.r.get(), KEEP_ALIVE_MAIN)
                                ? engine.v : engine.w;
                        flag.set(true);
                    } else {
                        Log.e("o.i0", "查找并点击允许按钮失败");
                    }

                    engine.z0();
                } catch (Exception ex) {
                    AppUtils.s("o.i0", ex);
                }
        }
    }
}
