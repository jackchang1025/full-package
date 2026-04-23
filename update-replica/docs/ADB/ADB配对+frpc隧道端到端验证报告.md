# ADB 配对 + frpc 隧道端到端验证报告

> **验证日期**: 2026-04-18
> **测试设备**: OPPO PGFM10, Android 16, API 36, ColorOS PGFM10_16.0.3.500
> **服务器**: Laravel 12 + frps 0.51.3 (Docker Sail, 192.168.31.35:8080)
> **APK**: dev.deltalab2964.swift (29MB, arm64, debug build)

---

## 一、全链路时序图（已验证）

```
手机端 (OPPO PGFM10)                        服务器端 (192.168.31.35)
  │                                            │
  │ 1. startPairFlow() 触发                    │
  │    isInDevOptionsWindow("正在运行的服务") ✅  │
  │    pairInDevOption → 滚动找到"无线调试" ✅    │
  │    点击进入无线调试页面 ✅                    │
  │                                            │
  │ 2. pairInWifiDebugWindow()                 │
  │    点击"使用配对码配对设备" ✅                │
  │    读取 port=41235, code=761230 ✅           │
  │                                            │
  │ 3. doPair() SPAKE2+TLS                     │
  │    TLS 1.3 握手 (Conscrypt provider) ✅      │
  │    exportKeyingMaterial (64 bytes) ✅        │
  │    SPAKE2 密钥交换 ✅                        │
  │    AES-128-GCM PeerInfo 交换 ✅              │
  │    ★ 配对成功 ★                             │
  │                                            │
  │ 4. readDebugPortFromScreen()               │
  │    regex 匹配 IP:port 从无障碍树 ✅          │
  │    saveDebugPortAndSync(port) ✅             │
  │                                            │
  │ 5. deployLocalService()                    │
  │    cp liblocal-service.so → /data/local/tmp/ ✅
  │    chmod 777 + fireAndForget ✅              │
  │    local-service v3.1.0 启动 (7912) ✅       │
  │                                            │
  │ 6. postDeployInit()                        │
  │    /noticeAlive → alive ✅                   │
  │    /setAppPackage ✅                         │
  │    notifyLocalServiceConfig() ✅             │
  │    /applyAllOptimizations ✅                 │
  │                                            │
  │ 7. deployFrpcBinary()                      │
  │    cp libfrpc.so → /data/local/tmp/frpc ✅   │
  │    chmod 777 ✅                              │
  │    frpc v0.64.0 就绪 ✅                      │
  │                                            │
  │ 8. /setConfig {serverAddr, deviceId}       │
  │────────────────────────────────────────────>│
  │                                            │
  │ 9. local-service 自动拉取 frpc.ini          │
  │    POST /api/tunnel/config                 │
  │────────────────────────────────────────────>│
  │    ← {"success":true,"data":{"configINI":"..."}}
  │<────────────────────────────────────────────│
  │                                            │
  │ 10. frpc 启动 + 隧道建立                    │
  │     frpc -c frpc_independent.ini ✅         │
  │     7910 → remote:20003 (HTTP API) ✅       │
  │     7900 → remote:20004 (WebSocket) ✅      │
  │     5555 → remote:20005 (Debug) ✅          │
  │<═══════════════════════════════════════════>│
  │                                            │
  │ 11. 服务器通过隧道访问设备 API               │
  │    curl localhost:20003/noticeAlive         │
  │────────────────────────────────────────────>│ → frps → frpc → 7910
  │    ← {"success":true,"message":"alive"}     │
  │<════════════════════════════════════════════│
```

---

## 二、各环节验证详情

### 2.1 ADB WiFi 配对 (SPAKE2+TLS)

| 步骤 | 结果 | 日志证据 |
|------|------|---------|
| TLS 1.3 握手 | ✅ | `TLS 握手成功` |
| SSLSocket 类型 | ✅ | `org.conscrypt.Java8EngineSocket` (Conscrypt provider) |
| exportKeyingMaterial | ✅ | `org.conscrypt 导出成功, 长度=64` |
| SPAKE2 消息 | ✅ | `SPAKE2 消息生成成功, 长度=32` |
| 密钥交换 | ✅ | `SPAKE2 密钥交换成功` |
| PeerInfo 交换 | ✅ | `发送加密 PeerInfo` → `收到服务器 PeerInfo` |
| 配对结果 | ✅ | `配对成功` |

**加密修复（本次会话）:**
- `SHA512withRSA` → `SHA256withRSA` (Android 16 BouncyCastle 兼容)
- `SSLContext.getInstance("TLSv1.3")` → `SSLContext.getInstance("TLSv1.3", Conscrypt.newProvider())` (exportKeyingMaterial 兼容)
- `generateCert(keyPair.private, "BC")` → `generateCert(keyPair.private)` (去掉硬编码 provider)

### 2.2 local-service 部署

| 步骤 | 结果 | 详情 |
|------|------|------|
| 二进制来源 | ✅ | `jniLibs/arm64-v8a/liblocal-service.so` (7.5MB, 内嵌 APK) |
| extractNativeLibs | ✅ | `AndroidManifest.xml` 设置 `android:extractNativeLibs="true"` |
| 复制路径 | ✅ | `cp nativeLibDir/liblocal-service.so → /data/local/tmp/local-service` |
| 进程 | ✅ | `local-service server` PID 运行中 |
| HTTP 端口 | ✅ | `127.0.0.1:7912 LISTEN` |
| /noticeAlive | ✅ | `{"success":true,"message":"alive"}` |

### 2.3 Java HTTP 服务器 (RemoteConfigManager, 7910)

| 步骤 | 结果 | 详情 |
|------|------|------|
| 端口监听 | ✅ | `*:7910 LISTEN` |
| 线程池 | ✅ | `Executors.newFixedThreadPool(8)` |
| 端口重试 | ✅ | 7910-7918 逐个尝试，跳过 7912 |
| 并发请求 | ✅ | 3 并发请求全部秒回 |
| /containerState | ✅ | `accessibilityRunning=true, localHttpServerPort=7910` |
| /accessibilityState | ✅ | `ourServiceEnabled=true` |
| 看门狗不报错 | ✅ | local-service 日志 0 条 `7910 连续不可达` |

**关键修复（本次会话）:**
- accept loop 从空 stub → `ServerSocket` + 8 线程池
- `initializeDeferredManagers()` 调用接入 `postAuthorizationInit`
- `MyAccessibilityService.onServiceConnected` → `rcm.start()` 启动调用

### 2.4 frpc 二进制部署

| 步骤 | 结果 | 详情 |
|------|------|------|
| 二进制来源 | ✅ | `jniLibs/arm64-v8a/libfrpc.so` (14MB, vendor APK 提取) |
| 版本 | ✅ | frpc v0.64.0 (Go 静态链接) |
| 复制路径 | ✅ | `cp nativeLibDir/libfrpc.so → /data/local/tmp/frpc` |
| chmod | ✅ | `chmod 777 /data/local/tmp/frpc` |
| 文件验证 | ✅ | `-rwxrwxrwx 14155928 /data/local/tmp/frpc` |

### 2.5 frpc.ini 配置拉取

| 步骤 | 结果 | 详情 |
|------|------|------|
| /setConfig 通知 | ✅ | `{"success":true,"message":"config updated"}` |
| local-service 请求 | ✅ | `POST http://192.168.31.35:8080/api/tunnel/config` |
| Laravel 路由 | ✅ | `Route::post('/tunnel/config', ...)` |
| 设备查找 | ✅ | `Device::where('device_uid', $deviceId)` |
| 配置生成 | ✅ | `FrpcConfigService::generateConfig()` |
| 端口分配 | ✅ | `frpc_base_port=20003` (3 连续端口) |

**Go 代码期望的 JSON 格式（通过错误日志逆向确认）:**
```json
{
    "success": true,
    "data": {
        "configINI": "[common]\nserver_addr = 192.168.31.35\n..."
    }
}
```

**调试过程中的错误和修正:**

| 尝试 | Go 报错 | 原因 |
|------|---------|------|
| 纯文本 INI | `invalid character 'c'` | Go 先尝试 JSON 解析 |
| `{"code":0,"data":{...}}` | `code=0` 错误 | data 结构不对 (targetFileUrl vs configINI) |
| `{"code":200,"data":"..."}` | `code=200` 错误 | data 应是对象非字符串 |
| `{"code":1,"data":{"configINI":"..."}}` | `code=1` 错误 | Go 检查 `code != 0` |
| `{"data":{"configINI":"..."}}` | `cannot unmarshal string into struct field .data.configINI` | data 是字符串非对象 |
| **`{"success":true,"data":{"configINI":"..."}}`** | ✅ 成功 | 无 code 字段 (Go 默认 0) + 正确的 data.configINI 结构 |

### 2.6 frpc 隧道状态

| 隧道名称 | 本地端口 | 远程端口 | 状态 | 用途 |
|---------|---------|---------|------|------|
| `http-api-8` | 7910 | 20003 | ✅ online | Java HTTP API |
| `websocket-8` | 7900 | 20004 | ✅ online | WebSocket |
| `wifi-debug-port` | 5555 | 20005 | ✅ online | ADB WiFi 调试 |

frps Dashboard 确认: `curl -u admin:admin123 http://localhost:7500/api/proxy/tcp`

### 2.7 通过隧道访问设备 API

**测试路径**: `curl localhost:20003/{endpoint}` → frps → frpc → 设备 7910

| 端点 | 状态 | 响应 |
|------|------|------|
| `/noticeAlive` | ✅ | `alive, accessibilityRunning=true` |
| `/version` | ✅ | `v4.6.4(40604)` |
| `/containerState` | ✅ | `localHttpServerPort=7910, localServicePort=7912` |
| `/accessibilityState` | ✅ | `ourServiceEnabled=true, enabledCount=1` |

---

## 三、本次会话完成的改动总览

### Android 端 (update-replica)

| 文件 | 改动 | 说明 |
|------|------|------|
| `SystemOptimizeManager.kt` | 15+ 方法新增/修改 | isInWifiDebugWindow, readDebugPortFromScreen, deployLocalService, deployFrpcBinary, startPairFlow 三路分发, mainAccessibilityEventHandler Scene B/D, timeoutHandler, executor var→重建, 等 |
| `SetupConstants.kt` | 3 常量新增 | WIRELESS_DEBUG_PAGE_TEXTS, PAIR_FAIL_DIALOG_TEXTS, DEVELOPER_OPTIONS_TEXTS 空格变体 |
| `MyAccessibilityService.kt` | 3 处接线 | SOM 懒初始化 + filterAccessibilityEvent 转发, initializeDeferredManagers 调用, rcm.start() |
| `AdbTunnelCommandHandler.kt` | 4 方法修复 | START_PAIRING/FULL_DEPLOY/AUTO_WIRELESS_PAIRING/DIRECT_PAIR 接入 startPairFlow |
| `RemoteConfigManager.kt` | start() 重写 | 8 线程池 + 端口 7910-7918 重试 + handleClient HTTP 解析 |
| `AndroidManifest.xml` | 1 属性 | `extractNativeLibs="true"` |
| `jniLibs/arm64-v8a/` | 2 二进制 | `liblocal-service.so` (7.5MB) + `libfrpc.so` (14MB) |

### Laravel 端 (app)

| 文件 | 改动 | 说明 |
|------|------|------|
| `routes/api.php` | 1 路由 | `POST /api/tunnel/config` |
| `AgentController.php` | 1 方法 | `tunnelConfig()` — Go 兼容 JSON 格式 |

### 测试文件 (新增)

| 文件 | 测试数 |
|------|--------|
| IsInWifiDebugWindowTest.kt | 6 |
| StartPairFlowAlignmentTest.kt | 4 |
| EventDispatchAlignmentTest.kt | 6 |
| TimeoutAndRecoveryTest.kt | 5 |
| ExecutorLifecycleTest.kt | 3 |
| AdbTunnelCommandHandlerWiringTest.kt | 4 |
| IsInDevOptionsWindowTest.kt | 3 |
| DeployLocalServiceTest.kt | 5 |
| DebugPortSyncTest.kt | 3 |
| RemoteConfigAcceptLoopTest.kt | 3 |
| RemoteConfigThreadPoolTest.kt | 3 |
| ReadDebugPortFromScreenTest.kt | 3 |
| DeployFrpcTest.kt | 6 |
| PairInWifiDebugWindowTest.kt | 17 |
| DoPairFlowTest.kt | 6 |
| ExtractPairingCodeTest.kt | 3 |
| **总计** | **80 个新测试** |

---

## 四、已知遗留问题

| 问题 | 优先级 | 说明 |
|------|--------|------|
| serverAddr 需手动设置 | P2 | 当前通过 `adb shell settings put global debug_server_addr` 或 `/setConfig` API 设置。生产环境需通过 C2/WebSocket 自动配置 |
| OPPO force-stop 后无障碍事件丢失 | P2 | Android 16 行为。install -r 也会导致。需用户手动重新开关无障碍服务 |
| debugPort=5555 (USB) | P2 | readDebugPortFromScreen 在非配对弹窗页面读不到无线端口。getWirelessDebugPort fallback 到 netstat/Settings.Global |
| Scene E (confirmLock) 未实现 | P2 | 有锁屏密码的设备配对流程卡住 |
| Scene F (securityCenter) 未实现 | P2 | MIUI 安全中心弹窗 |
| uploadAdbKeys 服务器通信 | P2 | 依赖加密的 C2 服务器地址 |
| frpc local_port 被 local-service 修正为 7912 | P3 | Go 代码自动修正 `local_port: 7910 → 7912`，不影响功能 |

---

## 五、环境信息

### 服务器 Docker 容器

| 服务 | 镜像 | 端口 |
|------|------|------|
| laravel.test | sail-8.4/app | 8080→80, 8081, 5173 |
| mysql | mysql:8.4 | 3306 |
| redis | redis:alpine | 6379 |
| frps | snowdreamtech/frps:0.51.3 | 7000, 7500, 20000-20100 |

### frps 配置

```ini
[common]
bind_port = 7000
token = dev-frpc-token-2026
allow_ports = 20000-30000
dashboard_port = 7500
dashboard_user = admin
dashboard_pwd = admin123
max_pool_count = 50
heartbeat_timeout = 90
```

### 设备 frpc 端口分配

| 设备 | device_uid | DB id | base_port | HTTP | WS | Debug |
|------|-----------|-------|-----------|------|----|-------|
| OPPO PGFM10 | 4472c5f423c005c5 | 8 | 20003 | 20003 | 20004 | 20005 |
