# 开发环境详解

飞鹰管理系统 V2 开发环境配置与常用命令参考。

## 环境要求

- Docker Desktop
- Git
- Node.js 18+ (可选，用于本地开发)

## 目录结构

```
app/
├── app/
│   ├── Actions/Fortify/      # 认证动作类
│   ├── Events/               # 广播事件
│   ├── Http/
│   │   ├── Controllers/      # 控制器
│   │   └── Middleware/       # 中间件
│   ├── Models/               # Eloquent 模型
│   └── Providers/            # 服务提供者
├── config/                   # 配置文件
├── database/
│   ├── migrations/           # 数据库迁移
│   └── seeders/              # 数据填充
├── resources/
│   ├── css/                  # 样式文件
│   ├── ts/                   # TypeScript 源码
│   │   ├── Components/       # 通用组件
│   │   ├── Layouts/          # 布局组件
│   │   └── Pages/            # 页面组件
│   └── views/                # Blade 模板
├── routes/                   # 路由定义
├── public/                   # 公共资源
├── .env                      # 环境配置
├── compose.yaml              # Docker Compose
├── dev-start.sh              # 开发启动脚本
└── vite.config.ts            # Vite 配置
```

## 快速启动

### 方式一：使用启动脚本

```bash
cd app
./dev-start.sh
./vendor/bin/sail npm run dev
```

### 方式二：手动启动

```bash
cd app

# 启动 Docker 容器
./vendor/bin/sail up -d

# 等待 MySQL 就绪后运行迁移
./vendor/bin/sail artisan migrate

# 启动前端开发服务器
./vendor/bin/sail npm run dev
```

## 常用命令

### Sail 容器管理

```bash
./vendor/bin/sail up -d              # 启动容器 (后台)
./vendor/bin/sail down               # 停止容器
./vendor/bin/sail restart            # 重启容器
./vendor/bin/sail logs -f            # 查看所有日志
./vendor/bin/sail logs laravel.test  # 查看应用日志
./vendor/bin/sail shell              # 进入容器 Shell
./vendor/bin/sail root-shell         # 以 root 进入容器
```

### Artisan 命令

```bash
# 数据库
./vendor/bin/sail artisan migrate              # 运行迁移
./vendor/bin/sail artisan migrate:fresh        # 重置数据库
./vendor/bin/sail artisan migrate:rollback     # 回滚迁移
./vendor/bin/sail artisan db:seed              # 填充数据

# 缓存
./vendor/bin/sail artisan cache:clear          # 清除缓存
./vendor/bin/sail artisan config:clear         # 清除配置缓存
./vendor/bin/sail artisan route:clear          # 清除路由缓存
./vendor/bin/sail artisan view:clear           # 清除视图缓存

# 开发工具
./vendor/bin/sail artisan tinker               # 交互式 Shell
./vendor/bin/sail artisan route:list           # 查看路由列表
./vendor/bin/sail artisan make:controller Name # 创建控制器
./vendor/bin/sail artisan make:model Name -m   # 创建模型+迁移
./vendor/bin/sail artisan make:event Name      # 创建事件

# WebSocket
./vendor/bin/sail artisan websocket:serve   # 启动 Swoole WebSocket 服务器
```

### 前端命令

```bash
./vendor/bin/sail npm run dev        # 开发模式 (热重载) ⚠️ 开发时必须使用
./vendor/bin/sail npm run build      # 生产构建 (仅部署时使用)
./vendor/bin/sail npm install        # 安装依赖
```

**⚠️ 重要：开发环境必须使用 `npm run dev`**

开发时请勿使用 `npm run build`，原因：
1. `build` 会将代码编译为静态文件，修改代码后需要重新编译
2. `dev` 提供热重载，修改代码后自动刷新浏览器
3. 环境变量 (`VITE_*`) 在 `build` 时被固化，修改 `.env` 后需重新编译

如果误执行了 `build`，请删除 `public/build` 目录后重新运行 `dev`：
```bash
rm -rf public/build
./vendor/bin/sail npm run dev
```

### 数据库

```bash
./vendor/bin/sail mysql              # MySQL 客户端
./vendor/bin/sail artisan db:show    # 显示数据库信息
```

## 访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 应用 | http://localhost:8899 | Laravel 应用 |
| Vite HMR | http://localhost:5173 | 前端热重载 |
| WebSocket | ws://localhost:8081 | Swoole WebSocket 服务器 |
| MySQL | localhost:3307 | 数据库 (用户: sail, 密码: password) |
| Redis | localhost:6380 | 缓存服务 |

## 环境变量

关键配置 (`.env`):

```bash
# 应用
APP_NAME="飞鹰管理系统"
APP_ENV=local
APP_DEBUG=true
APP_URL=http://localhost

# 数据库
DB_CONNECTION=mysql
DB_HOST=mysql
DB_PORT=3306
DB_DATABASE=feiying_v2
DB_USERNAME=sail
DB_PASSWORD=password

# Sail 端口 (避免冲突)
APP_PORT=8899
DB_PORT=3306
REDIS_PORT=6379

# WebSocket (Swoole 服务器)
VITE_WEBSOCKET_URL=ws://localhost:8081
```

## 数据库模型

### 核心模型

| 模型 | 表名 | 用途 |
|------|------|------|
| `User` | `users` | 用户 (管理员/客户) |
| `Device` | `devices` | 受控设备 |
| `AppTemplate` | `app_templates` | APK 模板 |
| `AppBuild` | `app_builds` | APK 构建记录 |

### 模型关系

```
User
├── hasMany → Device
└── hasMany → AppBuild

AppTemplate
└── hasMany → AppBuild

Device
└── belongsTo → User

AppBuild
├── belongsTo → User
└── belongsTo → AppTemplate
```

## 路由结构

### Web 路由

| 方法 | 路径 | 名称 | 说明 |
|------|------|------|------|
| GET | `/` | - | 欢迎页 |
| GET | `/login` | login | 登录 |
| GET | `/register` | register | 注册 |
| GET | `/dashboard` | dashboard | 控制台 |
| GET | `/devices` | devices.index | 设备列表 |
| GET | `/devices/{id}` | devices.show | 设备详情 |
| DELETE | `/devices/{id}` | devices.destroy | 删除设备 |
| GET | `/builds` | builds.index | 构建列表 |
| GET | `/builds/create` | builds.create | 创建构建 |
| POST | `/builds` | builds.store | 保存构建 |
| GET | `/builds/{id}` | builds.show | 构建详情 |
| DELETE | `/builds/{id}` | builds.destroy | 删除构建 |
| GET | `/settings/profile` | settings.profile | 用户设置 |

## 前端页面

### 页面组件 (`resources/ts/Pages/`)

| 页面 | 路径 | 功能 |
|------|------|------|
| `Welcome.vue` | `/` | 欢迎页 |
| `Auth/Login.vue` | `/login` | 登录 |
| `Auth/Register.vue` | `/register` | 注册 |
| `Dashboard/Index.vue` | `/dashboard` | 控制台 |
| `Devices/Index.vue` | `/devices` | 设备列表 |
| `Devices/Show.vue` | `/devices/{id}` | 设备详情 |
| `Builds/Index.vue` | `/builds` | 构建列表 |
| `Builds/Create.vue` | `/builds/create` | 创建构建 |
| `Builds/Show.vue` | `/builds/{id}` | 构建详情 |
| `Settings/Profile.vue` | `/settings/profile` | 用户设置 |

### 布局组件 (`resources/ts/Layouts/`)

| 组件 | 用途 |
|------|------|
| `DefaultLayout.vue` | Naive UI Provider 包装 |
| `AuthenticatedLayout.vue` | 登录后布局 (侧边栏 + 导航) |

## 代码风格

### PHP / Laravel

```php
<?php

namespace App\Http\Controllers;

use App\Models\Device;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class DeviceController extends Controller
{
    public function index(Request $request): Response
    {
        $devices = Device::where('user_id', $request->user()->id)
            ->orderBy('last_seen_at', 'desc')
            ->paginate(20);

        return Inertia::render('Devices/Index', [
            'devices' => $devices,
        ]);
    }
}
```

### TypeScript / Vue

```vue
<script setup lang="ts">
import { Head } from '@inertiajs/vue3';
import { NCard, NButton } from 'naive-ui';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';

interface Props {
    devices: Device[];
}

const props = defineProps<Props>();
</script>

<template>
    <Head title="设备管理" />
    <AuthenticatedLayout>
        <NCard>
            <!-- 内容 -->
        </NCard>
    </AuthenticatedLayout>
</template>
```

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 控制器 | PascalCase + Controller | `DeviceController` |
| 模型 | PascalCase 单数 | `Device`, `AppBuild` |
| 迁移 | snake_case | `create_devices_table` |
| Vue 组件 | PascalCase | `DeviceList.vue` |
| 路由名 | kebab-case | `devices.index` |
| 数据库字段 | snake_case | `last_seen_at` |

## WebSocket 服务

本项目使用 PHP Swoole 实现 WebSocket 服务器，用于设备实时通信。

### 启动 WebSocket 服务器

```bash
./vendor/bin/sail artisan websocket:serve
```

### WebSocket 功能

| 功能 | 说明 |
|------|------|
| 设备列表实时更新 | 页面加载后自动连接，每 5 秒刷新设备状态 |
| 设备远程控制 | 屏幕共享、触摸控制、文件管理等 |
| 数据获取 | 短信、联系人、应用列表、键盘记录等 |

### 前端 WebSocket 连接

前端在 `AuthenticatedLayout.vue` 中自动建立全局 WebSocket 连接：

```typescript
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const { devices, connectionState, refreshDevices } = useGlobalWebSocket();
```

详细文档参见 [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md)

## 测试账号

```
邮箱: admin@feiying.local
密码: password
```

## 常见问题

### 端口冲突

修改 `.env` 中的端口配置：

```bash
APP_PORT=8001           # 应用端口
DB_PORT=3306    # MySQL 端口
REDIS_PORT=6379 # Redis 端口
```

### 权限问题

```bash
# 修复文件权限
sudo chown -R $(id -u):$(id -g) .

# 确保 .env 中的用户配置正确
WWWUSER=1001
WWWGROUP=1002
```

### 前端构建失败

```bash
# 清理并重新安装
./vendor/bin/sail npm cache clean --force
rm -rf node_modules
./vendor/bin/sail npm install
./vendor/bin/sail npm run build
```
