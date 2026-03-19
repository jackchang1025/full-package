# MODULE_05 数据收集 — Vendor 行为审计

## 1. 模块职责

系统事件采集 + 密码捕获。通过广播接收器监听系统事件（息屏/亮屏/来电/短信/开机/安装卸载/电池/网络），通过覆盖层捕获锁屏密码（PIN/图案），所有采集数据通过消息队列上报到服务端。

## 2. 文件映射对比

### receiver/ — 广播接收器

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 差距 |
|------------|------|-------------|------|------|
| ScreenBroadcastReceiver | 167 | data/collector/ScreenBroadcastReceiver | 106 | ⚠️ 缺 61 行 |
| PowerBroadcastReceiver | 140 | data/collector/PowerBroadcastReceiver | 66 | ⚠️ 缺 74 行 |
| PackageReceiver | 117 | data/collector/PackageReceiver | 50 | ⚠️ 缺 67 行 |
| BootBroadcast | 103 | data/collector/BootBroadcast | 48 | ⚠️ 缺 55 行 |
| CallReceiver | 88 | data/collector/CallReceiver | 61 | ⚠️ 缺 27 行 |
| ShutDownBroadcastReceiver | 89 | data/collector/ShutDownBroadcastReceiver | 41 | ⚠️ 缺 48 行 |
| BatteryLevelReceiver | 80 | data/collector/BatteryLevelReceiver | 41 | ⚠️ 缺 39 行 |
| SmsReceiver | 67 | data/collector/SmsReceiver | 69 | ✅ 接近 |
| AlarmReceiver | 37 | data/collector/AlarmReceiver | 26 | ⚠️ 缺 11 行 |
| NetWorkReceiver | 33 | data/collector/NetWorkReceiver | 37 | ✅ 接近 |
| LocaleChangeReceiver | 29 | data/collector/LocaleChangeReceiver | 39 | ✅ replica 更多 |

### stat/ — 统计事件 VO

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 状态 |
|------------|------|-------------|------|------|
| AccessibilityEventStatVO | 132 | data/stat/AccessibilityEventStatVO | 146 | ✅ |
| ScreenEventStatVO | 110 | data/stat/ScreenEventStatVO | 121 | ✅ |
| KeyboardEventVO | 55 | data/stat/KeyboardEventVO | 51 | ✅ |

### helper/ — 密码捕获覆盖层

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 状态 |
|------------|------|-------------|------|------|
| helper/r.java (PIN) | 428 | helper/PinCaptureOverlay | 405 | ✅ 接近 |
| helper/o.java (图案) | 303 | helper/PatternCaptureOverlay | 300 | ✅ 接近 |
| helper/n.java (弹窗) | 148 | helper/DialogOverlay | 186 | ✅ replica 更多 |

### Replica 独有

| 文件 | 行数 | 说明 |
|------|------|------|
| DataCollectionManager | 81 | 统一管理器 (vendor 没有，分散在 MainApplication.init) |
| LockCipherCollector | 30 | 密码采集桩 |

## 3. 核心行为分析

### 3.1 ScreenBroadcastReceiver — 最关键的 receiver

Vendor 行为 (167 行):
```
SCREEN_OFF:
  1. onLockStateChanged(0) — 发送 lockSubscribeId + 控制 PinCapture/PatternCapture
  2. MyAccessibilityService.q.set(true) — 暂停无障碍代理
  3. MyAccessibilityService.P().D() — 停止本地代理
  4. MyAccessibilityService.P().H(true, false) — 清缓存
  5. offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_OFF")
  6. CrackLockCipherPlug.f() — 触发密码破解
  7. d.a() — 辅助操作
  8. 清除 lockBatchId

SCREEN_ON:
  1. offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_ON")
  2. 如果屏幕锁定: 生成 lockBatchId

USER_PRESENT (解锁):
  1. MainApplication.unlockedInstance() — 如果未初始化
  2. CrackLockCipherPlug.g() — 密码破解成功回调
  3. offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT")
  4. onLockStateChanged(4)
  5. 恢复无障碍代理 (q.set(false) + g.F0(2))

所有事件:
  - LockActivity.a() — 非息屏时触发
  - h.D(screenState, "screenState") — 持久化状态
  - h.H(screenState, action) — 上报事件
```

Replica 差距:
- ❌ 缺少 MyAccessibilityService 暂停/恢复控制
- ❌ 缺少 offerStrategyEvent 调用
- ❌ 缺少 CrackLockCipherPlug 密码破解触发
- ❌ 缺少 lockBatchId 管理
- ❌ 缺少 LockActivity 触发
- ❌ 缺少 h.D/h.H 持久化和上报

### 3.2 所有 Receiver 的共同模式

Vendor 的每个 receiver 都遵循相同模式:
```java
onReceive(context, intent) {
    try {
        // 1. 解析 intent 数据
        // 2. 构建 XXX_VO (数据对象)
        // 3. 构建 MessageRecordVO
        //    messageRecordVO.setExtraBody(xxxVO)
        //    messageRecordVO.setIntentCode("android.intent.action.XXX")
        // 4. 通过消息队列发送
        //    MainApplication.getInstance().getHandlerMsgAndTimer().b(messageRecordVO)
    } catch (Exception e) { ... }
}
```

Replica 的 receiver 都只有日志，缺少:
- ❌ VO 数据对象构建
- ❌ MessageRecordVO 封装
- ❌ 消息队列发送 (HandlerMsgAndTimer.b())

### 3.3 密码捕获系统 (helper/r + helper/o + helper/n)

Vendor 架构:
```
helper/r.java (PinCapture, 428行):
  - WindowManager 悬浮窗覆盖层
  - 监听密码输入框 (CombineFilter: password=true)
  - 捕获 PIN 码 → DeviceCipher.textCipher
  - 通过 ReqListenHelper 上报

helper/o.java (PatternCapture, 303行):
  - WindowManager 悬浮窗覆盖层
  - 监听图案解锁 (CombineFilter: 特定 className)
  - 捕获触摸轨迹 → DeviceCipher.patternCipher
  - 通过 ReqListenHelper 上报

helper/n.java (DialogOverlay, 148行):
  - 弹窗覆盖层
  - 显示"系统修复中"等伪装消息
  - 阻止用户操作
```

Replica 行数接近 vendor，基本结构已有，但需要验证实际功能。

## 4. 优先修复项

### P0 (影响数据上报)
1. ScreenBroadcastReceiver 补齐 MyAccessibilityService 暂停/恢复控制
2. ScreenBroadcastReceiver 补齐 offerStrategyEvent 调用
3. 所有 receiver 补齐 MessageRecordVO 消息队列发送模式 (依赖 MODULE_01 网络)

### P1 (影响密码捕获)
4. ScreenBroadcastReceiver 补齐 CrackLockCipherPlug 触发
5. ScreenBroadcastReceiver 补齐 lockBatchId 管理
6. 验证 PinCaptureOverlay/PatternCaptureOverlay 实际功能

### P2 (完善)
7. PackageReceiver 补齐应用安装/卸载事件上报 (缺 67 行)
8. BootBroadcast 补齐开机初始化逻辑 (缺 55 行)
9. PowerBroadcastReceiver 补齐电池事件上报 (缺 74 行)
10. ShutDownBroadcastReceiver 补齐关机事件上报 (缺 48 行)

## 5. 真机验证要点

```bash
# 息屏/亮屏事件
adb shell input keyevent KEYCODE_POWER  # 息屏
adb shell input keyevent KEYCODE_POWER  # 亮屏
adb logcat -s "ScreenBroadcastReceiver"

# 来电事件
adb logcat -s "CallReceiver"

# 短信事件
adb logcat -s "SmsReceiver"

# 应用安装/卸载
adb install some.apk
adb logcat -s "PackageReceiver"

# 电池事件
adb logcat -s "BatteryLevelReceiver" "PowerBroadcastReceiver"

# 网络变化
adb shell svc wifi disable && adb shell svc wifi enable
adb logcat -s "NetWorkReceiver"
```
