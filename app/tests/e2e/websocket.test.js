const { MockDevice, MockPanel } = require('./mock-device');

const WS_URL = process.env.WS_URL || 'ws://localhost:8081';
const TEST_TIMEOUT = 10000;

function waitForMessage(client, type, timeout = 5000) {
    return new Promise((resolve, reject) => {
        const timer = setTimeout(() => {
            reject(new Error(`Timeout waiting for message type: ${type}`));
        }, timeout);

        const unsubscribe = client.onMessage((msg) => {
            if (msg.type === type) {
                clearTimeout(timer);
                unsubscribe();
                resolve(msg);
            }
        });
    });
}

function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

class TestRunner {
    constructor() {
        this.passed = 0;
        this.failed = 0;
        this.results = [];
    }

    async run(name, testFn) {
        console.log(`\n▶ Running: ${name}`);
        try {
            await testFn();
            this.passed++;
            this.results.push({ name, status: 'PASS' });
            console.log(`  ✓ PASS`);
        } catch (err) {
            this.failed++;
            this.results.push({ name, status: 'FAIL', error: err.message });
            console.log(`  ✗ FAIL: ${err.message}`);
        }
    }

    summary() {
        console.log('\n' + '='.repeat(60));
        console.log('TEST SUMMARY');
        console.log('='.repeat(60));
        console.log(`Total: ${this.passed + this.failed}`);
        console.log(`Passed: ${this.passed}`);
        console.log(`Failed: ${this.failed}`);
        console.log('='.repeat(60));

        if (this.failed > 0) {
            console.log('\nFailed tests:');
            this.results
                .filter(r => r.status === 'FAIL')
                .forEach(r => console.log(`  - ${r.name}: ${r.error}`));
        }

        return this.failed === 0;
    }
}

async function runTests() {
    const runner = new TestRunner();
    let device = null;
    let panel = null;

    console.log('='.repeat(60));
    console.log('WebSocket E2E Integration Tests');
    console.log(`Server: ${WS_URL}`);
    console.log('='.repeat(60));

    try {
        await runner.run('Device can connect to server', async () => {
            device = new MockDevice('e2e-test-device-' + Date.now());
            await device.connect();
            if (!device.isConnected) {
                throw new Error('Device not connected');
            }
        });

        await runner.run('Panel can connect to server', async () => {
            panel = new MockPanel();
            await panel.connect();
            if (!panel.isConnected) {
                throw new Error('Panel not connected');
            }
        });

        await runner.run('Panel receives checkphone response', async () => {
            const responsePromise = waitForMessage(panel, 'checkphone');
            panel.checkPhone();
            const response = await responsePromise;

            if (response.type !== 'checkphone') {
                throw new Error(`Expected type 'checkphone', got '${response.type}'`);
            }
            if (!Array.isArray(response.list)) {
                throw new Error('Expected list to be an array');
            }
            if (typeof response.total !== 'number') {
                throw new Error('Expected total to be a number');
            }
        });

        await runner.run('Panel can join device', async () => {
            // Wait for device to be fully registered
            await delay(1000);
            
            const responsePromise = waitForMessage(panel, 'joinResponse');
            panel.joinDevice(device.deviceId);
            const response = await responsePromise;

            if (response.type !== 'joinResponse') {
                throw new Error(`Expected type 'joinResponse', got '${response.type}'`);
            }
            if (response.pid !== device.deviceId) {
                throw new Error(`Expected pid '${device.deviceId}', got '${response.pid}'`);
            }
            
            // Wait for join to be fully processed
            await delay(500);
        });

        await runner.run('Panel receives device status', async () => {
            const responsePromise = waitForMessage(panel, 'statusBatch');
            panel.pingDevice(device.deviceId);
            const response = await responsePromise;

            if (response.type !== 'statusBatch') {
                throw new Error(`Expected type 'statusBatch', got '${response.type}'`);
            }
            if (!['OPEN', 'CLOSED', 'UNKNOWN'].includes(response.serverToPhone)) {
                throw new Error(`Invalid serverToPhone: ${response.serverToPhone}`);
            }
        });

        await runner.run('Panel can request SMS and receive data', async () => {
            await delay(300);
            const responsePromise = waitForMessage(panel, 'sms');
            panel.requestSms();
            const response = await responsePromise;

            if (response.type !== 'sms') {
                throw new Error(`Expected type 'sms', got '${response.type}'`);
            }
            if (!response.data) {
                throw new Error('Expected data field');
            }
            if (response.pid !== device.deviceId) {
                throw new Error(`Expected pid '${device.deviceId}', got '${response.pid}'`);
            }
        });

        await runner.run('Panel can request contacts and receive data', async () => {
            await delay(100);
            const responsePromise = waitForMessage(panel, 'loadcontacts');
            panel.requestContacts();
            const response = await responsePromise;

            if (response.type !== 'loadcontacts') {
                throw new Error(`Expected type 'loadcontacts', got '${response.type}'`);
            }
        });

        await runner.run('Panel can request files and receive data', async () => {
            await delay(100);
            const responsePromise = waitForMessage(panel, 'files');
            panel.requestFiles('/sdcard');
            const response = await responsePromise;

            if (response.type !== 'files') {
                throw new Error(`Expected type 'files', got '${response.type}'`);
            }
        });

        await runner.run('Panel can request apps and receive data', async () => {
            await delay(100);
            const responsePromise = waitForMessage(panel, 'loadapps');
            panel.requestApps();
            const response = await responsePromise;

            if (response.type !== 'loadapps') {
                throw new Error(`Expected type 'loadapps', got '${response.type}'`);
            }
        });

        await runner.run('Panel can request location and receive data', async () => {
            await delay(100);
            const responsePromise = waitForMessage(panel, 'loc');
            panel.requestLocation();
            const response = await responsePromise;

            if (response.type !== 'loc') {
                throw new Error(`Expected type 'loc', got '${response.type}'`);
            }
        });

        await runner.run('Panel can start screen share and receive screen data', async () => {
            await delay(100);
            const responsePromise = waitForMessage(panel, 'screen');
            panel.startScreenShare();
            const response = await responsePromise;

            if (response.type !== 'screen') {
                throw new Error(`Expected type 'screen', got '${response.type}'`);
            }
            if (!response.data) {
                throw new Error('Expected screen data');
            }
            if (!response.wmob || !response.hmob) {
                throw new Error('Expected screen dimensions');
            }
        });

        await runner.run('Panel can send tap command', async () => {
            panel.sendTap(500, 800);
            await delay(100);
        });

        await runner.run('Panel can send swipe command', async () => {
            panel.sendSwipe(500, 800, 500, 200);
            await delay(100);
        });

        await runner.run('Panel can send navigation command', async () => {
            panel.sendNavigation('ho');
            await delay(100);
            panel.sendNavigation('bak');
            await delay(100);
            panel.sendNavigation('rec');
            await delay(100);
        });

        await runner.run('Device ping updates status', async () => {
            device.sendPing();
            await delay(500);

            const responsePromise = waitForMessage(panel, 'statusBatch');
            panel.pingDevice(device.deviceId);
            const response = await responsePromise;

            if (!response.phoneInfo) {
                throw new Error('Expected phoneInfo in status');
            }
        });

        await runner.run('Multiple devices can connect', async () => {
            const device2 = new MockDevice('e2e-test-device-2-' + Date.now());
            await device2.connect();

            if (!device2.isConnected) {
                throw new Error('Second device not connected');
            }

            device2.disconnect();
        });

    } catch (err) {
        console.error('\nTest setup error:', err.message);
    } finally {
        if (device) {
            device.disconnect();
        }
        if (panel) {
            panel.disconnect();
        }

        await delay(500);
    }

    const success = runner.summary();
    process.exit(success ? 0 : 1);
}

if (require.main === module) {
    runTests().catch(err => {
        console.error('Fatal error:', err);
        process.exit(1);
    });
}

module.exports = { runTests, waitForMessage, delay };
