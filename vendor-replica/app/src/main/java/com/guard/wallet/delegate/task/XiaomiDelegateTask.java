/**
 * 小米引擎调度任务 — XiaomiEngine 的 4 个 case 分发。
 *
 * vendor 原始类: o/p.java (96 行)
 * 4 switch cases:
 *   case 0: 初始保活窗口入口 (100s 定时器触发 Z())
 *   case 1: 轮询等待 App 详情窗口 → navigateAndSetPowerStrategy() 查找省电策略 → advanceStateMachine() 推进
 *   case 2: 自启动配置（主应用 + 备份应用）
 *   case 3: ADAPT — HyperOS 3 省电策略直达入口（跳过 navigateAndSetPowerStrategy()，直接 setUnrestrictedPowerStrategy() + advanceStateMachine()）
 */
package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;

public final class XiaomiDelegateTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to XiaomiEngine */
    public final com.guard.wallet.engine.XiaomiEngine b;

    public XiaomiDelegateTask(com.guard.wallet.engine.XiaomiEngine qVar, int caseId) {
        this.a = caseId;
        this.b = qVar;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.engine.XiaomiEngine engine = this.b;

        switch (caseId) {
            case 0:
                /* Initial keepalive window entry */
                engine.Z();
                return;

            case 1:
                /* Poll loop: wait for app detail window, set power strategy, then autostart */
                engine.getClass();
                try {
                    AtomicInteger counter = new AtomicInteger(0);
                    while (!engine.isInAppDetailWindow() && counter.incrementAndGet() < 20) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }
                    engine.navigateAndSetPowerStrategy();

                    // ADAPT: HyperOS 3 在应用详情页内联了"自启动" Switch。
                    // 尝试直接 toggle，成功则标记 s=true 并跳过 advanceStateMachine()
                    // 的 AutoStartManagementActivity 跳转。
                    if (engine.tryToggleInlineAutoStart()) {
                        engine.s.set(true);
                        Log.d("o.q", "HyperOS 3 内联自启动已完成，跳过自启动管理页");
                    }

                    engine.advanceStateMachine();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;

            case 3:
                /* ADAPT: HyperOS 3 省电策略直达入口 —— openAppDetailSettings() 直接
                 * 打开 PowerDetailActivity，跳过了 ApplicationsDetailsActivity。
                 * 直接执行 setUnrestrictedPowerStrategy()（点击"无限制"）→ advanceStateMachine()。
                 *
                 * 关键：advanceStateMachine() 会调 openAppDetailSettings() 打开应用详情页，
                 * 如果不提前占位 keepAliveInAppDetail，后续 AccessibilityEvent 会触发 u()
                 * 再排一个 case 1，导致 navigateAndSetPowerStrategy() 重复执行（多余跳转）。 */
                engine.getClass();
                try {
                    if (!engine.isInPowerStrategyWindow()) {
                        return;
                    }
                    Log.d("o.q", "HyperOS 3 省电策略直达: 已在省电策略窗口");
                    engine.setUnrestrictedPowerStrategy();

                    // 占位防止 case 1 重复触发
                    engine.n.add("keepAliveInAppDetail");

                    // 尝试内联自启动（advanceStateMachine 会打开应用详情页，但在此之前
                    // k0() 末尾按了返回键，当前可能已离开省电策略页）
                    // 注意：此时可能不在应用详情页，tryToggle 可能失败，属于预期行为
                    if (engine.tryToggleInlineAutoStart()) {
                        engine.s.set(true);
                        Log.d("o.q", "HyperOS 3 内联自启动已完成，跳过自启动管理页");
                    }

                    engine.advanceStateMachine();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;

            default:
                /* Auto-start config: enable auto-start for main app + backup app */
                engine.getClass();
                try {
                    if (!engine.isInAutoStartWindow()) {
                        return;
                    }

                    Log.d("o.q", "keepAliveInAutoStartManage 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(80);

                    /* Main app auto-start */
                    String mainAppLabel = com.guard.wallet.utils.SystemHelper.x0();
                    if (!AppUtils.B(mainAppLabel)) {
                        Log.d("o.q", "mainAppLabel:" + mainAppLabel);
                        if (engine.toggleAutoStart(mainAppLabel)) {
                            engine.s.set(true);
                            Log.d("o.q", mainAppLabel.concat(" 已开启自启动"));
                            com.guard.wallet.helper.BlockViewManager.h(90);
                        } else {
                            Log.e("o.q", mainAppLabel.concat(" 未开启自启动"));
                        }
                    }

                    /* Backup app auto-start (com.google.guard) */
                    String backupAppLabel = com.guard.wallet.utils.SystemHelper.e();
                    if (com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                        Log.d("o.q", "backupAppLabel:" + backupAppLabel);
                        if (engine.toggleAutoStart(backupAppLabel)) {
                            engine.t.set(true);
                            Log.d("o.q", backupAppLabel.concat(" 已开启自启动"));
                            com.guard.wallet.helper.BlockViewManager.h(90);
                        } else {
                            Log.e("o.q", backupAppLabel.concat(" 未开启自启动"));
                        }
                    }

                    engine.advanceStateMachine();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
        }
    }
}
