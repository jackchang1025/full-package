# TODO List

## P1 — 功能性缺失

### /adbShell 通过隧道返回空 output
- **现象**: `POST /adbShell {"cmd":"whoami"}` → `{"success":false,"output":""}`
- **根因**: Java 层 `RemoteConfigManager.adbShell()` 调用 `SystemOptimizeManager.executeShellCommand(cmd)`，后者需要 `getOrCreateAdbConnection()` 建立 ADB TCP 连接。当前 `debugPort=5555`（USB 端口），无线调试端口未正确保存。
- **影响**: 服务器无法通过 frps 隧道远程执行 ADB shell 命令
- **修复方向**: 
  1. `readDebugPortFromScreen()` 需要在配对弹窗**关闭前**读取端口（当前在弹窗关闭后读，页面已切换）
  2. 或在 `pairInPairSuccess` handler 中用重试循环从无线调试详情页读取
  3. fallback: `getWirelessDebugPort()` 通过 `Settings.Global.adb_wifi_port` 或 netstat 扫描
- **验证命令**: `curl -s -X POST http://localhost:20003/adbShell -H "Content-Type: application/json" -d '{"cmd":"whoami"}'`
- **期望结果**: `{"success":true,"data":{"output":"shell\n"}}`

### Scene E: confirmLock 未实现
- **影响**: 有锁屏密码的设备，开启开发者选项时弹出密码确认，配对流程卡住
- **Vendor**: `mainAccessibilityEventHandler` 中 Scene E 检测 + 自动输入/等待

### Scene F: securityCenter 未实现
- **影响**: MIUI 安全中心弹窗阻断配对流程
- **Vendor**: `mainAccessibilityEventHandler` 中 Scene F 检测 + 自动点击确认

## P2 — 鲁棒性

### serverAddr 需手动设置
- 当前通过 `adb shell settings put global debug_server_addr` 或 `/setConfig` API 设置
- 生产环境需通过 C2/WebSocket 自动配置

### OPPO force-stop 后无障碍事件丢失
- Android 16 行为，install -r 也会导致
- 需用户手动重新开关无障碍服务

### SilentRecover (c41 case 7) 未完整实现
- local-service 掉线时的静默恢复流程（端口扫描 + ADB 重连 + push binary）
- 当前只有简化版 `deployLocalService()` fallback

### uploadAdbKeys 服务器通信
- 依赖加密的 C2 服务器地址，当前 stub 返回 false

## P3 — 细节优化

### WindowDetector (bf1) 未复刻
- Vendor 使用缓存的窗口标题检测，replica 每次遍历 accessibility tree
- 影响 MIUI/ColorOS 设备的页面检测可靠性

### frpc local_port 被 local-service 修正
- Go 代码自动修正 `local_port: 7910 → 7912`，实际隧道连到 Go 服务而非 Java HTTP
- 功能上不影响（Go 转发请求到 Java），但不符合原始设计意图
