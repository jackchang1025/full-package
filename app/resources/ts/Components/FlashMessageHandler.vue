<script setup lang="ts">
import { useFlashMessage } from '@/composables/useFlashMessage';

useFlashMessage();
</script>

<template>
    <slot />
</template>
