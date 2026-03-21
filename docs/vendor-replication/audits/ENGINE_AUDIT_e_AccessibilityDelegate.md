# ENGINE AUDIT: o/e.java → AccessibilityDelegate 基类

> Vendor: `decompiled_vendor/sources/o/e.java` (982行)
> Replica: `AutoEngine.java` (725行, 合并了 e.java + c.java)
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor | Replica |
|------|--------|---------|
| 类名 | `o.e` | `AutoEngine` |
| 继承 | 无 (顶层基类) | 无 (顶层基类) |
| 行数 | 982 | 725 |
| 角色 | 所有 Delegate 的基类，管理 ListenWindow + EventSubscribe 匹配引擎 | 简化基类，只有 WindowMatcher + 生命周期 |

## 2. 字段映射

| Vendor 字段 | 类型 | Replica 字段 | 状态 | 说明 |
|-------------|------|-------------|------|------|
| `f615a` | `String` | `primaryPackage` | ✅ | 监听的包名 |
| `b` | `com.guard.wallet.utils.i` | *(无)* | ❌ | ID 生成器 (雪花算法) |
| `c` | `String` | `engineName` | ⚠️ | vendor 用 ID 生成器生成, replica 用类名 |
| `f616d` | `ConcurrentLinkedQueue<ListenWindow>` | `matchWindows (List<WindowMatcher>)` | ⚠️ | vendor 用完整 ListenWindow, replica 简化为 WindowMatcher |
| `f617e` | `ConcurrentHashMap` | *(无)* | ❌ | EventSubscribe ID → 状态映射 |
| `f618f` | `ConcurrentHashMap` | *(无)* | ❌ | replySubscribes 回复订阅映射 |
| `f619g` | `AtomicInteger` | *(无)* | ❌ | 事件处理计数器 (初始 -1) |
| `f620h` | `AtomicReference` | `cachedRoot` | ✅ | 根节点缓存 |
| `f621i` | `AtomicBoolean` | `running` | ✅ | 运行状态 |
| `f622j` | `AtomicReference` | *(无)* | ❌ | 当前匹配的 ListenWindow 引用 |
| `f623k` | `AtomicReference` | *(无)* | ❌ | 当前匹配的 EventSubscribe 引用 |
| `f624l` | `AtomicReference` | *(无)* | ❌ | 上一次匹配的 EventSubscribe 引用 |
| `f625m` | `ConcurrentHashMap` | *(无)* | ❌ | listenHelper 辅助监听状态映射 |

## 3. 方法映射

### 3.1 构造函数

| Vendor | Replica | 状态 |
|--------|---------|------|
| `e(Collection<ListenWindow>, String)` | `AutoEngine(List<WindowMatcher>, String)` | ⚠️ |

Vendor 构造函数逻辑:
```java
// vendor e.java:70-97
public e(Collection collection, String str) {
    f616d = new ConcurrentLinkedQueue();  // ListenWindow 队列
    f617e = new ConcurrentHashMap();       // EventSubscribe 状态
    f618f = new ConcurrentHashMap();       // replySubscribes
    f619g = new AtomicInteger(-1);         // 事件计数
    f620h = new AtomicReference(null);     // 根节点缓存
    f621i = new AtomicBoolean(false);      // 运行状态
    f622j = new AtomicReference(null);     // 当前 ListenWindow
    f623k = new AtomicReference(null);     // 当前 EventSubscribe
    f624l = new AtomicReference(null);     // 上一个 EventSubscribe
    f625m = new ConcurrentHashMap();       // listenHelper
    b = new utils.i(10L);                  // ID 生成器
    c = String.valueOf(b.a());             // delegateId
    f615a = str;                           // packageName
    if (collection != null) f616d.addAll(collection);
    // 如果没有 ListenWindow 且有 packageName, 创建默认的
    if (!q.B(f615a) && f616d.isEmpty()) {
        f616d.add(new ListenWindow(c, f615a, null));
    }
}
```

### 3.2 静态方法

| Vendor 方法 | 签名 | Replica | 状态 | 说明 |
|-------------|------|---------|------|------|
| `A()` | `static void A(EventSubscribe, ArrayList<UiObject>)` | *(无)* | ❌ | 执行 replyActions — 核心自动化执行器 |
| `s()` | `static LinkedList s(EventSubscribe, UiObject)` | *(无)* | ❌ | 按 sourceRule 搜索目标节点 |

#### A() — replyActions 执行器 (核心!)

```java
// vendor e.java:99-143
// 遍历匹配到的 UiObject 列表, 对每个执行 replyActions
for (UiObject uiObject : arrayList) {
    for (TargetActionCondition action : eventSubscribe.getReplyActions()) {
        if (action.getActionType() == 0) {
            // GlobalAction (如 BACK, HOME, RECENTS)
            result = g.a(action.toGlobalActionCondition());
        } else {
            // 节点操作 (click, setText, scroll 等)
            uiObject.refresh();
            result = uiObject.actionByName(action);
        }
        if (result) {
            Thread.sleep(eventGap > 0 ? eventGap * 1000 : 300);
        }
    }
}
```

#### s() — 节点搜索器

```java
// vendor e.java:153-211 (smali)
// 按 sourceRule 决定搜索策略:
//   sourceRule=0 或 10: selector.t(root) — 查找第一个匹配
//   sourceRule=1: selector.q(root) — 查找父节点匹配
//   sourceRule=2: selector.r(root) — 查找所有匹配 (集合)
//   其他: Log "无效节点检索规则"
```

### 3.3 实例方法

| Vendor 方法 | 签名 | Replica | 状态 | 说明 |
|-------------|------|---------|------|------|
| `B()` | `void B(EventSubscribe)` | *(无)* | ❌ | 处理 replySubscribes 链式订阅 |
| `C()` | `SearchNodeListResultVO C(UiObjectCollection)` | *(无)* | ❌ | 集合节点搜索结果封装 |
| `D()` | `SearchNodeResultVO D(UiObject)` | *(无)* | ❌ | 单节点搜索结果封装 |
| `E()` | `void E(EventSubscribe, Long)` | *(无)* | ❌ | 事件时间戳更新 + eventGap 去抖 |
| `F()` | `void F(UiObject)` | *(无)* | ❌ | 刷新根节点缓存 |
| `G()` | `void G()` | `activateRoot()` | ⚠️ | vendor 清空缓存, replica 刷新缓存 |
| `a()` | `void a(String)` | *(无)* | ❌ | 触发点击采集 (listenType=9) |
| `b()` | `boolean b(EventSubscribe)` | *(无)* | ❌ | 检查 eventGap 去抖 |
| `c()` | `boolean c(String, String)` | `matchWindow()` | ⚠️ | 窗口匹配 |
| `d()` | `void d()` | `destroy()` | ⚠️ | 销毁 — vendor 清理所有队列和映射 |
| `e()` | `void e(ListenWindow, j0)` | *(无)* | ❌ | 处理单个 ListenWindow 的事件 |
| `f()` | `SearchNodeListResultVO f(CombineFilterWithChild)` | *(无)* | ❌ | 带子节点的组合过滤搜索 |
| `g()` | `SearchNodeListResultVO g(CombineFilterWithChild)` | *(无)* | ❌ | 带子节点的组合过滤搜索 (变体) |
| `h()` | `SearchNodeListResultVO h(CombineFiltersWithOr)` | *(无)* | ❌ | OR 组合过滤搜索 |
| `i()` | `SearchNodeResultVO i(CombineFiltersWithOr)` | *(无)* | ❌ | OR 组合过滤搜索 (单结果) |
| `j()` | `void j(String)` | *(无)* | ❌ | 触发键盘输入采集 (listenType=8) |
| `k()` | `UiObject k()` | `k()` | ✅ | 获取根节点 |
| `l()` | `LinkedHashSet l()` | *(无)* | ❌ | 获取所有 ListenWindow 的 listenProps 集合 |
| `m()` | `UiObject m(int, String)` | *(无)* | ❌ | 按索引和 ID 查找节点 |
| `n()` | `UiObject n(CombineFilter)` | `findOneByCombine()` | ⚠️ | CombineFilter 节点搜索 |
| `o()` | `boolean o()` | *(无)* | ❌ | 检查是否有 listenHelper |
| `p()` | `boolean p(ListenWindow, UiObject)` | *(无)* | ❌ | ListenWindow 匹配检查 (matchs + dismiss) |
| `q()` | `boolean q(List)` | *(无)* | ❌ | 批量 ListenWindow 匹配 |
| `r()` | `ArrayList r(EventSubscribe, UiObject)` | *(无)* | ❌ | EventSubscribe 节点搜索 |
| `t()` | `boolean t(CombineFilter, UiObject)` | *(无)* | ❌ | CombineFilter 匹配检查 |
| `u()` | `void u(AccessibilityEvent, String, String)` | `onAccessibilityEvent()` | ⚠️ | 事件处理入口 |
| `v()` | `void v(UiObject, boolean, String, String, String)` | *(无)* | ❌ | listenHelper 回调处理 |
| `w()` | `void w(boolean)` | *(无)* | ❌ | 发送 listenHelper 请求 |
| `x()` | `void x(EventSubscribe, ArrayList, String, String, Long)` | *(无)* | ❌ | 完整事件处理流水线 |
| `y()` | `SearchNodeResultVO y(int, String)` | *(无)* | ❌ | 按索引搜索节点 |
| `z()` | `void z(EventSubscribe)` | *(无)* | ❌ | EventSubscribe 状态重置 |

## 4. 核心缺失分析

### 4.1 EventSubscribe 匹配引擎 ❌

Vendor `o/e.java` 的核心是一个完整的事件订阅匹配引擎:

```
AccessibilityEvent 到达
  → u() 入口
    → 遍历 f616d (ListenWindow 队列)
      → p() 检查 ListenWindow.matchs 是否匹配当前窗口
      → 遍历 ListenWindow.eventSubscribes
        → b() 检查 eventGap 去抖
        → r() 用 EventSubscribe.combineFilter 搜索目标节点
        → A() 执行 replyActions (click/setText/scroll/globalAction)
        → B() 处理 replySubscribes 链式触发
        → v()/w() 处理 listenHelper 回调
```

Replica 的 `AutoEngine` 完全没有这套机制。它用的是硬编码的 `onWindowMatched()` + `execute()` 模式。

### 4.2 ListenWindow 动态规则 ❌

Vendor 的 ListenWindow 包含:
- `matchs` — 窗口匹配条件 (CombineFilter 列表)
- `dismiss` — 窗口消失条件
- `eventSubscribes` — 事件订阅列表 (含 combineFilter + replyActions)
- `eventTypes` — 监听的事件类型

Replica 的 `WindowMatcher` 只有:
- `packageName` + `className` + `eventTypes`

### 4.3 replyActions 自动执行 ❌

Vendor 的 `A()` 方法可以自动执行:
- `actionType=0` → GlobalAction (BACK/HOME/RECENTS)
- 其他 → 节点操作 (click/setText/scroll/longClick 等)
- 支持 eventGap 延迟控制

Replica 没有这套声明式执行系统，所有操作都是在各引擎中硬编码。

### 4.4 listenHelper 辅助监听 ❌

Vendor 支持 `listenHelper` 机制:
- `TOUCH_POINT` — 触摸坐标采集
- `GESTURE_POINTS` — 手势轨迹采集
- 通过 HTTP 回调发送到本地服务

Replica 完全没有实现。

## 5. 复刻优先级

| 优先级 | 缺失功能 | 影响 |
|--------|----------|------|
| P0 | EventSubscribe 匹配引擎 | 厂商引擎的 ListenWindow 规则无法生效 |
| P0 | replyActions 执行器 | 无法自动执行 click/setText 等操作 |
| P1 | ListenWindow matchs/dismiss | 窗口匹配精度不足 |
| P1 | eventGap 去抖 | 可能重复触发操作 |
| P2 | replySubscribes 链式触发 | 复杂多步操作无法串联 |
| P2 | listenHelper | 数据采集功能缺失 |
| P3 | sourceRule 搜索策略 | 节点搜索灵活性不足 |

## 6. 结论

Replica 的 `AutoEngine` 是对 vendor `o/e.java` + `o/c.java` 的大幅简化重写。它保留了基本的窗口匹配和生命周期管理，但完全缺失了 vendor 的核心 — **声明式事件订阅匹配引擎**。

要实现一比一复刻，需要在 `AutoEngine` 中补充完整的 `EventSubscribe` 处理流水线，或者新建一个 `AccessibilityDelegate` 类来承载这套逻辑。
