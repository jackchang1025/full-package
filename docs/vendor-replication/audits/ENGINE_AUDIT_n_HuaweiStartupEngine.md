# ENGINE AUDIT: o/n.java → 华为/荣耀 OppoEngine (实际是 HuaweiStartupEngine)

> Vendor: `decompiled_vendor/sources/o/n.java` (454行)
> Replica: `vendor/OppoEngine.java` (映射名有误，实际是华为启动管理引擎)
> 审计日期: 2026-03-21

## 重要发现

**文件名映射修正**: `o/n.java` 虽然在 replica 中映射为 `OppoEngine`，但从源码分析来看，它实际上是**华为/荣耀启动管理引擎**：
- 配置 Key 全部以 `HUA_WEI_` 开头
- 目标包名: `com.huawei.systemmanager` + `com.hihonor.systemmanager`
- 窗口: `HWSettings`, `StartupAppControlActivity`
- 日志: "华为系统设置窗口", "应用启动管理窗口"

与 `o/v.java` (HuaweiEngine) 的区别:
- `o/v.java` → 华为**电池/耗电管理** (com.oplus.battery)
- `o/n.java` → 华为**启动管理** (com.huawei.systemmanager)

## 1. 类定义

| 属性 | Vendor |
|------|--------|
| 类名 | `o.n` |
| 继承 | `extends o.c` (KeepAliveEngine) |
| 构造 | `super(s0(), "com.android.settings")` |
| 超时 | 50 秒 (比其他引擎短) |

## 2. 字段映射

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f674r` | `AtomicReference<r.e>` | 当前保活目标 (UNKNOWN → MAIN_APP → BACKUP_APP) |
| `f675s` | `AtomicBoolean` | 主应用自启动 已完成 |
| `f676t` | `AtomicBoolean` | 备份应用自启动 已完成 |
| `f677u` | `AtomicBoolean` | 主应用关联启动 (默认 true) |
| `f678v` | `AtomicBoolean` | 备份应用关联启动 (默认 true) |
| `f679w` | `AtomicBoolean` | 主应用完全后台 已完成 |
| `f680x` | `AtomicBoolean` | 备份应用完全后台 已完成 |

## 3. s0() — ListenWindow 列表 (7 个窗口规则)

| # | 方法 | packageName | className | 说明 |
|---|------|-------------|-----------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | 电池优化对话框 (共享) |
| 1 | `q0()` | com.android.settings | ...HWSettings | 华为系统设置主页 |
| 2 | `f0()` | com.android.settings | ...AppAndNotificationDashboardActivity | 应用和通知页 |
| 3 | `p0()` | com.huawei.systemmanager | ...StartupAppControlActivity | 华为启动管理 |
| 4 | `n0()` | com.hihonor.systemmanager | ...StartupAppControlActivity | 荣耀启动管理 |
| 5 | `o0()` | com.huawei.systemmanager | android.app.AlertDialog | 华为手动管理对话框 |
| 6 | `m0()` | com.hihonor.systemmanager | android.app.AlertDialog | 荣耀手动管理对话框 |

## 4. CombineFilter 配置 Key

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `b0()` | HUA_WEI_ALLOW_AUTO_STARTUP_TEXT | 允许自启动 |
| `c0()` | HUA_WEI_ALLOW_IN_BACKGROUND_TEXT | 允许后台运行 |
| `d0()` | HUA_WEI_ALLOW_RELATE_STARTUP_TEXT | 允许关联启动 |
| `e0()` | HUA_WEI_APP_AND_NOTIFICATION_TEXT | 应用和通知 (prefix 匹配) |
| `g0()` | HUA_WEI_APP_STARTUP_MANAGE_TEXT | 应用启动管理 |
| `l0()` | HUA_WEI_CONFIRM_TEXT | 确认按钮 |

## 5. 状态机 — u() 事件处理 (4 状态)

```
事件到达 → u()
  ├─ j0() → keepAliveInHwSettings → m(this, 0)
  │   └─ 华为设置主页: 查找"应用和通知"入口 → 点击
  ├─ i0() → keepAliveInAppAndNotification → m(this, 1)
  │   └─ 应用和通知页: 查找"应用启动管理"入口 → 点击
  ├─ k0() → keepAlvieInStartupAppControl → m(this, 2)
  │   └─ 启动管理页: r0() 滚动查找应用 → 操作 Switch
  └─ h0() → keepAliveInAlertDialog → m(this, 3)
      └─ 手动管理对话框: 操作允许自启动/后台/关联启动 CheckBox
```

## 6. r0() — 启动管理核心操作

```
1. k0() 确认在启动管理页
2. 判断当前目标 (MAIN_APP / BACKUP_APP)
3. G() 激活根节点
4. Q() 获取滚动视图
5. scrollForwardUtil(H(包名)) 向下滚动查找应用
6. 找到后 findParentUtilCombine(L()) 获取 clickable 行
7. findOneByCombine(a0()) 查找 Switch 控件
8. 如果 Switch.checked() = true (自动管理):
   → click() 取消自动管理 → 弹出手动管理对话框
9. 如果 Switch.checked() = false (已手动管理):
   → 标记完成 → 处理下一个应用或结束
```

### 关键逻辑: Switch checked 含义

```
华为启动管理 UI:
  [应用名] [Switch]

  Switch checked=true  → "自动管理" (需要关闭!)
  Switch checked=false → "手动管理" (已完成)

  点击 Switch (true→false) → 弹出对话框:
    ☐ 允许自启动
    ☐ 允许后台活动
    ☐ 允许关联启动
    [取消] [确认]
```

## 7. 手动管理对话框处理 (h0 状态)

对话框中有 3 个 CheckBox:
- 允许自启动 → b0() 匹配
- 允许后台运行 → c0() 匹配
- 允许关联启动 → d0() 匹配

操作: 确保所有 CheckBox 都勾选 → 点击"确认"按钮 (l0())

## 8. 双应用保活流程

```
r0() 中:
  KEEP_ALIVE_UNKNOWN → 设为 MAIN_APP → 处理主应用
  MAIN_APP 完成后:
    如果备份应用存在 → 设为 BACKUP_APP → 继续处理
    如果不存在 → t0() 保存 → Z() 结束
  BACKUP_APP 完成后 → t0() 保存 → Z() 结束
```

## 9. 与 o/v.java (HuaweiEngine) 的对比

| 维度 | o/v.java | o/n.java |
|------|----------|----------|
| 功能 | 电池/耗电管理 | 启动管理 |
| 主包名 | com.android.settings | com.android.settings |
| 目标包名 | com.oplus.battery, com.coloros.oppoguardelf | com.huawei.systemmanager, com.hihonor.systemmanager |
| ListenWindow 数 | 12 | 7 |
| 状态数 | 4 | 4 |
| Switch 操作 | R() 坐标点击 | a0() + click() |
| 超时 | 100 秒 | 50 秒 |
| 对话框 | "允许"按钮 | 3 个 CheckBox + "确认" |
| 滚动 | 无 | scrollForward/BackwardUtil |

## 10. 复刻注意事项

1. **Replica 映射名需修正**: `OppoEngine` 应改为 `HuaweiStartupEngine` 或合并到 `HuaweiEngine`
2. **Switch checked=true 表示自动管理**: 需要点击关闭，与直觉相反
3. **华为 + 荣耀双覆盖**: 同时支持 `com.huawei.systemmanager` 和 `com.hihonor.systemmanager`
4. **手动管理对话框**: 需要操作 3 个 CheckBox 后点击确认
5. **50 秒超时**: 比其他引擎短，因为启动管理操作相对简单
