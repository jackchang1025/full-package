<script setup lang="ts">
import { ref } from 'vue';
import {
    NCard,
    NSpace,
    NButton,
    NPopconfirm,
    NInput,
    NModal,
    NForm,
    NFormItem,
    useMessage,
} from 'naive-ui';

interface Emits {
    (e: 'rename', name: string): void;
    (e: 'hideIcon'): void;
    (e: 'delete'): void;
    (e: 'requestPermissions'): void;
}

const emit = defineEmits<Emits>();
const message = useMessage();

const showRenameModal = ref(false);
const newName = ref('');

const handleRename = () => {
    if (!newName.value.trim()) {
        message.warning('请输入设备名称');
        return;
    }
    emit('rename', newName.value.trim());
    showRenameModal.value = false;
    newName.value = '';
};
</script>

<template>
    <NCard title="设备操作" size="small">
        <NSpace vertical>
            <NButton block @click="showRenameModal = true">
                重命名设备
            </NButton>

            <NButton block @click="emit('requestPermissions')">
                请求权限
            </NButton>

            <NPopconfirm @positive-click="emit('hideIcon')">
                <template #trigger>
                    <NButton block type="warning">
                        隐藏图标
                    </NButton>
                </template>
                确定要隐藏设备上的应用图标吗？
            </NPopconfirm>

            <NPopconfirm @positive-click="emit('delete')">
                <template #trigger>
                    <NButton block type="error">
                        移除设备
                    </NButton>
                </template>
                确定要移除此设备吗？此操作不可撤销。
            </NPopconfirm>
        </NSpace>

        <NModal
            v-model:show="showRenameModal"
            title="重命名设备"
            preset="dialog"
            positive-text="确定"
            negative-text="取消"
            @positive-click="handleRename"
        >
            <NForm :model="{ name: newName }" label-placement="left">
                <NFormItem label="新名称">
                    <NInput
                        v-model:value="newName"
                        placeholder="输入新的设备名称"
                        @keyup.enter="handleRename"
                    />
                </NFormItem>
            </NForm>
        </NModal>
    </NCard>
</template>
