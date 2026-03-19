# 运维手册 (Runbook)

飞鹰管理系统 V2 生产环境运维手册。

## 部署流程

### 首次部署

```bash
cd app
cp .env.production .env
# 编辑 .env：配置 APP_URL、DB_PASSWORD、WEBSOCKET_URL 等

./deploy.sh init
```

`deploy.sh init` 自动执行：
1. 检查 `.env` 文件
2. 创建必要目录并设置权限
3. 构建 Docker 镜像
4. 安装 Composer 依赖（`--no-dev`）
5. 启动所有服务（MySQL / Redis / App）
6. 等待 MySQL 就绪
7. 安装 npm 依赖并构建前端
8. 生成 APP_KEY
9. 运行数据库迁移与种子
10. 创建存储链接
11. 执行 GeoIP 数据库安装
12. 修复 APK 目录权限
13. 优化缓存（config / route / view）

### 首次部署后必须步骤

```bash
# 1. 填充角色与权限
docker compose -f compose.prod.yaml exec -T app php artisan db:seed

# 2. 创建管理员账号（交互式）
docker compose -f compose.prod.yaml exec -it app php artisan admin:create
```

### 更新部署

```bash
cd app
./deploy.sh update
```

自动执行：拉取代码 → 更新 Composer → 迁移 → 清缓存 → 重建前端 → 重启 app 容器。

## deploy.sh 命令参考

| 命令 | 说明 |
|------|------|
| `./deploy.sh init` | 首次部署初始化 |
| `./deploy.sh update` | 更新部署（拉取、迁移、清缓存、构建前端、重启） |
| `./deploy.sh start` | 启动所有服务 |
| `./deploy.sh stop` | 停止所有服务 |
| `./deploy.sh restart` | 强制重建并启动所有容器 |
| `./deploy.sh status` | 查看服务状态 |
| `./deploy.sh logs [service]` | 查看日志（默认 app，可选 mysql / redis） |
| `./deploy.sh shell` | 进入 app 容器 bash |
| `./deploy.sh build-frontend` | 仅重新构建前端资源 |
| `./deploy.sh fix-apk` | 修复 APK 构建目录权限 |
| `./deploy.sh setup-geoip` | 安装 GeoIP 数据库（需 MAXMIND_LICENSE_KEY） |

## 监控与告警

### 服务健康检查

```bash
# 查看所有容器状态
./deploy.sh status

# 查看应用日志（实时）
./deploy.sh logs app

# 查看 MySQL 日志
./deploy.sh logs mysql

# 查看 Redis 日志
./deploy.sh logs redis
```

### 应用日志位置

容器内日志路径：`storage/logs/`

| 日志文件 | 内容 |
|---------|------|
| `laravel.log` | 应用主日志 |
| `websocket-stderr.log` | WebSocket 服务器错误日志 |
| `php-stderr.log` | PHP-FPM 错误日志 |

### 关键监控指标

| 指标 | 检查方式 | 告警阈值 |
|------|---------|---------|
| 容器运行状态 | `docker compose -f compose.prod.yaml ps` | 任一服务非 Up |
| 磁盘空间 | `df -h` | > 85% |
| MySQL 连接 | `artisan tinker` → `DB::connection()->getPdo()` | 连接失败 |
| Redis 连接 | `redis-cli ping` | 非 PONG |
| WebSocket 端口 | `curl -s ws://localhost:8081` | 连接拒绝 |

## 常见问题与修复

### 1. 端口冲突（宝塔环境）

**现象**：启动失败或无法访问。

**修复**：确保 `.env` 中 `APP_PORT`、`WEBSOCKET_PORT` 未被宝塔或其他程序占用。宝塔默认占用 80 端口，建议 `APP_PORT=8080`。

```bash
# 检查端口占用
ss -tlnp | grep -E '8080|8081'
```

### 2. 容器内无法连接 MySQL / Redis

**现象**：迁移或应用报错连接被拒绝。

**修复**：
- 确认 `.env` 中 `DB_HOST=mysql`、`REDIS_HOST=redis`（使用 Docker 服务名）
- 检查服务是否正常运行：

```bash
docker compose -f compose.prod.yaml ps
```

### 3. WebSocket 无法连接

**现象**：前端设备列表无实时更新。

**排查步骤**：
1. 检查防火墙/安全组是否放行 `WEBSOCKET_PORT`（默认 8081）
2. 确认 `.env` 中 `WEBSOCKET_URL` 协议、域名、端口一致
3. 如果使用 Nginx 代理，确认 location 已配置 `Upgrade` 和 `Connection` 头
4. HTTPS 站点需用 `wss://` 协议

```bash
# 检查 WebSocket 进程
docker compose -f compose.prod.yaml exec app ps aux | grep swoole
```

### 4. APK 构建失败（Killed / 137 错误）

**根本原因**：宝塔面板 syssafe 插件的异常进程监控杀死了 `aapt2` 进程。

**已有自动修复**：`ApkBuilder.php` 会自动预提取 `aapt2` 到 `/opt/apk-tools/aapt2`（避免 `/tmp/` 路径触发 syssafe），并在每次构建前清理 apktool 框架缓存。

**手动排查**：

```bash
# 查看 syssafe 是否杀进程
cat /www/server/panel/plugin/syssafe/service.log | tail -10

# 验证 aapt2 已提取
docker exec feiying-app ls -la /opt/apk-tools/aapt2

# 手动清理 apktool 缓存
docker exec feiying-app rm -rf ~/.local/share/apktool/framework/*
```

### 5. 前端构建失败

**修复**：

```bash
# 进入容器清理并重装
./deploy.sh shell
rm -rf node_modules public/build
npm install
npm run build
exit
```

### 6. 权限问题

**修复**：

```bash
# 修复存储目录权限
docker compose -f compose.prod.yaml exec app chmod -R 775 storage bootstrap/cache
docker compose -f compose.prod.yaml exec app chown -R sail:sail storage

# 修复 APK 构建目录权限
./deploy.sh fix-apk
```

### 7. 初始化时 vendor 缺失导致 WebSocket 报错

**现象**：supervisord 启动 WebSocket 时报缺少类或 autoload。

**原因**：旧版 deploy.sh 先启动容器再安装依赖。当前版本已修复（先 `composer install` 再 `up -d`）。

**修复**：重新执行 `./deploy.sh init`。

## 回滚方案

### 代码回滚

```bash
cd app

# 1. 查看最近提交
git log --oneline -10

# 2. 回滚到指定版本
git checkout <commit-hash> .

# 3. 重新部署
./deploy.sh update
```

### 数据库回滚

```bash
# 回滚最近一批迁移
docker compose -f compose.prod.yaml exec -T app php artisan migrate:rollback

# 回滚指定步数
docker compose -f compose.prod.yaml exec -T app php artisan migrate:rollback --step=2
```

### 前端回滚

如果仅前端出问题，可单独重建：

```bash
# 回退前端代码
git checkout <commit-hash> -- resources/

# 仅重建前端
./deploy.sh build-frontend
```

### 完整回滚流程

1. 停止服务：`./deploy.sh stop`
2. 回滚代码：`git checkout <commit-hash> .`
3. 回滚数据库（如有新迁移）：`artisan migrate:rollback`
4. 重新部署：`./deploy.sh update`（或 `init`）
5. 验证服务：`./deploy.sh status` + 访问应用

### 紧急回滚（Docker 镜像层面）

如果代码仓库不可用，可利用 Docker 镜像回滚：

```bash
# 查看本地镜像历史
docker images feiying-app

# 使用旧镜像启动（需要修改 compose 中的 image 标签）
```

## 生产环境配置要点

| 项目 | 必须值 | 说明 |
|------|-------|------|
| `APP_ENV` | `production` | 生产环境标识 |
| `APP_DEBUG` | `false` | 关闭调试（避免泄露敏感信息） |
| `APP_KEY` | 自动生成 | `artisan key:generate` |
| `DB_PASSWORD` | 强密码 | 不使用默认值 |
| `DEVICE_AUTH_SECRET` | 强随机值 | 设备认证密钥 |
| `BCRYPT_ROUNDS` | `12` | 密码哈希轮次 |

## 访问地址

| 用途 | 地址模板 |
|------|---------|
| 用户端 | `http://域名:APP_PORT` |
| 管理后台 | `http://域名:APP_PORT/admin` |
| 后台登录 | `http://域名:APP_PORT/admin/login` |
| WebSocket | `ws://域名:WEBSOCKET_PORT` |
