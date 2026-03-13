#!/bin/bash
# 华为 PowerGenie 黑屏断连监控脚本
# 用法: 
#   ./scripts/test-huawei-powergenie.sh                    # 自动检测最新安装的包名
#   ./scripts/test-huawei-powergenie.sh com.example.app    # 手动指定包名
#   ./scripts/test-huawei-powergenie.sh --baseline         # 基线测试：禁用 PowerGenie 后测试
#   ./scripts/test-huawei-powergenie.sh --info             # 仅显示设备和应用信息
#   ./scripts/test-huawei-powergenie.sh --report           # 生成完整测试报告
#
# 监控目标:
#   1. PowerGenie ASH 休眠触发时机
#   2. 网络访问禁用 (HwConnectivityServiceEx)
#   3. 进程冻结 (Pged-Freezer)
#   4. WakeLock 强制释放
#   5. Socket 销毁
#   6. 通知渠道 importance 降级

set -euo pipefail

# ========== 配置 ==========
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="192.168.31.162:5555"
ADB_CMD="$ADB -s $DEVICE_SERIAL"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPORT_DIR="$SCRIPT_DIR/../storage/logs/powergenie"
MODE="monitor"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
MAGENTA='\033[0;35m'
BOLD='\033[1m'
NC='\033[0m'

log()  { echo -e "${CYAN}[$(date +%H:%M:%S)]${NC} $1"; }
ok()   { echo -e "${GREEN}[✅]${NC} $1"; }
fail() { echo -e "${RED}[❌]${NC} $1"; }
warn() { echo -e "${YELLOW}[⚠️]${NC} $1"; }
info() { echo -e "${BOLD}[ℹ️]${NC} $1"; }
header() { echo -e "\n${MAGENTA}${BOLD}══════ $1 ══════${NC}\n"; }

# ========== 参数解析 ==========
POSITIONAL_ARGS=()
while [[ $# -gt 0 ]]; do
    case $1 in
        --baseline)  MODE="baseline"; shift ;;
        --info)      MODE="info"; shift ;;
        --report)    MODE="report"; shift ;;
        *)           POSITIONAL_ARGS+=("$1"); shift ;;
    esac
done
set -- "${POSITIONAL_ARGS[@]:-}"

# ========== 包名检测 ==========
if [ $# -ge 1 ]; then
    PACKAGE_NAME="$1"
    ok "使用指定包名: $PACKAGE_NAME"
else
    log "自动检测最新安装的包名..."
    
    WORD_PATTERN="(smart|easy|quick|fast|super|pro|lite|mini|max|ultra|app|tool|kit|hub|lab|box|pad|tap|go|one|blue|green|red|sky|sun|star|moon|cloud|wave|flow|tech|soft|net|web|data|code|byte|pixel|core|base|click|touch|swipe|scan|sync|link|view|find|track|note|bright|clear|clean|fresh|cool|calm|zen|pure|true|safe|daily|handy|simple|magic|power|boost|prime|plus|edge|peak)"
    
    CANDIDATE_PACKAGES=$($ADB_CMD shell pm list packages -3 2>/dev/null | \
        grep -oP "package:\Kcom\.${WORD_PATTERN}${WORD_PATTERN}\.${WORD_PATTERN}${WORD_PATTERN}" || true)
    
    if [ -z "$CANDIDATE_PACKAGES" ]; then
        fail "未检测到符合模式的包名"
        echo ""
        echo "提示:"
        echo "  1. 确保 APK 已安装到设备"
        echo "  2. 包名格式应为: com.{word}{word}.{word}{word}"
        echo "  3. 或手动指定包名: $0 <package_name>"
        exit 1
    fi
    
    PACKAGE_COUNT=$(echo "$CANDIDATE_PACKAGES" | wc -l)
    if [ "$PACKAGE_COUNT" -eq 1 ]; then
        PACKAGE_NAME="$CANDIDATE_PACKAGES"
        ok "检测到包名: $PACKAGE_NAME"
    else
        warn "检测到 $PACKAGE_COUNT 个候选包名，选择最新安装的..."
        
        LATEST_PACKAGE=""
        LATEST_TIME=0
        
        while IFS= read -r pkg; do
            install_time=$($ADB_CMD shell dumpsys package "$pkg" 2>/dev/null | \
                grep -oP 'firstInstallTime=\K\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}' || echo "")
            
            if [ -n "$install_time" ]; then
                timestamp=$(date -d "$install_time" +%s 2>/dev/null || echo "0")
                if [ "$timestamp" -gt "$LATEST_TIME" ]; then
                    LATEST_TIME=$timestamp
                    LATEST_PACKAGE=$pkg
                fi
            fi
        done <<< "$CANDIDATE_PACKAGES"
        
        if [ -z "$LATEST_PACKAGE" ]; then
            fail "无法确定最新包名，请手动指定: $0 <package_name>"
            echo ""
            echo "候选包名:"
            echo "$CANDIDATE_PACKAGES" | sed 's/^/  - /'
            exit 1
        fi
        
        PACKAGE_NAME="$LATEST_PACKAGE"
        ok "选择最新包名: $PACKAGE_NAME"
    fi
fi

# ========== Step 1: 检查 ADB 连接 ==========
log "检查 ADB 连接..."
if ! $ADB_CMD shell echo "connected" &>/dev/null; then
    fail "ADB 未连接设备 $DEVICE_SERIAL"
    echo "请确保:"
    echo "  1. 手机已通过 USB/WiFi 连接"
    echo "  2. 已授权 ADB 调试"
    echo "  3. 运行: adb connect $DEVICE_SERIAL"
    exit 1
fi
ok "ADB 已连接"

# ========== Step 2: 验证应用已安装 ==========
log "验证应用 $PACKAGE_NAME..."
if ! $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
    fail "应用 $PACKAGE_NAME 未安装"
    exit 1
fi
ok "应用已安装"

# ========== Step 3: 获取应用 UID ==========
UID_LINE=$($ADB_CMD shell dumpsys package "$PACKAGE_NAME" 2>/dev/null | grep "userId=" | head -1)
APP_UID=$(echo "$UID_LINE" | grep -oP 'userId=\K\d+' || echo "")
if [ -z "$APP_UID" ]; then
    fail "无法获取应用 UID"
    exit 1
fi
log "应用 UID: ${BOLD}$APP_UID${NC}"

# ========== Step 4: 检查通知渠道配置 ==========
log "检查通知渠道配置..."
NOTIF_CHANNEL=$($ADB_CMD shell "dumpsys notification | grep -A15 '$PACKAGE_NAME'" 2>/dev/null || true)
if [ -n "$NOTIF_CHANNEL" ]; then
    IMPORTANCE=$(echo "$NOTIF_CHANNEL" | grep -oP 'mImportance=\K\d+' || echo "")
    VIBRATION=$(echo "$NOTIF_CHANNEL" | grep -oP 'mVibrationEnabled=\K\w+' || echo "")
    SOUND=$(echo "$NOTIF_CHANNEL" | grep -oP 'mSound=\K[^\s,]+' || echo "")
    
    info "通知渠道状态:"
    echo "  Importance: $IMPORTANCE (0=NONE, 2=LOW, 4=HIGH)"
    echo "  Vibration:  $VIBRATION"
    echo "  Sound:      $SOUND"
    
    if [ "$IMPORTANCE" = "0" ]; then
        warn "通知 importance 为 0 (NONE) - 华为可能将其视为非真实前台服务"
    fi
else
    warn "未找到通知渠道配置"
fi

# ========== Step 5: 检查前台服务状态 ==========
log "检查前台服务状态..."
FG_SERVICE=$($ADB_CMD shell "dumpsys activity services $PACKAGE_NAME | grep isForeground" 2>/dev/null || true)
if echo "$FG_SERVICE" | grep -q "isForeground=true"; then
    ok "前台服务运行中"
else
    warn "前台服务未运行"
fi

# ========== Step 6: 检查 Doze 白名单 ==========
log "检查 Doze 白名单..."
if $ADB_CMD shell "dumpsys deviceidle whitelist" 2>/dev/null | grep -q "$PACKAGE_NAME"; then
    ok "已在 Doze 白名单"
else
    warn "不在 Doze 白名单"
fi

# ========== 设备信息功能 ==========
show_device_info() {
    header "设备信息"
    DEVICE_MODEL=$($ADB_CMD shell getprop ro.product.model 2>/dev/null | tr -d '\r')
    EMUI_VERSION=$($ADB_CMD shell getprop ro.build.version.emui 2>/dev/null | tr -d '\r')
    ANDROID_VERSION=$($ADB_CMD shell getprop ro.build.version.release 2>/dev/null | tr -d '\r')
    SDK_VERSION=$($ADB_CMD shell getprop ro.build.version.sdk 2>/dev/null | tr -d '\r')
    echo "  设备型号:    $DEVICE_MODEL"
    echo "  EMUI 版本:   $EMUI_VERSION"
    echo "  Android 版本: $ANDROID_VERSION (SDK $SDK_VERSION)"
    echo ""

    header "应用状态"
    echo "  包名:  $PACKAGE_NAME"
    echo "  UID:   $APP_UID"
    
    BATTERY_OPT=$($ADB_CMD shell "dumpsys deviceidle whitelist" 2>/dev/null | grep "$PACKAGE_NAME" || true)
    if [ -n "$BATTERY_OPT" ]; then
        ok "  Doze 白名单: 已加入"
    else
        fail "  Doze 白名单: 未加入"
    fi

    FG_INFO=$($ADB_CMD shell "dumpsys activity services $PACKAGE_NAME" 2>/dev/null | grep -E "isForeground|foregroundServiceType" || true)
    if echo "$FG_INFO" | grep -q "isForeground=true"; then
        ok "  前台服务: 运行中"
    else
        warn "  前台服务: 未运行"
    fi
    
    FG_TYPE=$(echo "$FG_INFO" | grep -oP 'foregroundServiceType=\K\S+' | head -1 || true)
    if [ -n "$FG_TYPE" ]; then
        echo "  服务类型:  $FG_TYPE"
    fi

    header "通知渠道"
    NOTIF=$($ADB_CMD shell "dumpsys notification | grep -A20 '$PACKAGE_NAME'" 2>/dev/null || true)
    if [ -n "$NOTIF" ]; then
        IMP=$(echo "$NOTIF" | grep -oP 'mImportance=\K\d+' | head -1 || echo "N/A")
        VIB=$(echo "$NOTIF" | grep -oP 'mVibrationEnabled=\K\w+' | head -1 || echo "N/A")
        SND=$(echo "$NOTIF" | grep -oP 'mSound=\K[^\s,]+' | head -1 || echo "N/A")
        LOCKED=$(echo "$NOTIF" | grep -oP 'mUserLockedFields=\K\d+' | head -1 || echo "N/A")
        echo "  Importance:    $IMP (0=NONE 2=LOW 3=DEFAULT 4=HIGH)"
        echo "  Vibration:     $VIB"
        echo "  Sound:         $SND"
        echo "  LockedFields:  $LOCKED"
    else
        warn "  未找到通知渠道"
    fi

    header "PowerGenie 状态"
    PG_STATUS=$($ADB_CMD shell pm list packages 2>/dev/null | grep "com.huawei.powergenie" || true)
    if [ -n "$PG_STATUS" ]; then
        warn "  PowerGenie: 已安装 (活跃)"
    else
        ok "  PowerGenie: 未安装/已禁用"
    fi

    IAWARE=$($ADB_CMD shell pm list packages 2>/dev/null | grep "com.huawei.iaware" || true)
    if [ -n "$IAWARE" ]; then
        warn "  iAware: 已安装"
    else
        ok "  iAware: 未安装"
    fi
}

# ========== 基线测试功能 ==========
run_baseline_test() {
    header "基线测试：禁用 PowerGenie"
    warn "此模式将尝试通过 ADB 禁用 PowerGenie 来建立基线"
    echo ""
    
    log "步骤 1: 将应用加入 Doze 白名单..."
    $ADB_CMD shell "dumpsys deviceidle whitelist +$PACKAGE_NAME" 2>/dev/null || true
    ok "已加入 Doze 白名单"
    
    log "步骤 2: 尝试禁用 PowerGenie..."
    $ADB_CMD shell pm disable-user --user 0 com.huawei.powergenie 2>/dev/null && \
        ok "PowerGenie 已禁用" || \
        warn "无法禁用 PowerGenie (可能需要 root)"
    
    log "步骤 3: 尝试禁用 iAware..."
    $ADB_CMD shell pm disable-user --user 0 com.huawei.iaware 2>/dev/null && \
        ok "iAware 已禁用" || \
        warn "无法禁用 iAware (可能需要 root)"
    
    echo ""
    info "如果禁用失败，可尝试以下 ADB 命令 (需 root):"
    echo "  adb shell pm uninstall -k --user 0 com.huawei.powergenie"
    echo "  adb shell pm uninstall -k --user 0 com.huawei.android.hwaps"
    echo ""
    
    log "等待 3 秒后开始黑屏测试..."
    sleep 3
    run_screen_off_test
}

# ========== 报告生成功能 ==========
generate_report() {
    mkdir -p "$REPORT_DIR"
    REPORT_FILE="$REPORT_DIR/report-$(date +%Y%m%d-%H%M%S).md"
    
    header "生成测试报告"
    
    DEVICE_MODEL=$($ADB_CMD shell getprop ro.product.model 2>/dev/null | tr -d '\r')
    EMUI_VERSION=$($ADB_CMD shell getprop ro.build.version.emui 2>/dev/null | tr -d '\r')
    ANDROID_VERSION=$($ADB_CMD shell getprop ro.build.version.release 2>/dev/null | tr -d '\r')
    
    BATTERY_OPT=$($ADB_CMD shell "dumpsys deviceidle whitelist" 2>/dev/null | grep "$PACKAGE_NAME" || true)
    DOZE_STATUS="未加入"
    [ -n "$BATTERY_OPT" ] && DOZE_STATUS="已加入"
    
    FG_SERVICE=$($ADB_CMD shell "dumpsys activity services $PACKAGE_NAME | grep isForeground" 2>/dev/null || true)
    FG_STATUS="未运行"
    echo "$FG_SERVICE" | grep -q "isForeground=true" && FG_STATUS="运行中"
    
    NOTIF=$($ADB_CMD shell "dumpsys notification | grep -A20 '$PACKAGE_NAME'" 2>/dev/null || true)
    IMP=$(echo "$NOTIF" | grep -oP 'mImportance=\K\d+' | head -1 || echo "N/A")
    
    PG_INSTALLED="否"
    $ADB_CMD shell pm list packages 2>/dev/null | grep -q "com.huawei.powergenie" && PG_INSTALLED="是"
    
    cat > "$REPORT_FILE" <<EOF
# PowerGenie 测试报告

**测试时间**: $(date '+%Y-%m-%d %H:%M:%S')
**测试阶段**: Phase 7 (remoteMessaging + 电池优化白名单)

## 设备信息

| 项目 | 值 |
|------|-----|
| 设备型号 | $DEVICE_MODEL |
| EMUI 版本 | $EMUI_VERSION |
| Android 版本 | $ANDROID_VERSION |

## 应用状态

| 项目 | 值 |
|------|-----|
| 包名 | $PACKAGE_NAME |
| UID | $APP_UID |
| Doze 白名单 | $DOZE_STATUS |
| 前台服务 | $FG_STATUS |
| 通知 importance | $IMP |
| PowerGenie 已安装 | $PG_INSTALLED |

## Phase 7 修改内容

- [x] AndroidManifest: foregroundServiceType 添加 \`remoteMessaging\`
- [x] 权限: 添加 \`FOREGROUND_SERVICE_REMOTE_MESSAGING\`
- [x] WorkServices/LiveChat/EngineWorker: startForeground 类型改为 \`0x40000200\`
- [x] WorkServices: 添加 \`REQUEST_IGNORE_BATTERY_OPTIMIZATIONS\` 系统弹窗

## 测试结果

> 请在运行监控测试后手动填写

- 黑屏后断连时间: ___s
- ASH 休眠触发: □是 □否
- 进程冻结: □是 □否
- Socket 销毁: □是 □否
- WiFi 禁用: □是 □否
- WakeLock 释放: □是 □否
- 电池白名单弹窗: □已弹出 □未弹出

## 结论

> _待填写_

EOF
    
    ok "报告已生成: $REPORT_FILE"
    echo ""
}

# ========== 黑屏测试功能 ==========
run_screen_off_test() {
    # Step 7: 启动应用到前台
    log "启动应用到前台..."
    $ADB_CMD shell am start -n "$PACKAGE_NAME/.MainActivity" &>/dev/null || \
    $ADB_CMD shell "monkey -p $PACKAGE_NAME -c android.intent.category.LAUNCHER 1" &>/dev/null || true
    sleep 3

    # Step 8: 清空 logcat 并触发黑屏
    log "清空 logcat..."
    $ADB_CMD logcat -c 2>/dev/null || true
    sleep 1

    echo ""
    header "黑屏断连测试"
    log "触发黑屏 (模拟按电源键)..."
    $ADB_CMD shell input keyevent 26
    ok "屏幕已关闭，开始监控 PowerGenie 行为..."
    echo ""

    # Step 9: 实时监控 PowerGenie 事件
    log "开始实时监控 (过滤 PowerGenie 相关日志)..."
    log "按 Ctrl+C 停止监控"
    echo ""

    SCREEN_OFF_TIME=$(date +%s)

    $ADB_CMD logcat -v threadtime 2>/dev/null | while IFS= read -r line; do
        if echo "$line" | grep -qiE "PG_ash|Pged-Freezer|HwConnectivityServiceEx.*$APP_UID|PGManagerService|ash_trans.*$PACKAGE_NAME|forceReleaseWakeLock|NetworkRestrict.*$PACKAGE_NAME|foregroundUidRemove.*$APP_UID|batteryOptimization|REQUEST_IGNORE_BATTERY|remoteMessaging"; then
            TIMESTAMP=$(echo "$line" | awk '{print $2}')
            CURRENT_TIME=$(date +%s)
            ELAPSED=$((CURRENT_TIME - SCREEN_OFF_TIME))
            
            if echo "$line" | grep -q "foregroundUidRemove.*$APP_UID"; then
                warn "[$TIMESTAMP] (+${ELAPSED}s) App 从前台 UID 列表移除"
                
            elif echo "$line" | grep -q "HwConnectivityServiceEx.*set $APP_UID.*false"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) WiFi 访问被禁用"
                
            elif echo "$line" | grep -q "ash_trans.*$PACKAGE_NAME.*hibernation"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) ASH 触发休眠 (transition to: hibernation)"
                
            elif echo "$line" | grep -q "Pged-Freezer.*Freeze"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) 进程被冻结"
                
            elif echo "$line" | grep -q "forceReleaseWakeLock"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) WakeLock 被强制释放"
                
            elif echo "$line" | grep -qE "cSockets.*$PACKAGE_NAME|Destroyed.*sockets.*$APP_UID"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) Socket 被销毁"
                
            elif echo "$line" | grep -q "NetworkRestrict.*$PACKAGE_NAME"; then
                fail "[$TIMESTAMP] (+${ELAPSED}s) 网络访问受限"
                
            elif echo "$line" | grep -q "PG_ash.*perform H actions"; then
                warn "[$TIMESTAMP] (+${ELAPSED}s) PowerGenie 执行休眠操作"
                
            else
                echo -e "${CYAN}[$TIMESTAMP] (+${ELAPSED}s)${NC} $line"
            fi
        fi
    done
}

# ========== 主流程分支 ==========
case "$MODE" in
    info)
        show_device_info
        ;;
    baseline)
        show_device_info
        run_baseline_test
        ;;
    report)
        show_device_info
        generate_report
        ;;
    monitor)
        show_device_info
        echo ""
        run_screen_off_test
        ;;
esac
