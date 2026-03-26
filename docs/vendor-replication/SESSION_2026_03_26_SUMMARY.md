# Session 2026-03-26 工作总结

> **主题**: GKD Selector 集成 + OPPO 权限自动化修复 + ColorOS 16 适配
> **设备**: OPPO PGFM10, Android 16 (SDK 36), ColorOS PGFM10_16.0.3.500(CN01)

---

## 一、完成的工作

### 阶段 1：调研

1. **Vendor APK 分析** — 确认 vendor 未使用开源框架，纯原生 AccessibilityService
2. **开源方案调研** — 评估 GKD / Assists / AutoJs6 / HelloDaemon / AutoStarter 等
3. **方案选型** — 选择 GKD Selector 引擎（37k stars，CSS-like 语法，可独立提取）
4. **UIAutomator 评估** — 不可行（需 ADB 连接，不能嵌入 APK）

### 阶段 2：GKD Selector CRITICAL 修复

代码审核发现 GKD 集成层**完全不工作**（所有选择器调用静默返回 null），修复了 3 个 CRITICAL + 4 个 HIGH 问题：

| Commit | 修复 |
|--------|------|
| `5aa2d80d` | `GkdTransform.getAttr` 解包 QueryContext + Boolean 类型 + getRoot |
| `86e7bcd1` | `GkdSelectorHelper` 用 `querySelector` 遍历子树 + 选择器缓存 |
| `b967d362` | 修复 `#id` 无效语法 + 11 处动态拼接 escape |
| `d2e567a0` | ProGuard keep 规则 |
| `98de2ac8` | ScreenAdaptUtil 零值防护 |

### 阶段 3：OPPO 权限自动化 Bug 修复

| Commit | 修复 |
|--------|------|
| `9c5bad54` | 过度导航：先检测当前页面再决定是否 back |
| `6a501c34` | 坐标适配：ScreenAdaptUtil 按屏幕比例计算 |
| `7fc7cddc` | 遮罩过早移除：finish 前验证已离开权限页面 |
| `aa61efe4` | OppoPermissionEngine 支持 PermissionController |

### 阶段 4：ColorOS 16 适配

| Commit | 修复 |
|--------|------|
| `54617942` | 自启动/关联启动失败不阻塞 + `allowFullBackground` 门控移除 |
| `6814f6ac` | RadioButton 模式：Switch → RadioButton → 对话框 → fallback |
| `94718cd8` | E2E 脚本适配 ColorOS 16 日志模式 |
| `c662dcdb` | **`>` 改为 `>n`**（任意深度后代，穿透 RelativeLayout 中间层） |

### 阶段 5：测试

| 测试 | 数量 | 结果 |
|------|------|------|
| `OppoGkdSelectorFixtureTest` | 43 | ✅ 全部通过 |
| `ScreenAdaptUtilTest` | 7 | ✅ 全部通过 |
| 既有单元测试 | 全部 | ✅ 无回归 |
| E2E 真机 | - | 🟡 `granted=2`，部分权限成功 |

---

## 二、E2E 真机测试结果分析

### 最终运行结果

```
Pipeline 触发:     PASS (Pipeline 日志)
应用详情页进入:     FAIL — 设备状态已被之前测试修改，Pipeline 跳过保活
耗电管理进入:       FAIL — 同上
完全允许后台:       FAIL — 同上
允许自启动:         FAIL — ColorOS 16 已移除
引擎完成:          PASS
权限自动授权:       FAIL — PermissionController 坐标点击未验证
遮罩已关闭:        PASS
返回应用页面:       PASS
```

### 关键发现

1. **`granted=2` 证明 GKD `>n` 修复生效** — 通讯录、电话等 securitypermission 权限已成功授权
2. **Pipeline 跳过保活阶段** — 因设备已被前几次测试修改过保活设置
3. **位置/摄像头/麦克风权限卡在循环** — PermissionController 坐标点击需要验证

---

## 三、下次 Session 待修复

### 3.1 PermissionController 坐标点击未生效

**症状：** "位置信息"权限进入 PermissionController 页面后，`selectBestAllowOption()` 调用 `clickAllowByCoordinate()` 但点击未命中按钮。

**排查步骤：**
1. ADB dump PermissionController 页面获取真实按钮坐标
   ```bash
   adb shell uiautomator dump /sdcard/perm_controller.xml
   adb pull /sdcard/perm_controller.xml
   ```
2. 对比 `ScreenAdaptUtil` 计算值与实际坐标
3. 注意：`DisplayMetrics.heightPixels` 不含系统栏，但 `tapAtCoordinate` 可能是绝对坐标

**关键代码：**
- `OppoEngine.selectBestAllowOption()` 行 804-829
- `OppoEngine.clickAllowByCoordinate()` 行 838-858
- `ScreenAdaptUtil.getPermissionAllowCoordinate()` — 基准 (550, 1052) @ 1240x2772

### 3.2 权限循环卡在 PermissionController

**症状：** `handlePermissionManagement()` 循环中，点击"位置信息"进入 PermissionController → `selectBestAllowOption()` 返回 false → `performBack()` → 回到列表 → 再次点击"位置信息" → 无限循环

**修复方向：**
- 在循环中记录失败的权限名
- 同一权限失败 2 次后跳过
- 或检测 PermissionController 包名后直接使用坐标点击

**关键代码：**
- `OppoEngine.handlePermissionManagement()` 行 672-752
- `OppoEngine.selectBestAllowOption()` 行 804-829

### 3.3 E2E 设备状态重置

**症状：** 多次运行后设备保活设置已被修改，Pipeline 检测到已完成就跳过保活阶段。

**修复方向：**
- E2E 脚本开头添加设备状态重置
  ```bash
  adb shell pm clear com.oplus.battery  # 清除耗电管理设置
  adb shell settings delete secure enabled_accessibility_services
  ```
- 或在应用中添加"重置保活状态"的测试入口

---

## 四、关键文件速查

### 生产代码

```
# GKD Selector 集成层
android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt
android/app/src/main/java/com/vendor/rat/auto/util/GkdSelectorHelper.java
android/app/src/main/java/com/vendor/rat/auto/util/ScreenAdaptUtil.java

# OPPO 引擎
android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java
android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java
android/app/src/main/java/com/vendor/rat/auto/engine/AutoEngine.java
android/app/src/main/java/com/vendor/rat/auto/engine/support/SwitchOperations.java

# 配置
android/app/src/main/java/com/vendor/rat/config/TextConfig.java
android/app/proguard-rules.pro
```

### 测试

```
android/app/src/test/java/com/vendor/rat/auto/engine/vendor/OppoGkdSelectorFixtureTest.java
android/app/src/test/java/com/vendor/rat/auto/engine/vendor/OppoEngineFixtureTest.java
android/app/src/test/java/com/vendor/rat/auto/engine/vendor/OppoPermissionMgmtFixtureTest.java
android/app/src/test/java/com/vendor/rat/auto/util/ScreenAdaptUtilTest.java
android/app/src/test/java/com/vendor/rat/auto/testutil/UiDumpFixture.java
android/scripts/e2e_oppo_test.sh
```

### Fixture XML（真机 ADB dump）

```
android/app/src/test/resources/fixtures/oppo/
  app_detail.xml          — 应用详情页
  power_control.xml       — 耗电管理页 (RadioButton 模式)
  power_dialog.xml        — 确认对话框
  permission_popup.xml    — 系统权限弹窗
  perm_mgmt_top.xml       — 权限管理顶部
  perm_mgmt_denied.xml    — 权限管理不允许分组
  perm_sub_location.xml   — 位置权限 (4 RadioButton)
  perm_sub_camera.xml     — 摄像头权限 (3 RadioButton)
  perm_sub_phone.xml      — 电话权限 (2 RadioButton)
  perm_sub_contacts.xml   — 通讯录权限 (2 RadioButton + 隐私替身)
  perm_sub_sms_mgmt.xml   — 短信权限
  perm_sub_shortcut.xml   — 桌面快捷方式
```

### 日志

```
/tmp/e2e_oppo_192.168.31.249_20260326_162732.log  — 最新 E2E 日志
```

### 文档

```
docs/vendor-replication/GKD_SELECTOR_INTEGRATION.md    — GKD 集成技术文档
docs/vendor-replication/SESSION_2026_03_26_SUMMARY.md   — 本次会话总结
docs/superpowers/plans/2026-03-26-oppo-permission-automation-fix.md
docs/superpowers/plans/2026-03-26-gkd-selector-critical-fixes.md
```

---

## 五、GKD 选择器语法速查

```
# 属性
TextView[text="精确"]        [text*="包含"]       [text^="前缀"]
[id$="button1"]             [clickable=true]     [checked=false]

# 关系 (⚠️ 项目中统一用 >n 不用 >)
A >n B    B 是 A 的任意深度后代 ← 常用
A > B     B 是 A 的直接子节点 (不穿透中间层，慎用)
A <<n B   B 是 A 的任意深度祖先
A < B     B 是 A 的直接父节点

# OPPO UI 结构: LinearLayout[clickable] > RelativeLayout > TextView
# 必须用 >n 穿透 RelativeLayout:
[clickable=true] >n TextView[text="允许"]    ✅
[clickable=true] > TextView[text="允许"]     ❌ 不匹配
```

---

## 六、Commit 日志

```
c662dcdb fix(critical): use >n (descendant) instead of > (direct child) in GKD selectors
94718cd8 fix: update E2E test script for ColorOS 16 adaptation
6814f6ac fix: handle ColorOS 16 RadioButton mode in handleFullBackgroundSwitch
54617942 fix: ColorOS 16 adaptation — remove flow-blocking gates
30127faa test: add GKD Selector fixture tests for OPPO engines
98de2ac8 fix: add zero-dimension guard to ScreenAdaptUtil
d2e567a0 fix: add ProGuard keep rules for GKD Selector library
b967d362 fix(critical): replace invalid #id syntax + escape dynamic selector strings
86e7bcd1 fix(critical): GkdSelectorHelper uses querySelector for tree traversal + cache
5aa2d80d fix(critical): GkdTransform.getAttr handles QueryContext + returns Boolean
aa61efe4 feat: add PermissionController support to OppoPermissionEngine
7fc7cddc fix: prevent premature overlay removal after permission granting
6a501c34 fix: adapt coordinate clicking to screen resolution
9c5bad54 fix: prevent over-navigation in OppoEngine permission management
5442ac02 feat: add ScreenAdaptUtil for coordinate-based clicking across resolutions
```
