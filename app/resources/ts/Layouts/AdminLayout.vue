<script setup lang="ts">
import { computed, ref, h, onMounted, onUnmounted } from 'vue';
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
} from 'naive-ui';
import {
    HomeOutline,
    PeopleOutline,
    PhonePortraitOutline,
    CloudDownloadOutline,
    LogOutOutline,
    ChevronBackOutline,
    ChevronForwardOutline,
    KeyOutline,
} from '@vicons/ionicons5';
import DefaultLayout from './DefaultLayout.vue';
import SidebarLogo from '@/Components/SidebarLogo.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const page = usePage();
const admin = computed(() => (page.props.auth as { admin?: { id: number; name: string; email: string } })?.admin);
const collapsed = ref(false);
const { connect, disconnect } = useGlobalWebSocket();

onMounted(() => {
    if (admin.value?.email) {
        connect(admin.value.email);
    }
});

onUnmounted(() => {
    disconnect();
});

const menuOptions = [
    { label: '控制台', key: 'dashboard', icon: () => h(NIcon, null, { default: () => h(HomeOutline) }) },
    { label: '用户管理', key: 'users', icon: () => h(NIcon, null, { default: () => h(PeopleOutline) }) },
    { label: '角色与权限', key: 'roles', icon: () => h(NIcon, null, { default: () => h(KeyOutline) }) },
    { label: 'APK 构建', key: 'builds', icon: () => h(NIcon, null, { default: () => h(CloudDownloadOutline) }) },
    { label: '设备管理', key: 'devices', icon: () => h(NIcon, null, { default: () => h(PhonePortraitOutline) }) },
];

const userMenuOptions = [
    { label: '退出登录', key: 'logout', icon: () => h(NIcon, null, { default: () => h(LogOutOutline) }) },
];

const handleMenuSelect = (key: string) => {
    const routes: Record<string, string> = {
        dashboard: '/admin/dashboard',
        users: '/admin/users',
        roles: '/admin/roles',
        builds: '/admin/builds',
        devices: '/admin/devices',
    };
    if (routes[key]) router.visit(routes[key]);
};

const handleUserMenuSelect = (key: string) => {
    if (key === 'logout') {
        router.post('/admin/logout');
    }
};

const currentPath = computed(() => {
    const path = window.location.pathname;
    if (path.startsWith('/admin/users')) return 'users';
    if (path.startsWith('/admin/roles')) return 'roles';
    if (path.startsWith('/admin/builds')) return 'builds';
    if (path.startsWith('/admin/devices')) return 'devices';
    return 'dashboard';
});

const adminInitial = computed(() => admin.value?.name?.charAt(0).toUpperCase() ?? 'A');
</script>

<template>
    <DefaultLayout>
        <NLayout has-sider class="admin-root min-h-screen">
            <NLayoutSider
                bordered
                :width="260"
                :collapsed-width="72"
                :collapsed="collapsed"
                collapse-mode="width"
                :native-scrollbar="false"
                class="admin-sidebar"
            >
                <div class="admin-sidebar-header" :class="{ 'admin-sidebar-header--collapsed': collapsed }">
                    <SidebarLogo
                        :collapsed="collapsed"
                        variant="admin"
                        title="总管理后台"
                        :size="44"
                    />
                </div>
                <NMenu
                    :options="menuOptions"
                    :value="currentPath"
                    :collapsed="collapsed"
                    :collapsed-width="72"
                    :collapsed-icon-size="22"
                    @update:value="handleMenuSelect"
                    class="admin-menu"
                />
                <div class="collapse-trigger" @click="collapsed = !collapsed">
                    <NIcon size="18" :component="collapsed ? ChevronForwardOutline : ChevronBackOutline" />
                </div>
            </NLayoutSider>
            <NLayout class="admin-main-layout">
                <NLayoutHeader bordered class="admin-header">
                    <div class="header-title">
                        <slot name="header-title" />
                    </div>
                    <div class="header-actions">
                        <NDropdown :options="userMenuOptions" @select="handleUserMenuSelect" trigger="click" placement="bottom-end">
                            <div class="admin-dropdown">
                                <NAvatar round :size="38" class="admin-avatar">{{ adminInitial }}</NAvatar>
                                <transition name="fade">
                                    <div v-if="!collapsed" class="admin-info">
                                        <span class="admin-name">{{ admin?.name }}</span>
                                        <span class="admin-email">{{ admin?.email }}</span>
                                    </div>
                                </transition>
                            </div>
                        </NDropdown>
                    </div>
                </NLayoutHeader>
                <NLayoutContent class="admin-content">
                    <div class="admin-content-wrapper admin-page-reveal">
                        <slot />
                    </div>
                </NLayoutContent>
            </NLayout>
        </NLayout>
    </DefaultLayout>
</template>

<style scoped>
.admin-root {
    --admin-bg: #f4f5f7;
    --admin-surface: #ffffff;
    --admin-border: rgba(0, 0, 0, 0.06);
    --admin-accent: #0d9488;
    --admin-accent-hover: #0f766e;
    --admin-accent-muted: rgba(13, 148, 136, 0.12);
    --admin-text: #1a1d21;
    --admin-text-muted: #6b7280;
    --admin-radius: 12px;
    --admin-radius-lg: 16px;
    --admin-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    --admin-shadow-hover: 0 8px 24px rgba(0, 0, 0, 0.08);
    font-family: var(--font-sans, ui-sans-serif, system-ui, sans-serif);
}

.admin-sidebar {
    background: var(--admin-surface) !important;
    border-right: 1px solid var(--admin-border) !important;
    box-shadow: var(--admin-shadow);
}

.admin-sidebar-header {
    padding: 24px 20px;
    border-bottom: 1px solid var(--admin-border);
    transition: padding 0.3s ease;
}

.admin-sidebar-header--collapsed {
    padding: 20px 14px;
}

.admin-menu {
    margin-top: 12px;
}

.admin-menu :deep(.n-menu-item-content) {
    border-radius: var(--admin-radius);
    margin: 0 10px;
}

.admin-menu :deep(.n-menu-item-content--selected) {
    background: var(--admin-accent-muted);
    color: var(--admin-accent);
}

.admin-menu :deep(.n-menu-item-content--selected::before) {
    background: var(--admin-accent);
    width: 3px;
    border-radius: 0 2px 2px 0;
}

.collapse-trigger {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    width: 36px;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: var(--admin-radius);
    background: var(--admin-bg);
    cursor: pointer;
    color: var(--admin-text-muted);
    transition: background 0.2s, color 0.2s;
}

.collapse-trigger:hover {
    background: var(--admin-accent-muted);
    color: var(--admin-accent);
}

.admin-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 28px;
    background: var(--admin-surface) !important;
    border-bottom: 1px solid var(--admin-border) !important;
    min-height: 60px;
}

.header-title {
    font-size: 18px;
    font-weight: 600;
    letter-spacing: -0.02em;
    color: var(--admin-text);
}

.admin-dropdown {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 6px 12px 6px 6px;
    border-radius: var(--admin-radius-lg);
    cursor: pointer;
    transition: background 0.2s;
}

.admin-dropdown:hover {
    background: var(--admin-bg);
}

.admin-avatar {
    background: linear-gradient(145deg, var(--admin-accent) 0%, var(--admin-accent-hover) 100%) !important;
    color: white !important;
    font-weight: 600;
    font-size: 14px;
}

.admin-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.admin-name {
    font-weight: 600;
    font-size: 14px;
    color: var(--admin-text);
}

.admin-email {
    font-size: 12px;
    color: var(--admin-text-muted);
}

.admin-content {
    padding: 28px;
    background: var(--admin-bg);
    min-height: calc(100vh - 60px);
}

.admin-content-wrapper {
    max-width: 1600px;
    margin: 0 auto;
}

.admin-page-reveal {
    animation: adminPageReveal 0.4s ease-out;
}

@keyframes adminPageReveal {
    from {
        opacity: 0;
        transform: translateY(8px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
