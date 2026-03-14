<script setup lang="ts">
import { computed } from 'vue';
import { Head, usePage, router } from '@inertiajs/vue3';
import {
    NIcon,
    NButton,
    NTag,
    NAlert,
} from 'naive-ui';
import {
    PhonePortraitOutline,
    CloudDownloadOutline,
    ArrowForwardOutline,
    PulseOutline,
    ServerOutline,
    CalendarOutline,
    TodayOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import StatsGrid, { type StatCard } from '@/Components/Dashboard/StatsGrid.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface Props {
    stats: {
        totalDevices: number;
        onlineDevices: number;
        totalBuilds: number;
        todayInstalled: number;
        monthInstalled: number;
    };
}

const props = defineProps<Props>();

const page = usePage();
type AuthUser = { username?: string; permissions?: string[] };
const user = computed(() => (page.props.auth as { user?: AuthUser })?.user);
const { userRoute } = useAdminBasePath();

const userPermissions = computed(() => user.value?.permissions ?? []);
const hasPerm = (perm: string) => userPermissions.value.includes(perm);
const hasNoCorePerm = computed(() => !hasPerm('devices.view') && !hasPerm('builds.view'));

const onlinePercentage = computed(() => {
    if (props.stats.totalDevices === 0) return 0;
    return Math.round((props.stats.onlineDevices / props.stats.totalDevices) * 100);
});

const statCards = computed<StatCard[]>(() => {
    // 需要 dashboard.stats 权限才显示统计卡片
    if (!hasPerm('dashboard.stats')) {
        return [];
    }

    const cards: StatCard[] = [];

    // 设备统计：需 devices.view 权限
    if (hasPerm('devices.view')) {
        cards.push(
            {
                key: 'totalDevices',
                title: '设备总数',
                icon: PhonePortraitOutline,
                color: '#10B981',
                bgColor: 'rgba(16, 185, 129, 0.1)',
            },
            {
                key: 'onlineDevices',
                title: '在线设备',
                icon: PulseOutline,
                color: '#3B82F6',
                bgColor: 'rgba(59, 130, 246, 0.1)',
                suffix: `/ ${props.stats.totalDevices}`,
                progress: onlinePercentage.value,
            },
            {
                key: 'todayInstalled',
                title: '今日安装',
                icon: TodayOutline,
                color: '#F59E0B',
                bgColor: 'rgba(245, 158, 11, 0.1)',
            },
            {
                key: 'monthInstalled',
                title: '本月安装',
                icon: CalendarOutline,
                color: '#EC4899',
                bgColor: 'rgba(236, 72, 153, 0.1)',
            }
        );
    }

    // APK 构建统计：需 builds.view 权限
    if (hasPerm('builds.view')) {
        cards.push({
            key: 'totalBuilds',
            title: 'APK 构建',
            icon: CloudDownloadOutline,
            color: '#8B5CF6',
            bgColor: 'rgba(139, 92, 246, 0.1)',
        });
    }

    return cards;
});

const quickActions = computed(() => {
    const actions: Array<{ label: string; icon: any; route: string; color: string }> = [];
    if (hasPerm('devices.view')) {
        actions.push({ label: '管理设备', icon: PhonePortraitOutline, route: userRoute('/devices'), color: '#10B981' });
    }
    if (hasPerm('builds.view')) {
        actions.push({ label: 'APK 构建', icon: CloudDownloadOutline, route: userRoute('/builds'), color: '#3B82F6' });
    }
    actions.push({ label: '系统设置', icon: ServerOutline, route: userRoute('/settings/profile'), color: '#8B5CF6' });
    return actions;
});

const goTo = (route: string) => router.visit(route);
</script>

<template>
    <Head title="控制台" />
    <AuthenticatedLayout>
        <template #header-title>控制台</template>

        <div class="dashboard-container">
            <!-- 欢迎区域 -->
            <div class="welcome-section">
                <div class="welcome-content">
                    <h1 class="welcome-title">
                        欢迎回来，<span class="username">{{ user?.username }}</span> 👋
                    </h1>
                    <p class="welcome-subtitle">
                        这是您的 {{ page.props.appName }} 控制台，查看设备状态和系统概览
                    </p>
                </div>
                <div v-if="hasPerm('devices.view')" class="welcome-actions">
                    <NButton type="primary" class="action-btn" @click="goTo(userRoute('/devices'))">
                        <template #icon>
                            <NIcon :component="PhonePortraitOutline" />
                        </template>
                        查看设备
                    </NButton>
                </div>
            </div>

            <!-- 无权限警告 -->
            <NAlert v-if="hasNoCorePerm" type="warning" class="no-perm-alert">
                <template #header>您的账号尚未分配功能权限</template>
                当前账号没有设备管理、APK 构建等功能权限，无法使用核心功能。请联系管理员为您分配所需权限后即可正常使用。
            </NAlert>

            <!-- 统计卡片 -->
            <StatsGrid v-if="statCards.length > 0" :stats="stats" :cards="statCards" class="stats-section" />

            <!-- 快捷操作 -->
            <div class="quick-section">
                <h2 class="section-title">快捷操作</h2>
                <div class="quick-grid">
                    <div 
                        v-for="action in quickActions" 
                        :key="action.label" 
                        class="quick-card"
                        @click="goTo(action.route)"
                    >
                        <div class="quick-icon" :style="{ background: `${action.color}15`, color: action.color }">
                            <NIcon :component="action.icon" size="28" />
                        </div>
                        <span class="quick-label">{{ action.label }}</span>
                        <NIcon :component="ArrowForwardOutline" class="quick-arrow" />
                    </div>
                </div>
            </div>

            <!-- 系统状态：需 dashboard.system_status 权限 -->
            <div v-if="hasPerm('dashboard.system_status')" class="status-section">
                <h2 class="section-title">系统状态</h2>
                <div class="status-card">
                    <div class="status-item">
                        <div class="status-indicator online"></div>
                        <span class="status-label">API 服务</span>
                        <NTag type="success" size="small" round>正常</NTag>
                    </div>
                    <div v-if="hasPerm('devices.view')" class="status-item">
                        <div class="status-indicator online"></div>
                        <span class="status-label">WebSocket</span>
                        <NTag type="success" size="small" round>已连接</NTag>
                    </div>
                    <div class="status-item">
                        <div class="status-indicator online"></div>
                        <span class="status-label">数据库</span>
                        <NTag type="success" size="small" round>正常</NTag>
                    </div>
                    <div v-if="hasPerm('builds.view')" class="status-item">
                        <div class="status-indicator online"></div>
                        <span class="status-label">构建服务</span>
                        <NTag type="success" size="small" round>就绪</NTag>
                    </div>
                </div>
            </div>
        </div>
    </AuthenticatedLayout>
</template>

<style scoped>
.dashboard-container {
    max-width: 1200px;
}

/* 欢迎区域 */
.welcome-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 20px;
    padding: 32px 40px;
    margin-bottom: 28px;
    box-shadow: 0 8px 32px rgba(16, 185, 129, 0.25);
}

.welcome-title {
    font-size: 28px;
    font-weight: 700;
    color: white;
    margin: 0 0 8px;
}

.username {
    color: rgba(255, 255, 255, 0.95);
}

.welcome-subtitle {
    font-size: 15px;
    color: rgba(255, 255, 255, 0.8);
    margin: 0;
}

.action-btn {
    height: 44px;
    padding: 0 24px;
    border-radius: 12px;
    font-weight: 600;
    background: white;
    color: #059669;
    border: none;
    transition: all 0.3s ease;
}

.action-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

/* 无权限警告 */
.no-perm-alert {
    margin-bottom: 28px;
    border-radius: 14px;
}

/* 统计卡片 */
.stats-section {
    margin-bottom: 28px;
}

.stats-section :deep(.stats-grid--default) {
    grid-template-columns: repeat(3, 1fr);
}

/* 快捷操作 */
.section-title {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 16px;
}

.quick-section {
    margin-bottom: 28px;
}

.quick-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
}

.quick-card {
    display: flex;
    align-items: center;
    gap: 16px;
    background: white;
    border-radius: 14px;
    padding: 20px 24px;
    border: 1px solid #e2e8f0;
    cursor: pointer;
    transition: all 0.3s ease;
}

.quick-card:hover {
    border-color: #10B981;
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.12);
    transform: translateX(4px);
}

.quick-card:hover .quick-arrow {
    opacity: 1;
    transform: translateX(4px);
}

.quick-icon {
    width: 52px;
    height: 52px;
    border-radius: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.quick-label {
    font-size: 16px;
    font-weight: 600;
    color: #1e293b;
    flex: 1;
}

.quick-arrow {
    color: #10B981;
    opacity: 0;
    transition: all 0.3s ease;
}

/* 系统状态 */
.status-section {
    margin-bottom: 20px;
}

.status-card {
    background: white;
    border-radius: 14px;
    padding: 8px;
    border: 1px solid #e2e8f0;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 8px;
}

.status-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px 20px;
    background: #f8fafc;
    border-radius: 10px;
}

.status-indicator {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    flex-shrink: 0;
}

.status-indicator.online {
    background: #10B981;
    box-shadow: 0 0 8px rgba(16, 185, 129, 0.5);
}

.status-indicator.offline {
    background: #ef4444;
    box-shadow: 0 0 8px rgba(239, 68, 68, 0.5);
}

.status-label {
    font-size: 14px;
    font-weight: 500;
    color: #475569;
    flex: 1;
}

/* 响应式 */
@media (max-width: 1024px) {
    .stats-section :deep(.stats-grid--default) {
        grid-template-columns: repeat(2, 1fr);
    }
    
    .quick-grid {
        grid-template-columns: repeat(2, 1fr);
    }
    
    .status-card {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 640px) {
    .welcome-section {
        flex-direction: column;
        text-align: center;
        gap: 20px;
        padding: 28px 24px;
    }
    
    .welcome-title {
        font-size: 22px;
    }
    
    .stats-section :deep(.stats-grid--default) {
        grid-template-columns: 1fr;
    }
    
    .quick-grid {
        grid-template-columns: 1fr;
    }
    
    .status-card {
        grid-template-columns: 1fr;
    }
}
</style>
