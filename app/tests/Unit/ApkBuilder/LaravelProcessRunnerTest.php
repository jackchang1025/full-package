<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use App\Services\ApkBuilder\LaravelProcessRunner;

describe('LaravelProcessRunner', function () {
    it('implements ProcessRunnerInterface', function () {
        $runner = new LaravelProcessRunner;

        expect($runner)->toBeInstanceOf(ProcessRunnerInterface::class);
    });

    it('run executes command and returns result', function () {
        $runner = new LaravelProcessRunner;

        $result = $runner->run('echo "hello"');

        expect($result->successful())->toBeTrue();
        expect(trim($result->output()))->toBe('hello');
    });

    it('run returns failed result for invalid command', function () {
        $runner = new LaravelProcessRunner;

        $result = $runner->run('nonexistent_command_xyz_123');

        expect($result->successful())->toBeFalse();
    });

    it('timeout returns PendingProcess', function () {
        $runner = new LaravelProcessRunner;

        $pending = $runner->timeout(30);

        expect($pending)->toBeInstanceOf(\Illuminate\Process\PendingProcess::class);
    });

    it('start returns InvokedProcess', function () {
        $runner = new LaravelProcessRunner;

        $process = $runner->start('echo "test"', 30);

        expect($process)->toBeInstanceOf(\Illuminate\Process\InvokedProcess::class);

        $process->wait();
    });
});
