# Overlay Module Design — 统一遮罩模块

> **日期**: 2026-04-22
> **范围**: `service/modules/overlay/` 模块重建 + AudioStealthManager 提取
> **Vendor 源码**: C0763km (ConfigMaskManager), C0708j7 (MaskOverlay), RunnableC0707j6 (ProgressAnimator), dd0 (MaskConfig), yojggfhv (ActivityMask)
> **审计文档**: `docs/遮罩/遮罩与隐蔽机制审计.md`, `docs/遮罩/配置遮罩模块详细审计.md`

---

## 1. 问题陈述

当前遮罩系统有 5 个 GAP：

| GAP | 描述 | 影响 |
|-----|------|------|
| GAP 1 | ConfigMaskOverlay.kt 是 8 行空壳 | 自动化期间用户能看到全部操作 |
| GAP 2 | 静音逻辑只有恢复没有静音 | 设备不静音，点击声和振动暴露操作 |
| GAP 3 | 无 4 条件遮罩消失检查 | 遮罩可能过早/过迟消失 |
| GAP 4 | PairFlowOrchestrator 兜底关闭是空实现 | 遮罩可能永远不消失 |
| GAP 5 | 3 套遮罩实现重复（WindowManager + Activity + BlackScreen） | 维护困难，行为不一致 |

## 2. 设计决策

### 2.1 单一 WindowManager 实现

废弃 Activity 遮罩（yojggfhv），统一使用 WindowManager `TYPE_ACCESSIBILITY_OVERLAY`。

| 能力 | WindowManager 方案 |
|------|-------------------|
| 触摸穿透 | `FLAG_NOT_TOUCHABLE` 动态切换 |
| 触摸拦截 | 去掉 `FLAG_NOT_TOUCHABLE` |
| 禁止截屏 | `FLAG_SECURE` |
| 覆盖 Settings | `TYPE_ACCESSIBILITY_OVERLAY` Z-order 最高 |
| 自我保护 | 天然免疫 — 用户无法关掉 overlay |
| 屏蔽返回键 | 天然免疫 — overlay 不在导航栈 |

### 2.2 Config 驱动

客户端只需传入 `OverlayConfig` 数据类，模块内部处理所有 UI 构建、窗口管理、动画逻辑。

### 2.3 AudioStealthManager 独立提取

从 OpenDevelopmentDelegate 提取音频静音/恢复逻辑为独立工具类，补全缺失的 `muteAll()` 方法。

---

## 3. 模块结构

```
service/modules/overlay/
├── OverlayConfig.kt            # 配置数据类
├── OverlayManager.kt           # 门面：show/hide/update 生命周期管理
├── OverlayWindowView.kt        # UI 构建 + WindowManager 操作
├── OverlayProgressAnimator.kt  # 双模式进度动画
└── AudioStealthManager.kt      # 音频+触感 静音/恢复
```

### 职责边界

| 类 | 单一职责 | 依赖 |
|---|---------|------|
| `OverlayConfig` | 纯数据，描述外观+行为 | 无 |
| `OverlayManager` | 生命周期门面 | OverlayWindowView, OverlayProgressAnimator |
| `OverlayWindowView` | UI 构建 + WindowManager addView/removeView/updateLayout | OverlayConfig |
| `OverlayProgressAnimator` | 进度条时间线动画 + 提示文本轮播 | OverlayWindowView |
| `AudioStealthManager` | 独立工具：mute/restore 5流+铃声+触感 | 无（纯 Android API） |

### 不动的部分

- `ConfigProgressManager` — 保持独立，职责是阶段广播，与视觉动画无关
- `yojggfhv` — 标记 `@Deprecated`，暂不删除，后续清理

---

## 4. OverlayConfig 详细设计

```kotlin
data class OverlayConfig(
    val background: OverlayBackground = OverlayBackground.Image(),
    val touchMode: TouchMode = TouchMode.PASSTHROUGH,
    val preventScreenshot: Boolean = false,
    val showAppIcon: Boolean = true,
    val progressBar: ProgressBarStyle = ProgressBarStyle.GradientBlue(),
    val titleText: String = "配置中请稍后...",
    val subtitleText: String = "正在自动配置和连接\n请勿操作设备",
    val statusText: String = "配置完成后将自动返回应用",
    val titleColor: String = "#FFFFFF",
    val subtitleColor: String = "#CCCCCC",
    val loadingTips: List<String> = DEFAULT_TIPS,
    val keepScreenOn: Boolean = true,
)
```

### 4.1 TouchMode

```kotlin
enum class TouchMode {
    PASSTHROUGH,  // FLAG_NOT_TOUCHABLE — 自动化点击穿透
    INTERCEPT     // 消费触摸 — 阻止用户操作
}
```

### 4.2 OverlayBackground (密封类)

```kotlin
sealed interface OverlayBackground {
    data class SolidColor(
        val color: Int = Color.BLACK,
        val alpha: Float = 1f
    ) : OverlayBackground

    data class Image(
        val assetPaths: List<String> = listOf(
            "app_loading_bg.webp",
            "app_loading_bg.png"
        ),
        val fallbackDrawable: String? = "bg_config_mask",
        val fallbackColor: Int = Color.BLACK
    ) : OverlayBackground
}
```

背景加载优先级（Image 模式）：
1. 按 assetPaths 顺序尝试加载 assets 目录图片
2. 尝试加载 fallbackDrawable
3. 全部失败 → fallbackColor 纯色

### 4.3 ProgressBarStyle (密封类)

```kotlin
sealed interface ProgressBarStyle {
    data object None : ProgressBarStyle

    data class GradientBlue(
        val startColor: Int = 0xFF4A90D9.toInt(),
        val endColor: Int = 0xFF67B8F7.toInt(),
        val startFromMax: Boolean = false
    ) : ProgressBarStyle

    data class SystemOrange(
        val color: Int = 0xFFFF9800.toInt(),
        val startFromMax: Boolean = false
    ) : ProgressBarStyle
}
```

### 4.4 工厂方法

```kotlin
companion object {
    val DEFAULT_TIPS = listOf(
        "检查最优线路中", "正在连接服务器...",
        "正在加载资源...", "正在初始化配置...", "正在启动"
    )

    fun configMask() = OverlayConfig()

    fun blackScreen(
        text: String = "",
        alpha: Float = 0.99f,
        interceptTouch: Boolean = true
    ) = OverlayConfig(
        background = OverlayBackground.SolidColor(alpha = alpha),
        touchMode = if (interceptTouch) TouchMode.INTERCEPT else TouchMode.PASSTHROUGH,
        preventScreenshot = true,
        showAppIcon = false,
        progressBar = ProgressBarStyle.None,
        titleText = text,
        subtitleText = "",
        statusText = "",
        loadingTips = emptyList()
    )

    fun fromJson(json: JSONObject): OverlayConfig = ...
}
```

---

## 5. OverlayManager 详细设计

```kotlin
class OverlayManager(private val service: AccessibilityService) {

    private var windowView: OverlayWindowView? = null
    private var animator: OverlayProgressAnimator? = null
    private var currentConfig: OverlayConfig? = null
    @Volatile private var showing: Boolean = false
    private val mainHandler = Handler(Looper.getMainLooper())

    fun show(config: OverlayConfig = OverlayConfig.configMask())
    fun hide()
    fun isShowing(): Boolean
    fun updateProgress(percent: Int, message: String? = null)
    fun dispose()
}
```

### 行为规则

| 场景 | 行为 |
|------|------|
| `show()` 未显示时 | 构建 View → addView → 启动动画 |
| `show()` 已显示时 | 更新 config → updateViewLayout |
| `hide()` 未显示时 | no-op |
| `hide()` 已显示时 | 停止动画 → removeView → 释放引用 |
| addView 失败 | 指数退避重试 5 次（200→400→800→1600→3000ms） |
| 非主线程调用 | 自动 post 到主线程 |

### 线程安全

所有状态变更通过 mainHandler 串行化。`isShowing()` 读取 `@Volatile` 标志。

---

## 6. OverlayWindowView 详细设计

### 6.1 UI 层级

```
FrameLayout (根, 全屏, systemUiVisibility=5894 全沉浸)
│
├── ImageView (背景层, MATCH_PARENT, CENTER_CROP)
│
├── LinearLayout (中央内容, VERTICAL, Gravity.CENTER)
│   ├── [showAppIcon] ImageView (APP 图标, 80dp, 圆角 16dp)
│   ├── [showAppIcon] TextView (APP 名称, 18sp, 白色)
│   ├── [progressBar != None] FrameLayout (进度条容器, 65%屏幕宽 × 6dp)
│   │   ├── View (轨道, 0x33FFFFFF, 圆角)
│   │   └── View (指示器, 渐变/纯色, 宽度动态更新)
│   └── [loadingTips.isNotEmpty()] TextView (提示文本, 14sp)
│
└── [titleText/subtitleText 非空] LinearLayout (底部, Gravity.BOTTOM, marginBottom=60dp)
    ├── TextView (titleText, 16sp)
    ├── TextView (subtitleText, 12sp)
    └── TextView (statusText, 12sp, #AAAAAA)
```

### 6.2 WindowManager Flags

```kotlin
baseFlags = FLAG_NOT_FOCUSABLE or
    FLAG_LAYOUT_IN_SCREEN or FLAG_LAYOUT_NO_LIMITS or
    FLAG_FULLSCREEN or FLAG_HARDWARE_ACCELERATED or
    FLAG_KEEP_SCREEN_ON or FLAG_TURN_SCREEN_ON or
    FLAG_SHOW_WHEN_LOCKED or FLAG_DISMISS_KEYGUARD

// 动态 flags
if (config.touchMode == PASSTHROUGH) flags |= FLAG_NOT_TOUCHABLE
if (config.preventScreenshot) flags |= FLAG_SECURE
```

窗口类型：
- SDK >= 26: `TYPE_ACCESSIBILITY_OVERLAY` (2032)
- SDK < 26: `TYPE_PHONE` (2006)

屏幕尺寸获取：
- SDK >= 30: `WindowMetrics.getBounds()`
- SDK < 30: `Display.getRealMetrics()`

刘海屏兼容：
- SDK >= 28: `layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES`

### 6.3 addView 重试机制

指数退避：`delay = min(2^retryCount × 200, 3000)` ms，最多 5 次。

### 6.4 资源释放 (detachFromWindow)

1. 停止进度动画
2. ImageView.setImageDrawable(null) — 释放 bitmap
3. rootView.setBackground(null)
4. windowManager.removeView(rootView)
5. 清空所有 View 引用

---

## 7. OverlayProgressAnimator 详细设计

### 7.1 双模式动画

**模式 B (startFromMax = false, 首次配置):**

| 时间段 | 进度范围 | 更新间隔 | 公式 |
|--------|---------|---------|------|
| 0-30s | 0% → 80% | 1000ms | `clamp((elapsed / 30000.0) × 80, 0, 80)` |
| 30s+ | 80% → 95% | 3000ms | `clamp((elapsed - 30000) / 3000 + 80, 80, 95)` |

**模式 A (startFromMax = true, 恢复):**

| 时间段 | 进度范围 | 更新间隔 | 公式 |
|--------|---------|---------|------|
| 0-60s | 80% → 100% | 1000ms | `clamp((elapsed / 60000.0) × 20 + 80, 80, 100)` |

### 7.2 提示文本轮播

```
tipIndex = clamp(floor(progress / 100.0 × tipCount), 0, tipCount - 1)
```

仅当 `loadingTips.isNotEmpty()` 时更新文本。

### 7.3 外部推进

`forceProgress(percent, message)` 直接设置进度值和文本，跳过自动动画时间线。

---

## 8. AudioStealthManager 详细设计

```kotlin
class AudioStealthManager(private val context: Context) {

    private val savedVolumes = LinkedHashMap<Int, Int>()
    private var savedRingerMode: Int = AudioManager.RINGER_MODE_NORMAL
    private var savedHapticFeedback: Int = 1
    @Volatile var isActive: Boolean = false
        private set

    companion object {
        val STREAM_TYPES = listOf(2, 5, 1, 3, 4)
        // STREAM_VOICE_CALL, STREAM_NOTIFICATION, STREAM_RING, STREAM_MUSIC, STREAM_ALARM
    }

    fun muteAll()
    fun restoreAll()
    fun forceRestoreDefaults()
}
```

### 8.1 muteAll() 流程

```
1. for (type in STREAM_TYPES):
       savedVolumes[type] = audioManager.getStreamVolume(type)
       audioManager.setStreamVolume(type, 0, 0)
2. savedRingerMode = audioManager.ringerMode
   audioManager.ringerMode = RINGER_MODE_SILENT
3. savedHapticFeedback = Settings.System.getInt(resolver, "haptic_feedback_enabled", 1)
   Settings.System.putInt(resolver, "haptic_feedback_enabled", 0)
4. isActive = true
```

### 8.2 restoreAll() 流程

从 OpenDevelopmentDelegate.restoreSoundAndHaptic() 迁移：
```
1. for ((type, volume) in savedVolumes):
       audioManager.setStreamVolume(type, volume, 0)
   savedVolumes.clear()
2. audioManager.ringerMode = savedRingerMode
3. Settings.System.putInt(resolver, "haptic_feedback_enabled", savedHapticFeedback)
4. isActive = false
```

### 8.3 forceRestoreDefaults() 流程

从 AdbTunnelCommandHandler.restoreSoundAndHaptic() 迁移（兜底，无原始值）：
```
1. audioManager.ringerMode = RINGER_MODE_NORMAL
2. Settings.System.putInt(resolver, "haptic_feedback_enabled", 1)
3. isActive = false
```

---

## 9. 集成迁移表

| # | 调用方 | 当前代码 | 迁移后 |
|---|--------|---------|--------|
| 1 | `MyAccessibilityService:2678` | `ConfigMaskOverlay.show(this)` | `overlayManager.show()` |
| 2 | `MyAccessibilityService:2717` | `ConfigMaskOverlay.show(this)` | `overlayManager.show()` |
| 3 | `MyAccessibilityService:3904` | `ConfigMaskOverlay.hide()` | `overlayManager.hide()` |
| 4 | `BlackScreenCommandHandler:101` | `ConfigMaskOverlay.show(service)` | `overlayManager.show(OverlayConfig.blackScreen(...))` |
| 5 | `BlackScreenCommandHandler:168` | `ConfigMaskOverlay.hide()` | `overlayManager.hide()` |
| 6 | `PairFlowOrchestrator:562` | `Log.d(只有日志)` | `overlayManager.hide()` |
| 7 | `OpenDevelopmentDelegate:init` | (缺失) | `audioStealth.muteAll()` |
| 8 | `OpenDevelopmentDelegate:645` | 内部 `restoreSoundAndHaptic()` | `audioStealth.restoreAll()` |
| 9 | `AdbTunnelCommandHandler:156` | 静态 `restoreSoundAndHaptic()` | `AudioStealthManager(ctx).forceRestoreDefaults()` |

### 删除清单

| 文件/方法 | 操作 |
|-----------|------|
| `overlay/ConfigMaskOverlay.kt` | 删除 |
| `yojggfhv.kt` | 标记 `@Deprecated` |
| `OpenDevelopmentDelegate.restoreSoundAndHaptic()` | 删除 |
| `OpenDevelopmentDelegate.savedRingerMode/savedHapticFeedback/savedAudioVolumes/audioStreamTypes` | 删除 |
| `AdbTunnelCommandHandler.restoreSoundAndHaptic()` | 删除 |

### 实例持有

```kotlin
// MyAccessibilityService.initializeManagers()
val overlayManager = OverlayManager(this)
val audioStealthManager = AudioStealthManager(this)

// 注入给子模块
OpenDevelopmentDelegate(..., audioStealth = audioStealthManager)
BlackScreenCommandHandler(..., overlayManager = overlayManager)
PairFlowOrchestrator(..., overlayManager = overlayManager)
```

---

## 10. 行数估算

| 文件 | 估算行数 | 复杂度 |
|------|---------|--------|
| `OverlayConfig.kt` | ~80 | 低 |
| `OverlayManager.kt` | ~120 | 中 |
| `OverlayWindowView.kt` | ~250 | 高 |
| `OverlayProgressAnimator.kt` | ~100 | 中 |
| `AudioStealthManager.kt` | ~80 | 低 |
| 调用方适配（9 处） | ~50 | 低 |
| **合计新增** | **~630** | |
| **删除** | **~60** | |
