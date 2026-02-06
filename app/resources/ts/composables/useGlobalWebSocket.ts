import { ref, type Ref } from 'vue';
import type {
    ConnectionState,
    WebSocketOutboundMessage,
    WebSocketInboundMessage,
    DeviceOnlineMessage,
    DeviceOfflineMessage,
} from '@/types/websocket';

const WEBSOCKET_URL = import.meta.env.WEBSOCKET_URL || 'ws://localhost:8081';
const RECONNECT_BASE_DELAY = 1000;
const RECONNECT_MAX_DELAY = 30000;
const HEARTBEAT_INTERVAL = 30000; // 30 秒发送一次心跳

let socket: WebSocket | null = null;
let reconnectTimeout: ReturnType<typeof setTimeout> | null = null;
let heartbeatTimer: ReturnType<typeof setInterval> | null = null;
let reconnectAttempts = 0;
let isInitialized = false;

// 连接状态
const connectionState: Ref<ConnectionState> = ref('disconnected');
const lastError: Ref<string | null> = ref(null);

// 统计数据（由 WebSocket 推送更新）
// [core-dry] 统一数据源，避免 HTTP 和 WebSocket 数据不一致
const stats: Ref<{ total: number; online: number; offline: number }> = ref({
    total: 0,
    online: 0,
    offline: 0,
});

// 设备列表（subscribe 时返回全量，之后增量更新）
// [solid-srp] 设备数据与统计数据分离管理
export interface WsDeviceRow {
    id: number;
    uuid: string;
    name: string;
    remark: string | null;
    model: string;
    android_version?: string;
    country?: string;
    ip_address?: string | null;
    ip_location?: string | null;
    network_type?: string | null;
    battery_level?: number | null;
    battery_is_charging?: boolean;
    is_online: boolean;
    has_accessibility?: boolean;
    last_seen_at: string | null;
    installed_at?: string | null;
    user?: { id: number; username: string; email: string } | null;
    wallpap?: string | null;
    screen_status?: string;
}

const devices: Ref<WsDeviceRow[]> = ref([]);

// 标记是否已收到 WebSocket 推送的数据（用于判断数据源）
// [core-kiss] 简单标志位解决数据源切换问题
const hasReceivedWsData: Ref<boolean> = ref(false);

// 消息处理器
const messageHandlers: Array<(msg: WebSocketInboundMessage) => void> = [];

let userEmail: string = '';

const clearReconnectTimer = () => {
    if (reconnectTimeout) {
        clearTimeout(reconnectTimeout);
        reconnectTimeout = null;
    }
};

const startHeartbeat = () => {
    stopHeartbeat();
    heartbeatTimer = setInterval(() => {
        if (socket?.readyState === WebSocket.OPEN) {
            socket.send(JSON.stringify({ subc: 'ping' }));
        }
    }, HEARTBEAT_INTERVAL);
};

const stopHeartbeat = () => {
    if (heartbeatTimer) {
        clearInterval(heartbeatTimer);
        heartbeatTimer = null;
    }
};

// 发送订阅请求
const sendSubscribe = () => {
    if (socket?.readyState === WebSocket.OPEN && userEmail) {
        socket.send(JSON.stringify({
            subc: 'subscribe',
            email: userEmail,
        }));
    }
};

const scheduleReconnect = () => {
    if (!userEmail) return;

    const delay = Math.min(
        RECONNECT_BASE_DELAY * Math.pow(2, reconnectAttempts),
        RECONNECT_MAX_DELAY
    );
    reconnectAttempts++;
    connectionState.value = 'reconnecting';

    reconnectTimeout = setTimeout(() => {
        if (userEmail) {
            connectGlobal(userEmail);
        }
    }, delay);
};

const handleMessage = (event: MessageEvent) => {
    try {
        const data = JSON.parse(event.data) as WebSocketInboundMessage;
        
        // 处理 stats 更新
        const msgWithStats = data as { stats?: { total: number; online: number; offline: number } };
        if (msgWithStats.stats) {
            stats.value = msgWithStats.stats;
            hasReceivedWsData.value = true;
        }

        // 处理 subscribe 响应中的设备列表
        // [core-dry] 统一处理初始设备列表
        const subscribeMsg = data as { type?: string; devices?: WsDeviceRow[] };
        if (subscribeMsg.type === 'subscribe' && Array.isArray(subscribeMsg.devices)) {
            devices.value = subscribeMsg.devices;
            hasReceivedWsData.value = true;
        }

        // 处理设备上线通知 - 增量更新（phoneInfo 与 deviceUpdate 同构，便于复用逻辑）
        if (data.type === 'deviceOnline') {
            const onlineMsg = data as DeviceOnlineMessage;
            updateDeviceOnline(onlineMsg.pid, onlineMsg.phoneInfo as unknown as Record<string, unknown> | null);
        }

        // 处理设备下线通知 - 增量更新
        if (data.type === 'deviceOffline') {
            const offlineMsg = data as DeviceOfflineMessage;
            updateDeviceOffline(offlineMsg.pid);
        }

        // 处理设备状态更新
        const updateMsg = data as { type?: string; pid?: string; phoneInfo?: Record<string, unknown> };
        if (updateMsg.type === 'deviceUpdate' && updateMsg.pid && updateMsg.phoneInfo) {
            updateDeviceStatus(updateMsg.pid, updateMsg.phoneInfo);
        }

        messageHandlers.forEach(handler => handler(data));
    } catch {
        lastError.value = '消息解析失败';
    }
};

/**
 * 从 phoneInfo 构建列表行增量字段（与 deviceUpdate 同构，复用同一套字段映射）
 */
const applyPhoneInfoToRow = (phoneInfo: Record<string, unknown>): Partial<WsDeviceRow> => {
    const updates: Partial<WsDeviceRow> = {
        is_online: phoneInfo.is_online !== false,
    };
    if (phoneInfo.phone_name != null && String(phoneInfo.phone_name).trim()) {
        updates.name = String(phoneInfo.phone_name).trim();
    }
    if (phoneInfo.model != null && String(phoneInfo.model).trim()) {
        updates.model = String(phoneInfo.model).trim();
    }
    if (phoneInfo.battery_charge != null) {
        updates.battery_level = parseBatteryLevel(phoneInfo.battery_charge as string);
        updates.battery_is_charging = parseBatteryCharging(phoneInfo.battery_charge as string);
    }
    if (phoneInfo.lastPing != null) {
        const ts = typeof phoneInfo.lastPing === 'number'
            ? phoneInfo.lastPing
            : parseInt(String(phoneInfo.lastPing), 10);
        if (!isNaN(ts)) {
            updates.last_seen_at = new Date(ts).toISOString();
        }
    }
    if (phoneInfo.accessibility != null) {
        updates.has_accessibility = phoneInfo.accessibility === '1';
    }
    if (phoneInfo.ip != null) {
        updates.ip_address = String(phoneInfo.ip);
    }
    if (phoneInfo.ip_location != null) {
        updates.ip_location = String(phoneInfo.ip_location);
    }
    if (phoneInfo.network != null) {
        updates.network_type = String(phoneInfo.network);
    }
    if (phoneInfo.wallpap != null) {
        updates.wallpap = String(phoneInfo.wallpap);
    }
    if (phoneInfo.activz != null) {
        updates.screen_status = String(phoneInfo.activz);
    }
    return updates;
};

/** 设备 ID 统一转字符串再比较，避免后端返回数字时超出 Number.MAX_SAFE_INTEGER 导致精度丢失、查找失败 */
const sameDeviceId = (a: string | number | undefined, b: string | number | undefined): boolean =>
    String(a ?? '') === String(b ?? '');

/**
 * 设备上线时更新列表（使用与 deviceUpdate 一致的 phoneInfo 结构）
 */
const updateDeviceOnline = (pid: string, phoneInfo: Record<string, unknown> | null) => {
    const index = devices.value.findIndex(d => sameDeviceId(d.uuid, pid));

    if (index >= 0) {
        const updates = phoneInfo ? applyPhoneInfoToRow(phoneInfo) : { is_online: true };
        devices.value[index] = { ...devices.value[index], ...updates, is_online: true };
    } else if (phoneInfo) {
        const base = applyPhoneInfoToRow(phoneInfo);
        const newDevice: WsDeviceRow = {
            id: 0,
            uuid: pid,
            name: (base.name as string) || (phoneInfo.phone_name as string) || '新设备',
            remark: null,
            model: (base.model as string) || (phoneInfo.model as string) || '未知型号',
            android_version: (phoneInfo.android_version as string)?.replace?.('Android ', ''),
            country: (phoneInfo.country as string) ?? '',
            ip_address: base.ip_address ?? (phoneInfo.ip as string),
            ip_location: base.ip_location ?? (phoneInfo.ip_location as string),
            battery_level: base.battery_level ?? parseBatteryLevel(phoneInfo.battery_charge as string),
            battery_is_charging: base.battery_is_charging ?? parseBatteryCharging(phoneInfo.battery_charge as string),
            is_online: true,
            has_accessibility: base.has_accessibility ?? (phoneInfo.accessibility === '1'),
            last_seen_at: base.last_seen_at ?? new Date().toISOString(),
            installed_at: (phoneInfo.install_date as string) ?? undefined,
            user: null,
        };
        devices.value.unshift(newDevice);
    }
};

/**
 * 设备下线时更新列表
 * [func-single-purpose] 单一用途：处理设备下线
 */
const updateDeviceOffline = (pid: string) => {
    const index = devices.value.findIndex(d => sameDeviceId(d.uuid, pid));
    if (index >= 0) {
        devices.value[index] = {
            ...devices.value[index],
            is_online: false,
        };
    }
};

/**
 * 设备状态更新（ping 时推送的 deviceUpdate）
 */
const updateDeviceStatus = (pid: string, phoneInfo: Record<string, unknown>) => {
    const index = devices.value.findIndex(d => sameDeviceId(d.uuid, pid));
    if (index < 0) return;

    const updates = applyPhoneInfoToRow(phoneInfo);
    if (Object.keys(updates).length > 0) {
        devices.value[index] = { ...devices.value[index], ...updates };
    }
};

/**
 * 解析电池电量
 * [core-dry] 复用电池解析逻辑
 */
const parseBatteryLevel = (batteryCharge: string | undefined): number | null => {
    if (!batteryCharge || typeof batteryCharge !== 'string') return null;
    const parts = batteryCharge.split('~');
    if (parts.length >= 2) {
        const level = parseInt(parts[1], 10);
        return isNaN(level) ? null : level;
    }
    const level = parseInt(batteryCharge, 10);
    return isNaN(level) ? null : level;
};

/**
 * 解析电池充电状态
 * [core-dry] 复用充电状态解析逻辑
 */
const parseBatteryCharging = (batteryCharge: string | undefined): boolean => {
    if (!batteryCharge || typeof batteryCharge !== 'string') return false;
    const parts = batteryCharge.split('~');
    return parts.length >= 1 && parts[0] === 't';
};

const connectGlobal = (email: string) => {
    if (socket?.readyState === WebSocket.OPEN) {
        return;
    }

    userEmail = email;
    connectionState.value = 'connecting';
    lastError.value = null;

    try {
        socket = new WebSocket(WEBSOCKET_URL);

        socket.onopen = () => {
            connectionState.value = 'connected';
            reconnectAttempts = 0;
            isInitialized = true;
            startHeartbeat();
            sendSubscribe();
        };

        socket.onmessage = handleMessage;

        socket.onclose = () => {
            connectionState.value = 'disconnected';
            stopHeartbeat();
            clearReconnectTimer();
            if (userEmail) {
                scheduleReconnect();
            }
        };

        socket.onerror = () => {
            lastError.value = '连接错误';
            connectionState.value = 'disconnected';
        };
    } catch {
        lastError.value = '无法建立连接';
        connectionState.value = 'disconnected';
    }
};

const disconnectGlobal = () => {
    stopHeartbeat();
    clearReconnectTimer();
    reconnectAttempts = 0;
    userEmail = '';

    if (socket) {
        socket.close();
        socket = null;
    }

    connectionState.value = 'disconnected';
    stats.value = { total: 0, online: 0, offline: 0 };
    devices.value = [];
    hasReceivedWsData.value = false;
    isInitialized = false;
};

const send = (message: WebSocketOutboundMessage): boolean => {
    if (socket?.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(message));
        return true;
    }
    lastError.value = '连接未就绪';
    return false;
};

const onMessage = (handler: (msg: WebSocketInboundMessage) => void) => {
    messageHandlers.push(handler);
    return () => {
        const index = messageHandlers.indexOf(handler);
        if (index > -1) {
            messageHandlers.splice(index, 1);
        }
    };
};

export function useGlobalWebSocket() {
    return {
        // 状态
        connectionState,
        lastError,
        stats,
        devices,
        hasReceivedWsData,
        isConnected: () => connectionState.value === 'connected',
        // 方法
        connect: connectGlobal,
        disconnect: disconnectGlobal,
        send,
        onMessage,
    };
}
