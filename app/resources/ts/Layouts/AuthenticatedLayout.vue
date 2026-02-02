<script setup lang="ts">
import { computed, ref, onMounted, onUnmounted } from 'vue';
import { usePage, router } from '@inertiajs/vue3';
import {
    NLayout,
    NLayoutHeader,
    NLayoutSider,
    NLayoutContent,
    NMenu,
    NAvatar,
    NDropdown,
    NIcon,
    NButton,
    NBadge,
    NDivider,
    NText,
} from 'naive-ui';
import {
    HomeOutline,
    PhonePortraitOutline,
    CloudDownloadOutline,
    SettingsOutline,
    LogOutOutline,
    PersonOutline,
    ChevronBackOutline,
    ChevronForwardOutline,
} from '@vicons/ionicons5';
import { h } from 'vue';
import DefaultLayout from './DefaultLayout.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const page = usePage();
const user = computed(() => page.props.auth?.user);
const collapsed = ref(false);

const { connect, disconnect, connectionState } = useGlobalWebSocket();

onMounted(() => {
    if (user.value?.email) {
        connect(user.value.email);
    }
});

onUnmounted(() => {
    disconnect();
});

const menuOptions = [
    {
        label: '控制台',
        key: 'dashboard',
        icon: () => h(NIcon, null, { default: () => h(HomeOutline) }),
    },
    {
        label: '设备管理',
        key: 'devices',
        icon: () => h(NIcon, null, { default: () => h(PhonePortraitOutline) }),
    },
    {
        label: 'APK 构建',
        key: 'builds',
        icon: () => h(NIcon, null, { default: () => h(CloudDownloadOutline) }),
    },
    {
        label: '设置',
        key: 'settings',
        icon: () => h(NIcon, null, { default: () => h(SettingsOutline) }),
    },
];

const userMenuOptions = [
    {
        label: '个人资料',
        key: 'profile',
        icon: () => h(NIcon, null, { default: () => h(PersonOutline) }),
    },
    {
        type: 'divider',
        key: 'd1',
    },
    {
        label: '退出登录',
        key: 'logout',
        icon: () => h(NIcon, null, { default: () => h(LogOutOutline) }),
    },
];

const handleMenuSelect = (key: string) => {
    const routes: Record<string, string> = {
        dashboard: '/dashboard',
        devices: '/devices',
        builds: '/builds',
        settings: '/settings/profile',
    };
    if (routes[key]) {
        router.visit(routes[key]);
    }
};

const handleUserMenuSelect = (key: string) => {
    if (key === 'logout') {
        router.post('/logout');
    } else if (key === 'profile') {
        router.visit('/settings/profile');
    }
};

const currentPath = computed(() => {
    const path = window.location.pathname;
    if (path.startsWith('/devices')) return 'devices';
    if (path.startsWith('/builds')) return 'builds';
    if (path.startsWith('/settings')) return 'settings';
    return 'dashboard';
});

const userInitial = computed(() => {
    return user.value?.username?.charAt(0).toUpperCase() || 'U';
});
</script>

<template>
    <DefaultLayout>
        <NLayout has-sider class="min-h-screen">
            <!-- 现代化侧边栏 -->
            <NLayoutSider
                bordered
                :width="240"
                :collapsed-width="64"
                :collapsed="collapsed"
                collapse-mode="width"
                :native-scrollbar="false"
                class="sidebar-modern"
            >
                <!-- Logo 区域 -->
                <div class="sidebar-header">
                    <div class="logo-container">
                        <div class="logo-icon">
                            <svg viewBox="0 0 24 24" fill="none" class="w-8 h-8">
                                <path d="M12 2L2 7L12 12L22 7L12 2Z" fill="url(#gradient1)" />
                                <path d="M2 17L12 22L22 17" stroke="url(#gradient2)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                                <path d="M2 12L12 17L22 12" stroke="url(#gradient2)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                                <defs>
                                    <linearGradient id="gradient1" x1="2" y1="7" x2="22" y2="7">
                                        <stop stop-color="#10B981" />
                                        <stop offset="1" stop-color="#059669" />
                                    </linearGradient>
                                    <linearGradient id="gradient2" x1="2" y1="17" x2="22" y2="17">
                                        <stop stop-color="#10B981" />
                                        <stop offset="1" stop-color="#059669" />
                                    </linearGradient>
                                </defs>
                            </svg>
                        </div>
                        <transition name="fade">
                            <span v-if="!collapsed" class="logo-text">飞鹰系统</span>
                        </transition>
                    </div>
                </div>

                <!-- 导航菜单 -->
                <NMenu
                    :options="menuOptions"
                    :value="currentPath"
                    :collapsed="collapsed"
                    :collapsed-width="64"
                    :collapsed-icon-size="22"
                    @update:value="handleMenuSelect"
                    class="menu-modern"
                />

                <!-- 折叠按钮 -->
                <div class="collapse-trigger" @click="collapsed = !collapsed">
                    <NIcon size="18" :component="collapsed ? ChevronForwardOutline : ChevronBackOutline" />
                </div>
            </NLayoutSider>

            <NLayout>
                <!-- 现代化顶部导航 -->
                <NLayoutHeader bordered class="header-modern">
                    <div class="header-title">
                        <slot name="header-title" />
                    </div>
                    
                    <div class="header-actions">
                        <NDropdown
                            :options="userMenuOptions"
                            @select="handleUserMenuSelect"
                            trigger="click"
                            placement="bottom-end"
                        >
                            <div class="user-dropdown">
                                <NAvatar
                                    round
                                    :size="36"
                                    class="user-avatar"
                                >
                                    {{ userInitial }}
                                </NAvatar>
                                <transition name="fade">
                                    <div class="user-info">
                                        <span class="user-name">{{ user?.username }}</span>
                                        <span class="user-role">{{ user?.role === 'admin' ? '管理员' : '用户' }}</span>
                                    </div>
                                </transition>
                            </div>
                        </NDropdown>
                    </div>
                </NLayoutHeader>

                <!-- 内容区域 -->
                <NLayoutContent class="content-modern">
                    <div class="content-wrapper">
                        <slot />
                    </div>
                </NLayoutContent>
            </NLayout>
        </NLayout>
    </DefaultLayout>
</template>

<style scoped>
/* 侧边栏样式 */
.sidebar-modern {
    background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
    border-right: 1px solid #e2e8f0;
    display: flex;
    flex-direction: column;
}

.sidebar-header {
    padding: 20px 16px;
    border-bottom: 1px solid #e2e8f0;
}

.logo-container {
    display: flex;
    align-items: center;
    gap: 12px;
}

.logo-icon {
    width: 40px;
    height: 40px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.logo-text {
    font-size: 18px;
    font-weight: 700;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.menu-modern {
    flex: 1;
    padding: 12px 8px;
}

.menu-modern :deep(.n-menu-item) {
    margin: 4px 0;
    border-radius: 10px;
    transition: all 0.2s ease;
}

.menu-modern :deep(.n-menu-item:hover) {
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.1) 0%, rgba(5, 150, 105, 0.1) 100%);
}

.menu-modern :deep(.n-menu-item-content--selected) {
    background: linear-gradient(135deg, #10B981 0%, #059669 100%) !important;
    color: white !important;
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.menu-modern :deep(.n-menu-item-content--selected .n-icon) {
    color: white !important;
}

.collapse-trigger {
    padding: 12px;
    margin: 8px;
    border-radius: 8px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #64748b;
    transition: all 0.2s ease;
    background: #f1f5f9;
}

.collapse-trigger:hover {
    background: #e2e8f0;
    color: #10B981;
}

/* 顶部导航样式 */
.header-modern {
    height: 64px;
    padding: 0 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #ffffff;
    border-bottom: 1px solid #e2e8f0;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.header-title {
    font-size: 20px;
    font-weight: 600;
    color: #1e293b;
}

.header-actions {
    display: flex;
    align-items: center;
    gap: 16px;
}

.user-dropdown {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 6px 12px 6px 6px;
    border-radius: 50px;
    cursor: pointer;
    transition: all 0.2s ease;
    background: #f8fafc;
    border: 1px solid #e2e8f0;
}

.user-dropdown:hover {
    background: #f1f5f9;
    border-color: #10B981;
    box-shadow: 0 2px 8px rgba(16, 185, 129, 0.15);
}

.user-avatar {
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    color: white;
    font-weight: 600;
}

.user-info {
    display: flex;
    flex-direction: column;
}

.user-name {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
    line-height: 1.2;
}

.user-role {
    font-size: 12px;
    color: #64748b;
    line-height: 1.2;
}

/* 内容区域样式 */
.content-modern {
    background: #f8fafc;
    min-height: calc(100vh - 64px);
}

.content-wrapper {
    padding: 24px;
    max-width: 1400px;
    margin: 0 auto;
}

/* 过渡动画 */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

/* 暗色模式支持 */
:deep(.dark) .sidebar-modern {
    background: linear-gradient(180deg, #1e293b 0%, #0f172a 100%);
    border-right-color: #334155;
}

:deep(.dark) .sidebar-header {
    border-bottom-color: #334155;
}

:deep(.dark) .logo-text {
    background: linear-gradient(135deg, #34d399 0%, #10B981 100%);
    -webkit-background-clip: text;
    background-clip: text;
}

:deep(.dark) .header-modern {
    background: #1e293b;
    border-bottom-color: #334155;
}

:deep(.dark) .header-title {
    color: #f1f5f9;
}

:deep(.dark) .user-dropdown {
    background: #334155;
    border-color: #475569;
}

:deep(.dark) .user-name {
    color: #f1f5f9;
}

:deep(.dark) .user-role {
    color: #94a3b8;
}

:deep(.dark) .content-modern {
    background: #0f172a;
}

:deep(.dark) .collapse-trigger {
    background: #334155;
    color: #94a3b8;
}
</style>
