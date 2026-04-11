package com.guard.wallet.delegate.task;

import java.util.Objects;

/**
 * AutoEngine dispatcher Runnable.
 *
 * vendor 原始路径: o/z.java (46 行)
 * 功能: 两种分发 — case 0 调用 D0()，default 在 PAIR_DEPT_UNKNOWN 状态下刷新根节点。
 */
public final class AutoEngineTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to PairAccessibilityDelegate */
    public final com.guard.wallet.delegate.PairAccessibilityDelegate b;

    public AutoEngineTask(com.guard.wallet.delegate.PairAccessibilityDelegate a0Var, int caseId) {
        this.a = caseId;
        this.b = a0Var;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.delegate.PairAccessibilityDelegate engine = this.b;

        switch (caseId) {
            case 0:
                engine.D0();
                return;
            default:
                /* Only refresh when pair state is PAIR_DEPT_UNKNOWN (com.guard.wallet.delegate.ScreenCaptureManager.g.b) */
                if (Objects.equals(engine.p.get(), com.guard.wallet.delegate.ScreenCaptureManager.g.b)) {
                    if (com.guard.wallet.utils.SystemHelper.F0(3)) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    if (com.guard.wallet.utils.SystemHelper.F0(1)) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    if (engine.k() != null) {
                        engine.k().refresh();
                    }
                }
        }
    }
}
