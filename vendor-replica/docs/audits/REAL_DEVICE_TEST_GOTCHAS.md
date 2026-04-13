# 真机测试 Gotchas — vendor-replica 自动化验证陷阱集

**Date:** 2026-04-12
**Scope:** 在小米 / OPPO / 华为 / vivo 真机上跑 `vendor-replica/scripts/real-device-verify/` 时已知的踩坑点
**Source:** 2026-04-12 Xiaomi 2211133C / Android 16 / HyperOS 3 (`192.168.31.102:39851`) 真机调试 session

---

## Gotcha 1: `adb install -g` 触发 vendor 的 "dev mode bypass" 跳过无障碍引导（**最严重 P0**）

### 现象

用 `adb install -r -g app-debug.apk` 装包后冷启动 App，**用户看不到无障碍引导对话框，App 直接进入主页 (loadUrl(MainUrl))**。结果 7 个保活引擎全部无法启用，整个 UI 自动化模块瘫痪。

### Root cause

vendor `MainActivity.onResume()` (replica `vendor-replica/app/src/main/java/com/guard/wallet/activity/MainActivity.java:120`) 有这段判断：

```java
if (MyAccessibilityService.P() == null && !SystemHelper.j()) {
    boolean adbCanWrite;
    synchronized (SharedPrefsManager.class) {
        adbCanWrite = SharedPrefsManager.e("adbCanWriteSecure");
    }
    if (!adbCanWrite) {
        this.webViewRef.get().loadUrl(GuideDialogUtils.getGuidePageUrl());
        this.webViewRef.get().setGuide(true);
        GuideDialogUtils.showAccessibilityEnableDialog();
        return;
    }
}
```

其中 `SystemHelper.j() = PermissionUtils.hasWriteSecureSettings()` (`SystemHelper.java:93`)。

vendor 的设计意图：**如果 app 已经能写 secure settings 就不需要无障碍引导（开发模式下 ADB 可以代替无障碍干很多事）**。生产用户从应用市场装包**永远**不会有 `WRITE_SECURE_SETTINGS`，所以引导在真实场景下总会显示。

但是 `adb install -g` 会让 HyperOS / MIUI 把 manifest 里**所有**权限都尝试 grant，包括 signature 级的 `WRITE_SECURE_SETTINGS`（因为 `com.android.shell` uid 本身有这个权限，可以传递给装包的 app）。

链路：

```
adb install -r -g APK
       ↓
HyperOS pm install 看到 -g → 对 manifest 所有权限调 pm grant
       ↓
manifest 里有 <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS" />
       ↓
HyperOS 允许 com.android.shell uid 授予 WRITE_SECURE_SETTINGS（dev mode 特权）
       ↓
APK 启动 → MainActivity.onResume() line 120:
   MyAccessibilityService.P() == null  →  true (无障碍未启用)
   !SystemHelper.j()                   →  false (因为 hasWriteSecureSettings() 返回 true)
       ↓
组合后整个 if 不进入 → 跳过引导分支
       ↓
直接 loadUrl(ConfigManager.getMainUrl()) → 显示主页
```

### 验证证据（2026-04-12 真机调试）

```
$ adb shell dumpsys package com.guard.wallet | grep WRITE_SECURE_SETTINGS
      android.permission.WRITE_SECURE_SETTINGS     ← granted (出现在权限列表里)

$ adb shell pm revoke com.guard.wallet android.permission.WRITE_SECURE_SETTINGS
$ adb shell am start -W -n com.guard.wallet/.activity.MainActivity
$ adb shell uiautomator dump /sdcard/ui.xml && adb pull /sdcard/ui.xml
$ grep -oE 'text="[^"]*"' ui.xml | sort -u
text="GO IMMEDIATELY"   ← ✅ 这是 vendor LangDialog.okText，正是无障碍引导对话框的"开启权限"按钮
```

### 修复

`vendor-replica/scripts/real-device-verify/lib/common.sh` 的 `clean_install()` 函数在 `install -r -g` 之后**立即** revoke：

```bash
"$ADB" -s "$DEVICE_ID" shell "pm revoke $PACKAGE android.permission.WRITE_SECURE_SETTINGS"
```

### 不修复 vendor / replica 代码本身

`SystemHelper.j()` 的判断逻辑是 **vendor 1:1 对齐** (vendor `MainActivity.java:317`)，不能改。修复发生在测试脚本层。

### 影响范围

任何人写 vendor-replica 的真机自动化脚本时**必须**在 install 后 revoke `WRITE_SECURE_SETTINGS`，否则永远看不到引导对话框，永远没法启用无障碍服务，永远没法测试任何保活/自动化引擎。

---

## Gotcha 2: APK mtime 检查只看 assets 文件会忽略 Java 源码改动（**P1 build correctness**）

### 现象

`vendor-replica/scripts/real-device-verify/lib/common.sh` 之前的 `ensure_apk_built()` 只比较 `vendor-replica/app/src/main/assets/locateValues.json` 的 mtime 与 APK 的 mtime。

如果在两次跑脚本之间**修改了 Java 源码（但没动 locateValues.json）**，mtime 检查会判定"APK 已最新"跳过 gradle 编译，结果**装到设备上的是没有最新代码的旧 APK**。

### 真实事故

2026-04-12 02:30 调试时，Plan A 的 4 个 commit 全部合并到 main，但我们之前 23:32 编译过一次 APK。脚本看到 APK mtime > locateValues.json mtime，跳过编译，安装的旧 APK 里**完全没有 LocateValuesSeeder 类**。结果 logcat 里搜不到 `LocateValuesSeeder:` 输出，浪费了大概 30 分钟时间排查。

### 修复

新版 `ensure_apk_built()` 用 `find vendor-replica/app/src/main -type f \( -name '*.java' -o -name '*.kt' -o -name '*.xml' -o -name '*.json' -o -name '*.so' \) -printf '%T@ %p\n' | sort -nr | head -1` 找出整棵 src 树里 mtime 最新的文件作为参照，确保任何源码改动都能触发重新打包。

**额外防线**：打包后用 `unzip -p classes*.dex | strings | grep LocateValuesSeeder` 验证 dex 里确实包含目标类。这个检查不依赖 mtime 启发式，是最后的 ground truth。

---

## Gotcha 3: HyperOS / MIUI "USB 安装管理" 信任窗口机制（**P2 process**）

### 现象

短时间内重复 `adb install` 同一包名，会偶发 `INSTALL_FAILED_USER_RESTRICTED: Install canceled by user`。`pm install` 路径也会失败。

这不是简单的频率限制，是 **MIUI 安全中心的 USB 安装信任窗口机制**：
- 设备屏幕灭屏后一段时间会重置"USB 安装信任"
- 重置后下一次 ADB 安装需要用户在设备屏幕上手动点"继续安装" / "允许"
- 自动化脚本在无人值守时会因此卡住

### 解决

**永久解除**（推荐）：
1. 设置 → 我的设备 → 全部参数 → 多次点 "MIUI 版本" 进开发者模式
2. 设置 → 更多设置 → 开发者选项 → **关闭** "USB 安装" 的安全保护
3. 部分 HyperOS 版本路径：设置 → 安全 → 应用安全 → USB 安装 → 关闭

**临时**：
1. 让设备屏幕保持唤醒（`adb shell svc power stayon true`）
2. 在脚本里加 retry + 等待逻辑（30s, 60s, 90s 三轮）
3. 装包失败时立即提示操作员去设备上点确认弹窗

### 影响范围

只影响 MIUI / HyperOS 真机。OPPO ColorOS / 华为 / vivo 也有类似机制，但触发条件不同：
- OPPO: 第三方 app 的"未知来源安装" Switch
- 华为: 安装来源管控
- vivo: i 管家 → 安全防护 → USB 调试授权管理

---

## Gotcha 4: `clean_install()` 不是原子操作（次要）

### 现象

之前的 `clean_install()` 是 `uninstall + install` 两步独立 ADB 命令。如果 `install` 失败，App 已经被 uninstall 了 → 设备上没有 App → 后续步骤全部 cascade 失败。

### 修复

可选改进：把 `install` 失败时回滚到上一次的 APK 版本（但这需要保留旧 APK 副本）。当前做法是 install 失败立即 fail-fast 退出整个脚本，让操作员手动处理。

---

## Gotcha 5: `am start` 直接跳转厂商 Activity 在严苛 ROM 上会失败

### 现象

OPPO ColorOS 16+, HyperOS 3+ 等严苛 ROM **拒绝** `com.android.shell` 通过 `am start -n` 直接跳到厂商私有 Activity（例如 `com.miui.powerkeeper/.ui.HiddenAppsContainerManagementActivity`），返回：
```
Error: Activity class {com.miui.powerkeeper/com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity} does not exist
```

这不是 Activity 真的不存在，是 ROM 的 PackageManager 对 shell uid 隐藏了非 exported 的 Activity。

### 缓解

1. 用 `am start -a android.settings.APPLICATION_DETAILS_SETTINGS -d package:com.guard.wallet` 跳到应用详情页（这是 standard 的，所有 ROM 都允许），然后再让用户/无障碍服务自动 click 进入子页
2. 或者通过 vendor 自身的 engine（`OppoEngine.k0()` 等方法）打开，因为 engine 用 AccessibilityService 触发的导航不受 shell uid 限制
3. 或者用 `monkey -p com.guard.wallet 1` 启动 App 后，让 vendor 自己的 ConfirmDeviceCredentialDelegate / OpenDevelopmentDelegate 来导航（前提：无障碍服务已启用）

---

## Gotcha 6: 设备端口号不是固定的 5555

### 现象

`ADB_CONNECTION.md` 列的 IP 是 `192.168.31.102:5555`，但 Android 11+ 的 wireless ADB（`adb pair` 配对模式）每次重启 ADB 服务**端口号是动态分配**的（例如 `192.168.31.102:39851`）。

### 缓解

脚本支持 `DEVICE_ID` 环境变量覆盖默认值：

```bash
DEVICE_ID=192.168.31.102:39851 \
  bash vendor-replica/scripts/real-device-verify/verify-xiaomi-13.sh
```

`ADB_CONNECTION.md` 的 IP/端口表只是默认占位，每次连接前应该用 `adb devices` 确认实际端口。

---

## 检查清单：开始真机验证前

- [ ] Plan A 4 个 task 全部合并 (`grep -c LocateValuesSeeder.seedIfChanged vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` ≥ 1)
- [ ] APK 打包脚本会捕获 Java 源码 mtime（`ensure_apk_built` 用 find 而不是单文件 stat）
- [ ] 设备 USB 安装管理已关闭"安全保护"（HyperOS / MIUI 必须）
- [ ] 设备屏幕保持唤醒（`adb shell svc power stayon true`）
- [ ] 实际 ADB 端口号已通过 `adb devices` 确认
- [ ] 脚本里的 `clean_install()` 包含 `pm revoke WRITE_SECURE_SETTINGS` 步骤
- [ ] 脚本里 `assert_seeder_success` 接受 `SEEDED_FIRST_TIME` / `SKIPPED_UP_TO_DATE` / `SKIPPED_ADOPTED_EXISTING` / `SEEDED_UPDATED` 4 种 PASS 状态
- [ ] 设备前有人能在偶发 install 限流时手动确认弹窗（或者提前永久解除）
