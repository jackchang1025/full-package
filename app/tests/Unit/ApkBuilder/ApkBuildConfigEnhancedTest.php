<?php

use App\Services\ApkBuilder\ApkBuildConfig;

describe('ApkBuildConfig enhanced protection options', function () {
    it('parses new boolean options from array', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
            'enable_fake_encryption' => true,
            'enable_eocd_tampering' => true,
            'enable_path_traversal_entries' => true,
            'enable_unknown_compression' => true,
            'enable_full_string_encryption' => true,
            'enable_fake_components' => true,
            'enable_multi_package_junk' => true,
        ]);

        expect($config->enableFakeEncryption)->toBeTrue();
        expect($config->enableEocdTampering)->toBeTrue();
        expect($config->enablePathTraversalEntries)->toBeTrue();
        expect($config->enableUnknownCompression)->toBeTrue();
        expect($config->enableFullStringEncryption)->toBeTrue();
        expect($config->enableFakeComponents)->toBeTrue();
        expect($config->enableMultiPackageJunk)->toBeTrue();
    });

    it('defaults new options to false', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
        ]);

        expect($config->enableFakeEncryption)->toBeFalse();
        expect($config->enableEocdTampering)->toBeFalse();
        expect($config->enablePathTraversalEntries)->toBeFalse();
        expect($config->enableUnknownCompression)->toBeFalse();
        expect($config->enableFullStringEncryption)->toBeFalse();
        expect($config->enableFakeComponents)->toBeFalse();
        expect($config->enableMultiPackageJunk)->toBeTrue();
    });

    it('parses integer options with defaults', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
        ]);

        expect($config->fakeEntryCount)->toBe(120);
        expect($config->fakeComponentCount)->toBe(28);
    });

    it('parses custom integer values', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
            'fake_entry_count' => 200,
            'fake_component_count' => 50,
        ]);

        expect($config->fakeEntryCount)->toBe(200);
        expect($config->fakeComponentCount)->toBe(50);
    });

    it('validates fake_entry_count range', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
            'fake_entry_count' => 1000,
        ]);

        $errors = $config->validate();
        expect($errors)->toContain('fake_entry_count must be between 10 and 500');
    });

    it('validates fake_component_count range', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
            'fake_component_count' => 2,
        ]);

        $errors = $config->validate();
        expect($errors)->toContain('fake_component_count must be between 5 and 100');
    });

    it('serializes new options in toArray', function () {
        $config = ApkBuildConfig::fromArray([
            'app_id' => 'com.test.app',
            'user_id' => '1',
            'app_name' => 'Test',
            'app_version' => '1.0',
            'websocket_url' => 'ws://localhost:8080',
            'enable_fake_encryption' => true,
            'fake_entry_count' => 200,
        ]);

        $array = $config->toArray();

        expect($array['enable_fake_encryption'])->toBeTrue();
        expect($array['fake_entry_count'])->toBe(200);
        expect($array['enable_eocd_tampering'])->toBeFalse();
        expect($array['fake_component_count'])->toBe(28);
    });
});
