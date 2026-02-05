<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;
use Illuminate\Process\PendingProcess;
use Illuminate\Support\Facades\Process;

final class LaravelProcessRunner implements ProcessRunnerInterface
{
    public function run(string $command): ProcessResult
    {
        return Process::run($command);
    }

    public function timeout(int $seconds): PendingProcess
    {
        return Process::timeout($seconds);
    }

    public function start(string $command, int $timeout): InvokedProcess
    {
        return Process::timeout($timeout)->start($command);
    }
}
