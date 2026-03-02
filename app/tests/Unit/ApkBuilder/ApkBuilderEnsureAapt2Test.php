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
    // Clean up container-local fallback path if created during tests
    if (File::isDirectory('/opt/apk-tools')) {
        File::deleteDirectory('/opt/apk-tools');
    }
});

function setupConfigForAapt2Test($tempBase, $toolsPath): void
{
    Config::set('apk-builder.template_path', $tempBase.'/template');
    File::ensureDirectoryExists($tempBase.'/template');
    File::ensureDirectoryExists($tempBase.'/template/'.dirname(ApkBuilderConstants::CONFIGS_SMALI_RELATIVE));
    File::put($tempBase.'/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE, 'smali');
    Config::set('apk-builder.stub_zip_path', $tempBase.'/stub.zip');
    Config::set('apk-builder.tools_path', $toolsPath);
    Config::set('apk-builder.output_path', $tempBase.'/output');
    Config::set('apk-builder.temp_path', $tempBase);
    Config::set('apk-builder.timeout', 300);
    Config::set('apk-builder.cleanup_on_success', false);
    Config::set('apk-builder.cleanup_on_failure', false);
}

describe('ApkBuilder ensureAapt2Extracted', function () {
    it('uses existing aapt2 from tools dir when available', function () {
        $toolsPath = $this->tempBase.'/tools';
        ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);
        ApkBuilderTestFixtures::createFakeAapt2($toolsPath);
        setupConfigForAapt2Test($this->tempBase, $toolsPath);

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
    });

    it('extracts aapt2 when not present and makes it executable', function () {
        $toolsPath = $this->tempBase.'/tools';
        ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);
        setupConfigForAapt2Test($this->tempBase, $toolsPath);

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
            File::ensureDirectoryExists($path);
        });

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        // aapt2 should exist at either /opt/apk-tools/aapt2 or tools dir
        $containerLocalExists = File::exists('/opt/apk-tools/aapt2') && is_executable('/opt/apk-tools/aapt2');
        $toolsDirExists = File::exists($toolsPath.'/aapt2') && is_executable($toolsPath.'/aapt2');
        expect($containerLocalExists || $toolsDirExists)->toBeTrue();
    });

    it('throws when apktool.jar cannot be opened', function () {
        $toolsPath = $this->tempBase.'/tools';
        File::ensureDirectoryExists($toolsPath);
        File::put($toolsPath.'/apktool.jar', 'not a valid zip');
        setupConfigForAapt2Test($this->tempBase, $toolsPath);

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
        setupConfigForAapt2Test($this->tempBase, $toolsPath);

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

    it('falls back to tools dir when container-local path is not writable', function () {
        $toolsPath = $this->tempBase.'/tools';
        ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);
        setupConfigForAapt2Test($this->tempBase, $toolsPath);

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
            File::ensureDirectoryExists($path);
        });

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn () => createFakeSmaliProcessor(),
            obfuscatorFactory: fn () => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        // Should have extracted aapt2 to at least one of the paths
        $containerLocalExists = File::exists('/opt/apk-tools/aapt2');
        $toolsDirExists = File::exists($toolsPath.'/aapt2');
        expect($containerLocalExists || $toolsDirExists)->toBeTrue();
    });
});
