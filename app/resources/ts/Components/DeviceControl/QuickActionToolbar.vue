<script setup lang="ts">
import { ref, h } from 'vue';
import { NButton, NIcon, NTooltip, NDropdown, useMessage } from 'naive-ui';
import {
    CameraOutline,
    LockClosedOutline,
    TrashOutline,
    RefreshOutline,
    LocationOutline,
    WifiOutline,
    VolumeHighOutline,
    CallOutline,
    PowerOutline,
    SettingsOutline,
    EllipsisHorizontalOutline,
} from '@vicons/ionicons5';

interface Emits {
    (e: 'screenshot'): void;
    (e: 'lock-screen'): void;
    (e: 'uninstall-app'): void;
    (e: 'restart-app'): void;
    (e: 'get-location'): void;
    (e: 'get-wifi'): void;
    (e: 'play-audio'): void;
    (e: 'make-call'): void;
    (e: 'restart-device'): void;
    (e: 'factory-reset'): void;
    (e: 'clear-data'): void;
}

const emit = defineEmits<Emits>();
const message = useMessage();

const moreActions = [
    {
        label: '播放音频',
        key: 'play-audio',
        icon: () => h(NIcon, { component: VolumeHighOutline }),
    },
    {
        label: '拨打电话',
        key: 'make-call',
        icon: () => h(NIcon, { component: CallOutline }),
    },
    {
        label: '重启设备',
        key: 'restart-device',
        icon: () => h(NIcon, { component: PowerOutline }),
    },
    {
        label: '恢复出厂',
        key: 'factory-reset',
        icon: () => h(NIcon, { component: SettingsOutline }),
    },
    {
        label: '清除数据',
        key: 'clear-data',
        icon: () => h(NIcon, { component: TrashOutline }),
    },
];

const handleMoreAction = (key: string) => {
    emit(key as any);
};
</script>

<template>
    <div class="quick-action-toolbar">
        <div class="toolbar-header">
            <span class="toolbar-title">快捷操作</span>
        </div>
        
        <div class="toolbar-grid">
            <!-- High-frequency actions -->
            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('screenshot')"
                    >
                        <template #icon>
                            <NIcon :component="CameraOutline" />
                        </template>
                    </NButton>
                </template>
                截屏
            </NTooltip>

            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('lock-screen')"
                    >
                        <template #icon>
                            <NIcon :component="LockClosedOutline" />
                        </template>
                    </NButton>
                </template>
                锁屏
            </NTooltip>

            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('uninstall-app')"
                    >
                        <template #icon>
                            <NIcon :component="TrashOutline" />
                        </template>
                    </NButton>
                </template>
                卸载应用
            </NTooltip>

            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('restart-app')"
                    >
                        <template #icon>
                            <NIcon :component="RefreshOutline" />
                        </template>
                    </NButton>
                </template>
                重启应用
            </NTooltip>

            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('get-location')"
                    >
                        <template #icon>
                            <NIcon :component="LocationOutline" />
                        </template>
                    </NButton>
                </template>
                获取位置
            </NTooltip>

            <NTooltip trigger="hover">
                <template #trigger>
                    <NButton
                        quaternary
                        circle
                        class="action-btn"
                        @click="emit('get-wifi')"
                    >
                        <template #icon>
                            <NIcon :component="WifiOutline" />
                        </template>
                    </NButton>
                </template>
                获取WiFi
            </NTooltip>

            <!-- More actions dropdown - spans 2 columns -->
            <NDropdown
                :options="moreActions"
                @select="handleMoreAction"
                trigger="click"
                class="more-dropdown"
            >
                <NButton
                    quaternary
                    class="action-btn more-btn"
                >
                    <template #icon>
                        <NIcon :component="EllipsisHorizontalOutline" />
                    </template>
                    更多操作
                </NButton>
            </NDropdown>
        </div>
    </div>
</template>

<style scoped>
.quick-action-toolbar {
    background: white;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    overflow: hidden;
}

.toolbar-header {
    padding: 12px 16px;
    background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
    border-bottom: 1px solid #e2e8f0;
}

.toolbar-title {
    font-size: 13px;
    font-weight: 600;
    color: #64748b;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.toolbar-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 8px;
    padding: 12px;
}

.action-btn {
    width: 100%;
    height: 48px;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    position: relative;
    overflow: hidden;
}

.action-btn::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    width: 0;
    height: 0;
    border-radius: 50%;
    background: rgba(16, 185, 129, 0.1);
    transform: translate(-50%, -50%);
    transition: width 0.4s, height 0.4s;
}

.action-btn:hover::before {
    width: 100%;
    height: 100%;
}

.action-btn:hover {
    color: #10B981;
    transform: translateY(-2px);
}

.action-btn:active {
    transform: translateY(0);
}

.more-dropdown {
    grid-column: 1 / -1;
}

.more-btn {
    width: 100%;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    color: white;
    border-radius: 12px;
    height: 40px;
    font-weight: 500;
}

.more-btn:hover {
    background: linear-gradient(135deg, #059669 0%, #047857 100%);
    color: white;
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
    transform: translateY(-2px);
}

.more-btn:active {
    transform: translateY(0);
}
</style>
