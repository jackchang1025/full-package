# ENGINE AUDIT: o/v.java → 华为 HuaweiEngine

> Vendor: `decompiled_vendor/sources/o/v.java` (526行)
> Replica: `vendor/HuaweiEngine.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor | Replica |
|------|--------|---------|
| 类名 | `o.v` | `HuaweiEngine` |
| 继承 | `extends o.c` (KeepAliveEngine) | `extends AutoEngine` |
| 行数 | 526 | *(需检查)* |
| 构造 | `super(w0(), "com.android.settings")` | *(需检查)* |

## 2. 字段映射

| Vendor 字段 | 类型 | Replica 字段 | 状态 | 说明 |
|-------------|------|-------------|------|------|
| `f699v` | `static int` | *(无)* | ❌ | 合成字段 (switch 表) |
| `f700r` | `AtomicReference<r.e>` | *(需检查)* | ⚠️ | 当前保活目标 (MAIN_APP / BACKUP_APP) |
| `f701s` | `AtomicBoolean` | *(需检查)* | ⚠️ | 完全允许后台行为 已完成 |
| `f702t` | `AtomicBoolean` | *(需检查)* | ⚠️ | 允许自启动 已完成 |
| `f703u` | `AtomicBoolean` | *(需检查)* | ⚠️ | 允许关联启动 已完成 |

## 3. 构造函数

```java
// vendor v.java:42-53
public v() {
    super(w0(), "com.android.settings");  // 传入 12 个 ListenWindow + 主包名
    f700r = new AtomicReference(r.e.KEEP_ALIVE_UNKNOWN);
    f701s = new AtomicBoolean(false);  // 完全允许后台
    f702t = new AtomicBoolean(false);  // 允许自启动
    f703u = new AtomicBoolean(false);  // 允许关联启动
    // 100 秒超时自动结束
    f611p.schedule(new u(this, 4), 100L, TimeUnit.SECONDS);
}
```

## 4. w0() — ListenWindow 列表 (12 个窗口规则)

```java
public static LinkedList w0() {
    LinkedList list = new LinkedList();
    list.add(c.J());                          // [0] 电池优化对话框 (共享)
    list.add(A0(g.x0()));                     // [1] 应用详情 (主包名)
    list.add(A0(g.e()));                      // [2] 应用详情 (备份包名)
    list.add(v0(g.x0()));                     // [3] 设置 FrameLayout (主包名)
    list.add(v0(g.e()));                      // [4] 设置 FrameLayout (备份包名)
    list.add(y0());                           // [5] oplus 耗电管理 Activity
    list.add(q0());                           // [6] coloros 耗电管理 Activity
    list.add(g0());                           // [7] oplus 对话框 (允许按钮)
    list.add(n0());                           // [8] oplus coui 对话框
    list.add(h0());                           // [9] oplus 通用 (允许按钮)
    list.add(o0());                           // [10] coloros 通用 (允许按钮)
    list.add(z0());                           // [11] oplus 自启动管理
    return list;
}
```

### 4.1 ListenWindow 详细规则

| # | 方法 | packageName | className | matchs | eventTypes |
|---|------|-------------|-----------|--------|------------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | *(无)* | 32, 16384 |
| 1 | `A0(主包名)` | com.android.settings | ...InstalledAppDetailsTop | H(主包名): TextView.text.contains(包名) | 32, 16384 |
| 2 | `A0(备份包名)` | com.android.settings | ...InstalledAppDetailsTop | H(备份包名) | 32, 16384 |
| 3 | `v0(主包名)` | com.android.settings | android.widget.FrameLayout | H(主包名) | 32, 16384 |
| 4 | `v0(备份包名)` | com.android.settings | android.widget.FrameLayout | H(备份包名) | 32, 16384 |
| 5 | `y0()` | com.oplus.battery | ...PowerControlActivity | *(无)* | 32, 16384 |
| 6 | `q0()` | com.coloros.oppoguardelf | ...PowerControlActivity | *(无)* | 32, 16384 |
| 7 | `g0()` | com.oplus.battery | androidx.appcompat.app.b | d0(): Button.text=允许按钮 | 32, 16384 |
| 8 | `n0()` | com.oplus.battery | com.coui.appcompat.dialog.app.a | d0(): Button.text=允许按钮 | 32, 16384 |
| 9 | `h0()` | com.oplus.battery | *(null=任意)* | d0(): Button.text=允许按钮 | 32, 16384 |
| 10 | `o0()` | com.coloros.oppoguardelf | *(null=任意)* | d0(): Button.text=允许按钮 | 32, 16384 |
| 11 | `z0()` | com.oplus.battery | ...StartupAppListActivity | *(无)* | 32, 16384 |

### 4.2 CombineFilter 详细规则

| 方法 | 目标控件 | 匹配条件 | 配置 Key |
|------|----------|----------|----------|
| `b0()` | TextView | text.contains(允许后台行为) | COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT |
| `c0()` | TextView | text.contains(允许自启动) | COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT |
| `d0()` | Button | text.contains(允许) | COLORS_SETTINGS_ALLOW_BUTTON_TEXT |
| `e0()` | TextView | text.contains(完全允许后台) | COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT |
| `f0()` | TextView | text.contains(关联启动) | COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT |
| `i0()` | TextView | text.contains(后台行为) | COLORS_APP_IN_BACKGROUND_TEXT |
| `B0()` | TextView | text=电源管理 | COLORS_SETTINGS_POWER_MANAGE_TEXT |
| `C0()` | TextView | text=电源管理2 | COLORS_SETTINGS_POWER_MANAGE_2_TEXT |

## 5. 状态机 — u() 事件处理

```java
// vendor v.java:439-491
public void u(AccessibilityEvent event, String pkg, String cls) {
    if (T()) return;  // 已暂停则跳过
    super.u(event, pkg, cls);  // 调用 c.u() → 处理电池优化对话框

    // 状态机: 互斥状态切换
    if (k0()) {  // 在应用详情页?
        remove("keepAliveInPowerControl", "keepAliveInAndroidXDialog", "keepAliveInStartup");
        if (!contains("keepAliveInAppDetail")) {
            add("keepAliveInAppDetail");
            thread.l.c(new u(this, 0), delegateId);  // → 异步处理应用详情
        }
    }
    if (l0()) {  // 在耗电管理页?
        remove("keepAliveInAppDetail", "keepAliveInAndroidXDialog", "keepAliveInStartup");
        if (!contains("keepAliveInPowerControl")) {
            add("keepAliveInPowerControl");
            thread.l.c(new u(this, 1), delegateId);  // → 异步处理耗电管理
        }
    }
    if (j0()) {  // 在允许对话框?
        remove("keepAliveInAppDetail", "keepAliveInPowerControl", "keepAliveInStartup");
        if (!contains("keepAliveInAndroidXDialog")) {
            add("keepAliveInAndroidXDialog");
            thread.l.c(new u(this, 2), delegateId);  // → 异步处理对话框
        }
    }
    if (m0()) {  // 在自启动管理页?
        remove("keepAliveInAppDetail", "keepAliveInPowerControl", "keepAliveInAndroidXDialog");
        if (!contains("keepAliveInStartup")) {
            add("keepAliveInStartup");
            thread.l.c(new u(this, 3), delegateId);  // → 异步处理自启动
        }
    }
}
```

### 状态转换图

```
事件到达 → u()
  ├─ k0() 匹配 → keepAliveInAppDetail → u(this, 0)
  │   └─ 应用详情页: 查找电源管理入口 → 点击进入
  ├─ l0() 匹配 → keepAliveInPowerControl → u(this, 1)
  │   └─ 耗电管理页: 操作 Switch (完全允许后台/自启动/关联启动)
  ├─ j0() 匹配 → keepAliveInAndroidXDialog → u(this, 2)
  │   └─ 允许对话框: 点击"允许"按钮
  └─ m0() 匹配 → keepAliveInStartup → u(this, 3)
      └─ 自启动管理页: 操作自启动开关
```

## 6. 核心操作方法

### 6.1 r0() — 完全允许后台行为

```java
// vendor v.java:351-383
public boolean r0() {
    // 1. 查找 "完全允许后台行为" 或 "允许后台行为" 文本所在的 clickable 行
    UiObject row = k().findOneByCombineWithChild(
        new CombineFilterWithChild(c.K(), e0()));  // clickable LinearLayout + 完全允许文本
    if (row == null) {
        row = k().findOneByCombineWithChild(
            new CombineFilterWithChild(c.K(), b0()));  // fallback: 允许后台行为
    }

    // 2. 操作 Switch (使用坐标点击 R())
    CheckedResult result = R(row, 0);  // R() = Switch 坐标点击 (right-50)

    // 3. 验证: 如果 checked 且没弹出对话框 → 成功
    if (result.isChecked()) {
        T0(10);  // 等待 2 秒
        if (!j0()) {  // 没弹出"允许"对话框
            f701s.set(true);
            return true;
        }
    }
    return false;
}
```

### 6.2 s0() — 允许自启动

```java
// vendor v.java:385-410
public boolean s0() {
    UiObject row = k().findOneByCombineWithChild(
        new CombineFilterWithChild(c.K(), c0()));  // clickable + 允许自启动文本
    CheckedResult result = R(row, 5);  // Switch 坐标点击, 重试 5 次
    if (result.isChecked()) {
        f702t.set(true);
        return true;
    }
    return false;
}
```

### 6.3 t0() — 允许关联启动

```java
// vendor v.java:412-437
public boolean t0() {
    UiObject row = k().findOneByCombineWithChild(
        new CombineFilterWithChild(c.K(), f0()));  // clickable + 关联启动文本
    CheckedResult result = R(row, 5);
    if (result.isChecked()) {
        f703u.set(true);
        return true;
    }
    return false;
}
```

### 6.4 u0() — 双应用保活流程

```java
// vendor v.java:493-525
public void u0() {
    if (f701s.get()) {  // 完全允许后台已完成
        if (当前是 MAIN_APP) {
            D0(主包名);  // 保存保活状态
            // 重置状态, 切换到备份应用
            f609n.clear();
            f701s/f702t/f703u.set(false);
            if (备份应用已处理 || 备份应用不存在) {
                Z();  // 结束
            } else {
                f700r.set(KEEP_ALIVE_BACKUP_APP);
                g.Z0("com.google.guard");  // 打开备份应用详情
            }
        } else if (当前是 BACKUP_APP) {
            D0("com.google.guard");
            Z();  // 结束
        }
    }
}
```

## 7. Z() — 完成流程

```java
// vendor v.java:243-283
public void Z() {
    if (lock.tryLock()) {
        if (!T()) {
            g.h(100);           // 进度 100%
            X();                // 暂停事件处理
            P().x();            // 清理无障碍缓存

            // 保存保活状态
            if (MAIN_APP) D0(主包名);
            if (BACKUP_APP) D0("com.google.guard");

            scheduler.shutdownNow();
            thread.l.a(delegateId);
            stateQueue.clear();

            if (屏幕亮着) T0(5);  // 等 1 秒

            // 移除遮罩
            if (需要PIP) {
                offerStrategyEvent("PREPARE_LEAVE_PIP");
            } else {
                e.b.d();        // 恢复亮度
                g.c();          // 移除遮罩 (RECENTS → sleep → removeView)
            }

            c.W();              // 通知策略线程
            d();                // 销毁
        }
        lock.unlock();
    }
}
```

## 8. 窗口匹配检查方法

| 方法 | 匹配的 ListenWindow | 含义 |
|------|---------------------|------|
| `k0()` | A0(包名) + v0(包名) | 在应用详情页 |
| `l0()` | y0() + x0() + q0() + p0() | 在耗电管理页 |
| `j0()` | g0() + n0() + h0() + o0() | 在"允许"对话框 |
| `m0()` | z0() | 在自启动管理页 |

## 9. 目标包名覆盖

| 包名 | 说明 | 覆盖版本 |
|------|------|----------|
| `com.android.settings` | Android 系统设置 | 所有 |
| `com.oplus.battery` | OPPO/OnePlus 电池管理 | ColorOS 12+ |
| `com.coloros.oppoguardelf` | ColorOS 守护精灵 | ColorOS 11 及以下 |

注意: 虽然文件名是 `v.java` (HuaweiEngine)，但实际上它同时覆盖了华为和 OPPO/ColorOS 的电池管理页面。这是因为华为 EMUI 和 ColorOS 共享了部分 UI 结构。

## 10. 复刻差异总结

| 功能 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 12 个 ListenWindow 规则 | ✅ 完整定义 | ⚠️ 简化 WindowMatcher | ❌ 需补充 |
| 状态机 (4 状态互斥) | ✅ ConcurrentLinkedQueue | ⚠️ 简化实现 | ⚠️ 需对齐 |
| Switch 坐标点击 R() | ✅ boundsInScreen.right-50 | ⚠️ 部分实现 | ⚠️ 需验证 |
| CombineFilterWithChild | ✅ 父子节点组合搜索 | ❌ 缺失 | ❌ 需实现 |
| 双应用保活 (主+备份) | ✅ u0() 流程 | ❌ 缺失 | ❌ 需实现 |
| 100 秒超时自动结束 | ✅ scheduler.schedule | ⚠️ 需检查 | ⚠️ |
| D0() 保活状态持久化 | ✅ SharedPreferences | ❌ 缺失 | ❌ 需实现 |
| 配置文本 (COLORS_*) | ✅ f.b() 多语言 | ⚠️ TextConfig | ⚠️ 需对齐 |
