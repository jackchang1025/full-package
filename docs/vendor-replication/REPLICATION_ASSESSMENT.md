# Vendor → Replica Java 复刻详细评估

> **评估日期**: 2026-04-02
> **Vendor 源码**: `decompiled_vendor/sources/com/guard/wallet/` (294 文件) + `o/` (33 文件)
> **Replica 代码**: `android/app/src/main/java/com/vendor/rat/`

---

## 一、总体概况

| 指标 | Vendor | Replica | 比率 |
|------|--------|---------|------|
| 文件数 | 327 | 406 | 124%（拆分更细） |
| 代码行数 | 57,658 | 48,108 | 83.4% |
| 包/目录数 | 19 | 35 | 更模块化 |
| 编译状态 | — | ✅ BUILD SUCCESSFUL | — |
| TODO 标记 | — | 217 (其中 VENDOR_VERIFY 185) | — |
| Stub 文件 | — | 16 | — |

**映射表状态**: 全部 9 个模块标记为 ✅ 完成。

---

## 二、模块级评估

### MODULE_01: 网络通信 — ✅ 完整

| Vendor | Replica | 评价 |
|--------|---------|------|
| `http/h.java` (221L) | `network/HttpClient.java` | 完整 |
| `http/l.java` (374L) | `network/NetworkManager.java` | 完整 |
| `bridge/a.java` (115L) | `network/WebSocketClient.java` | 完整，已扩展 |
| `msg/` 9 个 | `network/msg/` 9 个 | 1:1 映射 |
| `http/` 30 个回调 | `network/` 26 个 | 大部分为 5 行 stub |

**风险**: 26 个网络回调文件中有 4 个仅 5 行（h/i/l/v.java），标记 `VENDOR_VERIFY`。这些是 HTTP API 回调，如果服务端需要特定响应格式可能出问题。但当前 WebSocket 通信正常工作。

### MODULE_02: 权限绕过 — ✅ 基本完整

| Vendor | Replica | 差异 |
|--------|---------|------|
| `MyAccessibilityService` 1402L | 1137L | **-19%** |
| `AccessibilityDelegateManager` 800L | `EngineManager` 583L | **-27%** |

**差异分析**: Replica 精简了部分 vendor 的冗余逻辑（重复的 null check、日志），核心行为路径完整。无障碍服务在真机上已验证可用。

### MODULE_03: 厂商适配引擎 — ✅ 完整

| Vendor `o/` | Replica `auto/engine/` | 差异 |
|-------------|----------------------|------|
| 33 文件, 11,410L | 31 文件, 11,911L | **+4%** |

这是复刻质量最高的模块。Replica 行数甚至略多于 Vendor（因为添加了注释和更清晰的命名）。华为/小米/OPPO/vivo/三星引擎全部完成。

### MODULE_04: UI 自动化框架 — ⚠️ 有缺口

| Vendor | Replica | 差异 |
|--------|---------|------|
| `UiObject.java` 3801L | `UiNode.java` 1046L | **-72%** |
| `filter/` 39 文件 | `auto/filter/` 42 文件 | 文件数完整 |
| `condition/` 8 文件 | `auto/condition/` 9 文件 | 完整 |

**关键缺口**: `UiObject.java` 是 vendor 最大的单文件（3801 行），包含完整的 UI 节点操作 API（查找、点击、滑动、输入、等待等）。Replica 的 `UiNode.java` 只有 1046 行，缺失约 2755 行。这些缺失的方法主要是高级 UI 操作（组合查找、条件等待、复杂手势），目前引擎层直接调用 AccessibilityNodeInfo API 绕过了部分缺失。

**影响**: 如果未来需要实现更复杂的自动化脚本（如多步骤 UI 流程），这个缺口会成为瓶颈。

### MODULE_05: 数据收集 — ✅ 完整

所有 receiver、stat、helper 文件已映射。密码采集覆盖层（PinCaptureOverlay、PatternCaptureOverlay）已实现。

### MODULE_06: 远程控制 — ⚠️ 最大缺口

| Vendor | Replica | 差异 |
|--------|---------|------|
| `server/b.java` 11,172L (244 方法) | `control/` 5,141L (含 handler/) | **-54%** |
| `CommandDispatcher` — | 1,919L (93 方法) | 核心路由 |

**关键缺口**: Vendor 的 `server/b.java` 是整个 APK 的核心命令处理器（11,172 行、244 个方法），处理所有来自 Panel 的远程控制命令。Replica 拆分为 `CommandDispatcher` + 12 个 handler，但总行数只有一半。

缺失的主要是：
- 部分 UI 自动化命令的服务端处理（搜索节点、条件等待等）
- 部分文件操作的完整实现（文件搜索、批量操作）
- 部分 ADB 命令的完整处理链

**影响**: 这是功能完整性的最大风险。Panel 发送的命令如果在 CommandDispatcher 中没有对应 handler，会被静默忽略。

### MODULE_07: 保活机制 — ✅ 完整

| Vendor | Replica | 差异 |
|--------|---------|------|
| `thread/` 13 文件, 1912L | `keepalive/thread/` 15 文件 | 完整 |
| `receiver/` 相关 | `keepalive/receiver/` | 完整 |
| `sync/` 2 文件 | `keepalive/sync/` 2 文件 | 完整 |

### MODULE_08: 启动流程与隐蔽 — ⚠️ 有缺口

| Vendor | Replica | 差异 |
|--------|---------|------|
| `MainApplication.java` 909L | 425L | **-53%** |
| `utils/g.java` 3142L | `MiscUtils.java` 493L | **-84%** |
| `utils/h.java` 761L | `SharedUtils.java` 477L | **-37%** |

**关键缺口**:
- `utils/g.java`（3142 行）是 vendor 的万能工具类，包含设备信息采集、文件操作、加密、网络检测等。Replica 只实现了 16%。
- `MainApplication.java` 缺失的部分主要是初始化时的各种检测和配置加载。

**影响**: 部分设备信息采集可能不完整，影响 Panel 显示的设备详情。核心功能（WebSocket 连接、命令执行）不受影响。

### MODULE_09: 数据模型 — ✅ 完整

| Vendor | Replica | 差异 |
|--------|---------|------|
| `req/` 55 文件, 3696L | `model/req/` 55 文件 | 1:1 映射 |
| `resp/` 42 文件, 4520L | `model/resp/` 42 文件 | 1:1 映射 |

数据模型是批量转换的，文件数完全匹配。但 185 个 `VENDOR_VERIFY` 标记中大部分在这里，主要是 `static of()` 工厂方法依赖混淆后的工具类。

---

## 三、风险矩阵

| 风险等级 | 模块 | 缺口 | 影响 |
|---------|------|------|------|
| 🔴 高 | MODULE_06 远程控制 | server/b.java 54% 缺失 | Panel 部分命令无响应 |
| 🟡 中 | MODULE_04 UiObject | UiObject 72% 缺失 | 复杂自动化脚本受限 |
| 🟡 中 | MODULE_08 utils/g | MiscUtils 84% 缺失 | 设备信息采集不完整 |
| 🟢 低 | MODULE_08 MainApplication | 53% 缺失 | 初始化检测不完整 |
| 🟢 低 | MODULE_01 回调 stub | 4 个 5 行 stub | HTTP API 回调可能不完整 |
| ⚪ 无 | MODULE_03/07/09 | 完整或超出 | — |

---

## 四、TODO 分析

| 类型 | 数量 | 说明 |
|------|------|------|
| `VENDOR_VERIFY` | 185 | 需要对照 vendor 验证的逻辑，大部分在 model/req 和 model/resp |
| 功能性 TODO | 32 | 实际需要实现的功能点 |
| Stub 文件 | 16 | 仅有类声明，无实际逻辑 |

`VENDOR_VERIFY` 标记大多是数据模型的 `static of()` 方法，这些方法在 vendor 中依赖混淆后的工具类（`utils/g`、`utils/h`）。由于工具类本身复刻不完整，这些方法被标记为待验证。

---

## 五、已验证可用的功能

通过真机测试（OPPO 设备）确认可用：

- ✅ WebSocket 连接和心跳
- ✅ 设备注册和上线
- ✅ 屏幕投屏（SN）
- ✅ 截图（SM）
- ✅ 文字辅助 / 节点树读取（SK）
- ✅ 触摸转发（tap/swipe/gesture）
- ✅ 屏幕唤醒 / 解锁
- ✅ 无障碍服务引导
- ✅ 厂商适配引擎（OPPO）

---

## 六、建议优先级

1. **MODULE_06 CommandDispatcher 补全**（高优先）— 逐个对照 vendor `server/b.java` 的 244 个方法，补全缺失的命令处理。这直接决定 Panel 能控制多少功能。

2. **MODULE_04 UiNode 补全**（中优先）— 补全 UiObject 的高级查找和操作方法，为自动化引擎提供完整 API。

3. **MODULE_08 MiscUtils 补全**（低优先）— 按需补全设备信息采集方法，不需要一次性全部实现。

4. **VENDOR_VERIFY 清理**（低优先）— 逐步验证和修复 185 个标记，优先处理运行时实际调用到的路径。
