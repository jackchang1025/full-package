<script setup lang="ts">
import { useForm, usePage, Head } from '@inertiajs/vue3';
import { NForm, NFormItem, NInput, NButton, NA, NAlert, NIcon } from 'naive-ui';
import { MailOutline } from '@vicons/ionicons5';
import DefaultLayout from '@/Layouts/DefaultLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const page = usePage();
const shared = (page.props as { appName?: string; appLogo?: string }) ?? {};
const { userRoute } = useAdminBasePath();

const form = useForm({
    email: '',
});

const submit = () => {
    form.post(userRoute('/forgot-password'), {
        onFinish: () => form.reset('email'),
    });
};
</script>

<template>
    <Head title="找回密码" />
    <DefaultLayout>
        <div class="auth-container">
            <div class="bg-decoration">
                <div class="bg-circle bg-circle-1"></div>
                <div class="bg-circle bg-circle-2"></div>
                <div class="bg-circle bg-circle-3"></div>
            </div>

            <div class="auth-content">
                <div class="auth-header">
                    <div class="logo-wrapper" :class="{ 'logo-wrapper--custom': !!shared.appLogo }">
                        <img v-if="shared.appLogo" :src="shared.appLogo" :alt="shared.appName ?? ''" class="logo-img" />
                        <svg v-else viewBox="0 0 24 24" fill="none" class="logo-svg">
                            <path d="M12 2L2 7L12 12L22 7L12 2Z" fill="white" />
                            <path d="M2 17L12 22L22 17" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                            <path d="M2 12L12 17L22 12" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                        </svg>
                    </div>
                    <h1 class="auth-title">找回密码</h1>
                    <p class="auth-subtitle">输入注册邮箱，我们将发送重置链接</p>
                </div>

                <div class="auth-card">
                    <NAlert v-if="form.recentlySuccessful" type="success" class="mb-6" closable>
                        重置链接已发送到您的邮箱，请查收。
                    </NAlert>
                    <NAlert v-if="form.errors.email" type="error" class="mb-6" closable>
                        {{ form.errors.email }}
                    </NAlert>

                    <NForm @submit.prevent="submit" class="auth-form">
                        <NFormItem :validation-status="form.errors.email ? 'error' : undefined">
                            <NInput
                                v-model:value="form.email"
                                type="text"
                                placeholder="请输入注册邮箱"
                                size="large"
                                :disabled="form.processing"
                                class="custom-input"
                            >
                                <template #prefix>
                                    <NIcon :component="MailOutline" class="input-icon" />
                                </template>
                            </NInput>
                            <template #feedback>
                                {{ form.errors.email }}
                            </template>
                        </NFormItem>

                        <NButton
                            type="primary"
                            block
                            size="large"
                            attr-type="submit"
                            :loading="form.processing"
                            :disabled="form.processing"
                            class="auth-button"
                        >
                            <span v-if="!form.processing">发送重置链接</span>
                        </NButton>
                    </NForm>

                    <p class="auth-footer-link">
                        <NA :href="userRoute('/login')">返回登录</NA>
                    </p>
                </div>

                <p class="auth-footer">
                    © 2024 {{ shared.appName }} · 安全可靠的设备管理平台
                </p>
            </div>
        </div>
    </DefaultLayout>
</template>

<style scoped>
.auth-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f0fdf4 0%, #ecfdf5 50%, #f0fdfa 100%);
    position: relative;
    overflow: hidden;
    padding: 20px;
}

.bg-decoration {
    position: absolute;
    inset: 0;
    overflow: hidden;
    pointer-events: none;
}

.bg-circle {
    position: absolute;
    border-radius: 50%;
    opacity: 0.5;
}

.bg-circle-1 {
    width: 600px;
    height: 600px;
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.15) 0%, rgba(5, 150, 105, 0.1) 100%);
    top: -200px;
    right: -200px;
}

.bg-circle-2 {
    width: 400px;
    height: 400px;
    background: linear-gradient(135deg, rgba(52, 211, 153, 0.1) 0%, rgba(16, 185, 129, 0.15) 100%);
    bottom: -100px;
    left: -100px;
}

.bg-circle-3 {
    width: 200px;
    height: 200px;
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.2) 0%, rgba(5, 150, 105, 0.1) 100%);
    top: 50%;
    left: 10%;
}

.auth-content {
    width: 100%;
    max-width: 420px;
    position: relative;
    z-index: 1;
}

.auth-header {
    text-align: center;
    margin-bottom: 32px;
}

.logo-wrapper {
    width: 72px;
    height: 72px;
    margin: 0 auto 20px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 12px 32px rgba(16, 185, 129, 0.35);
}

.logo-wrapper--custom {
    background: transparent;
    box-shadow: none;
    width: auto;
    height: auto;
    min-width: 72px;
    min-height: 72px;
    max-width: 200px;
    max-height: 120px;
}

.logo-wrapper .logo-img {
    width: 48px;
    height: 48px;
    object-fit: contain;
}

.logo-wrapper--custom .logo-img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
    display: block;
}

.logo-svg {
    width: 40px;
    height: 40px;
}

.auth-title {
    font-size: 28px;
    font-weight: 700;
    color: #1e293b;
    margin: 0 0 8px;
}

.auth-subtitle {
    font-size: 15px;
    color: #64748b;
    margin: 0;
}

.auth-card {
    background: white;
    border-radius: 24px;
    padding: 40px;
    box-shadow:
        0 4px 6px rgba(0, 0, 0, 0.02),
        0 12px 24px rgba(0, 0, 0, 0.04),
        0 24px 48px rgba(16, 185, 129, 0.08);
    border: 1px solid rgba(16, 185, 129, 0.1);
}

.auth-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.custom-input :deep(.n-input) {
    border-radius: 12px;
    background: #f8fafc;
    border: 2px solid transparent;
    transition: all 0.3s ease;
}

.custom-input :deep(.n-input:hover) {
    background: #f1f5f9;
}

.custom-input :deep(.n-input--focus) {
    background: white;
    border-color: #10B981;
    box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
}

.input-icon {
    color: #94a3b8;
    font-size: 18px;
}

.auth-button {
    height: 48px;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
    box-shadow: 0 4px 16px rgba(16, 185, 129, 0.35);
    transition: all 0.3s ease;
    margin-top: 8px;
}

.auth-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.4);
}

.auth-footer-link {
    text-align: center;
    margin-top: 20px;
}

.auth-footer-link a {
    font-size: 14px;
    color: #10B981;
    text-decoration: none;
}

.auth-footer-link a:hover {
    color: #059669;
}

.auth-footer {
    text-align: center;
    font-size: 13px;
    color: #94a3b8;
    margin-top: 32px;
}

.mb-6 {
    margin-bottom: 24px;
}
</style>
