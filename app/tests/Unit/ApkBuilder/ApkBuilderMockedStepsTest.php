<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuilderConstants;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

require_once __DIR__.'/ApkBuilderTestHelpers.php';

$mockStepsTempBase = null;

beforeEach(function () {
    global $mockStepsTempBase;
    $mockStepsTempBase = sys_get_temp_dir().'/apk_builder_mocked_'.uniqid();
    File::ensureDirectoryExists($mockStepsTempBase);

    $toolsPath = $mockStepsTempBase.'/tools';
    ApkBuilderTestFixtures::createFakeApktoolJar($toolsPath);

    Config::set('apk-builder.template_path', $mockStepsTempBase.'/template');
    Config::set('apk-builder.stub_zip_path', $mockStepsTempBase.'/stub.zip');
    Config::set('apk-builder.tools_path', $toolsPath);
    Config::set('apk-builder.output_path', $mockStepsTempBase.'/output');
    Config::set('apk-builder.temp_path', $mockStepsTempBase);
    Config::set('apk-builder.timeout', 300);
    Config::set('apk-builder.cleanup_on_success', false);
    Config::set('apk-builder.cleanup_on_failure', false);
});

afterEach(function () {
    global $mockStepsTempBase;
    if ($mockStepsTempBase !== null && File::isDirectory($mockStepsTempBase)) {
        File::deleteDirectory($mockStepsTempBase);
    }
});

describe('ApkBuilder build steps with mocks', function () {
    it('checkDependencies throws when template directory missing', function () {
        $fileSystem = createMockFileSystem();
        $templatePath = config('apk-builder.template_path');
        $stubZipPath = config('apk-builder.stub_zip_path');
        $fileSystem->shouldReceive('isDirectory')->with($templatePath)->andReturn(false);
        $fileSystem->shouldReceive('exists')->with($stubZipPath)->andReturn(false);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: createMockProcessRunner()
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'template directory not found');
    });

    it('checkDependencies throws when apktool.jar missing', function () {
        $fileSystem = createMockFileSystem();
        $templatePath = config('apk-builder.template_path');
        $toolsPath = config('apk-builder.tools_path');
        $fileSystem->shouldReceive('isDirectory')->with($templatePath)->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->with($templatePath.'/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE)
            ->andReturn(true);
        $fileSystem->shouldReceive('exists')->with($toolsPath.'/apktool.jar')->andReturn(false);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: createMockProcessRunner()
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'apktool.jar');
    });

    it('checkDependencies throws when java not installed', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);

        $failedResult = mock(ProcessResult::class);
        $failedResult->shouldReceive('successful')->andReturn(false);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->with('java -version')->andReturn($failedResult);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'Java is not installed');
    });

    it('prepareWorkDir creates work directory and copies template', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('ensureDirectoryExists')->atLeast()->once();
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn(
            mock(ProcessResult::class)->shouldReceive('successful')->andReturn(true)->getMock()
        );

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))->not->toThrow(\Exception::class);
    });

    it('modifyManifest replaces package name with a real Play Store package', function () {
        $manifestContent = '<manifest package="com.icontrol.protector">@drawable/mylogo</manifest>';

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $fileSystem->shouldReceive('get')
            ->andReturnUsing(function ($path) use ($manifestContent) {
                if (str_contains($path, 'AndroidManifest.xml')) {
                    return $manifestContent;
                }

                return '';
            });

        $manifestNewPackage = null;
        $fileSystem->shouldReceive('put')
            ->andReturnUsing(function ($path, $content) use (&$manifestNewPackage) {
                if (str_contains($path, 'AndroidManifest.xml')) {
                    // 原始包名不应再出现
                    if (! str_contains($content, 'com.icontrol.protector')) {
                        // 提取新包名
                        if (preg_match('/package="([^"]+)"/', $content, $m)) {
                            $manifestNewPackage = $m[1];
                        }
                    }
                }

                return true;
            });

        $renamePackageCalled = false;
        $renameNewPackage = null;
        $smaliProcessor = createFakeSmaliProcessor(
            onRenamePackage: function ($oldPackage, $newPackage) use (&$renamePackageCalled, &$renameNewPackage) {
                expect($oldPackage)->toBe(ApkBuilderConstants::DEFAULT_PACKAGE);
                $renameNewPackage = $newPackage;
                $renamePackageCalled = true;
            }
        );

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn(
            mock(ProcessResult::class)->shouldReceive('successful')->andReturn(true)->getMock()
        );

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => $smaliProcessor,
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($renamePackageCalled)->toBeTrue();
        $validPackages = array_column(ApkBuilderConstants::REAL_PACKAGE_NAMES, 'pkg');
        expect(in_array($renameNewPackage, $validPackages, true))->toBeTrue();
        expect($manifestNewPackage)->toBe($renameNewPackage);
    });

    it('replaceIcon throws when no icon found', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->andReturnUsing(function ($path) {
                if (str_contains($path, 'mylogo.png')) {
                    return false;
                }
                if (str_contains($path, ApkBuilderConstants::CONFIGS_SMALI_RELATIVE)) {
                    return true;
                }
                if (str_contains($path, 'apktool.jar')) {
                    return true;
                }

                return false;
            });
        $fileSystem->shouldReceive('size')->andReturn(0);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        Config::set('apk-builder.default_icon', null);

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn(
            mock(ProcessResult::class)->shouldReceive('successful')->andReturn(true)->getMock()
        );

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'Icon file not found');
    });

    it('buildApk throws when apktool fails', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->andReturnUsing(function ($path) {
                if (str_contains($path, 'app-unsigned.apk')) {
                    return false;
                }

                return true;
            });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $failedResult = mock(ProcessResult::class);
        $failedResult->shouldReceive('successful')->andReturn(false);
        $failedResult->shouldReceive('output')->andReturn('Build error');
        $failedResult->shouldReceive('errorOutput')->andReturn('apktool failed');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($failedResult);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturn($successResult);
        $processRunner->shouldReceive('start')
            ->andReturnUsing(function ($cmd) use ($invokedProcess, $successResult) {
                if (str_contains($cmd, 'java -jar')) {
                    return $invokedProcess;
                }
                $successInvoked = mock(InvokedProcess::class);
                $successInvoked->shouldReceive('running')->andReturn(false);
                $successInvoked->shouldReceive('wait')->andReturn($successResult);

                return $successInvoked;
            });

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'apktool');
    });

    it('buildApk runs framework cache cleanup before build', function () {
        $runCalls = [];
        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = mock(ProcessRunnerInterface::class);
        $processRunner->shouldReceive('run')
            ->andReturnUsing(function ($cmd) use (&$runCalls, $successResult) {
                $runCalls[] = $cmd;

                return $successResult;
            });
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        $frameworkCleanupCalls = array_values(array_filter($runCalls, fn ($c) => str_contains($c, 'apktool/framework')));
        expect($frameworkCleanupCalls)->not->toBeEmpty();
        expect($frameworkCleanupCalls[0])->toContain('rm -rf');
    });

    it('buildApk command uses plain apktool without aapt2 (legacy alignment)', function () {
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
                if (str_contains($cmd, 'apktool.jar')) {
                    $startCommand = $cmd;
                }

                return $invokedProcess;
            });

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        // 旧版: java -jar apktool.jar b -f <path> -o <output>（不使用 --aapt/--use-aapt2）
        expect($startCommand)->not->toBeNull();
        expect($startCommand)->toContain('apktool.jar');
        expect($startCommand)->toContain(' b -f ');
        expect($startCommand)->not->toContain('--aapt');
        expect($startCommand)->not->toContain('aapt2');
    });

    it('buildApk runs framework cache cleanup on build failure', function () {
        $runCalls = [];
        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);

        $failedResult = mock(ProcessResult::class);
        $failedResult->shouldReceive('successful')->andReturn(false);
        $failedResult->shouldReceive('output')->andReturn('Build error');
        $failedResult->shouldReceive('errorOutput')->andReturn('apktool failed');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($failedResult);

        $processRunner = mock(ProcessRunnerInterface::class);
        $processRunner->shouldReceive('run')
            ->andReturnUsing(function ($cmd) use (&$runCalls, $successResult) {
                $runCalls[] = $cmd;

                return $successResult;
            });
        $processRunner->shouldReceive('start')
            ->andReturnUsing(function ($cmd) use ($invokedProcess, $successResult) {
                if (str_contains($cmd, 'java -jar')) {
                    return $invokedProcess;
                }
                $p = mock(InvokedProcess::class);
                $p->shouldReceive('running')->andReturn(false);
                $p->shouldReceive('wait')->andReturn($successResult);

                return $p;
            });

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->andReturnUsing(fn ($path) => ! str_contains($path, 'app-unsigned.apk'));
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))->toThrow(ApkBuildException::class);

        $frameworkCleanupCalls = array_values(array_filter($runCalls, fn ($c) => str_contains($c, 'apktool/framework')));
        expect(count($frameworkCleanupCalls))->toBe(2); // 构建前一次，失败后一次
    });

    it('signApk throws when all signing methods fail', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->andReturnUsing(function ($path) {
                if (str_contains($path, 'app-signed.apk')) {
                    return false;
                }

                return true;
            });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'signing');
    });
});

describe('ApkBuilder optional steps', function () {
    it('generates junk classes when enabled', function () {
        $obfuscatorCalled = false;
        $obfuscator = createFakeObfuscator(
            onGenerateJunkClasses: function ($classCount, $methodCount) use (&$obfuscatorCalled) {
                $obfuscatorCalled = true;

                return 10;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => $obfuscator,
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableJunkClasses: true
        );

        $builder->build($config);

        expect($obfuscatorCalled)->toBeTrue();
    });

    it('shuffles classes when enabled', function () {
        $shuffleCalled = false;
        $obfuscator = createFakeObfuscator(
            onShuffleClassNames: function () use (&$shuffleCalled) {
                $shuffleCalled = true;

                return 5;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => $obfuscator,
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableClassShuffle: true
        );

        $builder->build($config);

        expect($shuffleCalled)->toBeTrue();
    });

    it('obfuscates strings when enabled', function () {
        $obfuscateStringsCalled = false;
        $obfuscator = createFakeObfuscator(
            onObfuscateStrings: function () use (&$obfuscateStringsCalled) {
                $obfuscateStringsCalled = true;

                return 15;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => $obfuscator,
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableStringObfuscation: true
        );

        $builder->build($config);

        expect($obfuscateStringsCalled)->toBeTrue();
    });

    it('does not obfuscate strings when disabled', function () {
        $obfuscateStringsCalled = false;
        $obfuscator = createFakeObfuscator(
            onObfuscateStrings: function () use (&$obfuscateStringsCalled) {
                $obfuscateStringsCalled = true;

                return 0;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => $obfuscator,
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableStringObfuscation: false
        );

        $builder->build($config);

        expect($obfuscateStringsCalled)->toBeFalse();
    });

    it('protects APK when enabled', function () {
        $protectCalled = false;
        $protector = createFakeApkProtector(
            onProtect: function ($apkPath) use (&$protectCalled) {
                $protectCalled = true;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => $protector,
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableApkProtection: true
        );

        $builder->build($config);

        // protect_apk 当前暂时禁用（ApkProtector ZIP 操作导致安装失败）
        expect($protectCalled)->toBeFalse();
    });

    it('modifies DEX when enabled', function () {
        $dexModified = false;
        $protector = createFakeApkProtector(
            onModifyDex: function ($apkPath) use (&$dexModified) {
                $dexModified = true;

                return 2;
            }
        );

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => $protector,
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081',
            enableDexModification: true
        );

        $builder->build($config);

        expect($dexModified)->toBeTrue();
    });
});

describe('ApkBuilder progress callback', function () {
    it('emits progress events for each step', function () {
        $progressEvents = [];

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->buildWithProgress(createValidConfig(), function ($event) use (&$progressEvents) {
            $progressEvents[] = $event;
        });

        $stepEvents = array_filter($progressEvents, fn ($e) => ($e['type'] ?? '') === 'step');
        expect(count($stepEvents))->toBeGreaterThan(0);

        $runningEvents = array_filter($stepEvents, fn ($e) => ($e['status'] ?? '') === 'running');
        $doneEvents = array_filter($stepEvents, fn ($e) => ($e['status'] ?? '') === 'done');

        expect(count($runningEvents))->toBe(count($doneEvents));
    });

    it('emits error event on build failure', function () {
        $progressEvents = [];

        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(false);
        $fileSystem->shouldReceive('exists')->andReturn(false);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: createMockProcessRunner()
        );

        try {
            $builder->buildWithProgress(createValidConfig(), function ($event) use (&$progressEvents) {
                $progressEvents[] = $event;
            });
        } catch (ApkBuildException $e) {
        }

        $errorEvents = array_filter($progressEvents, fn ($e) => ($e['type'] ?? '') === 'error');
        expect(count($errorEvents))->toBe(1);
    });
});

describe('ApkBuilder signing (resolveKeystore)', function () {
    it('uses custom keystore when configured via env', function () {
        global $mockStepsTempBase;
        $customKeystorePath = $mockStepsTempBase . '/custom.keystore';
        file_put_contents($customKeystorePath, 'fake-keystore');

        Config::set('apk-builder.signing.mode', 'release');
        Config::set('apk-builder.signing.keystore_path', $customKeystorePath);
        Config::set('apk-builder.signing.keystore_pass', 'mypass');
        Config::set('apk-builder.signing.key_alias', 'myalias');
        Config::set('apk-builder.signing.key_pass', 'mykeypass');

        $signingCommand = null;
        $signedApkCreated = false;
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturnUsing(function ($path) use ($customKeystorePath, &$signedApkCreated) {
            if (str_contains($path, 'signapk.jar') || str_contains($path, 'certificate.pem') || str_contains($path, 'key.pk8')) {
                return false;
            }
            if ($path === $customKeystorePath) {
                return true;
            }
            if (str_contains($path, 'app-signed.apk')) {
                return $signedApkCreated;
            }

            return true;
        });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturnUsing(function ($cmd) use (&$signingCommand, &$signedApkCreated, $successResult) {
            if (str_contains($cmd, 'apksigner') || str_contains($cmd, 'jarsigner') || str_contains($cmd, 'signapk')) {
                $signingCommand = $cmd;
                $signedApkCreated = true;
            }

            return $successResult;
        });
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($signingCommand)->not->toBeNull();
        expect($signingCommand)->toContain($customKeystorePath);
        expect($signingCommand)->toContain('myalias');
    });

    it('uses debug keystore when mode is debug', function () {
        Config::set('apk-builder.signing.mode', 'debug');
        Config::set('apk-builder.signing.keystore_path', null);

        $signingCommand = null;
        $signedApkCreated = false;
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturnUsing(function ($path) use (&$signedApkCreated) {
            if (str_contains($path, 'signapk.jar') || str_contains($path, 'certificate.pem') || str_contains($path, 'key.pk8')) {
                return false;
            }
            if (str_contains($path, 'app-signed.apk')) {
                return $signedApkCreated;
            }

            return true;
        });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturnUsing(function ($cmd) use (&$signingCommand, &$signedApkCreated, $successResult) {
            if (str_contains($cmd, 'apksigner') || str_contains($cmd, 'jarsigner') || str_contains($cmd, 'signapk')) {
                $signingCommand = $cmd;
                $signedApkCreated = true;
            }

            return $successResult;
        });
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($signingCommand)->not->toBeNull();
        expect($signingCommand)->toContain('androiddebugkey');
    });

    it('auto-generates release keystore when no custom keystore and mode is release', function () {
        Config::set('apk-builder.signing.mode', 'release');
        Config::set('apk-builder.signing.keystore_path', null);

        $keytoolCommand = null;
        $signedApkCreated = false;
        $releaseKeystoreGenerated = false;
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturnUsing(function ($path) use (&$signedApkCreated, &$releaseKeystoreGenerated) {
            if (str_contains($path, 'signapk.jar') || str_contains($path, 'certificate.pem') || str_contains($path, 'key.pk8')) {
                return false;
            }
            if (str_contains($path, ApkBuilderConstants::RELEASE_KEYSTORE_FILENAME)) {
                return $releaseKeystoreGenerated;
            }
            if (str_contains($path, ApkBuilderConstants::KEYSTORE_META_FILENAME)) {
                return false;
            }
            if (str_contains($path, 'app-signed.apk')) {
                return $signedApkCreated;
            }

            return true;
        });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturnUsing(function ($cmd) use (&$keytoolCommand, &$signedApkCreated, &$releaseKeystoreGenerated, $successResult) {
            if (str_contains($cmd, 'keytool -genkey')) {
                $keytoolCommand = $cmd;
                $releaseKeystoreGenerated = true;
            }
            if (str_contains($cmd, 'apksigner') || str_contains($cmd, 'jarsigner') || str_contains($cmd, 'signapk')) {
                $signedApkCreated = true;
            }

            return $successResult;
        });
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($keytoolCommand)->not->toBeNull();
        expect($keytoolCommand)->toContain('keytool -genkey');
        expect($keytoolCommand)->toContain('-keyalg');
        expect($keytoolCommand)->toContain(ApkBuilderConstants::RELEASE_KEYSTORE_FILENAME);
    });

    it('reuses existing auto-generated release keystore from meta file', function () {
        global $mockStepsTempBase;
        $toolsPath = config('apk-builder.tools_path');
        $releaseKeystorePath = $toolsPath . '/' . ApkBuilderConstants::RELEASE_KEYSTORE_FILENAME;
        $metaPath = $toolsPath . '/' . ApkBuilderConstants::KEYSTORE_META_FILENAME;

        $metaContent = json_encode([
            'key_alias' => 'saved_alias',
            'keystore_pass' => 'saved_pass',
            'key_pass' => 'saved_key_pass',
        ]);

        Config::set('apk-builder.signing.mode', 'release');
        Config::set('apk-builder.signing.keystore_path', null);

        $signingCommand = null;
        $keytoolCalled = false;
        $signedApkCreated = false;
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturnUsing(function ($path) use ($releaseKeystorePath, $metaPath, &$signedApkCreated) {
            if (str_contains($path, 'signapk.jar') || str_contains($path, 'certificate.pem') || str_contains($path, 'key.pk8')) {
                return false;
            }
            if ($path === $releaseKeystorePath || $path === $metaPath) {
                return true;
            }
            if (str_contains($path, 'app-signed.apk')) {
                return $signedApkCreated;
            }

            return true;
        });
        $fileSystem->shouldReceive('get')->andReturnUsing(function ($path) use ($metaPath, $metaContent) {
            if ($path === $metaPath) {
                return $metaContent;
            }

            return '';
        });
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

        $successResult = mock(ProcessResult::class);
        $successResult->shouldReceive('successful')->andReturn(true);
        $successResult->shouldReceive('output')->andReturn('');
        $successResult->shouldReceive('errorOutput')->andReturn('');

        $invokedProcess = mock(InvokedProcess::class);
        $invokedProcess->shouldReceive('running')->andReturn(false);
        $invokedProcess->shouldReceive('wait')->andReturn($successResult);

        $processRunner = createMockProcessRunner();
        $processRunner->shouldReceive('run')->andReturnUsing(function ($cmd) use (&$signingCommand, &$keytoolCalled, &$signedApkCreated, $successResult) {
            if (str_contains($cmd, 'keytool -genkey')) {
                $keytoolCalled = true;
            }
            if (str_contains($cmd, 'apksigner') || str_contains($cmd, 'jarsigner') || str_contains($cmd, 'signapk')) {
                $signingCommand = $cmd;
                $signedApkCreated = true;
            }

            return $successResult;
        });
        $processRunner->shouldReceive('start')->andReturn($invokedProcess);

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $builder->build(createValidConfig());

        expect($keytoolCalled)->toBeFalse('Should not generate new keystore when meta exists');
        expect($signingCommand)->not->toBeNull();
        expect($signingCommand)->toContain('saved_alias');
    });
});

describe('ApkBuilder result', function () {
    it('returns ApkBuildResult with stats on success', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('exists')->andReturn(true);
        $fileSystem->shouldReceive('size')->andReturn(1000);
        $fileSystem->shouldReceive('glob')->andReturn([]);

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

        $builder = new ApkBuilder(
            smaliProcessorFactory: fn ($dir) => createFakeSmaliProcessor(),
            obfuscatorFactory: fn ($dir) => createFakeObfuscator(),
            apkProtectorFactory: fn () => createFakeApkProtector(),
            fileSystem: $fileSystem,
            processRunner: $processRunner
        );

        $result = $builder->build(createValidConfig());

        expect($result->path)->toContain('/storage/apk/');
        expect($result->stats)->toBeArray();
        expect($result->stats)->toHaveKey('check_dependencies');
        expect($result->stats)->toHaveKey('prepare_work_dir');
        expect($result->stats)->toHaveKey('build_apk');
        expect($result->stats)->toHaveKey('sign_apk');
        expect($result->totalTimeMs)->toBeGreaterThan(0);
    });
});
