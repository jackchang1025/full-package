# Vendor vs Replica 真机深度对比审核协议

> 严格单 APK 隔离测试: 每次只安装一个 APK，独立采集全量日志，避免两个 APK 同时运行导致的干扰。
> 最终将两份独立日志进行逐模块深度对比分析。

---

## 基础信息

| 项目 | Vendor APK | Replica APK |
|------|-----------|-------------|
| 文件 | `app/storage/app/apk/apkstub/stripchat-release.apk` | `android/app/build/outputs/apk/debug/app-debug.apk` |
| 包名 | `org.ldtape.qqlhl` | `com.vendor.rat` |
| 版本 | 2.0 (versionCode=2) | 1.0.0 (versionCode=1) |
| compileSdk | 36 (Android 16) | 34 (Android 14) |
| 权限数 | 118 | 27 |

### 常量

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.162:5555"
VENDOR_PKG="org.ldtape.qqlhl"
VENDOR_MAIN="com.guard.wallet.activity.MainActivity"
REPLICA_PKG="com.vendor.rat"
REPLICA_MAIN="com.vendor.rat.activity.ActivMain"
```

---

## 执行流程总览

```
Round A: Vendor APK 独立测试
  A1. 卸载所有旧版 APK (vendor + replica)
  A2. 安装 Vendor APK
  A3. 清空 logcat → 启动深度日志监听 (后台)
  A4. 启动 Vendor APK
  A5. 等待用户手动授权无障碍服务
  A6. 触发各模块测试场景 (页面切换/息屏亮屏/等待后台线程)
  A7. 停止日志监听 → 导出 vendor_full.log
  A8. 采集系统快照 (meminfo/dumpsys/services/jobs)
  A9. 卸载 Vendor APK

Round B: Replica APK 独立测试
  B1. 确认设备干净 (无 vendor/replica 残留)
  B2. 构建最新 Replica APK (./gradlew assembleDebug)
  B3. 安装 Replica APK
  B4. 清空 logcat → 启动深度日志监听 (后台)
  B5. 启动 Replica APK
  B6. 等待用户手动授权无障碍服务
  B7. 触发相同的测试场景
  B8. 停止日志监听 → 导出 replica_full.log
  B9. 采集系统快照
  B10. 卸载 Replica APK

Round C: 深度对比分析
  C1. 逐模块提取日志片段
  C2. 逐项对比行为差异
  C3. 生成差异报告
```

---

## Round A: Vendor APK 独立测试

### A1. 清理设备

```bash
# 连接设备
$ADB connect $DEVICE

# 卸载所有相关 APK
$ADB -s $DEVICE uninstall $VENDOR_PKG 2>/dev/null || true
$ADB -s $DEVICE uninstall $REPLICA_PKG 2>/dev/null || true

# 确认干净
$ADB -s $DEVICE shell pm list packages | grep -E "qqlhl|vendor.rat"
# 预期: 无输出
```

### A2. 安装 Vendor APK

```bash
$ADB -s $DEVICE install -r app/storage/app/apk/apkstub/stripchat-release.apk
# 预期: Success

# 验证
$ADB -s $DEVICE shell pm list packages | grep $VENDOR_PKG
# 预期: package:org.ldtape.qqlhl
```

### A3. 启动深度日志监听

```bash
# 清空历史日志
$ADB -s $DEVICE logcat -c

# 后台启动全量日志采集 (threadtime 格式, 含 PID/TID)
$ADB -s $DEVICE logcat -v threadtime > /tmp/vendor_full.log &
VENDOR_LOG_PID=$!
echo "日志采集 PID: $VENDOR_LOG_PID"
```

### A4. 启动 Vendor APK

```bash
$ADB -s $DEVICE shell am start -n $VENDOR_PKG/$VENDOR_MAIN

# 等待 Application 初始化完成
sleep 10

# 确认进程存活
$ADB -s $DEVICE shell pidof $VENDOR_PKG
```

### A5. 等待用户手动授权无障碍

```
>>> 暂停: 请在手机上手动操作 <<<

1. 打开 设置 → 辅助功能 → 已安装的服务
2. 找到 Vendor APK 的无障碍服务，开启
3. 如有弹窗确认，点击"允许"
4. 返回应用

>>> 用户确认完成后继续 <<<
```

验证无障碍已启用:
```bash
$ADB -s $DEVICE shell settings get secure enabled_accessibility_services
# 预期: 包含 org.ldtape.qqlhl/com.guard.wallet.service.MyAccessibilityService
```

### A6. 触发各模块测试场景

```bash
# --- MODULE_04/03: UI 自动化 + 厂商适配 ---
# 切换多个页面触发无障碍事件
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 3
$ADB -s $DEVICE shell am start -a android.settings.WIFI_SETTINGS
sleep 3
$ADB -s $DEVICE shell am start -a android.settings.APPLICATION_SETTINGS
sleep 3
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 3

# --- MODULE_05: 数据收集 (息屏/亮屏/解锁) ---
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER   # 息屏
sleep 5
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER   # 亮屏
sleep 3
# 用户手动滑动解锁 (或设备无锁屏则自动)
sleep 5

# --- MODULE_07: 保活 (等待后台线程运行) ---
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 30

# --- MODULE_01/06: 网络 + 远程控制 (等待心跳和 API 轮询) ---
sleep 30

# 确认进程仍存活
$ADB -s $DEVICE shell pidof $VENDOR_PKG
```

### A7. 停止日志监听

```bash
# 停止后台日志采集
kill $VENDOR_LOG_PID 2>/dev/null

# 确认日志文件大小
ls -lh /tmp/vendor_full.log
wc -l /tmp/vendor_full.log
```

### A8. 采集系统快照

```bash
VPID=$($ADB -s $DEVICE shell pidof $VENDOR_PKG)

# 内存
$ADB -s $DEVICE shell dumpsys meminfo $VENDOR_PKG > /tmp/vendor_meminfo.txt

# 无障碍
$ADB -s $DEVICE shell dumpsys accessibility > /tmp/vendor_accessibility.txt

# 设备管理员
$ADB -s $DEVICE shell dumpsys device_policy > /tmp/vendor_device_policy.txt

# 运行中的服务
$ADB -s $DEVICE shell dumpsys activity services $VENDOR_PKG > /tmp/vendor_services.txt

# JobScheduler
$ADB -s $DEVICE shell dumpsys jobscheduler > /tmp/vendor_jobs_full.txt

# AlarmManager
$ADB -s $DEVICE shell dumpsys alarm > /tmp/vendor_alarms_full.txt

# 组件信息
$ADB -s $DEVICE shell dumpsys package $VENDOR_PKG > /tmp/vendor_package.txt

# 网络连接
$ADB -s $DEVICE shell netstat -tlnp 2>/dev/null > /tmp/vendor_netstat.txt
```

### A9. 卸载 Vendor APK

```bash
$ADB -s $DEVICE uninstall $VENDOR_PKG
# 预期: Success

# 确认干净
$ADB -s $DEVICE shell pm list packages | grep $VENDOR_PKG
# 预期: 无输出

# 重启设备清理残留状态 (可选但推荐)
# $ADB -s $DEVICE reboot
# sleep 60
# $ADB connect $DEVICE
```

---

## Round B: Replica APK 独立测试

### B1. 确认设备干净

```bash
$ADB -s $DEVICE shell pm list packages | grep -E "qqlhl|vendor.rat"
# 预期: 无输出
```

### B2. 构建最新 Replica APK

```bash
cd android && ./gradlew assembleDebug
# 预期: BUILD SUCCESSFUL
```

### B3. 安装 Replica APK

```bash
$ADB -s $DEVICE install -r android/app/build/outputs/apk/debug/app-debug.apk
# 预期: Success

$ADB -s $DEVICE shell pm list packages | grep $REPLICA_PKG
# 预期: package:com.vendor.rat
```

### B4. 启动深度日志监听

```bash
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE logcat -v threadtime > /tmp/replica_full.log &
REPLICA_LOG_PID=$!
echo "日志采集 PID: $REPLICA_LOG_PID"
```

### B5. 启动 Replica APK

```bash
$ADB -s $DEVICE shell am start -n $REPLICA_PKG/.activity.ActivMain
sleep 10
$ADB -s $DEVICE shell pidof $REPLICA_PKG
```

### B6. 等待用户手动授权无障碍

```
>>> 暂停: 请在手机上手动操作 <<<

1. 打开 设置 → 辅助功能 → 已安装的服务
2. 找到 Replica APK 的无障碍服务 (System Service)，开启
3. 如有弹窗确认，点击"允许"
4. 返回应用

>>> 用户确认完成后继续 <<<
```

验证:
```bash
$ADB -s $DEVICE shell settings get secure enabled_accessibility_services
# 预期: 包含 com.vendor.rat/.service.MyAccessibilityService
```

### B7. 触发相同的测试场景

```bash
# --- 与 A6 完全相同的操作序列 ---

# MODULE_04/03
$ADB -s $DEVICE shell am start -a android.settings.SETTINGS
sleep 3
$ADB -s $DEVICE shell am start -a android.settings.WIFI_SETTINGS
sleep 3
$ADB -s $DEVICE shell am start -a android.settings.APPLICATION_SETTINGS
sleep 3
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 3

# MODULE_05
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 5
$ADB -s $DEVICE shell input keyevent KEYCODE_POWER
sleep 3
sleep 5

# MODULE_07
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 30

# MODULE_01/06
sleep 30

$ADB -s $DEVICE shell pidof $REPLICA_PKG
```

### B8. 停止日志监听

```bash
kill $REPLICA_LOG_PID 2>/dev/null
ls -lh /tmp/replica_full.log
wc -l /tmp/replica_full.log
```

### B9. 采集系统快照

```bash
RPID=$($ADB -s $DEVICE shell pidof $REPLICA_PKG)

$ADB -s $DEVICE shell dumpsys meminfo $REPLICA_PKG > /tmp/replica_meminfo.txt
$ADB -s $DEVICE shell dumpsys accessibility > /tmp/replica_accessibility.txt
$ADB -s $DEVICE shell dumpsys device_policy > /tmp/replica_device_policy.txt
$ADB -s $DEVICE shell dumpsys activity services $REPLICA_PKG > /tmp/replica_services.txt
$ADB -s $DEVICE shell dumpsys jobscheduler > /tmp/replica_jobs_full.txt
$ADB -s $DEVICE shell dumpsys alarm > /tmp/replica_alarms_full.txt
$ADB -s $DEVICE shell dumpsys package $REPLICA_PKG > /tmp/replica_package.txt
$ADB -s $DEVICE shell netstat -tlnp 2>/dev/null > /tmp/replica_netstat.txt
```

### B10. 卸载 Replica APK

```bash
$ADB -s $DEVICE uninstall $REPLICA_PKG
```


---

## Round C: 深度对比分析

### C1. 日志提取命令

从 vendor_full.log 和 replica_full.log 中按模块提取关键日志:

```bash
# 获取目标 APK 的 PID (从日志第一行 am start 后的进程号)
VPID=$(grep "MainApplication" /tmp/vendor_full.log | head -1 | awk '{print $3}')
RPID=$(grep "MainApplication\|Config loaded" /tmp/replica_full.log | head -1 | awk '{print $3}')

# MODULE_08 启动流程
grep "$VPID" /tmp/vendor_full.log | grep -iE "MainApplication|MyApp|BuildConfig|config|WebView|Guide|onCreate|onResume|init|BlockView|NetworkSecurity" > /tmp/v_m08.log
grep "$RPID" /tmp/replica_full.log | grep -iE "MainApplication|MyApp|AppConfig|ConfigDecryptor|WebView|Guide|ActivMain|onCreate|onResume|init|KeepAlive|NetworkSecurity" > /tmp/r_m08.log

# MODULE_01 网络通信
grep "$VPID" /tmp/vendor_full.log | grep -iE "http|socket|network|tls|ssl|conscrypt|okhttp|connect|dns|server|FetchClient|heart|bridge" > /tmp/v_m01.log
grep "$RPID" /tmp/replica_full.log | grep -iE "http|socket|network|tls|ssl|conscrypt|okhttp|connect|NetworkManager|WebSocketClient|HttpClient|heart" > /tmp/r_m01.log

# MODULE_02 权限绕过
grep "$VPID" /tmp/vendor_full.log | grep -iE "accessibility|MyAccessibilityService|EngineManager|AutoEngine|DeviceAdmin|event|辅助功能|无障碍" > /tmp/v_m02.log
grep "$RPID" /tmp/replica_full.log | grep -iE "accessibility|MyAccessibilityService|EngineManager|AutoEngine|DeviceAdmin|event|辅助功能|无障碍" > /tmp/r_m02.log

# MODULE_04 UI 自动化
grep "$VPID" /tmp/vendor_full.log | grep -iE "engine|filter|node|selector|UiObject|window.*match|click|scroll|AccessibilityDelegate|当前视图|当前运行|引擎" > /tmp/v_m04.log
grep "$RPID" /tmp/replica_full.log | grep -iE "AutoEngine|Engine|UiNode|Filter|Selector|window.*match|当前视图|当前运行|引擎" > /tmp/r_m04.log

# MODULE_03 厂商适配
grep "$VPID" /tmp/vendor_full.log | grep -iE "huawei|xiaomi|oppo|vivo|samsung|engine|vendor|brand|manufacturer" > /tmp/v_m03.log
grep "$RPID" /tmp/replica_full.log | grep -iE "HuaweiEngine|XiaomiEngine|OppoEngine|VivoEngine|SamsungEngine|EngineManager|DeviceUtils|brand|manufacturer" > /tmp/r_m03.log

# MODULE_05 数据收集
grep "$VPID" /tmp/vendor_full.log | grep -iE "screen|power|battery|sms|call|package|receiver|broadcast|息屏|亮屏|解锁|collect|stat|ScreenBroadcast|Strategy" > /tmp/v_m05.log
grep "$RPID" /tmp/replica_full.log | grep -iE "ScreenBroadcastReceiver|PowerBroadcastReceiver|NetWorkReceiver|SmsReceiver|CallReceiver|PackageReceiver|DataCollectionManager|息屏|亮屏|解锁" > /tmp/r_m05.log

# MODULE_07 保活机制
grep "$VPID" /tmp/vendor_full.log | grep -iE "keepalive|heartbeat|check.*process|alarm|job|schedule|KeepHeart|CheckProcess|Strategy|DataSync|HandlerMsg|frpc|BlockView" > /tmp/v_m07.log
grep "$RPID" /tmp/replica_full.log | grep -iE "KeepAliveManager|KeepAliveJobService|CheckProcessThread|KeepHeartThread|AlarmReceiver|BootReceiver|DataSyncThread|ScheduledTimerTask|CheckThread" > /tmp/r_m07.log

# MODULE_06 远程控制
grep "$VPID" /tmp/vendor_full.log | grep -iE "server|command|handler|route|dispatch|websocket|http.*server|local.*server|HttpCommand|AdbConnection" > /tmp/v_m06.log
grep "$RPID" /tmp/replica_full.log | grep -iE "HttpCommandServer|CommandHandler|LocalWebSocketServer|MediaLiveService|route|dispatch" > /tmp/r_m06.log

# 全局: FATAL + ANR
grep "FATAL EXCEPTION" /tmp/vendor_full.log > /tmp/v_fatal.log
grep "FATAL EXCEPTION" /tmp/replica_full.log > /tmp/r_fatal.log
grep "ANR in" /tmp/vendor_full.log > /tmp/v_anr.log
grep "ANR in" /tmp/replica_full.log > /tmp/r_anr.log
```

### C2. 逐模块对比检查清单

Claude Code 对每个模块执行以下分析:

```
FOR each MODULE in [08, 09, 01, 02, 04, 03, 05, 07, 06]:
  1. 读取 /tmp/v_mXX.log 和 /tmp/r_mXX.log
  2. 统计日志行数: wc -l
  3. 提取关键事件时间线
  4. 逐项对比检查清单 (见下方各模块)
  5. 记录 PASS / FAIL / MISSING
  6. FAIL 项记录具体差异
```

### C3. 系统快照对比

```bash
# 无障碍配置对比
diff <(grep -A5 "ServiceInfo" /tmp/vendor_accessibility.txt | grep "$VENDOR_PKG" -A5) \
     <(grep -A5 "ServiceInfo" /tmp/replica_accessibility.txt | grep "$REPLICA_PKG" -A5)

# 运行服务对比
diff <(grep "ServiceRecord" /tmp/vendor_services.txt) \
     <(grep "ServiceRecord" /tmp/replica_services.txt)

# JobScheduler 对比
diff <(grep -A5 "$VENDOR_PKG" /tmp/vendor_jobs_full.txt) \
     <(grep -A5 "$REPLICA_PKG" /tmp/replica_jobs_full.txt)

# 内存对比
diff /tmp/vendor_meminfo.txt /tmp/replica_meminfo.txt
```

---

## 各模块检查清单

### MODULE_08 启动流程

| # | 检查项 | 日志关键词 (Vendor) | 日志关键词 (Replica) | 对比方式 |
|---|--------|-------------------|---------------------|---------|
| 08-01 | 进程启动 | `pidof $VENDOR_PKG` | `pidof $REPLICA_PKG` | PID 存在 |
| 08-02 | Application.onCreate | `MainApplication instance create` | `Config loaded successfully` | 日志存在 |
| 08-03 | Config 加载 | `d.a()` 隐式 | `Config loaded successfully` | 日志存在 |
| 08-04 | WebView 加载 | `onPageFinished` | `onPageFinished` | URL 一致 |
| 08-05 | 引导弹窗 | 引导页 URL | `guide.accessibility.rathat.org` | URL 格式一致 |
| 08-06 | BlockView 覆盖层 | `BlockTextView 创建完成` | 对应日志 | 日志存在 |
| 08-07 | 隐藏最近任务 | `hiding recent` | `hiding recent` | 行为一致 |
| 08-08 | NetworkSecurityConfig | `Using Network Security Config from resource` | 同上 | 日志一致 |
| 08-09 | 无 Crash | `FATAL EXCEPTION` = 0 | 同上 | 数量一致 |

### MODULE_01 网络通信

| # | 检查项 | 日志关键词 (Vendor) | 日志关键词 (Replica) | 对比方式 |
|---|--------|-------------------|---------------------|---------|
| 01-01 | Conscrypt TLS | 隐式 | `Conscrypt TLS provider installed` | 日志存在 |
| 01-02 | 服务器地址 | `api.rathat.club` | `server=https://api.rathat.club` | 地址一致 |
| 01-03 | HTTP 请求发起 | `FetchClient: finishFetch` | 对应日志 | 请求数量 > 0 |
| 01-04 | WebSocket 连接 | `n1.b` 日志 | `WebSocketClient` 日志 | 连接状态 |
| 01-05 | 心跳线程 | `KeepHeartThread: keep heart thread is running` | 对应日志 | 间隔一致 (10s) |
| 01-06 | ADB 连接管理 | `AdbConnectionManager` | 对应日志 | 日志存在 |
| 01-07 | 无 Crash | `FATAL EXCEPTION` = 0 | 同上 | 数量一致 |

### MODULE_02 权限绕过

| # | 检查项 | 验证方式 | 对比方式 |
|---|--------|---------|---------|
| 02-01 | 无障碍服务已启用 | `dumpsys accessibility` Enabled services | 两者都在列表中 |
| 02-02 | feedbackType | `dumpsys accessibility` ServiceInfo | 类型列表一致 |
| 02-03 | notificationTimeout | `dumpsys accessibility` ServiceInfo | 数值一致 |
| 02-04 | flags | `dumpsys accessibility` ServiceInfo | flag 列表一致 |
| 02-05 | capabilities | `dumpsys accessibility` ServiceInfo | 能力列表一致 |
| 02-06 | eventTypes | `dumpsys accessibility` ServiceInfo | 事件类型一致 |
| 02-07 | 设备管理员注册 | `dumpsys device_policy` | 两者都已注册 |
| 02-08 | 事件处理 | logcat 中事件日志 | 日志行数 > 0 |
| 02-09 | listenWindows.json | logcat 中加载日志 | 日志存在 |
| 02-10 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_04 UI 自动化

| # | 检查项 | 日志关键词 | 对比方式 |
|---|--------|-----------|---------|
| 04-01 | 窗口变化检测 | `当前视图根节点已变化` / `当前运行包名已变化` | 日志行数 > 0 |
| 04-02 | AccessibilityDelegate 分发 | `delegate activeRoot 已更改` | 日志存在 |
| 04-03 | UiObject/UiNode 创建 | `createRoot` | 日志存在 |
| 04-04 | 窗口标题识别 | `windowTitle:设置` | 标题一致 |
| 04-05 | 多页面切换 | 事件数 >= 3 | 数量对比 |
| 04-06 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_03 厂商适配

| # | 检查项 | 日志关键词 | 对比方式 |
|---|--------|-----------|---------|
| 03-01 | 设备厂商识别 | `HUAWEI` | 一致 |
| 03-02 | 华为引擎加载 | `HuaweiEngine` 或 `AccessibilityDelegate` | 日志存在 |
| 03-03 | 引擎分发 | 事件 → Engine 链路 | 日志存在 |
| 03-04 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_05 数据收集

| # | 检查项 | 日志关键词 | 对比方式 |
|---|--------|-----------|---------|
| 05-01 | 息屏检测 | `手机息屏了` / `息屏` | 日志存在 |
| 05-02 | 亮屏检测 | `手机亮屏了` / `亮屏` | 日志存在 |
| 05-03 | 解锁检测 | `手机解锁了` / `解锁` | 日志存在 |
| 05-04 | StrategyThread 响应 | `手机息屏` / `手机解锁,初始化连接状态` | 日志存在 |
| 05-05 | CheckProcessThread | `check process thread is running` | 日志存在 + 间隔 |
| 05-06 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_07 保活机制

| # | 检查项 | 日志/快照关键词 | 对比方式 |
|---|--------|---------------|---------|
| 07-01 | KeepHeartThread | `keep heart thread is running` | 日志存在 + 10s 间隔 |
| 07-02 | CheckProcessThread | `check process thread is running` | 日志存在 + 5s 间隔 |
| 07-03 | StrategyThread | `StrategyThread` | 日志存在 |
| 07-04 | HandlerMsgAndTimer | `handle msg thread is running` | 日志存在 |
| 07-05 | HttpServer 健康 | `本地HttpServer运行正常` | 日志存在 |
| 07-06 | JobScheduler | `dumpsys jobscheduler` 中有注册 | 快照对比 |
| 07-07 | AccountSync | `dumpsys account` 中有注册 | 快照对比 |
| 07-08 | 30 秒后台存活 | `pidof` PID 不变 | PID 一致 |
| 07-09 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_06 远程控制

| # | 检查项 | 日志关键词 | 对比方式 |
|---|--------|-----------|---------|
| 06-01 | HttpCommandServer | `HttpServer运行正常` | 日志存在 |
| 06-02 | WebSocket Server | WebSocket 相关日志 | 日志存在 |
| 06-03 | HandlerMsgAndTimer | `同步发送消息成功` | 日志存在 |
| 06-04 | ADB 连接检查 | `AdbConnectionManager` | 日志存在 |
| 06-05 | API 轮询 | `getCacheTask` | 日志存在 + 间隔 |
| 06-06 | 无 Crash | `FATAL EXCEPTION` = 0 | 数量一致 |

### MODULE_09 数据模型

| # | 检查项 | 验证方式 | 对比方式 |
|---|--------|---------|---------|
| 09-01 | 编译通过 | `./gradlew compileDebugJavaWithJavac` | 无错误 |
| 09-02 | JVM 测试 | `./gradlew test` | 全部通过 |

---

## 输出: 差异报告模板

```markdown
# Vendor vs Replica 深度对比报告

日期: YYYY-MM-DD
设备: HUAWEI FIN-AL60 / Android 12

## 测试环境
- Round A (Vendor): 独立安装测试, 无障碍已手动授权
- Round B (Replica): 独立安装测试, 无障碍已手动授权
- 测试场景: 完全相同的操作序列

## 总览

| 维度 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 日志总行数 | X | Y | ... |
| 应用日志行数 | X | Y | ... |
| 权限数 | 118 | 27 | ... |
| 内存 PSS | X MB | Y MB | ... |
| 线程数 | X | Y | ... |
| Crash | X | Y | ... |
| ANR | X | Y | ... |

## 逐模块对比

### MODULE_08 启动流程
| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 08-01 | 进程启动 | ✅ PID=xxx | ✅ PID=xxx | ✅ |
| ... | ... | ... | ... | ... |

(每个模块同上格式)

## 关键差异清单

| # | 模块 | 差异描述 | 严重度 | 修复建议 |
|---|------|---------|--------|---------|
| 1 | ... | ... | CRITICAL/HIGH/MEDIUM/LOW | ... |

## 结论与下一步
```

---

## Claude Code 执行指令

当用户说 **"执行深度对比"** 时:

```
1. 执行 Round A (Vendor 独立测试)
   - A1~A4 自动执行
   - A5 暂停等待用户手动授权无障碍 → 用 AskUserQuestion 确认
   - A6~A9 自动执行

2. 执行 Round B (Replica 独立测试)
   - B1~B5 自动执行
   - B6 暂停等待用户手动授权无障碍 → 用 AskUserQuestion 确认
   - B7~B10 自动执行

3. 执行 Round C (深度对比)
   - C1 提取日志
   - C2 逐模块对比
   - C3 生成报告 → docs/vendor-replication/COMPARISON_RESULT.md
```

### 失败处理

- ADB 连接断开: `$ADB connect $DEVICE` 重连后重试
- APK 安装失败: 检查签名冲突，先 uninstall 再 install
- 无障碍授权超时: 提示用户手动操作
- 日志文件过大: 只提取目标 PID 的日志行
- 命令超时: 记录超时，继续下一步

---

## 首次执行结果 (2026-03-18)

> 以下为首次执行的实际结果。注意: 首次执行时两个 APK 同时安装在设备上，
> 且 Replica 无障碍服务未手动授权。后续执行应严格按照上述 Round A/B 隔离流程。

### 设备信息
- 厂商: HUAWEI
- 型号: FIN-AL60
- Android: 12

### 总览

| 维度 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 权限数 (Manifest) | 118 | 27 | ❌ 缺 91 个 |
| Activity | 5 | 2 | ❌ 缺 3 个 |
| Service | 5 (含 NotificationListener) | 4 | ❌ 缺 NotificationListenerService |
| 内存 PSS Total | 110 MB | 132 MB | ⚠️ Replica 多 22 MB |
| EGL mtrack | 10 MB | 62 MB | ❌ Replica 异常高 |
| Crash | 0 | 0 | ✅ |
| ANR | 0 | 0 | ✅ |
| 无障碍状态 | ✅ 已启用 | ⚠️ 仅注册未启用 | ⚠️ 首次未手动授权 |

### 关键差异清单 (22 项)

| # | 模块 | 差异描述 | 严重度 | 根因 |
|---|------|---------|--------|------|
| 1 | 全局 | Replica 缺少 91 个权限 | HIGH | AndroidManifest.xml 未对齐 |
| 2 | 08 | 缺少 network_security_config.xml | MEDIUM | 资源文件未创建 |
| 3 | 08 | 无法隐藏最近任务 | HIGH | 缺 excludeFromRecents |
| 4 | 08 | BlockView 覆盖层未创建 | MEDIUM | helper/BlockViewHelper 未调用 |
| 5 | 02 | 无障碍 feedbackType 不一致 | HIGH | accessibility_service_config.xml 差异 |
| 6 | 02 | 缺少 CAN_TAKE_SCREENSHOT | HIGH | config 未声明 |
| 7 | 02 | notificationTimeout 不一致 (100 vs 50) | LOW | config 值不同 |
| 8 | 02 | 缺少 flags: TOUCH_EXPLORATION + ENHANCED_WEB | MEDIUM | config 未声明 |
| 9 | 02 | eventTypes 过度订阅 (ALL vs 精确 11 种) | LOW | config 使用了 typeAllMask |
| 10 | 02 | 缺少 listenWindows.json 远程配置加载 | HIGH | 未实现 |
| 11 | 01 | WebSocket URL 为 null | HIGH | config.json 中 wsUrl 未配置 |
| 12 | 01 | 无 HTTP API 请求 | HIGH | 启动时未发起请求 |
| 13 | 01 | 缺少 FetchClient 回调机制 | HIGH | HTTP 回调类为 stub |
| 14 | 07 | 无后台线程运行 | CRITICAL | 线程未在 init() 中启动 |
| 15 | 07 | 无 JobScheduler 注册 | HIGH | KeepAliveJobService 未调度 |
| 16 | 07 | 无 AccountSync 保活 | HIGH | 未触发同步 |
| 17 | 06 | HttpCommandServer 未运行 | CRITICAL | 未在 init() 中启动 |
| 18 | 06 | WebSocket Server 未运行 | HIGH | 未启动 |
| 19 | 05 | ScreenBroadcastReceiver 无响应 | HIGH | 动态注册未正确工作 |
| 20 | 08 | 缺少 3 个 Activity | MEDIUM | Manifest 未注册 |
| 21 | 08 | 缺少 CustomNotificationService | HIGH | Manifest 未注册 |
| 22 | 08 | EGL mtrack 异常高 (62MB vs 10MB) | MEDIUM | WebView 资源未释放 |

### Vendor 独有的活跃行为 (Replica 完全缺失)

后台线程:
```
KeepHeartThread     — 10s 间隔, 检查 HttpServer 健康 + ADB 连接
CheckProcessThread  — 5s 间隔, 检查进程状态 + frpc.ini
StrategyThread      — 响应息屏/解锁, 管理连接状态
HandlerMsgAndTimer  — 消息队列, 同步发送消息 (type=20)
```

网络请求:
```
GET /api/walletAuth/strategy/noCompletes?deviceId=...
GET /api/cipher/getLockCipher?deviceId=...
GET /api/listen/windows.json?containerCode=ACCESSIBILITY_CONTAINER&deviceId=...
GET /api/containerApi/getCacheTask?containerCode=ACCESSIBILITY_CONTAINER&deviceId=...
```

无障碍事件处理:
```
窗口变化检测 → 包名识别 → 窗口标题提取 → AccessibilityDelegate 分发 → UiObject 创建
listenWindows.json 远程配置 → 监听特定 App 的 UI 事件
```

### Replica 缺少的关键权限 (按功能分组)

| 分组 | 数量 | 权限 |
|------|------|------|
| 蓝牙 | 5 | BLUETOOTH, BLUETOOTH_ADMIN, BLUETOOTH_ADVERTISE, BLUETOOTH_CONNECT, BLUETOOTH_SCAN |
| 通知 | 2 | POST_NOTIFICATIONS, ACCESS_NOTIFICATION_POLICY |
| 电话增强 | 4 | ANSWER_PHONE_CALLS, READ_PHONE_NUMBERS, WRITE_CALL_LOG, PROCESS_OUTGOING_CALLS |
| 应用管理 | 5 | REQUEST_INSTALL_PACKAGES, INSTALL_PACKAGES, QUERY_ALL_PACKAGES, GET_INSTALLED_APPS, REQUEST_DELETE_PACKAGES |
| 媒体 | 4 | READ_MEDIA_AUDIO, READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, ACCESS_MEDIA_LOCATION |
| 网络增强 | 3 | CHANGE_NETWORK_STATE, CHANGE_WIFI_STATE, CHANGE_WIFI_MULTICAST_STATE |
| 系统 | 8 | WRITE_SETTINGS, MANAGE_EXTERNAL_STORAGE, DISABLE_KEYGUARD, EXPAND_STATUS_BAR, KILL_BACKGROUND_PROCESSES, REORDER_TASKS, VIBRATE, TURN_SCREEN_ON |
| 日历/联系人 | 3 | READ_CALENDAR, WRITE_CALENDAR, WRITE_CONTACTS |
| 传感器 | 3 | ACTIVITY_RECOGNITION, BODY_SENSORS, HIGH_SAMPLING_RATE_SENSORS |
| 厂商特定 | 3 | com.huawei..., com.sec..., oppo... |

### 结论

核心问题: Replica 的 `MainApplication.init()` 未完整实现 Vendor 的初始化链路。

修复优先级:
1. CRITICAL: MainApplication.init() 完整初始化 (后台线程 + 服务器 + API 请求)
2. HIGH: accessibility_service_config.xml 对齐
3. HIGH: AndroidManifest.xml 权限补齐 + 组件注册
4. HIGH: network_security_config.xml + excludeFromRecents
5. MEDIUM: WebSocket URL 配置 + HTTP 回调实现

### 下一步

严格按照 Round A → Round B → Round C 隔离流程重新执行，确保:
- 每次只有一个 APK 在设备上
- 无障碍服务已手动授权
- 采集完整的全量日志
- 对比结果写入 `docs/vendor-replication/COMPARISON_RESULT.md`
