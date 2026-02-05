<?php

use App\Services\ApkBuilder\ApkBuilderConstants;

describe('ApkBuilderConstants', function () {
    it('defines required constants', function () {
        expect(ApkBuilderConstants::DEFAULT_PACKAGE)->toBe('com.icontrol.protector');
        expect(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE)->toContain('My_Configs.smali');
        expect(ApkBuilderConstants::MIN_ASSET_FILE_SIZE)->toBe(100);
        expect(ApkBuilderConstants::HEARTBEAT_INTERVAL_SEC)->toBe(10);
        expect(ApkBuilderConstants::FILE_COPY_HEARTBEAT_INTERVAL)->toBe(100);
    });

    it('SMALI_DIRS contains expected directories', function () {
        expect(ApkBuilderConstants::SMALI_DIRS)->toContain('smali');
        expect(ApkBuilderConstants::SMALI_DIRS)->toContain('smali_classes2');
    });

    it('DRAWABLE_DIRS contains expected directories', function () {
        expect(ApkBuilderConstants::DRAWABLE_DIRS)->toContain('drawable');
        expect(ApkBuilderConstants::DRAWABLE_DIRS)->toContain('drawable-hdpi');
    });

    it('APK file name constants are defined', function () {
        expect(ApkBuilderConstants::APK_UNSIGNED)->toBe('app-unsigned.apk');
        expect(ApkBuilderConstants::APK_ALIGNED)->toBe('app-aligned.apk');
        expect(ApkBuilderConstants::APK_SIGNED)->toBe('app-signed.apk');
    });
});
