# OPPO/Realme/OnePlus/OPLUS UI 自动化权限获取深度分析

> **样本**: update.apk
> **分析文件**: `jadx-reference/rock/service/modules/yw5xud/C0368a5.java` (OppoStepsSimplified, 11012 行) + 28 个 Continuation 内类
> **SharedPreferences**: `"oppo_simplified_v6"`
> **日期**: 2026-04-17


---

## 一、执行架构

### 1.1 SubBrand 枚举

OppoStepsSimplified 通过 `Build.BRAND/MANUFACTURER/MODEL` 将设备划分为 4 个子品牌：

| SubBrand | ordinal | 检测逻辑 |
|----------|---------|---------|
| **OPPO** | 0 | 默认（不匹配其他品牌） |
| **REALME** | 1 | BRAND/MANUFACTURER/MODEL 含 `"realme"` |
| **ONEPLUS** | 2 | 含 `"oneplus"` |
| **OPLUS** | 3 | 含 `"oplus"` |

特殊白名单机型：`RMX3823, RMX1991, PKA110, PHM110, PEDM00, PHB110` → 特殊处理

### 1.2 主编排器 execute()（29 状态协程）

```
execute (m212321b9)
│
├─ Step 0:  前置检查 Settings.System.canWrite()
├─ Step 1:  executeBasicPermissions（umrkmgrri 子模块）
├─ Step 2:  executeBatterySettingsWithResult → SubBrand 分发
│           ├─ REALME  → mRealme
│           ├─ ONEPLUS → mOnePlus
│           └─ OPPO/OPLUS → mOppo
├─ Step 3:  executeBackgroundAndAutoStartWithResult
├─ Step 4:  executeOverlayWithResult
├─ Step 5:  executeReadAppListWithResult
├─ Step 6:  executeFileAccessWithResult (Android 11+)
├─ Step 7:  executeNotificationManagement
├─ Step 8:  executeOppoRecentTaskLock
└─ Step 9:  返回桌面
```

每个权限步骤最多重试 2 次，重试前调 `performGlobalAction(HOME)` 返回桌面。

---

## 二、权限获取机制分类

### 机制 A — 无障碍 UI 文本搜索点击

通过 `findAccessibilityNodeInfosByText()` 定位节点，`performAction(ACTION_CLICK=16)` 点击。

**4 策略点击回退机制**（核心方法 `clickV` / `openSwitch` / `closeSwitch`）：

```
策略 1: node.isClickable() && performAction(ACTION_CLICK)
    └─ 直接点击目标节点

策略 2: 遍历父节点链 → parent.isClickable() && performAction(ACTION_CLICK)
    └─ openSwitch/closeSwitch: 最多 8 层
    └─ clickV: 不限层数

策略 3: getBoundsInScreen(rect) → dispatchGesture(centerX, centerY)
    └─ 50ms stroke duration

策略 4 (仅 openSwitch/closeSwitch):
    └─ collectSwitchNodes() 收集所有 Switch/CheckBox
    └─ syncGestureClick(100ms, CountDownLatch 2000ms)
```

跳过条件：`AutoCompleteTextView` 类型节点。

**`#` 分隔符多级菜单导航**（`clickVWithScroll`）：
文本支持 `#` 分隔多级候选词（最多 6 个），按顺序逐级导航。

### 机制 B — Intent 直连系统 Activity

**标准 Android Settings Action**：
| Intent Action | 目标 |
|--------------|------|
| `android.settings.SETTINGS` | 设置主页 |
| `android.settings.APPLICATION_DETAILS_SETTINGS` + `package:` URI | 应用详情 |
| `android.settings.action.MANAGE_OVERLAY_PERMISSION` + `package:` URI | 悬浮窗 |
| `android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION` + `package:` URI | 文件访问 |
| `android.settings.CHANNEL_NOTIFICATION_SETTINGS` + `CHANNEL_ID="OFF"` | 关闭通知渠道 |

**OPPO 私有 ComponentName 直连**（5 个自启动管理 Activity）：
| 包名 | Activity |
|------|----------|
| `com.coloros.safecenter` | `.permission.startup.StartupAppListActivity` |
| `com.oppo.safe` | `.permission.startup.StartupAppListActivity` |
| `com.oplus.safecenter` | `.permission.startup.StartupAppListActivity` |
| `com.coloros.safecenter` | `.startupapp.view.StartupAppListActivity` |
| `com.oplus.safecenter` | `.startupapp.view.StartupAppListActivity` |

> **注意**：OPPO 系 **0 条加密字符串**（全部明文 / 标准 Intent），与华为系大量使用 StringUtil 加密形成对比。

### 机制 C — 系统 API / 子模块调用

- `umrkmgrri.start(context)` — 基础权限授予（独立线程模型）
- `performGlobalAction(RECENTS=3)` — 打开最近任务
- `performGlobalAction(HOME=2)` — 返回桌面
- `performGlobalAction(BACK=1)` — 返回

### 机制 D — 坐标点击 / 手势

```java
// 点击手势
Path path = new Path();
path.moveTo(x, y);
dispatchGesture(new GestureDescription.Builder()
    .addStroke(new StrokeDescription(path, 0, 50/*ms*/))
    .build(), null, null);

// 水平滑动（最近任务翻页）
from: (width × 0.80, height × 0.40)
to:   (width × 0.20, height × 0.40)
duration: 400ms

// 垂直滚动
from: (50%w, 65%h) → (50%w, 40%h), 300ms
```

**权限弹窗坐标点击**（按窗口高度分 3 路）：

| 窗口高度 | X 范围 | Y 范围 | 场景 |
|---------|--------|--------|------|
| > 700px | 30%–70% | 74%–80% | 聚合弹窗（多权限） |
| < 600px | 55%–85% | 65%–85% | 单权限弹窗 |
| 中等 | 55%–80% | 60%–80% | 中等大小弹窗 |

---

## 三、按权限逐项详解

### 权限 1 — 基础运行时权限（umrkmgrri 子模块）

| 维度 | 详情 |
|------|------|
| **Android 权限** | Camera, Microphone, Location, Storage, Contacts, Phone, SMS 等全部危险权限 |
| **方法** | `m212323c1` (executeBasicPermissions) |
| **机制** | **C** — 委托 umrkmgrri 子模块 |
| **SP 标记** | 无专用 key（子模块自管理） |
| **SubBrand 差异** | 无 |

**umrkmgrri 异步执行线程模型**：
```
1. umrkmgrri.f55158a3.start(context)     // 启动权限授予服务
2. delay(500ms)
3. 重置点击计数器 f55131c0 = 0
4. new Thread(RunnableC0941o6(type=15, this)).start()  // 独立工作线程
5. 轮询循环（最多 20 次 × 500ms = 10 秒）：
   if (umrkmgrri.isRequestingPermissions()) { 挂起等待 }
6. umrkmgrri.setRequestingPermissions(false)  // 完成后重置
```

`RunnableC0941o6(type=15)` 在独立 Thread 中处理权限对话框点击：
- "步骤1: 启动umrkmgrri"
- "步骤2: 延迟500ms"
- "步骤3: 启动新线程执行点击逻辑"

将 UI 点击拆到独立线程，避免 AccessibilityService 主线程阻塞。

---

### 权限 2 — 电池优化豁免（SubBrand 分发）

| 维度 | 详情 |
|------|------|
| **获取能力** | 厂商电池优化豁免（禁用省电限制、睡眠待机优化、耗电异常优化等） |
| **方法** | `m212337e1` (dispatcher) → SubBrand 分发 |
| **机制** | **E = B + A + D** |
| **SP 标记** | `"battery"` |

#### 2a. OPPO / OPLUS 路径 — `mOppo` (m212339e3)

**四级菜单路径**（核心特征）：
```
设置 → "电池" → "更多设置#高级设置#智能省电场景#更多" (scrollLimit=5)
                  ↓ 逐级 # 分隔导航
                  "更多设置" → "高级设置" → "智能省电场景" → "更多"
```

进入后：
```
→ closeSwitch("睡眠待机优化")           // 关闭
  └─ fallback: closeSwitch("待机耗电优化")
→ clickVWithScroll("耗电异常优化")
→ waitForRootViewCount(3, 1500ms)       // 等待列表加载
→ clickVWithScroll(appName, 25)          // 滚动找到自身
→ clickV("不优化")                      // 选择不优化
→ pressBack × 2

→ closeSwitch("省电模式")               // fallback 关闭省电
```

**UI 目标文本**：
```
"电池","更多设置","高级设置","智能省电场景","更多",
"睡眠待机优化","待机耗电优化","耗电异常优化","不优化","省电模式"
```

#### 2b. OnePlus 路径 — `mOnePlus` (m212338e2)

| SDK 条件 | 路径 |
|---------|------|
| SDK ≥ 36 | 委托 mOppo 流程 |
| SDK 35 (Android 15) | 专用路径（见下） |
| SDK ≤ 34 | 传统路径（见下） |

**Android 15 路径**：
```
设置 → "电池" → "电池模式" → "均衡模式"
→ "省电设置"
→ closeSwitch("自动进入省电模式")
→ closeSwitch("睡眠待机优化")
→ pressBack × 2
```

**传统路径 (SDK ≤ 34)**：
```
应用详情 → toggle "省电模式" OFF
  ├─ 弹窗 "立即关闭" → 点击
  └─ 弹窗 "立即开启" → toggle 关闭
→ "耗电管理"/"电池"
→ clickVWithScroll("高级设置#更多设置", 3)   // 二级菜单
→ closeSwitch("睡眠待机优化")
→ "耗电异常优化" → 滚动找 App → "不优化"
→ pressBack × 2-3
```

**UI 目标文本**：
```
"高级设置","更多设置","睡眠待机优化","耗电异常优化","不优化",
"省电模式","均衡模式","电池模式","省电设置","自动进入省电模式",
"电池优化","耗电管理","立即关闭","立即开启"
```

#### 2c. Realme 路径 — `mRealme` (m212340e4)

| SDK 条件 | 路径 |
|---------|------|
| SDK ≥ 36 | 委托 mOppo 流程 |
| SDK 35 (Android 15) | 专用路径 |
| SDK 29 (Android 10) | Android 10 专用路径 |
| SDK ≤ 34 且 ≠ 29 | 传统路径 |

**Android 15 路径**：
```
设置 → "电池" → "省电设置"
→ closeSwitch("睡眠待机优化")
→ closeSwitch("自动进入省电模式")
→ pressBack × 3
```

**Android 10 路径**：
```
设置 → "电池"
→ closeSwitch("省电模式")
→ "智能省电场景" → closeSwitch("睡眠待机优化")
```

**传统路径 (SDK ≤ 34, ≠ 29)**：
```
设置 → "电池" → "省电模式" (如存在)
→ 禁用以下开关：
    "充电至 90% 自动关闭"
    "设定自动开启电量"
    "超级省电模式"
→ "省电模式优化项"
→ 禁用以下开关：
    "降低屏幕亮度"
    "自动息屏时间调整为15秒"
    "停用后台同步功能"
    "降低屏幕刷新率"
→ 设置 → "电池"
→ clickVWithScroll("更多设置#高级设置#更多", 5)   // 三级菜单
→ "耗电异常优化" → 滚动到 App → "不优化"
  └─ 备选: "待机优化" → "关闭"
→ pressBack × 2
```

**UI 目标文本**：
```
"电池","省电模式","省电设置","智能省电场景","自动进入省电模式",
"睡眠待机优化","更多设置","高级设置","更多",
"耗电异常优化","不优化","待机优化","关闭",
"充电至 90% 自动关闭","设定自动开启电量","超级省电模式",
"省电模式优化项","降低屏幕亮度","自动息屏时间调整为15秒",
"停用后台同步功能","降低屏幕刷新率"
```

---

### 权限 3 — 自启动 + 后台运行

| 维度 | 详情 |
|------|------|
| **获取能力** | 自启动权限 + 后台行为权限（ColorOS 厂商私有） |
| **方法** | `m212318b6` (e, executeBackgroundAndAutoStart) |
| **机制** | **E = B (ComponentName / Intent) + A (4 策略点击)** |
| **SP 标记** | `"autostart_switch"`, `"autostart_background"`, `"autostart"` |
| **SubBrand 差异** | 无（按 SDK 版本分支，不按品牌） |

#### SDK ≥ 35 路径

```
设置 → clickVWithScroll("应用", 5)
→ clickV("自启动#自启动管理")             // 二级菜单
→ waitForRootViewCount(3, 1500ms)
→ clickVWithScroll(appName, 25)          // 滚动找到 App
→ openSwitch(appName)                    // 开启自启动
→ pressBack
→ openAppDetails()                       // 进入应用详情
→ clickV("耗电管理")
→ openSwitch([后台行为文本列表])           // 开启后台权限
→ clickV("允许")
→ pressBack
```

#### SDK < 35 路径

```
openAppDetails()
→ openSwitch(["允许自动启动","允许应用自启动","自动启动","允许自启动","开机自启动"])
→ clickV(["耗电管理","耗电保护","电量消耗","耗电详情","电池"])
```

若自启动开关未找到 → **SafeCenter 直连路径**：
```
依次尝试 5 个 ComponentName:
  com.coloros.safecenter / .StartupAppListActivity
  com.oppo.safe / .StartupAppListActivity
  com.oplus.safecenter / .StartupAppListActivity
  com.coloros.safecenter / .startupapp.view.StartupAppListActivity
  com.oplus.safecenter / .startupapp.view.StartupAppListActivity

验证：前台包名含 "safecenter" 或 "oppo.safe"
→ enableAppAutoStartSwitch(appName)   // 在列表中找到并开启
```

**后台行为开关文本**（按序尝试）：
```
"完全允许后台行为","允许应用后台行为","允许完全后台行为",
"允许后台运行","完全后台行为","后台运行","允许后台活动"
```

确认对话框：`clickV("允许#确定")`

---

### 权限 4 — 悬浮窗（SYSTEM_ALERT_WINDOW）

| 维度 | 详情 |
|------|------|
| **Android 权限** | `android.permission.SYSTEM_ALERT_WINDOW` |
| **方法** | `m212329c7` (executeOverlay) |
| **机制** | **E = B + A + D** |
| **SP 标记** | `"overlay"` |
| **SubBrand 差异** | 无 |

**执行流程**：
1. **前置检查**：`Settings.canDrawOverlays(context)` → 已授权跳过
2. **[B]** `Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", "package:{pkg}")`
3. **[A]** 等待列表加载 → `clickVWithScroll(appName, 25)` 滚动找应用
4. **[A]** 若找到应用（进入详情页）：
   ```
   openSwitch("授予悬浮窗权限")
     → "允许在其他应用上层显示"
     → "在其他应用上层显示"
     → "显示在其他应用上层"
   失败 → findSwitchNode() 直接找页面开关
     → performAction(16) / 父节点 / gestureClick
   最后兜底 → clickV("允许")
   ```
5. **[A]** 若未找到应用：
   ```
   openSwitch("允许显示悬浮窗") / openSwitch("显示悬浮窗") / clickV("允许")
   ```
6. 验证：`Settings.canDrawOverlays()`

---

### 权限 5 — 读取应用列表（ColorOS 独有）

| 维度 | 详情 |
|------|------|
| **Android 权限** | `QUERY_ALL_PACKAGES`（ColorOS 运行时弹窗权限） |
| **方法** | `m212331c9` (executeReadAppList) |
| **机制** | **E = B + A** |
| **SP 标记** | `"applist"` |
| **SubBrand 差异** | 无 |

**SDK 分支**：
- SDK < 31 (Android 11-)：通过 Manifest 声明自动授予 → 直接标记完成
- SDK ≥ 31 + (Android 12 或 Android 14+)：需手动操作

**流程**：
```
openAppDetails() → 权限管理 → 查找"读取应用列表"或等效文本
```

---

### 权限 6 — 所有文件访问

| 维度 | 详情 |
|------|------|
| **Android 权限** | `android.permission.MANAGE_EXTERNAL_STORAGE` (API ≥ 30) |
| **方法** | `m212325c3` (executeFileAccess) |
| **机制** | **E = B + A** |
| **SP 标记** | `"fileaccess"` |
| **SubBrand 差异** | 无 |

**执行流程**：
1. **门控**：SDK < 30 → 跳过；`isExternalStorageManager()` → 跳过
2. **[B]** `Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", "package:{pkg}")`
3. **[A]** 开关文本匹配（按序尝试 `openSwitch`）：
   ```
   "授予所有文件的管理权限"
   "所有文件访问权限"
   "授予管理所有文件的权限"
   "允许访问所有文件"
   "允许管理所有文件"
   ```
4. **[A]** 按钮点击：`"开启"` / `"Enable"` / `"Turn on"`
5. **[A]** 确认对话框（按 Android 版本分支）：

| Android 版本 | 确认按钮文本 |
|-------------|------------|
| 10/11 | `"确定"/"OK"/"允许"/"Allow"/"我知道了"/"Got it"` |
| 12 | `"确定"/"应用"/"允许"` |
| 13 | `"确定"` → 再点 `"允许"` |
| 14/15 | `"允许"/"授予权限"/"确定"` |

6. **验证**：每步后检查 `Environment.isExternalStorageManager()`

---

### 权限 7 — 关闭通知渠道（隐身操作）

| 维度 | 详情 |
|------|------|
| **操作目标** | **关闭**  自身 `"OFF"` 通知渠道 |
| **方法** | `m212327c5` (executeNotificationManagement) |
| **机制** | **E = B + A** |
| **SP 标记** | `"notification"` |
| **SubBrand 差异** | 无 |

**执行流程**：
1. **[B]** `Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")` + extras：
   ```
   "android.provider.extra.APP_PACKAGE" = packageName
   "android.provider.extra.CHANNEL_ID"  = "OFF"
   ```
2. 等待 800ms → 轮询最多 6 次（每次 500ms）检测页面
3. **[A]** `closeSwitch("允许通知")` — 将开关置为 **OFF**
4. 失败 → `collectSwitchNodes()` + `syncGestureClick()` 强制关闭
5. pressBack → 标记完成

> ⚠️ **关键反常行为**：`closeSwitch` 而非 `openSwitch`
---

### 权限 8 — 最近任务锁定

| 维度 | 详情 |
|------|------|
| **获取能力** | 锁定 在最近任务中，防止用户滑掉 |
| **方法** | `m212328c6` (executeOppoRecentTaskLock) |
| **机制** | **E = C (GlobalAction) + A + D** |
| **SP 标记** | `"applock"` |
| **SubBrand 差异** | 无 |

**执行流程**：
1. 启动 自身 Activity
2. **[C]** `performGlobalAction(RECENTS=3)` 打开最近任务
3. **[D]** 水平滑动 (80%w→20%w, 40%h, 400ms)
4. 循环最多 4 次：
   - **[A]** `findAccessibilityNodeInfosByText(appName)` 找 App 卡片
   - **[A]** `findAccessibilityNodeInfosByText("更多")` → 验证 text/desc == `"更多"`
   - **[D]** `gestureClick(rect.centerX, rect.centerY)` 点击"更多"
   - 等待 800ms
   - **[D]** `clickLockButton()` 点击锁定

**锁定按钮文本**（多语言）：
```
锁定: "锁定","鎖定","加锁","Lock","LOCK","잠금","잠그기"
排除: 含"解"或"已"的文本
```

**已锁定检测文本**：
```
"解锁","解鎖","Unlock","UNLOCK","취소 잠금","잠금 해제"
"已锁定","已鎖定","Locked","LOCKED"
```

---

## 四、`#` 分隔符多级菜单路径全览

这是 OPPO 系独有的设计模式——通过 `#` 分隔的字符串驱动逐级菜单导航：

| SubBrand | 路径字符串 | 级数 | scrollLimit | 用途 |
|----------|----------|:---:|:-----------:|------|
| OPPO/OPLUS | `"更多设置#高级设置#智能省电场景#更多"` | **4** | 5 | 电池深度设置 |
| Realme | `"更多设置#高级设置#更多"` | **3** | 5 | 电池深度设置 |
| OnePlus | `"高级设置#更多设置"` | **2** | 3 | 电池深度设置 |
| 通用(SDK≥35) | `"自启动#自启动管理"` | **2** | — | 自启动管理入口 |
| 通用 | `"允许#确定"` | **2** | — | 确认对话框 |

**实现机制**：`clickVWithScroll` 方法内对 `#` 分割，Continuation 状态机逐段处理，每段最多滚动 `scrollLimit` 次。

> `"更多设置#高级设置#智能省电场景#更多"` 四级路径几乎不会出现在正常应用中——这是强 YARA 特征。

---

## 五、SubBrand 差异对照表

| 权限/功能 | OPPO | REALME | ONEPLUS | OPLUS |
|----------|:----:|:------:|:-------:|:-----:|
| 基础权限 | umrkmgrri | umrkmgrri | umrkmgrri | umrkmgrri |
| 电池路径 | mOppo (4 级菜单) | mRealme (3 级 + 省电优化项) | mOnePlus (2 级 + 均衡模式) | 同 mOppo |
| 自启动 | 统一 (SDK 分支) | 统一 | 统一 | 统一 |
| SafeCenter 包名 | coloros/oppo | coloros/oppo | oplus | oplus |
| 悬浮窗 | 统一 | 统一 | 统一 | 统一 |
| 文件访问 | 统一 (SDK 分支) | 统一 | 统一 | 统一 |
| 通知 | 统一 | 统一 | 统一 | 统一 |
| 最近任务锁 | 统一 | 统一 | 统一 | 统一 |
| SDK≥36 电池 | mOppo | 委托 mOppo | 委托 mOppo | mOppo |

**核心观察**：电池路径是唯一存在 SubBrand 差异的权限——因为 OPPO/Realme/OnePlus 三家的 ColorOS 电池管理 UI 在不同版本间差异极大。其他权限全部走统一代码路径。

---

## 六、SDK 版本适配矩阵

| 字段 | 条件 | Android 版本 |
|------|------|------------|
| `f55115a4` | SDK ≥ 29 && < 31 | Android 10/11 |
| `f55116a5` | SDK == 29 | Android 10 |
| `f55117a6` | SDK ≥ 31 && < 33 | Android 12/12L |
| `f55118a7` | SDK == 33 | Android 13 |
| `f55119a8` | SDK ≥ 34 | Android 14+ |

---

## 七、SharedPreferences 持久化标记完整表

> **文件名**: `"oppo_simplified_v6"`（v6 版本号是强特征）

| Key | 对应步骤 | 含义 |
|-----|---------|------|
| `"autostart"` | Step 3 | 自启动+后台整体完成 |
| `"autostart_switch"` | Step 3 子步骤 | 自启动开关已开启 |
| `"autostart_background"` | Step 3 子步骤 | 后台行为已允许 |
| `"battery"` | Step 2 | 电池优化豁免完成 |
| `"overlay"` | Step 4 | 悬浮窗权限完成 |
| `"applist"` | Step 5 | 应用列表权限完成 |
| `"fileaccess"` | Step 6 | 文件访问权限完成 |
| `"notification"` | Step 7 | 通知渠道已关闭 |
| `"applock"` | Step 8 | 最近任务锁定完成 |

---

## 八、权限获取目的分析

| 权限 | 方式 |
|------|------------|
| 基础危险权限 | 摄像头/麦克风/定位/短信/通讯录/通话记录窃取 |
| 电池优化豁免 | 禁用睡眠待机优化+省电模式 → 确保 C2 永不断连 |
| 自启动+后台 | 开机自启 + 永久后台运行 |
| 悬浮窗 | overlay 注入（银行/支付钓鱼覆盖） |
| 应用列表 | 感知设备安装的银行/安全应用（目标选择） |
| 文件访问 | 全文件系统读写（文档/照片/数据库窃取） |
| **关闭**通知 | 隐藏前台服务通知，用户无法发现 |
| 最近任务锁定 | 防止用户滑掉 进程 |

完整权限页面结构(OPPO ColorOS 16)

  应用详情(InstalledAppDetails)
  ├─ 通知管理
  ├─ 权限管理 → PermissionGroupsActivity
  │   ├─ 各 runtime 权限(摄像头/位置/麦克风/通讯录...)
  │   └─ 其他权限 → AppAllPermissionsActivity
  ├─ 耗电管理 → PowerControlActivity  ← Step 2 已走
  ├─ 流量消耗
  ├─ 存储占用
  ├─ 默认打开
  ├─ 管理闲置应用
  ├─ 你可能想找:
  │   ├─ 悬浮窗 → SubSettings(switch_widget, checked=false)  ← Step 4!
  │   └─ 闹钟和提醒 → SubSettings(switch, checked=true)