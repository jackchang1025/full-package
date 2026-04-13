# 代码审查清单

> 在 FILE_MAPPING.md 中将文件标记为 `done` 之前，对每个复刻文件逐项检查。

## 1. 源码保真度

- [ ] 已完整阅读 JADX 源码（非浏览）
- [ ] 所有 public/internal 方法已复刻
- [ ] 所有字段/属性存在且类型正确
- [ ] 所有 companion object 常量与厂商值一致
- [ ] 内部类/密封类/枚举均已包含
- [ ] 未添加 JADX 源码中不存在的方法
- [ ] 未删除 JADX 源码中存在的方法

## 2. 偏差标注

- [ ] 每个有意偏差均标注 `// ADAPT: <原因>`
- [ ] 每个反编译不明确处均标注 `// TODO: VENDOR_VERIFY — <描述>`
- [ ] 无静默修改（重命名参数、调整逻辑顺序、"简化"流程）

## 3. 测试覆盖

- [ ] 存在 `*Test.kt` 文件，类名为源类名 + `Test` 后缀
- [ ] 测试覆盖所有 public 方法
- [ ] 测试覆盖错误路径（异常、null 返回）
- [ ] 测试验证常量值与 JADX 源码一致
- [ ] `./gradlew test` 通过，零失败

## 4. 构建完整性

- [ ] `./gradlew compileDebugKotlin` 成功
- [ ] 未引入新的编译警告
- [ ] 无未解析的导入

## 5. 架构对齐

- [ ] 包路径与 JADX 包结构一致（`com.storm.safe.rock.service.modules.setup/`）
- [ ] 类继承关系一致（extends/implements 相同基类）
- [ ] 构造函数签名与厂商一致
- [ ] 线程安全模式保留（锁、原子变量、并发集合）

## 6. 文件映射

- [ ] `FILE_MAPPING.md` 已更新：状态设为 `done`
- [ ] Git 提交信息格式: `feat(<模块>): implement <类名> (TDD)`

## 快速拒绝标准

如果以下任一条件为真，则文件**审查不通过**：

1. 无对应的测试文件
2. `./gradlew test` 存在失败
3. 未阅读 JADX 源码（无法证明与厂商签名匹配）
4. public 方法数量与 JADX 不一致且缺少 `// ADAPT` 标注
5. 硬编码值与 JADX 常量不一致且无说明
