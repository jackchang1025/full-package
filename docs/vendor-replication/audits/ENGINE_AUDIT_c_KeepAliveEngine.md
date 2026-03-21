# ENGINE AUDIT: o/c.java → KeepAliveEngine 基类

> Vendor: `decompiled_vendor/sources/o/c.java` (801行)
> Replica: `AutoEngine.java` (725行, 合并了 e.java + c.java)
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor | Replica |
|------|--------|---------|
| 类名 | `o.c` | `AutoEngine` (合并) |
| 继承 | `extends o.e` | 无 (顶层) |
| 修饰 | `abstract` | `abstract` |
| 行数 | 801 | 725 |
| 角色 | 6 个厂商引擎的公共基类，提供 Switch/CheckBox 操作、电池优化对话框处理 | 所有引擎的统一基类 |

## 2. 字段映射

| Vendor 字段 | 类型 | Replica 字段 | 状态 | 说明 |
|-------------|------|-------------|------|------|
| `f609n` | `ConcurrentLinkedQueue` | `stateQueue` | ✅ | 状态队列 (已处理的步骤) |
| `f610o` | `ReentrantLock` | `lock` | ✅ | 线程锁 |
| `f611p` | `ScheduledExecutorService` | `scheduler` | ✅ | 定时任务调度器 |
| `q` | `AtomicBoolean` | `finished` | ⚠️ | vendor 是暂停标志, replica 是完成标志 |

## 3. 方法映射

### 3.1 静态 CombineFilter 构建方法

| Vendor 方法 | 返回类型 | Replica | 状态 | 说明 |
|-------------|----------|---------|------|------|
| `H(String)` | `CombineFilter` | `CombineFilter.textView(text)` | ⚠️ | TextView + text.contains 匹配 |
| `I()` | `CombineFiltersWithOr` | *(无)* | ❌ | 电池优化对话框"允许"按钮 (android:id/button1 OR settings:id/btn_positive) |
| `J()` | `ListenWindow` | *(无)* | ❌ | 电池优化对话框 ListenWindow (com.android.settings/android.app.Dialog) |
| `K()` | `CombineFilter` | *(无)* | ❌ | clickable LinearLayout 匹配 |
| `L()` | `CombineFilter` | *(无)* | ❌ | clickable 任意控件匹配 |
| `N()` | `CombineFilter` | *(无)* | ❌ | 对话框取消按钮 (android:id/button1) |
| `U()` | `CombineFilter` | `CombineFilter.scrollable()` | ⚠️ | scrollable 控件匹配 |
| `V()` | `CombineFiltersWithOr` | *(无)* | ❌ | scrollable OR (ListView/ScrollView/RecyclerView) |
| `a0()` | `CombineFilter` | *(无)* | ❌ | Switch 控件匹配 (android.widget.Switch) |

### 3.2 静态操作方法

| Vendor 方法 | Replica | 状态 | 说明 |
|-------------|---------|------|------|
| `M()` | *(无)* | ❌ | 点击电池优化对话框取消按钮 |
| `W()` | `Z()` 中调用 | ✅ | offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK") |
| `Y()` | *(无)* | ❌ | 检查并关闭对话框 + 检查屏幕状态 |

### 3.3 核心实例方法 — Switch/CheckBox 操作

| Vendor 方法 | 签名 | Replica | 状态 | 说明 |
|-------------|------|---------|------|------|
| `P()` | `static CheckedResult P(UiObject)` | *(无)* | ❌ | CompoundButton 查找+点击+验证 |
| `O()` | `CheckedResult O(UiObject, int)` | *(无)* | ❌ | Switch/CheckBox 查找+点击+验证 (OR 匹配) |
| `R()` | `CheckedResult R(UiObject, int)` | *(无)* | ❌ | Switch 坐标点击+验证 (用 g.s() 坐标点击) |
| `S()` | `static CheckedResult S(UiObject)` | *(无)* | ❌ | Switch 查找+坐标点击 (boundsInScreen.right-80) |

#### P() — CompoundButton 操作 (核心!)

```
逻辑:
1. 构建 CombineFilter: className=android.widget.CompoundButton
2. 从目标节点向上遍历 parent (最多 2 层) 查找 CompoundButton
3. 如果找到且 checked=false:
   a. 先尝试 click()
   b. 等待 200ms, 刷新, 检查 checked (最多重试 5 次)
   c. 如果仍未 checked, 查找 clickable 父节点并点击
   d. 再次等待+验证
4. 返回 CheckedResult { checked, clicked }
```

#### R() — Switch 坐标点击 (华为特有!)

```
逻辑:
1. 构建 CombineFilter: className=android.widget.Switch
2. 从目标节点向上遍历 parent 查找 Switch
3. 如果找到且 checked=false:
   a. 计算点击坐标: x = boundsInScreen.right - 50, y = centerInScreen.y
   b. 调用 g.s(x, y) 执行坐标点击 (dispatchGesture)
   c. 刷新根节点, 重新查找 Switch, 验证 checked
   d. 如果仍未 checked, 查找 clickable 父节点并 click()
   e. 最多重试 5 次
4. 返回 CheckedResult { checked, clicked }
```

### 3.4 生命周期方法

| Vendor 方法 | Replica | 状态 | 说明 |
|-------------|---------|------|------|
| `T()` | `isCompleted()` | ⚠️ | vendor 检查 q (暂停), replica 检查 finished |
| `X()` | `X()` | ✅ | 暂停事件处理 |
| `Z()` | `Z()` | ✅ | 抽象方法, 子类实现完成逻辑 |
| `d()` | `destroy()` | ✅ | 销毁: shutdownNow + 清理队列 + super.d() |
| `Q()` | `Q()` | ✅ | 获取可滚动节点 |
| `u()` | `onAccessibilityEvent()` | ⚠️ | 事件处理入口 |

### 3.5 u() — 事件处理入口 (关键!)

```java
// vendor c.java:762-801 (smali)
public void u(AccessibilityEvent r3, String r4, String r5) {
    super.u(r3, r4, r5);  // 调用 e.u() 处理 EventSubscribe

    // 检查是否进入电池优化对话框
    ListenWindow dialog = J();  // com.android.settings/android.app.Dialog
    if (q(Collections.singletonList(dialog))) {
        Log.d("o.c", "已进入是否允许忽略电池优化窗口");

        // 状态机: 避免重复处理
        if (!f609n.contains("keepInBatteryUnRestricted")) {
            f609n.add("keepInBatteryUnRestricted");
            // 异步执行: 点击"允许"按钮
            thread.l.c(new a(this, 0), this.c);
        }
    }
}
```

这意味着所有厂商引擎在处理事件时，会自动检测并处理电池优化对话框。

## 4. 核心缺失分析

### 4.1 Switch/CheckBox 操作系统 ❌

Vendor 提供了 4 种 Switch/CheckBox 操作方法:

| 方法 | 目标控件 | 点击方式 | 使用场景 |
|------|----------|----------|----------|
| `P()` | CompoundButton | click() | 通用开关 |
| `O()` | Switch OR CheckBox | click() | 厂商设置页 |
| `R()` | Switch | 坐标点击 (right-50) | 华为启动管理 |
| `S()` | Switch | 坐标点击 (right-80) | 其他厂商 |

Replica 没有这套统一的 Switch 操作系统，各引擎各自实现。

### 4.2 电池优化对话框自动处理 ❌

Vendor 在 `u()` 中自动检测 `com.android.settings/android.app.Dialog`，匹配到后自动点击"允许"按钮。这是所有厂商引擎共享的逻辑。

### 4.3 CheckedResult 验证机制 ❌

Vendor 的 Switch 操作返回 `CheckedResult { checked, clicked }`，支持:
- 点击后验证 checked 状态
- 最多重试 5 次 (每次 200ms 间隔)
- 如果 click() 失败，尝试点击 clickable 父节点
- 如果仍失败，尝试坐标点击

## 5. 共享 ListenWindow 规则

`c.java` 定义了所有厂商引擎共享的 ListenWindow:

```java
// J() — 电池优化对话框
ListenWindow("com.android.settings", "android.app.Dialog")
  eventTypes: [32 (WINDOW_CONTENT_CHANGED), 16384 (VIEW_SCROLLED)]
```

对应的 CombineFilter:
```java
// I() — "允许"按钮匹配 (OR)
Filter1: className=Button AND id=android:id/button1
Filter2: className=Button AND id=com.android.settings:id/btn_positive
```

## 6. 复刻优先级

| 优先级 | 缺失功能 | 影响 |
|--------|----------|------|
| P0 | Switch/CheckBox 操作 (P/O/R/S) | 厂商设置页无法操作开关 |
| P0 | 电池优化对话框自动处理 | 所有厂商引擎缺少共享逻辑 |
| P1 | CheckedResult 验证+重试 | 操作可靠性不足 |
| P1 | 坐标点击 (g.s()) | 华为等厂商 Switch 无法操作 |
| P2 | CombineFiltersWithOr 搜索 | 多条件 OR 匹配缺失 |
