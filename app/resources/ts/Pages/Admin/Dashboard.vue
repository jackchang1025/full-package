<script setup lang="ts">
import { Head, router } from '@inertiajs/vue3';
import { NCard, NGrid, NGi, NStatistic, NIcon, NButton } from 'naive-ui';
import { PeopleOutline, PhonePortraitOutline, CloudDownloadOutline, ArrowForwardOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';

interface Props {
    stats: {
        totalUsers: number;
        totalDevices: number;
        totalBuilds: number;
    };
}

const props = defineProps<Props>();

const statCards = [
    { title: '用户总数', value: props.stats.totalUsers, icon: PeopleOutline, route: '/admin/users', color: '#059669' },
    { title: '设备总数', value: props.stats.totalDevices, icon: PhonePortraitOutline, route: '/admin/devices', color: '#3B82F6' },
    { title: 'APK 构建', value: props.stats.totalBuilds, icon: CloudDownloadOutline, route: '/admin/builds', color: '#8B5CF6' },
];

const goTo = (route: string) => router.visit(route);
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
            <div class="admin-stats-grid">
                <div
                    v-for="(stat, i) in statCards"
                    :key="stat.title"
                    class="admin-stat-card"
                    :style="{ animationDelay: `${i * 0.08}s` }"
                    @click="goTo(stat.route)"
                >
                    <div class="admin-stat-icon" :style="{ background: stat.color + '18', color: stat.color }">
                        <NIcon :component="stat.icon" size="28" />
                    </div>
                    <div class="admin-stat-body">
                        <div class="admin-stat-value">{{ stat.value }}</div>
                        <div class="admin-stat-title">{{ stat.title }}</div>
                    </div>
                    <NIcon :component="ArrowForwardOutline" class="admin-stat-arrow" />
                </div>
            </div>
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

.admin-stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 24px;
}

.admin-stat-card {
    display: flex;
    align-items: center;
    gap: 20px;
    background: var(--admin-surface, #fff);
    border-radius: var(--admin-radius-lg, 16px);
    padding: 28px;
    border: 1px solid var(--admin-border, rgba(0,0,0,.06));
    cursor: pointer;
    transition: transform 0.25s ease, box-shadow 0.25s ease, border-color 0.2s;
    animation: adminCardReveal 0.5s ease-out backwards;
}

.admin-stat-card:hover {
    transform: translateY(-4px);
    border-color: var(--admin-accent, #0d9488);
    box-shadow: var(--admin-shadow-hover, 0 8px 24px rgba(0,0,0,.08));
}

.admin-stat-icon {
    width: 56px;
    height: 56px;
    border-radius: var(--admin-radius, 12px);
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.admin-stat-body {
    flex: 1;
    min-width: 0;
}

.admin-stat-value {
    font-size: 30px;
    font-weight: 700;
    letter-spacing: -0.02em;
    color: var(--admin-text, #1a1d21);
}

.admin-stat-title {
    font-size: 14px;
    color: var(--admin-text-muted, #6b7280);
    margin-top: 4px;
}

.admin-stat-arrow {
    color: var(--admin-text-muted, #6b7280);
    transition: transform 0.25s ease, color 0.2s;
    flex-shrink: 0;
}

.admin-stat-card:hover .admin-stat-arrow {
    color: var(--admin-accent, #0d9488);
    transform: translateX(4px);
}

@keyframes adminCardReveal {
    from {
        opacity: 0;
        transform: translateY(16px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>
