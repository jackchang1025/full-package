<script setup lang="ts">
import { ref } from 'vue';
import { useForm, Head } from '@inertiajs/vue3';
import {
    NForm,
    NFormItem,
    NInput,
    NButton,
    NA,
    NAlert,
    NIcon,
} from 'naive-ui';
import { PersonOutline, MailOutline, LockClosedOutline, EyeOutline, EyeOffOutline } from '@vicons/ionicons5';
import DefaultLayout from '@/Layouts/DefaultLayout.vue';

const form = useForm({
    username: '',
    email: '',
    password: '',
    password_confirmation: '',
});

const showPassword = ref(false);
const showConfirmPassword = ref(false);

const submit = () => {
    form.post('/register', {
        onFinish: () => form.reset('password', 'password_confirmation'),
    });
};
</script>

<template>
    <Head title="注册" />
    <DefaultLayout>
        <div class="register-container">
            <!-- 背景装饰 -->
            <div class="bg-decoration">
                <div class="bg-circle bg-circle-1"></div>
                <div class="bg-circle bg-circle-2"></div>
                <div class="bg-circle bg-circle-3"></div>
            </div>

            <div class="register-content">
                <!-- Logo 和标题 -->
                <div class="register-header">
                    <div class="logo-wrapper">
                        <svg viewBox="0 0 24 24" fill="none" class="logo-svg">
                            <path d="M12 2L2 7L12 12L22 7L12 2Z" fill="white" />
                            <path d="M2 17L12 22L22 17" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                            <path d="M2 12L12 17L22 12" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                        </svg>
                    </div>
                    <h1 class="register-title">创建账号</h1>
                    <p class="register-subtitle">加入飞鹰管理系统</p>
                </div>

                <!-- 注册卡片 -->
                <div class="register-card">
                    <NAlert v-if="Object.keys(form.errors).length > 0" type="error" class="mb-6" closable>
                        请检查并修正以下错误
                    </NAlert>

                    <NForm @submit.prevent="submit" class="register-form">
                        <NFormItem :validation-status="form.errors.username ? 'error' : undefined">
                            <NInput
                                v-model:value="form.username"
                                placeholder="请输入用户名"
                                size="large"
                                :disabled="form.processing"
                                class="custom-input"
                            >
                                <template #prefix>
                                    <NIcon :component="PersonOutline" class="input-icon" />
                                </template>
                            </NInput>
                            <template #feedback>
                                {{ form.errors.username }}
                            </template>
                        </NFormItem>

                        <NFormItem :validation-status="form.errors.email ? 'error' : undefined">
                            <NInput
                                v-model:value="form.email"
                                type="text"
                                placeholder="请输入邮箱"
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

                        <NFormItem :validation-status="form.errors.password ? 'error' : undefined">
                            <NInput
                                v-model:value="form.password"
                                :type="showPassword ? 'text' : 'password'"
                                placeholder="请输入密码"
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

                        <NFormItem>
                            <NInput
                                v-model:value="form.password_confirmation"
                                :type="showConfirmPassword ? 'text' : 'password'"
                                placeholder="请再次输入密码"
                                size="large"
                                :disabled="form.processing"
                                class="custom-input"
                            >
                                <template #prefix>
                                    <NIcon :component="LockClosedOutline" class="input-icon" />
                                </template>
                                <template #suffix>
                                    <NIcon 
                                        :component="showConfirmPassword ? EyeOffOutline : EyeOutline" 
                                        class="password-toggle"
                                        @click="showConfirmPassword = !showConfirmPassword"
                                    />
                                </template>
                            </NInput>
                        </NFormItem>

                        <NButton
                            type="primary"
                            block
                            size="large"
                            attr-type="submit"
                            :loading="form.processing"
                            :disabled="form.processing"
                            class="register-button"
                        >
                            <span v-if="!form.processing">创建账号</span>
                        </NButton>

                        <div class="login-link">
                            已有账号？
                            <NA href="/login" class="link-highlight">立即登录</NA>
                        </div>
                    </NForm>
                </div>

                <!-- 底部信息 -->
                <p class="register-footer">
                    注册即表示您同意我们的服务条款和隐私政策
                </p>
            </div>
        </div>
    </DefaultLayout>
</template>

<style scoped>
.register-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f0fdf4 0%, #ecfdf5 50%, #f0fdfa 100%);
    position: relative;
    overflow: hidden;
    padding: 20px;
}

/* 背景装饰圆 */
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
    left: -200px;
}

.bg-circle-2 {
    width: 400px;
    height: 400px;
    background: linear-gradient(135deg, rgba(52, 211, 153, 0.1) 0%, rgba(16, 185, 129, 0.15) 100%);
    bottom: -100px;
    right: -100px;
}

.bg-circle-3 {
    width: 200px;
    height: 200px;
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.2) 0%, rgba(5, 150, 105, 0.1) 100%);
    top: 40%;
    right: 15%;
}

.register-content {
    width: 100%;
    max-width: 420px;
    position: relative;
    z-index: 1;
}

/* Logo 和标题 */
.register-header {
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

.logo-svg {
    width: 40px;
    height: 40px;
}

.register-title {
    font-size: 28px;
    font-weight: 700;
    color: #1e293b;
    margin: 0 0 8px;
}

.register-subtitle {
    font-size: 15px;
    color: #64748b;
    margin: 0;
}

/* 注册卡片 */
.register-card {
    background: white;
    border-radius: 24px;
    padding: 40px;
    box-shadow: 
        0 4px 6px rgba(0, 0, 0, 0.02),
        0 12px 24px rgba(0, 0, 0, 0.04),
        0 24px 48px rgba(16, 185, 129, 0.08);
    border: 1px solid rgba(16, 185, 129, 0.1);
}

.register-form {
    display: flex;
    flex-direction: column;
    gap: 16px;
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

.register-button {
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

.register-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.4);
}

.register-button:active {
    transform: translateY(0);
}

.login-link {
    text-align: center;
    font-size: 14px;
    color: #64748b;
    margin-top: 8px;
}

.link-highlight {
    color: #10B981;
    font-weight: 600;
    text-decoration: none;
    transition: color 0.2s;
}

.link-highlight:hover {
    color: #059669;
}

/* 底部信息 */
.register-footer {
    text-align: center;
    font-size: 13px;
    color: #94a3b8;
    margin-top: 32px;
}

/* 响应式 */
@media (max-width: 480px) {
    .register-card {
        padding: 28px 24px;
        border-radius: 20px;
    }
    
    .register-title {
        font-size: 24px;
    }
    
    .logo-wrapper {
        width: 60px;
        height: 60px;
        border-radius: 16px;
    }
    
    .logo-svg {
        width: 32px;
        height: 32px;
    }
}
</style>
