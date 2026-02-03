<?php

declare(strict_types=1);

namespace App\WebSocket;

use App\WebSocket\Services\HeartbeatService;
use Illuminate\Support\Facades\DB;
use Swoole\Http\Request;
use Swoole\Table;
use Swoole\WebSocket\Frame;
use Swoole\WebSocket\Server as SwooleServer;

final class Server
{
    private SwooleServer $server;
    private ConnectionManager $connectionManager;
    private MessageRouter $messageRouter;
    private HeartbeatService $heartbeatService;

    /**
     * 共享内存表 - 必须在 server->start() 之前创建，才能在所有 Worker 间共享
     */
    private Table $fdToPhoneId;
    private Table $phoneIdToFd;
    private Table $panelSubscriptions;
    private Table $panelUserSubscriptions;

    private const TABLE_SIZE = 65536;

    public function __construct()
    {
        $host = config('websocket.host', '0.0.0.0');
        $port = config('websocket.port', 8081);

        $this->server = new SwooleServer($host, $port);
        $this->server->set(config('websocket.settings', []));

        // 关键：在 server->start() 之前创建 Swoole Table，这样所有 Worker 共享同一份数据
        $this->initializeSharedTables();

        $this->connectionManager = new ConnectionManager($this->server, [
            'fdToPhoneId' => $this->fdToPhoneId,
            'phoneIdToFd' => $this->phoneIdToFd,
            'panelSubscriptions' => $this->panelSubscriptions,
            'panelUserSubscriptions' => $this->panelUserSubscriptions,
        ]);
        $this->heartbeatService = new HeartbeatService($this->connectionManager);
        $this->messageRouter = new MessageRouter($this->connectionManager, $this->heartbeatService);

        $this->registerEventHandlers();
    }

    /**
     * 初始化共享内存表
     * 必须在 server->start() 之前调用，才能实现多 Worker 共享
     */
    private function initializeSharedTables(): void
    {
        $this->fdToPhoneId = new Table(self::TABLE_SIZE);
        $this->fdToPhoneId->column('phone_id', Table::TYPE_STRING, 64);
        $this->fdToPhoneId->column('client_type', Table::TYPE_STRING, 16);
        $this->fdToPhoneId->create();

        $this->phoneIdToFd = new Table(self::TABLE_SIZE);
        $this->phoneIdToFd->column('fd', Table::TYPE_INT);
        $this->phoneIdToFd->create();

        $this->panelSubscriptions = new Table(self::TABLE_SIZE);
        $this->panelSubscriptions->column('phone_id', Table::TYPE_STRING, 64);
        $this->panelSubscriptions->create();

        $this->panelUserSubscriptions = new Table(self::TABLE_SIZE);
        $this->panelUserSubscriptions->column('email_encrypted', Table::TYPE_STRING, 128);
        $this->panelUserSubscriptions->column('is_admin', Table::TYPE_INT, 1);
        $this->panelUserSubscriptions->create();

        WebSocketLog::getLogger()->info('Shared tables initialized before server start');
    }

    public function start(): void
    {
        $host = config('websocket.host');
        $port = config('websocket.port');

        WebSocketLog::getLogger()->debug("WebSocket server starting on {$host}:{$port}");

        $this->server->start();
    }

    private function registerEventHandlers(): void
    {
        $this->server->on('start', [$this, 'onStart']);
        $this->server->on('workerStart', [$this, 'onWorkerStart']);
        $this->server->on('open', [$this, 'onOpen']);
        $this->server->on('message', [$this, 'onMessage']);
        $this->server->on('close', [$this, 'onClose']);
    }

    public function onStart(SwooleServer $server): void
    {
        $masterPid = $server->master_pid;
        $managerPid = $server->manager_pid;

        WebSocketLog::getLogger()->debug('Server started', [
            'master_pid' => $masterPid,
            'manager_pid' => $managerPid,
        ]);

        $pidFile = storage_path('app/websocket.pid');
        file_put_contents($pidFile, $masterPid);
    }

    public function onWorkerStart(SwooleServer $server, int $workerId): void
    {
        // Worker 内使用 Laravel 的 websocket channel（config/logging.php），统一配置与按日分割
        WebSocketLog::useLaravelChannel();

        // 重置数据库连接 - Swoole 每个 Worker 需要独立的连接
        // 使用懒加载模式：只清除旧连接，不立即建立新连接
        // 新连接会在首次数据库查询时自动建立
        $this->resetDatabaseConnections(lazy: true);

        WebSocketLog::getLogger()->info("Worker {$workerId} started");

        if ($workerId === 0) {
            $this->startHeartbeatTimer($server);
        }
    }

    /**
     * 重置所有数据库连接
     * 在 Swoole Worker 进程启动时调用，确保每个 Worker 有独立的连接
     *
     * @param bool $lazy 是否懒加载（不立即建立连接）
     */
    private function resetDatabaseConnections(bool $lazy = false): void
    {
        try {
            // 断开所有现有连接
            DB::purge();

            // 如果不是懒加载模式，立即重新建立连接
            if (!$lazy) {
                DB::reconnect();
            }
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning('Failed to reset database connections', [
                'error' => $e->getMessage(),
            ]);
        }
    }

    public function onOpen(SwooleServer $server, Request $request): void
    {
        $fd = $request->fd;
        $path = $request->server['request_uri'] ?? '/';

        WebSocketLog::getLogger()->debug("Connection opened: fd={$fd}, path={$path}");
    }

    public function onMessage(SwooleServer $server, Frame $frame): void
    {
        $fd = $frame->fd;
        $data = $frame->data;

        WebSocketLog::getLogger()->debug("Message received: fd={$fd}", ['data' => substr($data, 0, 500)]);

        try {
            $this->messageRouter->route($fd, $data);
        } catch (\Throwable $e) {
            // 检查是否是数据库连接错误，如果是则尝试重连后重试
            if ($this->isDatabaseConnectionError($e)) {
                $this->handleDatabaseConnectionError($fd, $data, $e);
            } else {
                WebSocketLog::getLogger()->error("Message handling error: fd={$fd}", [
                    'error' => $e->getMessage(),
                    'trace' => $e->getTraceAsString(),
                ]);
                $this->sendErrorResponse($fd, 'Internal server error');
            }
        }
    }

    /**
     * 处理数据库连接错误，带延迟重试机制
     */
    private function handleDatabaseConnectionError(int $fd, string $data, \Throwable $originalError): void
    {
        $maxRetries = 3;
        $retryDelays = [100, 500, 1000]; // 毫秒: 100ms, 500ms, 1s

        for ($attempt = 1; $attempt <= $maxRetries; $attempt++) {
            $delay = $retryDelays[$attempt - 1] ?? 1000;

            WebSocketLog::getLogger()->warning("Database connection error, retry {$attempt}/{$maxRetries} after {$delay}ms: fd={$fd}");

            // 等待一段时间再重试（Swoole 协程友好的方式）
            usleep($delay * 1000);

            // 重置数据库连接
            $this->resetDatabaseConnections();

            try {
                $this->messageRouter->route($fd, $data);
                WebSocketLog::getLogger()->info("Database connection recovered after retry {$attempt}: fd={$fd}");
                return; // 成功，退出
            } catch (\Throwable $retryException) {
                if ($attempt === $maxRetries) {
                    WebSocketLog::getLogger()->error("Message handling failed after {$maxRetries} retries: fd={$fd}", [
                        'error' => $retryException->getMessage(),
                    ]);
                    $this->sendErrorResponse($fd, 'Database temporarily unavailable, please retry');
                }
            }
        }
    }

    /**
     * 发送错误响应给客户端
     */
    private function sendErrorResponse(int $fd, string $message): void
    {
        try {
            $this->connectionManager->send($fd, [
                'type' => 'error',
                'error' => $message,
                'timestamp' => time(),
            ]);
        } catch (\Throwable $e) {
            // 发送失败忽略，可能连接已关闭
        }
    }

    /**
     * 检查异常是否是数据库连接错误
     */
    private function isDatabaseConnectionError(\Throwable $e): bool
    {
        $message = $e->getMessage();

        $connectionErrors = [
            'Connection refused',
            'server has gone away',
            'Lost connection',
            'is not a valid',
            'No connection could be made',
            'Connection timed out',
            'SQLSTATE[HY000] [2002]',
            'SQLSTATE[HY000] [2006]',
            'SQLSTATE[08S01]',
        ];

        foreach ($connectionErrors as $error) {
            if (stripos($message, $error) !== false) {
                return true;
            }
        }

        return false;
    }

    public function onClose(SwooleServer $server, int $fd): void
    {
        WebSocketLog::getLogger()->debug("Connection closed: fd={$fd}");

        $this->connectionManager->handleDisconnect($fd);
    }

    private function startHeartbeatTimer(SwooleServer $server): void
    {
        $interval = config('websocket.heartbeat.check_interval', 25) * 1000;

        \Swoole\Timer::tick($interval, function () {
            $this->heartbeatService->checkAll();
        });
    }

    public function getServer(): SwooleServer
    {
        return $this->server;
    }

    public function getConnectionManager(): ConnectionManager
    {
        return $this->connectionManager;
    }
}
