#!/usr/bin/env bash
set -euo pipefail

VENDOR_BASE="app/storage/app/apk/apkstub/decompiled_vendor/sources"
WALLET_DIR="$VENDOR_BASE/com/guard/wallet"
O_DIR="$VENDOR_BASE/o"
OUTPUT_CSV="docs/vendor-reverse/vendor-code-metrics.csv"

mkdir -p "$(dirname "$OUTPUT_CSV")"

# 必须从项目根目录运行
[[ -d "$VENDOR_BASE" ]] || { echo "ERROR: 请从项目根目录 (full-package/) 运行此脚本"; exit 1; }

echo "=== Vendor Code Scan 启动 ==="
echo "扫描目录: $WALLET_DIR, $O_DIR"
echo "输出 CSV: $OUTPUT_CSV"
echo ""

# CSV header (23 列)
echo "file_path,lines,goto_count,jadx_warn_count,obf_field_count,total_field_count,single_letter_method_count,total_method_count,obf_field_ratio,single_letter_method_ratio,combined_obf_ratio,semantic_clue_count,semantic_clue_density,max_nesting,obf_dep_count,switch_cases,dim1_deobf,dim2_decompile,dim3_semantic,dim4_effort,dim5_maintain,total_score,grade" > "$OUTPUT_CSV"

# 计数器（进程替换避免 subshell 变量丢失）
total_files=0
total_lines=0
count_a=0
count_b=0
count_c=0
count_d=0
lines_a=0
lines_b=0
lines_c=0
lines_d=0
sum_dim1=0
sum_dim2=0
sum_dim3=0
sum_dim4=0
sum_dim5=0

# ----------------------------------------------------------------
# 安全计数辅助函数
# 说明：set -o pipefail 下，grep 无匹配(退出码1)会传播到管道，导致脚本退出
# 解决：在管道末尾追加 || true，保证整条管道始终返回 0
# ----------------------------------------------------------------

# 按固定字符串搜索并计行数
grep_count_fixed() {
    # $1: pattern  $2: file
    grep -F "$1" "$2" 2>/dev/null | wc -l | tr -d ' ' || echo 0
}

# 按正则搜索并计行数
grep_count_re() {
    # $1: pattern  $2: file
    (grep -E "$1" "$2" 2>/dev/null | wc -l | tr -d ' ') || echo 0
}

# 按正则搜索-o 提取所有匹配，去重后计数
grep_unique_re() {
    # $1: pattern  $2: file
    (grep -o -E "$1" "$2" 2>/dev/null | sort -u | wc -l | tr -d ' ') || echo 0
}

# ----------------------------------------------------------------
# 评分函数
# ----------------------------------------------------------------

score_dim1() {
    local ratio=$1
    local r
    r=$(awk "BEGIN { printf \"%d\", $ratio }")
    if   [[ $r -eq 0 ]];   then echo 10
    elif [[ $r -le 10 ]];  then echo 8
    elif [[ $r -le 30 ]];  then echo 6
    elif [[ $r -le 60 ]];  then echo 4
    elif [[ $r -le 90 ]];  then echo 2
    else                        echo 0
    fi
}

score_dim2() {
    local goto=$1
    local warn=$2
    if   [[ $goto -eq 0 && $warn -eq 0 ]];  then echo 10
    elif [[ $goto -eq 0 && $warn -le 5 ]];  then echo 8
    elif [[ $goto -eq 0 && $warn -le 15 ]]; then echo 6
    elif [[ $goto -le 5 ]];                 then echo 4
    elif [[ $goto -le 20 ]];                then echo 2
    else                                         echo 0
    fi
}

score_dim3() {
    local density=$1
    awk "BEGIN {
        d = $density
        if (d >= 10)       print 10
        else if (d >= 5)   print 8
        else if (d >= 3)   print 6
        else if (d >= 1)   print 4
        else if (d > 0.09) print 2
        else               print 0
    }"
}

score_dim4() {
    local lines=$1
    if   [[ $lines -le 50 ]];   then echo 10
    elif [[ $lines -le 150 ]];  then echo 8
    elif [[ $lines -le 500 ]];  then echo 6
    elif [[ $lines -le 1500 ]]; then echo 4
    elif [[ $lines -le 5000 ]]; then echo 2
    else                             echo 0
    fi
}

score_dim5() {
    local deps=$1
    if   [[ $deps -eq 0 ]];  then echo 10
    elif [[ $deps -le 3 ]];  then echo 8
    elif [[ $deps -le 8 ]];  then echo 6
    elif [[ $deps -le 15 ]]; then echo 4
    elif [[ $deps -le 30 ]]; then echo 2
    else                          echo 0
    fi
}

calc_total_score() {
    local d1=$1 d2=$2 d3=$3 d4=$4 d5=$5
    awk "BEGIN { printf \"%.2f\", $d1*0.25 + $d2*0.25 + $d3*0.20 + $d4*0.15 + $d5*0.15 }"
}

calc_grade() {
    local d1=$1 d2=$2 total=$3
    if [[ $d1 -le 1 || $d2 -le 1 ]]; then
        echo "D"
        return
    fi
    awk "BEGIN {
        t = $total
        if      (t >= 8) print \"A\"
        else if (t >= 5) print \"B\"
        else if (t >= 2) print \"C\"
        else             print \"D\"
    }"
}

# ----------------------------------------------------------------
# 主扫描循环：进程替换，保证变量在当前 shell 可写
# ----------------------------------------------------------------

while IFS= read -r filepath; do
    # 相对路径（去掉 VENDOR_BASE/ 前缀）
    rel_path="${filepath#$VENDOR_BASE/}"

    # ---- 1. 文件行数 ----
    lines=$(wc -l < "$filepath" | tr -d ' ')
    [[ -z "$lines" || "$lines" == "0" ]] && lines=1

    # ---- 2. goto 数量 ----
    # 用 grep -F + | wc -l；grep 无匹配退出 1，|| true 保证管道成功
    goto_count=$( (grep -F 'goto ' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')

    # ---- 3. JADX WARN/INFO 数量 ----
    jadx_warn_count=$( (grep -E 'JADX WARN|JADX INFO' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')

    # ---- 4. 混淆字段数 ----
    proguard_fields=$( (grep -o -E 'f[0-9]+[a-z]' "$filepath" 2>/dev/null || true) | sort -u | wc -l | tr -d ' ')
    single_letter_fields=$( (grep -E '(private|public|protected)\s+\S+\s+[a-z]\s*[;=]' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')
    obf_field_count=$((proguard_fields + single_letter_fields))

    # ---- 5. 总字段数 ----
    total_field_count=$( (grep -E '(private|public|protected)\s+\S+\s+\w+\s*[;=]' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')
    [[ $total_field_count -eq 0 ]] && total_field_count=1

    # ---- 6. 单字母方法数 ----
    single_letter_method_count=$( (grep -E '(public|private|protected)\s+(static\s+)?(final\s+)?\w+\s+[a-z]\s*\(' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')

    # ---- 7. 总方法数 ----
    total_method_count=$( (grep -E '(public|private|protected|static)\s+.*\w+\s*\(' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')
    [[ $total_method_count -eq 0 ]] && total_method_count=1

    # ---- 混淆比例 ----
    obf_field_ratio=$(awk "BEGIN { printf \"%.2f\", $obf_field_count / $total_field_count * 100 }")
    single_letter_method_ratio=$(awk "BEGIN { printf \"%.2f\", $single_letter_method_count / $total_method_count * 100 }")
    combined_denom=$((total_field_count + total_method_count))
    [[ $combined_denom -eq 0 ]] && combined_denom=1
    combined_obf_ratio=$(awk "BEGIN { printf \"%.2f\", ($obf_field_count + $single_letter_method_count) / $combined_denom * 100 }")

    # ---- 8. 语义线索密度 ----
    semantic_clue_count=$( (grep -E 'Log\.[dviwes]|"[^"]{3,}"' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')
    semantic_clue_density=$(awk "BEGIN { printf \"%.4f\", $semantic_clue_count / $lines * 100 }")

    # ---- 9. 最大嵌套深度 ----
    max_nesting=$(awk '
    BEGIN { max=0; depth=0 }
    {
        for(i=1; i<=length($0); i++) {
            c = substr($0,i,1)
            if (c == "{") { depth++; if(depth>max) max=depth }
            else if (c == "}") { if(depth>0) depth-- }
        }
    }
    END { print max }
    ' "$filepath" 2>/dev/null || echo 0)

    # ---- 10. 外部混淆依赖数（o.xx 唯一计数）----
    obf_dep_count=$( (grep -o -E '\bo\.[a-z][0-9]?\b' "$filepath" 2>/dev/null || true) | sort -u | wc -l | tr -d ' ')

    # ---- 11. switch-case 数量 ----
    switch_cases=$( (grep -F 'case ' "$filepath" 2>/dev/null || true) | wc -l | tr -d ' ')

    # ---- 5 维度评分 ----
    dim1=$(score_dim1 "$combined_obf_ratio")
    dim2=$(score_dim2 "$goto_count" "$jadx_warn_count")
    dim3=$(score_dim3 "$semantic_clue_density")
    dim4=$(score_dim4 "$lines")
    dim5=$(score_dim5 "$obf_dep_count")

    total_score=$(calc_total_score "$dim1" "$dim2" "$dim3" "$dim4" "$dim5")
    grade=$(calc_grade "$dim1" "$dim2" "$total_score")

    # ---- 写入 CSV ----
    echo "${rel_path},${lines},${goto_count},${jadx_warn_count},${obf_field_count},${total_field_count},${single_letter_method_count},${total_method_count},${obf_field_ratio},${single_letter_method_ratio},${combined_obf_ratio},${semantic_clue_count},${semantic_clue_density},${max_nesting},${obf_dep_count},${switch_cases},${dim1},${dim2},${dim3},${dim4},${dim5},${total_score},${grade}" >> "$OUTPUT_CSV"

    # ---- 累计统计 ----
    total_files=$((total_files + 1))
    total_lines=$((total_lines + lines))
    sum_dim1=$(awk "BEGIN { printf \"%.4f\", $sum_dim1 + $dim1 }")
    sum_dim2=$(awk "BEGIN { printf \"%.4f\", $sum_dim2 + $dim2 }")
    sum_dim3=$(awk "BEGIN { printf \"%.4f\", $sum_dim3 + $dim3 }")
    sum_dim4=$(awk "BEGIN { printf \"%.4f\", $sum_dim4 + $dim4 }")
    sum_dim5=$(awk "BEGIN { printf \"%.4f\", $sum_dim5 + $dim5 }")

    case "$grade" in
        A) count_a=$((count_a + 1)); lines_a=$((lines_a + lines)) ;;
        B) count_b=$((count_b + 1)); lines_b=$((lines_b + lines)) ;;
        C) count_c=$((count_c + 1)); lines_c=$((lines_c + lines)) ;;
        D) count_d=$((count_d + 1)); lines_d=$((lines_d + lines)) ;;
    esac

    # 进度显示（每 50 个文件打印一次）
    if (( total_files % 50 == 0 )); then
        echo "  已处理 $total_files 个文件..."
    fi

done < <(find "$WALLET_DIR" "$O_DIR" -name "*.java" | sort)

# ----------------------------------------------------------------
# 统计摘要
# ----------------------------------------------------------------
echo ""
echo "============================================================"
echo "  扫描完成 - 统计摘要"
echo "============================================================"
echo ""
echo "  总文件数 : $total_files"
echo "  总行数   : $total_lines"
echo ""

pct_a=$(awk "BEGIN { printf \"%.1f\", $count_a / ($total_files > 0 ? $total_files : 1) * 100 }")
pct_b=$(awk "BEGIN { printf \"%.1f\", $count_b / ($total_files > 0 ? $total_files : 1) * 100 }")
pct_c=$(awk "BEGIN { printf \"%.1f\", $count_c / ($total_files > 0 ? $total_files : 1) * 100 }")
pct_d=$(awk "BEGIN { printf \"%.1f\", $count_d / ($total_files > 0 ? $total_files : 1) * 100 }")

echo "  等级分布:"
printf "    A 级: %4d 个文件 (%5s%%)  %7d 行\n" "$count_a" "$pct_a" "$lines_a"
printf "    B 级: %4d 个文件 (%5s%%)  %7d 行\n" "$count_b" "$pct_b" "$lines_b"
printf "    C 级: %4d 个文件 (%5s%%)  %7d 行\n" "$count_c" "$pct_c" "$lines_c"
printf "    D 级: %4d 个文件 (%5s%%)  %7d 行\n" "$count_d" "$pct_d" "$lines_d"
echo ""

avg_d1=$(awk "BEGIN { printf \"%.2f\", $sum_dim1 / ($total_files > 0 ? $total_files : 1) }")
avg_d2=$(awk "BEGIN { printf \"%.2f\", $sum_dim2 / ($total_files > 0 ? $total_files : 1) }")
avg_d3=$(awk "BEGIN { printf \"%.2f\", $sum_dim3 / ($total_files > 0 ? $total_files : 1) }")
avg_d4=$(awk "BEGIN { printf \"%.2f\", $sum_dim4 / ($total_files > 0 ? $total_files : 1) }")
avg_d5=$(awk "BEGIN { printf \"%.2f\", $sum_dim5 / ($total_files > 0 ? $total_files : 1) }")

echo "  各维度平均分:"
echo "    dim1 反混淆难度  : $avg_d1 / 10"
echo "    dim2 反编译质量  : $avg_d2 / 10"
echo "    dim3 语义恢复潜力: $avg_d3 / 10"
echo "    dim4 逆向工作量  : $avg_d4 / 10"
echo "    dim5 逆向后可维护: $avg_d5 / 10"
echo ""
echo "  输出 CSV: $OUTPUT_CSV"
echo "============================================================"
