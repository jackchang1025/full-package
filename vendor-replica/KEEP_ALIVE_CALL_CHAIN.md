# 保活引擎完整调用链 — 详细追踪文档

## 1. HTTP API 触发入口

**文件**: `/app/src/main/java/com/guard/wallet/server/ApiRouter.java` (第 312-336 行)

```java
httpServer.get("/testOppoKeepAlive", (req, res) -> {
    try {
        android.util.Log.e("KeepAliveDebug", "testOppoKeepAlive called");
        com.guard.wallet.service.MyAccessibilityService svc = 
            com.guard.wallet.service.MyAccessibilityService.P();
        if (svc == null) {
            HttpResponseHelper.error(res, "accessibility service is null");
            return;
        }
        // 清除所有活跃 delegate
        if (svc.j()) {
            android.util.Log.e("KeepAliveDebug", "clearing active delegates");
            svc.x();  // remove KeepAliveEngine
            svc.w();  // remove GrantPermissionDelegate
            try { Thread.sleep(500); } catch (Exception ignored) {}
        }
        // 直接调用 b(str) 启动保活引擎
        String pkg = com.guard.wallet.MainApplication.getAppContext().getPackageName();
        android.util.Log.e("KeepAliveDebug", "starting OppoEngine via b() pkg=" + pkg);
        svc.b(pkg);  // ← HTTP API → 引擎初始化入口
        HttpResponseHelper.ok(res, true);
    } catch (Exception e) {
        android.util.Log.e("KeepAliveDebug", "testOppoKeepAlive error", e);
        HttpResponseHelper.error(res, e.getMessage());
    }
});
```

**关键点**:
- HTTP 端口 7910
- 路由: `/testOppoKeepAlive` 
- 调用 `MyAccessibilityService.P()` 获取单例
- 调用 `svc.b(packageName)` 启动保活引擎

---

## 2. 引擎初始化 — MyAccessibilityService.b()

**继承链**:
- `MyAccessibilityService` extends `AccessibilityDelegateManager`
- `b()` 方法定义在 `AccessibilityDelegateManager` (第 336 行)

**文件**: `/app/src/main/java/com/guard/wallet/service/AccessibilityDelegateManager.java` (第 336-415 行)

```java
public final void b(String str) {
    try {
        if (g()) {
            x();  // 清除 KeepAliveEngine
        }
        boolean isOppo = com.guard.wallet.utils.DeviceUtils.isOppoFamily();
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        
        if (isOppo) {
            // OPPO 保活引擎初始化
            OppoEngine vVar = new OppoEngine();
            concurrentLinkedQueue.add(vVar);  // 添加到 delegate 队列
            t(OppoEngine.class.getName(), OppoEngine.buildAllListenWindows());
            try {
                // 启动事件分发器线程
                com.guard.wallet.thread.DelegateTaskLauncher.c(
                    new DelegateEventDispatcher(vVar, str, 4), vVar.c);
                return;
            } catch (Exception e2) {
                AppUtils.s("OppoEngine", e2);
                return;
            }
        }
        // 其他厂商 (小米、华为、Vivo、传音等)...
    } catch (Exception e8) {
        AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
    }
}
```

**关键步骤**:
1. 清除现有 KeepAliveEngine (via `x()`)
2. 检测设备厂商 (OPPO/Xiaomi/Huawei/Vivo/Tecno/AOSP)
3. 创建对应厂商的引擎实例 (如 `new OppoEngine()`)
4. 向 delegate 队列添加引擎
5. 注册 ListenWindows (via `t()`)
6. 启动事件分发器线程 (via `DelegateTaskLauncher.c()`)

---

## 3. ListenWindow 注册机制

**文件**: `/app/src/main/java/com/guard/wallet/engine/OppoEngine.java`

### 3.1 静态 ListenWindow 构建器

```java
// OPPO/Realme/OnePlus 保活引擎的 ListenWindow 列表
public static final LinkedList buildAllListenWindows() {
    LinkedList windows = new LinkedList();
    
    // 应用详情窗口 (com.android.settings)
    windows.add(buildAppDetailListenWindow("Self"));  // 本应用
    windows.add(buildAppDetailListenWindow("Target"));
    
    // 电池管理窗口 (com.oplus.battery)
    windows.add(buildPowerControlListenWindow());
    windows.add(buildBatteryFrameLayoutListenWindow());
    windows.add(buildStartupListenWindow());
    
    // 电源控制对话框 (com.coloros.oppoguardelf)
    windows.add(buildGuardElfPowerControlListenWindow());
    
    // appcompat 对话框 (确认按钮)
    windows.add(buildAndroidXDialogListenWindow());
    windows.add(buildBatteryNullClassListenWindow());
    windows.add(buildCouiDialogListenWindow());
    
    // 权限对话框...
    
    return windows;
}
```

### 3.2 ListenWindow 定义示例

```java
public static ListenWindow buildPowerControlListenWindow() {
    ListenWindow lw = new ListenWindow(
        "com.oplus.battery",
        "com.oplus.powermanager.fuelgaue.PowerControlActivity");
    FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
    return lw;
}
```

**ListenWindow 属性**:
- `packageName`: "com.oplus.battery"
- `className`: "com.oplus.powermanager.fuelgaue.PowerControlActivity"
- `eventTypes`: [32 (TYPE_WINDOW_STATE_CHANGED), 16384 (TYPE_WINDOWS_CHANGED)]

---

## 4. onServiceConnected 初始化

**文件**: `/app/src/main/java/com/guard/wallet/service/MyAccessibilityService.java` (第 1462-1475 行)

```java
@Override
public final void onServiceConnected() {
    super.onServiceConnected();
    Log.d(TAG, "=== onServiceConnected 开始 ===");
    try {
        r0();  // 配置 AccessibilityServiceInfo (eventTypes, flags 等)
        Log.d(TAG, "r0() 完成");
        j0();  // ← 关键初始化
        Log.d(TAG, "j0() 完成");
    } catch (Exception ex) {
        Log.e(TAG, "onServiceConnected 异常", ex);
        AppUtils.s(TAG, ex);
    }
}
```

### 4.1 j0() 初始化详情

```java
public final void j0() {
    try {
        r2.set(false);
        // 创建事件处理线程池
        this.o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
        p.set(this);  // 设置单例引用
        
        // 处理屏幕返回...
        if (!com.guard.wallet.utils.SystemHelper.p0() && 
            com.guard.wallet.utils.SharedPrefsManager.q()) {
            com.guard.wallet.utils.SystemHelper.F0(1);
            com.guard.wallet.utils.SystemHelper.T0(5);
        }
        
        p0();  // 发送 ACCESSIBILITY_CONTAINER 打开事件
        
        // 加载本地 ListenWindow (listenWindows.json)
        if (d0() <= 2) {
            com.guard.wallet.http.HttpApiManager.syncListenWindows();
        }
        
        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerAccessibilityEvent(32);
        }
    } catch (Exception ex) {
        AppUtils.s(TAG, ex);
    }
}
```

---

## 5. 无障碍事件回调 — onAccessibilityEvent()

**文件**: `/app/src/main/java/com/guard/wallet/service/MyAccessibilityService.java` (第 1323-1365 行)

```java
@Override
public final void onAccessibilityEvent(AccessibilityEvent event) {
    ReentrantLock lock = this.l;
    if (!lock.tryLock()) {
        Log.e(TAG, "onAccessibilityEvent 事件被忽略");
        return;
    }
    try {
        this.h.set(true);
        if (p.get() == null) {
            p.set(this);
        }
    } catch (Exception ex) {
        AppUtils.s(TAG, ex);
        Log.e(TAG, "onAccessibilityEvent 出错");
    }
    
    if (U(event)) {  // 检查是否为自身服务窗口
        lock.unlock();
        return;
    }
    
    if (com.guard.wallet.power.PowerSaveChecker.shouldKeepAlive()) {
        lock.unlock();
        return;
    }
    
    G(event);     // ← 更新根节点和窗口信息
    f0(event);    // ← 分发事件给所有 delegate
    b0(event);    // 广播到 WebSocket
    c0(event);    // 处理 MiniCapture 事件
    
    try {
        if (!X(event) && this.o != null) {
            AccessibilityEvent copy;
            if (Build.VERSION.SDK_INT >= 30) {
                copy = com.guard.wallet.infra.AccessibilityCompat.copyEvent(event);
            } else {
                copy = AccessibilityEvent.obtain(event);
            }
            this.o.submit(new ScreenCaptureTask(this, copy, 1));
        }
    } catch (Exception ex) {
        AppUtils.s(TAG, ex);
    }
    
    lock.unlock();
}
```

### 5.1 f0() — 事件分发给 Delegate

```java
public final void f0(AccessibilityEvent event) {
    try {
        if (this.n.get() || W(event)) {
            return;
        }
        ConcurrentLinkedQueue delegateQueue = this.a;
        
        if (!delegateQueue.isEmpty()) {
            Iterator it = delegateQueue.iterator();
            while (it.hasNext()) {
                AccessibilityDelegate delegate = (AccessibilityDelegate) it.next();
                if (delegate != null && delegate.o() && delegate.l() != null
                        && !delegate.l().isEmpty()
                        && delegate.l().contains(Integer.valueOf(event.getEventType()))) {
                    // ← 调用 delegate.u() 方法处理事件
                    delegate.u(event, u2.get(), v2.get());
                }
            }
        }
        g0(event);  // 处理全局 credential delegate
    } catch (Exception ex) {
        AppUtils.s("noticeAccessibilityEvent", ex);
    }
}
```

### 5.2 G() — 窗口状态变化处理

```java
public final void G(AccessibilityEvent event) {
    if (event == null) return;
    try {
        int eventType = event.getEventType();
        
        // 获取当前活动窗口根节点
        RootInActiveWindowResult rootResult = this.R();
        AccessibilityNodeInfo curRoot = rootResult.getCurRoot();
        
        if (curRoot == null) return;
        
        // 提取包名和类名
        String rootPkg = curRoot.getPackageName().toString();
        String rootClass = curRoot.getClassName().toString();
        String windowTitle = this.T();
        
        // 检测包名或窗口类变化
        boolean packageEquals = Objects.equals(u2.get(), rootPkg);
        boolean packageChanged;
        boolean windowChanged;
        
        if (!packageEquals) {
            // 包名变化 → 通知所有 delegate
            u2.set(rootPkg);
            v2.set(rootClass);
            w2.set(windowTitle);
            packageChanged = true;
            windowChanged = true;
        } else {
            // 同包名下窗口类变化 → 仅通知有 listenWindow 的 delegate
            windowChanged = false;
            packageChanged = false;
        }
        
        // 清除缓存
        H(packageChanged, windowChanged);
        
        // 更新状态
        UiObject newRoot = UiObject.createRoot(curRoot);
        t2.set(curRoot);
        s2.set(newRoot);
        
        // ← 通知 delegate 根节点变化
        boolean delegatesNotified = this.h0(u2.get(), v2.get(),
                w2.get(), rootResult.isComplete());
        
    } catch (Exception ex) {
        AppUtils.s("changeRootInActiveWindow", ex);
    }
}
```

---

## 6. DelegateEventDispatcher — 事件分发器

**文件**: `/app/src/main/java/com/guard/wallet/delegate/task/DelegateEventDispatcher.java`

### 6.1 事件分发 switch-case

```java
@Override
public final void run() {
    String str;
    long nanoTime;
    switch (this.a) {
        case 0:
            /* AccessibilityDelegate 遍历监听窗口 */
            com.guard.wallet.delegate.AccessibilityDelegate eVar = 
                (com.guard.wallet.delegate.AccessibilityDelegate) this.c;
            com.guard.wallet.delegate.ListenWindowState j0Var = 
                (com.guard.wallet.delegate.ListenWindowState) this.b;
            
            ConcurrentLinkedQueue concurrentLinkedQueue = eVar.d;
            if (concurrentLinkedQueue.isEmpty() || j0Var == null) {
                return;
            }
            
            // 遍历所有 ListenWindow
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                ListenWindow listenWindow = (ListenWindow) it.next();
                
                // 检查事件类型是否匹配
                if (listenWindow != null && 
                    listenWindow.getEventTypes() != null && 
                    !listenWindow.getEventTypes().isEmpty() &&
                    listenWindow.getEventTypes().contains(
                        Integer.valueOf(j0Var.b)) &&  // eventType 匹配
                    listenWindow.equals(new ListenWindow(j0Var.c, j0Var.d)) &&
                    eVar.p(listenWindow, j0Var.a)) {  // 内容过滤器匹配
                    
                    eVar.e(listenWindow, j0Var);  // ← 执行 ListenWindow 动作
                }
            }
            return;
            
        case 4:
            /* OppoEngine — 启动应用详情 */
            com.guard.wallet.engine.OppoEngine vVar = 
                (com.guard.wallet.engine.OppoEngine) this.c;
            String str4 = (String) this.b;
            int i5 = com.guard.wallet.engine.OppoEngine.v;
            vVar.getClass();
            
            try {
                if (EngineHelper.cY()) {
                    com.guard.wallet.utils.SystemHelper.T0(20);
                }
                
                // 设置保活状态
                vVar.r.set(Objects.equals(str4, "com.google.guard") ? 
                    com.guard.wallet.delegate.ScreenCaptureManager.e.d : 
                    com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                
                // ← 启动应用详情页面 (触发 ListenWindow 回调)
                if (com.guard.wallet.utils.SystemHelper.Z0(str4)) {
                    Log.e("o.v", str4.concat(" 启动成功"));
                    return;
                } else {
                    Log.e("o.v", str4.concat(" 启动失败"));
                    return;
                }
            } catch (Exception e6) {
                AppUtils.s("o.v", e6);
                return;
            }
        
        // ... 其他厂商 ...
    }
}
```

---

## 7. AccessibilityDelegate.u() — 事件回调

**文件**: `/app/src/main/java/com/guard/wallet/delegate/AccessibilityDelegate.java` (第 960-978 行)

```java
public void u(AccessibilityEvent accessibilityEvent, String str, String str2) {
    if (accessibilityEvent != null) {
        try {
            if (o()) {  // 检查 delegate 是否活跃
                UiObject createRoot = UiObject.createRoot(
                    accessibilityEvent.getSource(), true);
                String str3 = this.c;
                if (createRoot != null) {
                    createRoot.setUniqueId(str3);
                }
                
                String beforeText = (accessibilityEvent.getEventType() == 16 && 
                                    accessibilityEvent.getBeforeText() != null)
                        ? accessibilityEvent.getBeforeText().toString()
                        : null;
                
                // ← 分发到事件队列
                com.guard.wallet.thread.DelegateTaskLauncher.c(
                    new com.guard.wallet.delegate.task.DelegateEventDispatcher(
                        this, 
                        new ListenWindowState(createRoot, 
                                            accessibilityEvent.getEventType(), 
                                            str,   // packageName
                                            str2,  // className
                                            beforeText), 
                        0),  // ← case 0: 遍历 ListenWindow
                    str3);
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate:onAccessibilityEvent", e2);
        }
    }
}
```

**关键**:
- `this.c` 是 delegate ID
- `accessibilityEvent.getSource()` 是事件发生的源节点
- 分发到 `DelegateEventDispatcher(case=0)` 以遍历 ListenWindow

---

## 8. 完整调用链总结

```
HTTP GET /testOppoKeepAlive
    ↓
MyAccessibilityService.P()  [单例获取]
    ↓
MyAccessibilityService.b(packageName)  [引擎初始化]
    ↓
    ├─ OppoEngine() constructor
    │   ├─ buildAllListenWindows()  [创建 ListenWindow 列表]
    │   └─ MediaProjectionTask 定时任务
    │
    ├─ t(OppoEngine.class.getName(), listenWindows)  [注册 ListenWindow]
    │   └─ AccessibilityDelegateManager.t()
    │       └─ delegate 队列添加 ListenWindow
    │
    └─ DelegateTaskLauncher.c(DelegateEventDispatcher, ...)  [启动事件分发线程]
        └─ DelegateEventDispatcher.run() [case 4: OppoEngine 启动器]
            └─ SystemHelper.Z0(packageName)  [启动应用详情]
                └─ triggerAppDetailIntent()
                    └─ 触发 AccessibilityEvent

            ================

                        onAccessibilityEvent()  [无障碍事件回调]
                            ↓
                        G(event)  [更新根节点和窗口]
                            ├─ R() → getRootInActiveWindow()
                            ├─ 提取 packageName, className
                            └─ h0(pkg, class, title, ...)  [通知 delegate 根节点变化]
                                ↓
                        f0(event)  [分发事件给 delegate]
                            ↓
                        delegate.u(event, pkg, class)  [事件回调]
                            ↓
                        DelegateEventDispatcher.run() [case 0]
                            ↓
                        遍历 ListenWindow
                            ├─ 检查 packageName 匹配
                            ├─ 检查 className 匹配
                            ├─ 检查 eventType 匹配 (32, 16384)
                            ├─ 检查内容过滤器匹配
                            └─ delegate.e(listenWindow, state)  [执行 ListenWindow 动作]
                                ↓
                        ListenWindowState
                            ├─ eventType: 32/16384
                            ├─ packageName: "com.oplus.battery"
                            ├─ className: "PowerControlActivity"
                            └─ sourceNode: AccessibilityNodeInfo
```

---

## 9. 关键类和字段映射

### MyAccessibilityService

| 字段 | 含义 |
|------|------|
| `p` | 服务单例引用 |
| `u2` | 当前活跃包名 |
| `v2` | 当前活跃窗口类名 |
| `s2` | 当前根节点 UiObject |
| `t2` | 当前根节点 AccessibilityNodeInfo |
| `a` | delegate 队列 (ConcurrentLinkedQueue) |
| `o` | 事件处理线程池 |
| `l` | 可重入锁 (onAccessibilityEvent) |

### AccessibilityDelegate

| 字段 | 含义 |
|------|------|
| `a` | 包名 (String) |
| `c` | delegate ID |
| `d` | ListenWindow 队列 |
| `h` | 活跃根节点 UiObject |
| `i` | rootReady 标志 |
| `j/k/l` | 包名/类名/窗口标题缓存 |

### KeepAliveEngine

| 字段 | 含义 |
|------|------|
| `n` | 任务队列 (ConcurrentLinkedQueue) |
| `o` | 可重入锁 |
| `p` | 调度执行器 |
| `q` | 终止标志 |
| `r` | 保活状态 (UNKNOWN/MAIN/BACKUP) |

---

## 10. 关键方法速查

| 方法 | 类 | 作用 |
|------|-----|------|
| `b(pkg)` | AccessibilityDelegateManager | 启动保活引擎 |
| `onServiceConnected()` | MyAccessibilityService | 无障碍服务连接 |
| `j0()` | MyAccessibilityService | 服务初始化 |
| `onAccessibilityEvent()` | MyAccessibilityService | 无障碍事件回调 |
| `G(event)` | MyAccessibilityService | 窗口状态变化 |
| `f0(event)` | MyAccessibilityService | 事件分发给 delegate |
| `u(event, pkg, class)` | AccessibilityDelegate | 无障碍事件回调 |
| `h0(pkg, class, title, complete)` | MyAccessibilityService | 通知 delegate 根节点变化 |
| `v(root, ready, pkg, class, title)` | AccessibilityDelegate | 更新活跃根节点 |
| `run()` | DelegateEventDispatcher | 事件分发器线程 |

---

## 11. ListenWindow 匹配流程

```
AccessibilityEvent
    ├─ eventType: 32 (WINDOW_STATE_CHANGED)
    ├─ source: AccessibilityNodeInfo
    └─ getPackageName(): "com.oplus.battery"
    
        ↓ 检查 ListenWindow 匹配
        
    ListenWindow
        ├─ packageName: "com.oplus.battery"  ✓ 包名匹配
        ├─ className: "PowerControlActivity"
        ├─ eventTypes: [32, 16384]  ✓ 事件类型匹配
        └─ matchs: [CombineFilter...]  ✓ 内容过滤
        
        ↓ 执行 ListenWindow 动作
        
    delegate.e(listenWindow, state)
        └─ 处理事件、点击按钮、设置参数等...
```

---

## 12. 引擎启动流程图

```
                    HTTP API
                      ↓
          /testOppoKeepAlive (端口 7910)
                      ↓
      svc.b(packageName)  [初始化入口]
                      ↓
          OppoEngine() 构造函数
                      ↓
      buildAllListenWindows()  [注册 ListenWindow]
                      ↓
    ┌─────────────────────────────┐
    │  ListenWindow 列表:         │
    │  1. com.oplus.battery/...  │
    │  2. com.coloros.oppoguardelf
    │  3. com.android.settings   │
    │  ...                       │
    └─────────────────────────────┘
                      ↓
    DelegateTaskLauncher.c()  [启动分发线程]
                      ↓
    DelegateEventDispatcher.run() [case 4]
                      ↓
    SystemHelper.Z0()  [启动应用详情页]
                      ↓
        AccessibilityEvent (WINDOW_STATE_CHANGED)
                      ↓
        onAccessibilityEvent()
                      ↓
            G() → h0() → f0()
                      ↓
        delegate.u() [事件回调]
                      ↓
    DelegateEventDispatcher.run() [case 0]
                      ↓
        遍历 ListenWindow
                      ↓
    ListenWindow 匹配 (pkg/class/eventType/filter)
                      ↓
        delegate.e() [执行 ListenWindow 动作]
                      ↓
        点击按钮 → 设置参数 → 等待界面更新
```

