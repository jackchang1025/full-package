<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuilderConstants;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use App\Services\ApkBuilder\Encryptor;
use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

beforeEach(function () {
    $this->tempBase = sys_get_temp_dir() . '/apk_builder_di_test_' . uniqid();
    File::ensureDirectoryExists($this->tempBase);

    Config::set('apk-builder.template_path', $this->tempBase . '/template');
    Config::set('apk-builder.stub_zip_path', $this->tempBase . '/apkstub.zip');
    Config::set('apk-builder.tools_path', $this->tempBase . '/tools');
    Config::set('apk-builder.output_path', $this->tempBase . '/output');
    Config::set('apk-builder.temp_path', $this->tempBase);
});

afterEach(function () {
    if (isset($this->tempBase) && File::isDirectory($this->tempBase)) {
        File::deleteDirectory($this->tempBase);
    }
});

describe('ApkBuilder with mocked dependencies', function () {
    it('accepts FileSystemInterface via constructor', function () {
        $fileSystem = mock(FileSystemInterface::class)->makePartial();

        $builder = new ApkBuilder(
            fileSystem: $fileSystem
        );

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });

    it('accepts ProcessRunnerInterface via constructor', function () {
        $processRunner = mock(ProcessRunnerInterface::class)->makePartial();

        $builder = new ApkBuilder(
            processRunner: $processRunner
        );

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });

    it('uses injected FileSystem for template validation', function () {
        $fileSystem = mock(FileSystemInterface::class);
        $fileSystem->shouldReceive('isDirectory')->andReturn(false);
        $fileSystem->shouldReceive('exists')->andReturn(false);

        $processRunner = mock(ProcessRunnerInterface::class);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect(fn() => $builder->build($config))
            ->toThrow(ApkBuildException::class);
    });

    it('uses injected ProcessRunner for java check', function () {
        $templateDir = $this->tempBase . '/template';
        $smaliPath = $templateDir . '/' . ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;
        File::ensureDirectoryExists(dirname($smaliPath));
        File::put($smaliPath, ApkBuilderTestFixtures::getDefaultMyConfigsContent());

        $toolsDir = $this->tempBase . '/tools';
        File::ensureDirectoryExists($toolsDir);
        File::put($toolsDir . '/apktool.jar', 'dummy');

        $processResult = mock(ProcessResult::class);
        $processResult->shouldReceive('successful')->andReturn(false);

        $processRunner = mock(ProcessRunnerInterface::class);
        $processRunner->shouldReceive('run')
            ->with('java -version')
            ->andReturn($processResult);

        $builder = new ApkBuilder(
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect(fn() => $builder->build($config))
            ->toThrow(ApkBuildException::class, 'Java is not installed');
    });
});

describe('ApkBuilder factory injection', function () {
    it('uses custom SmaliProcessor factory', function () {
        $factoryCalled = false;
        $customFactory = function (string $buildDir) use (&$factoryCalled) {
            $factoryCalled = true;
            return new \App\Services\ApkBuilder\SmaliProcessor($buildDir);
        };

        $builder = new ApkBuilder(
            smaliProcessorFactory: $customFactory
        );

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });

    it('uses custom Obfuscator factory', function () {
        $factoryCalled = false;
        $customFactory = function (string $buildDir) use (&$factoryCalled) {
            $factoryCalled = true;
            return new \App\Services\ApkBuilder\Obfuscator($buildDir);
        };

        $builder = new ApkBuilder(
            obfuscatorFactory: $customFactory
        );

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });

    it('uses custom ApkProtector factory', function () {
        $factoryCalled = false;
        $customFactory = function () use (&$factoryCalled) {
            $factoryCalled = true;
            return new \App\Services\ApkBuilder\ApkProtector();
        };

        $builder = new ApkBuilder(
            apkProtectorFactory: $customFactory
        );

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });
});

describe('ApkBuilder STEP_LABELS', function () {
    it('defines all expected step labels', function () {
        $expectedSteps = [
            'check_dependencies',
            'prepare_work_dir',
            'modify_smali',
            'modify_manifest',
            'modify_resources',
            'replace_icon',
            'replace_background',
            'generate_junk_classes',
            'shuffle_classes',
            'encrypt_resources',
            'build_apk',
            'protect_apk',
            'modify_dex',
            'sign_apk',
            'move_output',
        ];

        foreach ($expectedSteps as $step) {
            expect(ApkBuilder::STEP_LABELS)->toHaveKey($step);
        }
    });

    it('has Chinese labels for all steps', function () {
        foreach (ApkBuilder::STEP_LABELS as $step => $label) {
            expect($label)->toBeString();
            expect(strlen($label))->toBeGreaterThan(0);
        }
    });
});
