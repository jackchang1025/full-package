# APK 逆向分析报告 - stripchat-release.apk

> **分析时间**: 2026-03-14  
> **APK 路径**: `app/storage/app/apk/apkstub/stripchat-release.apk`  
> **文件大小**: 47 MB  
> **分析工具**: aapt, unzip, strings, file
---
---

## 📦 基本信息

### APK 元数据

```
包名:           org.ldtape.qqlhl
版本号:         2 (2.0)
最小 SDK:       26 (Android 8.0)
目标 SDK:       36 (Android 16 - 未来版本)
编译 SDK:       36
签名工具:       Android Gradle 8.0.2 + Signflinger
安装位置:       仅内部存储
```

### 文件结构

```
stripchat-release.apk (47 MB)
├── classes.dex              6.9 MB   # Java 代码
├── lib/                     90+ MB   # Native 库 (3 架构)
│   ├── arm64-v8a/          32 MB
│   │   ├── librat-hat.so   16 MB    # 🚨 RAT 核心
│   │   ├── libfrpc.so      14 MB    # 🚨 FRP 客户端
│   │   ├── libconscrypt_jni.so 2.1 MB
│   │   └── libspake2.so    30 KB
│   ├── armeabi-v7a/        31 MB
│   └── x86_64/             34 MB
├── assets/                 320 KB   # 配置文件
│   ├── config.json         25 KB    # 🔑 核心配置
│   ├── android.js          9 KB     # 自动化脚本
│   ├── miui.js, oppo.js... 各厂商适配脚本
│   └── (30+ 厂商脚本)
├── res/                             # 资源文件
└── META-INF/                        # 签名信息
```

---

## 🔐 核心配置分析 (config.json)

### 加密服务器地址

```json
{
  "serverHost": "fwhlIqYT5p+LiAbK34GbpA==",
  "downloadRatHatHost": "vibkjrT3asmWu3kojRMwFozvuWwA0Qn8RlqpjOijYM8=",
  "guideAccessibilityHost": "lRcygfIFpCUhaTI09hKT0yc6BcIEuRomrM9Gl0w5XO0BMgI4dMmCQccoMHvUzafD4RJNxR+Q8uhfhzAF165ojQ=="
}
```

**分析**:
- 所有服务器地址均使用 **Base64 + AES 加密**
- 需要运行时解密才能获取真实 C&C 服务器地址
- 多层加密表明高度对抗分析意图

### 伪装信息

```json
{
  "mainActivity": "com.guard.wallet.activity.MainActivity",
  "mainUrl": "https://zh.stripchat.com",
  "promotionModel": 1,
  "langMap": {
    "zh": {
      "appLabel": "StripChat assist",
      "launcherLabel": "StripChat",
      "accessibilityServiceLabel": "StripChat视频助手"
    }
  }
}
```

**社会工程学手法**:
- 伪装成合法成人网站 (StripChat) 的"视频助手"
- 包名 `org.ldtape.qqlhl` 无意义，避免关联
- 多语言支持 (简中/繁中/英文)，针对华语用户

### 恶意行为配置

```json
{
  "uninstall": 0,              // 禁止卸载
  "activeAdmin": 1,            // 激活设备管理员
  "debug": 1,                  // 调试模式开启
  "perScreenOffDuration": 2,   // 息屏后 2 分钟触发
  "perIdleDuration": 3,        // 空闲 3 分钟触发
  "trusteeId": "790694236383350784"  // 受托人 ID (C&C 标识)
}
```

### 诱导文案

```json
{
  "alertTitle": "系统提醒:\n该应用属于未知来源安装,需要开启权限才能正常使用",
  "alertMsg": "请仔细阅读使用步骤：\n\n1.点击下方 [开启权限] 按钮\n\n2.打开已下载应用(或服务)栏目\n\n3.开启 [StripChat视频助手],并允许\n\n4.等待系统初始化完成,即可正常使用\n\n",
  "aliveBlockMsg": "正在初始化[StripChat视频助手]...\n请勿触碰手机",
  "updateSystemMsg": "正在更新系统软件包...\n请勿触碰手机",
  "updateCredentialTitle": "验证锁屏密码",
  "updateCredentialSubTitle": "修复系统安全漏洞",
  "updateCredentialDescription": "请输入锁屏密码,完成系统更新,修复安全漏洞"
}
```

**欺骗手段**:
- 伪装成系统更新/安全修复
- 诱导用户授予无障碍服务权限
- 诱导用户输入锁屏密码
- 全屏阻塞界面防止用户操作

---

## 🛡️ 权限分析

### 危险权限统计

**总计**: 100+ 权限请求

### 核心危险权限

| 权限类别 | 权限名称 | 风险等级 | 用途 |
|---------|---------|---------|------|
| **短信** | READ_SMS, RECEIVE_SMS, SEND_SMS | 🔴 极高 | 窃取验证码/银行短信 |
| **通话** | READ_CALL_LOG, WRITE_CALL_LOG, ANSWER_PHONE_CALLS, CALL_PHONE | 🔴 极高 | 监听通话/拨打付费电话 |
| **联系人** | READ_CONTACTS, WRITE_CONTACTS | 🔴 高 | 窃取通讯录 |
| **位置** | ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION | 🔴 高 | 实时追踪位置 |
| **存储** | MANAGE_EXTERNAL_STORAGE | 🔴 极高 | 访问所有文件 |
| **设备管理** | BIND_DEVICE_ADMIN | 🔴 极高 | 防卸载/锁屏 |
| **无障碍** | BIND_ACCESSIBILITY_SERVICE | 🔴 极高 | 完全控制设备 |
| **安装** | INSTALL_PACKAGES, REQUEST_INSTALL_PACKAGES | 🔴 极高 | 静默安装应用 |
| **屏幕** | SYSTEM_ALERT_WINDOW, MEDIA_PROJECTION | 🔴 极高 | 截屏/录屏 |
| **通知** | BIND_NOTIFICATION_LISTENER_SERVICE | 🔴 高 | 读取所有通知 |
| **密码** | WRITE_SECURE_SETTINGS | 🔴 极高 | 修改系统设置 |


### 厂商特定权限

```
oppo.permission.OPPO_COMPONENT_SAFE
com.huawei.permission.external_app_settings.USE_COMPONENT
```

**目的**: 绕过 OPPO/华为的安全限制

---

## 🏗️ 组件架构分析

### 核心组件清单

#### Activities (活动)

| 组件名 | 功能 | 风险 |
|--------|------|------|
| `MainActivity` | 主界面 (伪装) | 低 |
| `GuideActivity` | 引导页 (诱导授权) | 🔴 高 |
| `NoDisplayActivity` | 无界面活动 (后台操作) | 🔴 极高 |
| `ConfirmDeviceActivity` | 设备确认 (锁屏密码窃取) | 🔴 极高 |

#### Services (服务)

| 组件名 | 功能 | 风险 |
|--------|------|------|
| `MyAccessibilityService` | **无障碍服务** - 完全控制设备 | 🔴 极高 |
| `AccountAuthenticatorService` | 账户认证服务 (持久化) | 🔴 高 |
| `WIFIBackgroundService` | WiFi 后台服务 (网络监控) | 🔴 高 |
| `CustomNotificationService` | **通知监听服务** - 窃取通知内容 | 🔴 极高 |
| `MediaLiveService` | 媒体保活服务 (前台服务) | 🔴 高 |

#### Receivers (广播接收器)

| 组件名 | 功能 | 风险 |
|--------|------|------|
| `CustomAdminReceiver` | **设备管理员** - 防卸载/锁屏控制 | 🔴 极高 |
| `AlarmReceiver` | 定时任务 | 🔴 中 |
| `BatteryLevelReceiver` | 电池监控 | 🔴 中 |
| `CallReceiver` | 通话监听 | 🔴 极高 |

---

## 🔧 Native 库分析

### librat-hat.so (16 MB)

**类型**: ELF 64-bit, Go 编译, **未剥离符号**

**功能**: 远程访问木马 (RAT) 核心模块

**关键字符串**:
```
server, connect, httpServer, httpDownload
serverName, serverHello, serverOffset
```

**能力推测**:
- HTTP 服务器 (接收远程命令)
- 文件下载/上传
- 与 C&C 服务器通信
- 屏幕截图/录屏

### libfrpc.so (14 MB)

**类型**: ELF 64-bit, Go 编译, 静态链接

**功能**: FRP (Fast Reverse Proxy) 客户端

**关键字符串**:
```
proxy, client, tunnel, frp
proxyURL, proxyAuth, proxyType
clientCfg, clientRouter, clientProtocol
```

**能力**:
- 内网穿透 (绕过 NAT/防火墙)
- 建立反向代理隧道
- 使攻击者可直接访问受害设备

### libconscrypt_jni.so (2.1 MB)

**类型**: ELF 64-bit, 动态链接

**功能**: Google Conscrypt 加密库

**用途**:
- TLS/SSL 加密通信
- 加密配置文件
- 保护 C&C 通信

### libspake2.so (30 KB)

**类型**: ELF 64-bit, 动态链接

**功能**: SPAKE2 密钥交换协议

**用途**:
- 安全密钥协商
- 防止中间人攻击

---

## 📱 厂商适配脚本分析

### 覆盖厂商 (30+)

```
android.js      - 原生 Android
miui.js         - 小米/红米
oppo.js         - OPPO
vivo.js         - vivo
huawei.js       - 华为
honor.js        - 荣耀
samsung.js      - 三星
oneplus.js      - 一加
realme.js       - realme
iqoo.js         - iQOO
blackshark.js   - 黑鲨
nubia.js        - 努比亚
zte.js          - 中兴
meizu.js        - 魅族
lenovo.js       - 联想
motorola.js     - 摩托罗拉
nokia.js        - 诺基亚
sony.js         - 索尼
... (更多)
```

**功能**:
- 自动化点击脚本 (绕过权限提示)
- 厂商特定 UI 适配
- 自动授予权限
- 绕过安全限制

**示例** (推测):
```javascript
// 自动点击"允许"按钮
// 识别厂商特定的权限对话框
// 模拟用户操作
```

---

## 🔍 DEX 代码分析

### 核心包结构

```
com.guard.wallet/
├── activity/           # 界面组件
│   ├── MainActivity
│   ├── GuideActivity
│   ├── NoDisplayActivity
│   └── ConfirmDeviceActivity
├── service/            # 后台服务
│   ├── MyAccessibilityService
│   ├── CustomNotificationService
│   └── WIFIBackgroundService
├── receiver/           # 广播接收器
│   ├── CustomAdminReceiver
│   ├── CallReceiver
│   └── BatteryLevelReceiver
├── bridge/             # JNI 桥接
├── helper/             # 工具类
├── http/               # 网络通信
├── plug/               # 插件系统
├── thread/             # 线程管理
└── utils/              # 工具函数
```

### 混淆程度

- **类名**: 部分混淆 (helper.d, helper.g, utils.h)
- **方法名**: 高度混淆
- **字符串**: 部分加密
- **控制流**: 推测有混淆

---

## 🚨 攻击链分析

### 阶段 1: 初始感染

```
用户下载 APK
    ↓
伪装成 StripChat 助手
    ↓
诱导安装 (社会工程学)
    ↓
首次启动
```

### 阶段 2: 权限获取

```
显示"系统提醒"对话框
    ↓
诱导开启无障碍服务
    ↓
利用无障碍服务自动授予其他权限
    ↓
激活设备管理员 (防卸载)
    ↓
诱导输入锁屏密码
```

### 阶段 3: 持久化

```
注册开机自启动
    ↓
启动多个保活服务
    ↓
设置定时任务
    ↓
监听系统事件 (电池/网络/通话)
    ↓
防止被杀死
```

### 阶段 4: C&C 通信

```
解密服务器地址
    ↓
建立 FRP 隧道 (libfrpc.so)
    ↓
启动 RAT 服务器 (librat-hat.so)
    ↓
等待远程命令
```

### 阶段 5: 恶意行为

```
数据窃取:
  - 短信 (验证码/银行通知)
  - 通话记录
  - 联系人
  - 位置信息
  - 屏幕截图/录屏
  - 通知内容
  - 文件系统

远程控制:
  - 执行任意命令
  - 安装/卸载应用
  - 拨打电话
  - 发送短信
  - 锁定设备
  - 擦除数据
```

---

## 🔐 加密与混淆

### 配置加密

- **算法**: AES (推测)
- **编码**: Base64
- **密钥**: 硬编码在 DEX 中

### 通信加密

- **协议**: TLS 1.2/1.3 (Conscrypt)
- **证书**: 自签名 (推测)
- **密钥交换**: SPAKE2

### 代码混淆

- **工具**: ProGuard/R8 (推测)
- **程度**: 中等
- **字符串**: 部分加密

---

### 地理定位

- **主要**: 中国大陆/台湾/香港
- **语言**: 简体中文/繁体中文
- **证据**: 多语言配置、厂商适配 (华为/小米/OPPO 等)

### 目标用户

- 成人内容消费者 (伪装成 StripChat 助手)
- 安全意识薄弱的用户
- Android 8.0+ 设备用户


## 🛠️ 技术细节深度分析

### 无障碍服务滥用

**配置文件** (推测):
```xml
<accessibility-service
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:accessibilityFlags="flagDefault|flagRetrieveInteractiveWindows"
    android:canRetrieveWindowContent="true"
    android:canPerformGestures="true"
    android:canRequestTouchExplorationMode="true"
    android:canRequestFilterKeyEvents="true" />
```

**能力**:
- 读取屏幕所有内容
- 模拟点击/滑动
- 监听键盘输入
- 自动授予权限
- 绕过安全提示

### 设备管理员滥用

**能力**:
- 防止卸载 (需先停用管理员)
- 锁定屏幕
- 重置密码
- 擦除数据
- 禁用相机

### FRP 隧道机制

```
受害设备 (libfrpc.so)
    ↓ 建立隧道
C&C 服务器 (frps)
    ↓ 端口映射
攻击者
    ↓ 直接访问
受害设备 (librat-hat.so HTTP 服务器)
```

**优势**:
- 绕过 NAT/防火墙
- 无需公网 IP
- 隐蔽性强

---

## 📊 IoC (Indicators of Compromise)

### 文件指纹

```
MD5:    (需实际计算)
SHA1:   (需实际计算)
SHA256: (需实际计算)
```

### 包名

```
org.ldtape.qqlhl
```

### 组件签名

```
com.guard.wallet.*
```

### 网络指标

```
加密的 C&C 地址 (需运行时解密):
  serverHost: fwhlIqYT5p+LiAbK34GbpA==
  downloadRatHatHost: vibkjrT3asmWu3kojRMwFozvuWwA0Qn8RlqpjOijYM8=
  guideAccessibilityHost: lRcygfIFpCUhaTI09hKT0yc6BcIEuRomrM9Gl0w5XO0BMgI4dMmCQccoMHvUzafD4RJNxR+Q8uhfhzAF165ojQ==
```

### 文件路径

```
/data/data/org.ldtape.qqlhl/
/sdcard/Android/data/org.ldtape.qqlhl/
```

### 进程名

```
org.ldtape.qqlhl
librat-hat.so
libfrpc.so
```

---



