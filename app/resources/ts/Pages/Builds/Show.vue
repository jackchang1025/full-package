<script setup lang="ts">
import { computed } from 'vue';
import { Head, router } from '@inertiajs/vue3';
import {
    NTag,
    NButton,
    NPopconfirm,
    NIcon,
    NQrCode,
    useMessage,
} from 'naive-ui';
import {
    CloudDownloadOutline,
    TimeOutline,
    CheckmarkCircleOutline,
    ArrowBackOutline,
    TrashOutline,
    ShareSocialOutline,
    CubeOutline,
    ServerOutline,
    LockClosedOutline,
    ColorPaletteOutline,
    ImageOutline,
    CodeSlashOutline,
    SpeedometerOutline,
    CalendarOutline,
    CopyOutline,
    LinkOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import AdminLayout from '@/Layouts/AdminLayout.vue';

interface AppBuild {
    id: number;
    name: string;
    package_name: string;
    version: string;
    user_host: string;
    client_name: string;
    is_custom: boolean;
    use_wss: boolean;
    icon_path: string | null;
    icon_url: string | null;
    background_path: string | null;
    background_url: string | null;
    file_path: string | null;
    build_config: Record<string, any> | null;
    build_stats: Record<string, any> | null;
    build_duration: number | null;
    download_url: string | null;
    share_url: string | null;
    created_at: string;
    started_at: string | null;
    completed_at: string | null;
    template?: {
        id: number;
        name: string;
        package_name: string;
    };
}

interface Props {
    build: AppBuild;
    backUrl?: string;
}

const props = withDefaults(defineProps<Props>(), {
    backUrl: '/builds',
});

const message = useMessage();

// 分享功能
const shareUrl = computed(() => props.build.share_url || '');

// 检测是否为移动设备
const isMobileDevice = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ||
        (navigator.maxTouchPoints > 0 && window.innerWidth <= 768);
};

const handleShare = async () => {
    if (!shareUrl.value) {
        message.warning('分享链接不可用');
        return;
    }
    
    const shareData = {
        title: props.build.name,
        text: `下载 ${props.build.name} APK`,
        url: shareUrl.value,
    };
    
    // 只在移动设备上使用原生分享 API
    // 桌面浏览器 (Windows/Mac/Linux) 的 Web Share API 经常不稳定
    const canUseNativeShare = isMobileDevice() &&
        'share' in navigator && 
        'canShare' in navigator && 
        navigator.canShare(shareData);
    
    if (canUseNativeShare) {
        try {
            await navigator.share(shareData);
            return;
        } catch (err: any) {
            // 用户取消分享 (AbortError) 不需要回退
            if (err?.name === 'AbortError') {
                return;
            }
            // 其他错误回退到复制链接
        }
    }
    
    // 回退：复制链接到剪贴板
    try {
        await navigator.clipboard.writeText(shareUrl.value);
        message.success('下载页面链接已复制到剪贴板');
    } catch (err) {
        message.error('复制失败，请手动复制链接');
    }
};

const buildParams = computed(() => {
    const params = [];
    
    // 基础参数
    params.push({ 
        label: '应用名称', 
        value: props.build.name, 
        icon: CubeOutline,
        color: '#10B981'
    });
    params.push({ 
        label: '包名', 
        value: props.build.package_name, 
        icon: CodeSlashOutline,
        color: '#3B82F6',
        mono: true
    });
    params.push({ 
        label: '版本号', 
        value: props.build.version || '1.0', 
        icon: SpeedometerOutline,
        color: '#8B5CF6'
    });
    
    // 服务器配置
    if (props.build.user_host) {
        params.push({ 
            label: '服务器地址', 
            value: props.build.user_host, 
            icon: ServerOutline,
            color: '#F59E0B',
            mono: true
        });
    }
    if (props.build.client_name) {
        params.push({ 
            label: '客户端标识', 
            value: props.build.client_name, 
            icon: LinkOutline,
            color: '#EC4899'
        });
    }
    
    // 安全配置
    params.push({ 
        label: 'WSS 加密', 
        value: props.build.use_wss ? '已启用' : '未启用', 
        icon: LockClosedOutline,
        color: props.build.use_wss ? '#10B981' : '#94a3b8',
        tag: true,
        tagType: props.build.use_wss ? 'success' : 'default'
    });
    
    // 外观配置
    if (props.build.background_path) {
        params.push({ 
            label: '启动背景', 
            value: props.build.background_path, 
            icon: ColorPaletteOutline,
            color: '#6366F1'
        });
    }
    
    // 构建类型
    params.push({ 
        label: '构建类型', 
        value: props.build.is_custom ? '自定义构建' : '模板构建', 
        icon: ImageOutline,
        color: '#14B8A6',
        tag: true,
        tagType: props.build.is_custom ? 'info' : 'default'
    });
    
    if (props.build.template) {
        params.push({ 
            label: '使用模板', 
            value: props.build.template.name, 
            icon: CubeOutline,
            color: '#F97316'
        });
    }
    
    return params;
});

const buildTimeline = computed(() => {
    const items = [];
    
    items.push({
        label: '创建时间',
        value: props.build.created_at,
        icon: CalendarOutline,
    });
    
    if (props.build.started_at) {
        items.push({
            label: '开始构建',
            value: props.build.started_at,
            icon: TimeOutline,
        });
    }
    
    if (props.build.completed_at) {
        items.push({
            label: '构建完成',
            value: props.build.completed_at,
            icon: CheckmarkCircleOutline,
        });
    }
    
    return items;
});

const formatDuration = (seconds: number | null) => {
    if (!seconds) return '-';
    if (seconds < 60) return `${seconds} 秒`;
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins} 分 ${secs} 秒`;
};

const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text);
};

const deleteBasePath = computed(() => (props.backUrl.includes('/admin/') ? '/admin/builds' : '/builds'));
const handleDelete = () => {
    router.delete(`${deleteBasePath.value}/${props.build.id}`, {
        onSuccess: () => router.visit(props.backUrl),
    });
};

const goBack = () => router.visit(props.backUrl);
const layoutComponent = computed(() => (props.backUrl.includes('/admin/') ? AdminLayout : AuthenticatedLayout));
</script>

<template>
    <Head :title="`构建: ${build.name}`" />
    <component :is="layoutComponent">
        <template #header-title>构建详情</template>

        <div class="build-detail-container">
            <!-- 返回按钮 -->
            <NButton text class="back-btn" @click="goBack">
                <template #icon>
                    <NIcon :component="ArrowBackOutline" />
                </template>
                返回列表
            </NButton>

            <!-- 主信息卡片 -->
            <div class="main-card">
                <div class="app-header">
                    <!-- 应用图标 -->
                    <div class="app-icon">
                        <img 
                            v-if="build.icon_url" 
                            :src="build.icon_url" 
                            :alt="build.name"
                        />
                        <NIcon v-else :component="CubeOutline" size="48" color="#94a3b8" />
                    </div>

                    <!-- 应用基本信息 -->
                    <div class="app-info">
                        <h1 class="app-name">{{ build.name }}</h1>
                        <div class="app-package">
                            <code>{{ build.package_name }}</code>
                            <NButton 
                                text 
                                size="tiny" 
                                @click="copyToClipboard(build.package_name)"
                            >
                                <template #icon>
                                    <NIcon :component="CopyOutline" size="14" />
                                </template>
                            </NButton>
                        </div>
                        <div class="app-meta">
                            <span class="version-badge">v{{ build.version || '1.0' }}</span>
                            <span v-if="build.template" class="template-name">
                                {{ build.template.name }}
                            </span>
                        </div>
                    </div>

                    <!-- 状态标签 - 已完成 -->
                    <div 
                        class="status-badge"
                        :style="{ 
                            background: 'rgba(16, 185, 129, 0.1)',
                            color: '#10B981' 
                        }"
                    >
                        <NIcon :component="CheckmarkCircleOutline" size="20" />
                        <span>已完成</span>
                    </div>
                </div>

                <!-- 操作按钮 -->
                <div class="action-bar">
                    <NButton 
                        type="primary" 
                        size="large"
                        class="download-btn"
                        tag="a"
                        :href="build.download_url || '#'"
                        download
                    >
                        <template #icon>
                            <NIcon :component="CloudDownloadOutline" />
                        </template>
                        下载 APK
                    </NButton>
                    <NButton size="large" class="share-btn" @click="handleShare">
                        <template #icon>
                            <NIcon :component="ShareSocialOutline" />
                        </template>
                        分享
                    </NButton>
                </div>
            </div>

            <!-- 构建参数 -->
            <div class="section-card">
                <h2 class="section-title">
                    <NIcon :component="CodeSlashOutline" size="20" />
                    构建参数
                </h2>
                
                <div class="params-grid">
                    <div 
                        v-for="param in buildParams" 
                        :key="param.label" 
                        class="param-item"
                    >
                        <div class="param-icon" :style="{ background: `${param.color}15`, color: param.color }">
                            <NIcon :component="param.icon" size="18" />
                        </div>
                        <div class="param-content">
                            <div class="param-label">{{ param.label }}</div>
                            <div class="param-value" :class="{ mono: param.mono }">
                                <NTag 
                                    v-if="param.tag" 
                                    :type="(param.tagType as 'success' | 'default' | 'info' | 'primary' | 'warning' | 'error') ?? undefined" 
                                    size="small" 
                                    round
                                >
                                    {{ param.value }}
                                </NTag>
                                <span v-else>{{ param.value }}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 额外构建配置 (如果有) -->
            <div class="section-card" v-if="build.build_config && Object.keys(build.build_config).length > 0">
                <h2 class="section-title">
                    <NIcon :component="ServerOutline" size="20" />
                    高级配置
                </h2>
                
                <div class="config-list">
                    <div 
                        v-for="(value, key) in build.build_config" 
                        :key="key" 
                        class="config-item"
                    >
                        <span class="config-key">{{ key }}</span>
                        <span class="config-value">{{ value }}</span>
                    </div>
                </div>
            </div>

            <!-- 构建统计 -->
            <div class="section-card" v-if="build.build_stats && Object.keys(build.build_stats).length > 0">
                <h2 class="section-title">
                    <NIcon :component="SpeedometerOutline" size="20" />
                    构建统计
                </h2>
                
                <div class="stats-grid">
                    <div 
                        v-for="(value, key) in build.build_stats" 
                        :key="key" 
                        class="stat-item"
                    >
                        <div class="stat-value">{{ value }}</div>
                        <div class="stat-label">{{ key }}</div>
                    </div>
                </div>
            </div>

            <!-- 时间线 -->
            <div class="section-card">
                <h2 class="section-title">
                    <NIcon :component="TimeOutline" size="20" />
                    构建时间线
                </h2>
                
                <div class="timeline">
                    <div 
                        v-for="(item, index) in buildTimeline" 
                        :key="item.label" 
                        class="timeline-item"
                    >
                        <div class="timeline-dot" :class="{ last: index === buildTimeline.length - 1 }">
                            <NIcon :component="item.icon" size="16" />
                        </div>
                        <div class="timeline-content">
                            <div class="timeline-label">{{ item.label }}</div>
                            <div class="timeline-value">{{ item.value }}</div>
                        </div>
                    </div>
                </div>

                <div class="duration-info" v-if="build.build_duration">
                    <span class="duration-label">总耗时</span>
                    <span class="duration-value">{{ formatDuration(build.build_duration) }}</span>
                </div>
            </div>

            <!-- 下载二维码 -->
            <div class="section-card qr-section" v-if="shareUrl">
                <h2 class="section-title">
                    <NIcon :component="ShareSocialOutline" size="20" />
                    扫码下载
                </h2>
                
                <div class="qr-content">
                    <NQrCode 
                        :value="shareUrl" 
                        :size="160"
                        class="qr-code"
                    />
                    <p class="qr-hint">使用手机扫描二维码下载应用</p>
                    <div class="download-link">
                        <code>{{ shareUrl }}</code>
                        <NButton 
                            size="small" 
                            quaternary 
                            @click="copyToClipboard(shareUrl)"
                        >
                            <template #icon>
                                <NIcon :component="CopyOutline" />
                            </template>
                        </NButton>
                    </div>
                </div>
            </div>

            <!-- 底部操作 -->
            <div class="footer-actions">
                <NButton size="large" @click="goBack">
                    <template #icon>
                        <NIcon :component="ArrowBackOutline" />
                    </template>
                    返回列表
                </NButton>
                <NPopconfirm @positive-click="handleDelete">
                    <template #trigger>
                        <NButton type="error" size="large" quaternary>
                            <template #icon>
                                <NIcon :component="TrashOutline" />
                            </template>
                            删除构建
                        </NButton>
                    </template>
                    确定要删除此构建吗？此操作不可恢复。
                </NPopconfirm>
            </div>
        </div>
    </component>
</template>

<style scoped>
.build-detail-container {
    max-width: 900px;
}

/* 返回按钮 */
.back-btn {
    margin-bottom: 20px;
    color: #64748b;
    font-weight: 500;
}

.back-btn:hover {
    color: #10B981;
}

/* 错误提示 */
.error-alert {
    margin-bottom: 20px;
    border-radius: 14px;
}

.alert-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
}

/* 主信息卡片 */
.main-card {
    background: white;
    border-radius: 20px;
    padding: 32px;
    margin-bottom: 20px;
    border: 1px solid #e2e8f0;
}

.app-header {
    display: flex;
    align-items: flex-start;
    gap: 24px;
}

.app-icon {
    width: 88px;
    height: 88px;
    border-radius: 20px;
    background: #f8fafc;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    flex-shrink: 0;
    border: 1px solid #e2e8f0;
}

.app-icon img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.app-info {
    flex: 1;
    min-width: 0;
}

.app-name {
    font-size: 28px;
    font-weight: 700;
    color: #1e293b;
    margin: 0 0 8px;
}

.app-package {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 12px;
}

.app-package code {
    font-size: 14px;
    color: #64748b;
    background: #f1f5f9;
    padding: 4px 10px;
    border-radius: 6px;
    font-family: 'SF Mono', Monaco, monospace;
}

.app-meta {
    display: flex;
    align-items: center;
    gap: 12px;
}

.version-badge {
    font-size: 13px;
    font-weight: 600;
    color: #10B981;
    background: rgba(16, 185, 129, 0.1);
    padding: 4px 12px;
    border-radius: 20px;
}

.template-name {
    font-size: 13px;
    color: #64748b;
}

.status-badge {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 10px 18px;
    border-radius: 24px;
    font-size: 15px;
    font-weight: 600;
    flex-shrink: 0;
}

/* 操作按钮 */
.action-bar {
    display: flex;
    gap: 12px;
    margin-top: 28px;
    padding-top: 28px;
    border-top: 1px solid #f1f5f9;
}

.download-btn {
    height: 48px;
    padding: 0 28px;
    border-radius: 12px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
    box-shadow: 0 4px 16px rgba(16, 185, 129, 0.3);
}

.share-btn {
    height: 48px;
    padding: 0 28px;
    border-radius: 12px;
    font-weight: 500;
}

/* 区块卡片 */
.section-card {
    background: white;
    border-radius: 16px;
    padding: 28px;
    margin-bottom: 16px;
    border: 1px solid #e2e8f0;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 24px;
}

/* 参数网格 */
.params-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
}

.param-item {
    display: flex;
    align-items: flex-start;
    gap: 14px;
    padding: 16px;
    background: #f8fafc;
    border-radius: 12px;
    transition: all 0.2s ease;
}

.param-item:hover {
    background: #f1f5f9;
}

.param-icon {
    width: 40px;
    height: 40px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.param-content {
    flex: 1;
    min-width: 0;
}

.param-label {
    font-size: 13px;
    color: #64748b;
    margin-bottom: 4px;
}

.param-value {
    font-size: 15px;
    font-weight: 600;
    color: #1e293b;
    word-break: break-all;
}

.param-value.mono {
    font-family: 'SF Mono', Monaco, monospace;
    font-size: 13px;
    font-weight: 500;
}

/* 配置列表 */
.config-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.config-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 14px 18px;
    background: #f8fafc;
    border-radius: 10px;
}

.config-key {
    font-size: 14px;
    color: #64748b;
    font-family: 'SF Mono', Monaco, monospace;
}

.config-value {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

/* 统计网格 */
.stats-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
}

.stat-item {
    text-align: center;
    padding: 20px;
    background: #f8fafc;
    border-radius: 12px;
}

.stat-value {
    font-size: 24px;
    font-weight: 700;
    color: #10B981;
    margin-bottom: 4px;
}

.stat-label {
    font-size: 13px;
    color: #64748b;
}

/* 时间线 */
.timeline {
    display: flex;
    flex-direction: column;
    gap: 0;
    position: relative;
}

.timeline-item {
    display: flex;
    align-items: flex-start;
    gap: 16px;
    padding-bottom: 24px;
    position: relative;
}

.timeline-item:last-child {
    padding-bottom: 0;
}

.timeline-item:not(:last-child)::before {
    content: '';
    position: absolute;
    left: 15px;
    top: 36px;
    bottom: 0;
    width: 2px;
    background: #e2e8f0;
}

.timeline-dot {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: #f1f5f9;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #64748b;
    flex-shrink: 0;
    position: relative;
    z-index: 1;
}

.timeline-dot.last {
    background: rgba(16, 185, 129, 0.1);
    color: #10B981;
}

.timeline-content {
    padding-top: 4px;
}

.timeline-label {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
    margin-bottom: 2px;
}

.timeline-value {
    font-size: 13px;
    color: #64748b;
}

.duration-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #f1f5f9;
}

.duration-label {
    font-size: 14px;
    color: #64748b;
}

.duration-value {
    font-size: 16px;
    font-weight: 700;
    color: #10B981;
}

/* 二维码区域 */
.qr-section {
    text-align: center;
}

.qr-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.qr-code {
    padding: 16px;
    background: white;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
}

.qr-hint {
    font-size: 14px;
    color: #64748b;
    margin: 16px 0;
}

.download-link {
    display: flex;
    align-items: center;
    gap: 8px;
    background: #f8fafc;
    padding: 12px 16px;
    border-radius: 10px;
    max-width: 100%;
}

.download-link code {
    font-size: 12px;
    color: #64748b;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

/* 底部操作 */
.footer-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 24px;
    padding-top: 24px;
    border-top: 1px solid #e2e8f0;
}

/* 响应式 */
@media (max-width: 768px) {
    .app-header {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .app-meta {
        justify-content: center;
    }
    
    .status-badge {
        margin-top: 16px;
    }
    
    .action-bar {
        flex-direction: column;
    }
    
    .download-btn,
    .share-btn {
        width: 100%;
    }
    
    .params-grid {
        grid-template-columns: 1fr;
    }
    
    .stats-grid {
        grid-template-columns: repeat(2, 1fr);
    }
    
    .footer-actions {
        flex-direction: column;
        gap: 12px;
    }
}
</style>
