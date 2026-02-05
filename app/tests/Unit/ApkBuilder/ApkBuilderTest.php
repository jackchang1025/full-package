<?php

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\Encryptor;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\File;

beforeEach(function () {
    $this->tempBase = sys_get_temp_dir() . '/apk_builder_unit_' . uniqid();
    File::ensureDirectoryExists($this->tempBase);
});

afterEach(function () {
    if (isset($this->tempBase) && File::isDirectory($this->tempBase)) {
        File::deleteDirectory($this->tempBase);
    }
});

describe('ApkBuilder build', function () {
    it('build throws when config validation fails', function () {
        Config::set('apk-builder.template_path', $this->tempBase . '/template');
        Config::set('apk-builder.stub_zip_path', $this->tempBase . '/apkstub.zip');
        Config::set('apk-builder.tools_path', $this->tempBase . '/tools');
        Config::set('apk-builder.output_path', $this->tempBase . '/output');
        Config::set('apk-builder.temp_path', $this->tempBase);

        $builder = new ApkBuilder();
        $config = new ApkBuildConfig(
            appId: '',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect(fn() => $builder->build($config))->toThrow(ApkBuildException::class);
    });
});

describe('ApkBuilder dependency injection', function () {
    it('accepts custom Encryptor via constructor', function () {
        $encryptor = new Encryptor();
        $builder = new ApkBuilder(encryptor: $encryptor);

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });

    it('works with default constructor for backward compatibility', function () {
        $builder = new ApkBuilder();

        expect($builder)->toBeInstanceOf(ApkBuilder::class);
    });
});

describe('ApkBuilder buildWithProgress', function () {
    it('invokes progress callback and throws on config validation failure', function () {
        Config::set('apk-builder.template_path', $this->tempBase . '/template');
        Config::set('apk-builder.tools_path', $this->tempBase . '/tools');
        Config::set('apk-builder.output_path', $this->tempBase . '/output');
        Config::set('apk-builder.temp_path', $this->tempBase);

        $builder = new ApkBuilder();
        $config = new ApkBuildConfig(
            appId: '',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );
        $progressCalls = [];
        $onProgress = function (array $data) use (&$progressCalls) {
            $progressCalls[] = $data;
        };

        expect(fn() => $builder->buildWithProgress($config, $onProgress))->toThrow(ApkBuildException::class);
        expect($progressCalls)->not->toBeEmpty();
        expect(collect($progressCalls)->pluck('type'))->toContain('error');
    });
});

describe('ApkBuilder checkDependencies', function () {
    it('throws when template invalid and zip missing', function () {
        $templateDir = $this->tempBase . '/template';
        File::ensureDirectoryExists($templateDir);

        Config::set('apk-builder.template_path', $templateDir);
        Config::set('apk-builder.stub_zip_path', $this->tempBase . '/nonexistent.zip');
        Config::set('apk-builder.tools_path', $this->tempBase . '/tools');
        Config::set('apk-builder.output_path', $this->tempBase . '/output');
        Config::set('apk-builder.temp_path', $this->tempBase);

        File::ensureDirectoryExists($this->tempBase . '/tools');
        File::put($this->tempBase . '/tools/apktool.jar', 'dummy');

        $builder = new ApkBuilder();
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );

        expect(fn() => $builder->build($config))->toThrow(ApkBuildException::class);
    });
});
