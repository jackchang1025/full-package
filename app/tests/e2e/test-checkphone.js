/**
 * 测试: 设备上线后，管理端能通过 checkphone 获取到设备信息
 */
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

async function runTest() {
    console.log('='.repeat(60));
    console.log('测试: 设备上线后，管理端能通过 checkphone 获取到设备信息');
    console.log(`WebSocket Server: ${WS_URL}`);
    console.log('='.repeat(60));

    const deviceId = 'test-device-' + Date.now();
    const device = new MockDevice(deviceId, {
        phoneName: 'Test Device ' + Date.now(),
        model: 'Pixel 8 Pro',
        batteryCharge: '88',
        accessibility: '1',
        country: 'China',
        userEmail: 'test@example.com',
    });

    const panel = new MockPanel();

    try {
        // Step 1: 设备连接并发送 ping (注册)
        console.log('\n[Step 1] 设备连接并注册...');
        await device.connect();
        console.log(`✓ 设备已连接, ID: ${deviceId}`);
        
        // 等待设备注册完成
        await delay(1500);
        console.log('✓ 设备注册完成 (已发送 ping)');

        // Step 2: 管理端连接
        console.log('\n[Step 2] 管理端连接...');
        await panel.connect();
        console.log('✓ 管理端已连接');

        // Step 3: 管理端发送 checkphone 请求
        console.log('\n[Step 3] 管理端发送 checkphone 请求...');
        const responsePromise = waitForMessage(panel, 'checkphone');
        panel.checkPhone();
        
        const response = await responsePromise;
        console.log('✓ 收到 checkphone 响应');

        // Step 4: 验证设备是否在列表中
        console.log('\n[Step 4] 验证设备信息...');
        console.log(`  - 返回设备总数: ${response.total}`);
        console.log(`  - 当前页设备数: ${response.list.length}`);
        
        // 查找我们的测试设备
        const foundDevice = response.list.find(d => d.phone_id === deviceId);
        
        if (foundDevice) {
            console.log('\n✓ 成功找到测试设备!');
            console.log('  设备信息:');
            console.log(`    - phone_id: ${foundDevice.phone_id}`);
            console.log(`    - phone_name: ${foundDevice.phone_name}`);
            console.log(`    - model: ${foundDevice.model}`);
            console.log(`    - battery_charge: ${foundDevice.battery_charge}`);
            console.log(`    - accessibility: ${foundDevice.accessibility}`);
            console.log(`    - country: ${foundDevice.country}`);
            console.log(`    - is_online: ${foundDevice.is_online}`);
        } else {
            console.log('\n✗ 未找到测试设备!');
            console.log('  返回的设备列表:');
            response.list.forEach((d, i) => {
                console.log(`    [${i}] phone_id: ${d.phone_id}, phone_name: ${d.phone_name}`);
            });
        }

        // 结果
        console.log('\n' + '='.repeat(60));
        if (foundDevice) {
            console.log('测试结果: ✓ 通过');
            console.log('设备上线后，管理端可以通过 checkphone 获取到设备信息');
        } else {
            console.log('测试结果: ✗ 失败');
            console.log('设备上线后，管理端无法通过 checkphone 获取到设备信息');
        }
        console.log('='.repeat(60));

        return !!foundDevice;

    } catch (err) {
        console.error('\n✗ 测试出错:', err.message);
        return false;
    } finally {
        device.disconnect();
        panel.disconnect();
        await delay(500);
    }
}

runTest().then(success => {
    process.exit(success ? 0 : 1);
}).catch(err => {
    console.error('Fatal error:', err);
    process.exit(1);
});
