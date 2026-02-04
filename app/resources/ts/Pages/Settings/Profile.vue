<script setup lang="ts">
import { computed, ref } from 'vue';
import { useForm, Head, usePage } from '@inertiajs/vue3';
import {
    NCard,
    NForm,
    NFormItem,
    NInput,
    NButton,
    NSpace,
    NAlert,
    NTabs,
    NTabPane,
    NIcon,
    NTag,
    NProgress,
    NDivider,
} from 'naive-ui';
import {
    PersonOutline,
    LockClosedOutline,
    CardOutline,
    MailOutline,
    CallOutline,
    CheckmarkCircleOutline,
    TimeOutline,
    ShieldCheckmarkOutline,
    EyeOutline,
    EyeOffOutline,
} from '@vicons/ionicons5';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const page = usePage();
const user = computed(() => page.props.auth?.user);

const { userRoute } = useAdminBasePath();

const profileForm = useForm({
    username: user.value?.username || '',
    email: user.value?.email || '',
    contact: user.value?.contact || '',
});

const passwordForm = useForm({
    current_password: '',
    password: '',
    password_confirmation: '',
});

const showCurrentPassword = ref(false);
const showNewPassword = ref(false);
const showConfirmPassword = ref(false);

const submitProfile = () => {
    profileForm.put(userRoute('/user/profile-information'));
};

const submitPassword = () => {
    passwordForm.put(userRoute('/user/password'), {
        onSuccess: () => passwordForm.reset(),
    });
};

const subscriptionStatus = computed(() => {
    if (!user.value?.subscription_expires_at) return 'none';
    const expires = new Date(user.value.subscription_expires_at);
    return expires > new Date() ? 'active' : 'expired';
});

const daysRemaining = computed(() => {
    if (!user.value?.subscription_expires_at) return 0;
    const expires = new Date(user.value.subscription_expires_at);
    const now = new Date();
    const diff = expires.getTime() - now.getTime();
    return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)));
});
</script>

<template>
    <Head title="个人设置" />
    <AuthenticatedLayout>
        <template #header-title>设置</template>

        <div class="settings-container">
            <!-- 用户信息卡片 -->
            <div class="user-card">
                <div class="user-avatar">
                    {{ user?.username?.charAt(0).toUpperCase() }}
                </div>
                <div class="user-info">
                    <h2 class="user-name">{{ user?.username }}</h2>
                    <p class="user-email">{{ user?.email }}</p>
                    <div class="user-badges">
                        <NTag
                            v-for="r in (user?.roles ?? [])"
                            :key="r"
                            type="info"
                            size="small"
                            round
                        >
                            {{ (page.props.roleLabels as Record<string, string>)?.[r] ?? r }}
                        </NTag>
                        <NTag 
                            v-if="subscriptionStatus === 'active'" 
                            type="warning" 
                            size="small" 
                            round
                        >
                            <template #icon>
                                <NIcon :component="ShieldCheckmarkOutline" />
                            </template>
                            订阅中
                        </NTag>
                    </div>
                </div>
            </div>

            <!-- 设置选项卡 -->
            <div class="settings-card">
                <NTabs type="line" animated class="settings-tabs">
                    <!-- 个人资料 -->
                    <NTabPane name="profile">
                        <template #tab>
                            <div class="tab-label">
                                <NIcon :component="PersonOutline" size="18" />
                                <span>个人资料</span>
                            </div>
                        </template>

                        <div class="tab-content">
                            <NAlert v-if="profileForm.recentlySuccessful" type="success" class="mb-6" closable>
                                <template #icon>
                                    <NIcon :component="CheckmarkCircleOutline" />
                                </template>
                                个人资料已更新
                            </NAlert>

                            <NForm @submit.prevent="submitProfile" class="settings-form">
                                <div class="form-section">
                                    <h3 class="section-title">基本信息</h3>
                                    
                                    <NFormItem label="用户名" :validation-status="profileForm.errors.username ? 'error' : undefined">
                                        <NInput
                                            v-model:value="profileForm.username"
                                            placeholder="请输入用户名"
                                            size="large"
                                            :disabled="profileForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="PersonOutline" color="#94a3b8" />
                                            </template>
                                        </NInput>
                                        <template #feedback>{{ profileForm.errors.username }}</template>
                                    </NFormItem>

                                    <NFormItem label="邮箱地址" :validation-status="profileForm.errors.email ? 'error' : undefined">
                                        <NInput
                                            v-model:value="profileForm.email"
                                            type="text"
                                            placeholder="请输入邮箱"
                                            size="large"
                                            :disabled="profileForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="MailOutline" color="#94a3b8" />
                                            </template>
                                        </NInput>
                                        <template #feedback>{{ profileForm.errors.email }}</template>
                                    </NFormItem>

                                    <NFormItem label="联系方式">
                                        <NInput
                                            v-model:value="profileForm.contact"
                                            placeholder="Telegram / 微信等"
                                            size="large"
                                            :disabled="profileForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="CallOutline" color="#94a3b8" />
                                            </template>
                                        </NInput>
                                    </NFormItem>
                                </div>

                                <div class="form-actions">
                                    <NButton
                                        type="primary"
                                        size="large"
                                        attr-type="submit"
                                        :loading="profileForm.processing"
                                        class="save-button"
                                    >
                                        保存更改
                                    </NButton>
                                </div>
                            </NForm>
                        </div>
                    </NTabPane>

                    <!-- 修改密码 -->
                    <NTabPane name="password">
                        <template #tab>
                            <div class="tab-label">
                                <NIcon :component="LockClosedOutline" size="18" />
                                <span>修改密码</span>
                            </div>
                        </template>

                        <div class="tab-content">
                            <NAlert v-if="passwordForm.recentlySuccessful" type="success" class="mb-6" closable>
                                <template #icon>
                                    <NIcon :component="CheckmarkCircleOutline" />
                                </template>
                                密码已成功更新
                            </NAlert>

                            <NForm @submit.prevent="submitPassword" class="settings-form">
                                <div class="form-section">
                                    <h3 class="section-title">更改密码</h3>
                                    <p class="section-description">确保您的账户使用强密码以保持安全</p>
                                    
                                    <NFormItem label="当前密码" :validation-status="passwordForm.errors.current_password ? 'error' : undefined">
                                        <NInput
                                            v-model:value="passwordForm.current_password"
                                            :type="showCurrentPassword ? 'text' : 'password'"
                                            placeholder="请输入当前密码"
                                            size="large"
                                            :disabled="passwordForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="LockClosedOutline" color="#94a3b8" />
                                            </template>
                                            <template #suffix>
                                                <NIcon 
                                                    :component="showCurrentPassword ? EyeOffOutline : EyeOutline" 
                                                    class="password-toggle"
                                                    @click="showCurrentPassword = !showCurrentPassword"
                                                />
                                            </template>
                                        </NInput>
                                        <template #feedback>{{ passwordForm.errors.current_password }}</template>
                                    </NFormItem>

                                    <NFormItem label="新密码" :validation-status="passwordForm.errors.password ? 'error' : undefined">
                                        <NInput
                                            v-model:value="passwordForm.password"
                                            :type="showNewPassword ? 'text' : 'password'"
                                            placeholder="请输入新密码"
                                            size="large"
                                            :disabled="passwordForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="LockClosedOutline" color="#94a3b8" />
                                            </template>
                                            <template #suffix>
                                                <NIcon 
                                                    :component="showNewPassword ? EyeOffOutline : EyeOutline" 
                                                    class="password-toggle"
                                                    @click="showNewPassword = !showNewPassword"
                                                />
                                            </template>
                                        </NInput>
                                        <template #feedback>{{ passwordForm.errors.password }}</template>
                                    </NFormItem>

                                    <NFormItem label="确认新密码">
                                        <NInput
                                            v-model:value="passwordForm.password_confirmation"
                                            :type="showConfirmPassword ? 'text' : 'password'"
                                            placeholder="请再次输入新密码"
                                            size="large"
                                            :disabled="passwordForm.processing"
                                            class="custom-input"
                                        >
                                            <template #prefix>
                                                <NIcon :component="LockClosedOutline" color="#94a3b8" />
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
                                </div>

                                <div class="form-actions">
                                    <NButton
                                        type="primary"
                                        size="large"
                                        attr-type="submit"
                                        :loading="passwordForm.processing"
                                        class="save-button"
                                    >
                                        更新密码
                                    </NButton>
                                </div>
                            </NForm>
                        </div>
                    </NTabPane>

                    <!-- 订阅信息 -->
                    <NTabPane name="subscription">
                        <template #tab>
                            <div class="tab-label">
                                <NIcon :component="CardOutline" size="18" />
                                <span>订阅信息</span>
                            </div>
                        </template>

                        <div class="tab-content">
                            <div class="subscription-card" :class="subscriptionStatus">
                                <div class="subscription-header">
                                    <div class="subscription-icon">
                                        <NIcon :component="ShieldCheckmarkOutline" size="32" />
                                    </div>
                                    <div class="subscription-title">
                                        <h3>{{ user?.subscription_type || '免费版' }}</h3>
                                        <NTag 
                                            :type="subscriptionStatus === 'active' ? 'success' : subscriptionStatus === 'expired' ? 'error' : 'default'"
                                            size="small"
                                            round
                                        >
                                            {{ subscriptionStatus === 'active' ? '有效' : subscriptionStatus === 'expired' ? '已过期' : '未订阅' }}
                                        </NTag>
                                    </div>
                                </div>

                                <div v-if="subscriptionStatus === 'active'" class="subscription-progress">
                                    <div class="progress-info">
                                        <span>剩余时间</span>
                                        <span class="days-count">{{ daysRemaining }} 天</span>
                                    </div>
                                    <NProgress
                                        type="line"
                                        :percentage="Math.min(100, (daysRemaining / 30) * 100)"
                                        :color="daysRemaining > 7 ? '#10B981' : '#F59E0B'"
                                        :height="8"
                                        :show-indicator="false"
                                    />
                                </div>

                                <NDivider />

                                <div class="subscription-details">
                                    <div class="detail-item">
                                        <NIcon :component="CardOutline" color="#64748b" />
                                        <span class="detail-label">订阅类型</span>
                                        <span class="detail-value">{{ user?.subscription_type || '无' }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <NIcon :component="TimeOutline" color="#64748b" />
                                        <span class="detail-label">到期时间</span>
                                        <span class="detail-value">{{ user?.subscription_expires_at || '无' }}</span>
                                    </div>
                                </div>

                                <div class="subscription-actions">
                                    <NButton type="primary" size="large" class="upgrade-button">
                                        {{ subscriptionStatus === 'active' ? '续费订阅' : '升级订阅' }}
                                    </NButton>
                                </div>
                            </div>
                        </div>
                    </NTabPane>
                </NTabs>
            </div>
        </div>
    </AuthenticatedLayout>
</template>

<style scoped>
.settings-container {
    max-width: 800px;
}

/* 用户信息卡片 */
.user-card {
    display: flex;
    align-items: center;
    gap: 20px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 20px;
    padding: 28px 32px;
    margin-bottom: 24px;
    box-shadow: 0 8px 32px rgba(16, 185, 129, 0.25);
}

.user-avatar {
    width: 72px;
    height: 72px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 18px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 28px;
    font-weight: 700;
    color: white;
    border: 3px solid rgba(255, 255, 255, 0.3);
}

.user-info {
    flex: 1;
}

.user-name {
    font-size: 24px;
    font-weight: 700;
    color: white;
    margin: 0 0 4px;
}

.user-email {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
    margin: 0 0 12px;
}

.user-badges {
    display: flex;
    gap: 8px;
}

/* 设置卡片 */
.settings-card {
    background: white;
    border-radius: 20px;
    border: 1px solid #e2e8f0;
    overflow: hidden;
}

.settings-tabs {
    padding: 0;
}

.settings-tabs :deep(.n-tabs-nav) {
    padding: 0 24px;
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
}

.settings-tabs :deep(.n-tabs-tab) {
    padding: 16px 8px;
}

.tab-label {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 500;
}

.tab-content {
    padding: 32px;
}

/* 表单样式 */
.settings-form {
    max-width: 500px;
}

.form-section {
    margin-bottom: 32px;
}

.section-title {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 8px;
}

.section-description {
    font-size: 14px;
    color: #64748b;
    margin: 0 0 24px;
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

.password-toggle {
    color: #94a3b8;
    cursor: pointer;
    transition: color 0.2s;
}

.password-toggle:hover {
    color: #10B981;
}

.form-actions {
    padding-top: 8px;
}

.save-button {
    height: 48px;
    padding: 0 32px;
    border-radius: 12px;
    font-size: 15px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
    box-shadow: 0 4px 16px rgba(16, 185, 129, 0.3);
    transition: all 0.3s ease;
}

.save-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.35);
}

/* 订阅卡片 */
.subscription-card {
    background: #f8fafc;
    border-radius: 16px;
    padding: 28px;
    border: 1px solid #e2e8f0;
}

.subscription-card.active {
    background: linear-gradient(135deg, rgba(16, 185, 129, 0.05) 0%, rgba(5, 150, 105, 0.05) 100%);
    border-color: rgba(16, 185, 129, 0.2);
}

.subscription-header {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 24px;
}

.subscription-icon {
    width: 56px;
    height: 56px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
}

.subscription-title h3 {
    font-size: 20px;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 6px;
}

.subscription-progress {
    margin-bottom: 8px;
}

.progress-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    font-size: 14px;
    color: #64748b;
}

.days-count {
    font-weight: 600;
    color: #10B981;
}

.subscription-details {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.detail-item {
    display: flex;
    align-items: center;
    gap: 12px;
}

.detail-label {
    font-size: 14px;
    color: #64748b;
    flex: 1;
}

.detail-value {
    font-size: 14px;
    font-weight: 600;
    color: #1e293b;
}

.subscription-actions {
    margin-top: 24px;
}

.upgrade-button {
    width: 100%;
    height: 48px;
    border-radius: 12px;
    font-size: 15px;
    font-weight: 600;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border: none;
}

/* 响应式 */
@media (max-width: 640px) {
    .user-card {
        flex-direction: column;
        text-align: center;
        padding: 24px;
    }
    
    .user-badges {
        justify-content: center;
    }
    
    .tab-content {
        padding: 24px 20px;
    }
    
    .settings-tabs :deep(.n-tabs-nav) {
        padding: 0 16px;
    }
}
</style>
