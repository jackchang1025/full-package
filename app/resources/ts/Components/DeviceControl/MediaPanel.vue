<script setup lang="ts">
import { ref, computed } from 'vue';
import {
    NCard,
    NSpace,
    NButton,
    NButtonGroup,
    NImage,
    NEmpty,
    NSpin,
    NTag,
} from 'naive-ui';

interface Props {
    cameraData: string | null;
    microphoneData: string | null;
    isCameraActive: boolean;
    isMicrophoneActive: boolean;
    loading?: boolean;
}

interface Emits {
    (e: 'startCamera', camera: 'front' | 'back'): void;
    (e: 'stopCamera'): void;
    (e: 'startMicrophone'): void;
    (e: 'stopMicrophone'): void;
}

const props = withDefaults(defineProps<Props>(), {
    loading: false,
});

const emit = defineEmits<Emits>();

const selectedCamera = ref<'front' | 'back'>('back');

const cameraImageSrc = computed(() => {
    if (!props.cameraData) return '';
    if (props.cameraData.startsWith('data:')) return props.cameraData;
    return `data:image/jpeg;base64,${props.cameraData}`;
});

const audioSrc = computed(() => {
    if (!props.microphoneData) return '';
    if (props.microphoneData.startsWith('data:')) return props.microphoneData;
    return `data:audio/wav;base64,${props.microphoneData}`;
});

const handleStartCamera = () => {
    emit('startCamera', selectedCamera.value);
};
</script>

<template>
    <NCard title="媒体控制" size="small">
        <NSpace vertical size="large">
            <div class="media-section">
                <div class="section-header">
                    <span class="section-title">摄像头</span>
                    <NTag v-if="isCameraActive" type="success" size="small">
                        录制中
                    </NTag>
                </div>

                <div class="camera-preview">
                    <NSpin v-if="loading" />
                    <NImage
                        v-else-if="cameraData"
                        :src="cameraImageSrc"
                        width="100%"
                        object-fit="contain"
                        preview-disabled
                    />
                    <NEmpty v-else description="摄像头未启动" />
                </div>

                <NSpace>
                    <NButtonGroup size="small">
                        <NButton
                            :type="selectedCamera === 'back' ? 'primary' : 'default'"
                            @click="selectedCamera = 'back'"
                        >
                            后置
                        </NButton>
                        <NButton
                            :type="selectedCamera === 'front' ? 'primary' : 'default'"
                            @click="selectedCamera = 'front'"
                        >
                            前置
                        </NButton>
                    </NButtonGroup>

                    <NButton
                        v-if="!isCameraActive"
                        type="primary"
                        size="small"
                        @click="handleStartCamera"
                    >
                        启动摄像头
                    </NButton>
                    <NButton
                        v-else
                        type="error"
                        size="small"
                        @click="emit('stopCamera')"
                    >
                        停止摄像头
                    </NButton>
                </NSpace>
            </div>

            <div class="media-section">
                <div class="section-header">
                    <span class="section-title">麦克风</span>
                    <NTag v-if="isMicrophoneActive" type="success" size="small">
                        录制中
                    </NTag>
                </div>

                <div v-if="microphoneData" class="audio-player">
                    <audio :src="audioSrc" controls style="width: 100%" />
                </div>
                <NEmpty v-else description="麦克风未启动" />

                <NSpace>
                    <NButton
                        v-if="!isMicrophoneActive"
                        type="primary"
                        size="small"
                        @click="emit('startMicrophone')"
                    >
                        启动麦克风
                    </NButton>
                    <NButton
                        v-else
                        type="error"
                        size="small"
                        @click="emit('stopMicrophone')"
                    >
                        停止麦克风
                    </NButton>
                </NSpace>
            </div>
        </NSpace>
    </NCard>
</template>

<style scoped>
.media-section {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.section-header {
    display: flex;
    align-items: center;
    gap: 8px;
}

.section-title {
    font-weight: 500;
}

.camera-preview {
    background: #1a1a1a;
    border-radius: 8px;
    min-height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
}

.audio-player {
    padding: 12px;
    background: var(--n-color-embedded);
    border-radius: 8px;
}
</style>
