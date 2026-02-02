const WebSocket = require('ws');

const WS_URL = process.env.WS_URL || 'ws://localhost:8081';

function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

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

function waitForPushMessage(panel, type, timeout = 5000) {
    return new Promise((resolve, reject) => {
        const timer = setTimeout(() => {
            reject(new Error(`Timeout waiting for push message: ${type}`));
        }, timeout);

        const unsubscribe = panel.onMessage((msg) => {
            if (msg.type === type) {
                clearTimeout(timer);
                unsubscribe();
                resolve(msg);
            }
        });
    });
}

async function connectWithRetry(client, maxRetries = 3) {
    let lastError;
    for (let i = 0; i < maxRetries; i++) {
        try {
            await client.connect();
            return;
        } catch (err) {
            lastError = err;
            await delay(1000);
        }
    }
    throw lastError;
}

function checkServerAvailable(url = WS_URL) {
    return new Promise((resolve) => {
        const ws = new WebSocket(url);
        const timer = setTimeout(() => {
            ws.close();
            resolve(false);
        }, 3000);

        ws.on('open', () => {
            clearTimeout(timer);
            ws.close();
            resolve(true);
        });

        ws.on('error', () => {
            clearTimeout(timer);
            resolve(false);
        });
    });
}

class TestRunner {
    constructor(name) {
        this.name = name;
        this.passed = 0;
        this.failed = 0;
        this.results = [];
    }

    async run(testName, testFn) {
        console.log(`\n▶ ${testName}`);
        try {
            await testFn();
            this.passed++;
            this.results.push({ name: testName, status: 'PASS' });
            console.log(`  ✓ 通过`);
            return true;
        } catch (err) {
            this.failed++;
            this.results.push({ name: testName, status: 'FAIL', error: err.message });
            console.log(`  ✗ 失败: ${err.message}`);
            return false;
        }
    }

    summary() {
        console.log('\n' + '='.repeat(60));
        console.log(`测试结果: ${this.passed} 通过, ${this.failed} 失败`);
        console.log('='.repeat(60));

        if (this.failed > 0) {
            console.log('\n失败的测试:');
            this.results
                .filter(r => r.status === 'FAIL')
                .forEach(r => console.log(`  - ${r.name}: ${r.error}`));
        }

        return this.failed === 0;
    }
}

function assert(condition, message) {
    if (!condition) {
        throw new Error(message || 'Assertion failed');
    }
}

function assertEqual(actual, expected, message) {
    if (actual !== expected) {
        throw new Error(message || `Expected ${expected}, got ${actual}`);
    }
}

module.exports = {
    WS_URL,
    delay,
    waitForMessage,
    waitForPushMessage,
    connectWithRetry,
    checkServerAvailable,
    TestRunner,
    assert,
    assertEqual,
};
