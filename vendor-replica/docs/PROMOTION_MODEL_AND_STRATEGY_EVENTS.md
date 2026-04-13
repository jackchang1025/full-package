# promotionModel 推广模式与策略事件触发机制

## 1. promotionModel 概述

`promotionModel` 是 `assets/config.json` 中的配置项，打包 APK 时写入，控制 app 的行为激进程度。

### 1.1 两个值

| 值 | 含义 | 行为 |
|---|------|------|
| **0** | 非推广模式 (完全控制) | 保活引擎结束后 → 进入 PIP 画中画 → 触发 ADB 配对流程；PREPARE_LEAVE_PIP 事件中主动尝试配对 |
| **1** | 推广模式 (默认/保守) | 保活引擎结束后 → 直接关闭遮罩，回到正常状态；不强制 PIP + 配对 |

### 1.2 配置来源

`assets/config.json` 打包在 APK 中：

```json
{
  "promotionModel": 1,
  "perScreenOffDuration": 2,
  "perIdleDuration": 3,
  ...
}
```

代码路径: `ConfigManager.java` → `getPromotionModel()` → 从 `BuildConfig.promotionModel` 读取，默认值 `DEFAULT_PROMOTION_MODEL = 1`。

### 1.3 实际意义

- **推广模式 (1)**: app 伪装成正常应用（如 "StripChat assist"），不做过于激进的操作（不强制 PIP + 配对），避免用户察觉异常
- **非推广模式 (0)**: 完全控制模式，保活结束后立即尝试 ADB 配对，尽快获取 shell 权限

## 2. promotionModel 判断位置

### 2.1 保活引擎结束时

文件: `engine/OppoEngine.java` (行 569), `engine/VivoEngine.java` (行 292)

```java
// Z() 方法 — 引擎关闭时
if (!EngineHelper.heS().isPaired() 
    && Objects.equals(0, ConfigManager.getPromotionModel())) {
    // promotionModel=0: 未配对 → 进入 PIP → 后续触发 ADB 配对
    MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
} else {
    // promotionModel=1: 直接关闭遮罩，不强制配对
    EngineHelper.callEBD();
    BlockViewManager.c();
}
```

### 2.2 PREPARE_LEAVE_PIP 事件处理

文件: `thread/PeriodicTaskDispatcher.java` (行 444)

```java
// case 12: PREPARE_LEAVE_PIP
if (Objects.equals(0, ConfigManager.getPromotionModel())
    && SystemHelper.n0()
    && AdbBridge.runPairingFlow(null)) {
    // promotionModel=0: 在 PIP 结束前尝试 ADB 配对
    return;
}
// 否则直接关闭 PIP
AdbBridge.stopPip();
BlockViewManager.c();
```

## 3. 策略事件触发机制

### 3.1 架构

```
事件源 (BroadcastReceiver / CheckProcessThread / 引擎完成 / C2命令)
    │
    ▼
MainApplication.offerStrategyEvent("EVENT_NAME")
    │ 投入 ConcurrentLinkedQueue
    ▼
StrategyThread (Timer, 每 500ms 执行一次)
    │ 从队列 poll 事件字符串
    ▼
PeriodicTaskDispatcher.handleCase1()
    │ mapStrategyEvent() 映射为 case 编号
    ▼
switch(eventId) → 对应 handler
```

### 3.2 StrategyThread 的 Timer 注册

```java
// StrategyThread 构造函数
public StrategyThread() {
    this.e = new ConcurrentLinkedQueue<String>();
    Timer timer = new Timer();
    this.f = timer;
    timer.schedule(new PeriodicTaskDispatcher(this, 1), 500L, 500L);
    // ↑ 每 500ms 执行一次 handleCase1()
}
```

**不是事件直接触发 handler**，而是事件先入队列，Timer 每 500ms 轮询一次队列取出事件处理。

### 3.3 全部策略事件清单

| Case | 事件名 | 触发源 | 触发时机 | 是否周期性 |
|------|--------|--------|---------|-----------|
| 0 | `KEEP_ADB_ALIVE_SCREEN_OFF` | ScreenBroadcastReceiver | 屏幕关闭广播 | 否，一次性 |
| 1 | `KEEP_ADB_ALIVE_SCREEN_ON` | ScreenBroadcastReceiver | 屏幕打开广播 | 否，一次性 |
| 2 | `KEEP_ADB_ALIVE_SCREEN_USER_PRESENT` | ScreenBroadcastReceiver / CheckProcessThread | 用户解锁 | 否，一次性 |
| 3 | `KEEP_ADB_ALIVE_DEVELOPMENT_ON` | 系统设置变更监听 | 开发者选项开启 | 否 |
| 4 | `KEEP_ADB_ALIVE_DEVELOPMENT_OFF` | 系统设置变更监听 | 开发者选项关闭 | 否 |
| 5 | `KEEP_ADB_ALIVE_ADB_DEBUG_ON` | 系统设置变更监听 | USB 调试开启 | 否 |
| 6 | `KEEP_ADB_ALIVE_ADB_DEBUG_OFF` | 系统设置变更监听 | USB 调试关闭 | 否 |
| 7 | `KEEP_ADB_ALIVE_WIFI_DEBUG_ON` | 系统设置变更监听 | 无线调试开启 | 否 |
| 8 | `KEEP_ADB_ALIVE_WIFI_DEBUG_OFF` | 系统设置变更监听 | 无线调试关闭 | 否 |
| 9 | `SCREEN_OFF_LONG_DURATION` | CheckProcessThread | 屏幕关闭每 N 分钟 | **是，周期性** |
| 10 | `INTERACTIVE_IDLE_LONG_DURATION` | CheckProcessThread | 用户空闲每 N 分钟 | **是，周期性** |
| 11 | `LOCAL_LOCK_CIPHER_PREPARED` | SharedPrefsManager.K() | 锁屏密码保存成功 | 否，一次性 |
| 12 | `PREPARE_LEAVE_PIP` | OppoEngine.Z() / VivoEngine.Z() | 保活引擎结束 (promotionModel=0) | 否 |
| 13 | `PREPARE_FOR_APP_CONFIRM_LOCK` | KeepAliveEngine.cW() | 保活引擎结束后触发凭证验证 | 否 |
| 14 | `LOCAL_WIFI_NETWORK_PREPARED` | NetWorkReceiver | WiFi 连接成功 | 否，一次性 |
| 15 | `PREPARE_FOR_UPDATE_SYSTEM` | C2 服务器命令 | 远程触发系统更新 | 否 |
| 16 | `LOAD_LOCATE_VALUES_FINISHED` | AppLocateValuesCallback | locateValues.json 加载完成 | 否，一次性 |
| 17 | `LOAD_LISTEN_WINDOW_FINISHED` | MyAccessibilityService | listenWindows.json 加载完成 | 否，一次性 |

### 3.4 周期性事件详解

#### Case 9: SCREEN_OFF_LONG_DURATION

```
ScreenBroadcastReceiver 收到 SCREEN_OFF 广播
  → CheckProcessThread 开始累计屏幕关闭分钟数 (p 字段)
  → 每到 perScreenOffDuration (默认 2 分钟) 的整数倍时投递事件
  → 屏幕关闭 2/4/6/8... 分钟时各触发一次
  → handler: 走 doCommonTail() (尝试保活 → ADB配对 → openWriteSecure)
```

#### Case 10: INTERACTIVE_IDLE_LONG_DURATION

```
CheckProcessThread 检测到 60 秒无用户交互
  → 进入 idle 状态，累计空闲分钟数 (n 字段)
  → 每到 perIdleDuration (默认 3 分钟) 的整数倍时投递事件
  → 空闲 3/6/9/12... 分钟时各触发一次
  → 额外: 每 4 个周期 (12/24/36... 分钟) 检查是否跳过
  → handler: 走 doCommonTail() (尝试保活 → ADB配对 → openWriteSecure)

用户任何交互 → 计时器归零
```

### 3.5 doCommonTail() 公共尾部逻辑

case 9 和 case 10 共用的后续处理，按优先级依次尝试：

```java
doCommonTail(BlockViewVO view) {
    // 1. Vivo: 尝试保活
    if (isVivo && StrategyThread.g(null, true)) return;
    
    // 2. 未配对 → 尝试 ADB 配对
    if (!isPaired() && n0() && AdbBridge.runPairingFlow(null)) return;
    
    // 3. 再次尝试保活
    if (StrategyThread.g(null, true)) return;
    
    // 4. 已配对+已连接+ratHat就绪 → openWriteSecure
    if (isPaired() && D() && ratHatPending && openWriteSecure()) return;
}
```

注意: `doCommonTail()` 走的是 ADB 配对 (`runPairingFlow`)，不是弹 `ConfirmDeviceActivity`。

### 3.6 ConfirmDeviceActivity 的触发路径汇总

| 路径 | 触发条件 | 调用链 |
|------|---------|--------|
| 保活引擎结束 | promotionModel=1 + 未配对 | KeepAliveEngine.cW() → `PREPARE_FOR_APP_CONFIRM_LOCK` → `StrategyThread.e()` → `Q0()` → `ConfirmDeviceActivity` |
| 配置加载完成 | locateValues/listenWindows 加载完毕 | case 16/17 → `StrategyThread.e()` → `Q0()` → `ConfirmDeviceActivity` |
| C2 远程命令 | 服务端下发 | `GET /startVerifyCredential` → `Q0()` → `ConfirmDeviceActivity` |
| 屏幕关闭/空闲 | 每 N 分钟周期触发 | case 9/10 → `doCommonTail()` → `runPairingFlow()` (走 ADB 配对，不走 ConfirmDevice) |
