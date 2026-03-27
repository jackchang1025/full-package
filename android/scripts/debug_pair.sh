#!/bin/bash
# debug_pair.sh — 一键调试 WirelessPairEngine
#
# 用法:
#   bash debug_pair.sh <device_serial>              # 完整流程: build → install → reset → trigger → tail logs
#   bash debug_pair.sh <device_serial> status        # 只查状态
#   bash debug_pair.sh <device_serial> trigger       # 只触发配对 (不重新构建)
#   bash debug_pair.sh <device_serial> reset         # 重置配对状态
#   bash debug_pair.sh <device_serial> logs          # 实时查看日志
#   bash debug_pair.sh <device_serial> install       # 只构建+安装 (不触发)
#
# 关键优势:
#   1. 使用 install -r (保留无障碍服务，不需要重新手动开启)
#   2. 通过 broadcast 触发配对 (跳过心跳冷却)
#   3. 不需要断开 ADB，实时查看日志
#
# 示例:
#   bash debug_pair.sh 192.168.31.249:33939              # 全流程
#   bash debug_pair.sh 192.168.31.249:33939 trigger      # 快速重测
#   bash debug_pair.sh 192.168.31.249:33939 logs         # 只看日志

set -e

DEVICE="${1:?Usage: debug_pair.sh <device_serial> [status|trigger|reset|logs|install]}"
CMD="${2:-full}"
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

log() { echo -e "${GREEN}[debug_pair]${NC} $1"; }
warn() { echo -e "${YELLOW}[debug_pair]${NC} $1"; }
err() { echo -e "${RED}[debug_pair]${NC} $1"; }

# Check device connectivity
check_device() {
    if ! $ADB -s "$DEVICE" shell echo "OK" >/dev/null 2>&1; then
        err "设备 $DEVICE 不可达"
        err "请提供正确的设备序列号 (adb devices 查看)"
        exit 1
    fi
    log "设备连接: ✅ $DEVICE"
}

# Build APK
do_build() {
    log "构建 APK..."
    cd "$PROJECT_DIR"
    ./gradlew assembleDebug 2>&1 | tail -3
    log "构建完成 ✅"
}

# Install (preserve data + accessibility)
do_install() {
    log "安装 APK (保留数据 + 无障碍)..."
    APK=$(find "$PROJECT_DIR/app/build/outputs/apk/debug/" -name "*.apk" | head -1)
    if [ -z "$APK" ]; then
        err "找不到 APK 文件，先执行构建"
        exit 1
    fi
    $ADB -s "$DEVICE" install -r "$APK" 2>&1
    log "安装完成 ✅ (无障碍服务保持开启)"
}

# Show status
do_status() {
    log "查询状态..."
    $ADB -s "$DEVICE" shell am broadcast -a com.vendor.rat.DEBUG_STATUS 2>/dev/null
    sleep 1
    echo ""
    $ADB -s "$DEVICE" shell logcat -d -s DebugReceiver | grep "DEBUG STATUS" -A 20 | tail -15
}

# Reset pairing state
do_reset() {
    log "重置配对状态..."
    $ADB -s "$DEVICE" shell am broadcast -a com.vendor.rat.DEBUG_RESET_PAIR 2>/dev/null
    sleep 1
    $ADB -s "$DEVICE" shell logcat -d -s DebugReceiver | tail -5
    log "配对状态已重置 ✅"
}

# Trigger pairing
do_trigger() {
    log "触发配对流程..."
    $ADB -s "$DEVICE" shell logcat -c 2>/dev/null  # Clear logs
    $ADB -s "$DEVICE" shell am broadcast -a com.vendor.rat.DEBUG_START_PAIR 2>/dev/null
    log "配对已触发 ✅ — 查看实时日志:"
    echo ""
    # Tail logs for key tags
    $ADB -s "$DEVICE" shell logcat -s \
        "DebugReceiver:*" \
        "WirelessPairEngine:*" \
        "AutoEngine/WirelessPairEngine:*" \
        "AdbConnectionManager:*" \
        "EngineManager:*" \
        "AutoEngine:*" \
        "AdbConnection:*" \
        "OpenDevelopmentDelegate:*"
}

# Just tail logs
do_logs() {
    log "实时日志 (Ctrl+C 退出):"
    $ADB -s "$DEVICE" shell logcat -s \
        "DebugReceiver:*" \
        "WirelessPairEngine:*" \
        "AutoEngine/WirelessPairEngine:*" \
        "AdbConnectionManager:*" \
        "EngineManager:*" \
        "KeepHeartThread:*" \
        "AutoEngine:*" \
        "AdbConnection:*"
}

# Full flow
do_full() {
    log "=== 完整调试流程 ==="
    do_build
    do_install
    sleep 2
    do_status
    echo ""
    warn "准备触发配对。按 Enter 继续，或 Ctrl+C 取消..."
    read
    do_trigger
}

# Main
check_device

case "$CMD" in
    full)    do_full ;;
    status)  do_status ;;
    trigger) do_trigger ;;
    reset)   do_reset ;;
    logs)    do_logs ;;
    install) do_build; do_install ;;
    *)       err "未知命令: $CMD"; exit 1 ;;
esac
