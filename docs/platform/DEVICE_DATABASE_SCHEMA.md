# 设备信息数据库架构

**Last Updated:** 2026-04-09

## 概述

设备信息存储采用两表设计：

- **devices 表：** 核心设备信息（35 列）
- **device_details 表：** 详细设备属性（50+ 列，1:1 关联）

这种分离设计提供了灵活性，可根据查询需求选择性加载详情。

---

## devices 表

**主要功能：** 存储设备的基本信息和关键状态

### 现有字段（原始设计）

| 字段 | 类型 | 空值 | 索引 | 说明 |
|------|------|------|------|------|
| `id` | bigint | NO | PK | 主键，自增 |
| `user_id` | bigint | NO | FK | 所属用户 ID，外键 |
| `uuid` | char(36) | NO | UNIQUE | 设备唯一 UUID，作为 deviceId 返回给客户端 |
| `name` | varchar(255) | YES | | 设备名称（通常为型号） |
| `model` | varchar(100) | YES | | 设备型号 |
| `android_version` | varchar(20) | YES | | Android 版本号 |
| `country` | varchar(50) | YES | | 所在国家 |
| `ip_address` | varchar(45) | YES | | IP 地址（IPv4/IPv6） |
| `phone_number` | varchar(50) | YES | | 电话号码 |
| `battery_level` | int | YES | | 电池电量百分比 (0-100) |
| `network_type` | varchar(50) | YES | | 网络类型（WiFi, 4G, 5G 等） |
| `has_accessibility` | tinyint(1) | NO | DEFAULT 0 | 是否启用无障碍服务 |
| `is_online` | tinyint(1) | NO | DEFAULT 0 | 是否在线 |
| `installed_at` | timestamp | YES | | 应用安装时间 |
| `last_seen_at` | timestamp | YES | | 最后活动时间 |
| `settings` | json | YES | | 设备设置（JSON） |
| `permissions` | json | YES | | 权限信息（JSON） |
| `created_at` | timestamp | NO | | 创建时间 |
| `updated_at` | timestamp | NO | | 更新时间 |

### 新增字段（2026-04-10）

| 字段 | 类型 | 空值 | 索引 | 说明 |
|------|------|------|------|------|
| `device_uid` | varchar(64) | YES | INDEX | 由客户端生成的唯一标识 |
| `brand` | varchar(50) | YES | | 品牌代码 |
| `manufacturer` | varchar(100) | YES | | 制造商 |
| `fingerprint` | varchar(255) | YES | | 设备指纹 |
| `serial` | varchar(64) | YES | | 序列号 |
| `package_name` | varchar(150) | YES | | 应用包名 |
| `is_root` | tinyint(1) | NO | DEFAULT 0 | 是否 Root |
| `enable_development` | tinyint(1) | NO | DEFAULT 0 | 开发者模式 |
| `enable_debug` | tinyint(1) | NO | DEFAULT 0 | 调试模式 |
| `enable_wifi_debug` | tinyint(1) | NO | DEFAULT 0 | WiFi 调试 |
| `lang_code` | varchar(20) | YES | | 语言代码（如 zh-CN） |
| `trustee_id` | varchar(100) | YES | | 受信人 ID |

### 总列数

19 (现有) + 12 (新增) = **31 列**（+ 系统字段 id, created_at, updated_at = 34 列）

### 典型查询

```sql
-- 获取用户的所有设备
SELECT * FROM devices WHERE user_id = ? ORDER BY last_seen_at DESC;

-- 查询特定设备
SELECT * FROM devices WHERE device_uid = ? AND user_id = ?;

-- 查询设备列表（带分页）
SELECT * FROM devices WHERE user_id = ? LIMIT 20 OFFSET 0;

-- 统计在线设备
SELECT COUNT(*) FROM devices WHERE user_id = ? AND is_online = 1;

-- 获取需要更新的设备（超过 N 分钟未活动）
SELECT * FROM devices 
WHERE user_id = ? 
  AND last_seen_at < DATE_SUB(NOW(), INTERVAL 5 MINUTE);
```

### 索引策略

```sql
-- 主键（自动）
PRIMARY KEY (id)

-- 唯一索引
UNIQUE KEY unique_uuid (uuid)

-- 普通索引
INDEX idx_device_uid (device_uid)
INDEX idx_user_id (user_id)           -- 可能已存在

-- 复合索引（推荐用于用户设备列表查询）
INDEX idx_user_device (user_id, device_uid)
```

---

## device_details 表

**主要功能：** 存储设备的详细属性信息

### 表结构

| 字段 | 类型 | 空值 | 默认值 | 说明 |
|------|------|------|--------|------|
| `id` | bigint | NO | | 主键，自增 |
| `device_id` | bigint | NO | UNIQUE, FK | 外键关联 devices 表，唯一（1:1 关系） |

### Build 信息字段（13 列）

| 字段 | 类型 | 说明 |
|------|------|------|
| `display_id` | varchar(255) | 显示 ID（如 PQ1A.191005.007） |
| `board` | varchar(100) | 主板型号 |
| `device_name` | varchar(100) | 设备名 |
| `hardware_name` | varchar(100) | 硬件名称 |
| `product` | varchar(100) | 产品名 |
| `code_name` | varchar(50) | 代号 |
| `incremental` | varchar(100) | 增量版本号 |
| `optimal_abi` | varchar(20) | 最优 ABI |
| `support_abi` | json | 支持的 ABI 列表（数组） |
| `factory_time` | varchar(30) | 出厂时间 |
| `os_version` | varchar(50) | 操作系统版本 |
| `os_name` | varchar(50) | 操作系统名称 |
| `os_arch` | varchar(20) | 操作系统架构 |

### Screen 信息字段（13 列）

| 字段 | 类型 | 说明 | 范围 |
|------|------|------|------|
| `screen_width` | unsigned smallint | 屏幕宽度（像素） | 0-65535 |
| `screen_height` | unsigned smallint | 屏幕高度（像素） | 0-65535 |
| `screen_density` | unsigned smallint | 屏幕密度（DPI） | 0-65535 |
| `screen_scaled_density` | float | 缩放密度 | 浮点数 |
| `screen_xdpi` | float | X 轴 DPI | 浮点数 |
| `screen_ydpi` | float | Y 轴 DPI | 浮点数 |
| `screen_is_on` | tinyint(1) | 屏幕是否点亮 | 0/1，默认 1 |
| `screen_state` | unsigned tinyint | 屏幕状态 | 0-255 |
| `screen_off_timeout` | unsigned int | 屏幕熄灭超时（毫秒） | 0-4294967295 |
| `screen_is_round` | tinyint(1) | 是否圆形屏幕 | 0/1，默认 0 |
| `status_bar_height` | unsigned smallint | 状态栏高度（像素） | 0-65535 |
| `navigation_bar_height` | unsigned smallint | 导航栏高度（像素） | 0-65535 |
| `screen_is_blocked` | tinyint(1) | 屏幕是否被屏蔽 | 0/1，默认 0 |

### Lock 信息字段（7 列）

| 字段 | 类型 | 说明 | 范围 |
|------|------|------|------|
| `is_keyguard_locked` | tinyint(1) | KeyGuard 是否锁定 | 0/1，nullable |
| `is_device_locked` | tinyint(1) | 设备是否锁定 | 0/1，nullable |
| `is_keyguard_secure` | tinyint(1) | KeyGuard 是否安全 | 0/1，nullable |
| `is_device_secure` | tinyint(1) | 设备是否安全 | 0/1，nullable |
| `in_keyguard_restricted_input_mode` | tinyint(1) | 是否在受限输入模式 | 0/1，nullable |
| `lock_quality` | int | 锁屏质量等级 | 整数，默认 -1 |

### Battery 信息字段（8 列）

| 字段 | 类型 | 说明 | 范围 |
|------|------|------|------|
| `battery_percent` | float | 电池百分比 | 0.0-100.0 |
| `battery_status` | unsigned tinyint | 电池状态 | 1=unknown, 2=charging, 3=discharging, 4=not_charging, 5=full |
| `battery_health` | unsigned tinyint | 电池健康度 | 1=unknown, 2=good, 3=overheat, ... |
| `battery_voltage` | unsigned smallint | 电压（毫伏） | 0-65535 |
| `battery_temperature` | smallint | 温度（摄氏度） | -32768-32767 |
| `battery_technology` | varchar(30) | 电池技术 | 如 "Li-ion" |
| `battery_plugged` | unsigned tinyint | 充电状态 | 0=unplugged, 1=ac, 2=usb, 4=wireless |
| `in_power_save_mode` | tinyint(1) | 是否进省电模式 | 0/1，默认 0 |

### Admin 信息字段（4 列）

| 字段 | 类型 | 说明 | 范围 |
|------|------|------|------|
| `admin_package_name` | varchar(150) | 管理员应用包名 | 包名格式 |
| `is_admin_active` | tinyint(1) | 管理员是否激活 | 0/1，默认 0 |
| `is_device_owner` | tinyint(1) | 是否设备所有者 | 0/1，默认 0 |
| `is_profile_owner` | tinyint(1) | 是否配置文件所有者 | 0/1，默认 0 |

### 系统字段（2 列）

| 字段 | 类型 | 说明 |
|------|------|------|
| `created_at` | timestamp | 创建时间 |
| `updated_at` | timestamp | 更新时间 |

### 总列数

**1 (id) + 1 (device_id) + 13 (build) + 13 (screen) + 7 (lock) + 8 (battery) + 4 (admin) + 2 (system) = 49 列**

### 典型查询

```sql
-- 获取设备的详细信息
SELECT d.*, dd.* FROM devices d
LEFT JOIN device_details dd ON d.id = dd.device_id
WHERE d.device_uid = ? AND d.user_id = ?;

-- 查询特定屏幕尺寸的设备
SELECT d.* FROM devices d
JOIN device_details dd ON d.id = dd.device_id
WHERE d.user_id = ? AND dd.screen_width = 1440 AND dd.screen_height = 3120;

-- 查询有电池信息的设备
SELECT d.* FROM devices d
JOIN device_details dd ON d.id = dd.device_id
WHERE d.user_id = ? AND dd.battery_percent IS NOT NULL;

-- 查询启用了设备管理员的设备
SELECT d.* FROM devices d
JOIN device_details dd ON d.id = dd.device_id
WHERE d.user_id = ? AND dd.is_admin_active = 1;
```

### 索引策略

```sql
-- 主键（自动）
PRIMARY KEY (id)

-- 唯一外键（自动创建）
UNIQUE KEY unique_device_id (device_id)

-- 可选：查询优化索引
-- 如果频繁查询特定品牌或屏幕尺寸
INDEX idx_battery_percent (battery_percent)
INDEX idx_is_admin_active (is_admin_active)
```

---

## 关系图

```
┌─────────────────────┐
│       devices       │
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │ ──────┐
│ uuid (UNIQUE)       │       │ 用户拥有多个设备
│ device_uid (INDEX)  │       │
│ name                │       │
│ model               │       │
│ android_version     │       │
│ is_online           │       │
│ last_seen_at        │       │
│ ... (19 更多字段)   │       │
│ created_at          │       │
│ updated_at          │       │
└─────────────────────┘       │
         │                     │
         │ (1:1)              │
         │                     │
         ▼                     │
┌─────────────────────┐       │
│  device_details     │       │
├─────────────────────┤       │
│ id (PK)             │       │
│ device_id (UNIQUE)  │───────┴──── 一个设备拥有一个详情
│ display_id          │
│ board               │
│ ... (13 build字段)  │
│ screen_width        │
│ ... (13 screen字段) │
│ is_keyguard_locked  │
│ ... (7 lock字段)    │
│ battery_percent     │
│ ... (8 battery字段) │
│ admin_package_name  │
│ ... (4 admin字段)   │
│ created_at          │
│ updated_at          │
└─────────────────────┘

      users
       │
       │ (1:many)
       │
       ▼
     devices ─────────── device_details
                         (1:1)
```

---

## 数据类型选择说明

### String 类型

- `varchar(50-100)` — 短字符串（品牌、型号、硬件名等）
- `varchar(150-255)` — 中长字符串（包名、指纹、显示 ID 等）
- `varchar(20-30)` — 枚举类型字符串（语言代码、ABI、电池技术等）

### Integer 类型

- `int` — 通用整数（电池电量百分比、设备状态等）
- `tinyint(1)` — 布尔值（0/1）
- `unsigned tinyint` — 0-255 范围（状态码、DPI 等级等）
- `unsigned smallint` — 0-65535 范围（屏幕分辨率、DPI 值等）
- `smallint` — -32768-32767 范围（温度、某些相对值等）
- `unsigned int` — 0-4294967295 范围（超时毫秒值等）

### Float 类型

- `float` — 浮点数（DPI、缩放密度等）

### JSON 类型

- `json` — 数组或对象（支持的 ABI 列表、设置、权限等）

---

## 迁移脚本

### 创建表 (已执行)

```php
// database/migrations/2026_04_10_000003_add_device_uid_to_devices_table.php
Schema::table('devices', function (Blueprint $table) {
    $table->string('device_uid', 64)->nullable()->index()->after('uuid');
    $table->string('brand', 50)->nullable()->after('model');
    // ... 10 more columns
});

// database/migrations/2026_04_10_000004_create_device_details_table.php
Schema::create('device_details', function (Blueprint $table) {
    $table->id();
    $table->foreignId('device_id')->unique()->constrained()->onDelete('cascade');
    // ... 48 more columns
    $table->timestamps();
});
```

### 回滚

```bash
# 回滚最后一个迁移
./vendor/bin/sail artisan migrate:rollback

# 重新运行迁移
./vendor/bin/sail artisan migrate
```

---

## 性能考虑

### 存储空间

- **devices 表：** 每行约 1-2 KB（取决于 JSON 字段大小）
- **device_details 表：** 每行约 1-2 KB（50+ 列，多数为 nullable）
- **每台设备：** ~2-4 KB

**示例：** 10000 台设备 = 20-40 MB

### 查询性能

| 操作 | 复杂度 | 索引 |
|------|--------|------|
| 查询单个设备 | O(log N) | device_uid + user_id |
| 列出用户设备 | O(log N + K) | user_id |
| 更新设备信息 | O(log N) | id |
| 删除设备 | O(log N) | id + CASCADE 删除 details |

### 推荐索引

```sql
-- 最小化索引（已创建）
CREATE INDEX idx_device_uid ON devices(device_uid);
CREATE INDEX idx_user_id ON devices(user_id);
CREATE UNIQUE INDEX idx_uuid ON devices(uuid);

-- 推荐补充索引
CREATE INDEX idx_user_device ON devices(user_id, device_uid);
CREATE INDEX idx_is_online ON devices(is_online, last_seen_at);

-- device_details 查询优化（可选）
CREATE INDEX idx_battery_percent ON device_details(battery_percent);
CREATE INDEX idx_is_admin_active ON device_details(is_admin_active);
```

---

## 维护建议

### 定期检查

```bash
# 检查表大小
SELECT table_name, 
       ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'yourdb' 
  AND table_name IN ('devices', 'device_details');

# 检查慢查询
SHOW FULL PROCESSLIST;
```

### 备份

- 完整备份：每天一次
- 增量备份：每小时一次
- 保留策略：30 天内日备份，7 天内时备份

### 清理过期数据

```sql
-- 删除超过 90 天未活动的设备（谨慎执行）
DELETE FROM devices 
WHERE last_seen_at < DATE_SUB(NOW(), INTERVAL 90 DAY);
```

---

## 相关文档

- **API 文档：** [API.md](./API.md)
- **实现文档：** [DEVICE_API_IMPLEMENTATION.md](./DEVICE_API_IMPLEMENTATION.md)
- **数据库迁移：** `database/migrations/`
- **模型：** `app/Models/Device.php` 和 `app/Models/DeviceDetail.php`
