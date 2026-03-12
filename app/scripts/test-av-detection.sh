#!/bin/bash
# APK 构建 + 安装 + 病毒检测自动化测试脚本
# 用法: ./scripts/test-av-detection.sh [config_path]
#
# 检测逻辑（三重检测）:
#   1. pm list packages — 包是否被自动卸载
#   2. logcat 分析 — 华为 AntiVirus 扫描链关键词匹配
#   3. dumpsys notification — 检查通知栏是否有病毒/安全警告
#
# 华为 AV 扫描链（logcat 逆向分析）:
#   AntiVirusReceiver.onReceive
#   → AntiVirusScanService（启动扫描进程 security_scan）
#   → AvlAntivirusEngine.checkPluginAntivirusEngine
#   → AiProtectionPlugin.setStaticVirusScanResult
#   → VirusNotifyService（弹出病毒通知 = 报毒确认）
#   → store.getControlStrategy（云端信誉查询）

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
APP_DIR="$(dirname "$SCRIPT_DIR")"
CONFIG_PATH="${1:-/var/www/html/storage/apk-build-config.json}"
SCAN_WAIT_SECONDS=60

# ADB 配置 (WSL NAT 模式)
WINDOWS_HOST=$(ip route show default | awk '{print $3}')
export ADB_SERVER_SOCKET="tcp:${WINDOWS_HOST}:5037"
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_SERIAL="2TV9K24710071129"
ADB_CMD="$ADB -s $DEVICE_SERIAL"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m'

log()  { echo -e "${CYAN}[$(date +%H:%M:%S)]${NC} $1"; }
ok()   { echo -e "${GREEN}[✅ PASS]${NC} $1"; }
fail() { echo -e "${RED}[❌ FAIL]${NC} $1"; }
warn() { echo -e "${YELLOW}[⚠️  WARN]${NC} $1"; }
info() { echo -e "${BOLD}[ℹ️  INFO]${NC} $1"; }

# ========== Step 1: 检查 ADB 连接 ==========
log "检查 ADB 连接..."
if ! $ADB_CMD shell echo "connected" &>/dev/null; then
    fail "ADB 未连接设备 $DEVICE_SERIAL"
    echo "请确保:"
    echo "  1. Windows 端已运行 adb start-server"
    echo "  2. 手机已通过 USB 连接并授权调试"
    exit 1
fi
ok "ADB 已连接"

# ========== Step 2: 构建 APK ==========
log "开始构建 APK..."
cd "$APP_DIR"
BUILD_OUTPUT=$(./vendor/bin/sail artisan apk:build --config="$CONFIG_PATH" --no-interaction 2>&1)
BUILD_EXIT=$?

if [ $BUILD_EXIT -ne 0 ]; then
    fail "APK 构建失败 (exit code: $BUILD_EXIT)"
    echo "$BUILD_OUTPUT" | tail -20
    exit 1
fi
ok "APK 构建成功"

# ========== Step 3: 动态定位 APK 输出文件 ==========
# 从构建输出中提取路径，或查找最新的 APK 文件
APK_OUTPUT=""
OUTPUT_LINE=$(echo "$BUILD_OUTPUT" | grep -oP '输出路径\s*\|\s*\K/storage/apk/[^\s|]+' || true)
if [ -n "$OUTPUT_LINE" ]; then
    # 将 /storage/apk/... 转换为实际文件系统路径
    APK_OUTPUT="$APP_DIR/storage/app/public/apk/${OUTPUT_LINE#/storage/apk/}"
fi

# fallback: 查找最近 2 分钟内修改的 APK
if [ -z "$APK_OUTPUT" ] || [ ! -f "$APK_OUTPUT" ]; then
    APK_OUTPUT=$(find "$APP_DIR/storage/app/public/apk" -name "*.apk" -mmin -2 -size +1M 2>/dev/null | sort -t/ -k1 | tail -1 || true)
fi

if [ -z "$APK_OUTPUT" ] || [ ! -f "$APK_OUTPUT" ]; then
    fail "找不到构建输出的 APK 文件"
    echo "$BUILD_OUTPUT" | tail -10
    exit 1
fi

APK_SIZE=$(stat -c%s "$APK_OUTPUT")
log "APK 文件: $APK_OUTPUT"
log "APK 大小: $(numfmt --to=iec $APK_SIZE)"

# ========== Step 4: 提取实际包名 ==========
PACKAGE_NAME=$(aapt2 dump packagename "$APK_OUTPUT" 2>/dev/null || echo "")
if [ -z "$PACKAGE_NAME" ]; then
    fail "无法提取包名 (aapt2 dump 失败)"
    exit 1
fi
log "实际包名: ${BOLD}$PACKAGE_NAME${NC}"

# ========== Step 5: 卸载旧版本 ==========
if $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
    log "卸载旧版本 $PACKAGE_NAME..."
    $ADB_CMD uninstall "$PACKAGE_NAME" &>/dev/null || true
    sleep 2
fi

# ========== Step 6: 清空 logcat（确保只捕获本次安装的日志）==========
log "清空 logcat..."
$ADB_CMD logcat -c 2>/dev/null || true
sleep 1

# ========== Step 7: 安装 APK ==========
log "安装 APK..."
INSTALL_OUTPUT=$($ADB_CMD install "$APK_OUTPUT" 2>&1)
if echo "$INSTALL_OUTPUT" | grep -q "Success"; then
    ok "APK 安装成功"
elif echo "$INSTALL_OUTPUT" | grep -q "INSTALL_PARSE_FAILED"; then
    fail "APK 安装失败: INSTALL_PARSE_FAILED (ZIP 结构损坏)"
    echo "$INSTALL_OUTPUT"
    exit 1
else
    fail "APK 安装失败: $INSTALL_OUTPUT"
    exit 1
fi

# ========== Step 8: 验证安装后包存在 ==========
sleep 3
RETRY=0
PACKAGE_FOUND=false
while [ $RETRY -lt 3 ]; do
    if $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
        PACKAGE_FOUND=true
        break
    fi
    RETRY=$((RETRY + 1))
    sleep 2
done
if [ "$PACKAGE_FOUND" = false ]; then
    fail "安装后包不存在 — 被即时拦截"
    exit 1
fi
ok "安装后包存在"

# ========== Step 9: 等待华为安全扫描 ==========
log "等待华为安全扫描 (${SCAN_WAIT_SECONDS}s)..."
EARLY_DETECT=false
for i in $(seq 1 $SCAN_WAIT_SECONDS); do
    printf "\r  扫描中... %d/%d" "$i" "$SCAN_WAIT_SECONDS"

    # 每 10 秒做一次早期检测
    if [ $((i % 10)) -eq 0 ]; then
        # 只检查确定性信号：EXIST_MALICIOUS_APP（包含包名）
        if $ADB_CMD logcat -d 2>/dev/null | grep -q "EXIST_MALICIOUS_APP" 2>/dev/null; then
            EARLY_DETECT=true
            echo ""
            warn "早期检测到 EXIST_MALICIOUS_APP (${i}s)"
            break
        fi
    fi

    sleep 1
done
if [ "$EARLY_DETECT" = false ]; then
    echo ""
fi

# ========== Step 10: 三重检测 ==========
log "执行三重检测..."

# --- 检测 1: 包是否还在（重试 3 次防止 ADB 连接抖动误判）---
PACKAGE_EXISTS=true
PKG_CHECK_RETRIES=0
while [ $PKG_CHECK_RETRIES -lt 3 ]; do
    if $ADB_CMD shell pm list packages 2>/dev/null | grep -q "$PACKAGE_NAME"; then
        PACKAGE_EXISTS=true
        break
    fi
    PACKAGE_EXISTS=false
    PKG_CHECK_RETRIES=$((PKG_CHECK_RETRIES + 1))
    sleep 2
done

# --- 检测 2: logcat AV 扫描链分析 ---
# 收集安装后的所有日志
ALL_LOGS=$($ADB_CMD logcat -d 2>/dev/null || true)

# 2a: 华为 AntiVirus 扫描链关键词（不依赖包名）
AV_SCAN_DETECTED=false
AV_SCAN_LOGS=$(echo "$ALL_LOGS" | grep -iE "VirusNotifyService|setStaticVirusScanResult|AntiVirusScanService.*$PACKAGE_NAME|security_scan.*$PACKAGE_NAME|AntiVirusReceiver.*$PACKAGE_NAME" || true)
if [ -n "$AV_SCAN_LOGS" ]; then
    AV_SCAN_DETECTED=true
fi

# 2b: 病毒通知服务（最强信号 — 这个服务启动 = 确认弹出病毒通知）
VIRUS_NOTIFY=false
if echo "$ALL_LOGS" | grep -q "VirusNotifyService"; then
    VIRUS_NOTIFY=true
fi

# 2c: AiProtection 结果（静态扫描结果回调）
AI_PROTECTION=false
if echo "$ALL_LOGS" | grep -q "setStaticVirusScanResult"; then
    AI_PROTECTION=true
fi

# 2d: 云端信誉查询（包含包名的 URL 编码）
CLOUD_QUERY=false
CLOUD_LOGS=$(echo "$ALL_LOGS" | grep -E "getControlStrategy" | grep -i "$PACKAGE_NAME" || true)
if [ -n "$CLOUD_LOGS" ]; then
    CLOUD_QUERY=true
fi

# 2e: 通用恶意软件标记
GENERIC_AV=false
GENERIC_LOGS=$(echo "$ALL_LOGS" | grep -iE "MaliciousApp.*$PACKAGE_NAME|UNINSTALL_MALICIOUS.*$PACKAGE_NAME" || true)
if [ -n "$GENERIC_LOGS" ]; then
    GENERIC_AV=true
fi

# --- 检测 3: 通知栏检查 ---
NOTIFICATION_VIRUS=false
NOTIF_DUMP=$($ADB_CMD shell dumpsys notification --noredact 2>/dev/null || true)
# 检查华为安全中心的活跃通知中是否包含病毒/风险/恶意相关内容
if echo "$NOTIF_DUMP" | grep -A20 "com.huawei.systemmanager" | grep -qiE "病毒|恶意|风险|威胁|virus|malicious|harmful|threat|risk"; then
    NOTIFICATION_VIRUS=true
fi
# 也检查 trustspace（华为安全空间）
if echo "$NOTIF_DUMP" | grep -A20 "trustspace" | grep -qiE "病毒|恶意|风险|威胁|virus|malicious|harmful|threat|risk"; then
    NOTIFICATION_VIRUS=true
fi

# ========== Step 11: 综合判定 ==========
echo ""
log "========== 检测结果详情 =========="

# 信号汇总
info "检测信号:"
echo "  包是否存在:           $([ "$PACKAGE_EXISTS" = true ] && echo '✅ 存在' || echo '❌ 已卸载')"
echo "  AV 扫描链触发:        $([ "$AV_SCAN_DETECTED" = true ] && echo '⚠️  是' || echo '✅ 否')"
echo "  VirusNotifyService:   $([ "$VIRUS_NOTIFY" = true ] && echo '❌ 已启动（确认弹毒）' || echo '✅ 未启动')"
echo "  AiProtection 结果:    $([ "$AI_PROTECTION" = true ] && echo '⚠️  有结果回调' || echo '✅ 无')"
echo "  云端信誉查询:         $([ "$CLOUD_QUERY" = true ] && echo '⚠️  已查询' || echo '— 未检测到')"
echo "  通知栏病毒提示:       $([ "$NOTIFICATION_VIRUS" = true ] && echo '❌ 有病毒通知' || echo '✅ 无')"
echo "  通用恶意标记:         $([ "$GENERIC_AV" = true ] && echo '❌ 有' || echo '✅ 无')"

# 判定逻辑:
#   FAIL 条件（任一触发）:
#     - 包被卸载
#     - VirusNotifyService 启动（= 弹出病毒通知）
#     - 通知栏有病毒提示
#     - 通用恶意标记
#   WARN 条件:
#     - AV 扫描链触发但无病毒通知（扫描了但判定为安全）
#     - 云端查询了但无后续动作
#   PASS 条件:
#     - 包存在 + 无病毒通知 + 无恶意标记

RESULT="PASS"

if [ "$PACKAGE_EXISTS" = false ]; then
    RESULT="FAIL"
    fail "包 $PACKAGE_NAME 已被自动卸载 — 被检测为病毒! 💀"
elif [ "$VIRUS_NOTIFY" = true ]; then
    RESULT="FAIL"
    fail "VirusNotifyService 已启动 — 华为弹出了病毒通知! 💀"
elif [ "$NOTIFICATION_VIRUS" = true ]; then
    RESULT="FAIL"
    fail "通知栏检测到病毒/安全警告! 💀"
elif [ "$GENERIC_AV" = true ]; then
    RESULT="FAIL"
    fail "检测到通用恶意软件标记! 💀"
elif [ "$AV_SCAN_DETECTED" = true ] && [ "$VIRUS_NOTIFY" = false ]; then
    RESULT="PASS"
    warn "AV 扫描链触发但未弹毒通知 — 扫描后判定为安全"
    ok "包 $PACKAGE_NAME 通过华为安全扫描 🎉"
else
    ok "包 $PACKAGE_NAME 未触发任何 AV 信号 — 完全通过! 🎉"
fi

# ========== Step 12: 详细 AV 日志（仅在检测到信号时显示）==========
if [ "$AV_SCAN_DETECTED" = true ] || [ "$VIRUS_NOTIFY" = true ] || [ "$GENERIC_AV" = true ]; then
    echo ""
    log "华为 AV 相关日志:"
    echo "$ALL_LOGS" | grep -iE "AntiVirus|VirusNotify|AiProtection|security_scan|getControlStrategy|MaliciousApp|UNINSTALL_MALICIOUS" | tail -30
fi

# ========== Step 13: APK 特征摘要 ==========
echo ""
log "APK 特征摘要:"
python3 << PYEOF
import zipfile, os, subprocess
apk_path = '$APK_OUTPUT'
z = zipfile.ZipFile(apk_path)
total = len(z.infolist())
fake = len([i for i in z.infolist() if '\\\\' in i.filename or i.filename.startswith('/') or '//' in i.filename])
v1 = len([i for i in z.infolist() if i.filename.endswith(('.SF', '.RSA', '.MF'))])
manifest = [i for i in z.infolist() if i.filename == 'AndroidManifest.xml']
m_size = manifest[0].file_size if manifest else 0
dex = [i for i in z.infolist() if i.filename.endswith('.dex') and '/' not in i.filename]
apk_size = os.path.getsize(apk_path)

# 提取签名证书 DN
try:
    cert_out = subprocess.check_output(
        ['apksigner', 'verify', '--print-certs', apk_path],
        stderr=subprocess.STDOUT, timeout=10
    ).decode()
    cert_dn = ''
    for line in cert_out.splitlines():
        if 'certificate DN' in line:
            cert_dn = line.split(':', 1)[1].strip()
            break
except Exception:
    cert_dn = '(无法提取)'

print(f"  APK 大小:       {apk_size/1024/1024:.1f} MB")
print(f"  ZIP 条目:       {total} (假条目: {fake})")
print(f"  Manifest 大小:  {m_size/1024/1024:.0f} MB (解压后)")
print(f"  DEX 文件:       {len(dex)}")
print(f"  V1 签名文件:    {v1}")
print(f"  签名证书:       {cert_dn}")
print(f"  包名:           $PACKAGE_NAME")
z.close()
PYEOF

echo ""
echo "=============================="
if [ "$RESULT" = "PASS" ]; then
    ok "最终结果: 未报毒 ✅"
else
    fail "最终结果: 被检测为病毒 ❌"
fi
echo "=============================="

exit $([ "$RESULT" = "PASS" ] && echo 0 || echo 1)
