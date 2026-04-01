<script setup lang="ts">
import { ref, computed } from 'vue';
import { NIcon, NButton, NModal } from 'naive-ui';
import { PlayOutline, StopOutline, ExpandOutline } from '@vicons/ionicons5';

interface NodeInfo {
    text?: string; desc?: string; cls?: string; id?: string; hint?: string;
    x: number; y: number; l: number; t: number; r: number; b: number;
    click?: boolean; edit?: boolean; focus?: boolean; checked?: boolean;
    pwd?: boolean; scroll?: boolean; depth: number; index: number;
}

interface NodeTree {
    windowTitle?: string; activePackage?: string; activeWindow?: string;
    children?: NodeInfo[];
}

interface Props { nodeTree?: NodeTree | null; isRunning?: boolean; }
interface Emits {
    (e: 'start'): void; (e: 'stop'): void;
    (e: 'tap', x: number, y: number): void;
    (e: 'swipe', sx: number, sy: number, ex: number, ey: number): void;
    (e: 'longpress', x: number, y: number): void;
}

const props = withDefaults(defineProps<Props>(), { nodeTree: null, isRunning: false });
const emit = defineEmits<Emits>();
const showFullscreen = ref(false);

const nodes = computed(() => props.nodeTree?.children || []);
const windowInfo = computed(() => {
    if (!props.nodeTree) return '';
    return [props.nodeTree.windowTitle, props.nodeTree.activePackage].filter(Boolean).join(' · ') || '';
});

// 计算屏幕边界
const screenBounds = computed(() => {
    const ns = nodes.value;
    if (!ns.length) return { maxX: 1080, maxY: 1920 };
    let maxX = 0, maxY = 0;
    for (const n of ns) {
        if (n.r > maxX) maxX = n.r;
        if (n.b > maxY) maxY = n.b;
    }
    return { maxX: maxX || 1080, maxY: maxY || 1920 };
});

// 只显示有文字的节点（过滤空节点减少噪音）
const visibleNodes = computed(() => {
    return nodes.value.filter(n => {
        const label = n.text || n.desc || n.hint || '';
        const w = n.r - n.l;
        const h = n.b - n.t;
        // 有文字，或者是可交互的有尺寸的节点
        return (label.length > 0 && w > 0 && h > 0) || (n.pwd && w > 0 && h > 0);
    });
});

const nodeLabel = (n: NodeInfo): string => {
    if (n.pwd) return '••••••';
    return n.text || n.desc || n.hint || '';
};

const nodeBorderColor = (n: NodeInfo): string => {
    if (n.pwd) return '#ef4444';
    if (n.edit) return '#f59e0b';
    if (n.focus) return '#10b981';
    if (n.click) return '#3b82f6';
    return '#cbd5e1';
};

const nodeBg = (n: NodeInfo): string => {
    if (n.click) return 'rgba(59,130,246,0.06)';
    if (n.edit) return 'rgba(245,158,11,0.06)';
    return 'transparent';
};

const handleNodeClick = (n: NodeInfo, event: MouseEvent) => {
    event.stopPropagation();
    emit('tap', n.x, n.y);
};

// 点击空白区域
const handleScreenClick = (event: MouseEvent, container: HTMLElement | null) => {
    if (!container) return;
    const rect = container.getBoundingClientRect();
    const { maxX, maxY } = screenBounds.value;
    const scaleX = maxX / rect.width;
    const scaleY = maxY / rect.height;
    const x = Math.round((event.clientX - rect.left) * scaleX);
    const y = Math.round((event.clientY - rect.top) * scaleY);
    emit('tap', x, y);
};

const screenRef = ref<HTMLElement | null>(null);
const fullscreenRef = ref<HTMLElement | null>(null);
</script>

<template>
    <div class="text-assist-panel">
        <div class="panel-header">
            <div class="header-title">
                <span class="title-icon">A</span>
                <span class="title-text">文字辅助</span>
                <span v-if="isRunning && nodes.length" class="node-count">{{ visibleNodes.length }}</span>
            </div>
            <div class="header-actions">
                <NButton size="tiny" quaternary :disabled="!nodeTree" @click="showFullscreen = true">
                    <template #icon><NIcon :component="ExpandOutline" :size="14" /></template>
                    放大
                </NButton>
                <NButton size="tiny" :type="isRunning ? 'error' : 'success'" @click="isRunning ? emit('stop') : emit('start')">
                    <template #icon><NIcon :component="isRunning ? StopOutline : PlayOutline" :size="14" /></template>
                    {{ isRunning ? '停止' : '开启' }}
                </NButton>
            </div>
        </div>

        <div v-if="isRunning && windowInfo" class="window-info">{{ windowInfo }}</div>

        <!-- 可视化屏幕 -->
        <div class="screen-container">
            <template v-if="isRunning && visibleNodes.length">
                <div
                    ref="screenRef"
                    class="screen-canvas"
                    :style="{ aspectRatio: screenBounds.maxX + '/' + screenBounds.maxY }"
                    @click="handleScreenClick($event, screenRef)"
                >
                    <div
                        v-for="(n, i) in visibleNodes"
                        :key="i"
                        class="node-box"
                        :style="{
                            left: (n.l / screenBounds.maxX * 100) + '%',
                            top: (n.t / screenBounds.maxY * 100) + '%',
                            width: ((n.r - n.l) / screenBounds.maxX * 100) + '%',
                            height: ((n.b - n.t) / screenBounds.maxY * 100) + '%',
                            borderColor: nodeBorderColor(n),
                            background: nodeBg(n),
                            cursor: n.click ? 'pointer' : 'default',
                        }"
                        :title="`${nodeLabel(n)} (${n.x},${n.y})`"
                        @click="handleNodeClick(n, $event)"
                    >
                        <span class="node-label" :style="{ color: nodeBorderColor(n) }">{{ nodeLabel(n) }}</span>
                    </div>
                </div>
            </template>
            <template v-else-if="isRunning">
                <div class="placeholder loading">
                    <span class="placeholder-icon">A</span>
                    <div>正在读取节点树...</div>
                </div>
            </template>
            <template v-else>
                <div class="placeholder" @click="emit('start')">
                    <span class="placeholder-icon">A</span>
                    <div>点击开启文字辅助</div>
                    <div class="placeholder-sub">PIN 界面可用</div>
                </div>
            </template>
        </div>

        <!-- 放大 -->
        <NModal v-model:show="showFullscreen" preset="card" title="文字辅助" style="width: 420px; max-width: 95vw;" :bordered="false">
            <div v-if="windowInfo" class="window-info">{{ windowInfo }}</div>
            <div
                ref="fullscreenRef"
                class="screen-canvas fullscreen"
                :style="{ aspectRatio: screenBounds.maxX + '/' + screenBounds.maxY }"
                @click="handleScreenClick($event, fullscreenRef)"
            >
                <div
                    v-for="(n, i) in visibleNodes"
                    :key="i"
                    class="node-box"
                    :style="{
                        left: (n.l / screenBounds.maxX * 100) + '%',
                        top: (n.t / screenBounds.maxY * 100) + '%',
                        width: ((n.r - n.l) / screenBounds.maxX * 100) + '%',
                        height: ((n.b - n.t) / screenBounds.maxY * 100) + '%',
                        borderColor: nodeBorderColor(n),
                        background: nodeBg(n),
                        cursor: n.click ? 'pointer' : 'default',
                    }"
                    :title="`${nodeLabel(n)} (${n.x},${n.y})`"
                    @click="handleNodeClick(n, $event)"
                >
                    <span class="node-label" :style="{ color: nodeBorderColor(n) }">{{ nodeLabel(n) }}</span>
                </div>
            </div>
        </NModal>
    </div>
</template>

<style scoped>
.text-assist-panel {
    display: flex; flex-direction: column; background: white;
    border-radius: 12px; overflow: hidden;
    border: 1px solid #e5e7eb; box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}
.panel-header {
    display: flex; align-items: center; justify-content: space-between;
    padding: 10px 14px; background: #f8fafc; border-bottom: 1px solid #e5e7eb;
}
.header-title { display: flex; align-items: center; gap: 8px; }
.title-icon {
    width: 24px; height: 24px; display: flex; align-items: center; justify-content: center;
    background: linear-gradient(135deg, #10B981, #059669); color: white;
    font-weight: 700; font-size: 14px; border-radius: 6px;
}
.title-text { font-size: 13px; font-weight: 500; color: #1e293b; }
.node-count {
    font-size: 10px; color: #64748b; background: #e2e8f0;
    padding: 1px 6px; border-radius: 8px; font-weight: 500;
}
.header-actions { display: flex; gap: 6px; }
.window-info {
    padding: 3px 14px; font-size: 10px; color: #94a3b8; background: #fafafa;
    border-bottom: 1px solid #f1f5f9; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;
}
.screen-container { flex: 1; display: flex; align-items: center; justify-content: center; background: #1e293b; min-height: 200px; }
.screen-canvas {
    position: relative; width: 100%; background: #0f172a; overflow: hidden;
}
.screen-canvas.fullscreen { max-height: 70vh; }
.node-box {
    position: absolute; border: 1px solid; box-sizing: border-box;
    display: flex; align-items: center; justify-content: center;
    overflow: hidden; transition: background 0.1s;
}
.node-box:hover { background: rgba(255,255,255,0.1) !important; }
.node-label {
    font-size: 9px; line-height: 1.2; text-align: center;
    overflow: hidden; text-overflow: ellipsis; white-space: nowrap;
    padding: 0 2px; max-width: 100%; font-weight: 500;
}
.placeholder {
    display: flex; flex-direction: column; align-items: center; justify-content: center;
    gap: 8px; color: #64748b; padding: 40px; cursor: pointer;
}
.placeholder.loading { cursor: default; color: #10B981; }
.placeholder-icon { font-size: 36px; font-weight: 700; color: #475569; }
.placeholder-sub { font-size: 11px; color: #94a3b8; }
.header-actions :deep(.n-button) { font-size: 12px; }
</style>
