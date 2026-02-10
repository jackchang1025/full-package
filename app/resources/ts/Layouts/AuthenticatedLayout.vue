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
    PeopleOutline,
    ChevronBackOutline,
    ChevronForwardOutline,
} from '@vicons/ionicons5';
import { h } from 'vue';
import DefaultLayout from './DefaultLayout.vue';
import SidebarLogo from '@/Components/SidebarLogo.vue';
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const page = usePage();
type AuthUser = {
    username?: string;
    email?: string;
    roles?: string[];
    permissions?: string[];
    is_sub_account?: boolean;
};
type AuthProps = { auth?: { user?: AuthUser } };
const user = computed(() => (page.props as AuthProps).auth?.user);
const collapsed = ref(false);

const { userRoute, userBaseUrl } = useAdminBasePath();
const { connect, disconnect, connectionState } = useGlobalWebSocket();

onMounted(() => {
    if (user.value?.email) {
        connect(user.value.email);
    }
});

onUnmounted(() => {
    disconnect();
});

const userPermissions = computed(() => user.value?.permissions ?? []);

const hasPerm = (perm: string) => userPermissions.value.includes(perm);

const menuOptions = computed(() => {
    const items: Array<{ label: string; key: string; icon: () => any }> = [
        {
            label: '控制台',
            key: 'dashboard',
            icon: () => h(NIcon, null, { default: () => h(HomeOutline) }),
        },
    ];

    // 设备管理：需 devices.view 权限
    if (hasPerm('devices.view')) {
        items.push({
            label: '设备管理',
            key: 'devices',
            icon: () => h(NIcon, null, { default: () => h(PhonePortraitOutline) }),
        });
    }

    // APK 构建：需 builds.view 权限
    if (hasPerm('builds.view')) {
        items.push({
            label: 'APK 构建',
            key: 'builds',
            icon: () => h(NIcon, null, { default: () => h(CloudDownloadOutline) }),
        });
    }

    // 子账号管理：仅父用户且有 teams.manage 权限时显示
    if (!user.value?.is_sub_account && hasPerm('teams.manage')) {
        items.push({
            label: '子账号管理',
            key: 'sub-accounts',
            icon: () => h(NIcon, null, { default: () => h(PeopleOutline) }),
        });
    }

    items.push({
        label: '设置',
        key: 'settings',
        icon: () => h(NIcon, null, { default: () => h(SettingsOutline) }),
    });

    return items;
});

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
        dashboard: userRoute('/dashboard'),
        devices: userRoute('/devices'),
        builds: userRoute('/builds'),
        'sub-accounts': userRoute('/sub-accounts'),
        settings: userRoute('/settings/profile'),
    };
    if (routes[key]) {
        router.visit(routes[key]);
    }
};

const handleUserMenuSelect = (key: string) => {
    if (key === 'logout') {
        router.post(userRoute('/logout'));
    } else if (key === 'profile') {
        router.visit(userRoute('/settings/profile'));
    }
};

const currentPath = computed(() => {
    const path = window.location.pathname;
    const base = userBaseUrl.value;
    const relativePath = base && path.startsWith(base) ? path.slice(base.length) : path;
    if (relativePath.startsWith('/devices')) return 'devices';
    if (relativePath.startsWith('/builds')) return 'builds';
    if (relativePath.startsWith('/sub-accounts')) return 'sub-accounts';
    if (relativePath.startsWith('/settings')) return 'settings';
    return 'dashboard';
});

const userInitial = computed(() => {
    return user.value?.username?.charAt(0).toUpperCase() || 'U';
});
</script>

<template>
    <DefaultLayout>
        <NLayout has-sider class="user-root min-h-screen">
            <!-- 用户后台侧边栏：设计系统 + 精致菜单 -->
            <NLayoutSider
                bordered
                :width="260"
                :collapsed-width="72"
                :collapsed="collapsed"
                collapse-mode="width"
                :native-scrollbar="false"
                class="user-sidebar"
            >
                <!-- Logo 区域 -->
                <div class="user-sidebar-header" :class="{ 'user-sidebar-header--collapsed': collapsed }">
                    <SidebarLogo
                        :collapsed="collapsed"
                        variant="user"
                        :size="44"
                    />
                </div>

                <!-- 导航菜单：带错峰入场 -->
                <div class="user-menu-wrap" :class="{ 'user-menu-wrap--collapsed': collapsed }">
                    <NMenu
                        :options="menuOptions"
                        :value="currentPath"
                        :collapsed="collapsed"
                        :collapsed-width="72"
                        :collapsed-icon-size="22"
                        @update:value="handleMenuSelect"
                        class="user-menu"
                        :class="{ 'user-menu--collapsed': collapsed }"
                    />
                </div>

                <!-- 折叠按钮 -->
                <div class="user-collapse-trigger" @click="collapsed = !collapsed" aria-label="折叠侧边栏">
                    <NIcon size="18" :component="collapsed ? ChevronForwardOutline : ChevronBackOutline" />
                </div>
            </NLayoutSider>

            <NLayout>
                <!-- 顶部导航 -->
                <NLayoutHeader bordered class="user-header">
                    <div class="user-header-title">
                        <slot name="header-title" />
                    </div>
                    <div class="user-header-actions">
                        <NDropdown
                            :options="userMenuOptions"
                            @select="handleUserMenuSelect"
                            trigger="click"
                            placement="bottom-end"
                        >
                            <div class="user-dropdown-trigger">
                                <NAvatar
                                    round
                                    :size="36"
                                    class="user-header-avatar"
                                >
                                    {{ userInitial }}
                                </NAvatar>
                                <transition name="sidebar-fade" mode="out-in">
                                    <div v-if="user?.username" class="user-header-info">
                                        <span class="user-header-name">{{ user.username }}</span>
                                        <span class="user-header-role">{{ (user?.roles?.length && user.roles[0] === 'client') ? '普通用户' : (user?.roles?.[0] ?? '用户') }}</span>
                                    </div>
                                </transition>
                            </div>
                        </NDropdown>
                    </div>
                </NLayoutHeader>

                <!-- 内容区域 -->
                <NLayoutContent class="user-content">
                    <div class="user-content-wrapper">
                        <slot />
                    </div>
                </NLayoutContent>
            </NLayout>
        </NLayout>
    </DefaultLayout>
</template>

<style scoped>
/* ========== 用户后台设计系统（设计变量） ========== */
.user-root {
    --user-bg: #f8fafc;
    --user-surface: #ffffff;
    --user-surface-soft: #fafbfc;
    --user-border: #e2e8f0;
    --user-border-soft: #f1f5f9;
    --user-accent: #10b981;
    --user-accent-strong: #059669;
    --user-accent-muted: rgba(16, 185, 129, 0.12);
    --user-accent-muted-hover: rgba(16, 185, 129, 0.18);
    --user-text: #1e293b;
    --user-text-muted: #64748b;
    --user-radius: 12px;
    --user-radius-lg: 16px;
    --user-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    --user-shadow-hover: 0 4px 12px rgba(16, 185, 129, 0.12);
    font-family: var(--font-sans, ui-sans-serif, system-ui, sans-serif);
}

/* ========== 侧边栏 ========== */
.user-sidebar {
    display: flex;
    flex-direction: column;
    background: var(--user-surface) !important;
    border-right: 1px solid var(--user-border) !important;
    box-shadow: var(--user-shadow);
    position: relative;
}

.user-sidebar-header {
    padding: 24px 20px;
    border-bottom: 1px solid var(--user-border);
    flex-shrink: 0;
    transition: padding 0.3s ease;
}

/* 折叠时 Logo 区域居中 */
.user-sidebar :deep(.n-layout-sider-scroll-container) {
    display: flex;
    flex-direction: column;
}

/* 导航菜单容器：错峰入场 */
.user-menu-wrap {
    flex: 1;
    padding: 16px 12px 0;
    overflow: auto;
    transition: padding 0.3s ease;
}

.user-menu {
    display: block;
}

.user-menu :deep(.n-menu-item) {
    margin: 2px 0;
    border-radius: var(--user-radius);
    transition: background 0.2s ease, color 0.2s ease, transform 0.2s ease;
    animation: user-menu-item-in 0.35s ease backwards;
}

.user-menu :deep(.n-menu-item:nth-child(1)) { animation-delay: 0.04s; }
.user-menu :deep(.n-menu-item:nth-child(2)) { animation-delay: 0.08s; }
.user-menu :deep(.n-menu-item:nth-child(3)) { animation-delay: 0.12s; }
.user-menu :deep(.n-menu-item:nth-child(4)) { animation-delay: 0.16s; }
.user-menu :deep(.n-menu-item:nth-child(5)) { animation-delay: 0.2s; }

@keyframes user-menu-item-in {
    from {
        opacity: 0;
        transform: translateX(-6px);
    }
    to {
        opacity: 1;
        transform: translateX(0);
    }
}

.user-menu :deep(.n-menu-item-content) {
    position: relative;
    margin: 0 8px;
    padding: 10px 14px;
    border-radius: var(--user-radius);
    transition: margin 0.3s ease, padding 0.3s ease;
}

.user-menu :deep(.n-menu-item-content:hover) {
    background: var(--user-accent-muted);
}

.user-menu :deep(.n-menu-item-content--selected) {
    background: var(--user-accent-muted);
    color: var(--user-accent);
}

.user-menu :deep(.n-menu-item-content--selected::before) {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 3px;
    height: 20px;
    background: var(--user-accent);
    border-radius: 0 2px 2px 0;
}

.user-menu :deep(.n-menu-item-content--selected .n-icon) {
    color: var(--user-accent);
}

/* ========== 折叠状态样式 ========== */
.user-sidebar-header--collapsed {
    padding: 20px 14px !important;
}

.user-menu-wrap--collapsed {
    padding: 16px 0 0 !important;
}

.user-menu--collapsed :deep(.n-menu-item-content) {
    margin: 0 10px !important;
    padding: 10px !important;
    display: flex !important;
    align-items: center !important;
    justify-content: center !important;
}

.user-menu--collapsed :deep(.n-menu-item-content--selected::before) {
    left: -10px !important;
}

/* 折叠按钮 */
.user-collapse-trigger {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    width: 36px;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: var(--user-radius);
    background: var(--user-border-soft);
    cursor: pointer;
    color: var(--user-text-muted);
    transition: background 0.2s, color 0.2s;
}

.user-collapse-trigger:hover {
    background: var(--user-accent-muted);
    color: var(--user-accent);
}

/* ========== 顶部导航 ========== */
.user-header {
    min-height: 64px;
    padding: 0 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: var(--user-surface) !important;
    border-bottom: 1px solid var(--user-border) !important;
    box-shadow: var(--user-shadow);
}

.user-header-title {
    font-size: 18px;
    font-weight: 600;
    letter-spacing: -0.02em;
    color: var(--user-text);
}

.user-header-actions {
    display: flex;
    align-items: center;
    gap: 16px;
}

.user-dropdown-trigger {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 6px 14px 6px 6px;
    border-radius: 50px;
    cursor: pointer;
    transition: background 0.2s, border-color 0.2s, box-shadow 0.2s;
    background: var(--user-border-soft);
    border: 1px solid var(--user-border);
}

.user-dropdown-trigger:hover {
    background: var(--user-accent-muted);
    border-color: var(--user-accent);
    box-shadow: var(--user-shadow-hover);
}

.user-header-avatar {
    background: linear-gradient(145deg, var(--user-accent) 0%, var(--user-accent-strong) 100%);
    color: white;
    font-weight: 600;
}

.user-header-info {
    display: flex;
    flex-direction: column;
    text-align: left;
}

.user-header-name {
    font-size: 14px;
    font-weight: 600;
    color: var(--user-text);
    line-height: 1.25;
}

.user-header-role {
    font-size: 12px;
    color: var(--user-text-muted);
    line-height: 1.25;
}

/* ========== 内容区 ========== */
.user-content {
    background: var(--user-bg);
    min-height: calc(100vh - 64px);
}

.user-content-wrapper {
    padding: 24px;
    max-width: 1600px;
    margin: 0 auto;
}

/* ========== 过渡动画 ========== */
.sidebar-fade-enter-active,
.sidebar-fade-leave-active {
    transition: opacity 0.2s ease;
}

.sidebar-fade-enter-from,
.sidebar-fade-leave-to {
    opacity: 0;
}

/* ========== 暗色模式 ========== */
:deep(.dark) .user-root {
    --user-bg: #0f172a;
    --user-surface: #1e293b;
    --user-surface-soft: #334155;
    --user-border: #334155;
    --user-border-soft: #475569;
    --user-text: #f1f5f9;
    --user-text-muted: #94a3b8;
    --user-accent-muted: rgba(52, 211, 153, 0.15);
    --user-accent-muted-hover: rgba(52, 211, 153, 0.22);
}

:deep(.dark) .user-logo-text {
    color: var(--user-text);
}

:deep(.dark) .user-collapse-trigger {
    background: var(--user-surface-soft);
    color: var(--user-text-muted);
}

:deep(.dark) .user-collapse-trigger:hover {
    background: var(--user-accent-muted);
    color: #34d399;
}
</style>
