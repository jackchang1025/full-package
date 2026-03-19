# MODULE_03 厂商适配引擎 — Vendor 行为审计

## 1. 模块职责

厂商适配引擎。在无障碍服务启动后，根据设备品牌自动注册对应的保活引擎，通过 UI 自动化操作系统设置页面，实现自启动/后台运行/电池优化等权限的自动开启。

## 2. Vendor 架构

```
o/e.java (982行) — 引擎基类 (AccessibilityDelegate)
  ├── o/c.java (801行) — 厂商保活引擎基类 (extends e, 带 ScheduledExecutor + ReentrantLock)
  │     ├── o/n.java (454行) — 华为/荣耀引擎
  │     ├── o/q.java (498行) — 小米/红米引擎
  │     ├── o/v.java (526行) — OPPO/realme/一加引擎
  │     ├── o/i0.java (684行) — 屏幕解锁引擎
  │     ├── o/e0.java (373行) — 传音引擎
  │     └── o/g.java (316行) — 通用 AOSP 引擎
  ├── o/a0.java (2003行) — 安装代理引擎
  ├── o/t.java (677行) — 开发者选项引擎
  ├── o/x.java (531行) — 无障碍服务引擎
  ├── o/k.java (382行) — 权限自动授予引擎
  ├── o/g0.java (432行) — AOSP 保活引擎
  ├── o/h.java (196行) — 锁屏监听引擎
  ├── o/i.java (266行) — 设备管理员引擎
  ├── o/l.java (71行) — 权限控制器引擎
  ├── o/o.java (55行) — 系统 UI 引擎
  └── o/h0.java (307行) — 异步事件处理

辅助类:
  o/a.java (346行) — 异步任务 Runnable (引擎启动)
  o/b.java (177行) — 抽象辅助 (CombineFilter 构建工具)
  o/d.java (292行) — 异步任务 Runnable (引擎执行)
  o/b0.java (136行) — 异步事件 Runnable
  o/d0.java (261行) — 异步任务 Runnable
  o/r.java (69行) — 截图执行器 (MiniCap)
  o/c0.java (30行) — 事件类型常量
  o/j0.java (51行) — 序列化数据类
  o/m.java (32行) — 定时任务 Runnable
  o/f.java (31行) — 辅助 Runnable
  o/p.java (80行) — 异步任务 Runnable
  o/s.java (107行) — 三星引擎 Runnable
  o/u.java (169行) — vivo 引擎 Runnable
  o/w.java (33行) — 辅助 Runnable
  o/z.java (42行) — 辅助 Runnable
```

## 3. 文件映射对比

### 引擎类

| Vendor | 行数 | Replica | 行数 | 方法数差 | 状态 |
|--------|------|---------|------|---------|------|
| o/e.java (基类) | 982 | AutoEngine.java | 510 | 36→20 | ⚠️ 缺 16 方法 |
| o/c.java (厂商基类) | 801 | AutoEngine.java (合并) | — | 23→0 | ⚠️ 合并丢失 |
| o/n.java (华为) | 454 | vendor/HuaweiEngine.java | 551 | 22→11 | ⚠️ 缺 11 方法 |
| o/q.java (小米) | 498 | vendor/XiaomiEngine.java | 535 | 21→9 | ⚠️ 缺 12 方法 |
| o/v.java (OPPO) | 526 | vendor/OppoEngine.java | 735 | 32→11 | ⚠️ 缺 21 方法 |
| o/i0.java (屏幕解锁) | 684 | ScreenUnlockDelegate.java | 267 | — | ⚠️ 行数差大 |
| o/e0.java (传音) | 373 | TranssionKeepAliveEngine.java | 328 | — | ✅ 接近 |
| o/g.java (通用) | 316 | 无对应 | — | — | ❌ 缺失 |
| o/a0.java (安装代理) | 2003 | PackageInstallerDelegate.java | 596 | — | ⚠️ 行数差大 |
| o/t.java (开发者选项) | 677 | OpenDevelopmentDelegate.java | 566 | — | ✅ 接近 |
| o/x.java (无障碍) | 531 | AccessibilityServiceEngine.java | 366 | — | ⚠️ 行数差 |
| o/k.java (权限授予) | 382 | PermissionAutoGrantEngine.java | 218 | — | ⚠️ 行数差 |
| o/g0.java (AOSP保活) | 432 | AospKeepAliveEngine.java | 302 | — | ⚠️ 行数差 |
| o/h.java (锁屏) | 196 | LockScreenMonitor.java | 363 | — | ✅ replica 更多 |
| o/i.java (设备管理) | 266 | DeviceAdminEngine.java | 287 | — | ✅ 接近 |
| o/l.java (权限控制器) | 71 | PermissionGrantDelegate.java | 151 | — | ✅ replica 更多 |
| o/o.java (系统UI) | 55 | 无对应 | — | — | ❌ 缺失 |

### 辅助类

| Vendor | 行数 | Replica | 状态 |
|--------|------|---------|------|
| o/a.java (异步启动) | 346 | 无对应 | ❌ 内联到引擎 |
| o/b.java (Filter构建) | 177 | 无对应 | ❌ 内联到引擎 |
| o/d.java (异步执行) | 292 | 无对应 | ❌ 内联到引擎 |
| o/r.java (截图) | 69 | MiniCapture.java (83行) | ✅ |
| o/c0.java (事件常量) | 30 | AccessibilityEventRecord.java (77行) | ✅ |
| o/s.java (三星) | 107 | vendor/SamsungEngine.java (328行) | ✅ 合并 |
| o/u.java (vivo) | 169 | vendor/VivoEngine.java (348行) | ✅ 合并 |
| 其余 8 个 Runnable | ~600 | 无对应 | ⚠️ 内联到引擎 |

## 4. 华为引擎 (o/n.java) 行为分析

### 入口
```
构造函数 → super(s0(), "com.android.settings")
  s0() 返回 7 个 ListenWindow:
    - com.android.settings / HWSettings
    - com.android.settings / AppAndNotificationDashboardActivity
    - com.huawei.systemmanager / StartupAppControlActivity
    - com.hihonor.systemmanager / StartupAppControlActivity
    - com.huawei.systemmanager / AlertDialog
    - com.hihonor.systemmanager / AlertDialog
    - com.android.settings / Dialog
  定时任务: 50 秒超时
```

### 执行流程
```
1. j0() — 进入华为系统设置 (HWSettings)
2. i0() — 进入应用和服务 (AppAndNotificationDashboardActivity)
3. k0() — 进入应用启动管理 (StartupAppControlActivity)
4. r0() — 在启动管理中:
   a. 滚动查找目标应用
   b. 点击进入应用详情
   c. h0() — 进入手动管理对话框
   d. 勾选: 允许自启动 / 允许后台活动 / 允许关联启动
   e. 点击确认
5. Z() — 完成: 返回桌面 + 清理
```

### CombineFilter 构建
```
b0() — 查找 "允许自启动" 文本 (HUA_WEI_ALLOW_AUTO_STARTUP_TEXT)
c0() — 查找 "允许后台活动" 文本 (HUA_WEI_ALLOW_IN_BACKGROUND_TEXT)
d0() — 查找 "允许关联启动" 文本 (HUA_WEI_ALLOW_RELATE_STARTUP_TEXT)
e0() — 查找 "应用和通知" 文本 (HUA_WEI_APP_AND_NOTIFICATION_TEXT)
g0() — 查找 "应用启动管理" 文本 (HUA_WEI_APP_STARTUP_MANAGE_TEXT)
l0() — 查找 "确认" 按钮 (HUA_WEI_CONFIRM_TEXT)
```

## 5. 关键差距分析

### 5.1 引擎基类 (e.java + c.java → AutoEngine.java)

Vendor e.java 有 36 个方法，c.java 有 23 个方法。Replica AutoEngine 只有 20 个方法。

缺失的关键方法:
- `A()` — 执行 replyActions (操作分发)
- `s()` — 查找匹配节点 (selector + sourceRule)
- `u(event, pkg, cls)` — 事件处理入口
- `v(root, isComplete, pkg, cls, title)` — 根节点变化处理
- `w(boolean)` — 设置活跃状态
- `o()` — 是否活跃
- `l()` — 获取事件类型列表
- `c(pkg, cls)` — 窗口匹配
- `q(List<ListenWindow>)` — 等待窗口出现 (c.java)
- `G()` — 激活根节点 (c.java)
- `Q()` — 获取可滚动视图 (c.java)
- `T()` — 检查是否完成 (c.java)
- `X()` — 返回桌面 (c.java)
- `t0()` — 上报保活状态 (c.java)

### 5.2 华为引擎方法差距

| Vendor 方法 | 功能 | Replica | 状态 |
|------------|------|---------|------|
| s0() | 7 个 ListenWindow | 部分 | ⚠️ |
| b0/c0/d0/e0/g0/l0 | CombineFilter 构建 | 部分 | ⚠️ |
| j0/i0/k0 | 导航到目标页面 | 部分 | ⚠️ |
| r0 | 核心保活逻辑 | 简化 | ⚠️ |
| h0 | 手动管理对话框 | 简化 | ⚠️ |
| Z() | 完成清理 | 简化 | ⚠️ |

### 5.3 辅助类 o/b.java — CombineFilter 构建工具

Vendor 的 o/b.java 提供了构建 CombineFilter 的工具方法:
```java
b.b(filter, classCondition, property) — 添加 StringCondition
b.v(textConfigKey, condition, filter, condition) — 从 TextConfig 获取文本
b.q(eventType, list, window) — 添加事件类型
b.r(window) — 获取事件类型列表
a.a.c(filter, property, value) — 创建 className 条件
a.a.b(filter, classCondition, property, value) — 创建 id 条件
```

Replica 没有这些工具方法，CombineFilter 构建逻辑分散在各引擎中。

## 6. 真机验证要点

### 华为设备验证
1. 启动应用 → 无障碍启用 → 华为引擎注册
2. 引擎自动打开设置 → 应用启动管理
3. 滚动查找目标应用 → 点击进入
4. 勾选自启动/后台/关联启动
5. 点击确认 → 返回桌面

### 验证命令
```bash
# 检查引擎注册
adb logcat -s "EngineManager" | grep "HuaweiEngine"

# 检查引擎执行
adb logcat -s "HuaweiEngine" "o.n"

# 检查窗口匹配
adb logcat | grep "Window matched\|窗口匹配"
```

## 7. 优先修复项

### P0 (引擎基本运行)
1. AutoEngine 补齐 c.java 的 q()/G()/Q()/T()/X()/t0() — 厂商引擎依赖这些方法
2. 华为引擎补齐 ListenWindow 列表 (s0) — 7 个窗口匹配
3. 华为引擎补齐 CombineFilter 构建方法 (b0~l0) — 节点查找

### P1 (完整功能)
4. 小米/OPPO 引擎补齐方法 (缺 12/21 个)
5. 创建 o/b.java 对应的 CombineFilter 构建工具类
6. 创建 o/g.java 对应的通用 AOSP 引擎

### P2 (边缘功能)
7. 安装代理引擎 (a0.java 2003行 → 596行) 补齐
8. 屏幕解锁引擎 (i0.java 684行 → 267行) 补齐
9. 系统 UI 引擎 (o.java 55行) 创建
