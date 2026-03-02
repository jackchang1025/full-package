<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;

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
