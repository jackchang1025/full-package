<script setup lang="ts">
import { ref, computed } from 'vue';
import { NIcon, NButton, NModal } from 'naive-ui';
import {
    PlayOutline,
    StopOutline,
    ExpandOutline,
    TextOutline,
} from '@vicons/ionicons5';

interface Props {
    /** OCR 屏幕图像数据 (base64) */
    screenData?: string | null;
    /** 屏幕宽度 */
    screenWidth?: number;
    /** 屏幕高度 */
    screenHeight?: number;
    /** 是否正在运行 OCR */
    isRunning?: boolean;
}

interface Emits {
    (e: 'start'): void;
    (e: 'stop'): void;
    (e: 'tap', x: number, y: number): void;
    (e: 'swipe', startX: number, startY: number, endX: number, endY: number): void;
    (e: 'longpress', x: number, y: number): void;
}

const props = withDefaults(defineProps<Props>(), {
    screenData: null,
    screenWidth: 1080,
    screenHeight: 1920,
    isRunning: false,
});

const emit = defineEmits<Emits>();

// 放大模态框
const showFullscreen = ref(false);

// 图片元素引用
const imageRef = ref<HTMLImageElement | null>(null);
const fullscreenImageRef = ref<HTMLImageElement | null>(null);

// 触摸状态
const touchStartX = ref(0);
const touchStartY = ref(0);
const isTouching = ref(false);
const isClick = ref(true);
const longPressTimer = ref<ReturnType<typeof setTimeout> | null>(null);

// 阈值配置 (与 info.php 一致)
const LONG_PRESS_DURATION = 350;
const MOVE_THRESHOLD = 5;

// 计算图片 src
const imageSrc = computed(() => {
    if (!props.screenData) return '';
    if (props.screenData.startsWith('data:')) return props.screenData;
    return `data:image/jpeg;base64,${props.screenData}`;
});

/**
 * 按 object-fit: contain 的实际绘制区域做坐标映射，避免留白导致底部点击偏移。
 * 图片在容器内等比居中时，只有「内容矩形」对应设备屏幕，需用内容区 rect 换算。
 */
const getScaledCoordinates = (
    event: MouseEvent | Touch,
    imgEl: HTMLImageElement | null
): { x: number; y: number } => {
    if (!imgEl) return { x: 0, y: 0 };

    const rect = imgEl.getBoundingClientRect();
    const scale = Math.min(
        rect.width / props.screenWidth,
        rect.height / props.screenHeight
    );
    const contentWidth = props.screenWidth * scale;
    const contentHeight = props.screenHeight * scale;
    const contentLeft = rect.left + (rect.width - contentWidth) / 2;
    const contentTop = rect.top + (rect.height - contentHeight) / 2;

    const clientX = event.clientX;
    const clientY = event.clientY;
    const x = ((clientX - contentLeft) / contentWidth) * props.screenWidth;
    const y = ((clientY - contentTop) / contentHeight) * props.screenHeight;

    return {
        x: Math.max(0, Math.min(props.screenWidth, Math.round(x))),
        y: Math.max(0, Math.min(props.screenHeight, Math.round(y))),
    };
};

// 清除长按计时器
const clearLongPressTimer = () => {
    if (longPressTimer.value) {
        clearTimeout(longPressTimer.value);
        longPressTimer.value = null;
    }
};

// 鼠标/触摸事件处理
const handlePointerDown = (event: MouseEvent, imgEl: HTMLImageElement | null) => {
    if (!props.isRunning) return;
    event.preventDefault();

    const coords = getScaledCoordinates(event, imgEl);
    touchStartX.value = coords.x;
    touchStartY.value = coords.y;
    isTouching.value = true;
    isClick.value = true;

    longPressTimer.value = setTimeout(() => {
        if (isTouching.value && isClick.value) {
            emit('longpress', touchStartX.value, touchStartY.value);
            isTouching.value = false;
        }
    }, LONG_PRESS_DURATION);
};

const handlePointerMove = (event: MouseEvent, imgEl: HTMLImageElement | null) => {
    if (!isTouching.value || !imgEl) return;

    const coords = getScaledCoordinates(event, imgEl);

    if (
        Math.abs(coords.x - touchStartX.value) > MOVE_THRESHOLD ||
        Math.abs(coords.y - touchStartY.value) > MOVE_THRESHOLD
    ) {
        isClick.value = false;
        clearLongPressTimer();
    }
};

const handlePointerUp = (event: MouseEvent, imgEl: HTMLImageElement | null) => {
    if (!isTouching.value) return;
    event.preventDefault();

    clearLongPressTimer();
    const coords = getScaledCoordinates(event, imgEl);

    if (isClick.value) {
        emit('tap', touchStartX.value, touchStartY.value);
    } else {
        emit('swipe', touchStartX.value, touchStartY.value, coords.x, coords.y);
    }

    isTouching.value = false;
};

const handlePointerLeave = () => {
    clearLongPressTimer();
    isTouching.value = false;
};

// 开启/停止
const handleToggle = () => {
    if (props.isRunning) {
        emit('stop');
    } else {
        emit('start');
    }
};
</script>

<template>
    <div class="text-assist-panel">
        <!-- 头部 -->
        <div class="panel-header">
            <div class="header-title">
                <span class="title-icon">A</span>
                <span class="title-text">文字辅助</span>
            </div>
            <div class="header-actions">
                <NButton
                    size="tiny"
                    quaternary
                    :disabled="!screenData"
                    @click="showFullscreen = true"
                >
                    <template #icon>
                        <NIcon :component="ExpandOutline" :size="14" />
                    </template>
                    放大
                </NButton>
                <NButton
                    size="tiny"
                    :type="isRunning ? 'default' : 'success'"
                    @click="emit('start')"
                    :disabled="isRunning"
                >
                    <template #icon>
                        <NIcon :component="PlayOutline" :size="14" />
                    </template>
                    开启
                </NButton>
                <NButton
                    size="tiny"
                    type="error"
                    @click="emit('stop')"
                    :disabled="!isRunning"
                >
                    <template #icon>
                        <NIcon :component="StopOutline" :size="14" />
                    </template>
                    停止
                </NButton>
            </div>
        </div>

        <!-- OCR 屏幕显示区域 -->
        <div class="screen-display">
            <!-- 有屏幕数据时显示图像 -->
            <template v-if="isRunning && screenData">
                <img
                    ref="imageRef"
                    :src="imageSrc"
                    class="screen-image"
                    draggable="false"
                    @mousedown="handlePointerDown($event, imageRef)"
                    @mousemove="handlePointerMove($event, imageRef)"
                    @mouseup="handlePointerUp($event, imageRef)"
                    @mouseleave="handlePointerLeave"
                />
            </template>

            <!-- 加载中状态 -->
            <template v-else-if="isRunning && !screenData">
                <div class="screen-placeholder loading">
                    <NIcon :component="TextOutline" :size="48" class="placeholder-icon spinning" />
                    <div class="placeholder-text">正在连接...</div>
                </div>
            </template>

            <!-- 默认占位符 -->
            <template v-else>
                <div class="screen-placeholder" @click="emit('start')">
                    <span class="placeholder-icon-text">A</span>
                    <div class="placeholder-text">点击开启文字识别</div>
                </div>
            </template>
        </div>

        <!-- 放大模态框 -->
        <NModal
            v-model:show="showFullscreen"
            preset="card"
            title="文字辅助 - 放大视图"
            style="width: 90vw; max-width: 800px;"
            :bordered="false"
        >
            <div class="fullscreen-display">
                <img
                    v-if="screenData"
                    ref="fullscreenImageRef"
                    :src="imageSrc"
                    class="fullscreen-image"
                    draggable="false"
                    @mousedown="handlePointerDown($event, fullscreenImageRef)"
                    @mousemove="handlePointerMove($event, fullscreenImageRef)"
                    @mouseup="handlePointerUp($event, fullscreenImageRef)"
                    @mouseleave="handlePointerLeave"
                />
                <div v-else class="screen-placeholder">
                    <span class="placeholder-icon-text">A</span>
                    <div class="placeholder-text">暂无屏幕数据</div>
                </div>
            </div>
        </NModal>
    </div>
</template>

<style scoped>
.text-assist-panel {
    display: flex;
    flex-direction: column;
    background: white;
    border-radius: 12px;
    overflow: hidden;
    height: 100%;
    border: 1px solid #e5e7eb;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.panel-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 14px;
    background: #f8fafc;
    border-bottom: 1px solid #e5e7eb;
}

.header-title {
    display: flex;
    align-items: center;
    gap: 8px;
}

.title-icon {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #10B981, #059669);
    color: white;
    font-weight: 700;
    font-size: 14px;
    border-radius: 6px;
}

.title-text {
    font-size: 13px;
    font-weight: 500;
    color: #1e293b;
}

.header-actions {
    display: flex;
    gap: 6px;
    align-items: center;
}

.screen-display {
    flex: 1;
    min-height: 200px;
    background: #f1f5f9;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    cursor: pointer;
}

.screen-image {
    width: 100%;
    height: 100%;
    object-fit: contain;
    display: block;
    user-select: none;
    -webkit-user-drag: none;
}

.screen-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 12px;
    color: #94a3b8;
    padding: 40px;
    cursor: pointer;
    transition: all 0.2s ease;
}

.screen-placeholder:hover {
    color: #64748b;
}

.screen-placeholder.loading {
    cursor: default;
    color: #10B981;
}

.placeholder-icon-text {
    font-size: 48px;
    font-weight: 700;
    color: #cbd5e1;
}

.placeholder-icon {
    opacity: 0.6;
}

.placeholder-icon.spinning {
    animation: spin 1.5s linear infinite;
}

@keyframes spin {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}

.placeholder-text {
    font-size: 13px;
    color: inherit;
}

.fullscreen-display {
    background: #f1f5f9;
    border-radius: 8px;
    overflow: hidden;
    min-height: 400px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.fullscreen-image {
    max-width: 100%;
    max-height: 70vh;
    object-fit: contain;
    user-select: none;
    -webkit-user-drag: none;
}

/* 按钮样式覆盖 */
.header-actions :deep(.n-button) {
    font-size: 12px;
}

.header-actions :deep(.n-button--default-type) {
    color: #64748b;
}

.header-actions :deep(.n-button--default-type:hover) {
    color: #1e293b;
}
</style>
