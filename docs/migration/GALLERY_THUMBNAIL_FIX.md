# 相册缩略图功能修复

## 问题描述

设备控制页面的相册功能无法显示图片缩略图。

## 根本原因

1. 文件列表（`files` 消息）不包含图片数据
2. 缺少缩略图请求机制（`viewfile` 命令）
3. 缺少 `thumb` 消息处理

## 解决方案

### 1. 相册功能改为复用文件列表

- 使用 `files` 命令请求 `/sdcard/DCIM/Camera/` 目录
- 前端过滤图片文件（.jpg, .png, .gif 等）
- 与旧项目实现一致

### 2. 添加缩略图加载机制

**请求流程**：
```
获取文件列表 → 过滤图片 → 发送 viewfile 请求 → 接收 thumb 消息 → 显示图片
```

**消息格式**：
```typescript
// 请求缩略图
{
  itype: 'slr_panelsend',
  subc: 'viewfile',
  pid: 'device-uuid',
  filepath: '/sdcard/DCIM/Camera/photo.jpg'
}

// 接收缩略图
{
  type: 'thumb',
  data: 'base64_image_data',
  path: '/sdcard/DCIM/Camera/photo.jpg',
  pid: 'device-uuid'
}
```

## 修改文件

### 1. `app/resources/ts/Pages/Devices/Control.vue`

**添加 thumb 消息处理**：
```typescript
case 'thumb': {
    const thumbMsg = msg as any;
    const imagePath = thumbMsg.path;
    const imageData = thumbMsg.data;
    
    const imageIndex = galleryImages.value.findIndex(img => 
        (img.path + '/' + img.name) === imagePath || img.path === imagePath
    );
    
    if (imageIndex !== -1) {
        galleryImages.value[imageIndex].thumbnail = imageData;
    }
    break;
}
```

**修改 files 消息处理**：
- 过滤图片文件
- 构建 `galleryImages` 数组（匹配 GalleryTab 接口）
- 自动发送 `viewfile` 请求获取缩略图

### 2. `app/resources/ts/composables/useDeviceData.ts`

**修复 parseFilesData 字段索引**：
```typescript
// 修正前：parts[0]=name, parts[1]=size...
// 修正后：parts[2]=name, parts[3]=size, parts[4]=path...
```

**添加位置管理**：
- 将 `locationLoading` 和 `locationInfo` 移到 useDeviceData
- 统一超时管理（5秒）

### 3. `app/app/WebSocket/Handlers/PanelSendHandler.php`

**删除无效的 handleGetGallery 方法**：
- 旧项目从未使用 `getgallery` 命令
- 相册功能复用 `files` 命令

## 测试步骤

1. 刷新页面
2. 进入设备控制页
3. 点击「相册」标签
4. 点击「获取相册」
5. 等待几秒，图片逐个加载显示

## 技术细节

- 超时时间：5秒
- 支持格式：.jpg, .jpeg, .png, .gif, .bmp, .webp
- 图片格式：Base64 编码
- 无缓存机制（简化实现）

## 相关文档

- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - WebSocket 消息协议
- [DEVICE_STATUS_FIELDS.md](./DEVICE_STATUS_FIELDS.md) - 设备状态字段
- [CONTROL_PANEL_SCREEN_OPERATIONS.md](./CONTROL_PANEL_SCREEN_OPERATIONS.md) - 控制面板操作
