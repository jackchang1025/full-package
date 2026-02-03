<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { NModal, NButton } from 'naive-ui';
import { ShieldCheckmarkOutline } from '@vicons/ionicons5';

export interface PermissionDeniedDetail {
    title?: string;
    content?: string;
    positiveText?: string;
}

const defaultTitle = '无操作权限';
const defaultContent = '您没有执行该操作的权限。如需开通请联系管理员。';
const defaultPositiveText = '知道了';

const visible = ref(false);
const detail = ref<PermissionDeniedDetail>({});

function showPermissionDeniedDialog(event: Event) {
    const d = (event as CustomEvent<PermissionDeniedDetail>).detail ?? {};
    detail.value = {
        title: d.title ?? defaultTitle,
        content: d.content ?? defaultContent,
        positiveText: d.positiveText ?? defaultPositiveText,
    };
    visible.value = true;
}

function handleConfirm() {
    visible.value = false;
}

onMounted(() => {
    window.addEventListener('permission-denied', showPermissionDeniedDialog);
});

onUnmounted(() => {
    window.removeEventListener('permission-denied', showPermissionDeniedDialog);
});
</script>

<template>
    <NModal
        v-model:show="visible"
        :mask-closable="true"
        :closable="true"
        transform-origin="center"
        class="status-modal status-modal--denied"
    >
        <div class="status-modal__card">
            <div class="status-modal__icon-wrap status-modal__icon-wrap--denied">
                <ShieldCheckmarkOutline class="status-modal__icon" />
            </div>
            <h2 class="status-modal__title">{{ detail.title }}</h2>
            <p class="status-modal__content">{{ detail.content }}</p>
            <NButton
                type="primary"
                size="large"
                block
                class="status-modal__btn"
                @click="handleConfirm"
            >
                {{ detail.positiveText }}
            </NButton>
        </div>
    </NModal>
</template>

<style scoped>
.status-modal :deep(.n-modal) {
    max-width: 420px;
    width: calc(100vw - 32px);
    padding: 0;
    border-radius: 20px;
    overflow: hidden;
    box-shadow:
        0 24px 48px -12px rgba(0, 0, 0, 0.18),
        0 0 0 1px rgba(0, 0, 0, 0.04);
}

.status-modal :deep(.n-modal__content) {
    padding: 0;
    background: transparent;
}

.status-modal__card {
    padding: 40px 32px 32px;
    background: linear-gradient(180deg, #fafbff 0%, #f0f4ff 100%);
    border-radius: 20px;
    text-align: center;
    position: relative;
    animation: status-modal-in 0.32s cubic-bezier(0.34, 1.56, 0.64, 1);
}

.status-modal__card::before {
    content: '';
    position: absolute;
    inset: 0;
    border-radius: inherit;
    padding: 1px;
    background: linear-gradient(145deg, rgba(67, 56, 202, 0.15) 0%, transparent 50%);
    -webkit-mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    -webkit-mask-composite: xor;
    mask-composite: exclude;
    pointer-events: none;
}

.status-modal__icon-wrap {
    width: 64px;
    height: 64px;
    margin: 0 auto 20px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.status-modal__icon-wrap--denied {
    background: linear-gradient(145deg, #4f46e5 0%, #3730a3 100%);
    color: #fff;
    box-shadow: 0 8px 24px rgba(79, 70, 229, 0.35);
}

.status-modal__icon {
    width: 32px;
    height: 32px;
}

.status-modal__title {
    font-family: var(--font-sans), ui-sans-serif, system-ui, sans-serif;
    font-size: 1.25rem;
    font-weight: 600;
    color: #1e1b4b;
    margin: 0 0 12px;
    letter-spacing: -0.02em;
}

.status-modal__content {
    font-size: 0.9375rem;
    line-height: 1.6;
    color: #4338ca;
    margin: 0 0 28px;
    max-width: 320px;
    margin-left: auto;
    margin-right: auto;
}

@keyframes status-modal-in {
    from {
        opacity: 0;
        transform: scale(0.92);
    }
    to {
        opacity: 1;
        transform: scale(1);
    }
}

.status-modal__btn {
    font-weight: 600;
    border-radius: 12px;
    height: 44px;
    --n-color: #4f46e5;
    --n-color-hover: #4338ca;
    --n-color-pressed: #3730a3;
}
</style>
