<?php

use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;

describe('ApkBuilder auto-generates appId', function () {
    it('generates valid package name format', function () {
        $mockFileSystem = Mockery::mock(FileSystemInterface::class);
        $mockProcessRunner = Mockery::mock(ProcessRunnerInterface::class);
        
        $builder = new ApkBuilder(
            fileSystem: $mockFileSystem,
            processRunner: $mockProcessRunner
        );
        
        $reflection = new ReflectionClass($builder);
        $method = $reflection->getMethod('generateRandomPackageName');
        $method->setAccessible(true);
        
        $packageName = $method->invoke($builder);
        
        expect($packageName)
            ->toMatch('/^com\.[a-z]+\.[a-z]+$/')
            ->toBeString();
    });
    
    it('generates unique package names', function () {
        $mockFileSystem = Mockery::mock(FileSystemInterface::class);
        $mockProcessRunner = Mockery::mock(ProcessRunnerInterface::class);
        
        $builder = new ApkBuilder(
            fileSystem: $mockFileSystem,
            processRunner: $mockProcessRunner
        );
        
        $reflection = new ReflectionClass($builder);
        $method = $reflection->getMethod('generateRandomPackageName');
        $method->setAccessible(true);
        
        $names = array_map(
            fn() => $method->invoke($builder),
            range(1, 50)
        );
        
        expect(count(array_unique($names)))->toBeGreaterThan(40);
    });
});

describe('ApkBuilder auto-generates appVersion', function () {
    it('generates valid semantic version format', function () {
        $mockFileSystem = Mockery::mock(FileSystemInterface::class);
        $mockProcessRunner = Mockery::mock(ProcessRunnerInterface::class);
        
        $builder = new ApkBuilder(
            fileSystem: $mockFileSystem,
            processRunner: $mockProcessRunner
        );
        
        $reflection = new ReflectionClass($builder);
        $method = $reflection->getMethod('generateRandomVersion');
        $method->setAccessible(true);
        
        $version = $method->invoke($builder);
        
        expect($version)
            ->toMatch('/^\d+\.\d+\.\d+$/')
            ->toBeString();
    });
    
    it('generates version with valid ranges', function () {
        $mockFileSystem = Mockery::mock(FileSystemInterface::class);
        $mockProcessRunner = Mockery::mock(ProcessRunnerInterface::class);
        
        $builder = new ApkBuilder(
            fileSystem: $mockFileSystem,
            processRunner: $mockProcessRunner
        );
        
        $reflection = new ReflectionClass($builder);
        $method = $reflection->getMethod('generateRandomVersion');
        $method->setAccessible(true);
        
        $version = $method->invoke($builder);
        [$major, $minor, $patch] = explode('.', $version);
        
        expect((int)$major)->toBeGreaterThanOrEqual(1)->toBeLessThanOrEqual(9);
        expect((int)$minor)->toBeGreaterThanOrEqual(0)->toBeLessThanOrEqual(99);
        expect((int)$patch)->toBeGreaterThanOrEqual(0)->toBeLessThanOrEqual(99);
    });
});
