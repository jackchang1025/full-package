# Vendor 逆向工程优化计划 (v5 — 审查修复版)

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复逆向工程的 5 个核心缺陷 — 恢复 1,214 个 stub 空方法体、重命名 o/ 引擎层、拆分 server/b.java、重命名混淆标识符、消除 goto 残留。

**Architecture:** 5 个优先级递进 — P0 恢复 stub 方法体（从 CFR 原始输出）→ P1a o/ 引擎层重命名 → P1b server/b.java 路由拆分 → P2 混淆标识符重命名 → P3 goto 消除。每步完成后编译验证。

**Tech Stack:** CFR 0.152 原始输出 (`/tmp/cfr-full/`), Gradle 8.5, Java 17, sed/awk

**前置条件:**
- `androidReverseEngineering/` 当前 889 文件, BUILD SUCCESSFUL
- `/tmp/cfr-full/` CFR 原始反编译输出仍存在（已确认）
- 1,214 个 stub 方法分布在 ~200 个文件中

---

## 审查发现的 5 个缺陷

| 优先级 | 缺陷 | 数量 | 影响 |
|--------|------|------|------|
| **P0** | stub 空方法体 | 1,214 个方法 | 代码能编译但无实际逻辑，不能二次开发 |
| **P1a** | o/ 文件名仍为单字母 | 36 个文件 | 无法辨识厂商引擎 |
| **P1b** | server/b.java 未拆分 | 4,591 行单文件 | 不可维护 |
| **P2** | 混淆标识符残留 | 570 方法 + 79 字段 | 代码不可读 |
| **P3** | goto 残留 | 103 个 (3 文件) | 不影响编译但影响可读性 |

---

## Task 1: P0 — 恢复 stub 方法体（1,214 个方法, ~200 文件）

**Why:** 这是最严重的问题。1,214 个方法被替换为 `return null/0/false`，丢失了全部业务逻辑。CFR 原始输出 `/tmp/cfr-full/` 有完整方法体，需要恢复。

**策略:** 不能简单用 CFR 原始文件覆盖（因为当前文件已有注释合并+编译修复）。需要**逐方法**从 CFR 原始输出提取方法体，替换当前 stub。

**分三批处理:**

### Batch 1: 核心业务文件（6 文件, 445 stub）

| 文件 | stub 数 | 重要性 |
|------|---------|--------|
| server/b.java | 160 | 命令路由核心 |
| entity/UiObject.java | 124 | UI 节点操作 |
| o/a0.java | 59 | ADB 配对引擎 |
| utils/g.java | 54 | 工具方法 |
| MyAccessibilityService.java | 24 | 无障碍服务 |
| a1/q.java | 24 | 基础设施 |

### Batch 2: 中等文件（~30 文件, ~350 stub）

stub 数 5-22 的文件：o/t, o/e, BuildConfig, utils/h, h/e, AccessibilityDelegateManager, Gson, http/l, o/g0, UiObjectCollection, n1/b, a/a, k/a, JsonReader, LinkedTreeMap, a1/e 等。

### Batch 3: 长尾文件（~160 文件, ~420 stub）

stub 数 1-4 的文件。大部分是单个方法 stub。

- [ ] **Step 1: 编写恢复脚本**

对每个有 stub 的文件:
1. 从 CFR 原始输出读取对应文件
2. 提取每个方法的完整方法体
3. 在当前文件中找到对应的 stub 方法
4. 用 CFR 原始方法体替换 stub
5. 保留已有的 @reverse/@field/@route 注释

```bash
# 伪代码
for each stub_file in current src/:
    cfr_file = /tmp/cfr-full/ + same_path
    for each stub_method in stub_file:
        cfr_method_body = extract_method_body(cfr_file, method_signature)
        replace_stub_with(stub_file, method_signature, cfr_method_body)
```

由于方法体提取需要理解 Java 语法（花括号匹配），建议用 Python 脚本或逐文件手动处理。

- [ ] **Step 2: 恢复 Batch 1（6 文件, 445 stub）**

对 server/b.java, UiObject.java, o/a0.java, utils/g.java, MyAccessibilityService.java, a1/q.java 逐一:
1. 读取 CFR 原始文件
2. 读取当前文件
3. 对每个 stub 方法，从 CFR 提取方法体替换

- [ ] **Step 3: 编译验证 Batch 1**

```bash
./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

如果有编译错误（CFR 原始方法体可能有类型问题），逐个修复。

- [ ] **Step 4: 恢复 Batch 2（~30 文件, ~350 stub）**

同上策略。

- [ ] **Step 5: 编译验证 Batch 2**

- [ ] **Step 6: 恢复 Batch 3（~160 文件, ~420 stub）**

长尾文件大部分只有 1-2 个 stub，可以批量处理。

- [ ] **Step 7: 最终编译验证**

```bash
./gradlew clean compileJava 2>&1 | tail -3
echo $?
# 目标: 0
```

- [ ] **Step 8: 验证 stub 残留**

```bash
grep -r 'CFR decompile incomplete\|CFR_STUB\|decompile incomplete' src/ --include='*.java' | wc -l
# 目标: 0（或尽可能接近 0）
```

---

## Task 2: P1a — o/ 引擎层按厂商重命名（36 文件）

**Why:** 36 个文件全是 a.java, b.java... 单字母名，无法辨识功能。

**已识别映射（来自 package-info.java 和之前分析）:**

| 原文件 | 重命名 | 依据 |
|--------|--------|------|
| o/a0.java | PairDelegate.java | Log "PairAccessibilityDelegate" |
| o/k.java | EnableSecureDelegate.java | USB调试自动化 |
| o/n.java | HuaweiStartupEngine.java | HUA_WEI_* keys |
| o/h.java | HuaweiEngine.java | 华为关键词 |
| o/q.java | XiaomiEngine.java | xiaomi/miui |
| o/v.java | OppoEngine.java | COLORS_* keys |
| o/i0.java | VivoEngine.java | vivo/funtouch |
| o/e0.java | TranssionEngine.java | transsion/tecno |
| o/g.java | AospEngine.java | 无厂商关键词 |
| o/c.java | CoreEngine.java | 被 14 文件引用 |
| o/e.java | EngineCoordinator.java | 被 19 文件引用 |

剩余 25 个文件需要读取 CFR 输出推断功能后命名。

- [ ] **Step 1: 对已确认的 11 个文件执行重命名**

对每个文件执行 4 步:
```bash
# 示例: o/e.java → o/EngineCoordinator.java
mv src/o/e.java src/o/EngineCoordinator.java
sed -i 's/^public final class e/public final class EngineCoordinator/' src/o/EngineCoordinator.java
find src/ -name '*.java' -exec sed -i 's/\bimport o\.e;/import o.EngineCoordinator;/g' {} +
find src/ -name '*.java' -exec sed -i 's/\bo\.e\b/o.EngineCoordinator/g' {} +
```

**按引用数从高到低重命名**（减少中间编译错误）:
1. o/e.java (19 引用) → EngineCoordinator
2. o/c.java (14 引用) → CoreEngine
3. o/a0.java (9 引用) → PairDelegate
4. 其余按序

- [ ] **Step 2: 分析并重命名剩余 25 个文件**

读取每个文件的 CFR 输出，通过 Log 标签/字符串常量/import 推断功能。

- [ ] **Step 3: 编译验证**

```bash
./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

- [ ] **Step 4: 验证无单字母文件名残留**

```bash
ls src/o/*.java | grep -cP '/[a-z]\.java$|/[a-z]0\.java$'
# 目标: 0
```

---

## Task 3: P1b — server/b.java 路由拆分（4,591 行 → 多个 Handler）

**Why:** 4,591 行单文件不可维护。24 条真实路由提供天然拆分点。

**24 条真实路由:**
```
/, /version, /deviceId, /containerState, /isDeviceOwner, /noticeAlive,
/openADBDebug, /closeADBDebug, /openWifiDebug, /closeWifiDebug, /resetWifiDebug,
/openDevelopment, /closeDevelopment, /rewriteDebugPort, /shareADBConfig, /syncADBConfig,
/api/pairKeyFile/query.json, /syncAdminActivating, /syncPowerControl, /syncLockCipher,
/resetAccessibilityService, /listenHelper, /finishListenHelper, /blockView
```

**路由分发机制:** `string.equals()` 链（非 switch/case），两个入口:
- `X1(String, String, k)` — POST 路由 (行 ~3461)
- `e1(String, i0.e, k)` — GET 路由 (行 ~8880)

- [ ] **Step 1: 读取 b.java 定位路由分发入口和方法映射**

```bash
grep -n '.equals("/' src/com/guard/wallet/server/b.java | head -30
```

建立完整的 路由→方法名 映射表。

- [ ] **Step 2: 按功能域创建 Handler 类**

```bash
mkdir -p src/com/guard/wallet/server/handler
```

| Handler | 路由数 | 职责 |
|---------|--------|------|
| AdbCommandHandler.java | 11 | ADB/开发者选项 |
| DeviceStatusHandler.java | 6 | 设备状态查询 |
| PermissionHandler.java | 3 | 权限管理 |
| AccessibilityHandler.java | 4 | 无障碍+UI |

- [ ] **Step 3: 将方法从 b.java 移到对应 Handler**

保持方法签名不变（public static），b.java 中改为委托调用。

- [ ] **Step 4: 编译验证**

```bash
./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

---

## Task 4: P2 — 混淆标识符重命名（570 方法 + 79 字段）

**分布:**
- com/guard/wallet/: 346 个单字母方法
- a1/: 143 个单字母方法
- o/: 81 个单字母方法
- 79 个唯一 `f数字` 混淆字段

**策略:** 按文件重要性和调用频率排序，优先重命名高频方法。

- [ ] **Step 1: 统计每个单字母方法的全局调用次数**

```bash
# 找出所有单字母方法定义及其调用频率
grep -rP '(?:public|private|protected)\s+(?:static\s+)?(?:final\s+)?\w+\s+([a-z])\s*\(' src/com/guard/wallet/ src/o/ src/a1/ --include='*.java' -oh | sort | uniq -c | sort -rn | head -30
```

- [ ] **Step 2: 对 a1/ 包剩余方法重命名**

a1/q.java 已完成，但 a1/ 下还有 22 个文件 143 个方法。逐文件读取推断功能后重命名。

- [ ] **Step 3: 对 o/ 包方法重命名**

81 个方法，结合 Task 2 的厂商识别一起处理。

- [ ] **Step 4: 对 com/guard/wallet/ 核心文件方法重命名**

优先处理:
- utils/g.java (54 方法 — 已有 Log 标签分组)
- utils/h.java (19 方法)
- http/ 目录 (多个回调类)
- helper/ 目录

- [ ] **Step 5: 重命名 79 个混淆字段**

对 `f\d+[a-z]` 模式字段，基于使用上下文推断语义后重命名。

- [ ] **Step 6: 编译验证**

```bash
./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

---

## Task 5: P3 — goto 消除（103 个, 3 文件）

**分布:**
- o/a0.java: 64 goto
- a1/q.java: 36 goto
- server/b.java: 3 goto

**策略:** 这些 goto 在 CFR 注释块中（`** GOTO lbl`），不影响编译但影响可读性。

- [ ] **Step 1: 检查 goto 是否在注释块还是可执行代码中**

```bash
for f in src/o/a0.java src/a1/q.java src/com/guard/wallet/server/b.java; do
    echo "=== $f ==="
    grep -n 'goto ' "$f" | head -10
done
```

- [ ] **Step 2: 如果在注释块中，确保注释正确闭合**

- [ ] **Step 3: 如果在可执行代码中，用 if-else 重构**

- [ ] **Step 4: 编译验证**

---

## Task 6: 最终验证 + 文档更新

- [ ] **Step 1: 完整编译**

```bash
./gradlew clean compileJava 2>&1 | tail -3
echo $?
```

- [ ] **Step 2: 质量统计**

```bash
echo "文件数: $(find src/ -name '*.java' | wc -l)"
echo "代码行: $(find src/ -name '*.java' -exec cat {} + | wc -l)"
echo "stub 残留: $(grep -r 'CFR decompile incomplete' src/ --include='*.java' | wc -l)"
echo "goto 残留: $(grep -r 'goto ' src/ --include='*.java' | grep -v '/\*' | grep -v '//' | wc -l)"
echo "单字母方法: $(grep -rP 'public.*\s+[a-z]\s*\(' src/com/guard/wallet/ src/o/ src/a1/ --include='*.java' | wc -l)"
echo "混淆字段: $(grep -roP '\bf\d+[a-z]\b' src/com/guard/wallet/ src/o/ src/a1/ --include='*.java' | sort -u | wc -l)"
echo "注释: $(grep -r '@reverse:\|@field:\|@route:\|@reverse-library' src/ --include='*.java' | wc -l)"
```

- [ ] **Step 3: 更新 README.md**

- [ ] **Step 4: 等待用户确认后提交**

---

## 验证标准

| 阶段 | 命令 | 预期 |
|------|------|------|
| Task 1 | `grep -r 'CFR decompile incomplete' src/ \| wc -l` | 0 |
| Task 2 | `ls src/o/*.java \| grep -cP '/[a-z]\.java$'` | 0 |
| Task 3 | `wc -l src/.../server/b.java` | < 2000 |
| Task 4 | 单字母方法数 | < 100 |
| Task 5 | `grep goto 非注释行` | 0 |
| Task 6 | `./gradlew clean compileJava && echo $?` | 0 |
