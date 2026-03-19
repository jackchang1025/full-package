# 01 - 系统架构分析

## 1. 整体架构图

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              前端 (Vue.js)                               │
│                        assets/system-CxULExmm.js                        │
│                         点击"生成应用"按钮                                │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │ POST 请求
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         PHP API 层                                       │
│  ┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐  │
│  │  Eaod36921.php   │───▶│  Eaod65501.php   │───▶│  Eaod91370.php   │  │
│  │   (API 入口)     │    │  (构建函数)       │    │   (回调接口)      │  │
│  └──────────────────┘    └────────┬─────────┘    └──────────────────┘  │
└───────────────────────────────────┼─────────────────────────────────────┘
                                    │ exec()
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         .NET 可执行程序层                                │
│  ┌──────────────────┐         ┌──────────────────┐                      │
│  │ EaodStarter.exe  │────────▶│  EaodWorker.exe  │                      │
│  │   (启动器)        │         │   (构建工作器)    │                      │
│  └──────────────────┘         └────────┬─────────┘                      │
└────────────────────────────────────────┼────────────────────────────────┘
                                         │ 调用
                                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                           Java 工具层                                    │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐  ┌────────────┐        │
│  │ apktool    │  │ APKEditor  │  │ zipalign   │  │ signapk    │        │
│  │ (解压/构建) │  │ (保护)     │  │ (对齐)     │  │ (签名)     │        │
│  └────────────┘  └────────────┘  └────────────┘  └────────────┘        │
└─────────────────────────────────────────────────────────────────────────┘
                                         │
                                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                            输出                                          │
│                C:\xampp\htdocs\user\apps\{userid}\{appid}\              │
│                              {appid}.apk                                │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. 组件详解

### 2.1 前端层

**文件**: `assets/system-CxULExmm.js`

**功能**:
- 收集用户输入的应用配置（名称、图标、URL 等）
- 验证表单数据（如检查应用图标是否为空）
- 发送 POST 请求到 PHP API

**关键验证逻辑**:
```javascript
if (!l.value.icoid) {
    // 提示"应用图标为空"
}
```

---

### 2.2 PHP API 层

#### Eaod36921.php (API 入口)

**功能**: 接收前端请求，验证参数，调用构建函数

**关键代码**:
```php
require_once 'Eaod65501.php';
// 验证用户权限
// 解析请求参数
// 调用 BuildCustom() 或 BuildStore()
```

#### Eaod65501.php (构建函数)

**功能**: 准备构建参数，启动 EaodStarter.exe

**核心函数**:
- `BuildCustom()` - 自定义应用构建
- `BuildStore()` - 商店应用构建

**关键代码**:
```php
function BuildCustom($params) {
    $arguments = [
        base64_encode($appid),
        base64_encode($userid),
        // ... 31 个 base64 编码的参数
    ];
    
    $command = "EaodStarter.exe lunch " . implode(" ", $arguments);
    chdir(__DIR__);
    exec($command, $output, $return_var);
}
```

#### Eaod91370.php (回调接口)

**功能**: 接收 EaodWorker.exe 的构建状态回调

**接受的状态**:
| 状态 | 描述 | 操作 |
|------|------|------|
| `onbuild` | 构建开始 | 在 custom_app 表插入/更新记录 |
| `finished` | 构建完成 | 更新状态为完成 |
| `failed` | 构建失败 | 更新状态为失败 |

**安全限制**: 仅接受来自 localhost 的请求

---

### 2.3 .NET 可执行程序层

#### EaodStarter.exe (启动器)

**功能**:
1. 解析 32 个 base64 编码的命令行参数
2. 检查是否有相同任务正在构建（通过注册表）
3. 启动 EaodWorker.exe 并传递参数

**参数列表**:
| 序号 | 参数名 | 描述 |
|------|--------|------|
| 0 | lunch | 启动命令 |
| 1 | appid | 应用包名 |
| 2 | userid | 用户 ID |
| 3 | ClientName | 客户端名称 |
| 4 | Email | 邮箱 |
| 5 | MainActivity | 主活动类名 |
| 6 | appdir | 应用目录 |
| 7 | UserHost | 用户主机 |
| 8-31 | ... | 其他配置参数 |

**防重复构建机制**:
```csharp
// 通过注册表检查
RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\EaodWorkers");
if (key.GetValue(Workerid) != null) {
    // 检查进程是否仍在运行
}
```

#### EaodWorker.exe (构建工作器)

**功能**: 执行完整的 APK 构建流程

**主要模块**:
| 模块 | 文件 | 功能 |
|------|------|------|
| Worker | Worker.cs | 主构建逻辑 |
| APKProtector | APKProtector.cs | APK 保护/混淆 |
| DexEditor | DexEditor.cs | DEX 文件编辑 |
| Crypters | Crypters.cs | 加密功能 |
| Codes | Codes.cs | 工具函数 |
| Mylogger | Mylogger.cs | 日志记录 |

---

### 2.4 Java 工具层

| 工具 | 文件 | 功能 |
|------|------|------|
| apktool | apktool.jar | APK 解压和重新打包 |
| APKEditor | APKEditor.jar | APK 保护和优化 |
| zipalign | zipalign.exe | APK 字节对齐 |
| signapk | signapk.jar | APK 签名 |
| 7-Zip | 7.exe | ZIP 解压 |

---

## 3. 构建流程详解

### 3.1 完整流程时序图

```
用户          前端           PHP              EaodStarter    EaodWorker      Java工具
 │             │              │                   │              │              │
 │──点击构建──▶│              │                   │              │              │
 │             │──POST请求───▶│                   │              │              │
 │             │              │──exec()──────────▶│              │              │
 │             │              │                   │──启动────────▶│              │
 │             │              │                   │              │──回调(onbuild)▶
 │             │              │◀──────────────────┼──────────────┼──────────────│
 │             │              │                   │              │              │
 │             │              │                   │              │──解压APK────▶│
 │             │              │                   │              │◀─────────────│
 │             │              │                   │              │──修改资源────│
 │             │              │                   │              │──混淆代码────│
 │             │              │                   │              │──重新打包───▶│
 │             │              │                   │              │◀─────────────│
 │             │              │                   │              │──保护APK────▶│
 │             │              │                   │              │◀─────────────│
 │             │              │                   │              │──对齐───────▶│
 │             │              │                   │              │◀─────────────│
 │             │              │                   │              │──签名───────▶│
 │             │              │                   │              │◀─────────────│
 │             │              │                   │              │──回调(finished)
 │             │              │◀──────────────────┼──────────────┼──────────────│
 │◀────────────┼──────────────│                   │              │              │
```

### 3.2 EaodWorker 构建步骤

| 步骤 | 日志标记 | 操作 |
|------|----------|------|
| 1 | `WORKER 参数初始化完成` | 解析所有参数 |
| 2 | `>> Step1 Started..` | 初始化构建环境 |
| 3 | `>> Preparation Started..` | 准备工作目录 |
| 4 | `>Server InsertApp` | 发送 onbuild 回调 |
| 5 | `>> Extract New Data..` | 复制 APK stub |
| 6 | `>> Extract Apk Start..` | 使用 7-Zip 解压 |
| 7 | `>> Extract Finish..` | 解压完成 |
| 8 | `>> Check Permissions...` | 检查权限配置 |
| 9 | `>> Encoding Strings file...` | 加密字符串资源 |
| 10 | `>> Change ico...` | 替换应用图标 |
| 11 | `>> Change blackui...` | 修改 UI 资源 |
| 12 | `>> Coding AndroidManifest...` | 修改 Manifest |
| 13 | `New apk PKG: xxx` | 生成新包名 |
| 14 | `>> Updating Res files...` | 合并资源文件 |
| 15 | `>> Custom Step 3...` | 自定义处理 |
| 16 | `>> Encryption...` | 加密处理 |
| 17 | `>> Encryption ALL...` | 完整加密 |
| 18 | `junk classes...` | 添加垃圾类 |
| 19 | `>> Shuffle Classes...` | 混淆类名 |
| 20 | `>>  Junk files:done...` | 垃圾文件完成 |
| 21 | `Encrypt Assets:xxx` | 加密资源 |
| 22 | `>> Big namespace manifist...` | 膨胀 Manifest |
| 23 | `>----------------->> Building Apk...` | 开始构建 |
| 24 | `>>  Using Apktool x.x.x` | apktool 构建 |
| 25 | `>>  Smaling smali folder...` | 编译 smali |
| 26 | `>>  Building resources with aapt2...` | 编译资源 |
| 27 | `I: Built apk into: xxx` | 构建完成 |
| 28 | `> Protect Apk..` | APK 保护 |
| 29 | `>> Zip Align..` | 字节对齐 |
| 30 | `>> Sign APK..` | 签名 |
| 31 | `>-----------Finished-------------` | 完成 |

---

## 4. 数据库结构

### 4.1 配置文件

**文件**: `Eaod85401.php`

```php
define('DB_ServerName', 'localhost');
define('DB_UserName', 'root');
define('DB_Password', '');
define('DB_Name', 'database_name');
```

### 4.2 相关数据表

#### users 表
存储用户信息

#### custom_app 表
存储自定义应用构建记录

| 字段 | 类型 | 描述 |
|------|------|------|
| app_package | VARCHAR | 应用包名（唯一键） |
| userid | INT | 用户 ID |
| apppath | VARCHAR | APK 文件路径 |
| status | VARCHAR | 构建状态 |
| created_at | DATETIME | 创建时间 |

---

## 5. 文件路径说明

### 5.1 程序文件

```
c:\xampp\htdocs\private\
├── EaodStarter.exe         # 启动器
├── EaodWorker.exe          # 构建器
├── DotNetZip.dll           # .NET ZIP 库
├── Eaod36921.php           # API 入口
├── Eaod65501.php           # 构建函数
├── Eaod85401.php           # 数据库配置
├── Eaod91370.php           # 回调接口
└── tools/
    ├── apktool.jar
    ├── APKEditor.jar
    └── ...
```

### 5.2 APK Stub 文件

```
c:\xampp\htdocs\private\apkstub\
├── apkstub.zip             # 主 stub（自定义应用）
├── apkstubg.zip            # 商店应用 stub
├── dropstub.zip            # Dropper stub
└── apkres/                 # 资源文件
    ├── drawable/
    └── values/
```

### 5.3 输出文件

```
c:\xampp\htdocs\user\apps\{userid}\{appid}\
└── {appid}.apk
```

### 5.4 临时文件

```
C:\Users\{user}\AppData\Local\Temp\
└── Eaod_custom_{random}\
    ├── temp\               # 解压后的 APK 内容
    ├── out\                # 构建输出
    ├── apktool.jar
    ├── signapk.jar
    └── ...
```

### 5.5 日志文件

```
C:\Eaod_logs\{userid}\{date}-log.json      # 构建日志
C:\Eaod_errors\{userid}\{date}-log.json    # 错误日志
```
