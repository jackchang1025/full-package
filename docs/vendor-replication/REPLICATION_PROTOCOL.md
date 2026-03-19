# Vendor APK 系统级复刻协议 V3 — 行为驱动

> 代码对齐 ≠ 行为对齐。真正的一比一复刻 = 代码对齐 + 功能验证 + 真机通过。
> 每个模块必须走完 5 个阶段，缺一不可。

---

## 核心理念

```
V2 (旧): 读 vendor → 写 replica → 编译通过 → 完成
V3 (新): 审计 vendor 行为 → 写功能测试 → 写 replica → 编译通过 → 真机验证通过 → 完成
```

---

## 使用方式

```
"复刻模块02"  → 自动执行 5 阶段完整流程
"审计模块02"  → 只执行 Phase 1 (审计 vendor 行为)
"测试模块02"  → 只执行 Phase 4-5 (构建+真机验证)
"验证模块02"  → 只执行 Phase 5 (真机验证)
```

---

## 路径常量

```
VENDOR_WALLET = app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet
VENDOR_ENGINE = app/storage/app/apk/apkstub/decompiled_vendor/sources/o
REPLICA_BASE  = android/app/src/main/java/com/vendor/rat
TEST_BASE     = android/app/src/test/java/com/vendor/rat
DOCS_BASE     = docs/vendor-replication
ADB           = /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE        = 192.168.31.162:5555
PACKAGE       = com.vendor.rat
```

---

## 五阶段工作流

### Phase 1: 审计 — 分析 Vendor 模块行为

> 目标: 搞清楚 vendor 这个模块到底做了什么，执行流程是什么，有哪些可观测行为。

```
FOR each vendor file in module:
  1. 完整读取 vendor 源文件
  2. 分析并记录:
     a. 入口点: 谁调用了这个类？从哪里触发？
     b. 执行流程: 方法调用链 A → B → C → D
     c. 外部交互: 调用了哪些 Android API？发了什么 Intent？写了什么 SharedPreferences？
     d. 可观测行为: logcat 输出、文件写入、网络请求、UI 变化、系统状态变更
     e. 异常处理: 失败时的降级逻辑
     f. 依赖关系: 依赖哪些其他模块的类
```

输出: `docs/vendor-replication/AUDIT_MODULE_XX.md`

审计文档模板:

```markdown
# MODULE_XX 行为审计

## 1. 模块职责
一句话描述这个模块做什么。

## 2. 入口点
| 入口 | 触发条件 | 调用链 |
|------|---------|--------|
| MyAccessibilityService.onServiceConnected() | 用户开启无障碍 | → EngineManager.init() → AutoEngine.start() |

## 3. 执行流程图
```
触发条件 → 入口方法
  → 步骤1: 做什么 (调用什么 API)
  → 步骤2: 做什么
  → 步骤3: 做什么
  → 结果: 产生什么可观测效果
```

## 4. 可观测行为清单
| # | 行为 | 如何验证 | 验证命令 |
|---|------|---------|---------|
| 1 | 无障碍服务启动 | dumpsys accessibility | adb shell dumpsys accessibility |
| 2 | 引擎加载日志 | logcat TAG=EngineManager | adb logcat -s EngineManager |

## 5. 依赖关系
- 依赖 MODULE_XX: 原因
- 被 MODULE_XX 依赖: 原因

## 6. 关键方法签名清单
(原 V2 的签名提取，保留)
```

### Phase 2: 测试设计 — 基于审计结果编写测试

> 目标: 把审计发现的每个可观测行为转化为可执行的测试用例。

两类测试:

#### A. JVM 单元测试 (不需要设备)

```
位置: android/app/src/test/java/com/vendor/rat/
运行: cd android && ./gradlew test
```

测试内容:
- 纯逻辑方法的输入输出
- 数据模型的序列化/反序列化
- 过滤器/条件匹配逻辑
- 工具类方法

#### B. 真机功能测试脚本 (需要 ADB)

```
位置: docs/vendor-replication/DEVICE_VERIFY_MODULE_XX.md
运行: Claude Code 通过 ADB 逐步执行
```

测试内容:
- 组件注册 (Service/Receiver/Activity 在系统中可见)
- 组件激活 (能启动、能运行)
- 行为验证 (产生预期的系统状态变更)
- 无 Crash (logcat 无 FATAL EXCEPTION)

测试用例模板:

```markdown
### TEST-XX-01: {测试名称}

前置条件: {需要什么状态}
操作步骤:
1. {ADB 命令或操作}
2. {ADB 命令或操作}

预期结果: {具体的可验证输出}
验证命令: {ADB 命令}
通过标准: {输出包含 XXX / 返回值为 YYY}
```

### Phase 3: 实现 — 对照 Vendor 逐文件编写 Replica

> 与 V2 相同，但现在有了审计文档和测试用例作为参照。

```
FOR each file (按依赖顺序):
  1. 读取 vendor 源文件
  2. 对照签名清单 + 审计文档中的执行流程
  3. 写入 replica (保持逻辑对齐)
  4. 编译检查: ./gradlew compileDebugJavaWithJavac
  5. JVM 测试: ./gradlew test (确保不破坏已有测试)
```

### Phase 4: 构建 + 安装

```bash
# 构建
cd android && ./gradlew assembleDebug

# 连接设备
$ADB connect $DEVICE

# 安装
$ADB -s $DEVICE install -r app/build/outputs/apk/debug/app-debug.apk
```

### Phase 5: 真机验证 — 逐项执行测试用例

```
FOR each test case in DEVICE_VERIFY_MODULE_XX.md:
  1. 执行 ADB 命令
  2. 检查输出是否匹配预期
  3. 记录 PASS/FAIL
  4. FAIL 时: 查 logcat → 定位问题 → 修复 → 重新构建 → 重新验证
```

输出验证报告:

```
MODULE_XX 真机验证报告
━━━━━━━━━━━━━━━━━━━━
设备: {model} / Android {version}

TEST-XX-01: {名称}  ✅ PASS
TEST-XX-02: {名称}  ✅ PASS
TEST-XX-03: {名称}  ❌ FAIL — {原因}
TEST-XX-04: {名称}  ✅ PASS

通过率: 3/4 (75%)
阻塞问题: TEST-XX-03 需要修复 XXX
```

---

## 模块验证顺序 (按依赖关系)

```
MODULE_08 启动流程  ← 最先验证，所有模块的基础
  ↓
MODULE_09 数据模型  ← VO/请求/响应，被所有模块依赖
  ↓
MODULE_01 网络通信  ← HTTP/WebSocket，被控制和数据模块依赖
  ↓
MODULE_02 权限绕过  ← 无障碍+设备管理员，被引擎和控制依赖
  ↓
MODULE_04 UI自动化  ← UiNode/Filter，被厂商引擎依赖
  ↓
MODULE_03 厂商适配  ← 依赖 02+04
  ↓
MODULE_05 数据收集  ← 依赖 01+02
  ↓
MODULE_07 保活机制  ← 依赖 01+08
  ↓
MODULE_06 远程控制  ← 依赖所有模块，最后验证
```

---

## 模块 → Vendor 文件映射注册表

(保持 V2 的完整映射表，此处省略，见下方各 MODULE 章节)

### MODULE_01: 网络通信 (http/ + bridge/ + msg/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/http/h.java | 221 | network/HttpClient.java |
| wallet/http/i.java | 293 | network/i.java |
| wallet/http/l.java | 374 | network/NetworkManager.java |
| wallet/bridge/a.java | 115 | network/WebSocketClient.java |
| wallet/http/v.java | 123 | network/HttpCallback.java |
| wallet/http/ 其余30个 | ~1304 | network/ 按功能归类 |
| wallet/msg/ 全部9个 | 320 | network/msg/ 消息体 |

### MODULE_02: 权限绕过 (service/ + o/c + o/e)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/service/MyAccessibilityService.java | 1402 | service/MyAccessibilityService.java |
| wallet/service/AccessibilityDelegateManager.java | 800 | service/EngineManager.java |
| wallet/receiver/CustomAdminReceiver.java | 119 | service/AppDeviceAdminReceiver.java |
| wallet/activity/ConfirmDeviceActivity.java | 225 | activity/PermissionActivity.java |
| o/e.java | 982 | auto/engine/AutoEngine.java (接口) |
| o/c.java | 801 | auto/engine/AutoEngine.java (基类) |

### MODULE_03: 厂商适配引擎 (o/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| o/n.java | 454 | auto/engine/vendor/HuaweiEngine.java |
| o/q.java | 498 | auto/engine/vendor/XiaomiEngine.java |
| o/v.java | 526 | auto/engine/vendor/OppoEngine.java |
| o/u.java | 169 | auto/engine/vendor/VivoEngine.java |
| o/s.java | 107 | auto/engine/vendor/SamsungEngine.java |
| o/a0.java | 2003 | auto/engine/PackageInstallerDelegate.java |
| o/t.java | 677 | auto/engine/OpenDevelopmentDelegate.java |
| o/x.java | 531 | auto/engine/AccessibilityServiceEngine.java |
| o/i0.java | 684 | auto/engine/ScreenUnlockDelegate.java |
| o/k.java | 382 | auto/engine/PermissionAutoGrantEngine.java |
| o/g0.java | 432 | auto/engine/AospKeepAliveEngine.java |
| o/e0.java | 373 | auto/engine/TranssionKeepAliveEngine.java |
| o/ 其余小文件 | ~2577 | auto/engine/ 辅助类 |
| wallet/utils/e.java | 367 | utils/DeviceUtils.java |

### MODULE_04: UI 自动化框架 (entity/ + filter/ + condition/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/entity/UiObject.java | 3801 | auto/entity/UiNode.java |
| wallet/entity/UiObjectCollection.java | 370 | auto/entity/UiNodeCollection.java |
| wallet/filter/CombineFilter.java | 207 | auto/condition/CombineFilter.java |
| wallet/filter/Filter.java | 8 | auto/filter/NodeFilter.java |
| wallet/filter/Selector.java | 58 | auto/filter/Selector.java |
| wallet/filter/ 全部39个 | 1483 | auto/filter/ 逐文件复刻 |
| wallet/condition/ 全部8个 | 1103 | auto/condition/ 逐文件复刻 |
| wallet/entity/ReadScreenNodeInfo.java | 145 | auto/entity/ |
| wallet/entity/ReadScreenWindow.java | 67 | auto/entity/ |
| wallet/entity/DistanceTouchNode.java | 39 | auto/entity/ |
| wallet/entity/Point.java | 59 | auto/entity/ |

### MODULE_05: 数据收集 (receiver/ + stat/ + helper/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/receiver/SmsReceiver.java | 67 | data/collector/SmsReceiver.java |
| wallet/receiver/CallReceiver.java | 88 | data/collector/CallReceiver.java |
| wallet/receiver/PackageReceiver.java | 117 | data/collector/PackageReceiver.java |
| wallet/stat/ 全部3个 | 297 | data/stat/ |
| wallet/entity/DeviceCipher.java | 102 | data/entity/DeviceCipher.java |
| wallet/helper/r.java | 428 | helper/PinCaptureOverlay.java |
| wallet/helper/o.java | 303 | helper/PatternCaptureOverlay.java |
| wallet/helper/n.java | 148 | helper/DialogOverlay.java |

### MODULE_06: 远程控制 (server/ + plug/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/server/b.java | 11172 | control/server/ (拆分为路由+handler) |
| wallet/server/a.java | 63 | control/server/ServerUploadTask.java |
| wallet/server/c.java | 146 | control/server/LocalWebSocketServer.java |
| wallet/service/MediaLiveService.java | 112 | control/service/MediaLiveService.java |
| wallet/plug/ 全部6个 | 500 | control/plug/ |
| wallet/entity/CommandResult.java | 51 | control/entity/ |
| wallet/entity/ADBConfig.java | 129 | control/entity/ |
| wallet/entity/AdbShellResult.java | 43 | control/entity/ |

### MODULE_07: 保活机制 (receiver/ + thread/ + sync/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/receiver/BootBroadcast.java | 103 | data/collector/BootBroadcast.java |
| wallet/receiver/ScreenBroadcastReceiver.java | 167 | data/collector/ScreenBroadcastReceiver.java |
| wallet/receiver/AlarmReceiver.java | 37 | keepalive/receiver/AlarmReceiver.java |
| wallet/receiver/BatteryLevelReceiver.java | 80 | keepalive/receiver/BatteryLevelReceiver.java |
| wallet/receiver/PowerBroadcastReceiver.java | 140 | data/collector/PowerBroadcastReceiver.java |
| wallet/receiver/ShutDownBroadcastReceiver.java | 89 | data/collector/ShutDownBroadcastReceiver.java |
| wallet/receiver/NetWorkReceiver.java | 33 | data/collector/NetWorkReceiver.java |
| wallet/receiver/LocaleChangeReceiver.java | 29 | data/collector/LocaleChangeReceiver.java |
| wallet/service/WIFIBackgroundService.java | 63 | keepalive/service/ |
| wallet/service/AccountAuthenticatorService.java | 64 | keepalive/service/ |
| wallet/sync/ 全部2个 | 66 | keepalive/service/ |
| wallet/thread/ 全部13个 | 1912 | keepalive/thread/ |

### MODULE_08: 启动流程与隐蔽 (root + activity/ + helper/ + utils/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/MyApp.java | 24 | MyApp.java |
| wallet/MainApplication.java | 909 | MainApplication.java |
| wallet/LockActivity.java | 225 | activity/ConfirmDeviceActivity.java |
| wallet/activity/MainActivity.java | 345 | activity/ActivMain.java |
| wallet/activity/GuideActivity.java | 99 | activity/GuideActivity.java |
| wallet/activity/NoDisplayActivity.java | 57 | activity/NoDisplayActivity.java |
| wallet/helper/g.java | 233 | helper/BlockViewHelper.java |
| wallet/helper/ 其余17个 | 1589 | helper/ |
| wallet/entity/BuildConfig.java | 601 | config/AppConfig.java |
| wallet/utils/d.java | 123 | config/ConfigDecryptor.java |
| wallet/utils/g.java | 3142 | utils/MiscUtils.java |
| wallet/utils/h.java | 761 | utils/SharedPrefsHelper.java |
| wallet/utils/ 其余9个 | 816 | utils/ |
| wallet/service/CustomNotificationService.java | 173 | service/CustomNotificationService.java |
| wallet/service/LocalHotspotService.java | 42 | service/LocalHotspotService.java |

### MODULE_09: 数据模型 (req/ + resp/ + entity/)

| Vendor 源文件 | 行数 | Replica 目标 |
|--------------|------|-------------|
| wallet/req/ 全部55个 | 3696 | model/req/ |
| wallet/resp/ 全部42个 | 4520 | model/resp/ |
| wallet/entity/ 剩余VO ~15个 | ~1500 | model/entity/ |

---

## 逻辑对齐规则（强制）

### 必须对齐

```
✅ vendor 有 5 个 if 分支 → replica 也必须有 5 个
✅ vendor 用 ConcurrentHashMap → replica 也用 ConcurrentHashMap
✅ vendor 有 synchronized → replica 也有
✅ vendor 方法调用顺序 A→B→C → replica 也是 A→B→C
✅ vendor 有 try-catch → replica 也有
✅ vendor 有 null 检查 → replica 也有
```

### 禁止"优化"

```
❌ 删掉 vendor 的防御性代码
❌ 用 Stream 替换 vendor 的 for 循环
❌ 省略 vendor 声明的字段
❌ 合并 vendor 的多个方法
```

### 允许的偏离（必须标注）

```java
// ADAPT: 包名 com.guard.wallet → com.vendor.rat.xxx
// ADAPT: 混淆名还原 o/n.java → HuaweiEngine
// ADAPT: 超大文件拆分 server/b.java → 多个 Handler
// ADAPT: 第三方库替换
```

---

## 大文件处理策略

| 文件 | 行数 | 策略 |
|------|------|------|
| server/b.java | 11172 | 按功能拆分为路由+多个 Handler |
| entity/UiObject.java | 3801 | 分3段: 字段+查找 / 操作 / 滚动 |
| utils/g.java | 3142 | 按功能拆分为多个工具类 |
| service/MyAccessibilityService.java | 1402 | 分2段: 事件处理 / 辅助方法 |
| o/a0.java | 2003 | 分2段: 核心方法 / 辅助方法 |

---

## 质量门禁 (V3 升级版)

每个模块必须满足全部条件才算完成：

1. ☐ Phase 1: 审计文档已生成 (AUDIT_MODULE_XX.md)
2. ☐ Phase 2: 功能测试用例已编写 (DEVICE_VERIFY_MODULE_XX.md)
3. ☐ Phase 3: 所有文件已实现，签名对齐
4. ☐ Phase 3: JVM 编译通过 (`./gradlew compileDebugJavaWithJavac`)
5. ☐ Phase 3: JVM 单元测试通过 (`./gradlew test`)
6. ☐ Phase 4: APK 构建成功 (`./gradlew assembleDebug`)
7. ☐ Phase 4: APK 安装成功
8. ☐ Phase 5: 真机功能测试全部 PASS
9. ☐ REPLICATION_MAP.md 已更新
