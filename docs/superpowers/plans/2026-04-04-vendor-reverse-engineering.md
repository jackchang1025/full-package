# Vendor 反编译代码逆向工程实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 327 个 vendor 反编译 Java 文件通过逆向工程转化为可读、可编译、可二次开发的代码，输出到 `androidReverseEngineering/` 目录。

**Architecture:** 分 4 个 Phase 按等级递进执行 — Phase 1 直接复制 A 级文件并重组包结构 → Phase 2 对 B 级文件执行轻度逆向（重命名+goto 消除）→ Phase 3 对 C 级文件深度逆向 → Phase 4 对 D 级文件逐方法重构。每个 Phase 产出可编译的增量代码。

**Tech Stack:** Java 8+, JADX (反编译), IntelliJ IDEA (重构), bash (批量处理)

**Spec:** `docs/superpowers/specs/2026-04-02-vendor-code-readability-assessment-design.md`
**评估报告:** `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
**指标 CSV:** `docs/vendor-reverse/vendor-code-metrics.csv`

**Vendor 源码基路径:** `app/storage/app/apk/apkstub/decompiled_vendor/sources/`
**输出目录:** `androidReverseEngineering/`

---

## File Structure

逆向后的代码保留 vendor 原始包结构，放在 `androidReverseEngineering/src/` 下：

```
androidReverseEngineering/
├── src/
│   ├── a1/
│   │   └── q.java                          # 基础设施工具类（外部依赖，优先逆向）
│   ├── com/guard/wallet/
│   │   ├── MainApplication.java            # 应用入口
│   │   ├── MyApp.java                      # App 类
│   │   ├── LockActivity.java               # 锁屏 Activity
│   │   ├── activity/                        # 4 文件
│   │   ├── bridge/                          # 1 文件
│   │   ├── condition/                       # 8 文件
│   │   ├── entity/                          # 24 文件
│   │   ├── filter/                          # 39 文件
│   │   ├── helper/                          # 18 文件
│   │   ├── http/                            # 34 文件
│   │   ├── msg/                             # 9 文件
│   │   ├── plug/                            # 6 文件
│   │   ├── receiver/                        # 12 文件
│   │   ├── req/                             # 55 文件
│   │   ├── resp/                            # 42 文件
│   │   ├── server/                          # 3 文件
│   │   ├── service/                         # 7 文件
│   │   ├── stat/                            # 3 文件
│   │   ├── sync/                            # 2 文件
│   │   ├── thread/                          # 13 文件
│   │   └── utils/                           # 11 文件
│   └── o/                                   # 33 文件（厂商引擎层）
├── docs/
│   └── REVERSE_CHANGELOG.md                 # 逆向变更日志
└── README.md                                # 项目说明
```

---

### Task 1: 项目初始化 + a1/q.java 基础设施逆向

**Why first:** `a1/q.java` 被全库 70% 文件引用（301 次），包含 58 个静态工具方法（空值检查、AES 加密、日志、文件 IO）。逆向此文件是解锁全库语义的最高性价比投入。

**Files:**
- Create: `androidReverseEngineering/README.md`
- Create: `androidReverseEngineering/docs/REVERSE_CHANGELOG.md`
- Create: `androidReverseEngineering/src/a1/q.java`

- [ ] **Step 1: 创建项目目录结构**

```bash
cd /home/code/php/project/full-package
mkdir -p androidReverseEngineering/{src/a1,src/com/guard/wallet,src/o,docs}
```

- [ ] **Step 2: 创建 README.md**

创建 `androidReverseEngineering/README.md`：

```markdown
# Vendor 反编译代码逆向工程

本目录包含从 vendor APK 反编译后经逆向工程处理的 Java 源码。

## 来源
- 原始反编译代码: `app/storage/app/apk/apkstub/decompiled_vendor/sources/`
- 评估报告: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
- 指标 CSV: `docs/vendor-reverse/vendor-code-metrics.csv`

## 逆向状态

| Phase | 等级 | 文件数 | 行数 | 状态 |
|-------|------|--------|------|------|
| Phase 1 | A 级 | 199 | 14,147 | 待处理 |
| Phase 2 | B 级 | 116 | 21,973 | 待处理 |
| Phase 3 | C 级 | 3 | 856 | 待处理 |
| Phase 4 | D 级 | 9 | 20,682 | 待处理 |
| 外部依赖 | a1/q.java | 1 | 1,134 | 待处理 |

## 包结构
- `src/a1/` — 基础设施工具类（优先逆向）
- `src/com/guard/wallet/` — 主业务代码（294 文件）
- `src/o/` — 厂商引擎层（33 文件，全混淆）

## 逆向规范
- 保留原始包结构和类名
- goto 语句用 if-else/while/for 重构
- 混淆字段添加语义注释 `/* renamed: 原名 -> 新名, 依据: ... */`
- JADX WARN 区域标注 `/* RECONSTRUCTED: 原因 */`
```

- [ ] **Step 3: 创建变更日志**

创建 `androidReverseEngineering/docs/REVERSE_CHANGELOG.md`：

```markdown
# 逆向变更日志

## 格式
每次逆向操作记录: 日期 | 文件 | 操作类型 | 说明

## 操作类型
- COPY: 直接复制（A 级无需修改）
- RENAME: 变量/方法/字段重命名
- GOTO_FIX: goto 消除（重构控制流）
- RECONSTRUCT: 方法逻辑重构（JADX 失败区域）
- ANNOTATE: 添加语义注释
```

- [ ] **Step 4: 复制 a1/q.java 并开始逆向**

```bash
cp app/storage/app/apk/apkstub/decompiled_vendor/sources/a1/q.java androidReverseEngineering/src/a1/q.java
```

读取 `androidReverseEngineering/src/a1/q.java`，对 58 个静态方法逐一添加语义注释：

已知方法功能映射（来自校准阶段分析）：
- `B()` → `isNullOrEmpty()` — 空值检查，全库调用频率最高
- `m()` → `decryptAES()` — AES/ECB/PKCS5Padding 解密，硬编码密钥 `****1qaz2wsx****`
- `s()` → `logError()` — 错误日志
- `t()` → `logException()` — 异常日志
- `u()` → `execShell()` — Runtime.exec() shell 命令执行
- `y()` → `createSSLContext()` — TLS SSLContext 构建

对每个方法添加 `/* @reverse: 原名=X, 功能=Y, 依据=Z */` 注释。
不修改方法名（保持与其他文件的引用兼容），只添加注释。

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/
git commit -m "feat: 初始化逆向工程项目 + a1/q.java 基础设施注解"
```

---

### Task 2: Phase 1 — A 级文件批量复制（199 文件, 14,147 行）

**Why:** A 级文件已可读，无需逆向，直接复制即可。占总文件数 60.9%。

**Files:**
- Create: `androidReverseEngineering/src/com/guard/wallet/` 下 197 个文件
- Create: `androidReverseEngineering/src/o/` 下 2 个文件（h.java, n.java）

A 级文件分布：
- filter/ (39), req/ (51), resp/ (39), entity/ (20), http/ (12), msg/ (9)
- condition/ (6), receiver/ (5), stat/ (3), utils/ (4), activity/ (2)
- helper/ (2), service/ (1), server/ (1), sync/ (1), wallet-root (2)
- o/ (2: h.java, n.java)

- [ ] **Step 1: 编写批量复制脚本**

```bash
#!/usr/bin/env bash
set -euo pipefail

VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
CSV="docs/vendor-reverse/vendor-code-metrics.csv"
DEST="androidReverseEngineering/src"

# 从 CSV 提取 A 级文件并复制
tail -n +2 "$CSV" | awk -F, '$23=="A"{print $1}' | while IFS= read -r relpath; do
    src="$VENDOR/$relpath"
    dst="$DEST/$relpath"
    mkdir -p "$(dirname "$dst")"
    cp "$src" "$dst"
done

echo "A 级文件复制完成"
find "$DEST" -name '*.java' | wc -l
```

- [ ] **Step 2: 运行批量复制**

```bash
bash scripts/copy-a-grade.sh
```

验证: `find androidReverseEngineering/src -name '*.java' | wc -l` 应为 200（199 A 级 + 1 a1/q.java）

- [ ] **Step 3: 验证 A 级文件中有混淆依赖的 4 个文件**

从评估报告已知 4 个 A 级文件有混淆依赖（obf_dep_count > 0）：
- `MainApplication.java`
- `receiver/CustomAdminReceiver.java`
- `o/h.java`
- `o/n.java`

对这 4 个文件添加头部注释标注其混淆依赖：

```java
/* @reverse: A 级文件，代码已可读
 * @warning: 依赖混淆类 o.X（需后续 Phase 2 完成后更新引用）
 */
```

- [ ] **Step 4: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 1 完成 — 199 个 A 级文件直接复制"
```

---

### Task 3: Phase 2 — B 级文件批量复制 + 重命名准备（116 文件, 21,973 行）

**Files:**
- Create: `androidReverseEngineering/src/` 下 116 个 B 级文件

B 级文件分布：
- o/ (27), http/ (22), helper/ (16), thread/ (11)
- plug/ (6), receiver/ (6), service/ (5), req/ (4)
- entity/ (3), resp/ (3), activity/ (2), utils/ (6)
- bridge/ (1), condition/ (1), server/ (1), sync/ (1), wallet-root/ (1)

- [ ] **Step 1: 批量复制 B 级文件**

```bash
VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
CSV="docs/vendor-reverse/vendor-code-metrics.csv"
DEST="androidReverseEngineering/src"

tail -n +2 "$CSV" | awk -F, '$23=="B"{print $1}' | while IFS= read -r relpath; do
    src="$VENDOR/$relpath"
    dst="$DEST/$relpath"
    mkdir -p "$(dirname "$dst")"
    cp "$src" "$dst"
done

echo "B 级文件复制完成"
find "$DEST" -name '*.java' | wc -l
```

验证: 应为 316（199 A + 116 B + 1 a1/q.java）

- [ ] **Step 2: 为 B 级文件添加逆向状态头注释**

```bash
# 为每个 B 级文件添加标识头
tail -n +2 "$CSV" | awk -F, '$23=="B"{print $1}' | while IFS= read -r relpath; do
    dst="androidReverseEngineering/src/$relpath"
    # 在文件开头插入注释（保留 package 声明在注释之后）
    sed -i '1i\/* @reverse-status: B 级 - 轻度逆向待处理 */' "$dst"
done
```

- [ ] **Step 3: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 2 准备 — 116 个 B 级文件复制 + 状态标注"
```

---

### Task 4: Phase 2a — B 级无 goto 文件批量重命名（99 文件）

**Why:** 116 个 B 级文件中，99 个没有 goto（只需要重命名混淆标识符），17 个有 goto（需要额外处理）。先处理简单的 99 个。

**Files:**
- Modify: `androidReverseEngineering/src/` 下 99 个 B 级无 goto 文件

- [ ] **Step 1: 识别 B 级无 goto 文件清单**

```bash
CSV="docs/vendor-reverse/vendor-code-metrics.csv"
tail -n +2 "$CSV" | awk -F, '$23=="B" && $3==0{print $1}' | wc -l
# 应为 99 个
tail -n +2 "$CSV" | awk -F, '$23=="B" && $3==0{print $1}' > /tmp/b-no-goto.txt
```

- [ ] **Step 2: 对每个文件执行自动重命名注解**

对每个无 goto 的 B 级文件：
1. 识别 JADX 的 `/* renamed from: X */` 注释，提取原始名→混淆名映射
2. 对 ProGuard 编号字段（`f\d+[a-z]`）添加语义推断注释
3. 更新文件头的 `@reverse-status` 为 `B 级 - 已重命名注解`

```bash
DEST="androidReverseEngineering/src"
while IFS= read -r relpath; do
    filepath="$DEST/$relpath"
    # 统计该文件中的 renamed 注释数
    renamed_count=$(grep -c 'renamed from' "$filepath" 2>/dev/null || echo 0)
    # 更新状态头
    sed -i 's/@reverse-status: B 级 - 轻度逆向待处理/@reverse-status: B 级 - 已重命名注解 (无goto)/' "$filepath"
done < /tmp/b-no-goto.txt
```

**注意:** 实际的语义推断（将 `f198a` 重命名为有意义的名字）需要逐文件人工分析，此步骤只做标注。后续 Task 由子代理逐目录深入处理。

- [ ] **Step 3: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 2a — 99 个 B 级无 goto 文件重命名注解"
```

---

### Task 5: Phase 2b — B 级 goto 文件控制流重构（17 文件, 165 goto）

**Why:** 17 个 B 级文件包含 goto 语句，需要将 goto 重构为合法 Java 控制流。

**Files:**
- Modify: 17 个 B 级含 goto 文件

17 个文件清单（按 goto 数量降序）:
1. `http/v.java` (123 行, 18 goto)
2. `receiver/ShutDownBroadcastReceiver.java` (89 行, 17 goto)
3. `receiver/PackageReceiver.java` (117 行, 17 goto)
4. `receiver/BootBroadcast.java` (103 行, 17 goto)
5. `utils/g.java` (3142 行, 14 goto)
6. `o/g0.java` (432 行, 12 goto)
7. `receiver/CallReceiver.java` (88 行, 12 goto)
8. `utils/k.java` (101 行, 11 goto)
9. `o/r.java` (69 行, 9 goto)
10. `o/x.java` (531 行, 8 goto)
11. `thread/l.java` (108 行, 8 goto)
12. `http/i.java` (293 行, 7 goto)
13. `service/MediaLiveService.java` (112 行, 6 goto)
14. `o/i.java` (266 行, 5 goto)
15. `thread/b.java` (208 行, 2 goto)
16. `o/f.java` (31 行, 1 goto)
17. `plug/f.java` (32 行, 1 goto)

- [ ] **Step 1: 先处理小文件（goto ≤ 5, 共 5 个文件）**

对以下 5 个小文件手动重构 goto：
- `thread/b.java` (2 goto)
- `o/f.java` (1 goto)
- `plug/f.java` (1 goto)
- `o/i.java` (5 goto)
- `service/MediaLiveService.java` (6 goto)

逐文件读取 → 找到 goto 所在方法 → 用 if-else/while/break 替代 → 标注 `/* GOTO_FIX: 重构自 goto Lxx */`

- [ ] **Step 2: 处理中等文件（goto 6-12, 共 7 个文件）**

- `http/i.java` (7 goto)
- `thread/l.java` (8 goto)
- `o/x.java` (8 goto)
- `o/r.java` (9 goto)
- `utils/k.java` (11 goto)
- `receiver/CallReceiver.java` (12 goto)
- `o/g0.java` (12 goto)

同上逆向策略。

- [ ] **Step 3: 处理大文件（goto > 12, 共 5 个文件）**

- `utils/g.java` (3142 行, 14 goto) — 仅重构含 goto 的方法，其余 115+ 个方法不动
- `http/v.java` (18 goto)
- `receiver/ShutDownBroadcastReceiver.java` (17 goto)
- `receiver/PackageReceiver.java` (17 goto)
- `receiver/BootBroadcast.java` (17 goto)

每个文件逐方法处理，标注重构区域。

- [ ] **Step 4: 验证无残留 goto**

```bash
grep -r 'goto ' androidReverseEngineering/src/ --include='*.java' | grep -v '/\*' | grep -v '//' | wc -l
# 应为 0（所有 goto 已重构或在 C/D 级文件中尚未处理）
```

注意: 此时只验证 A+B 级文件。C/D 级文件尚未复制。

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 2b — 17 个 B 级文件 goto 消除（165 goto → 0）"
```

---

### Task 6: Phase 3 — C 级文件深度逆向（3 文件, 856 行）

**Files:**
- Create + Modify: 3 个 C 级文件

3 个 C 级文件：
1. `thread/e.java` (147 行, 12 goto, 3 warn)
2. `thread/i.java` (327 行, 14 goto, 6 warn)
3. `o/k.java` (382 行, 19 goto, 22 warn)

- [ ] **Step 1: 复制 C 级文件**

```bash
VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
for f in "com/guard/wallet/thread/e.java" "com/guard/wallet/thread/i.java" "o/k.java"; do
    mkdir -p "androidReverseEngineering/src/$(dirname "$f")"
    cp "$VENDOR/$f" "androidReverseEngineering/src/$f"
    sed -i '1i\/* @reverse-status: C 级 - 深度逆向 */' "androidReverseEngineering/src/$f"
done
```

- [ ] **Step 2: 逆向 thread/e.java (147 行, 12 goto)**

读取文件，逐方法分析：
- 识别日志标签和字符串常量
- 重构 12 个 goto 为合法控制流
- 重命名混淆字段（obf_field_ratio=100%）
- 标注 JADX WARN 区域

- [ ] **Step 3: 逆向 thread/i.java (327 行, 14 goto)**

同上策略：
- 14 个 goto 逐一重构
- 6 个 JADX WARN 区域标注
- 混淆字段语义推断

- [ ] **Step 4: 逆向 o/k.java (382 行, 19 goto, 22 warn)**

最复杂的 C 级文件：
- 19 个 goto + 22 个 JADX WARN
- 有 4 个外部混淆依赖（obf_dep_count=4）
- 需要参考已逆向的 o/ 目录其他文件推断功能

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 3 — 3 个 C 级文件深度逆向（45 goto + 31 warn 处理）"
```

---

### Task 7: Phase 4a — D 级小文件逆向（3 文件, ≤ 258 行）

**Files:**
- Create + Modify: 3 个 D 级小文件

3 个 D 级小文件：
1. `utils/d.java` (123 行, 0 goto, dim1=0) — 纯命名混淆
2. `receiver/PowerBroadcastReceiver.java` (140 行, 33 goto) — goto 密集但功能清晰
3. `condition/TargetActionCondition.java` (258 行, 26 goto, 36 warn) — POJO + equals/hashCode goto

- [ ] **Step 1: 复制 3 个文件**

```bash
VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
for f in "com/guard/wallet/utils/d.java" "com/guard/wallet/receiver/PowerBroadcastReceiver.java" "com/guard/wallet/condition/TargetActionCondition.java"; do
    mkdir -p "androidReverseEngineering/src/$(dirname "$f")"
    cp "$VENDOR/$f" "androidReverseEngineering/src/$f"
    sed -i '1i\/* @reverse-status: D 级 - 逆向中 */' "androidReverseEngineering/src/$f"
done
```

- [ ] **Step 2: 逆向 utils/d.java (123 行, dim1=0)**

这个文件 D 级是因为 dim1=0（9 个单字母方法），但 dim2=10（完美反编译）：
- 读取文件，通过方法体内容推断每个单字母方法的功能
- 添加语义注释（不改方法名，保持引用兼容）
- 已知: 包含常量定义（`f279a=1`, `b=0`, `c=1` 等）

- [ ] **Step 3: 逆向 PowerBroadcastReceiver.java (140 行, 33 goto)**

140 行小文件但 goto 密集（每 4.2 行一个 goto）：
- 只有 1 个方法 `onReceive()` 包含全部 33 个 goto
- 功能明确: 电源广播监听（开关机、省电模式）
- 用 switch-case + if-else 完全重写 onReceive() 方法

- [ ] **Step 4: 逆向 TargetActionCondition.java (258 行, 26 goto)**

字段名完全可读（`actionName`, `actionType`, `delegateId`）：
- goto 集中在 `equals()` 和 `hashCode()` 方法
- 这两个方法可以根据字段列表完全重写
- 其余方法（getter/setter/toString）无 goto

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 4a — 3 个 D 级小文件逆向完成"
```

---

### Task 8: Phase 4b — D 级中型文件逆向（3 文件, o/ 引擎层）

**Files:**
- Create + Modify: 3 个 D 级 o/ 文件

3 个 D 级 o/ 文件：
1. `o/c.java` (801 行, 79 goto, 48 warn) — 辅助引擎
2. `o/e.java` (982 行, 47 goto, 12 warn) — 引擎类
3. `o/a0.java` (2003 行, 126 goto, 49 warn) — PairAccessibilityDelegate

- [ ] **Step 1: 复制 3 个文件**

```bash
VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
for f in "o/c.java" "o/e.java" "o/a0.java"; do
    cp "$VENDOR/$f" "androidReverseEngineering/src/$f"
    sed -i '1i\/* @reverse-status: D 级 - 逆向中 */' "androidReverseEngineering/src/$f"
done
```

- [ ] **Step 2: 逆向 o/c.java (801 行, 79 goto)**

策略: 干净方法直接保留，goto 重灾方法逐个重构
- 28 个方法中 8 个含 goto（20 个干净）
- 先处理 20 个干净方法的重命名注解
- 再逐个重构 8 个 goto 方法
- 48 个 JADX WARN 需逐一检查：对于 `Code restructure failed` 类型，需要手动重建控制流

- [ ] **Step 3: 逆向 o/e.java (982 行, 47 goto)**

- 50 个方法中 4 个含 goto（46 个干净）
- dim1=2（混淆严重），需要大量语义推断
- 有 6 个外部混淆依赖，参考已逆向的 o/ 文件

- [ ] **Step 4: 逆向 o/a0.java (2003 行, 126 goto)**

最困难的文件之一：
- 85 个方法中 8 个含 goto，但这 8 个方法 goto 密度极高（平均 15.75 个/方法）
- Log 标签 "PairAccessibilityDelegate" 明确表示这是 ADB 无线配对自动化引擎
- 16 个 `PAIR_*` 字符串常量提供完整语义线索
- 策略: 干净方法（77 个）直接重命名注解，8 个 goto 方法参考日志和字符串常量手动重构

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 4b — 3 个 D 级 o/ 引擎文件逆向（252 goto 处理）"
```

---

### Task 9: Phase 4c — D 级大文件逆向（3 文件, 核心系统）

**Files:**
- Create + Modify: 3 个 D 级核心文件

3 个文件：
1. `entity/UiObject.java` (3801 行, 25 goto, 4 warn) — UI 节点对象
2. `service/MyAccessibilityService.java` (1402 行, 32 goto, 9 warn) — 无障碍服务
3. `server/b.java` (11172 行, 84 goto, 22 warn) — 命令路由中枢

- [ ] **Step 1: 复制 3 个文件**

```bash
VENDOR="app/storage/app/apk/apkstub/decompiled_vendor/sources"
for f in "com/guard/wallet/entity/UiObject.java" "com/guard/wallet/service/MyAccessibilityService.java" "com/guard/wallet/server/b.java"; do
    mkdir -p "androidReverseEngineering/src/$(dirname "$f")"
    cp "$VENDOR/$f" "androidReverseEngineering/src/$f"
    sed -i '1i\/* @reverse-status: D 级 - 逆向中 */' "androidReverseEngineering/src/$f"
done
```

- [ ] **Step 2: 逆向 entity/UiObject.java (3801 行, 25 goto)**

- dim1=10（命名可读），D 级仅因为 dim2=0（goto）
- 229 个方法中只有 6 个含 goto（97.4% 干净）
- goto 集中在 `findNode` 系列遍历方法
- 策略: 6 个 goto 方法逐个重构，其余 223 个方法直接保留

- [ ] **Step 3: 逆向 service/MyAccessibilityService.java (1402 行, 32 goto)**

- 68 个方法中只有 3 个含 goto（95.6% 干净）
- 类名和大部分字段可读
- 3 个 goto 方法重构 + 9 个 JADX WARN 处理

- [ ] **Step 4: 逆向 server/b.java (11172 行, 84 goto)**

全库最大最复杂的文件。策略:
- 244 个方法中只有 12 个含 goto（95.1% 干净）
- 每个方法对应一个 API 路由字符串，语义完全可辨
- **不全量重构**: 先建立路由→方法映射表，再逐个重构 12 个 goto 方法
- 232 个干净方法添加路由注释: `/* @route: /contacts — 同步联系人 */`

步骤：
1. 提取所有路由字符串: `grep -o '"\/[^"]*"' server/b.java`
2. 建立 `路由 → 方法名` 对照表
3. 为每个方法添加路由注释
4. 重构 12 个含 goto 的方法

- [ ] **Step 5: Commit**

```bash
git add androidReverseEngineering/src/
git commit -m "feat: Phase 4c — 3 个 D 级核心文件逆向（UiObject + Accessibility + CommandRouter）"
```

---

### Task 10: 最终验证 + 文档更新

**Files:**
- Modify: `androidReverseEngineering/README.md`
- Modify: `androidReverseEngineering/docs/REVERSE_CHANGELOG.md`

- [ ] **Step 1: 验证文件完整性**

```bash
echo "=== 文件总数 ==="
find androidReverseEngineering/src -name '*.java' | wc -l
# 应为 328（327 scope 内 + 1 a1/q.java）

echo "=== 残留 goto 统计 ==="
grep -r 'goto ' androidReverseEngineering/src/ --include='*.java' | grep -v '/\*' | grep -v '//' | wc -l

echo "=== @reverse-status 统计 ==="
grep -r '@reverse-status' androidReverseEngineering/src/ --include='*.java' | grep -o 'A 级\|B 级\|C 级\|D 级' | sort | uniq -c
```

- [ ] **Step 2: 更新 README.md 状态表**

将所有 Phase 的状态从"待处理"更新为"已完成"。

- [ ] **Step 3: 生成 REVERSE_CHANGELOG 条目**

汇总所有逆向操作记录到 `docs/REVERSE_CHANGELOG.md`。

- [ ] **Step 4: Final Commit**

```bash
git add androidReverseEngineering/
git commit -m "docs: 逆向工程完成 — 328 文件全部处理，更新状态文档"
```
