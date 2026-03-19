# 贡献指南

飞鹰管理系统 V2 开发贡献指南。

## 技术栈

| 层 | 技术 | 版本 |
|---|------|------|
| 后端 | Laravel | 12 |
| 语言 | PHP | 8.5 |
| 前端 | Vue 3 + Inertia.js | Vue 3.5, Inertia 2 |
| UI 库 | Naive UI | 2.43 |
| 样式 | Tailwind CSS | 4 |
| 类型 | TypeScript | 5.8 |
| 构建 | Vite | 7 |
| WebSocket | PHP Swoole | - |
| 数据库 | MySQL | 8.4 |
| 缓存 | Redis | - |
| 容器 | Laravel Sail (Docker) | - |

## 开发工作流

### 1. 环境准备

```bash
cd app
cp .env.example .env   # 首次需要复制环境变量
./dev-start.sh          # 启动 Docker 容器 + 数据库迁移
./vendor/bin/sail npm run dev  # 启动前端 HMR
```

### 2. 日常开发

```bash
# 启动/停止容器
./vendor/bin/sail up -d
./vendor/bin/sail down

# 前端开发（必须使用 dev，不要 build）
./vendor/bin/sail npm run dev
```

### 3. 提交前检查

```bash
# PHP 代码风格
./vendor/bin/sail pint

# 运行测试
./vendor/bin/sail pest

# TypeScript 类型检查
./vendor/bin/sail npx tsc --noEmit
```

## 可用脚本命令

### npm scripts (package.json)

| 命令 | 说明 |
|------|------|
| `npm run dev` | 启动 Vite 开发服务器（HMR 热重载） |
| `npm run build` | 生产环境构建（仅部署时使用） |
| `npm run preview` | 预览生产构建结果 |

### Composer scripts (composer.json)

| 命令 | 说明 |
|------|------|
| `composer setup` | 一键初始化：安装依赖、生成 .env、密钥、迁移、npm 构建 |
| `composer dev` | 并发启动 serve + queue + pail + vite（开发全家桶） |
| `composer test` | 清配置缓存后运行测试 |

### Artisan 常用命令

| 命令 | 说明 |
|------|------|
| `artisan migrate` | 运行数据库迁移 |
| `artisan migrate:fresh --seed` | 重置数据库并填充 |
| `artisan cache:clear` | 清除应用缓存 |
| `artisan config:clear` | 清除配置缓存 |
| `artisan route:clear` | 清除路由缓存 |
| `artisan websocket:serve` | 启动 Swoole WebSocket 服务器 |
| `artisan pint` | PHP 代码风格修复 (Laravel Pint) |
| `artisan tinker` | 交互式 REPL |

> 所有 artisan 命令通过 `./vendor/bin/sail artisan ...` 在容器内执行。

## 环境配置

所有环境变量定义在 `.env.example`，按功能分组如下：

### 应用基础

| 变量 | 说明 | 示例 |
|------|------|------|
| `APP_NAME` | 应用名称，显示在登录页等 | `"安卓设备管理大师"` |
| `APP_LOGO` | 站点 Logo 图片路径 | `/logo.png` |
| `APP_FAVICON` | 浏览器标签页图标路径 | `/favicon.ico` |
| `APP_ENV` | 运行环境 | `local` / `production` |
| `APP_KEY` | 加密密钥（artisan key:generate 自动生成） | `base64:...` |
| `APP_DEBUG` | 调试模式（生产必须 false） | `false` |
| `APP_URL` | 应用对外 URL | `http://localhost` |
| `TIMEZONE` | 时区设置 | `Asia/Shanghai` |

### 语言与本地化

| 变量 | 说明 | 示例 |
|------|------|------|
| `APP_LOCALE` | 默认语言 | `zh_CN` |
| `APP_FALLBACK_LOCALE` | 回退语言 | `zh_CN` |
| `APP_FAKER_LOCALE` | Faker 数据生成语言 | `zh_CN` |

### 数据库

| 变量 | 说明 | 示例 |
|------|------|------|
| `DB_CONNECTION` | 数据库驱动 | `mysql` |
| `DB_HOST` | 数据库主机（容器内用服务名） | `mysql` |
| `DB_PORT` | 数据库端口 | `3306` |
| `DB_DATABASE` | 数据库名 | `feiying_v2` |
| `DB_USERNAME` | 数据库用户名 | `sail` |
| `DB_PASSWORD` | 数据库密码 | `password` |

### 缓存与会话

| 变量 | 说明 | 示例 |
|------|------|------|
| `CACHE_STORE` | 缓存驱动 | `redis` |
| `SESSION_DRIVER` | 会话驱动 | `database` |
| `SESSION_LIFETIME` | 会话有效期（分钟） | `120` |
| `SESSION_COOKIE` | 会话 Cookie 名 | `feiying_session` |
| `REDIS_HOST` | Redis 主机 | `redis` |
| `REDIS_PORT` | Redis 端口 | `6379` |

### 队列与广播

| 变量 | 说明 | 示例 |
|------|------|------|
| `QUEUE_CONNECTION` | 队列驱动 | `database` |
| `BROADCAST_CONNECTION` | 广播驱动 | `log` |

### 邮件

| 变量 | 说明 | 示例 |
|------|------|------|
| `MAIL_MAILER` | 邮件驱动 | `log` / `smtp` |
| `MAIL_HOST` | SMTP 服务器 | `127.0.0.1` |
| `MAIL_PORT` | SMTP 端口 | `2525` |
| `MAIL_USERNAME` | SMTP 用户名 | `null` |
| `MAIL_PASSWORD` | SMTP 密码 | `null` |
| `MAIL_FROM_ADDRESS` | 发件人邮箱 | `noreply@feiying.local` |
| `MAIL_FROM_NAME` | 发件人名称 | `${APP_NAME}` |

### WebSocket

| 变量 | 说明 | 示例 |
|------|------|------|
| `WEBSOCKET_URL` | 前端连接的 WebSocket 地址 | `ws://your-server-ip:8081` |
| `WEBSOCKET_HOST` | WebSocket 服务器绑定地址 | `your-server-ip` |
| `WEBSOCKET_PORT` | WebSocket 服务器端口 | `8081` |
| `DEVICE_AUTH_SECRET` | 设备认证密钥（生产必须设强随机值） | - |
| `WEBSOCKET_LOG_LEVEL` | 设备数据转发日志级别 | `info` |

### Sail / Docker

| 变量 | 说明 | 示例 |
|------|------|------|
| `WWWUSER` | 容器内用户 UID（避免权限问题） | `1001` |
| `WWWGROUP` | 容器内用户 GID | `1002` |
| `APP_PORT` | 应用映射到宿主机的端口 | `8080` |

### 前端

| 变量 | 说明 | 示例 |
|------|------|------|
| `VITE_APP_NAME` | 传递给 Vite 的应用名 | `${APP_NAME}` |
| `VITE_DEV_HOST` | Vite 开发服务器绑定地址（局域网开发） | `your-server-ip` |

### APK 签名

| 变量 | 必填 | 说明 | 示例 |
|------|------|------|------|
| `APK_SIGNING_MODE` | 否 | 签名模式 | `release`（默认）/ `debug` |
| `APK_KEYSTORE_PATH` | 否 | 自定义 keystore 路径（留空自动生成） | `/path/to/keystore.jks` |
| `APK_KEYSTORE_PASS` | 否 | keystore 密码 | - |
| `APK_KEY_ALIAS` | 否 | key 别名 | - |
| `APK_KEY_PASS` | 否 | key 密码 | - |
| `APK_KEYSTORE_DNAME` | 否 | 自动生成 keystore 的证书 DN | `CN=App,OU=Mobile,O=Company,L=City,ST=State,C=CN` |
| `APK_ENABLE_AUTO_WAKE_SCREEN` | 否 | 黑屏自动唤醒（TransparentActivity） | `true`（默认）/ `false` |

### WebSocket 心跳

| 变量 | 说明 | 默认值 |
|------|------|--------|
| `WEBSOCKET_HEARTBEAT_CHECK_INTERVAL` | 定时器扫描在线设备间隔（秒） | `25` |
| `WEBSOCKET_HEARTBEAT_IDLE_TIME` | Swoole TCP 空闲超时（秒），需大于面板 30 秒心跳 | `75` |
| `WEBSOCKET_HEARTBEAT_TIMEOUT` | 设备停止 ping 后判定离线的超时（秒） | `75` |
| `WEBSOCKET_HEARTBEAT_PROBE_INTERVAL` | 疑似离线时探测包发送间隔（秒） | `10` |
| `WEBSOCKET_HEARTBEAT_MAX_PROBES` | 连续探测无响应后强制断开次数 | `3` |

### 部署

| 变量 | 说明 | 示例 |
|------|------|------|
| `APP_IMAGE` | 远程镜像地址（可选，配置后 deploy.sh 从远程拉取） | `registry.cn-hangzhou.aliyuncs.com/yourns/feiying-app:latest` |

### 后台设置

| 变量 | 说明 | 示例 |
|------|------|------|
| `SITE_LOGO_MAX_SIZE_KB` | Logo 上传大小上限（KB） | `10240` (10MB) |

## 测试流程

### 运行全部测试

```bash
./vendor/bin/sail pest
```

### 运行特定模块

```bash
# WebSocket 测试
./vendor/bin/sail pest tests/Feature/WebSocket/

# 筛选测试名
./vendor/bin/sail pest --filter="登录"
```

### 测试说明

- 使用 **Pest** 测试框架
- WebSocket 测试使用随机端口并自动清理，无需手动管理服务器
- 运行前无需手动清缓存，`composer test` 脚本已包含 `config:clear`

## 代码规范

### PHP / Laravel

- 使用 **Laravel Pint** 进行代码风格检查
- 使用 Eloquent ORM，避免原生 SQL
- 所有输入添加验证规则
- 遵循 Laravel 最佳实践

### Vue / TypeScript

- 使用 Composition API + `<script setup>`
- 所有组件使用 TypeScript
- UI 组件统一使用 Naive UI
- 使用 Inertia.js 处理页面路由

### 全局消息机制

后端通过 `->with('success', '消息')` 或 `->with('error', '消息')` 闪存消息，前端通过 `FlashMessageHandler` 组件自动以 Naive UI toast 展示，无需额外处理。

## 访问地址（开发环境）

| 服务 | 地址 |
|------|------|
| 应用 | http://localhost:8000 |
| WebSocket | ws://localhost:8081 |
| Vite HMR | http://localhost:5173 |
| MySQL | localhost:3307 |
| Redis | localhost:6380 |
