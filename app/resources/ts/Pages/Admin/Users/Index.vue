<script setup lang="ts">
import { computed, ref, watch, h } from 'vue';
import { Head, router, usePage } from '@inertiajs/vue3';
import {
    NTree, NInput, NInputGroup, NButton, NIcon, NAvatar, NTag,
    NForm, NFormItem, NInputNumber, NDatePicker, NSelect,
    NAlert, NScrollbar, NDivider, useMessage,
} from 'naive-ui';
import type { TreeOption } from 'naive-ui';
import {
    AddOutline, PersonOutline, PeopleOutline,
    TrashOutline, SaveOutline, PersonAddOutline, CopyOutline,
} from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import StatusModal from '@/Components/StatusModal.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

// ── 类型 ─────────────────────────────────────

interface UserTreeNode {
    id: number;
    username: string;
    email: string;
    subscription_expires_at: string | null;
    subscription_type: string | null;
    roles: string[];
    max_sub_accounts?: number;
    sub_accounts_count?: number;
    parent_id?: number | null;
    children?: UserTreeNode[];
    all_permissions?: string[];
}

interface SelectedUser {
    id: number;
    username: string;
    email: string;
    subscription_expires_at: string | null;
    subscription_type: string | null;
    roles: string[];
    direct_permissions: string[];
    contact: string | null;
    max_sub_accounts: number;
    is_sub_account: boolean;
    parent_username: string | null;
    sub_accounts_count: number;
    parent_subscription_expires_at?: string | null;
    parent_permissions?: string[];
}

interface Paginated<T> {
    data: T[];
    current_page: number;
    last_page: number;
    per_page: number;
    total: number;
}

// ── Props ────────────────────────────────────

const props = defineProps<{
    users: Paginated<UserTreeNode>;
    selectedUser: SelectedUser | null;
    roles: string[];
    roleLabels?: Record<string, string>;
    permissions?: string[];
    permissionLabels?: Record<string, string>;
    filters?: { search?: string; selected?: number | null; expanded?: number[] };
}>();

const { adminRoute } = useAdminBasePath();
const message = useMessage();
const page = usePage();

// ── 状态 ─────────────────────────────────────

const searchText = ref(props.filters?.search ?? '');
const isCreating = ref(false);
const expandedKeys = ref<number[]>([...(props.filters?.expanded ?? [])]);

// 编辑表单
const editForm = ref({
    username: '',
    email: '',
    password: '',
    password_confirmation: '',
    subscription_expires_at: null as string | null,
    roles: [] as string[],
    direct_permissions: [] as string[],
    max_sub_accounts: 0,
});
const editErrors = ref<Record<string, string>>({});
const editProcessing = ref(false);

// 创建表单
const createForm = ref({
    username: '',
    email: '',
    password: '',
    password_confirmation: '',
    subscription_expires_at: null as string | null,
    roles: ['client'] as string[],
    parent_id: null as number | null,
    direct_permissions: [] as string[],
    max_sub_accounts: 0,
});
const createErrors = ref<Record<string, string>>({});
const createProcessing = ref(false);
const createParentEmail = ref<string | null>(null);
const createParentPermissions = ref<string[]>([]);

// 删除确认弹框
const deleteModalShow = ref(false);
const pendingDeleteId = ref<number | null>(null);
const pendingDeleteName = ref('');

// ── 计算属性 ──────────────────────────────────

const currentMode = computed<'idle' | 'edit' | 'create'>(() => {
    if (isCreating.value) return 'create';
    if (props.selectedUser) return 'edit';
    return 'idle';
});

const selectedKeys = computed(() => {
    const id = props.filters?.selected;
    return id ? [id] : [];
});

interface ExtTreeOption extends TreeOption {
    rawUser: UserTreeNode;
}

const treeData = computed<ExtTreeOption[]>(() => {
    return props.users.data.map(user => ({
        key: user.id,
        label: user.username,
        rawUser: user,
        children: user.children?.length
            ? user.children.map(child => ({
                key: child.id,
                label: child.username,
                rawUser: child,
            }))
            : undefined,
    }));
});

const roleOptions = computed(() => {
    const labels = props.roleLabels ?? {};
    return (props.roles ?? []).map(r => ({ label: labels[r] ?? r, value: r }));
});

const permissionOptions = computed(() => {
    const labels = props.permissionLabels ?? {};
    return (props.permissions ?? []).map(p => ({ label: labels[p] ?? p, value: p }));
});

const totalSubAccounts = computed(() =>
    props.users.data.reduce((sum, user) => sum + (user.children?.length ?? 0), 0)
);

/** 编辑子账号时：权限选项限制为父账号的有效权限范围 */
const editPermissionOptions = computed(() => {
    if (props.selectedUser?.is_sub_account && props.selectedUser.parent_permissions) {
        const allowed = new Set(props.selectedUser.parent_permissions);
        const labels = props.permissionLabels ?? {};
        return props.selectedUser.parent_permissions.map(p => ({ label: labels[p] ?? p, value: p }));
    }
    return permissionOptions.value;
});

/** 创建子账号时：权限选项限制为父账号的有效权限范围 */
const createPermissionOptions = computed(() => {
    if (createForm.value.parent_id && createParentPermissions.value.length) {
        const labels = props.permissionLabels ?? {};
        return createParentPermissions.value.map(p => ({ label: labels[p] ?? p, value: p }));
    }
    return permissionOptions.value;
});

// ── Tree 渲染 ─────────────────────────────────

const renderPrefix = ({ option }: { option: TreeOption }) => {
    const raw = (option as ExtTreeOption).rawUser;
    if (!raw) return null;
    const isSub = !!raw.parent_id;
    return h(NAvatar, {
        round: true,
        size: 26,
        class: isSub ? 'tree-avatar tree-avatar--sub' : 'tree-avatar tree-avatar--parent',
    }, { default: () => raw.email[0]?.toUpperCase() ?? 'U' });
};

const renderLabel = ({ option }: { option: TreeOption }) => {
    const raw = (option as ExtTreeOption).rawUser;
    if (!raw) return h('span', option.label as string);
    return h('span', { class: 'tree-label' }, raw.email);
};

const renderSuffix = ({ option }: { option: TreeOption }) => {
    const raw = (option as ExtTreeOption).rawUser;
    if (!raw) return null;
    const isSub = !!raw.parent_id;
    const items: any[] = [];

    if (raw.subscription_expires_at) {
        const expired = new Date(raw.subscription_expires_at) < new Date();
        if (expired) {
            items.push(h(NTag, { size: 'tiny', round: true, bordered: false, type: 'error' }, { default: () => '过期' }));
        }
    }

    // 复制邮箱按钮
    items.push(
        h(NButton, {
            size: 'tiny',
            quaternary: true,
            circle: true,
            class: 'tree-action-btn',
            onClick: (e: MouseEvent) => {
                e.stopPropagation();
                navigator.clipboard.writeText(raw.email).then(() => {
                    message.success('已复制邮箱');
                });
            },
        }, {
            icon: () => h(NIcon, { size: 13 }, { default: () => h(CopyOutline) }),
        })
    );

    // 父节点显示"添加子账号"按钮
    if (!isSub) {
        items.push(
            h(NButton, {
                size: 'tiny',
                quaternary: true,
                circle: true,
                type: 'primary',
                class: 'tree-action-btn',
                onClick: (e: MouseEvent) => {
                    e.stopPropagation();
                    startCreateSubAccount(raw.id, raw.email);
                },
            }, {
                icon: () => h(NIcon, { size: 14 }, { default: () => h(PersonAddOutline) }),
            })
        );
    }

    // 每个节点都渲染删除按钮，用 CSS 控制可见性（hover / 选中时显示）
    items.push(
        h(NButton, {
            size: 'tiny',
            quaternary: true,
            circle: true,
            type: 'error',
            class: 'tree-delete-btn',
            onClick: (e: MouseEvent) => {
                e.stopPropagation();
                pendingDeleteId.value = raw.id;
                pendingDeleteName.value = raw.username;
                deleteModalShow.value = true;
            },
        }, {
            icon: () => h(NIcon, { size: 14 }, { default: () => h(TrashOutline) }),
        })
    );

    return h('div', { class: 'tree-suffix' }, items);
};

// ── 交互 ─────────────────────────────────────

const buildUsersQuery = (overrides: { selected?: number; search?: string; page?: number; expanded?: number[] } = {}) => {
    const q: Record<string, string | number | undefined> = {
        search: overrides.search ?? (searchText.value || undefined),
        page: overrides.page,
        selected: overrides.selected,
        expanded: (overrides.expanded ?? expandedKeys.value).length
            ? (overrides.expanded ?? expandedKeys.value).join(',')
            : undefined,
    };
    return Object.fromEntries(Object.entries(q).filter(([, v]) => v !== undefined && v !== ''));
};

const handleTreeSelect = (keys: Array<string | number>) => {
    const selectedId = keys[0] as number;
    if (!selectedId) return;
    isCreating.value = false;
    router.get(adminRoute('/users'), buildUsersQuery({ selected: selectedId }), { preserveScroll: true });
};

const handleExpandedKeysUpdate = (keys: number[]) => {
    expandedKeys.value = keys;
};

const submitSearch = () => {
    isCreating.value = false;
    router.get(adminRoute('/users'), buildUsersQuery({ search: searchText.value }), { preserveScroll: true });
};

/** NInput @clear 可能在 v-model 更新前触发，显式清空再提交 */
const handleClearSearch = () => {
    searchText.value = '';
    router.get(adminRoute('/users'), buildUsersQuery({ search: '' }), { preserveScroll: true });
};

const startCreate = () => {
    isCreating.value = true;
    createParentEmail.value = null;
    createParentPermissions.value = [];
    createForm.value = {
        username: '',
        email: '',
        password: '',
        password_confirmation: '',
        subscription_expires_at: null,
        roles: ['client'],
        parent_id: null,
        direct_permissions: [],
        max_sub_accounts: 0,
    };
    createErrors.value = {};
};

const startCreateSubAccount = (parentId: number, parentEmail: string) => {
    // 从树数据中查找父账号的权限
    const parentNode = props.users.data.find(u => u.id === parentId);
    isCreating.value = true;
    createParentEmail.value = parentEmail;
    createParentPermissions.value = parentNode?.all_permissions ?? [];
    createForm.value = {
        username: '',
        email: '',
        password: '',
        password_confirmation: '',
        subscription_expires_at: null,
        roles: ['client'],
        parent_id: parentId,
        direct_permissions: [],
        max_sub_accounts: 0,
    };
    createErrors.value = {};
};

const submitEdit = () => {
    if (!props.selectedUser) return;
    editProcessing.value = true;
    const expandedQuery = expandedKeys.value.length ? `?expanded=${expandedKeys.value.join(',')}` : '';
    router.put(adminRoute(`/users/${props.selectedUser.id}`) + expandedQuery, editForm.value, {
        preserveScroll: true,
        onError: (errors) => { editErrors.value = errors; },
        onFinish: () => { editProcessing.value = false; },
        onSuccess: () => { message.success('用户已保存'); },
    });
};

const submitCreate = () => {
    createProcessing.value = true;
    const expandedQuery = expandedKeys.value.length ? `?expanded=${expandedKeys.value.join(',')}` : '';
    router.post(adminRoute('/users') + expandedQuery, createForm.value, {
        preserveScroll: true,
        onError: (errors) => { createErrors.value = errors; },
        onFinish: () => { createProcessing.value = false; },
        onSuccess: () => { message.success('用户已创建'); },
    });
};

const confirmDelete = () => {
    if (!pendingDeleteId.value) return;
    const expandedQuery = expandedKeys.value.length ? `?expanded=${expandedKeys.value.join(',')}` : '';
    router.delete(adminRoute(`/users/${pendingDeleteId.value}`) + expandedQuery, {
        preserveScroll: true,
        onSuccess: () => {
            message.success('用户已删除');
            pendingDeleteId.value = null;
            pendingDeleteName.value = '';
        },
    });
};

const goPage = (pageNum: number) => {
    router.get(adminRoute('/users'), buildUsersQuery({ page: pageNum }), { preserveScroll: true });
};

// ── Watchers ─────────────────────────────────

// 验证失败时 Laravel 302 重定向带回 errors，Inertia 跟随加载新页面，onError 不会被调用；
// 错误在 page.props.errors，需同步到 editErrors/createErrors 以便表单展示。
const pageErrors = computed(() => (page.props.errors as Record<string, string> | undefined) ?? {});

const editFormFieldSet = new Set(['username', 'email', 'password', 'password_confirmation', 'subscription_expires_at', 'roles', 'direct_permissions', 'max_sub_accounts']);
const createFormFieldSet = new Set(['username', 'email', 'password', 'password_confirmation', 'subscription_expires_at', 'roles', 'parent_id', 'direct_permissions', 'max_sub_accounts']);

/**
 * 当前表单中无法展示的错误（如编辑模式下的 parent_id 错误、idle 模式下的所有错误）。
 * 用于在面板顶部以 NAlert 形式渲染，确保用户一定能看到。
 */
const unmatchedErrors = computed<string[]>(() => {
    const errObj = pageErrors.value;
    if (!errObj || Object.keys(errObj).length === 0) return [];

    const inEditMode = !!props.selectedUser && !isCreating.value;
    const visibleFields = inEditMode ? editFormFieldSet : isCreating.value ? createFormFieldSet : null;

    const msgs: string[] = [];
    for (const [key, msg] of Object.entries(errObj)) {
        if (!visibleFields || !visibleFields.has(key)) {
            msgs.push(msg as string);
        }
    }
    return msgs;
});

watch(
    () => [pageErrors.value, props.selectedUser] as const,
    ([errors, selectedUser]) => {
        const errObj = errors && typeof errors === 'object' && Object.keys(errors).length > 0 ? errors : null;
        if (!errObj) {
            editErrors.value = {};
            createErrors.value = {};
            return;
        }

        const inEditMode = !!selectedUser && !isCreating.value;

        if (inEditMode) {
            editErrors.value = { ...errObj };
            createErrors.value = {};
        } else if (isCreating.value) {
            createErrors.value = { ...errObj };
            editErrors.value = {};
        } else {
            editErrors.value = {};
            createErrors.value = {};
        }
    },
    { immediate: true }
);

// 选中用户变化时重置编辑表单（切换用户时清空错误，避免覆盖服务端带回的验证错误）
watch(() => props.selectedUser, (user, oldUser) => {
    if (user) {
        editForm.value = {
            username: user.username,
            email: user.email,
            password: '',
            password_confirmation: '',
            subscription_expires_at: user.subscription_expires_at,
            roles: [...user.roles],
            direct_permissions: [...user.direct_permissions],
            max_sub_accounts: user.max_sub_accounts,
        };
        if (oldUser && oldUser.id !== user.id) {
            editErrors.value = {};
        }
        isCreating.value = false;
    }
}, { immediate: true });

/* 从 URL 恢复展开状态，并确保选中项的父节点处于展开 */
watch(
    () => [props.filters?.expanded, props.filters?.selected, props.users.data] as const,
    () => {
        const expanded = new Set<number>(props.filters?.expanded ?? []);
        const selectedId = props.filters?.selected;
        if (selectedId) {
            for (const user of props.users.data) {
                if (user.children?.some((c) => c.id === selectedId)) {
                    expanded.add(user.id);
                    break;
                }
            }
        }
        expandedKeys.value = [...expanded];
    },
    { immediate: true }
);
</script>

<template>
    <Head title="用户管理" />
    <AdminLayout>
        <template #header-title>用户管理</template>

        <div class="um">
            <!-- ━━ 左栏：用户树 ━━━━━━━━━━━━━━━━━━ -->
            <aside class="um-tree">
                <header class="um-tree__head">
                    <div class="um-tree__meta">
                        <div class="um-tree__meta-item">
                            <span class="um-tree__meta-label">主账号</span>
                            <strong>{{ users.data.length }}</strong>
                        </div>
                        <div class="um-tree__meta-item">
                            <span class="um-tree__meta-label">子账号</span>
                            <strong>{{ totalSubAccounts }}</strong>
                        </div>
                        <div class="um-tree__meta-item">
                            <span class="um-tree__meta-label">总数</span>
                            <strong>{{ users.total }}</strong>
                        </div>
                    </div>
                    <NInputGroup>
                        <NInput
                            v-model:value="searchText"
                            placeholder="搜索用户名 / 邮箱"
                            clearable
                            size="small"
                            @keyup.enter="submitSearch"
                            @clear="handleClearSearch"
                        />
                        <NButton size="small" type="primary" @click="submitSearch">搜索</NButton>
                    </NInputGroup>
                    <NButton
                        size="small"
                        type="primary"
                        secondary
                        class="um-tree__add-btn"
                        @click="startCreate"
                    >
                        <template #icon><NIcon :component="AddOutline" /></template>
                        新增
                    </NButton>
                </header>

                <NScrollbar class="um-tree__body">
                    <NTree
                        v-if="treeData.length"
                        :data="treeData"
                        :selected-keys="selectedKeys"
                        :expanded-keys="expandedKeys"
                        block-line
                        selectable
                        :render-prefix="renderPrefix"
                        :render-label="renderLabel"
                        :render-suffix="renderSuffix"
                        @update:selected-keys="handleTreeSelect"
                        @update:expanded-keys="handleExpandedKeysUpdate"
                        class="um-ntree"
                    />
                    <div v-else class="um-tree__empty">
                        <NIcon :component="PeopleOutline" size="36" depth="4" />
                        <span>暂无用户数据</span>
                    </div>
                </NScrollbar>

                <footer v-if="users.last_page > 1" class="um-tree__foot">
                    <span class="um-tree__page-info">{{ users.current_page }} / {{ users.last_page }}</span>
                    <NButton size="tiny" :disabled="users.current_page <= 1" @click="goPage(users.current_page - 1)">上页</NButton>
                    <NButton size="tiny" :disabled="users.current_page >= users.last_page" @click="goPage(users.current_page + 1)">下页</NButton>
                </footer>
            </aside>

            <!-- ━━ 右栏：详情面板 ━━━━━━━━━━━━━━━━━ -->
            <main class="um-detail">
                <!-- 全局错误提示（当前表单无法展示的后端错误） -->
                <NAlert v-if="unmatchedErrors.length" type="error" class="um-global-error" closable>
                    <ul v-if="unmatchedErrors.length > 1" style="margin: 0; padding-left: 1.2em;">
                        <li v-for="(msg, i) in unmatchedErrors" :key="i">{{ msg }}</li>
                    </ul>
                    <span v-else>{{ unmatchedErrors[0] }}</span>
                </NAlert>

                <!-- 空闲状态 -->
                <div v-if="currentMode === 'idle'" class="um-idle">
                    <div class="um-idle__graphic">
                        <div class="um-idle__ring"></div>
                        <NIcon :component="PersonOutline" size="44" class="um-idle__icon" />
                    </div>
                    <p class="um-idle__title">选择左侧用户查看详情</p>
                    <p class="um-idle__sub">或点击「新增」按钮创建新用户</p>
                </div>

                <!-- ── 编辑面板 ── -->
                <div v-else-if="currentMode === 'edit' && selectedUser" class="um-form-wrap" :key="`edit-${selectedUser.id}`">
                    <header class="um-form-head">
                        <div class="um-form-head__left">
                            <NAvatar round :size="42" class="um-form-head__avatar">
                                {{ selectedUser.username[0]?.toUpperCase() ?? 'U' }}
                            </NAvatar>
                            <div class="um-form-head__info">
                                <h3 class="um-form-head__name">{{ selectedUser.username }}</h3>
                                <span class="um-form-head__email">{{ selectedUser.email }}</span>
                            </div>
                        </div>
                    </header>

                    <NAlert v-if="selectedUser.is_sub_account" type="info" class="um-sub-alert">
                        该用户是子账号，父账号：<strong>{{ selectedUser.parent_username }}</strong>
                    </NAlert>

                    <NScrollbar class="um-form-scroll">
                        <div class="um-form-pad">
                            <NForm class="um-form" @submit.prevent="submitEdit">
                                <div class="um-section">
                                    <h4 class="um-section__title">基本信息</h4>
                                    <NFormItem label="用户名" :validation-status="editErrors.username ? 'error' : undefined" :feedback="editErrors.username">
                                        <NInput v-model:value="editForm.username" placeholder="用户名" maxlength="50" show-count clearable :disabled="editProcessing" />
                                    </NFormItem>
                                    <NFormItem label="邮箱" :validation-status="editErrors.email ? 'error' : undefined" :feedback="editErrors.email">
                                        <NInput v-model:value="editForm.email" placeholder="邮箱" clearable :disabled="editProcessing" />
                                    </NFormItem>
                                    <NFormItem label="新密码" :validation-status="editErrors.password ? 'error' : undefined" :feedback="editErrors.password">
                                        <NInput v-model:value="editForm.password" type="password" placeholder="留空则不修改" show-password-on="click" :disabled="editProcessing" />
                                    </NFormItem>
                                    <NFormItem label="确认密码" :validation-status="editErrors.password_confirmation ? 'error' : undefined" :feedback="editErrors.password_confirmation">
                                        <NInput v-model:value="editForm.password_confirmation" type="password" placeholder="留空则不修改" show-password-on="click" :disabled="editProcessing" />
                                    </NFormItem>
                                </div>

                                <NDivider />

                                <div class="um-section">
                                    <h4 class="um-section__title">订阅与权限</h4>

                                    <!-- 子账号：到期时间继承父账号，只读 -->
                                    <NFormItem v-if="selectedUser.is_sub_account" label="到期时间">
                                        <NInput :value="selectedUser.parent_subscription_expires_at || selectedUser.subscription_expires_at || '未设置'" disabled style="width: 100%" />
                                        <template #feedback>
                                            <span class="um-hint">继承自父账号，不可修改</span>
                                        </template>
                                    </NFormItem>
                                    <!-- 主账号：到期时间可编辑 -->
                                    <NFormItem v-else label="到期时间">
                                        <NDatePicker v-model:formatted-value="editForm.subscription_expires_at" type="date" value-format="yyyy-MM-dd" clearable style="width: 100%" />
                                    </NFormItem>

                                    <!-- 子账号：角色继承父账号，只读 -->
                                    <NFormItem v-if="selectedUser.is_sub_account" label="角色">
                                        <NSelect :value="editForm.roles" :options="roleOptions" multiple disabled />
                                        <template #feedback>
                                            <span class="um-hint">继承自父账号，不可修改</span>
                                        </template>
                                    </NFormItem>
                                    <!-- 主账号：角色可编辑 -->
                                    <NFormItem v-else label="角色">
                                        <NSelect v-model:value="editForm.roles" :options="roleOptions" multiple placeholder="选择角色" />
                                    </NFormItem>

                                    <NFormItem v-if="!selectedUser.is_sub_account" label="子账号配额" :validation-status="editErrors.max_sub_accounts ? 'error' : undefined" :feedback="editErrors.max_sub_accounts">
                                        <NInputNumber v-model:value="editForm.max_sub_accounts" :min="0" :max="9999" placeholder="0 = 不可创建" style="width: 100%" :disabled="editProcessing" />
                                        <template #feedback>
                                            <span class="um-hint">已创建 {{ selectedUser.sub_accounts_count }} 个。需拥有"团队管理"权限。</span>
                                        </template>
                                    </NFormItem>

                                    <NFormItem label="单独权限">
                                        <NSelect v-model:value="editForm.direct_permissions" :options="editPermissionOptions" multiple :placeholder="selectedUser.is_sub_account ? '仅可选择父账号拥有的权限' : '在角色之外单独赋予权限'" clearable style="width: 100%" />
                                        <template #feedback>
                                            <span class="um-hint">{{ selectedUser.is_sub_account ? '仅可选择父账号拥有的权限。' : '与角色权限叠加生效。' }}</span>
                                        </template>
                                    </NFormItem>
                                </div>

                                <div class="um-form-actions">
                                    <NButton type="primary" attr-type="submit" :loading="editProcessing">
                                        <template #icon><NIcon :component="SaveOutline" /></template>
                                        保存更改
                                    </NButton>
                                </div>
                            </NForm>
                        </div>
                    </NScrollbar>
                </div>

                <!-- ── 创建面板 ── -->
                <div v-else-if="currentMode === 'create'" class="um-form-wrap">
                    <header class="um-form-head">
                        <div class="um-form-head__left">
                            <NAvatar round :size="42" class="um-form-head__avatar" :class="createForm.parent_id ? 'um-form-head__avatar--sub' : 'um-form-head__avatar--new'">
                                <NIcon :component="createForm.parent_id ? PersonAddOutline : AddOutline" :size="20" />
                            </NAvatar>
                            <div class="um-form-head__info">
                                <h3 class="um-form-head__name">{{ createForm.parent_id ? '新增子账号' : '新增用户' }}</h3>
                                <span class="um-form-head__email">{{ createForm.parent_id ? `父账号：${createParentEmail}` : '填写以下信息创建新用户' }}</span>
                            </div>
                        </div>
                    </header>

                    <NScrollbar class="um-form-scroll">
                        <div class="um-form-pad">
                            <NForm class="um-form" @submit.prevent="submitCreate">
                                <div class="um-section">
                                    <NFormItem label="用户名" :validation-status="createErrors.username ? 'error' : undefined" :feedback="createErrors.username">
                                        <NInput v-model:value="createForm.username" placeholder="请输入用户名" maxlength="50" show-count clearable :disabled="createProcessing" />
                                    </NFormItem>
                                    <NFormItem label="邮箱" :validation-status="createErrors.email ? 'error' : undefined" :feedback="createErrors.email">
                                        <NInput v-model:value="createForm.email" placeholder="请输入邮箱" clearable :disabled="createProcessing" />
                                    </NFormItem>
                                    <NFormItem label="密码" :validation-status="createErrors.password ? 'error' : undefined" :feedback="createErrors.password">
                                        <NInput v-model:value="createForm.password" type="password" placeholder="请输入密码" show-password-on="click" :disabled="createProcessing" />
                                    </NFormItem>
                                    <NFormItem label="确认密码" :validation-status="createErrors.password_confirmation ? 'error' : undefined" :feedback="createErrors.password_confirmation">
                                        <NInput v-model:value="createForm.password_confirmation" type="password" placeholder="再次输入密码" show-password-on="click" :disabled="createProcessing" />
                                    </NFormItem>

                                    <!-- 创建主账号：可选到期时间、角色、单独权限、子账号配额 -->
                                    <template v-if="!createForm.parent_id">
                                        <NFormItem label="到期时间">
                                            <NDatePicker v-model:formatted-value="createForm.subscription_expires_at" type="date" value-format="yyyy-MM-dd" clearable style="width: 100%" />
                                            <template #feedback><span class="um-hint">不填默认 7 天后到期</span></template>
                                        </NFormItem>
                                        <NFormItem label="角色">
                                            <NSelect v-model:value="createForm.roles" :options="roleOptions" multiple placeholder="不选默认 client" :disabled="createProcessing" style="width: 100%" />
                                        </NFormItem>
                                        <NFormItem label="子账号配额" :validation-status="createErrors.max_sub_accounts ? 'error' : undefined" :feedback="createErrors.max_sub_accounts">
                                            <NInputNumber v-model:value="createForm.max_sub_accounts" :min="0" :max="9999" placeholder="0 = 不可创建" style="width: 100%" :disabled="createProcessing" />
                                            <template #feedback>
                                                <span class="um-hint">需拥有"团队管理"权限才能使用子账号功能。</span>
                                            </template>
                                        </NFormItem>
                                        <NFormItem label="单独权限">
                                            <NSelect v-model:value="createForm.direct_permissions" :options="permissionOptions" multiple placeholder="在角色之外单独赋予权限" clearable :disabled="createProcessing" style="width: 100%" />
                                            <template #feedback>
                                                <span class="um-hint">与角色权限叠加生效。</span>
                                            </template>
                                        </NFormItem>
                                    </template>

                                    <!-- 创建子账号：权限选择（限父账号范围） -->
                                    <template v-else>
                                        <NFormItem v-if="createParentPermissions.length" label="权限">
                                            <NSelect v-model:value="createForm.direct_permissions" :options="createPermissionOptions" multiple placeholder="可选择父账号拥有的权限" clearable :disabled="createProcessing" style="width: 100%" />
                                            <template #feedback>
                                                <span class="um-hint">到期时间和角色将自动继承父账号。</span>
                                            </template>
                                        </NFormItem>
                                        <NFormItem v-else>
                                            <template #label><span></span></template>
                                            <NAlert type="info" style="width: 100%">
                                                到期时间和角色将自动继承父账号。父账号暂无可分配权限。
                                            </NAlert>
                                        </NFormItem>
                                    </template>
                                </div>

                                <div class="um-form-actions">
                                    <NButton type="primary" attr-type="submit" :loading="createProcessing">
                                        {{ createForm.parent_id ? '创建子账号' : '创建用户' }}
                                    </NButton>
                                    <NButton @click="isCreating = false" :disabled="createProcessing">取消</NButton>
                                </div>
                            </NForm>
                        </div>
                    </NScrollbar>
                </div>
            </main>

            <!-- ── 删除确认弹框 ── -->
            <StatusModal
                v-model:show="deleteModalShow"
                variant="danger"
                :icon="TrashOutline"
                title="确认删除用户"
                :content="`确定要删除用户「${pendingDeleteName}」吗？此操作不可撤销，该用户的所有数据将被永久移除。`"
                positive-text="确定删除"
                negative-text="取消"
                @confirm="confirmDelete"
            />
        </div>
    </AdminLayout>
</template>

<style scoped>
/* ========== Master-Detail 两栏布局 ========== */
.um {
    display: flex;
    height: calc(100vh - 60px - 56px);
    min-height: 520px;
    gap: 16px; /* 增加显著间距 */
    /* 移除外层统一背景和边框，改为布局容器 */
    background: transparent;
    border: none;
    box-shadow: none;
    overflow: hidden;
    position: relative;
}

.um::before {
    display: none;
}

.um > * {
    position: relative;
    z-index: 1;
    /* 让子元素各自拥有阴影和背景 */
    background: var(--admin-surface, #fff);
    border-radius: var(--admin-radius-lg, 16px);
    box-shadow: var(--admin-shadow-sm, 0 1px 2px rgba(0,0,0,.04), 0 4px 12px rgba(0,0,0,.02));
    border: 1px solid var(--admin-border, rgba(0,0,0,.05));
    overflow: hidden;
}

/* ========== 左栏：用户树 ========== */
.um-tree {
    width: 440px;
    min-width: 360px;
    display: flex;
    flex-direction: column;
    border-right: none;
    background: rgba(255, 255, 255, 0.8);
    backdrop-filter: saturate(180%) blur(12px);
}

.um-tree__head {
    padding: 16px 16px 12px;
    display: flex;
    flex-direction: column;
    gap: 12px;
    border-bottom: 1px solid rgba(0,0,0,.04);
    background: transparent;
}

.um-tree__meta {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 8px;
    margin-bottom: 4px;
}

.um-tree__meta-item {
    border: none;
    background: rgba(0, 0, 0, 0.03);
    border-radius: 8px;
    padding: 8px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2px;
    line-height: 1.2;
    transition: background 0.2s;
}

.um-tree__meta-item:hover {
    background: rgba(0, 0, 0, 0.05);
}

.um-tree__meta-item strong {
    font-size: 15px;
    font-weight: 700;
    letter-spacing: -0.02em;
    color: var(--admin-text, #1a1d21);
}

.um-tree__meta-label {
    font-size: 10px;
    font-weight: 600;
    text-transform: uppercase;
    color: var(--admin-text-muted, #888);
}

.um-tree__add-btn {
    width: 100%;
    font-weight: 600;
    border-radius: 8px;
}

.um-tree__body {
    flex: 1;
    overflow: hidden;
    background: transparent;
}

.um-tree__body :deep(.n-scrollbar-content) {
    padding: 12px 10px;
}

.um-tree__empty {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
    padding: 64px 16px;
    color: var(--admin-text-muted, #9ca3af);
    font-size: 13px;
}

.um-tree__foot {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    gap: 8px;
    padding: 12px 16px;
    border-top: 1px solid rgba(0,0,0,.04);
    background: transparent;
}

.um-tree__page-info {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
    margin-right: auto;
}

/* ── NTree 样式覆盖 ── */
.um-ntree :deep(.n-tree-node) {
    border-radius: 8px;
    margin-bottom: 4px;
    align-items: center;
}

.um-ntree :deep(.n-tree-node-content) {
    padding: 6px 10px !important;
    border-radius: 8px;
    min-height: 40px;
    align-items: center;
    border: 1px solid transparent;
}

.um-ntree :deep(.n-tree-node--selected > .n-tree-node-content) {
    background: var(--admin-primary-fade, rgba(99, 102, 241, 0.08)) !important;
    border-color: transparent;
    box-shadow: none;
    position: relative;
    overflow: hidden;
}

/* 左侧强调条 */
.um-ntree :deep(.n-tree-node--selected > .n-tree-node-content)::before {
    content: '';
    position: absolute;
    left: 0;
    top: 8px;
    bottom: 8px;
    width: 3px;
    background: var(--admin-primary, #4f46e5);
    border-radius: 0 3px 3px 0;
}

.um-ntree :deep(.n-tree-node-content:hover) {
    background: rgba(0,0,0,.025);
    border-color: transparent;
}

.um-ntree :deep(.n-tree-node--selected > .n-tree-node-content:hover) {
    background: var(--admin-primary-fade, rgba(99, 102, 241, 0.12)) !important;
}

/* 展开/收起图标居中 */
.um-ntree :deep(.n-tree-node-switcher) {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    align-self: center;
}
.um-ntree :deep(.n-tree-node-switcher .n-tree-node-switcher__icon),
.um-ntree :deep(.n-tree-node-switcher .n-base-icon) {
    display: flex;
    align-items: center;
    justify-content: center;
}
.um-ntree :deep(.n-tree-node-switcher svg) {
    width: 14px;
    height: 14px;
    display: block;
}

/* ── Tree 节点内容 ── */
.tree-avatar {
    font-size: 11px !important;
    font-weight: 600;
    color: #fff !important;
    flex-shrink: 0;
}
.tree-avatar--parent {
    background: linear-gradient(145deg, var(--admin-accent, #0d9488), var(--admin-accent-hover, #0f766e)) !important;
}
.tree-avatar--sub {
    background: linear-gradient(145deg, #6366f1, #4f46e5) !important;
}

.tree-label {
    font-size: 13px;
    font-weight: 500;
    color: var(--admin-text, #111827);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    min-width: 0;
    line-height: 1.4;
}

.tree-suffix {
    display: flex;
    gap: 4px;
    align-items: center;
    flex-shrink: 0;
}

/* 树节点操作按钮：默认隐藏，hover 或选中时显示 */
.tree-suffix :deep(.tree-action-btn),
.tree-suffix :deep(.tree-delete-btn) {
    opacity: 0;
    transition: opacity 0.15s, transform 0.15s;
    pointer-events: none;
}

/* 鼠标悬停在节点行上时显示操作按钮 */
.um-ntree :deep(.n-tree-node-content:hover .tree-action-btn),
.um-ntree :deep(.n-tree-node-content:hover .tree-delete-btn) {
    opacity: 0.45;
    pointer-events: auto;
}

/* 选中节点始终显示操作按钮 */
.um-ntree :deep(.n-tree-node--selected .tree-action-btn),
.um-ntree :deep(.n-tree-node--selected .tree-delete-btn) {
    opacity: 0.6;
    pointer-events: auto;
}

/* 操作按钮自身 hover 高亮 */
.um-ntree :deep(.tree-action-btn:hover),
.um-ntree :deep(.tree-delete-btn:hover) {
    opacity: 1 !important;
    transform: scale(1.15);
}

/* ========== 右栏：详情面板 ========== */
.um-detail {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    background: #fff;
    /* 右侧卡片可以稍微突出一点，或者保持一致 */
}

/* ── 空闲状态 ── */
.um-idle {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 16px;
    user-select: none;
    background: radial-gradient(circle at center, rgba(243, 244, 246, 0.5) 0%, transparent 70%);
}

.um-idle__graphic {
    width: 100px;
    height: 100px;
    border-radius: 28px;
    background: #f3f4f6; /* Gray 100 */
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 0;
    box-shadow: inset 0 2px 4px rgba(0,0,0,.03);
}

.um-idle__ring {
    display: none; /* 移除复杂的动画 */
}

.um-idle__icon {
    color: #9ca3af; /* Gray 400 */
    opacity: 1;
}

.um-idle__title {
    font-size: 16px;
    font-weight: 600;
    color: #374151;
}

/* ── 编辑/创建面板 ── */
.um-form-wrap {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    background: #fff;
    animation: um-fade-in 0.3s cubic-bezier(0.2, 0.8, 0.2, 1);
}

@keyframes um-fade-in {
    from { opacity: 0; transform: translateY(8px); } /* 稍微改从下方浮现 */
    to   { opacity: 1; transform: translateY(0); }
}

.um-form-head {
    padding: 24px 32px;
    border-bottom: 1px solid rgba(0,0,0,.05);
    background: linear-gradient(180deg, #fff 0%, #fafbfc 100%);
    display: flex;
    align-items: center;
    flex-shrink: 0;
}

.um-form-head__left {
    display: flex;
    align-items: center;
    gap: 14px;
}

.um-form-head__avatar {
    font-size: 18px;
    box-shadow: 0 2px 8px -2px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.06);
}

.um-form-head__avatar--new {
    background: linear-gradient(145deg, #f59e0b, #d97706) !important;
}

.um-form-head__avatar--sub {
    background: linear-gradient(145deg, #6366f1, #4f46e5) !important;
}

.um-form-head__info {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.um-form-head__name {
    font-size: 16px;
    font-weight: 700;
    color: var(--admin-text, #1a1d21);
    margin: 0;
    line-height: 1.3;
    letter-spacing: -0.01em;
}

.um-form-head__email {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
    line-height: 1.3;
}

.um-global-error {
    margin: 16px 32px 0;
    border-radius: 8px;
}

.um-sub-alert {
    margin: 16px 32px 0;
    border-radius: 8px;
    border: 1px solid rgba(59, 130, 246, 0.1);
    background: rgba(59, 130, 246, 0.04);
}

.um-form-scroll {
    flex: 1;
    overflow: hidden;
}

.um-form-pad {
    padding: 8px 32px 40px;
}

.um-form {
    max-width: 560px;
}

.um-form :deep(.n-form-item-label__text) {
    font-weight: 600;
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
    letter-spacing: 0.03em;
}

.um-form :deep(.n-input .n-input__input-el),
.um-form :deep(.n-base-selection .n-base-selection-input__content),
.um-form :deep(.n-input-number .n-input__input-el) {
    font-size: 13px;
}

.um-form :deep(.n-input),
.um-form :deep(.n-base-selection),
.um-form :deep(.n-input-number) {
    border-radius: 10px;
}

.um-section {
    padding-top: 20px;
}

.um-section:first-child {
    padding-top: 16px;
}

.um-section__title {
    font-size: 11px;
    font-weight: 700;
    color: #9ca3af;
    text-transform: uppercase;
    letter-spacing: 0.1em;
    margin: 0 0 16px;
    display: flex;
    align-items: center;
    gap: 10px;
}

/* 标题后加个装饰线 */
.um-section__title::after {
    content: '';
    flex: 1;
    height: 1px;
    background: linear-gradient(90deg, rgba(0,0,0,.06), transparent);
}

.um-hint {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
}

.um-form-actions {
    display: flex;
    gap: 10px;
    padding-top: 8px;
}

/* ========== 响应式 ========== */
@media (max-width: 900px) {
    .um {
        flex-direction: column;
        height: auto;
        gap: 12px;
        background: transparent;
        box-shadow: none;
        border: none;
    }
    .um-tree {
        width: 100%;
        max-height: 400px;
        border-right: none;
        box-shadow: var(--admin-shadow-sm, 0 1px 2px rgba(0,0,0,.04), 0 4px 12px rgba(0,0,0,.02));
    }
    .um-detail {
        min-height: 400px;
        box-shadow: var(--admin-shadow-sm, 0 1px 2px rgba(0,0,0,.04), 0 4px 12px rgba(0,0,0,.02));
    }
}
</style>
