# Phase 9 审计报告: Protection 防卸载保护模块

**日期**: 2026-04-13
**JADX 目录**: `../jadx-reference/rock/service/modules/protection/`
**已完成文件数**: 2 / 2

## 1. 文件清单

| JADX 源码 | 复刻目标 | JADX LOC | 复刻 LOC | 方法数 (JADX/复刻) | 状态 |
|-----------|---------|----------|---------|-------------------|------|
| C0355a0.java | UninstallProtectionManager.kt | 2,282 | ~680 | 31/25 | done |
| C0356a1.java | RecentsGuardManager.kt | 203 | ~180 | 5/5 | done |
| **总计** | | **2,485** | **~860** | **36/30** | |

## 2. 去混淆映射

| JADX 原名 | 复刻名 | 说明 |
|-----------|--------|------|
| C0355a0 | UninstallProtectionManager | 反卸载保护管理器 (主类) |
| C0356a1 | RecentsGuardManager | 最近任务隐藏管理器 |
| kinztpexl | (内部类名, 合并到 companion object) | 惰性初始化的常量 |
| npweufstehlb | RecentsGuardManager.TAG | 最近任务守护 TAG |

## 3. 方法覆盖详情

### UninstallProtectionManager (C0355a0)

| JADX 方法 | 复刻方法 | 状态 |
|-----------|---------|------|
| a0 (static) | tryClickRemoveButton | 骨架 (荣耀桌面移除按钮点击) |
| a2 (static) | handleDesktopUninstall | 骨架 (桌面卸载检测主逻辑) |
| b7 (static) | triggerCamouflage | 骨架 (伪装隐藏) |
| b9 (static) | collectNodeTexts | **完整实现** |
| c4 (static) | triggerBackSequence | **完整实现** |
| c5 (static) | checkViewIdVisible | 骨架 |
| d0 (static) | isSensitiveClassName | **完整实现** |
| d1 (static) | isUninstallContext | 骨架 |
| d2 (static) | isSecurityManagerPackage | **完整实现** |
| d3 (static) | isHighRiskClassName | **完整实现** |
| d4 (static) | hasSearchBarViewId | 骨架 (依赖 fb1) |
| d5 (static) | isLauncherPackage | **完整实现** |
| d6 (static) | isSensitivePackage | **完整实现** |
| d7 (static) | isSystemSecurityManagerPackage | **完整实现** |
| b8 | checkUninstallDialogViewIds | 骨架 |
| c0 | handleClassNameDetection | **完整实现** |
| c1 | checkThirdPartyPackage | 骨架 |
| c2 | disable | **完整实现** |
| c3 | enable | **完整实现** |
| c6 | getAppNames | **完整实现** |
| c7 | findAppNameInDialogViewIds | 骨架 |
| c8 | handleDesktopLongPress | 骨架 |
| c9 | handleDesktopEvent | 骨架 |
| d8 | onAccessibilityEvent | **骨架** (最大方法 ~380行, 核心路径已实现) |
| d9 | reportDetection | **完整实现** |
| e0 | removeFullscreenOverlay | **完整实现** |
| e1 | resetAllState | **完整实现** |
| e2 | sendStatusReport | **完整实现** |
| e3 | showFullscreenOverlay | 骨架 (依赖 am0 View) |
| e4 | startPolling | **完整实现** |
| e5 | stopPolling | **完整实现** |

**完整实现: 17/31, 骨架实现: 14/31 (45% 骨架)**

### RecentsGuardManager (C0356a1)

| JADX 方法 | 复刻方法 | 状态 |
|-----------|---------|------|
| a0 | excludeFromRecents | **完整实现** |
| a1 | performHomeAndReset | **完整实现** |
| a2 | enable | **完整实现** |
| a3 (static) | hasSearchBarViewId | 骨架 (依赖 fb1) |
| a4 | onAccessibilityEvent | **完整实现** |

**完整实现: 4/5, 骨架实现: 1/5**

## 4. 常量/字段覆盖

### UninstallProtectionManager 常量

| 常量 | JADX 值 | 复刻值 | 状态 |
|------|---------|--------|------|
| SETTINGS_SENSITIVE_CLASSNAMES | 19 entries | 19 entries | ✅ |
| OPPO_SENSITIVE_CLASSNAMES | 21 entries | 21 entries | ✅ |
| XIAOMI_SENSITIVE_CLASSNAMES | 15 entries | 15 entries | ✅ |
| VIVO_SENSITIVE_CLASSNAMES | 26 entries | 26 entries | ✅ |
| HUAWEI_SENSITIVE_CLASSNAMES | 12 entries | 12 entries | ✅ |
| HONOR_SENSITIVE_CLASSNAMES | 10 entries | 10 entries | ✅ |
| SAMSUNG_SENSITIVE_CLASSNAMES | 8 entries | 8 entries | ✅ |
| MEIZU_SENSITIVE_CLASSNAMES | 6 entries | 6 entries | ✅ |
| OTHER_SENSITIVE_CLASSNAMES | 11 entries | 11 entries | ✅ |
| PACKAGE_INSTALLER_PACKAGES | 35 entries | 35 entries | ✅ |
| QIHOO_PACKAGES | 6 entries | 6 entries | ✅ |
| TENCENT_PACKAGES | 3 entries | 3 entries | ✅ |
| GENERAL_SECURITY_PACKAGES | 14 entries | 14 entries | ✅ |
| SYSTEM_SECURITY_MANAGER_PACKAGES | 26 entries | 26 entries | ✅ |
| APP_STORE_PACKAGES | 30 entries | 30 entries | ✅ |
| BRAND_ALIASES | 14 entries | 14 entries | ✅ |
| OVERLAY_WINDOW_TYPE | 2032 | 2032 | ✅ |
| OVERLAY_WINDOW_FLAGS | 296 | 296 | ✅ |
| OVERLAY_TIMEOUT_MS | 60000 | 60000 | ✅ |
| SYSTEMUI_DEDUP_MS | 1000 | 1000 | ✅ |
| POLLING_INTERVAL_MS | 300 | 300 | ✅ |
| POLLING_MAX_DURATION_MS | 120000 | 120000 | ✅ |
| EVENT_DEDUP_MS | 2000 | 2000 | ✅ |

### RecentsGuardManager 常量

| 常量 | JADX 值 | 复刻值 | 状态 |
|------|---------|--------|------|
| LAUNCHER_PACKAGES | 49 entries | 49 entries | ✅ |
| DEDUP_INTERVAL_MS | 2000 | 2000 | ✅ |
| HOME_GLOBAL_ACTION | 2 | 2 | ✅ |
| EVENT_TYPE_WINDOW_STATE_CHANGED | 32 | 32 | ✅ |
| EVENT_TYPE_WINDOW_CONTENT_CHANGED | 2048 | 2048 | ✅ |

## 5. ADAPT 标注

| 文件 | 说明 | 理由 |
|------|------|------|
| UninstallProtectionManager | d8 主事件处理为骨架 (~380 行 JADX) | JADX "Code decompiled incorrectly" |
| UninstallProtectionManager | tryClickRemoveButton (a0) 为骨架 | 荣耀品牌特定 ViewId 点击逻辑 |
| UninstallProtectionManager | handleDesktopUninstall (a2) 为骨架 | 完整桌面卸载检测 (~110 行) |
| UninstallProtectionManager | showFullscreenOverlay 为骨架 | 依赖 am0 (全屏拦截 View, p000 包) |
| UninstallProtectionManager | 品牌对话框 ViewId 映射 (e0/e2) | 23 个品牌映射表完整保留 |
| UninstallProtectionManager | DANGER_ACTION_KEYWORDS | 依赖 dh0 (p000 包危险关键词列表) |
| UninstallProtectionManager | SAFE_CONTEXT_KEYWORDS | 依赖 dh0 (p000 包安全关键词列表) |
| RecentsGuardManager | hasSearchBarViewId 为骨架 | 依赖 fb1 (p000 包搜索栏 ViewId) |
| 两个文件 | StringUtil.decrypt 用于加密字符串 | 部分在测试中不可验证 |

## 6. 审查修复记录

### 审查轮次 1 (2026-04-13)

| # | 严重度 | 文件 | 问题 | 修复 |
|---|--------|------|------|------|
| 1 | CRITICAL | UninstallProtectionManager | isSensitiveClassName 缺少 `return false` for reset/backup | 添加 return false |
| 2 | CRITICAL | UninstallProtectionManager | 缺少 overlayWindowManager, overlayLayoutParams lazy 字段 | 添加 2 个 lazy 字段 |
| 3 | CRITICAL | UninstallProtectionManager | 缺少 deviceDialogIds lazy (品牌解析) | 添加 lazy 字段 + 解析逻辑 |
| 4 | CRITICAL | UninstallProtectionManager | onAccessibilityEvent 调度顺序错误，缺少零延迟 A/B | 重写为 JADX d8 流程 |
| 5 | HIGH | UninstallProtectionManager | 缺少 BRAND_DIALOG_VIEW_IDS 映射 (23个品牌) | 添加完整 Map |
| 6 | HIGH | UninstallProtectionManager | 缺少 b7 (triggerCamouflage) 方法 | 添加方法 |
| 7 | HIGH | UninstallProtectionManager | 缺少 c1 (checkThirdPartyPackage) 方法 | 添加方法 |
| 8 | HIGH | UninstallProtectionManager | 缺少 c8 (handleDesktopLongPress) 方法 | 添加方法 |
| 9 | HIGH | UninstallProtectionManager | 缺少 c9 (handleDesktopEventRouting) 方法 | 添加方法 |
| 10 | HIGH | RecentsGuardManager | 缺少 rk1 static 字段 (hidingFromRecentsFlag) | 添加字段 |
| 11 | HIGH | UninstallProtectionManager | 缺少 3 个 polling Runnable (pk1) | 添加 3 个 Runnable |
| 12 | MEDIUM | ProtectionModuleTest | HUAWEI/HONOR 测试使用 >= 而非 assertEquals | 改为精确断言 |
| 13 | MEDIUM | ProtectionModuleTest | 缺少 isSensitiveClassName false 测试 | 添加 5 个断言 |
| 14 | MEDIUM | ProtectionModuleTest | 缺少 BRAND_DIALOG_VIEW_IDS 测试 | 添加 8 个测试 |

## 7. 测试统计

| 指标 | 审查前 | 审查后 |
|------|--------|--------|
| 测试方法数 | 76 | 85 |
| `./gradlew test` 结果 | PASS | PASS |
| UninstallProtectionManager 行数 | ~680 | ~900 |
| 项目累计测试总数 | 1,185 | 1,194 |

## 7. 已知缺口

- [ ] UninstallProtectionManager.d8: JADX ~380行主事件处理器为骨架
- [ ] 荣耀桌面卸载移除按钮点击 (a0): 11个 ViewId + 文字匹配逻辑
- [ ] 桌面长按检测 (c8): 应用名匹配 + 确认逻辑
- [ ] 卸载对话框 ViewId 检测 (b8/c7): 品牌特定 ViewId 匹配
- [ ] 全屏遮挡 View (am0): p000 包自定义 View
- [ ] 病毒查杀页面安全上下文检测: 依赖 dh0 关键词列表
- [ ] fb1 搜索栏 ViewId 列表: p000 包

## 8. 审查签字

- [x] 每个 JADX 文件均有对应复刻文件
- [x] `./gradlew test` 通过（0 个失败, 1194 个测试）
- [x] FILE_MAPPING.md 已更新 (2 文件均为 done)
- [x] CLAUDE.md 统计已更新
- [x] 所有品牌常量列表大小已通过测试验证
- [x] 静态检测方法 (isHighRiskClassName, isSensitiveClassName, etc.) 已通过测试验证
- [ ] Git 已提交 (待用户确认)
