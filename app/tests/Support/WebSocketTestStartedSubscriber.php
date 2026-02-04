<?php

declare(strict_types=1);

namespace Tests\Support;

use PHPUnit\Event\TestSuite\Started;
use PHPUnit\Event\TestSuite\StartedSubscriber;

/**
 * 测试套件开始时启动 WebSocket 服务器
 */
final class WebSocketTestStartedSubscriber implements StartedSubscriber
{
    public function notify(Started $event): void
    {
        $suiteName = $event->testSuite()->name();

        // 只在 WebSocket 测试套件或包含 WebSocket 目录的测试中启动服务器
        if ($this->shouldStartServer($suiteName)) {
            $this->startServer();
        }
    }

    private function shouldStartServer(string $suiteName): bool
    {
        // 匹配 WebSocket 测试套件
        if (stripos($suiteName, 'WebSocket') !== false) {
            return true;
        }

        // 匹配测试目录路径
        if (stripos($suiteName, 'tests/Feature/WebSocket') !== false) {
            return true;
        }

        // 匹配 Pest 运行时的路径格式
        if (stripos($suiteName, 'Feature\\WebSocket') !== false) {
            return true;
        }

        return false;
    }

    private function startServer(): void
    {
        // 如果已经启动则跳过
        if (WebSocketTestServer::isStarted()) {
            return;
        }

        try {
            echo "\n[WebSocket Test] Starting test server on random port...\n";
            WebSocketTestServer::start(15);
            $port = WebSocketTestServer::getPort();
            echo "[WebSocket Test] Server started on port {$port}\n\n";
        } catch (\Throwable $e) {
            echo "\n[WebSocket Test] Failed to start server: {$e->getMessage()}\n";
            throw $e;
        }
    }
}
