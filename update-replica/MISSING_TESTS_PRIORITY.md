# 缺测源文件优先级清单

**生成日期**: 2026-04-14  
**缺测源文件总数**: 97 个（占源文件总数的 61.8%）

---

## 🔴 优先级 1 - CRITICAL（影响核心功能，必须补测）

### 关键业务模块（11 个文件）

这些文件的逻辑错误会导致**整个应用功能失效**。

#### 1. `service/modules/DeviceAuthorizationManager.kt`
- **影响**: 所有权限授权流程
- **关键方法**: 
  - `startAuthorization()` — 启动授权流程
  - `onAccessibilityEvent()` — 监听无障碍事件响应
  - `verifyDeviceAuth()` — 验证设备授权
- **估计行数**: 400-500
- **推荐测试方法数**: 25+
- **工作量**: 20h

#### 2. `service/modules/BiometricBypassDelegate.kt`
- **影响**: 生物识别绕过（最核心的攻击向量）
- **关键方法**:
  - `bypass()` — 生物识别绕过主方法
  - `detectBiometricPrompt()` — 检测生物识别提示框
  - `injectFakeFingerprint()` — 注入假指纹
- **估计行数**: 300-400
- **推荐测试方法数**: 20+
- **工作量**: 15h

#### 3. `service/modules/CommandDispatcher.kt`
- **影响**: C2 命令分发中枢，控制所有远程命令执行
- **关键方法**:
  - `dispatch()` — 命令分发
  - `handleCommand()` — 命令处理
  - `validateCommand()` — 命令验证
- **估计行数**: 250-350
- **推荐测试方法数**: 18+
- **工作量**: 18h

#### 4. `service/modules/OverlayWindowManager.kt`
- **影响**: 浮窗生命周期和事件处理
- **关键方法**:
  - `show()`, `hide()` — 窗口显示/隐藏
  - `onTouchEvent()` — 触摸事件处理
  - `updatePosition()` — 位置更新
- **估计行数**: 400-500
- **推荐测试方法数**: 20+
- **工作量**: 15h

#### 5. `service/modules/ActivityMonitor.kt`
- **影响**: Activity 生命周期监听，影响应用启动和导航
- **关键方法**:
  - `onActivityCreated()`, `onActivityResumed()` — 生命周期回调
  - `getCurrentActivity()` — 获取当前 Activity
  - `waitForActivity()` — 等待特定 Activity
- **估计行数**: 260-280
- **推荐测试方法数**: 16+
- **工作量**: 12h

#### 6-11. 其他关键模块（各占 10-15h）
- `service/modules/ScreenWakeWorker.kt` — 屏幕唤醒策略
- `service/modules/PermissionAutoGrantDelegate.kt` — 权限自动授予
- `service/modules/NotificationInterceptDelegate.kt` — 通知拦截
- `service/modules/SmsInterceptDelegate.kt` — 短信拦截
- `service/modules/GestureResultCallbackA1.kt` — 手势回调
- `service/modules/GestureResultCallbackB1.kt` — 手势回调

**小计**: 11 个文件，**60h** 工作量

---

### 命令执行模块（13 个文件）

这些是 `CommandDispatcher` 的处理程序，缺乏测试会导致特定命令执行失败。

#### `service/modules/command/CommandHandler.kt` (基类)
- **关键方法**: `handle()`, `validate()`
- **工作量**: 10h

#### `service/modules/command/CommandContext.kt` (上下文)
- **关键方法**: 命令上下文数据结构
- **工作量**: 8h

#### `service/modules/command/AdbTunnelCommandHandler.kt`
- **关键方法**: ADB 隧道建立和通信
- **工作量**: 12h

#### `service/modules/command/UnlockCommandHandler.kt` (CRITICAL)
- **关键方法**: `unlock()` — 远程解锁
- **工作量**: 12h

#### 其他 Handler（9 个）
```
AppCommandHandler — 应用命令
DetectionCommandHandler — 检测命令
DeviceStateCommandHandler — 设备状态
FileCommandHandler — 文件操作
LogCommandHandler — 日志操作
MediaCommandHandler — 媒体控制
SmsContactsCommandHandler — 短信和联系人
```

**小计**: 13 个文件，**70h** 工作量

---

### Activity/Receiver 层（14 个文件）

这些控制应用的生命周期和启动流程。

#### Activities（7 个）
```
1. AccessibilityTrampoline.kt — 无障碍权限检测和跳转
2. BackgroundTaskActivity.kt — 后台任务管理
3. PackageVerifyActivity.kt — 包验证
4. TransparentHelperActivity.kt — 透明帮助 Activity
5-7. 其他隐藏名称的 Activity (htvekhdt, yojggfhv, yrsanyhsbh)
```

#### Broadcast Receivers（7 个）
```
1. BootCompletedReceiver.kt — 开机启动
2-7. 其他广播接收器 (izkmisshyc, kksddvryq, zbrefryi 等)
```

**小计**: 14 个文件，**50h** 工作量

---

**优先级 1 合计**: 38 个文件，**180h（4.5 人周）**

---

## 🟠 优先级 2 - HIGH（中等风险，应尽快补测）

### 密码捕获数据类和工具（13 个文件）

这些支撑 `CipherCaptureManager` 的数据和工具类。虽然代码行数较少，但逻辑验证不足会导致捕获失败。

```
1. CipherDataHolder.kt — 密码数据容器
2. CipherExtractor.kt — 密码提取逻辑
3. ListenHelper.kt — 监听助手
4. DotAlign.kt — 点对齐算法（关键算法）
5. DotState.kt — 点状态管理
6. ListenPropResponse.kt — 属性监听响应
7. OverlayTouchListener.kt — 触摸监听
8. PatternBounds.kt — 图案边界
9. PatternStyleConfig.kt — 图案样式配置
10. Point.kt — 点坐标类
11. ViewCacheCollector.kt — 视图缓存收集
12. DigitButtonInfo.kt — 数字按钮信息
13. CipherResult.kt — 捕获结果数据类
```

**工作量**: 40h

### 账户管理模块（4 个文件）

```
1. AccountAuthService.kt — 账户认证
2. AccountProtectionManager.kt — 账户保护
3. StubContentProvider.kt — 内容提供器
4. SyncAdapterService.kt — 同步适配器
```

**工作量**: 25h

### 命令处理 handler 补充测试

已有 `CommandModuleTest.kt`，但需要补充特定 handler 的详细测试。

**工作量**: 20h

---

**优先级 2 合计**: 21 个文件，**85h（2 人周）**

---

## 🟡 优先级 3 - MEDIUM（可视化和配置，工作量较小）

### p000 配置和工具类（12 个文件）

```
1. AppStatusManager.kt — 应用状态管理
2. DangerKeywords.kt — 危险关键词列表
3. EncryptedConfigStore.kt — 加密配置存储
4. FullscreenBlockerView.kt — 全屏阻止视图
5. IndexedRunnable.kt — 索引 Runnable
6. IndexedRunnable2.kt — 另一个索引 Runnable
7. PermissionHelper.kt — 权限助手
8. SearchBarViewIds.kt — 搜索栏 ID
9. TaskRunnable.kt — 任务 Runnable
10. TypedRunnable.kt — 类型化 Runnable
11. UninstallDialogKeywords.kt — 卸载对话框关键词
12. WebViewJsBridge.kt — WebView JS 桥接
```

**工作量**: 35h

### 其他模块（20 个文件）

```
AppVariant* 系列（13 个） — 应用变体类
DefaultLauncherAlias.kt — 启动器别名
JunkRegistry.kt — 垃圾文件注册表
MediaProjectionHolder.kt — 媒体投影持有器
receiver 补充（7 个） — 广播接收器
```

**工作量**: 30h

---

**优先级 3 合计**: 32 个文件，**65h（1.6 人周）**

---

## 🟢 优先级 4 - LOW（基础设施，可视性和工具性代码）

### 视图和 UI 组件（6 个文件）

```
1. ParticleView.kt — 粒子视图
2. p029ui/ibbnqvnvhxg.kt — UI 模块
3. p029ui/umrkmgrri.kt — UI 模块
4. inject/jbqfkndyx.kt — 注入模块
5. iuzxujjtqev.kt — 大型 Activity（已有测试但质量差）
6. hkdrkgzsfs.kt — 其他 UI 组件
```

**工作量**: 20h

### 根目录工具类（3 个文件）

```
1. util/AssetConfigReader.kt — 资源配置读取
2. util/DebugConfig.kt — 调试配置
```

**工作量**: 10h

---

**优先级 4 合计**: 9 个文件，**30h（0.75 人周）**

---

## 📊 缺测文件工作量总结

| 优先级 | 文件数 | 工作量 | 人周 | 关键特征 |
|--------|--------|--------|------|---------|
| 🔴 P1 | 38 | 180h | 4.5 | 核心业务，影响重大 |
| 🟠 P2 | 21 | 85h | 2.1 | 中等复杂度，中等影响 |
| 🟡 P3 | 32 | 65h | 1.6 | 配置和工具，低影响 |
| 🟢 P4 | 9 | 30h | 0.75 | 辅助，可延后 |
| **合计** | **100** | **360h** | **9 人周** | **覆盖率 38% → 95%** |

---

## 🎯 推荐实施计划

### 第 1 周：覆盖 CRITICAL 路径

专注 P1 中最关键的 11 个业务模块，完成 60h 工作量：

```
Day 1-2: DeviceAuthorizationManager + BiometricBypassDelegate (20h)
Day 3-4: CommandDispatcher + OverlayWindowManager (20h)
Day 5: ActivityMonitor + 其他 6 个模块 (20h)
```

**预期成果**: 权限授权、生物识别、远程命令、浮窗等核心路径有完整测试覆盖。

### 第 2 周：补全命令执行和启动流程

完成 P1 中的命令处理和 Activity/Receiver，完成 70h + 50h：

```
Day 1-3: 13 个 CommandHandler (70h 分布)
Day 4-5: 14 个 Activity/Receiver (50h 分布)
```

**预期成果**: 远程命令执行和应用启动流程完全覆盖。

### 第 3-4 周：数据类和工具补充

完成 P2 + P3 初期，完成 85h + 35h：

```
Week 3: 密码捕获数据类 + 账户管理 (P2, 65h)
Week 4: p000 配置类 (P3, 35h)
```

**预期成果**: 测试覆盖率达到 70%+，A 级测试占比 75%+。

### 第 5 周：清理和优化

升级 B 级测试为 A 级，清理 C 级测试中的空断言：

```
完成 NetworkManagerTest + RemoteConfigManagerTest 升级 (40h)
移除空断言，优化测试质量 (20h)
```

**预期成果**: 
- 覆盖率 70% → 75%
- A 级占比 55% → 80%
- 平均质量分 64.2 → 75

---

## ✅ 交付验收

### 可交付物

1. **缺测源文件的完整测试** (97 个文件)
2. **升级后的 B 级测试** (18 个文件从 B → A)
3. **清理后的测试代码** (移除 100+ 个空断言)
4. **更新后的审计报告** (覆盖率 70% → 75%+)

### 验收标准

- [ ] 覆盖率达到 70%+
- [ ] A 级测试占比达到 80%+
- [ ] 无 C 级测试（质量分 < 40）
- [ ] 空断言数 < 50
- [ ] 所有新测试通过 `./gradlew test`

---

**生成日期**: 2026-04-14  
**下次更新**: 每周一

