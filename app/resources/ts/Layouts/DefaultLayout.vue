<script setup lang="ts">
import {
    NConfigProvider,
    NMessageProvider,
    NDialogProvider,
    NNotificationProvider,
    NLoadingBarProvider,
    zhCN,
    dateZhCN,
    darkTheme,
} from 'naive-ui';
import { ref, computed } from 'vue';
import FlashMessageHandler from '@/Components/FlashMessageHandler.vue';

const isDark = ref(false);
const theme = computed(() => (isDark.value ? darkTheme : null));
</script>

<template>
    <NConfigProvider :theme="theme" :locale="zhCN" :date-locale="dateZhCN">
        <NLoadingBarProvider>
            <NDialogProvider>
                <NNotificationProvider>
                    <NMessageProvider>
                        <FlashMessageHandler>
                            <slot />
                        </FlashMessageHandler>
                    </NMessageProvider>
                </NNotificationProvider>
            </NDialogProvider>
        </NLoadingBarProvider>
    </NConfigProvider>
</template>
