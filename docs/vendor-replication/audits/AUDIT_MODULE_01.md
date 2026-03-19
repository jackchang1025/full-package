# MODULE_01 网络通信 — Vendor 行为审计

## 1. 模块职责

HTTP 通信 + WebSocket 桥接 + 消息体定义。所有模块的数据上报、指令接收、文件上传都通过此模块。是整个系统的通信基础设施。

## 2. Vendor 架构

```
http/i.java (293行) — HTTP 请求执行器 (FetchClient)
  ├── OkHttp 客户端封装
  ├── 超时: 连接60s, 读120s, 写120s, 调用240s, ping 30s
  ├── d(body, path, callback) — GET/POST JSON
  ├── h(body, path, callback) — POST JSON
  ├── j(uploadVO, path, files, callback) — 文件上传 (Multipart)
  ├── k(uploadVO, path, files, bytes, callback) — 字节上传
  └── b(request) — 同步请求 → JsonObject

http/h.java (221行) — SSL/TLS 配置 (Conscrypt)
  ├── 自定义 SSLSocketFactory
  ├── 自定义 HostnameVerifier (信任所有)
  └── 证书固定 (CertificatePinner)

http/l.java (374行) — API 路由管理器 (HttpUtils)
  ├── 7 个预注册 API 端点 (消息/密码/文件上传)
  ├── 30+ 个静态方法 (每个对应一个 API 调用)
  ├── 本地代理路由 (127.0.0.1:7911/7912)
  └── 消息队列 URL 管理

http/v.java (123行) — 设备注册回调
  ├── 解析 ApiResult<DeviceInfoVO>
  ├── 保存 deviceId 到 SharedPreferences
  └── 触发后续初始化

bridge/a.java (115行) — WebSocket 桥接
  ├── OkHttp WebSocket
  ├── 自动重连
  └── 消息转发到 server/b.java

msg/ (9个文件, 320行) — 消息体定义
  ├── BaseMsgBody — 基类
  ├── BridgeBody/BridgeMessage — 桥接消息
  ├── BridgeBufferBody/BridgeBufferMessage — 二进制桥接
  ├── BridgeHttpMessage — HTTP 桥接
  ├── ReadScreenEvent/ReadScreenMessage — 屏幕读取
  └── ReadEventMessage — 事件消息

http/ 回调类 (30个, ~1300行) — 每个 API 端点的响应处理
  ├── 全部实现 p0.e 接口 (OkHttp Callback)
  ├── 解析 ApiResult<T> 响应
  └── 触发后续业务逻辑
```

## 3. API 端点清单 (vendor http/l.java)

### 远程服务器 API (https://serverHost)

| 方法 | 端点 | 功能 | 回调 |
|------|------|------|------|
| z() | /api/device/updateDeviceInfo.json | 上报设备信息 | g |
| t(str) | /api/message/post.json | 发送消息 | j.e(1) |
| q() | /api/message/post.json | 同步发送消息 | k (FutureTask) |
| B() | /api/cipher/postLockCipher.json | 上报锁屏密码 | c0 |
| C() | /api/cipher/postOtherCipher.json | 上报其他密码 | c0 |
| c() | /api/cipher/lockCiphers | 查询密码状态 | j.e(2) |
| d() | /api/listen/windows.json | 获取监听窗口配置 | m |
| a() | /api/locateValue/entryAppMap.json | 获取应用定位值 | a |
| u() | /api/agent/query.json | 查询代理文件 | u |
| v() | /api/walletAuth/strategy/noCompletes | 获取未完成策略 | o |
| y() | /api/smsRecognize/plug.json | 短信识别插件 | z |
| s() | /api/deviceInstallLog/post.json | 上报安装日志 | j.e(1) |
| r() | /api/containerApi/postCacheTaskResponse.json | 上报缓存任务响应 | j.e(1) |
| A() | /api/audioFile/batch.json | 上传音频文件 | e0 |
| D() | /api/photoFile/batch.json | 上传照片文件 | e0 |
| E() | /api/videoFile/batch.json | 上传视频文件 | e0 |
| — | /api/shotFile/batch.json | 上传截图文件 | e0 |
| — | /api/pairKeyFile/batch.json | 上传配对密钥 | e0 |

### 本地代理 API (127.0.0.1:7911 — rathat 代理)

| 方法 | 端点 | 功能 |
|------|------|------|
| g() | /deviceId | 获取设备ID |
| p() | /syncADBConfig | 同步 ADB 配置 |
| — | /shareADBConfig | 共享 ADB 配置 |

### 本地代理 API (127.0.0.1:7912 — 无障碍代理)

| 方法 | 端点 | 功能 |
|------|------|------|
| e() | /closeDevelopment | 关闭开发者选项 |
| f() | /closeWifiDebug | 关闭 WiFi 调试 |
| h() | /finishListenHelper | 完成监听助手 |
| i() | /listenHelper | 启动监听助手 |
| j() | /noticeAlive | 通知存活 |
| k() | /openADBDebug | 打开 ADB 调试 |
| l() | /openDevelopment | 打开开发者选项 |
| m() | /openWifiDebug | 打开 WiFi 调试 |
| n() | /screenrecord/start | 开始录屏 |
| o() | /screenrecord/stop | 停止录屏 |
| w() | /resetAccessibilityService | 重置无障碍服务 |
| — | /activeMainNotification | 激活通知监听 |
| x() | /router | 路由转发 |

## 4. 文件映射对比

### 核心文件

| Vendor | 行数 | Replica | 行数 | 差距 |
|--------|------|---------|------|------|
| http/i.java (FetchClient) | 293 | network/HttpClient.java | 176 | ⚠️ 缺 117 行 |
| http/h.java (SSL) | 221 | (内联到 HttpClient) | — | ⚠️ SSL 配置简化 |
| http/l.java (API路由) | 374 | network/NetworkManager.java | 69 | ❌ 严重缺失 305 行 |
| http/v.java (设备注册) | 123 | network/HttpCallback.java | 9 | ❌ 严重缺失 |
| bridge/a.java (WebSocket) | 115 | network/WebSocketClient.java | 192 | ✅ replica 更多 |

### 消息体

| Vendor | 行数 | Replica | 行数 | 状态 |
|--------|------|---------|------|------|
| msg/BaseMsgBody | 12 | msg/BaseMsgBody | 11 | ✅ |
| msg/BridgeBody | 42 | msg/BridgeBody | 42 | ✅ |
| msg/BridgeBufferBody | 58 | msg/BridgeBufferBody | 57 | ✅ |
| msg/BridgeMessage | 34 | msg/BridgeMessage | 33 | ✅ |
| msg/BridgeBufferMessage | 33 | msg/BridgeBufferMessage | 32 | ✅ |
| msg/BridgeHttpMessage | 34 | msg/BridgeHttpMessage | 34 | ✅ |
| msg/ReadScreenEvent | 44 | msg/ReadScreenEvent | 43 | ✅ |
| msg/ReadScreenMessage | 32 | msg/ReadScreenMessage | 31 | ✅ |
| msg/ReadEventMessage | 31 | msg/ReadEventMessage | 30 | ✅ |

### 回调类

| Vendor | Replica | 状态 |
|--------|---------|------|
| 30 个回调类 (~1300行) | 30 个桩文件 (各5行) | ❌ 全部是空桩 |

## 5. 核心差距

### 5.1 NetworkManager (69行) vs http/l.java (374行)

Replica NetworkManager 只有 init/getClient/getDeviceId，缺少:
- ❌ 30+ 个 API 调用方法 (z/t/q/B/C/d/a/u/v/y/s/r/A/D/E 等)
- ❌ 7 个预注册 API 端点 URL
- ❌ 本地代理路由 (127.0.0.1:7911/7912)
- ❌ 消息队列 URL 管理
- ❌ 路由转发 x()

### 5.2 HttpCallback (9行) vs http/v.java (123行)

Replica HttpCallback 只是一个空接口，缺少:
- ❌ 设备注册回调 (解析 DeviceInfoVO, 保存 deviceId)
- ❌ 所有 30 个回调类都是 5 行空桩

### 5.3 SSL/TLS 配置

Vendor http/h.java 有完整的 Conscrypt SSL 配置:
- 自定义 SSLSocketFactory
- 信任所有证书的 HostnameVerifier
- CertificatePinner

Replica 使用 OkHttp 默认 SSL，缺少自定义配置。

## 6. 优先修复项

### P0 (消息上报基础 — 所有模块依赖)
1. NetworkManager 补齐 API 端点常量 (7 个预注册 URL)
2. NetworkManager 补齐 postMessage() — 消息上报核心方法 (对应 vendor t())
3. NetworkManager 补齐 updateDeviceInfo() — 设备信息上报 (对应 vendor z())
4. HttpCallback 补齐设备注册回调 (对应 vendor v.java)

### P1 (完整 API)
5. NetworkManager 补齐密码上报 (B/C)
6. NetworkManager 补齐文件上传 (A/D/E)
7. NetworkManager 补齐监听窗口获取 (d)
8. NetworkManager 补齐本地代理路由 (7911/7912)
9. 30 个回调类从空桩补齐为实际实现

### P2 (安全)
10. SSL/TLS 自定义配置 (Conscrypt)
11. 证书固定 (CertificatePinner)
