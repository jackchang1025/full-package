<script setup lang="ts">
import { ref, computed } from 'vue';
import { NIcon, NButton, NModal, NScrollbar } from 'naive-ui';
import {
    PlayOutline,
    StopOutline,
    ExpandOutline,
} from '@vicons/ionicons5';

interface NodeInfo {
    text?: string;
    desc?: string;
    cls?: string;
    id?: string;
    hint?: string;
    x: number;
    y: number;
    l: number;
    t: number;
    r: number;
    b: number;
    click?: boolean;
    edit?: boolean;
    focus?: boolean;
    checked?: boolean;
    pwd?: boolean;
    scroll?: boolean;
    depth: number;
    index: number;
}

interface NodeTree {
    windowTitle?: string;
    activePackage?: string;
    activeWindow?: string;
    children?: NodeInfo[];
}

interface Props {
    nodeTree?: NodeTree | null;
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
    nodeTree: null,
    isRunning: false,
});

const emit = defineEmits<Emits>();
const showFullscreen = ref(false);

const nodes = computed(() => props.nodeTree?.children || []);
const windowInfo = computed(() => {
    if (!props.nodeTree) return '';
    const parts: string[] = [];
    if (props.nodeTree.windowTitle) parts.push(props.nodeTree.windowTitle);
    if (props.nodeTree.activePackage) parts.push(props.nodeTree.activePackage);
    return parts.join(' · ') || '未知窗口';
});

const nodeLabel = (n: NodeInfo): string => {
    if (n.pwd) return '●●●●●●';
    return n.text || n.desc || n.hint || '';
};

const nodeTypeTag = (n: NodeInfo): string => {
    const cls = n.cls || '';
    if (cls.includes('Button')) return 'BTN';
    if (cls.includes('EditText')) return 'INPUT';
    if (cls.includes('CheckBox') || cls.includes('Switch')) return 'CHK';
    if (cls.includes('Image')) return 'IMG';
    if (cls.includes('RecyclerView') || cls.includes('ListView') || cls.includes('ScrollView')) return 'LIST';
    if (n.click) return 'TAP';
    if (n.scroll) return 'SCROLL';
    return '';
};

const nodeColor = (n: NodeInfo): string => {
    if (n.pwd) return '#ef4444';
    if (n.edit) return '#f59e0b';
    if (n.click) return '#3b82f6';
    if (n.focus) return '#10b981';
    if (n.checked) return '#8b5cf6';
    return '#64748b';
};

const handleNodeClick = (n: NodeInfo) => {
    emit('tap', n.x, n.y);
};
</script>

<template>
    <div class="text-assist-panel">
        <div class="panel-header">
            <div class="header-title">
                <span class="title-icon">A</span>
                <span class="title-text">文字辅助</span>
                <span v-if="isRunning && nodes.length" class="node-count">{{ nodes.length }} 节点</span>
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

        <!-- 窗口信息 -->
        <div v-if="isRunning && windowInfo" class="window-info">{{ windowInfo }}</div>

        <!-- 节点列表 -->
        <div class="node-list-container">
            <template v-if="isRunning && nodes.length">
                <NScrollbar style="max-height: 500px;">
                    <div class="node-list">
                        <div
                            v-for="(n, i) in nodes"
                            :key="i"
                            class="node-item"
                            :class="{ clickable: n.click, editable: n.edit, password: n.pwd }"
                            :style="{ paddingLeft: Math.min(n.depth * 8, 48) + 8 + 'px', borderLeftColor: nodeColor(n) }"
                            @click="handleNodeClick(n)"
                        >
                            <span v-if="nodeTypeTag(n)" class="node-tag" :style="{ background: nodeColor(n) }">{{ nodeTypeTag(n) }}</span>
                            <span class="node-text" :style="{ color: nodeColor(n) }">{{ nodeLabel(n) || '(空)' }}</span>
                            <span class="node-coords">{{ n.x }},{{ n.y }}</span>
                        </div>
                    </div>
                </NScrollbar>
            </template>
            <template v-else-if="isRunning">
                <div class="placeholder loading">
                    <span class="placeholder-icon">A</span>
                    <div class="placeholder-text">正在读取节点树...</div>
                </div>
            </template>
            <template v-else>
                <div class="placeholder" @click="emit('start')">
                    <span class="placeholder-icon">A</span>
                    <div class="placeholder-text">点击开启文字辅助</div>
                    <div class="placeholder-sub">读取屏幕 UI 节点树，PIN 界面可用</div>
                </div>
            </template>
        </div>

        <!-- 放大模态框 -->
        <NModal v-model:show="showFullscreen" preset="card" title="文字辅助 - 节点树" style="width: 90vw; max-width: 700px;" :bordered="false">
            <div v-if="windowInfo" class="window-info">{{ windowInfo }}</div>
            <NScrollbar style="max-height: 70vh;">
                <div class="node-list">
                    <div
                        v-for="(n, i) in nodes"
                        :key="i"
                        class="node-item"
                        :class="{ clickable: n.click, editable: n.edit, password: n.pwd }"
                        :style="{ paddingLeft: Math.min(n.depth * 12, 72) + 12 + 'px', borderLeftColor: nodeColor(n) }"
                        @click="handleNodeClick(n)"
                    >
                        <span v-if="nodeTypeTag(n)" class="node-tag" :style="{ background: nodeColor(n) }">{{ nodeTypeTag(n) }}</span>
                        <span class="node-text" :style="{ color: nodeColor(n) }">{{ nodeLabel(n) || '(空)' }}</span>
                        <span v-if="n.id" class="node-id">{{ n.id.split('/').pop() }}</span>
                        <span class="node-coords">{{ n.x }},{{ n.y }}</span>
                    </div>
                </div>
            </NScrollbar>
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
.header-title { display: flex; align-items: center; gap: 8px; }
.title-icon {
    width: 24px; height: 24px; display: flex; align-items: center; justify-content: center;
    background: linear-gradient(135deg, #10B981, #059669); color: white;
    font-weight: 700; font-size: 14px; border-radius: 6px;
}
.title-text { font-size: 13px; font-weight: 500; color: #1e293b; }
.node-count { font-size: 11px; color: #94a3b8; background: #f1f5f9; padding: 1px 6px; border-radius: 8px; }
.header-actions { display: flex; gap: 6px; align-items: center; }
.window-info {
    padding: 4px 14px; font-size: 11px; color: #64748b; background: #f8fafc;
    border-bottom: 1px solid #f1f5f9; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
}
.node-list-container { flex: 1; min-height: 150px; }
.node-list { padding: 4px 0; }
.node-item {
    display: flex; align-items: center; gap: 6px; padding: 5px 8px;
    font-size: 12px; cursor: pointer; border-left: 3px solid transparent;
    transition: background 0.15s;
}
.node-item:hover { background: #f1f5f9; }
.node-item.clickable { cursor: pointer; }
.node-item.password .node-text { color: #ef4444; letter-spacing: 2px; }
.node-tag {
    font-size: 9px; color: white; padding: 1px 4px; border-radius: 3px;
    font-weight: 600; flex-shrink: 0; line-height: 1.2;
}
.node-text { flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.node-id { font-size: 10px; color: #cbd5e1; flex-shrink: 0; }
.node-coords { font-size: 10px; color: #cbd5e1; flex-shrink: 0; font-family: monospace; }
.placeholder {
    display: flex; flex-direction: column; align-items: center; justify-content: center;
    gap: 8px; color: #94a3b8; padding: 40px; cursor: pointer; transition: all 0.2s;
}
.placeholder:hover { color: #64748b; }
.placeholder.loading { cursor: default; color: #10B981; }
.placeholder-icon { font-size: 48px; font-weight: 700; color: #cbd5e1; }
.placeholder-text { font-size: 13px; }
.placeholder-sub { font-size: 11px; color: #cbd5e1; }
.header-actions :deep(.n-button) { font-size: 12px; }
</style>
