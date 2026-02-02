<script setup lang="ts">
import { ref } from 'vue';
import {
    NButton,
    NEmpty,
    NSpin,
    NIcon,
    NImage,
    NImageGroup,
    NSpace,
    NGrid,
    NGridItem,
} from 'naive-ui';
import {
    RefreshOutline,
    DownloadOutline,
    TrashOutline,
    ImagesOutline,
} from '@vicons/ionicons5';

interface GalleryImage {
    id: string;
    path: string;
    thumbnail: string;
    name: string;
    size: number;
    created_at: string;
}

interface Props {
    images: GalleryImage[];
    loading?: boolean;
}

interface Emits {
    (e: 'refresh'): void;
    (e: 'download', path: string): void;
    (e: 'delete', path: string): void;
}

const props = withDefaults(defineProps<Props>(), {
    loading: false,
});

const emit = defineEmits<Emits>();

const formatSize = (bytes: number) => {
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
};

const getImageSrc = (image: GalleryImage) => {
    if (image.thumbnail.startsWith('data:')) return image.thumbnail;
    return `data:image/jpeg;base64,${image.thumbnail}`;
};
</script>

<template>
    <div class="gallery-tab">
        <div class="tab-header">
            <div class="header-title">
                <NIcon :component="ImagesOutline" size="18" />
                <span>设备相册</span>
                <span class="image-count" v-if="images.length > 0">({{ images.length }})</span>
            </div>
            <NButton size="small" type="primary" :loading="loading" @click="emit('refresh')">
                <template #icon>
                    <NIcon :component="RefreshOutline" />
                </template>
                获取相册
            </NButton>
        </div>

        <NSpin :show="loading">
            <div v-if="images.length > 0" class="gallery-grid">
                <NImageGroup>
                    <div
                        v-for="image in images"
                        :key="image.id"
                        class="gallery-item"
                    >
                        <div class="image-wrapper">
                            <NImage
                                :src="getImageSrc(image)"
                                :alt="image.name"
                                object-fit="cover"
                                width="100%"
                                height="120"
                                lazy
                            />
                            <div class="image-overlay">
                                <NSpace size="small">
                                    <NButton
                                        size="tiny"
                                        circle
                                        type="primary"
                                        @click.stop="emit('download', image.path)"
                                    >
                                        <template #icon>
                                            <NIcon :component="DownloadOutline" size="14" />
                                        </template>
                                    </NButton>
                                    <NButton
                                        size="tiny"
                                        circle
                                        type="error"
                                        @click.stop="emit('delete', image.path)"
                                    >
                                        <template #icon>
                                            <NIcon :component="TrashOutline" size="14" />
                                        </template>
                                    </NButton>
                                </NSpace>
                            </div>
                        </div>
                        <div class="image-info">
                            <span class="image-name">{{ image.name }}</span>
                            <span class="image-size">{{ formatSize(image.size) }}</span>
                        </div>
                    </div>
                </NImageGroup>
            </div>
            <NEmpty v-else description="点击获取相册查看设备图片" />
        </NSpin>
    </div>
</template>

<style scoped>
.gallery-tab {
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

.image-count {
    font-weight: 400;
    color: #64748b;
    font-size: 13px;
}

.gallery-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 12px;
    max-height: 400px;
    overflow-y: auto;
    padding: 4px;
}

.gallery-item {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.image-wrapper {
    position: relative;
    border-radius: 8px;
    overflow: hidden;
    background: #f1f5f9;
}

.image-wrapper :deep(.n-image) {
    display: block;
}

.image-overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 8px;
    background: linear-gradient(transparent, rgba(0, 0, 0, 0.6));
    display: flex;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.2s ease;
}

.image-wrapper:hover .image-overlay {
    opacity: 1;
}

.image-info {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.image-name {
    font-size: 11px;
    color: #1e293b;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.image-size {
    font-size: 10px;
    color: #94a3b8;
}

@media (max-width: 768px) {
    .gallery-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 480px) {
    .gallery-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}
</style>
