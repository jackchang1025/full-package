<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import {
    NButton,
    NGrid,
    NGridItem,
    NIcon,
    NTag,
    NSpace,
    NSpin,
    NDivider,
    NEmpty,
    useMessage,
} from 'naive-ui';
import {
    WifiOutline,
    SettingsOutline,
    CloudUploadOutline,
    SyncOutline,
    CodeSlashOutline,
    RocketOutline,
    TerminalOutline,
    InformationCircleOutline,
    RefreshOutline,
    CheckmarkCircleOutline,
    CloseCircleOutline,
    ShieldCheckmarkOutline,
    HammerOutline,
} from '@vicons/ionicons5';
import axios from 'axios';

interface AdbStatusData {
    pairCompleted: boolean;
    adbDeployEnabled: boolean;
    localServiceAlive: boolean;
    debugPort: number;
    wifiDebugEnabled: boolean;
    isPairRunning: boolean;
    pairState: string;
}

type PermissionMap = Record<string, boolean>;

const PERMISSION_LABELS: Record<string, string> = {
    accessibility: '无障碍服务',
    overlay: '悬浮窗',
    notification: '通知',
    photo: '图片访问',
    contacts: '通讯录',
    readSms: '读取短信',
    sendSms: '发送短信',
    camera: '相机',
    microphone: '麦克风',
    storage: '存储',
    appList: '应用列表',
};

interface Props {
    deviceId: string;
}

const props = defineProps<Props>();
const message = useMessage();

const activeSection = ref<'actions' | 'permissions'>('actions');

const isProcessing = ref(false);
const lastCommand = ref<{ success: boolean; message: string; command: string } | null>(null);

const adbStatus = ref<AdbStatusData | null>(null);
const adbStatusLoading = ref(false);
const adbStatusError = ref<string | null>(null);

const permissions = ref<PermissionMap | null>(null);
const permissionsLoading = ref(false);
const permissionsError = ref<string | null>(null);

async function fetchAdbStatus() {
    try {
        adbStatusLoading.value = true;
        const { data } = await axios.get(`/api/devices/${props.deviceId}/adb-status`);
        // Response: { success, data: { code, success, data: { pairCompleted, ... } } }
        const apkResp = data.data;
        if (data.success && apkResp?.data) {
            adbStatus.value = apkResp.data;
        }
    } catch (e: unknown) {
        console.error('获取 ADB 状态失败', e);
        adbStatusError.value = e instanceof Error ? e.message : '网络错误';
    } finally {
        adbStatusLoading.value = false;
    }
}

async function fetchPermissions() {
    try {
        permissionsLoading.value = true;
        permissionsError.value = null;
        const { data } = await axios.get(`/api/devices/${props.deviceId}/permissions`);
        // Response: { success, data: { code, success, data: { accessibility: true, ... } } }
        const apkResp = data.data;
        if (data.success && apkResp?.success && apkResp.data) {
            permissions.value = apkResp.data;
        } else {
            permissionsError.value = apkResp?.msg || data.error || '获取失败';
        }
    } catch (e: unknown) {
        const errMsg = e instanceof Error ? e.message : '网络错误';
        permissionsError.value = errMsg;
    } finally {
        permissionsLoading.value = false;
    }
}

onMounted(() => {
    fetchAdbStatus();
});

const isAdbConnected = computed(() =>
    adbStatus.value?.pairCompleted && adbStatus.value?.localServiceAlive
);

const pairStateDisplay = computed(() => {
    switch (adbStatus.value?.pairState) {
        case 'PAIR_DEPT_PAIRING':
            return { text: '配对中...', type: 'warning' as const };
        case 'PAIR_DEPT_PAIR_SUCCESS':
        case 'PAIR_DEPT_PREPARE_FINISH':
        case 'PAIR_DEPT_PAIR_FINISH':
            return { text: '已连接', type: 'success' as const };
        case 'PAIR_DEPT_PAIR_FAIL':
            return { text: '配对失败', type: 'error' as const };
        case 'PAIR_DEPT_PAIR_LEAVE_DEV_OPT':
        case 'PAIR_DEPT_PAIR_RETRY':
            return { text: '准备中...', type: 'info' as const };
        default:
            return { text: '未配对', type: 'default' as const };
    }
});

const statusItems = computed(() => {
    if (!adbStatus.value) return [];
    const s = adbStatus.value;
    return [
        { label: '配对状态', value: pairStateDisplay.value.text, type: pairStateDisplay.value.type },
        { label: 'WiFi 调试', value: s.wifiDebugEnabled ? '已开启' : '未开启', type: s.wifiDebugEnabled ? 'success' as const : 'default' as const },
        { label: '本地服务', value: s.localServiceAlive ? '运行中' : '未运行', type: s.localServiceAlive ? 'success' as const : 'error' as const },
        { label: 'ADB 部署', value: s.adbDeployEnabled ? '已启用' : '未启用', type: s.adbDeployEnabled ? 'success' as const : 'default' as const },
        { label: '调试端口', value: s.debugPort ? String(s.debugPort) : '-', type: s.debugPort ? 'info' as const : 'default' as const },
    ];
});

const permissionItems = computed(() => {
    if (!permissions.value) return [];
    return Object.entries(permissions.value).map(([key, granted]) => ({
        key,
        label: PERMISSION_LABELS[key] ?? key,
        granted,
    }));
});

const permissionSummary = computed(() => {
    if (!permissions.value) return null;
    const entries = Object.values(permissions.value);
    const granted = entries.filter(Boolean).length;
    return { granted, total: entries.length };
});

const sendAdbCommand = async (command: string, label: string) => {
    isProcessing.value = true;
    lastCommand.value = null;
    try {
        const { data } = await axios.post(
            `/api/devices/${props.deviceId}/adb-command`,
            { command, params: {} }
        );
        lastCommand.value = {
            success: data.success,
            message: data.data?.msg || data.error || label,
            command: label,
        };
        if (data.success) {
            message.success(`${label} 已发送`);
        } else {
            message.error(data.error || '命令发送失败');
        }
    } catch (e: unknown) {
        const errMsg = e instanceof Error
            ? (e as any).response?.data?.error || (e as any).response?.data?.message || e.message
            : 'Unknown error';
        lastCommand.value = { success: false, message: errMsg, command: label };
        message.error(`请求失败: ${errMsg}`);
    } finally {
        isProcessing.value = false;
    }
};

function handleSwitchToPermissions() {
    activeSection.value = 'permissions';
    if (!permissions.value && !permissionsLoading.value) {
        fetchPermissions();
    }
}
</script>

<template>
    <div class="permission-tab">
        <!-- Section 切换按钮 -->
        <div class="tab-header">
            <NSpace>
                <NButton
                    :type="activeSection === 'actions' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'actions'"
                >
                    <template #icon><NIcon :component="HammerOutline" /></template>
                    操作
                </NButton>
                <NButton
                    :type="activeSection === 'permissions' ? 'primary' : 'default'"
                    size="small"
                    @click="handleSwitchToPermissions"
                >
                    <template #icon><NIcon :component="ShieldCheckmarkOutline" /></template>
                    权限
                    <NTag
                        v-if="permissionSummary"
                        size="small"
                        round
                        :type="permissionSummary.granted === permissionSummary.total ? 'success' : 'warning'"
                        style="margin-left: 6px;"
                    >
                        {{ permissionSummary.granted }}/{{ permissionSummary.total }}
                    </NTag>
                </NButton>
            </NSpace>
        </div>

        <NDivider style="margin: 8px 0" />

        <!-- Section: 操作 -->
        <div v-if="activeSection === 'actions'" class="section">
            <!-- ADB 状态概览 -->
            <div class="status-section">
                <div class="section-header">
                    <span class="section-title">ADB 状态</span>
                    <NButton
                        size="tiny"
                        quaternary
                        :loading="adbStatusLoading"
                        @click="fetchAdbStatus"
                        style="margin-left: auto;"
                    >
                        <template #icon><NIcon :component="RefreshOutline" /></template>
                        刷新
                    </NButton>
                </div>
                <NSpin :show="adbStatusLoading && !adbStatus" size="small">
                    <div v-if="adbStatus" class="status-grid">
                        <div
                            v-for="item in statusItems"
                            :key="item.label"
                            class="status-item"
                        >
                            <span class="status-label">{{ item.label }}</span>
                            <NTag :type="item.type" size="small" round>
                                {{ item.value }}
                            </NTag>
                        </div>
                    </div>
                    <div v-else class="status-empty">等待状态加载...</div>
                </NSpin>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- ADB 配对 -->
            <div class="action-section">
                <div class="section-header">
                    <NIcon :component="WifiOutline" size="18" />
                    <span class="section-title">ADB 配对</span>
                    <NTag v-if="adbStatus?.isPairRunning" type="warning" size="small" round>配对进行中</NTag>
                </div>
                <div class="action-card">
                    <NButton block type="primary" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('START_PAIRING', '手动配对')">
                        <template #icon><NIcon :component="WifiOutline" /></template>
                        手动配对
                    </NButton>
                    <p class="action-desc">自动打开开发者选项 → 无线调试 → 读取配对码 → SPAKE2 密钥交换完成配对。需要设备已开启开发者模式。</p>
                </div>
                <div class="action-card">
                    <NButton block type="info" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('AUTO_WIRELESS_PAIRING', '自动配对')">
                        <template #icon><NIcon :component="SyncOutline" /></template>
                        自动配对
                    </NButton>
                    <p class="action-desc">从头开始：自动打开设置 → 连续点击版本号激活开发者模式 → 开启无线调试 → 配对。适用于设备尚未开启开发者选项的场景。</p>
                </div>
                <div class="action-card">
                    <NButton block type="warning" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('DIRECT_PAIR', '直接配对')">
                        <template #icon><NIcon :component="CodeSlashOutline" /></template>
                        直接配对
                    </NButton>
                    <p class="action-desc">假定无线调试配对弹窗已在屏幕上显示，直接读取配对码和端口执行配对。最快速，但需要手动先打开配对弹窗。</p>
                </div>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 设置操作 -->
            <div class="action-section">
                <div class="section-header">
                    <NIcon :component="SettingsOutline" size="18" />
                    <span class="section-title">设置操作</span>
                </div>
                <NGrid :cols="2" :x-gap="10" :y-gap="10">
                    <NGridItem>
                        <NButton block :disabled="isProcessing" @click="sendAdbCommand('OPEN_WIFI_DEBUG_SETTINGS', '开发者选项')">
                            <template #icon><NIcon :component="SettingsOutline" /></template>
                            开发者选项
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton block :disabled="isProcessing" @click="sendAdbCommand('OPEN_ABOUT_PHONE', '关于手机')">
                            <template #icon><NIcon :component="InformationCircleOutline" /></template>
                            关于手机
                        </NButton>
                    </NGridItem>
                </NGrid>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 部署操作 -->
            <div class="action-section">
                <div class="section-header">
                    <NIcon :component="CloudUploadOutline" size="18" />
                    <span class="section-title">部署操作</span>
                </div>
                <div class="action-card">
                    <NButton block type="error" :loading="isProcessing" :disabled="isProcessing" @click="sendAdbCommand('FULL_DEPLOY', '完整部署')">
                        <template #icon><NIcon :component="RocketOutline" /></template>
                        完整部署
                    </NButton>
                    <p class="action-desc">一键执行完整流程：重置配对状态 → ADB WiFi 配对 → 部署 local-service 到 /data/local/tmp/ → 初始化配置。完成后设备将拥有 shell 级操作能力（录屏、截屏、输入监听、系统设置修改）。</p>
                </div>
                <div class="action-card">
                    <NButton block type="warning" :loading="isProcessing" :disabled="isProcessing" @click="sendAdbCommand('DEPLOY_LOCAL_SERVICE', '部署 Local-Service')">
                        <template #icon><NIcon :component="TerminalOutline" /></template>
                        部署 Local-Service
                    </NButton>
                    <p class="action-desc">仅部署 local-service（需已完成 ADB 配对）。将 Go 二进制推送到设备并以 shell 权限启动，监听端口 7912 提供系统级 API。</p>
                </div>
            </div>

            <!-- 命令结果 -->
            <Transition name="fade">
                <div v-if="lastCommand" class="command-result">
                    <NDivider style="margin: 12px 0" />
                    <div class="result-row">
                        <NTag :type="lastCommand.success ? 'success' : 'error'" size="small">
                            {{ lastCommand.command }}
                        </NTag>
                        <span class="result-message">{{ lastCommand.message }}</span>
                    </div>
                </div>
            </Transition>
        </div>

        <!-- Section: 权限列表 -->
        <div v-else-if="activeSection === 'permissions'" class="section">
            <div class="section-header">
                <span class="section-title">设备权限状态</span>
                <NTag
                    v-if="permissionSummary"
                    size="small"
                    round
                    :type="permissionSummary.granted === permissionSummary.total ? 'success' : 'warning'"
                >
                    已获取 {{ permissionSummary.granted }} / {{ permissionSummary.total }}
                </NTag>
                <NButton
                    size="small"
                    :loading="permissionsLoading"
                    @click="fetchPermissions"
                    style="margin-left: auto;"
                >
                    <template #icon><NIcon :component="RefreshOutline" /></template>
                    刷新权限
                </NButton>
            </div>

            <NSpin :show="permissionsLoading && !permissions" size="small">
                <div v-if="permissions" class="perm-list">
                    <div
                        v-for="item in permissionItems"
                        :key="item.key"
                        class="perm-row"
                    >
                        <span class="perm-name">{{ item.label }}</span>
                        <span :class="item.granted ? 'perm-granted' : 'perm-denied'">
                            {{ item.granted ? '已授权' : '未授权' }}
                        </span>
                    </div>
                </div>
                <NEmpty v-else-if="permissionsError" :description="permissionsError">
                    <template #extra>
                        <NButton size="small" @click="fetchPermissions">重试</NButton>
                    </template>
                </NEmpty>
                <div v-else class="status-empty">加载中...</div>
            </NSpin>
        </div>
    </div>
</template>

<style scoped>
.permission-tab {
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.tab-header {
    display: flex;
    align-items: center;
}

.section {
    display: flex;
    flex-direction: column;
}

.section-header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 12px;
}

.section-title {
    font-size: 14px;
    font-weight: 600;
    color: #334155;
}

.status-section {
    display: flex;
    flex-direction: column;
}

.status-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
    gap: 10px;
}

.status-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4px;
    padding: 10px 8px;
    background: #f8fafc;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
}

.status-label {
    font-size: 12px;
    color: #64748b;
}

.status-empty {
    text-align: center;
    padding: 20px;
    color: #94a3b8;
    font-size: 13px;
}

.action-section {
    display: flex;
    flex-direction: column;
}

.action-card {
    margin-bottom: 10px;
}

.action-desc {
    margin: 4px 0 0;
    padding: 0 4px;
    font-size: 12px;
    line-height: 1.5;
    color: #94a3b8;
}

.command-result {
    display: flex;
    flex-direction: column;
}

.result-row {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 12px;
    background: #f8fafc;
    border-radius: 8px;
}

.result-message {
    font-size: 13px;
    color: #475569;
}

/* 权限列表 */
.perm-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    overflow: hidden;
}

.perm-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 16px;
    font-size: 13px;
    background: white;
}

.perm-row:nth-child(even) {
    background: #f8fafc;
}

.perm-name {
    color: #334155;
    font-weight: 500;
}

.perm-granted {
    color: #16a34a;
    font-weight: 500;
}

.perm-denied {
    color: #dc2626;
    font-weight: 500;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
