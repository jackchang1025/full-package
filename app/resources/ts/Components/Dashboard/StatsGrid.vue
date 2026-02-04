<script setup lang="ts">
import { computed, type Component } from 'vue';
import { router } from '@inertiajs/vue3';
import { NIcon, NProgress } from 'naive-ui';
import { TrendingUpOutline, ArrowForwardOutline } from '@vicons/ionicons5';

export interface StatCard {
    key: string;
    title: string;
    icon: Component;
    color: string;
    bgColor?: string;
    route?: string;
    suffix?: string;
    trend?: string;
    trendUp?: boolean;
    progress?: number;
}

interface Props {
    stats: Record<string, number>;
    cards: StatCard[];
    clickable?: boolean;
    variant?: 'default' | 'admin';
}

const props = withDefaults(defineProps<Props>(), {
    clickable: false,
    variant: 'default',
});

const goTo = (route?: string) => {
    if (route) {
        router.visit(route);
    }
};

const getCardBgColor = (card: StatCard): string => {
    return card.bgColor || `${card.color}18`;
};
</script>

<template>
    <div :class="['stats-grid', `stats-grid--${variant}`]">
        <div
            v-for="card in cards"
            :key="card.key"
            :class="['stat-card', { clickable: clickable && card.route }]"
            @click="clickable && card.route ? goTo(card.route) : undefined"
        >
            <div v-if="variant === 'default'" class="stat-header">
                <div class="stat-icon" :style="{ background: getCardBgColor(card), color: card.color }">
                    <NIcon :component="card.icon" size="24" />
                </div>
                <div v-if="card.trend" class="stat-trend" :class="{ up: card.trendUp }">
                    <NIcon :component="TrendingUpOutline" size="14" />
                    {{ card.trend }}
                </div>
            </div>
            
            <div v-if="variant === 'admin'" class="admin-stat-icon" :style="{ background: getCardBgColor(card), color: card.color }">
                <NIcon :component="card.icon" size="28" />
            </div>
            
            <div :class="variant === 'admin' ? 'admin-stat-body' : 'stat-body'">
                <div :class="variant === 'admin' ? 'admin-stat-value' : 'stat-value'">
                    {{ stats[card.key] ?? 0 }}
                    <span v-if="card.suffix" class="stat-suffix">{{ card.suffix }}</span>
                </div>
                <div :class="variant === 'admin' ? 'admin-stat-title' : 'stat-title'">{{ card.title }}</div>
            </div>
            
            <div v-if="card.progress !== undefined && variant === 'default'" class="stat-progress">
                <NProgress
                    type="line"
                    :percentage="card.progress"
                    :color="card.color"
                    :rail-color="getCardBgColor(card)"
                    :height="6"
                    :show-indicator="false"
                />
            </div>
            
            <NIcon v-if="variant === 'admin' && clickable && card.route" :component="ArrowForwardOutline" class="admin-stat-arrow" />
        </div>
    </div>
</template>

<style scoped>
/* Default variant styles */
.stats-grid {
    display: grid;
    gap: 20px;
}

.stats-grid--default {
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
}

.stats-grid--admin {
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 24px;
}

.stat-card {
    background: white;
    border-radius: 16px;
    padding: 24px;
    border: 1px solid #e2e8f0;
    transition: all 0.3s ease;
}

.stat-card.clickable {
    cursor: pointer;
}

.stat-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.08);
    border-color: transparent;
}

.stat-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 16px;
}

.stat-icon {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.stat-trend {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 13px;
    font-weight: 600;
    color: #10B981;
    background: rgba(16, 185, 129, 0.1);
    padding: 4px 10px;
    border-radius: 20px;
}

.stat-body {
    margin-bottom: 8px;
}

.stat-value {
    font-size: 32px;
    font-weight: 700;
    color: #1e293b;
    line-height: 1.2;
}

.stat-suffix {
    font-size: 16px;
    font-weight: 500;
    color: #64748b;
    margin-left: 4px;
}

.stat-title {
    font-size: 14px;
    color: #64748b;
    margin-top: 4px;
}

.stat-progress {
    margin-top: 12px;
}

/* Admin variant styles */
.stats-grid--admin .stat-card {
    display: flex;
    align-items: center;
    gap: 20px;
    padding: 28px;
    border-radius: var(--admin-radius-lg, 16px);
    background: var(--admin-surface, #fff);
    border: 1px solid var(--admin-border, rgba(0,0,0,.06));
}

.stats-grid--admin .stat-card:hover {
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

.stats-grid--admin .stat-card:hover .admin-stat-arrow {
    color: var(--admin-accent, #0d9488);
    transform: translateX(4px);
}

/* Responsive */
@media (max-width: 1024px) {
    .stats-grid--default {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 640px) {
    .stats-grid--default,
    .stats-grid--admin {
        grid-template-columns: 1fr;
    }
}
</style>
