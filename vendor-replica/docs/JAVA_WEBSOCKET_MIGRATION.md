# Java-WebSocket 库迁移计划

> 依赖: `org.java-websocket:Java-WebSocket:1.5.4`（已在 build.gradle 中）

## 一、混淆包 → 真实包映射

| 混淆包 | 真实 Java-WebSocket 包 | 文件数 | 行数 | replica 现状 |
|--------|----------------------|--------|------|-------------|
| `e1/` | `org.java_websocket` (核心) | 4 | 1571 | **已迁移**到 `com.guard.wallet.websocket.*` |
| `f1/` | `org.java_websocket.client` | 1 | 686 | **已删**，`VendorWebSocketClient` extends real lib |
| `g1/` | `org.java_websocket.drafts` | 2 | 1367 | **已删**，桩在 `websocket/` |
| `i1/` | `org.java_websocket.exceptions` | 8 | 103 | 5 个桩文件 |
| `k1/` | `org.java_websocket.framing` | 6 | 388 | 3 个桩文件 |
| `l1/` | `org.java_websocket.handshake` | 6 | 43 | 2 个桩接口 |
| `n1/` | `org.java_websocket.server` | 2 | 1604 | 桩 + .pending 完整代码 |
| `o1/` | `org.java_websocket.util` | 2 | 160 | 极简桩 |

另有辅助包:
- `j1/` → `org.java_websocket.extensions` (DefaultExtension)
- `m1/` → `org.java_websocket.protocols` (IProtocol, Protocol)

## 二、类级映射表

### e1/ → `org.java_websocket` (已迁移)

| 混淆 | 真实类 | replica 类 |
|------|--------|-----------|
| `e1.a` | `AbstractWebSocket` | `com.guard.wallet.websocket.AbstractWebSocketServer` |
| `e1.b` | `WebSocket` (接口) | `com.guard.wallet.websocket.WebSocketConnection` |
| `e1.c` | `WebSocketListener` | `com.guard.wallet.websocket.WebSocketEventListener` |
| `e1.d` | `WebSocketImpl` | `com.guard.wallet.websocket.WebSocketConnectionImpl` |

### f1/ → `org.java_websocket.client`

| 混淆 | 真实类 | 关键方法映射 |
|------|--------|-------------|
| `f1.a` | `WebSocketClient` | `u()→connect()`, `t()→close()`, `v()→getPort()`, `w(Ex)→onError()`, `x(Str)→onMessage()`, `y()→prepareSocket()`, `z()→sendHandshake()`, `A()→upgradeSocketToSSL()` |

### g1/ → `org.java_websocket.drafts` (已删，桩在 websocket/)

| 混淆 | 真实类 |
|------|--------|
| `g1.a` | `Draft` → replica: `WebSocketDraft` |
| `g1.b` | `Draft_6455` → replica: `WebSocketDraftRFC6455` |

### i1/ → `org.java_websocket.exceptions`

| 混淆 | 真实类 |
|------|--------|
| `i1.a` | `IncompleteException` |
| `i1.b` | `IncompleteHandshakeException` |
| `i1.c` | `InvalidDataException` |
| `i1.d` | `InvalidFrameException` |
| `i1.e` | `InvalidHandshakeException` |
| `i1.f` | `LimitExceededException` |
| `i1.g` | `WebsocketNotConnectedException` |
| `i1.h` | `WrappedIOException` |

### k1/ → `org.java_websocket.framing`

| 混淆 | 真实类 | 内部 opcode |
|------|--------|------------|
| `k1.d` | `FramedataImpl1` (基类) | — |
| `k1.c` | `ControlFrame` (抽象) | — |
| `k1.a` | `DataFrame` (Text/Binary/Continuous) | i=1→CONTINUOUS, i=2→TEXT, i=3→BINARY |
| `k1.b` | `CloseFrame` | opcode=6 (wire=8) |
| `k1.e` | `PingFrame` | opcode=4 (wire=9) |
| `k1.f` | `PongFrame` | opcode=5 (wire=10) |

### l1/ → `org.java_websocket.handshake`

| 混淆 | 真实类 |
|------|--------|
| `l1.b` | `Handshakedata` (接口) |
| `l1.a` | `ClientHandshake` (接口) |
| `l1.f` | `ServerHandshake` (接口) |
| `l1.e` | `HandshakedataImpl1` (TreeMap headers) |
| `l1.c` | `HandshakeImpl1Client` (field b=resourceDescriptor) |
| `l1.d` | `HandshakeImpl1Server` (field b=httpStatusMessage) |

### n1/ → `org.java_websocket.server`

| 混淆 | 真实类 |
|------|--------|
| `n1.a` | `WebSocketServer.WebSocketWorkerThread` |
| `n1.b` | `WebSocketServer` |

### o1/ → `org.java_websocket.util`

| 混淆 | 真实类 |
|------|--------|
| `o1.a` | `Charsetfunctions` (UTF-8 验证) |
| `o1.b` | `NamedThreadFactory` |

## 三、业务代码调用方

| 调用方 | 引用的包 | 用途 |
|--------|---------|------|
| `com/guard/wallet/server/c.java` | `n1.b` | WebSocket 服务器单例，管理 4 路客户端队列 |
| `com/guard/wallet/websocket/*` | `e1.*` (已迁移) | WebSocket 核心抽象层 |
| `o/d.java` | `f1.a` | 任务执行器，直接操作 WebSocketClient 字段 |
| `com/guard/wallet/websocket/WebSocketEventListener` | `k1.e`, `l1.b` | 事件监听接口 |

## 四、分步迁移计划

### Phase 1: 异常类（最简单，无内部依赖）
- 删除 `i1/` 全部 8 个桩文件
- 所有调用方的 `i1.X` → `org.java_websocket.exceptions.RealName`
- 需要映射字段: `i1.c.a` → `InvalidDataException.getCloseCode()`

### Phase 2: 帧数据类
- 删除 `k1/` 全部 6 个桩文件  
- `k1.d` → `org.java_websocket.framing.FramedataImpl1`
- `k1.b` → `org.java_websocket.framing.CloseFrame`
- 注意内部 opcode 编号差异（库用枚举 Opcode.TEXT 等）

### Phase 3: 握手类
- 删除 `l1/` 全部桩文件
- 接口类直接替换

### Phase 4: 工具类
- 删除 `o1/`
- `Charsetfunctions` 和 `NamedThreadFactory` 直接用真实类

### Phase 5: Draft 协议（已部分完成）
- `g1/` 已删除，`WebSocketDraft`/`WebSocketDraftRFC6455` 桩在 `websocket/`
- 替换为 `org.java_websocket.drafts.Draft` / `Draft_6455`

### Phase 6: 核心类 + Client（已完成）
- `e1/` 已迁移到 `com.guard.wallet.websocket.*`
- `f1/a.java` (WebSocketClient) **已删除** — 替换为 `VendorWebSocketClient`
  - `com.guard.wallet.websocket.VendorWebSocketClient` extends `org.java_websocket.client.WebSocketClient`
  - 通过反射暴露 `engine`/`ostream`/`socket` 给 `o/d.java` 使用
  - 提供 vendor 方法别名: `u()`→connect, `t()`→close, `c()`→send, `w()`→onError, `x()`→onMessage
  - `com.guard.wallet.bridge.a` 已更新为 extends `VendorWebSocketClient`
  - `o/d.java` 已更新：所有 `f1.a` 引用替换为 `VendorWebSocketClient`

### Phase 7: Server
- `n1.b` → `org.java_websocket.server.WebSocketServer`
- 包含 vendor 自定义逻辑（路由分发、摄像头流等）

## 五、风险评估

| 风险 | 说明 | 应对 |
|------|------|------|
| 方法名不匹配 | 混淆方法名 vs 真实 API 名 | 本文档已提供完整映射 |
| 内部 opcode 差异 | 混淆用 int 1-6, 真实库用 Opcode 枚举 | Phase 2 重点处理 |
| WebSocketClient 字段直接访问 | `o/d.java` 直接读 `f1.a.j/l` 等字段 | 改用公共 API 或子类方法 |
| vendor 自定义逻辑混入库代码 | `n1/b` 的 onOpen 路由到 minicap/camera | Phase 7 需要分离业务逻辑和库代码 |
