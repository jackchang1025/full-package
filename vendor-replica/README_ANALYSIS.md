# 保活引擎完整分析文档 — 索引

## 📚 文档导航

本项目包含以下分析文档，详细记录保活引擎的启动和触发机制：

### 1. **KEEP_ALIVE_CALL_CHAIN.md** ⭐ 主文档
**详细的完整调用链分析文档** (650 行)

包含内容：
- ✅ HTTP API 触发入口详解 (`/testOppoKeepAlive`)
- ✅ 引擎初始化流程 (`b()` 方法)
- ✅ ListenWindow 注册机制
- ✅ 无障碍服务连接初始化
- ✅ 无障碍事件回调完整流程
- ✅ DelegateEventDispatcher 事件分发机制
- ✅ AccessibilityDelegate.u() 事件回调
- ✅ 完整调用链总结
- ✅ 关键类和字段映射表
- ✅ 关键方法速查
- ✅ ListenWindow 匹配流程
- ✅ 引擎启动流程图

**推荐首先阅读此文档**

---

### 2. **QUICK_REFERENCE.md** ⚡ 速查手册
**快速参考卡，便于快速定位和查询** (377 行)

包含内容：
- 🔍 关键文件快速定位
- 🔗 完整调用链速记 (4 步简化)
- 🎯 6 个关键代码片段
- 📊 字段/方法映射表 (3 张表)
- 📋 快速搜索指令 (bash 命令)
- 📈 代码流程图
- 🎲 关键分支判断
- ⚡ 常见问题排查
- 🔄 生命周期顺序
- 🛠️ 调试技巧
- 📝 备忘单

**快速查询时首选**

---

### 3. **VENDOR_RUNTIME_ANALYSIS.md**
**运行时分析文档** (213 行)

包含内容：
- 运行时行为分析
- 性能特征
- 内存管理

---

### 4. **DROPPER_APK_RUNTIME_ANALYSIS.md**
**Dropper APK 运行时分析** (308 行)

包含内容：
- Dropper 组件分析
- APK 启动流程

---

## 🎯 使用指南

### 快速开始（5 分钟）
1. 打开 **QUICK_REFERENCE.md**
2. 查看"完整调用链速记"部分
3. 查看"关键代码片段"中的 6 个代码块

### 深入学习（30 分钟）
1. 打开 **KEEP_ALIVE_CALL_CHAIN.md**
2. 按顺序阅读：
   - 第 1 章 HTTP API 触发入口
   - 第 2 章 引擎初始化
   - 第 3 章 ListenWindow 注册机制
   - 第 8 章 完整调用链总结

### 代码调试（实时查询）
1. 打开 **QUICK_REFERENCE.md**
2. 使用"快速搜索指令"部分的 bash 命令
3. 参考"添加断点位置"部分

---

## 📍 关键文件位置速查

```
/home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/
├── server/ApiRouter.java                      第 312 行 ← HTTP API 入口
├── service/
│   ├── MyAccessibilityService.java            第 1323 行 ← 无障碍事件回调
│   │                                          第 1462 行 ← onServiceConnected 初始化
│   └── AccessibilityDelegateManager.java      第 336 行 ← 引擎初始化 b()
├── engine/
│   ├── OppoEngine.java                        第 91 行 ← ListenWindow 定义
│   └── KeepAliveEngine.java                   第 106 行 ← 引擎基类
└── delegate/
    ├── AccessibilityDelegate.java             第 960 行 ← u() 事件回调
    └── task/DelegateEventDispatcher.java      第 92 行 ← 事件分发器 run()
```

---

## 🔗 完整调用链（一句话总结）

```
HTTP API (/testOppoKeepAlive)
  ↓
b(packageName)  [引擎初始化]
  ↓
onAccessibilityEvent()  [无障碍事件回调]
  ↓
G() → h0() → f0()  [窗口变化和事件分发]
  ↓
delegate.u(event)  [委托回调]
  ↓
DelegateEventDispatcher.run(case=0)  [事件分发器]
  ↓
遍历 ListenWindow 并匹配  [检查 pkg/class/eventType/filter]
  ↓
delegate.e()  [执行 ListenWindow 动作]
```

---

## 🎓 学习路径

### 初级（了解整体架构）
1. 阅读 QUICK_REFERENCE.md 的"完整调用链速记"
2. 理解 4 个关键步骤：HTTP API → 引擎初始化 → 无障碍事件 → 事件分发

### 中级（理解核心机制）
1. 阅读 KEEP_ALIVE_CALL_CHAIN.md 的前 5 章
2. 重点关注：
   - HTTP API 如何触发引擎
   - ListenWindow 如何注册
   - 无障碍事件如何回调

### 高级（掌握细节实现）
1. 通读 KEEP_ALIVE_CALL_CHAIN.md 所有章节
2. 对照代码逐行分析
3. 使用 QUICK_REFERENCE.md 的快速搜索指令查阅

---

## 🚀 常见任务查询

### 我要快速找到 HTTP API 入口
→ 打开 **QUICK_REFERENCE.md**，查看"关键代码片段 1"

### 我要理解 ListenWindow 是如何工作的
→ 打开 **KEEP_ALIVE_CALL_CHAIN.md** 第 3 章

### 我要知道无障碍事件如何分发给 delegate
→ 打开 **KEEP_ALIVE_CALL_CHAIN.md** 第 5 和 6 章

### 我要添加调试断点
→ 打开 **QUICK_REFERENCE.md**，查看"调试技巧"部分

### 我要查找特定的类或方法
→ 打开 **QUICK_REFERENCE.md**，查看"快速搜索指令"部分

### 我要排查为什么 ListenWindow 未触发
→ 打开 **QUICK_REFERENCE.md**，查看"常见问题排查"部分

### 我要了解整个生命周期
→ 打开 **QUICK_REFERENCE.md**，查看"生命周期顺序"部分

---

## 📊 文档数据统计

| 文档 | 行数 | 大小 | 主要内容 |
|------|------|------|--------|
| KEEP_ALIVE_CALL_CHAIN.md | 650 | 22K | 完整调用链分析 ⭐ |
| QUICK_REFERENCE.md | 377 | 12K | 快速参考卡 ⚡ |
| VENDOR_RUNTIME_ANALYSIS.md | 213 | 8.6K | 运行时分析 |
| DROPPER_APK_RUNTIME_ANALYSIS.md | 308 | 16K | APK 运行时分析 |
| **总计** | **1548** | **58.6K** | 完整技术文档集 |

---

## 🔍 关键概念快速解释

### HTTP API 触发
- **位置**: `ApiRouter.java:312`
- **接口**: `GET /testOppoKeepAlive`
- **端口**: 7910
- **作用**: 通过 HTTP 请求触发保活引擎启动

### 引擎初始化
- **位置**: `AccessibilityDelegateManager.java:336`
- **方法**: `b(String packageName)`
- **作用**: 创建并启动厂商特定的保活引擎（OPPO/Xiaomi/Huawei 等）

### ListenWindow 注册
- **位置**: `OppoEngine.java:91`
- **方法**: `buildAllListenWindows()`
- **作用**: 定义需要监听的窗口（包名/类名/事件类型/过滤器）

### 无障碍事件回调
- **位置**: `MyAccessibilityService.java:1323`
- **方法**: `onAccessibilityEvent(AccessibilityEvent event)`
- **作用**: 接收所有无障碍事件并分发给各个 delegate

### 事件分发
- **位置**: `MyAccessibilityService.java:1077`
- **方法**: `f0(AccessibilityEvent event)`
- **作用**: 将事件分发给所有活跃的 delegate

### Delegate 事件回调
- **位置**: `AccessibilityDelegate.java:960`
- **方法**: `u(AccessibilityEvent event, String pkg, String class)`
- **作用**: 委托处理特定的无障碍事件

### 事件分发器
- **位置**: `DelegateEventDispatcher.java:92`
- **方法**: `run()`
- **作用**: 根据事件类型进行不同的处理（case 0-12）

---

## 💡 核心设计模式

### 1. Delegate 模式
- 每个 delegate 代表一个监听对象
- delegate 可以有多个 ListenWindow
- 无障碍事件首先分发给所有 delegate

### 2. ListenWindow 机制
- 定义：包名 + 类名 + 事件类型 + 内容过滤器
- 匹配：事件来自该包名且类名相同且事件类型在订阅列表中且通过内容过滤
- 执行：匹配成功后执行预定义的动作（点击、设置参数等）

### 3. 事件分发模式
- 异步处理：使用 DelegateTaskLauncher 在线程池中处理事件
- 队列机制：事件放入队列，由分发器线程处理
- 优先级控制：通过不同的 case 值实现优先级

---

## ⚙️ 配置和定制

### 如何添加新的 ListenWindow?
→ 在对应引擎类（如 OppoEngine.java）中添加新的 `buildXxx()` 方法

### 如何改变 delegate 的行为?
→ 继承 AccessibilityDelegate 并覆盖 `e()` 方法

### 如何添加新的厂商支持?
→ 在 AccessibilityDelegateManager.java 的 `b()` 方法中添加新的 if 分支

---

## 🛠️ 故障排查指南

### ListenWindow 未触发
检查清单：
1. ✓ eventType 是否在 ListenWindow.eventTypes 中?
2. ✓ packageName 是否与实际包名相同?
3. ✓ className 是否与实际类名相同?
4. ✓ 内容过滤器是否通过?

### delegate.u() 未被调用
检查清单：
1. ✓ delegate.o() 是否返回 true?
2. ✓ delegate.l() 中是否包含该 eventType?
3. ✓ onAccessibilityEvent() 中 f0(event) 是否被执行?

### 应用详情页未启动
检查清单：
1. ✓ DelegateEventDispatcher(case=4) 是否执行?
2. ✓ SystemHelper.Z0() 是否返回 true?
3. ✓ 是否有启动 Activity 的权限?

---

## 📝 总结

本分析文档集详细记录了保活引擎从 HTTP API 触发到无障碍事件处理的完整流程，包括：

- 清晰的代码位置定位
- 逐步的流程分析
- 关键类和方法的详细说明
- 快速参考和查询工具
- 故障排查指南

**建议用法**：
1. 初次了解时：先读 QUICK_REFERENCE.md
2. 深入学习时：读 KEEP_ALIVE_CALL_CHAIN.md
3. 查询时：用 QUICK_REFERENCE.md 的搜索指令

---

**文档最后更新**: 2026-04-12
**覆盖范围**: 完整保活引擎调用链
**难度级别**: 中到高
**建议阅读时间**: 30-60 分钟
