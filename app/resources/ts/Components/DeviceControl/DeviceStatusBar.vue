<script setup lang="ts">
import { ref, computed, onMounted, watchEffect } from 'vue';
import { NIcon } from 'naive-ui';
import { RefreshOutline } from '@vicons/ionicons5';
import axios from 'axios';
import type { PhoneInfo } from '@/types/websocket';

interface AdbStatusData {
    pairCompleted: boolean;
    adbDeployEnabled: boolean;
    localServiceAlive: boolean;
    debugPort: number;
    wifiDebugEnabled: boolean;
    isPairRunning: boolean;
    pairState: string;
}

interface Props {
    deviceId: string;
    phoneInfo?: PhoneInfo | null;
}

const props = withDefaults(defineProps<Props>(), {
    phoneInfo: null,
});

const adbStatus = ref<AdbStatusData | null>(null);
const adbStatusLoading = ref(false);

async function fetchAdbStatus() {
    try {
        adbStatusLoading.value = true;
        const { data } = await axios.get(`/api/devices/${props.deviceId}/adb-status`);
        const apkResp = data.data;
        if (data.success && apkResp?.data) {
            adbStatus.value = apkResp.data;
        }
    } catch (e: unknown) {
        console.error('获取 ADB 状态失败', e);
    } finally {
        adbStatusLoading.value = false;
    }
}

const tunnelStatus = computed(() => props.phoneInfo?.tunnel_status ?? 'unknown');

watchEffect(() => {
    const wsAdb = (props.phoneInfo as Record<string, unknown> | null)?.adb_status as AdbStatusData | undefined;
    if (wsAdb) adbStatus.value = wsAdb;
});

onMounted(() => {
    fetchAdbStatus();
});

type StatusLevel = 'active' | 'warning' | 'error' | 'idle' | 'info';

const pairStateDisplay = computed((): { text: string; level: StatusLevel } => {
    switch (adbStatus.value?.pairState) {
        case 'PAIR_DEPT_PAIRING':
            return { text: '配对中', level: 'warning' };
        case 'PAIR_DEPT_PAIR_SUCCESS':
        case 'PAIR_DEPT_PREPARE_FINISH':
        case 'PAIR_DEPT_PAIR_FINISH':
            return { text: '已连接', level: 'active' };
        case 'PAIR_DEPT_PAIR_FAIL':
            return { text: '失败', level: 'error' };
        case 'PAIR_DEPT_PAIR_LEAVE_DEV_OPT':
        case 'PAIR_DEPT_PAIR_RETRY':
            return { text: '准备中', level: 'warning' };
        default:
            return { text: '未配对', level: 'idle' };
    }
});

const tunnelDisplay = computed((): { text: string; level: StatusLevel } => {
    switch (tunnelStatus.value) {
        case 'online': return { text: '已连接', level: 'active' };
        case 'offline': return { text: '未连接', level: 'error' };
        default: return { text: '未知', level: 'idle' };
    }
});

interface StatusItem {
    label: string;
    value: string;
    level: StatusLevel;
    pulse?: boolean;
}

const statusItems = computed((): StatusItem[] => {
    const s = adbStatus.value;
    return [
        { label: '配对', value: s ? pairStateDisplay.value.text : '--', level: s ? pairStateDisplay.value.level : 'idle', pulse: s?.isPairRunning },
        { label: 'WiFi调试', value: s ? (s.wifiDebugEnabled ? '已开启' : '未开启') : '--', level: s ? (s.wifiDebugEnabled ? 'active' : 'idle') : 'idle' },
        { label: '本地服务', value: s ? (s.localServiceAlive ? '运行中' : '未运行') : '--', level: s ? (s.localServiceAlive ? 'active' : 'error') : 'idle' },
        { label: 'ADB', value: s ? (s.adbDeployEnabled ? '已部署' : '未部署') : '--', level: s ? (s.adbDeployEnabled ? 'active' : 'idle') : 'idle' },
        { label: '端口', value: s?.debugPort ? String(s.debugPort) : '-', level: s?.debugPort ? 'info' : 'idle' },
        { label: 'frpc', value: tunnelDisplay.value.text, level: tunnelDisplay.value.level },
    ];
});
</script>

<template>
    <div class="status-bar">
        <div
            v-for="item in statusItems"
            :key="item.label"
            class="status-cell"
        >
            <span
                class="led"
                :class="[`led--${item.level}`, { 'led--pulse': item.pulse }]"
            />
            <span class="cell-label">{{ item.label }}</span>
            <span class="cell-value" :class="`val--${item.level}`">{{ item.value }}</span>
        </div>
        <button
            class="refresh-trigger"
            :class="{ spinning: adbStatusLoading }"
            :disabled="adbStatusLoading"
            @click="fetchAdbStatus"
        >
            <NIcon :component="RefreshOutline" :size="13" />
        </button>
    </div>
</template>

<style scoped>
.status-bar {
    display: flex;
    align-items: center;
    gap: 2px;
    flex: 1;
    justify-content: center;
    background: #f8fafc;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    padding: 4px 8px;
    margin: 0 16px;
}

.status-cell {
    display: flex;
    align-items: center;
    gap: 5px;
    padding: 3px 8px;
    border-radius: 6px;
    transition: background 0.2s;
    cursor: default;
    white-space: nowrap;
}

.status-cell:hover {
    background: #e2e8f0;
}

.led {
    width: 6px;
    height: 6px;
    border-radius: 50%;
    flex-shrink: 0;
    transition: background 0.3s, box-shadow 0.3s;
}

.led--active {
    background: #22c55e;
    box-shadow: 0 0 5px rgba(34, 197, 94, 0.5);
}

.led--warning {
    background: #f59e0b;
    box-shadow: 0 0 5px rgba(245, 158, 11, 0.4);
}

.led--error {
    background: #ef4444;
    box-shadow: 0 0 5px rgba(239, 68, 68, 0.4);
}

.led--info {
    background: #3b82f6;
    box-shadow: 0 0 5px rgba(59, 130, 246, 0.3);
}

.led--idle {
    background: #cbd5e1;
    box-shadow: none;
}

.led--pulse {
    animation: led-pulse 1.2s ease-in-out infinite;
}

@keyframes led-pulse {
    0%, 100% { opacity: 1; transform: scale(1); }
    50% { opacity: 0.3; transform: scale(0.7); }
}

.cell-label {
    font-size: 11px;
    color: #64748b;
    font-weight: 500;
}

.cell-value {
    font-size: 11px;
    font-weight: 600;
}

.val--active { color: #16a34a; }
.val--warning { color: #d97706; }
.val--error { color: #dc2626; }
.val--info { color: #2563eb; }
.val--idle { color: #94a3b8; }

.refresh-trigger {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 22px;
    height: 22px;
    border: none;
    background: transparent;
    border-radius: 6px;
    color: #94a3b8;
    cursor: pointer;
    transition: all 0.2s;
    flex-shrink: 0;
    margin-left: 2px;
}

.refresh-trigger:hover {
    background: #e2e8f0;
    color: #475569;
}

.refresh-trigger:active {
    transform: scale(0.9);
}

.refresh-trigger.spinning {
    animation: spin 0.8s linear infinite;
    color: #3b82f6;
}

.refresh-trigger:disabled {
    cursor: not-allowed;
    opacity: 0.5;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

@media (max-width: 1200px) {
    .status-bar {
        margin: 0 8px;
        padding: 3px 4px;
        gap: 1px;
    }
    .status-cell { padding: 2px 5px; }
    .cell-label { display: none; }
}

@media (max-width: 768px) {
    .status-bar { display: none; }
}
</style>
