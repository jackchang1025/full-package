# WebSocket 服务器技术文档

> 本文档详细分析 `legacy/src/api/ws/websocket-server.js` 的架构设计、消息协议和功能实现。

## 概述

这是一个 **设备远程管理系统** 的 WebSocket 服务器，基于 Node.js 实现，用于在 **管理面板（Web端）** 和 **被控设备（Android手机）** 之间建立实时双向通信。

### 技术栈

- **运行时**: Node.js
- **WebSocket**: ws 库
- **HTTP**: Express
- **加密**: crypto (AES-256-CBC)

### 端口配置

| 服务 | 端口 | 说明 |
|------|------|------|
| HTTP Server | 3000 (或 env.PORT) | Express HTTP 服务 |
| WebSocket Server | 8080 (固定) | WebSocket 主服务 |

---

## 系统架构

```
┌─────────────────┐         ┌──────────────────┐         ┌─────────────────┐
│   管理面板       │◄───────►│  WebSocket Server │◄───────►│   Android 设备   │
│   (Web 前端)     │         │    (Node.js)      │         │   (被控端)       │
│   itype=slr_panel│         │    Port: 8080     │         │ itype=Slr_client │
└─────────────────┘         └──────────────────┘         └─────────────────┘
        │                           │                           │
        │    1. join (订阅设备)      │                           │
        │─────────────────────────►│                           │
        │                           │                           │
        │    2. 发送命令             │    3. 转发命令到设备       │
        │─────────────────────────►│─────────────────────────►│
        │                           │                           │
        │    5. 转发响应到面板       │    4. 设备响应             │
        │◄─────────────────────────│◄─────────────────────────│
        │                           │                           │
```

---

## 核心数据结构

### 连接管理 Map

| Map 名称 | 键类型 | 值类型 | 用途 |
|---------|--------|--------|------|
| `SolrUsers` | phoneId (string) | Set\<WebSocket\> | 存储订阅某设备的所有管理面板连接（一对多） |
| `SolrMobs` | phoneId (string) | WebSocket | 存储设备端连接（一对一） |
| `wsToPhoneId` | WebSocket | phoneId (string) | 反向映射，用于连接断开时快速查找 |
| `DeviceStatus` | phoneId (string) | Object | 设备状态信息缓存（心跳、设备信息等） |
| `probes` | phoneId (string) | {time, count} | 心跳探测记录 |

### 连接类型标识

| 常量名 | 值 | 来源 | 说明 |
|--------|-----|------|------|
| `idf_admin` | `"slr_panel"` | 管理面板 | 发送控制命令 |
| `idf_adminsend` | `"slr_panelsend"` | 管理面板 | 发送数据操作命令 |
| `idf_client` | `"Slr_client"` | 设备端 | 设备响应/数据上报 |

### WebSocket 客户端标记

```javascript
ws.clientType = "phone"  // 手机端连接
ws.clientType = "web"    // 网页端连接
```

---

## 消息协议

### 基础消息格式

所有消息均为 JSON 格式：

```javascript
{
  pid: "设备唯一标识",      // 必需 - phoneId
  itype: "消息来源类型",    // slr_panel / slr_panelsend / Slr_client
  subc: "子命令",          // 具体操作类型
  msg: "消息内容",         // 可选 - 具体数据
  // ...其他命令特定字段
}
```

### 消息流向

```
管理面板 ──(itype=slr_panel/slr_panelsend)──► 服务器 ──(type=xxx)──► 设备
设备 ──(itype=Slr_client)──► 服务器 ──(type=xxx)──► 管理面板
```

---

## 功能模块详解

### 1. 连接管理

#### 设备端连接 (`itype = "Slr_client"`)

```javascript
// 设备首次连接
if (data.itype === "Slr_client") {
  ws.clientType = "phone";
  SolrMobs.set(phoneId, ws);
  DeviceStatus.set(phoneId, { lastPing: Date.now() });
}
```

#### 管理面板连接 (`itype = "slr_panel"`)

| 子命令 | 功能 | 说明 |
|--------|------|------|
| `join` | 订阅设备 | 将面板 WebSocket 加入 SolrUsers |
| `out` | 退出命令 | 向设备发送退出指令 |
| `ping` | 状态查询 | 返回设备连接状态和信息 |
| `disag` | 断开清理 | 清理所有相关连接和状态 |

### 2. 心跳机制

```javascript
const PING_TIMEOUT = 75 * 1000;   // 心跳超时时间 75秒
const CHECK_INTERVAL = 25 * 1000; // 检查间隔 25秒
```

**心跳流程：**

1. 设备定期发送 `ping` 消息，更新 `DeviceStatus.lastPing`
2. 服务器每 25 秒检查所有设备连接
3. 超过 75 秒未收到心跳 → 发送探测包
4. 每 10 秒重复探测，直到恢复或连接关闭
5. 连接已关闭 → 清理资源

**探测包格式：**
```javascript
{
  type: "connected",
  kdate: Date.now()
}
```

### 3. 设备状态推送

当设备发送 ping 时，服务器会主动推送更新给订阅的管理面板：

```javascript
{
  type: "deviceUpdate",
  pid: phoneId,
  phoneInfo: deviceData  // 包含 lastPing 和设备信息
}
```

---

## 命令详解

### 管理面板命令 (`itype = "slr_panel"`)

#### 屏幕控制 (`subc = "screen"`)

| comand | 功能 | 参数 |
|--------|------|------|
| `block` | 锁定屏幕 | bstate (0/1), color |
| `paste` | 粘贴文本 | txt |
| `mov` | 模拟触摸 | movetype, poi (坐标点) |
| `snap` | 截图 | stype (0=连续, 1=单次) |
| `vol` | 音量控制 | volstate (0=降, 1=升) |
| `kb` | 键盘控制 | kbstate (0=隐藏, 1=显示) |
| `L` | 锁屏 | lockit (0/1) |
| `nav` | 导航按键 | navshort (ho/rec/bak) |
| `q` | 画质调整 | newqulity |
| `phonepass` | 设置密码 | passtype, txt |
| `usdt` | USDT 相关 | usdttype |
| `blockd` | 阻止显示 | blocktext |

#### 浏览器控制 (`subc = "brows"`)

| btype | 模式 | 参数 |
|-------|------|------|
| `h` | 隐藏浏览器 | bcom (0=停止, 1=启动, 3=命令), extdata |
| `n` | 普通浏览器 | ltype (f=HTML, u=URL), extdata |

#### 其他命令

| subc | 功能 | 说明 |
|------|------|------|
| `proxy` | 代理控制 | prxcom (ON/OFF) |
| `fetch` | 文件获取 | ftype, fpath |
| `bc` | 广播通知 | comand (alert/notify), title, msg, act |
| `srch` | 文件搜索 | srchfor, srchin (G/S), targetpath |
| `cocu` | 复制/剪切 | state (co/cu), tp, fp |
| `chat` | 发送消息 | msg, title |

### 数据操作命令 (`itype = "slr_panelsend"`)

#### 屏幕/媒体

| subc | 功能 | 参数 |
|------|------|------|
| `screen` | 屏幕共享 | screentype |
| `cam` | 开启摄像头 | SelectedCam |
| `camoff` | 关闭摄像头 | - |
| `mic` | 开启麦克风 | - |
| `micoff` | 关闭麦克风 | - |
| `loc` | 开启定位 | - |
| `locoff` | 关闭定位 | - |

#### 数据获取

| subc | 功能 | 参数 |
|------|------|------|
| `SMS` | 获取短信 | - |
| `SMSSEND` | 发送短信 | smsnumber, message |
| `Contacts` | 获取联系人 | - |
| `files` | 文件列表 | filepath |
| `changefiles` | 文件操作 | comdtype (U/R/D), filepath, content |
| `viewfile` | 查看文件 | filepath |
| `Keylog` | 键盘记录 | keylogtype |
| `Logdate` | 按日期查记录 | keylogtype, keylogdate |

#### 应用管理

| subc | 功能 | 参数 |
|------|------|------|
| `LOADAPPS` | 获取应用列表 | - |
| `OPENAPP` | 打开应用 | packageName |
| `UNINSTALLAPP` | 卸载应用 | packageName |
| `Hideico` | 隐藏图标 | - |

#### 活动记录

| subc | 功能 | 子命令 |
|------|------|--------|
| `activz` | 活动记录 | L=列表, D=删除 |
| `notifys` | 通知记录 | L=列表, D=删除 |
| `vapps` | 访问应用 | L=列表, D=删除 |
| `vlinks` | 访问链接 | L=列表, D=删除 |

#### 其他

| subc | 功能 | 参数 |
|------|------|------|
| `Permissions` | 权限管理 | subc (R=请求), prim |
| `rename` | 重命名设备 | nam |
| `change` | 修改配置 | domain, ip, changeid |
| `delete` | 远程删除 | - |
| `DIAO` | 对话框 | pin, title, lckdis, typ |
| `OPENINJ` | 打开注入 | - |
| `noinj` | 取消注入 | jctid |
| `display` | 显示控制 | display |

### 设备响应 (`itype = "Slr_client"`)

设备上报数据，服务器转发给订阅的管理面板：

| subc | 数据类型 | 字段 |
|------|----------|------|
| `screen` | 屏幕图像 | img, wmob, hmob |
| `screenshot` | 截图 | img, wmob, hmob |
| `cam` | 摄像头图像 | img |
| `mic` | 麦克风音频 | voip |
| `loc` | 位置信息 | msg |
| `sms` | 短信内容 | msg |
| `chat` | 聊天消息 | msg |
| `files` | 文件列表 | msg |
| `savefiles` | 文件内容 | msg |
| `thumb` | 缩略图 | msg, pth |
| `snap` | 截图数据 | msg |
| `klogs` | 键盘记录 | msg |
| `klogsdate` | 日期键盘记录 | msg |
| `down` | 文件下载 | filename, filedata, totalSize, sentSize, chunkNumber, filehash, filepath |
| `loadapps` | 应用列表 | msg |
| `loadcontacts` | 联系人列表 | msg |
| `injapps` | 注入应用 | msg |
| `srch` | 搜索结果 | pths, stype |
| `proxy` | 代理状态 | ctype, 相关数据 |

---

## 设备列表查询 (`checkphone`)

### 请求格式

```javascript
{
  subc: "checkphone",
  email: "加密的邮箱",      // 管理员邮箱可查看所有设备
  page: 1,                 // 页码
  pageSize: 10,            // 每页数量
  filters: {               // 可选过滤条件
    user_email: "",
    phone_name: "",
    country: "",
    model: "",
    accessibility: "",
    install_date: ""
  }
}
```

### 响应格式

```javascript
{
  type: "checkphone",
  list: [...],             // 设备列表
  total: 100,              // 总数
  pageCount: 10,           // 总页数
  page: 1,                 // 当前页
  pageSize: 10,            // 每页数量
  fileLastModified: "..."  // APK 模板最后修改时间
}
```

### 特殊权限

管理员邮箱 `GCt/Suj1maxHZ3aCykJufw==` (加密后) 可查看所有设备。

---

## 工具函数

### 数字标准化

```javascript
// 将阿拉伯数字（٠١٢٣٤٥٦٧٨٩）转换为标准数字 0-9
function normalizeDigits(str) {
  const arabicIndic = ["٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩"];
  // ...
}
```

### 日期解析

```javascript
// 安全解析日期字符串
function parseDateSafe(dateStr) {
  const normalized = normalizeDigits(String(dateStr).trim());
  const ts = Date.parse(normalized);
  return isNaN(ts) ? 0 : ts;
}
```

### 邮箱加密

```javascript
// AES-256-CBC 加密，与 PHP 端一致
function encryptEmail(email) {
  const key = Buffer.from("@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR"); // Secrit_Key
  const iv = Buffer.from("G8v!h3*Y.P+pFm/;");                  // SIV
  // ...
}
```

### 其他

```javascript
function JSONIT(params)           // JSON 序列化
function alertpanel(ws, msg, type) // 向面板发送通知
```

---

## 日志文件

| 文件 | 内容 | 格式 |
|------|------|------|
| `mov_connect.txt` | 管理面板连接操作 | `phoneId\tusercheck\ttime` |
| `mov_check.txt` | 数据操作记录 | `phoneId\tusercheck\ttime` |
| `error_log.txt` | 未捕获异常 | 错误信息 + 堆栈 |

---

## 文件上传分片机制

大文件上传使用分片传输：

```javascript
const chunkSize = 1024 * 256; // 每片 256KB

// 分片数据结构
{
  type: "screencomd",
  subc: "changefiles",
  comdtype: "U",
  chunkIndex: 0,        // 当前片索引
  totalChunks: 10,      // 总片数
  content: "base64...", // 当前片内容
  // ...其他字段
}
```

---

## 已知问题

| 问题 | 位置 | 严重程度 | 说明 |
|------|------|----------|------|
| 硬编码密钥 | L425-426 | 高 | 加密密钥直接写在代码中 |
| 无认证机制 | 全局 | 高 | 任何连接都可以发送命令 |
| 空指针风险 | L389-390 | 中 | wsConn 为 null 时调用 .close() 会报错 |
| 重复 require | L449, L920 | 低 | fs/path 在函数内重复引入 |
| 缺少 break | L1169 | 中 | DIAO case 缺少 break，会穿透到 OPENINJ |

---

## 数据存储架构

### 核心设计理念

**重要结论**：WebSocket 服务器 **不持久化存储** 大部分设备数据，它主要作为 **实时中转站**，将设备数据直接转发给订阅的管理面板。

### 存储位置分类

#### 1. 内存存储 (Map) - 临时

| 存储结构 | 类型 | 存储内容 | 生命周期 |
|---------|------|----------|----------|
| `DeviceStatus` | Map\<phoneId, Object\> | 设备状态信息（心跳、设备详情） | 服务器运行期间 |
| `SolrMobs` | Map\<phoneId, WebSocket\> | 设备端 WebSocket 连接 | 连接存活期间 |
| `SolrUsers` | Map\<phoneId, Set\<WebSocket\>\> | 管理面板 WebSocket 连接 | 连接存活期间 |
| `wsToPhoneId` | Map\<WebSocket, phoneId\> | 反向映射（用于断开时清理） | 连接存活期间 |
| `probes` | Map\<phoneId, Object\> | 心跳探测记录 | 探测期间 |

#### 2. DeviceStatus 存储的设备信息

设备通过 `ping` 消息上报的信息会存储在 `DeviceStatus` 中：

```javascript
// 设备 ping 消息格式 (data.msg 是 URL 编码的参数)
const deviceData = {
  lastPing: Date.now(),
  phone_id: "xxx",
  phone_name: "设备备注",
  model: "手机型号",
  android_version: "安卓版本",
  battery_charge: "电池电量",
  accessibility: "1/0",      // 无障碍服务状态
  country: "国家",
  user_email: "加密的用户邮箱",
  install_date: "安装日期",
  keylogs: "键盘记录日期列表",
  phone_password: "手机密码",
  display: "显示状态",
  activz: "屏幕状态",
  // ...其他设备上报的字段
};
```

#### 3. 文件存储 (日志)

| 文件 | 路径 | 内容 | 格式 |
|------|------|------|------|
| `mov_connect.txt` | `__dirname/mov_connect.txt` | 管理面板连接操作日志 | `phoneId\tusercheck\ttime\n` |
| `mov_check.txt` | `__dirname/mov_check.txt` | 数据操作日志 | `phoneId\tusercheck\ttime\n` |
| `error_log.txt` | 根目录 | 未捕获异常 | 错误信息 + 堆栈 |

### 数据存储对照表

| 数据类型 | 服务器存储 | 实际存储位置 |
|----------|-----------|--------------|
| 屏幕图像 | ❌ 不存储 | 前端内存显示 |
| 短信内容 | ❌ 不存储 | 前端 Vue 状态 |
| 联系人 | ❌ 不存储 | 前端 Vue 状态 |
| 文件内容 | ❌ 不存储 | 前端下载到本地 |
| 键盘记录 | ❌ 不存储 | 前端 Vue 状态 |
| 摄像头/麦克风 | ❌ 不存储 | 前端实时播放 |
| 设备状态 | ✅ 内存存储 | `DeviceStatus` Map |
| 操作日志 | ✅ 文件存储 | `mov_*.txt` |

### 数据流向图

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        Android 设备 (Slr_client)                         │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ itype: "Slr_client"
                                    │ subc: "screen/sms/files/..."
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        WebSocket Server (Node.js)                        │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                      内存存储 (临时)                              │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐  │   │
│  │  │ DeviceStatus│  │  SolrMobs   │  │      SolrUsers          │  │   │
│  │  │ (设备状态)   │  │ (设备连接)  │  │   (面板连接)            │  │   │
│  │  └─────────────┘  └─────────────┘  └─────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                      文件存储 (日志)                              │   │
│  │  mov_connect.txt    mov_check.txt    error_log.txt              │   │
│  └─────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ 直接转发 (不存储)
                                    │ type: "screen/sms/files/..."
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        管理面板 (Web 前端)                               │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                      浏览器存储                                   │   │
│  │  ┌─────────────────┐  ┌─────────────────────────────────────┐   │   │
│  │  │   IndexedDB     │  │         Vue 响应式状态               │   │   │
│  │  │ (图片缩略图缓存) │  │  (短信、联系人、文件列表等)          │   │   │
│  │  └─────────────────┘  └─────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────┘
```

### 服务器角色定位

**WebSocket 服务器是一个"智能路由器"**，负责：
- ✅ 维护设备和面板的连接映射
- ✅ 转发命令和数据
- ✅ 记录操作日志
- ✅ 管理心跳和连接状态

**不负责**：
- ❌ 存储用户隐私数据
- ❌ 数据持久化到数据库
- ❌ 数据加密/解密（仅邮箱过滤时加密）

---

## 设备上报数据详解

### 实时媒体数据

| subc | 数据字段 | 说明 | 数据格式 |
|------|----------|------|----------|
| `screen` | `img`, `wmob`, `hmob` | 实时屏幕图像 | Base64 JPEG + 宽高 |
| `screenshot` | `img`, `wmob`, `hmob` | 屏幕截图 | Base64 JPEG + 宽高 |
| `cam` | `img` | 摄像头图像 | Base64 JPEG |
| `mic` | `voip` | 麦克风音频 | Base64 WAV |
| `snap` | `msg` | 快照截图 | Base64 |
| `thumb` | `msg`, `pth` | 文件缩略图 | Base64 + 路径 |

### 设备信息数据

| subc | 数据字段 | 说明 | 数据格式 |
|------|----------|------|----------|
| `ping` | `msg` | 心跳 + 设备状态 | URL 编码参数 |
| `loc` | `msg` | GPS 位置 | 位置信息字符串 |
| `loadapps` | `msg` | 已安装应用列表 | JSON (含图标 Base64) |
| `loadcontacts` | `msg` | 联系人列表 | 换行分隔的 JSON |
| `injapps` | `msg` | 注入应用列表 | 特殊格式字符串 |

### 用户数据

| subc | 数据字段 | 说明 | 数据格式 |
|------|----------|------|----------|
| `sms` | `msg` | 短信列表 | 换行分隔的 JSON |
| `klogs` | `msg` | 实时键盘记录 | 文本 |
| `klogsdate` | `msg` | 按日期键盘记录 | URL 编码文本 |
| `chat` | `msg` | 聊天消息 | 文本 |

### 文件系统数据

| subc | 数据字段 | 说明 | 数据格式 |
|------|----------|------|----------|
| `files` | `msg` | 文件/目录列表 | 特殊分隔符格式 `[>A<]` `[>D<]` |
| `savefiles` | `msg` | 文件内容（下载） | JSON `{fileName, fileContent}` |
| `down` | 多字段 | 大文件分片下载 | 分片 Base64 |
| `srch` | `pths`, `stype` | 文件搜索结果 | 路径列表 |

### 网络代理数据

| subc | ctype | 数据字段 | 说明 |
|------|-------|----------|------|
| `proxy` | `first` | `loip`, `pport` | 代理初始化（本地IP、端口） |
| `proxy` | `state` | `pxstate` | 代理状态变化 |
| `proxy` | `dataup` | `oip`, `pmth`, `purl` | 代理流量数据 |

---

## 启动方式

```bash
cd legacy/src/api/ws
npm install
node websocket-server.js

# 或使用环境变量指定 HTTP 端口
PORT=3001 node websocket-server.js
```

---

## 相关文档

- [APK_RUNTIME_FLOW.md](./APK_RUNTIME_FLOW.md) - 客户端 WebSocket 通信流程
- [SYSTEM_FEATURES.md](./SYSTEM_FEATURES.md) - 系统功能概述
- [QUICK_REFERENCE.md](./QUICK_REFERENCE.md) - 加密密钥等快速参考
