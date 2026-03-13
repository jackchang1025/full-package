#!/bin/bash
# 一键构建 + 安装 + 测试脚本
# 用法:
#   ./scripts/build-and-install.sh              # 构建 + 安装
#   ./scripts/build-and-install.sh --test       # 构建 + 安装 + 运行 PowerGenie 测试
#   ./scripts/build-and-install.sh --fast       # 快速模式(跳过混淆)
#   ./scripts/build-and-install.sh --baseline   # 构建 + 安装 + 基线测试(禁用PG)

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
APP_DIR="$(dirname "$SCRIPT_DIR")"
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="192.168.31.162:5555"
ADB_CMD="$ADB -s $DEVICE_SERIAL"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
MAGENTA='\033[0;35m'
BOLD='\033[1m'
NC='\033[0m'

log()    { echo -e "${CYAN}[$(date +%H:%M:%S)]${NC} $1"; }
ok()     { echo -e "${GREEN}[✅]${NC} $1"; }
fail()   { echo -e "${RED}[❌]${NC} $1"; }
warn()   { echo -e "${YELLOW}[⚠️]${NC} $1"; }
header() { echo -e "\n${MAGENTA}${BOLD}══════ $1 ══════${NC}\n"; }

RUN_TEST=false
BASELINE=false
FAST_MODE=false

while [[ $# -gt 0 ]]; do
    case $1 in
        --test)     RUN_TEST=true; shift ;;
        --baseline) RUN_TEST=true; BASELINE=true; shift ;;
        --fast)     FAST_MODE=true; shift ;;
        *)          shift ;;
    esac
done

CONFIG_FILE="$SCRIPT_DIR/config.json"
if [ ! -f "$CONFIG_FILE" ]; then
    fail "配置文件不存在: $CONFIG_FILE"
    exit 1
fi

if [ "$FAST_MODE" = true ]; then
    log "快速模式：创建简化配置（跳过混淆）..."
    FAST_CONFIG_FILE="$APP_DIR/storage/build-config-fast.json"
    python3 -c "
import json
with open('$CONFIG_FILE') as f: c = json.load(f)
c['enable_junk_classes'] = False
c['enable_string_obfuscation'] = False
c['enable_apk_protection'] = False
c['enable_r8_obfuscation'] = False
c['junk_class_count'] = 0
with open('$FAST_CONFIG_FILE', 'w') as f: json.dump(c, f)
" 2>/dev/null || {
    log "快速模式：使用 jq 创建简化配置..."
    jq '.enable_junk_classes=false | .enable_string_obfuscation=false | .enable_apk_protection=false | .enable_r8_obfuscation=false | .junk_class_count=0' "$CONFIG_FILE" > "$FAST_CONFIG_FILE"
}
    CONFIG_FILE="$FAST_CONFIG_FILE"
    ok "快速配置已生成"
fi

header "步骤 1/4: 检查环境"

log "检查 ADB 连接..."
if ! $ADB_CMD shell echo "connected" &>/dev/null; then
    warn "ADB 未连接，尝试连接..."
    $ADB connect "$DEVICE_SERIAL" 2>/dev/null || true
    sleep 2
    if ! $ADB_CMD shell echo "connected" &>/dev/null; then
        fail "ADB 连接失败: $DEVICE_SERIAL"
        exit 1
    fi
fi
ok "ADB 已连接"

DEVICE_MODEL=$($ADB_CMD shell getprop ro.product.model 2>/dev/null | tr -d '\r')
log "设备: $DEVICE_MODEL"

header "步骤 2/4: 构建 APK"

START_TIME=$(date +%s)
log "开始构建..."
cd "$APP_DIR"
BUILD_OUTPUT=$(./vendor/bin/sail artisan apk:build --config="$CONFIG_FILE" 2>&1)
BUILD_EXIT=$?
END_TIME=$(date +%s)
BUILD_DURATION=$((END_TIME - START_TIME))

if [ $BUILD_EXIT -ne 0 ]; then
    fail "构建失败 (耗时 ${BUILD_DURATION}s)"
    echo "$BUILD_OUTPUT" | tail -30
    exit 1
fi
ok "构建成功 (耗时 ${BUILD_DURATION}s)"

APK_PATH=$(find "$APP_DIR/storage/app/public/apk" -name "*.apk" -mmin -2 | sort -t/ -k1 | tail -1)
if [ -z "$APK_PATH" ]; then
    fail "找不到 APK 文件"
    exit 1
fi

PACKAGE_NAME=$(aapt2 dump packagename "$APK_PATH" 2>/dev/null)
APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
log "包名: ${BOLD}$PACKAGE_NAME${NC}"
log "文件: $APK_PATH ($APK_SIZE)"

header "步骤 3/4: 安装 APK"

if $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
    log "卸载旧版本..."
    $ADB_CMD uninstall "$PACKAGE_NAME" &>/dev/null || true
    sleep 2
fi

log "安装中..."
INSTALL_OUTPUT=$($ADB_CMD install "$APK_PATH" 2>&1)
if echo "$INSTALL_OUTPUT" | grep -q "Success"; then
    ok "安装成功"
else
    fail "安装失败: $INSTALL_OUTPUT"
    exit 1
fi

sleep 3
if $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
    ok "验证通过"
else
    fail "安装后包不存在"
    exit 1
fi

header "步骤 4/4: 启动应用"

log "启动应用..."
$ADB_CMD shell am start -n "$PACKAGE_NAME/.MainActivity" &>/dev/null || \
$ADB_CMD shell "monkey -p $PACKAGE_NAME -c android.intent.category.LAUNCHER 1" &>/dev/null || true

echo ""
ok "构建 + 安装完成！"
echo ""
echo -e "  包名: ${BOLD}$PACKAGE_NAME${NC}"
echo -e "  大小: $APK_SIZE"
echo -e "  耗时: ${BUILD_DURATION}s"
echo ""

warn "手动操作提醒:"
echo "  1. 在手机上打开应用"
echo "  2. 如果弹出「电池优化」对话框，点击「允许」"
echo "  3. 打开无障碍模式：设置 → 辅助功能 → 已下载的服务 → 找到应用 → 开启"
echo "  4. 授予悬浮窗权限（如果提示）"
echo ""

if [ "$RUN_TEST" = true ]; then
    echo ""
    header "运行 PowerGenie 测试"
    
    if [ "$BASELINE" = true ]; then
        exec "$SCRIPT_DIR/test-huawei-powergenie.sh" --baseline "$PACKAGE_NAME"
    else
        exec "$SCRIPT_DIR/test-huawei-powergenie.sh" "$PACKAGE_NAME"
    fi
else
    info "后续测试命令:"
    echo "  # 运行断连监控测试"
    echo "  ./scripts/test-huawei-powergenie.sh $PACKAGE_NAME"
    echo ""
    echo "  # 基线测试 (禁用 PowerGenie)"
    echo "  ./scripts/test-huawei-powergenie.sh --baseline $PACKAGE_NAME"
    echo ""
    echo "  # 生成测试报告"
    echo "  ./scripts/test-huawei-powergenie.sh --report $PACKAGE_NAME"
fi

[ "$FAST_MODE" = true ] && [ -f "$FAST_CONFIG_FILE" ] && rm -f "$FAST_CONFIG_FILE"
