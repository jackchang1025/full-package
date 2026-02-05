<?php

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilderConstants;
use App\Services\ApkBuilder\Encryptor;
use App\Services\ApkBuilder\SmaliProcessor;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

describe('SmaliProcessor modifyConfig', function () {
    it('modifyConfig throws when My_Configs.smali missing', function () {
        $buildDir = sys_get_temp_dir() . '/apk_builder_test_' . uniqid();
        File::ensureDirectoryExists($buildDir);

        $processor = new SmaliProcessor($buildDir);
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );
        $encryptor = new Encryptor();

        expect(fn() => $processor->modifyConfig($config, 'key123', $encryptor))
            ->toThrow(ApkBuildException::class);

        File::deleteDirectory($buildDir);
    });

    it('modifyConfig replaces placeholders in smali', function () {
        $content = '[Client_N]
[USER_DOM]
[log-title]
[AST-PAS]
[OBFS]
[NAME>LNK>ID!]
const-string v1, "wss://"';
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir($content);

        try {
            $processor = new SmaliProcessor($buildDir);
            $config = new ApkBuildConfig(
                appId: 'com.test.app',
                userId: 'u1',
                appName: 'TestApp',
                appVersion: '1.0',
                websocketUrl: 'ws://host.example:9000',
                clientName: 'MyClient',
                loginTitle: 'Welcome'
            );
            $encryptor = new Encryptor();

            $processor->modifyConfig($config, 'asset_key_123', $encryptor);

            $smaliPath = $buildDir . '/' . ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;
            $result = File::get($smaliPath);

            expect($result)->toContain('MyClient');
            expect($result)->toContain('host.example:9000');
            expect($result)->toContain('Welcome');
            expect($result)->toContain('asset_key_123');
            expect($result)->toContain('MyClient>u1>com.test.app');
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('modifyConfig escapes special chars in strings', function () {
        $content = '[Client_N]';
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir($content);

        try {
            $processor = new SmaliProcessor($buildDir);
            $config = new ApkBuildConfig(
                appId: 'com.test.app',
                userId: '1',
                appName: 'Test',
                appVersion: '1.0',
                websocketUrl: 'ws://localhost:8081',
                clientName: 'Test "quoted" and \backslash'
            );
            $encryptor = new Encryptor();

            $processor->modifyConfig($config, 'key', $encryptor);

            $smaliPath = $buildDir . '/' . ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;
            $result = File::get($smaliPath);

            expect($result)->toContain('\\"');
            expect($result)->toContain('\\\\');
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});

describe('SmaliProcessor renamePackage', function () {
    it('renamePackage moves and updates references', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDirWithPackage(ApkBuilderConstants::DEFAULT_PACKAGE);

        try {
            $processor = new SmaliProcessor($buildDir);
            $processor->renamePackage(ApkBuilderConstants::DEFAULT_PACKAGE, 'com.new.package');

            $oldPath = $buildDir . '/smali/com/icontrol/protector';
            $newPath = $buildDir . '/smali/com/new/package';

            expect(File::isDirectory($oldPath))->toBeFalse();
            expect(File::isDirectory($newPath))->toBeTrue();
            expect(File::exists($newPath . '/SomeClass.smali'))->toBeTrue();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});
