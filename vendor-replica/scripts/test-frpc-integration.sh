#!/bin/bash
# test-frpc-integration.sh — frpc 集成端到端验证
# 用法: bash scripts/test-frpc-integration.sh <DEVICE_IP>

set -e

ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_IP="${1:-192.168.31.249}"
DEVICE="${DEVICE_IP}:5555"
PACKAGE="com.guard.wallet"

echo "=== frpc Integration Verification ==="
echo ""

# 1. 检查 APK 中的 libfrpc.so
echo "[1/6] 检查 APK 是否包含 libfrpc.so..."
APK="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK" ]; then
    FRPC_COUNT=$(unzip -l "$APK" | grep -c "libfrpc.so" || true)
    if [ "$FRPC_COUNT" -ge 1 ]; then
        echo "  OK APK 包含 libfrpc.so ($FRPC_COUNT 个架构)"
    else
        echo "  FAIL APK 中未找到 libfrpc.so"
        exit 1
    fi
else
    echo "  SKIP APK 未构建"
fi

# 2. 连接设备
echo "[2/6] 连接设备 $DEVICE..."
$ADB connect "$DEVICE" 2>/dev/null || true
sleep 1

# 3. 检查设备上的 libfrpc.so
echo "[3/6] 检查设备上的 libfrpc.so..."
SO_EXISTS=$($ADB -s "$DEVICE" shell "ls /data/app/*${PACKAGE}*/lib/*/libfrpc.so 2>/dev/null | head -1" || echo "")
if [ -n "$SO_EXISTS" ]; then
    echo "  OK libfrpc.so 存在于设备: $SO_EXISTS"
else
    echo "  WARN 设备上未找到 libfrpc.so (可能未安装)"
fi

# 4. 检查 frpc 进程
echo "[4/6] 检查 frpc 进程..."
FRPC_PROC=$($ADB -s "$DEVICE" shell "ps -ef 2>/dev/null | grep libfrpc | grep -v grep" || echo "")
if [ -n "$FRPC_PROC" ]; then
    echo "  OK frpc 进程运行中"
    echo "  $FRPC_PROC"
else
    echo "  WARN frpc 进程未运行 (可能缺少 frpc.ini)"
fi

# 5. 检查端口 7400
echo "[5/6] 检查 frpc 管理端口 7400..."
PORT_CHECK=$($ADB -s "$DEVICE" shell "cat /proc/net/tcp 2>/dev/null | grep '1CE8'" || echo "")
if [ -n "$PORT_CHECK" ]; then
    echo "  OK 端口 7400 已被占用 (frpc admin)"
else
    echo "  WARN 端口 7400 未被占用"
fi

# 6. 检查 frpc.ini
echo "[6/6] 检查 frpc.ini 配置文件..."
INI_EXISTS=$($ADB -s "$DEVICE" shell "run-as $PACKAGE test -f files/frpc.ini && echo 'yes' || echo 'no'" 2>/dev/null || echo "no")
if [ "$INI_EXISTS" = "yes" ]; then
    echo "  OK frpc.ini 存在"
    $ADB -s "$DEVICE" shell "run-as $PACKAGE cat files/frpc.ini 2>/dev/null | head -10"
else
    echo "  WARN frpc.ini 不存在 (需要服务端 /api/agent/query.json 返回)"
fi

echo ""
echo "=== 验证完成 ==="
