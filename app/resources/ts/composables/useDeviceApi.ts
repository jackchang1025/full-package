import type { AxiosError } from 'axios';
import type { MessageApi } from 'naive-ui';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

export interface DeviceApiResult {
    success: boolean;
    status: number;
    data: Record<string, unknown> | null;
    error: string | null;
}

export type DeviceApiMethod = 'GET' | 'POST';

export interface DeviceApiCallOptions {
    query?: Record<string, string | number | boolean>;
    body?: Record<string, unknown>;
    toastOnError?: boolean;
    successMessage?: string;
}

export function useDeviceApi(deviceUuid: string, message: MessageApi) {
    const { userRoute } = useAdminBasePath();

    async function call(
        method: DeviceApiMethod,
        path: string,
        options: DeviceApiCallOptions = {},
    ): Promise<DeviceApiResult> {
        const url = userRoute(`/devices/${deviceUuid}/api-proxy`);

        const query: Record<string, string> = {};
        if (options.query) {
            for (const [key, value] of Object.entries(options.query)) {
                query[key] = String(value);
            }
        }

        try {
            const { data: result } = await window.axios.post<DeviceApiResult>(url, {
                method,
                path,
                query: Object.keys(query).length > 0 ? query : undefined,
                body: options.body,
            });

            if (!result.success) {
                if (options.toastOnError !== false) {
                    message.error(result.error ?? `设备 API 调用失败 (status=${result.status})`);
                }
                return result;
            }

            if (options.successMessage) {
                message.success(options.successMessage);
            }
            return result;
        } catch (err) {
            const axiosErr = err as AxiosError<{ message?: string; error?: string }>;
            const laravelErr = axiosErr.response?.data?.error
                ?? axiosErr.response?.data?.message;
            const errorMsg = laravelErr ?? axiosErr.message ?? '网络错误';

            if (options.toastOnError !== false) {
                message.error(`设备命令失败: ${errorMsg}`);
            }
            return {
                success: false,
                status: axiosErr.response?.status ?? 0,
                data: null,
                error: errorMsg,
            };
        }
    }

    return { call };
}
