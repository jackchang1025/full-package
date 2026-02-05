#!/bin/bash
# 飞鹰管理系统 V2 - 开发环境启动脚本

set -e

cd "$(dirname "$0")"

echo "🚀 启动飞鹰管理系统开发环境..."

# 检查 Docker 是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker 未运行，请先启动 Docker"
    exit 1
fi

# 启动 Sail 服务
echo "📦 启动 Docker 容器..."
./vendor/bin/sail up -d

# 等待 MySQL 就绪
echo "⏳ 等待 MySQL 就绪..."
sleep 5
until ./vendor/bin/sail exec mysql mysqladmin ping -h localhost -u sail -ppassword --silent 2>/dev/null; do
    echo "   等待 MySQL..."
    sleep 2
done
echo "✅ MySQL 已就绪"

# 运行迁移
echo "🗄️  运行数据库迁移..."
./vendor/bin/sail artisan migrate --force

# 创建存储符号链接
echo "🔗 创建存储符号链接..."
./vendor/bin/sail artisan storage:link 2>/dev/null || true

# 清理缓存
echo "🧹 清理缓存..."
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan cache:clear
./vendor/bin/sail artisan route:clear

# 修复目录权限（确保 artisan 可写入 storage、bootstrap/cache、lang）
echo "🔧 修复目录权限..."
./vendor/bin/sail exec -T -u root laravel.test bash -c "
    chown -R sail:sail /var/www/html/storage /var/www/html/bootstrap/cache /var/www/html/lang
    chmod -R 775 /var/www/html/storage /var/www/html/bootstrap/cache /var/www/html/lang
" 2>/dev/null || true

echo ""
echo "✅ 开发环境已启动!"
echo ""
echo "📍 访问地址:"
echo "   - 应用: http://localhost:8000"
echo "   - MySQL: localhost:3307"
echo "   - Redis: localhost:6380"
echo ""
echo "🔧 常用命令:"
echo "   - 前端开发: ./vendor/bin/sail npm run dev"
echo "   - 前端构建: ./vendor/bin/sail npm run build"
echo "   - 停止服务: ./vendor/bin/sail down"
echo "   - 查看日志: ./vendor/bin/sail logs -f"
echo ""
