# 新 Android 客户端重构可行性报告

> 当前 APK 为混淆闭源 Stub，本报告基于现有服务端与文档分析，给出「规划新 Android 客户端」的可行性、范围与实施建议。

---

## 一、现状与目标

### 1.1 当前客户端情况

| 项目 | 说明 |
|------|------|
| **形态** | 预编译 Stub（apktool 解码后约 2.4 万文件、4 个 DEX），深度混淆 |
| **构建** | 通过占位符注入（My_Configs.smali）生成定制 APK，无源码可维护 |
| **协议** | WebSocket（ws/wss），与 PHP Swoole 服务端（端口 8081）通信 |
| **标识** | 设备端 `itype: 'Slr_client'`，连接与消息均带 `pid`（设备唯一 ID） |

### 1.2 重构目标

- **可维护**：使用 Kotlin/Java 源码，版本可控、可审计、可迭代。
- **协议兼容**：与现有 Web 管理端、Swoole 服务端、前端控制面板**协议兼容**，无需改后端即可接入新客户端。
- **能力对等**：在合理范围内实现与当前 Stub 对等的功能（设备注册、心跳、远程控制、数据上报等），可按阶段分 MVP 与完整版。

---

## 二、服务端契约（新客户端必须满足）

以下从服务端代码与文档归纳，新 Android 客户端必须遵守的协议与数据格式。

### 2.1 连接与身份

- **连接地址**：`wss://{host}:{port}/api/ws/` 或 `ws://`（由构建/配置决定）。
- **消息格式**：JSON，UTF-8。
- **设备识别**：每条消息需带 `pid`（设备唯一 ID，如 UUID），首条带 `pid` 的消息会触发服务端 `registerDevice(fd, phoneId)`。
- **itype**：设备发送的消息需带 `itype: "Slr_client"`（`WebSocketConfig::clientTypes()['device']`），否则会被路由到「未知类型」并仅打日志。

### 2.2 心跳与状态（ping）

- **子类型**：`subc: "ping"`。
- **状态数据**：`msg` 为 **application/x-www-form-urlencoded** 字符串（如 `phone_name=xxx&model=xxx&battery_charge=t~88`），服务端使用 `parse_str($encodedData, $params)` 解析后与 `phone_id`、`last_ping`、`is_online` 合并写入内存并同步到 Redis/DB。

**服务端依赖的 ping 字段（建议新客户端至少实现）：**

| 字段 | 说明 | 示例 |
|------|------|------|
| `phone_name` | 设备名称/备注 | 可默认 Build.MODEL 或用户可改 |
| `model` | 设备型号 | Build.MODEL |
| `android_version` | 系统版本 | Build.VERSION.RELEASE 等 |
| `battery_charge` | 电池：`t~%d` 充电中 / `f~%d` 未充电 | `t~88`、`f~45` |
| `accessibility` | 无障碍是否开启 | `1` / `0` |
| `country` | 国家代码 | Locale.getDefault().country |
| `ip` | 可选，服务端可代填连接 IP | - |
| `user_email` | 绑定用户（用于创建设备时归属） | 与构建/登录一致 |
| `install_date` | 安装日期 | 首次安装时写入 |
| `activz` | 屏幕状态：0/1/2/3（亮/灭 × 锁/未锁） | 见 DEVICE_STATUS_FIELDS.md |
| `network` | 网络类型 | WIFI / 4G / 5G / MOBILE |
| `display` | 显示/隐藏图标等 | 可选 |
| `has_password` | 锁屏密码 | 0 / 1 |

服务端会把这些字段原样存入状态并通过 `formatForPanel` 返回给前端；缺失字段不会导致断连，但列表/详情展示会不完整。`battery_charge` 格式须为 `t~数字` 或 `f~数字`，否则 `BatteryParser::parseLevel` 可能解析失败。

### 2.3 设备需要接收的命令（服务端 → 设备）

服务端将面板/面板发送类的请求转发给设备，新客户端需解析并响应。下表按 Handler 与 subc 归纳（仅列主要项，完整映射见 PanelHandler / PanelSendHandler）。

**PanelHandler（itype: slr_panel）转发给设备：**

| subc | 含义 | 设备侧预期行为（当前 Stub 行为） |
|------|------|----------------------------------|
| join | 面板订阅设备 | 可选：准备投屏等资源 |
| out | 退出控制 | 停止投屏/释放资源 |
| ping | 面板查状态 | 服务端直接回 statusBatch，设备可不处理 |
| screen | 屏幕控制 | 见下 |
| brows / proxy / fetch / bc / srch / cocu / chat 等 | 浏览器/代理/抓取/广播/搜索/复制/聊天 | 按协议实现或暂不实现 |

**screen 子命令（PanelHandler 内）：**  
`comand` 与 `movetype`、`poi` 等由前端约定（见 CONTROL_PANEL_SCREEN_OPERATIONS.md）：

- `comand: "mov"`：点击/滑动/长按，`movetype` 0/1/2，`poi` 为坐标或 `"(x1,y1):(x2,y2)"`。
- 其他：snap（截图）、vol、kb、L、nav、Q、phonepass、usdt 等，由服务端原样转发。

**PanelSendHandler（itype: slr_panelsend）转发给设备：**

| subc | 含义 | 设备侧预期行为 |
|------|------|----------------|
| Screen | 投屏开关/前后台 | SM/SN/SK/SMOFF/SNOFF/SKOFF 等 |
| Camera / CameraOff | 摄像头开/关 | 采集画面并上报 |
| Location / Locationoff | 定位开/关 | 上报 GPS |
| SMS / SMSSEND | 短信列表/发送 | 读短信/发短信并回复 |
| Contacts | 联系人 | 读联系人并回复 |
| files / changefiles / viewfile | 文件列表/变更/查看 | 文件浏览与下载块 |
| Keylog / Logdate | 键盘记录开关/按日期查询 | 本地记录并回复 |
| LOADAPPS / OPENAPP / UNINSTALLAPP | 应用列表/打开/卸载 | 应用管理 |
| Hideico / Rename / 等 | 隐藏图标/重命名等 | 按协议实现 |
| Activitys（GA/DA/GF/DF/…） | 活动/权限等 | 按协议实现 |
| DIAO / OPENINJ / display / getinject / getgallery 等 | 弹窗/注入/显示/图库等 | 按协议实现 |

以上为「服务端会转发给设备」的命令集合；新客户端可先实现子集（如 ping + screen + Screen + SMS + files + 基础应用/键盘），再逐步补全。

### 2.4 设备需要上报的响应（设备 → 服务端 → 面板）

设备发送的消息经 DeviceHandler 按 `subc` 转成面板可消费的 `type`。新客户端需按以下格式发送，否则前端收不到或解析错误。

| 设备 subc | 建议 payload | 面板侧 type / 说明 |
|-----------|--------------|---------------------|
| ping | `subc: "ping", msg: "urlencoded"` | 触发 statusBatch，不直接到面板为「数据」 |
| screen / screenshot | `subc`, `img`（base64）, `wmob`, `hmob` | type: screen，投屏/截图 |
| sms | `subc: "sms", msg: "..."` | type: sms |
| files / savefiles | `subc`, `msg` | type: files / 文件列表等 |
| snap | `subc: "snap", img: "..."` | type: snap |
| loc | `subc: "loc", msg: "..."` | type: loc |
| loadapps | `subc: "loadapps", msg: "..."` | type: loadapps |
| loadcontacts | `subc: "loadcontacts", msg: "..."` | type: loadcontacts |
| klogs | `subc: "klogs", msg: "..."` | type: klog |
| klogsdate | `subc: "klogsdate", msg: "..."` | type: klogsdate |
| thumb | `subc: "thumb", msg: "..."`, `pth` | type: thumb |
| mic | `subc: "mic", voip: "..."` | type: mic |
| cam | `subc: "cam", img: "..."` | type: cam |
| down | 分块下载字段 | type: down |
| proxy | `ctype` first/state/dataup 等 | type: proxy |

所有消息均需带 `pid`、`itype: "Slr_client"`；服务端根据 `pid` 转发给已订阅该设备的面板。

### 2.5 用户绑定与设备创建

- 设备首次 ping 时，服务端若在内存/Redis 中无该 `pid`，会调用 `DeviceStatusService::createDevice()`。
- `createDevice` 依赖 ping 的 `msg` 中的 `user_email`（或加密邮箱）查找用户；若找不到则回退到 `User::first()`。
- 新客户端在首次上报时应在 `msg` 中包含与当前业务一致的 `user_email`（或服务端认可的标识），以便设备正确归属。

---

## 三、新客户端功能范围建议

### 3.1 MVP（第一阶段：可上线、可替代基础能力）

| 能力 | 说明 | 协议要点 |
|------|------|----------|
| 连接与注册 | 建立 ws/wss，发带 pid、itype 的消息 | itype=Slr_client, pid=UUID |
| 心跳 | 周期（如 10s）发送 ping，msg 为 urlencoded 状态 | subc=ping, msg=phone_name=...&battery_charge=... |
| 设备信息 | 至少 phone_name, model, android_version, battery_charge, country, user_email, install_date, accessibility, activz, network | 满足 formatForPanel 与 DB 同步 |
| 屏幕控制 | 接收 mov（点击/滑动/长按）、snap | 解析 screencomd + movetype + poi，执行 InputManager 或无障碍 |
| 投屏 | 接收 Screen 开/关，上报 screen/screenshot（img + wmob + hmob） | subc=Screen(SM/SN/SK/…)，上报 subc=screen 或 screenshot |
| 基础保活 | 前台服务 + 可选 WakeLock，断线重连 | 保证列表里「在线」稳定 |

**MVP 不包含**：短信/联系人/文件/键盘记录/摄像头/麦克风/定位/应用列表/卸载/注入/钓鱼等，可在列表与远程画面下「能用」，便于验证协议与部署流程。

### 3.2 第二阶段（与现有面板功能对等）

在 MVP 基础上增加：

- 短信：SMS/SMSSEND，上报 sms。
- 联系人：Contacts，上报 loadcontacts。
- 文件：files / changefiles / viewfile，上报 files、savefiles、down（分块）。
- 键盘记录：Keylog/Logdate，本地存储，上报 klogs/klogsdate。
- 摄像头/麦克风：Camera/CameraOff、mic，上报 cam、mic。
- 定位：Location/Locationoff，上报 loc。
- 应用：LOADAPPS/OPENAPP/UNINSTALLAPP，上报 loadapps。
- 其他面板已有但非核心的 subc：按需实现（Rename、Hideico、Activitys、DIAO 等）。

### 3.3 第三阶段（增强与合规）

- 安全性：证书固定、请求签名、敏感数据加密。
- 合规与隐私：权限说明、合规文案、可配置关闭敏感能力。
- 可观测：日志、埋点、崩溃上报（不泄露隐私前提下）。

---

## 四、技术选型建议

### 4.1 语言与运行时

- **Kotlin** + Android SDK，minSdk 建议 24+（与当前 Stub 覆盖范围接近），targetSdk 34。
- 避免依赖与当前服务端协议无关的 Native 库，以便维护和审计。

### 4.2 网络与协议

- **WebSocket**：OkHttp 的 `okhttp-ws` 或 Ktor Client WebSocket，支持 ws/wss、重连、心跳。
- 消息：JSON 序列化/反序列化（kotlinx.serialization 或 Gson），严格按服务端字段名（itype、subc、pid、msg、img、wmob、hmob 等）构造。

### 4.3 关键能力实现方式

| 能力 | 实现方式 |
|------|----------|
| 设备 ID（pid） | 首次安装生成 UUID 存 SharedPreferences/DataStore，与当前逻辑一致可沿用「Android ID + 包名」等生成方式 |
| 投屏/截图 | MediaProjection API，编码为 JPEG base64 通过 subc=screen/screenshot 上报 |
| 点击/滑动/长按 | AccessibilityService 的 performAction，或 Instrumentation（需 root/辅助权限） |
| 保活 | ForegroundService + 通知，AlarmManager/WorkManager 定时拉活，可选 WakeLock |
| 用户标识 | 构建时注入（如 BuildConfig / assets 文件），与现有「构建时占位符」等价 |

### 4.4 构建与配置注入

- **Gradle**：productFlavors 或 buildConfigField 注入服务器 host/port、wss 开关、默认 user 标识等。
- **与现有 ApkBuilder 的关系**：  
  - 方案 A：新客户端独立 APK，不再使用现有 apkstub 模板；构建改为 Gradle 产出 APK，再签名/分发。  
  - 方案 B：保留现有 ApkBuilder 用于旧包，新客户端作为另一套构建产物，共用一个服务端与面板。  
建议采用 **方案 B**，双轨运行一段时间，再逐步下线 Stub 构建。

---

## 五、风险与依赖

| 风险 | 说明 | 缓解 |
|------|------|------|
| 协议遗漏或歧义 | 文档与代码不一致、未覆盖的 subc | 以服务端 MessageRouter、DeviceHandler、PanelHandler、PanelSendHandler 为准做集成测试；新客户端对未实现的 subc 可先忽略或回传空 |
| 用户/设备绑定 | 设备归属依赖 user_email 等 | 新客户端构建/配置阶段注入与现有一致的邮箱或标识，并做「新设备出现在对应用户下」的测试 |
| 加密与兼容 | 当前 Stub 对 USER_MAIL、BSE_URL 等有加密，服务端有 EncryptionService | 若新客户端不沿用旧加密，需确认服务端是否支持「明文或新算法」；建议先与现有加密兼容，再考虑迁移 |
| 保活与厂商差异 | 国产 ROM 杀后台、省电策略 | 前台服务 + 通知 + 用户引导（电池/自启白名单），与现有 Stub 策略类似 |
| 无障碍依赖 | 远程点击/滑动/长按多依赖无障碍 | 新客户端同样需要用户开启无障碍，并在 ping 中正确上报 accessibility |

---

## 六、结论与实施顺序

### 6.1 可行性结论

- **可行**：服务端与前端已固定协议（itype、subc、pid、msg、img 等），新 Android 客户端可按契约实现，无需改服务端即可接入。
- **建议**：采用 **Kotlin + 现有 WebSocket 协议**，分阶段实现：MVP（连接 + 心跳 + 设备信息 + 屏幕控制 + 投屏）→ 数据类能力（短信/联系人/文件/键盘/摄像头/定位/应用）→ 增强与合规。

### 6.2 实施顺序建议

1. **协议与文档固化**：以本报告与 DEVICE_STATUS_FIELDS、WEBSOCKET_CLIENT、CONTROL_PANEL_SCREEN_OPERATIONS 为基础，整理一份「设备端协议清单」（含所有 subc、字段、示例），便于实现与联调。
2. **新仓库/模块**：新建 Android 工程（或 monorepo 下子项目），配置 Gradle、签名、构建产物输出路径。
3. **MVP 开发**：实现连接、pid 注册、周期 ping（含必填字段）、接收 screen 命令（mov + snap）、投屏开关与 screen/screenshot 上报；与现有面板联调设备列表、远程画面、点击/滑动/长按。
4. **集成与构建**：将新 APK 纳入现有分发流程（替换或与 Stub 并存）；如需与现有 ApkBuilder 统一入口，可增加「构建类型：Stub / 新客户端」选择。
5. **第二阶段**：按业务优先级实现 SMS、Contacts、files、Keylog、cam、mic、loc、LOADAPPS 等，逐项与面板/服务端联调。
6. **第三阶段**：安全加固、合规与可观测。

### 6.3 与「模板重构」的关系

- 本报告针对 **「新写一个 Android 客户端」**，与 [APK_TEMPLATE_REFACTORING_FEASIBILITY.md](./APK_TEMPLATE_REFACTORING_FEASIBILITY.md) 中的「层次 D：替换为全新客户端」一致。
- 模板重构文档侧重「现有 apkstub 模板的存储与占位符」；本报告侧重「新客户端的协议、范围与落地步骤」。两者可并行：保留现有 Stub 构建的同时推进新客户端，待新客户端稳定后再考虑下线模板构建。

---

**文档版本**：1.0  
**编写日期**：2026-02-09  
**依据**：`app/WebSocket/` 下 MessageRouter、DeviceHandler、PanelHandler、PanelSendHandler、DeviceStatusService、BatteryParser，及 docs/migration 下 WEBSOCKET_CLIENT、DEVICE_STATUS_FIELDS、CONTROL_PANEL_SCREEN_OPERATIONS、WEBSOCKET_SERVER_PHP，docs/legacy 下 APK_RUNTIME_FLOW。
