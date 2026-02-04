<script setup lang="ts">
import { computed } from 'vue';
import { usePage } from '@inertiajs/vue3';
import { NIcon } from 'naive-ui';
import { ShieldCheckmarkOutline } from '@vicons/ionicons5';

interface Props {
    /** 是否折叠状态 */
    collapsed?: boolean;
    /** 主题变体：user（用户后台绿色）或 admin（管理后台青色） */
    variant?: 'user' | 'admin';
    /** 自定义显示文字（不传则使用 appName） */
    title?: string;
    /** Logo 图标尺寸 */
    size?: number;
}

const props = withDefaults(defineProps<Props>(), {
    collapsed: false,
    variant: 'user',
    title: '',
    size: 44,
});

const page = usePage();

// 从 Inertia 共享数据获取 Logo 和应用名
const appLogo = computed(() => (page.props as Record<string, unknown>).appLogo as string | undefined);
const appName = computed(() => (page.props as Record<string, unknown>).appName as string | undefined);

// 显示的标题
const displayTitle = computed(() => props.title || appName.value || '');

// 是否有自定义 Logo
const hasCustomLogo = computed(() => !!appLogo.value);

// 渐变色配置
const gradientColors = computed(() => {
    if (props.variant === 'admin') {
        return {
            start: '#0d9488',
            end: '#0f766e',
        };
    }
    // user variant
    return {
        start: '#10b981',
        end: '#059669',
    };
});

// 生成唯一的渐变 ID（避免多个实例冲突）
const gradientId = computed(() => `sidebarLogoGrad-${props.variant}-${Math.random().toString(36).slice(2, 8)}`);
</script>

<template>
    <div class="sidebar-logo" :class="[`sidebar-logo--${variant}`, { 'sidebar-logo--collapsed': collapsed }]">
        <div
            class="sidebar-logo-icon"
            :class="{ 'sidebar-logo-icon--custom': hasCustomLogo }"
            :style="{ width: `${size}px`, height: `${size}px` }"
        >
            <!-- 自定义 Logo -->
            <img
                v-if="hasCustomLogo"
                :src="appLogo"
                :alt="displayTitle"
                class="sidebar-logo-img"
            />
            <!-- 管理后台默认图标 -->
            <NIcon
                v-else-if="variant === 'admin'"
                :component="ShieldCheckmarkOutline"
                :size="Math.round(size * 0.59)"
            />
            <!-- 用户后台默认 SVG -->
            <svg
                v-else
                viewBox="0 0 24 24"
                fill="none"
                class="sidebar-logo-svg"
                aria-hidden="true"
            >
                <path d="M12 2L2 7L12 12L22 7L12 2Z" :fill="`url(#${gradientId}-1)`" />
                <path
                    d="M2 17L12 22L22 17"
                    :stroke="`url(#${gradientId}-2)`"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                />
                <path
                    d="M2 12L12 17L22 12"
                    :stroke="`url(#${gradientId}-2)`"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                />
                <defs>
                    <linearGradient :id="`${gradientId}-1`" x1="2" y1="7" x2="22" y2="7">
                        <stop :stop-color="gradientColors.start" />
                        <stop offset="1" :stop-color="gradientColors.end" />
                    </linearGradient>
                    <linearGradient :id="`${gradientId}-2`" x1="2" y1="17" x2="22" y2="17">
                        <stop :stop-color="gradientColors.start" />
                        <stop offset="1" :stop-color="gradientColors.end" />
                    </linearGradient>
                </defs>
            </svg>
        </div>
        <transition name="sidebar-logo-fade" mode="out-in">
            <span v-if="!collapsed" class="sidebar-logo-text">{{ displayTitle }}</span>
        </transition>
    </div>
</template>

<style scoped>
.sidebar-logo {
    display: flex;
    align-items: center;
    gap: 14px;
    transition: justify-content 0.3s ease;
}

.sidebar-logo--collapsed {
    justify-content: center;
}

/* ========== Icon 样式 ========== */
.sidebar-logo-icon {
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    transition: background 0.2s ease, box-shadow 0.2s ease;
}

/* 用户后台主题 */
.sidebar-logo--user .sidebar-logo-icon {
    background: linear-gradient(145deg, #10b981 0%, #059669 100%);
    color: white;
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

/* 管理后台主题 */
.sidebar-logo--admin .sidebar-logo-icon {
    background: linear-gradient(145deg, #0d9488 0%, #0f766e 100%);
    color: white;
    box-shadow: 0 4px 12px rgba(13, 148, 136, 0.35);
}

/* 自定义 Logo 时移除背景 */
.sidebar-logo-icon--custom {
    background: transparent !important;
    box-shadow: none !important;
}

.sidebar-logo-img {
    width: 100%;
    height: 100%;
    object-fit: contain;
    border-radius: 8px;
}

.sidebar-logo-svg {
    width: 60%;
    height: 60%;
}

/* ========== Text 样式 ========== */
.sidebar-logo-text {
    font-size: 17px;
    font-weight: 700;
    letter-spacing: -0.02em;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.sidebar-logo--user .sidebar-logo-text {
    color: var(--user-text, #1e293b);
}

.sidebar-logo--admin .sidebar-logo-text {
    color: var(--admin-text, #1a1d21);
}

/* ========== 过渡动画 ========== */
.sidebar-logo-fade-enter-active,
.sidebar-logo-fade-leave-active {
    transition: opacity 0.2s ease;
}

.sidebar-logo-fade-enter-from,
.sidebar-logo-fade-leave-to {
    opacity: 0;
}
</style>
