<script setup lang="ts">
import { computed } from 'vue';
import {
    NCard,
    NButton,
    NSpace,
    NEmpty,
    NSpin,
    NDescriptions,
    NDescriptionsItem,
    NTag,
    NIcon,
} from 'naive-ui';
import { LocationOutline, RefreshOutline, OpenOutline } from '@vicons/ionicons5';
import type { LocationInfo } from '@/types/device';

interface Props {
    location: LocationInfo | null;
    loading: boolean;
}

interface Emits {
    (e: 'refresh'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const hasLocation = computed(() => {
    return props.location && props.location.latitude !== 0 && props.location.longitude !== 0;
});

const googleMapsUrl = computed(() => {
    if (!props.location) return '';
    return `https://www.google.com/maps?q=${props.location.latitude},${props.location.longitude}`;
});

const baiduMapsUrl = computed(() => {
    if (!props.location) return '';
    return `https://api.map.baidu.com/marker?location=${props.location.latitude},${props.location.longitude}&title=设备位置&content=设备当前位置&output=html`;
});

const aMapUrl = computed(() => {
    if (!props.location) return '';
    return `https://uri.amap.com/marker?position=${props.location.longitude},${props.location.latitude}&name=设备位置`;
});

const formatCoordinate = (value: number, type: 'lat' | 'lng') => {
    const direction = type === 'lat' 
        ? (value >= 0 ? 'N' : 'S')
        : (value >= 0 ? 'E' : 'W');
    return `${Math.abs(value).toFixed(6)}° ${direction}`;
};

const formatTimestamp = (timestamp?: string) => {
    if (!timestamp) return '-';
    try {
        return new Date(timestamp).toLocaleString('zh-CN');
    } catch {
        return timestamp;
    }
};
</script>

<template>
    <div class="location-tab">
        <NSpace justify="space-between" class="tab-header">
            <NButton size="small" @click="emit('refresh')">
                <template #icon>
                    <NIcon><RefreshOutline /></NIcon>
                </template>
                获取位置
            </NButton>
            <NSpace v-if="hasLocation">
                <NButton size="small" tag="a" :href="googleMapsUrl" target="_blank">
                    <template #icon>
                        <NIcon><OpenOutline /></NIcon>
                    </template>
                    Google 地图
                </NButton>
                <NButton size="small" tag="a" :href="baiduMapsUrl" target="_blank">
                    百度地图
                </NButton>
                <NButton size="small" tag="a" :href="aMapUrl" target="_blank">
                    高德地图
                </NButton>
            </NSpace>
        </NSpace>

        <NSpin :show="loading">
            <template v-if="hasLocation">
                <NCard size="small" class="location-card">
                    <div class="location-content">
                        <div class="location-icon">
                            <NIcon size="48" color="#18a058">
                                <LocationOutline />
                            </NIcon>
                        </div>
                        <NDescriptions :column="2" label-placement="left" size="small">
                            <NDescriptionsItem label="纬度">
                                <NTag type="info" size="small">
                                    {{ formatCoordinate(location!.latitude, 'lat') }}
                                </NTag>
                            </NDescriptionsItem>
                            <NDescriptionsItem label="经度">
                                <NTag type="info" size="small">
                                    {{ formatCoordinate(location!.longitude, 'lng') }}
                                </NTag>
                            </NDescriptionsItem>
                            <NDescriptionsItem v-if="location!.accuracy" label="精度">
                                {{ location!.accuracy.toFixed(1) }} 米
                            </NDescriptionsItem>
                            <NDescriptionsItem v-if="location!.timestamp" label="更新时间">
                                {{ formatTimestamp(location!.timestamp) }}
                            </NDescriptionsItem>
                        </NDescriptions>
                    </div>
                </NCard>

                <div class="map-preview">
                    <iframe
                        :src="`https://maps.google.com/maps?q=${location!.latitude},${location!.longitude}&z=15&output=embed`"
                        width="100%"
                        height="300"
                        style="border: 0; border-radius: 8px;"
                        allowfullscreen
                        loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"
                    />
                </div>
            </template>
            <NEmpty v-else description="暂无位置信息，点击获取位置按钮" />
        </NSpin>
    </div>
</template>

<style scoped>
.location-tab {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.tab-header {
    margin-bottom: 8px;
}

.location-card {
    margin-bottom: 12px;
}

.location-content {
    display: flex;
    align-items: flex-start;
    gap: 16px;
}

.location-icon {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 64px;
    height: 64px;
    background: rgba(24, 160, 88, 0.1);
    border-radius: 12px;
}

.map-preview {
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
