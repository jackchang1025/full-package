<script setup lang="ts">
import { NButton, NIcon, NSpace } from 'naive-ui';
import { 
    RocketOutline, 
    ShieldCheckmarkOutline, 
    PhonePortraitOutline,
    CloudDownloadOutline,
    FlashOutline,
    LockClosedOutline,
    ArrowForwardOutline,
} from '@vicons/ionicons5';
import { router, usePage } from '@inertiajs/vue3';
import DefaultLayout from '@/Layouts/DefaultLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const page = usePage();
const { userRoute } = useAdminBasePath();

const features = [
    {
        icon: PhonePortraitOutline,
        title: '设备管理',
        description: '实时监控和管理您的所有 Android 设备',
        color: '#10B981',
    },
    {
        icon: CloudDownloadOutline,
        title: 'APK 构建',
        description: '一键生成定制化的客户端应用',
        color: '#3B82F6',
    },
    {
        icon: FlashOutline,
        title: '实时通信',
        description: 'WebSocket 实时双向通信，毫秒级响应',
        color: '#F59E0B',
    },
    {
        icon: LockClosedOutline,
        title: '安全可靠',
        description: '企业级安全架构，数据加密传输',
        color: '#8B5CF6',
    },
];

const goToLogin = () => router.visit(userRoute('/login'));
const goToRegister = () => router.visit(userRoute('/register'));
</script>

<template>
    <DefaultLayout>
        <div class="welcome-container">
            <!-- 背景装饰 -->
            <div class="bg-decoration">
                <div class="bg-gradient"></div>
                <div class="bg-grid"></div>
                <div class="bg-circle bg-circle-1"></div>
                <div class="bg-circle bg-circle-2"></div>
                <div class="bg-circle bg-circle-3"></div>
                <div class="bg-glow"></div>
            </div>

            <!-- Hero 区域 -->
            <section class="hero-section">
                <div class="hero-content">
                    <!-- 自定义 Logo（.env APP_LOGO 设置时显示） -->
                    <div v-if="page.props.appLogo" class="hero-logo">
                        <img :src="page.props.appLogo" :alt="page.props.appName" class="hero-logo-img" />
                    </div>
                    <!-- 徽章 -->
                    <div class="hero-badge">
                        <NIcon :component="RocketOutline" size="16" />
                        <span>全新 V2 版本发布</span>
                    </div>

                    <!-- 主标题 -->
                    <h1 class="hero-title">
                        <span class="title-line">专业的</span>
                        <span class="title-highlight">Android 设备管理</span>
                        <span class="title-line">解决方案</span>
                    </h1>

                    <!-- 副标题 -->
                    <p class="hero-subtitle">
                        {{ page.props.appName }} — 基于 Laravel 12 + Vue 3 构建的现代化设备管理平台，
                        提供实时监控、远程控制、APK 构建等一站式服务。
                    </p>

                    <!-- CTA 按钮 -->
                    <div class="hero-actions">
                        <NButton 
                            type="primary" 
                            size="large" 
                            class="cta-primary"
                            @click="goToRegister"
                        >
                            <template #icon>
                                <NIcon :component="RocketOutline" />
                            </template>
                            免费开始使用
                        </NButton>
                        <NButton 
                            size="large" 
                            class="cta-secondary"
                            @click="goToLogin"
                        >
                            已有账号？登录
                            <template #icon>
                                <NIcon :component="ArrowForwardOutline" />
                            </template>
                        </NButton>
                    </div>

                    <!-- 信任标识 -->
                    <div class="trust-badges">
                        <div class="trust-item">
                            <NIcon :component="ShieldCheckmarkOutline" size="20" />
                            <span>安全加密</span>
                        </div>
                        <div class="trust-divider"></div>
                        <div class="trust-item">
                            <span class="trust-number">99.9%</span>
                            <span>可用性</span>
                        </div>
                        <div class="trust-divider"></div>
                        <div class="trust-item">
                            <span class="trust-number">24/7</span>
                            <span>技术支持</span>
                        </div>
                    </div>
                </div>

                <!-- Hero 图片/装饰 -->
                <div class="hero-visual">
                    <div class="visual-card">
                        <div class="card-header">
                            <div class="card-dots">
                                <span></span>
                                <span></span>
                                <span></span>
                            </div>
                            <span class="card-title">设备控制台</span>
                        </div>
                        <div class="card-content">
                            <div class="device-item">
                                <div class="device-icon online"></div>
                                <div class="device-info">
                                    <span class="device-name">Samsung Galaxy S24</span>
                                    <span class="device-status">在线 · 电量 85%</span>
                                </div>
                            </div>
                            <div class="device-item">
                                <div class="device-icon online"></div>
                                <div class="device-info">
                                    <span class="device-name">Xiaomi 14 Pro</span>
                                    <span class="device-status">在线 · 电量 92%</span>
                                </div>
                            </div>
                            <div class="device-item">
                                <div class="device-icon offline"></div>
                                <div class="device-info">
                                    <span class="device-name">OPPO Find X7</span>
                                    <span class="device-status">离线 · 2小时前</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="visual-float visual-float-1">
                        <NIcon :component="PhonePortraitOutline" size="24" />
                    </div>
                    <div class="visual-float visual-float-2">
                        <NIcon :component="CloudDownloadOutline" size="24" />
                    </div>
                </div>
            </section>

            <!-- 功能特性 -->
            <section class="features-section">
                <div class="features-header">
                    <h2 class="features-title">强大功能，简单易用</h2>
                    <p class="features-subtitle">一站式设备管理解决方案，满足您的所有需求</p>
                </div>

                <div class="features-grid">
                    <div 
                        v-for="feature in features" 
                        :key="feature.title" 
                        class="feature-card"
                    >
                        <div class="feature-icon" :style="{ background: `${feature.color}15`, color: feature.color }">
                            <NIcon :component="feature.icon" size="28" />
                        </div>
                        <h3 class="feature-title">{{ feature.title }}</h3>
                        <p class="feature-description">{{ feature.description }}</p>
                    </div>
                </div>
            </section>

            <!-- 底部 CTA -->
            <section class="cta-section">
                <div class="cta-card">
                    <h2 class="cta-title">准备好开始了吗？</h2>
                    <p class="cta-subtitle">立即注册，体验专业的设备管理服务</p>
                    <NButton 
                        type="primary" 
                        size="large" 
                        class="cta-button"
                        @click="goToRegister"
                    >
                        免费注册
                        <template #icon>
                            <NIcon :component="ArrowForwardOutline" />
                        </template>
                    </NButton>
                </div>
            </section>

            <!-- 页脚 -->
            <footer class="welcome-footer">
                <p>© 2026 {{ page.props.appName }} · 专业的 Android 设备管理平台</p>
            </footer>
        </div>
    </DefaultLayout>
</template>

<style scoped>
.welcome-container {
    min-height: 100vh;
    position: relative;
    overflow: hidden;
}

/* 背景装饰 */
.bg-decoration {
    position: fixed;
    inset: 0;
    pointer-events: none;
    z-index: 0;
}

.bg-gradient {
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, #f0fdf4 0%, #ecfdf5 30%, #f0fdfa 60%, #f8fafc 100%);
}

.bg-grid {
    position: absolute;
    inset: 0;
    background-image: 
        linear-gradient(rgba(16, 185, 129, 0.03) 1px, transparent 1px),
        linear-gradient(90deg, rgba(16, 185, 129, 0.03) 1px, transparent 1px);
    background-size: 60px 60px;
}

.bg-circle {
    position: absolute;
    border-radius: 50%;
    filter: blur(60px);
}

.bg-circle-1 {
    width: 800px;
    height: 800px;
    background: rgba(16, 185, 129, 0.12);
    top: -400px;
    right: -200px;
}

.bg-circle-2 {
    width: 600px;
    height: 600px;
    background: rgba(59, 130, 246, 0.08);
    bottom: -200px;
    left: -200px;
}

.bg-circle-3 {
    width: 400px;
    height: 400px;
    background: rgba(139, 92, 246, 0.08);
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.bg-glow {
    position: absolute;
    width: 100%;
    height: 600px;
    top: 0;
    background: radial-gradient(ellipse at 50% 0%, rgba(16, 185, 129, 0.1) 0%, transparent 70%);
}

/* Hero 区域 */
.hero-section {
    position: relative;
    z-index: 1;
    max-width: 1400px;
    margin: 0 auto;
    padding: 80px 24px 60px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 60px;
    align-items: center;
    min-height: 90vh;
}

.hero-content {
    max-width: 600px;
}

.hero-logo {
    margin-bottom: 24px;
}

.hero-logo-img {
    width: 80px;
    height: 80px;
    object-fit: contain;
}

.hero-badge {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 8px 16px;
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.1) 0%, rgba(5, 150, 105, 0.1) 100%);
    border: 1px solid rgba(16, 185, 129, 0.2);
    border-radius: 50px;
    font-size: 14px;
    font-weight: 500;
    color: #059669;
    margin-bottom: 24px;
}

.hero-title {
    font-size: 56px;
    font-weight: 800;
    line-height: 1.1;
    margin: 0 0 24px;
    color: #1e293b;
}

.title-line {
    display: block;
}

.title-highlight {
    display: block;
    background: linear-gradient(135deg, #10B981 0%, #059669 50%, #047857 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.hero-subtitle {
    font-size: 18px;
    line-height: 1.7;
    color: #64748b;
    margin: 0 0 32px;
}

.hero-actions {
    display: flex;
    gap: 16px;
    margin-bottom: 40px;
}

.cta-primary {
    height: 52px;
    padding: 0 32px;
    border-radius: 14px;
    font-size: 16px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.35);
    transition: all 0.3s ease;
}

.cta-primary:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 32px rgba(16, 185, 129, 0.4);
}

.cta-secondary {
    height: 52px;
    padding: 0 28px;
    border-radius: 14px;
    font-size: 16px;
    font-weight: 500;
    background: white;
    border: 2px solid #e2e8f0;
    color: #475569;
    transition: all 0.3s ease;
}

.cta-secondary:hover {
    border-color: #10B981;
    color: #10B981;
    background: rgba(16, 185, 129, 0.05);
}

.trust-badges {
    display: flex;
    align-items: center;
    gap: 20px;
}

.trust-item {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #64748b;
    font-size: 14px;
}

.trust-number {
    font-weight: 700;
    color: #10B981;
}

.trust-divider {
    width: 1px;
    height: 24px;
    background: #e2e8f0;
}

/* Hero 视觉 */
.hero-visual {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
}

.visual-card {
    width: 100%;
    max-width: 420px;
    background: white;
    border-radius: 20px;
    box-shadow: 
        0 4px 6px rgba(0, 0, 0, 0.02),
        0 20px 40px rgba(0, 0, 0, 0.06),
        0 40px 80px rgba(16, 185, 129, 0.1);
    border: 1px solid rgba(16, 185, 129, 0.1);
    overflow: hidden;
}

.card-header {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px 20px;
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
}

.card-dots {
    display: flex;
    gap: 6px;
}

.card-dots span {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background: #e2e8f0;
}

.card-dots span:first-child { background: #f87171; }
.card-dots span:nth-child(2) { background: #fbbf24; }
.card-dots span:last-child { background: #34d399; }

.card-title {
    font-size: 13px;
    font-weight: 500;
    color: #64748b;
}

.card-content {
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.device-item {
    display: flex;
    align-items: center;
    gap: 14px;
    padding: 14px 16px;
    background: #f8fafc;
    border-radius: 12px;
    transition: all 0.2s ease;
}

.device-item:hover {
    background: #f1f5f9;
    transform: translateX(4px);
}

.device-icon {
    width: 12px;
    height: 12px;
    border-radius: 50%;
}

.device-icon.online {
    background: #10B981;
    box-shadow: 0 0 8px rgba(16, 185, 129, 0.5);
}

.device-icon.offline {
    background: #94a3b8;
}

.device-info {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.device-name {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

.device-status {
    font-size: 12px;
    color: #64748b;
}

.visual-float {
    position: absolute;
    width: 56px;
    height: 56px;
    background: white;
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    color: #10B981;
}

.visual-float-1 {
    top: -20px;
    right: 40px;
    animation: float 3s ease-in-out infinite;
}

.visual-float-2 {
    bottom: 40px;
    left: -20px;
    animation: float 3s ease-in-out infinite 1.5s;
}

@keyframes float {
    0%, 100% { transform: translateY(0); }
    50% { transform: translateY(-10px); }
}

/* 功能特性 */
.features-section {
    position: relative;
    z-index: 1;
    max-width: 1200px;
    margin: 0 auto;
    padding: 80px 24px;
}

.features-header {
    text-align: center;
    margin-bottom: 60px;
}

.features-title {
    font-size: 40px;
    font-weight: 700;
    color: #1e293b;
    margin: 0 0 16px;
}

.features-subtitle {
    font-size: 18px;
    color: #64748b;
    margin: 0;
}

.features-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 24px;
}

.feature-card {
    background: white;
    border-radius: 20px;
    padding: 32px 24px;
    text-align: center;
    border: 1px solid #e2e8f0;
    transition: all 0.3s ease;
}

.feature-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
    border-color: transparent;
}

.feature-icon {
    width: 64px;
    height: 64px;
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 20px;
}

.feature-title {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 12px;
}

.feature-description {
    font-size: 14px;
    color: #64748b;
    line-height: 1.6;
    margin: 0;
}

/* CTA 区域 */
.cta-section {
    position: relative;
    z-index: 1;
    max-width: 800px;
    margin: 0 auto;
    padding: 40px 24px 80px;
}

.cta-card {
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 24px;
    padding: 60px 40px;
    text-align: center;
    box-shadow: 0 20px 60px rgba(16, 185, 129, 0.3);
}

.cta-title {
    font-size: 32px;
    font-weight: 700;
    color: white;
    margin: 0 0 12px;
}

.cta-subtitle {
    font-size: 16px;
    color: rgba(255, 255, 255, 0.85);
    margin: 0 0 32px;
}

.cta-button {
    height: 52px;
    padding: 0 36px;
    border-radius: 14px;
    font-size: 16px;
    font-weight: 600;
    background: white;
    color: #059669;
    border: none;
    transition: all 0.3s ease;
}

.cta-button:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

/* 页脚 */
.welcome-footer {
    position: relative;
    z-index: 1;
    text-align: center;
    padding: 24px;
    color: #94a3b8;
    font-size: 14px;
}

/* 响应式 */
@media (max-width: 1024px) {
    .hero-section {
        grid-template-columns: 1fr;
        text-align: center;
        padding-top: 60px;
        min-height: auto;
    }
    
    .hero-content {
        max-width: 100%;
    }
    
    .hero-title {
        font-size: 42px;
    }
    
    .hero-actions {
        justify-content: center;
    }
    
    .trust-badges {
        justify-content: center;
    }
    
    .hero-visual {
        order: -1;
    }
    
    .visual-float {
        display: none;
    }
    
    .features-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 640px) {
    .hero-title {
        font-size: 32px;
    }
    
    .hero-actions {
        flex-direction: column;
    }
    
    .cta-primary,
    .cta-secondary {
        width: 100%;
    }
    
    .trust-badges {
        flex-wrap: wrap;
        gap: 12px;
    }
    
    .trust-divider {
        display: none;
    }
    
    .features-grid {
        grid-template-columns: 1fr;
    }
    
    .features-title {
        font-size: 28px;
    }
    
    .cta-card {
        padding: 40px 24px;
    }
    
    .cta-title {
        font-size: 24px;
    }
}
</style>
