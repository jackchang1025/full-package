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

// ========== 公共起点状态 ==========
const touchStartX = ref(0);
const touchStartY = ref(0);
const touchStartTime = ref(0);

// ========== 左键状态（点击 + 长按 + 拖拽滑动）==========
const isTouching = ref(false);
const isLeftDragging = ref(false);
const leftSwipeEndX = ref(0);
const leftSwipeEndY = ref(0);
const longPressTimer = ref<ReturnType<typeof setTimeout> | null>(null);

// ========== 右键状态（按住拖拽滑动）==========
const isRightDragging = ref(false);
const rightSwipeEndX = ref(0);
const rightSwipeEndY = ref(0);

// ========== 触摸设备状态（保留原始滑动逻辑）==========
const isTouchDevice = ref(false);

// ========== 常量 ==========
const LONG_PRESS_DURATION = 500;
/** 左键拖拽判定为滑动的最小距离（设备坐标像素） */
const SWIPE_THRESHOLD = 30;
/** 右键拖拽最小有效距离（设备坐标像素），低于此值不发送 swipe */
const RIGHT_SWIPE_MIN_DISTANCE = 10;

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

/**
 * 重置左键拖拽状态
 */
const resetLeftDrag = () => {
    isLeftDragging.value = false;
    leftSwipeEndX.value = 0;
    leftSwipeEndY.value = 0;
};

/**
 * 重置右键拖拽状态
 */
const resetRightDrag = () => {
    isRightDragging.value = false;
    rightSwipeEndX.value = 0;
    rightSwipeEndY.value = 0;
};

// ============================================================
// 鼠标交互约定：
//   左键 (button=0) → 点击(tap) / 长按(longpress) / 拖拽滑动(swipe)
//     - 按下后不移动或移动 <30px：松开时 → tap
//     - 按下后移动 ≥30px：进入拖拽滑动模式，松开时 → swipe
//     - 按住 500ms 不移动：→ longpress
//   右键 (button=2) → 按住拖拽 = 滑动(swipe)
//   触摸 (pointerType='touch') → 同左键逻辑
// ============================================================

const handlePointerDown = (event: PointerEvent) => {
    if (!props.isStreaming) return;

    const coords = getScaledCoordinates(event.clientX, event.clientY);
    touchStartX.value = coords.x;
    touchStartY.value = coords.y;
    touchStartTime.value = Date.now();

    // 触摸设备：标记以便 pointerup 里统一处理
    isTouchDevice.value = event.pointerType === 'touch';

    // 右键：进入拖拽滑动模式（右键只做滑动，不做 tap/longpress）
    if (event.button === 2) {
        isRightDragging.value = true;
        rightSwipeEndX.value = coords.x;
        rightSwipeEndY.value = coords.y;

        // 捕获指针，确保鼠标移出投屏区域后仍能收到 move/up 事件
        (event.target as HTMLElement).setPointerCapture(event.pointerId);
        return;
    }

    // 左键 / 触摸：点击 + 长按 + 拖拽滑动
    if (event.button === 0) {
        isTouching.value = true;
        isLeftDragging.value = false;
        leftSwipeEndX.value = coords.x;
        leftSwipeEndY.value = coords.y;

        // 捕获指针，左键拖拽到画面外也能收到 move/up
        (event.target as HTMLElement).setPointerCapture(event.pointerId);

        longPressTimer.value = setTimeout(() => {
            if (isTouching.value && !isLeftDragging.value) {
                emit('longpress', touchStartX.value, touchStartY.value);
                isTouching.value = false;
            }
        }, LONG_PRESS_DURATION);
    }
};

const handlePointerMove = (event: PointerEvent) => {
    // 右键拖拽中：持续更新终点坐标
    if (isRightDragging.value) {
        const coords = getScaledCoordinates(event.clientX, event.clientY);
        rightSwipeEndX.value = coords.x;
        rightSwipeEndY.value = coords.y;
        return;
    }

    // 左键 / 触摸拖拽中：跟踪移动距离，超过阈值则切换到滑动模式
    if (isTouching.value) {
        const coords = getScaledCoordinates(event.clientX, event.clientY);
        leftSwipeEndX.value = coords.x;
        leftSwipeEndY.value = coords.y;

        if (!isLeftDragging.value) {
            const deltaX = coords.x - touchStartX.value;
            const deltaY = coords.y - touchStartY.value;
            const distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance >= SWIPE_THRESHOLD) {
                // 移动超过阈值 → 进入左键滑动模式，取消长按定时器
                isLeftDragging.value = true;
                clearLongPressTimer();
            }
        }
    }
};

const handlePointerUp = (event: PointerEvent) => {
    // 右键松开：若拖拽距离够则发 swipe
    if (event.button === 2 && isRightDragging.value) {
        const deltaX = rightSwipeEndX.value - touchStartX.value;
        const deltaY = rightSwipeEndY.value - touchStartY.value;
        const distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance >= RIGHT_SWIPE_MIN_DISTANCE) {
            emit('swipe', touchStartX.value, touchStartY.value, rightSwipeEndX.value, rightSwipeEndY.value);
        }

        resetRightDrag();
        return;
    }

    // 左键 / 触摸松开
    if (event.button === 0 && isTouching.value) {
        clearLongPressTimer();

        if (isLeftDragging.value) {
            // 已进入滑动模式 → 发 swipe
            emit('swipe', touchStartX.value, touchStartY.value, leftSwipeEndX.value, leftSwipeEndY.value);
        } else {
            // 未超过阈值 → 发 tap
            emit('tap', touchStartX.value, touchStartY.value);
        }

        isTouching.value = false;
        resetLeftDrag();
    }
};

const handlePointerLeave = (event: PointerEvent) => {
    // setPointerCapture 生效时 pointerleave 不会触发，此处仅处理未捕获的边界情况
    if (!isRightDragging.value && !isTouching.value) {
        clearLongPressTimer();
    }
};

/**
 * 安全兜底：指针捕获丢失时重置拖拽状态，防止状态卡死。
 */
const handleLostPointerCapture = () => {
    // 右键拖拽中捕获丢失
    if (isRightDragging.value) {
        const deltaX = rightSwipeEndX.value - touchStartX.value;
        const deltaY = rightSwipeEndY.value - touchStartY.value;
        const distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance >= RIGHT_SWIPE_MIN_DISTANCE) {
            emit('swipe', touchStartX.value, touchStartY.value, rightSwipeEndX.value, rightSwipeEndY.value);
        }

        resetRightDrag();
    }

    // 左键拖拽中捕获丢失
    if (isTouching.value) {
        clearLongPressTimer();

        if (isLeftDragging.value) {
            emit('swipe', touchStartX.value, touchStartY.value, leftSwipeEndX.value, leftSwipeEndY.value);
        }

        isTouching.value = false;
        resetLeftDrag();
    }
};

onUnmounted(() => {
    clearLongPressTimer();
    resetLeftDrag();
    resetRightDrag();
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
                    :class="{ 'is-dragging': isRightDragging || isLeftDragging }"
                    draggable="false"
                    @pointerdown="handlePointerDown"
                    @pointermove="handlePointerMove"
                    @pointerup="handlePointerUp"
                    @pointerleave="handlePointerLeave"
                    @lostpointercapture="handleLostPointerCapture"
                    @contextmenu.prevent
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

.screen-image.is-dragging {
    cursor: crosshair;
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
