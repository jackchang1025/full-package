# Vendor 反编译代码逆向可读性与二次开发评估 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 系统评估 327 个 vendor 反编译 Java 文件的逆向可行性，输出量化评分报告和逆向实施方案。

**Architecture:** 三阶段流水线 — 自动化脚本扫描采集指标 → 人工采样校准评分 → 撰写评估报告。脚本输出 CSV，报告基于 CSV 数据生成。

**Tech Stack:** Bash (扫描脚本), grep/awk (指标采集), Markdown (报告)

**Spec:** `docs/superpowers/specs/2026-04-02-vendor-code-readability-assessment-design.md`

**Vendor 源码基路径:** `app/storage/app/apk/apkstub/decompiled_vendor/sources/`

---

## File Structure

| 文件 | 职责 |
|------|------|
| Create: `scripts/vendor-code-scan.sh` | 自动化扫描脚本，采集 9 项指标并计算 5 维度评分 |
| Create: `docs/vendor-reverse/vendor-code-metrics.csv` | 脚本输出的逐文件指标和评分数据 |
| Create: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md` | 最终评估报告（6 章） |

---

### Task 1: 编写自动化扫描脚本

**Files:**
- Create: `scripts/vendor-code-scan.sh`

- [ ] **Step 1: 创建 scripts 目录和脚本文件骨架**

```bash
mkdir -p scripts
```

创建 `scripts/vendor-code-scan.sh`，包含：
- shebang + set -euo pipefail
- VENDOR_BASE 路径变量
- CSV 输出路径变量
- CSV header 行写入
- find 命令遍历所有 327 个 .java 文件的主循环骨架

```bash
#!/usr/bin/env bash
set -euo pipefail

VENDOR_BASE="app/storage/app/apk/apkstub/decompiled_vendor/sources"
WALLET_DIR="$VENDOR_BASE/com/guard/wallet"
O_DIR="$VENDOR_BASE/o"
OUTPUT_CSV="docs/vendor-reverse/vendor-code-metrics.csv"

mkdir -p "$(dirname "$OUTPUT_CSV")"

# 必须从项目根目录运行
[[ -d "$VENDOR_BASE" ]] || { echo "ERROR: 请从项目根目录 (full-package/) 运行此脚本"; exit 1; }

# CSV header
echo "file_path,lines,goto_count,jadx_warn_count,obf_field_count,total_field_count,single_letter_method_count,total_method_count,obf_field_ratio,single_letter_method_ratio,combined_obf_ratio,semantic_clue_count,semantic_clue_density,max_nesting,obf_dep_count,switch_cases,dim1_deobf,dim2_decompile,dim3_semantic,dim4_effort,dim5_maintain,total_score,grade" > "$OUTPUT_CSV"

# Collect all Java files
find "$WALLET_DIR" "$O_DIR" -name '*.java' -type f | sort | while IFS= read -r filepath; do
    # (指标采集和评分逻辑在后续步骤实现)
    echo "Processing: $filepath"
done
```

- [ ] **Step 2: 实现 9 项原始指标采集函数**

在脚本主循环内，对每个文件采集：

```bash
    # --- 原始指标采集 ---
    # 1. 文件行数
    lines=$(wc -l < "$filepath")

    # 2. goto 数量
    goto_count=$(grep -c 'goto ' "$filepath" 2>/dev/null || echo 0)

    # 3. JADX WARN/INFO 数量
    # 与 spec 一致: 仅统计 JADX WARN 和 JADX INFO
    jadx_warn_count=$(grep -c -E 'JADX WARN|JADX INFO' "$filepath" 2>/dev/null || echo 0)

    # 4. 混淆字段: ProGuard f\d+[a-z] 和单字母字段
    obf_field_count=$(grep -oP '(?:(?<=\s)|(?<=\.))f\d+[a-z]\b' "$filepath" 2>/dev/null | sort -u | wc -l || echo 0)
    obf_field_count=$((obf_field_count + $(grep -oP '(?:private|public|protected)\s+\S+\s+([a-z])\s*[;=]' "$filepath" 2>/dev/null | wc -l || echo 0)))
    total_field_count=$(grep -cP '(?:private|public|protected)\s+\S+\s+\w+\s*[;=]' "$filepath" 2>/dev/null || echo 0)
    if [ "$total_field_count" -gt 0 ]; then
        obf_field_ratio=$(awk "BEGIN {printf \"%.2f\", $obf_field_count / $total_field_count * 100}")
    else
        obf_field_ratio="0.00"
    fi

    # 5. 单字母方法比例
    single_letter_method_count=$(grep -cP '(?:public|private|protected)\s+(?:static\s+)?(?:final\s+)?\w+\s+[a-z]\s*\(' "$filepath" 2>/dev/null || echo 0)
    total_method_count=$(grep -cP '(?:public|private|protected|static)\s+.*\w+\s*\(' "$filepath" 2>/dev/null || echo 0)
    if [ "$total_method_count" -gt 0 ]; then
        single_letter_method_ratio=$(awk "BEGIN {printf \"%.2f\", $single_letter_method_count / $total_method_count * 100}")
    else
        single_letter_method_ratio="0.00"
    fi

    # 综合混淆比例
    total_identifiers=$((total_field_count + total_method_count))
    total_obf=$((obf_field_count + single_letter_method_count))
    if [ "$total_identifiers" -gt 0 ]; then
        combined_obf_ratio=$(awk "BEGIN {printf \"%.2f\", $total_obf / $total_identifiers * 100}")
    else
        combined_obf_ratio="0.00"
    fi

    # 6. 语义线索密度 (Log.x + 字符串常量，每百行)
    semantic_clue_count=$(grep -cP 'Log\.[dviwes]|"[^"]{3,}"' "$filepath" 2>/dev/null || echo 0)
    if [ "$lines" -gt 0 ]; then
        semantic_clue_density=$(awk "BEGIN {printf \"%.2f\", $semantic_clue_count / $lines * 100}")
    else
        semantic_clue_density="0.00"
    fi

    # 7. 最大嵌套深度 (花括号计数)
    max_nesting=$(awk 'BEGIN{max=0;d=0}{for(i=1;i<=length($0);i++){c=substr($0,i,1);if(c=="{")d++;if(d>max)max=d;if(c=="}")d--}}END{print max}' "$filepath")

    # 8. 外部混淆依赖数 (引用 o.单字母类)
    obf_dep_count=$(grep -oP '\bo\.[a-z][0-9]?\b' "$filepath" 2>/dev/null | sort -u | wc -l || echo 0)

    # 9. switch-case 数量
    switch_cases=$(grep -c 'case ' "$filepath" 2>/dev/null || echo 0)
```

- [ ] **Step 3: 实现 5 维度评分函数**

在指标采集后，按 spec 阈值计算评分：

```bash
    # --- 维度评分 ---

    # dim1: 反混淆难度 (基于 combined_obf_ratio)
    obf_int=$(awk "BEGIN {printf \"%d\", $combined_obf_ratio}")
    if [ "$obf_int" -eq 0 ]; then dim1=10
    elif [ "$obf_int" -le 10 ]; then dim1=8
    elif [ "$obf_int" -le 30 ]; then dim1=6
    elif [ "$obf_int" -le 60 ]; then dim1=4
    elif [ "$obf_int" -le 90 ]; then dim1=2
    else dim1=0; fi

    # dim2: 反编译质量 (基于 goto + JADX WARN)
    if [ "$goto_count" -eq 0 ] && [ "$jadx_warn_count" -eq 0 ]; then dim2=10
    elif [ "$goto_count" -eq 0 ] && [ "$jadx_warn_count" -le 5 ]; then dim2=8
    elif [ "$goto_count" -eq 0 ] && [ "$jadx_warn_count" -le 15 ]; then dim2=6
    elif [ "$goto_count" -le 5 ]; then dim2=4
    elif [ "$goto_count" -le 20 ]; then dim2=2
    else dim2=0; fi

    # dim3: 语义恢复潜力 (基于 semantic_clue_density)
    density_int=$(awk "BEGIN {printf \"%d\", $semantic_clue_density}")
    if [ "$density_int" -ge 10 ]; then dim3=10
    elif [ "$density_int" -ge 5 ]; then dim3=8
    elif [ "$density_int" -ge 3 ]; then dim3=6
    elif [ "$density_int" -ge 1 ]; then dim3=4
    else
        density_frac=$(awk "BEGIN {print ($semantic_clue_density > 0.09) ? 1 : 0}")
        if [ "$density_frac" -eq 1 ]; then dim3=2
        else dim3=0; fi
    fi

    # dim4: 逆向工作量 (基于文件行数)
    if [ "$lines" -le 50 ]; then dim4=10
    elif [ "$lines" -le 150 ]; then dim4=8
    elif [ "$lines" -le 500 ]; then dim4=6
    elif [ "$lines" -le 1500 ]; then dim4=4
    elif [ "$lines" -le 5000 ]; then dim4=2
    else dim4=0; fi

    # dim5: 逆向后可维护性 (基于外部混淆依赖数)
    if [ "$obf_dep_count" -eq 0 ]; then dim5=10
    elif [ "$obf_dep_count" -le 3 ]; then dim5=8
    elif [ "$obf_dep_count" -le 8 ]; then dim5=6
    elif [ "$obf_dep_count" -le 15 ]; then dim5=4
    elif [ "$obf_dep_count" -le 30 ]; then dim5=2
    else dim5=0; fi

    # 加权综合分 (25/25/20/15/15)
    total_score=$(awk "BEGIN {printf \"%.1f\", $dim1*0.25 + $dim2*0.25 + $dim3*0.20 + $dim4*0.15 + $dim5*0.15}")

    # 等级判定 (含单维度下限规则)
    total_int=$(awk "BEGIN {printf \"%d\", $total_score}")
    if [ "$dim1" -le 1 ] || [ "$dim2" -le 1 ]; then
        grade="D"
    elif [ "$total_int" -ge 8 ]; then
        grade="A"
    elif [ "$total_int" -ge 5 ]; then
        grade="B"
    elif [ "$total_int" -ge 2 ]; then
        grade="C"
    else
        grade="D"
    fi

    # 输出相对路径到 CSV（去掉 vendor base prefix）
    rel_path="${filepath#$VENDOR_BASE/}"
    echo "$rel_path,$lines,$goto_count,$jadx_warn_count,$obf_field_count,$total_field_count,$single_letter_method_count,$total_method_count,$obf_field_ratio,$single_letter_method_ratio,$combined_obf_ratio,$semantic_clue_count,$semantic_clue_density,$max_nesting,$obf_dep_count,$switch_cases,$dim1,$dim2,$dim3,$dim4,$dim5,$total_score,$grade" >> "$OUTPUT_CSV"
```

- [ ] **Step 4: 实现统计摘要输出**

在主循环结束后，添加统计摘要：

```bash
# --- 统计摘要 ---
echo ""
echo "========== 扫描完成 =========="
total_files=$(tail -n +2 "$OUTPUT_CSV" | wc -l)
echo "总文件数: $total_files"
echo "总行数: $(tail -n +2 "$OUTPUT_CSV" | awk -F, '{s+=$2}END{print s}')"
echo ""
echo "--- 四级分布 ---"
for g in A B C D; do
    count=$(tail -n +2 "$OUTPUT_CSV" | awk -F, -v grade="$g" '$NF==grade{c++}END{print c+0}')
    lines_sum=$(tail -n +2 "$OUTPUT_CSV" | awk -F, -v grade="$g" '$NF==grade{s+=$2}END{print s+0}')
    pct=$(awk "BEGIN {printf \"%.1f\", $count / $total_files * 100}")
    echo "  $g 级: $count 文件 ($pct%), $lines_sum 行"
done
echo ""
echo "--- 各维度平均分 ---"
tail -n +2 "$OUTPUT_CSV" | awk -F, '{d1+=$17;d2+=$18;d3+=$19;d4+=$20;d5+=$21;t+=$22;n++}END{
    printf "  反混淆难度: %.1f\n", d1/n;
    printf "  反编译质量: %.1f\n", d2/n;
    printf "  语义恢复潜力: %.1f\n", d3/n;
    printf "  逆向工作量: %.1f\n", d4/n;
    printf "  逆向后可维护性: %.1f\n", d5/n;
    printf "  综合分: %.1f\n", t/n
}'
echo ""
echo "CSV 已输出到: $OUTPUT_CSV"
```

- [ ] **Step 5: 运行脚本并验证输出**

```bash
chmod +x scripts/vendor-code-scan.sh
bash scripts/vendor-code-scan.sh
```

验证:
- CSV 文件存在且有 328 行（1 header + 327 数据）
- 统计摘要输出 A/B/C/D 分布
- 抽查 2-3 个已知文件（如 `req/ReqAdbShellVO.java` 应为 A 级，`o/a0.java` 应为 D 级）

- [ ] **Step 6: Commit**

```bash
git add scripts/vendor-code-scan.sh docs/vendor-reverse/vendor-code-metrics.csv
git commit -m "feat: vendor 反编译代码逆向可行性自动扫描脚本及指标 CSV"
```

---

### Task 2: 人工采样校准 — 验证混淆字段正则

**Files:**
- Read: 5 个采样文件的字段声明

- [ ] **Step 1: 从 CSV 中按等级各取代表文件**

```bash
# 查看各等级的代表文件
for g in A B C D; do
    echo "=== $g 级代表 ==="
    grep ",$g$" docs/vendor-reverse/vendor-code-metrics.csv | head -3
done
```

- [ ] **Step 2: 验证混淆字段正则准确性**

从以下 5 个文件人工检查字段声明，对比脚本识别结果：
- 1 个 A 级文件（预期: `req/` 或 `resp/` 下的 VO）
- 1 个 B 级文件
- 1 个 C 级文件
- 1 个 D 级文件（预期: `o/` 下的引擎文件）
- 1 个边界文件（`helper/` 或 `http/` 下）

对每个文件:
- 读取文件前 50 行，检查字段声明格式
- 比对脚本 CSV 中的 `obf_field_count` 和 `total_field_count`
- 确认正则是否遗漏了混淆字段或误匹配了正常字段

- [ ] **Step 3: 如有偏差，调整正则并重新运行**

如果发现正则遗漏模式（如 `this.b` 风格的单字母字段未被计入），更新脚本中的正则并重新运行 `bash scripts/vendor-code-scan.sh`。

- [ ] **Step 4: Commit 校准后的更新**

```bash
git add scripts/vendor-code-scan.sh docs/vendor-reverse/vendor-code-metrics.csv
git commit -m "fix: 校准混淆字段正则，更新指标数据"
```

---

### Task 3: 人工采样校准 — 关键文件深度验证

**Files:**
- Read: `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/server/b.java`
- Read: `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/utils/g.java`
- Read: `app/storage/app/apk/apkstub/decompiled_vendor/sources/o/a0.java`
- Read: `app/storage/app/apk/apkstub/decompiled_vendor/sources/a1/q.java` (外部依赖参考，范围外)

- [ ] **Step 1: 深度分析 server/b.java (11,172 行)**

读取文件（分段），分析：
- 混淆模式（方法名、字段名规律）
- API 路由字符串线索（`"/contacts"`, `"/sms"` 等可读字符串）
- goto 分布（84 个 goto 是否集中在特定方法）
- 评估逆向可行性：能否通过路由字符串反推每个方法的功能

记录：该文件自动评分是否合理，是否需要调整阈值。

- [ ] **Step 2: 深度分析 utils/g.java (3,142 行)**

读取文件，分析：
- 工具方法的混淆程度
- 每个方法是否有日志标签可识别功能
- 对其他文件的影响（被引用频率）

- [ ] **Step 3: 深度分析 o/a0.java (2,003 行, 126 goto)**

读取文件，分析：
- goto 的分布模式（是否由 switch-case 生成）
- JADX WARN 类型分布
- 是否存在可读的日志标签或字符串常量

- [ ] **Step 4: 外部依赖参考分析 a1/q.java (1,134 行)**

读取文件，分析：
- 该文件被 301 次引用的调用方式
- 哪些方法是"工具方法"（加密、日志、设备检测等）
- 逆向这个文件对范围内文件的解锁效果

- [ ] **Step 5: 从 A/B/C/D 各等级再抽 2-3 个文件验证评分**

从 CSV 中按等级抽样，验证自动评分与人工判断的一致性。如有系统性偏差（如 B 级文件实际应为 C 级），调整阈值并重新运行脚本。

- [ ] **Step 6: 如有阈值调整，重新运行脚本并 Commit**

```bash
bash scripts/vendor-code-scan.sh
git add scripts/vendor-code-scan.sh docs/vendor-reverse/vendor-code-metrics.csv
git commit -m "fix: 根据人工校准调整评分阈值"
```

---

### Task 4: 撰写评估报告 — 第 1-2 章

**Files:**
- Create: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
- Read: `docs/vendor-reverse/vendor-code-metrics.csv`

- [ ] **Step 1: 撰写第 1 章「评估概述」**

内容包含：
- 评估范围（327 文件, ~57K 行, 20 个分析单元）
- 方法论简述（五维度评分 + 自动化扫描 + 人工校准）
- 评分体系表格（从 spec 复制维度/权重/含义）
- 四级分类表格
- 单维度下限规则说明

- [ ] **Step 2: 撰写第 2 章「全局统计」**

从 CSV 数据生成：
- 总文件数 / 总行数
- A/B/C/D 四级分布表（文件数、行数、占比）
- 各维度平均分和中位数
- 按目录聚合的热力图（20 行 × 5 列，每格为该目录在该维度的平均分）
- **决策矩阵结论**（A+B 占比 → 逆向可行/部分可行/不经济）

```bash
# 生成统计数据的辅助命令
# 按目录聚合
tail -n +2 docs/vendor-reverse/vendor-code-metrics.csv | awk -F, '{
    split($1, parts, "/");
    # 提取目录名
    if (index($1, "/o/") > 0) dir="o";
    else {
        n = split($1, p, "/");
        dir = p[n-1];
        if (dir == "wallet") dir = "wallet-root";
    }
    count[dir]++;
    d1[dir]+=$17; d2[dir]+=$18; d3[dir]+=$19; d4[dir]+=$20; d5[dir]+=$21;
    total[dir]+=$22;
}END{
    for (d in count) printf "%s: %d files, avg=%.1f\n", d, count[d], total[d]/count[d]
}' | sort
```

- [ ] **Step 3: Commit**

```bash
git add docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md
git commit -m "docs: 评估报告第 1-2 章（概述 + 全局统计）"
```

---

### Task 5: 撰写评估报告 — 第 3 章（分区级评估）

**Files:**
- Modify: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
- Read: `docs/vendor-reverse/vendor-code-metrics.csv`

- [ ] **Step 1: 对 20 个分析单元逐一撰写评估**

每个单元包含：
- 文件数 / 行数 / 平均综合分
- A/B/C/D 分布
- 最佳文件分析（为何逆向容易）
- 最差文件分析（为何逆向困难）
- 逆向策略建议

20 个分析单元：
1. activity/ (4 文件)
2. bridge/ (1 文件)
3. condition/ (8 文件)
4. entity/ (24 文件)
5. filter/ (39 文件)
6. helper/ (18 文件)
7. http/ (34 文件)
8. msg/ (9 文件)
9. plug/ (6 文件)
10. receiver/ (12 文件)
11. req/ (55 文件)
12. resp/ (42 文件)
13. server/ (3 文件)
14. service/ (7 文件)
15. stat/ (3 文件)
16. sync/ (2 文件)
17. thread/ (13 文件)
18. utils/ (11 文件)
19. wallet-root (3 文件: LockActivity, MainApplication, MyApp)
20. o/ (33 文件)

- [ ] **Step 2: Commit**

```bash
git add docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md
git commit -m "docs: 评估报告第 3 章（20 个分区级评估）"
```

---

### Task 6: 撰写评估报告 — 第 4 章（关键文件深度分析）

**Files:**
- Modify: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`

- [ ] **Step 1: 撰写 A 级典型文件分析**

选取 2-3 个 A 级文件，分析为何无需逆向：
- 命名完全语义化
- 无 goto / JADX WARN
- 代码结构清晰
- 示例代码片段

- [ ] **Step 2: 撰写 D 级典型文件分析**

选取 2-3 个 D 级文件（如 `o/a0.java`, `server/b.java`），分析为何逆向不可行：
- goto 密度
- 混淆层级
- 控制流崩溃
- 示例代码片段

- [ ] **Step 3: 撰写混淆模式规律分析**

基于 Task 3 的深度分析，总结：
- ProGuard 命名混淆模式（字段: `f\d+[a-z]`, 方法: 单字母, 类: `o/` 包单字母）
- 控制流混淆模式（goto 替代 break/continue/return）
- JADX 反编译系统性问题（WARN 类型分布、goto 生成原因）

- [ ] **Step 4: 撰写关键外部依赖分析**

基于 Task 3 Step 4 的 `a1/q.java` 分析：
- 被引用 301 次的方法列表
- 逆向该文件对范围内文件的解锁效果
- 建议的逆向优先级

- [ ] **Step 5: Commit**

```bash
git add docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md
git commit -m "docs: 评估报告第 4 章（关键文件深度分析）"
```

---

### Task 7: 撰写评估报告 — 第 5 章（逆向实施方案）

**Files:**
- Modify: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`

- [ ] **Step 1: 撰写 Phase 1 — A 级文件直接使用**

- 列出所有 A 级文件清单（从 CSV 筛选）
- 注意事项（哪些虽然可读但依赖了混淆类）
- 建议的使用方式

- [ ] **Step 2: 撰写 Phase 2 — B 级文件轻度逆向**

- 列出所有 B 级文件清单
- 批量操作流程：
  1. goto 消除（JADX `--no-inline-methods` 重新反编译，或手动重构）
  2. 变量重命名（基于语义线索的批量重命名策略）
  3. JADX WARN 修复（常见 WARN 类型的标准修复方法）
- 预估工时

- [ ] **Step 3: 撰写 Phase 3 — C 级文件深度逆向**

- 列出所有 C 级文件清单
- 逐方法分析策略：
  1. 从日志标签识别方法功能
  2. 从 API 调用推断行为
  3. 从调用者/被调用者关系还原上下文
- 优先级排序（按被引用次数 / 功能重要性）
- 预估工时

- [ ] **Step 4: 撰写 Phase 4 — D 级文件替代方案**

- 列出所有 D 级文件清单
- 每个文件的替代策略（参考行为重写 / 放弃 / 替代实现）
- 预估工时

- [ ] **Step 5: 撰写逆向工具链推荐**

- JADX 配置优化（推荐参数）
- 反混淆工具（JEB, Procyon, CFR 等对比）
- IDE 重构辅助（IntelliJ rename/inline/extract 快捷操作）
- 动态分析辅助（Frida hook 辅助理解运行时行为）

- [ ] **Step 6: 撰写预估总工时**

按 Phase 汇总工时：
- Phase 1: A 级（直接使用，0 工时）
- Phase 2: B 级（轻度逆向，X 天）
- Phase 3: C 级（深度逆向，X 天）
- Phase 4: D 级（替代方案，X 天）
- 总计

- [ ] **Step 7: Commit**

```bash
git add docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md
git commit -m "docs: 评估报告第 5 章（逆向实施方案）"
```

---

### Task 8: 撰写评估报告 — 第 6 章（逐文件评分清单）

**Files:**
- Modify: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
- Read: `docs/vendor-reverse/vendor-code-metrics.csv`

- [ ] **Step 1: 从 CSV 生成 Markdown 表格**

```bash
# 辅助命令：CSV 转 Markdown 表格
echo "| 文件 | 行数 | 反混淆 | 反编译 | 语义 | 工作量 | 可维护 | 综合 | 等级 |"
echo "|------|------|--------|--------|------|--------|--------|------|------|"
tail -n +2 docs/vendor-reverse/vendor-code-metrics.csv | awk -F, '{
    # 缩短路径: 去掉 vendor base prefix
    gsub(/app\/storage\/app\/apk\/apkstub\/decompiled_vendor\/sources\//, "", $1);
    printf "| %s | %s | %s | %s | %s | %s | %s | %s | %s |\n", $1, $2, $17, $18, $19, $20, $21, $22, $23
}'
```

将生成的表格追加到报告第 6 章。

- [ ] **Step 2: Commit**

```bash
git add docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md
git commit -m "docs: 评估报告第 6 章（327 文件逐文件评分清单）"
```

---

### Task 9: 最终审查和提交

**Files:**
- Read: `docs/vendor-reverse/VENDOR_CODE_ASSESSMENT.md`
- Read: `docs/vendor-reverse/vendor-code-metrics.csv`
- Read: `scripts/vendor-code-scan.sh`

- [ ] **Step 1: 通读完整报告，检查一致性**

验证：
- 第 2 章全局统计的数字与 CSV 数据一致
- 第 3 章 20 个分区的文件数之和 = 327
- 第 5 章各 Phase 的文件清单与等级分类一致
- 第 6 章表格行数 = 327
- 决策矩阵结论与实际 A+B 占比匹配

- [ ] **Step 2: 修复任何不一致**

如发现数据不一致，修复报告中的对应部分。

- [ ] **Step 3: Final Commit**

```bash
git add -A docs/vendor-reverse/ scripts/vendor-code-scan.sh
git commit -m "docs: Vendor 反编译代码逆向可读性与二次开发评估完整报告"
```
