# AppBuildController Gradle 迁移设计文档

**日期：** 2026-03-25
**状态：** 待审查

## 1. 目标

将 AppBuildController 从 Smali 构建系统（ApkBuilder）迁移到 Gradle 源码构建系统（GradleApkBuilder），同步修改前端表单，移除所有 Smali 遗留字段。

## 2. 背景

项目中存在两套 APK 构建系统：
- **ApkBuilder**：基于反编译的 APK 模板，通过修改 Smali 代码重新打包（已废弃）
- **GradleApkBuilder**：基于 Android Gradle 项目源码编译（当前使用）

AppBuildController 的 `stream()` 方法目前使用 ApkBuilder，需要迁移到 GradleApkBuilder。

## 3. 设计方案

### 3.1 方案选择

**选定方案：** 最小改动 - 直接替换构建器 + 前端同步修改

**理由：**
- Smali 系统已明确废弃，无需兼容
- GradleApkBuilder 已在 CLI 命令中验证可用
- 不做字段映射，前后端直接使用 Gradle 配置字段
- 风险可控，快速交付

### 3.2 架构变更

**后端改动：**
- 移除 `ApkBuilder` 依赖，注入 `GradleApkBuilder`
- 移除 `prepareBuildConfig()` 方法（Smali 字段映射逻辑）
- `stream()` 方法中进行最小字段重命名后调用 `GradleApkBuildConfig::fromArray()`
- 保持 SSE 流式响应接口不变

**前端改动：**
- 移除 18 个 Smali 特有字段
- 保留 11 个用户可见字段（使用前端友好的字段名）
- 2 个字段由后端自动填充（websocket_url, user_email）

**字段命名说明：**
- 前端使用简洁字段名（如 `name`, `client_name`）
- 后端在调用 `GradleApkBuildConfig::fromArray()` 前重命名为 snake_case（如 `app_name`, `app_label`）
- `GradleApkBuildConfig::fromArray()` 同时支持 snake_case 和 camelCase

**数据库：**
- `app_builds.build_config` 字段内容结构变化（JSON 字段，无需迁移脚本）

## 4. 字段映射

### 4.1 前端用户可见字段（11 个）

**必填字段（1 个）：**
- `name` → `app_name` - 应用显示名称

**可选字段（10 个）：**
- `package_name` → `application_id` - 包名
- `version` → `version_name` - 版本号
- `icon_path` - 应用图标路径
- `background_path` - 引导背景图路径
- `client_name` → `app_label` - 应用标签
- `debug` - 调试模式（Switch 开关，默认 1）
- `alertTitle` - 无障碍引导标题
- `alertMsg` - 无障碍引导内容（多行文本域）
- `okText` - 引导按钮文本
- `mainUrl` - WebView 主页地址（默认为空）

### 4.2 后端自动填充字段（2 个）

- `websocket_url` - 从 `config('apk-builder.defaults.websocket_url')` 获取
- `user_email` - 从 `$request->user()->getResourceOwner()->email` 获取

### 4.3 移除的 Smali 字段（18 个）

- 隐藏/安装：`hide_type`, `install_type`, `install_type2`, `hidden_app`
- 权限：`user_allprims`, `user_blackprims`, `open_access`, `use_access`, `use_draw`
- 登录弹窗：`login_title`, `login_dis`, `login_btn`
- 其他：`use_atoprims`, `notify_msg`, `use_antkill`, `diao_type`, `enable_auto_wake_screen`, `abg_path`, `lng_short`, `app_url`

## 5. 实施细节

### 5.1 AppBuildController 改动

**依赖注入变更：**
```php
// 移除
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuildConfig;

// 新增
use App\Services\GradleApkBuilder\GradleApkBuilder;
use App\Services\GradleApkBuilder\GradleApkBuildConfig;
```

**stream() 方法重构：**
1. 移除 `prepareBuildConfig()` 调用
2. 从 `$validated` 构建配置数组，添加自动填充字段
3. 调用 `GradleApkBuildConfig::fromArray()` 创建配置对象
4. 使用 `GradleApkBuilder` 替代 `ApkBuilder`
5. 保持 SSE 响应格式不变

**移除的方法：**
- `prepareBuildConfig()` - 完整删除
- `listUserImages()` - 保留（图标/背景列表）
- `generatePackageName()` - 保留（Gradle 也需要）
- `generateVersion()` - 保留（Gradle 也需要）

### 5.2 BuildRequest 验证规则调整

**文件路径：** `app/Http/Requests/Build/BuildRequest.php`

**移除：** 18 个 Smali 字段的验证规则

**保留 + 新增：**
```php
'name' => 'required|string|max:100',
'package_name' => 'nullable|string|regex:/^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$/',
'version' => 'nullable|string|regex:/^\d+\.\d+(\.\d+)?$/',
'icon_path' => 'nullable|string',
'background_path' => 'nullable|string',
'client_name' => 'nullable|string|max:100',
'debug' => 'nullable|integer|in:0,1',
'alertTitle' => 'nullable|string|max:200',
'alertMsg' => 'nullable|string|max:1000',
'okText' => 'nullable|string|max:50',
'mainUrl' => 'nullable|url',
```

### 5.3 前端组件改动

**受影响文件：**
- `resources/ts/Pages/Builds/Create.vue`（主要改动）
- 无 Edit.vue 组件（不存在编辑页面）

**改动内容：**
1. 移除 18 个 Smali 字段的表单项
2. 移除 `websocket_url` 和 `user_email` 输入框（后端自动填充）
3. 新增 `debug` Switch 开关
4. 新增 `alertTitle` 单行输入
5. 新增 `alertMsg` 多行文本域
6. 新增 `okText` 单行输入
7. 新增 `mainUrl` URL 输入框

### 5.4 SSE 流式响应

**保持不变：**
- 进度事件：`data: {"type": "progress", "step": "...", "label": "...", "status": "..."}\n\n`
- 心跳：`: heartbeat\n\n`
- 完成：`{"type": "complete", "build_id": ..., "path": "...", "duration": ...}`
- 错误：`{"type": "error", "error": "..."}`

**GradleApkBuilder 进度回调：**
- 通过 `onProgress(\Closure $callback)` 注册
- 回调参数：`($step, $label, $status)`
- 状态：`running`, `done`, `failed`

### 5.5 错误处理

- 配置验证失败 → 422 错误（BuildRequest 验证）
- 构建失败 → SSE 发送 error 事件，删除预创建的 `app_builds` 记录
- 环境检查失败 → GradleApkBuildException

## 6. 测试计划

### 6.1 后端测试
- [ ] 验证 `stream()` 方法使用 GradleApkBuilder
- [ ] 验证配置字段正确传递到 GradleApkBuildConfig
- [ ] 验证 `websocket_url` 从 config 正确获取
- [ ] 验证 `user_email` 从当前用户正确获取
- [ ] 验证字段默认值处理（debug=1, mainUrl=''）
- [ ] 验证 SSE 流式响应格式
- [ ] 验证构建成功后 `app_builds` 记录正确保存
- [ ] 验证构建失败后记录被删除

### 6.2 前端测试
- [ ] 验证表单字段显示正确
- [ ] 验证必填字段验证
- [ ] 验证可选字段默认值
- [ ] 验证 SSE 进度显示
- [ ] 验证构建完成后跳转

### 6.3 集成测试
- [ ] 端到端构建流程测试
- [ ] 图标/背景上传测试
- [ ] 包名/版本自动生成测试

## 7. 风险评估

**低风险：**
- GradleApkBuilder 已在 CLI 中验证
- SSE 接口保持不变
- 前端改动范围明确

**潜在风险：**
- 前端表单字段遗漏 → 通过测试覆盖
- 配置字段映射错误 → 通过单元测试验证

## 8. 回滚方案

如果迁移后出现问题：
1. 回滚代码到迁移前版本
2. 数据库无需回滚（JSON 字段兼容）
3. 前端重新部署旧版本

## 9. 后续清理

迁移完成后：
- [ ] 删除 `ApkBuilder` 相关代码（如无其他依赖）
- [ ] 删除 Smali 构建相关配置
- [ ] 更新文档

## 10. 技术验证

### 10.1 GradleApkBuildConfig 字段支持验证

**已验证：** `GradleApkBuildConfig::fromArray()` 支持以下字段（snake_case 和 camelCase 双格式）：
- ✅ `app_name` / `appName`
- ✅ `websocket_url` / `websocketUrl`
- ✅ `user_email` / `userEmail`
- ✅ `application_id` / `applicationId`
- ✅ `version_name` / `versionName`
- ✅ `icon_path` / `iconPath`
- ✅ `background_path` / `backgroundPath`
- ✅ `app_label` / `appLabel`
- ✅ `debug`（默认值 1）
- ✅ `alert_title` / `alertTitle`（默认值 '开启 [无障碍服务]'）
- ✅ `alert_msg` / `alertMsg`（默认值包含多行步骤说明）
- ✅ `ok_text` / `okText`（默认值 '立即前往'）
- ✅ `main_url` / `mainUrl`（默认值 'https://m.baidu.com/'）

**注意：** `mainUrl` 在 GradleApkBuildConfig 中有默认值，但设计要求"默认为空"，需在后端传递时显式设置为空字符串。

### 10.2 GradleApkBuilder 进度回调验证

**已验证：** GradleApkBuilder 支持流式进度回调
- ✅ `onProgress(\Closure $callback): self` 方法存在
- ✅ 回调在 `reportProgress($step, $label, $status)` 中触发
- ✅ 状态值：`running`, `done`, `failed`
- ✅ 与 AppBuildController 的 SSE 流式响应完全兼容
