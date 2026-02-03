<script setup lang="ts">
import { computed } from 'vue';
import { Head } from '@inertiajs/vue3';
import { NTag, NIcon } from 'naive-ui';
import { WifiOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import DeviceListContent from '@/Components/DeviceList/DeviceListContent.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const props = defineProps<{
    devices: {
        data: unknown[];
        current_page: number;
        last_page: number;
        per_page: number;
        total: number;
    };
    stats: { total: number; online: number; offline: number };
    filters?: { search?: string };
}>();

const { connectionState } = useGlobalWebSocket();
const isConnected = computed(() => connectionState.value === 'connected');
const isConnecting = computed(() => connectionState.value === 'connecting' || connectionState.value === 'reconnecting');
</script>

<template>
    <Head title="设备管理" />
    <AdminLayout>
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
            base-path="/admin/devices"
            :show-user-column="true"
            :allow-control="true"
            :allow-delete="true"
            :allow-edit-remark="true"
            :allow-edit-link="true"
            :filters="props.filters ?? {}"
        />
    </AdminLayout>
</template>

<style scoped>
.header-with-status {
    display: flex;
    align-items: center;
    gap: 12px;
}
</style>
