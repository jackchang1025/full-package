# 文字辅助功能 — 无障碍节点树方案

> **日期**: 2026-04-01
> **状态**: 已实施

## 功能概述

文字辅助通过读取 Android 无障碍节点树（AccessibilityNodeInfo），将屏幕上所有 UI 控件的文字、坐标、类型发送给 Panel，Panel 渲染为可视化布局。操作者点击节点即可发送 tap 到对应坐标。

**核心优势**: 不依赖截图，PIN 输入界面可用（`getRootInActiveWindow()` 不受 `FLAG_SECURE` 限制）。

## 架构

```
Panel 点击"开启"
  → slr_panelsend { subc: 'screen', screentype: 'SK' }
  → PHP PanelSendHandler → 设备 { type: 'screencomd', subc: 'Screen', comdtype: 'SK' }
  → ScreenshotHandler.startNodeTree()
  → 每 800ms 执行 readAndSendNodeTree()
    → getWindows() 遍历所有窗口 → getRoot() → traverseNode() 递归
    → WebSocket { itype: 'Slr_client', subc: 'readScreen', children: [...] }
  → PHP DeviceHandler → Panel { type: 'readScreen', children: [...] }
  → TextAssistPanel 可视化渲染
```

## 数据格式

### 设备 → Panel 消息

```json
{
  "type": "readScreen",
  "pid": "设备ID",
  "windowTitle": "窗口标题",
  "activePackage": "com.example.app",
  "activeWindow": "android.widget.FrameLayout",
  "children": [
    {
      "depth": 3,
      "index": 0,
      "text": "按钮文字",
      "desc": "content-description",
      "cls": "android.widget.Button",
      "id": "com.example:id/btn",
      "hint": "提示文字",
      "x": 540, "y": 960,
      "l": 100, "t": 900, "r": 980, "b": 1020,
      "click": true,
      "edit": false,
      "focus": false,
      "pwd": false,
      "scroll": false
    }
  ]
}
```

### 节点过滤规则

只发送有意义的节点（减少数据量）：
- 有文字内容（text / desc / hint）
- 或可交互（clickable / editable / focusable / checkable / scrollable）
- 或密码框（password）
- 且有实际尺寸（width > 0 && height > 0）

## 与投屏的关系

| 功能 | 命令 | 数据类型 | 独立运行 |
|------|------|---------|---------|
| 投屏 (SN) | `screentype: 'SN'` | 截图 base64 | ✅ |
| 截图 (SM) | `screentype: 'SM'` | 截图 base64 | ✅ |
| 文字辅助 (SK) | `screentype: 'SK'` | 节点树 JSON | ✅ |

三者使用独立的定时器和状态标志，可同时运行互不干扰。

## 已知限制

### 受保护的系统页面

部分厂商（OPPO/ColorOS 等）对以下系统应用启用了安全保护，`AccessibilityService.getRoot()` 返回 null：

- `com.android.permissioncontroller`（权限管理器）
- 部分厂商安全设置页面

**表现**: 文字辅助只能获取到通知栏节点，无法获取应用内容。

**原因**: 厂商安全策略阻止第三方无障碍服务读取这些窗口的节点树。`uiautomator dump`（shell 权限）可以读取，但无障碍服务不行。

**Vendor 同样受此限制**。

### 可能的绕过方案（未实施）

通过 ADB 自连接执行 `uiautomator dump`，需要设备已完成 ADB 配对。

## 相关文件

| 文件 | 说明 |
|------|------|
| `android/.../ScreenshotHandler.java` | SK 节点树读取、遍历、发送 |
| `app/WebSocket/Handlers/DeviceHandler.php` | readScreen 路由转发 |
| `app/resources/ts/Components/DeviceControl/TextAssistPanel.vue` | 可视化布局渲染 |
| `app/resources/ts/Pages/Devices/Control.vue` | readScreen 消息处理 |
