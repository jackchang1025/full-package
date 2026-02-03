<script setup lang="ts">
import { computed } from 'vue';
import { Head, useForm, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NDatePicker, NSelect, NSpace } from 'naive-ui';
import AdminLayout from '@/Layouts/AdminLayout.vue';

interface UserForm {
    id: number;
    username: string;
    email: string;
    subscription_expires_at: string | null;
    subscription_type: string | null;
    roles: string[];
    direct_permissions: string[];
    contact: string | null;
}

const props = defineProps<{
    user: UserForm;
    roles: string[];
    roleLabels?: Record<string, string>;
    permissions?: string[];
    permissionLabels?: Record<string, string>;
}>();

const form = useForm({
    subscription_expires_at: props.user.subscription_expires_at || null,
    roles: props.user.roles ?? [],
    direct_permissions: props.user.direct_permissions ?? [],
});

const roleOptions = computed(() => {
    const labels = props.roleLabels ?? {};
    return (props.roles ?? []).map((r) => ({ label: labels[r] ?? r, value: r }));
});

const permissionOptions = computed(() => {
    const labels = props.permissionLabels ?? {};
    return (props.permissions ?? []).map((p) => ({ label: labels[p] ?? p, value: p }));
});

const submit = () => {
    form.put(`/admin/users/${props.user.id}`, { onSuccess: () => router.visit('/admin/users') });
};
</script>

<template>
    <Head title="编辑用户" />
    <AdminLayout>
        <template #header-title>编辑用户</template>
        <div class="admin-form-page">
            <NCard title="基本信息" class="admin-form-card">
                <p><strong>用户名</strong> {{ user.username }}</p>
                <p><strong>邮箱</strong> {{ user.email }}</p>
            </NCard>
            <NCard title="到期与权限" class="admin-form-card">
                <NForm @submit.prevent="submit">
                    <NFormItem label="到期时间">
                        <NDatePicker
                            v-model:formatted-value="form.subscription_expires_at"
                            type="date"
                            value-format="yyyy-MM-dd"
                            clearable
                            style="width: 100%"
                        />
                    </NFormItem>
                    <NFormItem label="角色">
                        <NSelect v-model:value="form.roles" :options="roleOptions" multiple placeholder="选择角色" />
                    </NFormItem>
                    <NFormItem label="单独权限">
                        <NSelect
                            v-model:value="form.direct_permissions"
                            :options="permissionOptions"
                            multiple
                            placeholder="可为该用户单独赋予或收回权限（在角色权限之外）"
                            clearable
                            style="width: 100%"
                        />
                        <template #feedback>
                            <span class="form-hint">单独权限与角色权限叠加生效；此处仅管理直接赋予该用户的权限。</span>
                        </template>
                    </NFormItem>
                    <NFormItem>
                        <NSpace>
                            <NButton type="primary" attr-type="submit" :loading="form.processing">保存</NButton>
                            <NButton @click="router.visit('/admin/users')">取消</NButton>
                        </NSpace>
                    </NFormItem>
                </NForm>
            </NCard>
        </div>
    </AdminLayout>
</template>

<style scoped>
.admin-form-page { max-width: 600px; }
.admin-form-card {
    margin-bottom: 24px;
    border-radius: var(--admin-radius-lg, 16px);
    border: 1px solid var(--admin-border, rgba(0,0,0,.06));
    box-shadow: var(--admin-shadow, 0 1px 3px rgba(0,0,0,.05));
}
.admin-form-card:last-child { margin-bottom: 0; }
.form-hint { font-size: 12px; color: var(--admin-text-muted, #6b7280); }
</style>
