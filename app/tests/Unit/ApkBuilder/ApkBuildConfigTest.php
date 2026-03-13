<?php

use App\Services\ApkBuilder\ApkBuildConfig;
use Illuminate\Support\Facades\Config;

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

describe('ApkBuildConfig enableStringObfuscation', function () {
    it('defaults enableStringObfuscation to false in constructor', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect($config->enableStringObfuscation)->toBeFalse();
    });

    it('accepts enableStringObfuscation as true in constructor', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableStringObfuscation: true
        );

        expect($config->enableStringObfuscation)->toBeTrue();
    });

    it('fromArray reads enable_string_obfuscation from data', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
            'enable_string_obfuscation' => true,
        ]);

        expect($config->enableStringObfuscation)->toBeTrue();
    });

    it('fromArray reads enableStringObfuscation camelCase alias', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
            'enableStringObfuscation' => true,
        ]);

        expect($config->enableStringObfuscation)->toBeTrue();
    });

    it('toArray includes enable_string_obfuscation', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableStringObfuscation: true
        );

        $array = $config->toArray();

        expect($array)->toHaveKey('enable_string_obfuscation');
        expect($array['enable_string_obfuscation'])->toBeTrue();
    });
});

describe('ApkBuildConfig fromArray reads config defaults', function () {
    it('reads protection defaults from config when keys missing in data', function () {
        Config::set('apk-builder.protection.enable_junk_classes', true);
        Config::set('apk-builder.protection.enable_class_shuffle', true);
        Config::set('apk-builder.protection.enable_string_obfuscation', true);
        Config::set('apk-builder.protection.enable_apk_protection', true);
        Config::set('apk-builder.protection.enable_dex_modification', true);
        Config::set('apk-builder.protection.junk_class_count', 100);
        Config::set('apk-builder.protection.junk_method_count', 20);

        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
        ]);

        expect($config->enableJunkClasses)->toBeTrue();
        expect($config->enableClassShuffle)->toBeTrue();
        expect($config->enableStringObfuscation)->toBeTrue();
        expect($config->enableApkProtection)->toBeTrue();
        expect($config->enableDexModification)->toBeTrue();
        expect($config->junkClassCount)->toBe(100);
        expect($config->junkMethodCount)->toBe(20);
    });

    it('data array values override config defaults', function () {
        Config::set('apk-builder.protection.enable_junk_classes', true);
        Config::set('apk-builder.protection.enable_class_shuffle', true);
        Config::set('apk-builder.protection.enable_string_obfuscation', true);

        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
            'enable_junk_classes' => false,
            'enable_class_shuffle' => false,
            'enable_string_obfuscation' => false,
        ]);

        expect($config->enableJunkClasses)->toBeFalse();
        expect($config->enableClassShuffle)->toBeFalse();
        expect($config->enableStringObfuscation)->toBeFalse();
    });

    it('falls back to hardcoded defaults when config not set and keys missing', function () {
        Config::set('apk-builder.protection.enable_junk_classes', null);
        Config::set('apk-builder.protection.enable_class_shuffle', null);
        Config::set('apk-builder.protection.enable_string_obfuscation', null);
        Config::set('apk-builder.protection.enable_apk_protection', null);
        Config::set('apk-builder.protection.enable_dex_modification', null);

        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
        ]);

        // config() returns null, (bool) null = false, which is the hardcoded fallback
        expect($config->enableJunkClasses)->toBeFalse();
        expect($config->enableClassShuffle)->toBeFalse();
        expect($config->enableStringObfuscation)->toBeFalse();
        expect($config->enableApkProtection)->toBeFalse();
        expect($config->enableDexModification)->toBeFalse();
    });
});

describe('ApkBuildConfig enableAutoWakeScreen', function () {
    it('defaults to true in constructor', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect($config->enableAutoWakeScreen)->toBeTrue();
    });

    it('accepts false in constructor', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableAutoWakeScreen: false
        );

        expect($config->enableAutoWakeScreen)->toBeFalse();
    });

    it('fromArray reads enable_auto_wake_screen', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
            'enable_auto_wake_screen' => false,
        ]);

        expect($config->enableAutoWakeScreen)->toBeFalse();
    });

    it('fromArray reads enableAutoWakeScreen camelCase', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
            'enableAutoWakeScreen' => false,
        ]);

        expect($config->enableAutoWakeScreen)->toBeFalse();
    });

    it('fromArray falls back to config default', function () {
        Config::set('apk-builder.protection.enable_auto_wake_screen', false);

        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8081',
        ]);

        expect($config->enableAutoWakeScreen)->toBeFalse();
    });

    it('toArray exports enable_auto_wake_screen', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableAutoWakeScreen: false
        );

        $array = $config->toArray();

        expect($array)->toHaveKey('enable_auto_wake_screen');
        expect($array['enable_auto_wake_screen'])->toBeFalse();
    });
});
