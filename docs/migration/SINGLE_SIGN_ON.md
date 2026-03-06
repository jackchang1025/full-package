# 单点登录功能文档

## 概述

飞鹰管理系统 V2 实现了单点登录（Single Sign-On）功能，确保同一账号只能在一个设备上保持登录状态。当用户在新设备登录时，旧设备的会话将被自动踢出。

## 功能特性

- ✅ **单点登录**：同一账号只能在一个设备登录
- ✅ **多设备登出**：新设备登录后，旧设备自动登出
- ✅ **友好提示**：被踢出时显示模态框，引导用户重新登录
- ✅ **双 Guard 支持**：同时支持 `web` 和 `admin` guard
- ✅ **向后兼容**：旧 session（无 token）可继续使用，直到重新登录
- ✅ **Remember Me 清理**：避免自动登录循环

## 技术实现

### 数据库设计

**新增字段**：
```sql
-- users 表
ALTER TABLE users ADD COLUMN session_token VARCHAR(64) NULL;
ALTER TABLE users ADD INDEX idx_session_token (session_token);

-- admins 表
ALTER TABLE admins ADD COLUMN session_token VARCHAR(64) NULL;
ALTER TABLE admins ADD INDEX idx_session_token (session_token);
```

### 核心组件

#### 1. 中间件：`EnsureSingleSession`

**位置**：`app/Http/Middleware/EnsureSingleSession.php`

**工作流程**：
1. 遍历 `['web', 'admin']` guard
2. 获取当前认证用户
3. 比对 session 中的 token 与数据库中的 token
4. 不匹配则执行登出并返回 409 响应

**关键代码**：
```php
$user->refresh();
$dbToken = $user->session_token;
$sessionToken = $request->session()->get("single_session_token_{$guard}");

if ($sessionToken !== null && $dbToken === $sessionToken) {
    continue; // Token 匹配，允许通过
}

// Token 不匹配，踢出用户
Auth::guard($guard)->logout();
$request->session()->flush();
$request->session()->regenerateToken();
cookie()->queue(cookie()->forget(Auth::guard($guard)->getRecallerName()));
```

#### 2. 登录时生成 Token

**Web Guard** (`LoginResponse.php`)：
```php
$token = bin2hex(random_bytes(32));
$user->update(['session_token' => $token]);
$request->session()->put('single_session_token_web', $token);
```

**Admin Guard** (`AuthController.php`)：
```php
$token = bin2hex(random_bytes(32));
$admin->update(['session_token' => $token]);
$request->session()->put('single_session_token_admin', $token);
```

#### 3. 前端处理：`SessionKickedHandler.vue`

**位置**：`resources/ts/Components/SessionKickedHandler.vue`

**功能**：
- 监听全局 `session-kicked` 事件
- 显示"会话已失效"模态框
- 引导用户重新登录

**触发机制** (`app.ts`)：
```typescript
router.on('invalid', (event) => {
    const res = event.detail.response;
    if (res.status !== 409 || res.data?.message !== 'session_kicked') return;

    event.preventDefault();
    window.dispatchEvent(new CustomEvent('session-kicked', {
        detail: { guard: res.data?.guard ?? 'web' },
    }));
});
```

## 使用场景

### 场景 1：正常单点登录

1. 用户 A 在设备 1 登录 → 生成 `token_1`
2. 用户 A 在设备 2 登录 → 生成 `token_2`，覆盖数据库
3. 设备 1 下次请求 → token 不匹配 → 返回 409 → 显示"会话已失效"

### 场景 2：并发登录

1. 设备 A 和设备 B 几乎同时登录
2. 后登录的设备覆盖数据库 token
3. 先登录的设备下次请求时被踢出

### 场景 3：Session 过期

1. 用户登录后长时间未操作，session 过期
2. 下次访问时，Laravel 自动重定向到登录页
3. 不触发"被踢出"逻辑（正常的未认证重定向）

### 场景 4：双 Guard 同时登录

1. 用户同时登录 web 和 admin guard（共享 session）
2. 任一 guard 被踢出时，执行 `session()->flush()`
3. 两个 guard 的 session token 都被清空

## 配置说明

### 中间件注册

**位置**：`bootstrap/app.php`

```php
$middleware->web(append: [
    \App\Http\Middleware\EnsureSingleSession::class,
    \App\Http\Middleware\HandleInertiaRequests::class,
]);
```

**注意**：必须在 `HandleInertiaRequests` 之前注册，确保 Inertia 请求能正确接收 409 响应。

### 模型配置

**User.php** 和 **Admin.php**：
```php
protected $fillable = [
    // ...
    'session_token',
];

protected $hidden = [
    'password',
    'remember_token',
    'session_token', // 隐藏敏感字段
];
```

## 测试覆盖

### 测试文件

**位置**：`tests/Feature/SingleSessionTest.php`

### 测试场景

**基础功能**（15 个测试）：
- ✅ 登录时生成 session_token
- ✅ 每次登录生成不同 token
- ✅ Token 不匹配时返回 409（Inertia 请求）
- ✅ Token 不匹配时重定向到登录页（普通请求）
- ✅ Token 匹配时正常访问
- ✅ 被踢出后用户已登出
- ✅ 旧 session（无 token）允许通过
- ✅ 旧 session 遇到新 token 时被踢出

**并发登录**（2 个测试）：
- ✅ 第二设备登录使第一设备失效
- ✅ 快速连续登录生成不同 token

**Session 过期**（2 个测试）：
- ✅ 过期 session 不触发"被踢出"
- ✅ Token 在登出后保留在数据库

**双 Guard 边界**（4 个测试）：
- ✅ Web guard 被踢出时 session flush 影响两个 guard
- ✅ Admin guard 被踢出时 session flush 影响两个 guard
- ✅ 中间件按顺序检查 guard
- ✅ Web guard 通过后继续检查 admin guard

### 运行测试

```bash
./vendor/bin/sail pest tests/Feature/SingleSessionTest.php
```

**预期结果**：23 passed (55 assertions)

## 性能优化

### 1. 数据库索引

```sql
ALTER TABLE users ADD INDEX idx_session_token (session_token);
ALTER TABLE admins ADD INDEX idx_session_token (session_token);
```

### 2. 使用模型 refresh

```php
// ❌ 旧方案：每次请求执行 DB 查询
$dbToken = DB::table($table)->where('id', $user->getKey())->value('session_token');

// ✅ 新方案：使用模型 refresh
$user->refresh();
$dbToken = $user->session_token;
```

### 3. 清除 Remember Cookie

避免用户被踢出后，通过 remember cookie 自动登录，再次被踢出的循环：

```php
cookie()->queue(cookie()->forget(Auth::guard($guard)->getRecallerName()));
```

## 安全考虑

### Token 生成

使用 `random_bytes(32)` 生成 64 字符的十六进制字符串，确保足够的随机性：

```php
$token = bin2hex(random_bytes(32)); // 64 字符
```

### Token 存储

- **数据库**：明文存储（用于比对，非敏感数据）
- **Session**：服务器端存储，客户端无法访问
- **模型隐藏**：`$hidden` 数组中包含 `session_token`，防止 API 泄露

### 竞态条件

**问题**：两个设备同时登录，可能导致 token 覆盖不一致。

**当前方案**：后登录的设备覆盖数据库 token，先登录的设备下次请求时被踢出。

**可选优化**（如需严格控制）：
```php
// 使用数据库锁
$user->lockForUpdate()->update([
    'session_token' => $token,
    'session_updated_at' => now(),
]);
```

## 故障排查

### 问题 1：用户频繁被踢出

**可能原因**：
- 多个浏览器/设备同时使用
- 浏览器扩展或脚本自动刷新

**解决方案**：
- 确认用户只在一个设备登录
- 检查浏览器扩展是否干扰

### 问题 2：登出后仍能访问

**可能原因**：
- 中间件未正确注册
- 路由缓存未清除

**解决方案**：
```bash
./vendor/bin/sail artisan route:clear
./vendor/bin/sail artisan config:clear
```

### 问题 3：测试环境 session 冲突

**可能原因**：
- 测试使用 `RefreshDatabase`，但 session 数据未清理

**解决方案**：
- 测试中使用 `$this->post(route('logout'))` 清理 session
- 或在 `beforeEach` 中手动清空 session

## 向后兼容性

### 旧 Session 处理

**场景**：已登录用户（无 token）在部署后首次访问

**行为**：
```php
if ($sessionToken === null && $dbToken === null) {
    continue; // ✅ 允许通过
}
```

**说明**：旧 session 可以继续使用，直到用户重新登录才启用单点登录。

### 强制启用（可选）

如需立即启用单点登录，可在部署时：

1. **清空所有 session**：
```bash
./vendor/bin/sail artisan session:flush
```

2. **或在中间件中为旧 session 生成 token**：
```php
if ($sessionToken === null && $dbToken === null) {
    $token = bin2hex(random_bytes(32));
    $user->update(['session_token' => $token]);
    $request->session()->put("single_session_token_{$guard}", $token);
    continue;
}
```

## 部署清单

### 1. 数据库迁移

```bash
./vendor/bin/sail artisan migrate
```

### 2. 清除缓存

```bash
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan route:clear
./vendor/bin/sail artisan cache:clear
```

### 3. 前端构建

```bash
./vendor/bin/sail npm run build
```

### 4. 重启服务

```bash
./vendor/bin/sail restart
```

### 5. 验证功能

1. 在设备 A 登录
2. 在设备 B 登录
3. 设备 A 刷新页面，应显示"会话已失效"模态框

## 相关文档

- [DEVELOPMENT.md](./DEVELOPMENT.md) - 开发环境配置
- [FRONTEND.md](./FRONTEND.md) - 前端架构文档
- [API.md](./API.md) - API 接口文档
- [DEPLOYMENT.md](./DEPLOYMENT.md) - 部署文档

## 更新日志

### 2026-03-06

- ✅ 实现单点登录基础功能
- ✅ 添加 EnsureSingleSession 中间件
- ✅ 前端 SessionKickedHandler 组件
- ✅ 完整测试覆盖（23 个测试）
- ✅ 性能优化（索引 + model refresh）
- ✅ 修复 AppServiceProvider 迁移问题
