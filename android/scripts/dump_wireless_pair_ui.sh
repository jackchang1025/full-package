#!/bin/bash
#
# dump_wireless_pair_ui.sh — Dump OPPO Settings UI for WirelessPairEngine
#
# Captures 6+ Settings screens as uiautomator XML dumps for developing
# GKD Selector expressions used by WirelessPairEngine.
#
# Usage:
#   bash scripts/dump_wireless_pair_ui.sh [device_serial]
#   bash scripts/dump_wireless_pair_ui.sh 192.168.31.249:5555
#
# No interactive prompts — uses ADB intents/input commands for navigation.
# Some screens (about_phone, version_info, pair_code_dialog) require
# tap/scroll navigation since no direct intent is available.
#
# Target screens:
#   1. settings_home       — Settings main page (android.settings.SETTINGS)
#   2. about_phone         — 关于本机 (scroll to bottom of settings_home, tap)
#   3. version_info        — 版本信息 (tap within about_phone)
#   4. dev_options          — 开发者选项 (APPLICATION_DEVELOPMENT_SETTINGS)
#   5. wireless_debug      — 无线调试 page (scroll in dev_options, tap)
#   6. wireless_debug_confirm — 无线调试 confirm dialog (toggle switch off→on)
#   7. pair_code_dialog    — 使用配对码配对 dialog (tap button in wireless_debug)
#
# Device: OPPO PGFM10 (Android 16, API 36, ColorOS)
#

set -euo pipefail

# ============ Configuration ============

DEVICE="${1:-192.168.31.249:5555}"
ADB="${ADB:-/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe}"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
FIXTURE_DIR="$SCRIPT_DIR/../app/src/test/resources/fixtures/oppo"

# Screen dimensions (OPPO PGFM10: 1080x2400 typical)
SCREEN_W=1080
SCREEN_H=2400

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

# Counters
DUMP_COUNT=0
FAIL_COUNT=0

# ============ Utility Functions ============

adb_cmd() {
    "$ADB" -s "$DEVICE" "$@" 2>&1
}

log_info() {
    echo -e "${CYAN}[INFO]${NC} $1"
}

log_ok() {
    echo -e "${GREEN}[OK]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_fail() {
    echo -e "${RED}[FAIL]${NC} $1"
}

# Dump current screen to an XML file
# Usage: dump_screen <name> [wait_seconds]
dump_screen() {
    local name="$1"
    local wait_sec="${2:-3}"
    local out_file="$FIXTURE_DIR/${name}.xml"

    log_info "Dumping: $name (wait ${wait_sec}s for UI to settle)..."
    sleep "$wait_sec"

    # uiautomator dump to device, then pull
    local dump_result
    dump_result=$(adb_cmd shell uiautomator dump /sdcard/window_dump.xml)

    if echo "$dump_result" | grep -q "dumped"; then
        adb_cmd pull /sdcard/window_dump.xml "$out_file" > /dev/null 2>&1
        local size
        size=$(stat -c%s "$out_file" 2>/dev/null || echo "0")
        if [ "$size" -gt 100 ]; then
            log_ok "Saved $name.xml (${size} bytes)"
            DUMP_COUNT=$((DUMP_COUNT + 1))
        else
            log_fail "$name.xml too small (${size} bytes) — UI may not have loaded"
            FAIL_COUNT=$((FAIL_COUNT + 1))
        fi
    else
        log_fail "uiautomator dump failed for $name: $dump_result"
        FAIL_COUNT=$((FAIL_COUNT + 1))
    fi
}

# Scroll down (swipe up) on the screen
# Usage: scroll_down [distance_ratio] [duration_ms]
scroll_down() {
    local ratio="${1:-0.6}"
    local duration="${2:-300}"
    local start_y=$(echo "$SCREEN_H * 0.75" | bc | cut -d. -f1)
    local end_y=$(echo "$SCREEN_H * (0.75 - $ratio)" | bc | cut -d. -f1)
    local center_x=$((SCREEN_W / 2))
    adb_cmd shell input swipe "$center_x" "$start_y" "$center_x" "$end_y" "$duration"
}

# Tap at coordinates
# Usage: tap_xy <x> <y>
tap_xy() {
    adb_cmd shell input tap "$1" "$2"
}

# Find and tap a text node using uiautomator dump + grep
# Returns 0 if found and tapped, 1 if not found
# Usage: find_and_tap_text <text> [max_scrolls]
find_and_tap_text() {
    local text="$1"
    local max_scrolls="${2:-8}"

    for attempt in $(seq 0 "$max_scrolls"); do
        # Dump current screen
        adb_cmd shell uiautomator dump /sdcard/window_dump.xml > /dev/null 2>&1
        local xml
        xml=$(adb_cmd shell cat /sdcard/window_dump.xml 2>/dev/null)

        # Look for the text and extract its bounds
        local bounds
        bounds=$(echo "$xml" | grep -oP "text=\"[^\"]*${text}[^\"]*\"[^>]*bounds=\"\[(\d+),(\d+)\]\[(\d+),(\d+)\]\"" | head -1 | grep -oP 'bounds="\[\d+,\d+\]\[\d+,\d+\]"' | head -1)

        if [ -n "$bounds" ]; then
            # Parse bounds: [left,top][right,bottom]
            local left top right bottom
            left=$(echo "$bounds" | grep -oP '\[\K\d+' | sed -n '1p')
            top=$(echo "$bounds" | grep -oP ',\K\d+' | sed -n '1p')
            right=$(echo "$bounds" | grep -oP '\[\K\d+' | sed -n '2p')
            bottom=$(echo "$bounds" | grep -oP ',\K\d+' | sed -n '2p')

            # Tap center of the element
            local cx=$(( (left + right) / 2 ))
            local cy=$(( (top + bottom) / 2 ))
            log_info "Found '$text' at ($cx, $cy) — tapping"
            tap_xy "$cx" "$cy"
            return 0
        fi

        if [ "$attempt" -lt "$max_scrolls" ]; then
            log_info "Scroll #$((attempt + 1)) — looking for '$text'..."
            scroll_down 0.4
            sleep 1
        fi
    done

    log_warn "Text '$text' not found after $max_scrolls scrolls"
    return 1
}

# Go back one screen
go_back() {
    adb_cmd shell input keyevent KEYCODE_BACK
    sleep 1
}

# ============ Preflight Checks ============

echo ""
echo "============================================"
echo "  OPPO Settings UI Dump for WirelessPairEngine"
echo "============================================"
echo ""
echo "  Device : $DEVICE"
echo "  ADB    : $ADB"
echo "  Output : $FIXTURE_DIR"
echo ""

mkdir -p "$FIXTURE_DIR"

# Verify device connectivity
log_info "Checking device connectivity..."
if ! adb_cmd shell echo "connected" | grep -q "connected"; then
    log_fail "Device $DEVICE is not reachable!"
    echo ""
    echo "Troubleshooting:"
    echo "  1. Ensure the device is on the same network"
    echo "  2. Try: $ADB connect $DEVICE"
    echo "  3. Check USB debugging / wireless debugging is enabled"
    echo ""
    exit 1
fi
log_ok "Device connected"

# Get device info
local_model=$(adb_cmd shell getprop ro.product.model 2>/dev/null | tr -d '\r')
local_android=$(adb_cmd shell getprop ro.build.version.release 2>/dev/null | tr -d '\r')
local_api=$(adb_cmd shell getprop ro.build.version.sdk 2>/dev/null | tr -d '\r')
log_info "Device: $local_model, Android $local_android (API $local_api)"
echo ""

# ============ Screen 1: Settings Home ============

log_info ">>> Screen 1/7: Settings Home"
adb_cmd shell am start -a android.settings.SETTINGS > /dev/null 2>&1
dump_screen "settings_home" 3
echo ""

# ============ Screen 2: About Phone (关于本机) ============

log_info ">>> Screen 2/7: About Phone (关于本机)"
# On OPPO ColorOS, 关于本机 is near the bottom of Settings
# Try direct intent first (works on some OPPO builds)
adb_cmd shell am start -a android.settings.DEVICE_INFO_SETTINGS > /dev/null 2>&1
sleep 2

# Verify we reached the right page
adb_cmd shell uiautomator dump /sdcard/window_dump.xml > /dev/null 2>&1
local_check=$(adb_cmd shell cat /sdcard/window_dump.xml 2>/dev/null | grep -c "关于本机\|关于手机\|About phone" || true)

if [ "$local_check" -gt 0 ]; then
    log_info "Reached About Phone via intent"
else
    # Fallback: navigate via Settings home scroll + tap
    log_info "Intent didn't work, navigating manually..."
    adb_cmd shell am start -a android.settings.SETTINGS > /dev/null 2>&1
    sleep 2
    if ! find_and_tap_text "关于本机" 10; then
        find_and_tap_text "关于手机" 5 || log_warn "Could not find About Phone entry"
    fi
    sleep 2
fi
dump_screen "about_phone" 2
echo ""

# ============ Screen 3: Version Info (版本信息) ============

log_info ">>> Screen 3/7: Version Info (版本信息)"
# 版本信息 is usually within 关于本机
if find_and_tap_text "版本信息" 3; then
    dump_screen "version_info" 2
else
    log_warn "Could not find 版本信息 — may need manual capture"
    FAIL_COUNT=$((FAIL_COUNT + 1))
fi
go_back  # Back to about_phone
go_back  # Back to settings_home
echo ""

# ============ Screen 4: Developer Options ============

log_info ">>> Screen 4/7: Developer Options (开发者选项)"
adb_cmd shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS > /dev/null 2>&1
dump_screen "dev_options" 3
echo ""

# ============ Screen 5: Wireless Debugging Page ============

log_info ">>> Screen 5/7: Wireless Debugging Page (无线调试)"
# Wireless debugging is in developer options, usually needs scrolling
if find_and_tap_text "无线调试" 10; then
    sleep 2
    # Check if we entered a sub-page or just toggled a switch
    adb_cmd shell uiautomator dump /sdcard/window_dump.xml > /dev/null 2>&1
    local_page_check=$(adb_cmd shell cat /sdcard/window_dump.xml 2>/dev/null | grep -c "使用配对码\|配对新设备\|Pair device" || true)

    if [ "$local_page_check" -gt 0 ]; then
        log_info "On wireless debugging sub-page"
        dump_screen "wireless_debug" 2
    else
        # We may have tapped the switch instead of the text — try entering the page
        log_info "May have toggled switch — looking for wireless debug page entry..."
        go_back
        sleep 1
        adb_cmd shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS > /dev/null 2>&1
        sleep 2
        # Try scrolling to find it again and tap the text (not switch)
        find_and_tap_text "无线调试" 10
        sleep 2
        dump_screen "wireless_debug" 2
    fi
else
    log_warn "Could not find 无线调试 in developer options"
    FAIL_COUNT=$((FAIL_COUNT + 1))
fi
echo ""

# ============ Screen 6: Wireless Debug Confirm Dialog ============

log_info ">>> Screen 6/7: Wireless Debug Confirm Dialog"
# This dialog appears when toggling wireless debugging from OFF to ON
# We need to check if wireless debug is currently off, then toggle it
adb_cmd shell uiautomator dump /sdcard/window_dump.xml > /dev/null 2>&1
local_dialog_check=$(adb_cmd shell cat /sdcard/window_dump.xml 2>/dev/null | grep -c "允许\|确认\|确定\|取消" || true)

if [ "$local_dialog_check" -gt 0 ]; then
    log_info "Dialog detected on current screen"
    dump_screen "wireless_debug_confirm" 1
else
    log_warn "No confirm dialog visible — wireless debug may already be enabled"
    log_warn "To capture this screen manually:"
    log_warn "  1. Go to 开发者选项 → 无线调试"
    log_warn "  2. Toggle wireless debugging OFF then ON"
    log_warn "  3. When confirm dialog appears, run:"
    log_warn "     $ADB -s $DEVICE shell uiautomator dump /sdcard/window_dump.xml"
    log_warn "     $ADB -s $DEVICE pull /sdcard/window_dump.xml $FIXTURE_DIR/wireless_debug_confirm.xml"
fi
echo ""

# ============ Screen 7: Pair Code Dialog ============

log_info ">>> Screen 7/7: Pair Code Dialog (配对码对话框)"
# Try to tap "使用配对码配对" button
adb_cmd shell uiautomator dump /sdcard/window_dump.xml > /dev/null 2>&1
local_pair_btn=$(adb_cmd shell cat /sdcard/window_dump.xml 2>/dev/null | grep -c "使用配对码\|Pair device with pairing code" || true)

if [ "$local_pair_btn" -gt 0 ]; then
    if find_and_tap_text "使用配对码" 0; then
        sleep 2
        dump_screen "pair_code_dialog" 2
        # Dismiss the dialog
        go_back
    else
        log_warn "Found pair button text but couldn't tap it"
        FAIL_COUNT=$((FAIL_COUNT + 1))
    fi
else
    log_warn "Not on wireless debug page — cannot capture pair code dialog"
    log_warn "To capture this screen manually:"
    log_warn "  1. Go to 开发者选项 → 无线调试"
    log_warn "  2. Tap '使用配对码配对'"
    log_warn "  3. When dialog appears, run:"
    log_warn "     $ADB -s $DEVICE shell uiautomator dump /sdcard/window_dump.xml"
    log_warn "     $ADB -s $DEVICE pull /sdcard/window_dump.xml $FIXTURE_DIR/pair_code_dialog.xml"
    FAIL_COUNT=$((FAIL_COUNT + 1))
fi
echo ""

# ============ Summary ============

echo "============================================"
echo "  Dump Summary"
echo "============================================"
echo ""
echo -e "  ${GREEN}Captured : $DUMP_COUNT screens${NC}"
if [ "$FAIL_COUNT" -gt 0 ]; then
    echo -e "  ${YELLOW}Skipped  : $FAIL_COUNT screens${NC}"
fi
echo ""
echo "  Files in $FIXTURE_DIR:"
ls -la "$FIXTURE_DIR"/*.xml 2>/dev/null | grep -E "(settings_home|about_phone|version_info|dev_options|wireless_debug|pair_code_dialog)" | while read -r line; do
    echo "    $line"
done
echo ""

if [ "$DUMP_COUNT" -ge 5 ]; then
    echo -e "${GREEN}=== Dump successful — ready for selector calibration ===${NC}"
elif [ "$DUMP_COUNT" -ge 3 ]; then
    echo -e "${YELLOW}=== Partial dump — some screens need manual capture ===${NC}"
else
    echo -e "${RED}=== Few screens captured — check device and retry ===${NC}"
fi
echo ""

# Screens that need manual capture for WirelessPairEngine development:
#
# If any of these are missing, capture them manually:
#
# settings_home:
#   adb shell am start -a android.settings.SETTINGS
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/settings_home.xml
#
# about_phone:
#   Navigate to 设置 → 关于本机
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/about_phone.xml
#
# version_info:
#   Navigate to 设置 → 关于本机 → 版本信息
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/version_info.xml
#
# dev_options:
#   adb shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/dev_options.xml
#
# wireless_debug:
#   Navigate to 开发者选项 → 无线调试 (tap text, not switch)
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/wireless_debug.xml
#
# wireless_debug_confirm:
#   Toggle 无线调试 OFF→ON to trigger confirm dialog
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/wireless_debug_confirm.xml
#
# pair_code_dialog:
#   On 无线调试 page, tap "使用配对码配对"
#   adb shell uiautomator dump /sdcard/window_dump.xml
#   adb pull /sdcard/window_dump.xml fixtures/oppo/pair_code_dialog.xml
