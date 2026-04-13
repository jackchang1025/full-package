#!/bin/bash
# verify-xiaomi-13.sh — Xiaomi 13 (HyperOS V816) 真机验证
#
# 验证 XiaomiEngine 使用的 MIUI_* key 在 HyperOS 真机 UI 上的匹配度。
# HyperOS 相对 MIUI 14 有较多文案改动，这是最需要真机验证的目标。
#
# 用法:
#   bash vendor-replica/scripts/real-device-verify/verify-xiaomi-13.sh
#
# 产出:
#   reports/xiaomi-13.md + .seeder.log + .ui.xml

set -u

# 来自 CLAUDE.md / ADB_CONNECTION.md:
#   Xiaomi 13 (2211133C) | 192.168.31.102:5555 | Android 15 HyperOS V816
: "${DEVICE_ID:=192.168.31.102:5555}"
: "${DEVICE_LABEL:=xiaomi-13}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/common.sh
source "${SCRIPT_DIR}/lib/common.sh"

export DEVICE_ID DEVICE_LABEL

echo "════════════════════════════════════════════════════════════════"
echo "  真机验证 — Xiaomi 13 (HyperOS V816)"
echo "  Device: $DEVICE_ID"
echo "════════════════════════════════════════════════════════════════"

assert_prerequisites
ensure_apk_built
adb_connect || exit 10
clean_install || exit 11
launch_and_capture_seeder_log
assert_seeder_success

# ─── MIUI/HyperOS 特定的 UI 验证 ───
#
# XiaomiEngine 监听的关键窗口:
#   com.miui.securitycenter / com.miui.permcenter.autostart.AutoStartManagementActivity
#     — 自启动管理
#   com.miui.powerkeeper / com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity
#     — 省电策略 / "无限制"
#
# HyperOS 对第三方直接 am start 更严格，通常需要经过应用详情页。
# 这里优先尝试自启动管理页（MIUI_SETTINGS_UNRESTRICTED_TEXT 在那里不可见，
# 但至少能验证 windowPackage 正确被 replica 识别）。
OPEN_TARGET="com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity"
open_vendor_settings "$OPEN_TARGET" || true

dump_ui_hierarchy || true

# 第二轮：抓一次省电策略页（更可能命中 MIUI_APP_POWER_CONSUME_TEXT / MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT）
log_info "二次采样：尝试打开 powerkeeper HiddenApps 页"
"$ADB" -s "$DEVICE_ID" shell am start \
    -n "com.miui.powerkeeper/com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity" \
    >/dev/null 2>&1 || log_info "powerkeeper 页打不开（可能需要先通过主菜单进入）"
sleep 4

UI_DUMP_PATH_PRIMARY="${REPORTS_DIR}/${DEVICE_LABEL}.ui.xml"
UI_DUMP_PATH_POWERKEEPER="${REPORTS_DIR}/${DEVICE_LABEL}.powerkeeper.ui.xml"

"$ADB" -s "$DEVICE_ID" shell "uiautomator dump /sdcard/ui-pk.xml" >/dev/null 2>&1 || true
"$ADB" -s "$DEVICE_ID" pull /sdcard/ui-pk.xml "$UI_DUMP_PATH_POWERKEEPER" >/dev/null 2>&1 || true

# 把两个 dump 合并到 primary（简单拼接，compare.py 的 ElementTree 只会解析第一个根节点，
# 所以真正的做法是跑两次 compare）
# 这里为了简洁：分别对比，取命中率更高的那次。
log_info "对比自启动管理页 UI"
compare_keys_against_ui "MIUI_"
MIUI_AUTOSTART_MATCHED=$COMPARE_MATCHED
MIUI_AUTOSTART_TOTAL=$COMPARE_TOTAL
MIUI_AUTOSTART_MISSING=$COMPARE_MISSING

if [ -f "$UI_DUMP_PATH_POWERKEEPER" ] && [ "$(wc -c < "$UI_DUMP_PATH_POWERKEEPER")" -gt 100 ]; then
    log_info "对比 powerkeeper 省电策略页 UI"
    UI_DUMP_PATH="$UI_DUMP_PATH_POWERKEEPER"
    compare_keys_against_ui "MIUI_"
    if [ "${COMPARE_MATCHED:-0}" -gt "${MIUI_AUTOSTART_MATCHED:-0}" ]; then
        log_ok "powerkeeper 页命中率更高，采用其对比结果"
    else
        # 回退到自启动管理页的结果
        COMPARE_MATCHED=$MIUI_AUTOSTART_MATCHED
        COMPARE_TOTAL=$MIUI_AUTOSTART_TOTAL
        COMPARE_MISSING=$MIUI_AUTOSTART_MISSING
        UI_DUMP_PATH="$UI_DUMP_PATH_PRIMARY"
    fi
else
    UI_DUMP_PATH="$UI_DUMP_PATH_PRIMARY"
fi

COMPARE_PREFIXES="MIUI_"
export COMPARE_PREFIXES

generate_device_report

echo ""
echo "════════════════════════════════════════════════════════════════"
if [ "$VERIFY_FAILED" = "0" ]; then
    echo "  ✅ Xiaomi 13 验证通过"
else
    echo "  ❌ Xiaomi 13 验证失败 — 查看报告定位问题"
fi
echo "  Report: ${REPORTS_DIR}/${DEVICE_LABEL}.md"
echo "════════════════════════════════════════════════════════════════"

exit "$VERIFY_FAILED"
