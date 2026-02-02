<script setup lang="ts">
import { computed, ref, onMounted, onUnmounted } from 'vue';
import { Head, router } from '@inertiajs/vue3';
import {
    NDataTable,
    NButton,
    NSpace,
    NTag,
    NPopconfirm,
    NIcon,
    NInput,
    NSelect,
    NPagination,
} from 'naive-ui';
import { h } from 'vue';
import {
    SearchOutline,
    RefreshOutline,
    EyeOutline,
    TrashOutline,
    PhonePortraitOutline,
    LocationOutline,
    WifiOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';
import type { DeviceOnlineMessage, DeviceOfflineMessage } from '@/types/websocket';

// Props from server
interface Device {
    id: number;
    uuid: string;
    name: string;
    model: string;
    android_version: string;
    country: string;
    battery_level: number | null;
    is_online: boolean;
    has_accessibility: boolean;
    last_seen_at: string | null;
    installed_at: string | null;
}

interface PaginatedDevices {
    data: Device[];
    current_page: number;
    last_page: number;
    per_page: number;
    total: number;
}

interface Stats {
    total: number;
    online: number;
    offline: number;
}

const props = defineProps<{
    devices: PaginatedDevices;
    stats: Stats;
}>();

// WebSocket for real-time updates
const { connectionState, stats: wsStats, onMessage } = useGlobalWebSocket();

// Local reactive copy of devices for real-time updates
const localDevices = ref<Device[]>([...props.devices.data]);
const localStats = ref<Stats>({ ...props.stats });

// Search and filter
const searchQuery = ref('');
const statusFilter = ref<string>('all');

const statusOptions = [
    { label: '全部状态', value: 'all' },
    { label: '在线', value: 'online' },
    { label: '离线', value: 'offline' },
];

// Filter devices
const filteredDevices = computed(() => {
    let result = localDevices.value;
    
    if (searchQuery.value) {
        const query = searchQuery.value.toLowerCase();
        result = result.filter(d => 
            (d.name || '').toLowerCase().includes(query) ||
            (d.model || '').toLowerCase().includes(query) ||
            (d.uuid || '').toLowerCase().includes(query)
        );
    }
    
    if (statusFilter.value === 'online') {
        result = result.filter(d => d.is_online);
    } else if (statusFilter.value === 'offline') {
        result = result.filter(d => !d.is_online);
    }
    // 'all' - no filter
    
    return result;
});

// Connection status
const isConnected = computed(() => connectionState.value === 'connected');
const isConnecting = computed(() => connectionState.value === 'connecting' || connectionState.value === 'reconnecting');

// Handle WebSocket messages
let unsubscribe: (() => void) | null = null;

onMounted(() => {
    unsubscribe = onMessage((msg) => {
        const msgType = (msg as { type?: string }).type;
        
        if (msgType === 'deviceOnline') {
            const data = msg as DeviceOnlineMessage;
            handleDeviceOnline(data.pid, data.deviceInfo);
            if (data.stats) {
                localStats.value = data.stats;
            }
        } else if (msgType === 'deviceOffline') {
            const data = msg as DeviceOfflineMessage;
            handleDeviceOffline(data.pid);
            if (data.stats) {
                localStats.value = data.stats;
            }
        }
    });
});

onUnmounted(() => {
    if (unsubscribe) {
        unsubscribe();
    }
});

const handleDeviceOnline = (pid: string, deviceInfo: any) => {
    const index = localDevices.value.findIndex(d => d.uuid === pid);
    if (index >= 0) {
        localDevices.value[index] = {
            ...localDevices.value[index],
            is_online: true,
            name: deviceInfo?.phone_name || localDevices.value[index].name,
        };
    }
};

const handleDeviceOffline = (pid: string) => {
    const index = localDevices.value.findIndex(d => d.uuid === pid);
    if (index >= 0) {
        localDevices.value[index] = {
            ...localDevices.value[index],
            is_online: false,
        };
    }
};

// Table columns
const columns = [
    {
        title: '设备信息',
        key: 'device',
        render: (row: Device) => h('div', { class: 'device-cell' }, [
            h('div', { class: 'device-icon-wrapper' }, [
                h(NIcon, { component: PhonePortraitOutline, size: 20, color: '#64748b' }),
            ]),
            h('div', { class: 'device-details' }, [
                h('div', { class: 'device-name' }, row.name || '未命名设备'),
                h('div', { class: 'device-model' }, row.model || '-'),
            ]),
        ]),
    },
    {
        title: 'Android',
        key: 'android_version',
        width: 100,
        render: (row: Device) => h(NTag, { 
            size: 'small', 
            round: true,
            bordered: false,
            style: { background: '#f1f5f9', color: '#475569' }
        }, () => row.android_version ? `v${row.android_version}` : '-'),
    },
    {
        title: '地区',
        key: 'country',
        width: 120,
        render: (row: Device) => h('div', { class: 'country-cell' }, [
            h(NIcon, { component: LocationOutline, size: 16, color: '#94a3b8' }),
            h('span', {}, row.country || '-'),
        ]),
    },
    {
        title: '电量',
        key: 'battery_level',
        width: 80,
        render: (row: Device) => {
            const battery = row.battery_level ?? 0;
            const color = battery > 50 ? '#10B981' : battery > 20 ? '#F59E0B' : '#EF4444';
            return h('span', { style: { color, fontWeight: 500 } }, `${battery}%`);
        },
    },
    {
        title: '状态',
        key: 'is_online',
        width: 100,
        render: (row: Device) => h('div', { class: 'status-cell' }, [
            h('div', { 
                class: ['status-dot', row.is_online ? 'online' : 'offline']
            }),
            h('span', { 
                class: row.is_online ? 'text-emerald-600' : 'text-slate-400'
            }, row.is_online ? '在线' : '离线'),
        ]),
    },
    {
        title: '操作',
        key: 'actions',
        width: 140,
        render: (row: Device) => h(NSpace, { size: 8 }, () => [
            h(NButton, { 
                size: 'small',
                quaternary: true,
                circle: true,
                onClick: () => window.open(`/devices/${row.uuid}/control`, '_blank')
            }, { 
                icon: () => h(NIcon, { component: EyeOutline, color: '#3B82F6' })
            }),
            h(NPopconfirm, { 
                onPositiveClick: () => router.delete(`/devices/${row.id}`),
                positiveButtonProps: { type: 'error' },
            }, {
                trigger: () => h(NButton, { 
                    size: 'small',
                    quaternary: true,
                    circle: true,
                }, { 
                    icon: () => h(NIcon, { component: TrashOutline, color: '#EF4444' })
                }),
                default: () => '确定要移除此设备吗？',
            }),
        ]),
    },
];

// Refresh page data
const refresh = () => {
    router.reload({ only: ['devices', 'stats'] });
};

// Handle pagination
const handlePageChange = (page: number) => {
    router.visit(`/devices?page=${page}`, { preserveState: true });
};
</script>

<template>
    <Head title="设备管理" />
    <AuthenticatedLayout>
        <template #header-title>
            <div class="header-with-status">
                <span>设备管理</span>
                <NTag v-if="isConnected" type="success" size="small" round>
                    <template #icon>
                        <NIcon :component="WifiOutline" />
                    </template>
                    实时
                </NTag>
                <NTag v-else-if="isConnecting" type="warning" size="small" round>
                    连接中...
                </NTag>
                <NTag v-else type="default" size="small" round>
                    离线
                </NTag>
            </div>
        </template>

        <div class="devices-container">
            <div class="stats-bar">
                <div class="stat-item">
                    <span class="stat-label">全部设备</span>
                    <span class="stat-value">{{ localStats.total }}</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                    <div class="status-dot online"></div>
                    <span class="stat-label">在线</span>
                    <span class="stat-value text-emerald-600">{{ localStats.online }}</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                    <div class="status-dot offline"></div>
                    <span class="stat-label">离线</span>
                    <span class="stat-value text-slate-400">{{ localStats.offline }}</span>
                </div>
            </div>

            <div class="main-card">
                <div class="toolbar">
                    <div class="toolbar-left">
                        <NInput
                            v-model:value="searchQuery"
                            placeholder="搜索设备名称、型号..."
                            clearable
                            class="search-input"
                        >
                            <template #prefix>
                                <NIcon :component="SearchOutline" color="#94a3b8" />
                            </template>
                        </NInput>
                        <NSelect
                            v-model:value="statusFilter"
                            :options="statusOptions"
                            placeholder="状态筛选"
                            class="status-select"
                            clearable
                        />
                    </div>
                    <div class="toolbar-right">
                        <NButton quaternary circle @click="refresh" :loading="isConnecting">
                            <template #icon>
                                <NIcon :component="RefreshOutline" />
                            </template>
                        </NButton>
                    </div>
                </div>

                <div class="table-wrapper">
                    <NDataTable
                        v-if="filteredDevices.length > 0"
                        :columns="columns"
                        :data="filteredDevices"
                        :bordered="false"
                        :single-line="false"
                        :row-key="(row: Device) => row.uuid"
                        class="devices-table"
                    />
                    <div v-else class="empty-state">
                        <div class="empty-icon">
                            <NIcon :component="PhonePortraitOutline" size="48" color="#cbd5e1" />
                        </div>
                        <h3 class="empty-title">暂无设备</h3>
                        <p class="empty-description">
                            {{ searchQuery || statusFilter !== 'all' ? '没有找到匹配的设备' : '还没有设备连接到系统' }}
                        </p>
                    </div>
                </div>

                <!-- Pagination -->
                <div v-if="props.devices.last_page > 1" class="pagination-wrapper">
                    <NPagination
                        :page="props.devices.current_page"
                        :page-count="props.devices.last_page"
                        :page-size="props.devices.per_page"
                        @update:page="handlePageChange"
                    />
                </div>
            </div>
        </div>
    </AuthenticatedLayout>
</template>

<style scoped>
.devices-container {
    max-width: 1200px;
}

.header-with-status {
    display: flex;
    align-items: center;
    gap: 12px;
}

.stats-bar {
    display: flex;
    align-items: center;
    gap: 24px;
    background: white;
    border-radius: 14px;
    padding: 20px 28px;
    margin-bottom: 20px;
    border: 1px solid #e2e8f0;
}

.stat-item {
    display: flex;
    align-items: center;
    gap: 10px;
}

.stat-label {
    font-size: 14px;
    color: #64748b;
}

.stat-value {
    font-size: 20px;
    font-weight: 700;
    color: #1e293b;
}

.stat-divider {
    width: 1px;
    height: 32px;
    background: #e2e8f0;
}

.status-dot {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    flex-shrink: 0;
}

.status-dot.online {
    background: #10B981;
    box-shadow: 0 0 8px rgba(16, 185, 129, 0.5);
}

.status-dot.offline {
    background: #cbd5e1;
}

.main-card {
    background: white;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
    overflow: hidden;
}

.toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px 24px;
    border-bottom: 1px solid #f1f5f9;
}

.toolbar-left {
    display: flex;
    gap: 12px;
}

.search-input {
    width: 280px;
}

.search-input :deep(.n-input) {
    border-radius: 10px;
    background: #f8fafc;
}

.search-input :deep(.n-input:focus-within) {
    background: white;
}

.status-select {
    width: 140px;
}

.status-select :deep(.n-base-selection) {
    border-radius: 10px;
    background: #f8fafc;
}

.table-wrapper {
    padding: 0 8px 8px;
}

.devices-table :deep(.n-data-table-th) {
    background: #f8fafc;
    font-weight: 600;
    color: #475569;
    font-size: 13px;
    padding: 14px 16px;
}

.devices-table :deep(.n-data-table-td) {
    padding: 16px;
    border-bottom: 1px solid #f1f5f9;
}

.devices-table :deep(.n-data-table-tr:hover .n-data-table-td) {
    background: #fafafa;
}

:deep(.device-cell) {
    display: flex;
    align-items: center;
    gap: 14px;
}

:deep(.device-icon-wrapper) {
    width: 42px;
    height: 42px;
    background: #f1f5f9;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

:deep(.device-details) {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

:deep(.device-name) {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

:deep(.device-model) {
    font-size: 13px;
    color: #64748b;
}

:deep(.country-cell) {
    display: flex;
    align-items: center;
    gap: 6px;
    color: #64748b;
    font-size: 14px;
}

:deep(.status-cell) {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    font-weight: 500;
}

.pagination-wrapper {
    display: flex;
    justify-content: center;
    padding: 20px;
    border-top: 1px solid #f1f5f9;
}

.empty-state {
    padding: 80px 20px;
    text-align: center;
}

.empty-icon {
    width: 80px;
    height: 80px;
    background: #f8fafc;
    border-radius: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 20px;
}

.empty-title {
    font-size: 18px;
    font-weight: 600;
    color: #475569;
    margin: 0 0 8px;
}

.empty-description {
    font-size: 14px;
    color: #94a3b8;
    margin: 0;
}

@media (max-width: 768px) {
    .stats-bar {
        flex-wrap: wrap;
        gap: 16px;
    }
    
    .stat-divider {
        display: none;
    }
    
    .toolbar {
        flex-direction: column;
        gap: 12px;
        align-items: stretch;
    }
    
    .toolbar-left {
        flex-direction: column;
    }
    
    .search-input {
        width: 100%;
    }
    
    .status-select {
        width: 100%;
    }
    
    .toolbar-right {
        justify-content: flex-end;
    }
}
</style>
