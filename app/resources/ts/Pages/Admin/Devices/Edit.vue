<script setup lang="ts">
import { Head, useForm, router } from '@inertiajs/vue3';
import { NCard, NForm, NFormItem, NInput, NButton, NSpace } from 'naive-ui';
import AdminLayout from '@/Layouts/AdminLayout.vue';

const props = defineProps<{
    device: {
        uuid: string;
        name: string;
        remark: string | null;
        user: { id: number; username: string; email: string } | null;
    };
}>();

const form = useForm({ remark: props.device.remark ?? '' });

const submit = () => {
    form.put(`/admin/devices/${props.device.uuid}`, { onSuccess: () => router.visit('/admin/devices') });
};
</script>

<template>
    <Head title="编辑设备" />
    <AdminLayout>
        <template #header-title>编辑设备</template>
        <div class="admin-form-page">
            <NCard title="设备信息" class="admin-form-card">
                <p><strong>UUID</strong> {{ device.uuid }}</p>
                <p><strong>名称</strong> {{ device.name || '-' }}</p>
                <p v-if="device.user"><strong>所属用户</strong> {{ device.user.username }} ({{ device.user.email }})</p>
            </NCard>
            <NCard title="备注" class="admin-form-card">
                <NForm @submit.prevent="submit">
                    <NFormItem label="备注">
                        <NInput v-model:value="form.remark" type="textarea" placeholder="备注" :rows="3" />
                    </NFormItem>
                    <NFormItem>
                        <NSpace>
                            <NButton type="primary" attr-type="submit" :loading="form.processing">保存</NButton>
                            <NButton @click="router.visit('/admin/devices')">取消</NButton>
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
</style>
