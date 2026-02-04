<script setup lang="ts">
import { Head } from '@inertiajs/vue3';
import { PeopleOutline, PhonePortraitOutline, CloudDownloadOutline, TodayOutline, CalendarOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import StatsGrid, { type StatCard } from '@/Components/Dashboard/StatsGrid.vue';

interface Props {
    stats: {
        totalUsers: number;
        totalDevices: number;
        totalBuilds: number;
        todayInstalled: number;
        monthInstalled: number;
    };
}

const props = defineProps<Props>();

const statCards: StatCard[] = [
    { key: 'totalUsers', title: '用户总数', icon: PeopleOutline, route: '/admin/users', color: '#059669' },
    { key: 'totalDevices', title: '设备总数', icon: PhonePortraitOutline, route: '/admin/devices', color: '#3B82F6' },
    { key: 'todayInstalled', title: '今日安装', icon: TodayOutline, route: '/admin/devices', color: '#F59E0B' },
    { key: 'monthInstalled', title: '本月安装', icon: CalendarOutline, route: '/admin/devices', color: '#EC4899' },
    { key: 'totalBuilds', title: 'APK 构建', icon: CloudDownloadOutline, route: '/admin/builds', color: '#8B5CF6' },
];
</script>

<template>
    <Head title="总管理后台 - 控制台" />
    <AdminLayout>
        <template #header-title>控制台</template>
        <div class="admin-dashboard">
            <header class="admin-dashboard-header">
                <h1 class="admin-dashboard-title">总管理后台</h1>
                <p class="admin-dashboard-subtitle">管理用户、设备与 APK 构建</p>
            </header>
            <StatsGrid :stats="stats" :cards="statCards" variant="admin" clickable />
        </div>
    </AdminLayout>
</template>

<style scoped>
.admin-dashboard {
    max-width: 960px;
}

.admin-dashboard-header {
    margin-bottom: 32px;
}

.admin-dashboard-title {
    font-size: 26px;
    font-weight: 700;
    letter-spacing: -0.03em;
    color: var(--admin-text, #1a1d21);
    margin: 0 0 8px;
}

.admin-dashboard-subtitle {
    font-size: 15px;
    color: var(--admin-text-muted, #6b7280);
    margin: 0;
}
</style>
