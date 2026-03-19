# MODULE_06 远程控制 — Vendor 行为审计

## 1. 模块职责

远程控制核心。通过本地 HTTP 服务器接收来自 rathat 代理和无障碍代理的指令，执行 235 个远程操作（UI 节点查找/操作、设备控制、数据同步、ADB 管理、截图录屏等）。是整个系统的指令执行中枢。

## 2. Vendor 架构

```
server/b.java (11172行, 242个方法) — 核心指令路由器
  ├── 实现 o 接口 (HTTP 请求处理器)
  ├── 235 个路由端点
  ├── 单例模式 (volatile static b)
  └── 分发到各功能模块

server/c.java (146行) — 本地 WebSocket 服务器
  ├── 继承 n1.b (WebSocket Server)
  ├── 端口: 7912
  └── 转发指令到 server/b

server/a.java (63行) — 异步上传任务
  ├── 实现 Runnable
  └── 文件上传到远程服务器

plug/c.java (261行) — 密码破解插件
  ├── ConcurrentLinkedQueue 密码队列
  └── 自动尝试解锁

plug/a.java (55行) — 密码收集器 (PIN)
plug/b.java (76行) — 密码收集器 (图案)
plug/d.java (55行) — 图案密码收集器
plug/e.java (21行) — 接口
plug/f.java (32行) — PIN 密码收集器
```

## 3. 235 个路由端点分类

### /target/ — UI 节点操作 (93 个)

查找节点:
- findByText/findByTextContains/findByTextEndsWith/findByTextMatches/findByTextStartsWith (5)
- findByDesc/findByDescContains/... (5)
- findById/findByIdContains/... (5)
- findByClassName/findByClassNameContains/... (5)
- findByCombine/findByCombineWithChild/findByCombineWithoutChild (3)
- findByOperateOr (1)
- findOneBy* 系列 (同上 ~25)
- findLastBy* 系列 (同上 ~25)
- findParentByCombine/findParentByCombineWithUpLevel/findParentUtilCombine (3)
- findChildUtilUpLevel (1)

滚动查找:
- scrollForwardUtilWithCombine/WithChild/WithoutChild/WithOperateOr (4)
- scrollBackwardUtilWithCombine/WithChild/WithoutChild/WithOperateOr (4)
- scrollForwardUtilMultipleWith* (4)
- scrollBackwardUtilMultipleWith* (4)

操作:
- /target/action — 执行操作 (click/longClick/setText 等)
- /target/refresh — 刷新节点
- /target/matchListenWindow — 匹配监听窗口

### /global/ — 全局操作 (12 个)

- /global/action — 全局操作 (back/home/recents 等)
- /global/lockScreen — 锁屏
- /global/wakeUpScreen — 唤醒屏幕
- /global/keepScreenOn — 保持屏幕常亮
- /global/setText — 设置文本
- /global/copy — 复制
- /global/paste — 粘贴
- /global/delete — 删除
- /global/clear — 清除
- /global/moveHome — 移到开头
- /global/moveEnd — 移到末尾
- /global/execCommand — 执行 shell 命令

### /sync* — 数据同步 (15 个)

- /syncContacts — 同步联系人
- /syncSms — 同步短信
- /syncPackages — 同步应用列表
- /syncPermissions — 同步权限
- /syncPhotos — 同步照片
- /syncVideos — 同步视频
- /syncAudios — 同步音频
- /syncWindows — 同步监听窗口
- /syncADBConfig — 同步 ADB 配置
- /syncPowerControl — 同步电源控制
- /syncLockCipher — 同步锁屏密码
- /syncDownload — 同步下载
- /syncCanWriteSecure — 同步安全写入状态
- /syncAdminActivating — 同步管理员激活
- /syncSmsRecognizePlug — 同步短信识别

### /start* — 启动操作 (14 个)

- /startApp — 启动应用
- /startSettings — 打开设置
- /startAccessibility — 打开无障碍设置
- /startAdminActive — 激活设备管理员
- /startDevSetting — 打开开发者选项
- /startWifiSetting — 打开 WiFi 设置
- /startAppDetailSetting — 打开应用详情
- /startAppWriteSetting — 打开应用写入设置
- /startAboutDevice — 打开关于手机
- /startAppFromDesktop — 从桌面启动应用
- /startVerifyCredential — 启动凭证验证
- /startInstallApp — 启动安装应用
- /startRecord — 开始录制
- /startRatHat — 启动 rathat

### /local* — 本地 ADB 操作 (6 个)

- /localAdbConnect — ADB 连接
- /localAdbPair — ADB 配对
- /localAdbPush — ADB 推送文件
- /localAdbShell — ADB 执行命令
- /localBackAppState — 本地应用状态
- /localDebugPort — 本地调试端口

### 其他 (~95 个)

设备信息: /info, /version, /deviceId, /mainPackageName, /mainServerHost
设备状态: /batteryState, /netState, /screenState, /lockState, /callState, /recordState
权限: /permissions, /permissionInfo, /requestPermission
截图录屏: /screenshot/0, /screenrecord/start, /screenrecord/stop, /screenrecord/state, /miniCap/scale
摄像头: /frontCameraLive, /backCameraLive, /stopCameraLive
应用管理: /browserApps, /packages, /killApp, /install, /prepareInstallApp, /finishInstallApp
文件: /deleteFile, /asyncDownload, /uploadAppIcon
通信: /callPhone, /sendSms, /contacts
ADB: /enableDebug, /enableDevelopment, /enableWifiDebug, /closeADBDebug, /closeDevelopment, /closeWifiDebug
安全: /deviceAdmin, /stopAdminActive, /confirmLock, /enterCipher, /unlock, /openWriteSecure
保活: /blockView, /requestLocalKeepAlive, /ignoreBatteryOptimization
其他: /readScreenWindow, /refreshActiveWindow, /removeDelegate, /removeAccount, /uninstallPolicy 等

## 4. Vendor vs Replica 对比

| Vendor | 行数 | Replica | 行数 | 覆盖 |
|--------|------|---------|------|------|
| server/b.java (路由器) | 11172 | control/server/ (7个handler) | ~1100 | ~10% |
| server/c.java (WebSocket) | 146 | control/server/LocalWebSocketServer | 125 | ✅ 85% |
| server/a.java (上传) | 63 | control/server/ServerUploadTask | 62 | ✅ 98% |
| plug/c.java (密码破解) | 261 | control/plug/CrackLockCipherPlug | 140 | ⚠️ 54% |
| plug/a+b+d+e+f | 239 | control/plug/ (3个) | ~155 | ⚠️ 65% |
| — | — | service/CommandHandler | 370 | replica 独有 |

### 关键差距

- Vendor server/b.java: 242 个方法, 235 个路由
- Replica control/server/: 7 个 handler 文件, ~50 个方法
- 差距: ~190 个路由未实现

## 5. 优先修复项

### P0 (基本远程控制)
1. 补齐 /global/ 12 个全局操作 (lockScreen/wakeUp/execCommand 等)
2. 补齐 /info, /version, /deviceId 等设备信息端点
3. 补齐 /screenshot/0 截图端点
4. 补齐 /target/action 操作执行端点

### P1 (数据同步)
5. 补齐 /sync* 15 个数据同步端点
6. 补齐 /start* 14 个启动操作端点
7. 补齐 /local* 6 个 ADB 操作端点

### P2 (完整功能)
8. 补齐 /target/findBy* 93 个节点查找端点
9. 补齐摄像头/录屏端点
10. 补齐密码破解插件完整逻辑

## 6. 真机验证要点

```bash
# 检查本地 HTTP 服务器
adb logcat -s "HttpCommandServer" "LocalWebSocketServer"

# 测试设备信息端点
curl http://127.0.0.1:7912/info
curl http://127.0.0.1:7912/version

# 测试截图
curl http://127.0.0.1:7912/screenshot/0

# 测试全局操作
curl -X POST http://127.0.0.1:7912/global/action -d '{"action":"back"}'
```
