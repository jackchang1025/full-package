# 全模块真机功能测试用例

> 按 V3 协议 Phase 2 输出，覆盖 MODULE_01~09 全部可观测行为。
> ADB: /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
> DEVICE: 192.168.31.162:5555
> PACKAGE: com.vendor.rat

---

## MODULE_08 启动流程

### TEST-08-01: 应用启动
操作: `adb shell am start -n com.vendor.rat/.activity.ActivMain`
验证: `adb shell pidof com.vendor.rat` 返回 PID

### TEST-08-02: Config 解密加载
验证: `logcat -s MainApplication | grep "Config loaded successfully"`

### TEST-08-03: WebView 加载
验证: `logcat -s AppWebViewClient | grep "onPageFinished"`

### TEST-08-04: 引导弹窗中文
前置: 无障碍未开启
验证: 弹窗显示中文标题 "开启 [无障碍服务]"

### TEST-08-05: 无通知弹窗
验证: `logcat | grep "blockNotificationByPermission.*com.vendor.rat"` 为空

### TEST-08-06: 双击退出
操作: 按两次返回键
验证: 第一次显示 Toast "再按一次退出"，第二次退出

### TEST-08-07: 无 Crash
验证: `logcat | grep "FATAL EXCEPTION" | grep "com.vendor.rat"` 为空

---

## MODULE_09 数据模型

### TEST-09-01: ApiResult 序列化
验证: 编译通过 + JVM 测试通过 (`./gradlew test`)

### TEST-09-02: req/ 55 个文件编译
验证: `./gradlew compileDebugJavaWithJavac` 无错误

### TEST-09-03: resp/ 42 个文件编译
验证: 同上

---

## MODULE_01 网络通信

### TEST-01-01: Conscrypt TLS 初始化
验证: `logcat -s NetworkManager | grep "Conscrypt TLS provider installed"`

### TEST-01-02: 服务器地址解密
验证: `logcat -s NetworkManager | grep "server=https://api.rathat.club"`

### TEST-01-03: WebSocket null 保护
验证: `logcat -s WebSocketClient | grep "URL is null, skipping"` (不崩溃)

### TEST-01-04: 无 Crash
验证: FATAL EXCEPTION 为 0

---

## MODULE_02 权限绕过

### TEST-02-01: 无障碍服务注册
验证: `dumpsys accessibility | grep "com.vendor.rat"` 可见

### TEST-02-02: 设备管理员注册
验证: `dumpsys device_policy | grep "com.vendor.rat"` 可见

### TEST-02-03: ServiceInfo 配置
验证: `dumpsys accessibility` 中 feedbackType 包含 ALL, notificationTimeout=0

### TEST-02-04: 无障碍事件接收
操作: 切换到设置页面
验证: `logcat -s MyAccessibilityService | grep "EVENT: WINDOW_STATE_CHANGED"`

### TEST-02-05: U(event) 不误触发
验证: `logcat -s MyAccessibilityService | grep "^back$"` 为 0

### TEST-02-06: Force-stop 后自动恢复
前置: `adb shell pm grant com.vendor.rat android.permission.WRITE_SECURE_SETTINGS`
操作: `adb shell am force-stop com.vendor.rat` → 重新启动
验证: `settings get secure enabled_accessibility_services` 包含 com.vendor.rat

---

## MODULE_04 UI 自动化

### TEST-04-01: 事件处理
操作: 切换到设置页面
验证: `logcat -s MyAccessibilityService | grep "EVENT: WINDOW_STATE_CHANGED pkg=com.android.settings"`

### TEST-04-02: 引擎分发
操作: 打开无障碍设置
验证: `logcat | grep "AutoEngine/AccessibilityServiceEngine.*Window matched"`

### TEST-04-03: 多页面切换
操作: 快速切换 设置→WiFi→应用
验证: 事件数 >= 3

### TEST-04-04: 无 Crash
验证: FATAL EXCEPTION 为 0

---

## MODULE_03 厂商适配

### TEST-03-01: 华为引擎注册
验证: `logcat -s EngineManager | grep "HuaweiEngine"`

### TEST-03-02: 设置页引擎分发
操作: 打开设置
验证: `logcat | grep "AutoEngine/HuaweiEngine"` 有输出

### TEST-03-03: 无障碍设置页匹配
操作: 打开无障碍设置
验证: `logcat | grep "AccessibilityServiceEngine.*Window matched"`

### TEST-03-04: 无 Crash
验证: FATAL EXCEPTION 为 0

---

## MODULE_05 数据收集

### TEST-05-01: ScreenBroadcastReceiver 注册
验证: `logcat -s ScreenBroadcastReceiver | grep "启动完成"`

### TEST-05-02: 息屏事件
操作: `adb shell input keyevent KEYCODE_POWER`
验证: `logcat -s ScreenBroadcastReceiver | grep "手机息屏了"`

### TEST-05-03: 亮屏事件
操作: `adb shell input keyevent KEYCODE_POWER`
验证: `logcat -s ScreenBroadcastReceiver | grep "手机亮屏了"`

### TEST-05-04: 解锁事件
操作: 滑动解锁
验证: `logcat -s ScreenBroadcastReceiver | grep "手机解锁了"`

### TEST-05-05: 无障碍暂停/恢复
验证: 息屏→"stopLocalAccessibilityDelegate", 解锁→恢复

### TEST-05-06: 无 Crash
验证: FATAL EXCEPTION 为 0

---

## MODULE_07 保活机制

### TEST-07-01: 7 个 Receiver 注册
验证: `logcat -s KeepAliveManager | grep "启动完成"` 有 6 条 + ScreenBroadcastReceiver 1 条

### TEST-07-02: 后台 30 秒存活
操作: 按 HOME 键，等 30 秒
验证: `adb shell pidof com.vendor.rat` PID 不变

### TEST-07-03: CheckThread 运行
验证: `logcat -s CheckThread` 有输出

### TEST-07-04: 无 Crash
验证: FATAL EXCEPTION 为 0

---

## MODULE_06 远程控制

### TEST-06-01: HttpCommandServer 初始化
验证: 9 个 Handler 创建成功

### TEST-06-02: 路由分发
验证: dispatch() 方法编译通过，覆盖 235 路由

### TEST-06-03: 无 Crash
验证: FATAL EXCEPTION 为 0
