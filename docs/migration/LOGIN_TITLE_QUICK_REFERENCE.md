# Login Title 功能 - 快速参考

## 📍 位置

**前端页面**：APK 构建 → 界面设置 → 界面文字（第一项）

## 🎯 功能

自定义 APK 加载页显示的标题文字

## 📝 字段信息

| 属性 | 值 |
|------|-----|
| 字段名 | `login_title` |
| 默认值 | `欢迎使用` |
| 最大长度 | 100 字符 |
| 验证规则 | `nullable\|string\|max:100` |
| 支持 Unicode | ✅ |

## 🔄 数据流

```
前端表单 → BuildRequest → AppBuildController 
→ ApkBuildConfig → SmaliProcessor → APK 模板
```

## 📂 修改文件

### 后端 (2 个修改)

- `app/Http/Requests/Build/BuildRequest.php` - 添加验证规则
- `app/Http/Controllers/AppBuildController.php` - 添加字段处理

### 前端 (1 个修改)

- `resources/ts/Pages/Builds/Create.vue` - 添加输入框

### 测试 (2 个新建)

- `tests/Feature/Build/LoginTitleTest.php` - 9 个单元测试
- `tests/Feature/Build/LoginTitleControllerTest.php` - 3 个控制器测试

## ✅ 测试结果

```
✓ 12 passed (21 assertions)
✓ Duration: 0.40s
```

## 💡 使用示例

### 前端

```vue
<NFormItem label="加载页标题">
    <NInput v-model:value="form.login_title" 
            placeholder="欢迎使用" 
            maxlength="100" 
            show-count />
</NFormItem>
```

### 后端

```php
// 验证
'login_title' => 'nullable|string|max:100'

// 处理（空字符串使用默认值）
'login_title' => !empty($validated['login_title']) 
    ? $validated['login_title'] 
    : '欢迎使用'
```

### APK 模板

```smali
# 占位符: [USE-AUTOGRANT]
# 目标字段: My_Configs.loadingText
const-string v0, "欢迎使用"
sput-object v0, Lcom/icontrol/protector/My_Configs;->loadingText:Ljava/lang/String;
```

## 🛡️ 特殊字符处理

| 输入 | 输出 |
|------|------|
| `"` | `\\"` |
| `\` | `\\\\` |
| `\n` | `\\n` |
| `🎉` | `🎉` (保持原样) |

## 🔍 验证命令

```bash
# 运行测试
./vendor/bin/sail pest tests/Feature/Build/

# 检查前端代码
grep "加载页标题" resources/ts/Pages/Builds/Create.vue
```

## 📚 相关文档

- [LOGIN_TITLE_FEATURE.md](./LOGIN_TITLE_FEATURE.md) - 详细功能文档
- [LOGIN_TITLE_IMPLEMENTATION_SUMMARY.md](./LOGIN_TITLE_IMPLEMENTATION_SUMMARY.md) - 实现总结
- [APK_BUILDER.md](./APK_BUILDER.md) - APK 构建服务文档
