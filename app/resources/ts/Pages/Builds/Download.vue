<script setup lang="ts">
import { Head } from '@inertiajs/vue3';
import { computed, onMounted } from 'vue';
import { NIcon } from 'naive-ui';
import { CheckmarkCircleOutline, CloudDownloadOutline } from '@vicons/ionicons5';

interface AppBuild {
    id: number;
    name: string;
    package_name: string;
    version: string;
    icon_url?: string;
    download_url?: string;
}

interface Props {
    build: AppBuild;
    fileSize?: string;
}

const props = defineProps<Props>();

const displayVersion = computed(() => props.build.version || '1.0.0');

const downloadApp = () => {
    if (props.build.download_url) {
        window.location.href = props.build.download_url;
    }
};

// 设置移动端 viewport
onMounted(() => {
    let viewport = document.querySelector('meta[name="viewport"]');
    if (!viewport) {
        viewport = document.createElement('meta');
        viewport.setAttribute('name', 'viewport');
        document.head.appendChild(viewport);
    }
    viewport.setAttribute('content', 'width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover');
});
</script>

<template>
    <Head :title="`下载 ${build.name}`" />
    
    <div class="download-page">
        <div class="download-container">
            <!-- 应用图标 -->
            <div class="app-icon">
                <img 
                    v-if="build.icon_url" 
                    :src="build.icon_url" 
                    :alt="build.name"
                />
                <div v-else class="icon-placeholder">
                    <span class="icon-letter">{{ build.name.charAt(0).toUpperCase() }}</span>
                </div>
            </div>

            <!-- 应用名称 -->
            <h1 class="app-name">{{ build.name }}</h1>

            <!-- 应用信息栏 -->
            <div class="app-info-bar">
                <div class="info-item">
                    <span class="info-label">大小</span>
                    <span class="info-value">{{ fileSize || '未知' }}</span>
                </div>
                <div class="info-divider"></div>
                <div class="info-item">
                    <span class="info-label">平台</span>
                    <span class="info-value">Android</span>
                </div>
                <div class="info-divider"></div>
                <div class="info-item">
                    <span class="info-label">版本</span>
                    <span class="info-value">{{ displayVersion }}</span>
                </div>
            </div>

            <!-- 下载按钮 -->
            <button 
                class="download-btn"
                :disabled="!build.download_url"
                @click="downloadApp"
            >
                <NIcon :component="CloudDownloadOutline" :size="18" />
                <span>立即安装</span>
            </button>

            <!-- 安全提示 -->
            <div class="security-badge">
                <NIcon :component="CheckmarkCircleOutline" color="#10B981" :size="16" />
                <span>已通过安全检测</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* 页面容器 */
.download-page {
    min-height: 100vh;
    min-height: 100dvh;
    background: #ffffff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px 24px;
    padding-top: calc(40px + env(safe-area-inset-top, 0));
    padding-bottom: calc(40px + env(safe-area-inset-bottom, 0));
    box-sizing: border-box;
}

/* 内容容器 */
.download-container {
    width: 100%;
    max-width: 320px;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

/* 应用图标 */
.app-icon {
    width: 80px;
    height: 80px;
    border-radius: 20px;
    background: #1a1a1a;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    margin-bottom: 16px;
    flex-shrink: 0;
}

.app-icon img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.icon-placeholder {
    width: 100%;
    height: 100%;
    background: #1a1a1a;
    display: flex;
    align-items: center;
    justify-content: center;
}

.icon-letter {
    font-size: 38px;
    font-weight: 700;
    color: #fff;
    background: linear-gradient(135deg, #00d4ff, #ff00d4);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

/* 应用名称 */
.app-name {
    font-size: 20px;
    font-weight: 500;
    color: #1a1a1a;
    margin: 0 0 32px;
    letter-spacing: 0;
    line-height: 1.4;
    word-break: break-word;
}

/* 信息栏 */
.app-info-bar {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    margin-bottom: 40px;
}

.info-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 6px;
    flex: 1;
    padding: 0 16px;
}

.info-label {
    font-size: 12px;
    color: #9ca3af;
    font-weight: 400;
}

.info-value {
    font-size: 14px;
    color: #1a1a1a;
    font-weight: 600;
}

.info-divider {
    width: 1px;
    height: 32px;
    background: #e5e7eb;
    flex-shrink: 0;
}

/* 下载按钮 */
.download-btn {
    width: 100%;
    height: 50px;
    border-radius: 25px;
    font-size: 16px;
    font-weight: 500;
    background: #1a1a1a;
    border: none;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    cursor: pointer;
    transition: opacity 0.2s ease;
    -webkit-tap-highlight-color: transparent;
    touch-action: manipulation;
    margin-bottom: 32px;
}

.download-btn:active:not(:disabled) {
    opacity: 0.85;
}

.download-btn:disabled {
    opacity: 0.4;
    cursor: not-allowed;
}

/* 安全提示 */
.security-badge {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    font-size: 13px;
    color: #10B981;
    font-weight: 400;
}

/* ========== 响应式设计 ========== */

/* 小屏手机 */
@media (max-width: 360px) {
    .download-page {
        padding: 32px 20px;
    }

    .app-icon {
        width: 72px;
        height: 72px;
        border-radius: 18px;
    }

    .icon-letter {
        font-size: 34px;
    }

    .app-name {
        font-size: 18px;
        margin-bottom: 28px;
    }

    .info-item {
        padding: 0 12px;
    }

    .info-label {
        font-size: 11px;
    }

    .info-value {
        font-size: 13px;
    }

    .download-btn {
        height: 48px;
        font-size: 15px;
    }
}

/* 大屏设备 */
@media (min-width: 480px) {
    .download-container {
        max-width: 360px;
    }

    .app-icon {
        width: 88px;
        height: 88px;
        border-radius: 22px;
        margin-bottom: 20px;
    }

    .icon-letter {
        font-size: 42px;
    }

    .app-name {
        font-size: 22px;
        margin-bottom: 36px;
    }

    .app-info-bar {
        margin-bottom: 48px;
    }

    .info-label {
        font-size: 13px;
    }

    .info-value {
        font-size: 15px;
    }

    .download-btn {
        height: 52px;
        font-size: 17px;
        border-radius: 26px;
    }
}

/* 横屏模式 */
@media (max-height: 480px) and (orientation: landscape) {
    .download-page {
        padding: 24px;
        justify-content: flex-start;
    }

    .app-icon {
        width: 64px;
        height: 64px;
        border-radius: 16px;
        margin-bottom: 12px;
    }

    .icon-letter {
        font-size: 30px;
    }

    .app-name {
        font-size: 18px;
        margin-bottom: 20px;
    }

    .app-info-bar {
        margin-bottom: 24px;
    }

    .download-btn {
        height: 44px;
        margin-bottom: 20px;
    }
}

/* 减少动画 */
@media (prefers-reduced-motion: reduce) {
    .download-btn {
        transition: none;
    }
}
</style>
