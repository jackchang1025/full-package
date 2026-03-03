<?php

declare(strict_types=1);

namespace App\WebSocket\Enums;

use Psr\Log\LogLevel;

/**
 * 设备消息转发到 Panel 时的日志级别策略。
 *
 * 高频二进制数据（投屏/截图/摄像头/麦克风）仅在 debug 级别记录，避免 info 日志膨胀。
 */
enum DeviceForwardLogLevel: string
{
    case Debug = LogLevel::DEBUG;
    case Info = LogLevel::INFO;

    /** 高频二进制子类型，使用 debug 级别 */
    private const HIGH_FREQUENCY_SUBCS = ['screen', 'screenshot', 'cam', 'mic'];

    public static function forSubc(string $subc): self
    {
        return in_array($subc, self::HIGH_FREQUENCY_SUBCS, true) ? self::Debug : self::Info;
    }

    /** PSR-3 标准日志级别，供 LoggerInterface::log() 使用 */
    public function toPsrLevel(): string
    {
        return $this->value;
    }
}
