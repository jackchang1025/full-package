package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;

/**
 * 华为引擎锁屏确认 Task。
 *
 * vendor 原始路径: o/m.java (252 行)
 * 功能: 5 个 case — keepAliveInHwSettings / keepAliveInAppAndNotification /
 *       toggleStartupSwitches() / keepAliveInAlertDialog / 默认 Z()。
 */
public final class ConfirmLockTask implements Runnable {
    public final int a;
    public final com.guard.wallet.engine.HuaweiEngine b;

    public ConfirmLockTask(com.guard.wallet.engine.HuaweiEngine nVar, int i2) {
        this.a = i2;
        this.b = nVar;
    }

    @Override
    public final void run() {
        int var1 = this.a;
        boolean var4 = false;
        com.guard.wallet.engine.HuaweiEngine var6 = this.b;
        switch (var1) {
            case 0:
                /* keepAliveInHwSettings — find and click app&services item */
                var6.getClass();
                try {
                    if (!var6.isInHwSettings()) {
                        return;
                    }
                    Log.d("o.n", "keepAliveInHwSettings 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(10);
                    var6.G();
                    Log.d("o.n", "active root complete");
                    UiObject scrollView = var6.findScrollableContainer();

                    String errorMsg;
                    if (scrollView != null) {
                        Log.d("o.n", "查找华为系统设置滚动视图成功");
                        com.guard.wallet.helper.BlockViewManager.h(15);
                        UiObject found = FilterHelper.scrollForwardUtilFilter(scrollView, com.guard.wallet.engine.HuaweiEngine.buildAppAndNotificationFilter());
                        if (found == null) {
                            found = FilterHelper.scrollBackwardUtilFilter(scrollView, com.guard.wallet.engine.HuaweiEngine.buildAppAndNotificationFilter());
                        }
                        if (found == null) {
                            found = FilterHelper.scrollForwardUtilFilter(scrollView, com.guard.wallet.engine.HuaweiEngine.buildAppAndNotificationFilter());
                        }
                        if (found != null) {
                            com.guard.wallet.helper.BlockViewManager.h(20);
                            UiObject parent = found.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter());
                            if (parent != null && parent.click()) {
                                Log.d("o.n", "已点击进入应用和服务栏目");
                                com.guard.wallet.helper.BlockViewManager.h(25);
                                return;
                            }
                            errorMsg = "点击进入应用和服务栏目失败";
                        } else {
                            errorMsg = "查找应用和服务栏目栏目失败";
                        }
                    } else {
                        errorMsg = "查找华为系统设置滚动视图失败";
                    }
                    Log.e("o.n", errorMsg);
                    return;
                } catch (Exception ex) {
                    AppUtils.s("o.n", ex);
                    return;
                }

            case 1:
                /* keepAliveInAppAndNotification — find and click app startup management */
                var6.getClass();
                try {
                    if (!var6.isInAppAndNotification()) {
                        return;
                    }
                    Log.d("o.n", "keepAliveInAppAndNotification 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(30);
                    var6.G();
                    Log.d("o.n", "active root complete");
                    UiObject scrollView2 = var6.findScrollableContainer();

                    UiObject found2;
                    if (scrollView2 != null) {
                        Log.d("o.n", "应用和服务窗口滚动视图查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(35);
                        found2 = scrollView2.scrollForwardUtil(FilterHelper.createSingleScrollCondition(com.guard.wallet.engine.HuaweiEngine.buildStartupManageFilter()));
                        if (found2 == null) {
                            found2 = scrollView2.scrollBackwardUtil(FilterHelper.createSingleScrollCondition(com.guard.wallet.engine.HuaweiEngine.buildStartupManageFilter()));
                        }
                    } else {
                        Log.e("o.n", "应用和服务窗口滚动视图查找失败");
                        found2 = var6.k().findOneByCombine(com.guard.wallet.engine.HuaweiEngine.buildStartupManageFilter());
                    }

                    String errorMsg2;
                    if (found2 != null) {
                        Log.d("o.n", "应用启动管理栏目查找成功");
                        com.guard.wallet.helper.BlockViewManager.h(40);
                        found2 = found2.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildClickableLinearLayoutFilter());
                        if (found2 != null && found2.click()) {
                            Log.d("o.n", "点击应用启动管理栏目完成");
                            com.guard.wallet.helper.BlockViewManager.h(45);
                            return;
                        }
                        errorMsg2 = "点击应用启动管理栏目失败";
                    } else {
                        errorMsg2 = "应用启动管理栏目查找失败";
                    }
                    Log.e("o.n", errorMsg2);
                    return;
                } catch (Exception ex) {
                    AppUtils.s("o.n", ex);
                    return;
                }

            case 2:
                /* toggleStartupSwitches() — intermediate step */
                var6.toggleStartupSwitches();
                return;

            case 3:
                /* keepAliveInAlertDialog — toggle self-start, associate-start, background activity */
                var6.getClass();
                try {
                    if (!var6.isInAlertDialog()) {
                        return;
                    }
                    Log.d("o.n", "keepAliveInAlertDialog 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(70);
                    var6.G();
                    Log.d("o.n", "active root complete");

                    /* Self-start toggle */
                    UiObject selfStartNode = var6.k().findOneByCombine(com.guard.wallet.engine.HuaweiEngine.buildAutoStartFilter());
                    boolean selfStartChecked;
                    checkSelfStart: {
                        if (selfStartNode != null) {
                            Log.d("o.n", "自启动节点查找成功");
                            selfStartNode = selfStartNode.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildLinearLayoutFilter());
                            if (selfStartNode != null) {
                                Log.d("o.n", "自启动栏目查找成功");
                                CheckedResult result = var6.toggleSwitchWithRetry(selfStartNode, 5);
                                if (result.isClicked()) {
                                    Log.d("o.n", "已点击允许自启动");
                                }
                                if (result.isChecked()) {
                                    Log.d("o.n", "已勾选允许自启动");
                                    com.guard.wallet.helper.BlockViewManager.h(75);
                                    selfStartChecked = true;
                                    break checkSelfStart;
                                }
                                Log.e("o.n", "未勾选允许自启动");
                            } else {
                                Log.e("o.n", "自启动栏目查找失败");
                            }
                        } else {
                            Log.e("o.n", "自启动节点查找失败");
                        }
                        selfStartChecked = false;
                    }

                    /* Associate-start toggle */
                    UiObject assocNode = var6.k().findOneByCombine(com.guard.wallet.engine.HuaweiEngine.buildRelateStartFilter());
                    boolean assocStartChecked;
                    checkAssoc: {
                        if (assocNode != null) {
                            Log.d("o.n", "关联启动节点查找成功");
                            assocNode = assocNode.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildLinearLayoutFilter());
                            if (assocNode != null) {
                                Log.d("o.n", "关联启动栏目查找成功");
                                CheckedResult result2 = var6.toggleSwitchWithRetry(assocNode, 5);
                                if (result2.isClicked()) {
                                    Log.d("o.n", "已点击允许关联启动");
                                }
                                if (result2.isChecked()) {
                                    Log.d("o.n", "已勾选允许关联启动");
                                    com.guard.wallet.helper.BlockViewManager.h(80);
                                    assocStartChecked = true;
                                    break checkAssoc;
                                }
                                Log.e("o.n", "未勾选允许关联启动");
                            } else {
                                Log.e("o.n", "关联启动栏目查找失败");
                            }
                        } else {
                            Log.e("o.n", "关联启动节点查找失败");
                        }
                        assocStartChecked = false;
                    }

                    /* Background activity toggle */
                    UiObject bgNode = var6.k().findOneByCombine(com.guard.wallet.engine.HuaweiEngine.buildBackgroundActivityFilter());
                    checkBg: {
                        if (bgNode != null) {
                            Log.d("o.n", "允许后台活动节点查找成功");
                            bgNode = bgNode.findParentUtilCombine(com.guard.wallet.engine.KeepAliveEngine.buildLinearLayoutFilter());
                            if (bgNode != null) {
                                Log.d("o.n", "允许后台活动栏目查找成功");
                                CheckedResult result3 = var6.toggleSwitchWithRetry(bgNode, 5);
                                if (result3.isClicked()) {
                                    Log.d("o.n", "已点击允许后台活动");
                                }
                                if (result3.isChecked()) {
                                    Log.d("o.n", "已勾选允许后台活动");
                                    com.guard.wallet.helper.BlockViewManager.h(85);
                                    var4 = true;
                                    break checkBg;
                                }
                                Log.e("o.n", "未勾选允许后台活动");
                            } else {
                                Log.e("o.n", "允许后台活动栏目查找失败");
                            }
                        } else {
                            Log.e("o.n", "允许后台活动节点查找失败");
                        }
                    }

                    /* Click confirm button and update strategy */
                    UiObject confirmBtn = var6.k().findOneByCombine(com.guard.wallet.engine.HuaweiEngine.buildConfirmButtonFilter());
                    if (confirmBtn != null && confirmBtn.click()) {
                        Log.d("o.n", "查找并点击确认按钮完成");
                        com.guard.wallet.helper.BlockViewManager.h(90);
                        String strategyMsg;
                        if (Objects.equals(var6.r.get(), com.guard.wallet.delegate.ScreenCaptureManager.e.c)) {
                            var6.s.set(selfStartChecked);
                            var6.u.set(assocStartChecked);
                            var6.w.set(var4);
                            strategyMsg = "更新主进程保活策略";
                        } else {
                            var6.t.set(selfStartChecked);
                            var6.v.set(assocStartChecked);
                            var6.x.set(var4);
                            strategyMsg = "更新备用进程保活策略";
                        }
                        Log.d("o.n", strategyMsg);
                        return;
                    }
                    Log.e("o.n", "查找并点击确认按钮失败");
                    return;
                } catch (Exception ex) {
                    AppUtils.s("o.n", ex);
                    return;
                }

            default:
                var6.Z();
                return;
        }
    }
}
