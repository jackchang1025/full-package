<script setup lang="ts">
import {
    NDataTable,
    NButton,
    NEmpty,
    NSpin,
    NInput,
} from 'naive-ui';
import { ref, computed } from 'vue';
import type { Contact } from '@/types/device';

interface Props {
    contacts: Contact[];
    loading: boolean;
}

interface Emits {
    (e: 'refresh'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const searchQuery = ref('');

const filteredContacts = computed(() => {
    if (!searchQuery.value.trim()) return props.contacts;
    const query = searchQuery.value.toLowerCase();
    return props.contacts.filter(
        c => c.name.toLowerCase().includes(query) || c.number.includes(query)
    );
});

const columns = [
    { title: '姓名', key: 'name' },
    { title: '号码', key: 'number' },
];
</script>

<template>
    <div class="contacts-tab">
        <div class="tab-header">
            <NInput
                v-model:value="searchQuery"
                placeholder="搜索联系人..."
                size="small"
                clearable
                style="width: 200px"
            />
            <NButton size="small" @click="emit('refresh')">
                刷新
            </NButton>
        </div>

        <NSpin :show="loading">
            <NDataTable
                v-if="filteredContacts.length > 0"
                :columns="columns"
                :data="filteredContacts"
                :bordered="false"
                :max-height="400"
                size="small"
            />
            <NEmpty v-else description="暂无联系人" />
        </NSpin>
    </div>
</template>

<style scoped>
.contacts-tab {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.tab-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}
</style>
