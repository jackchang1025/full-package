<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { ShieldCheckmarkOutline } from '@vicons/ionicons5';
import StatusModal from './StatusModal.vue';

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

onMounted(() => {
    window.addEventListener('permission-denied', showPermissionDeniedDialog);
});

onUnmounted(() => {
    window.removeEventListener('permission-denied', showPermissionDeniedDialog);
});
</script>

<template>
    <StatusModal
        v-model:show="visible"
        variant="indigo"
        :icon="ShieldCheckmarkOutline"
        :title="detail.title"
        :content="detail.content"
        :positive-text="detail.positiveText"
    />
</template>
