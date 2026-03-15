# APK Builder - 自动唤醒屏幕功能实现

> 本文档记录了自动唤醒屏幕功能的完整实现过程和技术细节

---

## ⚠️ 重要更正

**唤醒触发机制**：

本文档主要描述了如何**禁用**自动唤醒功能（移除 Manifest 属性和 Window Flags）。但关于**触发机制**的描述需要更正：

- ❌ **之前的说法**：自动唤醒由 WebSocket 消息触发
- ✅ **实际机制**：自动唤醒由 **AccessServices 监听锁屏事件**触发

**完整触发流程**：
```
点击锁屏按钮
  ↓
AccessServices.onAccessibilityEvent() 检测到锁屏
  ↓
启动 TransparentActivity
  ↓
Manifest 属性 (turnScreenOn) + Window Flags 双重机制
  ↓
屏幕立即唤醒
```

详见：[APK_KEEP_ALIVE_MECHANISM.md](./APK_KEEP_ALIVE_MECHANISM.md) - 无障碍服务保活章节

---

## 问题背景

### 用户需求

用户报告：手动点击锁屏按钮后，应用立即唤醒屏幕，即使已在前端关闭"自动唤醒屏幕"功能。

### 初步分析

最初认为只需要移除 `AndroidManifest.xml` 中的 `android:turnScreenOn="true"` 属性即可，但测试发现无效。

## 根本原因

### Android 屏幕唤醒机制

Android 应用可以通过**四种机制**唤醒屏幕：

1. **Manifest 属性**（声明式）
   - `android:turnScreenOn="true"`
   - `android:showOnLockScreen="true"`
   - `android:showWhenLocked="true"`

2. **Window Flags**（代码式）
   - `FLAG_SHOW_WHEN_LOCKED (0x80000)`
   - `FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20)`
   - `FLAG_TURN_SCREEN_ON (0x200000)`

3. **PowerManager WakeLock**（系统级）
   - `ACQUIRE_CAUSES_WAKEUP (0x10000000)` — 获取 WakeLock 时唤醒屏幕
   - `SCREEN_BRIGHT_WAKE_LOCK (0xa)` — 保持屏幕亮
   - `SCREEN_DIM_WAKE_LOCK (0xa)` — 保持屏幕暗亮

4. **Activity 启动**（间接）
   - 通过启动带唤醒属性的 Activity 间接唤醒屏幕

### 关键发现

经过 ADB 调试发现，屏幕唤醒涉及 **4 条代码路径**：

| 路径 | 文件 | 机制 | 标志值 |
|------|------|------|--------|
| 1 | `AndroidManifest.xml` | TransparentActivity 声明属性 | `turnScreenOn`/`showWhenLocked` |
| 2 | `TransparentActivity.smali` | Window flags | `0x80000` / `0x20` |
| 3 | `a.smali` | PowerManager.newWakeLock | `0x3000001a` (SCREEN_BRIGHT + ACQUIRE_CAUSES_WAKEUP) |
| 4 | `WorkServices.smali` | PowerManager.newWakeLock | `0x1000000a` (SCREEN_DIM + ACQUIRE_CAUSES_WAKEUP) |
| 5 | `a$a.smali` | 启动 TransparentActivity | Intent → startActivity |

**ADB 调试确认**：通过 `dumpsys power` 和 `logcat` 发现 `WorkServices:ScreenLock` 是主要唤醒源：
```
PowerManagerService: Waking up from Asleep (uid=10357, reason=WAKE_REASON_APPLICATION, details=WorkServices:ScreenLock)
```

## 解决方案

### 架构设计

采用**五重防护**策略，覆盖所有唤醒代码路径：

1. **Manifest 层**：移除 TransparentActivity 的唤醒属性
2. **Smali 层**：移除 TransparentActivity 字节码中的 Window flags
3. **WakeLock 层 (a.smali)**：将 `ACQUIRE_CAUSES_WAKEUP` WakeLock 降级为 `PARTIAL_WAKE_LOCK`
4. **WakeLock 层 (WorkServices.smali)**：将 `ACQUIRE_CAUSES_WAKEUP` WakeLock 降级为 `PARTIAL_WAKE_LOCK`
5. **Activity 层 (a$a.smali)**：阻止启动 TransparentActivity

### 实现细节

#### 1. Manifest 属性移除

**位置**: `ApkBuilder::modifyTransparentActivityWakeScreen()`

移除 TransparentActivity 的 `turnScreenOn`、`showOnLockScreen`、`showWhenLocked` 属性。

#### 2. Smali Window Flags 移除 (TransparentActivity)

**位置**: `SmaliProcessor::removeWakeScreenFlags()` — Path 1

移除 `FLAG_SHOW_WHEN_LOCKED (0x80000)` 和 `FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20)` 的 addFlags/setFlags 调用。

#### 3. WakeLock 降级 (a.smali)

**位置**: `SmaliProcessor::removeWakeScreenFlags()` — Path 2

```
0x3000001a = SCREEN_BRIGHT_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE
→ 改为 0x1 = PARTIAL_WAKE_LOCK（保持CPU唤醒但不点亮屏幕）
```

#### 4. WakeLock 降级 (WorkServices.smali)

**位置**: `SmaliProcessor::removeWakeScreenFlags()` — Path 2b

这是 ADB 调试发现的**主要唤醒源**（`WorkServices:ScreenLock`）：

```
0x1000000a = ACQUIRE_CAUSES_WAKEUP | SCREEN_DIM_WAKE_LOCK → 0x1 (PARTIAL_WAKE_LOCK)
0x20000001 = ON_AFTER_RELEASE | PARTIAL_WAKE_LOCK → 0x1 (PARTIAL_WAKE_LOCK)
```

#### 5. 阻止 TransparentActivity 启动 (a$a.smali)

**位置**: `SmaliProcessor::removeWakeScreenFlags()` — Path 3

在 `a$a.smali` 的 `run()` 方法开头插入 `return-void`，阻止启动 TransparentActivity。

### 完整实现代码

**位置**: `SmaliProcessor::removeWakeScreenFlags()`

```php
public function removeWakeScreenFlags(bool $enableAutoWakeScreen): void
{
    if ($enableAutoWakeScreen) {
        return;
    }

    // Path 1: TransparentActivity.smali Window flags
    // 移除 FLAG_SHOW_WHEN_LOCKED (0x80000) 和 FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20)

    // Path 2: a.smali WakeLock
    // 0x3000001a → 0x1 (PARTIAL_WAKE_LOCK)

    // Path 2b: WorkServices.smali WakeLock
    // 0x1000000a → 0x1 (PARTIAL_WAKE_LOCK)
    // 0x20000001 → 0x1 (PARTIAL_WAKE_LOCK)

    // Path 3: a$a.smali
    // 在 run() 开头插入 return-void，阻止启动 TransparentActivity
}
```

## 测试验证

### 单元测试

**文件**: `tests/Unit/ApkBuilder/SmaliProcessorWakeFlagsTest.php`

**测试用例**:
1. 移除 FLAG_SHOW_WHEN_LOCKED (0x80000) 当禁用自动唤醒时 ✅
2. 移除 FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20) 当禁用自动唤醒时 ✅
3. 保留 Window flags 当启用自动唤醒时 ✅
4. 不报错当 TransparentActivity 文件不存在时 ✅
5. 同时移除两个 flags 当禁用自动唤醒时 ✅

**运行结果**: 5/5 通过

### 手动测试

#### 测试 1: 禁用自动唤醒

```bash
# 1. 修改配置
echo '{"enable_auto_wake_screen": false}' > config.json

# 2. 构建 APK
./vendor/bin/sail artisan apk:build --config=config.json --no-interaction

# 3. 安装到手机
adb install com.truezen.pixelsafe.apk

# 4. 测试流程
# - 启动应用
# - 按 Home 键切换到后台
# - 等待 10 秒
# - 按电源键锁屏
# - 等待 10 秒
# - 检查屏幕状态

# 5. 验证结果
adb shell dumpsys power | grep mWakefulness
# 输出: mWakefulness=Asleep ✅
```

#### 测试 2: 启用自动唤醒

```bash
# 1. 修改配置
echo '{"enable_auto_wake_screen": true}' > config.json

# 2. 构建 APK
./vendor/bin/sail artisan apk:build --config=config.json --no-interaction

# 3. 安装到手机
adb install com.findsync.cleanhandy.apk

# 4. 测试流程
# - 启动应用
# - 按 Home 键切换到后台
# - 等待 5 秒
# - 按电源键锁屏
# - 等待 3 秒
# - 检查屏幕状态

# 5. 验证结果
adb shell dumpsys power | grep mWakefulness
# 输出: mWakefulness=Awake ✅
```

## 其他优化

### 1. 移除 ApkBuildConfig readonly 限制

**原因**: 需要在构建过程中动态修改 `appId` 和 `appVersion`

**修改**: 移除所有 49 个属性的 `readonly` 修饰符

```php
// 之前
public readonly string $appId;

// 之后
public string $appId;
```

### 2. 自动生成包名和版本号

**实现**: `ApkBuilder::build()` 方法在验证前自动生成空值

```php
if (empty($config->appId)) {
    $config->appId = $this->generateRandomPackageName();
}
if (empty($config->appVersion)) {
    $config->appVersion = $this->generateRandomVersion();
}
```

**生成规则**:
- 包名: `com.[word][word].[word][word]`
- 版本号: `[1-9].[0-99].[0-99]`

**示例**:
- `com.basenet.touchbase`
- `3.42.17`

### 3. 修复命令输出

**问题**: 构建成功后不显示包名

**解决**: 在 `ApkBuildResult` 中添加 `packageName` 属性

```php
return new ApkBuildResult(
    path: $outputPath,
    packageName: $config->appId,  // 新增
    stats: $this->stepStats,
    totalTimeMs: $totalTime,
);
```

### 4. 修复验证逻辑

**问题**: `ApkBuildConfig::validate()` 拒绝空的 `appId` 和 `appVersion`

**解决**: 修改验证逻辑，允许空值（因为会自动生成）

```php
// appId 允许为空，ApkBuilder 会自动生成
if (! empty($this->appId) && ! preg_match('/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/', $this->appId)) {
    $errors[] = 'app_id must be a valid package name (e.g., com.example.app)';
}

// appVersion 允许为空，ApkBuilder 会自动生成
if (! empty($this->appVersion) && ! preg_match('/^\d+(\.\d+){0,2}$/', $this->appVersion)) {
    $errors[] = 'app_version must be a valid version (e.g., 1.0 or 1.0.0)';
}
```

## 代码统计

### 修改文件

- `ApkBuildConfig.php` - 移除 readonly，修改验证
- `ApkBuilder.php` - 自动生成逻辑，Manifest 修改
- `SmaliProcessor.php` - Window flags 移除
- `ApkBuildResult.php` - 添加 packageName
- `BuildApkCommand.php` - 移除验证逻辑

### 测试文件

- `ApkBuildConfigMutabilityTest.php` - 3 个测试
- `ApkBuilderAutoGenerationTest.php` - 4 个测试
- `SmaliProcessorWakeFlagsTest.php` - 5 个测试

### 提交信息

```
Commit: 1f6ade8e
Message: feat(apk-builder): 实现自动唤醒屏幕配置功能

- 移除 ApkBuildConfig readonly 限制，允许运行时修改属性
- 实现 appId 和 appVersion 为空时自动生成随机值
- 修复 BuildApkCommand 验证逻辑，允许空值传递
- 添加自动唤醒屏幕配置功能（enable_auto_wake_screen）
- 移除 Manifest 中的 turnScreenOn/showOnLockScreen/showWhenLocked 属性
- 移除 Smali 代码中的 Window flags (0x80000, 0x20)
- 修复命令输出显示正确的包名和路径
- 添加 12 个单元测试验证功能

Files changed: 17
Insertions: 1851
Deletions: 358
```

## 技术要点

### 1. Manifest vs Smali

- **Manifest 属性**：声明式配置，在 APK 安装时解析
- **Smali 代码**：运行时执行，可以覆盖 Manifest 配置
- **结论**：必须同时处理两个层面才能彻底控制行为

### 2. Window Flags 常量

| 常量 | 十六进制 | 作用 |
|------|---------|------|
| FLAG_SHOW_WHEN_LOCKED | 0x80000 | 在锁屏界面显示 |
| FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | 0x20 | 允许锁屏时保持屏幕开启 |
| FLAG_TURN_SCREEN_ON | 0x200000 | 唤醒屏幕（未在代码中发现） |

### 3. WakeLock 常量

| 常量 | 十六进制 | 作用 |
|------|---------|------|
| PARTIAL_WAKE_LOCK | 0x1 | 保持CPU唤醒，不点亮屏幕 |
| SCREEN_DIM_WAKE_LOCK | 0xa | 保持屏幕暗亮 |
| SCREEN_BRIGHT_WAKE_LOCK | 0xa | 保持屏幕亮 |
| ACQUIRE_CAUSES_WAKEUP | 0x10000000 | 获取时唤醒屏幕 |
| ON_AFTER_RELEASE | 0x20000000 | 释放后保持屏幕亮一段时间 |

### 4. 唤醒代码路径汇总

| 文件 | 标志值 | 含义 | 处理方式 |
|------|--------|------|---------|
| `TransparentActivity.smali` | `0x80000` | FLAG_SHOW_WHEN_LOCKED | 移除 addFlags 调用 |
| `TransparentActivity.smali` | `0x20` | FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | 移除 setFlags 调用 |
| `a.smali` | `0x3000001a` | SCREEN_BRIGHT + ACQUIRE_CAUSES_WAKEUP + ON_AFTER_RELEASE | 改为 0x1 (PARTIAL) |
| `WorkServices.smali` | `0x1000000a` | SCREEN_DIM + ACQUIRE_CAUSES_WAKEUP | 改为 0x1 (PARTIAL) |
| `WorkServices.smali` | `0x20000001` | ON_AFTER_RELEASE + PARTIAL | 改为 0x1 (PARTIAL) |
| `a$a.smali` | N/A | 启动 TransparentActivity | 插入 return-void |
| `AndroidManifest.xml` | N/A | turnScreenOn/showWhenLocked | 移除属性 |

### 3. 正则表达式设计

**Smali 代码模式**:
```regex
/\s*const\/high16\s+v\d+,\s*0x80000\s*\n\s*invoke-virtual\s+\{[^}]+\},\s*Landroid\/view\/Window;->addFlags\(I\)V\s*\n/m
```

**关键点**:
- `\s*` - 匹配可选的空白字符
- `v\d+` - 匹配任意寄存器（v0, v1, v2...）
- `\{[^}]+\}` - 匹配参数列表
- `/m` - 多行模式

### 4. 模板文件管理

**问题**: 构建过程中模板文件被意外修改

**原因**: 之前的构建过程中，模板 `AndroidManifest.xml` 被错误修改

**解决**: 从 `apkstub.zip` 重新解压模板文件

```bash
rm -rf storage/app/apk/template
unzip -q storage/app/apk/apkstub/apkstub.zip -d storage/app/apk/template
```

## 经验教训

1. **Android 机制复杂性** - 屏幕唤醒涉及多个层面，需要全面分析
2. **代码覆盖配置** - Java/Kotlin 代码可以覆盖 Manifest 配置
3. **测试驱动开发** - 单元测试帮助快速验证功能，避免频繁构建 APK
4. **模板文件保护** - 构建过程应该只修改工作目录副本，不应修改模板本身
5. **双重验证** - 自动化测试 + 手动测试，确保功能完全正确

## 相关文档

- [APK_BUILDER.md](./APK_BUILDER.md) - APK Builder 主文档
- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - WebSocket 客户端文档
- [DEVICE_STATUS_FIELDS.md](./DEVICE_STATUS_FIELDS.md) - 设备状态字段参考
