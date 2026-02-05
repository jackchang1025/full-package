<?php

use App\Services\ApkBuilder\ApkBuildConfig;

describe('ApkBuildConfig validation', function () {
    it('validate returns errors for empty app_id', function () {
        $config = new ApkBuildConfig(
            appId: '',
            userId: '1',
            appName: 'Test App',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        $errors = $config->validate();

        expect($errors)->toContain('app_id is required');
    });

    it('validate returns errors for invalid package name', function () {
        $config = new ApkBuildConfig(
            appId: 'invalid-name',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        $errors = $config->validate();

        expect($errors)->toContain('app_id must be a valid package name (e.g., com.example.app)');
    });

    it('validate passes for valid package name', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        $errors = $config->validate();

        expect($errors)->toBeEmpty();
    });

    it('validate returns errors for invalid app_version', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: 'abc',
            websocketUrl: 'ws://localhost:8081'
        );

        $errors = $config->validate();

        expect($errors)->toContain('app_version must be a valid version (e.g., 1.0 or 1.0.0)');
    });

    it('validate returns errors for invalid websocket_url', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'http://invalid'
        );

        $errors = $config->validate();

        expect($errors)->toContain('websocket_url must be a valid WebSocket URL (e.g., ws://example.com:8080 or wss://example.com:8080)');
    });

    it('validate passes for valid ws and wss URLs', function () {
        $configWs = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://example.com:8080'
        );
        $configWss = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'wss://example.com:443'
        );

        expect($configWs->validate())->toBeEmpty();
        expect($configWss->validate())->toBeEmpty();
    });

    it('isStoreMode returns true when buildType is S', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            buildType: 'S'
        );

        expect($config->isStoreMode())->toBeTrue();
    });

    it('isStoreMode returns false when buildType is C', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            buildType: 'C'
        );

        expect($config->isStoreMode())->toBeFalse();
    });

    it('hasCustomBackground returns true for non-black path', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            backgroundPath: '/path/to/background.png'
        );

        expect($config->hasCustomBackground())->toBeTrue();
    });

    it('hasCustomBackground returns false for black path', function () {
        $config = new ApkBuildConfig(
            appId: 'com.example.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            backgroundPath: 'black'
        );

        expect($config->hasCustomBackground())->toBeFalse();
    });
});

describe('ApkBuildConfig fromArray', function () {
    it('fromArray maps snake_case keys', function () {
        $data = [
            'app_id' => 'com.test.app',
            'user_id' => '42',
            'app_name' => 'Snake App',
            'app_version' => '2.0',
            'websocket_url' => 'ws://host:9000',
        ];

        $config = ApkBuildConfig::fromArray($data);

        expect($config->appId)->toBe('com.test.app');
        expect($config->userId)->toBe('42');
        expect($config->appName)->toBe('Snake App');
        expect($config->appVersion)->toBe('2.0');
        expect($config->websocketUrl)->toBe('ws://host:9000');
    });

    it('fromArray maps lowercase alias keys', function () {
        $data = [
            'appid' => 'com.test.lower',
            'userid' => '43',
            'appname' => 'Lower App',
            'appversion' => '3.0',
            'websocketUrl' => 'ws://host:9001',
        ];

        $config = ApkBuildConfig::fromArray($data);

        expect($config->appId)->toBe('com.test.lower');
        expect($config->userId)->toBe('43');
        expect($config->appName)->toBe('Lower App');
        expect($config->appVersion)->toBe('3.0');
        expect($config->websocketUrl)->toBe('ws://host:9001');
    });
});
