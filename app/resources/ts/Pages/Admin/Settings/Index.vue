<script setup lang="ts">
import { computed, ref } from 'vue';
import { Head, useForm, usePage, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NAlert, NSpace, NIcon } from 'naive-ui';
import { GlobeOutline, ImageOutline, LinkOutline, CloudUploadOutline, TrashOutline, AlertCircleOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface SettingsData {
    app_name: string;
    app_logo: string;
    app_logo_url: string;
    logo_max_size_label: string;
    user_entry_path: string;
    admin_entry_path: string;
}

const props = defineProps<{
    settings: SettingsData;
}>();

const page = usePage();
const { adminRoute } = useAdminBasePath();
const settingsUrl = computed(() => adminRoute('/settings'));
const successMessage = computed(() => (page.props as { flash?: { success?: string } }).flash?.success);

const form = useForm({
    app_name: props.settings.app_name ?? '',
    app_logo: props.settings.app_logo ?? '',
    logo_file: null as File | null,
    user_entry_path: props.settings.user_entry_path ?? '',
    admin_entry_path: props.settings.admin_entry_path ?? 'admin',
});

const logoPreview = ref<string | null>(null);
const logoFileInput = ref<HTMLInputElement | null>(null);
const isDragging = ref(false);

const currentLogoUrl = computed(() => {
    if (logoPreview.value) return logoPreview.value;
    if (form.app_logo === '') return '';
    return props.settings.app_logo_url || '';
});

const hasLogoError = computed(() => Boolean(form.errors.logo_file));

const logoFileName = computed(() => form.logo_file?.name ?? null);

const logoFileSize = computed(() => {
    const file = form.logo_file;
    if (!file?.size) return null;
    const kb = file.size / 1024;
    return kb >= 1024 ? `${(kb / 1024).toFixed(1)} MB` : `${Math.round(kb)} KB`;
});

const handleFile = (file: File) => {
    if (!file.type.startsWith('image/')) return;
    form.logo_file = file;
    const reader = new FileReader();
    reader.onload = () => { logoPreview.value = reader.result as string; };
    reader.readAsDataURL(file);
};

const onLogoChange = (e: Event) => {
    const target = e.target as HTMLInputElement;
    const file = target.files?.[0];
    if (file) {
        handleFile(file);
    } else {
        form.logo_file = null;
        logoPreview.value = null;
    }
};

const onDrop = (e: DragEvent) => {
    e.preventDefault();
    isDragging.value = false;
    const file = e.dataTransfer?.files?.[0];
    if (file) handleFile(file);
};

const onDragOver = (e: DragEvent) => {
    e.preventDefault();
    isDragging.value = true;
};

const onDragLeave = () => {
    isDragging.value = false;
};

const removeLogo = () => {
    form.logo_file = null;
    form.app_logo = '';
    logoPreview.value = null;
    if (logoFileInput.value) logoFileInput.value.value = '';
};

const syncFormAfterSuccess = () => {
    form.logo_file = null;
    logoPreview.value = null;
    // useForm 不会自动随 props 同步，手动将 app_logo 对齐到最新 props
    form.app_logo = props.settings.app_logo ?? '';
    if (logoFileInput.value) logoFileInput.value.value = '';
};

const submit = () => {
    if (form.logo_file) {
        form.post(settingsUrl.value, {
            forceFormData: true,
            onSuccess: syncFormAfterSuccess,
        });
    } else {
        form.put(settingsUrl.value, {
            onSuccess: syncFormAfterSuccess,
        });
    }
};
</script>

<template>
    <Head title="系统设置" />
    <AdminLayout>
        <template #header-title>系统设置</template>
        <div class="settings-page">
            <!-- Page Header -->
            <header class="settings-header">
                <h1 class="settings-title">系统设置</h1>
                <p class="settings-subtitle">配置网站名称、Logo 与入口路径</p>
            </header>

            <!-- Success Alert -->
            <transition name="alert-slide">
                <NAlert v-if="successMessage" type="success" class="settings-alert" closable>
                    {{ successMessage }}
                </NAlert>
            </transition>

            <NForm @submit.prevent="submit">
                <!-- Website Info Card -->
                <NCard class="settings-card settings-card--delay-1">
                    <template #header>
                        <div class="card-header">
                            <div class="card-header-icon">
                                <NIcon :component="GlobeOutline" :size="20" />
                            </div>
                            <div class="card-header-text">
                                <span class="card-header-title">网站信息</span>
                                <span class="card-header-desc">设置网站名称和品牌标识</span>
                            </div>
                        </div>
                    </template>

                    <NFormItem label="网站名称">
                        <NInput
                            v-model:value="form.app_name"
                            placeholder="留空则使用 .env 中的 APP_NAME"
                            :disabled="form.processing"
                            clearable
                        />
                    </NFormItem>

                    <NFormItem label="网站 Logo" :validation-status="form.errors.logo_file ? 'error' : undefined">
                        <div class="logo-upload-area">
                            <!-- Drop Zone -->
                            <div
                                class="logo-dropzone"
                                :class="{
                                    'logo-dropzone--active': isDragging,
                                    'logo-dropzone--has-logo': currentLogoUrl,
                                    'logo-dropzone--error': hasLogoError,
                                }"
                                @drop="onDrop"
                                @dragover="onDragOver"
                                @dragleave="onDragLeave"
                                @click="logoFileInput?.click()"
                            >
                                <input
                                    ref="logoFileInput"
                                    type="file"
                                    accept="image/*"
                                    class="logo-file-input"
                                    @change="onLogoChange"
                                />

                                <!-- Error badge inside dropzone -->
                                <div v-if="hasLogoError" class="logo-dropzone-error-badge">
                                    <NIcon :component="AlertCircleOutline" :size="14" />
                                    <span>{{ form.errors.logo_file }}</span>
                                </div>

                                <!-- Preview State -->
                                <template v-if="currentLogoUrl">
                                    <div class="logo-preview-container">
                                        <button
                                            type="button"
                                            class="logo-remove-btn-inner"
                                            aria-label="移除 Logo"
                                            @click.stop="removeLogo"
                                        >
                                            <NIcon :component="TrashOutline" :size="16" />
                                        </button>
                                        <div class="logo-preview-bg" />
                                        <img :src="currentLogoUrl" alt="Logo" class="logo-preview-img" />
                                        <div class="logo-preview-overlay">
                                            <NIcon :component="CloudUploadOutline" :size="20" />
                                            <span>更换图片</span>
                                        </div>
                                    </div>
                                </template>

                                <!-- Empty State -->
                                <template v-else>
                                    <div class="logo-empty-state">
                                        <div class="logo-empty-icon">
                                            <NIcon :component="ImageOutline" :size="32" />
                                        </div>
                                        <span class="logo-empty-text">点击或拖拽上传 Logo</span>
                                        <span class="logo-empty-hint">登录页将按图片比例显示，建议宽度 80–200px</span>
                                    </div>
                                </template>
                            </div>

                            <!-- File info when new file selected -->
                            <div v-if="logoFileName" class="logo-file-info">
                                <span class="logo-file-info-name">{{ logoFileName }}</span>
                                <span v-if="logoFileSize" class="logo-file-info-size">{{ logoFileSize }}</span>
                            </div>

                            <span class="logo-hint">留空则使用 .env 中的 APP_LOGO，单张图片不超过 {{ props.settings.logo_max_size_label }}</span>
                        </div>
                    </NFormItem>
                </NCard>

                <!-- Entry Paths Card -->
                <NCard class="settings-card settings-card--delay-2">
                    <template #header>
                        <div class="card-header">
                            <div class="card-header-icon">
                                <NIcon :component="LinkOutline" :size="20" />
                            </div>
                            <div class="card-header-text">
                                <span class="card-header-title">入口路径</span>
                                <span class="card-header-desc">配置用户端和管理端的 URL 前缀</span>
                            </div>
                        </div>
                    </template>

                    <NFormItem label="用户入口路径">
                        <NInput
                            v-model:value="form.user_entry_path"
                            placeholder="如留空则为无前缀（/login, /dashboard）"
                            :disabled="form.processing"
                            clearable
                        />
                        <template #feedback>
                            {{ form.errors.user_entry_path }}
                        </template>
                        <template #extra>
                            <span class="form-extra">用户登录、控制台等 URL 的前缀。例如填 <code>portal</code> 则登录页为 <code>/portal/login</code></span>
                        </template>
                    </NFormItem>

                    <NFormItem label="总后台入口路径">
                        <NInput
                            v-model:value="form.admin_entry_path"
                            placeholder="如 admin"
                            :disabled="form.processing"
                        />
                        <template #feedback>
                            {{ form.errors.admin_entry_path }}
                        </template>
                        <template #extra>
                            <span class="form-extra">管理后台 URL 前缀。例如 <code>admin</code> 则后台为 <code>/admin/login</code></span>
                        </template>
                    </NFormItem>
                </NCard>

                <!-- Actions -->
                <div class="settings-actions settings-card--delay-3">
                    <NButton type="primary" attr-type="submit" :loading="form.processing" size="large">
                        保存设置
                    </NButton>
                    <NButton size="large" @click="router.visit(adminRoute('/dashboard'))">
                        返回控制台
                    </NButton>
                </div>
            </NForm>
        </div>
    </AdminLayout>
</template>

<style scoped>
.settings-page {
    max-width: 640px;
}

/* Header */
.settings-header {
    margin-bottom: 28px;
}

.settings-title {
    font-size: 26px;
    font-weight: 700;
    letter-spacing: -0.03em;
    color: var(--admin-text, #1a1d21);
    margin: 0 0 8px;
}

.settings-subtitle {
    font-size: 15px;
    color: var(--admin-text-muted, #6b7280);
    margin: 0;
}

/* Alert */
.settings-alert {
    margin-bottom: 24px;
}

.alert-slide-enter-active,
.alert-slide-leave-active {
    transition: all 0.3s ease;
}

.alert-slide-enter-from,
.alert-slide-leave-to {
    opacity: 0;
    transform: translateY(-12px);
}

/* Cards */
.settings-card {
    margin-bottom: 20px;
    border-radius: var(--admin-radius-lg, 16px);
    border: 1px solid var(--admin-border, rgba(0, 0, 0, 0.06));
    box-shadow: var(--admin-shadow, 0 1px 3px rgba(0, 0, 0, 0.05));
    transition: box-shadow 0.2s ease, border-color 0.2s ease;
    animation: cardReveal 0.5s ease-out backwards;
}

.settings-card:hover {
    box-shadow: var(--admin-shadow-hover, 0 8px 24px rgba(0, 0, 0, 0.08));
}

.settings-card--delay-1 { animation-delay: 0.05s; }
.settings-card--delay-2 { animation-delay: 0.15s; }
.settings-card--delay-3 { animation-delay: 0.25s; }

@keyframes cardReveal {
    from {
        opacity: 0;
        transform: translateY(12px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Card Header */
.card-header {
    display: flex;
    align-items: center;
    gap: 14px;
}

.card-header-icon {
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: var(--admin-radius, 12px);
    background: var(--admin-accent-muted, rgba(13, 148, 136, 0.12));
    color: var(--admin-accent, #0d9488);
}

.card-header-text {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.card-header-title {
    font-size: 16px;
    font-weight: 600;
    color: var(--admin-text, #1a1d21);
}

.card-header-desc {
    font-size: 13px;
    color: var(--admin-text-muted, #6b7280);
}

/* Logo Upload */
.logo-upload-area {
    display: flex;
    flex-direction: column;
    gap: 12px;
    width: 100%;
}

.logo-dropzone {
    position: relative;
    width: 100%;
    min-height: 140px;
    border: 2px dashed var(--admin-border, rgba(0, 0, 0, 0.12));
    border-radius: var(--admin-radius, 12px);
    background: var(--admin-bg, #f4f5f7);
    background-image: radial-gradient(circle at 1px 1px, rgba(0, 0, 0, 0.06) 1px, transparent 0);
    background-size: 12px 12px;
    background-position: 0 0;
    cursor: pointer;
    transition: border-color 0.2s ease, background-color 0.2s ease, transform 0.2s ease, box-shadow 0.2s ease;
    display: flex;
    align-items: center;
    justify-content: center;
}

.logo-dropzone:hover {
    border-color: var(--admin-accent, #0d9488);
    background-color: rgba(13, 148, 136, 0.04);
}

.logo-dropzone--active {
    border-color: var(--admin-accent, #0d9488);
    border-style: solid;
    background-color: var(--admin-accent-muted, rgba(13, 148, 136, 0.12));
    transform: scale(1.01);
    animation: logoDropzonePulse 1.2s ease-in-out infinite;
}

.logo-dropzone--has-logo {
    min-height: 120px;
    background-color: var(--admin-surface, #ffffff);
    background-image: none;
}

.logo-dropzone--error {
    border-color: #e5484d;
    border-style: dashed;
    background-color: rgba(229, 72, 77, 0.06);
    animation: logoDropzoneShake 0.4s ease-in-out;
}

@keyframes logoDropzonePulse {
    0%, 100% { box-shadow: 0 0 0 0 rgba(13, 148, 136, 0.2); }
    50% { box-shadow: 0 0 0 6px rgba(13, 148, 136, 0); }
}

@keyframes logoDropzoneShake {
    0%, 100% { transform: translateX(0); }
    20% { transform: translateX(-4px); }
    40% { transform: translateX(4px); }
    60% { transform: translateX(-2px); }
    80% { transform: translateX(2px); }
}

.logo-dropzone-error-badge {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    z-index: 10;
    display: inline-flex;
    align-items: center;
    gap: 6px;
    padding: 6px 12px;
    border-radius: 8px;
    background: rgba(229, 72, 77, 0.95);
    color: #fff;
    font-size: 12px;
    font-weight: 500;
    pointer-events: none;
    box-shadow: 0 2px 8px rgba(229, 72, 77, 0.3);
}

.logo-file-input {
    display: none;
}

/* Logo Preview */
.logo-preview-container {
    position: relative;
    padding: 24px;
}

.logo-preview-bg {
    position: absolute;
    inset: 24px;
    border-radius: var(--admin-radius, 12px);
    background-color: #fff;
    background-image:
        linear-gradient(45deg, #e5e7eb 25%, transparent 25%),
        linear-gradient(-45deg, #e5e7eb 25%, transparent 25%),
        linear-gradient(45deg, transparent 75%, #e5e7eb 75%),
        linear-gradient(-45deg, transparent 75%, #e5e7eb 75%);
    background-size: 12px 12px;
    background-position: 0 0, 0 6px, 6px -6px, -6px 0;
}

.logo-preview-img {
    position: relative;
    width: 96px;
    height: 96px;
    object-fit: contain;
    border-radius: var(--admin-radius, 12px);
    border: 1px solid var(--admin-border, rgba(0, 0, 0, 0.06));
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.04);
    animation: logoPreviewIn 0.25s ease-out;
}

@keyframes logoPreviewIn {
    from {
        opacity: 0;
        transform: scale(0.95);
    }
    to {
        opacity: 1;
        transform: scale(1);
    }
}

.logo-preview-overlay {
    position: absolute;
    left: 24px;
    right: 24px;
    bottom: 24px;
    padding: 8px 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    background: linear-gradient(to top, rgba(13, 148, 136, 0.92), rgba(13, 148, 136, 0.7));
    color: white;
    border-radius: 0 0 var(--admin-radius, 12px) var(--admin-radius, 12px);
    opacity: 0;
    transition: opacity 0.2s ease;
    font-size: 12px;
    font-weight: 500;
}

.logo-dropzone:hover .logo-preview-overlay {
    opacity: 1;
}

.logo-remove-btn-inner {
    position: absolute;
    top: 12px;
    right: 12px;
    width: 32px;
    height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    border: none;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.5);
    color: white;
    cursor: pointer;
    opacity: 0;
    transition: opacity 0.2s ease, background-color 0.2s ease;
    z-index: 2;
}

.logo-preview-container:hover .logo-remove-btn-inner,
.logo-remove-btn-inner:focus {
    opacity: 1;
}

.logo-remove-btn-inner:hover {
    background: #e5484d;
}

/* Logo Empty State */
.logo-empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
    padding: 20px;
}

.logo-empty-icon {
    width: 56px;
    height: 56px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background: var(--admin-surface, #ffffff);
    color: var(--admin-text-muted, #6b7280);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    transition: all 0.2s ease;
}

.logo-dropzone:hover .logo-empty-icon {
    color: var(--admin-accent, #0d9488);
    transform: scale(1.05);
}

.logo-empty-text {
    font-size: 14px;
    font-weight: 500;
    color: var(--admin-text, #1a1d21);
}

.logo-empty-hint {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
}

.logo-file-info {
    display: flex;
    align-items: baseline;
    gap: 8px;
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
}

.logo-file-info-name {
    font-weight: 500;
    color: var(--admin-text, #1a1d21);
    max-width: 280px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.logo-file-info-size {
    flex-shrink: 0;
}

.logo-hint {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
}

/* Form Extras */
.form-extra {
    font-size: 13px;
    color: var(--admin-text-muted, #6b7280);
}

.form-extra code {
    padding: 2px 6px;
    background: var(--admin-bg, #f4f5f7);
    border-radius: 4px;
    font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
    font-size: 12px;
    color: var(--admin-accent, #0d9488);
}

/* Actions */
.settings-actions {
    display: flex;
    gap: 12px;
    padding-top: 8px;
    animation: cardReveal 0.5s ease-out backwards;
}
</style>
