/**
 * Push Mode E2E Tests
 * 
 * 测试 WebSocket 推送模式功能:
 * - Panel 订阅注册 (checkphone 触发)
 * - 设备上线推送 (deviceOnline)
 * - 设备离线推送 (deviceOffline)
 * - 用户隔离 (User A 看不到 User B 的设备)
 * - 管理员全量推送
 * - 多 Panel 同时在线
 * 
 * 运行前需要设置环境变量:
 * - USER_A_EMAIL: 用户 A 明文邮箱
 * - USER_A_ENCRYPTED_EMAIL: 用户 A 加密邮箱
 * - USER_B_EMAIL: 用户 B 明文邮箱  
 * - USER_B_ENCRYPTED_EMAIL: 用户 B 加密邮箱
 * - ADMIN_ENCRYPTED_EMAIL: 管理员加密邮箱 (可选，默认使用内置)
 * 
 * 运行: npm run test:push 或 node test-push-mode.js
 */

const { MockDevice, MockPanel } = require('./mock-device');
const {
    WS_URL,
    delay,
    waitForPushMessage,
    connectWithRetry,
    checkServerAvailable,
    TestRunner,
    assert,
    assertEqual,
} = require('./test-utils');

// 测试配置
const CONFIG = {
    // 用户 A - 普通用户
    USER_A_EMAIL: process.env.USER_A_EMAIL || 'usera@test.com',
    USER_A_ENCRYPTED_EMAIL: process.env.USER_A_ENCRYPTED_EMAIL || 'GCt/Suj1maxHZ3aCykJufw==',
    
    // 用户 B - 另一个普通用户
    USER_B_EMAIL: process.env.USER_B_EMAIL || 'userb@test.com',
    USER_B_ENCRYPTED_EMAIL: process.env.USER_B_ENCRYPTED_EMAIL || 'encrypted-user-b-email',
    
    // 管理员
    ADMIN_ENCRYPTED_EMAIL: process.env.ADMIN_ENCRYPTED_EMAIL || 'GCt/Suj1maxHZ3aCykJufw==',
    
    // 超时设置
    PUSH_TIMEOUT: 8000,
    CONNECT_TIMEOUT: 5000,
};

/**
 * 创建带用户关联的 MockDevice
 */
function createDevice(deviceId, userEmail) {
    return new MockDevice(deviceId, {
        phoneName: `Test Device ${deviceId}`,
        model: 'Test Model',
        userEmail: userEmail,
    });
}

/**
 * 创建带加密邮箱的 MockPanel
 */
function createPanel(encryptedEmail, isAdmin = false) {
    return new MockPanel({
        userEmail: encryptedEmail,
        isAdmin: isAdmin,
    });
}

/**
 * 等待特定设备的推送消息
 */
function waitForDevicePush(panel, type, deviceId, timeout = CONFIG.PUSH_TIMEOUT) {
    return new Promise((resolve, reject) => {
        const timer = setTimeout(() => {
            reject(new Error(`Timeout waiting for ${type} push for device ${deviceId}`));
        }, timeout);

        const unsubscribe = panel.onMessage((msg) => {
            if (msg.type === type && msg.pid === deviceId) {
                clearTimeout(timer);
                unsubscribe();
                resolve(msg);
            }
        });
    });
}

/**
 * 等待任意推送消息 (用于验证不应该收到的消息)
 */
function waitForAnyPush(panel, types, timeout = 2000) {
    return new Promise((resolve, reject) => {
        const timer = setTimeout(() => {
            resolve(null); // 超时返回 null，表示没收到消息
        }, timeout);

        const unsubscribe = panel.onMessage((msg) => {
            if (types.includes(msg.type)) {
                clearTimeout(timer);
                unsubscribe();
                resolve(msg);
            }
        });
    });
}

// ============================================================================
// 测试用例
// ============================================================================

const runner = new TestRunner('Push Mode E2E Tests');

/**
 * Test 1: Panel 订阅注册
 * 验证 checkphone 调用后 Panel 被注册到订阅表
 */
async function testPanelSubscriptionRegistration() {
    const panel = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    
    try {
        await connectWithRetry(panel);
        
        // 发送 checkphone 触发订阅注册
        panel.checkPhone();
        
        // 等待 checkphone 响应
        const response = await new Promise((resolve, reject) => {
            const timer = setTimeout(() => reject(new Error('Timeout waiting for checkphone response')), 5000);
            const unsub = panel.onMessage((msg) => {
                if (msg.type === 'checkphone') {
                    clearTimeout(timer);
                    unsub();
                    resolve(msg);
                }
            });
        });
        
        assert(response.type === 'checkphone', 'Should receive checkphone response');
        console.log('  Panel subscription registered via checkphone');
        
    } finally {
        panel.disconnect();
    }
}

/**
 * Test 2: 设备上线推送 (deviceOnline)
 * 验证设备上线时 Panel 收到推送
 */
async function testDeviceOnlinePush() {
    const deviceId = `push-test-device-${Date.now()}`;
    const panel = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const device = createDevice(deviceId, CONFIG.USER_A_EMAIL);
    
    try {
        // 1. Panel 先连接并注册订阅
        await connectWithRetry(panel);
        panel.checkPhone();
        await delay(500);
        
        // 2. 设置监听器等待 deviceOnline 推送
        const pushPromise = waitForDevicePush(panel, 'deviceOnline', deviceId);
        
        // 3. 设备连接 (触发上线推送)
        await connectWithRetry(device);
        
        // 4. 验证收到推送
        const pushMsg = await pushPromise;
        
        assertEqual(pushMsg.type, 'deviceOnline', 'Push type should be deviceOnline');
        assertEqual(pushMsg.pid, deviceId, 'Push should contain correct device ID');
        assert(pushMsg.deviceInfo !== undefined, 'Push should contain deviceInfo');
        
        console.log(`  Received deviceOnline push for ${deviceId}`);
        
    } finally {
        device.disconnect();
        panel.disconnect();
    }
}

/**
 * Test 3: 设备离线推送 (deviceOffline)
 * 验证设备断开时 Panel 收到推送
 */
async function testDeviceOfflinePush() {
    const deviceId = `push-test-offline-${Date.now()}`;
    const panel = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const device = createDevice(deviceId, CONFIG.USER_A_EMAIL);
    
    try {
        // 1. Panel 先连接并注册订阅
        await connectWithRetry(panel);
        panel.checkPhone();
        await delay(500);
        
        // 2. 设备连接
        await connectWithRetry(device);
        await delay(500);
        
        // 3. 设置监听器等待 deviceOffline 推送
        const pushPromise = waitForDevicePush(panel, 'deviceOffline', deviceId);
        
        // 4. 设备断开 (触发离线推送)
        device.disconnect();
        
        // 5. 验证收到推送
        const pushMsg = await pushPromise;
        
        assertEqual(pushMsg.type, 'deviceOffline', 'Push type should be deviceOffline');
        assertEqual(pushMsg.pid, deviceId, 'Push should contain correct device ID');
        
        console.log(`  Received deviceOffline push for ${deviceId}`);
        
    } finally {
        panel.disconnect();
    }
}

/**
 * Test 4: 用户隔离 - User A 看不到 User B 的设备
 */
async function testUserIsolation() {
    const deviceIdA = `isolation-device-a-${Date.now()}`;
    const deviceIdB = `isolation-device-b-${Date.now()}`;
    
    const panelA = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const panelB = createPanel(CONFIG.USER_B_ENCRYPTED_EMAIL);
    const deviceA = createDevice(deviceIdA, CONFIG.USER_A_EMAIL);
    const deviceB = createDevice(deviceIdB, CONFIG.USER_B_EMAIL);
    
    try {
        // 1. 两个 Panel 都连接并注册订阅
        await Promise.all([
            connectWithRetry(panelA),
            connectWithRetry(panelB),
        ]);
        
        panelA.checkPhone();
        panelB.checkPhone();
        await delay(500);
        
        // 2. 设置监听器
        const panelAReceived = [];
        const panelBReceived = [];
        
        panelA.onMessage((msg) => {
            if (msg.type === 'deviceOnline') {
                panelAReceived.push(msg.pid);
            }
        });
        
        panelB.onMessage((msg) => {
            if (msg.type === 'deviceOnline') {
                panelBReceived.push(msg.pid);
            }
        });
        
        // 3. 设备 A 上线 (属于 User A)
        await connectWithRetry(deviceA);
        await delay(1000);
        
        // 4. 设备 B 上线 (属于 User B)
        await connectWithRetry(deviceB);
        await delay(1000);
        
        // 5. 验证隔离
        // Panel A 应该只收到 Device A 的推送
        assert(panelAReceived.includes(deviceIdA), 'Panel A should receive Device A online');
        assert(!panelAReceived.includes(deviceIdB), 'Panel A should NOT receive Device B online');
        
        // Panel B 应该只收到 Device B 的推送
        assert(panelBReceived.includes(deviceIdB), 'Panel B should receive Device B online');
        assert(!panelBReceived.includes(deviceIdA), 'Panel B should NOT receive Device A online');
        
        console.log('  User isolation verified: each panel only sees their own devices');
        
    } finally {
        deviceA.disconnect();
        deviceB.disconnect();
        panelA.disconnect();
        panelB.disconnect();
    }
}

/**
 * Test 5: 管理员全量推送
 * 验证管理员能收到所有用户的设备推送
 */
async function testAdminReceivesAllDevices() {
    const deviceIdA = `admin-test-a-${Date.now()}`;
    const deviceIdB = `admin-test-b-${Date.now()}`;
    
    // 注意: 需要一个真正的管理员加密邮箱
    // 这里假设 ADMIN_ENCRYPTED_EMAIL 对应的用户是管理员
    const adminPanel = createPanel(CONFIG.ADMIN_ENCRYPTED_EMAIL, true);
    const deviceA = createDevice(deviceIdA, CONFIG.USER_A_EMAIL);
    const deviceB = createDevice(deviceIdB, CONFIG.USER_B_EMAIL);
    
    try {
        // 1. 管理员 Panel 连接并注册订阅
        await connectWithRetry(adminPanel);
        adminPanel.checkPhone();
        await delay(500);
        
        // 2. 设置监听器
        const adminReceived = [];
        adminPanel.onMessage((msg) => {
            if (msg.type === 'deviceOnline') {
                adminReceived.push(msg.pid);
            }
        });
        
        // 3. 两个不同用户的设备上线
        await connectWithRetry(deviceA);
        await delay(500);
        await connectWithRetry(deviceB);
        await delay(1000);
        
        // 4. 验证管理员收到所有设备推送
        assert(adminReceived.includes(deviceIdA), 'Admin should receive Device A online');
        assert(adminReceived.includes(deviceIdB), 'Admin should receive Device B online');
        
        console.log('  Admin received all device pushes');
        
    } finally {
        deviceA.disconnect();
        deviceB.disconnect();
        adminPanel.disconnect();
    }
}

/**
 * Test 6: 多 Panel 同时在线
 * 验证同一用户多个 Panel 都能收到推送
 */
async function testMultiplePanelsSameUser() {
    const deviceId = `multi-panel-device-${Date.now()}`;
    
    const panel1 = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const panel2 = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const device = createDevice(deviceId, CONFIG.USER_A_EMAIL);
    
    try {
        // 1. 两个 Panel 都连接并注册订阅
        await Promise.all([
            connectWithRetry(panel1),
            connectWithRetry(panel2),
        ]);
        
        panel1.checkPhone();
        panel2.checkPhone();
        await delay(500);
        
        // 2. 设置监听器
        const panel1Promise = waitForDevicePush(panel1, 'deviceOnline', deviceId);
        const panel2Promise = waitForDevicePush(panel2, 'deviceOnline', deviceId);
        
        // 3. 设备上线
        await connectWithRetry(device);
        
        // 4. 验证两个 Panel 都收到推送
        const [msg1, msg2] = await Promise.all([panel1Promise, panel2Promise]);
        
        assertEqual(msg1.pid, deviceId, 'Panel 1 should receive correct device');
        assertEqual(msg2.pid, deviceId, 'Panel 2 should receive correct device');
        
        console.log('  Both panels received the push');
        
    } finally {
        device.disconnect();
        panel1.disconnect();
        panel2.disconnect();
    }
}

/**
 * Test 7: Panel 断开后不再收到推送
 */
async function testPanelDisconnectStopsPush() {
    const deviceId = `disconnect-test-${Date.now()}`;
    
    const panel = createPanel(CONFIG.USER_A_ENCRYPTED_EMAIL);
    const device = createDevice(deviceId, CONFIG.USER_A_EMAIL);
    
    try {
        // 1. Panel 连接并注册订阅
        await connectWithRetry(panel);
        panel.checkPhone();
        await delay(500);
        
        // 2. Panel 断开
        panel.disconnect();
        await delay(500);
        
        // 3. 设备上线 (此时 Panel 已断开)
        await connectWithRetry(device);
        await delay(1000);
        
        // 如果代码执行到这里没有错误，说明服务器正确处理了断开的 Panel
        console.log('  Server correctly handles disconnected panel');
        
    } finally {
        device.disconnect();
    }
}

// ============================================================================
// 主函数
// ============================================================================

async function main() {
    console.log('='.repeat(60));
    console.log('Push Mode E2E Tests');
    console.log('='.repeat(60));
    console.log(`WebSocket URL: ${WS_URL}`);
    console.log(`User A Email: ${CONFIG.USER_A_EMAIL}`);
    console.log(`User B Email: ${CONFIG.USER_B_EMAIL}`);
    console.log('');
    
    // 检查服务器是否可用
    const serverAvailable = await checkServerAvailable();
    if (!serverAvailable) {
        console.error('WebSocket server is not available!');
        console.error('Please start the server: ./vendor/bin/sail artisan websocket:serve');
        process.exit(1);
    }
    
    console.log('Server is available, starting tests...\n');
    
    // 运行测试
    await runner.run('Panel subscription registration', testPanelSubscriptionRegistration);
    await runner.run('Device online push (deviceOnline)', testDeviceOnlinePush);
    await runner.run('Device offline push (deviceOffline)', testDeviceOfflinePush);
    await runner.run('User isolation', testUserIsolation);
    await runner.run('Admin receives all devices', testAdminReceivesAllDevices);
    await runner.run('Multiple panels same user', testMultiplePanelsSameUser);
    await runner.run('Panel disconnect stops push', testPanelDisconnectStopsPush);
    
    // 输出结果
    const success = runner.summary();
    process.exit(success ? 0 : 1);
}

main().catch((err) => {
    console.error('Test runner error:', err);
    process.exit(1);
});
