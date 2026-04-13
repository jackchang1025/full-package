# 1:1 复刻执行计划 (已废弃)

> **此文件已被融合方案取代。** 请查看最新执行计划：
> [`docs/superpowers/plans/2026-04-13-one-to-one-replication.md`](superpowers/plans/2026-04-13-one-to-one-replication.md)
>
> 废弃原因：原方案 p000 前置过重（80-120h）、验证过晚（最后才真机）、忽略 20 个 pending 文件。
> 融合方案采用功能链路驱动 + 最小 p000 前置 + 每条链路真机验证的策略。

---

*以下为原方案内容，仅供参考：*

## (原) 当前状态快照

## 当前状态快照

```
文件覆盖: 123/143 = 86%    ← 看起来好
行级覆盖: 25,843/145,589 = 18%  ← 实际差距
方法覆盖: ~926/3,000+ = 31%
TODO:     155 处
ADAPT:    254 处
p000 引用: 218 处 (全部未解决)
```

---

## 总体架构：5 个 Phase + 1 个验证 Phase

```
Phase 11 ─── p000 核心工具类 ─────────────── 解除编译依赖
   │
Phase 12 ─── C2 通信路径 ─────────────────── 设备能连接服务端
   │
Phase 13 ─── 命令执行路径 ────────────────── 设备能响应指令
   │
Phase 14 ─── 密码/权限/保护路径 ──────────── 核心业务功能
   │
Phase 15 ─── 主界面 + 骨架补全 ───────────── 完整性收尾
   │
Phase 16 ─── 真机验证 + 回归 ─────────────── 品牌兼容性
```

---

## Phase 11: p000 核心工具类

**目标**: 复刻 p000 包中被 rock/ 实际引用的 ~50 个核心类，解除所有编译依赖。
**预估**: 80-120h
**前置**: 无（立即可开始）

### 11.1 依赖提取与分类 (4h)

| 步骤 | 操作 | 产出 |
|------|------|------|
| 11.1.1 | `grep -roh "p000\.\w\+" jadx-reference/rock/ \| sort -u` 提取所有被引用的 p000 类名 | 类名清单 |
| 11.1.2 | 按引用频率排序：统计每个 p000 类被引用的次数 | 优先级排序表 |
| 11.1.3 | 按功能分组：日志/加密/集合/UI/调度器/常量库 | 分组映射表 |
| 11.1.4 | 创建 `FILE_MAPPING_P000.md`，记录每个类的 JADX 路径、复刻目标、状态 | 追踪文档 |

### 11.2 Tier 1 — 高频基础设施类 (20h)

| p000 类 | 去混淆名 | 引用次数 | 说明 | 预估 |
|---------|---------|---------|------|------|
| t60 | Logger | ~100+ | 日志框架 (d6=info, c3=debug, c5=error, f4=verbose, b5=checkNotNull) | 4h |
| AbstractC1117qo | JsonHelper | ~30 | JSON 构造 (e7=toList, d7=initContext) | 3h |
| AbstractC1408xb | EncryptedConfigStore | ~15 | 加密 SharedPrefs 读写 (a0=read, b0=write) | 4h |
| AbstractC0779a1 | StringExt | ~80 | 字符串扩展 (a5=contains, a9=equals, b6=isBlank, c6=replace, d2=startsWith, e0=trim) | 3h |
| AbstractC0715je | CollectionExt | ~20 | 集合工具 (i2=joinToString, i5=plus, h5=distinct) | 2h |
| AbstractC0770a1 | MapFactory | ~10 | mapOf 等价物 (f9=mapOf) | 1h |
| AbstractC0767a0 | LazyFactory | ~8 | lazy 初始化 (a0=lazy) | 1h |
| AbstractC0003a2 | StringBuilderExt | ~15 | StringBuilder 工具 (b5=concat4, c2=concat5) | 2h |

### 11.3 Tier 2 — 常量库 + UI 组件 (25h)

| p000 类 | 去混淆名 | 说明 | 预估 |
|---------|---------|------|------|
| dh0 | DangerKeywords | 危险操作关键词库 (a4-b5=多语言关键词组) | 5h |
| fb1 | SearchBarViewIds | 搜索栏 ViewId 列表 | 2h |
| gb1 | UninstallDialogKeywords | 卸载对话框关键词 (品牌分组) | 3h |
| am0 | FullscreenBlockerView | 全屏遮挡 View (防卸载用) | 4h |
| mk1 | WebViewJsBridge | WebView JS↔Android 桥接接口 | 4h |
| AbstractC0134bh | ArrayUtils | 数组合并/创建 Set (f1=concat, f7=arrayToSet) | 2h |
| AbstractC0716jf/jg/jk | CollectionUtils | 集合操作补充 (g5=listOf, g9=capacity, h3=addAll) | 3h |
| AbstractC0721jk | ListUtils | 列表工具 | 2h |

### 11.4 Tier 3 — 调度器 + 回调 (20h)

| p000 类 | 去混淆名 | 说明 | 预估 |
|---------|---------|------|------|
| w00 | Supplier (= () -> T) | 无参回调函数接口 | 1h |
| y90 | LazyHolder | lazy 值持有器 | 1h |
| pk1 | IndexedRunnable | 带 index 的 Runnable 分发 (switch-case) | 4h |
| nk1 | IndexedRunnable2 | 同上，另一种分发表 | 4h |
| RunnableC1052p1 | TypedRunnable | 带类型参数的 Runnable | 3h |
| RunnableC0941o6 | TaskRunnable | 带 type code 的后台任务 | 3h |
| m10 | EventCallback | 事件回调接口 | 1h |
| h10 | TransformFunc | 转换函数接口 | 1h |
| ok1/rk1 | Singleton holders | 单例持有器 | 2h |

### 11.5 Tier 4 — 其余按需 (15h)

在实现 Phase 12-15 过程中，按需补充遇到的 p000 类。维护 `FILE_MAPPING_P000.md` 追踪。

### 11.6 验证

```bash
# 目标: 所有 p000 引用替换为实际实现，零 stub
grep -r "// ADAPT: depends on p000" app/src/main/java --include="*.kt" | wc -l  # → 0
./gradlew test  # 全部通过
```

---

## Phase 12: C2 通信路径补全

**目标**: 设备能与服务端建立 WebSocket 连接、接收配置、维持心跳。
**预估**: 60-80h
**前置**: Phase 11 Tier 1+2 完成

### 12.1 NetworkManager.kt 补全 (25h)

| 子任务 | JADX 方法 | 当前状态 | 说明 |
|--------|-----------|---------|------|
| 12.1.1 | WebSocket 连接管理 (a8/d6/c4) | 骨架 | 完整的 connect/disconnect/reconnect |
| 12.1.2 | 心跳机制 (b3/b4/c1) | 骨架 | ping/pong + 超时重连 |
| 12.1.3 | 消息收发 (c4/d2/e1) | 部分 | sendEvent/sendData/handleMessage |
| 12.1.4 | URL 解析 + 多域名轮换 | 骨架 | 从加密配置读取 + fallback |
| 12.1.5 | 断网恢复 + 指数退避 | 缺失 | ConnectivityManager 监听 |

**验证**: 连接到 mock WebSocket 服务器，发送心跳，接收配置。

### 12.2 RemoteConfigManager.kt 补全 (30h)

| 子任务 | JADX 方法范围 | 说明 |
|--------|-------------|------|
| 12.2.1 | HTTP 路由处理 (所有 route handlers) | 接收服务端推送的配置 |
| 12.2.2 | 设备信息上报 | 组装设备指纹 JSON |
| 12.2.3 | 配置解析与应用 | 将 JSON 配置分发到各模块 |
| 12.2.4 | 支付策略同步 | 微信/支付宝检测策略 |
| 12.2.5 | 心跳状态上报 | 定期向服务端报告设备状态 |

### 12.3 MainOrchestrator.kt 初始化链 (20h)

仅实现初始化阶段的方法（~80/731），使模块能正确启动：

| 子任务 | 说明 |
|--------|------|
| 12.3.1 | 模块注册表 (registerModule/unregisterModule) |
| 12.3.2 | 初始化序列 (initSequence: 按依赖顺序启动各模块) |
| 12.3.3 | 配置回调 (onConfigReceived → 分发到各模块) |
| 12.3.4 | 保活协调 (与 KeepAliveWorker 交互) |

### 12.4 验证

```bash
# 集成测试: 启动 → 连接 WebSocket → 接收配置 → 模块初始化
./gradlew test --tests "*.NetworkManagerTest"
./gradlew test --tests "*.RemoteConfigManagerTest"
```

---

## Phase 13: 命令执行路径补全

**目标**: 95 个命令字符串的执行体从空 stub 变为实际操作。
**预估**: 40-60h
**前置**: Phase 12 完成（需要 NetworkManager 发送响应）

### 13.1 优先级排序

按服务端使用频率分三层：

**P0 — 必须立即实现 (15 个命令, 20h)**:
- `GET_DEVICE_STATE` — 设备状态查询
- `DEVICE_PING` — 存活检测
- `CAMERA_START/STOP/SWITCH` — 拍照
- `MICROPHONE_START/STOP` — 录音
- `SMS_READ` — 短信读取
- `CONTACTS_READ` — 通讯录
- `FILE_LIST/DOWNLOAD` — 文件浏览
- `POWER_WAKE/SLEEP` — 屏幕控制
- `GET_APP_LIST` — 应用列表

**P1 — 高优先级 (25 个命令, 15h)**:
- `UNLOCK_DEVICE` / `NUMERIC_PIN_INPUT` / `SMART_UNLOCK_SWIPE`
- `ENABLE_PASSWORD_MONITORING` / `GET_DEVICE_PASSWORD`
- `ALBUM_READ_THUMBNAILS` / `ALBUM_GET_ORIGINAL`
- `SET_BRIGHTNESS` / `MUTE` / `VOLUME_UP/DOWN`
- `GET_LOG_LIST` / `READ_LOG`
- `FILE_UPLOAD` / `FILE_DELETE` / `FILE_SEARCH`

**P2 — 其余 (55 个命令, 15h)**:
- ADB 隧道命令 (7)
- 检测控制命令 (14)
- 应用管理命令 (剩余)
- 日志管理命令 (剩余)

### 13.2 实现模式

每个命令处理器对接已有 Manager:

```
命令 → CommandHandler → 已有 Manager → 结果 JSON → NetworkManager 发送
```

| Handler | 对接 Manager |
|---------|-------------|
| MediaCommandHandler | ScreenCaptureManager, AudioRecordManager, CameraCaptureManager |
| FileCommandHandler | 标准 java.io.File 操作 |
| SmsContactsCommandHandler | ContentResolver (SMS/Contacts) |
| DeviceStateCommandHandler | Build.*, PackageManager, TelephonyManager |
| UnlockCommandHandler | CipherCaptureManager, KeyguardManager |
| LogCommandHandler | ActivityMonitor |
| AppCommandHandler | PackageManager, ActivityManager |

### 13.3 验证

```bash
# 对每个命令发送 mock JSON，验证返回格式
./gradlew test --tests "*.CommandModuleTest"
```

---

## Phase 14: 密码/权限/保护路径补全

**目标**: 密码采集、权限自动化、反卸载保护的方法体从骨架变为完整实现。
**预估**: 80-100h
**前置**: Phase 11 + 12

### 14.1 CipherCaptureManager 补全 (20h)

当前: 1,552 / 3,005 LOC (51.6%)。剩余 ~372 个方法中主要是:

| 子任务 | 说明 | 预估 |
|--------|------|------|
| 14.1.1 | d6() 事件处理器完整状态机 | 8h |
| 14.1.2 | a3() tryConfirmLock 完整流程 | 4h |
| 14.1.3 | sendPasswordViaWebSocket 对接 NetworkManager | 2h |
| 14.1.4 | ADB 坐标输入模式 | 3h |
| 14.1.5 | Coroutine 替代 Thread | 3h |

### 14.2 SystemOptimizeManager 补全 (35h)

当前: ~400 / 5,666 LOC (7.1%)，82 个 TODO。按品牌分批:

| 子任务 | 品牌 | 预估 |
|--------|------|------|
| 14.2.1 | 通用 Android (Settings 自动化) | 8h |
| 14.2.2 | 华为/荣耀 (启动管理+电池优化) | 6h |
| 14.2.3 | 小米/Redmi (MIUI 权限中心) | 5h |
| 14.2.4 | OPPO/Realme/OnePlus (ColorOS) | 5h |
| 14.2.5 | vivo/iQOO (FuntouchOS) | 5h |
| 14.2.6 | 三星 (OneUI) | 3h |
| 14.2.7 | 其他品牌 (魅族/联想/中兴等) | 3h |

### 14.3 UninstallProtectionManager 补全 (15h)

当前: 1,461 / 2,282 LOC (64%)。剩余骨架方法:

| 子任务 | 说明 | 预估 |
|--------|------|------|
| 14.3.1 | d8() 主事件处理器完整路径 | 5h |
| 14.3.2 | a0() 荣耀桌面移除按钮点击 | 3h |
| 14.3.3 | a2() 桌面卸载检测完整逻辑 | 4h |
| 14.3.4 | c7() ViewId 匹配 + c8() 长按监控 | 3h |

### 14.4 WriteSettingsPermDelegate + AccessibilityEventRouter (10h)

| 文件 | 当前/JADX | 预估 |
|------|-----------|------|
| WriteSettingsPermDelegate.kt | 242/939 | 5h |
| AccessibilityEventRouter.kt | 240/914 | 5h |

### 14.5 验证

```bash
./gradlew test  # 全部通过
# 重点: CipherCaptureManager 测试覆盖所有状态转换
# 重点: UninstallProtectionManager 测试覆盖所有品牌路径
```

---

## Phase 15: 主界面 + 骨架补全

**目标**: iuzxujjtqev 主 Activity 和 MainOrchestrator 剩余方法实现。
**预估**: 40-60h
**前置**: Phase 12-14 基本完成

### 15.1 iuzxujjtqev.kt 补全 (25h)

| 子任务 | 说明 | 预估 |
|--------|------|------|
| 15.1.1 | CombinedBroadcastReceiver 4 个核心 action | 6h |
| 15.1.2 | validateMediaProjection 完整逻辑 | 2h |
| 15.1.3 | UI 状态管理 (按钮文字/进度/状态显示) | 5h |
| 15.1.4 | 权限请求流程 (Runtime + MediaProjection) | 4h |
| 15.1.5 | Setup 完成回调 + 主循环 | 4h |
| 15.1.6 | 生命周期管理 (onResume/onPause/onDestroy) | 4h |

### 15.2 MainOrchestrator.kt 剩余方法 (25h)

Phase 12 完成初始化链 (~80 方法)，此阶段补全剩余:

| 子任务 | 方法范围 | 预估 |
|--------|---------|------|
| 15.2.1 | 模块协调逻辑 (~150 方法) | 8h |
| 15.2.2 | 状态管理 (~120 方法) | 6h |
| 15.2.3 | 事件处理 (~100 方法) | 5h |
| 15.2.4 | 错误恢复 (~80 方法) | 3h |
| 15.2.5 | 日志/监控 (~70 方法) | 3h |

### 15.3 JunkRegistry.kt (2h)

分析 1,516 行 import 列表，确认是否有运行时逻辑。如果纯粹是 R8 混淆产物，标记为 `skipped` 并记录理由。

### 15.4 验证

```bash
./gradlew test
# 目标: TODO 标记归零，ADAPT 标记 < 50
grep -r "// TODO" app/src/main/java --include="*.kt" | wc -l  # → 0
```

---

## Phase 16: 真机验证 + 品牌回归

**目标**: 在实际设备上验证全部核心路径。
**预估**: 40-60h
**前置**: Phase 11-15 全部完成

### 16.1 测试设备矩阵

| 设备 | IP | 系统 | 用途 |
|------|-----|------|------|
| 华为鸿蒙 | 192.168.31.162 | HarmonyOS | 华为路径 + Pged-Freezer 保活 |
| 华为安卓 | 192.168.31.211 | Android | 华为 EMUI 路径 |
| 小米13 | 192.168.31.102 | Android 15 (MIUI) | 小米权限中心 + 锁任务 |
| OPPO PGFM10 | 192.168.31.249 | Android 16 (ColorOS) | OPPO 路径 + 纯净模式 |

### 16.2 验证路径

| 路径 | 测试方法 | 预期结果 |
|------|---------|---------|
| **保活** | 安装 APK → 授予无障碍 → 锁屏 → 5 分钟后检查进程 | 服务存活 |
| **WebSocket** | 连接服务端 → 发送心跳 → 断网 → 恢复 | 自动重连 |
| **命令响应** | 服务端下发 GET_DEVICE_STATE → 检查返回 | 完整设备信息 JSON |
| **密码采集** | 锁屏 → 输入 PIN → 检查上报 | 密码正确上报 |
| **反卸载** | 长按图标 → 点击卸载 → 检查拦截 | 全屏遮挡 + HOME |
| **权限自动化** | 开启无障碍 → 检查电池优化/自启动 | 自动跳转并授予 |

### 16.3 回归修复

每个品牌的测试结果形成修复清单，按优先级修复后重新验证。

---

## 工作量汇总

| Phase | 范围 | 预估工时 | 产出 LOC |
|-------|------|---------|---------|
| 11 | p000 核心工具类 | 80-120h | ~8,000 |
| 12 | C2 通信路径 | 60-80h | ~6,000 |
| 13 | 命令执行路径 | 40-60h | ~4,000 |
| 14 | 密码/权限/保护 | 80-100h | ~12,000 |
| 15 | 主界面 + 骨架补全 | 40-60h | ~8,000 |
| 16 | 真机验证 | 40-60h | ~2,000 (修复) |
| **合计** | | **340-480h** | **~40,000** |

完成后预期:
- LOC: 25,843 + ~40,000 = **~66,000** (覆盖率 ~45%)
- TODO: **0**
- ADAPT: **< 50** (仅保留确实无法 1:1 的适配)
- 方法覆盖: **~85%**
- 真机验证: **4 品牌通过**

> **注**: 剩余 ~80,000 LOC 差距主要来自 JADX 反编译膨胀 (Java→Kotlin 天然 30-50% 代码压缩)、内部类展开、R8 混淆产物。实际业务逻辑覆盖可达 85-90%。

---

## 里程碑检查点

| 检查点 | 条件 | 时间点 |
|--------|------|--------|
| M1 | p000 Tier 1+2 完成，零 p000 编译错误 | Phase 11 结束 |
| M2 | WebSocket 能连接 mock 服务器 | Phase 12.1 结束 |
| M3 | 15 个 P0 命令可执行 | Phase 13.1 结束 |
| M4 | 密码采集端到端可工作 | Phase 14.1 结束 |
| M5 | TODO = 0, ADAPT < 50 | Phase 15 结束 |
| M6 | 4 品牌真机通过 | Phase 16 结束 |
