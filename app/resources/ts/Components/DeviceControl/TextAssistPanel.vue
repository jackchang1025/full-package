<script setup lang="ts">
import { ref } from 'vue';
import {
    NCard,
    NButton,
    NInput,
    NIcon,
    NSpace,
} from 'naive-ui';
import {
    ClipboardOutline,
    SendOutline,
    DocumentTextOutline,
} from '@vicons/ionicons5';

interface Emits {
    (e: 'paste', text: string): void;
    (e: 'showKeyboard'): void;
    (e: 'hideKeyboard'): void;
}

const emit = defineEmits<Emits>();

const pasteText = ref('');
const quickTexts = [
    '验证码',
    '确认',
    '取消',
    '下一步',
    '完成',
    '同意',
];

const handlePaste = () => {
    if (pasteText.value.trim()) {
        emit('paste', pasteText.value);
        pasteText.value = '';
    }
};

const handleQuickPaste = (text: string) => {
    emit('paste', text);
};
</script>

<template>
    <NCard size="small" class="text-assist-card">
        <template #header>
            <div class="card-header">
                <NIcon :component="DocumentTextOutline" size="16" />
                <span>文字辅助</span>
            </div>
        </template>

        <div class="assist-content">
            <!-- 粘贴输入 -->
            <div class="paste-row">
                <NInput
                    v-model:value="pasteText"
                    placeholder="输入文本..."
                    size="small"
                    @keyup.enter="handlePaste"
                />
                <NButton
                    size="small"
                    type="primary"
                    :disabled="!pasteText.trim()"
                    @click="handlePaste"
                >
                    <template #icon>
                        <NIcon :component="SendOutline" />
                    </template>
                </NButton>
            </div>

            <!-- 快捷文本 -->
            <div class="quick-texts">
                <NButton
                    v-for="text in quickTexts"
                    :key="text"
                    size="tiny"
                    quaternary
                    class="quick-btn"
                    @click="handleQuickPaste(text)"
                >
                    {{ text }}
                </NButton>
            </div>

            <!-- 键盘控制 -->
            <div class="keyboard-controls">
                <NButton size="tiny" block @click="emit('showKeyboard')">
                    显示键盘
                </NButton>
                <NButton size="tiny" block @click="emit('hideKeyboard')">
                    隐藏键盘
                </NButton>
            </div>
        </div>
    </NCard>
</template>

<style scoped>
.text-assist-card {
    background: white;
}

.text-assist-card :deep(.n-card__header) {
    padding: 12px 16px;
    border-bottom: 1px solid #f1f5f9;
}

.card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 14px;
    color: #1e293b;
}

.assist-content {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.paste-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.quick-texts {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
}

.quick-btn {
    font-size: 11px;
    padding: 4px 8px;
    background: #f1f5f9;
    border-radius: 6px;
    transition: all 0.2s ease;
}

.quick-btn:hover {
    background: #e2e8f0;
    color: #10B981;
}

.keyboard-controls {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 8px;
}
</style>
