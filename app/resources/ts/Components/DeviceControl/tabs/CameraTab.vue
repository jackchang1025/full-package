<script setup lang="ts">
import { ref, computed } from 'vue';
import {
    NButton,
    NEmpty,
    NSpin,
    NIcon,
    NImage,
    NSpace,
    NButtonGroup,
    NTag,
} from 'naive-ui';
import {
    CameraOutline,
    CameraReverseOutline,
    PlayOutline,
    StopOutline,
    DownloadOutline,
} from '@vicons/ionicons5';

interface Props {
    cameraData: string | null;
    isActive?: boolean;
    loading?: boolean;
}

interface Emits {
    (e: 'start', camera: 'front' | 'back'): void;
    (e: 'stop'): void;
    (e: 'capture'): void;
}

const props = withDefaults(defineProps<Props>(), {
    isActive: false,
    loading: false,
});

const emit = defineEmits<Emits>();

const selectedCamera = ref<'front' | 'back'>('back');

const imageSrc = computed(() => {
    if (!props.cameraData) return '';
    if (props.cameraData.startsWith('data:')) return props.cameraData;
    return `data:image/jpeg;base64,${props.cameraData}`;
});

const handleStart = () => {
    emit('start', selectedCamera.value);
};
</script>

<template>
    <div class="camera-tab">
        <div class="tab-header">
            <div class="header-title">
                <NIcon :component="CameraOutline" size="18" />
                <span>实时相机</span>
                <NTag v-if="isActive" type="success" size="small">
                    录制中
                </NTag>
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
                    v-if="!isActive"
                    size="small"
                    type="success"
                    :loading="loading"
                    @click="handleStart"
                >
                    <template #icon>
                        <NIcon :component="PlayOutline" />
                    </template>
                    开启
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
                    停止
                </NButton>
            </NSpace>
        </div>

        <NSpin :show="loading">
            <div class="camera-preview">
                <div v-if="cameraData" class="preview-container">
                    <NImage
                        :src="imageSrc"
                        object-fit="contain"
                        width="100%"
                        preview-disabled
                    />
                </div>
                <NEmpty v-else description="点击开启查看实时相机画面" />
            </div>
        </NSpin>
    </div>
</template>

<style scoped>
.camera-tab {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.tab-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 12px;
}

.header-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

.camera-preview {
    background: #0a0a0a;
    border-radius: 12px;
    min-height: 300px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
}

.preview-container {
    width: 100%;
    max-height: 400px;
}

.preview-container :deep(.n-image) {
    display: block;
}

.camera-preview :deep(.n-empty) {
    color: #64748b;
}
</style>
