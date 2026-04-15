# SystemOptimizeManager 知识缓存
> 生成时间: 2026-04-14 | JADX: `jadx-reference/rock/service/modules/setup/C0360a2.java` (5,666 行) | Replica: `service/modules/setup/SystemOptimizeManager.kt` (4,097 行)

## 身份信息
- JADX 类名: `C0360a2` (a2)
- 去混淆名: `SystemOptimizeManager`
- 包: `com.storm.safe.rock.service.modules.setup`
- 继承: 无 (普通类, private constructor + 单例)
- 内部类数: 14+ 个 (j41=Companion, PairState/DevOptState 枚举, bf1=WindowDetector, gg0=UIAutomator, h40=SwitchHelper, g41=AdbConnection, p41=PairSuccessCallback, OpenDevelopmentDelegate(C0358a0), PairingPacketHeader, AdbPacket, 多个 Runnable/Callable)

## 字段映射 (完整 — 70+字段)
| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f53810f9 (static) | companion lock | j41 | 单例锁 |
| 2 | f53811g0 (static) | sInstance | volatile C0360a2? | 单例实例 |
| 3 | f53812g1 (static) | cachedSslContext | volatile SSLContext? | TLS上下文缓存 |
| 4 | f53813g2 (static) | cachedPrivateKey | volatile PrivateKey? | RSA私钥缓存 |
| 5 | f53814g3 (static) | cachedCertificate | volatile X509Certificate? | 证书缓存 |
| 6 | f53815a0 | service | volatile AccessibilityService | 无障碍服务 |
| 7 | f53816a1 | context | Context | 上下文 |
| 8 | f53817a2 | executor | ScheduledExecutorService | 调度线程池 |
| 9 | f53818a3 | processedActions | ConcurrentLinkedQueue | 已处理操作队列 |
| 10 | f53819a4 | pairState | AtomicReference(PairState) | 配对状态 |
| 11 | f53820a5 | devOptState | AtomicReference(DevOptState) | 开发者选项状态 |
| 12 | f53821a6 | mainLock | ReentrantLock | 主锁 |
| 13 | f53822a7 | isFinished | AtomicBoolean | 完成标志 |
| 14 | f53823a8 | isPairRunning | AtomicBoolean | 配对运行中 |
| 15 | f53824a9 | windowDetector | bf1 | 窗口检测器 |
| 16 | f53825b0 | uiAutomator | gg0 | UI自动化器 |
| 17 | f53826b1 | switchHelper | h40 | Switch辅助 |
| 18 | f53827b2 | mainHandler | Handler | 主线程Handler |
| 19 | f53828b3 | openDevDelegate | OpenDevelopmentDelegate | 开发者选项委托 |
| 20 | f53829b4 | onCompleteCallback | w00 | 完成回调 |
| 21 | f53830b5 | onFailureCallback | h10 | 失败回调 |
| 22 | f53831b6 | autoInputTriggered | boolean | 自动输入已触发 |
| 23 | f53832b7 | onPairSuccessCallback | p41 | 配对成功回调 |
| 24 | f53833b8 | openDevRetryCount | int | 开发者选项重试次数 |
| 25 | f53834b9 | maxRetries | final int (3) | 最大重试次数 |
| 26 | f53835c0 | oppoDisablePermMonitorDone | boolean | OPPO 权限监控关闭完成 |
| 27 | f53836c1 | usbInstallSettingsDone | boolean | USB安装设置完成 |
| 28 | f53837c2 | usbSecurityDialogDone | boolean | USB安全对话框完成 |
| 29 | f53838c3 | adbConfigPrefs | lazy SharedPreferences | ADB配置 |
| 30 | f53839c4 | localIpAddress | String | 本地IP地址 |
| 31 | f53840c5 | isLocalServiceAlive | AtomicBoolean | 本地服务存活 |
| 32 | f53841c6 | isConnected | AtomicBoolean | ADB连接标志 |
| 33 | f53842c7 | discoveredPorts | ArrayList<Pair> | mDNS发现端口 |
| 34 | f53843c8 | tlsKeyPair | KeyPair? | TLS密钥对 |
| 35 | f53844c9 | tlsCertificate | X509Certificate? | TLS自签名证书 |
| 36 | f53845d0 | lastUsbDebugDialogTime | volatile long | USB调试对话框时间 |
| 37 | f53846d1 | lastHeartbeatTime | long | 上次心跳时间 |
| 38 | f53847d2 | heartbeatScheduled | boolean | 心跳已调度 |
| 39 | f53848d3 | heartbeatFailCount | AtomicInteger(0) | 心跳失败计数 |
| 40 | f53849d4 | silentRecoverRunning | volatile boolean | 静默恢复运行中 |
| 41 | f53850d5 | heartbeatExecutor | lazy ScheduledExecutor | 心跳线程池 |
| 42 | f53851d6 | reconnectAttemptCount | AtomicInteger(0) | 重连尝试计数 |
| 43 | f53852d7 | firstDeployDone | volatile boolean | 首次部署完成 |
| 44 | f53853d8 | pairRetryCount | AtomicInteger(0) | 配对重试计数 |
| 45 | f53854d9 | connectErrorCount | AtomicInteger(0) | 连接错误计数 |
| 46 | f53855e0 | heartbeatLock | ReentrantLock | 心跳锁 |
| 47 | f53856e1 | adbLock | ReentrantLock | ADB锁 |
| 48 | f53857e2 | adbTaskExecutor | lazy ExecutorService | ADB任务线程池 |
| 49 | f53860e5 | nsdCallback | C0931ny (NsdManager) | mDNS回调 |
| 50 | f53861-70 | ADB_CMD_CNXN..STLS_VERSION | final int | ADB协议常量 |
| 51 | f53871f6 | HOST_IDENTIFIER | final byte[] | "host::\0" |
| 52 | f53872f7 | adbConnection | volatile g41? | ADB连接对象 |
| 53 | f53873f8 | connectionLock | Object | 连接锁 |

## 方法映射 (核心方法 — 完整有 60+)
| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| 1 | a9 (static) | findClickableParentCompat | OK | 441 | 查找可点击父节点(10层) |
| 2 | b0 (static) | pairInDevOption | OK | 452 | 开发者选项内配对(核心UI自动化) |
| 3 | b2 (static) | toPeerInfo | OK | 626 | 构建RSA peer info |
| 4 | b5 (static) | readAdbPacket | OK | 798 | 读取ADB协议包 |
| 5 | c2 (static) | decryptPairingMessage | OK | 850 | AES-GCM解密配对消息 |
| 6 | c3 (static) | encryptPairingMessage | OK | 862 | AES-GCM加密配对消息 |
| 7 | c5 (static) | reverseBytes | OK | 874 | BigInteger→LE字节 |
| 8 | c7 (static) | buildAdbPacket | OK | 888 | 构建ADB wire协议包 |
| 9 | e5 (static) | toAndroidRsaPublicKey | OK | 972 | RSA→Android ADB格式(524字节) |
| 10 | h5 (static) | deriveKeys | OK | 1412 | HKDF-SHA256密钥派生 |
| 11 | i8 (static) | readPairingPacket | OK | 1468 | 读取配对包头(6字节) |
| 12 | j9 (static) | writePairingPacket | OK | 1524 | 写入配对包 |
| 13 | k1 (static) | sleepQuietly | OK | ~1560 | 安全sleep |
| 14 | a1 (instance) | isInDevOptions | OK | ~500 | 是否在开发者选项页 |
| 15 | a2 (instance) | checkDevOptionsEnabled | OK | ~550 | 开发者选项是否启用 |
| 16 | a3 (instance) | startPairingFlow | OK | ~600 | 启动配对流程(核心) |
| 17 | a4 (instance) | openDeveloperOptions | OK | ~700 | 打开开发者选项 |
| 18 | a5 (instance) | navigateToWirelessDebug | OK | ~800 | 导航到无线调试 |
| 19 | a6 (instance) | handleAccessibilityEvent | OK | ~900 | 处理无障碍事件 |
| 20 | a7 (instance) | attemptAutoInput | OK | ~1000 | 尝试自动输入配对码 |
| 21 | a8 (instance) | performSpake2Pairing | OK | ~1100 | SPAKE2配对(核心密码学) |
| 22 | b1 (instance) | connectAdbTls | OK | ~1200 | TLS ADB连接 |
| 23 | b3 (instance) | executeShellCommand | OK | ~1300 | 执行shell命令 |
| 24 | b4 (instance) | generateKeyPairAndCert | OK | ~1400 | 生成RSA密钥对+X509证书 |
| 25 | c0 (instance) | startMdnsDiscovery | OK | ~1500 | 启动mDNS发现 |
| 26 | c1 (instance) | onMdnsServiceFound | OK | ~1600 | mDNS服务发现回调 |
| 27 | c4 (instance) | sendAdbAuth | OK | ~1700 | 发送ADB认证 |
| 28 | c6 (instance) | handleAdbCnxn | OK | ~1800 | 处理ADB CNXN |
| 29 | d0 (instance) | saveAdbKeys | OK | ~1900 | 保存ADB密钥 |
| 30 | d1 (instance) | loadAdbKeys | OK | ~2000 | 加载ADB密钥 |
| 31 | d6 (instance) | findScrollableView | OK | ~2200 | 查找可滚动视图 |
| 32 | g5 (static) | getLocalIpAddress | OK | ~3000 | 获取本地IP |

## 依赖关系
- **使用**: AccessibilityService, SPAKE2 (io.github.muntashirakon), Conscrypt (TLS), NsdManager (mDNS), OpenDevelopmentDelegate(C0358a0), WindowDetector(bf1), UIAutomator(gg0), SwitchHelper(h40)
- **被使用**: MyAccessibilityService (通过单例), RemoteConfigManager (/adbShell → executeShellCommand), 自动化配对流程

## 已知缺口
- [ ] 完整的 UI 自动化状态机 (b0: pairInDevOption 中品牌分支非常复杂, ~500行)
- [ ] vivo/OPPO 特殊开发者选项流程 (多种 Switch ID 和布局变体)
- [ ] ADB 连接后的持久化管理 (心跳/断线重连)

## 逆向经验

> 记录从 JADX 源码审查中发现的经验。

## 补全指引
如需补全 stub 方法，需要读取的 JADX 行范围:
- b0 (pairInDevOption 完整): JADX 第 452-950 行 (最大方法)
- a8 (performSpake2Pairing): JADX 第 1100-1400 行
- b1 (connectAdbTls): JADX 第 1200-1500 行
- UI自动化 (品牌分支): JADX 第 2500-4000 行
