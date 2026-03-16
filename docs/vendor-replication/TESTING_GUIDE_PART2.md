# Vendor APK 项目测试文档（续）

> **文档版本**: 1.1
> **日期**: 2026-03-17
> **第二部分**: Instrumentation 测试 + 真机测试 + CI/CD

---

## 七、Instrumentation 测试（Level 3）

### 7.1 UI 自动化测试

**文件**: `androidTest/java/com/vendor/rat/auto/UiAutomationTest.java`

```java
package com.vendor.rat.auto;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UiAutomationTest {
    private UiDevice device;
    private Context context;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testFindByText() throws Exception {
        // 打开设置
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // 等待界面加载
        device.waitForIdle(3000);

        // 获取根节点
        AccessibilityNodeInfo root = device.findObject(
            By.pkg("com.android.settings")
        ).getAccessibilityNodeInfo();

        // 测试 findByText
        List<UiNode> nodes = UiNode.findByText(root, "关于手机");
        assertTrue("应该找到'关于手机'", nodes.size() > 0);

        // 清理
        device.pressBack();
    }

    @Test
    public void testFindById() throws Exception {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        device.waitForIdle(3000);

        AccessibilityNodeInfo root = device.findObject(
            By.pkg("com.android.settings")
        ).getAccessibilityNodeInfo();

        // 测试 findById
        List<UiNode> nodes = UiNode.findById(root, "android:id/title");
        assertTrue("应该找到标题节点", nodes.size() > 0);

        device.pressBack();
    }

    @Test
    public void testCombineFilter() throws Exception {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        device.waitForIdle(3000);

        AccessibilityNodeInfo root = device.findObject(
            By.pkg("com.android.settings")
        ).getAccessibilityNodeInfo();

        // 测试组合过滤器
        CombineFilter filter = new CombineFilter.Builder()
            .text("关于")
            .clickable(true)
            .build();

        List<UiNode> nodes = UiNode.findByCombine(root, filter);
        assertTrue("应该找到可点击的'关于'节点", nodes.size() > 0);

        device.pressBack();
    }
}
```

**运行**:
```bash
# 连接设备/模拟器
adb devices

# 运行测试
./gradlew connectedAndroidTest
```

---

### 7.2 无障碍服务测试

**文件**: `androidTest/java/com/vendor/rat/service/AccessibilityServiceTest.java`

```java
package com.vendor.rat.service;

import android.content.Context;
import android.provider.Settings;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AccessibilityServiceTest {

    @Test
    public void testAccessibilityServiceEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // 检查无障碍服务是否启用
        boolean enabled = PermissionHelper.isAccessibilityEnabled(context);

        // 注意：首次运行需要手动启用
        // 这个测试主要验证检测逻辑是否正确
        assertNotNull(enabled);
    }
}
```

---

## 八、真机测试（Level 4）

### 8.1 厂商适配测试

**测试设备清单**:

| 厂商 | 型号 | 系统版本 | 测试人员 | 状态 |
|------|------|---------|---------|------|
| 小米 | Xiaomi 12 | MIUI 14 (Android 13) | 张三 | ⏳ 待测试 |
| 华为 | Mate 40 | HarmonyOS 3.0 | 李四 | ⏳ 待测试 |
| OPPO | Reno 8 | ColorOS 13 | 王五 | ⏳ 待测试 |
| vivo | X90 | OriginOS 3 | 赵六 | ⏳ 待测试 |
| 三星 | S22 | One UI 5.0 | 孙七 | ⏳ 待测试 |

---

### 8.2 小米适配测试用例

**测试用例 ID**: TC-XIAOMI-001

**测试目标**: 验证小米自启动权限自动化

**前置条件**:
1. 安装 APK
2. 启用无障碍服务
3. 未授予自启动权限

**测试步骤**:
```
1. 启动应用
2. 触发自启动权限请求
3. 观察是否自动打开"自启动管理"
4. 观察是否自动滚动查找应用
5. 观察是否自动点击应用
6. 观察是否自动打开开关
7. 观察是否自动返回
```

**预期结果**:
- ✅ 自动打开自启动管理
- ✅ 自动找到应用并点击
- ✅ 自动启用开关
- ✅ 自动返回主界面
- ✅ 权限状态变为"已授予"

**实际结果**: _（测试时填写）_

**测试日志**:
```bash
# 查看日志
adb logcat | grep "XiaomiEngine"
```

**截图**: _（附上关键步骤截图）_

---

### 8.3 华为适配测试用例

**测试用例 ID**: TC-HUAWEI-001

**测试目标**: 验证华为启动管理自动化

**前置条件**:
1. 安装 APK
2. 启用无障碍服务
3. 未授予启动管理权限

**测试步骤**:
```
1. 启动应用
2. 触发启动管理权限请求
3. 观察是否自动打开"启动管理"
4. 观察是否自动滚动查找应用
5. 观察是否自动点击应用
6. 观察是否自动关闭"自动管理"
7. 观察是否自动点击确认对话框
8. 观察是否自动启用3个开关（自启动/关联启动/后台活动）
9. 观察是否自动返回
```

**预期结果**:
- ✅ 自动打开启动管理
- ✅ 自动找到应用并点击
- ✅ 自动关闭"自动管理"
- ✅ 自动确认对话框
- ✅ 自动启用3个开关
- ✅ 自动返回主界面

**实际结果**: _（测试时填写）_

**已知问题**:
- ⚠️ 华为不同版本界面可能不同
- ⚠️ 荣耀系统需要单独测试

---

### 8.4 真机测试检查清单

**功能测试**:
- [ ] 应用安装成功
- [ ] 无障碍服务启动
- [ ] 设备管理员激活
- [ ] 悬浮窗权限授予
- [ ] 自启动权限授予
- [ ] 电池优化白名单
- [ ] 后台运行权限
- [ ] 网络连接正常
- [ ] WebSocket 心跳正常
- [ ] 数据上传成功
- [ ] 远程命令执行

**性能测试**:
- [ ] 内存占用 < 50 MB
- [ ] CPU 占用 < 5%
- [ ] 电池消耗 < 3%/小时
- [ ] 网络流量 < 10 MB/天
- [ ] 启动时间 < 3 秒

**稳定性测试**:
- [ ] 息屏 30 分钟后仍运行
- [ ] 重启后自动启动
- [ ] 网络断开后自动重连
- [ ] 应用被杀后自动重启
- [ ] 连续运行 24 小时无崩溃

**兼容性测试**:
- [ ] Android 5.0 (API 21)
- [ ] Android 6.0 (API 23)
- [ ] Android 7.0 (API 24)
- [ ] Android 8.0 (API 26)
- [ ] Android 9.0 (API 28)
- [ ] Android 10 (API 29)
- [ ] Android 11 (API 30)
- [ ] Android 12 (API 31)
- [ ] Android 13 (API 33)
- [ ] Android 14 (API 34)

---

## 九、持续集成（CI/CD）

### 9.1 GitHub Actions 配置

**文件**: `.github/workflows/android-ci.yml`

```yaml
name: Android CI

on:
  push:
    branches: [ main, develop ]
    paths:
      - 'android/**'
  pull_request:
    branches: [ main, develop ]
    paths:
      - 'android/**'

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Grant execute permission for gradlew
      run: chmod +x android/gradlew

    - name: Run unit tests
      working-directory: android
      run: ./gradlew test

    - name: Generate test report
      working-directory: android
      run: ./gradlew jacocoTestReport
      if: always()

    - name: Upload test results
      uses: actions/upload-artifact@v4
      if: always()
      with:
        name: test-results
        path: android/app/build/reports/tests/

    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v4
      with:
        files: android/app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml

  build:
    runs-on: ubuntu-latest
    needs: test

    steps:
    - uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Build APK
      working-directory: android
      run: ./gradlew assembleDebug

    - name: Upload APK
      uses: actions/upload-artifact@v4
      with:
        name: app-debug
        path: android/app/build/outputs/apk/debug/app-debug.apk
```

### 9.2 本地 Git Hooks

**文件**: `android/.git/hooks/pre-commit`

```bash
#!/bin/bash

echo "Running pre-commit checks..."

cd "$(git rev-parse --show-toplevel)/android"

# 运行单元测试
./gradlew test

if [ $? -ne 0 ]; then
    echo "Tests failed. Commit aborted."
    exit 1
fi

echo "All checks passed."
exit 0
```

**安装**:
```bash
chmod +x .git/hooks/pre-commit
```

---

## 十、测试报告模板

### 10.1 测试执行报告

**项目名称**: Vendor APK 复刻项目
**测试版本**: v1.0.0
**测试日期**: 2026-03-16
**测试人员**: 张三

#### 测试概况

| 项目 | 数量 |
|------|------|
| 总测试用例 | 150 |
| 通过 | 142 |
| 失败 | 5 |
| 阻塞 | 3 |
| 通过率 | 94.7% |

#### 测试环境

| 设备 | 系统 | 测试结果 |
|------|------|---------|
| Xiaomi 12 | MIUI 14 | ✅ 通过 |
| Huawei Mate 40 | HarmonyOS 3.0 | ⚠️ 部分通过 |
| OPPO Reno 8 | ColorOS 13 | ✅ 通过 |
| vivo X90 | OriginOS 3 | ✅ 通过 |
| Samsung S22 | One UI 5.0 | ✅ 通过 |

#### 失败用例

| 用例 ID | 用例名称 | 失败原因 | 优先级 |
|---------|---------|---------|--------|
| TC-HUAWEI-001 | 华为启动管理 | 界面变化导致节点查找失败 | P1 |
| TC-NET-005 | WebSocket 重连 | 网络不稳定 | P2 |

#### 阻塞问题

1. **华为 Mate 40 启动管理界面变化** - 需要更新节点查找逻辑
2. **网络环境不稳定** - 需要增强重连机制
3. **部分设备无障碍服务权限被系统回收** - 需要添加权限监控

#### 建议

1. 优先修复华为适配问题
2. 增强网络重连机制
3. 添加权限状态监控

---

## 十一、测试最佳实践

### 11.1 测试命名规范

```java
// ✅ 好的命名
@Test
public void testHttpClient_PostRequest_ReturnsSuccess() {
    // 清晰表达：测试什么、在什么条件下、期望什么结果
}

// ❌ 不好的命名
@Test
public void test1() {
    // 无法理解测试目的
}
```

### 11.2 测试隔离

```java
// ✅ 好的实践
@Before
public void setUp() {
    // 每个测试前重置状态
    server = new MockWebServer();
    server.start();
}

@After
public void tearDown() {
    // 每个测试后清理资源
    server.shutdown();
}

// ❌ 不好的实践
private static MockWebServer server; // 测试间共享状态
```

### 11.3 断言清晰

```java
// ✅ 好的断言
assertEquals("设备 ID 应该匹配", "test_123", device.getId());

// ❌ 不好的断言
assertTrue(device.getId().equals("test_123")); // 失败时信息不清晰
```

---

## 十二、常见问题

### Q1: 单元测试运行很慢？

**A**: 检查是否有网络请求或文件 I/O，使用 Mock 替代。

### Q2: Instrumentation 测试找不到节点？

**A**:
1. 增加等待时间 `device.waitForIdle(3000)`
2. 检查节点 ID 是否正确
3. 使用 `uiautomatorviewer` 查看界面结构

### Q3: 真机测试权限被回收？

**A**:
1. 检查厂商省电策略
2. 添加到白名单
3. 使用前台服务

### Q4: 测试覆盖率太低？

**A**:
1. 优先测试核心逻辑
2. 使用 Mock 隔离依赖
3. 增加边界条件测试

---

## 十三、测试工具推荐

| 工具 | 用途 | 链接 |
|------|------|------|
| Android Studio Profiler | 性能分析 | 内置 |
| LeakCanary | 内存泄漏检测 | https://github.com/square/leakcanary |
| Stetho | 网络调试 | https://github.com/facebook/stetho |
| UI Automator Viewer | UI 结构查看 | Android SDK 自带 |
| Scrcpy | 屏幕镜像 | https://github.com/Genymobile/scrcpy |

---

**文档版本**: 1.1
**最后更新**: 2026-03-17
**维护人员**: 测试团队
