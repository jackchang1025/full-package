# ENGINE AUDIT: o/i0.java → vivo VivoEngine

> Vendor: `decompiled_vendor/sources/o/i0.java` (684行, 最大的厂商引擎)
> Replica: `vendor/VivoEngine.java` + `VivoKeepAliveEngine.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor |
|------|--------|
| 类名 | `o.i0` |
| 继承 | `extends o.c` (KeepAliveEngine) |
| 构造 | `super(u0(), "com.android.settings")` |
| 超时 | 120 秒 (最长) |

## 2. 字段 (11 个 — 最多)

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f649r` | `AtomicReference<r.e>` | 当前保活目标 |
| `f650s` | `AtomicReference<String>` | 当前导航阶段 (状态机 phase) |
| `f651t` | `AtomicBoolean` | 主应用自启动 |
| `f652u` | `AtomicBoolean` | 备份应用自启动 |
| `f653v` | `AtomicBoolean` | 主应用关联启动 (默认 true) |
| `f654w` | `AtomicBoolean` | 备份应用关联启动 (默认 true) |
| `f655x` | `AtomicBoolean` | 主应用完全后台 |
| `f656y` | `AtomicBoolean` | 备份应用完全后台 |
| `f657z` | `AtomicBoolean` | 主应用后台弹窗 |
| `A` | `AtomicBoolean` | 备份应用后台弹窗 |

## 3. u0() — ListenWindow 列表 (17 个, 最多)

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | android.app.Dialog | 电池优化对话框 (共享) |
| 1-2 | com.android.settings | ...InstalledAppDetailsTop | 应用详情 (主/备份) |
| 3-4 | com.android.settings | ...VivoSubSettings | vivo 子设置 (主/备份) |
| 5 | com.android.permissioncontroller | ...ManagePermissionsActivity | 权限管理 |
| 6 | com.android.settings | android.widget.FrameLayout | 设置 FrameLayout |
| 7 | com.vivo.permissionmanager | ...SoftPermissionDetailActivity | 权限详情 |
| 8-9 | *(null)* | *(null)* | 通用匹配 (主/备份包名) |
| 10 | com.vivo.permissionmanager | ...originui.widget.dialog.h | vivo 对话框 |
| 11 | com.vivo.permissionmanager | android.app.AlertDialog | 权限对话框 |
| 12 | com.iqoo.powersaving | ...PowerRankActivity | iQOO 电池排行 |
| 13 | com.vivo.abe | ...ExcessivePowerManagerActivity | vivo 耗电管理 |
| 14 | com.iqoo.powersaving | ...ExcessivePowerManagerActivity | iQOO 耗电管理 |
| 15 | com.vivo.abe | ...ExcessivePowerDescriptionActivity | vivo 耗电详情 |
| 16 | com.iqoo.powersaving | ...ExcessivePowerDescriptionActivity | iQOO 耗电详情 |

## 4. 状态机 — 7 个状态 (最复杂)

```
u() 事件处理 — 基于 f650s (phase) + 窗口匹配的双重条件:

phase="prepareInAppPowerRank" + p0() → keepAliveInPowerRank → h0(1)
  └─ 电池排行页: 查找应用 → 点击进入耗电管理

phase="prepareInExcessivePowerManager" + n0() → keepAliveInExcessivePowerManager → h0(2)
  └─ 耗电管理页: 操作后台耗电开关

phase="prepareInExcessivePowerDescription" + m0() → keepAliveInExcessivePowerDescription → h0(3)
  └─ 耗电详情页: 操作详细设置

phase="prepareInAppDetailSetting" + j0() → keepAliveInAppDetail → h0(4)
  └─ 应用详情页: 查找权限入口 → 点击

phase="prepareInAppPermissionManage" + l0() → keepAliveInAppPermissionManage → h0(5)
  └─ 权限管理页: 查找"所有权限" → 点击

phase="prepareInAppPermissionDetail" + k0() → keepAliveInAppPermissionDetail → h0(6)
  └─ 权限详情页: 操作各权限开关

phase="prepareInPermissionAllowDialog" + o0() → keepAliveInPermissionAllowDialog → h0(7)
  └─ 权限允许对话框: 点击"允许"
```

## 5. CombineFilter 配置 Key

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `b0()` | VIVO_ALLOW_TEXT | 允许按钮 |
| `D0()` | VIVO_APP_ALL_PERMISSION_TEXT | 所有权限 |
| `E0()` | VIVO_BACKGROUND_POWER_MANAGER_TEXT | 后台耗电管理 |
| `H0()` | VIVO_APP_PERMISSION_TEXT | 应用权限 |
| `i0()` | VIVO_AUTO_START_TEXT | 自启动 |
| `w0()` | VIVO_POPUP_IN_BACKGROUND_TEXT | 后台弹窗 |

## 6. 目标包名覆盖

| 包名 | 说明 |
|------|------|
| `com.android.settings` | 系统设置 |
| `com.android.permissioncontroller` | 权限控制器 |
| `com.vivo.permissionmanager` | vivo 权限管理 |
| `com.vivo.abe` | vivo 应用行为引擎 |
| `com.iqoo.powersaving` | iQOO 省电管理 |

## 7. 特殊操作

### A0() — 启动耗电管理 (Intent)

```java
ComponentName("com.iqoo.powersaving", "...PowerRankActivity")
flags: FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK
       | FLAG_ACTIVITY_NO_ANIMATION | FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
```

### q0() — 手势滚动 + 坐标点击

```java
// 从底部滚动到顶部
g.S(10L, 1000L, Point(width/2, height-navBar-100), Point(width/2, statusBar))
// 等待 2 秒后点击屏幕底部
g.s(width/2, height-navBar-200)
```

### y0() — 保活状态持久化 (含后台弹窗)

vivo 比其他厂商多一个 `allowPopupInBackground` 字段。

## 8. 与其他厂商引擎的对比

| 维度 | 华为 (v) | 小米 (q) | 华为启动 (n) | vivo (i0) |
|------|----------|----------|-------------|-----------|
| ListenWindow 数 | 12 | 16 | 7 | 17 |
| 状态数 | 4 | 2 | 4 | 7 |
| 字段数 | 4 | 8 | 7 | 11 |
| 超时 | 100s | 100s | 50s | 120s |
| 行数 | 526 | 498 | 454 | 684 |
| 复杂度 | 中 | 中 | 低 | 高 |
| 特殊 | 坐标点击 | 滚动查找 | Switch 反转 | 手势+坐标+Intent |
