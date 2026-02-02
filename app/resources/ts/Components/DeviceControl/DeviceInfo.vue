<script setup lang="ts">
import {
    NCard,
    NTag,
    NProgress,
    NIcon,
} from 'naive-ui';
import {
    PhonePortraitOutline,
    BatteryChargingOutline,
    WifiOutline,
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
} from '@vicons/ionicons5';
import type { PhoneInfo } from '@/types/websocket';

interface Props {
    phoneInfo: PhoneInfo | null;
    connectionStatus: string;
    lastPing: string;
    deviceId?: string;
}

const props = defineProps<Props>();

const getBatteryColor = (level: number) => {
    if (level > 60) return '#10B981';
    if (level > 20) return '#F59E0B';
    return '#EF4444';
};

// 按照 info.php 的逻辑：serverToPhone === 'OPEN' 表示在线
const isOnline = () => {
    const status = props.connectionStatus?.toUpperCase?.() || '';
    return status === 'OPEN';
};
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
                    <NIcon :component="BatteryChargingOutline" size="14" />
                    <span>电量</span>
                </div>
                <div class="info-value battery-row">
                    <div class="battery-bar">
                        <div
                            class="battery-fill"
                            :style="{
                                width: `${parseInt(phoneInfo.battery_charge) || 0}%`,
                                backgroundColor: getBatteryColor(parseInt(phoneInfo.battery_charge) || 0)
                            }"
                        ></div>
                    </div>
                    <span class="battery-text">{{ phoneInfo.battery_charge || 0 }}%</span>
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

.battery-bar {
    width: 60px;
    height: 8px;
    background: #e2e8f0;
    border-radius: 4px;
    overflow: hidden;
}

.battery-fill {
    height: 100%;
    border-radius: 4px;
    transition: width 0.3s ease;
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
</style>
