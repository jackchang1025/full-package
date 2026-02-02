# 飞鹰管理系统 V2

基于 Laravel 12 + Vue 3 + Naive UI 的设备管理平台。

## 技术栈

- **后端**: Laravel 12, PHP 8.5
- **前端**: Vue 3, Inertia.js, Naive UI, TypeScript
- **数据库**: MySQL 8.4, Redis
- **WebSocket**: Laravel Reverb
- **开发环境**: Laravel Sail (Docker)

## 快速开始

### 环境要求

- Docker Desktop
- Git

### 安装步骤

```bash
# 1. 克隆项目
git clone <repository-url>
cd full-package/app

# 2. 复制环境配置
cp .env.example .env

# 3. 安装 PHP 依赖
docker run --rm \
    -u "$(id -u):$(id -g)" \
    -v "$(pwd):/var/www/html" \
    -w /var/www/html \
    laravelsail/php85-composer:latest \
    composer install --ignore-platform-reqs

# 4. 启动开发环境
./dev-start.sh

# 5. 启动前端开发服务器
./vendor/bin/sail npm run dev
```

### 访问地址

| 服务 | 地址 |
|------|------|
| 应用 | http://localhost:8000 |
| Vite HMR | http://localhost:5173 |
| MySQL | localhost:3307 |
| Redis | localhost:6380 |

## 开发命令

```bash
# 启动/停止服务
./vendor/bin/sail up -d
./vendor/bin/sail down

# 前端开发
./vendor/bin/sail npm run dev      # 开发模式 (热重载)
./vendor/bin/sail npm run build    # 生产构建

# 数据库
./vendor/bin/sail artisan migrate           # 运行迁移
./vendor/bin/sail artisan migrate:fresh     # 重置数据库
./vendor/bin/sail artisan db:seed           # 填充数据

# 缓存
./vendor/bin/sail artisan cache:clear
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan route:clear

# 日志
./vendor/bin/sail logs -f
./vendor/bin/sail logs laravel.test -f
```

## 项目结构

```
app/
├── app/
│   ├── Http/Controllers/     # 控制器
│   ├── Models/               # Eloquent 模型
│   ├── Events/               # 广播事件
│   └── Actions/              # Fortify 动作
├── resources/
│   └── ts/
│       ├── Pages/            # 页面组件
│       ├── Layouts/          # 布局组件
│       └── Components/       # 通用组件
├── routes/
│   ├── web.php               # Web 路由
│   └── channels.php          # 广播频道
└── database/
    └── migrations/           # 数据库迁移
```

## 功能模块

### 用户认证
- 登录/注册
- 密码重置
- 两步验证 (2FA)

### 设备管理
- 设备列表
- 设备详情
- 实时状态更新 (WebSocket)

### APK 构建
- 模板选择
- 自定义构建
- 构建状态追踪

### 用户设置
- 个人资料
- 修改密码
- 订阅信息

## 测试账号

```
邮箱: admin@feiying.local
密码: password
```

## 文档

- [AGENTS.md](../AGENTS.md) - AI 代理开发指南
- [docs/legacy/](../docs/legacy/) - 旧系统文档

## License

Proprietary
