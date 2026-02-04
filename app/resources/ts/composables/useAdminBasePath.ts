import { computed, type ComputedRef } from 'vue';
import { usePage } from '@inertiajs/vue3';

interface SharedProps {
    adminBasePath?: string;
    userBasePath?: string;
}

interface UseAdminBasePathReturn {
    /** The admin base path (e.g., 'admin' or 'manage') */
    adminBasePath: ComputedRef<string>;
    /** The full admin base URL (e.g., '/admin' or '/manage') */
    adminBaseUrl: ComputedRef<string>;
    /** The user base path (e.g., '' or 'portal') */
    userBasePath: ComputedRef<string>;
    /** The full user base URL (e.g., '' or '/portal') */
    userBaseUrl: ComputedRef<string>;
    /** Build an admin route URL */
    adminRoute: (path: string) => string;
    /** Build a user route URL */
    userRoute: (path: string) => string;
    /** Check if a given URL belongs to admin routes */
    isAdminUrl: (url: string) => boolean;
}

/**
 * Composable for accessing dynamic base paths configured in admin settings.
 *
 * Provides consistent access to admin and user base paths across all components,
 * eliminating the need to repeat `usePage().props.adminBasePath` everywhere.
 *
 * @example
 * ```vue
 * <script setup lang="ts">
 * import { useAdminBasePath } from '@/composables/useAdminBasePath';
 *
 * const { adminBaseUrl, adminRoute } = useAdminBasePath();
 *
 * // Navigate to admin users
 * router.visit(adminRoute('/users'));
 *
 * // Or use the computed URL directly
 * router.visit(`${adminBaseUrl.value}/users`);
 * </script>
 * ```
 */
export function useAdminBasePath(): UseAdminBasePathReturn {
    const page = usePage();
    const props = computed(() => page.props as SharedProps);

    const adminBasePath = computed(() => props.value.adminBasePath ?? 'admin');
    const userBasePath = computed(() => props.value.userBasePath ?? '');

    const adminBaseUrl = computed(() => (adminBasePath.value ? `/${adminBasePath.value}` : ''));
    const userBaseUrl = computed(() => (userBasePath.value ? `/${userBasePath.value}` : ''));

    const adminRoute = (path: string): string => {
        const normalizedPath = path.startsWith('/') ? path : `/${path}`;
        return `${adminBaseUrl.value}${normalizedPath}`;
    };

    const userRoute = (path: string): string => {
        const normalizedPath = path.startsWith('/') ? path : `/${path}`;
        return userBaseUrl.value ? `${userBaseUrl.value}${normalizedPath}` : normalizedPath;
    };

    const isAdminUrl = (url: string): boolean => {
        const base = adminBaseUrl.value;
        return base !== '' && url.startsWith(`${base}/`);
    };

    return {
        adminBasePath,
        adminBaseUrl,
        userBasePath,
        userBaseUrl,
        adminRoute,
        userRoute,
        isAdminUrl,
    };
}
