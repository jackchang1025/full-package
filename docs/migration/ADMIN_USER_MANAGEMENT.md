# 总后台用户管理与子账号

> 总后台用户管理模块、子账号（团队）功能、两栏布局与前端 UX 实现说明

## 一、功能概述

### 1.1 子账号（团队）模型

- **非多团队**：系统采用「主账号 + 子账号」的单一层级，不是「用户创建多个团队、每个团队下再建账号」。
- **主账号**：`users.parent_id = null`，可拥有 `teams.manage` 权限和 `max_sub_accounts` 子账号配额。
- **子账号**：`users.parent_id = 主账号 id`，继承主账号的订阅到期时间，共享主账号下的设备、APK 构建等资源；权限由主账号在「单独权限」中分配（只能是主账号自身权限的子集）。

### 1.2 总后台用户管理

- **入口**：总后台 → 用户管理（需管理员权限）。
- **能力**：用户列表（树形展示主账号与子账号）、搜索、分页、内联编辑/创建、删除；支持通过 URL 参数保持选中项、展开状态与搜索条件。

---

## 二、后端实现

### 2.1 数据与模型

| 说明 | 位置 |
|------|------|
| 用户表子账号字段 | `database/migrations/..._add_sub_account_fields_to_users_table.php`：`parent_id`、`max_sub_accounts` |
| User 模型 | `app/Models/User.php`：`parent()`、`subAccounts()`、`isSubAccount()`、`getResourceOwnerId()`、`canCreateSubAccount()`、`hasActiveSubscription()` 重写、`scopeParentOnly`、`scopeSearch` |

资源归属统一使用 `User::getResourceOwnerId()`（子账号返回主账号 id），设备、APK 构建等均按此查询。

### 2.2 权限与配置

- **权限**：`teams.manage`（团队/子账号管理）、`devices.view` / `devices.edit`、`builds.view` / `builds.create` / `builds.assets.manage` 等，见 `database/seeders/RolePermissionSeeder.php` 与 `config/permission_labels.php`、`lang/zh_CN/permissions.php`。
- **角色**：如 `client` 默认拥有除 `teams.manage` 外的权限；主账号可被分配 `teams.manage` 和 `max_sub_accounts` 配额。

### 2.3 总后台用户列表 API

**路由**：`admin.users.index`（GET）

**控制器**：`App\Http\Controllers\Admin\UserController::index`

**查询参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `search` | string | 按用户名/邮箱搜索（主账号与子账号均匹配） |
| `page` | int | 分页页码 |
| `selected` | int | 当前选中用户 id；若该用户（或其主账号）不在当前页，会 302 重定向到所在页 |
| `expanded` | string | 树展开节点 id，逗号分隔，如 `1,2,3` |

**响应**：Inertia 渲染 `Admin/Users/Index`，传入 `users`（分页树形数据）、`selectedUser`（选中用户的完整编辑结构）、`roles`、`roleLabels`、`permissions`、`permissionLabels`、`filters`（含 `search`、`selected`、`expanded`）。

**树形结构**：仅主账号分页；每条主账号带 `children`（其子账号列表）。前端据此组装 NTree 的 `key/children`。

### 2.4 编辑、创建、删除与重定向

- **更新/创建/删除**：使用 Form Request 校验（`UpdateUserRequest`、`StoreUserRequest`）；更新时子账号的 `subscription_expires_at` 与主账号同步。
- **重定向**：编辑/创建成功后重定向回 `admin.users.index`，并带上 `selected`（当前用户 id）和请求中的 `expanded`，以保持树展开状态；删除后仅带 `expanded`。

---

## 三、前端：用户管理页面（两栏布局）

### 3.1 页面与路由

- **组件**：`resources/ts/Pages/Admin/Users/Index.vue`
- **布局**：`AdminLayout`，标题「用户管理」。

### 3.2 布局结构

- **左栏（`.um-tree`）**  
  - 宽度 400px（min-width 320px），独立卡片样式。  
  - 顶部：统计（主账号数、子账号数、总数）、搜索框（用户名/邮箱）、「新增」按钮。  
  - 中部：`NScrollbar` + `NTree`，展示主账号与子账号树；无数据时占位文案。  
  - 底部分页：上一页/下一页与页码信息（在 `last_page > 1` 时显示）。

- **右栏（`.um-detail`）**  
  - 三种状态：  
    - **idle**：未选用户且非创建模式，显示引导文案与插图。  
    - **edit**：存在 `selectedUser` 时，显示该用户头像、名称、邮箱及表单（基本信息 + 订阅与权限），底部「保存更改」。  
    - **create**：点击「新增」后显示创建用户表单，底部「创建用户」「取消」。

### 3.3 URL 与状态同步

所有会刷新列表的请求（选节点、搜索、清空搜索、分页、保存、创建、删除）均通过 `buildUsersQuery()` 拼 query：

- `search`：来自 `searchText`，空时省略。  
- `selected`：选中用户 id。  
- `expanded`：当前展开节点 id 数组转逗号分隔字符串。  
- `page`：分页页码。

从而保证：

- 选中某用户后 URL 带 `selected=id`，刷新或分享链接仍打开该用户编辑。  
- 展开/收起节点后，再搜索或翻页，树会按 `expanded` 恢复展开。  
- 清空搜索时调用 `handleClearSearch()`：先置空 `searchText`，再请求 `buildUsersQuery({ search: '' })`，避免 NInput 的 `@clear` 与 v-model 时序导致 URL 未清空。

### 3.4 树节点行为

- **选中**：`selectedKeys` 来自 `filters.selected`；点击节点触发 `handleTreeSelect`，请求带 `selected` 与当前 `expanded`。  
- **展开**：`expandedKeys` 由 `filters.expanded` 初始化，并在 watch 中合并「当前选中项的父节点」，确保选中子账号时父节点展开；用户展开/收起通过 `handleExpandedKeysUpdate` 更新本地，下次请求带出。  
- **删除**：每个节点 suffix 均渲染删除按钮（小圆型红色图标），用 CSS 控制可见性：  
  - 默认隐藏（`opacity: 0`、`pointer-events: none`）。  
  - 节点行 hover 或节点为选中态时显示；按钮自身 hover 时高亮。  
  点击删除按钮会 `stopPropagation`，避免触发选中，然后打开删除确认弹框。

### 3.5 删除确认弹框

- 使用 **StatusModal**（见下节），`variant="danger"`，`positive-text="确定删除"`，`negative-text="取消"`。  
- 确认后请求 `DELETE /admin/users/{id}?expanded=...`，成功后清空 `pendingDeleteId`/`pendingDeleteName` 并提示。

### 3.6 样式要点

- 左右栏为独立卡片（圆角、阴影、边框），中间 `gap: 16px`。  
- 右侧表单区左边距统一为 **64px**（头部、子账号提示、`.um-form-scroll .n-scrollbar-content`），避免内容贴边。  
- 树节点选中态：左侧竖条 + 浅色背景；展开/收起图标通过 CSS 居中。  
- 响应式：宽度 &lt; 900px 时改为上下布局，树区域限高。

---

## 四、StatusModal 组件

### 4.1 用途

通用状态/结果弹框，用于权限提示、过期或配额提示、删除确认等，风格统一。

**路径**：`resources/ts/Components/StatusModal.vue`

### 4.2 变体（variant）

| 值 | 场景 | 主色 |
|----|------|------|
| `indigo` | 默认，权限拒绝、访问受限 | 靛蓝 |
| `amber` | 警告、过期、配额不足 | 琥珀 |
| `danger` | 删除、不可逆操作 | 红 |

### 4.3 Props

| Prop | 类型 | 默认 | 说明 |
|------|------|------|------|
| show | boolean | - | 是否显示（v-model:show） |
| title | string | '' | 标题 |
| content | string | '' | 正文 |
| positiveText | string | '知道了' | 主按钮文案 |
| negativeText | string | '' | 取消按钮文案；非空时显示双按钮 |
| variant | 'indigo' \| 'amber' \| 'danger' | 'indigo' | 主题 |
| icon | Component | ShieldCheckmarkOutline | 图标组件 |
| maskClosable | boolean | true | 点击遮罩是否关闭 |
| closable | boolean | true | 是否显示关闭按钮 |

### 4.4 事件

- `update:show`：显隐变化。  
- `confirm`：点击主按钮（确定）。  
- `cancel`：点击取消按钮（仅在存在 `negativeText` 时）。

### 4.5 使用示例

```vue
<!-- 单按钮提示 -->
<StatusModal v-model:show="visible" title="无权限" content="您没有团队管理权限" />

<!-- 双按钮确认（如删除） -->
<StatusModal
  v-model:show="deleteModalShow"
  variant="danger"
  :icon="TrashOutline"
  title="确认删除用户"
  :content="`确定要删除用户「${name}」吗？此操作不可撤销。`"
  positive-text="确定删除"
  negative-text="取消"
  @confirm="confirmDelete"
/>
```

---

## 五、相关文件索引

| 类型 | 路径 |
|------|------|
| 迁移 | `app/database/migrations/..._add_sub_account_fields_to_users_table.php` |
| 模型 | `app/Models/User.php` |
| 种子 | `app/database/seeders/RolePermissionSeeder.php` |
| 权限配置/文案 | `app/config/permission_labels.php`，`app/lang/zh_CN/permissions.php`，`app/lang/en/permissions.php` |
| 总后台用户 | `app/Http/Controllers/Admin/UserController.php` |
| Form Request | `app/Http/Requests/Admin/StoreUserRequest.php`，`app/Http/Requests/Admin/UpdateUserRequest.php` |
| 用户管理页 | `app/resources/ts/Pages/Admin/Users/Index.vue` |
| 通用状态弹框 | `app/resources/ts/Components/StatusModal.vue` |
| 子账号（用户端） | `app/Http/Controllers/SubAccountController.php`，`app/resources/ts/Pages/SubAccounts/*.vue` |

---

## 六、与 AGENTS.md / 其他文档的衔接

- **开发规范**：见项目根目录 `AGENTS.md`、`CLAUDE.md`。  
- **前端架构**：见 `docs/migration/FRONTEND.md`。  
- **API 约定**：见 `docs/migration/API.md`。  
- **子账号业务**：本文档第二节与 `User` 模型、`RolePermissionSeeder` 对应；用户端子账号 CRUD 见 `SubAccountController` 与子账号相关路由。

如需扩展「仅主账号可分配的角色」或「子账号可选的权限集合」，需同时改动 Seeder、权限配置与前端角色/权限选项的过滤逻辑。
