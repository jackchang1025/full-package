# vendor-replica 保活 UI 自动化引擎启动链路分析

## 1. AccessibilityService 实现类

### 文件位置
```
/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/service/MyAccessibilityService.java
```

### 核心方法映射

| Vendor 方法 | Replica 方法 | 行号范围 | 功能说明 |
|-----------|-----------|--------|--------|
| (extends AccessibilityService) | extends AccessibilityDelegateManager (extends AccessibilityService) | 79 | 继承链：MyAccessibilityService → AccessibilityDelegateManager → AccessibilityService |
| onAccessibilityEvent(event) | onAccessibilityEvent(event) | 1323-1365 | **核心事件入口**，锁定+状态检查→G()→f0()→b0()→c0() |
| onServiceConnected() | onServiceConnected() | 1462-1475 | 服务连接初始化：r0()→j0() |

### onAccessibilityEvent() 关键流程 (行 1323-1365)
```java
public final void onAccessibilityEvent(AccessibilityEvent event) {
    ReentrantLock lock = this.l;  // line 1324
    if (!lock.tryLock()) { return; }
    
    // Stage 1: 获取/缓存 service 实例 (line 1330-1333)
    this.h.set(true);
    if (p.get() == null) { p.set(this); }
    
    // Stage 2: 检查是否忽略事件 - U() 检查本服务窗口 (line 1338)
    if (U(event)) { lock.unlock(); return; }
    
    // Stage 3: 电源优化器检查 (line 1342)
    if (com.guard.wallet.power.PowerSaveChecker.shouldKeepAlive()) {
        lock.unlock(); return;
    }
    
    // Stage 4: 处理事件分发链
    G(event);        // line 1346 - 更新根节点 & window state
    f0(event);       // line 1347 - **分发到所有 delegate 的 u() 方法**
    b0(event);       // line 1348 - 广播 WebSocket
    c0(event);       // line 1349 - MiniCapture 屏幕捕获
    
    // Stage 5: 后台线程池处理 (line 1351-1359)
    if (!X(event) && this.o != null) {
        AccessibilityEvent copy = ...;
        this.o.submit(new ScreenCaptureTask(this, copy, 1));
    }
    lock.unlock();
}
```

### 关键静态字段 (事件分发涉及)

| 字段 | Vendor | 类型 | 用途 |
|-----|--------|------|-----|
| p | f219p | AtomicReference<MyAccessibilityService> | 全局 service 单例 |
| s2 | f221s | AtomicReference<UiObject> | 当前根 UiObject |
| t2 | f222t | AtomicReference<AccessibilityNodeInfo> | 当前根节点 |
| u2 | f223u | AtomicReference<String> | 当前活跃包名 |
| v2 | f224v | AtomicReference<String> | 当前活跃窗口类名 |

### G() 方法 - 根节点更新 (行 544-717)
```java
public final void G(AccessibilityEvent event) {
    // 行 549-574: 事件类型检查，仅处理 32/16384/2048 等特定类型
    // 行 577-596: 获取根节点，比较是否变化
    // 行 598-625: 提取 root package/class，处理 TYPE_WINDOW_CONTENT_CHANGED 特殊情况
    // 行 659-683: 判断 package/window 是否变化
    // 行 686: H() - 清空 accessibility 缓存
    // 行 704-706: 调用 **h0() 和 i0()** 通知所有 delegate 根节点变化
}
```

### f0() 方法 - Delegate 事件分发 (行 1077-1099)
```java
public final void f0(AccessibilityEvent event) {
    // 行 1079: 检查是否忽略 (W() 过滤)
    // 行 1082-1093: 遍历 delegate queue (this.a)
    //   对每个 delegate:
    //     if (delegate.o() && delegate.l() && delegate.l().contains(eventType))
    //       **delegate.u(event, u2.get(), v2.get())** ← 核心事件回调
    // 行 1095: g0() - 也分发到 g0 delegate (UseDeviceCredentialDelegate)
}
```

---

## 2. 引擎的启动入口

### HTTP 测试端点：/testOppoKeepAlive (行 312-336)

**路径**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java` 行 312-336

```java
httpServer.get("/testOppoKeepAlive", (req, res) -> {
    // 行 316-319: 获取 service，校验非空
    com.guard.wallet.service.MyAccessibilityService svc = 
        com.guard.wallet.service.MyAccessibilityService.P();
    if (svc == null) { HttpResponseHelper.error(res, "..."); return; }
    
    // 行 321-326: 清除活跃 delegate (保活引擎)
    if (svc.j()) {  // j() - has any engine/delegate
        svc.x();    // x() - remove KeepAliveEngine
        svc.w();    // w() - remove GrantPermissionDelegate
        Thread.sleep(500);
    }
    
    // 行 328-330: **直接启动 OPPO 保活引擎**
    String pkg = com.guard.wallet.MainApplication.getAppContext().getPackageName();
    svc.b(pkg);  // **← 核心启动方法**
    HttpResponseHelper.ok(res, true);
});
```

### AccessibilityDelegateManager.b() - 引擎工厂方法

**路径**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/service/AccessibilityDelegateManager.java` 行 336-415

```java
public final void b(String str) {
    // 行 338-340: 清除已有的保活引擎
    if (g()) { x(); }
    
    // 行 341-353: OPPO 家族检测
    if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
        OppoEngine vVar = new OppoEngine();
        concurrentLinkedQueue.add(vVar);
        t(OppoEngine.class.getName(), OppoEngine.buildAllListenWindows());
        // 行 348: 分发 DelegateEventDispatcher 任务到线程池
        com.guard.wallet.thread.DelegateTaskLauncher.c(
            new DelegateEventDispatcher(vVar, str, 4), vVar.c);
        return;
    }
    
    // 类似分支: Xiaomi (355-365), Huawei (367-377), Vivo (379-389), 
    // Transsion (391-401), 默认 AOSP (403-411)
}
```

### 启动流程序列图
```
HTTP GET /testOppoKeepAlive
    ↓
ApiRouter.registerRoutes() [行 312]
    ↓
svc.b(packageName) [行 330]
    ↓
new OppoEngine()
    ↓
AccessibilityDelegateManager.add(engine) [行 345]
    ↓
AccessibilityDelegateManager.t(className, listenWindows) [行 346]
    ↓ (注册 eventTypes 订阅)
DelegateTaskLauncher.c(DelegateEventDispatcher, taskQueue) [行 348]
    ↓
在后台线程池异步初始化引擎
```

---

## 3. ListenWindow 的注册机制

### ListenWindow 数据结构

**文件**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/req/ListenWindow.java`

```java
public class ListenWindow implements Comparable<ListenWindow> {
    private String id;
    private String packageName;        // e.g. "com.miui.securitycenter"
    private String className;          // e.g. "android.app.Dialog"
    private List<CombineFilter> matchs;
    private List<CombineFilter> dismiss;
    private HashSet<Integer> eventTypes;  // e.g. {32, 16384, 2048}
    private List<EventSubscribe> eventSubscribes;
    private Integer listenType;
    private Integer orderNo;
    
    // 行 106-142: equals() - 包名/类名匹配逻辑
    // 包括特殊处理: SoftInputWindow, android.view.View (HyperOS fallback)
}
```

### XiaomiEngine 的 ListenWindow 注册

**文件**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java` 行 71-79

```java
public XiaomiEngine() {
    // 行 72: super(buildAllListenWindows(), "com.miui.securitycenter")
    // 行 73: this.y = new AtomicBoolean(false)
    // 行 75: 调度 100s 定时任务
    super.p.schedule(
        new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 0), 
        100L, TimeUnit.SECONDS);
}
```

### ListenWindow 过滤器构建示例

**Builder 例子**:
```java
public static CombineFilter buildPowerConsumeFilter() {  // 行 84-91
    CombineFilter f = new CombineFilter();
    StringCondition sc = FilterHelper.addCondition(f,
        FilterHelper.initFilter(f, "className", "android.widget.TextView"), 
        "text");
    sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue(
        "MIUI_APP_POWER_CONSUME_TEXT"));
    f.getStringConditions().add(sc);
    return f;
}
```

### AccessibilityDelegateManager.t() - 注册监听窗口

**文件**: AccessibilityDelegateManager.java 行 799-822

```java
public final void t(String str, List list) {  // str = delegateClassName, list = listenWindows
    if (list != null && !list.isEmpty()) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListenWindow listenWindow = (ListenWindow) it.next();
            if (listenWindow != null) {
                // 行 809: 检查 eventTypes 包含 2048 (TYPE_VIEW_TEXT_CHANGED)
                if (...contains(2048)) { s(listenWindow.getPackageName()); }
                
                // 行 812: 检查 eventTypes 包含 204832 (custom event)
                if (...contains(j)) { q(listenWindow.getPackageName()); }
                
                // 行 815: 注册 listenWindow 唯一 ID
                r(com.guard.wallet.utils.SystemHelper.v0(
                    listenWindow.getPackageName(), 
                    listenWindow.getClassName(), str));
            }
        }
    }
}
```

---

## 4. XiaomiEngine 初始化详解

### 文件
```
/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java
```

### l0() 等核心方法

**注**: XiaomiEngine 不存在 l0() 方法，这是 MyAccessibilityService 的方法。

### XiaomiEngine 关键字段和构造

| 字段 | Vendor | 类型 | 作用 |
|-----|--------|------|-----|
| r | 持有 keep-alive phase | AtomicReference | 记录保活阶段状态 |
| s | 主应用自启动标志 | AtomicBoolean | 标记主应用是否启用自动启动 |
| t | 备用应用自启动标志 | AtomicBoolean | 标记备用应用自启动 |
| y | 处理中标志 | AtomicBoolean | 防止并发处理 |

### 定时任务 (XiaomiDelegateTask)

```java
super.p.schedule(
    new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 0), 
    100L,              // 初始延迟 100 秒
    TimeUnit.SECONDS);
```

---

## 5. 引擎的 u() 方法 - 事件回调入口

### AccessibilityDelegate.u() 基类实现

**文件**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/delegate/AccessibilityDelegate.java` 行 960-978

```java
public void u(AccessibilityEvent accessibilityEvent, String str, String str2) {
    // str = packageName, str2 = windowClassName
    if (accessibilityEvent != null) {
        try {
            if (o()) {  // o() - 检查 delegate 是否激活
                // 行 964: 从 event 源创建 UiObject
                UiObject createRoot = UiObject.createRoot(
                    accessibilityEvent.getSource(), true);
                
                // 行 967: 设置唯一 ID (delegateId)
                String str3 = this.c;
                if (createRoot != null) { createRoot.setUniqueId(str3); }
                
                // 行 969: 提取 beforeText (用于文本变化 diff)
                String beforeText = (accessibilityEvent.getEventType() == 16 
                    && accessibilityEvent.getBeforeText() != null)
                    ? accessibilityEvent.getBeforeText().toString()
                    : null;
                
                // 行 972: **分发到 DelegateEventDispatcher 处理**
                com.guard.wallet.thread.DelegateTaskLauncher.c(
                    new DelegateEventDispatcher(this, 
                        new ListenWindowState(
                            createRoot, 
                            accessibilityEvent.getEventType(), 
                            str,        // packageName
                            str2,       // windowClassName
                            beforeText), 
                        0),  // 模式 0: 事件处理
                    str3);
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate:onAccessibilityEvent", e2);
        }
    }
}
```

### KeepAliveEngine.u() 重写

**文件**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/engine/KeepAliveEngine.java` 行 679-707

```java
@Override
public void u(AccessibilityEvent event, String packageName, String className) {
    try {
        // 行 682: 调用父类 u()
        super.u(event, packageName, className);
        
        // 行 684-690: 检查是否在电池优化对话框窗口
        boolean inBatteryDialog = false;
        try {
            if (this.q(Collections.singletonList(buildBatteryDialogListenWindow()))) {
                Log.d("o.c", "已进入是否允许忽略电池优化窗口");
                inBatteryDialog = true;
            }
        } catch (Exception ex) { AppUtils.s("o.c", ex); }
        
        if (!inBatteryDialog) { return; }
        
        // 行 698: 将电池优化对话框处理任务加入队列
        ConcurrentLinkedQueue taskQueue = this.n;
        if (!taskQueue.contains("keepInBatteryUnRestricted")) {
            taskQueue.add("keepInBatteryUnRestricted");
            AccessibilityDelegate.a batteryTask = 
                new AccessibilityDelegate.a(this, 0);
            com.guard.wallet.thread.DelegateTaskLauncher.c(
                batteryTask, super.c);
        }
    } catch (Exception ex2) {
        AppUtils.s("o.c", ex2);
    }
}
```

---

## 6. ApiRouter 与 HTTP 端点

### 文件
```
/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java
```

### 主要 HTTP 端点 (保活相关)

| 路由 | 方法 | 行号 | 功能 |
|-----|------|------|------|
| /testOppoKeepAlive | GET | 312-336 | **测试端点**：清除并直接启动 OPPO 引擎 |
| /refreshActiveWindow | GET | [AccessibilityHandler] | 刷新根节点，调用 l0(true) |
| /listenWindow | POST | [AccessibilityHandler] | 注册监听窗口 |
| /readScreenWindow | GET | [AccessibilityHandler] | 读取全屏窗口信息 (k0()) |
| /requestLocalKeepAlive | GET | [UnlockHandler] | 请求本地保活 |

### POST 路由分发表

**文件**: ApiRouter.java 行 772-960

```java
static {
    Gson gson = new Gson();
    
    // /listenWindow 路由内部处理 (行 852)
    INTERNAL_ROUTES.put("/listenWindow", 
        (p, r) -> AccessibilityHandler.listenWindow(p, r));
}
```

### 无障碍服务相关路由

**文件**: `/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/server/handler/AccessibilityHandler.java`

#### /listenWindow (行 64-113)
```java
public static void listenWindow(Multimap params, AsyncHttpServerResponse response) {
    // 行 65: 检查无障碍服务是否可用
    if (!HttpResponseHelper.requireAccessibility(response)) { return; }
    
    // 行 70-98: 解析请求参数 (packageName, className, eventTypes 等)
    ListenWindow window = new ListenWindow();
    window.setPackageName(params.getString("packageName"));
    window.setClassName(params.getString("className"));
    // ... 解析 eventTypes, listenType, id 等
    
    // 行 100-111: 创建或更新 delegate
    MyAccessibilityService service = MyAccessibilityService.P();
    AccessibilityDelegate existing = HttpResponseHelper.getDelegate(delegateId);
    if (existing != null) {
        existing.d.add(window);
        service.t(existing.getClass().getName(), 
            Collections.singletonList(window));
    } else {
        delegate = service.c(window);  // 创建新 delegate
    }
}
```

#### /refreshActiveWindow (行 30-40)
```java
public static void refreshActiveWindow(AsyncHttpServerResponse response) {
    // 行 37: 清空 accessibility 缓存
    service.H(true, true);
    
    // 行 38: 刷新根节点并通知所有 delegate
    HttpResponseHelper.ok(response, service.l0(true));
}
```

---

## 7. 完整事件处理链路 (从 AccessibilityEvent 到 engine.u())

### 序列图

```
AccessibilityEvent (系统事件)
    ↓ [OS Level]
MyAccessibilityService.onAccessibilityEvent() [行 1323]
    ↓
ReentrantLock.tryLock() [行 1324-1325]
    ↓ (检查忽略条件)
U(event) [行 1338] → 本服务窗口检查
PowerSaveChecker.shouldKeepAlive() [行 1342]
    ↓
G(event) [行 1346] - 更新根节点、包名、窗口类名
    ├─ RootInActiveWindowResult R() [行 577]
    ├─ curRoot = getRootInActiveWindow() / getWindows()
    ├─ 判断 package/window 是否变化
    ├─ H() 清空 accessibility cache
    └─ h0() / i0() 通知所有 delegate
        ├─ delegate.c(pkgName, className) [检查匹配]
        ├─ delegate.w(true) [激活 delegate]
        └─ delegate.v() [更新 activeRoot 等状态]
    
    ↓
f0(event) [行 1347] - **核心事件分发**
    ├─ W(event) [行 1079] - 检查是否忽略
    ├─ 遍历 delegate queue this.a [行 1082-1093]
    │   └─ if (delegate.o() && delegate.l().contains(eventType))
    │        ├─ **delegate.u(event, u2.get(), v2.get())** [核心回调]
    │        │   ↓
    │        │   AccessibilityDelegate.u() [行 960-978]
    │        │   ├─ o() - 检查激活状态
    │        │   ├─ UiObject.createRoot(event.getSource())
    │        │   ├─ ListenWindowState(createRoot, eventType, pkg, cls, beforeText)
    │        │   └─ DelegateTaskLauncher.c(DelegateEventDispatcher(...))
    │        │       ↓
    │        │       DelegateEventDispatcher.run() [后台线程]
    │        │       ├─ delegate.p(listenWindowState) [处理窗口]
    │        │       └─ (对引擎) KeepAliveEngine.u() override
    │        │           ├─ super.u()
    │        │           └─ q(batteryDialogListenWindow) [检查电池对话框]
    │        │               ├─ 若匹配，加入 taskQueue
    │        │               └─ 分发电池优化处理任务
    │        │
    │        └─ g0(event) [行 1095] - UseDeviceCredentialDelegate
    │
    ├─ b0(event) [行 1348] - WebSocket 广播
    └─ c0(event) [行 1349] - MiniCapture 屏幕捕获
```

---

## 8. 关键代码位置汇总

### MyAccessibilityService 核心方法

| 方法 | 行号 | 功能 |
|------|------|------|
| onAccessibilityEvent() | 1323-1365 | **事件入口，分发链顶点** |
| G() | 544-717 | 更新根节点状态 |
| H() | 724-761 | 清除 accessibility cache |
| f0() | 1077-1099 | **Delegate 事件分发核心** |
| g0() | 1104-1120 | g0 delegate 事件分发 |
| h0() | 1126-1158 | Delegate 根节点变化通知 |
| i0() | 1163-1181 | g0 delegate 根节点通知 |
| j0() | 1187-1213 | 服务初始化 |
| l0() | 1255-1318 | 刷新根节点并通知 |
| R() | 788-822 | 获取活跃窗口根节点 |
| P() | 250-253 | 获取 service 单例 |
| Q() | 255-258 | 获取当前根 UiObject |
| N() | 226-228 | 获取当前包名 |

### AccessibilityDelegateManager 核心方法

| 方法 | 行号 | 功能 |
|------|------|------|
| b() | 336-415 | **引擎工厂方法** |
| c() | 423-479 | ListenWindow → delegate 映射或创建 |
| d() | 485-514 | 创建新 delegate |
| t() | 799-822 | **ListenWindow 注册** |
| g() | 555-572 | has KeepAliveEngine? |
| x() | 866-876 | remove KeepAliveEngine |
| D() | 174-293 | 关闭所有 delegate |

### AccessibilityDelegate 核心方法

| 方法 | 行号 | 功能 |
|------|------|------|
| u() | 960-978 | **事件回调入口** |
| c() | 326-336 | 窗口匹配检查 |
| q() | 834-... | 列表窗口匹配 |
| v() | 986-... | 根节点更新通知 |
| o() | ... | delegate 激活状态 |

### KeepAliveEngine 核心方法

| 方法 | 行号 | 功能 |
|------|------|------|
| u() [override] | 679-707 | 事件分发 + 电池对话框检测 |
| Z() | 652 | 抽象清理方法 |
| d() | 660-669 | 销毁引擎 |

### ApiRouter HTTP 端点

| 路由 | 行号 | 文件 |
|-----|------|------|
| /testOppoKeepAlive | 312-336 | ApiRouter.java |
| 其他 /listenWindow 等 | 507-518 | ApiRouter.java |

### AccessibilityHandler HTTP 处理

| 路由 | 行号 | 功能 |
|------|------|------|
| /listenWindow | 64-113 | 注册监听窗口 |
| /refreshActiveWindow | 30-40 | 刷新根节点 |
| /readScreenWindow | 117-125 | 读取屏幕 UI 树 |
| /removeDelegate | 44-60 | 删除 delegate |

---

## 9. 保活引擎启动完整链路

```
[HTTP REST API] GET /testOppoKeepAlive
    ↓
ApiRouter.registerRoutes() [行 312-336]
    └─ svc.b(packageName)  // 行 330
        ↓
        AccessibilityDelegateManager.b() [行 336]
            ├─ if (isOppoFamily()) → new OppoEngine()
            ├─ concurrentLinkedQueue.add(engine)
            ├─ t(OppoEngine.class.getName(), buildAllListenWindows())
            │   ↓
            │   t() [行 799-822] 
            │   ├─ s(packageName) - 注册 2048 事件
            │   ├─ q(packageName) - 注册 204832 事件
            │   └─ r(listenWindowUniqueId) - 注册 ID
            │
            └─ DelegateTaskLauncher.c(DelegateEventDispatcher(...))
                ↓
                后台线程池初始化引擎
                └─ XiaomiEngine 构造
                   └─ super.p.schedule(XiaomiDelegateTask(), 100s)
                      ↓
                      定时任务定期检查保活条件
```

---

## 10. 关键概念映射

### 无障碍事件类型

| EventType | 值 | 含义 | 监听场景 |
|-----------|----|----|--------|
| TYPE_WINDOW_STATE_CHANGED | 32 | 窗口状态变化 | 应用启动/切换 |
| TYPE_WINDOWS_CHANGED | 16384 | 窗口列表变化 | 多窗口场景 |
| TYPE_VIEW_TEXT_CHANGED | 2048 | 文本变化 | 输入框/标签变化 |
| Custom (204832) | 204832 | 自定义事件 | vendor 专用 |

### Delegate 队列

```
AccessibilityDelegateManager.a 
    → ConcurrentLinkedQueue<AccessibilityDelegate>
    → 包含: OppoEngine, XiaomiEngine, GrantPermissionDelegate, 等...
```

### ListenWindow 匹配机制

```
ListenWindow:
    packageName = "com.miui.securitycenter"
    className = "android.app.Dialog"
    eventTypes = {32, 16384, 2048}
    
匹配条件 (equals):
    1. 若包/类都为空 → 匹配
    2. 若一侧包为空 → 仅比较类名
    3. 若一侧类为空 → 仅比较包名
    4. 特殊：SoftInputWindow (仅比较包)
    5. 特殊：android.view.View (仅比较包 - HyperOS 兼容)
    6. 正常：包 && 类 都匹配
```

### Delegate 激活机制

```
根节点变化 (G() → h0())
    ↓
delegate.c(pkgName, windowClassName)  // 检查是否匹配
    ↓
if (匹配) 
    delegate.w(true)  // 激活
    delegate.v(root)  // 更新状态
else 
    delegate.w(false)  // 停用
    
事件来临 (f0())
    ↓
if (delegate.o())  // 若已激活
    delegate.u(event)  // 分发事件
```

---

## 11. 关键测试点

### 测试端点使用

```bash
# 清除并启动 OPPO 保活引擎
curl http://localhost:7910/testOppoKeepAlive

# 手动注册监听窗口
curl -X POST http://localhost:7910/listenWindow \
  -d '{"packageName":"com.example","className":"com.example.Activity"}'

# 刷新根节点
curl http://localhost:7910/refreshActiveWindow

# 读取屏幕 UI 树
curl http://localhost:7910/readScreenWindow
```

---

## 12. 完整初始化流程

### onServiceConnected() [行 1462-1475]

```
onServiceConnected()
    ├─ r0() [行 1557] - 配置无障碍服务参数
    │   ├─ getServiceInfo()
    │   ├─ 设置 eventTypes = 8419391 (0x80783f)
    │   ├─ 设置 feedbackType = -1
    │   ├─ 设置 flags = 91
    │   └─ setCacheEnabled(true) [API 33+]
    │
    └─ j0() [行 1187] - 初始化线程池和 delegate
        ├─ this.o = ThreadPoolExecutor(0, 20, 50ms, SynchronousQueue)
        ├─ p.set(this) - 设置单例
        ├─ 检查首次开启无障碍 (isFirstOpenAccessibility)
        ├─ p0() - 发送 ACCESSIBILITY_CONTAINER opened 事件
        ├─ d0() - 加载本地监听窗口 (listenWindows.json)
        ├─ HttpApiManager.syncListenWindows() - 同步远程窗口
        └─ MainApplication.offerAccessibilityEvent(32) - 上报窗口变化事件
```

---

