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

function createValidConfig(): ApkBuildConfig
{
    return new ApkBuildConfig(
        appId: 'com.test.app',
        userId: '1',
        appName: 'Test App',
        appVersion: '1.0',
        websocketUrl: 'ws://localhost:8081'
    );
}

function createMockFileSystem(): FileSystemInterface
{
    $mock = mock(FileSystemInterface::class);
    $mock->shouldReceive('isDirectory')->andReturn(true)->byDefault();
    $mock->shouldReceive('exists')->andReturn(true)->byDefault();
    $mock->shouldReceive('ensureDirectoryExists')->andReturn(null)->byDefault();
    $mock->shouldReceive('get')->andReturn('')->byDefault();
    $mock->shouldReceive('put')->andReturn(true)->byDefault();
    $mock->shouldReceive('copy')->andReturn(true)->byDefault();
    $mock->shouldReceive('delete')->andReturn(true)->byDefault();
    $mock->shouldReceive('deleteDirectory')->andReturn(true)->byDefault();
    $mock->shouldReceive('size')->andReturn(1000)->byDefault();
    $mock->shouldReceive('glob')->andReturn([])->byDefault();

    return $mock;
}

function createMockProcessRunner(): ProcessRunnerInterface
{
    $successResult = mock(ProcessResult::class);
    $successResult->shouldReceive('successful')->andReturn(true)->byDefault();
    $successResult->shouldReceive('output')->andReturn('')->byDefault();
    $successResult->shouldReceive('errorOutput')->andReturn('')->byDefault();

    $invokedProcess = mock(InvokedProcess::class);
    $invokedProcess->shouldReceive('running')->andReturn(false)->byDefault();
    $invokedProcess->shouldReceive('wait')->andReturn($successResult)->byDefault();

    $mock = mock(ProcessRunnerInterface::class);
    $mock->shouldReceive('run')->andReturn($successResult)->byDefault();
    $mock->shouldReceive('start')->andReturn($invokedProcess)->byDefault();

    return $mock;
}

/**
 * Create a test double for SmaliProcessor (final class, cannot be mocked).
 * Returns an anonymous class with the same public interface.
 */
function createFakeSmaliProcessor(?\Closure $onModifyConfig = null, ?\Closure $onRenamePackage = null): object
{
    return new class($onModifyConfig, $onRenamePackage)
    {
        private ?\Closure $onModifyConfig;

        private ?\Closure $onRenamePackage;

        public function __construct(?\Closure $onModifyConfig = null, ?\Closure $onRenamePackage = null)
        {
            $this->onModifyConfig = $onModifyConfig;
            $this->onRenamePackage = $onRenamePackage;
        }

        public function modifyConfig(ApkBuildConfig $config): void
        {
            if ($this->onModifyConfig) {
                ($this->onModifyConfig)($config);
            }
        }

        public function renamePackage(string $oldPackage, string $newPackage): void
        {
            if ($this->onRenamePackage) {
                ($this->onRenamePackage)($oldPackage, $newPackage);
            }
        }
    };
}

/**
 * Create a test double for Obfuscator (final class, cannot be mocked).
 * Returns an anonymous class with the same public interface.
 */
function createFakeObfuscator(?\Closure $onGenerateJunkClasses = null, ?\Closure $onShuffleClassNames = null): object
{
    return new class($onGenerateJunkClasses, $onShuffleClassNames)
    {
        private ?\Closure $onGenerateJunkClasses;

        private ?\Closure $onShuffleClassNames;

        public function __construct(?\Closure $onGenerateJunkClasses = null, ?\Closure $onShuffleClassNames = null)
        {
            $this->onGenerateJunkClasses = $onGenerateJunkClasses;
            $this->onShuffleClassNames = $onShuffleClassNames;
        }

        public function generateJunkClasses(int $classCount, int $methodCount): int
        {
            if ($this->onGenerateJunkClasses) {
                return ($this->onGenerateJunkClasses)($classCount, $methodCount);
            }

            return $classCount;
        }

        public function shuffleClassNames(): int
        {
            if ($this->onShuffleClassNames) {
                return ($this->onShuffleClassNames)();
            }

            return 5;
        }
    };
}

/**
 * Create a test double for ApkProtector (final class, cannot be mocked).
 * Returns an anonymous class with the same public interface.
 */
function createFakeApkProtector(?\Closure $onProtect = null, ?\Closure $onModifyDex = null): object
{
    return new class($onProtect, $onModifyDex)
    {
        private ?\Closure $onProtect;

        private ?\Closure $onModifyDex;

        public function __construct(?\Closure $onProtect = null, ?\Closure $onModifyDex = null)
        {
            $this->onProtect = $onProtect;
            $this->onModifyDex = $onModifyDex;
        }

        public function protect(string $apkPath): void
        {
            if ($this->onProtect) {
                ($this->onProtect)($apkPath);
            }
        }

        public function modifyDex(string $apkPath): int
        {
            if ($this->onModifyDex) {
                return ($this->onModifyDex)($apkPath);
            }

            return 2;
        }
    };
}

beforeEach(function () {
    Config::set('apk-builder.template_path', '/tmp/template');
    Config::set('apk-builder.stub_zip_path', '/tmp/stub.zip');
    Config::set('apk-builder.tools_path', '/tmp/tools');
    Config::set('apk-builder.output_path', '/tmp/output');
    Config::set('apk-builder.temp_path', '/tmp');
    Config::set('apk-builder.timeout', 300);
    Config::set('apk-builder.cleanup_on_success', false);
    Config::set('apk-builder.cleanup_on_failure', false);
});

describe('ApkBuilder build steps with mocks', function () {
    it('checkDependencies throws when template directory missing', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->with('/tmp/template')->andReturn(false);
        $fileSystem->shouldReceive('exists')->with('/tmp/stub.zip')->andReturn(false);

        $builder = new ApkBuilder(
            fileSystem: $fileSystem,
            processRunner: createMockProcessRunner()
        );

        expect(fn () => $builder->build(createValidConfig()))
            ->toThrow(ApkBuildException::class, 'template directory not found');
    });

    it('checkDependencies throws when apktool.jar missing', function () {
        $fileSystem = createMockFileSystem();
        $fileSystem->shouldReceive('isDirectory')->with('/tmp/template')->andReturn(true);
        $fileSystem->shouldReceive('exists')
            ->with('/tmp/template/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE)
            ->andReturn(true);
        $fileSystem->shouldReceive('exists')->with('/tmp/tools/apktool.jar')->andReturn(false);

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

    it('modifyManifest replaces package name when different from default', function () {
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

        $manifestWasModified = false;
        $fileSystem->shouldReceive('put')
            ->andReturnUsing(function ($path, $content) use (&$manifestWasModified) {
                if (str_contains($path, 'AndroidManifest.xml')) {
                    $manifestWasModified = str_contains($content, 'com.test.app');
                }

                return true;
            });

        $renamePackageCalled = false;
        $smaliProcessor = createFakeSmaliProcessor(
            onRenamePackage: function ($oldPackage, $newPackage) use (&$renamePackageCalled) {
                expect($oldPackage)->toBe(ApkBuilderConstants::DEFAULT_PACKAGE);
                expect($newPackage)->toBe('com.test.app');
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

        expect($manifestWasModified)->toBeTrue();
        expect($renamePackageCalled)->toBeTrue();
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

        expect($protectCalled)->toBeTrue();
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
