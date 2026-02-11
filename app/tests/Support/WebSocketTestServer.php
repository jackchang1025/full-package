<?php

declare(strict_types=1);

namespace Tests\Support;

/**
 * WebSocket 测试服务器管理器
 *
 * 在测试运行前自动启动一个使用随机端口的 WebSocket 服务器，
 * 测试完成后自动关闭，避免端口冲突。
 */
final class WebSocketTestServer
{
    private static ?int $port = null;

    /** @var resource|null */
    private static $process = null;

    /** @var resource[]|null */
    private static ?array $pipes = null;

    private static bool $started = false;

    /**
     * 启动测试 WebSocket 服务器
     */
    public static function start(int $timeoutSeconds = 15): void
    {
        if (self::$started) {
            return;
        }

        self::$port = self::findAvailablePort();

        $artisanPath = self::getArtisanPath();
        $basePath = self::getBasePath();
        $logFile = $basePath.'/storage/logs/websocket-test.log';

        // 确保日志目录存在
        $logDir = dirname($logFile);
        if (! is_dir($logDir)) {
            mkdir($logDir, 0755, true);
        }

        // 使用 proc_open 启动服务器进程
        $command = sprintf(
            'exec php %s websocket:serve --port=%d 2>&1',
            escapeshellarg($artisanPath),
            self::$port
        );

        $descriptors = [
            0 => ['pipe', 'r'], // stdin
            1 => ['file', $logFile, 'a'], // stdout -> log file
            2 => ['file', $logFile, 'a'], // stderr -> log file
        ];

        $env = array_merge($_ENV, [
            'APP_ENV' => 'testing',
            'WEBSOCKET_HOST' => '127.0.0.1',
            'WEBSOCKET_PORT' => (string) self::$port,
            'DEVICE_AUTH_SECRET' => self::getTestSecret(),
            'PANEL_AUTH_SECRET' => self::getTestSecret(),
        ]);

        self::$process = proc_open($command, $descriptors, self::$pipes, $basePath, $env);

        if (! is_resource(self::$process)) {
            throw new \RuntimeException('Failed to start WebSocket test server');
        }

        self::$started = true;

        // 等待服务器就绪
        if (! self::waitUntilReady($timeoutSeconds)) {
            self::stop();
            throw new \RuntimeException(
                sprintf('WebSocket test server failed to start on port %d within %d seconds', self::$port, $timeoutSeconds)
            );
        }
    }

    /**
     * 停止测试服务器
     */
    public static function stop(): void
    {
        if (! self::$started || ! is_resource(self::$process)) {
            self::reset();

            return;
        }

        // 获取进程状态
        $status = proc_get_status(self::$process);

        if ($status['running']) {
            $pid = $status['pid'];

            // 发送 SIGTERM
            if (function_exists('posix_kill')) {
                // 发送 SIGTERM 给进程组
                posix_kill(-$pid, SIGTERM);
                posix_kill($pid, SIGTERM);
            } else {
                // Windows 兼容
                proc_terminate(self::$process, SIGTERM);
            }

            // 等待进程退出 (最多 5 秒)
            $waited = 0;
            while ($waited < 50) {
                $status = proc_get_status(self::$process);
                if (! $status['running']) {
                    break;
                }
                usleep(100000); // 100ms
                $waited++;
            }

            // 如果还在运行，强制 kill
            $status = proc_get_status(self::$process);
            if ($status['running']) {
                if (function_exists('posix_kill')) {
                    posix_kill(-$status['pid'], SIGKILL);
                    posix_kill($status['pid'], SIGKILL);
                } else {
                    proc_terminate(self::$process, SIGKILL);
                }
            }
        }

        // 关闭管道
        if (self::$pipes) {
            foreach (self::$pipes as $pipe) {
                if (is_resource($pipe)) {
                    fclose($pipe);
                }
            }
        }

        proc_close(self::$process);

        self::reset();
    }

    /**
     * 获取测试服务器端口
     */
    public static function getPort(): ?int
    {
        return self::$port;
    }

    /**
     * 检查服务器是否已启动
     */
    public static function isStarted(): bool
    {
        return self::$started;
    }

    /**
     * 检查服务器进程是否仍在运行
     */
    public static function isRunning(): bool
    {
        if (! self::$started || ! is_resource(self::$process)) {
            return false;
        }

        $status = proc_get_status(self::$process);

        return $status['running'];
    }

    /**
     * 查找可用的随机端口
     */
    private static function findAvailablePort(): int
    {
        // 创建一个临时 socket，让系统分配端口
        $socket = stream_socket_server('tcp://127.0.0.1:0', $errno, $errstr);

        if ($socket === false) {
            throw new \RuntimeException("Failed to find available port: {$errstr}");
        }

        $address = stream_socket_get_name($socket, false);
        fclose($socket);

        if ($address === false) {
            throw new \RuntimeException('Failed to get socket address');
        }

        $parts = explode(':', $address);

        return (int) end($parts);
    }

    /**
     * 等待服务器就绪
     */
    private static function waitUntilReady(int $timeoutSeconds): bool
    {
        $startTime = time();
        $host = '127.0.0.1';
        $port = self::$port;

        while ((time() - $startTime) < $timeoutSeconds) {
            // 检查进程是否还在运行
            if (! self::isRunning()) {
                return false;
            }

            // 尝试 TCP 连接
            $fp = @stream_socket_client(
                "tcp://{$host}:{$port}",
                $errno,
                $errstr,
                1,
                STREAM_CLIENT_CONNECT
            );

            if ($fp !== false) {
                fclose($fp);

                return true;
            }

            // 等待 200ms 再试
            usleep(200000);
        }

        return false;
    }

    /**
     * 获取 artisan 脚本路径
     * 不依赖 Laravel 容器，直接从文件系统推断
     */
    private static function getArtisanPath(): string
    {
        // 从当前文件位置推断项目根目录
        // 当前文件: tests/Support/WebSocketTestServer.php
        // 项目根目录: ../../
        $dir = __DIR__;

        // 向上查找包含 artisan 文件的目录
        for ($i = 0; $i < 5; $i++) {
            $artisanPath = $dir.'/artisan';
            if (file_exists($artisanPath)) {
                return $artisanPath;
            }
            $dir = dirname($dir);
        }

        // 回退到 base_path (如果 Laravel 已加载)
        if (function_exists('base_path')) {
            return base_path('artisan');
        }

        throw new \RuntimeException('Unable to locate artisan file');
    }

    /**
     * 获取项目根目录
     */
    private static function getBasePath(): string
    {
        $dir = __DIR__;

        for ($i = 0; $i < 5; $i++) {
            if (file_exists($dir.'/artisan')) {
                return $dir;
            }
            $dir = dirname($dir);
        }

        if (function_exists('base_path')) {
            return base_path();
        }

        throw new \RuntimeException('Unable to locate project root');
    }

    /**
     * 测试用固定密钥，与服务器启动时传入的 DEVICE_AUTH_SECRET 一致
     */
    public static function getTestSecret(): string
    {
        return 'websocket-test-secret-key-for-ci';
    }

    /**
     * 重置状态
     */
    private static function reset(): void
    {
        self::$process = null;
        self::$pipes = null;
        self::$port = null;
        self::$started = false;
    }
}
