# 03 - 问题诊断报告

## 1. 问题现象

### 1.1 用户报告

- 点击"生成应用"按钮后，系统返回 `Success` 响应
- 但在 `C:\xampp\htdocs\user\apps\126021\com.icontrol.protector` 目录下没有生成 APK 文件
- 数据库中 `custom_app` 表有记录（状态为 `onbuild`），但从未更新为 `finished`

### 1.2 初步观察

```
请求 URL: http://localhost/private/Eaod36921.php
请求方法: POST
响应状态: Success
预期结果: APK 文件生成
实际结果: 无 APK 文件
```

---

## 2. 诊断过程

### 2.1 日志分析

**日志位置**: `C:\Eaod_logs\126021\2026-01-30-log.json`

**构建时间线**:

| 时间 | 日志内容 | 状态 |
|------|----------|------|
| 3:55:35 | `WORKER 参数初始化完成` | ✅ 正常 |
| 3:55:35 | `>> Step1 Started..` | ✅ 正常 |
| 3:55:35 | `>> Preparation Started..` | ✅ 正常 |
| 3:55:35 | `>Server InsertApp 2: Update successful` | ✅ 回调成功 |
| 3:55:36 | `>> Extract Apk Start..` | ✅ 正常 |
| 3:56:10 | `>> Extract Finish..` | ✅ 正常 |
| 3:56:10 | `>> Check Permissions...` | ✅ 正常 |
| 3:56:10 | `>> Encoding Strings file...` | ✅ 正常 |
| 3:56:10 | `>> Change ico...` | ✅ 正常 |
| 3:56:10 | `>> Change blackui...` | ✅ 正常 |
| 3:56:11 | `>> Coding AndroidManifest...` | ✅ 正常 |
| 3:56:11 | `New apk PKG: com.appautomatic.ankulua.lite` | ✅ 正常 |
| 3:56:11 | `>> Updating Res files...` | ✅ 正常 |
| 3:56:12 | `>> Custom Step 3...` | ✅ 正常 |
| 3:56:12 | `>> Encryption...` | ✅ 正常 |
| 3:56:17 | `>> Encryption ALL...` | ✅ 正常 |
| 3:57:12 | `junk classes...` | ✅ 正常 |
| 3:57:35 | `>> Shuffle Classes...` | ✅ 正常 |
| 3:57:48 | `>>  Junk files:done...` | ✅ 正常 |
| 3:57:48 | `Encrypt Assets:hroywbolcu1` | ✅ 正常 |
| 3:57:50 | `>> Big namespace manifist...` | ⚠️ 膨胀 Manifest |
| 3:57:51 | `>----------------->> Building Apk...` | ⚠️ 开始构建 |
| 3:57:51 | `>>  Using Apktool 2.12.1 on apkstub.apk with 8 threads` | ⚠️ |
| 3:57:51 | `>>  Smaling smali folder into classes.dex...` | ⚠️ |
| 3:57:54 | `>>  Building resources with aapt2...` | ❌ **最后日志** |
| - | **日志中断** | ❌ |

### 2.2 关键发现

日志在 `Building resources with aapt2...` 后停止，说明：
- apktool 的 smali 编译成功
- 资源编译（aapt2）阶段失败
- 没有触发 `finished` 或 `failed` 回调

---

## 3. 临时目录分析

### 3.1 发现临时目录

```powershell
# 查找 EaodWorker 的临时目录
Get-ChildItem -Path "C:\Users\67464\AppData\Local\Temp" -Filter "Eaod_*" -Directory
```

**发现的目录**:
```
Eaod_custom_bXDTioUPbc    (2026/1/30 2:43)
Eaod_custom_OyzawySHJP    (2026/1/30 3:11)
Eaod_custom_rQsnQQfVNp    (2026/1/30 3:01)
Eaod_custom_QgZSMgbOFx    (2026/1/30 3:57)
Eaod_custom_sXagbtXjOA    (2026/1/30 4:00)  ← 最新
```

### 3.2 临时目录结构

```
C:\Users\67464\AppData\Local\Temp\Eaod_custom_sXagbtXjOA\
├── 7.exe                    # 7-Zip 解压工具
├── ApkEditor.jar            # APK 保护工具
├── apktool.jar              # APK 构建工具
├── signapk.jar              # 签名工具
├── zipalign.exe             # 对齐工具
├── temp.zip                 # 原始 APK stub
├── temp\                    # 解压后的 APK 内容
│   ├── AndroidManifest.xml       ← 383 MB！！
│   ├── AndroidManifest.xml.orig  ← 383 MB！！
│   ├── apktool.yml
│   ├── assets\
│   ├── build\
│   │   ├── apk\
│   │   │   ├── classes.dex       (9.4 MB)
│   │   │   ├── classes2.dex      (2.4 MB)
│   │   │   ├── classes3.dex      (3.9 MB)
│   │   │   ├── classes4.dex      (6.8 MB)
│   │   │   ├── classes5.dex      (2.4 MB)
│   │   │   ├── classes6.dex      (2.3 MB)
│   │   │   └── classes7.dex      (2.3 MB)
│   │   └── resources.zip         (3.3 MB)
│   ├── res\
│   ├── smali\                    (29530 个 .smali 文件)
│   ├── smali_classes2\
│   ├── smali_classes3\
│   ├── smali_classes4\
│   ├── smali_classes5\
│   ├── smali_classes6\
│   └── smali_classes7\
└── out\                     # 输出目录（空）
```

### 3.3 关键发现

**AndroidManifest.xml 文件大小**:

| 文件 | 大小 | 状态 |
|------|------|------|
| 原始 (apkstub) | 22 KB | 正常 |
| 膨胀后 | **401,625,646 字节 (383 MB)** | ❌ 异常 |

**放大倍数**: 383 MB / 22 KB = **17,347 倍**

---

## 4. 根本原因分析

### 4.1 问题代码定位

**文件**: `Worker.cs`  
**行号**: 1395

```csharp
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);
```

**参数说明**:
| 参数 | 值 | 用途 |
|------|-----|------|
| `randomLength` | 800,000 | 随机字符串长度 |
| `slashLength` | 400,000,000 | 斜杠数量 |

### 4.2 膨胀机制分析

原始 AndroidManifest.xml 包含占位符:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" 
          xmlns:cnamspace="http://cnamevalue" ...>
```

`ReplaceHugePlaceholders` 函数将其转换为:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" 
          xmlns:aaaa...(800000个a)...="http:////...(400000000个/)...aaaa...(800000个a)..." ...>
```

### 4.3 失败原因

#### 原因 1: XML 解析器安全限制

```
[Fatal Error] :2:70: JAXP00010005: 实例 "[xml]" 的长度为 "8,116", 
超过了 "FEATURE_SECURE_PROCESSING" 设置的 "1,000" 限制
```

- Java XML 解析器限制单个元素/属性长度不超过 1,000 字符
- 命名空间名称（800,000 字符）远超此限制

#### 原因 2: 内存不足

```
There is insufficient memory for the Java Runtime Environment to continue.
Native memory allocation (mmap) failed to map 1413480448 bytes (1.3 GB)
```

- 解析 383 MB 的 XML 文件需要大量内存
- Java 默认堆内存不足以处理

### 4.4 次要问题：资源引用不匹配

**AndroidManifest.xml 中的引用**:
```xml
<meta-data android:resource="@xml/accessibilityprivatesrcapp"/>
```

**实际文件**:
```
res/xml/hknbezotsnrz.xml  ← 被随机重命名
```

EaodWorker 重命名了资源文件，但未更新 Manifest 中的引用。

---

## 5. 手动构建测试

### 5.1 测试 1: 原始 apkstub

```powershell
cd "C:\xampp\htdocs\private\apktool_test\apkstub"
java -jar apktool.jar b -f . -o test_build.apk
```

**结果**: ✅ 成功 (15秒)

```
I: Using Apktool 2.12.1 on apkstub.apk with 8 threads
I: Smaling smali folder into classes.dex...
I: Building resources with aapt2...
I: Building apk file...
I: Built apk into: test_build.apk
```

### 5.2 测试 2: 膨胀后的 Manifest (383 MB)

```powershell
cd "C:\Users\67464\AppData\Local\Temp\Eaod_custom_sXagbtXjOA\temp"
java -Xmx8g -jar apktool.jar b -f . -o ..\out\Ready.apk
```

**结果**: ❌ 失败

```
[Fatal Error] :2:70: JAXP00010005: 实例长度 8,116 > 限制 1,000
#
# There is insufficient memory for the Java Runtime Environment
# Native memory allocation (mmap) failed to map 1413480448 bytes
```

### 5.3 测试 3: 修复后的 Manifest

```powershell
# 1. 缩小 Manifest 的膨胀内容
# 2. 添加缺失的资源文件
Copy-Item "res\xml\hknbezotsnrz.xml" "res\xml\accessibilityprivatesrcapp.xml"

# 3. 重新构建
java -jar apktool.jar b -f . -o ..\out\Ready.apk
```

**结果**: ✅ 成功

```
I: Using Apktool 2.12.1 on apkstub.apk with 8 threads
I: Smaling smali folder into classes.dex...
I: Building resources with aapt2...
I: Building apk file...
I: Built apk into: ..\out\Ready.apk
```

---

## 6. 诊断结论

### 6.1 主要问题

| # | 问题 | 严重性 | 影响 |
|---|------|--------|------|
| 1 | AndroidManifest.xml 膨胀到 383 MB | **严重** | 导致构建完全失败 |
| 2 | 资源文件引用不匹配 | 中等 | 导致资源找不到错误 |

### 6.2 问题代码位置

```csharp
// Worker.cs 第 1395 行
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);
                                                              ↑         ↑
                                                          太大！     太大！
```

### 6.3 为什么程序没有报错退出？

1. `cmdOutputHandler` 依赖检测 `"Built apk"` 字符串来触发后续操作
2. apktool 构建失败时，不会输出 `"Built apk"`
3. 程序在等待这个字符串时陷入无限等待
4. 没有超时机制，进程最终被系统终止或用户关闭

### 6.4 流程图

```
正常流程:
apktool build → 输出 "Built apk" → 触发签名 → UpdateState(finished)

实际流程:
apktool build → XML 解析失败 → 进程崩溃 → 无输出 → 程序卡住 → 无回调
```

---

## 7. 技术细节

### 7.1 Java XML 安全限制

```java
// JAXP 安全处理特性
XMLConstants.FEATURE_SECURE_PROCESSING = true

// 默认限制
entityExpansionLimit = 64000
maxOccur = 5000
elementAttributeLimit = 10000
totalEntitySizeLimit = 50000000
maxGeneralEntitySizeLimit = 0
maxParameterEntitySizeLimit = 1000000
maxElementDepth = 0
maxXMLNameLimit = 1000  ← 命名空间名称限制
```

### 7.2 膨胀计算

```
原始占位符:
xmlns:cnamspace="http://cnamevalue"
      ↓
替换后:
xmlns:aaaa...(800000个)...="http:////...(400000000个)...aaaa...(800000个)..."

总大小 ≈ 800000 + 400000000 + 800000 = 401,600,000 字节 ≈ 383 MB
```

### 7.3 设计意图推测

膨胀 AndroidManifest.xml 的目的可能是：
1. **反反编译**：使 APK 分析工具难以解析
2. **增加文件大小**：阻止某些自动化分析
3. **混淆**：隐藏真实的 Manifest 内容

但当前参数设置过于激进，导致连自己的构建工具都无法处理。
