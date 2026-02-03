<script setup lang="ts">
import { computed } from 'vue';
import { Head, useForm, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NSelect, NSpace } from 'naive-ui';
import AdminLayout from '@/Layouts/AdminLayout.vue';

const props = defineProps<{
    permissions: string[];
    permissionLabels?: Record<string, string>;
}>();

const form = useForm({
    name: '',
    permissions: [] as string[],
});

const permissionOptions = computed(() => {
    const labels = props.permissionLabels ?? {};
    return props.permissions.map((p) => ({ label: labels[p] ?? p, value: p }));
});

const submit = () => {
    form.post('/admin/roles', {
        onSuccess: () => router.visit('/admin/roles'),
    });
};
</script>

<template>
    <Head title="新建角色" />
    <AdminLayout>
        <template #header-title>新建角色</template>
        <div class="admin-form-page">
            <NCard title="角色信息" class="admin-form-card">
                <NForm @submit.prevent="submit">
                    <NFormItem label="角色名称" required>
                        <NInput
                            v-model:value="form.name"
                            placeholder="如：vip、trial"
                            :disabled="form.processing"
                            clearable
                        />
                        <template v-if="form.errors.name" #feedback>
                            {{ form.errors.name }}
                        </template>
                    </NFormItem>
                    <NFormItem label="权限">
                        <NSelect
                            v-model:value="form.permissions"
                            :options="permissionOptions"
                            multiple
                            placeholder="选择该角色拥有的权限"
                            :disabled="form.processing"
                            style="width: 100%"
                        />
                    </NFormItem>
                    <NFormItem>
                        <NSpace>
                            <NButton type="primary" attr-type="submit" :loading="form.processing">创建</NButton>
                            <NButton @click="router.visit('/admin/roles')">取消</NButton>
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
    border-radius: var(--admin-radius-lg, 16px);
    border: 1px solid var(--admin-border, rgba(0,0,0,.06));
    box-shadow: var(--admin-shadow, 0 1px 3px rgba(0,0,0,.05));
}
</style>
