<?php

declare(strict_types=1);

namespace App\WebSocket;

use Monolog\Handler\RotatingFileHandler;
use Monolog\Logger;
use Psr\Log\LoggerInterface;

/**
 * WebSocket 专用日志入口。
 *
 * - Worker 内：由 Server::onWorkerStart 注入 Laravel Log::channel('websocket')，
 *   使用 config/logging.php 的 websocket channel 配置（按日分割等）。
 * - 主进程或未注入时：getLogger() 使用内置 RotatingFileHandler 回退，不在热路径
 *   调用 Log::channel()，避免 Swoole 回调内触发容器解析无限循环。
 */
final class WebSocketLog
{
    /** 回退 logger 保留天数，与 config/logging.php daily 一致 */
    private const DEFAULT_DAYS = 14;

    private static ?LoggerInterface $logger = null;

    public static function getLogger(): LoggerInterface
    {
        if (self::$logger !== null) {
            return self::$logger;
        }

        self::$logger = self::createFallbackLogger();

        return self::$logger;
    }

    /**
     * 注入 Laravel 的 websocket channel，Worker 启动时调用后即使用 Laravel 日志配置。
     */
    public static function useLaravelChannel(): void
    {
        self::$logger = \Illuminate\Support\Facades\Log::channel('websocket');
    }

    /**
     * 供测试或显式注入自定义 logger 时使用。
     */
    public static function setLogger(LoggerInterface $logger): void
    {
        self::$logger = $logger;
    }

    private static function createFallbackLogger(): LoggerInterface
    {
        $path = storage_path('logs/websocket/websocket.log');
        $dir = dirname($path);
        if (! is_dir($dir)) {
            @mkdir($dir, 0755, true);
        }

        $logger = new Logger('websocket');
        $logger->pushHandler(new RotatingFileHandler(
            $path,
            self::DEFAULT_DAYS,
            Logger::DEBUG,
            true,
            null,
            false
        ));

        return $logger;
    }
}
