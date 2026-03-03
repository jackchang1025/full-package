<?php

declare(strict_types=1);

namespace Tests\Unit\WebSocket;

use App\WebSocket\Enums\DeviceForwardLogLevel;
use PHPUnit\Framework\Attributes\DataProvider;
use Psr\Log\LogLevel;
use Tests\TestCase;

class DeviceForwardLogLevelTest extends TestCase
{
    #[DataProvider('highFrequencySubcProvider')]
    public function test_high_frequency_subc_returns_debug(string $subc): void
    {
        $level = DeviceForwardLogLevel::forSubc($subc);

        $this->assertSame(DeviceForwardLogLevel::Debug, $level);
        $this->assertSame(LogLevel::DEBUG, $level->toPsrLevel());
    }

    /** @return iterable<string, array{string}> */
    public static function highFrequencySubcProvider(): iterable
    {
        yield 'screen' => ['screen'];
        yield 'screenshot' => ['screenshot'];
        yield 'cam' => ['cam'];
        yield 'mic' => ['mic'];
    }

    #[DataProvider('normalSubcProvider')]
    public function test_normal_subc_returns_info(string $subc): void
    {
        $level = DeviceForwardLogLevel::forSubc($subc);

        $this->assertSame(DeviceForwardLogLevel::Info, $level);
        $this->assertSame(LogLevel::INFO, $level->toPsrLevel());
    }

    /** @return iterable<string, array{string}> */
    public static function normalSubcProvider(): iterable
    {
        yield 'sms' => ['sms'];
        yield 'chat' => ['chat'];
        yield 'klogs' => ['klogs'];
        yield 'unknown' => ['unknown'];
        yield 'proxy' => ['proxy'];
    }
}
