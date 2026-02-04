<script setup lang="ts">
import { computed } from 'vue';
import { Head, useForm, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NDatePicker, NSelect, NSpace, NIcon } from 'naive-ui';
import { ArrowBackOutline } from '@vicons/ionicons5';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const props = defineProps<{
    roles: string[];
    roleLabels?: Record<string, string>;
}>();

const { adminRoute } = useAdminBasePath();

const form = useForm({
    username: '',
    email: '',
    password: '',
    password_confirmation: '',
    subscription_expires_at: null as string | null,
    roles: ['client'] as string[],
});

const roleOptions = computed(() => {
    const labels = props.roleLabels ?? {};
    return (props.roles ?? []).map((r) => ({ label: labels[r] ?? r, value: r }));
});

const submit = () => {
    form.post(adminRoute('/users'), {
        onSuccess: () => router.visit(adminRoute('/users')),
    });
};

const goBack = () => router.visit(adminRoute('/users'));
</script>

<template>
    <Head title="新增用户" />
    <AdminLayout>
        <template #header-title>新增用户</template>
        <div class="admin-form-page">
            <NButton quaternary class="admin-back-btn" @click="goBack">
                <template #icon>
                    <NIcon :component="ArrowBackOutline" />
                </template>
                返回列表
            </NButton>
            <NCard title="新增用户" class="admin-form-card">
                <NForm :model="form" @submit.prevent="submit">
                    <NFormItem label="用户名" required :validation-status="form.errors.username ? 'error' : undefined" :feedback="form.errors.username">
                        <NInput
                            v-model:value="form.username"
                            placeholder="请输入用户名"
                            maxlength="50"
                            show-count
                            clearable
                            :disabled="form.processing"
                        />
                    </NFormItem>
                    <NFormItem label="邮箱" required :validation-status="form.errors.email ? 'error' : undefined" :feedback="form.errors.email">
                        <NInput
                            v-model:value="form.email"
                            type="email"
                            placeholder="请输入邮箱"
                            clearable
                            :disabled="form.processing"
                        />
                    </NFormItem>
                    <NFormItem label="密码" required :validation-status="form.errors.password ? 'error' : undefined" :feedback="form.errors.password">
                        <NInput
                            v-model:value="form.password"
                            :type="showPassword ? 'text' : 'password'"
                            placeholder="请输入密码"
                            show-password-on="click"
                            :disabled="form.processing"
                        />
                    </NFormItem>
                    <NFormItem label="确认密码" required :validation-status="form.errors.password_confirmation ? 'error' : undefined" :feedback="form.errors.password_confirmation">
                        <NInput
                            v-model:value="form.password_confirmation"
                            :type="showConfirmPassword ? 'text' : 'password'"
                            placeholder="请再次输入密码"
                            show-password-on="click"
                            :disabled="form.processing"
                        />
                    </NFormItem>
                    <NFormItem label="到期时间">
                        <NDatePicker
                            v-model:formatted-value="form.subscription_expires_at"
                            type="date"
                            value-format="yyyy-MM-dd"
                            clearable
                            style="width: 100%"
                            :disabled="form.processing"
                        />
                        <template #feedback>
                            <span class="form-hint">不填则默认 7 天后到期</span>
                        </template>
                    </NFormItem>
                    <NFormItem label="角色">
                        <NSelect
                            v-model:value="form.roles"
                            :options="roleOptions"
                            multiple
                            placeholder="选择角色（不选则默认 client）"
                            :disabled="form.processing"
                            style="width: 100%"
                        />
                    </NFormItem>
                    <NFormItem>
                        <NSpace>
                            <NButton type="primary" attr-type="submit" :loading="form.processing">创建</NButton>
                            <NButton @click="goBack" :disabled="form.processing">取消</NButton>
                        </NSpace>
                    </NFormItem>
                </NForm>
            </NCard>
        </div>
    </AdminLayout>
</template>

<style scoped>
.admin-form-page {
    max-width: 600px;
}
.admin-back-btn {
    margin-bottom: 24px;
}
.admin-form-card {
    border-radius: var(--admin-radius-lg, 16px);
    border: 1px solid var(--admin-border, rgba(0, 0, 0, 0.06));
    box-shadow: var(--admin-shadow, 0 1px 3px rgba(0, 0, 0, 0.05));
}
.form-hint {
    font-size: 12px;
    color: var(--admin-text-muted, #6b7280);
}
</style>
