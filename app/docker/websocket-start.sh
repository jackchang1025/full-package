#!/bin/bash
# WebSocket 启动脚本 - 等待 MySQL 就绪后再启动

MAX_WAIT=60  # 最多等待 60 秒
WAIT_INTERVAL=2  # 每 2 秒检查一次
WAITED=0

# 从 .env 文件加载环境变量
if [ -f /var/www/html/.env ]; then
    export $(grep -E '^(DB_HOST|DB_PORT|DB_USERNAME|DB_PASSWORD)=' /var/www/html/.env | xargs)
fi

# 使用默认值（Sail 默认配置）
DB_HOST="${DB_HOST:-mysql}"
DB_PORT="${DB_PORT:-3306}"
DB_USERNAME="${DB_USERNAME:-sail}"
DB_PASSWORD="${DB_PASSWORD:-password}"

echo "[WebSocket] Waiting for MySQL ($DB_HOST:$DB_PORT) to be ready..."

while [ $WAITED -lt $MAX_WAIT ]; do
    # 尝试连接 MySQL
    if php -r "
        try {
            \$pdo = new PDO(
                'mysql:host=${DB_HOST};port=${DB_PORT}',
                '${DB_USERNAME}',
                '${DB_PASSWORD}',
                [PDO::ATTR_TIMEOUT => 3]
            );
            exit(0);
        } catch (Exception \$e) {
            exit(1);
        }
    " 2>/dev/null; then
        echo "[WebSocket] MySQL is ready after ${WAITED}s, starting WebSocket server..."
        exec php /var/www/html/artisan websocket:serve
    fi
    
    echo "[WebSocket] MySQL not ready, waiting... (${WAITED}s/${MAX_WAIT}s)"
    sleep $WAIT_INTERVAL
    WAITED=$((WAITED + WAIT_INTERVAL))
done

echo "[WebSocket] ERROR: MySQL not ready after ${MAX_WAIT}s, starting anyway..."
exec php /var/www/html/artisan websocket:serve
