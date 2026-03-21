# Vendor APK 项目测试文档

> **文档版本**: 1.2
> **日期**: 2026-03-21
> **适用项目**: Vendor APK Java 复刻项目
> **测试策略**: 分层测试 + 持续集成
> **构建环境**: WSL Ubuntu 22.04 + JDK 17 + Android SDK CLI

---

## 一、测试策略概览

### 1.1 测试金字塔

```
           /\
          /  \  真机测试（10%）
         /____\  - 厂商适配
        /      \  - 权限绕过
       /________\ Instrumentation 测试（20%）
      /          \ - UI 自动化
     /____________\ - 无障碍服务
    /              \
   /________________\ 单元测试（70%）
  /                  \ - 网络通信
 /____________________\ - 数据模型
                        - 工具类
```

### 1.2 测试分层

| 层级 | 测试类型 | 速度 | 覆盖率目标 | 运行频率 |
|------|---------|------|-----------|---------|
| L1 | 单元测试 | ⚡ 秒级 | 70% | 每次提交 |
| L2 | Robolectric | ⚡⚡ 10秒 | 15% | 每次提交 |
| L3 | Instrumentation | 🐢 1-2分钟 | 10% | 每日构建 |
| L4 | 真机测试 | 🐌 5-10分钟 | 5% | 发布前 |

### 1.3 测试原则

1. ✅ **快速反馈** - 优先运行快速测试
2. ✅ **隔离性** - 测试之间互不影响
3. ✅ **可重复** - 相同输入产生相同结果
4. ✅ **自动化** - 集成到 CI/CD
5. ✅ **覆盖关键路径** - 优先测试核心功能

---

## 二、测试环境配置

### 2.1 开发环境

**前置条件**（已在 WSL 中配置完成）:

| 组件 | 路径 / 版本 |
|------|-------------|
| JDK 17 | `/usr/lib/jvm/java-17-openjdk-amd64` |
| Android SDK | `/opt/android-sdk` |
| Gradle 8.5 | wrapper 自动管理 |
| platforms | android-34 |
| build-tools | 34.0.0 |

**环境变量**（已持久化到 `~/.bashrc`）:
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=/opt/android-sdk
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH
```

### 2.2 Gradle 配置

**文件**: `app/build.gradle`

```gradle
android {
    defaultConfig {
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            includeAndroidResources = true
            returnDefaultValues = true
        }
    }
}

dependencies {
    // ========== 单元测试 ==========
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.3.1'
    testImplementation 'org.mockito:mockito-inline:5.3.1'
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.12.0'

    // Robolectric
    testImplementation 'org.robolectric:robolectric:4.11.1'

    // ========== Instrumentation 测试 ==========
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test:runner:1.5.2'
    androidTestImplementation 'androidx.test:rules:1.5.0'
    androidTestImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

### 2.3 测试目录结构（实际）

```
android/app/src/
├── main/java/com/vendor/rat/
│   ├── network/
│   ├── service/
│   ├── auto/
│   ├── config/
│   ├── data/
│   ├── control/
│   ├── keepalive/
│   ├── activity/
│   ├── exception/
│   └── utils/
└── test/java/com/vendor/rat/       # 14 个单元测试（已全部通过）
    ├── StartupModuleTest.java       # 启动模块集成测试
    ├── network/
    │   └── HttpClientTest.java      # MockWebServer POST/GET 测试
    ├── auto/
    │   ├── NodeFilterTest.java      # StringCondition/BoolCondition/CombineFilter 测试
    │   └── engine/
    │       ├── AutoEngineWindowMatcherTest.java              # WindowMatcher.matches() 边界 (15 用例)
    │       ├── PermissionAutoGrantEngineMatchWindowTest.java  # 权限弹窗窗口匹配 (12 用例)
    │       └── vendor/
    │           ├── HuaweiEngineWindowMatchTest.java          # 华为四组窗口检测 (21 用例)
    │           ├── HuaweiEngineStateMachineTest.java         # 华为状态机转换 (12 用例)
    │           ├── XiaomiEngineWindowMatchTest.java          # 小米窗口检测
    │           └── XiaomiEngineStateMachineTest.java         # 小米状态机转换
    ├── config/
    │   └── AppConfigTest.java       # 默认配置和 getter/setter 测试
    ├── control/handler/
    │   ├── ScreenCommandTest.java       # 屏幕命令解析测试
    │   └── ScreenActionParserTest.java  # 屏幕动作解析测试
    ├── service/
    │   └── DeviceAdminReceiverTest.java # 设备管理接收器测试
    └── utils/
        └── DeviceUtilsTest.java     # getBrandName 非空测试
```

---

## 三、单元测试（Level 1）

### 3.1 网络通信测试

**文件**: `test/java/com/vendor/rat/network/HttpClientTest.java`

```java
package com.vendor.rat.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.*;

public class HttpClientTest {
    private MockWebServer server;
    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

        // 使用 Mock 服务器地址
        String baseUrl = server.url("/").toString();
        httpClient = new HttpClient(null);
        httpClient.setBaseUrl(baseUrl);
    }

    @Test
    public void testPostRequest() throws Exception {
        // 1. Mock 响应
        server.enqueue(new MockResponse()
            .setBody("{\"code\":200,\"message\":\"success\"}")
            .setResponseCode(200));

        // 2. 发送请求
        TestCallback callback = new TestCallback();
        httpClient.post("/api/test", new TestData("test"), callback);

        // 3. 等待响应
        Thread.sleep(1000);

        // 4. 验证
        assertTrue(callback.isSuccess());
        assertNotNull(callback.getResponse());

        // 5. 验证请求
        RecordedRequest request = server.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/api/test", request.getPath());
    }

    @Test
    public void testRequestTimeout() throws Exception {
        // Mock 延迟响应
        server.enqueue(new MockResponse()
            .setBody("{\"code\":200}")
            .setBodyDelay(5, TimeUnit.SECONDS));

        TestCallback callback = new TestCallback();
        httpClient.post("/api/test", new TestData("test"), callback);

        Thread.sleep(6000);

        assertTrue(callback.isError());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    // 测试辅助类
    private static class TestCallback implements Callback {
        private boolean success = false;
        private boolean error = false;
        private String response;

        @Override
        public void onSuccess(String response) {
            this.success = true;
            this.response = response;
        }

        @Override
        public void onError(Exception e) {
            this.error = true;
        }

        public boolean isSuccess() { return success; }
        public boolean isError() { return error; }
        public String getResponse() { return response; }
    }

    private static class TestData {
        String value;
        TestData(String value) { this.value = value; }
    }
}
```

**运行**:
```bash
./gradlew test --tests HttpClientTest
```

---

### 3.2 WebSocket 测试

**文件**: `test/java/com/vendor/rat/network/WebSocketClientTest.java`

```java
package com.vendor.rat.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

public class WebSocketClientTest {
    private MockWebServer server;
    private WebSocketClient wsClient;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        wsClient = new WebSocketClient(null);
    }

    @Test
    public void testConnect() throws Exception {
        String wsUrl = "ws://localhost:" + server.getPort();

        TestWebSocketListener listener = new TestWebSocketListener();
        wsClient.connect(wsUrl, "test_token", listener);

        Thread.sleep(2000);

        assertTrue(listener.isConnected());
    }

    @Test
    public void testSendMessage() throws Exception {
        String wsUrl = "ws://localhost:" + server.getPort();

        TestWebSocketListener listener = new TestWebSocketListener();
        wsClient.connect(wsUrl, "test_token", listener);

        Thread.sleep(1000);

        wsClient.send("{\"type\":1,\"deviceId\":\"test\"}");

        Thread.sleep(1000);

        // 验证消息发送
        assertTrue(listener.getMessageCount() > 0);
    }

    @After
    public void tearDown() throws Exception {
        wsClient.disconnect();
        server.shutdown();
    }

    private static class TestWebSocketListener implements WebSocketListener {
        private boolean connected = false;
        private int messageCount = 0;

        @Override
        public void onConnected() {
            connected = true;
        }

        @Override
        public void onMessage(String message) {
            messageCount++;
        }

        @Override
        public void onError(Throwable t) {}

        @Override
        public void onClosed() {
            connected = false;
        }

        public boolean isConnected() { return connected; }
        public int getMessageCount() { return messageCount; }
    }
}
```

---

### 3.3 UI 自动化框架测试

**文件**: `test/java/com/vendor/rat/auto/NodeFilterTest.java`

```java
package com.vendor.rat.auto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import android.view.accessibility.AccessibilityNodeInfo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class NodeFilterTest {

    @Test
    public void testStringCondition_Equals() {
        // Mock AccessibilityNodeInfo
        AccessibilityNodeInfo node = mock(AccessibilityNodeInfo.class);
        when(node.getText()).thenReturn("设置");

        // 测试 EQUALS 条件
        StringCondition condition = new StringCondition(
            "text",
            StringCondition.Type.EQUALS,
            "设置"
        );

        assertTrue(condition.match(node));
    }

    @Test
    public void testStringCondition_Contains() {
        AccessibilityNodeInfo node = mock(AccessibilityNodeInfo.class);
        when(node.getText()).thenReturn("系统设置");

        StringCondition condition = new StringCondition(
            "text",
            StringCondition.Type.CONTAINS,
            "设置"
        );

        assertTrue(condition.match(node));
    }

    @Test
    public void testBoolCondition_Clickable() {
        AccessibilityNodeInfo node = mock(AccessibilityNodeInfo.class);
        when(node.isClickable()).thenReturn(true);

        BoolCondition condition = new BoolCondition(
            BoolCondition.Type.CLICKABLE,
            true
        );

        assertTrue(condition.match(node));
    }

    @Test
    public void testCombineFilter_And() {
        AccessibilityNodeInfo node = mock(AccessibilityNodeInfo.class);
        when(node.getText()).thenReturn("确定");
        when(node.isClickable()).thenReturn(true);

        CombineFilter filter = new CombineFilter.Builder()
            .text("确定")
            .clickable(true)
            .build();

        assertTrue(filter.match(node));
    }

    @Test
    public void testCombineFilter_Or() {
        AccessibilityNodeInfo node = mock(AccessibilityNodeInfo.class);
        when(node.getText()).thenReturn("取消");
        when(node.isClickable()).thenReturn(false);

        CombineFilter filter = new CombineFilter(CombineFilter.Operator.OR);
        filter.add(new StringCondition("text", StringCondition.Type.EQUALS, "确定"));
        filter.add(new StringCondition("text", StringCondition.Type.EQUALS, "取消"));

        assertTrue(filter.match(node));
    }
}
```

---

### 3.4 工具类测试

**文件**: `test/java/com/vendor/rat/utils/DeviceUtilsTest.java`

```java
package com.vendor.rat.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class DeviceUtilsTest {

    @Test
    public void testGetVendorId() {
        int vendorId = DeviceUtils.getVendorId();
        assertTrue(vendorId >= 0 && vendorId <= 14);
    }

    @Test
    public void testGetVendorName() {
        String vendorName = DeviceUtils.getVendorName();
        assertNotNull(vendorName);
        assertFalse(vendorName.isEmpty());
    }

    @Test
    public void testIsXiaomi() {
        // 根据当前设备判断
        boolean isXiaomi = DeviceUtils.isXiaomi();
        // 只验证方法不抛异常
        assertNotNull(isXiaomi);
    }
}
```

**运行所有单元测试**:
```bash
./gradlew test
```

---

### 3.5 自动化引擎测试（纯 JVM，无需 Android 框架）

以下测试覆盖华为保活自动化和权限自动授予的核心逻辑，全部纯 JVM 运行。

#### 3.5.1 WindowMatcher 边界测试

**文件**: `test/java/com/vendor/rat/auto/engine/AutoEngineWindowMatcherTest.java` (15 用例)

测试 `AutoEngine.WindowMatcher.matches(pkg, cls, eventType)` 的所有分支：
- 精确包名+类名匹配、包名通配（className=null/空）
- eventType 过滤（集合内/集合外/空集合/eventType=0 边界）
- buildAllMatchers 风格（带 eventTypes）vs buildDetectionGroups 风格（无 eventTypes）行为差异

```bash
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.AutoEngineWindowMatcherTest"
```

#### 3.5.2 华为四组窗口检测测试

**文件**: `test/java/com/vendor/rat/auto/engine/vendor/HuaweiEngineWindowMatchTest.java` (21 用例)

复制 HuaweiEngine 常量和 buildDetectionGroups 逻辑，验证四组检测列表：
- hwSettingsWins (j0): HWSettings ✓, CleanSubSettings ✓, SubSettings ✗
- appNotifWins (i0): SubSettings ✓, AppAndNotification ✓, InstalledAppDetails ✓
- startupWindows (k0): StartupAppControl ✓, StartupNormalList ✓, HonorStartup ✓
- dialogWins (h0): HUAWEI_SM+AlertDialog ✓, HONOR_SM+AlertDialog ✓, SETTINGS+AlertDialog ✗
- 跨组互斥验证 + 空列表/null/错误包名边界

```bash
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineWindowMatchTest"
```

#### 3.5.3 华为状态机转换测试

**文件**: `test/java/com/vendor/rat/auto/engine/vendor/HuaweiEngineStateMachineTest.java` (12 用例)

创建 TestableEngine 暴露 stateQueue，测试状态转换：
- 初始状态为空、enterState 添加/互斥移除、重复添加不重复
- exitState 移除/不存在不报错
- 完整转换序列: HW→APP→STARTUP→DIALOG
- stateQueue.clear() 清空、完成条件检查

```bash
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineStateMachineTest"
```

#### 3.5.4 权限弹窗窗口匹配测试

**文件**: `test/java/com/vendor/rat/auto/engine/PermissionAutoGrantEngineMatchWindowTest.java` (12 用例)

测试 `PermissionAutoGrantEngine.matchWindow()` 重写逻辑：
- 权限控制器包名匹配 (android/google/packageinstaller)
- 华为 systemmanager + className 含 "Permission" → true
- 通用 GrantPermissions 类名匹配
- null 包名/类名边界、不匹配的包名

```bash
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.PermissionAutoGrantEngineMatchWindowTest"
```

---

## 四、Robolectric 测试（Level 2）

### 4.1 Service 测试

**文件**: `test/java/com/vendor/rat/service/MyAccessibilityServiceTest.java`

```java
package com.vendor.rat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class MyAccessibilityServiceTest {

    @Test
    public void testServiceCreation() {
        MyAccessibilityService service = Robolectric.setupService(MyAccessibilityService.class);
        assertNotNull(service);
    }

    @Test
    public void testEngineRegistration() {
        MyAccessibilityService service = Robolectric.setupService(MyAccessibilityService.class);

        // 验证引擎已注册
        // 注意：需要在 MyAccessibilityService 中添加 getter 方法
        assertNotNull(service.getEngineManager());
    }
}
```

---

## 五、运行测试

### 5.1 WSL 命令行运行（推荐）

```bash
cd /home/code/php/project/full-package/android

# 运行所有单元测试（日常主要命令）
./gradlew test

# 运行特定测试类
./gradlew testDebugUnitTest --tests "com.vendor.rat.network.HttpClientTest"

# 运行特定测试方法
./gradlew testDebugUnitTest --tests "com.vendor.rat.network.HttpClientTest.testPostRequest"

# 运行自动化引擎全部测试
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.*"

# 清理后运行（构建缓存出问题时使用）
./gradlew clean test

# 生成详细测试报告
./gradlew test --info

# 查看测试报告（HTML）
# 路径：app/build/reports/tests/testDebugUnitTest/index.html
```

**首次运行注意事项**:
- 首次 `./gradlew test` 会下载 Gradle 8.5 和所有依赖，约需 3 分钟
- 后续运行利用配置缓存和构建缓存，通常 1-3 秒完成
- WSL 性能优化已在 `gradle.properties` 中配置

### 5.2 Android Studio 运行（可选）

1. **运行单个测试**：
   - 右键测试方法 → Run 'testPostRequest()'

2. **运行测试类**：
   - 右键测试类 → Run 'HttpClientTest'

3. **运行所有测试**：
   - 右键 `test` 目录 → Run 'Tests in test'

4. **调试测试**：
   - 设置断点 → 右键 → Debug 'testPostRequest()'

---

## 六、测试覆盖率

### 6.1 配置 JaCoCo

**文件**: `app/build.gradle`

```gradle
apply plugin: 'jacoco'

jacoco {
    toolVersion = "0.8.10"
}

tasks.withType(Test) {
    jacoco.includeNoLocationClasses = true
    jacoco.excludes = ['jdk.internal.*']
}

task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest']) {
    reports {
        xml.required = true
        html.required = true
    }

    def fileFilter = [
        '**/R.class',
        '**/R$*.class',
        '**/BuildConfig.*',
        '**/Manifest*.*'
    ]

    def debugTree = fileTree(dir: "${buildDir}/intermediates/javac/debug", excludes: fileFilter)
    def mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files([mainSrc]))
    classDirectories.setFrom(files([debugTree]))
    executionData.setFrom(fileTree(dir: "$buildDir", includes: [
        "jacoco/testDebugUnitTest.exec"
    ]))
}
```

### 6.2 生成覆盖率报告

```bash
# 运行测试并生成覆盖率报告
./gradlew jacocoTestReport

# 查看报告
open app/build/reports/jacoco/jacocoTestReport/html/index.html
```

### 6.3 覆盖率目标

| 模块 | 目标覆盖率 |
|------|-----------|
| 网络通信 | 80% |
| 数据模型 | 90% |
| 工具类 | 85% |
| UI 自动化框架 | 75% |
| 权限绕过 | 60% |
| 厂商适配 | 50% |

---

**文档版本**: 1.2
**最后更新**: 2026-03-21
**下一部分**: Instrumentation 测试和真机测试
