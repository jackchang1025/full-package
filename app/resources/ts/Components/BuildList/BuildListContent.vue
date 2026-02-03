<script setup lang="ts">
import { ref, computed } from 'vue';
import { router } from '@inertiajs/vue3';
import {
    NButton,
    NPopconfirm,
    NIcon,
    NInput,
    NModal,
    NQrCode,
    NSpace,
    NPagination,
    useMessage,
} from 'naive-ui';
import {
    SearchOutline,
    RefreshOutline,
    AddOutline,
    CloudDownloadOutline,
    ShareSocialOutline,
    TrashOutline,
    CubeOutline,
    CopyOutline,
} from '@vicons/ionicons5';

export interface BuildRow {
    id: number;
    name: string;
    package_name: string;
    icon_url?: string;
    is_custom?: boolean;
    created_at: string;
    download_url?: string;
    share_url?: string;
    template?: { name: string };
    user?: { id: number; username: string; email: string };
}

const props = withDefaults(
    defineProps<{
        builds: { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number };
        basePath?: string;
        showUserColumn?: boolean;
        allowCreate?: boolean;
        allowShare?: boolean;
        allowDownload?: boolean;
        allowDelete?: boolean;
        pageTitle?: string;
        filters?: Record<string, string>;
    }>(),
    {
        basePath: '/builds',
        showUserColumn: false,
        allowCreate: true,
        allowShare: true,
        allowDownload: true,
        allowDelete: true,
        pageTitle: '我的应用',
        filters: () => ({}),
    }
);

const message = useMessage();
const searchQuery = ref('');
const showShareModal = ref(false);
const shareApp = ref<BuildRow | null>(null);

const buildList = computed(() => (props.builds.data as BuildRow[]) || []);

const filteredBuilds = computed(() => {
    let result = buildList.value;
    if (searchQuery.value) {
        const q = searchQuery.value.toLowerCase();
        result = result.filter(
            (b) =>
                b.name.toLowerCase().includes(q) ||
                b.package_name.toLowerCase().includes(q) ||
                (b.user?.username ?? '').toLowerCase().includes(q) ||
                (b.user?.email ?? '').toLowerCase().includes(q)
        );
    }
    return result;
});

const statsCount = computed(() => ({
    total: props.builds.total ?? buildList.value.length,
}));

const openShareModal = (build: BuildRow) => {
    shareApp.value = build;
    showShareModal.value = true;
};

const closeShareModal = () => {
    showShareModal.value = false;
    shareApp.value = null;
};

const shareUrl = computed(() => shareApp.value?.share_url ?? '');

const copyShareLink = () => {
    if (shareUrl.value) {
        navigator.clipboard.writeText(shareUrl.value);
        message.success('链接已复制到剪贴板');
    }
};

const downloadApp = (build: BuildRow) => {
    if (build.download_url) {
        window.location.href = build.download_url;
    } else {
        message.warning('下载链接不可用，请稍后重试');
    }
};

const deleteBuild = (build: BuildRow) => {
    router.delete(`${props.basePath}/${build.id}`);
};

const refresh = () => router.reload();
const goToCreate = () => router.visit(`${props.basePath}/create`);
const goToShow = (build: BuildRow) => router.visit(`${props.basePath}/${build.id}`);

function handlePageChange(page: number) {
    const params = new URLSearchParams({ page: String(page) });
    Object.entries(props.filters).forEach(([k, v]) => {
        if (v != null && v !== '') params.set(k, v);
    });
    const query = params.toString();
    router.visit(`${props.basePath}${query ? `?${query}` : ''}`, { preserveState: true });
}

function formatCreatedAt(createdAt: string) {
    if (!createdAt) return '';
    try {
        const d = new Date(createdAt);
        return d.toLocaleString('zh-CN');
    } catch {
        return createdAt;
    }
}
</script>

<template>
    <div class="builds-container">
        <div class="header-bar">
            <div class="header-left">
                <h2 class="page-title">{{ pageTitle }}</h2>
                <div class="stats-badges">
                    <span class="stat-badge">
                        <span class="stat-count">{{ statsCount.total }}</span> 全部
                    </span>
                </div>
            </div>
            <NButton v-if="allowCreate" type="primary" class="create-btn" @click="goToCreate">
                <template #icon>
                    <NIcon :component="AddOutline" />
                </template>
                新建构建
            </NButton>
        </div>

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

        <div v-if="filteredBuilds.length > 0" class="app-list">
            <div
                v-for="build in filteredBuilds"
                :key="build.id"
                class="app-item"
                @click="goToShow(build)"
            >
                <div class="app-icon">
                    <img v-if="build.icon_url" :src="build.icon_url" :alt="build.name" />
                    <NIcon v-else :component="CubeOutline" size="32" color="#94a3b8" />
                </div>
                <div class="app-info">
                    <div class="app-name">{{ build.name }}</div>
                    <div class="app-package">{{ build.package_name }}</div>
                    <div class="app-meta">
                        <span class="app-date">构建: {{ formatCreatedAt(build.created_at) }}</span>
                        <span v-if="build.template" class="app-template"> · {{ build.template.name }}</span>
                        <span v-else-if="build.is_custom" class="app-template"> · 自定义</span>
                        <span v-if="showUserColumn && build.user" class="app-user"> · {{ build.user.username }}</span>
                    </div>
                </div>
                <div class="app-actions" @click.stop>
                    <NButton
                        v-if="allowShare"
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
                        v-if="allowDownload"
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
                        v-if="allowDelete"
                        :positive-button-props="{ type: 'error' }"
                        @positive-click="deleteBuild(build)"
                    >
                        <template #trigger>
                            <NButton type="error" size="small" class="action-btn delete-btn" quaternary>
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

        <div v-else class="empty-state">
            <div class="empty-icon">
                <NIcon :component="CubeOutline" size="56" color="#cbd5e1" />
            </div>
            <h3 class="empty-title">暂无构建记录</h3>
            <p class="empty-description">
                {{ searchQuery ? '没有找到匹配的应用' : '点击上方按钮创建您的第一个应用' }}
            </p>
            <NButton v-if="allowCreate && !searchQuery" type="primary" @click="goToCreate">
                <template #icon>
                    <NIcon :component="AddOutline" />
                </template>
                新建构建
            </NButton>
        </div>

        <div v-if="builds.last_page > 1" class="pagination-wrapper">
            <NPagination
                :page="builds.current_page"
                :page-count="builds.last_page"
                :page-size="builds.per_page ?? 20"
                @update:page="handlePageChange"
            />
        </div>

        <NModal
            v-model:show="showShareModal"
            preset="card"
            :title="`分享 ${shareApp?.name || ''}`"
            class="share-modal"
            style="width: 420px"
            :bordered="false"
        >
            <div v-if="shareApp" class="share-content">
                <div class="share-app-info">
                    <div class="share-icon">
                        <img v-if="shareApp.icon_url" :src="shareApp.icon_url" :alt="shareApp.name" />
                        <NIcon v-else :component="CubeOutline" size="32" color="#94a3b8" />
                    </div>
                    <div class="share-details">
                        <div class="share-name">{{ shareApp.name }}</div>
                        <div class="share-package">{{ shareApp.package_name }}</div>
                    </div>
                </div>
                <div class="qr-section">
                    <NQrCode :value="shareUrl" :size="180" class="qr-code" />
                    <p class="qr-hint">扫描二维码下载应用</p>
                </div>
                <div class="share-link-section">
                    <div class="share-link">
                        <span class="link-text">{{ shareUrl || '暂无分享链接' }}</span>
                        <NButton size="small" quaternary :disabled="!shareUrl" @click="copyShareLink">
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
                    <NButton type="primary" @click="shareApp && downloadApp(shareApp)">
                        <template #icon>
                            <NIcon :component="CloudDownloadOutline" />
                        </template>
                        下载 APK
                    </NButton>
                </NSpace>
            </template>
        </NModal>
    </div>
</template>

<style scoped>
.builds-container {
    max-width: 1000px;
}

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
    background: linear-gradient(135deg, #10b981 0%, #059669 100%);
    border: none;
    box-shadow: 0 4px 16px rgba(16, 185, 129, 0.3);
    transition: all 0.3s ease;
}

.create-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.35);
}

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
    border-color: #10b981;
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.1);
    transform: translateY(-2px);
}

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
    color: #10b981;
}

.app-user {
    color: #64748b;
}

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
    background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
    border: none;
}

.download-btn {
    background: linear-gradient(135deg, #10b981 0%, #059669 100%);
    border: none;
}

.delete-btn {
    color: #ef4444;
}

.delete-btn:hover {
    background: rgba(239, 68, 68, 0.1);
}

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

.pagination-wrapper {
    display: flex;
    justify-content: center;
    padding: 20px;
    margin-top: 16px;
}

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
