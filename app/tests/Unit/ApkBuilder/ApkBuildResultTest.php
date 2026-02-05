<?php

use App\Services\ApkBuilder\ApkBuildResult;

describe('ApkBuildResult formatTime', function () {
    it('formatTime returns ms for under 1000', function () {
        $result = new ApkBuildResult(path: '/out.apk', stats: [], totalTimeMs: 500);

        expect($result->formatTime())->toMatch('/^\d+ms$/');
        expect($result->formatTime(500))->toMatch('/500ms/');
    });

    it('formatTime returns seconds for under 60000', function () {
        $result = new ApkBuildResult(path: '/out.apk', stats: [], totalTimeMs: 15000);

        expect($result->formatTime(15000))->toMatch('/s$/');
        expect($result->formatTime(15000))->toMatch('/15/');
    });

    it('formatTime returns minutes for 60000 and above', function () {
        $result = new ApkBuildResult(path: '/out.apk', stats: [], totalTimeMs: 120000);

        expect($result->formatTime(120000))->toMatch('/min$/');
    });
});

describe('ApkBuildResult toArray', function () {
    it('toArray includes path and stats', function () {
        $result = new ApkBuildResult(
            path: '/storage/apk/1/com.test.app/com.test.app.apk',
            stats: ['check_dependencies' => 10.5, 'prepare_work_dir' => 20.0],
            totalTimeMs: 100.5
        );

        $arr = $result->toArray();

        expect($arr)->toHaveKeys(['path', 'stats']);
        expect($arr['path'])->toBe('/storage/apk/1/com.test.app/com.test.app.apk');
        expect($arr['stats'])->toHaveKeys(['steps', 'total_time_ms', 'total_time_formatted']);
        expect($arr['stats']['steps'])->toBe(['check_dependencies' => 10.5, 'prepare_work_dir' => 20.0]);
    });
});
