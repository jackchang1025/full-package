<script setup lang="ts">
import { computed } from 'vue';
import { Head } from '@inertiajs/vue3';
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import BuildListContent from '@/Components/BuildList/BuildListContent.vue';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

const props = defineProps<{
    builds: { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number };
}>();

const { userRoute } = useAdminBasePath();
const buildsBasePath = computed(() => userRoute('/builds'));
</script>

<template>
    <Head title="APK 构建" />
    <AuthenticatedLayout>
        <template #header-title>APK 构建</template>
        <BuildListContent
            :builds="(props.builds as { data: unknown[]; current_page: number; last_page: number; per_page?: number; total?: number })"
            :base-path="buildsBasePath"
            page-title="我的应用"
            :show-user-column="false"
            :allow-create="true"
            :allow-share="true"
            :allow-download="true"
            :allow-delete="true"
        />
    </AuthenticatedLayout>
</template>
