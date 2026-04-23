# ConfigMask 配置遮罩机制分析

> **样本**: update.apk
> **遮罩管理器**: `jadx-reference/p000/C0763km.java` (ConfigMaskManager)
> **遮罩 UI**: `jadx-reference/p000/C0708j7.java` (MaskOverlay View 构建)
> **遮罩动画**: `jadx-reference/p000/RunnableC0707j6.java` (进度条 + 轮播)
> **触发方**: `jadx-reference/rock/service/dqtvuisjd.java` → `startPermissionGrantFlow()`
> **消失检查**: `jadx-reference/rock/service/dqtvuisjd.java` 行 4441-4455
> **配置文件**: `assets/server_config.json` / `files/server_config.json`
> **日期**: 2026-04-19

---

## 一、结论先行

ConfigMask 是一个**全屏系统级悬浮窗遮罩**（TYPE_ACCESSIBILITY_OVERLAY 2032），在用户开启无障碍服务后**立即显示**，覆盖整个屏幕，阻止用户操作设备。

**设计目的**：在 yw5xud 权限自动化执行期间，防止用户看到自动化操作（点击系统设置、授权弹窗等），同时用"配置中请稍后..."的假进度条消除用户疑虑。

**生命周期**：

```
用户开启无障碍
    │
    ▼
dqtvuisjd.onServiceConnected()
    → initializeManagers()
    → startPermissionGrantFlow()
    → ★ ConfigMask.show()         ← 遮罩出现
    │
    │  ┌── 遮罩覆盖全屏 ─────────────────────────────┐
    │  │  黑屏 + APP 图标 + "配置中请稍后..."          │
    │  │  蓝色进度条 + 轮播提示                        │
    │  │                                               │
    │  │  后台执行：                                    │
    │  │  yw5xud 权限自动化                             │
    │  │  C2 WebSocket 连接                             │
    │  │  设备注册                                      │
    │  │  WRITE_SETTINGS 权限获取                       │
    │  └───────────────────────────────────────────────┘
    │
    ▼
4 个条件全部满足
    → ★ ConfigMask.hide()         ← 遮罩消失
```

---

## 二、触发机制

### 2.1 触发入口

**文件**: `dqtvuisjd.java` 方法 `m211530m8()` — `startPermissionGrantFlow()` (行 9297)

**触发链**：

```
用户开启无障碍
    → Android 系统启动 dqtvuisjd (AccessibilityService)
    → onServiceConnected()
    → initializeService() (行 6440+)
        → initializeManagers() → 创建 ConfigMask 管理器
        → startPermissionGrantFlow() → 显示遮罩
```

### 2.2 触发条件判断

```java
// startPermissionGrantFlow — 行 9325
t60.m214714d6("dqtvuisjd", "🚀 startPermissionGrantFlow() 开始执行");

// 检查是否已完成授权
boolean authCompleted = getSharedPreferences("app_state")
    .getBoolean("authorization_completed", false);

if (authCompleted) {
    // 已完成 → 跳过遮罩
    t60.m214714d6("dqtvuisjd", "✅ authorization_completed=true，跳过遮挡和适配流程");
    authorizationModule.m211768a6();
    return;
}

// 未完成 → 显示遮罩
if (Build.VERSION.SDK_INT >= 30) {
    // Android 11+ 路径
    if (!screenBrightnessManager.m213351a1()) {
        // ★★★ 显示遮罩 ★★★
        configMaskManager.m213601a1(false);
        t60.m214714d6("dqtvuisjd", "🖤 Android 11+设备：显示配置期间遮盖");
        // 初始化进度条
        configProgressManager.m211569a3();
        configProgressManager.m211570a4(CHECKING_PERMISSIONS, null);
    }
    // 启动 yw5xud 授权模块
    authorizationModule.m211768a6();
} else {
    // Android 10 路径
    configMaskManager.m213601a1(false);
    t60.m214714d6("dqtvuisjd", "🖤 显示配置期间遮盖，防止用户误操作");
    // delay 1000ms 后启动授权模块
}
```

**决策逻辑**：

| 条件 | 行为 |
|---|---|
| `authorization_completed = true` | 跳过遮罩，直接检查授权状态 |
| `authorization_completed = false` + Android 11+ | 显示遮罩 + 初始化进度条 + 启动 yw5xud |
| `authorization_completed = false` + Android 10 | 显示遮罩 + 1s 延迟后启动 yw5xud |

---

## 三、遮罩管理器 (ConfigMaskManager)

### 3.1 初始化

**文件**: `C0763km.java`

```java
// dqtvuisjd.initializeManagers() — 行 6366
this.f52427f8 = new C0763km(this, this);
```

### 3.2 显示遮罩

```java
// C0763km.m213601a1(boolean z) — 行 58
public final void m213601a1(boolean z) {
    C0708j7 maskView = this.f57545a2;
    if (maskView == null) {
        t60.m214704c5("ConfigMaskManager", "❌ maskManager为null，无法显示遮盖");
    } else {
        maskView.f57286b1 = z;  // 控制某个显示选项
        maskView.m213214a3();    // 添加到 WindowManager
    }
}
```

### 3.3 隐藏遮罩

```java
// C0763km.m213600a0() — 行 39
public final void m213600a0() {
    C0708j7 maskView = this.f57545a2;
    if (maskView != null) {
        maskView.f57287b2 = false;  // 标记关闭
        if (maskView.f57278a3) {     // 如果已显示
            if (是主线程) {
                maskView.m213213a2();  // 直接移除
            } else {
                post到主线程(maskView::m213213a2);  // 线程切换后移除
            }
        }
    }
}
```

---

## 四、遮罩 UI 构建

### 4.1 视觉结构

**文件**: `C0708j7.java` 方法 `m213211a0()` (行 85)

```
┌──────────────────────────────────────────────────────┐
│                  全屏黑色背景 (#000000)                │
│          (或 assets/app_loading_bg.webp 背景图)       │
│                                                       │
│                ┌────────────┐                         │
│                │   APP 图标  │  ← 80dp 圆角           │
│                │ (真实图标)  │    getApplicationIcon   │
│                └────────────┘                         │
│                                                       │
│              "APP 真实名称"  ← 18sp 白色              │
│            getApplicationLabel                        │
│                                                       │
│      ╔════════════════════════════╗                   │
│      ║  ▓▓▓▓▓▓▓▓░░░░░░░░░░░░░░  ║  ← 蓝色渐变进度条 │
│      ╚════════════════════════════╝    宽度 65%       │
│              (#4A90D9 → #67B8F7)                      │
│                                                       │
│          "正在连接服务器..."  ← 轮播切换提示           │
│           14sp, #FFFFFF                               │
│                                                       │
│                                                       │
│                                                       │
│       "正在自动配置和连接"  ← 底部文本                 │
│        "请勿操作设备"       16sp, #FFFFFF              │
│                                                       │
│    "配置完成后将自动返回应用"  ← 底部状态              │
│        12sp, #AAAAAA                                  │
│                                                       │
└──────────────────────────────────────────────────────┘
```

### 4.2 代码构建细节

```java
// C0708j7.m213211a0() — 构建全屏 FrameLayout

// 1. 根容器 — 全屏黑色
FrameLayout root = new FrameLayout(context);
root.setBackgroundColor(-16777216);  // #000000
root.setSystemUiVisibility(5894);     // 沉浸模式

// 2. 背景图 — 全屏 center_crop
ImageView bgImage = new ImageView(context);
bgImage.setScaleType(CENTER_CROP);
// 尝试加载 assets/app_loading_bg.webp → app_loading_bg.png
// 如果 assets 没有 → 尝试 drawable/bg_config_mask

// 3. 内容区 — 垂直居中 LinearLayout
LinearLayout content = new LinearLayout(context);
content.setOrientation(VERTICAL);
content.setGravity(CENTER);

// 4. APP 图标（可选，showAppIcon 配置控制）
if (showAppIcon) {
    ImageView icon = new ImageView(context);
    icon.setImageDrawable(packageManager.getApplicationIcon(packageName));
    icon.setClipToOutline(true);  // 圆角
    // 80dp × 80dp
}

// 5. APP 名称
TextView appName = new TextView(context);
appName.setText(getApplicationLabel());
appName.setTextSize(18.0f);
appName.setTextColor(Color.WHITE);

// 6. 进度条 — 双层 View
FrameLayout progressContainer = new FrameLayout(context);
// 宽度 = 屏幕宽度 × 65%，高度 6dp
View bgBar = new View(context);  // 底色 #33FFFFFF
View fgBar = new View(context);  // 前景渐变 #4A90D9 → #67B8F7
// fgBar 初始宽度 0，通过动画递增

// 7. 状态文本 — 轮播切换
TextView statusText = new TextView(context);
statusText.setText(loadingTips[0]);  // 初始提示
// 定时器切换 loadingTips 列表中的文本

// 8. 底部信息区
// 副标题 + 状态文本
```

### 4.3 Window 参数

**方法**: `m213212a1()` (行 272)

```java
int windowType = Build.VERSION.SDK_INT >= 26
    ? 2032   // TYPE_ACCESSIBILITY_OVERLAY (Android 8+)
    : 2006;  // TYPE_TOAST (Android 7-)

WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    screenWidth,      // 全屏宽
    screenHeight,     // 全屏高
    windowType,       // 2032
    -2140338280,      // flags
    1                 // RGBA_8888
);
params.gravity = Gravity.TOP | Gravity.START;
params.x = 0;
params.y = 0;
// Android 9+ (API 28): 刘海屏适配
if (Build.VERSION.SDK_INT >= 28) {
    params.layoutInDisplayCutoutMode = 
        LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
}
```

**Flags 解析** (`-2140338280`)：

| 标志位 | 含义 |
|---|---|
| `FLAG_NOT_FOCUSABLE` | 不获取焦点，不拦截按键事件 |
| `FLAG_NOT_TOUCH_MODAL` | 只消费触摸区域内的事件 |
| `FLAG_LAYOUT_IN_SCREEN` | 全屏布局（含状态栏/导航栏） |
| `FLAG_LAYOUT_NO_LIMITS` | 不受屏幕边界限制 |
| `FLAG_FULLSCREEN` | 隐藏状态栏 |
| `FLAG_TRANSLUCENT_STATUS` | 透明状态栏 |
| `FLAG_TRANSLUCENT_NAVIGATION` | 透明导航栏 |

**关键特性**：TYPE_ACCESSIBILITY_OVERLAY (2032) 是无障碍服务专用的窗口类型，**不需要 SYSTEM_ALERT_WINDOW 权限**。只要无障碍服务在运行，就有权限显示此类窗口。

---

## 五、遮罩配置

### 5.1 配置来源

**文件**: `dqtvuisjd.java` 行 6229

遮罩配置从 `server_config.json` 读取（与 C2 地址、deviceKeySalt 等在同一个文件）：

```java
JSONObject config = new JSONObject(
    AbstractC1408xb.m215154a0(context, "server_config.json加密文件名")
);
```

读取优先级：
1. `files/server_config.json`（C2 远程推送可覆盖）
2. `assets/server_config.json`（APK 内置默认）

### 5.2 配置字段

| 配置字段 | 加密 | 默认值 | 含义 |
|---|---|---|---|
| `configMaskEnabled` | 是 | `true` | 是否显示遮罩 |
| `configProgressEnabled` | 是 | `true` | 是否显示进度条 |
| `configMaskText` | 是 | `"配置中请稍后..."` | 主标题文本 |
| `configMaskSubtitle` | 是 | `"正在自动配置和连接\n请勿操作设备"` | 副标题文本 |
| `configMaskStatus` | 是 | `"配置完成后将自动返回应用"` | 底部状态文本 |
| `configMaskTextColor` | 否 | `"#FFFFFF"` | 标题文字颜色 |
| `configMaskSubtitleColor` | 否 | `"#CCCCCC"` | 副标题文字颜色 |
| `configMaskStyle` | 否 | `"loading"` | 遮罩样式 |
| `showAppIcon` | 否 | `true` | 是否显示 APP 图标 |
| `loadingTips` | 否 | 见下 | 轮播提示文本列表 |

**默认 `loadingTips` 列表**：

```json
[
    "检查最优线路中",
    "正在连接服务器...",
    "正在加载资源...",
    "正在初始化配置...",
    "正在启动"
]
```

### 5.3 默认配置对象

```java
// dqtvuisjd.java 行 6242/6291 — 配置读取失败时的 fallback
dd0Var = new dd0(
    true,                              // configMaskEnabled
    true,                              // configProgressEnabled
    "配置中请稍后...",                   // maskText
    "正在自动配置和连接\n请勿操作设备",   // maskSubtitle
    "配置完成后将自动返回应用",           // maskStatus
    "#FFFFFF",                          // textColor
    "#CCCCCC",                          // subtitleColor
    "loading",                          // style
    ["检查最优线路中", ...],             // loadingTips
    true                               // showAppIcon
);
```

---

## 六、遮罩消失条件

### 6.1 检查逻辑

**文件**: `dqtvuisjd.java` 行 4441-4455

```java
t60.m214714d6("dqtvuisjd", "🔍 检查配置遮盖隐藏条件:");

boolean mediaProjection = ...;          // 截屏权限（SDK<30 不需要）
boolean serverConnected = m211487i1();  // C2 WebSocket 已连接
boolean deviceRegistered = m211484h8(); // 设备已在 C2 注册
boolean writeSettings = canWrite;       // WRITE_SETTINGS 已授予

t60.m214714d6("dqtvuisjd",
    "MediaProjection=" + mediaProjection +
    ", Server=" + serverConnected +
    ", Device=" + deviceRegistered +
    ", WriteSettings=" + writeSettings);

if (mediaProjection && serverConnected && deviceRegistered && writeSettings) {
    t60.m214714d6("dqtvuisjd", "✅ 所有条件满足，准备隐藏配置遮盖");
    configMaskManager.m213600a0();  // ★ 隐藏遮罩
} else {
    t60.m214726f4("dqtvuisjd", "⚠️ 条件未满足，保持配置遮盖显示");
}
```

### 6.2 四个条件详解

| 条件 | 方法 | 含义 | 何时满足 |
|---|---|---|---|
| MediaProjection | — | 截屏权限 | Android 10: 始终 true; Android 11+: 投屏权限授予后 |
| Server | `m211487i1()` | C2 WebSocket 连接 | `DataSyncClient` 建立 WSS 连接后 |
| Device | `m211484h8()` | 设备注册 | HTTP POST `/api/client/register` 成功后 |
| WriteSettings | `Settings.System.canWrite()` | 修改系统设置权限 | yw5xud 自动化授予后 |

### 6.3 检查时机

遮罩消失检查在多个位置触发（不是定时轮询，而是事件驱动）：
- C2 WebSocket 连接成功回调
- 设备注册成功回调
- 权限自动化每完成一项后检查
- `configProgressManager` 状态更新时

---

## 七、遮罩与自动化的协同

### 7.1 遮罩保护自动化操作

遮罩在自动化执行期间起到**屏幕锁定**的作用：

```
┌─ 遮罩层（TYPE 2032）────── 用户可见 ──┐
│  "配置中请稍后..."                     │
│  进度条 + 轮播提示                     │
└────────────────────────────────────────┘
     ↑ 覆盖
┌─ 系统设置/权限弹窗 ──── 用户不可见 ──┐
│  yw5xud 自动化正在操作的系统页面      │
│  例如：打开悬浮窗权限、自启动开关     │
└────────────────────────────────────────┘
     ↑ 覆盖
┌─ iuzxujjtqev Activity ── 用户不可见 ──┐
│  WebView 伪装页面（已加载但被盖住）    │
└────────────────────────────────────────┘
```

### 7.2 遮罩与 WebView 的关系

| 时间段 | ConfigMask 遮罩 | WebView | 用户看到的 |
|---|---|---|---|
| 用户开启无障碍前 | 不存在 | 不存在 | 引导 UI |
| 开启无障碍后 ~0.4s | 显示（全屏） | 加载中（被遮罩盖住） | 遮罩进度条 |
| 自动化执行期间 | 显示 | 已加载（不可见） | 遮罩进度条 |
| 4 条件满足后 | 消失 | 可见 | WebView 页面 |
| PIN 捕获期间 | 已消失 | 可见（被 syuqattwmgit 弹窗覆盖） | 系统密码验证框 |
| `uninstallMode=true` 后 | 已消失 | 被 PkgVerifyOverlay 覆盖 | 假卸载页面 |

### 7.3 进度条与自动化阶段映射

`ConfigProgressManager` (`C0318a3`) 维护进度阶段枚举：

```java
enum ConfigStage {
    IDLE,                  // 空闲
    CHECKING_PERMISSIONS,  // 检查权限
    // ... 其他阶段
}
```

进度条根据 `ConfigStage` 更新宽度，给用户一种"正在处理"的假象。

---

## 八、检测特征

### 8.1 Window 特征

| 特征 | 值 |
|---|---|
| Window Type | `2032` (TYPE_ACCESSIBILITY_OVERLAY) |
| Window Size | 全屏 (screenWidth × screenHeight) |
| 背景色 | `#000000` (纯黑) |
| Flags | `-2140338280` (不可聚焦 + 全屏沉浸) |

### 8.2 Assets 资源

| 文件 | 用途 |
|---|---|
| `app_loading_bg.webp` | 遮罩背景图（优先） |
| `app_loading_bg.png` | 遮罩背景图（fallback） |
| `bg_config_mask` | drawable 目录下的遮罩背景 |

### 8.3 配置字段

| 字段 | 位置 |
|---|---|
| `configMaskEnabled` | `server_config.json`（加密） |
| `configMaskText` | `server_config.json`（加密） |
| `configMaskStyle` | `server_config.json`（明文） |
| `showAppIcon` | `server_config.json`（明文） |
| `loadingTips` | `server_config.json`（明文 JSONArray） |

### 8.4 Logcat 标签

| 标签 | 关键日志 |
|---|---|
| `dqtvuisjd` | `"🖤 Android 11+设备：显示配置期间遮盖"` |
| `dqtvuisjd` | `"🔍 检查配置遮盖隐藏条件:"` |
| `dqtvuisjd` | `"✅ 所有条件满足，准备隐藏配置遮盖"` |
| `ConfigMaskManager` | `"❌ maskManager为null，无法显示遮盖"` |
| `ConfigMaskManager` | `"❌ 隐藏配置遮盖失败"` |
