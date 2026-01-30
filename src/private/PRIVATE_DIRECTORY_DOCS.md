# src/private/ 目录文档

> 本文档详细说明 `src/private/` 目录下所有文件的作用和功能。

## 📁 目录结构概览

```
src/private/
├── 核心配置文件 (Eaod*.php)
├── 管理后台页面 (login.php, create.php, etc.)
├── API 端点
├── APK 构建系统
├── 工具和资源
└── 数据文件
```

---

## 🔧 核心配置文件

### Eaod85401.php - 全局常量配置
**作用**: 存储所有全局常量，包括数据库配置、加密密钥、邮件设置等。

| 常量 | 说明 |
|------|------|
| `DB_ServerName` | 数据库主机地址 |
| `DB_UserName` | 数据库用户名 |
| `DB_Password` | 数据库密码 |
| `DB_Name` | 数据库名称 |
| `Secrit_Key` | AES-256-CBC 加密密钥 |
| `SIV` | 加密初始化向量 |
| `Admin_Key` | 管理员登录密钥 |
| `Email_Host/Name/Pass` | 邮件服务配置 |
| `SplitLINE/SplitARRAY` | 数据分隔符 |

---

### Eaod14881.php - 主引导文件
**作用**: 统一引入所有核心依赖文件，其他 PHP 文件只需引入此文件即可。

**引入的文件**:
- Eaod28491.php (日志)
- Eaod57561.php (封禁)
- Eaod41561.php (GeoIP)
- Eaod29251.php (加密)
- Eaod84941.php (响应格式)
- Eaod30651.php (防洪)
- Eaod40971.php (工具函数)
- Eaod26821.php (邮件)
- Eaod85401.php (常量)
- Eaod45021.php (会话检查)

---

### Eaod28491.php - 日志系统
**作用**: 提供错误日志、调试日志、警告日志功能。

| 函数 | 说明 |
|------|------|
| `logError($throwable)` | 记录异常错误到 `errors/日期/` 目录 |
| `logdebug($method, $msg)` | 记录调试信息到 `logs/日期/` 目录 |
| `logwarnings($method, $msg, $ip)` | 记录警告信息到 `warnings/日期/` 目录 |

**特性**:
- 自动创建日期目录
- 自动添加 `.htaccess` 保护日志文件
- 注册全局错误处理器

---

### Eaod29251.php - 加密/解密模块
**作用**: 提供 AES-256-CBC 加密解密功能。

| 函数 | 说明 |
|------|------|
| `EN($input)` | 使用默认密钥加密字符串 |
| `DE($input)` | 使用默认密钥解密字符串 |
| `EN_jector($input, $key)` | 使用自定义密钥加密 (用于注入器) |
| `DE_jector($input, $key)` | 使用自定义密钥解密 (用于注入器) |
| `Encrypt($key, $iv, $str)` | 底层加密函数 |
| `Decrypt($key, $iv, $str)` | 底层解密函数 |

---

### Eaod45021.php - 会话验证
**作用**: 验证用户登录状态，检查 email + token 是否有效。

| 函数 | 说明 |
|------|------|
| `SessionCheck($email, $token)` | 验证用户会话，返回 `[bool, message]` |

---

### Eaod57561.php - 用户封禁系统
**作用**: 临时封禁可疑用户（多次错误登录、请求过多等）。

| 函数 | 说明 |
|------|------|
| `Suspend($ip, $extraInfo)` | 封禁 IP 15 分钟 |
| `isBlocked($ip)` | 检查 IP 是否被封禁 |
| `BadLogin($ip, $name)` | 记录错误登录次数 |
| `isSus($ip, $name)` | 检查是否达到封禁阈值 (8次) |
| `GRstr($length)` | 生成随机字符串 |

---

### Eaod30651.php - 防洪检查
**作用**: 防止 DDoS 攻击，限制请求频率。

| 函数 | 说明 |
|------|------|
| `FloodCheck($UseFor)` | 检查请求频率，3秒内重复请求返回 true |

---

### Eaod40971.php - 工具函数库
**作用**: 提供通用工具函数。

| 函数 | 说明 |
|------|------|
| `str_zip($str)` | gzip 压缩字符串 |
| `str_Dzip($str)` | gzip 解压字符串 |
| `PasswordGenerator(...)` | 生成随机密码 |
| `getClientIP()` | 获取客户端真实 IP |

---

### Eaod84941.php - 响应格式化
**作用**: 定义 API 响应格式常量和格式化函数。

| 常量/函数 | 说明 |
|-----------|------|
| `OP_Success` | 成功响应标识 |
| `OP_Fail` | 失败响应标识 |
| `OP_Request` | 请求响应标识 |
| `OP_Blocked` | 封禁响应标识 |
| `Format($msg, $type)` | 格式化 JSON 响应 |

---

### Eaod41561.php - GeoIP 地理位置
**作用**: 根据 IP 地址获取地理位置信息。

| 类/方法 | 说明 |
|---------|------|
| `GeoIpLibrary` | GeoIP 查询类 |
| `getCountryInfo($ip)` | 获取国家信息 (ISO代码, 名称) |
| `getlocations($ip)` | 获取经纬度坐标 |

**依赖**: MaxMind GeoIP2 数据库

---

### Eaod26821.php - 邮件模块
**作用**: 发送邮件功能（当前为空，预留接口）。

---

## 🖥️ 管理后台页面

### login.php - 管理员登录页
**作用**: 管理员登录入口，验证 `Admin_Key`。

**功能**:
- 显示登录表单
- 验证管理员密钥
- 成功后设置 `$_SESSION['admin_logged_in'] = true`
- 重定向到 `create.php`

---

### create.php - 账号管理页面
**作用**: 管理后台主页面，提供完整的账号管理功能。

**功能**:
| 功能 | 说明 |
|------|------|
| 添加账号 | 创建新用户账号 |
| 随机生成 | 自动生成用户名/邮箱/密码 |
| 账号列表 | 显示所有账号，支持搜索 |
| 续期 | 延长账号有效期 |
| 修改密码 | 更新用户密码 |
| 修改邮箱 | 更新用户邮箱 |
| 删除账号 | 单个/批量删除 |
| 备注管理 | 为账号添加备注 |
| 隐藏账号 | 隐藏/显示账号 (Ctrl+Shift+H) |

**隐藏功能快捷键**:
- `Ctrl+Shift+U`: 显示删除邮箱唯一索引按钮
- `Ctrl+Shift+H`: 显示隐藏账号按钮
- `Ctrl+Shift+S`: 显示隐藏账号列表按钮
- `Ctrl+Shift+A`: 显示不受限制添加按钮

---

### createacc.php - 账号管理 API
**作用**: 处理账号管理的所有 AJAX 请求。

**支持的操作 (action)**:
| Action | 说明 |
|--------|------|
| `add` | 添加新账号 (限制最多6个) |
| `list` | 获取账号列表 |
| `renew` | 续期账号 |
| `update_password` | 更新密码 |
| `update_email` | 更新邮箱 |
| `delete` | 删除账号 |
| `toggle_hide` | 切换账号隐藏状态 |
| `update_remark` | 更新备注 |
| `get_remarks` | 获取所有备注 |
| `remove_email_unique_index` | 删除邮箱唯一索引 |

---

### logout.php - 登出
**作用**: 清除会话，销毁登录状态，重定向到登录页。

---

## 🔌 API 端点

### Eaod36921.php - APK 构建 API
**作用**: 处理 APK 构建请求，调用 VB.NET 构建程序。

**子命令 (subcom)**:
| 命令 | 说明 |
|------|------|
| `build` | 构建 APK (支持 Store/Custom 两种类型) |
| `load` | 加载应用商店列表 |
| `like` | 点赞应用 |

**构建参数**: 应用ID、用户ID、客户端名称、权限设置、通知配置、隐藏设置等。

---

### Eaod65501.php - 构建执行器
**作用**: 封装调用 VB.NET 构建程序的函数。

| 函数 | 说明 |
|------|------|
| `BuildStore(...)` | 构建商店应用 |
| `BuildCustom(...)` | 构建自定义应用 |
| `excutejector($args)` | 执行注入器程序 |

---

### Eaod91328.php - 用户应用库 API
**作用**: 管理用户的已构建应用（加载、下载、删除）。

**子命令**:
| 命令 | 说明 |
|------|------|
| `load` | 加载用户的应用列表 |
| `download` | 下载 APK 文件 (支持断点续传) |
| `delete` | 删除应用 |

---

### Eaod45071.php - 自定义应用构建状态回调
**作用**: VB.NET 构建程序调用此接口更新构建状态。

**子命令**:
| 命令 | 说明 |
|------|------|
| `onbuild` | 开始构建，插入记录 |
| `finished` | 构建完成 |
| `failed` | 构建失败 |

**限制**: 仅允许本地访问 (127.0.0.1)

---

### Eaod90061.php - 商店应用构建状态回调
**作用**: 与 Eaod45071.php 类似，但用于商店应用。

---

### Eaod90061.php - 用户设置 API
**作用**: 处理用户个人设置（头像、用户名、图标等）。

**命令类型 (type)**:
| 类型 | 说明 |
|------|------|
| `img` | 上传头像图片 |
| `name` | 修改用户名 |
| `ico` | 上传应用图标 |
| `listico` | 列出用户图标 |
| `remico` | 删除图标 |
| `ui` | 上传 UI 图片 |
| `listui` | 列出 UI 图片 |
| `remui` | 删除 UI 图片 |
| `listmp3` | 列出 MP3 文件 |

---

## 🛠️ 数据库维护脚本

### check_table.php
**作用**: 查看 users 表结构，删除 contact 字段。

### add_hidden_field.php
**作用**: 为 users 表添加 hidden 字段。

### remove_email_unique_index.php
**作用**: 删除 email 字段的唯一索引，允许重复邮箱。

### check_expired_accounts.php
**作用**: 定时任务脚本，检查过期账号并修改密码禁止登录。

**功能**:
- 查询所有过期账号
- 生成随机密码替换原密码
- 记录操作日志到 `expired_accounts.log`

---

## 📦 APK 构建系统

### apkstub/ 目录
APK 构建所需的模板和资源文件。

```
apkstub/
├── apkstub.zip      # APK 模板包
├── apkstubg.zip     # APK 模板包 (备用)
├── builder.zip      # 构建器资源
└── apkres/          # APK 资源文件
    ├── drawable/    # 图片资源
    │   ├── btnback.png
    │   ├── closebutton.png
    │   ├── mylogo.png
    │   ├── notify.png
    │   └── oppo_bty_*.png/jpg  # OPPO 电池优化引导图
    └── values/
        ├── ids.xml
        ├── public.xml
        └── strings.xml  # 字符串资源 (含占位符 [BASE_NAME])
```

### tools/ 目录
APK 构建工具。

| 文件 | 说明 |
|------|------|
| `APKEditor.jar` | APK 编辑工具 |
| `apktool.jar` | APK 反编译/重打包工具 |

### Windows 构建程序
| 文件 | 说明 |
|------|------|
| `EaodStarter.exe` | APK 构建启动器 |
| `EaodWorker.exe` | APK 构建工作进程 |
| `*.pdb` | 调试符号文件 |
| `*.config` | 配置文件 |
| `DotNetZip.dll` | ZIP 压缩库 |
| `Newtonsoft.Json.dll` | JSON 处理库 |

---

## 📄 数据文件

### remarks.json
**作用**: 存储账号备注信息。

```json
{
    "用户ID": "备注内容"
}
```

### expired_accounts.log
**作用**: 记录过期账号处理日志。

### errors/ 目录
**作用**: 存储错误日志，按日期分目录。

```
errors/
└── 2026-01-28/
    ├── .htaccess      # 禁止外部访问
    └── 2026-01-28_15-52-10.txt
```

---

## 🔐 安全机制

1. **会话验证**: 所有管理页面检查 `$_SESSION['admin_logged_in']`
2. **Token 验证**: API 请求需要 email + token 双重验证
3. **IP 白名单**: 构建回调接口仅允许本地访问
4. **数据加密**: 敏感数据使用 AES-256-CBC 加密
5. **防洪保护**: 限制请求频率
6. **封禁机制**: 多次错误操作自动封禁 IP
7. **日志保护**: 日志目录添加 .htaccess 禁止访问

---

## 📊 文件依赖关系图

```
Eaod14881.php (主引导)
    ├── Eaod28491.php (日志)
    ├── Eaod57561.php (封禁)
    ├── Eaod41561.php (GeoIP)
    ├── Eaod29251.php (加密)
    ├── Eaod84941.php (响应格式)
    ├── Eaod30651.php (防洪)
    ├── Eaod40971.php (工具)
    ├── Eaod26821.php (邮件)
    ├── Eaod85401.php (常量)
    └── Eaod45021.php (会话)

login.php → Eaod14881.php
create.php → Eaod14881.php
createacc.php → 多个 Eaod*.php
Eaod36921.php → Eaod14881.php + Eaod65501.php
Eaod91328.php → Eaod14881.php
```

---

## 📝 命名规范说明

文件使用 `Eaod` + 5位数字的混淆命名，对应关系：

| 文件名 | 功能模块 |
|--------|----------|
| Eaod85401 | 常量配置 |
| Eaod14881 | 主引导 |
| Eaod28491 | 日志系统 |
| Eaod29251 | 加密解密 |
| Eaod45021 | 会话检查 |
| Eaod57561 | 封禁系统 |
| Eaod30651 | 防洪检查 |
| Eaod40971 | 工具函数 |
| Eaod84941 | 响应格式 |
| Eaod41561 | GeoIP |
| Eaod26821 | 邮件 |
| Eaod65501 | 构建执行 |
| Eaod36921 | 构建 API |
| Eaod91328 | 应用库 API |
| Eaod45071 | 自定义应用回调 |
| Eaod90061 | 用户设置 API |

---

*文档生成时间: 2026-01-29*
