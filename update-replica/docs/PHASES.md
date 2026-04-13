# 阶段路线图

> 复刻 `update.apk` 的 10 阶段计划（559 个 JADX 文件 → Kotlin）。
> 源码: `../jadx-reference/rock/`

## 阶段依赖关系

```
Phase 1 (util, security)
  └→ Phase 2 (network)
       └→ Phase 3 (service, manager)
            └→ Phase 4 (modules/base, UiObject)
                 ├→ Phase 5 (yw5xud — 厂商引擎)     ✅
                 ├→ Phase 6 (setup — 开发者选项, ADB)    ← 下一步
                 ├→ Phase 7 (cipher — 密码捕获)
                 └→ Phase 8 (command, overlay, screen)
                      └→ Phase 9 (protection)
                           └→ Phase 10 (activity, receiver, root)
```

## Phase 1 — 工具类与安全检查 ✅

**范围**: 基础工具类和安全检查。

| JADX 源码 | 复刻文件 | LOC |
|-----------|---------|-----|
| util/StringUtil.java | util/StringUtil.kt | 64 |
| util/AbstractC0385a0.java | util/DeviceUtils.kt | 85 |
| security/AbstractC0276a0.java | security/SecurityChecker.kt | 147 |
| keepalive/KeepAliveWorker.java | keepalive/KeepAliveWorker.kt | 28 |

## Phase 2 — 网络层 ✅

**范围**: WebSocket C2 通信层。

| JADX 源码 | 复刻文件 | LOC |
|-----------|---------|-----|
| network/C0267a0.java | network/DataSyncClient.kt | 393 |

## Phase 3 — 服务与管理器 ✅

**范围**: 核心 Android 服务和资源管理器。

| JADX 源码 | 复刻文件 | LOC |
|-----------|---------|-----|
| service/AppCoreService.java | service/AppCoreService.kt | 92 |
| service/dqtvuisjd.java | service/MyAccessibilityService.kt | 234 |
| service/InitWorkerService.java | service/InitWorkerService.kt | 39 |
| service/MediaDisplayService.java | service/MediaDisplayService.kt | 84 |
| manager/C0260a2.java | manager/ScreenCaptureManager.kt | 85 |
| *(另有 12 个文件)* | | |

## Phase 4 — 模块基础 ✅

**范围**: 无障碍代理基类和 UI 节点包装器。

| JADX 源码 | 复刻文件 | LOC |
|-----------|---------|-----|
| modules/base/AbstractC0330a0.java | modules/base/AccessibilityDelegate.kt | 130 |
| modules/cipher/UiObject.java | modules/cipher/UiObject.kt | 217 |
| *(另有 ListenWindow, NodeTraverser, DelegateTaskLauncher)* | | |

## Phase 5 — 厂商引擎 (yw5xud) ✅

**范围**: 系统检测 + 7 个厂商特定的保活自动化。

| JADX 源码 | 复刻文件 | LOC |
|-----------|---------|-----|
| modules/yw5xud/C0372a9.java | modules/yw5xud/Yw5xudHandler.kt | 525 |
| modules/yw5xud/C0365a2.java | modules/yw5xud/GenericSteps.kt | 577 |
| modules/yw5xud/C0367a4.java | modules/yw5xud/HuaweiSteps.kt | 174 |
| modules/yw5xud/C0368a5.java | modules/yw5xud/VivoSteps.kt | 207 |
| *(另有 7 个文件)* | | |

## Phase 6 — 设置与 ADB 配对 ⏳

**范围**: 开发者选项解锁 + ADB 无线配对（通过 SPAKE2）。
**JADX 目录**: `../jadx-reference/rock/service/modules/setup/`

| JADX 源码 | 去混淆名称 | LOC | 职责 |
|-----------|-----------|-----|------|
| C0358a0.java | OpenDevelopmentDelegate | 1,401 | 开发者选项 UI 自动化（状态机） |
| C0360a2.java | SystemOptimizeManager | 5,666 | ADB 配对（SPAKE2 + TLS + mDNS） |
| C0362a4.java | UiNodeHelper | 249 | 无障碍节点工具类 |
| AbstractC0361a3.java | SetupConstants | 30 | 共享常量 |

**内部类**（14 个文件，编译进父类）:
- `OpenDevelopmentDelegate$State.java` — 11 状态枚举
- `SystemOptimizeManager$PairState.java` — 8 状态配对枚举
- `SystemOptimizeManager$DevOptState.java` — 开发者选项子状态
- `OpenDevelopmentDelegate$P$1-6.java` — 厂商特定谓词
- `OpenDevelopmentDelegate$T$2-3.java` — 窗口匹配辅助类
- `SystemOptimizeManager$startOpenDevelopmentDelegate$1-2.java` — 协程 lambda
- `UiNodeHelper$waitForPageStable$1.java` — 稳定性检查器

**关键特性**:
- 无障碍驱动的设置导航（多厂商: AOSP/华为/MIUI/ColorOS/OriginOS/OneUI）
- USB 调试 + 无线调试开关自动化
- SPAKE2 密码认证密钥交换
- TLS 双向认证（自签名证书）
- mDNS 服务发现（`_adb-tls-pairing._tcp`, `_adb-tls-connect._tcp`）
- RSA 2048 密钥对生成 + Android 格式公钥编码
- `WRITE_SECURE_SETTINGS` 权限用于自引导

## Phase 7 — 密码捕获 (Cipher)

**范围**: 通过无障碍服务捕获锁屏密码/PIN/图案。
**JADX 目录**: `../jadx-reference/rock/service/modules/cipher/`
**文件数**: 15（不含 UiObject，已在 Phase 4 完成）

## Phase 8 — 命令、悬浮窗、屏幕

**范围**: 远程命令执行、屏幕悬浮窗、屏幕唤醒。
**JADX 目录**: `modules/command/`, `modules/overlay/`, `modules/screen/`, `modules/` 根目录
**文件数**: 34

## Phase 9 — 防护

**范围**: 反分析/防卸载。
**JADX 目录**: `../jadx-reference/rock/service/modules/protection/`
**文件数**: 2

## Phase 10 — Activity、Receiver、根类

**范围**: 所有剩余文件 — Activity、广播接收器、应用变体、根类。
**JADX 目录**: `activity/`, `receiver/`, `inject/`, `p029ui/`, 根目录 `*.java`
**文件数**: 51
