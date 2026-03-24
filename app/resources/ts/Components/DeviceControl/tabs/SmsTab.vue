<script setup lang="ts">
import { ref } from 'vue';
import {
    NDataTable,
    NButton,
    NInput,
    NEmpty,
    NSpin,
    NIcon,
    useMessage,
} from 'naive-ui';
import { RefreshOutline, SendOutline } from '@vicons/ionicons5';
import type { SmsMessage } from '@/types/device';

interface Props {
    messages: SmsMessage[];
    loading: boolean;
}

interface Emits {
    (e: 'refresh'): void;
    (e: 'sendSms', number: string, message: string): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const message = useMessage();
const smsNumber = ref('');
const smsContent = ref('');
const sending = ref(false);

const columns = [
    { title: '时间', key: 'time', width: 160 },
    { title: '号码', key: 'number', width: 140 },
    {
        title: '内容',
        key: 'message',
        ellipsis: { tooltip: true },
    },
];

const rowProps = (row: SmsMessage) => {
    return {
        style: {
            backgroundColor: row.type === 2 ? '#f0f9ff' : '#fef3f2',
        }
    };
};

const handleSend = () => {
    if (!smsNumber.value.trim()) {
        message.warning('请输入接收号码');
        return;
    }
    if (!smsContent.value.trim()) {
        message.warning('请输入短信内容');
        return;
    }
    
    sending.value = true;
    emit('sendSms', smsNumber.value.trim(), smsContent.value.trim());
    
    // 发送后清空内容，保留号码方便连续发送
    smsContent.value = '';
    setTimeout(() => {
        sending.value = false;
        message.success('短信已发送');
    }, 500);
};
</script>

<template>
    <div class="sms-tab">
        <!-- 标题栏 -->
        <div class="card-header">
            <div class="card-title">
                <span>📱 短信记录</span>
            </div>
            <NButton 
                type="primary" 
                size="small" 
                :loading="loading"
                @click="emit('refresh')"
            >
                <template #icon>
                    <NIcon><RefreshOutline /></NIcon>
                </template>
                获取
            </NButton>
        </div>

        <!-- 发送短信输入区 -->
        <div class="input-row">
            <NInput
                v-model:value="smsNumber"
                placeholder="接收号码"
                size="small"
                clearable
                style="flex: 1; min-width: 120px;"
            />
            <NInput
                v-model:value="smsContent"
                placeholder="短信内容"
                size="small"
                clearable
                style="flex: 2; min-width: 200px;"
            />
            <NButton 
                type="error" 
                size="small"
                :loading="sending"
                :disabled="!smsNumber.trim() || !smsContent.trim()"
                @click="handleSend"
            >
                <template #icon>
                    <NIcon><SendOutline /></NIcon>
                </template>
                发送
            </NButton>
        </div>

        <!-- 短信列表 -->
        <NSpin :show="loading">
            <NDataTable
                v-if="messages.length > 0"
                :columns="columns"
                :data="messages"
                :row-props="rowProps"
                :bordered="false"
                :max-height="400"
                size="small"
                striped
            />
            <NEmpty v-else description="暂无短信记录，点击「获取」按钮加载" />
        </NSpin>
    </div>
</template>

<style scoped>
.sms-tab {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 8px;
    border-bottom: 1px solid var(--n-border-color, #e0e0e0);
}

.card-title {
    font-size: 14px;
    font-weight: 600;
    color: var(--n-text-color, #333);
}

.input-row {
    display: flex;
    gap: 8px;
    align-items: center;
    flex-wrap: wrap;
}

@media (max-width: 640px) {
    .input-row {
        flex-direction: column;
    }
    
    .input-row > * {
        width: 100% !important;
        min-width: unset !important;
    }
}
</style>
