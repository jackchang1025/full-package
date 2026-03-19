# 控制面板屏幕操作实现说明

本文档说明「设备控制页」如何实现**在投屏画面上点击、滑动、长按**等屏幕操作，从前端采集到 WebSocket 消息、再到后端转发设备的完整链路。

---

## 1. 总体结论

- **真正“操作屏幕”的组件**：`ScreenViewer.vue`（显示投屏画面并采集点击/滑动/长按，换算为设备坐标后通过事件上报）。
- **ControlPanel.vue**：仅提供右侧「快捷操作」按钮（导航、音量、锁屏、粘贴、钓鱼等），不参与“在画面上点一下”的坐标与发送；屏幕触摸由 ScreenViewer 负责。
- **后端分工**：
  - **点击/滑动/长按（mov）**：由 **PanelHandler** 处理（`itype: slr_panel`，`subc: screen`，`comand: mov`）。
  - **投屏开关、截图、OCR（screentype）**：由 **PanelSendHandler** 处理（`itype: slr_panelsend`，`subc: screen`）。

---

## 2. 前端：谁在“操作屏幕”

| 文件 | 职责 |
|------|------|
| **ScreenViewer.vue** | 显示设备画面；把用户在画面上的点击/滑动/长按转换为**设备坐标**并 emit `tap` / `swipe` / `longpress`。 |
| **ControlPanel.vue** | 右侧快捷操作面板（导航、音量、锁屏、粘贴、钓鱼等按钮），不处理“在画面上点一下”的坐标。 |
| **Control.vue** | 设备控制页：挂载 ScreenViewer 与 ControlPanel，将 ScreenViewer 的 `tap` / `swipe` / `longpress` 接到 `useScreenControl.sendTap` / `sendSwipe` / `sendLongPress`，并负责 WebSocket `send`。 |

因此：「操作屏幕」= 用户在投屏画面上点/滑/长按 → ScreenViewer 采集并换算坐标 → Control.vue 调用 useScreenControl → 以 `comand: 'mov'` 通过 WebSocket 发到后端。

---

## 3. ScreenViewer.vue：坐标与事件

### 3.1 显示与坐标基准

- 使用 `screenData`（base64 图）、`screenWidth` / `screenHeight` 在容器内以 **object-fit: contain** 显示设备屏。
- 坐标换算在 **getScaledCoordinates(clientX, clientY)** 中完成：按 object-fit: contain 的**实际绘制区域**（内容矩形）将「视图坐标」换算为「设备坐标」，并钳在 `[0, screenWidth]` × `[0, screenHeight]`，避免留白导致点击错位。

### 3.2 事件与阈值

- **pointerdown**：记录起点、启动长按计时（500ms）。
- **pointerup**：
  - 若移动距离 > 30px → 视为滑动，`emit('swipe', startX, startY, endX, endY)`（均为设备坐标）；
  - 否则 → `emit('tap', x, y)`。
- 若在 500ms 内未 pointerup 则触发长按 → `emit('longpress', x, y)`。
- 仅在 **isStreaming** 为真时处理 pointer 事件（投屏开启时才响应操作）。

### 3.3 关键常量

- `LONG_PRESS_DURATION = 500`（ms）
- `SWIPE_THRESHOLD = 30`（像素，设备坐标）

---

## 4. Control.vue：事件与发送的衔接

ScreenViewer 的三种事件直接对应 useScreenControl 的三种发送方法：

```ts
// 模板中
<ScreenViewer
    ...
    @tap="handleTap"
    @swipe="handleSwipe"
    @longpress="handleLongPress"
/>

// 处理函数
const handleTap = (x: number, y: number) => screenControl.sendTap(x, y);
const handleSwipe = (startX, startY, endX, endY) => screenControl.sendSwipe(startX, startY, endX, endY);
const handleLongPress = (x: number, y: number) => screenControl.sendLongPress(x, y);
```

所有「在画面上点/滑/长按」的交互，最终都通过上述三个方法发出 WebSocket 消息。

---

## 5. useScreenControl.ts：发往服务器的消息格式

`sendTap` / `sendSwipe` / `sendLongPress` 均通过 **sendScreenCommand** 发送，统一为：

- **itype**: `'slr_panel'`
- **subc**: `'screen'`
- **comand**: `'mov'`
- **movetype**: `'0'` = 点击，`'1'` = 滑动，`'2'` = 长按
- **poi**:
  - 点击/长按：`{ x, y }`（设备坐标，已取整）
  - 滑动：字符串 `"(x1,y1):(x2,y2)"`（起点与终点设备坐标）

| 操作   | movetype | poi 格式 |
|--------|----------|----------|
| 点击   | `'0'`    | `{ x, y }` |
| 滑动   | `'1'`    | `"(startX,startY):(endX,endY)"` |
| 长按   | `'2'`    | `{ x, y }` |

因此，「操作屏幕」在协议上就是：**itype=slr_panel + subc=screen + comand=mov + movetype + poi**。

---

## 6. 后端：谁处理“操作屏幕”（mov）

### 6.1 消息路由（MessageRouter）

- **itype === 'slr_panel'** → **PanelHandler**
- **itype === 'slr_panelsend'** → **PanelSendHandler**

屏幕触摸（tap/swipe/longpress）发送的是 `itype: 'slr_panel'`，因此由 **PanelHandler** 处理。

### 6.2 PanelHandler 与 handleScreenCommand

- **PanelHandler** 根据 `subc` 分发：`subc === 'screen'` 时调用 **handleScreenCommand($phoneId, $data)**。
- 在 handleScreenCommand 中，根据 `comand` 构造发往设备的 payload；当 **comand === 'mov'** 时：

```php
'mov' => [
    'type' => 'screen',
    'subc' => 'mov',
    'poi' => $data['poi'] ?? '',
    'movetype' => $data['movetype'] ?? '',
],
```

即：所有「操作屏幕」（点击/滑动/长按）都由 **PanelHandler → handleScreenCommand → comand 'mov'** 转发到设备。**PanelSendHandler 不处理 mov**。

### 6.3 PanelSendHandler 与“屏幕”相关逻辑

**PanelSendHandler** 处理的是 **itype: slr_panelsend**，其中与屏幕相关的是 **投屏/截图/OCR 的开关**（如 SN/SNOFF、SM/SMOFF、SK/SKOFF），对应「开启/关闭投屏」「开启/关闭截图」「开启/关闭 OCR」等，而不是在画面上的触摸操作。例如：

```php
// PanelSendHandler::handleScreen
$this->connectionManager->sendToDevice($phoneId, [
    'type' => 'screencomd',
    'subc' => 'Screen',
    'comdtype' => $screentype,  // SM / SN / SK / SMOFF / SNOFF / SKOFF 等
]);
```

小结：

- **在画面上点/滑/长按（操作屏幕）** → **PanelHandler**（slr_panel + subc=screen + comand=mov）。
- **投屏/截图/OCR 的开关** → **PanelSendHandler**（slr_panelsend + subc=screen + screentype）。

---

## 7. 端到端流程小结

| 步骤 | 位置 | 说明 |
|------|------|------|
| 1 | ScreenViewer.vue | 用户在投屏图上点/滑/长按 → getScaledCoordinates 转设备坐标 → emit tap / swipe / longpress |
| 2 | Control.vue | @tap / @swipe / @longpress 绑定 handleTap / handleSwipe / handleLongPress → 调用 screenControl.sendTap / sendSwipe / sendLongPress |
| 3 | useScreenControl.ts | 组装 itype: slr_panel, subc: screen, comand: mov, movetype, poi，经 WebSocket send() 发出 |
| 4 | MessageRouter | itype === slr_panel → PanelHandler |
| 5 | PanelHandler | subc === screen → handleScreenCommand → comand === mov 时构造 type: screen, subc: mov, poi, movetype 发往设备 |

**ControlPanel.vue** 的按钮（导航、音量、锁屏等）也通过同一 WebSocket 的 send / screenControl 发到后端，但不经过 ScreenViewer 的 tap/swipe/longpress，而是各自对应的 comand（如 nav、vol、L 等），同样在 **PanelHandler** 的 handleScreenCommand 中按 comand 分支转发到设备。

---

## 8. 鼠标操作约定（ScreenViewer）

### 8.1 交互规则

| 输入方式 | 操作 | 效果 |
|----------|------|------|
| 鼠标左键 | 单击（移动 <30px 设备坐标） | **点击 (tap)**：在按下位置触发设备点击 |
| 鼠标左键 | 按住 500ms 不移动 | **长按 (longpress)**：在按下位置触发设备长按 |
| 鼠标左键 | 按住并移动 ≥30px，松开 | **滑动 (swipe)**：按下位置为起点，松开位置为终点 |
| 鼠标右键 | 按住并移动鼠标，松开 | **滑动 (swipe)**：按下位置为起点，松开位置为终点（右键专用滑动，阈值仅 10px） |
| 鼠标右键 | 按住不移动（或移动 <10px 设备坐标）| 不触发任何操作 |
| 触摸屏 | 点击 / 滑动 / 长按 | 与鼠标左键逻辑一致：移动 ≥30px 为滑动，否则为点击；500ms 为长按 |

### 8.2 左键与右键滑动的区别

| 对比项 | 左键滑动 | 右键滑动 |
|--------|----------|----------|
| 滑动阈值 | 30px（与 tap 共存，需较大移动量才切换为滑动） | 10px（专用滑动，低阈值避免误触即可） |
| 附带功能 | 同时支持 tap（<30px）和 longpress（500ms） | 仅滑动，无 tap/longpress |
| 适用场景 | 常规操作，兼顾点击与滑动 | 需要精准滑动时（如小距离微调、在拥挤 UI 中滑动） |
| 手感 | 与手机触摸屏一致 | 更类似"拖拽画布"，只要按住右键移动就是滑动 |

### 8.3 左键判定逻辑

左键按下后进入三个互斥分支：

1. **移动 ≥30px**：取消长按定时器，进入滑动模式；松开时以起点→当前位置发送 swipe。
2. **500ms 内未移动 ≥30px 且未松开**：触发长按 (longpress)，结束交互。
3. **500ms 内松开且移动 <30px**：视为点击 (tap)。

### 8.4 技术细节

- **右键菜单**：投屏 img 元素上使用 `@contextmenu.prevent` 禁止浏览器右键菜单弹出。
- **setPointerCapture**：左键和右键按下时均对当前指针调用 `setPointerCapture(event.pointerId)`，确保鼠标移出投屏区域后仍能收到 `pointermove` / `pointerup`，拖到画面外再松开也能正确发送滑动。
- **lostpointercapture**：兜底事件，若指针捕获意外丢失（如焦点切换），按当前终点发送一次 swipe（若移动距离有效），然后重置状态。
- **触摸设备**：通过 `event.pointerType === 'touch'` 判定，走与左键相同的逻辑（按距离判定 tap/swipe + 长按）。
- **视觉反馈**：左键或右键拖拽进入滑动模式后，光标变为十字 (crosshair)，提示用户处于滑动模式。

---

## 9. 相关文件索引

| 层级 | 文件 | 说明 |
|------|------|------|
| 前端组件 | `resources/ts/Components/DeviceControl/ScreenViewer.vue` | 投屏显示、坐标换算、tap/swipe/longpress 事件 |
| 前端组件 | `resources/ts/Components/DeviceControl/ControlPanel.vue` | 快捷操作按钮（导航、音量、锁屏等） |
| 前端页面 | `resources/ts/Pages/Devices/Control.vue` | 设备控制页，挂载 ScreenViewer/ControlPanel 并绑定事件与 send |
| 前端逻辑 | `resources/ts/composables/useScreenControl.ts` | sendTap / sendSwipe / sendLongPress 及 sendScreenCommand 格式 |
| 前端类型 | `resources/ts/types/websocket.ts` | ScreenControlMessage 等类型定义 |
| 后端路由 | `app/WebSocket/MessageRouter.php` | itype → PanelHandler / PanelSendHandler |
| 后端处理 | `app/WebSocket/Handlers/PanelHandler.php` | handleScreenCommand，comand 'mov' 转发到设备 |
| 后端处理 | `app/WebSocket/Handlers/PanelSendHandler.php` | 投屏/截图/OCR 开关（screentype），不处理 mov |

---

## 10. 扩展阅读

- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - WebSocket 系统架构与消息协议
- [WEBSOCKET_SERVER_PHP.md](./WEBSOCKET_SERVER_PHP.md) - PHP WebSocket 服务与 Handler
- [FRONTEND.md](./FRONTEND.md) - 前端架构与组件说明
