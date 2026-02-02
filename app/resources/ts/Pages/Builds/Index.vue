<script setup lang="ts">
import { ref, computed } from 'vue';
import { Head, router } from '@inertiajs/vue3';
import {
    NButton,
    NEmpty,
    NPopconfirm,
    NIcon,
    NInput,
    NModal,
    NQrCode,
    NSpace,
    useMessage,
} from 'naive-ui';
import {
    SearchOutline,
    RefreshOutline,
    AddOutline,
    CloudDownloadOutline,
    ShareSocialOutline,
    TrashOutline,
    CheckmarkCircleOutline,
    CubeOutline,
    CopyOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';

interface AppBuild {
    id: number;
    name: string;
    package_name: string;
    icon_path?: string;
    icon_url?: string;
    is_custom: boolean;
    created_at: string;
    download_url?: string;
    template?: { name: string };
}

interface Props {
    builds: {
        data: AppBuild[];
        current_page: number;
        last_page: number;
    };
}

const props = defineProps<Props>();
const message = useMessage();

const searchQuery = ref('');
const showShareModal = ref(false);
const shareApp = ref<AppBuild | null>(null);

const filteredBuilds = computed(() => {
    let result = props.builds.data;
    
    if (searchQuery.value) {
        const query = searchQuery.value.toLowerCase();
        result = result.filter(b => 
            b.name.toLowerCase().includes(query) ||
            b.package_name.toLowerCase().includes(query)
        );
    }
    
    return result;
});

const statsCount = computed(() => ({
    total: props.builds.data.length,
}));

const openShareModal = (build: AppBuild) => {
    shareApp.value = build;
    showShareModal.value = true;
};

const closeShareModal = () => {
    showShareModal.value = false;
    shareApp.value = null;
};

const copyShareLink = () => {
    if (shareApp.value?.download_url) {
        navigator.clipboard.writeText(window.location.origin + shareApp.value.download_url);
    }
};

const downloadApp = (build: AppBuild) => {
    if (build.download_url) {
        window.location.href = build.download_url;
    } else {
        message.warning('下载链接不可用，请稍后重试');
    }
};

const deleteBuild = (build: AppBuild) => {
    router.delete(`/builds/${build.id}`);
};

const refresh = () => router.reload();
const goToCreate = () => router.visit('/builds/create');
</script>

<template>
    <Head title="APK 构建" />
    <AuthenticatedLayout>
        <template #header-title>APK 构建</template>

        <div class="builds-container">
            <!-- 顶部操作栏 -->
            <div class="header-bar">
                <div class="header-left">
                    <h2 class="page-title">我的应用</h2>
                    <div class="stats-badges">
                        <span class="stat-badge">
                            <span class="stat-count">{{ statsCount.total }}</span> 全部
                        </span>
                    </div>
                </div>
                <NButton type="primary" class="create-btn" @click="goToCreate">
                    <template #icon>
                        <NIcon :component="AddOutline" />
                    </template>
                    新建构建
                </NButton>
            </div>

            <!-- 搜索筛选栏 -->
            <div class="filter-bar">
                <div class="filter-left">
                    <NInput
                        v-model:value="searchQuery"
                        placeholder="搜索应用名称、包名..."
                        clearable
                        class="search-input"
                    >
                        <template #prefix>
                            <NIcon :component="SearchOutline" color="#94a3b8" />
                        </template>
                    </NInput>
                </div>
                <NButton quaternary circle @click="refresh">
                    <template #icon>
                        <NIcon :component="RefreshOutline" />
                    </template>
                </NButton>
            </div>

            <!-- 应用列表 -->
            <div class="app-list" v-if="filteredBuilds.length > 0">
                <div 
                    v-for="build in filteredBuilds" 
                    :key="build.id" 
                    class="app-item"
                    @click="router.visit(`/builds/${build.id}`)"
                >
                    <!-- 应用图标 -->
                    <div class="app-icon">
                        <img 
                            v-if="build.icon_url" 
                            :src="build.icon_url" 
                            :alt="build.name"
                        />
                        <NIcon v-else :component="CubeOutline" size="32" color="#94a3b8" />
                    </div>

                    <!-- 应用信息 -->
                    <div class="app-info">
                        <div class="app-name">{{ build.name }}</div>
                        <div class="app-package">{{ build.package_name }}</div>
                        <div class="app-meta">
                            <span class="app-date">构建: {{ build.created_at }}</span>
                            <span v-if="build.template" class="app-template">
                                · {{ build.template.name }}
                            </span>
                            <span v-else-if="build.is_custom" class="app-template">
                                · 自定义
                            </span>
                        </div>
                    </div>

                    <!-- 操作按钮 -->
                    <div class="app-actions" @click.stop>
                        <NButton 
                            type="primary"
                            size="small"
                            class="action-btn share-btn"
                            @click="openShareModal(build)"
                        >
                            <template #icon>
                                <NIcon :component="ShareSocialOutline" />
                            </template>
                            分享
                        </NButton>
                        
                        <NButton 
                            type="success"
                            size="small"
                            class="action-btn download-btn"
                            @click="downloadApp(build)"
                        >
                            <template #icon>
                                <NIcon :component="CloudDownloadOutline" />
                            </template>
                            下载
                        </NButton>

                        <NPopconfirm 
                            @positive-click="deleteBuild(build)"
                            :positive-button-props="{ type: 'error' }"
                        >
                            <template #trigger>
                                <NButton 
                                    type="error"
                                    size="small"
                                    class="action-btn delete-btn"
                                    quaternary
                                >
                                    <template #icon>
                                        <NIcon :component="TrashOutline" />
                                    </template>
                                </NButton>
                            </template>
                            确定要删除此构建吗？
                        </NPopconfirm>
                    </div>
                </div>
            </div>

            <!-- 空状态 -->
            <div v-else class="empty-state">
                <div class="empty-icon">
                    <NIcon :component="CubeOutline" size="56" color="#cbd5e1" />
                </div>
                <h3 class="empty-title">暂无构建记录</h3>
                <p class="empty-description">
                    {{ searchQuery ? '没有找到匹配的应用' : '点击上方按钮创建您的第一个应用' }}
                </p>
                <NButton 
                    v-if="!searchQuery"
                    type="primary" 
                    @click="goToCreate"
                >
                    <template #icon>
                        <NIcon :component="AddOutline" />
                    </template>
                    新建构建
                </NButton>
            </div>
        </div>

        <!-- 分享弹窗 -->
        <NModal 
            v-model:show="showShareModal" 
            preset="card"
            :title="`分享 ${shareApp?.name || ''}`"
            class="share-modal"
            style="width: 420px;"
            :bordered="false"
        >
            <div class="share-content" v-if="shareApp">
                <div class="share-app-info">
                    <div class="share-icon">
                        <img 
                            v-if="shareApp.icon_url" 
                            :src="shareApp.icon_url" 
                            :alt="shareApp.name"
                        />
                        <NIcon v-else :component="CubeOutline" size="32" color="#94a3b8" />
                    </div>
                    <div class="share-details">
                        <div class="share-name">{{ shareApp.name }}</div>
                        <div class="share-package">{{ shareApp.package_name }}</div>
                    </div>
                </div>

                <div class="qr-section">
                    <NQrCode 
                        :value="shareApp.download_url ? window.location.origin + shareApp.download_url : ''" 
                        :size="180"
                        class="qr-code"
                    />
                    <p class="qr-hint">扫描二维码下载应用</p>
                </div>

                <div class="share-link-section">
                    <div class="share-link">
                        <span class="link-text">{{ shareApp.download_url || '暂无下载链接' }}</span>
                        <NButton 
                            size="small" 
                            quaternary 
                            @click="copyShareLink"
                        >
                            <template #icon>
                                <NIcon :component="CopyOutline" />
                            </template>
                        </NButton>
                    </div>
                </div>
            </div>

            <template #footer>
                <NSpace justify="end">
                    <NButton @click="closeShareModal">关闭</NButton>
                    <NButton type="primary" @click="downloadApp(shareApp!)">
                        <template #icon>
                            <NIcon :component="CloudDownloadOutline" />
                        </template>
                        下载 APK
                    </NButton>
                </NSpace>
            </template>
        </NModal>
    </AuthenticatedLayout>
</template>

<style scoped>
.builds-container {
    max-width: 1000px;
}

/* 顶部操作栏 */
.header-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 20px;
}

.page-title {
    font-size: 24px;
    font-weight: 700;
    color: #1e293b;
    margin: 0;
}

.stats-badges {
    display: flex;
    gap: 12px;
}

.stat-badge {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: #64748b;
    background: #f1f5f9;
    padding: 6px 12px;
    border-radius: 20px;
}

.stat-count {
    font-weight: 700;
}

.create-btn {
    height: 44px;
    padding: 0 24px;
    border-radius: 12px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
    box-shadow: 0 4px 16px rgba(16, 185, 129, 0.3);
    transition: all 0.3s ease;
}

.create-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.35);
}

/* 筛选栏 */
.filter-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: white;
    border-radius: 14px;
    padding: 16px 20px;
    margin-bottom: 16px;
    border: 1px solid #e2e8f0;
}

.filter-left {
    display: flex;
    gap: 12px;
}

.search-input {
    width: 280px;
}

.search-input :deep(.n-input) {
    border-radius: 10px;
    background: #f8fafc;
}

/* 应用列表 */
.app-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.app-item {
    display: flex;
    align-items: center;
    gap: 16px;
    background: white;
    border-radius: 16px;
    padding: 20px 24px;
    border: 1px solid #e2e8f0;
    cursor: pointer;
    transition: all 0.3s ease;
}

.app-item:hover {
    border-color: #10B981;
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.1);
    transform: translateY(-2px);
}

/* 应用图标 */
.app-icon {
    width: 64px;
    height: 64px;
    border-radius: 16px;
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

/* 应用信息 */
.app-info {
    flex: 1;
    min-width: 0;
}

.app-name {
    font-size: 16px;
    font-weight: 600;
    color: #1e293b;
    margin-bottom: 4px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.app-package {
    font-size: 13px;
    color: #64748b;
    font-family: 'SF Mono', Monaco, monospace;
    margin-bottom: 6px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.app-meta {
    font-size: 12px;
    color: #94a3b8;
}

.app-template {
    color: #10B981;
}

/* 操作按钮 */
.app-actions {
    display: flex;
    align-items: center;
    gap: 8px;
    flex-shrink: 0;
}

.action-btn {
    border-radius: 10px;
    font-weight: 500;
}

.share-btn {
    background: linear-gradient(135deg, #3B82F6 0%, #2563EB 100%);
    border: none;
}

.download-btn {
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
}

.delete-btn {
    color: #EF4444;
}

.delete-btn:hover {
    background: rgba(239, 68, 68, 0.1);
}

/* 空状态 */
.empty-state {
    background: white;
    border-radius: 20px;
    padding: 80px 40px;
    text-align: center;
    border: 1px solid #e2e8f0;
}

.empty-icon {
    width: 100px;
    height: 100px;
    background: #f8fafc;
    border-radius: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 24px;
}

.empty-title {
    font-size: 20px;
    font-weight: 600;
    color: #475569;
    margin: 0 0 8px;
}

.empty-description {
    font-size: 14px;
    color: #94a3b8;
    margin: 0 0 24px;
}

/* 分享弹窗 */
.share-content {
    padding: 8px 0;
}

.share-app-info {
    display: flex;
    align-items: center;
    gap: 16px;
    padding-bottom: 20px;
    border-bottom: 1px solid #f1f5f9;
    margin-bottom: 24px;
}

.share-icon {
    width: 56px;
    height: 56px;
    border-radius: 14px;
    background: #f8fafc;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    border: 1px solid #e2e8f0;
}

.share-icon img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.share-name {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin-bottom: 4px;
}

.share-package {
    font-size: 13px;
    color: #64748b;
    font-family: 'SF Mono', Monaco, monospace;
}

.qr-section {
    text-align: center;
    margin-bottom: 24px;
}

.qr-code {
    margin: 0 auto;
    padding: 16px;
    background: white;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
}

.qr-hint {
    font-size: 13px;
    color: #94a3b8;
    margin: 16px 0 0;
}

.share-link-section {
    background: #f8fafc;
    border-radius: 12px;
    padding: 4px;
}

.share-link {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 16px;
}

.link-text {
    font-size: 13px;
    color: #64748b;
    font-family: 'SF Mono', Monaco, monospace;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

/* 响应式 */
@media (max-width: 768px) {
    .header-bar {
        flex-direction: column;
        align-items: stretch;
        gap: 16px;
    }
    
    .header-left {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
    
    .stats-badges {
        flex-wrap: wrap;
    }
    
    .filter-bar {
        flex-direction: column;
        gap: 12px;
        align-items: stretch;
    }
    
    .filter-left {
        flex-direction: column;
    }
    
    .search-input {
        width: 100%;
    }
    
    .app-item {
        flex-wrap: wrap;
        padding: 16px;
    }
    
    .app-info {
        flex: 1 1 calc(100% - 80px);
    }
    
    .app-actions {
        order: 3;
        width: 100%;
        justify-content: flex-end;
        margin-top: 12px;
        padding-top: 12px;
        border-top: 1px solid #f1f5f9;
    }
}
</style>
