<script setup lang="ts">
/**
 * 通用状态弹框组件。
 *
 * 支持三种颜色主题：
 * - indigo（默认）：权限拒绝、访问受限等
 * - amber：警告、过期、配额不足等
 * - danger：删除、不可逆操作等
 *
 * 用法：
 *   <StatusModal v-model:show="visible" title="标题" content="内容" />
 *   <StatusModal v-model:show="visible" variant="amber" :icon="CalendarOutline" />
 *   <StatusModal v-model:show="visible" variant="danger" title="确认删除"
 *       content="此操作不可撤销" positive-text="确定删除" negative-text="取消"
 *       @confirm="doDelete" />
 */
import { computed } from 'vue';
import { NModal, NButton } from 'naive-ui';
import { ShieldCheckmarkOutline } from '@vicons/ionicons5';

export type StatusModalVariant = 'indigo' | 'amber' | 'danger';

const props = withDefaults(defineProps<{
    show: boolean;
    title?: string;
    content?: string;
    positiveText?: string;
    negativeText?: string;
    variant?: StatusModalVariant;
    icon?: any;
    maskClosable?: boolean;
    closable?: boolean;
}>(), {
    title: '',
    content: '',
    positiveText: '知道了',
    negativeText: '',
    variant: 'indigo',
    icon: undefined,
    maskClosable: true,
    closable: true,
});

const emit = defineEmits<{
    'update:show': [value: boolean];
    'confirm': [];
    'cancel': [];
}>();

const iconComponent = computed(() => props.icon ?? ShieldCheckmarkOutline);

function handleConfirm() {
    emit('update:show', false);
    emit('confirm');
}

function handleCancel() {
    emit('update:show', false);
    emit('cancel');
}
</script>

<template>
    <NModal
        :show="show"
        :mask-closable="maskClosable"
        :closable="closable"
        transform-origin="center"
        class="status-modal"
        @update:show="(val: boolean) => emit('update:show', val)"
    >
        <div class="status-modal__card" :class="`status-modal__card--${variant}`">
            <div class="status-modal__icon-wrap" :class="`status-modal__icon-wrap--${variant}`">
                <component :is="iconComponent" class="status-modal__icon" />
            </div>
            <h2 class="status-modal__title" :class="`status-modal__title--${variant}`">{{ title }}</h2>
            <p class="status-modal__content" :class="`status-modal__content--${variant}`">{{ content }}</p>

            <!-- 双按钮模式（有 negativeText 时） -->
            <div v-if="negativeText" class="status-modal__btn-group">
                <NButton
                    size="large"
                    class="status-modal__btn status-modal__btn-secondary"
                    @click="handleCancel"
                >
                    {{ negativeText }}
                </NButton>
                <NButton
                    type="primary"
                    size="large"
                    class="status-modal__btn"
                    :class="`status-modal__btn--${variant}`"
                    @click="handleConfirm"
                >
                    {{ positiveText }}
                </NButton>
            </div>

            <!-- 单按钮模式（默认） -->
            <NButton
                v-else
                type="primary"
                size="large"
                block
                class="status-modal__btn"
                :class="`status-modal__btn--${variant}`"
                @click="handleConfirm"
            >
                {{ positiveText }}
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

/* ---- Card base ---- */
.status-modal__card {
    padding: 40px 32px 32px;
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
    -webkit-mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
    -webkit-mask-composite: xor;
    mask-composite: exclude;
    pointer-events: none;
}

/* ---- Indigo variant (default) ---- */
.status-modal__card--indigo {
    background: linear-gradient(180deg, #fafbff 0%, #f0f4ff 100%);
}
.status-modal__card--indigo::before {
    background: linear-gradient(145deg, rgba(67, 56, 202, 0.15) 0%, transparent 50%);
}

.status-modal__icon-wrap--indigo {
    background: linear-gradient(145deg, #4f46e5 0%, #3730a3 100%);
    color: #fff;
    box-shadow: 0 8px 24px rgba(79, 70, 229, 0.35);
}

.status-modal__title--indigo { color: #1e1b4b; }
.status-modal__content--indigo { color: #4338ca; }

.status-modal__btn--indigo {
    --n-color: #4f46e5;
    --n-color-hover: #4338ca;
    --n-color-pressed: #3730a3;
}

/* ---- Amber variant ---- */
.status-modal__card--amber {
    background: linear-gradient(180deg, #fffefb 0%, #fff9f0 100%);
}
.status-modal__card--amber::before {
    background: linear-gradient(145deg, rgba(217, 119, 6, 0.2) 0%, transparent 50%);
}

.status-modal__icon-wrap--amber {
    background: linear-gradient(145deg, #f59e0b 0%, #d97706 100%);
    color: #fff;
    box-shadow: 0 8px 24px rgba(245, 158, 11, 0.35);
}

.status-modal__title--amber { color: #1c1917; }
.status-modal__content--amber { color: #57534e; }

.status-modal__btn--amber {
    --n-color: #d97706;
    --n-color-hover: #b45309;
    --n-color-pressed: #92400e;
}

/* ---- Danger variant ---- */
.status-modal__card--danger {
    background: linear-gradient(180deg, #fffbfb 0%, #fef2f2 100%);
}
.status-modal__card--danger::before {
    background: linear-gradient(145deg, rgba(220, 38, 38, 0.15) 0%, transparent 50%);
}

.status-modal__icon-wrap--danger {
    background: linear-gradient(145deg, #ef4444 0%, #dc2626 100%);
    color: #fff;
    box-shadow: 0 8px 24px rgba(239, 68, 68, 0.35);
}

.status-modal__title--danger { color: #450a0a; }
.status-modal__content--danger { color: #991b1b; }

.status-modal__btn--danger {
    --n-color: #ef4444;
    --n-color-hover: #dc2626;
    --n-color-pressed: #b91c1c;
}

/* ---- Shared elements ---- */
.status-modal__icon-wrap {
    width: 64px;
    height: 64px;
    margin: 0 auto 20px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.status-modal__icon {
    width: 32px;
    height: 32px;
}

.status-modal__title {
    font-family: var(--font-sans), ui-sans-serif, system-ui, sans-serif;
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0 0 12px;
    letter-spacing: -0.02em;
}

.status-modal__content {
    font-size: 0.9375rem;
    line-height: 1.6;
    margin: 0 0 28px;
    max-width: 320px;
    margin-left: auto;
    margin-right: auto;
}

/* ---- 按钮组（双按钮） ---- */
.status-modal__btn-group {
    display: flex;
    gap: 12px;
}

.status-modal__btn-group .status-modal__btn {
    flex: 1;
}

.status-modal__btn-secondary {
    border-radius: 12px;
    height: 44px;
    font-weight: 600;
}

.status-modal__btn {
    font-weight: 600;
    border-radius: 12px;
    height: 44px;
}

@keyframes status-modal-in {
    from { opacity: 0; transform: scale(0.92); }
    to   { opacity: 1; transform: scale(1); }
}
</style>
