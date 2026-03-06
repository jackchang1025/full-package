import { watch } from 'vue';
import { usePage } from '@inertiajs/vue3';
import { useMessage } from 'naive-ui';

export function useFlashMessage() {
    const page = usePage();
    const message = useMessage();

    watch(
        () => (page.props.flash as { success?: string; error?: string }),
        (flash) => {
            if (flash?.success) {
                message.success(flash.success);
            }
            if (flash?.error) {
                message.error(flash.error);
            }
        },
        { immediate: true },
    );
}
