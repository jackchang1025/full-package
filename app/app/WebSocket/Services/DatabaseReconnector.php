<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\WebSocket\WebSocketLog;
use Illuminate\Support\Facades\DB;

class DatabaseReconnector
{
    /**
     * 确保数据库连接可用。
     * 在 Swoole 长连接环境中，连接可能因超时断开，调用此方法重新建立连接。
     */
    public function reconnect(): void
    {
        DB::reconnect();
    }

    /**
     * 重置所有数据库连接。
     * 在 Swoole Worker 进程启动时调用，确保每个 Worker 有独立的连接。
     *
     * @param  bool  $lazy  是否懒加载（不立即建立连接）
     */
    public function reset(bool $lazy = false): void
    {
        try {
            DB::purge();

            if (! $lazy) {
                DB::reconnect();
            }
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning('Failed to reset database connections', [
                'error' => $e->getMessage(),
            ]);
        }
    }
}
