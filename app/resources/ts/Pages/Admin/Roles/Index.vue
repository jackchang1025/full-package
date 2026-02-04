<script setup lang="ts">
import { h, computed } from 'vue';
import { Head, router } from '@inertiajs/vue3';
import { NDataTable, NButton, NTag, NSpace } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { AddOutline } from '@vicons/ionicons5';
import { NIcon } from 'naive-ui';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface RoleRow {
    id: number;
    name: string;
    permissions: string[];
    users_count: number;
}

interface Paginated {
    data: RoleRow[];
    current_page: number;
    last_page: number;
    per_page: number;
    total: number;
}

const props = defineProps<{
    roles: Paginated;
    allPermissions?: string[];
    permissionLabels?: Record<string, string>;
}>();

const { adminRoute } = useAdminBasePath();

const permissionLabelsMap = computed(() => props.permissionLabels ?? {});

const columns: DataTableColumns<RoleRow> = [
    { title: 'ID', key: 'id', width: 70 },
    { title: '角色名', key: 'name', width: 140 },
    {
        title: '权限',
        key: 'permissions',
        ellipsis: { tooltip: true },
        render: (row) =>
            row.permissions?.length
                ? h(NSpace, { size: 4, wrap: true }, () =>
                    row.permissions.map((p) =>
                        h(NTag, { size: 'small', type: 'info' }, () => permissionLabelsMap.value[p] ?? p)
                    )
                )
                : '-',
    },
    { title: '用户数', key: 'users_count', width: 90 },
    {
        title: '操作',
        key: 'actions',
        width: 160,
        render: (row) =>
            h(NSpace, { size: 8 }, () => [
                h(
                    NButton,
                    {
                        size: 'small',
                        onClick: () => router.visit(adminRoute(`/roles/${row.id}/edit`)),
                    },
                    { default: () => '编辑' }
                ),
                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'error',
                        tertiary: true,
                        disabled: row.users_count > 0,
                        onClick: () => {
                            if (row.users_count > 0) return;
                            if (confirm('确定删除该角色？')) {
                                router.delete(adminRoute(`/roles/${row.id}`), {
                                    preserveScroll: true,
                                });
                            }
                        },
                    },
                    { default: () => '删除' }
                ),
            ]),
    },
];
</script>

<template>
    <Head title="角色与权限" />
    <AdminLayout>
        <template #header-title>角色与权限</template>
        <div class="admin-page-card">
            <div class="admin-toolbar">
                <NButton type="primary" @click="router.visit(adminRoute('/roles/create'))">
                    <template #icon>
                        <NIcon :component="AddOutline" />
                    </template>
                    新建角色
                </NButton>
            </div>
            <NDataTable
                :columns="columns"
                :data="roles.data"
                :bordered="false"
                :single-line="false"
                size="small"
                class="admin-table"
            />
            <div v-if="roles.last_page > 1" class="admin-pagination">
                <NButton
                    :disabled="roles.current_page <= 1"
                    @click="router.get(adminRoute('/roles'), { page: roles.current_page - 1 })"
                >
                    上一页
                </NButton>
                <span class="page-info">{{ roles.current_page }} / {{ roles.last_page }}</span>
                <NButton
                    :disabled="roles.current_page >= roles.last_page"
                    @click="router.get(adminRoute('/roles'), { page: roles.current_page + 1 })"
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
    margin-bottom: 24px;
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
