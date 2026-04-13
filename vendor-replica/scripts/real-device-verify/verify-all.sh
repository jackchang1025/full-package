#!/bin/bash
# verify-all.sh — 在全部 4 台设备上依次运行真机验证
#
# 用法:
#   bash vendor-replica/scripts/real-device-verify/verify-all.sh
#
# 默认顺序: OPPO → Xiaomi → Huawei HarmonyOS → Huawei Android
# 某一台设备验证失败不会阻止后续设备，但最终退出码会反映整体状态。
#
# 产出:
#   reports/*.md           — 单设备报告
#   reports/SUMMARY.md     — 4 台设备汇总
#   reports/SUMMARY.md 末尾会列出需要人工 follow-up 的 key 差异

set -u

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPORTS_DIR="${SCRIPT_DIR}/reports"
SUMMARY_FILE="${REPORTS_DIR}/SUMMARY.md"
mkdir -p "$REPORTS_DIR"

# 设备 → 脚本的映射
declare -a DEVICE_SCRIPTS=(
    "verify-oppo-pgfm10.sh|OPPO PGFM10|192.168.31.249:5555"
    "verify-xiaomi-13.sh|Xiaomi 13 HyperOS|192.168.31.102:5555"
    "verify-huawei-harmony.sh|Huawei HarmonyOS|192.168.31.162:5555"
    "verify-huawei-android.sh|Huawei Android EMUI|192.168.31.211:5555"
)

declare -a RESULTS

echo "════════════════════════════════════════════════════════════════"
echo "  全量真机验证 — 4 台设备顺序执行"
echo "════════════════════════════════════════════════════════════════"

for entry in "${DEVICE_SCRIPTS[@]}"; do
    IFS='|' read -r script label addr <<<"$entry"
    echo ""
    echo "▶ 开始验证: $label ($addr)"
    echo "────────────────────────────────────────────────────────────────"
    if bash "${SCRIPT_DIR}/${script}"; then
        RESULTS+=("PASS|$label|$addr|$script")
        echo "◀ $label 验证通过"
    else
        RESULTS+=("FAIL|$label|$addr|$script")
        echo "◀ $label 验证失败（退出码非 0）"
    fi
done

# ─── 汇总报告 ───
echo ""
echo "════════════════════════════════════════════════════════════════"
echo "  生成汇总报告: $SUMMARY_FILE"
echo "════════════════════════════════════════════════════════════════"

TS=$(date '+%Y-%m-%d %H:%M:%S')
{
    echo "# 真机验证汇总 — LocateValues / 保活 UI 自动化"
    echo ""
    echo "**生成时间**: $TS"
    echo ""
    echo "## 设备结果"
    echo ""
    echo "| 设备 | 状态 | 地址 | 报告 |"
    echo "|---|---|---|---|"
    PASS_COUNT=0
    FAIL_COUNT=0
    for r in "${RESULTS[@]}"; do
        IFS='|' read -r status label addr script <<<"$r"
        dev_label="${script#verify-}"
        dev_label="${dev_label%.sh}"
        if [ "$status" = "PASS" ]; then
            echo "| $label | ✅ $status | \`$addr\` | [\`$dev_label.md\`](./$dev_label.md) |"
            PASS_COUNT=$((PASS_COUNT + 1))
        else
            echo "| $label | ❌ $status | \`$addr\` | [\`$dev_label.md\`](./$dev_label.md) |"
            FAIL_COUNT=$((FAIL_COUNT + 1))
        fi
    done
    echo ""
    echo "**总计**: ${PASS_COUNT} PASS / ${FAIL_COUNT} FAIL / ${#RESULTS[@]} 台"
    echo ""

    # 命中率汇总 — 从每份单机报告里抽取 "命中数 / 总数"
    echo "## UI 文本命中率对比"
    echo ""
    echo "| 设备 | 前缀 | 命中 / 总 | 命中率 | 未命中 key |"
    echo "|---|---|---|---|---|"
    for r in "${RESULTS[@]}"; do
        IFS='|' read -r status label addr script <<<"$r"
        dev_label="${script#verify-}"
        dev_label="${dev_label%.sh}"
        report="${REPORTS_DIR}/${dev_label}.md"
        if [ -f "$report" ]; then
            # 用 grep + awk 从 markdown 报告里抽字段
            prefix=$(grep '检查的 key 前缀' "$report" | head -1 | sed 's/.*`\(.*\)`.*/\1/')
            hit=$(grep '命中数 / 总数' "$report" | head -1 | sed 's/.*`\(.*\)`.*/\1/')
            miss=$(grep '未命中的 key' "$report" | head -1 | sed 's/.*`\(.*\)`.*/\1/')
            # 算命中率
            m=$(echo "$hit" | cut -d/ -f1 | tr -d ' ')
            t=$(echo "$hit" | cut -d/ -f2 | tr -d ' ')
            rate="-"
            if [ -n "$m" ] && [ -n "$t" ] && [ "$t" != "0" ]; then
                rate=$(python3 -c "print(f'{${m}/${t}*100:.1f}%')" 2>/dev/null || echo "-")
            fi
            echo "| $label | \`$prefix\` | \`$hit\` | $rate | \`${miss:-无}\` |"
        else
            echo "| $label | — | — | — | _报告缺失_ |"
        fi
    done
    echo ""

    echo "## 下一步建议"
    echo ""
    if [ "$FAIL_COUNT" -gt 0 ]; then
        echo "- ❌ 有 $FAIL_COUNT 台设备验证失败，**先逐台打开单机报告查看失败原因**"
        echo "- 常见失败原因:"
        echo "  - **ERROR**: Seeder 执行时抛出异常 — 看 \`*.seeder.log\` 里的完整 stacktrace"
        echo "  - **seeder 日志缺失**: 可能 Plan A 实际没合并到 main，或 TAG 不是 \`LocateValuesSeeder\`"
        echo "  - **externalFilesDir 文件不存在**: 可能 \`MainApplication.init()\` 的 6 行插入位置错了"
        echo ""
    fi
    echo "- 对于命中率 < 100% 的设备，**逐 key 人工核对**未命中的 key:"
    echo "  1. 打开对应设备的 \`reports/<device>.ui.xml\`"
    echo "  2. grep 未命中 key 对应的**值**（例如 \`grep '允许自启动' ui.xml\`）"
    echo "  3. 如果 grep 到实际文本，但名字略不同（比如 HyperOS 叫 \"自启动\" 而不是 \"允许自启动\"），**更新 \`locateValues.json\` 中的对应 key**"
    echo "  4. 如果 grep 不到任何类似文本，**说明当前 UI dump 的窗口没覆盖到那个 key**，换一个设置页面重新跑"
    echo ""
    echo "- 对命中率 100% 的设备：直接触发 ADB 配对 / 保活完整流程做端到端验证。"
    echo ""
    echo "## 相关文件"
    echo ""
    echo "- 脚本: \`vendor-replica/scripts/real-device-verify/\`"
    echo "- 数据源: \`vendor-replica/app/src/main/assets/locateValues.json\` (80 key)"
    echo "- 审计文档: \`vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md\`"
} > "$SUMMARY_FILE"

cat "$SUMMARY_FILE"

echo ""
echo "════════════════════════════════════════════════════════════════"
echo "  汇总报告已生成: $SUMMARY_FILE"
echo "════════════════════════════════════════════════════════════════"

# 只要有一台失败，整体退出码非 0
if [ "$FAIL_COUNT" -gt 0 ]; then
    exit 1
fi
exit 0
