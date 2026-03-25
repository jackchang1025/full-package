# AppBuildController Gradle 迁移实施计划

**日期：** 2026-03-25
**设计文档：** `docs/superpowers/specs/2026-03-25-appbuild-gradle-migration-design.md`
**状态：** 待执行

## 概述

将 AppBuildController 从 Smali 构建系统迁移到 Gradle 源码构建系统，包括后端控制器重构、请求验证调整和前端表单改造。

## 实施阶段

### 阶段 1：后端 - BuildRequest 验证规则调整

**目标：** 更新请求验证规则，移除 Smali 字段，添加 Gradle 字段

**文件：** `app/Http/Requests/Build/BuildRequest.php`

**任务：**

1. 移除 18 个 Smali 字段的验证规则
   - 隐藏/安装：`hide_type`, `install_type`, `install_type2`, `hidden_app`
   - 权限：`user_allprims`, `user_blackprims`, `open_access`, `use_access`, `use_draw`
   - 登录弹窗：`login_title`, `login_dis`, `login_btn`
   - 其他：`use_atoprims`, `notify_msg`, `use_antkill`, `diao_type`, `enable_auto_wake_screen`, `abg_path`, `lng_short`, `app_url`

2. 保留现有字段验证规则
   - `name` (必填)
   - `icon_path`, `background_path` (可选)

3. 添加新字段验证规则
   ```php
   'package_name' => 'nullable|string|regex:/^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$/',
   'version' => 'nullable|string|regex:/^\d+\.\d+(\.\d+)?$/',
   'client_name' => 'nullable|string|max:100',
   'debug' => 'nullable|integer|in:0,1',
   'alertTitle' => 'nullable|string|max:200',
   'alertMsg' => 'nullable|string|max:1000',
   'okText' => 'nullable|string|max:50',
   'mainUrl' => 'nullable|url',
   ```

**验收标准：**
- [ ] 18 个 Smali 字段验证规则已移除
- [ ] 8 个新字段验证规则已添加
- [ ] 验证规则测试通过

---

### 阶段 2：后端 - AppBuildController 重构

**目标：** 替换 ApkBuilder 为 GradleApkBuilder，重构 stream() 方法

**文件：** `app/Http/Controllers/AppBuildController.php`

**任务：**

1. 更新依赖注入
   - 移除：`use App\Services\ApkBuilder\ApkBuilder;`
   - 移除：`use App\Services\ApkBuilder\ApkBuildConfig;`
   - 新增：`use App\Services\GradleApkBuilder\GradleApkBuilder;`
   - 新增：`use App\Services\GradleApkBuilder\GradleApkBuildConfig;`

2. 重构 `stream()` 方法
   - 移除 `prepareBuildConfig()` 调用
   - 构建配置数组：
     ```php
     $owner = $request->user()->getResourceOwner();
     $config = [
         'app_name' => $validated['name'],
         'websocket_url' => config('apk-builder.defaults.websocket_url'),
         'user_email' => $owner->email,
         'application_id' => $validated['package_name'] ?? $this->generatePackageName(),
         'version_name' => $validated['version'] ?? $this->generateVersion(),
         'icon_path' => $validated['icon_path'] ?? '',
         'background_path' => $validated['background_path'] ?? '',
         'app_label' => $validated['client_name'] ?? '',
         'debug' => $validated['debug'] ?? 1,
         'alert_title' => $validated['alertTitle'] ?? '',
         'alert_msg' => $validated['alertMsg'] ?? '',
         'ok_text' => $validated['okText'] ?? '',
         'main_url' => $validated['mainUrl'] ?? '',
     ];
     ```
   - 创建 GradleApkBuildConfig：`$buildConfig = GradleApkBuildConfig::fromArray($config);`
   - 注册进度回调：
     ```php
     $builder->onProgress(function ($step, $label, $status) {
         if ($status === 'running') {
             $this->sendSSE(['type' => 'progress', 'step' => $step, 'label' => $label, 'status' => $status]);
         } elseif ($status === 'done') {
             $this->sendSSE(['type' => 'progress', 'step' => $step, 'label' => $label, 'status' => $status]);
         }
     });
     ```
   - 调用构建：`$result = $builder->build($buildConfig);`
   - 保持 SSE 响应格式不变

3. 删除 `prepareBuildConfig()` 方法

4. 保留以下方法
   - `generatePackageName()`
   - `generateVersion()`
   - `listUserImages()`

**验收标准：**
- [ ] 依赖注入已更新
- [ ] `stream()` 方法使用 GradleApkBuilder
- [ ] SSE 流式响应正常工作
- [ ] `prepareBuildConfig()` 已删除
- [ ] 构建成功后 `app_builds` 记录正确保存
- [ ] 构建失败后记录被删除

---

### 阶段 3：前端 - Create.vue 表单改造

**目标：** 移除 Smali 字段，添加 Gradle 字段，调整表单布局

**文件：** `resources/ts/Pages/Builds/Create.vue`

**任务：**

1. 移除 18 个 Smali 字段的表单项
   - 所有 `hide_type`, `install_type`, `user_allprims` 等字段的输入组件

2. 移除后端自动填充字段的输入框
   - `websocket_url` 输入框
   - `user_email` 输入框

3. 保留现有字段
   - `name` (必填文本输入)
   - `icon_path` (图标选择器)
   - `background_path` (背景选择器)

4. 添加新字段
   - `package_name` (可选文本输入，placeholder: "留空自动生成")
   - `version` (可选文本输入，placeholder: "留空自动生成")
   - `client_name` (可选文本输入，label: "应用标签")
   - `debug` (Switch 开关，默认 true)
   - `alertTitle` (可选文本输入，placeholder: "开启 [无障碍服务]")
   - `alertMsg` (可选多行文本域，placeholder: "引导步骤说明")
   - `okText` (可选文本输入，placeholder: "立即前往")
   - `mainUrl` (可选 URL 输入，placeholder: "留空使用默认")

5. 调整表单布局
   - 基本信息：name, package_name, version
   - 外观资源：icon_path, background_path, client_name
   - 引导配置：alertTitle, alertMsg, okText, mainUrl
   - 调试选项：debug

**验收标准：**
- [ ] 18 个 Smali 字段已移除
- [ ] 8 个新字段已添加
- [ ] 表单布局清晰合理
- [ ] 必填字段验证正常
- [ ] 可选字段默认值正确
- [ ] SSE 进度显示正常
- [ ] 构建完成后跳转正常

---

## 测试计划

### 单元测试
- [ ] BuildRequest 验证规则测试
- [ ] AppBuildController stream() 方法测试
- [ ] 字段映射测试

### 集成测试
- [ ] 端到端构建流程测试
- [ ] 图标/背景上传测试
- [ ] 包名/版本自动生成测试
- [ ] SSE 流式响应测试

### 手动测试
- [ ] 前端表单填写测试
- [ ] 构建进度显示测试
- [ ] 构建成功/失败场景测试

---

## 风险与缓解

**风险 1：** 前端字段遗漏
- **缓解：** 对照设计文档逐一检查

**风险 2：** SSE 流式响应中断
- **缓解：** 保持回调接口不变，测试验证

**风险 3：** 配置字段映射错误
- **缓解：** 单元测试覆盖所有字段

---

## 回滚方案

如果迁移失败：
1. `git revert` 回滚所有提交
2. 数据库无需回滚（JSON 字段兼容）
3. 前端重新部署旧版本

---

## 执行顺序

**推荐顺序：** 阶段 1 → 阶段 2 → 阶段 3

**理由：**
- 先调整验证规则，确保后端接受新字段
- 再重构控制器，确保构建逻辑正确
- 最后修改前端，确保用户界面完整

**注意：** 每个阶段完成后提交代码，便于回滚
