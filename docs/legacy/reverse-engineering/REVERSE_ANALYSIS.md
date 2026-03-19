# 逆向源码分析报告

## 1. 目录结构

```
/home/code/php/project/full-package/src/private/decompiled/
├── EaodStarter/                    # 启动器程序
│   ├── EaodStarter.csproj
│   ├── EaodStarter/
│   │   ├── Starter.cs              # 主启动类
│   │   └── Mylogger.cs             # 日志记录
│   ├── Properties/
│   │   └── AssemblyInfo.cs
│   ├── EaodStarter.My/             # VB.NET 运行时支持
│   ├── app.config
│   └── app.manifest
│
└── EaodWorker/                     # 工作进程（APK构建）
    ├── EaodWorker.csproj
    ├── EaodWorker/
    │   ├── Worker.cs               # 核心构建逻辑 (3494行)
    │   ├── APKProtector.cs         # APK保护/混淆
    │   ├── DexEditor.cs            # DEX文件编辑
    │   ├── DexHeaderInfo.cs        # DEX头信息
    │   ├── Codes.cs                # 工具函数库
    │   ├── Crypters.cs             # 加密/解密
    │   └── Mylogger.cs
    ├── Properties/
    ├── EaodWorker.My/
    ├── app.config
    └── app.manifest
```

## 2. 核心流程

### 2.1 EaodStarter.cs - 启动器主入口

**Main() 函数流程：**
1. 接收命令行参数（32个Base64编码的参数）
2. 解码所有参数
3. 生成WorkerId (MD5(userid + "_" + appid))
4. 检查是否已有构建进程运行（通过Registry）
5. 启动 EaodWorker.exe 进程

**关键参数（32个）：**
```
0: appid (Base64)
1: userid (Base64)
2: ClientName (Base64)
3: Email (Base64)
4: MainActivity (Base64)
5: appdir (Base64)
6: UserHost (Base64)
7: use_access (Base64)
8: use_antkill (Base64)
9: use_atoprims (Base64)
10: notifytitle (Base64)
11: notifymsg (Base64)
12: allprims (Base64)
13: blackprims (Base64)
14: Buildtype (Base64) - "S" 或 "C"
15: appname (Base64)
16: appversion (Base64)
17: appicopath (Base64)
18: appurl (Base64)
19: logintitle (Base64)
20: logindis (Base64)
21: loginbtn (Base64)
22: lngshort (Base64)
23: hiddenapp (Base64)
24: noemulator (Base64)
25: installtype (Base64)
26: hidetype (Base64)
27: use_draw (Base64)
28: open_access (Base64)
29: descr_iption (Base64)
30: diao_type (Base64)
```

**关键函数：**
- `Base64Decode()` - Base64解码
- `ToBase64()` - Base64编码
- `CovertToMD5()` - MD5哈希
- `Busy()` - 检查进程是否运行

---

### 2.2 EaodWorker.cs - APK构建核心

**Main() 函数流程：**
1. 解码所有参数
2. 注册进程ID到Registry
3. 初始化路径：
   - `userfolder = directoryName + "\\user\\apps\\" + userid + "\\" + appid`
   - `DropStub = directoryName + "\\private\\apkstub\\dropstub.zip"`
   - `InjectStub = directoryName + "\\private\\apkstub\\jectstub.zip"`
4. 根据 Buildtype 判断：
   - "S" = Store模式（使用现有APK）
   - "C" = Custom模式（自定义应用）
5. 执行三个步骤：
   - `Step1()` - 解包APK
   - `Step2()` - 注入恶意代码
   - `Step3()` - 重新打包签名

**关键变量：**
```csharp
private static string ServerApi = "http://localhost/private/Eaod90061.php";
private static string ServerApi_Customapp = "http://localhost/private/Eaod91370.php";
private static string onbuild = "onbuild";
private static string failed = "failed";
private static string finished = "finished";
```

---

## 3. HTTP 回调实现

### 3.1 UpdateState() 函数

**功能：** 向服务器报告构建状态

**代码位置：** Worker.cs 第2780-2840行

```csharp
private static async void UpdateState(string subCommand)
{
    // 如果是自定义应用，先发送到 ServerApi_Customapp
    if (IsCustomeApp)
    {
        HttpClient httpClient = new HttpClient();
        string content = JsonConvert.SerializeObject(new Dictionary<string, object>
        {
            { "userid", userid },
            { "appid", appid },
            { "subcom", subCommand }
        });
        StringContent content2 = new StringContent(content, Encoding.UTF8, "application/json");
        
        HttpResponseMessage httpResponseMessage = await httpClient.PostAsync(
            ServerApi_Customapp, content2);
    }
    
    // 然后发送到主 ServerApi
    HttpClient httpClient2 = new HttpClient();
    string content3 = JsonConvert.SerializeObject(new Dictionary<string, object>
    {
        { "userid", userid },
        { "appid", appid },
        { "subcom", subCommand }
    });
    StringContent content4 = new StringContent(content3, Encoding.UTF8, "application/json");
    
    HttpResponseMessage httpResponseMessage2 = await httpClient2.PostAsync(
        ServerApi, content4);
}
```

**POST 数据格式：**
```json
{
    "userid": "用户ID",
    "appid": "应用ID",
    "subcom": "onbuild|finished|failed"
}
```

### 3.2 InsertApp() 函数

**功能：** 报告APK构建完成

**代码位置：** Worker.cs 第2842-2906行

```csharp
private static async void InsertApp()
{
    // 自定义应用
    if (IsCustomeApp)
    {
        HttpClient httpClient = new HttpClient();
        string content = JsonConvert.SerializeObject(new Dictionary<string, object>
        {
            { "userid", userid },
            { "appid", appid },
            { "apppath", userfolder + "\\" + appid + ".apk" },
            { "subcom", onbuild },
            { "appname", appname },
            { "appico", userid + "/icons/" + appicopath }
        });
        StringContent content2 = new StringContent(content, Encoding.UTF8, "application/json");
        
        HttpResponseMessage httpResponseMessage = await httpClient.PostAsync(
            ServerApi_Customapp, content2);
    }
    
    // Store应用
    HttpClient httpClient2 = new HttpClient();
    string content3 = JsonConvert.SerializeObject(new Dictionary<string, object>
    {
        { "userid", userid },
        { "appid", appid },
        { "apppath", userfolder + "\\" + appid + ".apk" },
        { "subcom", onbuild }
    });
    StringContent content4 = new StringContent(content3, Encoding.UTF8, "application/json");
    
    HttpResponseMessage httpResponseMessage2 = await httpClient2.PostAsync(
        ServerApi, content4);
}
```

**POST 数据格式（自定义应用）：**
```json
{
    "userid": "用户ID",
    "appid": "应用ID",
    "apppath": "C:\\...\\user\\apps\\userid\\appid\\appid.apk",
    "subcom": "onbuild",
    "appname": "应用名称",
    "appico": "userid/icons/icon.png"
}
```

---

## 4. APK 构建核心逻辑

### 4.1 Step1() - 解包

- 使用 apktool 解包APK
- 提取 AndroidManifest.xml
- 提取资源文件

### 4.2 Step2() - 注入

- 复制恶意Stub代码
- 修改 AndroidManifest.xml
- 注入权限声明
- 注入Service/Activity/Receiver

### 4.3 Step3() - 重新打包

**关键操作：**
1. 创建 smali_classes2 目录
2. 解压 jectstub.zip 到该目录
3. 生成垃圾Smali文件（混淆）
4. 替换类名和字符串：
   ```
   AccessibilityActivity → N_AccessibilityActivity
   AccessServices → N_AccessServices
   HiddenBrowser → N_HiddenBrowser
   ...等50+个类名
   ```
5. 替换配置值：
   ```
   [USER_MAIL] → Email
   [USE-SUPER] → use_access
   [USER_DOM] → UserHost
   [USE-NOKILL] → use_antkill
   [USE-DRAWOVER] → use_draw
   [USE-AUTOGRANT] → use_atoprims
   [USE-ALLPRIM] → ASKPRIM_all
   [USE-BLACK] → ASKPRIM_black
   [USE-HIDDEEN] → hiddenapp
   [USE-STORE] → IsStoreMod
   [USE-GUID] → installtype
   [USE-FAKE] → hidetype
   [AST-PAS] → AssetsPass
   [Client_N] → ClientName
   [_NOTIFI_TITLE_] → notifytitle
   [_NOTIFI_MSG_] → notifymsg
   [OBFS] → NEWRANDOM
   [BSE_URL] → appurl
   [log-title] → logintitle
   [log-dis] → logindis
   [log-btn] → loginbtn
   [log-lng] → lngshort
   [USE-OOENACC] → open_access
   [USE-DIAO] → diao_type
   ```

---

## 5. 加密/解密

### 5.1 Crypters.cs - AES加密

```csharp
private static string MY_IV = "2230209522049090";
private static string My_PASSWORD = "4814780584699673";
private static string SALT = "2894356330652558";

// 使用 AES-CBC + PKCS7 填充
// 密钥派生：Rfc2898DeriveBytes (65536 迭代)
```

**加密方法：**
```csharp
public string Encrypt(string raw)
{
    // 使用 AES-CBC 加密
    // 返回 Base64 编码的密文
}

public string Decrypt(string encrypted)
{
    // 解密 Base64 编码的密文
}
```

---

## 6. APK 保护机制

### 6.1 APKProtector.cs

**功能：** 对APK进行混淆和保护

**保护方法：**
1. **修改CRC校验** - 破坏ZIP完整性检查
2. **零化大小字段** - 隐藏文件大小
3. **损坏偏移量** - 破坏文件位置指针
4. **添加虚假数据** - 增加混淆
5. **随机压缩方法** - 改变压缩算法
6. **添加虚假头部** - 增加混淆

**目标文件：**
- AndroidManifest.xml (CRC: 20425)
- resources.arsc (CRC: 28061)
- classes.dex (CRC: 35000)

---

## 7. 路径配置

### 7.1 目录结构

```
WorkingDir/
├── private/
│   ├── apkstub/
│   │   ├── apkstub.zip          # 完整权限Stub
│   │   ├── apkstubg.zip         # 部分权限Stub
│   │   ├── dropstub.zip         # 下载器Stub
│   │   └── jectstub.zip         # 注入Stub
│   ├── Eaod90061.php            # 主回调接口
│   └── Eaod91370.php            # 自定义应用回调
│
├── user/
│   ├── apps/
│   │   └── {userid}/
│   │       └── {appid}/
│   │           └── {appid}.apk  # 最终APK
│   └── storage/
│       └── {userid}/
│           └── icons/
│               └── {icon}.png   # 应用图标
│
└── {appdir}/                    # Store应用目录
    ├── {appid}.zip              # 源APK
    └── ico.png                  # 图标
```

---

## 8. 关键类和函数

### 8.1 Codes.cs - 工具函数库

**主要函数：**
- `GenerateRandomFolderName()` - 生成随机文件夹名
- `FixStrings()` - 转义特殊字符
- `FileInUse()` - 检查文件是否被占用
- `RandommMad()` - 生成随机字符串
- `RandomSTR()` - 生成随机字符串
- `madladstr()` - 生成混淆字符串
- `FromBase64()` - Base64解码
- `ToBase64()` - Base64编码
- `Encoding()` - 获取编码

### 8.2 DexEditor.cs - DEX文件编辑

**功能：**
- 读取DEX文件头
- 修改Magic字节
- 修改Checksum
- 修改Signature
- 修改FileSize
- 修改HeaderSize

**支持的文件类型：**
- DEX 035/036/037
- PNG, JPG, GIF, BMP, WEBP
- TXT, HTML, JSON, PDF
- ZIP, RAR, 7Z
- EXE, ELF
- WAV, MP3, OGG, MP4, MOV

---

## 9. 混淆和变量替换

### 9.1 类名混淆列表（50+个）

```
AccessibilityActivity, AccessServices, HiddenBrowser, AccessTools,
ActivityCaptureScreen, ActivityMonitors, _update_app_, Consts, Codes,
ChatActivity, CameraCap, Contct_manager, Deviceinfo, filesManager,
id_Commands, KeyStorksQ, LiveChat, QueryChats, LiveKeysStrok,
StarterServices, LocationMonitor, LockAppsActivity, ActivMain,
MyLoger, MyNotification, MyPacket, My_Configs, ActivityDraw,
My_Crpter, MySettings, PermissionsActivity, RecordPayPassWord,
RequestDraw, MuteUninstall, RequestPermissions2, ScreenCaps,
ScreenReceiver, StatusMonitor, UtliTools, NotifyListenService,
WorkServices, HiddenActivity, LockActivity, RestrectionActivity,
OPPOAutostart, BrodcastActivity, AnUninstall, TransparentActivity,
EngineWorker, TransparentLauncherAlias, SIMLauncherAlias,
ChromeLauncherAlias, OppoLauncherAlias, VivoLauncherAlias,
MuteActivity, AlertActivity, HiddenIco, WebBrowser, Webjector,
Apps_Manage, AudioRecorder, ClassGen
```

### 9.2 字符串混淆列表（50+个）

```
URL_PING, URL_MSG, URL_SOCKT, getIPAddress, USR_MAIL, USR_HOST,
SPLIT_SKT, SPLIT_DATA, SPLIT_LINE, SPLIT_ARAY, USR_NAME, DEVICE_ID,
Rec_Activitys, Rec_Notifications, Rec_keystrokes, Rec_links, Rec_apps,
THE_IDF, LIVE_KLOG, localip, SERVER_DIR, get_prims, get_draw,
get_kill, get_click, Draws_overs, User_allPrims, HOME_NAME,
Use_Access, Anti_Kill, Click_Prim, Auto_Clicker, Auto_Prims,
Send_Skilton, Skeleton_Color, Black_Screen, Auto_Sreen,
Stored_resultCode, Stored_intentdata, _Notfy_TITL_, _Notfy_MSG_,
Tracking_Data_str, Notifi_ID, My_Access_inst, STATUS_MONITOR,
LOCK_SERVS, PAKET_LOCK, MY_COMMANDS_LIST, EMIL_POST, PHONE_POST,
TYPE_POST, CUZ_POST, DATA_POST, Fix_it, Get_Network, Create_DevicID,
IsIgnore_Battery, Time_Stamp, Accessibility_Service, Read_Contacts,
Read_SMS, Read_Call_Log, Acc_Camera, Get_Accounts, Record_Audio,
Call_Phone, Call_Record, Dcrpt_KET, Dcrypt_datas, Send_SMS,
Set_Wallpaper, Doze_Mode, Draw_Overlays, Package_Installs,
is_Access_Enabled, Battery_state, TempPassLock, Blocked_Apps,
Lock_App_list, Supported_Browsers, Dcrpt_Str, Get_Cifr, get_accss,
Gnrat_Ky, Mob_Name, Access_type, Hide_ico, auto_start, auto_battery,
get_btry, get_start, Anti_emulator, get_emu, get_hideit,
get_accsstype, Hide_Type, get_hideentype, Capture_Lock, AsstsKey,
get_caplock, Is_Store, get_storemod, Anti_Doze, get_dozestate,
URL_CASH
```

---

## 10. PHP 实现要点

### 10.1 接收回调的PHP接口

**Eaod90061.php** - 主回调接口
```php
// 接收 POST JSON 数据
$data = json_decode(file_get_contents('php://input'), true);

// 处理状态更新
if ($data['subcom'] == 'onbuild') {
    // 应用开始构建
} elseif ($data['subcom'] == 'finished') {
    // 应用构建完成
} elseif ($data['subcom'] == 'failed') {
    // 应用构建失败
}
```

**Eaod91370.php** - 自定义应用回调接口
```php
// 接收自定义应用的回调
// 包含 appname 和 appico 信息
```

### 10.2 APK 构建流程

1. 接收 32 个 Base64 参数
2. 解码参数
3. 调用 EaodStarter.exe
4. 监听 HTTP 回调
5. 更新数据库状态

---

## 11. 总结

这是一个完整的 Android APK 恶意软件构建系统：

1. **EaodStarter.exe** - 参数解析和进程启动
2. **EaodWorker.exe** - APK 解包、注入、打包、签名
3. **HTTP 回调** - 向服务器报告构建状态
4. **加密** - AES-CBC 加密敏感数据
5. **混淆** - 类名、字符串、DEX 头部混淆
6. **保护** - APK 结构破坏和虚假数据注入

PHP 需要实现的核心功能：
- 参数验证和解码
- APK 构建流程管理
- HTTP 回调处理
- 数据库状态更新
- 文件存储管理

