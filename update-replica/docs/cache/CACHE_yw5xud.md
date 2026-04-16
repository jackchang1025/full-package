# Yw5xudEngines 知识缓存
> 生成时间: 2026-04-14 | 文件数: 11 (非内部类) | 总 LOC: 50,596 | 内部类文件: 178

## 文件清单

| # | JADX 文件 | 实际 vendor 类 | Kotlin 复刻 | JADX LOC | 内部类 | 品牌 |
|---|----------|---------------|------------|---------|--------|------|
| 1 | C0368a5.java | **OppoStepsSimplified** | OppoSteps.kt | 11,012 | 42 | OPPO/OnePlus/Realme |
| 2 | C0371a8.java | **VivoSteps** | VivoSteps.kt | 11,061 | 9 | vivo/iQOO |
| 3 | C0365a2.java | **HuaweiSteps** | HuaweiSteps.kt | 8,907 | 14 | 华为/荣耀 |
| 4 | C0367a4.java | **MiuiSteps (主)** | MiuiSteps.kt | 8,853 | 30 | 小米/红米 (MIUI/澎湃) |
| 5 | C0364a1.java | **MiuiSteps (辅助)** | (合并入 MiuiSteps.kt) | 3,715 | 0 | 小米/红米 内部类辅助 |
| 6 | C0372a9.java | Yw5xudHandler | Yw5xudHandler.kt | 2,672 | 0 | 入口 handler |
| 7 | C0366a3.java | **MeizuSteps** | MeizuSteps.kt | 2,482 | 29 | 魅族 |
| 8 | C0370a7.java | **SamsungSteps** | SamsungSteps.kt | 1,574 | 27 | 三星 |
| 9 | umrkmgrri.java | umrkmgrri | umrkmgrri.kt | 276 | 19 | 品牌引擎入口 Activity |
| 10 | AbstractC0363a0.java | AbstractSteps | OsFamily.kt | 22 | 0 | 抽象基类 |
| 11 | AbstractC0369a6.java | AbstractBrandDetector | BrandDetector.kt | 22 | 0 | 品牌检测抽象类 |

> **重要修正 (2026-04-16 审计)**: 旧版缓存映射完全错误（C0365a2 误标为 GenericSteps 实为 HuaweiSteps, C0367a4 误标为 HuaweiSteps 实为 MiuiSteps 等）。以上为基于 Log tag + 内部类名 + 品牌关键词计数的精确映射。vendor 无独立 "GenericSteps" 类，replica 的 GenericSteps.kt 是跨品牌通用逻辑的抽象（无单一 vendor 对应文件）。WRITE_SETTINGS 不在 yw5xud 目录，由 `modules/C0327b2.java` (WriteSettingsPermissionManager) 处理。

## 品牌引擎详情

### OppoStepsSimplified (C0368a5) — 11,012 LOC, 42 内部类 → OppoSteps.kt
- **覆盖品牌**: OPPO, OnePlus, Realme
- **FlowType 枚举**: 6 种 (execute$1 ~ execute$6)
- **关键操作**: 自启动、后台弹窗、电池优化、悬浮窗、通知、权限管理、最近任务锁定
- **特殊处理**: vivo29 开关兼容、BalancedMode 点击、软权限详情页

### VivoSteps (C0371a8) — 11,061 LOC, 9 内部类 → VivoSteps.kt
- **覆盖品牌**: vivo, iQOO
- **FlowType 枚举**: 7 种
- **关键操作**: 基础权限、电池优化、通知渠道、悬浮窗、全部文件访问
- **特殊处理**: 页面稳定等待、返回桌面

### HuaweiSteps (C0365a2) — 8,907 LOC, 14 内部类 → HuaweiSteps.kt
- **覆盖品牌**: 华为、荣耀
- **FlowType 枚举**: 12+ 种
- **关键操作**: 基础权限、悬浮窗、电池优化、通知渠道、全部文件访问、Play Store 禁用、小米自启(通用路径)
- **特殊处理**: WindowManager 获取显示尺寸、多 API 级别适配

### MiuiSteps (C0367a4) — 8,853 LOC, 30 内部类 → MiuiSteps.kt
- **覆盖品牌**: 小米、红米 (MIUI/澎湃OS)
- **VerifyResult/LockVerifyResult/HonorClickResult**: 3 种结果枚举
- **关键操作**: 自启管理、电池优化、电池白名单、悬浮窗、通知监听、最近任务锁定、搜索App
- **特殊处理**: 荣耀 Gallery 权限、性能与省电、手势点击、滚动点击、页面稳定等待

### MeizuSteps (C0366a3) — 2,482 LOC, 29 内部类 → MeizuSteps.kt
- **覆盖品牌**: 魅族
- **FlowType 枚举**: 4+ 种
- **关键操作**: 自启、后台弹窗、电池设置、通知管理、悬浮窗、基础权限
- **特殊处理**: ViewId 切换、CheckBox 模糊搜索、确认弹窗处理、Android 版本分支

### SamsungSteps (C0370a7) — 1,574 LOC, 27 内部类 → SamsungSteps.kt
- **覆盖品牌**: Samsung/三星
- **SubBrand 枚举**: 3 种
- **关键操作**: 后台自启、电池设置、文件访问、悬浮窗、通知管理、最近任务锁定、App 列表读取
- **特殊处理**: 子品牌检测 (mOppo/mOnePlus/mRealme)、Smali Intent 启动

### MeizuSteps (umrkmgrri) — 276 LOC, 19 内部类
- **覆盖品牌**: 魅族
- **FlowType 枚举**: 5 种 (execute$1 ~ execute$5)
- **关键操作**: 自启管理、电池优化、悬浮窗、全部文件访问
- **特殊处理**: App 搜索定位、流程验证循环

### Yw5xudHandler (C0372a9) — 2,672 LOC, 0 内部类
- **角色**: 入口调度器，extends AbstractC0330a0 (AccessibilityDelegate)
- **职责**: 根据 BrandDetector 结果路由到对应 Steps 类

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| C0372a9 | Yw5xudHandler | AccessibilityDelegate | 品牌路由入口 |
| AbstractC0363a0 | OsFamily | abstract | OS 族抽象基类 |
| AbstractC0369a6 | BrandDetector | abstract | 品牌检测抽象 |
| C0365a2 + C0364a1 | GenericSteps | — | 通用 AOSP 步骤 |
| C0366a3 | MiuiSteps | — | 小米/红米步骤 |
| C0367a4 | HuaweiSteps | — | 华为/荣耀步骤 |
| C0368a5 | VivoSteps | — | vivo/iQOO 步骤 |
| C0370a7 | OppoSteps | — | OPPO/OnePlus/Realme 步骤 |
| C0371a8 | SamsungSteps | — | 三星步骤 |
| umrkmgrri | MeizuSteps | Activity (JADX) | 魅族步骤 |

## 模块间依赖
- **依赖**: modules/base/ (AccessibilityDelegate), service/ (MyAccessibilityService 获取窗口信息), util/ (DeviceUtils 品牌检测)
- **被依赖**: service/ (MyAccessibilityService 持有 Yw5xudHandler), modules/ (MainOrchestrator 调度)

## 已知缺口
- [x] 全部 11 个文件已完成复刻 (178 个内部类合并到 10 个 .kt 文件)
- [x] C0364a1 + C0365a2 合并到 GenericSteps.kt

## 逆向经验

> 记录从 JADX 源码审查中发现的经验。
