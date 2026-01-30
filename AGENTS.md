# AGENTS.md - 代码库指南

> 本文档为 AI 编程代理提供项目结构、代码风格和开发规范指南。

## 项目概述

这是一个 PHP 后端项目，用于设备管理系统（飞鹰管理系统）。包含 REST API、管理后台和 WebSocket 服务。

### 技术栈

- **后端**: 原生 PHP (无框架)
- **数据库**: MySQL (PDO)
- **WebSocket**: Node.js + Express
- **前端**: Vue.js (已构建的静态资源)

## 项目结构

```
full-package/
├── api/api/              # REST API 端点
│   ├── ws/               # WebSocket 服务 (Node.js)
│   ├── assets/           # GeoIP 数据库等资源
│   ├── EaodLogin.php     # 用户登录 API
│   ├── EaodAllDevices.php # 设备列表 API
│   ├── Ping.php          # 设备心跳 API
│   └── ...
├── private/private/      # 管理后台
│   ├── Eaod85401.php     # 数据库配置 (常量定义)
│   ├── login.php         # 管理员登录页
│   ├── create.php        # 账号管理页
│   ├── createacc.php     # 账号管理 API
│   └── ...
├── vendor/vendor/        # Composer 依赖
├── assets/               # 前端构建资源
├── htdocs_root/          # Web 根目录
└── eaod_logs/            # 日志文件
```

## 构建/运行命令

### PHP 服务

```bash
# 本项目无正式构建流程，直接通过 Web 服务器运行
# 确保 PHP 和 MySQL 服务已启动

# 检查 PHP 语法错误
php -l <file.php>

# 批量检查所有 PHP 文件
find . -name "*.php" ! -path "./vendor/*" -exec php -l {} \;
```

### WebSocket 服务

```bash
cd api/api/ws
npm install
node server.js  # 或相应的启动文件
```

### 测试

```bash
# 本项目无正式测试框架
# 手动测试 API 端点:
curl -X POST http://localhost/api/EaodLogin.php \
  -H "Content-Type: application/json" \
  -d '{"usrname":"test","password":"test"}'
```

## 代码风格规范

### PHP 文件头部模板

```php
<?php
date_default_timezone_set('Asia/Shanghai');

// API 文件需要 CORS 头
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Content-Type: application/json");

// 处理 OPTIONS 预检请求
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit();
}

require_once '../private/Eaod85401.php';
```

### 数据库连接

```php
// 使用 PDO 连接数据库
$pdo = new PDO(
    "mysql:host=" . DB_ServerName . ";dbname=" . DB_Name,
    DB_UserName,
    DB_Password
);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

// 始终使用预处理语句防止 SQL 注入
$stmt = $pdo->prepare("SELECT * FROM users WHERE usrname = :usrname");
$stmt->bindParam(':usrname', $username, PDO::PARAM_STR);
$stmt->execute();
$result = $stmt->fetch(PDO::FETCH_ASSOC);
```

### 错误处理

```php
try {
    // 业务逻辑
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Server busy']);
    error_log($e->getMessage());
    // 或写入日志文件
    file_put_contents("log.txt", "异常：" . $e->getMessage() . "\n", FILE_APPEND);
}
```

### API 响应格式

```php
// 成功响应
echo json_encode([
    'code' => 200,
    'msg' => '操作成功',
    'data' => $result
]);

// 错误响应
http_response_code(400);
echo json_encode(['error' => '错误信息']);

// 管理后台响应格式
echo json_encode(['Success' => '操作成功']);
echo json_encode(['Fail' => '操作失败']);
```

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 文件名 | PascalCase 或混淆名 | `EaodLogin.php`, `Eaod85401.php` |
| 函数名 | camelCase | `getCountry()`, `generateUUID()` |
| 变量名 | camelCase | `$phoneId`, `$userName` |
| 常量 | UPPER_SNAKE_CASE | `DB_ServerName`, `Admin_Key` |
| 数据库字段 | snake_case | `phone_id`, `user_name` |

### 重要常量 (定义在 Eaod85401.php)

```php
// 数据库配置
DB_ServerName  // 数据库主机
DB_UserName    // 数据库用户名
DB_Password    // 数据库密码
DB_Name        // 数据库名

// 加密配置
Secrit_Key     // 加密密钥
SIV            // 初始化向量

// 管理配置
Admin_Key      // 管理员密钥
```

### 敏感数据处理

```php
// 密码存储 - 使用 password_hash
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// 密码验证
if (password_verify($inputPassword, $storedHash)) {
    // 验证成功
}

// 敏感数据加密 (使用项目自定义函数)
$encrypted = EN($plainText);  // 加密
$decrypted = DE($encrypted);  // 解密
```

## 安全注意事项

1. **SQL 注入防护**: 始终使用 PDO 预处理语句
2. **XSS 防护**: 输出时进行适当转义
3. **CORS**: API 端点已配置允许跨域
4. **Session**: 管理后台使用 PHP Session 认证
5. **Token**: API 使用 token 认证，有效期 7 天

## 常见开发任务

### 添加新 API 端点

1. 在 `api/api/` 目录创建新 PHP 文件
2. 添加标准头部 (时区、CORS、Content-Type)
3. 引入配置文件 `require_once '../private/Eaod85401.php'`
4. 实现业务逻辑，使用 try-catch 包裹
5. 返回 JSON 格式响应

### 修改数据库操作

1. 使用 PDO 预处理语句
2. 绑定参数时指定类型 (`PDO::PARAM_STR`, `PDO::PARAM_INT`)
3. 捕获 `PDOException` 并记录日志

### 调试技巧

```php
// 开启错误显示 (仅开发环境)
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// 日志记录
file_put_contents("log.txt", "调试信息：" . print_r($data, true) . "\n", FILE_APPEND);
```

## 依赖库

主要 Composer 依赖 (位于 vendor/vendor/):

- `phpmailer/phpmailer` - 邮件发送
- `maxmind-db/reader` - GeoIP 地理位置查询
- `geoip2/geoip2` - GeoIP2 API
- `php-ffmpeg/php-ffmpeg` - 视频处理
- `robthree/twofactorauth` - 两步验证
- `symfony/cache` - 缓存组件
- `spatie/temporary-directory` - 临时目录管理

## 注意事项

1. **无正式测试**: 项目没有单元测试，修改后需手动验证
2. **无代码格式化工具**: 保持与现有代码风格一致
3. **混淆文件名**: 部分文件使用 `Eaod` 前缀的混淆命名
4. **中文注释**: 项目使用中文注释，保持一致
5. **时区**: 所有时间操作使用 `Asia/Shanghai` 时区
6. **编码**: 文件使用 UTF-8 编码

## 文件修改检查清单

- [ ] PHP 语法检查通过 (`php -l`)
- [ ] 数据库操作使用预处理语句
- [ ] 敏感信息已加密处理
- [ ] API 响应格式正确
- [ ] 错误处理完整
- [ ] 日志记录适当
