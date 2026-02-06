<script setup lang="ts">
import { computed, ref, h, watch } from 'vue';
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
    wallpap?: string | null;
    screen_status?: string;
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
        canControl?: boolean;
        filters?: Record<string, string>;
    }>(),
    {
        basePath: '/devices',
        showUserColumn: false,
        allowControl: true,
        allowDelete: true,
        allowEditRemark: true,
        allowEditLink: false,
        canControl: true,
        filters: () => ({}),
    }
);

// [solid-srp] 从 useGlobalWebSocket 获取所有状态
// [core-dry] 设备列表由 WebSocket 的 phoneInfo 驱动（deviceOnline / deviceUpdate 同构，统一用 phoneInfo）
const {
    connectionState,
    stats: globalStats,
    devices: wsDevices,
    hasReceivedWsData,
} = useGlobalWebSocket();

// [core-kiss] 简化数据源逻辑：优先使用 WebSocket 数据
const localDevices = computed<DeviceRow[]>(() => {
    // WebSocket 已连接且收到过数据，使用 WebSocket 设备列表
    if (hasReceivedWsData.value && wsDevices.value.length > 0) {
        return wsDevices.value as DeviceRow[];
    }
    // 否则使用 HTTP props 数据作为 fallback
    return props.devices.data as DeviceRow[];
});

// [solid-ocp] 统计数据源：WebSocket 优先，HTTP fallback
const displayStats = computed(() => {
    // WebSocket 已收到数据，使用实时统计
    if (hasReceivedWsData.value) {
        return globalStats.value;
    }
    // 否则使用 HTTP 初始数据
    return props.stats;
});

const searchQuery = ref('');
const statusFilter = ref<string>('all');
const statusOptions = [
    { label: '全部状态', value: 'all' },
    { label: '在线', value: 'online' },
    { label: '离线', value: 'offline' },
];

// 客户端分页状态
const currentPage = ref(1);
const pageSize = ref(10);
const pageSizeOptions = [
    { label: '10 条/页', value: 10 },
    { label: '20 条/页', value: 20 },
    { label: '50 条/页', value: 50 },
    { label: '100 条/页', value: 100 },
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

// 分页后的设备列表
const paginatedDevices = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return filteredDevices.value.slice(start, end);
});

const totalPages = computed(() => Math.ceil(filteredDevices.value.length / pageSize.value) || 1);
const totalCount = computed(() => filteredDevices.value.length);

// 筛选条件变化时重置页码
watch([searchQuery, statusFilter], () => {
    currentPage.value = 1;
});

const isConnected = computed(() => connectionState.value === 'connected');
const isConnecting = computed(() => connectionState.value === 'connecting' || connectionState.value === 'reconnecting');

// NDataTable 需要 scroll-x 才能严格执行列 width，否则浏览器 auto 布局会忽略宽度设定
// 值 = 所有固定宽列之和 + 设备列 minWidth；设备列使用 minWidth 故容器更宽时自动拉伸
const scrollX = computed(() => {
    // device(160) + remark(80) + system(50) + status(58) + battery(55) + network(50)
    // + ip(90) + accessibility(60) + screen(42) + activity(68) + install(68) + wallpaper(38) + actions(100)
    let total = 919;
    if (props.showUserColumn) total += 80; // user column
    return total;
});

// [solid-srp] 设备状态更新逻辑已移至 useGlobalWebSocket
// 组件只需响应 computed 属性的变化，无需手动处理消息

function formatLastSeen(dateStr: string | null) {
    if (!dateStr) return '从未';
    const date = new Date(dateStr);
    if (Number.isNaN(date.getTime())) return '从未';
    const now = new Date();
    const diffMs = now.getTime() - date.getTime();
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    if (diffMs < 0) return date.toLocaleDateString('zh-CN');
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    return date.toLocaleDateString('zh-CN');
}

function formatInstallTime(dateStr: string | null | undefined): string {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (Number.isNaN(date.getTime())) return '-';
    const now = new Date();
    const diffDays = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));

    // 未来或当天零点（仅日期时区导致 diffDays 为 -1）时显示日期，避免 "-1天前"
    if (diffDays < 0) return date.toLocaleDateString('zh-CN', { month: 'short', day: 'numeric' });
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

function getScreenStatusInfo(status: string | undefined) {
    const basePath = '/images/activz/';
    switch (status) {
        case '0': return { label: '亮屏已锁', image: `${basePath}ON_LOCK.png`, isOn: true, isLocked: true };
        case '1': return { label: '息屏已锁', image: `${basePath}OFF_LOCK.png`, isOn: false, isLocked: true };
        case '2': return { label: '亮屏解锁', image: `${basePath}ON.png`, isOn: true, isLocked: false };
        case '3': return { label: '息屏解锁', image: `${basePath}OFF.png`, isOn: false, isLocked: false };
        default: return { label: '未知', image: `${basePath}known.png`, isOn: false, isLocked: false };
    }
}

function openControl(uuid: string) {
    if (!props.canControl) {
        window.dispatchEvent(new CustomEvent('permission-denied', { detail: {} }));
        return;
    }
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
            width: 160,
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'device-cell' }, [
                            h('div', { class: ['device-avatar', { online: row.is_online }] }, [h(NIcon, { component: PhonePortraitOutline, size: 18 })]),
                            h('div', { class: 'device-info' }, [
                                h('div', { class: 'device-name' }, row.name || '未命名设备'),
                                h('div', { class: 'device-model' }, [
                                    h(NIcon, { component: HardwareChipOutline, size: 11, color: '#94a3b8' }),
                                    h('span', { style: { marginLeft: '3px' } }, row.model || '未知型号'),
                                ]),
                            ]),
                        ]),
                    default: () => `设备名称: ${row.name || '-'}`,
                }),
        },
        {
            title: '备注',
            key: 'remark',
            width: 80,
            ellipsis: { tooltip: true },
            render: (row) => (row.remark ? h('span', { class: 'remark-cell' }, row.remark) : h('span', { class: 'text-slate-400' }, '-')),
        },
    ];

    if (props.showUserColumn) {
        cols.push({
            title: '用户',
            key: 'user',
            width: 80,
            ellipsis: { tooltip: true },
            render: (row) => (row.user ? row.user.username : '-'),
        });
    }

    cols.push(
        {
            title: '系统',
            key: 'android_version',
            width: 50,
            align: 'center',
            render: (row) =>
                h(NTag, { size: 'tiny', round: true, bordered: false, style: { background: '#f1f5f9', color: '#475569', fontSize: '11px' } }, () =>
                    row.android_version ? `${row.android_version}` : '-'
                ),
        },
        {
            title: '状态',
            key: 'is_online',
            width: 58,
            align: 'center',
            render: (row) =>
                h('div', { class: 'status-cell' }, [
                    h('span', { class: ['status-dot', row.is_online ? 'online' : 'offline'] }),
                    h('span', { style: { color: row.is_online ? '#059669' : '#94a3b8', fontWeight: 500, fontSize: '12px' } }, row.is_online ? '在线' : '离线'),
                ]),
        },
        {
            title: '电量',
            key: 'battery_level',
            width: 55,
            align: 'center',
            render: (row) => {
                const isCharging = row.battery_is_charging ?? false;
                const icon = isCharging ? BatteryChargingOutline : getBatteryIcon(row.battery_level ?? null);
                const color = getBatteryColor(row.battery_level ?? null);
                const label = row.battery_level !== null && row.battery_level !== undefined ? `${row.battery_level}%` : '-';
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h(NIcon, { component: icon, size: 14, color }),
                            h('span', { style: { color, marginLeft: '2px', fontWeight: 500, fontSize: '12px' } }, label),
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
            width: 50,
            align: 'center',
            render: (row) => {
                const info = getNetworkInfo(row.network_type ?? null);
                return h('div', { class: 'metric-cell' }, [
                    h(NIcon, { component: info.icon, size: 14, color: info.color }),
                    h('span', { style: { color: info.color, marginLeft: '2px', fontWeight: 500, fontSize: '12px' } }, info.label),
                ]);
            },
        },
        {
            title: 'IP',
            key: 'ip_address',
            width: 90,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'ip-cell' }, [
                            h('span', { class: 'ip-address', style: { fontFamily: 'monospace', fontSize: '11px' } }, row.ip_address || '-'),
                            row.ip_location ? h('span', { class: 'ip-location' }, row.ip_location) : null,
                        ].filter(Boolean)),
                    default: () =>
                        row.ip_address ? `${row.ip_address}${row.ip_location ? ` · ${row.ip_location}` : ''}` : '暂无 IP 信息',
                }),
        },
        {
            title: '无障碍',
            key: 'has_accessibility',
            width: 60,
            align: 'center',
            render: (row) => {
                const active = row.has_accessibility ?? false;
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: ['accessibility-cell', active ? 'accessibility-cell--on' : 'accessibility-cell--off'] }, [
                            h(NIcon, { component: AccessibilityOutline, size: 14 }),
                            h('span', { class: 'accessibility-label' }, active ? '开' : '关'),
                        ]),
                    default: () => active
                        ? (isConnected && row.is_online ? '无障碍已开启 · 实时更新' : '无障碍已开启')
                        : (isConnected && row.is_online ? '无障碍未开启 · 实时更新' : '无障碍未开启'),
                });
            },
        },
        {
            title: '屏幕',
            key: 'screen_status',
            width: 42,
            align: 'center',
            render: (row) => {
                const info = getScreenStatusInfo(row.screen_status);
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'screen-status-cell' }, [
                            h('img', { 
                                src: info.image, 
                                alt: info.label,
                                class: 'screen-status-img',
                            }),
                        ]),
                    default: () => `屏幕状态: ${info.label}${isConnected && row.is_online ? ' · 实时更新' : ''}`,
                });
            },
        },
        {
            title: '活动',
            key: 'last_seen_at',
            width: 68,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h('span', { style: { color: '#64748b', fontSize: '12px' } }, formatLastSeen(row.last_seen_at)),
                        ]),
                    default: () =>
                        row.last_seen_at
                            ? `最后活动: ${new Date(row.last_seen_at).toLocaleString('zh-CN')}${isConnected && row.is_online ? ' · 实时更新' : ''}`
                            : '暂无活动记录',
                }),
        },
        {
            title: '安装',
            key: 'installed_at',
            width: 68,
            align: 'center',
            render: (row) =>
                h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'metric-cell' }, [
                            h('span', { style: { color: '#64748b', fontSize: '12px' } }, formatInstallTime(row.installed_at)),
                        ]),
                    default: () =>
                        row.installed_at
                            ? `安装时间: ${new Date(row.installed_at).toLocaleString('zh-CN')}`
                            : '暂无安装记录',
                }),
        },
        {
            title: '截图',
            key: 'wallpap',
            width: 38,
            align: 'center',
            render: (row) => {
                if (!row.wallpap) {
                    return h('div', { class: 'wallpaper-cell' }, [
                        h('div', { class: 'wallpaper-thumbnail wallpaper-thumbnail--empty' }),
                    ]);
                }
                const wallpaperUrl = row.wallpap.startsWith('data:') 
                    ? row.wallpap 
                    : `data:image/png;base64,${row.wallpap}`;
                return h(NTooltip, { trigger: 'hover' }, {
                    trigger: () =>
                        h('div', { class: 'wallpaper-cell' }, [
                            h('img', { 
                                src: wallpaperUrl, 
                                alt: '屏幕', 
                                class: 'wallpaper-thumbnail',
                            }),
                        ]),
                    default: () => h('img', { 
                        src: wallpaperUrl, 
                        alt: '设备屏幕',
                        style: { maxWidth: '150px', maxHeight: '150px', borderRadius: '8px' },
                    }),
                });
            },
        }
    );

    cols.push({
        title: '操作',
        key: 'actions',
        width: 100,
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
                <span class="stat-value">{{ displayStats.total }}</span>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
                <div class="status-dot online"></div>
                <span class="stat-label">在线</span>
                <span class="stat-value text-emerald-600">{{ displayStats.online }}</span>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
                <div class="status-dot offline"></div>
                <span class="stat-label">离线</span>
                <span class="stat-value text-slate-400">{{ displayStats.offline }}</span>
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
                    v-if="paginatedDevices.length > 0"
                    :columns="columns"
                    :data="paginatedDevices"
                    :scroll-x="scrollX"
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

            <div v-if="totalCount > 0" class="pagination-wrapper">
                <div class="pagination-info">
                    显示 {{ Math.min((currentPage - 1) * pageSize + 1, totalCount) }}-{{ Math.min(currentPage * pageSize, totalCount) }} / 共 {{ totalCount }} 条
                </div>
                <div class="pagination-controls">
                    <NSelect 
                        v-model:value="pageSize" 
                        :options="pageSizeOptions" 
                        size="small"
                        style="width: 110px"
                        @update:value="() => currentPage = 1"
                    />
                    <NPagination
                        v-model:page="currentPage"
                        :page-count="totalPages"
                        :page-size="pageSize"
                        :page-slot="5"
                        size="medium"
                    />
                </div>
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
}

.devices-table :deep(.n-data-table-th) {
    background: #f8fafc;
    font-weight: 600;
    color: #475569;
    font-size: 13px;
    padding: 12px 8px;
}

.devices-table :deep(.n-data-table-td) {
    padding: 12px 8px;
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
    gap: 10px;
}

:deep(.device-avatar) {
    width: 36px;
    height: 36px;
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
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

:deep(.device-model) {
    display: flex;
    align-items: center;
    font-size: 12px;
    color: #94a3b8;
    overflow: hidden;
    white-space: nowrap;
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
    gap: 4px;
    min-width: 0;
    white-space: nowrap;
    transition: color 0.25s ease;
}

:deep(.accessibility-cell--off) {
    color: #94a3b8;
}

:deep(.accessibility-cell--on) {
    color: #059669;
}

:deep(.accessibility-label) {
    font-size: 12px;
    font-weight: 500;
}

:deep(.screen-status-cell) {
    display: flex;
    align-items: center;
    justify-content: center;
}

:deep(.screen-status-img) {
    width: 32px;
    height: 32px;
    object-fit: contain;
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

:deep(.wallpaper-cell) {
    display: flex;
    align-items: center;
    justify-content: center;
}

:deep(.wallpaper-thumbnail) {
    width: 27px;
    height: 48px;
    border-radius: 4px;
    object-fit: cover;
    border: 1px solid #e2e8f0;
    background: #f8fafc;
}

:deep(.wallpaper-thumbnail--empty) {
    background: #e2e8f0;
}

.pagination-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 24px;
    border-top: 1px solid #f1f5f9;
}

.pagination-info {
    font-size: 13px;
    color: #64748b;
}

.pagination-controls {
    display: flex;
    align-items: center;
    gap: 16px;
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
