# APK 华为静默自动化实现

## 概述

在华为/荣耀设备上，用户授权无障碍服务后，静默自动执行华为"启动管理 → 手动管理 → 全部允许"功能，让应用获得后台常驻能力。

## 完整流程

```
用户授权无障碍服务
  → onServiceConnected() 触发
  → 检测华为设备 (ev.a() == 1)
  → 延迟 1.5 秒（等待系统稳定）
  → 设置 m.b=true, m.o=false
  → 打开手机管家 MainScreenActivity
  → 等待 1 秒 → 点击"应用启动管理"
  → 系统事件触发 D0() → J0(2) 自动化点击
  → 自动化完成
```

用户在整个过程中看到的是 GuideActivity 引导页（带进度条），完全自然。

## 修改文件

| 文件 | 改动 |
|------|------|
| `AccessServices.smali` | onServiceConnected 添加华为检测+线程启动；新增 `startHuaweiAutomationSimple()` 方法；onAccessibilityEvent 添加华为/荣耀 AlertDialog 自动确认；添加 `hwBlockView`/`hwBlockWm` 字段 |
| `AccessServices$HuaweiAutomationRunnable.smali` | **新建** — 1.5秒延迟后执行自动化，30秒超时保护 |
| `AccessServices$HuaweiBlockViewRunnable.smali` | **新建** — 遮罩创建（保留但当前未使用，GuideActivity 作为自然遮罩） |
| `AccessServices$HuaweiBlockViewRemoveRunnable.smali` | **新建** — 遮罩移除（保留但当前未使用） |
| `m.smali` | m.b 改为 public；D0() 门控 sleep 优化 800ms→500ms；B1/Z2 设置页面检测修复；G1()/x1() 通知自动化已禁用 |
| `AndroidManifest.xml` | 添加华为权限声明 |

## 关键技术点

### 触发机制
- `AccessServices.onServiceConnected()` 中检测华为设备
- 启动 `HuaweiAutomationRunnable` 线程执行自动化

### 打开启动管理
- 直接打开华为手机管家 `MainScreenActivity`（不需要系统签名权限）
- 通过无障碍查找并点击"应用启动管理"文本
- 华为失败后自动 fallback 到荣耀 `com.hihonor.systemmanager`

### 事件驱动自动化
- 设置 `m.b=true` 后，系统 `TYPE_WINDOW_STATE_CHANGED` 事件触发 `D0() → J0(2)` 链路
- `J0()` 遍历启动管理列表，匹配关键词并点击开关

### AlertDialog 自动确认
- `onAccessibilityEvent` 中检测华为/荣耀 `AlertDialog`
- 自动查找并点击 `android:id/button1`（确认按钮）

### 通知自动化已禁用
- `G1()` 和 `x1()` 方法已禁用（直接 return）
- 不再跳转系统通知设置页面

### 超时保护
- 30 秒超时（150 次 × 200ms 轮询）
- 超时后重置标志位

## 时间线

| 阶段 | 耗时 |
|------|------|
| 初始延迟 | 1.5s |
| 设置标志+打开手机管家 | 0.5s |
| 等待 UI 加载 | 1s |
| 点击启动管理 | 即时 |
| D0 门控 + J0 自动化 | ~2.5s |
| **总计** | **~5.5s** |
