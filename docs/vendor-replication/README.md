# Vendor APK 复刻项目 - 模块索引

> **项目名称**: Vendor APK Java 复刻项目
> **文档版本**: 2.0
> **最后更新**: 2026-03-22
> **总模块数**: 8 个核心模块
> **项目目录**: `android/`（项目根目录下）
> **构建状态**: ✅ BUILD SUCCESSFUL（编译通过 + 全部 23 个测试文件通过）

---

## 一、项目概述

**运行时交互文档**: [APK_RUNTIME_INTERACTION_FLOW.md](./APK_RUNTIME_INTERACTION_FLOW.md)

本项目旨在一比一复刻 Vendor APK 的核心功能，构建一个完整的 Java Android 项目，用于企业安全培训靶场环境。

### 1.1 技术栈

- **语言**: Java 8+
- **平台**: Android API 21-34
- **构建**: Gradle 8.5 + AGP 8.2.2
- **网络**: OkHttp 4.12.0 + Conscrypt 2.5.2
- **JSON**: Gson 2.10.1
- **测试**: JUnit 4.13.2 + Mockito 5.3.1 + MockWebServer + Robolectric 4.11.1

### 1.2 开发环境

| 组件 | 版本 / 配置 |
|------|-------------|
| JDK | OpenJDK 17 (`/usr/lib/jvm/java-17-openjdk-amd64`) |
| Android SDK | CLI Tools (`/opt/android-sdk`) |
| 平台 SDK | platforms;android-34 |
| Build Tools | 34.0.0 |
| Gradle | 8.5 (wrapper) |
| 开发环境 | WSL (Ubuntu 22.04) |
| 日常构建 | `./gradlew test`（无需 APK/模拟器） |

**环境变量**（已配置到 `~/.bashrc`）:
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=/opt/android-sdk
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH
```

### 1.3 项目结构

```
com.vendor.rat
├── MyApp.java                # Application 子类（入口）
├── MainApplication.java      # 应用初始化管理器
├── ActivityLifecycleTracker.java
├── config/                   # 模块 08: 配置管理
│   ├── AppConfig.java
│   ├── ConfigDecryptor.java
│   └── ApiEndpoints.java
├── network/                  # 模块 01: 网络通信
│   ├── NetworkManager.java
│   ├── HttpClient.java
│   ├── WebSocketClient.java
│   └── HttpCallback.java
├── service/                  # 模块 02: 权限绕过
│   ├── MyAccessibilityService.java
│   ├── EngineManager.java
│   └── AppDeviceAdminReceiver.java
├── auto/
│   ├── entity/               # 模块 04: UI 自动化框架
│   │   └── UiNode.java
│   ├── filter/
│   │   └── NodeFilter.java
│   ├── condition/
│   │   ├── StringCondition.java
│   │   ├── BoolCondition.java
│   │   └── CombineFilter.java
│   └── engine/
│       ├── AutoEngine.java
│       └── vendor/           # 模块 03: 厂商适配
│           ├── XiaomiEngine.java
│           ├── HuaweiEngine.java
│           ├── OppoEngine.java
│           ├── VivoEngine.java
│           └── TranssionEngine.java
├── data/                     # 模块 05: 数据收集
│   ├── collector/
│   │   ├── DataCollectionManager.java
│   │   ├── SmsReceiver.java
│   │   ├── CallReceiver.java
│   │   └── LockCipherCollector.java
│   ├── observer/
│   │   └── PhotoAlbumContentObserver.java
│   └── queue/
│       └── UploadQueue.java
├── control/                  # 模块 06: 远程控制
│   ├── handler/
│   │   ├── CommandDispatcher.java
│   │   ├── ScreenshotHandler.java
│   │   ├── AudioRecordHandler.java
│   │   ├── ShellCommandHandler.java
│   │   └── FileTransferHandler.java
│   └── service/
│       └── MediaLiveService.java
├── keepalive/                # 模块 07: 保活机制
│   ├── KeepAliveManager.java
│   ├── KeepAliveJobService.java
│   ├── receiver/
│   │   ├── BootReceiver.java
│   │   ├── ScreenBroadcastReceiver.java
│   │   ├── AlarmReceiver.java
│   │   └── BatteryLevelReceiver.java
│   ├── thread/
│   │   ├── CheckThread.java
│   │   └── HeartThread.java
│   └── service/
│       ├── WIFIBackgroundService.java
│       └── AccountAuthenticatorService.java
├── activity/                 # 模块 08: 启动流程
│   ├── ActivMain.java
│   └── PermissionActivity.java
├── exception/
│   └── GlobalExceptionHandler.java
└── utils/
    ├── DeviceUtils.java
    └── HiddenApiBypass.java
```

---

## 二、核心模块清单

### 模块 01: 网络通信模块

**文档**: [MODULE_01_NETWORK_COMMUNICATION.md](./MODULE_01_NETWORK_COMMUNICATION.md)

**优先级**: P0（极高）

**功能**:
- HTTP/HTTPS 通信（数据上传）
- WebSocket 实时通信（命令推送）
- FRP 反向代理（可选）
- TLS 1.3 支持

**核心类**:
- `NetworkManager` - 网络管理器
- `HttpClient` - HTTP 客户端
- `WebSocketClient` - WebSocket 客户端

**工作量**: 9-12 天

**状态**: 📝 设计完成

---

### 模块 02: 权限绕过模块

**文档**: [MODULE_02_PERMISSION_BYPASS.md](./MODULE_02_PERMISSION_BYPASS.md)

**优先级**: P0（极高）

**功能**:
- 无障碍服务自动启用
- 设备管理员自动激活
- 悬浮窗权限自动授予
- 引擎管理和事件分发

**核心类**:
- `MyAccessibilityService` - 无障碍服务
- `EngineManager` - 引擎管理器
- `AutoEngine` - 引擎基类
- `AccessibilityEngine` - 无障碍引擎
- `DeviceAdminEngine` - 设备管理员引擎

**工作量**: 9 天

**状态**: ✅ 实现完成，AutoEngine 基类 TODO 已清零

---

### 模块 03: 厂商适配模块

**文档**: [MODULE_03_VENDOR_ADAPTATION.md](./MODULE_03_VENDOR_ADAPTATION.md)

**优先级**: P1（高）

**功能**:
- 小米自启动/电池优化
- 华为启动管理（3个开关）
- OPPO 自启动管理
- vivo 自启动管理
- 三星电池优化

**状态**: ✅ 6/6 厂商引擎全部对齐 vendor

**核心类**:
- `XiaomiEngine` - 小米适配
- `HuaweiEngine` - 华为适配
- `OppoEngine` - OPPO 适配
- `VivoEngine` - vivo 适配
- `TranssionEngine` - 传音适配
- `AospKeepAliveEngine` - AOSP/三星通用
- `DeviceUtils` - 设备检测

**工作量**: 13 天

**状态**: ✅ 6/6 厂商引擎全部对齐 vendor

---

### 模块 04: UI 自动化框架

**文档**: [MODULE_04_UI_AUTOMATION_FRAMEWORK.md](./MODULE_04_UI_AUTOMATION_FRAMEWORK.md)

**优先级**: P0（极高）

**功能**:
- 节点查询（byText/byId/byClass/byCombine）
- 节点过滤（文本/布尔/坐标条件）
- 节点操作（click/setText/scroll）
- 滚动查找（scrollForwardUntil）

**核心类**:
- `UiNode` - 节点封装
- `NodeFilter` - 过滤器接口
- `StringCondition` - 文本条件
- `BoolCondition` - 布尔条件
- `CombineFilter` - 组合过滤器

**工作量**: 6 天

**状态**: 📝 设计完成

---

## 三、扩展模块清单

### 模块 05: 数据收集模块

**文档**: [MODULE_05_DATA_COLLECTION.md](./MODULE_05_DATA_COLLECTION.md)

**优先级**: P1（高）

**功能**:
- 短信收集（实时拦截 + 历史读取）
- 联系人收集（全量 + 增量同步）
- 通话记录收集
- 文件扫描（目录 + 类型过滤）
- 相册监控（ContentObserver）
- 应用列表、位置、锁屏密码、设备信息

**核心类**:
- `DataCollectionManager` - 数据收集管理器
- `UploadQueue` - 上传队列（批量 + 重试）
- `SmsReceiver` / `CallReceiver` - 广播接收器
- `PhotoAlbumContentObserver` - 相册监听
- `LockCipherCollector` - 锁屏密码采集

**工作量**: 11 天

**状态**: 📝 设计完成

---

### 模块 06: 远程控制模块

**文档**: [MODULE_06_REMOTE_CONTROL.md](./MODULE_06_REMOTE_CONTROL.md)

**优先级**: P1（高）

**功能**:
- 屏幕截图（MediaProjection）
- 录音控制（MediaRecorder）
- Shell 命令执行
- 文件上传/下载管理
- 摄像头拍照
- 短信发送、通话控制

**核心类**:
- `CommandDispatcher` - 指令分发器
- `ScreenshotHandler` - 截图处理器
- `AudioRecordHandler` - 录音处理器
- `ShellCommandHandler` - Shell 命令处理器
- `FileTransferHandler` - 文件传输处理器
- `MediaLiveService` - 前台媒体服务

**工作量**: 12 天

**状态**: 📝 设计完成

---

### 模块 07: 保活机制模块

**文档**: [MODULE_07_KEEPALIVE_MECHANISM.md](./MODULE_07_KEEPALIVE_MECHANISM.md)

**优先级**: P1（高）

**功能**:
- 前台服务保活（Foreground Service + START_STICKY）
- 系统广播监听（开机、息屏、电池、网络）
- 定时唤醒（AlarmManager + JobScheduler）
- WakeLock 管理
- 账号同步保活（AccountAuthenticator）
- 进程监控（CheckThread + HeartThread）

**核心类**:
- `KeepAliveManager` - 保活管理器
- `BootReceiver` / `ScreenBroadcastReceiver` - 广播接收器
- `AlarmScheduler` - 闹钟调度器
- `CheckThread` / `HeartThread` - 监控线程
- `WakeLockManager` - WakeLock 管理

**工作量**: 8 天

**状态**: 📝 设计完成

---

### 模块 08: 启动流程模块

**文档**: [MODULE_08_STARTUP_FLOW.md](./MODULE_08_STARTUP_FLOW.md)

**优先级**: P0（极高）

**功能**:
- Application 生命周期管理
- 加密配置加载（AES-128-ECB）
- 核心服务有序启动
- 广播接收器注册
- 隐藏 API 绕过
- 全局异常处理与自动重启
- 设备注册与鉴权

**核心类**:
- `MainApplication` - 应用入口管理器
- `AppConfig` / `ConfigDecryptor` - 配置管理
- `ApiEndpoints` - API 端点常量
- `GlobalExceptionHandler` - 全局异常处理
- `HiddenApiBypass` - 隐藏 API 绕过
- `ActivMain` - 启动 Activity

**工作量**: 5 天

**状态**: 📝 设计完成

---

## 四、实施路线图

### Phase 1: 基础设施（3 周）

**目标**: 搭建核心框架

| 模块 | 工作量 | 状态 |
|------|--------|------|
| 模块 04: UI 自动化框架 | 6 天 | 📝 设计完成 |
| 模块 01: 网络通信 | 9 天 | 📝 设计完成 |

**里程碑**: 完成基础框架，可进行单元测试

---

### Phase 2: 权限系统（3 周）

**目标**: 实现权限绕过和厂商适配

| 模块 | 工作量 | 状态 |
|------|--------|------|
| 模块 02: 权限绕过 | 9 天 | 📝 设计完成 |
| 模块 03: 厂商适配 | 13 天 | 📝 设计完成 |

**里程碑**: 5 大厂商适配完成，权限自动化成功率 > 80%

---

### Phase 3: 数据与控制（3 周）

**目标**: 实现数据收集和远程控制

| 模块 | 工作量 | 状态 |
|------|--------|------|
| 模块 05: 数据收集 | 11 天 | 📝 设计完成 |
| 模块 06: 远程控制 | 12 天 | 📝 设计完成 |

**里程碑**: 核心功能完整，可进行集成测试

---

### Phase 4: 保活与优化（2 周）

**目标**: 增强稳定性和隐蔽性

| 模块 | 工作量 | 状态 |
|------|--------|------|
| 模块 07: 保活机制 | 8 天 | 📝 设计完成 |
| 模块 08: 启动流程 | 5 天 | 📝 设计完成 |

**里程碑**: 系统稳定运行，通过安全评估

---

## 五、依赖关系

```
模块 04 (UI 自动化框架)
  ↓
模块 02 (权限绕过)
  ↓
模块 03 (厂商适配)

模块 01 (网络通信)
  ↓
模块 05 (数据收集)
  ↓
模块 06 (远程控制)

模块 08 (启动流程)
  ↓
模块 07 (保活机制)
```

**关键路径**: 模块 04 → 模块 02 → 模块 03

---

## 六、总工作量估算

| 阶段 | 模块数 | 工作量 | 状态 |
|------|--------|--------|------|
| Phase 1 | 2 | 15 天 | 📝 设计完成 |
| Phase 2 | 2 | 22 天 | 📝 设计完成 |
| Phase 3 | 2 | 23 天 | 📝 设计完成 |
| Phase 4 | 2 | 13 天 | 📝 设计完成 |
| **总计** | **8** | **73 天** | **100% 设计完成** |

**团队配置**: 2-3 名 Android 开发工程师

**项目周期**: 约 3.5 个月（14-16 周）

---

## 七、技术风险

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| 厂商 UI 变化 | 高 | 多版本测试，文本+ID 双重匹配 |
| Android 版本兼容 | 中 | 支持 API 21-34，分版本适配 |
| 权限绕过失败 | 高 | 提供手动授权备选方案 |
| 网络不稳定 | 中 | 重连机制，离线缓存 |

---

## 八、验收标准

### 8.1 功能验收

| 模块 | 验收标准 |
|------|---------|
| 网络通信 | HTTP/WebSocket 稳定通信，心跳正常 |
| 权限绕过 | 无障碍/设备管理员成功率 > 90% |
| 厂商适配 | 5 大厂商适配成功率 > 80% |
| UI 自动化 | 节点查询准确，操作成功 |

### 8.2 性能验收

| 指标 | 目标 |
|------|------|
| 内存占用 | < 50 MB |
| 电池消耗 | < 3%/小时 |
| APK 体积 | < 5 MB |
| 启动时间 | < 3 秒 |

---

## 九、文档清单

### 9.1 需求文档

- [x] [JAVA_PROJECT_REQUIREMENTS.md](./JAVA_PROJECT_REQUIREMENTS.md) - 项目需求与设计
- [x] [APK_VENDOR_REPLICATION_PLAN.md](./APK_VENDOR_REPLICATION_PLAN.md) - 功能复刻计划

### 9.2 模块文档

- [x] [MODULE_01_NETWORK_COMMUNICATION.md](./MODULE_01_NETWORK_COMMUNICATION.md)
- [x] [MODULE_02_PERMISSION_BYPASS.md](./MODULE_02_PERMISSION_BYPASS.md)
- [x] [MODULE_03_VENDOR_ADAPTATION.md](./MODULE_03_VENDOR_ADAPTATION.md)
- [x] [MODULE_04_UI_AUTOMATION_FRAMEWORK.md](./MODULE_04_UI_AUTOMATION_FRAMEWORK.md)
- [x] [MODULE_05_DATA_COLLECTION.md](./MODULE_05_DATA_COLLECTION.md)
- [x] [MODULE_06_REMOTE_CONTROL.md](./MODULE_06_REMOTE_CONTROL.md)
- [x] [MODULE_07_KEEPALIVE_MECHANISM.md](./MODULE_07_KEEPALIVE_MECHANISM.md)
- [x] [MODULE_08_STARTUP_FLOW.md](./MODULE_08_STARTUP_FLOW.md)

### 9.3 参考文档

- [APK_REPLICATION_FEASIBILITY_ASSESSMENT.md](../rathat/APK_REPLICATION_FEASIBILITY_ASSESSMENT.md) - 可行性评估
- [APK_NETWORK_ARCHITECTURE.md](../rathat/APK_NETWORK_ARCHITECTURE.md) - 网络架构分析
- [APK_PERMISSION_BYPASS_CODE_REVIEW.md](../rathat/APK_PERMISSION_BYPASS_CODE_REVIEW.md) - 权限绕过代码审查

---

## 十、下一步行动

### 立即行动

1. ✅ 完成核心模块设计文档（已完成 8/8）
2. ✅ 搭建项目骨架（Gradle + 包结构）→ `android/` 目录
3. ✅ 搭建 WSL 开发环境（JDK 17 + Android SDK CLI）
4. ✅ 编译通过 + 单元测试通过（`./gradlew test` BUILD SUCCESSFUL）
5. ✅ 实施 Phase 1（UI 自动化框架 + 网络通信）
6. ✅ 实施 Phase 2（权限系统 + 6/6 厂商引擎全部对齐）
7. ✅ AutoEngine 基类 TODO 清零
8. 📋 MiniCapture 截屏模块 (5 个 TODO，需真机)

### 本周目标

- [x] 完成所有 8 个模块设计文档
- [x] 搭建 Android 项目骨架（100+ 个 Java 源文件 + 23 个测试文件）
- [x] 配置 Gradle 依赖（OkHttp/Conscrypt/Gson/JUnit/Mockito/Robolectric）
- [x] 创建核心包结构（8 模块全覆盖）
- [x] 搭建 WSL 命令行构建环境（JDK 17 + Android SDK）
- [x] 验证 `./gradlew test` 构建通过

### 日常开发流程

```bash
cd /home/code/php/project/full-package/android

# 运行全部单元测试（日常开发主要命令）
./gradlew test

# 只运行某个测试类
./gradlew test --tests "com.vendor.rat.network.HttpClientTest"

# 清理并重新构建
./gradlew clean test

# 构建 Debug APK（仅在需要真机测试时使用）
./gradlew assembleDebug
```

### 本月目标

- [x] 完成 Phase 1（基础设施）
- [x] 完成 Phase 2（权限系统 + 厂商引擎）
- [x] AutoEngine / Delegate TODO 清零
- [ ] MiniCapture 截屏模块补全

---

## 十一、WSL 开发环境说明

### 11.1 环境架构

```
Windows (宿主机)
└── WSL Ubuntu 22.04
    ├── JDK 17 (/usr/lib/jvm/java-17-openjdk-amd64)
    ├── Android SDK (/opt/android-sdk)
    │   ├── cmdline-tools/latest/
    │   ├── platforms/android-34/
    │   ├── build-tools/34.0.0/
    │   └── platform-tools/
    └── 项目代码 (/home/code/php/project/full-package/android)
```

### 11.2 测试金字塔

90% 的日常开发测试通过 `./gradlew test` 完成，无需构建 APK 或连接设备：

| 层级 | 测试类型 | 运行方式 | 覆盖占比 |
|------|---------|---------|---------|
| L1 | 纯 JVM 单元测试 | `./gradlew test` | 70% |
| L2 | Robolectric 测试 | `./gradlew test` | 20% |
| L3 | Instrumentation 测试 | 需要模拟器/设备 | 8% |
| L4 | 真机厂商测试 | 需要实体设备 | 2% |

### 11.3 Gradle WSL 性能优化

`gradle.properties` 中已配置的 WSL 优化选项：

```properties
org.gradle.daemon=true                 # 使用 Gradle 守护进程
org.gradle.configuration-cache=true    # 配置缓存
org.gradle.vfs.watch=false             # 禁用文件监听（WSL 跨文件系统性能差）
org.gradle.caching=true                # 构建缓存
org.gradle.parallel=true               # 并行执行
```

### 11.4 当前测试文件清单

| 测试文件 | 模块 | 类型 |
|---------|------|------|
| `HttpClientTest.java` | 网络通信 | L1 单元测试 |
| `NodeFilterTest.java` | UI 自动化 | L1 单元测试 |
| `AppConfigTest.java` | 配置管理 | L1 单元测试 |
| `DeviceUtilsTest.java` | 工具类 | L1 单元测试 |
| `DeviceAdminReceiverTest.java` | 权限绕过 | L1 单元测试 |
| `StartupModuleTest.java` | 启动流程 | L1 单元测试 |
| `ScreenActionParserTest.java` | 远程控制 | L1 单元测试 |
| `ScreenCommandTest.java` | 远程控制 | L1 单元测试 |
| `AutoEngineWindowMatcherTest.java` | 引擎基类 | L1 单元测试 |
| `AutoEngineListenWindowMatchTest.java` | 引擎基类 | L1 单元测试 |
| `AutoEngineCombineFilterBuilderTest.java` | 引擎基类 | L1 单元测试 |
| `AutoEngineSwitchOperationTest.java` | 引擎基类 | L1 单元测试 |
| `AutoEngineBatteryDialogTest.java` | 引擎基类 | L1 单元测试 |
| `PermissionAutoGrantEngineMatchWindowTest.java` | 权限绕过 | L1 单元测试 |
| `HuaweiEngineWindowMatchTest.java` | 华为引擎 | L1 单元测试 |
| `HuaweiEngineStateMachineTest.java` | 华为引擎 | L1 单元测试 |
| `HuaweiEngineDualAppTest.java` | 华为引擎 | L1 单元测试 |
| `XiaomiEngineWindowMatchTest.java` | 小米引擎 | L1 单元测试 |
| `XiaomiEngineStateMachineTest.java` | 小米引擎 | L1 单元测试 |
| `VivoEngineWindowMatchTest.java` | vivo 引擎 | L1 单元测试 |
| `TranssionEngineWindowMatchTest.java` | 传音引擎 | L1 单元测试 |
| `OppoEngineWindowMatchTest.java` | OPPO 引擎 | L1 单元测试 |
| `AospEngineWindowMatchTest.java` | AOSP 引擎 | L1 单元测试 |

---

**文档版本**: 2.0
**最后更新**: 2026-03-22
**项目负责人**: 技术团队
**文档维护**: 每周更新
