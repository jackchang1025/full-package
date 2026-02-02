import { ref, computed, onUnmounted, type Ref, type ComputedRef } from 'vue';
import type {
    ConnectionState,
    WebSocketOutboundMessage,
    WebSocketInboundMessage,
    JoinMessage,
    PingMessage,
    OutMessage,
    DeviceStatus,
    PhoneInfo,
    StatusBatchMessage,
    DeviceOnlineMessage,
    DeviceOfflineMessage,
    DeviceUpdateMessage,
} from '@/types/websocket';

const WEBSOCKET_URL = import.meta.env.WEBSOCKET_URL || 'ws://localhost:8081';
const PING_INTERVAL = 5000; // 与旧系统保持一致，5秒心跳
const RECONNECT_BASE_DELAY = 1000;
const RECONNECT_MAX_DELAY = 30000;

// 设备在线状态判断：serverToPhone === 'OPEN' 表示在线
const DEVICE_ONLINE_STATUS = 'OPEN';

export function useDeviceWebSocket() {
    const connectionState: Ref<ConnectionState> = ref('disconnected');
    const lastError: Ref<string | null> = ref(null);
    const deviceStatus: Ref<DeviceStatus | null> = ref(null);

    // 设备在线状态 (独立于 WebSocket 连接状态)
    const isDeviceOnline: ComputedRef<boolean> = computed(() => {
        if (!deviceStatus.value) return false;
        const status = deviceStatus.value.connectionStatus;
        // 兼容字符串和数字类型
        return status === DEVICE_ONLINE_STATUS || status === 'OPEN' || String(status).toUpperCase() === 'OPEN';
    });

    let socket: WebSocket | null = null;
    let pingInterval: ReturnType<typeof setInterval> | null = null;
    let reconnectTimeout: ReturnType<typeof setTimeout> | null = null;
    let reconnectAttempts = 0;
    let currentDeviceId: string | null = null;
    let currentUsercheck: string | null = null;
    let messageHandlers: Array<(msg: WebSocketInboundMessage) => void> = [];

    const clearTimers = () => {
        if (pingInterval) {
            clearInterval(pingInterval);
            pingInterval = null;
        }
        if (reconnectTimeout) {
            clearTimeout(reconnectTimeout);
            reconnectTimeout = null;
        }
    };

    const startPing = () => {
        if (pingInterval) clearInterval(pingInterval);
        pingInterval = setInterval(() => {
            if (socket?.readyState === WebSocket.OPEN && currentDeviceId) {
                const pingMsg: PingMessage = {
                    itype: 'slr_panel',
                    subc: 'ping',
                    pid: currentDeviceId,
                };
                socket.send(JSON.stringify(pingMsg));
            }
        }, PING_INTERVAL);
    };

    const scheduleReconnect = () => {
        if (!currentDeviceId || !currentUsercheck) return;

        const delay = Math.min(
            RECONNECT_BASE_DELAY * Math.pow(2, reconnectAttempts),
            RECONNECT_MAX_DELAY
        );
        reconnectAttempts++;
        connectionState.value = 'reconnecting';

        reconnectTimeout = setTimeout(() => {
            if (currentDeviceId && currentUsercheck) {
                connect(currentDeviceId, currentUsercheck);
            }
        }, delay);
    };

    const updateDeviceStatus = (
        serverToPhone: string | number | undefined,
        isOnline: boolean | undefined,
        lastPing: string | null,
        phoneInfo: PhoneInfo | null
    ) => {
        let connectionStatus: string;

        if (serverToPhone !== undefined) {
            connectionStatus = String(serverToPhone).toUpperCase();
        } else if (isOnline !== undefined) {
            connectionStatus = isOnline ? DEVICE_ONLINE_STATUS : 'CLOSED';
        } else {
            connectionStatus = 'CLOSED';
        }

        // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
        deviceStatus.value = {
            lastPing: lastPing || '',
            connectionStatus,
            phoneInfo,
        };
    };

    const handleMessage = (event: MessageEvent) => {
        try {
            const data = JSON.parse(event.data) as WebSocketInboundMessage;
            const msgType = (data as { type?: string }).type;

            switch (msgType) {
                case 'statusBatch': {
                    // 统一处理 join 响应和 ping 响应（都使用 statusBatch 类型）
                    const msg = data as StatusBatchMessage;
                    updateDeviceStatus(
                        msg.serverToPhone,
                        undefined,
                        msg.lastPing,
                        msg.phoneInfo
                    );
                    break;
                }
                case 'deviceOnline': {
                    const msg = data as DeviceOnlineMessage;
                    if (msg.pid === currentDeviceId && deviceStatus.value) {
                        deviceStatus.value = {
                            ...deviceStatus.value,
                            connectionStatus: DEVICE_ONLINE_STATUS,
                        };
                    }
                    break;
                }
                case 'deviceOffline': {
                    const msg = data as DeviceOfflineMessage;
                    if (msg.pid === currentDeviceId && deviceStatus.value) {
                        deviceStatus.value = {
                            ...deviceStatus.value,
                            connectionStatus: 'CLOSED',
                        };
                    }
                    break;
                }
                case 'deviceUpdate': {
                    const msg = data as DeviceUpdateMessage;
                    if (msg.pid === currentDeviceId && deviceStatus.value) {
                        const isOnline = msg.phoneInfo?.is_online ?? false;
                        deviceStatus.value = {
                            ...deviceStatus.value,
                            connectionStatus: isOnline ? DEVICE_ONLINE_STATUS : 'CLOSED',
                            phoneInfo: msg.phoneInfo || deviceStatus.value.phoneInfo,
                        };
                    }
                    break;
                }
            }

            messageHandlers.forEach(handler => handler(data));
        } catch {
            lastError.value = '消息解析失败';
        }
    };

    const connect = (deviceId: string, usercheck: string) => {
        if (socket?.readyState === WebSocket.OPEN) {
            disconnect();
        }

        currentDeviceId = deviceId;
        currentUsercheck = usercheck;
        connectionState.value = 'connecting';
        lastError.value = null;

        try {
            socket = new WebSocket(WEBSOCKET_URL);

            socket.onopen = () => {
                connectionState.value = 'connected';
                reconnectAttempts = 0;

                const joinMsg: JoinMessage = {
                    itype: 'slr_panel',
                    subc: 'join',
                    pid: deviceId,
                    usercheck: usercheck,
                };
                socket!.send(JSON.stringify(joinMsg));
                startPing();
            };

            socket.onmessage = handleMessage;

            socket.onclose = () => {
                connectionState.value = 'disconnected';
                clearTimers();
                scheduleReconnect();
            };

            socket.onerror = () => {
                lastError.value = '连接错误';
                connectionState.value = 'disconnected';
            };
        } catch (err) {
            lastError.value = '无法建立连接';
            connectionState.value = 'disconnected';
        }
    };

    const disconnect = () => {
        clearTimers();
        reconnectAttempts = 0;

        if (socket && currentDeviceId) {
            if (socket.readyState === WebSocket.OPEN) {
                const outMsg: OutMessage = {
                    itype: 'slr_panel',
                    subc: 'out',
                    pid: currentDeviceId,
                };
                socket.send(JSON.stringify(outMsg));
            }
            socket.close();
        }

        socket = null;
        currentDeviceId = null;
        currentUsercheck = null;
        connectionState.value = 'disconnected';
        deviceStatus.value = null;
    };

    const send = (message: WebSocketOutboundMessage) => {
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
            messageHandlers = messageHandlers.filter(h => h !== handler);
        };
    };

    onUnmounted(() => {
        disconnect();
        messageHandlers = [];
    });

    return {
        connectionState,
        lastError,
        deviceStatus,
        isDeviceOnline,
        connect,
        disconnect,
        send,
        onMessage,
    };
}
