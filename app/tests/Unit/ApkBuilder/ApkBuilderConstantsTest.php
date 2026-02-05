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

    it('PROCESS_POLL_INTERVAL_US is 100ms in microseconds', function () {
        expect(ApkBuilderConstants::PROCESS_POLL_INTERVAL_US)->toBe(100_000);
    });

    it('defines file path constants', function () {
        expect(ApkBuilderConstants::MANIFEST_PATH)->toBe('/AndroidManifest.xml');
        expect(ApkBuilderConstants::STRINGS_XML_PATH)->toBe('/res/values/strings.xml');
        expect(ApkBuilderConstants::PUBLIC_XML_PATH)->toBe('/res/values/public.xml');
        expect(ApkBuilderConstants::APKTOOL_YML_PATH)->toBe('/apktool.yml');
        expect(ApkBuilderConstants::ASSETS_PATH)->toBe('/assets');
        expect(ApkBuilderConstants::XML_DIR_PATH)->toBe('/res/xml');
        expect(ApkBuilderConstants::BLACKUI_PATH)->toBe('/res/drawable/blackui.png');
    });

    it('defines icon filename constants', function () {
        expect(ApkBuilderConstants::ICON_FILENAME)->toBe('mylogo.png');
        expect(ApkBuilderConstants::APP_ICON_FILENAME)->toBe('app_icon.png');
    });

    it('defines default accessibility XML', function () {
        expect(ApkBuilderConstants::DEFAULT_ACCESSIBILITY_XML)->toContain('<?xml version="1.0"');
        expect(ApkBuilderConstants::DEFAULT_ACCESSIBILITY_XML)->toContain('accessibility-service');
        expect(ApkBuilderConstants::DEFAULT_ACCESSIBILITY_XML)->toContain('typeAllMask');
    });

    it('defines ZIP error messages for all common error codes', function () {
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toBeArray();
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_EXISTS);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_INCONS);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_INVAL);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_MEMORY);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_NOENT);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_NOZIP);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_OPEN);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_READ);
        expect(ApkBuilderConstants::ZIP_ERROR_MESSAGES)->toHaveKey(\ZipArchive::ER_SEEK);
    });

    it('ZIP error messages are in Chinese', function () {
        foreach (ApkBuilderConstants::ZIP_ERROR_MESSAGES as $code => $message) {
            expect($message)->toBeString();
            expect(strlen($message))->toBeGreaterThan(0);
        }
    });
});
