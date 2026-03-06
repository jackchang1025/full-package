#!/bin/bash
# Laravel Docker 生产环境部署脚本
# 使用方法: ./deploy.sh [init|update|restart|logs|shell]

set -e

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 项目目录
PROJECT_DIR=$(dirname "$(readlink -f "$0")")
cd "$PROJECT_DIR"

# Docker Compose 命令
DC="docker compose -f compose.prod.yaml"

# 读取 .env 中的 APP_IMAGE（用于判断是否使用远程镜像）
APP_IMAGE=$(grep -E '^APP_IMAGE=' .env 2>/dev/null | sed 's/^APP_IMAGE=//' | tr -d '"' | tr -d "'" || echo "")

# 判断是否使用远程镜像（包含 / 表示 registry 地址，如 registry.cn-hangzhou.aliyuncs.com/xxx/feiying-app:latest）
is_remote_image() {
    [ -n "$APP_IMAGE" ] && echo "$APP_IMAGE" | grep -q '/'
}

# 打印带颜色的消息
print_msg() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查 .env 文件
check_env() {
    if [ ! -f .env ]; then
        print_error ".env 文件不存在！"
        print_msg "正在从 .env.production 创建..."
        cp .env.production .env
        print_warn "请编辑 .env 文件配置数据库密码等信息后重新运行脚本"
        exit 1
    fi
}

# 初始化部署
init() {
    print_msg "=== 开始初始化部署 ==="
    
    check_env
    
    # 创建必要目录
    print_msg "创建必要目录..."
    mkdir -p storage/logs storage/app storage/framework/{cache,sessions,views}
    mkdir -p storage/app/apk/{apkstub,tools,template}
    mkdir -p storage/app/public/{apk,icons,backgrounds}
    
    # 设置权限
    print_msg "设置目录权限..."
    chmod -R 775 storage bootstrap/cache
    
    # 构建镜像（不加 --no-cache，重跑 init 时可复用缓存，节省时间）
    if is_remote_image; then
        print_msg "拉取远程镜像: $APP_IMAGE ..."
        $DC pull app
    else
        print_msg "构建 Docker 镜像..."
        $DC build
    fi
    
    # 先安装 Composer 依赖（再启动服务，避免 supervisord/WebSocket 因缺 vendor 报错）
    # 独立容器不在 compose 网络内，无法解析 mysql/redis，故用 --no-scripts 跳过 package:discover，待服务启动后再执行
    print_msg "安装 Composer 依赖..."
    docker run --rm \
        -v "${PROJECT_DIR}:/var/www/html" \
        -e WWWUSER="${WWWUSER:-1000}" \
        ${APP_IMAGE:-feiying-app:latest} \
        composer install --optimize-autoloader --no-dev --no-scripts
    
    # 启动服务
    print_msg "启动服务..."
    $DC up -d
    
    # 等待 MySQL 就绪
    print_msg "等待 MySQL 就绪..."
    sleep 15
    
    # 在已联网的 app 容器内执行 package:discover（composer install 时因 --no-scripts 被跳过）
    print_msg "注册 Composer 包..."
    $DC exec -T app composer run-script post-autoload-dump --no-interaction
    
    # 构建前端资源
    print_msg "安装 npm 依赖..."
    $DC exec -T app npm install
    
    print_msg "构建前端资源..."
    $DC exec -T app npm run build
    
    # 生成密钥
    print_msg "生成应用密钥..."
    $DC exec -T app php artisan key:generate --force
    
    # 运行迁移
    print_msg "运行数据库迁移..."
    $DC exec -T app php artisan migrate --force

    print_msg "运行数据库填充..."
    $DC exec -T app php artisan db:seed --force
    
    # 创建存储链接
    print_msg "创建存储链接..."
    $DC exec -T app php artisan storage:link || true

    # GeoIP 数据库安装脚本（自动执行：从 legacy 复制或凭 MAXMIND_LICENSE_KEY 下载）
    print_msg "执行 GeoIP 数据库安装脚本..."
    if [ -f .env ] && grep -qE '^MAXMIND_LICENSE_KEY=' .env 2>/dev/null; then
        export $(grep -E '^MAXMIND_LICENSE_KEY=' .env | sed 's/[[:space:]]*#.*//' | xargs)
    fi
    if ./scripts/setup-geoip.sh 2>/dev/null; then
        print_msg "GeoIP 数据库已就绪"
    else
        print_warn "GeoIP 数据库未配置，IP 归属地功能将不可用。详见 storage/app/geoip/README.md"
    fi

    # 修复 APK 构建目录权限（确保 sail 用户可写）
    print_msg "修复 APK 构建目录权限..."
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/apk || true
    $DC exec -T app chmod -R 775 /var/www/html/storage/app/apk || true
    
    # 优化
    print_msg "优化应用..."
    $DC exec -T app php artisan config:cache
    $DC exec -T app php artisan route:cache
    $DC exec -T app php artisan view:cache
    
    print_msg "=== 初始化完成 ==="
    app_url=$(grep -E '^APP_URL=' .env 2>/dev/null | sed 's/^APP_URL=//;s/^["'\''"]//;s/["'\''"]$//' | head -1)
    [ -z "$app_url" ] && app_url="http://localhost:${APP_PORT:-80}"
    print_msg "应用地址: $app_url"
    print_msg "后台地址: ${app_url%/}/admin"
}

# 更新部署
update() {
    print_msg "=== 开始更新部署 ==="
    
    check_env
    
    # 拉取最新代码（如果使用 Git）
    if [ -d .git ]; then
        print_msg "拉取最新代码..."
        git pull origin main || git pull origin master || true
    fi
    
    # 安装依赖
    print_msg "更新 Composer 依赖..."
    $DC exec -T app composer install --optimize-autoloader --no-dev
    
    # 运行迁移
    print_msg "运行数据库迁移..."
    $DC exec -T app php artisan migrate --force
    
    # 修复 APK 构建目录权限（确保 sail 用户可写）
    print_msg "修复 APK 构建目录权限..."
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/apk 2>/dev/null || true
    $DC exec -T app chmod -R 775 /var/www/html/storage/app/apk 2>/dev/null || true
    
    # 清除并重建缓存
    print_msg "重建缓存..."
    $DC exec -T app php artisan config:clear
    $DC exec -T app php artisan route:clear
    $DC exec -T app php artisan view:clear
    $DC exec -T app php artisan cache:clear
    
    $DC exec -T app php artisan config:cache
    $DC exec -T app php artisan route:cache
    $DC exec -T app php artisan view:cache
    
    # 重启队列
    # print_msg "重启队列..."
    # $DC exec -T app php artisan queue:restart

    print_msg "构建前端资源..."
    $DC exec -T app rm -rf public/build
    
    print_msg "安装 npm 依赖..."
    $DC exec -T app npm install

    print_msg "构建前端资源..."
    $DC exec -T app npm run build
    print_msg "前端构建完成"
    
    # 重启 app 容器，使 PHP/WebSocket/队列/调度器 等常驻进程加载新代码
    # 使用 stop+start 替代 restart，并显式指定超时，避免 Swoole/Queue 无响应导致无限卡死
    print_msg "重启 app 容器..."
    $DC stop -t 25 app && $DC start app
    print_msg "容器已重启"
    
    print_msg "=== 更新完成 ==="
}

# 启动服务
start() {
    print_msg "启动服务..."
    $DC up -d
    print_msg "启动完成"
}

# 重启服务（强制重建容器）
restart() {
    print_msg "重启所有服务..."
    $DC up -d --force-recreate
    print_msg "重启完成"
}

# 查看日志
logs() {
    SERVICE=${2:-app}
    print_msg "查看 $SERVICE 日志..."
    $DC logs -f $SERVICE
}

# 进入容器
shell() {
    print_msg "进入 app 容器..."
    $DC exec app bash
}

# 停止服务
stop() {
    print_msg "停止所有服务..."
    $DC down
    print_msg "服务已停止"
}

# 状态检查
status() {
    print_msg "服务状态:"
    $DC ps
}

# 重建前端
build_frontend() {
    print_msg "构建前端资源..."
    print_msg "清理旧的构建产物..."
    $DC exec -T app rm -rf public/build
    
    $DC exec -T app npm install
    $DC exec -T app npm run build
    print_msg "前端构建完成"
}

# 修复 APK 构建目录权限
fix_apk_permissions() {
    print_msg "修复 APK 构建目录权限..."
    
    # 确保目录存在
    $DC exec -T app mkdir -p /var/www/html/storage/app/apk/{apkstub,tools,template}
    $DC exec -T app mkdir -p /var/www/html/storage/app/public/{apk,icons,backgrounds}
    
    # 修复所有者为 sail 用户
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/apk
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/public/apk
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/public/icons
    $DC exec -T app chown -R sail:sail /var/www/html/storage/app/public/backgrounds
    
    # 设置目录权限
    $DC exec -T app chmod -R 775 /var/www/html/storage/app/apk
    $DC exec -T app chmod -R 775 /var/www/html/storage/app/public/apk
    
    print_msg "APK 目录权限修复完成"
    
    # 显示当前权限状态
    print_msg "当前目录权限:"
    $DC exec -T app ls -la /var/www/html/storage/app/apk/
}

push_image() {
    local TAG="${2:-latest}"
    if [ -z "$APP_IMAGE" ]; then
        print_error "未配置 APP_IMAGE，请在 .env 中设置远程镜像地址"
        print_msg "示例: APP_IMAGE=registry.cn-hangzhou.aliyuncs.com/yourns/feiying-app:latest"
        exit 1
    fi

    print_msg "构建镜像: ${APP_IMAGE} ..."
    docker buildx build --platform linux/amd64 \
        -t "${APP_IMAGE}" \
        --build-arg WWWGROUP=1000 \
        ./docker/8.4

    print_msg "推送镜像: ${APP_IMAGE} ..."
    docker push "${APP_IMAGE}"

    print_msg "=== 镜像推送完成: ${APP_IMAGE} ==="
}

# 帮助信息
help() {
    echo "Laravel Docker 部署脚本"
    echo ""
    echo "使用方法: $0 <command>"
    echo ""
    echo "可用命令:"
    echo "  init            首次部署初始化"
    echo "  update          更新部署（拉取代码、迁移、清缓存）"
    echo "  start           启动服务（容器不存在时创建）"
    echo "  restart         重启服务（强制重建容器）"
    echo "  stop            停止所有服务"
    echo "  status          查看服务状态"
    echo "  logs [service]  查看日志（默认 app）"
    echo "  shell           进入 app 容器"
    echo "  build-frontend  重新构建前端资源"
    echo "  push-image      构建并推送镜像到远程仓库（需配置 APP_IMAGE）"
    echo "  fix-apk         修复 APK 构建目录权限"
    echo "  setup-geoip     安装 GeoIP 数据库（IP 归属地）"
    echo "  help            显示此帮助信息"
}

# 主逻辑
case "${1:-help}" in
    init)
        init
        ;;
    update)
        update
        ;;
    start)
        start
        ;;
    restart)
        restart
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    logs)
        logs "$@"
        ;;
    shell)
        shell
        ;;
    build-frontend)
        build_frontend
        ;;
    push-image)
        push_image "$@"
        ;;
    fix-apk)
        fix_apk_permissions
        ;;
    setup-geoip)
        [ -f .env ] && export $(grep -E '^MAXMIND_LICENSE_KEY=' .env 2>/dev/null | sed 's/[[:space:]]*#.*//' | xargs) 2>/dev/null || true
        ./scripts/setup-geoip.sh
        ;;
    help|*)
        help
        ;;
esac
