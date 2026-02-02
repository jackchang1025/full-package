#!/bin/bash
# GeoIP 数据库安装脚本
# 用于部署时下载 GeoLite2-City.mmdb（约 60MB，不适合 git 提交）

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
APP_DIR="$(dirname "$SCRIPT_DIR")"
GEOIP_DIR="$APP_DIR/storage/app/geoip"
TARGET="$GEOIP_DIR/GeoLite2-City.mmdb"

mkdir -p "$GEOIP_DIR"

# 已存在则跳过
if [ -f "$TARGET" ]; then
    echo "✅ GeoIP 数据库已存在: $TARGET"
    exit 0
fi

# 方式一：从同仓库的 legacy 复制
LEGACY_SRC="$APP_DIR/../legacy/src/api/assets/GeoIP/GeoLite2-City.mmdb"
if [ -f "$LEGACY_SRC" ]; then
    echo "📂 从 legacy 复制 GeoIP 数据库..."
    cp "$LEGACY_SRC" "$TARGET"
    echo "✅ 完成"
    exit 0
fi

# 方式二：从 MaxMind 下载（需 MAXMIND_LICENSE_KEY）
# 获取方式: https://www.maxmind.com/en/geolite2/signup → 创建账户 → License Keys
if [ -n "${MAXMIND_LICENSE_KEY:-}" ]; then
    echo "📥 从 MaxMind 下载 GeoIP 数据库..."
    TMP_TAR="/tmp/GeoLite2-City.tar.gz"
    curl -sS "https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-City&license_key=${MAXMIND_LICENSE_KEY}&suffix=tar.gz" -o "$TMP_TAR"
    tar -xzf "$TMP_TAR" -C /tmp
    mv /tmp/GeoLite2-City_*/GeoLite2-City.mmdb "$TARGET"
    rm -rf /tmp/GeoLite2-City_* "$TMP_TAR"
    echo "✅ 完成"
    exit 0
fi

echo "❌ GeoIP 数据库未找到，请任选一种方式："
echo ""
echo "  1) 从 legacy 复制（若同机部署）："
echo "     mkdir -p app/storage/app/geoip"
echo "     cp legacy/src/api/assets/GeoIP/GeoLite2-City.mmdb app/storage/app/geoip/"
echo ""
echo "  2) 使用 MaxMind License Key 下载："
echo "     export MAXMIND_LICENSE_KEY=your_key"
echo "     ./scripts/setup-geoip.sh"
echo "     (获取 Key: https://www.maxmind.com/en/geolite2/signup)"
echo ""
echo "  3) 手动下载后放入 storage/app/geoip/GeoLite2-City.mmdb"
echo ""
exit 1
