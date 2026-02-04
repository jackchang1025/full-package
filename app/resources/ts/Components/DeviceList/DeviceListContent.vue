<script setup lang="ts">
import { computed, ref, onMounted, onUnmounted, watch, h } from 'vue';
import { router } from '@inertiajs/vue3';
import {
    NDataTable,
    NButton,
    NTag,
    NPopconfirm,
    NIcon,
    NInput,
    NSelect,
    NPagination,
    NTooltip,
    NSpace,
    NModal,
    NForm,
    NFormItem,
    useMessage,
} from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import {
    SearchOutline,
    RefreshOutline,
    EyeOutline,
    TrashOutline,
    PhonePortraitOutline,
    WifiOutline,
    CellularOutline,
    AccessibilityOutline,
    TimeOutline,
    BatteryChargingOutline,
    BatteryFullOutline,
    BatteryHalfOutline,
    BatteryDeadOutline,
    EllipseOutline,
    HardwareChipOutline,
    CreateOutline,
    DocumentTextOutline,
    CalendarOutline,
} from '@vicons/ionicons5';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';
import type { DeviceOnlineMessage, DeviceOfflineMessage, DeviceUpdateMessage } from '@/types/websocket';

export interface DeviceRow {
    id: number;
    uuid: string;
    name: string;
    remark: string | null;
    model: string;
    android_version?: string;
    country?: string;
    ip_address?: string | null;
    ip_location?: string | null;
    network_type?: string | null;
    battery_level?: number | null;
    battery_is_charging?: boolean;
    is_online: boolean;
    has_accessibility?: boolean;
    last_seen_at: string | null;
    installed_at?: string | null;
    user?: { id: number; username: string; email: string } | null;
}

export interface PaginatedDevices {
    data: DeviceRow[];
    current_page: number;
    last_page: number;
    per_page: number;
    total: number;
}

export interface Stats {
    total: number;
    online: number;
    offline: number;
}

const props = withDefaults(
    defineProps<{
        devices: { data: unknown[]; current_page: number; last_page: number; per_page: number; total: number };
        stats: Stats;
        basePath?: string;
        showUserColumn?: boolean;
        allowControl?: boolean;
        allowDelete?: boolean;
        allowEditRemark?: boolean;
        allowEditLink?: boolean;
        filters?: Record<string, string>;
    }>(),
    {
        basePath: '/devices',
        showUserColumn: false,
        allowControl: true,
        allowDelete: true,
        allowEditRemark: true,
        allowEditLink: false,
        filters: () => ({}),
    }
);

const { connectionState, onMessage } = useGlobalWebSocket();
const localDevices = ref<DeviceRow[]>([...(props.devices.data as DeviceRow[])]);
const localStats = ref<Stats>({ ...props.stats });

watch(
    () => props.devices.data,
    (newData) => {
        localDevices.value = [...(newData as DeviceRow[])];
    },
    { deep: true }
);
watch(
    () => props.stats,
    (newStats) => {
        localStats.value = { ...newStats };
    },
    { deep: true }
);

const searchQuery = ref('');
const statusFilter = ref<string>('all');
const statusOptions = [
    { label: '全部状态', value: 'all' },
    { label: '在线', value: 'online' },
    { label: '离线', value: 'offline' },
];

const filteredDevices = computed(() => {
    let result = localDevices.value;
    if (searchQuery.value) {
        const q = searchQuery.value.toLowerCase();
        result = result.filter(
            (d) =>
                (d.name || '').toLowerCase().includes(q) ||
                (d.remark || '').toLowerCase().includes(q) ||
                (d.model || '').toLowerCase().includes(q) ||
                (d.uuid || '').toLowerCase().includes(q) ||
                (d.user?.username || '').toLowerCase().includes(q) ||
                (d.user?.email || '').toLowerCase().includes(q)
        );
    }
    if (statusFilter.value === 'online') result = result.filter((d) => d.is_online);
    else if (statusFilter.value === 'offline') result = result.filter((d) => !d.is_online);
    return result;
});

const isConnected = computed(() => connectionState.value === 'connected');
const isConnecting = computed(() => connectionState.value === 'connecting' || connectionState.value === 'reconnecting');

let unsubscribe: (() => void) | null = null;
onMounted(() => {
    unsubscribe = onMessage((msg) => {
        const msgType = (msg as { type?: string }).type;
        if (msgType === 'deviceOnline') {
            const data = msg as DeviceOnlineMessage;
            handleDeviceOnline(data.pid, data.deviceInfo);
            if (data.stats) localStats.value = data.stats;
        } else if (msgType === 'deviceOffline') {
            const data = msg as DeviceOfflineMessage;
            handleDeviceOffline(data.pid);
            if (data.stats) localStats.value = data.stats;
        } else if (msgType === 'deviceUpdate') {
            const data = msg as DeviceUpdateMessage;
            handleDeviceUpdate(data.pid, (data.phoneInfo || {}) as Record<string, unknown>);
        }
    });
});
onUnmounted(() => {
    if (unsubscribe) unsubscribe();
});

function parseBattery(batteryCharge: string | undefined): { isCharging: boolean; level: number | null } {
    if (!batteryCharge || typeof batteryCharge !== 'string') return { isCharging: false, level: null };
    const parts = batteryCharge.split('~');
    if (parts.length >= 2) {
        const level = parseInt(parts[1], 10);
        return { isCharging: parts[0] === 't', level: isNaN(level) ? null : level };
    }
    const level = parseInt(batteryCharge, 10);
    return { isCharging: false, level: isNaN(level) ? null : level };
}

function handleDeviceOnline(pid: string, deviceInfo: any) {
    const index = localDevices.value.findIndex((d) => d.uuid === pid);
    if (index >= 0) {
        const updates: Partial<DeviceRow> = {
            ...localDevices.value[index],
            is_online: true,
            name: deviceInfo?.phone_name || localDevices.value[index].name,
        };
        if (deviceInfo?.battery_charge != null) {
            const { isCharging, level } = parseBattery(deviceInfo.battery_charge);
            if (level !== null) updates.battery_level = level;
            updates.battery_is_charging = isCharging;
        }
        localDevices.value[index] = { ...localDevices.value[index], ...updates };
    }
}

function handleDeviceOffline(pid: string) {
    const index = localDevices.value.findIndex((d) => d.uuid === pid);
    if (index >= 0) {
        localDevices.value[index] = { ...localDevices.value[index], is_online: false };
    }
}

function handleDeviceUpdate(pid: string, phoneInfo: Record<string, unknown>) {
    const index = localDevices.value.findIndex((d) => d.uuid === pid);
    if (index < 0) return;
    const updates: Partial<DeviceRow> = {};
    if (phoneInfo.phone_name != null && String(phoneInfo.phone_name).trim()) updates.name = String(phoneInfo.phone_name).trim();
    if (phoneInfo.model != null && String(phoneInfo.model).trim()) updates.model = String(phoneInfo.model).trim();
    if (phoneInfo.android_version != null) updates.android_version = String(phoneInfo.android_version);
    if (phoneInfo.battery_charge != null) {
        const { isCharging, level } = parseBattery(phoneInfo.battery_charge as string);
        if (level !== null) updates.battery_level = level;
        updates.battery_is_charging = isCharging;
    }
    if (phoneInfo.lastPing != null) {
        const ts = typeof phoneInfo.lastPing === 'number' ? phoneInfo.lastPing : parseInt(String(phoneInfo.lastPing), 10);
        if (!isNaN(ts)) updates.last_seen_at = new Date(ts).toISOString();
    }
    if (phoneInfo.accessibility != null) updates.has_accessibility = phoneInfo.accessibility === '1';
    if (phoneInfo.ip != null) updates.ip_address = String(phoneInfo.ip);
    if (phoneInfo.ip_location != null) updates.ip_location = String(phoneInfo.ip_location);
    if (Object.keys(updates).length > 0) {
        localDevices.value[index] = { ...localDevices.value[index], ...updates };
    }
}

function formatLastSeen(dateStr: string | null) {
    if (!dateStr) return '从未';
    const date = new Date(dateStr);
    const now = new Date();
    const diffMs = now.getTime() - date.getTime();
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    return date.toLocaleDateString('zh-CN');
}

function formatInstallTime(dateStr: string | null | undefined): string {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    const now = new Date();
    const diffDays = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    
    if (diffDays === 0) return '今天';
    if (diffDays === 1) return '昨天';
    if (diffDays < 7) return `${diffDays}天前`;
    
    return date.toLocaleDateString('zh-CN', { month: 'short', day: 'numeric' });
}

function getBatteryIcon(level: number | null) {
    if (level === null) return BatteryDeadOutline;
    if (level > 60) return BatteryFullOutline;
    if (level > 20) return BatteryHalfOutline;
    return BatteryDeadOutline;
}

function getBatteryColor(level: number | null) {
    if (level === null) return '#94a3b8';
    if (level > 60) return '#10B981';
    if (level > 20) return '#F59E0B';
    return '#EF4444';
}

function getNetworkInfo(type: string | null) {
    if (!type) return { icon: EllipseOutline, color: '#cbd5e1', label: '未知' };
    const t = type.toLowerCase();
    if (t.includes('wifi')) return { icon: WifiOutline, color: '#3B82F6', label: 'WiFi' };
    if (t.includes('5g')) return { icon: CellularOutline, color: '#8B5CF6', label: '5G' };
    if (t.includes('4g') || t.includes('lte')) return { icon: CellularOutline, color: '#10B981', label: '4G' };
    if (t.includes('3g')) return { icon: CellularOutline, color: '#F59E0B', label: '3G' };
    return { icon: CellularOutline, color: '#64748b', label: type };
}

function openControl(uuid: string) {
    window.open(`${props.basePath}/${uuid}/control`, '_blank');
}

function deleteDevice(uuid: string) {
    router.delete(`${props.basePath}/${uuid}`);
}

function goToEdit(uuid: string) {
    router.visit(`${props.basePath}/${uuid}/edit`);
}

const message = useMessage();
const showEditRemarkModal = ref(false);
const editRemarkDevice = ref<DeviceRow | null>(null);
const editRemarkValue = ref('');
const editRemarkLoading = ref(false);

function openEditRemarkModal(device: DeviceRow) {
    editRemarkDevice.value = device;
    editRemarkValue.value = device.remark ?? '';
    showEditRemarkModal.value = true;
}

function closeEditRemarkModal() {
    showEditRemarkModal.value = false;
    editRemarkDevice.value = null;
}

function saveRemark() {
    const device = editRemarkDevice.value;
    if (!device || editRemarkLoading.value) return;
    const newRemark = editRemarkValue.value.trim();
    if (newRemark === (device.remark ?? '')) {
        closeEditRemarkModal();
        return;
    }
    editRemarkLoading.value = true;
    router.put(`${props.basePath}/${device.uuid}`, { remark: newRemark }, {
        preserveScroll: true,
        onSuccess: () => {
            const index = localDevices.value.findIndex((d) => d.uuid === device.uuid);
            if (index >= 0) {
                localDevices.value[index] = { ...localDevices.value[index], remark: newRemark };
            }
            message.success('备注已更新');
            closeEditRemarkModal();
        },
        onError: () => message.error('更新失败'),
        onFinish: () => {
            editRemarkLoading.value = false;
        },
    });
}

function refresh() {
    router.reload({ only: ['devices', 'stats'] });
}

function handlePageChange(page: number) {
    const params = new URLSearchParams({ page: String(page) });
    Object.entries(props.filters).forEach(([k, v]) => {
        if (v != null && v !== '') params.set(k, v);
    });
    const query = params.toString();
    router.visit(`${props.basePath}${query ? `?${query}` : ''}`, { preserveState: true });
}

const columns = computed<DataTableColumns<DeviceRow>>(() => {
    const cols: DataTableColumns<DeviceRow> = [
        {
            title: '设备',
            key: 'device',
            minWidth: 220,
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'device-cell' }, [
                            h('div', { class: ['device-avatar', { online: row.is_online }] }, [h(NIcon, { component: PhonePortraitOutline, size: 20 })]),
                            h('div', { class: 'device-info' }, [
                                h('div', { class: 'device-name' }, row.name || '未命名设备'),
                                h('div', { class: 'device-model' }, [
                                    h(NIcon, { component: HardwareChipOutline, size: 12, color: '#94a3b8' }),
                                    h('span', { style: { marginLeft: '4px' } }, row.model || '未知型号'),
                                ]),
                            ]),
                        ]),
                    default: () => `设备名称: ${row.name || '-'}`,
                }),
        },
        {
            title: '备注',
            key: 'remark',
            width: 120,
            ellipsis: { tooltip: true },
            render: (row) => (row.remark ? h('span', { class: 'remark-cell' }, row.remark) : h('span', { class: 'text-slate-400' }, '-')),
        },
    ];

    if (props.showUserColumn) {
        cols.push({
            title: '所属用户',
            key: 'user',
            width: 160,
            ellipsis: { tooltip: true },
            render: (row) => (row.user ? row.user.username : '-'),
        });
    }

    cols.push(
        {
            title: 'Android',
            key: 'android_version',
            width: 90,
            align: 'center',
            render: (row) =>
                h(NTag, { size: 'small', round: true, bordered: false, style: { background: '#f1f5f9', color: '#475569' } }, () =>
                    row.android_version ? `v${row.android_version}` : '-'
                ),
        },
        {
            title: '状态',
            key: 'is_online',
            width: 90,
            align: 'center',
            render: (row) =>
                h('div', { class: 'status-cell' }, [
                    h('span', { class: ['status-dot', row.is_online ? 'online' : 'offline'] }),
                    h('span', { style: { color: row.is_online ? '#059669' : '#94a3b8', fontWeight: 500 } }, row.is_online ? '在线' : '离线'),
                ]),
        },
        {
            title: '电量',
            key: 'battery_level',
            width: 100,
            align: 'center',
            render: (row) => {
                const isCharging = row.battery_is_charging ?? false;
                const icon = isCharging ? BatteryChargingOutline : getBatteryIcon(row.battery_level ?? null);
                const color = getBatteryColor(row.battery_level ?? null);
                const label = row.battery_level !== null && row.battery_level !== undefined ? `${row.battery_level}%` : '-';
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h(NIcon, { component: icon, size: 16, color }),
                            h('span', { style: { color, marginLeft: '4px', fontWeight: 500 } }, label),
                        ]),
                    default: () =>
                        isCharging
                            ? isConnected && row.is_online
                                ? '设备正在充电 · 实时更新'
                                : '设备正在充电'
                            : row.battery_level != null
                              ? isConnected && row.is_online
                                ? `电量 ${row.battery_level}% · 实时更新`
                                : `电量 ${row.battery_level}%`
                              : '暂无电量数据',
                });
            },
        },
        {
            title: '网络',
            key: 'network_type',
            width: 90,
            align: 'center',
            render: (row) => {
                const info = getNetworkInfo(row.network_type ?? null);
                return h('div', { class: 'metric-cell' }, [
                    h(NIcon, { component: info.icon, size: 16, color: info.color }),
                    h('span', { style: { color: info.color, marginLeft: '4px', fontWeight: 500 } }, info.label),
                ]);
            },
        },
        {
            title: 'IP',
            key: 'ip_address',
            width: 130,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'ip-cell' }, [
                            h('span', { class: 'ip-address', style: { fontFamily: 'monospace', fontSize: '12px' } }, row.ip_address || '-'),
                            row.ip_location ? h('span', { class: 'ip-location' }, row.ip_location) : null,
                        ].filter(Boolean)),
                    default: () =>
                        row.ip_address ? `${row.ip_address}${row.ip_location ? ` · ${row.ip_location}` : ''}` : '暂无 IP 信息',
                }),
        },
        {
            title: '无障碍',
            key: 'has_accessibility',
            minWidth: 100,
            width: 100,
            align: 'center',
            render: (row) => {
                const active = row.has_accessibility ?? false;
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: ['accessibility-cell', active ? 'accessibility-cell--on' : 'accessibility-cell--off'] }, [
                            h('span', { class: 'accessibility-icon-wrap' }, [
                                h(NIcon, { component: AccessibilityOutline, size: 18 }),
                            ]),
                            h(
                                NTag,
                                {
                                    size: 'small',
                                    round: true,
                                    bordered: false,
                                    type: active ? 'success' : undefined,
                                    class: active ? 'accessibility-tag-on' : 'accessibility-tag-off',
                                },
                                { default: () => (active ? '开启' : '关闭') }
                            ),
                        ]),
                    default: () => (isConnected && row.is_online ? '无障碍状态 · 实时更新' : active ? '已开启' : '未开启'),
                });
            },
        },
        {
            title: '活动时间',
            key: 'last_seen_at',
            width: 120,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h(NIcon, { component: TimeOutline, size: 14, color: '#64748b' }),
                            h('span', { style: { color: '#64748b', marginLeft: '4px' } }, formatLastSeen(row.last_seen_at)),
                        ]),
                    default: () =>
                        row.last_seen_at
                            ? `最后活动: ${new Date(row.last_seen_at).toLocaleString('zh-CN')}${isConnected && row.is_online ? ' · 实时更新' : ''}`
                            : '暂无活动记录',
                }),
        },
        {
            title: '安装时间',
            key: 'installed_at',
            width: 120,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h(NIcon, { component: CalendarOutline, size: 14, color: '#64748b' }),
                            h('span', { style: { color: '#64748b', marginLeft: '4px' } }, formatInstallTime(row.installed_at)),
                        ]),
                    default: () =>
                        row.installed_at
                            ? `安装时间: ${new Date(row.installed_at).toLocaleString('zh-CN')}`
                            : '暂无安装记录',
                }),
        }
    );

    cols.push({
        title: '操作',
        key: 'actions',
        minWidth: 152,
        width: 152,
        align: 'center',
        render: (row) => {
            const actionButtons: ReturnType<typeof h>[] = [];
            if (props.allowEditLink) {
                actionButtons.push(
                    h(NTooltip, { trigger: 'hover' }, {
                        trigger: () =>
                            h(NButton, { size: 'small', quaternary: true, circle: true, class: 'action-btn action-btn--edit', onClick: () => goToEdit(row.uuid) }, {
                                icon: () => h(NIcon, { component: CreateOutline, size: 16 }),
                            }),
                        default: () => '编辑',
                    })
                );
            }
            if (props.allowEditRemark) {
                actionButtons.push(
                    h(NTooltip, { trigger: 'hover' }, {
                        trigger: () =>
                            h(NButton, { size: 'small', quaternary: true, circle: true, class: 'action-btn action-btn--remark', onClick: () => openEditRemarkModal(row) }, {
                                icon: () => h(NIcon, { component: DocumentTextOutline, size: 16 }),
                            }),
                        default: () => '修改备注',
                    })
                );
            }
            if (props.allowControl) {
                actionButtons.push(
                    h(NTooltip, { trigger: 'hover' }, {
                        trigger: () =>
                            h(NButton, { size: 'small', quaternary: true, circle: true, class: 'action-btn action-btn--control', onClick: () => openControl(row.uuid) }, {
                                icon: () => h(NIcon, { component: EyeOutline, size: 16 }),
                            }),
                        default: () => '查看控制',
                    })
                );
            }
            if (props.allowDelete) {
                actionButtons.push(
                    h(NPopconfirm, { onPositiveClick: () => deleteDevice(row.uuid), positiveButtonProps: { type: 'error' } }, {
                        trigger: () =>
                            h(NButton, { size: 'small', quaternary: true, circle: true, class: 'action-btn action-btn--delete' }, {
                                icon: () => h(NIcon, { component: TrashOutline, size: 16 }),
                            }),
                        default: () => '确定要移除此设备吗？',
                    })
                );
            }
            return h('div', { class: 'actions-cell' }, actionButtons);
        },
    });

    return cols;
});
</script>

<template>
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
                    <NInput v-model:value="searchQuery" placeholder="搜索设备名称、型号..." clearable class="search-input">
                        <template #prefix>
                            <NIcon :component="SearchOutline" color="#94a3b8" />
                        </template>
                    </NInput>
                    <NSelect v-model:value="statusFilter" :options="statusOptions" placeholder="状态筛选" class="status-select" clearable />
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
                    :single-line="true"
                    :row-key="(row: DeviceRow) => row.uuid"
                    :row-class-name="(row: DeviceRow) => (row.is_online ? 'row-online' : 'row-offline')"
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

            <NModal v-model:show="showEditRemarkModal" preset="card" title="修改设备备注" style="width: 400px" :bordered="false">
                <NForm label-placement="top">
                    <NFormItem label="设备备注">
                        <NInput v-model:value="editRemarkValue" placeholder="请输入备注（选填）" maxlength="200" show-count clearable />
                    </NFormItem>
                </NForm>
                <template #footer>
                    <NSpace justify="end">
                        <NButton @click="closeEditRemarkModal">取消</NButton>
                        <NButton type="primary" :loading="editRemarkLoading" @click="saveRemark">保存</NButton>
                    </NSpace>
                </template>
            </NModal>

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
</template>

<style scoped>
.devices-container {
    max-width: 100%;
    width: 100%;
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
    width: 8px;
    height: 8px;
    border-radius: 50%;
    flex-shrink: 0;
}

.status-dot.online {
    background: #10b981;
    box-shadow: 0 0 6px rgba(16, 185, 129, 0.5);
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
    overflow-x: auto;
}

.devices-table :deep(.n-data-table-th) {
    background: #f8fafc;
    font-weight: 600;
    color: #475569;
    font-size: 13px;
    padding: 14px 12px;
}

.devices-table :deep(.n-data-table-td) {
    padding: 14px 12px;
    border-bottom: 1px solid #f1f5f9;
}

.devices-table :deep(.n-data-table-tr:hover .n-data-table-td) {
    background: #fafbfc;
}

.devices-table :deep(.row-online) {
    background: rgba(16, 185, 129, 0.02);
}

:deep(.device-cell) {
    display: flex;
    align-items: center;
    gap: 12px;
}

:deep(.device-avatar) {
    width: 40px;
    height: 40px;
    border-radius: 10px;
    background: linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #64748b;
    flex-shrink: 0;
}

:deep(.device-avatar.online) {
    background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
    color: #059669;
}

:deep(.device-info) {
    display: flex;
    flex-direction: column;
    gap: 2px;
    min-width: 0;
}

:deep(.device-name) {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

:deep(.device-model) {
    display: flex;
    align-items: center;
    font-size: 12px;
    color: #94a3b8;
}

:deep(.status-cell) {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    font-size: 13px;
}

:deep(.metric-cell) {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 13px;
}

:deep(.accessibility-cell) {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    min-width: 0;
    white-space: nowrap;
}

:deep(.accessibility-icon-wrap) {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    color: #94a3b8;
}

:deep(.accessibility-cell--on .accessibility-icon-wrap) {
    color: #059669;
}

:deep(.accessibility-tag-on) {
    --n-color: #059669;
    --n-color-hover: #059669;
    --n-color-pressed: #047857;
    background: rgba(5, 150, 105, 0.12);
    color: #059669;
}

:deep(.accessibility-tag-off) {
    background: #f1f5f9;
    color: #64748b;
}

:deep(.actions-cell) {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    flex-wrap: nowrap;
    white-space: nowrap;
    min-width: 0;
    padding: 4px 0;
}

:deep(.actions-cell .action-btn) {
    flex-shrink: 0;
}

:deep(.actions-cell .action-btn--edit .n-icon),
:deep(.actions-cell .action-btn--edit:hover .n-icon) {
    color: #64748b;
}

:deep(.actions-cell .action-btn--remark .n-icon),
:deep(.actions-cell .action-btn--remark:hover .n-icon) {
    color: #64748b;
}

:deep(.actions-cell .action-btn--control .n-icon),
:deep(.actions-cell .action-btn--control:hover .n-icon) {
    color: #3b82f6;
}

:deep(.actions-cell .action-btn--delete .n-icon),
:deep(.actions-cell .action-btn--delete:hover .n-icon) {
    color: #ef4444;
}

:deep(.actions-cell .action-btn--edit:hover .n-icon) {
    color: #0d9488;
}

:deep(.actions-cell .action-btn--remark:hover .n-icon) {
    color: #0d9488;
}

:deep(.ip-cell) {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2px;
    font-size: 12px;
    min-width: 0;
}

:deep(.ip-cell .ip-address) {
    color: #475569;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
}

:deep(.ip-cell .ip-location) {
    color: #94a3b8;
    font-size: 11px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
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

@media (max-width: 1024px) {
    .devices-table :deep(.n-data-table) {
        font-size: 12px;
    }
    .devices-table :deep(.n-data-table-th),
    .devices-table :deep(.n-data-table-td) {
        padding: 10px 8px;
    }
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
    .table-wrapper {
        overflow-x: auto;
    }
}
</style>
