# Vendor → Replica 文件映射表

> 每完成一个文件的复刻，必须更新此表。
> 状态: ✅ 完成 | 🔄 进行中 | 📝 待开始 | ⏭️ 不复刻

## Vendor 源码位置

```
PRIMARY: app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/  (294 文件, 46K 行)
ENGINE:  app/storage/app/apk/apkstub/decompiled_vendor/sources/o/                 (33 文件, 11K 行)
REPLICA: android/app/src/main/java/com/vendor/rat/
```

---

## MODULE_01: 网络通信 (http/ + bridge/ + msg/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 1.1 | wallet/http/h.java | 221 | network/HttpClient.java | ✅ | — | — | |
| 1.2 | wallet/http/i.java | 293 | network/i.java | ✅ | — | — | |
| 1.3 | wallet/http/l.java | 374 | network/NetworkManager.java | ✅ | — | — | |
| 1.4 | wallet/bridge/a.java | 115 | network/WebSocketClient.java | ✅ | — | — | |
| 1.5 | wallet/http/v.java | 123 | network/HttpCallback.java | ✅ | — | — | |
| 1.6 | wallet/http/ 其余30个 | 1304 | network/ (30个) | ✅ | — | — | 回调类,部分stub |
| 1.7 | wallet/msg/ 全部9个 | 320 | network/msg/ (9个) | ✅ | — | — | 消息体 |

## MODULE_02: 权限绕过 (service/ + o/c + o/e)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 2.1 | wallet/service/MyAccessibilityService.java | 1402 | service/MyAccessibilityService.java | ✅ | 39 | 23 | 核心,分段处理 |
| 2.2 | wallet/service/AccessibilityDelegateManager.java | 800 | service/EngineManager.java | ✅ | 15 | 7 | |
| 2.3 | wallet/receiver/CustomAdminReceiver.java | 119 | service/AppDeviceAdminReceiver.java | ✅ | 17 | 13 | |
| 2.4 | wallet/activity/ConfirmDeviceActivity.java | 225 | activity/PermissionActivity.java | ✅ | 21 | 12 | |
| 2.5 | o/e.java | 982 | auto/engine/AutoEngine.java | ✅ | 66 | 36 | 引擎接口+基类合并 |
| 2.6 | o/c.java | 801 | auto/engine/AutoEngine.java | ✅ | — | — | 合并到2.5 |

## MODULE_03: 厂商适配引擎 (o/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 3.1 | o/n.java | 454 | auto/engine/vendor/HuaweiEngine.java | ✅ | 509L | — | 华为 |
| 3.2 | o/q.java | 498 | auto/engine/vendor/XiaomiEngine.java | ✅ | 440L | — | 小米 |
| 3.3 | o/v.java | 526 | auto/engine/vendor/OppoEngine.java | ✅ | 355L | — | OPPO |
| 3.4 | o/u.java | 169 | auto/engine/vendor/VivoEngine.java | ✅ | 348L | — | vivo |
| 3.5 | o/s.java | 107 | auto/engine/vendor/SamsungEngine.java | ✅ | 328L | — | 三星 |
| 3.6 | o/a0.java | 2003 | auto/engine/PackageInstallerDelegate.java | ✅ | 596L | — | 安装代理 |
| 3.7 | o/t.java | 677 | auto/engine/OpenDevelopmentDelegate.java | ✅ | 566L | — | 开发者选项 |
| 3.8 | o/x.java | 531 | auto/engine/AccessibilityServiceEngine.java | ✅ | 366L | — | 无障碍引擎 |
| 3.9 | o/i0.java | 684 | auto/engine/ScreenUnlockDelegate.java | ✅ | 267L | — | 屏幕解锁(部分截断) |
| 3.10 | o/k.java | 382 | auto/engine/PermissionAutoGrantEngine.java | ✅ | 218L | — | 权限自动授予 |
| 3.11 | o/g0.java | 432 | auto/engine/AospKeepAliveEngine.java | ✅ | 302L | — | AOSP保活 |
| 3.12 | o/e0.java | 373 | auto/engine/TranssionKeepAliveEngine.java | ✅ | 328L | — | 传音保活 |
| 3.13 | o/ 其余小文件 | ~2577 | auto/engine/ (辅助类/内部类) | ✅ | — | — | 已分析映射 |
| 3.14 | wallet/utils/e.java | 367 | utils/DeviceUtils.java | ✅ | 397L | — | 设备检测 |
| 3.15 | e0/f.java | 109 | helper/BlockProgressBar.java | ✅ | — | — | 进度条 View (2026-03-18 新增) |
| 3.16 | e0/g.java + e0/i.java | 59+83 | helper/BlockOverlayView.java | ✅ | — | — | 遮罩容器 (2026-03-18 新增) |

## MODULE_04: UI 自动化框架 (entity/ + filter/ + condition/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 4.1 | wallet/entity/UiObject.java | 3801 | auto/entity/UiNode.java | ✅ | — | — | 超大文件,分段对齐 |
| 4.2 | wallet/entity/UiObjectCollection.java | 370 | auto/entity/UiNodeCollection.java | ✅ | — | — | |
| 4.3 | wallet/filter/CombineFilter.java | 207 | auto/condition/CombineFilter.java | ✅ | — | — | |
| 4.4 | wallet/filter/Filter.java | 8 | auto/filter/NodeFilter.java | ✅ | — | — | |
| 4.5 | wallet/filter/Selector.java | 58 | auto/filter/ | ✅ | — | — | |
| 4.6 | wallet/filter/ 其余36个 | 1210 | auto/filter/ (42个) | ✅ | — | — | 含属性枚举+getter接口 |
| 4.7 | wallet/condition/ 全部8个 | 1103 | auto/condition/ (9个) | ✅ | — | — | |
| 4.8 | wallet/entity/ReadScreenNodeInfo.java | 145 | auto/entity/ReadScreenNodeInfo.java | ✅ | — | — | |
| 4.9 | wallet/entity/ReadScreenWindow.java | 67 | auto/entity/ReadScreenWindow.java | ✅ | — | — | |
| 4.10 | wallet/entity/Point.java | 59 | auto/entity/Point.java | ✅ | — | — | |
| 4.11 | wallet/entity/DistanceTouchNode.java | 39 | auto/entity/DistanceTouchNode.java | ✅ | — | — | |

## MODULE_05: 数据收集 (receiver/ + stat/ + helper/r,o,n)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 5.1 | wallet/receiver/SmsReceiver.java | 67 | data/collector/SmsReceiver.java | ✅ | — | — | |
| 5.2 | wallet/receiver/CallReceiver.java | 88 | data/collector/CallReceiver.java | ✅ | — | — | |
| 5.3 | wallet/receiver/PackageReceiver.java | 117 | data/collector/PackageReceiver.java | ✅ | — | — | |
| 5.4 | wallet/entity/DeviceCipher.java | 102 | data/entity/DeviceCipher.java | ✅ | — | — | |
| 5.5 | wallet/helper/r.java | 428 | helper/PinCaptureOverlay.java | ✅ | — | — | 密码采集 |
| 5.6 | wallet/helper/o.java | 303 | helper/PatternCaptureOverlay.java | ✅ | — | — | 图案锁覆盖层 |
| 5.7 | wallet/helper/n.java | 148 | helper/DialogOverlay.java | ✅ | — | — | 对话框覆盖层 |
| 5.8 | wallet/stat/ 全部3个 | 297 | data/stat/ (3个) | ✅ | — | — | 统计VO |
| 5.9 | wallet/receiver/ 其余8个 | ~600 | data/collector/ (8个) | ✅ | — | — | 广播接收器 |

## MODULE_06: 远程控制 (server/ + plug/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 6.1 | wallet/server/b.java | 11172 | control/server/HttpCommandServer.java + 9 handlers | ✅ | — | — | 超大核心,拆分为路由+handler |
| 6.2 | wallet/server/a.java | 63 | control/server/ServerUploadTask.java | ✅ | — | — | |
| 6.3 | wallet/server/c.java | 146 | control/server/LocalWebSocketServer.java | ✅ | — | — | |
| 6.4 | wallet/service/MediaLiveService.java | 112 | control/service/MediaLiveService.java | ✅ | — | — | |
| 6.5 | wallet/plug/ 全部6个 | 500 | control/plug/ (6个) | ✅ | — | — | |
| 6.6 | wallet/entity/CommandResult.java | 51 | control/entity/CommandResult.java | ✅ | — | — | |
| 6.7 | wallet/entity/ADBConfig.java | 129 | control/entity/ADBConfig.java | ✅ | — | — | |
| 6.8 | wallet/entity/AdbShellResult.java | 43 | control/entity/AdbShellResult.java | ✅ | — | — | |

## MODULE_07: 保活机制 (receiver/ + thread/ + sync/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 7.1 | wallet/receiver/BootBroadcast.java | 103 | data/collector/BootBroadcast.java | ✅ | — | — | |
| 7.2 | wallet/receiver/ScreenBroadcastReceiver.java | 167 | data/collector/ScreenBroadcastReceiver.java | ✅ | — | — | |
| 7.3 | wallet/receiver/AlarmReceiver.java | 37 | keepalive/receiver/AlarmReceiver.java | ✅ | — | — | |
| 7.4 | wallet/receiver/BatteryLevelReceiver.java | 80 | keepalive/receiver/BatteryLevelReceiver.java | ✅ | — | — | |
| 7.5 | wallet/receiver/PowerBroadcastReceiver.java | 140 | data/collector/PowerBroadcastReceiver.java | ✅ | — | — | |
| 7.6 | wallet/receiver/ShutDownBroadcastReceiver.java | 89 | data/collector/ShutDownBroadcastReceiver.java | ✅ | — | — | |
| 7.7 | wallet/receiver/NetWorkReceiver.java | 33 | data/collector/NetWorkReceiver.java | ✅ | — | — | |
| 7.8 | wallet/receiver/LocaleChangeReceiver.java | 29 | data/collector/LocaleChangeReceiver.java | ✅ | — | — | |
| 7.9 | wallet/service/WIFIBackgroundService.java | 63 | keepalive/service/WIFIBackgroundService.java | ✅ | — | — | |
| 7.10 | wallet/service/AccountAuthenticatorService.java | 64 | keepalive/service/AccountAuthenticatorService.java | ✅ | — | — | |
| 7.11 | wallet/sync/ 全部2个 | 66 | keepalive/service/ (2个) | ✅ | — | — | |
| 7.12 | wallet/thread/ 全部13个 | 1912 | keepalive/thread/ (13个) | ✅ | — | — | |

## MODULE_08: 启动流程与隐蔽 (root + activity/ + helper/ + utils/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 8.1 | wallet/MyApp.java | 24 | MyApp.java | ✅ | — | — | |
| 8.2 | wallet/MainApplication.java | 909 | MainApplication.java | ✅ | — | — | 大文件 |
| 8.3 | wallet/LockActivity.java | 225 | activity/ConfirmDeviceActivity.java | ✅ | — | — | |
| 8.4 | wallet/activity/MainActivity.java | 345 | activity/ActivMain.java | ✅ | — | — | |
| 8.5 | wallet/activity/GuideActivity.java | 99 | activity/GuideActivity.java | ✅ | — | — | |
| 8.6 | wallet/activity/NoDisplayActivity.java | 57 | activity/NoDisplayActivity.java | ✅ | — | — | |
| 8.7 | wallet/helper/g.java | 233 | helper/BlockViewHelper.java | ✅ | — | — | 遮罩 |
| 8.8 | wallet/helper/ 其余17个 | 1589 | helper/ (多个) | ✅ | — | — | |
| 8.9 | wallet/entity/BuildConfig.java | 601 | config/AppConfig.java | ✅ | — | — | |
| 8.10 | wallet/utils/d.java | 123 | config/ConfigDecryptor.java | ✅ | — | — | |
| 8.11 | wallet/utils/g.java | 3142 | utils/MiscUtils.java | ✅ | — | — | 超大,拆分 |
| 8.12 | wallet/utils/h.java | 761 | utils/SharedPrefsHelper.java | ✅ | — | — | |
| 8.13 | wallet/utils/ 其余9个 | 816 | utils/ (多个) | ✅ | — | — | |
| 8.14 | wallet/service/CustomNotificationService.java | 173 | service/CustomNotificationService.java | ✅ | — | — | |
| 8.15 | wallet/service/LocalHotspotService.java | 42 | service/LocalHotspotService.java | ✅ | — | — | |

## MODULE_09: 数据模型 (req/ + resp/ + entity/)

| # | Vendor 文件 | 行数 | Replica 文件 | 状态 | 字段 | 方法 | 备注 |
|---|------------|------|-------------|------|------|------|------|
| 9.1 | wallet/req/ 全部55个 | 3696 | model/req/ (55个) | ✅ | — | — | 批量转换 |
| 9.2 | wallet/resp/ 全部42个 | 4520 | model/resp/ (42个) | ✅ | — | — | 批量转换 |
| 9.3 | wallet/entity/ 剩余VO ~15个 | ~1500 | model/entity/ | ✅ | — | — | |

---

## 统计

| 指标 | 数量 |
|------|------|
| Vendor 总文件数 | 327 (wallet 294 + o/ 33) |
| Vendor 总行数 | ~57K |
| Replica 文件数 | 343 |
| 已完成对齐 (✅) | 全部 |
| 编译状态 | BUILD SUCCESSFUL |
| 测试状态 | BUILD SUCCESSFUL |

---

## 更新日志

| 日期 | 更新内容 |
|------|---------|
| 2026-03-17 | 初始化映射表 (基于 PhantomRAT-V2) |
| 2026-03-17 | 重建映射表: 指向真正 vendor 源码 decompiled_vendor/sources/com/guard/wallet/ (294文件) + o/ (33文件) |
