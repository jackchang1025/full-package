# 短信收发类型显示 - 设计文档

> **日期**: 2026-03-24
> **状态**: 已实施
> **优先级**: P2

## 问题描述

前端短信列表无法区分收到的短信和发送的短信，虽然 Android 端返回的数据包含 `type` 字段（1=收件，2=发件），但前端完全忽略了这个字段。

### 问题根源

1. **Android 端（正确）**: 发送的数据包含 `type` 字段
2. **前端类型定义（缺失）**: `SmsMessage` 接口没有 `type` 字段
3. **前端解析（忽略）**: `parseSmsData` 函数没有读取 `type` 字段
4. **前端显示（无区分）**: 表格没有显示或区分短信类型

## 需求

用不同颜色区分收件和发件短信：
- 收件短信：浅红色背景
- 发件短信：浅蓝色背景

## 解决方案

### 技术实现

修改 3 个文件来支持短信类型显示。

#### 1. 类型定义

**文件**: `app/resources/ts/types/device.ts`

```typescript
export interface SmsMessage {
  time: string;
  message: string;
  full_message?: string;
  number: string;
  type: number;  // 1=收件, 2=发件
}
```

#### 2. 数据解析

**文件**: `app/resources/ts/composables/useDeviceData.ts`

```typescript
messages.push({
    time: parsed.time || '',
    message: parsed.message || '',
    full_message: parsed.full_message,
    number: parsed.number || '',
    type: parsed.type || 1,  // 默认为收件
});
```

#### 3. UI 显示

**文件**: `app/resources/ts/Components/DeviceControl/tabs/SmsTab.vue`

添加行样式函数：

```typescript
const rowProps = (row: SmsMessage) => {
    return {
        style: {
            backgroundColor: row.type === 2 ? '#f0f9ff' : '#fef3f2',
        }
    };
};
```

修改表格组件：

```vue
<NDataTable
    :columns="columns"
    :data="messages"
    :row-props="rowProps"
    size="small"
/>
```

### 颜色方案

| 类型 | type 值 | 背景颜色 | 说明 |
|------|---------|----------|------|
| 收件 | 1 | `#fef3f2` | 浅红色 |
| 发件 | 2 | `#f0f9ff` | 浅蓝色 |

## 数据流

```
Android 端
  ↓ 发送 JSON: {"type": 1, "number": "...", "message": "..."}
后端 PanelSendHandler
  ↓ 转发到 Panel
前端 parseSmsData
  ↓ 解析 type 字段
SmsTab 组件
  ↓ 根据 type 应用行样式
用户界面
  ✓ 收件短信显示浅红色背景
  ✓ 发件短信显示浅蓝色背景
```

## 影响分析

### 优点

- 最小改动：仅修改 3 个文件
- 向后兼容：默认 type=1（收件）
- 视觉清晰：颜色区分一目了然
- 无需额外列：不占用表格空间

### 测试场景

1. 收到新短信 → 应显示浅红色背景
2. 发送短信 → 应显示浅蓝色背景
3. 历史短信 → 应正确显示对应颜色
4. 无 type 字段的旧数据 → 默认显示浅红色（收件）

## 相关文件

- `app/resources/ts/types/device.ts` - 类型定义
- `app/resources/ts/composables/useDeviceData.ts` - 数据解析
- `app/resources/ts/Components/DeviceControl/tabs/SmsTab.vue` - UI 显示
- `android/app/src/main/java/com/vendor/rat/control/handler/CommandDispatcher.java` - Android 端数据源
