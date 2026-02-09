<script setup lang="ts">
import { ref, computed } from 'vue';
import { useForm, usePage, Head } from '@inertiajs/vue3';
import {
    NForm,
    NFormItem,
    NInput,
    NButton,
    NIcon,
    NCheckbox,
    NAlert,
} from 'naive-ui';
import { LockClosedOutline, MailOutline, EyeOutline, EyeOffOutline, ShieldCheckmarkOutline } from '@vicons/ionicons5';
import DefaultLayout from '@/Layouts/DefaultLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const page = usePage();
const shared = computed(() => page.props as { appName?: string; appLogo?: string });
const { adminRoute } = useAdminBasePath();
const form = useForm({
    email: '',
    password: '',
    remember: false,
});

const showPassword = ref(false);

const submit = () => {
    form.post(adminRoute('/login'), {
        onFinish: () => form.reset('password'),
    });
};
</script>

<template>
    <Head title="管理后台登录" />
    <DefaultLayout>
        <div class="admin-login-container">
            <div class="bg-decoration">
                <div class="bg-circle bg-circle-1"></div>
                <div class="bg-circle bg-circle-2"></div>
                <div class="bg-circle bg-circle-3"></div>
            </div>

            <div class="admin-login-content">
                <div class="admin-login-header">
                    <div class="logo-wrapper" :class="{ 'logo-wrapper--custom': !!shared.appLogo }">
                        <img v-if="shared.appLogo" :src="shared.appLogo" :alt="shared.appName ?? ''" class="logo-img" />
                        <NIcon v-else :component="ShieldCheckmarkOutline" class="logo-icon" />
                    </div>
                    <h1 class="admin-login-title">管理后台</h1>
                    <p class="admin-login-subtitle">登录 {{ shared.appName }} 管理员账号</p>
                </div>

                <div class="admin-login-card">
                    <NAlert v-if="form.errors.email" type="error" class="mb-6" closable>
                        {{ form.errors.email }}
                    </NAlert>

                    <NForm @submit.prevent="submit" class="admin-login-form">
                        <NFormItem :validation-status="form.errors.email ? 'error' : undefined">
                            <NInput
                                v-model:value="form.email"
                                type="text"
                                placeholder="管理员邮箱"
                                size="large"
                                :disabled="form.processing"
                                class="custom-input"
                            >
                                <template #prefix>
                                    <NIcon :component="MailOutline" class="input-icon" />
                                </template>
                            </NInput>
                        </NFormItem>

                        <NFormItem :validation-status="form.errors.password ? 'error' : undefined">
                            <NInput
                                v-model:value="form.password"
                                :type="showPassword ? 'text' : 'password'"
                                placeholder="密码"
                                size="large"
                                :disabled="form.processing"
                                class="custom-input"
                            >
                                <template #prefix>
                                    <NIcon :component="LockClosedOutline" class="input-icon" />
                                </template>
                                <template #suffix>
                                    <NIcon
                                        :component="showPassword ? EyeOffOutline : EyeOutline"
                                        class="password-toggle"
                                        @click="showPassword = !showPassword"
                                    />
                                </template>
                            </NInput>
                            <template #feedback>
                                {{ form.errors.password }}
                            </template>
                        </NFormItem>

                        <div class="form-options">
                            <NCheckbox v-model:checked="form.remember" class="remember-checkbox">
                                记住我
                            </NCheckbox>
                        </div>

                        <NButton
                            type="primary"
                            block
                            size="large"
                            attr-type="submit"
                            :loading="form.processing"
                            :disabled="form.processing"
                            class="login-button"
                        >
                            <span v-if="!form.processing">登 录</span>
                        </NButton>
                    </NForm>
                </div>

                <p class="admin-login-footer">
                    © {{ new Date().getFullYear() }} {{ shared.appName }} · 管理后台
                </p>
            </div>
        </div>
    </DefaultLayout>
</template>

<style scoped>
/* 与用户后台认证页一致的浅色背景与装饰 */
.admin-login-container {
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

.admin-login-content {
    width: 100%;
    max-width: 420px;
    position: relative;
    z-index: 1;
}

.admin-login-header {
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

.logo-icon {
    font-size: 36px;
    color: white;
}

.admin-login-title {
    font-size: 28px;
    font-weight: 700;
    color: #1e293b;
    margin: 0 0 8px;
}

.admin-login-subtitle {
    font-size: 15px;
    color: #64748b;
    margin: 0;
}

/* 与用户后台一致的白色卡片与翠绿描边 */
.admin-login-card {
    background: white;
    border-radius: 24px;
    padding: 40px;
    box-shadow:
        0 4px 6px rgba(0, 0, 0, 0.02),
        0 12px 24px rgba(0, 0, 0, 0.04),
        0 24px 48px rgba(16, 185, 129, 0.08);
    border: 1px solid rgba(16, 185, 129, 0.1);
}

.admin-login-form {
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

.password-toggle {
    color: #94a3b8;
    cursor: pointer;
    transition: color 0.2s;
}

.password-toggle:hover {
    color: #10B981;
}

.form-options {
    display: flex;
    align-items: center;
    margin-top: -8px;
}

.remember-checkbox {
    font-size: 14px;
    color: #64748b;
}

.login-button {
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

.login-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.4);
}

.login-button:active {
    transform: translateY(0);
}

.admin-login-footer {
    text-align: center;
    font-size: 13px;
    color: #94a3b8;
    margin-top: 32px;
}

@media (max-width: 480px) {
    .admin-login-card {
        padding: 28px 24px;
        border-radius: 20px;
    }

    .admin-login-title {
        font-size: 24px;
    }

    .logo-wrapper {
        width: 60px;
        height: 60px;
        border-radius: 16px;
    }

    .logo-wrapper--custom {
        min-width: 60px;
        min-height: 60px;
        max-width: 140px;
        max-height: 84px;
    }

    .logo-icon {
        font-size: 28px;
    }
}
</style>
