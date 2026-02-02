<script setup lang="ts">
import { ref, computed, h } from 'vue';
import {
    NDataTable,
    NButton,
    NSpace,
    NEmpty,
    NSpin,
    NInput,
    NImage,
    NPopconfirm,
} from 'naive-ui';
import type { AppInfo } from '@/types/device';

interface Props {
    apps: AppInfo[];
    loading: boolean;
}

interface Emits {
    (e: 'refresh'): void;
    (e: 'open', packageName: string): void;
    (e: 'uninstall', packageName: string): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const searchQuery = ref('');

const filteredApps = computed(() => {
    if (!searchQuery.value.trim()) return props.apps;
    const query = searchQuery.value.toLowerCase();
    return props.apps.filter(
        app =>
            app.name.toLowerCase().includes(query) ||
            app.packageName.toLowerCase().includes(query)
    );
});

const columns = [
    {
        title: '图标',
        key: 'icon',
        width: 60,
        render: (row: AppInfo) => {
            if (!row.icon) return null;
            const src = row.icon.startsWith('data:')
                ? row.icon
                : `data:image/png;base64,${row.icon}`;
            return h(NImage, {
                src,
                width: 32,
                height: 32,
                objectFit: 'contain',
                previewDisabled: true,
            });
        },
    },
    { title: '应用名称', key: 'name' },
    {
        title: '包名',
        key: 'packageName',
        ellipsis: { tooltip: true },
    },
    {
        title: '操作',
        key: 'actions',
        width: 140,
        render: (row: AppInfo) => {
            return h(NSpace, { size: 'small' }, () => [
                h(
                    NButton,
                    {
                        size: 'tiny',
                        type: 'primary',
                        onClick: () => emit('open', row.packageName),
                    },
                    () => '打开'
                ),
                h(
                    NPopconfirm,
                    {
                        onPositiveClick: () => emit('uninstall', row.packageName),
                    },
                    {
                        trigger: () =>
                            h(NButton, { size: 'tiny', type: 'error' }, () => '卸载'),
                        default: () => `确定卸载 ${row.name}？`,
                    }
                ),
            ]);
        },
    },
];
</script>

<template>
    <div class="apps-tab">
        <div class="tab-header">
            <NInput
                v-model:value="searchQuery"
                placeholder="搜索应用..."
                size="small"
                clearable
                style="width: 200px"
            />
            <NButton size="small" @click="emit('refresh')">
                刷新
            </NButton>
        </div>

        <NSpin :show="loading">
            <NDataTable
                v-if="filteredApps.length > 0"
                :columns="columns"
                :data="filteredApps"
                :bordered="false"
                :max-height="400"
                size="small"
            />
            <NEmpty v-else description="暂无应用" />
        </NSpin>
    </div>
</template>

<style scoped>
.apps-tab {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.tab-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}
</style>
