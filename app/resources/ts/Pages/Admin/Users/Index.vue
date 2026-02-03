<script setup lang="ts">
import { h } from 'vue';
import { Head, router, useForm } from '@inertiajs/vue3';
import { NDataTable, NButton, NInput, NInputGroup, NPopconfirm } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { NIcon } from 'naive-ui';
import { PencilOutline, AddOutline, TrashOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';

interface UserRow {
    id: number;
    username: string;
    email: string;
    subscription_expires_at: string | null;
    subscription_type: string | null;
    roles: string[];
    created_at: string;
}

interface Paginated {
    data: UserRow[];
    current_page: number;
    last_page: number;
    per_page: number;
    total: number;
}

const props = defineProps<{
    users: Paginated;
    roles: string[];
    roleLabels?: Record<string, string>;
    filters?: { search?: string };
}>();

const searchForm = useForm({ search: props.filters?.search ?? '' });
const submitSearch = () => searchForm.get('/admin/users', { preserveState: true, data: { search: searchForm.search } });

const roleLabels = () => props.roleLabels ?? {};
const deleteUser = (id: number) => router.delete(`/admin/users/${id}`);

const columns: DataTableColumns<UserRow> = [
    { title: 'ID', key: 'id', width: 70 },
    { title: '用户名', key: 'username', width: 120 },
    { title: '邮箱', key: 'email', width: 200 },
    {
        title: '角色',
        key: 'roles',
        width: 120,
        render: (row) =>
            row.roles?.length
                ? row.roles.map((r) => roleLabels()[r] ?? r).join(', ')
                : '-',
    },
    { title: '到期时间', key: 'subscription_expires_at', width: 120 },
    {
        title: '操作',
        key: 'actions',
        width: 160,
        render: (row) =>
            h('div', { class: 'actions-cell' }, [
                h(NButton, {
                    size: 'small',
                    onClick: () => router.visit(`/admin/users/${row.id}/edit`),
                }, { default: () => '编辑' }),
                h(NPopconfirm, {
                    positiveButtonProps: { type: 'error' },
                    onPositiveClick: () => deleteUser(row.id),
                }, {
                    trigger: () =>
                        h(NButton, { size: 'small', type: 'error', quaternary: true }, {
                            icon: () => h(NIcon, { component: TrashOutline }),
                            default: () => '删除',
                        }),
                    default: () => '确定要删除该用户吗？删除后该用户将无法登录。',
                }),
            ]),
    },
];
</script>

<template>
    <Head title="用户管理" />
    <AdminLayout>
        <template #header-title>用户管理</template>
        <div class="admin-page-card">
            <div class="admin-toolbar">
                <NInputGroup class="admin-search-group">
                    <NInput
                        v-model:value="searchForm.search"
                        placeholder="搜索用户名/邮箱"
                        clearable
                        @keyup.enter="submitSearch"
                    />
                    <NButton type="primary" @click="submitSearch">搜索</NButton>
                </NInputGroup>
                <NButton type="primary" @click="router.visit('/admin/users/create')">
                    <template #icon>
                        <NIcon :component="AddOutline" />
                    </template>
                    新增用户
                </NButton>
            </div>
            <NDataTable
                :columns="columns"
                :data="users.data"
                :bordered="false"
                :single-line="false"
                size="small"
                class="admin-table"
            />
            <div v-if="users.last_page > 1" class="admin-pagination">
                <NButton
                    :disabled="users.current_page <= 1"
                    @click="router.get('/admin/users', { page: users.current_page - 1 })"
                >
                    上一页
                </NButton>
                <span class="page-info">{{ users.current_page }} / {{ users.last_page }}</span>
                <NButton
                    :disabled="users.current_page >= users.last_page"
                    @click="router.get('/admin/users', { page: users.current_page + 1 })"
                >
                    下一页
                </NButton>
            </div>
        </div>
    </AdminLayout>
</template>

<style scoped>
.admin-page-card {
    background: var(--admin-surface, #fff);
    border-radius: var(--admin-radius-lg, 16px);
    padding: 28px;
    border: 1px solid var(--admin-border, rgba(0,0,0,.06));
    box-shadow: var(--admin-shadow, 0 1px 3px rgba(0,0,0,.05));
}

.admin-toolbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 16px;
    margin-bottom: 24px;
}

.actions-cell {
    display: flex;
    align-items: center;
    gap: 8px;
}

.admin-search-group {
    max-width: 340px;
}

.admin-table {
    margin-bottom: 20px;
}

.admin-table :deep(.n-data-table-th) {
    font-weight: 600;
    color: var(--admin-text-muted, #6b7280);
    font-size: 12px;
    text-transform: uppercase;
    letter-spacing: 0.04em;
}

.admin-table :deep(.n-data-table-td) {
    transition: background 0.15s;
}

.admin-table :deep(.n-data-table-tr:hover .n-data-table-td) {
    background: var(--admin-accent-muted, rgba(13,148,136,.06));
}

.admin-pagination {
    display: flex;
    align-items: center;
    gap: 16px;
}

.page-info {
    font-size: 14px;
    color: var(--admin-text-muted, #6b7280);
}
</style>
