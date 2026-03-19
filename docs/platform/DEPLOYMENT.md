# 飞鹰管理系统 V2 - 部署文档

本文档提供生产环境完整部署指南，包括通用 Docker 部署与**宝塔面板中使用 Docker 部署**。

## 目录

1. [架构与端口](#架构与端口)
2. [环境要求](#环境要求)
3. [通用 Docker 部署](#通用-docker-部署)
4. [宝塔面板 + Docker 部署](#宝塔面板--docker-部署)
5. [环境变量与配置](#环境变量与配置)
6. [Nginx 反向代理与 WebSocket](#nginx-反向代理与-websocket)
7. [SSL/HTTPS 配置](#sslhttps-配置)
8. [日常运维命令](#日常运维命令)
9. [故障排查](#故障排查)

---

## 环境要求

- **宿主机**：Linux（推荐 Ubuntu 22.04+ / Debian 11+），x86_64
- **Docker**：20.10+
- **Docker Compose**：v2（`docker compose` 命令）
- **资源**：建议 2 核 CPU、2GB 内存、20GB 磁盘；APK 构建与并发多时适当提高

---

## 通用 Docker 部署

### 1. 准备代码与配置

```bash
# 进入应用目录（注意：部署脚本在 app 目录下执行）
cd /path/to/full-package/app

# 若没有 .env，从生产模板复制并编辑
cp .env.production .env
# 编辑 .env：至少修改 DB_PASSWORD、APP_URL、WEBSOCKET_URL 等（见下方「环境变量与配置」）
```

### 2. 首次初始化部署

```bash
./deploy.sh init
```

脚本将依次执行：检查 `.env`、创建目录与权限、构建镜像、**先安装 Composer 依赖**、启动所有服务（MySQL/Redis/App）、等待 MySQL 就绪、安装 npm 并构建前端、生成密钥、迁移数据库、存储链接、**自动执行 GeoIP 数据库安装脚本**（`scripts/setup-geoip.sh`）、APK 目录权限、缓存优化。

完成后可访问：

- **用户端**：`http://服务器IP:APP_PORT`（默认 80）
- **WebSocket**：`ws://服务器IP:WEBSOCKET_PORT`（默认 8081）
- **后台管理**：`http://服务器IP:APP_PORT/admin`，登录页 `http://服务器IP:APP_PORT/admin/login`（首次部署后需完成下方「首次部署后的必要步骤」才能登录）

### 3. 首次部署后的必要步骤

`./deploy.sh init` 完成后，必须执行以下两步，否则后台无法获取权限数据且无法登录：

**（1）执行角色与权限种子**

初始化权限与默认角色（否则后台接口会因无权限数据报错）：

```bash
docker compose -f compose.prod.yaml exec -T app php artisan db:seed
```

**（2）创建管理员账号**

后台使用独立管理员表（`admins`），需至少创建一个总管理员才能登录后台：

```bash
# 交互式（按提示输入名称、邮箱、密码）
docker compose -f compose.prod.yaml exec -it app php artisan admin:create
```

完成后使用上述邮箱与密码访问 **后台登录页**：`http://你的域名或IP:APP_PORT/admin/login`。

### 4. 后续更新部署

```bash
./deploy.sh update
```

会拉取代码（若为 Git）、更新 Composer、迁移、清缓存、重新构建前端、重启 app 容器。

### 5. 其他常用命令


| 命令                                   | 说明                                  |
| ------------------------------------ | ----------------------------------- |
| `./deploy.sh start`                  | 启动所有服务                              |
| `./deploy.sh stop`                   | 停止所有服务                              |
| `./deploy.sh restart`                | 强制重建并启动                             |
| `./deploy.sh status`                 | 查看服务状态                              |
| `./deploy.sh logs [app|mysql|redis]` | 查看日志（默认 app）                        |
| `./deploy.sh shell`                  | 进入 app 容器 bash                      |
| `./deploy.sh build-frontend`         | 仅重新构建前端                             |
| `./deploy.sh fix-apk`                | 修复 APK 构建目录权限                       |
| `./deploy.sh setup-geoip`            | 安装 GeoIP 数据库（需 MAXMIND_LICENSE_KEY） |


---

## 宝塔面板 + Docker 部署

在已安装宝塔面板的服务器上，可使用宝塔的 Docker 管理或纯命令行部署本应用。推荐：**在宝塔中安装 Docker 与 Docker Compose，项目放在任意目录（如 www 或 home），用命令行执行 `deploy.sh`**；再用宝塔 Nginx 做反向代理与 SSL。

### 1. 宝塔中安装 Docker

- 登录宝塔 → **软件商店** → 搜索 **Docker** → 安装 **Docker 管理器**（或系统环境内安装 Docker）

### 2. 放置项目并配置 .env

- 将项目上传或克隆到服务器，例如：
  - `/www/wwwroot/feiying`（与宝塔网站目录一致便于后续 Nginx 配置）

### 配置数据库，websocket 端口等

```bash
cd /www/wwwroot/feiying/app   # 或你的 app 所在路径

cp .env.production .env
# 用宝塔「文件」或 vi/nano 编辑 .env
```

生产环境至少修改：

- `APP_KEY=` → 留空，init 时会自动生成；
- `APP_URL=` → 你的域名，如 `https://your-domain.com`
- `DB_DATABASE`、`DB_USERNAME`、`DB_PASSWORD` 
- `WEBSOCKET_URL` 例如：WEBSOCKET_URL=ws://192.168.31.35:8081
- `VITE_DEV_HOST` 你的域名

为便于宝塔 Nginx 反向代理，建议将 Web 端口改为非 80（避免与宝塔占用冲突），例如：

```ini
APP_PORT=8080
WEBSOCKET_PORT=8081
```

### 3. 在宝塔服务器上执行首次部署

在宝塔「终端」或 SSH 中（在 **app** 目录下）：

```bash
cd /www/wwwroot/feiying/app
chmod +x deploy.sh
./deploy.sh init
```

### 4. 宝塔首次部署后的必要步骤

`./deploy.sh init` 完成后，必须执行以下两步，否则后台无法获取权限数据且无法登录。在 **app** 目录下执行：

**（1）执行角色与权限种子**

```bash
cd /www/wwwroot/feiying/app   # 或你的 app 所在路径
docker compose -f compose.prod.yaml exec -T app php artisan db:seed --class=RolePermissionSeeder
```

或执行完整种子：`docker compose -f compose.prod.yaml exec -T app php artisan db:seed`

**（2）创建管理员账号**

```bash
# 交互式（按提示输入名称、邮箱、密码）
docker compose -f compose.prod.yaml exec -it app php artisan admin:create

# 或非交互式
docker compose -f compose.prod.yaml exec -T app php artisan admin:create \
  --name="管理员" \
  --email="admin@example.com" \
  --password="你的强密码"
```

### 5. 使用宝塔 Nginx 做反向代理

不直接暴露 8080 给公网，而是由 Nginx 反向代理到本机端口，便于加 SSL 和统一域名。

- 宝塔 → **网站** → **添加站点**：填写你的域名，如 `your-domain.com`，根目录可随意（例如 `/www/wwwroot/feiying/public` 仅作占位，实际由 Docker 内 PHP 处理）。
- 进入该站点 → **设置** → **反向代理** → **添加反向代理**：
  - **代理名称**：`feiying-web`
  - **目标 URL**：`http://127.0.0.1:8080`（与 `.env` 中 `APP_PORT` 一致）
  - **发送域名**：`$host`
  - 保存。

这样 HTTP 访问 `http://your-domain.com` 会转发到容器 Web 端口。HTTPS 在下一节配置。
| [示例](./image/web-site.png)


### 6. 配置 WebSocket

- **方式 A（端口直连）**：不经过 Nginx，前端直接连 `ws://your-domain.com:8081`。 
  - 查看 .env  WEBSOCKET_URL 配置端口 例如：`WEBSOCKET_URL=ws://your-domain.com:8081`
  - 在宝塔 **安全** 中放行 **8081** 端口。  8081 替换成你的 WEBSOCKET_URL 配置端口

[示例](./image/image.png)
### 7. 宝塔中后续更新

代码更新后，在 **app** 目录下执行：

```bash
cd /www/wwwroot/feiying/app
./deploy.sh update
```

## 环境变量与配置

部署依赖的环境变量来自 **app 目录下的 `.env`**（Compose 会读取同一目录下的 `.env` 做端口等替换）。与部署强相关的项如下。

### 必须与 compose 一致的数据库变量

`compose.prod.yaml` 中 MySQL 使用以下环境变量（来自 `.env`）：


| 变量            | 说明                       | 示例           |
| ------------- | ------------------------ | ------------ |
| `DB_DATABASE` | 数据库名                     | `feiying_v2` |
| `DB_USERNAME` | 数据库用户                    | `feiying`    |
| `DB_PASSWORD` | 数据库密码                    | 强密码          |
| `DB_HOST`     | 必须为 `mysql`（Compose 服务名） | `mysql`      |
| `DB_PORT`     | 容器内端口                    | `3306`       |


Redis 同理：`REDIS_HOST=redis`，`REDIS_PORT=6379`（无密码则 `REDIS_PASSWORD=null`）。

### 端口与 WebSocket


| 变量               | 说明                 | 建议                                   |
| ---------------- | ------------------ | ------------------------------------ |
| `APP_PORT`       | Web 映射到宿主机的端口      | 与 Nginx 反向代理目标一致，如 8080              |
| `WEBSOCKET_PORT` | WebSocket 映射端口     | 8081；若用 Nginx 代理 wss 可仍用 8081 仅本机访问  |
| `APP_URL`        | 站点对外 URL           | `https://your-domain.com`            |
| `WEBSOCKET_URL`  | 前端连接的 WebSocket 地址 | 直连：`ws://域名:8081`；代理：`wss://域名/ws` 等 |


### 生产模板

复制并编辑：`cp .env.production .env`。`.env.production` 中已包含上述项及默认值，按需修改即可。

---

## SSL/HTTPS 配置

- **宝塔**：站点设置 → **SSL** → 申请 Let’s Encrypt 或上传证书，开启强制 HTTPS。
- **自建 Nginx**：在 `server` 中配置 `listen 443 ssl`、`ssl_certificate`、`ssl_certificate_key`，并保留上述 `proxy_set_header X-Forwarded-Proto $scheme`，Laravel 才能正确识别为 HTTPS。

WebSocket 若走 Nginx 代理，同一 server 的 `location /ws` 在 443 下即自动为 wss，无需额外配置。

---

## 日常运维命令

均在 **app** 目录下执行：


| 操作          | 命令                           |
| ----------- | ---------------------------- |
| 启动          | `./deploy.sh start`          |
| 停止          | `./deploy.sh stop`           |
| 重启（重建容器）    | `./deploy.sh restart`        |
| 查看状态        | `./deploy.sh status`         |
| 查看 app 日志   | `./deploy.sh logs app`       |
| 进入容器        | `./deploy.sh shell`          |
| 仅重建前端       | `./deploy.sh build-frontend` |
| 修复 APK 目录权限 | `./deploy.sh fix-apk`        |


---

### 访问地址汇总


| 用途        | 地址示例（将 `域名或IP`、`APP_PORT` 换为实际值）     |
| --------- | ------------------------------------ |
| 用户端首页     | `http://域名或IP:APP_PORT`              |
| 后台管理首页    | `http://域名或IP:APP_PORT/admin`        |
| 后台登录页     | `http://域名或IP:APP_PORT/admin/login`  |
| WebSocket | `ws://域名或IP:WEBSOCKET_PORT`（默认 8081） |


后台路径可通过配置 `site.admin_entry_path` 修改（默认 `admin`）。

---

## 故障排查

### 初始化时 WebSocket/PHP 报错（缺 vendor）

现象：`./deploy.sh init` 后，容器内 supervisord 已启动 WebSocket，但报错缺少类或 autoload。  
原因：历史版本曾先 `up -d` 再 `composer install`，导致首次启动时 vendor 未就绪。  
处理：当前 `deploy.sh init` 已改为**先**用 `docker run` 安装 Composer 依赖**再** `up -d`。若仍遇此问题，确认未改乱 init 顺序，并重跑一次 init（或先删 vendor 再 init）。

### 宝塔下端口冲突

现象：启动失败或无法访问。  
处理：确保 `.env` 中 `APP_PORT`、`WEBSOCKET_PORT` 未被宝塔或其他程序占用（如 80 常被 Nginx 占，故建议 APP_PORT=8080）。

### 容器内无法连 MySQL/Redis

现象：迁移或应用报错连接被拒绝。  
处理：确认 `.env` 中 `DB_HOST=mysql`、`REDIS_HOST=redis`（服务名），且未改 compose 中的服务名；`docker compose -f compose.prod.yaml ps` 查看 mysql/redis 是否均为 Up。

### WebSocket 无法连接

- 直连：检查防火墙/安全组是否放行 `WEBSOCKET_PORT`（如 8081）；前端与 `.env` 中 `WEBSOCKET_URL` 一致（协议、域名、端口）。
- Nginx 代理：确认 location 已加 `Upgrade`、`Connection` 与 `proxy_read_timeout`；HTTPS 站用 `wss://`。

### APK 构建失败（Killed / 137 错误）

现象：构建 APK 时在 `build_apk` (apktool) 阶段失败，日志中显示 `Killed`（退出码 137 = SIGKILL）。

**根本原因：宝塔面板 syssafe 插件的异常进程监控。**

`apktool` 在构建资源时，会将内置的 `aapt2` 二进制文件解压到 `/tmp/brut_util_Jar_xxxxx.tmp` 并执行。宝塔面板的 **系统加固 (syssafe)** 插件会持续扫描所有进程，对 exe 路径在 `/tmp/` 下的进程进行安全检查——如果 CPU > 30% 或虚拟内存 > 100MB，会立即 SIGKILL 该进程**及其父进程**。`aapt2` 编译资源时 CPU 占用远超 30%，因此被 syssafe 秒杀，连带 Java (apktool) 父进程一起被杀。

> **注意**：这不是 OOM (内存不足) 问题。通过内存监控可以观察到进程被杀时仅使用了约 192MB 内存，而系统有 13GB 可用。dmesg 中也没有 OOM 相关日志。

**已实现的修复（代码层面）**：

`ApkBuilder.php` 已实现以下自动化处理，正常情况下无需手动干预：

1. **预提取 aapt2 到容器本地路径**：首次构建时自动从 `apktool.jar` 中提取 `aapt2` 二进制文件。优先写入 `/opt/apk-tools/aapt2`（容器本地文件系统），回退到 `storage/app/apk/tools/aapt2`。通过 `--aapt` 参数让 apktool 使用该路径的 aapt2，避免解压到 `/tmp/`。
   - **为什么用 `/opt/apk-tools/`？** Docker bind mount 卷上 Java 的 `File.setExecutable()` 会返回 false，导致 apktool 内部 `AaptManager.setAaptBinaryExecutable()` 抛出 "Could not set aapt binary as executable" 错误。容器本地文件系统无此限制。
2. **自动清理 apktool 框架缓存**：每次构建前执行 `rm -rf ~/.local/share/apktool/framework/*`，防止之前的崩溃遗留损坏缓存。

**手动排查步骤**（如果仍然失败）：

1. **确认 syssafe 插件状态**：
   ```bash
   # 查看 syssafe 是否正在杀进程
   cat /www/server/panel/plugin/syssafe/service.log | tail -10
   
   # 查看 syssafe 进程监控配置
   python3 -c "import json; c=json.load(open('/www/server/panel/plugin/syssafe/config.json')); print('进程监控:', '开启' if c['process']['open'] else '关闭')"
   ```

2. **验证 aapt2 是否已提取**：
   ```bash
   # 优先检查容器本地路径
   docker exec feiying-app ls -la /opt/apk-tools/aapt2
   # 回退路径
   docker exec feiying-app ls -la /var/www/html/storage/app/apk/tools/aapt2
   # 如果都不存在，下次构建时会自动从 apktool.jar 提取
   ```

3. **手动清理 apktool 框架缓存**：
   ```bash
   docker exec feiying-app rm -rf ~/.local/share/apktool/framework/*
   ```

4. **（可选）在 syssafe 白名单中添加 aapt2**：如果未来 apktool 更新导致新的临时文件问题，可在宝塔面板 → 系统加固 → 异常进程白名单中添加 `aapt2`。或编辑配置：
   ```bash
   # 在 process_white 列表中添加 "aapt2"
   vi /www/server/panel/plugin/syssafe/config.json
   ```

### 查看容器与日志

```bash
cd /path/to/app
docker compose -f compose.prod.yaml ps
docker compose -f compose.prod.yaml logs -f app
# 或
./deploy.sh logs app
```

应用日志在容器内：`storage/logs/`（如 `websocket-stderr.log`、`php-stderr.log`）。