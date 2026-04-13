# Phase 6 审计报告: Setup & ADB Pairing

**日期**: 2026-04-13
**JADX 目录**: `../jadx-reference/rock/service/modules/setup/`
**已完成文件数**: 4 / 4 (+14 内部类编译进父类)

## 1. 文件清单

| JADX 源码 | 复刻目标 | JADX LOC | 复刻 LOC | 方法数 (JADX/复刻) |
|-----------|---------|----------|---------|-------------------|
| C0362a4.java | UiNodeHelper.kt | 249 | 270 | 9/9 |
| AbstractC0361a3.java + dh0 | SetupConstants.kt | 30+dh0 | 572 | 0/0 (15 常量列表) |
| C0358a0.java + 9 inner | OpenDevelopmentDelegate.kt | 1,401 | 1,413 | 33/35 |
| C0360a2.java + 5 inner | SystemOptimizeManager.kt | 5,666 | 3,450 | 115/105 |
| **总计** | | **7,346** | **5,705** | |

## 2. 审查修复记录

### 审查轮次 1 (2026-04-13)

| # | 严重度 | 文件 | 问题 | 修复 |
|---|--------|------|------|------|
| 1 | CRITICAL | SetupConstants | 15 个字符串列表严重截断（仅 3-5 条/列表 vs vendor 30-100 条） | 从 dh0.java 完整提取全部条目 (196→572 行) |
| 2 | CRITICAL | SystemOptimizeManager | 缺少 ~45 个方法（UI 自动化/配对流程/心跳/权限授予） | 补充 35 个方法 (2066→3450 行) |
| 3 | HIGH | UiNodeHelper | findSameRowToggle 缺少 `rect.top >= 0` 边界检查 | 已修复 |
| 4 | HIGH | SetupConstants | 新增 6 个子列表 (MIUI/OS/ColorOS/软件版本号/版本号/HarmonyOS) | 已实现 |
| 5 | MEDIUM | UiNodeHelper | findSameRowToggle 添加 JADX 反编译歧义标注 | 已添加 ADAPT 注释 |
| 6 | MEDIUM | SetupConstants | 新增 12 个 UI 文本常量列表 (USB调试弹窗/网络确认/无线调试等) | 已实现 |

## 3. ADAPT 标注

| 文件 | 说明 | 理由 |
|------|------|------|
| SystemOptimizeManager | SPAKE2 doPair() 为 stub | Spake2 库未加入 build.gradle |
| SystemOptimizeManager | executeShellCommand() 为 stub | 依赖 g41 ADB 连接类（p000 包） |
| SystemOptimizeManager | uploadAdbKeys()/uploadDebugPort() 为 stub | 依赖 HTTP client（p000 包） |
| OpenDevelopmentDelegate | bringAppForeground() 简化 | iuzxujjtqev Activity 尚未复刻 |
| UiNodeHelper | findSameRowToggle 使用 OR 逻辑 | JADX 反编译标记 "incorrectly", OR 为最可能意图 |
| SetupConstants | ALL_BUILD_NUMBER_TEXTS 使用 Set 去重 | vendor 使用 List 拼接（可能含重复）|

## 4. VENDOR_VERIFY 条目

| 文件 | 描述 | 风险等级 |
|------|------|---------|
| OpenDevelopmentDelegate | Y() 7 次点击后密码弹窗轮询的 goto 逻辑 | MEDIUM |
| SystemOptimizeManager | mainAccessibilityEventHandler() 状态机精确转换 | HIGH |
| SystemOptimizeManager | JADX "Method not decompiled" 的方法 (~7 个) | HIGH |
| SystemOptimizeManager | heartbeatEventDispatcher() 账户保护检查逻辑 | MEDIUM |

## 5. 测试统计

| 指标 | 审查前 | 审查后 |
|------|--------|--------|
| 测试文件数 | 4 | 4 |
| 测试方法数 | 259 | 301 (18+26+98+159) |
| `./gradlew test` 结果 | PASS | PASS |
| 本阶段测试总数 | 259 | 301 |
| 项目累计测试总数 | 812 | 854 |

## 6. 厂商差异摘要

### 方法覆盖率

| JADX 类 | JADX 方法数 | 复刻方法数 | 覆盖率 |
|---------|-----------|-----------|--------|
| C0362a4 | 9 | 9 | 100% |
| C0358a0 | 33 | 35 | 100%+ |
| C0360a2 | 115 | 105 | 91% |

### 剩余 10 个未覆盖方法（C0360a2）

主要为静态工具方法已合并到实例方法中，或依赖不可用的 p000 类：
- c2/c3: 文本集合辅助方法（已合并到 collectTextViewNodes/collectAllNodes）
- d2/d3: 同上
- e5/e6: ADB 连接建立（依赖 g41 类）
- f0: SSLSocket 密钥导出辅助（已合并到 exportKeyingMaterial）
- g0/g1/g2: 节点搜索辅助（已合并到 findNodeByTexts/findScrollableNode）

## 7. 已知缺口

- [ ] SPAKE2 配对: 需要添加 `io.github.muntashirakon:spake2-java` 依赖
- [ ] ADB 底层连接 (g41 类): 需要在后续 Phase 实现 p000 工具类
- [ ] HTTP 上传 (uploadAdbKeys/uploadDebugPort): 依赖 HTTP client
- [ ] 真机测试: 开发者选项 UI 自动化需要在真实设备上验证
- [ ] exportKeyingMaterial: Conscrypt 反射调用需要真机验证
- [ ] NsdManager mDNS 发现: 需要真机 WiFi 环境测试

## 8. 审查签字

- [x] 每个文件均通过 REVIEW.md 全部检查项
- [x] `./gradlew test` 通过（0 个失败, 854 个测试）
- [x] FILE_MAPPING.md 已更新 (4 文件均为 done)
- [x] 审查修复已完成（6 个问题全部修复）
- [ ] Git 已提交 (待用户确认)
