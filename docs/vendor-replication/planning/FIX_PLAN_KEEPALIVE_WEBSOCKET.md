# 修复计划: 保活与 WebSocket 断连 (基于 2026-03-20 深度对比)

> 基于 COMPARISON_RESULT.md (2026-03-20) 的 11 项差异，聚焦 3 个 CRITICAL + 4 个 HIGH 问题。

---

## 问题根因总结

### CRITICAL-1: WebSocket 连接 ~14s 后断开

**现象**: `Software caused connection abort`，持续重连失败
**根因**: Replica 的 WebSocketClient 连接的是 Laravel 后端 WS (`ws://192.168.31.35:8081`)，这是为 Web 面板设计的。Vendor 使用独立的 Bridge WebSocket (`wss://{host}/bridge`) 与服务端通信。两者协议完全不同。

**Vendor 实现** (`bridge/a.java`):
- URL: `wss://{host}/bridge` (host 从 config 读取)
- 消息协议: type=7 (请求) / type=8 (响应) / type=15 (buffer 数据) / type=16 (buffer 确认)
- 连接失败 6 次自动断开
- 通过 BridgeMessage/BridgeBufferMessage 封装消息

### CRITICAL-2: HandlerMsgAndTimer 完全缺失

**现象**: Vendor 每 10s `同步发送消息成功`，Replica 无此线程
**根因**: Vendor 的 `thread/e.java` 是消息队列管理器，持有两个 ConcurrentLinkedQueue，通过 `thread/d.java` (TimerTask) 每 10s 批量发送。Replica 未实现。

**Vendor 实现** (`thread/e.java` + `thread/d.java`):
- `e.java`: 管理器，两个队列 (f252e=统计消息, f253f=即时消息)
- `e.a()`: 入队即时消息 (MessageRecordVO → ReqMessageVO)
- `e.b()`: 入队统计消息 (带去重: 相同事件 1s 内不重复)
- `d.java`: TimerTask, 10s 间隔执行 run() — 1898 条指令反编译失败
- d.run() 推测行为: 从队列取消息 → 通过 Bridge WebSocket 或 HTTP 批量发送

### CRITICAL-3: 无障碍事件未分发

**现象**: 配置完全一致 (feedbackType/capabilities/eventTypes 全部匹配)，但 Replica 0 行事件日志
**根因**: 三个缺陷叠加导致事件链路完全断裂

**缺陷 A: G() 被阉割**
- Vendor: G(event) 检测窗口/包名变化 → 调用 l0() 打印日志 → 调用 h0() 激活引擎
- Replica: G(event) 仅更新静态变量，未检测变化，未调用 notifyRootChanged()

**缺陷 B: notifyRootChanged() 是占位符**
```java
// Replica EngineManager.java
if (engine.matchWindow(packageName, className)) {
    if (!engine.isRunning()) {
        // ADAPT: vendor 调用 eVar.w(true) 设置活跃状态
    }
    // ADAPT: vendor 调用 eVar.v(root, isComplete, pkg, cls, title)
    matched = true;
}
```
引擎永远不会被设为 RUNNING，永远收不到 execute() 信号。

**缺陷 C: listenWindows.json 未解析注册**
- Vendor: d0() 解析 JSON → 注册包名白名单到 AccessibilityDelegateManager
- Replica: d0() 读取文件内容但未反序列化，未注册到 EngineManager
- 后果: isContentChangedPackage() 永远返回 false → TYPE_WINDOW_CONTENT_CHANGED 事件全部被过滤

---

## 修复计划

### Phase 1: 无障碍事件分发 (CRITICAL-3)

这是最基础的修复 — 没有事件分发，引擎无法工作，厂商适配无法运行。

#### FIX-1A: 修复 G() 窗口变化检测

**文件**: `android/.../service/MyAccessibilityService.java`
**改动**:
1. 在 G(event) 中添加旧包名/窗口与新包名/窗口的对比
2. 变化时调用 EngineManager.notifyRootChanged(root, packageName, className, title)
3. 添加日志: `"当前运行包名已变化"`, `"当前视图根节点已变化"`

#### FIX-1B: 补全 notifyRootChanged()

**文件**: `android/.../service/EngineManager.java`
**改动**:
1. 替换 `// ADAPT` 占位符为实际代码
2. `engine.setRunning(true)` 当 matchWindow 成功
3. `engine.execute(root, packageName, className, title)` 触发引擎主逻辑
4. 不匹配时 `engine.setRunning(false)`

#### FIX-1C: 实现 listenWindows.json 解析

**文件**: `android/.../service/MyAccessibilityService.java`
**改动**:
1. 在 d0() 中用 Gson 解析 JSON 为 ListenWindow 列表
2. 调用 EngineManager.registerWindowFilters() 注册包名白名单
3. 使 isContentChangedPackage() 能正确返回 true

**验证**: 重新安装后，logcat 应出现 `"当前运行包名已变化"` 等日志

---

### Phase 2: HandlerMsgAndTimer 消息队列 (CRITICAL-2)

#### FIX-2A: 实现 HandlerMsgAndTimer 管理器

**文件**: 新建 `android/.../thread/HandlerMsgAndTimer.java`
**参照**: `thread/e.java`
**实现**:
1. 两个 ConcurrentLinkedQueue<ReqMessageVO> (统计队列 + 即时队列)
2. Timer + TimerTask 10s 间隔
3. a(MessageRecordVO): 入队即时消息
4. b(MessageRecordVO): 入队统计消息 (带 1s 去重)

#### FIX-2B: 实现消息发送 TimerTask

**文件**: 新建或内联 TimerTask
**参照**: `thread/d.java` (反编译失败，基于日志推断)
**实现**:
1. 从两个队列取出所有待发消息
2. 通过 HTTP POST 批量发送到服务端 (或通过 Bridge WebSocket)
3. 打印 `"handle msg thread is running"` 和 `"同步发送消息成功：N"`
4. 失败时保留消息在队列中重试

#### FIX-2C: 在 MainApplication.init() 中启动

**文件**: `android/.../MainApplication.java`
**改动**: 在 init() 中创建 HandlerMsgAndTimer 单例并启动

**验证**: logcat 应出现 `"handle msg thread is running"` 和 `"同步发送消息成功"`

---

### Phase 3: Bridge WebSocket (HIGH)

注意: Replica APK 连接的是 Laravel 后端 WS，这是 Web 面板的 WebSocket。
Vendor APK 连接的是自己的 Bridge WebSocket。两者是不同的系统。

需要确认: Replica 是否需要实现 Bridge WebSocket，还是应该修复与 Laravel WS 的兼容性。

#### 方案 A: 修复 Laravel WS 兼容性 (推荐，如果 Replica 设计为连接 Laravel WS)

**文件**: `android/.../network/WebSocketClient.java`
**改动**:
1. 修复 registration ping 格式，使 Laravel WS 服务端能识别
2. 实现心跳 ping/pong 保持连接
3. 处理 `Software caused connection abort` — 可能是 TCP keepalive 超时

#### 方案 B: 实现 Bridge WebSocket (如果需要与 Vendor 服务端通信)

**文件**: 新建 `android/.../network/BridgeWebSocketClient.java`
**参照**: `bridge/a.java`
**实现**:
1. URL: `wss://{host}/bridge`
2. 消息类型: BridgeMessage (type=7), BridgeBufferMessage (type=15)
3. 响应处理: type=8 (命令响应), type=16 (buffer 确认, 6 次失败断开)
4. getCacheTask 中转

**验证**: WebSocket 连接稳定，无 `Software caused connection abort`

---

### Phase 4: API 请求链 (HIGH)

#### FIX-4A: 启动时 API 请求序列

**文件**: `android/.../MainApplication.java` 或新建初始化类
**参照**: Vendor 日志中的请求序列
**实现**:
1. register.json — 设备注册
2. agent/query.json — 查询代理配置
3. entryAppMap.json — 入口应用映射
4. windows.json — 远程监听窗口配置
5. getCacheTask — 缓存任务轮询

#### FIX-4B: AccountSync 实际执行

**文件**: `android/.../keepalive/service/AccountAuthenticatorService.java` + `SyncAdapter.java`
**改动**:
1. AccountUtils.addAccountExplicitly() 实际创建账号
2. SyncAdapter.onPerformSync() 实际执行同步
3. 在 KeepHeartThread 的 triggerDataSync 中触发

**验证**: logcat 出现 `"addAccountExplicitly success"` + `"onPerformSync"`

---

### Phase 5: 次要修复 (MEDIUM)

| # | 修复项 | 文件 |
|---|--------|------|
| 5A | HttpServer 健康检查确认 | KeepHeartThread.java |
| 5B | frpc 进程管理 (可选) | CheckProcessThread.java |
| 5C | NetworkSecurityConfig debugBuild=false | network_security_config.xml |
| 5D | WebView ERR_ABORTED 修复 | 引导页 URL 配置 |

---

## 实施顺序

```
Phase 1 (CRITICAL-3) → Phase 2 (CRITICAL-2) → Phase 3 (CRITICAL-1) → Phase 4 (HIGH) → Phase 5 (MEDIUM)
```

Phase 1 最先做，因为无障碍事件分发是所有自动化引擎的基础。
Phase 2 次之，消息队列是数据上报的通道。
Phase 3 解决 WebSocket 断连的直接原因。
Phase 4 补全 API 请求链。
Phase 5 收尾。

---

## 验证标准

每个 Phase 完成后:
1. `./gradlew test` 通过
2. `./gradlew assembleDebug` 成功
3. 安装到真机，开启无障碍
4. 执行相同测试场景 (设置页切换 → 息屏亮屏 → 后台30s)
5. 对比 logcat 日志，确认修复项的日志出现
6. WebSocket 连接稳定 (无 `Software caused connection abort`)

最终验证: 重新执行完整的 Round A → Round B → Round C 深度对比
