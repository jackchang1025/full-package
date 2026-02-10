<script setup lang="ts">
import { h, ref, computed } from 'vue';
import { Head, router, usePage } from '@inertiajs/vue3';
import { NDataTable, NButton, NIcon, NTag, NPopconfirm } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { AddOutline, PencilOutline, TrashOutline, AlertCircleOutline } from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import StatusModal from '@/Components/StatusModal.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface SubAccountRow {
    id: number;
    username: string;
    email: string;
    permissions: string[];
    created_at: string;
}

interface Paginated {
    data: SubAccountRow[];
    current_page: number;
    last_page: number;
    total: number;
}

const props = defineProps<{
    subAccounts: Paginated;
    maxSubAccounts: number;
    canCreate: boolean;
}>();

const { userRoute } = useAdminBasePath();
const page = usePage();
const permLabels = computed(() => (page.props as any).permissionLabels ?? {});

const quotaModalVisible = ref(false);

const handleCreateClick = () => {
    if (props.canCreate) {
        router.visit(userRoute('/sub-accounts/create'));
        return;
    }
    quotaModalVisible.value = true;
};

const deleteSubAccount = (id: number) => {
    router.delete(userRoute(`/sub-accounts/${id}`));
};

const columns: DataTableColumns<SubAccountRow> = [
    { title: 'ID', key: 'id', width: 70 },
    { title: '用户名', key: 'username', width: 140 },
    { title: '邮箱', key: 'email', width: 220 },
    {
        title: '权限',
        key: 'permissions',
        render: (row) =>
            row.permissions?.length
                ? row.permissions.map((p) =>
                    h(NTag, { size: 'small', type: 'info', style: 'margin: 2px' }, () => permLabels.value[p] ?? p)
                )
                : h('span', { style: 'color: #999' }, '无权限'),
    },
    {
        title: '操作',
        key: 'actions',
        width: 160,
        render: (row) =>
            h('div', { class: 'actions-cell' }, [
                h(NButton, {
                    size: 'small',
                    onClick: () => router.visit(userRoute(`/sub-accounts/${row.id}/edit`)),
                }, { default: () => '编辑' }),
                h(NPopconfirm, {
                    positiveButtonProps: { type: 'error' },
                    onPositiveClick: () => deleteSubAccount(row.id),
                }, {
                    trigger: () =>
                        h(NButton, { size: 'small', type: 'error', quaternary: true }, {
                            icon: () => h(NIcon, { component: TrashOutline }),
                            default: () => '删除',
                        }),
                    default: () => '确定要删除该子账号吗？删除后该账号将无法登录。',
                }),
            ]),
    },
];
</script>

<template>
    <Head title="子账号管理" />
    <AuthenticatedLayout>
        <template #header-title>子账号管理</template>
        <div class="page-card">
            <div class="toolbar">
                <div class="toolbar-info">
                    已创建 <strong>{{ subAccounts.total }}</strong> / {{ maxSubAccounts }} 个子账号
                </div>
                <NButton
                    type="primary"
                    @click="handleCreateClick"
                >
                    <template #icon>
                        <NIcon :component="AddOutline" />
                    </template>
                    新增子账号
                </NButton>
            </div>
            <NDataTable
                :columns="columns"
                :data="subAccounts.data"
                :bordered="false"
                :single-line="false"
                size="small"
                class="data-table"
            />
            <div v-if="subAccounts.last_page > 1" class="pagination">
                <NButton
                    :disabled="subAccounts.current_page <= 1"
                    @click="router.get(userRoute('/sub-accounts'), { page: subAccounts.current_page - 1 })"
                >
                    上一页
                </NButton>
                <span class="page-info">{{ subAccounts.current_page }} / {{ subAccounts.last_page }}</span>
                <NButton
                    :disabled="subAccounts.current_page >= subAccounts.last_page"
                    @click="router.get(userRoute('/sub-accounts'), { page: subAccounts.current_page + 1 })"
                >
                    下一页
                </NButton>
            </div>
        </div>

        <StatusModal
            v-model:show="quotaModalVisible"
            variant="amber"
            :icon="AlertCircleOutline"
            title="子账号配额已满"
            :content="`当前已创建 ${subAccounts.total} / ${maxSubAccounts} 个子账号，已达上限。如需更多名额请联系管理员。`"
        />
    </AuthenticatedLayout>
</template>

<style scoped>
.page-card {
    background: var(--user-surface, #fff);
    border-radius: var(--user-radius-lg, 16px);
    padding: 28px;
    border: 1px solid var(--user-border, #e2e8f0);
    box-shadow: var(--user-shadow, 0 1px 3px rgba(0,0,0,.05));
}

.toolbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 16px;
    margin-bottom: 24px;
}

.toolbar-info {
    font-size: 14px;
    color: var(--user-text-muted, #64748b);
}

.actions-cell {
    display: flex;
    align-items: center;
    gap: 8px;
}

.data-table {
    margin-bottom: 20px;
}

.data-table :deep(.n-data-table-th) {
    font-weight: 600;
    color: var(--user-text-muted, #64748b);
    font-size: 12px;
    text-transform: uppercase;
    letter-spacing: 0.04em;
}

.data-table :deep(.n-data-table-tr:hover .n-data-table-td) {
    background: rgba(16, 185, 129, 0.06);
}

.pagination {
    display: flex;
    align-items: center;
    gap: 16px;
}

.page-info {
    font-size: 14px;
    color: var(--user-text-muted, #64748b);
}
</style>
