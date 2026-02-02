<script setup lang="ts">
import { ref, computed, onUnmounted } from 'vue';
import { NSpin } from 'naive-ui';

interface Props {
    screenData: string | null;
    screenWidth: number;
    screenHeight: number;
    isStreaming: boolean;
    loading?: boolean;
}

interface Emits {
    (e: 'tap', x: number, y: number): void;
    (e: 'swipe', startX: number, startY: number, endX: number, endY: number): void;
    (e: 'longpress', x: number, y: number): void;
}

const props = withDefaults(defineProps<Props>(), {
    loading: false,
});

const emit = defineEmits<Emits>();

const containerRef = ref<HTMLDivElement | null>(null);
const imageRef = ref<HTMLImageElement | null>(null);

const touchStartX = ref(0);
const touchStartY = ref(0);
const touchStartTime = ref(0);
const isTouching = ref(false);
const longPressTimer = ref<ReturnType<typeof setTimeout> | null>(null);

const LONG_PRESS_DURATION = 500;
const SWIPE_THRESHOLD = 30;

const imageSrc = computed(() => {
    if (!props.screenData) return '';
    if (props.screenData.startsWith('data:')) return props.screenData;
    return `data:image/jpeg;base64,${props.screenData}`;
});

/**
 * 按 object-fit: contain 的实际绘制区域做坐标映射，避免留白导致底部点击偏移。
 * 图片在容器内等比居中时，只有「内容矩形」对应设备屏幕，需用内容区 rect 换算。
 */
const getScaledCoordinates = (clientX: number, clientY: number) => {
    if (!imageRef.value) return { x: 0, y: 0 };

    const rect = imageRef.value.getBoundingClientRect();
    const scale = Math.min(
        rect.width / props.screenWidth,
        rect.height / props.screenHeight
    );
    const contentWidth = props.screenWidth * scale;
    const contentHeight = props.screenHeight * scale;
    const contentLeft = rect.left + (rect.width - contentWidth) / 2;
    const contentTop = rect.top + (rect.height - contentHeight) / 2;

    const x = ((clientX - contentLeft) / contentWidth) * props.screenWidth;
    const y = ((clientY - contentTop) / contentHeight) * props.screenHeight;

    return {
        x: Math.max(0, Math.min(props.screenWidth, Math.round(x))),
        y: Math.max(0, Math.min(props.screenHeight, Math.round(y))),
    };
};

const clearLongPressTimer = () => {
    if (longPressTimer.value) {
        clearTimeout(longPressTimer.value);
        longPressTimer.value = null;
    }
};

const handlePointerDown = (event: PointerEvent) => {
    if (!props.isStreaming) return;

    const coords = getScaledCoordinates(event.clientX, event.clientY);
    touchStartX.value = coords.x;
    touchStartY.value = coords.y;
    touchStartTime.value = Date.now();
    isTouching.value = true;

    longPressTimer.value = setTimeout(() => {
        if (isTouching.value) {
            emit('longpress', touchStartX.value, touchStartY.value);
            isTouching.value = false;
        }
    }, LONG_PRESS_DURATION);
};

const handlePointerUp = (event: PointerEvent) => {
    if (!isTouching.value) return;

    clearLongPressTimer();
    const coords = getScaledCoordinates(event.clientX, event.clientY);
    const deltaX = coords.x - touchStartX.value;
    const deltaY = coords.y - touchStartY.value;
    const distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    if (distance > SWIPE_THRESHOLD) {
        emit('swipe', touchStartX.value, touchStartY.value, coords.x, coords.y);
    } else {
        emit('tap', touchStartX.value, touchStartY.value);
    }

    isTouching.value = false;
};

const handlePointerLeave = () => {
    clearLongPressTimer();
    isTouching.value = false;
};

onUnmounted(() => {
    clearLongPressTimer();
});
</script>

<template>
    <div class="screen-viewer">
        <div
            ref="containerRef"
            class="screen-container"
        >
            <NSpin v-if="loading" class="loading-spinner" />
            <template v-else-if="isStreaming && screenData">
                <img
                    ref="imageRef"
                    :src="imageSrc"
                    class="screen-image"
                    draggable="false"
                    @pointerdown="handlePointerDown"
                    @pointerup="handlePointerUp"
                    @pointerleave="handlePointerLeave"
                />
            </template>
            <div v-else class="empty-state">
                <div class="empty-icon">📱</div>
                <div class="empty-text">点击"开启"启动投屏</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.screen-viewer {
    background: transparent;
    height: 100%;
}

.screen-viewer :deep(.n-card__content) {
    padding: 0 !important;
    height: 100%;
}

.screen-container {
    position: relative;
    width: 100%;
    height: 100%;
    background: #000;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
}

.screen-image {
    width: 100%;
    height: 100%;
    object-fit: contain;
    cursor: pointer;
    touch-action: none;
    user-select: none;
}

.loading-spinner {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    color: #666;
    gap: 12px;
}

.empty-icon {
    font-size: 48px;
    opacity: 0.5;
}

.empty-text {
    font-size: 14px;
    color: #888;
}
</style>
