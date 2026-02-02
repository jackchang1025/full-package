import { ref, type Ref } from 'vue';
import type {
    ConnectionState,
    WebSocketOutboundMessage,
    WebSocketInboundMessage,
    DeviceOnlineMessage,
    DeviceOfflineMessage,
} from '@/types/websocket';

const WEBSOCKET_URL = import.meta.env.VITE_WEBSOCKET_URL || 'ws://localhost:8081';
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
const stats: Ref<{ total: number; online: number; offline: number }> = ref({
    total: 0,
    online: 0,
    offline: 0,
});

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
        const msgType = (data as { type?: string }).type;

        switch (msgType) {
            case 'subscribe':
                // 订阅成功响应，不需要特殊处理
                break;
            case 'deviceOnline':
            case 'deviceOffline': {
                // 更新统计数据
                const statusData = data as DeviceOnlineMessage | DeviceOfflineMessage;
                if (statusData.stats) {
                    stats.value = statusData.stats;
                }
                break;
            }
            case 'pong':
                // 心跳响应，不需要处理
                break;
        }

        // 调用外部消息处理器
        messageHandlers.forEach(handler => handler(data));
    } catch {
        lastError.value = '消息解析失败';
    }
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
        isConnected: () => connectionState.value === 'connected',
        // 方法
        connect: connectGlobal,
        disconnect: disconnectGlobal,
        send,
        onMessage,
    };
}
