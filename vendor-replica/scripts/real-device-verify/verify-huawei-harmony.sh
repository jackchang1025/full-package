#!/bin/bash
# verify-huawei-harmony.sh — 华为鸿蒙系统设备真机验证
#
# 验证 HuaweiEngine 在 HarmonyOS 真机上的 key 命中度。
# HarmonyOS 的 systemmanager 包名与 EMUI 不同（部分设备为 com.hihonor.systemmanager），
# 需要单独验证。
#
# 用法:
#   bash vendor-replica/scripts/real-device-verify/verify-huawei-harmony.sh
#
# 产出:
#   reports/huawei-harmony.md + .seeder.log + .ui.xml

set -u

# 来自 ADB_CONNECTION.md: 华为鸿蒙系统设备 1 | 192.168.31.162:5555
: "${DEVICE_ID:=192.168.31.162:5555}"
: "${DEVICE_LABEL:=huawei-harmony}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/common.sh
source "${SCRIPT_DIR}/lib/common.sh"

export DEVICE_ID DEVICE_LABEL

echo "════════════════════════════════════════════════════════════════"
echo "  真机验证 — Huawei HarmonyOS"
echo "  Device: $DEVICE_ID"
echo "════════════════════════════════════════════════════════════════"

assert_prerequisites
ensure_apk_built
adb_connect || exit 10
clean_install || exit 11
launch_and_capture_seeder_log
assert_seeder_success

# HarmonyOS 的启动管理页面包名需要实际探测，先尝试几种常见组合：
HARMONY_TARGETS=(
    "com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity"
    "com.hihonor.systemmanager/.startupmgr.ui.StartupNormalAppListActivity"
    "com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
)

OPENED=0
for target in "${HARMONY_TARGETS[@]}"; do
    log_info "尝试打开 $target"
    if "$ADB" -s "$DEVICE_ID" shell am start -n "$target" 2>&1 | grep -v "Error"; then
        OPENED=1
        log_ok "成功打开 $target"
        break
    fi
done

if [ "$OPENED" = "0" ]; then
    log_warn "所有 Huawei/Honor 启动管理 Activity 都打不开，回退到应用详情页"
    "$ADB" -s "$DEVICE_ID" shell am start -a android.settings.APPLICATION_DETAILS_SETTINGS \
        -d "package:${PACKAGE}" >/dev/null 2>&1 || true
fi
sleep "$UI_WAIT_SEC"

dump_ui_hierarchy || true

COMPARE_PREFIXES="HUA_WEI_"
export COMPARE_PREFIXES
compare_keys_against_ui "$COMPARE_PREFIXES"

generate_device_report

echo ""
echo "════════════════════════════════════════════════════════════════"
if [ "$VERIFY_FAILED" = "0" ]; then
    echo "  ✅ Huawei HarmonyOS 验证通过"
else
    echo "  ❌ Huawei HarmonyOS 验证失败 — 查看报告定位问题"
fi
echo "  Report: ${REPORTS_DIR}/${DEVICE_LABEL}.md"
echo "════════════════════════════════════════════════════════════════"

exit "$VERIFY_FAILED"
