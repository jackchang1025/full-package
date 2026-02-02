<script setup lang="ts">
import { ref, computed } from 'vue';
import {
    NButton,
    NEmpty,
    NSpin,
    NIcon,
    NSpace,
    NTag,
    NSlider,
} from 'naive-ui';
import {
    MicOutline,
    PlayOutline,
    StopOutline,
    DownloadOutline,
} from '@vicons/ionicons5';

interface Props {
    audioData: string | null;
    isActive?: boolean;
    loading?: boolean;
}

interface Emits {
    (e: 'start'): void;
    (e: 'stop'): void;
}

const props = withDefaults(defineProps<Props>(), {
    isActive: false,
    loading: false,
});

const emit = defineEmits<Emits>();

const audioSrc = computed(() => {
    if (!props.audioData) return '';
    if (props.audioData.startsWith('data:')) return props.audioData;
    return `data:audio/wav;base64,${props.audioData}`;
});
</script>

<template>
    <div class="mic-tab">
        <div class="tab-header">
            <div class="header-title">
                <NIcon :component="MicOutline" size="18" />
                <span>实时录音</span>
                <NTag v-if="isActive" type="success" size="small">
                    录制中
                </NTag>
            </div>
            <NSpace>
                <NButton
                    v-if="!isActive"
                    size="small"
                    type="success"
                    :loading="loading"
                    @click="emit('start')"
                >
                    <template #icon>
                        <NIcon :component="PlayOutline" />
                    </template>
                    开始录音
                </NButton>
                <NButton
                    v-else
                    size="small"
                    type="error"
                    @click="emit('stop')"
                >
                    <template #icon>
                        <NIcon :component="StopOutline" />
                    </template>
                    停止录音
                </NButton>
            </NSpace>
        </div>

        <NSpin :show="loading">
            <div class="mic-content">
                <div v-if="audioData" class="audio-container">
                    <div class="audio-visualizer">
                        <div class="visualizer-bars">
                            <div
                                v-for="i in 20"
                                :key="i"
                                class="bar"
                                :class="{ active: isActive }"
                                :style="{ animationDelay: `${i * 0.05}s` }"
                            ></div>
                        </div>
                    </div>
                    <audio :src="audioSrc" controls class="audio-player" />
                </div>
                <div v-else class="mic-placeholder">
                    <div class="mic-icon" :class="{ recording: isActive }">
                        <NIcon :component="MicOutline" size="48" />
                    </div>
                    <p v-if="isActive">正在录音中...</p>
                    <p v-else>点击开始录音监听设备麦克风</p>
                </div>
            </div>
        </NSpin>
    </div>
</template>

<style scoped>
.mic-tab {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.tab-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

.mic-content {
    background: #f8fafc;
    border-radius: 12px;
    min-height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 24px;
}

.audio-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 20px;
    align-items: center;
}

.audio-visualizer {
    width: 100%;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.visualizer-bars {
    display: flex;
    align-items: center;
    gap: 4px;
    height: 100%;
}

.bar {
    width: 4px;
    height: 20px;
    background: #10B981;
    border-radius: 2px;
    transition: height 0.1s ease;
}

.bar.active {
    animation: pulse 0.5s ease-in-out infinite alternate;
}

@keyframes pulse {
    0% {
        height: 20px;
    }
    100% {
        height: 60px;
    }
}

.audio-player {
    width: 100%;
    max-width: 400px;
}

.mic-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
    color: #64748b;
}

.mic-icon {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: #e2e8f0;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #64748b;
    transition: all 0.3s ease;
}

.mic-icon.recording {
    background: #10B981;
    color: white;
    animation: pulse-bg 1s ease-in-out infinite;
}

@keyframes pulse-bg {
    0%, 100% {
        transform: scale(1);
        box-shadow: 0 0 0 0 rgba(16, 185, 129, 0.4);
    }
    50% {
        transform: scale(1.05);
        box-shadow: 0 0 0 20px rgba(16, 185, 129, 0);
    }
}

.mic-placeholder p {
    margin: 0;
    font-size: 14px;
}
</style>
