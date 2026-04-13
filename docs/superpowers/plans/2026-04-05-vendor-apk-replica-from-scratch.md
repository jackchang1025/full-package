# Vendor APK 一比一复刻实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 基于 CFR 反编译输出，在全新 Android 项目中从零重写 vendor APK 的全部业务逻辑，实现功能一比一复刻。

**Architecture:** CFR 反编译输出作为唯一阅读源（vendor 业务代码零空桩，方法体 100% 完整，570 条中文日志）。第三方库（Okio/OkHttp/Gson/BouncyCastle/Conscrypt/Java-WebSocket）通过 Gradle 依赖引入，不重写。vendor 业务代码按模块逐个理解→重写→编译验证→提交。

**Tech Stack:** Android API 21-34, Java 8+, Gradle 8.5 + AGP 8.2.2, OkHttp 4.12.0, Gson 2.10.1, Conscrypt 2.5.2, BouncyCastle 1.70, Java-WebSocket 1.5.4

**CFR 阅读源路径:** `/home/code/php/project/full-package/androidReverseEngineering/src/`

**第三方包映射（不需要重写，用 Gradle 依赖替换）:**

| 混淆包名 | 真实库 | Gradle 依赖 |
|----------|--------|------------|
| a1/ | Okio | `com.squareup.okio:okio:1.17.6` |
| p0/ | OkHttp internal | `com.squareup.okhttp3:okhttp:4.12.0` |
| n1/ | Java-WebSocket (自定义) | 需要重写，vendor 自定义了 WebSocket Server |
| e1/ | WebSocket 接口 | 随 n1/ 一起重写 |
| com/google/json/ | Gson (混淆包名) | `com.google.code.gson:gson:2.10.1` |

**vendor 相关外围包（需要重写）:**

| 混淆包名 | 功能 | 行数 |
|----------|------|------|
| k/ | UiObject 查找辅助 | 4,035 |
| z/ | UiObject 查找条件 | ~800 |
| m/ | Camera/Media 控制 | 859 |
| h/ | Runnable 任务封装 | 2,562 |
| n1/ + e1/ | WebSocket Server | 3,175 |
| r/ | 枚举定义 | 313 |

---

## File Structure

### 新项目目录结构

```
vendor-replica/
├── app/
│   ├── build.gradle
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   └── java/com/guard/wallet/
│       │       ├── MainApplication.java
│       │       ├── MyApp.java
│       │       ├── LockActivity.java
│       │       ├── activity/          # 4 files
│       │       ├── bridge/            # 3 files
│       │       ├── condition/         # 8 files
│       │       ├── entity/            # 24 files
│       │       ├── filter/            # 39 files
│       │       ├── helper/            # 19 files
│       │       ├── http/              # 34+ files
│       │       ├── msg/               # 9 files
│       │       ├── plug/              # 6+ files
│       │       ├── receiver/          # 12 files
│       │       ├── req/               # 55 files
│       │       ├── resp/              # 42 files
│       │       ├── server/            # 7 files (拆分 b.java)
│       │       ├── service/           # 7 files
│       │       ├── stat/              # 3 files
│       │       ├── sync/              # 2 files
│       │       ├── thread/            # 13+ files
│       │       ├── utils/             # 11 files
│       │       ├── engine/            # 36 files (from o/)
│       │       └── websocket/         # from n1/ + e1/
│       └── test/
│           └── java/com/guard/wallet/
│               └── ... (test files per module)
├── build.gradle
├── settings.gradle
└── gradle/
```

---

### Task 0: 新项目搭建

**Files:**
- Create: `vendor-replica/build.gradle`
- Create: `vendor-replica/settings.gradle`
- Create: `vendor-replica/gradle.properties`
- Create: `vendor-replica/app/build.gradle`
- Create: `vendor-replica/app/src/main/AndroidManifest.xml`

- [ ] **Step 1: 创建项目根目录和 Gradle 配置**

```bash
mkdir -p vendor-replica/app/src/main/java/com/guard/wallet
mkdir -p vendor-replica/app/src/test/java/com/guard/wallet
mkdir -p vendor-replica/gradle/wrapper
```

- [ ] **Step 2: 创建 settings.gradle**

```groovy
// vendor-replica/settings.gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "vendor-replica"
include ':app'
```

- [ ] **Step 3: 创建根 build.gradle**

```groovy
// vendor-replica/build.gradle
plugins {
    id 'com.android.application' version '8.2.2' apply false
}
```

- [ ] **Step 4: 创建 app/build.gradle**

```groovy
// vendor-replica/app/build.gradle
plugins {
    id 'com.android.application'
}

android {
    namespace 'com.guard.wallet'
    compileSdk 34

    defaultConfig {
        applicationId "com.guard.wallet"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    // 替代 a1/ (Okio)
    implementation 'com.squareup.okio:okio:1.17.6'
    // 替代 p0/ (OkHttp)
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    // 替代 com/google/json/ (Gson 混淆包名)
    implementation 'com.google.code.gson:gson:2.10.1'
    // 加密库
    implementation 'org.bouncycastle:bcprov-jdk15on:1.70'
    implementation 'org.bouncycastle:bcpkix-jdk15on:1.70'
    // TLS
    implementation 'org.conscrypt:conscrypt-android:2.5.2'
    // WebSocket (基础依赖，vendor 自定义了 Server)
    implementation 'org.java-websocket:Java-WebSocket:1.5.4'
    // Android Support
    implementation 'androidx.core:core:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    // Test
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.3.1'
    testImplementation 'org.robolectric:robolectric:4.11.1'
}
```

- [ ] **Step 5: 创建 AndroidManifest.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE"
        tools:ignore="ProtectedPermissions" />

    <application
        android:name=".MainApplication"
        android:allowBackup="false"
        android:label="@string/app_name"
        android:supportsRtl="true">

        <!-- Activities, Services, Receivers 在后续 Task 中逐步添加 -->

    </application>
</manifest>
```

- [ ] **Step 6: 创建 gradle.properties**

```properties
android.useAndroidX=true
org.gradle.jvmargs=-Xmx2048m
```

- [ ] **Step 7: 复制 Gradle Wrapper**

```bash
cp -r /home/code/php/project/full-package/android/gradle/wrapper vendor-replica/gradle/
cp /home/code/php/project/full-package/android/gradlew vendor-replica/
cp /home/code/php/project/full-package/android/gradlew.bat vendor-replica/
```

- [ ] **Step 8: 编译验证**

Run: `cd vendor-replica && ./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 9: 提交**

```bash
cd vendor-replica
git init
git add -A
git commit -m "feat: 初始化 vendor-replica 项目骨架"
```

---

### Task 1: 数据类 — entity/ (24 files, 6390 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/entity/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/entity/` (24 files)

字段名全部可读（ADBConfig, UiObject, Point, CommandResult 等），直接从 CFR 输出翻译为干净 Java。

**文件清单:** ADBConfig, ADBKey, AdbShellResult, BuildConfig, CacheResponseKey, CheckPortResult, CheckedResult, CommandResult, CookieVO, DeviceCipher, DistanceTouchNode, HostCookies, LangDialog, NoticeRootChangedVO, PairPortAndCodeResult, Point, ProcessInfo, ReadScreenNodeInfo, ReadScreenWindow, RootInActiveWindowResult, TakeScreenShotResult, UiObject, UiObjectCollection, WIFIState

**工作方法:**
1. 打开 CFR 源文件，阅读字段和方法
2. 在新项目中创建同名类，保留包名 `com.guard.wallet.entity`
3. 复制字段定义、构造函数、getter/setter
4. 清理 CFR 噪音（多余的 cast、var 命名改为语义名）
5. 将 `a1.q` 引用替换为 Okio API，`com.google.json.Gson` 替换为 `com.google.gson.Gson`

- [ ] **Step 1: 创建简单 POJO 类（无外部依赖的 15 个文件）**

从以下文件开始（纯数据类，无复杂依赖）:
ADBConfig, ADBKey, AdbShellResult, CheckPortResult, CheckedResult, CommandResult, CookieVO, DeviceCipher, DistanceTouchNode, HostCookies, LangDialog, PairPortAndCodeResult, Point, ProcessInfo, WIFIState

每个文件的模式相同，以 ADBConfig 为例:

```java
// vendor-replica/app/src/main/java/com/guard/wallet/entity/ADBConfig.java
package com.guard.wallet.entity;

import java.io.Serializable;

public class ADBConfig implements Serializable {
    private int connectErrorCount;
    private boolean connected;
    private String connectedDevice;
    private Integer debugPort;
    private int enableDebug;
    private int enableDevelopment;
    private int enableWifiDebug;
    private int installedRatHat;
    private int isRatHatRunning;
    private boolean paired;
    private long updateTime;

    public ADBConfig() {}

    public ADBConfig(int enableDevelopment, int enableDebug, int enableWifiDebug,
                     boolean paired, boolean connected, int connectErrorCount,
                     Integer debugPort, String connectedDevice,
                     int installedRatHat, int isRatHatRunning) {
        this.enableDevelopment = enableDevelopment;
        this.enableDebug = enableDebug;
        this.enableWifiDebug = enableWifiDebug;
        this.paired = paired;
        this.connected = connected;
        this.connectErrorCount = connectErrorCount;
        this.debugPort = debugPort;
        this.connectedDevice = connectedDevice;
        this.installedRatHat = installedRatHat;
        this.isRatHatRunning = isRatHatRunning;
    }

    // getter/setter for each field...
}
```

- [ ] **Step 2: 编译验证简单 POJO**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 创建复杂 entity 类（有内部依赖的 9 个文件）**

UiObject, UiObjectCollection, ReadScreenNodeInfo, ReadScreenWindow, RootInActiveWindowResult, TakeScreenShotResult, NoticeRootChangedVO, CacheResponseKey, BuildConfig

这些类引用了其他 entity 或 Android API（AccessibilityNodeInfo 等）。按依赖顺序创建:
1. Point → UiObject → UiObjectCollection
2. ReadScreenNodeInfo → ReadScreenWindow
3. 其余独立

- [ ] **Step 4: 编译验证全部 entity**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add app/src/main/java/com/guard/wallet/entity/
git commit -m "feat: entity/ 数据类 24 files"
```

---

### Task 2: 数据类 — req/ (55 files, 3696 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/req/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/req/` (55 files)

全部是请求 VO，字段名可读。模式与 entity/ 相同。

**文件清单:** AdminAdminActivatingVO, ApiRequest, AppLocateValueVO, BatteryLevelVO, BlockViewVO, BootEventVO, ContainerEventVO, DeviceCipherStateVO, DeviceTokenVO, DeviceUpdateVO, EventSubscribe, HeartBodyVO, ListenPropResponse, ListenResponseVO, ListenWindow, LocalDebugEventVO, LockPatternVO, MatchListenWindowVO, MessageBodyVO, MessageRecordVO, NavigateWifiSettingDialogVO, NetStateVO, NotificationDialogVO, PasswordEventBodyVO, PermissionRequestVO, PermissionResponseVO, QueryAgentFileVO, ReqADBPairVO, ReqAdbInstallVO, ReqAdbPushVO, ReqAdbShellVO, ReqAppLocateValueVO, ReqCacheTaskBodyVO, ReqDefaultBodyVO, ReqDeleteFileVO, ReqDownloadFileVO, ReqListenHelper, ReqListenWindowVO, ReqMessageVO, ReqMonitorLocationVO, ReqNoticeAliveVO, ReqOpenWifiDebugVO, ReqResetAccessibilityService, ReqSendSMSVO, ReqSmsRecognizePlugVO, ReqStartApp, ReqUnlockDeviceVO, ReqWifiSettingDialogVO, RequestCommand, RewriteDebugPortVO, ScreenMetricsVO, SearchFieldVO, TouchEvent, UploadAppIconVO, UploadFileVO

- [ ] **Step 1: 批量创建 req/ 类**

每个文件模式相同: package + fields + constructors + getters/setters + toString。
以 ReqSendSMSVO 为例:

```java
package com.guard.wallet.req;

import java.io.Serializable;

public class ReqSendSMSVO implements Serializable {
    private String phoneNumber;
    private String content;

    public ReqSendSMSVO() {}
    public ReqSendSMSVO(String phoneNumber, String content) {
        this.phoneNumber = phoneNumber;
        this.content = content;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
```

- [ ] **Step 2: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 提交**

```bash
git add app/src/main/java/com/guard/wallet/req/
git commit -m "feat: req/ 请求 VO 55 files"
```

---

### Task 3: 数据类 — resp/ (42 files, 4520 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/resp/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/resp/` (42 files)

全部是响应 VO，字段名可读。

**文件清单:** ApiResult, AppInfo, AttachFileVO, BackAppStateVO, CacheTaskResponseVO, CacheTaskVO, CallMessageVO, CallStateVO, ContactsBodyVO, DeviceAdminVO, DeviceAgentFileVO, DeviceContactInfoVO, DeviceContactNumberVO, DeviceDebugVO, DeviceInfoVO, DeviceKeepAliveVO, DeviceLocationVO, DeviceMediaStoreImageVO, DeviceNotificationVO, DevicePairStateVO, DeviceRecordStateVO, DeviceSmsRecognizeVO, DeviceWalletAuthStrategyVO, MainUninstallPolicyVO, MessageGroupVO, PackagesBodyVO, PairResponseVO, PermissionInfoVO, PermissionsBodyVO, PowerControlStateVO, PushResponseVO, ResStartApp, RespCipherStateVO, RespDeleteFileVO, RespDownloadFileVO, SearchNodeListResultVO, SearchNodeResultVO, SmsMessageVO, SmsRecognizePlug, SmsRecognizeRespVO, SyncSmsBodyVO, UiObjectVO

- [ ] **Step 1: 批量创建 resp/ 类**

模式同 req/。注意 ApiResult 是通用响应包装:

```java
package com.guard.wallet.resp;

public class ApiResult<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.data = data;
        return result;
    }

    public static ApiResult<?> error(String message) {
        ApiResult<?> result = new ApiResult<>();
        result.code = 500;
        result.message = message;
        return result;
    }
    // getters/setters...
}
```

- [ ] **Step 2: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 提交**

```bash
git add app/src/main/java/com/guard/wallet/resp/
git commit -m "feat: resp/ 响应 VO 42 files"
```

---

### Task 4: 数据类 — msg/ + stat/ + sync/ + bridge/ (17 files, 798 lines)

**CFR 阅读源:**
- `androidReverseEngineering/src/com/guard/wallet/msg/` (9 files)
- `androidReverseEngineering/src/com/guard/wallet/stat/` (3 files)
- `androidReverseEngineering/src/com/guard/wallet/sync/` (2 files)
- `androidReverseEngineering/src/com/guard/wallet/bridge/` (3 files)

- [ ] **Step 1: 创建 msg/ 消息体类**

BaseMsgBody, BridgeBody, BridgeBufferBody, BridgeBufferMessage, BridgeHttpMessage, BridgeMessage, ReadEventMessage, ReadScreenEvent, ReadScreenMessage

- [ ] **Step 2: 创建 stat/ 统计类**

AccessibilityEventStatVO, KeyboardEventVO, ScreenEventStatVO

- [ ] **Step 3: 创建 sync/ 同步类**

StubProvider (ContentProvider), SyncService (AbstractThreadedSyncAdapter)

- [ ] **Step 4: 创建 bridge/ 桥接类**

WebSocketBridge 及其内部类

- [ ] **Step 5: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: 提交**

```bash
git add app/src/main/java/com/guard/wallet/{msg,stat,sync,bridge}/
git commit -m "feat: msg/ stat/ sync/ bridge/ 数据类 17 files"
```

---

### Task 5: 枚举与基础类型 — r/ 包 + 顶层文件

**CFR 阅读源:**
- `androidReverseEngineering/src/r/` (枚举定义, 313 lines)
- `androidReverseEngineering/src/com/guard/wallet/MainApplication.java`
- `androidReverseEngineering/src/com/guard/wallet/MyApp.java`
- `androidReverseEngineering/src/com/guard/wallet/LockActivity.java`

- [ ] **Step 1: 创建枚举类**

r/ 包含引擎状态枚举等，重命名为语义包名 `com.guard.wallet.enums/`

- [ ] **Step 2: 创建 MainApplication 和 MyApp**

从 CFR 源阅读初始化逻辑，重写为干净 Java。

- [ ] **Step 3: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/guard/wallet/enums/
git add app/src/main/java/com/guard/wallet/MainApplication.java
git add app/src/main/java/com/guard/wallet/MyApp.java
git add app/src/main/java/com/guard/wallet/LockActivity.java
git commit -m "feat: 枚举定义 + Application 入口"
```

---

### Task 6: UI 自动化框架 — condition/ (8 files, 1103 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/condition/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/condition/` (8 files)

类名全部可读，是 AccessibilityNodeInfo 匹配条件的定义。

**文件清单:** ActionValueCondition, BoolCondition, BoundsCondition, GlobalActionCondition, IntCondition, PointCondition, StringCondition, TargetActionCondition

- [ ] **Step 1: 创建 condition/ 类**

每个 Condition 类定义一种匹配规则。以 StringCondition 为例:

```java
package com.guard.wallet.condition;

import java.io.Serializable;

public class StringCondition implements Serializable {
    private String field;
    private String value;
    private int matchType; // equals, contains, startsWith, endsWith, matches

    public StringCondition() {}
    public StringCondition(String field, String value, int matchType) {
        this.field = field;
        this.value = value;
        this.matchType = matchType;
    }
    // getters/setters...
}
```

其余 7 个类模式相同，从 CFR 源逐个翻译。

- [ ] **Step 2: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 提交**

```bash
git add app/src/main/java/com/guard/wallet/condition/
git commit -m "feat: condition/ UI 匹配条件 8 files"
```

---

### Task 7: UI 自动化框架 — filter/ (39 files, 1483 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/filter/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/filter/` (39 files)
**依赖:** Task 6 (condition/)

类名全部可读。filter/ 是基于 condition/ 的 AccessibilityNodeInfo 过滤器实现。

**文件清单:** Filter (接口), Selector, BooleanFilter, BoundsFilter, PointFilter, IntFilter, IntEqualsFilter, IntNotEqualsFilter, IntGreaterThanFilter, IntGreaterThanOrEqualFilter, IntLessThanFilter, IntLessThanOrEqualFilter, IntFilters, StringContainsFilter, StringEndsWithFilter, StringEqualsFilter, StringMatchesFilter, StringStartsWithFilter, TextFilters, IdFilters, DescFilters, ClassNameFilters, HintTextFilters, TooltipFilters, PanelTitleFilters, RoleDescFilters, StateDescFilters, UniqueIdFilters, PackageNameFilters, WindowTitleFilters, WindowTitleContainsFilter, WindowTitleEndsWithFilter, WindowTitleEqualFilter, WindowTitleMatchesFilter, WindowTitleStartsWithFilter, CombineFilter, CombineFilterWithChild, CombineFilterWithUpLevel, CombineFiltersWithOr

**工作方法:**
1. 先创建 Filter 接口和 Selector 基类
2. 再创建简单 Filter（Boolean, Bounds, Point, Int 系列, String 系列）
3. 最后创建 Combine 系列（依赖前面的 Filter）

- [ ] **Step 1: 创建 Filter 接口 + Selector**

```java
package com.guard.wallet.filter;

import android.view.accessibility.AccessibilityNodeInfo;

public interface Filter {
    boolean match(AccessibilityNodeInfo node);
}
```

- [ ] **Step 2: 创建简单 Filter 实现（30 个文件）**

Int 系列 (6 个)、String 系列 (5 个)、属性 Filters (10 个)、Window 系列 (5 个)、其余 (4 个)

- [ ] **Step 3: 创建 Combine 系列（4 个文件）**

CombineFilter, CombineFilterWithChild, CombineFilterWithUpLevel, CombineFiltersWithOr

这些是核心类，组合多个 condition 进行复合匹配。从 CFR 源逐行翻译。

- [ ] **Step 4: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add app/src/main/java/com/guard/wallet/filter/
git commit -m "feat: filter/ UI 过滤器 39 files"
```

---

### Task 8: UI 自动化框架 — helper/ (19 files, 1822 lines) + k/ + z/

**CFR 阅读源:**
- `androidReverseEngineering/src/com/guard/wallet/helper/` (19 files)
- `androidReverseEngineering/src/k/a.java` (4035 lines, UiObject 查找辅助)
- `androidReverseEngineering/src/z/` (~800 lines, UiObject 查找条件)
**依赖:** Task 1 (entity/UiObject), Task 7 (filter/)

helper/ 部分方法名混淆（a-r 单字母），但功能可从上下文推断。k/ 和 z/ 是 UiObject 操作的核心辅助类。

**工作方法:**
1. 先读 k/a.java — 这是 UiObject 查找引擎，所有 findBy/scrollUtil 方法都在这里
2. 读 z/ — 查找条件构造器
3. 读 helper/ — 逐个文件理解功能，重命名混淆方法
4. k/ 重命名为 `com.guard.wallet.helper.UiObjectFinder`
5. z/ 重命名为 `com.guard.wallet.helper.SearchCondition`

- [ ] **Step 1: 重写 k/a.java → UiObjectFinder**

k/a.java 是 4035 行的核心类，包含所有 AccessibilityNodeInfo 查找方法。
从 CFR 源阅读，按功能分组重写:
- findByXxx 系列
- findOneByXxx 系列
- findLastByXxx 系列
- scrollForwardUtil / scrollBackwardUtil 系列
- findParentUtilCombine

- [ ] **Step 2: 重写 z/ → SearchCondition**

z/d.java 是查找条件构造器，被 o/ 引擎层大量使用。

- [ ] **Step 3: 重写 helper/ 19 个文件**

LockCipherHelper 类名可读。a-r 单字母文件需要从 CFR 源阅读后重命名:
- helper/g.java: 包含 `h(int millis)` 方法 = sleep 工具
- 其余从调用上下文推断

- [ ] **Step 4: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add app/src/main/java/com/guard/wallet/helper/
git commit -m "feat: helper/ + UiObjectFinder + SearchCondition"
```

---

### Task 9: 网络层 — http/ (34+ files, 2315 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/http/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/http/` (34+ files)
**依赖:** Task 1-3 (entity/req/resp)

http/ 是 OkHttp 封装层。类名部分可读（各种 Callback），部分混淆（a-z 单字母）。
有 6 个 goto 需要人工理解控制流后重写。

**可读类名:** HttpUtils, AppLocateValuesCallback, CloseADBDebugCallback, CloseDevelopmentCallback, CloseWifiDebugCallback, ContactsCallback, DeviceIdCallback, DeviceOwnerCallback, DeviceSmsRecognizeCallback, DeviceUpdateCallback, GetCacheTaskCallback, ListenWindowCallback, NavigateWifiDialogContentCallback, NoCompleteWalletCallback, OpenADBDebugCallback, OpenDevelopmentCallback, OpenWifiDebugCallback, PackagesCallback, PostMessageCallback, QueryAgentFileCallback, QueryPairKeyCallback, RegisterCallback, ResetWifiDebugCallback, ServerLockCipherCallback, ShareADBConfigCallback, SmsRecognizePlugCallback, SyncSmsCallback, UploadAppIconCallback, UploadCipherCallback, UploadPairKeyCallback, UploadStoreFileCallback

**混淆类名 (a-z):** 需要从 CFR 源阅读后重命名

- [ ] **Step 1: 创建 HttpUtils 核心类**

HttpUtils 是 OkHttp 封装，包含 GET/POST 方法。将 `a1.q` 引用替换为 Okio API，`p0` 引用替换为 OkHttp API。

- [ ] **Step 2: 创建各 Callback 类**

30+ 个 Callback 类，每个对应一个 HTTP 接口。模式统一:
1. 构造请求参数
2. 调用 HttpUtils 发送
3. 解析响应

- [ ] **Step 3: 重写混淆类 a-z**

从 CFR 源阅读，根据功能重命名。

- [ ] **Step 4: 处理 6 个 goto**

逐个阅读 goto 所在方法，理解控制流后用正常 Java 重写。

- [ ] **Step 5: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: 提交**

```bash
git add app/src/main/java/com/guard/wallet/http/
git commit -m "feat: http/ 网络通信层"
```

---

### Task 10: 广播接收器 — receiver/ (12 files, 1069 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/receiver/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/receiver/` (12 files)

类名全部可读。

**文件清单:** AlarmReceiver, BatteryLevelReceiver, BootBroadcast, CallReceiver, CustomAdminReceiver, LocaleChangeReceiver, NetWorkReceiver, PackageReceiver, PowerBroadcastReceiver, ScreenBroadcastReceiver, ShutDownBroadcastReceiver, SmsReceiver

- [ ] **Step 1: 逐个创建 Receiver 类**

每个 Receiver 继承 BroadcastReceiver，重写 onReceive()。从 CFR 源翻译。

- [ ] **Step 2: 在 AndroidManifest.xml 中注册**

```xml
<receiver android:name=".receiver.BootBroadcast">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
<!-- 其余 receiver 同理 -->
```

- [ ] **Step 3: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/guard/wallet/receiver/
git commit -m "feat: receiver/ 广播接收器 12 files"
```

---

### Task 11: Activity + plug/ (10 files, 1226 lines)

**CFR 阅读源:**
- `androidReverseEngineering/src/com/guard/wallet/activity/` (4 files)
- `androidReverseEngineering/src/com/guard/wallet/plug/` (6+ files)

**activity/ 文件清单:** ConfirmDeviceActivity, GuideActivity, MainActivity, NoDisplayActivity
**plug/ 文件清单:** CrackLockCipherPlug 及其内部类, a-f 混淆类

- [ ] **Step 1: 创建 Activity 类**

4 个 Activity，从 CFR 源翻译。在 AndroidManifest.xml 中注册。

- [ ] **Step 2: 创建 plug/ 类**

CrackLockCipherPlug 类名可读（锁屏密码破解插件）。a-f 从 CFR 源阅读后重命名。

- [ ] **Step 3: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/guard/wallet/{activity,plug}/
git commit -m "feat: activity/ + plug/"
```

---

### Task 12: 核心服务 — service/ (7 files, 2656 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/service/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/service/` (7 files)
**依赖:** Task 1-8 (entity, filter, helper)

类名全部可读。MyAccessibilityService 是整个系统的核心。

**文件清单:** AccessibilityDelegateManager, AccountAuthenticatorService, CustomNotificationService, LocalHotspotService, MediaLiveService, MyAccessibilityService, WIFIBackgroundService

**难点:** 4 个 goto 在 MyAccessibilityService 中，需要人工理解控制流。

- [ ] **Step 1: 创建 MyAccessibilityService**

这是最核心的服务，处理所有 AccessibilityEvent。从 CFR 源逐方法翻译:
- onAccessibilityEvent() — 事件分发
- onServiceConnected() — 服务绑定
- 各种 delegate 调用

- [ ] **Step 2: 创建 AccessibilityDelegateManager**

管理所有 Accessibility 委托的注册和分发。

- [ ] **Step 3: 创建其余 5 个 Service**

AccountAuthenticatorService, CustomNotificationService, LocalHotspotService, MediaLiveService, WIFIBackgroundService

- [ ] **Step 4: 在 AndroidManifest.xml 中注册**

```xml
<service android:name=".service.MyAccessibilityService"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:exported="true">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config" />
</service>
```

- [ ] **Step 5: 处理 4 个 goto**

逐个阅读 goto 所在方法，理解控制流后用正常 Java 重写。

- [ ] **Step 6: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 7: 提交**

```bash
git add app/src/main/java/com/guard/wallet/service/
git commit -m "feat: service/ 核心服务 7 files"
```

---

### Task 13: 线程管理 — thread/ (13+ files, 1912 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/thread/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/thread/` (13+ files)
**依赖:** Task 9 (http/), Task 12 (service/)

**可读类名:** HandlerMsgAndTimer, KeepHeartThread
**混淆类名:** a-m (需要从 CFR 源阅读后重命名)

**难点:** 37 个 goto，线程时序关键。

- [ ] **Step 1: 创建 KeepHeartThread（心跳线程）**

从 CFR 源阅读 KeepHeartThread，这是与服务端保持连接的核心线程。

- [ ] **Step 2: 创建 HandlerMsgAndTimer（消息定时器）**

Handler + Timer 组合，处理定时任务。

- [ ] **Step 3: 重写混淆类 a-m**

从 CFR 源逐个阅读，根据功能重命名。每个文件:
1. 阅读 CFR 源码，理解线程职责
2. 用中文日志辅助理解
3. 重命名为语义名
4. 用正常 Java 重写 goto 控制流

- [ ] **Step 4: 处理 37 个 goto**

thread/ 是 goto 第二多的模块。逐个方法理解控制流:
- goto 通常是 try-catch 嵌套或 loop-with-break 的反编译产物
- 用 while/for + break/continue 重写
- 必要时用 Frida hook 验证线程行为

- [ ] **Step 5: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: 提交**

```bash
git add app/src/main/java/com/guard/wallet/thread/
git commit -m "feat: thread/ 线程管理"
```

---

### Task 14: 工具类 — utils/ (11 files, 4719 lines)

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/utils/`
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/utils/` (11 files)

**可读类名:** GsonUtils, ListenWindowUtils, LocateValuesUtils, MessageUtils, SharedUtils, SmsRecognizePlugUtils
**混淆类名:** a-k (需要从 CFR 源阅读后重命名)

**难点:** 16 个 goto，体量大（4719 行）。

- [ ] **Step 1: 创建可读名工具类**

GsonUtils — Gson 序列化/反序列化封装，将 `com.google.json.Gson` 替换为 `com.google.gson.Gson`
SharedUtils — SharedPreferences 封装
MessageUtils — 消息处理工具
ListenWindowUtils — 窗口监听工具
LocateValuesUtils — 定位值工具
SmsRecognizePlugUtils — 短信识别插件工具

- [ ] **Step 2: 重写混淆类 a-k**

从 CFR 源逐个阅读，根据功能重命名。

- [ ] **Step 3: 处理 16 个 goto**

逐个方法理解控制流后用正常 Java 重写。

- [ ] **Step 4: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add app/src/main/java/com/guard/wallet/utils/
git commit -m "feat: utils/ 工具类 11 files"
```

---

### Task 15: WebSocket Server — n1/ + e1/ (重写为 websocket/)

**CFR 阅读源:**
- `androidReverseEngineering/src/n1/` (2 files, 1604 lines)
- `androidReverseEngineering/src/e1/` (4 files, 1571 lines)
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/websocket/`
**依赖:** Task 9 (http/)

n1/ 是 vendor 自定义的 WebSocket Server（基于 Java-WebSocket 库扩展）。
e1/ 是 WebSocket 连接接口定义。
server/c.java 是 WebSocket Server 的入口（已在 Task 16 中处理）。

- [ ] **Step 1: 创建 WebSocket 接口**

从 e1/ 翻译接口定义:
- WebSocketConnection (e1/b.java) — send, close, onMessage
- WebSocketListener — onOpen, onClose, onError

- [ ] **Step 2: 创建 WebSocket Server 实现**

从 n1/b.java 翻译 Server 实现（1481 行）:
- 继承 Java-WebSocket 的 WebSocketServer
- 处理连接管理、消息路由
- 中文日志: "MyWebSocketServer onMessage", "WebSocket onOpen minicap"

- [ ] **Step 3: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/guard/wallet/websocket/
git commit -m "feat: websocket/ WebSocket Server"
```

---

### Task 16: Camera/Media — m/ + h/ (重写为 media/)

**CFR 阅读源:**
- `androidReverseEngineering/src/m/` (6 files, 859 lines) — Camera2 API 封装
- `androidReverseEngineering/src/h/` (5 files, 2562 lines) — Runnable 任务封装
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/media/`

- [ ] **Step 1: 创建 Camera 控制类**

从 m/ 翻译 Camera2 API 封装:
- CameraController — 前后摄像头控制
- CaptureCallback — 拍照回调
- MediaRecorderHelper — 录音/录屏

- [ ] **Step 2: 创建 Runnable 任务类**

从 h/ 翻译任务封装（h/ 引用了 vendor 代码，是业务相关的 Runnable）。

- [ ] **Step 3: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/guard/wallet/media/
git commit -m "feat: media/ Camera + Media 控制"
```

---

### Task 17: HTTP 路由核心 — server/b.java 拆分重写

**CFR 阅读源:** `androidReverseEngineering/src/com/guard/wallet/server/b.java` (28,071 lines, 244 methods)
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/server/` (拆分为多个文件)
**依赖:** Task 1-16 (几乎所有模块)

**突破策略:** 路由字符串→方法映射。不需要猜 244 个混淆方法名。

**路由分发结构:**
- `z3()` — 主路由分发器，190 条路由的 switch-case
- `X1()` — 复杂操作分发器，63 条路由
- `r1()` — 简单操作分发器，22 条路由
- `g2()` — 单独处理 `/api/pairKeyFile/query.json`

**拆分方案:** 将 28,071 行的单文件拆分为按功能分组的多个 Handler:

```
server/
├── HttpServer.java          — 服务器启动/停止 (from server/a.java)
├── WebSocketServer.java     — WebSocket 入口 (from server/c.java)
├── RouteDispatcher.java     — 路由分发 (from z3/X1/r1)
├── DeviceInfoHandler.java   — /info, /version, /deviceId, /batteryState 等
├── GlobalActionHandler.java — /global/* 系列 (15 条)
├── TargetFindHandler.java   — /target/findBy* 系列 (~60 条, 模板化)
├── TargetScrollHandler.java — /target/scroll* 系列 (~30 条, 模板化)
├── TargetActionHandler.java — /target/action, /target/refresh, /target/matchListenWindow
├── AdbHandler.java          — /localAdbShell, /localAdbPair, /localAdbPush 等
├── SyncHandler.java         — /sync* 系列 (~15 条)
├── AppHandler.java          — /install, /startApp, /killApp, /browserApps 等
├── MediaHandler.java        — /screenshot, /screenrecord/*, /frontCameraLive 等
├── PermissionHandler.java   — /permissions, /requestPermission, /deviceAdmin 等
├── SettingsHandler.java     — /startSettings, /startDevSetting, /enableWifiDebug 等
└── MiscHandler.java         — 其余路由
```

- [ ] **Step 1: 创建 RouteDispatcher**

从 z3() 的 switch-case 提取路由→Handler 映射:

```java
package com.guard.wallet.server;

public class RouteDispatcher {
    public static String dispatch(String path, String body) {
        switch (path) {
            case "/info": return DeviceInfoHandler.handleInfo();
            case "/version": return DeviceInfoHandler.handleVersion();
            case "/sendSms": return MiscHandler.handleSendSms(body);
            // ... 按路由分发到各 Handler
        }

        if (path.startsWith("/global/")) {
            return GlobalActionHandler.dispatch(path, body);
        }
        if (path.startsWith("/target/")) {
            return TargetDispatcher.dispatch(path, body);
        }
        if (path.startsWith("/sync")) {
            return SyncHandler.dispatch(path, body);
        }

        return ApiResult.error("Unknown route: " + path).toJson();
    }
}
```

- [ ] **Step 2: 创建 GlobalActionHandler (/global/* 15 条)**

```java
package com.guard.wallet.server;

public class GlobalActionHandler {
    public static String dispatch(String path, String body) {
        switch (path) {
            case "/global/action": return handleAction(body);
            case "/global/lockScreen": return handleLockScreen();
            case "/global/wakeUpScreen": return handleWakeUpScreen();
            case "/global/moveHome": return handleMoveHome();
            case "/global/moveEnd": return handleMoveEnd();
            case "/global/copy": return handleCopy();
            case "/global/paste": return handlePaste(body);
            case "/global/setText": return handleSetText(body);
            case "/global/delete": return handleDelete();
            case "/global/clear": return handleClear();
            case "/global/keepScreenOn": return handleKeepScreenOn(body);
            case "/global/execCommand": return handleExecCommand(body);
            default: return ApiResult.error("Unknown global: " + path).toJson();
        }
    }
    // 每个 handle 方法从 CFR 源对应的混淆方法翻译
}
```

- [ ] **Step 3: 创建 TargetFindHandler (/target/findBy* ~60 条, 模板化)**

/target/findBy* 系列是模板化的，只有查找条件不同:

```java
package com.guard.wallet.server;

public class TargetFindHandler {
    // 模板方法
    private static String findBy(String body, FindMode mode, MatchType matchType) {
        // 1. 解析 body 为 SearchFieldVO
        // 2. 根据 mode (findBy/findOneBy/findLastBy) 和 matchType (equals/contains/startsWith/endsWith/matches)
        // 3. 调用 UiObjectFinder 执行查找
        // 4. 返回 SearchNodeResultVO / SearchNodeListResultVO
    }

    public static String dispatch(String path, String body) {
        // findByText, findByTextContains, findByTextStartsWith...
        // findById, findByIdContains...
        // findByDesc, findByClassName...
        // 全部映射到 findBy() 模板方法，只是参数不同
    }
}
```

理解一个模式后批量生成 ~60 条路由。

- [ ] **Step 4: 创建 TargetScrollHandler (/target/scroll* ~30 条, 模板化)**

同理，scroll 系列也是模板化的:

```java
package com.guard.wallet.server;

public class TargetScrollHandler {
    private static String scrollUtil(String body, boolean forward, boolean multiple, FilterType filterType) {
        // 1. 解析 body
        // 2. 根据方向(forward/backward) + 是否多次(multiple) + 过滤器类型
        // 3. 调用 UiObjectFinder 执行滚动查找
        // 4. 返回结果
    }
}
```

- [ ] **Step 5: 创建 DeviceInfoHandler**

/info, /version, /deviceId, /batteryState, /lockState, /screenState, /netState, /callState, /containerState, /accessibilityState, /recordState, /pairState 等

- [ ] **Step 6: 创建 AdbHandler**

/localAdbShell, /localAdbPair, /localAdbPush, /localAdbConnect, /syncADBConfig, /shareADBConfig, /requestLocalAdbPair 等

- [ ] **Step 7: 创建 SyncHandler**

/syncSms, /syncContacts, /syncPhotos, /syncVideos, /syncAudios, /syncPackages, /syncPermissions, /syncPowerControl, /syncWindows, /syncDownload, /syncLockCipher, /syncCanWriteSecure, /syncSmsRecognizePlug, /syncAdminActivating

- [ ] **Step 8: 创建 AppHandler**

/install, /startApp, /killApp, /browserApps, /startInstallApp, /finishInstallApp, /prepareInstallApp, /startAppFromDesktop, /uploadAppIcon

- [ ] **Step 9: 创建 MediaHandler**

/screenshot/0, /screenrecord/start, /screenrecord/stop, /screenrecord/state, /frontCameraLive, /backCameraLive, /stopCameraLive, /miniCap/scale

- [ ] **Step 10: 创建 PermissionHandler + SettingsHandler + MiscHandler**

剩余路由按功能分组。

- [ ] **Step 11: 创建 HttpServer (from server/a.java)**

HTTP 服务器启动/停止/端口管理。

- [ ] **Step 12: 创建 WebSocketServerWrapper (from server/c.java)**

WebSocket Server 入口，端口 7900/7980。

- [ ] **Step 13: 处理 7 个 goto**

server/ 中 7 个 goto 集中在复杂路由处理方法中，逐个理解后重写。

- [ ] **Step 14: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 15: 提交**

```bash
git add app/src/main/java/com/guard/wallet/server/
git commit -m "feat: server/ HTTP 路由核心 (239 routes, 拆分为 15 Handler)"
```

---

### Task 18: 引擎层 — o/ 重写为 engine/ (36 files, 24234 lines)

**CFR 阅读源:** `androidReverseEngineering/src/o/` (36 files)
**Create:** `vendor-replica/app/src/main/java/com/guard/wallet/engine/` (36 files)
**依赖:** Task 8 (helper/UiObjectFinder), Task 12 (service/MyAccessibilityService)

**已有映射:**

| CFR 文件 | 厂商 | 重命名 |
|----------|------|--------|
| o/n.java | 华为启动管理 | HuaweiStartupEngine |
| o/m.java | 华为 Runnable | HuaweiStartupRunnable |
| o/q.java | 小米 | XiaomiEngine |
| o/v.java | OPPO ColorOS | OppoEngine |
| o/i0.java | vivo | VivoEngine |
| o/e0.java | 传音 | TranssionEngine |
| o/g.java | AOSP | AospEngine |
| o/a0.java | 引擎基类 (2003 lines) | BaseKeepAliveEngine |
| o/e.java | 核心引擎 (982 lines) | EngineCore |
| o/c.java | 权限引擎 (801 lines) | PermissionEngine |
| o/t.java | 引擎调度 (677 lines) | EngineScheduler |

**突破口:** 448 条中文日志 = 每个方法的功能文档。

- [ ] **Step 1: 创建 BaseKeepAliveEngine (from o/a0.java, 2003 lines)**

引擎基类，定义所有厂商引擎的公共接口和默认行为。
中文日志抽样: "准备结束本地保活自动化引擎", "已结束本地保活自动化引擎"

从 CFR 源逐方法翻译:
- 生命周期方法 (start, stop, pause, resume)
- 窗口匹配方法
- 状态管理方法

- [ ] **Step 2: 创建 EngineCore (from o/e.java, 982 lines)**

核心引擎逻辑。

- [ ] **Step 3: 创建 PermissionEngine (from o/c.java, 801 lines)**

权限请求引擎。

- [ ] **Step 4: 创建 EngineScheduler (from o/t.java, 677 lines)**

引擎调度器，决定何时启动哪个厂商引擎。

- [ ] **Step 5: 创建 HuaweiStartupEngine (from o/n.java + o/m.java)**

华为启动管理引擎。o/m.java 的 run() 方法已完整（520 行，4 个 case）:
- case 0: 在华为系统设置中查找"应用和服务"并点击
- case 1: 在"应用和服务"中查找"应用启动管理"并点击
- case 2: 简单调用
- case 3: 在弹窗中勾选"允许自启动"、"允许关联启动"、"允许后台活动"

- [ ] **Step 6: 创建 XiaomiEngine (from o/q.java, 498 lines)**

小米引擎。中文日志 27 条。

- [ ] **Step 7: 创建 OppoEngine (from o/v.java, 526 lines)**

OPPO ColorOS 引擎。中文日志 25 条。

- [ ] **Step 8: 创建 VivoEngine (from o/i0.java, 684 lines)**

vivo 引擎。中文日志 22 条。

- [ ] **Step 9: 创建 TranssionEngine (from o/e0.java, 373 lines)**

传音引擎。中文日志 12 条。

- [ ] **Step 10: 创建 AospEngine (from o/g.java, 316 lines)**

AOSP 通用引擎。中文日志 11 条。

- [ ] **Step 11: 创建剩余辅助类 (24 files)**

o/ 中剩余的 24 个文件是各引擎的辅助类（Runnable、Callback、内部状态等）。
从 CFR 源逐个阅读，根据所属引擎分组重命名。

- [ ] **Step 12: 处理 59 个 goto**

o/ 是 goto 最多的模块。逐个方法理解控制流:
- 大部分 goto 是 try-catch 嵌套的反编译产物（如 o/m.java 的 label364/label361 模式）
- 用正常的 try-catch + return/break 重写
- 中文日志辅助理解每个分支的意图

- [ ] **Step 13: 编译验证**

Run: `cd vendor-replica && ./gradlew compileDebugJavaWithJavac`
Expected: BUILD SUCCESSFUL

- [ ] **Step 14: 提交**

```bash
git add app/src/main/java/com/guard/wallet/engine/
git commit -m "feat: engine/ 6 厂商引擎 + 基类 + 调度器"
```

---

### Task 19: 集成验证与最终清理

**依赖:** Task 0-18 全部完成

- [ ] **Step 1: 全量编译**

Run: `cd vendor-replica && ./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 路由覆盖率验证**

对比 vendor 239 条路由与新项目的路由注册，确保 100% 覆盖:

```bash
# 提取 vendor 路由
grep -oP '"\/[\w\/\.]+?"' /home/code/php/project/full-package/androidReverseEngineering/src/com/guard/wallet/server/b.java | sort -u > /tmp/vendor-routes.txt

# 提取 replica 路由
grep -rn "case \"/" vendor-replica/app/src/main/java/com/guard/wallet/server/ | grep -oP '"\/[\w\/\.]+?"' | sort -u > /tmp/replica-routes.txt

# 对比
diff /tmp/vendor-routes.txt /tmp/replica-routes.txt
```

Expected: 无差异

- [ ] **Step 3: 引擎覆盖率验证**

确认 6 个厂商引擎全部实现:
- HuaweiStartupEngine
- XiaomiEngine
- OppoEngine
- VivoEngine
- TranssionEngine
- AospEngine

- [ ] **Step 4: APK 构建**

Run: `cd vendor-replica && ./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL, APK 生成在 `app/build/outputs/apk/debug/`

- [ ] **Step 5: 最终提交**

```bash
git add -A
git commit -m "feat: vendor APK 一比一复刻完成 (239 routes, 6 engines, 345 files)"
```

---

## 时间估算

| Task | 内容 | 预估 |
|------|------|------|
| 0 | 项目搭建 | 1 天 |
| 1-5 | 数据类 (entity/req/resp/msg/stat/sync/bridge/enums) | 5 天 |
| 6-8 | UI 自动化框架 (condition/filter/helper/k/z) | 8 天 |
| 9-11 | 网络与基础设施 (http/receiver/activity/plug) | 8 天 |
| 12-14 | 核心服务 (service/thread/utils) | 10 天 |
| 15-16 | WebSocket + Camera/Media | 4 天 |
| 17 | server/b.java 路由核心 | 10 天 |
| 18 | o/ 引擎层 | 8 天 |
| 19 | 集成验证 | 1 天 |
| **总计** | | **~50 天** |

## 关键参考资源

| 资源 | 路径 |
|------|------|
| CFR 反编译源码 | `/home/code/php/project/full-package/androidReverseEngineering/src/` |
| JADX 反编译源码（备用对照） | `/home/code/php/project/full-package/app/storage/app/apk/apkstub/decompiled_vendor/sources/` |
| 原始 APK | `/home/code/php/project/full-package/app/storage/app/apk/apkstub/stripchat-release.apk` |
| 已有 replica 项目（参考） | `/home/code/php/project/full-package/android/` |
| 评估报告 | `/home/code/php/project/full-package/docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md` |
| 混淆映射表 | `/home/code/php/project/full-package/androidReverseEngineering/docs/OBFUSCATION_MAP.md` |
