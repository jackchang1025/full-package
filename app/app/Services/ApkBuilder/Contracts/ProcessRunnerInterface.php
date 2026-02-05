<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder\Contracts;

use Illuminate\Contracts\Process\ProcessResult;
use Illuminate\Process\InvokedProcess;
use Illuminate\Process\PendingProcess;

interface ProcessRunnerInterface
{
    public function run(string $command): ProcessResult;

    public function timeout(int $seconds): PendingProcess;

    public function start(string $command, int $timeout): InvokedProcess;
}
