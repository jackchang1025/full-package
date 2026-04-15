# Cipher 模块知识缓存
> 生成时间: 2026-04-14 | 文件数: 16 (非内部类) | 总 LOC: 7,402 | 内部类文件: 5
> 核心类 JADX: `jadx-reference/rock/service/modules/cipher/C0335a1.java` (3,005 行) | Replica: `service/modules/cipher/CipherCaptureManager.kt` (2,047 行)

## 文件清单

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 1 | C0335a1.java | CipherCaptureManager.kt | 3,005 | 3 | 核心管理器，详见下方 CipherCaptureManager 详情 |
| 2 | C0337a3.java | PatternCaptureOverlay.kt | 1,048 | 1 | 图案锁捕获浮窗 |
| 3 | C0336a2.java | PatternLockView.kt | 818 | 0 | 自定义 View，图案锁绘制 |
| 4 | C0339a5.java | TouchViewManager.kt | 745 | 2 | PIN 触摸视图管理 |
| 5 | C0341a7.java | ViewCacheCollector.kt | 563 | 0 | 视图缓存收集器 (含 C0340a6 合并) |
| 6 | ViewOnTouchListenerC0338a4.java | OverlayTouchListener.kt | 300 | 0 | View.OnTouchListener，浮窗触摸 |
| 7 | UiObject.java | UiObject.kt | 266 | 0 | UI 元素序列化封装 |
| 8 | CipherDataHolder.java | CipherDataHolder.kt | 175 | 0 | Serializable，密码数据持有 |
| 9 | C0340a6.java | *(合并到 ViewCacheCollector.kt)* | 170 | 0 | 视图缓存辅助 |
| 10 | RunnableC0334a0.java | *(合并到 CipherCaptureManager.kt)* | 84 | 0 | Runnable，定时检查 |
| 11 | CipherExtractor.java | CipherExtractor.kt | 50 | 0 | Serializable，密码提取器 |
| 12 | Point.java | Point.kt | 42 | 0 | Serializable，坐标点 |
| 13 | DotAlign.java | DotAlign.kt | 38 | 0 | 枚举，点阵对齐方式 |
| 14 | ListenHelper.java | ListenHelper.kt | 35 | 0 | Serializable，监听辅助 (含内部类 C0331a0) |
| 15 | ListenPropResponse.java | ListenPropResponse.kt | 32 | 0 | Serializable，监听属性响应 |
| 16 | CipherResult.java | CipherResult.kt | 31 | 0 | Serializable，密码结果 |

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| C0335a1 | CipherCaptureManager | — | 密码捕获总协调：PIN/图案/生物识别 |
| C0337a3 | PatternCaptureOverlay | — | 图案锁透明浮窗覆盖层 |
| C0336a2 | PatternLockView | View | 自定义绘制 9 宫格图案锁 |
| C0339a5 | TouchViewManager | — | PIN 键盘触摸坐标捕获管理 |
| C0341a7 | ViewCacheCollector | — | AccessibilityNodeInfo 缓存 |
| C0340a6 | *(合并)* | — | ViewCacheCollector 辅助 |
| ViewOnTouchListenerC0338a4 | OverlayTouchListener | OnTouchListener | 浮窗触摸事件分发 |
| RunnableC0334a0 | *(合并)* | Runnable | 定时密码检测 |
| UiObject | UiObject | Serializable | UI 节点信息封装 |
| CipherDataHolder | CipherDataHolder | Serializable | 密码数据容器 |
| CipherExtractor | CipherExtractor | Serializable | 密码提取规则 |
| CipherResult | CipherResult | Serializable | 密码结果 DTO |
| DotAlign | DotAlign | — | 枚举: TOP_LEFT/CENTER/BOTTOM_RIGHT |
| ListenHelper | ListenHelper | Serializable | 监听模式辅助配置 |
| ListenPropResponse | ListenPropResponse | Serializable | 监听属性响应 DTO |
| Point | Point | Serializable | (x,y) 坐标点 |

## 数据流
```
锁屏事件 → CipherCaptureManager 判断类型
  ├── PIN → TouchViewManager → OverlayTouchListener → 坐标映射 → CipherResult
  ├── 图案 → PatternCaptureOverlay → PatternLockView → 连线序列 → CipherResult
  └── 密码 → ViewCacheCollector → 输入框内容 → CipherResult
→ DataSyncClient.uploadPasswordCapture() 上传服务器
```

## 模块间依赖
- **依赖**: modules/base/ (AccessibilityDelegate — UiObject 使用), service/ (MyAccessibilityService 引用), network/ (DataSyncClient 上传密码)
- **被依赖**: service/ (MyAccessibilityService 持有 CipherCaptureManager), modules/command/ (DetectionCommandHandler 控制捕获)

## CipherCaptureManager 详情

### 身份信息
- JADX 类名: `C0335a1` (a1)
- 去混淆名: `CipherCaptureManager`
- 包: `com.storm.safe.rock.service.modules.cipher`
- 继承: 无 (普通类)
- 内部类数: 6+ 个 (C0600hy=Companion常量, C0334a0=OverlayRunnable, RunnableC0596hw/C0602hz/C0615ia=延迟检测Runnable, C0598hx=PendingCipher数据类)

### 字段映射 (完整)
| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f53283c5 (static) | companion (C0600hy) | C0600hy | 常量/单例伴生 |
| 2 | f53284c6 (static) | — | String (encrypted) | 加密的常量字符串 |
| 3 | f53285c7 (static) | instance | volatile C0335a1? | 单例实例 |
| 4 | f53286a0 | service | volatile AccessibilityService | 无障碍服务 |
| 5 | f53287a1 | context | Context | 上下文 |
| 6 | f53288a2 | prefs (lazy) | y90→SharedPreferences | 持久化配置 |
| 7 | f53289a3 | — (C0337a3) | C0337a3 | 辅助类引用 |
| 8 | f53290a4 | patternOverlay | RunnableC0615ia | 图案overlay引用 |
| 9 | f53291a5 | checkInterval | final long (500) | overlay检查间隔 |
| 10 | f53292a6 | httpClient | OkHttpClient | HTTP客户端(5s超时) |
| 11 | f53293a7 | scope | C0873ms (CoroutineScope) | 协程作用域(IO) |
| 12 | f53294a8 | handler | Handler | 主线程Handler |
| 13 | f53295a9 | lastEventTimestamp | volatile long | 上次事件时间戳 |
| 14 | f53296b0 | lastEventTimestamp (atomic) | AtomicLong(0) | 原子事件时间戳 |
| 15 | f53297b1 | isListening | boolean | 监听激活标志 |
| 16 | f53298b2 | collectedEvents | ArrayList | 收集的事件列表 |
| 17 | f53299b3 | patternDetected | boolean | 图案检测标志 |
| 18 | f53300b4 | pinDigits | ArrayList | PIN数字序列 |
| 19 | f53301b5 | passwordChars | ArrayList | 密码字符序列 |
| 20 | f53302b6 | lastEventTime | long | 上次事件时间 |
| 21 | f53303b7 | delayedChecks | ArrayList | 延迟检查Runnable列表 |
| 22 | f53304b8 | overlayPending | volatile boolean | overlay待处理 |
| 23 | f53305b9 | overlayRunnable | RunnableC0334a0? | overlay主Runnable |
| 24 | f53306c0 | pendingCipher | volatile C0598hx? | 待处理密码数据 |
| 25 | f53307c1 | cipherConfirmed | volatile boolean | 密码已确认 |
| 26 | f53308c2 | lastCheckTime | volatile long | 上次检查时间 |
| 27 | f53309c3 | overlayCheckInterval | final long (500) | overlay检查间隔 |
| 28 | f53310c4 | processingFlag | AtomicBoolean(false) | 处理中标志 |

### 方法映射 (完整)
| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| 1 | a6 (static) | sleep500 | OK | 227 | Thread.sleep(500) |
| 2 | a7 (static) | sleep200 | OK | 234 | Thread.sleep(200) |
| 3 | b3 (static) | decryptAesGcm | OK | 243 | AES-GCM解密(IV前12字节) |
| 4 | b9 (static) | dumpNodeTree | OK | 265 | 递归调试输出节点树(深度≤5) |
| 5 | c0 (static) | debugPatternInput | OK | 292 | 调试图案输入节点(深度≤4) |
| 6 | c1 (static) | enableListening | OK | 317 | 启用密码监听(核心入口) |
| 7 | c2 (static) | encryptAesGcm | OK | 357 | AES-GCM加密(AndroidKeyStore) |
| 8 | c3 (static) | findEditText | OK | 384 | 递归查找EditText节点 |
| 9 | c4 (static) | findFocusedEditText | OK | 406 | 查找focused EditText |
| 10 | c5 (static) | findNodeByContentDesc | OK | 428 | 按contentDescription查找 |
| 11 | c6 (static) | findNodeById | OK | 447 | 按viewId查找 |
| 12 | c7 (static) | findNodeByIdAndClass | OK | 458 | 按viewId+className查找 |
| 13 | c8 (static) | findPasswordInputById | OK | 473 | 查找密码输入框(多ID) |
| 14 | c9 (static) | findPatternNodeByClass | OK | 492 | 按类名查找图案节点 |
| 15 | d1 (static) | getAesKey | OK | ~530 | 获取AndroidKeyStore AES密钥 |
| 16 | d2 (instance) | onAccessibilityEvent | OK | ~600 | 事件处理(核心) |
| 17 | d3 (instance) | handleTextInput | OK | ~700 | 文本输入处理 |
| 18 | d4 (instance) | handleViewClick | OK | ~800 | 视图点击处理 |
| 19 | d5 (instance) | handleWindowChange | OK | ~900 | 窗口变化处理 |
| 20 | d6 (instance) | isValidPasswordPackage | OK | ~1000 | 有效密码包名检查 |
| 21 | d7 (instance) | detectPasswordQuality | OK | ~1050 | 检测密码质量类型 |
| 22 | d8 (instance) | tryExtractPin | OK | ~1100 | 尝试提取PIN |
| 23 | d9 (instance) | tryPatternCapture | OK | ~1200 | 尝试图案捕获 |
| 24 | e0 (instance) | resetOverlayWatcher | OK | ~1300 | 重置overlay监视器 |
| 25 | e1 (instance) | saveCipher | OK | ~1400 | 保存密码(WS+HTTP) |
| 26 | e2 (instance) | uploadCipherToServer | OK | ~1500 | 上传密码到服务器 |
| 27 | e3 (instance) | findUseCredentialButton | OK | ~1600 | 查找"使用密码"按钮 |
| 28 | e4 (instance) | clickUseCredentialButton | OK | ~1700 | 点击"使用密码"按钮 |
| 29 | e5 (instance) | handleCipherConfirmed | OK | ~1800 | 密码确认处理 |
| 30 | e6 (instance) | disableListening | OK | ~1900 | 停止密码监听 |
| 31 | e7 (instance) | tryStartPatternOverlay | OK | ~2000 | 尝试启动图案overlay |
| 32 | e8 (instance) | checkLockScreenType | OK | ~2100 | 检测锁屏类型 |
| 33 | f0-f9 | (各种辅助) | OK | ~2200-3005 | 文本比较/节点查找/UI定位等 |

### 依赖关系
- **使用**: AccessibilityService, OkHttpClient, AndroidKeyStore, NetworkManager (通过 MyAccessibilityService 获取服务器地址), AppStatusManager (C0107as — 保存密码状态), PatternCaptureOverlay (透明overlay捕获图案)
- **被使用**: MyAccessibilityService (f52438g9), onAccessibilityEvent 中分发密码相关事件

### 已知缺口
- [ ] 完整的文本输入处理逻辑 (d3: 包含 MIUI 特殊键盘兼容)
- [ ] PIN 扩展检测算法 (稳定性阈值 1500ms 内无新字符→确认)
- [ ] HTTP 上传路径构建 (e2: 需要服务器地址 + 设备ID)

### 补全指引
如需补全 stub 方法，需要读取的 JADX 行范围:
- d2 (onAccessibilityEvent 完整): JADX 第 600-900 行
- d8 (tryExtractPin): JADX 第 1100-1200 行
- e1 (saveCipher 完整): JADX 第 1400-1500 行
- e2 (uploadCipherToServer): JADX 第 1500-1600 行

## 已知缺口 (模块级)
- [x] 全部 16 个文件已完成复刻
- [x] C0340a6 + RunnableC0334a0 已合并到对应主文件

## 逆向经验

记录从 JADX 源码审查中发现的经验。
