# 交付验收标准

> 阶段通过验收的前提条件：以下所有标准全部满足。

## 单文件验收

| # | 标准 | 验证方式 |
|---|------|---------|
| 1 | 已完整阅读 JADX 源码 | 审查者能列出方法数量和字段清单 |
| 2 | 测试文件存在 | `app/src/test/.../ClassNameTest.kt` 已创建 |
| 3 | 测试通过 | `./gradlew test` 退出码为 0 |
| 4 | 构建通过 | `./gradlew compileDebugKotlin` 退出码为 0 |
| 5 | 审查清单完成 | [REVIEW.md](REVIEW.md) 中所有条目已勾选 |
| 6 | 映射已更新 | FILE_MAPPING.md 显示 `done` |

## 阶段验收

| # | 标准 | 验证方式 |
|---|------|---------|
| 1 | 阶段内所有文件完成 | FILE_MAPPING.md：阶段内每一行均为 `done` |
| 2 | 零测试失败 | `./gradlew test` 完整运行, 0 个失败 |
| 3 | 无回归 | 前一阶段的测试仍然通过 |
| 4 | 审计文档 | `docs/audits/AUDIT_PHASE_N.md` 已编写并包含审计结论 |
| 5 | Git 已提交 | 单次或逻辑性提交，使用 conventional 格式 |

## 审计文档要求

每完成一个阶段后，使用 [AUDIT_TEMPLATE.md](AUDIT_TEMPLATE.md) 生成 `docs/audits/AUDIT_PHASE_N.md`。必须包含：

1. **厂商差异摘要** — JADX 与复刻代码的方法/字段对比
2. **ADAPT 标注** — 列出所有 `// ADAPT` 及其理由
3. **VENDOR_VERIFY 条目** — 列出所有 `// TODO: VENDOR_VERIFY`
4. **测试统计** — 测试方法数量、断言数量
5. **已知缺口** — 推迟到真机测试的内容

## 最终项目验收（Phase 10）

| # | 标准 |
|---|------|
| 1 | FILE_MAPPING.md 中全部 143 个文件状态为 `done` |
| 2 | `./gradlew test` 通过（目标: 80%+ 行覆盖率） |
| 3 | `./gradlew assembleDebug` 成功生成 APK |
| 4 | 全部 10 份审计文档已编写 |
| 5 | 无剩余 `// TODO: VENDOR_VERIFY`（或在最终审计文档中记录） |
