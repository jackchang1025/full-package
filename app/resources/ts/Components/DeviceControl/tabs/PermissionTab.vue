<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import {
    NButton,
    NIcon,
    NTag,
    NSpin,
    NEmpty,
} from 'naive-ui';
import { RefreshOutline, ShieldCheckmarkOutline } from '@vicons/ionicons5';
import axios from 'axios';

type PermissionMap = Record<string, boolean>;

const PERMISSION_LABELS: Record<string, string> = {
    accessibility: '无障碍服务',
    overlay: '悬浮窗',
    notification: '通知',
    photo: '图片访问',
    contacts: '通讯录',
    readSms: '读取短信',
    sendSms: '发送短信',
    camera: '相机',
    microphone: '麦克风',
    storage: '存储',
    appList: '应用列表',
};

interface Props {
    deviceId: string;
}

const props = defineProps<Props>();

const permissions = ref<PermissionMap | null>(null);
const permissionsLoading = ref(false);
const permissionsError = ref<string | null>(null);

async function fetchPermissions() {
    try {
        permissionsLoading.value = true;
        permissionsError.value = null;
        const { data } = await axios.get(`/api/devices/${props.deviceId}/permissions`);
        const apkResp = data.data;
        if (data.success && apkResp?.success && apkResp.data) {
            permissions.value = apkResp.data;
        } else {
            permissionsError.value = apkResp?.msg || data.error || '获取失败';
        }
    } catch (e: unknown) {
        permissionsError.value = e instanceof Error ? e.message : '网络错误';
    } finally {
        permissionsLoading.value = false;
    }
}

onMounted(() => {
    fetchPermissions();
});

const permissionItems = computed(() => {
    if (!permissions.value) return [];
    return Object.entries(permissions.value).map(([key, granted]) => ({
        key,
        label: PERMISSION_LABELS[key] ?? key,
        granted,
    }));
});

const permissionSummary = computed(() => {
    if (!permissions.value) return null;
    const entries = Object.values(permissions.value);
    const granted = entries.filter(Boolean).length;
    return { granted, total: entries.length };
});
</script>

<template>
    <div class="permission-tab">
        <div class="section-header">
            <NIcon :component="ShieldCheckmarkOutline" size="18" />
            <span class="section-title">设备权限状态</span>
            <NTag
                v-if="permissionSummary"
                size="small"
                round
                :type="permissionSummary.granted === permissionSummary.total ? 'success' : 'warning'"
            >
                已获取 {{ permissionSummary.granted }} / {{ permissionSummary.total }}
            </NTag>
            <NButton
                size="small"
                :loading="permissionsLoading"
                @click="fetchPermissions"
                style="margin-left: auto;"
            >
                <template #icon><NIcon :component="RefreshOutline" /></template>
                刷新权限
            </NButton>
        </div>

        <NSpin :show="permissionsLoading && !permissions" size="small">
            <div v-if="permissions" class="perm-list">
                <div
                    v-for="item in permissionItems"
                    :key="item.key"
                    class="perm-row"
                >
                    <span class="perm-name">{{ item.label }}</span>
                    <span :class="item.granted ? 'perm-granted' : 'perm-denied'">
                        {{ item.granted ? '已授权' : '未授权' }}
                    </span>
                </div>
            </div>
            <NEmpty v-else-if="permissionsError" :description="permissionsError">
                <template #extra>
                    <NButton size="small" @click="fetchPermissions">重试</NButton>
                </template>
            </NEmpty>
            <div v-else class="perm-empty">加载中...</div>
        </NSpin>
    </div>
</template>

<style scoped>
.permission-tab {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.section-header {
    display: flex;
    align-items: center;
    gap: 8px;
}

.section-title {
    font-size: 14px;
    font-weight: 600;
    color: #334155;
}

.perm-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    overflow: hidden;
}

.perm-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 16px;
    font-size: 13px;
    background: white;
}

.perm-row:nth-child(even) {
    background: #f8fafc;
}

.perm-name {
    color: #334155;
    font-weight: 500;
}

.perm-granted {
    color: #16a34a;
    font-weight: 500;
}

.perm-denied {
    color: #dc2626;
    font-weight: 500;
}

.perm-empty {
    text-align: center;
    padding: 20px;
    color: #94a3b8;
    font-size: 13px;
}
</style>
