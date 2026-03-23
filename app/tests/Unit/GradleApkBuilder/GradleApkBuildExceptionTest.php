<?php

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;

describe('GradleApkBuildException', function () {
    it('creates with step and build output', function () {
        $e = new GradleApkBuildException(
            message: 'Build failed',
            step: 'gradle_build',
            buildOutput: 'FAILURE: Build failed with an exception',
        );

        expect($e->getMessage())->toBe('Build failed');
        expect($e->step)->toBe('gradle_build');
        expect($e->buildOutput)->toContain('FAILURE');
    });

    it('creates environment missing exception', function () {
        $e = GradleApkBuildException::environmentMissing('JAVA_HOME', '路径不存在');

        expect($e->getMessage())->toContain('JAVA_HOME');
        expect($e->getMessage())->toContain('路径不存在');
        expect($e->step)->toBe('check_environment');
    });

    it('creates step failed exception', function () {
        $e = GradleApkBuildException::stepFailed('modify_config', '无法写入文件', 'Permission denied');

        expect($e->getMessage())->toContain('modify_config');
        expect($e->getMessage())->toContain('无法写入文件');
        expect($e->step)->toBe('modify_config');
        expect($e->buildOutput)->toBe('Permission denied');
    });
});
