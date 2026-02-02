const { MockDevice, MockPanel } = require('./mock-device');

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

class TestRunner {
    constructor() {
        this.passed = 0;
        this.failed = 0;
    }

    async test(name, fn) {
        console.log(`\n▶ 测试: ${name}`);
        try {
            await fn();
            this.passed++;
            console.log(`  ✓ 通过`);
            return true;
        } catch (err) {
            this.failed++;
            console.log(`  ✗ 失败: ${err.message}`);
            return false;
        }
    }

    summary() {
        console.log('\n' + '='.repeat(60));
        console.log(`测试结果: ${this.passed} 通过, ${this.failed} 失败`);
        console.log('='.repeat(60));
        return this.failed === 0;
    }
}

async function runTests() {
    const timestamp = Date.now();
    const userAEmail = process.env.USER_A_EMAIL;
    const userAEncryptedEmail = process.env.USER_A_ENCRYPTED_EMAIL;
    
    if (!userAEncryptedEmail || !userAEmail) {
        console.error('错误: 请设置 USER_A_EMAIL 和 USER_A_ENCRYPTED_EMAIL 环境变量');
        process.exit(1);
    }

    const deviceBId = `device-B-${timestamp}`;
    const deviceCId = `device-C-${timestamp}`;
    const randomEmail = `random-${timestamp}@other.com`;

    console.log('='.repeat(60));
    console.log('测试: 用户-设备隔离验证');
    console.log('='.repeat(60));
    console.log(`用户 A 邮箱: ${userAEmail}`);
    console.log(`用户 A 加密邮箱: ${userAEncryptedEmail}`);
    console.log(`设备 B ID: ${deviceBId} (属于用户 A)`);
    console.log(`设备 C ID: ${deviceCId} (随机用户)`);

    const runner = new TestRunner();
    let panelA = null;
    let deviceB = null;
    let deviceC = null;

    try {
        // ========== 场景 1: 用户 A 连接，checkphone 应返回空列表 ==========
        await runner.test('场景1: 用户A连接后，checkphone返回空列表', async () => {
            panelA = new MockPanel({ userEmail: userAEncryptedEmail });
            await panelA.connect();
            
            const responsePromise = waitForMessage(panelA, 'checkphone');
            panelA.checkPhone();
            const response = await responsePromise;

            console.log(`    返回设备数: ${response.list.length}`);
            
            if (response.list.length !== 0) {
                throw new Error(`期望 0 个设备，实际返回 ${response.list.length} 个`);
            }
        });

        // ========== 场景 2: 设备 B (用户A的邮箱) 上线，checkphone 应返回设备 B ==========
        await runner.test('场景2: 设备B(用户A邮箱)上线后，checkphone返回设备B', async () => {
            deviceB = new MockDevice(deviceBId, {
                phoneName: 'Device B',
                model: 'Pixel 8',
                userEmail: userAEmail,
            });
            await deviceB.connect();
            await delay(2000);

            const responsePromise = waitForMessage(panelA, 'checkphone');
            panelA.checkPhone();
            const response = await responsePromise;

            console.log(`    返回设备数: ${response.list.length}`);
            
            const foundB = response.list.find(d => d.phone_id === deviceBId);
            if (!foundB) {
                throw new Error(`未找到设备 B (${deviceBId})`);
            }
            
            console.log(`    找到设备 B: ${foundB.phone_name}, is_online: ${foundB.is_online}`);
            
            if (response.list.length !== 1) {
                throw new Error(`期望 1 个设备，实际返回 ${response.list.length} 个`);
            }
        });

        // ========== 场景 3: 设备 C (随机邮箱) 上线，checkphone 应仍只返回设备 B ==========
        await runner.test('场景3: 设备C(随机邮箱)上线后，checkphone仍只返回设备B', async () => {
            deviceC = new MockDevice(deviceCId, {
                phoneName: 'Device C',
                model: 'Samsung S24',
                userEmail: randomEmail,
            });
            await deviceC.connect();
            await delay(2000);

            const responsePromise = waitForMessage(panelA, 'checkphone');
            panelA.checkPhone();
            const response = await responsePromise;

            console.log(`    返回设备数: ${response.list.length}`);
            
            const foundB = response.list.find(d => d.phone_id === deviceBId);
            const foundC = response.list.find(d => d.phone_id === deviceCId);
            
            if (!foundB) {
                throw new Error(`未找到设备 B (${deviceBId})`);
            }
            
            if (foundC) {
                throw new Error(`不应该看到设备 C (${deviceCId})，但却找到了`);
            }
            
            console.log(`    设备 B 存在: ✓`);
            console.log(`    设备 C 不可见: ✓`);
            
            if (response.list.length !== 1) {
                throw new Error(`期望 1 个设备，实际返回 ${response.list.length} 个`);
            }
        });

    } finally {
        if (deviceB) deviceB.disconnect();
        if (deviceC) deviceC.disconnect();
        if (panelA) panelA.disconnect();
        await delay(500);
    }

    const success = runner.summary();
    process.exit(success ? 0 : 1);
}

runTests().catch(err => {
    console.error('Fatal error:', err);
    process.exit(1);
});
