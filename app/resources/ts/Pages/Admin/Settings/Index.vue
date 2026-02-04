<script setup lang="ts">
import { computed, ref } from 'vue';
import { Head, useForm, usePage, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NAlert, NSpace, NIcon } from 'naive-ui';
import { GlobeOutline, ImageOutline, LinkOutline, CloudUploadOutline, TrashOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface SettingsData {
    app_name: string;
    app_logo: string;
    app_logo_url: string;
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
    return props.settings.app_logo_url || '';
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
    logoPreview.value = null;
    if (logoFileInput.value) logoFileInput.value.value = '';
};

const submit = () => {
    if (form.logo_file) {
        form.post(settingsUrl.value, {
            forceFormData: true,
            onSuccess: () => {
                form.logo_file = null;
                logoPreview.value = null;
            },
        });
    } else {
        form.put(settingsUrl.value);
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

                    <NFormItem label="网站 Logo">
                        <div class="logo-upload-area">
                            <!-- Drop Zone -->
                            <div
                                class="logo-dropzone"
                                :class="{ 'logo-dropzone--active': isDragging, 'logo-dropzone--has-logo': currentLogoUrl }"
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

                                <!-- Preview State -->
                                <template v-if="currentLogoUrl">
                                    <div class="logo-preview-container">
                                        <img :src="currentLogoUrl" alt="Logo" class="logo-preview-img" />
                                        <div class="logo-preview-overlay">
                                            <NIcon :component="CloudUploadOutline" :size="24" />
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
                                        <span class="logo-empty-hint">建议尺寸 44×44 或正方形</span>
                                    </div>
                                </template>
                            </div>

                            <!-- Remove Button -->
                            <NButton
                                v-if="currentLogoUrl"
                                size="small"
                                quaternary
                                type="error"
                                class="logo-remove-btn"
                                @click.stop="removeLogo"
                            >
                                <template #icon>
                                    <NIcon :component="TrashOutline" />
                                </template>
                                移除 Logo
                            </NButton>

                            <span class="logo-hint">留空则使用 .env 中的 APP_LOGO</span>
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
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    justify-content: center;
}

.logo-dropzone:hover {
    border-color: var(--admin-accent, #0d9488);
    background: var(--admin-accent-muted, rgba(13, 148, 136, 0.06));
}

.logo-dropzone--active {
    border-color: var(--admin-accent, #0d9488);
    background: var(--admin-accent-muted, rgba(13, 148, 136, 0.12));
    border-style: solid;
}

.logo-dropzone--has-logo {
    min-height: 120px;
    background: var(--admin-surface, #ffffff);
}

.logo-file-input {
    display: none;
}

/* Logo Preview */
.logo-preview-container {
    position: relative;
    padding: 20px;
}

.logo-preview-img {
    width: 72px;
    height: 72px;
    object-fit: contain;
    border-radius: var(--admin-radius, 12px);
    border: 1px solid var(--admin-border, rgba(0, 0, 0, 0.06));
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.logo-preview-overlay {
    position: absolute;
    inset: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 4px;
    background: rgba(13, 148, 136, 0.9);
    color: white;
    border-radius: var(--admin-radius, 12px);
    opacity: 0;
    transition: opacity 0.2s ease;
    font-size: 13px;
    font-weight: 500;
}

.logo-dropzone:hover .logo-preview-overlay {
    opacity: 1;
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

.logo-remove-btn {
    align-self: flex-start;
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
