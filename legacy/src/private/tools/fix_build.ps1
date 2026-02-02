<#
.SYNOPSIS
    修复 EaodWorker 构建失败的临时目录并完成 APK 构建
.DESCRIPTION
    此脚本用于修复因 AndroidManifest.xml 过度膨胀导致的 APK 构建失败。
    它会缩小 Manifest 文件，修复资源引用，然后完成构建、对齐和签名流程。
.PARAMETER TempDirName
    临时目录名称（如 Eaod_custom_sXagbtXjOA）
.PARAMETER OutputPath
    APK 输出路径
.PARAMETER JavaHome
    Java 安装目录（可选，默认自动检测）
.EXAMPLE
    .\fix_build.ps1 -TempDirName "Eaod_custom_sXagbtXjOA" -OutputPath "C:\output\app.apk"
.NOTES
    作者: AI Assistant
    日期: 2026-01-30
#>

param(
    [Parameter(Mandatory=$true, HelpMessage="输入临时目录名称")]
    [string]$TempDirName,
    
    [Parameter(Mandatory=$false)]
    [string]$OutputPath = ".\output_signed.apk",
    
    [Parameter(Mandatory=$false)]
    [string]$JavaHome = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
)

$ErrorActionPreference = "Stop"

# 颜色输出函数
function Write-Step {
    param([string]$Message)
    Write-Host "`n[$([DateTime]::Now.ToString('HH:mm:ss'))] $Message" -ForegroundColor Cyan
}

function Write-Success {
    param([string]$Message)
    Write-Host "  ✓ $Message" -ForegroundColor Green
}

function Write-Warning {
    param([string]$Message)
    Write-Host "  ! $Message" -ForegroundColor Yellow
}

function Write-Error {
    param([string]$Message)
    Write-Host "  ✗ $Message" -ForegroundColor Red
}

# 设置 Java 环境
Write-Step "设置 Java 环境"
if (Test-Path $JavaHome) {
    $env:JAVA_HOME = $JavaHome
    $env:Path = "$JavaHome\bin;" + [System.Environment]::GetEnvironmentVariable("Path", "Machine")
    Write-Success "JAVA_HOME: $JavaHome"
} else {
    throw "Java 目录不存在: $JavaHome"
}

# 验证 Java
$javaVersion = java -version 2>&1 | Select-Object -First 1
Write-Success "Java: $javaVersion"

# 构建路径
$tempDir = Join-Path $env:TEMP $TempDirName
$apkDir = Join-Path $tempDir "temp"
$outDir = Join-Path $tempDir "out"

Write-Step "验证临时目录"
if (-not (Test-Path $apkDir)) {
    throw "临时目录不存在: $apkDir"
}
Write-Success "临时目录: $tempDir"

# 工具路径
$apktoolJar = Join-Path $tempDir "apktool.jar"
$signapkJar = Join-Path $tempDir "signapk.jar"
$zipalignExe = Join-Path $tempDir "zipalign.exe"

foreach ($tool in @($apktoolJar, $signapkJar, $zipalignExe)) {
    if (-not (Test-Path $tool)) {
        throw "缺少工具: $tool"
    }
}
Write-Success "所有构建工具已就绪"

# 步骤 1: 修复 AndroidManifest.xml
Write-Step "修复 AndroidManifest.xml"
$manifestPath = Join-Path $apkDir "AndroidManifest.xml"
$manifestOrig = Join-Path $apkDir "AndroidManifest.xml.orig"

$originalSize = (Get-Item $manifestPath -ErrorAction SilentlyContinue).Length
Write-Warning "当前大小: $([math]::Round($originalSize/1MB, 2)) MB"

if ($originalSize -gt 1000000) {
    # Manifest 过大，需要修复
    if (Test-Path $manifestOrig) {
        $content = [System.IO.File]::ReadAllText($manifestOrig)
    } else {
        $content = [System.IO.File]::ReadAllText($manifestPath)
    }
    
    # 缩小膨胀内容
    # 将超长的 namespace 名称替换为较短的版本
    $shortNs = "customns" + ("x" * 40)
    $shortVal = "/" * 80 + ("y" * 40)
    
    # 替换超长的 aaaa... 序列
    $content = $content -replace "xmlns:a{50,}=", "xmlns:$shortNs="
    $content = $content -replace '"//{50,}[a-z]+?"', "`"$shortVal`""
    
    # 保存修复后的文件
    [System.IO.File]::WriteAllText($manifestPath, $content, [System.Text.Encoding]::UTF8)
    
    $newSize = (Get-Item $manifestPath).Length
    Write-Success "修复后大小: $([math]::Round($newSize/1KB, 2)) KB"
} else {
    Write-Success "Manifest 大小正常，无需修复"
}

# 步骤 2: 修复资源引用
Write-Step "检查资源引用"
$xmlDir = Join-Path $apkDir "res\xml"
$targetFile = Join-Path $xmlDir "accessibilityprivatesrcapp.xml"

if (-not (Test-Path $targetFile)) {
    $sourceFiles = Get-ChildItem $xmlDir -Filter "*.xml" -ErrorAction SilentlyContinue | 
                   Where-Object { $_.Name -match "^[a-z]+\.xml$" -and $_.Length -gt 100 }
    
    if ($sourceFiles.Count -gt 0) {
        Copy-Item $sourceFiles[0].FullName $targetFile -Force
        Write-Success "已创建: accessibilityprivatesrcapp.xml (从 $($sourceFiles[0].Name) 复制)"
    } else {
        Write-Warning "未找到合适的源文件，可能会导致构建警告"
    }
} else {
    Write-Success "资源引用已存在"
}

# 步骤 3: 构建 APK
Write-Step "构建 APK (apktool)"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
$readyApk = Join-Path $outDir "Ready.apk"

Push-Location $apkDir
try {
    Write-Host "  正在编译 smali 和资源..."
    $buildOutput = java -jar $apktoolJar b -f . -o $readyApk 2>&1
    
    # 显示关键输出
    $buildOutput | ForEach-Object {
        if ($_ -match "^I:|Built apk|Error|Exception") {
            Write-Host "  $_" -ForegroundColor Gray
        }
    }
    
    if (Test-Path $readyApk) {
        $apkSize = (Get-Item $readyApk).Length
        Write-Success "APK 构建成功: $([math]::Round($apkSize/1MB, 2)) MB"
    } else {
        throw "APK 构建失败，文件未生成"
    }
} finally {
    Pop-Location
}

# 步骤 4: 对齐 APK
Write-Step "对齐 APK (zipalign)"
$alignedApk = Join-Path $outDir "Ready_aligned.apk"

& $zipalignExe -f 4 $readyApk $alignedApk 2>&1 | Out-Null

if (Test-Path $alignedApk) {
    Write-Success "APK 对齐完成"
} else {
    throw "APK 对齐失败"
}

# 步骤 5: 签名 APK
Write-Step "签名 APK (apksigner)"
$keystore = Join-Path $tempDir "build.keystore"
$signedApk = Join-Path $outDir "Ready_signed.apk"

# 创建密钥（如果不存在）
if (-not (Test-Path $keystore)) {
    Write-Host "  创建签名密钥..."
    $keytoolArgs = @(
        "-genkey", "-v",
        "-keystore", $keystore,
        "-alias", "buildkey",
        "-keyalg", "RSA",
        "-keysize", "2048",
        "-validity", "10000",
        "-storepass", "android",
        "-keypass", "android",
        "-dname", "CN=Build, OU=Build, O=Build, L=Build, ST=Build, C=US"
    )
    keytool @keytoolArgs 2>$null
    Write-Success "密钥已创建"
}

# 执行签名
java -jar $signapkJar sign `
    --ks $keystore `
    --ks-pass pass:android `
    --key-pass pass:android `
    --v2-signing-enabled true `
    --v3-signing-enabled false `
    --out $signedApk $alignedApk 2>&1 | Out-Null

if (Test-Path $signedApk) {
    Write-Success "APK 签名完成"
} else {
    throw "APK 签名失败"
}

# 步骤 6: 验证签名
Write-Step "验证签名"
$verifyOutput = java -jar $signapkJar verify --verbose $signedApk 2>&1

if ($verifyOutput -match "Verifies") {
    Write-Success "签名验证通过"
    $verifyOutput | Select-String "Verified using" | ForEach-Object {
        Write-Host "  $_" -ForegroundColor Gray
    }
} else {
    Write-Warning "签名验证可能存在问题"
}

# 步骤 7: 复制到输出路径
Write-Step "输出 APK"

# 确保输出目录存在
$outputDir = Split-Path $OutputPath -Parent
if ($outputDir -and -not (Test-Path $outputDir)) {
    New-Item -ItemType Directory -Force -Path $outputDir | Out-Null
}

Copy-Item $signedApk $OutputPath -Force

$finalSize = (Get-Item $OutputPath).Length
Write-Success "APK 已保存到: $OutputPath"
Write-Success "文件大小: $([math]::Round($finalSize/1MB, 2)) MB"

# 完成
Write-Host "`n" + "="*60 -ForegroundColor Green
Write-Host "  构建完成！" -ForegroundColor Green
Write-Host "="*60 -ForegroundColor Green
Write-Host "`n输出文件: $OutputPath"
Write-Host "文件大小: $([math]::Round($finalSize/1MB, 2)) MB"
Write-Host "`n可以使用以下命令安装:"
Write-Host "  adb install `"$OutputPath`"" -ForegroundColor Yellow
