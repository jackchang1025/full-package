<script setup lang="ts">
import { NSkeleton } from 'naive-ui';

interface Props {
    type?: 'list' | 'card' | 'text';
    rows?: number;
}

const props = withDefaults(defineProps<Props>(), {
    type: 'list',
    rows: 5,
});
</script>

<template>
    <div class="loading-skeleton">
        <!-- List skeleton -->
        <div v-if="type === 'list'" class="skeleton-list">
            <div v-for="i in rows" :key="i" class="skeleton-item">
                <NSkeleton circle :width="40" :height="40" />
                <div class="skeleton-content">
                    <NSkeleton text :width="'60%'" />
                    <NSkeleton text :width="'40%'" style="margin-top: 8px" />
                </div>
            </div>
        </div>

        <!-- Card skeleton -->
        <div v-else-if="type === 'card'" class="skeleton-grid">
            <div v-for="i in rows" :key="i" class="skeleton-card">
                <NSkeleton :height="120" />
                <NSkeleton text :width="'80%'" style="margin-top: 12px" />
                <NSkeleton text :width="'60%'" style="margin-top: 8px" />
            </div>
        </div>

        <!-- Text skeleton -->
        <div v-else class="skeleton-text">
            <NSkeleton text v-for="i in rows" :key="i" :width="`${Math.random() * 30 + 60}%`" />
        </div>
    </div>
</template>

<style scoped>
.loading-skeleton {
    animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}

.skeleton-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.skeleton-item {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 16px;
    background: #f8fafc;
    border-radius: 12px;
    animation: shimmer 1.5s infinite;
}

.skeleton-content {
    flex: 1;
}

.skeleton-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 16px;
}

.skeleton-card {
    padding: 16px;
    background: #f8fafc;
    border-radius: 12px;
    animation: shimmer 1.5s infinite;
}

.skeleton-text {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

@keyframes shimmer {
    0% {
        background-position: -1000px 0;
    }
    100% {
        background-position: 1000px 0;
    }
}

.skeleton-item,
.skeleton-card {
    background: linear-gradient(
        90deg,
        #f8fafc 0%,
        #f1f5f9 50%,
        #f8fafc 100%
    );
    background-size: 1000px 100%;
}
</style>
