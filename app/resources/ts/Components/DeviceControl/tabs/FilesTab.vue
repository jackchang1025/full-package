<script setup lang="ts">
import { ref, computed, h } from 'vue';
import {
    NDataTable,
    NButton,
    NSpace,
    NEmpty,
    NSpin,
    NBreadcrumb,
    NBreadcrumbItem,
    NIcon,
    NPopconfirm,
} from 'naive-ui';
import {
    FolderOutline,
    DocumentOutline,
    DownloadOutline,
    TrashOutline,
} from '@vicons/ionicons5';
import type { FileItem } from '@/types/device';

interface Props {
    files: FileItem[];
    loading: boolean;
    currentPath: string;
}

interface Emits {
    (e: 'navigate', path: string): void;
    (e: 'download', path: string): void;
    (e: 'delete', path: string): void;
    (e: 'refresh'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const pathParts = computed(() => {
    const parts = props.currentPath.split('/').filter(Boolean);
    return parts.map((part, index) => ({
        name: part,
        path: '/' + parts.slice(0, index + 1).join('/'),
    }));
});

const columns = [
    {
        title: '名称',
        key: 'name',
        render: (row: FileItem) => {
            return h('div', { class: 'file-name' }, [
                h(NIcon, {
                    component: row.isDirectory ? FolderOutline : DocumentOutline,
                    class: 'file-icon',
                }),
                h(
                    'span',
                    {
                        class: row.isDirectory ? 'clickable' : '',
                        onClick: () => {
                            if (row.isDirectory) {
                                emit('navigate', row.path);
                            }
                        },
                    },
                    row.name
                ),
            ]);
        },
    },
    { title: '大小', key: 'size', width: 100 },
    { title: '修改时间', key: 'lastModified', width: 160 },
    {
        title: '操作',
        key: 'actions',
        width: 120,
        render: (row: FileItem) => {
            if (row.isDirectory) return null;
            return h(NSpace, { size: 'small' }, () => [
                h(
                    NButton,
                    {
                        size: 'tiny',
                        quaternary: true,
                        onClick: () => emit('download', row.path),
                    },
                    {
                        icon: () => h(NIcon, { component: DownloadOutline }),
                    }
                ),
                h(
                    NPopconfirm,
                    {
                        onPositiveClick: () => emit('delete', row.path),
                    },
                    {
                        trigger: () =>
                            h(
                                NButton,
                                { size: 'tiny', quaternary: true, type: 'error' },
                                { icon: () => h(NIcon, { component: TrashOutline }) }
                            ),
                        default: () => '确定删除此文件？',
                    }
                ),
            ]);
        },
    },
];

const handleNavigateToRoot = () => {
    emit('navigate', '/sdcard');
};
</script>

<template>
    <div class="files-tab">
        <div class="tab-header">
            <NBreadcrumb>
                <NBreadcrumbItem @click="handleNavigateToRoot">
                    根目录
                </NBreadcrumbItem>
                <NBreadcrumbItem
                    v-for="part in pathParts"
                    :key="part.path"
                    @click="emit('navigate', part.path)"
                >
                    {{ part.name }}
                </NBreadcrumbItem>
            </NBreadcrumb>
            <NButton size="small" @click="emit('refresh')">
                刷新
            </NButton>
        </div>

        <NSpin :show="loading">
            <NDataTable
                v-if="files.length > 0"
                :columns="columns"
                :data="files"
                :bordered="false"
                :max-height="400"
                size="small"
            />
            <NEmpty v-else description="文件夹为空" />
        </NSpin>
    </div>
</template>

<style scoped>
.files-tab {
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

:deep(.file-name) {
    display: flex;
    align-items: center;
    gap: 8px;
}

:deep(.file-icon) {
    font-size: 16px;
}

:deep(.clickable) {
    cursor: pointer;
    color: var(--n-text-color);
}

:deep(.clickable:hover) {
    color: var(--n-primary-color);
}
</style>
