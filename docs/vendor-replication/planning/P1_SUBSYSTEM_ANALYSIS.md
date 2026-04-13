# P1 功能子系统深度分析

> vendor-replica 三大核心子系统的触发条件、执行时机、完整链路
> 生成日期: 2026-04-09

## 目录

- [一、权限自动获取流程](#一权限自动获取流程)
- [二、保活引擎](#二保活引擎)
- [三、ADB 无线配对与连接](#三adb-无线配对与连接)
- [四、三大子系统对比](#四三大子系统对比)

---

## 一、权限自动获取流程

### 1.1 触发模式

**服务端命令 → 系统弹窗 → 无障碍自动点击**

```
服务端 HTTP 命令                          应用自检
     │                                      │
     ├─ /requestPermission ──┐               │
     ├─ /startAccessibility  │               │
     ├─ /ignoreBatteryOpt.  ├→ 系统弹出权限对话框
     ├─ /startAdminActive    │               │
     └─ /startInstallApp ───┘               │
                                             │
                              ┌──────────────┘
                              ▼
              MyAccessibilityService 监听到窗口变化
                              │
                    ┌─────────┼─────────┐
                    ▼         ▼         ▼
         GrantPermission  MediaProj.  PackageInstaller
         Delegate         Delegate    Delegate
                    │         │         │
                    ▼         ▼         ▼
              自动点击"允许"/"安装"/"确认"
```

权限获取是**服务端主动发起**的，应用本身不会自动请求权限。无障碍服务只负责在权限弹窗出现后**自动点击确认**。

### 1.2 HTTP 路由入口

| 路由 | Handler | 说明 |
|------|---------|------|
| `/requestPermission` | `UiDialogHandler.requestPermission()` | 通用权限请求（按组或按值） |
| `/startAccessibility` | `AppManageHandler.startAccessibility()` | 打开无障碍设置页 |
| `/ignoreBatteryOptimization` | `UiDialogHandler.ignoreBatteryOptimization()` | 电池优化白名单 |
| `/startAdminActive` | `AppManageHandler.startAdminActive()` | 激活设备管理员 |
| `/startInstallApp` | `AppManageHandler.startInstallApp()` | 应用安装（含自动确认） |

### 1.3 权限映射表 (PermissionManager)

```java
// PermissionManager.mapPermissionValue()
"android.permission.AUTO_START"                   → 0  // 自启动
"android.permission.USAGE_ACCESS_SETTINGS"        → 1  // 使用情况访问
"android.permission.ACCESSIBILITY"                → 2  // 无障碍服务
"android.permission.OVERLAY"                      → 3  // 悬浮窗
"android.permission.MANAGE_UNKNOWN_APP_SOURCES"   → 4  // 安装未知来源
"android.permission.IGNORE_BATTERY_OPTIMIZATIONS" → 5  // 电池优化白名单
"android.permission.MEDIA_PROJECTION"             → 6  // 屏幕投影
```

### 1.4 六大权限委托详解

#### A. GrantPermissionDelegate — 权限弹窗自动点击

- **文件**: `delegate/GrantPermissionDelegate.java`
- **触发方式**: 被动 — 任何权限弹窗出现时自动触发
- **监听窗口**:
  - `com.android.permissioncontroller.permission.ui.GrantPermissionsActivity`
  - `com.android.packageinstaller.permission.ui.GrantPermissionsActivity`
- **监听事件**: `WINDOW_STATE_CHANGED (32)`, `WINDOW_CONTENT_CHANGED (16384)`
- **自动化动作**: 点击"允许"按钮
- **执行流程**:
  ```
  系统弹出权限对话框
    → AccessibilityDelegate 监听到窗口变化
    → GrantPermissionDelegate.u() 回调
    → queue.add("allowInGrantPermission")
    → DelegateTaskLauncher.c(new ConfirmLockRunnable(this, 2))
    → 自动查找并点击"允许"按钮
  ```

#### B. PackageInstallerDelegate — 应用安装自动确认

- **文件**: `delegate/PackageInstallerDelegate.java`
- **触发方式**: 主动 — `/startInstallApp` 路由处理时加入队列
- **加入时机**:
  ```java
  // AppManageHandler.startInstallApp()
  PackageInstallerDelegate installDelegate = new PackageInstallerDelegate();
  delegates.add(installDelegate);
  service.t(PackageInstallerDelegate.class.getName(), windows);
  ```
- **自动化动作**: 点击"安装"/"完成"按钮

#### C. MediaProjectionDelegate — 屏幕投影权限

- **文件**: `delegate/MediaProjectionDelegate.java`
- **触发方式**: 被动 — 屏幕投影权限对话框出现时
- **监听窗口**: `com.android.systemui.media.MediaProjectionPermissionActivity`
- **自动化动作**: 点击"允许"

#### D. OpenDevelopmentDelegate — 开发者选项自动开启

- **文件**: `delegate/OpenDevelopmentDelegate.java`
- **触发方式**: 主动 — ADB 配对流程中若开发者选项未启用
- **加入时机**:
  ```java
  // AdbConnectionManager.startPairingFlow()
  if (!SystemHelper.K()) {  // 开发者选项未启用
      OpenDevelopmentDelegate delegate = AdbBridge.createOpenDevDelegate();
      delegateQueue.add(delegate);
  }
  ```
- **自动化动作**: 导航到"关于手机" → 连续点击"版本号" 7 次
- **状态机**: `DevelopmentStage` enum (b→c→d→e→f→g→h→i→j→k→l)
- **超时控制**: 100 秒后自动清理

#### E. EnableSecureDelegate — 系统设置写权限

- **文件**: `delegate/EnableSecureDelegate.java`
- **触发方式**: 主动 — 需要写 Secure Settings 时
- **加入时机**:
  ```java
  // AdbConnectionManager
  svc.a.add(AdbBridge.createWriteSecureDelegate());
  ```

#### F. PairAccessibilityDelegate — 无线配对自动化

- **文件**: `delegate/PairAccessibilityDelegate.java`
- **触发方式**: 主动 — 启动无线调试配对流程时
- **加入时机**:
  ```java
  // AccessibilityDelegateManager.e()
  this.a.add(new PairAccessibilityDelegate());
  ```
- **自动化动作**: 导航到开发者选项 → 无线调试 → 输入配对码
- **厂商特殊处理**:
  - **Xiaomi**: USB 安装权限 + 安全中心处理
  - **OPPO**: 权限监控禁用
  - **Vivo/Samsung/Huawei**: 特殊窗口导航

### 1.5 ConfirmDeviceActivity 启动时机

- **文件**: `activity/ConfirmDeviceActivity.java`
- **触发路由**: `/startVerifyCredential`, `/showConfirmLock`
- **Intent 参数**:
  - `CONFIRM_DEVICE_CREDENTIAL_TITLE` — 标题
  - `CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE` — 副标题
  - `CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION` — 描述
  - `CONFIRM_FOR_EVENT_CODE` — 事件码
- **执行逻辑**:
  - API 30+ → `BiometricPrompt` (生物识别 + 锁屏密码)
  - API < 30 → `KeyguardManager.createConfirmDeviceCredentialIntent()`
- **验证成功后**: `notifyCredentialResult()` → 通知 `CrackLockCipherPlug` 启动密码监控

---

## 二、保活引擎

### 2.1 触发模式

**服务端下发策略 → StrategyThread 判断条件 → 选择厂商引擎 → 无障碍自动操作**

```
服务端                    应用本地
  │                         │
  ├─ /syncPowerControl ──→ 保存策略到 SharedPrefs
  │                         │
  └─ /sharePowerControl ←── 查询当前保活状态
                            │
                   StrategyThread (500ms 轮询)
                            │
                   5 重条件判断:
                   ├─ ADB 未配对？
                   ├─ 屏幕亮着？
                   ├─ 电池尚未豁免？
                   ├─ 无障碍已就绪？
                   └─ 非省电模式？
                            │
                   全部满足 → 选择厂商引擎
                            │
              ┌──────┬──────┼──────┬──────┐
              ▼      ▼      ▼      ▼      ▼
           OPPO   Xiaomi  Huawei  Vivo  AOSP
              │      │      │      │      │
              ▼      ▼      ▼      ▼      ▼
         导航到厂商的后台管理设置页面
         自动关闭"自动管理"/开启"允许后台运行"
```

保活引擎是**混合触发** — 服务端下发策略（什么时候执行），应用本地判断条件（能不能执行），无障碍服务自动操作（怎么执行）。

### 2.2 五重执行条件

| 条件 | 判断方法 | 不满足时 |
|------|---------|---------|
| ADB 未配对 | `AdbConnectionManager.isPaired()` == false | 跳过（用 ADB 远程控制更好） |
| 屏幕亮着 | `SystemHelper.p0()` == true | 跳过（无法操作 UI） |
| 电池未豁免 | `isKeepAliveExempt()` == false | 跳过（已经豁免了） |
| 无障碍就绪 | `MyAccessibilityService.P()` != null | 跳过 |
| 非省电模式 | `PowerSaveChecker.shouldKeepAlive()` == false | 跳过 |

### 2.3 电池豁免判断逻辑

```java
private static boolean isKeepAliveExempt() {
    // 主应用已获全后台？
    boolean serviceAllowed = hasFullBackgroundAllowance(servicePackage, false);
    // 备用应用已安装？
    boolean guardInstalled = SystemHelper.d0("com.google.guard") != null;
    // 备用应用已获全后台？
    boolean guardAllowed = hasFullBackgroundAllowance("com.google.guard", true);
    // 两者都豁免 → 无需保活
    return serviceAllowed && (guardAllowed || !guardInstalled);
}

private static boolean hasFullBackgroundAllowance(String pkg, boolean allowRetryFallback) {
    PowerControlStateVO state = SharedPrefsManager.k(pkg);
    if (Boolean.TRUE.equals(state.getAllowAllFullBackground())) return true;
    return allowRetryFallback
        && (Boolean.TRUE.equals(state.getAllowAutoStart()) || state.getRetryCount() >= 3);
}
```

### 2.4 六大厂商引擎

| 引擎 | 匹配条件 | 监听窗口 | 自动化动作 |
|------|---------|---------|-----------|
| **OppoEngine** | `DeviceUtils.isOppoFamily()` | `com.android.settings/InstalledAppDetailsTop`, `com.oplus.battery/PowerControlActivity` | 导航应用详情→电源控制→关闭自动管理 |
| **XiaomiEngine** | `DeviceUtils.isXiaomiFamily()` | `com.miui.securitycenter/AutoStartManagementActivity`, `com.miui.powerkeeper/HiddenAppsConfigActivity` | 导航安全中心→自启动管理→开启 |
| **HuaweiEngine** | `DeviceUtils.isHuaweiOrHonor()` | `com.huawei.systemmanager/AlertDialog`, `com.hihonor.systemmanager/AlertDialog` | 导航手机管家→启动管理→手动管理 |
| **VivoEngine** | `DeviceUtils.isVivoFamily()` | `com.vivo.permissionmanager/AlertDialog`, `com.vivo.abe/ExcessivePowerManagerActivity` | 7-phase 权限管理流程 |
| **TranssionEngine** | `DeviceUtils.isTecnoFamily()` | `com.transsion.phonemaster/AutoStartActivity` | 导航手机管家→自启动 |
| **AospKeepAliveEngine** | 以上都不匹配（兜底） | `com.android.settings/InstalledAppDetailsTop`, `com.android.settings/SubSettings` | 通用电池优化设置 |

### 2.5 HTTP 路由

#### `/syncPowerControl` — 服务端下发保活策略

```
POST /syncPowerControl?packageName=xxx&allowAllFullBackground=true&allowAutoStart=true
  → 保存 PowerControlStateVO 到 SharedPrefs
  → StrategyThread 后续检查时读取
```

#### `/sharePowerControl` — 查询当前保活状态

```
GET /sharePowerControl
  → 返回主应用 + 备用应用的 PowerControlStateVO 列表
  → 服务端据此决定是否需要重新执行保活
```

### 2.6 完整数据流

```
Server
  ↓ POST /syncPowerControl (下发策略)
Device SharedPrefs (保存)
  ↓
StrategyThread 读取 (检查是否已豁免)
  ↓
AccessibilityDelegateManager.b() 选择厂商引擎
  ↓
KeepAliveEngine.u(AccessibilityEvent) 无障碍事件触发
  ↓
DelegateTaskLauncher 执行 UI 自动化操作
  ↓
HttpApiManager.sendIntentCodeMessage("KEEP_ALIVE_RUNNING_EVENT")
  ↓ 上报结果
Server 记录保活状态, 评估是否需要重试
```

---

## 三、ADB 无线配对与连接

### 3.1 三种触发源

```
触发源 1: 服务端命令
  POST /localAdbPair {host, port, code}
  → AdbHandler.localAdbPair()
  → pairDevice(host, port, code)

触发源 2: StrategyThread 事件
  WIFI_DEBUG_ON / LOCAL_LOCK_CIPHER_PREPARED
  LOCAL_WIFI_NETWORK_PREPARED / PREPARE_LEAVE_PIP
  → runPairingFlow()

触发源 3: 系统设置变化
  ContentObserver(adb_enabled/adb_wifi_enabled)
  → BroadcastReceiver → StrategyThread 队列
  → periodicMaintenance()
```

### 3.2 StrategyThread 事件映射

| 事件 | Case | 触发场景 | ADB 动作 |
|------|------|---------|---------|
| `KEEP_ADB_ALIVE_DEVELOPMENT_ON` | 3 | 开发者选项被开启 | `periodicMaintenance()` |
| `KEEP_ADB_ALIVE_DEVELOPMENT_OFF` | 4 | 开发者选项被关闭 | `periodicMaintenance()` |
| `KEEP_ADB_ALIVE_ADB_DEBUG_ON` | 5 | USB 调试被开启 | `periodicMaintenance()` |
| `KEEP_ADB_ALIVE_ADB_DEBUG_OFF` | 6 | USB 调试被关闭 | `periodicMaintenance()` |
| `KEEP_ADB_ALIVE_WIFI_DEBUG_ON` | 7 | 无线调试被开启 | `periodicMaintenance()` |
| `KEEP_ADB_ALIVE_WIFI_DEBUG_OFF` | 8 | 无线调试被关闭 | `periodicMaintenance()` |
| `SCREEN_OFF_LONG_DURATION` | 9 | 屏幕长期关闭 | 尝试配对或安全写入 |
| `INTERACTIVE_IDLE_LONG_DURATION` | 10 | 交互空闲 | 尝试配对或安全写入 |
| `LOCAL_LOCK_CIPHER_PREPARED` | 11 | 锁屏密码已准备 | `runPairingFlow()` → 启动配对 |
| `PREPARE_LEAVE_PIP` | 12 | 离开 PiP 模式 | `runPairingFlow()` |
| `PREPARE_FOR_APP_CONFIRM_LOCK` | 13 | APP 确认锁 | `runPairingFlow()` |
| `LOCAL_WIFI_NETWORK_PREPARED` | 14 | WiFi 网络就绪 | `runPairingFlow()` → 启动配对 |

### 3.3 完整配对流程

```
startPairingFlow()
  │
  ├─ 前置条件检查
  │   ├─ WiFi 已连接？
  │   ├─ 开发者选项已启用？
  │   │   └─ 否 → 创建 OpenDevelopmentDelegate (自动启用)
  │   │         导航: 关于手机 → 版本号点击7次 → 开发者选项启用
  │   │         状态机: DevelopmentStage (b→c→d→e→f→g→h→i→j→k→l)
  │   │         超时: 100 秒
  │   ├─ 无线调试已启用？
  │   │   └─ 否 → enableWirelessDebugging()
  │   └─ 配对码可用？
  │
  ├─ 创建 PairAccessibilityDelegate
  │   导航: 开发者选项 → 无线调试 → 查找配对码 → 输入
  │   厂商特殊处理:
  │     Xiaomi: USB 安装权限 + 安全中心
  │     OPPO:   权限监控禁用
  │     Vivo:   特殊窗口导航
  │
  ├─ AdbTlsPairing.z() — TLS + SPAKE2 配对协议
  │   ├─ 状态 1→2: TLS 1.3 握手
  │   │   ├─ new Socket(host, port)
  │   │   ├─ SSLSocket.startHandshake()
  │   │   ├─ Conscrypt.exportKeyingMaterial() (导出密钥材料)
  │   │   └─ 初始化 AdbSpake2Cipher
  │   │
  │   ├─ 状态 2→3: SPAKE2 密钥交换
  │   │   ├─ 发送 ourMsg (SPAKE2 消息)
  │   │   ├─ 接收 serverMsg
  │   │   ├─ ctx.processMessage(serverMsg)
  │   │   ├─ HKDF 派生加密密钥 (基于共享密钥)
  │   │   └─ 设置导出密钥
  │   │
  │   └─ 状态 3→4: 加密 PeerInfo 交换
  │       ├─ 构建本地 PeerInfo (8192 字节)
  │       ├─ AES-GCM 加密本地 PeerInfo → 发送
  │       ├─ 接收服务端加密 PeerInfo → AES-GCM 解密
  │       └─ 验证 PeerInfo 大小 (8192 字节)
  │
  ├─ 配对成功后处理
  │   ├─ 上传密钥文件 (private.key + cert.pem)
  │   ├─ 更新 ADBConfig 持久化状态
  │   └─ wPaired.set(true)
  │
  └─ 建立 ADB 连接
      ├─ scanForDebugPort() — 并行端口扫描 (30000-49999, 4段并行)
      ├─ connectToPort(port)
      │   └─ new AdbConnection(host, port, keyPair, apiVersion)
      │       ├─ Socket 原始 TCP 连接
      │       ├─ 启动接收线程 (AdbOaHelper)
      │       ├─ 发送 CNXN 握手消息
      │       └─ B(timeout) 等待 CNXN 响应
      │
      └─ ADB 连接就绪
          ├─ adbConnected.set(true)
          └─ 可执行 shell 命令
```

### 3.4 ADB Shell 命令执行场景

```
executeShellCommand(command)
  └─ 包装: if command; then echo "Success"; else echo "Failed"; fi
     └─ 打开 AdbStream → 发送 WRTE → 轮询匹配结果
        ├─ 0 = success (匹配成功标记)
        ├─ 1 = failure (匹配失败标记)
        └─ 5 = timeout
```

**常见 Shell 命令**:

| 命令 | 用途 | 触发路由 |
|------|------|---------|
| `settings put secure enabled_accessibility_services ...` | 通过 ADB 启用无障碍服务 | 配对成功后自动执行 |
| `pm install -r <apk>` | 安装 APK | `/localAdbShell` |
| `pm grant <pkg> <perm>` | 授予权限 | `/localAdbShell` |
| `settings put global adb_wifi_enabled 1` | 启用无线调试 | 配对流程中 |
| `settings put global development_settings_enabled 1` | 启用开发者选项 | 配对流程中 |

### 3.5 AdbConnection 生命周期

```
创建: new AdbConnection(host, port, keyPair, sdkInt)
  └─ Socket 原始连接 + RSA 认证

运行: 接收线程持续处理 ADB 协议消息
  ├─ A_CNXN (握手)
  ├─ A_AUTH (认证)
  ├─ A_WRTE (写数据)
  ├─ A_OKAY (确认)
  └─ A_CLSE (关闭流)

流管理: ConcurrentHashMap<Integer, AdbStream>
  └─ 多路复用多个 shell 会话 (按 streamId 索引)

销毁: close()
  ├─ Socket.close()
  ├─ 接收线程 interrupt() + join()
  ├─ 关闭所有 AdbStream
  └─ PrivateKey.destroy()
```

### 3.6 ContentObserver 监听机制

**三个观察者**:
- `devEnabledContentObserver` → `Settings.Global.development_settings_enabled`
- `adbEnabledContentObserver` → `Settings.Global.adb_enabled`
- `adbWIFIEnabledContentObserver` → `Settings.Global.adb_wifi_enabled`

**变化后处理链**:
```
Settings 值变化
  → ContentObserver.onChange() (仅记录日志)
  → BroadcastReceiver 检测系统广播
  → StrategyThread 队列入对应事件 (如 KEEP_ADB_ALIVE_WIFI_DEBUG_ON)
  → PeriodicTaskDispatcher 处理 (case 7)
  → periodicMaintenance() 重新检测连接
```

### 3.7 OpenDevelopmentDelegate 与 PairAccessibilityDelegate 的关系

```
OpenDevelopmentDelegate (开启开发者选项)
  ↓ finish() 回调
PairAccessibilityDelegate (自动配对)
  ↓ 配对成功
AdbConnectionManager.pairDevice() (建立连接)
```

两者是**串联关系**:
- `OpenDevelopmentDelegate` 负责第一步：确保开发者选项已启用
- `PairAccessibilityDelegate` 负责第二步：在开发者选项页面找到无线调试并配对
- 都扩展 `AccessibilityDelegate` 基类
- 都使用 `ListenWindow` + `CombineFilter` 窗口匹配机制
- 都通过 `DelegateTaskLauncher` 提交任务

---

## 四、三大子系统对比

### 4.1 执行时机对比

| 维度 | 权限自动获取 | 保活引擎 | ADB 配对 |
|------|------------|---------|---------|
| **发起方** | 服务端命令 | 服务端策略+本地条件 | 多源触发 (3条路径) |
| **执行时机** | 服务端请求时 | StrategyThread 500ms 轮询 | 设置变化/事件触发 |
| **前置条件** | 无障碍已启用 | 屏幕亮+未豁免+非省电 | WiFi+开发者+无线调试 |
| **自动化方式** | 监听弹窗→点击确认 | 导航设置→切换开关 | 导航设置→输入配对码 |
| **完成标志** | 权限授予成功 | `allowAllFullBackground=true` | `adbConnected=true` |
| **失败处理** | 重试 (服务端再次发送) | retryCount++, 3次后降级 | 重新扫描端口 |
| **持久化** | 系统权限状态 | SharedPrefs PowerControlStateVO | SharedPrefs ADBConfig |

### 4.2 依赖关系

```
                    ┌─────────────┐
                    │ 无障碍服务   │ ← 所有子系统的基础
                    │ (已验证 ✅)  │
                    └──────┬──────┘
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
      ┌──────────┐ ┌──────────┐ ┌──────────┐
      │ 权限获取  │ │ 保活引擎  │ │ ADB 配对  │
      │          │ │          │ │          │
      │ 服务端   │ │ 服务端+  │ │ 多源     │
      │ 主动发起 │ │ 本地条件 │ │ 触发     │
      └────┬─────┘ └────┬─────┘ └────┬─────┘
           │            │            │
           │       ┌────┘            │
           ▼       ▼                 ▼
      ┌──────────────┐        ┌──────────┐
      │ HTTP Server   │        │ Shell    │
      │ (已验证 ✅)   │        │ 命令执行 │
      └──────────────┘        └──────────┘
```

### 4.3 真机验证清单

#### 权限自动获取验证

```bash
# 1. 测试权限请求 (需从 HTTP Server 发送)
adb shell "echo -e 'POST /requestPermission HTTP/1.1\r\nHost: 127.0.0.1\r\nContent-Type: application/json\r\nContent-Length: 70\r\n\r\n{\"groupValue\":\"android.permission-group.CONTACTS\",\"requestCode\":1001}' | nc 127.0.0.1 7910"

# 2. 测试电池优化白名单
adb shell "echo -e 'GET /ignoreBatteryOptimization HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n' | nc 127.0.0.1 7910"

# 3. 测试设备管理员激活
adb shell "echo -e 'GET /startAdminActive HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n' | nc 127.0.0.1 7910"

# 4. 观察日志
adb logcat -s "GrantPermissionDelegate" "o.l" "PermissionManager"
```

#### 保活引擎验证 (OPPO)

```bash
# 1. 查询当前保活状态
adb shell "echo -e 'GET /sharePowerControl HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n' | nc 127.0.0.1 7910"

# 2. 下发保活策略 (触发引擎)
adb shell "echo -e 'GET /syncPowerControl?packageName=com.guard.wallet&allowAllFullBackground=false HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n' | nc 127.0.0.1 7910"

# 3. 观察 OppoEngine 日志
adb logcat -s "OppoEngine" "EngineHelper" "StrategyThread" "AccessibilityDelegate"
```

#### ADB 配对验证

```bash
# 1. 确认无线调试状态
adb shell settings get global adb_wifi_enabled

# 2. 发起配对请求 (需要先在设备上启用无线调试并获取配对码)
adb shell "echo -e 'POST /localAdbPair HTTP/1.1\r\nHost: 127.0.0.1\r\nContent-Type: application/json\r\nContent-Length: 60\r\n\r\n{\"host\":\"127.0.0.1\",\"port\":XXXXX,\"code\":\"XXXXXX\"}' | nc 127.0.0.1 7910"

# 3. 观察配对日志
adb logcat -s "AdbConnectionManager" "AdbTlsPairing" "PairAccessibilityDelegate"
```

---

## 关键文件索引

| 模块 | 文件路径 | 说明 |
|------|---------|------|
| **权限** | `delegate/GrantPermissionDelegate.java` | 权限弹窗自动点击 |
| | `delegate/PackageInstallerDelegate.java` | 安装自动确认 |
| | `delegate/MediaProjectionDelegate.java` | 屏幕投影权限 |
| | `permission/PermissionManager.java` | 权限请求核心 |
| | `server/handler/UiDialogHandler.java` | HTTP 权限路由 |
| | `server/handler/AppManageHandler.java` | HTTP 应用管理路由 |
| | `activity/ConfirmDeviceActivity.java` | 设备凭据验证 |
| **保活** | `engine/OppoEngine.java` | OPPO 保活引擎 |
| | `engine/XiaomiEngine.java` | 小米保活引擎 |
| | `engine/HuaweiEngine.java` | 华为保活引擎 |
| | `engine/VivoEngine.java` | vivo 保活引擎 |
| | `engine/TranssionEngine.java` | 传音保活引擎 |
| | `engine/AospKeepAliveEngine.java` | 通用保活引擎 |
| | `delegate/EngineHelper.java` | 引擎选择适配器 |
| | `delegate/task/AutoEngineTask.java` | 引擎任务调度 |
| | `thread/StrategyThread.java` | 策略事件队列 |
| | `server/handler/AppManageHandler.java` | syncPowerControl 路由 |
| **ADB** | `adb/AdbConnectionManager.java` | 连接管理核心 |
| | `adb/AdbTlsPairing.java` | TLS+SPAKE2 配对协议 |
| | `adb/AdbConnection.java` | ADB 连接生命周期 |
| | `adb/AdbStream.java` | Shell 命令流 |
| | `delegate/OpenDevelopmentDelegate.java` | 开发者选项自动化 |
| | `delegate/PairAccessibilityDelegate.java` | 配对流程自动化 |
| | `delegate/AdbBridge.java` | ADB 委托桥接 |
| | `server/handler/AdbHandler.java` | HTTP ADB 路由 |
| | `thread/PeriodicTaskDispatcher.java` | 周期任务分发 |
