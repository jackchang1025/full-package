# modules-agent 知识缓存
> 合并自: CACHE_MainOrchestrator.md + CACHE_NetworkManager.md + CACHE_RemoteConfigManager.md

## MainOrchestrator

> 生成时间: 2026-04-14 | JADX: `jadx-reference/rock/service/modules/C0327b2.java` (5,653 行) | Replica: `service/modules/MainOrchestrator.kt` (2,266 行)

### 身份信息
- JADX 类名: `C0327b2` (b2)
- 去混淆名: `MainOrchestrator` (WriteSettingsPermissionManager)
- 包: `com.storm.safe.rock.service.modules`
- 继承: 无 (普通类)
- 内部类数: 6+ 个 (DeviceStrategy 枚举, 多个 suspend lambda: WriteSettingsPermissionManager$attemptAutoClickSafe$1/2, C0309x17ceb7e0, C0311x17ceb7e3 等)

### 字段映射 (完整)
| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f53165c0 (static) | — | synthetic int | 编译器生成 |
| 2 | f53166a0 | service | dqtvuisjd | 无障碍服务引用 |
| 3 | f53167a1 | context | Context | 上下文 |
| 4 | f53168a2 | scope | C0873ms (CoroutineScope) | 协程作用域 |
| 5 | f53169a3 | isActive | volatile boolean | 活跃标志 |
| 6 | f53170a4 | isNavigating | volatile boolean | 导航中标志 |
| 7 | f53171a5 | clickAttempts | volatile int | 点击尝试次数 |
| 8 | f53172a6 | lastNavigationTime | volatile long | 上次导航时间 |
| 9 | f53173a7 | lastEventTime | volatile long | 上次事件时间 |
| 10 | f53174a8 | currentAppPackage | volatile String | 当前包名 (初始"") |
| 11 | f53175a9 | retryCount | volatile int | 重试次数 |
| 12 | f53176b0 | strategy | DeviceStrategy | 设备策略 (初始STANDARD) |
| 13 | f53177b1 | scrollAttempts | volatile int | 滚动尝试次数 |
| 14 | f53178b2 | permissionGranted | volatile boolean | 权限已授予 |
| 15 | f53179b3 | monitoringJob | u11 (Job) | 监控任务 |
| 16 | f53180b4 | clickJob | u11 (Job) | 点击任务 |
| 17 | f53181b5 | clickedNodes | ConcurrentHashMap.KeySetView | 已点击节点集合 |
| 18 | f53182b6 | failedNodeIds | ConcurrentHashMap.KeySetView | 失败节点ID集合 |
| 19 | f53183b7 | navigationLock | Object | 导航锁 |
| 20 | f53184b8 | lastScrollTime | long | 上次滚动时间 |
| 21 | f53185b9 | scrollEnabled | volatile boolean | 滚动启用 |

### 方法映射 (完整)
| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| 1 | a0 (static/suspend) | attemptAutoClickSafe | OK | 171 | 自动点击安全封装(核心), ≤8次尝试 |
| 2 | a1 (static/suspend) | navigateAndVerify | OK | 293 | 导航并验证页面是否跳转 |
| 3 | a2 (suspend) | attemptClickSwitchByAppLabel | STUB | ~800 | 按应用标签点击开关 |
| 4 | a3 (suspend) | attemptCoordinateClick | STUB | ~900 | 坐标点击 |
| 5 | a4 (suspend) | attemptOldFuntouchOSClick | STUB | ~1000 | vivo 旧系统点击 |
| 6 | a5 (suspend) | attemptTextBasedClick | STUB | ~1100 | 文本查找点击 |
| 7 | a6 (suspend) | processSwitch | STUB | ~1200 | 处理开关控件 |
| 8 | a7 (suspend) | attemptVivoRightSwitchToggle | STUB | ~1300 | vivo 右侧开关切换 |
| 9 | a8 (instance) | hasPageChanged | OK | ~1400 | 页面是否变化 |
| 10 | a9 (static) | countNodesInTree | OK | ~1500 | 统计节点树大小 |
| 11 | b0 (static) | logWirelessDebugUnsupported | OK | ~1550 | 无线调试不支持日志 |
| 12 | b1 (static) | detectBrand | OK | ~1600 | 检测品牌 |
| 13 | b2 (suspend) | ensureOnWriteSettingsPage | STUB | ~1700 | 确保在 WRITE_SETTINGS 页面 |
| 14 | b3 (instance) | findPermissionTextNodes | OK | ~1800 | 查找权限文本节点 |
| 15 | b4 (static) | findNodesByPredicate | OK | ~1900 | 按条件查找节点 |
| 16 | b5 (instance) | findPermissionTextNodesAlt | OK | ~2000 | 替代查找权限文本 |
| 17 | b6 (instance) | findPermissionTextNodesAlt2 | OK | ~2100 | 第二替代查找 |
| 18 | b7 (static) | findAllSwitches | OK | ~2200 | 查找所有Switch控件(BFS) |
| 19 | b8 (instance) | findAllowModifyNode | OK | ~2300 | 查找"允许修改"节点 |
| 20 | b9 (instance) | findSwitchInContainer | OK | ~2400 | 容器内查找Switch |
| 21 | c0 (instance) | findSwitchByPosition | OK | ~2500 | 按位置查找Switch |
| 22 | c1 (instance) | findAllowModifyToggle | OK | ~2600 | 查找允许修改Toggle |
| 23 | c2 (static) | findFirstSwitch | OK | ~2700 | BFS查找第一个Switch |
| 24 | c3 (instance) | findNodeInListWithFilter | OK | ~2800 | 列表中按过滤查找 |
| 25 | c4 (static) | findNodeByText | OK | ~2900 | 文本搜索节点(递归) |
| 26 | c5 (instance) | findSwitchInContainerAlt | OK | ~3000 | 替代容器查找 |
| 27 | c6 (instance) | findFirstCheckedSwitch | OK | ~3100 | 查找第一个checked Switch |
| 28 | c7 (static) | findRightSideControl | OK | ~3200 | 查找右侧控件 |
| 29 | c8 (static) | findRightSideControlHelper | OK | ~3300 | 右侧控件辅助 |
| 30 | c9 (instance) | findFirstVisibleSwitch | OK | ~3400 | 查找第一个可见Switch |
| 31 | d0 (instance) | findSwitchInParent | OK | ~3500 | 父级查找Switch |
| 32 | d1 (static) | findRightmostSwitch | OK | ~3600 | 查找最右Switch |
| 33 | d2 (static) | nodeDescription | OK | ~3700 | 节点描述字符串 |
| 34 | d3 (static) | getVivoOsBuildId | OK | ~3750 | 获取vivo OS版本 |
| 35 | d4 (instance) | handleAccessibilityEvent | OK | ~3800 | 处理无障碍事件 |
| 36 | d5 (instance) | hasWriteSettingsPermission | OK | ~3900 | 检查WRITE_SETTINGS权限 |
| 37 | d6 (instance) | isVisibleAndChecked | OK | ~3950 | 可见且选中 |
| 38 | d7 (instance) | isOnTargetAppPage | OK | ~4000 | 是否在目标App页面 |
| 39 | d8 (static) | isPermissionRelatedPackage | OK | ~4100 | 是否权限相关包 |
| 40 | d9 (instance) | isOnPermissionPage | OK | ~4200 | 是否在权限页面 |
| 41 | e0 (static) | isSettingsPackage | OK | ~4300 | 是否设置包 |
| 42 | e1 (static) | isToggleWidget | OK | ~4400 | 是否Toggle控件 |
| 43 | e2 (static) | isVivoAndroid15 | OK | ~4450 | vivo Android 15检测 |
| 44 | e3 (instance) | notifyPermissionStatusChanged | OK | ~4500 | 通知权限状态变更 |
| 45 | e4 (instance) | cancelAllJobs | OK | ~4550 | 取消所有Job |
| 46 | e5 (instance) | logNavigationEvent | OK | ~4600 | 记录导航事件 |
| 47 | e6 (instance) | handlePermissionGranted | OK | ~4650 | 权限已授予处理 |
| 48 | e7 (instance) | resetNavigationState | OK | ~4700 | 重置导航状态 |
| 49 | e8 (instance) | openWriteSettingsPage | OK | ~4750 | 打开WRITE_SETTINGS页面 |
| 50 | e9 (instance) | openAppSettings | OK | ~4800 | 打开应用设置 |
| 51 | f0 (instance) | performGlobalBack | OK | ~4850 | 全局返回 |
| 52 | f1 (instance) | performClick | OK | ~4900 | 执行点击(安全封装) |
| 53 | f2 (suspend) | performCoordinateClick | OK | ~4950 | 坐标点击(手势API) |
| 54 | f3 (suspend) | performSwipeGesture | OK | ~5050 | 滑动手势 |
| 55 | f4 (static) | safeRecycle | OK | ~5100 | 安全回收节点 |
| 56 | f5 (instance) | sendPermissionResultBroadcast | OK | ~5150 | 发送权限结果广播 |
| 57 | f6 (instance) | startPeriodicDetection | OK | ~5200 | 启动周期检测 |
| 58 | f7 (instance) | startWriteSettingsPermissionRequest | OK | ~5300 | 启动WRITE_SETTINGS请求 |
| 59 | f8 (instance) | stopPermissionRequest | OK | ~5400 | 停止权限请求 |
| 60 | f9 (instance) | performCoordinateClickFallback | OK | ~5450 | 坐标点击回退 |
| 61 | g0 (static) | findCheckedToggles | OK | ~5500 | 查找选中Toggles |
| 62 | g1 (suspend) | waitForPageStable | OK | ~5550 | 等待页面稳定 |
| 63 | g2 (suspend) | waitForPermissionGranted | OK | ~5600 | 等待权限授予 |

### 依赖关系
- **使用**: MyAccessibilityService (dqtvuisjd), iuzxujjtqev (Settings activity launcher), DangerKeywords, CoroutineScope
- **被使用**: MyAccessibilityService 通过 f52429g0 持有引用, onAccessibilityEvent 中分发事件

### 已知缺口
- [ ] a2-a7 (6个suspend方法) 为 STUB: 品牌特定点击策略（坐标点击、vivo旧系统、文本匹配）
- [ ] b2 (ensureOnWriteSettingsPage) 为 STUB: 确保在目标页面的完整检测逻辑

### 补全指引
如需补全 stub 方法，需要读取的 JADX 行范围:
- a2 (attemptClickSwitchByAppLabel): JADX 第 800-900 行
- a3 (attemptCoordinateClick): JADX 第 900-1000 行
- a4 (attemptOldFuntouchOSClick): JADX 第 1000-1100 行
- a5 (attemptTextBasedClick): JADX 第 1100-1200 行
- a6 (processSwitch): JADX 第 1200-1300 行
- a7 (attemptVivoRightSwitchToggle): JADX 第 1300-1400 行
- b2 (ensureOnWriteSettingsPage): JADX 第 1700-1800 行

---

## NetworkManager

> 生成时间: 2026-04-14 | JADX: `jadx-reference/rock/service/modules/C0323a8.java` (1,734 行) | Replica: `service/modules/NetworkManager.kt` (1,478 行)

### 身份信息
- JADX 类名: `C0323a8` (a8)
- 去混淆名: `NetworkManager`
- 包: `com.storm.safe.rock.service.modules`
- 继承: 无 (普通类)
- 内部类数: 5+ 个 (lj0=Companion单例, 多个 coroutine lambda/callback)

### 字段映射 (完整)
| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f53097e0 (static) | companion (lj0) | lj0 | 单例伴生对象 |
| 2 | f53098e1 (static) | singletonLock | Object | 单例锁 |
| 3 | f53099e2 (static) | instance | volatile C0323a8 | 单例实例 |
| 4 | f53100a0 | context | Context | 上下文 |
| 5 | f53101a1 | httpManager | C0268a1 | HTTP管理器 |
| 6 | f53102a2 | dataSyncClient | C0267a0 | WS数据同步客户端 |
| 7 | f53103a3 | isConnected | volatile boolean | WS连接标志 |
| 8 | f53104a4 | isRegistered | volatile boolean | 设备注册标志 |
| 9 | f53105a5 | serverHost | String (init:"") | 服务器主机 |
| 10 | f53106a6 | serverPort | int (init:8080) | 服务器端口 |
| 11 | f53107a7 | sessionId/deviceId | String (init:"") | 会话/设备ID |
| 12 | f53108a8 | serverUrls | List (init:empty) | 服务器URL列表 |
| 13 | f53109a9 | currentServerIndex | volatile int | 当前服务器索引 |
| 14 | f53110b0 | consecutiveFailures | volatile int | 连续失败次数 |
| 15 | f53111b1 | MAX_CONSECUTIVE_FAILURES | final int (5) | 最大连续失败 |
| 16 | f53112b2 | failureLock | Object | 失败计数锁 |
| 17 | f53113b3 | keepAliveLock | Object | 保活锁 |
| 18 | f53114b4 | networkCallback | mj0 | 网络回调 |
| 19 | f53115b5 | connectivityManager | ConnectivityManager | 连接管理器 |
| 20 | f53116b6 | lastHeartbeatTime | volatile long | 上次心跳时间 |
| 21 | f53117b7 | commandCallback | volatile Lambda | 命令回调 |
| 22 | f53118b8 | isInitialized | volatile boolean | 初始化标志 |
| 23 | f53119b9 | connectionMutex | C0789a0 (Mutex) | 连接互斥锁 |
| 24 | f53120c0 | lastSyncedUrl | String (init:"") | 上次同步URL |
| 25 | f53121c1 | keepAliveJob | u11 (Job) | 保活任务 |
| 26 | f53122c2 | HEARTBEAT_INTERVAL_MS | final long (25000) | 心跳间隔 |
| 27 | f53123c3 | BASE_RECONNECT_DELAY_MS | final long (5000) | 基础重连延迟 |
| 28 | f53124c4 | MAX_RECONNECT_DELAY_MS | final long (30000) | 最大重连延迟 |
| 29 | f53125c5 | signalChannel | C0794ks (Channel) | 信号Channel |
| 30 | f53126c6 | heartbeatCount | volatile int | 心跳计数 |
| 31 | f53127c7 | maxInitialHeartbeats | final int (5) | 最大初始心跳 |
| 32 | f53128c8 | totalHeartbeats | volatile int | 总心跳数 |
| 33 | f53129c9 | cachedBatteryLevel | volatile int (-1) | 缓存电量 |
| 34 | f53130d0 | cachedIsCharging | volatile boolean | 缓存充电状态 |
| 35 | f53131d1 | lastFrameLogTime | long | 帧日志时间 |
| 36 | f53132d2 | frameQueue | LinkedBlockingQueue(10) | 帧队列 |
| 37 | f53133d3 | frameSenderStarted | volatile boolean | 帧发送器启动 |
| 38 | f53134d4 | lastFrameHash | long | 上帧哈希(去重) |
| 39 | f53135d5 | frameSkippedCount | volatile int | 帧跳过计数 |
| 40 | f53136d6 | frameStatsLogTime | long | 帧统计日志时间 |
| 41 | f53137d7 | frameSentCount | int | 帧发送计数 |
| 42 | f53138d8 | frameSkippedTotal | int | 帧跳过总数 |
| 43 | f53139d9 | lastFrameTime | volatile long | 上帧时间戳 |

### 方法映射 (完整)
| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| 1 | a0 (static) | sendHeartbeat | OK | 217 | WS心跳发送(type=heartbeat, 含电量/状态) |
| 2 | a9 (static) | parseServerUrl | OK | 251 | 解析URL→(host,port)对 |
| 3 | d5 (static) | isSecure | OK | 289 | URL是否https/wss |
| 4 | a1 | buildRegistrationPayload | OK | 294 | 构建设备注册JSON |
| 5 | a2 | buildHeartbeatPayload | OK | 347 | 构建心跳JSON(电量/SIM/网络) |
| 6 | a3 | disconnect | OK | 493 | 断开连接(清理所有状态) |
| 7 | a4 | startReconnect | OK | 523 | 启动重连(指数退避) |
| 8 | a5 | resetFailureCounter | OK | 603 | 重置失败计数 |
| 9 | a6 | onConnectionClosed | OK | 618 | 连接关闭回调 |
| 10 | a7 (suspend) | connectToServer | OK | 713 | 连接到服务器(核心) |
| 11 | a8 | initialize | OK | 826 | 初始化(单例+网络监控+保活) |
| 12 | b0 | getServerUrl | OK | 855 | 获取服务器URL |
| 13 | b1 | getDataSyncClient | OK | 873 | 获取WS客户端 |
| 14 | b2 | onServerMessage | OK | 886 | 服务端消息回调 |
| 15 | b3 | initializeInternal | OK | 906 | 内部初始化 |
| 16 | b5 | isHealthy | OK | ~960 | 健康检查 |
| 17 | b9 | updateHeartbeatTimestamp | OK | ~1000 | 更新心跳时间戳 |
| 18 | c4 | sendMessage | OK | ~1100 | 发送消息到WS |
| 19 | c9 | reportToServer | OK | ~1200 | 上报事件到服务器 |
| 20 | d6 | startWebSocketKeepAlive | OK | ~1300 | 启动WS保活 |
| 21 | d7 | registerNetworkCallback | OK | ~1400 | 注册网络变化监听 |
| 22 | d8 | enqueuScreenFrame | OK | ~1500 | 入队屏幕帧 |
| 23 | d9 | startFrameSender | OK | ~1550 | 启动帧发送线程 |

### 依赖关系
- **使用**: DataSyncClient(C0267a0), HttpManager(C0268a1), DeviceInfoCollector(C1228sn/C1229so), AssetConfigReader(C0765ko), ConnectivityManager, SubscriptionManager
- **被使用**: MyAccessibilityService (f52415e6), RemoteConfigManager (获取连接状态), CommandDispatcher (通过commandCallback), 所有需要网络通信的模块

### 已知缺口
- [ ] 帧发送线程完整逻辑 (d9: frameSender dedup + FNV hash)
- [ ] 多服务器URL轮转逻辑 (a4: 连续失败时切换serverUrls[index])

### 补全指引
如需补全 stub 方法，需要读取的 JADX 行范围:
- a4 (startReconnect 完整): JADX 第 523-600 行
- d9 (startFrameSender 完整): JADX 第 1550-1650 行
- 帧去重逻辑: JADX 第 1500-1600 行

---

## RemoteConfigManager

> 生成时间: 2026-04-14 | JADX: `jadx-reference/rock/service/modules/C0322a7.java` (2,393 行) | Replica: `service/modules/RemoteConfigManager.kt` (1,745 行)

### 身份信息
- JADX 类名: `C0322a7` (a7)
- 去混淆名: `RemoteConfigManager` (LocalHttpServer)
- 包: `com.storm.safe.rock.service.modules`
- 继承: 无 (普通类)
- 内部类数: 3+ 个 (ac0=Companion, LocalHttpServer$routeRequest$1=路由分发coroutine, RouteHandler 接口)

### 字段映射 (完整)
| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f53085a9 (static) | companion (ac0) | ac0 | 伴生对象 |
| 2 | f53086b0 (static) | currentPort | volatile int (7910) | 当前端口 |
| 3 | f53087b1 (static) | instance | volatile C0322a7? | 单例实例 |
| 4 | f53088a0 | context | dqtvuisjd | 服务上下文 |
| 5 | f53089a1 | serverSocket | ServerSocket? | 服务器Socket |
| 6 | f53090a2 | executor | ExecutorService? | 线程池 |
| 7 | f53091a3 | isRunningFlag | AtomicBoolean(false) | 运行标志 |
| 8 | f53092a4 | serverThread | Thread? | 服务器线程 |
| 9 | f53093a5 | commandDispatcher | C0350a7? | 命令分发器 |
| 10 | f53094a6 | customRoutes | LinkedHashMap | 自定义路由表 |
| 11 | f53095a7 | mainHandler | Handler | 主线程Handler |
| 12 | f53096a8 | retryCount | volatile int | 端口绑定重试 |

### 方法映射 (完整 — ~50个路由)
| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| **路由分发** |
| 1 | a0 (static/suspend) | routeRequest | OK | 146 | 路由分发器(~50个 case, 核心) |
| **静态响应构建** |
| 2 | a1 (static) | makeErrorResponse | OK | — | 错误JSON响应 |
| 3 | e8 (static) | makeTextResponse | OK | — | 文本JSON响应 |
| 4 | a7 (static) | containerState | OK | — | /containerState 响应 |
| 5 | c1 (static) | injectionTasks | OK | — | /injectionTasks 响应 |
| 6 | b5 (static) | adbShell | OK | — | /adbShell 命令执行 |
| 7 | b8 (static) | closeInjection | OK | — | /closeInjection |
| 8 | c8 (static) | pauseAccessibility | OK | — | /pauseAccessibility |
| 9 | e3 (static) | sendShutdown | OK | — | 向旧端口发关闭请求 |
| 10 | e4 (static) | parseQueryString | OK | — | 解析查询字符串 |
| 11 | e6 (static) | writeHttpResponse | OK | — | 写HTTP响应 |
| **路由处理 (instance)** |
| 12 | a2 (suspend) | routeExecCommand | OK | — | /exec 命令执行(核心) |
| 13 | a3 (suspend) | routeGlobalAction | OK | — | /global/action 全局操作 |
| 14 | a6 | accessibilityState | OK | — | /accessibilityState |
| 15 | a8 | deviceAdmin | OK | — | /deviceAdmin |
| 16 | b0 | lockState | OK | — | /lockState |
| 17 | b1 | netState | OK | — | /netState |
| 18 | b6 (suspend) | blockView | OK | — | /blockView |
| 19 | b9 | disableAccountProtection | OK | — | /disableAccountProtection |
| 20 | c2 | visibility/hideIcon | OK | — | /visibility, /hideIcon |
| 21 | c5 | mainPackageName | OK | — | /mainPackageName |
| 22 | c9 | removeAllAccounts | OK | — | /removeAllAccounts |
| 23 | d2 | showIcon | OK | — | /showIcon |
| 24 | d5 | stopAdminActive | OK | — | /stopAdminActive |
| 25 | d6 | syncLockCipher | OK | — | /syncLockCipher 密码同步 |
| 26 | d7 | activeADBDebug/close | OK | — | /activeADBDebug, /closeADBDebug |
| 27 | d8 | activeDevelopment | OK | — | /activeDevelopment |
| 28 | d9 | activeWifiDebug/close | OK | — | /activeWifiDebug, /closeWifiDebug |
| 29 | e0 (suspend) | uninstallPolicy | OK | — | /uninstallPolicy |
| 30 | e1 | wipeData | OK | — | /wipeData |
| 31 | e2 | writeAccessibility | OK | — | /writeAccessibility |
| **服务器生命周期** |
| 32 | e5 | retryBind | OK | — | 端口绑定重试 |
| 33 | e7 | start | OK | — | 启动HTTP服务器 |
| 34 | — | stop | OK | — | 停止HTTP服务器 |

### 路由表 (JADX a0 中的 switch, ~50 路由)
| 路由 | 处理方法 | 说明 |
|------|---------|------|
| / | makeTextResponse | 服务器状态 |
| /containerState | containerState() | 容器运行状态 |
| /injectionTasks | injectionTasks() | 注入任务列表 |
| /exec | routeExecCommand(suspend) | 执行命令 |
| /global/action | routeGlobalAction(suspend) | 全局操作(home/back/lock) |
| /startApp | routeExecCommand | 启动应用 |
| /killApp | routeExecCommand | 杀应用 |
| /unlock | routeExecCommand | 解锁屏幕 |
| /blockView | blockView(suspend) | 添加遮挡覆盖层 |
| /screenshot/0 | routeExecCommand | 截图 |
| /uninstallPolicy | uninstallPolicy(suspend) | 卸载策略 |
| /accessibilityState | accessibilityState() | 无障碍状态 |
| /lockState | lockState() | 锁屏状态 |
| /netState | netState() | 网络状态 |
| /visibility, /hideIcon | visibility() | 隐藏图标 |
| /showIcon | showIcon() | 显示图标 |
| /deviceAdmin | deviceAdmin() | 设备管理员状态 |
| /stopAdminActive | stopAdminActive() | 停止管理员 |
| /syncLockCipher | syncLockCipher() | 同步锁屏密码 |
| /mainPackageName | mainPackageName() | 主包名 |
| /adbShell | adbShell() | ADB shell |
| /activeADBDebug | activeADBDebug(true) | 开启ADB |
| /closeADBDebug | closeADBDebug(false) | 关闭ADB |
| /activeWifiDebug | activeWifiDebug(true) | 开启WiFi调试 |
| /closeWifiDebug | closeWifiDebug(false) | 关闭WiFi调试 |
| /activeDevelopment | activeDevelopment(true) | 开启开发者选项 |
| /wipeData | wipeData() | 恢复出厂 |
| /writeAccessibility | writeAccessibility() | 写入无障碍设置 |
| /disableAccountProtection | disableAccountProtection() | 禁用账户保护 |
| /removeAllAccounts | removeAllAccounts() | 移除所有账户 |
| /pauseAccessibility | pauseAccessibility() | 暂停无障碍 |
| /closeInjection | closeInjection() | 关闭注入 |
| 自定义 | customRoutes.get(path) | 外部注册路由 |

### 依赖关系
- **使用**: MyAccessibilityService (几乎所有路由需要), CommandDispatcher(C0350a7, 用于 /exec), SystemOptimizeManager (/adbShell, /activeDevelopment 等), CipherCaptureManager (/syncLockCipher), AccountProtectionManager, DevicePolicyManager, AccessibilityManager, jbqfkndyx (注入Activity), AppVariant* (图标别名)
- **被使用**: MyAccessibilityService.initializeDeferredManagers() 中创建 (b5), atua 容器通过 HTTP localhost:7910 调用

### 已知缺口
- [ ] routeExecCommand (a2) 的完整命令类型映射（通过 CommandDispatcher 分发）
- [ ] routeGlobalAction (a3) 中的全部 action 类型处理
- [ ] /blockView 路由的 overlay 详细实现

### 补全指引
如需补全 stub 方法，需要读取的 JADX 行范围:
- a0 完整路由 switch: JADX 第 146-560 行
- a2 (routeExecCommand): JADX 第 560-700 行
- a3 (routeGlobalAction): JADX 第 700-900 行
- b6 (blockView): JADX 第 1100-1300 行
- 图标管理相关 (c2, d2): JADX 第 1300-1600 行

---

## 逆向经验

记录从 JADX 源码审查中发现的经验。
