# 类似开源 Android 客户端调研报告

> 针对「与当前飞鹰管理系统 Android 客户端类似」的开源方案做市面调研，便于评估替代、参考或集成可能。

---

## 一、当前系统客户端特征（对标维度）

| 维度 | 当前系统 |
|------|----------|
| **部署** | 每台设备安装定制 APK，通过互联网连接自建服务端 |
| **连接** | WebSocket（ws/wss），PHP Swoole 服务端，端口 8081 |
| **协议** | JSON 消息，设备端 `itype: Slr_client`，`pid` 标识设备，心跳 `subc: ping` + urlencoded `msg` |
| **能力** | 远程投屏与触摸、截图、短信/联系人/文件、键盘记录、摄像头/麦克风、定位、应用列表与卸载、保活与前台服务等 |
| **服务端** | 自建（Laravel + Swoole），可多租户、设备归属用户 |

因此「类似」指：**设备端为独立 APK，通过网络连接自建或可自建的服务端，支持远程画面与部分数据能力（如文件、短信等）**。

---

## 二、调研结论概览

| 结论 | 说明 |
|------|------|
| **无直接替代** | 未发现与当前 WebSocket 协议、Swoole 服务端及现有功能集完全一致的开源 Android 客户端，无法「直接替换」现有 APK 且不改后端。 |
| **有可参考/部分借鉴** | 存在若干开源「设备端 Agent + 服务端」方案，具备投屏、远程操作或简单数据能力，可参考实现方式或协议设计，但需自接协议或改后端。 |
| **部署模型不同** | 部分知名项目（如 STF）为 USB/ADB 设备农场模型，与「单机安装 APK + 互联网连接」不同，不能直接当「类似客户端」用。 |

---

## 三、主要开源项目对比

### 3.1 Headwind Remote / remote-control-android（最接近架构）

| 项目 | 说明 |
|------|------|
| **仓库** | [lt3stus3el/remote-control-android](https://github.com/lt3stus3el/remote-control-android)（原 Headwind Remote 开源版 Agent） |
| **许可** | Apache-2.0 |
| **架构** | Android Agent（APK） + 自建服务端，设备通过互联网连接服务器，浏览器端远程查看画面并发送手势。 |
| **技术** | 设备端：MediaProjection 投屏 + AccessibilityService 回放手势；通信：**WebRTC**（Janus 媒体服务），非 WebSocket。 |
| **能力** | 投屏、远程手势（点击/滑动等），无 USB。 |
| **状态** | 开源版已停止维护（因被滥用做恶意软件），源码仍在 GitHub；商业版/集成版由 Headwind MDM 提供。 |

**与当前系统对比**：  
- 相同点：设备端独立 APK、连接自建服务器、远程画面与手势控制、无需 USB。  
- 差异：协议为 WebRTC 而非 WebSocket/JSON；无短信、文件、键盘记录、摄像头/麦克风等数据能力；需配套 Headwind 服务端或自建 Janus/WebRTC 服务，无法直接对接现有 PHP/Swoole WebSocket。

**可借鉴**：MediaProjection + AccessibilityService 的投屏与手势实现、无 root 设计；若要做「新客户端 + 新协议」，可参考其架构，但协议层需自实现或适配现有 WebSocket。

---

### 3.2 MeshCentral Android Agent

| 项目 | 说明 |
|------|------|
| **仓库** | [Ylianst/MeshCentralAndroidAgent](https://github.com/Ylianst/MeshCentralAndroidAgent) |
| **许可** | Apache-2.0 |
| **架构** | Android Agent + MeshCentral 服务端（Node.js），设备通过 QR 码配对连接。 |
| **能力** | 仅看屏（view-only 投屏）、文件下载（图片/音视频）、设备信息与电池；不支持远程点击、短信/联系人/键盘记录等。 |
| **连接** | 自有协议（MeshCentral 体系），非当前 WebSocket 格式。 |

**与当前系统对比**：  
- 相同点：设备端 Agent、远程查看画面、部分文件与设备信息。  
- 差异：投屏为仅看、无触摸控制；无短信/联系人/键盘/摄像头等；协议与 MeshCentral 强绑定，不能直接对接现有 Swoole 服务端。

**可借鉴**：设备信息与文件浏览的 UI/交互思路；若采用 MeshCentral 作为新服务端则可直接使用该 Agent，否则仅作参考。

---

### 3.3 DeviceFarmer STF（Smartphone Test Farm）

| 项目 | 说明 |
|------|------|
| **仓库** | [DeviceFarmer/stf](https://github.com/DeviceFarmer/stf) |
| **许可** | Apache-2.0 |
| **架构** | 设备通过 **USB 连接** 到运行 STF + ADB 的机器，浏览器通过 STF 服务端控制设备；需在设备上安装 STFService.apk 等，但**连接依赖 USB/ADB**，非「设备单独联网」。 |
| **能力** | 投屏、触摸、输入、复制粘贴、APK 安装、Shell、文件浏览、设备列表与预约等，功能丰富。 |
| **状态** | 活跃维护（DeviceFarmer 组织），Star 约 4.3k。 |

**与当前系统对比**：  
- 部署模型不同：STF 是「设备插在机房/本机 USB + ADB」，当前系统是「设备装 APK + 任意网络连接服务器」。  
- 不能作为「类似的开源 Android 客户端」直接替代现有 APK；若未来有「机房 USB 设备农场」需求，可单独评估 STF。

---

### 3.4 scrcpy 及 Web 化变种

| 项目 | 说明 |
|------|------|
| **本体** | [Genymobile/scrcpy](https://github.com/Genymobile/scrcpy)：桌面端通过 **ADB（多数为 USB）** 投屏与控制 Android 设备。 |
| **Web 化** | 如 [chongbo2013/scrcpy-websocket](https://github.com/chongbo2013/scrcpy-websocket)、ws-scrcpy-docker 等：在服务端跑 scrcpy，通过 WebSocket 把画面/控制推到浏览器。设备仍通过 ADB 连到该服务端。 |

**与当前系统对比**：  
- 模型仍是「设备 —ADB— 服务端 —WebSocket— 浏览器」，设备端没有「独立 APK 直连业务服务器」的形态。  
- 与当前「每台设备一个 APK + 直连 PHP WebSocket 服务」不直接可比；可作为「机房内设备通过 scrcpy 做 Web 控制」的参考，而非替代现有客户端。

---

### 3.5 aPuppet Android / 其他远程控制 App

| 项目 | 说明 |
|------|------|
| **aPuppet** | [MrYoda/apuppet-android](https://github.com/MrYoda/apuppet-android)：与 remote-control-android 同源或类似思路，设备投屏到浏览器，手势回放，无 USB。 |
| **ControlR** | 自托管远程控制方案（MIT），体量较小。 |

与 Headwind Remote / remote-control-android 类似：偏「投屏 + 手势」，无短信/文件/键盘等数据能力，协议也非当前 WebSocket 格式。

---

## 四、功能与协议对照简表

| 能力/维度 | 当前系统 | Headwind Remote | MeshCentral Agent | STF |
|-----------|----------|------------------|-------------------|-----|
| 设备端独立 APK + 互联网连接 | ✅ | ✅ | ✅ | ❌（USB/ADB） |
| 远程投屏 | ✅ | ✅ | ✅（仅看） | ✅ |
| 远程触摸/手势 | ✅ | ✅ | ❌ | ✅ |
| 短信/联系人/文件 | ✅ | ❌ | 部分文件 | 文件浏览 |
| 键盘记录 | ✅ | ❌ | ❌ | ❌ |
| 摄像头/麦克风 | ✅ | ❌ | ❌ | ❌ |
| 定位 | ✅ | ❌ | ❌ | ❌ |
| 应用列表/卸载等 | ✅ | ❌ | ❌ | 有 |
| 协议对接现有 Swoole WebSocket | — | ❌（WebRTC） | ❌（MeshCentral） | ❌（ADB） |

---

## 五、结论与建议

### 5.1 是否有「类似」开源 Android 客户端？

- **有架构相近的**：Headwind Remote（remote-control-android）、MeshCentral Android Agent 均为「设备端 APK + 连接自建/指定服务端」，具备远程画面或简单数据能力。  
- **无可直接替代的**：没有发现与当前系统「同一 WebSocket 协议 + 同一功能集（短信/文件/键盘/摄像头等）」的开源客户端；协议与后端均为各自生态，不能即插即用。

### 5.2 建议用法

| 需求 | 建议 |
|------|------|
| **直接替代现有 APK 且不改后端** | 不可行；需自研新客户端并实现现有协议（参见 [NEW_ANDROID_CLIENT_FEASIBILITY.md](./NEW_ANDROID_CLIENT_FEASIBILITY.md)）。 |
| **参考实现方式** | 可参考 Headwind Remote / MeshCentral 的 MediaProjection、AccessibilityService、设备信息与文件能力实现；STF 可参考其设备管理与控制交互。 |
| **采用整套开源方案替代当前系统** | 若可接受更换服务端与协议：可评估 MeshCentral（含 Android Agent）或 Headwind MDM + Remote 等，需迁移后端与前端。 |
| **仅需机房 USB 设备农场** | 可单独评估 DeviceFarmer STF，与当前「互联网 APK 客户端」场景互补而非替代。 |

### 5.3 与新客户端重构的关系

- 本调研说明：**市面上没有可拿来即用的、与当前协议与功能对等的开源客户端**，新 Android 客户端仍需按 [NEW_ANDROID_CLIENT_FEASIBILITY.md](./NEW_ANDROID_CLIENT_FEASIBILITY.md) 自研并兼容现有 WebSocket 契约。  
- 实现时可借鉴上述项目的**技术选型与实现方式**（如投屏、无障碍、保活等），协议与业务逻辑需自行实现以对接现有服务端与面板。

---

**文档版本**：1.0  
**编写日期**：2026-02-09  
**依据**：公开网络检索与各项目 GitHub/官网说明整理。
