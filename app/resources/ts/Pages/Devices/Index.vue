<script setup lang="ts">
import { computed } from 'vue';
import { Head } from '@inertiajs/vue3';
import { NTag, NIcon } from 'naive-ui';
import { WifiOutline } from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import DeviceListContent from '@/Components/DeviceList/DeviceListContent.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const props = defineProps<{
    devices: { data: unknown[]; current_page: number; last_page: number; per_page: number; total: number };
    stats: { total: number; online: number; offline: number };
    canControl?: boolean;
    showOfflineDevices?: boolean;
}>();

const { userRoute } = useAdminBasePath();
const devicesBasePath = computed(() => userRoute('/devices'));

const { connectionState } = useGlobalWebSocket();
const isConnected = computed(() => connectionState.value === 'connected');
const isConnecting = computed(() => connectionState.value === 'connecting' || connectionState.value === 'reconnecting');
</script>

<template>
    <Head title="设备管理" />
    <AuthenticatedLayout>
        <template #header-title>
            <div class="header-with-status">
                <span>设备管理</span>
                <NTag v-if="isConnected" type="success" size="small" round>
                    <template #icon>
                        <NIcon :component="WifiOutline" />
                    </template>
                    实时
                </NTag>
                <NTag v-else-if="isConnecting" type="warning" size="small" round>
                    连接中...
                </NTag>
                <NTag v-else type="default" size="small" round>
                    离线
                </NTag>
            </div>
        </template>

        <DeviceListContent
            :devices="(props.devices as { data: unknown[]; current_page: number; last_page: number; per_page: number; total: number })"
            :stats="props.stats"
            :base-path="devicesBasePath"
            :show-user-column="false"
            :allow-control="true"
            :allow-delete="true"
            :allow-edit-remark="true"
            :can-control="props.canControl ?? true"
            :show-offline-devices="props.showOfflineDevices ?? true"
        />
    </AuthenticatedLayout>
</template>

<style scoped>
.header-with-status {
    display: flex;
    align-items: center;
    gap: 12px;
}
</style>
