# Docker Supervisor 配置

> 本文档说明如何通过 Supervisor 在 Laravel Sail 容器中自动启动后台服务。

## 概述

Laravel Sail 使用 Supervisor 管理容器内的进程。通过 volume 映射自定义配置文件，可以在容器启动时自动运行额外服务（如 WebSocket 服务器），无需重建镜像。

## 文件结构

```
app/
├── compose.yaml                    # Docker Compose 配置
└── docker/
    ├── supervisord.conf            # 自定义 Supervisor 配置 (映射到容器)
    └── 8.4/
        └── supervisord.conf        # 镜像默认配置 (构建时 COPY)
```

## 配置说明

### 自定义配置文件

位置: `app/docker/supervisord.conf`

```ini
[supervisord]
nodaemon=true
user=root
logfile=/var/log/supervisor/supervisord.log
pidfile=/var/run/supervisord.pid

[program:php]
command=%(ENV_SUPERVISOR_PHP_COMMAND)s
user=%(ENV_SUPERVISOR_PHP_USER)s
environment=LARAVEL_SAIL="1"
stdout_logfile=/dev/stdout
stdout_logfile_maxbytes=0
stderr_logfile=/dev/stderr
stderr_logfile_maxbytes=0

[program:websocket]
command=php /var/www/html/artisan websocket:serve
user=sail
autostart=true
autorestart=true
environment=LARAVEL_SAIL="1"
stdout_logfile=/dev/stdout
stdout_logfile_maxbytes=0
stderr_logfile=/dev/stderr
stderr_logfile_maxbytes=0
startsecs=1
startretries=3
```

### Volume 映射

在 `compose.yaml` 中配置映射:

```yaml
services:
    laravel.test:
        volumes:
            - '.:/var/www/html'
            - './docker/supervisord.conf:/etc/supervisor/conf.d/supervisord.conf'
```

## 服务管理

### 启动/重启

修改 `docker/supervisord.conf` 后，只需重启容器即可生效:

```bash
./vendor/bin/sail restart
```

### 查看服务状态

```bash
# 进入容器
./vendor/bin/sail shell

# 查看 Supervisor 状态
supervisorctl status

# 输出示例:
# php                              RUNNING   pid 7, uptime 0:05:32
# websocket                        RUNNING   pid 8, uptime 0:05:32
```

### 手动控制服务

```bash
# 在容器内执行
supervisorctl stop websocket
supervisorctl start websocket
supervisorctl restart websocket
```

### 查看日志

```bash
# 查看容器日志 (包含所有 Supervisor 管理的服务)
./vendor/bin/sail logs -f

# 或单独查看 Laravel 容器
./vendor/bin/sail logs laravel.test -f
```

## 添加新服务

要添加新的后台服务，在 `docker/supervisord.conf` 中追加配置块:

```ini
[program:your-service]
command=php /var/www/html/artisan your:command
user=sail
autostart=true
autorestart=true
environment=LARAVEL_SAIL="1"
stdout_logfile=/dev/stdout
stdout_logfile_maxbytes=0
stderr_logfile=/dev/stderr
stderr_logfile_maxbytes=0
startsecs=1
startretries=3
```

### 配置参数说明

| 参数 | 说明 |
|------|------|
| `command` | 要执行的命令 |
| `user` | 运行用户 (Sail 容器中使用 `sail`) |
| `autostart` | 容器启动时自动启动服务 |
| `autorestart` | 服务异常退出时自动重启 |
| `startsecs` | 启动后运行 N 秒视为成功启动 |
| `startretries` | 启动失败时的重试次数 |
| `stdout_logfile` | 标准输出日志位置 (`/dev/stdout` 输出到 Docker 日志) |

## 与镜像内置配置的关系

| 配置来源 | 路径 | 生效方式 |
|----------|------|----------|
| 镜像内置 | `docker/8.x/supervisord.conf` | 构建时 COPY 到镜像 |
| Volume 映射 | `docker/supervisord.conf` | 运行时覆盖镜像配置 |

**优先级**: Volume 映射 > 镜像内置

因此修改 `docker/supervisord.conf` 后只需 `sail restart`，无需重建镜像。

## 当前运行的服务

| 服务 | 命令 | 端口 | 说明 |
|------|------|------|------|
| php | `artisan serve` | 80 | Laravel HTTP 服务 |
| websocket | `artisan websocket:serve` | 8081 | WebSocket 服务 (设备通信) |

## 故障排查

### 服务启动失败

```bash
# 查看详细日志
./vendor/bin/sail logs laravel.test -f

# 进入容器手动测试命令
./vendor/bin/sail shell
php artisan websocket:serve
```

### 端口冲突

确保 `compose.yaml` 中已暴露对应端口:

```yaml
ports:
    - '${WEBSOCKET_PORT:-8081}:8081'
```

### 配置未生效

1. 确认 volume 映射正确
2. 重启容器: `./vendor/bin/sail restart`
3. 检查配置文件语法: `supervisord -c /etc/supervisor/conf.d/supervisord.conf -n`
