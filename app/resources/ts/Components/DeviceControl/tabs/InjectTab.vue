<script setup lang="ts">
import { ref } from 'vue';
import {
    NCard,
    NButton,
    NEmpty,
    NSpin,
    NList,
    NListItem,
    NTag,
    NIcon,
    NInput,
    NSpace,
} from 'naive-ui';
import {
    RefreshOutline,
    SearchOutline,
    TimeOutline,
    CheckmarkCircleOutline,
    CloseCircleOutline,
} from '@vicons/ionicons5';

interface InjectRecord {
    id: string;
    app_name: string;
    package_name: string;
    inject_type: string;
    status: 'success' | 'failed' | 'pending';
    data?: string;
    created_at: string;
}

interface Props {
    records: InjectRecord[];
    loading?: boolean;
}

interface Emits {
    (e: 'refresh'): void;
}

const props = withDefaults(defineProps<Props>(), {
    loading: false,
});

const emit = defineEmits<Emits>();

const searchKeyword = ref('');

const filteredRecords = () => {
    if (!searchKeyword.value) return props.records;
    const keyword = searchKeyword.value.toLowerCase();
    return props.records.filter(
        (r) =>
            r.app_name.toLowerCase().includes(keyword) ||
            r.package_name.toLowerCase().includes(keyword) ||
            r.inject_type.toLowerCase().includes(keyword)
    );
};

const getStatusType = (status: string) => {
    switch (status) {
        case 'success':
            return 'success';
        case 'failed':
            return 'error';
        default:
            return 'warning';
    }
};

const getStatusText = (status: string) => {
    switch (status) {
        case 'success':
            return '成功';
        case 'failed':
            return '失败';
        default:
            return '等待中';
    }
};
</script>

<template>
    <div class="inject-tab">
        <div class="tab-header">
            <NSpace>
                <NInput
                    v-model:value="searchKeyword"
                    placeholder="搜索应用名或包名..."
                    size="small"
                    clearable
                    style="width: 200px"
                >
                    <template #prefix>
                        <NIcon :component="SearchOutline" />
                    </template>
                </NInput>
            </NSpace>
            <NButton size="small" type="primary" :loading="loading" @click="emit('refresh')">
                <template #icon>
                    <NIcon :component="RefreshOutline" />
                </template>
                刷新
            </NButton>
        </div>

        <NSpin :show="loading">
            <div v-if="filteredRecords().length > 0" class="records-list">
                <div
                    v-for="record in filteredRecords()"
                    :key="record.id"
                    class="record-item"
                >
                    <div class="record-header">
                        <div class="record-app">
                            <span class="app-name">{{ record.app_name }}</span>
                            <span class="package-name">{{ record.package_name }}</span>
                        </div>
                        <NTag :type="getStatusType(record.status)" size="small">
                            <template #icon>
                                <NIcon
                                    :component="
                                        record.status === 'success'
                                            ? CheckmarkCircleOutline
                                            : record.status === 'failed'
                                            ? CloseCircleOutline
                                            : TimeOutline
                                    "
                                />
                            </template>
                            {{ getStatusText(record.status) }}
                        </NTag>
                    </div>
                    <div class="record-info">
                        <span class="inject-type">{{ record.inject_type }}</span>
                        <span class="record-time">
                            <NIcon :component="TimeOutline" size="12" />
                            {{ record.created_at }}
                        </span>
                    </div>
                    <div v-if="record.data" class="record-data">
                        {{ record.data }}
                    </div>
                </div>
            </div>
            <NEmpty v-else description="暂无注入记录" />
        </NSpin>
    </div>
</template>

<style scoped>
.inject-tab {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.tab-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.records-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
    max-height: 400px;
    overflow-y: auto;
}

.record-item {
    background: #f8fafc;
    border-radius: 8px;
    padding: 12px;
    border: 1px solid #e2e8f0;
}

.record-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 8px;
}

.record-app {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.app-name {
    font-weight: 600;
    font-size: 14px;
    color: #1e293b;
}

.package-name {
    font-size: 11px;
    color: #64748b;
}

.record-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
}

.inject-type {
    color: #10B981;
    font-weight: 500;
}

.record-time {
    display: flex;
    align-items: center;
    gap: 4px;
    color: #94a3b8;
}

.record-data {
    margin-top: 8px;
    padding: 8px;
    background: #fff;
    border-radius: 4px;
    font-size: 12px;
    color: #10B981;
    word-break: break-all;
    border: 1px solid #e2e8f0;
}
</style>
