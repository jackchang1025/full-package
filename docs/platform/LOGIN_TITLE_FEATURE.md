# Login Title 字段功能实现

## 概述

在 APK 构建页面的"界面设置"中添加了 `login_title` 字段，允许用户自定义 APK 加载页标题。

## 实现内容

### 1. 后端验证 (`BuildRequest.php`)

```php
'login_title' => 'nullable|string|max:100',
```

- 最大长度：100 字符
- 可选字段
- 支持 Unicode 字符（中文、emoji 等）

### 2. 控制器处理 (`AppBuildController.php`)

```php
'login_title' => !empty($validated['login_title']) ? $validated['login_title'] : '欢迎使用',
```

- 默认值：`欢迎使用`
- 空字符串自动使用默认值
- 存储在 `build_config` 中

### 3. 前端表单 (`Create.vue`)

```vue
<NFormItem label="加载页标题">
    <NInput v-model:value="form.login_title" placeholder="欢迎使用" maxlength="100" show-count />
</NFormItem>
```

- 位置：界面设置 → 界面文字（第一项）
- 显示字符计数
- 最大长度限制

### 4. 配置对象 (`ApkBuildConfig.php`)

```php
public readonly string $loginTitle = '欢迎使用',
```

- 支持 `login_title` 和 `loginTitle` 两种命名
- 验证最大长度
- 转换为 `toArray()` 时使用 snake_case

### 5. Smali 注入 (`SmaliProcessor.php`)

```php
'[USE-AUTOGRANT]' => $this->escapeForSmaliString($config->loginTitle),
```

- 占位符：`[USE-AUTOGRANT]`
- 映射到 APK 模板的 `loadingText` 字段
- 自动转义特殊字符（双引号、反斜杠、换行等）

## 数据流

```
前端表单 (login_title)
    ↓
BuildRequest 验证
    ↓
AppBuildController::prepareBuildConfig()
    ↓
ApkBuildConfig::fromArray()
    ↓
SmaliProcessor::processSmaliConfig()
    ↓
My_Configs.smali (loadingText 字段)
    ↓
APK 运行时显示
```

## 测试覆盖

### 单元测试 (`LoginTitleTest.php`)

- ✅ 字段传递到 ApkBuildConfig
- ✅ 支持 snake_case 和 camelCase
- ✅ 默认值处理
- ✅ 长度验证
- ✅ 特殊字符转义
- ✅ toArray() 包含字段
- ✅ 空字符串处理
- ✅ Unicode 字符支持

### 控制器测试 (`LoginTitleControllerTest.php`)

- ✅ prepareBuildConfig 包含字段
- ✅ 空字符串使用默认值
- ✅ 未提供时使用默认值

## 使用示例

### 前端提交

```javascript
form.login_title = '欢迎使用我的应用';
```

### 后端接收

```php
$validated['login_title'] // '欢迎使用我的应用'
```

### APK 模板注入

```smali
const-string v0, "欢迎使用我的应用"
sput-object v0, Lcom/icontrol/protector/My_Configs;->loadingText:Ljava/lang/String;
```

### 运行时显示

```java
TextView.setText(My_Configs.loadingText); // 显示 "欢迎使用我的应用"
```

## 特殊字符处理

| 输入 | 转义后 | 说明 |
|------|--------|------|
| `欢迎"使用"` | `欢迎\\"使用\\"` | 双引号转义 |
| `欢迎\\使用` | `欢迎\\\\使用` | 反斜杠转义 |
| `欢迎\n使用` | `欢迎\\n使用` | 换行符转义 |
| `欢迎 🎉 使用` | `欢迎 🎉 使用` | Unicode 保持原样 |

## 注意事项

1. **最大长度**：100 字符（包括中文、emoji）
2. **默认值**：空字符串自动使用 `欢迎使用`
3. **特殊字符**：自动转义，无需手动处理
4. **历史问题**：修复了之前 `[USE-AUTOGRANT]` 错误映射到 `useAtoprims` 的 bug

## 相关文件

- `app/Http/Requests/Build/BuildRequest.php` - 验证规则
- `app/Http/Controllers/AppBuildController.php` - 控制器逻辑
- `app/Services/ApkBuilder/ApkBuildConfig.php` - 配置对象
- `app/Services/ApkBuilder/SmaliProcessor.php` - Smali 处理
- `resources/ts/Pages/Builds/Create.vue` - 前端表单
- `tests/Feature/Build/LoginTitleTest.php` - 单元测试
- `tests/Feature/Build/LoginTitleControllerTest.php` - 控制器测试
