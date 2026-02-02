<script setup lang="ts">
import {
    NDataTable,
    NButton,
    NEmpty,
    NSpin,
    NDatePicker,
    NSpace,
    NIcon,
} from 'naive-ui';
import { ref } from 'vue';
import { PlayOutline, StopOutline } from '@vicons/ionicons5';
import type { KeylogEntry } from '@/types/device';

interface Props {
    entries: KeylogEntry[];
    loading: boolean;
    isMonitoring: boolean;
}

interface Emits {
    (e: 'refresh'): void;
    (e: 'fetchByDate', date: string): void;
    (e: 'toggleMonitor'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const selectedDate = ref<number | null>(null);

const columns = [
    { title: '时间', key: 'time', width: 160 },
    { title: '应用', key: 'app', width: 150 },
    {
        title: '内容',
        key: 'action',
        ellipsis: { tooltip: true },
    },
    { title: '状态', key: 'status', width: 100 },
];

const handleDateChange = (value: number | null) => {
    if (value) {
        const date = new Date(value);
        const formatted = date.toISOString().split('T')[0];
        emit('fetchByDate', formatted);
    }
};
</script>

<template>
    <div class="keylog-tab">
        <div class="tab-header">
            <NSpace>
                <NButton
                    :type="isMonitoring ? 'warning' : 'primary'"
                    size="small"
                    @click="emit('toggleMonitor')"
                >
                    <template #icon>
                        <NIcon :component="isMonitoring ? StopOutline : PlayOutline" />
                    </template>
                    {{ isMonitoring ? '关闭监听' : '开启监听' }}
                </NButton>
                <NDatePicker
                    v-model:value="selectedDate"
                    type="date"
                    size="small"
                    placeholder="选择日期"
                    clearable
                    @update:value="handleDateChange"
                />
            </NSpace>
            <NButton size="small" @click="emit('refresh')">
                刷新
            </NButton>
        </div>

        <NSpin :show="loading">
            <NDataTable
                v-if="entries.length > 0"
                :columns="columns"
                :data="entries"
                :bordered="false"
                :max-height="400"
                size="small"
            />
            <NEmpty v-else description="暂无键盘记录" />
        </NSpin>
    </div>
</template>

<style scoped>
.keylog-tab {
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
