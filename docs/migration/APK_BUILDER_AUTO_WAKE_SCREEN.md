# APK Builder - 自动唤醒屏幕功能实现

> 本文档记录了自动唤醒屏幕功能的完整实现过程和技术细节

## 问题背景

### 用户需求

用户报告：手动点击锁屏按钮后，应用立即唤醒屏幕，即使已在前端关闭"自动唤醒屏幕"功能。

### 初步分析

最初认为只需要移除 `AndroidManifest.xml` 中的 `android:turnScreenOn="true"` 属性即可，但测试发现无效。

## 根本原因

### Android 屏幕唤醒机制

Android 应用可以通过**两种机制**唤醒屏幕：

1. **Manifest 属性**（声明式）
   - `android:turnScreenOn="true"`
   - `android:showOnLockScreen="true"`
   - `android:showWhenLocked="true"`

2. **Window Flags**（代码式）
   - `FLAG_SHOW_WHEN_LOCKED (0x80000)`
   - `FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20)`
   - `FLAG_TURN_SCREEN_ON (0x200000)`

### 关键发现

**这两种机制必须同时存在才能生效！**

在 `TransparentActivity.smali` 中发现硬编码的 Window flags：

```smali
# 第 285 行
const/high16 v0, 0x80000
invoke-virtual {p1, v0}, Landroid/view/Window;->addFlags(I)V

# 第 293 行
const/16 v0, 0x20
invoke-virtual {p1, v0, v0}, Landroid/view/Window;->setFlags(II)V
```

这些代码在 `onCreate()` 中直接调用 `window.addFlags()`，**覆盖了 Manifest 配置**。

## 解决方案

### 架构设计

采用**双重防护**策略：

1. **Manifest 层**：移除 TransparentActivity 的唤醒属性
2. **Smali 层**：移除字节码中的 Window flags 调用

### 实现细节

#### 1. Manifest 属性移除

**位置**: `ApkBuilder::modifyTransparentActivityWakeScreen()`

**实现**:
```php
private function modifyTransparentActivityWakeScreen(string $content, ApkBuildConfig $config): string
{
    if (!$config->enableAutoWakeScreen) {
        // 移除 turnScreenOn
        $content = preg_replace(
            '/(<activity[^>]*android:name="[^"]*TransparentActivity"[^>]*)\s*android:turnScreenOn="true"\s*/',
            '$1 ',
            $content
        );
        // 移除 showOnLockScreen
        $content = preg_replace(
            '/(<activity[^>]*android:name="[^"]*TransparentActivity"[^>]*)\s*android:showOnLockScreen="true"\s*/',
            '$1 ',
            $content
        );
        // 移除 showWhenLocked
        $content = preg_replace(
            '/(<activity[^>]*android:name="[^"]*TransparentActivity"[^>]*)\s*android:showWhenLocked="true"\s*/',
            '$1 ',
            $content
        );
        Log::channel('apk')->info('Disabled auto-wake screen for TransparentActivity');
    }
    return $content;
}
```

#### 2. Smali Window Flags 移除

**位置**: `SmaliProcessor::removeWakeScreenFlags()`

**实现**:
```php
public function removeWakeScreenFlags(bool $enableAutoWakeScreen): void
{
    if ($enableAutoWakeScreen) {
        return; // 保持默认行为
    }

    $transparentActivityPath = $this->buildDir . '/smali/com/icontrol/protector/TransparentActivity.smali';
    
    if (!File::exists($transparentActivityPath)) {
        return;
    }

    $content = File::get($transparentActivityPath);
    
    // 移除 FLAG_SHOW_WHEN_LOCKED (0x80000)
    $content = preg_replace(
        '/\s*const\/high16\s+v\d+,\s*0x80000\s*\n\s*invoke-virtual\s+\{[^}]+\},\s*Landroid\/view\/Window;->addFlags\(I\)V\s*\n/m',
        "\n",
        $content
    );
    
    // 移除 FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20)
    $content = preg_replace(
        '/\s*const\/16\s+v\d+,\s*0x20\s*\n\s*invoke-virtual\s+\{[^}]+\},\s*Landroid\/view\/Window;->setFlags\(II\)V\s*\n/m',
        "\n",
        $content
    );

    File::put($transparentActivityPath, $content);
}
```

#### 3. 调用集成

**位置**: `ApkBuilder::modifySmali()`

```php
private function modifySmali(ApkBuildConfig $config): void
{
    $this->getSmaliProcessor()->modifyConfig($config, $this->assetsKey, $this->encryptor);
    $this->getSmaliProcessor()->removeWakeScreenFlags($config->enableAutoWakeScreen);
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
