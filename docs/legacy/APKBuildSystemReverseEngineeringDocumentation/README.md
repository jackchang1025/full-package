# APK 构建系统逆向分析文档

## 文档目录

| 序号 | 文档名称 | 描述 |
|------|----------|------|
| 1 | [系统架构分析](./01-system-architecture.md) | APK 构建系统的整体架构和组件说明 |
| 2 | [反编译分析报告](./02-decompile-analysis.md) | EaodStarter.exe 和 EaodWorker.exe 的反编译分析 |
| 3 | [问题诊断报告](./03-problem-diagnosis.md) | 构建失败的根本原因分析 |
| 4 | [修复方案](./04-fix-solution.md) | 问题修复的详细步骤和方案 |
| 5 | [验证与测试](./05-verification.md) | 修复后的验证结果和测试流程 |

---

## 项目概述

### 背景

用户在点击"生成应用"按钮后，系统返回成功响应，但 APK 文件未能成功生成。本文档记录了对该问题的完整逆向分析过程。

### 分析目标

1. 理解 APK 构建系统的完整架构
2. 通过反编译分析 EaodWorker.exe 和 EaodStarter.exe
3. 定位构建失败的根本原因
4. 提供可行的修复方案

### 关键发现

- **根本原因**：`ReplaceHugePlaceholders` 函数将 AndroidManifest.xml 膨胀至 383 MB，超出 Java XML 解析器限制
- **次要原因**：资源文件引用不匹配（文件被重命名但引用未更新）
- **解决方案**：减小膨胀参数或修复资源引用

### 相关文件位置

```
\home\code\php\project\full-package\src\private\
├── EaodStarter.exe          # 启动器程序
├── EaodWorker.exe           # 构建工作程序
├── Eaod36921.php            # API 入口
├── Eaod65501.php            # 构建函数
├── Eaod91370.php            # 回调接口
├── decompiled/              # 反编译输出
│   ├── EaodStarter/
│   └── EaodWorker/
├── tools/
│   └── apktool.jar
└── docs/                    # 本文档目录
```

### 日志位置

```
C:\Eaod_logs\{userid}\{date}-log.json      # 构建日志
C:\Eaod_errors\{userid}\{date}-log.json    # 错误日志
```

---

## 分析时间线

| 日期 | 阶段 | 内容 |
|------|------|------|
| 2026-01-30 | 初始分析 | 确认 PHP 构建流程和数据库配置 |
| 2026-01-30 | 反编译 | 安装 ILSpy 并反编译 .NET 程序 |
| 2026-01-30 | 诊断 | 定位到 AndroidManifest.xml 膨胀问题 |
| 2026-01-30 | 修复 | 手动修复临时目录并成功构建 APK |
| 2026-01-30 | 验证 | 签名 APK 并验证可安装性 |

---

## 快速参考

### 构建命令

```powershell
# 设置 Java 环境
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

# apktool 构建
java -jar apktool.jar b -f <source_dir> -o <output.apk>

# zipalign 对齐
zipalign.exe -f 4 <input.apk> <output_aligned.apk>

# apksigner 签名
java -jar signapk.jar sign --ks <keystore> --ks-pass pass:<password> --out <output_signed.apk> <input.apk>
```

### 回调 URL

```
http://localhost/private/Eaod90061.php    # 普通构建回调
http://localhost/private/Eaod91370.php    # 自定义应用回调
```

### 回调状态

- `onbuild` - 构建开始
- `finished` - 构建完成
- `failed` - 构建失败
