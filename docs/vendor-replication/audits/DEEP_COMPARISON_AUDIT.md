# Vendor APK vs Replica APK — 真机深度对比审核协议

> 本文档为 Claude Code 可执行的自动化深度对比审核方案。
> 目标: 在真机上同时运行 vendor APK 和 replica APK，逐模块采集运行日志，深度分析行为差异。

---

## 基础信息

| 项目 | Vendor APK | Replica APK |
|------|-----------|-------------|
| 文件 | `app/storage/app/apk/apkstub/stripchat-release.apk` | `android/app/build/outputs/apk/debug/app-debug.apk` |
| 包名 | `org.ldtape.qqlhl` | `com.vendor.rat` |
| 版本 | 2.0 (versionCode=2) | 1.0.0 (versionCode=1) |
| compileSdk | 36 (Android 16) | 34 (Android 14) |
| 权限数 | 118 | 27 |
| 大小 | 48.6 MB | 见构建产物 |

### ADB 常量

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.162:5555"
VENDOR_PKG="org.ldtape.qqlhl"
REPLICA_PKG="com.vendor.rat"
```

---

## 执行前提

```bash
# 1. 连接设备
$ADB connect $DEVICE

# 2. 确认设备在线
$ADB -s $DEVICE devices

# 3. 确保 replica APK 是最新构建
cd android && ./gradlew assembleDebug

# 4. 卸载旧版本 (如有)
$ADB -s $DEVICE uninstall $VENDOR_PKG 2>/dev/null
$ADB -s $DEVICE uninstall $REPLICA_PKG 2>/dev/null
```

---

## 阶段一: 安装对比

### STEP-1.1: 安装 Vendor APK

```bash
$ADB -s $DEVICE install -r app/storage/app/apk/apkstub/stripchat-release.apk
```

验证:
```bash
$ADB -s $DEVICE shell pm list packages | grep $VENDOR_PKG
# 预期: package:org.ldtape.qqlhl
```

### STEP-1.2: 安装 Replica APK

```bash
$ADB -s $DEVICE install -r android/app/build/outputs/apk/debug/app-debug.apk
```

验证:
```bash
$ADB -s $DEVICE shell pm list packages | grep $REPLICA_PKG
# 预期: package:com.vendor.rat
```

### STEP-1.3: 权限对比快照

```bash
# Vendor 权限
$ADB -s $DEVICE shell dumpsys package $VENDOR_PKG | grep "permission" > /tmp/vendor_permissions.txt

# Replica 权限
$ADB -s $DEVICE shell dumpsys package $REPLICA_PKG | grep "permission" > /tmp/replica_permissions.txt

# 差异
diff /tmp/vendor_permissions.txt /tmp/replica_permissions.txt
```

### STEP-1.4: 组件注册对比

```bash
# Vendor 组件
$ADB -s $DEVICE shell dumpsys package $VENDOR_PKG | grep -E "Activity|Service|Receiver|Provider" > /tmp/vendor_components.txt

# Replica 组件
$ADB -s $DEVICE shell dumpsys package $REPLICA_PKG | grep -E "Activity|Service|Receiver|Provider" > /tmp/replica_components.txt

# 差异
diff /tmp/vendor_components.txt /tmp/replica_components.txt
```

---

## 阶段二: MODULE_08 启动流程对比

> 最基础的模块，所有其他模块依赖于此。

### STEP-2.1: Vendor 启动日志采集

```bash
# 清空 logcat
$ADB -s $DEVICE logcat -c

# 启动 vendor
$ADB -s $DEVICE shell am start -n $VENDOR_PKG/.activity.MainActivity

# 等待 5 秒让初始化完成
sleep 5

# 采集全量日志 (保存到本地)
$ADB -s $DEVICE logcat -d -v threadtime | grep -i "$VENDOR_PKG\|MainApplication\|MyApp\|BuildConfig\|config\|init\|WebView\|Guide" > /tmp/vendor_m08_startup.log

# 采集进程信息
$ADB -s $DEVICE shell pidof $VENDOR_PKG > /tmp/vendor_m08_pid.txt

# 采集 crash 信息
$ADB -s $DEVICE logcat -d | grep "FATAL EXCEPTION" | grep -i "$VENDOR_PKG" > /tmp/vendor_m08_crash.log
```

检查项:
```
□ 进程启动成功 (PID 存在)
□ Application.onCreate 执行
□ Config 加载 (config.json 解密)
□ WebView 加载完成
□ 引导弹窗 (无障碍未开启时)
□ 无 FATAL EXCEPTION
```

### STEP-2.2: Replica 启动日志采集

```bash
# 清空 logcat
$ADB -s $DEVICE logcat -c

# 启动 replica
$ADB -s $DEVICE shell am start -n $REPLICA_PKG/.activity.ActivMain

# 等待 5 秒
sleep 5

# 采集全量日志
$ADB -s $DEVICE logcat -d -v threadtime | grep -i "$REPLICA_PKG\|MainApplication\|MyApp\|AppConfig\|ConfigDecryptor\|WebView\|Guide\|ActivMain" > /tmp/replica_m08_startup.log

# 采集进程信息
$ADB -s $DEVICE shell pidof $REPLICA_PKG > /tmp/replica_m08_pid.txt

# 采集 crash 信息
$ADB -s $DEVICE logcat -d | grep "FATAL EXCEPTION" | grep -i "$REPLICA_PKG" > /tmp/replica_m08_crash.log
```

### STEP-2.3: 启动流程差异分析

```
对比维度:
1. 启动耗时: 从 am start 到 Activity.onResume 的时间差
2. 初始化顺序: Application.init → Config → WebView → Guide 的日志顺序
3. 线程模型: 主线程 vs 后台线程的初始化分布
4. 异常处理: 是否有 Warning/Error 级别日志
5. 内存占用: adb shell dumpsys meminfo <pkg>
```

```bash
# 内存对比
$ADB -s $DEVICE shell dumpsys meminfo $VENDOR_PKG > /tmp/vendor_m08_mem.txt
$ADB -s $DEVICE shell dumpsys meminfo $REPLICA_PKG > /tmp/replica_m08_mem.txt
```

---

## 阶段三: MODULE_01 网络通信对比

### STEP-3.1: Vendor 网络行为

```bash
$ADB -s $DEVICE logcat -c
# 等待 10 秒让网络初始化完成
sleep 10
$ADB -s $DEVICE logcat -d -v threadtime | grep -iE "http|https|socket|network|tls|ssl|conscrypt|okhttp|connect|dns|url|server" | grep -i "$VENDOR_PKG" > /tmp/vendor_m01_network.log

# 网络连接状态
$ADB -s $DEVICE shell dumpsys connectivity | grep -A5 "$VENDOR_PKG" > /tmp/vendor_m01_conn.txt
```

检查项:
```
□ TLS Provider 初始化 (Conscrypt)
□ 服务器地址解密/加载
□ HTTP 客户端初始化
□ WebSocket 连接尝试
□ DNS 解析行为
□ 证书验证行为
□ 连接超时/重试策略
```

### STEP-3.2: Replica 网络行为

```bash
$ADB -s $DEVICE logcat -c
sleep 10
$ADB -s $DEVICE logcat -d -v threadtime | grep -iE "http|https|socket|network|tls|ssl|conscrypt|okhttp|connect|NetworkManager|WebSocketClient|HttpClient" > /tmp/replica_m01_network.log

$ADB -s $DEVICE shell dumpsys connectivity | grep -A5 "$REPLICA_PKG" > /tmp/replica_m01_conn.txt
```

### STEP-3.3: 网络差异分析

```
对比维度:
1. TLS 版本: TLS 1.2 vs 1.3
2. 连接目标: 域名/IP/端口
3. 请求频率: 心跳间隔
4. 重连策略: 断线后的行为
5. 证书固定: 是否有 certificate pinning
```

---

## 阶段四: MODULE_02 权限绕过对比

### STEP-4.1: Vendor 无障碍 + 设备管理员

```bash
# 无障碍服务状态
$ADB -s $DEVICE shell dumpsys accessibility | grep -A20 "$VENDOR_PKG" > /tmp/vendor_m02_a11y.txt

# 设备管理员状态
$ADB -s $DEVICE shell dumpsys device_policy | grep -A10 "$VENDOR_PKG" > /tmp/vendor_m02_admin.txt

# 无障碍事件日志
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 3
$ADB -s $DEVICE logcat -d | grep -iE "accessibility|a11y|event|window.*change" | grep -i "$VENDOR_PKG" > /tmp/vendor_m02_events.log
```

检查项:
```
□ 无障碍服务已注册 (dumpsys 可见)
□ ServiceInfo 配置 (feedbackType, flags, eventTypes)
□ 设备管理员已注册
□ 事件接收 (WINDOW_STATE_CHANGED, WINDOW_CONTENT_CHANGED)
□ Force-stop 后自动恢复 (WRITE_SECURE_SETTINGS)
□ 无障碍暂停/恢复逻辑
```

### STEP-4.2: Replica 无障碍 + 设备管理员

```bash
$ADB -s $DEVICE shell dumpsys accessibility | grep -A20 "$REPLICA_PKG" > /tmp/replica_m02_a11y.txt
$ADB -s $DEVICE shell dumpsys device_policy | grep -A10 "$REPLICA_PKG" > /tmp/replica_m02_admin.txt

$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 3
$ADB -s $DEVICE logcat -d | grep -iE "accessibility|MyAccessibilityService|EngineManager|AutoEngine|event" | grep -i "$REPLICA_PKG" > /tmp/replica_m02_events.log
```

### STEP-4.3: 权限差异分析

```
对比维度:
1. ServiceInfo 配置是否一致 (feedbackType, notificationTimeout, flags)
2. 事件过滤: 哪些 eventType 被处理
3. 自动恢复: force-stop 后的行为
4. 引擎分发: 事件 → EngineManager → 具体 Engine 的链路
```

---

## 阶段五: MODULE_04 UI 自动化对比

### STEP-5.1: Vendor UI 引擎行为

```bash
$ADB -s $DEVICE logcat -c

# 触发多个页面切换
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 2
$ADB -s $DEVICE shell am start -a android.settings.WIFI_SETTINGS
sleep 2
$ADB -s $DEVICE shell am start -a android.settings.APPLICATION_SETTINGS
sleep 2
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 2

$ADB -s $DEVICE logcat -d | grep -iE "engine|filter|node|selector|uiobject|window.*match|click|scroll" | grep -i "$VENDOR_PKG" > /tmp/vendor_m04_ui.log
```

### STEP-5.2: Replica UI 引擎行为

```bash
$ADB -s $DEVICE logcat -c

$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 2
$ADB -s $DEVICE shell am start -a android.settings.WIFI_SETTINGS
sleep 2
$ADB -s $DEVICE shell am start -a android.settings.APPLICATION_SETTINGS
sleep 2
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 2

$ADB -s $DEVICE logcat -d | grep -iE "AutoEngine|Engine|UiNode|Filter|Selector|window.*match|HuaweiEngine|XiaomiEngine|OppoEngine" > /tmp/replica_m04_ui.log
```

### STEP-5.3: UI 引擎差异分析

```
对比维度:
1. 页面识别: 同一个 Settings 页面，两个 APK 是否识别出相同的窗口标题
2. 引擎选择: 同一设备上，是否选择了相同的厂商引擎
3. 节点查找: UiNode 查找结果数量是否一致
4. 操作执行: click/scroll 等操作是否触发
5. 性能: 事件处理延迟
```

---

## 阶段六: MODULE_03 厂商适配对比

### STEP-6.1: 设备厂商识别

```bash
# 获取设备信息
$ADB -s $DEVICE shell getprop ro.product.manufacturer
$ADB -s $DEVICE shell getprop ro.product.brand
$ADB -s $DEVICE shell getprop ro.product.model
$ADB -s $DEVICE shell getprop ro.build.display.id
```

### STEP-6.2: Vendor 厂商引擎日志

```bash
$ADB -s $DEVICE logcat -c
# 打开设置触发引擎
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 5
$ADB -s $DEVICE logcat -d | grep -iE "huawei|xiaomi|oppo|vivo|samsung|engine|vendor|brand|manufacturer" | grep -i "$VENDOR_PKG" > /tmp/vendor_m03_engine.log
```

### STEP-6.3: Replica 厂商引擎日志

```bash
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 5
$ADB -s $DEVICE logcat -d | grep -iE "HuaweiEngine|XiaomiEngine|OppoEngine|VivoEngine|SamsungEngine|EngineManager|DeviceUtils|brand|manufacturer" > /tmp/replica_m03_engine.log
```

### STEP-6.4: 厂商适配差异分析

```
对比维度:
1. 厂商识别: 是否识别出相同的设备厂商
2. 引擎加载: 是否加载了相同的厂商引擎
3. 自动化策略: 同一设置页面，执行的自动化步骤是否一致
4. 保活策略: 厂商特定的保活方案是否一致
```

---

## 阶段七: MODULE_05 数据收集对比

### STEP-7.1: Vendor 广播接收

```bash
$ADB -s $DEVICE logcat -c

# 触发息屏/亮屏
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 3
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 3

# 触发网络变化 (开关飞行模式)
$ADB -s $DEVICE shell settings put global airplane_mode_on 1
$ADB -s $DEVICE shell am broadcast -a android.intent.action.AIRPLANE_MODE
sleep 3
$ADB -s $DEVICE shell settings put global airplane_mode_on 0
$ADB -s $DEVICE shell am broadcast -a android.intent.action.AIRPLANE_MODE
sleep 3

$ADB -s $DEVICE logcat -d | grep -iE "screen|power|battery|network|sms|call|package|boot|locale|receiver|broadcast" | grep -i "$VENDOR_PKG" > /tmp/vendor_m05_data.log
```

### STEP-7.2: Replica 广播接收

```bash
$ADB -s $DEVICE logcat -c

$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 3
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 3

$ADB -s $DEVICE shell settings put global airplane_mode_on 1
$ADB -s $DEVICE shell am broadcast -a android.intent.action.AIRPLANE_MODE
sleep 3
$ADB -s $DEVICE shell settings put global airplane_mode_on 0
$ADB -s $DEVICE shell am broadcast -a android.intent.action.AIRPLANE_MODE
sleep 3

$ADB -s $DEVICE logcat -d | grep -iE "ScreenBroadcastReceiver|PowerBroadcastReceiver|NetWorkReceiver|SmsReceiver|CallReceiver|PackageReceiver|DataCollectionManager|KeepAliveManager" > /tmp/replica_m05_data.log
```

### STEP-7.3: 数据收集差异分析

```
对比维度:
1. 广播注册: 注册了哪些 BroadcastReceiver
2. 事件响应: 息屏/亮屏/解锁 是否都有响应
3. 数据采集: 采集了哪些类型的数据
4. 上报频率: 数据上报的时间间隔
5. 本地存储: SharedPreferences / 文件写入
```

---

## 阶段八: MODULE_07 保活机制对比

### STEP-8.1: Vendor 保活行为

```bash
# 采集后台存活状态
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 30

# 检查进程是否存活
$ADB -s $DEVICE shell pidof $VENDOR_PKG

# 检查 JobScheduler
$ADB -s $DEVICE shell dumpsys jobscheduler | grep -A5 "$VENDOR_PKG" > /tmp/vendor_m07_job.txt

# 检查 AlarmManager
$ADB -s $DEVICE shell dumpsys alarm | grep "$VENDOR_PKG" > /tmp/vendor_m07_alarm.txt

# 检查账号同步
$ADB -s $DEVICE shell dumpsys account | grep -A5 "$VENDOR_PKG" > /tmp/vendor_m07_account.txt

# 保活日志
$ADB -s $DEVICE logcat -d | grep -iE "keepalive|heartbeat|check.*process|alarm|job|schedule|boot|wake" | grep -i "$VENDOR_PKG" > /tmp/vendor_m07_keepalive.log
```

### STEP-8.2: Replica 保活行为

```bash
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 30

$ADB -s $DEVICE shell pidof $REPLICA_PKG

$ADB -s $DEVICE shell dumpsys jobscheduler | grep -A5 "$REPLICA_PKG" > /tmp/replica_m07_job.txt
$ADB -s $DEVICE shell dumpsys alarm | grep "$REPLICA_PKG" > /tmp/replica_m07_alarm.txt
$ADB -s $DEVICE shell dumpsys account | grep -A5 "$REPLICA_PKG" > /tmp/replica_m07_account.txt

$ADB -s $DEVICE logcat -d | grep -iE "KeepAliveManager|KeepAliveJobService|CheckProcessThread|KeepHeartThread|AlarmReceiver|BootReceiver|DataSyncThread|ScheduledTimerTask" > /tmp/replica_m07_keepalive.log
```

### STEP-8.3: 保活差异分析

```
对比维度:
1. 后台存活: 30 秒后进程是否存活
2. JobScheduler: 注册的 Job 数量和调度间隔
3. AlarmManager: 定时唤醒的间隔
4. 账号同步: AccountAuthenticator 是否注册
5. 前台服务: 是否有前台通知
6. Force-stop 恢复: 被杀后能否自动重启
```

---

## 阶段九: MODULE_06 远程控制对比

### STEP-9.1: Vendor 控制服务

```bash
$ADB -s $DEVICE logcat -d | grep -iE "server|command|handler|route|dispatch|websocket|http.*server|local.*server" | grep -i "$VENDOR_PKG" > /tmp/vendor_m06_control.log

# 检查监听端口
$ADB -s $DEVICE shell netstat -tlnp 2>/dev/null | grep "$VENDOR_PKG" > /tmp/vendor_m06_ports.txt
# 备选
$ADB -s $DEVICE shell ss -tlnp 2>/dev/null | grep "$VENDOR_PKG" >> /tmp/vendor_m06_ports.txt
```

### STEP-9.2: Replica 控制服务

```bash
$ADB -s $DEVICE logcat -d | grep -iE "HttpCommandServer|CommandHandler|LocalWebSocketServer|MediaLiveService|route|dispatch|handler" > /tmp/replica_m06_control.log

$ADB -s $DEVICE shell netstat -tlnp 2>/dev/null | grep "$REPLICA_PKG" > /tmp/replica_m06_ports.txt
$ADB -s $DEVICE shell ss -tlnp 2>/dev/null | grep "$REPLICA_PKG" >> /tmp/replica_m06_ports.txt
```

### STEP-9.3: 远程控制差异分析

```
对比维度:
1. HTTP Server: 监听端口是否一致
2. WebSocket Server: 是否启动
3. 路由数量: 支持的命令数
4. 命令响应: 同一命令的返回格式
5. 屏幕投射: MediaProjection 是否可用
```

---

## 阶段十: 全局深度对比

### STEP-10.1: 全量 Crash 对比

```bash
# 运行 5 分钟后采集
$ADB -s $DEVICE logcat -d | grep "FATAL EXCEPTION" > /tmp/all_crashes.log
$ADB -s $DEVICE logcat -d | grep -c "FATAL EXCEPTION"
```

### STEP-10.2: ANR 对比

```bash
$ADB -s $DEVICE shell ls /data/anr/ 2>/dev/null
$ADB -s $DEVICE logcat -d | grep "ANR in" > /tmp/all_anr.log
```

### STEP-10.3: 电池消耗对比

```bash
$ADB -s $DEVICE shell dumpsys batterystats | grep -A10 "$VENDOR_PKG" > /tmp/vendor_battery.txt
$ADB -s $DEVICE shell dumpsys batterystats | grep -A10 "$REPLICA_PKG" > /tmp/replica_battery.txt
```

### STEP-10.4: 内存占用对比

```bash
$ADB -s $DEVICE shell dumpsys meminfo $VENDOR_PKG > /tmp/vendor_meminfo.txt
$ADB -s $DEVICE shell dumpsys meminfo $REPLICA_PKG > /tmp/replica_meminfo.txt
```

### STEP-10.5: 线程数对比

```bash
$ADB -s $DEVICE shell ls /proc/$(adb -s $DEVICE shell pidof $VENDOR_PKG)/task 2>/dev/null | wc -l
$ADB -s $DEVICE shell ls /proc/$(adb -s $DEVICE shell pidof $REPLICA_PKG)/task 2>/dev/null | wc -l
```

---

## 输出: 差异报告模板

执行完所有阶段后，Claude Code 应生成如下格式的报告:

```markdown
# Vendor vs Replica 深度对比报告

日期: YYYY-MM-DD
设备: {manufacturer} {model} / Android {version}
Vendor: org.ldtape.qqlhl v2.0
Replica: com.vendor.rat v1.0.0

## 总览

| 维度 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 权限数 | 118 | 27 | ❌ 缺 91 个 |
| 组件数 | X | Y | ... |
| 启动耗时 | Xms | Yms | ... |
| 内存占用 | X MB | Y MB | ... |
| 线程数 | X | Y | ... |
| Crash 数 | X | Y | ... |

## 逐模块对比

### MODULE_08 启动流程
| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| 进程启动 | ✅ PID=xxx | ✅ PID=xxx | ✅ 一致 |
| Config 加载 | ✅ | ✅/❌ | ... |
| ... | ... | ... | ... |

### MODULE_01 网络通信
(同上格式)

### MODULE_02 权限绕过
(同上格式)

... (每个模块)

## 关键差异清单

| # | 模块 | 差异描述 | 严重度 | 修复建议 |
|---|------|---------|--------|---------|
| 1 | 权限 | Replica 缺少 91 个权限 | HIGH | 补齐 AndroidManifest.xml |
| 2 | ... | ... | ... | ... |

## 已知权限差异 (Vendor 有, Replica 缺)

(列出 91 个缺失权限及其用途)

## 结论与下一步
```

---

## Claude Code 执行指令

当用户说 **"执行深度对比"** 或 **"run deep comparison"** 时，Claude Code 应:

1. 读取本文档
2. 按阶段一到阶段十顺序执行所有 ADB 命令
3. 每个阶段完成后输出中间结果
4. 全部完成后生成差异报告 (`docs/vendor-replication/COMPARISON_REPORT.md`)
5. 标记关键差异并给出修复建议

### 执行顺序

```
阶段一: 安装对比 (权限 + 组件)
  ↓
阶段二: MODULE_08 启动流程
  ↓
阶段三: MODULE_01 网络通信
  ↓
阶段四: MODULE_02 权限绕过
  ↓
阶段五: MODULE_04 UI 自动化
  ↓
阶段六: MODULE_03 厂商适配
  ↓
阶段七: MODULE_05 数据收集
  ↓
阶段八: MODULE_07 保活机制
  ↓
阶段九: MODULE_06 远程控制
  ↓
阶段十: 全局深度对比 → 生成报告
```

### 失败处理

- 任何阶段出现 FATAL EXCEPTION: 记录但继续执行后续阶段
- ADB 连接断开: 重新 `adb connect` 后重试当前阶段
- 某个 APK 未安装: 跳过该 APK 的对比，标记为 SKIP
- 命令超时: 记录超时，继续下一步
