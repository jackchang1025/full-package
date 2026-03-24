# 修复点亮屏幕命令重复问题 - 设计文档

> **日期**: 2026-03-24
> **状态**: 已实施
> **优先级**: P2

## 问题描述

控制面板中的"点亮屏幕"按钮和"返回主页"按钮发送了相同的 WebSocket 命令，导致功能重复。

### 问题根源

`Control.vue` 中的 `handleWakeScreen()` 函数直接发送硬编码的导航命令：

```javascript
send({
    itype: 'slr_panel',
    subc: 'screen',
    pid: deviceId.value,
    comand: 'nav',      // 导航命令
    navshort: 'ho'      // 主页
});
```

而 `handleNavigate('home')` 通过 `screenControl.sendNavigation('home')` 也发送相同的命令。

## 需求

点亮屏幕应该只唤醒设备屏幕，不改变当前应用或页面。

## 解决方案

### 方案选择

采用**方案 1：使用解锁命令**

- 修改 `handleWakeScreen` 使用现有的解锁命令 `screenControl.lockDevice(0)`
- 解锁操作（lockit: '0'）会唤醒屏幕
- 只需修改前端一行代码，无需改动后端或 Android 客户端

### 技术实现

**修改文件**: `app/resources/ts/Pages/Devices/Control.vue`

**修改内容**:

```javascript
// 修改前
const handleWakeScreen = () => {
    send({
        itype: 'slr_panel',
        subc: 'screen',
        pid: deviceId.value,
        comand: 'nav',
        navshort: 'ho'
    });
    message.success('点亮屏幕请求已发送');
};

// 修改后
const handleWakeScreen = () => {
    screenControl.lockDevice(0);  // 0 = 解锁，会唤醒屏幕
    message.success('点亮屏幕请求已发送');
};
```

### 命令对比

| 操作 | 命令 | 参数 |
|------|------|------|
| 点亮屏幕（旧） | `comand: 'nav'` | `navshort: 'ho'` |
| 点亮屏幕（新） | `comand: 'L'` | `lockit: '0'` |
| 返回主页 | `comand: 'nav'` | `navshort: 'ho'` |

## 影响分析

### 优点

- 最小改动：仅修改 2 行代码
- 无需部署协调：不涉及后端或 Android 客户端
- 语义合理：解锁操作会唤醒屏幕
- 立即可测试：修改后即可验证

### 潜在问题

- 如果设备已解锁且屏幕已亮，可能无明显效果
- 解锁可能触发额外的系统行为（如显示锁屏通知）

### 测试场景

1. 设备锁屏且屏幕关闭 → 应唤醒并解锁
2. 设备锁屏但屏幕亮着 → 应解锁
3. 设备已解锁但屏幕关闭 → 行为待验证

## 备选方案

### 方案 2：添加专门的唤醒命令

需要修改前端、后端、Android 客户端三层，工作量大，未采用。

### 方案 3：重构为共享逻辑

不符合需求（点亮屏幕仍会导航到主页），未采用。

## 相关文件

- `app/resources/ts/Pages/Devices/Control.vue` - 控制页面
- `app/resources/ts/composables/useScreenControl.ts` - 屏幕控制逻辑
- `app/app/WebSocket/Handlers/PanelHandler.php` - 后端命令处理
