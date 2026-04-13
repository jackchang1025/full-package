#!/bin/bash
# verify-huawei-android.sh — 华为安卓系统设备 (EMUI) 真机验证
#
# 验证 HuaweiEngine 使用的 HUA_WEI_* key 在 EMUI 真机 UI 上的匹配度。
#
# 用法:
#   bash vendor-replica/scripts/real-device-verify/verify-huawei-android.sh
#
# 产出:
#   reports/huawei-android.md + .seeder.log + .ui.xml

set -u

# 来自 ADB_CONNECTION.md: 华为安卓系统设备 2 | 192.168.31.211:5555
: "${DEVICE_ID:=192.168.31.211:5555}"
: "${DEVICE_LABEL:=huawei-android}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/common.sh
source "${SCRIPT_DIR}/lib/common.sh"

export DEVICE_ID DEVICE_LABEL

echo "════════════════════════════════════════════════════════════════"
echo "  真机验证 — Huawei Android (EMUI)"
echo "  Device: $DEVICE_ID"
echo "════════════════════════════════════════════════════════════════"

assert_prerequisites
ensure_apk_built
adb_connect || exit 10
clean_install || exit 11
launch_and_capture_seeder_log
assert_seeder_success

# HuaweiEngine 监听包:
#   com.huawei.systemmanager / StartupAppControlActivity (或类似)
#   包含 HUA_WEI_ALLOW_AUTO_STARTUP_TEXT 等开关
OPEN_TARGET="com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity"
open_vendor_settings "$OPEN_TARGET" || true

dump_ui_hierarchy || true

COMPARE_PREFIXES="HUA_WEI_"
export COMPARE_PREFIXES
compare_keys_against_ui "$COMPARE_PREFIXES"

generate_device_report

echo ""
echo "════════════════════════════════════════════════════════════════"
if [ "$VERIFY_FAILED" = "0" ]; then
    echo "  ✅ Huawei Android 验证通过"
else
    echo "  ❌ Huawei Android 验证失败 — 查看报告定位问题"
fi
echo "  Report: ${REPORTS_DIR}/${DEVICE_LABEL}.md"
echo "════════════════════════════════════════════════════════════════"

exit "$VERIFY_FAILED"
