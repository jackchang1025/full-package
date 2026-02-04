<?php

declare(strict_types=1);

namespace Tests\Support;

use PHPUnit\Event\TestSuite\Finished;
use PHPUnit\Event\TestSuite\FinishedSubscriber;

/**
 * 测试套件结束时关闭 WebSocket 服务器
 */
final class WebSocketTestFinishedSubscriber implements FinishedSubscriber
{
    public function notify(Finished $event): void
    {
        // 只有在服务器已启动时才关闭
        if (WebSocketTestServer::isStarted()) {
            $this->stopServer();
        }
    }

    private function stopServer(): void
    {
        try {
            $port = WebSocketTestServer::getPort();
            echo "\n[WebSocket Test] Stopping test server on port {$port}...\n";
            WebSocketTestServer::stop();
            echo "[WebSocket Test] Server stopped\n";
        } catch (\Throwable $e) {
            echo "\n[WebSocket Test] Error stopping server: {$e->getMessage()}\n";
        }
    }
}
