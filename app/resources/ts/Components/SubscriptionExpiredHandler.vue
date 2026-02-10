<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { router } from '@inertiajs/vue3';
import { CalendarOutline } from '@vicons/ionicons5';
import StatusModal from './StatusModal.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

export interface SubscriptionExpiredDetail {
    title?: string;
    content?: string;
    positiveText?: string;
}

const defaultTitle = '订阅已过期';
const defaultContent = '您的账号订阅已过期，无法继续使用。请联系管理员续费。点击确定将退出登录。';
const defaultPositiveText = '确定退出';

const { userRoute } = useAdminBasePath();
const visible = ref(false);
const detail = ref<SubscriptionExpiredDetail>({});

function showExpiredDialog(event: Event) {
    const d = (event as CustomEvent<SubscriptionExpiredDetail>).detail ?? {};
    detail.value = {
        title: d.title ?? defaultTitle,
        content: d.content ?? defaultContent,
        positiveText: d.positiveText ?? defaultPositiveText,
    };
    visible.value = true;
}

function handleConfirm() {
    router.post(userRoute('/logout'));
}

onMounted(() => {
    window.addEventListener('subscription-expired', showExpiredDialog);
});

onUnmounted(() => {
    window.removeEventListener('subscription-expired', showExpiredDialog);
});
</script>

<template>
    <StatusModal
        v-model:show="visible"
        variant="amber"
        :icon="CalendarOutline"
        :mask-closable="false"
        :closable="false"
        :title="detail.title"
        :content="detail.content"
        :positive-text="detail.positiveText"
        @confirm="handleConfirm"
    />
</template>
