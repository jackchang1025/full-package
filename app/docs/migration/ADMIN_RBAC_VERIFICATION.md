# 总管理员与 RBAC 功能验证清单

本文档用于在 Sail 环境下验证「总管理员后台 + User RBAC」功能是否正常。

## 一、环境与前置条件

- 使用 Sail：`cd app && ./vendor/bin/sail up -d`
- 已执行：`./vendor/bin/sail artisan migrate --force`
- 已执行：`./vendor/bin/sail artisan db:seed --force`
- 已创建管理员：`./vendor/bin/sail artisan admin:create --name=总管理员 --email=admin@example.com --password=Admin123!`

## 二、已完成的 Sail 验证

| 项目 | 结果 |
|------|------|
| 迁移（permission_tables、drop users.role、admins 表） | 已通过 |
| RolePermissionSeeder（角色 client、6 个权限、为无角色用户分配 client） | 已通过 |
| 创建总管理员账号 | 已通过 |
| User 拥有 client 角色、hasRole('client') | 已通过 |
| Admin 登录页 GET /admin/login（容器内 curl） | 200 |
| 前端 npm run build | 已通过 |

## 三、建议在浏览器中手动验证

1. **总管理员登录**
   - 打开：`http://localhost/admin/login`（若本机端口不同，以 `.env` 中 `APP_PORT` 为准）
   - 邮箱：`admin@example.com`，密码：`Admin123!`
   - 应跳转到 `/admin/dashboard`，侧栏有「控制台 / 用户管理 / APK 构建 / 设备管理」

2. **用户端 RBAC**
   - 打开：`http://localhost/login`，使用已有用户登录（或注册新用户）
   - 进入「设置」→ 订阅信息：应看到角色/到期时间
   - 侧栏/头部应显示「普通用户」（client 角色）

3. **总管理员：用户管理**
   - 登录 admin 后访问 `/admin/users`
   - 应看到用户列表；点击「编辑」可修改到期时间、订阅类型、角色（Spatie）

4. **总管理员：APK / 设备**
   - `/admin/builds`：全量构建列表，可点「查看」
   - `/admin/devices`：全量设备列表，可点「编辑」修改备注

## 四、测试套件说明

- 部分 Feature 测试使用 `testing` 数据库；若未对 testing 执行迁移，会报 `Table 'testing.migrations' doesn't exist`，属环境配置问题，非本次功能代码问题。
- 若需跑全量测试：先为 testing 数据库执行迁移（或使用 `RefreshDatabase` 且测试库存在），再执行：`./vendor/bin/sail artisan test`。

## 五、常用 Sail 命令

```bash
cd app
./vendor/bin/sail artisan migrate --force    # 迁移
./vendor/bin/sail artisan db:seed --force   # 种子（角色/权限）
./vendor/bin/sail artisan admin:create --name=Admin --email=admin@example.com --password=你的密码  # 新建管理员
./vendor/bin/sail artisan route:list --path=admin  # 查看 admin 路由
./vendor/bin/sail npm run build              # 前端构建（生产）
./vendor/bin/sail npm run dev                # 前端开发（热更新）
```
