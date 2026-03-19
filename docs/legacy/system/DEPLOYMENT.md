# 飞鹰管理系统 - 部署文档

> 本文档提供完整的生产环境部署指南。

## 目录

1. [系统要求](#系统要求)
2. [部署架构](#部署架构)
3. [快速部署 (Docker)](#快速部署-docker)
4. [手动部署](#手动部署)
5. [配置说明](#配置说明)
6. [SSL/HTTPS 配置](#sslhttps-配置)
7. [WebSocket 服务部署](#websocket-服务部署)
8. [Nginx 反向代理配置](#nginx-反向代理配置)
9. [安全加固](#安全加固)
10. [备份与恢复](#备份与恢复)
11. [故障排查](#故障排查)

---

## 系统要求

### 最低配置

| 组件 | 要求 |
|------|------|
| CPU | 1 核 |
| 内存 | 1 GB |
| 磁盘 | 20 GB |
| 操作系统 | Ubuntu 20.04+ / CentOS 7+ / Debian 10+ |

### 推荐配置

| 组件 | 要求 |
|------|------|
| CPU | 2 核+ |
| 内存 | 2 GB+ |
| 磁盘 | 50 GB+ SSD |

### 软件依赖

| 软件 | 版本要求 |
|------|----------|
| PHP | 8.0+ |
| MySQL/MariaDB | 10.4+ / MySQL 8.0+ |
| Node.js | 16+ |
| Nginx/Apache | 最新稳定版 |

### PHP 扩展要求

```
pdo_mysql    # 数据库连接
mbstring     # 多字节字符串
openssl      # 加密功能
curl         # HTTP 请求
gd           # 图像处理
json         # JSON 处理
fileinfo     # 文件类型检测
```

---

## 部署架构

```
                    ┌─────────────────┐
                    │   Nginx/Apache  │
                    │   (反向代理)     │
                    └────────┬────────┘
                             │
           ┌─────────────────┼─────────────────┐
           │                 │                 │
           ▼                 ▼                 ▼
    ┌─────────────┐   ┌─────────────┐   ┌─────────────┐
    │  PHP-FPM    │   │  WebSocket  │   │   静态资源   │
    │  (API)      │   │  (Node.js)  │   │  (Vue.js)   │
    └──────┬──────┘   └──────┬──────┘   └─────────────┘
           │                 │
           └────────┬────────┘
                    ▼
           ┌─────────────────┐
           │  MySQL/MariaDB  │
           └─────────────────┘
```

**端口规划：**

| 服务 | 端口 | 说明 |
|------|------|------|
| HTTP | 80 | Web 服务 |
| HTTPS | 443 | SSL Web 服务 |
| WebSocket | 8080 | 实时通信服务 |
| MySQL | 3306 | 数据库 (仅内网) |

---

## 快速部署 (Docker)

### 1. 创建 docker-compose.yml

```yaml
version: '3.8'

services:
  web:
    image: php:8.2-apache
    container_name: feiying-web
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./:/var/www/html
      - ./xampp_configs/configs/php.ini:/usr/local/etc/php/php.ini
    depends_on:
      - db
    environment:
      - TZ=Asia/Shanghai
    restart: unless-stopped

  db:
    image: mariadb:10.4
    container_name: feiying-db
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./all_databases.sql:/docker-entrypoint-initdb.d/init.sql
    environment:
      - MYSQL_ROOT_PASSWORD=your_secure_password
      - MYSQL_DATABASE=clients
      - TZ=Asia/Shanghai
    restart: unless-stopped

  websocket:
    image: node:18-alpine
    container_name: feiying-ws
    working_dir: /app
    ports:
      - "8080:8080"
    volumes:
      - ./api/api/ws:/app
    command: node websocket-server.js
    environment:
      - TZ=Asia/Shanghai
    restart: unless-stopped

volumes:
  mysql_data:
```

### 2. 启动服务

```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

---

## 手动部署

### 步骤 1: 安装系统依赖

#### Ubuntu/Debian

```bash
# 更新系统
sudo apt update && sudo apt upgrade -y

# 安装 Nginx
sudo apt install -y nginx

# 安装 PHP 及扩展
sudo apt install -y php8.2-fpm php8.2-mysql php8.2-mbstring \
    php8.2-curl php8.2-gd php8.2-xml php8.2-zip php8.2-fileinfo

# 安装 MariaDB
sudo apt install -y mariadb-server mariadb-client

# 安装 Node.js
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs

# 安装 PM2 (Node.js 进程管理)
sudo npm install -g pm2
```

#### CentOS/RHEL

```bash
# 安装 EPEL 仓库
sudo yum install -y epel-release

# 安装 Nginx
sudo yum install -y nginx

# 安装 PHP (使用 Remi 仓库)
sudo yum install -y https://rpms.remirepo.net/enterprise/remi-release-7.rpm
sudo yum install -y php82-php-fpm php82-php-mysqlnd php82-php-mbstring \
    php82-php-curl php82-php-gd php82-php-xml php82-php-zip

# 安装 MariaDB
sudo yum install -y mariadb-server mariadb

# 安装 Node.js
curl -fsSL https://rpm.nodesource.com/setup_18.x | sudo bash -
sudo yum install -y nodejs

# 安装 PM2
sudo npm install -g pm2
```

### 步骤 2: 配置数据库

```bash
# 启动 MariaDB
sudo systemctl start mariadb
sudo systemctl enable mariadb

# 安全配置
sudo mysql_secure_installation

# 登录 MySQL
sudo mysql -u root -p

# 创建数据库和用户
CREATE DATABASE clients CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER 'feiying'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON clients.* TO 'feiying'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# 导入数据库结构
mysql -u feiying -p clients < all_databases.sql
```

### 步骤 3: 部署项目文件

```bash
# 创建项目目录
sudo mkdir -p /var/www/feiying

# 复制项目文件
sudo cp -r /path/to/full-package/* /var/www/feiying/

# 设置权限
sudo chown -R www-data:www-data /var/www/feiying
sudo chmod -R 755 /var/www/feiying

# 设置可写目录权限
sudo chmod -R 777 /var/www/feiying/api/api/error_logs
sudo chmod -R 777 /var/www/feiying/eaod_logs
sudo chmod -R 777 /var/www/feiying/user
```

### 步骤 4: 配置 PHP

编辑 `/etc/php/8.2/fpm/php.ini`:

```ini
; 时区设置
date.timezone = Asia/Shanghai

; 上传限制
upload_max_filesize = 50M
post_max_size = 50M
max_execution_time = 300
memory_limit = 256M

; 错误处理 (生产环境)
display_errors = Off
log_errors = On
error_log = /var/log/php/error.log
```

重启 PHP-FPM:

```bash
sudo systemctl restart php8.2-fpm
```

---

## 配置说明

### 数据库配置

编辑 `private/private/Eaod85401.php`:

```php
<?php
// 数据库配置 - 修改为你的实际配置
define('DB_ServerName', '127.0.0.1');
define('DB_UserName', 'feiying');           // 数据库用户名
define('DB_Password', 'your_secure_password'); // 数据库密码
define('DB_Name', 'clients');

// 加密配置 - 生产环境请更换为随机字符串
define('Secrit_Key', '替换为32位随机字符串');
define('SIV', '替换为16位随机字符串');
define('SIV_jec', '替换为32位随机字符串');

// 管理员密钥 - 必须更换
define('Admin_Key', '替换为强密码');

// 邮件配置
define('Email_Host', 'smtp.your-domain.com');
define('My_Name', 'Your Site Support');
define('Email_Name', 'support@your-domain.com');
define('Email_Pass', 'your_email_password');
```

### 生成随机密钥

```bash
# 生成 32 位随机字符串
openssl rand -base64 32

# 生成 16 位随机字符串
openssl rand -base64 16
```

---

## SSL/HTTPS 配置

### 使用 Let's Encrypt (推荐)

```bash
# 安装 Certbot
sudo apt install -y certbot python3-certbot-nginx

# 获取证书
sudo certbot --nginx -d your-domain.com -d www.your-domain.com

# 自动续期测试
sudo certbot renew --dry-run

# 设置自动续期 (crontab)
echo "0 0 1 * * /usr/bin/certbot renew --quiet" | sudo crontab -
```

### 手动配置 SSL

将证书文件放置到:
- 证书: `/etc/ssl/certs/your-domain.crt`
- 私钥: `/etc/ssl/private/your-domain.key`

---

## WebSocket 服务部署

### 安装依赖

```bash
cd /var/www/feiying/api/api/ws
npm install
```

### 使用 PM2 管理

```bash
# 启动 WebSocket 服务
pm2 start websocket-server.js --name feiying-ws

# 设置开机自启
pm2 startup
pm2 save

# 查看状态
pm2 status

# 查看日志
pm2 logs feiying-ws

# 重启服务
pm2 restart feiying-ws
```

### 创建 PM2 配置文件

创建 `ecosystem.config.js`:

```javascript
module.exports = {
  apps: [{
    name: 'feiying-ws',
    script: 'websocket-server.js',
    cwd: '/var/www/feiying/api/api/ws',
    instances: 1,
    autorestart: true,
    watch: false,
    max_memory_restart: '500M',
    env: {
      NODE_ENV: 'production'
    },
    error_file: '/var/log/pm2/feiying-ws-error.log',
    out_file: '/var/log/pm2/feiying-ws-out.log',
    log_date_format: 'YYYY-MM-DD HH:mm:ss'
  }]
};
```

启动:

```bash
pm2 start ecosystem.config.js
```

---

## Nginx 反向代理配置

创建 `/etc/nginx/sites-available/feiying`:

```nginx
# HTTP 重定向到 HTTPS
server {
    listen 80;
    server_name your-domain.com www.your-domain.com;
    return 301 https://$server_name$request_uri;
}

# HTTPS 主配置
server {
    listen 443 ssl http2;
    server_name your-domain.com www.your-domain.com;

    # SSL 证书
    ssl_certificate /etc/ssl/certs/your-domain.crt;
    ssl_certificate_key /etc/ssl/private/your-domain.key;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256;
    ssl_prefer_server_ciphers off;

    # 项目根目录
    root /var/www/feiying;
    index index.php index.html;

    # 日志
    access_log /var/log/nginx/feiying-access.log;
    error_log /var/log/nginx/feiying-error.log;

    # 前端静态资源
    location / {
        try_files $uri $uri/ /index.html;
    }

    # API 路由
    location /api/ {
        alias /var/www/feiying/api/api/;
        
        location ~ \.php$ {
            fastcgi_pass unix:/var/run/php/php8.2-fpm.sock;
            fastcgi_param SCRIPT_FILENAME $request_filename;
            include fastcgi_params;
        }
    }

    # 管理后台
    location /private/ {
        alias /var/www/feiying/private/private/;
        
        location ~ \.php$ {
            fastcgi_pass unix:/var/run/php/php8.2-fpm.sock;
            fastcgi_param SCRIPT_FILENAME $request_filename;
            include fastcgi_params;
        }
    }

    # WebSocket 代理
    location /ws/ {
        proxy_pass http://127.0.0.1:8080/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_read_timeout 86400;
    }

    # 禁止访问敏感文件
    location ~ /\.(htaccess|git|env) {
        deny all;
    }

    location ~ \.sql$ {
        deny all;
    }

    # PHP 文件处理
    location ~ \.php$ {
        fastcgi_pass unix:/var/run/php/php8.2-fpm.sock;
        fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
        include fastcgi_params;
    }
}
```

启用配置:

```bash
# 创建软链接
sudo ln -s /etc/nginx/sites-available/feiying /etc/nginx/sites-enabled/

# 测试配置
sudo nginx -t

# 重载 Nginx
sudo systemctl reload nginx
```

---

## 安全加固

### 1. 防火墙配置

```bash
# UFW (Ubuntu)
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP
sudo ufw allow 443/tcp   # HTTPS
sudo ufw enable

# 不要开放 3306 (MySQL) 和 8080 (WebSocket) 到公网
```

### 2. 文件权限

```bash
# 设置正确的所有者
sudo chown -R www-data:www-data /var/www/feiying

# 目录权限 755, 文件权限 644
sudo find /var/www/feiying -type d -exec chmod 755 {} \;
sudo find /var/www/feiying -type f -exec chmod 644 {} \;

# 可写目录
sudo chmod -R 775 /var/www/feiying/eaod_logs
sudo chmod -R 775 /var/www/feiying/api/api/error_logs
sudo chmod -R 775 /var/www/feiying/user
```

### 3. 隐藏敏感信息

```bash
# 禁止直接访问配置文件
# 在 Nginx 配置中已包含

# 移除 SQL 文件 (导入后)
rm /var/www/feiying/all_databases.sql
rm /var/www/feiying/clients.sql
```

### 4. 更换默认密钥

**必须更换以下配置 (Eaod85401.php):**

- `DB_Password` - 数据库密码
- `Secrit_Key` - 加密密钥
- `SIV` - 初始化向量
- `Admin_Key` - 管理员密钥

---

## 备份与恢复

### 数据库备份

```bash
# 创建备份脚本 /opt/scripts/backup-db.sh
#!/bin/bash
BACKUP_DIR="/var/backups/feiying"
DATE=$(date +%Y%m%d_%H%M%S)
mkdir -p $BACKUP_DIR

# 备份数据库
mysqldump -u feiying -p'your_password' clients > $BACKUP_DIR/clients_$DATE.sql

# 压缩
gzip $BACKUP_DIR/clients_$DATE.sql

# 保留最近 7 天的备份
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "Backup completed: clients_$DATE.sql.gz"
```

设置定时任务:

```bash
# 每天凌晨 2 点备份
echo "0 2 * * * /opt/scripts/backup-db.sh" | sudo crontab -
```

### 文件备份

```bash
# 备份用户数据和日志
tar -czvf /var/backups/feiying/files_$(date +%Y%m%d).tar.gz \
    /var/www/feiying/user \
    /var/www/feiying/eaod_logs \
    /var/www/feiying/private/private/remarks.json
```

### 恢复

```bash
# 恢复数据库
gunzip < /var/backups/feiying/clients_20260128.sql.gz | mysql -u feiying -p clients

# 恢复文件
tar -xzvf /var/backups/feiying/files_20260128.tar.gz -C /
```

---

## 故障排查

### 常见问题

#### 1. PHP 连接数据库失败

```bash
# 检查 MySQL 服务状态
sudo systemctl status mariadb

# 测试连接
mysql -u feiying -p -h 127.0.0.1 clients

# 检查 PHP PDO 扩展
php -m | grep pdo
```

#### 2. WebSocket 连接失败

```bash
# 检查 WebSocket 服务状态
pm2 status

# 查看日志
pm2 logs feiying-ws

# 检查端口占用
netstat -tlnp | grep 8080

# 手动测试
curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" \
    http://127.0.0.1:8080/
```

#### 3. 权限问题

```bash
# 检查文件所有者
ls -la /var/www/feiying

# 修复权限
sudo chown -R www-data:www-data /var/www/feiying
```

#### 4. 502 Bad Gateway

```bash
# 检查 PHP-FPM 状态
sudo systemctl status php8.2-fpm

# 检查 PHP-FPM socket
ls -la /var/run/php/php8.2-fpm.sock

# 重启 PHP-FPM
sudo systemctl restart php8.2-fpm
```

### 日志位置

| 日志 | 位置 |
|------|------|
| Nginx 访问日志 | `/var/log/nginx/feiying-access.log` |
| Nginx 错误日志 | `/var/log/nginx/feiying-error.log` |
| PHP 错误日志 | `/var/log/php/error.log` |
| WebSocket 日志 | `pm2 logs feiying-ws` |
| 应用日志 | `/var/www/feiying/api/api/log.txt` |
| GeoIP 日志 | `/var/www/feiying/api/api/geoip_errors.log` |

### 性能监控

```bash
# 查看系统资源
htop

# 查看 MySQL 状态
mysqladmin -u root -p status

# 查看 Nginx 连接数
netstat -an | grep :80 | wc -l

# 查看 PHP-FPM 进程
ps aux | grep php-fpm
```

---

## 部署检查清单

- [ ] 系统依赖已安装 (PHP, MySQL, Node.js, Nginx)
- [ ] 数据库已创建并导入
- [ ] 项目文件已部署到正确位置
- [ ] 文件权限已正确设置
- [ ] 数据库配置已更新 (Eaod85401.php)
- [ ] 所有默认密钥已更换
- [ ] SSL 证书已配置
- [ ] Nginx 配置已启用
- [ ] WebSocket 服务已启动 (PM2)
- [ ] 防火墙规则已配置
- [ ] 备份脚本已设置
- [ ] 敏感文件已删除 (SQL 文件)

---

## 联系支持

如遇到部署问题，请检查:

1. 系统日志和应用日志
2. 确认所有服务正常运行
3. 验证网络连接和端口开放状态
