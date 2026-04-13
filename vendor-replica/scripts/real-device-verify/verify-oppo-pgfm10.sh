#!/bin/bash
# verify-oppo-pgfm10.sh — OPPO PGFM10 (ColorOS 16) 真机验证
#
# 该脚本验证 LocateValuesSeeder 在 OPPO 真机上正确工作，并校验
# OppoEngine 使用的所有 COLORS_* / OPPO_* key 在真机 UI 上能否匹配到文本。
#
# 用法:
#   bash vendor-replica/scripts/real-device-verify/verify-oppo-pgfm10.sh
#   DEVICE_ID=192.168.31.249:5555 bash vendor-replica/scripts/real-device-verify/verify-oppo-pgfm10.sh
#
# 产出:
#   vendor-replica/scripts/real-device-verify/reports/oppo-pgfm10.md
#   vendor-replica/scripts/real-device-verify/reports/oppo-pgfm10.seeder.log
#   vendor-replica/scripts/real-device-verify/reports/oppo-pgfm10.ui.xml
#
# 退出码:
#   0 - 全部步骤通过
#   非 0 - 某一步硬失败（前置检查/打包/安装）

set -u

# 来自 CLAUDE.md / ADB_CONNECTION.md:
#   OPPO PGFM10 | 192.168.31.249:5555 | ColorOS 16.0.3.500 Android 16
: "${DEVICE_ID:=192.168.31.249:5555}"
: "${DEVICE_LABEL:=oppo-pgfm10}"

# 加载共享函数
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/common.sh
source "${SCRIPT_DIR}/lib/common.sh"

export DEVICE_ID DEVICE_LABEL

echo "════════════════════════════════════════════════════════════════"
echo "  真机验证 — OPPO PGFM10 (ColorOS 16)"
echo "  Device: $DEVICE_ID"
echo "════════════════════════════════════════════════════════════════"

# ─── 通用步骤 ───
assert_prerequisites
ensure_apk_built
adb_connect || exit 10
clean_install || exit 11
launch_and_capture_seeder_log
assert_seeder_success

# ─── OPPO 特定的 UI 验证 ───
#
# OPPO ColorOS 保活关键窗口:
#   com.oplus.battery / com.oplus.powermanager.fuelgaue.PowerControlActivity
#   — 包含: COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT,
#            COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT,
#            COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT,
#            COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT
#
# 直接 am start 可能被 ColorOS 权限拦截（需要从 Settings 主页导航）。
# 使用 APPLICATION_DETAILS_SETTINGS 作为 fallback，至少能打到应用详情页，
# 捕获 OppoEngine.A0/v0 那两个 ListenWindow 对应的 TextView 文本。
OPEN_TARGET="com.oplus.battery/com.oplus.powermanager.fuelgaue.PowerControlActivity"
open_vendor_settings "$OPEN_TARGET" || {
    log_warn "直接打开 PowerControlActivity 失败，改用应用详情页"
    "$ADB" -s "$DEVICE_ID" shell am start -a android.settings.APPLICATION_DETAILS_SETTINGS \
        -d "package:${PACKAGE}" >/dev/null 2>&1 || true
    sleep "$UI_WAIT_SEC"
}

dump_ui_hierarchy || true

# ─── 对比 ───
#
# OPPO 相关的 key 前缀:
#   COLORS_  — OppoEngine 使用的 9 个保活文本 key
#   OPPO_    — PackageInstallerDelegate 使用的 5 个 OPPO 安装授权 key
COMPARE_PREFIXES="COLORS_ OPPO_"
export COMPARE_PREFIXES
compare_keys_against_ui "$COMPARE_PREFIXES"

# ─── 生成报告 ───
generate_device_report

echo ""
echo "════════════════════════════════════════════════════════════════"
if [ "$VERIFY_FAILED" = "0" ]; then
    echo "  ✅ OPPO PGFM10 验证通过"
else
    echo "  ❌ OPPO PGFM10 验证失败 — 查看报告定位问题"
fi
echo "  Report: ${REPORTS_DIR}/${DEVICE_LABEL}.md"
echo "════════════════════════════════════════════════════════════════"

exit "$VERIFY_FAILED"
