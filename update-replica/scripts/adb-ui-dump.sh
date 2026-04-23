#!/usr/bin/env bash
# 从华为设备 dump 当前页面 UI tree，用于诊断 keyword 不匹配问题
# Usage: ./scripts/adb-ui-dump.sh <label>
#   label: 输出文件名前缀（如 "step3-battery-page"）
set -euo pipefail
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="${ADB_DEVICE:-2TV9K24710071129}"
LABEL="${1:-dump}"
OUT_DIR="/tmp/ui-dumps"
mkdir -p "$OUT_DIR"
TS=$(date +%Y%m%d-%H%M%S)
OUT="$OUT_DIR/${LABEL}-${TS}"

"$ADB" -s "$DEVICE" shell "uiautomator dump /sdcard/window_dump.xml" >/dev/null
"$ADB" -s "$DEVICE" pull /sdcard/window_dump.xml "${OUT}.xml" >/dev/null
"$ADB" -s "$DEVICE" exec-out screencap -p > "${OUT}.png"

echo "Dumped:"
echo "  $OUT.xml  ($(wc -l < $OUT.xml) lines)"
echo "  $OUT.png  ($(du -h $OUT.png | cut -f1))"
echo ""
echo "Visible text nodes (top 20):"
grep -oP 'text="[^"]*"' "${OUT}.xml" | sort -u | grep -v 'text=""' | head -20
