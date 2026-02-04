<script setup lang="ts">
import { computed } from 'vue';
import {
    NCard,
    NTag,
    NProgress,
    NIcon,
} from 'naive-ui';
import {
    PhonePortraitOutline,
    BatteryChargingOutline,
    BatteryFullOutline,
    BatteryHalfOutline,
    BatteryDeadOutline,
    FlashOutline,
    WifiOutline,
    CellularOutline,
    EllipseOutline,
    LocationOutline,
    TimeOutline,
    FingerPrintOutline,
    PricetagOutline,
    PhonePortraitOutline as ModelIcon,
    LogoAndroid,
    CallOutline,
    GlobeOutline,
    LockClosedOutline,
    AccessibilityOutline,
    ImageOutline,
} from '@vicons/ionicons5';
import type { PhoneInfo } from '@/types/websocket';

interface Props {
    phoneInfo: PhoneInfo | null;
    connectionStatus: string;
    lastPing: string;
    deviceId?: string;
}

const props = defineProps<Props>();

/**
 * 解析电池状态字符串
 * 格式: "{充电状态}~{电量}" 例如 "t~88" 表示充电中，电量88%
 */
const parseBattery = (batteryCharge: string | undefined) => {
    if (!batteryCharge) {
        return { isCharging: false, level: 0 };
    }
    
    const parts = batteryCharge.split('~');
    if (parts.length === 2) {
        return {
            isCharging: parts[0] === 't',
            level: parseInt(parts[1], 10) || 0
        };
    }
    
    // 兼容旧格式（纯数字）
    const level = parseInt(batteryCharge, 10);
    return {
        isCharging: false,
        level: isNaN(level) ? 0 : level
    };
};

// 计算属性：解析后的电池信息
const batteryInfo = computed(() => parseBattery(props.phoneInfo?.battery_charge));

// 根据电量获取颜色
const getBatteryColor = (level: number) => {
    if (level > 60) return '#10B981';  // 绿色
    if (level > 20) return '#F59E0B';  // 橙色
    return '#EF4444';                   // 红色
};

// 根据电量获取图标
const getBatteryIcon = (level: number) => {
    if (level > 60) return BatteryFullOutline;
    if (level > 20) return BatteryHalfOutline;
    return BatteryDeadOutline;
};

// 按照 info.php 的逻辑：serverToPhone === 'OPEN' 表示在线
const isOnline = () => {
    const status = props.connectionStatus?.toUpperCase?.() || '';
    return status === 'OPEN';
};

// 获取网络类型信息
const getNetworkInfo = (type: string | undefined) => {
    if (!type) return { icon: EllipseOutline, color: '#cbd5e1', label: '未知' };
    const t = type.toLowerCase();
    if (t.includes('wifi')) return { icon: WifiOutline, color: '#3B82F6', label: 'WiFi' };
    if (t.includes('5g')) return { icon: CellularOutline, color: '#8B5CF6', label: '5G' };
    if (t.includes('4g') || t.includes('lte')) return { icon: CellularOutline, color: '#10B981', label: '4G' };
    if (t.includes('3g')) return { icon: CellularOutline, color: '#F59E0B', label: '3G' };
    return { icon: CellularOutline, color: '#64748b', label: type };
};

// 计算属性：网络信息
const networkInfo = computed(() => getNetworkInfo(props.phoneInfo?.network));

// 计算属性：壁纸图片 URL
const wallpaperUrl = computed(() => {
    const wallpap = props.phoneInfo?.wallpap;
    if (!wallpap) return null;
    // wallpap 是 Base64 PNG 数据
    if (wallpap.startsWith('data:')) return wallpap;
    return `data:image/png;base64,${wallpap}`;
});
</script>

<template>
    <NCard size="small" class="device-info-card">
        <template #header>
            <div class="card-header">
                <div class="header-title">
                    <NIcon :component="PhonePortraitOutline" size="18" />
                    <span>设备信息</span>
                </div>
                <NTag :type="isOnline() ? 'success' : 'default'" size="small" round>
                    {{ isOnline() ? '在线' : '离线' }}
                </NTag>
            </div>
        </template>

        <div v-if="phoneInfo" class="info-list">
            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="FingerPrintOutline" size="14" />
                    <span>ID</span>
                </div>
                <div class="info-value">{{ deviceId || '-' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="PricetagOutline" size="14" />
                    <span>备注</span>
                </div>
                <div class="info-value">{{ phoneInfo.phone_name || '-' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="ModelIcon" size="14" />
                    <span>型号</span>
                </div>
                <div class="info-value">{{ phoneInfo.model || '-' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="LogoAndroid" size="14" />
                    <span>版本</span>
                </div>
                <div class="info-value">{{ phoneInfo.android_version || '-' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="CallOutline" size="14" />
                    <span>号码</span>
                </div>
                <div class="info-value">{{ phoneInfo.phone || '--' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="GlobeOutline" size="14" />
                    <span>IP</span>
                </div>
                <div class="info-value">{{ phoneInfo.ip || '--' }}</div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="networkInfo.icon" size="14" :color="networkInfo.color" />
                    <span>网络</span>
                </div>
                <div class="info-value">
                    <NTag 
                        size="tiny" 
                        round 
                        :bordered="false"
                        :style="{ background: networkInfo.color + '15', color: networkInfo.color }"
                    >
                        {{ networkInfo.label }}
                    </NTag>
                </div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="LockClosedOutline" size="14" />
                    <span>密码</span>
                </div>
                <div class="info-value">
                    <NTag v-if="phoneInfo.has_password" type="success" size="tiny">有数据</NTag>
                    <span v-else class="text-muted">--</span>
                </div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="batteryInfo.isCharging ? BatteryChargingOutline : getBatteryIcon(batteryInfo.level)" size="14" />
                    <span>电量</span>
                </div>
                <div class="info-value battery-row">
                    <NIcon 
                        v-if="batteryInfo.isCharging" 
                        :component="FlashOutline" 
                        size="14" 
                        class="charging-icon"
                    />
                    <NProgress
                        type="line"
                        :percentage="batteryInfo.level"
                        :color="getBatteryColor(batteryInfo.level)"
                        :rail-color="'#e2e8f0'"
                        :height="10"
                        :border-radius="5"
                        :show-indicator="false"
                        :processing="batteryInfo.isCharging"
                        style="width: 70px;"
                    />
                    <span class="battery-text">{{ batteryInfo.level }}%</span>
                </div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="AccessibilityOutline" size="14" />
                    <span>无障碍</span>
                </div>
                <div class="info-value">
                    <NTag :type="phoneInfo.accessibility === '1' ? 'success' : 'warning'" size="tiny">
                        {{ phoneInfo.accessibility === '1' ? '已开启' : '未开启' }}
                    </NTag>
                </div>
            </div>

            <div class="info-row">
                <div class="info-label">
                    <NIcon :component="TimeOutline" size="14" />
                    <span>心跳</span>
                </div>
                <div class="info-value time-value">{{ lastPing || '-' }}</div>
            </div>

            <div v-if="wallpaperUrl" class="info-row wallpaper-row">
                <div class="info-label">
                    <NIcon :component="ImageOutline" size="14" />
                    <span>壁纸</span>
                </div>
                <div class="info-value">
                    <div class="wallpaper-thumbnail">
                        <img :src="wallpaperUrl" alt="设备壁纸" />
                    </div>
                </div>
            </div>
        </div>

        <div v-else class="no-info">
            <NIcon :component="WifiOutline" size="32" class="no-info-icon" />
            <div>暂无设备信息</div>
            <div class="no-info-hint">请连接设备获取信息</div>
        </div>
    </NCard>
</template>

<style scoped>
.device-info-card {
    background: white;
}

.device-info-card :deep(.n-card__header) {
    padding: 16px 20px;
    border-bottom: 1px solid #f1f5f9;
}

.card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.header-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

.info-list {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.info-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 0;
    border-bottom: 1px solid #f8fafc;
}

.info-row:last-child {
    border-bottom: none;
}

.info-label {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #64748b;
    font-size: 13px;
}

.info-value {
    font-size: 13px;
    color: #1e293b;
    font-weight: 500;
    text-align: right;
    word-break: break-all;
    max-width: 60%;
}

.battery-row {
    display: flex;
    align-items: center;
    gap: 8px;
}

.charging-icon {
    color: #10B981;
    animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
    0%, 100% {
        opacity: 1;
    }
    50% {
        opacity: 0.5;
    }
}

.battery-text {
    font-size: 12px;
    color: #64748b;
    min-width: 36px;
}

.time-value {
    font-size: 12px;
    color: #64748b;
}

.text-muted {
    color: #94a3b8;
}

.no-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px 20px;
    color: #94a3b8;
    text-align: center;
}

.no-info-icon {
    margin-bottom: 12px;
    color: #cbd5e1;
}

.no-info-hint {
    font-size: 12px;
    margin-top: 4px;
    color: #cbd5e1;
}

.wallpaper-row {
    padding: 12px 0;
}

.wallpaper-thumbnail {
    width: 48px;
    height: 48px;
    border-radius: 8px;
    overflow: hidden;
    border: 1px solid #e2e8f0;
    background: #f8fafc;
}

.wallpaper-thumbnail img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>
