<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuilderConstants;
use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

require_once __DIR__.'/ApkBuilderTestHelpers.php';

beforeEach(function () {
    $this->tempBase = sys_get_temp_dir().'/apk_ensure_aapt2_'.uniqid();
    File::ensureDirectoryExists($this->tempBase);
});

afterEach(function () {
    if (isset($this->tempBase) && File::isDirectory($this->tempBase)) {
        File::deleteDirectory($this->tempBase);
    }
});

describe('ApkBuilder ensureAapt2Extracted', function () {
    it('uses existing aapt2 when already present in tools dir', function () {
        $toolsPath = $this->tempBase.'/tools';
        ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);
        ApkBuilderTestFixtures::createFakeAapt2($toolsPath);

        Config::set('apk-builder.template_path', $this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template/'.dirname(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE));
        File::put($this->tempBase.'/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE, 'smali');
        Config::set('apk-builder.stub_zip_path', $this->tempBase.'/stub.zip');
        Config::set('apk-builder.tools_path', $toolsPath);
        Config::set('apk-builder.output_path', $this->tempBase.'/output');
        Config::set('apk-builder.temp_path', $this->tempBase);
        Config::set('apk-builder.timeout', 300);
        Config::set('apk-builder.cleanup_on_success', false);
        Config::set('apk-builder.cleanup_on_failure', false);

        $startCommand = null;
        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturn($successResult);
        $processRunner->shouldReceive('start')
            ->andReturnUsing(function ($cmd) use (&$startCommand, $invokedProcess) {
                $startCommand = $cmd;

                return $invokedProcess;
            });

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($startCommand)->toContain('--aapt');
        expect($startCommand)->toContain($toolsPath.'/aapt2');
    });

    it('extracts aapt2 from apktool.jar when not present', function () {
        $toolsPath = $this->tempBase.'/tools';
        ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);

        Config::set('apk-builder.template_path', $this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template/'.dirname(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE));
        File::put($this->tempBase.'/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE, 'smali');
        Config::set('apk-builder.stub_zip_path', $this->tempBase.'/stub.zip');
        Config::set('apk-builder.tools_path', $toolsPath);
        Config::set('apk-builder.output_path', $this->tempBase.'/output');
        Config::set('apk-builder.temp_path', $this->tempBase);
        Config::set('apk-builder.timeout', 300);
        Config::set('apk-builder.cleanup_on_success', false);
        Config::set('apk-builder.cleanup_on_failure', false);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturn($successResult);
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);
        $fileSystem->shouldReceive('ensureDirectoryExists')->andReturnUsing(function ($path) {
        });

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(File::exists($toolsPath.'/aapt2'))->toBeFalse();

        $builder->build(createValidConfig());

        expect(File::exists($toolsPath.'/aapt2'))->toBeTrue();
        expect(File::exists($toolsPath.'/aapt2') && is_executable($toolsPath.'/aapt2'))->toBeTrue();
    });

    it('throws when apktool.jar cannot be opened', function () {
        $toolsPath = $this->tempBase.'/tools';
        File::ensureDirectoryExists($toolsPath);
        File::put($toolsPath.'/apktool.jar', 'not a valid zip');

        Config::set('apk-builder.template_path', $this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template/'.dirname(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE));
        File::put($this->tempBase.'/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE, 'smali');
        Config::set('apk-builder.stub_zip_path', $this->tempBase.'/stub.zip');
        Config::set('apk-builder.tools_path', $toolsPath);
        Config::set('apk-builder.output_path', $this->tempBase.'/output');
        Config::set('apk-builder.temp_path', $this->tempBase);
        Config::set('apk-builder.timeout', 300);
        Config::set('apk-builder.cleanup_on_success', false);
        Config::set('apk-builder.cleanup_on_failure', false);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturn($successResult);
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, '无法打开 apktool.jar 来提取 aapt2');
    });

    it('throws when aapt2 entry not found in apktool.jar', function () {
        $toolsPath = $this->tempBase.'/tools';
        File::ensureDirectoryExists($toolsPath);
        $zip = new \ZipArchive;
        $zip->open($toolsPath.'/apktool.jar', \ZipArchive::CREATE | \ZipArchive::OVERWRITE);
        $zip->addFromString('other/file.txt', 'dummy');
        $zip->close();

        Config::set('apk-builder.template_path', $this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template');
        File::ensureDirectoryExists($this->tempBase.'/template/'.dirname(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE));
        File::put($this->tempBase.'/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE, 'smali');
        Config::set('apk-builder.stub_zip_path', $this->tempBase.'/stub.zip');
        Config::set('apk-builder.tools_path', $toolsPath);
        Config::set('apk-builder.output_path', $this->tempBase.'/output');
        Config::set('apk-builder.temp_path', $this->tempBase);
        Config::set('apk-builder.timeout', 300);
        Config::set('apk-builder.cleanup_on_success', false);
        Config::set('apk-builder.cleanup_on_failure', false);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturn($successResult);
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $entryName = PHP_INT_SIZE === 8 ? 'prebuilt/linux/aapt2_64' : 'prebuilt/linux/aapt2';

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, "apktool.jar 中未找到 {$entryName}");
    });
});
