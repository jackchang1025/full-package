# 04 - 修复方案

## 1. 修复方案概述

| 方案 | 难度 | 持久性 | 推荐度 |
|------|------|--------|--------|
| A: 修改 EaodWorker.exe | 中等 | 永久 | ⭐⭐⭐⭐⭐ |
| B: 手动修复临时目录 | 简单 | 临时 | ⭐⭐ |
| C: 创建自动修复脚本 | 中等 | 半永久 | ⭐⭐⭐ |

---

## 2. 方案 A: 修改 EaodWorker.exe（推荐）

### 2.1 准备工具

**安装 dnSpy（.NET 反编译和编辑器）**:

1. 下载 dnSpy: https://github.com/dnSpy/dnSpy/releases
2. 解压到任意目录
3. 运行 `dnSpy.exe`

### 2.2 修改步骤

#### 步骤 1: 打开程序集

1. 启动 dnSpy
2. 文件 → 打开 → 选择 `c:\xampp\htdocs\private\EaodWorker.exe`
3. 在左侧树形视图中展开 `EaodWorker` → `EaodWorker` → `Worker`

#### 步骤 2: 定位问题代码

找到以下两处调用（使用 Ctrl+F 搜索 `ReplaceHugePlaceholders`）:

**位置 1** (约第 1395 行):
```csharp
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);
```

**位置 2** (约第 2534 行):
```csharp
ReplaceHugePlaceholders(MainfistPath, 800000L, 400000000L);
```

#### 步骤 3: 修改参数

右键点击方法 → 编辑方法（Edit Method）

将参数修改为:

```csharp
// 修改前
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);

// 修改后（推荐值）
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 100L, 500L);
```

**参数建议值**:

| 参数 | 原值 | 推荐值 | 说明 |
|------|------|--------|------|
| randomLength | 800,000 | 100 | 随机字符串长度 |
| slashLength | 400,000,000 | 500 | 斜杠数量 |

#### 步骤 4: 保存修改

1. 文件 → 全部保存
2. 选择保存位置（建议先备份原文件）

### 2.3 备份原文件

```powershell
# 备份原始文件
Copy-Item "c:\xampp\htdocs\private\EaodWorker.exe" "c:\xampp\htdocs\private\EaodWorker.exe.bak"
```

### 2.4 验证修改

```powershell
# 使用 ILSpy 验证修改
ilspycmd "c:\xampp\htdocs\private\EaodWorker.exe" | Select-String "ReplaceHugePlaceholders"
```

---

## 3. 方案 B: 手动修复临时目录

### 3.1 适用场景

- 紧急需要构建一个 APK
- 不想修改 EaodWorker.exe
- 一次性修复

### 3.2 修复步骤

#### 步骤 1: 找到临时目录

```powershell
# 列出所有 EaodWorker 临时目录
Get-ChildItem -Path "$env:TEMP" -Filter "Eaod_custom_*" -Directory | 
    Sort-Object LastWriteTime -Descending | 
    Select-Object Name, LastWriteTime
```

#### 步骤 2: 备份原始 Manifest

```powershell
$tempDir = "C:\Users\67464\AppData\Local\Temp\Eaod_custom_sXagbtXjOA"
Copy-Item "$tempDir\temp\AndroidManifest.xml" "$tempDir\temp\AndroidManifest.xml.huge"
```

#### 步骤 3: 缩小 Manifest

```powershell
# 读取膨胀后的 Manifest
$content = [System.IO.File]::ReadAllText("$tempDir\temp\AndroidManifest.xml.orig")

# 替换超长命名空间为较短的版本
$shortNs = "ns" + ("a" * 20)
$shortVal = "/" * 50 + ("b" * 20)

# 使用正则表达式替换
$content = $content -replace "xmlns:a{100,}=", "xmlns:$shortNs="
$content = $content -replace '"//{100,}[ab]+?"', "`"$shortVal`""

# 保存修复后的文件
[System.IO.File]::WriteAllText("$tempDir\temp\AndroidManifest.xml", $content, [System.Text.Encoding]::UTF8)
```

#### 步骤 4: 修复资源引用

```powershell
# 复制缺失的资源文件
$xmlDir = "$tempDir\temp\res\xml"
$files = Get-ChildItem $xmlDir -Filter "*.xml" | Where-Object { $_.Name -ne "accessibilityprivatesrcapp.xml" }

# 假设第一个 xml 文件就是 accessibility 配置
if ($files.Count -gt 0) {
    Copy-Item $files[0].FullName "$xmlDir\accessibilityprivatesrcapp.xml"
}
```

#### 步骤 5: 手动构建

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

cd "$tempDir\temp"
java -jar "$tempDir\apktool.jar" b -f . -o "$tempDir\out\Ready.apk"
```

#### 步骤 6: 对齐和签名

```powershell
# 对齐
& "$tempDir\zipalign.exe" -f 4 "$tempDir\out\Ready.apk" "$tempDir\out\Ready_aligned.apk"

# 创建测试密钥（如果没有）
keytool -genkey -v -keystore "$tempDir\test.keystore" -alias testkey -keyalg RSA -keysize 2048 -validity 10000 -storepass 123456 -keypass 123456 -dname "CN=Test, OU=Test, O=Test, L=Test, ST=Test, C=US"

# 签名
java -jar "$tempDir\signapk.jar" sign --ks "$tempDir\test.keystore" --ks-pass pass:123456 --key-pass pass:123456 --v2-signing-enabled true --out "$tempDir\out\Ready_signed.apk" "$tempDir\out\Ready_aligned.apk"
```

#### 步骤 7: 复制到输出目录

```powershell
$outputDir = "C:\xampp\htdocs\user\apps\126021\com.icontrol.protector"
Copy-Item "$tempDir\out\Ready_signed.apk" "$outputDir\com.icontrol.protector.apk"
```

---

## 4. 方案 C: 创建自动修复脚本

### 4.1 PowerShell 修复脚本

创建文件: `c:\xampp\htdocs\private\tools\fix_build.ps1`

```powershell
<#
.SYNOPSIS
    修复 EaodWorker 构建失败的临时目录并完成 APK 构建
.PARAMETER TempDirName
    临时目录名称（如 Eaod_custom_sXagbtXjOA）
.PARAMETER OutputPath
    APK 输出路径
#>
param(
    [Parameter(Mandatory=$true)]
    [string]$TempDirName,
    
    [Parameter(Mandatory=$false)]
    [string]$OutputPath = ".\output.apk"
)

$ErrorActionPreference = "Stop"

# 设置 Java 环境
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + [System.Environment]::GetEnvironmentVariable("Path", "Machine")

$tempDir = Join-Path $env:TEMP $TempDirName
$apkDir = Join-Path $tempDir "temp"

Write-Host "修复临时目录: $tempDir" -ForegroundColor Cyan

# 步骤 1: 检查目录
if (-not (Test-Path $apkDir)) {
    throw "临时目录不存在: $apkDir"
}

# 步骤 2: 修复 AndroidManifest.xml
Write-Host "修复 AndroidManifest.xml..." -ForegroundColor Yellow
$manifestPath = Join-Path $apkDir "AndroidManifest.xml"
$manifestOrig = Join-Path $apkDir "AndroidManifest.xml.orig"

if (Test-Path $manifestOrig) {
    $content = [System.IO.File]::ReadAllText($manifestOrig)
    
    # 缩小膨胀内容
    $shortNs = "ns" + ("a" * 50)
    $shortVal = "/" * 100 + ("b" * 50)
    
    $content = $content -replace "xmlns:a{100,}=", "xmlns:$shortNs="
    
    [System.IO.File]::WriteAllText($manifestPath, $content, [System.Text.Encoding]::UTF8)
    
    $size = (Get-Item $manifestPath).Length
    Write-Host "  Manifest 大小: $([math]::Round($size/1KB, 2)) KB" -ForegroundColor Green
}

# 步骤 3: 修复资源引用
Write-Host "修复资源引用..." -ForegroundColor Yellow
$xmlDir = Join-Path $apkDir "res\xml"
$targetFile = Join-Path $xmlDir "accessibilityprivatesrcapp.xml"

if (-not (Test-Path $targetFile)) {
    $sourceFiles = Get-ChildItem $xmlDir -Filter "*.xml" | 
                   Where-Object { $_.Name -match "^[a-z]+\.xml$" }
    
    if ($sourceFiles.Count -gt 0) {
        Copy-Item $sourceFiles[0].FullName $targetFile
        Write-Host "  已创建: accessibilityprivatesrcapp.xml" -ForegroundColor Green
    }
}

# 步骤 4: 构建 APK
Write-Host "构建 APK..." -ForegroundColor Yellow
$apktoolJar = Join-Path $tempDir "apktool.jar"
$outDir = Join-Path $tempDir "out"
$readyApk = Join-Path $outDir "Ready.apk"

New-Item -ItemType Directory -Force -Path $outDir | Out-Null

Push-Location $apkDir
try {
    $output = java -jar $apktoolJar b -f . -o $readyApk 2>&1
    $output | ForEach-Object { Write-Host "  $_" }
    
    if (-not (Test-Path $readyApk)) {
        throw "APK 构建失败"
    }
} finally {
    Pop-Location
}

# 步骤 5: 对齐
Write-Host "对齐 APK..." -ForegroundColor Yellow
$zipalign = Join-Path $tempDir "zipalign.exe"
$alignedApk = Join-Path $outDir "Ready_aligned.apk"

& $zipalign -f 4 $readyApk $alignedApk

# 步骤 6: 签名
Write-Host "签名 APK..." -ForegroundColor Yellow
$signapkJar = Join-Path $tempDir "signapk.jar"
$keystore = Join-Path $tempDir "build.keystore"
$signedApk = Join-Path $outDir "Ready_signed.apk"

# 创建密钥（如果不存在）
if (-not (Test-Path $keystore)) {
    keytool -genkey -v -keystore $keystore -alias buildkey -keyalg RSA -keysize 2048 `
            -validity 10000 -storepass android -keypass android `
            -dname "CN=Android, OU=Android, O=Android, L=Android, ST=Android, C=US" 2>$null
}

java -jar $signapkJar sign --ks $keystore --ks-pass pass:android --key-pass pass:android `
     --v2-signing-enabled true --out $signedApk $alignedApk

# 步骤 7: 验证签名
Write-Host "验证签名..." -ForegroundColor Yellow
$verifyOutput = java -jar $signapkJar verify --verbose $signedApk 2>&1
if ($verifyOutput -match "Verifies") {
    Write-Host "  签名验证通过" -ForegroundColor Green
}

# 步骤 8: 复制到输出路径
Write-Host "复制到: $OutputPath" -ForegroundColor Yellow
Copy-Item $signedApk $OutputPath -Force

$finalSize = (Get-Item $OutputPath).Length
Write-Host "`n构建完成!" -ForegroundColor Green
Write-Host "APK 大小: $([math]::Round($finalSize/1MB, 2)) MB" -ForegroundColor Green
Write-Host "输出路径: $OutputPath" -ForegroundColor Green
```

### 4.2 使用方法

```powershell
# 运行修复脚本
.\fix_build.ps1 -TempDirName "Eaod_custom_sXagbtXjOA" -OutputPath "C:\xampp\htdocs\user\apps\126021\com.icontrol.protector\com.icontrol.protector.apk"
```

---

## 5. 修复资源引用问题

### 5.1 问题描述

EaodWorker 会随机重命名 XML 资源文件，但不更新 AndroidManifest.xml 中的引用。

### 5.2 受影响的资源

```xml
<!-- AndroidManifest.xml 中的引用 -->
<meta-data android:resource="@xml/accessibilityprivatesrcapp"/>
```

### 5.3 修复方法

#### 方法 1: 复制文件

```powershell
# 找到实际的 accessibility 配置文件
$xmlDir = "...\temp\res\xml"
Get-ChildItem $xmlDir -Filter "*.xml" | Select-Object Name, Length

# 复制为正确的名称
Copy-Item "hknbezotsnrz.xml" "accessibilityprivatesrcapp.xml"
```

#### 方法 2: 修改 Manifest 引用

```powershell
# 将 Manifest 中的引用改为实际文件名
$content = Get-Content "AndroidManifest.xml" -Raw
$content = $content -replace "accessibilityprivatesrcapp", "hknbezotsnrz"
Set-Content "AndroidManifest.xml" $content
```

---

## 6. 推荐的膨胀参数

### 6.1 参数对照表

| 场景 | randomLength | slashLength | Manifest 大小 | 兼容性 |
|------|--------------|-------------|---------------|--------|
| 原始（问题值）| 800,000 | 400,000,000 | ~383 MB | ❌ 不兼容 |
| 保守修复 | 100 | 500 | ~22 KB | ✅ 完全兼容 |
| 中等混淆 | 500 | 5,000 | ~30 KB | ✅ 兼容 |
| 较强混淆 | 900 | 50,000 | ~80 KB | ⚠️ 需测试 |

### 6.2 推荐配置

```csharp
// 推荐值：保持一定混淆效果，同时确保构建成功
ReplaceHugePlaceholders(manifestPath, 500L, 5000L);
```

---

## 7. 验证修复

### 7.1 构建测试

```powershell
# 触发一次新的构建
Invoke-WebRequest -Uri "http://localhost/private/Eaod36921.php" -Method POST -Body @{...}

# 等待构建完成（约 2-3 分钟）
Start-Sleep -Seconds 180

# 检查输出
Test-Path "C:\xampp\htdocs\user\apps\126021\com.icontrol.protector\com.icontrol.protector.apk"
```

### 7.2 日志检查

```powershell
# 查看最新日志
$logPath = "C:\Eaod_logs\126021"
$latestLog = Get-ChildItem $logPath -Filter "*.json" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
Get-Content $latestLog.FullName | ConvertFrom-Json | Select-Object -Last 10
```

### 7.3 预期日志

修复后应该能看到完整的构建日志，包括:

```
>> Building resources with aapt2...
I: Building apk file...
I: Built apk into: xxx.apk
> Protect Apk..
>> Zip Align..
>> Sign APK..
>-----------Finished-------------
```
