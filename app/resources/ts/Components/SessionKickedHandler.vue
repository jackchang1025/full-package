<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { LogOutOutline } from '@vicons/ionicons5';
import StatusModal from './StatusModal.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface SessionKickedDetail {
    guard?: string;
}

const { userRoute, adminRoute } = useAdminBasePath();
const visible = ref(false);
const kickedGuard = ref<string>('web');

function showKickedDialog(event: Event) {
    const d = (event as CustomEvent<SessionKickedDetail>).detail ?? {};
    kickedGuard.value = d.guard ?? 'web';
    visible.value = true;
}

function handleConfirm() {
    const loginUrl = kickedGuard.value === 'admin'
        ? adminRoute('/login')
        : userRoute('/login');
    window.location.href = loginUrl;
}

onMounted(() => {
    window.addEventListener('session-kicked', showKickedDialog);
});

onUnmounted(() => {
    window.removeEventListener('session-kicked', showKickedDialog);
});
</script>

<template>
    <StatusModal
        v-model:show="visible"
        variant="amber"
        :icon="LogOutOutline"
        :mask-closable="false"
        :closable="false"
        title="会话已失效"
        content="您的账号已在其他设备登录，当前会话已被强制下线。请重新登录。"
        positive-text="重新登录"
        @confirm="handleConfirm"
    />
</template>
