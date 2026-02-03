<script setup lang="ts">
import { Head } from '@inertiajs/vue3';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import BuildListContent from '@/Components/BuildList/BuildListContent.vue';

const props = defineProps<{
    builds: { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number };
}>();
</script>

<template>
    <Head title="APK 构建" />
    <AuthenticatedLayout>
        <template #header-title>APK 构建</template>
        <BuildListContent
            :builds="(props.builds as { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number })"
            base-path="/builds"
            page-title="我的应用"
            :show-user-column="false"
            :allow-create="true"
            :allow-share="true"
            :allow-download="true"
            :allow-delete="true"
        />
    </AuthenticatedLayout>
</template>
