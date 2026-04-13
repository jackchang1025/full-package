# vendor-replica 保活引擎启动链路 — 速查卡

## 🎯 5 分钟快速理解

### 三大核心组件

| 组件 | 文件 | 主要职责 |
|------|------|--------|
| **MyAccessibilityService** | `service/MyAccessibilityService.java` | 无障碍事件入口、根节点管理、事件分发 |
| **AccessibilityDelegateManager** | `service/AccessibilityDelegateManager.java` | Delegate 队列管理、引擎工厂、生命周期 |
| **KeepAliveEngine** & **具体引擎** | `engine/*.java` | 保活操作、窗口事件处理、电池优化适配 |

### 启动链路 (3 步)

```
1. HTTP: GET /testOppoKeepAlive
         ↓
2. AccessibilityDelegateManager.b()  创建引擎、注册 ListenWindow
         ↓
3. 后台线程初始化引擎，等待事件触发
```

### 事件处理链路 (4 步)

```
1. OS → MyAccessibilityService.onAccessibilityEvent()
         ↓
2. G(event)         更新根节点状态
         ↓
3. f0(event)        遍历 delegate 队列
         ↓
4. delegate.u()     事件回调到引擎处理
```

---

## 🔍 关键方法查询

### 我想... 快速查找这个方法

**启动引擎:**
```
→ MyAccessibilityService.java 行 1347: f0() 分发事件到 delegate
→ AccessibilityDelegateManager.java 行 336: b() 工厂方法
→ ApiRouter.java 行 312: /testOppoKeepAlive HTTP 端点
```

**获取当前状态:**
```
→ MyAccessibilityService.N() [行 226]  当前包名
→ MyAccessibilityService.Q() [行 255]  当前根 UiObject
→ MyAccessibilityService.P() [行 250]  获取 service 单例
```

**注册监听窗口:**
```
→ AccessibilityDelegateManager.t() [行 799]  注册 ListenWindow
→ AccessibilityDelegateManager.c() [行 423]  创建 delegate
→ AccessibilityHandler.listenWindow() [行 64]  HTTP 处理
```

**检查窗口匹配:**
```
→ AccessibilityDelegate.c() [行 326]  单个窗口匹配
→ AccessibilityDelegate.q() [行 834]  列表窗口匹配
→ ListenWindow.equals() [行 106]  匹配逻辑
```

---

## 📍 文件速查表

### 按功能快速定位

| 需求 | 文件 | 行号范围 |
|------|------|--------|
| 事件入口 | MyAccessibilityService.java | 1323-1365 |
| 根节点更新 | MyAccessibilityService.java | 544-717 |
| 事件分发 | MyAccessibilityService.java | 1077-1099 |
| 引擎启动 | AccessibilityDelegateManager.java | 336-415 |
| ListenWindow 注册 | AccessibilityDelegateManager.java | 799-822 |
| 事件回调 | AccessibilityDelegate.java | 960-978 |
| 电池优化处理 | KeepAliveEngine.java | 679-707 |
| HTTP API | AccessibilityHandler.java | 64-113 |
| HTTP 端点 | ApiRouter.java | 312-336 |

---

## 🎪 常见问题定位

### Q: 事件为什么没有传到我的 delegate?

**检查点:**
1. ListenWindow 的 eventTypes 是否包含该事件类型? 
   → ListenWindow.java 字段 `eventTypes`

2. Delegate 是否被激活?
   → AccessibilityDelegate.o() [行 963] 检查激活状态

3. 当前窗口是否匹配 ListenWindow?
   → AccessibilityDelegate.c() [行 326] 窗口匹配逻辑

4. 事件是否被过滤?
   → MyAccessibilityService.W() [行 896] 事件过滤

### Q: 如何添加新的 ListenWindow?

**方案:**
```
HTTP POST /listenWindow
   ↓
AccessibilityHandler.listenWindow() [行 64]
   ↓
service.c(window) [行 109]
   ↓
service.t(...) [行 799] 注册订阅
```

### Q: delegate 为什么没有收到根节点变化通知?

**检查点:**
1. 根节点是否确实变化了?
   → MyAccessibilityService.G() [行 544] 更新逻辑

2. Delegate 是否被添加到队列?
   → AccessibilityDelegateManager.a (delegate queue)

3. h0() / i0() 是否被调用?
   → MyAccessibilityService.h0() [行 1126]
   → MyAccessibilityService.i0() [行 1163]

---

## 🔗 关键字段映射

### Vendor 字段 → Readable 名称

| Vendor | Readable | 类型 | 位置 |
|--------|----------|------|------|
| f219p | p | AtomicReference<MyAccessibilityService> | MyAccessibilityService |
| f221s | s2 | AtomicReference<UiObject> | MyAccessibilityService |
| f222t | t2 | AtomicReference<AccessibilityNodeInfo> | MyAccessibilityService |
| f223u | u2 | AtomicReference<String> | MyAccessibilityService |
| f224v | v2 | AtomicReference<String> | MyAccessibilityService |

### 事件类型常数

| 常数 | 值 | 含义 |
|------|----|----|
| TYPE_WINDOW_STATE_CHANGED | 32 | 窗口状态变化 |
| TYPE_WINDOWS_CHANGED | 16384 | 窗口列表变化 |
| TYPE_VIEW_TEXT_CHANGED | 2048 | 文本变化 |
| Custom Event | 204832 | 厂商自定义 |

---

## 📊 工作流概览

### 启动流程

```
HTTP GET /testOppoKeepAlive (ApiRouter.java 行 312)
    ↓
MyAccessibilityService.P() ← 获取单例
    ↓
svc.j() ← 检查有无活跃 delegate
    ↓
svc.x() + svc.w() ← 清除旧 delegate
    ↓
svc.b(packageName) (AccessibilityDelegateManager.java 行 336)
    ├─ DeviceUtils.isOppoFamily() 检测品牌
    ├─ new OppoEngine() 创建引擎
    ├─ concurrentLinkedQueue.add(engine) 添加到队列
    ├─ t(OppoEngine.class.getName(), ListenWindows) 注册
    │   ├─ s(packageName) 注册 2048
    │   ├─ q(packageName) 注册 204832
    │   └─ r(uniqueId) 注册 ID
    └─ DelegateTaskLauncher.c(...) 后台初始化
```

### 事件流程

```
AccessibilityEvent (OS)
    ↓
onAccessibilityEvent() (行 1323)
    ├─ tryLock()
    ├─ U(event) ← 检查是否本服务
    ├─ G(event) (行 544)
    │   ├─ R() ← 获取根节点
    │   ├─ h0() / i0() ← 通知 delegate
    │   └─ H() ← 清缓存
    ├─ f0(event) (行 1347) ⭐ 核心分发
    │   ├─ W(event) ← 检查忽略
    │   ├─ 遍历 delegate queue (this.a)
    │   └─ if (delegate.o() && delegate.l().contains(eventType))
    │       └─ delegate.u(event, u2, v2) ⭐ 回调
    │           ├─ UiObject.createRoot()
    │           └─ DelegateTaskLauncher.c()
    ├─ b0(event) ← WebSocket 广播
    └─ c0(event) ← MiniCapture
```

---

## 💡 代码示例

### 启动 OPPO 引擎

```java
// ApiRouter.java 行 312-336
httpServer.get("/testOppoKeepAlive", (req, res) -> {
    MyAccessibilityService svc = MyAccessibilityService.P();
    if (svc == null) { return; }
    
    // 清除旧引擎
    if (svc.j()) {  // has delegate?
        svc.x();    // remove KeepAliveEngine
        svc.w();    // remove GrantPermissionDelegate
    }
    
    // 启动新引擎
    String pkg = MainApplication.getAppContext().getPackageName();
    svc.b(pkg);  // 核心启动方法
    
    HttpResponseHelper.ok(res, true);
});
```

### 事件分发链

```java
// MyAccessibilityService.java 行 1347
public final void f0(AccessibilityEvent event) {
    if (W(event)) return;  // 忽略检查
    
    ConcurrentLinkedQueue delegateQueue = this.a;
    Iterator it = delegateQueue.iterator();
    while (it.hasNext()) {
        AccessibilityDelegate delegate = (AccessibilityDelegate) it.next();
        if (delegate.o() && delegate.l().contains(event.getEventType())) {
            // ⭐ 核心回调
            delegate.u(event, u2.get(), v2.get());
        }
    }
    g0(event);  // 也分发到 g0 delegate
}
```

### ListenWindow 匹配逻辑

```java
// ListenWindow.java 行 106-142
@Override
public boolean equals(Object obj) {
    ListenWindow other = (ListenWindow) obj;
    
    // 都空 → 匹配
    if (empty(this.packageName) && empty(this.className)) return true;
    
    // 包空 → 仅比较类
    if (empty(this.packageName) || empty(other.packageName)) 
        return Objects.equals(this.className, other.className);
    
    // 类空 → 仅比较包
    if (empty(this.className) || empty(other.className))
        return Objects.equals(this.packageName, other.packageName);
    
    // 特殊情况: SoftInputWindow (仅包)
    if ("android.inputmethodservice.SoftInputWindow".equals(...))
        return Objects.equals(this.packageName, other.packageName);
    
    // HyperOS 兼容: android.view.View (仅包)
    if ("android.view.View".equals(...))
        return Objects.equals(this.packageName, other.packageName);
    
    // 正常: 包 && 类
    return Objects.equals(this.packageName, other.packageName)
        && Objects.equals(this.className, other.className);
}
```

---

## 📚 延伸阅读

- **详细分析**: 见 `KEEPALIVE_ANALYSIS.md` (24KB)
- **详细位置表**: 见 `KEY_LOCATIONS.txt` (19KB)
- **源代码**: 见 `/app/src/main/java/com/guard/wallet/`

---

生成时间: 2026-04-12 | 版本: v1.0
