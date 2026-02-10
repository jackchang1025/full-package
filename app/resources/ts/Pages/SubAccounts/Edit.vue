<script setup lang="ts">
import { computed } from 'vue';
import { Head, useForm, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NSelect, NSpace } from 'naive-ui';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

interface SubAccountData {
    id: number;
    username: string;
    email: string;
    permissions: string[];
}

const props = defineProps<{
    subAccount: SubAccountData;
    availablePermissions: string[];
    permissionLabels?: Record<string, string>;
}>();

const { userRoute } = useAdminBasePath();

const form = useForm({
    username: props.subAccount.username,
    email: props.subAccount.email,
    password: '',
    password_confirmation: '',
    permissions: props.subAccount.permissions ?? [],
});

const permissionOptions = computed(() => {
    const labels = props.permissionLabels ?? {};
    return props.availablePermissions.map((p) => ({ label: labels[p] ?? p, value: p }));
});

const submit = () => {
    form.put(userRoute(`/sub-accounts/${props.subAccount.id}`), {
        onSuccess: () => router.visit(userRoute('/sub-accounts')),
    });
};
</script>

<template>
    <Head title="编辑子账号" />
    <AuthenticatedLayout>
        <template #header-title>编辑子账号</template>
        <div class="form-page">
            <NForm @submit.prevent="submit">
                <NCard title="账号信息" class="form-card">
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
                    <NFormItem label="新密码" :validation-status="form.errors.password ? 'error' : undefined" :feedback="form.errors.password || '留空则不修改'">
                        <NInput
                            v-model:value="form.password"
                            type="password"
                            placeholder="留空则不修改"
                            show-password-on="click"
                            :disabled="form.processing"
                        />
                    </NFormItem>
                    <NFormItem label="确认新密码" :validation-status="form.errors.password_confirmation ? 'error' : undefined" :feedback="form.errors.password_confirmation">
                        <NInput
                            v-model:value="form.password_confirmation"
                            type="password"
                            placeholder="留空则不修改"
                            show-password-on="click"
                            :disabled="form.processing"
                        />
                    </NFormItem>
                </NCard>
                <NCard title="权限分配" class="form-card">
                    <NFormItem label="权限">
                        <NSelect
                            v-model:value="form.permissions"
                            :options="permissionOptions"
                            multiple
                            placeholder="选择该子账号可使用的功能"
                            clearable
                            style="width: 100%"
                        />
                        <template #feedback>
                            <span class="form-hint">仅可分配您自身拥有的权限。</span>
                        </template>
                    </NFormItem>
                    <NFormItem>
                        <NSpace>
                            <NButton type="primary" attr-type="submit" :loading="form.processing">保存</NButton>
                            <NButton @click="router.visit(userRoute('/sub-accounts'))">取消</NButton>
                        </NSpace>
                    </NFormItem>
                </NCard>
            </NForm>
        </div>
    </AuthenticatedLayout>
</template>

<style scoped>
.form-page { max-width: 600px; }
.form-card {
    margin-bottom: 24px;
    border-radius: var(--user-radius-lg, 16px);
    border: 1px solid var(--user-border, #e2e8f0);
    box-shadow: var(--user-shadow, 0 1px 3px rgba(0,0,0,.05));
}
.form-card:last-child { margin-bottom: 0; }
.form-hint { font-size: 12px; color: var(--user-text-muted, #64748b); }
</style>
