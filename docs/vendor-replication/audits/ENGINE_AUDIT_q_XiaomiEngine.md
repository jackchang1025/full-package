# ENGINE AUDIT: o/q.java → 小米 XiaomiEngine

> Vendor: `decompiled_vendor/sources/o/q.java` (498行)
> Replica: `vendor/XiaomiEngine.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor | Replica |
|------|--------|---------|
| 类名 | `o.q` | `XiaomiEngine` |
| 继承 | `extends o.c` (KeepAliveEngine) | `extends AutoEngine` |
| 构造 | `super(l0(), "com.miui.securitycenter")` | *(需检查)* |

## 2. 字段映射

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f685r` | `AtomicReference<r.e>` | 当前保活目标 (MAIN_APP / BACKUP_APP) |
| `f686s` | `AtomicBoolean` | 主应用自启动 已完成 |
| `f687t` | `AtomicBoolean` | 备份应用自启动 已完成 |
| `f688u` | `AtomicBoolean` | 主应用关联启动 (默认 true) |
| `f689v` | `AtomicBoolean` | 备份应用关联启动 (默认 true) |
| `f690w` | `AtomicBoolean` | 主应用完全后台 已完成 |
| `f691x` | `AtomicBoolean` | 备份应用完全后台 已完成 |
| `f692y` | `AtomicBoolean` | 正在执行自启动流程 (锁) |

## 3. l0() — ListenWindow 列表 (16 个窗口规则)

| # | packageName | className | matchs | 说明 |
|---|-------------|-----------|--------|------|
| 0 | com.android.settings | android.app.Dialog | *(无)* | 电池优化对话框 (共享) |
| 1 | com.miui.securitycenter | ...AutoStartManagementActivity | *(无)* | MIUI 自启动管理 |
| 2 | com.miui.powerkeeper | ...HiddenAppsContainerManagementActivity | *(无)* | MIUI 省电策略容器 |
| 3-4 | com.miui.securitycenter | ...ApplicationsDetailsActivity | H(包名) | 应用详情 (主/备份) |
| 5-6 | com.miui.securitycenter | ...AppManagerMainActivity | H(包名) | 应用管理主页 (主/备份) |
| 7-8 | com.miui.securitycenter | android.widget.FrameLayout | H(包名) | 安全中心 FrameLayout (主/备份) |
| 9 | com.miui.powerkeeper | ...HiddenAppsConfigActivity | *(无)* | 省电策略配置 |
| 10 | com.miui.securitycenter | ...PowerDetailActivity | *(无)* | 电量详情 |
| 11 | com.miui.securitycenter | ...PermissionsEditorActivity | *(无)* | 权限编辑 |
| 12 | com.miui.securitycenter | ...OtherPermissionsActivity | *(无)* | 其他权限 |
| 13 | com.miui.securitycenter | ...PermissionAppsModifyActivity | *(无)* | 权限修改 |
| 14 | com.miui.powerkeeper | miuix.appcompat.app.AlertDialog | *(无)* | MIUI 对话框 |
| 15 | com.miui.securitycenter | miuix.appcompat.app.AlertDialog | *(无)* | 安全中心对话框 |

## 4. 状态机 — u() 事件处理

```
事件到达 → u()
  ├─ f692y=true → 跳过 (正在执行自启动流程)
  ├─ f0() 匹配 → keepAliveInAppDetail → p(this, 1)
  │   └─ 应用详情页: 查找省电策略入口 → c0() 滚动查找 → 点击进入
  └─ h0() 匹配 → keepAliveInAutoStartManage → p(this, 2)
      └─ 自启动管理页: i0() 滚动查找应用 → 操作 Switch
```

## 5. 核心操作方法

### 5.1 c0() — 省电策略导航

```
1. g.h(10) 进度 10%
2. Q() 获取滚动视图
3. scrollForwardEnd() 滚到底部
4. scrollBackwardUtil(d0()) 向上滚动查找 "省电策略" 文本
5. 如果没找到, scrollForwardUtil(b0()) 向下查找 "电量消耗"
6. 找到后 findParentUtilCombine(L()) 获取 clickable 父节点
7. click() 点击进入
8. 等待 g0() 匹配省电策略窗口 (最多 20 次, 每次 400ms)
9. k0() 执行省电策略操作
```

### 5.2 i0(String) — 自启动管理操作

```
1. Q() 获取滚动视图
2. scrollForwardUtil(H(包名)) 向下滚动查找应用名
3. 如果没找到, scrollBackwardUtil() 向上查找
4. 找到后 findParentUtilCombine(L()) 获取 clickable 父节点
5. O(parent, 5) 操作 Switch/CheckBox (重试 5 次)
```

### 5.3 j0() — 双应用保活流程

```
MAIN_APP:
  如果自启动未完成 → 启动 MIUI 自启动管理
  如果自启动已完成 → 保存状态 → 切换到 BACKUP_APP
BACKUP_APP:
  如果自启动未完成 → 启动 MIUI 自启动管理
  如果自启动已完成 → 保存状态 → Z() 结束
```

### 5.4 r0() — 手势滚动

```java
// 从底部滚动到顶部 (dispatchGesture)
g.S(10L, 1000L,
    new Point(width/2, height-navBar-100),  // 起点: 屏幕底部
    new Point(width/2, statusBar));          // 终点: 屏幕顶部
```

## 6. 目标包名覆盖

| 包名 | 说明 |
|------|------|
| `com.miui.securitycenter` | MIUI 安全中心 (自启动管理、应用管理) |
| `com.miui.powerkeeper` | MIUI 省电管理 (省电策略、后台管理) |
| `com.android.settings` | 系统设置 (电池优化对话框) |

## 7. CombineFilter 配置 Key

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `b0()` | MIUI_APP_POWER_CONSUME_TEXT | 电量消耗 |
| `d0()` | MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT | 省电策略 |

## 8. 与华为引擎的结构对比

| 维度 | 华为 (o/v) | 小米 (o/q) |
|------|-----------|-----------|
| 主包名 | com.android.settings | com.miui.securitycenter |
| ListenWindow 数 | 12 | 16 |
| 状态数 | 4 | 2 (+ 自启动锁) |
| Switch 操作 | R() 坐标点击 | O() click 点击 |
| 滚动方式 | 无 | scrollForward/BackwardUtil |
| 双应用 | u0() | j0() |
| 超时 | 100 秒 | 100 秒 |
