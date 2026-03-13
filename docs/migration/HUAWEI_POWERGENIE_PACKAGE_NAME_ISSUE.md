# 华为 PowerGenie 测试脚本包名问题分析与解决方案

## 问题描述

**现象**：
- 每次执行 `./vendor/bin/sail artisan apk:build --config=app/scripts/config.json` 构建 APK 时，包名都会随机变化
- 测试脚本 `app/scripts/test-huawei-powergenie.sh` 硬编码了包名 `com.sunsimple.taplite`（第 16 行）
- 导致测试脚本无法找到新构建的 APK，测试失败

**影响**：
- 无法自动化测试华为 PowerGenie 黑屏断连问题
- 每次构建后需要手动修改测试脚本中的包名
- 降低了调试效率

---

## 根因分析

### 包名动态生成机制

**位置**：`app/app/Services/ApkBuilder/ApkBuilder.php`

```php
// 第 511-524 行：modifyManifest() 方法
private function modifyManifest(ApkBuildConfig $config): void
{
    $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
    $content = $this->fileSystem->get($manifestPath);
    
    $oldPackage = ApkBuilderConstants::DEFAULT_PACKAGE;
    
    // 每次构建都生成随机包名
    $newPackage = $this->generateRandomPackageName();
    $versionMajor = random_int(1, 9);
    $versionMinor = random_int(0, 9);
    $versionPatch = random_int(0, 9);
    $versionName = "{$versionMajor}.{$versionMinor}.{$versionPatch}";
    $versionCode = $versionMajor * 100 + $versionMinor * 10 + $versionPatch;
    
    // 包名存储在内部变量中，但不返回给调用者
    $this->selectedPackageInfo = ['pkg' => $newPackage, 'versionCode' => $versionCode, 'versionName' => $versionName];
    
    $content = str_replace($oldPackage, $newPackage, $content);
    $this->getSmaliProcessor()->renamePackage($oldPackage, $newPackage);
    // ...
}

// 第 538-543 行：包名生成算法
private function generateRandomPackageName(): string
{
    $words = ApkBuilderConstants::PACKAGE_NAME_WORDS;
    $w = fn() => $words[array_rand($words)];
    return 'com.' . $w() . $w() . '.' . $w() . $w();
}
```

**包名格式**：`com.{word1}{word2}.{word3}{word4}`

**单词池**（`ApkBuilderConstants.php` 第 135-143 行）：
```php
public const PACKAGE_NAME_WORDS = [
    'smart', 'easy', 'quick', 'fast', 'super', 'pro', 'lite', 'mini', 'max', 'ultra',
    'app', 'tool', 'kit', 'hub', 'lab', 'box', 'pad', 'tap', 'go', 'one',
    'blue', 'green', 'red', 'sky', 'sun', 'star', 'moon', 'cloud', 'wave', 'flow',
    'tech', 'soft', 'net', 'web', 'data', 'code', 'byte', 'pixel', 'core', 'base',
    'click', 'touch', 'swipe', 'scan', 'sync', 'link', 'view', 'find', 'track', 'note',
    'bright', 'clear', 'clean', 'fresh', 'cool', 'calm', 'zen', 'pure', 'true', 'safe',
    'daily', 'handy', 'simple', 'magic', 'power', 'boost', 'prime', 'plus', 'edge', 'peak',
];
```

**示例包名**：
- `com.sunsimple.taplite`
- `com.smartquick.goone`
- `com.bluesky.webdata`

**设计目的**：
- 每次构建生成不同包名，避免华为云端识别为"重打包恶意应用"
- 使用常见英文单词组合，看起来像正常应用

### 当前构建输出

**ApkBuildResult**（`app/app/Services/ApkBuilder/ApkBuildResult.php`）：
```php
public function __construct(
    public string $path,           // ✅ APK 文件路径
    public array $stats = [],      // ✅ 构建步骤耗时
    public float $totalTimeMs = 0, // ✅ 总耗时
) {}
```

**问题**：❌ **不包含包名字段**

**命令行输出**（`BuildApkCommand.php` 第 156-180 行）：
```
╔════════════════════════════════════════╗
║            ✅ 构建成功!                 ║
╚════════════════════════════════════════╝

输出路径: /path/to/output.apk
总耗时: 2.5min
```

**问题**：❌ **不显示包名**

---

## 解决方案

### 方案 1：修改构建系统返回包名（推荐）

**优点**：
- ✅ 最彻底的解决方案
- ✅ 构建命令可以输出包名供其他脚本使用
- ✅ 支持自动化流程（构建 → 安装 → 测试）

**缺点**：
- ⚠️ 需要修改核心代码（3 个文件）

#### 实现步骤

**Step 1：修改 ApkBuildResult 添加包名字段**

文件：`app/app/Services/ApkBuilder/ApkBuildResult.php`

```php
final readonly class ApkBuildResult
{
    public function __construct(
        public string $path,
        public string $packageName,    // 新增：包名
        public array $stats = [],
        public float $totalTimeMs = 0,
    ) {}

    public function toArray(): array
    {
        return [
            'path' => $this->path,
            'package_name' => $this->packageName,  // 新增
            'stats' => [
                'steps' => $this->stats,
                'total_time_ms' => $this->totalTimeMs,
                'total_time_formatted' => $this->formatTime($this->totalTimeMs),
            ],
        ];
    }
}
```

**Step 2：修改 ApkBuilder 返回包名**

文件：`app/app/Services/ApkBuilder/ApkBuilder.php`

```php
// 第 222-226 行：修改返回语句
return new ApkBuildResult(
    path: $outputPath,
    packageName: $this->selectedPackageInfo['pkg'] ?? '',  // 新增
    stats: $this->stepStats,
    totalTimeMs: $totalTime,
);
```

**Step 3：修改命令行输出显示包名**

文件：`app/app/Console/Commands/BuildApkCommand.php`

```php
// 第 163-169 行：修改 displaySuccess() 方法
$this->table(
    ['项目', '值'],
    [
        ['包名', $result->packageName],        // 新增
        ['输出路径', $result->path],
        ['总耗时', $result->formatTime()],
    ]
);
```

#### 测试脚本改进

**方式 A：从构建输出提取包名**

```bash
#!/bin/bash
# 构建 APK 并提取包名
BUILD_OUTPUT=$(./vendor/bin/sail artisan apk:build --config=app/scripts/config.json 2>&1)
PACKAGE_NAME=$(echo "$BUILD_OUTPUT" | grep -oP '包名\s+\|\s+\K[^\s]+' | head -1)

if [ -z "$PACKAGE_NAME" ]; then
    echo "❌ 无法从构建输出提取包名"
    exit 1
fi

echo "✅ 检测到包名: $PACKAGE_NAME"

# 使用提取的包名运行测试
./scripts/test-huawei-powergenie.sh "$PACKAGE_NAME"
```

**方式 B：保存包名到文件**

修改 `BuildApkCommand.php`：

```php
// 构建成功后保存包名
if ($this->option('save')) {
    $build = $this->saveToDatabase($config, $result);
    $this->info("构建记录已保存，ID: {$build->id}");
    
    // 保存包名到临时文件
    file_put_contents(
        storage_path('app/last_build_package.txt'),
        $result->packageName
    );
}
```

测试脚本读取：

```bash
#!/bin/bash
PACKAGE_FILE="/home/code/php/project/full-package/app/storage/app/last_build_package.txt"

if [ ! -f "$PACKAGE_FILE" ]; then
    echo "❌ 未找到包名文件，请先运行构建命令"
    exit 1
fi

PACKAGE_NAME=$(cat "$PACKAGE_FILE")
echo "✅ 从文件读取包名: $PACKAGE_NAME"

# 继续测试流程...
```

---

### 方案 2：测试脚本接受包名参数（简单）

**优点**：
- ✅ 无需修改构建系统
- ✅ 实现简单

**缺点**：
- ⚠️ 需要手动查看构建日志获取包名
- ⚠️ 无法完全自动化

#### 实现

修改 `app/scripts/test-huawei-powergenie.sh`：

```bash
#!/bin/bash
# 华为 PowerGenie 黑屏断连监控脚本
# 用法: ./scripts/test-huawei-powergenie.sh [PACKAGE_NAME]

set -euo pipefail

# ========== 配置 ==========
# 支持命令行参数传入包名
PACKAGE_NAME="${1:-com.sunsimple.taplite}"
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="192.168.31.162:5555"
ADB_CMD="$ADB -s $DEVICE_SERIAL"

echo "使用包名: $PACKAGE_NAME"

# 其余代码保持不变...
```

**使用方式**：

```bash
# 1. 构建 APK（查看输出中的包名）
./vendor/bin/sail artisan apk:build --config=app/scripts/config.json

# 2. 从输出中找到包名（如 com.smartquick.goone）

# 3. 运行测试脚本并传入包名
./scripts/test-huawei-powergenie.sh com.smartquick.goone
```

---

### 方案 3：从 ADB 动态查找最新包名（智能）

**优点**：
- ✅ 完全自动化
- ✅ 无需修改构建系统
- ✅ 适合快速迭代测试

**缺点**：
- ⚠️ 依赖包名模式匹配（可能误匹配）
- ⚠️ 需要 APK 已安装到设备

#### 实现

修改 `app/scripts/test-huawei-powergenie.sh`：

```bash
#!/bin/bash
set -euo pipefail

# ========== 配置 ==========
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="192.168.31.162:5555"
ADB_CMD="$ADB -s $DEVICE_SERIAL"

# ========== 自动检测包名 ==========
log "自动检测最新安装的包名..."

# 获取所有符合模式的包名（com.{word}{word}.{word}{word}）
# 按安装时间排序，取最新的
PACKAGE_NAME=$($ADB_CMD shell pm list packages -3 2>/dev/null | \
    grep -oP 'package:\K(com\.[a-z]+[a-z]+\.[a-z]+[a-z]+)' | \
    while read pkg; do
        install_time=$($ADB_CMD shell dumpsys package "$pkg" 2>/dev/null | \
            grep -oP 'firstInstallTime=\K[0-9-]+ [0-9:]+' || echo "")
        if [ -n "$install_time" ]; then
            echo "$install_time|$pkg"
        fi
    done | \
    sort -r | \
    head -1 | \
    cut -d'|' -f2)

if [ -z "$PACKAGE_NAME" ]; then
    fail "未找到符合模式的包名，请确保 APK 已安装"
    echo "提示: 包名格式应为 com.{word}{word}.{word}{word}"
    exit 1
fi

ok "检测到包名: $PACKAGE_NAME"

# 继续测试流程...
```

**优化版（更精确）**：

```bash
# 从单词池生成正则表达式
WORD_PATTERN="(smart|easy|quick|fast|super|pro|lite|mini|max|ultra|app|tool|kit|hub|lab|box|pad|tap|go|one|blue|green|red|sky|sun|star|moon|cloud|wave|flow|tech|soft|net|web|data|code|byte|pixel|core|base|click|touch|swipe|scan|sync|link|view|find|track|note|bright|clear|clean|fresh|cool|calm|zen|pure|true|safe|daily|handy|simple|magic|power|boost|prime|plus|edge|peak)"

PACKAGE_NAME=$($ADB_CMD shell pm list packages -3 2>/dev/null | \
    grep -oP "package:\Kcom\.${WORD_PATTERN}${WORD_PATTERN}\.${WORD_PATTERN}${WORD_PATTERN}" | \
    head -1)
```

---

## 推荐实施方案

### 短期方案（立即可用）

**方案 2 + 方案 3 组合**：

```bash
#!/bin/bash
# 华为 PowerGenie 黑屏断连监控脚本
# 用法: 
#   ./scripts/test-huawei-powergenie.sh                    # 自动检测
#   ./scripts/test-huawei-powergenie.sh com.example.app    # 手动指定

set -euo pipefail

ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="192.168.31.162:5555"
ADB_CMD="$ADB -s $DEVICE_SERIAL"

# 如果提供了参数，使用参数；否则自动检测
if [ $# -ge 1 ]; then
    PACKAGE_NAME="$1"
    echo "✅ 使用指定包名: $PACKAGE_NAME"
else
    echo "🔍 自动检测包名..."
    PACKAGE_NAME=$($ADB_CMD shell pm list packages -3 2>/dev/null | \
        grep -oP 'package:\Kcom\.[a-z]+\.[a-z]+' | \
        head -1)
    
    if [ -z "$PACKAGE_NAME" ]; then
        echo "❌ 未检测到包名，请手动指定: $0 <package_name>"
        exit 1
    fi
    echo "✅ 检测到包名: $PACKAGE_NAME"
fi

# 继续测试流程...
```

### 长期方案（最佳实践）

**实施方案 1**：修改构建系统返回包名

**配套工具链**：

1. **一键构建测试脚本**（`app/scripts/build-and-test.sh`）：

```bash
#!/bin/bash
set -euo pipefail

echo "=========================================="
echo "APK 构建 + 华为 PowerGenie 测试"
echo "=========================================="

# Step 1: 构建 APK
echo ""
echo "📦 开始构建 APK..."
BUILD_OUTPUT=$(./vendor/bin/sail artisan apk:build \
    --config=app/scripts/config.json \
    --save 2>&1)

echo "$BUILD_OUTPUT"

# Step 2: 提取包名
PACKAGE_NAME=$(echo "$BUILD_OUTPUT" | grep -oP '包名\s+\|\s+\K[^\s]+' | head -1)

if [ -z "$PACKAGE_NAME" ]; then
    echo "❌ 无法提取包名，构建可能失败"
    exit 1
fi

echo ""
echo "✅ 构建完成，包名: $PACKAGE_NAME"

# Step 3: 提取 APK 路径
APK_PATH=$(echo "$BUILD_OUTPUT" | grep -oP '输出路径\s+\|\s+\K[^\s]+' | head -1)

# Step 4: 安装 APK
echo ""
echo "📲 安装 APK 到设备..."
adb -s 192.168.31.162:5555 install -r "$APK_PATH"

# Step 5: 运行测试
echo ""
echo "🧪 开始 PowerGenie 测试..."
./scripts/test-huawei-powergenie.sh "$PACKAGE_NAME"
```

2. **CI/CD 集成**：

```yaml
# .github/workflows/huawei-test.yml
name: Huawei PowerGenie Test

on:
  push:
    paths:
      - 'app/storage/app/apk/template/**'
      - 'app/app/Services/ApkBuilder/**'

jobs:
  test:
    runs-on: self-hosted
    steps:
      - uses: actions/checkout@v3
      
      - name: Build APK
        id: build
        run: |
          cd app
          OUTPUT=$(./vendor/bin/sail artisan apk:build --config=scripts/config.json --save)
          PACKAGE=$(echo "$OUTPUT" | grep -oP '包名\s+\|\s+\K[^\s]+')
          echo "package_name=$PACKAGE" >> $GITHUB_OUTPUT
      
      - name: Install APK
        run: |
          adb connect 192.168.31.162:5555
          adb install -r app/storage/app/apk/output/*.apk
      
      - name: Run PowerGenie Test
        run: |
          cd app
          ./scripts/test-huawei-powergenie.sh ${{ steps.build.outputs.package_name }}
```

---

## 总结

| 方案 | 实现难度 | 自动化程度 | 推荐场景 |
|------|---------|-----------|---------|
| **方案 1** | 中等（修改 3 个文件） | ⭐⭐⭐⭐⭐ | 长期使用、CI/CD 集成 |
| **方案 2** | 简单（修改 1 个文件） | ⭐⭐ | 临时测试、手动调试 |
| **方案 3** | 中等（复杂脚本逻辑） | ⭐⭐⭐⭐ | 快速迭代、本地开发 |
| **组合方案** | 简单 | ⭐⭐⭐⭐ | 立即可用、兼容性好 |

**建议**：
1. **立即实施**：方案 2 + 方案 3 组合（修改测试脚本支持参数和自动检测）
2. **后续优化**：实施方案 1（修改构建系统返回包名）
3. **最终目标**：构建一键构建测试脚本和 CI/CD 流程

---

**文档版本**：v1.0  
**创建日期**：2026-03-12  
**相关文档**：[HUAWEI_POWERGENIE_ANALYSIS.md](./HUAWEI_POWERGENIE_ANALYSIS.md)
