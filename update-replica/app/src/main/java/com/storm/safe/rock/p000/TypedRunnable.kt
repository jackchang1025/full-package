package com.storm.safe.rock.p000

import android.util.Log

/**
 * JADX: p000/RunnableC1052p1.java (429 LOC) — Typed parameter Runnable.
 *
 * Similar to TaskRunnable but with two target objects. The run() method dispatches
 * based on a type int, with each case casting the two target objects to specific types.
 *
 * Fields:
 * - f59134a0 → type: int selector
 * - f59135a1 → target1: first object parameter
 * - f59136a2 → target2: second object parameter
 *
 * Key cases referenced from rock/ code:
 * - type=0  → ActivityMonitor file logging
 * - type=1  → Callback invocation with list result
 * - type=2  → AutoEngine init callback
 * - type=3  → Executor run + cleanup
 * - type=4  → UI hide + delayed lambda
 * - type=5  → ConfigProgressManager broadcast
 * - type=7  → ConstraintTrackingWorker result handling
 * - type=9  → Huawei permission activity launch
 * - type=12 → SystemOptimize timeout
 * - type=17 → Accessibility popup delayed click
 * - type=18 → Full-screen notification for device confirm
 * - type=19 → Screen state password handling
 * - type=21 → Service command dispatch
 * - default → RecentsGuardManager protection check
 */
class TypedRunnable : Runnable {

    /** JADX: f59134a0 — Task type selector */
    val type: Int

    /** JADX: f59135a1 — First target object */
    val target1: Any

    /** JADX: f59136a2 — Second target object */
    val target2: Any

    /**
     * Primary constructor — JADX: RunnableC1052p1(Object, int, Object)
     */
    constructor(target1: Any, type: Int, target2: Any) {
        this.type = type
        this.target1 = target1
        this.target2 = target2
    }

    override fun run() {
        try {
            when (type) {
                0 -> {
                    // vendor: type=0 — ActivityMonitor file logging
                    // JADX: Writes log to external storage file with rotation
                }
                1 -> {
                    // vendor: type=1 — Callback invocation with list result
                    // JADX: h10Var.invoke(list)
                }
                2 -> {
                    // vendor: type=2 — AutoEngine init callback
                    // JADX: c0032al.m209811a0(dqtvuisjd)
                }
                3 -> {
                    // vendor: type=3 — SerialExecutor run with cleanup
                    // JADX: runnable.run() then executorC0034an.m209823a0()
                }
                4 -> {
                    // vendor: type=4 — UI overlay hide + delayed lambda
                    // JADX: relativeLayout.setVisibility(GONE), postDelayed
                }
                5 -> {
                    // vendor: type=5 — ConfigProgressManager sendBroadcast
                    // JADX: c0318a3.f53045a0.sendBroadcast(intent)
                }
                6 -> {
                    // vendor: type=6 — LiveData observer list notification
                }
                7 -> {
                    // vendor: type=7 — ConstraintTrackingWorker result set
                    // JADX: Handles constraint stopped vs normal completion
                }
                8 -> {
                    // vendor: type=8 — DatePicker error formatting
                }
                9 -> {
                    // vendor: type=9 — Huawei permission activity launch
                    // JADX: startActivity(umrkmgrri.class), countDownLatch.countDown()
                }
                10 -> {
                    // vendor: type=10 — Animation observer dispatch
                }
                11 -> {
                    // vendor: type=11 — EmojiCompat typeface callback
                }
                12 -> {
                    // vendor: type=12 — SystemOptimize 180s force timeout
                    // JADX: atomicBoolean.set(false), callback.invoke()
                }
                13 -> {
                    // vendor: type=13 — Upload debug port to server
                }
                14 -> {
                    // vendor: type=14 — View tag stamping for transitions
                }
                15 -> {
                    // vendor: type=15 — WorkerFactory future cancellation
                }
                16 -> {
                    // vendor: type=16 — Worker future cancellation (alternate)
                }
                17 -> {
                    // vendor: type=17 — Accessibility popup delayed click
                    // JADX: c0372a9.m212449a5(rootNode, buttonText)
                }
                18 -> {
                    // vendor: type=18 — Full-screen notification for device confirmation
                    // JADX: moveToFront fallback with notification channel + PendingIntent
                }
                19 -> {
                    // vendor: type=19 — Screen state password type handling
                    // JADX: dqtvuisjdVar.m211521l8(passwordType)
                }
                20 -> {
                    // vendor: type=20 — Permission UI status text update
                    // JADX: iuzxujjtqev statusText update with orange color
                }
                21 -> {
                    // vendor: type=21 — Service command dispatch
                    // JADX: dqtvuisjdVar.m211448d3(jbqfkndyx.f51949a1)
                }
                else -> {
                    // vendor: default — RecentsGuardManager protection check
                    // JADX: C0356a1 accessibility node inspection for recent tasks
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "TypedRunnable type=$type failed", e)
        }
    }

    companion object {
        private const val TAG = "TypedRunnable"
    }
}
