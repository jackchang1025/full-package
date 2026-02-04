<script setup lang="ts">
import { computed } from 'vue';
import { Head } from '@inertiajs/vue3';
import AdminLayout from '@/Layouts/AdminLayout.vue';
import BuildListContent from '@/Components/BuildList/BuildListContent.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const props = defineProps<{
    builds: { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number };
    filters?: { search?: string };
}>();

const { adminRoute } = useAdminBasePath();
const buildsBasePath = computed(() => adminRoute('/builds'));
</script>

<template>
    <Head title="APK 构建管理" />
    <AdminLayout>
        <template #header-title>APK 构建管理</template>
        <BuildListContent
            :builds="(props.builds as { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number })"
            :base-path="buildsBasePath"
            page-title="APK 构建管理"
            :show-user-column="true"
            :allow-create="false"
            :allow-share="true"
            :allow-download="true"
            :allow-delete="true"
            :filters="props.filters ?? {}"
        />
    </AdminLayout>
</template>
